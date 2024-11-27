package com.android.server.usage;

/* loaded from: classes2.dex */
public class StorageStatsService extends android.app.usage.IStorageStatsManager.Stub {
    private static final long DEFAULT_QUOTA = android.util.DataUnit.MEBIBYTES.toBytes(64);
    private static final long DELAY_CHECK_STORAGE_DELTA = 30000;
    private static final long DELAY_RECALCULATE_QUOTAS = 36000000;
    private static final java.lang.String PROP_DISABLE_QUOTA = "fw.disable_quota";
    private static final java.lang.String PROP_STORAGE_CRATES = "fw.storage_crates";
    private static final java.lang.String PROP_VERIFY_STORAGE = "fw.verify_storage";
    private static final java.lang.String TAG = "StorageStatsService";
    private final android.app.AppOpsManager mAppOps;
    private final android.content.Context mContext;
    private final com.android.server.usage.StorageStatsService.H mHandler;
    private final com.android.server.pm.Installer mInstaller;
    private final android.content.pm.PackageManager mPackage;
    private final android.os.storage.StorageManager mStorage;
    private final android.os.UserManager mUser;
    private final java.util.concurrent.CopyOnWriteArrayList<android.util.Pair<java.lang.String, com.android.server.usage.StorageStatsManagerLocal.StorageStatsAugmenter>> mStorageStatsAugmenters = new java.util.concurrent.CopyOnWriteArrayList<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int mStorageThresholdPercentHigh = 20;
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.util.ArrayMap<java.lang.String, android.util.SparseLongArray> mCacheQuotas = new android.util.ArrayMap<>();

    public static class Lifecycle extends com.android.server.SystemService {
        private com.android.server.usage.StorageStatsService mService;

        public Lifecycle(android.content.Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            this.mService = new com.android.server.usage.StorageStatsService(getContext());
            publishBinderService("storagestats", this.mService);
        }
    }

    public StorageStatsService(android.content.Context context) {
        this.mContext = (android.content.Context) com.android.internal.util.Preconditions.checkNotNull(context);
        this.mAppOps = (android.app.AppOpsManager) com.android.internal.util.Preconditions.checkNotNull((android.app.AppOpsManager) context.getSystemService(android.app.AppOpsManager.class));
        this.mUser = (android.os.UserManager) com.android.internal.util.Preconditions.checkNotNull((android.os.UserManager) context.getSystemService(android.os.UserManager.class));
        this.mPackage = (android.content.pm.PackageManager) com.android.internal.util.Preconditions.checkNotNull(context.getPackageManager());
        this.mStorage = (android.os.storage.StorageManager) com.android.internal.util.Preconditions.checkNotNull((android.os.storage.StorageManager) context.getSystemService(android.os.storage.StorageManager.class));
        this.mInstaller = new com.android.server.pm.Installer(context);
        this.mInstaller.onStart();
        invalidateMounts();
        this.mHandler = new com.android.server.usage.StorageStatsService.H(com.android.server.IoThread.get().getLooper());
        this.mHandler.sendEmptyMessage(101);
        this.mStorage.registerListener(new android.os.storage.StorageEventListener() { // from class: com.android.server.usage.StorageStatsService.1
            public void onVolumeStateChanged(android.os.storage.VolumeInfo volumeInfo, int i, int i2) {
                switch (volumeInfo.type) {
                    case 0:
                    case 1:
                    case 2:
                        if (i2 == 2) {
                            com.android.server.usage.StorageStatsService.this.invalidateMounts();
                            break;
                        }
                        break;
                }
            }
        });
        com.android.server.LocalManagerRegistry.addManager(com.android.server.usage.StorageStatsManagerLocal.class, new com.android.server.usage.StorageStatsService.LocalService());
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_FULLY_REMOVED");
        intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        this.mContext.registerReceiver(new android.content.BroadcastReceiver() { // from class: com.android.server.usage.StorageStatsService.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                java.lang.String action = intent.getAction();
                if ("android.intent.action.PACKAGE_REMOVED".equals(action) || "android.intent.action.PACKAGE_FULLY_REMOVED".equals(action)) {
                    com.android.server.usage.StorageStatsService.this.mHandler.removeMessages(103);
                    com.android.server.usage.StorageStatsService.this.mHandler.sendEmptyMessage(103);
                }
            }
        }, intentFilter);
        updateConfig();
        android.provider.DeviceConfig.addOnPropertiesChangedListener("storage_native_boot", this.mContext.getMainExecutor(), new android.provider.DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.usage.StorageStatsService$$ExternalSyntheticLambda3
            public final void onPropertiesChanged(android.provider.DeviceConfig.Properties properties) {
                com.android.server.usage.StorageStatsService.this.lambda$new$0(properties);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(android.provider.DeviceConfig.Properties properties) {
        updateConfig();
    }

    private void updateConfig() {
        synchronized (this.mLock) {
            this.mStorageThresholdPercentHigh = android.provider.DeviceConfig.getInt("storage_native_boot", "storage_threshold_percent_high", 20);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invalidateMounts() {
        try {
            this.mInstaller.invalidateMounts();
        } catch (com.android.server.pm.Installer.InstallerException e) {
            android.util.Slog.wtf(TAG, "Failed to invalidate mounts", e);
        }
    }

    private void enforceStatsPermission(int i, java.lang.String str) {
        java.lang.String checkStatsPermission = checkStatsPermission(i, str, true);
        if (checkStatsPermission != null) {
            throw new java.lang.SecurityException(checkStatsPermission);
        }
    }

    private java.lang.String checkStatsPermission(int i, java.lang.String str, boolean z) {
        int noteOp = z ? this.mAppOps.noteOp(43, i, str) : this.mAppOps.checkOp(43, i, str);
        switch (noteOp) {
            case 0:
                return null;
            case 3:
                if (this.mContext.checkCallingOrSelfPermission("android.permission.PACKAGE_USAGE_STATS") == 0) {
                    return null;
                }
                return "Caller does not have android.permission.PACKAGE_USAGE_STATS; callingPackage=" + str + ", callingUid=" + i;
            default:
                return "Package " + str + " from UID " + i + " blocked by mode " + noteOp;
        }
    }

    public boolean isQuotaSupported(java.lang.String str, java.lang.String str2) {
        try {
            return this.mInstaller.isQuotaSupported(str);
        } catch (com.android.server.pm.Installer.InstallerException e) {
            throw new android.os.ParcelableException(new java.io.IOException(e.getMessage()));
        }
    }

    public boolean isReservedSupported(java.lang.String str, java.lang.String str2) {
        if (str == android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL) {
            return android.os.SystemProperties.getBoolean("vold.has_reserved", false) || android.os.Build.IS_ARC;
        }
        return false;
    }

    public long getTotalBytes(java.lang.String str, java.lang.String str2) {
        if (str == android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL) {
            long primaryStorageSize = this.mStorage.getPrimaryStorageSize();
            if (primaryStorageSize <= android.util.DataUnit.GIGABYTES.toBytes(512L)) {
                return primaryStorageSize;
            }
            long internalStorageBlockDeviceSize = this.mStorage.getInternalStorageBlockDeviceSize();
            long roundStorageSize = android.os.FileUtils.roundStorageSize(internalStorageBlockDeviceSize);
            if (roundStorageSize - internalStorageBlockDeviceSize <= android.util.DataUnit.GIGABYTES.toBytes(3L)) {
                return roundStorageSize;
            }
            return internalStorageBlockDeviceSize;
        }
        android.os.storage.VolumeInfo findVolumeByUuid = this.mStorage.findVolumeByUuid(str);
        if (findVolumeByUuid == null) {
            throw new android.os.ParcelableException(new java.io.IOException("Failed to find storage device for UUID " + str));
        }
        return android.os.FileUtils.roundStorageSize(findVolumeByUuid.disk.size);
    }

    public long getFreeBytes(java.lang.String str, java.lang.String str2) {
        long usableSpace;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                java.io.File findPathForUuid = this.mStorage.findPathForUuid(str);
                if (isQuotaSupported(str, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME)) {
                    usableSpace = findPathForUuid.getUsableSpace() + java.lang.Math.max(0L, getCacheBytes(str, com.android.server.pm.PackageManagerService.PLATFORM_PACKAGE_NAME) - this.mStorage.getStorageCacheBytes(findPathForUuid, 0));
                } else {
                    usableSpace = findPathForUuid.getUsableSpace();
                }
                android.util.Slog.d(TAG, "getFreeBytes: " + usableSpace);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return usableSpace;
            } catch (java.io.FileNotFoundException e) {
                throw new android.os.ParcelableException(e);
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public long getCacheBytes(java.lang.String str, java.lang.String str2) {
        enforceStatsPermission(android.os.Binder.getCallingUid(), str2);
        java.util.Iterator it = this.mUser.getUsers().iterator();
        long j = 0;
        while (it.hasNext()) {
            j += queryStatsForUser(str, ((android.content.pm.UserInfo) it.next()).id, null).cacheBytes;
        }
        return j;
    }

    public long getCacheQuotaBytes(java.lang.String str, int i, java.lang.String str2) {
        enforceStatsPermission(android.os.Binder.getCallingUid(), str2);
        if (this.mCacheQuotas.containsKey(str)) {
            return this.mCacheQuotas.get(str).get(i, DEFAULT_QUOTA);
        }
        return DEFAULT_QUOTA;
    }

    public android.app.usage.StorageStats queryStatsForPackage(java.lang.String str, final java.lang.String str2, int i, java.lang.String str3) {
        final boolean z;
        java.lang.String[] strArr;
        if (i != android.os.UserHandle.getCallingUserId()) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS", TAG);
        }
        try {
            android.content.pm.ApplicationInfo applicationInfoAsUser = this.mPackage.getApplicationInfoAsUser(str2, 8192, i);
            if (android.os.Binder.getCallingUid() == applicationInfoAsUser.uid) {
                z = checkStatsPermission(android.os.Binder.getCallingUid(), str3, false) == null;
            } else {
                enforceStatsPermission(android.os.Binder.getCallingUid(), str3);
                z = true;
            }
            if (com.android.internal.util.ArrayUtils.defeatNullable(this.mPackage.getPackagesForUid(applicationInfoAsUser.uid)).length == 1) {
                return queryStatsForUid(str, applicationInfoAsUser.uid, str3);
            }
            int appId = android.os.UserHandle.getAppId(applicationInfoAsUser.uid);
            java.lang.String[] strArr2 = {str2};
            long[] jArr = new long[1];
            java.lang.String[] strArr3 = new java.lang.String[0];
            if ((!applicationInfoAsUser.isSystemApp() || applicationInfoAsUser.isUpdatedSystemApp()) && applicationInfoAsUser.getCodePath() != null) {
                strArr = (java.lang.String[]) com.android.internal.util.ArrayUtils.appendElement(java.lang.String.class, strArr3, applicationInfoAsUser.getCodePath());
            } else {
                strArr = strArr3;
            }
            final android.content.pm.PackageStats packageStats = new android.content.pm.PackageStats(TAG);
            try {
                this.mInstaller.getAppSize(str, strArr2, i, 0, appId, jArr, strArr, packageStats);
                if (str == android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL) {
                    final android.os.UserHandle of = android.os.UserHandle.of(i);
                    forEachStorageStatsAugmenter(new java.util.function.Consumer() { // from class: com.android.server.usage.StorageStatsService$$ExternalSyntheticLambda2
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            ((com.android.server.usage.StorageStatsManagerLocal.StorageStatsAugmenter) obj).augmentStatsForPackageForUser(packageStats, str2, of, z);
                        }
                    }, "queryStatsForPackage");
                }
                return translate(packageStats);
            } catch (com.android.server.pm.Installer.InstallerException e) {
                throw new android.os.ParcelableException(new java.io.IOException(e.getMessage()));
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
            throw new android.os.ParcelableException(e2);
        }
    }

    public android.app.usage.StorageStats queryStatsForUid(java.lang.String str, final int i, java.lang.String str2) {
        final boolean z;
        final android.content.pm.PackageStats packageStats;
        int userId = android.os.UserHandle.getUserId(i);
        int appId = android.os.UserHandle.getAppId(i);
        if (userId != android.os.UserHandle.getCallingUserId()) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS", TAG);
        }
        if (android.os.Binder.getCallingUid() != i) {
            enforceStatsPermission(android.os.Binder.getCallingUid(), str2);
            z = true;
        } else {
            z = checkStatsPermission(android.os.Binder.getCallingUid(), str2, false) == null;
        }
        java.lang.String[] defeatNullable = com.android.internal.util.ArrayUtils.defeatNullable(this.mPackage.getPackagesForUid(i));
        long[] jArr = new long[defeatNullable.length];
        android.content.pm.PackageStats packageStats2 = new android.content.pm.PackageStats(TAG);
        java.lang.String[] strArr = new java.lang.String[0];
        for (int i2 = 0; i2 < defeatNullable.length; i2++) {
            try {
                android.content.pm.ApplicationInfo applicationInfoAsUser = this.mPackage.getApplicationInfoAsUser(defeatNullable[i2], 8192, userId);
                if (!applicationInfoAsUser.isSystemApp() || applicationInfoAsUser.isUpdatedSystemApp()) {
                    if (applicationInfoAsUser.getCodePath() != null) {
                        strArr = (java.lang.String[]) com.android.internal.util.ArrayUtils.appendElement(java.lang.String.class, strArr, applicationInfoAsUser.getCodePath());
                    }
                    if (android.app.usage.Flags.getAppBytesByDataTypeApi()) {
                        computeAppStatsByDataTypes(packageStats2, applicationInfoAsUser.sourceDir, defeatNullable[i2]);
                    }
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                throw new android.os.ParcelableException(e);
            }
        }
        try {
            java.lang.String[] strArr2 = strArr;
            this.mInstaller.getAppSize(str, defeatNullable, userId, getDefaultFlags(), appId, jArr, strArr2, packageStats2);
            if (!android.os.SystemProperties.getBoolean(PROP_VERIFY_STORAGE, false)) {
                packageStats = packageStats2;
            } else {
                android.content.pm.PackageStats packageStats3 = new android.content.pm.PackageStats(TAG);
                this.mInstaller.getAppSize(str, defeatNullable, userId, 0, appId, jArr, strArr2, packageStats3);
                packageStats = packageStats2;
                checkEquals("UID " + i, packageStats3, packageStats);
            }
            if (str == android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL) {
                forEachStorageStatsAugmenter(new java.util.function.Consumer() { // from class: com.android.server.usage.StorageStatsService$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((com.android.server.usage.StorageStatsManagerLocal.StorageStatsAugmenter) obj).augmentStatsForUid(packageStats, i, z);
                    }
                }, "queryStatsForUid");
            }
            return translate(packageStats);
        } catch (com.android.server.pm.Installer.InstallerException e2) {
            throw new android.os.ParcelableException(new java.io.IOException(e2.getMessage()));
        }
    }

    public android.app.usage.StorageStats queryStatsForUser(java.lang.String str, int i, java.lang.String str2) {
        if (i != android.os.UserHandle.getCallingUserId()) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS", TAG);
        }
        enforceStatsPermission(android.os.Binder.getCallingUid(), str2);
        int[] appIds = getAppIds(i);
        final android.content.pm.PackageStats packageStats = new android.content.pm.PackageStats(TAG);
        try {
            this.mInstaller.getUserSize(str, i, getDefaultFlags(), appIds, packageStats);
            if (android.os.SystemProperties.getBoolean(PROP_VERIFY_STORAGE, false)) {
                android.content.pm.PackageStats packageStats2 = new android.content.pm.PackageStats(TAG);
                this.mInstaller.getUserSize(str, i, 0, appIds, packageStats2);
                checkEquals("User " + i, packageStats2, packageStats);
            }
            if (str == android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL) {
                final android.os.UserHandle of = android.os.UserHandle.of(i);
                forEachStorageStatsAugmenter(new java.util.function.Consumer() { // from class: com.android.server.usage.StorageStatsService$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        ((com.android.server.usage.StorageStatsManagerLocal.StorageStatsAugmenter) obj).augmentStatsForUser(packageStats, of);
                    }
                }, "queryStatsForUser");
            }
            return translate(packageStats);
        } catch (com.android.server.pm.Installer.InstallerException e) {
            throw new android.os.ParcelableException(new java.io.IOException(e.getMessage()));
        }
    }

    public android.app.usage.ExternalStorageStats queryExternalStatsForUser(java.lang.String str, int i, java.lang.String str2) {
        if (i != android.os.UserHandle.getCallingUserId()) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS", TAG);
        }
        enforceStatsPermission(android.os.Binder.getCallingUid(), str2);
        int[] appIds = getAppIds(i);
        try {
            long[] externalSize = this.mInstaller.getExternalSize(str, i, getDefaultFlags(), appIds);
            if (android.os.SystemProperties.getBoolean(PROP_VERIFY_STORAGE, false)) {
                checkEquals("External " + i, this.mInstaller.getExternalSize(str, i, 0, appIds), externalSize);
            }
            android.app.usage.ExternalStorageStats externalStorageStats = new android.app.usage.ExternalStorageStats();
            externalStorageStats.totalBytes = externalSize[0];
            externalStorageStats.audioBytes = externalSize[1];
            externalStorageStats.videoBytes = externalSize[2];
            externalStorageStats.imageBytes = externalSize[3];
            externalStorageStats.appBytes = externalSize[4];
            externalStorageStats.obbBytes = externalSize[5];
            return externalStorageStats;
        } catch (com.android.server.pm.Installer.InstallerException e) {
            throw new android.os.ParcelableException(new java.io.IOException(e.getMessage()));
        }
    }

    private int[] getAppIds(int i) {
        java.util.Iterator it = this.mPackage.getInstalledApplicationsAsUser(8192, i).iterator();
        int[] iArr = null;
        while (it.hasNext()) {
            int appId = android.os.UserHandle.getAppId(((android.content.pm.ApplicationInfo) it.next()).uid);
            if (!com.android.internal.util.ArrayUtils.contains(iArr, appId)) {
                iArr = com.android.internal.util.ArrayUtils.appendInt(iArr, appId);
            }
        }
        return iArr;
    }

    private static int getDefaultFlags() {
        return android.os.SystemProperties.getBoolean(PROP_DISABLE_QUOTA, false) ? 0 : 4096;
    }

    private static void checkEquals(java.lang.String str, long[] jArr, long[] jArr2) {
        for (int i = 0; i < jArr.length; i++) {
            checkEquals(str + "[" + i + "]", jArr[i], jArr2[i]);
        }
    }

    private static void checkEquals(java.lang.String str, android.content.pm.PackageStats packageStats, android.content.pm.PackageStats packageStats2) {
        checkEquals(str + " codeSize", packageStats.codeSize, packageStats2.codeSize);
        checkEquals(str + " dataSize", packageStats.dataSize, packageStats2.dataSize);
        checkEquals(str + " cacheSize", packageStats.cacheSize, packageStats2.cacheSize);
        checkEquals(str + " externalCodeSize", packageStats.externalCodeSize, packageStats2.externalCodeSize);
        checkEquals(str + " externalDataSize", packageStats.externalDataSize, packageStats2.externalDataSize);
        checkEquals(str + " externalCacheSize", packageStats.externalCacheSize, packageStats2.externalCacheSize);
    }

    private static void checkEquals(java.lang.String str, long j, long j2) {
        if (j != j2) {
            android.util.Slog.e(TAG, str + " expected " + j + " actual " + j2);
        }
    }

    private static android.app.usage.StorageStats translate(android.content.pm.PackageStats packageStats) {
        android.app.usage.StorageStats storageStats = new android.app.usage.StorageStats();
        storageStats.codeBytes = packageStats.codeSize + packageStats.externalCodeSize;
        storageStats.dataBytes = packageStats.dataSize + packageStats.externalDataSize;
        storageStats.cacheBytes = packageStats.cacheSize + packageStats.externalCacheSize;
        storageStats.dexoptBytes = packageStats.dexoptSize;
        storageStats.curProfBytes = packageStats.curProfSize;
        storageStats.refProfBytes = packageStats.refProfSize;
        storageStats.apkBytes = packageStats.apkSize;
        storageStats.libBytes = packageStats.libSize;
        storageStats.dmBytes = packageStats.dmSize;
        storageStats.externalCacheBytes = packageStats.externalCacheSize;
        return storageStats;
    }

    private class H extends android.os.Handler {
        private static final boolean DEBUG = false;
        private static final long MINIMUM_CHANGE_DELTA_PERCENT_HIGH = 5;
        private static final long MINIMUM_CHANGE_DELTA_PERCENT_LOW = 2;
        private static final int MSG_CHECK_STORAGE_DELTA = 100;
        private static final int MSG_LOAD_CACHED_QUOTAS_FROM_FILE = 101;
        private static final int MSG_PACKAGE_REMOVED = 103;
        private static final int MSG_RECALCULATE_QUOTAS = 102;
        private static final int UNSET = -1;
        private long mPreviousBytes;
        private final android.os.StatFs mStats;
        private long mTotalBytes;

        public H(android.os.Looper looper) {
            super(looper);
            this.mStats = new android.os.StatFs(android.os.Environment.getDataDirectory().getAbsolutePath());
            this.mPreviousBytes = this.mStats.getAvailableBytes();
            this.mTotalBytes = this.mStats.getTotalBytes();
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            long j;
            if (!com.android.server.usage.StorageStatsService.isCacheQuotaCalculationsEnabled(com.android.server.usage.StorageStatsService.this.mContext.getContentResolver())) {
                return;
            }
            switch (message.what) {
                case 100:
                    this.mStats.restat(android.os.Environment.getDataDirectory().getAbsolutePath());
                    long abs = java.lang.Math.abs(this.mPreviousBytes - this.mStats.getAvailableBytes());
                    synchronized (com.android.server.usage.StorageStatsService.this.mLock) {
                        try {
                            if (this.mStats.getAvailableBytes() > (this.mTotalBytes * com.android.server.usage.StorageStatsService.this.mStorageThresholdPercentHigh) / 100) {
                                j = (this.mTotalBytes * MINIMUM_CHANGE_DELTA_PERCENT_HIGH) / 100;
                            } else {
                                j = (this.mTotalBytes * 2) / 100;
                            }
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                    if (abs > j) {
                        this.mPreviousBytes = this.mStats.getAvailableBytes();
                        recalculateQuotas(getInitializedStrategy());
                        com.android.server.usage.StorageStatsService.this.notifySignificantDelta();
                    }
                    sendEmptyMessageDelayed(100, 30000L);
                    return;
                case 101:
                    com.android.server.storage.CacheQuotaStrategy initializedStrategy = getInitializedStrategy();
                    this.mPreviousBytes = -1L;
                    try {
                        this.mPreviousBytes = initializedStrategy.setupQuotasFromFile();
                    } catch (java.io.IOException e) {
                        android.util.Slog.e(com.android.server.usage.StorageStatsService.TAG, "An error occurred while reading the cache quota file.", e);
                    } catch (java.lang.IllegalStateException e2) {
                        android.util.Slog.e(com.android.server.usage.StorageStatsService.TAG, "Cache quota XML file is malformed?", e2);
                    }
                    if (this.mPreviousBytes < 0) {
                        this.mStats.restat(android.os.Environment.getDataDirectory().getAbsolutePath());
                        this.mPreviousBytes = this.mStats.getAvailableBytes();
                        recalculateQuotas(initializedStrategy);
                    }
                    sendEmptyMessageDelayed(100, 30000L);
                    sendEmptyMessageDelayed(102, com.android.server.usage.StorageStatsService.DELAY_RECALCULATE_QUOTAS);
                    return;
                case 102:
                    recalculateQuotas(getInitializedStrategy());
                    sendEmptyMessageDelayed(102, com.android.server.usage.StorageStatsService.DELAY_RECALCULATE_QUOTAS);
                    return;
                case 103:
                    recalculateQuotas(getInitializedStrategy());
                    return;
                default:
                    return;
            }
        }

        private void recalculateQuotas(com.android.server.storage.CacheQuotaStrategy cacheQuotaStrategy) {
            cacheQuotaStrategy.recalculateQuotas();
        }

        private com.android.server.storage.CacheQuotaStrategy getInitializedStrategy() {
            return new com.android.server.storage.CacheQuotaStrategy(com.android.server.usage.StorageStatsService.this.mContext, (android.app.usage.UsageStatsManagerInternal) com.android.server.LocalServices.getService(android.app.usage.UsageStatsManagerInternal.class), com.android.server.usage.StorageStatsService.this.mInstaller, com.android.server.usage.StorageStatsService.this.mCacheQuotas);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    static boolean isCacheQuotaCalculationsEnabled(android.content.ContentResolver contentResolver) {
        return android.provider.Settings.Global.getInt(contentResolver, "enable_cache_quota_calculation", 1) != 0;
    }

    void notifySignificantDelta() {
        this.mContext.getContentResolver().notifyChange(android.net.Uri.parse("content://com.android.externalstorage.documents/"), (android.database.ContentObserver) null, false);
    }

    private static void checkCratesEnable() {
        if (!android.os.SystemProperties.getBoolean(PROP_STORAGE_CRATES, false)) {
            throw new java.lang.IllegalStateException("Storage Crate feature is disabled.");
        }
    }

    private void enforceCratesPermission(int i, java.lang.String str) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.MANAGE_CRATES", str);
    }

    @android.annotation.NonNull
    private static java.util.List<android.os.storage.CrateInfo> convertCrateInfoFrom(@android.annotation.Nullable android.os.storage.CrateMetadata[] crateMetadataArr) {
        android.os.storage.CrateInfo copyFrom;
        if (com.android.internal.util.ArrayUtils.isEmpty(crateMetadataArr)) {
            return java.util.Collections.EMPTY_LIST;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.os.storage.CrateMetadata crateMetadata : crateMetadataArr) {
            if (crateMetadata != null && !android.text.TextUtils.isEmpty(crateMetadata.id) && !android.text.TextUtils.isEmpty(crateMetadata.packageName) && (copyFrom = android.os.storage.CrateInfo.copyFrom(crateMetadata.uid, crateMetadata.packageName, crateMetadata.id)) != null) {
                arrayList.add(copyFrom);
            }
        }
        return arrayList;
    }

    @android.annotation.NonNull
    private android.content.pm.ParceledListSlice<android.os.storage.CrateInfo> getAppCrates(java.lang.String str, java.lang.String[] strArr, int i) {
        try {
            return new android.content.pm.ParceledListSlice<>(convertCrateInfoFrom(this.mInstaller.getAppCrates(str, strArr, i)));
        } catch (com.android.server.pm.Installer.InstallerException e) {
            throw new android.os.ParcelableException(new java.io.IOException(e.getMessage()));
        }
    }

    @android.annotation.NonNull
    public android.content.pm.ParceledListSlice<android.os.storage.CrateInfo> queryCratesForPackage(java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i, @android.annotation.NonNull java.lang.String str3) {
        checkCratesEnable();
        if (i != android.os.UserHandle.getCallingUserId()) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS", TAG);
        }
        try {
            if (android.os.Binder.getCallingUid() != this.mPackage.getApplicationInfoAsUser(str2, 8192, i).uid) {
                enforceCratesPermission(android.os.Binder.getCallingUid(), str3);
            }
            return getAppCrates(str, new java.lang.String[]{str2}, i);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            throw new android.os.ParcelableException(e);
        }
    }

    @android.annotation.NonNull
    public android.content.pm.ParceledListSlice<android.os.storage.CrateInfo> queryCratesForUid(java.lang.String str, int i, @android.annotation.NonNull java.lang.String str2) {
        checkCratesEnable();
        int userId = android.os.UserHandle.getUserId(i);
        if (userId != android.os.UserHandle.getCallingUserId()) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS", TAG);
        }
        if (android.os.Binder.getCallingUid() != i) {
            enforceCratesPermission(android.os.Binder.getCallingUid(), str2);
        }
        java.lang.String[] strArr = new java.lang.String[0];
        for (java.lang.String str3 : com.android.internal.util.ArrayUtils.defeatNullable(this.mPackage.getPackagesForUid(i))) {
            if (!android.text.TextUtils.isEmpty(str3)) {
                try {
                    if (this.mPackage.getApplicationInfoAsUser(str3, 8192, userId) != null) {
                        strArr = (java.lang.String[]) com.android.internal.util.ArrayUtils.appendElement(java.lang.String.class, strArr, str3);
                    }
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    throw new android.os.ParcelableException(e);
                }
            }
        }
        return getAppCrates(str, strArr, userId);
    }

    @android.annotation.NonNull
    public android.content.pm.ParceledListSlice<android.os.storage.CrateInfo> queryCratesForUser(java.lang.String str, int i, @android.annotation.NonNull java.lang.String str2) {
        checkCratesEnable();
        if (i != android.os.UserHandle.getCallingUserId()) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS", TAG);
        }
        enforceCratesPermission(android.os.Binder.getCallingUid(), str2);
        try {
            return new android.content.pm.ParceledListSlice<>(convertCrateInfoFrom(this.mInstaller.getUserCrates(str, i)));
        } catch (com.android.server.pm.Installer.InstallerException e) {
            throw new android.os.ParcelableException(new java.io.IOException(e.getMessage()));
        }
    }

    void forEachStorageStatsAugmenter(@android.annotation.NonNull java.util.function.Consumer<com.android.server.usage.StorageStatsManagerLocal.StorageStatsAugmenter> consumer, @android.annotation.NonNull java.lang.String str) {
        int size = this.mStorageStatsAugmenters.size();
        for (int i = 0; i < size; i++) {
            android.util.Pair<java.lang.String, com.android.server.usage.StorageStatsManagerLocal.StorageStatsAugmenter> pair = this.mStorageStatsAugmenters.get(i);
            java.lang.String str2 = (java.lang.String) pair.first;
            com.android.server.usage.StorageStatsManagerLocal.StorageStatsAugmenter storageStatsAugmenter = (com.android.server.usage.StorageStatsManagerLocal.StorageStatsAugmenter) pair.second;
            android.os.Trace.traceBegin(524288L, str + ":" + str2);
            try {
                consumer.accept(storageStatsAugmenter);
                android.os.Trace.traceEnd(524288L);
            } catch (java.lang.Throwable th) {
                android.os.Trace.traceEnd(524288L);
                throw th;
            }
        }
    }

    private class LocalService implements com.android.server.usage.StorageStatsManagerLocal {
        private LocalService() {
        }

        @Override // com.android.server.usage.StorageStatsManagerLocal
        public void registerStorageStatsAugmenter(@android.annotation.NonNull com.android.server.usage.StorageStatsManagerLocal.StorageStatsAugmenter storageStatsAugmenter, @android.annotation.NonNull java.lang.String str) {
            com.android.server.usage.StorageStatsService.this.mStorageStatsAugmenters.add(android.util.Pair.create(str, storageStatsAugmenter));
        }
    }

    private long getDirBytes(java.io.File file) {
        long j = 0;
        if (!file.isDirectory()) {
            return 0L;
        }
        try {
            for (java.io.File file2 : file.listFiles()) {
                if (file2.isFile()) {
                    j += file2.length();
                } else if (file2.isDirectory()) {
                    j += getDirBytes(file2);
                }
            }
        } catch (java.lang.NullPointerException e) {
            android.util.Slog.w(TAG, "Failed to list directory " + file.getName());
        }
        return j;
    }

    private long getFileBytesInDir(java.io.File file, java.lang.String str) {
        long j = 0;
        if (!file.isDirectory()) {
            return 0L;
        }
        try {
            for (java.io.File file2 : file.listFiles()) {
                if (file2.isFile() && file2.getName().endsWith(str)) {
                    j += file2.length();
                }
            }
        } catch (java.lang.NullPointerException e) {
            android.util.Slog.w(TAG, "Failed to list directory " + file.getName());
        }
        return j;
    }

    private void computeAppStatsByDataTypes(android.content.pm.PackageStats packageStats, java.lang.String str, java.lang.String str2) {
        java.io.File file = new java.io.File(str);
        if (file.isFile()) {
            str = file.getParent();
            file = new java.io.File(str);
        }
        packageStats.apkSize += getFileBytesInDir(file, ".apk");
        packageStats.dmSize += getFileBytesInDir(file, ".dm");
        packageStats.libSize = packageStats.libSize + getDirBytes(new java.io.File(str + "/lib/"));
        if (android.os.SystemProperties.getBoolean("dalvik.vm.features.art_managed_file_stats", false)) {
            com.android.server.pm.PackageManagerLocal.FilteredSnapshot withFilteredSnapshot = com.android.server.pm.PackageManagerServiceUtils.getPackageManagerLocal().withFilteredSnapshot();
            try {
                com.android.server.art.model.ArtManagedFileStats artManagedFileStats = com.android.server.pm.DexOptHelper.getArtManagerLocal().getArtManagedFileStats(withFilteredSnapshot, str2);
                if (withFilteredSnapshot != null) {
                    withFilteredSnapshot.close();
                }
                packageStats.dexoptSize += artManagedFileStats.getTotalSizeBytesByType(0);
                packageStats.refProfSize += artManagedFileStats.getTotalSizeBytesByType(1);
                packageStats.curProfSize += artManagedFileStats.getTotalSizeBytesByType(2);
            } catch (java.lang.Throwable th) {
                if (withFilteredSnapshot != null) {
                    try {
                        withFilteredSnapshot.close();
                    } catch (java.lang.Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
    }
}
