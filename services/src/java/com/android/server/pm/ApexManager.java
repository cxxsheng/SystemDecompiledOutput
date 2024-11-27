package com.android.server.pm;

/* loaded from: classes2.dex */
public abstract class ApexManager {
    public static final int MATCH_ACTIVE_PACKAGE = 1;
    static final int MATCH_FACTORY_PACKAGE = 2;
    private static final java.lang.String TAG = "ApexManager";
    private static final android.util.Singleton<com.android.server.pm.ApexManager> sApexManagerSingleton = new android.util.Singleton<com.android.server.pm.ApexManager>() { // from class: com.android.server.pm.ApexManager.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: create, reason: merged with bridge method [inline-methods] */
        public com.android.server.pm.ApexManager m5928create() {
            return new com.android.server.pm.ApexManager.ApexManagerImpl();
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface PackageInfoFlags {
    }

    abstract boolean abortStagedSession(int i);

    public abstract long calculateSizeForCompressedApex(android.apex.CompressedApexInfoList compressedApexInfoList) throws android.os.RemoteException;

    public abstract boolean destroyCeSnapshots(int i, int i2);

    public abstract boolean destroyCeSnapshotsNotSpecified(int i, int[] iArr);

    public abstract boolean destroyDeSnapshots(int i);

    abstract void dump(java.io.PrintWriter printWriter);

    public abstract java.util.List<com.android.server.pm.ApexManager.ActiveApexInfo> getActiveApexInfos();

    @android.annotation.Nullable
    public abstract java.lang.String getActiveApexPackageNameContainingPackage(@android.annotation.NonNull java.lang.String str);

    @android.annotation.Nullable
    public abstract java.lang.String getActivePackageNameForApexModuleName(java.lang.String str);

    abstract android.apex.ApexInfo[] getAllApexInfos();

    @android.annotation.Nullable
    public abstract java.lang.String getApexModuleNameForPackageName(java.lang.String str);

    public abstract java.util.List<com.android.server.pm.ApexSystemServiceInfo> getApexSystemServices();

    @android.annotation.Nullable
    abstract java.lang.String getApkInApexInstallError(java.lang.String str);

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    public abstract java.util.List<java.lang.String> getApksInApex(java.lang.String str);

    @android.annotation.Nullable
    public abstract java.io.File getBackingApexFile(@android.annotation.NonNull java.io.File file);

    @android.annotation.NonNull
    abstract android.util.SparseArray<android.apex.ApexSessionInfo> getSessions();

    abstract android.apex.ApexInfo[] getStagedApexInfos(android.apex.ApexSessionParams apexSessionParams);

    @android.annotation.Nullable
    abstract android.apex.ApexSessionInfo getStagedSessionInfo(int i);

    abstract android.apex.ApexInfo installPackage(java.io.File file, boolean z) throws com.android.server.pm.PackageManagerException;

    abstract boolean isApexSupported();

    public abstract void markBootCompleted();

    abstract void markStagedSessionReady(int i) throws com.android.server.pm.PackageManagerException;

    abstract void markStagedSessionSuccessful(int i);

    abstract void notifyScanResult(java.util.List<com.android.server.pm.ApexManager.ScanResult> list);

    abstract void registerApkInApex(com.android.server.pm.pkg.AndroidPackage androidPackage);

    abstract void reportErrorWithApkInApex(java.lang.String str, java.lang.String str2);

    public abstract void reserveSpaceForCompressedApex(android.apex.CompressedApexInfoList compressedApexInfoList) throws android.os.RemoteException;

    public abstract boolean restoreCeData(int i, int i2, java.lang.String str);

    abstract boolean revertActiveSessions();

    public abstract boolean snapshotCeData(int i, int i2, java.lang.String str);

    abstract android.apex.ApexInfoList submitStagedSession(android.apex.ApexSessionParams apexSessionParams) throws com.android.server.pm.PackageManagerException;

    abstract boolean uninstallApex(java.lang.String str);

    public static com.android.server.pm.ApexManager getInstance() {
        return (com.android.server.pm.ApexManager) sApexManagerSingleton.get();
    }

    static class ScanResult {
        public final android.apex.ApexInfo apexInfo;
        public final java.lang.String packageName;
        public final com.android.server.pm.pkg.AndroidPackage pkg;

        ScanResult(android.apex.ApexInfo apexInfo, com.android.server.pm.pkg.AndroidPackage androidPackage, java.lang.String str) {
            this.apexInfo = apexInfo;
            this.pkg = androidPackage;
            this.packageName = str;
        }
    }

    public static class ActiveApexInfo {
        public final boolean activeApexChanged;
        public final java.io.File apexDirectory;
        public final java.io.File apexFile;

        @android.annotation.Nullable
        public final java.lang.String apexModuleName;
        public final boolean isFactory;
        public final java.io.File preInstalledApexPath;

        private ActiveApexInfo(java.io.File file, java.io.File file2, java.io.File file3) {
            this(null, file, file2, true, file3, false);
        }

        private ActiveApexInfo(@android.annotation.Nullable java.lang.String str, java.io.File file, java.io.File file2, boolean z, java.io.File file3, boolean z2) {
            this.apexModuleName = str;
            this.apexDirectory = file;
            this.preInstalledApexPath = file2;
            this.isFactory = z;
            this.apexFile = file3;
            this.activeApexChanged = z2;
        }

        public ActiveApexInfo(android.apex.ApexInfo apexInfo) {
            this(apexInfo.moduleName, new java.io.File(android.os.Environment.getApexDirectory() + java.io.File.separator + apexInfo.moduleName), new java.io.File(apexInfo.preinstalledModulePath), apexInfo.isFactory, new java.io.File(apexInfo.modulePath), apexInfo.activeApexChanged);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected static class ApexManagerImpl extends com.android.server.pm.ApexManager {

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private java.util.Set<com.android.server.pm.ApexManager.ActiveApexInfo> mActiveApexInfosCache;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private android.util.ArrayMap<java.lang.String, java.lang.String> mApexModuleNameToActivePackageName;

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private android.util.ArrayMap<java.lang.String, java.lang.String> mPackageNameToApexModuleName;
        private final java.lang.Object mLock = new java.lang.Object();

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private final java.util.List<com.android.server.pm.ApexSystemServiceInfo> mApexSystemServices = new java.util.ArrayList();

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private final android.util.ArrayMap<java.lang.String, java.util.List<java.lang.String>> mApksInApex = new android.util.ArrayMap<>();

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private final java.util.Map<java.lang.String, java.lang.String> mErrorWithApkInApex = new android.util.ArrayMap();

        protected ApexManagerImpl() {
        }

        @com.android.internal.annotations.VisibleForTesting
        protected android.apex.IApexService waitForApexService() {
            return android.apex.IApexService.Stub.asInterface(android.os.Binder.allowBlocking(android.os.ServiceManager.waitForService("apexservice")));
        }

        @Override // com.android.server.pm.ApexManager
        android.apex.ApexInfo[] getAllApexInfos() {
            try {
                return waitForApexService().getAllPackages();
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.pm.ApexManager.TAG, "Unable to retrieve packages from apexservice: " + e.toString());
                throw new java.lang.RuntimeException(e);
            }
        }

        @Override // com.android.server.pm.ApexManager
        void notifyScanResult(java.util.List<com.android.server.pm.ApexManager.ScanResult> list) {
            synchronized (this.mLock) {
                notifyScanResultLocked(list);
            }
        }

        @com.android.internal.annotations.GuardedBy({"mLock"})
        private void notifyScanResultLocked(java.util.List<com.android.server.pm.ApexManager.ScanResult> list) {
            this.mPackageNameToApexModuleName = new android.util.ArrayMap<>();
            this.mApexModuleNameToActivePackageName = new android.util.ArrayMap<>();
            for (com.android.server.pm.ApexManager.ScanResult scanResult : list) {
                android.apex.ApexInfo apexInfo = scanResult.apexInfo;
                java.lang.String str = scanResult.packageName;
                for (com.android.internal.pm.pkg.component.ParsedApexSystemService parsedApexSystemService : scanResult.pkg.getApexSystemServices()) {
                    java.lang.String minSdkVersion = parsedApexSystemService.getMinSdkVersion();
                    if (minSdkVersion != null && !com.android.modules.utils.build.UnboundedSdkLevel.isAtLeast(minSdkVersion)) {
                        android.util.Slog.d(com.android.server.pm.ApexManager.TAG, java.lang.String.format("ApexSystemService %s with min_sdk_version=%s is skipped", parsedApexSystemService.getName(), parsedApexSystemService.getMinSdkVersion()));
                    } else {
                        java.lang.String maxSdkVersion = parsedApexSystemService.getMaxSdkVersion();
                        if (maxSdkVersion != null && !com.android.modules.utils.build.UnboundedSdkLevel.isAtMost(maxSdkVersion)) {
                            android.util.Slog.d(com.android.server.pm.ApexManager.TAG, java.lang.String.format("ApexSystemService %s with max_sdk_version=%s is skipped", parsedApexSystemService.getName(), parsedApexSystemService.getMaxSdkVersion()));
                        } else if (apexInfo.isActive) {
                            java.lang.String name = parsedApexSystemService.getName();
                            for (int i = 0; i < this.mApexSystemServices.size(); i++) {
                                com.android.server.pm.ApexSystemServiceInfo apexSystemServiceInfo = this.mApexSystemServices.get(i);
                                if (apexSystemServiceInfo.getName().equals(name)) {
                                    throw new java.lang.IllegalStateException(android.text.TextUtils.formatSimple("Duplicate apex-system-service %s from %s, %s", new java.lang.Object[]{name, apexSystemServiceInfo.mJarPath, parsedApexSystemService.getJarPath()}));
                                }
                            }
                            this.mApexSystemServices.add(new com.android.server.pm.ApexSystemServiceInfo(parsedApexSystemService.getName(), parsedApexSystemService.getJarPath(), parsedApexSystemService.getInitOrder()));
                        } else {
                            continue;
                        }
                    }
                }
                java.util.Collections.sort(this.mApexSystemServices);
                this.mPackageNameToApexModuleName.put(str, apexInfo.moduleName);
                if (apexInfo.isActive) {
                    if (this.mApexModuleNameToActivePackageName.containsKey(apexInfo.moduleName)) {
                        throw new java.lang.IllegalStateException("Two active packages have the same APEX module name: " + apexInfo.moduleName);
                    }
                    this.mApexModuleNameToActivePackageName.put(apexInfo.moduleName, str);
                }
            }
        }

        @Override // com.android.server.pm.ApexManager
        public java.util.List<com.android.server.pm.ApexManager.ActiveApexInfo> getActiveApexInfos() {
            com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog("ApexManagerTiming", 262144L);
            synchronized (this.mLock) {
                if (this.mActiveApexInfosCache == null) {
                    timingsTraceAndSlog.traceBegin("getActiveApexInfos_noCache");
                    try {
                        this.mActiveApexInfosCache = new android.util.ArraySet();
                        for (android.apex.ApexInfo apexInfo : waitForApexService().getActivePackages()) {
                            this.mActiveApexInfosCache.add(new com.android.server.pm.ApexManager.ActiveApexInfo(apexInfo));
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(com.android.server.pm.ApexManager.TAG, "Unable to retrieve packages from apexservice", e);
                    }
                    timingsTraceAndSlog.traceEnd();
                }
                if (this.mActiveApexInfosCache != null) {
                    return new java.util.ArrayList(this.mActiveApexInfosCache);
                }
                return java.util.Collections.emptyList();
            }
        }

        @Override // com.android.server.pm.ApexManager
        @android.annotation.Nullable
        public java.lang.String getActiveApexPackageNameContainingPackage(java.lang.String str) {
            java.util.Objects.requireNonNull(str);
            synchronized (this.mLock) {
                try {
                    com.android.internal.util.Preconditions.checkState(this.mPackageNameToApexModuleName != null, "APEX packages have not been scanned");
                    int size = this.mApksInApex.size();
                    for (int i = 0; i < size; i++) {
                        if (this.mApksInApex.valueAt(i).contains(str)) {
                            java.lang.String keyAt = this.mApksInApex.keyAt(i);
                            int size2 = this.mPackageNameToApexModuleName.size();
                            for (int i2 = 0; i2 < size2; i2++) {
                                if (this.mPackageNameToApexModuleName.valueAt(i2).equals(keyAt)) {
                                    return this.mPackageNameToApexModuleName.keyAt(i2);
                                }
                            }
                        }
                    }
                    return null;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.pm.ApexManager
        @android.annotation.Nullable
        android.apex.ApexSessionInfo getStagedSessionInfo(int i) {
            try {
                android.apex.ApexSessionInfo stagedSessionInfo = waitForApexService().getStagedSessionInfo(i);
                if (stagedSessionInfo.isUnknown) {
                    return null;
                }
                return stagedSessionInfo;
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.pm.ApexManager.TAG, "Unable to contact apexservice", e);
                throw new java.lang.RuntimeException(e);
            }
        }

        @Override // com.android.server.pm.ApexManager
        android.util.SparseArray<android.apex.ApexSessionInfo> getSessions() {
            try {
                android.apex.ApexSessionInfo[] sessions = waitForApexService().getSessions();
                android.util.SparseArray<android.apex.ApexSessionInfo> sparseArray = new android.util.SparseArray<>(sessions.length);
                for (int i = 0; i < sessions.length; i++) {
                    sparseArray.put(sessions[i].sessionId, sessions[i]);
                }
                return sparseArray;
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.pm.ApexManager.TAG, "Unable to contact apexservice", e);
                throw new java.lang.RuntimeException(e);
            }
        }

        @Override // com.android.server.pm.ApexManager
        android.apex.ApexInfoList submitStagedSession(android.apex.ApexSessionParams apexSessionParams) throws com.android.server.pm.PackageManagerException {
            try {
                android.apex.ApexInfoList apexInfoList = new android.apex.ApexInfoList();
                waitForApexService().submitStagedSession(apexSessionParams, apexInfoList);
                return apexInfoList;
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.pm.ApexManager.TAG, "Unable to contact apexservice", e);
                throw new java.lang.RuntimeException(e);
            } catch (java.lang.Exception e2) {
                throw new com.android.server.pm.PackageManagerException(-22, "apexd verification failed : " + e2.getMessage());
            }
        }

        @Override // com.android.server.pm.ApexManager
        android.apex.ApexInfo[] getStagedApexInfos(android.apex.ApexSessionParams apexSessionParams) {
            try {
                return waitForApexService().getStagedApexInfos(apexSessionParams);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.pm.ApexManager.TAG, "Unable to contact apexservice" + e.getMessage());
                throw new java.lang.RuntimeException(e);
            } catch (java.lang.Exception e2) {
                android.util.Slog.w(com.android.server.pm.ApexManager.TAG, "Failed to collect staged apex infos" + e2.getMessage());
                return new android.apex.ApexInfo[0];
            }
        }

        @Override // com.android.server.pm.ApexManager
        void markStagedSessionReady(int i) throws com.android.server.pm.PackageManagerException {
            try {
                waitForApexService().markStagedSessionReady(i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.pm.ApexManager.TAG, "Unable to contact apexservice", e);
                throw new java.lang.RuntimeException(e);
            } catch (java.lang.Exception e2) {
                throw new com.android.server.pm.PackageManagerException(-22, "Failed to mark apexd session as ready : " + e2.getMessage());
            }
        }

        @Override // com.android.server.pm.ApexManager
        void markStagedSessionSuccessful(int i) {
            try {
                waitForApexService().markStagedSessionSuccessful(i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.pm.ApexManager.TAG, "Unable to contact apexservice", e);
                throw new java.lang.RuntimeException(e);
            } catch (java.lang.Exception e2) {
                android.util.Slog.e(com.android.server.pm.ApexManager.TAG, "Failed to mark session " + i + " as successful", e2);
            }
        }

        @Override // com.android.server.pm.ApexManager
        boolean isApexSupported() {
            return true;
        }

        @Override // com.android.server.pm.ApexManager
        boolean revertActiveSessions() {
            try {
                waitForApexService().revertActiveSessions();
                return true;
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.pm.ApexManager.TAG, "Unable to contact apexservice", e);
                return false;
            } catch (java.lang.Exception e2) {
                android.util.Slog.e(com.android.server.pm.ApexManager.TAG, e2.getMessage(), e2);
                return false;
            }
        }

        @Override // com.android.server.pm.ApexManager
        boolean abortStagedSession(int i) {
            try {
                waitForApexService().abortStagedSession(i);
                return true;
            } catch (java.lang.Exception e) {
                android.util.Slog.e(com.android.server.pm.ApexManager.TAG, e.getMessage(), e);
                return false;
            }
        }

        @Override // com.android.server.pm.ApexManager
        boolean uninstallApex(java.lang.String str) {
            try {
                waitForApexService().unstagePackages(java.util.Collections.singletonList(str));
                return true;
            } catch (java.lang.Exception e) {
                return false;
            }
        }

        @Override // com.android.server.pm.ApexManager
        void registerApkInApex(com.android.server.pm.pkg.AndroidPackage androidPackage) {
            synchronized (this.mLock) {
                try {
                    for (com.android.server.pm.ApexManager.ActiveApexInfo activeApexInfo : this.mActiveApexInfosCache) {
                        if (androidPackage.getBaseApkPath().startsWith(activeApexInfo.apexDirectory.getAbsolutePath() + java.io.File.separator)) {
                            java.util.List<java.lang.String> list = this.mApksInApex.get(activeApexInfo.apexModuleName);
                            if (list == null) {
                                list = com.google.android.collect.Lists.newArrayList();
                                this.mApksInApex.put(activeApexInfo.apexModuleName, list);
                            }
                            android.util.Slog.i(com.android.server.pm.ApexManager.TAG, "Registering " + androidPackage.getPackageName() + " as apk-in-apex of " + activeApexInfo.apexModuleName);
                            list.add(androidPackage.getPackageName());
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.pm.ApexManager
        void reportErrorWithApkInApex(java.lang.String str, java.lang.String str2) {
            synchronized (this.mLock) {
                try {
                    for (com.android.server.pm.ApexManager.ActiveApexInfo activeApexInfo : this.mActiveApexInfosCache) {
                        if (str.startsWith(activeApexInfo.apexDirectory.getAbsolutePath())) {
                            this.mErrorWithApkInApex.put(activeApexInfo.apexModuleName, str2);
                        }
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.pm.ApexManager
        @android.annotation.Nullable
        java.lang.String getApkInApexInstallError(java.lang.String str) {
            synchronized (this.mLock) {
                try {
                    com.android.internal.util.Preconditions.checkState(this.mPackageNameToApexModuleName != null, "APEX packages have not been scanned");
                    java.lang.String str2 = this.mPackageNameToApexModuleName.get(str);
                    if (str2 == null) {
                        return null;
                    }
                    return this.mErrorWithApkInApex.get(str2);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.pm.ApexManager
        @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
        public java.util.List<java.lang.String> getApksInApex(java.lang.String str) {
            synchronized (this.mLock) {
                try {
                    com.android.internal.util.Preconditions.checkState(this.mPackageNameToApexModuleName != null, "APEX packages have not been scanned");
                    java.lang.String str2 = this.mPackageNameToApexModuleName.get(str);
                    if (str2 == null) {
                        return java.util.Collections.emptyList();
                    }
                    return this.mApksInApex.getOrDefault(str2, java.util.Collections.emptyList());
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.android.server.pm.ApexManager
        @android.annotation.Nullable
        public java.lang.String getApexModuleNameForPackageName(java.lang.String str) {
            java.lang.String str2;
            synchronized (this.mLock) {
                com.android.internal.util.Preconditions.checkState(this.mPackageNameToApexModuleName != null, "APEX packages have not been scanned");
                str2 = this.mPackageNameToApexModuleName.get(str);
            }
            return str2;
        }

        @Override // com.android.server.pm.ApexManager
        @android.annotation.Nullable
        public java.lang.String getActivePackageNameForApexModuleName(java.lang.String str) {
            java.lang.String str2;
            synchronized (this.mLock) {
                com.android.internal.util.Preconditions.checkState(this.mApexModuleNameToActivePackageName != null, "APEX packages have not been scanned");
                str2 = this.mApexModuleNameToActivePackageName.get(str);
            }
            return str2;
        }

        @Override // com.android.server.pm.ApexManager
        public boolean snapshotCeData(int i, int i2, java.lang.String str) {
            java.lang.String str2;
            synchronized (this.mLock) {
                com.android.internal.util.Preconditions.checkState(this.mPackageNameToApexModuleName != null, "APEX packages have not been scanned");
                str2 = this.mPackageNameToApexModuleName.get(str);
            }
            if (str2 == null) {
                android.util.Slog.e(com.android.server.pm.ApexManager.TAG, "Invalid apex package name: " + str);
                return false;
            }
            try {
                waitForApexService().snapshotCeData(i, i2, str2);
                return true;
            } catch (java.lang.Exception e) {
                android.util.Slog.e(com.android.server.pm.ApexManager.TAG, e.getMessage(), e);
                return false;
            }
        }

        @Override // com.android.server.pm.ApexManager
        public boolean restoreCeData(int i, int i2, java.lang.String str) {
            java.lang.String str2;
            synchronized (this.mLock) {
                com.android.internal.util.Preconditions.checkState(this.mPackageNameToApexModuleName != null, "APEX packages have not been scanned");
                str2 = this.mPackageNameToApexModuleName.get(str);
            }
            if (str2 == null) {
                android.util.Slog.e(com.android.server.pm.ApexManager.TAG, "Invalid apex package name: " + str);
                return false;
            }
            try {
                waitForApexService().restoreCeData(i, i2, str2);
                return true;
            } catch (java.lang.Exception e) {
                android.util.Slog.e(com.android.server.pm.ApexManager.TAG, e.getMessage(), e);
                return false;
            }
        }

        @Override // com.android.server.pm.ApexManager
        public boolean destroyDeSnapshots(int i) {
            try {
                waitForApexService().destroyDeSnapshots(i);
                return true;
            } catch (java.lang.Exception e) {
                android.util.Slog.e(com.android.server.pm.ApexManager.TAG, e.getMessage(), e);
                return false;
            }
        }

        @Override // com.android.server.pm.ApexManager
        public boolean destroyCeSnapshots(int i, int i2) {
            try {
                waitForApexService().destroyCeSnapshots(i, i2);
                return true;
            } catch (java.lang.Exception e) {
                android.util.Slog.e(com.android.server.pm.ApexManager.TAG, e.getMessage(), e);
                return false;
            }
        }

        @Override // com.android.server.pm.ApexManager
        public boolean destroyCeSnapshotsNotSpecified(int i, int[] iArr) {
            try {
                waitForApexService().destroyCeSnapshotsNotSpecified(i, iArr);
                return true;
            } catch (java.lang.Exception e) {
                android.util.Slog.e(com.android.server.pm.ApexManager.TAG, e.getMessage(), e);
                return false;
            }
        }

        @Override // com.android.server.pm.ApexManager
        public void markBootCompleted() {
            try {
                waitForApexService().markBootCompleted();
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(com.android.server.pm.ApexManager.TAG, "Unable to contact apexservice", e);
            }
        }

        @Override // com.android.server.pm.ApexManager
        public long calculateSizeForCompressedApex(android.apex.CompressedApexInfoList compressedApexInfoList) throws android.os.RemoteException {
            return waitForApexService().calculateSizeForCompressedApex(compressedApexInfoList);
        }

        @Override // com.android.server.pm.ApexManager
        public void reserveSpaceForCompressedApex(android.apex.CompressedApexInfoList compressedApexInfoList) throws android.os.RemoteException {
            waitForApexService().reserveSpaceForCompressedApex(compressedApexInfoList);
        }

        private android.content.pm.SigningDetails getSigningDetails(android.content.pm.PackageInfo packageInfo) throws com.android.server.pm.PackageManagerException {
            android.content.pm.parsing.result.ParseResult verify = android.util.apk.ApkSignatureVerifier.verify(android.content.pm.parsing.result.ParseTypeImpl.forDefaultParsing(), packageInfo.applicationInfo.sourceDir, android.util.apk.ApkSignatureVerifier.getMinimumSignatureSchemeVersionForTargetSdk(packageInfo.applicationInfo.targetSdkVersion));
            if (verify.isError()) {
                throw new com.android.server.pm.PackageManagerException(verify.getErrorCode(), verify.getErrorMessage(), verify.getException());
            }
            return (android.content.pm.SigningDetails) verify.getResult();
        }

        private void checkApexSignature(android.content.pm.PackageInfo packageInfo, android.content.pm.PackageInfo packageInfo2) throws com.android.server.pm.PackageManagerException {
            if (!getSigningDetails(packageInfo2).checkCapability(getSigningDetails(packageInfo), 1)) {
                throw new com.android.server.pm.PackageManagerException(-118, "APK container signature of " + packageInfo2.applicationInfo.sourceDir + " is not compatible with currently installed on device");
            }
        }

        @Override // com.android.server.pm.ApexManager
        android.apex.ApexInfo installPackage(java.io.File file, boolean z) throws com.android.server.pm.PackageManagerException {
            try {
                return waitForApexService().installAndActivatePackage(file.getAbsolutePath(), z);
            } catch (android.os.RemoteException e) {
                throw new com.android.server.pm.PackageManagerException(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, "apexservice not available");
            } catch (java.lang.Exception e2) {
                throw new com.android.server.pm.PackageManagerException(android.hardware.biometrics.fingerprint.V2_1.RequestStatus.SYS_ETIMEDOUT, e2.getMessage());
            }
        }

        @Override // com.android.server.pm.ApexManager
        public java.util.List<com.android.server.pm.ApexSystemServiceInfo> getApexSystemServices() {
            java.util.List<com.android.server.pm.ApexSystemServiceInfo> list;
            synchronized (this.mLock) {
                com.android.internal.util.Preconditions.checkState(this.mApexSystemServices != null, "APEX packages have not been scanned");
                list = this.mApexSystemServices;
            }
            return list;
        }

        @Override // com.android.server.pm.ApexManager
        public java.io.File getBackingApexFile(java.io.File file) {
            java.nio.file.Path path = file.toPath();
            if (!path.startsWith(android.os.Environment.getApexDirectory().toPath()) || path.getNameCount() < 2) {
                return null;
            }
            java.lang.String path2 = file.toPath().getName(1).toString();
            java.util.List<com.android.server.pm.ApexManager.ActiveApexInfo> activeApexInfos = getActiveApexInfos();
            for (int i = 0; i < activeApexInfos.size(); i++) {
                if (activeApexInfos.get(i).apexModuleName.equals(path2)) {
                    return activeApexInfos.get(i).apexFile;
                }
            }
            return null;
        }

        @Override // com.android.server.pm.ApexManager
        void dump(java.io.PrintWriter printWriter) {
            com.android.internal.util.IndentingPrintWriter indentingPrintWriter = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ", 120);
            try {
                indentingPrintWriter.println();
                indentingPrintWriter.println("APEX session state:");
                indentingPrintWriter.increaseIndent();
                for (android.apex.ApexSessionInfo apexSessionInfo : waitForApexService().getSessions()) {
                    indentingPrintWriter.println("Session ID: " + apexSessionInfo.sessionId);
                    indentingPrintWriter.increaseIndent();
                    if (apexSessionInfo.isUnknown) {
                        indentingPrintWriter.println("State: UNKNOWN");
                    } else if (apexSessionInfo.isVerified) {
                        indentingPrintWriter.println("State: VERIFIED");
                    } else if (apexSessionInfo.isStaged) {
                        indentingPrintWriter.println("State: STAGED");
                    } else if (apexSessionInfo.isActivated) {
                        indentingPrintWriter.println("State: ACTIVATED");
                    } else if (apexSessionInfo.isActivationFailed) {
                        indentingPrintWriter.println("State: ACTIVATION FAILED");
                    } else if (apexSessionInfo.isSuccess) {
                        indentingPrintWriter.println("State: SUCCESS");
                    } else if (apexSessionInfo.isRevertInProgress) {
                        indentingPrintWriter.println("State: REVERT IN PROGRESS");
                    } else if (apexSessionInfo.isReverted) {
                        indentingPrintWriter.println("State: REVERTED");
                    } else if (apexSessionInfo.isRevertFailed) {
                        indentingPrintWriter.println("State: REVERT FAILED");
                    }
                    indentingPrintWriter.decreaseIndent();
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
            } catch (android.os.RemoteException e) {
                indentingPrintWriter.println("Couldn't communicate with apexd.");
            }
        }
    }
}
