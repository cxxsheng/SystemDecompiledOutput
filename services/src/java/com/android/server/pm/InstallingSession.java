package com.android.server.pm;

/* loaded from: classes2.dex */
class InstallingSession {
    final java.util.List<java.lang.String> mAllowlistedRestrictedPermissions;
    final boolean mApplicationEnabledSettingPersistent;
    final int mAutoRevokePermissionsMode;
    final int mDataLoaderType;
    final int mDevelopmentInstallFlags;
    final boolean mForceQueryableOverride;
    int mInstallFlags;
    final int mInstallReason;
    final int mInstallScenario;

    @android.annotation.NonNull
    final com.android.server.pm.InstallSource mInstallSource;
    final boolean mIsInherit;
    final com.android.server.pm.MoveInfo mMoveInfo;
    final android.content.pm.IPackageInstallObserver2 mObserver;
    final com.android.server.pm.OriginInfo mOriginInfo;
    final java.lang.String mPackageAbiOverride;
    final android.content.pm.parsing.PackageLite mPackageLite;
    final int mPackageSource;

    @android.annotation.Nullable
    com.android.server.pm.InstallingSession.MultiPackageInstallingSession mParentInstallingSession;

    @android.annotation.NonNull
    final android.util.ArrayMap<java.lang.String, java.lang.Integer> mPermissionStates;

    @android.annotation.NonNull
    final com.android.server.pm.PackageManagerService mPm;

    @android.annotation.Nullable
    final android.content.pm.verify.domain.DomainSet mPreVerifiedDomains;
    final int mRequireUserAction;
    final long mRequiredInstalledVersionCode;
    int mRet;
    final int mSessionId;
    final android.content.pm.SigningDetails mSigningDetails;
    int mTraceCookie;
    java.lang.String mTraceMethod;
    private final android.os.UserHandle mUser;
    final java.lang.String mVolumeUuid;

    InstallingSession(com.android.server.pm.OriginInfo originInfo, com.android.server.pm.MoveInfo moveInfo, android.content.pm.IPackageInstallObserver2 iPackageInstallObserver2, int i, int i2, com.android.server.pm.InstallSource installSource, java.lang.String str, android.os.UserHandle userHandle, java.lang.String str2, int i3, android.content.pm.parsing.PackageLite packageLite, com.android.server.pm.PackageManagerService packageManagerService) {
        this.mPm = packageManagerService;
        this.mUser = userHandle;
        this.mOriginInfo = originInfo;
        this.mMoveInfo = moveInfo;
        this.mObserver = iPackageInstallObserver2;
        this.mInstallFlags = i;
        this.mDevelopmentInstallFlags = i2;
        this.mInstallSource = (com.android.server.pm.InstallSource) com.android.internal.util.Preconditions.checkNotNull(installSource);
        this.mVolumeUuid = str;
        this.mPackageAbiOverride = str2;
        this.mPermissionStates = new android.util.ArrayMap<>();
        this.mAllowlistedRestrictedPermissions = null;
        this.mAutoRevokePermissionsMode = 3;
        this.mSigningDetails = android.content.pm.SigningDetails.UNKNOWN;
        this.mInstallReason = 0;
        this.mInstallScenario = 0;
        this.mForceQueryableOverride = false;
        this.mDataLoaderType = 0;
        this.mRequiredInstalledVersionCode = -1L;
        this.mPackageSource = i3;
        this.mPackageLite = packageLite;
        this.mIsInherit = false;
        this.mSessionId = -1;
        this.mRequireUserAction = 0;
        this.mApplicationEnabledSettingPersistent = false;
        this.mPreVerifiedDomains = null;
    }

    InstallingSession(int i, java.io.File file, android.content.pm.IPackageInstallObserver2 iPackageInstallObserver2, android.content.pm.PackageInstaller.SessionParams sessionParams, com.android.server.pm.InstallSource installSource, android.os.UserHandle userHandle, android.content.pm.SigningDetails signingDetails, int i2, android.content.pm.parsing.PackageLite packageLite, android.content.pm.verify.domain.DomainSet domainSet, com.android.server.pm.PackageManagerService packageManagerService) {
        this.mPm = packageManagerService;
        this.mUser = userHandle;
        this.mOriginInfo = com.android.server.pm.OriginInfo.fromStagedFile(file);
        this.mMoveInfo = null;
        this.mInstallReason = fixUpInstallReason(installSource.mInstallerPackageName, i2, sessionParams.installReason);
        this.mInstallScenario = sessionParams.installScenario;
        this.mObserver = iPackageInstallObserver2;
        this.mInstallFlags = sessionParams.installFlags;
        this.mDevelopmentInstallFlags = sessionParams.developmentInstallFlags;
        this.mInstallSource = installSource;
        this.mVolumeUuid = sessionParams.volumeUuid;
        this.mPackageAbiOverride = sessionParams.abiOverride;
        this.mPermissionStates = sessionParams.getPermissionStates();
        this.mAllowlistedRestrictedPermissions = sessionParams.whitelistedRestrictedPermissions;
        this.mAutoRevokePermissionsMode = sessionParams.autoRevokePermissionsMode;
        this.mSigningDetails = signingDetails;
        this.mForceQueryableOverride = sessionParams.forceQueryableOverride;
        this.mDataLoaderType = sessionParams.dataLoaderParams != null ? sessionParams.dataLoaderParams.getType() : 0;
        this.mRequiredInstalledVersionCode = sessionParams.requiredInstalledVersionCode;
        this.mPackageSource = sessionParams.packageSource;
        this.mPackageLite = packageLite;
        this.mIsInherit = sessionParams.mode == 2;
        this.mSessionId = i;
        this.mRequireUserAction = sessionParams.requireUserAction;
        this.mApplicationEnabledSettingPersistent = sessionParams.applicationEnabledSettingPersistent;
        this.mPreVerifiedDomains = domainSet;
    }

    public java.lang.String toString() {
        return "InstallingSession{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " file=" + this.mOriginInfo.mFile + "}";
    }

    private int overrideInstallLocation(java.lang.String str, int i, int i2) {
        if (this.mOriginInfo.mStaged) {
            if (this.mOriginInfo.mFile != null) {
                this.mInstallFlags |= 16;
            } else {
                throw new java.lang.IllegalStateException("Invalid stage location");
            }
        }
        if (i < 0) {
            return com.android.internal.content.InstallLocationUtils.getInstallationErrorCode(i);
        }
        com.android.server.pm.pkg.PackageStateInternal packageStateInternal = this.mPm.snapshotComputer().getPackageStateInternal(str);
        com.android.server.pm.pkg.AndroidPackage androidPackage = packageStateInternal == null ? null : packageStateInternal.getAndroidPackage();
        if (androidPackage != null) {
            i = com.android.internal.content.InstallLocationUtils.installLocationPolicy(i2, i, this.mInstallFlags, packageStateInternal.isSystem(), androidPackage.isExternalStorage());
        }
        if (!((this.mInstallFlags & 16) != 0)) {
            if (i == 2) {
                this.mInstallFlags &= -17;
            } else {
                this.mInstallFlags |= 16;
            }
        }
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStartCopy(com.android.server.pm.InstallRequest installRequest) {
        if ((this.mInstallFlags & 131072) != 0) {
            this.mRet = 1;
            return;
        }
        android.content.pm.PackageInfoLite minimalPackageInfo = com.android.server.pm.PackageManagerServiceUtils.getMinimalPackageInfo(this.mPm.mContext, this.mPackageLite, this.mOriginInfo.mResolvedPath, this.mInstallFlags, this.mPackageAbiOverride);
        if ((this.mInstallFlags & 2097152) != 0) {
            this.mRet = ((java.lang.Integer) this.mPm.verifyReplacingVersionCode(minimalPackageInfo, this.mRequiredInstalledVersionCode, this.mInstallFlags).first).intValue();
            if (this.mRet != 1) {
                installRequest.setError(this.mRet, "Failed to verify version code");
                return;
            }
        }
        boolean z = (this.mInstallFlags & 2048) != 0;
        if (com.android.server.pm.PackageManagerService.DEBUG_INSTANT && z) {
            android.util.Slog.v("PackageManager", "pkgLite for install: " + minimalPackageInfo);
        }
        if (!this.mOriginInfo.mStaged && minimalPackageInfo.recommendedInstallLocation == -1) {
            minimalPackageInfo.recommendedInstallLocation = this.mPm.freeCacheForInstallation(minimalPackageInfo.recommendedInstallLocation, this.mPackageLite, this.mOriginInfo.mResolvedPath, this.mPackageAbiOverride, this.mInstallFlags);
        }
        this.mRet = overrideInstallLocation(minimalPackageInfo.packageName, minimalPackageInfo.recommendedInstallLocation, minimalPackageInfo.installLocation);
        if (this.mRet != 1) {
            installRequest.setError(this.mRet, "Failed to override installation location");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleReturnCode(com.android.server.pm.InstallRequest installRequest) {
        processPendingInstall(installRequest);
    }

    private void processPendingInstall(final com.android.server.pm.InstallRequest installRequest) {
        if (this.mRet == 1) {
            this.mRet = copyApk(installRequest);
        }
        if (this.mRet == 1) {
            com.android.internal.content.F2fsUtils.releaseCompressedBlocks(this.mPm.mContext.getContentResolver(), new java.io.File(installRequest.getCodePath()));
        }
        installRequest.setReturnCode(this.mRet);
        if (this.mParentInstallingSession != null) {
            this.mParentInstallingSession.tryProcessInstallRequest(installRequest);
        } else {
            this.mPm.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.InstallingSession$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.pm.InstallingSession.this.lambda$processPendingInstall$0(installRequest);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processPendingInstall$0(com.android.server.pm.InstallRequest installRequest) {
        processInstallRequests(this.mRet == 1, java.util.Collections.singletonList(installRequest));
    }

    private int copyApk(com.android.server.pm.InstallRequest installRequest) {
        if (this.mMoveInfo == null) {
            return copyApkForFileInstall(installRequest);
        }
        return copyApkForMoveInstall(installRequest);
    }

    private int copyApkForFileInstall(com.android.server.pm.InstallRequest installRequest) {
        android.os.Trace.traceBegin(262144L, "copyApk");
        try {
            if (this.mOriginInfo.mStaged) {
                installRequest.setCodeFile(this.mOriginInfo.mFile);
                android.os.Trace.traceEnd(262144L);
                return 1;
            }
            try {
                installRequest.setCodeFile(this.mPm.mInstallerService.allocateStageDirLegacy(this.mVolumeUuid, (this.mInstallFlags & 2048) != 0));
                int copyPackage = com.android.server.pm.PackageManagerServiceUtils.copyPackage(this.mOriginInfo.mFile.getAbsolutePath(), installRequest.getCodeFile());
                if (copyPackage != 1) {
                    android.util.Slog.e("PackageManager", "Failed to copy package");
                    installRequest.setError(copyPackage, "Failed to copy package");
                    android.os.Trace.traceEnd(262144L);
                    return copyPackage;
                }
                boolean isIncrementalPath = android.os.incremental.IncrementalManager.isIncrementalPath(installRequest.getCodeFile().getAbsolutePath());
                java.io.File file = new java.io.File(installRequest.getCodeFile(), "lib");
                com.android.internal.content.NativeLibraryHelper.Handle handle = null;
                try {
                    try {
                        handle = com.android.internal.content.NativeLibraryHelper.Handle.create(installRequest.getCodeFile());
                        copyPackage = com.android.internal.content.NativeLibraryHelper.copyNativeBinariesWithOverride(handle, file, installRequest.getAbiOverride(), isIncrementalPath);
                        if (copyPackage != 1) {
                            installRequest.setError(copyPackage, "Failed to copy native libraries");
                        }
                    } catch (java.io.IOException e) {
                        android.util.Slog.e("PackageManager", "Copying native libraries failed", e);
                        installRequest.setError(com.android.server.pm.PackageManagerException.ofInternalError("Copying native libraries failed", -1));
                    }
                    android.os.Trace.traceEnd(262144L);
                    return copyPackage;
                } finally {
                    libcore.io.IoUtils.closeQuietly(handle);
                }
            } catch (java.io.IOException e2) {
                android.util.Slog.w("PackageManager", "Failed to create copy file: " + e2);
                installRequest.setError(-4, "Failed to create copy file");
                android.os.Trace.traceEnd(262144L);
                return -4;
            }
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(262144L);
            throw th;
        }
    }

    private int copyApkForMoveInstall(com.android.server.pm.InstallRequest installRequest) {
        synchronized (this.mPm.mInstallLock) {
            try {
                this.mPm.mInstaller.moveCompleteApp(this.mMoveInfo.mFromUuid, this.mMoveInfo.mToUuid, this.mMoveInfo.mPackageName, this.mMoveInfo.mAppId, this.mMoveInfo.mSeInfo, this.mMoveInfo.mTargetSdkVersion, this.mMoveInfo.mFromCodePath);
            } catch (com.android.server.pm.Installer.InstallerException e) {
                installRequest.setError(com.android.server.pm.PackageManagerException.ofInternalError("Failed to move app", -2));
                android.util.Slog.w("PackageManager", "Failed to move app", e);
                return android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT;
            }
        }
        installRequest.setCodeFile(new java.io.File(android.os.Environment.getDataAppDirectory(this.mMoveInfo.mToUuid), new java.io.File(this.mMoveInfo.mFromCodePath).getName()));
        return 1;
    }

    private int fixUpInstallReason(java.lang.String str, int i, int i2) {
        if (this.mPm.snapshotComputer().checkUidPermission("android.permission.INSTALL_PACKAGES", i) == 0) {
            return i2;
        }
        java.lang.String deviceOwnerOrProfileOwnerPackage = this.mPm.mProtectedPackages.getDeviceOwnerOrProfileOwnerPackage(android.os.UserHandle.getUserId(i));
        if (deviceOwnerOrProfileOwnerPackage != null && deviceOwnerOrProfileOwnerPackage.equals(str)) {
            return 1;
        }
        if (i2 == 1) {
            return 0;
        }
        return i2;
    }

    public void installStage() {
        setTraceMethod("installStage").setTraceCookie(java.lang.System.identityHashCode(this));
        android.os.Trace.asyncTraceBegin(262144L, "installStage", java.lang.System.identityHashCode(this));
        android.os.Trace.asyncTraceBegin(262144L, "queueInstall", java.lang.System.identityHashCode(this));
        this.mPm.mHandler.post(new com.android.server.pm.InstallingSession$$ExternalSyntheticLambda2(this));
    }

    public void installStage(java.util.List<com.android.server.pm.InstallingSession> list) throws com.android.server.pm.PackageManagerException {
        final com.android.server.pm.InstallingSession.MultiPackageInstallingSession multiPackageInstallingSession = new com.android.server.pm.InstallingSession.MultiPackageInstallingSession(getUser(), list, this.mPm);
        setTraceMethod("installStageMultiPackage").setTraceCookie(java.lang.System.identityHashCode(multiPackageInstallingSession));
        android.os.Trace.asyncTraceBegin(262144L, "installStageMultiPackage", java.lang.System.identityHashCode(multiPackageInstallingSession));
        android.os.Trace.asyncTraceBegin(262144L, "queueInstall", java.lang.System.identityHashCode(multiPackageInstallingSession));
        android.os.Handler handler = this.mPm.mHandler;
        java.util.Objects.requireNonNull(multiPackageInstallingSession);
        handler.post(new java.lang.Runnable() { // from class: com.android.server.pm.InstallingSession$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.InstallingSession.MultiPackageInstallingSession.this.start();
            }
        });
    }

    public void movePackage() {
        setTraceMethod("movePackage").setTraceCookie(java.lang.System.identityHashCode(this));
        android.os.Trace.asyncTraceBegin(262144L, "movePackage", java.lang.System.identityHashCode(this));
        android.os.Trace.asyncTraceBegin(262144L, "queueInstall", java.lang.System.identityHashCode(this));
        this.mPm.mHandler.post(new com.android.server.pm.InstallingSession$$ExternalSyntheticLambda2(this));
    }

    public android.os.UserHandle getUser() {
        return this.mUser;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void start() {
        android.os.Trace.asyncTraceEnd(262144L, "queueInstall", java.lang.System.identityHashCode(this));
        android.os.Trace.traceBegin(262144L, "startInstall");
        com.android.server.pm.InstallRequest installRequest = new com.android.server.pm.InstallRequest(this);
        handleStartCopy(installRequest);
        handleReturnCode(installRequest);
        android.os.Trace.traceEnd(262144L);
    }

    private com.android.server.pm.InstallingSession setTraceMethod(java.lang.String str) {
        this.mTraceMethod = str;
        return this;
    }

    private void setTraceCookie(int i) {
        this.mTraceCookie = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processInstallRequests(boolean z, java.util.List<com.android.server.pm.InstallRequest> list) {
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        for (com.android.server.pm.InstallRequest installRequest : list) {
            if ((installRequest.getInstallFlags() & 131072) != 0) {
                arrayList.add(installRequest);
            } else {
                arrayList2.add(installRequest);
            }
        }
        if (!arrayList.isEmpty() && !arrayList2.isEmpty()) {
            throw new java.lang.IllegalStateException("Attempted to do a multi package install of both APEXes and APKs");
        }
        if (!arrayList.isEmpty()) {
            if (z) {
                new java.lang.Thread(new java.lang.Runnable() { // from class: com.android.server.pm.InstallingSession$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.pm.InstallingSession.this.lambda$processInstallRequests$1(arrayList);
                    }
                }, "installApexPackages").start();
                return;
            } else {
                this.mPm.notifyInstallObserver((com.android.server.pm.InstallRequest) arrayList.get(0));
                return;
            }
        }
        processApkInstallRequests(z, list);
    }

    private void processApkInstallRequests(boolean z, java.util.List<com.android.server.pm.InstallRequest> list) {
        if (!z) {
            for (com.android.server.pm.InstallRequest installRequest : list) {
                if (installRequest.getReturnCode() != 1) {
                    cleanUpForFailedInstall(installRequest);
                }
            }
        } else {
            this.mPm.installPackagesTraced(list);
            java.util.Iterator<com.android.server.pm.InstallRequest> it = list.iterator();
            while (it.hasNext()) {
                doPostInstall(it.next());
            }
        }
        java.util.Iterator<com.android.server.pm.InstallRequest> it2 = list.iterator();
        while (it2.hasNext()) {
            this.mPm.restoreAndPostInstall(it2.next());
        }
    }

    private void doPostInstall(com.android.server.pm.InstallRequest installRequest) {
        if (this.mMoveInfo != null) {
            if (installRequest.getReturnCode() == 1) {
                this.mPm.cleanUpForMoveInstall(this.mMoveInfo.mFromUuid, this.mMoveInfo.mPackageName, this.mMoveInfo.mFromCodePath);
                return;
            } else {
                this.mPm.cleanUpForMoveInstall(this.mMoveInfo.mToUuid, this.mMoveInfo.mPackageName, this.mMoveInfo.mFromCodePath);
                return;
            }
        }
        if (installRequest.getReturnCode() != 1) {
            this.mPm.removeCodePath(installRequest.getCodeFile());
        }
    }

    private void cleanUpForFailedInstall(com.android.server.pm.InstallRequest installRequest) {
        if (installRequest.isInstallMove()) {
            this.mPm.cleanUpForMoveInstall(installRequest.getMoveToUuid(), installRequest.getMovePackageName(), installRequest.getMoveFromCodePath());
        } else {
            this.mPm.removeCodePath(installRequest.getCodeFile());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: installApexPackagesTraced, reason: merged with bridge method [inline-methods] */
    public void lambda$processInstallRequests$1(java.util.List<com.android.server.pm.InstallRequest> list) {
        try {
            android.os.Trace.traceBegin(262144L, "installApexPackages");
            installApexPackages(list);
        } finally {
            android.os.Trace.traceEnd(262144L);
        }
    }

    private void installApexPackages(final java.util.List<com.android.server.pm.InstallRequest> list) {
        if (list.isEmpty()) {
            return;
        }
        if (list.size() != 1) {
            throw new java.lang.IllegalStateException("Only a non-staged install of a single APEX is supported");
        }
        com.android.server.pm.InstallRequest installRequest = list.get(0);
        boolean z = (installRequest.getDevelopmentInstallFlags() & 1) != 0;
        try {
            java.io.File file = installRequest.getOriginInfo().mResolvedFile;
            java.io.File[] listFiles = file.listFiles();
            if (listFiles == null) {
                throw com.android.server.pm.PackageManagerException.ofInternalError(file.getAbsolutePath() + " is not a directory", -36);
            }
            if (listFiles.length != 1) {
                throw com.android.server.pm.PackageManagerException.ofInternalError("Expected exactly one .apex file under " + file.getAbsolutePath() + " got: " + listFiles.length, -37);
            }
            com.android.internal.pm.parsing.PackageParser2 scanningPackageParser = this.mPm.mInjector.getScanningPackageParser();
            try {
                android.apex.ApexInfo installPackage = this.mPm.mApexManager.installPackage(listFiles[0], z);
                installRequest.setApexInfo(installPackage);
                installRequest.setApexModuleName(installPackage.moduleName);
                this.mPm.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.pm.InstallingSession$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.pm.InstallingSession.this.lambda$installApexPackages$2(list);
                    }
                });
                if (scanningPackageParser != null) {
                    scanningPackageParser.close();
                }
            } finally {
            }
        } catch (com.android.server.pm.PackageManagerException e) {
            installRequest.setError("APEX installation failed", e);
            com.android.server.pm.PackageManagerService.invalidatePackageInfoCache();
            this.mPm.notifyInstallObserver(installRequest);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$installApexPackages$2(java.util.List list) {
        processApkInstallRequests(true, list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    class MultiPackageInstallingSession {
        private final java.util.List<com.android.server.pm.InstallingSession> mChildInstallingSessions;
        private final java.util.Set<com.android.server.pm.InstallRequest> mCurrentInstallRequests;

        @android.annotation.NonNull
        final com.android.server.pm.PackageManagerService mPm;
        final android.os.UserHandle mUser;

        MultiPackageInstallingSession(android.os.UserHandle userHandle, java.util.List<com.android.server.pm.InstallingSession> list, com.android.server.pm.PackageManagerService packageManagerService) throws com.android.server.pm.PackageManagerException {
            if (list.size() == 0) {
                throw com.android.server.pm.PackageManagerException.ofInternalError("No child sessions found!", -20);
            }
            this.mPm = packageManagerService;
            this.mUser = userHandle;
            this.mChildInstallingSessions = list;
            for (int i = 0; i < list.size(); i++) {
                list.get(i).mParentInstallingSession = this;
            }
            this.mCurrentInstallRequests = new android.util.ArraySet(this.mChildInstallingSessions.size());
        }

        public void start() {
            android.os.Trace.asyncTraceEnd(262144L, "queueInstall", java.lang.System.identityHashCode(this));
            android.os.Trace.traceBegin(262144L, "start");
            int size = this.mChildInstallingSessions.size();
            java.util.ArrayList arrayList = new java.util.ArrayList(size);
            for (int i = 0; i < size; i++) {
                com.android.server.pm.InstallingSession installingSession = this.mChildInstallingSessions.get(i);
                com.android.server.pm.InstallRequest installRequest = new com.android.server.pm.InstallRequest(installingSession);
                arrayList.add(installRequest);
                installingSession.handleStartCopy(installRequest);
            }
            for (int i2 = 0; i2 < size; i2++) {
                this.mChildInstallingSessions.get(i2).handleReturnCode((com.android.server.pm.InstallRequest) arrayList.get(i2));
            }
            android.os.Trace.traceEnd(262144L);
        }

        /* JADX WARN: Code restructure failed: missing block: B:14:0x003c, code lost:
        
            r4 = new java.util.ArrayList(r3.mCurrentInstallRequests.size());
            r0 = r3.mCurrentInstallRequests.iterator();
         */
        /* JADX WARN: Code restructure failed: missing block: B:16:0x0051, code lost:
        
            if (r0.hasNext() == false) goto L25;
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x0053, code lost:
        
            r2 = r0.next();
            r2.setReturnCode(r1);
            r4.add(r2);
         */
        /* JADX WARN: Code restructure failed: missing block: B:19:0x0060, code lost:
        
            r3.mPm.mHandler.post(new com.android.server.pm.InstallingSession$MultiPackageInstallingSession$$ExternalSyntheticLambda0(r3, r1, r4));
         */
        /* JADX WARN: Code restructure failed: missing block: B:20:0x006d, code lost:
        
            return;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void tryProcessInstallRequest(com.android.server.pm.InstallRequest installRequest) {
            this.mCurrentInstallRequests.add(installRequest);
            if (this.mCurrentInstallRequests.size() != this.mChildInstallingSessions.size()) {
                return;
            }
            java.util.Iterator<com.android.server.pm.InstallRequest> it = this.mCurrentInstallRequests.iterator();
            while (true) {
                final int i = 1;
                if (!it.hasNext()) {
                    break;
                }
                com.android.server.pm.InstallRequest next = it.next();
                if (next.getReturnCode() == 0) {
                    return;
                }
                if (next.getReturnCode() != 1) {
                    i = next.getReturnCode();
                    break;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$tryProcessInstallRequest$0(int i, java.util.List list) {
            com.android.server.pm.InstallingSession.this.processInstallRequests(i == 1, list);
        }

        public java.lang.String toString() {
            return "MultiPackageInstallingSession{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + "}";
        }
    }
}
