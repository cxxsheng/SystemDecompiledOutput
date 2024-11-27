package com.android.server.pm;

/* loaded from: classes2.dex */
final class InitAppsHelper {
    private final com.android.server.pm.ApexManager mApexManager;
    private int mCachedSystemApps;
    private final java.util.concurrent.ExecutorService mExecutorService;
    private final com.android.server.pm.InstallPackageHelper mInstallPackageHelper;
    private final boolean mIsDeviceUpgrading;
    private final com.android.server.pm.PackageManagerService mPm;
    private final int mScanFlags;
    private int mSystemPackagesCount;
    private final int mSystemParseFlags;
    private final java.util.List<com.android.server.pm.ScanPartition> mSystemPartitions;
    private final int mSystemScanFlags;
    private long mSystemScanTime;
    private final android.util.ArrayMap<java.lang.String, java.io.File> mExpectingBetter = new android.util.ArrayMap<>();
    private final java.util.List<java.lang.String> mPossiblyDeletedUpdatedSystemApps = new java.util.ArrayList();
    private final java.util.List<java.lang.String> mStubSystemApps = new java.util.ArrayList();
    private final java.util.List<com.android.server.pm.ScanPartition> mDirsToScanAsSystem = getSystemScanPartitions();

    InitAppsHelper(com.android.server.pm.PackageManagerService packageManagerService, com.android.server.pm.ApexManager apexManager, com.android.server.pm.InstallPackageHelper installPackageHelper, java.util.List<com.android.server.pm.ScanPartition> list) {
        this.mPm = packageManagerService;
        this.mApexManager = apexManager;
        this.mInstallPackageHelper = installPackageHelper;
        this.mSystemPartitions = list;
        this.mIsDeviceUpgrading = this.mPm.isDeviceUpgrading();
        if (this.mIsDeviceUpgrading || this.mPm.isFirstBoot()) {
            this.mScanFlags = 4624;
        } else {
            this.mScanFlags = com.android.internal.util.FrameworkStatsLog.EXPRESS_EVENT_REPORTED;
        }
        this.mSystemParseFlags = this.mPm.getDefParseFlags() | 16;
        this.mSystemScanFlags = this.mScanFlags | 65536;
        this.mExecutorService = com.android.server.pm.ParallelPackageParser.makeExecutorService();
    }

    private java.util.List<com.android.server.pm.ScanPartition> getSystemScanPartitions() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.addAll(this.mSystemPartitions);
        arrayList.addAll(getApexScanPartitions());
        android.util.Slog.d("PackageManager", "Directories scanned as system partitions: " + arrayList);
        return arrayList;
    }

    private java.util.List<com.android.server.pm.ScanPartition> getApexScanPartitions() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.List<com.android.server.pm.ApexManager.ActiveApexInfo> activeApexInfos = this.mApexManager.getActiveApexInfos();
        for (int i = 0; i < activeApexInfos.size(); i++) {
            com.android.server.pm.ScanPartition resolveApexToScanPartition = resolveApexToScanPartition(activeApexInfos.get(i));
            if (resolveApexToScanPartition != null) {
                arrayList.add(resolveApexToScanPartition);
            }
        }
        return arrayList;
    }

    @android.annotation.Nullable
    private static com.android.server.pm.ScanPartition resolveApexToScanPartition(com.android.server.pm.ApexManager.ActiveApexInfo activeApexInfo) {
        int size = com.android.server.pm.PackageManagerService.SYSTEM_PARTITIONS.size();
        for (int i = 0; i < size; i++) {
            com.android.server.pm.ScanPartition scanPartition = com.android.server.pm.PackageManagerService.SYSTEM_PARTITIONS.get(i);
            if (!activeApexInfo.preInstalledApexPath.getAbsolutePath().equals(scanPartition.getFolder().getAbsolutePath())) {
                if (!activeApexInfo.preInstalledApexPath.getAbsolutePath().startsWith(scanPartition.getFolder().getAbsolutePath() + java.io.File.separator)) {
                }
            }
            return new com.android.server.pm.ScanPartition(activeApexInfo.apexDirectory, scanPartition, activeApexInfo);
        }
        return null;
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock", "mPm.mLock"})
    private java.util.List<com.android.server.pm.ApexManager.ScanResult> scanApexPackagesTraced(com.android.internal.pm.parsing.PackageParser2 packageParser2) {
        android.os.Trace.traceBegin(262144L, "scanApexPackages");
        try {
            return this.mInstallPackageHelper.scanApexPackages(this.mApexManager.getAllApexInfos(), this.mSystemParseFlags, this.mSystemScanFlags, packageParser2, this.mExecutorService);
        } finally {
            android.os.Trace.traceEnd(262144L);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock", "mPm.mLock"})
    public com.android.internal.content.om.OverlayConfig initSystemApps(com.android.internal.pm.parsing.PackageParser2 packageParser2, com.android.server.utils.WatchedArrayMap<java.lang.String, com.android.server.pm.PackageSetting> watchedArrayMap, int[] iArr, long j) {
        this.mApexManager.notifyScanResult(scanApexPackagesTraced(packageParser2));
        scanSystemDirs(packageParser2, this.mExecutorService);
        final android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        for (com.android.server.pm.ApexManager.ActiveApexInfo activeApexInfo : this.mApexManager.getActiveApexInfos()) {
            java.util.Iterator<java.lang.String> it = this.mApexManager.getApksInApex(this.mApexManager.getActivePackageNameForApexModuleName(activeApexInfo.apexModuleName)).iterator();
            while (it.hasNext()) {
                arrayMap.put(it.next(), activeApexInfo.preInstalledApexPath);
            }
        }
        com.android.internal.content.om.OverlayConfig initializeSystemInstance = com.android.internal.content.om.OverlayConfig.initializeSystemInstance(new com.android.internal.content.om.OverlayConfig.PackageProvider() { // from class: com.android.server.pm.InitAppsHelper$$ExternalSyntheticLambda1
            public final void forEachPackage(com.android.internal.util.function.TriConsumer triConsumer) {
                com.android.server.pm.InitAppsHelper.this.lambda$initSystemApps$1(arrayMap, triConsumer);
            }
        });
        updateStubSystemAppsList(this.mStubSystemApps);
        this.mInstallPackageHelper.prepareSystemPackageCleanUp(watchedArrayMap, this.mPossiblyDeletedUpdatedSystemApps, this.mExpectingBetter, iArr);
        logSystemAppsScanningTime(j);
        return initializeSystemInstance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initSystemApps$1(final android.util.ArrayMap arrayMap, final com.android.internal.util.function.TriConsumer triConsumer) {
        this.mPm.forEachPackageState(this.mPm.snapshotComputer(), new java.util.function.Consumer() { // from class: com.android.server.pm.InitAppsHelper$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.pm.InitAppsHelper.lambda$initSystemApps$0(triConsumer, arrayMap, (com.android.server.pm.pkg.PackageStateInternal) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$initSystemApps$0(com.android.internal.util.function.TriConsumer triConsumer, android.util.ArrayMap arrayMap, com.android.server.pm.pkg.PackageStateInternal packageStateInternal) {
        com.android.internal.pm.parsing.pkg.AndroidPackageInternal pkg = packageStateInternal.getPkg();
        if (pkg != null) {
            triConsumer.accept(pkg, java.lang.Boolean.valueOf(packageStateInternal.isSystem()), (java.io.File) arrayMap.get(pkg.getPackageName()));
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock", "mPm.mLock"})
    private void logSystemAppsScanningTime(long j) {
        this.mCachedSystemApps = com.android.server.pm.parsing.PackageCacher.sCachedPackageReadCount.get();
        this.mPm.mSettings.pruneSharedUsersLPw();
        this.mSystemScanTime = android.os.SystemClock.uptimeMillis() - j;
        this.mSystemPackagesCount = this.mPm.mPackages.size();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Finished scanning system apps. Time: ");
        sb.append(this.mSystemScanTime);
        sb.append(" ms, packageCount: ");
        sb.append(this.mSystemPackagesCount);
        sb.append(" , timePerPackage: ");
        sb.append(this.mSystemPackagesCount == 0 ? 0L : this.mSystemScanTime / this.mSystemPackagesCount);
        sb.append(" , cached: ");
        sb.append(this.mCachedSystemApps);
        android.util.Slog.i("PackageManager", sb.toString());
        if (this.mIsDeviceUpgrading && this.mSystemPackagesCount > 0) {
            com.android.internal.util.FrameworkStatsLog.write(239, 15, this.mSystemScanTime / this.mSystemPackagesCount);
        }
    }

    void fixInstalledAppDirMode() {
        try {
            java.nio.file.DirectoryStream<java.nio.file.Path> newDirectoryStream = java.nio.file.Files.newDirectoryStream(this.mPm.getAppInstallDir().toPath());
            try {
                newDirectoryStream.forEach(new java.util.function.Consumer() { // from class: com.android.server.pm.InitAppsHelper$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        com.android.server.pm.InitAppsHelper.lambda$fixInstalledAppDirMode$2((java.nio.file.Path) obj);
                    }
                });
                newDirectoryStream.close();
            } finally {
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.w("PackageManager", "Failed to walk the app install directory to fix the modes", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$fixInstalledAppDirMode$2(java.nio.file.Path path) {
        try {
            android.system.Os.chmod(path.toString(), 505);
        } catch (android.system.ErrnoException e) {
            android.util.Slog.w("PackageManager", "Failed to fix an installed app dir mode", e);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock", "mPm.mLock"})
    public void initNonSystemApps(com.android.internal.pm.parsing.PackageParser2 packageParser2, @android.annotation.NonNull int[] iArr, long j) {
        android.util.EventLog.writeEvent(com.android.server.EventLogTags.BOOT_PROGRESS_PMS_DATA_SCAN_START, android.os.SystemClock.uptimeMillis());
        if ((this.mScanFlags & 4096) == 4096) {
            fixInstalledAppDirMode();
        }
        scanDirTracedLI(this.mPm.getAppInstallDir(), 0, this.mScanFlags | 128, packageParser2, this.mExecutorService, null);
        java.util.List<java.lang.Runnable> shutdownNow = this.mExecutorService.shutdownNow();
        if (!shutdownNow.isEmpty()) {
            throw new java.lang.IllegalStateException("Not all tasks finished before calling close: " + shutdownNow);
        }
        fixSystemPackages(iArr);
        logNonSystemAppScanningTime(j);
        this.mExpectingBetter.clear();
        this.mPm.mSettings.pruneRenamedPackagesLPw();
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock", "mPm.mLock"})
    private void fixSystemPackages(@android.annotation.NonNull int[] iArr) {
        this.mInstallPackageHelper.cleanupDisabledPackageSettings(this.mPossiblyDeletedUpdatedSystemApps, iArr, this.mScanFlags);
        this.mInstallPackageHelper.checkExistingBetterPackages(this.mExpectingBetter, this.mStubSystemApps, this.mSystemScanFlags, this.mSystemParseFlags);
        this.mInstallPackageHelper.installSystemStubPackages(this.mStubSystemApps, this.mScanFlags);
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock", "mPm.mLock"})
    private void logNonSystemAppScanningTime(long j) {
        int i = com.android.server.pm.parsing.PackageCacher.sCachedPackageReadCount.get() - this.mCachedSystemApps;
        long uptimeMillis = (android.os.SystemClock.uptimeMillis() - this.mSystemScanTime) - j;
        int size = this.mPm.mPackages.size() - this.mSystemPackagesCount;
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Finished scanning non-system apps. Time: ");
        sb.append(uptimeMillis);
        sb.append(" ms, packageCount: ");
        sb.append(size);
        sb.append(" , timePerPackage: ");
        sb.append(size == 0 ? 0L : uptimeMillis / size);
        sb.append(" , cached: ");
        sb.append(i);
        android.util.Slog.i("PackageManager", sb.toString());
        if (this.mIsDeviceUpgrading && size > 0) {
            com.android.internal.util.FrameworkStatsLog.write(239, 14, uptimeMillis / size);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock", "mPm.mLock"})
    private void scanSystemDirs(com.android.internal.pm.parsing.PackageParser2 packageParser2, java.util.concurrent.ExecutorService executorService) {
        java.io.File file = new java.io.File(android.os.Environment.getRootDirectory(), "framework");
        for (int size = this.mDirsToScanAsSystem.size() - 1; size >= 0; size--) {
            com.android.server.pm.ScanPartition scanPartition = this.mDirsToScanAsSystem.get(size);
            if (scanPartition.getOverlayFolder() != null) {
                scanDirTracedLI(scanPartition.getOverlayFolder(), this.mSystemParseFlags, this.mSystemScanFlags | scanPartition.scanFlag, packageParser2, executorService, scanPartition.apexInfo);
            }
        }
        scanDirTracedLI(file, this.mSystemParseFlags, this.mSystemScanFlags | 1 | 131072, packageParser2, executorService, null);
        if (!this.mPm.mPackages.containsKey(com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME)) {
            throw new java.lang.IllegalStateException("Failed to load frameworks package; check log for warnings");
        }
        int size2 = this.mDirsToScanAsSystem.size();
        for (int i = 0; i < size2; i++) {
            com.android.server.pm.ScanPartition scanPartition2 = this.mDirsToScanAsSystem.get(i);
            if (scanPartition2.getPrivAppFolder() != null) {
                scanDirTracedLI(scanPartition2.getPrivAppFolder(), this.mSystemParseFlags, scanPartition2.scanFlag | this.mSystemScanFlags | 131072, packageParser2, executorService, scanPartition2.apexInfo);
            }
            scanDirTracedLI(scanPartition2.getAppFolder(), this.mSystemParseFlags, scanPartition2.scanFlag | this.mSystemScanFlags, packageParser2, executorService, scanPartition2.apexInfo);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mLock"})
    private void updateStubSystemAppsList(java.util.List<java.lang.String> list) {
        int size = this.mPm.mPackages.size();
        for (int i = 0; i < size; i++) {
            com.android.server.pm.pkg.AndroidPackage valueAt = this.mPm.mPackages.valueAt(i);
            if (valueAt.isStub()) {
                list.add(valueAt.getPackageName());
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mPm.mInstallLock", "mPm.mLock"})
    private void scanDirTracedLI(java.io.File file, int i, int i2, com.android.internal.pm.parsing.PackageParser2 packageParser2, java.util.concurrent.ExecutorService executorService, @android.annotation.Nullable com.android.server.pm.ApexManager.ActiveApexInfo activeApexInfo) {
        int i3;
        android.os.Trace.traceBegin(262144L, "scanDir [" + file.getAbsolutePath() + "]");
        if ((8388608 & i2) == 0) {
            i3 = i;
        } else {
            i3 = i | 512;
        }
        try {
            this.mInstallPackageHelper.installPackagesFromDir(file, i3, i2, packageParser2, executorService, activeApexInfo);
        } finally {
            android.os.Trace.traceEnd(262144L);
        }
    }

    public boolean isExpectingBetter(java.lang.String str) {
        return this.mExpectingBetter.containsKey(str);
    }

    public java.util.List<com.android.server.pm.ScanPartition> getDirsToScanAsSystem() {
        return this.mDirsToScanAsSystem;
    }

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
    public int getSystemScanFlags() {
        return this.mSystemScanFlags;
    }
}
