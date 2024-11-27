package com.android.server.pm;

/* loaded from: classes2.dex */
public class StagingManager {
    private static final java.lang.String TAG = "StagingManager";
    private final com.android.server.pm.ApexManager mApexManager;
    private final java.util.concurrent.CompletableFuture<java.lang.Void> mBootCompleted;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mFailedPackageNames"})
    private final java.util.List<java.lang.String> mFailedPackageNames;
    private java.lang.String mFailureReason;
    private final java.io.File mFailureReasonFile;
    private java.lang.String mNativeFailureReason;
    private final android.os.PowerManager mPowerManager;

    @com.android.internal.annotations.GuardedBy({"mStagedApexObservers"})
    private final java.util.List<android.content.pm.IStagedApexObserver> mStagedApexObservers;

    @com.android.internal.annotations.GuardedBy({"mStagedSessions"})
    private final android.util.SparseArray<com.android.server.pm.StagingManager.StagedSession> mStagedSessions;

    @com.android.internal.annotations.GuardedBy({"mSuccessfulStagedSessionIds"})
    private final java.util.List<java.lang.Integer> mSuccessfulStagedSessionIds;

    interface StagedSession {
        void abandon();

        boolean containsApexSession();

        boolean containsApkSession();

        java.util.List<com.android.server.pm.StagingManager.StagedSession> getChildSessions();

        long getCommittedMillis();

        java.lang.String getPackageName();

        int getParentSessionId();

        boolean hasParentSessionId();

        java.util.concurrent.CompletableFuture<java.lang.Void> installSession();

        boolean isApexSession();

        boolean isCommitted();

        boolean isDestroyed();

        boolean isInTerminalState();

        boolean isMultiPackage();

        boolean isSessionApplied();

        boolean isSessionFailed();

        boolean isSessionReady();

        boolean sessionContains(java.util.function.Predicate<com.android.server.pm.StagingManager.StagedSession> predicate);

        int sessionId();

        android.content.pm.PackageInstaller.SessionParams sessionParams();

        void setSessionApplied();

        void setSessionFailed(int i, java.lang.String str);

        void setSessionReady();

        void verifySession();
    }

    StagingManager(android.content.Context context) {
        this(context, com.android.server.pm.ApexManager.getInstance());
    }

    @com.android.internal.annotations.VisibleForTesting
    StagingManager(android.content.Context context, com.android.server.pm.ApexManager apexManager) {
        this.mFailureReasonFile = new java.io.File("/metadata/staged-install/failure_reason.txt");
        this.mStagedSessions = new android.util.SparseArray<>();
        this.mFailedPackageNames = new java.util.ArrayList();
        this.mSuccessfulStagedSessionIds = new java.util.ArrayList();
        this.mStagedApexObservers = new java.util.ArrayList();
        this.mBootCompleted = new java.util.concurrent.CompletableFuture<>();
        this.mContext = context;
        this.mApexManager = apexManager;
        this.mPowerManager = (android.os.PowerManager) context.getSystemService("power");
        if (this.mFailureReasonFile.exists()) {
            try {
                java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.FileReader(this.mFailureReasonFile));
                try {
                    this.mFailureReason = bufferedReader.readLine();
                    bufferedReader.close();
                } finally {
                }
            } catch (java.lang.Exception e) {
            }
        }
    }

    public static final class Lifecycle extends com.android.server.SystemService {
        private static com.android.server.pm.StagingManager sStagingManager;

        public Lifecycle(android.content.Context context) {
            super(context);
        }

        void startService(com.android.server.pm.StagingManager stagingManager) {
            sStagingManager = stagingManager;
            ((com.android.server.SystemServiceManager) com.android.server.LocalServices.getService(com.android.server.SystemServiceManager.class)).startService(this);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            if (i == 1000 && sStagingManager != null) {
                sStagingManager.markStagedSessionsAsSuccessful();
                sStagingManager.markBootCompleted();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void markBootCompleted() {
        this.mApexManager.markBootCompleted();
    }

    void registerStagedApexObserver(final android.content.pm.IStagedApexObserver iStagedApexObserver) {
        if (iStagedApexObserver == null) {
            return;
        }
        if (iStagedApexObserver.asBinder() != null) {
            try {
                iStagedApexObserver.asBinder().linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.android.server.pm.StagingManager.1
                    @Override // android.os.IBinder.DeathRecipient
                    public void binderDied() {
                        synchronized (com.android.server.pm.StagingManager.this.mStagedApexObservers) {
                            com.android.server.pm.StagingManager.this.mStagedApexObservers.remove(iStagedApexObserver);
                        }
                    }
                }, 0);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(TAG, e.getMessage());
            }
        }
        synchronized (this.mStagedApexObservers) {
            this.mStagedApexObservers.add(iStagedApexObserver);
        }
    }

    void unregisterStagedApexObserver(android.content.pm.IStagedApexObserver iStagedApexObserver) {
        synchronized (this.mStagedApexObservers) {
            this.mStagedApexObservers.remove(iStagedApexObserver);
        }
    }

    private void abortCheckpoint(java.lang.String str, boolean z, boolean z2) {
        android.util.Slog.e(TAG, str);
        if (z) {
            try {
                if (z2) {
                    try {
                        java.io.BufferedWriter bufferedWriter = new java.io.BufferedWriter(new java.io.FileWriter(this.mFailureReasonFile));
                        try {
                            bufferedWriter.write(str);
                            bufferedWriter.close();
                        } finally {
                        }
                    } catch (java.lang.Exception e) {
                        android.util.Slog.w(TAG, "Failed to save failure reason: ", e);
                    }
                    if (this.mApexManager.isApexSupported()) {
                        this.mApexManager.revertActiveSessions();
                    }
                    com.android.internal.content.InstallLocationUtils.getStorageManager().abortChanges("abort-staged-install", false);
                }
            } catch (java.lang.Exception e2) {
                android.util.Slog.wtf(TAG, "Failed to abort checkpoint", e2);
                if (this.mApexManager.isApexSupported()) {
                    this.mApexManager.revertActiveSessions();
                }
                this.mPowerManager.reboot(null);
            }
        }
    }

    private java.util.List<com.android.server.pm.StagingManager.StagedSession> extractApexSessions(com.android.server.pm.StagingManager.StagedSession stagedSession) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (stagedSession.isMultiPackage()) {
            for (com.android.server.pm.StagingManager.StagedSession stagedSession2 : stagedSession.getChildSessions()) {
                if (stagedSession2.containsApexSession()) {
                    arrayList.add(stagedSession2);
                }
            }
        } else {
            arrayList.add(stagedSession);
        }
        return arrayList;
    }

    private void checkInstallationOfApkInApexSuccessful(com.android.server.pm.StagingManager.StagedSession stagedSession) throws com.android.server.pm.PackageManagerException {
        java.util.List<com.android.server.pm.StagingManager.StagedSession> extractApexSessions = extractApexSessions(stagedSession);
        if (extractApexSessions.isEmpty()) {
            return;
        }
        java.util.Iterator<com.android.server.pm.StagingManager.StagedSession> it = extractApexSessions.iterator();
        while (it.hasNext()) {
            java.lang.String packageName = it.next().getPackageName();
            java.lang.String apkInApexInstallError = this.mApexManager.getApkInApexInstallError(packageName);
            if (apkInApexInstallError != null) {
                throw new com.android.server.pm.PackageManagerException(com.android.server.usb.descriptors.UsbEndpointDescriptor.MASK_ENDPOINT_DIRECTION, "Failed to install apk-in-apex of " + packageName + " : " + apkInApexInstallError);
            }
        }
    }

    private void snapshotAndRestoreForApexSession(com.android.server.pm.StagingManager.StagedSession stagedSession) {
        if (!((stagedSession.sessionParams().installFlags & 262144) != 0 || stagedSession.sessionParams().installReason == 5)) {
            return;
        }
        java.util.List<com.android.server.pm.StagingManager.StagedSession> extractApexSessions = extractApexSessions(stagedSession);
        if (extractApexSessions.isEmpty()) {
            return;
        }
        int[] userIds = ((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)).getUserIds();
        com.android.server.rollback.RollbackManagerInternal rollbackManagerInternal = (com.android.server.rollback.RollbackManagerInternal) com.android.server.LocalServices.getService(com.android.server.rollback.RollbackManagerInternal.class);
        int size = extractApexSessions.size();
        for (int i = 0; i < size; i++) {
            java.lang.String packageName = extractApexSessions.get(i).getPackageName();
            snapshotAndRestoreApexUserData(packageName, userIds, rollbackManagerInternal);
            java.util.List<java.lang.String> apksInApex = this.mApexManager.getApksInApex(packageName);
            int size2 = apksInApex.size();
            for (int i2 = 0; i2 < size2; i2++) {
                snapshotAndRestoreApkInApexUserData(apksInApex.get(i2), userIds, rollbackManagerInternal);
            }
        }
    }

    private void snapshotAndRestoreApexUserData(java.lang.String str, int[] iArr, com.android.server.rollback.RollbackManagerInternal rollbackManagerInternal) {
        rollbackManagerInternal.snapshotAndRestoreUserData(str, android.os.UserHandle.toUserHandles(iArr), 0, 0L, null, 0);
    }

    private void snapshotAndRestoreApkInApexUserData(java.lang.String str, int[] iArr, com.android.server.rollback.RollbackManagerInternal rollbackManagerInternal) {
        android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        if (packageManagerInternal.getPackage(str) == null) {
            android.util.Slog.e(TAG, "Could not find package: " + str + "for snapshotting/restoring user data.");
            return;
        }
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = packageManagerInternal.getPackageStateInternal(str);
        if (packageStateInternal != null) {
            rollbackManagerInternal.snapshotAndRestoreUserData(str, android.os.UserHandle.toUserHandles(com.android.server.pm.pkg.PackageStateUtils.queryInstalledUsers(packageStateInternal, iArr, true)), packageStateInternal.getAppId(), packageStateInternal.getUserStateOrDefault(0).getCeDataInode(), packageStateInternal.getSeInfo(), 0);
        }
    }

    private void prepareForLoggingApexdRevert(@android.annotation.NonNull com.android.server.pm.StagingManager.StagedSession stagedSession, @android.annotation.NonNull java.lang.String str) {
        synchronized (this.mFailedPackageNames) {
            try {
                this.mNativeFailureReason = str;
                if (stagedSession.getPackageName() != null) {
                    this.mFailedPackageNames.add(stagedSession.getPackageName());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void resumeSession(@android.annotation.NonNull com.android.server.pm.StagingManager.StagedSession stagedSession, boolean z, boolean z2) throws com.android.server.pm.PackageManagerException {
        android.util.Slog.d(TAG, "Resuming session " + stagedSession.sessionId());
        boolean containsApexSession = stagedSession.containsApexSession();
        if (z && !z2) {
            java.lang.String str = "Reverting back to safe state. Marking " + stagedSession.sessionId() + " as failed.";
            java.lang.String reasonForRevert = getReasonForRevert();
            if (!android.text.TextUtils.isEmpty(reasonForRevert)) {
                str = str + " Reason for revert: " + reasonForRevert;
            }
            android.util.Slog.d(TAG, str);
            stagedSession.setSessionFailed(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, str);
            return;
        }
        if (containsApexSession) {
            checkInstallationOfApkInApexSuccessful(stagedSession);
            checkDuplicateApkInApex(stagedSession);
            snapshotAndRestoreForApexSession(stagedSession);
            android.util.Slog.i(TAG, "APEX packages in session " + stagedSession.sessionId() + " were successfully activated. Proceeding with APK packages, if any");
        }
        android.util.Slog.d(TAG, "Installing APK packages in session " + stagedSession.sessionId());
        android.util.TimingsTraceLog timingsTraceLog = new android.util.TimingsTraceLog("StagingManagerTiming", 262144L);
        timingsTraceLog.traceBegin("installApksInSession");
        installApksInSession(stagedSession);
        timingsTraceLog.traceEnd();
        if (containsApexSession) {
            if (z) {
                synchronized (this.mSuccessfulStagedSessionIds) {
                    this.mSuccessfulStagedSessionIds.add(java.lang.Integer.valueOf(stagedSession.sessionId()));
                }
                return;
            }
            this.mApexManager.markStagedSessionSuccessful(stagedSession.sessionId());
        }
    }

    void onInstallationFailure(com.android.server.pm.StagingManager.StagedSession stagedSession, com.android.server.pm.PackageManagerException packageManagerException, boolean z, boolean z2) {
        stagedSession.setSessionFailed(packageManagerException.error, packageManagerException.getMessage());
        abortCheckpoint("Failed to install sessionId: " + stagedSession.sessionId() + " Error: " + packageManagerException.getMessage(), z, z2);
        if (!stagedSession.containsApexSession()) {
            return;
        }
        if (!this.mApexManager.revertActiveSessions()) {
            android.util.Slog.e(TAG, "Failed to abort APEXd session");
        } else {
            android.util.Slog.e(TAG, "Successfully aborted apexd session. Rebooting device in order to revert to the previous state of APEXd.");
            this.mPowerManager.reboot(null);
        }
    }

    private java.lang.String getReasonForRevert() {
        if (!android.text.TextUtils.isEmpty(this.mFailureReason)) {
            return this.mFailureReason;
        }
        if (!android.text.TextUtils.isEmpty(this.mNativeFailureReason)) {
            return "Session reverted due to crashing native process: " + this.mNativeFailureReason;
        }
        return "";
    }

    private void checkDuplicateApkInApex(@android.annotation.NonNull com.android.server.pm.StagingManager.StagedSession stagedSession) throws com.android.server.pm.PackageManagerException {
        if (!stagedSession.isMultiPackage()) {
            return;
        }
        android.util.ArraySet arraySet = new android.util.ArraySet();
        for (com.android.server.pm.StagingManager.StagedSession stagedSession2 : stagedSession.getChildSessions()) {
            if (!stagedSession2.isApexSession()) {
                arraySet.add(stagedSession2.getPackageName());
            }
        }
        for (com.android.server.pm.StagingManager.StagedSession stagedSession3 : extractApexSessions(stagedSession)) {
            java.lang.String packageName = stagedSession3.getPackageName();
            for (java.lang.String str : this.mApexManager.getApksInApex(packageName)) {
                if (!arraySet.add(str)) {
                    throw new com.android.server.pm.PackageManagerException(com.android.server.usb.descriptors.UsbEndpointDescriptor.MASK_ENDPOINT_DIRECTION, "Package: " + packageName + " in session: " + stagedSession3.sessionId() + " has duplicate apk-in-apex: " + str, (java.lang.Throwable) null);
                }
            }
        }
    }

    private void installApksInSession(com.android.server.pm.StagingManager.StagedSession stagedSession) throws com.android.server.pm.PackageManagerException {
        try {
            stagedSession.installSession().get();
        } catch (java.lang.InterruptedException e) {
            throw new java.lang.RuntimeException(e);
        } catch (java.util.concurrent.ExecutionException e2) {
            throw ((com.android.server.pm.PackageManagerException) e2.getCause());
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void commitSession(@android.annotation.NonNull com.android.server.pm.StagingManager.StagedSession stagedSession) {
        createSession(stagedSession);
        handleCommittedSession(stagedSession);
    }

    private void handleCommittedSession(@android.annotation.NonNull com.android.server.pm.StagingManager.StagedSession stagedSession) {
        if (stagedSession.isSessionReady() && stagedSession.containsApexSession()) {
            notifyStagedApexObservers();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void createSession(@android.annotation.NonNull com.android.server.pm.StagingManager.StagedSession stagedSession) {
        synchronized (this.mStagedSessions) {
            this.mStagedSessions.append(stagedSession.sessionId(), stagedSession);
        }
    }

    void abortSession(@android.annotation.NonNull com.android.server.pm.StagingManager.StagedSession stagedSession) {
        synchronized (this.mStagedSessions) {
            this.mStagedSessions.remove(stagedSession.sessionId());
        }
    }

    void abortCommittedSession(@android.annotation.NonNull com.android.server.pm.StagingManager.StagedSession stagedSession) {
        int sessionId = stagedSession.sessionId();
        if (stagedSession.isInTerminalState()) {
            android.util.Slog.w(TAG, "Cannot abort session in final state: " + sessionId);
            return;
        }
        if (!stagedSession.isDestroyed()) {
            throw new java.lang.IllegalStateException("Committed session must be destroyed before aborting it from StagingManager");
        }
        if (getStagedSession(sessionId) == null) {
            android.util.Slog.w(TAG, "Session " + sessionId + " has been abandoned already");
            return;
        }
        if (stagedSession.isSessionReady()) {
            if (!ensureActiveApexSessionIsAborted(stagedSession)) {
                android.util.Slog.e(TAG, "Failed to abort apex session " + stagedSession.sessionId());
            }
            if (stagedSession.containsApexSession()) {
                notifyStagedApexObservers();
            }
        }
        abortSession(stagedSession);
    }

    private boolean ensureActiveApexSessionIsAborted(com.android.server.pm.StagingManager.StagedSession stagedSession) {
        android.apex.ApexSessionInfo stagedSessionInfo;
        if (!stagedSession.containsApexSession() || (stagedSessionInfo = this.mApexManager.getStagedSessionInfo(stagedSession.sessionId())) == null || isApexSessionFinalized(stagedSessionInfo)) {
            return true;
        }
        return this.mApexManager.abortStagedSession(stagedSession.sessionId());
    }

    private boolean isApexSessionFinalized(android.apex.ApexSessionInfo apexSessionInfo) {
        return apexSessionInfo.isUnknown || apexSessionInfo.isActivationFailed || apexSessionInfo.isSuccess || apexSessionInfo.isReverted;
    }

    private static boolean isApexSessionFailed(android.apex.ApexSessionInfo apexSessionInfo) {
        return apexSessionInfo.isActivationFailed || apexSessionInfo.isUnknown || apexSessionInfo.isReverted || apexSessionInfo.isRevertInProgress || apexSessionInfo.isRevertFailed;
    }

    private void handleNonReadyAndDestroyedSessions(java.util.List<com.android.server.pm.StagingManager.StagedSession> list) {
        int size = list.size();
        int i = 0;
        while (i < size) {
            final com.android.server.pm.StagingManager.StagedSession stagedSession = list.get(i);
            if (stagedSession.isDestroyed()) {
                stagedSession.abandon();
                list.set(i, list.set(size - 1, stagedSession));
                size--;
            } else if (!stagedSession.isSessionReady()) {
                android.util.Slog.i(TAG, "Restart verification for session=" + stagedSession.sessionId());
                this.mBootCompleted.thenRun(new java.lang.Runnable() { // from class: com.android.server.pm.StagingManager$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.pm.StagingManager.StagedSession.this.verifySession();
                    }
                });
                list.set(i, list.set(size + (-1), stagedSession));
                size += -1;
            } else {
                i++;
            }
        }
        list.subList(size, list.size()).clear();
    }

    void restoreSessions(@android.annotation.NonNull java.util.List<com.android.server.pm.StagingManager.StagedSession> list, boolean z) {
        android.util.TimingsTraceLog timingsTraceLog = new android.util.TimingsTraceLog("StagingManagerTiming", 262144L);
        timingsTraceLog.traceBegin("restoreSessions");
        if (android.os.SystemProperties.getBoolean("sys.boot_completed", false)) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            com.android.server.pm.StagingManager.StagedSession stagedSession = list.get(i);
            com.android.internal.util.Preconditions.checkArgument(!stagedSession.hasParentSessionId(), stagedSession.sessionId() + " is a child session");
            com.android.internal.util.Preconditions.checkArgument(stagedSession.isCommitted(), stagedSession.sessionId() + " is not committed");
            com.android.internal.util.Preconditions.checkArgument(true ^ stagedSession.isInTerminalState(), stagedSession.sessionId() + " is in terminal state");
            createSession(stagedSession);
        }
        if (z) {
            for (int i2 = 0; i2 < list.size(); i2++) {
                list.get(i2).setSessionFailed(com.android.server.usb.descriptors.UsbEndpointDescriptor.MASK_ENDPOINT_DIRECTION, "Build fingerprint has changed");
            }
            return;
        }
        try {
            boolean supportsCheckpoint = com.android.internal.content.InstallLocationUtils.getStorageManager().supportsCheckpoint();
            boolean needsCheckpoint = com.android.internal.content.InstallLocationUtils.getStorageManager().needsCheckpoint();
            if (list.size() > 1 && !supportsCheckpoint) {
                throw new java.lang.IllegalStateException("Detected multiple staged sessions on a device without fs-checkpoint support");
            }
            handleNonReadyAndDestroyedSessions(list);
            android.util.SparseArray<android.apex.ApexSessionInfo> sessions = this.mApexManager.getSessions();
            boolean z2 = false;
            boolean z3 = false;
            for (int i3 = 0; i3 < list.size(); i3++) {
                com.android.server.pm.StagingManager.StagedSession stagedSession2 = list.get(i3);
                if (stagedSession2.containsApexSession()) {
                    android.apex.ApexSessionInfo apexSessionInfo = sessions.get(stagedSession2.sessionId());
                    if (apexSessionInfo == null || apexSessionInfo.isUnknown) {
                        stagedSession2.setSessionFailed(com.android.server.usb.descriptors.UsbEndpointDescriptor.MASK_ENDPOINT_DIRECTION, "apexd did not know anything about a staged session supposed to be activated");
                        z3 = true;
                    } else if (isApexSessionFailed(apexSessionInfo)) {
                        if (!android.text.TextUtils.isEmpty(apexSessionInfo.crashingNativeProcess)) {
                            prepareForLoggingApexdRevert(stagedSession2, apexSessionInfo.crashingNativeProcess);
                        }
                        java.lang.String reasonForRevert = getReasonForRevert();
                        java.lang.String str = "APEX activation failed.";
                        if (!android.text.TextUtils.isEmpty(reasonForRevert)) {
                            str = "APEX activation failed. Reason: " + reasonForRevert;
                        } else if (!android.text.TextUtils.isEmpty(apexSessionInfo.errorMessage)) {
                            str = "APEX activation failed. Error: " + apexSessionInfo.errorMessage;
                        }
                        android.util.Slog.d(TAG, str);
                        stagedSession2.setSessionFailed(com.android.server.usb.descriptors.UsbEndpointDescriptor.MASK_ENDPOINT_DIRECTION, str);
                        z3 = true;
                    } else if (apexSessionInfo.isActivated || apexSessionInfo.isSuccess) {
                        z2 = true;
                    } else if (!apexSessionInfo.isStaged) {
                        android.util.Slog.w(TAG, "Apex session " + stagedSession2.sessionId() + " is in impossible state");
                        stagedSession2.setSessionFailed(com.android.server.usb.descriptors.UsbEndpointDescriptor.MASK_ENDPOINT_DIRECTION, "Impossible state");
                        z3 = true;
                    } else {
                        stagedSession2.setSessionFailed(com.android.server.usb.descriptors.UsbEndpointDescriptor.MASK_ENDPOINT_DIRECTION, "Staged session " + stagedSession2.sessionId() + " at boot didn't activate nor fail. Marking it as failed anyway.");
                        z3 = true;
                    }
                }
            }
            if (z2 && z3) {
                abortCheckpoint("Found both applied and failed apex sessions", supportsCheckpoint, needsCheckpoint);
                return;
            }
            if (z3) {
                for (int i4 = 0; i4 < list.size(); i4++) {
                    com.android.server.pm.StagingManager.StagedSession stagedSession3 = list.get(i4);
                    if (!stagedSession3.isSessionFailed()) {
                        stagedSession3.setSessionFailed(com.android.server.usb.descriptors.UsbEndpointDescriptor.MASK_ENDPOINT_DIRECTION, "Another apex session failed");
                    }
                }
                return;
            }
            for (int i5 = 0; i5 < list.size(); i5++) {
                com.android.server.pm.StagingManager.StagedSession stagedSession4 = list.get(i5);
                try {
                    resumeSession(stagedSession4, supportsCheckpoint, needsCheckpoint);
                } catch (com.android.server.pm.PackageManagerException e) {
                    onInstallationFailure(stagedSession4, e, supportsCheckpoint, needsCheckpoint);
                } catch (java.lang.Exception e2) {
                    android.util.Slog.e(TAG, "Staged install failed due to unhandled exception", e2);
                    onInstallationFailure(stagedSession4, new com.android.server.pm.PackageManagerException(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, "Staged install failed due to unhandled exception: " + e2), supportsCheckpoint, needsCheckpoint);
                }
            }
            timingsTraceLog.traceEnd();
        } catch (android.os.RemoteException e3) {
            throw new java.lang.IllegalStateException("Failed to get checkpoint status", e3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: logFailedApexSessionsIfNecessary, reason: merged with bridge method [inline-methods] */
    public void lambda$onBootCompletedBroadcastReceived$1() {
        synchronized (this.mFailedPackageNames) {
            try {
                if (!this.mFailedPackageNames.isEmpty()) {
                    com.android.server.rollback.WatchdogRollbackLogger.logApexdRevert(this.mContext, this.mFailedPackageNames, this.mNativeFailureReason);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void markStagedSessionsAsSuccessful() {
        synchronized (this.mSuccessfulStagedSessionIds) {
            for (int i = 0; i < this.mSuccessfulStagedSessionIds.size(); i++) {
                try {
                    this.mApexManager.markStagedSessionSuccessful(this.mSuccessfulStagedSessionIds.get(i).intValue());
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
    }

    void systemReady() {
        new com.android.server.pm.StagingManager.Lifecycle(this.mContext).startService(this);
        this.mContext.registerReceiver(new android.content.BroadcastReceiver() { // from class: com.android.server.pm.StagingManager.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                com.android.server.pm.StagingManager.this.onBootCompletedBroadcastReceived();
                context.unregisterReceiver(this);
            }
        }, new android.content.IntentFilter("android.intent.action.BOOT_COMPLETED"));
        this.mFailureReasonFile.delete();
    }

    @com.android.internal.annotations.VisibleForTesting
    void onBootCompletedBroadcastReceived() {
        this.mBootCompleted.complete(null);
        com.android.internal.os.BackgroundThread.getExecutor().execute(new java.lang.Runnable() { // from class: com.android.server.pm.StagingManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.StagingManager.this.lambda$onBootCompletedBroadcastReceived$1();
            }
        });
    }

    private com.android.server.pm.StagingManager.StagedSession getStagedSession(int i) {
        com.android.server.pm.StagingManager.StagedSession stagedSession;
        synchronized (this.mStagedSessions) {
            stagedSession = this.mStagedSessions.get(i);
        }
        return stagedSession;
    }

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting
    java.util.Map<java.lang.String, android.apex.ApexInfo> getStagedApexInfos(@android.annotation.NonNull com.android.server.pm.StagingManager.StagedSession stagedSession) {
        com.android.internal.util.Preconditions.checkArgument(stagedSession != null, "Session is null");
        com.android.internal.util.Preconditions.checkArgument(true ^ stagedSession.hasParentSessionId(), stagedSession.sessionId() + " session has parent session");
        com.android.internal.util.Preconditions.checkArgument(stagedSession.containsApexSession(), stagedSession.sessionId() + " session does not contain apex");
        if (!stagedSession.isSessionReady() || stagedSession.isDestroyed()) {
            return java.util.Collections.emptyMap();
        }
        android.apex.ApexSessionParams apexSessionParams = new android.apex.ApexSessionParams();
        apexSessionParams.sessionId = stagedSession.sessionId();
        android.util.IntArray intArray = new android.util.IntArray();
        if (stagedSession.isMultiPackage()) {
            for (com.android.server.pm.StagingManager.StagedSession stagedSession2 : stagedSession.getChildSessions()) {
                if (stagedSession2.isApexSession()) {
                    intArray.add(stagedSession2.sessionId());
                }
            }
        }
        apexSessionParams.childSessionIds = intArray.toArray();
        android.apex.ApexInfo[] stagedApexInfos = this.mApexManager.getStagedApexInfos(apexSessionParams);
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        for (android.apex.ApexInfo apexInfo : stagedApexInfos) {
            arrayMap.put(apexInfo.moduleName, apexInfo);
        }
        return arrayMap;
    }

    java.util.List<java.lang.String> getStagedApexModuleNames() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        synchronized (this.mStagedSessions) {
            for (int i = 0; i < this.mStagedSessions.size(); i++) {
                try {
                    com.android.server.pm.StagingManager.StagedSession valueAt = this.mStagedSessions.valueAt(i);
                    if (valueAt.isSessionReady() && !valueAt.isDestroyed() && !valueAt.hasParentSessionId() && valueAt.containsApexSession()) {
                        arrayList.addAll(getStagedApexInfos(valueAt).keySet());
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        return arrayList;
    }

    @android.annotation.Nullable
    android.content.pm.StagedApexInfo getStagedApexInfo(java.lang.String str) {
        android.apex.ApexInfo apexInfo;
        synchronized (this.mStagedSessions) {
            for (int i = 0; i < this.mStagedSessions.size(); i++) {
                try {
                    com.android.server.pm.StagingManager.StagedSession valueAt = this.mStagedSessions.valueAt(i);
                    if (valueAt.isSessionReady() && !valueAt.isDestroyed() && !valueAt.hasParentSessionId() && valueAt.containsApexSession() && (apexInfo = getStagedApexInfos(valueAt).get(str)) != null) {
                        android.content.pm.StagedApexInfo stagedApexInfo = new android.content.pm.StagedApexInfo();
                        stagedApexInfo.moduleName = apexInfo.moduleName;
                        stagedApexInfo.diskImagePath = apexInfo.modulePath;
                        stagedApexInfo.versionCode = apexInfo.versionCode;
                        stagedApexInfo.versionName = apexInfo.versionName;
                        stagedApexInfo.hasClassPathJars = apexInfo.hasClassPathJars;
                        return stagedApexInfo;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return null;
        }
    }

    private void notifyStagedApexObservers() {
        synchronized (this.mStagedApexObservers) {
            for (android.content.pm.IStagedApexObserver iStagedApexObserver : this.mStagedApexObservers) {
                android.content.pm.ApexStagedEvent apexStagedEvent = new android.content.pm.ApexStagedEvent();
                apexStagedEvent.stagedApexModuleNames = (java.lang.String[]) getStagedApexModuleNames().toArray(new java.lang.String[0]);
                try {
                    iStagedApexObserver.onApexStaged(apexStagedEvent);
                } catch (android.os.RemoteException e) {
                    android.util.Slog.w(TAG, "Failed to contact the observer " + e.getMessage());
                }
            }
        }
    }
}
