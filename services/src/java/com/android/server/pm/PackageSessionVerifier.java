package com.android.server.pm;

/* loaded from: classes2.dex */
final class PackageSessionVerifier {
    private static final java.lang.String TAG = "PackageSessionVerifier";
    private final com.android.server.pm.ApexManager mApexManager;
    private final android.content.Context mContext;
    private final android.os.Handler mHandler;
    private final java.util.function.Supplier<com.android.internal.pm.parsing.PackageParser2> mPackageParserSupplier;
    private final com.android.server.pm.PackageManagerService mPm;
    private final java.util.List<com.android.server.pm.StagingManager.StagedSession> mStagedSessions;

    interface Callback {
        void onResult(int i, java.lang.String str);
    }

    PackageSessionVerifier(android.content.Context context, com.android.server.pm.PackageManagerService packageManagerService, com.android.server.pm.ApexManager apexManager, java.util.function.Supplier<com.android.internal.pm.parsing.PackageParser2> supplier, android.os.Looper looper) {
        this.mStagedSessions = new java.util.ArrayList();
        this.mContext = context;
        this.mPm = packageManagerService;
        this.mApexManager = apexManager;
        this.mPackageParserSupplier = supplier;
        this.mHandler = new android.os.Handler(looper);
    }

    @com.android.internal.annotations.VisibleForTesting
    PackageSessionVerifier() {
        this.mStagedSessions = new java.util.ArrayList();
        this.mContext = null;
        this.mPm = null;
        this.mApexManager = null;
        this.mPackageParserSupplier = null;
        this.mHandler = null;
    }

    public void verify(final com.android.server.pm.PackageInstallerSession packageInstallerSession, final com.android.server.pm.PackageSessionVerifier.Callback callback) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.PackageSessionVerifier$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.PackageSessionVerifier.this.lambda$verify$0(packageInstallerSession, callback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$verify$0(com.android.server.pm.PackageInstallerSession packageInstallerSession, com.android.server.pm.PackageSessionVerifier.Callback callback) {
        try {
            storeSession(packageInstallerSession.mStagedSession);
            if (packageInstallerSession.isMultiPackage()) {
                for (com.android.server.pm.PackageInstallerSession packageInstallerSession2 : packageInstallerSession.getChildSessions()) {
                    checkApexUpdateAllowed(packageInstallerSession2);
                    checkRebootlessApex(packageInstallerSession2);
                    checkApexSignature(packageInstallerSession2);
                }
            } else {
                checkApexUpdateAllowed(packageInstallerSession);
                checkRebootlessApex(packageInstallerSession);
                checkApexSignature(packageInstallerSession);
            }
            verifyAPK(packageInstallerSession, callback);
        } catch (com.android.server.pm.PackageManagerException e) {
            packageInstallerSession.setSessionFailed(e.error, android.content.pm.PackageManager.installStatusToString(e.error, e.getMessage()));
            callback.onResult(e.error, e.getMessage());
        }
    }

    private android.content.pm.SigningDetails getSigningDetails(android.content.pm.PackageInfo packageInfo) throws com.android.server.pm.PackageManagerException {
        java.lang.String str = packageInfo.applicationInfo.sourceDir;
        android.content.pm.parsing.result.ParseResult verify = android.util.apk.ApkSignatureVerifier.verify(android.content.pm.parsing.result.ParseTypeImpl.forDefaultParsing(), str, android.util.apk.ApkSignatureVerifier.getMinimumSignatureSchemeVersionForTargetSdk(packageInfo.applicationInfo.targetSdkVersion));
        if (verify.isError()) {
            throw new com.android.server.pm.PackageManagerException(-22, "Failed to verify APEX package " + str + " : " + verify.getException(), verify.getException());
        }
        return (android.content.pm.SigningDetails) verify.getResult();
    }

    private void checkApexSignature(com.android.server.pm.PackageInstallerSession packageInstallerSession) throws com.android.server.pm.PackageManagerException {
        if (!packageInstallerSession.isApexSession()) {
            return;
        }
        java.lang.String packageName = packageInstallerSession.getPackageName();
        android.content.pm.PackageInfo packageInfo = this.mPm.snapshotComputer().getPackageInfo(packageInstallerSession.getPackageName(), 1073741824L, 0);
        if (packageInfo == null) {
            throw new com.android.server.pm.PackageManagerException(-23, "Attempting to install new APEX package " + packageName);
        }
        android.content.pm.SigningDetails signingDetails = getSigningDetails(packageInfo);
        android.content.pm.SigningDetails signingDetails2 = packageInstallerSession.getSigningDetails();
        if (signingDetails2.checkCapability(signingDetails, 1) || signingDetails.checkCapability(signingDetails2, 8)) {
            return;
        }
        throw new com.android.server.pm.PackageManagerException(-22, "APK container signature of APEX package " + packageName + " is not compatible with the one currently installed on device");
    }

    private void verifyAPK(final com.android.server.pm.PackageInstallerSession packageInstallerSession, final com.android.server.pm.PackageSessionVerifier.Callback callback) throws com.android.server.pm.PackageManagerException {
        com.android.server.pm.VerifyingSession createVerifyingSession = createVerifyingSession(packageInstallerSession, new android.content.pm.IPackageInstallObserver2.Stub() { // from class: com.android.server.pm.PackageSessionVerifier.1
            public void onUserActionRequired(android.content.Intent intent) {
                throw new java.lang.IllegalStateException();
            }

            public void onPackageInstalled(java.lang.String str, int i, java.lang.String str2, android.os.Bundle bundle) {
                if (packageInstallerSession.isStaged() && i == 1) {
                    com.android.server.pm.PackageSessionVerifier.this.verifyStaged(packageInstallerSession.mStagedSession, callback);
                    return;
                }
                if (i != 1) {
                    packageInstallerSession.setSessionFailed(i, android.content.pm.PackageManager.installStatusToString(i, str2));
                    callback.onResult(i, str2);
                } else {
                    packageInstallerSession.setSessionReady();
                    callback.onResult(1, null);
                }
            }
        });
        if (packageInstallerSession.isMultiPackage()) {
            java.util.List<com.android.server.pm.PackageInstallerSession> childSessions = packageInstallerSession.getChildSessions();
            java.util.ArrayList arrayList = new java.util.ArrayList(childSessions.size());
            java.util.Iterator<com.android.server.pm.PackageInstallerSession> it = childSessions.iterator();
            while (it.hasNext()) {
                arrayList.add(createVerifyingSession(it.next(), null));
            }
            createVerifyingSession.verifyStage(arrayList);
            return;
        }
        createVerifyingSession.verifyStage();
    }

    private com.android.server.pm.VerifyingSession createVerifyingSession(com.android.server.pm.PackageInstallerSession packageInstallerSession, android.content.pm.IPackageInstallObserver2 iPackageInstallObserver2) {
        android.os.UserHandle userHandle;
        if ((packageInstallerSession.params.installFlags & 64) != 0) {
            userHandle = android.os.UserHandle.ALL;
        } else {
            userHandle = new android.os.UserHandle(packageInstallerSession.userId);
        }
        return new com.android.server.pm.VerifyingSession(userHandle, packageInstallerSession.stageDir, iPackageInstallObserver2, packageInstallerSession.params, packageInstallerSession.getInstallSource(), packageInstallerSession.getInstallerUid(), packageInstallerSession.getSigningDetails(), packageInstallerSession.sessionId, packageInstallerSession.getPackageLite(), packageInstallerSession.getUserActionRequired(), this.mPm);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void verifyStaged(final com.android.server.pm.StagingManager.StagedSession stagedSession, final com.android.server.pm.PackageSessionVerifier.Callback callback) {
        android.util.Slog.d(TAG, "Starting preRebootVerification for session " + stagedSession.sessionId());
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.PackageSessionVerifier$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.PackageSessionVerifier.this.lambda$verifyStaged$1(stagedSession, callback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$verifyStaged$1(com.android.server.pm.StagingManager.StagedSession stagedSession, com.android.server.pm.PackageSessionVerifier.Callback callback) {
        try {
            checkActiveSessions();
            checkRollbacks(stagedSession);
            if (stagedSession.isMultiPackage()) {
                java.util.Iterator<com.android.server.pm.StagingManager.StagedSession> it = stagedSession.getChildSessions().iterator();
                while (it.hasNext()) {
                    checkOverlaps(stagedSession, it.next());
                }
            } else {
                checkOverlaps(stagedSession, stagedSession);
            }
            dispatchVerifyApex(stagedSession, callback);
        } catch (com.android.server.pm.PackageManagerException e) {
            onVerificationFailure(stagedSession, callback, e.error, e.getMessage());
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void storeSession(com.android.server.pm.StagingManager.StagedSession stagedSession) {
        if (stagedSession != null) {
            this.mStagedSessions.add(stagedSession);
        }
    }

    private void onVerificationSuccess(com.android.server.pm.StagingManager.StagedSession stagedSession, com.android.server.pm.PackageSessionVerifier.Callback callback) {
        callback.onResult(1, null);
    }

    private void onVerificationFailure(com.android.server.pm.StagingManager.StagedSession stagedSession, com.android.server.pm.PackageSessionVerifier.Callback callback, int i, java.lang.String str) {
        if (!ensureActiveApexSessionIsAborted(stagedSession)) {
            android.util.Slog.e(TAG, "Failed to abort apex session " + stagedSession.sessionId());
        }
        stagedSession.setSessionFailed(i, str);
        callback.onResult(-22, str);
    }

    private void dispatchVerifyApex(final com.android.server.pm.StagingManager.StagedSession stagedSession, final com.android.server.pm.PackageSessionVerifier.Callback callback) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.PackageSessionVerifier$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.PackageSessionVerifier.this.lambda$dispatchVerifyApex$2(stagedSession, callback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispatchVerifyApex$2(com.android.server.pm.StagingManager.StagedSession stagedSession, com.android.server.pm.PackageSessionVerifier.Callback callback) {
        try {
            verifyApex(stagedSession);
            dispatchEndVerification(stagedSession, callback);
        } catch (com.android.server.pm.PackageManagerException e) {
            onVerificationFailure(stagedSession, callback, e.error, e.getMessage());
        }
    }

    private void dispatchEndVerification(final com.android.server.pm.StagingManager.StagedSession stagedSession, final com.android.server.pm.PackageSessionVerifier.Callback callback) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.PackageSessionVerifier$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.PackageSessionVerifier.this.lambda$dispatchEndVerification$3(stagedSession, callback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispatchEndVerification$3(com.android.server.pm.StagingManager.StagedSession stagedSession, com.android.server.pm.PackageSessionVerifier.Callback callback) {
        try {
            endVerification(stagedSession);
            onVerificationSuccess(stagedSession, callback);
        } catch (com.android.server.pm.PackageManagerException e) {
            onVerificationFailure(stagedSession, callback, e.error, e.getMessage());
        }
    }

    private void verifyApex(com.android.server.pm.StagingManager.StagedSession stagedSession) throws com.android.server.pm.PackageManagerException {
        int i = -1;
        if ((stagedSession.sessionParams().installFlags & 262144) != 0) {
            try {
                i = ((com.android.server.rollback.RollbackManagerInternal) com.android.server.LocalServices.getService(com.android.server.rollback.RollbackManagerInternal.class)).notifyStagedSession(stagedSession.sessionId());
            } catch (java.lang.RuntimeException e) {
                android.util.Slog.e(TAG, "Failed to notifyStagedSession for session: " + stagedSession.sessionId(), e);
            }
        } else if (isRollback(stagedSession)) {
            i = retrieveRollbackIdForCommitSession(stagedSession.sessionId());
        }
        if (stagedSession.containsApexSession()) {
            submitSessionToApexService(stagedSession, i);
        }
    }

    private void endVerification(com.android.server.pm.StagingManager.StagedSession stagedSession) throws com.android.server.pm.PackageManagerException {
        try {
            if (com.android.internal.content.InstallLocationUtils.getStorageManager().supportsCheckpoint()) {
                com.android.internal.content.InstallLocationUtils.getStorageManager().startCheckpoint(2);
            }
            android.util.Slog.d(TAG, "Marking session " + stagedSession.sessionId() + " as ready");
            stagedSession.setSessionReady();
            if (stagedSession.isSessionReady() && stagedSession.containsApexSession()) {
                this.mApexManager.markStagedSessionReady(stagedSession.sessionId());
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Failed to get hold of StorageManager", e);
            throw new com.android.server.pm.PackageManagerException(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, "Failed to get hold of StorageManager");
        }
    }

    private void submitSessionToApexService(com.android.server.pm.StagingManager.StagedSession stagedSession, int i) throws com.android.server.pm.PackageManagerException {
        android.util.IntArray intArray = new android.util.IntArray();
        if (stagedSession.isMultiPackage()) {
            for (com.android.server.pm.StagingManager.StagedSession stagedSession2 : stagedSession.getChildSessions()) {
                if (stagedSession2.isApexSession()) {
                    intArray.add(stagedSession2.sessionId());
                }
            }
        }
        android.apex.ApexSessionParams apexSessionParams = new android.apex.ApexSessionParams();
        apexSessionParams.sessionId = stagedSession.sessionId();
        apexSessionParams.childSessionIds = intArray.toArray();
        if (stagedSession.sessionParams().installReason == 5) {
            apexSessionParams.isRollback = true;
            apexSessionParams.rollbackId = i;
        } else if (i != -1) {
            apexSessionParams.hasRollbackEnabled = true;
            apexSessionParams.rollbackId = i;
        }
        android.apex.ApexInfoList submitStagedSession = this.mApexManager.submitStagedSession(apexSessionParams);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.apex.ApexInfo apexInfo : submitStagedSession.apexInfos) {
            try {
                com.android.internal.pm.parsing.PackageParser2 packageParser2 = this.mPackageParserSupplier.get();
                try {
                    com.android.internal.pm.parsing.pkg.ParsedPackage parsePackage = packageParser2.parsePackage(new java.io.File(apexInfo.modulePath), 0, false);
                    packageParser2.close();
                    arrayList.add(parsePackage.getPackageName());
                } finally {
                }
            } catch (com.android.internal.pm.parsing.PackageParserException e) {
                throw new com.android.server.pm.PackageManagerException(-22, "Failed to parse APEX package " + apexInfo.modulePath + " : " + e, (java.lang.Throwable) e);
            }
        }
        android.util.Slog.d(TAG, "Session " + stagedSession.sessionId() + " has following APEX packages: " + arrayList);
    }

    private int retrieveRollbackIdForCommitSession(int i) throws com.android.server.pm.PackageManagerException {
        java.util.List recentlyCommittedRollbacks = ((android.content.rollback.RollbackManager) this.mContext.getSystemService(android.content.rollback.RollbackManager.class)).getRecentlyCommittedRollbacks();
        int size = recentlyCommittedRollbacks.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.content.rollback.RollbackInfo rollbackInfo = (android.content.rollback.RollbackInfo) recentlyCommittedRollbacks.get(i2);
            if (rollbackInfo.getCommittedSessionId() == i) {
                return rollbackInfo.getRollbackId();
            }
        }
        throw new com.android.server.pm.PackageManagerException(-22, "Could not find rollback id for commit session: " + i);
    }

    private static boolean isRollback(com.android.server.pm.StagingManager.StagedSession stagedSession) {
        return stagedSession.sessionParams().installReason == 5;
    }

    private static boolean isApexSessionFinalized(android.apex.ApexSessionInfo apexSessionInfo) {
        return apexSessionInfo.isUnknown || apexSessionInfo.isActivationFailed || apexSessionInfo.isSuccess || apexSessionInfo.isReverted;
    }

    private boolean ensureActiveApexSessionIsAborted(com.android.server.pm.StagingManager.StagedSession stagedSession) {
        int sessionId;
        android.apex.ApexSessionInfo stagedSessionInfo;
        if (!stagedSession.containsApexSession() || (stagedSessionInfo = this.mApexManager.getStagedSessionInfo((sessionId = stagedSession.sessionId()))) == null || isApexSessionFinalized(stagedSessionInfo)) {
            return true;
        }
        return this.mApexManager.abortStagedSession(sessionId);
    }

    private boolean isApexUpdateAllowed(java.lang.String str, java.lang.String str2) {
        if (this.mPm.getModuleInfo(str, 0) != null) {
            java.lang.String modulesInstallerPackageName = com.android.server.SystemConfig.getInstance().getModulesInstallerPackageName();
            if (modulesInstallerPackageName == null) {
                android.util.Slog.w(TAG, "No modules installer defined");
                return false;
            }
            return modulesInstallerPackageName.equals(str2);
        }
        java.lang.String str3 = com.android.server.SystemConfig.getInstance().getAllowedVendorApexes().get(str);
        if (str3 == null) {
            android.util.Slog.w(TAG, str + " is not allowed to be updated");
            return false;
        }
        return str3.equals(str2);
    }

    private void checkApexUpdateAllowed(com.android.server.pm.PackageInstallerSession packageInstallerSession) throws com.android.server.pm.PackageManagerException {
        if (!packageInstallerSession.isApexSession() || (packageInstallerSession.params.installFlags & 8388608) != 0) {
            return;
        }
        java.lang.String packageName = packageInstallerSession.getPackageName();
        java.lang.String str = packageInstallerSession.getInstallSource().mInstallerPackageName;
        if (!isApexUpdateAllowed(packageName, str)) {
            throw new com.android.server.pm.PackageManagerException(-22, "Update of APEX package " + packageName + " is not allowed for " + str);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void checkRebootlessApex(com.android.server.pm.PackageInstallerSession packageInstallerSession) throws com.android.server.pm.PackageManagerException {
        if (packageInstallerSession.isStaged() || !packageInstallerSession.isApexSession()) {
            return;
        }
        final java.lang.String packageName = packageInstallerSession.getPackageName();
        if (packageName == null) {
            throw new com.android.server.pm.PackageManagerException(-22, "Invalid session " + packageInstallerSession.sessionId + " with package name null");
        }
        for (com.android.server.pm.StagingManager.StagedSession stagedSession : this.mStagedSessions) {
            if (!stagedSession.isDestroyed() && !stagedSession.isInTerminalState() && stagedSession.sessionContains(new java.util.function.Predicate() { // from class: com.android.server.pm.PackageSessionVerifier$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$checkRebootlessApex$4;
                    lambda$checkRebootlessApex$4 = com.android.server.pm.PackageSessionVerifier.lambda$checkRebootlessApex$4(packageName, (com.android.server.pm.StagingManager.StagedSession) obj);
                    return lambda$checkRebootlessApex$4;
                }
            })) {
                throw new com.android.server.pm.PackageManagerException(-22, "Staged session " + stagedSession.sessionId() + " already contains " + packageName);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$checkRebootlessApex$4(java.lang.String str, com.android.server.pm.StagingManager.StagedSession stagedSession) {
        return str.equals(stagedSession.getPackageName());
    }

    private void checkActiveSessions() throws com.android.server.pm.PackageManagerException {
        try {
            checkActiveSessions(com.android.internal.content.InstallLocationUtils.getStorageManager().supportsCheckpoint());
        } catch (android.os.RemoteException e) {
            throw new com.android.server.pm.PackageManagerException(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, "Can't query fs-checkpoint status : " + e);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void checkActiveSessions(boolean z) throws com.android.server.pm.PackageManagerException {
        int i = 0;
        for (com.android.server.pm.StagingManager.StagedSession stagedSession : this.mStagedSessions) {
            if (!stagedSession.isDestroyed() && !stagedSession.isInTerminalState()) {
                i++;
            }
        }
        if (!z && i > 1) {
            throw new com.android.server.pm.PackageManagerException(-119, "Cannot stage multiple sessions without checkpoint support");
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void checkRollbacks(com.android.server.pm.StagingManager.StagedSession stagedSession) throws com.android.server.pm.PackageManagerException {
        if (stagedSession.isDestroyed() || stagedSession.isInTerminalState()) {
            return;
        }
        for (com.android.server.pm.StagingManager.StagedSession stagedSession2 : this.mStagedSessions) {
            if (!stagedSession2.isDestroyed() && !stagedSession2.isInTerminalState()) {
                if (isRollback(stagedSession) && !isRollback(stagedSession2)) {
                    if (!ensureActiveApexSessionIsAborted(stagedSession2)) {
                        android.util.Slog.e(TAG, "Failed to abort apex session " + stagedSession2.sessionId());
                    }
                    stagedSession2.setSessionFailed(-119, "Session was failed by rollback session: " + stagedSession.sessionId());
                    android.util.Slog.i(TAG, "Session " + stagedSession2.sessionId() + " is marked failed due to rollback session: " + stagedSession.sessionId());
                } else if (!isRollback(stagedSession) && isRollback(stagedSession2)) {
                    throw new com.android.server.pm.PackageManagerException(-119, "Session was failed by rollback session: " + stagedSession2.sessionId());
                }
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void checkOverlaps(com.android.server.pm.StagingManager.StagedSession stagedSession, com.android.server.pm.StagingManager.StagedSession stagedSession2) throws com.android.server.pm.PackageManagerException {
        if (stagedSession.isDestroyed() || stagedSession.isInTerminalState()) {
            return;
        }
        final java.lang.String packageName = stagedSession2.getPackageName();
        if (packageName == null) {
            throw new com.android.server.pm.PackageManagerException(-22, "Cannot stage session " + stagedSession2.sessionId() + " with package name null");
        }
        for (com.android.server.pm.StagingManager.StagedSession stagedSession3 : this.mStagedSessions) {
            if (!stagedSession3.isDestroyed() && !stagedSession3.isInTerminalState() && stagedSession3.sessionId() != stagedSession.sessionId() && stagedSession3.sessionContains(new java.util.function.Predicate() { // from class: com.android.server.pm.PackageSessionVerifier$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$checkOverlaps$5;
                    lambda$checkOverlaps$5 = com.android.server.pm.PackageSessionVerifier.lambda$checkOverlaps$5(packageName, (com.android.server.pm.StagingManager.StagedSession) obj);
                    return lambda$checkOverlaps$5;
                }
            })) {
                if (stagedSession3.getCommittedMillis() < stagedSession.getCommittedMillis()) {
                    throw new com.android.server.pm.PackageManagerException(-119, "Package: " + packageName + " in session: " + stagedSession2.sessionId() + " has been staged already by session: " + stagedSession3.sessionId());
                }
                stagedSession3.setSessionFailed(-119, "Package: " + packageName + " in session: " + stagedSession3.sessionId() + " has been staged already by session: " + stagedSession2.sessionId());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$checkOverlaps$5(java.lang.String str, com.android.server.pm.StagingManager.StagedSession stagedSession) {
        return str.equals(stagedSession.getPackageName());
    }
}
