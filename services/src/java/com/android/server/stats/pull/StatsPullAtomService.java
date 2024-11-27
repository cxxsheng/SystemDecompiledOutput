package com.android.server.stats.pull;

/* loaded from: classes2.dex */
public class StatsPullAtomService extends com.android.server.SystemService {
    private static final long APP_OPS_SAMPLING_INITIALIZATION_DELAY_MILLIS = 45000;
    private static final int APP_OPS_SIZE_ESTIMATE = 2000;
    private static final java.lang.String APP_OPS_TARGET_COLLECTION_SIZE = "app_ops_target_collection_size";
    private static final java.lang.String COMMON_PERMISSION_PREFIX = "android.permission.";
    private static final int CPU_CYCLES_PER_UID_CLUSTER_VALUES = 3;
    private static final int CPU_TIME_PER_THREAD_FREQ_MAX_NUM_FREQUENCIES = 8;
    private static final java.lang.String DANGEROUS_PERMISSION_STATE_SAMPLE_RATE = "dangerous_permission_state_sample_rate";
    private static final boolean DEBUG = true;
    private static final int DIMENSION_KEY_SIZE_HARD_LIMIT = 800;
    private static final int DIMENSION_KEY_SIZE_SOFT_LIMIT = 500;
    public static final boolean ENABLE_MOBILE_DATA_STATS_AGGREGATED_PULLER;
    private static final long EXTERNAL_STATS_SYNC_TIMEOUT_MILLIS = 2000;
    private static final int MAX_PROCSTATS_RAW_SHARD_SIZE = 58982;
    private static final int MAX_PROCSTATS_SHARDS = 5;
    private static final int MAX_PROCSTATS_SHARD_SIZE = 49152;
    private static final long MILLIS_PER_SEC = 1000;
    private static final long MILLI_AMP_HR_TO_NANO_AMP_SECS = 3600000000L;
    private static final int MIN_CPU_TIME_PER_UID_FREQ = 10;
    private static final int OP_FLAGS_PULLED = 9;
    private static final java.lang.String RESULT_RECEIVER_CONTROLLER_KEY = "controller_activity";
    private static final java.lang.String TAG = "StatsPullAtomService";
    private com.android.server.stats.pull.AggregatedMobileDataStatsPuller mAggregatedMobileDataStatsPuller;
    private final java.lang.Object mAppOpsLock;

    @com.android.internal.annotations.GuardedBy({"mAttributedAppOpsLock"})
    private int mAppOpsSamplingRate;
    private final java.lang.Object mAppSizeLock;
    private final java.lang.Object mAppsOnExternalStorageInfoLock;
    private final java.lang.Object mAttributedAppOpsLock;

    @com.android.internal.annotations.GuardedBy({"mProcStatsLock"})
    private java.io.File mBaseDir;
    private final java.lang.Object mBinderCallsStatsExceptionsLock;
    private final java.lang.Object mBinderCallsStatsLock;
    private final java.lang.Object mBluetoothActivityInfoLock;
    private final java.lang.Object mBluetoothBytesTransferLock;
    private final java.lang.Object mBuildInformationLock;
    private final java.lang.Object mCategorySizeLock;
    private final android.content.Context mContext;
    private final java.lang.Object mCooldownDeviceLock;
    private final java.lang.Object mCpuActiveTimeLock;
    private final java.lang.Object mCpuClusterTimeLock;
    private final java.lang.Object mCpuTimePerClusterFreqLock;
    private final java.lang.Object mCpuTimePerThreadFreqLock;
    private final java.lang.Object mCpuTimePerUidFreqLock;
    private final java.lang.Object mCpuTimePerUidLock;

    @com.android.internal.annotations.GuardedBy({"mCpuActiveTimeLock"})
    private com.android.internal.os.KernelCpuUidTimeReader.KernelCpuUidActiveTimeReader mCpuUidActiveTimeReader;

    @com.android.internal.annotations.GuardedBy({"mClusterTimeLock"})
    private com.android.internal.os.KernelCpuUidTimeReader.KernelCpuUidClusterTimeReader mCpuUidClusterTimeReader;

    @com.android.internal.annotations.GuardedBy({"mCpuTimePerUidFreqLock"})
    private com.android.internal.os.KernelCpuUidTimeReader.KernelCpuUidFreqTimeReader mCpuUidFreqTimeReader;

    @com.android.internal.annotations.GuardedBy({"mCpuTimePerUidLock"})
    private com.android.internal.os.KernelCpuUidTimeReader.KernelCpuUidUserSysTimeReader mCpuUidUserSysTimeReader;

    @com.android.internal.annotations.GuardedBy({"mDangerousAppOpsListLock"})
    private final android.util.ArraySet<java.lang.Integer> mDangerousAppOpsList;
    private final java.lang.Object mDangerousAppOpsListLock;
    private final java.lang.Object mDangerousPermissionStateLock;
    private final java.lang.Object mDataBytesTransferLock;
    private final java.lang.Object mDebugElapsedClockLock;

    @com.android.internal.annotations.GuardedBy({"mDebugElapsedClockLock"})
    private long mDebugElapsedClockPreviousValue;

    @com.android.internal.annotations.GuardedBy({"mDebugElapsedClockLock"})
    private long mDebugElapsedClockPullCount;
    private final java.lang.Object mDebugFailingElapsedClockLock;

    @com.android.internal.annotations.GuardedBy({"mDebugFailingElapsedClockLock"})
    private long mDebugFailingElapsedClockPreviousValue;

    @com.android.internal.annotations.GuardedBy({"mDebugFailingElapsedClockLock"})
    private long mDebugFailingElapsedClockPullCount;
    private final java.lang.Object mDeviceCalculatedPowerUseLock;
    private final java.lang.Object mDirectoryUsageLock;
    private final java.lang.Object mDiskIoLock;
    private final java.lang.Object mDiskStatsLock;
    private final java.lang.Object mExternalStorageInfoLock;
    private final java.lang.Object mFaceSettingsLock;
    private final java.lang.Object mHealthHalLock;

    @com.android.internal.annotations.GuardedBy({"mHealthHalLock"})
    private com.android.server.health.HealthServiceWrapper mHealthService;

    @com.android.internal.annotations.GuardedBy({"mDataBytesTransferLock"})
    private final java.util.ArrayList<com.android.server.stats.pull.netstats.SubInfo> mHistoricalSubs;

    @com.android.internal.annotations.GuardedBy({"mKeystoreLock"})
    private android.security.metrics.IKeystoreMetrics mIKeystoreMetrics;
    private final java.lang.Object mInstalledIncrementalPackagesLock;
    private final java.lang.Object mIonHeapSizeLock;

    @com.android.internal.annotations.GuardedBy({"mCpuTimePerThreadFreqLock"})
    @android.annotation.Nullable
    private com.android.internal.os.KernelCpuThreadReaderDiff mKernelCpuThreadReader;
    private final java.lang.Object mKernelWakelockLock;

    @com.android.internal.annotations.GuardedBy({"mKernelWakelockLock"})
    private com.android.server.power.stats.KernelWakelockReader mKernelWakelockReader;
    private final java.lang.Object mKeystoreLock;
    private final java.lang.Object mLooperStatsLock;
    private final java.lang.Object mModemActivityInfoLock;

    @android.annotation.NonNull
    @com.android.internal.annotations.GuardedBy({"mDataBytesTransferLock"})
    private final java.util.ArrayList<com.android.server.stats.pull.netstats.NetworkStatsExt> mNetworkStatsBaselines;
    private android.app.usage.NetworkStatsManager mNetworkStatsManager;

    @com.android.internal.annotations.GuardedBy({"mNotificationStatsLock"})
    private android.app.INotificationManager mNotificationManagerService;
    private final java.lang.Object mNotificationRemoteViewsLock;
    private final java.lang.Object mNotificationStatsLock;
    private final java.lang.Object mNumBiometricsEnrolledLock;
    private final java.lang.Object mPowerProfileLock;
    private final java.lang.Object mProcStatsLock;
    private final java.lang.Object mProcessCpuTimeLock;

    @com.android.internal.annotations.GuardedBy({"mProcessCpuTimeLock"})
    private com.android.internal.os.ProcessCpuTracker mProcessCpuTracker;
    private final java.lang.Object mProcessMemoryHighWaterMarkLock;
    private final java.lang.Object mProcessMemoryStateLock;

    @com.android.internal.annotations.GuardedBy({"mProcStatsLock"})
    private com.android.internal.app.procstats.IProcessStats mProcessStatsService;
    private final java.lang.Object mProcessSystemIonHeapSizeLock;
    private final java.lang.Object mRoleHolderLock;
    private final java.lang.Object mRuntimeAppOpAccessMessageLock;
    private final java.lang.Object mSettingsStatsLock;
    private com.android.server.stats.pull.StatsPullAtomService.StatsPullAtomCallbackImpl mStatsCallbackImpl;
    private android.app.StatsManager mStatsManager;
    private com.android.server.stats.pull.StatsPullAtomService.StatsSubscriptionsListener mStatsSubscriptionsListener;
    private android.os.storage.StorageManager mStorageManager;

    @com.android.internal.annotations.GuardedBy({"mStoragedLock"})
    private android.os.IStoraged mStorageService;
    private final java.lang.Object mStoragedLock;

    @com.android.internal.annotations.GuardedBy({"mDiskIoLock"})
    private com.android.internal.os.StoragedUidIoStatsReader mStoragedUidIoStatsReader;
    private android.telephony.SubscriptionManager mSubscriptionManager;
    private com.android.internal.os.SelectedProcessCpuThreadReader mSurfaceFlingerProcessCpuThreadReader;
    private final java.lang.Object mSystemElapsedRealtimeLock;
    private final java.lang.Object mSystemIonHeapSizeLock;
    private final java.lang.Object mSystemUptimeLock;
    private android.telephony.TelephonyManager mTelephony;
    private final java.lang.Object mTemperatureLock;
    private final java.lang.Object mThermalLock;

    @com.android.internal.annotations.GuardedBy({"mThermalLock"})
    private android.os.IThermalService mThermalService;
    private final java.lang.Object mTimeZoneDataInfoLock;
    private final java.lang.Object mTimeZoneDetectionInfoLock;

    @com.android.internal.annotations.GuardedBy({"mKernelWakelockLock"})
    private com.android.server.power.stats.KernelWakelockStats mTmpWakelockStats;
    private final java.lang.Object mUwbActivityInfoLock;
    private android.uwb.UwbManager mUwbManager;
    private final java.lang.Object mWifiActivityInfoLock;
    private android.net.wifi.WifiManager mWifiManager;
    private static final int RANDOM_SEED = new java.util.Random().nextInt();
    private static final long NETSTATS_UID_DEFAULT_BUCKET_DURATION_MS = java.util.concurrent.TimeUnit.HOURS.toMillis(2);

    private native void initializeNativePullers();

    static {
        com.android.server.stats.Flags.addMobileBytesTransferByProcStatePuller();
        ENABLE_MOBILE_DATA_STATS_AGGREGATED_PULLER = false;
    }

    public StatsPullAtomService(android.content.Context context) {
        super(context);
        this.mThermalLock = new java.lang.Object();
        this.mStoragedLock = new java.lang.Object();
        this.mNotificationStatsLock = new java.lang.Object();
        this.mDebugElapsedClockPreviousValue = 0L;
        this.mDebugElapsedClockPullCount = 0L;
        this.mDebugFailingElapsedClockPreviousValue = 0L;
        this.mDebugFailingElapsedClockPullCount = 0L;
        this.mAppOpsSamplingRate = 0;
        this.mDangerousAppOpsListLock = new java.lang.Object();
        this.mDangerousAppOpsList = new android.util.ArraySet<>();
        this.mNetworkStatsBaselines = new java.util.ArrayList<>();
        this.mHistoricalSubs = new java.util.ArrayList<>();
        this.mAggregatedMobileDataStatsPuller = null;
        this.mDataBytesTransferLock = new java.lang.Object();
        this.mBluetoothBytesTransferLock = new java.lang.Object();
        this.mKernelWakelockLock = new java.lang.Object();
        this.mCpuTimePerClusterFreqLock = new java.lang.Object();
        this.mCpuTimePerUidLock = new java.lang.Object();
        this.mCpuTimePerUidFreqLock = new java.lang.Object();
        this.mCpuActiveTimeLock = new java.lang.Object();
        this.mCpuClusterTimeLock = new java.lang.Object();
        this.mWifiActivityInfoLock = new java.lang.Object();
        this.mModemActivityInfoLock = new java.lang.Object();
        this.mBluetoothActivityInfoLock = new java.lang.Object();
        this.mUwbActivityInfoLock = new java.lang.Object();
        this.mSystemElapsedRealtimeLock = new java.lang.Object();
        this.mSystemUptimeLock = new java.lang.Object();
        this.mProcessMemoryStateLock = new java.lang.Object();
        this.mProcessMemoryHighWaterMarkLock = new java.lang.Object();
        this.mSystemIonHeapSizeLock = new java.lang.Object();
        this.mIonHeapSizeLock = new java.lang.Object();
        this.mProcessSystemIonHeapSizeLock = new java.lang.Object();
        this.mTemperatureLock = new java.lang.Object();
        this.mCooldownDeviceLock = new java.lang.Object();
        this.mBinderCallsStatsLock = new java.lang.Object();
        this.mBinderCallsStatsExceptionsLock = new java.lang.Object();
        this.mLooperStatsLock = new java.lang.Object();
        this.mDiskStatsLock = new java.lang.Object();
        this.mDirectoryUsageLock = new java.lang.Object();
        this.mAppSizeLock = new java.lang.Object();
        this.mCategorySizeLock = new java.lang.Object();
        this.mNumBiometricsEnrolledLock = new java.lang.Object();
        this.mProcStatsLock = new java.lang.Object();
        this.mDiskIoLock = new java.lang.Object();
        this.mPowerProfileLock = new java.lang.Object();
        this.mProcessCpuTimeLock = new java.lang.Object();
        this.mCpuTimePerThreadFreqLock = new java.lang.Object();
        this.mDeviceCalculatedPowerUseLock = new java.lang.Object();
        this.mDebugElapsedClockLock = new java.lang.Object();
        this.mDebugFailingElapsedClockLock = new java.lang.Object();
        this.mBuildInformationLock = new java.lang.Object();
        this.mRoleHolderLock = new java.lang.Object();
        this.mTimeZoneDataInfoLock = new java.lang.Object();
        this.mTimeZoneDetectionInfoLock = new java.lang.Object();
        this.mExternalStorageInfoLock = new java.lang.Object();
        this.mAppsOnExternalStorageInfoLock = new java.lang.Object();
        this.mFaceSettingsLock = new java.lang.Object();
        this.mAppOpsLock = new java.lang.Object();
        this.mRuntimeAppOpAccessMessageLock = new java.lang.Object();
        this.mNotificationRemoteViewsLock = new java.lang.Object();
        this.mDangerousPermissionStateLock = new java.lang.Object();
        this.mHealthHalLock = new java.lang.Object();
        this.mAttributedAppOpsLock = new java.lang.Object();
        this.mSettingsStatsLock = new java.lang.Object();
        this.mInstalledIncrementalPackagesLock = new java.lang.Object();
        this.mKeystoreLock = new java.lang.Object();
        this.mContext = context;
    }

    private final class StatsPullAtomServiceInternalImpl extends com.android.server.stats.pull.StatsPullAtomServiceInternal {
        private StatsPullAtomServiceInternalImpl() {
        }

        @Override // com.android.server.stats.pull.StatsPullAtomServiceInternal
        public void noteUidProcessState(int i, int i2) {
            if (com.android.server.stats.pull.StatsPullAtomService.ENABLE_MOBILE_DATA_STATS_AGGREGATED_PULLER && com.android.server.stats.pull.StatsPullAtomService.this.mAggregatedMobileDataStatsPuller != null) {
                com.android.server.stats.pull.StatsPullAtomService.this.mAggregatedMobileDataStatsPuller.noteUidProcessState(i, i2, android.os.SystemClock.elapsedRealtime(), android.os.SystemClock.uptimeMillis());
            }
        }
    }

    private class StatsPullAtomCallbackImpl implements android.app.StatsManager.StatsPullAtomCallback {
        private StatsPullAtomCallbackImpl() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Removed duplicated region for block: B:673:0x0577 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public int onPullAtom(int i, java.util.List<android.util.StatsEvent> list) {
            int pullDataBytesTransferLocked;
            int pullKernelWakelockLocked;
            int pullBluetoothBytesTransferLocked;
            int pullBluetoothActivityInfoLocked;
            int pullCpuTimePerUidLocked;
            int pullCpuTimePerUidFreqLocked;
            int pullWifiActivityInfoLocked;
            int pullModemActivityInfoLocked;
            int pullProcessMemoryStateLocked;
            int pullSystemElapsedRealtimeLocked;
            int pullSystemUptimeLocked;
            int pullCpuActiveTimeLocked;
            int pullCpuClusterTimeLocked;
            int pullHealthHalLocked;
            int pullTemperatureLocked;
            int pullBinderCallsStatsLocked;
            int pullBinderCallsStatsExceptionsLocked;
            int pullLooperStatsLocked;
            int pullDiskStatsLocked;
            int pullDirectoryUsageLocked;
            int pullAppSizeLocked;
            int pullCategorySizeLocked;
            int pullProcStatsLocked;
            int pullNumBiometricsEnrolledLocked;
            int pullDiskIOLocked;
            int pullPowerProfileLocked;
            int pullProcStatsLocked2;
            int pullProcessCpuTimeLocked;
            int pullCpuTimePerThreadFreqLocked;
            int pullDeviceCalculatedPowerUseLocked;
            int pullProcessMemoryHighWaterMarkLocked;
            int pullBuildInformationLocked;
            int pullDebugElapsedClockLocked;
            int pullDebugFailingElapsedClockLocked;
            int pullNumBiometricsEnrolledLocked2;
            int pullRoleHolderLocked;
            int pullDangerousPermissionStateLocked;
            int pullTimeZoneDataInfoLocked;
            int pullExternalStorageInfoLocked;
            int pullSystemIonHeapSizeLocked;
            int pullAppsOnExternalStorageInfoLocked;
            int pullFaceSettingsLocked;
            int pullCooldownDeviceLocked;
            int pullAppOpsLocked;
            int pullProcessSystemIonHeapSizeLocked;
            int pullNotificationRemoteViewsLocked;
            int pullRuntimeAppOpAccessMessageLocked;
            int pullIonHeapSizeLocked;
            int pullAttributedAppOpsLocked;
            int pullSettingsStatsLocked;
            int pullCpuTimePerClusterFreqLocked;
            int pullCpuCyclesPerUidClusterLocked;
            int pullTimeZoneDetectorStateLocked;
            int pullInstalledIncrementalPackagesLocked;
            int pullProcessStateLocked;
            int pullProcessAssociationLocked;
            int pullUwbActivityInfoLocked;
            if (android.os.Trace.isTagEnabled(524288L)) {
                android.os.Trace.traceBegin(524288L, "StatsPull-" + i);
            }
            try {
                switch (i) {
                    case 10000:
                    case com.android.internal.util.FrameworkStatsLog.WIFI_BYTES_TRANSFER_BY_FG_BG /* 10001 */:
                    case com.android.internal.util.FrameworkStatsLog.MOBILE_BYTES_TRANSFER /* 10002 */:
                    case com.android.internal.util.FrameworkStatsLog.MOBILE_BYTES_TRANSFER_BY_FG_BG /* 10003 */:
                    case com.android.internal.util.FrameworkStatsLog.DATA_USAGE_BYTES_TRANSFER /* 10082 */:
                    case com.android.internal.util.FrameworkStatsLog.BYTES_TRANSFER_BY_TAG_AND_METERED /* 10083 */:
                    case com.android.internal.util.FrameworkStatsLog.OEM_MANAGED_BYTES_TRANSFER /* 10100 */:
                    case com.android.internal.util.FrameworkStatsLog.PROXY_BYTES_TRANSFER_BY_FG_BG /* 10200 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mDataBytesTransferLock) {
                            pullDataBytesTransferLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullDataBytesTransferLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullDataBytesTransferLocked;
                    case com.android.internal.util.FrameworkStatsLog.KERNEL_WAKELOCK /* 10004 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mKernelWakelockLock) {
                            pullKernelWakelockLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullKernelWakelockLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullKernelWakelockLocked;
                    case com.android.internal.util.FrameworkStatsLog.BLUETOOTH_BYTES_TRANSFER /* 10006 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mBluetoothBytesTransferLock) {
                            pullBluetoothBytesTransferLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullBluetoothBytesTransferLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullBluetoothBytesTransferLocked;
                    case com.android.internal.util.FrameworkStatsLog.BLUETOOTH_ACTIVITY_INFO /* 10007 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mBluetoothActivityInfoLock) {
                            pullBluetoothActivityInfoLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullBluetoothActivityInfoLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullBluetoothActivityInfoLocked;
                    case com.android.internal.util.FrameworkStatsLog.CPU_TIME_PER_UID /* 10009 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mCpuTimePerUidLock) {
                            pullCpuTimePerUidLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullCpuTimePerUidLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullCpuTimePerUidLocked;
                    case com.android.internal.util.FrameworkStatsLog.CPU_TIME_PER_UID_FREQ /* 10010 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mCpuTimePerUidFreqLock) {
                            pullCpuTimePerUidFreqLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullCpuTimePerUidFreqLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullCpuTimePerUidFreqLocked;
                    case com.android.internal.util.FrameworkStatsLog.WIFI_ACTIVITY_INFO /* 10011 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mWifiActivityInfoLock) {
                            pullWifiActivityInfoLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullWifiActivityInfoLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullWifiActivityInfoLocked;
                    case com.android.internal.util.FrameworkStatsLog.MODEM_ACTIVITY_INFO /* 10012 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mModemActivityInfoLock) {
                            pullModemActivityInfoLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullModemActivityInfoLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullModemActivityInfoLocked;
                    case com.android.internal.util.FrameworkStatsLog.PROCESS_MEMORY_STATE /* 10013 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mProcessMemoryStateLock) {
                            pullProcessMemoryStateLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullProcessMemoryStateLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullProcessMemoryStateLocked;
                    case com.android.internal.util.FrameworkStatsLog.SYSTEM_ELAPSED_REALTIME /* 10014 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mSystemElapsedRealtimeLock) {
                            pullSystemElapsedRealtimeLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullSystemElapsedRealtimeLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullSystemElapsedRealtimeLocked;
                    case com.android.internal.util.FrameworkStatsLog.SYSTEM_UPTIME /* 10015 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mSystemUptimeLock) {
                            pullSystemUptimeLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullSystemUptimeLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullSystemUptimeLocked;
                    case com.android.internal.util.FrameworkStatsLog.CPU_ACTIVE_TIME /* 10016 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mCpuActiveTimeLock) {
                            pullCpuActiveTimeLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullCpuActiveTimeLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullCpuActiveTimeLocked;
                    case com.android.internal.util.FrameworkStatsLog.CPU_CLUSTER_TIME /* 10017 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mCpuClusterTimeLock) {
                            pullCpuClusterTimeLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullCpuClusterTimeLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullCpuClusterTimeLocked;
                    case com.android.internal.util.FrameworkStatsLog.REMAINING_BATTERY_CAPACITY /* 10019 */:
                    case com.android.internal.util.FrameworkStatsLog.FULL_BATTERY_CAPACITY /* 10020 */:
                    case com.android.internal.util.FrameworkStatsLog.BATTERY_VOLTAGE /* 10030 */:
                    case com.android.internal.util.FrameworkStatsLog.BATTERY_LEVEL /* 10043 */:
                    case com.android.internal.util.FrameworkStatsLog.BATTERY_CYCLE_COUNT /* 10045 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mHealthHalLock) {
                            pullHealthHalLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullHealthHalLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullHealthHalLocked;
                    case com.android.internal.util.FrameworkStatsLog.TEMPERATURE /* 10021 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mTemperatureLock) {
                            pullTemperatureLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullTemperatureLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullTemperatureLocked;
                    case com.android.internal.util.FrameworkStatsLog.BINDER_CALLS /* 10022 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mBinderCallsStatsLock) {
                            pullBinderCallsStatsLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullBinderCallsStatsLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullBinderCallsStatsLocked;
                    case com.android.internal.util.FrameworkStatsLog.BINDER_CALLS_EXCEPTIONS /* 10023 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mBinderCallsStatsExceptionsLock) {
                            pullBinderCallsStatsExceptionsLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullBinderCallsStatsExceptionsLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullBinderCallsStatsExceptionsLocked;
                    case com.android.internal.util.FrameworkStatsLog.LOOPER_STATS /* 10024 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mLooperStatsLock) {
                            pullLooperStatsLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullLooperStatsLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullLooperStatsLocked;
                    case com.android.internal.util.FrameworkStatsLog.DISK_STATS /* 10025 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mDiskStatsLock) {
                            pullDiskStatsLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullDiskStatsLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullDiskStatsLocked;
                    case com.android.internal.util.FrameworkStatsLog.DIRECTORY_USAGE /* 10026 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mDirectoryUsageLock) {
                            pullDirectoryUsageLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullDirectoryUsageLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullDirectoryUsageLocked;
                    case com.android.internal.util.FrameworkStatsLog.APP_SIZE /* 10027 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mAppSizeLock) {
                            pullAppSizeLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullAppSizeLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullAppSizeLocked;
                    case com.android.internal.util.FrameworkStatsLog.CATEGORY_SIZE /* 10028 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mCategorySizeLock) {
                            pullCategorySizeLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullCategorySizeLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullCategorySizeLocked;
                    case com.android.internal.util.FrameworkStatsLog.PROC_STATS /* 10029 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mProcStatsLock) {
                            pullProcStatsLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullProcStatsLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullProcStatsLocked;
                    case com.android.internal.util.FrameworkStatsLog.NUM_FINGERPRINTS_ENROLLED /* 10031 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mNumBiometricsEnrolledLock) {
                            pullNumBiometricsEnrolledLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullNumBiometricsEnrolledLocked(1, i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullNumBiometricsEnrolledLocked;
                    case com.android.internal.util.FrameworkStatsLog.DISK_IO /* 10032 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mDiskIoLock) {
                            pullDiskIOLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullDiskIOLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullDiskIOLocked;
                    case com.android.internal.util.FrameworkStatsLog.POWER_PROFILE /* 10033 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mPowerProfileLock) {
                            pullPowerProfileLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullPowerProfileLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullPowerProfileLocked;
                    case com.android.internal.util.FrameworkStatsLog.PROC_STATS_PKG_PROC /* 10034 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mProcStatsLock) {
                            pullProcStatsLocked2 = com.android.server.stats.pull.StatsPullAtomService.this.pullProcStatsLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullProcStatsLocked2;
                    case com.android.internal.util.FrameworkStatsLog.PROCESS_CPU_TIME /* 10035 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mProcessCpuTimeLock) {
                            pullProcessCpuTimeLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullProcessCpuTimeLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullProcessCpuTimeLocked;
                    case com.android.internal.util.FrameworkStatsLog.CPU_TIME_PER_THREAD_FREQ /* 10037 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mCpuTimePerThreadFreqLock) {
                            pullCpuTimePerThreadFreqLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullCpuTimePerThreadFreqLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullCpuTimePerThreadFreqLocked;
                    case com.android.internal.util.FrameworkStatsLog.DEVICE_CALCULATED_POWER_USE /* 10039 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mDeviceCalculatedPowerUseLock) {
                            pullDeviceCalculatedPowerUseLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullDeviceCalculatedPowerUseLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullDeviceCalculatedPowerUseLocked;
                    case com.android.internal.util.FrameworkStatsLog.PROCESS_MEMORY_HIGH_WATER_MARK /* 10042 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mProcessMemoryHighWaterMarkLock) {
                            pullProcessMemoryHighWaterMarkLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullProcessMemoryHighWaterMarkLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullProcessMemoryHighWaterMarkLocked;
                    case com.android.internal.util.FrameworkStatsLog.BUILD_INFORMATION /* 10044 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mBuildInformationLock) {
                            pullBuildInformationLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullBuildInformationLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullBuildInformationLocked;
                    case com.android.internal.util.FrameworkStatsLog.DEBUG_ELAPSED_CLOCK /* 10046 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mDebugElapsedClockLock) {
                            pullDebugElapsedClockLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullDebugElapsedClockLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullDebugElapsedClockLocked;
                    case com.android.internal.util.FrameworkStatsLog.DEBUG_FAILING_ELAPSED_CLOCK /* 10047 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mDebugFailingElapsedClockLock) {
                            pullDebugFailingElapsedClockLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullDebugFailingElapsedClockLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullDebugFailingElapsedClockLocked;
                    case com.android.internal.util.FrameworkStatsLog.NUM_FACES_ENROLLED /* 10048 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mNumBiometricsEnrolledLock) {
                            pullNumBiometricsEnrolledLocked2 = com.android.server.stats.pull.StatsPullAtomService.this.pullNumBiometricsEnrolledLocked(4, i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullNumBiometricsEnrolledLocked2;
                    case com.android.internal.util.FrameworkStatsLog.ROLE_HOLDER /* 10049 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mRoleHolderLock) {
                            pullRoleHolderLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullRoleHolderLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullRoleHolderLocked;
                    case com.android.internal.util.FrameworkStatsLog.DANGEROUS_PERMISSION_STATE /* 10050 */:
                    case com.android.internal.util.FrameworkStatsLog.DANGEROUS_PERMISSION_STATE_SAMPLED /* 10067 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mDangerousPermissionStateLock) {
                            pullDangerousPermissionStateLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullDangerousPermissionStateLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullDangerousPermissionStateLocked;
                    case com.android.internal.util.FrameworkStatsLog.TIME_ZONE_DATA_INFO /* 10052 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mTimeZoneDataInfoLock) {
                            pullTimeZoneDataInfoLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullTimeZoneDataInfoLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullTimeZoneDataInfoLocked;
                    case com.android.internal.util.FrameworkStatsLog.EXTERNAL_STORAGE_INFO /* 10053 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mExternalStorageInfoLock) {
                            pullExternalStorageInfoLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullExternalStorageInfoLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullExternalStorageInfoLocked;
                    case com.android.internal.util.FrameworkStatsLog.SYSTEM_ION_HEAP_SIZE /* 10056 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mSystemIonHeapSizeLock) {
                            pullSystemIonHeapSizeLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullSystemIonHeapSizeLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullSystemIonHeapSizeLocked;
                    case com.android.internal.util.FrameworkStatsLog.APPS_ON_EXTERNAL_STORAGE_INFO /* 10057 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mAppsOnExternalStorageInfoLock) {
                            pullAppsOnExternalStorageInfoLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullAppsOnExternalStorageInfoLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullAppsOnExternalStorageInfoLocked;
                    case com.android.internal.util.FrameworkStatsLog.FACE_SETTINGS /* 10058 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mFaceSettingsLock) {
                            pullFaceSettingsLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullFaceSettingsLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullFaceSettingsLocked;
                    case com.android.internal.util.FrameworkStatsLog.COOLING_DEVICE /* 10059 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mCooldownDeviceLock) {
                            pullCooldownDeviceLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullCooldownDeviceLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullCooldownDeviceLocked;
                    case com.android.internal.util.FrameworkStatsLog.APP_OPS /* 10060 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mAppOpsLock) {
                            pullAppOpsLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullAppOpsLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullAppOpsLocked;
                    case com.android.internal.util.FrameworkStatsLog.PROCESS_SYSTEM_ION_HEAP_SIZE /* 10061 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mProcessSystemIonHeapSizeLock) {
                            pullProcessSystemIonHeapSizeLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullProcessSystemIonHeapSizeLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullProcessSystemIonHeapSizeLocked;
                    case com.android.internal.util.FrameworkStatsLog.PROCESS_MEMORY_SNAPSHOT /* 10064 */:
                        int pullProcessMemorySnapshot = com.android.server.stats.pull.StatsPullAtomService.this.pullProcessMemorySnapshot(i, list);
                        android.os.Trace.traceEnd(524288L);
                        return pullProcessMemorySnapshot;
                    case com.android.internal.util.FrameworkStatsLog.NOTIFICATION_REMOTE_VIEWS /* 10066 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mNotificationRemoteViewsLock) {
                            pullNotificationRemoteViewsLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullNotificationRemoteViewsLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullNotificationRemoteViewsLocked;
                    case com.android.internal.util.FrameworkStatsLog.RUNTIME_APP_OP_ACCESS /* 10069 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mRuntimeAppOpAccessMessageLock) {
                            pullRuntimeAppOpAccessMessageLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullRuntimeAppOpAccessMessageLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullRuntimeAppOpAccessMessageLocked;
                    case com.android.internal.util.FrameworkStatsLog.ION_HEAP_SIZE /* 10070 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mIonHeapSizeLock) {
                            pullIonHeapSizeLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullIonHeapSizeLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullIonHeapSizeLocked;
                    case com.android.internal.util.FrameworkStatsLog.ATTRIBUTED_APP_OPS /* 10075 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mAttributedAppOpsLock) {
                            pullAttributedAppOpsLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullAttributedAppOpsLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullAttributedAppOpsLocked;
                    case com.android.internal.util.FrameworkStatsLog.SETTING_SNAPSHOT /* 10080 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mSettingsStatsLock) {
                            pullSettingsStatsLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullSettingsStatsLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullSettingsStatsLocked;
                    case com.android.internal.util.FrameworkStatsLog.SYSTEM_MEMORY /* 10092 */:
                        int pullSystemMemory = com.android.server.stats.pull.StatsPullAtomService.this.pullSystemMemory(i, list);
                        android.os.Trace.traceEnd(524288L);
                        return pullSystemMemory;
                    case com.android.internal.util.FrameworkStatsLog.CPU_TIME_PER_CLUSTER_FREQ /* 10095 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mCpuTimePerClusterFreqLock) {
                            pullCpuTimePerClusterFreqLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullCpuTimePerClusterFreqLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullCpuTimePerClusterFreqLocked;
                    case com.android.internal.util.FrameworkStatsLog.CPU_CYCLES_PER_UID_CLUSTER /* 10096 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mCpuTimePerUidFreqLock) {
                            pullCpuCyclesPerUidClusterLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullCpuCyclesPerUidClusterLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullCpuCyclesPerUidClusterLocked;
                    case com.android.internal.util.FrameworkStatsLog.CPU_CYCLES_PER_THREAD_GROUP_CLUSTER /* 10098 */:
                        int pullCpuCyclesPerThreadGroupCluster = com.android.server.stats.pull.StatsPullAtomService.this.pullCpuCyclesPerThreadGroupCluster(i, list);
                        android.os.Trace.traceEnd(524288L);
                        return pullCpuCyclesPerThreadGroupCluster;
                    case com.android.internal.util.FrameworkStatsLog.TIME_ZONE_DETECTOR_STATE /* 10102 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mTimeZoneDetectionInfoLock) {
                            pullTimeZoneDetectorStateLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullTimeZoneDetectorStateLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullTimeZoneDetectorStateLocked;
                    case com.android.internal.util.FrameworkStatsLog.KEYSTORE2_STORAGE_STATS /* 10103 */:
                    case com.android.internal.util.FrameworkStatsLog.KEYSTORE2_KEY_CREATION_WITH_GENERAL_INFO /* 10118 */:
                    case com.android.internal.util.FrameworkStatsLog.KEYSTORE2_KEY_CREATION_WITH_AUTH_INFO /* 10119 */:
                    case com.android.internal.util.FrameworkStatsLog.KEYSTORE2_KEY_CREATION_WITH_PURPOSE_AND_MODES_INFO /* 10120 */:
                    case com.android.internal.util.FrameworkStatsLog.KEYSTORE2_ATOM_WITH_OVERFLOW /* 10121 */:
                    case com.android.internal.util.FrameworkStatsLog.KEYSTORE2_KEY_OPERATION_WITH_PURPOSE_AND_MODES_INFO /* 10122 */:
                    case com.android.internal.util.FrameworkStatsLog.KEYSTORE2_KEY_OPERATION_WITH_GENERAL_INFO /* 10123 */:
                    case com.android.internal.util.FrameworkStatsLog.RKP_ERROR_STATS /* 10124 */:
                    case com.android.internal.util.FrameworkStatsLog.KEYSTORE2_CRASH_STATS /* 10125 */:
                        int pullKeystoreAtoms = com.android.server.stats.pull.StatsPullAtomService.this.pullKeystoreAtoms(i, list);
                        android.os.Trace.traceEnd(524288L);
                        return pullKeystoreAtoms;
                    case com.android.internal.util.FrameworkStatsLog.PROCESS_DMABUF_MEMORY /* 10105 */:
                        int pullProcessDmabufMemory = com.android.server.stats.pull.StatsPullAtomService.this.pullProcessDmabufMemory(i, list);
                        android.os.Trace.traceEnd(524288L);
                        return pullProcessDmabufMemory;
                    case com.android.internal.util.FrameworkStatsLog.INSTALLED_INCREMENTAL_PACKAGE /* 10114 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mInstalledIncrementalPackagesLock) {
                            pullInstalledIncrementalPackagesLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullInstalledIncrementalPackagesLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullInstalledIncrementalPackagesLocked;
                    case com.android.internal.util.FrameworkStatsLog.VMSTAT /* 10117 */:
                        int pullVmStat = com.android.server.stats.pull.StatsPullAtomService.this.pullVmStat(i, list);
                        android.os.Trace.traceEnd(524288L);
                        return pullVmStat;
                    case com.android.internal.util.FrameworkStatsLog.ACCESSIBILITY_SHORTCUT_STATS /* 10127 */:
                        int pullAccessibilityShortcutStatsLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullAccessibilityShortcutStatsLocked(i, list);
                        android.os.Trace.traceEnd(524288L);
                        return pullAccessibilityShortcutStatsLocked;
                    case com.android.internal.util.FrameworkStatsLog.ACCESSIBILITY_FLOATING_MENU_STATS /* 10128 */:
                        int pullAccessibilityFloatingMenuStatsLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullAccessibilityFloatingMenuStatsLocked(i, list);
                        android.os.Trace.traceEnd(524288L);
                        return pullAccessibilityFloatingMenuStatsLocked;
                    case com.android.internal.util.FrameworkStatsLog.MEDIA_CAPABILITIES /* 10130 */:
                        int pullMediaCapabilitiesStats = com.android.server.stats.pull.StatsPullAtomService.this.pullMediaCapabilitiesStats(i, list);
                        android.os.Trace.traceEnd(524288L);
                        return pullMediaCapabilitiesStats;
                    case com.android.internal.util.FrameworkStatsLog.PINNED_FILE_SIZES_PER_PACKAGE /* 10150 */:
                        int pullSystemServerPinnerStats = com.android.server.stats.pull.StatsPullAtomService.this.pullSystemServerPinnerStats(i, list);
                        android.os.Trace.traceEnd(524288L);
                        return pullSystemServerPinnerStats;
                    case com.android.internal.util.FrameworkStatsLog.PENDING_INTENTS_PER_PACKAGE /* 10151 */:
                        int pullPendingIntentsPerPackage = com.android.server.stats.pull.StatsPullAtomService.this.pullPendingIntentsPerPackage(i, list);
                        android.os.Trace.traceEnd(524288L);
                        return pullPendingIntentsPerPackage;
                    case com.android.internal.util.FrameworkStatsLog.PROCESS_STATE /* 10171 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mProcStatsLock) {
                            pullProcessStateLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullProcessStateLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullProcessStateLocked;
                    case com.android.internal.util.FrameworkStatsLog.PROCESS_ASSOCIATION /* 10172 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mProcStatsLock) {
                            pullProcessAssociationLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullProcessAssociationLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullProcessAssociationLocked;
                    case com.android.internal.util.FrameworkStatsLog.HDR_CAPABILITIES /* 10175 */:
                        int pullHdrCapabilities = com.android.server.stats.pull.StatsPullAtomService.this.pullHdrCapabilities(i, list);
                        android.os.Trace.traceEnd(524288L);
                        return pullHdrCapabilities;
                    case com.android.internal.util.FrameworkStatsLog.UWB_ACTIVITY_INFO /* 10188 */:
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mUwbActivityInfoLock) {
                            pullUwbActivityInfoLocked = com.android.server.stats.pull.StatsPullAtomService.this.pullUwbActivityInfoLocked(i, list);
                        }
                        android.os.Trace.traceEnd(524288L);
                        return pullUwbActivityInfoLocked;
                    case com.android.internal.util.FrameworkStatsLog.CACHED_APPS_HIGH_WATERMARK /* 10189 */:
                        int pullCachedAppsHighWatermark = com.android.server.stats.pull.StatsPullAtomService.this.pullCachedAppsHighWatermark(i, list);
                        android.os.Trace.traceEnd(524288L);
                        return pullCachedAppsHighWatermark;
                    case com.android.internal.util.FrameworkStatsLog.MOBILE_BYTES_TRANSFER_BY_PROC_STATE /* 10204 */:
                        if (com.android.server.stats.pull.StatsPullAtomService.ENABLE_MOBILE_DATA_STATS_AGGREGATED_PULLER && com.android.server.stats.pull.StatsPullAtomService.this.mAggregatedMobileDataStatsPuller != null) {
                            int pullDataBytesTransfer = com.android.server.stats.pull.StatsPullAtomService.this.mAggregatedMobileDataStatsPuller.pullDataBytesTransfer(list);
                            android.os.Trace.traceEnd(524288L);
                            return pullDataBytesTransfer;
                        }
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mDataBytesTransferLock) {
                        }
                        break;
                    default:
                        throw new java.lang.UnsupportedOperationException("Unknown tagId=" + i);
                }
            } catch (java.lang.Throwable th) {
                android.os.Trace.traceEnd(524288L);
                throw th;
            }
        }
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        if (ENABLE_MOBILE_DATA_STATS_AGGREGATED_PULLER) {
            com.android.server.LocalServices.addService(com.android.server.stats.pull.StatsPullAtomServiceInternal.class, new com.android.server.stats.pull.StatsPullAtomService.StatsPullAtomServiceInternalImpl());
        }
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        super.onBootPhase(i);
        if (i == 500) {
            com.android.internal.os.BackgroundThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda20
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.stats.pull.StatsPullAtomService.this.lambda$onBootPhase$0();
                }
            });
        } else if (i == 600) {
            com.android.internal.os.BackgroundThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda21
                @Override // java.lang.Runnable
                public final void run() {
                    com.android.server.stats.pull.StatsPullAtomService.this.lambda$onBootPhase$1();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBootPhase$0() {
        initializeNativePullers();
        initializePullersState();
        registerPullers();
        registerEventListeners();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onBootPhase$1() {
        initAndRegisterNetworkStatsPullers();
        initAndRegisterDeferredPullers();
    }

    void initializePullersState() {
        this.mStatsManager = (android.app.StatsManager) this.mContext.getSystemService("stats");
        this.mWifiManager = (android.net.wifi.WifiManager) this.mContext.getSystemService("wifi");
        this.mTelephony = (android.telephony.TelephonyManager) this.mContext.getSystemService(com.android.server.autofill.HintsHelper.AUTOFILL_HINT_PHONE);
        this.mSubscriptionManager = (android.telephony.SubscriptionManager) this.mContext.getSystemService("telephony_subscription_service");
        this.mStatsSubscriptionsListener = new com.android.server.stats.pull.StatsPullAtomService.StatsSubscriptionsListener(this.mSubscriptionManager);
        this.mStorageManager = (android.os.storage.StorageManager) this.mContext.getSystemService(android.os.storage.StorageManager.class);
        this.mNetworkStatsManager = (android.app.usage.NetworkStatsManager) this.mContext.getSystemService(android.app.usage.NetworkStatsManager.class);
        initMobileDataStatsPuller();
        this.mStoragedUidIoStatsReader = new com.android.internal.os.StoragedUidIoStatsReader();
        this.mBaseDir = new java.io.File(com.android.server.SystemServiceManager.ensureSystemDir(), "stats_pull");
        this.mBaseDir.mkdirs();
        this.mCpuUidUserSysTimeReader = new com.android.internal.os.KernelCpuUidTimeReader.KernelCpuUidUserSysTimeReader(false);
        this.mCpuUidFreqTimeReader = new com.android.internal.os.KernelCpuUidTimeReader.KernelCpuUidFreqTimeReader(false);
        this.mCpuUidActiveTimeReader = new com.android.internal.os.KernelCpuUidTimeReader.KernelCpuUidActiveTimeReader(false);
        this.mCpuUidClusterTimeReader = new com.android.internal.os.KernelCpuUidTimeReader.KernelCpuUidClusterTimeReader(false);
        this.mKernelWakelockReader = new com.android.server.power.stats.KernelWakelockReader();
        this.mTmpWakelockStats = new com.android.server.power.stats.KernelWakelockStats();
        this.mKernelCpuThreadReader = com.android.internal.os.KernelCpuThreadReaderSettingsObserver.getSettingsModifiedReader(this.mContext);
        try {
            this.mHealthService = com.android.server.health.HealthServiceWrapper.create(null);
        } catch (android.os.RemoteException | java.util.NoSuchElementException e) {
            android.util.Slog.e(TAG, "failed to initialize healthHalWrapper");
        }
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        for (int i = 0; i < 148; i++) {
            java.lang.String opToPermission = android.app.AppOpsManager.opToPermission(i);
            if (opToPermission != null) {
                try {
                    if (packageManager.getPermissionInfo(opToPermission, 0).getProtection() == 1) {
                        this.mDangerousAppOpsList.add(java.lang.Integer.valueOf(i));
                    }
                } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
                }
            }
        }
        this.mSurfaceFlingerProcessCpuThreadReader = new com.android.internal.os.SelectedProcessCpuThreadReader("/system/bin/surfaceflinger");
        getIKeystoreMetricsService();
    }

    void registerEventListeners() {
        byte b = 0;
        ((android.net.ConnectivityManager) this.mContext.getSystemService("connectivity")).registerNetworkCallback(new android.net.NetworkRequest.Builder().build(), new com.android.server.stats.pull.StatsPullAtomService.ConnectivityStatsCallback());
        android.os.IThermalService iThermalService = getIThermalService();
        if (iThermalService != null) {
            try {
                iThermalService.registerThermalEventListener(new com.android.server.stats.pull.StatsPullAtomService.ThermalEventListener());
                android.util.Slog.i(TAG, "register thermal listener successfully");
            } catch (android.os.RemoteException e) {
                android.util.Slog.i(TAG, "failed to register thermal listener");
            }
        }
    }

    void registerPullers() {
        android.util.Slog.d(TAG, "Registering pullers with statsd");
        this.mStatsCallbackImpl = new com.android.server.stats.pull.StatsPullAtomService.StatsPullAtomCallbackImpl();
        registerBluetoothBytesTransfer();
        registerKernelWakelock();
        registerCpuTimePerClusterFreq();
        registerCpuTimePerUid();
        registerCpuCyclesPerUidCluster();
        registerCpuTimePerUidFreq();
        registerCpuCyclesPerThreadGroupCluster();
        registerCpuActiveTime();
        registerCpuClusterTime();
        registerWifiActivityInfo();
        registerModemActivityInfo();
        registerBluetoothActivityInfo();
        registerSystemElapsedRealtime();
        registerSystemUptime();
        registerProcessMemoryState();
        registerProcessMemoryHighWaterMark();
        registerProcessMemorySnapshot();
        registerSystemIonHeapSize();
        registerIonHeapSize();
        registerProcessSystemIonHeapSize();
        registerSystemMemory();
        registerProcessDmabufMemory();
        registerVmStat();
        registerTemperature();
        registerCoolingDevice();
        registerBinderCallsStats();
        registerBinderCallsStatsExceptions();
        registerLooperStats();
        registerDiskStats();
        registerDirectoryUsage();
        registerAppSize();
        registerCategorySize();
        registerNumFingerprintsEnrolled();
        registerNumFacesEnrolled();
        registerProcStats();
        registerProcStatsPkgProc();
        registerProcessState();
        registerProcessAssociation();
        registerDiskIO();
        registerPowerProfile();
        registerProcessCpuTime();
        registerCpuTimePerThreadFreq();
        registerDeviceCalculatedPowerUse();
        registerDebugElapsedClock();
        registerDebugFailingElapsedClock();
        registerBuildInformation();
        registerRoleHolder();
        registerTimeZoneDataInfo();
        registerTimeZoneDetectorState();
        registerExternalStorageInfo();
        registerAppsOnExternalStorageInfo();
        registerFaceSettings();
        registerAppOps();
        registerAttributedAppOps();
        registerRuntimeAppOpAccessMessage();
        registerNotificationRemoteViews();
        registerDangerousPermissionState();
        registerDangerousPermissionStateSampled();
        registerBatteryLevel();
        registerRemainingBatteryCapacity();
        registerFullBatteryCapacity();
        registerBatteryVoltage();
        registerBatteryCycleCount();
        registerSettingsStats();
        registerInstalledIncrementalPackages();
        registerKeystoreStorageStats();
        registerKeystoreKeyCreationWithGeneralInfo();
        registerKeystoreKeyCreationWithAuthInfo();
        registerKeystoreKeyCreationWithPurposeModesInfo();
        registerKeystoreAtomWithOverflow();
        registerKeystoreKeyOperationWithPurposeAndModesInfo();
        registerKeystoreKeyOperationWithGeneralInfo();
        registerRkpErrorStats();
        registerKeystoreCrashStats();
        registerAccessibilityShortcutStats();
        registerAccessibilityFloatingMenuStats();
        registerMediaCapabilitiesStats();
        registerPendingIntentsPerPackagePuller();
        registerPinnerServiceStats();
        registerHdrCapabilitiesPuller();
        registerCachedAppsHighWatermarkPuller();
    }

    private void initMobileDataStatsPuller() {
        android.util.Slog.d(TAG, "ENABLE_MOBILE_DATA_STATS_AGGREGATED_PULLER = " + ENABLE_MOBILE_DATA_STATS_AGGREGATED_PULLER);
        if (ENABLE_MOBILE_DATA_STATS_AGGREGATED_PULLER) {
            this.mAggregatedMobileDataStatsPuller = new com.android.server.stats.pull.AggregatedMobileDataStatsPuller(this.mNetworkStatsManager);
        }
    }

    private void initAndRegisterNetworkStatsPullers() {
        android.util.Slog.d(TAG, "Registering NetworkStats pullers with statsd");
        boolean canQueryNetworkStatsForTypeProxy = canQueryNetworkStatsForTypeProxy();
        synchronized (this.mDataBytesTransferLock) {
            try {
                this.mNetworkStatsBaselines.addAll(collectNetworkStatsSnapshotForAtom(10000));
                this.mNetworkStatsBaselines.addAll(collectNetworkStatsSnapshotForAtom(com.android.internal.util.FrameworkStatsLog.WIFI_BYTES_TRANSFER_BY_FG_BG));
                this.mNetworkStatsBaselines.addAll(collectNetworkStatsSnapshotForAtom(com.android.internal.util.FrameworkStatsLog.MOBILE_BYTES_TRANSFER));
                this.mNetworkStatsBaselines.addAll(collectNetworkStatsSnapshotForAtom(com.android.internal.util.FrameworkStatsLog.MOBILE_BYTES_TRANSFER_BY_FG_BG));
                this.mNetworkStatsBaselines.addAll(collectNetworkStatsSnapshotForAtom(com.android.internal.util.FrameworkStatsLog.BYTES_TRANSFER_BY_TAG_AND_METERED));
                this.mNetworkStatsBaselines.addAll(collectNetworkStatsSnapshotForAtom(com.android.internal.util.FrameworkStatsLog.DATA_USAGE_BYTES_TRANSFER));
                this.mNetworkStatsBaselines.addAll(collectNetworkStatsSnapshotForAtom(com.android.internal.util.FrameworkStatsLog.OEM_MANAGED_BYTES_TRANSFER));
                if (canQueryNetworkStatsForTypeProxy) {
                    this.mNetworkStatsBaselines.addAll(collectNetworkStatsSnapshotForAtom(com.android.internal.util.FrameworkStatsLog.PROXY_BYTES_TRANSFER_BY_FG_BG));
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        this.mSubscriptionManager.addOnSubscriptionsChangedListener(com.android.internal.os.BackgroundThread.getExecutor(), this.mStatsSubscriptionsListener);
        registerWifiBytesTransfer();
        registerWifiBytesTransferBackground();
        registerMobileBytesTransfer();
        registerMobileBytesTransferBackground();
        if (ENABLE_MOBILE_DATA_STATS_AGGREGATED_PULLER) {
            registerMobileBytesTransferByProcState();
        }
        registerBytesTransferByTagAndMetered();
        registerDataUsageBytesTransfer();
        registerOemManagedBytesTransfer();
        if (canQueryNetworkStatsForTypeProxy) {
            registerProxyBytesTransferBackground();
        }
    }

    private void registerMobileBytesTransferByProcState() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.MOBILE_BYTES_TRANSFER_BY_PROC_STATE, new android.app.StatsManager.PullAtomMetadata.Builder().setAdditiveFields(new int[]{3, 4, 5, 6}).build(), com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private void initAndRegisterDeferredPullers() {
        this.mUwbManager = this.mContext.getPackageManager().hasSystemFeature("android.hardware.uwb") ? (android.uwb.UwbManager) this.mContext.getSystemService(android.uwb.UwbManager.class) : null;
        registerUwbActivityInfo();
    }

    private android.os.IThermalService getIThermalService() {
        android.os.IThermalService iThermalService;
        synchronized (this.mThermalLock) {
            if (this.mThermalService == null) {
                this.mThermalService = android.os.IThermalService.Stub.asInterface(android.os.ServiceManager.getService("thermalservice"));
                if (this.mThermalService != null) {
                    try {
                        this.mThermalService.asBinder().linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda22
                            @Override // android.os.IBinder.DeathRecipient
                            public final void binderDied() {
                                com.android.server.stats.pull.StatsPullAtomService.this.lambda$getIThermalService$2();
                            }
                        }, 0);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(TAG, "linkToDeath with thermalService failed", e);
                        this.mThermalService = null;
                    }
                }
            }
            iThermalService = this.mThermalService;
        }
        return iThermalService;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getIThermalService$2() {
        synchronized (this.mThermalLock) {
            this.mThermalService = null;
        }
    }

    private android.security.metrics.IKeystoreMetrics getIKeystoreMetricsService() {
        android.security.metrics.IKeystoreMetrics iKeystoreMetrics;
        synchronized (this.mKeystoreLock) {
            if (this.mIKeystoreMetrics == null) {
                this.mIKeystoreMetrics = android.security.metrics.IKeystoreMetrics.Stub.asInterface(android.os.ServiceManager.getService("android.security.metrics"));
                if (this.mIKeystoreMetrics != null) {
                    try {
                        this.mIKeystoreMetrics.asBinder().linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda25
                            @Override // android.os.IBinder.DeathRecipient
                            public final void binderDied() {
                                com.android.server.stats.pull.StatsPullAtomService.this.lambda$getIKeystoreMetricsService$3();
                            }
                        }, 0);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(TAG, "linkToDeath with IKeystoreMetrics failed", e);
                        this.mIKeystoreMetrics = null;
                    }
                }
            }
            iKeystoreMetrics = this.mIKeystoreMetrics;
        }
        return iKeystoreMetrics;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getIKeystoreMetricsService$3() {
        synchronized (this.mKeystoreLock) {
            this.mIKeystoreMetrics = null;
        }
    }

    private android.os.IStoraged getIStoragedService() {
        synchronized (this.mStoragedLock) {
            try {
                if (this.mStorageService == null) {
                    this.mStorageService = android.os.IStoraged.Stub.asInterface(android.os.ServiceManager.getService("storaged"));
                }
                if (this.mStorageService != null) {
                    try {
                        this.mStorageService.asBinder().linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda24
                            @Override // android.os.IBinder.DeathRecipient
                            public final void binderDied() {
                                com.android.server.stats.pull.StatsPullAtomService.this.lambda$getIStoragedService$4();
                            }
                        }, 0);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(TAG, "linkToDeath with storagedService failed", e);
                        this.mStorageService = null;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return this.mStorageService;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getIStoragedService$4() {
        synchronized (this.mStoragedLock) {
            this.mStorageService = null;
        }
    }

    private android.app.INotificationManager getINotificationManagerService() {
        synchronized (this.mNotificationStatsLock) {
            try {
                if (this.mNotificationManagerService == null) {
                    this.mNotificationManagerService = android.app.INotificationManager.Stub.asInterface(android.os.ServiceManager.getService("notification"));
                }
                if (this.mNotificationManagerService != null) {
                    try {
                        this.mNotificationManagerService.asBinder().linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda9
                            @Override // android.os.IBinder.DeathRecipient
                            public final void binderDied() {
                                com.android.server.stats.pull.StatsPullAtomService.this.lambda$getINotificationManagerService$5();
                            }
                        }, 0);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(TAG, "linkToDeath with notificationManager failed", e);
                        this.mNotificationManagerService = null;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return this.mNotificationManagerService;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getINotificationManagerService$5() {
        synchronized (this.mNotificationStatsLock) {
            this.mNotificationManagerService = null;
        }
    }

    private com.android.internal.app.procstats.IProcessStats getIProcessStatsService() {
        synchronized (this.mProcStatsLock) {
            try {
                if (this.mProcessStatsService == null) {
                    this.mProcessStatsService = com.android.internal.app.procstats.IProcessStats.Stub.asInterface(android.os.ServiceManager.getService("procstats"));
                }
                if (this.mProcessStatsService != null) {
                    try {
                        this.mProcessStatsService.asBinder().linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda2
                            @Override // android.os.IBinder.DeathRecipient
                            public final void binderDied() {
                                com.android.server.stats.pull.StatsPullAtomService.this.lambda$getIProcessStatsService$6();
                            }
                        }, 0);
                    } catch (android.os.RemoteException e) {
                        android.util.Slog.e(TAG, "linkToDeath with ProcessStats failed", e);
                        this.mProcessStatsService = null;
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return this.mProcessStatsService;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getIProcessStatsService$6() {
        synchronized (this.mProcStatsLock) {
            this.mProcessStatsService = null;
        }
    }

    private void registerWifiBytesTransfer() {
        this.mStatsManager.setPullAtomCallback(10000, new android.app.StatsManager.PullAtomMetadata.Builder().setAdditiveFields(new int[]{2, 3, 4, 5}).build(), com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    @android.annotation.NonNull
    private java.util.List<com.android.server.stats.pull.netstats.NetworkStatsExt> collectNetworkStatsSnapshotForAtom(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        switch (i) {
            case 10000:
                android.net.NetworkStats uidNetworkStatsSnapshotForTransport = getUidNetworkStatsSnapshotForTransport(1);
                if (uidNetworkStatsSnapshotForTransport != null) {
                    arrayList.add(new com.android.server.stats.pull.netstats.NetworkStatsExt(sliceNetworkStatsByUid(uidNetworkStatsSnapshotForTransport), new int[]{1}, false));
                }
                return arrayList;
            case com.android.internal.util.FrameworkStatsLog.WIFI_BYTES_TRANSFER_BY_FG_BG /* 10001 */:
                android.net.NetworkStats uidNetworkStatsSnapshotForTransport2 = getUidNetworkStatsSnapshotForTransport(1);
                if (uidNetworkStatsSnapshotForTransport2 != null) {
                    arrayList.add(new com.android.server.stats.pull.netstats.NetworkStatsExt(sliceNetworkStatsByUidAndFgbg(uidNetworkStatsSnapshotForTransport2), new int[]{1}, true));
                }
                return arrayList;
            case com.android.internal.util.FrameworkStatsLog.MOBILE_BYTES_TRANSFER /* 10002 */:
                android.net.NetworkStats uidNetworkStatsSnapshotForTransport3 = getUidNetworkStatsSnapshotForTransport(0);
                if (uidNetworkStatsSnapshotForTransport3 != null) {
                    arrayList.add(new com.android.server.stats.pull.netstats.NetworkStatsExt(sliceNetworkStatsByUid(uidNetworkStatsSnapshotForTransport3), new int[]{0}, false));
                }
                return arrayList;
            case com.android.internal.util.FrameworkStatsLog.MOBILE_BYTES_TRANSFER_BY_FG_BG /* 10003 */:
                android.net.NetworkStats uidNetworkStatsSnapshotForTransport4 = getUidNetworkStatsSnapshotForTransport(0);
                if (uidNetworkStatsSnapshotForTransport4 != null) {
                    arrayList.add(new com.android.server.stats.pull.netstats.NetworkStatsExt(sliceNetworkStatsByUidAndFgbg(uidNetworkStatsSnapshotForTransport4), new int[]{0}, true));
                }
                return arrayList;
            case com.android.internal.util.FrameworkStatsLog.DATA_USAGE_BYTES_TRANSFER /* 10082 */:
                java.util.Iterator<com.android.server.stats.pull.netstats.SubInfo> it = this.mHistoricalSubs.iterator();
                while (it.hasNext()) {
                    arrayList.addAll(getDataUsageBytesTransferSnapshotForSub(it.next()));
                }
                return arrayList;
            case com.android.internal.util.FrameworkStatsLog.BYTES_TRANSFER_BY_TAG_AND_METERED /* 10083 */:
                android.net.NetworkStats uidNetworkStatsSnapshotForTemplate = getUidNetworkStatsSnapshotForTemplate(new android.net.NetworkTemplate.Builder(4).build(), true);
                android.net.NetworkStats uidNetworkStatsSnapshotForTemplate2 = getUidNetworkStatsSnapshotForTemplate(new android.net.NetworkTemplate.Builder(1).setMeteredness(1).build(), true);
                if (uidNetworkStatsSnapshotForTemplate != null && uidNetworkStatsSnapshotForTemplate2 != null) {
                    arrayList.add(new com.android.server.stats.pull.netstats.NetworkStatsExt(sliceNetworkStatsByUidTagAndMetered(uidNetworkStatsSnapshotForTemplate.add(uidNetworkStatsSnapshotForTemplate2)), new int[]{1, 0}, false, true, true, 0, null, -1, false));
                }
                return arrayList;
            case com.android.internal.util.FrameworkStatsLog.OEM_MANAGED_BYTES_TRANSFER /* 10100 */:
                arrayList.addAll(getDataUsageBytesTransferSnapshotForOemManaged());
                return arrayList;
            case com.android.internal.util.FrameworkStatsLog.PROXY_BYTES_TRANSFER_BY_FG_BG /* 10200 */:
                android.net.NetworkStats uidNetworkStatsSnapshotForTemplate3 = getUidNetworkStatsSnapshotForTemplate(new android.net.NetworkTemplate.Builder(9).build(), true);
                if (uidNetworkStatsSnapshotForTemplate3 != null) {
                    arrayList.add(new com.android.server.stats.pull.netstats.NetworkStatsExt(sliceNetworkStatsByUidTagAndMetered(uidNetworkStatsSnapshotForTemplate3), new int[]{2}, true, false, false, 0, null, -1, true));
                }
                return arrayList;
            default:
                throw new java.lang.IllegalArgumentException("Unknown atomTag " + i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int pullDataBytesTransferLocked(int i, @android.annotation.NonNull java.util.List<android.util.StatsEvent> list) {
        java.util.List<com.android.server.stats.pull.netstats.NetworkStatsExt> collectNetworkStatsSnapshotForAtom = collectNetworkStatsSnapshotForAtom(i);
        int i2 = 1;
        if (collectNetworkStatsSnapshotForAtom == null) {
            android.util.Slog.e(TAG, "current snapshot is null for " + i + ", return.");
            return 1;
        }
        java.util.Iterator<com.android.server.stats.pull.netstats.NetworkStatsExt> it = collectNetworkStatsSnapshotForAtom.iterator();
        while (it.hasNext()) {
            final com.android.server.stats.pull.netstats.NetworkStatsExt next = it.next();
            com.android.server.stats.pull.netstats.NetworkStatsExt networkStatsExt = (com.android.server.stats.pull.netstats.NetworkStatsExt) com.android.internal.util.CollectionUtils.find(this.mNetworkStatsBaselines, new java.util.function.Predicate() { // from class: com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda10
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$pullDataBytesTransferLocked$7;
                    lambda$pullDataBytesTransferLocked$7 = com.android.server.stats.pull.StatsPullAtomService.lambda$pullDataBytesTransferLocked$7(com.android.server.stats.pull.netstats.NetworkStatsExt.this, (com.android.server.stats.pull.netstats.NetworkStatsExt) obj);
                    return lambda$pullDataBytesTransferLocked$7;
                }
            });
            if (networkStatsExt == null) {
                android.util.Slog.e(TAG, "baseline is null for " + i + ", return.");
                return i2;
            }
            java.util.Iterator<com.android.server.stats.pull.netstats.NetworkStatsExt> it2 = it;
            com.android.server.stats.pull.netstats.NetworkStatsExt networkStatsExt2 = new com.android.server.stats.pull.netstats.NetworkStatsExt(removeEmptyEntries(next.stats.subtract(networkStatsExt.stats)), next.transports, next.slicedByFgbg, next.slicedByTag, next.slicedByMetered, next.ratType, next.subInfo, next.oemManaged, next.isTypeProxy);
            if (networkStatsExt2.stats.iterator().hasNext()) {
                switch (i) {
                    case com.android.internal.util.FrameworkStatsLog.DATA_USAGE_BYTES_TRANSFER /* 10082 */:
                        addDataUsageBytesTransferAtoms(networkStatsExt2, list);
                        break;
                    case com.android.internal.util.FrameworkStatsLog.BYTES_TRANSFER_BY_TAG_AND_METERED /* 10083 */:
                        addBytesTransferByTagAndMeteredAtoms(networkStatsExt2, list);
                        break;
                    case com.android.internal.util.FrameworkStatsLog.OEM_MANAGED_BYTES_TRANSFER /* 10100 */:
                        addOemDataUsageBytesTransferAtoms(networkStatsExt2, list);
                        break;
                    default:
                        addNetworkStats(i, list, networkStatsExt2);
                        break;
                }
                it = it2;
                i2 = 1;
            } else {
                it = it2;
                i2 = 1;
            }
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$pullDataBytesTransferLocked$7(com.android.server.stats.pull.netstats.NetworkStatsExt networkStatsExt, com.android.server.stats.pull.netstats.NetworkStatsExt networkStatsExt2) {
        return networkStatsExt2.hasSameSlicing(networkStatsExt);
    }

    @android.annotation.NonNull
    private static android.net.NetworkStats removeEmptyEntries(android.net.NetworkStats networkStats) {
        android.net.NetworkStats networkStats2 = new android.net.NetworkStats(0L, 1);
        java.util.Iterator it = networkStats.iterator();
        while (it.hasNext()) {
            android.net.NetworkStats.Entry entry = (android.net.NetworkStats.Entry) it.next();
            if (entry.getRxBytes() != 0 || entry.getRxPackets() != 0 || entry.getTxBytes() != 0 || entry.getTxPackets() != 0 || entry.getOperations() != 0) {
                networkStats2 = networkStats2.addEntry(entry);
            }
        }
        return networkStats2;
    }

    private void addNetworkStats(int i, @android.annotation.NonNull java.util.List<android.util.StatsEvent> list, @android.annotation.NonNull com.android.server.stats.pull.netstats.NetworkStatsExt networkStatsExt) {
        android.util.StatsEvent buildStatsEvent;
        java.util.Iterator it = networkStatsExt.stats.iterator();
        while (it.hasNext()) {
            android.net.NetworkStats.Entry entry = (android.net.NetworkStats.Entry) it.next();
            if (networkStatsExt.slicedByFgbg) {
                buildStatsEvent = com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, entry.getUid(), entry.getSet() > 0, entry.getRxBytes(), entry.getRxPackets(), entry.getTxBytes(), entry.getTxPackets());
            } else {
                buildStatsEvent = com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, entry.getUid(), entry.getRxBytes(), entry.getRxPackets(), entry.getTxBytes(), entry.getTxPackets());
            }
            list.add(buildStatsEvent);
        }
    }

    private void addBytesTransferByTagAndMeteredAtoms(@android.annotation.NonNull com.android.server.stats.pull.netstats.NetworkStatsExt networkStatsExt, @android.annotation.NonNull java.util.List<android.util.StatsEvent> list) {
        boolean z = networkStatsExt.ratType == -2;
        java.util.Iterator it = networkStatsExt.stats.iterator();
        while (it.hasNext()) {
            android.net.NetworkStats.Entry entry = (android.net.NetworkStats.Entry) it.next();
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(com.android.internal.util.FrameworkStatsLog.BYTES_TRANSFER_BY_TAG_AND_METERED, entry.getUid(), entry.getMetered() == 1, entry.getTag(), entry.getRxBytes(), entry.getRxPackets(), entry.getTxBytes(), entry.getTxPackets(), z ? 13 : networkStatsExt.ratType));
        }
    }

    private void addDataUsageBytesTransferAtoms(@android.annotation.NonNull com.android.server.stats.pull.netstats.NetworkStatsExt networkStatsExt, @android.annotation.NonNull java.util.List<android.util.StatsEvent> list) {
        int i;
        boolean z = networkStatsExt.ratType == -2;
        boolean z2 = z || networkStatsExt.ratType == 20;
        java.util.Iterator it = networkStatsExt.stats.iterator();
        while (it.hasNext()) {
            android.net.NetworkStats.Entry entry = (android.net.NetworkStats.Entry) it.next();
            int set = entry.getSet();
            long rxBytes = entry.getRxBytes();
            long rxPackets = entry.getRxPackets();
            long txBytes = entry.getTxBytes();
            long txPackets = entry.getTxPackets();
            int i2 = z ? 13 : networkStatsExt.ratType;
            java.lang.String str = networkStatsExt.subInfo.mcc;
            java.lang.String str2 = networkStatsExt.subInfo.mnc;
            int i3 = networkStatsExt.subInfo.carrierId;
            boolean z3 = z;
            if (networkStatsExt.subInfo.isOpportunistic) {
                i = 2;
            } else {
                i = 3;
            }
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(com.android.internal.util.FrameworkStatsLog.DATA_USAGE_BYTES_TRANSFER, set, rxBytes, rxPackets, txBytes, txPackets, i2, str, str2, i3, i, z2));
            z = z3;
        }
    }

    private void addOemDataUsageBytesTransferAtoms(@android.annotation.NonNull com.android.server.stats.pull.netstats.NetworkStatsExt networkStatsExt, @android.annotation.NonNull java.util.List<android.util.StatsEvent> list) {
        int i = networkStatsExt.oemManaged;
        int[] iArr = networkStatsExt.transports;
        int length = iArr.length;
        int i2 = 0;
        while (i2 < length) {
            int i3 = iArr[i2];
            java.util.Iterator it = networkStatsExt.stats.iterator();
            while (it.hasNext()) {
                android.net.NetworkStats.Entry entry = (android.net.NetworkStats.Entry) it.next();
                list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(com.android.internal.util.FrameworkStatsLog.OEM_MANAGED_BYTES_TRANSFER, entry.getUid(), entry.getSet() > 0, i, i3, entry.getRxBytes(), entry.getRxPackets(), entry.getTxBytes(), entry.getTxPackets()));
                length = length;
                i2 = i2;
            }
            i2++;
        }
    }

    @android.annotation.NonNull
    private java.util.List<com.android.server.stats.pull.netstats.NetworkStatsExt> getDataUsageBytesTransferSnapshotForOemManaged() {
        int i = 3;
        java.util.List<android.util.Pair> of = java.util.List.of(new android.util.Pair(5, 3), new android.util.Pair(1, 0), new android.util.Pair(4, 1));
        int[] iArr = {3, 1, 2};
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.util.Pair pair : of) {
            java.lang.Integer num = (java.lang.Integer) pair.first;
            int i2 = 0;
            while (i2 < i) {
                int i3 = iArr[i2];
                android.net.NetworkStats uidNetworkStatsSnapshotForTemplate = getUidNetworkStatsSnapshotForTemplate(new android.net.NetworkTemplate.Builder(num.intValue()).setOemManaged(i3).build(), false);
                java.lang.Integer num2 = (java.lang.Integer) pair.second;
                if (uidNetworkStatsSnapshotForTemplate != null) {
                    arrayList.add(new com.android.server.stats.pull.netstats.NetworkStatsExt(sliceNetworkStatsByUidAndFgbg(uidNetworkStatsSnapshotForTemplate), new int[]{num2.intValue()}, true, false, false, 0, null, i3, false));
                }
                i2++;
                i = 3;
            }
            i = 3;
        }
        return arrayList;
    }

    @android.annotation.Nullable
    private android.net.NetworkStats getUidNetworkStatsSnapshotForTransport(int i) {
        android.net.NetworkTemplate build;
        switch (i) {
            case 0:
                build = new android.net.NetworkTemplate.Builder(1).setMeteredness(1).build();
                break;
            case 1:
                build = new android.net.NetworkTemplate.Builder(4).build();
                break;
            default:
                android.util.Log.wtf(TAG, "Unexpected transport.");
                build = null;
                break;
        }
        return getUidNetworkStatsSnapshotForTemplate(build, false);
    }

    private static boolean canQueryNetworkStatsForTypeProxy() {
        try {
            new android.net.NetworkTemplate.Builder(9).build();
            return true;
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Slog.w(TAG, "Querying network stats for TYPE_PROXY is not allowed");
            return false;
        }
    }

    @android.annotation.Nullable
    private android.net.NetworkStats getUidNetworkStatsSnapshotForTemplate(@android.annotation.NonNull android.net.NetworkTemplate networkTemplate, boolean z) {
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        long millis = java.util.concurrent.TimeUnit.MICROSECONDS.toMillis(android.os.SystemClock.currentTimeMicro());
        long j = android.provider.Settings.Global.getLong(this.mContext.getContentResolver(), "netstats_uid_bucket_duration", NETSTATS_UID_DEFAULT_BUCKET_DURATION_MS);
        if (networkTemplate.getMatchRule() == 4 && networkTemplate.getSubscriberIds().isEmpty()) {
            this.mNetworkStatsManager.forceUpdate();
        }
        long j2 = (millis - elapsedRealtime) - j;
        android.app.usage.NetworkStats querySummary = this.mNetworkStatsManager.querySummary(networkTemplate, j2, millis);
        android.net.NetworkStats fromPublicNetworkStats = com.android.net.module.util.NetworkStatsUtils.fromPublicNetworkStats(querySummary);
        querySummary.close();
        if (!z) {
            return fromPublicNetworkStats;
        }
        android.app.usage.NetworkStats queryTaggedSummary = this.mNetworkStatsManager.queryTaggedSummary(networkTemplate, j2, millis);
        android.net.NetworkStats fromPublicNetworkStats2 = com.android.net.module.util.NetworkStatsUtils.fromPublicNetworkStats(queryTaggedSummary);
        queryTaggedSummary.close();
        return fromPublicNetworkStats.add(fromPublicNetworkStats2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v2 */
    @android.annotation.NonNull
    public java.util.List<com.android.server.stats.pull.netstats.NetworkStatsExt> getDataUsageBytesTransferSnapshotForSub(@android.annotation.NonNull com.android.server.stats.pull.netstats.SubInfo subInfo) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int[] allCollapsedRatTypes = getAllCollapsedRatTypes();
        int length = allCollapsedRatTypes.length;
        int i = 0;
        int i2 = 0;
        while (i2 < length) {
            int i3 = allCollapsedRatTypes[i2];
            android.net.NetworkStats uidNetworkStatsSnapshotForTemplate = getUidNetworkStatsSnapshotForTemplate(new android.net.NetworkTemplate.Builder(1).setSubscriberIds(java.util.Set.of(subInfo.subscriberId)).setRatType(i3).setMeteredness(1).build(), i);
            if (uidNetworkStatsSnapshotForTemplate != null) {
                arrayList.add(new com.android.server.stats.pull.netstats.NetworkStatsExt(sliceNetworkStatsByFgbg(uidNetworkStatsSnapshotForTemplate), new int[]{i}, true, false, false, i3, subInfo, -1, false));
            }
            i2++;
            i = 0;
        }
        return arrayList;
    }

    @android.annotation.NonNull
    private static int[] getAllCollapsedRatTypes() {
        int[] allNetworkTypes = android.telephony.TelephonyManager.getAllNetworkTypes();
        java.util.HashSet hashSet = new java.util.HashSet();
        for (int i : allNetworkTypes) {
            hashSet.add(java.lang.Integer.valueOf(android.app.usage.NetworkStatsManager.getCollapsedRatType(i)));
        }
        hashSet.add(java.lang.Integer.valueOf(android.app.usage.NetworkStatsManager.getCollapsedRatType(-2)));
        hashSet.add(0);
        return com.android.net.module.util.CollectionUtils.toIntArray(hashSet);
    }

    @android.annotation.NonNull
    private android.net.NetworkStats sliceNetworkStatsByUid(@android.annotation.NonNull android.net.NetworkStats networkStats) {
        return sliceNetworkStats(networkStats, new java.util.function.Function() { // from class: com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.net.NetworkStats.Entry lambda$sliceNetworkStatsByUid$8;
                lambda$sliceNetworkStatsByUid$8 = com.android.server.stats.pull.StatsPullAtomService.lambda$sliceNetworkStatsByUid$8((android.net.NetworkStats.Entry) obj);
                return lambda$sliceNetworkStatsByUid$8;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.net.NetworkStats.Entry lambda$sliceNetworkStatsByUid$8(android.net.NetworkStats.Entry entry) {
        return new android.net.NetworkStats.Entry((java.lang.String) null, entry.getUid(), -1, 0, -1, -1, -1, entry.getRxBytes(), entry.getRxPackets(), entry.getTxBytes(), entry.getTxPackets(), 0L);
    }

    @android.annotation.NonNull
    private android.net.NetworkStats sliceNetworkStatsByFgbg(@android.annotation.NonNull android.net.NetworkStats networkStats) {
        return sliceNetworkStats(networkStats, new java.util.function.Function() { // from class: com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda18
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.net.NetworkStats.Entry lambda$sliceNetworkStatsByFgbg$9;
                lambda$sliceNetworkStatsByFgbg$9 = com.android.server.stats.pull.StatsPullAtomService.lambda$sliceNetworkStatsByFgbg$9((android.net.NetworkStats.Entry) obj);
                return lambda$sliceNetworkStatsByFgbg$9;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.net.NetworkStats.Entry lambda$sliceNetworkStatsByFgbg$9(android.net.NetworkStats.Entry entry) {
        return new android.net.NetworkStats.Entry((java.lang.String) null, -1, entry.getSet(), 0, -1, -1, -1, entry.getRxBytes(), entry.getRxPackets(), entry.getTxBytes(), entry.getTxPackets(), 0L);
    }

    @android.annotation.NonNull
    private android.net.NetworkStats sliceNetworkStatsByUidAndFgbg(@android.annotation.NonNull android.net.NetworkStats networkStats) {
        return sliceNetworkStats(networkStats, new java.util.function.Function() { // from class: com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda19
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.net.NetworkStats.Entry lambda$sliceNetworkStatsByUidAndFgbg$10;
                lambda$sliceNetworkStatsByUidAndFgbg$10 = com.android.server.stats.pull.StatsPullAtomService.lambda$sliceNetworkStatsByUidAndFgbg$10((android.net.NetworkStats.Entry) obj);
                return lambda$sliceNetworkStatsByUidAndFgbg$10;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.net.NetworkStats.Entry lambda$sliceNetworkStatsByUidAndFgbg$10(android.net.NetworkStats.Entry entry) {
        return new android.net.NetworkStats.Entry((java.lang.String) null, entry.getUid(), entry.getSet(), 0, -1, -1, -1, entry.getRxBytes(), entry.getRxPackets(), entry.getTxBytes(), entry.getTxPackets(), 0L);
    }

    @android.annotation.NonNull
    private android.net.NetworkStats sliceNetworkStatsByUidTagAndMetered(@android.annotation.NonNull android.net.NetworkStats networkStats) {
        return sliceNetworkStats(networkStats, new java.util.function.Function() { // from class: com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.net.NetworkStats.Entry lambda$sliceNetworkStatsByUidTagAndMetered$11;
                lambda$sliceNetworkStatsByUidTagAndMetered$11 = com.android.server.stats.pull.StatsPullAtomService.lambda$sliceNetworkStatsByUidTagAndMetered$11((android.net.NetworkStats.Entry) obj);
                return lambda$sliceNetworkStatsByUidTagAndMetered$11;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.net.NetworkStats.Entry lambda$sliceNetworkStatsByUidTagAndMetered$11(android.net.NetworkStats.Entry entry) {
        return new android.net.NetworkStats.Entry((java.lang.String) null, entry.getUid(), -1, entry.getTag(), entry.getMetered(), -1, -1, entry.getRxBytes(), entry.getRxPackets(), entry.getTxBytes(), entry.getTxPackets(), 0L);
    }

    @android.annotation.NonNull
    private android.net.NetworkStats sliceNetworkStats(@android.annotation.NonNull android.net.NetworkStats networkStats, @android.annotation.NonNull java.util.function.Function<android.net.NetworkStats.Entry, android.net.NetworkStats.Entry> function) {
        android.net.NetworkStats networkStats2 = new android.net.NetworkStats(0L, 1);
        java.util.Iterator it = networkStats.iterator();
        while (it.hasNext()) {
            networkStats2 = networkStats2.addEntry(function.apply((android.net.NetworkStats.Entry) it.next()));
        }
        return networkStats2;
    }

    private void registerWifiBytesTransferBackground() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.WIFI_BYTES_TRANSFER_BY_FG_BG, new android.app.StatsManager.PullAtomMetadata.Builder().setAdditiveFields(new int[]{3, 4, 5, 6}).build(), com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private void registerMobileBytesTransfer() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.MOBILE_BYTES_TRANSFER, new android.app.StatsManager.PullAtomMetadata.Builder().setAdditiveFields(new int[]{2, 3, 4, 5}).build(), com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private void registerMobileBytesTransferBackground() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.MOBILE_BYTES_TRANSFER_BY_FG_BG, new android.app.StatsManager.PullAtomMetadata.Builder().setAdditiveFields(new int[]{3, 4, 5, 6}).build(), com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private void registerProxyBytesTransferBackground() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.PROXY_BYTES_TRANSFER_BY_FG_BG, new android.app.StatsManager.PullAtomMetadata.Builder().setAdditiveFields(new int[]{3, 4, 5, 6}).build(), com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private void registerBytesTransferByTagAndMetered() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.BYTES_TRANSFER_BY_TAG_AND_METERED, new android.app.StatsManager.PullAtomMetadata.Builder().setAdditiveFields(new int[]{4, 5, 6, 7}).build(), com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private void registerDataUsageBytesTransfer() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.DATA_USAGE_BYTES_TRANSFER, new android.app.StatsManager.PullAtomMetadata.Builder().setAdditiveFields(new int[]{2, 3, 4, 5}).build(), com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private void registerOemManagedBytesTransfer() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.OEM_MANAGED_BYTES_TRANSFER, new android.app.StatsManager.PullAtomMetadata.Builder().setAdditiveFields(new int[]{5, 6, 7, 8}).build(), com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private void registerBluetoothBytesTransfer() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.BLUETOOTH_BYTES_TRANSFER, new android.app.StatsManager.PullAtomMetadata.Builder().setAdditiveFields(new int[]{2, 3}).build(), com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private static <T extends android.os.Parcelable> T awaitControllerInfo(@android.annotation.Nullable android.os.SynchronousResultReceiver synchronousResultReceiver) {
        if (synchronousResultReceiver == null) {
            return null;
        }
        try {
            android.os.SynchronousResultReceiver.Result awaitResult = synchronousResultReceiver.awaitResult(EXTERNAL_STATS_SYNC_TIMEOUT_MILLIS);
            if (awaitResult.bundle != null) {
                awaitResult.bundle.setDefusable(true);
                T t = (T) awaitResult.bundle.getParcelable(RESULT_RECEIVER_CONTROLLER_KEY);
                if (t != null) {
                    return t;
                }
            }
        } catch (java.util.concurrent.TimeoutException e) {
            android.util.Slog.w(TAG, "timeout reading " + synchronousResultReceiver.getName() + " stats");
        }
        return null;
    }

    private android.bluetooth.BluetoothActivityEnergyInfo fetchBluetoothData() {
        android.bluetooth.BluetoothAdapter defaultAdapter = android.bluetooth.BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null) {
            final android.os.SynchronousResultReceiver synchronousResultReceiver = new android.os.SynchronousResultReceiver("bluetooth");
            defaultAdapter.requestControllerActivityEnergyInfo(new com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda0(), new android.bluetooth.BluetoothAdapter.OnBluetoothActivityEnergyInfoCallback() { // from class: com.android.server.stats.pull.StatsPullAtomService.1
                public void onBluetoothActivityEnergyInfoAvailable(android.bluetooth.BluetoothActivityEnergyInfo bluetoothActivityEnergyInfo) {
                    android.os.Bundle bundle = new android.os.Bundle();
                    bundle.putParcelable(com.android.server.stats.pull.StatsPullAtomService.RESULT_RECEIVER_CONTROLLER_KEY, bluetoothActivityEnergyInfo);
                    synchronousResultReceiver.send(0, bundle);
                }

                public void onBluetoothActivityEnergyInfoError(int i) {
                    android.util.Slog.w(com.android.server.stats.pull.StatsPullAtomService.TAG, "error reading Bluetooth stats: " + i);
                    android.os.Bundle bundle = new android.os.Bundle();
                    bundle.putParcelable(com.android.server.stats.pull.StatsPullAtomService.RESULT_RECEIVER_CONTROLLER_KEY, null);
                    synchronousResultReceiver.send(0, bundle);
                }
            });
            return awaitControllerInfo(synchronousResultReceiver);
        }
        android.util.Slog.e(TAG, "Failed to get bluetooth adapter!");
        return null;
    }

    int pullBluetoothBytesTransferLocked(int i, java.util.List<android.util.StatsEvent> list) {
        android.bluetooth.BluetoothActivityEnergyInfo fetchBluetoothData = fetchBluetoothData();
        if (fetchBluetoothData == null) {
            return 1;
        }
        for (android.bluetooth.UidTraffic uidTraffic : fetchBluetoothData.getUidTraffic()) {
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, uidTraffic.getUid(), uidTraffic.getRxBytes(), uidTraffic.getTxBytes()));
        }
        return 0;
    }

    private void registerKernelWakelock() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.KERNEL_WAKELOCK, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullKernelWakelockLocked(int i, java.util.List<android.util.StatsEvent> list) {
        for (java.util.Map.Entry<java.lang.String, com.android.server.power.stats.KernelWakelockStats.Entry> entry : this.mKernelWakelockReader.readKernelWakelockStats(this.mTmpWakelockStats).entrySet()) {
            java.lang.String key = entry.getKey();
            com.android.server.power.stats.KernelWakelockStats.Entry value = entry.getValue();
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, key, value.count, value.version, value.totalTimeUs));
        }
        return 0;
    }

    private void registerCpuTimePerClusterFreq() {
        if (com.android.internal.os.KernelCpuBpfTracking.isSupported()) {
            this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.CPU_TIME_PER_CLUSTER_FREQ, new android.app.StatsManager.PullAtomMetadata.Builder().setAdditiveFields(new int[]{3}).build(), com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
        }
    }

    int pullCpuTimePerClusterFreqLocked(int i, java.util.List<android.util.StatsEvent> list) {
        int[] freqsClusters = com.android.internal.os.KernelCpuBpfTracking.getFreqsClusters();
        long[] freqs = com.android.internal.os.KernelCpuBpfTracking.getFreqs();
        long[] read = com.android.internal.os.KernelCpuTotalBpfMapReader.read();
        if (read == null) {
            return 1;
        }
        for (int i2 = 0; i2 < read.length; i2++) {
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, freqsClusters[i2], (int) freqs[i2], read[i2]));
        }
        return 0;
    }

    private void registerCpuTimePerUid() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.CPU_TIME_PER_UID, new android.app.StatsManager.PullAtomMetadata.Builder().setAdditiveFields(new int[]{2, 3}).build(), com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullCpuTimePerUidLocked(final int i, final java.util.List<android.util.StatsEvent> list) {
        this.mCpuUidUserSysTimeReader.readAbsolute(new com.android.internal.os.KernelCpuUidTimeReader.Callback() { // from class: com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda14
            public final void onUidCpuTime(int i2, java.lang.Object obj) {
                com.android.server.stats.pull.StatsPullAtomService.lambda$pullCpuTimePerUidLocked$12(list, i, i2, (long[]) obj);
            }
        });
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$pullCpuTimePerUidLocked$12(java.util.List list, int i, int i2, long[] jArr) {
        list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, i2, jArr[0], jArr[1]));
    }

    private void registerCpuCyclesPerUidCluster() {
        if (com.android.internal.os.KernelCpuBpfTracking.isSupported() || com.android.internal.os.KernelCpuBpfTracking.getClusters() > 0) {
            this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.CPU_CYCLES_PER_UID_CLUSTER, new android.app.StatsManager.PullAtomMetadata.Builder().setAdditiveFields(new int[]{3, 4, 5}).build(), com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
        }
    }

    int pullCpuCyclesPerUidClusterLocked(int i, java.util.List<android.util.StatsEvent> list) {
        com.android.internal.os.PowerProfile powerProfile = new com.android.internal.os.PowerProfile(this.mContext);
        final int[] freqsClusters = com.android.internal.os.KernelCpuBpfTracking.getFreqsClusters();
        final int clusters = com.android.internal.os.KernelCpuBpfTracking.getClusters();
        final long[] freqs = com.android.internal.os.KernelCpuBpfTracking.getFreqs();
        final double[] dArr = new double[freqs.length];
        int i2 = -1;
        int i3 = 0;
        int i4 = 0;
        while (i3 < freqs.length) {
            int i5 = freqsClusters[i3];
            if (i5 != i2) {
                i4 = 0;
            }
            dArr[i3] = powerProfile.getAveragePowerForCpuCore(i5, i4);
            i3++;
            i4++;
            i2 = i5;
        }
        final android.util.SparseArray sparseArray = new android.util.SparseArray();
        this.mCpuUidFreqTimeReader.readAbsolute(new com.android.internal.os.KernelCpuUidTimeReader.Callback() { // from class: com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda11
            public final void onUidCpuTime(int i6, java.lang.Object obj) {
                com.android.server.stats.pull.StatsPullAtomService.lambda$pullCpuCyclesPerUidClusterLocked$13(sparseArray, clusters, freqsClusters, freqs, dArr, i6, (long[]) obj);
            }
        });
        int size = sparseArray.size();
        for (int i6 = 0; i6 < size; i6++) {
            int keyAt = sparseArray.keyAt(i6);
            double[] dArr2 = (double[]) sparseArray.valueAt(i6);
            for (int i7 = 0; i7 < clusters; i7++) {
                int i8 = i7 * 3;
                list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, keyAt, i7, (long) (dArr2[i8] / 1000000.0d), (long) dArr2[i8 + 1], (long) (dArr2[i8 + 2] / 1000.0d)));
            }
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$pullCpuCyclesPerUidClusterLocked$13(android.util.SparseArray sparseArray, int i, int[] iArr, long[] jArr, double[] dArr, int i2, long[] jArr2) {
        int appId;
        if (android.os.UserHandle.isIsolated(i2)) {
            return;
        }
        if (android.os.UserHandle.isSharedAppGid(i2)) {
            appId = 59999;
        } else {
            appId = android.os.UserHandle.getAppId(i2);
        }
        double[] dArr2 = (double[]) sparseArray.get(appId);
        if (dArr2 == null) {
            dArr2 = new double[i * 3];
            sparseArray.put(appId, dArr2);
        }
        for (int i3 = 0; i3 < jArr2.length; i3++) {
            int i4 = iArr[i3];
            long j = jArr2[i3];
            int i5 = i4 * 3;
            dArr2[i5] = dArr2[i5] + (jArr[i3] * j);
            int i6 = i5 + 1;
            double d = j;
            dArr2[i6] = dArr2[i6] + d;
            int i7 = i5 + 2;
            dArr2[i7] = dArr2[i7] + (dArr[i3] * d);
        }
    }

    private void registerCpuTimePerUidFreq() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.CPU_TIME_PER_UID_FREQ, new android.app.StatsManager.PullAtomMetadata.Builder().setAdditiveFields(new int[]{3}).build(), com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullCpuTimePerUidFreqLocked(int i, java.util.List<android.util.StatsEvent> list) {
        final android.util.SparseArray sparseArray = new android.util.SparseArray();
        this.mCpuUidFreqTimeReader.readAbsolute(new com.android.internal.os.KernelCpuUidTimeReader.Callback() { // from class: com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda16
            public final void onUidCpuTime(int i2, java.lang.Object obj) {
                com.android.server.stats.pull.StatsPullAtomService.lambda$pullCpuTimePerUidFreqLocked$14(sparseArray, i2, (long[]) obj);
            }
        });
        int size = sparseArray.size();
        for (int i2 = 0; i2 < size; i2++) {
            int keyAt = sparseArray.keyAt(i2);
            long[] jArr = (long[]) sparseArray.valueAt(i2);
            for (int i3 = 0; i3 < jArr.length; i3++) {
                if (jArr[i3] >= 10) {
                    list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, keyAt, i3, jArr[i3]));
                }
            }
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$pullCpuTimePerUidFreqLocked$14(android.util.SparseArray sparseArray, int i, long[] jArr) {
        int appId;
        if (android.os.UserHandle.isIsolated(i)) {
            return;
        }
        if (android.os.UserHandle.isSharedAppGid(i)) {
            appId = 59999;
        } else {
            appId = android.os.UserHandle.getAppId(i);
        }
        long[] jArr2 = (long[]) sparseArray.get(appId);
        if (jArr2 == null) {
            jArr2 = new long[jArr.length];
            sparseArray.put(appId, jArr2);
        }
        for (int i2 = 0; i2 < jArr.length; i2++) {
            jArr2[i2] = jArr2[i2] + jArr[i2];
        }
    }

    private void registerCpuCyclesPerThreadGroupCluster() {
        if (com.android.internal.os.KernelCpuBpfTracking.isSupported()) {
            com.android.server.power.optimization.Flags.disableSystemServicePowerAttr();
            this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.CPU_CYCLES_PER_THREAD_GROUP_CLUSTER, new android.app.StatsManager.PullAtomMetadata.Builder().setAdditiveFields(new int[]{3, 4}).build(), com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
        }
    }

    int pullCpuCyclesPerThreadGroupCluster(int i, java.util.List<android.util.StatsEvent> list) {
        com.android.server.power.optimization.Flags.disableSystemServicePowerAttr();
        com.android.server.power.stats.SystemServerCpuThreadReader.SystemServiceCpuThreadTimes systemServiceCpuThreadTimes = ((android.os.BatteryStatsInternal) com.android.server.LocalServices.getService(android.os.BatteryStatsInternal.class)).getSystemServiceCpuThreadTimes();
        if (systemServiceCpuThreadTimes == null) {
            return 1;
        }
        addCpuCyclesPerThreadGroupClusterAtoms(i, list, 2, systemServiceCpuThreadTimes.threadCpuTimesUs);
        addCpuCyclesPerThreadGroupClusterAtoms(i, list, 1, systemServiceCpuThreadTimes.binderThreadCpuTimesUs);
        com.android.internal.os.KernelSingleProcessCpuThreadReader.ProcessCpuUsage readAbsolute = this.mSurfaceFlingerProcessCpuThreadReader.readAbsolute();
        if (readAbsolute != null && readAbsolute.threadCpuTimesMillis != null) {
            int length = readAbsolute.threadCpuTimesMillis.length;
            long[] jArr = new long[length];
            for (int i2 = 0; i2 < length; i2++) {
                jArr[i2] = readAbsolute.threadCpuTimesMillis[i2] * 1000;
            }
            addCpuCyclesPerThreadGroupClusterAtoms(i, list, 3, jArr);
        }
        return 0;
    }

    private static void addCpuCyclesPerThreadGroupClusterAtoms(int i, java.util.List<android.util.StatsEvent> list, int i2, long[] jArr) {
        int[] freqsClusters = com.android.internal.os.KernelCpuBpfTracking.getFreqsClusters();
        int clusters = com.android.internal.os.KernelCpuBpfTracking.getClusters();
        long[] freqs = com.android.internal.os.KernelCpuBpfTracking.getFreqs();
        long[] jArr2 = new long[clusters];
        long[] jArr3 = new long[clusters];
        for (int i3 = 0; i3 < jArr.length; i3++) {
            int i4 = freqsClusters[i3];
            jArr2[i4] = jArr2[i4] + ((freqs[i3] * jArr[i3]) / 1000);
            int i5 = freqsClusters[i3];
            jArr3[i5] = jArr3[i5] + jArr[i3];
        }
        for (int i6 = 0; i6 < clusters; i6++) {
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, i2, i6, jArr2[i6] / 1000000, jArr3[i6] / 1000));
        }
    }

    private void registerCpuActiveTime() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.CPU_ACTIVE_TIME, new android.app.StatsManager.PullAtomMetadata.Builder().setAdditiveFields(new int[]{2}).build(), com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullCpuActiveTimeLocked(final int i, final java.util.List<android.util.StatsEvent> list) {
        this.mCpuUidActiveTimeReader.readAbsolute(new com.android.internal.os.KernelCpuUidTimeReader.Callback() { // from class: com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda7
            public final void onUidCpuTime(int i2, java.lang.Object obj) {
                com.android.server.stats.pull.StatsPullAtomService.lambda$pullCpuActiveTimeLocked$15(list, i, i2, (java.lang.Long) obj);
            }
        });
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$pullCpuActiveTimeLocked$15(java.util.List list, int i, int i2, java.lang.Long l) {
        list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, i2, l.longValue()));
    }

    private void registerCpuClusterTime() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.CPU_CLUSTER_TIME, new android.app.StatsManager.PullAtomMetadata.Builder().setAdditiveFields(new int[]{3}).build(), com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullCpuClusterTimeLocked(final int i, final java.util.List<android.util.StatsEvent> list) {
        this.mCpuUidClusterTimeReader.readAbsolute(new com.android.internal.os.KernelCpuUidTimeReader.Callback() { // from class: com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda28
            public final void onUidCpuTime(int i2, java.lang.Object obj) {
                com.android.server.stats.pull.StatsPullAtomService.lambda$pullCpuClusterTimeLocked$16(list, i, i2, (long[]) obj);
            }
        });
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$pullCpuClusterTimeLocked$16(java.util.List list, int i, int i2, long[] jArr) {
        for (int i3 = 0; i3 < jArr.length; i3++) {
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, i2, i3, jArr[i3]));
        }
    }

    private void registerWifiActivityInfo() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.WIFI_ACTIVITY_INFO, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullWifiActivityInfoLocked(int i, java.util.List<android.util.StatsEvent> list) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            final android.os.SynchronousResultReceiver synchronousResultReceiver = new android.os.SynchronousResultReceiver("wifi");
            this.mWifiManager.getWifiActivityEnergyInfoAsync(new java.util.concurrent.Executor() { // from class: com.android.server.stats.pull.StatsPullAtomService.2
                @Override // java.util.concurrent.Executor
                public void execute(java.lang.Runnable runnable) {
                    runnable.run();
                }
            }, new android.net.wifi.WifiManager.OnWifiActivityEnergyInfoListener() { // from class: com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda6
                public final void onWifiActivityEnergyInfo(android.os.connectivity.WifiActivityEnergyInfo wifiActivityEnergyInfo) {
                    com.android.server.stats.pull.StatsPullAtomService.lambda$pullWifiActivityInfoLocked$17(synchronousResultReceiver, wifiActivityEnergyInfo);
                }
            });
            android.os.connectivity.WifiActivityEnergyInfo awaitControllerInfo = awaitControllerInfo(synchronousResultReceiver);
            if (awaitControllerInfo == null) {
                return 1;
            }
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, awaitControllerInfo.getTimeSinceBootMillis(), awaitControllerInfo.getStackState(), awaitControllerInfo.getControllerTxDurationMillis(), awaitControllerInfo.getControllerRxDurationMillis(), awaitControllerInfo.getControllerIdleDurationMillis(), awaitControllerInfo.getControllerEnergyUsedMicroJoules()));
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0;
        } catch (java.lang.RuntimeException e) {
            android.util.Slog.e(TAG, "failed to getWifiActivityEnergyInfoAsync", e);
            return 1;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$pullWifiActivityInfoLocked$17(android.os.SynchronousResultReceiver synchronousResultReceiver, android.os.connectivity.WifiActivityEnergyInfo wifiActivityEnergyInfo) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putParcelable(RESULT_RECEIVER_CONTROLLER_KEY, wifiActivityEnergyInfo);
        synchronousResultReceiver.send(0, bundle);
    }

    private void registerModemActivityInfo() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.MODEM_ACTIVITY_INFO, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullModemActivityInfoLocked(int i, java.util.List<android.util.StatsEvent> list) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            final java.util.concurrent.CompletableFuture completableFuture = new java.util.concurrent.CompletableFuture();
            this.mTelephony.requestModemActivityInfo(new com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda0(), new android.os.OutcomeReceiver<android.telephony.ModemActivityInfo, android.telephony.TelephonyManager.ModemActivityInfoException>() { // from class: com.android.server.stats.pull.StatsPullAtomService.3
                @Override // android.os.OutcomeReceiver
                public void onResult(android.telephony.ModemActivityInfo modemActivityInfo) {
                    completableFuture.complete(modemActivityInfo);
                }

                @Override // android.os.OutcomeReceiver
                public void onError(android.telephony.TelephonyManager.ModemActivityInfoException modemActivityInfoException) {
                    android.util.Slog.w(com.android.server.stats.pull.StatsPullAtomService.TAG, "error reading modem stats:" + modemActivityInfoException);
                    completableFuture.complete(null);
                }
            });
            android.telephony.ModemActivityInfo modemActivityInfo = (android.telephony.ModemActivityInfo) completableFuture.get(EXTERNAL_STATS_SYNC_TIMEOUT_MILLIS, java.util.concurrent.TimeUnit.MILLISECONDS);
            if (modemActivityInfo == null) {
                return 1;
            }
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, modemActivityInfo.getTimestampMillis(), modemActivityInfo.getSleepTimeMillis(), modemActivityInfo.getIdleTimeMillis(), modemActivityInfo.getTransmitDurationMillisAtPowerLevel(0), modemActivityInfo.getTransmitDurationMillisAtPowerLevel(1), modemActivityInfo.getTransmitDurationMillisAtPowerLevel(2), modemActivityInfo.getTransmitDurationMillisAtPowerLevel(3), modemActivityInfo.getTransmitDurationMillisAtPowerLevel(4), modemActivityInfo.getReceiveTimeMillis(), -1L));
            return 0;
        } catch (java.lang.InterruptedException | java.util.concurrent.TimeoutException e) {
            android.util.Slog.w(TAG, "timeout or interrupt reading modem stats: " + e);
            return 1;
        } catch (java.util.concurrent.ExecutionException e2) {
            android.util.Slog.w(TAG, "exception reading modem stats: " + e2.getCause());
            return 1;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void registerBluetoothActivityInfo() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.BLUETOOTH_ACTIVITY_INFO, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullBluetoothActivityInfoLocked(int i, java.util.List<android.util.StatsEvent> list) {
        android.bluetooth.BluetoothActivityEnergyInfo fetchBluetoothData = fetchBluetoothData();
        if (fetchBluetoothData == null) {
            return 1;
        }
        list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, fetchBluetoothData.getTimestampMillis(), fetchBluetoothData.getBluetoothStackState(), fetchBluetoothData.getControllerTxTimeMillis(), fetchBluetoothData.getControllerRxTimeMillis(), fetchBluetoothData.getControllerIdleTimeMillis(), fetchBluetoothData.getControllerEnergyUsed()));
        return 0;
    }

    private void registerUwbActivityInfo() {
        if (this.mUwbManager == null) {
            return;
        }
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.UWB_ACTIVITY_INFO, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullUwbActivityInfoLocked(int i, java.util.List<android.util.StatsEvent> list) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            final android.os.SynchronousResultReceiver synchronousResultReceiver = new android.os.SynchronousResultReceiver("uwb");
            this.mUwbManager.getUwbActivityEnergyInfoAsync(new com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda0(), new java.util.function.Consumer() { // from class: com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda27
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.stats.pull.StatsPullAtomService.lambda$pullUwbActivityInfoLocked$18(synchronousResultReceiver, (android.uwb.UwbActivityEnergyInfo) obj);
                }
            });
            android.uwb.UwbActivityEnergyInfo awaitControllerInfo = awaitControllerInfo(synchronousResultReceiver);
            if (awaitControllerInfo == null) {
                return 1;
            }
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, awaitControllerInfo.getControllerTxDurationMillis(), awaitControllerInfo.getControllerRxDurationMillis(), awaitControllerInfo.getControllerIdleDurationMillis(), awaitControllerInfo.getControllerWakeCount()));
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0;
        } catch (java.lang.RuntimeException e) {
            android.util.Slog.e(TAG, "failed to getUwbActivityEnergyInfoAsync", e);
            return 1;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$pullUwbActivityInfoLocked$18(android.os.SynchronousResultReceiver synchronousResultReceiver, android.uwb.UwbActivityEnergyInfo uwbActivityEnergyInfo) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putParcelable(RESULT_RECEIVER_CONTROLLER_KEY, uwbActivityEnergyInfo);
        synchronousResultReceiver.send(0, bundle);
    }

    private void registerSystemElapsedRealtime() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.SYSTEM_ELAPSED_REALTIME, new android.app.StatsManager.PullAtomMetadata.Builder().setCoolDownMillis(1000L).setTimeoutMillis(500L).build(), com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullSystemElapsedRealtimeLocked(int i, java.util.List<android.util.StatsEvent> list) {
        list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, android.os.SystemClock.elapsedRealtime()));
        return 0;
    }

    private void registerSystemUptime() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.SYSTEM_UPTIME, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullSystemUptimeLocked(int i, java.util.List<android.util.StatsEvent> list) {
        list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, android.os.SystemClock.uptimeMillis()));
        return 0;
    }

    private void registerProcessMemoryState() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.PROCESS_MEMORY_STATE, new android.app.StatsManager.PullAtomMetadata.Builder().setAdditiveFields(new int[]{4, 5, 6, 7, 8}).build(), com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullProcessMemoryStateLocked(int i, java.util.List<android.util.StatsEvent> list) {
        for (android.app.ProcessMemoryState processMemoryState : ((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).getMemoryStateForProcesses()) {
            com.android.server.am.MemoryStatUtil.MemoryStat readMemoryStatFromFilesystem = com.android.server.am.MemoryStatUtil.readMemoryStatFromFilesystem(processMemoryState.uid, processMemoryState.pid);
            if (readMemoryStatFromFilesystem != null) {
                list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, processMemoryState.uid, processMemoryState.processName, processMemoryState.oomScore, readMemoryStatFromFilesystem.pgfault, readMemoryStatFromFilesystem.pgmajfault, readMemoryStatFromFilesystem.rssInBytes, readMemoryStatFromFilesystem.cacheInBytes, readMemoryStatFromFilesystem.swapInBytes, -1L, -1L, -1));
            }
        }
        return 0;
    }

    private void registerProcessMemoryHighWaterMark() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.PROCESS_MEMORY_HIGH_WATER_MARK, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullProcessMemoryHighWaterMarkLocked(int i, java.util.List<android.util.StatsEvent> list) {
        java.util.List<android.app.ProcessMemoryState> memoryStateForProcesses = ((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).getMemoryStateForProcesses();
        for (android.app.ProcessMemoryState processMemoryState : memoryStateForProcesses) {
            com.android.server.stats.pull.ProcfsMemoryUtil.MemorySnapshot readMemorySnapshotFromProcfs = com.android.server.stats.pull.ProcfsMemoryUtil.readMemorySnapshotFromProcfs(processMemoryState.pid);
            if (readMemorySnapshotFromProcfs != null) {
                list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, processMemoryState.uid, processMemoryState.processName, readMemorySnapshotFromProcfs.rssHighWaterMarkInKilobytes * 1024, readMemorySnapshotFromProcfs.rssHighWaterMarkInKilobytes));
            }
        }
        final android.util.SparseArray<java.lang.String> processCmdlines = com.android.server.stats.pull.ProcfsMemoryUtil.getProcessCmdlines();
        memoryStateForProcesses.forEach(new java.util.function.Consumer() { // from class: com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda13
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.stats.pull.StatsPullAtomService.lambda$pullProcessMemoryHighWaterMarkLocked$19(processCmdlines, (android.app.ProcessMemoryState) obj);
            }
        });
        int size = processCmdlines.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.stats.pull.ProcfsMemoryUtil.MemorySnapshot readMemorySnapshotFromProcfs2 = com.android.server.stats.pull.ProcfsMemoryUtil.readMemorySnapshotFromProcfs(processCmdlines.keyAt(i2));
            if (readMemorySnapshotFromProcfs2 != null) {
                list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, readMemorySnapshotFromProcfs2.uid, processCmdlines.valueAt(i2), readMemorySnapshotFromProcfs2.rssHighWaterMarkInKilobytes * 1024, readMemorySnapshotFromProcfs2.rssHighWaterMarkInKilobytes));
            }
        }
        android.os.SystemProperties.set("sys.rss_hwm_reset.on", "1");
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$pullProcessMemoryHighWaterMarkLocked$19(android.util.SparseArray sparseArray, android.app.ProcessMemoryState processMemoryState) {
        sparseArray.delete(processMemoryState.pid);
    }

    private void registerProcessMemorySnapshot() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.PROCESS_MEMORY_SNAPSHOT, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullProcessMemorySnapshot(int i, java.util.List<android.util.StatsEvent> list) {
        java.util.List<android.app.ProcessMemoryState> memoryStateForProcesses = ((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).getMemoryStateForProcesses();
        com.android.internal.os.KernelAllocationStats.ProcessGpuMem[] gpuAllocations = com.android.internal.os.KernelAllocationStats.getGpuAllocations();
        android.util.SparseIntArray sparseIntArray = new android.util.SparseIntArray(gpuAllocations.length);
        for (com.android.internal.os.KernelAllocationStats.ProcessGpuMem processGpuMem : gpuAllocations) {
            sparseIntArray.put(processGpuMem.pid, processGpuMem.gpuMemoryKb);
        }
        for (android.app.ProcessMemoryState processMemoryState : memoryStateForProcesses) {
            com.android.server.stats.pull.ProcfsMemoryUtil.MemorySnapshot readMemorySnapshotFromProcfs = com.android.server.stats.pull.ProcfsMemoryUtil.readMemorySnapshotFromProcfs(processMemoryState.pid);
            if (readMemorySnapshotFromProcfs != null) {
                list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, processMemoryState.uid, processMemoryState.processName, processMemoryState.pid, processMemoryState.oomScore, readMemorySnapshotFromProcfs.rssInKilobytes, readMemorySnapshotFromProcfs.anonRssInKilobytes, readMemorySnapshotFromProcfs.swapInKilobytes, readMemorySnapshotFromProcfs.swapInKilobytes + readMemorySnapshotFromProcfs.anonRssInKilobytes, sparseIntArray.get(processMemoryState.pid), processMemoryState.hasForegroundServices, readMemorySnapshotFromProcfs.rssShmemKilobytes, processMemoryState.mHostingComponentTypes, processMemoryState.mHistoricalHostingComponentTypes));
            }
        }
        final android.util.SparseArray<java.lang.String> processCmdlines = com.android.server.stats.pull.ProcfsMemoryUtil.getProcessCmdlines();
        memoryStateForProcesses.forEach(new java.util.function.Consumer() { // from class: com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda26
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.stats.pull.StatsPullAtomService.lambda$pullProcessMemorySnapshot$20(processCmdlines, (android.app.ProcessMemoryState) obj);
            }
        });
        int size = processCmdlines.size();
        for (int i2 = 0; i2 < size; i2++) {
            int keyAt = processCmdlines.keyAt(i2);
            com.android.server.stats.pull.ProcfsMemoryUtil.MemorySnapshot readMemorySnapshotFromProcfs2 = com.android.server.stats.pull.ProcfsMemoryUtil.readMemorySnapshotFromProcfs(keyAt);
            if (readMemorySnapshotFromProcfs2 != null) {
                list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, readMemorySnapshotFromProcfs2.uid, processCmdlines.valueAt(i2), keyAt, com.android.server.job.JobSchedulerShellCommand.CMD_ERR_NO_JOB, readMemorySnapshotFromProcfs2.rssInKilobytes, readMemorySnapshotFromProcfs2.anonRssInKilobytes, readMemorySnapshotFromProcfs2.swapInKilobytes, readMemorySnapshotFromProcfs2.swapInKilobytes + readMemorySnapshotFromProcfs2.anonRssInKilobytes, sparseIntArray.get(keyAt), false, readMemorySnapshotFromProcfs2.rssShmemKilobytes, 0, 0));
            }
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$pullProcessMemorySnapshot$20(android.util.SparseArray sparseArray, android.app.ProcessMemoryState processMemoryState) {
        sparseArray.delete(processMemoryState.pid);
    }

    private void registerSystemIonHeapSize() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.SYSTEM_ION_HEAP_SIZE, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullSystemIonHeapSizeLocked(int i, java.util.List<android.util.StatsEvent> list) {
        list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, com.android.server.stats.pull.IonMemoryUtil.readSystemIonHeapSizeFromDebugfs()));
        return 0;
    }

    private void registerIonHeapSize() {
        if (!new java.io.File("/sys/kernel/ion/total_heaps_kb").exists()) {
            return;
        }
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.ION_HEAP_SIZE, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullIonHeapSizeLocked(int i, java.util.List<android.util.StatsEvent> list) {
        list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, (int) android.os.Debug.getIonHeapsSizeKb()));
        return 0;
    }

    private void registerProcessSystemIonHeapSize() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.PROCESS_SYSTEM_ION_HEAP_SIZE, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullProcessSystemIonHeapSizeLocked(int i, java.util.List<android.util.StatsEvent> list) {
        for (com.android.server.stats.pull.IonMemoryUtil.IonAllocations ionAllocations : com.android.server.stats.pull.IonMemoryUtil.readProcessSystemIonHeapSizesFromDebugfs()) {
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, android.os.Process.getUidForPid(ionAllocations.pid), com.android.server.stats.pull.ProcfsMemoryUtil.readCmdlineFromProcfs(ionAllocations.pid), (int) (ionAllocations.totalSizeInBytes / 1024), ionAllocations.count, (int) (ionAllocations.maxSizeInBytes / 1024)));
        }
        return 0;
    }

    private void registerProcessDmabufMemory() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.PROCESS_DMABUF_MEMORY, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullProcessDmabufMemory(int i, java.util.List<android.util.StatsEvent> list) {
        com.android.internal.os.KernelAllocationStats.ProcessDmabuf[] dmabufAllocations = com.android.internal.os.KernelAllocationStats.getDmabufAllocations();
        if (dmabufAllocations == null) {
            return 1;
        }
        for (com.android.internal.os.KernelAllocationStats.ProcessDmabuf processDmabuf : dmabufAllocations) {
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, processDmabuf.uid, processDmabuf.processName, processDmabuf.oomScore, processDmabuf.retainedSizeKb, processDmabuf.retainedBuffersCount, 0, 0, processDmabuf.surfaceFlingerSizeKb, processDmabuf.surfaceFlingerCount));
        }
        return 0;
    }

    private void registerSystemMemory() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.SYSTEM_MEMORY, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullSystemMemory(int i, java.util.List<android.util.StatsEvent> list) {
        com.android.server.stats.pull.SystemMemoryUtil.Metrics metrics = com.android.server.stats.pull.SystemMemoryUtil.getMetrics();
        list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, metrics.unreclaimableSlabKb, metrics.vmallocUsedKb, metrics.pageTablesKb, metrics.kernelStackKb, metrics.totalIonKb, metrics.unaccountedKb, metrics.gpuTotalUsageKb, metrics.gpuPrivateAllocationsKb, metrics.dmaBufTotalExportedKb, metrics.shmemKb, metrics.totalKb, metrics.freeKb, metrics.availableKb, metrics.activeKb, metrics.inactiveKb, metrics.activeAnonKb, metrics.inactiveAnonKb, metrics.activeFileKb, metrics.inactiveFileKb, metrics.swapTotalKb, metrics.swapFreeKb, metrics.cmaTotalKb, metrics.cmaFreeKb));
        return 0;
    }

    private void registerVmStat() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.VMSTAT, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullVmStat(int i, java.util.List<android.util.StatsEvent> list) {
        com.android.server.stats.pull.ProcfsMemoryUtil.VmStat readVmStat = com.android.server.stats.pull.ProcfsMemoryUtil.readVmStat();
        if (readVmStat != null) {
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, readVmStat.oomKillCount));
            return 0;
        }
        return 0;
    }

    private void registerTemperature() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.TEMPERATURE, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullTemperatureLocked(int i, java.util.List<android.util.StatsEvent> list) {
        android.os.IThermalService iThermalService = getIThermalService();
        if (iThermalService == null) {
            return 1;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            for (android.os.Temperature temperature : iThermalService.getCurrentTemperatures()) {
                list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, temperature.getType(), temperature.getName(), (int) (temperature.getValue() * 10.0f), temperature.getStatus()));
            }
            return 0;
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Disconnected from thermal service. Cannot pull temperatures.");
            return 1;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void registerCoolingDevice() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.COOLING_DEVICE, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullCooldownDeviceLocked(int i, java.util.List<android.util.StatsEvent> list) {
        android.os.IThermalService iThermalService = getIThermalService();
        if (iThermalService == null) {
            return 1;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            for (android.os.CoolingDevice coolingDevice : iThermalService.getCurrentCoolingDevices()) {
                list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, coolingDevice.getType(), coolingDevice.getName(), (int) coolingDevice.getValue()));
            }
            return 0;
        } catch (android.os.RemoteException e) {
            android.util.Slog.e(TAG, "Disconnected from thermal service. Cannot pull temperatures.");
            return 1;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void registerBinderCallsStats() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.BINDER_CALLS, new android.app.StatsManager.PullAtomMetadata.Builder().setAdditiveFields(new int[]{4, 5, 6, 8, 12}).build(), com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullBinderCallsStatsLocked(int i, java.util.List<android.util.StatsEvent> list) {
        com.android.server.BinderCallsStatsService.Internal internal = (com.android.server.BinderCallsStatsService.Internal) com.android.server.LocalServices.getService(com.android.server.BinderCallsStatsService.Internal.class);
        if (internal == null) {
            android.util.Slog.e(TAG, "failed to get binderStats");
            return 1;
        }
        java.util.ArrayList<com.android.internal.os.BinderCallsStats.ExportedCallStat> exportedCallStats = internal.getExportedCallStats();
        internal.reset();
        for (com.android.internal.os.BinderCallsStats.ExportedCallStat exportedCallStat : exportedCallStats) {
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, exportedCallStat.workSourceUid, exportedCallStat.className, exportedCallStat.methodName, exportedCallStat.callCount, exportedCallStat.exceptionCount, exportedCallStat.latencyMicros, exportedCallStat.maxLatencyMicros, exportedCallStat.cpuTimeMicros, exportedCallStat.maxCpuTimeMicros, exportedCallStat.maxReplySizeBytes, exportedCallStat.maxRequestSizeBytes, exportedCallStat.recordedCallCount, exportedCallStat.screenInteractive, exportedCallStat.callingUid));
        }
        return 0;
    }

    private void registerBinderCallsStatsExceptions() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.BINDER_CALLS_EXCEPTIONS, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullBinderCallsStatsExceptionsLocked(int i, java.util.List<android.util.StatsEvent> list) {
        com.android.server.BinderCallsStatsService.Internal internal = (com.android.server.BinderCallsStatsService.Internal) com.android.server.LocalServices.getService(com.android.server.BinderCallsStatsService.Internal.class);
        if (internal == null) {
            android.util.Slog.e(TAG, "failed to get binderStats");
            return 1;
        }
        java.util.Iterator<java.util.Map.Entry<java.lang.String, java.lang.Integer>> it = internal.getExportedExceptionStats().entrySet().iterator();
        while (it.hasNext()) {
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, it.next().getKey(), r1.getValue().intValue()));
        }
        return 0;
    }

    private void registerLooperStats() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.LOOPER_STATS, new android.app.StatsManager.PullAtomMetadata.Builder().setAdditiveFields(new int[]{5, 6, 7, 8, 9}).build(), com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullLooperStatsLocked(int i, java.util.List<android.util.StatsEvent> list) {
        com.android.internal.os.LooperStats looperStats = (com.android.internal.os.LooperStats) com.android.server.LocalServices.getService(com.android.internal.os.LooperStats.class);
        if (looperStats == null) {
            return 1;
        }
        java.util.List<com.android.internal.os.LooperStats.ExportedEntry> entries = looperStats.getEntries();
        looperStats.reset();
        for (com.android.internal.os.LooperStats.ExportedEntry exportedEntry : entries) {
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, exportedEntry.workSourceUid, exportedEntry.handlerClassName, exportedEntry.threadName, exportedEntry.messageName, exportedEntry.messageCount, exportedEntry.exceptionCount, exportedEntry.recordedMessageCount, exportedEntry.totalLatencyMicros, exportedEntry.cpuUsageMicros, exportedEntry.isInteractive, exportedEntry.maxCpuUsageMicros, exportedEntry.maxLatencyMicros, exportedEntry.recordedDelayMessageCount, exportedEntry.delayMillis, exportedEntry.maxDelayMillis));
        }
        return 0;
    }

    private void registerDiskStats() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.DISK_STATS, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0070 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0072 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.io.IOException] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v7, types: [java.io.IOException] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    int pullDiskStatsLocked(int i, java.util.List<android.util.StatsEvent> list) {
        java.io.FileOutputStream fileOutputStream;
        android.os.IStoraged iStoragedService;
        int i2;
        byte[] bArr = new byte[512];
        for (int i3 = 0; i3 < 512; i3++) {
            bArr[i3] = (byte) i3;
        }
        java.io.File file = new java.io.File(android.os.Environment.getDataDirectory(), "system/statsdperftest.tmp");
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        java.io.FileOutputStream fileOutputStream2 = null;
        try {
            try {
                fileOutputStream = new java.io.FileOutputStream(file);
                try {
                    fileOutputStream.write(bArr);
                    fileOutputStream.close();
                } catch (java.io.IOException e) {
                    e = e;
                    fileOutputStream2 = e;
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    long elapsedRealtime2 = android.os.SystemClock.elapsedRealtime() - elapsedRealtime;
                    if (file.exists()) {
                    }
                    if (fileOutputStream2 != null) {
                    }
                    boolean isFileEncrypted = android.os.storage.StorageManager.isFileEncrypted();
                    iStoragedService = getIStoragedService();
                    if (iStoragedService != null) {
                    }
                } catch (java.lang.Throwable th) {
                    th = th;
                    fileOutputStream2 = fileOutputStream;
                    if (fileOutputStream2 != null) {
                        try {
                            fileOutputStream2.close();
                        } catch (java.io.IOException e2) {
                        }
                    }
                    throw th;
                }
            } catch (java.io.IOException e3) {
                e = e3;
                fileOutputStream = null;
            } catch (java.lang.Throwable th2) {
                th = th2;
            }
        } catch (java.io.IOException e4) {
        }
        long elapsedRealtime22 = android.os.SystemClock.elapsedRealtime() - elapsedRealtime;
        if (file.exists()) {
            file.delete();
        }
        if (fileOutputStream2 != null) {
            android.util.Slog.e(TAG, "Error performing diskstats latency test");
            elapsedRealtime22 = -1;
        }
        boolean isFileEncrypted2 = android.os.storage.StorageManager.isFileEncrypted();
        iStoragedService = getIStoragedService();
        if (iStoragedService != null) {
            return 1;
        }
        try {
            i2 = iStoragedService.getRecentPerf();
        } catch (android.os.RemoteException e5) {
            android.util.Slog.e(TAG, "storaged not found");
            i2 = -1;
        }
        list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, elapsedRealtime22, isFileEncrypted2, i2));
        return 0;
    }

    private void registerDirectoryUsage() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.DIRECTORY_USAGE, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullDirectoryUsageLocked(int i, java.util.List<android.util.StatsEvent> list) {
        android.os.StatFs statFs = new android.os.StatFs(android.os.Environment.getDataDirectory().getAbsolutePath());
        android.os.StatFs statFs2 = new android.os.StatFs(android.os.Environment.getRootDirectory().getAbsolutePath());
        android.os.StatFs statFs3 = new android.os.StatFs(android.os.Environment.getDownloadCacheDirectory().getAbsolutePath());
        android.os.StatFs statFs4 = new android.os.StatFs(android.os.Environment.getMetadataDirectory().getAbsolutePath());
        list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, 1, statFs.getAvailableBytes(), statFs.getTotalBytes()));
        list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, 2, statFs3.getAvailableBytes(), statFs3.getTotalBytes()));
        list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, 3, statFs2.getAvailableBytes(), statFs2.getTotalBytes()));
        list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, 4, statFs4.getAvailableBytes(), statFs4.getTotalBytes()));
        return 0;
    }

    private void registerAppSize() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.APP_SIZE, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullAppSizeLocked(int i, java.util.List<android.util.StatsEvent> list) {
        try {
            org.json.JSONObject jSONObject = new org.json.JSONObject(libcore.io.IoUtils.readFileAsString(com.android.server.storage.DiskStatsLoggingService.DUMPSYS_CACHE_PATH));
            long optLong = jSONObject.optLong(com.android.server.storage.DiskStatsFileLogger.LAST_QUERY_TIMESTAMP_KEY, -1L);
            org.json.JSONArray jSONArray = jSONObject.getJSONArray(com.android.server.storage.DiskStatsFileLogger.PACKAGE_NAMES_KEY);
            org.json.JSONArray jSONArray2 = jSONObject.getJSONArray(com.android.server.storage.DiskStatsFileLogger.APP_SIZES_KEY);
            org.json.JSONArray jSONArray3 = jSONObject.getJSONArray(com.android.server.storage.DiskStatsFileLogger.APP_DATA_KEY);
            org.json.JSONArray jSONArray4 = jSONObject.getJSONArray(com.android.server.storage.DiskStatsFileLogger.APP_CACHES_KEY);
            int length = jSONArray.length();
            if (jSONArray2.length() == length && jSONArray3.length() == length && jSONArray4.length() == length) {
                int i2 = 0;
                while (i2 < length) {
                    list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, jSONArray.getString(i2), jSONArray2.optLong(i2, -1L), jSONArray3.optLong(i2, -1L), jSONArray4.optLong(i2, -1L), optLong));
                    i2++;
                    jSONArray2 = jSONArray2;
                    jSONArray3 = jSONArray3;
                    length = length;
                }
                return 0;
            }
            android.util.Slog.e(TAG, "formatting error in diskstats cache file!");
            return 1;
        } catch (java.io.IOException | org.json.JSONException e) {
            android.util.Slog.w(TAG, "Unable to read diskstats cache file within pullAppSize");
            return 1;
        }
    }

    private void registerCategorySize() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.CATEGORY_SIZE, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullCategorySizeLocked(int i, java.util.List<android.util.StatsEvent> list) {
        try {
            org.json.JSONObject jSONObject = new org.json.JSONObject(libcore.io.IoUtils.readFileAsString(com.android.server.storage.DiskStatsLoggingService.DUMPSYS_CACHE_PATH));
            long optLong = jSONObject.optLong(com.android.server.storage.DiskStatsFileLogger.LAST_QUERY_TIMESTAMP_KEY, -1L);
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, 1, jSONObject.optLong(com.android.server.storage.DiskStatsFileLogger.APP_SIZE_AGG_KEY, -1L), optLong));
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, 2, jSONObject.optLong(com.android.server.storage.DiskStatsFileLogger.APP_DATA_SIZE_AGG_KEY, -1L), optLong));
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, 3, jSONObject.optLong(com.android.server.storage.DiskStatsFileLogger.APP_CACHE_AGG_KEY, -1L), optLong));
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, 4, jSONObject.optLong(com.android.server.storage.DiskStatsFileLogger.PHOTOS_KEY, -1L), optLong));
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, 5, jSONObject.optLong(com.android.server.storage.DiskStatsFileLogger.VIDEOS_KEY, -1L), optLong));
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, 6, jSONObject.optLong(com.android.server.storage.DiskStatsFileLogger.AUDIO_KEY, -1L), optLong));
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, 7, jSONObject.optLong(com.android.server.storage.DiskStatsFileLogger.DOWNLOADS_KEY, -1L), optLong));
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, 8, jSONObject.optLong(com.android.server.storage.DiskStatsFileLogger.SYSTEM_KEY, -1L), optLong));
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, 9, jSONObject.optLong(com.android.server.storage.DiskStatsFileLogger.MISC_KEY, -1L), optLong));
            return 0;
        } catch (java.io.IOException | org.json.JSONException e) {
            android.util.Slog.w(TAG, "Unable to read diskstats cache file within pullCategorySize");
            return 1;
        }
    }

    private void registerNumFingerprintsEnrolled() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.NUM_FINGERPRINTS_ENROLLED, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private void registerNumFacesEnrolled() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.NUM_FACES_ENROLLED, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int pullNumBiometricsEnrolledLocked(int i, int i2, java.util.List<android.util.StatsEvent> list) {
        android.os.UserManager userManager;
        int size;
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        android.hardware.fingerprint.FingerprintManager fingerprintManager = packageManager.hasSystemFeature("android.hardware.fingerprint") ? (android.hardware.fingerprint.FingerprintManager) this.mContext.getSystemService(android.hardware.fingerprint.FingerprintManager.class) : null;
        android.hardware.face.FaceManager faceManager = packageManager.hasSystemFeature("android.hardware.biometrics.face") ? (android.hardware.face.FaceManager) this.mContext.getSystemService(android.hardware.face.FaceManager.class) : null;
        if (i == 1 && fingerprintManager == null) {
            return 1;
        }
        if ((i == 4 && faceManager == null) || (userManager = (android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class)) == null) {
            return 1;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            java.util.Iterator it = userManager.getUsers().iterator();
            while (it.hasNext()) {
                int identifier = ((android.content.pm.UserInfo) it.next()).getUserHandle().getIdentifier();
                if (i == 1) {
                    size = fingerprintManager.getEnrolledFingerprints(identifier).size();
                } else {
                    if (i != 4) {
                        android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                        return 1;
                    }
                    size = faceManager.getEnrolledFaces(identifier).size();
                }
                list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i2, identifier, size));
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    private void registerProcStats() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.PROC_STATS, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private void registerProcStatsPkgProc() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.PROC_STATS_PKG_PROC, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private void registerProcessState() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.PROCESS_STATE, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private void registerProcessAssociation() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.PROCESS_ASSOCIATION, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    @com.android.internal.annotations.GuardedBy({"mProcStatsLock"})
    private com.android.internal.app.procstats.ProcessStats getStatsFromProcessStatsService(int i) {
        com.android.internal.app.procstats.IProcessStats iProcessStatsService = getIProcessStatsService();
        if (iProcessStatsService == null) {
            return null;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            long readProcStatsHighWaterMark = readProcStatsHighWaterMark(i);
            com.android.internal.app.procstats.ProcessStats processStats = new com.android.internal.app.procstats.ProcessStats(false);
            long committedStatsMerged = iProcessStatsService.getCommittedStatsMerged(readProcStatsHighWaterMark, 31, true, (java.util.List) null, processStats);
            new java.io.File(this.mBaseDir.getAbsolutePath() + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + highWaterMarkFilePrefix(i) + "_" + readProcStatsHighWaterMark).delete();
            new java.io.File(this.mBaseDir.getAbsolutePath() + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + highWaterMarkFilePrefix(i) + "_" + committedStatsMerged).createNewFile();
            return processStats;
        } catch (android.os.RemoteException | java.io.IOException e) {
            android.util.Slog.e(TAG, "Getting procstats failed: ", e);
            return null;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mProcStatsLock"})
    public int pullProcStatsLocked(int i, java.util.List<android.util.StatsEvent> list) {
        com.android.internal.app.procstats.ProcessStats statsFromProcessStatsService = getStatsFromProcessStatsService(i);
        if (statsFromProcessStatsService == null) {
            return 1;
        }
        android.util.proto.ProtoOutputStream[] protoOutputStreamArr = new android.util.proto.ProtoOutputStream[5];
        for (int i2 = 0; i2 < 5; i2++) {
            protoOutputStreamArr[i2] = new android.util.proto.ProtoOutputStream();
        }
        statsFromProcessStatsService.dumpAggregatedProtoForStatsd(protoOutputStreamArr, 58982L);
        for (int i3 = 0; i3 < 5; i3++) {
            byte[] bytes = protoOutputStreamArr[i3].getBytes();
            if (bytes.length > 0) {
                list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, bytes, i3));
            }
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mProcStatsLock"})
    public int pullProcessStateLocked(int i, java.util.List<android.util.StatsEvent> list) {
        com.android.internal.app.procstats.ProcessStats statsFromProcessStatsService = getStatsFromProcessStatsService(i);
        if (statsFromProcessStatsService == null) {
            return 1;
        }
        statsFromProcessStatsService.dumpProcessState(i, new com.android.internal.app.procstats.StatsEventOutput(list));
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mProcStatsLock"})
    public int pullProcessAssociationLocked(int i, java.util.List<android.util.StatsEvent> list) {
        com.android.internal.app.procstats.ProcessStats statsFromProcessStatsService = getStatsFromProcessStatsService(i);
        if (statsFromProcessStatsService == null) {
            return 1;
        }
        statsFromProcessStatsService.dumpProcessAssociation(i, new com.android.internal.app.procstats.StatsEventOutput(list));
        return 0;
    }

    private java.lang.String highWaterMarkFilePrefix(int i) {
        if (i == 10029) {
            return java.lang.String.valueOf(31);
        }
        if (i == 10034) {
            return java.lang.String.valueOf(2);
        }
        return "atom-" + i;
    }

    @com.android.internal.annotations.GuardedBy({"mProcStatsLock"})
    private long readProcStatsHighWaterMark(final int i) {
        try {
            java.io.File[] listFiles = this.mBaseDir.listFiles(new java.io.FilenameFilter() { // from class: com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda17
                @Override // java.io.FilenameFilter
                public final boolean accept(java.io.File file, java.lang.String str) {
                    boolean lambda$readProcStatsHighWaterMark$21;
                    lambda$readProcStatsHighWaterMark$21 = com.android.server.stats.pull.StatsPullAtomService.this.lambda$readProcStatsHighWaterMark$21(i, file, str);
                    return lambda$readProcStatsHighWaterMark$21;
                }
            });
            if (listFiles == null || listFiles.length == 0) {
                return 0L;
            }
            if (listFiles.length > 1) {
                android.util.Slog.e(TAG, "Only 1 file expected for high water mark. Found " + listFiles.length);
            }
            return java.lang.Long.valueOf(listFiles[0].getName().split("_")[1]).longValue();
        } catch (java.lang.NumberFormatException e) {
            android.util.Slog.e(TAG, "Failed to parse file name.", e);
            return 0L;
        } catch (java.lang.SecurityException e2) {
            android.util.Slog.e(TAG, "Failed to get procstats high watermark file.", e2);
            return 0L;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$readProcStatsHighWaterMark$21(int i, java.io.File file, java.lang.String str) {
        return str.toLowerCase().startsWith(highWaterMarkFilePrefix(i) + '_');
    }

    private void registerDiskIO() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.DISK_IO, new android.app.StatsManager.PullAtomMetadata.Builder().setAdditiveFields(new int[]{2, 3, 4, 5, 6, 7, 8, 9, 10, 11}).setCoolDownMillis(com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_QUOTA_EXCEEDED_TIMEOUT_MILLIS).build(), com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullDiskIOLocked(final int i, final java.util.List<android.util.StatsEvent> list) {
        this.mStoragedUidIoStatsReader.readAbsolute(new com.android.internal.os.StoragedUidIoStatsReader.Callback() { // from class: com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda12
            public final void onUidStorageStats(int i2, long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10) {
                com.android.server.stats.pull.StatsPullAtomService.lambda$pullDiskIOLocked$22(list, i, i2, j, j2, j3, j4, j5, j6, j7, j8, j9, j10);
            }
        });
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$pullDiskIOLocked$22(java.util.List list, int i, int i2, long j, long j2, long j3, long j4, long j5, long j6, long j7, long j8, long j9, long j10) {
        list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, i2, j, j2, j3, j4, j5, j6, j7, j8, j9, j10));
    }

    private void registerPowerProfile() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.POWER_PROFILE, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullPowerProfileLocked(int i, java.util.List<android.util.StatsEvent> list) {
        com.android.internal.os.PowerProfile powerProfile = new com.android.internal.os.PowerProfile(this.mContext);
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
        powerProfile.dumpDebug(protoOutputStream);
        protoOutputStream.flush();
        list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, protoOutputStream.getBytes()));
        return 0;
    }

    private void registerProcessCpuTime() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.PROCESS_CPU_TIME, new android.app.StatsManager.PullAtomMetadata.Builder().setCoolDownMillis(5000L).build(), com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullProcessCpuTimeLocked(int i, java.util.List<android.util.StatsEvent> list) {
        if (this.mProcessCpuTracker == null) {
            this.mProcessCpuTracker = new com.android.internal.os.ProcessCpuTracker(false);
            this.mProcessCpuTracker.init();
        }
        this.mProcessCpuTracker.update();
        for (int i2 = 0; i2 < this.mProcessCpuTracker.countStats(); i2++) {
            com.android.internal.os.ProcessCpuTracker.Stats stats = this.mProcessCpuTracker.getStats(i2);
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, stats.uid, stats.name, stats.base_utime, stats.base_stime));
        }
        return 0;
    }

    private void registerCpuTimePerThreadFreq() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.CPU_TIME_PER_THREAD_FREQ, new android.app.StatsManager.PullAtomMetadata.Builder().setAdditiveFields(new int[]{7, 9, 11, 13, 15, 17, 19, 21}).build(), com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullCpuTimePerThreadFreqLocked(int i, java.util.List<android.util.StatsEvent> list) {
        if (this.mKernelCpuThreadReader == null) {
            android.util.Slog.e(TAG, "mKernelCpuThreadReader is null");
            return 1;
        }
        java.util.ArrayList processCpuUsageDiffed = this.mKernelCpuThreadReader.getProcessCpuUsageDiffed();
        if (processCpuUsageDiffed == null) {
            android.util.Slog.e(TAG, "processCpuUsages is null");
            return 1;
        }
        int[] cpuFrequenciesKhz = this.mKernelCpuThreadReader.getCpuFrequenciesKhz();
        if (cpuFrequenciesKhz.length > 8) {
            android.util.Slog.w(TAG, "Expected maximum 8 frequencies, but got " + cpuFrequenciesKhz.length);
            return 1;
        }
        for (int i2 = 0; i2 < processCpuUsageDiffed.size(); i2++) {
            com.android.internal.os.KernelCpuThreadReader.ProcessCpuUsage processCpuUsage = (com.android.internal.os.KernelCpuThreadReader.ProcessCpuUsage) processCpuUsageDiffed.get(i2);
            java.util.ArrayList arrayList = processCpuUsage.threadCpuUsages;
            for (int i3 = 0; i3 < arrayList.size(); i3++) {
                com.android.internal.os.KernelCpuThreadReader.ThreadCpuUsage threadCpuUsage = (com.android.internal.os.KernelCpuThreadReader.ThreadCpuUsage) arrayList.get(i3);
                if (threadCpuUsage.usageTimesMillis.length != cpuFrequenciesKhz.length) {
                    android.util.Slog.w(TAG, "Unexpected number of usage times, expected " + cpuFrequenciesKhz.length + " but got " + threadCpuUsage.usageTimesMillis.length);
                    return 1;
                }
                int[] iArr = new int[8];
                int[] iArr2 = new int[8];
                for (int i4 = 0; i4 < 8; i4++) {
                    if (i4 < cpuFrequenciesKhz.length) {
                        iArr[i4] = cpuFrequenciesKhz[i4];
                        iArr2[i4] = threadCpuUsage.usageTimesMillis[i4];
                    } else {
                        iArr[i4] = 0;
                        iArr2[i4] = 0;
                    }
                }
                list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, processCpuUsage.uid, processCpuUsage.processId, threadCpuUsage.threadId, processCpuUsage.processName, threadCpuUsage.threadName, iArr[0], iArr2[0], iArr[1], iArr2[1], iArr[2], iArr2[2], iArr[3], iArr2[3], iArr[4], iArr2[4], iArr[5], iArr2[5], iArr[6], iArr2[6], iArr[7], iArr2[7]));
            }
        }
        return 0;
    }

    private long milliAmpHrsToNanoAmpSecs(double d) {
        return (long) ((d * 3.6E9d) + 0.5d);
    }

    private void registerDeviceCalculatedPowerUse() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.DEVICE_CALCULATED_POWER_USE, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullDeviceCalculatedPowerUseLocked(int i, java.util.List<android.util.StatsEvent> list) {
        try {
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, milliAmpHrsToNanoAmpSecs(((android.os.BatteryStatsManager) this.mContext.getSystemService(android.os.BatteryStatsManager.class)).getBatteryUsageStats().getConsumedPower())));
            return 0;
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Could not obtain battery usage stats", e);
            return 1;
        }
    }

    private void registerDebugElapsedClock() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.DEBUG_ELAPSED_CLOCK, new android.app.StatsManager.PullAtomMetadata.Builder().setAdditiveFields(new int[]{1, 2, 3, 4}).build(), com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullDebugElapsedClockLocked(int i, java.util.List<android.util.StatsEvent> list) {
        long j;
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        if (this.mDebugElapsedClockPreviousValue != 0) {
            j = elapsedRealtime - this.mDebugElapsedClockPreviousValue;
        } else {
            j = 0;
        }
        list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, this.mDebugElapsedClockPullCount, elapsedRealtime, elapsedRealtime, j, 1));
        if (this.mDebugElapsedClockPullCount % 2 == 1) {
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, this.mDebugElapsedClockPullCount, elapsedRealtime, elapsedRealtime, j, 2));
        }
        this.mDebugElapsedClockPullCount++;
        this.mDebugElapsedClockPreviousValue = elapsedRealtime;
        return 0;
    }

    private void registerDebugFailingElapsedClock() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.DEBUG_FAILING_ELAPSED_CLOCK, new android.app.StatsManager.PullAtomMetadata.Builder().setAdditiveFields(new int[]{1, 2, 3, 4}).build(), com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullDebugFailingElapsedClockLocked(int i, java.util.List<android.util.StatsEvent> list) {
        long j;
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        long j2 = this.mDebugFailingElapsedClockPullCount;
        this.mDebugFailingElapsedClockPullCount = 1 + j2;
        if (j2 % 5 == 0) {
            this.mDebugFailingElapsedClockPreviousValue = elapsedRealtime;
            android.util.Slog.e(TAG, "Failing debug elapsed clock");
            return 1;
        }
        long j3 = this.mDebugFailingElapsedClockPullCount;
        if (this.mDebugFailingElapsedClockPreviousValue == 0) {
            j = 0;
        } else {
            j = elapsedRealtime - this.mDebugFailingElapsedClockPreviousValue;
        }
        list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, j3, elapsedRealtime, elapsedRealtime, j));
        this.mDebugFailingElapsedClockPreviousValue = elapsedRealtime;
        return 0;
    }

    private void registerBuildInformation() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.BUILD_INFORMATION, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullBuildInformationLocked(int i, java.util.List<android.util.StatsEvent> list) {
        list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, android.os.Build.FINGERPRINT, android.os.Build.BRAND, android.os.Build.PRODUCT, android.os.Build.DEVICE, android.os.Build.VERSION.RELEASE_OR_CODENAME, android.os.Build.ID, android.os.Build.VERSION.INCREMENTAL, android.os.Build.TYPE, android.os.Build.TAGS));
        return 0;
    }

    private void registerRoleHolder() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.ROLE_HOLDER, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullRoleHolderLocked(int i, java.util.List<android.util.StatsEvent> list) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
            com.android.role.RoleManagerLocal roleManagerLocal = (com.android.role.RoleManagerLocal) com.android.server.LocalManagerRegistry.getManager(com.android.role.RoleManagerLocal.class);
            java.util.List users = ((android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class)).getUsers();
            int size = users.size();
            for (int i2 = 0; i2 < size; i2++) {
                int identifier = ((android.content.pm.UserInfo) users.get(i2)).getUserHandle().getIdentifier();
                for (java.util.Map.Entry entry : roleManagerLocal.getRolesAndHolders(identifier).entrySet()) {
                    java.lang.String str = (java.lang.String) entry.getKey();
                    for (java.lang.String str2 : (java.util.Set) entry.getValue()) {
                        try {
                            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, packageManager.getPackageInfoAsUser(str2, 0, identifier).applicationInfo.uid, str2, str));
                        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                            android.util.Slog.w(TAG, "Role holder " + str2 + " not found");
                            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                            return 1;
                        }
                    }
                }
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    private void registerDangerousPermissionState() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.DANGEROUS_PERMISSION_STATE, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullDangerousPermissionStateLocked(int i, java.util.List<android.util.StatsEvent> list) {
        float f;
        java.util.HashSet hashSet;
        int i2;
        java.util.List list2;
        android.os.UserHandle userHandle;
        int i3;
        int i4;
        int i5;
        java.util.List list3;
        android.os.UserHandle userHandle2;
        int i6;
        android.content.pm.PackageInfo packageInfo;
        android.content.pm.PermissionInfo permissionInfo;
        java.lang.String str;
        android.util.StatsEvent buildStatsEvent;
        int i7 = i;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        float f2 = android.provider.DeviceConfig.getFloat("permissions", DANGEROUS_PERMISSION_STATE_SAMPLE_RATE, 0.015f);
        java.util.HashSet hashSet2 = new java.util.HashSet();
        try {
            android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
            java.util.List users = ((android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class)).getUsers();
            int size = users.size();
            int i8 = 0;
            while (i8 < size) {
                android.os.UserHandle userHandle3 = ((android.content.pm.UserInfo) users.get(i8)).getUserHandle();
                java.util.List installedPackagesAsUser = packageManager.getInstalledPackagesAsUser(4096, userHandle3.getIdentifier());
                int size2 = installedPackagesAsUser.size();
                int i9 = 0;
                while (i9 < size2) {
                    android.content.pm.PackageInfo packageInfo2 = (android.content.pm.PackageInfo) installedPackagesAsUser.get(i9);
                    if (packageInfo2.requestedPermissions == null) {
                        i2 = size2;
                        list2 = installedPackagesAsUser;
                        userHandle = userHandle3;
                        i3 = i8;
                        i4 = size;
                        f = f2;
                        hashSet = hashSet2;
                    } else if (hashSet2.contains(java.lang.Integer.valueOf(packageInfo2.applicationInfo.uid))) {
                        i2 = size2;
                        list2 = installedPackagesAsUser;
                        userHandle = userHandle3;
                        i3 = i8;
                        i4 = size;
                        f = f2;
                        hashSet = hashSet2;
                    } else {
                        hashSet2.add(java.lang.Integer.valueOf(packageInfo2.applicationInfo.uid));
                        if (i7 == 10067 && java.util.concurrent.ThreadLocalRandom.current().nextFloat() > f2) {
                            i2 = size2;
                            list2 = installedPackagesAsUser;
                            userHandle = userHandle3;
                            i3 = i8;
                            i4 = size;
                            f = f2;
                            hashSet = hashSet2;
                        } else {
                            f = f2;
                            int length = packageInfo2.requestedPermissions.length;
                            hashSet = hashSet2;
                            int i10 = 0;
                            while (i10 < length) {
                                java.lang.String str2 = packageInfo2.requestedPermissions[i10];
                                int i11 = size;
                                try {
                                    permissionInfo = packageManager.getPermissionInfo(str2, 0);
                                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                                    i5 = size2;
                                    list3 = installedPackagesAsUser;
                                    userHandle2 = userHandle3;
                                    i6 = i8;
                                    packageInfo = packageInfo2;
                                }
                                try {
                                    int permissionFlags = packageManager.getPermissionFlags(str2, packageInfo2.packageName, userHandle3);
                                    if (!str2.startsWith(COMMON_PERMISSION_PREFIX)) {
                                        i5 = size2;
                                        str = str2;
                                    } else {
                                        i5 = size2;
                                        str = str2.substring(COMMON_PERMISSION_PREFIX.length());
                                    }
                                    if (i7 == 10050) {
                                        list3 = installedPackagesAsUser;
                                        userHandle2 = userHandle3;
                                        i6 = i8;
                                        packageInfo = packageInfo2;
                                        buildStatsEvent = com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, str, packageInfo2.applicationInfo.uid, "", (packageInfo2.requestedPermissionsFlags[i10] & 2) != 0, permissionFlags, permissionInfo.getProtection() | permissionInfo.getProtectionFlags());
                                    } else {
                                        list3 = installedPackagesAsUser;
                                        userHandle2 = userHandle3;
                                        i6 = i8;
                                        packageInfo = packageInfo2;
                                        buildStatsEvent = com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, str, packageInfo.applicationInfo.uid, (packageInfo.requestedPermissionsFlags[i10] & 2) != 0, permissionFlags, permissionInfo.getProtection() | permissionInfo.getProtectionFlags());
                                    }
                                    list.add(buildStatsEvent);
                                } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
                                    i5 = size2;
                                    list3 = installedPackagesAsUser;
                                    userHandle2 = userHandle3;
                                    i6 = i8;
                                    packageInfo = packageInfo2;
                                    i10++;
                                    packageInfo2 = packageInfo;
                                    size = i11;
                                    size2 = i5;
                                    i8 = i6;
                                    userHandle3 = userHandle2;
                                    installedPackagesAsUser = list3;
                                    i7 = i;
                                }
                                i10++;
                                packageInfo2 = packageInfo;
                                size = i11;
                                size2 = i5;
                                i8 = i6;
                                userHandle3 = userHandle2;
                                installedPackagesAsUser = list3;
                                i7 = i;
                            }
                            i2 = size2;
                            list2 = installedPackagesAsUser;
                            userHandle = userHandle3;
                            i3 = i8;
                            i4 = size;
                        }
                    }
                    i9++;
                    i7 = i;
                    f2 = f;
                    hashSet2 = hashSet;
                    size = i4;
                    size2 = i2;
                    i8 = i3;
                    userHandle3 = userHandle;
                    installedPackagesAsUser = list2;
                }
                i8++;
                i7 = i;
            }
            return 0;
        } catch (java.lang.Throwable th) {
            try {
                android.util.Log.e(TAG, "Could not read permissions", th);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return 1;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    private void registerTimeZoneDataInfo() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.TIME_ZONE_DATA_INFO, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullTimeZoneDataInfoLocked(int i, java.util.List<android.util.StatsEvent> list) {
        try {
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, android.icu.util.TimeZone.getTZDataVersion()));
            return 0;
        } catch (java.util.MissingResourceException e) {
            android.util.Slog.e(TAG, "Getting tzdb version failed: ", e);
            return 1;
        }
    }

    private void registerTimeZoneDetectorState() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.TIME_ZONE_DETECTOR_STATE, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullTimeZoneDetectorStateLocked(int i, java.util.List<android.util.StatsEvent> list) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                com.android.server.timezonedetector.MetricsTimeZoneDetectorState generateMetricsState = ((com.android.server.timezonedetector.TimeZoneDetectorInternal) com.android.server.LocalServices.getService(com.android.server.timezonedetector.TimeZoneDetectorInternal.class)).generateMetricsState();
                list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, generateMetricsState.isTelephonyDetectionSupported(), generateMetricsState.isGeoDetectionSupported(), generateMetricsState.getUserLocationEnabledSetting(), generateMetricsState.getAutoDetectionEnabledSetting(), generateMetricsState.getGeoDetectionEnabledSetting(), convertToMetricsDetectionMode(generateMetricsState.getDetectionMode()), generateMetricsState.getDeviceTimeZoneIdOrdinal(), convertTimeZoneSuggestionToProtoBytes(generateMetricsState.getLatestManualSuggestion()), convertTimeZoneSuggestionToProtoBytes(generateMetricsState.getLatestTelephonySuggestion()), convertTimeZoneSuggestionToProtoBytes(generateMetricsState.getLatestGeolocationSuggestion()), generateMetricsState.isTelephonyTimeZoneFallbackSupported(), generateMetricsState.getDeviceTimeZoneId(), generateMetricsState.isEnhancedMetricsCollectionEnabled(), generateMetricsState.getGeoDetectionRunInBackgroundEnabled()));
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return 0;
            } catch (java.lang.RuntimeException e) {
                android.util.Slog.e(TAG, "Getting time zone detection state failed: ", e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return 1;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    private static int convertToMetricsDetectionMode(int i) {
        switch (i) {
            case 1:
                return 1;
            case 2:
                return 3;
            case 3:
                return 2;
            default:
                return 0;
        }
    }

    @android.annotation.Nullable
    private static byte[] convertTimeZoneSuggestionToProtoBytes(@android.annotation.Nullable com.android.server.timezonedetector.MetricsTimeZoneDetectorState.MetricsTimeZoneSuggestion metricsTimeZoneSuggestion) {
        int i;
        if (metricsTimeZoneSuggestion == null) {
            return null;
        }
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(byteArrayOutputStream);
        if (metricsTimeZoneSuggestion.isCertain()) {
            i = 1;
        } else {
            i = 2;
        }
        protoOutputStream.write(1159641169921L, i);
        if (metricsTimeZoneSuggestion.isCertain()) {
            for (int i2 : metricsTimeZoneSuggestion.getZoneIdOrdinals()) {
                protoOutputStream.write(2220498092034L, i2);
            }
            java.lang.String[] zoneIds = metricsTimeZoneSuggestion.getZoneIds();
            if (zoneIds != null) {
                for (java.lang.String str : zoneIds) {
                    protoOutputStream.write(2237677961219L, str);
                }
            }
        }
        protoOutputStream.flush();
        libcore.io.IoUtils.closeQuietly(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void registerExternalStorageInfo() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.EXTERNAL_STORAGE_INFO, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullExternalStorageInfoLocked(int i, java.util.List<android.util.StatsEvent> list) {
        int i2;
        if (this.mStorageManager == null) {
            return 1;
        }
        for (android.os.storage.VolumeInfo volumeInfo : this.mStorageManager.getVolumes()) {
            java.lang.String environmentForState = android.os.storage.VolumeInfo.getEnvironmentForState(volumeInfo.getState());
            android.os.storage.DiskInfo disk = volumeInfo.getDisk();
            if (disk != null && environmentForState.equals("mounted")) {
                int i3 = 2;
                if (volumeInfo.getType() == 0) {
                    i2 = 1;
                } else if (volumeInfo.getType() != 1) {
                    i2 = 3;
                } else {
                    i2 = 2;
                }
                if (disk.isSd()) {
                    i3 = 1;
                } else if (!disk.isUsb()) {
                    i3 = 3;
                }
                list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, i3, i2, disk.size));
            }
        }
        return 0;
    }

    private void registerAppsOnExternalStorageInfo() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.APPS_ON_EXTERNAL_STORAGE_INFO, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullAppsOnExternalStorageInfoLocked(int i, java.util.List<android.util.StatsEvent> list) {
        android.os.storage.VolumeInfo findVolumeByUuid;
        android.os.storage.DiskInfo disk;
        int i2;
        if (this.mStorageManager == null) {
            return 1;
        }
        for (android.content.pm.ApplicationInfo applicationInfo : this.mContext.getPackageManager().getInstalledApplications(0)) {
            if (applicationInfo.storageUuid != null && (findVolumeByUuid = this.mStorageManager.findVolumeByUuid(applicationInfo.storageUuid.toString())) != null && (disk = findVolumeByUuid.getDisk()) != null) {
                if (disk.isSd()) {
                    i2 = 1;
                } else if (disk.isUsb()) {
                    i2 = 2;
                } else if (!applicationInfo.isExternal()) {
                    i2 = -1;
                } else {
                    i2 = 3;
                }
                if (i2 != -1) {
                    list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, i2, applicationInfo.packageName));
                }
            }
        }
        return 0;
    }

    private void registerFaceSettings() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.FACE_SETTINGS, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullFaceSettingsLocked(int i, java.util.List<android.util.StatsEvent> list) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.os.UserManager userManager = (android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class);
            if (userManager == null) {
                return 1;
            }
            java.util.List users = userManager.getUsers();
            int size = users.size();
            for (int i2 = 0; i2 < size; i2++) {
                int identifier = ((android.content.pm.UserInfo) users.get(i2)).getUserHandle().getIdentifier();
                int intForUser = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "face_unlock_keyguard_enabled", 1, identifier);
                int intForUser2 = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "face_unlock_dismisses_keyguard", 1, identifier);
                int intForUser3 = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "face_unlock_attention_required", 0, identifier);
                int intForUser4 = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "face_unlock_app_enabled", 1, identifier);
                int intForUser5 = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "face_unlock_always_require_confirmation", 0, identifier);
                int intForUser6 = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "face_unlock_diversity_required", 1, identifier);
                if (intForUser != 0) {
                    z = true;
                } else {
                    z = false;
                }
                if (intForUser2 != 0) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (intForUser3 != 0) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                if (intForUser4 != 0) {
                    z4 = true;
                } else {
                    z4 = false;
                }
                if (intForUser5 != 0) {
                    z5 = true;
                } else {
                    z5 = false;
                }
                if (intForUser6 != 0) {
                    z6 = true;
                } else {
                    z6 = false;
                }
                list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, z, z2, z3, z4, z5, z6));
            }
            return 0;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void registerAppOps() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.APP_OPS, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private void registerRuntimeAppOpAccessMessage() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.RUNTIME_APP_OP_ACCESS, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private class AppOpEntry {
        public final java.lang.String mAttributionTag;
        public final int mHash;
        public final android.app.AppOpsManager.HistoricalOp mOp;
        public final java.lang.String mPackageName;
        public final int mUid;

        AppOpEntry(java.lang.String str, @android.annotation.Nullable java.lang.String str2, android.app.AppOpsManager.HistoricalOp historicalOp, int i) {
            this.mPackageName = str;
            this.mAttributionTag = str2;
            this.mUid = i;
            this.mOp = historicalOp;
            this.mHash = ((str.hashCode() + com.android.server.stats.pull.StatsPullAtomService.RANDOM_SEED) & Integer.MAX_VALUE) % 100;
        }
    }

    int pullAppOpsLocked(int i, java.util.List<android.util.StatsEvent> list) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.app.AppOpsManager appOpsManager = (android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class);
            java.util.concurrent.CompletableFuture completableFuture = new java.util.concurrent.CompletableFuture();
            android.app.AppOpsManager.HistoricalOpsRequest build = new android.app.AppOpsManager.HistoricalOpsRequest.Builder(0L, com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME).setFlags(9).build();
            java.util.concurrent.Executor executor = android.os.AsyncTask.THREAD_POOL_EXECUTOR;
            java.util.Objects.requireNonNull(completableFuture);
            appOpsManager.getHistoricalOps(build, executor, new com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda0(completableFuture));
            if (sampleAppOps(list, processHistoricalOps((android.app.AppOpsManager.HistoricalOps) completableFuture.get(EXTERNAL_STATS_SYNC_TIMEOUT_MILLIS, java.util.concurrent.TimeUnit.MILLISECONDS), i, 100), i, 100) != 100) {
                android.util.Slog.e(TAG, "Atom 10060 downsampled - too many dimensions");
            }
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0;
        } catch (java.lang.Throwable th) {
            try {
                android.util.Slog.e(TAG, "Could not read appops", th);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return 1;
            } catch (java.lang.Throwable th2) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th2;
            }
        }
    }

    private int sampleAppOps(java.util.List<android.util.StatsEvent> list, java.util.List<com.android.server.stats.pull.StatsPullAtomService.AppOpEntry> list2, int i, int i2) {
        int i3;
        int i4;
        android.util.StatsEvent buildStatsEvent;
        java.util.List<android.util.StatsEvent> list3 = list;
        java.util.List<com.android.server.stats.pull.StatsPullAtomService.AppOpEntry> list4 = list2;
        int i5 = i;
        int i6 = i2;
        int size = list2.size();
        int i7 = 0;
        while (i7 < size) {
            com.android.server.stats.pull.StatsPullAtomService.AppOpEntry appOpEntry = list4.get(i7);
            if (appOpEntry.mHash >= i6) {
                i3 = i7;
                i4 = size;
            } else {
                if (i5 == 10075) {
                    i3 = i7;
                    i4 = size;
                    buildStatsEvent = com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, appOpEntry.mUid, appOpEntry.mPackageName, appOpEntry.mAttributionTag, appOpEntry.mOp.getOpCode(), appOpEntry.mOp.getForegroundAccessCount(9), appOpEntry.mOp.getBackgroundAccessCount(9), appOpEntry.mOp.getForegroundRejectCount(9), appOpEntry.mOp.getBackgroundRejectCount(9), appOpEntry.mOp.getForegroundAccessDuration(9), appOpEntry.mOp.getBackgroundAccessDuration(9), this.mDangerousAppOpsList.contains(java.lang.Integer.valueOf(appOpEntry.mOp.getOpCode())), i2);
                } else {
                    i3 = i7;
                    i4 = size;
                    buildStatsEvent = com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, appOpEntry.mUid, appOpEntry.mPackageName, appOpEntry.mOp.getOpCode(), appOpEntry.mOp.getForegroundAccessCount(9), appOpEntry.mOp.getBackgroundAccessCount(9), appOpEntry.mOp.getForegroundRejectCount(9), appOpEntry.mOp.getBackgroundRejectCount(9), appOpEntry.mOp.getForegroundAccessDuration(9), appOpEntry.mOp.getBackgroundAccessDuration(9), this.mDangerousAppOpsList.contains(java.lang.Integer.valueOf(appOpEntry.mOp.getOpCode())));
                }
                list3 = list;
                list3.add(buildStatsEvent);
            }
            i7 = i3 + 1;
            list4 = list2;
            i5 = i;
            i6 = i2;
            size = i4;
        }
        if (list.size() > 800) {
            int constrain = android.util.MathUtils.constrain((i2 * 500) / list.size(), 0, i2 - 1);
            list.clear();
            return sampleAppOps(list3, list2, i, constrain);
        }
        return i2;
    }

    private void registerAttributedAppOps() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.ATTRIBUTED_APP_OPS, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullAttributedAppOpsLocked(int i, java.util.List<android.util.StatsEvent> list) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.app.AppOpsManager appOpsManager = (android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class);
            java.util.concurrent.CompletableFuture completableFuture = new java.util.concurrent.CompletableFuture();
            android.app.AppOpsManager.HistoricalOpsRequest build = new android.app.AppOpsManager.HistoricalOpsRequest.Builder(0L, com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME).setFlags(9).build();
            java.util.concurrent.Executor executor = android.os.AsyncTask.THREAD_POOL_EXECUTOR;
            java.util.Objects.requireNonNull(completableFuture);
            appOpsManager.getHistoricalOps(build, executor, new com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda0(completableFuture));
            android.app.AppOpsManager.HistoricalOps historicalOps = (android.app.AppOpsManager.HistoricalOps) completableFuture.get(EXTERNAL_STATS_SYNC_TIMEOUT_MILLIS, java.util.concurrent.TimeUnit.MILLISECONDS);
            if (this.mAppOpsSamplingRate == 0) {
                this.mContext.getMainThreadHandler().postDelayed(new java.lang.Runnable() { // from class: com.android.server.stats.pull.StatsPullAtomService.4
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            com.android.server.stats.pull.StatsPullAtomService.this.estimateAppOpsSamplingRate();
                        } finally {
                        }
                    }
                }, APP_OPS_SAMPLING_INITIALIZATION_DELAY_MILLIS);
                this.mAppOpsSamplingRate = 100;
            }
            this.mAppOpsSamplingRate = java.lang.Math.min(this.mAppOpsSamplingRate, sampleAppOps(list, processHistoricalOps(historicalOps, i, this.mAppOpsSamplingRate), i, this.mAppOpsSamplingRate));
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0;
        } catch (java.lang.Throwable th) {
            try {
                android.util.Slog.e(TAG, "Could not read appops", th);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return 1;
            } catch (java.lang.Throwable th2) {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th2;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void estimateAppOpsSamplingRate() throws java.lang.Exception {
        int i = android.provider.DeviceConfig.getInt("permissions", APP_OPS_TARGET_COLLECTION_SIZE, 2000);
        android.app.AppOpsManager appOpsManager = (android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class);
        java.util.concurrent.CompletableFuture completableFuture = new java.util.concurrent.CompletableFuture();
        long j = 0;
        android.app.AppOpsManager.HistoricalOpsRequest build = new android.app.AppOpsManager.HistoricalOpsRequest.Builder(java.lang.Math.max(java.time.Instant.now().minus(1L, (java.time.temporal.TemporalUnit) java.time.temporal.ChronoUnit.DAYS).toEpochMilli(), 0L), com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME).setFlags(9).build();
        java.util.concurrent.Executor executor = android.os.AsyncTask.THREAD_POOL_EXECUTOR;
        java.util.Objects.requireNonNull(completableFuture);
        appOpsManager.getHistoricalOps(build, executor, new com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda0(completableFuture));
        java.util.List<com.android.server.stats.pull.StatsPullAtomService.AppOpEntry> processHistoricalOps = processHistoricalOps((android.app.AppOpsManager.HistoricalOps) completableFuture.get(EXTERNAL_STATS_SYNC_TIMEOUT_MILLIS, java.util.concurrent.TimeUnit.MILLISECONDS), com.android.internal.util.FrameworkStatsLog.ATTRIBUTED_APP_OPS, 100);
        int size = processHistoricalOps.size();
        for (int i2 = 0; i2 < size; i2++) {
            j += r5.mPackageName.length() + 32 + (processHistoricalOps.get(i2).mAttributionTag == null ? 1 : r5.mAttributionTag.length());
        }
        int constrain = (int) android.util.MathUtils.constrain((i * 100) / j, 0L, 100L);
        synchronized (this.mAttributedAppOpsLock) {
            this.mAppOpsSamplingRate = java.lang.Math.min(this.mAppOpsSamplingRate, constrain);
        }
    }

    private java.util.List<com.android.server.stats.pull.StatsPullAtomService.AppOpEntry> processHistoricalOps(android.app.AppOpsManager.HistoricalOps historicalOps, int i, int i2) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i3 = 0; i3 < historicalOps.getUidCount(); i3++) {
            android.app.AppOpsManager.HistoricalUidOps uidOpsAt = historicalOps.getUidOpsAt(i3);
            int uid = uidOpsAt.getUid();
            for (int i4 = 0; i4 < uidOpsAt.getPackageCount(); i4++) {
                android.app.AppOpsManager.HistoricalPackageOps packageOpsAt = uidOpsAt.getPackageOpsAt(i4);
                if (i != 10075) {
                    if (i == 10060) {
                        for (int i5 = 0; i5 < packageOpsAt.getOpCount(); i5++) {
                            processHistoricalOp(packageOpsAt.getOpAt(i5), arrayList, uid, i2, packageOpsAt.getPackageName(), null);
                        }
                    }
                } else {
                    int i6 = 0;
                    while (i6 < packageOpsAt.getAttributedOpsCount()) {
                        int i7 = 0;
                        for (android.app.AppOpsManager.AttributedHistoricalOps attributedOpsAt = packageOpsAt.getAttributedOpsAt(i6); i7 < attributedOpsAt.getOpCount(); attributedOpsAt = attributedOpsAt) {
                            processHistoricalOp(attributedOpsAt.getOpAt(i7), arrayList, uid, i2, packageOpsAt.getPackageName(), attributedOpsAt.getTag());
                            i7++;
                            i6 = i6;
                        }
                        i6++;
                    }
                }
            }
        }
        return arrayList;
    }

    private void processHistoricalOp(android.app.AppOpsManager.HistoricalOp historicalOp, java.util.List<com.android.server.stats.pull.StatsPullAtomService.AppOpEntry> list, int i, int i2, java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        int i3;
        if (str2 != null && str2.startsWith(str)) {
            i3 = str.length();
            if (i3 < str2.length() && str2.charAt(i3) == '.') {
                i3++;
            }
        } else {
            i3 = 0;
        }
        com.android.server.stats.pull.StatsPullAtomService.AppOpEntry appOpEntry = new com.android.server.stats.pull.StatsPullAtomService.AppOpEntry(str, str2 == null ? null : str2.substring(i3), historicalOp, i);
        if (appOpEntry.mHash < i2) {
            list.add(appOpEntry);
        }
    }

    int pullRuntimeAppOpAccessMessageLocked(int i, java.util.List<android.util.StatsEvent> list) {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.app.RuntimeAppOpAccessMessage collectRuntimeAppOpAccessMessage = ((android.app.AppOpsManager) this.mContext.getSystemService(android.app.AppOpsManager.class)).collectRuntimeAppOpAccessMessage();
            if (collectRuntimeAppOpAccessMessage == null) {
                android.util.Slog.i(TAG, "No runtime appop access message collected");
                return 0;
            }
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, collectRuntimeAppOpAccessMessage.getUid(), collectRuntimeAppOpAccessMessage.getPackageName(), "", collectRuntimeAppOpAccessMessage.getAttributionTag() == null ? "" : collectRuntimeAppOpAccessMessage.getAttributionTag(), collectRuntimeAppOpAccessMessage.getMessage(), collectRuntimeAppOpAccessMessage.getSamplingStrategy(), android.app.AppOpsManager.strOpToOp(collectRuntimeAppOpAccessMessage.getOp())));
            return 0;
        } catch (java.lang.Throwable th) {
            try {
                android.util.Slog.e(TAG, "Could not read runtime appop access message", th);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return 1;
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    static void unpackStreamedData(int i, java.util.List<android.util.StatsEvent> list, java.util.List<android.os.ParcelFileDescriptor> list2) throws java.io.IOException {
        android.os.ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new android.os.ParcelFileDescriptor.AutoCloseInputStream(list2.get(0));
        int[] iArr = new int[1];
        list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, java.util.Arrays.copyOf(readFully(autoCloseInputStream, iArr), iArr[0])));
    }

    static byte[] readFully(java.io.InputStream inputStream, int[] iArr) throws java.io.IOException {
        int available = inputStream.available();
        byte[] bArr = new byte[available > 0 ? available + 1 : 16384];
        int i = 0;
        while (true) {
            int read = inputStream.read(bArr, i, bArr.length - i);
            android.util.Slog.i(TAG, "Read " + read + " bytes at " + i + " of avail " + bArr.length);
            if (read < 0) {
                android.util.Slog.i(TAG, "**** FINISHED READING: pos=" + i + " len=" + bArr.length);
                iArr[0] = i;
                return bArr;
            }
            i += read;
            if (i >= bArr.length) {
                int i2 = i + 16384;
                byte[] bArr2 = new byte[i2];
                android.util.Slog.i(TAG, "Copying " + i + " bytes to new array len " + i2);
                java.lang.System.arraycopy(bArr, 0, bArr2, 0, i);
                bArr = bArr2;
            }
        }
    }

    private void registerNotificationRemoteViews() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.NOTIFICATION_REMOTE_VIEWS, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullNotificationRemoteViewsLocked(int i, java.util.List<android.util.StatsEvent> list) {
        android.app.INotificationManager iNotificationManagerService = getINotificationManagerService();
        if (iNotificationManagerService == null) {
            return 1;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            long currentTimeMicro = (android.os.SystemClock.currentTimeMicro() * 1000) - java.util.concurrent.TimeUnit.NANOSECONDS.convert(1L, java.util.concurrent.TimeUnit.DAYS);
            java.util.ArrayList arrayList = new java.util.ArrayList();
            iNotificationManagerService.pullStats(currentTimeMicro, 1, true, arrayList);
            if (arrayList.size() != 1) {
                return 1;
            }
            unpackStreamedData(i, list, arrayList);
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0;
        } catch (java.io.IOException e) {
            android.util.Slog.e(TAG, "Getting notistats failed: ", e);
            return 1;
        } catch (java.lang.SecurityException e2) {
            android.util.Slog.e(TAG, "Getting notistats failed: ", e2);
            return 1;
        } catch (android.os.RemoteException e3) {
            android.util.Slog.e(TAG, "Getting notistats failed: ", e3);
            return 1;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void registerDangerousPermissionStateSampled() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.DANGEROUS_PERMISSION_STATE_SAMPLED, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private void registerBatteryLevel() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.BATTERY_LEVEL, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private void registerRemainingBatteryCapacity() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.REMAINING_BATTERY_CAPACITY, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private void registerFullBatteryCapacity() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.FULL_BATTERY_CAPACITY, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private void registerBatteryVoltage() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.BATTERY_VOLTAGE, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private void registerBatteryCycleCount() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.BATTERY_CYCLE_COUNT, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullHealthHalLocked(int i, java.util.List<android.util.StatsEvent> list) {
        int i2;
        if (this.mHealthService == null) {
            return 1;
        }
        try {
            android.hardware.health.HealthInfo healthInfo = this.mHealthService.getHealthInfo();
            if (healthInfo == null) {
                return 1;
            }
            switch (i) {
                case com.android.internal.util.FrameworkStatsLog.REMAINING_BATTERY_CAPACITY /* 10019 */:
                    i2 = healthInfo.batteryChargeCounterUah;
                    break;
                case com.android.internal.util.FrameworkStatsLog.FULL_BATTERY_CAPACITY /* 10020 */:
                    i2 = healthInfo.batteryFullChargeUah;
                    break;
                case com.android.internal.util.FrameworkStatsLog.BATTERY_VOLTAGE /* 10030 */:
                    i2 = healthInfo.batteryVoltageMillivolts;
                    break;
                case com.android.internal.util.FrameworkStatsLog.BATTERY_LEVEL /* 10043 */:
                    i2 = healthInfo.batteryLevel;
                    break;
                case com.android.internal.util.FrameworkStatsLog.BATTERY_CYCLE_COUNT /* 10045 */:
                    i2 = healthInfo.batteryCycleCount;
                    break;
                default:
                    return 1;
            }
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, i2));
            return 0;
        } catch (android.os.RemoteException | java.lang.IllegalStateException e) {
            return 1;
        }
    }

    private void registerSettingsStats() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.SETTING_SNAPSHOT, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullSettingsStatsLocked(int i, java.util.List<android.util.StatsEvent> list) {
        android.os.UserManager userManager = (android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class);
        if (userManager == null) {
            return 1;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                java.util.Iterator it = userManager.getUsers().iterator();
                while (it.hasNext()) {
                    int identifier = ((android.content.pm.UserInfo) it.next()).getUserHandle().getIdentifier();
                    if (identifier == 0) {
                        list.addAll(com.android.server.stats.pull.SettingsStatsUtil.logGlobalSettings(this.mContext, i, 0));
                    }
                    list.addAll(com.android.server.stats.pull.SettingsStatsUtil.logSystemSettings(this.mContext, i, identifier));
                    list.addAll(com.android.server.stats.pull.SettingsStatsUtil.logSecureSettings(this.mContext, i, identifier));
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return 0;
            } catch (java.lang.Exception e) {
                android.util.Slog.e(TAG, "failed to pullSettingsStats", e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return 1;
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    private void registerInstalledIncrementalPackages() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.INSTALLED_INCREMENTAL_PACKAGE, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullInstalledIncrementalPackagesLocked(int i, java.util.List<android.util.StatsEvent> list) {
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        android.content.pm.PackageManagerInternal packageManagerInternal = (android.content.pm.PackageManagerInternal) com.android.server.LocalServices.getService(android.content.pm.PackageManagerInternal.class);
        if (!packageManager.hasSystemFeature("android.software.incremental_delivery")) {
            return 0;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                for (int i2 : ((com.android.server.pm.UserManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.UserManagerInternal.class)).getUserIds()) {
                    for (android.content.pm.PackageInfo packageInfo : packageManager.getInstalledPackagesAsUser(0, i2)) {
                        if (android.os.incremental.IncrementalManager.isIncrementalPath(packageInfo.applicationInfo.getBaseCodePath())) {
                            android.content.pm.IncrementalStatesInfo incrementalStatesInfo = packageManagerInternal.getIncrementalStatesInfo(packageInfo.packageName, 1000, i2);
                            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, packageInfo.applicationInfo.uid, incrementalStatesInfo.isLoading(), incrementalStatesInfo.getLoadingCompletedTime()));
                        }
                    }
                }
                return 0;
            } catch (java.lang.Exception e) {
                android.util.Slog.e(TAG, "failed to pullInstalledIncrementalPackagesLocked", e);
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                return 1;
            }
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    private void registerKeystoreStorageStats() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.KEYSTORE2_STORAGE_STATS, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private void registerKeystoreKeyCreationWithGeneralInfo() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.KEYSTORE2_KEY_CREATION_WITH_GENERAL_INFO, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private void registerKeystoreKeyCreationWithAuthInfo() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.KEYSTORE2_KEY_CREATION_WITH_AUTH_INFO, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private void registerKeystoreKeyCreationWithPurposeModesInfo() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.KEYSTORE2_KEY_CREATION_WITH_PURPOSE_AND_MODES_INFO, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private void registerKeystoreAtomWithOverflow() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.KEYSTORE2_ATOM_WITH_OVERFLOW, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private void registerKeystoreKeyOperationWithPurposeAndModesInfo() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.KEYSTORE2_KEY_OPERATION_WITH_PURPOSE_AND_MODES_INFO, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private void registerKeystoreKeyOperationWithGeneralInfo() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.KEYSTORE2_KEY_OPERATION_WITH_GENERAL_INFO, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private void registerRkpErrorStats() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.RKP_ERROR_STATS, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private void registerKeystoreCrashStats() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.KEYSTORE2_CRASH_STATS, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private void registerAccessibilityShortcutStats() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.ACCESSIBILITY_SHORTCUT_STATS, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private void registerAccessibilityFloatingMenuStats() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.ACCESSIBILITY_FLOATING_MENU_STATS, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private void registerMediaCapabilitiesStats() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.MEDIA_CAPABILITIES, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int parseKeystoreStorageStats(android.security.metrics.KeystoreAtom[] keystoreAtomArr, java.util.List<android.util.StatsEvent> list) {
        for (android.security.metrics.KeystoreAtom keystoreAtom : keystoreAtomArr) {
            if (keystoreAtom.payload.getTag() != 0) {
                return 1;
            }
            android.security.metrics.StorageStats storageStats = keystoreAtom.payload.getStorageStats();
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(com.android.internal.util.FrameworkStatsLog.KEYSTORE2_STORAGE_STATS, storageStats.storage_type, storageStats.size, storageStats.unused_size));
        }
        return 0;
    }

    int parseKeystoreKeyCreationWithGeneralInfo(android.security.metrics.KeystoreAtom[] keystoreAtomArr, java.util.List<android.util.StatsEvent> list) {
        for (android.security.metrics.KeystoreAtom keystoreAtom : keystoreAtomArr) {
            if (keystoreAtom.payload.getTag() != 1) {
                return 1;
            }
            android.security.metrics.KeyCreationWithGeneralInfo keyCreationWithGeneralInfo = keystoreAtom.payload.getKeyCreationWithGeneralInfo();
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(com.android.internal.util.FrameworkStatsLog.KEYSTORE2_KEY_CREATION_WITH_GENERAL_INFO, keyCreationWithGeneralInfo.algorithm, keyCreationWithGeneralInfo.key_size, keyCreationWithGeneralInfo.ec_curve, keyCreationWithGeneralInfo.key_origin, keyCreationWithGeneralInfo.error_code, keyCreationWithGeneralInfo.attestation_requested, keystoreAtom.count));
        }
        return 0;
    }

    int parseKeystoreKeyCreationWithAuthInfo(android.security.metrics.KeystoreAtom[] keystoreAtomArr, java.util.List<android.util.StatsEvent> list) {
        for (android.security.metrics.KeystoreAtom keystoreAtom : keystoreAtomArr) {
            if (keystoreAtom.payload.getTag() != 2) {
                return 1;
            }
            android.security.metrics.KeyCreationWithAuthInfo keyCreationWithAuthInfo = keystoreAtom.payload.getKeyCreationWithAuthInfo();
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(com.android.internal.util.FrameworkStatsLog.KEYSTORE2_KEY_CREATION_WITH_AUTH_INFO, keyCreationWithAuthInfo.user_auth_type, keyCreationWithAuthInfo.log10_auth_key_timeout_seconds, keyCreationWithAuthInfo.security_level, keystoreAtom.count));
        }
        return 0;
    }

    int parseKeystoreKeyCreationWithPurposeModesInfo(android.security.metrics.KeystoreAtom[] keystoreAtomArr, java.util.List<android.util.StatsEvent> list) {
        for (android.security.metrics.KeystoreAtom keystoreAtom : keystoreAtomArr) {
            if (keystoreAtom.payload.getTag() != 3) {
                return 1;
            }
            android.security.metrics.KeyCreationWithPurposeAndModesInfo keyCreationWithPurposeAndModesInfo = keystoreAtom.payload.getKeyCreationWithPurposeAndModesInfo();
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(com.android.internal.util.FrameworkStatsLog.KEYSTORE2_KEY_CREATION_WITH_PURPOSE_AND_MODES_INFO, keyCreationWithPurposeAndModesInfo.algorithm, keyCreationWithPurposeAndModesInfo.purpose_bitmap, keyCreationWithPurposeAndModesInfo.padding_mode_bitmap, keyCreationWithPurposeAndModesInfo.digest_bitmap, keyCreationWithPurposeAndModesInfo.block_mode_bitmap, keystoreAtom.count));
        }
        return 0;
    }

    int parseKeystoreAtomWithOverflow(android.security.metrics.KeystoreAtom[] keystoreAtomArr, java.util.List<android.util.StatsEvent> list) {
        for (android.security.metrics.KeystoreAtom keystoreAtom : keystoreAtomArr) {
            if (keystoreAtom.payload.getTag() != 4) {
                return 1;
            }
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(com.android.internal.util.FrameworkStatsLog.KEYSTORE2_ATOM_WITH_OVERFLOW, keystoreAtom.payload.getKeystore2AtomWithOverflow().atom_id, keystoreAtom.count));
        }
        return 0;
    }

    int parseKeystoreKeyOperationWithPurposeModesInfo(android.security.metrics.KeystoreAtom[] keystoreAtomArr, java.util.List<android.util.StatsEvent> list) {
        for (android.security.metrics.KeystoreAtom keystoreAtom : keystoreAtomArr) {
            if (keystoreAtom.payload.getTag() != 5) {
                return 1;
            }
            android.security.metrics.KeyOperationWithPurposeAndModesInfo keyOperationWithPurposeAndModesInfo = keystoreAtom.payload.getKeyOperationWithPurposeAndModesInfo();
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(com.android.internal.util.FrameworkStatsLog.KEYSTORE2_KEY_OPERATION_WITH_PURPOSE_AND_MODES_INFO, keyOperationWithPurposeAndModesInfo.purpose, keyOperationWithPurposeAndModesInfo.padding_mode_bitmap, keyOperationWithPurposeAndModesInfo.digest_bitmap, keyOperationWithPurposeAndModesInfo.block_mode_bitmap, keystoreAtom.count));
        }
        return 0;
    }

    int parseKeystoreKeyOperationWithGeneralInfo(android.security.metrics.KeystoreAtom[] keystoreAtomArr, java.util.List<android.util.StatsEvent> list) {
        for (android.security.metrics.KeystoreAtom keystoreAtom : keystoreAtomArr) {
            if (keystoreAtom.payload.getTag() != 6) {
                return 1;
            }
            android.security.metrics.KeyOperationWithGeneralInfo keyOperationWithGeneralInfo = keystoreAtom.payload.getKeyOperationWithGeneralInfo();
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(com.android.internal.util.FrameworkStatsLog.KEYSTORE2_KEY_OPERATION_WITH_GENERAL_INFO, keyOperationWithGeneralInfo.outcome, keyOperationWithGeneralInfo.error_code, keyOperationWithGeneralInfo.key_upgraded, keyOperationWithGeneralInfo.security_level, keystoreAtom.count));
        }
        return 0;
    }

    int parseRkpErrorStats(android.security.metrics.KeystoreAtom[] keystoreAtomArr, java.util.List<android.util.StatsEvent> list) {
        for (android.security.metrics.KeystoreAtom keystoreAtom : keystoreAtomArr) {
            if (keystoreAtom.payload.getTag() != 7) {
                return 1;
            }
            android.security.metrics.RkpErrorStats rkpErrorStats = keystoreAtom.payload.getRkpErrorStats();
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(com.android.internal.util.FrameworkStatsLog.RKP_ERROR_STATS, rkpErrorStats.rkpError, keystoreAtom.count, rkpErrorStats.security_level));
        }
        return 0;
    }

    int parseKeystoreCrashStats(android.security.metrics.KeystoreAtom[] keystoreAtomArr, java.util.List<android.util.StatsEvent> list) {
        for (android.security.metrics.KeystoreAtom keystoreAtom : keystoreAtomArr) {
            if (keystoreAtom.payload.getTag() != 8) {
                return 1;
            }
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(com.android.internal.util.FrameworkStatsLog.KEYSTORE2_CRASH_STATS, keystoreAtom.payload.getCrashStats().count_of_crash_events));
        }
        return 0;
    }

    int pullKeystoreAtoms(int i, java.util.List<android.util.StatsEvent> list) {
        android.security.metrics.IKeystoreMetrics iKeystoreMetricsService = getIKeystoreMetricsService();
        if (iKeystoreMetricsService == null) {
            android.util.Slog.w(TAG, "Keystore service is null");
            return 1;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.security.metrics.KeystoreAtom[] pullMetrics = iKeystoreMetricsService.pullMetrics(i);
            switch (i) {
                case com.android.internal.util.FrameworkStatsLog.KEYSTORE2_STORAGE_STATS /* 10103 */:
                    return parseKeystoreStorageStats(pullMetrics, list);
                case com.android.internal.util.FrameworkStatsLog.KEYSTORE2_KEY_CREATION_WITH_GENERAL_INFO /* 10118 */:
                    return parseKeystoreKeyCreationWithGeneralInfo(pullMetrics, list);
                case com.android.internal.util.FrameworkStatsLog.KEYSTORE2_KEY_CREATION_WITH_AUTH_INFO /* 10119 */:
                    return parseKeystoreKeyCreationWithAuthInfo(pullMetrics, list);
                case com.android.internal.util.FrameworkStatsLog.KEYSTORE2_KEY_CREATION_WITH_PURPOSE_AND_MODES_INFO /* 10120 */:
                    return parseKeystoreKeyCreationWithPurposeModesInfo(pullMetrics, list);
                case com.android.internal.util.FrameworkStatsLog.KEYSTORE2_ATOM_WITH_OVERFLOW /* 10121 */:
                    return parseKeystoreAtomWithOverflow(pullMetrics, list);
                case com.android.internal.util.FrameworkStatsLog.KEYSTORE2_KEY_OPERATION_WITH_PURPOSE_AND_MODES_INFO /* 10122 */:
                    return parseKeystoreKeyOperationWithPurposeModesInfo(pullMetrics, list);
                case com.android.internal.util.FrameworkStatsLog.KEYSTORE2_KEY_OPERATION_WITH_GENERAL_INFO /* 10123 */:
                    return parseKeystoreKeyOperationWithGeneralInfo(pullMetrics, list);
                case com.android.internal.util.FrameworkStatsLog.RKP_ERROR_STATS /* 10124 */:
                    return parseRkpErrorStats(pullMetrics, list);
                case com.android.internal.util.FrameworkStatsLog.KEYSTORE2_CRASH_STATS /* 10125 */:
                    return parseKeystoreCrashStats(pullMetrics, list);
                default:
                    android.util.Slog.w(TAG, "Unsupported keystore atom: " + i);
                    return 1;
            }
        } catch (android.os.ServiceSpecificException e) {
            android.util.Slog.e(TAG, "pulling keystore metrics failed", e);
            return 1;
        } catch (android.os.RemoteException e2) {
            android.util.Slog.e(TAG, "Disconnected from keystore service. Cannot pull.", e2);
            return 1;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    int pullAccessibilityShortcutStatsLocked(int i, java.util.List<android.util.StatsEvent> list) {
        android.os.UserManager userManager = (android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class);
        if (userManager == null) {
            return 1;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
            java.util.Iterator it = userManager.getUsers().iterator();
            while (it.hasNext()) {
                int identifier = ((android.content.pm.UserInfo) it.next()).getUserHandle().getIdentifier();
                if (isAccessibilityShortcutUser(this.mContext, identifier)) {
                    list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, convertToAccessibilityShortcutType(android.provider.Settings.Secure.getIntForUser(contentResolver, "accessibility_button_mode", 0, identifier)), countAccessibilityServices(android.provider.Settings.Secure.getStringForUser(contentResolver, "accessibility_button_targets", identifier)), 2, countAccessibilityServices(android.provider.Settings.Secure.getStringForUser(contentResolver, "accessibility_shortcut_target_service", identifier)), 3, android.provider.Settings.Secure.getIntForUser(contentResolver, "accessibility_display_magnification_enabled", 0, identifier)));
                }
            }
            return 0;
        } catch (java.lang.RuntimeException e) {
            android.util.Slog.e(TAG, "pulling accessibility shortcuts stats failed at getUsers", e);
            return 1;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    int pullAccessibilityFloatingMenuStatsLocked(int i, java.util.List<android.util.StatsEvent> list) {
        android.os.UserManager userManager = (android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class);
        if (userManager == null) {
            return 1;
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
            java.util.Iterator it = userManager.getUsers().iterator();
            while (true) {
                if (!it.hasNext()) {
                    return 0;
                }
                int identifier = ((android.content.pm.UserInfo) it.next()).getUserHandle().getIdentifier();
                if (isAccessibilityFloatingMenuUser(this.mContext, identifier)) {
                    list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, android.provider.Settings.Secure.getIntForUser(contentResolver, "accessibility_floating_menu_size", 0, identifier), android.provider.Settings.Secure.getIntForUser(contentResolver, "accessibility_floating_menu_icon_type", 0, identifier), android.provider.Settings.Secure.getIntForUser(contentResolver, "accessibility_floating_menu_fade_enabled", 1, identifier) == 1, android.provider.Settings.Secure.getFloatForUser(contentResolver, "accessibility_floating_menu_opacity", 0.55f, identifier)));
                }
            }
        } catch (java.lang.RuntimeException e) {
            android.util.Slog.e(TAG, "pulling accessibility floating menu stats failed at getUsers", e);
            return 1;
        } finally {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    int pullMediaCapabilitiesStats(int i, java.util.List<android.util.StatsEvent> list) {
        android.media.AudioManager audioManager;
        byte[] bArr;
        int i2;
        int i3;
        int i4;
        boolean z;
        int i5;
        if (!this.mContext.getPackageManager().hasSystemFeature("android.software.leanback") || (audioManager = (android.media.AudioManager) this.mContext.getSystemService(android.media.AudioManager.class)) == null) {
            return 1;
        }
        java.util.Map surroundFormats = audioManager.getSurroundFormats();
        byte[] bytes = toBytes(new java.util.ArrayList(surroundFormats.keySet()));
        byte[] bytes2 = toBytes((java.util.List<java.lang.Integer>) audioManager.getReportedSurroundFormats());
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        java.util.Iterator it = surroundFormats.keySet().iterator();
        while (it.hasNext()) {
            int intValue = ((java.lang.Integer) it.next()).intValue();
            if (!audioManager.isSurroundFormatEnabled(intValue)) {
                arrayList.add(java.lang.Integer.valueOf(intValue));
            } else {
                arrayList2.add(java.lang.Integer.valueOf(intValue));
            }
        }
        byte[] bytes3 = toBytes(arrayList);
        byte[] bytes4 = toBytes(arrayList2);
        int encodedSurroundMode = audioManager.getEncodedSurroundMode();
        android.hardware.display.DisplayManager displayManager = (android.hardware.display.DisplayManager) this.mContext.getSystemService(android.hardware.display.DisplayManager.class);
        android.view.Display display = displayManager.getDisplay(0);
        android.view.Display.HdrCapabilities hdrCapabilities = display.getHdrCapabilities();
        byte[] bArr2 = new byte[0];
        if (hdrCapabilities != null) {
            bArr = toBytes(hdrCapabilities.getSupportedHdrTypes());
        } else {
            bArr = bArr2;
        }
        byte[] bytes5 = toBytes(display.getSupportedModes());
        java.util.List<java.util.UUID> supportedCryptoSchemes = android.media.MediaDrm.getSupportedCryptoSchemes();
        try {
            if (supportedCryptoSchemes.isEmpty()) {
                i5 = -1;
            } else {
                i5 = new android.media.MediaDrm(supportedCryptoSchemes.get(0)).getConnectedHdcpLevel();
            }
            i2 = i5;
        } catch (android.media.UnsupportedSchemeException e) {
            android.util.Slog.e(TAG, "pulling hdcp level failed.", e);
            i2 = -1;
        }
        int matchContentFrameRateUserPreference = displayManager.getMatchContentFrameRateUserPreference();
        byte[] bytes6 = toBytes(displayManager.getUserDisabledHdrTypes());
        android.view.Display.Mode globalUserPreferredDisplayMode = displayManager.getGlobalUserPreferredDisplayMode();
        if (globalUserPreferredDisplayMode == null) {
            i3 = -1;
        } else {
            i3 = globalUserPreferredDisplayMode.getPhysicalWidth();
        }
        if (globalUserPreferredDisplayMode == null) {
            i4 = -1;
        } else {
            i4 = globalUserPreferredDisplayMode.getPhysicalHeight();
        }
        float refreshRate = globalUserPreferredDisplayMode != null ? globalUserPreferredDisplayMode.getRefreshRate() : com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        try {
            z = android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "minimal_post_processing_allowed", 1) == 0;
        } catch (android.provider.Settings.SettingNotFoundException e2) {
            android.util.Slog.e(TAG, "unable to find setting for MINIMAL_POST_PROCESSING_ALLOWED.", e2);
            z = false;
        }
        list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, bytes, bytes2, bytes3, bytes4, encodedSurroundMode, bArr, bytes5, i2, matchContentFrameRateUserPreference, bytes6, i3, i4, refreshRate, z));
        return 0;
    }

    private void registerPendingIntentsPerPackagePuller() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.PENDING_INTENTS_PER_PACKAGE, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int pullHdrCapabilities(int i, java.util.List<android.util.StatsEvent> list) {
        boolean z;
        boolean z2;
        android.hardware.display.DisplayManager displayManager = (android.hardware.display.DisplayManager) this.mContext.getSystemService(android.hardware.display.DisplayManager.class);
        android.view.Display display = displayManager.getDisplay(0);
        int conversionMode = displayManager.getHdrConversionMode().getConversionMode();
        int preferredHdrOutputType = displayManager.getHdrConversionMode().getPreferredHdrOutputType();
        if (conversionMode != 1) {
            z = false;
        } else {
            z = true;
        }
        int i2 = preferredHdrOutputType == -1 ? 0 : preferredHdrOutputType;
        boolean hasDolbyVisionIssue = hasDolbyVisionIssue(display);
        byte[] bytes = toBytes(displayManager.getSupportedHdrOutputTypes());
        if (conversionMode == 0) {
            z2 = false;
        } else {
            z2 = true;
        }
        list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, bytes, z, i2, hasDolbyVisionIssue, z2));
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int pullCachedAppsHighWatermark(int i, java.util.List<android.util.StatsEvent> list) {
        list.add((android.util.StatsEvent) ((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).getCachedAppsHighWatermarkStats(i, true));
        return 0;
    }

    private boolean hasDolbyVisionIssue(android.view.Display display) {
        final java.util.concurrent.atomic.AtomicInteger atomicInteger = new java.util.concurrent.atomic.AtomicInteger();
        java.util.Arrays.stream(display.getSupportedModes()).map(new java.util.function.Function() { // from class: com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return ((android.view.Display.Mode) obj).getSupportedHdrTypes();
            }
        }).filter(new java.util.function.Predicate() { // from class: com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$hasDolbyVisionIssue$24;
                lambda$hasDolbyVisionIssue$24 = com.android.server.stats.pull.StatsPullAtomService.lambda$hasDolbyVisionIssue$24((int[]) obj);
                return lambda$hasDolbyVisionIssue$24;
            }
        }).forEach(new java.util.function.Consumer() { // from class: com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                atomicInteger.incrementAndGet();
            }
        });
        if (atomicInteger.get() != 0 && atomicInteger.get() < display.getSupportedModes().length) {
            return true;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$hasDolbyVisionIssue$23(int i) {
        return i == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$hasDolbyVisionIssue$24(int[] iArr) {
        return java.util.Arrays.stream(iArr).anyMatch(new java.util.function.IntPredicate() { // from class: com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda23
            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                boolean lambda$hasDolbyVisionIssue$23;
                lambda$hasDolbyVisionIssue$23 = com.android.server.stats.pull.StatsPullAtomService.lambda$hasDolbyVisionIssue$23(i);
                return lambda$hasDolbyVisionIssue$23;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int pullPendingIntentsPerPackage(int i, java.util.List<android.util.StatsEvent> list) {
        for (android.app.PendingIntentStats pendingIntentStats : ((android.app.ActivityManagerInternal) com.android.server.LocalServices.getService(android.app.ActivityManagerInternal.class)).getPendingIntentStats()) {
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, pendingIntentStats.uid, pendingIntentStats.count, pendingIntentStats.sizeKb));
        }
        return 0;
    }

    private void registerPinnerServiceStats() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.PINNED_FILE_SIZES_PER_PACKAGE, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private void registerHdrCapabilitiesPuller() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.HDR_CAPABILITIES, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    private void registerCachedAppsHighWatermarkPuller() {
        this.mStatsManager.setPullAtomCallback(com.android.internal.util.FrameworkStatsLog.CACHED_APPS_HIGH_WATERMARK, (android.app.StatsManager.PullAtomMetadata) null, com.android.internal.util.ConcurrentUtils.DIRECT_EXECUTOR, this.mStatsCallbackImpl);
    }

    int pullSystemServerPinnerStats(int i, java.util.List<android.util.StatsEvent> list) {
        for (com.android.server.PinnerService.PinnedFileStats pinnedFileStats : ((com.android.server.PinnerService) com.android.server.LocalServices.getService(com.android.server.PinnerService.class)).dumpDataForStatsd()) {
            list.add(com.android.internal.util.FrameworkStatsLog.buildStatsEvent(i, pinnedFileStats.uid, pinnedFileStats.filename, pinnedFileStats.sizeKb));
        }
        return 0;
    }

    private byte[] toBytes(java.util.List<java.lang.Integer> list) {
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
        java.util.Iterator<java.lang.Integer> it = list.iterator();
        while (it.hasNext()) {
            protoOutputStream.write(2259152797697L, it.next().intValue());
        }
        return protoOutputStream.getBytes();
    }

    private byte[] toBytes(int[] iArr) {
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
        for (int i : iArr) {
            protoOutputStream.write(2259152797697L, i);
        }
        return protoOutputStream.getBytes();
    }

    private byte[] toBytes(android.view.Display.Mode[] modeArr) {
        java.util.Map<java.lang.Integer, java.lang.Integer> createModeGroups = createModeGroups(modeArr);
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream();
        for (android.view.Display.Mode mode : modeArr) {
            android.util.proto.ProtoOutputStream protoOutputStream2 = new android.util.proto.ProtoOutputStream();
            protoOutputStream2.write(1120986464257L, mode.getPhysicalHeight());
            protoOutputStream2.write(1120986464258L, mode.getPhysicalWidth());
            protoOutputStream2.write(1108101562371L, mode.getRefreshRate());
            protoOutputStream2.write(1120986464260L, createModeGroups.get(java.lang.Integer.valueOf(mode.getModeId())).intValue());
            protoOutputStream.write(2246267895809L, protoOutputStream2.getBytes());
        }
        return protoOutputStream.getBytes();
    }

    private java.util.Map<java.lang.Integer, java.lang.Integer> createModeGroups(android.view.Display.Mode[] modeArr) {
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        int i = 1;
        for (android.view.Display.Mode mode : modeArr) {
            if (!arrayMap.containsKey(java.lang.Integer.valueOf(mode.getModeId()))) {
                arrayMap.put(java.lang.Integer.valueOf(mode.getModeId()), java.lang.Integer.valueOf(i));
                for (float f : mode.getAlternativeRefreshRates()) {
                    int findModeId = findModeId(modeArr, mode.getPhysicalWidth(), mode.getPhysicalHeight(), f);
                    if (findModeId != -1 && !arrayMap.containsKey(java.lang.Integer.valueOf(findModeId))) {
                        arrayMap.put(java.lang.Integer.valueOf(findModeId), java.lang.Integer.valueOf(i));
                    }
                }
                i++;
            }
        }
        return arrayMap;
    }

    private int findModeId(android.view.Display.Mode[] modeArr, int i, int i2, float f) {
        for (android.view.Display.Mode mode : modeArr) {
            if (mode.matches(i, i2, f)) {
                return mode.getModeId();
            }
        }
        return -1;
    }

    private int countAccessibilityServices(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return 0;
        }
        int count = (int) str.chars().filter(new java.util.function.IntPredicate() { // from class: com.android.server.stats.pull.StatsPullAtomService$$ExternalSyntheticLambda15
            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                boolean lambda$countAccessibilityServices$26;
                lambda$countAccessibilityServices$26 = com.android.server.stats.pull.StatsPullAtomService.lambda$countAccessibilityServices$26(i);
                return lambda$countAccessibilityServices$26;
            }
        }).count();
        if (android.text.TextUtils.isEmpty(str)) {
            return 0;
        }
        return count + 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$countAccessibilityServices$26(int i) {
        return i == 58;
    }

    private boolean isAccessibilityShortcutUser(android.content.Context context, int i) {
        android.content.ContentResolver contentResolver = context.getContentResolver();
        java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(contentResolver, "accessibility_button_targets", i);
        java.lang.String stringForUser2 = android.provider.Settings.Secure.getStringForUser(contentResolver, "accessibility_shortcut_target_service", i);
        return (android.text.TextUtils.isEmpty(stringForUser) ^ true) || ((android.provider.Settings.Secure.getIntForUser(contentResolver, "accessibility_shortcut_dialog_shown", 0, i) == 1) && !android.text.TextUtils.isEmpty(stringForUser2)) || (android.provider.Settings.Secure.getIntForUser(contentResolver, "accessibility_display_magnification_enabled", 0, i) == 1);
    }

    private boolean isAccessibilityFloatingMenuUser(android.content.Context context, int i) {
        android.content.ContentResolver contentResolver = context.getContentResolver();
        return android.provider.Settings.Secure.getIntForUser(contentResolver, "accessibility_button_mode", 0, i) == 1 && !android.text.TextUtils.isEmpty(android.provider.Settings.Secure.getStringForUser(contentResolver, "accessibility_button_targets", i));
    }

    private int convertToAccessibilityShortcutType(int i) {
        switch (i) {
            case 0:
                return 1;
            case 1:
                return 5;
            case 2:
                return 6;
            default:
                return 0;
        }
    }

    private static final class ThermalEventListener extends android.os.IThermalEventListener.Stub {
        private ThermalEventListener() {
        }

        public void notifyThrottling(android.os.Temperature temperature) {
            com.android.internal.util.FrameworkStatsLog.write(189, temperature.getType(), temperature.getName(), (int) (temperature.getValue() * 10.0f), temperature.getStatus());
        }
    }

    private static final class ConnectivityStatsCallback extends android.net.ConnectivityManager.NetworkCallback {
        private ConnectivityStatsCallback() {
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onAvailable(android.net.Network network) {
            com.android.internal.util.FrameworkStatsLog.write(98, network.getNetId(), 1);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(android.net.Network network) {
            com.android.internal.util.FrameworkStatsLog.write(98, network.getNetId(), 2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class StatsSubscriptionsListener extends android.telephony.SubscriptionManager.OnSubscriptionsChangedListener {

        @android.annotation.NonNull
        private final android.telephony.SubscriptionManager mSm;

        StatsSubscriptionsListener(@android.annotation.NonNull android.telephony.SubscriptionManager subscriptionManager) {
            this.mSm = subscriptionManager;
        }

        @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
        public void onSubscriptionsChanged() {
            for (final android.telephony.SubscriptionInfo subscriptionInfo : this.mSm.getCompleteActiveSubscriptionInfoList()) {
                if (((com.android.server.stats.pull.netstats.SubInfo) com.android.internal.util.CollectionUtils.find(com.android.server.stats.pull.StatsPullAtomService.this.mHistoricalSubs, new java.util.function.Predicate() { // from class: com.android.server.stats.pull.StatsPullAtomService$StatsSubscriptionsListener$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(java.lang.Object obj) {
                        boolean lambda$onSubscriptionsChanged$0;
                        lambda$onSubscriptionsChanged$0 = com.android.server.stats.pull.StatsPullAtomService.StatsSubscriptionsListener.lambda$onSubscriptionsChanged$0(subscriptionInfo, (com.android.server.stats.pull.netstats.SubInfo) obj);
                        return lambda$onSubscriptionsChanged$0;
                    }
                })) == null) {
                    int subscriptionId = subscriptionInfo.getSubscriptionId();
                    java.lang.String mccString = subscriptionInfo.getMccString();
                    java.lang.String mncString = subscriptionInfo.getMncString();
                    java.lang.String subscriberId = com.android.server.stats.pull.StatsPullAtomService.this.mTelephony.getSubscriberId(subscriptionId);
                    if (android.text.TextUtils.isEmpty(subscriberId) || android.text.TextUtils.isEmpty(mccString) || android.text.TextUtils.isEmpty(mncString) || subscriptionInfo.getCarrierId() == -1) {
                        android.util.Slog.e(com.android.server.stats.pull.StatsPullAtomService.TAG, "subInfo of subId " + subscriptionId + " is invalid, ignored.");
                    } else {
                        com.android.server.stats.pull.netstats.SubInfo subInfo = new com.android.server.stats.pull.netstats.SubInfo(subscriptionId, subscriptionInfo.getCarrierId(), mccString, mncString, subscriberId, subscriptionInfo.isOpportunistic());
                        android.util.Slog.i(com.android.server.stats.pull.StatsPullAtomService.TAG, "subId " + subscriptionId + " added into historical sub list");
                        synchronized (com.android.server.stats.pull.StatsPullAtomService.this.mDataBytesTransferLock) {
                            com.android.server.stats.pull.StatsPullAtomService.this.mHistoricalSubs.add(subInfo);
                            com.android.server.stats.pull.StatsPullAtomService.this.mNetworkStatsBaselines.addAll(com.android.server.stats.pull.StatsPullAtomService.this.getDataUsageBytesTransferSnapshotForSub(subInfo));
                        }
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean lambda$onSubscriptionsChanged$0(android.telephony.SubscriptionInfo subscriptionInfo, com.android.server.stats.pull.netstats.SubInfo subInfo) {
            return subInfo.subId == subscriptionInfo.getSubscriptionId();
        }
    }
}
