package com.android.server;

/* loaded from: classes.dex */
class StorageManagerService extends android.os.storage.IStorageManager.Stub implements com.android.server.Watchdog.Monitor, com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver {
    private static final java.lang.String ANDROID_VOLD_APP_DATA_ISOLATION_ENABLED_PROPERTY = "persist.sys.vold_app_data_isolation_enabled";
    private static final java.lang.String ANR_DELAY_MILLIS_DEVICE_CONFIG_KEY = "anr_delay_millis";
    private static final java.lang.String ANR_DELAY_NOTIFY_EXTERNAL_STORAGE_SERVICE_DEVICE_CONFIG_KEY = "anr_delay_notify_external_storage_service";
    private static final java.lang.String ATTR_CREATED_MILLIS = "createdMillis";
    private static final java.lang.String ATTR_FS_UUID = "fsUuid";
    private static final java.lang.String ATTR_LAST_BENCH_MILLIS = "lastBenchMillis";
    private static final java.lang.String ATTR_LAST_SEEN_MILLIS = "lastSeenMillis";
    private static final java.lang.String ATTR_LAST_TRIM_MILLIS = "lastTrimMillis";
    private static final java.lang.String ATTR_NICKNAME = "nickname";
    private static final java.lang.String ATTR_PART_GUID = "partGuid";
    private static final java.lang.String ATTR_PRIMARY_STORAGE_UUID = "primaryStorageUuid";
    private static final java.lang.String ATTR_TYPE = "type";
    private static final java.lang.String ATTR_USER_FLAGS = "userFlags";
    private static final java.lang.String ATTR_VERSION = "version";
    private static final boolean DEBUG_OBB = false;
    private static final boolean DEFAULT_CHARGING_REQUIRED = true;
    private static final float DEFAULT_DIRTY_RECLAIM_RATE = 0.5f;
    private static final int DEFAULT_LIFETIME_PERCENT_THRESHOLD = 70;
    private static final float DEFAULT_LOW_BATTERY_LEVEL = 20.0f;
    private static final int DEFAULT_MIN_GC_SLEEPTIME = 10000;
    private static final int DEFAULT_MIN_SEGMENTS_THRESHOLD = 512;
    private static final float DEFAULT_SEGMENT_RECLAIM_WEIGHT = 1.0f;
    private static final boolean DEFAULT_SMART_IDLE_MAINT_ENABLED = false;
    private static final int DEFAULT_SMART_IDLE_MAINT_PERIOD = 60;
    private static final int DEFAULT_TARGET_DIRTY_RATIO = 80;
    public static final int FAILED_MOUNT_RESET_TIMEOUT_SECONDS = 10;
    private static final int H_ABORT_IDLE_MAINT = 12;
    private static final int H_BOOT_COMPLETED = 13;
    private static final int H_CLOUD_MEDIA_PROVIDER_CHANGED = 16;
    private static final int H_COMPLETE_UNLOCK_USER = 14;
    private static final int H_DAEMON_CONNECTED = 2;
    private static final int H_FSTRIM = 4;
    private static final int H_INTERNAL_BROADCAST = 7;
    private static final int H_PARTITION_FORGET = 9;
    private static final int H_REMOUNT_VOLUMES_ON_MOVE = 18;
    private static final int H_RESET = 10;
    private static final int H_RUN_IDLE_MAINT = 11;
    private static final int H_SECURE_KEYGUARD_STATE_CHANGED = 17;
    private static final int H_SHUTDOWN = 3;
    private static final int H_SYSTEM_READY = 1;
    private static final int H_VOLUME_BROADCAST = 6;
    private static final int H_VOLUME_MOUNT = 5;
    private static final int H_VOLUME_STATE_CHANGED = 15;
    private static final int H_VOLUME_UNMOUNT = 8;
    private static final java.lang.String LAST_FSTRIM_FILE = "last-fstrim";
    private static final int MAX_PERIOD_WRITE_RECORD = 4320;
    private static final int MAX_SMART_IDLE_MAINT_PERIOD = 1440;
    private static final int MIN_SMART_IDLE_MAINT_PERIOD = 10;
    private static final int MOVE_STATUS_COPY_FINISHED = 82;
    private static final int OBB_FLUSH_MOUNT_STATE = 2;
    private static final int OBB_RUN_ACTION = 1;
    private static final int PARTITION_OPERATION_WATCHDOG_TIMEOUT_MS = 180000;
    private static final int SLOW_OPERATION_WATCHDOG_TIMEOUT_MS = 20000;
    private static final java.lang.String TAG_STORAGE_BENCHMARK = "storage_benchmark";
    private static final java.lang.String TAG_STORAGE_TRIM = "storage_trim";
    private static final java.lang.String TAG_VOLUME = "volume";
    private static final java.lang.String TAG_VOLUMES = "volumes";
    private static final int VERSION_ADD_PRIMARY = 2;
    private static final int VERSION_FIX_PRIMARY = 3;
    private static final int VERSION_INIT = 1;
    private static final boolean WATCHDOG_ENABLE = true;
    private static final java.lang.String ZRAM_ENABLED_PROPERTY = "persist.sys.zram_enabled";

    @android.annotation.Nullable
    public static java.lang.String sMediaStoreAuthorityProcessName;
    private final com.android.server.StorageManagerService.Callbacks mCallbacks;
    private volatile boolean mChargingRequired;
    private final android.content.Context mContext;
    private volatile float mDirtyReclaimRate;
    private final android.os.Handler mHandler;
    private com.android.internal.app.IAppOpsService mIAppOpsService;
    private android.content.pm.IPackageManager mIPackageManager;
    private final com.android.server.pm.Installer mInstaller;
    private long mLastMaintenance;
    private final java.io.File mLastMaintenanceFile;
    private volatile int mLifetimePercentThreshold;
    private volatile float mLowBatteryLevel;
    private volatile int mMaxWriteRecords;
    private volatile int mMinGCSleepTime;
    private volatile int mMinSegmentsThreshold;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.content.pm.IPackageMoveObserver mMoveCallback;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.lang.String mMoveTargetUuid;
    private final com.android.server.StorageManagerService.ObbActionHandler mObbActionHandler;
    private volatile boolean mPassedLifetimeThresh;
    private android.content.pm.PackageManagerInternal mPmInternal;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private java.lang.String mPrimaryStorageUuid;
    private volatile float mSegmentReclaimWeight;
    private final android.util.AtomicFile mSettingsFile;
    private final com.android.server.storage.StorageSessionController mStorageSessionController;
    private volatile int[] mStorageWriteRecords;
    private volatile android.os.IStoraged mStoraged;
    private volatile int mTargetDirtyRatio;
    private volatile android.os.IVold mVold;
    private final boolean mVoldAppDataIsolationEnabled;
    private final android.util.AtomicFile mWriteRecordFile;
    static com.android.server.StorageManagerService sSelf = null;
    private static final java.lang.String TAG = "StorageManagerService";
    private static final boolean LOCAL_LOGV = android.util.Log.isLoggable(TAG, 2);
    static volatile int sSmartIdleMaintPeriod = 60;
    public static final java.util.regex.Pattern KNOWN_APP_DIR_PATHS = java.util.regex.Pattern.compile("(?i)(^/storage/[^/]+/(?:([0-9]+)/)?Android/(?:data|media|obb|sandbox)/)([^/]+)(/.*)?");

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Set<java.lang.Integer> mFuseMountedUser = new android.util.ArraySet();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Set<java.lang.Integer> mCeStoragePreparedUsers = new android.util.ArraySet();
    private volatile long mInternalStorageSize = 0;
    private volatile boolean mNeedGC = true;
    private final java.lang.Object mLock = com.android.server.LockGuard.installNewLock(4);

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.StorageManagerService.WatchedUnlockedUsers mCeUnlockedUsers = new com.android.server.StorageManagerService.WatchedUnlockedUsers();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private int[] mSystemUnlockedUsers = libcore.util.EmptyArray.INT;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.util.ArrayMap<java.lang.String, android.os.storage.DiskInfo> mDisks = new android.util.ArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final android.util.ArrayMap<java.lang.String, android.os.storage.VolumeInfo> mVolumes = new android.util.ArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.util.ArrayMap<java.lang.String, android.os.storage.VolumeRecord> mRecords = new android.util.ArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.util.ArrayMap<java.lang.String, java.util.concurrent.CountDownLatch> mDiskScanLatches = new android.util.ArrayMap<>();

    @com.android.internal.annotations.GuardedBy({"mCloudMediaProviders"})
    private final android.util.SparseArray<java.lang.String> mCloudMediaProviders = new android.util.SparseArray<>();
    private volatile int mMediaStoreAuthorityAppId = -1;
    private volatile int mDownloadsAuthorityAppId = -1;
    private volatile int mExternalStorageAuthorityAppId = -1;
    private volatile int mCurrentUserId = 0;
    private volatile boolean mRemountCurrentUserVolumesOnUnlock = false;
    private final java.lang.Object mAppFuseLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mAppFuseLock"})
    private int mNextAppFuseName = 0;

    @com.android.internal.annotations.GuardedBy({"mAppFuseLock"})
    private com.android.server.storage.AppFuseBridge mAppFuseBridge = null;
    private final android.util.SparseIntArray mUserSharesMediaWith = new android.util.SparseIntArray();
    private volatile boolean mBootCompleted = false;
    private volatile boolean mDaemonConnected = false;
    private volatile boolean mSecureKeyguardShowing = true;
    private final java.util.Map<android.os.IBinder, java.util.List<com.android.server.StorageManagerService.ObbState>> mObbMounts = new java.util.HashMap();
    private final java.util.Map<java.lang.String, com.android.server.StorageManagerService.ObbState> mObbPathToStateMap = new java.util.HashMap();
    private final com.android.server.StorageManagerService.StorageManagerInternalImpl mStorageManagerInternal = new com.android.server.StorageManagerService.StorageManagerInternalImpl();

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private final java.util.Set<java.lang.Integer> mUidsWithLegacyExternalStorage = new android.util.ArraySet();
    private final android.util.SparseArray<com.android.internal.content.PackageMonitor> mPackageMonitorsForUser = new android.util.SparseArray<>();
    private android.content.BroadcastReceiver mUserReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.StorageManagerService.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            java.lang.String action = intent.getAction();
            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
            com.android.internal.util.Preconditions.checkArgument(intExtra >= 0);
            try {
                if ("android.intent.action.USER_ADDED".equals(action)) {
                    android.os.UserManager userManager = (android.os.UserManager) com.android.server.StorageManagerService.this.mContext.getSystemService(android.os.UserManager.class);
                    int userSerialNumber = userManager.getUserSerialNumber(intExtra);
                    android.content.pm.UserInfo userInfo = userManager.getUserInfo(intExtra);
                    if (userInfo.isCloneProfile()) {
                        com.android.server.StorageManagerService.this.mVold.onUserAdded(intExtra, userSerialNumber, userInfo.profileGroupId);
                    } else {
                        com.android.server.StorageManagerService.this.mVold.onUserAdded(intExtra, userSerialNumber, -1);
                    }
                } else if ("android.intent.action.USER_REMOVED".equals(action)) {
                    synchronized (com.android.server.StorageManagerService.this.mLock) {
                        try {
                            int size = com.android.server.StorageManagerService.this.mVolumes.size();
                            for (int i = 0; i < size; i++) {
                                android.os.storage.VolumeInfo volumeInfo = (android.os.storage.VolumeInfo) com.android.server.StorageManagerService.this.mVolumes.valueAt(i);
                                if (volumeInfo.mountUserId == intExtra) {
                                    volumeInfo.mountUserId = com.android.server.am.ProcessList.INVALID_ADJ;
                                    com.android.server.StorageManagerService.this.mHandler.obtainMessage(8, volumeInfo).sendToTarget();
                                }
                            }
                        } finally {
                        }
                    }
                    com.android.server.StorageManagerService.this.mVold.onUserRemoved(intExtra);
                }
            } catch (java.lang.Exception e) {
                android.util.Slog.wtf(com.android.server.StorageManagerService.TAG, e);
            }
        }
    };
    private final android.os.IVoldListener mListener = new android.os.IVoldListener.Stub() { // from class: com.android.server.StorageManagerService.3
        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.os.IVoldListener
        public void onDiskCreated(java.lang.String str, int i) {
            char c;
            synchronized (com.android.server.StorageManagerService.this.mLock) {
                try {
                    java.lang.String str2 = android.os.SystemProperties.get("persist.sys.adoptable");
                    switch (str2.hashCode()) {
                        case 464944051:
                            if (str2.equals("force_on")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1528363547:
                            if (str2.equals("force_off")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
                    switch (c) {
                        case 0:
                            i |= 1;
                            break;
                        case 1:
                            i &= -2;
                            break;
                    }
                    com.android.server.StorageManagerService.this.mDisks.put(str, new android.os.storage.DiskInfo(str, i));
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.os.IVoldListener
        public void onDiskScanned(java.lang.String str) {
            synchronized (com.android.server.StorageManagerService.this.mLock) {
                try {
                    android.os.storage.DiskInfo diskInfo = (android.os.storage.DiskInfo) com.android.server.StorageManagerService.this.mDisks.get(str);
                    if (diskInfo != null) {
                        com.android.server.StorageManagerService.this.onDiskScannedLocked(diskInfo);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.os.IVoldListener
        public void onDiskMetadataChanged(java.lang.String str, long j, java.lang.String str2, java.lang.String str3) {
            synchronized (com.android.server.StorageManagerService.this.mLock) {
                try {
                    android.os.storage.DiskInfo diskInfo = (android.os.storage.DiskInfo) com.android.server.StorageManagerService.this.mDisks.get(str);
                    if (diskInfo != null) {
                        diskInfo.size = j;
                        diskInfo.label = str2;
                        diskInfo.sysPath = str3;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.os.IVoldListener
        public void onDiskDestroyed(java.lang.String str) {
            synchronized (com.android.server.StorageManagerService.this.mLock) {
                try {
                    android.os.storage.DiskInfo diskInfo = (android.os.storage.DiskInfo) com.android.server.StorageManagerService.this.mDisks.remove(str);
                    if (diskInfo != null) {
                        com.android.server.StorageManagerService.this.mCallbacks.notifyDiskDestroyed(diskInfo);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.os.IVoldListener
        public void onVolumeCreated(java.lang.String str, int i, java.lang.String str2, java.lang.String str3, int i2) {
            synchronized (com.android.server.StorageManagerService.this.mLock) {
                android.os.storage.VolumeInfo volumeInfo = new android.os.storage.VolumeInfo(str, i, (android.os.storage.DiskInfo) com.android.server.StorageManagerService.this.mDisks.get(str2), str3);
                volumeInfo.mountUserId = i2;
                com.android.server.StorageManagerService.this.mVolumes.put(str, volumeInfo);
                com.android.server.StorageManagerService.this.onVolumeCreatedLocked(volumeInfo);
            }
        }

        @Override // android.os.IVoldListener
        public void onVolumeStateChanged(java.lang.String str, int i, int i2) {
            synchronized (com.android.server.StorageManagerService.this.mLock) {
                try {
                    android.os.storage.VolumeInfo volumeInfo = (android.os.storage.VolumeInfo) com.android.server.StorageManagerService.this.mVolumes.get(str);
                    if (volumeInfo != null) {
                        int i3 = volumeInfo.state;
                        volumeInfo.state = i;
                        android.os.storage.VolumeInfo volumeInfo2 = new android.os.storage.VolumeInfo(volumeInfo);
                        volumeInfo2.mountUserId = i2;
                        com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
                        obtain.arg1 = volumeInfo2;
                        obtain.argi1 = i3;
                        obtain.argi2 = i;
                        com.android.server.StorageManagerService.this.mHandler.obtainMessage(15, obtain).sendToTarget();
                        com.android.server.StorageManagerService.this.onVolumeStateChangedLocked(volumeInfo2, i);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.os.IVoldListener
        public void onVolumeMetadataChanged(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) {
            synchronized (com.android.server.StorageManagerService.this.mLock) {
                try {
                    android.os.storage.VolumeInfo volumeInfo = (android.os.storage.VolumeInfo) com.android.server.StorageManagerService.this.mVolumes.get(str);
                    if (volumeInfo != null) {
                        volumeInfo.fsType = str2;
                        volumeInfo.fsUuid = str3;
                        volumeInfo.fsLabel = str4;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.os.IVoldListener
        public void onVolumePathChanged(java.lang.String str, java.lang.String str2) {
            synchronized (com.android.server.StorageManagerService.this.mLock) {
                try {
                    android.os.storage.VolumeInfo volumeInfo = (android.os.storage.VolumeInfo) com.android.server.StorageManagerService.this.mVolumes.get(str);
                    if (volumeInfo != null) {
                        volumeInfo.path = str2;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.os.IVoldListener
        public void onVolumeInternalPathChanged(java.lang.String str, java.lang.String str2) {
            synchronized (com.android.server.StorageManagerService.this.mLock) {
                try {
                    android.os.storage.VolumeInfo volumeInfo = (android.os.storage.VolumeInfo) com.android.server.StorageManagerService.this.mVolumes.get(str);
                    if (volumeInfo != null) {
                        volumeInfo.internalPath = str2;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.os.IVoldListener
        public void onVolumeDestroyed(java.lang.String str) {
            android.os.storage.VolumeInfo volumeInfo;
            synchronized (com.android.server.StorageManagerService.this.mLock) {
                volumeInfo = (android.os.storage.VolumeInfo) com.android.server.StorageManagerService.this.mVolumes.remove(str);
            }
            if (volumeInfo != null) {
                com.android.server.StorageManagerService.this.mStorageSessionController.onVolumeRemove(volumeInfo);
                try {
                    if (volumeInfo.type == 1) {
                        com.android.server.StorageManagerService.this.mInstaller.onPrivateVolumeRemoved(volumeInfo.getFsUuid());
                    }
                } catch (com.android.server.pm.Installer.InstallerException e) {
                    android.util.Slog.i(com.android.server.StorageManagerService.TAG, "Failed when private volume unmounted " + volumeInfo, e);
                }
            }
        }
    };

    public static class Lifecycle extends com.android.server.SystemService {
        private com.android.server.StorageManagerService mStorageManagerService;

        public Lifecycle(android.content.Context context) {
            super(context);
        }

        @Override // com.android.server.SystemService
        public void onStart() {
            this.mStorageManagerService = new com.android.server.StorageManagerService(getContext());
            publishBinderService("mount", this.mStorageManagerService);
            this.mStorageManagerService.start();
        }

        @Override // com.android.server.SystemService
        public void onBootPhase(int i) {
            if (i == 500) {
                this.mStorageManagerService.servicesReady();
            } else if (i == 550) {
                this.mStorageManagerService.systemReady();
            } else if (i == 1000) {
                this.mStorageManagerService.bootCompleted();
            }
        }

        @Override // com.android.server.SystemService
        public void onUserSwitching(@android.annotation.Nullable com.android.server.SystemService.TargetUser targetUser, @android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser2) {
            int userIdentifier = targetUser2.getUserIdentifier();
            this.mStorageManagerService.mCurrentUserId = userIdentifier;
            if (((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)).isUserUnlocked(userIdentifier)) {
                android.util.Slog.d(com.android.server.StorageManagerService.TAG, "Attempt remount volumes for user: " + userIdentifier);
                this.mStorageManagerService.maybeRemountVolumes(userIdentifier);
                this.mStorageManagerService.mRemountCurrentUserVolumesOnUnlock = false;
                return;
            }
            android.util.Slog.d(com.android.server.StorageManagerService.TAG, "Attempt remount volumes for user: " + userIdentifier + " on unlock");
            this.mStorageManagerService.mRemountCurrentUserVolumesOnUnlock = true;
        }

        @Override // com.android.server.SystemService
        public void onUserUnlocking(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            this.mStorageManagerService.onUserUnlocking(targetUser.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public void onUserStopped(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            this.mStorageManagerService.onUserStopped(targetUser.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public void onUserStopping(@android.annotation.NonNull com.android.server.SystemService.TargetUser targetUser) {
            this.mStorageManagerService.onUserStopping(targetUser.getUserIdentifier());
        }

        @Override // com.android.server.SystemService
        public void onUserStarting(com.android.server.SystemService.TargetUser targetUser) {
            this.mStorageManagerService.snapshotAndMonitorLegacyStorageAppOp(targetUser.getUserHandle());
        }
    }

    private static class WatchedUnlockedUsers {
        private int[] users = libcore.util.EmptyArray.INT;

        public WatchedUnlockedUsers() {
            invalidateIsUserUnlockedCache();
        }

        public void append(int i) {
            this.users = com.android.internal.util.ArrayUtils.appendInt(this.users, i);
            invalidateIsUserUnlockedCache();
        }

        public void appendAll(int[] iArr) {
            for (int i : iArr) {
                this.users = com.android.internal.util.ArrayUtils.appendInt(this.users, i);
            }
            invalidateIsUserUnlockedCache();
        }

        public void remove(int i) {
            this.users = com.android.internal.util.ArrayUtils.removeInt(this.users, i);
            invalidateIsUserUnlockedCache();
        }

        public boolean contains(int i) {
            return com.android.internal.util.ArrayUtils.contains(this.users, i);
        }

        public int[] all() {
            return this.users;
        }

        public java.lang.String toString() {
            return java.util.Arrays.toString(this.users);
        }

        private void invalidateIsUserUnlockedCache() {
            android.os.UserManager.invalidateIsUserUnlockedCache();
        }
    }

    private android.os.storage.VolumeInfo findVolumeByIdOrThrow(java.lang.String str) {
        synchronized (this.mLock) {
            try {
                android.os.storage.VolumeInfo volumeInfo = this.mVolumes.get(str);
                if (volumeInfo != null) {
                    return volumeInfo;
                }
                throw new java.lang.IllegalArgumentException("No volume found for ID " + str);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.os.storage.VolumeRecord findRecordForPath(java.lang.String str) {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mVolumes.size(); i++) {
                try {
                    android.os.storage.VolumeInfo valueAt = this.mVolumes.valueAt(i);
                    if (valueAt.path != null && str.startsWith(valueAt.path)) {
                        return this.mRecords.get(valueAt.fsUuid);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String scrubPath(java.lang.String str) {
        if (str.startsWith(android.os.Environment.getDataDirectory().getAbsolutePath())) {
            return "internal";
        }
        android.os.storage.VolumeRecord findRecordForPath = findRecordForPath(str);
        if (findRecordForPath == null || findRecordForPath.createdMillis == 0) {
            return "unknown";
        }
        return "ext:" + ((int) ((java.lang.System.currentTimeMillis() - findRecordForPath.createdMillis) / com.android.server.usage.UnixCalendar.WEEK_IN_MILLIS)) + "w";
    }

    @android.annotation.Nullable
    private android.os.storage.VolumeInfo findStorageForUuidAsUser(java.lang.String str, int i) {
        android.os.storage.StorageManager storageManager = (android.os.storage.StorageManager) this.mContext.getSystemService(android.os.storage.StorageManager.class);
        if (java.util.Objects.equals(android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL, str)) {
            return storageManager.findVolumeById("emulated;" + i);
        }
        if (java.util.Objects.equals("primary_physical", str)) {
            return storageManager.getPrimaryPhysicalVolume();
        }
        android.os.storage.VolumeInfo findVolumeByUuid = storageManager.findVolumeByUuid(str);
        if (findVolumeByUuid == null) {
            android.util.Slog.w(TAG, "findStorageForUuidAsUser cannot find volumeUuid:" + str);
            return null;
        }
        return storageManager.findVolumeById(findVolumeByUuid.getId().replace("private", "emulated") + ";" + i);
    }

    private java.util.concurrent.CountDownLatch findOrCreateDiskScanLatch(java.lang.String str) {
        java.util.concurrent.CountDownLatch countDownLatch;
        synchronized (this.mLock) {
            try {
                countDownLatch = this.mDiskScanLatches.get(str);
                if (countDownLatch == null) {
                    countDownLatch = new java.util.concurrent.CountDownLatch(1);
                    this.mDiskScanLatches.put(str, countDownLatch);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return countDownLatch;
    }

    class ObbState implements android.os.IBinder.DeathRecipient {
        final java.lang.String canonicalPath;
        final int nonce;
        final int ownerGid;
        final java.lang.String rawPath;
        final android.os.storage.IObbActionListener token;
        java.lang.String volId;

        public ObbState(java.lang.String str, java.lang.String str2, int i, android.os.storage.IObbActionListener iObbActionListener, int i2, java.lang.String str3) {
            this.rawPath = str;
            this.canonicalPath = str2;
            this.ownerGid = android.os.UserHandle.getSharedAppGid(i);
            this.token = iObbActionListener;
            this.nonce = i2;
            this.volId = str3;
        }

        public android.os.IBinder getBinder() {
            return this.token.asBinder();
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            com.android.server.StorageManagerService.this.mObbActionHandler.sendMessage(com.android.server.StorageManagerService.this.mObbActionHandler.obtainMessage(1, com.android.server.StorageManagerService.this.new UnmountObbAction(this, true)));
        }

        public void link() throws android.os.RemoteException {
            getBinder().linkToDeath(this, 0);
        }

        public void unlink() {
            getBinder().unlinkToDeath(this, 0);
        }

        public java.lang.String toString() {
            return "ObbState{rawPath=" + this.rawPath + ",canonicalPath=" + this.canonicalPath + ",ownerGid=" + this.ownerGid + ",token=" + this.token + ",binder=" + getBinder() + ",volId=" + this.volId + '}';
        }
    }

    class StorageManagerServiceHandler extends android.os.Handler {
        public StorageManagerServiceHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            boolean z;
            switch (message.what) {
                case 1:
                    com.android.server.StorageManagerService.this.handleSystemReady();
                    break;
                case 2:
                    com.android.server.StorageManagerService.this.handleDaemonConnected();
                    break;
                case 3:
                    android.os.storage.IStorageShutdownObserver iStorageShutdownObserver = (android.os.storage.IStorageShutdownObserver) message.obj;
                    try {
                        com.android.server.StorageManagerService.this.mVold.shutdown();
                        z = true;
                    } catch (java.lang.Exception e) {
                        android.util.Slog.wtf(com.android.server.StorageManagerService.TAG, e);
                        z = false;
                    }
                    if (iStorageShutdownObserver != null) {
                        try {
                            iStorageShutdownObserver.onShutDownComplete(z ? 0 : -1);
                            break;
                        } catch (java.lang.Exception e2) {
                            return;
                        }
                    }
                    break;
                case 4:
                    android.util.Slog.i(com.android.server.StorageManagerService.TAG, "Running fstrim idle maintenance");
                    try {
                        com.android.server.StorageManagerService.this.mLastMaintenance = java.lang.System.currentTimeMillis();
                        com.android.server.StorageManagerService.this.mLastMaintenanceFile.setLastModified(com.android.server.StorageManagerService.this.mLastMaintenance);
                    } catch (java.lang.Exception e3) {
                        android.util.Slog.e(com.android.server.StorageManagerService.TAG, "Unable to record last fstrim!");
                    }
                    com.android.server.StorageManagerService.this.fstrim(0, null);
                    java.lang.Runnable runnable = (java.lang.Runnable) message.obj;
                    if (runnable != null) {
                        runnable.run();
                        break;
                    }
                    break;
                case 5:
                    android.os.storage.VolumeInfo volumeInfo = (android.os.storage.VolumeInfo) message.obj;
                    if (com.android.server.StorageManagerService.this.isMountDisallowed(volumeInfo)) {
                        android.util.Slog.i(com.android.server.StorageManagerService.TAG, "Ignoring mount " + volumeInfo.getId() + " due to policy");
                        break;
                    } else {
                        com.android.server.StorageManagerService.this.mount(volumeInfo);
                        break;
                    }
                case 6:
                    android.os.storage.StorageVolume storageVolume = (android.os.storage.StorageVolume) message.obj;
                    java.lang.String state = storageVolume.getState();
                    android.util.Slog.d(com.android.server.StorageManagerService.TAG, "Volume " + storageVolume.getId() + " broadcasting " + state + " to " + storageVolume.getOwner());
                    java.lang.String broadcastForEnvironment = android.os.storage.VolumeInfo.getBroadcastForEnvironment(state);
                    if (broadcastForEnvironment != null) {
                        android.content.Intent intent = new android.content.Intent(broadcastForEnvironment, android.net.Uri.fromFile(storageVolume.getPathFile()));
                        intent.putExtra("android.os.storage.extra.STORAGE_VOLUME", storageVolume);
                        intent.addFlags(android.hardware.audio.common.V2_0.AudioFormat.HE_AAC_V1);
                        com.android.server.StorageManagerService.this.mContext.sendBroadcastAsUser(intent, storageVolume.getOwner());
                        break;
                    }
                    break;
                case 7:
                    com.android.server.StorageManagerService.this.mContext.sendBroadcastAsUser((android.content.Intent) message.obj, android.os.UserHandle.ALL, "android.permission.WRITE_MEDIA_STORAGE");
                    break;
                case 8:
                    com.android.server.StorageManagerService.this.unmount((android.os.storage.VolumeInfo) message.obj);
                    break;
                case 9:
                    android.os.storage.VolumeRecord volumeRecord = (android.os.storage.VolumeRecord) message.obj;
                    com.android.server.StorageManagerService.this.forgetPartition(volumeRecord.partGuid, volumeRecord.fsUuid);
                    break;
                case 10:
                    com.android.server.StorageManagerService.this.resetIfBootedAndConnected();
                    break;
                case 11:
                    android.util.Slog.i(com.android.server.StorageManagerService.TAG, "Running idle maintenance");
                    com.android.server.StorageManagerService.this.runIdleMaint((java.lang.Runnable) message.obj);
                    break;
                case 12:
                    android.util.Slog.i(com.android.server.StorageManagerService.TAG, "Aborting idle maintenance");
                    com.android.server.StorageManagerService.this.abortIdleMaint((java.lang.Runnable) message.obj);
                    break;
                case 13:
                    com.android.server.StorageManagerService.this.handleBootCompleted();
                    break;
                case 14:
                    com.android.server.StorageManagerService.this.completeUnlockUser(message.arg1);
                    break;
                case 15:
                    com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
                    com.android.server.StorageManagerService.this.onVolumeStateChangedAsync((android.os.storage.VolumeInfo) someArgs.arg1, someArgs.argi1, someArgs.argi2);
                    someArgs.recycle();
                    break;
                case 16:
                    if (message.obj instanceof android.os.storage.StorageManagerInternal.CloudProviderChangeListener) {
                        com.android.server.StorageManagerService.this.notifyCloudMediaProviderChangedAsync((android.os.storage.StorageManagerInternal.CloudProviderChangeListener) message.obj);
                        break;
                    } else {
                        com.android.server.StorageManagerService.this.onCloudMediaProviderChangedAsync(message.arg1, (java.lang.String) message.obj);
                        break;
                    }
                case 17:
                    try {
                        com.android.server.StorageManagerService.this.mVold.onSecureKeyguardStateChanged(((java.lang.Boolean) message.obj).booleanValue());
                        break;
                    } catch (java.lang.Exception e4) {
                        android.util.Slog.wtf(com.android.server.StorageManagerService.TAG, e4);
                        return;
                    }
                case 18:
                    com.android.server.StorageManagerService.this.remountVolumesForRunningUsersOnMove();
                    break;
            }
        }
    }

    private void waitForLatch(java.util.concurrent.CountDownLatch countDownLatch, java.lang.String str, long j) throws java.util.concurrent.TimeoutException {
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        while (!countDownLatch.await(5000L, java.util.concurrent.TimeUnit.MILLISECONDS)) {
            try {
                android.util.Slog.w(TAG, "Thread " + java.lang.Thread.currentThread().getName() + " still waiting for " + str + "...");
            } catch (java.lang.InterruptedException e) {
                android.util.Slog.w(TAG, "Interrupt while waiting for " + str);
            }
            if (j > 0 && android.os.SystemClock.elapsedRealtime() > elapsedRealtime + j) {
                throw new java.util.concurrent.TimeoutException("Thread " + java.lang.Thread.currentThread().getName() + " gave up waiting for " + str + " after " + j + "ms");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSystemReady() {
        if (prepareSmartIdleMaint()) {
            com.android.server.SmartStorageMaintIdler.scheduleSmartIdlePass(this.mContext, sSmartIdleMaintPeriod);
        }
        com.android.server.MountServiceIdler.scheduleIdlePass(this.mContext);
        this.mContext.getContentResolver().registerContentObserver(android.provider.Settings.Global.getUriFor("zram_enabled"), false, new android.database.ContentObserver(null) { // from class: com.android.server.StorageManagerService.2
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                com.android.server.StorageManagerService.this.refreshZramSettings();
            }
        });
        refreshZramSettings();
        if (!android.os.SystemProperties.get(ZRAM_ENABLED_PROPERTY).equals("0") && this.mContext.getResources().getBoolean(android.R.bool.config_windowOverscanByDefault)) {
            com.android.server.ZramWriteback.scheduleZramWriteback(this.mContext);
        }
        configureTranscoding();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshZramSettings() {
        java.lang.String str = android.os.SystemProperties.get(ZRAM_ENABLED_PROPERTY);
        if ("".equals(str)) {
            return;
        }
        java.lang.String str2 = android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "zram_enabled", 1) != 0 ? "1" : "0";
        if (!str2.equals(str)) {
            android.os.SystemProperties.set(ZRAM_ENABLED_PROPERTY, str2);
            if (str2.equals("1") && this.mContext.getResources().getBoolean(android.R.bool.config_windowOverscanByDefault)) {
                com.android.server.ZramWriteback.scheduleZramWriteback(this.mContext);
            }
        }
    }

    private boolean isHevcDecoderSupported() {
        for (android.media.MediaCodecInfo mediaCodecInfo : new android.media.MediaCodecList(0).getCodecInfos()) {
            if (!mediaCodecInfo.isEncoder()) {
                for (java.lang.String str : mediaCodecInfo.getSupportedTypes()) {
                    if (str.equalsIgnoreCase("video/hevc")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void configureTranscoding() {
        boolean z;
        boolean isHevcDecoderSupported = isHevcDecoderSupported();
        if (android.os.SystemProperties.getBoolean("persist.sys.fuse.transcode_user_control", false)) {
            z = android.os.SystemProperties.getBoolean("persist.sys.fuse.transcode_enabled", isHevcDecoderSupported);
        } else {
            z = android.provider.DeviceConfig.getBoolean("storage_native_boot", "transcode_enabled", isHevcDecoderSupported);
        }
        android.os.SystemProperties.set("sys.fuse.transcode_enabled", java.lang.String.valueOf(z));
        if (z) {
            ((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).registerAnrController(new com.android.server.StorageManagerService.ExternalStorageServiceAnrController());
        }
    }

    private class ExternalStorageServiceAnrController implements android.app.AnrController {
        private ExternalStorageServiceAnrController() {
        }

        public long getAnrDelayMillis(java.lang.String str, int i) {
            if (!com.android.server.StorageManagerService.this.isAppIoBlocked(i)) {
                return 0L;
            }
            int i2 = android.provider.DeviceConfig.getInt("storage_native_boot", com.android.server.StorageManagerService.ANR_DELAY_MILLIS_DEVICE_CONFIG_KEY, 5000);
            android.util.Slog.v(com.android.server.StorageManagerService.TAG, "getAnrDelayMillis for " + str + ". " + i2 + "ms");
            return i2;
        }

        public void onAnrDelayStarted(java.lang.String str, int i) {
            if (com.android.server.StorageManagerService.this.isAppIoBlocked(i) && android.provider.DeviceConfig.getBoolean("storage_native_boot", com.android.server.StorageManagerService.ANR_DELAY_NOTIFY_EXTERNAL_STORAGE_SERVICE_DEVICE_CONFIG_KEY, true)) {
                android.util.Slog.d(com.android.server.StorageManagerService.TAG, "onAnrDelayStarted for " + str + ". Notifying external storage service");
                try {
                    com.android.server.StorageManagerService.this.mStorageSessionController.notifyAnrDelayStarted(str, i, 0, 1);
                } catch (com.android.server.storage.StorageSessionController.ExternalStorageServiceException e) {
                    android.util.Slog.e(com.android.server.StorageManagerService.TAG, "Failed to notify ANR delay started for " + str, e);
                }
            }
        }

        public boolean onAnrDelayCompleted(java.lang.String str, int i) {
            if (com.android.server.StorageManagerService.this.isAppIoBlocked(i)) {
                android.util.Slog.d(com.android.server.StorageManagerService.TAG, "onAnrDelayCompleted for " + str + ". Showing ANR dialog...");
                return true;
            }
            android.util.Slog.d(com.android.server.StorageManagerService.TAG, "onAnrDelayCompleted for " + str + ". Skipping ANR dialog...");
            return false;
        }
    }

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void addInternalVolumeLocked() {
        android.os.storage.VolumeInfo volumeInfo = new android.os.storage.VolumeInfo("private", 1, (android.os.storage.DiskInfo) null, (java.lang.String) null);
        volumeInfo.state = 2;
        volumeInfo.path = android.os.Environment.getDataDirectory().getAbsolutePath();
        this.mVolumes.put(volumeInfo.id, volumeInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetIfBootedAndConnected() {
        int[] copyOf;
        android.util.Slog.d(TAG, "Thinking about reset, mBootCompleted=" + this.mBootCompleted + ", mDaemonConnected=" + this.mDaemonConnected);
        if (this.mBootCompleted && this.mDaemonConnected) {
            android.os.UserManager userManager = (android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class);
            java.util.List<android.content.pm.UserInfo> users = userManager.getUsers();
            extendWatchdogTimeout("#onReset might be slow");
            this.mStorageSessionController.onReset(this.mVold, new java.lang.Runnable() { // from class: com.android.server.StorageManagerService$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.StorageManagerService.this.lambda$resetIfBootedAndConnected$0();
                }
            });
            synchronized (this.mLock) {
                copyOf = java.util.Arrays.copyOf(this.mSystemUnlockedUsers, this.mSystemUnlockedUsers.length);
                this.mDisks.clear();
                this.mVolumes.clear();
                addInternalVolumeLocked();
            }
            try {
                android.util.Slog.i(TAG, "Resetting vold...");
                this.mVold.reset();
                android.util.Slog.i(TAG, "Reset vold");
                for (android.content.pm.UserInfo userInfo : users) {
                    if (userInfo.isCloneProfile()) {
                        this.mVold.onUserAdded(userInfo.id, userInfo.serialNumber, userInfo.profileGroupId);
                    } else {
                        this.mVold.onUserAdded(userInfo.id, userInfo.serialNumber, -1);
                    }
                }
                for (int i : copyOf) {
                    this.mVold.onUserStarted(i);
                    this.mStoraged.onUserStarted(i);
                }
                restoreSystemUnlockedUsers(userManager, users, copyOf);
                this.mVold.onSecureKeyguardStateChanged(this.mSecureKeyguardShowing);
                this.mStorageManagerInternal.onReset(this.mVold);
            } catch (java.lang.Exception e) {
                android.util.Slog.wtf(TAG, e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resetIfBootedAndConnected$0() {
        this.mHandler.removeCallbacksAndMessages(null);
    }

    private void restoreSystemUnlockedUsers(android.os.UserManager userManager, java.util.List<android.content.pm.UserInfo> list, int[] iArr) throws java.lang.Exception {
        java.util.Arrays.sort(iArr);
        android.os.UserManager.invalidateIsUserUnlockedCache();
        java.util.Iterator<android.content.pm.UserInfo> it = list.iterator();
        while (it.hasNext()) {
            int i = it.next().id;
            if (userManager.isUserRunning(i) && java.util.Arrays.binarySearch(iArr, i) < 0 && userManager.isUserUnlockingOrUnlocked(i)) {
                android.util.Slog.w(TAG, "UNLOCK_USER lost from vold reset, will retry, user:" + i);
                this.mVold.onUserStarted(i);
                this.mStoraged.onUserStarted(i);
                this.mHandler.obtainMessage(14, i, 0).sendToTarget();
            }
        }
    }

    private void restoreCeUnlockedUsers() {
        try {
            int[] unlockedUsers = this.mVold.getUnlockedUsers();
            if (!com.android.internal.util.ArrayUtils.isEmpty(unlockedUsers)) {
                android.util.Slog.d(TAG, "CE storage for users " + java.util.Arrays.toString(unlockedUsers) + " is already unlocked");
                synchronized (this.mLock) {
                    this.mCeUnlockedUsers.appendAll(unlockedUsers);
                }
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Failed to get unlocked users from vold", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUserUnlocking(int i) {
        android.util.Slog.d(TAG, "onUserUnlocking " + i);
        if (i != 0) {
            try {
                android.os.UserManager userManager = (android.os.UserManager) this.mContext.createPackageContextAsUser("system", 0, android.os.UserHandle.of(i)).getSystemService(android.os.UserManager.class);
                if (userManager != null && userManager.isMediaSharedWithParent()) {
                    int i2 = userManager.getProfileParent(i).id;
                    this.mUserSharesMediaWith.put(i, i2);
                    this.mUserSharesMediaWith.put(i2, i);
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                android.util.Log.e(TAG, "Failed to create user context for user " + i);
            }
        }
        try {
            this.mStorageSessionController.onUnlockUser(i);
            this.mVold.onUserStarted(i);
            this.mStoraged.onUserStarted(i);
        } catch (java.lang.Exception e2) {
            android.util.Slog.wtf(TAG, e2);
        }
        this.mHandler.obtainMessage(14, i, 0).sendToTarget();
        if (this.mRemountCurrentUserVolumesOnUnlock && i == this.mCurrentUserId) {
            maybeRemountVolumes(i);
            this.mRemountCurrentUserVolumesOnUnlock = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void completeUnlockUser(int i) {
        onKeyguardStateChanged(false);
        synchronized (this.mLock) {
            try {
                for (int i2 : this.mSystemUnlockedUsers) {
                    if (i2 == i) {
                        android.util.Log.i(TAG, "completeUnlockUser called for already unlocked user:" + i);
                        return;
                    }
                }
                for (int i3 = 0; i3 < this.mVolumes.size(); i3++) {
                    android.os.storage.VolumeInfo valueAt = this.mVolumes.valueAt(i3);
                    if (valueAt.isVisibleForUser(i) && valueAt.isMountedReadable()) {
                        android.os.storage.StorageVolume buildStorageVolume = valueAt.buildStorageVolume(this.mContext, i, false);
                        this.mHandler.obtainMessage(6, buildStorageVolume).sendToTarget();
                        java.lang.String environmentForState = android.os.storage.VolumeInfo.getEnvironmentForState(valueAt.getState());
                        this.mCallbacks.notifyStorageStateChanged(buildStorageVolume.getPath(), environmentForState, environmentForState);
                    }
                }
                this.mSystemUnlockedUsers = com.android.internal.util.ArrayUtils.appendInt(this.mSystemUnlockedUsers, i);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void extendWatchdogTimeout(java.lang.String str) {
        com.android.server.Watchdog watchdog = com.android.server.Watchdog.getInstance();
        watchdog.pauseWatchingMonitorsFor(SLOW_OPERATION_WATCHDOG_TIMEOUT_MS, str);
        watchdog.pauseWatchingCurrentThreadFor(SLOW_OPERATION_WATCHDOG_TIMEOUT_MS, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUserStopped(int i) {
        android.util.Slog.d(TAG, "onUserStopped " + i);
        extendWatchdogTimeout("#onUserStopped might be slow");
        try {
            this.mVold.onUserStopped(i);
            this.mStoraged.onUserStopped(i);
        } catch (java.lang.Exception e) {
            android.util.Slog.wtf(TAG, e);
        }
        synchronized (this.mLock) {
            this.mSystemUnlockedUsers = com.android.internal.util.ArrayUtils.removeInt(this.mSystemUnlockedUsers, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUserStopping(int i) {
        android.util.Slog.i(TAG, "onUserStopping " + i);
        try {
            this.mStorageSessionController.onUserStopping(i);
        } catch (java.lang.Exception e) {
            android.util.Slog.wtf(TAG, e);
        }
        com.android.internal.content.PackageMonitor packageMonitor = (com.android.internal.content.PackageMonitor) this.mPackageMonitorsForUser.removeReturnOld(i);
        if (packageMonitor != null) {
            packageMonitor.unregister();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeRemountVolumes(int i) {
        java.util.ArrayList<android.os.storage.VolumeInfo> arrayList = new java.util.ArrayList();
        synchronized (this.mLock) {
            for (int i2 = 0; i2 < this.mVolumes.size(); i2++) {
                try {
                    android.os.storage.VolumeInfo valueAt = this.mVolumes.valueAt(i2);
                    if (!valueAt.isPrimary() && valueAt.isMountedWritable() && valueAt.isVisible() && valueAt.getMountUserId() != this.mCurrentUserId) {
                        valueAt.mountUserId = this.mCurrentUserId;
                        arrayList.add(valueAt);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }
        for (android.os.storage.VolumeInfo volumeInfo : arrayList) {
            android.util.Slog.i(TAG, "Remounting volume for user: " + i + ". Volume: " + volumeInfo);
            this.mHandler.obtainMessage(8, volumeInfo).sendToTarget();
            this.mHandler.obtainMessage(5, volumeInfo).sendToTarget();
        }
    }

    private void updateVolumeMountIdIfRequired(android.os.storage.VolumeInfo volumeInfo) {
        synchronized (this.mLock) {
            try {
                if (!volumeInfo.isPrimary() && volumeInfo.isVisible() && volumeInfo.getMountUserId() != this.mCurrentUserId) {
                    volumeInfo.mountUserId = this.mCurrentUserId;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void remountVolumesForRunningUsersOnMove() {
        java.util.ArrayList<java.lang.Integer> arrayList = new java.util.ArrayList();
        synchronized (this.mLock) {
            try {
                for (int i : this.mSystemUnlockedUsers) {
                    if (i != this.mCurrentUserId) {
                        arrayList.add(java.lang.Integer.valueOf(i));
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        extendWatchdogTimeout("#onUserStopped might be slow");
        for (java.lang.Integer num : arrayList) {
            try {
                this.mVold.onUserStopped(num.intValue());
                this.mStoraged.onUserStopped(num.intValue());
            } catch (java.lang.Exception e) {
                android.util.Slog.wtf(TAG, e);
            }
        }
        for (java.lang.Integer num2 : arrayList) {
            try {
                this.mVold.onUserStarted(num2.intValue());
                this.mStoraged.onUserStarted(num2.intValue());
            } catch (java.lang.Exception e2) {
                android.util.Slog.wtf(TAG, e2);
            }
        }
    }

    private boolean supportsBlockCheckpoint() throws android.os.RemoteException {
        enforcePermission("android.permission.MOUNT_FORMAT_FILESYSTEMS");
        return this.mVold.supportsBlockCheckpoint();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void prepareUserStorageForMoveInternal(java.lang.String str, java.lang.String str2, java.util.List<android.content.pm.UserInfo> list) throws java.lang.Exception {
        for (android.content.pm.UserInfo userInfo : list) {
            prepareUserStorageInternal(str, userInfo.id, 3);
            prepareUserStorageInternal(str2, userInfo.id, 3);
        }
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
    public void onAwakeStateChanged(boolean z) {
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
    public void onKeyguardStateChanged(boolean z) {
        boolean z2 = z && ((android.app.KeyguardManager) this.mContext.getSystemService(android.app.KeyguardManager.class)).isDeviceSecure(this.mCurrentUserId);
        if (this.mSecureKeyguardShowing != z2) {
            this.mSecureKeyguardShowing = z2;
            this.mHandler.obtainMessage(17, java.lang.Boolean.valueOf(this.mSecureKeyguardShowing)).sendToTarget();
        }
    }

    void runIdleMaintenance(java.lang.Runnable runnable) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(4, runnable));
    }

    @android.annotation.EnforcePermission("android.permission.MOUNT_UNMOUNT_FILESYSTEMS")
    public void runMaintenance() {
        super.runMaintenance_enforcePermission();
        runIdleMaintenance(null);
    }

    public long lastMaintenance() {
        return this.mLastMaintenance;
    }

    public void onDaemonConnected() {
        this.mDaemonConnected = true;
        this.mHandler.obtainMessage(2).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDaemonConnected() {
        resetIfBootedAndConnected();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onDiskScannedLocked(android.os.storage.DiskInfo diskInfo) {
        int i = 0;
        for (int i2 = 0; i2 < this.mVolumes.size(); i2++) {
            if (java.util.Objects.equals(diskInfo.id, this.mVolumes.valueAt(i2).getDiskId())) {
                i++;
            }
        }
        android.content.Intent intent = new android.content.Intent("android.os.storage.action.DISK_SCANNED");
        intent.addFlags(android.hardware.audio.common.V2_0.AudioFormat.HE_AAC_V1);
        intent.putExtra("android.os.storage.extra.DISK_ID", diskInfo.id);
        intent.putExtra("android.os.storage.extra.VOLUME_COUNT", i);
        this.mHandler.obtainMessage(7, intent).sendToTarget();
        java.util.concurrent.CountDownLatch remove = this.mDiskScanLatches.remove(diskInfo.id);
        if (remove != null) {
            remove.countDown();
        }
        diskInfo.volumeCount = i;
        this.mCallbacks.notifyDiskScanned(diskInfo, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onVolumeCreatedLocked(android.os.storage.VolumeInfo volumeInfo) {
        android.app.ActivityManagerInternal activityManagerInternal = (android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class);
        if (volumeInfo.mountUserId >= 0 && !activityManagerInternal.isUserRunning(volumeInfo.mountUserId, 0)) {
            android.util.Slog.d(TAG, "Ignoring volume " + volumeInfo.getId() + " because user " + java.lang.Integer.toString(volumeInfo.mountUserId) + " is no longer running.");
            return;
        }
        if (volumeInfo.type == 2) {
            android.content.Context createContextAsUser = this.mContext.createContextAsUser(android.os.UserHandle.of(volumeInfo.mountUserId), 0);
            if (!(createContextAsUser != null ? ((android.os.UserManager) createContextAsUser.getSystemService(android.os.UserManager.class)).isMediaSharedWithParent() : false) && !this.mStorageSessionController.supportsExternalStorage(volumeInfo.mountUserId)) {
                android.util.Slog.d(TAG, "Ignoring volume " + volumeInfo.getId() + " because user " + java.lang.Integer.toString(volumeInfo.mountUserId) + " does not support external storage.");
                return;
            }
            android.os.storage.VolumeInfo findPrivateForEmulated = ((android.os.storage.StorageManager) this.mContext.getSystemService(android.os.storage.StorageManager.class)).findPrivateForEmulated(volumeInfo);
            if ((java.util.Objects.equals(android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL, this.mPrimaryStorageUuid) && "private".equals(findPrivateForEmulated.id)) || java.util.Objects.equals(findPrivateForEmulated.fsUuid, this.mPrimaryStorageUuid)) {
                android.util.Slog.v(TAG, "Found primary storage at " + volumeInfo);
                volumeInfo.mountFlags = volumeInfo.mountFlags | 1;
                volumeInfo.mountFlags = volumeInfo.mountFlags | 4;
                this.mHandler.obtainMessage(5, volumeInfo).sendToTarget();
                return;
            }
            return;
        }
        if (volumeInfo.type == 0) {
            if (java.util.Objects.equals("primary_physical", this.mPrimaryStorageUuid) && volumeInfo.disk.isDefaultPrimary()) {
                android.util.Slog.v(TAG, "Found primary storage at " + volumeInfo);
                volumeInfo.mountFlags = volumeInfo.mountFlags | 1;
                volumeInfo.mountFlags = volumeInfo.mountFlags | 4;
            }
            if (volumeInfo.disk.isAdoptable()) {
                volumeInfo.mountFlags |= 4;
            } else if (volumeInfo.disk.isSd()) {
                volumeInfo.mountFlags |= 4;
            }
            volumeInfo.mountUserId = this.mCurrentUserId;
            this.mHandler.obtainMessage(5, volumeInfo).sendToTarget();
            return;
        }
        if (volumeInfo.type == 1) {
            this.mHandler.obtainMessage(5, volumeInfo).sendToTarget();
            return;
        }
        if (volumeInfo.type == 5) {
            if (volumeInfo.disk.isStubVisible()) {
                volumeInfo.mountFlags |= 4;
            } else {
                volumeInfo.mountFlags |= 2;
            }
            volumeInfo.mountUserId = this.mCurrentUserId;
            this.mHandler.obtainMessage(5, volumeInfo).sendToTarget();
            return;
        }
        android.util.Slog.d(TAG, "Skipping automatic mounting of " + volumeInfo);
    }

    private boolean isBroadcastWorthy(android.os.storage.VolumeInfo volumeInfo) {
        switch (volumeInfo.getType()) {
            case 0:
            case 1:
            case 2:
            case 5:
                switch (volumeInfo.getState()) {
                }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onVolumeStateChangedLocked(final android.os.storage.VolumeInfo volumeInfo, int i) {
        if (volumeInfo.type == 2) {
            if (i != 2) {
                this.mFuseMountedUser.remove(java.lang.Integer.valueOf(volumeInfo.getMountUserId()));
            } else if (this.mVoldAppDataIsolationEnabled) {
                final int mountUserId = volumeInfo.getMountUserId();
                new java.lang.Thread(new java.lang.Runnable() { // from class: com.android.server.StorageManagerService$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.StorageManagerService.this.lambda$onVolumeStateChangedLocked$1(mountUserId, volumeInfo);
                    }
                }).start();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onVolumeStateChangedLocked$1(int i, android.os.storage.VolumeInfo volumeInfo) {
        java.util.Map<java.lang.Integer, java.lang.String> map;
        if (i == 0 && android.os.Build.VERSION.DEVICE_INITIAL_SDK_INT < 29) {
            this.mPmInternal.migrateLegacyObbData();
        }
        synchronized (this.mLock) {
            this.mFuseMountedUser.add(java.lang.Integer.valueOf(i));
        }
        int i2 = 0;
        while (true) {
            if (i2 >= 5) {
                map = null;
                break;
            }
            try {
                map = ((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).getProcessesWithPendingBindMounts(volumeInfo.getMountUserId());
                break;
            } catch (java.lang.IllegalStateException e) {
                android.util.Slog.i(TAG, "Some processes are starting, retry");
                android.os.SystemClock.sleep(100L);
                i2++;
            }
        }
        if (map != null) {
            remountAppStorageDirs(map, i);
        } else {
            android.util.Slog.wtf(TAG, "Not able to getStorageNotOptimizedProcesses() after 5 retries");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onVolumeStateChangedAsync(android.os.storage.VolumeInfo volumeInfo, int i, int i2) {
        if (i2 == 2) {
            try {
                prepareUserStorageIfNeeded(volumeInfo);
            } catch (java.lang.Exception e) {
                try {
                    this.mVold.unmount(volumeInfo.id);
                    return;
                } catch (java.lang.Exception e2) {
                    android.util.Slog.wtf(TAG, e2);
                    return;
                }
            }
        }
        synchronized (this.mLock) {
            try {
                if (!android.text.TextUtils.isEmpty(volumeInfo.fsUuid)) {
                    android.os.storage.VolumeRecord volumeRecord = this.mRecords.get(volumeInfo.fsUuid);
                    if (volumeRecord == null) {
                        volumeRecord = new android.os.storage.VolumeRecord(volumeInfo.type, volumeInfo.fsUuid);
                        volumeRecord.partGuid = volumeInfo.partGuid;
                        volumeRecord.createdMillis = java.lang.System.currentTimeMillis();
                        if (volumeInfo.type == 1) {
                            volumeRecord.nickname = volumeInfo.disk.getDescription();
                        }
                        this.mRecords.put(volumeRecord.fsUuid, volumeRecord);
                    } else if (android.text.TextUtils.isEmpty(volumeRecord.partGuid)) {
                        volumeRecord.partGuid = volumeInfo.partGuid;
                    }
                    volumeRecord.lastSeenMillis = java.lang.System.currentTimeMillis();
                    writeSettingsLocked();
                }
            } finally {
            }
        }
        try {
            this.mStorageSessionController.notifyVolumeStateChanged(volumeInfo);
        } catch (com.android.server.storage.StorageSessionController.ExternalStorageServiceException e3) {
            android.util.Log.e(TAG, "Failed to notify volume state changed to the Storage Service", e3);
        }
        synchronized (this.mLock) {
            try {
                this.mCallbacks.notifyVolumeStateChanged(volumeInfo, i, i2);
                if (this.mBootCompleted && isBroadcastWorthy(volumeInfo)) {
                    android.content.Intent intent = new android.content.Intent("android.os.storage.action.VOLUME_STATE_CHANGED");
                    intent.putExtra("android.os.storage.extra.VOLUME_ID", volumeInfo.id);
                    intent.putExtra("android.os.storage.extra.VOLUME_STATE", i2);
                    intent.putExtra("android.os.storage.extra.FS_UUID", volumeInfo.fsUuid);
                    intent.addFlags(android.hardware.audio.common.V2_0.AudioFormat.HE_AAC_V1);
                    this.mHandler.obtainMessage(7, intent).sendToTarget();
                }
                java.lang.String environmentForState = android.os.storage.VolumeInfo.getEnvironmentForState(i);
                java.lang.String environmentForState2 = android.os.storage.VolumeInfo.getEnvironmentForState(i2);
                if (!java.util.Objects.equals(environmentForState, environmentForState2)) {
                    for (int i3 : this.mSystemUnlockedUsers) {
                        if (volumeInfo.isVisibleForUser(i3)) {
                            android.os.storage.StorageVolume buildStorageVolume = volumeInfo.buildStorageVolume(this.mContext, i3, false);
                            this.mHandler.obtainMessage(6, buildStorageVolume).sendToTarget();
                            this.mCallbacks.notifyStorageStateChanged(buildStorageVolume.getPath(), environmentForState, environmentForState2);
                        }
                    }
                }
                if ((volumeInfo.type == 0 || volumeInfo.type == 5) && volumeInfo.state == 5) {
                    this.mObbActionHandler.sendMessage(this.mObbActionHandler.obtainMessage(2, volumeInfo.path));
                }
                maybeLogMediaMount(volumeInfo, i2);
            } finally {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyCloudMediaProviderChangedAsync(@android.annotation.NonNull android.os.storage.StorageManagerInternal.CloudProviderChangeListener cloudProviderChangeListener) {
        synchronized (this.mCloudMediaProviders) {
            try {
                for (int size = this.mCloudMediaProviders.size() - 1; size >= 0; size--) {
                    cloudProviderChangeListener.onCloudProviderChanged(this.mCloudMediaProviders.keyAt(size), this.mCloudMediaProviders.valueAt(size));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onCloudMediaProviderChangedAsync(int i, @android.annotation.Nullable java.lang.String str) {
        java.util.Iterator it = this.mStorageManagerInternal.mCloudProviderChangeListeners.iterator();
        while (it.hasNext()) {
            ((android.os.storage.StorageManagerInternal.CloudProviderChangeListener) it.next()).onCloudProviderChanged(i, str);
        }
    }

    private void maybeLogMediaMount(android.os.storage.VolumeInfo volumeInfo, int i) {
        android.os.storage.DiskInfo disk;
        if (!android.app.admin.SecurityLog.isLoggingEnabled() || (disk = volumeInfo.getDisk()) == null || (disk.flags & 12) == 0) {
            return;
        }
        java.lang.String trim = disk.label != null ? disk.label.trim() : "";
        if (i == 2 || i == 3) {
            android.app.admin.SecurityLog.writeEvent(210013, new java.lang.Object[]{volumeInfo.path, trim});
        } else if (i == 0 || i == 8) {
            android.app.admin.SecurityLog.writeEvent(210014, new java.lang.Object[]{volumeInfo.path, trim});
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void onMoveStatusLocked(int i) {
        if (this.mMoveCallback == null) {
            android.util.Slog.w(TAG, "Odd, status but no move requested");
            return;
        }
        try {
            this.mMoveCallback.onStatusChanged(-1, i, -1L);
        } catch (android.os.RemoteException e) {
        }
        if (i == 82) {
            android.util.Slog.d(TAG, "Move to " + this.mMoveTargetUuid + " copy phase finshed; persisting");
            this.mPrimaryStorageUuid = this.mMoveTargetUuid;
            writeSettingsLocked();
            this.mHandler.obtainMessage(18).sendToTarget();
        }
        if (android.content.pm.PackageManager.isMoveStatusFinished(i)) {
            android.util.Slog.d(TAG, "Move to " + this.mMoveTargetUuid + " finished with status " + i);
            this.mMoveCallback = null;
            this.mMoveTargetUuid = null;
        }
    }

    private void enforcePermission(java.lang.String str) {
        this.mContext.enforceCallingOrSelfPermission(str, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isMountDisallowed(android.os.storage.VolumeInfo volumeInfo) {
        boolean z;
        android.os.UserManager userManager = (android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class);
        if (volumeInfo.disk != null && volumeInfo.disk.isUsb()) {
            z = userManager.hasUserRestriction("no_usb_file_transfer", android.os.Binder.getCallingUserHandle());
        } else {
            z = false;
        }
        return z || ((volumeInfo.type == 0 || volumeInfo.type == 1 || volumeInfo.type == 5) ? userManager.hasUserRestriction("no_physical_media", android.os.Binder.getCallingUserHandle()) : false);
    }

    private void enforceAdminUser() {
        android.os.UserManager userManager = (android.os.UserManager) this.mContext.getSystemService("user");
        int callingUserId = android.os.UserHandle.getCallingUserId();
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (!userManager.getUserInfo(callingUserId).isAdmin()) {
                throw new java.lang.SecurityException("Only admin users can adopt sd cards");
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public StorageManagerService(android.content.Context context) {
        sSelf = this;
        this.mVoldAppDataIsolationEnabled = android.os.SystemProperties.getBoolean(ANDROID_VOLD_APP_DATA_ISOLATION_ENABLED_PROPERTY, false);
        this.mContext = context;
        this.mCallbacks = new com.android.server.StorageManagerService.Callbacks(com.android.server.FgThread.get().getLooper());
        android.os.HandlerThread handlerThread = new android.os.HandlerThread(TAG);
        handlerThread.start();
        this.mHandler = new com.android.server.StorageManagerService.StorageManagerServiceHandler(handlerThread.getLooper());
        this.mObbActionHandler = new com.android.server.StorageManagerService.ObbActionHandler(com.android.server.IoThread.get().getLooper());
        this.mStorageSessionController = new com.android.server.storage.StorageSessionController(this.mContext);
        this.mInstaller = new com.android.server.pm.Installer(this.mContext);
        this.mInstaller.onStart();
        this.mLastMaintenanceFile = new java.io.File(new java.io.File(android.os.Environment.getDataDirectory(), "system"), LAST_FSTRIM_FILE);
        if (!this.mLastMaintenanceFile.exists()) {
            try {
                new java.io.FileOutputStream(this.mLastMaintenanceFile).close();
            } catch (java.io.IOException e) {
                android.util.Slog.e(TAG, "Unable to create fstrim record " + this.mLastMaintenanceFile.getPath());
            }
        } else {
            this.mLastMaintenance = this.mLastMaintenanceFile.lastModified();
        }
        this.mSettingsFile = new android.util.AtomicFile(new java.io.File(android.os.Environment.getDataSystemDirectory(), "storage.xml"), "storage-settings");
        this.mWriteRecordFile = new android.util.AtomicFile(new java.io.File(android.os.Environment.getDataSystemDirectory(), "storage-write-records"));
        sSmartIdleMaintPeriod = android.provider.DeviceConfig.getInt("storage_native_boot", "smart_idle_maint_period", 60);
        if (sSmartIdleMaintPeriod < 10) {
            sSmartIdleMaintPeriod = 10;
        } else if (sSmartIdleMaintPeriod > MAX_SMART_IDLE_MAINT_PERIOD) {
            sSmartIdleMaintPeriod = MAX_SMART_IDLE_MAINT_PERIOD;
        }
        this.mMaxWriteRecords = MAX_PERIOD_WRITE_RECORD / sSmartIdleMaintPeriod;
        this.mStorageWriteRecords = new int[this.mMaxWriteRecords];
        synchronized (this.mLock) {
            readSettingsLocked();
        }
        com.android.server.LocalServices.addService(android.os.storage.StorageManagerInternal.class, this.mStorageManagerInternal);
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.USER_ADDED");
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        this.mContext.registerReceiver(this.mUserReceiver, intentFilter, null, this.mHandler);
        synchronized (this.mLock) {
            addInternalVolumeLocked();
        }
        com.android.server.Watchdog.getInstance().addMonitor(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void start() {
        lambda$connectStoraged$2();
        lambda$connectVold$3();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: connectStoraged, reason: merged with bridge method [inline-methods] */
    public void lambda$connectStoraged$2() {
        android.os.IBinder service = android.os.ServiceManager.getService("storaged");
        if (service != null) {
            try {
                service.linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.android.server.StorageManagerService.4
                    @Override // android.os.IBinder.DeathRecipient
                    public void binderDied() {
                        android.util.Slog.w(com.android.server.StorageManagerService.TAG, "storaged died; reconnecting");
                        com.android.server.StorageManagerService.this.mStoraged = null;
                        com.android.server.StorageManagerService.this.lambda$connectStoraged$2();
                    }
                }, 0);
            } catch (android.os.RemoteException e) {
                service = null;
            }
        }
        if (service != null) {
            this.mStoraged = android.os.IStoraged.Stub.asInterface(service);
        } else {
            android.util.Slog.w(TAG, "storaged not found; trying again");
        }
        if (this.mStoraged == null) {
            com.android.internal.os.BackgroundThread.getHandler().postDelayed(new java.lang.Runnable() { // from class: com.android.server.StorageManagerService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.StorageManagerService.this.lambda$connectStoraged$2();
                }
            }, 1000L);
        } else {
            onDaemonConnected();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: connectVold, reason: merged with bridge method [inline-methods] */
    public void lambda$connectVold$3() {
        android.os.IBinder service = android.os.ServiceManager.getService("vold");
        if (service != null) {
            try {
                service.linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.android.server.StorageManagerService.5
                    @Override // android.os.IBinder.DeathRecipient
                    public void binderDied() {
                        android.util.Slog.w(com.android.server.StorageManagerService.TAG, "vold died; reconnecting");
                        com.android.server.StorageManagerService.this.mVold = null;
                        com.android.server.StorageManagerService.this.lambda$connectVold$3();
                    }
                }, 0);
            } catch (android.os.RemoteException e) {
                service = null;
            }
        }
        if (service == null) {
            android.util.Slog.w(TAG, "vold not found; trying again");
        } else {
            this.mVold = android.os.IVold.Stub.asInterface(service);
            try {
                this.mVold.setListener(this.mListener);
            } catch (android.os.RemoteException e2) {
                this.mVold = null;
                android.util.Slog.w(TAG, "vold listener rejected; trying again", e2);
            }
        }
        if (this.mVold == null) {
            com.android.internal.os.BackgroundThread.getHandler().postDelayed(new java.lang.Runnable() { // from class: com.android.server.StorageManagerService$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.StorageManagerService.this.lambda$connectVold$3();
                }
            }, 1000L);
        } else {
            restoreCeUnlockedUsers();
            onDaemonConnected();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void servicesReady() {
        this.mPmInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        this.mIPackageManager = android.content.pm.IPackageManager.Stub.asInterface(android.os.ServiceManager.getService(com.android.server.pm.Settings.ATTR_PACKAGE));
        this.mIAppOpsService = com.android.internal.app.IAppOpsService.Stub.asInterface(android.os.ServiceManager.getService("appops"));
        android.content.pm.ProviderInfo providerInfo = getProviderInfo("media");
        if (providerInfo != null) {
            this.mMediaStoreAuthorityAppId = android.os.UserHandle.getAppId(providerInfo.applicationInfo.uid);
            sMediaStoreAuthorityProcessName = providerInfo.applicationInfo.processName;
        }
        android.content.pm.ProviderInfo providerInfo2 = getProviderInfo("downloads");
        if (providerInfo2 != null) {
            this.mDownloadsAuthorityAppId = android.os.UserHandle.getAppId(providerInfo2.applicationInfo.uid);
        }
        android.content.pm.ProviderInfo providerInfo3 = getProviderInfo("com.android.externalstorage.documents");
        if (providerInfo3 != null) {
            this.mExternalStorageAuthorityAppId = android.os.UserHandle.getAppId(providerInfo3.applicationInfo.uid);
        }
    }

    private android.content.pm.ProviderInfo getProviderInfo(java.lang.String str) {
        return this.mPmInternal.resolveContentProvider(str, 786432L, android.os.UserHandle.getUserId(0), 1000);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLegacyStorageApps(java.lang.String str, int i, boolean z) {
        synchronized (this.mLock) {
            try {
                if (z) {
                    android.util.Slog.v(TAG, "Package " + str + " has legacy storage");
                    this.mUidsWithLegacyExternalStorage.add(java.lang.Integer.valueOf(i));
                } else {
                    android.util.Slog.v(TAG, "Package " + str + " does not have legacy storage");
                    this.mUidsWithLegacyExternalStorage.remove(java.lang.Integer.valueOf(i));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void snapshotAndMonitorLegacyStorageAppOp(android.os.UserHandle userHandle) {
        int identifier = userHandle.getIdentifier();
        for (android.content.pm.ApplicationInfo applicationInfo : this.mPmInternal.getInstalledApplications(4988928L, identifier, android.os.Process.myUid())) {
            try {
                updateLegacyStorageApps(applicationInfo.packageName, applicationInfo.uid, this.mIAppOpsService.checkOperation(87, applicationInfo.uid, applicationInfo.packageName) == 0);
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, "Failed to check legacy op for package " + applicationInfo.packageName, e);
            }
        }
        if (this.mPackageMonitorsForUser.get(identifier) == null) {
            com.android.internal.content.PackageMonitor packageMonitor = new com.android.internal.content.PackageMonitor() { // from class: com.android.server.StorageManagerService.6
                public void onPackageRemoved(java.lang.String str, int i) {
                    com.android.server.StorageManagerService.this.updateLegacyStorageApps(str, i, false);
                }
            };
            packageMonitor.register(this.mContext, userHandle, this.mHandler);
            this.mPackageMonitorsForUser.put(identifier, packageMonitor);
        } else {
            android.util.Slog.w(TAG, "PackageMonitor is already registered for: " + identifier);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void systemReady() {
        ((com.android.server.wm.ActivityTaskManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.ActivityTaskManagerInternal.class)).registerScreenObserver(this);
        this.mHandler.obtainMessage(1).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bootCompleted() {
        this.mBootCompleted = true;
        this.mHandler.obtainMessage(13).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleBootCompleted() {
        resetIfBootedAndConnected();
    }

    private java.lang.String getDefaultPrimaryStorageUuid() {
        if (android.os.SystemProperties.getBoolean("ro.vold.primary_physical", false)) {
            return "primary_physical";
        }
        return android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL;
    }

    /* JADX WARN: Not initialized variable reg: 3, insn: 0x0057: MOVE (r2 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]), block:B:49:0x0057 */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    private void readSettingsLocked() {
        java.io.FileInputStream fileInputStream;
        org.xmlpull.v1.XmlPullParserException e;
        java.io.IOException e2;
        java.io.FileInputStream fileInputStream2;
        this.mRecords.clear();
        this.mPrimaryStorageUuid = getDefaultPrimaryStorageUuid();
        java.io.FileInputStream fileInputStream3 = null;
        try {
            try {
                fileInputStream = this.mSettingsFile.openRead();
            } catch (java.io.FileNotFoundException e3) {
            } catch (java.io.IOException e4) {
                fileInputStream = null;
                e2 = e4;
            } catch (org.xmlpull.v1.XmlPullParserException e5) {
                fileInputStream = null;
                e = e5;
            } catch (java.lang.Throwable th) {
                th = th;
                libcore.io.IoUtils.closeQuietly(fileInputStream3);
                throw th;
            }
            try {
                com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(fileInputStream);
                while (true) {
                    int next = resolvePullParser.next();
                    boolean z = true;
                    if (next == 1) {
                        break;
                    }
                    if (next == 2) {
                        java.lang.String name = resolvePullParser.getName();
                        if (TAG_VOLUMES.equals(name)) {
                            int attributeInt = resolvePullParser.getAttributeInt((java.lang.String) null, ATTR_VERSION, 1);
                            boolean z2 = android.os.SystemProperties.getBoolean("ro.vold.primary_physical", false);
                            if (attributeInt < 3 && (attributeInt < 2 || z2)) {
                                z = false;
                            }
                            if (z) {
                                this.mPrimaryStorageUuid = com.android.internal.util.XmlUtils.readStringAttribute(resolvePullParser, ATTR_PRIMARY_STORAGE_UUID);
                            }
                        } else if (TAG_VOLUME.equals(name)) {
                            android.os.storage.VolumeRecord readVolumeRecord = readVolumeRecord(resolvePullParser);
                            this.mRecords.put(readVolumeRecord.fsUuid, readVolumeRecord);
                        }
                    }
                }
            } catch (java.io.FileNotFoundException e6) {
                fileInputStream3 = fileInputStream;
                libcore.io.IoUtils.closeQuietly(fileInputStream3);
                return;
            } catch (java.io.IOException e7) {
                e2 = e7;
                android.util.Slog.wtf(TAG, "Failed reading metadata", e2);
                libcore.io.IoUtils.closeQuietly(fileInputStream);
            } catch (org.xmlpull.v1.XmlPullParserException e8) {
                e = e8;
                android.util.Slog.wtf(TAG, "Failed reading metadata", e);
                libcore.io.IoUtils.closeQuietly(fileInputStream);
            }
            libcore.io.IoUtils.closeQuietly(fileInputStream);
        } catch (java.lang.Throwable th2) {
            th = th2;
            fileInputStream3 = fileInputStream2;
            libcore.io.IoUtils.closeQuietly(fileInputStream3);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mLock"})
    public void writeSettingsLocked() {
        java.io.FileOutputStream startWrite;
        java.io.FileOutputStream fileOutputStream = null;
        try {
            startWrite = this.mSettingsFile.startWrite();
        } catch (java.io.IOException e) {
        }
        try {
            com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
            resolveSerializer.startDocument((java.lang.String) null, true);
            resolveSerializer.startTag((java.lang.String) null, TAG_VOLUMES);
            resolveSerializer.attributeInt((java.lang.String) null, ATTR_VERSION, 3);
            com.android.internal.util.XmlUtils.writeStringAttribute(resolveSerializer, ATTR_PRIMARY_STORAGE_UUID, this.mPrimaryStorageUuid);
            int size = this.mRecords.size();
            for (int i = 0; i < size; i++) {
                writeVolumeRecord(resolveSerializer, this.mRecords.valueAt(i));
            }
            resolveSerializer.endTag((java.lang.String) null, TAG_VOLUMES);
            resolveSerializer.endDocument();
            this.mSettingsFile.finishWrite(startWrite);
        } catch (java.io.IOException e2) {
            fileOutputStream = startWrite;
            if (fileOutputStream != null) {
                this.mSettingsFile.failWrite(fileOutputStream);
            }
        }
    }

    public static android.os.storage.VolumeRecord readVolumeRecord(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        android.os.storage.VolumeRecord volumeRecord = new android.os.storage.VolumeRecord(typedXmlPullParser.getAttributeInt((java.lang.String) null, "type"), com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, ATTR_FS_UUID));
        volumeRecord.partGuid = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, ATTR_PART_GUID);
        volumeRecord.nickname = com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, ATTR_NICKNAME);
        volumeRecord.userFlags = typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_USER_FLAGS);
        volumeRecord.createdMillis = typedXmlPullParser.getAttributeLong((java.lang.String) null, ATTR_CREATED_MILLIS, 0L);
        volumeRecord.lastSeenMillis = typedXmlPullParser.getAttributeLong((java.lang.String) null, ATTR_LAST_SEEN_MILLIS, 0L);
        volumeRecord.lastTrimMillis = typedXmlPullParser.getAttributeLong((java.lang.String) null, ATTR_LAST_TRIM_MILLIS, 0L);
        volumeRecord.lastBenchMillis = typedXmlPullParser.getAttributeLong((java.lang.String) null, ATTR_LAST_BENCH_MILLIS, 0L);
        return volumeRecord;
    }

    public static void writeVolumeRecord(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, android.os.storage.VolumeRecord volumeRecord) throws java.io.IOException {
        typedXmlSerializer.startTag((java.lang.String) null, TAG_VOLUME);
        typedXmlSerializer.attributeInt((java.lang.String) null, "type", volumeRecord.type);
        com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, ATTR_FS_UUID, volumeRecord.fsUuid);
        com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, ATTR_PART_GUID, volumeRecord.partGuid);
        com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, ATTR_NICKNAME, volumeRecord.nickname);
        typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_USER_FLAGS, volumeRecord.userFlags);
        typedXmlSerializer.attributeLong((java.lang.String) null, ATTR_CREATED_MILLIS, volumeRecord.createdMillis);
        typedXmlSerializer.attributeLong((java.lang.String) null, ATTR_LAST_SEEN_MILLIS, volumeRecord.lastSeenMillis);
        typedXmlSerializer.attributeLong((java.lang.String) null, ATTR_LAST_TRIM_MILLIS, volumeRecord.lastTrimMillis);
        typedXmlSerializer.attributeLong((java.lang.String) null, ATTR_LAST_BENCH_MILLIS, volumeRecord.lastBenchMillis);
        typedXmlSerializer.endTag((java.lang.String) null, TAG_VOLUME);
    }

    public void registerListener(android.os.storage.IStorageEventListener iStorageEventListener) {
        this.mCallbacks.register(iStorageEventListener);
    }

    public void unregisterListener(android.os.storage.IStorageEventListener iStorageEventListener) {
        this.mCallbacks.unregister(iStorageEventListener);
    }

    @android.annotation.EnforcePermission("android.permission.SHUTDOWN")
    public void shutdown(android.os.storage.IStorageShutdownObserver iStorageShutdownObserver) {
        super.shutdown_enforcePermission();
        android.util.Slog.i(TAG, "Shutting down");
        this.mHandler.obtainMessage(3, iStorageShutdownObserver).sendToTarget();
    }

    @android.annotation.EnforcePermission("android.permission.MOUNT_UNMOUNT_FILESYSTEMS")
    public void mount(java.lang.String str) {
        super.mount_enforcePermission();
        android.os.storage.VolumeInfo findVolumeByIdOrThrow = findVolumeByIdOrThrow(str);
        if (isMountDisallowed(findVolumeByIdOrThrow)) {
            throw new java.lang.SecurityException("Mounting " + str + " restricted by policy");
        }
        updateVolumeMountIdIfRequired(findVolumeByIdOrThrow);
        mount(findVolumeByIdOrThrow);
    }

    private void remountAppStorageDirs(java.util.Map<java.lang.Integer, java.lang.String> map, int i) {
        for (java.util.Map.Entry<java.lang.Integer, java.lang.String> entry : map.entrySet()) {
            int intValue = entry.getKey().intValue();
            java.lang.String value = entry.getValue();
            android.util.Slog.i(TAG, "Remounting storage for pid: " + intValue);
            java.lang.String[] sharedUserPackagesForPackage = this.mPmInternal.getSharedUserPackagesForPackage(value, i);
            int packageUid = this.mPmInternal.getPackageUid(value, 0L, i);
            if (sharedUserPackagesForPackage.length == 0) {
                sharedUserPackagesForPackage = new java.lang.String[]{value};
            }
            try {
                this.mVold.remountAppStorageDirs(packageUid, intValue, sharedUserPackagesForPackage);
            } catch (android.os.RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void mount(final android.os.storage.VolumeInfo volumeInfo) {
        try {
            android.util.Slog.i(TAG, "Mounting volume " + volumeInfo);
            extendWatchdogTimeout("#mount might be slow");
            this.mVold.mount(volumeInfo.id, volumeInfo.mountFlags, volumeInfo.mountUserId, new android.os.IVoldMountCallback.Stub() { // from class: com.android.server.StorageManagerService.7
                @Override // android.os.IVoldMountCallback
                public boolean onVolumeChecking(java.io.FileDescriptor fileDescriptor, java.lang.String str, java.lang.String str2) {
                    volumeInfo.path = str;
                    volumeInfo.internalPath = str2;
                    android.os.ParcelFileDescriptor parcelFileDescriptor = new android.os.ParcelFileDescriptor(fileDescriptor);
                    try {
                        try {
                            com.android.server.StorageManagerService.this.mStorageSessionController.onVolumeMount(parcelFileDescriptor, volumeInfo);
                            try {
                                parcelFileDescriptor.close();
                                return true;
                            } catch (java.lang.Exception e) {
                                android.util.Slog.e(com.android.server.StorageManagerService.TAG, "Failed to close FUSE device fd", e);
                                return true;
                            }
                        } catch (com.android.server.storage.StorageSessionController.ExternalStorageServiceException e2) {
                            android.util.Slog.e(com.android.server.StorageManagerService.TAG, "Failed to mount volume " + volumeInfo, e2);
                            android.util.Slog.i(com.android.server.StorageManagerService.TAG, "Scheduling reset in 10s");
                            com.android.server.StorageManagerService.this.mHandler.removeMessages(10);
                            com.android.server.StorageManagerService.this.mHandler.sendMessageDelayed(com.android.server.StorageManagerService.this.mHandler.obtainMessage(10), java.util.concurrent.TimeUnit.SECONDS.toMillis((long) 10));
                            try {
                                parcelFileDescriptor.close();
                                return false;
                            } catch (java.lang.Exception e3) {
                                android.util.Slog.e(com.android.server.StorageManagerService.TAG, "Failed to close FUSE device fd", e3);
                                return false;
                            }
                        }
                    } catch (java.lang.Throwable th) {
                        try {
                            parcelFileDescriptor.close();
                        } catch (java.lang.Exception e4) {
                            android.util.Slog.e(com.android.server.StorageManagerService.TAG, "Failed to close FUSE device fd", e4);
                        }
                        throw th;
                    }
                }
            });
            android.util.Slog.i(TAG, "Mounted volume " + volumeInfo);
        } catch (java.lang.Exception e) {
            android.util.Slog.wtf(TAG, e);
        }
    }

    @android.annotation.EnforcePermission("android.permission.MOUNT_UNMOUNT_FILESYSTEMS")
    public void unmount(java.lang.String str) {
        super.unmount_enforcePermission();
        unmount(findVolumeByIdOrThrow(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unmount(android.os.storage.VolumeInfo volumeInfo) {
        try {
            try {
                if (volumeInfo.type == 1) {
                    this.mInstaller.onPrivateVolumeRemoved(volumeInfo.getFsUuid());
                }
            } catch (com.android.server.pm.Installer.InstallerException e) {
                android.util.Slog.e(TAG, "Failed unmount mirror data", e);
            }
            this.mVold.unmount(volumeInfo.id);
            this.mStorageSessionController.onVolumeUnmount(volumeInfo);
        } catch (java.lang.Exception e2) {
            android.util.Slog.wtf(TAG, e2);
        }
    }

    @android.annotation.EnforcePermission("android.permission.MOUNT_FORMAT_FILESYSTEMS")
    public void format(java.lang.String str) {
        super.format_enforcePermission();
        android.os.storage.VolumeInfo findVolumeByIdOrThrow = findVolumeByIdOrThrow(str);
        java.lang.String str2 = findVolumeByIdOrThrow.fsUuid;
        try {
            this.mVold.format(findVolumeByIdOrThrow.id, com.android.server.UiModeManagerService.Shell.NIGHT_MODE_STR_AUTO);
            if (!android.text.TextUtils.isEmpty(str2)) {
                forgetVolume(str2);
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.wtf(TAG, e);
        }
    }

    @android.annotation.EnforcePermission("android.permission.MOUNT_FORMAT_FILESYSTEMS")
    public void benchmark(java.lang.String str, final android.os.IVoldTaskListener iVoldTaskListener) {
        super.benchmark_enforcePermission();
        try {
            this.mVold.benchmark(str, new android.os.IVoldTaskListener.Stub() { // from class: com.android.server.StorageManagerService.8
                @Override // android.os.IVoldTaskListener
                public void onStatus(int i, android.os.PersistableBundle persistableBundle) {
                    com.android.server.StorageManagerService.this.dispatchOnStatus(iVoldTaskListener, i, persistableBundle);
                }

                @Override // android.os.IVoldTaskListener
                public void onFinished(int i, android.os.PersistableBundle persistableBundle) {
                    com.android.server.StorageManagerService.this.dispatchOnFinished(iVoldTaskListener, i, persistableBundle);
                    java.lang.String string = persistableBundle.getString("path");
                    java.lang.String string2 = persistableBundle.getString("ident");
                    long j = persistableBundle.getLong("create");
                    long j2 = persistableBundle.getLong("run");
                    long j3 = persistableBundle.getLong("destroy");
                    ((android.os.DropBoxManager) com.android.server.StorageManagerService.this.mContext.getSystemService(android.os.DropBoxManager.class)).addText(com.android.server.StorageManagerService.TAG_STORAGE_BENCHMARK, com.android.server.StorageManagerService.this.scrubPath(string) + " " + string2 + " " + j + " " + j2 + " " + j3);
                    synchronized (com.android.server.StorageManagerService.this.mLock) {
                        try {
                            android.os.storage.VolumeRecord findRecordForPath = com.android.server.StorageManagerService.this.findRecordForPath(string);
                            if (findRecordForPath != null) {
                                findRecordForPath.lastBenchMillis = java.lang.System.currentTimeMillis();
                                com.android.server.StorageManagerService.this.writeSettingsLocked();
                            }
                        } catch (java.lang.Throwable th) {
                            throw th;
                        }
                    }
                }
            });
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @android.annotation.EnforcePermission("android.permission.MOUNT_FORMAT_FILESYSTEMS")
    public void partitionPublic(java.lang.String str) {
        super.partitionPublic_enforcePermission();
        java.util.concurrent.CountDownLatch findOrCreateDiskScanLatch = findOrCreateDiskScanLatch(str);
        extendWatchdogTimeout("#partition might be slow");
        try {
            this.mVold.partition(str, 0, -1);
            waitForLatch(findOrCreateDiskScanLatch, "partitionPublic", 180000L);
        } catch (java.lang.Exception e) {
            android.util.Slog.wtf(TAG, e);
        }
    }

    @android.annotation.EnforcePermission("android.permission.MOUNT_FORMAT_FILESYSTEMS")
    public void partitionPrivate(java.lang.String str) {
        super.partitionPrivate_enforcePermission();
        enforceAdminUser();
        java.util.concurrent.CountDownLatch findOrCreateDiskScanLatch = findOrCreateDiskScanLatch(str);
        extendWatchdogTimeout("#partition might be slow");
        try {
            this.mVold.partition(str, 1, -1);
            waitForLatch(findOrCreateDiskScanLatch, "partitionPrivate", 180000L);
        } catch (java.lang.Exception e) {
            android.util.Slog.wtf(TAG, e);
        }
    }

    @android.annotation.EnforcePermission("android.permission.MOUNT_FORMAT_FILESYSTEMS")
    public void partitionMixed(java.lang.String str, int i) {
        super.partitionMixed_enforcePermission();
        enforceAdminUser();
        java.util.concurrent.CountDownLatch findOrCreateDiskScanLatch = findOrCreateDiskScanLatch(str);
        extendWatchdogTimeout("#partition might be slow");
        try {
            this.mVold.partition(str, 2, i);
            waitForLatch(findOrCreateDiskScanLatch, "partitionMixed", 180000L);
        } catch (java.lang.Exception e) {
            android.util.Slog.wtf(TAG, e);
        }
    }

    @android.annotation.EnforcePermission("android.permission.MOUNT_UNMOUNT_FILESYSTEMS")
    public void setVolumeNickname(java.lang.String str, java.lang.String str2) {
        super.setVolumeNickname_enforcePermission();
        java.util.Objects.requireNonNull(str);
        synchronized (this.mLock) {
            android.os.storage.VolumeRecord volumeRecord = this.mRecords.get(str);
            volumeRecord.nickname = str2;
            this.mCallbacks.notifyVolumeRecordChanged(volumeRecord);
            writeSettingsLocked();
        }
    }

    @android.annotation.EnforcePermission("android.permission.MOUNT_UNMOUNT_FILESYSTEMS")
    public void setVolumeUserFlags(java.lang.String str, int i, int i2) {
        super.setVolumeUserFlags_enforcePermission();
        java.util.Objects.requireNonNull(str);
        synchronized (this.mLock) {
            android.os.storage.VolumeRecord volumeRecord = this.mRecords.get(str);
            volumeRecord.userFlags = (i & i2) | (volumeRecord.userFlags & (~i2));
            this.mCallbacks.notifyVolumeRecordChanged(volumeRecord);
            writeSettingsLocked();
        }
    }

    @android.annotation.EnforcePermission("android.permission.MOUNT_UNMOUNT_FILESYSTEMS")
    public void forgetVolume(java.lang.String str) {
        super.forgetVolume_enforcePermission();
        java.util.Objects.requireNonNull(str);
        synchronized (this.mLock) {
            try {
                android.os.storage.VolumeRecord remove = this.mRecords.remove(str);
                if (remove != null && !android.text.TextUtils.isEmpty(remove.partGuid)) {
                    this.mHandler.obtainMessage(9, remove).sendToTarget();
                }
                this.mCallbacks.notifyVolumeForgotten(str);
                if (java.util.Objects.equals(this.mPrimaryStorageUuid, str)) {
                    this.mPrimaryStorageUuid = getDefaultPrimaryStorageUuid();
                    this.mHandler.obtainMessage(10).sendToTarget();
                }
                writeSettingsLocked();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.EnforcePermission("android.permission.MOUNT_UNMOUNT_FILESYSTEMS")
    public void forgetAllVolumes() {
        super.forgetAllVolumes_enforcePermission();
        synchronized (this.mLock) {
            for (int i = 0; i < this.mRecords.size(); i++) {
                try {
                    java.lang.String keyAt = this.mRecords.keyAt(i);
                    android.os.storage.VolumeRecord valueAt = this.mRecords.valueAt(i);
                    if (!android.text.TextUtils.isEmpty(valueAt.partGuid)) {
                        this.mHandler.obtainMessage(9, valueAt).sendToTarget();
                    }
                    this.mCallbacks.notifyVolumeForgotten(keyAt);
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            this.mRecords.clear();
            if (!java.util.Objects.equals(android.os.storage.StorageManager.UUID_PRIVATE_INTERNAL, this.mPrimaryStorageUuid)) {
                this.mPrimaryStorageUuid = getDefaultPrimaryStorageUuid();
            }
            writeSettingsLocked();
            this.mHandler.obtainMessage(10).sendToTarget();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void forgetPartition(java.lang.String str, java.lang.String str2) {
        try {
            this.mVold.forgetPartition(str, str2);
        } catch (java.lang.Exception e) {
            android.util.Slog.wtf(TAG, e);
        }
    }

    @android.annotation.EnforcePermission("android.permission.MOUNT_FORMAT_FILESYSTEMS")
    public void fstrim(int i, final android.os.IVoldTaskListener iVoldTaskListener) {
        super.fstrim_enforcePermission();
        try {
            if (!needsCheckpoint() || !supportsBlockCheckpoint()) {
                this.mVold.fstrim(i, new android.os.IVoldTaskListener.Stub() { // from class: com.android.server.StorageManagerService.9
                    @Override // android.os.IVoldTaskListener
                    public void onStatus(int i2, android.os.PersistableBundle persistableBundle) {
                        com.android.server.StorageManagerService.this.dispatchOnStatus(iVoldTaskListener, i2, persistableBundle);
                        if (i2 != 0) {
                            return;
                        }
                        java.lang.String string = persistableBundle.getString("path");
                        long j = persistableBundle.getLong("bytes");
                        long j2 = persistableBundle.getLong("time");
                        ((android.os.DropBoxManager) com.android.server.StorageManagerService.this.mContext.getSystemService(android.os.DropBoxManager.class)).addText(com.android.server.StorageManagerService.TAG_STORAGE_TRIM, com.android.server.StorageManagerService.this.scrubPath(string) + " " + j + " " + j2);
                        synchronized (com.android.server.StorageManagerService.this.mLock) {
                            try {
                                android.os.storage.VolumeRecord findRecordForPath = com.android.server.StorageManagerService.this.findRecordForPath(string);
                                if (findRecordForPath != null) {
                                    findRecordForPath.lastTrimMillis = java.lang.System.currentTimeMillis();
                                    com.android.server.StorageManagerService.this.writeSettingsLocked();
                                }
                            } catch (java.lang.Throwable th) {
                                throw th;
                            }
                        }
                    }

                    @Override // android.os.IVoldTaskListener
                    public void onFinished(int i2, android.os.PersistableBundle persistableBundle) {
                        com.android.server.StorageManagerService.this.dispatchOnFinished(iVoldTaskListener, i2, persistableBundle);
                    }
                });
            } else {
                android.util.Slog.i(TAG, "Skipping fstrim - block based checkpoint in progress");
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    void runIdleMaint(final java.lang.Runnable runnable) {
        enforcePermission("android.permission.MOUNT_FORMAT_FILESYSTEMS");
        try {
            if (needsCheckpoint() && supportsBlockCheckpoint()) {
                android.util.Slog.i(TAG, "Skipping idle maintenance - block based checkpoint in progress");
            } else {
                this.mVold.runIdleMaint(this.mNeedGC, new android.os.IVoldTaskListener.Stub() { // from class: com.android.server.StorageManagerService.10
                    @Override // android.os.IVoldTaskListener
                    public void onStatus(int i, android.os.PersistableBundle persistableBundle) {
                    }

                    @Override // android.os.IVoldTaskListener
                    public void onFinished(int i, android.os.PersistableBundle persistableBundle) {
                        if (runnable != null) {
                            com.android.internal.os.BackgroundThread.getHandler().post(runnable);
                        }
                    }
                });
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.wtf(TAG, e);
        }
    }

    public void runIdleMaintenance() {
        runIdleMaint(null);
    }

    void abortIdleMaint(final java.lang.Runnable runnable) {
        enforcePermission("android.permission.MOUNT_FORMAT_FILESYSTEMS");
        try {
            this.mVold.abortIdleMaint(new android.os.IVoldTaskListener.Stub() { // from class: com.android.server.StorageManagerService.11
                @Override // android.os.IVoldTaskListener
                public void onStatus(int i, android.os.PersistableBundle persistableBundle) {
                }

                @Override // android.os.IVoldTaskListener
                public void onFinished(int i, android.os.PersistableBundle persistableBundle) {
                    if (runnable != null) {
                        com.android.internal.os.BackgroundThread.getHandler().post(runnable);
                    }
                }
            });
        } catch (java.lang.Exception e) {
            android.util.Slog.wtf(TAG, e);
        }
    }

    public void abortIdleMaintenance() {
        abortIdleMaint(null);
    }

    private boolean prepareSmartIdleMaint() {
        boolean z = android.provider.DeviceConfig.getBoolean("storage_native_boot", "smart_idle_maint_enabled", false);
        if (z) {
            this.mLifetimePercentThreshold = android.provider.DeviceConfig.getInt("storage_native_boot", "lifetime_threshold", 70);
            this.mMinSegmentsThreshold = android.provider.DeviceConfig.getInt("storage_native_boot", "min_segments_threshold", 512);
            this.mDirtyReclaimRate = android.provider.DeviceConfig.getFloat("storage_native_boot", "dirty_reclaim_rate", 0.5f);
            this.mSegmentReclaimWeight = android.provider.DeviceConfig.getFloat("storage_native_boot", "segment_reclaim_weight", 1.0f);
            this.mLowBatteryLevel = android.provider.DeviceConfig.getFloat("storage_native_boot", "low_battery_level", DEFAULT_LOW_BATTERY_LEVEL);
            this.mChargingRequired = android.provider.DeviceConfig.getBoolean("storage_native_boot", "charging_required", true);
            this.mMinGCSleepTime = android.provider.DeviceConfig.getInt("storage_native_boot", "min_gc_sleeptime", 10000);
            this.mTargetDirtyRatio = android.provider.DeviceConfig.getInt("storage_native_boot", "target_dirty_ratio", 80);
            this.mNeedGC = false;
            loadStorageWriteRecords();
            try {
                this.mVold.refreshLatestWrite();
            } catch (java.lang.Exception e) {
                android.util.Slog.wtf(TAG, e);
            }
            refreshLifetimeConstraint();
        }
        return z;
    }

    public boolean isPassedLifetimeThresh() {
        return this.mPassedLifetimeThresh;
    }

    private void loadStorageWriteRecords() {
        java.io.FileInputStream fileInputStream = null;
        try {
            try {
                fileInputStream = this.mWriteRecordFile.openRead();
                java.io.ObjectInputStream objectInputStream = new java.io.ObjectInputStream(fileInputStream);
                if (objectInputStream.readInt() == sSmartIdleMaintPeriod) {
                    this.mStorageWriteRecords = (int[]) objectInputStream.readObject();
                }
            } catch (java.io.FileNotFoundException e) {
            } catch (java.lang.Exception e2) {
                android.util.Slog.wtf(TAG, "Failed reading write records", e2);
            }
        } finally {
            libcore.io.IoUtils.closeQuietly(fileInputStream);
        }
    }

    private int getAverageWriteAmount() {
        return java.util.Arrays.stream(this.mStorageWriteRecords).sum() / this.mMaxWriteRecords;
    }

    private void updateStorageWriteRecords(int i) {
        java.lang.System.arraycopy(this.mStorageWriteRecords, 0, this.mStorageWriteRecords, 1, this.mMaxWriteRecords - 1);
        this.mStorageWriteRecords[0] = i;
        java.io.FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = this.mWriteRecordFile.startWrite();
            java.io.ObjectOutputStream objectOutputStream = new java.io.ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeInt(sSmartIdleMaintPeriod);
            objectOutputStream.writeObject(this.mStorageWriteRecords);
            this.mWriteRecordFile.finishWrite(fileOutputStream);
        } catch (java.io.IOException e) {
            if (fileOutputStream != null) {
                this.mWriteRecordFile.failWrite(fileOutputStream);
            }
        }
    }

    private boolean checkChargeStatus() {
        int intExtra;
        android.content.Intent registerReceiver = this.mContext.registerReceiver(null, new android.content.IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if (this.mChargingRequired && (intExtra = registerReceiver.getIntExtra("status", -1)) != 2 && intExtra != 5) {
            android.util.Slog.w(TAG, "Battery is not being charged");
            return false;
        }
        float intExtra2 = (registerReceiver.getIntExtra("level", -1) * 100.0f) / registerReceiver.getIntExtra("scale", -1);
        if (intExtra2 < this.mLowBatteryLevel) {
            android.util.Slog.w(TAG, "Battery level is " + intExtra2 + ", which is lower than threshold: " + this.mLowBatteryLevel);
            return false;
        }
        return true;
    }

    private boolean refreshLifetimeConstraint() {
        try {
            int storageLifeTime = this.mVold.getStorageLifeTime();
            if (storageLifeTime == -1) {
                android.util.Slog.w(TAG, "Failed to get storage lifetime");
                return false;
            }
            if (storageLifeTime > this.mLifetimePercentThreshold) {
                android.util.Slog.w(TAG, "Ended smart idle maintenance, because of lifetime(" + storageLifeTime + "), lifetime threshold(" + this.mLifetimePercentThreshold + ")");
                this.mPassedLifetimeThresh = true;
                return false;
            }
            android.util.Slog.i(TAG, "Storage lifetime: " + storageLifeTime);
            return true;
        } catch (java.lang.Exception e) {
            android.util.Slog.wtf(TAG, e);
            return false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x00ca, code lost:
    
        if (r12 != null) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00cc, code lost:
    
        r12.run();
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00da, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00d6, code lost:
    
        if (r12 == null) goto L39;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    synchronized void runSmartIdleMaint(java.lang.Runnable runnable) {
        int i;
        int i2;
        enforcePermission("android.permission.MOUNT_FORMAT_FILESYSTEMS");
        try {
            try {
                int i3 = this.mTargetDirtyRatio;
                int writeAmount = this.mVold.getWriteAmount();
                if (writeAmount == -1) {
                    android.util.Slog.w(TAG, "Failed to get storage write record");
                    return;
                }
                updateStorageWriteRecords(writeAmount);
                if (needsCheckpoint() && supportsBlockCheckpoint()) {
                    android.util.Slog.i(TAG, "Skipping smart idle maintenance - block based checkpoint in progress");
                } else {
                    if (refreshLifetimeConstraint() && checkChargeStatus()) {
                        i = i3;
                        i2 = getAverageWriteAmount();
                    } else {
                        android.util.Slog.i(TAG, "Turn off gc_urgent based on checking lifetime and charge status");
                        i = 100;
                        i2 = 0;
                    }
                    android.util.Slog.i(TAG, "Set smart idle maintenance: latest write amount: " + writeAmount + ", average write amount: " + i2 + ", min segment threshold: " + this.mMinSegmentsThreshold + ", dirty reclaim rate: " + this.mDirtyReclaimRate + ", segment reclaim weight: " + this.mSegmentReclaimWeight + ", period(min): " + sSmartIdleMaintPeriod + ", min gc sleep time(ms): " + this.mMinGCSleepTime + ", target dirty ratio: " + i);
                    this.mVold.setGCUrgentPace(i2, this.mMinSegmentsThreshold, this.mDirtyReclaimRate, this.mSegmentReclaimWeight, sSmartIdleMaintPeriod, this.mMinGCSleepTime, i);
                }
            } catch (java.lang.Exception e) {
                android.util.Slog.wtf(TAG, e);
            }
        } finally {
            if (runnable != null) {
                runnable.run();
            }
        }
    }

    @android.annotation.EnforcePermission("android.permission.MOUNT_UNMOUNT_FILESYSTEMS")
    public void setDebugFlags(int i, int i2) {
        long clearCallingIdentity;
        java.lang.String str;
        super.setDebugFlags_enforcePermission();
        java.lang.String str2 = "";
        if ((i2 & 3) != 0) {
            if ((i & 1) != 0) {
                str = "force_on";
            } else if ((i & 2) != 0) {
                str = "force_off";
            } else {
                str = "";
            }
            clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.os.SystemProperties.set("persist.sys.adoptable", str);
                this.mHandler.obtainMessage(10).sendToTarget();
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            } finally {
            }
        }
        if ((i2 & 12) != 0) {
            if ((i & 4) != 0) {
                str2 = "force_on";
            } else if ((i & 8) != 0) {
                str2 = "force_off";
            }
            clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.os.SystemProperties.set("persist.sys.sdcardfs", str2);
                this.mHandler.obtainMessage(10).sendToTarget();
            } finally {
            }
        }
        if ((i2 & 16) != 0) {
            boolean z = (i & 16) != 0;
            clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                android.os.SystemProperties.set("persist.sys.virtual_disk", java.lang.Boolean.toString(z));
                this.mHandler.obtainMessage(10).sendToTarget();
            } finally {
            }
        }
    }

    public java.lang.String getPrimaryStorageUuid() {
        java.lang.String str;
        synchronized (this.mLock) {
            str = this.mPrimaryStorageUuid;
        }
        return str;
    }

    @android.annotation.EnforcePermission("android.permission.MOUNT_UNMOUNT_FILESYSTEMS")
    public void setPrimaryStorageUuid(java.lang.String str, android.content.pm.IPackageMoveObserver iPackageMoveObserver) {
        super.setPrimaryStorageUuid_enforcePermission();
        synchronized (this.mLock) {
            try {
                if (java.util.Objects.equals(this.mPrimaryStorageUuid, str)) {
                    throw new java.lang.IllegalArgumentException("Primary storage already at " + str);
                }
                if (this.mMoveCallback != null) {
                    throw new java.lang.IllegalStateException("Move already in progress");
                }
                this.mMoveCallback = iPackageMoveObserver;
                this.mMoveTargetUuid = str;
                java.util.List users = ((android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class)).getUsers();
                for (android.content.pm.UserInfo userInfo : users) {
                    if (android.os.storage.StorageManager.isFileEncrypted() && !isCeStorageUnlocked(userInfo.id)) {
                        android.util.Slog.w(TAG, "Failing move due to locked user " + userInfo.id);
                        onMoveStatusLocked(-10);
                        return;
                    }
                }
                if (!java.util.Objects.equals("primary_physical", this.mPrimaryStorageUuid) && !java.util.Objects.equals("primary_physical", str)) {
                    int i = this.mCurrentUserId;
                    android.os.storage.VolumeInfo findStorageForUuidAsUser = findStorageForUuidAsUser(this.mPrimaryStorageUuid, i);
                    android.os.storage.VolumeInfo findStorageForUuidAsUser2 = findStorageForUuidAsUser(str, i);
                    if (findStorageForUuidAsUser == null) {
                        android.util.Slog.w(TAG, "Failing move due to missing from volume " + this.mPrimaryStorageUuid);
                        onMoveStatusLocked(-6);
                        return;
                    }
                    if (findStorageForUuidAsUser2 == null) {
                        android.util.Slog.w(TAG, "Failing move due to missing to volume " + str);
                        onMoveStatusLocked(-6);
                        return;
                    }
                    try {
                        prepareUserStorageForMoveInternal(this.mPrimaryStorageUuid, str, users);
                        try {
                            this.mVold.moveStorage(findStorageForUuidAsUser.id, findStorageForUuidAsUser2.id, new android.os.IVoldTaskListener.Stub() { // from class: com.android.server.StorageManagerService.12
                                @Override // android.os.IVoldTaskListener
                                public void onStatus(int i2, android.os.PersistableBundle persistableBundle) {
                                    synchronized (com.android.server.StorageManagerService.this.mLock) {
                                        com.android.server.StorageManagerService.this.onMoveStatusLocked(i2);
                                    }
                                }

                                @Override // android.os.IVoldTaskListener
                                public void onFinished(int i2, android.os.PersistableBundle persistableBundle) {
                                }
                            });
                            return;
                        } catch (java.lang.Exception e) {
                            android.util.Slog.wtf(TAG, e);
                            return;
                        }
                    } catch (java.lang.Exception e2) {
                        android.util.Slog.w(TAG, "Failing move due to failure on prepare user data", e2);
                        synchronized (this.mLock) {
                            onMoveStatusLocked(-6);
                            return;
                        }
                    }
                }
                android.util.Slog.d(TAG, "Skipping move to/from primary physical");
                onMoveStatusLocked(82);
                onMoveStatusLocked(-100);
                this.mHandler.obtainMessage(10).sendToTarget();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void warnOnNotMounted() {
        synchronized (this.mLock) {
            for (int i = 0; i < this.mVolumes.size(); i++) {
                try {
                    android.os.storage.VolumeInfo valueAt = this.mVolumes.valueAt(i);
                    if (valueAt.isPrimary() && valueAt.isMountedWritable()) {
                        return;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            android.util.Slog.w(TAG, "No primary storage mounted!");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isUidOwnerOfPackageOrSystem(java.lang.String str, int i) {
        if (i == 1000) {
            return true;
        }
        return this.mPmInternal.isSameApp(str, i, android.os.UserHandle.getUserId(i));
    }

    public java.lang.String getMountedObbPath(java.lang.String str) {
        com.android.server.StorageManagerService.ObbState obbState;
        java.util.Objects.requireNonNull(str, "rawPath cannot be null");
        warnOnNotMounted();
        synchronized (this.mObbMounts) {
            obbState = this.mObbPathToStateMap.get(str);
        }
        if (obbState == null) {
            android.util.Slog.w(TAG, "Failed to find OBB mounted at " + str);
            return null;
        }
        return findVolumeByIdOrThrow(obbState.volId).getPath().getAbsolutePath();
    }

    public boolean isObbMounted(java.lang.String str) {
        boolean containsKey;
        java.util.Objects.requireNonNull(str, "rawPath cannot be null");
        synchronized (this.mObbMounts) {
            containsKey = this.mObbPathToStateMap.containsKey(str);
        }
        return containsKey;
    }

    public void mountObb(java.lang.String str, java.lang.String str2, android.os.storage.IObbActionListener iObbActionListener, int i, android.content.res.ObbInfo obbInfo) {
        java.util.Objects.requireNonNull(str, "rawPath cannot be null");
        java.util.Objects.requireNonNull(str2, "canonicalPath cannot be null");
        java.util.Objects.requireNonNull(iObbActionListener, "token cannot be null");
        java.util.Objects.requireNonNull(obbInfo, "obbIfno cannot be null");
        int callingUid = android.os.Binder.getCallingUid();
        this.mObbActionHandler.sendMessage(this.mObbActionHandler.obtainMessage(1, new com.android.server.StorageManagerService.MountObbAction(new com.android.server.StorageManagerService.ObbState(str, str2, callingUid, iObbActionListener, i, null), callingUid, obbInfo)));
    }

    public void unmountObb(java.lang.String str, boolean z, android.os.storage.IObbActionListener iObbActionListener, int i) {
        com.android.server.StorageManagerService.ObbState obbState;
        java.util.Objects.requireNonNull(str, "rawPath cannot be null");
        synchronized (this.mObbMounts) {
            obbState = this.mObbPathToStateMap.get(str);
        }
        if (obbState != null) {
            this.mObbActionHandler.sendMessage(this.mObbActionHandler.obtainMessage(1, new com.android.server.StorageManagerService.UnmountObbAction(new com.android.server.StorageManagerService.ObbState(str, obbState.canonicalPath, android.os.Binder.getCallingUid(), iObbActionListener, i, obbState.volId), z)));
        } else {
            android.util.Slog.w(TAG, "Unknown OBB mount at " + str);
        }
    }

    public boolean supportsCheckpoint() throws android.os.RemoteException {
        return this.mVold.supportsCheckpoint();
    }

    public void startCheckpoint(int i) throws android.os.RemoteException {
        int callingUid = android.os.Binder.getCallingUid();
        if (callingUid != 1000 && callingUid != 0 && callingUid != 2000) {
            throw new java.lang.SecurityException("no permission to start filesystem checkpoint");
        }
        this.mVold.startCheckpoint(i);
    }

    public void commitChanges() throws android.os.RemoteException {
        if (android.os.Binder.getCallingUid() != 1000) {
            throw new java.lang.SecurityException("no permission to commit checkpoint changes");
        }
        this.mVold.commitChanges();
    }

    @android.annotation.EnforcePermission("android.permission.MOUNT_FORMAT_FILESYSTEMS")
    public boolean needsCheckpoint() throws android.os.RemoteException {
        super.needsCheckpoint_enforcePermission();
        return this.mVold.needsCheckpoint();
    }

    public void abortChanges(java.lang.String str, boolean z) throws android.os.RemoteException {
        if (android.os.Binder.getCallingUid() != 1000) {
            throw new java.lang.SecurityException("no permission to commit checkpoint changes");
        }
        this.mVold.abortChanges(str, z);
    }

    @android.annotation.EnforcePermission("android.permission.STORAGE_INTERNAL")
    public void createUserStorageKeys(int i, boolean z) {
        super.createUserStorageKeys_enforcePermission();
        try {
            this.mVold.createUserStorageKeys(i, z);
            synchronized (this.mLock) {
                this.mCeUnlockedUsers.append(i);
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.wtf(TAG, e);
        }
    }

    @android.annotation.EnforcePermission("android.permission.STORAGE_INTERNAL")
    public void destroyUserStorageKeys(int i) {
        super.destroyUserStorageKeys_enforcePermission();
        try {
            this.mVold.destroyUserStorageKeys(i);
            synchronized (this.mLock) {
                this.mCeUnlockedUsers.remove(i);
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.wtf(TAG, e);
        }
    }

    @android.annotation.EnforcePermission("android.permission.STORAGE_INTERNAL")
    public void setCeStorageProtection(int i, byte[] bArr) throws android.os.RemoteException {
        super.setCeStorageProtection_enforcePermission();
        this.mVold.setCeStorageProtection(i, com.android.internal.util.HexDump.toHexString(bArr));
    }

    @android.annotation.EnforcePermission("android.permission.STORAGE_INTERNAL")
    public void unlockCeStorage(int i, byte[] bArr) throws android.os.RemoteException {
        super.unlockCeStorage_enforcePermission();
        if (android.os.storage.StorageManager.isFileEncrypted()) {
            this.mVold.unlockCeStorage(i, com.android.internal.util.HexDump.toHexString(bArr));
        }
        synchronized (this.mLock) {
            this.mCeUnlockedUsers.append(i);
        }
    }

    @android.annotation.EnforcePermission("android.permission.STORAGE_INTERNAL")
    public void lockCeStorage(int i) {
        super.lockCeStorage_enforcePermission();
        if (i == 0 && android.os.UserManager.isHeadlessSystemUserMode()) {
            throw new java.lang.IllegalArgumentException("Headless system user data cannot be locked..");
        }
        if (!isCeStorageUnlocked(i)) {
            android.util.Slog.d(TAG, "User " + i + "'s CE storage is already locked");
            return;
        }
        try {
            this.mVold.lockCeStorage(i);
            synchronized (this.mLock) {
                this.mCeUnlockedUsers.remove(i);
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.wtf(TAG, e);
        }
    }

    public boolean isCeStorageUnlocked(int i) {
        boolean contains;
        synchronized (this.mLock) {
            contains = this.mCeUnlockedUsers.contains(i);
        }
        return contains;
    }

    private boolean isSystemUnlocked(int i) {
        boolean contains;
        synchronized (this.mLock) {
            contains = com.android.internal.util.ArrayUtils.contains(this.mSystemUnlockedUsers, i);
        }
        return contains;
    }

    private void prepareUserStorageIfNeeded(android.os.storage.VolumeInfo volumeInfo) throws java.lang.Exception {
        int i;
        if (volumeInfo.type != 1) {
            return;
        }
        android.os.UserManager userManager = (android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class);
        com.android.server.pm.UserManagerInternal userManagerInternal = (com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class);
        for (android.content.pm.UserInfo userInfo : userManager.getUsers()) {
            if (userManagerInternal.isUserUnlockingOrUnlocked(userInfo.id)) {
                i = 3;
            } else if (userManagerInternal.isUserRunning(userInfo.id)) {
                i = 1;
            }
            prepareUserStorageInternal(volumeInfo.fsUuid, userInfo.id, i);
        }
    }

    @android.annotation.EnforcePermission("android.permission.STORAGE_INTERNAL")
    public void prepareUserStorage(java.lang.String str, int i, int i2) {
        super.prepareUserStorage_enforcePermission();
        try {
            prepareUserStorageInternal(str, i, i2);
        } catch (java.lang.Exception e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    private void prepareUserStorageInternal(java.lang.String str, int i, int i2) throws java.lang.Exception {
        android.os.storage.VolumeInfo findVolumeByUuid;
        try {
            this.mVold.prepareUserStorage(str, i, i2);
            if (str != null && (findVolumeByUuid = ((android.os.storage.StorageManager) this.mContext.getSystemService(android.os.storage.StorageManager.class)).findVolumeByUuid(str)) != null && i == 0 && findVolumeByUuid.type == 1) {
                this.mInstaller.tryMountDataMirror(str);
            }
        } catch (java.lang.Exception e) {
            android.util.EventLog.writeEvent(1397638484, "224585613", -1, "");
            android.util.Slog.wtf(TAG, e);
            if (((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)).shouldIgnorePrepareStorageErrors(i)) {
                android.util.Slog.wtf(TAG, "ignoring error preparing storage for existing user " + i + "; device may be insecure!");
                return;
            }
            throw e;
        }
    }

    @android.annotation.EnforcePermission("android.permission.STORAGE_INTERNAL")
    public void destroyUserStorage(java.lang.String str, int i, int i2) {
        super.destroyUserStorage_enforcePermission();
        try {
            this.mVold.destroyUserStorage(str, i, i2);
        } catch (java.lang.Exception e) {
            android.util.Slog.wtf(TAG, e);
        }
    }

    public void fixupAppDir(java.lang.String str) {
        java.util.regex.Matcher matcher = KNOWN_APP_DIR_PATHS.matcher(str);
        if (matcher.matches()) {
            if (matcher.group(2) == null) {
                android.util.Log.e(TAG, "Asked to fixup an app dir without a userId: " + str);
                return;
            }
            try {
                int parseInt = java.lang.Integer.parseInt(matcher.group(2));
                java.lang.String group = matcher.group(3);
                int packageUidAsUser = this.mContext.getPackageManager().getPackageUidAsUser(group, parseInt);
                try {
                    this.mVold.fixupAppDir(str + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER, packageUidAsUser);
                    return;
                } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
                    android.util.Log.e(TAG, "Failed to fixup app dir for " + group, e);
                    return;
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
                android.util.Log.e(TAG, "Couldn't find package to fixup app dir " + str, e2);
                return;
            } catch (java.lang.NumberFormatException e3) {
                android.util.Log.e(TAG, "Invalid userId in path: " + str, e3);
                return;
            }
        }
        android.util.Log.e(TAG, "Path " + str + " is not a valid application-specific directory");
    }

    public void disableAppDataIsolation(java.lang.String str, int i, int i2) {
        int callingUid = android.os.Binder.getCallingUid();
        if (callingUid != 0 && callingUid != 2000) {
            throw new java.lang.SecurityException("no permission to enable app visibility");
        }
        java.lang.String[] sharedUserPackagesForPackage = this.mPmInternal.getSharedUserPackagesForPackage(str, i2);
        int packageUid = this.mPmInternal.getPackageUid(str, 0L, i2);
        if (sharedUserPackagesForPackage.length == 0) {
            sharedUserPackagesForPackage = new java.lang.String[]{str};
        }
        try {
            this.mVold.unmountAppStorageDirs(packageUid, i, sharedUserPackagesForPackage);
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    @android.annotation.Nullable
    public android.app.PendingIntent getManageSpaceActivityIntent(@android.annotation.NonNull java.lang.String str, int i) {
        int callingUidOrThrow = android.os.Binder.getCallingUidOrThrow();
        try {
            java.lang.String[] packagesForUid = this.mIPackageManager.getPackagesForUid(callingUidOrThrow);
            if (packagesForUid == null) {
                throw new java.lang.SecurityException("Unknown uid " + callingUidOrThrow);
            }
            if (!this.mStorageManagerInternal.hasExternalStorageAccess(callingUidOrThrow, packagesForUid[0])) {
                throw new java.lang.SecurityException("Only File Manager Apps permitted");
            }
            try {
                android.content.pm.ApplicationInfo applicationInfo = this.mIPackageManager.getApplicationInfo(str, 0L, android.os.UserHandle.getUserId(callingUidOrThrow));
                if (applicationInfo == null) {
                    throw new java.lang.IllegalArgumentException("Invalid packageName");
                }
                if (applicationInfo.manageSpaceActivityName == null) {
                    android.util.Log.i(TAG, str + " doesn't have a manageSpaceActivity");
                    return null;
                }
                long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
                try {
                    try {
                        android.content.Context createPackageContext = this.mContext.createPackageContext(str, 0);
                        android.content.Intent intent = new android.content.Intent("android.intent.action.VIEW");
                        intent.setClassName(str, applicationInfo.manageSpaceActivityName);
                        intent.setFlags(268435456);
                        return android.app.PendingIntent.getActivity(createPackageContext, i, intent, 1409286144, android.app.ActivityOptions.makeBasic().setPendingIntentCreatorBackgroundActivityStartMode(2).toBundle());
                    } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                        throw new java.lang.IllegalArgumentException("packageName not found");
                    }
                } finally {
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (android.os.RemoteException e2) {
                throw new java.lang.SecurityException("Only File Manager Apps permitted");
            }
        } catch (android.os.RemoteException e3) {
            throw new java.lang.SecurityException("Unknown uid " + callingUidOrThrow, e3);
        }
    }

    public void notifyAppIoBlocked(java.lang.String str, int i, int i2, int i3) {
        enforceExternalStorageService();
        this.mStorageSessionController.notifyAppIoBlocked(str, i, i2, i3);
    }

    public void notifyAppIoResumed(java.lang.String str, int i, int i2, int i3) {
        enforceExternalStorageService();
        this.mStorageSessionController.notifyAppIoResumed(str, i, i2, i3);
    }

    public boolean isAppIoBlocked(java.lang.String str, int i, int i2, int i3) {
        return isAppIoBlocked(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isAppIoBlocked(int i) {
        return this.mStorageSessionController.isAppIoBlocked(i);
    }

    public void setCloudMediaProvider(@android.annotation.Nullable java.lang.String str) {
        enforceExternalStorageService();
        int userId = android.os.UserHandle.getUserId(android.os.Binder.getCallingUid());
        synchronized (this.mCloudMediaProviders) {
            try {
                if (!java.util.Objects.equals(str, this.mCloudMediaProviders.get(userId))) {
                    this.mCloudMediaProviders.put(userId, str);
                    this.mHandler.obtainMessage(16, userId, 0, str).sendToTarget();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    public java.lang.String getCloudMediaProvider() {
        java.lang.String str;
        android.content.pm.ProviderInfo resolveContentProvider;
        int callingUid = android.os.Binder.getCallingUid();
        int userId = android.os.UserHandle.getUserId(callingUid);
        synchronized (this.mCloudMediaProviders) {
            str = this.mCloudMediaProviders.get(userId);
        }
        if (str == null || (resolveContentProvider = this.mPmInternal.resolveContentProvider(str, 0L, userId, callingUid)) == null || this.mPmInternal.filterAppAccess(resolveContentProvider.packageName, callingUid, userId)) {
            return null;
        }
        return str;
    }

    public long getInternalStorageBlockDeviceSize() throws android.os.RemoteException {
        if (this.mInternalStorageSize == 0) {
            this.mInternalStorageSize = this.mVold.getStorageSize();
        }
        return this.mInternalStorageSize;
    }

    @android.annotation.EnforcePermission("android.permission.READ_PRIVILEGED_PHONE_STATE")
    public int getInternalStorageRemainingLifetime() throws android.os.RemoteException {
        super.getInternalStorageRemainingLifetime_enforcePermission();
        return this.mVold.getStorageRemainingLifetime();
    }

    private void enforceExternalStorageService() {
        enforcePermission("android.permission.WRITE_MEDIA_STORAGE");
        if (android.os.UserHandle.getAppId(android.os.Binder.getCallingUid()) != this.mMediaStoreAuthorityAppId) {
            throw new java.lang.SecurityException("Only the ExternalStorageService is permitted");
        }
    }

    class AppFuseMountScope extends com.android.server.storage.AppFuseBridge.MountScope {
        private boolean mMounted;

        public AppFuseMountScope(int i, int i2) {
            super(i, i2);
            this.mMounted = false;
        }

        @Override // com.android.server.storage.AppFuseBridge.MountScope
        public android.os.ParcelFileDescriptor open() throws com.android.server.AppFuseMountException {
            com.android.server.StorageManagerService.this.extendWatchdogTimeout("#open might be slow");
            try {
                java.io.FileDescriptor mountAppFuse = com.android.server.StorageManagerService.this.mVold.mountAppFuse(this.uid, this.mountId);
                this.mMounted = true;
                return new android.os.ParcelFileDescriptor(mountAppFuse);
            } catch (java.lang.Exception e) {
                throw new com.android.server.AppFuseMountException("Failed to mount", e);
            }
        }

        @Override // com.android.server.storage.AppFuseBridge.MountScope
        public android.os.ParcelFileDescriptor openFile(int i, int i2, int i3) throws com.android.server.AppFuseMountException {
            com.android.server.StorageManagerService.this.extendWatchdogTimeout("#openFile might be slow");
            try {
                return new android.os.ParcelFileDescriptor(com.android.server.StorageManagerService.this.mVold.openAppFuseFile(this.uid, i, i2, i3));
            } catch (java.lang.Exception e) {
                throw new com.android.server.AppFuseMountException("Failed to open", e);
            }
        }

        @Override // java.lang.AutoCloseable
        public void close() throws java.lang.Exception {
            com.android.server.StorageManagerService.this.extendWatchdogTimeout("#close might be slow");
            if (this.mMounted) {
                com.android.internal.os.BackgroundThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.StorageManagerService$AppFuseMountScope$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.StorageManagerService.AppFuseMountScope.this.lambda$close$0();
                    }
                });
                this.mMounted = false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$close$0() {
            try {
                com.android.server.StorageManagerService.this.mVold.unmountAppFuse(this.uid, this.mountId);
            } catch (android.os.RemoteException e) {
                throw e.rethrowAsRuntimeException();
            }
        }
    }

    @android.annotation.Nullable
    public com.android.internal.os.AppFuseMount mountProxyFileDescriptorBridge() {
        boolean z;
        com.android.internal.os.AppFuseMount appFuseMount;
        android.util.Slog.v(TAG, "mountProxyFileDescriptorBridge");
        int callingUid = android.os.Binder.getCallingUid();
        while (true) {
            synchronized (this.mAppFuseLock) {
                if (this.mAppFuseBridge != null) {
                    z = false;
                } else {
                    this.mAppFuseBridge = new com.android.server.storage.AppFuseBridge();
                    new java.lang.Thread(this.mAppFuseBridge, com.android.server.storage.AppFuseBridge.TAG).start();
                    z = true;
                }
                try {
                    int i = this.mNextAppFuseName;
                    this.mNextAppFuseName = i + 1;
                    try {
                        appFuseMount = new com.android.internal.os.AppFuseMount(i, this.mAppFuseBridge.addBridge(new com.android.server.StorageManagerService.AppFuseMountScope(callingUid, i)));
                    } catch (com.android.internal.os.FuseUnavailableMountException e) {
                        if (z) {
                            android.util.Slog.e(TAG, "", e);
                            return null;
                        }
                        this.mAppFuseBridge = null;
                    }
                } catch (com.android.server.AppFuseMountException e2) {
                    throw e2.rethrowAsParcelableException();
                }
            }
            return appFuseMount;
        }
    }

    @android.annotation.Nullable
    public android.os.ParcelFileDescriptor openProxyFileDescriptor(int i, int i2, int i3) {
        android.util.Slog.v(TAG, "mountProxyFileDescriptor");
        int i4 = i3 & 805306368;
        try {
            synchronized (this.mAppFuseLock) {
                try {
                    if (this.mAppFuseBridge == null) {
                        android.util.Slog.e(TAG, "FuseBridge has not been created");
                        return null;
                    }
                    return this.mAppFuseBridge.openFile(i, i2, i4);
                } finally {
                }
            }
        } catch (com.android.internal.os.FuseUnavailableMountException | java.lang.InterruptedException e) {
            android.util.Slog.v(TAG, "The mount point has already been invalid", e);
            return null;
        }
    }

    public void mkdirs(java.lang.String str, java.lang.String str2) {
        android.content.pm.PackageManager.Property propertyAsUser;
        int callingUid = android.os.Binder.getCallingUid();
        int userId = android.os.UserHandle.getUserId(callingUid);
        java.lang.String str3 = "sys.user." + userId + ".ce_available";
        if (!isCeStorageUnlocked(userId)) {
            throw new java.lang.IllegalStateException("Failed to prepare " + str2);
        }
        if (userId == 0 && !android.os.SystemProperties.getBoolean(str3, false)) {
            throw new java.lang.IllegalStateException("Failed to prepare " + str2);
        }
        ((android.app.AppOpsManager) this.mContext.getSystemService("appops")).checkPackage(callingUid, str);
        try {
            propertyAsUser = this.mContext.getPackageManager().getPropertyAsUser("android.internal.PROPERTY_NO_APP_DATA_STORAGE", str, null, userId);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
        }
        if (propertyAsUser != null && propertyAsUser.getBoolean()) {
            throw new java.lang.SecurityException(str + " should not have " + str2);
        }
        try {
            java.io.File canonicalFile = new java.io.File(str2).getCanonicalFile();
            java.lang.String absolutePath = canonicalFile.getAbsolutePath();
            if (!absolutePath.endsWith(com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER)) {
                absolutePath = absolutePath + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER;
            }
            java.util.regex.Matcher matcher = KNOWN_APP_DIR_PATHS.matcher(absolutePath);
            if (matcher.matches()) {
                if (!matcher.group(3).equals(str)) {
                    throw new java.lang.SecurityException("Invalid mkdirs path: " + canonicalFile + " does not contain calling package " + str);
                }
                if ((matcher.group(2) != null && !matcher.group(2).equals(java.lang.Integer.toString(userId))) || (matcher.group(2) == null && userId != this.mCurrentUserId)) {
                    throw new java.lang.SecurityException("Invalid mkdirs path: " + canonicalFile + " does not match calling user id " + userId);
                }
                try {
                    this.mVold.setupAppDir(absolutePath, callingUid);
                    return;
                } catch (android.os.RemoteException e2) {
                    throw new java.lang.IllegalStateException("Failed to prepare " + absolutePath + ": " + e2);
                }
            }
            throw new java.lang.SecurityException("Invalid mkdirs path: " + canonicalFile + " is not a known app path.");
        } catch (java.io.IOException e3) {
            throw new java.lang.IllegalStateException("Failed to resolve " + str2 + ": " + e3);
        }
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:33:0x00fc. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:42:0x011d A[Catch: all -> 0x0115, TryCatch #1 {all -> 0x0115, blocks: (B:30:0x00de, B:32:0x00e6, B:33:0x00fc, B:37:0x0223, B:38:0x0103, B:42:0x011d, B:45:0x0126, B:53:0x01eb, B:55:0x01f2, B:57:0x01f8, B:58:0x01fe, B:60:0x020a, B:62:0x0210, B:63:0x021a, B:65:0x0217, B:68:0x0173, B:69:0x0194, B:72:0x01a2, B:75:0x01cc, B:77:0x0131, B:79:0x0137, B:82:0x013f, B:84:0x0145, B:86:0x014b, B:89:0x0153, B:95:0x010e, B:102:0x0231, B:103:0x023a, B:105:0x0242, B:109:0x0271, B:110:0x0253, B:112:0x025b, B:114:0x0261, B:119:0x0274), top: B:29:0x00de }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0168  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0131 A[Catch: all -> 0x0115, TryCatch #1 {all -> 0x0115, blocks: (B:30:0x00de, B:32:0x00e6, B:33:0x00fc, B:37:0x0223, B:38:0x0103, B:42:0x011d, B:45:0x0126, B:53:0x01eb, B:55:0x01f2, B:57:0x01f8, B:58:0x01fe, B:60:0x020a, B:62:0x0210, B:63:0x021a, B:65:0x0217, B:68:0x0173, B:69:0x0194, B:72:0x01a2, B:75:0x01cc, B:77:0x0131, B:79:0x0137, B:82:0x013f, B:84:0x0145, B:86:0x014b, B:89:0x0153, B:95:0x010e, B:102:0x0231, B:103:0x023a, B:105:0x0242, B:109:0x0271, B:110:0x0253, B:112:0x025b, B:114:0x0261, B:119:0x0274), top: B:29:0x00de }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public android.os.storage.StorageVolume[] getVolumeList(int i, java.lang.String str, int i2) {
        boolean z;
        int i3;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        int i4;
        android.os.storage.StorageVolume buildStorageVolume;
        android.util.ArraySet arraySet;
        int callingUid = android.os.Binder.getCallingUid();
        int userId = android.os.UserHandle.getUserId(callingUid);
        if (!isUidOwnerOfPackageOrSystem(str, callingUid)) {
            throw new java.lang.SecurityException("callingPackage does not match UID");
        }
        if (userId != i) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS", "Need INTERACT_ACROSS_USERS to get volumes for another user");
        }
        boolean z6 = (i2 & 256) != 0;
        boolean z7 = (i2 & 512) != 0;
        boolean z8 = (i2 & 1024) != 0;
        boolean z9 = (i2 & 2048) != 0;
        boolean z10 = (i2 & 4096) != 0;
        boolean isSameApp = android.os.UserHandle.isSameApp(callingUid, this.mMediaStoreAuthorityAppId);
        if (z10) {
            try {
                java.lang.String[] packagesForUid = this.mIPackageManager.getPackagesForUid(callingUid);
                if (packagesForUid == null) {
                    throw new java.lang.SecurityException("Unknown uid " + callingUid);
                }
                if (!isSameApp && !this.mStorageManagerInternal.hasExternalStorageAccess(callingUid, packagesForUid[0])) {
                    throw new java.lang.SecurityException("Only File Manager Apps permitted");
                }
            } catch (android.os.RemoteException e) {
                throw new java.lang.SecurityException("Unknown uid " + callingUid, e);
            }
        }
        boolean isSystemUnlocked = isSystemUnlocked(0);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            boolean isDemo = ((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)).getUserInfo(i).isDemo();
            boolean hasExternalStorage = this.mStorageManagerInternal.hasExternalStorage(callingUid, str);
            boolean isCeStorageUnlocked = isCeStorageUnlocked(i);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            java.util.ArrayList arrayList = new java.util.ArrayList();
            android.util.ArraySet arraySet2 = new android.util.ArraySet();
            int i5 = this.mUserSharesMediaWith.get(i, -1);
            synchronized (this.mLock) {
                int i6 = 0;
                z = false;
                while (true) {
                    boolean z11 = z9;
                    try {
                        if (i6 < this.mVolumes.size()) {
                            java.lang.String keyAt = this.mVolumes.keyAt(i6);
                            android.util.ArraySet arraySet3 = arraySet2;
                            android.os.storage.VolumeInfo valueAt = this.mVolumes.valueAt(i6);
                            switch (valueAt.getType()) {
                                case 0:
                                case 5:
                                    i3 = i6;
                                    if (!z6) {
                                        if (!valueAt.isVisibleForWrite(i) && (!z10 || !valueAt.isVisibleForWrite(i5))) {
                                            z2 = false;
                                        }
                                        z2 = true;
                                    } else {
                                        if (!valueAt.isVisibleForUser(i) && ((valueAt.isVisible() || !z8 || valueAt.getPath() == null) && ((valueAt.getType() != 0 || !valueAt.isVisibleForUser(i5)) && (!z10 || !valueAt.isVisibleForUser(i5))))) {
                                            z2 = false;
                                        }
                                        z2 = true;
                                    }
                                    if (z2) {
                                        if (!isSameApp) {
                                            if (!isSystemUnlocked) {
                                                z3 = z10;
                                                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                                                z4 = z6;
                                                sb.append("Reporting ");
                                                sb.append(keyAt);
                                                sb.append(" unmounted due to system locked");
                                                android.util.Slog.w(TAG, sb.toString());
                                                z5 = true;
                                            } else {
                                                z3 = z10;
                                                z4 = z6;
                                                if (valueAt.getType() == 2 && !isCeStorageUnlocked) {
                                                    android.util.Slog.w(TAG, "Reporting " + keyAt + "unmounted due to " + i + " locked");
                                                    z5 = true;
                                                } else if (!hasExternalStorage && !z7) {
                                                    android.util.Slog.w(TAG, "Reporting " + keyAt + "unmounted due to missing permissions");
                                                    z5 = true;
                                                }
                                            }
                                            if (i == valueAt.getMountUserId() && valueAt.getMountUserId() >= 0) {
                                                i4 = valueAt.getMountUserId();
                                            } else {
                                                i4 = i;
                                            }
                                            buildStorageVolume = valueAt.buildStorageVolume(this.mContext, i4, z5);
                                            if (!valueAt.isPrimary() && valueAt.getMountUserId() == i) {
                                                arrayList.add(0, buildStorageVolume);
                                                z = true;
                                            } else {
                                                arrayList.add(buildStorageVolume);
                                            }
                                            arraySet = arraySet3;
                                            arraySet.add(buildStorageVolume.getUuid());
                                            break;
                                        } else {
                                            z3 = z10;
                                            z4 = z6;
                                        }
                                        z5 = false;
                                        if (i == valueAt.getMountUserId()) {
                                        }
                                        i4 = i;
                                        buildStorageVolume = valueAt.buildStorageVolume(this.mContext, i4, z5);
                                        if (!valueAt.isPrimary()) {
                                        }
                                        arrayList.add(buildStorageVolume);
                                        arraySet = arraySet3;
                                        arraySet.add(buildStorageVolume.getUuid());
                                    }
                                    z3 = z10;
                                    z4 = z6;
                                    arraySet = arraySet3;
                                    break;
                                case 2:
                                    i3 = i6;
                                    if (valueAt.getMountUserId() != i) {
                                        if (z10 && valueAt.getMountUserId() == i5) {
                                        }
                                        z3 = z10;
                                        z4 = z6;
                                        arraySet = arraySet3;
                                        break;
                                    }
                                    if (!z6) {
                                    }
                                    if (z2) {
                                    }
                                    z3 = z10;
                                    z4 = z6;
                                    arraySet = arraySet3;
                                    break;
                                default:
                                    i3 = i6;
                                    z3 = z10;
                                    z4 = z6;
                                    arraySet = arraySet3;
                                    break;
                            }
                            i6 = i3 + 1;
                            arraySet2 = arraySet;
                            z9 = z11;
                            z10 = z3;
                            z6 = z4;
                        } else {
                            android.util.ArraySet arraySet4 = arraySet2;
                            if (z11) {
                                long currentTimeMillis = java.lang.System.currentTimeMillis() - com.android.server.usage.UnixCalendar.WEEK_IN_MILLIS;
                                for (int i7 = 0; i7 < this.mRecords.size(); i7++) {
                                    android.os.storage.VolumeRecord valueAt2 = this.mRecords.valueAt(i7);
                                    if (!arraySet4.contains(valueAt2.fsUuid) && valueAt2.lastSeenMillis > 0 && valueAt2.lastSeenMillis < currentTimeMillis) {
                                        android.os.storage.StorageVolume buildStorageVolume2 = valueAt2.buildStorageVolume(this.mContext);
                                        arrayList.add(buildStorageVolume2);
                                        arraySet4.add(buildStorageVolume2.getUuid());
                                    }
                                }
                            }
                        }
                    } finally {
                    }
                }
            }
            if (isDemo) {
                java.io.File dataPreloadsMediaDirectory = android.os.Environment.getDataPreloadsMediaDirectory();
                arrayList.add(new android.os.storage.StorageVolume("demo", dataPreloadsMediaDirectory, dataPreloadsMediaDirectory, this.mContext.getString(android.R.string.unknownName), false, false, true, false, false, 0L, new android.os.UserHandle(i), null, "demo", "mounted_ro"));
            }
            if (!z) {
                android.util.Slog.w(TAG, "No primary storage defined yet; hacking together a stub");
                boolean z12 = android.os.SystemProperties.getBoolean("ro.vold.primary_physical", false);
                java.io.File legacyExternalStorageDirectory = android.os.Environment.getLegacyExternalStorageDirectory();
                arrayList.add(0, new android.os.storage.StorageVolume("stub_primary", legacyExternalStorageDirectory, legacyExternalStorageDirectory, this.mContext.getString(android.R.string.unknownName), true, z12, !z12, false, false, 0L, new android.os.UserHandle(i), null, null, "removed"));
            }
            return (android.os.storage.StorageVolume[]) arrayList.toArray(new android.os.storage.StorageVolume[arrayList.size()]);
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public android.os.storage.DiskInfo[] getDisks() {
        android.os.storage.DiskInfo[] diskInfoArr;
        synchronized (this.mLock) {
            try {
                diskInfoArr = new android.os.storage.DiskInfo[this.mDisks.size()];
                for (int i = 0; i < this.mDisks.size(); i++) {
                    diskInfoArr[i] = this.mDisks.valueAt(i);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return diskInfoArr;
    }

    public android.os.storage.VolumeInfo[] getVolumes(int i) {
        android.os.storage.VolumeInfo[] volumeInfoArr;
        synchronized (this.mLock) {
            try {
                volumeInfoArr = new android.os.storage.VolumeInfo[this.mVolumes.size()];
                for (int i2 = 0; i2 < this.mVolumes.size(); i2++) {
                    volumeInfoArr[i2] = this.mVolumes.valueAt(i2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return volumeInfoArr;
    }

    public android.os.storage.VolumeRecord[] getVolumeRecords(int i) {
        android.os.storage.VolumeRecord[] volumeRecordArr;
        synchronized (this.mLock) {
            try {
                volumeRecordArr = new android.os.storage.VolumeRecord[this.mRecords.size()];
                for (int i2 = 0; i2 < this.mRecords.size(); i2++) {
                    volumeRecordArr[i2] = this.mRecords.valueAt(i2);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return volumeRecordArr;
    }

    public long getCacheQuotaBytes(java.lang.String str, int i) {
        if (i != android.os.Binder.getCallingUid()) {
            this.mContext.enforceCallingPermission("android.permission.STORAGE_INTERNAL", TAG);
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            return ((android.app.usage.StorageStatsManager) this.mContext.getSystemService(android.app.usage.StorageStatsManager.class)).getCacheQuotaBytes(str, i);
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public long getCacheSizeBytes(java.lang.String str, int i) {
        if (i != android.os.Binder.getCallingUid()) {
            this.mContext.enforceCallingPermission("android.permission.STORAGE_INTERNAL", TAG);
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                return ((android.app.usage.StorageStatsManager) this.mContext.getSystemService(android.app.usage.StorageStatsManager.class)).queryStatsForUid(str, i).getCacheBytes();
            } catch (java.io.IOException e) {
                throw new android.os.ParcelableException(e);
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private int adjustAllocateFlags(int i, int i2, java.lang.String str) {
        if ((i & 1) != 0) {
            this.mContext.enforceCallingOrSelfPermission("android.permission.ALLOCATE_AGGRESSIVE", TAG);
        }
        int i3 = i & (-3) & (-5);
        android.app.AppOpsManager appOpsManager = (android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            if (appOpsManager.isOperationActive(26, i2, str)) {
                android.util.Slog.d(TAG, "UID " + i2 + " is actively using camera; letting them defy reserved cached data");
                i3 |= 4;
            }
            return i3;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public long getAllocatableBytes(java.lang.String str, int i, java.lang.String str2) {
        long j;
        long j2;
        long j3;
        int adjustAllocateFlags = adjustAllocateFlags(i, android.os.Binder.getCallingUid(), str2);
        android.os.storage.StorageManager storageManager = (android.os.storage.StorageManager) this.mContext.getSystemService(android.os.storage.StorageManager.class);
        android.app.usage.StorageStatsManager storageStatsManager = (android.app.usage.StorageStatsManager) this.mContext.getSystemService(android.app.usage.StorageStatsManager.class);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                java.io.File findPathForUuid = storageManager.findPathForUuid(str);
                if ((adjustAllocateFlags & 16) == 0) {
                    j = findPathForUuid.getUsableSpace();
                    j2 = storageManager.getStorageLowBytes(findPathForUuid);
                    j3 = storageManager.getStorageFullBytes(findPathForUuid);
                } else {
                    j = 0;
                    j2 = 0;
                    j3 = 0;
                }
                long max = ((adjustAllocateFlags & 8) == 0 && storageStatsManager.isQuotaSupported(str)) ? java.lang.Math.max(0L, storageStatsManager.getCacheBytes(str) - storageManager.getStorageCacheBytes(findPathForUuid, adjustAllocateFlags)) : 0L;
                if ((adjustAllocateFlags & 1) != 0) {
                    long max2 = java.lang.Math.max(0L, (j + max) - j3);
                    android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                    return max2;
                }
                long max3 = java.lang.Math.max(0L, (j + max) - j2);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return max3;
            } catch (java.io.IOException e) {
                throw new android.os.ParcelableException(e);
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public void allocateBytes(java.lang.String str, long j, int i, java.lang.String str2) {
        long storageLowBytes;
        int adjustAllocateFlags = adjustAllocateFlags(i, android.os.Binder.getCallingUid(), str2);
        long allocatableBytes = getAllocatableBytes(str, adjustAllocateFlags | 8, str2);
        if (j > allocatableBytes) {
            long allocatableBytes2 = allocatableBytes + getAllocatableBytes(str, adjustAllocateFlags | 16, str2);
            if (j > allocatableBytes2) {
                throw new android.os.ParcelableException(new java.io.IOException("Failed to allocate " + j + " because only " + allocatableBytes2 + " allocatable"));
            }
        }
        android.os.storage.StorageManager storageManager = (android.os.storage.StorageManager) this.mContext.getSystemService(android.os.storage.StorageManager.class);
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                java.io.File findPathForUuid = storageManager.findPathForUuid(str);
                if ((adjustAllocateFlags & 1) != 0) {
                    storageLowBytes = j + storageManager.getStorageFullBytes(findPathForUuid);
                } else {
                    storageLowBytes = j + storageManager.getStorageLowBytes(findPathForUuid);
                }
                this.mPmInternal.freeStorage(str, storageLowBytes, adjustAllocateFlags);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (java.io.IOException e) {
                throw new android.os.ParcelableException(e);
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addObbStateLocked(com.android.server.StorageManagerService.ObbState obbState) throws android.os.RemoteException {
        android.os.IBinder binder = obbState.getBinder();
        java.util.List<com.android.server.StorageManagerService.ObbState> list = this.mObbMounts.get(binder);
        if (list == null) {
            list = new java.util.ArrayList<>();
            this.mObbMounts.put(binder, list);
        } else {
            java.util.Iterator<com.android.server.StorageManagerService.ObbState> it = list.iterator();
            while (it.hasNext()) {
                if (it.next().rawPath.equals(obbState.rawPath)) {
                    throw new java.lang.IllegalStateException("Attempt to add ObbState twice. This indicates an error in the StorageManagerService logic.");
                }
            }
        }
        list.add(obbState);
        try {
            obbState.link();
            this.mObbPathToStateMap.put(obbState.rawPath, obbState);
        } catch (android.os.RemoteException e) {
            list.remove(obbState);
            if (list.isEmpty()) {
                this.mObbMounts.remove(binder);
            }
            throw e;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeObbStateLocked(com.android.server.StorageManagerService.ObbState obbState) {
        android.os.IBinder binder = obbState.getBinder();
        java.util.List<com.android.server.StorageManagerService.ObbState> list = this.mObbMounts.get(binder);
        if (list != null) {
            if (list.remove(obbState)) {
                obbState.unlink();
            }
            if (list.isEmpty()) {
                this.mObbMounts.remove(binder);
            }
        }
        this.mObbPathToStateMap.remove(obbState.rawPath);
    }

    private class ObbActionHandler extends android.os.Handler {
        ObbActionHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case 1:
                    ((com.android.server.StorageManagerService.ObbAction) message.obj).execute(this);
                    return;
                case 2:
                    java.lang.String str = (java.lang.String) message.obj;
                    synchronized (com.android.server.StorageManagerService.this.mObbMounts) {
                        try {
                            java.util.ArrayList<com.android.server.StorageManagerService.ObbState> arrayList = new java.util.ArrayList();
                            for (com.android.server.StorageManagerService.ObbState obbState : com.android.server.StorageManagerService.this.mObbPathToStateMap.values()) {
                                if (obbState.canonicalPath.startsWith(str)) {
                                    arrayList.add(obbState);
                                }
                            }
                            for (com.android.server.StorageManagerService.ObbState obbState2 : arrayList) {
                                com.android.server.StorageManagerService.this.removeObbStateLocked(obbState2);
                                try {
                                    obbState2.token.onObbResult(obbState2.rawPath, obbState2.nonce, 2);
                                } catch (android.os.RemoteException e) {
                                    android.util.Slog.i(com.android.server.StorageManagerService.TAG, "Couldn't send unmount notification for  OBB: " + obbState2.rawPath);
                                }
                            }
                        } finally {
                        }
                    }
                    return;
                default:
                    return;
            }
        }
    }

    private static class ObbException extends java.lang.Exception {
        public final int status;

        public ObbException(int i, java.lang.String str) {
            super(str);
            this.status = i;
        }

        public ObbException(int i, java.lang.Throwable th) {
            super(th.getMessage(), th);
            this.status = i;
        }
    }

    private static abstract class ObbAction {
        com.android.server.StorageManagerService.ObbState mObbState;

        abstract void handleExecute() throws com.android.server.StorageManagerService.ObbException;

        ObbAction(com.android.server.StorageManagerService.ObbState obbState) {
            this.mObbState = obbState;
        }

        public void execute(com.android.server.StorageManagerService.ObbActionHandler obbActionHandler) {
            try {
                handleExecute();
            } catch (com.android.server.StorageManagerService.ObbException e) {
                notifyObbStateChange(e);
            }
        }

        protected void notifyObbStateChange(com.android.server.StorageManagerService.ObbException obbException) {
            android.util.Slog.w(com.android.server.StorageManagerService.TAG, obbException);
            notifyObbStateChange(obbException.status);
        }

        protected void notifyObbStateChange(int i) {
            if (this.mObbState == null || this.mObbState.token == null) {
                return;
            }
            try {
                this.mObbState.token.onObbResult(this.mObbState.rawPath, this.mObbState.nonce, i);
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.StorageManagerService.TAG, "StorageEventListener went away while calling onObbStateChanged");
            }
        }
    }

    class MountObbAction extends com.android.server.StorageManagerService.ObbAction {
        private final int mCallingUid;
        private android.content.res.ObbInfo mObbInfo;

        MountObbAction(com.android.server.StorageManagerService.ObbState obbState, int i, android.content.res.ObbInfo obbInfo) {
            super(obbState);
            this.mCallingUid = i;
            this.mObbInfo = obbInfo;
        }

        @Override // com.android.server.StorageManagerService.ObbAction
        public void handleExecute() throws com.android.server.StorageManagerService.ObbException {
            boolean containsKey;
            com.android.server.StorageManagerService.this.warnOnNotMounted();
            if (!com.android.server.StorageManagerService.this.isUidOwnerOfPackageOrSystem(this.mObbInfo.packageName, this.mCallingUid)) {
                throw new com.android.server.StorageManagerService.ObbException(25, "Denied attempt to mount OBB " + this.mObbInfo.filename + " which is owned by " + this.mObbInfo.packageName);
            }
            synchronized (com.android.server.StorageManagerService.this.mObbMounts) {
                containsKey = com.android.server.StorageManagerService.this.mObbPathToStateMap.containsKey(this.mObbState.rawPath);
            }
            if (containsKey) {
                throw new com.android.server.StorageManagerService.ObbException(24, "Attempt to mount OBB which is already mounted: " + this.mObbInfo.filename);
            }
            try {
                this.mObbState.volId = com.android.server.StorageManagerService.this.mVold.createObb(this.mObbState.canonicalPath, this.mObbState.ownerGid);
                com.android.server.StorageManagerService.this.mVold.mount(this.mObbState.volId, 0, -1, null);
                synchronized (com.android.server.StorageManagerService.this.mObbMounts) {
                    com.android.server.StorageManagerService.this.addObbStateLocked(this.mObbState);
                }
                notifyObbStateChange(1);
            } catch (java.lang.Exception e) {
                throw new com.android.server.StorageManagerService.ObbException(21, e);
            }
        }

        public java.lang.String toString() {
            return "MountObbAction{" + this.mObbState + '}';
        }
    }

    class UnmountObbAction extends com.android.server.StorageManagerService.ObbAction {
        private final boolean mForceUnmount;

        UnmountObbAction(com.android.server.StorageManagerService.ObbState obbState, boolean z) {
            super(obbState);
            this.mForceUnmount = z;
        }

        @Override // com.android.server.StorageManagerService.ObbAction
        public void handleExecute() throws com.android.server.StorageManagerService.ObbException {
            com.android.server.StorageManagerService.ObbState obbState;
            com.android.server.StorageManagerService.this.warnOnNotMounted();
            synchronized (com.android.server.StorageManagerService.this.mObbMounts) {
                obbState = (com.android.server.StorageManagerService.ObbState) com.android.server.StorageManagerService.this.mObbPathToStateMap.get(this.mObbState.rawPath);
            }
            if (obbState == null) {
                throw new com.android.server.StorageManagerService.ObbException(23, "Missing existingState");
            }
            if (obbState.ownerGid != this.mObbState.ownerGid) {
                notifyObbStateChange(new com.android.server.StorageManagerService.ObbException(25, "Permission denied to unmount OBB " + obbState.rawPath + " (owned by GID " + obbState.ownerGid + ")"));
                return;
            }
            try {
                com.android.server.StorageManagerService.this.mVold.unmount(this.mObbState.volId);
                com.android.server.StorageManagerService.this.mVold.destroyObb(this.mObbState.volId);
                this.mObbState.volId = null;
                synchronized (com.android.server.StorageManagerService.this.mObbMounts) {
                    com.android.server.StorageManagerService.this.removeObbStateLocked(obbState);
                }
                notifyObbStateChange(2);
            } catch (java.lang.Exception e) {
                throw new com.android.server.StorageManagerService.ObbException(22, e);
            }
        }

        public java.lang.String toString() {
            return "UnmountObbAction{" + this.mObbState + ",force=" + this.mForceUnmount + '}';
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchOnStatus(android.os.IVoldTaskListener iVoldTaskListener, int i, android.os.PersistableBundle persistableBundle) {
        if (iVoldTaskListener != null) {
            try {
                iVoldTaskListener.onStatus(i, persistableBundle);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchOnFinished(android.os.IVoldTaskListener iVoldTaskListener, int i, android.os.PersistableBundle persistableBundle) {
        if (iVoldTaskListener != null) {
            try {
                iVoldTaskListener.onFinished(i, persistableBundle);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    @android.annotation.EnforcePermission("android.permission.WRITE_MEDIA_STORAGE")
    public int getExternalStorageMountMode(int i, java.lang.String str) {
        super.getExternalStorageMountMode_enforcePermission();
        return this.mStorageManagerInternal.getExternalStorageMountMode(i, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getMountModeInternal(int i, java.lang.String str) {
        android.content.pm.ApplicationInfo applicationInfo;
        boolean z = false;
        try {
            if (android.os.Process.isIsolated(i) || android.os.Process.isSdkSandboxUid(i)) {
                return 0;
            }
            java.lang.String[] packagesForUid = this.mIPackageManager.getPackagesForUid(i);
            if (com.android.internal.util.ArrayUtils.isEmpty(packagesForUid)) {
                return 0;
            }
            if (str == null) {
                str = packagesForUid[0];
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                if (this.mPmInternal.isInstantApp(str, android.os.UserHandle.getUserId(i))) {
                    return 0;
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                if (this.mStorageManagerInternal.isExternalStorageService(i)) {
                    return 3;
                }
                if (this.mDownloadsAuthorityAppId != android.os.UserHandle.getAppId(i) && this.mExternalStorageAuthorityAppId != android.os.UserHandle.getAppId(i)) {
                    if ((this.mIPackageManager.checkUidPermission("android.permission.ACCESS_MTP", i) == 0) && (applicationInfo = this.mIPackageManager.getApplicationInfo(str, 0L, android.os.UserHandle.getUserId(i))) != null && applicationInfo.isSignedWithPlatformKey()) {
                        return 4;
                    }
                    boolean z2 = this.mIPackageManager.checkUidPermission("android.permission.INSTALL_PACKAGES", i) == 0;
                    int length = packagesForUid.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            break;
                        }
                        if (this.mIAppOpsService.checkOperation(66, i, packagesForUid[i2]) != 0) {
                            i2++;
                        } else {
                            z = true;
                            break;
                        }
                    }
                    return (z2 || z) ? 2 : 1;
                }
                return 4;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } catch (android.os.RemoteException e) {
            return 0;
        }
    }

    private static class Callbacks extends android.os.Handler {
        private static final int MSG_DISK_DESTROYED = 6;
        private static final int MSG_DISK_SCANNED = 5;
        private static final int MSG_STORAGE_STATE_CHANGED = 1;
        private static final int MSG_VOLUME_FORGOTTEN = 4;
        private static final int MSG_VOLUME_RECORD_CHANGED = 3;
        private static final int MSG_VOLUME_STATE_CHANGED = 2;
        private final android.os.RemoteCallbackList<android.os.storage.IStorageEventListener> mCallbacks;

        public Callbacks(android.os.Looper looper) {
            super(looper);
            this.mCallbacks = new android.os.RemoteCallbackList<>();
        }

        public void register(android.os.storage.IStorageEventListener iStorageEventListener) {
            this.mCallbacks.register(iStorageEventListener);
        }

        public void unregister(android.os.storage.IStorageEventListener iStorageEventListener) {
            this.mCallbacks.unregister(iStorageEventListener);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            com.android.internal.os.SomeArgs someArgs = (com.android.internal.os.SomeArgs) message.obj;
            int beginBroadcast = this.mCallbacks.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                try {
                    invokeCallback(this.mCallbacks.getBroadcastItem(i), message.what, someArgs);
                } catch (android.os.RemoteException e) {
                }
            }
            this.mCallbacks.finishBroadcast();
            someArgs.recycle();
        }

        private void invokeCallback(android.os.storage.IStorageEventListener iStorageEventListener, int i, com.android.internal.os.SomeArgs someArgs) throws android.os.RemoteException {
            switch (i) {
                case 1:
                    iStorageEventListener.onStorageStateChanged((java.lang.String) someArgs.arg1, (java.lang.String) someArgs.arg2, (java.lang.String) someArgs.arg3);
                    break;
                case 2:
                    iStorageEventListener.onVolumeStateChanged((android.os.storage.VolumeInfo) someArgs.arg1, someArgs.argi2, someArgs.argi3);
                    break;
                case 3:
                    iStorageEventListener.onVolumeRecordChanged((android.os.storage.VolumeRecord) someArgs.arg1);
                    break;
                case 4:
                    iStorageEventListener.onVolumeForgotten((java.lang.String) someArgs.arg1);
                    break;
                case 5:
                    iStorageEventListener.onDiskScanned((android.os.storage.DiskInfo) someArgs.arg1, someArgs.argi2);
                    break;
                case 6:
                    iStorageEventListener.onDiskDestroyed((android.os.storage.DiskInfo) someArgs.arg1);
                    break;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyStorageStateChanged(java.lang.String str, java.lang.String str2, java.lang.String str3) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtain.arg2 = str2;
            obtain.arg3 = str3;
            obtainMessage(1, obtain).sendToTarget();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyVolumeStateChanged(android.os.storage.VolumeInfo volumeInfo, int i, int i2) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = volumeInfo.clone();
            obtain.argi2 = i;
            obtain.argi3 = i2;
            obtainMessage(2, obtain).sendToTarget();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyVolumeRecordChanged(android.os.storage.VolumeRecord volumeRecord) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = volumeRecord.clone();
            obtainMessage(3, obtain).sendToTarget();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyVolumeForgotten(java.lang.String str) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = str;
            obtainMessage(4, obtain).sendToTarget();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyDiskScanned(android.os.storage.DiskInfo diskInfo, int i) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = diskInfo.clone();
            obtain.argi2 = i;
            obtainMessage(5, obtain).sendToTarget();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyDiskDestroyed(android.os.storage.DiskInfo diskInfo) {
            com.android.internal.os.SomeArgs obtain = com.android.internal.os.SomeArgs.obtain();
            obtain.arg1 = diskInfo.clone();
            obtainMessage(6, obtain).sendToTarget();
        }
    }

    protected void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        if (com.android.internal.util.DumpUtils.checkDumpPermission(this.mContext, TAG, printWriter)) {
            com.android.internal.util.IndentingPrintWriter indentingPrintWriter = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ", 160);
            synchronized (this.mLock) {
                try {
                    indentingPrintWriter.println("Disks:");
                    indentingPrintWriter.increaseIndent();
                    for (int i = 0; i < this.mDisks.size(); i++) {
                        this.mDisks.valueAt(i).dump(indentingPrintWriter);
                    }
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println();
                    indentingPrintWriter.println("Volumes:");
                    indentingPrintWriter.increaseIndent();
                    for (int i2 = 0; i2 < this.mVolumes.size(); i2++) {
                        android.os.storage.VolumeInfo valueAt = this.mVolumes.valueAt(i2);
                        if (!"private".equals(valueAt.id)) {
                            valueAt.dump(indentingPrintWriter);
                        }
                    }
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println();
                    indentingPrintWriter.println("Records:");
                    indentingPrintWriter.increaseIndent();
                    for (int i3 = 0; i3 < this.mRecords.size(); i3++) {
                        this.mRecords.valueAt(i3).dump(indentingPrintWriter);
                    }
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println();
                    indentingPrintWriter.println("Primary storage UUID: " + this.mPrimaryStorageUuid);
                    indentingPrintWriter.println();
                    android.util.Pair primaryStoragePathAndSize = android.os.storage.StorageManager.getPrimaryStoragePathAndSize();
                    if (primaryStoragePathAndSize == null) {
                        indentingPrintWriter.println("Internal storage total size: N/A");
                    } else {
                        indentingPrintWriter.print("Internal storage (");
                        indentingPrintWriter.print((java.lang.String) primaryStoragePathAndSize.first);
                        indentingPrintWriter.print(") total size: ");
                        indentingPrintWriter.print(primaryStoragePathAndSize.second);
                        indentingPrintWriter.print(" (");
                        indentingPrintWriter.print(((java.lang.Long) primaryStoragePathAndSize.second).longValue() / android.util.DataUnit.MEBIBYTES.toBytes(1L));
                        indentingPrintWriter.println(" MiB)");
                    }
                    indentingPrintWriter.println();
                    indentingPrintWriter.println("CE unlocked users: " + this.mCeUnlockedUsers);
                    indentingPrintWriter.println("System unlocked users: " + java.util.Arrays.toString(this.mSystemUnlockedUsers));
                } finally {
                }
            }
            synchronized (this.mObbMounts) {
                try {
                    indentingPrintWriter.println();
                    indentingPrintWriter.println("mObbMounts:");
                    indentingPrintWriter.increaseIndent();
                    for (java.util.Map.Entry<android.os.IBinder, java.util.List<com.android.server.StorageManagerService.ObbState>> entry : this.mObbMounts.entrySet()) {
                        indentingPrintWriter.println(entry.getKey() + ":");
                        indentingPrintWriter.increaseIndent();
                        java.util.Iterator<com.android.server.StorageManagerService.ObbState> it = entry.getValue().iterator();
                        while (it.hasNext()) {
                            indentingPrintWriter.println(it.next());
                        }
                        indentingPrintWriter.decreaseIndent();
                    }
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println();
                    indentingPrintWriter.println("mObbPathToStateMap:");
                    indentingPrintWriter.increaseIndent();
                    for (java.util.Map.Entry<java.lang.String, com.android.server.StorageManagerService.ObbState> entry2 : this.mObbPathToStateMap.entrySet()) {
                        indentingPrintWriter.print(entry2.getKey());
                        indentingPrintWriter.print(" -> ");
                        indentingPrintWriter.println(entry2.getValue());
                    }
                    indentingPrintWriter.decreaseIndent();
                } finally {
                }
            }
            synchronized (this.mCloudMediaProviders) {
                indentingPrintWriter.println();
                indentingPrintWriter.print("Media cloud providers: ");
                indentingPrintWriter.println(this.mCloudMediaProviders);
            }
            indentingPrintWriter.println();
            indentingPrintWriter.print("Last maintenance: ");
            indentingPrintWriter.println(android.util.TimeUtils.formatForLogging(this.mLastMaintenance));
        }
    }

    @Override // com.android.server.Watchdog.Monitor
    public void monitor() {
        try {
            this.mVold.monitor();
        } catch (java.lang.Exception e) {
            android.util.Slog.wtf(TAG, e);
        }
    }

    private final class StorageManagerInternalImpl extends android.os.storage.StorageManagerInternal {
        private final java.util.concurrent.CopyOnWriteArraySet<android.os.storage.StorageManagerInternal.CloudProviderChangeListener> mCloudProviderChangeListeners;

        @com.android.internal.annotations.GuardedBy({"mResetListeners"})
        private final java.util.List<android.os.storage.StorageManagerInternal.ResetListener> mResetListeners;

        private StorageManagerInternalImpl() {
            this.mResetListeners = new java.util.ArrayList();
            this.mCloudProviderChangeListeners = new java.util.concurrent.CopyOnWriteArraySet<>();
        }

        public boolean isFuseMounted(int i) {
            boolean contains;
            synchronized (com.android.server.StorageManagerService.this.mLock) {
                contains = com.android.server.StorageManagerService.this.mFuseMountedUser.contains(java.lang.Integer.valueOf(i));
            }
            return contains;
        }

        public boolean prepareStorageDirs(int i, java.util.Set<java.lang.String> set, java.lang.String str) {
            synchronized (com.android.server.StorageManagerService.this.mLock) {
                try {
                    if (!com.android.server.StorageManagerService.this.mFuseMountedUser.contains(java.lang.Integer.valueOf(i))) {
                        android.util.Slog.w(com.android.server.StorageManagerService.TAG, "User " + i + " is not unlocked yet so skip mounting obb");
                        return false;
                    }
                    try {
                        android.os.IVold asInterface = android.os.IVold.Stub.asInterface(android.os.ServiceManager.getServiceOrThrow("vold"));
                        for (java.lang.String str2 : set) {
                            asInterface.ensureAppDirsCreated(new java.lang.String[]{java.lang.String.format(java.util.Locale.US, "/storage/emulated/%d/Android/obb/%s/", java.lang.Integer.valueOf(i), str2), java.lang.String.format(java.util.Locale.US, "/storage/emulated/%d/Android/data/%s/", java.lang.Integer.valueOf(i), str2)}, android.os.UserHandle.getUid(i, com.android.server.StorageManagerService.this.mPmInternal.getPackage(str2).getUid()));
                        }
                        return true;
                    } catch (android.os.ServiceManager.ServiceNotFoundException | android.os.RemoteException e) {
                        android.util.Slog.e(com.android.server.StorageManagerService.TAG, "Unable to create obb and data directories for " + str, e);
                        return false;
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public int getExternalStorageMountMode(int i, java.lang.String str) {
            int mountModeInternal = com.android.server.StorageManagerService.this.getMountModeInternal(i, str);
            if (com.android.server.StorageManagerService.LOCAL_LOGV) {
                android.util.Slog.v(com.android.server.StorageManagerService.TAG, "Resolved mode " + mountModeInternal + " for " + str + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + android.os.UserHandle.formatUid(i));
            }
            return mountModeInternal;
        }

        public boolean hasExternalStorageAccess(int i, java.lang.String str) {
            try {
                int checkOperation = com.android.server.StorageManagerService.this.mIAppOpsService.checkOperation(92, i, str);
                return checkOperation == 3 ? com.android.server.StorageManagerService.this.mIPackageManager.checkUidPermission("android.permission.MANAGE_EXTERNAL_STORAGE", i) == 0 : checkOperation == 0;
            } catch (android.os.RemoteException e) {
                android.util.Slog.w("Failed to check MANAGE_EXTERNAL_STORAGE access for " + str, e);
                return false;
            }
        }

        public void addResetListener(android.os.storage.StorageManagerInternal.ResetListener resetListener) {
            synchronized (this.mResetListeners) {
                this.mResetListeners.add(resetListener);
            }
        }

        public void onReset(android.os.IVold iVold) {
            synchronized (this.mResetListeners) {
                try {
                    java.util.Iterator<android.os.storage.StorageManagerInternal.ResetListener> it = this.mResetListeners.iterator();
                    while (it.hasNext()) {
                        it.next().onReset(iVold);
                    }
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
        }

        public void resetUser(int i) {
            com.android.server.StorageManagerService.this.mHandler.obtainMessage(10).sendToTarget();
        }

        public boolean hasLegacyExternalStorage(int i) {
            boolean contains;
            synchronized (com.android.server.StorageManagerService.this.mLock) {
                contains = com.android.server.StorageManagerService.this.mUidsWithLegacyExternalStorage.contains(java.lang.Integer.valueOf(i));
            }
            return contains;
        }

        public void prepareAppDataAfterInstall(java.lang.String str, int i) {
            for (java.io.File file : new android.os.Environment.UserEnvironment(android.os.UserHandle.getUserId(i)).buildExternalStorageAppObbDirs(str)) {
                if (file.getPath().startsWith(android.os.Environment.getDataPreloadsMediaDirectory().getPath())) {
                    android.util.Slog.i(com.android.server.StorageManagerService.TAG, "Skipping app data preparation for " + file);
                } else {
                    try {
                        com.android.server.StorageManagerService.this.mVold.fixupAppDir(file.getCanonicalPath() + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER, i);
                    } catch (android.os.RemoteException | android.os.ServiceSpecificException e) {
                        android.util.Log.e(com.android.server.StorageManagerService.TAG, "Failed to fixup app dir for " + str, e);
                    } catch (java.io.IOException e2) {
                        android.util.Log.e(com.android.server.StorageManagerService.TAG, "Failed to get canonical path for " + str);
                    }
                }
            }
        }

        public boolean isExternalStorageService(int i) {
            return com.android.server.StorageManagerService.this.mMediaStoreAuthorityAppId == android.os.UserHandle.getAppId(i);
        }

        public void freeCache(java.lang.String str, long j) {
            try {
                com.android.server.StorageManagerService.this.mStorageSessionController.freeCache(str, j);
            } catch (com.android.server.storage.StorageSessionController.ExternalStorageServiceException e) {
                android.util.Log.e(com.android.server.StorageManagerService.TAG, "Failed to free cache of vol : " + str, e);
            }
        }

        public boolean hasExternalStorage(int i, java.lang.String str) {
            return i == 1000 || getExternalStorageMountMode(i, str) != 0;
        }

        private void killAppForOpChange(int i, int i2) {
            android.app.IActivityManager service = android.app.ActivityManager.getService();
            try {
                service.killUid(android.os.UserHandle.getAppId(i2), -1, android.app.AppOpsManager.opToName(i) + " changed.");
            } catch (android.os.RemoteException e) {
            }
        }

        public void onAppOpsChanged(int i, int i2, @android.annotation.Nullable java.lang.String str, int i3, int i4) {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                switch (i) {
                    case 66:
                        if (i4 == 0 && i3 != 0) {
                            killAppForOpChange(i, i2);
                        }
                        return;
                    case 87:
                        com.android.server.StorageManagerService.this.updateLegacyStorageApps(str, i2, i3 == 0);
                        return;
                    case 92:
                        if (i3 != 0) {
                            killAppForOpChange(i, i2);
                        }
                        return;
                    default:
                        return;
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public java.util.List<java.lang.String> getPrimaryVolumeIds() {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            synchronized (com.android.server.StorageManagerService.this.mLock) {
                for (int i = 0; i < com.android.server.StorageManagerService.this.mVolumes.size(); i++) {
                    try {
                        android.os.storage.VolumeInfo volumeInfo = (android.os.storage.VolumeInfo) com.android.server.StorageManagerService.this.mVolumes.valueAt(i);
                        if (volumeInfo.isPrimary()) {
                            arrayList.add(volumeInfo.getId());
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
            return arrayList;
        }

        public void markCeStoragePrepared(int i) {
            synchronized (com.android.server.StorageManagerService.this.mLock) {
                com.android.server.StorageManagerService.this.mCeStoragePreparedUsers.add(java.lang.Integer.valueOf(i));
            }
        }

        public boolean isCeStoragePrepared(int i) {
            boolean contains;
            synchronized (com.android.server.StorageManagerService.this.mLock) {
                contains = com.android.server.StorageManagerService.this.mCeStoragePreparedUsers.contains(java.lang.Integer.valueOf(i));
            }
            return contains;
        }

        public void registerCloudProviderChangeListener(@android.annotation.NonNull android.os.storage.StorageManagerInternal.CloudProviderChangeListener cloudProviderChangeListener) {
            this.mCloudProviderChangeListeners.add(cloudProviderChangeListener);
            com.android.server.StorageManagerService.this.mHandler.obtainMessage(16, cloudProviderChangeListener).sendToTarget();
        }

        public void prepareUserStorageForMove(java.lang.String str, java.lang.String str2, java.util.List<android.content.pm.UserInfo> list) {
            try {
                com.android.server.StorageManagerService.this.prepareUserStorageForMoveInternal(str, str2, list);
            } catch (java.lang.Exception e) {
                throw new java.lang.RuntimeException(e);
            }
        }

        public android.os.IInstalld.IFsveritySetupAuthToken createFsveritySetupAuthToken(android.os.ParcelFileDescriptor parcelFileDescriptor, int i) throws java.io.IOException {
            try {
                return com.android.server.StorageManagerService.this.mInstaller.createFsveritySetupAuthToken(parcelFileDescriptor, i);
            } catch (com.android.server.pm.Installer.InstallerException e) {
                throw new java.io.IOException(e);
            }
        }

        public int enableFsverity(android.os.IInstalld.IFsveritySetupAuthToken iFsveritySetupAuthToken, java.lang.String str, java.lang.String str2) throws java.io.IOException {
            try {
                return com.android.server.StorageManagerService.this.mInstaller.enableFsverity(iFsveritySetupAuthToken, str, str2);
            } catch (com.android.server.pm.Installer.InstallerException e) {
                throw new java.io.IOException(e);
            }
        }
    }
}
