package com.android.server.pm;

/* loaded from: classes2.dex */
final class FreeStorageHelper {
    private static final long DEFAULT_MANDATORY_FSTRIM_INTERVAL = 259200000;
    private static final long FREE_STORAGE_UNUSED_STATIC_SHARED_LIB_MIN_CACHE_PERIOD = java.util.concurrent.TimeUnit.HOURS.toMillis(2);
    private final android.content.Context mContext;
    private final boolean mEnableFreeCacheV2;
    private final com.android.server.pm.PackageManagerServiceInjector mInjector;
    private final com.android.server.pm.PackageManagerService mPm;

    FreeStorageHelper(com.android.server.pm.PackageManagerService packageManagerService, com.android.server.pm.PackageManagerServiceInjector packageManagerServiceInjector, android.content.Context context, boolean z) {
        this.mPm = packageManagerService;
        this.mInjector = packageManagerServiceInjector;
        this.mContext = context;
        this.mEnableFreeCacheV2 = z;
    }

    FreeStorageHelper(com.android.server.pm.PackageManagerService packageManagerService) {
        this(packageManagerService, packageManagerService.mInjector, packageManagerService.mContext, android.os.SystemProperties.getBoolean("fw.free_cache_v2", true));
    }

    void freeStorage(java.lang.String str, long j, int i) throws java.io.IOException {
        java.io.File findPathForUuid = ((android.os.storage.StorageManager) this.mInjector.getSystemService(android.os.storage.StorageManager.class)).findPathForUuid(str);
        if (findPathForUuid.getUsableSpace() >= j) {
            return;
        }
        if (this.mEnableFreeCacheV2) {
            boolean equals = java.util.Objects.equals(android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL, str);
            boolean z = (i & 1) != 0;
            if (equals && (z || android.os.SystemProperties.getBoolean("persist.sys.preloads.file_cache_expired", false))) {
                this.mPm.deletePreloadsFileCache();
                if (findPathForUuid.getUsableSpace() >= j) {
                    return;
                }
            }
            if (equals && z) {
                android.os.FileUtils.deleteContents(this.mPm.getCacheDir());
                if (findPathForUuid.getUsableSpace() >= j) {
                    return;
                }
            }
            synchronized (this.mPm.mInstallLock) {
                try {
                    this.mPm.mInstaller.freeCache(str, j, 256);
                } catch (com.android.server.pm.Installer.InstallerException e) {
                }
            }
            if (findPathForUuid.getUsableSpace() >= j) {
                return;
            }
            com.android.server.pm.Computer snapshotComputer = this.mPm.snapshotComputer();
            com.android.server.pm.SharedLibrariesImpl sharedLibrariesImpl = this.mPm.mInjector.getSharedLibrariesImpl();
            if (equals && sharedLibrariesImpl.pruneUnusedStaticSharedLibraries(snapshotComputer, j, android.provider.Settings.Global.getLong(this.mContext.getContentResolver(), "unused_static_shared_lib_min_cache_period", FREE_STORAGE_UNUSED_STATIC_SHARED_LIB_MIN_CACHE_PERIOD))) {
                return;
            }
            if (equals && this.mPm.mInstantAppRegistry.pruneInstalledInstantApps(snapshotComputer, j, android.provider.Settings.Global.getLong(this.mContext.getContentResolver(), "installed_instant_app_min_cache_period", com.android.server.usage.UnixCalendar.WEEK_IN_MILLIS))) {
                return;
            }
            synchronized (this.mPm.mInstallLock) {
                try {
                    this.mPm.mInstaller.freeCache(str, j, 768);
                } catch (com.android.server.pm.Installer.InstallerException e2) {
                }
            }
            if (findPathForUuid.getUsableSpace() >= j) {
                return;
            }
            if (equals && this.mPm.mInstantAppRegistry.pruneUninstalledInstantApps(snapshotComputer, j, android.provider.Settings.Global.getLong(this.mContext.getContentResolver(), "uninstalled_instant_app_min_cache_period", com.android.server.usage.UnixCalendar.WEEK_IN_MILLIS))) {
                return;
            }
            android.os.storage.StorageManagerInternal storageManagerInternal = (android.os.storage.StorageManagerInternal) this.mInjector.getLocalService(android.os.storage.StorageManagerInternal.class);
            long usableSpace = j - findPathForUuid.getUsableSpace();
            if (usableSpace > 0) {
                storageManagerInternal.freeCache(str, usableSpace);
            }
            this.mPm.mInstallerService.freeStageDirs(str);
        } else {
            synchronized (this.mPm.mInstallLock) {
                try {
                    this.mPm.mInstaller.freeCache(str, j, 0);
                } catch (com.android.server.pm.Installer.InstallerException e3) {
                }
            }
        }
        if (findPathForUuid.getUsableSpace() >= j) {
            return;
        }
        throw new java.io.IOException("Failed to free " + j + " on storage device at " + findPathForUuid);
    }

    int freeCacheForInstallation(int i, android.content.pm.parsing.PackageLite packageLite, java.lang.String str, java.lang.String str2, int i2) {
        int i3;
        long storageLowBytes = android.os.storage.StorageManager.from(this.mContext).getStorageLowBytes(android.os.Environment.getDataDirectory());
        long calculateInstalledSize = com.android.server.pm.PackageManagerServiceUtils.calculateInstalledSize(str, str2);
        if (calculateInstalledSize >= 0) {
            synchronized (this.mPm.mInstallLock) {
                try {
                    this.mPm.mInstaller.freeCache(null, calculateInstalledSize + storageLowBytes, 0);
                    android.content.pm.PackageInfoLite minimalPackageInfo = com.android.server.pm.PackageManagerServiceUtils.getMinimalPackageInfo(this.mContext, packageLite, str, i2, str2);
                    if (minimalPackageInfo.recommendedInstallLocation == -6) {
                        minimalPackageInfo.recommendedInstallLocation = -1;
                    }
                    i3 = minimalPackageInfo.recommendedInstallLocation;
                } catch (com.android.server.pm.Installer.InstallerException e) {
                    android.util.Slog.w("PackageManager", "Failed to free cache", e);
                } finally {
                }
            }
            return i3;
        }
        return i;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0051 A[Catch: RemoteException -> 0x004c, TryCatch #0 {RemoteException -> 0x004c, blocks: (B:3:0x0005, B:6:0x000e, B:8:0x0023, B:10:0x0031, B:12:0x0051, B:17:0x0055), top: B:2:0x0005 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void performFstrimIfNeeded() {
        boolean z;
        com.android.server.pm.PackageManagerServiceUtils.enforceSystemOrRoot("Only the system can request fstrim");
        try {
            android.os.storage.IStorageManager storageManager = com.android.internal.content.InstallLocationUtils.getStorageManager();
            if (storageManager == null) {
                android.util.Slog.e("PackageManager", "storageManager service unavailable!");
                return;
            }
            long j = android.provider.Settings.Global.getLong(this.mContext.getContentResolver(), "fstrim_mandatory_interval", DEFAULT_MANDATORY_FSTRIM_INTERVAL);
            if (j > 0) {
                long currentTimeMillis = java.lang.System.currentTimeMillis() - storageManager.lastMaintenance();
                if (currentTimeMillis > j) {
                    android.util.Slog.w("PackageManager", "No disk maintenance in " + currentTimeMillis + "; running immediately");
                    z = true;
                    if (z) {
                        storageManager.runMaintenance();
                    }
                }
            }
            z = false;
            if (z) {
            }
        } catch (android.os.RemoteException e) {
        }
    }
}
