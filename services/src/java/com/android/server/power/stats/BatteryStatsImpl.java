package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class BatteryStatsImpl extends android.os.BatteryStats {
    public static final int BATTERY_PLUGGED_NONE = 0;
    private static final int CELL_SIGNAL_STRENGTH_LEVEL_COUNT;
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_BINDER_STATS = false;
    public static final boolean DEBUG_ENERGY = false;
    private static final boolean DEBUG_ENERGY_CPU = false;
    private static final boolean DEBUG_MEMORY = false;
    static final long DELAY_UPDATE_WAKELOCKS = 60000;
    private static final int GPS_SIGNAL_QUALITY_NONE = 2;
    static final int MAX_DAILY_ITEMS = 10;
    static final int MAX_LEVEL_STEPS = 200;
    private static final int MAX_WAKELOCKS_PER_UID;
    private static final double MILLISECONDS_IN_HOUR = 3600000.0d;
    private static final long MILLISECONDS_IN_YEAR = 31536000000L;

    @com.android.internal.annotations.VisibleForTesting
    protected static final long MOBILE_RADIO_POWER_STATE_UPDATE_FREQ_MS = 600000;
    private static final int MODEM_TX_POWER_LEVEL_COUNT;
    static final int MSG_REPORT_CHARGING = 3;
    static final int MSG_REPORT_CPU_UPDATE_NEEDED = 1;
    static final int MSG_REPORT_POWER_CHANGE = 2;
    static final int MSG_REPORT_RESET_STATS = 4;
    private static final int NR_FREQUENCY_COUNT = 5;
    private static final int NUM_BT_TX_LEVELS = 1;
    private static final int NUM_WIFI_TX_LEVELS = 1;
    public static final int PER_UID_MODEM_POWER_MODEL_MOBILE_RADIO_ACTIVE_TIME = 1;
    public static final int PER_UID_MODEM_POWER_MODEL_MODEM_ACTIVITY_INFO_RX_TX = 2;
    private static final int PROC_STATE_TIME_COUNTER_STATE_COUNT = 8;
    public static final int RESET_REASON_ADB_COMMAND = 2;
    public static final int RESET_REASON_CORRUPT_FILE = 1;
    public static final int RESET_REASON_ENERGY_CONSUMER_BUCKETS_CHANGE = 4;
    public static final int RESET_REASON_FULL_CHARGE = 3;
    public static final int RESET_REASON_PLUGGED_IN_FOR_LONG_DURATION = 5;
    private static final long RPM_STATS_UPDATE_FREQ_MS = 1000;
    private static final int[] SUPPORTED_PER_PROCESS_STATE_STANDARD_ENERGY_BUCKETS;
    private static final java.lang.String TAG = "BatteryStatsImpl";
    private static final int USB_DATA_CONNECTED = 2;
    private static final int USB_DATA_DISCONNECTED = 1;
    private static final int USB_DATA_UNKNOWN = 0;
    public static final int VERSION;

    @com.android.internal.annotations.VisibleForTesting
    public static final int WAKE_LOCK_WEIGHT = 50;
    private static final android.os.BatteryStats.LongCounter ZERO_LONG_COUNTER;
    private static final android.os.BatteryStats.LongCounter[] ZERO_LONG_COUNTER_ARRAY;
    private final android.os.BatteryStats.HistoryEventTracker mActiveEvents;
    int mActiveRat;

    @com.android.internal.annotations.GuardedBy({"this"})
    private android.app.AlarmManager mAlarmManager;
    int mAudioOnNesting;
    com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mAudioOnTimer;
    private final java.util.ArrayList<com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer> mAudioTurnedOnTimers;
    private int mBatteryChargeUah;
    private int mBatteryHealth;
    private int mBatteryLevel;
    private int mBatteryPlugType;
    private boolean mBatteryPluggedIn;
    private long mBatteryPluggedInRealTimeMs;

    @com.android.internal.annotations.GuardedBy({"this"})
    @com.android.internal.annotations.VisibleForTesting
    protected com.android.server.power.stats.BatteryStatsImpl.BatteryStatsConfig mBatteryStatsConfig;
    private int mBatteryStatus;
    private int mBatteryTemperature;
    private long mBatteryTimeToFullSeconds;
    private com.android.server.power.stats.BatteryUsageStatsProvider mBatteryUsageStatsProvider;
    private int mBatteryVoltageMv;
    private com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounterArray mBinderThreadCpuTimesUs;
    com.android.server.power.stats.BatteryStatsImpl.ControllerActivityCounterImpl mBluetoothActivity;

    @android.annotation.Nullable
    com.android.server.power.stats.BluetoothPowerCalculator mBluetoothPowerCalculator;
    int mBluetoothScanNesting;
    private final java.util.ArrayList<com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer> mBluetoothScanOnTimers;
    com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mBluetoothScanTimer;
    private com.android.server.power.stats.BatteryStatsImpl.BatteryCallback mCallback;
    int mCameraOnNesting;
    com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mCameraOnTimer;
    private final java.util.ArrayList<com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer> mCameraTurnedOnTimers;
    final android.os.BatteryStats.LevelStepTracker mChargeStepTracker;
    boolean mCharging;
    public final android.util.AtomicFile mCheckinFile;
    protected com.android.internal.os.Clock mClock;

    @com.android.internal.annotations.GuardedBy({"this"})
    @com.android.internal.annotations.VisibleForTesting
    protected final com.android.server.power.stats.BatteryStatsImpl.Constants mConstants;
    private int[] mCpuPowerBracketMap;

    @android.annotation.Nullable
    com.android.server.power.stats.CpuPowerCalculator mCpuPowerCalculator;
    private final com.android.server.power.stats.CpuPowerStatsCollector mCpuPowerStatsCollector;

    @com.android.internal.annotations.VisibleForTesting
    protected com.android.internal.os.CpuScalingPolicies mCpuScalingPolicies;

    @com.android.internal.annotations.GuardedBy({"this"})
    private long mCpuTimeReadsTrackingStartTimeMs;

    @com.android.internal.annotations.VisibleForTesting
    protected com.android.internal.os.KernelCpuUidTimeReader.KernelCpuUidActiveTimeReader mCpuUidActiveTimeReader;

    @com.android.internal.annotations.VisibleForTesting
    protected com.android.internal.os.KernelCpuUidTimeReader.KernelCpuUidClusterTimeReader mCpuUidClusterTimeReader;

    @com.android.internal.annotations.VisibleForTesting
    protected com.android.internal.os.KernelCpuUidTimeReader.KernelCpuUidFreqTimeReader mCpuUidFreqTimeReader;

    @com.android.internal.annotations.VisibleForTesting
    protected com.android.internal.os.KernelCpuUidTimeReader.KernelCpuUidUserSysTimeReader mCpuUidUserSysTimeReader;
    int mCurStepMode;
    final android.os.BatteryStats.LevelStepTracker mDailyChargeStepTracker;
    final android.os.BatteryStats.LevelStepTracker mDailyDischargeStepTracker;
    public final android.util.AtomicFile mDailyFile;
    final java.util.ArrayList<android.os.BatteryStats.DailyItem> mDailyItems;
    java.util.ArrayList<android.os.BatteryStats.PackageChange> mDailyPackageChanges;
    long mDailyStartTimeMs;
    private final java.lang.Runnable mDeferSetCharging;
    int mDeviceIdleMode;
    com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mDeviceIdleModeFullTimer;
    com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mDeviceIdleModeLightTimer;
    boolean mDeviceIdling;
    com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mDeviceIdlingTimer;
    boolean mDeviceLightIdling;
    com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mDeviceLightIdlingTimer;
    int mDischargeAmountScreenDoze;
    int mDischargeAmountScreenDozeSinceCharge;
    int mDischargeAmountScreenOff;
    int mDischargeAmountScreenOffSinceCharge;
    int mDischargeAmountScreenOn;
    int mDischargeAmountScreenOnSinceCharge;
    private com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter mDischargeCounter;
    int mDischargeCurrentLevel;
    private com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter mDischargeDeepDozeCounter;
    private com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter mDischargeLightDozeCounter;
    int mDischargePlugLevel;
    private com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter mDischargeScreenDozeCounter;
    int mDischargeScreenDozeUnplugLevel;
    private com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter mDischargeScreenOffCounter;
    int mDischargeScreenOffUnplugLevel;
    int mDischargeScreenOnUnplugLevel;
    final android.os.BatteryStats.LevelStepTracker mDischargeStepTracker;
    int mDischargeUnplugLevel;
    private int mDisplayMismatchWtfCount;
    private final java.util.ArrayList<com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer> mDrawTimers;
    java.lang.String mEndPlatformVersion;
    public final com.android.server.power.stats.BatteryStatsImpl.EnergyStatsRetriever mEnergyConsumerRetriever;

    @com.android.internal.annotations.GuardedBy({"this"})
    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    protected com.android.internal.power.EnergyConsumerStats.Config mEnergyConsumerStatsConfig;
    private int mEstimatedBatteryCapacityMah;
    private com.android.server.power.stats.BatteryStatsImpl.ExternalStatsSync mExternalSync;
    int mFlashlightOnNesting;
    com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mFlashlightOnTimer;
    private final java.util.ArrayList<com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer> mFlashlightTurnedOnTimers;
    private final com.android.server.power.stats.BatteryStatsImpl.FrameworkStatsLogger mFrameworkStatsLogger;
    private final java.util.ArrayList<com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer> mFullTimers;
    private final java.util.ArrayList<com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer> mFullWifiLockTimers;

    @com.android.internal.annotations.GuardedBy({"this"})
    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    protected com.android.internal.power.EnergyConsumerStats mGlobalEnergyConsumerStats;
    boolean mGlobalWifiRunning;
    com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mGlobalWifiRunningTimer;
    int mGpsNesting;
    int mGpsSignalQualityBin;
    final com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer[] mGpsSignalQualityTimer;
    public android.os.Handler mHandler;
    boolean mHasBluetoothReporting;
    boolean mHasModemReporting;
    boolean mHasWifiReporting;
    private boolean mHaveBatteryLevel;
    int mHighDischargeAmountSinceCharge;

    @android.annotation.NonNull
    private final com.android.internal.os.BatteryStatsHistory mHistory;

    @com.android.internal.annotations.GuardedBy({"this"})
    boolean mIgnoreNextExternalStats;
    int mInitStepMode;
    boolean mInteractive;
    com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mInteractiveTimer;

    @com.android.internal.annotations.VisibleForTesting
    protected com.android.internal.os.KernelCpuSpeedReader[] mKernelCpuSpeedReaders;
    private com.android.internal.os.KernelMemoryBandwidthStats mKernelMemoryBandwidthStats;
    private final android.util.LongSparseArray<com.android.server.power.stats.BatteryStatsImpl.SamplingTimer> mKernelMemoryStats;

    @com.android.internal.annotations.VisibleForTesting
    protected com.android.internal.os.KernelSingleUidTimeReader mKernelSingleUidTimeReader;

    @com.android.internal.annotations.VisibleForTesting
    protected com.android.server.power.stats.KernelWakelockReader mKernelWakelockReader;
    private final java.util.HashMap<java.lang.String, com.android.server.power.stats.BatteryStatsImpl.SamplingTimer> mKernelWakelockStats;
    private final com.android.server.power.stats.BatteryStatsImpl.BluetoothActivityInfoCache mLastBluetoothActivityInfo;
    int mLastChargeStepLevel;
    int mLastDischargeStepLevel;
    long mLastIdleTimeStartMs;
    private int mLastLearnedBatteryCapacityUah;
    private android.telephony.ModemActivityInfo mLastModemActivityInfo;

    @com.android.internal.annotations.GuardedBy({"mModemNetworkLock"})
    private android.net.NetworkStats mLastModemNetworkStats;

    @com.android.internal.annotations.VisibleForTesting
    protected java.util.ArrayList<com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer> mLastPartialTimers;
    private long mLastRpmStatsUpdateTimeMs;
    long mLastWakeupElapsedTimeMs;
    java.lang.String mLastWakeupReason;
    long mLastWakeupUptimeMs;

    @com.android.internal.annotations.GuardedBy({"mWifiNetworkLock"})
    private android.net.NetworkStats mLastWifiNetworkStats;
    long mLastWriteTimeMs;
    private final android.app.AlarmManager.OnAlarmListener mLongPlugInAlarmHandler;
    long mLongestFullIdleTimeMs;
    long mLongestLightIdleTimeMs;
    int mLowDischargeAmountSinceCharge;
    int mMaxChargeStepLevel;
    private int mMaxLearnedBatteryCapacityUah;
    int mMinDischargeStepLevel;
    private int mMinLearnedBatteryCapacityUah;
    com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter mMobileRadioActiveAdjustedTime;
    com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mMobileRadioActivePerAppTimer;
    long mMobileRadioActiveStartTimeMs;
    com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mMobileRadioActiveTimer;
    com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter mMobileRadioActiveUnknownCount;
    com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter mMobileRadioActiveUnknownTime;

    @android.annotation.Nullable
    com.android.server.power.stats.MobileRadioPowerCalculator mMobileRadioPowerCalculator;
    int mMobileRadioPowerState;
    int mModStepMode;
    com.android.server.power.stats.BatteryStatsImpl.ControllerActivityCounterImpl mModemActivity;

    @com.android.internal.annotations.GuardedBy({"mModemNetworkLock"})
    private java.lang.String[] mModemIfaces;
    private final java.lang.Object mModemNetworkLock;

    @android.annotation.NonNull
    private final com.android.internal.os.MonotonicClock mMonotonicClock;
    long mMonotonicEndTime;
    long mMonotonicStartTime;
    final com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter[] mNetworkByteActivityCounters;
    final com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter[] mNetworkPacketActivityCounters;
    long mNextMaxDailyDeadlineMs;
    long mNextMinDailyDeadlineMs;
    boolean mNoAutoReset;
    com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mNrNsaTimer;
    int mNrState;

    @com.android.internal.annotations.GuardedBy({"this"})
    private int mNumAllUidCpuTimeReads;
    private int mNumConnectivityChange;

    @com.android.internal.annotations.GuardedBy({"this"})
    private long mNumSingleUidCpuTimeReads;

    @com.android.internal.annotations.GuardedBy({"this"})
    private int mNumUidsRemoved;
    boolean mOnBattery;

    @com.android.internal.annotations.VisibleForTesting
    protected boolean mOnBatteryInternal;
    protected final com.android.server.power.stats.BatteryStatsImpl.TimeBase mOnBatteryScreenOffTimeBase;
    protected final com.android.server.power.stats.BatteryStatsImpl.TimeBase mOnBatteryTimeBase;

    @com.android.internal.annotations.VisibleForTesting
    protected java.util.ArrayList<com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer> mPartialTimers;

    @com.android.internal.annotations.GuardedBy({"this"})
    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    protected java.util.Queue<com.android.server.power.stats.BatteryStatsImpl.UidToRemove> mPendingRemovedUids;
    com.android.server.power.stats.BatteryStatsImpl.DisplayBatteryStats[] mPerDisplayBatteryStats;

    @com.android.internal.annotations.GuardedBy({"this"})
    public boolean mPerProcStateCpuTimesAvailable;
    com.android.server.power.stats.BatteryStatsImpl.RadioAccessTechnologyBatteryStats[] mPerRatBatteryStats;
    int mPhoneDataConnectionType;
    final com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer[] mPhoneDataConnectionsTimer;
    boolean mPhoneOn;
    com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mPhoneOnTimer;
    private int mPhoneServiceState;
    private int mPhoneServiceStateRaw;
    com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mPhoneSignalScanningTimer;
    int mPhoneSignalStrengthBin;
    int mPhoneSignalStrengthBinRaw;
    final com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer[] mPhoneSignalStrengthsTimer;
    private int mPhoneSimStateRaw;
    private final com.android.server.power.stats.BatteryStatsImpl.PlatformIdleStateCallback mPlatformIdleStateCallback;

    @com.android.internal.annotations.VisibleForTesting
    protected com.android.internal.os.PowerProfile mPowerProfile;
    boolean mPowerSaveModeEnabled;
    com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mPowerSaveModeEnabledTimer;
    private boolean mPowerStatsCollectorEnabled;
    private final com.android.internal.os.PowerStats.DescriptorRegistry mPowerStatsDescriptorRegistry;
    private com.android.server.power.stats.PowerStatsStore mPowerStatsStore;

    @com.android.internal.annotations.VisibleForTesting
    protected final com.android.server.power.stats.PowerStatsUidResolver mPowerStatsUidResolver;
    boolean mPretendScreenOff;
    long mRealtimeStartUs;
    long mRealtimeUs;
    public boolean mRecordAllHistory;
    private final java.util.HashMap<java.lang.String, com.android.server.power.stats.BatteryStatsImpl.SamplingTimer> mRpmStats;
    private boolean mSaveBatteryUsageStatsOnReset;
    int mScreenBrightnessBin;
    final com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer[] mScreenBrightnessTimer;
    com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mScreenDozeTimer;
    private final java.util.HashMap<java.lang.String, com.android.server.power.stats.BatteryStatsImpl.SamplingTimer> mScreenOffRpmStats;
    com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mScreenOnTimer;

    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    protected int mScreenState;
    int mSensorNesting;
    private final android.util.SparseArray<java.util.ArrayList<com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer>> mSensorTimers;
    private boolean mShuttingDown;
    long mStartClockTimeMs;
    int mStartCount;
    java.lang.String mStartPlatformVersion;
    private final android.util.AtomicFile mStatsFile;
    private final com.android.server.power.stats.BatteryStatsImpl.HistoryStepDetailsCalculatorImpl mStepDetailsCalculator;
    private boolean mSystemReady;

    @com.android.internal.annotations.VisibleForTesting
    protected com.android.server.power.stats.SystemServerCpuThreadReader mSystemServerCpuThreadReader;
    long mTempTotalCpuSystemTimeUs;
    long mTempTotalCpuUserTimeUs;
    private com.android.internal.os.LongArrayMultiStateCounter.LongArrayContainer mTmpCpuTimeInFreq;
    private com.android.internal.os.RailStats mTmpRailStats;
    private com.android.internal.os.RpmStats mTmpRpmStats;
    private final com.android.server.power.stats.KernelWakelockStats mTmpWakelockStats;
    private final android.util.SparseArray<com.android.server.power.stats.BatteryStatsImpl.Uid> mUidStats;
    long mUptimeStartUs;
    long mUptimeUs;
    int mUsbDataState;

    @com.android.internal.annotations.VisibleForTesting
    protected com.android.server.power.stats.BatteryStatsImpl.UserInfoProvider mUserInfoProvider;
    int mVideoOnNesting;
    com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mVideoOnTimer;
    private final java.util.ArrayList<com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer> mVideoTurnedOnTimers;
    long[][] mWakeLockAllocationsUs;
    boolean mWakeLockImportant;
    int mWakeLockNesting;
    private final java.util.HashMap<java.lang.String, com.android.server.power.stats.BatteryStatsImpl.SamplingTimer> mWakeupReasonStats;
    com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mWifiActiveTimer;
    com.android.server.power.stats.BatteryStatsImpl.ControllerActivityCounterImpl mWifiActivity;
    private final android.util.SparseArray<java.util.ArrayList<com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer>> mWifiBatchedScanTimers;
    private int mWifiFullLockNesting;

    @com.android.internal.annotations.GuardedBy({"mWifiNetworkLock"})
    private java.lang.String[] mWifiIfaces;
    private int mWifiMulticastNesting;
    private final java.util.ArrayList<com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer> mWifiMulticastTimers;
    com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mWifiMulticastWakelockTimer;
    private final java.lang.Object mWifiNetworkLock;
    boolean mWifiOn;
    com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mWifiOnTimer;

    @android.annotation.Nullable
    com.android.server.power.stats.WifiPowerCalculator mWifiPowerCalculator;
    int mWifiRadioPowerState;
    private final java.util.ArrayList<com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer> mWifiRunningTimers;
    int mWifiScanNesting;
    private final java.util.ArrayList<com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer> mWifiScanTimers;
    int mWifiSignalStrengthBin;
    final com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer[] mWifiSignalStrengthsTimer;
    int mWifiState;
    final com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer[] mWifiStateTimer;
    int mWifiSupplState;
    final com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer[] mWifiSupplStateTimer;
    private final java.util.ArrayList<com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer> mWindowTimers;
    private final java.lang.Runnable mWriteAsyncRunnable;
    private final java.util.concurrent.locks.ReentrantLock mWriteLock;

    public interface BatteryCallback {
        void batteryNeedsCpuUpdate();

        void batteryPowerChanged(boolean z);

        void batterySendBroadcast(android.content.Intent intent);

        void batteryStatsReset();
    }

    public interface EnergyStatsRetriever {
        void fillRailDataStats(com.android.internal.os.RailStats railStats);
    }

    public interface ExternalStatsSync {
        public static final int RESET = 128;
        public static final int UPDATE_ALL = 127;
        public static final int UPDATE_BT = 8;
        public static final int UPDATE_CAMERA = 64;
        public static final int UPDATE_CPU = 1;
        public static final int UPDATE_DISPLAY = 32;
        public static final int UPDATE_ON_PROC_STATE_CHANGE = 14;
        public static final int UPDATE_ON_RESET = 255;
        public static final int UPDATE_RADIO = 4;
        public static final int UPDATE_RPM = 16;
        public static final int UPDATE_WIFI = 2;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface ExternalUpdateFlag {
        }

        void cancelCpuSyncDueToWakelockChange();

        java.util.concurrent.Future<?> scheduleCleanupDueToRemovedUser(int i);

        java.util.concurrent.Future<?> scheduleCpuSyncDueToRemovedUid(int i);

        java.util.concurrent.Future<?> scheduleCpuSyncDueToWakelockChange(long j);

        java.util.concurrent.Future<?> scheduleSync(java.lang.String str, int i);

        java.util.concurrent.Future<?> scheduleSyncDueToBatteryLevelChange(long j);

        void scheduleSyncDueToProcessStateChange(int i, long j);

        java.util.concurrent.Future<?> scheduleSyncDueToScreenStateChange(int i, boolean z, boolean z2, int i2, int[] iArr);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PerUidModemPowerModel {
    }

    public interface PlatformIdleStateCallback {
        void fillLowPowerStats(com.android.internal.os.RpmStats rpmStats);

        java.lang.String getSubsystemLowPowerStats();
    }

    static {
        com.android.server.power.optimization.Flags.disableSystemServicePowerAttr();
        VERSION = com.android.internal.util.FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__PLATFORM_ROLE_HOLDER_UPDATE_FINISHED;
        MAX_WAKELOCKS_PER_UID = android.os.BatteryStats.isLowRamDevice() ? 40 : 200;
        CELL_SIGNAL_STRENGTH_LEVEL_COUNT = android.os.BatteryStats.getCellSignalStrengthLevelCount();
        MODEM_TX_POWER_LEVEL_COUNT = android.os.BatteryStats.getModemTxPowerLevelCount();
        ZERO_LONG_COUNTER = new android.os.BatteryStats.LongCounter() { // from class: com.android.server.power.stats.BatteryStatsImpl.1
            public long getCountLocked(int i) {
                return 0L;
            }

            public long getCountForProcessState(int i) {
                return 0L;
            }

            public void logState(android.util.Printer printer, java.lang.String str) {
                printer.println(str + "mCount=0");
            }
        };
        ZERO_LONG_COUNTER_ARRAY = new android.os.BatteryStats.LongCounter[]{ZERO_LONG_COUNTER};
        SUPPORTED_PER_PROCESS_STATE_STANDARD_ENERGY_BUCKETS = new int[]{3, 7, 4, 5};
    }

    public android.util.LongSparseArray<com.android.server.power.stats.BatteryStatsImpl.SamplingTimer> getKernelMemoryStats() {
        return this.mKernelMemoryStats;
    }

    @android.annotation.NonNull
    public com.android.internal.os.BatteryStatsHistory getHistory() {
        return this.mHistory;
    }

    @android.annotation.NonNull
    com.android.internal.os.BatteryStatsHistory copyHistory() {
        return this.mHistory.copy();
    }

    @com.android.internal.annotations.VisibleForTesting
    public final class UidToRemove {
        private final int mEndUid;
        private final int mStartUid;
        private final long mUidRemovalTimestamp;

        public UidToRemove(com.android.server.power.stats.BatteryStatsImpl batteryStatsImpl, int i, long j) {
            this(i, i, j);
        }

        public UidToRemove(int i, int i2, long j) {
            this.mStartUid = i;
            this.mEndUid = i2;
            this.mUidRemovalTimestamp = j;
        }

        public long getUidRemovalTimestamp() {
            return this.mUidRemovalTimestamp;
        }

        @com.android.internal.annotations.GuardedBy({"BatteryStatsImpl.this"})
        void removeLocked() {
            com.android.server.power.stats.BatteryStatsImpl.this.removeCpuStatsForUidRangeLocked(this.mStartUid, this.mEndUid);
        }
    }

    public static abstract class UserInfoProvider {
        private int[] userIds;

        @android.annotation.Nullable
        protected abstract int[] getUserIds();

        @com.android.internal.annotations.VisibleForTesting
        public final void refreshUserIds() {
            this.userIds = getUserIds();
        }

        @com.android.internal.annotations.VisibleForTesting
        public boolean exists(int i) {
            if (this.userIds != null) {
                return com.android.internal.util.ArrayUtils.contains(this.userIds, i);
            }
            return true;
        }
    }

    public static class BatteryStatsConfig {
        static final int RESET_ON_UNPLUG_AFTER_SIGNIFICANT_CHARGE_FLAG = 2;
        static final int RESET_ON_UNPLUG_HIGH_BATTERY_LEVEL_FLAG = 1;
        private final int mFlags;
        private final long mPowerStatsThrottlePeriodCpu;

        private BatteryStatsConfig(com.android.server.power.stats.BatteryStatsImpl.BatteryStatsConfig.Builder builder) {
            int i;
            if (!builder.mResetOnUnplugHighBatteryLevel) {
                i = 0;
            } else {
                i = 1;
            }
            this.mFlags = builder.mResetOnUnplugAfterSignificantCharge ? i | 2 : i;
            this.mPowerStatsThrottlePeriodCpu = builder.mPowerStatsThrottlePeriodCpu;
        }

        boolean shouldResetOnUnplugHighBatteryLevel() {
            return (this.mFlags & 1) == 1;
        }

        boolean shouldResetOnUnplugAfterSignificantCharge() {
            return (this.mFlags & 2) == 2;
        }

        long getPowerStatsThrottlePeriodCpu() {
            return this.mPowerStatsThrottlePeriodCpu;
        }

        public static class Builder {
            private boolean mResetOnUnplugHighBatteryLevel = true;
            private boolean mResetOnUnplugAfterSignificantCharge = true;
            private long mPowerStatsThrottlePeriodCpu = 60000;

            public com.android.server.power.stats.BatteryStatsImpl.BatteryStatsConfig build() {
                return new com.android.server.power.stats.BatteryStatsImpl.BatteryStatsConfig(this);
            }

            public com.android.server.power.stats.BatteryStatsImpl.BatteryStatsConfig.Builder setResetOnUnplugHighBatteryLevel(boolean z) {
                this.mResetOnUnplugHighBatteryLevel = z;
                return this;
            }

            public com.android.server.power.stats.BatteryStatsImpl.BatteryStatsConfig.Builder setResetOnUnplugAfterSignificantCharge(boolean z) {
                this.mResetOnUnplugAfterSignificantCharge = z;
                return this;
            }

            public com.android.server.power.stats.BatteryStatsImpl.BatteryStatsConfig.Builder setPowerStatsThrottlePeriodCpu(long j) {
                this.mPowerStatsThrottlePeriodCpu = j;
                return this;
            }
        }
    }

    final class MyHandler extends android.os.Handler {
        public MyHandler(android.os.Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            java.lang.String str;
            com.android.server.power.stats.BatteryStatsImpl.BatteryCallback batteryCallback = com.android.server.power.stats.BatteryStatsImpl.this.mCallback;
            switch (message.what) {
                case 1:
                    if (batteryCallback != null) {
                        batteryCallback.batteryNeedsCpuUpdate();
                        return;
                    }
                    return;
                case 2:
                    if (batteryCallback != null) {
                        batteryCallback.batteryPowerChanged(message.arg1 != 0);
                        return;
                    }
                    return;
                case 3:
                    if (batteryCallback != null) {
                        synchronized (com.android.server.power.stats.BatteryStatsImpl.this) {
                            try {
                                str = com.android.server.power.stats.BatteryStatsImpl.this.mCharging ? "android.os.action.CHARGING" : "android.os.action.DISCHARGING";
                            } catch (java.lang.Throwable th) {
                                throw th;
                            }
                        }
                        android.content.Intent intent = new android.content.Intent(str);
                        intent.addFlags(67108864);
                        batteryCallback.batterySendBroadcast(intent);
                        return;
                    }
                    return;
                case 4:
                    if (batteryCallback != null) {
                        batteryCallback.batteryStatsReset();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public void postBatteryNeedsCpuUpdateMsg() {
        this.mHandler.sendEmptyMessage(1);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    @com.android.internal.annotations.VisibleForTesting
    public void updateProcStateCpuTimesLocked(int i, long j, long j2) {
        if (this.mPowerStatsCollectorEnabled) {
            return;
        }
        ensureKernelSingleUidTimeReaderLocked();
        com.android.server.power.stats.BatteryStatsImpl.Uid uidStatsLocked = getUidStatsLocked(i);
        this.mNumSingleUidCpuTimeReads++;
        com.android.internal.os.LongArrayMultiStateCounter counter = uidStatsLocked.getProcStateTimeCounter(j).getCounter();
        com.android.internal.os.LongArrayMultiStateCounter counter2 = uidStatsLocked.getProcStateScreenOffTimeCounter(j).getCounter();
        this.mKernelSingleUidTimeReader.addDelta(i, counter, j);
        this.mKernelSingleUidTimeReader.addDelta(i, counter2, j);
        if (uidStatsLocked.mChildUids != null) {
            com.android.internal.os.LongArrayMultiStateCounter.LongArrayContainer cpuTimeInFreqContainer = getCpuTimeInFreqContainer();
            for (int size = uidStatsLocked.mChildUids.size() - 1; size >= 0; size--) {
                com.android.internal.os.LongArrayMultiStateCounter longArrayMultiStateCounter = uidStatsLocked.mChildUids.valueAt(size).cpuTimeInFreqCounter;
                if (longArrayMultiStateCounter != null) {
                    this.mKernelSingleUidTimeReader.addDelta(uidStatsLocked.mChildUids.keyAt(size), longArrayMultiStateCounter, j, cpuTimeInFreqContainer);
                    counter.addCounts(cpuTimeInFreqContainer);
                    counter2.addCounts(cpuTimeInFreqContainer);
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void clearPendingRemovedUidsLocked() {
        long elapsedRealtime = this.mClock.elapsedRealtime() - this.mConstants.UID_REMOVE_DELAY_MS;
        while (!this.mPendingRemovedUids.isEmpty() && this.mPendingRemovedUids.peek().getUidRemovalTimestamp() < elapsedRealtime) {
            this.mPendingRemovedUids.poll().removeLocked();
        }
    }

    public void updateCpuTimesForAllUids() {
        com.android.internal.os.LongArrayMultiStateCounter longArrayMultiStateCounter;
        if (this.mPowerStatsCollectorEnabled && this.mCpuPowerStatsCollector != null) {
            this.mCpuPowerStatsCollector.schedule();
            return;
        }
        synchronized (this) {
            try {
                if (trackPerProcStateCpuTimes()) {
                    ensureKernelSingleUidTimeReaderLocked();
                    android.util.SparseArray allUidCpuFreqTimeMs = this.mCpuUidFreqTimeReader.getAllUidCpuFreqTimeMs();
                    for (int size = allUidCpuFreqTimeMs.size() - 1; size >= 0; size--) {
                        int keyAt = allUidCpuFreqTimeMs.keyAt(size);
                        int mapUid = mapUid(keyAt);
                        com.android.server.power.stats.BatteryStatsImpl.Uid availableUidStatsLocked = getAvailableUidStatsLocked(mapUid);
                        if (availableUidStatsLocked != null && availableUidStatsLocked.mProcessState != 7) {
                            long elapsedRealtime = this.mClock.elapsedRealtime();
                            this.mClock.uptimeMillis();
                            com.android.internal.os.LongArrayMultiStateCounter counter = availableUidStatsLocked.getProcStateTimeCounter(elapsedRealtime).getCounter();
                            com.android.internal.os.LongArrayMultiStateCounter counter2 = availableUidStatsLocked.getProcStateScreenOffTimeCounter(elapsedRealtime).getCounter();
                            if (keyAt == mapUid || android.os.Process.isSdkSandboxUid(keyAt)) {
                                this.mKernelSingleUidTimeReader.addDelta(mapUid, counter, elapsedRealtime);
                                this.mKernelSingleUidTimeReader.addDelta(mapUid, counter2, elapsedRealtime);
                            } else {
                                com.android.server.power.stats.BatteryStatsImpl.Uid.ChildUid childUid = availableUidStatsLocked.getChildUid(keyAt);
                                if (childUid != null && (longArrayMultiStateCounter = childUid.cpuTimeInFreqCounter) != null) {
                                    com.android.internal.os.LongArrayMultiStateCounter.LongArrayContainer cpuTimeInFreqContainer = getCpuTimeInFreqContainer();
                                    this.mKernelSingleUidTimeReader.addDelta(keyAt, longArrayMultiStateCounter, elapsedRealtime, cpuTimeInFreqContainer);
                                    counter.addCounts(cpuTimeInFreqContainer);
                                    counter2.addCounts(cpuTimeInFreqContainer);
                                }
                            }
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void ensureKernelSingleUidTimeReaderLocked() {
        if (this.mPowerStatsCollectorEnabled || this.mKernelSingleUidTimeReader != null) {
            return;
        }
        this.mKernelSingleUidTimeReader = new com.android.internal.os.KernelSingleUidTimeReader(this.mCpuScalingPolicies.getScalingStepCount());
        this.mPerProcStateCpuTimesAvailable = this.mCpuUidFreqTimeReader.perClusterTimesAvailable() && this.mKernelSingleUidTimeReader.singleUidCpuTimesAvailable();
    }

    private static class DisplayBatteryStats {
        public com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer screenDozeTimer;
        public com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer screenOnTimer;
        public int screenState = 0;
        public int screenBrightnessBin = -1;
        public com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer[] screenBrightnessTimers = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer[5];
        public int screenStateAtLastEnergyMeasurement = 0;

        DisplayBatteryStats(com.android.internal.os.Clock clock, com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase) {
            this.screenOnTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(clock, null, -1, null, timeBase);
            this.screenDozeTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(clock, null, -1, null, timeBase);
            for (int i = 0; i < 5; i++) {
                this.screenBrightnessTimers[i] = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(clock, null, (-100) - i, null, timeBase);
            }
        }

        public void reset(long j) {
            this.screenOnTimer.reset(false, j);
            this.screenDozeTimer.reset(false, j);
            for (int i = 0; i < 5; i++) {
                this.screenBrightnessTimers[i].reset(false, j);
            }
        }

        public void writeSummaryToParcel(android.os.Parcel parcel, long j) {
            this.screenOnTimer.writeSummaryFromParcelLocked(parcel, j);
            this.screenDozeTimer.writeSummaryFromParcelLocked(parcel, j);
            for (int i = 0; i < 5; i++) {
                this.screenBrightnessTimers[i].writeSummaryFromParcelLocked(parcel, j);
            }
        }

        public void readSummaryFromParcel(android.os.Parcel parcel) {
            this.screenOnTimer.readSummaryFromParcelLocked(parcel);
            this.screenDozeTimer.readSummaryFromParcelLocked(parcel);
            for (int i = 0; i < 5; i++) {
                this.screenBrightnessTimers[i].readSummaryFromParcelLocked(parcel);
            }
        }
    }

    private static class RadioAccessTechnologyBatteryStats {
        public final com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer[][] perStateTimers;
        private boolean mActive = false;
        private int mFrequencyRange = 0;
        private int mSignalStrength = 0;

        @android.annotation.Nullable
        private com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter[][] mPerStateTxDurationMs = null;

        @android.annotation.Nullable
        private com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter[] mPerFrequencyRxDurationMs = null;

        RadioAccessTechnologyBatteryStats(int i, com.android.internal.os.Clock clock, com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase) {
            this.perStateTimers = (com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer.class, i, 5);
            for (int i2 = 0; i2 < i; i2++) {
                for (int i3 = 0; i3 < 5; i3++) {
                    this.perStateTimers[i2][i3] = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(clock, null, -1, null, timeBase);
                }
            }
        }

        public void noteActive(boolean z, long j) {
            if (this.mActive == z) {
                return;
            }
            this.mActive = z;
            if (this.mActive) {
                this.perStateTimers[this.mFrequencyRange][this.mSignalStrength].startRunningLocked(j);
            } else {
                this.perStateTimers[this.mFrequencyRange][this.mSignalStrength].stopRunningLocked(j);
            }
        }

        public void noteFrequencyRange(int i, long j) {
            if (this.mFrequencyRange == i) {
                return;
            }
            if (!this.mActive) {
                this.mFrequencyRange = i;
                return;
            }
            this.perStateTimers[this.mFrequencyRange][this.mSignalStrength].stopRunningLocked(j);
            this.perStateTimers[i][this.mSignalStrength].startRunningLocked(j);
            this.mFrequencyRange = i;
        }

        public void noteSignalStrength(int i, long j) {
            if (this.mSignalStrength == i) {
                return;
            }
            if (!this.mActive) {
                this.mSignalStrength = i;
                return;
            }
            this.perStateTimers[this.mFrequencyRange][this.mSignalStrength].stopRunningLocked(j);
            this.perStateTimers[this.mFrequencyRange][i].startRunningLocked(j);
            this.mSignalStrength = i;
        }

        public long getTimeSinceMark(int i, int i2, long j) {
            return this.perStateTimers[i][i2].getTimeSinceMarkLocked(j * 1000) / 1000;
        }

        public void setMark(long j) {
            int length = this.perStateTimers.length;
            for (int i = 0; i < length; i++) {
                for (int i2 = 0; i2 < 5; i2++) {
                    this.perStateTimers[i][i2].setMark(j);
                }
            }
        }

        public int getFrequencyRangeCount() {
            return this.perStateTimers.length;
        }

        public void incrementTxDuration(int i, int i2, long j) {
            getTxDurationCounter(i, i2, true).addCountLocked(j);
        }

        public void incrementRxDuration(int i, long j) {
            getRxDurationCounter(i, true).addCountLocked(j);
        }

        public void reset(long j) {
            int length = this.perStateTimers.length;
            for (int i = 0; i < length; i++) {
                for (int i2 = 0; i2 < 5; i2++) {
                    this.perStateTimers[i][i2].reset(false, j);
                    if (this.mPerStateTxDurationMs != null) {
                        this.mPerStateTxDurationMs[i][i2].reset(false, j);
                    }
                }
                if (this.mPerFrequencyRxDurationMs != null) {
                    this.mPerFrequencyRxDurationMs[i].reset(false, j);
                }
            }
        }

        public void writeSummaryToParcel(android.os.Parcel parcel, long j) {
            int length = this.perStateTimers.length;
            parcel.writeInt(length);
            parcel.writeInt(5);
            for (int i = 0; i < length; i++) {
                for (int i2 = 0; i2 < 5; i2++) {
                    this.perStateTimers[i][i2].writeSummaryFromParcelLocked(parcel, j);
                }
            }
            if (this.mPerStateTxDurationMs == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                for (int i3 = 0; i3 < length; i3++) {
                    for (int i4 = 0; i4 < 5; i4++) {
                        this.mPerStateTxDurationMs[i3][i4].writeSummaryFromParcelLocked(parcel);
                    }
                }
            }
            if (this.mPerFrequencyRxDurationMs == null) {
                parcel.writeInt(0);
                return;
            }
            parcel.writeInt(1);
            for (int i5 = 0; i5 < length; i5++) {
                this.mPerFrequencyRxDurationMs[i5].writeSummaryFromParcelLocked(parcel);
            }
        }

        public void readSummaryFromParcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int length = this.perStateTimers.length;
            for (int i = 0; i < readInt; i++) {
                for (int i2 = 0; i2 < readInt2; i2++) {
                    if (i >= length || i2 >= 5) {
                        new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(null, null, -1, null, new com.android.server.power.stats.BatteryStatsImpl.TimeBase()).readSummaryFromParcelLocked(parcel);
                    } else {
                        this.perStateTimers[i][i2].readSummaryFromParcelLocked(parcel);
                    }
                }
            }
            if (parcel.readInt() == 1) {
                for (int i3 = 0; i3 < readInt; i3++) {
                    for (int i4 = 0; i4 < readInt2; i4++) {
                        if (i3 >= length || i4 >= 5) {
                            new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(null, null, -1, null, new com.android.server.power.stats.BatteryStatsImpl.TimeBase()).readSummaryFromParcelLocked(parcel);
                        }
                        getTxDurationCounter(i3, i4, true).readSummaryFromParcelLocked(parcel);
                    }
                }
            }
            if (parcel.readInt() == 1) {
                for (int i5 = 0; i5 < readInt; i5++) {
                    if (i5 >= length) {
                        new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(null, null, -1, null, new com.android.server.power.stats.BatteryStatsImpl.TimeBase()).readSummaryFromParcelLocked(parcel);
                    } else {
                        getRxDurationCounter(i5, true).readSummaryFromParcelLocked(parcel);
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter getTxDurationCounter(int i, int i2, boolean z) {
            if (this.mPerStateTxDurationMs == null) {
                if (!z) {
                    return null;
                }
                int frequencyRangeCount = getFrequencyRangeCount();
                int length = this.perStateTimers[0].length;
                com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase = this.perStateTimers[0][0].mTimeBase;
                this.mPerStateTxDurationMs = (com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter.class, frequencyRangeCount, length);
                for (int i3 = 0; i3 < frequencyRangeCount; i3++) {
                    for (int i4 = 0; i4 < length; i4++) {
                        this.mPerStateTxDurationMs[i3][i4] = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter(timeBase);
                    }
                }
            }
            if (i < 0 || i >= getFrequencyRangeCount()) {
                android.util.Slog.w(com.android.server.power.stats.BatteryStatsImpl.TAG, "Unexpected frequency range (" + i + ") requested in getTxDurationCounter");
                return null;
            }
            if (i2 < 0 || i2 >= this.perStateTimers[0].length) {
                android.util.Slog.w(com.android.server.power.stats.BatteryStatsImpl.TAG, "Unexpected signal strength (" + i2 + ") requested in getTxDurationCounter");
                return null;
            }
            return this.mPerStateTxDurationMs[i][i2];
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter getRxDurationCounter(int i, boolean z) {
            if (this.mPerFrequencyRxDurationMs == null) {
                if (!z) {
                    return null;
                }
                int frequencyRangeCount = getFrequencyRangeCount();
                com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase = this.perStateTimers[0][0].mTimeBase;
                this.mPerFrequencyRxDurationMs = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter[frequencyRangeCount];
                for (int i2 = 0; i2 < frequencyRangeCount; i2++) {
                    this.mPerFrequencyRxDurationMs[i2] = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter(timeBase);
                }
            }
            if (i < 0 || i >= getFrequencyRangeCount()) {
                android.util.Slog.w(com.android.server.power.stats.BatteryStatsImpl.TAG, "Unexpected frequency range (" + i + ") requested in getRxDurationCounter");
                return null;
            }
            return this.mPerFrequencyRxDurationMs[i];
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private com.android.server.power.stats.BatteryStatsImpl.RadioAccessTechnologyBatteryStats getRatBatteryStatsLocked(int i) {
        com.android.server.power.stats.BatteryStatsImpl.RadioAccessTechnologyBatteryStats radioAccessTechnologyBatteryStats = this.mPerRatBatteryStats[i];
        if (radioAccessTechnologyBatteryStats == null) {
            com.android.server.power.stats.BatteryStatsImpl.RadioAccessTechnologyBatteryStats radioAccessTechnologyBatteryStats2 = new com.android.server.power.stats.BatteryStatsImpl.RadioAccessTechnologyBatteryStats(i == 2 ? 5 : 1, this.mClock, this.mOnBatteryTimeBase);
            this.mPerRatBatteryStats[i] = radioAccessTechnologyBatteryStats2;
            return radioAccessTechnologyBatteryStats2;
        }
        return radioAccessTechnologyBatteryStats;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1() {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.power.stats.BatteryStatsImpl$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.power.stats.BatteryStatsImpl.this.lambda$new$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        synchronized (this) {
            maybeResetWhilePluggedInLocked();
        }
    }

    public java.util.Map<java.lang.String, ? extends com.android.server.power.stats.BatteryStatsImpl.Timer> getRpmStats() {
        return this.mRpmStats;
    }

    public java.util.Map<java.lang.String, ? extends com.android.server.power.stats.BatteryStatsImpl.Timer> getScreenOffRpmStats() {
        return this.mScreenOffRpmStats;
    }

    public java.util.Map<java.lang.String, ? extends com.android.server.power.stats.BatteryStatsImpl.Timer> getKernelWakelockStats() {
        return this.mKernelWakelockStats;
    }

    public android.os.WakeLockStats getWakeLockStats() {
        long elapsedRealtime = this.mClock.elapsedRealtime();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        for (int size = this.mUidStats.size() - 1; size >= 0; size--) {
            com.android.server.power.stats.BatteryStatsImpl.Uid valueAt = this.mUidStats.valueAt(size);
            android.util.ArrayMap<java.lang.String, com.android.server.power.stats.BatteryStatsImpl.Uid.Wakelock> map = valueAt.mWakelockStats.getMap();
            for (int size2 = map.size() - 1; size2 >= 0; size2--) {
                android.os.WakeLockStats.WakeLock createWakeLock = createWakeLock(valueAt, map.keyAt(size2), false, map.valueAt(size2).mTimerPartial, elapsedRealtime);
                if (createWakeLock != null) {
                    arrayList.add(createWakeLock);
                }
            }
            android.os.WakeLockStats.WakeLock createWakeLock2 = createWakeLock(valueAt, "wakelockstats_aggregated", true, valueAt.mAggregatedPartialWakelockTimer, elapsedRealtime);
            if (createWakeLock2 != null) {
                arrayList2.add(createWakeLock2);
            }
        }
        return new android.os.WakeLockStats(arrayList, arrayList2);
    }

    private android.os.WakeLockStats.WakeLock createWakeLock(com.android.server.power.stats.BatteryStatsImpl.Uid uid, java.lang.String str, boolean z, com.android.server.power.stats.BatteryStatsImpl.DualTimer dualTimer, long j) {
        if (dualTimer == null) {
            return null;
        }
        android.os.WakeLockStats.WakeLockData createWakeLockData = createWakeLockData(dualTimer, j);
        android.os.WakeLockStats.WakeLockData createWakeLockData2 = createWakeLockData(dualTimer.getSubTimer(), j);
        if (!android.os.WakeLockStats.WakeLock.isDataValid(createWakeLockData, createWakeLockData2)) {
            return null;
        }
        return new android.os.WakeLockStats.WakeLock(uid.getUid(), str, z, createWakeLockData, createWakeLockData2);
    }

    @android.annotation.NonNull
    private android.os.WakeLockStats.WakeLockData createWakeLockData(com.android.server.power.stats.BatteryStatsImpl.DurationTimer durationTimer, long j) {
        if (durationTimer == null) {
            return android.os.WakeLockStats.WakeLockData.EMPTY;
        }
        long totalTimeLocked = durationTimer.getTotalTimeLocked(j * 1000, 0) / 1000;
        if (totalTimeLocked == 0) {
            return android.os.WakeLockStats.WakeLockData.EMPTY;
        }
        return new android.os.WakeLockStats.WakeLockData(durationTimer.getCountLocked(0), totalTimeLocked, durationTimer.isRunningLocked() ? durationTimer.getCurrentDurationMsLocked(j) : 0L);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public android.os.BluetoothBatteryStats getBluetoothBatteryStats() {
        long j;
        long j2;
        int i;
        long j3;
        long j4;
        long elapsedRealtime = this.mClock.elapsedRealtime() * 1000;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int size = this.mUidStats.size() - 1; size >= 0; size--) {
            com.android.server.power.stats.BatteryStatsImpl.Uid valueAt = this.mUidStats.valueAt(size);
            com.android.server.power.stats.BatteryStatsImpl.Timer bluetoothScanTimer = valueAt.getBluetoothScanTimer();
            if (bluetoothScanTimer != null) {
                j = bluetoothScanTimer.getTotalTimeLocked(elapsedRealtime, 0) / 1000;
            } else {
                j = 0;
            }
            com.android.server.power.stats.BatteryStatsImpl.Timer bluetoothUnoptimizedScanTimer = valueAt.getBluetoothUnoptimizedScanTimer();
            if (bluetoothUnoptimizedScanTimer != null) {
                j2 = bluetoothUnoptimizedScanTimer.getTotalTimeLocked(elapsedRealtime, 0) / 1000;
            } else {
                j2 = 0;
            }
            com.android.server.power.stats.BatteryStatsImpl.Counter bluetoothScanResultCounter = valueAt.getBluetoothScanResultCounter();
            if (bluetoothScanResultCounter != null) {
                i = bluetoothScanResultCounter.getCountLocked(0);
            } else {
                i = 0;
            }
            com.android.server.power.stats.BatteryStatsImpl.ControllerActivityCounterImpl bluetoothControllerActivity = valueAt.getBluetoothControllerActivity();
            if (bluetoothControllerActivity != null) {
                j3 = bluetoothControllerActivity.getRxTimeCounter().getCountLocked(0);
            } else {
                j3 = 0;
            }
            if (bluetoothControllerActivity != null) {
                j4 = bluetoothControllerActivity.getTxTimeCounters()[0].getCountLocked(0);
            } else {
                j4 = 0;
            }
            if (j != 0 || j2 != 0 || i != 0 || j3 != 0 || j4 != 0) {
                arrayList.add(new android.os.BluetoothBatteryStats.UidStats(valueAt.getUid(), j, j2, i, j3, j4));
            }
        }
        return new android.os.BluetoothBatteryStats(arrayList);
    }

    public java.util.Map<java.lang.String, ? extends com.android.server.power.stats.BatteryStatsImpl.Timer> getWakeupReasonStats() {
        return this.mWakeupReasonStats;
    }

    public long getUahDischarge(int i) {
        return this.mDischargeCounter.getCountLocked(i);
    }

    public long getUahDischargeScreenOff(int i) {
        return this.mDischargeScreenOffCounter.getCountLocked(i);
    }

    public long getUahDischargeScreenDoze(int i) {
        return this.mDischargeScreenDozeCounter.getCountLocked(i);
    }

    public long getUahDischargeLightDoze(int i) {
        return this.mDischargeLightDozeCounter.getCountLocked(i);
    }

    public long getUahDischargeDeepDoze(int i) {
        return this.mDischargeDeepDozeCounter.getCountLocked(i);
    }

    public int getEstimatedBatteryCapacity() {
        return this.mEstimatedBatteryCapacityMah;
    }

    public int getLearnedBatteryCapacity() {
        return this.mLastLearnedBatteryCapacityUah;
    }

    public int getMinLearnedBatteryCapacity() {
        return this.mMinLearnedBatteryCapacityUah;
    }

    public int getMaxLearnedBatteryCapacity() {
        return this.mMaxLearnedBatteryCapacityUah;
    }

    public class FrameworkStatsLogger {
        public FrameworkStatsLogger() {
        }

        public void uidProcessStateChanged(int i, int i2) {
            com.android.internal.util.FrameworkStatsLog.write(27, i, android.app.ActivityManager.processStateAmToProto(i2));
        }

        public void wakelockStateChanged(int i, android.os.WorkSource.WorkChain workChain, java.lang.String str, int i2, int i3, boolean z) {
            int i4;
            if (z) {
                i4 = 1;
            } else {
                i4 = 0;
            }
            if (workChain != null) {
                com.android.internal.util.FrameworkStatsLog.write(10, workChain.getUids(), workChain.getTags(), com.android.server.power.stats.BatteryStatsImpl.this.getPowerManagerWakeLockLevel(i2), str, i4, i3);
            } else {
                com.android.internal.util.FrameworkStatsLog.write_non_chained(10, com.android.server.power.stats.BatteryStatsImpl.this.mapIsolatedUid(i), null, com.android.server.power.stats.BatteryStatsImpl.this.getPowerManagerWakeLockLevel(i2), str, i4, i3);
            }
        }

        public void kernelWakeupReported(long j) {
            com.android.internal.util.FrameworkStatsLog.write(36, com.android.server.power.stats.BatteryStatsImpl.this.mLastWakeupReason, j, com.android.server.power.stats.BatteryStatsImpl.this.mLastWakeupElapsedTimeMs);
        }

        public void gpsScanStateChanged(int i, android.os.WorkSource.WorkChain workChain, boolean z) {
            int i2;
            if (z) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            if (workChain == null) {
                com.android.internal.util.FrameworkStatsLog.write_non_chained(6, i, null, i2);
            } else {
                com.android.internal.util.FrameworkStatsLog.write(6, workChain.getUids(), workChain.getTags(), i2);
            }
        }

        public void batterySaverModeChanged(boolean z) {
            int i;
            if (z) {
                i = 1;
            } else {
                i = 0;
            }
            com.android.internal.util.FrameworkStatsLog.write(20, i);
        }

        public void deviceIdlingModeStateChanged(int i) {
            com.android.internal.util.FrameworkStatsLog.write(22, i);
        }

        public void deviceIdleModeStateChanged(int i) {
            com.android.internal.util.FrameworkStatsLog.write(21, i);
        }

        public void chargingStateChanged(int i) {
            com.android.internal.util.FrameworkStatsLog.write(31, i);
        }

        public void pluggedStateChanged(int i) {
            com.android.internal.util.FrameworkStatsLog.write(32, i);
        }

        public void batteryLevelChanged(int i) {
            com.android.internal.util.FrameworkStatsLog.write(30, i);
        }

        public void phoneServiceStateChanged(int i, int i2, int i3) {
            com.android.internal.util.FrameworkStatsLog.write(94, i, i2, i3);
        }

        public void phoneSignalStrengthChanged(int i) {
            com.android.internal.util.FrameworkStatsLog.write(40, i);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public BatteryStatsImpl(com.android.internal.os.Clock clock, java.io.File file, @android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull com.android.server.power.stats.PowerStatsUidResolver powerStatsUidResolver, @android.annotation.NonNull com.android.server.power.stats.BatteryStatsImpl.FrameworkStatsLogger frameworkStatsLogger, @android.annotation.NonNull com.android.internal.os.BatteryStatsHistory.TraceDelegate traceDelegate, @android.annotation.NonNull com.android.internal.os.BatteryStatsHistory.EventLogger eventLogger) {
        this.mTmpWakelockStats = new com.android.server.power.stats.KernelWakelockStats();
        this.mKernelMemoryStats = new android.util.LongSparseArray<>();
        this.mPerProcStateCpuTimesAvailable = true;
        this.mCpuTimeReadsTrackingStartTimeMs = android.os.SystemClock.uptimeMillis();
        this.mTmpRpmStats = null;
        this.mLastRpmStatsUpdateTimeMs = -1000L;
        this.mPendingRemovedUids = new java.util.LinkedList();
        this.mDeferSetCharging = new java.lang.Runnable() { // from class: com.android.server.power.stats.BatteryStatsImpl.2
            @Override // java.lang.Runnable
            public void run() {
                synchronized (com.android.server.power.stats.BatteryStatsImpl.this) {
                    try {
                        if (com.android.server.power.stats.BatteryStatsImpl.this.mOnBattery) {
                            return;
                        }
                        if (com.android.server.power.stats.BatteryStatsImpl.this.setChargingLocked(true)) {
                            long uptimeMillis = com.android.server.power.stats.BatteryStatsImpl.this.mClock.uptimeMillis();
                            com.android.server.power.stats.BatteryStatsImpl.this.mHistory.writeHistoryItem(com.android.server.power.stats.BatteryStatsImpl.this.mClock.elapsedRealtime(), uptimeMillis);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mExternalSync = null;
        this.mUserInfoProvider = null;
        this.mUidStats = new android.util.SparseArray<>();
        this.mPartialTimers = new java.util.ArrayList<>();
        this.mFullTimers = new java.util.ArrayList<>();
        this.mWindowTimers = new java.util.ArrayList<>();
        this.mDrawTimers = new java.util.ArrayList<>();
        this.mSensorTimers = new android.util.SparseArray<>();
        this.mWifiRunningTimers = new java.util.ArrayList<>();
        this.mFullWifiLockTimers = new java.util.ArrayList<>();
        this.mWifiMulticastTimers = new java.util.ArrayList<>();
        this.mWifiScanTimers = new java.util.ArrayList<>();
        this.mWifiBatchedScanTimers = new android.util.SparseArray<>();
        this.mAudioTurnedOnTimers = new java.util.ArrayList<>();
        this.mVideoTurnedOnTimers = new java.util.ArrayList<>();
        this.mFlashlightTurnedOnTimers = new java.util.ArrayList<>();
        this.mCameraTurnedOnTimers = new java.util.ArrayList<>();
        this.mBluetoothScanOnTimers = new java.util.ArrayList<>();
        this.mLastPartialTimers = new java.util.ArrayList<>();
        this.mOnBatteryTimeBase = new com.android.server.power.stats.BatteryStatsImpl.TimeBase(true);
        this.mOnBatteryScreenOffTimeBase = new com.android.server.power.stats.BatteryStatsImpl.TimeBase(true);
        this.mActiveEvents = new android.os.BatteryStats.HistoryEventTracker();
        this.mStepDetailsCalculator = new com.android.server.power.stats.BatteryStatsImpl.HistoryStepDetailsCalculatorImpl();
        this.mPowerStatsDescriptorRegistry = new com.android.internal.os.PowerStats.DescriptorRegistry();
        this.mHaveBatteryLevel = false;
        this.mBatteryPluggedInRealTimeMs = 0L;
        this.mIgnoreNextExternalStats = false;
        this.mMonotonicEndTime = -1L;
        this.mScreenState = 0;
        this.mScreenBrightnessBin = -1;
        this.mScreenBrightnessTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer[5];
        this.mDisplayMismatchWtfCount = 0;
        this.mUsbDataState = 0;
        this.mGpsSignalQualityBin = -1;
        this.mGpsSignalQualityTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer[2];
        this.mPhoneSignalStrengthBin = -1;
        this.mPhoneSignalStrengthBinRaw = -1;
        this.mPhoneSignalStrengthsTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer[CELL_SIGNAL_STRENGTH_LEVEL_COUNT];
        this.mPhoneDataConnectionType = -1;
        this.mPhoneDataConnectionsTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer[android.os.BatteryStats.NUM_DATA_CONNECTION_TYPES];
        this.mNrState = -1;
        this.mActiveRat = 0;
        this.mPerRatBatteryStats = new com.android.server.power.stats.BatteryStatsImpl.RadioAccessTechnologyBatteryStats[3];
        this.mNetworkByteActivityCounters = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter[10];
        this.mNetworkPacketActivityCounters = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter[10];
        this.mHasWifiReporting = false;
        this.mHasBluetoothReporting = false;
        this.mHasModemReporting = false;
        this.mWifiState = -1;
        this.mWifiStateTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer[8];
        this.mWifiSupplState = -1;
        this.mWifiSupplStateTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer[13];
        this.mWifiSignalStrengthBin = -1;
        this.mWifiSignalStrengthsTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer[5];
        this.mMobileRadioPowerState = 1;
        this.mWifiRadioPowerState = 1;
        this.mBluetoothPowerCalculator = null;
        this.mCpuPowerCalculator = null;
        this.mMobileRadioPowerCalculator = null;
        this.mWifiPowerCalculator = null;
        this.mCharging = true;
        this.mInitStepMode = 0;
        this.mCurStepMode = 0;
        this.mModStepMode = 0;
        this.mDischargeStepTracker = new android.os.BatteryStats.LevelStepTracker(200);
        this.mDailyDischargeStepTracker = new android.os.BatteryStats.LevelStepTracker(400);
        this.mChargeStepTracker = new android.os.BatteryStats.LevelStepTracker(200);
        this.mDailyChargeStepTracker = new android.os.BatteryStats.LevelStepTracker(400);
        this.mDailyStartTimeMs = 0L;
        this.mNextMinDailyDeadlineMs = 0L;
        this.mNextMaxDailyDeadlineMs = 0L;
        this.mDailyItems = new java.util.ArrayList<>();
        this.mLastWriteTimeMs = 0L;
        this.mPhoneServiceState = -1;
        this.mPhoneServiceStateRaw = -1;
        this.mPhoneSimStateRaw = -1;
        this.mEstimatedBatteryCapacityMah = -1;
        this.mLastLearnedBatteryCapacityUah = -1;
        this.mMinLearnedBatteryCapacityUah = -1;
        this.mMaxLearnedBatteryCapacityUah = -1;
        this.mBatteryTimeToFullSeconds = -1L;
        this.mAlarmManager = null;
        this.mLongPlugInAlarmHandler = new android.app.AlarmManager.OnAlarmListener() { // from class: com.android.server.power.stats.BatteryStatsImpl$$ExternalSyntheticLambda6
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                com.android.server.power.stats.BatteryStatsImpl.this.lambda$new$1();
            }
        };
        this.mRpmStats = new java.util.HashMap<>();
        this.mScreenOffRpmStats = new java.util.HashMap<>();
        this.mKernelWakelockStats = new java.util.HashMap<>();
        this.mLastWakeupReason = null;
        this.mLastWakeupUptimeMs = 0L;
        this.mLastWakeupElapsedTimeMs = 0L;
        this.mWakeupReasonStats = new java.util.HashMap<>();
        this.mWifiFullLockNesting = 0;
        this.mWifiScanNesting = 0;
        this.mWifiMulticastNesting = 0;
        this.mWifiNetworkLock = new java.lang.Object();
        this.mWifiIfaces = libcore.util.EmptyArray.STRING;
        this.mModemNetworkLock = new java.lang.Object();
        this.mModemIfaces = libcore.util.EmptyArray.STRING;
        this.mLastModemActivityInfo = null;
        this.mLastBluetoothActivityInfo = new com.android.server.power.stats.BatteryStatsImpl.BluetoothActivityInfoCache();
        this.mWriteAsyncRunnable = new java.lang.Runnable() { // from class: com.android.server.power.stats.BatteryStatsImpl$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.power.stats.BatteryStatsImpl.this.lambda$new$10();
            }
        };
        this.mWriteLock = new java.util.concurrent.locks.ReentrantLock();
        this.mClock = clock;
        initKernelStatsReaders();
        this.mBatteryStatsConfig = new com.android.server.power.stats.BatteryStatsImpl.BatteryStatsConfig.Builder().build();
        this.mHandler = handler;
        this.mPowerStatsUidResolver = powerStatsUidResolver;
        this.mFrameworkStatsLogger = frameworkStatsLogger;
        this.mConstants = new com.android.server.power.stats.BatteryStatsImpl.Constants(this.mHandler);
        this.mStartClockTimeMs = clock.currentTimeMillis();
        this.mDailyFile = null;
        this.mMonotonicClock = new com.android.internal.os.MonotonicClock(0L, this.mClock);
        if (file != null) {
            this.mCheckinFile = new android.util.AtomicFile(new java.io.File(file, "batterystats-checkin.bin"));
            this.mStatsFile = new android.util.AtomicFile(new java.io.File(file, "batterystats.bin"));
            this.mHistory = new com.android.internal.os.BatteryStatsHistory(file, this.mConstants.MAX_HISTORY_FILES, this.mConstants.MAX_HISTORY_BUFFER, this.mStepDetailsCalculator, this.mClock, this.mMonotonicClock, traceDelegate, eventLogger);
        } else {
            this.mCheckinFile = null;
            this.mStatsFile = null;
            this.mHistory = new com.android.internal.os.BatteryStatsHistory(this.mConstants.MAX_HISTORY_BUFFER, this.mStepDetailsCalculator, this.mClock, this.mMonotonicClock, traceDelegate, eventLogger);
        }
        this.mPlatformIdleStateCallback = null;
        this.mEnergyConsumerRetriever = null;
        this.mUserInfoProvider = null;
        this.mCpuPowerStatsCollector = null;
    }

    private void initKernelStatsReaders() {
        if (!android.os.BatteryStats.isKernelStatsAvailable()) {
            return;
        }
        this.mCpuUidUserSysTimeReader = new com.android.internal.os.KernelCpuUidTimeReader.KernelCpuUidUserSysTimeReader(true, this.mClock);
        this.mCpuUidFreqTimeReader = new com.android.internal.os.KernelCpuUidTimeReader.KernelCpuUidFreqTimeReader(true, this.mClock);
        this.mCpuUidActiveTimeReader = new com.android.internal.os.KernelCpuUidTimeReader.KernelCpuUidActiveTimeReader(true, this.mClock);
        this.mCpuUidClusterTimeReader = new com.android.internal.os.KernelCpuUidTimeReader.KernelCpuUidClusterTimeReader(true, this.mClock);
        this.mKernelWakelockReader = new com.android.server.power.stats.KernelWakelockReader();
        com.android.server.power.optimization.Flags.disableSystemServicePowerAttr();
        this.mSystemServerCpuThreadReader = com.android.server.power.stats.SystemServerCpuThreadReader.create();
        this.mKernelMemoryBandwidthStats = new com.android.internal.os.KernelMemoryBandwidthStats();
        this.mTmpRailStats = new com.android.internal.os.RailStats();
    }

    public interface TimeBaseObs {
        void detach();

        void onTimeStarted(long j, long j2, long j3);

        void onTimeStopped(long j, long j2, long j3);

        boolean reset(boolean z, long j);

        default boolean reset(boolean z) {
            return reset(z, android.os.SystemClock.elapsedRealtime() * 1000);
        }
    }

    public static class TimeBase {
        protected final java.util.Collection<com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs> mObservers;
        protected long mPastRealtimeUs;
        protected long mPastUptimeUs;
        protected long mRealtimeStartUs;
        protected long mRealtimeUs;
        protected boolean mRunning;
        protected long mUnpluggedRealtimeUs;
        protected long mUnpluggedUptimeUs;
        protected long mUptimeStartUs;
        protected long mUptimeUs;

        public void dump(java.io.PrintWriter printWriter, java.lang.String str) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
            printWriter.print(str);
            printWriter.print("mRunning=");
            printWriter.println(this.mRunning);
            sb.setLength(0);
            sb.append(str);
            sb.append("mUptime=");
            android.os.BatteryStats.formatTimeMs(sb, this.mUptimeUs / 1000);
            printWriter.println(sb.toString());
            sb.setLength(0);
            sb.append(str);
            sb.append("mRealtime=");
            android.os.BatteryStats.formatTimeMs(sb, this.mRealtimeUs / 1000);
            printWriter.println(sb.toString());
            sb.setLength(0);
            sb.append(str);
            sb.append("mPastUptime=");
            android.os.BatteryStats.formatTimeMs(sb, this.mPastUptimeUs / 1000);
            sb.append("mUptimeStart=");
            android.os.BatteryStats.formatTimeMs(sb, this.mUptimeStartUs / 1000);
            sb.append("mUnpluggedUptime=");
            android.os.BatteryStats.formatTimeMs(sb, this.mUnpluggedUptimeUs / 1000);
            printWriter.println(sb.toString());
            sb.setLength(0);
            sb.append(str);
            sb.append("mPastRealtime=");
            android.os.BatteryStats.formatTimeMs(sb, this.mPastRealtimeUs / 1000);
            sb.append("mRealtimeStart=");
            android.os.BatteryStats.formatTimeMs(sb, this.mRealtimeStartUs / 1000);
            sb.append("mUnpluggedRealtime=");
            android.os.BatteryStats.formatTimeMs(sb, this.mUnpluggedRealtimeUs / 1000);
            printWriter.println(sb.toString());
        }

        public TimeBase(boolean z) {
            this.mObservers = z ? new java.util.HashSet<>() : new java.util.ArrayList<>();
        }

        public TimeBase() {
            this(false);
        }

        public void add(com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs timeBaseObs) {
            this.mObservers.add(timeBaseObs);
        }

        public void remove(com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs timeBaseObs) {
            this.mObservers.remove(timeBaseObs);
        }

        public boolean hasObserver(com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs timeBaseObs) {
            return this.mObservers.contains(timeBaseObs);
        }

        public void init(long j, long j2) {
            this.mRealtimeUs = 0L;
            this.mUptimeUs = 0L;
            this.mPastUptimeUs = 0L;
            this.mPastRealtimeUs = 0L;
            this.mUptimeStartUs = j;
            this.mRealtimeStartUs = j2;
            this.mUnpluggedUptimeUs = getUptime(this.mUptimeStartUs);
            this.mUnpluggedRealtimeUs = getRealtime(this.mRealtimeStartUs);
        }

        public void reset(long j, long j2) {
            if (!this.mRunning) {
                this.mPastUptimeUs = 0L;
                this.mPastRealtimeUs = 0L;
            } else {
                this.mUptimeStartUs = j;
                this.mRealtimeStartUs = j2;
                this.mUnpluggedUptimeUs = getUptime(j);
                this.mUnpluggedRealtimeUs = getRealtime(j2);
            }
        }

        public long computeUptime(long j, int i) {
            return this.mUptimeUs + getUptime(j);
        }

        public long computeRealtime(long j, int i) {
            return this.mRealtimeUs + getRealtime(j);
        }

        public long getUptime(long j) {
            long j2 = this.mPastUptimeUs;
            if (this.mRunning) {
                return j2 + (j - this.mUptimeStartUs);
            }
            return j2;
        }

        public long getRealtime(long j) {
            long j2 = this.mPastRealtimeUs;
            if (this.mRunning) {
                return j2 + (j - this.mRealtimeStartUs);
            }
            return j2;
        }

        public long getUptimeStart() {
            return this.mUptimeStartUs;
        }

        public long getRealtimeStart() {
            return this.mRealtimeStartUs;
        }

        public boolean isRunning() {
            return this.mRunning;
        }

        public boolean setRunning(boolean z, long j, long j2) {
            if (this.mRunning != z) {
                this.mRunning = z;
                if (z) {
                    this.mUptimeStartUs = j;
                    this.mRealtimeStartUs = j2;
                    long uptime = getUptime(j);
                    this.mUnpluggedUptimeUs = uptime;
                    long realtime = getRealtime(j2);
                    this.mUnpluggedRealtimeUs = realtime;
                    java.util.Iterator<com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs> it = this.mObservers.iterator();
                    while (it.hasNext()) {
                        it.next().onTimeStarted(j2, uptime, realtime);
                    }
                    return true;
                }
                this.mPastUptimeUs += j - this.mUptimeStartUs;
                this.mPastRealtimeUs += j2 - this.mRealtimeStartUs;
                long uptime2 = getUptime(j);
                long realtime2 = getRealtime(j2);
                java.util.Iterator<com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs> it2 = this.mObservers.iterator();
                while (it2.hasNext()) {
                    it2.next().onTimeStopped(j2, uptime2, realtime2);
                }
                return true;
            }
            return false;
        }

        public void readSummaryFromParcel(android.os.Parcel parcel) {
            this.mUptimeUs = parcel.readLong();
            this.mRealtimeUs = parcel.readLong();
        }

        public void writeSummaryToParcel(android.os.Parcel parcel, long j, long j2) {
            parcel.writeLong(computeUptime(j, 0));
            parcel.writeLong(computeRealtime(j2, 0));
        }

        public void readFromParcel(android.os.Parcel parcel) {
            this.mRunning = false;
            this.mUptimeUs = parcel.readLong();
            this.mPastUptimeUs = parcel.readLong();
            this.mUptimeStartUs = parcel.readLong();
            this.mRealtimeUs = parcel.readLong();
            this.mPastRealtimeUs = parcel.readLong();
            this.mRealtimeStartUs = parcel.readLong();
            this.mUnpluggedUptimeUs = parcel.readLong();
            this.mUnpluggedRealtimeUs = parcel.readLong();
        }

        public void writeToParcel(android.os.Parcel parcel, long j, long j2) {
            long uptime = getUptime(j);
            long realtime = getRealtime(j2);
            parcel.writeLong(this.mUptimeUs);
            parcel.writeLong(uptime);
            parcel.writeLong(this.mUptimeStartUs);
            parcel.writeLong(this.mRealtimeUs);
            parcel.writeLong(realtime);
            parcel.writeLong(this.mRealtimeStartUs);
            parcel.writeLong(this.mUnpluggedUptimeUs);
            parcel.writeLong(this.mUnpluggedRealtimeUs);
        }
    }

    public static class Counter extends android.os.BatteryStats.Counter implements com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs {
        final java.util.concurrent.atomic.AtomicInteger mCount = new java.util.concurrent.atomic.AtomicInteger();
        final com.android.server.power.stats.BatteryStatsImpl.TimeBase mTimeBase;

        public Counter(com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase, android.os.Parcel parcel) {
            this.mTimeBase = timeBase;
            this.mCount.set(parcel.readInt());
            timeBase.add(this);
        }

        public Counter(com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase) {
            this.mTimeBase = timeBase;
            timeBase.add(this);
        }

        public void writeToParcel(android.os.Parcel parcel) {
            parcel.writeInt(this.mCount.get());
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public void onTimeStarted(long j, long j2, long j3) {
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public void onTimeStopped(long j, long j2, long j3) {
        }

        public int getCountLocked(int i) {
            return this.mCount.get();
        }

        public void logState(android.util.Printer printer, java.lang.String str) {
            printer.println(str + "mCount=" + this.mCount.get());
        }

        @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
        public void stepAtomic() {
            if (this.mTimeBase.isRunning()) {
                this.mCount.incrementAndGet();
            }
        }

        void addAtomic(int i) {
            if (this.mTimeBase.isRunning()) {
                this.mCount.addAndGet(i);
            }
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public boolean reset(boolean z, long j) {
            this.mCount.set(0);
            if (z) {
                detach();
                return true;
            }
            return true;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public void detach() {
            this.mTimeBase.remove(this);
        }

        @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
        public void writeSummaryFromParcelLocked(android.os.Parcel parcel) {
            parcel.writeInt(this.mCount.get());
        }

        @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
        public void readSummaryFromParcelLocked(android.os.Parcel parcel) {
            this.mCount.set(parcel.readInt());
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class LongSamplingCounterArray extends android.os.BatteryStats.LongCounterArray implements com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs {
        public long[] mCounts;
        final com.android.server.power.stats.BatteryStatsImpl.TimeBase mTimeBase;

        private LongSamplingCounterArray(com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase, android.os.Parcel parcel) {
            this.mTimeBase = timeBase;
            this.mCounts = parcel.createLongArray();
            timeBase.add(this);
        }

        public LongSamplingCounterArray(com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase) {
            this.mTimeBase = timeBase;
            timeBase.add(this);
        }

        private void writeToParcel(android.os.Parcel parcel) {
            parcel.writeLongArray(this.mCounts);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public void onTimeStarted(long j, long j2, long j3) {
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public void onTimeStopped(long j, long j2, long j3) {
        }

        public long[] getCountsLocked(int i) {
            if (this.mCounts == null) {
                return null;
            }
            return java.util.Arrays.copyOf(this.mCounts, this.mCounts.length);
        }

        public void logState(android.util.Printer printer, java.lang.String str) {
            printer.println(str + "mCounts=" + java.util.Arrays.toString(this.mCounts));
        }

        public void addCountLocked(long[] jArr) {
            addCountLocked(jArr, this.mTimeBase.isRunning());
        }

        public void addCountLocked(long[] jArr, boolean z) {
            if (jArr != null && z) {
                if (this.mCounts == null) {
                    this.mCounts = new long[jArr.length];
                }
                for (int i = 0; i < jArr.length; i++) {
                    long[] jArr2 = this.mCounts;
                    jArr2[i] = jArr2[i] + jArr[i];
                }
            }
        }

        public int getSize() {
            if (this.mCounts == null) {
                return 0;
            }
            return this.mCounts.length;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public boolean reset(boolean z, long j) {
            if (this.mCounts != null) {
                java.util.Arrays.fill(this.mCounts, 0L);
            }
            if (z) {
                detach();
                return true;
            }
            return true;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public void detach() {
            this.mTimeBase.remove(this);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void writeSummaryToParcelLocked(android.os.Parcel parcel) {
            parcel.writeLongArray(this.mCounts);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void readSummaryFromParcelLocked(android.os.Parcel parcel) {
            this.mCounts = parcel.createLongArray();
        }

        public static void writeToParcel(android.os.Parcel parcel, com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounterArray longSamplingCounterArray) {
            if (longSamplingCounterArray != null) {
                parcel.writeInt(1);
                longSamplingCounterArray.writeToParcel(parcel);
            } else {
                parcel.writeInt(0);
            }
        }

        public static com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounterArray readFromParcel(android.os.Parcel parcel, com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase) {
            if (parcel.readInt() != 0) {
                return new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounterArray(timeBase, parcel);
            }
            return null;
        }

        public static void writeSummaryToParcelLocked(android.os.Parcel parcel, com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounterArray longSamplingCounterArray) {
            if (longSamplingCounterArray != null) {
                parcel.writeInt(1);
                longSamplingCounterArray.writeSummaryToParcelLocked(parcel);
            } else {
                parcel.writeInt(0);
            }
        }

        public static com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounterArray readSummaryFromParcelLocked(android.os.Parcel parcel, com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase) {
            if (parcel.readInt() != 0) {
                com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounterArray longSamplingCounterArray = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounterArray(timeBase);
                longSamplingCounterArray.readSummaryFromParcelLocked(parcel);
                return longSamplingCounterArray;
            }
            return null;
        }
    }

    private static class TimeMultiStateCounter extends android.os.BatteryStats.LongCounter implements com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs {
        private final com.android.internal.os.LongMultiStateCounter mCounter;
        private final com.android.server.power.stats.BatteryStatsImpl.TimeBase mTimeBase;

        private TimeMultiStateCounter(com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase, int i, long j) {
            this(timeBase, new com.android.internal.os.LongMultiStateCounter(i), j);
        }

        private TimeMultiStateCounter(com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase, com.android.internal.os.LongMultiStateCounter longMultiStateCounter, long j) {
            this.mTimeBase = timeBase;
            this.mCounter = longMultiStateCounter;
            this.mCounter.setEnabled(this.mTimeBase.isRunning(), j);
            timeBase.add(this);
        }

        /* JADX INFO: Access modifiers changed from: private */
        @android.annotation.Nullable
        public static com.android.server.power.stats.BatteryStatsImpl.TimeMultiStateCounter readFromParcel(android.os.Parcel parcel, com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase, int i, long j) {
            com.android.internal.os.LongMultiStateCounter longMultiStateCounter = (com.android.internal.os.LongMultiStateCounter) com.android.internal.os.LongMultiStateCounter.CREATOR.createFromParcel(parcel);
            if (longMultiStateCounter.getStateCount() != i) {
                return null;
            }
            return new com.android.server.power.stats.BatteryStatsImpl.TimeMultiStateCounter(timeBase, longMultiStateCounter, j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void writeToParcel(android.os.Parcel parcel) {
            this.mCounter.writeToParcel(parcel, 0);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public void onTimeStarted(long j, long j2, long j3) {
            this.mCounter.setEnabled(true, j / 1000);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public void onTimeStopped(long j, long j2, long j3) {
            this.mCounter.setEnabled(false, j / 1000);
        }

        public int getStateCount() {
            return this.mCounter.getStateCount();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setState(int i, long j) {
            this.mCounter.setState(i, j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long update(long j, long j2) {
            return this.mCounter.updateValue(j, j2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void increment(long j, long j2) {
            this.mCounter.incrementValue(j, j2);
        }

        public long getCountForProcessState(int i) {
            return this.mCounter.getCount(i);
        }

        public long getTotalCountLocked() {
            return this.mCounter.getTotalCount();
        }

        public long getCountLocked(int i) {
            return getTotalCountLocked();
        }

        public void logState(android.util.Printer printer, java.lang.String str) {
            printer.println(str + "mCounter=" + this.mCounter);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public boolean reset(boolean z, long j) {
            this.mCounter.reset();
            if (z) {
                detach();
                return true;
            }
            return true;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public void detach() {
            this.mTimeBase.remove(this);
        }
    }

    private static class TimeInFreqMultiStateCounter implements com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs {
        private final com.android.internal.os.LongArrayMultiStateCounter mCounter;
        private final com.android.server.power.stats.BatteryStatsImpl.TimeBase mTimeBase;

        private TimeInFreqMultiStateCounter(com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase, int i, int i2, long j) {
            this(timeBase, new com.android.internal.os.LongArrayMultiStateCounter(i, i2), j);
        }

        private TimeInFreqMultiStateCounter(com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase, com.android.internal.os.LongArrayMultiStateCounter longArrayMultiStateCounter, long j) {
            this.mTimeBase = timeBase;
            this.mCounter = longArrayMultiStateCounter;
            this.mCounter.setEnabled(this.mTimeBase.isRunning(), j);
            timeBase.add(this);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void writeToParcel(android.os.Parcel parcel) {
            this.mCounter.writeToParcel(parcel, 0);
        }

        /* JADX INFO: Access modifiers changed from: private */
        @android.annotation.Nullable
        public static com.android.server.power.stats.BatteryStatsImpl.TimeInFreqMultiStateCounter readFromParcel(android.os.Parcel parcel, com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase, int i, int i2, long j) {
            com.android.internal.os.LongArrayMultiStateCounter longArrayMultiStateCounter = (com.android.internal.os.LongArrayMultiStateCounter) com.android.internal.os.LongArrayMultiStateCounter.CREATOR.createFromParcel(parcel);
            if (longArrayMultiStateCounter.getStateCount() != i || longArrayMultiStateCounter.getArrayLength() != i2) {
                return null;
            }
            return new com.android.server.power.stats.BatteryStatsImpl.TimeInFreqMultiStateCounter(timeBase, longArrayMultiStateCounter, j);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public void onTimeStarted(long j, long j2, long j3) {
            this.mCounter.setEnabled(true, j / 1000);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public void onTimeStopped(long j, long j2, long j3) {
            this.mCounter.setEnabled(false, j / 1000);
        }

        public com.android.internal.os.LongArrayMultiStateCounter getCounter() {
            return this.mCounter;
        }

        public int getStateCount() {
            return this.mCounter.getStateCount();
        }

        public void setTrackingEnabled(boolean z, long j) {
            this.mCounter.setEnabled(z && this.mTimeBase.isRunning(), j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setState(int i, long j) {
            this.mCounter.setState(i, j);
        }

        public boolean getCountsLocked(long[] jArr, int i) {
            if (jArr.length != this.mCounter.getArrayLength()) {
                return false;
            }
            this.mCounter.getCounts(jArr, i);
            for (int length = jArr.length - 1; length >= 0; length--) {
                if (jArr[length] != 0) {
                    return true;
                }
            }
            return false;
        }

        public void logState(android.util.Printer printer, java.lang.String str) {
            printer.println(str + "mCounter=" + this.mCounter);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public boolean reset(boolean z, long j) {
            this.mCounter.reset();
            if (z) {
                detach();
                return true;
            }
            return true;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public void detach() {
            this.mTimeBase.remove(this);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class LongSamplingCounter extends android.os.BatteryStats.LongCounter implements com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs {
        private long mCount;
        final com.android.server.power.stats.BatteryStatsImpl.TimeBase mTimeBase;

        public LongSamplingCounter(com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase, android.os.Parcel parcel) {
            this.mTimeBase = timeBase;
            this.mCount = parcel.readLong();
            timeBase.add(this);
        }

        public LongSamplingCounter(com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase) {
            this.mTimeBase = timeBase;
            timeBase.add(this);
        }

        public void writeToParcel(android.os.Parcel parcel) {
            parcel.writeLong(this.mCount);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public void onTimeStarted(long j, long j2, long j3) {
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public void onTimeStopped(long j, long j2, long j3) {
        }

        public long getCountLocked(int i) {
            return this.mCount;
        }

        public long getCountForProcessState(int i) {
            if (i == 0) {
                return getCountLocked(0);
            }
            return 0L;
        }

        public void logState(android.util.Printer printer, java.lang.String str) {
            printer.println(str + "mCount=" + this.mCount);
        }

        public void addCountLocked(long j) {
            addCountLocked(j, this.mTimeBase.isRunning());
        }

        public void addCountLocked(long j, boolean z) {
            if (z) {
                this.mCount += j;
            }
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public boolean reset(boolean z, long j) {
            this.mCount = 0L;
            if (z) {
                detach();
                return true;
            }
            return true;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public void detach() {
            this.mTimeBase.remove(this);
        }

        public void writeSummaryFromParcelLocked(android.os.Parcel parcel) {
            parcel.writeLong(this.mCount);
        }

        public void readSummaryFromParcelLocked(android.os.Parcel parcel) {
            this.mCount = parcel.readLong();
        }
    }

    public static abstract class Timer extends android.os.BatteryStats.Timer implements com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs {
        protected final com.android.internal.os.Clock mClock;
        protected int mCount;
        protected final com.android.server.power.stats.BatteryStatsImpl.TimeBase mTimeBase;
        protected long mTimeBeforeMarkUs;
        protected long mTotalTimeUs;
        protected final int mType;

        protected abstract int computeCurrentCountLocked();

        protected abstract long computeRunTimeLocked(long j, long j2);

        public Timer(com.android.internal.os.Clock clock, int i, com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase, android.os.Parcel parcel) {
            this.mClock = clock;
            this.mType = i;
            this.mTimeBase = timeBase;
            this.mCount = parcel.readInt();
            this.mTotalTimeUs = parcel.readLong();
            this.mTimeBeforeMarkUs = parcel.readLong();
            timeBase.add(this);
        }

        public Timer(com.android.internal.os.Clock clock, int i, com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase) {
            this.mClock = clock;
            this.mType = i;
            this.mTimeBase = timeBase;
            timeBase.add(this);
        }

        public void writeToParcel(android.os.Parcel parcel, long j) {
            parcel.writeInt(computeCurrentCountLocked());
            parcel.writeLong(computeRunTimeLocked(this.mTimeBase.getRealtime(j), j));
            parcel.writeLong(this.mTimeBeforeMarkUs);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public boolean reset(boolean z) {
            return reset(z, this.mClock.elapsedRealtime() * 1000);
        }

        public boolean reset(boolean z, long j) {
            this.mTimeBeforeMarkUs = 0L;
            this.mTotalTimeUs = 0L;
            this.mCount = 0;
            if (z) {
                detach();
                return true;
            }
            return true;
        }

        public void detach() {
            this.mTimeBase.remove(this);
        }

        public void onTimeStarted(long j, long j2, long j3) {
        }

        public void onTimeStopped(long j, long j2, long j3) {
            this.mTotalTimeUs = computeRunTimeLocked(j3, j);
            this.mCount = computeCurrentCountLocked();
        }

        public static void writeTimerToParcel(android.os.Parcel parcel, com.android.server.power.stats.BatteryStatsImpl.Timer timer, long j) {
            if (timer == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                timer.writeToParcel(parcel, j);
            }
        }

        public long getTotalTimeLocked(long j, int i) {
            return computeRunTimeLocked(this.mTimeBase.getRealtime(j), j);
        }

        public int getCountLocked(int i) {
            return computeCurrentCountLocked();
        }

        public long getTimeSinceMarkLocked(long j) {
            return computeRunTimeLocked(this.mTimeBase.getRealtime(j), j) - this.mTimeBeforeMarkUs;
        }

        public void logState(android.util.Printer printer, java.lang.String str) {
            printer.println(str + "mCount=" + this.mCount);
            printer.println(str + "mTotalTime=" + this.mTotalTimeUs);
        }

        public void writeSummaryFromParcelLocked(android.os.Parcel parcel, long j) {
            parcel.writeLong(computeRunTimeLocked(this.mTimeBase.getRealtime(j), j));
            parcel.writeInt(computeCurrentCountLocked());
        }

        public void readSummaryFromParcelLocked(android.os.Parcel parcel) {
            this.mTotalTimeUs = parcel.readLong();
            this.mCount = parcel.readInt();
            this.mTimeBeforeMarkUs = this.mTotalTimeUs;
        }
    }

    public static class SamplingTimer extends com.android.server.power.stats.BatteryStatsImpl.Timer {
        int mBaseReportedCount;
        long mBaseReportedTotalTimeUs;
        int mCurrentReportedCount;
        long mCurrentReportedTotalTimeUs;
        boolean mTimeBaseRunning;
        boolean mTrackingReportedValues;
        int mUpdateVersion;

        @com.android.internal.annotations.VisibleForTesting
        public SamplingTimer(com.android.internal.os.Clock clock, com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase, android.os.Parcel parcel) {
            super(clock, 0, timeBase, parcel);
            this.mCurrentReportedCount = parcel.readInt();
            this.mBaseReportedCount = parcel.readInt();
            this.mCurrentReportedTotalTimeUs = parcel.readLong();
            this.mBaseReportedTotalTimeUs = parcel.readLong();
            this.mTrackingReportedValues = parcel.readInt() == 1;
            this.mTimeBaseRunning = timeBase.isRunning();
        }

        @com.android.internal.annotations.VisibleForTesting
        public SamplingTimer(com.android.internal.os.Clock clock, com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase) {
            super(clock, 0, timeBase);
            this.mTrackingReportedValues = false;
            this.mTimeBaseRunning = timeBase.isRunning();
        }

        public void endSample() {
            endSample(this.mClock.elapsedRealtime() * 1000);
        }

        public void endSample(long j) {
            this.mTotalTimeUs = computeRunTimeLocked(0L, j);
            this.mCount = computeCurrentCountLocked();
            this.mCurrentReportedTotalTimeUs = 0L;
            this.mBaseReportedTotalTimeUs = 0L;
            this.mCurrentReportedCount = 0;
            this.mBaseReportedCount = 0;
            this.mTrackingReportedValues = false;
        }

        public void setUpdateVersion(int i) {
            this.mUpdateVersion = i;
        }

        public int getUpdateVersion() {
            return this.mUpdateVersion;
        }

        public void update(long j, int i, long j2) {
            update(j, 0L, i, j2);
        }

        public void update(long j, long j2, int i, long j3) {
            if (this.mTimeBaseRunning && !this.mTrackingReportedValues) {
                this.mBaseReportedTotalTimeUs = j - j2;
                this.mBaseReportedCount = j2 == 0 ? i : i - 1;
            }
            this.mTrackingReportedValues = true;
            if (j < this.mCurrentReportedTotalTimeUs || i < this.mCurrentReportedCount) {
                endSample(j3);
            }
            this.mCurrentReportedTotalTimeUs = j;
            this.mCurrentReportedCount = i;
        }

        public void add(long j, int i) {
            add(j, i, this.mClock.elapsedRealtime() * 1000);
        }

        public void add(long j, int i, long j2) {
            update(this.mCurrentReportedTotalTimeUs + j, this.mCurrentReportedCount + i, j2);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer, com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public void onTimeStarted(long j, long j2, long j3) {
            super.onTimeStarted(j, j2, j3);
            if (this.mTrackingReportedValues) {
                this.mBaseReportedTotalTimeUs = this.mCurrentReportedTotalTimeUs;
                this.mBaseReportedCount = this.mCurrentReportedCount;
            }
            this.mTimeBaseRunning = true;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer, com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public void onTimeStopped(long j, long j2, long j3) {
            super.onTimeStopped(j, j2, j3);
            this.mTimeBaseRunning = false;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer
        public void logState(android.util.Printer printer, java.lang.String str) {
            super.logState(printer, str);
            printer.println(str + "mCurrentReportedCount=" + this.mCurrentReportedCount + " mBaseReportedCount=" + this.mBaseReportedCount + " mCurrentReportedTotalTime=" + this.mCurrentReportedTotalTimeUs + " mBaseReportedTotalTimeUs=" + this.mBaseReportedTotalTimeUs);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer
        protected long computeRunTimeLocked(long j, long j2) {
            return this.mTotalTimeUs + ((this.mTimeBaseRunning && this.mTrackingReportedValues) ? this.mCurrentReportedTotalTimeUs - this.mBaseReportedTotalTimeUs : 0L);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer
        protected int computeCurrentCountLocked() {
            return this.mCount + ((this.mTimeBaseRunning && this.mTrackingReportedValues) ? this.mCurrentReportedCount - this.mBaseReportedCount : 0);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer
        public void writeToParcel(android.os.Parcel parcel, long j) {
            super.writeToParcel(parcel, j);
            parcel.writeInt(this.mCurrentReportedCount);
            parcel.writeInt(this.mBaseReportedCount);
            parcel.writeLong(this.mCurrentReportedTotalTimeUs);
            parcel.writeLong(this.mBaseReportedTotalTimeUs);
            parcel.writeInt(this.mTrackingReportedValues ? 1 : 0);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer, com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public boolean reset(boolean z, long j) {
            super.reset(z, j);
            this.mTrackingReportedValues = false;
            this.mBaseReportedTotalTimeUs = 0L;
            this.mBaseReportedCount = 0;
            return true;
        }
    }

    public static class BatchTimer extends com.android.server.power.stats.BatteryStatsImpl.Timer {
        boolean mInDischarge;
        long mLastAddedDurationUs;
        long mLastAddedTimeUs;
        final com.android.server.power.stats.BatteryStatsImpl.Uid mUid;

        BatchTimer(com.android.internal.os.Clock clock, com.android.server.power.stats.BatteryStatsImpl.Uid uid, int i, com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase, android.os.Parcel parcel) {
            super(clock, i, timeBase, parcel);
            this.mUid = uid;
            this.mLastAddedTimeUs = parcel.readLong();
            this.mLastAddedDurationUs = parcel.readLong();
            this.mInDischarge = timeBase.isRunning();
        }

        BatchTimer(com.android.internal.os.Clock clock, com.android.server.power.stats.BatteryStatsImpl.Uid uid, int i, com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase) {
            super(clock, i, timeBase);
            this.mUid = uid;
            this.mInDischarge = timeBase.isRunning();
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer
        public void writeToParcel(android.os.Parcel parcel, long j) {
            super.writeToParcel(parcel, j);
            parcel.writeLong(this.mLastAddedTimeUs);
            parcel.writeLong(this.mLastAddedDurationUs);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer, com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public void onTimeStopped(long j, long j2, long j3) {
            recomputeLastDuration(j, false);
            this.mInDischarge = false;
            super.onTimeStopped(j, j2, j3);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer, com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public void onTimeStarted(long j, long j2, long j3) {
            recomputeLastDuration(j, false);
            this.mInDischarge = true;
            if (this.mLastAddedTimeUs == j) {
                this.mTotalTimeUs += this.mLastAddedDurationUs;
            }
            super.onTimeStarted(j, j2, j3);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer
        public void logState(android.util.Printer printer, java.lang.String str) {
            super.logState(printer, str);
            printer.println(str + "mLastAddedTime=" + this.mLastAddedTimeUs + " mLastAddedDuration=" + this.mLastAddedDurationUs);
        }

        private long computeOverage(long j) {
            if (this.mLastAddedTimeUs > 0) {
                return this.mLastAddedDurationUs - j;
            }
            return 0L;
        }

        private void recomputeLastDuration(long j, boolean z) {
            long computeOverage = computeOverage(j);
            if (computeOverage > 0) {
                if (this.mInDischarge) {
                    this.mTotalTimeUs -= computeOverage;
                }
                if (z) {
                    this.mLastAddedTimeUs = 0L;
                } else {
                    this.mLastAddedTimeUs = j;
                    this.mLastAddedDurationUs -= computeOverage;
                }
            }
        }

        public void addDuration(long j, long j2) {
            long j3 = j2 * 1000;
            recomputeLastDuration(j3, true);
            this.mLastAddedTimeUs = j3;
            this.mLastAddedDurationUs = j * 1000;
            if (this.mInDischarge) {
                this.mTotalTimeUs += this.mLastAddedDurationUs;
                this.mCount++;
            }
        }

        public void abortLastDuration(long j) {
            recomputeLastDuration(j * 1000, true);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer
        protected int computeCurrentCountLocked() {
            return this.mCount;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer
        protected long computeRunTimeLocked(long j, long j2) {
            long computeOverage = computeOverage(j2);
            if (computeOverage > 0) {
                this.mTotalTimeUs = computeOverage;
                return computeOverage;
            }
            return this.mTotalTimeUs;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer, com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public boolean reset(boolean z, long j) {
            recomputeLastDuration(j, true);
            boolean z2 = false;
            boolean z3 = this.mLastAddedTimeUs == j;
            if (!z3 && z) {
                z2 = true;
            }
            super.reset(z2, j);
            return !z3;
        }
    }

    public static class DurationTimer extends com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer {
        long mCurrentDurationMs;
        long mMaxDurationMs;
        long mStartTimeMs;
        long mTotalDurationMs;

        public DurationTimer(com.android.internal.os.Clock clock, com.android.server.power.stats.BatteryStatsImpl.Uid uid, int i, java.util.ArrayList<com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer> arrayList, com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase, android.os.Parcel parcel) {
            super(clock, uid, i, arrayList, timeBase, parcel);
            this.mStartTimeMs = -1L;
            this.mMaxDurationMs = parcel.readLong();
            this.mTotalDurationMs = parcel.readLong();
            this.mCurrentDurationMs = parcel.readLong();
        }

        public DurationTimer(com.android.internal.os.Clock clock, com.android.server.power.stats.BatteryStatsImpl.Uid uid, int i, java.util.ArrayList<com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer> arrayList, com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase) {
            super(clock, uid, i, arrayList, timeBase);
            this.mStartTimeMs = -1L;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer, com.android.server.power.stats.BatteryStatsImpl.Timer
        public void writeToParcel(android.os.Parcel parcel, long j) {
            super.writeToParcel(parcel, j);
            long j2 = j / 1000;
            parcel.writeLong(getMaxDurationMsLocked(j2));
            parcel.writeLong(this.mTotalDurationMs);
            parcel.writeLong(getCurrentDurationMsLocked(j2));
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer
        public void writeSummaryFromParcelLocked(android.os.Parcel parcel, long j) {
            super.writeSummaryFromParcelLocked(parcel, j);
            long j2 = j / 1000;
            parcel.writeLong(getMaxDurationMsLocked(j2));
            parcel.writeLong(getTotalDurationMsLocked(j2));
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer, com.android.server.power.stats.BatteryStatsImpl.Timer
        public void readSummaryFromParcelLocked(android.os.Parcel parcel) {
            super.readSummaryFromParcelLocked(parcel);
            this.mMaxDurationMs = parcel.readLong();
            this.mTotalDurationMs = parcel.readLong();
            this.mStartTimeMs = -1L;
            this.mCurrentDurationMs = 0L;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer, com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public void onTimeStarted(long j, long j2, long j3) {
            super.onTimeStarted(j, j2, j3);
            if (this.mNesting > 0) {
                this.mStartTimeMs = j3 / 1000;
            }
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer, com.android.server.power.stats.BatteryStatsImpl.Timer, com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public void onTimeStopped(long j, long j2, long j3) {
            super.onTimeStopped(j, j2, j3);
            if (this.mNesting > 0) {
                this.mCurrentDurationMs += (j3 / 1000) - this.mStartTimeMs;
            }
            this.mStartTimeMs = -1L;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer, com.android.server.power.stats.BatteryStatsImpl.Timer
        public void logState(android.util.Printer printer, java.lang.String str) {
            super.logState(printer, str);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer
        public void startRunningLocked(long j) {
            super.startRunningLocked(j);
            if (this.mNesting == 1 && this.mTimeBase.isRunning()) {
                this.mStartTimeMs = this.mTimeBase.getRealtime(j * 1000) / 1000;
            }
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer
        public void stopRunningLocked(long j) {
            if (this.mNesting == 1) {
                long currentDurationMsLocked = getCurrentDurationMsLocked(j);
                this.mTotalDurationMs += currentDurationMsLocked;
                if (currentDurationMsLocked > this.mMaxDurationMs) {
                    this.mMaxDurationMs = currentDurationMsLocked;
                }
                this.mStartTimeMs = -1L;
                this.mCurrentDurationMs = 0L;
            }
            super.stopRunningLocked(j);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer, com.android.server.power.stats.BatteryStatsImpl.Timer, com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public boolean reset(boolean z, long j) {
            boolean reset = super.reset(z, j);
            this.mMaxDurationMs = 0L;
            this.mTotalDurationMs = 0L;
            this.mCurrentDurationMs = 0L;
            if (this.mNesting > 0) {
                this.mStartTimeMs = this.mTimeBase.getRealtime(j) / 1000;
            } else {
                this.mStartTimeMs = -1L;
            }
            return reset;
        }

        public long getMaxDurationMsLocked(long j) {
            if (this.mNesting > 0) {
                long currentDurationMsLocked = getCurrentDurationMsLocked(j);
                if (currentDurationMsLocked > this.mMaxDurationMs) {
                    return currentDurationMsLocked;
                }
            }
            return this.mMaxDurationMs;
        }

        public long getCurrentDurationMsLocked(long j) {
            long j2 = this.mCurrentDurationMs;
            if (this.mNesting > 0 && this.mTimeBase.isRunning()) {
                return j2 + ((this.mTimeBase.getRealtime(j * 1000) / 1000) - this.mStartTimeMs);
            }
            return j2;
        }

        public long getTotalDurationMsLocked(long j) {
            return this.mTotalDurationMs + getCurrentDurationMsLocked(j);
        }
    }

    public static class StopwatchTimer extends com.android.server.power.stats.BatteryStatsImpl.Timer {
        long mAcquireTimeUs;

        @com.android.internal.annotations.VisibleForTesting
        public boolean mInList;
        int mNesting;
        long mTimeoutUs;
        final java.util.ArrayList<com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer> mTimerPool;
        final com.android.server.power.stats.BatteryStatsImpl.Uid mUid;
        long mUpdateTimeUs;

        public StopwatchTimer(com.android.internal.os.Clock clock, com.android.server.power.stats.BatteryStatsImpl.Uid uid, int i, java.util.ArrayList<com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer> arrayList, com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase, android.os.Parcel parcel) {
            super(clock, i, timeBase, parcel);
            this.mAcquireTimeUs = -1L;
            this.mUid = uid;
            this.mTimerPool = arrayList;
            this.mUpdateTimeUs = parcel.readLong();
        }

        public StopwatchTimer(com.android.internal.os.Clock clock, com.android.server.power.stats.BatteryStatsImpl.Uid uid, int i, java.util.ArrayList<com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer> arrayList, com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase) {
            super(clock, i, timeBase);
            this.mAcquireTimeUs = -1L;
            this.mUid = uid;
            this.mTimerPool = arrayList;
        }

        public void setTimeout(long j) {
            this.mTimeoutUs = j;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer
        public void writeToParcel(android.os.Parcel parcel, long j) {
            super.writeToParcel(parcel, j);
            parcel.writeLong(this.mUpdateTimeUs);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer, com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public void onTimeStopped(long j, long j2, long j3) {
            if (this.mNesting > 0) {
                super.onTimeStopped(j, j2, j3);
                this.mUpdateTimeUs = j3;
            }
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer
        public void logState(android.util.Printer printer, java.lang.String str) {
            super.logState(printer, str);
            printer.println(str + "mNesting=" + this.mNesting + " mUpdateTime=" + this.mUpdateTimeUs + " mAcquireTime=" + this.mAcquireTimeUs);
        }

        public void startRunningLocked(long j) {
            int i = this.mNesting;
            this.mNesting = i + 1;
            if (i == 0) {
                long realtime = this.mTimeBase.getRealtime(j * 1000);
                this.mUpdateTimeUs = realtime;
                if (this.mTimerPool != null) {
                    refreshTimersLocked(realtime, this.mTimerPool, null);
                    this.mTimerPool.add(this);
                }
                if (this.mTimeBase.isRunning()) {
                    this.mCount++;
                    this.mAcquireTimeUs = this.mTotalTimeUs;
                } else {
                    this.mAcquireTimeUs = -1L;
                }
            }
        }

        public boolean isRunningLocked() {
            return this.mNesting > 0;
        }

        public void stopRunningLocked(long j) {
            if (this.mNesting == 0) {
                return;
            }
            int i = this.mNesting - 1;
            this.mNesting = i;
            if (i == 0) {
                long j2 = j * 1000;
                long realtime = this.mTimeBase.getRealtime(j2);
                if (this.mTimerPool != null) {
                    refreshTimersLocked(realtime, this.mTimerPool, null);
                    this.mTimerPool.remove(this);
                } else {
                    this.mNesting = 1;
                    this.mTotalTimeUs = computeRunTimeLocked(realtime, j2);
                    this.mNesting = 0;
                }
                if (this.mAcquireTimeUs >= 0 && this.mTotalTimeUs == this.mAcquireTimeUs) {
                    this.mCount--;
                }
            }
        }

        public void stopAllRunningLocked(long j) {
            if (this.mNesting > 0) {
                this.mNesting = 1;
                stopRunningLocked(j);
            }
        }

        private static long refreshTimersLocked(long j, java.util.ArrayList<com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer> arrayList, com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer stopwatchTimer) {
            int size = arrayList.size();
            long j2 = 0;
            for (int i = size - 1; i >= 0; i--) {
                com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer stopwatchTimer2 = arrayList.get(i);
                long j3 = j - stopwatchTimer2.mUpdateTimeUs;
                if (j3 > 0) {
                    long j4 = j3 / size;
                    if (stopwatchTimer2 == stopwatchTimer) {
                        j2 = j4;
                    }
                    stopwatchTimer2.mTotalTimeUs += j4;
                }
                stopwatchTimer2.mUpdateTimeUs = j;
            }
            return j2;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer
        protected long computeRunTimeLocked(long j, long j2) {
            long j3 = 0;
            if (this.mTimeoutUs > 0 && j > this.mUpdateTimeUs + this.mTimeoutUs) {
                j = this.mUpdateTimeUs + this.mTimeoutUs;
            }
            long j4 = this.mTotalTimeUs;
            if (this.mNesting > 0) {
                j3 = (j - this.mUpdateTimeUs) / (this.mTimerPool != null ? this.mTimerPool.size() : 1);
            }
            return j4 + j3;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer
        protected int computeCurrentCountLocked() {
            return this.mCount;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer, com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public boolean reset(boolean z, long j) {
            boolean z2 = false;
            boolean z3 = this.mNesting <= 0;
            if (z3 && z) {
                z2 = true;
            }
            super.reset(z2, j);
            if (this.mNesting > 0) {
                this.mUpdateTimeUs = this.mTimeBase.getRealtime(j);
            }
            this.mAcquireTimeUs = -1L;
            return z3;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer, com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public void detach() {
            super.detach();
            if (this.mTimerPool != null) {
                this.mTimerPool.remove(this);
            }
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.Timer
        public void readSummaryFromParcelLocked(android.os.Parcel parcel) {
            super.readSummaryFromParcelLocked(parcel);
            this.mNesting = 0;
        }

        public void setMark(long j) {
            long realtime = this.mTimeBase.getRealtime(j * 1000);
            if (this.mNesting > 0) {
                if (this.mTimerPool != null) {
                    refreshTimersLocked(realtime, this.mTimerPool, this);
                } else {
                    this.mTotalTimeUs += realtime - this.mUpdateTimeUs;
                    this.mUpdateTimeUs = realtime;
                }
            }
            this.mTimeBeforeMarkUs = this.mTotalTimeUs;
        }
    }

    public static class DualTimer extends com.android.server.power.stats.BatteryStatsImpl.DurationTimer {
        private final com.android.server.power.stats.BatteryStatsImpl.DurationTimer mSubTimer;

        public DualTimer(com.android.internal.os.Clock clock, com.android.server.power.stats.BatteryStatsImpl.Uid uid, int i, java.util.ArrayList<com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer> arrayList, com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase, com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase2, android.os.Parcel parcel) {
            super(clock, uid, i, arrayList, timeBase, parcel);
            this.mSubTimer = new com.android.server.power.stats.BatteryStatsImpl.DurationTimer(clock, uid, i, null, timeBase2, parcel);
        }

        public DualTimer(com.android.internal.os.Clock clock, com.android.server.power.stats.BatteryStatsImpl.Uid uid, int i, java.util.ArrayList<com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer> arrayList, com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase, com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase2) {
            super(clock, uid, i, arrayList, timeBase);
            this.mSubTimer = new com.android.server.power.stats.BatteryStatsImpl.DurationTimer(clock, uid, i, null, timeBase2);
        }

        public com.android.server.power.stats.BatteryStatsImpl.DurationTimer getSubTimer() {
            return this.mSubTimer;
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.DurationTimer, com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer
        public void startRunningLocked(long j) {
            super.startRunningLocked(j);
            this.mSubTimer.startRunningLocked(j);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.DurationTimer, com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer
        public void stopRunningLocked(long j) {
            super.stopRunningLocked(j);
            this.mSubTimer.stopRunningLocked(j);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer
        public void stopAllRunningLocked(long j) {
            super.stopAllRunningLocked(j);
            this.mSubTimer.stopAllRunningLocked(j);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.DurationTimer, com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer, com.android.server.power.stats.BatteryStatsImpl.Timer, com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public boolean reset(boolean z, long j) {
            return !((!super.reset(z, j)) | (!this.mSubTimer.reset(false, j)) | false);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer, com.android.server.power.stats.BatteryStatsImpl.Timer, com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
        public void detach() {
            this.mSubTimer.detach();
            super.detach();
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.DurationTimer, com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer, com.android.server.power.stats.BatteryStatsImpl.Timer
        public void writeToParcel(android.os.Parcel parcel, long j) {
            super.writeToParcel(parcel, j);
            this.mSubTimer.writeToParcel(parcel, j);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.DurationTimer, com.android.server.power.stats.BatteryStatsImpl.Timer
        public void writeSummaryFromParcelLocked(android.os.Parcel parcel, long j) {
            super.writeSummaryFromParcelLocked(parcel, j);
            this.mSubTimer.writeSummaryFromParcelLocked(parcel, j);
        }

        @Override // com.android.server.power.stats.BatteryStatsImpl.DurationTimer, com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer, com.android.server.power.stats.BatteryStatsImpl.Timer
        public void readSummaryFromParcelLocked(android.os.Parcel parcel) {
            super.readSummaryFromParcelLocked(parcel);
            this.mSubTimer.readSummaryFromParcelLocked(parcel);
        }
    }

    public abstract class OverflowArrayMap<T> {
        private static final java.lang.String OVERFLOW_NAME = "*overflow*";
        android.util.ArrayMap<java.lang.String, android.util.MutableInt> mActiveOverflow;
        T mCurOverflow;
        long mLastCleanupTimeMs;
        long mLastClearTimeMs;
        long mLastOverflowFinishTimeMs;
        long mLastOverflowTimeMs;
        final android.util.ArrayMap<java.lang.String, T> mMap = new android.util.ArrayMap<>();
        final int mUid;

        public abstract T instantiateObject();

        public OverflowArrayMap(int i) {
            this.mUid = i;
        }

        public android.util.ArrayMap<java.lang.String, T> getMap() {
            return this.mMap;
        }

        public void clear() {
            this.mLastClearTimeMs = android.os.SystemClock.elapsedRealtime();
            this.mMap.clear();
            this.mCurOverflow = null;
            this.mActiveOverflow = null;
        }

        public void add(java.lang.String str, T t) {
            if (str == null) {
                str = "";
            }
            this.mMap.put(str, t);
            if (OVERFLOW_NAME.equals(str)) {
                this.mCurOverflow = t;
            }
        }

        public void cleanup(long j) {
            this.mLastCleanupTimeMs = j;
            if (this.mActiveOverflow != null && this.mActiveOverflow.size() == 0) {
                this.mActiveOverflow = null;
            }
            if (this.mActiveOverflow == null) {
                if (this.mMap.containsKey(OVERFLOW_NAME)) {
                    android.util.Slog.wtf(com.android.server.power.stats.BatteryStatsImpl.TAG, "Cleaning up with no active overflow, but have overflow entry " + this.mMap.get(OVERFLOW_NAME));
                    this.mMap.remove(OVERFLOW_NAME);
                }
                this.mCurOverflow = null;
                return;
            }
            if (this.mCurOverflow == null || !this.mMap.containsKey(OVERFLOW_NAME)) {
                android.util.Slog.wtf(com.android.server.power.stats.BatteryStatsImpl.TAG, "Cleaning up with active overflow, but no overflow entry: cur=" + this.mCurOverflow + " map=" + this.mMap.get(OVERFLOW_NAME));
            }
        }

        public T startObject(java.lang.String str, long j) {
            android.util.MutableInt mutableInt;
            if (str == null) {
                str = "";
            }
            T t = this.mMap.get(str);
            if (t != null) {
                return t;
            }
            if (this.mActiveOverflow != null && (mutableInt = this.mActiveOverflow.get(str)) != null) {
                T t2 = this.mCurOverflow;
                if (t2 == null) {
                    android.util.Slog.wtf(com.android.server.power.stats.BatteryStatsImpl.TAG, "Have active overflow " + str + " but null overflow");
                    t2 = instantiateObject();
                    this.mCurOverflow = t2;
                    this.mMap.put(OVERFLOW_NAME, t2);
                }
                mutableInt.value++;
                return t2;
            }
            if (this.mMap.size() >= com.android.server.power.stats.BatteryStatsImpl.MAX_WAKELOCKS_PER_UID) {
                T t3 = this.mCurOverflow;
                if (t3 == null) {
                    t3 = instantiateObject();
                    this.mCurOverflow = t3;
                    this.mMap.put(OVERFLOW_NAME, t3);
                }
                if (this.mActiveOverflow == null) {
                    this.mActiveOverflow = new android.util.ArrayMap<>();
                }
                this.mActiveOverflow.put(str, new android.util.MutableInt(1));
                this.mLastOverflowTimeMs = j;
                return t3;
            }
            T instantiateObject = instantiateObject();
            this.mMap.put(str, instantiateObject);
            return instantiateObject;
        }

        public T stopObject(java.lang.String str, long j) {
            android.util.MutableInt mutableInt;
            T t;
            if (str == null) {
                str = "";
            }
            T t2 = this.mMap.get(str);
            if (t2 != null) {
                return t2;
            }
            if (this.mActiveOverflow != null && (mutableInt = this.mActiveOverflow.get(str)) != null && (t = this.mCurOverflow) != null) {
                mutableInt.value--;
                if (mutableInt.value <= 0) {
                    this.mActiveOverflow.remove(str);
                    this.mLastOverflowFinishTimeMs = j;
                }
                return t;
            }
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Unable to find object for ");
            sb.append(str);
            sb.append(" in uid ");
            sb.append(this.mUid);
            sb.append(" mapsize=");
            sb.append(this.mMap.size());
            sb.append(" activeoverflow=");
            sb.append(this.mActiveOverflow);
            sb.append(" curoverflow=");
            sb.append(this.mCurOverflow);
            if (this.mLastOverflowTimeMs != 0) {
                sb.append(" lastOverflowTime=");
                android.util.TimeUtils.formatDuration(this.mLastOverflowTimeMs - j, sb);
            }
            if (this.mLastOverflowFinishTimeMs != 0) {
                sb.append(" lastOverflowFinishTime=");
                android.util.TimeUtils.formatDuration(this.mLastOverflowFinishTimeMs - j, sb);
            }
            if (this.mLastClearTimeMs != 0) {
                sb.append(" lastClearTime=");
                android.util.TimeUtils.formatDuration(this.mLastClearTimeMs - j, sb);
            }
            if (this.mLastCleanupTimeMs != 0) {
                sb.append(" lastCleanupTime=");
                android.util.TimeUtils.formatDuration(this.mLastCleanupTimeMs - j, sb);
            }
            android.util.Slog.wtf(com.android.server.power.stats.BatteryStatsImpl.TAG, sb.toString());
            return null;
        }
    }

    public static class ControllerActivityCounterImpl extends android.os.BatteryStats.ControllerActivityCounter implements android.os.Parcelable {
        private final com.android.internal.os.Clock mClock;
        private com.android.server.power.stats.BatteryStatsImpl.TimeMultiStateCounter mIdleTimeMillis;
        private final com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter mMonitoredRailChargeConsumedMaMs;
        private int mNumTxStates;
        private final com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter mPowerDrainMaMs;
        private int mProcessState;
        private com.android.server.power.stats.BatteryStatsImpl.TimeMultiStateCounter mRxTimeMillis;
        private final com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter mScanTimeMillis;
        private final com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter mSleepTimeMillis;
        private final com.android.server.power.stats.BatteryStatsImpl.TimeBase mTimeBase;
        private com.android.server.power.stats.BatteryStatsImpl.TimeMultiStateCounter[] mTxTimeMillis;

        public ControllerActivityCounterImpl(com.android.internal.os.Clock clock, com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase, int i) {
            this.mClock = clock;
            this.mTimeBase = timeBase;
            this.mNumTxStates = i;
            this.mScanTimeMillis = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter(timeBase);
            this.mSleepTimeMillis = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter(timeBase);
            this.mPowerDrainMaMs = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter(timeBase);
            this.mMonitoredRailChargeConsumedMaMs = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter(timeBase);
        }

        public void readSummaryFromParcel(android.os.Parcel parcel) {
            this.mIdleTimeMillis = readTimeMultiStateCounter(parcel, this.mTimeBase);
            this.mScanTimeMillis.readSummaryFromParcelLocked(parcel);
            this.mSleepTimeMillis.readSummaryFromParcelLocked(parcel);
            this.mRxTimeMillis = readTimeMultiStateCounter(parcel, this.mTimeBase);
            this.mTxTimeMillis = readTimeMultiStateCounters(parcel, this.mTimeBase, this.mNumTxStates);
            this.mPowerDrainMaMs.readSummaryFromParcelLocked(parcel);
            this.mMonitoredRailChargeConsumedMaMs.readSummaryFromParcelLocked(parcel);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public void writeSummaryToParcel(android.os.Parcel parcel) {
            writeTimeMultiStateCounter(parcel, this.mIdleTimeMillis);
            this.mScanTimeMillis.writeSummaryFromParcelLocked(parcel);
            this.mSleepTimeMillis.writeSummaryFromParcelLocked(parcel);
            writeTimeMultiStateCounter(parcel, this.mRxTimeMillis);
            writeTimeMultiStateCounters(parcel, this.mTxTimeMillis);
            this.mPowerDrainMaMs.writeSummaryFromParcelLocked(parcel);
            this.mMonitoredRailChargeConsumedMaMs.writeSummaryFromParcelLocked(parcel);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            writeTimeMultiStateCounter(parcel, this.mIdleTimeMillis);
            this.mScanTimeMillis.writeToParcel(parcel);
            this.mSleepTimeMillis.writeToParcel(parcel);
            writeTimeMultiStateCounter(parcel, this.mRxTimeMillis);
            writeTimeMultiStateCounters(parcel, this.mTxTimeMillis);
            this.mPowerDrainMaMs.writeToParcel(parcel);
            this.mMonitoredRailChargeConsumedMaMs.writeToParcel(parcel);
        }

        private com.android.server.power.stats.BatteryStatsImpl.TimeMultiStateCounter readTimeMultiStateCounter(android.os.Parcel parcel, com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase) {
            if (parcel.readBoolean()) {
                return com.android.server.power.stats.BatteryStatsImpl.TimeMultiStateCounter.readFromParcel(parcel, timeBase, 5, this.mClock.elapsedRealtime());
            }
            return null;
        }

        private void writeTimeMultiStateCounter(android.os.Parcel parcel, com.android.server.power.stats.BatteryStatsImpl.TimeMultiStateCounter timeMultiStateCounter) {
            if (timeMultiStateCounter != null) {
                parcel.writeBoolean(true);
                timeMultiStateCounter.writeToParcel(parcel);
            } else {
                parcel.writeBoolean(false);
            }
        }

        private com.android.server.power.stats.BatteryStatsImpl.TimeMultiStateCounter[] readTimeMultiStateCounters(android.os.Parcel parcel, com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase, int i) {
            if (parcel.readBoolean()) {
                int readInt = parcel.readInt();
                boolean z = readInt == i;
                com.android.server.power.stats.BatteryStatsImpl.TimeMultiStateCounter[] timeMultiStateCounterArr = new com.android.server.power.stats.BatteryStatsImpl.TimeMultiStateCounter[readInt];
                for (int i2 = 0; i2 < readInt; i2++) {
                    com.android.server.power.stats.BatteryStatsImpl.TimeMultiStateCounter readFromParcel = com.android.server.power.stats.BatteryStatsImpl.TimeMultiStateCounter.readFromParcel(parcel, timeBase, 5, this.mClock.elapsedRealtime());
                    if (readFromParcel != null) {
                        timeMultiStateCounterArr[i2] = readFromParcel;
                    } else {
                        z = false;
                    }
                }
                if (z) {
                    return timeMultiStateCounterArr;
                }
                return null;
            }
            return null;
        }

        private void writeTimeMultiStateCounters(android.os.Parcel parcel, com.android.server.power.stats.BatteryStatsImpl.TimeMultiStateCounter[] timeMultiStateCounterArr) {
            if (timeMultiStateCounterArr != null) {
                parcel.writeBoolean(true);
                parcel.writeInt(timeMultiStateCounterArr.length);
                for (com.android.server.power.stats.BatteryStatsImpl.TimeMultiStateCounter timeMultiStateCounter : timeMultiStateCounterArr) {
                    timeMultiStateCounter.writeToParcel(parcel);
                }
                return;
            }
            parcel.writeBoolean(false);
        }

        public void reset(boolean z, long j) {
            com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mIdleTimeMillis, z, j);
            this.mScanTimeMillis.reset(z, j);
            this.mSleepTimeMillis.reset(z, j);
            com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mRxTimeMillis, z, j);
            com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mTxTimeMillis, z, j);
            this.mPowerDrainMaMs.reset(z, j);
            this.mMonitoredRailChargeConsumedMaMs.reset(z, j);
        }

        public void detach() {
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mIdleTimeMillis);
            this.mScanTimeMillis.detach();
            this.mSleepTimeMillis.detach();
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mRxTimeMillis);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mTxTimeMillis);
            this.mPowerDrainMaMs.detach();
            this.mMonitoredRailChargeConsumedMaMs.detach();
        }

        public android.os.BatteryStats.LongCounter getIdleTimeCounter() {
            if (this.mIdleTimeMillis == null) {
                return com.android.server.power.stats.BatteryStatsImpl.ZERO_LONG_COUNTER;
            }
            return this.mIdleTimeMillis;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.power.stats.BatteryStatsImpl.TimeMultiStateCounter getOrCreateIdleTimeCounter() {
            if (this.mIdleTimeMillis == null) {
                this.mIdleTimeMillis = createTimeMultiStateCounter();
            }
            return this.mIdleTimeMillis;
        }

        public com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter getScanTimeCounter() {
            return this.mScanTimeMillis;
        }

        public com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter getSleepTimeCounter() {
            return this.mSleepTimeMillis;
        }

        public android.os.BatteryStats.LongCounter getRxTimeCounter() {
            if (this.mRxTimeMillis == null) {
                return com.android.server.power.stats.BatteryStatsImpl.ZERO_LONG_COUNTER;
            }
            return this.mRxTimeMillis;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.power.stats.BatteryStatsImpl.TimeMultiStateCounter getOrCreateRxTimeCounter() {
            if (this.mRxTimeMillis == null) {
                this.mRxTimeMillis = createTimeMultiStateCounter();
            }
            return this.mRxTimeMillis;
        }

        public android.os.BatteryStats.LongCounter[] getTxTimeCounters() {
            if (this.mTxTimeMillis == null) {
                return com.android.server.power.stats.BatteryStatsImpl.ZERO_LONG_COUNTER_ARRAY;
            }
            return this.mTxTimeMillis;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.power.stats.BatteryStatsImpl.TimeMultiStateCounter[] getOrCreateTxTimeCounters() {
            if (this.mTxTimeMillis == null) {
                this.mTxTimeMillis = new com.android.server.power.stats.BatteryStatsImpl.TimeMultiStateCounter[this.mNumTxStates];
                for (int i = 0; i < this.mNumTxStates; i++) {
                    this.mTxTimeMillis[i] = createTimeMultiStateCounter();
                }
            }
            return this.mTxTimeMillis;
        }

        private com.android.server.power.stats.BatteryStatsImpl.TimeMultiStateCounter createTimeMultiStateCounter() {
            long elapsedRealtime = this.mClock.elapsedRealtime();
            com.android.server.power.stats.BatteryStatsImpl.TimeMultiStateCounter timeMultiStateCounter = new com.android.server.power.stats.BatteryStatsImpl.TimeMultiStateCounter(this.mTimeBase, 5, elapsedRealtime);
            timeMultiStateCounter.setState(android.os.BatteryStats.mapUidProcessStateToBatteryConsumerProcessState(this.mProcessState), elapsedRealtime);
            timeMultiStateCounter.update(0L, elapsedRealtime);
            return timeMultiStateCounter;
        }

        public com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter getPowerCounter() {
            return this.mPowerDrainMaMs;
        }

        public com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter getMonitoredRailChargeConsumedMaMs() {
            return this.mMonitoredRailChargeConsumedMaMs;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setState(int i, long j) {
            this.mProcessState = i;
            if (this.mIdleTimeMillis != null) {
                this.mIdleTimeMillis.setState(i, j);
            }
            if (this.mRxTimeMillis != null) {
                this.mRxTimeMillis.setState(i, j);
            }
            if (this.mTxTimeMillis != null) {
                for (int i2 = 0; i2 < this.mTxTimeMillis.length; i2++) {
                    this.mTxTimeMillis[i2].setState(i, j);
                }
            }
        }
    }

    public com.android.server.power.stats.BatteryStatsImpl.SamplingTimer getRpmTimerLocked(java.lang.String str) {
        com.android.server.power.stats.BatteryStatsImpl.SamplingTimer samplingTimer = this.mRpmStats.get(str);
        if (samplingTimer == null) {
            com.android.server.power.stats.BatteryStatsImpl.SamplingTimer samplingTimer2 = new com.android.server.power.stats.BatteryStatsImpl.SamplingTimer(this.mClock, this.mOnBatteryTimeBase);
            this.mRpmStats.put(str, samplingTimer2);
            return samplingTimer2;
        }
        return samplingTimer;
    }

    public com.android.server.power.stats.BatteryStatsImpl.SamplingTimer getScreenOffRpmTimerLocked(java.lang.String str) {
        com.android.server.power.stats.BatteryStatsImpl.SamplingTimer samplingTimer = this.mScreenOffRpmStats.get(str);
        if (samplingTimer == null) {
            com.android.server.power.stats.BatteryStatsImpl.SamplingTimer samplingTimer2 = new com.android.server.power.stats.BatteryStatsImpl.SamplingTimer(this.mClock, this.mOnBatteryScreenOffTimeBase);
            this.mScreenOffRpmStats.put(str, samplingTimer2);
            return samplingTimer2;
        }
        return samplingTimer;
    }

    public com.android.server.power.stats.BatteryStatsImpl.SamplingTimer getWakeupReasonTimerLocked(java.lang.String str) {
        com.android.server.power.stats.BatteryStatsImpl.SamplingTimer samplingTimer = this.mWakeupReasonStats.get(str);
        if (samplingTimer == null) {
            com.android.server.power.stats.BatteryStatsImpl.SamplingTimer samplingTimer2 = new com.android.server.power.stats.BatteryStatsImpl.SamplingTimer(this.mClock, this.mOnBatteryTimeBase);
            this.mWakeupReasonStats.put(str, samplingTimer2);
            return samplingTimer2;
        }
        return samplingTimer;
    }

    public com.android.server.power.stats.BatteryStatsImpl.SamplingTimer getKernelWakelockTimerLocked(java.lang.String str) {
        com.android.server.power.stats.BatteryStatsImpl.SamplingTimer samplingTimer = this.mKernelWakelockStats.get(str);
        if (samplingTimer == null) {
            com.android.server.power.stats.BatteryStatsImpl.SamplingTimer samplingTimer2 = new com.android.server.power.stats.BatteryStatsImpl.SamplingTimer(this.mClock, this.mOnBatteryScreenOffTimeBase);
            this.mKernelWakelockStats.put(str, samplingTimer2);
            return samplingTimer2;
        }
        return samplingTimer;
    }

    public com.android.server.power.stats.BatteryStatsImpl.SamplingTimer getKernelMemoryTimerLocked(long j) {
        com.android.server.power.stats.BatteryStatsImpl.SamplingTimer samplingTimer = this.mKernelMemoryStats.get(j);
        if (samplingTimer == null) {
            com.android.server.power.stats.BatteryStatsImpl.SamplingTimer samplingTimer2 = new com.android.server.power.stats.BatteryStatsImpl.SamplingTimer(this.mClock, this.mOnBatteryTimeBase);
            this.mKernelMemoryStats.put(j, samplingTimer2);
            return samplingTimer2;
        }
        return samplingTimer;
    }

    private class HistoryStepDetailsCalculatorImpl implements com.android.internal.os.BatteryStatsHistory.HistoryStepDetailsCalculator {
        private long mCurStepCpuSystemTimeMs;
        private long mCurStepCpuUserTimeMs;
        private long mCurStepStatIOWaitTimeMs;
        private long mCurStepStatIdleTimeMs;
        private long mCurStepStatIrqTimeMs;
        private long mCurStepStatSoftIrqTimeMs;
        private long mCurStepStatSystemTimeMs;
        private long mCurStepStatUserTimeMs;
        private final android.os.BatteryStats.HistoryStepDetails mDetails;
        private boolean mHasHistoryStepDetails;
        private long mLastStepCpuSystemTimeMs;
        private long mLastStepCpuUserTimeMs;
        private long mLastStepStatIOWaitTimeMs;
        private long mLastStepStatIdleTimeMs;
        private long mLastStepStatIrqTimeMs;
        private long mLastStepStatSoftIrqTimeMs;
        private long mLastStepStatSystemTimeMs;
        private long mLastStepStatUserTimeMs;
        private boolean mUpdateRequested;

        private HistoryStepDetailsCalculatorImpl() {
            this.mDetails = new android.os.BatteryStats.HistoryStepDetails();
        }

        public android.os.BatteryStats.HistoryStepDetails getHistoryStepDetails() {
            if (!this.mUpdateRequested) {
                this.mUpdateRequested = true;
                com.android.server.power.stats.BatteryStatsImpl.this.requestImmediateCpuUpdate();
                if (com.android.server.power.stats.BatteryStatsImpl.this.mPlatformIdleStateCallback != null) {
                    this.mDetails.statSubsystemPowerState = com.android.server.power.stats.BatteryStatsImpl.this.mPlatformIdleStateCallback.getSubsystemLowPowerStats();
                }
            }
            int i = 0;
            if (!this.mHasHistoryStepDetails) {
                int size = com.android.server.power.stats.BatteryStatsImpl.this.mUidStats.size();
                while (i < size) {
                    com.android.server.power.stats.BatteryStatsImpl.Uid uid = (com.android.server.power.stats.BatteryStatsImpl.Uid) com.android.server.power.stats.BatteryStatsImpl.this.mUidStats.valueAt(i);
                    uid.mLastStepUserTimeMs = uid.mCurStepUserTimeMs;
                    uid.mLastStepSystemTimeMs = uid.mCurStepSystemTimeMs;
                    i++;
                }
                this.mLastStepCpuUserTimeMs = this.mCurStepCpuUserTimeMs;
                this.mLastStepCpuSystemTimeMs = this.mCurStepCpuSystemTimeMs;
                this.mLastStepStatUserTimeMs = this.mCurStepStatUserTimeMs;
                this.mLastStepStatSystemTimeMs = this.mCurStepStatSystemTimeMs;
                this.mLastStepStatIOWaitTimeMs = this.mCurStepStatIOWaitTimeMs;
                this.mLastStepStatIrqTimeMs = this.mCurStepStatIrqTimeMs;
                this.mLastStepStatSoftIrqTimeMs = this.mCurStepStatSoftIrqTimeMs;
                this.mLastStepStatIdleTimeMs = this.mCurStepStatIdleTimeMs;
                return null;
            }
            this.mDetails.userTime = (int) (this.mCurStepCpuUserTimeMs - this.mLastStepCpuUserTimeMs);
            this.mDetails.systemTime = (int) (this.mCurStepCpuSystemTimeMs - this.mLastStepCpuSystemTimeMs);
            this.mDetails.statUserTime = (int) (this.mCurStepStatUserTimeMs - this.mLastStepStatUserTimeMs);
            this.mDetails.statSystemTime = (int) (this.mCurStepStatSystemTimeMs - this.mLastStepStatSystemTimeMs);
            this.mDetails.statIOWaitTime = (int) (this.mCurStepStatIOWaitTimeMs - this.mLastStepStatIOWaitTimeMs);
            this.mDetails.statIrqTime = (int) (this.mCurStepStatIrqTimeMs - this.mLastStepStatIrqTimeMs);
            this.mDetails.statSoftIrqTime = (int) (this.mCurStepStatSoftIrqTimeMs - this.mLastStepStatSoftIrqTimeMs);
            this.mDetails.statIdlTime = (int) (this.mCurStepStatIdleTimeMs - this.mLastStepStatIdleTimeMs);
            android.os.BatteryStats.HistoryStepDetails historyStepDetails = this.mDetails;
            android.os.BatteryStats.HistoryStepDetails historyStepDetails2 = this.mDetails;
            this.mDetails.appCpuUid3 = -1;
            historyStepDetails2.appCpuUid2 = -1;
            historyStepDetails.appCpuUid1 = -1;
            android.os.BatteryStats.HistoryStepDetails historyStepDetails3 = this.mDetails;
            android.os.BatteryStats.HistoryStepDetails historyStepDetails4 = this.mDetails;
            this.mDetails.appCpuUTime3 = 0;
            historyStepDetails4.appCpuUTime2 = 0;
            historyStepDetails3.appCpuUTime1 = 0;
            android.os.BatteryStats.HistoryStepDetails historyStepDetails5 = this.mDetails;
            android.os.BatteryStats.HistoryStepDetails historyStepDetails6 = this.mDetails;
            this.mDetails.appCpuSTime3 = 0;
            historyStepDetails6.appCpuSTime2 = 0;
            historyStepDetails5.appCpuSTime1 = 0;
            int size2 = com.android.server.power.stats.BatteryStatsImpl.this.mUidStats.size();
            while (i < size2) {
                com.android.server.power.stats.BatteryStatsImpl.Uid uid2 = (com.android.server.power.stats.BatteryStatsImpl.Uid) com.android.server.power.stats.BatteryStatsImpl.this.mUidStats.valueAt(i);
                int i2 = (int) (uid2.mCurStepUserTimeMs - uid2.mLastStepUserTimeMs);
                int i3 = (int) (uid2.mCurStepSystemTimeMs - uid2.mLastStepSystemTimeMs);
                int i4 = i2 + i3;
                uid2.mLastStepUserTimeMs = uid2.mCurStepUserTimeMs;
                uid2.mLastStepSystemTimeMs = uid2.mCurStepSystemTimeMs;
                if (i4 > this.mDetails.appCpuUTime3 + this.mDetails.appCpuSTime3) {
                    if (i4 <= this.mDetails.appCpuUTime2 + this.mDetails.appCpuSTime2) {
                        this.mDetails.appCpuUid3 = uid2.mUid;
                        this.mDetails.appCpuUTime3 = i2;
                        this.mDetails.appCpuSTime3 = i3;
                    } else {
                        this.mDetails.appCpuUid3 = this.mDetails.appCpuUid2;
                        this.mDetails.appCpuUTime3 = this.mDetails.appCpuUTime2;
                        this.mDetails.appCpuSTime3 = this.mDetails.appCpuSTime2;
                        if (i4 <= this.mDetails.appCpuUTime1 + this.mDetails.appCpuSTime1) {
                            this.mDetails.appCpuUid2 = uid2.mUid;
                            this.mDetails.appCpuUTime2 = i2;
                            this.mDetails.appCpuSTime2 = i3;
                        } else {
                            this.mDetails.appCpuUid2 = this.mDetails.appCpuUid1;
                            this.mDetails.appCpuUTime2 = this.mDetails.appCpuUTime1;
                            this.mDetails.appCpuSTime2 = this.mDetails.appCpuSTime1;
                            this.mDetails.appCpuUid1 = uid2.mUid;
                            this.mDetails.appCpuUTime1 = i2;
                            this.mDetails.appCpuSTime1 = i3;
                        }
                    }
                }
                i++;
            }
            this.mLastStepCpuUserTimeMs = this.mCurStepCpuUserTimeMs;
            this.mLastStepCpuSystemTimeMs = this.mCurStepCpuSystemTimeMs;
            this.mLastStepStatUserTimeMs = this.mCurStepStatUserTimeMs;
            this.mLastStepStatSystemTimeMs = this.mCurStepStatSystemTimeMs;
            this.mLastStepStatIOWaitTimeMs = this.mCurStepStatIOWaitTimeMs;
            this.mLastStepStatIrqTimeMs = this.mCurStepStatIrqTimeMs;
            this.mLastStepStatSoftIrqTimeMs = this.mCurStepStatSoftIrqTimeMs;
            this.mLastStepStatIdleTimeMs = this.mCurStepStatIdleTimeMs;
            return this.mDetails;
        }

        public void addCpuStats(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            this.mCurStepCpuUserTimeMs += i;
            this.mCurStepCpuSystemTimeMs += i2;
            this.mCurStepStatUserTimeMs += i3;
            this.mCurStepStatSystemTimeMs += i4;
            this.mCurStepStatIOWaitTimeMs += i5;
            this.mCurStepStatIrqTimeMs += i6;
            this.mCurStepStatSoftIrqTimeMs += i7;
            this.mCurStepStatIdleTimeMs += i8;
        }

        public void finishAddingCpuLocked() {
            this.mHasHistoryStepDetails = true;
            this.mUpdateRequested = false;
        }

        public void clear() {
            this.mHasHistoryStepDetails = false;
            this.mCurStepCpuUserTimeMs = 0L;
            this.mLastStepCpuUserTimeMs = 0L;
            this.mCurStepCpuSystemTimeMs = 0L;
            this.mLastStepCpuSystemTimeMs = 0L;
            this.mCurStepStatUserTimeMs = 0L;
            this.mLastStepStatUserTimeMs = 0L;
            this.mCurStepStatSystemTimeMs = 0L;
            this.mLastStepStatSystemTimeMs = 0L;
            this.mCurStepStatIOWaitTimeMs = 0L;
            this.mLastStepStatIOWaitTimeMs = 0L;
            this.mCurStepStatIrqTimeMs = 0L;
            this.mLastStepStatIrqTimeMs = 0L;
            this.mCurStepStatSoftIrqTimeMs = 0L;
            this.mLastStepStatSoftIrqTimeMs = 0L;
            this.mCurStepStatIdleTimeMs = 0L;
            this.mLastStepStatIdleTimeMs = 0L;
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void commitCurrentHistoryBatchLocked() {
        this.mHistory.commitCurrentHistoryBatchLocked();
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void createFakeHistoryEvents(long j) {
        long elapsedRealtime = this.mClock.elapsedRealtime();
        long uptimeMillis = this.mClock.uptimeMillis();
        for (long j2 = 0; j2 < j; j2++) {
            noteLongPartialWakelockStart("name1", "historyName1", 1000, elapsedRealtime, uptimeMillis);
            noteLongPartialWakelockFinish("name1", "historyName1", 1000, elapsedRealtime, uptimeMillis);
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void recordHistoryEventLocked(long j, long j2, int i, java.lang.String str, int i2) {
        this.mHistory.recordEvent(j, j2, i, str, i2);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void updateTimeBasesLocked(boolean z, int i, long j, long j2) {
        boolean z2 = !android.view.Display.isOnState(i);
        boolean z3 = z != this.mOnBatteryTimeBase.isRunning();
        boolean z4 = (z && z2) != this.mOnBatteryScreenOffTimeBase.isRunning();
        if (z4 || z3) {
            if (z4) {
                updateKernelWakelocksLocked(j2);
                updateBatteryPropertiesLocked();
            }
            if (z3) {
                updateRpmStatsLocked(j2);
            }
            this.mOnBatteryTimeBase.setRunning(z, j, j2);
            if (z3) {
                for (int size = this.mUidStats.size() - 1; size >= 0; size--) {
                    this.mUidStats.valueAt(size).updateOnBatteryBgTimeBase(j, j2);
                }
            }
            if (z4) {
                this.mOnBatteryScreenOffTimeBase.setRunning(z && z2, j, j2);
                for (int size2 = this.mUidStats.size() - 1; size2 >= 0; size2--) {
                    this.mUidStats.valueAt(size2).updateOnBatteryScreenOffBgTimeBase(j, j2);
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    protected void updateBatteryPropertiesLocked() {
        try {
            android.os.IBatteryPropertiesRegistrar asInterface = android.os.IBatteryPropertiesRegistrar.Stub.asInterface(android.os.ServiceManager.getService("batteryproperties"));
            if (asInterface != null) {
                asInterface.scheduleUpdate();
            }
        } catch (android.os.RemoteException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onIsolatedUidAdded(int i, int i2) {
        long elapsedRealtime = this.mClock.elapsedRealtime();
        long uptimeMillis = this.mClock.uptimeMillis();
        synchronized (this) {
            getUidStatsLocked(i2, elapsedRealtime, uptimeMillis).addIsolatedUid(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBeforeIsolatedUidRemoved(int i, int i2) {
        long elapsedRealtime = this.mClock.elapsedRealtime();
        this.mPowerStatsUidResolver.retainIsolatedUid(i);
        synchronized (this) {
            this.mPendingRemovedUids.add(new com.android.server.power.stats.BatteryStatsImpl.UidToRemove(this, i, elapsedRealtime));
        }
        if (this.mExternalSync != null) {
            this.mExternalSync.scheduleCpuSyncDueToRemovedUid(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAfterIsolatedUidRemoved(int i, int i2) {
        long elapsedRealtime = this.mClock.elapsedRealtime();
        long uptimeMillis = this.mClock.uptimeMillis();
        synchronized (this) {
            getUidStatsLocked(i2, elapsedRealtime, uptimeMillis).removeIsolatedUid(i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void releaseIsolatedUidLocked(int i, long j, long j2) {
        this.mPowerStatsUidResolver.releaseIsolatedUid(i);
    }

    private int mapUid(int i) {
        if (android.os.Process.isSdkSandboxUid(i)) {
            return android.os.Process.getAppUidForSdkSandboxUid(i);
        }
        return this.mPowerStatsUidResolver.mapUid(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int mapIsolatedUid(int i) {
        return this.mPowerStatsUidResolver.mapUid(i);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteEventLocked(int i, java.lang.String str, int i2, long j, long j2) {
        int mapUid = mapUid(i2);
        if (!this.mActiveEvents.updateState(i, str, mapUid, 0)) {
            return;
        }
        this.mHistory.recordEvent(j, j2, i, str, mapUid);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteCurrentTimeChangedLocked(long j, long j2, long j3) {
        this.mHistory.recordCurrentTimeChange(j2, j3, j);
        adjustStartClockTime(j);
    }

    private void adjustStartClockTime(long j) {
        this.mStartClockTimeMs = j - (this.mClock.elapsedRealtime() - (this.mRealtimeStartUs / 1000));
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteProcessStartLocked(java.lang.String str, int i, long j, long j2) {
        int mapUid = mapUid(i);
        if (isOnBattery()) {
            getUidStatsLocked(mapUid, j, j2).getProcessStatsLocked(str).incStartsLocked();
        }
        if (!this.mActiveEvents.updateState(32769, str, mapUid, 0) || !this.mRecordAllHistory) {
            return;
        }
        this.mHistory.recordEvent(j, j2, 32769, str, mapUid);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteProcessCrashLocked(java.lang.String str, int i, long j, long j2) {
        int mapUid = mapUid(i);
        if (isOnBattery()) {
            getUidStatsLocked(mapUid, j, j2).getProcessStatsLocked(str).incNumCrashesLocked();
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteProcessAnrLocked(java.lang.String str, int i, long j, long j2) {
        int mapUid = mapUid(i);
        if (isOnBattery()) {
            getUidStatsLocked(mapUid, j, j2).getProcessStatsLocked(str).incNumAnrsLocked();
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteUidProcessStateLocked(int i, int i2) {
        noteUidProcessStateLocked(i, i2, this.mClock.elapsedRealtime(), this.mClock.uptimeMillis());
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteUidProcessStateLocked(int i, int i2, long j, long j2) {
        int mapUid = mapUid(i);
        if (i != mapUid && android.os.Process.isIsolated(i)) {
            return;
        }
        this.mFrameworkStatsLogger.uidProcessStateChanged(i, i2);
        getUidStatsLocked(mapUid, j, j2).updateUidProcessStateLocked(i2, j, j2);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteProcessFinishLocked(java.lang.String str, int i, long j, long j2) {
        int mapUid = mapUid(i);
        if (!this.mActiveEvents.updateState(16385, str, mapUid, 0) || !this.mRecordAllHistory) {
            return;
        }
        this.mHistory.recordEvent(j, j2, 16385, str, mapUid);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteSyncStartLocked(java.lang.String str, int i) {
        noteSyncStartLocked(str, i, this.mClock.elapsedRealtime(), this.mClock.uptimeMillis());
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteSyncStartLocked(java.lang.String str, int i, long j, long j2) {
        int mapUid = mapUid(i);
        getUidStatsLocked(mapUid, j, j2).noteStartSyncLocked(str, j);
        if (!this.mActiveEvents.updateState(32772, str, mapUid, 0)) {
            return;
        }
        this.mHistory.recordEvent(j, j2, 32772, str, mapUid);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteSyncFinishLocked(java.lang.String str, int i) {
        noteSyncFinishLocked(str, i, this.mClock.elapsedRealtime(), this.mClock.uptimeMillis());
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteSyncFinishLocked(java.lang.String str, int i, long j, long j2) {
        int mapUid = mapUid(i);
        getUidStatsLocked(mapUid, j, j2).noteStopSyncLocked(str, j);
        if (!this.mActiveEvents.updateState(16388, str, mapUid, 0)) {
            return;
        }
        this.mHistory.recordEvent(j, j2, 16388, str, mapUid);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteJobStartLocked(java.lang.String str, int i) {
        noteJobStartLocked(str, i, this.mClock.elapsedRealtime(), this.mClock.uptimeMillis());
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteJobStartLocked(java.lang.String str, int i, long j, long j2) {
        int mapUid = mapUid(i);
        getUidStatsLocked(mapUid, j, j2).noteStartJobLocked(str, j);
        if (!this.mActiveEvents.updateState(32774, str, mapUid, 0)) {
            return;
        }
        this.mHistory.recordEvent(j, j2, 32774, str, mapUid);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteJobFinishLocked(java.lang.String str, int i, int i2) {
        noteJobFinishLocked(str, i, i2, this.mClock.elapsedRealtime(), this.mClock.uptimeMillis());
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteJobFinishLocked(java.lang.String str, int i, int i2, long j, long j2) {
        int mapUid = mapUid(i);
        getUidStatsLocked(mapUid, j, j2).noteStopJobLocked(str, j, i2);
        if (!this.mActiveEvents.updateState(16390, str, mapUid, 0)) {
            return;
        }
        this.mHistory.recordEvent(j, j2, 16390, str, mapUid);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteJobsDeferredLocked(int i, int i2, long j, long j2, long j3) {
        getUidStatsLocked(mapUid(i), j2, j3).noteJobsDeferredLocked(i2, j);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteAlarmStartLocked(java.lang.String str, android.os.WorkSource workSource, int i) {
        noteAlarmStartLocked(str, workSource, i, this.mClock.elapsedRealtime(), this.mClock.uptimeMillis());
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteAlarmStartLocked(java.lang.String str, android.os.WorkSource workSource, int i, long j, long j2) {
        noteAlarmStartOrFinishLocked(32781, str, workSource, i, j, j2);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteAlarmFinishLocked(java.lang.String str, android.os.WorkSource workSource, int i) {
        noteAlarmFinishLocked(str, workSource, i, this.mClock.elapsedRealtime(), this.mClock.uptimeMillis());
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteAlarmFinishLocked(java.lang.String str, android.os.WorkSource workSource, int i, long j, long j2) {
        noteAlarmStartOrFinishLocked(16397, str, workSource, i, j, j2);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void noteAlarmStartOrFinishLocked(int i, java.lang.String str, android.os.WorkSource workSource, int i2, long j, long j2) {
        if (this.mRecordAllHistory) {
            if (workSource != null) {
                for (int i3 = 0; i3 < workSource.size(); i3++) {
                    int mapUid = mapUid(workSource.getUid(i3));
                    if (this.mActiveEvents.updateState(i, str, mapUid, 0)) {
                        this.mHistory.recordEvent(j, j2, i, str, mapUid);
                    }
                }
                java.util.List workChains = workSource.getWorkChains();
                if (workChains != null) {
                    for (int i4 = 0; i4 < workChains.size(); i4++) {
                        int mapUid2 = mapUid(((android.os.WorkSource.WorkChain) workChains.get(i4)).getAttributionUid());
                        if (this.mActiveEvents.updateState(i, str, mapUid2, 0)) {
                            this.mHistory.recordEvent(j, j2, i, str, mapUid2);
                        }
                    }
                    return;
                }
                return;
            }
            int mapUid3 = mapUid(i2);
            if (this.mActiveEvents.updateState(i, str, mapUid3, 0)) {
                this.mHistory.recordEvent(j, j2, i, str, mapUid3);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteWakupAlarmLocked(java.lang.String str, int i, android.os.WorkSource workSource, java.lang.String str2) {
        noteWakupAlarmLocked(str, i, workSource, str2, this.mClock.elapsedRealtime(), this.mClock.uptimeMillis());
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteWakupAlarmLocked(java.lang.String str, int i, android.os.WorkSource workSource, java.lang.String str2, long j, long j2) {
        if (workSource != null) {
            for (int i2 = 0; i2 < workSource.size(); i2++) {
                int uid = workSource.getUid(i2);
                java.lang.String packageName = workSource.getPackageName(i2);
                if (isOnBattery()) {
                    getPackageStatsLocked(uid, packageName != null ? packageName : str, j, j2).noteWakeupAlarmLocked(str2);
                }
            }
            java.util.List workChains = workSource.getWorkChains();
            if (workChains != null) {
                for (int i3 = 0; i3 < workChains.size(); i3++) {
                    int attributionUid = ((android.os.WorkSource.WorkChain) workChains.get(i3)).getAttributionUid();
                    if (isOnBattery()) {
                        getPackageStatsLocked(attributionUid, str, j, j2).noteWakeupAlarmLocked(str2);
                    }
                }
                return;
            }
            return;
        }
        if (isOnBattery()) {
            getPackageStatsLocked(i, str, j, j2).noteWakeupAlarmLocked(str2);
        }
    }

    private void requestWakelockCpuUpdate() {
        this.mExternalSync.scheduleCpuSyncDueToWakelockChange(60000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestImmediateCpuUpdate() {
        this.mExternalSync.scheduleCpuSyncDueToWakelockChange(0L);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void setRecordAllHistoryLocked(boolean z) {
        this.mRecordAllHistory = z;
        if (!z) {
            this.mActiveEvents.removeEvents(5);
            this.mActiveEvents.removeEvents(13);
            java.util.HashMap stateForEvent = this.mActiveEvents.getStateForEvent(1);
            if (stateForEvent != null) {
                long elapsedRealtime = this.mClock.elapsedRealtime();
                long uptimeMillis = this.mClock.uptimeMillis();
                for (java.util.Map.Entry entry : stateForEvent.entrySet()) {
                    int i = 0;
                    for (android.util.SparseIntArray sparseIntArray = (android.util.SparseIntArray) entry.getValue(); i < sparseIntArray.size(); sparseIntArray = sparseIntArray) {
                        this.mHistory.recordEvent(elapsedRealtime, uptimeMillis, 16385, (java.lang.String) entry.getKey(), sparseIntArray.keyAt(i));
                        i++;
                    }
                }
                return;
            }
            return;
        }
        java.util.HashMap stateForEvent2 = this.mActiveEvents.getStateForEvent(1);
        if (stateForEvent2 != null) {
            long elapsedRealtime2 = this.mClock.elapsedRealtime();
            long uptimeMillis2 = this.mClock.uptimeMillis();
            for (java.util.Map.Entry entry2 : stateForEvent2.entrySet()) {
                int i2 = 0;
                for (android.util.SparseIntArray sparseIntArray2 = (android.util.SparseIntArray) entry2.getValue(); i2 < sparseIntArray2.size(); sparseIntArray2 = sparseIntArray2) {
                    this.mHistory.recordEvent(elapsedRealtime2, uptimeMillis2, 32769, (java.lang.String) entry2.getKey(), sparseIntArray2.keyAt(i2));
                    i2++;
                }
            }
        }
    }

    public void setNoAutoReset(boolean z) {
        this.mNoAutoReset = z;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void setPretendScreenOff(boolean z) {
        if (this.mPretendScreenOff != z) {
            this.mPretendScreenOff = z;
            noteScreenStateLocked(0, this.mPerDisplayBatteryStats[0].screenState, this.mClock.elapsedRealtime(), this.mClock.uptimeMillis(), this.mClock.currentTimeMillis());
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteStartWakeLocked(int i, int i2, android.os.WorkSource.WorkChain workChain, java.lang.String str, java.lang.String str2, int i3, boolean z) {
        noteStartWakeLocked(i, i2, workChain, str, str2, i3, z, this.mClock.elapsedRealtime(), this.mClock.uptimeMillis());
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteStartWakeLocked(int i, int i2, android.os.WorkSource.WorkChain workChain, java.lang.String str, java.lang.String str2, int i3, boolean z, long j, long j2) {
        java.lang.String str3;
        int mapUid = mapUid(i);
        if (i3 == 0) {
            aggregateLastWakeupUptimeLocked(j, j2);
            if (str2 != null) {
                str3 = str2;
            } else {
                str3 = str;
            }
            if (this.mRecordAllHistory && this.mActiveEvents.updateState(32773, str3, mapUid, 0)) {
                this.mHistory.recordEvent(j, j2, 32773, str3, mapUid);
            }
            if (this.mWakeLockNesting == 0) {
                this.mWakeLockImportant = !z;
                this.mHistory.recordWakelockStartEvent(j, j2, str3, mapUid);
            } else if (!this.mWakeLockImportant && !z && this.mHistory.maybeUpdateWakelockTag(j, j2, str3, mapUid)) {
                this.mWakeLockImportant = true;
            }
            this.mWakeLockNesting++;
        }
        if (mapUid >= 0) {
            if (mapUid != i) {
                this.mPowerStatsUidResolver.retainIsolatedUid(i);
            }
            if (this.mOnBatteryScreenOffTimeBase.isRunning()) {
                requestWakelockCpuUpdate();
            }
            com.android.server.power.stats.BatteryStatsImpl.Uid uidStatsLocked = getUidStatsLocked(mapUid, j, j2);
            uidStatsLocked.noteStartWakeLocked(i2, str, i3, j);
            this.mFrameworkStatsLogger.wakelockStateChanged(mapIsolatedUid(i), workChain, str, i3, uidStatsLocked.mProcessState, true);
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteStopWakeLocked(int i, int i2, android.os.WorkSource.WorkChain workChain, java.lang.String str, java.lang.String str2, int i3) {
        noteStopWakeLocked(i, i2, workChain, str, str2, i3, this.mClock.elapsedRealtime(), this.mClock.uptimeMillis());
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteStopWakeLocked(int i, int i2, android.os.WorkSource.WorkChain workChain, java.lang.String str, java.lang.String str2, int i3, long j, long j2) {
        java.lang.String str3;
        int mapUid = mapUid(i);
        if (i3 == 0) {
            this.mWakeLockNesting--;
            if (str2 != null) {
                str3 = str2;
            } else {
                str3 = str;
            }
            if (this.mRecordAllHistory && this.mActiveEvents.updateState(16389, str3, mapUid, 0)) {
                this.mHistory.recordEvent(j, j2, 16389, str3, mapUid);
            }
            if (this.mWakeLockNesting == 0) {
                this.mHistory.recordWakelockStopEvent(j, j2, str3, mapUid);
            }
        }
        if (mapUid >= 0) {
            if (this.mOnBatteryScreenOffTimeBase.isRunning()) {
                requestWakelockCpuUpdate();
            }
            com.android.server.power.stats.BatteryStatsImpl.Uid uidStatsLocked = getUidStatsLocked(mapUid, j, j2);
            uidStatsLocked.noteStopWakeLocked(i2, str, i3, j);
            this.mFrameworkStatsLogger.wakelockStateChanged(mapIsolatedUid(i), workChain, str, i3, uidStatsLocked.mProcessState, false);
            if (mapUid != i) {
                releaseIsolatedUidLocked(i, j, j2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getPowerManagerWakeLockLevel(int i) {
        switch (i) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                android.util.Slog.e(TAG, "Illegal window wakelock type observed in batterystats.");
                break;
            case 18:
                break;
            default:
                android.util.Slog.e(TAG, "Illegal wakelock type in batterystats: " + i);
                break;
        }
        return -1;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteStartWakeFromSourceLocked(android.os.WorkSource workSource, int i, java.lang.String str, java.lang.String str2, int i2, boolean z, long j, long j2) {
        int size = workSource.size();
        for (int i3 = 0; i3 < size; i3++) {
            noteStartWakeLocked(workSource.getUid(i3), i, null, str, str2, i2, z, j, j2);
        }
        java.util.List workChains = workSource.getWorkChains();
        if (workChains != null) {
            for (int i4 = 0; i4 < workChains.size(); i4++) {
                android.os.WorkSource.WorkChain workChain = (android.os.WorkSource.WorkChain) workChains.get(i4);
                noteStartWakeLocked(workChain.getAttributionUid(), i, workChain, str, str2, i2, z, j, j2);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteChangeWakelockFromSourceLocked(android.os.WorkSource workSource, int i, java.lang.String str, java.lang.String str2, int i2, android.os.WorkSource workSource2, int i3, java.lang.String str3, java.lang.String str4, int i4, boolean z, long j, long j2) {
        java.util.ArrayList arrayList;
        java.util.ArrayList arrayList2;
        java.util.ArrayList[] diffChains = android.os.WorkSource.diffChains(workSource, workSource2);
        int size = workSource2.size();
        for (int i5 = 0; i5 < size; i5++) {
            noteStartWakeLocked(workSource2.getUid(i5), i3, null, str3, str4, i4, z, j, j2);
        }
        if (diffChains != null && (arrayList2 = diffChains[0]) != null) {
            for (int i6 = 0; i6 < arrayList2.size(); i6++) {
                android.os.WorkSource.WorkChain workChain = (android.os.WorkSource.WorkChain) arrayList2.get(i6);
                noteStartWakeLocked(workChain.getAttributionUid(), i3, workChain, str3, str4, i4, z, j, j2);
            }
        }
        int size2 = workSource.size();
        for (int i7 = 0; i7 < size2; i7++) {
            noteStopWakeLocked(workSource.getUid(i7), i, null, str, str2, i2, j, j2);
        }
        if (diffChains == null || (arrayList = diffChains[1]) == null) {
            return;
        }
        for (int i8 = 0; i8 < arrayList.size(); i8++) {
            android.os.WorkSource.WorkChain workChain2 = (android.os.WorkSource.WorkChain) arrayList.get(i8);
            noteStopWakeLocked(workChain2.getAttributionUid(), i, workChain2, str, str2, i2, j, j2);
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteStopWakeFromSourceLocked(android.os.WorkSource workSource, int i, java.lang.String str, java.lang.String str2, int i2, long j, long j2) {
        int size = workSource.size();
        for (int i3 = 0; i3 < size; i3++) {
            noteStopWakeLocked(workSource.getUid(i3), i, null, str, str2, i2, j, j2);
        }
        java.util.List workChains = workSource.getWorkChains();
        if (workChains != null) {
            for (int i4 = 0; i4 < workChains.size(); i4++) {
                android.os.WorkSource.WorkChain workChain = (android.os.WorkSource.WorkChain) workChains.get(i4);
                noteStopWakeLocked(workChain.getAttributionUid(), i, workChain, str, str2, i2, j, j2);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteLongPartialWakelockStart(java.lang.String str, java.lang.String str2, int i) {
        noteLongPartialWakelockStart(str, str2, i, this.mClock.elapsedRealtime(), this.mClock.uptimeMillis());
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteLongPartialWakelockStart(java.lang.String str, java.lang.String str2, int i, long j, long j2) {
        noteLongPartialWakeLockStartInternal(str, str2, i, j, j2);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteLongPartialWakelockStartFromSource(java.lang.String str, java.lang.String str2, android.os.WorkSource workSource, long j, long j2) {
        int size = workSource.size();
        for (int i = 0; i < size; i++) {
            noteLongPartialWakeLockStartInternal(str, str2, mapUid(workSource.getUid(i)), j, j2);
        }
        java.util.List workChains = workSource.getWorkChains();
        if (workChains != null) {
            for (int i2 = 0; i2 < workChains.size(); i2++) {
                noteLongPartialWakeLockStartInternal(str, str2, ((android.os.WorkSource.WorkChain) workChains.get(i2)).getAttributionUid(), j, j2);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void noteLongPartialWakeLockStartInternal(java.lang.String str, java.lang.String str2, int i, long j, long j2) {
        java.lang.String str3;
        int mapUid = mapUid(i);
        if (str2 != null) {
            str3 = str2;
        } else {
            str3 = str;
        }
        if (!this.mActiveEvents.updateState(32788, str3, mapUid, 0)) {
            return;
        }
        this.mHistory.recordEvent(j, j2, 32788, str3, mapUid);
        if (mapUid != i) {
            this.mPowerStatsUidResolver.retainIsolatedUid(i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteLongPartialWakelockFinish(java.lang.String str, java.lang.String str2, int i) {
        noteLongPartialWakelockFinish(str, str2, i, this.mClock.elapsedRealtime(), this.mClock.uptimeMillis());
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteLongPartialWakelockFinish(java.lang.String str, java.lang.String str2, int i, long j, long j2) {
        noteLongPartialWakeLockFinishInternal(str, str2, i, j, j2);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteLongPartialWakelockFinishFromSource(java.lang.String str, java.lang.String str2, android.os.WorkSource workSource, long j, long j2) {
        int size = workSource.size();
        for (int i = 0; i < size; i++) {
            noteLongPartialWakeLockFinishInternal(str, str2, mapUid(workSource.getUid(i)), j, j2);
        }
        java.util.List workChains = workSource.getWorkChains();
        if (workChains != null) {
            for (int i2 = 0; i2 < workChains.size(); i2++) {
                noteLongPartialWakeLockFinishInternal(str, str2, ((android.os.WorkSource.WorkChain) workChains.get(i2)).getAttributionUid(), j, j2);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void noteLongPartialWakeLockFinishInternal(java.lang.String str, java.lang.String str2, int i, long j, long j2) {
        java.lang.String str3;
        int mapUid = mapUid(i);
        if (str2 != null) {
            str3 = str2;
        } else {
            str3 = str;
        }
        if (!this.mActiveEvents.updateState(16404, str3, mapUid, 0)) {
            return;
        }
        this.mHistory.recordEvent(j, j2, 16404, str3, mapUid);
        if (mapUid != i) {
            releaseIsolatedUidLocked(i, j, j2);
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    void aggregateLastWakeupUptimeLocked(long j, long j2) {
        if (this.mLastWakeupReason != null) {
            long j3 = (j2 - this.mLastWakeupUptimeMs) * 1000;
            getWakeupReasonTimerLocked(this.mLastWakeupReason).add(j3, 1, j);
            this.mFrameworkStatsLogger.kernelWakeupReported(j3);
            this.mLastWakeupReason = null;
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteWakeupReasonLocked(java.lang.String str, long j, long j2) {
        aggregateLastWakeupUptimeLocked(j, j2);
        this.mHistory.recordWakeupEvent(j, j2, str);
        this.mLastWakeupReason = str;
        this.mLastWakeupUptimeMs = j2;
        this.mLastWakeupElapsedTimeMs = j;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public boolean startAddingCpuStatsLocked() {
        this.mExternalSync.cancelCpuSyncDueToWakelockChange();
        return this.mOnBatteryInternal;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void addCpuStatsLocked(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        this.mStepDetailsCalculator.addCpuStats(i, i2, i3, i4, i5, i6, i7, i8);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void finishAddingCpuStatsLocked() {
        this.mStepDetailsCalculator.finishAddingCpuLocked();
    }

    public void noteProcessDiedLocked(int i, int i2) {
        com.android.server.power.stats.BatteryStatsImpl.Uid uid = this.mUidStats.get(mapUid(i));
        if (uid != null) {
            uid.mPids.remove(i2);
        }
    }

    public void reportExcessiveCpuLocked(int i, java.lang.String str, long j, long j2) {
        com.android.server.power.stats.BatteryStatsImpl.Uid uid = this.mUidStats.get(mapUid(i));
        if (uid != null) {
            uid.reportExcessiveCpuLocked(str, j, j2);
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteStartSensorLocked(int i, int i2) {
        noteStartSensorLocked(i, i2, this.mClock.elapsedRealtime(), this.mClock.uptimeMillis());
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteStartSensorLocked(int i, int i2, long j, long j2) {
        int mapUid = mapUid(i);
        if (this.mSensorNesting == 0) {
            this.mHistory.recordStateStartEvent(j, j2, 8388608);
        }
        this.mSensorNesting++;
        getUidStatsLocked(mapUid, j, j2).noteStartSensor(i2, j);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteStopSensorLocked(int i, int i2) {
        noteStopSensorLocked(i, i2, this.mClock.elapsedRealtime(), this.mClock.uptimeMillis());
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteStopSensorLocked(int i, int i2, long j, long j2) {
        int mapUid = mapUid(i);
        this.mSensorNesting--;
        if (this.mSensorNesting == 0) {
            this.mHistory.recordStateStopEvent(j, j2, 8388608);
        }
        getUidStatsLocked(mapUid, j, j2).noteStopSensor(i2, j);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteGpsChangedLocked(android.os.WorkSource workSource, android.os.WorkSource workSource2) {
        noteGpsChangedLocked(workSource, workSource2, this.mClock.elapsedRealtime(), this.mClock.uptimeMillis());
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteGpsChangedLocked(android.os.WorkSource workSource, android.os.WorkSource workSource2, long j, long j2) {
        for (int i = 0; i < workSource2.size(); i++) {
            noteStartGpsLocked(workSource2.getUid(i), null, j, j2);
        }
        for (int i2 = 0; i2 < workSource.size(); i2++) {
            noteStopGpsLocked(workSource.getUid(i2), null, j, j2);
        }
        java.util.ArrayList[] diffChains = android.os.WorkSource.diffChains(workSource, workSource2);
        if (diffChains != null) {
            if (diffChains[0] != null) {
                java.util.ArrayList arrayList = diffChains[0];
                for (int i3 = 0; i3 < arrayList.size(); i3++) {
                    noteStartGpsLocked(-1, (android.os.WorkSource.WorkChain) arrayList.get(i3), j, j2);
                }
            }
            if (diffChains[1] != null) {
                java.util.ArrayList arrayList2 = diffChains[1];
                for (int i4 = 0; i4 < arrayList2.size(); i4++) {
                    noteStopGpsLocked(-1, (android.os.WorkSource.WorkChain) arrayList2.get(i4), j, j2);
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void noteStartGpsLocked(int i, android.os.WorkSource.WorkChain workChain, long j, long j2) {
        if (workChain != null) {
            i = workChain.getAttributionUid();
        }
        int mapUid = mapUid(i);
        if (this.mGpsNesting == 0) {
            this.mHistory.recordStateStartEvent(j, j2, 536870912);
        }
        this.mGpsNesting++;
        this.mFrameworkStatsLogger.gpsScanStateChanged(mapIsolatedUid(i), workChain, true);
        getUidStatsLocked(mapUid, j, j2).noteStartGps(j);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void noteStopGpsLocked(int i, android.os.WorkSource.WorkChain workChain, long j, long j2) {
        int i2;
        if (workChain == null) {
            i2 = i;
        } else {
            i2 = workChain.getAttributionUid();
        }
        int mapUid = mapUid(i2);
        this.mGpsNesting--;
        if (this.mGpsNesting == 0) {
            this.mHistory.recordStateStopEvent(j, j2, 536870912);
            this.mHistory.recordGpsSignalQualityEvent(j, j2, 2);
            stopAllGpsSignalQualityTimersLocked(-1, j);
            this.mGpsSignalQualityBin = -1;
        }
        this.mFrameworkStatsLogger.gpsScanStateChanged(mapIsolatedUid(i2), workChain, false);
        getUidStatsLocked(mapUid, j, j2).noteStopGps(j);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteGpsSignalQualityLocked(int i, long j, long j2) {
        if (this.mGpsNesting == 0) {
            return;
        }
        if (i < 0 || i >= this.mGpsSignalQualityTimer.length) {
            stopAllGpsSignalQualityTimersLocked(-1, j);
            return;
        }
        if (this.mGpsSignalQualityBin != i) {
            if (this.mGpsSignalQualityBin >= 0) {
                this.mGpsSignalQualityTimer[this.mGpsSignalQualityBin].stopRunningLocked(j);
            }
            if (!this.mGpsSignalQualityTimer[i].isRunningLocked()) {
                this.mGpsSignalQualityTimer[i].startRunningLocked(j);
            }
            this.mHistory.recordGpsSignalQualityEvent(j, j2, i);
            this.mGpsSignalQualityBin = i;
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteScreenStateLocked(int i, int i2) {
        noteScreenStateLocked(i, i2, this.mClock.elapsedRealtime(), this.mClock.uptimeMillis(), this.mClock.currentTimeMillis());
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0262  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x026a  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x022e  */
    @com.android.internal.annotations.GuardedBy({"this"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void noteScreenStateLocked(int i, int i2, long j, long j2, long j3) {
        boolean z;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        boolean z2;
        int i12;
        int i13 = i2;
        if (i13 > 4) {
            if (android.view.Display.isOnState(i2)) {
                i13 = 2;
            } else if (android.view.Display.isDozeState(i2)) {
                if (android.view.Display.isSuspendedState(i2)) {
                    i13 = 4;
                } else {
                    i13 = 3;
                }
            } else if (android.view.Display.isOffState(i2)) {
                i13 = 1;
            } else {
                android.util.Slog.wtf(TAG, "Unknown screen state (not mapped): " + i13);
                i13 = 0;
            }
        }
        int i14 = this.mScreenBrightnessBin;
        int length = this.mPerDisplayBatteryStats.length;
        if (i < 0 || i >= length) {
            android.util.Slog.wtf(TAG, "Unexpected note screen state for display " + i + " (only " + this.mPerDisplayBatteryStats.length + " displays exist...)");
            return;
        }
        com.android.server.power.stats.BatteryStatsImpl.DisplayBatteryStats displayBatteryStats = this.mPerDisplayBatteryStats[i];
        int i15 = displayBatteryStats.screenState;
        if (i15 == i13) {
            i5 = this.mScreenState;
            i6 = i14;
            i4 = 0;
            z = false;
        } else {
            displayBatteryStats.screenState = i13;
            switch (i15) {
                case 0:
                case 1:
                    z = false;
                    break;
                case 2:
                    displayBatteryStats.screenOnTimer.stopRunningLocked(j);
                    int i16 = displayBatteryStats.screenBrightnessBin;
                    if (i16 >= 0) {
                        displayBatteryStats.screenBrightnessTimers[i16].stopRunningLocked(j);
                    }
                    i14 = evaluateOverallScreenBrightnessBinLocked();
                    z = true;
                    break;
                case 3:
                    if (i13 != 4) {
                        displayBatteryStats.screenDozeTimer.stopRunningLocked(j);
                        z = true;
                        break;
                    }
                    z = false;
                    break;
                case 4:
                    if (i13 != 3) {
                        displayBatteryStats.screenDozeTimer.stopRunningLocked(j);
                        z = true;
                        break;
                    }
                    z = false;
                    break;
                default:
                    android.util.Slog.wtf(TAG, "Attempted to stop timer for unexpected display state " + i);
                    z = false;
                    break;
            }
            switch (i13) {
                case 0:
                case 1:
                    break;
                case 2:
                    displayBatteryStats.screenOnTimer.startRunningLocked(j);
                    int i17 = displayBatteryStats.screenBrightnessBin;
                    if (i17 >= 0) {
                        displayBatteryStats.screenBrightnessTimers[i17].startRunningLocked(j);
                    }
                    i14 = evaluateOverallScreenBrightnessBinLocked();
                    z = true;
                    break;
                case 3:
                    if (i15 != 4) {
                        displayBatteryStats.screenDozeTimer.startRunningLocked(j);
                        z = true;
                        break;
                    }
                    break;
                case 4:
                    if (i15 != 3) {
                        displayBatteryStats.screenDozeTimer.startRunningLocked(j);
                        z = true;
                        break;
                    }
                    break;
                default:
                    android.util.Slog.wtf(TAG, "Attempted to start timer for unexpected display state " + i13 + " for display " + i);
                    break;
            }
            if (z && this.mGlobalEnergyConsumerStats != null && this.mGlobalEnergyConsumerStats.isStandardBucketSupported(0)) {
                i3 = 32;
            } else {
                i3 = 0;
            }
            int i18 = 0;
            for (int i19 = 0; i19 < length; i19++) {
                int i20 = this.mPerDisplayBatteryStats[i19].screenState;
                if (i20 == 2 || i18 == 2) {
                    i18 = 2;
                } else if (i20 == 3 || i18 == 3) {
                    i18 = 3;
                } else if (i20 == 4 || i18 == 4) {
                    i18 = 4;
                } else if (i20 == 1 || i18 == 1) {
                    i18 = 1;
                }
            }
            i4 = i3;
            i5 = i18;
            i6 = i14;
        }
        boolean isRunning = this.mOnBatteryTimeBase.isRunning();
        boolean isRunning2 = this.mOnBatteryScreenOffTimeBase.isRunning();
        int i21 = this.mPretendScreenOff ? 1 : i5;
        if (this.mScreenState != i21) {
            recordDailyStatsIfNeededLocked(true, j3);
            int i22 = this.mScreenState;
            this.mScreenState = i21;
            if (i21 != 0) {
                int i23 = i21 - 1;
                if ((i23 & 3) == i23) {
                    this.mModStepMode |= (this.mCurStepMode & 3) ^ i23;
                    this.mCurStepMode = i23 | (this.mCurStepMode & (-4));
                } else {
                    android.util.Slog.wtf(TAG, "Unexpected screen state: " + i21);
                }
            }
            int i24 = 262144;
            if (android.view.Display.isDozeState(i21) && !android.view.Display.isDozeState(i22)) {
                this.mScreenDozeTimer.startRunningLocked(j);
                i8 = 0;
            } else if (android.view.Display.isDozeState(i22) && !android.view.Display.isDozeState(i21)) {
                this.mScreenDozeTimer.stopRunningLocked(j);
                i8 = 262144;
                i24 = 0;
            } else {
                i8 = 0;
                i24 = 0;
            }
            if (android.view.Display.isOnState(i21)) {
                i24 |= 1048576;
                this.mScreenOnTimer.startRunningLocked(j);
                if (this.mScreenBrightnessBin >= 0) {
                    this.mScreenBrightnessTimer[this.mScreenBrightnessBin].startRunningLocked(j);
                }
            } else if (!android.view.Display.isOnState(i22)) {
                i9 = i8;
                i10 = i24;
                if (i10 == 0 || i9 != 0) {
                    this.mHistory.recordStateChangeEvent(j, j2, i10, i9);
                }
                int i25 = i4 | 1;
                if (!android.view.Display.isOnState(i21)) {
                    updateTimeBasesLocked(this.mOnBatteryTimeBase.isRunning(), i21, j2 * 1000, j * 1000);
                    i11 = i22;
                    z2 = true;
                    i12 = i21;
                    noteStartWakeLocked(-1, -1, null, "screen", null, 0, false, j, j2);
                } else {
                    i11 = i22;
                    z2 = true;
                    i12 = i21;
                    if (android.view.Display.isOnState(i11)) {
                        noteStopWakeLocked(-1, -1, null, "screen", "screen", 0, j, j2);
                        updateTimeBasesLocked(this.mOnBatteryTimeBase.isRunning(), i12, j2 * 1000, j * 1000);
                    }
                }
                if (!this.mOnBatteryInternal) {
                    i7 = i12;
                    updateDischargeScreenLevelsLocked(i11, i7);
                } else {
                    i7 = i12;
                }
                i4 = i25;
                z = z2;
            } else {
                i8 |= 1048576;
                this.mScreenOnTimer.stopRunningLocked(j);
                if (this.mScreenBrightnessBin >= 0) {
                    this.mScreenBrightnessTimer[this.mScreenBrightnessBin].stopRunningLocked(j);
                }
            }
            i9 = i8;
            i10 = i24;
            if (i10 == 0) {
            }
            this.mHistory.recordStateChangeEvent(j, j2, i10, i9);
            int i252 = i4 | 1;
            if (!android.view.Display.isOnState(i21)) {
            }
            if (!this.mOnBatteryInternal) {
            }
            i4 = i252;
            z = z2;
        } else {
            i7 = i21;
        }
        maybeUpdateOverallScreenBrightness(i6, j, j2);
        if (z) {
            int length2 = this.mPerDisplayBatteryStats.length;
            int[] iArr = new int[length2];
            for (int i26 = 0; i26 < length2; i26++) {
                iArr[i26] = this.mPerDisplayBatteryStats[i26].screenState;
            }
            this.mExternalSync.scheduleSyncDueToScreenStateChange(i4, isRunning, isRunning2, i7, iArr);
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteScreenBrightnessLocked(int i, int i2) {
        noteScreenBrightnessLocked(i, i2, this.mClock.elapsedRealtime(), this.mClock.uptimeMillis());
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteScreenBrightnessLocked(int i, int i2, long j, long j2) {
        int evaluateOverallScreenBrightnessBinLocked;
        int i3 = i2 / 51;
        if (i3 < 0) {
            i3 = 0;
        } else if (i3 >= 5) {
            i3 = 4;
        }
        int length = this.mPerDisplayBatteryStats.length;
        if (i < 0 || i >= length) {
            android.util.Slog.wtf(TAG, "Unexpected note screen brightness for display " + i + " (only " + this.mPerDisplayBatteryStats.length + " displays exist...)");
            return;
        }
        com.android.server.power.stats.BatteryStatsImpl.DisplayBatteryStats displayBatteryStats = this.mPerDisplayBatteryStats[i];
        int i4 = displayBatteryStats.screenBrightnessBin;
        if (i4 == i3) {
            evaluateOverallScreenBrightnessBinLocked = this.mScreenBrightnessBin;
        } else {
            displayBatteryStats.screenBrightnessBin = i3;
            if (displayBatteryStats.screenState == 2) {
                if (i4 >= 0) {
                    displayBatteryStats.screenBrightnessTimers[i4].stopRunningLocked(j);
                }
                displayBatteryStats.screenBrightnessTimers[i3].startRunningLocked(j);
            }
            evaluateOverallScreenBrightnessBinLocked = evaluateOverallScreenBrightnessBinLocked();
        }
        maybeUpdateOverallScreenBrightness(evaluateOverallScreenBrightnessBinLocked, j, j2);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private int evaluateOverallScreenBrightnessBinLocked() {
        int i;
        int displayCount = getDisplayCount();
        int i2 = -1;
        for (int i3 = 0; i3 < displayCount; i3++) {
            if (this.mPerDisplayBatteryStats[i3].screenState == 2) {
                i = this.mPerDisplayBatteryStats[i3].screenBrightnessBin;
            } else {
                i = -1;
            }
            if (i > i2) {
                i2 = i;
            }
        }
        return i2;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void maybeUpdateOverallScreenBrightness(int i, long j, long j2) {
        if (this.mScreenBrightnessBin != i) {
            if (i >= 0) {
                this.mHistory.recordScreenBrightnessEvent(j, j2, i);
            }
            if (this.mScreenState == 2) {
                if (this.mScreenBrightnessBin >= 0) {
                    this.mScreenBrightnessTimer[this.mScreenBrightnessBin].stopRunningLocked(j);
                }
                if (i >= 0) {
                    this.mScreenBrightnessTimer[i].startRunningLocked(j);
                }
            }
            this.mScreenBrightnessBin = i;
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteUserActivityLocked(int i, int i2, long j, long j2) {
        if (this.mOnBatteryInternal) {
            getUidStatsLocked(mapUid(i), j, j2).noteUserActivityLocked(i2);
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteWakeUpLocked(java.lang.String str, int i, long j, long j2) {
        this.mHistory.recordEvent(j, j2, 18, str, i);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteInteractiveLocked(boolean z, long j) {
        if (this.mInteractive != z) {
            this.mInteractive = z;
            if (z) {
                this.mInteractiveTimer.startRunningLocked(j);
            } else {
                this.mInteractiveTimer.stopRunningLocked(j);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteConnectivityChangedLocked(int i, java.lang.String str, long j, long j2) {
        this.mHistory.recordEvent(j, j2, 9, str, i);
        this.mNumConnectivityChange++;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void noteMobileRadioApWakeupLocked(long j, long j2, int i) {
        int mapUid = mapUid(i);
        this.mHistory.recordEvent(j, j2, 19, "", mapUid);
        getUidStatsLocked(mapUid, j, j2).noteMobileRadioApWakeupLocked();
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public boolean noteMobileRadioPowerStateLocked(int i, long j, int i2) {
        return noteMobileRadioPowerStateLocked(i, j, i2, this.mClock.elapsedRealtime(), this.mClock.uptimeMillis());
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public boolean noteMobileRadioPowerStateLocked(int i, long j, int i2, long j2, long j3) {
        long j4;
        if (this.mMobileRadioPowerState != i) {
            boolean isActiveRadioPowerState = isActiveRadioPowerState(i);
            if (isActiveRadioPowerState) {
                if (i2 > 0) {
                    noteMobileRadioApWakeupLocked(j2, j3, i2);
                }
                j4 = j / 1000000;
                this.mMobileRadioActiveStartTimeMs = j4;
                this.mHistory.recordStateStartEvent(j2, j3, 33554432);
            } else {
                long j5 = j / 1000000;
                long j6 = this.mMobileRadioActiveStartTimeMs;
                if (j5 < j6) {
                    android.util.Slog.wtf(TAG, "Data connection inactive timestamp " + j5 + " is before start time " + j6);
                    j4 = j2;
                } else {
                    if (j5 < j2) {
                        this.mMobileRadioActiveAdjustedTime.addCountLocked(j2 - j5);
                    }
                    j4 = j5;
                }
                this.mHistory.recordStateStopEvent(j2, j3, 33554432);
            }
            this.mMobileRadioPowerState = i;
            getRatBatteryStatsLocked(this.mActiveRat).noteActive(isActiveRadioPowerState, j2);
            if (isActiveRadioPowerState) {
                this.mMobileRadioActiveTimer.startRunningLocked(j2);
                this.mMobileRadioActivePerAppTimer.startRunningLocked(j2);
            } else {
                this.mMobileRadioActiveTimer.stopRunningLocked(j4);
                this.mMobileRadioActivePerAppTimer.stopRunningLocked(j4);
                return this.mLastModemActivityInfo == null || j2 >= this.mLastModemActivityInfo.getTimestampMillis() + 600000;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isActiveRadioPowerState(int i) {
        return i == 2 || i == 3;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void notePowerSaveModeLockedInit(boolean z, long j, long j2) {
        if (this.mPowerSaveModeEnabled != z) {
            notePowerSaveModeLocked(z, j, j2);
        } else {
            this.mFrameworkStatsLogger.batterySaverModeChanged(z);
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void notePowerSaveModeLocked(boolean z, long j, long j2) {
        if (this.mPowerSaveModeEnabled != z) {
            int i = z ? 4 : 0;
            this.mModStepMode = ((4 & this.mCurStepMode) ^ i) | this.mModStepMode;
            this.mCurStepMode = (this.mCurStepMode & (-5)) | i;
            this.mPowerSaveModeEnabled = z;
            if (z) {
                this.mHistory.recordState2StartEvent(j, j2, Integer.MIN_VALUE);
                this.mPowerSaveModeEnabledTimer.startRunningLocked(j);
            } else {
                this.mHistory.recordState2StopEvent(j, j2, Integer.MIN_VALUE);
                this.mPowerSaveModeEnabledTimer.stopRunningLocked(j);
            }
            this.mFrameworkStatsLogger.batterySaverModeChanged(z);
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteDeviceIdleModeLocked(int i, java.lang.String str, int i2, long j, long j2) {
        boolean z;
        boolean z2;
        int i3;
        boolean z3 = i == 2;
        if (this.mDeviceIdling && !z3 && str == null) {
            z3 = true;
        }
        boolean z4 = i == 1;
        if (this.mDeviceLightIdling && !z4 && !z3 && str == null) {
            z = true;
        } else {
            z = z4;
        }
        if (str != null) {
            if (this.mDeviceIdling || this.mDeviceLightIdling) {
                z2 = z;
                this.mHistory.recordEvent(j, j2, 10, str, i2);
            } else {
                z2 = z;
            }
        } else {
            z2 = z;
        }
        if (this.mDeviceIdling != z3 || this.mDeviceLightIdling != z2) {
            if (z3) {
                i3 = 2;
            } else {
                i3 = z2 ? 1 : 0;
            }
            this.mFrameworkStatsLogger.deviceIdlingModeStateChanged(i3);
        }
        if (this.mDeviceIdling != z3) {
            this.mDeviceIdling = z3;
            int i4 = z3 ? 8 : 0;
            this.mModStepMode = ((8 & this.mCurStepMode) ^ i4) | this.mModStepMode;
            this.mCurStepMode = (this.mCurStepMode & (-9)) | i4;
            if (z3) {
                this.mDeviceIdlingTimer.startRunningLocked(j);
            } else {
                this.mDeviceIdlingTimer.stopRunningLocked(j);
            }
        }
        if (this.mDeviceLightIdling != z2) {
            this.mDeviceLightIdling = z2;
            if (z2) {
                this.mDeviceLightIdlingTimer.startRunningLocked(j);
            } else {
                this.mDeviceLightIdlingTimer.stopRunningLocked(j);
            }
        }
        if (this.mDeviceIdleMode != i) {
            this.mHistory.recordDeviceIdleEvent(j, j2, i);
            long j3 = j - this.mLastIdleTimeStartMs;
            this.mLastIdleTimeStartMs = j;
            if (this.mDeviceIdleMode == 1) {
                if (j3 > this.mLongestLightIdleTimeMs) {
                    this.mLongestLightIdleTimeMs = j3;
                }
                this.mDeviceIdleModeLightTimer.stopRunningLocked(j);
            } else if (this.mDeviceIdleMode == 2) {
                if (j3 > this.mLongestFullIdleTimeMs) {
                    this.mLongestFullIdleTimeMs = j3;
                }
                this.mDeviceIdleModeFullTimer.stopRunningLocked(j);
            }
            if (i == 1) {
                this.mDeviceIdleModeLightTimer.startRunningLocked(j);
            } else if (i == 2) {
                this.mDeviceIdleModeFullTimer.startRunningLocked(j);
            }
            this.mDeviceIdleMode = i;
            this.mFrameworkStatsLogger.deviceIdleModeStateChanged(i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void notePackageInstalledLocked(java.lang.String str, long j, long j2, long j3) {
        this.mHistory.recordEvent(j2, j3, 11, str, (int) j);
        android.os.BatteryStats.PackageChange packageChange = new android.os.BatteryStats.PackageChange();
        packageChange.mPackageName = str;
        packageChange.mUpdate = true;
        packageChange.mVersionCode = j;
        addPackageChange(packageChange);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void notePackageUninstalledLocked(java.lang.String str, long j, long j2) {
        this.mHistory.recordEvent(j, j2, 12, str, 0);
        android.os.BatteryStats.PackageChange packageChange = new android.os.BatteryStats.PackageChange();
        packageChange.mPackageName = str;
        packageChange.mUpdate = true;
        addPackageChange(packageChange);
    }

    private void addPackageChange(android.os.BatteryStats.PackageChange packageChange) {
        if (this.mDailyPackageChanges == null) {
            this.mDailyPackageChanges = new java.util.ArrayList<>();
        }
        this.mDailyPackageChanges.add(packageChange);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    void stopAllGpsSignalQualityTimersLocked(int i, long j) {
        for (int i2 = 0; i2 < this.mGpsSignalQualityTimer.length; i2++) {
            if (i2 != i) {
                while (this.mGpsSignalQualityTimer[i2].isRunningLocked()) {
                    this.mGpsSignalQualityTimer[i2].stopRunningLocked(j);
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void notePhoneOnLocked(long j, long j2) {
        if (!this.mPhoneOn) {
            this.mHistory.recordState2StartEvent(j, j2, 8388608);
            this.mPhoneOn = true;
            this.mPhoneOnTimer.startRunningLocked(j);
            if (this.mConstants.PHONE_ON_EXTERNAL_STATS_COLLECTION) {
                scheduleSyncExternalStatsLocked("phone-on", 4);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void notePhoneOffLocked(long j, long j2) {
        if (this.mPhoneOn) {
            this.mHistory.recordState2StopEvent(j, j2, 8388608);
            this.mPhoneOn = false;
            this.mPhoneOnTimer.stopRunningLocked(j);
            scheduleSyncExternalStatsLocked("phone-off", 4);
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void registerUsbStateReceiver(android.content.Context context) {
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.hardware.usb.action.USB_STATE");
        context.registerReceiver(new android.content.BroadcastReceiver() { // from class: com.android.server.power.stats.BatteryStatsImpl.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context2, android.content.Intent intent) {
                boolean booleanExtra = intent.getBooleanExtra("connected", false);
                synchronized (com.android.server.power.stats.BatteryStatsImpl.this) {
                    com.android.server.power.stats.BatteryStatsImpl.this.noteUsbConnectionStateLocked(booleanExtra, com.android.server.power.stats.BatteryStatsImpl.this.mClock.elapsedRealtime(), com.android.server.power.stats.BatteryStatsImpl.this.mClock.uptimeMillis());
                }
            }
        }, intentFilter);
        synchronized (this) {
            try {
                if (this.mUsbDataState == 0) {
                    android.content.Intent registerReceiver = context.registerReceiver(null, intentFilter);
                    boolean z = false;
                    if (registerReceiver != null && registerReceiver.getBooleanExtra("connected", false)) {
                        z = true;
                    }
                    noteUsbConnectionStateLocked(z, this.mClock.elapsedRealtime(), this.mClock.uptimeMillis());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteUsbConnectionStateLocked(boolean z, long j, long j2) {
        int i = z ? 2 : 1;
        if (this.mUsbDataState != i) {
            this.mUsbDataState = i;
            if (z) {
                this.mHistory.recordState2StartEvent(j, j2, 262144);
            } else {
                this.mHistory.recordState2StopEvent(j, j2, 262144);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    void stopAllPhoneSignalStrengthTimersLocked(int i, long j) {
        for (int i2 = 0; i2 < CELL_SIGNAL_STRENGTH_LEVEL_COUNT; i2++) {
            if (i2 != i) {
                while (this.mPhoneSignalStrengthsTimer[i2].isRunningLocked()) {
                    this.mPhoneSignalStrengthsTimer[i2].stopRunningLocked(j);
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void updateAllPhoneStateLocked(int i, int i2, int i3, long j, long j2) {
        int i4;
        boolean z;
        boolean z2;
        int i5;
        int i6 = i;
        int i7 = i3;
        this.mPhoneServiceStateRaw = i6;
        this.mPhoneSimStateRaw = i2;
        this.mPhoneSignalStrengthBinRaw = i7;
        int i8 = 0;
        boolean z3 = true;
        if (i2 == 1 && i6 == 1 && i7 > 0) {
            i6 = 0;
        }
        int i9 = -1;
        if (i6 == 3) {
            i4 = 0;
            z = false;
            z2 = false;
            i7 = -1;
        } else if (i6 != 0 && i6 == 1) {
            if (this.mPhoneSignalScanningTimer.isRunningLocked()) {
                i7 = 0;
                i4 = 0;
                z2 = false;
                z = true;
            } else {
                this.mPhoneSignalScanningTimer.startRunningLocked(j);
                this.mFrameworkStatsLogger.phoneServiceStateChanged(i6, i2, 0);
                i7 = 0;
                z = true;
                z2 = true;
                i4 = 2097152;
            }
        } else {
            i4 = 0;
            z = false;
            z2 = false;
        }
        if (!z && this.mPhoneSignalScanningTimer.isRunningLocked()) {
            this.mPhoneSignalScanningTimer.stopRunningLocked(j);
            this.mFrameworkStatsLogger.phoneServiceStateChanged(i6, i2, i7);
            z2 = true;
            i8 = 2097152;
        }
        if (this.mPhoneServiceState == i6) {
            i5 = -1;
        } else {
            this.mPhoneServiceState = i6;
            i5 = i6;
            z2 = true;
        }
        if (this.mPhoneSignalStrengthBin != i7) {
            if (this.mPhoneSignalStrengthBin >= 0) {
                this.mPhoneSignalStrengthsTimer[this.mPhoneSignalStrengthBin].stopRunningLocked(j);
            }
            if (i7 >= 0) {
                if (!this.mPhoneSignalStrengthsTimer[i7].isRunningLocked()) {
                    this.mPhoneSignalStrengthsTimer[i7].startRunningLocked(j);
                }
                this.mFrameworkStatsLogger.phoneSignalStrengthChanged(i7);
                i9 = i7;
            } else {
                stopAllPhoneSignalStrengthTimersLocked(-1, j);
                z3 = z2;
            }
            this.mPhoneSignalStrengthBin = i7;
            z2 = z3;
        }
        if (z2) {
            this.mHistory.recordPhoneStateChangeEvent(j, j2, i4, i8, i5, i9);
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void notePhoneStateLocked(int i, int i2, long j, long j2) {
        updateAllPhoneStateLocked(i, i2, this.mPhoneSignalStrengthBinRaw, j, j2);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void notePhoneSignalStrengthLocked(android.telephony.SignalStrength signalStrength, long j, long j2) {
        int level;
        int i;
        int level2 = signalStrength.getLevel();
        android.util.SparseIntArray sparseIntArray = new android.util.SparseIntArray(3);
        java.util.List<android.telephony.CellSignalStrength> cellSignalStrengths = signalStrength.getCellSignalStrengths();
        int size = cellSignalStrengths.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.telephony.CellSignalStrength cellSignalStrength = cellSignalStrengths.get(i2);
            if (cellSignalStrength instanceof android.telephony.CellSignalStrengthNr) {
                level = cellSignalStrength.getLevel();
                i = 2;
            } else if (cellSignalStrength instanceof android.telephony.CellSignalStrengthLte) {
                level = cellSignalStrength.getLevel();
                i = 1;
            } else {
                level = cellSignalStrength.getLevel();
                i = 0;
            }
            if (sparseIntArray.get(i, -1) < level) {
                sparseIntArray.put(i, level);
            }
        }
        notePhoneSignalStrengthLocked(level2, sparseIntArray, j, j2);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void notePhoneSignalStrengthLocked(int i, android.util.SparseIntArray sparseIntArray) {
        notePhoneSignalStrengthLocked(i, sparseIntArray, this.mClock.elapsedRealtime(), this.mClock.uptimeMillis());
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void notePhoneSignalStrengthLocked(int i, android.util.SparseIntArray sparseIntArray, long j, long j2) {
        int size = sparseIntArray.size();
        for (int i2 = 0; i2 < size; i2++) {
            int keyAt = sparseIntArray.keyAt(i2);
            getRatBatteryStatsLocked(keyAt).noteSignalStrength(sparseIntArray.valueAt(i2), j);
        }
        updateAllPhoneStateLocked(this.mPhoneServiceStateRaw, this.mPhoneSimStateRaw, i, j, j2);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void notePhoneDataConnectionStateLocked(int i, boolean z, int i2, int i3, int i4) {
        notePhoneDataConnectionStateLocked(i, z, i2, i3, i4, this.mClock.elapsedRealtime(), this.mClock.uptimeMillis());
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void notePhoneDataConnectionStateLocked(int i, boolean z, int i2, int i3, int i4, long j, long j2) {
        int i5;
        if (!z) {
            i5 = 0;
        } else if (i > 0 && i <= android.os.BatteryStats.NUM_ALL_NETWORK_TYPES) {
            i5 = i;
        } else {
            switch (i2) {
                case 1:
                    i5 = 0;
                    break;
                case 2:
                    i5 = android.os.BatteryStats.DATA_CONNECTION_EMERGENCY_SERVICE;
                    break;
                default:
                    i5 = android.os.BatteryStats.DATA_CONNECTION_OTHER;
                    break;
            }
        }
        if (this.mPhoneDataConnectionType != i5) {
            this.mHistory.recordDataConnectionTypeChangeEvent(j, j2, i5);
            if (this.mPhoneDataConnectionType >= 0) {
                this.mPhoneDataConnectionsTimer[this.mPhoneDataConnectionType].stopRunningLocked(j);
            }
            this.mPhoneDataConnectionType = i5;
            this.mPhoneDataConnectionsTimer[i5].startRunningLocked(j);
        }
        if (this.mNrState != i3) {
            this.mHistory.recordNrStateChangeEvent(j, j2, i3);
            this.mNrState = i3;
        }
        boolean isNrNsa = isNrNsa(i5, i3);
        if (isNrNsa != this.mNrNsaTimer.isRunningLocked()) {
            if (isNrNsa) {
                this.mNrNsaTimer.startRunningLocked(j);
            } else {
                this.mNrNsaTimer.stopRunningLocked(j);
            }
        }
        int mapNetworkTypeToRadioAccessTechnology = mapNetworkTypeToRadioAccessTechnology(i5, i3);
        if (mapNetworkTypeToRadioAccessTechnology == 2) {
            getRatBatteryStatsLocked(mapNetworkTypeToRadioAccessTechnology).noteFrequencyRange(i4, j);
        }
        if (this.mActiveRat != mapNetworkTypeToRadioAccessTechnology) {
            getRatBatteryStatsLocked(this.mActiveRat).noteActive(false, j);
            this.mActiveRat = mapNetworkTypeToRadioAccessTechnology;
        }
        getRatBatteryStatsLocked(mapNetworkTypeToRadioAccessTechnology).noteActive(this.mMobileRadioActiveTimer.isRunningLocked(), j);
    }

    private static boolean isNrNsa(int i, int i2) {
        return i == 13 && i2 == 3;
    }

    private static int mapNetworkTypeToRadioAccessTechnology(int i, int i2) {
        if (isNrNsa(i, i2)) {
            return 2;
        }
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
                return 0;
            case 13:
                return 1;
            case 19:
            default:
                android.util.Slog.w(TAG, "Unhandled NetworkType (" + i + "), mapping to OTHER");
                return 0;
            case 20:
                return 2;
        }
    }

    private static int mapRadioAccessNetworkTypeToRadioAccessTechnology(int i) {
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 4:
            case 5:
                break;
            case 3:
                break;
            case 6:
                break;
            default:
                android.util.Slog.w(TAG, "Unhandled RadioAccessNetworkType (" + i + "), mapping to OTHER");
                break;
        }
        return 0;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteWifiOnLocked(long j, long j2) {
        if (!this.mWifiOn) {
            this.mHistory.recordState2StartEvent(j, j2, 268435456);
            this.mWifiOn = true;
            this.mWifiOnTimer.startRunningLocked(j);
            scheduleSyncExternalStatsLocked("wifi-off", 2);
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteWifiOffLocked(long j, long j2) {
        if (this.mWifiOn) {
            this.mHistory.recordState2StopEvent(j, j2, 268435456);
            this.mWifiOn = false;
            this.mWifiOnTimer.stopRunningLocked(j);
            scheduleSyncExternalStatsLocked("wifi-on", 2);
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteAudioOnLocked(int i, long j, long j2) {
        int mapUid = mapUid(i);
        if (this.mAudioOnNesting == 0) {
            this.mHistory.recordStateStartEvent(j, j2, 4194304);
            this.mAudioOnTimer.startRunningLocked(j);
        }
        this.mAudioOnNesting++;
        getUidStatsLocked(mapUid, j, j2).noteAudioTurnedOnLocked(j);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteAudioOffLocked(int i, long j, long j2) {
        if (this.mAudioOnNesting == 0) {
            return;
        }
        int mapUid = mapUid(i);
        int i2 = this.mAudioOnNesting - 1;
        this.mAudioOnNesting = i2;
        if (i2 == 0) {
            this.mHistory.recordStateStopEvent(j, j2, 4194304);
            this.mAudioOnTimer.stopRunningLocked(j);
        }
        getUidStatsLocked(mapUid, j, j2).noteAudioTurnedOffLocked(j);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteVideoOnLocked(int i, long j, long j2) {
        int mapUid = mapUid(i);
        if (this.mVideoOnNesting == 0) {
            this.mHistory.recordState2StartEvent(j, j2, 1073741824);
            this.mVideoOnTimer.startRunningLocked(j);
        }
        this.mVideoOnNesting++;
        getUidStatsLocked(mapUid, j, j2).noteVideoTurnedOnLocked(j);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteVideoOffLocked(int i, long j, long j2) {
        if (this.mVideoOnNesting == 0) {
            return;
        }
        int mapUid = mapUid(i);
        int i2 = this.mVideoOnNesting - 1;
        this.mVideoOnNesting = i2;
        if (i2 == 0) {
            this.mHistory.recordState2StopEvent(j, j2, 1073741824);
            this.mVideoOnTimer.stopRunningLocked(j);
        }
        getUidStatsLocked(mapUid, j, j2).noteVideoTurnedOffLocked(j);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteResetAudioLocked(long j, long j2) {
        if (this.mAudioOnNesting > 0) {
            this.mAudioOnNesting = 0;
            this.mHistory.recordStateStopEvent(j, j2, 4194304);
            this.mAudioOnTimer.stopAllRunningLocked(j);
            for (int i = 0; i < this.mUidStats.size(); i++) {
                this.mUidStats.valueAt(i).noteResetAudioLocked(j);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteResetVideoLocked(long j, long j2) {
        if (this.mVideoOnNesting > 0) {
            this.mVideoOnNesting = 0;
            this.mHistory.recordState2StopEvent(j, j2, 1073741824);
            this.mVideoOnTimer.stopAllRunningLocked(j);
            for (int i = 0; i < this.mUidStats.size(); i++) {
                this.mUidStats.valueAt(i).noteResetVideoLocked(j);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteActivityResumedLocked(int i) {
        noteActivityResumedLocked(i, this.mClock.elapsedRealtime(), this.mClock.uptimeMillis());
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteActivityResumedLocked(int i, long j, long j2) {
        getUidStatsLocked(mapUid(i), j, j2).noteActivityResumedLocked(j);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteActivityPausedLocked(int i) {
        noteActivityPausedLocked(i, this.mClock.elapsedRealtime(), this.mClock.uptimeMillis());
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteActivityPausedLocked(int i, long j, long j2) {
        getUidStatsLocked(mapUid(i), j, j2).noteActivityPausedLocked(j);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteVibratorOnLocked(int i, long j, long j2, long j3) {
        getUidStatsLocked(mapUid(i), j2, j3).noteVibratorOnLocked(j, j2);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteVibratorOffLocked(int i, long j, long j2) {
        getUidStatsLocked(mapUid(i), j, j2).noteVibratorOffLocked(j);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteFlashlightOnLocked(int i, long j, long j2) {
        int mapUid = mapUid(i);
        int i2 = this.mFlashlightOnNesting;
        this.mFlashlightOnNesting = i2 + 1;
        if (i2 == 0) {
            this.mHistory.recordState2StartEvent(j, j2, 134217728);
            this.mFlashlightOnTimer.startRunningLocked(j);
        }
        getUidStatsLocked(mapUid, j, j2).noteFlashlightTurnedOnLocked(j);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteFlashlightOffLocked(int i, long j, long j2) {
        if (this.mFlashlightOnNesting == 0) {
            return;
        }
        int mapUid = mapUid(i);
        int i2 = this.mFlashlightOnNesting - 1;
        this.mFlashlightOnNesting = i2;
        if (i2 == 0) {
            this.mHistory.recordState2StopEvent(j, j2, 134217728);
            this.mFlashlightOnTimer.stopRunningLocked(j);
        }
        getUidStatsLocked(mapUid, j, j2).noteFlashlightTurnedOffLocked(j);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteCameraOnLocked(int i, long j, long j2) {
        int mapUid = mapUid(i);
        int i2 = this.mCameraOnNesting;
        this.mCameraOnNesting = i2 + 1;
        if (i2 == 0) {
            this.mHistory.recordState2StartEvent(j, j2, 2097152);
            this.mCameraOnTimer.startRunningLocked(j);
        }
        getUidStatsLocked(mapUid, j, j2).noteCameraTurnedOnLocked(j);
        scheduleSyncExternalStatsLocked("camera-on", 64);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteCameraOffLocked(int i, long j, long j2) {
        if (this.mCameraOnNesting == 0) {
            return;
        }
        int mapUid = mapUid(i);
        int i2 = this.mCameraOnNesting - 1;
        this.mCameraOnNesting = i2;
        if (i2 == 0) {
            this.mHistory.recordState2StopEvent(j, j2, 2097152);
            this.mCameraOnTimer.stopRunningLocked(j);
        }
        getUidStatsLocked(mapUid, j, j2).noteCameraTurnedOffLocked(j);
        scheduleSyncExternalStatsLocked("camera-off", 64);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteResetCameraLocked(long j, long j2) {
        if (this.mCameraOnNesting > 0) {
            this.mCameraOnNesting = 0;
            this.mHistory.recordState2StopEvent(j, j2, 2097152);
            this.mCameraOnTimer.stopAllRunningLocked(j);
            for (int i = 0; i < this.mUidStats.size(); i++) {
                this.mUidStats.valueAt(i).noteResetCameraLocked(j);
            }
        }
        scheduleSyncExternalStatsLocked("camera-reset", 64);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteResetFlashlightLocked(long j, long j2) {
        if (this.mFlashlightOnNesting > 0) {
            this.mFlashlightOnNesting = 0;
            this.mHistory.recordState2StopEvent(j, j2, 134217728);
            this.mFlashlightOnTimer.stopAllRunningLocked(j);
            for (int i = 0; i < this.mUidStats.size(); i++) {
                this.mUidStats.valueAt(i).noteResetFlashlightLocked(j);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void noteBluetoothScanStartedLocked(android.os.WorkSource.WorkChain workChain, int i, boolean z, long j, long j2) {
        if (workChain != null) {
            i = workChain.getAttributionUid();
        }
        int mapUid = mapUid(i);
        if (this.mBluetoothScanNesting == 0) {
            this.mHistory.recordState2StartEvent(j, j2, 1048576);
            this.mBluetoothScanTimer.startRunningLocked(j);
        }
        this.mBluetoothScanNesting++;
        getUidStatsLocked(mapUid, j, j2).noteBluetoothScanStartedLocked(j, z);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteBluetoothScanStartedFromSourceLocked(android.os.WorkSource workSource, boolean z) {
        noteBluetoothScanStartedFromSourceLocked(workSource, z, this.mClock.elapsedRealtime(), this.mClock.uptimeMillis());
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteBluetoothScanStartedFromSourceLocked(android.os.WorkSource workSource, boolean z, long j, long j2) {
        int size = workSource.size();
        for (int i = 0; i < size; i++) {
            noteBluetoothScanStartedLocked(null, workSource.getUid(i), z, j, j2);
        }
        java.util.List workChains = workSource.getWorkChains();
        if (workChains != null) {
            for (int i2 = 0; i2 < workChains.size(); i2++) {
                noteBluetoothScanStartedLocked((android.os.WorkSource.WorkChain) workChains.get(i2), -1, z, j, j2);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void noteBluetoothScanStoppedLocked(android.os.WorkSource.WorkChain workChain, int i, boolean z, long j, long j2) {
        if (workChain != null) {
            i = workChain.getAttributionUid();
        }
        int mapUid = mapUid(i);
        this.mBluetoothScanNesting--;
        if (this.mBluetoothScanNesting == 0) {
            this.mHistory.recordState2StopEvent(j, j2, 1048576);
            this.mBluetoothScanTimer.stopRunningLocked(j);
        }
        getUidStatsLocked(mapUid, j, j2).noteBluetoothScanStoppedLocked(j, z);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteBluetoothScanStoppedFromSourceLocked(android.os.WorkSource workSource, boolean z) {
        noteBluetoothScanStoppedFromSourceLocked(workSource, z, this.mClock.elapsedRealtime(), this.mClock.uptimeMillis());
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteBluetoothScanStoppedFromSourceLocked(android.os.WorkSource workSource, boolean z, long j, long j2) {
        int size = workSource.size();
        for (int i = 0; i < size; i++) {
            noteBluetoothScanStoppedLocked(null, workSource.getUid(i), z, j, j2);
        }
        java.util.List workChains = workSource.getWorkChains();
        if (workChains != null) {
            for (int i2 = 0; i2 < workChains.size(); i2++) {
                noteBluetoothScanStoppedLocked((android.os.WorkSource.WorkChain) workChains.get(i2), -1, z, j, j2);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteResetBluetoothScanLocked(long j, long j2) {
        if (this.mBluetoothScanNesting > 0) {
            this.mBluetoothScanNesting = 0;
            this.mHistory.recordState2StopEvent(j, j2, 1048576);
            this.mBluetoothScanTimer.stopAllRunningLocked(j);
            for (int i = 0; i < this.mUidStats.size(); i++) {
                this.mUidStats.valueAt(i).noteResetBluetoothScanLocked(j);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteBluetoothScanResultsFromSourceLocked(android.os.WorkSource workSource, int i) {
        noteBluetoothScanResultsFromSourceLocked(workSource, i, this.mClock.elapsedRealtime(), this.mClock.uptimeMillis());
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteBluetoothScanResultsFromSourceLocked(android.os.WorkSource workSource, int i, long j, long j2) {
        int size = workSource.size();
        for (int i2 = 0; i2 < size; i2++) {
            getUidStatsLocked(mapUid(workSource.getUid(i2)), j, j2).noteBluetoothScanResultsLocked(i);
        }
        java.util.List workChains = workSource.getWorkChains();
        if (workChains != null) {
            for (int i3 = 0; i3 < workChains.size(); i3++) {
                getUidStatsLocked(mapUid(((android.os.WorkSource.WorkChain) workChains.get(i3)).getAttributionUid()), j, j2).noteBluetoothScanResultsLocked(i);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void noteWifiRadioApWakeupLocked(long j, long j2, int i) {
        int mapUid = mapUid(i);
        this.mHistory.recordEvent(j, j2, 19, "", mapUid);
        getUidStatsLocked(mapUid, j, j2).noteWifiRadioApWakeupLocked();
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteWifiRadioPowerState(int i, long j, int i2, long j2, long j3) {
        if (this.mWifiRadioPowerState != i) {
            if (i == 2 || i == 3) {
                if (i2 > 0) {
                    noteWifiRadioApWakeupLocked(j2, j3, i2);
                }
                this.mHistory.recordStateStartEvent(j2, j3, 67108864);
                this.mWifiActiveTimer.startRunningLocked(j2);
            } else {
                this.mHistory.recordStateStopEvent(j2, j3, 67108864);
                this.mWifiActiveTimer.stopRunningLocked(j / 1000000);
            }
            this.mWifiRadioPowerState = i;
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteWifiRunningLocked(android.os.WorkSource workSource, long j, long j2) {
        if (!this.mGlobalWifiRunning) {
            this.mHistory.recordState2StartEvent(j, j2, 536870912);
            this.mGlobalWifiRunning = true;
            this.mGlobalWifiRunningTimer.startRunningLocked(j);
            int size = workSource.size();
            for (int i = 0; i < size; i++) {
                getUidStatsLocked(mapUid(workSource.getUid(i)), j, j2).noteWifiRunningLocked(j);
            }
            java.util.List workChains = workSource.getWorkChains();
            if (workChains != null) {
                for (int i2 = 0; i2 < workChains.size(); i2++) {
                    getUidStatsLocked(mapUid(((android.os.WorkSource.WorkChain) workChains.get(i2)).getAttributionUid()), j, j2).noteWifiRunningLocked(j);
                }
            }
            scheduleSyncExternalStatsLocked("wifi-running", 2);
            return;
        }
        android.util.Log.w(TAG, "noteWifiRunningLocked -- called while WIFI running");
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteWifiRunningChangedLocked(android.os.WorkSource workSource, android.os.WorkSource workSource2, long j, long j2) {
        if (this.mGlobalWifiRunning) {
            int size = workSource.size();
            for (int i = 0; i < size; i++) {
                getUidStatsLocked(mapUid(workSource.getUid(i)), j, j2).noteWifiStoppedLocked(j);
            }
            java.util.List workChains = workSource.getWorkChains();
            if (workChains != null) {
                for (int i2 = 0; i2 < workChains.size(); i2++) {
                    getUidStatsLocked(mapUid(((android.os.WorkSource.WorkChain) workChains.get(i2)).getAttributionUid()), j, j2).noteWifiStoppedLocked(j);
                }
            }
            int size2 = workSource2.size();
            for (int i3 = 0; i3 < size2; i3++) {
                getUidStatsLocked(mapUid(workSource2.getUid(i3)), j, j2).noteWifiRunningLocked(j);
            }
            java.util.List workChains2 = workSource2.getWorkChains();
            if (workChains2 != null) {
                for (int i4 = 0; i4 < workChains2.size(); i4++) {
                    getUidStatsLocked(mapUid(((android.os.WorkSource.WorkChain) workChains2.get(i4)).getAttributionUid()), j, j2).noteWifiRunningLocked(j);
                }
                return;
            }
            return;
        }
        android.util.Log.w(TAG, "noteWifiRunningChangedLocked -- called while WIFI not running");
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteWifiStoppedLocked(android.os.WorkSource workSource, long j, long j2) {
        if (this.mGlobalWifiRunning) {
            this.mHistory.recordState2StopEvent(j, j2, 536870912);
            this.mGlobalWifiRunning = false;
            this.mGlobalWifiRunningTimer.stopRunningLocked(j);
            int size = workSource.size();
            for (int i = 0; i < size; i++) {
                getUidStatsLocked(mapUid(workSource.getUid(i)), j, j2).noteWifiStoppedLocked(j);
            }
            java.util.List workChains = workSource.getWorkChains();
            if (workChains != null) {
                for (int i2 = 0; i2 < workChains.size(); i2++) {
                    getUidStatsLocked(mapUid(((android.os.WorkSource.WorkChain) workChains.get(i2)).getAttributionUid()), j, j2).noteWifiStoppedLocked(j);
                }
            }
            scheduleSyncExternalStatsLocked("wifi-stopped", 2);
            return;
        }
        android.util.Log.w(TAG, "noteWifiStoppedLocked -- called while WIFI not running");
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteWifiStateLocked(int i, java.lang.String str, long j) {
        if (this.mWifiState != i) {
            if (this.mWifiState >= 0) {
                this.mWifiStateTimer[this.mWifiState].stopRunningLocked(j);
            }
            this.mWifiState = i;
            this.mWifiStateTimer[i].startRunningLocked(j);
            scheduleSyncExternalStatsLocked("wifi-state", 2);
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteWifiSupplicantStateChangedLocked(int i, boolean z, long j, long j2) {
        if (this.mWifiSupplState != i) {
            if (this.mWifiSupplState >= 0) {
                this.mWifiSupplStateTimer[this.mWifiSupplState].stopRunningLocked(j);
            }
            this.mWifiSupplState = i;
            this.mWifiSupplStateTimer[i].startRunningLocked(j);
            this.mHistory.recordWifiSupplicantStateChangeEvent(j, j2, i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    void stopAllWifiSignalStrengthTimersLocked(int i, long j) {
        for (int i2 = 0; i2 < 5; i2++) {
            if (i2 != i) {
                while (this.mWifiSignalStrengthsTimer[i2].isRunningLocked()) {
                    this.mWifiSignalStrengthsTimer[i2].stopRunningLocked(j);
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteWifiRssiChangedLocked(int i, long j, long j2) {
        int calculateSignalLevel = android.net.wifi.WifiManager.calculateSignalLevel(i, 5);
        if (this.mWifiSignalStrengthBin != calculateSignalLevel) {
            if (this.mWifiSignalStrengthBin >= 0) {
                this.mWifiSignalStrengthsTimer[this.mWifiSignalStrengthBin].stopRunningLocked(j);
            }
            if (calculateSignalLevel >= 0) {
                if (!this.mWifiSignalStrengthsTimer[calculateSignalLevel].isRunningLocked()) {
                    this.mWifiSignalStrengthsTimer[calculateSignalLevel].startRunningLocked(j);
                }
                this.mHistory.recordWifiSignalStrengthChangeEvent(j, j2, calculateSignalLevel);
            } else {
                stopAllWifiSignalStrengthTimersLocked(-1, j);
            }
            this.mWifiSignalStrengthBin = calculateSignalLevel;
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteFullWifiLockAcquiredLocked(int i, long j, long j2) {
        if (this.mWifiFullLockNesting == 0) {
            this.mHistory.recordStateStartEvent(j, j2, 268435456);
        }
        this.mWifiFullLockNesting++;
        getUidStatsLocked(i, j, j2).noteFullWifiLockAcquiredLocked(j);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteFullWifiLockReleasedLocked(int i, long j, long j2) {
        this.mWifiFullLockNesting--;
        if (this.mWifiFullLockNesting == 0) {
            this.mHistory.recordStateStopEvent(j, j2, 268435456);
        }
        getUidStatsLocked(i, j, j2).noteFullWifiLockReleasedLocked(j);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteWifiScanStartedLocked(int i) {
        noteWifiScanStartedLocked(i, this.mClock.elapsedRealtime(), this.mClock.uptimeMillis());
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteWifiScanStartedLocked(int i, long j, long j2) {
        if (this.mWifiScanNesting == 0) {
            this.mHistory.recordStateStartEvent(j, j2, 134217728);
        }
        this.mWifiScanNesting++;
        getUidStatsLocked(i, j, j2).noteWifiScanStartedLocked(j);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteWifiScanStoppedLocked(int i) {
        noteWifiScanStoppedLocked(i, this.mClock.elapsedRealtime(), this.mClock.uptimeMillis());
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteWifiScanStoppedLocked(int i, long j, long j2) {
        this.mWifiScanNesting--;
        if (this.mWifiScanNesting == 0) {
            this.mHistory.recordStateStopEvent(j, j2, 134217728);
        }
        getUidStatsLocked(i, j, j2).noteWifiScanStoppedLocked(j);
    }

    public void noteWifiBatchedScanStartedLocked(int i, int i2, long j, long j2) {
        getUidStatsLocked(mapUid(i), j, j2).noteWifiBatchedScanStartedLocked(i2, j);
    }

    public void noteWifiBatchedScanStoppedLocked(int i, long j, long j2) {
        getUidStatsLocked(mapUid(i), j, j2).noteWifiBatchedScanStoppedLocked(j);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteWifiMulticastEnabledLocked(int i, long j, long j2) {
        int mapUid = mapUid(i);
        if (this.mWifiMulticastNesting == 0) {
            this.mHistory.recordStateStartEvent(j, j2, 65536);
            if (!this.mWifiMulticastWakelockTimer.isRunningLocked()) {
                this.mWifiMulticastWakelockTimer.startRunningLocked(j);
            }
        }
        this.mWifiMulticastNesting++;
        getUidStatsLocked(mapUid, j, j2).noteWifiMulticastEnabledLocked(j);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteWifiMulticastDisabledLocked(int i, long j, long j2) {
        int mapUid = mapUid(i);
        this.mWifiMulticastNesting--;
        if (this.mWifiMulticastNesting == 0) {
            this.mHistory.recordStateStopEvent(j, j2, 65536);
            if (this.mWifiMulticastWakelockTimer.isRunningLocked()) {
                this.mWifiMulticastWakelockTimer.stopRunningLocked(j);
            }
        }
        getUidStatsLocked(mapUid, j, j2).noteWifiMulticastDisabledLocked(j);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteFullWifiLockAcquiredFromSourceLocked(android.os.WorkSource workSource, long j, long j2) {
        int size = workSource.size();
        for (int i = 0; i < size; i++) {
            noteFullWifiLockAcquiredLocked(mapUid(workSource.getUid(i)), j, j2);
        }
        java.util.List workChains = workSource.getWorkChains();
        if (workChains != null) {
            for (int i2 = 0; i2 < workChains.size(); i2++) {
                noteFullWifiLockAcquiredLocked(mapUid(((android.os.WorkSource.WorkChain) workChains.get(i2)).getAttributionUid()), j, j2);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteFullWifiLockReleasedFromSourceLocked(android.os.WorkSource workSource, long j, long j2) {
        int size = workSource.size();
        for (int i = 0; i < size; i++) {
            noteFullWifiLockReleasedLocked(mapUid(workSource.getUid(i)), j, j2);
        }
        java.util.List workChains = workSource.getWorkChains();
        if (workChains != null) {
            for (int i2 = 0; i2 < workChains.size(); i2++) {
                noteFullWifiLockReleasedLocked(mapUid(((android.os.WorkSource.WorkChain) workChains.get(i2)).getAttributionUid()), j, j2);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteWifiScanStartedFromSourceLocked(android.os.WorkSource workSource, long j, long j2) {
        int size = workSource.size();
        for (int i = 0; i < size; i++) {
            noteWifiScanStartedLocked(mapUid(workSource.getUid(i)), j, j2);
        }
        java.util.List workChains = workSource.getWorkChains();
        if (workChains != null) {
            for (int i2 = 0; i2 < workChains.size(); i2++) {
                noteWifiScanStartedLocked(mapUid(((android.os.WorkSource.WorkChain) workChains.get(i2)).getAttributionUid()), j, j2);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteWifiScanStoppedFromSourceLocked(android.os.WorkSource workSource, long j, long j2) {
        int size = workSource.size();
        for (int i = 0; i < size; i++) {
            noteWifiScanStoppedLocked(mapUid(workSource.getUid(i)), j, j2);
        }
        java.util.List workChains = workSource.getWorkChains();
        if (workChains != null) {
            for (int i2 = 0; i2 < workChains.size(); i2++) {
                noteWifiScanStoppedLocked(mapUid(((android.os.WorkSource.WorkChain) workChains.get(i2)).getAttributionUid()), j, j2);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteWifiBatchedScanStartedFromSourceLocked(android.os.WorkSource workSource, int i, long j, long j2) {
        int size = workSource.size();
        for (int i2 = 0; i2 < size; i2++) {
            noteWifiBatchedScanStartedLocked(workSource.getUid(i2), i, j, j2);
        }
        java.util.List workChains = workSource.getWorkChains();
        if (workChains != null) {
            for (int i3 = 0; i3 < workChains.size(); i3++) {
                noteWifiBatchedScanStartedLocked(((android.os.WorkSource.WorkChain) workChains.get(i3)).getAttributionUid(), i, j, j2);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void noteWifiBatchedScanStoppedFromSourceLocked(android.os.WorkSource workSource, long j, long j2) {
        int size = workSource.size();
        for (int i = 0; i < size; i++) {
            noteWifiBatchedScanStoppedLocked(workSource.getUid(i), j, j2);
        }
        java.util.List workChains = workSource.getWorkChains();
        if (workChains != null) {
            for (int i2 = 0; i2 < workChains.size(); i2++) {
                noteWifiBatchedScanStoppedLocked(((android.os.WorkSource.WorkChain) workChains.get(i2)).getAttributionUid(), j, j2);
            }
        }
    }

    private static java.lang.String[] includeInStringArray(java.lang.String[] strArr, java.lang.String str) {
        if (com.android.internal.util.ArrayUtils.indexOf(strArr, str) >= 0) {
            return strArr;
        }
        java.lang.String[] strArr2 = new java.lang.String[strArr.length + 1];
        java.lang.System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
        strArr2[strArr.length] = str;
        return strArr2;
    }

    private static java.lang.String[] excludeFromStringArray(java.lang.String[] strArr, java.lang.String str) {
        int indexOf = com.android.internal.util.ArrayUtils.indexOf(strArr, str);
        if (indexOf >= 0) {
            java.lang.String[] strArr2 = new java.lang.String[strArr.length - 1];
            if (indexOf > 0) {
                java.lang.System.arraycopy(strArr, 0, strArr2, 0, indexOf);
            }
            if (indexOf < strArr.length - 1) {
                java.lang.System.arraycopy(strArr, indexOf + 1, strArr2, indexOf, (strArr.length - indexOf) - 1);
            }
            return strArr2;
        }
        return strArr;
    }

    public void noteNetworkInterfaceForTransports(java.lang.String str, int[] iArr) {
        if (android.text.TextUtils.isEmpty(str)) {
            return;
        }
        int displayTransport = android.os.BatteryStats.getDisplayTransport(iArr);
        synchronized (this.mModemNetworkLock) {
            try {
                if (displayTransport == 0) {
                    this.mModemIfaces = includeInStringArray(this.mModemIfaces, str);
                } else {
                    this.mModemIfaces = excludeFromStringArray(this.mModemIfaces, str);
                }
            } finally {
            }
        }
        synchronized (this.mWifiNetworkLock) {
            try {
                if (displayTransport == 1) {
                    this.mWifiIfaces = includeInStringArray(this.mWifiIfaces, str);
                } else {
                    this.mWifiIfaces = excludeFromStringArray(this.mWifiIfaces, str);
                }
            } finally {
            }
        }
    }

    public void noteBinderCallStats(int i, long j, java.util.Collection<com.android.internal.os.BinderCallsStats.CallStat> collection) {
        noteBinderCallStats(i, j, collection, this.mClock.elapsedRealtime(), this.mClock.uptimeMillis());
    }

    public void noteBinderCallStats(int i, long j, java.util.Collection<com.android.internal.os.BinderCallsStats.CallStat> collection, long j2, long j3) {
        synchronized (this) {
            getUidStatsLocked(i, j2, j3).noteBinderCallStatsLocked(j, collection);
        }
    }

    public void noteBinderThreadNativeIds(int[] iArr) {
        this.mSystemServerCpuThreadReader.setBinderThreadNativeTids(iArr);
    }

    @com.android.internal.annotations.VisibleForTesting
    public void updateSystemServiceCallStats() {
        long j;
        int i = 0;
        long j2 = 0;
        for (int i2 = 0; i2 < this.mUidStats.size(); i2++) {
            android.util.ArraySet arraySet = this.mUidStats.valueAt(i2).mBinderCallStats;
            for (int size = arraySet.size() - 1; size >= 0; size--) {
                com.android.server.power.stats.BatteryStatsImpl.BinderCallStats binderCallStats = (com.android.server.power.stats.BatteryStatsImpl.BinderCallStats) arraySet.valueAt(size);
                i = (int) (i + binderCallStats.recordedCallCount);
                j2 += binderCallStats.recordedCpuTimeMicros;
            }
        }
        long j3 = 0;
        for (int i3 = 0; i3 < this.mUidStats.size(); i3++) {
            com.android.server.power.stats.BatteryStatsImpl.Uid valueAt = this.mUidStats.valueAt(i3);
            android.util.ArraySet arraySet2 = valueAt.mBinderCallStats;
            int size2 = arraySet2.size() - 1;
            int i4 = 0;
            long j4 = 0;
            while (size2 >= 0) {
                com.android.server.power.stats.BatteryStatsImpl.BinderCallStats binderCallStats2 = (com.android.server.power.stats.BatteryStatsImpl.BinderCallStats) arraySet2.valueAt(size2);
                long j5 = j3;
                i4 = (int) (i4 + binderCallStats2.callCount);
                if (binderCallStats2.recordedCallCount > 0) {
                    j4 += (binderCallStats2.callCount * binderCallStats2.recordedCpuTimeMicros) / binderCallStats2.recordedCallCount;
                } else if (i > 0) {
                    j4 += (binderCallStats2.callCount * j2) / i;
                }
                size2--;
                j3 = j5;
            }
            long j6 = j3;
            long j7 = i4;
            if (j7 < valueAt.mBinderCallCount && i > 0) {
                j4 += ((valueAt.mBinderCallCount - j7) * j2) / i;
            }
            valueAt.mSystemServiceTimeUs = j4;
            j3 = j6 + j4;
        }
        long j8 = j3;
        int i5 = 0;
        while (i5 < this.mUidStats.size()) {
            com.android.server.power.stats.BatteryStatsImpl.Uid valueAt2 = this.mUidStats.valueAt(i5);
            if (j8 > 0) {
                j = j8;
                valueAt2.mProportionalSystemServiceUsage = valueAt2.mSystemServiceTimeUs / j;
            } else {
                j = j8;
                valueAt2.mProportionalSystemServiceUsage = 0.0d;
            }
            i5++;
            j8 = j;
        }
    }

    public java.lang.String[] getWifiIfaces() {
        java.lang.String[] strArr;
        synchronized (this.mWifiNetworkLock) {
            strArr = this.mWifiIfaces;
        }
        return strArr;
    }

    public java.lang.String[] getMobileIfaces() {
        java.lang.String[] strArr;
        synchronized (this.mModemNetworkLock) {
            strArr = this.mModemIfaces;
        }
        return strArr;
    }

    public long getScreenOnTime(long j, int i) {
        return this.mScreenOnTimer.getTotalTimeLocked(j, i);
    }

    public int getScreenOnCount(int i) {
        return this.mScreenOnTimer.getCountLocked(i);
    }

    public long getScreenDozeTime(long j, int i) {
        return this.mScreenDozeTimer.getTotalTimeLocked(j, i);
    }

    public int getScreenDozeCount(int i) {
        return this.mScreenDozeTimer.getCountLocked(i);
    }

    public long getScreenBrightnessTime(int i, long j, int i2) {
        return this.mScreenBrightnessTimer[i].getTotalTimeLocked(j, i2);
    }

    public com.android.server.power.stats.BatteryStatsImpl.Timer getScreenBrightnessTimer(int i) {
        return this.mScreenBrightnessTimer[i];
    }

    public int getDisplayCount() {
        return this.mPerDisplayBatteryStats.length;
    }

    public long getDisplayScreenOnTime(int i, long j) {
        return this.mPerDisplayBatteryStats[i].screenOnTimer.getTotalTimeLocked(j, 0);
    }

    public long getDisplayScreenDozeTime(int i, long j) {
        return this.mPerDisplayBatteryStats[i].screenDozeTimer.getTotalTimeLocked(j, 0);
    }

    public long getDisplayScreenBrightnessTime(int i, int i2, long j) {
        return this.mPerDisplayBatteryStats[i].screenBrightnessTimers[i2].getTotalTimeLocked(j, 0);
    }

    public long getInteractiveTime(long j, int i) {
        return this.mInteractiveTimer.getTotalTimeLocked(j, i);
    }

    public long getPowerSaveModeEnabledTime(long j, int i) {
        return this.mPowerSaveModeEnabledTimer.getTotalTimeLocked(j, i);
    }

    public int getPowerSaveModeEnabledCount(int i) {
        return this.mPowerSaveModeEnabledTimer.getCountLocked(i);
    }

    public long getDeviceIdleModeTime(int i, long j, int i2) {
        switch (i) {
            case 1:
                return this.mDeviceIdleModeLightTimer.getTotalTimeLocked(j, i2);
            case 2:
                return this.mDeviceIdleModeFullTimer.getTotalTimeLocked(j, i2);
            default:
                return 0L;
        }
    }

    public int getDeviceIdleModeCount(int i, int i2) {
        switch (i) {
            case 1:
                return this.mDeviceIdleModeLightTimer.getCountLocked(i2);
            case 2:
                return this.mDeviceIdleModeFullTimer.getCountLocked(i2);
            default:
                return 0;
        }
    }

    public long getLongestDeviceIdleModeTime(int i) {
        switch (i) {
            case 1:
                return this.mLongestLightIdleTimeMs;
            case 2:
                return this.mLongestFullIdleTimeMs;
            default:
                return 0L;
        }
    }

    public long getDeviceIdlingTime(int i, long j, int i2) {
        switch (i) {
            case 1:
                return this.mDeviceLightIdlingTimer.getTotalTimeLocked(j, i2);
            case 2:
                return this.mDeviceIdlingTimer.getTotalTimeLocked(j, i2);
            default:
                return 0L;
        }
    }

    public int getDeviceIdlingCount(int i, int i2) {
        switch (i) {
            case 1:
                return this.mDeviceLightIdlingTimer.getCountLocked(i2);
            case 2:
                return this.mDeviceIdlingTimer.getCountLocked(i2);
            default:
                return 0;
        }
    }

    public int getNumConnectivityChange(int i) {
        return this.mNumConnectivityChange;
    }

    public long getGpsSignalQualityTime(int i, long j, int i2) {
        if (i < 0 || i >= this.mGpsSignalQualityTimer.length) {
            return 0L;
        }
        return this.mGpsSignalQualityTimer[i].getTotalTimeLocked(j, i2);
    }

    public long getGpsBatteryDrainMaMs() {
        double d = 0.0d;
        if (this.mPowerProfile.getAveragePower("gps.voltage") / 1000.0d == 0.0d) {
            return 0L;
        }
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime() * 1000;
        for (int i = 0; i < this.mGpsSignalQualityTimer.length; i++) {
            d += this.mPowerProfile.getAveragePower("gps.signalqualitybased", i) * (getGpsSignalQualityTime(i, elapsedRealtime, 0) / 1000);
        }
        return (long) d;
    }

    public long getPhoneOnTime(long j, int i) {
        return this.mPhoneOnTimer.getTotalTimeLocked(j, i);
    }

    public int getPhoneOnCount(int i) {
        return this.mPhoneOnTimer.getCountLocked(i);
    }

    public long getPhoneSignalStrengthTime(int i, long j, int i2) {
        return this.mPhoneSignalStrengthsTimer[i].getTotalTimeLocked(j, i2);
    }

    public long getPhoneSignalScanningTime(long j, int i) {
        return this.mPhoneSignalScanningTimer.getTotalTimeLocked(j, i);
    }

    public com.android.server.power.stats.BatteryStatsImpl.Timer getPhoneSignalScanningTimer() {
        return this.mPhoneSignalScanningTimer;
    }

    public int getPhoneSignalStrengthCount(int i, int i2) {
        return this.mPhoneSignalStrengthsTimer[i].getCountLocked(i2);
    }

    public com.android.server.power.stats.BatteryStatsImpl.Timer getPhoneSignalStrengthTimer(int i) {
        return this.mPhoneSignalStrengthsTimer[i];
    }

    public long getPhoneDataConnectionTime(int i, long j, int i2) {
        return this.mPhoneDataConnectionsTimer[i].getTotalTimeLocked(j, i2);
    }

    public int getPhoneDataConnectionCount(int i, int i2) {
        return this.mPhoneDataConnectionsTimer[i].getCountLocked(i2);
    }

    public com.android.server.power.stats.BatteryStatsImpl.Timer getPhoneDataConnectionTimer(int i) {
        return this.mPhoneDataConnectionsTimer[i];
    }

    public long getNrNsaTime(long j) {
        return this.mNrNsaTimer.getTotalTimeLocked(j, 0);
    }

    public long getActiveRadioDurationMs(int i, int i2, int i3, long j) {
        com.android.server.power.stats.BatteryStatsImpl.RadioAccessTechnologyBatteryStats radioAccessTechnologyBatteryStats = this.mPerRatBatteryStats[i];
        if (radioAccessTechnologyBatteryStats == null) {
            return 0L;
        }
        int length = radioAccessTechnologyBatteryStats.perStateTimers.length;
        if (i2 < 0 || i2 >= length) {
            return 0L;
        }
        int length2 = radioAccessTechnologyBatteryStats.perStateTimers[i2].length;
        if (i3 < 0 || i3 >= length2) {
            return 0L;
        }
        return radioAccessTechnologyBatteryStats.perStateTimers[i2][i3].getTotalTimeLocked(j * 1000, 0) / 1000;
    }

    public long getActiveTxRadioDurationMs(int i, int i2, int i3, long j) {
        com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter txDurationCounter;
        com.android.server.power.stats.BatteryStatsImpl.RadioAccessTechnologyBatteryStats radioAccessTechnologyBatteryStats = this.mPerRatBatteryStats[i];
        if (radioAccessTechnologyBatteryStats == null || (txDurationCounter = radioAccessTechnologyBatteryStats.getTxDurationCounter(i2, i3, false)) == null) {
            return -1L;
        }
        return txDurationCounter.getCountLocked(0);
    }

    public long getActiveRxRadioDurationMs(int i, int i2, long j) {
        com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter rxDurationCounter;
        com.android.server.power.stats.BatteryStatsImpl.RadioAccessTechnologyBatteryStats radioAccessTechnologyBatteryStats = this.mPerRatBatteryStats[i];
        if (radioAccessTechnologyBatteryStats == null || (rxDurationCounter = radioAccessTechnologyBatteryStats.getRxDurationCounter(i2, false)) == null) {
            return -1L;
        }
        return rxDurationCounter.getCountLocked(0);
    }

    public long getMobileRadioActiveTime(long j, int i) {
        return this.mMobileRadioActiveTimer.getTotalTimeLocked(j, i);
    }

    public int getMobileRadioActiveCount(int i) {
        return this.mMobileRadioActiveTimer.getCountLocked(i);
    }

    public long getMobileRadioActiveAdjustedTime(int i) {
        return this.mMobileRadioActiveAdjustedTime.getCountLocked(i);
    }

    public long getMobileRadioActiveUnknownTime(int i) {
        return this.mMobileRadioActiveUnknownTime.getCountLocked(i);
    }

    public int getMobileRadioActiveUnknownCount(int i) {
        return (int) this.mMobileRadioActiveUnknownCount.getCountLocked(i);
    }

    public long getWifiMulticastWakelockTime(long j, int i) {
        return this.mWifiMulticastWakelockTimer.getTotalTimeLocked(j, i);
    }

    public int getWifiMulticastWakelockCount(int i) {
        return this.mWifiMulticastWakelockTimer.getCountLocked(i);
    }

    public long getWifiOnTime(long j, int i) {
        return this.mWifiOnTimer.getTotalTimeLocked(j, i);
    }

    public long getWifiActiveTime(long j, int i) {
        return this.mWifiActiveTimer.getTotalTimeLocked(j, i);
    }

    public long getGlobalWifiRunningTime(long j, int i) {
        return this.mGlobalWifiRunningTimer.getTotalTimeLocked(j, i);
    }

    public long getWifiStateTime(int i, long j, int i2) {
        return this.mWifiStateTimer[i].getTotalTimeLocked(j, i2);
    }

    public int getWifiStateCount(int i, int i2) {
        return this.mWifiStateTimer[i].getCountLocked(i2);
    }

    public com.android.server.power.stats.BatteryStatsImpl.Timer getWifiStateTimer(int i) {
        return this.mWifiStateTimer[i];
    }

    public long getWifiSupplStateTime(int i, long j, int i2) {
        return this.mWifiSupplStateTimer[i].getTotalTimeLocked(j, i2);
    }

    public int getWifiSupplStateCount(int i, int i2) {
        return this.mWifiSupplStateTimer[i].getCountLocked(i2);
    }

    public com.android.server.power.stats.BatteryStatsImpl.Timer getWifiSupplStateTimer(int i) {
        return this.mWifiSupplStateTimer[i];
    }

    public long getWifiSignalStrengthTime(int i, long j, int i2) {
        return this.mWifiSignalStrengthsTimer[i].getTotalTimeLocked(j, i2);
    }

    public int getWifiSignalStrengthCount(int i, int i2) {
        return this.mWifiSignalStrengthsTimer[i].getCountLocked(i2);
    }

    public com.android.server.power.stats.BatteryStatsImpl.Timer getWifiSignalStrengthTimer(int i) {
        return this.mWifiSignalStrengthsTimer[i];
    }

    public android.os.BatteryStats.ControllerActivityCounter getBluetoothControllerActivity() {
        return this.mBluetoothActivity;
    }

    public android.os.BatteryStats.ControllerActivityCounter getWifiControllerActivity() {
        return this.mWifiActivity;
    }

    public android.os.BatteryStats.ControllerActivityCounter getModemControllerActivity() {
        return this.mModemActivity;
    }

    public boolean hasBluetoothActivityReporting() {
        return this.mHasBluetoothReporting;
    }

    public boolean hasWifiActivityReporting() {
        return this.mHasWifiReporting;
    }

    public boolean hasModemActivityReporting() {
        return this.mHasModemReporting;
    }

    public long getFlashlightOnTime(long j, int i) {
        return this.mFlashlightOnTimer.getTotalTimeLocked(j, i);
    }

    public long getFlashlightOnCount(int i) {
        return this.mFlashlightOnTimer.getCountLocked(i);
    }

    public long getCameraOnTime(long j, int i) {
        return this.mCameraOnTimer.getTotalTimeLocked(j, i);
    }

    public long getBluetoothScanTime(long j, int i) {
        return this.mBluetoothScanTimer.getTotalTimeLocked(j, i);
    }

    public long getNetworkActivityBytes(int i, int i2) {
        if (i >= 0 && i < this.mNetworkByteActivityCounters.length) {
            return this.mNetworkByteActivityCounters[i].getCountLocked(i2);
        }
        return 0L;
    }

    public long getNetworkActivityPackets(int i, int i2) {
        if (i >= 0 && i < this.mNetworkPacketActivityCounters.length) {
            return this.mNetworkPacketActivityCounters[i].getCountLocked(i2);
        }
        return 0L;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public long getBluetoothEnergyConsumptionUC() {
        return getPowerBucketConsumptionUC(5);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public long getCpuEnergyConsumptionUC() {
        return getPowerBucketConsumptionUC(3);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public long getGnssEnergyConsumptionUC() {
        return getPowerBucketConsumptionUC(6);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public long getMobileRadioEnergyConsumptionUC() {
        return getPowerBucketConsumptionUC(7);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public long getPhoneEnergyConsumptionUC() {
        return getPowerBucketConsumptionUC(9);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public long getScreenOnEnergyConsumptionUC() {
        return getPowerBucketConsumptionUC(0);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public long getScreenDozeEnergyConsumptionUC() {
        return getPowerBucketConsumptionUC(1);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public long getWifiEnergyConsumptionUC() {
        return getPowerBucketConsumptionUC(4);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public long getCameraEnergyConsumptionUC() {
        return getPowerBucketConsumptionUC(8);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private long getPowerBucketConsumptionUC(int i) {
        if (this.mGlobalEnergyConsumerStats == null) {
            return -1L;
        }
        return this.mGlobalEnergyConsumerStats.getAccumulatedStandardBucketCharge(i);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    @android.annotation.Nullable
    public long[] getCustomEnergyConsumerBatteryConsumptionUC() {
        if (this.mGlobalEnergyConsumerStats == null) {
            return null;
        }
        return this.mGlobalEnergyConsumerStats.getAccumulatedCustomBucketCharges();
    }

    @android.annotation.NonNull
    public java.lang.String[] getCustomEnergyConsumerNames() {
        synchronized (this) {
            try {
                if (this.mEnergyConsumerStatsConfig == null) {
                    return new java.lang.String[0];
                }
                java.lang.String[] customBucketNames = this.mEnergyConsumerStatsConfig.getCustomBucketNames();
                for (int i = 0; i < customBucketNames.length; i++) {
                    if (android.text.TextUtils.isEmpty(customBucketNames[i])) {
                        customBucketNames[i] = "CUSTOM_1000" + i;
                    }
                }
                return customBucketNames;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public long getStartClockTime() {
        long j;
        synchronized (this) {
            try {
                long currentTimeMillis = this.mClock.currentTimeMillis();
                if ((currentTimeMillis > 31536000000L && this.mStartClockTimeMs < currentTimeMillis - 31536000000L) || this.mStartClockTimeMs > currentTimeMillis) {
                    this.mHistory.recordCurrentTimeChange(this.mClock.elapsedRealtime(), this.mClock.uptimeMillis(), currentTimeMillis);
                    adjustStartClockTime(currentTimeMillis);
                }
                j = this.mStartClockTimeMs;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return j;
    }

    public long getMonotonicStartTime() {
        return this.mMonotonicStartTime;
    }

    public long getMonotonicEndTime() {
        return this.mMonotonicEndTime;
    }

    public java.lang.String getStartPlatformVersion() {
        return this.mStartPlatformVersion;
    }

    public java.lang.String getEndPlatformVersion() {
        return this.mEndPlatformVersion;
    }

    public int getParcelVersion() {
        return VERSION;
    }

    public boolean getIsOnBattery() {
        return this.mOnBattery;
    }

    public long getStatsStartRealtime() {
        return this.mRealtimeStartUs;
    }

    public android.util.SparseArray<? extends android.os.BatteryStats.Uid> getUidStats() {
        return this.mUidStats;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T extends com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs> boolean resetIfNotNull(T t, boolean z, long j) {
        if (t != null) {
            return t.reset(z, j);
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T extends com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs> boolean resetIfNotNull(T[] tArr, boolean z, long j) {
        boolean z2 = true;
        if (tArr == null) {
            return true;
        }
        for (T t : tArr) {
            z2 &= resetIfNotNull(t, z, j);
        }
        return z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T extends com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs> boolean resetIfNotNull(T[][] tArr, boolean z, long j) {
        boolean z2 = true;
        if (tArr == null) {
            return true;
        }
        for (T[] tArr2 : tArr) {
            z2 &= resetIfNotNull(tArr2, z, j);
        }
        return z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean resetIfNotNull(com.android.server.power.stats.BatteryStatsImpl.ControllerActivityCounterImpl controllerActivityCounterImpl, boolean z, long j) {
        if (controllerActivityCounterImpl != null) {
            controllerActivityCounterImpl.reset(z, j);
            return true;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T extends com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs> void detachIfNotNull(T t) {
        if (t != null) {
            t.detach();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T extends com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs> void detachIfNotNull(T[] tArr) {
        if (tArr != null) {
            for (T t : tArr) {
                detachIfNotNull(t);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T extends com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs> void detachIfNotNull(T[][] tArr) {
        if (tArr != null) {
            for (T[] tArr2 : tArr) {
                detachIfNotNull(tArr2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void detachIfNotNull(com.android.server.power.stats.BatteryStatsImpl.ControllerActivityCounterImpl controllerActivityCounterImpl) {
        if (controllerActivityCounterImpl != null) {
            controllerActivityCounterImpl.detach();
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected static class BinderCallStats {
        public java.lang.Class<? extends android.os.Binder> binderClass;
        public long callCount;
        public java.lang.String methodName;
        public long recordedCallCount;
        public long recordedCpuTimeMicros;
        public int transactionCode;

        protected BinderCallStats() {
        }

        public int hashCode() {
            return (this.binderClass.hashCode() * 31) + this.transactionCode;
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof com.android.server.power.stats.BatteryStatsImpl.BinderCallStats)) {
                return false;
            }
            com.android.server.power.stats.BatteryStatsImpl.BinderCallStats binderCallStats = (com.android.server.power.stats.BatteryStatsImpl.BinderCallStats) obj;
            return this.binderClass.equals(binderCallStats.binderClass) && this.transactionCode == binderCallStats.transactionCode;
        }

        public java.lang.String getClassName() {
            return this.binderClass.getName();
        }

        public java.lang.String getMethodName() {
            return this.methodName;
        }

        @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
        public void ensureMethodName(com.android.internal.os.BinderTransactionNameResolver binderTransactionNameResolver) {
            if (this.methodName == null) {
                this.methodName = binderTransactionNameResolver.getMethodName(this.binderClass, this.transactionCode);
            }
        }

        public java.lang.String toString() {
            return "BinderCallStats{" + this.binderClass + " transaction=" + this.transactionCode + " callCount=" + this.callCount + " recordedCallCount=" + this.recordedCallCount + " recorderCpuTimeMicros=" + this.recordedCpuTimeMicros + "}";
        }
    }

    public static class Uid extends android.os.BatteryStats.Uid {
        static final int NO_BATCHED_SCAN_STARTED = -1;
        private static com.android.server.power.stats.BatteryStatsImpl.BinderCallStats sTempBinderCallStats = new com.android.server.power.stats.BatteryStatsImpl.BinderCallStats();
        com.android.server.power.stats.BatteryStatsImpl.DualTimer mAggregatedPartialWakelockTimer;
        com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mAudioTurnedOnTimer;
        private long mBinderCallCount;
        private com.android.server.power.stats.BatteryStatsImpl.ControllerActivityCounterImpl mBluetoothControllerActivity;
        com.android.server.power.stats.BatteryStatsImpl.Counter mBluetoothScanResultBgCounter;
        com.android.server.power.stats.BatteryStatsImpl.Counter mBluetoothScanResultCounter;
        com.android.server.power.stats.BatteryStatsImpl.DualTimer mBluetoothScanTimer;
        com.android.server.power.stats.BatteryStatsImpl.DualTimer mBluetoothUnoptimizedScanTimer;
        protected com.android.server.power.stats.BatteryStatsImpl mBsi;
        com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mCameraTurnedOnTimer;
        android.util.SparseArray<com.android.server.power.stats.BatteryStatsImpl.Uid.ChildUid> mChildUids;
        com.android.server.power.stats.BatteryStatsImpl.TimeMultiStateCounter mCpuActiveTimeMs;
        com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter[][] mCpuClusterSpeedTimesUs;
        com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounterArray mCpuClusterTimesMs;
        com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounterArray mCpuFreqTimeMs;
        long mCurStepSystemTimeMs;
        long mCurStepUserTimeMs;
        com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mFlashlightTurnedOnTimer;
        com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mForegroundActivityTimer;
        com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mForegroundServiceTimer;
        boolean mFullWifiLockOut;
        com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mFullWifiLockTimer;
        final com.android.server.power.stats.BatteryStatsImpl.OverflowArrayMap<com.android.server.power.stats.BatteryStatsImpl.DualTimer> mJobStats;
        com.android.server.power.stats.BatteryStatsImpl.Counter mJobsDeferredCount;
        com.android.server.power.stats.BatteryStatsImpl.Counter mJobsDeferredEventCount;
        final com.android.server.power.stats.BatteryStatsImpl.Counter[] mJobsFreshnessBuckets;
        com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter mJobsFreshnessTimeMs;
        long mLastStepSystemTimeMs;
        long mLastStepUserTimeMs;
        com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter mMobileRadioActiveCount;
        com.android.server.power.stats.BatteryStatsImpl.TimeMultiStateCounter mMobileRadioActiveTime;
        private com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter mMobileRadioApWakeupCount;
        private com.android.server.power.stats.BatteryStatsImpl.ControllerActivityCounterImpl mModemControllerActivity;
        com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter[] mNetworkByteActivityCounters;
        com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter[] mNetworkPacketActivityCounters;

        @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
        public final com.android.server.power.stats.BatteryStatsImpl.TimeBase mOnBatteryScreenOffBackgroundTimeBase;
        com.android.server.power.stats.BatteryStatsImpl.TimeInFreqMultiStateCounter mProcStateScreenOffTimeMs;
        com.android.server.power.stats.BatteryStatsImpl.TimeInFreqMultiStateCounter mProcStateTimeMs;
        com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer[] mProcessStateTimer;
        private double mProportionalSystemServiceUsage;
        com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounterArray mScreenOffCpuFreqTimeMs;
        final com.android.server.power.stats.BatteryStatsImpl.OverflowArrayMap<com.android.server.power.stats.BatteryStatsImpl.DualTimer> mSyncStats;
        com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter mSystemCpuTime;
        private long mSystemServiceTimeUs;
        final int mUid;
        private com.android.internal.power.EnergyConsumerStats mUidEnergyConsumerStats;
        com.android.server.power.stats.BatteryStatsImpl.Counter[] mUserActivityCounters;
        com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter mUserCpuTime;
        com.android.server.power.stats.BatteryStatsImpl.BatchTimer mVibratorOnTimer;
        com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mVideoTurnedOnTimer;
        final com.android.server.power.stats.BatteryStatsImpl.OverflowArrayMap<com.android.server.power.stats.BatteryStatsImpl.Uid.Wakelock> mWakelockStats;
        com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer[] mWifiBatchedScanTimer;
        private com.android.server.power.stats.BatteryStatsImpl.ControllerActivityCounterImpl mWifiControllerActivity;
        com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mWifiMulticastTimer;
        int mWifiMulticastWakelockCount;
        private com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter mWifiRadioApWakeupCount;
        boolean mWifiRunning;
        com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mWifiRunningTimer;
        boolean mWifiScanStarted;
        com.android.server.power.stats.BatteryStatsImpl.DualTimer mWifiScanTimer;
        int mWifiBatchedScanBinStarted = -1;
        int mProcessState = 7;
        boolean mInForegroundService = false;
        final android.util.ArrayMap<java.lang.String, android.util.SparseIntArray> mJobCompletions = new android.util.ArrayMap<>();
        final android.util.SparseArray<com.android.server.power.stats.BatteryStatsImpl.Uid.Sensor> mSensorStats = new android.util.SparseArray<>();
        final android.util.ArrayMap<java.lang.String, com.android.server.power.stats.BatteryStatsImpl.Uid.Proc> mProcessStats = new android.util.ArrayMap<>();
        final android.util.ArrayMap<java.lang.String, com.android.server.power.stats.BatteryStatsImpl.Uid.Pkg> mPackageStats = new android.util.ArrayMap<>();
        final android.util.SparseArray<android.os.BatteryStats.Uid.Pid> mPids = new android.util.SparseArray<>();
        private final android.util.ArraySet<com.android.server.power.stats.BatteryStatsImpl.BinderCallStats> mBinderCallStats = new android.util.ArraySet<>();

        @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
        public final com.android.server.power.stats.BatteryStatsImpl.TimeBase mOnBatteryBackgroundTimeBase = new com.android.server.power.stats.BatteryStatsImpl.TimeBase(false);

        public Uid(com.android.server.power.stats.BatteryStatsImpl batteryStatsImpl, int i, long j, long j2) {
            this.mBsi = batteryStatsImpl;
            this.mUid = i;
            long j3 = j2 * 1000;
            long j4 = 1000 * j;
            this.mOnBatteryBackgroundTimeBase.init(j3, j4);
            this.mOnBatteryScreenOffBackgroundTimeBase = new com.android.server.power.stats.BatteryStatsImpl.TimeBase(false);
            this.mOnBatteryScreenOffBackgroundTimeBase.init(j3, j4);
            this.mUserCpuTime = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter(this.mBsi.mOnBatteryTimeBase);
            this.mSystemCpuTime = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter(this.mBsi.mOnBatteryTimeBase);
            this.mCpuClusterTimesMs = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounterArray(this.mBsi.mOnBatteryTimeBase);
            com.android.server.power.stats.BatteryStatsImpl batteryStatsImpl2 = this.mBsi;
            java.util.Objects.requireNonNull(batteryStatsImpl2);
            this.mWakelockStats = new com.android.server.power.stats.BatteryStatsImpl.OverflowArrayMap<com.android.server.power.stats.BatteryStatsImpl.Uid.Wakelock>(batteryStatsImpl2, i) { // from class: com.android.server.power.stats.BatteryStatsImpl.Uid.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(i);
                    java.util.Objects.requireNonNull(batteryStatsImpl2);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // com.android.server.power.stats.BatteryStatsImpl.OverflowArrayMap
                public com.android.server.power.stats.BatteryStatsImpl.Uid.Wakelock instantiateObject() {
                    return new com.android.server.power.stats.BatteryStatsImpl.Uid.Wakelock(com.android.server.power.stats.BatteryStatsImpl.Uid.this.mBsi, com.android.server.power.stats.BatteryStatsImpl.Uid.this);
                }
            };
            com.android.server.power.stats.BatteryStatsImpl batteryStatsImpl3 = this.mBsi;
            java.util.Objects.requireNonNull(batteryStatsImpl3);
            this.mSyncStats = new com.android.server.power.stats.BatteryStatsImpl.OverflowArrayMap<com.android.server.power.stats.BatteryStatsImpl.DualTimer>(batteryStatsImpl3, i) { // from class: com.android.server.power.stats.BatteryStatsImpl.Uid.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(i);
                    java.util.Objects.requireNonNull(batteryStatsImpl3);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // com.android.server.power.stats.BatteryStatsImpl.OverflowArrayMap
                public com.android.server.power.stats.BatteryStatsImpl.DualTimer instantiateObject() {
                    return new com.android.server.power.stats.BatteryStatsImpl.DualTimer(com.android.server.power.stats.BatteryStatsImpl.Uid.this.mBsi.mClock, com.android.server.power.stats.BatteryStatsImpl.Uid.this, 13, null, com.android.server.power.stats.BatteryStatsImpl.Uid.this.mBsi.mOnBatteryTimeBase, com.android.server.power.stats.BatteryStatsImpl.Uid.this.mOnBatteryBackgroundTimeBase);
                }
            };
            com.android.server.power.stats.BatteryStatsImpl batteryStatsImpl4 = this.mBsi;
            java.util.Objects.requireNonNull(batteryStatsImpl4);
            this.mJobStats = new com.android.server.power.stats.BatteryStatsImpl.OverflowArrayMap<com.android.server.power.stats.BatteryStatsImpl.DualTimer>(batteryStatsImpl4, i) { // from class: com.android.server.power.stats.BatteryStatsImpl.Uid.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(i);
                    java.util.Objects.requireNonNull(batteryStatsImpl4);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // com.android.server.power.stats.BatteryStatsImpl.OverflowArrayMap
                public com.android.server.power.stats.BatteryStatsImpl.DualTimer instantiateObject() {
                    return new com.android.server.power.stats.BatteryStatsImpl.DualTimer(com.android.server.power.stats.BatteryStatsImpl.Uid.this.mBsi.mClock, com.android.server.power.stats.BatteryStatsImpl.Uid.this, 14, null, com.android.server.power.stats.BatteryStatsImpl.Uid.this.mBsi.mOnBatteryTimeBase, com.android.server.power.stats.BatteryStatsImpl.Uid.this.mOnBatteryBackgroundTimeBase);
                }
            };
            this.mWifiRunningTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mBsi.mClock, this, 4, this.mBsi.mWifiRunningTimers, this.mBsi.mOnBatteryTimeBase);
            this.mFullWifiLockTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mBsi.mClock, this, 5, this.mBsi.mFullWifiLockTimers, this.mBsi.mOnBatteryTimeBase);
            this.mWifiScanTimer = new com.android.server.power.stats.BatteryStatsImpl.DualTimer(this.mBsi.mClock, this, 6, this.mBsi.mWifiScanTimers, this.mBsi.mOnBatteryTimeBase, this.mOnBatteryBackgroundTimeBase);
            this.mWifiBatchedScanTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer[5];
            this.mWifiMulticastTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mBsi.mClock, this, 7, this.mBsi.mWifiMulticastTimers, this.mBsi.mOnBatteryTimeBase);
            this.mProcessStateTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer[7];
            this.mJobsDeferredEventCount = new com.android.server.power.stats.BatteryStatsImpl.Counter(this.mBsi.mOnBatteryTimeBase);
            this.mJobsDeferredCount = new com.android.server.power.stats.BatteryStatsImpl.Counter(this.mBsi.mOnBatteryTimeBase);
            this.mJobsFreshnessTimeMs = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter(this.mBsi.mOnBatteryTimeBase);
            this.mJobsFreshnessBuckets = new com.android.server.power.stats.BatteryStatsImpl.Counter[android.os.BatteryStats.JOB_FRESHNESS_BUCKETS.length];
        }

        @com.android.internal.annotations.GuardedBy({"mBsi"})
        @com.android.internal.annotations.VisibleForTesting
        public void setProcessStateForTest(int i, long j) {
            this.mProcessState = i;
            getProcStateTimeCounter(j).setState(i, j);
            getProcStateScreenOffTimeCounter(j).setState(i, j);
            int mapUidProcessStateToBatteryConsumerProcessState = android.os.BatteryStats.mapUidProcessStateToBatteryConsumerProcessState(i);
            getCpuActiveTimeCounter().setState(mapUidProcessStateToBatteryConsumerProcessState, j);
            getMobileRadioActiveTimeCounter().setState(mapUidProcessStateToBatteryConsumerProcessState, j);
            com.android.server.power.stats.BatteryStatsImpl.ControllerActivityCounterImpl wifiControllerActivity = getWifiControllerActivity();
            if (wifiControllerActivity != null) {
                wifiControllerActivity.setState(mapUidProcessStateToBatteryConsumerProcessState, j);
            }
            com.android.server.power.stats.BatteryStatsImpl.ControllerActivityCounterImpl bluetoothControllerActivity = getBluetoothControllerActivity();
            if (bluetoothControllerActivity != null) {
                bluetoothControllerActivity.setState(mapUidProcessStateToBatteryConsumerProcessState, j);
            }
            com.android.internal.power.EnergyConsumerStats orCreateEnergyConsumerStatsIfSupportedLocked = getOrCreateEnergyConsumerStatsIfSupportedLocked();
            if (orCreateEnergyConsumerStatsIfSupportedLocked != null) {
                orCreateEnergyConsumerStatsIfSupportedLocked.setState(mapUidProcessStateToBatteryConsumerProcessState, j);
            }
        }

        public long[] getCpuFreqTimes(int i) {
            return nullIfAllZeros(this.mCpuFreqTimeMs, i);
        }

        public long[] getScreenOffCpuFreqTimes(int i) {
            return nullIfAllZeros(this.mScreenOffCpuFreqTimeMs, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public com.android.server.power.stats.BatteryStatsImpl.TimeMultiStateCounter getCpuActiveTimeCounter() {
            if (this.mCpuActiveTimeMs == null) {
                long elapsedRealtime = this.mBsi.mClock.elapsedRealtime();
                this.mCpuActiveTimeMs = new com.android.server.power.stats.BatteryStatsImpl.TimeMultiStateCounter(this.mBsi.mOnBatteryTimeBase, 5, elapsedRealtime);
                this.mCpuActiveTimeMs.setState(android.os.BatteryStats.mapUidProcessStateToBatteryConsumerProcessState(this.mProcessState), elapsedRealtime);
            }
            return this.mCpuActiveTimeMs;
        }

        public long getCpuActiveTime() {
            long j = 0;
            if (this.mCpuActiveTimeMs == null) {
                return 0L;
            }
            for (int i = 0; i < 5; i++) {
                j += this.mCpuActiveTimeMs.getCountForProcessState(i);
            }
            return j;
        }

        public long getCpuActiveTime(int i) {
            if (this.mCpuActiveTimeMs == null || i < 0 || i >= 5) {
                return 0L;
            }
            return this.mCpuActiveTimeMs.getCountForProcessState(i);
        }

        public long[] getCpuClusterTimes() {
            return nullIfAllZeros(this.mCpuClusterTimesMs, 0);
        }

        @com.android.internal.annotations.GuardedBy({"mBsi"})
        public boolean getCpuFreqTimes(long[] jArr, int i) {
            if (i < 0 || i >= 7 || this.mProcStateTimeMs == null) {
                return false;
            }
            if (!this.mBsi.mPerProcStateCpuTimesAvailable) {
                this.mProcStateTimeMs = null;
                return false;
            }
            return this.mProcStateTimeMs.getCountsLocked(jArr, i);
        }

        @com.android.internal.annotations.GuardedBy({"mBsi"})
        public boolean getScreenOffCpuFreqTimes(long[] jArr, int i) {
            if (i < 0 || i >= 7 || this.mProcStateScreenOffTimeMs == null) {
                return false;
            }
            if (!this.mBsi.mPerProcStateCpuTimesAvailable) {
                this.mProcStateScreenOffTimeMs = null;
                return false;
            }
            return this.mProcStateScreenOffTimeMs.getCountsLocked(jArr, i);
        }

        public long getBinderCallCount() {
            return this.mBinderCallCount;
        }

        @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PRIVATE)
        public android.util.ArraySet<com.android.server.power.stats.BatteryStatsImpl.BinderCallStats> getBinderCallStats() {
            return this.mBinderCallStats;
        }

        public double getProportionalSystemServiceUsage() {
            return this.mProportionalSystemServiceUsage;
        }

        public void addIsolatedUid(int i) {
            if (this.mChildUids == null) {
                this.mChildUids = new android.util.SparseArray<>();
            } else if (this.mChildUids.indexOfKey(i) >= 0) {
                return;
            }
            this.mChildUids.put(i, new com.android.server.power.stats.BatteryStatsImpl.Uid.ChildUid());
        }

        public void removeIsolatedUid(int i) {
            int indexOfKey = this.mChildUids == null ? -1 : this.mChildUids.indexOfKey(i);
            if (indexOfKey < 0) {
                return;
            }
            this.mChildUids.remove(indexOfKey);
        }

        @com.android.internal.annotations.GuardedBy({"mBsi"})
        com.android.server.power.stats.BatteryStatsImpl.Uid.ChildUid getChildUid(int i) {
            if (this.mChildUids == null) {
                return null;
            }
            return this.mChildUids.get(i);
        }

        private long[] nullIfAllZeros(com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounterArray longSamplingCounterArray, int i) {
            long[] countsLocked;
            if (longSamplingCounterArray == null || (countsLocked = longSamplingCounterArray.getCountsLocked(i)) == null) {
                return null;
            }
            for (int length = countsLocked.length - 1; length >= 0; length--) {
                if (countsLocked[length] != 0) {
                    return countsLocked;
                }
            }
            return null;
        }

        @com.android.internal.annotations.GuardedBy({"mBsi"})
        private void ensureMultiStateCounters(long j) {
            if (this.mBsi.mPowerStatsCollectorEnabled) {
                throw new java.lang.IllegalStateException("Multi-state counters used in streamlined mode");
            }
            if (this.mProcStateTimeMs == null) {
                this.mProcStateTimeMs = new com.android.server.power.stats.BatteryStatsImpl.TimeInFreqMultiStateCounter(this.mBsi.mOnBatteryTimeBase, 8, this.mBsi.mCpuScalingPolicies.getScalingStepCount(), j);
            }
            if (this.mProcStateScreenOffTimeMs == null) {
                this.mProcStateScreenOffTimeMs = new com.android.server.power.stats.BatteryStatsImpl.TimeInFreqMultiStateCounter(this.mBsi.mOnBatteryScreenOffTimeBase, 8, this.mBsi.mCpuScalingPolicies.getScalingStepCount(), j);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        @com.android.internal.annotations.GuardedBy({"mBsi"})
        public com.android.server.power.stats.BatteryStatsImpl.TimeInFreqMultiStateCounter getProcStateTimeCounter(long j) {
            ensureMultiStateCounters(j);
            return this.mProcStateTimeMs;
        }

        /* JADX INFO: Access modifiers changed from: private */
        @com.android.internal.annotations.GuardedBy({"mBsi"})
        public com.android.server.power.stats.BatteryStatsImpl.TimeInFreqMultiStateCounter getProcStateScreenOffTimeCounter(long j) {
            ensureMultiStateCounters(j);
            return this.mProcStateScreenOffTimeMs;
        }

        public com.android.server.power.stats.BatteryStatsImpl.Timer getAggregatedPartialWakelockTimer() {
            return this.mAggregatedPartialWakelockTimer;
        }

        public android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Wakelock> getWakelockStats() {
            return this.mWakelockStats.getMap();
        }

        public com.android.server.power.stats.BatteryStatsImpl.Timer getMulticastWakelockStats() {
            return this.mWifiMulticastTimer;
        }

        public android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Timer> getSyncStats() {
            return this.mSyncStats.getMap();
        }

        public android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Timer> getJobStats() {
            return this.mJobStats.getMap();
        }

        public android.util.ArrayMap<java.lang.String, android.util.SparseIntArray> getJobCompletionStats() {
            return this.mJobCompletions;
        }

        public android.util.SparseArray<? extends android.os.BatteryStats.Uid.Sensor> getSensorStats() {
            return this.mSensorStats;
        }

        public android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Proc> getProcessStats() {
            return this.mProcessStats;
        }

        public android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Pkg> getPackageStats() {
            return this.mPackageStats;
        }

        public int getUid() {
            return this.mUid;
        }

        public void noteWifiRunningLocked(long j) {
            if (!this.mWifiRunning) {
                this.mWifiRunning = true;
                if (this.mWifiRunningTimer == null) {
                    this.mWifiRunningTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mBsi.mClock, this, 4, this.mBsi.mWifiRunningTimers, this.mBsi.mOnBatteryTimeBase);
                }
                this.mWifiRunningTimer.startRunningLocked(j);
            }
        }

        public void noteWifiStoppedLocked(long j) {
            if (this.mWifiRunning) {
                this.mWifiRunning = false;
                this.mWifiRunningTimer.stopRunningLocked(j);
            }
        }

        public void noteFullWifiLockAcquiredLocked(long j) {
            if (!this.mFullWifiLockOut) {
                this.mFullWifiLockOut = true;
                if (this.mFullWifiLockTimer == null) {
                    this.mFullWifiLockTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mBsi.mClock, this, 5, this.mBsi.mFullWifiLockTimers, this.mBsi.mOnBatteryTimeBase);
                }
                this.mFullWifiLockTimer.startRunningLocked(j);
            }
        }

        public void noteFullWifiLockReleasedLocked(long j) {
            if (this.mFullWifiLockOut) {
                this.mFullWifiLockOut = false;
                this.mFullWifiLockTimer.stopRunningLocked(j);
            }
        }

        public void noteWifiScanStartedLocked(long j) {
            if (!this.mWifiScanStarted) {
                this.mWifiScanStarted = true;
                if (this.mWifiScanTimer == null) {
                    this.mWifiScanTimer = new com.android.server.power.stats.BatteryStatsImpl.DualTimer(this.mBsi.mClock, this, 6, this.mBsi.mWifiScanTimers, this.mBsi.mOnBatteryTimeBase, this.mOnBatteryBackgroundTimeBase);
                }
                this.mWifiScanTimer.startRunningLocked(j);
            }
        }

        public void noteWifiScanStoppedLocked(long j) {
            if (this.mWifiScanStarted) {
                this.mWifiScanStarted = false;
                this.mWifiScanTimer.stopRunningLocked(j);
            }
        }

        public void noteWifiBatchedScanStartedLocked(int i, long j) {
            int i2 = 0;
            while (i > 8 && i2 < 4) {
                i >>= 3;
                i2++;
            }
            if (this.mWifiBatchedScanBinStarted == i2) {
                return;
            }
            if (this.mWifiBatchedScanBinStarted != -1) {
                this.mWifiBatchedScanTimer[this.mWifiBatchedScanBinStarted].stopRunningLocked(j);
            }
            this.mWifiBatchedScanBinStarted = i2;
            if (this.mWifiBatchedScanTimer[i2] == null) {
                makeWifiBatchedScanBin(i2, null);
            }
            this.mWifiBatchedScanTimer[i2].startRunningLocked(j);
        }

        public void noteWifiBatchedScanStoppedLocked(long j) {
            if (this.mWifiBatchedScanBinStarted != -1) {
                this.mWifiBatchedScanTimer[this.mWifiBatchedScanBinStarted].stopRunningLocked(j);
                this.mWifiBatchedScanBinStarted = -1;
            }
        }

        public void noteWifiMulticastEnabledLocked(long j) {
            if (this.mWifiMulticastWakelockCount == 0) {
                if (this.mWifiMulticastTimer == null) {
                    this.mWifiMulticastTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mBsi.mClock, this, 7, this.mBsi.mWifiMulticastTimers, this.mBsi.mOnBatteryTimeBase);
                }
                this.mWifiMulticastTimer.startRunningLocked(j);
            }
            this.mWifiMulticastWakelockCount++;
        }

        public void noteWifiMulticastDisabledLocked(long j) {
            if (this.mWifiMulticastWakelockCount == 0) {
                return;
            }
            this.mWifiMulticastWakelockCount--;
            if (this.mWifiMulticastWakelockCount == 0) {
                this.mWifiMulticastTimer.stopRunningLocked(j);
            }
        }

        public com.android.server.power.stats.BatteryStatsImpl.ControllerActivityCounterImpl getWifiControllerActivity() {
            return this.mWifiControllerActivity;
        }

        public com.android.server.power.stats.BatteryStatsImpl.ControllerActivityCounterImpl getBluetoothControllerActivity() {
            return this.mBluetoothControllerActivity;
        }

        public android.os.BatteryStats.ControllerActivityCounter getModemControllerActivity() {
            return this.mModemControllerActivity;
        }

        public com.android.server.power.stats.BatteryStatsImpl.ControllerActivityCounterImpl getOrCreateWifiControllerActivityLocked() {
            if (this.mWifiControllerActivity == null) {
                this.mWifiControllerActivity = new com.android.server.power.stats.BatteryStatsImpl.ControllerActivityCounterImpl(this.mBsi.mClock, this.mBsi.mOnBatteryTimeBase, 1);
            }
            return this.mWifiControllerActivity;
        }

        public com.android.server.power.stats.BatteryStatsImpl.ControllerActivityCounterImpl getOrCreateBluetoothControllerActivityLocked() {
            if (this.mBluetoothControllerActivity == null) {
                this.mBluetoothControllerActivity = new com.android.server.power.stats.BatteryStatsImpl.ControllerActivityCounterImpl(this.mBsi.mClock, this.mBsi.mOnBatteryTimeBase, 1);
            }
            return this.mBluetoothControllerActivity;
        }

        public com.android.server.power.stats.BatteryStatsImpl.ControllerActivityCounterImpl getOrCreateModemControllerActivityLocked() {
            if (this.mModemControllerActivity == null) {
                this.mModemControllerActivity = new com.android.server.power.stats.BatteryStatsImpl.ControllerActivityCounterImpl(this.mBsi.mClock, this.mBsi.mOnBatteryTimeBase, com.android.server.power.stats.BatteryStatsImpl.MODEM_TX_POWER_LEVEL_COUNT);
            }
            return this.mModemControllerActivity;
        }

        @com.android.internal.annotations.GuardedBy({"mBsi"})
        private com.android.internal.power.EnergyConsumerStats getOrCreateEnergyConsumerStatsLocked() {
            if (this.mUidEnergyConsumerStats == null) {
                this.mUidEnergyConsumerStats = new com.android.internal.power.EnergyConsumerStats(this.mBsi.mEnergyConsumerStatsConfig);
            }
            return this.mUidEnergyConsumerStats;
        }

        @com.android.internal.annotations.GuardedBy({"mBsi"})
        private com.android.internal.power.EnergyConsumerStats getOrCreateEnergyConsumerStatsIfSupportedLocked() {
            if (this.mUidEnergyConsumerStats == null && this.mBsi.mEnergyConsumerStatsConfig != null) {
                this.mUidEnergyConsumerStats = new com.android.internal.power.EnergyConsumerStats(this.mBsi.mEnergyConsumerStatsConfig);
            }
            return this.mUidEnergyConsumerStats;
        }

        /* JADX INFO: Access modifiers changed from: private */
        @com.android.internal.annotations.GuardedBy({"mBsi"})
        public void addChargeToStandardBucketLocked(long j, int i, long j2) {
            getOrCreateEnergyConsumerStatsLocked().updateStandardBucket(i, j, j2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        @com.android.internal.annotations.GuardedBy({"mBsi"})
        public void addChargeToCustomBucketLocked(long j, int i) {
            getOrCreateEnergyConsumerStatsLocked().updateCustomBucket(i, j, this.mBsi.mClock.elapsedRealtime());
        }

        @com.android.internal.annotations.GuardedBy({"mBsi"})
        public long getEnergyConsumptionUC(int i) {
            if (this.mBsi.mGlobalEnergyConsumerStats == null || !this.mBsi.mGlobalEnergyConsumerStats.isStandardBucketSupported(i)) {
                return -1L;
            }
            if (this.mUidEnergyConsumerStats == null) {
                return 0L;
            }
            return this.mUidEnergyConsumerStats.getAccumulatedStandardBucketCharge(i);
        }

        @com.android.internal.annotations.GuardedBy({"mBsi"})
        public long getEnergyConsumptionUC(int i, int i2) {
            if (this.mBsi.mGlobalEnergyConsumerStats == null || !this.mBsi.mGlobalEnergyConsumerStats.isStandardBucketSupported(i)) {
                return -1L;
            }
            if (this.mUidEnergyConsumerStats == null) {
                return 0L;
            }
            return this.mUidEnergyConsumerStats.getAccumulatedStandardBucketCharge(i, i2);
        }

        @com.android.internal.annotations.GuardedBy({"mBsi"})
        public long[] getCustomEnergyConsumerBatteryConsumptionUC() {
            if (this.mBsi.mGlobalEnergyConsumerStats == null) {
                return null;
            }
            if (this.mUidEnergyConsumerStats == null) {
                return new long[this.mBsi.mGlobalEnergyConsumerStats.getNumberCustomPowerBuckets()];
            }
            return this.mUidEnergyConsumerStats.getAccumulatedCustomBucketCharges();
        }

        @com.android.internal.annotations.GuardedBy({"mBsi"})
        public long getBluetoothEnergyConsumptionUC() {
            return getEnergyConsumptionUC(5);
        }

        @com.android.internal.annotations.GuardedBy({"mBsi"})
        public long getBluetoothEnergyConsumptionUC(int i) {
            return getEnergyConsumptionUC(5, i);
        }

        @com.android.internal.annotations.GuardedBy({"mBsi"})
        public long getCpuEnergyConsumptionUC() {
            return getEnergyConsumptionUC(3);
        }

        @com.android.internal.annotations.GuardedBy({"mBsi"})
        public long getCpuEnergyConsumptionUC(int i) {
            return getEnergyConsumptionUC(3, i);
        }

        @com.android.internal.annotations.GuardedBy({"mBsi"})
        public long getGnssEnergyConsumptionUC() {
            return getEnergyConsumptionUC(6);
        }

        @com.android.internal.annotations.GuardedBy({"mBsi"})
        public long getMobileRadioEnergyConsumptionUC() {
            return getEnergyConsumptionUC(7);
        }

        @com.android.internal.annotations.GuardedBy({"mBsi"})
        public long getMobileRadioEnergyConsumptionUC(int i) {
            return getEnergyConsumptionUC(7, i);
        }

        @com.android.internal.annotations.GuardedBy({"mBsi"})
        public long getScreenOnEnergyConsumptionUC() {
            return getEnergyConsumptionUC(0);
        }

        @com.android.internal.annotations.GuardedBy({"mBsi"})
        public long getWifiEnergyConsumptionUC() {
            return getEnergyConsumptionUC(4);
        }

        @com.android.internal.annotations.GuardedBy({"mBsi"})
        public long getWifiEnergyConsumptionUC(int i) {
            return getEnergyConsumptionUC(4, i);
        }

        @com.android.internal.annotations.GuardedBy({"mBsi"})
        public long getCameraEnergyConsumptionUC() {
            return getEnergyConsumptionUC(8);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long markProcessForegroundTimeUs(long j, boolean z) {
            long j2;
            com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer stopwatchTimer = this.mForegroundActivityTimer;
            if (stopwatchTimer == null) {
                j2 = 0;
            } else {
                j2 = z ? stopwatchTimer.getTimeSinceMarkLocked(j * 1000) : 0L;
                stopwatchTimer.setMark(j);
            }
            com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer stopwatchTimer2 = this.mProcessStateTimer[0];
            if (stopwatchTimer2 != null) {
                r3 = z ? stopwatchTimer2.getTimeSinceMarkLocked(1000 * j) : 0L;
                stopwatchTimer2.setMark(j);
            }
            return r3 < j2 ? r3 : j2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long markGnssTimeUs(long j) {
            com.android.server.power.stats.BatteryStatsImpl.DualTimer dualTimer;
            com.android.server.power.stats.BatteryStatsImpl.Uid.Sensor sensor = this.mSensorStats.get(com.android.server.am.ProcessList.INVALID_ADJ);
            if (sensor == null || (dualTimer = sensor.mTimer) == null) {
                return 0L;
            }
            long timeSinceMarkLocked = dualTimer.getTimeSinceMarkLocked(1000 * j);
            dualTimer.setMark(j);
            return timeSinceMarkLocked;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long markCameraTimeUs(long j) {
            com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer stopwatchTimer = this.mCameraTurnedOnTimer;
            if (stopwatchTimer == null) {
                return 0L;
            }
            long timeSinceMarkLocked = stopwatchTimer.getTimeSinceMarkLocked(1000 * j);
            stopwatchTimer.setMark(j);
            return timeSinceMarkLocked;
        }

        public com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer createAudioTurnedOnTimerLocked() {
            if (this.mAudioTurnedOnTimer == null) {
                this.mAudioTurnedOnTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mBsi.mClock, this, 15, this.mBsi.mAudioTurnedOnTimers, this.mBsi.mOnBatteryTimeBase);
            }
            return this.mAudioTurnedOnTimer;
        }

        public void noteAudioTurnedOnLocked(long j) {
            createAudioTurnedOnTimerLocked().startRunningLocked(j);
        }

        public void noteAudioTurnedOffLocked(long j) {
            if (this.mAudioTurnedOnTimer != null) {
                this.mAudioTurnedOnTimer.stopRunningLocked(j);
            }
        }

        public void noteResetAudioLocked(long j) {
            if (this.mAudioTurnedOnTimer != null) {
                this.mAudioTurnedOnTimer.stopAllRunningLocked(j);
            }
        }

        public com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer createVideoTurnedOnTimerLocked() {
            if (this.mVideoTurnedOnTimer == null) {
                this.mVideoTurnedOnTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mBsi.mClock, this, 8, this.mBsi.mVideoTurnedOnTimers, this.mBsi.mOnBatteryTimeBase);
            }
            return this.mVideoTurnedOnTimer;
        }

        public void noteVideoTurnedOnLocked(long j) {
            createVideoTurnedOnTimerLocked().startRunningLocked(j);
        }

        public void noteVideoTurnedOffLocked(long j) {
            if (this.mVideoTurnedOnTimer != null) {
                this.mVideoTurnedOnTimer.stopRunningLocked(j);
            }
        }

        public void noteResetVideoLocked(long j) {
            if (this.mVideoTurnedOnTimer != null) {
                this.mVideoTurnedOnTimer.stopAllRunningLocked(j);
            }
        }

        public com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer createFlashlightTurnedOnTimerLocked() {
            if (this.mFlashlightTurnedOnTimer == null) {
                this.mFlashlightTurnedOnTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mBsi.mClock, this, 16, this.mBsi.mFlashlightTurnedOnTimers, this.mBsi.mOnBatteryTimeBase);
            }
            return this.mFlashlightTurnedOnTimer;
        }

        public void noteFlashlightTurnedOnLocked(long j) {
            createFlashlightTurnedOnTimerLocked().startRunningLocked(j);
        }

        public void noteFlashlightTurnedOffLocked(long j) {
            if (this.mFlashlightTurnedOnTimer != null) {
                this.mFlashlightTurnedOnTimer.stopRunningLocked(j);
            }
        }

        public void noteResetFlashlightLocked(long j) {
            if (this.mFlashlightTurnedOnTimer != null) {
                this.mFlashlightTurnedOnTimer.stopAllRunningLocked(j);
            }
        }

        public com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer createCameraTurnedOnTimerLocked() {
            if (this.mCameraTurnedOnTimer == null) {
                this.mCameraTurnedOnTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mBsi.mClock, this, 17, this.mBsi.mCameraTurnedOnTimers, this.mBsi.mOnBatteryTimeBase);
            }
            return this.mCameraTurnedOnTimer;
        }

        public void noteCameraTurnedOnLocked(long j) {
            createCameraTurnedOnTimerLocked().startRunningLocked(j);
        }

        public void noteCameraTurnedOffLocked(long j) {
            if (this.mCameraTurnedOnTimer != null) {
                this.mCameraTurnedOnTimer.stopRunningLocked(j);
            }
        }

        public void noteResetCameraLocked(long j) {
            if (this.mCameraTurnedOnTimer != null) {
                this.mCameraTurnedOnTimer.stopAllRunningLocked(j);
            }
        }

        public com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer createForegroundActivityTimerLocked() {
            if (this.mForegroundActivityTimer == null) {
                this.mForegroundActivityTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mBsi.mClock, this, 10, null, this.mBsi.mOnBatteryTimeBase);
            }
            return this.mForegroundActivityTimer;
        }

        public com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer createForegroundServiceTimerLocked() {
            if (this.mForegroundServiceTimer == null) {
                this.mForegroundServiceTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mBsi.mClock, this, 22, null, this.mBsi.mOnBatteryTimeBase);
            }
            return this.mForegroundServiceTimer;
        }

        public com.android.server.power.stats.BatteryStatsImpl.DualTimer createAggregatedPartialWakelockTimerLocked() {
            if (this.mAggregatedPartialWakelockTimer == null) {
                this.mAggregatedPartialWakelockTimer = new com.android.server.power.stats.BatteryStatsImpl.DualTimer(this.mBsi.mClock, this, 20, null, this.mBsi.mOnBatteryScreenOffTimeBase, this.mOnBatteryScreenOffBackgroundTimeBase);
            }
            return this.mAggregatedPartialWakelockTimer;
        }

        public com.android.server.power.stats.BatteryStatsImpl.DualTimer createBluetoothScanTimerLocked() {
            if (this.mBluetoothScanTimer == null) {
                this.mBluetoothScanTimer = new com.android.server.power.stats.BatteryStatsImpl.DualTimer(this.mBsi.mClock, this, 19, this.mBsi.mBluetoothScanOnTimers, this.mBsi.mOnBatteryTimeBase, this.mOnBatteryBackgroundTimeBase);
            }
            return this.mBluetoothScanTimer;
        }

        public com.android.server.power.stats.BatteryStatsImpl.DualTimer createBluetoothUnoptimizedScanTimerLocked() {
            if (this.mBluetoothUnoptimizedScanTimer == null) {
                this.mBluetoothUnoptimizedScanTimer = new com.android.server.power.stats.BatteryStatsImpl.DualTimer(this.mBsi.mClock, this, 21, null, this.mBsi.mOnBatteryTimeBase, this.mOnBatteryBackgroundTimeBase);
            }
            return this.mBluetoothUnoptimizedScanTimer;
        }

        public void noteBluetoothScanStartedLocked(long j, boolean z) {
            createBluetoothScanTimerLocked().startRunningLocked(j);
            if (z) {
                createBluetoothUnoptimizedScanTimerLocked().startRunningLocked(j);
            }
        }

        public void noteBluetoothScanStoppedLocked(long j, boolean z) {
            if (this.mBluetoothScanTimer != null) {
                this.mBluetoothScanTimer.stopRunningLocked(j);
            }
            if (z && this.mBluetoothUnoptimizedScanTimer != null) {
                this.mBluetoothUnoptimizedScanTimer.stopRunningLocked(j);
            }
        }

        public void noteResetBluetoothScanLocked(long j) {
            if (this.mBluetoothScanTimer != null) {
                this.mBluetoothScanTimer.stopAllRunningLocked(j);
            }
            if (this.mBluetoothUnoptimizedScanTimer != null) {
                this.mBluetoothUnoptimizedScanTimer.stopAllRunningLocked(j);
            }
        }

        public com.android.server.power.stats.BatteryStatsImpl.Counter createBluetoothScanResultCounterLocked() {
            if (this.mBluetoothScanResultCounter == null) {
                this.mBluetoothScanResultCounter = new com.android.server.power.stats.BatteryStatsImpl.Counter(this.mBsi.mOnBatteryTimeBase);
            }
            return this.mBluetoothScanResultCounter;
        }

        public com.android.server.power.stats.BatteryStatsImpl.Counter createBluetoothScanResultBgCounterLocked() {
            if (this.mBluetoothScanResultBgCounter == null) {
                this.mBluetoothScanResultBgCounter = new com.android.server.power.stats.BatteryStatsImpl.Counter(this.mOnBatteryBackgroundTimeBase);
            }
            return this.mBluetoothScanResultBgCounter;
        }

        public void noteBluetoothScanResultsLocked(int i) {
            createBluetoothScanResultCounterLocked().addAtomic(i);
            createBluetoothScanResultBgCounterLocked().addAtomic(i);
        }

        public void noteActivityResumedLocked(long j) {
            createForegroundActivityTimerLocked().startRunningLocked(j);
        }

        public void noteActivityPausedLocked(long j) {
            if (this.mForegroundActivityTimer != null) {
                this.mForegroundActivityTimer.stopRunningLocked(j);
            }
        }

        public void noteForegroundServiceResumedLocked(long j) {
            createForegroundServiceTimerLocked().startRunningLocked(j);
        }

        public void noteForegroundServicePausedLocked(long j) {
            if (this.mForegroundServiceTimer != null) {
                this.mForegroundServiceTimer.stopRunningLocked(j);
            }
        }

        public com.android.server.power.stats.BatteryStatsImpl.BatchTimer createVibratorOnTimerLocked() {
            if (this.mVibratorOnTimer == null) {
                this.mVibratorOnTimer = new com.android.server.power.stats.BatteryStatsImpl.BatchTimer(this.mBsi.mClock, this, 9, this.mBsi.mOnBatteryTimeBase);
            }
            return this.mVibratorOnTimer;
        }

        public void noteVibratorOnLocked(long j, long j2) {
            createVibratorOnTimerLocked().addDuration(j, j2);
        }

        public void noteVibratorOffLocked(long j) {
            if (this.mVibratorOnTimer != null) {
                this.mVibratorOnTimer.abortLastDuration(j);
            }
        }

        public long getWifiRunningTime(long j, int i) {
            if (this.mWifiRunningTimer == null) {
                return 0L;
            }
            return this.mWifiRunningTimer.getTotalTimeLocked(j, i);
        }

        public long getFullWifiLockTime(long j, int i) {
            if (this.mFullWifiLockTimer == null) {
                return 0L;
            }
            return this.mFullWifiLockTimer.getTotalTimeLocked(j, i);
        }

        public long getWifiScanTime(long j, int i) {
            if (this.mWifiScanTimer == null) {
                return 0L;
            }
            return this.mWifiScanTimer.getTotalTimeLocked(j, i);
        }

        public int getWifiScanCount(int i) {
            if (this.mWifiScanTimer == null) {
                return 0;
            }
            return this.mWifiScanTimer.getCountLocked(i);
        }

        public com.android.server.power.stats.BatteryStatsImpl.Timer getWifiScanTimer() {
            return this.mWifiScanTimer;
        }

        public int getWifiScanBackgroundCount(int i) {
            if (this.mWifiScanTimer == null || this.mWifiScanTimer.getSubTimer() == null) {
                return 0;
            }
            return this.mWifiScanTimer.getSubTimer().getCountLocked(i);
        }

        public long getWifiScanActualTime(long j) {
            if (this.mWifiScanTimer == null) {
                return 0L;
            }
            return this.mWifiScanTimer.getTotalDurationMsLocked((j + 500) / 1000) * 1000;
        }

        public long getWifiScanBackgroundTime(long j) {
            if (this.mWifiScanTimer == null || this.mWifiScanTimer.getSubTimer() == null) {
                return 0L;
            }
            return this.mWifiScanTimer.getSubTimer().getTotalDurationMsLocked((j + 500) / 1000) * 1000;
        }

        public com.android.server.power.stats.BatteryStatsImpl.Timer getWifiScanBackgroundTimer() {
            if (this.mWifiScanTimer == null) {
                return null;
            }
            return this.mWifiScanTimer.getSubTimer();
        }

        public long getWifiBatchedScanTime(int i, long j, int i2) {
            if (i < 0 || i >= 5 || this.mWifiBatchedScanTimer[i] == null) {
                return 0L;
            }
            return this.mWifiBatchedScanTimer[i].getTotalTimeLocked(j, i2);
        }

        public int getWifiBatchedScanCount(int i, int i2) {
            if (i < 0 || i >= 5 || this.mWifiBatchedScanTimer[i] == null) {
                return 0;
            }
            return this.mWifiBatchedScanTimer[i].getCountLocked(i2);
        }

        public long getWifiMulticastTime(long j, int i) {
            if (this.mWifiMulticastTimer == null) {
                return 0L;
            }
            return this.mWifiMulticastTimer.getTotalTimeLocked(j, i);
        }

        public com.android.server.power.stats.BatteryStatsImpl.Timer getAudioTurnedOnTimer() {
            return this.mAudioTurnedOnTimer;
        }

        public com.android.server.power.stats.BatteryStatsImpl.Timer getVideoTurnedOnTimer() {
            return this.mVideoTurnedOnTimer;
        }

        public com.android.server.power.stats.BatteryStatsImpl.Timer getFlashlightTurnedOnTimer() {
            return this.mFlashlightTurnedOnTimer;
        }

        public com.android.server.power.stats.BatteryStatsImpl.Timer getCameraTurnedOnTimer() {
            return this.mCameraTurnedOnTimer;
        }

        public com.android.server.power.stats.BatteryStatsImpl.Timer getForegroundActivityTimer() {
            return this.mForegroundActivityTimer;
        }

        public com.android.server.power.stats.BatteryStatsImpl.Timer getForegroundServiceTimer() {
            return this.mForegroundServiceTimer;
        }

        public com.android.server.power.stats.BatteryStatsImpl.Timer getBluetoothScanTimer() {
            return this.mBluetoothScanTimer;
        }

        public com.android.server.power.stats.BatteryStatsImpl.Timer getBluetoothScanBackgroundTimer() {
            if (this.mBluetoothScanTimer == null) {
                return null;
            }
            return this.mBluetoothScanTimer.getSubTimer();
        }

        public com.android.server.power.stats.BatteryStatsImpl.Timer getBluetoothUnoptimizedScanTimer() {
            return this.mBluetoothUnoptimizedScanTimer;
        }

        public com.android.server.power.stats.BatteryStatsImpl.Timer getBluetoothUnoptimizedScanBackgroundTimer() {
            if (this.mBluetoothUnoptimizedScanTimer == null) {
                return null;
            }
            return this.mBluetoothUnoptimizedScanTimer.getSubTimer();
        }

        public com.android.server.power.stats.BatteryStatsImpl.Counter getBluetoothScanResultCounter() {
            return this.mBluetoothScanResultCounter;
        }

        public com.android.server.power.stats.BatteryStatsImpl.Counter getBluetoothScanResultBgCounter() {
            return this.mBluetoothScanResultBgCounter;
        }

        void makeProcessState(int i, android.os.Parcel parcel) {
            if (i < 0 || i >= 7) {
                return;
            }
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mProcessStateTimer[i]);
            if (parcel == null) {
                this.mProcessStateTimer[i] = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mBsi.mClock, this, 12, null, this.mBsi.mOnBatteryTimeBase);
            } else {
                this.mProcessStateTimer[i] = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mBsi.mClock, this, 12, null, this.mBsi.mOnBatteryTimeBase, parcel);
            }
        }

        public long getProcessStateTime(int i, long j, int i2) {
            if (i < 0 || i >= 7 || this.mProcessStateTimer[i] == null) {
                return 0L;
            }
            return this.mProcessStateTimer[i].getTotalTimeLocked(j, i2);
        }

        public com.android.server.power.stats.BatteryStatsImpl.Timer getProcessStateTimer(int i) {
            if (i < 0 || i >= 7) {
                return null;
            }
            return this.mProcessStateTimer[i];
        }

        public com.android.server.power.stats.BatteryStatsImpl.Timer getVibratorOnTimer() {
            return this.mVibratorOnTimer;
        }

        public void noteUserActivityLocked(int i) {
            if (this.mUserActivityCounters == null) {
                initUserActivityLocked();
            }
            if (i >= 0 && i < android.os.BatteryStats.Uid.NUM_USER_ACTIVITY_TYPES) {
                this.mUserActivityCounters[i].stepAtomic();
                return;
            }
            android.util.Slog.w(com.android.server.power.stats.BatteryStatsImpl.TAG, "Unknown user activity type " + i + " was specified.", new java.lang.Throwable());
        }

        public boolean hasUserActivity() {
            return this.mUserActivityCounters != null;
        }

        public int getUserActivityCount(int i, int i2) {
            if (this.mUserActivityCounters == null) {
                return 0;
            }
            return this.mUserActivityCounters[i].getCountLocked(i2);
        }

        void makeWifiBatchedScanBin(int i, android.os.Parcel parcel) {
            java.util.ArrayList arrayList;
            if (i < 0 || i >= 5) {
                return;
            }
            java.util.ArrayList arrayList2 = (java.util.ArrayList) this.mBsi.mWifiBatchedScanTimers.get(i);
            if (arrayList2 != null) {
                arrayList = arrayList2;
            } else {
                java.util.ArrayList arrayList3 = new java.util.ArrayList();
                this.mBsi.mWifiBatchedScanTimers.put(i, arrayList3);
                arrayList = arrayList3;
            }
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mWifiBatchedScanTimer[i]);
            if (parcel == null) {
                this.mWifiBatchedScanTimer[i] = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mBsi.mClock, this, 11, arrayList, this.mBsi.mOnBatteryTimeBase);
            } else {
                this.mWifiBatchedScanTimer[i] = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mBsi.mClock, this, 11, arrayList, this.mBsi.mOnBatteryTimeBase, parcel);
            }
        }

        void initUserActivityLocked() {
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mUserActivityCounters);
            this.mUserActivityCounters = new com.android.server.power.stats.BatteryStatsImpl.Counter[android.os.BatteryStats.Uid.NUM_USER_ACTIVITY_TYPES];
            for (int i = 0; i < android.os.BatteryStats.Uid.NUM_USER_ACTIVITY_TYPES; i++) {
                this.mUserActivityCounters[i] = new com.android.server.power.stats.BatteryStatsImpl.Counter(this.mBsi.mOnBatteryTimeBase);
            }
        }

        void noteNetworkActivityLocked(int i, long j, long j2) {
            ensureNetworkActivityLocked();
            if (i >= 0 && i < 10) {
                this.mNetworkByteActivityCounters[i].addCountLocked(j);
                this.mNetworkPacketActivityCounters[i].addCountLocked(j2);
                return;
            }
            android.util.Slog.w(com.android.server.power.stats.BatteryStatsImpl.TAG, "Unknown network activity type " + i + " was specified.", new java.lang.Throwable());
        }

        void noteMobileRadioActiveTimeLocked(long j, long j2) {
            ensureNetworkActivityLocked();
            getMobileRadioActiveTimeCounter().increment(j, j2);
            this.mMobileRadioActiveCount.addCountLocked(1L);
        }

        private com.android.server.power.stats.BatteryStatsImpl.TimeMultiStateCounter getMobileRadioActiveTimeCounter() {
            if (this.mMobileRadioActiveTime == null) {
                long elapsedRealtime = this.mBsi.mClock.elapsedRealtime();
                this.mMobileRadioActiveTime = new com.android.server.power.stats.BatteryStatsImpl.TimeMultiStateCounter(this.mBsi.mOnBatteryTimeBase, 5, elapsedRealtime);
                this.mMobileRadioActiveTime.setState(android.os.BatteryStats.mapUidProcessStateToBatteryConsumerProcessState(this.mProcessState), elapsedRealtime);
                this.mMobileRadioActiveTime.update(0L, elapsedRealtime);
            }
            return this.mMobileRadioActiveTime;
        }

        public boolean hasNetworkActivity() {
            return this.mNetworkByteActivityCounters != null;
        }

        public long getNetworkActivityBytes(int i, int i2) {
            if (this.mNetworkByteActivityCounters != null && i >= 0 && i < this.mNetworkByteActivityCounters.length) {
                return this.mNetworkByteActivityCounters[i].getCountLocked(i2);
            }
            return 0L;
        }

        public long getNetworkActivityPackets(int i, int i2) {
            if (this.mNetworkPacketActivityCounters != null && i >= 0 && i < this.mNetworkPacketActivityCounters.length) {
                return this.mNetworkPacketActivityCounters[i].getCountLocked(i2);
            }
            return 0L;
        }

        public long getMobileRadioActiveTime(int i) {
            return getMobileRadioActiveTimeInProcessState(0);
        }

        public long getMobileRadioActiveTimeInProcessState(int i) {
            if (this.mMobileRadioActiveTime == null) {
                return 0L;
            }
            if (i == 0) {
                return this.mMobileRadioActiveTime.getTotalCountLocked();
            }
            return this.mMobileRadioActiveTime.getCountForProcessState(i);
        }

        public int getMobileRadioActiveCount(int i) {
            if (this.mMobileRadioActiveCount != null) {
                return (int) this.mMobileRadioActiveCount.getCountLocked(i);
            }
            return 0;
        }

        public long getUserCpuTimeUs(int i) {
            return this.mUserCpuTime.getCountLocked(i);
        }

        public long getSystemCpuTimeUs(int i) {
            return this.mSystemCpuTime.getCountLocked(i);
        }

        @java.lang.Deprecated
        public long getTimeAtCpuSpeed(int i, int i2, int i3) {
            com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter[] longSamplingCounterArr;
            com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter longSamplingCounter;
            if (this.mCpuClusterSpeedTimesUs != null && i >= 0 && i < this.mCpuClusterSpeedTimesUs.length && (longSamplingCounterArr = this.mCpuClusterSpeedTimesUs[i]) != null && i2 >= 0 && i2 < longSamplingCounterArr.length && (longSamplingCounter = longSamplingCounterArr[i2]) != null) {
                return longSamplingCounter.getCountLocked(i3);
            }
            return 0L;
        }

        public void noteMobileRadioApWakeupLocked() {
            if (this.mMobileRadioApWakeupCount == null) {
                this.mMobileRadioApWakeupCount = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter(this.mBsi.mOnBatteryTimeBase);
            }
            this.mMobileRadioApWakeupCount.addCountLocked(1L);
        }

        public long getMobileRadioApWakeupCount(int i) {
            if (this.mMobileRadioApWakeupCount != null) {
                return this.mMobileRadioApWakeupCount.getCountLocked(i);
            }
            return 0L;
        }

        public void noteWifiRadioApWakeupLocked() {
            if (this.mWifiRadioApWakeupCount == null) {
                this.mWifiRadioApWakeupCount = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter(this.mBsi.mOnBatteryTimeBase);
            }
            this.mWifiRadioApWakeupCount.addCountLocked(1L);
        }

        public long getWifiRadioApWakeupCount(int i) {
            if (this.mWifiRadioApWakeupCount != null) {
                return this.mWifiRadioApWakeupCount.getCountLocked(i);
            }
            return 0L;
        }

        public void getDeferredJobsCheckinLineLocked(java.lang.StringBuilder sb, int i) {
            sb.setLength(0);
            int countLocked = this.mJobsDeferredEventCount.getCountLocked(i);
            if (countLocked == 0) {
                return;
            }
            int countLocked2 = this.mJobsDeferredCount.getCountLocked(i);
            long countLocked3 = this.mJobsFreshnessTimeMs.getCountLocked(i);
            sb.append(countLocked);
            sb.append(',');
            sb.append(countLocked2);
            sb.append(',');
            sb.append(countLocked3);
            for (int i2 = 0; i2 < android.os.BatteryStats.JOB_FRESHNESS_BUCKETS.length; i2++) {
                if (this.mJobsFreshnessBuckets[i2] == null) {
                    sb.append(",0");
                } else {
                    sb.append(",");
                    sb.append(this.mJobsFreshnessBuckets[i2].getCountLocked(i));
                }
            }
        }

        public void getDeferredJobsLineLocked(java.lang.StringBuilder sb, int i) {
            sb.setLength(0);
            int countLocked = this.mJobsDeferredEventCount.getCountLocked(i);
            if (countLocked == 0) {
                return;
            }
            int countLocked2 = this.mJobsDeferredCount.getCountLocked(i);
            long countLocked3 = this.mJobsFreshnessTimeMs.getCountLocked(i);
            sb.append("times=");
            sb.append(countLocked);
            sb.append(", ");
            sb.append("count=");
            sb.append(countLocked2);
            sb.append(", ");
            sb.append("totalLatencyMs=");
            sb.append(countLocked3);
            sb.append(", ");
            for (int i2 = 0; i2 < android.os.BatteryStats.JOB_FRESHNESS_BUCKETS.length; i2++) {
                sb.append("<");
                sb.append(android.os.BatteryStats.JOB_FRESHNESS_BUCKETS[i2]);
                sb.append("ms=");
                if (this.mJobsFreshnessBuckets[i2] == null) {
                    sb.append("0");
                } else {
                    sb.append(this.mJobsFreshnessBuckets[i2].getCountLocked(i));
                }
                sb.append(" ");
            }
        }

        void ensureNetworkActivityLocked() {
            if (this.mNetworkByteActivityCounters != null) {
                return;
            }
            this.mNetworkByteActivityCounters = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter[10];
            this.mNetworkPacketActivityCounters = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter[10];
            for (int i = 0; i < 10; i++) {
                this.mNetworkByteActivityCounters[i] = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter(this.mBsi.mOnBatteryTimeBase);
                this.mNetworkPacketActivityCounters[i] = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter(this.mBsi.mOnBatteryTimeBase);
            }
            this.mMobileRadioActiveCount = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter(this.mBsi.mOnBatteryTimeBase);
        }

        @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
        public boolean reset(long j, long j2, int i) {
            boolean z;
            this.mOnBatteryBackgroundTimeBase.init(j, j2);
            this.mOnBatteryScreenOffBackgroundTimeBase.init(j, j2);
            if (this.mWifiRunningTimer == null) {
                z = false;
            } else {
                z = (!this.mWifiRunningTimer.reset(false, j2)) | false | this.mWifiRunning;
            }
            if (this.mFullWifiLockTimer != null) {
                z = z | (!this.mFullWifiLockTimer.reset(false, j2)) | this.mFullWifiLockOut;
            }
            if (this.mWifiScanTimer != null) {
                z = z | (!this.mWifiScanTimer.reset(false, j2)) | this.mWifiScanStarted;
            }
            if (this.mWifiBatchedScanTimer != null) {
                for (int i2 = 0; i2 < 5; i2++) {
                    if (this.mWifiBatchedScanTimer[i2] != null) {
                        z |= !this.mWifiBatchedScanTimer[i2].reset(false, j2);
                    }
                }
                z |= this.mWifiBatchedScanBinStarted != -1;
            }
            if (this.mWifiMulticastTimer != null) {
                z = z | (!this.mWifiMulticastTimer.reset(false, j2)) | (this.mWifiMulticastWakelockCount > 0);
            }
            boolean z2 = z | (!com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mAudioTurnedOnTimer, false, j2)) | (!com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mVideoTurnedOnTimer, false, j2)) | (!com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mFlashlightTurnedOnTimer, false, j2)) | (!com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mCameraTurnedOnTimer, false, j2)) | (!com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mForegroundActivityTimer, false, j2)) | (!com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mForegroundServiceTimer, false, j2)) | (!com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mAggregatedPartialWakelockTimer, false, j2)) | (!com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mBluetoothScanTimer, false, j2)) | (!com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mBluetoothUnoptimizedScanTimer, false, j2));
            com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mBluetoothScanResultCounter, false, j2);
            com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mBluetoothScanResultBgCounter, false, j2);
            if (this.mProcessStateTimer != null) {
                for (int i3 = 0; i3 < 7; i3++) {
                    z2 |= !com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mProcessStateTimer[i3], false, j2);
                }
                z2 |= this.mProcessState != 7;
            }
            if (this.mVibratorOnTimer != null) {
                if (this.mVibratorOnTimer.reset(false, j2)) {
                    this.mVibratorOnTimer.detach();
                    this.mVibratorOnTimer = null;
                } else {
                    z2 = true;
                }
            }
            com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull((com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs[]) this.mUserActivityCounters, false, j2);
            com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull((com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs[]) this.mNetworkByteActivityCounters, false, j2);
            com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull((com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs[]) this.mNetworkPacketActivityCounters, false, j2);
            com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mMobileRadioActiveTime, false, j2);
            com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mMobileRadioActiveCount, false, j2);
            com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mWifiControllerActivity, false, j2);
            com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mBluetoothControllerActivity, false, j2);
            com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mModemControllerActivity, false, j2);
            if (i == 4) {
                this.mUidEnergyConsumerStats = null;
            } else {
                com.android.internal.power.EnergyConsumerStats.resetIfNotNull(this.mUidEnergyConsumerStats);
            }
            com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mUserCpuTime, false, j2);
            com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mSystemCpuTime, false, j2);
            com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull((com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs[][]) this.mCpuClusterSpeedTimesUs, false, j2);
            com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mCpuFreqTimeMs, false, j2);
            com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mScreenOffCpuFreqTimeMs, false, j2);
            com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mCpuActiveTimeMs, false, j2);
            com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mCpuClusterTimesMs, false, j2);
            com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mProcStateTimeMs, false, j2);
            com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mProcStateScreenOffTimeMs, false, j2);
            com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mMobileRadioApWakeupCount, false, j2);
            com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mWifiRadioApWakeupCount, false, j2);
            android.util.ArrayMap<java.lang.String, com.android.server.power.stats.BatteryStatsImpl.Uid.Wakelock> map = this.mWakelockStats.getMap();
            for (int size = map.size() - 1; size >= 0; size--) {
                if (map.valueAt(size).reset(j2)) {
                    map.removeAt(size);
                } else {
                    z2 = true;
                }
            }
            long j3 = j2 / 1000;
            this.mWakelockStats.cleanup(j3);
            android.util.ArrayMap<java.lang.String, com.android.server.power.stats.BatteryStatsImpl.DualTimer> map2 = this.mSyncStats.getMap();
            for (int size2 = map2.size() - 1; size2 >= 0; size2--) {
                com.android.server.power.stats.BatteryStatsImpl.DualTimer valueAt = map2.valueAt(size2);
                if (valueAt.reset(false, j2)) {
                    map2.removeAt(size2);
                    valueAt.detach();
                } else {
                    z2 = true;
                }
            }
            this.mSyncStats.cleanup(j3);
            android.util.ArrayMap<java.lang.String, com.android.server.power.stats.BatteryStatsImpl.DualTimer> map3 = this.mJobStats.getMap();
            for (int size3 = map3.size() - 1; size3 >= 0; size3--) {
                com.android.server.power.stats.BatteryStatsImpl.DualTimer valueAt2 = map3.valueAt(size3);
                if (valueAt2.reset(false, j2)) {
                    map3.removeAt(size3);
                    valueAt2.detach();
                } else {
                    z2 = true;
                }
            }
            this.mJobStats.cleanup(j3);
            this.mJobCompletions.clear();
            com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mJobsDeferredEventCount, false, j2);
            com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mJobsDeferredCount, false, j2);
            com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mJobsFreshnessTimeMs, false, j2);
            com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull((com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs[]) this.mJobsFreshnessBuckets, false, j2);
            for (int size4 = this.mSensorStats.size() - 1; size4 >= 0; size4--) {
                if (this.mSensorStats.valueAt(size4).reset(j2)) {
                    this.mSensorStats.removeAt(size4);
                } else {
                    z2 = true;
                }
            }
            for (int size5 = this.mProcessStats.size() - 1; size5 >= 0; size5--) {
                this.mProcessStats.valueAt(size5).detach();
            }
            this.mProcessStats.clear();
            for (int size6 = this.mPids.size() - 1; size6 >= 0; size6--) {
                if (this.mPids.valueAt(size6).mWakeNesting > 0) {
                    z2 = true;
                } else {
                    this.mPids.removeAt(size6);
                }
            }
            for (int size7 = this.mPackageStats.size() - 1; size7 >= 0; size7--) {
                this.mPackageStats.valueAt(size7).detach();
            }
            this.mPackageStats.clear();
            this.mBinderCallCount = 0L;
            this.mBinderCallStats.clear();
            this.mProportionalSystemServiceUsage = 0.0d;
            this.mLastStepSystemTimeMs = 0L;
            this.mLastStepUserTimeMs = 0L;
            this.mCurStepSystemTimeMs = 0L;
            this.mCurStepUserTimeMs = 0L;
            return !z2;
        }

        void detachFromTimeBase() {
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mWifiRunningTimer);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mFullWifiLockTimer);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mWifiScanTimer);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mWifiBatchedScanTimer);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mWifiMulticastTimer);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mAudioTurnedOnTimer);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mVideoTurnedOnTimer);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mFlashlightTurnedOnTimer);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mCameraTurnedOnTimer);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mForegroundActivityTimer);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mForegroundServiceTimer);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mAggregatedPartialWakelockTimer);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mBluetoothScanTimer);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mBluetoothUnoptimizedScanTimer);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mBluetoothScanResultCounter);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mBluetoothScanResultBgCounter);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mProcessStateTimer);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mVibratorOnTimer);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mUserActivityCounters);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mNetworkByteActivityCounters);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mNetworkPacketActivityCounters);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mMobileRadioActiveTime);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mMobileRadioActiveCount);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mMobileRadioApWakeupCount);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mWifiRadioApWakeupCount);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mWifiControllerActivity);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mBluetoothControllerActivity);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mModemControllerActivity);
            this.mPids.clear();
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mUserCpuTime);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mSystemCpuTime);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mCpuClusterSpeedTimesUs);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mCpuActiveTimeMs);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mCpuFreqTimeMs);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mScreenOffCpuFreqTimeMs);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mCpuClusterTimesMs);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mProcStateTimeMs);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mProcStateScreenOffTimeMs);
            android.util.ArrayMap<java.lang.String, com.android.server.power.stats.BatteryStatsImpl.Uid.Wakelock> map = this.mWakelockStats.getMap();
            for (int size = map.size() - 1; size >= 0; size--) {
                map.valueAt(size).detachFromTimeBase();
            }
            android.util.ArrayMap<java.lang.String, com.android.server.power.stats.BatteryStatsImpl.DualTimer> map2 = this.mSyncStats.getMap();
            for (int size2 = map2.size() - 1; size2 >= 0; size2--) {
                com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(map2.valueAt(size2));
            }
            android.util.ArrayMap<java.lang.String, com.android.server.power.stats.BatteryStatsImpl.DualTimer> map3 = this.mJobStats.getMap();
            for (int size3 = map3.size() - 1; size3 >= 0; size3--) {
                com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(map3.valueAt(size3));
            }
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mJobsDeferredEventCount);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mJobsDeferredCount);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mJobsFreshnessTimeMs);
            com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mJobsFreshnessBuckets);
            for (int size4 = this.mSensorStats.size() - 1; size4 >= 0; size4--) {
                this.mSensorStats.valueAt(size4).detachFromTimeBase();
            }
            for (int size5 = this.mProcessStats.size() - 1; size5 >= 0; size5--) {
                this.mProcessStats.valueAt(size5).detach();
            }
            this.mProcessStats.clear();
            for (int size6 = this.mPackageStats.size() - 1; size6 >= 0; size6--) {
                this.mPackageStats.valueAt(size6).detach();
            }
            this.mPackageStats.clear();
        }

        void writeJobCompletionsToParcelLocked(android.os.Parcel parcel) {
            int size = this.mJobCompletions.size();
            parcel.writeInt(size);
            for (int i = 0; i < size; i++) {
                parcel.writeString(this.mJobCompletions.keyAt(i));
                android.util.SparseIntArray valueAt = this.mJobCompletions.valueAt(i);
                int size2 = valueAt.size();
                parcel.writeInt(size2);
                for (int i2 = 0; i2 < size2; i2++) {
                    parcel.writeInt(valueAt.keyAt(i2));
                    parcel.writeInt(valueAt.valueAt(i2));
                }
            }
        }

        void readJobCompletionsFromParcelLocked(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            this.mJobCompletions.clear();
            for (int i = 0; i < readInt; i++) {
                java.lang.String readString = parcel.readString();
                int readInt2 = parcel.readInt();
                if (readInt2 > 0) {
                    android.util.SparseIntArray sparseIntArray = new android.util.SparseIntArray();
                    for (int i2 = 0; i2 < readInt2; i2++) {
                        sparseIntArray.put(parcel.readInt(), parcel.readInt());
                    }
                    this.mJobCompletions.put(readString, sparseIntArray);
                }
            }
        }

        public void noteJobsDeferredLocked(int i, long j) {
            this.mJobsDeferredEventCount.addAtomic(1);
            this.mJobsDeferredCount.addAtomic(i);
            if (j != 0) {
                this.mJobsFreshnessTimeMs.addCountLocked(j);
                for (int i2 = 0; i2 < android.os.BatteryStats.JOB_FRESHNESS_BUCKETS.length; i2++) {
                    if (j < android.os.BatteryStats.JOB_FRESHNESS_BUCKETS[i2]) {
                        if (this.mJobsFreshnessBuckets[i2] == null) {
                            this.mJobsFreshnessBuckets[i2] = new com.android.server.power.stats.BatteryStatsImpl.Counter(this.mBsi.mOnBatteryTimeBase);
                        }
                        this.mJobsFreshnessBuckets[i2].addAtomic(1);
                        return;
                    }
                }
            }
        }

        public void noteBinderCallStatsLocked(long j, java.util.Collection<com.android.internal.os.BinderCallsStats.CallStat> collection) {
            com.android.server.power.stats.BatteryStatsImpl.BinderCallStats binderCallStats;
            this.mBinderCallCount += j;
            for (com.android.internal.os.BinderCallsStats.CallStat callStat : collection) {
                sTempBinderCallStats.binderClass = callStat.binderClass;
                sTempBinderCallStats.transactionCode = callStat.transactionCode;
                int indexOf = this.mBinderCallStats.indexOf(sTempBinderCallStats);
                if (indexOf >= 0) {
                    binderCallStats = this.mBinderCallStats.valueAt(indexOf);
                } else {
                    binderCallStats = new com.android.server.power.stats.BatteryStatsImpl.BinderCallStats();
                    binderCallStats.binderClass = callStat.binderClass;
                    binderCallStats.transactionCode = callStat.transactionCode;
                    this.mBinderCallStats.add(binderCallStats);
                }
                binderCallStats.callCount += callStat.incrementalCallCount;
                binderCallStats.recordedCallCount = callStat.recordedCallCount;
                binderCallStats.recordedCpuTimeMicros = callStat.cpuTimeMicros;
            }
        }

        public static class Wakelock extends android.os.BatteryStats.Uid.Wakelock {
            protected com.android.server.power.stats.BatteryStatsImpl mBsi;
            com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mTimerDraw;
            com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mTimerFull;
            com.android.server.power.stats.BatteryStatsImpl.DualTimer mTimerPartial;
            com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer mTimerWindow;
            protected com.android.server.power.stats.BatteryStatsImpl.Uid mUid;

            public Wakelock(com.android.server.power.stats.BatteryStatsImpl batteryStatsImpl, com.android.server.power.stats.BatteryStatsImpl.Uid uid) {
                this.mBsi = batteryStatsImpl;
                this.mUid = uid;
            }

            private com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer readStopwatchTimerFromParcel(int i, java.util.ArrayList<com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer> arrayList, com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase, android.os.Parcel parcel) {
                if (parcel.readInt() == 0) {
                    return null;
                }
                return new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mBsi.mClock, this.mUid, i, arrayList, timeBase, parcel);
            }

            private com.android.server.power.stats.BatteryStatsImpl.DualTimer readDualTimerFromParcel(int i, java.util.ArrayList<com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer> arrayList, com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase, com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase2, android.os.Parcel parcel) {
                if (parcel.readInt() == 0) {
                    return null;
                }
                return new com.android.server.power.stats.BatteryStatsImpl.DualTimer(this.mBsi.mClock, this.mUid, i, arrayList, timeBase, timeBase2, parcel);
            }

            boolean reset(long j) {
                boolean z = (!com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mTimerDraw, false, j)) | (!com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mTimerFull, false, j)) | false | (!com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mTimerPartial, false, j)) | (!com.android.server.power.stats.BatteryStatsImpl.resetIfNotNull(this.mTimerWindow, false, j));
                if (!z) {
                    com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mTimerFull);
                    this.mTimerFull = null;
                    com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mTimerPartial);
                    this.mTimerPartial = null;
                    com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mTimerWindow);
                    this.mTimerWindow = null;
                    com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mTimerDraw);
                    this.mTimerDraw = null;
                }
                return !z;
            }

            void readFromParcelLocked(com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase, com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase2, com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase3, android.os.Parcel parcel) {
                this.mTimerPartial = readDualTimerFromParcel(0, this.mBsi.mPartialTimers, timeBase2, timeBase3, parcel);
                this.mTimerFull = readStopwatchTimerFromParcel(1, this.mBsi.mFullTimers, timeBase, parcel);
                this.mTimerWindow = readStopwatchTimerFromParcel(2, this.mBsi.mWindowTimers, timeBase, parcel);
                this.mTimerDraw = readStopwatchTimerFromParcel(18, this.mBsi.mDrawTimers, timeBase, parcel);
            }

            void writeToParcelLocked(android.os.Parcel parcel, long j) {
                com.android.server.power.stats.BatteryStatsImpl.Timer.writeTimerToParcel(parcel, this.mTimerPartial, j);
                com.android.server.power.stats.BatteryStatsImpl.Timer.writeTimerToParcel(parcel, this.mTimerFull, j);
                com.android.server.power.stats.BatteryStatsImpl.Timer.writeTimerToParcel(parcel, this.mTimerWindow, j);
                com.android.server.power.stats.BatteryStatsImpl.Timer.writeTimerToParcel(parcel, this.mTimerDraw, j);
            }

            public com.android.server.power.stats.BatteryStatsImpl.Timer getWakeTime(int i) {
                switch (i) {
                    case 0:
                        return this.mTimerPartial;
                    case 1:
                        return this.mTimerFull;
                    case 2:
                        return this.mTimerWindow;
                    case 18:
                        return this.mTimerDraw;
                    default:
                        throw new java.lang.IllegalArgumentException("type = " + i);
                }
            }

            public void detachFromTimeBase() {
                com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mTimerPartial);
                com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mTimerFull);
                com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mTimerWindow);
                com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mTimerDraw);
            }
        }

        public static class Sensor extends android.os.BatteryStats.Uid.Sensor {
            protected com.android.server.power.stats.BatteryStatsImpl mBsi;
            final int mHandle;
            com.android.server.power.stats.BatteryStatsImpl.DualTimer mTimer;
            protected com.android.server.power.stats.BatteryStatsImpl.Uid mUid;

            public Sensor(com.android.server.power.stats.BatteryStatsImpl batteryStatsImpl, com.android.server.power.stats.BatteryStatsImpl.Uid uid, int i) {
                this.mBsi = batteryStatsImpl;
                this.mUid = uid;
                this.mHandle = i;
            }

            private com.android.server.power.stats.BatteryStatsImpl.DualTimer readTimersFromParcel(com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase, com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase2, android.os.Parcel parcel) {
                java.util.ArrayList arrayList;
                if (parcel.readInt() == 0) {
                    return null;
                }
                java.util.ArrayList arrayList2 = (java.util.ArrayList) this.mBsi.mSensorTimers.get(this.mHandle);
                if (arrayList2 != null) {
                    arrayList = arrayList2;
                } else {
                    java.util.ArrayList arrayList3 = new java.util.ArrayList();
                    this.mBsi.mSensorTimers.put(this.mHandle, arrayList3);
                    arrayList = arrayList3;
                }
                return new com.android.server.power.stats.BatteryStatsImpl.DualTimer(this.mBsi.mClock, this.mUid, 0, arrayList, timeBase, timeBase2, parcel);
            }

            boolean reset(long j) {
                if (this.mTimer.reset(true, j)) {
                    this.mTimer = null;
                    return true;
                }
                return false;
            }

            void readFromParcelLocked(com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase, com.android.server.power.stats.BatteryStatsImpl.TimeBase timeBase2, android.os.Parcel parcel) {
                this.mTimer = readTimersFromParcel(timeBase, timeBase2, parcel);
            }

            void writeToParcelLocked(android.os.Parcel parcel, long j) {
                com.android.server.power.stats.BatteryStatsImpl.Timer.writeTimerToParcel(parcel, this.mTimer, j);
            }

            public com.android.server.power.stats.BatteryStatsImpl.Timer getSensorTime() {
                return this.mTimer;
            }

            public com.android.server.power.stats.BatteryStatsImpl.Timer getSensorBackgroundTime() {
                if (this.mTimer == null) {
                    return null;
                }
                return this.mTimer.getSubTimer();
            }

            public int getHandle() {
                return this.mHandle;
            }

            public void detachFromTimeBase() {
                com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mTimer);
            }
        }

        public static class Proc extends android.os.BatteryStats.Uid.Proc implements com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs {
            boolean mActive = true;
            protected com.android.server.power.stats.BatteryStatsImpl mBsi;
            java.util.ArrayList<android.os.BatteryStats.Uid.Proc.ExcessivePower> mExcessivePower;
            long mForegroundTimeMs;
            final java.lang.String mName;
            int mNumAnrs;
            int mNumCrashes;
            int mStarts;
            long mSystemTimeMs;
            long mUserTimeMs;

            public Proc(com.android.server.power.stats.BatteryStatsImpl batteryStatsImpl, java.lang.String str) {
                this.mBsi = batteryStatsImpl;
                this.mName = str;
                this.mBsi.mOnBatteryTimeBase.add(this);
            }

            @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
            public void onTimeStarted(long j, long j2, long j3) {
            }

            @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
            public void onTimeStopped(long j, long j2, long j3) {
            }

            @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
            public boolean reset(boolean z, long j) {
                if (z) {
                    detach();
                    return true;
                }
                return true;
            }

            @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
            public void detach() {
                this.mActive = false;
                this.mBsi.mOnBatteryTimeBase.remove(this);
            }

            public int countExcessivePowers() {
                if (this.mExcessivePower != null) {
                    return this.mExcessivePower.size();
                }
                return 0;
            }

            public android.os.BatteryStats.Uid.Proc.ExcessivePower getExcessivePower(int i) {
                if (this.mExcessivePower != null) {
                    return this.mExcessivePower.get(i);
                }
                return null;
            }

            public void addExcessiveCpu(long j, long j2) {
                if (this.mExcessivePower == null) {
                    this.mExcessivePower = new java.util.ArrayList<>();
                }
                android.os.BatteryStats.Uid.Proc.ExcessivePower excessivePower = new android.os.BatteryStats.Uid.Proc.ExcessivePower();
                excessivePower.type = 2;
                excessivePower.overTime = j;
                excessivePower.usedTime = j2;
                this.mExcessivePower.add(excessivePower);
            }

            void writeExcessivePowerToParcelLocked(android.os.Parcel parcel) {
                if (this.mExcessivePower == null) {
                    parcel.writeInt(0);
                    return;
                }
                int size = this.mExcessivePower.size();
                parcel.writeInt(size);
                for (int i = 0; i < size; i++) {
                    android.os.BatteryStats.Uid.Proc.ExcessivePower excessivePower = this.mExcessivePower.get(i);
                    parcel.writeInt(excessivePower.type);
                    parcel.writeLong(excessivePower.overTime);
                    parcel.writeLong(excessivePower.usedTime);
                }
            }

            void readExcessivePowerFromParcelLocked(android.os.Parcel parcel) {
                int readInt = parcel.readInt();
                if (readInt == 0) {
                    this.mExcessivePower = null;
                    return;
                }
                if (readInt > 10000) {
                    throw new android.os.ParcelFormatException("File corrupt: too many excessive power entries " + readInt);
                }
                this.mExcessivePower = new java.util.ArrayList<>();
                for (int i = 0; i < readInt; i++) {
                    android.os.BatteryStats.Uid.Proc.ExcessivePower excessivePower = new android.os.BatteryStats.Uid.Proc.ExcessivePower();
                    excessivePower.type = parcel.readInt();
                    excessivePower.overTime = parcel.readLong();
                    excessivePower.usedTime = parcel.readLong();
                    this.mExcessivePower.add(excessivePower);
                }
            }

            void writeToParcelLocked(android.os.Parcel parcel) {
                parcel.writeLong(this.mUserTimeMs);
                parcel.writeLong(this.mSystemTimeMs);
                parcel.writeLong(this.mForegroundTimeMs);
                parcel.writeInt(this.mStarts);
                parcel.writeInt(this.mNumCrashes);
                parcel.writeInt(this.mNumAnrs);
                writeExcessivePowerToParcelLocked(parcel);
            }

            void readFromParcelLocked(android.os.Parcel parcel) {
                this.mUserTimeMs = parcel.readLong();
                this.mSystemTimeMs = parcel.readLong();
                this.mForegroundTimeMs = parcel.readLong();
                this.mStarts = parcel.readInt();
                this.mNumCrashes = parcel.readInt();
                this.mNumAnrs = parcel.readInt();
                readExcessivePowerFromParcelLocked(parcel);
            }

            public void addCpuTimeLocked(int i, int i2) {
                addCpuTimeLocked(i, i2, this.mBsi.mOnBatteryTimeBase.isRunning());
            }

            public void addCpuTimeLocked(int i, int i2, boolean z) {
                if (z) {
                    this.mUserTimeMs += i;
                    this.mSystemTimeMs += i2;
                }
            }

            public void addForegroundTimeLocked(long j) {
                this.mForegroundTimeMs += j;
            }

            public void incStartsLocked() {
                this.mStarts++;
            }

            public void incNumCrashesLocked() {
                this.mNumCrashes++;
            }

            public void incNumAnrsLocked() {
                this.mNumAnrs++;
            }

            public boolean isActive() {
                return this.mActive;
            }

            public long getUserTime(int i) {
                return this.mUserTimeMs;
            }

            public long getSystemTime(int i) {
                return this.mSystemTimeMs;
            }

            public long getForegroundTime(int i) {
                return this.mForegroundTimeMs;
            }

            public int getStarts(int i) {
                return this.mStarts;
            }

            public int getNumCrashes(int i) {
                return this.mNumCrashes;
            }

            public int getNumAnrs(int i) {
                return this.mNumAnrs;
            }
        }

        public static class Pkg extends android.os.BatteryStats.Uid.Pkg implements com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs {
            protected com.android.server.power.stats.BatteryStatsImpl mBsi;
            android.util.ArrayMap<java.lang.String, com.android.server.power.stats.BatteryStatsImpl.Counter> mWakeupAlarms = new android.util.ArrayMap<>();
            final android.util.ArrayMap<java.lang.String, com.android.server.power.stats.BatteryStatsImpl.Uid.Pkg.Serv> mServiceStats = new android.util.ArrayMap<>();

            public Pkg(com.android.server.power.stats.BatteryStatsImpl batteryStatsImpl) {
                this.mBsi = batteryStatsImpl;
                this.mBsi.mOnBatteryScreenOffTimeBase.add(this);
            }

            @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
            public void onTimeStarted(long j, long j2, long j3) {
            }

            @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
            public void onTimeStopped(long j, long j2, long j3) {
            }

            @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
            public boolean reset(boolean z, long j) {
                if (z) {
                    detach();
                    return true;
                }
                return true;
            }

            @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
            public void detach() {
                this.mBsi.mOnBatteryScreenOffTimeBase.remove(this);
                for (int size = this.mWakeupAlarms.size() - 1; size >= 0; size--) {
                    com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mWakeupAlarms.valueAt(size));
                }
                for (int size2 = this.mServiceStats.size() - 1; size2 >= 0; size2--) {
                    com.android.server.power.stats.BatteryStatsImpl.detachIfNotNull(this.mServiceStats.valueAt(size2));
                }
            }

            void readFromParcelLocked(android.os.Parcel parcel) {
                int readInt = parcel.readInt();
                this.mWakeupAlarms.clear();
                for (int i = 0; i < readInt; i++) {
                    this.mWakeupAlarms.put(parcel.readString(), new com.android.server.power.stats.BatteryStatsImpl.Counter(this.mBsi.mOnBatteryScreenOffTimeBase, parcel));
                }
                int readInt2 = parcel.readInt();
                this.mServiceStats.clear();
                for (int i2 = 0; i2 < readInt2; i2++) {
                    java.lang.String readString = parcel.readString();
                    com.android.server.power.stats.BatteryStatsImpl.Uid.Pkg.Serv serv = new com.android.server.power.stats.BatteryStatsImpl.Uid.Pkg.Serv(this.mBsi);
                    this.mServiceStats.put(readString, serv);
                    serv.readFromParcelLocked(parcel);
                }
            }

            void writeToParcelLocked(android.os.Parcel parcel) {
                int size = this.mWakeupAlarms.size();
                parcel.writeInt(size);
                for (int i = 0; i < size; i++) {
                    parcel.writeString(this.mWakeupAlarms.keyAt(i));
                    this.mWakeupAlarms.valueAt(i).writeToParcel(parcel);
                }
                int size2 = this.mServiceStats.size();
                parcel.writeInt(size2);
                for (int i2 = 0; i2 < size2; i2++) {
                    parcel.writeString(this.mServiceStats.keyAt(i2));
                    this.mServiceStats.valueAt(i2).writeToParcelLocked(parcel);
                }
            }

            public android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Counter> getWakeupAlarmStats() {
                return this.mWakeupAlarms;
            }

            public void noteWakeupAlarmLocked(java.lang.String str) {
                com.android.server.power.stats.BatteryStatsImpl.Counter counter = this.mWakeupAlarms.get(str);
                if (counter == null) {
                    counter = new com.android.server.power.stats.BatteryStatsImpl.Counter(this.mBsi.mOnBatteryScreenOffTimeBase);
                    this.mWakeupAlarms.put(str, counter);
                }
                counter.stepAtomic();
            }

            public android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Pkg.Serv> getServiceStats() {
                return this.mServiceStats;
            }

            public static class Serv extends android.os.BatteryStats.Uid.Pkg.Serv implements com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs {
                protected com.android.server.power.stats.BatteryStatsImpl mBsi;
                protected boolean mLaunched;
                protected long mLaunchedSinceMs;
                protected long mLaunchedTimeMs;
                protected int mLaunches;
                protected com.android.server.power.stats.BatteryStatsImpl.Uid.Pkg mPkg;
                protected boolean mRunning;
                protected long mRunningSinceMs;
                protected long mStartTimeMs;
                protected int mStarts;

                public Serv(com.android.server.power.stats.BatteryStatsImpl batteryStatsImpl) {
                    this.mBsi = batteryStatsImpl;
                    this.mBsi.mOnBatteryTimeBase.add(this);
                }

                @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
                public void onTimeStarted(long j, long j2, long j3) {
                }

                @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
                public void onTimeStopped(long j, long j2, long j3) {
                }

                @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
                public boolean reset(boolean z, long j) {
                    if (z) {
                        detach();
                        return true;
                    }
                    return true;
                }

                @Override // com.android.server.power.stats.BatteryStatsImpl.TimeBaseObs
                public void detach() {
                    this.mBsi.mOnBatteryTimeBase.remove(this);
                }

                public void readFromParcelLocked(android.os.Parcel parcel) {
                    this.mStartTimeMs = parcel.readLong();
                    this.mRunningSinceMs = parcel.readLong();
                    this.mRunning = parcel.readInt() != 0;
                    this.mStarts = parcel.readInt();
                    this.mLaunchedTimeMs = parcel.readLong();
                    this.mLaunchedSinceMs = parcel.readLong();
                    this.mLaunched = parcel.readInt() != 0;
                    this.mLaunches = parcel.readInt();
                }

                public void writeToParcelLocked(android.os.Parcel parcel) {
                    parcel.writeLong(this.mStartTimeMs);
                    parcel.writeLong(this.mRunningSinceMs);
                    parcel.writeInt(this.mRunning ? 1 : 0);
                    parcel.writeInt(this.mStarts);
                    parcel.writeLong(this.mLaunchedTimeMs);
                    parcel.writeLong(this.mLaunchedSinceMs);
                    parcel.writeInt(this.mLaunched ? 1 : 0);
                    parcel.writeInt(this.mLaunches);
                }

                public long getLaunchTimeToNowLocked(long j) {
                    return !this.mLaunched ? this.mLaunchedTimeMs : (this.mLaunchedTimeMs + j) - this.mLaunchedSinceMs;
                }

                public long getStartTimeToNowLocked(long j) {
                    return !this.mRunning ? this.mStartTimeMs : (this.mStartTimeMs + j) - this.mRunningSinceMs;
                }

                public void startLaunchedLocked() {
                    startLaunchedLocked(this.mBsi.mClock.uptimeMillis());
                }

                public void startLaunchedLocked(long j) {
                    if (!this.mLaunched) {
                        this.mLaunches++;
                        this.mLaunchedSinceMs = this.mBsi.getBatteryUptimeLocked(j) / 1000;
                        this.mLaunched = true;
                    }
                }

                public void stopLaunchedLocked() {
                    stopLaunchedLocked(this.mBsi.mClock.uptimeMillis());
                }

                public void stopLaunchedLocked(long j) {
                    if (this.mLaunched) {
                        long batteryUptimeLocked = (this.mBsi.getBatteryUptimeLocked(j) / 1000) - this.mLaunchedSinceMs;
                        if (batteryUptimeLocked > 0) {
                            this.mLaunchedTimeMs += batteryUptimeLocked;
                        } else {
                            this.mLaunches--;
                        }
                        this.mLaunched = false;
                    }
                }

                public void startRunningLocked() {
                    startRunningLocked(this.mBsi.mClock.uptimeMillis());
                }

                public void startRunningLocked(long j) {
                    if (!this.mRunning) {
                        this.mStarts++;
                        this.mRunningSinceMs = this.mBsi.getBatteryUptimeLocked(j) / 1000;
                        this.mRunning = true;
                    }
                }

                public void stopRunningLocked() {
                    stopRunningLocked(this.mBsi.mClock.uptimeMillis());
                }

                public void stopRunningLocked(long j) {
                    if (this.mRunning) {
                        long batteryUptimeLocked = (this.mBsi.getBatteryUptimeLocked(j) / 1000) - this.mRunningSinceMs;
                        if (batteryUptimeLocked > 0) {
                            this.mStartTimeMs += batteryUptimeLocked;
                        } else {
                            this.mStarts--;
                        }
                        this.mRunning = false;
                    }
                }

                public com.android.server.power.stats.BatteryStatsImpl getBatteryStats() {
                    return this.mBsi;
                }

                public int getLaunches(int i) {
                    return this.mLaunches;
                }

                public long getStartTime(long j, int i) {
                    return getStartTimeToNowLocked(j);
                }

                public int getStarts(int i) {
                    return this.mStarts;
                }
            }

            final com.android.server.power.stats.BatteryStatsImpl.Uid.Pkg.Serv newServiceStatsLocked() {
                return new com.android.server.power.stats.BatteryStatsImpl.Uid.Pkg.Serv(this.mBsi);
            }
        }

        private class ChildUid {
            public final com.android.server.power.stats.BatteryStatsImpl.TimeMultiStateCounter cpuActiveCounter;
            public final com.android.internal.os.LongArrayMultiStateCounter cpuTimeInFreqCounter;

            ChildUid() {
                long elapsedRealtime = com.android.server.power.stats.BatteryStatsImpl.Uid.this.mBsi.mClock.elapsedRealtime();
                this.cpuActiveCounter = new com.android.server.power.stats.BatteryStatsImpl.TimeMultiStateCounter(com.android.server.power.stats.BatteryStatsImpl.Uid.this.mBsi.mOnBatteryTimeBase, 1, elapsedRealtime);
                this.cpuActiveCounter.setState(0, elapsedRealtime);
                if (com.android.server.power.stats.BatteryStatsImpl.Uid.this.mBsi.trackPerProcStateCpuTimes()) {
                    int scalingStepCount = com.android.server.power.stats.BatteryStatsImpl.Uid.this.mBsi.mCpuScalingPolicies.getScalingStepCount();
                    this.cpuTimeInFreqCounter = new com.android.internal.os.LongArrayMultiStateCounter(1, scalingStepCount);
                    this.cpuTimeInFreqCounter.updateValues(new com.android.internal.os.LongArrayMultiStateCounter.LongArrayContainer(scalingStepCount), elapsedRealtime);
                    return;
                }
                this.cpuTimeInFreqCounter = null;
            }
        }

        public com.android.server.power.stats.BatteryStatsImpl.Uid.Proc getProcessStatsLocked(java.lang.String str) {
            com.android.server.power.stats.BatteryStatsImpl.Uid.Proc proc = this.mProcessStats.get(str);
            if (proc == null) {
                com.android.server.power.stats.BatteryStatsImpl.Uid.Proc proc2 = new com.android.server.power.stats.BatteryStatsImpl.Uid.Proc(this.mBsi, str);
                this.mProcessStats.put(str, proc2);
                return proc2;
            }
            return proc;
        }

        @com.android.internal.annotations.GuardedBy({"mBsi"})
        public void updateUidProcessStateLocked(int i, long j, long j2) {
            boolean isForegroundService = android.app.ActivityManager.isForegroundService(i);
            int mapToInternalProcessState = android.os.BatteryStats.mapToInternalProcessState(i);
            if (this.mProcessState == mapToInternalProcessState && isForegroundService == this.mInForegroundService) {
                return;
            }
            if (this.mProcessState != mapToInternalProcessState) {
                if (this.mProcessState != 7) {
                    this.mProcessStateTimer[this.mProcessState].stopRunningLocked(j);
                }
                if (mapToInternalProcessState != 7) {
                    if (this.mProcessStateTimer[mapToInternalProcessState] == null) {
                        makeProcessState(mapToInternalProcessState, null);
                    }
                    this.mProcessStateTimer[mapToInternalProcessState].startRunningLocked(j);
                }
                if (!this.mBsi.mPowerStatsCollectorEnabled && this.mBsi.trackPerProcStateCpuTimes()) {
                    this.mBsi.updateProcStateCpuTimesLocked(this.mUid, j, j2);
                    com.android.internal.os.LongArrayMultiStateCounter counter = getProcStateTimeCounter(j).getCounter();
                    com.android.internal.os.LongArrayMultiStateCounter counter2 = getProcStateScreenOffTimeCounter(j).getCounter();
                    counter.setState(mapToInternalProcessState, j);
                    counter2.setState(mapToInternalProcessState, j);
                }
                int mapUidProcessStateToBatteryConsumerProcessState = android.os.BatteryStats.mapUidProcessStateToBatteryConsumerProcessState(this.mProcessState);
                this.mProcessState = mapToInternalProcessState;
                long j3 = j2 * 1000;
                long j4 = 1000 * j;
                updateOnBatteryBgTimeBase(j3, j4);
                updateOnBatteryScreenOffBgTimeBase(j3, j4);
                int mapUidProcessStateToBatteryConsumerProcessState2 = android.os.BatteryStats.mapUidProcessStateToBatteryConsumerProcessState(mapToInternalProcessState);
                if (this.mBsi.mSystemReady && this.mBsi.mPowerStatsCollectorEnabled) {
                    this.mBsi.mHistory.recordProcessStateChange(j, j2, this.mUid, mapUidProcessStateToBatteryConsumerProcessState2);
                }
                getCpuActiveTimeCounter().setState(mapUidProcessStateToBatteryConsumerProcessState2, j);
                getMobileRadioActiveTimeCounter().setState(mapUidProcessStateToBatteryConsumerProcessState2, j);
                com.android.server.power.stats.BatteryStatsImpl.ControllerActivityCounterImpl wifiControllerActivity = getWifiControllerActivity();
                if (wifiControllerActivity != null) {
                    wifiControllerActivity.setState(mapUidProcessStateToBatteryConsumerProcessState2, j);
                }
                com.android.server.power.stats.BatteryStatsImpl.ControllerActivityCounterImpl bluetoothControllerActivity = getBluetoothControllerActivity();
                if (bluetoothControllerActivity != null) {
                    bluetoothControllerActivity.setState(mapUidProcessStateToBatteryConsumerProcessState2, j);
                }
                com.android.internal.power.EnergyConsumerStats orCreateEnergyConsumerStatsIfSupportedLocked = getOrCreateEnergyConsumerStatsIfSupportedLocked();
                if (orCreateEnergyConsumerStatsIfSupportedLocked != null) {
                    orCreateEnergyConsumerStatsIfSupportedLocked.setState(mapUidProcessStateToBatteryConsumerProcessState2, j);
                }
                maybeScheduleExternalStatsSync(mapUidProcessStateToBatteryConsumerProcessState, mapUidProcessStateToBatteryConsumerProcessState2);
            }
            if (isForegroundService != this.mInForegroundService) {
                if (isForegroundService) {
                    noteForegroundServiceResumedLocked(j);
                } else {
                    noteForegroundServicePausedLocked(j);
                }
                this.mInForegroundService = isForegroundService;
            }
        }

        @com.android.internal.annotations.GuardedBy({"mBsi"})
        private void maybeScheduleExternalStatsSync(int i, int i2) {
            int i3;
            if (i == i2) {
                return;
            }
            if (i != 0 || i2 != 2) {
                if (i == 2 && i2 == 0) {
                    return;
                }
                if (com.android.server.power.stats.BatteryStatsImpl.isActiveRadioPowerState(this.mBsi.mMobileRadioPowerState)) {
                    i3 = 14;
                } else {
                    i3 = 10;
                }
                this.mBsi.mExternalSync.scheduleSyncDueToProcessStateChange(i3, this.mBsi.mConstants.PROC_STATE_CHANGE_COLLECTION_DELAY_MS);
            }
        }

        public boolean isInBackground() {
            return this.mProcessState >= 3;
        }

        public boolean updateOnBatteryBgTimeBase(long j, long j2) {
            return this.mOnBatteryBackgroundTimeBase.setRunning(this.mBsi.mOnBatteryTimeBase.isRunning() && isInBackground(), j, j2);
        }

        public boolean updateOnBatteryScreenOffBgTimeBase(long j, long j2) {
            return this.mOnBatteryScreenOffBackgroundTimeBase.setRunning(this.mBsi.mOnBatteryScreenOffTimeBase.isRunning() && isInBackground(), j, j2);
        }

        public android.util.SparseArray<? extends android.os.BatteryStats.Uid.Pid> getPidStats() {
            return this.mPids;
        }

        public android.os.BatteryStats.Uid.Pid getPidStatsLocked(int i) {
            android.os.BatteryStats.Uid.Pid pid = this.mPids.get(i);
            if (pid == null) {
                android.os.BatteryStats.Uid.Pid pid2 = new android.os.BatteryStats.Uid.Pid(this);
                this.mPids.put(i, pid2);
                return pid2;
            }
            return pid;
        }

        public com.android.server.power.stats.BatteryStatsImpl.Uid.Pkg getPackageStatsLocked(java.lang.String str) {
            com.android.server.power.stats.BatteryStatsImpl.Uid.Pkg pkg = this.mPackageStats.get(str);
            if (pkg == null) {
                com.android.server.power.stats.BatteryStatsImpl.Uid.Pkg pkg2 = new com.android.server.power.stats.BatteryStatsImpl.Uid.Pkg(this.mBsi);
                this.mPackageStats.put(str, pkg2);
                return pkg2;
            }
            return pkg;
        }

        public com.android.server.power.stats.BatteryStatsImpl.Uid.Pkg.Serv getServiceStatsLocked(java.lang.String str, java.lang.String str2) {
            com.android.server.power.stats.BatteryStatsImpl.Uid.Pkg packageStatsLocked = getPackageStatsLocked(str);
            com.android.server.power.stats.BatteryStatsImpl.Uid.Pkg.Serv serv = packageStatsLocked.mServiceStats.get(str2);
            if (serv == null) {
                com.android.server.power.stats.BatteryStatsImpl.Uid.Pkg.Serv newServiceStatsLocked = packageStatsLocked.newServiceStatsLocked();
                packageStatsLocked.mServiceStats.put(str2, newServiceStatsLocked);
                return newServiceStatsLocked;
            }
            return serv;
        }

        public void readSyncSummaryFromParcelLocked(java.lang.String str, android.os.Parcel parcel) {
            com.android.server.power.stats.BatteryStatsImpl.DualTimer instantiateObject = this.mSyncStats.instantiateObject();
            instantiateObject.readSummaryFromParcelLocked(parcel);
            this.mSyncStats.add(str, instantiateObject);
        }

        public void readJobSummaryFromParcelLocked(java.lang.String str, android.os.Parcel parcel) {
            com.android.server.power.stats.BatteryStatsImpl.DualTimer instantiateObject = this.mJobStats.instantiateObject();
            instantiateObject.readSummaryFromParcelLocked(parcel);
            this.mJobStats.add(str, instantiateObject);
        }

        public void readWakeSummaryFromParcelLocked(java.lang.String str, android.os.Parcel parcel) {
            com.android.server.power.stats.BatteryStatsImpl.Uid.Wakelock wakelock = new com.android.server.power.stats.BatteryStatsImpl.Uid.Wakelock(this.mBsi, this);
            this.mWakelockStats.add(str, wakelock);
            if (parcel.readInt() != 0) {
                getWakelockTimerLocked(wakelock, 1).readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                getWakelockTimerLocked(wakelock, 0).readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                getWakelockTimerLocked(wakelock, 2).readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                getWakelockTimerLocked(wakelock, 18).readSummaryFromParcelLocked(parcel);
            }
        }

        public com.android.server.power.stats.BatteryStatsImpl.DualTimer getSensorTimerLocked(int i, boolean z) {
            java.util.ArrayList arrayList;
            com.android.server.power.stats.BatteryStatsImpl.Uid.Sensor sensor = this.mSensorStats.get(i);
            if (sensor == null) {
                if (!z) {
                    return null;
                }
                sensor = new com.android.server.power.stats.BatteryStatsImpl.Uid.Sensor(this.mBsi, this, i);
                this.mSensorStats.put(i, sensor);
            }
            com.android.server.power.stats.BatteryStatsImpl.DualTimer dualTimer = sensor.mTimer;
            if (dualTimer != null) {
                return dualTimer;
            }
            java.util.ArrayList arrayList2 = (java.util.ArrayList) this.mBsi.mSensorTimers.get(i);
            if (arrayList2 != null) {
                arrayList = arrayList2;
            } else {
                java.util.ArrayList arrayList3 = new java.util.ArrayList();
                this.mBsi.mSensorTimers.put(i, arrayList3);
                arrayList = arrayList3;
            }
            com.android.server.power.stats.BatteryStatsImpl.DualTimer dualTimer2 = new com.android.server.power.stats.BatteryStatsImpl.DualTimer(this.mBsi.mClock, this, 3, arrayList, this.mBsi.mOnBatteryTimeBase, this.mOnBatteryBackgroundTimeBase);
            sensor.mTimer = dualTimer2;
            return dualTimer2;
        }

        public void noteStartSyncLocked(java.lang.String str, long j) {
            com.android.server.power.stats.BatteryStatsImpl.DualTimer startObject = this.mSyncStats.startObject(str, j);
            if (startObject != null) {
                startObject.startRunningLocked(j);
            }
        }

        public void noteStopSyncLocked(java.lang.String str, long j) {
            com.android.server.power.stats.BatteryStatsImpl.DualTimer stopObject = this.mSyncStats.stopObject(str, j);
            if (stopObject != null) {
                stopObject.stopRunningLocked(j);
            }
        }

        public void noteStartJobLocked(java.lang.String str, long j) {
            com.android.server.power.stats.BatteryStatsImpl.DualTimer startObject = this.mJobStats.startObject(str, j);
            if (startObject != null) {
                startObject.startRunningLocked(j);
            }
        }

        public void noteStopJobLocked(java.lang.String str, long j, int i) {
            com.android.server.power.stats.BatteryStatsImpl.DualTimer stopObject = this.mJobStats.stopObject(str, j);
            if (stopObject != null) {
                stopObject.stopRunningLocked(j);
            }
            if (this.mBsi.mOnBatteryTimeBase.isRunning()) {
                android.util.SparseIntArray sparseIntArray = this.mJobCompletions.get(str);
                if (sparseIntArray == null) {
                    sparseIntArray = new android.util.SparseIntArray();
                    this.mJobCompletions.put(str, sparseIntArray);
                }
                sparseIntArray.put(i, sparseIntArray.get(i, 0) + 1);
            }
        }

        public com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer getWakelockTimerLocked(com.android.server.power.stats.BatteryStatsImpl.Uid.Wakelock wakelock, int i) {
            if (wakelock == null) {
                return null;
            }
            switch (i) {
                case 0:
                    com.android.server.power.stats.BatteryStatsImpl.DualTimer dualTimer = wakelock.mTimerPartial;
                    if (dualTimer == null) {
                        com.android.server.power.stats.BatteryStatsImpl.DualTimer dualTimer2 = new com.android.server.power.stats.BatteryStatsImpl.DualTimer(this.mBsi.mClock, this, 0, this.mBsi.mPartialTimers, this.mBsi.mOnBatteryScreenOffTimeBase, this.mOnBatteryScreenOffBackgroundTimeBase);
                        wakelock.mTimerPartial = dualTimer2;
                        return dualTimer2;
                    }
                    return dualTimer;
                case 1:
                    com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer stopwatchTimer = wakelock.mTimerFull;
                    if (stopwatchTimer == null) {
                        com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer stopwatchTimer2 = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mBsi.mClock, this, 1, this.mBsi.mFullTimers, this.mBsi.mOnBatteryTimeBase);
                        wakelock.mTimerFull = stopwatchTimer2;
                        return stopwatchTimer2;
                    }
                    return stopwatchTimer;
                case 2:
                    com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer stopwatchTimer3 = wakelock.mTimerWindow;
                    if (stopwatchTimer3 == null) {
                        com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer stopwatchTimer4 = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mBsi.mClock, this, 2, this.mBsi.mWindowTimers, this.mBsi.mOnBatteryTimeBase);
                        wakelock.mTimerWindow = stopwatchTimer4;
                        return stopwatchTimer4;
                    }
                    return stopwatchTimer3;
                case 18:
                    com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer stopwatchTimer5 = wakelock.mTimerDraw;
                    if (stopwatchTimer5 == null) {
                        com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer stopwatchTimer6 = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mBsi.mClock, this, 18, this.mBsi.mDrawTimers, this.mBsi.mOnBatteryTimeBase);
                        wakelock.mTimerDraw = stopwatchTimer6;
                        return stopwatchTimer6;
                    }
                    return stopwatchTimer5;
                default:
                    throw new java.lang.IllegalArgumentException("type=" + i);
            }
        }

        public void noteStartWakeLocked(int i, java.lang.String str, int i2, long j) {
            com.android.server.power.stats.BatteryStatsImpl.Uid.Wakelock startObject = this.mWakelockStats.startObject(str, j);
            if (startObject != null) {
                getWakelockTimerLocked(startObject, i2).startRunningLocked(j);
            }
            if (i2 == 0) {
                createAggregatedPartialWakelockTimerLocked().startRunningLocked(j);
                if (i >= 0) {
                    android.os.BatteryStats.Uid.Pid pidStatsLocked = getPidStatsLocked(i);
                    int i3 = pidStatsLocked.mWakeNesting;
                    pidStatsLocked.mWakeNesting = i3 + 1;
                    if (i3 == 0) {
                        pidStatsLocked.mWakeStartMs = j;
                    }
                }
            }
        }

        public void noteStopWakeLocked(int i, java.lang.String str, int i2, long j) {
            android.os.BatteryStats.Uid.Pid pid;
            com.android.server.power.stats.BatteryStatsImpl.Uid.Wakelock stopObject = this.mWakelockStats.stopObject(str, j);
            if (stopObject != null) {
                getWakelockTimerLocked(stopObject, i2).stopRunningLocked(j);
            }
            if (i2 == 0) {
                if (this.mAggregatedPartialWakelockTimer != null) {
                    this.mAggregatedPartialWakelockTimer.stopRunningLocked(j);
                }
                if (i >= 0 && (pid = this.mPids.get(i)) != null && pid.mWakeNesting > 0) {
                    int i3 = pid.mWakeNesting;
                    pid.mWakeNesting = i3 - 1;
                    if (i3 == 1) {
                        pid.mWakeSumMs += j - pid.mWakeStartMs;
                        pid.mWakeStartMs = 0L;
                    }
                }
            }
        }

        public void reportExcessiveCpuLocked(java.lang.String str, long j, long j2) {
            com.android.server.power.stats.BatteryStatsImpl.Uid.Proc processStatsLocked = getProcessStatsLocked(str);
            if (processStatsLocked != null) {
                processStatsLocked.addExcessiveCpu(j, j2);
            }
        }

        public void noteStartSensor(int i, long j) {
            getSensorTimerLocked(i, true).startRunningLocked(j);
        }

        public void noteStopSensor(int i, long j) {
            com.android.server.power.stats.BatteryStatsImpl.DualTimer sensorTimerLocked = getSensorTimerLocked(i, false);
            if (sensorTimerLocked != null) {
                sensorTimerLocked.stopRunningLocked(j);
            }
        }

        public void noteStartGps(long j) {
            noteStartSensor(com.android.server.am.ProcessList.INVALID_ADJ, j);
        }

        public void noteStopGps(long j) {
            noteStopSensor(com.android.server.am.ProcessList.INVALID_ADJ, j);
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public com.android.internal.os.CpuScalingPolicies getCpuScalingPolicies() {
        return this.mCpuScalingPolicies;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private com.android.internal.os.LongArrayMultiStateCounter.LongArrayContainer getCpuTimeInFreqContainer() {
        if (this.mTmpCpuTimeInFreq == null) {
            this.mTmpCpuTimeInFreq = new com.android.internal.os.LongArrayMultiStateCounter.LongArrayContainer(this.mCpuScalingPolicies.getScalingStepCount());
        }
        return this.mTmpCpuTimeInFreq;
    }

    public BatteryStatsImpl(@android.annotation.NonNull com.android.server.power.stats.BatteryStatsImpl.BatteryStatsConfig batteryStatsConfig, @android.annotation.NonNull com.android.internal.os.Clock clock, @android.annotation.NonNull com.android.internal.os.MonotonicClock monotonicClock, @android.annotation.Nullable java.io.File file, @android.annotation.NonNull android.os.Handler handler, @android.annotation.Nullable com.android.server.power.stats.BatteryStatsImpl.PlatformIdleStateCallback platformIdleStateCallback, @android.annotation.Nullable com.android.server.power.stats.BatteryStatsImpl.EnergyStatsRetriever energyStatsRetriever, @android.annotation.NonNull com.android.server.power.stats.BatteryStatsImpl.UserInfoProvider userInfoProvider, @android.annotation.NonNull com.android.internal.os.PowerProfile powerProfile, @android.annotation.NonNull com.android.internal.os.CpuScalingPolicies cpuScalingPolicies, @android.annotation.NonNull com.android.server.power.stats.PowerStatsUidResolver powerStatsUidResolver) {
        this.mTmpWakelockStats = new com.android.server.power.stats.KernelWakelockStats();
        this.mKernelMemoryStats = new android.util.LongSparseArray<>();
        this.mPerProcStateCpuTimesAvailable = true;
        this.mCpuTimeReadsTrackingStartTimeMs = android.os.SystemClock.uptimeMillis();
        this.mTmpRpmStats = null;
        this.mLastRpmStatsUpdateTimeMs = -1000L;
        this.mPendingRemovedUids = new java.util.LinkedList();
        this.mDeferSetCharging = new java.lang.Runnable() { // from class: com.android.server.power.stats.BatteryStatsImpl.2
            @Override // java.lang.Runnable
            public void run() {
                synchronized (com.android.server.power.stats.BatteryStatsImpl.this) {
                    try {
                        if (com.android.server.power.stats.BatteryStatsImpl.this.mOnBattery) {
                            return;
                        }
                        if (com.android.server.power.stats.BatteryStatsImpl.this.setChargingLocked(true)) {
                            long uptimeMillis = com.android.server.power.stats.BatteryStatsImpl.this.mClock.uptimeMillis();
                            com.android.server.power.stats.BatteryStatsImpl.this.mHistory.writeHistoryItem(com.android.server.power.stats.BatteryStatsImpl.this.mClock.elapsedRealtime(), uptimeMillis);
                        }
                    } catch (java.lang.Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mExternalSync = null;
        this.mUserInfoProvider = null;
        this.mUidStats = new android.util.SparseArray<>();
        this.mPartialTimers = new java.util.ArrayList<>();
        this.mFullTimers = new java.util.ArrayList<>();
        this.mWindowTimers = new java.util.ArrayList<>();
        this.mDrawTimers = new java.util.ArrayList<>();
        this.mSensorTimers = new android.util.SparseArray<>();
        this.mWifiRunningTimers = new java.util.ArrayList<>();
        this.mFullWifiLockTimers = new java.util.ArrayList<>();
        this.mWifiMulticastTimers = new java.util.ArrayList<>();
        this.mWifiScanTimers = new java.util.ArrayList<>();
        this.mWifiBatchedScanTimers = new android.util.SparseArray<>();
        this.mAudioTurnedOnTimers = new java.util.ArrayList<>();
        this.mVideoTurnedOnTimers = new java.util.ArrayList<>();
        this.mFlashlightTurnedOnTimers = new java.util.ArrayList<>();
        this.mCameraTurnedOnTimers = new java.util.ArrayList<>();
        this.mBluetoothScanOnTimers = new java.util.ArrayList<>();
        this.mLastPartialTimers = new java.util.ArrayList<>();
        this.mOnBatteryTimeBase = new com.android.server.power.stats.BatteryStatsImpl.TimeBase(true);
        this.mOnBatteryScreenOffTimeBase = new com.android.server.power.stats.BatteryStatsImpl.TimeBase(true);
        this.mActiveEvents = new android.os.BatteryStats.HistoryEventTracker();
        this.mStepDetailsCalculator = new com.android.server.power.stats.BatteryStatsImpl.HistoryStepDetailsCalculatorImpl();
        this.mPowerStatsDescriptorRegistry = new com.android.internal.os.PowerStats.DescriptorRegistry();
        this.mHaveBatteryLevel = false;
        this.mBatteryPluggedInRealTimeMs = 0L;
        this.mIgnoreNextExternalStats = false;
        this.mMonotonicEndTime = -1L;
        this.mScreenState = 0;
        this.mScreenBrightnessBin = -1;
        this.mScreenBrightnessTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer[5];
        this.mDisplayMismatchWtfCount = 0;
        this.mUsbDataState = 0;
        this.mGpsSignalQualityBin = -1;
        this.mGpsSignalQualityTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer[2];
        this.mPhoneSignalStrengthBin = -1;
        this.mPhoneSignalStrengthBinRaw = -1;
        this.mPhoneSignalStrengthsTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer[CELL_SIGNAL_STRENGTH_LEVEL_COUNT];
        this.mPhoneDataConnectionType = -1;
        this.mPhoneDataConnectionsTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer[android.os.BatteryStats.NUM_DATA_CONNECTION_TYPES];
        this.mNrState = -1;
        this.mActiveRat = 0;
        this.mPerRatBatteryStats = new com.android.server.power.stats.BatteryStatsImpl.RadioAccessTechnologyBatteryStats[3];
        this.mNetworkByteActivityCounters = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter[10];
        this.mNetworkPacketActivityCounters = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter[10];
        this.mHasWifiReporting = false;
        this.mHasBluetoothReporting = false;
        this.mHasModemReporting = false;
        this.mWifiState = -1;
        this.mWifiStateTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer[8];
        this.mWifiSupplState = -1;
        this.mWifiSupplStateTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer[13];
        this.mWifiSignalStrengthBin = -1;
        this.mWifiSignalStrengthsTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer[5];
        this.mMobileRadioPowerState = 1;
        this.mWifiRadioPowerState = 1;
        this.mBluetoothPowerCalculator = null;
        this.mCpuPowerCalculator = null;
        this.mMobileRadioPowerCalculator = null;
        this.mWifiPowerCalculator = null;
        this.mCharging = true;
        this.mInitStepMode = 0;
        this.mCurStepMode = 0;
        this.mModStepMode = 0;
        this.mDischargeStepTracker = new android.os.BatteryStats.LevelStepTracker(200);
        this.mDailyDischargeStepTracker = new android.os.BatteryStats.LevelStepTracker(400);
        this.mChargeStepTracker = new android.os.BatteryStats.LevelStepTracker(200);
        this.mDailyChargeStepTracker = new android.os.BatteryStats.LevelStepTracker(400);
        this.mDailyStartTimeMs = 0L;
        this.mNextMinDailyDeadlineMs = 0L;
        this.mNextMaxDailyDeadlineMs = 0L;
        this.mDailyItems = new java.util.ArrayList<>();
        this.mLastWriteTimeMs = 0L;
        this.mPhoneServiceState = -1;
        this.mPhoneServiceStateRaw = -1;
        this.mPhoneSimStateRaw = -1;
        this.mEstimatedBatteryCapacityMah = -1;
        this.mLastLearnedBatteryCapacityUah = -1;
        this.mMinLearnedBatteryCapacityUah = -1;
        this.mMaxLearnedBatteryCapacityUah = -1;
        this.mBatteryTimeToFullSeconds = -1L;
        this.mAlarmManager = null;
        this.mLongPlugInAlarmHandler = new android.app.AlarmManager.OnAlarmListener() { // from class: com.android.server.power.stats.BatteryStatsImpl$$ExternalSyntheticLambda6
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                com.android.server.power.stats.BatteryStatsImpl.this.lambda$new$1();
            }
        };
        this.mRpmStats = new java.util.HashMap<>();
        this.mScreenOffRpmStats = new java.util.HashMap<>();
        this.mKernelWakelockStats = new java.util.HashMap<>();
        this.mLastWakeupReason = null;
        this.mLastWakeupUptimeMs = 0L;
        this.mLastWakeupElapsedTimeMs = 0L;
        this.mWakeupReasonStats = new java.util.HashMap<>();
        this.mWifiFullLockNesting = 0;
        this.mWifiScanNesting = 0;
        this.mWifiMulticastNesting = 0;
        this.mWifiNetworkLock = new java.lang.Object();
        this.mWifiIfaces = libcore.util.EmptyArray.STRING;
        this.mModemNetworkLock = new java.lang.Object();
        this.mModemIfaces = libcore.util.EmptyArray.STRING;
        this.mLastModemActivityInfo = null;
        this.mLastBluetoothActivityInfo = new com.android.server.power.stats.BatteryStatsImpl.BluetoothActivityInfoCache();
        this.mWriteAsyncRunnable = new java.lang.Runnable() { // from class: com.android.server.power.stats.BatteryStatsImpl$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.power.stats.BatteryStatsImpl.this.lambda$new$10();
            }
        };
        this.mWriteLock = new java.util.concurrent.locks.ReentrantLock();
        this.mClock = clock;
        initKernelStatsReaders();
        this.mBatteryStatsConfig = batteryStatsConfig;
        this.mMonotonicClock = monotonicClock;
        this.mHandler = new com.android.server.power.stats.BatteryStatsImpl.MyHandler(handler.getLooper());
        this.mConstants = new com.android.server.power.stats.BatteryStatsImpl.Constants(this.mHandler);
        this.mPowerProfile = powerProfile;
        this.mCpuScalingPolicies = cpuScalingPolicies;
        this.mPowerStatsUidResolver = powerStatsUidResolver;
        this.mFrameworkStatsLogger = new com.android.server.power.stats.BatteryStatsImpl.FrameworkStatsLogger();
        initPowerProfile();
        if (file != null) {
            this.mStatsFile = new android.util.AtomicFile(new java.io.File(file, "batterystats.bin"));
            this.mCheckinFile = new android.util.AtomicFile(new java.io.File(file, "batterystats-checkin.bin"));
            this.mDailyFile = new android.util.AtomicFile(new java.io.File(file, "batterystats-daily.xml"));
            this.mHistory = new com.android.internal.os.BatteryStatsHistory(file, this.mConstants.MAX_HISTORY_FILES, this.mConstants.MAX_HISTORY_BUFFER, this.mStepDetailsCalculator, this.mClock, this.mMonotonicClock);
        } else {
            this.mStatsFile = null;
            this.mCheckinFile = null;
            this.mDailyFile = null;
            this.mHistory = new com.android.internal.os.BatteryStatsHistory(this.mConstants.MAX_HISTORY_BUFFER, this.mStepDetailsCalculator, this.mClock, this.mMonotonicClock);
        }
        this.mCpuPowerStatsCollector = new com.android.server.power.stats.CpuPowerStatsCollector(this.mCpuScalingPolicies, this.mPowerProfile, this.mPowerStatsUidResolver, new java.util.function.IntSupplier() { // from class: com.android.server.power.stats.BatteryStatsImpl$$ExternalSyntheticLambda8
            @Override // java.util.function.IntSupplier
            public final int getAsInt() {
                int lambda$new$2;
                lambda$new$2 = com.android.server.power.stats.BatteryStatsImpl.this.lambda$new$2();
                return lambda$new$2;
            }
        }, this.mHandler, this.mBatteryStatsConfig.getPowerStatsThrottlePeriodCpu());
        this.mCpuPowerStatsCollector.addConsumer(new java.util.function.Consumer() { // from class: com.android.server.power.stats.BatteryStatsImpl$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.power.stats.BatteryStatsImpl.this.recordPowerStats((com.android.internal.os.PowerStats) obj);
            }
        });
        this.mStartCount++;
        initTimersAndCounters();
        this.mOnBatteryInternal = false;
        this.mOnBattery = false;
        long uptimeMillis = this.mClock.uptimeMillis() * 1000;
        long elapsedRealtime = this.mClock.elapsedRealtime() * 1000;
        initTimes(uptimeMillis, elapsedRealtime);
        java.lang.String str = android.os.Build.ID;
        this.mEndPlatformVersion = str;
        this.mStartPlatformVersion = str;
        initDischarge(elapsedRealtime);
        updateDailyDeadlineLocked();
        this.mPlatformIdleStateCallback = platformIdleStateCallback;
        this.mEnergyConsumerRetriever = energyStatsRetriever;
        this.mUserInfoProvider = userInfoProvider;
        this.mPowerStatsUidResolver.addListener(new com.android.server.power.stats.PowerStatsUidResolver.Listener() { // from class: com.android.server.power.stats.BatteryStatsImpl.4
            @Override // com.android.server.power.stats.PowerStatsUidResolver.Listener
            public void onIsolatedUidAdded(int i, int i2) {
                com.android.server.power.stats.BatteryStatsImpl.this.onIsolatedUidAdded(i, i2);
            }

            @Override // com.android.server.power.stats.PowerStatsUidResolver.Listener
            public void onBeforeIsolatedUidRemoved(int i, int i2) {
                com.android.server.power.stats.BatteryStatsImpl.this.onBeforeIsolatedUidRemoved(i, i2);
            }

            @Override // com.android.server.power.stats.PowerStatsUidResolver.Listener
            public void onAfterIsolatedUidRemoved(int i, int i2) {
                com.android.server.power.stats.BatteryStatsImpl.this.onAfterIsolatedUidRemoved(i, i2);
            }
        });
        this.mDeviceIdleMode = 0;
        this.mFrameworkStatsLogger.deviceIdleModeStateChanged(this.mDeviceIdleMode);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$2() {
        return this.mBatteryVoltageMv;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recordPowerStats(com.android.internal.os.PowerStats powerStats) {
        if (powerStats.durationMs > 0) {
            synchronized (this) {
                this.mHistory.recordPowerStats(this.mClock.elapsedRealtime(), this.mClock.uptimeMillis(), powerStats);
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected void initTimersAndCounters() {
        this.mScreenOnTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mClock, null, -1, null, this.mOnBatteryTimeBase);
        this.mScreenDozeTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mClock, null, -1, null, this.mOnBatteryTimeBase);
        for (int i = 0; i < 5; i++) {
            this.mScreenBrightnessTimer[i] = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mClock, null, (-100) - i, null, this.mOnBatteryTimeBase);
        }
        this.mPerDisplayBatteryStats = new com.android.server.power.stats.BatteryStatsImpl.DisplayBatteryStats[1];
        this.mPerDisplayBatteryStats[0] = new com.android.server.power.stats.BatteryStatsImpl.DisplayBatteryStats(this.mClock, this.mOnBatteryTimeBase);
        this.mInteractiveTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mClock, null, -10, null, this.mOnBatteryTimeBase);
        this.mPowerSaveModeEnabledTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mClock, null, -2, null, this.mOnBatteryTimeBase);
        this.mDeviceIdleModeLightTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mClock, null, -11, null, this.mOnBatteryTimeBase);
        this.mDeviceIdleModeFullTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mClock, null, -14, null, this.mOnBatteryTimeBase);
        this.mDeviceLightIdlingTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mClock, null, -15, null, this.mOnBatteryTimeBase);
        this.mDeviceIdlingTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mClock, null, -12, null, this.mOnBatteryTimeBase);
        this.mPhoneOnTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mClock, null, -3, null, this.mOnBatteryTimeBase);
        for (int i2 = 0; i2 < CELL_SIGNAL_STRENGTH_LEVEL_COUNT; i2++) {
            this.mPhoneSignalStrengthsTimer[i2] = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mClock, null, (-200) - i2, null, this.mOnBatteryTimeBase);
        }
        this.mPhoneSignalScanningTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mClock, null, -199, null, this.mOnBatteryTimeBase);
        for (int i3 = 0; i3 < android.os.BatteryStats.NUM_DATA_CONNECTION_TYPES; i3++) {
            this.mPhoneDataConnectionsTimer[i3] = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mClock, null, (-300) - i3, null, this.mOnBatteryTimeBase);
        }
        this.mNrNsaTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mClock, null, -198, null, this.mOnBatteryTimeBase);
        for (int i4 = 0; i4 < 10; i4++) {
            this.mNetworkByteActivityCounters[i4] = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter(this.mOnBatteryTimeBase);
            this.mNetworkPacketActivityCounters[i4] = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter(this.mOnBatteryTimeBase);
        }
        this.mWifiActivity = new com.android.server.power.stats.BatteryStatsImpl.ControllerActivityCounterImpl(this.mClock, this.mOnBatteryTimeBase, 1);
        this.mBluetoothActivity = new com.android.server.power.stats.BatteryStatsImpl.ControllerActivityCounterImpl(this.mClock, this.mOnBatteryTimeBase, 1);
        this.mModemActivity = new com.android.server.power.stats.BatteryStatsImpl.ControllerActivityCounterImpl(this.mClock, this.mOnBatteryTimeBase, MODEM_TX_POWER_LEVEL_COUNT);
        this.mMobileRadioActiveTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mClock, null, -400, null, this.mOnBatteryTimeBase);
        this.mMobileRadioActivePerAppTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mClock, null, -401, null, this.mOnBatteryTimeBase);
        this.mMobileRadioActiveAdjustedTime = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter(this.mOnBatteryTimeBase);
        this.mMobileRadioActiveUnknownTime = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter(this.mOnBatteryTimeBase);
        this.mMobileRadioActiveUnknownCount = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter(this.mOnBatteryTimeBase);
        this.mWifiMulticastWakelockTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mClock, null, 23, null, this.mOnBatteryTimeBase);
        this.mWifiOnTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mClock, null, -4, null, this.mOnBatteryTimeBase);
        this.mGlobalWifiRunningTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mClock, null, -5, null, this.mOnBatteryTimeBase);
        for (int i5 = 0; i5 < 8; i5++) {
            this.mWifiStateTimer[i5] = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mClock, null, (-600) - i5, null, this.mOnBatteryTimeBase);
        }
        for (int i6 = 0; i6 < 13; i6++) {
            this.mWifiSupplStateTimer[i6] = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mClock, null, (-700) - i6, null, this.mOnBatteryTimeBase);
        }
        for (int i7 = 0; i7 < 5; i7++) {
            this.mWifiSignalStrengthsTimer[i7] = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mClock, null, (-800) - i7, null, this.mOnBatteryTimeBase);
        }
        this.mWifiActiveTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mClock, null, com.android.server.am.ProcessList.SYSTEM_ADJ, null, this.mOnBatteryTimeBase);
        for (int i8 = 0; i8 < this.mGpsSignalQualityTimer.length; i8++) {
            this.mGpsSignalQualityTimer[i8] = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mClock, null, (-1000) - i8, null, this.mOnBatteryTimeBase);
        }
        this.mAudioOnTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mClock, null, -7, null, this.mOnBatteryTimeBase);
        this.mVideoOnTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mClock, null, -8, null, this.mOnBatteryTimeBase);
        this.mFlashlightOnTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mClock, null, -9, null, this.mOnBatteryTimeBase);
        this.mCameraOnTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mClock, null, -13, null, this.mOnBatteryTimeBase);
        this.mBluetoothScanTimer = new com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer(this.mClock, null, -14, null, this.mOnBatteryTimeBase);
        this.mDischargeScreenOffCounter = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter(this.mOnBatteryScreenOffTimeBase);
        this.mDischargeScreenDozeCounter = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter(this.mOnBatteryTimeBase);
        this.mDischargeLightDozeCounter = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter(this.mOnBatteryTimeBase);
        this.mDischargeDeepDozeCounter = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter(this.mOnBatteryTimeBase);
        this.mDischargeCounter = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter(this.mOnBatteryTimeBase);
        this.mDischargeUnplugLevel = 0;
        this.mDischargePlugLevel = -1;
        this.mDischargeCurrentLevel = 0;
        this.mBatteryLevel = 0;
    }

    private void initPowerProfile() {
        int[] policies = this.mCpuScalingPolicies.getPolicies();
        this.mKernelCpuSpeedReaders = new com.android.internal.os.KernelCpuSpeedReader[policies.length];
        for (int i = 0; i < policies.length; i++) {
            this.mKernelCpuSpeedReaders[i] = new com.android.internal.os.KernelCpuSpeedReader(this.mCpuScalingPolicies.getRelatedCpus(policies[i])[0], this.mCpuScalingPolicies.getFrequencies(policies[i]).length);
        }
        this.mCpuPowerBracketMap = new int[this.mCpuScalingPolicies.getScalingStepCount()];
        int i2 = 0;
        for (int i3 : policies) {
            int length = this.mCpuScalingPolicies.getFrequencies(i3).length;
            int i4 = 0;
            while (i4 < length) {
                this.mCpuPowerBracketMap[i2] = this.mPowerProfile.getCpuPowerBracketForScalingStep(i3, i4);
                i4++;
                i2++;
            }
        }
        if (this.mEstimatedBatteryCapacityMah == -1) {
            this.mEstimatedBatteryCapacityMah = (int) this.mPowerProfile.getBatteryCapacity();
        }
        setDisplayCountLocked(this.mPowerProfile.getNumDisplays());
    }

    com.android.internal.os.PowerProfile getPowerProfile() {
        return this.mPowerProfile;
    }

    public void startTrackingSystemServerCpuTime() {
        this.mSystemServerCpuThreadReader.startTrackingThreadCpuTime();
    }

    public com.android.server.power.stats.SystemServerCpuThreadReader.SystemServiceCpuThreadTimes getSystemServiceCpuThreadTimes() {
        return this.mSystemServerCpuThreadReader.readAbsolute();
    }

    public void setCallback(com.android.server.power.stats.BatteryStatsImpl.BatteryCallback batteryCallback) {
        this.mCallback = batteryCallback;
    }

    public void setRadioScanningTimeoutLocked(long j) {
        if (this.mPhoneSignalScanningTimer != null) {
            this.mPhoneSignalScanningTimer.setTimeout(j);
        }
    }

    public void setExternalStatsSyncLocked(com.android.server.power.stats.BatteryStatsImpl.ExternalStatsSync externalStatsSync) {
        this.mExternalSync = externalStatsSync;
    }

    public void setDisplayCountLocked(int i) {
        this.mPerDisplayBatteryStats = new com.android.server.power.stats.BatteryStatsImpl.DisplayBatteryStats[i];
        for (int i2 = 0; i2 < i; i2++) {
            this.mPerDisplayBatteryStats[i2] = new com.android.server.power.stats.BatteryStatsImpl.DisplayBatteryStats(this.mClock, this.mOnBatteryTimeBase);
        }
    }

    public void updateDailyDeadlineLocked() {
        long currentTimeMillis = this.mClock.currentTimeMillis();
        this.mDailyStartTimeMs = currentTimeMillis;
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTimeInMillis(currentTimeMillis);
        calendar.set(6, calendar.get(6) + 1);
        calendar.set(14, 0);
        calendar.set(13, 0);
        calendar.set(12, 0);
        calendar.set(11, 1);
        this.mNextMinDailyDeadlineMs = calendar.getTimeInMillis();
        calendar.set(11, 3);
        this.mNextMaxDailyDeadlineMs = calendar.getTimeInMillis();
    }

    public void recordDailyStatsIfNeededLocked(boolean z, long j) {
        if (j >= this.mNextMaxDailyDeadlineMs) {
            recordDailyStatsLocked();
            return;
        }
        if (z && j >= this.mNextMinDailyDeadlineMs) {
            recordDailyStatsLocked();
        } else if (j < this.mDailyStartTimeMs - 86400000) {
            recordDailyStatsLocked();
        }
    }

    public void recordDailyStatsLocked() {
        boolean z;
        android.os.BatteryStats.DailyItem dailyItem = new android.os.BatteryStats.DailyItem();
        dailyItem.mStartTime = this.mDailyStartTimeMs;
        dailyItem.mEndTime = this.mClock.currentTimeMillis();
        boolean z2 = true;
        if (this.mDailyDischargeStepTracker.mNumStepDurations <= 0) {
            z = false;
        } else {
            dailyItem.mDischargeSteps = new android.os.BatteryStats.LevelStepTracker(this.mDailyDischargeStepTracker.mNumStepDurations, this.mDailyDischargeStepTracker.mStepDurations);
            z = true;
        }
        if (this.mDailyChargeStepTracker.mNumStepDurations > 0) {
            dailyItem.mChargeSteps = new android.os.BatteryStats.LevelStepTracker(this.mDailyChargeStepTracker.mNumStepDurations, this.mDailyChargeStepTracker.mStepDurations);
            z = true;
        }
        if (this.mDailyPackageChanges == null) {
            z2 = z;
        } else {
            dailyItem.mPackageChanges = this.mDailyPackageChanges;
            this.mDailyPackageChanges = null;
        }
        this.mDailyDischargeStepTracker.init();
        this.mDailyChargeStepTracker.init();
        updateDailyDeadlineLocked();
        if (z2) {
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            this.mDailyItems.add(dailyItem);
            while (this.mDailyItems.size() > 10) {
                this.mDailyItems.remove(0);
            }
            final java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            try {
                writeDailyItemsLocked(android.util.Xml.resolveSerializer(byteArrayOutputStream));
                final long uptimeMillis2 = android.os.SystemClock.uptimeMillis() - uptimeMillis;
                com.android.internal.os.BackgroundThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.power.stats.BatteryStatsImpl.5
                    @Override // java.lang.Runnable
                    public void run() {
                        synchronized (com.android.server.power.stats.BatteryStatsImpl.this.mCheckinFile) {
                            long uptimeMillis3 = android.os.SystemClock.uptimeMillis();
                            java.io.FileOutputStream fileOutputStream = null;
                            try {
                                fileOutputStream = com.android.server.power.stats.BatteryStatsImpl.this.mDailyFile.startWrite();
                                byteArrayOutputStream.writeTo(fileOutputStream);
                                fileOutputStream.flush();
                                com.android.server.power.stats.BatteryStatsImpl.this.mDailyFile.finishWrite(fileOutputStream);
                                com.android.internal.logging.EventLogTags.writeCommitSysConfigFile("batterystats-daily", (uptimeMillis2 + android.os.SystemClock.uptimeMillis()) - uptimeMillis3);
                            } catch (java.io.IOException e) {
                                android.util.Slog.w("BatteryStats", "Error writing battery daily items", e);
                                com.android.server.power.stats.BatteryStatsImpl.this.mDailyFile.failWrite(fileOutputStream);
                            }
                        }
                    }
                });
            } catch (java.io.IOException e) {
            }
        }
    }

    private void writeDailyItemsLocked(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(64);
        typedXmlSerializer.startDocument((java.lang.String) null, true);
        typedXmlSerializer.startTag((java.lang.String) null, "daily-items");
        for (int i = 0; i < this.mDailyItems.size(); i++) {
            android.os.BatteryStats.DailyItem dailyItem = this.mDailyItems.get(i);
            typedXmlSerializer.startTag((java.lang.String) null, com.android.server.pm.Settings.TAG_ITEM);
            typedXmlSerializer.attributeLong((java.lang.String) null, "start", dailyItem.mStartTime);
            typedXmlSerializer.attributeLong((java.lang.String) null, "end", dailyItem.mEndTime);
            writeDailyLevelSteps(typedXmlSerializer, "dis", dailyItem.mDischargeSteps, sb);
            writeDailyLevelSteps(typedXmlSerializer, "chg", dailyItem.mChargeSteps, sb);
            if (dailyItem.mPackageChanges != null) {
                for (int i2 = 0; i2 < dailyItem.mPackageChanges.size(); i2++) {
                    android.os.BatteryStats.PackageChange packageChange = (android.os.BatteryStats.PackageChange) dailyItem.mPackageChanges.get(i2);
                    if (packageChange.mUpdate) {
                        typedXmlSerializer.startTag((java.lang.String) null, "upd");
                        typedXmlSerializer.attribute((java.lang.String) null, "pkg", packageChange.mPackageName);
                        typedXmlSerializer.attributeLong((java.lang.String) null, "ver", packageChange.mVersionCode);
                        typedXmlSerializer.endTag((java.lang.String) null, "upd");
                    } else {
                        typedXmlSerializer.startTag((java.lang.String) null, "rem");
                        typedXmlSerializer.attribute((java.lang.String) null, "pkg", packageChange.mPackageName);
                        typedXmlSerializer.endTag((java.lang.String) null, "rem");
                    }
                }
            }
            typedXmlSerializer.endTag((java.lang.String) null, com.android.server.pm.Settings.TAG_ITEM);
        }
        typedXmlSerializer.endTag((java.lang.String) null, "daily-items");
        typedXmlSerializer.endDocument();
    }

    private void writeDailyLevelSteps(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, java.lang.String str, android.os.BatteryStats.LevelStepTracker levelStepTracker, java.lang.StringBuilder sb) throws java.io.IOException {
        if (levelStepTracker != null) {
            typedXmlSerializer.startTag((java.lang.String) null, str);
            typedXmlSerializer.attributeInt((java.lang.String) null, "n", levelStepTracker.mNumStepDurations);
            for (int i = 0; i < levelStepTracker.mNumStepDurations; i++) {
                typedXmlSerializer.startTag((java.lang.String) null, "s");
                sb.setLength(0);
                levelStepTracker.encodeEntryAt(i, sb);
                typedXmlSerializer.attribute((java.lang.String) null, "v", sb.toString());
                typedXmlSerializer.endTag((java.lang.String) null, "s");
            }
            typedXmlSerializer.endTag((java.lang.String) null, str);
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void readDailyStatsLocked() {
        android.util.Slog.d(TAG, "Reading daily items from " + this.mDailyFile.getBaseFile());
        this.mDailyItems.clear();
        try {
            try {
                java.io.FileInputStream openRead = this.mDailyFile.openRead();
                try {
                    readDailyItemsLocked(android.util.Xml.resolvePullParser(openRead));
                    openRead.close();
                } catch (java.io.IOException e) {
                    openRead.close();
                } catch (java.lang.Throwable th) {
                    try {
                        openRead.close();
                    } catch (java.io.IOException e2) {
                    }
                    throw th;
                }
            } catch (java.io.IOException e3) {
            }
        } catch (java.io.FileNotFoundException e4) {
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x003b, code lost:
    
        if (r4 != 4) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0048, code lost:
    
        if (r8.getName().equals(com.android.server.pm.Settings.TAG_ITEM) == false) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x004a, code lost:
    
        readDailyItemTagLocked(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x004e, code lost:
    
        android.util.Slog.w(com.android.server.power.stats.BatteryStatsImpl.TAG, "Unknown element under <daily-items>: " + r8.getName());
        com.android.internal.util.XmlUtils.skipCurrentTag(r8);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void readDailyItemsLocked(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
        int next;
        do {
            try {
                next = typedXmlPullParser.next();
                if (next == 2) {
                    break;
                }
            } catch (java.io.IOException e) {
                android.util.Slog.w(TAG, "Failed parsing daily " + e);
            } catch (java.lang.IllegalStateException e2) {
                android.util.Slog.w(TAG, "Failed parsing daily " + e2);
            } catch (java.lang.IndexOutOfBoundsException e3) {
                android.util.Slog.w(TAG, "Failed parsing daily " + e3);
                return;
            } catch (java.lang.NullPointerException e4) {
                android.util.Slog.w(TAG, "Failed parsing daily " + e4);
            } catch (java.lang.NumberFormatException e5) {
                android.util.Slog.w(TAG, "Failed parsing daily " + e5);
            } catch (org.xmlpull.v1.XmlPullParserException e6) {
                android.util.Slog.w(TAG, "Failed parsing daily " + e6);
            }
        } while (next != 1);
        if (next != 2) {
            throw new java.lang.IllegalStateException("no start tag found");
        }
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next2 = typedXmlPullParser.next();
            if (next2 != 1) {
                if (next2 == 3 && typedXmlPullParser.getDepth() <= depth) {
                    break;
                }
            } else {
                break;
            }
        }
    }

    void readDailyItemTagLocked(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.lang.NumberFormatException, org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.os.BatteryStats.DailyItem dailyItem = new android.os.BatteryStats.DailyItem();
        dailyItem.mStartTime = typedXmlPullParser.getAttributeLong((java.lang.String) null, "start", 0L);
        dailyItem.mEndTime = typedXmlPullParser.getAttributeLong((java.lang.String) null, "end", 0L);
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                java.lang.String name = typedXmlPullParser.getName();
                if (name.equals("dis")) {
                    readDailyItemTagDetailsLocked(typedXmlPullParser, dailyItem, false, "dis");
                } else if (name.equals("chg")) {
                    readDailyItemTagDetailsLocked(typedXmlPullParser, dailyItem, true, "chg");
                } else if (name.equals("upd")) {
                    if (dailyItem.mPackageChanges == null) {
                        dailyItem.mPackageChanges = new java.util.ArrayList();
                    }
                    android.os.BatteryStats.PackageChange packageChange = new android.os.BatteryStats.PackageChange();
                    packageChange.mUpdate = true;
                    packageChange.mPackageName = typedXmlPullParser.getAttributeValue((java.lang.String) null, "pkg");
                    packageChange.mVersionCode = typedXmlPullParser.getAttributeLong((java.lang.String) null, "ver", 0L);
                    dailyItem.mPackageChanges.add(packageChange);
                    com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                } else if (name.equals("rem")) {
                    if (dailyItem.mPackageChanges == null) {
                        dailyItem.mPackageChanges = new java.util.ArrayList();
                    }
                    android.os.BatteryStats.PackageChange packageChange2 = new android.os.BatteryStats.PackageChange();
                    packageChange2.mUpdate = false;
                    packageChange2.mPackageName = typedXmlPullParser.getAttributeValue((java.lang.String) null, "pkg");
                    dailyItem.mPackageChanges.add(packageChange2);
                    com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                } else {
                    android.util.Slog.w(TAG, "Unknown element under <item>: " + typedXmlPullParser.getName());
                    com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            }
        }
        this.mDailyItems.add(dailyItem);
    }

    void readDailyItemTagDetailsLocked(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, android.os.BatteryStats.DailyItem dailyItem, boolean z, java.lang.String str) throws java.lang.NumberFormatException, org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        java.lang.String attributeValue;
        int attributeInt = typedXmlPullParser.getAttributeInt((java.lang.String) null, "n", -1);
        if (attributeInt == -1) {
            android.util.Slog.w(TAG, "Missing 'n' attribute at " + typedXmlPullParser.getPositionDescription());
            com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
            return;
        }
        android.os.BatteryStats.LevelStepTracker levelStepTracker = new android.os.BatteryStats.LevelStepTracker(attributeInt);
        if (z) {
            dailyItem.mChargeSteps = levelStepTracker;
        } else {
            dailyItem.mDischargeSteps = levelStepTracker;
        }
        int depth = typedXmlPullParser.getDepth();
        int i = 0;
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                if ("s".equals(typedXmlPullParser.getName())) {
                    if (i < attributeInt && (attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, "v")) != null) {
                        levelStepTracker.decodeEntryAt(i, attributeValue);
                        i++;
                    }
                } else {
                    android.util.Slog.w(TAG, "Unknown element under <" + str + ">: " + typedXmlPullParser.getName());
                    com.android.internal.util.XmlUtils.skipCurrentTag(typedXmlPullParser);
                }
            }
        }
        levelStepTracker.mNumStepDurations = i;
    }

    public android.os.BatteryStats.DailyItem getDailyItemLocked(int i) {
        int size = (this.mDailyItems.size() - 1) - i;
        if (size >= 0) {
            return this.mDailyItems.get(size);
        }
        return null;
    }

    public long getCurrentDailyStartTime() {
        return this.mDailyStartTimeMs;
    }

    public long getNextMinDailyDeadline() {
        return this.mNextMinDailyDeadlineMs;
    }

    public long getNextMaxDailyDeadline() {
        return this.mNextMaxDailyDeadlineMs;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public int getHistoryTotalSize() {
        return this.mConstants.MAX_HISTORY_BUFFER * this.mConstants.MAX_HISTORY_FILES;
    }

    public int getHistoryUsedSize() {
        return this.mHistory.getHistoryUsedSize();
    }

    public com.android.internal.os.BatteryStatsHistoryIterator iterateBatteryStatsHistory(long j, long j2) {
        return this.mHistory.iterate(j, j2);
    }

    public int getHistoryStringPoolSize() {
        return this.mHistory.getHistoryStringPoolSize();
    }

    public int getHistoryStringPoolBytes() {
        return this.mHistory.getHistoryStringPoolBytes();
    }

    public java.lang.String getHistoryTagPoolString(int i) {
        return this.mHistory.getHistoryTagPoolString(i);
    }

    public int getHistoryTagPoolUid(int i) {
        return this.mHistory.getHistoryTagPoolUid(i);
    }

    public int getStartCount() {
        return this.mStartCount;
    }

    public boolean isOnBattery() {
        return this.mOnBattery;
    }

    public boolean isCharging() {
        return this.mCharging;
    }

    void initTimes(long j, long j2) {
        this.mStartClockTimeMs = this.mClock.currentTimeMillis();
        this.mOnBatteryTimeBase.init(j, j2);
        this.mOnBatteryScreenOffTimeBase.init(j, j2);
        this.mRealtimeUs = 0L;
        this.mUptimeUs = 0L;
        this.mRealtimeStartUs = j2;
        this.mUptimeStartUs = j;
        this.mMonotonicStartTime = this.mMonotonicClock.monotonicTime();
    }

    void initDischarge(long j) {
        this.mLowDischargeAmountSinceCharge = 0;
        this.mHighDischargeAmountSinceCharge = 0;
        this.mDischargeAmountScreenOn = 0;
        this.mDischargeAmountScreenOnSinceCharge = 0;
        this.mDischargeAmountScreenOff = 0;
        this.mDischargeAmountScreenOffSinceCharge = 0;
        this.mDischargeAmountScreenDoze = 0;
        this.mDischargeAmountScreenDozeSinceCharge = 0;
        this.mDischargeStepTracker.init();
        this.mChargeStepTracker.init();
        this.mDischargeScreenOffCounter.reset(false, j);
        this.mDischargeScreenDozeCounter.reset(false, j);
        this.mDischargeLightDozeCounter.reset(false, j);
        this.mDischargeDeepDozeCounter.reset(false, j);
        this.mDischargeCounter.reset(false, j);
    }

    public void saveBatteryUsageStatsOnReset(@android.annotation.NonNull com.android.server.power.stats.BatteryUsageStatsProvider batteryUsageStatsProvider, @android.annotation.NonNull com.android.server.power.stats.PowerStatsStore powerStatsStore) {
        this.mSaveBatteryUsageStatsOnReset = true;
        this.mBatteryUsageStatsProvider = batteryUsageStatsProvider;
        this.mPowerStatsStore = powerStatsStore;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void resetAllStatsAndHistoryLocked(int i) {
        long uptimeMillis = this.mClock.uptimeMillis();
        long j = uptimeMillis * 1000;
        long elapsedRealtime = this.mClock.elapsedRealtime();
        long j2 = elapsedRealtime * 1000;
        resetAllStatsLocked(uptimeMillis, elapsedRealtime, i);
        pullPendingStateUpdatesLocked();
        this.mHistory.writeHistoryItem(elapsedRealtime, uptimeMillis);
        int i2 = this.mBatteryLevel;
        this.mDischargePlugLevel = i2;
        this.mDischargeUnplugLevel = i2;
        this.mDischargeCurrentLevel = i2;
        this.mOnBatteryTimeBase.reset(j, j2);
        this.mOnBatteryScreenOffTimeBase.reset(j, j2);
        if (!this.mBatteryPluggedIn) {
            if (android.view.Display.isOnState(this.mScreenState)) {
                this.mDischargeScreenOnUnplugLevel = this.mBatteryLevel;
                this.mDischargeScreenDozeUnplugLevel = 0;
                this.mDischargeScreenOffUnplugLevel = 0;
            } else if (android.view.Display.isDozeState(this.mScreenState)) {
                this.mDischargeScreenOnUnplugLevel = 0;
                this.mDischargeScreenDozeUnplugLevel = this.mBatteryLevel;
                this.mDischargeScreenOffUnplugLevel = 0;
            } else {
                this.mDischargeScreenOnUnplugLevel = 0;
                this.mDischargeScreenDozeUnplugLevel = 0;
                this.mDischargeScreenOffUnplugLevel = this.mBatteryLevel;
            }
            this.mDischargeAmountScreenOn = 0;
            this.mDischargeAmountScreenOff = 0;
            this.mDischargeAmountScreenDoze = 0;
        }
        initActiveHistoryEventsLocked(elapsedRealtime, uptimeMillis);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void resetAllStatsLocked(long j, long j2, int i) {
        saveBatteryUsageStatsOnReset(i);
        long j3 = j * 1000;
        long j4 = 1000 * j2;
        this.mStartCount = 0;
        initTimes(j3, j4);
        this.mScreenOnTimer.reset(false, j4);
        this.mScreenDozeTimer.reset(false, j4);
        for (int i2 = 0; i2 < 5; i2++) {
            this.mScreenBrightnessTimer[i2].reset(false, j4);
        }
        int length = this.mPerDisplayBatteryStats.length;
        for (int i3 = 0; i3 < length; i3++) {
            this.mPerDisplayBatteryStats[i3].reset(j4);
        }
        if (this.mPowerProfile != null) {
            this.mEstimatedBatteryCapacityMah = (int) this.mPowerProfile.getBatteryCapacity();
        } else {
            this.mEstimatedBatteryCapacityMah = -1;
        }
        this.mLastLearnedBatteryCapacityUah = -1;
        this.mMinLearnedBatteryCapacityUah = -1;
        this.mMaxLearnedBatteryCapacityUah = -1;
        this.mInteractiveTimer.reset(false, j4);
        this.mPowerSaveModeEnabledTimer.reset(false, j4);
        this.mLastIdleTimeStartMs = j2;
        this.mLongestLightIdleTimeMs = 0L;
        this.mLongestFullIdleTimeMs = 0L;
        this.mDeviceIdleModeLightTimer.reset(false, j4);
        this.mDeviceIdleModeFullTimer.reset(false, j4);
        this.mDeviceLightIdlingTimer.reset(false, j4);
        this.mDeviceIdlingTimer.reset(false, j4);
        this.mPhoneOnTimer.reset(false, j4);
        this.mAudioOnTimer.reset(false, j4);
        this.mVideoOnTimer.reset(false, j4);
        this.mFlashlightOnTimer.reset(false, j4);
        this.mCameraOnTimer.reset(false, j4);
        this.mBluetoothScanTimer.reset(false, j4);
        for (int i4 = 0; i4 < CELL_SIGNAL_STRENGTH_LEVEL_COUNT; i4++) {
            this.mPhoneSignalStrengthsTimer[i4].reset(false, j4);
        }
        this.mPhoneSignalScanningTimer.reset(false, j4);
        for (int i5 = 0; i5 < android.os.BatteryStats.NUM_DATA_CONNECTION_TYPES; i5++) {
            this.mPhoneDataConnectionsTimer[i5].reset(false, j4);
        }
        this.mNrNsaTimer.reset(false, j4);
        for (int i6 = 0; i6 < 10; i6++) {
            this.mNetworkByteActivityCounters[i6].reset(false, j4);
            this.mNetworkPacketActivityCounters[i6].reset(false, j4);
        }
        for (int i7 = 0; i7 < 3; i7++) {
            com.android.server.power.stats.BatteryStatsImpl.RadioAccessTechnologyBatteryStats radioAccessTechnologyBatteryStats = this.mPerRatBatteryStats[i7];
            if (radioAccessTechnologyBatteryStats != null) {
                radioAccessTechnologyBatteryStats.reset(j4);
            }
        }
        this.mMobileRadioActiveTimer.reset(false, j4);
        this.mMobileRadioActivePerAppTimer.reset(false, j4);
        this.mMobileRadioActiveAdjustedTime.reset(false, j4);
        this.mMobileRadioActiveUnknownTime.reset(false, j4);
        this.mMobileRadioActiveUnknownCount.reset(false, j4);
        this.mWifiOnTimer.reset(false, j4);
        this.mGlobalWifiRunningTimer.reset(false, j4);
        for (int i8 = 0; i8 < 8; i8++) {
            this.mWifiStateTimer[i8].reset(false, j4);
        }
        for (int i9 = 0; i9 < 13; i9++) {
            this.mWifiSupplStateTimer[i9].reset(false, j4);
        }
        for (int i10 = 0; i10 < 5; i10++) {
            this.mWifiSignalStrengthsTimer[i10].reset(false, j4);
        }
        this.mWifiMulticastWakelockTimer.reset(false, j4);
        this.mWifiActiveTimer.reset(false, j4);
        this.mWifiActivity.reset(false, j4);
        for (int i11 = 0; i11 < this.mGpsSignalQualityTimer.length; i11++) {
            this.mGpsSignalQualityTimer[i11].reset(false, j4);
        }
        this.mBluetoothActivity.reset(false, j4);
        this.mModemActivity.reset(false, j4);
        this.mNumConnectivityChange = 0;
        int i12 = 0;
        while (i12 < this.mUidStats.size()) {
            if (this.mUidStats.valueAt(i12).reset(j3, j4, i)) {
                this.mUidStats.valueAt(i12).detachFromTimeBase();
                this.mUidStats.remove(this.mUidStats.keyAt(i12));
                i12--;
            }
            i12++;
        }
        if (this.mRpmStats.size() > 0) {
            java.util.Iterator<com.android.server.power.stats.BatteryStatsImpl.SamplingTimer> it = this.mRpmStats.values().iterator();
            while (it.hasNext()) {
                this.mOnBatteryTimeBase.remove(it.next());
            }
            this.mRpmStats.clear();
        }
        if (this.mScreenOffRpmStats.size() > 0) {
            java.util.Iterator<com.android.server.power.stats.BatteryStatsImpl.SamplingTimer> it2 = this.mScreenOffRpmStats.values().iterator();
            while (it2.hasNext()) {
                this.mOnBatteryScreenOffTimeBase.remove(it2.next());
            }
            this.mScreenOffRpmStats.clear();
        }
        if (this.mKernelWakelockStats.size() > 0) {
            java.util.Iterator<com.android.server.power.stats.BatteryStatsImpl.SamplingTimer> it3 = this.mKernelWakelockStats.values().iterator();
            while (it3.hasNext()) {
                this.mOnBatteryScreenOffTimeBase.remove(it3.next());
            }
            this.mKernelWakelockStats.clear();
        }
        if (this.mKernelMemoryStats.size() > 0) {
            for (int i13 = 0; i13 < this.mKernelMemoryStats.size(); i13++) {
                this.mOnBatteryTimeBase.remove(this.mKernelMemoryStats.valueAt(i13));
            }
            this.mKernelMemoryStats.clear();
        }
        if (this.mWakeupReasonStats.size() > 0) {
            java.util.Iterator<com.android.server.power.stats.BatteryStatsImpl.SamplingTimer> it4 = this.mWakeupReasonStats.values().iterator();
            while (it4.hasNext()) {
                this.mOnBatteryTimeBase.remove(it4.next());
            }
            this.mWakeupReasonStats.clear();
        }
        if (this.mTmpRailStats != null) {
            this.mTmpRailStats.reset();
        }
        com.android.internal.power.EnergyConsumerStats.resetIfNotNull(this.mGlobalEnergyConsumerStats);
        com.android.server.power.optimization.Flags.disableSystemServicePowerAttr();
        resetIfNotNull(this.mBinderThreadCpuTimesUs, false, j4);
        this.mNumAllUidCpuTimeReads = 0;
        this.mNumUidsRemoved = 0;
        initDischarge(j4);
        this.mHistory.reset();
        writeSyncLocked();
        if (this.mPowerStatsCollectorEnabled) {
            schedulePowerStatsSampleCollection();
        }
        this.mIgnoreNextExternalStats = true;
        this.mExternalSync.scheduleSync("reset", 255);
        this.mHandler.sendEmptyMessage(4);
    }

    private void saveBatteryUsageStatsOnReset(int i) {
        final android.os.BatteryUsageStats batteryUsageStats;
        if (!this.mSaveBatteryUsageStatsOnReset || i == 1) {
            return;
        }
        synchronized (this) {
            batteryUsageStats = this.mBatteryUsageStatsProvider.getBatteryUsageStats(this, new android.os.BatteryUsageStatsQuery.Builder().setMaxStatsAgeMs(0L).includePowerModels().includeProcessStateData().build());
        }
        final long monotonicTime = this.mMonotonicClock.monotonicTime() - batteryUsageStats.getStatsDuration();
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.power.stats.BatteryStatsImpl$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.power.stats.BatteryStatsImpl.this.lambda$saveBatteryUsageStatsOnReset$3(monotonicTime, batteryUsageStats);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveBatteryUsageStatsOnReset$3(long j, android.os.BatteryUsageStats batteryUsageStats) {
        this.mPowerStatsStore.storeBatteryUsageStats(j, batteryUsageStats);
        try {
            batteryUsageStats.close();
        } catch (java.io.IOException e) {
            android.util.Log.e(TAG, "Cannot close BatteryUsageStats", e);
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void initActiveHistoryEventsLocked(long j, long j2) {
        java.util.HashMap stateForEvent;
        for (int i = 0; i < 22; i++) {
            if ((this.mRecordAllHistory || i != 1) && (stateForEvent = this.mActiveEvents.getStateForEvent(i)) != null) {
                for (java.util.Map.Entry entry : stateForEvent.entrySet()) {
                    android.util.SparseIntArray sparseIntArray = (android.util.SparseIntArray) entry.getValue();
                    for (int i2 = 0; i2 < sparseIntArray.size(); i2++) {
                        this.mHistory.recordEvent(j, j2, i, (java.lang.String) entry.getKey(), sparseIntArray.keyAt(i2));
                    }
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    void updateDischargeScreenLevelsLocked(int i, int i2) {
        updateOldDischargeScreenLevelLocked(i);
        updateNewDischargeScreenLevelLocked(i2);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void updateOldDischargeScreenLevelLocked(int i) {
        int i2;
        if (android.view.Display.isOnState(i)) {
            int i3 = this.mDischargeScreenOnUnplugLevel - this.mDischargeCurrentLevel;
            if (i3 > 0) {
                this.mDischargeAmountScreenOn += i3;
                this.mDischargeAmountScreenOnSinceCharge += i3;
                return;
            }
            return;
        }
        if (android.view.Display.isDozeState(i)) {
            int i4 = this.mDischargeScreenDozeUnplugLevel - this.mDischargeCurrentLevel;
            if (i4 > 0) {
                this.mDischargeAmountScreenDoze += i4;
                this.mDischargeAmountScreenDozeSinceCharge += i4;
                return;
            }
            return;
        }
        if (android.view.Display.isOffState(i) && (i2 = this.mDischargeScreenOffUnplugLevel - this.mDischargeCurrentLevel) > 0) {
            this.mDischargeAmountScreenOff += i2;
            this.mDischargeAmountScreenOffSinceCharge += i2;
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void updateNewDischargeScreenLevelLocked(int i) {
        if (android.view.Display.isOnState(i)) {
            this.mDischargeScreenOnUnplugLevel = this.mDischargeCurrentLevel;
            this.mDischargeScreenOffUnplugLevel = 0;
            this.mDischargeScreenDozeUnplugLevel = 0;
        } else if (android.view.Display.isDozeState(i)) {
            this.mDischargeScreenOnUnplugLevel = 0;
            this.mDischargeScreenDozeUnplugLevel = this.mDischargeCurrentLevel;
            this.mDischargeScreenOffUnplugLevel = 0;
        } else if (android.view.Display.isOffState(i)) {
            this.mDischargeScreenOnUnplugLevel = 0;
            this.mDischargeScreenDozeUnplugLevel = 0;
            this.mDischargeScreenOffUnplugLevel = this.mDischargeCurrentLevel;
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void pullPendingStateUpdatesLocked() {
        if (this.mOnBatteryInternal) {
            updateDischargeScreenLevelsLocked(this.mScreenState, this.mScreenState);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    protected android.net.NetworkStats readMobileNetworkStatsLocked(@android.annotation.NonNull android.app.usage.NetworkStatsManager networkStatsManager) {
        return networkStatsManager.getMobileUidStats();
    }

    @com.android.internal.annotations.VisibleForTesting
    protected android.net.NetworkStats readWifiNetworkStatsLocked(@android.annotation.NonNull android.app.usage.NetworkStatsManager networkStatsManager) {
        return networkStatsManager.getWifiUidStats();
    }

    private static class NetworkStatsDelta {
        long mRxBytes;
        long mRxPackets;
        int mSet;
        long mTxBytes;
        long mTxPackets;
        int mUid;

        private NetworkStatsDelta() {
        }

        public int getUid() {
            return this.mUid;
        }

        public int getSet() {
            return this.mSet;
        }

        public long getRxBytes() {
            return this.mRxBytes;
        }

        public long getRxPackets() {
            return this.mRxPackets;
        }

        public long getTxBytes() {
            return this.mTxBytes;
        }

        public long getTxPackets() {
            return this.mTxPackets;
        }
    }

    private java.util.List<com.android.server.power.stats.BatteryStatsImpl.NetworkStatsDelta> computeDelta(android.net.NetworkStats networkStats, android.net.NetworkStats networkStats2) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator it = networkStats.iterator();
        while (it.hasNext()) {
            android.net.NetworkStats.Entry entry = (android.net.NetworkStats.Entry) it.next();
            android.net.NetworkStats.Entry entry2 = null;
            com.android.server.power.stats.BatteryStatsImpl.NetworkStatsDelta networkStatsDelta = new com.android.server.power.stats.BatteryStatsImpl.NetworkStatsDelta();
            networkStatsDelta.mUid = entry.getUid();
            networkStatsDelta.mSet = entry.getSet();
            if (networkStats2 != null) {
                java.util.Iterator it2 = networkStats2.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    android.net.NetworkStats.Entry entry3 = (android.net.NetworkStats.Entry) it2.next();
                    if (entry3.getUid() == entry.getUid() && entry3.getSet() == entry.getSet() && entry3.getTag() == entry.getTag() && entry3.getMetered() == entry.getMetered() && entry3.getRoaming() == entry.getRoaming() && entry3.getDefaultNetwork() == entry.getDefaultNetwork()) {
                        entry2 = entry3;
                        break;
                    }
                }
            }
            if (entry2 != null) {
                networkStatsDelta.mRxBytes = entry.getRxBytes() - entry2.getRxBytes();
                networkStatsDelta.mRxPackets = entry.getRxPackets() - entry2.getRxPackets();
                networkStatsDelta.mTxBytes = entry.getTxBytes() - entry2.getTxBytes();
                networkStatsDelta.mTxPackets = entry.getTxPackets() - entry2.getTxPackets();
            } else {
                networkStatsDelta.mRxBytes = entry.getRxBytes();
                networkStatsDelta.mRxPackets = entry.getRxPackets();
                networkStatsDelta.mTxBytes = entry.getTxBytes();
                networkStatsDelta.mTxPackets = entry.getTxPackets();
            }
            arrayList.add(networkStatsDelta);
        }
        return arrayList;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void updateWifiState(@android.annotation.Nullable android.os.connectivity.WifiActivityEnergyInfo wifiActivityEnergyInfo, long j, long j2, long j3, @android.annotation.NonNull android.app.usage.NetworkStatsManager networkStatsManager) {
        java.util.List<com.android.server.power.stats.BatteryStatsImpl.NetworkStatsDelta> list;
        android.util.SparseLongArray sparseLongArray;
        android.util.SparseLongArray sparseLongArray2;
        long j4;
        long j5;
        long j6;
        double d;
        long j7;
        long j8;
        android.util.SparseLongArray sparseLongArray3;
        int i;
        android.util.SparseLongArray sparseLongArray4;
        long j9;
        long j10;
        long j11;
        int i2;
        com.android.server.power.stats.BatteryStatsImpl batteryStatsImpl = this;
        synchronized (batteryStatsImpl.mWifiNetworkLock) {
            try {
                android.net.NetworkStats readWifiNetworkStatsLocked = batteryStatsImpl.readWifiNetworkStatsLocked(networkStatsManager);
                if (readWifiNetworkStatsLocked != null) {
                    list = batteryStatsImpl.computeDelta(readWifiNetworkStatsLocked, batteryStatsImpl.mLastWifiNetworkStats);
                    batteryStatsImpl.mLastWifiNetworkStats = readWifiNetworkStatsLocked;
                } else {
                    list = null;
                }
            } finally {
            }
        }
        synchronized (this) {
            try {
                if (!batteryStatsImpl.mOnBatteryInternal || batteryStatsImpl.mIgnoreNextExternalStats) {
                    return;
                }
                long j12 = 0;
                android.util.SparseDoubleArray sparseDoubleArray = (batteryStatsImpl.mGlobalEnergyConsumerStats == null || batteryStatsImpl.mWifiPowerCalculator == null || j <= 0) ? null : new android.util.SparseDoubleArray();
                android.util.SparseLongArray sparseLongArray5 = new android.util.SparseLongArray();
                android.util.SparseLongArray sparseLongArray6 = new android.util.SparseLongArray();
                android.util.SparseLongArray sparseLongArray7 = new android.util.SparseLongArray();
                android.util.SparseLongArray sparseLongArray8 = new android.util.SparseLongArray();
                if (list != null) {
                    j4 = 0;
                    j5 = 0;
                    for (com.android.server.power.stats.BatteryStatsImpl.NetworkStatsDelta networkStatsDelta : list) {
                        if (networkStatsDelta.getRxBytes() != j12 || networkStatsDelta.getTxBytes() != j12) {
                            int mapUid = batteryStatsImpl.mapUid(networkStatsDelta.getUid());
                            android.util.SparseLongArray sparseLongArray9 = sparseLongArray7;
                            android.util.SparseLongArray sparseLongArray10 = sparseLongArray8;
                            com.android.server.power.stats.BatteryStatsImpl.Uid uidStatsLocked = getUidStatsLocked(mapUid, j2, j3);
                            if (networkStatsDelta.getRxBytes() != j12) {
                                uidStatsLocked.noteNetworkActivityLocked(2, networkStatsDelta.getRxBytes(), networkStatsDelta.getRxPackets());
                                if (networkStatsDelta.getSet() == 0) {
                                    uidStatsLocked.noteNetworkActivityLocked(8, networkStatsDelta.getRxBytes(), networkStatsDelta.getRxPackets());
                                }
                                batteryStatsImpl.mNetworkByteActivityCounters[2].addCountLocked(networkStatsDelta.getRxBytes());
                                batteryStatsImpl.mNetworkPacketActivityCounters[2].addCountLocked(networkStatsDelta.getRxPackets());
                                i2 = mapUid;
                                sparseLongArray5.incrementValue(i2, networkStatsDelta.getRxPackets());
                                j5 += networkStatsDelta.getRxPackets();
                            } else {
                                i2 = mapUid;
                            }
                            if (networkStatsDelta.getTxBytes() != j12) {
                                uidStatsLocked.noteNetworkActivityLocked(3, networkStatsDelta.getTxBytes(), networkStatsDelta.getTxPackets());
                                if (networkStatsDelta.getSet() == 0) {
                                    uidStatsLocked.noteNetworkActivityLocked(9, networkStatsDelta.getTxBytes(), networkStatsDelta.getTxPackets());
                                }
                                batteryStatsImpl.mNetworkByteActivityCounters[3].addCountLocked(networkStatsDelta.getTxBytes());
                                batteryStatsImpl.mNetworkPacketActivityCounters[3].addCountLocked(networkStatsDelta.getTxPackets());
                                sparseLongArray6.incrementValue(i2, networkStatsDelta.getTxPackets());
                                j4 += networkStatsDelta.getTxPackets();
                            }
                            if (sparseDoubleArray != null && wifiActivityEnergyInfo == null && !batteryStatsImpl.mHasWifiReporting) {
                                long j13 = j2 * 1000;
                                long timeSinceMarkLocked = uidStatsLocked.mWifiRunningTimer.getTimeSinceMarkLocked(j13) / 1000;
                                if (timeSinceMarkLocked > j12) {
                                    uidStatsLocked.mWifiRunningTimer.setMark(j2);
                                }
                                long timeSinceMarkLocked2 = uidStatsLocked.mWifiScanTimer.getTimeSinceMarkLocked(j13) / 1000;
                                if (timeSinceMarkLocked2 > j12) {
                                    uidStatsLocked.mWifiScanTimer.setMark(j2);
                                }
                                long j14 = j12;
                                int i3 = 0;
                                while (i3 < 5) {
                                    if (uidStatsLocked.mWifiBatchedScanTimer[i3] != null) {
                                        long timeSinceMarkLocked3 = uidStatsLocked.mWifiBatchedScanTimer[i3].getTimeSinceMarkLocked(j13) / 1000;
                                        if (timeSinceMarkLocked3 > j12) {
                                            uidStatsLocked.mWifiBatchedScanTimer[i3].setMark(j2);
                                        }
                                        j14 += timeSinceMarkLocked3;
                                    }
                                    i3++;
                                    j12 = 0;
                                }
                                sparseDoubleArray.incrementValue(uidStatsLocked.getUid(), batteryStatsImpl.mWifiPowerCalculator.calcPowerWithoutControllerDataMah(networkStatsDelta.getRxPackets(), networkStatsDelta.getTxPackets(), timeSinceMarkLocked, timeSinceMarkLocked2, j14));
                            }
                            sparseLongArray7 = sparseLongArray9;
                            sparseLongArray8 = sparseLongArray10;
                            j12 = 0;
                        }
                    }
                    sparseLongArray = sparseLongArray7;
                    sparseLongArray2 = sparseLongArray8;
                } else {
                    sparseLongArray = sparseLongArray7;
                    sparseLongArray2 = sparseLongArray8;
                    j4 = 0;
                    j5 = 0;
                }
                double d2 = 0.0d;
                if (wifiActivityEnergyInfo != null) {
                    batteryStatsImpl.mHasWifiReporting = true;
                    long controllerTxDurationMillis = wifiActivityEnergyInfo.getControllerTxDurationMillis();
                    long controllerRxDurationMillis = wifiActivityEnergyInfo.getControllerRxDurationMillis();
                    wifiActivityEnergyInfo.getControllerScanDurationMillis();
                    long controllerIdleDurationMillis = wifiActivityEnergyInfo.getControllerIdleDurationMillis();
                    int size = batteryStatsImpl.mUidStats.size();
                    long j15 = 0;
                    long j16 = 0;
                    for (int i4 = 0; i4 < size; i4++) {
                        com.android.server.power.stats.BatteryStatsImpl.Uid valueAt = batteryStatsImpl.mUidStats.valueAt(i4);
                        long j17 = j2 * 1000;
                        j15 += valueAt.mWifiScanTimer.getTimeSinceMarkLocked(j17) / 1000;
                        j16 += valueAt.mFullWifiLockTimer.getTimeSinceMarkLocked(j17) / 1000;
                    }
                    int i5 = 0;
                    long j18 = controllerRxDurationMillis;
                    long j19 = controllerTxDurationMillis;
                    while (i5 < size) {
                        int i6 = size;
                        com.android.server.power.stats.BatteryStatsImpl.Uid valueAt2 = batteryStatsImpl.mUidStats.valueAt(i5);
                        android.util.SparseLongArray sparseLongArray11 = sparseLongArray5;
                        long j20 = j16;
                        long j21 = j2 * 1000;
                        long timeSinceMarkLocked4 = valueAt2.mWifiScanTimer.getTimeSinceMarkLocked(j21) / 1000;
                        if (timeSinceMarkLocked4 > 0) {
                            try {
                                valueAt2.mWifiScanTimer.setMark(j2);
                                long j22 = j15 > controllerRxDurationMillis ? (controllerRxDurationMillis * timeSinceMarkLocked4) / j15 : timeSinceMarkLocked4;
                                if (j15 > controllerTxDurationMillis) {
                                    j7 = j15;
                                    j8 = (timeSinceMarkLocked4 * controllerTxDurationMillis) / j15;
                                } else {
                                    j7 = j15;
                                    j8 = timeSinceMarkLocked4;
                                }
                                sparseLongArray3 = sparseLongArray6;
                                i = i5;
                                android.util.SparseLongArray sparseLongArray12 = sparseLongArray;
                                sparseLongArray12.incrementValue(valueAt2.getUid(), j22);
                                sparseLongArray = sparseLongArray12;
                                sparseLongArray4 = sparseLongArray2;
                                sparseLongArray4.incrementValue(valueAt2.getUid(), j8);
                                j18 -= j22;
                                j19 -= j8;
                                j9 = j8;
                                j10 = j22;
                            } catch (java.lang.Throwable th) {
                                th = th;
                                throw th;
                            }
                        } else {
                            i = i5;
                            j7 = j15;
                            sparseLongArray3 = sparseLongArray6;
                            sparseLongArray4 = sparseLongArray2;
                            j10 = timeSinceMarkLocked4;
                            j9 = j10;
                        }
                        long timeSinceMarkLocked5 = valueAt2.mFullWifiLockTimer.getTimeSinceMarkLocked(j21) / 1000;
                        if (timeSinceMarkLocked5 > 0) {
                            valueAt2.mFullWifiLockTimer.setMark(j2);
                            long j23 = (timeSinceMarkLocked5 * controllerIdleDurationMillis) / j20;
                            valueAt2.getOrCreateWifiControllerActivityLocked().getOrCreateIdleTimeCounter().increment(j23, j2);
                            j11 = j23;
                        } else {
                            j11 = 0;
                        }
                        if (sparseDoubleArray != null) {
                            batteryStatsImpl = this;
                            sparseDoubleArray.incrementValue(valueAt2.getUid(), batteryStatsImpl.mWifiPowerCalculator.calcPowerFromControllerDataMah(j10, j9, j11));
                        } else {
                            batteryStatsImpl = this;
                        }
                        sparseLongArray2 = sparseLongArray4;
                        sparseLongArray5 = sparseLongArray11;
                        sparseLongArray6 = sparseLongArray3;
                        j16 = j20;
                        j15 = j7;
                        i5 = i + 1;
                        size = i6;
                    }
                    android.util.SparseLongArray sparseLongArray13 = sparseLongArray5;
                    android.util.SparseLongArray sparseLongArray14 = sparseLongArray6;
                    android.util.SparseLongArray sparseLongArray15 = sparseLongArray2;
                    int i7 = 0;
                    while (i7 < sparseLongArray14.size()) {
                        android.util.SparseLongArray sparseLongArray16 = sparseLongArray14;
                        sparseLongArray15.incrementValue(sparseLongArray16.keyAt(i7), (sparseLongArray16.valueAt(i7) * j19) / j4);
                        i7++;
                        sparseLongArray14 = sparseLongArray16;
                    }
                    int i8 = 0;
                    while (i8 < sparseLongArray13.size()) {
                        android.util.SparseLongArray sparseLongArray17 = sparseLongArray13;
                        android.util.SparseLongArray sparseLongArray18 = sparseLongArray;
                        sparseLongArray18.incrementValue(sparseLongArray17.keyAt(i8), (sparseLongArray17.valueAt(i8) * j18) / j5);
                        i8++;
                        sparseLongArray13 = sparseLongArray17;
                        sparseLongArray = sparseLongArray18;
                    }
                    android.util.SparseLongArray sparseLongArray19 = sparseLongArray;
                    int i9 = 0;
                    while (i9 < sparseLongArray15.size()) {
                        int keyAt = sparseLongArray15.keyAt(i9);
                        long valueAt3 = sparseLongArray15.valueAt(i9);
                        android.util.SparseLongArray sparseLongArray20 = sparseLongArray15;
                        getUidStatsLocked(keyAt, j2, j3).getOrCreateWifiControllerActivityLocked().getOrCreateTxTimeCounters()[0].increment(valueAt3, j2);
                        if (sparseDoubleArray != null) {
                            sparseDoubleArray.incrementValue(keyAt, batteryStatsImpl.mWifiPowerCalculator.calcPowerFromControllerDataMah(0L, valueAt3, 0L));
                        }
                        i9++;
                        sparseLongArray15 = sparseLongArray20;
                    }
                    for (int i10 = 0; i10 < sparseLongArray19.size(); i10++) {
                        int keyAt2 = sparseLongArray19.keyAt(i10);
                        long valueAt4 = sparseLongArray19.valueAt(i10);
                        getUidStatsLocked(sparseLongArray19.keyAt(i10), j2, j3).getOrCreateWifiControllerActivityLocked().getOrCreateRxTimeCounter().increment(valueAt4, j2);
                        if (sparseDoubleArray != null) {
                            sparseDoubleArray.incrementValue(keyAt2, batteryStatsImpl.mWifiPowerCalculator.calcPowerFromControllerDataMah(valueAt4, 0L, 0L));
                        }
                    }
                    batteryStatsImpl.mWifiActivity.getOrCreateRxTimeCounter().increment(wifiActivityEnergyInfo.getControllerRxDurationMillis(), j2);
                    batteryStatsImpl.mWifiActivity.getOrCreateTxTimeCounters()[0].increment(wifiActivityEnergyInfo.getControllerTxDurationMillis(), j2);
                    batteryStatsImpl.mWifiActivity.getScanTimeCounter().addCountLocked(wifiActivityEnergyInfo.getControllerScanDurationMillis());
                    batteryStatsImpl.mWifiActivity.getOrCreateIdleTimeCounter().increment(wifiActivityEnergyInfo.getControllerIdleDurationMillis(), j2);
                    double averagePower = batteryStatsImpl.mPowerProfile.getAveragePower("wifi.controller.voltage") / 1000.0d;
                    if (averagePower != 0.0d) {
                        d = wifiActivityEnergyInfo.getControllerEnergyUsedMicroJoules() / averagePower;
                        batteryStatsImpl.mWifiActivity.getPowerCounter().addCountLocked((long) d);
                    } else {
                        d = 0.0d;
                    }
                    long wifiTotalEnergyUseduWs = batteryStatsImpl.mTmpRailStats != null ? (long) (batteryStatsImpl.mTmpRailStats.getWifiTotalEnergyUseduWs() / averagePower) : 0L;
                    batteryStatsImpl.mWifiActivity.getMonitoredRailChargeConsumedMaMs().addCountLocked(wifiTotalEnergyUseduWs);
                    j6 = j2;
                    batteryStatsImpl.mHistory.recordWifiConsumedCharge(j2, j3, wifiTotalEnergyUseduWs / MILLISECONDS_IN_HOUR);
                    if (batteryStatsImpl.mTmpRailStats != null) {
                        batteryStatsImpl.mTmpRailStats.resetWifiTotalEnergyUsed();
                    }
                    if (sparseDoubleArray != null) {
                        d2 = java.lang.Math.max(d / MILLISECONDS_IN_HOUR, batteryStatsImpl.mWifiPowerCalculator.calcPowerFromControllerDataMah(controllerRxDurationMillis, controllerTxDurationMillis, controllerIdleDurationMillis));
                    }
                } else {
                    j6 = j2;
                }
                if (sparseDoubleArray != null) {
                    batteryStatsImpl.mGlobalEnergyConsumerStats.updateStandardBucket(4, j);
                    if (!batteryStatsImpl.mHasWifiReporting) {
                        long timeSinceMarkLocked6 = batteryStatsImpl.mGlobalWifiRunningTimer.getTimeSinceMarkLocked(j6 * 1000) / 1000;
                        batteryStatsImpl.mGlobalWifiRunningTimer.setMark(j6);
                        d2 = batteryStatsImpl.mWifiPowerCalculator.calcGlobalPowerWithoutControllerDataMah(timeSinceMarkLocked6);
                    }
                    distributeEnergyToUidsLocked(4, j, sparseDoubleArray, d2, j2);
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
            }
        }
    }

    public void noteModemControllerActivity(@android.annotation.Nullable android.telephony.ModemActivityInfo modemActivityInfo, long j, long j2, long j3, @android.annotation.NonNull android.app.usage.NetworkStatsManager networkStatsManager) {
        android.telephony.ModemActivityInfo delta;
        android.net.NetworkStats networkStats;
        long j4;
        android.util.SparseDoubleArray sparseDoubleArray;
        long j5;
        android.net.NetworkStats networkStats2;
        android.util.SparseDoubleArray sparseDoubleArray2;
        long j6;
        boolean z;
        com.android.server.power.stats.BatteryStatsImpl.RxTxConsumption rxTxConsumption;
        long j7;
        com.android.server.power.stats.BatteryStatsImpl.RxTxConsumption rxTxConsumption2;
        long j8;
        android.util.SparseDoubleArray sparseDoubleArray3;
        double calcScanTimePowerMah;
        long j9;
        com.android.server.power.stats.BatteryStatsImpl.Uid uid;
        long j10;
        com.android.server.power.stats.BatteryStatsImpl.RxTxConsumption rxTxConsumption3;
        android.util.SparseDoubleArray sparseDoubleArray4;
        double calcPowerFromRadioActiveDurationMah;
        long j11;
        android.net.NetworkStats networkStats3;
        if (this.mLastModemActivityInfo == null) {
            delta = modemActivityInfo == null ? null : modemActivityInfo.getDelta(modemActivityInfo);
        } else {
            delta = this.mLastModemActivityInfo.getDelta(modemActivityInfo);
        }
        this.mLastModemActivityInfo = modemActivityInfo;
        addModemTxPowerToHistory(delta, j2, j3);
        synchronized (this.mModemNetworkLock) {
            try {
                android.net.NetworkStats readMobileNetworkStatsLocked = readMobileNetworkStatsLocked(networkStatsManager);
                if (readMobileNetworkStatsLocked == null) {
                    networkStats = null;
                } else {
                    if (this.mLastModemNetworkStats != null) {
                        networkStats3 = this.mLastModemNetworkStats;
                    } else {
                        networkStats3 = new android.net.NetworkStats(0L, -1);
                    }
                    android.net.NetworkStats subtract = readMobileNetworkStatsLocked.subtract(networkStats3);
                    this.mLastModemNetworkStats = readMobileNetworkStatsLocked;
                    networkStats = subtract;
                }
            } finally {
            }
        }
        synchronized (this) {
            try {
                long j12 = j2 * 1000;
                long timeSinceMarkLocked = this.mMobileRadioActiveTimer.getTimeSinceMarkLocked(j12) / 1000;
                this.mMobileRadioActiveTimer.setMark(j2);
                long min = java.lang.Math.min(timeSinceMarkLocked, this.mPhoneOnTimer.getTimeSinceMarkLocked(j12) / 1000);
                this.mPhoneOnTimer.setMark(j2);
                if (!this.mOnBatteryInternal || this.mIgnoreNextExternalStats) {
                    return;
                }
                if (j > 0 && isMobileRadioEnergyConsumerSupportedLocked()) {
                    if (timeSinceMarkLocked == 0) {
                        j11 = 0;
                    } else {
                        j11 = ((min * j) + (timeSinceMarkLocked / 2)) / timeSinceMarkLocked;
                    }
                    long j13 = j - j11;
                    this.mGlobalEnergyConsumerStats.updateStandardBucket(9, j11);
                    this.mGlobalEnergyConsumerStats.updateStandardBucket(7, j13);
                    j4 = j13;
                    sparseDoubleArray = new android.util.SparseDoubleArray();
                } else {
                    j4 = -1;
                    sparseDoubleArray = null;
                }
                if (delta == null) {
                    j5 = timeSinceMarkLocked;
                    networkStats2 = networkStats;
                    sparseDoubleArray2 = sparseDoubleArray;
                    j6 = j12;
                    z = false;
                    rxTxConsumption = null;
                } else {
                    this.mHasModemReporting = true;
                    this.mModemActivity.getOrCreateIdleTimeCounter().increment(delta.getIdleTimeMillis(), j2);
                    this.mModemActivity.getSleepTimeCounter().addCountLocked(delta.getSleepTimeMillis());
                    this.mModemActivity.getOrCreateRxTimeCounter().increment(delta.getReceiveTimeMillis(), j2);
                    int i = 0;
                    while (i < MODEM_TX_POWER_LEVEL_COUNT) {
                        this.mModemActivity.getOrCreateTxTimeCounters()[i].increment(delta.getTransmitDurationMillisAtPowerLevel(i), j2);
                        i++;
                        networkStats = networkStats;
                    }
                    networkStats2 = networkStats;
                    double averagePower = this.mPowerProfile.getAveragePower("modem.controller.voltage") / 1000.0d;
                    if (averagePower == 0.0d) {
                        j5 = timeSinceMarkLocked;
                        sparseDoubleArray2 = sparseDoubleArray;
                        j6 = j12;
                    } else {
                        double sleepTimeMillis = (delta.getSleepTimeMillis() * this.mPowerProfile.getAveragePower("modem.controller.sleep")) + (delta.getIdleTimeMillis() * this.mPowerProfile.getAveragePower("modem.controller.idle")) + (delta.getReceiveTimeMillis() * this.mPowerProfile.getAveragePower("modem.controller.rx"));
                        for (int i2 = 0; i2 < java.lang.Math.min(MODEM_TX_POWER_LEVEL_COUNT, CELL_SIGNAL_STRENGTH_LEVEL_COUNT); i2++) {
                            sleepTimeMillis += delta.getTransmitDurationMillisAtPowerLevel(i2) * this.mPowerProfile.getAveragePower("modem.controller.tx", i2);
                        }
                        this.mModemActivity.getPowerCounter().addCountLocked((long) sleepTimeMillis);
                        long cellularTotalEnergyUseduWs = (long) (this.mTmpRailStats.getCellularTotalEnergyUseduWs() / averagePower);
                        this.mModemActivity.getMonitoredRailChargeConsumedMaMs().addCountLocked(cellularTotalEnergyUseduWs);
                        j5 = timeSinceMarkLocked;
                        sparseDoubleArray2 = sparseDoubleArray;
                        j6 = j12;
                        this.mHistory.recordWifiConsumedCharge(j2, j3, cellularTotalEnergyUseduWs / MILLISECONDS_IN_HOUR);
                        this.mTmpRailStats.resetCellularTotalEnergyUsed();
                    }
                    com.android.server.power.stats.BatteryStatsImpl.RxTxConsumption incrementPerRatDataLocked = incrementPerRatDataLocked(delta, j2);
                    z = this.mConstants.PER_UID_MODEM_MODEL == 2 && incrementPerRatDataLocked != null;
                    rxTxConsumption = incrementPerRatDataLocked;
                }
                long timeSinceMarkLocked2 = this.mMobileRadioActivePerAppTimer.getTimeSinceMarkLocked(j6);
                this.mMobileRadioActivePerAppTimer.setMark(j2);
                if (networkStats2 != null) {
                    java.util.Iterator it = networkStats2.iterator();
                    long j14 = 0;
                    long j15 = 0;
                    while (it.hasNext()) {
                        android.net.NetworkStats.Entry entry = (android.net.NetworkStats.Entry) it.next();
                        if (entry.getRxPackets() != 0 || entry.getTxPackets() != 0) {
                            j14 += entry.getRxPackets();
                            j15 += entry.getTxPackets();
                            com.android.server.power.stats.BatteryStatsImpl.Uid uidStatsLocked = getUidStatsLocked(mapUid(entry.getUid()), j2, j3);
                            uidStatsLocked.noteNetworkActivityLocked(0, entry.getRxBytes(), entry.getRxPackets());
                            uidStatsLocked.noteNetworkActivityLocked(1, entry.getTxBytes(), entry.getTxPackets());
                            if (entry.getSet() == 0) {
                                uidStatsLocked.noteNetworkActivityLocked(6, entry.getRxBytes(), entry.getRxPackets());
                                uidStatsLocked.noteNetworkActivityLocked(7, entry.getTxBytes(), entry.getTxPackets());
                            }
                            this.mNetworkByteActivityCounters[0].addCountLocked(entry.getRxBytes());
                            this.mNetworkByteActivityCounters[1].addCountLocked(entry.getTxBytes());
                            this.mNetworkPacketActivityCounters[0].addCountLocked(entry.getRxPackets());
                            this.mNetworkPacketActivityCounters[1].addCountLocked(entry.getTxPackets());
                        }
                    }
                    long j16 = j14 + j15;
                    if (j16 <= 0) {
                        j7 = j6;
                        rxTxConsumption2 = rxTxConsumption;
                        j8 = 0;
                        sparseDoubleArray3 = sparseDoubleArray2;
                    } else {
                        java.util.Iterator it2 = networkStats2.iterator();
                        long j17 = j16;
                        long j18 = timeSinceMarkLocked2;
                        while (it2.hasNext()) {
                            android.net.NetworkStats.Entry entry2 = (android.net.NetworkStats.Entry) it2.next();
                            if (entry2.getRxPackets() != 0 || entry2.getTxPackets() != 0) {
                                com.android.server.power.stats.BatteryStatsImpl.Uid uidStatsLocked2 = getUidStatsLocked(mapUid(entry2.getUid()), j2, j3);
                                long rxPackets = entry2.getRxPackets() + entry2.getTxPackets();
                                long j19 = (j18 * rxPackets) / j17;
                                uidStatsLocked2.noteMobileRadioActiveTimeLocked(j19, j2);
                                if (sparseDoubleArray2 == null) {
                                    j9 = j19;
                                    uid = uidStatsLocked2;
                                    j10 = j6;
                                    rxTxConsumption3 = rxTxConsumption;
                                    sparseDoubleArray4 = sparseDoubleArray2;
                                } else {
                                    if (z) {
                                        com.android.server.power.stats.BatteryStatsImpl.RxTxConsumption rxTxConsumption4 = rxTxConsumption;
                                        j9 = j19;
                                        uid = uidStatsLocked2;
                                        j10 = j6;
                                        rxTxConsumption3 = rxTxConsumption;
                                        sparseDoubleArray4 = sparseDoubleArray2;
                                        calcPowerFromRadioActiveDurationMah = smearModemActivityInfoRxTxConsumptionMah(rxTxConsumption4, entry2.getRxPackets(), entry2.getTxPackets(), j14, j15);
                                    } else {
                                        j9 = j19;
                                        uid = uidStatsLocked2;
                                        j10 = j6;
                                        rxTxConsumption3 = rxTxConsumption;
                                        sparseDoubleArray4 = sparseDoubleArray2;
                                        calcPowerFromRadioActiveDurationMah = this.mMobileRadioPowerCalculator.calcPowerFromRadioActiveDurationMah(j9 / 1000);
                                    }
                                    sparseDoubleArray4.incrementValue(uid.getUid(), calcPowerFromRadioActiveDurationMah);
                                }
                                j18 -= j9;
                                j17 -= rxPackets;
                                if (delta != null) {
                                    com.android.server.power.stats.BatteryStatsImpl.ControllerActivityCounterImpl orCreateModemControllerActivityLocked = uid.getOrCreateModemControllerActivityLocked();
                                    if (j14 > 0 && entry2.getRxPackets() > 0) {
                                        orCreateModemControllerActivityLocked.getOrCreateRxTimeCounter().increment((entry2.getRxPackets() * delta.getReceiveTimeMillis()) / j14, j2);
                                    }
                                    if (j15 > 0 && entry2.getTxPackets() > 0) {
                                        for (int i3 = 0; i3 < MODEM_TX_POWER_LEVEL_COUNT; i3++) {
                                            orCreateModemControllerActivityLocked.getOrCreateTxTimeCounters()[i3].increment((entry2.getTxPackets() * delta.getTransmitDurationMillisAtPowerLevel(i3)) / j15, j2);
                                        }
                                    }
                                }
                                sparseDoubleArray2 = sparseDoubleArray4;
                                j6 = j10;
                                rxTxConsumption = rxTxConsumption3;
                            }
                        }
                        j7 = j6;
                        rxTxConsumption2 = rxTxConsumption;
                        j8 = 0;
                        sparseDoubleArray3 = sparseDoubleArray2;
                        timeSinceMarkLocked2 = j18;
                    }
                    if (timeSinceMarkLocked2 > j8) {
                        this.mMobileRadioActiveUnknownTime.addCountLocked(timeSinceMarkLocked2);
                        this.mMobileRadioActiveUnknownCount.addCountLocked(1L);
                    }
                    if (sparseDoubleArray3 != null) {
                        if (!z) {
                            double calcPowerFromRadioActiveDurationMah2 = this.mMobileRadioPowerCalculator.calcPowerFromRadioActiveDurationMah(j5) + 0.0d;
                            int length = this.mPhoneSignalStrengthsTimer.length;
                            int i4 = 0;
                            while (i4 < length) {
                                long j20 = j7;
                                long timeSinceMarkLocked3 = this.mPhoneSignalStrengthsTimer[i4].getTimeSinceMarkLocked(j20) / 1000;
                                this.mPhoneSignalStrengthsTimer[i4].setMark(j2);
                                calcPowerFromRadioActiveDurationMah2 += this.mMobileRadioPowerCalculator.calcIdlePowerAtSignalStrengthMah(timeSinceMarkLocked3, i4);
                                i4++;
                                j7 = j20;
                            }
                            long timeSinceMarkLocked4 = this.mPhoneSignalScanningTimer.getTimeSinceMarkLocked(j7) / 1000;
                            this.mPhoneSignalScanningTimer.setMark(j2);
                            calcScanTimePowerMah = calcPowerFromRadioActiveDurationMah2 + this.mMobileRadioPowerCalculator.calcScanTimePowerMah(timeSinceMarkLocked4);
                        } else {
                            com.android.server.power.stats.BatteryStatsImpl.RxTxConsumption rxTxConsumption5 = rxTxConsumption2;
                            calcScanTimePowerMah = this.mMobileRadioPowerCalculator.calcInactiveStatePowerMah(delta.getSleepTimeMillis(), delta.getIdleTimeMillis()) + 0.0d + rxTxConsumption5.rxConsumptionMah + rxTxConsumption5.txConsumptionMah;
                        }
                        distributeEnergyToUidsLocked(7, j4, sparseDoubleArray3, calcScanTimePowerMah, j2);
                    }
                }
            } finally {
            }
        }
    }

    private static class RxTxConsumption {
        public final double rxConsumptionMah;
        public final long rxDurationMs;
        public final double txConsumptionMah;
        public final long txDurationMs;
        public final double txToTotalRatio;

        RxTxConsumption(double d, long j, double d2, long j2) {
            this.rxConsumptionMah = d;
            this.rxDurationMs = j;
            this.txConsumptionMah = d2;
            this.txDurationMs = j2;
            long j3 = this.txDurationMs + this.rxDurationMs;
            if (j3 == 0) {
                this.txToTotalRatio = 0.0d;
            } else {
                this.txToTotalRatio = this.txDurationMs / j3;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:63:0x019e  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01b1  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01b9 A[RETURN] */
    @com.android.internal.annotations.GuardedBy({"this"})
    @android.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private com.android.server.power.stats.BatteryStatsImpl.RxTxConsumption incrementPerRatDataLocked(android.telephony.ModemActivityInfo modemActivityInfo, long j) {
        long j2;
        double d;
        long j3;
        double d2;
        long j4;
        int i;
        long j5;
        double d3;
        double d4;
        int i2;
        long[] jArr;
        long j6;
        long[] jArr2;
        int i3;
        android.telephony.ModemActivityInfo modemActivityInfo2 = modemActivityInfo;
        int specificInfoLength = modemActivityInfo.getSpecificInfoLength();
        long j7 = 0;
        if (specificInfoLength != 1 || modemActivityInfo2.getSpecificInfoRat(0) != 0) {
            j2 = 0;
        } else if (modemActivityInfo2.getSpecificInfoFrequencyRange(0) != 0) {
            j2 = 0;
        } else {
            int i4 = CELL_SIGNAL_STRENGTH_LEVEL_COUNT;
            long[] jArr3 = new long[i4];
            long j8 = 0;
            for (int i5 = 0; i5 < 3; i5++) {
                com.android.server.power.stats.BatteryStatsImpl.RadioAccessTechnologyBatteryStats radioAccessTechnologyBatteryStats = this.mPerRatBatteryStats[i5];
                if (radioAccessTechnologyBatteryStats != null) {
                    int frequencyRangeCount = radioAccessTechnologyBatteryStats.getFrequencyRangeCount();
                    for (int i6 = 0; i6 < frequencyRangeCount; i6++) {
                        for (int i7 = 0; i7 < i4; i7++) {
                            long timeSinceMark = radioAccessTechnologyBatteryStats.getTimeSinceMark(i6, i7, j);
                            jArr3[i7] = jArr3[i7] + timeSinceMark;
                            j8 += timeSinceMark;
                        }
                    }
                }
            }
            if (j8 == 0) {
                j5 = 0;
                d3 = 0.0d;
                d4 = 0.0d;
            } else {
                long j9 = 0;
                long j10 = 0;
                int i8 = 0;
                d3 = 0.0d;
                d4 = 0.0d;
                for (int i9 = 3; i8 < i9; i9 = 3) {
                    com.android.server.power.stats.BatteryStatsImpl.RadioAccessTechnologyBatteryStats radioAccessTechnologyBatteryStats2 = this.mPerRatBatteryStats[i8];
                    if (radioAccessTechnologyBatteryStats2 == null) {
                        i2 = i4;
                        jArr = jArr3;
                        j6 = j7;
                    } else {
                        int frequencyRangeCount2 = radioAccessTechnologyBatteryStats2.getFrequencyRangeCount();
                        long j11 = j9;
                        int i10 = 0;
                        while (i10 < frequencyRangeCount2) {
                            long j12 = j7;
                            long j13 = j10;
                            int i11 = 0;
                            while (i11 < i4) {
                                long timeSinceMark2 = radioAccessTechnologyBatteryStats2.getTimeSinceMark(i10, i11, j);
                                long j14 = jArr3[i11];
                                if (j14 == 0) {
                                    i3 = i4;
                                    jArr2 = jArr3;
                                } else {
                                    int i12 = i4;
                                    jArr2 = jArr3;
                                    long transmitDurationMillisAtPowerLevel = ((modemActivityInfo2.getTransmitDurationMillisAtPowerLevel(i11) * timeSinceMark2) + (j14 / 2)) / j14;
                                    radioAccessTechnologyBatteryStats2.incrementTxDuration(i10, i11, transmitDurationMillisAtPowerLevel);
                                    long j15 = j12 + timeSinceMark2;
                                    if (!isMobileRadioEnergyConsumerSupportedLocked()) {
                                        i3 = i12;
                                        j12 = j15;
                                    } else {
                                        i3 = i12;
                                        d4 += this.mMobileRadioPowerCalculator.calcTxStatePowerMah(i8, i10, i11, transmitDurationMillisAtPowerLevel);
                                        j13 += transmitDurationMillisAtPowerLevel;
                                        j12 = j15;
                                    }
                                }
                                i11++;
                                i4 = i3;
                                jArr3 = jArr2;
                            }
                            int i13 = i4;
                            long[] jArr4 = jArr3;
                            long receiveTimeMillis = ((j12 * modemActivityInfo.getReceiveTimeMillis()) + (j8 / 2)) / j8;
                            radioAccessTechnologyBatteryStats2.incrementRxDuration(i10, receiveTimeMillis);
                            if (isMobileRadioEnergyConsumerSupportedLocked()) {
                                d3 += this.mMobileRadioPowerCalculator.calcRxStatePowerMah(i8, i10, receiveTimeMillis);
                                j11 += receiveTimeMillis;
                            }
                            i10++;
                            j10 = j13;
                            j7 = 0;
                            i4 = i13;
                            jArr3 = jArr4;
                        }
                        i2 = i4;
                        jArr = jArr3;
                        j6 = j7;
                        j9 = j11;
                    }
                    i8++;
                    j7 = j6;
                    i4 = i2;
                    jArr3 = jArr;
                }
                j7 = j9;
                j5 = j10;
            }
            d = d3;
            j3 = j7;
            d2 = d4;
            j4 = j5;
            for (i = 0; i < 3; i++) {
                com.android.server.power.stats.BatteryStatsImpl.RadioAccessTechnologyBatteryStats radioAccessTechnologyBatteryStats3 = this.mPerRatBatteryStats[i];
                if (radioAccessTechnologyBatteryStats3 != null) {
                    radioAccessTechnologyBatteryStats3.setMark(j);
                }
            }
            if (!isMobileRadioEnergyConsumerSupportedLocked()) {
                return new com.android.server.power.stats.BatteryStatsImpl.RxTxConsumption(d, j3, d2, j4);
            }
            return null;
        }
        long j16 = j2;
        int i14 = 0;
        double d5 = 0.0d;
        double d6 = 0.0d;
        while (i14 < specificInfoLength) {
            int specificInfoRat = modemActivityInfo2.getSpecificInfoRat(i14);
            int specificInfoFrequencyRange = modemActivityInfo2.getSpecificInfoFrequencyRange(i14);
            int mapRadioAccessNetworkTypeToRadioAccessTechnology = mapRadioAccessNetworkTypeToRadioAccessTechnology(specificInfoRat);
            com.android.server.power.stats.BatteryStatsImpl.RadioAccessTechnologyBatteryStats ratBatteryStatsLocked = getRatBatteryStatsLocked(mapRadioAccessNetworkTypeToRadioAccessTechnology);
            long receiveTimeMillis2 = modemActivityInfo2.getReceiveTimeMillis(specificInfoRat, specificInfoFrequencyRange);
            int[] transmitTimeMillis = modemActivityInfo2.getTransmitTimeMillis(specificInfoRat, specificInfoFrequencyRange);
            ratBatteryStatsLocked.incrementRxDuration(specificInfoFrequencyRange, receiveTimeMillis2);
            if (isMobileRadioEnergyConsumerSupportedLocked()) {
                d5 += this.mMobileRadioPowerCalculator.calcRxStatePowerMah(mapRadioAccessNetworkTypeToRadioAccessTechnology, specificInfoFrequencyRange, receiveTimeMillis2);
                j16 += receiveTimeMillis2;
            }
            int length = transmitTimeMillis.length;
            int i15 = 0;
            while (i15 < length) {
                double d7 = d5;
                long j17 = transmitTimeMillis[i15];
                ratBatteryStatsLocked.incrementTxDuration(specificInfoFrequencyRange, i15, j17);
                if (isMobileRadioEnergyConsumerSupportedLocked()) {
                    d6 += this.mMobileRadioPowerCalculator.calcTxStatePowerMah(mapRadioAccessNetworkTypeToRadioAccessTechnology, specificInfoFrequencyRange, i15, j17);
                    j2 += j17;
                }
                i15++;
                d5 = d7;
            }
            i14++;
            modemActivityInfo2 = modemActivityInfo;
        }
        d = d5;
        j3 = j16;
        d2 = d6;
        j4 = j2;
        while (i < 3) {
        }
        if (!isMobileRadioEnergyConsumerSupportedLocked()) {
        }
    }

    private double smearModemActivityInfoRxTxConsumptionMah(com.android.server.power.stats.BatteryStatsImpl.RxTxConsumption rxTxConsumption, long j, long j2, long j3, long j4) {
        double d;
        if (j3 == 0) {
            d = 0.0d;
        } else {
            d = ((rxTxConsumption.rxConsumptionMah * j) / j3) + 0.0d;
        }
        if (j4 != 0 || (j3 != 0 && rxTxConsumption.txToTotalRatio != 0.0d)) {
            return d + ((rxTxConsumption.txConsumptionMah * (j2 + (rxTxConsumption.txToTotalRatio * j))) / (j4 + (rxTxConsumption.txToTotalRatio * j3)));
        }
        return d;
    }

    private synchronized void addModemTxPowerToHistory(android.telephony.ModemActivityInfo modemActivityInfo, long j, long j2) {
        if (modemActivityInfo == null) {
            return;
        }
        int i = 0;
        for (int i2 = 1; i2 < MODEM_TX_POWER_LEVEL_COUNT; i2++) {
            try {
                if (modemActivityInfo.getTransmitDurationMillisAtPowerLevel(i2) > modemActivityInfo.getTransmitDurationMillisAtPowerLevel(i)) {
                    i = i2;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        if (i == MODEM_TX_POWER_LEVEL_COUNT - 1) {
            this.mHistory.recordState2StartEvent(j, j2, 524288);
        }
    }

    private final class BluetoothActivityInfoCache {
        long energy;
        long idleTimeMs;
        long rxTimeMs;
        long txTimeMs;
        android.util.SparseLongArray uidRxBytes;
        android.util.SparseLongArray uidTxBytes;

        private BluetoothActivityInfoCache() {
            this.uidRxBytes = new android.util.SparseLongArray();
            this.uidTxBytes = new android.util.SparseLongArray();
        }

        void set(android.bluetooth.BluetoothActivityEnergyInfo bluetoothActivityEnergyInfo) {
            this.idleTimeMs = bluetoothActivityEnergyInfo.getControllerIdleTimeMillis();
            this.rxTimeMs = bluetoothActivityEnergyInfo.getControllerRxTimeMillis();
            this.txTimeMs = bluetoothActivityEnergyInfo.getControllerTxTimeMillis();
            this.energy = bluetoothActivityEnergyInfo.getControllerEnergyUsed();
            if (!bluetoothActivityEnergyInfo.getUidTraffic().isEmpty()) {
                for (android.bluetooth.UidTraffic uidTraffic : bluetoothActivityEnergyInfo.getUidTraffic()) {
                    this.uidRxBytes.put(uidTraffic.getUid(), uidTraffic.getRxBytes());
                    this.uidTxBytes.put(uidTraffic.getUid(), uidTraffic.getTxBytes());
                }
            }
        }

        void reset() {
            this.idleTimeMs = 0L;
            this.rxTimeMs = 0L;
            this.txTimeMs = 0L;
            this.energy = 0L;
            this.uidRxBytes.clear();
            this.uidTxBytes.clear();
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void updateBluetoothStateLocked(@android.annotation.Nullable android.bluetooth.BluetoothActivityEnergyInfo bluetoothActivityEnergyInfo, long j, long j2, long j3) {
        double d;
        long j4;
        boolean z;
        int i;
        int i2;
        long j5;
        long j6;
        if (bluetoothActivityEnergyInfo == null) {
            return;
        }
        if (this.mOnBatteryInternal && !this.mIgnoreNextExternalStats) {
            this.mHasBluetoothReporting = true;
            if (bluetoothActivityEnergyInfo.getControllerRxTimeMillis() < this.mLastBluetoothActivityInfo.rxTimeMs || bluetoothActivityEnergyInfo.getControllerTxTimeMillis() < this.mLastBluetoothActivityInfo.txTimeMs || bluetoothActivityEnergyInfo.getControllerIdleTimeMillis() < this.mLastBluetoothActivityInfo.idleTimeMs || bluetoothActivityEnergyInfo.getControllerEnergyUsed() < this.mLastBluetoothActivityInfo.energy) {
                this.mLastBluetoothActivityInfo.reset();
            }
            long controllerRxTimeMillis = bluetoothActivityEnergyInfo.getControllerRxTimeMillis() - this.mLastBluetoothActivityInfo.rxTimeMs;
            long controllerTxTimeMillis = bluetoothActivityEnergyInfo.getControllerTxTimeMillis() - this.mLastBluetoothActivityInfo.txTimeMs;
            long controllerIdleTimeMillis = bluetoothActivityEnergyInfo.getControllerIdleTimeMillis() - this.mLastBluetoothActivityInfo.idleTimeMs;
            android.util.SparseDoubleArray sparseDoubleArray = (this.mGlobalEnergyConsumerStats == null || this.mBluetoothPowerCalculator == null || j <= 0) ? null : new android.util.SparseDoubleArray();
            int size = this.mUidStats.size();
            long j7 = 0;
            int i3 = 0;
            while (i3 < size) {
                com.android.server.power.stats.BatteryStatsImpl.Uid valueAt = this.mUidStats.valueAt(i3);
                if (valueAt.mBluetoothScanTimer == null) {
                    j6 = controllerRxTimeMillis;
                } else {
                    j6 = controllerRxTimeMillis;
                    j7 += valueAt.mBluetoothScanTimer.getTimeSinceMarkLocked(j2 * 1000) / 1000;
                }
                i3++;
                controllerRxTimeMillis = j6;
            }
            long j8 = controllerRxTimeMillis;
            boolean z2 = j7 > j8;
            boolean z3 = j7 > controllerTxTimeMillis;
            android.util.SparseLongArray sparseLongArray = new android.util.SparseLongArray(size);
            android.util.SparseLongArray sparseLongArray2 = new android.util.SparseLongArray(size);
            long j9 = controllerTxTimeMillis;
            long j10 = j8;
            int i4 = 0;
            while (i4 < size) {
                com.android.server.power.stats.BatteryStatsImpl.Uid valueAt2 = this.mUidStats.valueAt(i4);
                if (valueAt2.mBluetoothScanTimer == null) {
                    z = z2;
                    i = size;
                    j4 = controllerIdleTimeMillis;
                } else {
                    j4 = controllerIdleTimeMillis;
                    long timeSinceMarkLocked = valueAt2.mBluetoothScanTimer.getTimeSinceMarkLocked(j2 * 1000) / 1000;
                    if (timeSinceMarkLocked <= 0) {
                        z = z2;
                        i = size;
                    } else {
                        valueAt2.mBluetoothScanTimer.setMark(j2);
                        if (!z2) {
                            z = z2;
                            i2 = size;
                            j5 = timeSinceMarkLocked;
                        } else {
                            i2 = size;
                            z = z2;
                            j5 = (j8 * timeSinceMarkLocked) / j7;
                        }
                        if (z3) {
                            timeSinceMarkLocked = (timeSinceMarkLocked * controllerTxTimeMillis) / j7;
                        }
                        i = i2;
                        sparseLongArray.incrementValue(valueAt2.getUid(), j5);
                        sparseLongArray2.incrementValue(valueAt2.getUid(), timeSinceMarkLocked);
                        if (sparseDoubleArray != null) {
                            sparseDoubleArray.incrementValue(valueAt2.getUid(), this.mBluetoothPowerCalculator.calculatePowerMah(j5, timeSinceMarkLocked, 0L));
                        }
                        j10 -= j5;
                        j9 -= timeSinceMarkLocked;
                    }
                }
                i4++;
                controllerIdleTimeMillis = j4;
                z2 = z;
                size = i;
            }
            long j11 = controllerIdleTimeMillis;
            java.util.List uidTraffic = bluetoothActivityEnergyInfo.getUidTraffic();
            int size2 = uidTraffic.size();
            long j12 = 0;
            long j13 = 0;
            int i5 = 0;
            while (i5 < size2) {
                android.bluetooth.UidTraffic uidTraffic2 = (android.bluetooth.UidTraffic) uidTraffic.get(i5);
                long rxBytes = uidTraffic2.getRxBytes() - this.mLastBluetoothActivityInfo.uidRxBytes.get(uidTraffic2.getUid());
                long txBytes = uidTraffic2.getTxBytes() - this.mLastBluetoothActivityInfo.uidTxBytes.get(uidTraffic2.getUid());
                this.mNetworkByteActivityCounters[4].addCountLocked(rxBytes);
                this.mNetworkByteActivityCounters[5].addCountLocked(txBytes);
                com.android.server.power.stats.BatteryStatsImpl.Uid uidStatsLocked = getUidStatsLocked(mapUid(uidTraffic2.getUid()), j2, j3);
                uidStatsLocked.noteNetworkActivityLocked(4, rxBytes, 0L);
                uidStatsLocked.noteNetworkActivityLocked(5, txBytes, 0L);
                j13 += rxBytes;
                j12 += txBytes;
                i5++;
                controllerTxTimeMillis = controllerTxTimeMillis;
            }
            long j14 = controllerTxTimeMillis;
            if ((j12 != 0 || j13 != 0) && (j10 != 0 || j9 != 0)) {
                int i6 = 0;
                while (i6 < size2) {
                    android.bluetooth.UidTraffic uidTraffic3 = (android.bluetooth.UidTraffic) uidTraffic.get(i6);
                    int uid = uidTraffic3.getUid();
                    long rxBytes2 = uidTraffic3.getRxBytes() - this.mLastBluetoothActivityInfo.uidRxBytes.get(uid);
                    long txBytes2 = uidTraffic3.getTxBytes() - this.mLastBluetoothActivityInfo.uidTxBytes.get(uid);
                    int i7 = i6;
                    java.util.List list = uidTraffic;
                    getUidStatsLocked(mapUid(uid), j2, j3).getOrCreateBluetoothControllerActivityLocked();
                    if (j13 > 0 && rxBytes2 > 0) {
                        sparseLongArray.incrementValue(uid, (rxBytes2 * j10) / j13);
                    }
                    if (j12 > 0 && txBytes2 > 0) {
                        sparseLongArray2.incrementValue(uid, (txBytes2 * j9) / j12);
                    }
                    i6 = i7 + 1;
                    uidTraffic = list;
                }
                int i8 = 0;
                while (i8 < sparseLongArray2.size()) {
                    int keyAt = sparseLongArray2.keyAt(i8);
                    long valueAt3 = sparseLongArray2.valueAt(i8);
                    android.util.SparseLongArray sparseLongArray3 = sparseLongArray2;
                    getUidStatsLocked(keyAt, j2, j3).getOrCreateBluetoothControllerActivityLocked().getOrCreateTxTimeCounters()[0].increment(valueAt3, j2);
                    if (sparseDoubleArray != null) {
                        sparseDoubleArray.incrementValue(keyAt, this.mBluetoothPowerCalculator.calculatePowerMah(0L, valueAt3, 0L));
                    }
                    i8++;
                    sparseLongArray2 = sparseLongArray3;
                }
                for (int i9 = 0; i9 < sparseLongArray.size(); i9++) {
                    int keyAt2 = sparseLongArray.keyAt(i9);
                    long valueAt4 = sparseLongArray.valueAt(i9);
                    getUidStatsLocked(sparseLongArray.keyAt(i9), j2, j3).getOrCreateBluetoothControllerActivityLocked().getOrCreateRxTimeCounter().increment(valueAt4, j2);
                    if (sparseDoubleArray != null) {
                        sparseDoubleArray.incrementValue(keyAt2, this.mBluetoothPowerCalculator.calculatePowerMah(valueAt4, 0L, 0L));
                    }
                }
            }
            this.mBluetoothActivity.getOrCreateRxTimeCounter().increment(j8, j2);
            this.mBluetoothActivity.getOrCreateTxTimeCounters()[0].increment(j14, j2);
            this.mBluetoothActivity.getOrCreateIdleTimeCounter().increment(j11, j2);
            double averagePower = this.mPowerProfile.getAveragePower("bluetooth.controller.voltage") / 1000.0d;
            if (averagePower != 0.0d) {
                d = (bluetoothActivityEnergyInfo.getControllerEnergyUsed() - this.mLastBluetoothActivityInfo.energy) / averagePower;
                this.mBluetoothActivity.getPowerCounter().addCountLocked((long) d);
            } else {
                d = 0.0d;
            }
            if (sparseDoubleArray != null) {
                this.mGlobalEnergyConsumerStats.updateStandardBucket(5, j);
                distributeEnergyToUidsLocked(5, j, sparseDoubleArray, java.lang.Math.max(this.mBluetoothPowerCalculator.calculatePowerMah(j8, j14, j11), d / MILLISECONDS_IN_HOUR), j2);
            }
            this.mLastBluetoothActivityInfo.set(bluetoothActivityEnergyInfo);
            return;
        }
        this.mLastBluetoothActivityInfo.set(bluetoothActivityEnergyInfo);
    }

    public void fillLowPowerStats() {
        if (this.mPlatformIdleStateCallback == null) {
            return;
        }
        com.android.internal.os.RpmStats rpmStats = new com.android.internal.os.RpmStats();
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        if (elapsedRealtime - this.mLastRpmStatsUpdateTimeMs >= 1000) {
            this.mPlatformIdleStateCallback.fillLowPowerStats(rpmStats);
            synchronized (this) {
                this.mTmpRpmStats = rpmStats;
                this.mLastRpmStatsUpdateTimeMs = elapsedRealtime;
            }
        }
    }

    public void updateRpmStatsLocked(long j) {
        if (this.mTmpRpmStats == null) {
            return;
        }
        for (java.util.Map.Entry entry : this.mTmpRpmStats.mPlatformLowPowerStats.entrySet()) {
            java.lang.String str = (java.lang.String) entry.getKey();
            getRpmTimerLocked(str).update(((com.android.internal.os.RpmStats.PowerStatePlatformSleepState) entry.getValue()).mTimeMs * 1000, ((com.android.internal.os.RpmStats.PowerStatePlatformSleepState) entry.getValue()).mCount, j);
            for (java.util.Map.Entry entry2 : ((com.android.internal.os.RpmStats.PowerStatePlatformSleepState) entry.getValue()).mVoters.entrySet()) {
                getRpmTimerLocked(str + "." + ((java.lang.String) entry2.getKey())).update(((com.android.internal.os.RpmStats.PowerStateElement) entry2.getValue()).mTimeMs * 1000, ((com.android.internal.os.RpmStats.PowerStateElement) entry2.getValue()).mCount, j);
            }
        }
        for (java.util.Map.Entry entry3 : this.mTmpRpmStats.mSubsystemLowPowerStats.entrySet()) {
            java.lang.String str2 = (java.lang.String) entry3.getKey();
            for (java.util.Map.Entry entry4 : ((com.android.internal.os.RpmStats.PowerStateSubsystem) entry3.getValue()).mStates.entrySet()) {
                getRpmTimerLocked(str2 + "." + ((java.lang.String) entry4.getKey())).update(((com.android.internal.os.RpmStats.PowerStateElement) entry4.getValue()).mTimeMs * 1000, ((com.android.internal.os.RpmStats.PowerStateElement) entry4.getValue()).mCount, j);
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void updateCpuEnergyConsumerStatsLocked(@android.annotation.NonNull long[] jArr, @android.annotation.NonNull com.android.server.power.stats.BatteryStatsImpl.CpuDeltaPowerAccumulator cpuDeltaPowerAccumulator) {
        if (this.mGlobalEnergyConsumerStats == null) {
            return;
        }
        int length = jArr.length;
        long j = 0;
        long j2 = 0;
        for (long j3 : jArr) {
            j2 += j3;
        }
        if (j2 <= 0) {
            return;
        }
        long elapsedRealtime = this.mClock.elapsedRealtime();
        this.mGlobalEnergyConsumerStats.updateStandardBucket(3, j2, elapsedRealtime);
        double[] dArr = new double[length];
        for (int i = 0; i < length; i++) {
            if (cpuDeltaPowerAccumulator.totalClusterChargesMah[i] <= 0.0d) {
                dArr[i] = 0.0d;
            } else {
                dArr[i] = jArr[i] / cpuDeltaPowerAccumulator.totalClusterChargesMah[i];
            }
        }
        long size = cpuDeltaPowerAccumulator.perUidCpuClusterChargesMah.size();
        int i2 = 0;
        while (i2 < size) {
            com.android.server.power.stats.BatteryStatsImpl.Uid keyAt = cpuDeltaPowerAccumulator.perUidCpuClusterChargesMah.keyAt(i2);
            double[] valueAt = cpuDeltaPowerAccumulator.perUidCpuClusterChargesMah.valueAt(i2);
            long j4 = j;
            for (int i3 = 0; i3 < length; i3++) {
                j4 += (long) ((valueAt[i3] * dArr[i3]) + 0.5d);
            }
            if (j4 < 0) {
                android.util.Slog.wtf(TAG, "Unexpected proportional EnergyConsumer charge (" + j4 + ") for uid " + keyAt.mUid);
            } else {
                keyAt.addChargeToStandardBucketLocked(j4, 3, elapsedRealtime);
            }
            i2++;
            j = 0;
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void updateDisplayEnergyConsumerStatsLocked(long[] jArr, int[] iArr, long j) {
        int length;
        if (this.mGlobalEnergyConsumerStats == null) {
            return;
        }
        if (this.mPerDisplayBatteryStats.length == iArr.length) {
            length = iArr.length;
        } else {
            int i = this.mDisplayMismatchWtfCount;
            this.mDisplayMismatchWtfCount = i + 1;
            if (i % 100 == 0) {
                android.util.Slog.wtf(TAG, "Mismatch between PowerProfile reported display count (" + this.mPerDisplayBatteryStats.length + ") and PowerStatsHal reported display count (" + iArr.length + ")");
            }
            length = this.mPerDisplayBatteryStats.length < iArr.length ? this.mPerDisplayBatteryStats.length : iArr.length;
        }
        int[] iArr2 = new int[length];
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = iArr[i2];
            iArr2[i2] = this.mPerDisplayBatteryStats[i2].screenStateAtLastEnergyMeasurement;
            this.mPerDisplayBatteryStats[i2].screenStateAtLastEnergyMeasurement = i3;
        }
        if (!this.mOnBatteryInternal) {
            return;
        }
        if (!this.mIgnoreNextExternalStats) {
            long j2 = 0;
            for (int i4 = 0; i4 < length; i4++) {
                long j3 = jArr[i4];
                if (j3 > 0) {
                    int displayPowerBucket = com.android.internal.power.EnergyConsumerStats.getDisplayPowerBucket(iArr2[i4]);
                    this.mGlobalEnergyConsumerStats.updateStandardBucket(displayPowerBucket, j3);
                    if (displayPowerBucket == 0) {
                        j2 += j3;
                    }
                }
            }
            if (j2 <= 0) {
                return;
            }
            android.util.SparseDoubleArray sparseDoubleArray = new android.util.SparseDoubleArray();
            int size = this.mUidStats.size();
            for (int i5 = 0; i5 < size; i5++) {
                com.android.server.power.stats.BatteryStatsImpl.Uid valueAt = this.mUidStats.valueAt(i5);
                long markProcessForegroundTimeUs = valueAt.markProcessForegroundTimeUs(j, true);
                if (markProcessForegroundTimeUs != 0) {
                    sparseDoubleArray.put(valueAt.getUid(), markProcessForegroundTimeUs);
                }
            }
            distributeEnergyToUidsLocked(0, j2, sparseDoubleArray, 0.0d, j);
            return;
        }
        int size2 = this.mUidStats.size();
        for (int i6 = 0; i6 < size2; i6++) {
            this.mUidStats.valueAt(i6).markProcessForegroundTimeUs(j, false);
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void updateGnssEnergyConsumerStatsLocked(long j, long j2) {
        if (this.mGlobalEnergyConsumerStats == null || !this.mOnBatteryInternal || j <= 0) {
            return;
        }
        int i = 0;
        if (this.mIgnoreNextExternalStats) {
            int size = this.mUidStats.size();
            while (i < size) {
                this.mUidStats.valueAt(i).markGnssTimeUs(j2);
                i++;
            }
            return;
        }
        this.mGlobalEnergyConsumerStats.updateStandardBucket(6, j);
        android.util.SparseDoubleArray sparseDoubleArray = new android.util.SparseDoubleArray();
        int size2 = this.mUidStats.size();
        while (i < size2) {
            com.android.server.power.stats.BatteryStatsImpl.Uid valueAt = this.mUidStats.valueAt(i);
            long markGnssTimeUs = valueAt.markGnssTimeUs(j2);
            if (markGnssTimeUs != 0) {
                sparseDoubleArray.put(valueAt.getUid(), markGnssTimeUs);
            }
            i++;
        }
        distributeEnergyToUidsLocked(6, j, sparseDoubleArray, 0.0d, j2);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void updateCameraEnergyConsumerStatsLocked(long j, long j2) {
        if (this.mGlobalEnergyConsumerStats == null || !this.mOnBatteryInternal || j <= 0) {
            return;
        }
        int i = 0;
        if (this.mIgnoreNextExternalStats) {
            int size = this.mUidStats.size();
            while (i < size) {
                this.mUidStats.valueAt(i).markCameraTimeUs(j2);
                i++;
            }
            return;
        }
        this.mGlobalEnergyConsumerStats.updateStandardBucket(8, j);
        android.util.SparseDoubleArray sparseDoubleArray = new android.util.SparseDoubleArray();
        int size2 = this.mUidStats.size();
        while (i < size2) {
            com.android.server.power.stats.BatteryStatsImpl.Uid valueAt = this.mUidStats.valueAt(i);
            long markCameraTimeUs = valueAt.markCameraTimeUs(j2);
            if (markCameraTimeUs != 0) {
                sparseDoubleArray.put(valueAt.getUid(), markCameraTimeUs);
            }
            i++;
        }
        distributeEnergyToUidsLocked(8, j, sparseDoubleArray, 0.0d, j2);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void updateCustomEnergyConsumerStatsLocked(int i, long j, @android.annotation.Nullable android.util.SparseLongArray sparseLongArray) {
        if (this.mGlobalEnergyConsumerStats == null || !this.mOnBatteryInternal || this.mIgnoreNextExternalStats || j <= 0) {
            return;
        }
        this.mGlobalEnergyConsumerStats.updateCustomBucket(i, j, this.mClock.elapsedRealtime());
        if (sparseLongArray == null) {
            return;
        }
        int size = sparseLongArray.size();
        for (int i2 = 0; i2 < size; i2++) {
            int mapUid = mapUid(sparseLongArray.keyAt(i2));
            long valueAt = sparseLongArray.valueAt(i2);
            if (valueAt != 0) {
                com.android.server.power.stats.BatteryStatsImpl.Uid availableUidStatsLocked = getAvailableUidStatsLocked(mapUid);
                if (availableUidStatsLocked != null) {
                    availableUidStatsLocked.addChargeToCustomBucketLocked(valueAt, i);
                } else if (!android.os.Process.isIsolated(mapUid)) {
                    android.util.Slog.w(TAG, "Received EnergyConsumer charge " + j + " for custom bucket " + i + " for non-existent uid " + mapUid);
                }
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void distributeEnergyToUidsLocked(int i, long j, android.util.SparseDoubleArray sparseDoubleArray, double d, long j2) {
        double d2 = 0.0d;
        for (int size = sparseDoubleArray.size() - 1; size >= 0; size--) {
            d2 += sparseDoubleArray.valueAt(size);
        }
        double max = java.lang.Math.max(d2, d);
        if (max <= 0.0d) {
            return;
        }
        for (int size2 = sparseDoubleArray.size() - 1; size2 >= 0; size2--) {
            getAvailableUidStatsLocked(sparseDoubleArray.keyAt(size2)).addChargeToStandardBucketLocked((long) (((j * sparseDoubleArray.valueAt(size2)) / max) + 0.5d), i, j2);
        }
    }

    public void updateRailStatsLocked() {
        if (this.mEnergyConsumerRetriever == null || !this.mTmpRailStats.isRailStatsAvailable()) {
            return;
        }
        this.mEnergyConsumerRetriever.fillRailDataStats(this.mTmpRailStats);
    }

    public void informThatAllExternalStatsAreFlushed() {
        synchronized (this) {
            this.mIgnoreNextExternalStats = false;
        }
    }

    public void updateKernelWakelocksLocked(long j) {
        if (this.mKernelWakelockReader != null) {
            com.android.server.power.stats.KernelWakelockStats readKernelWakelockStats = this.mKernelWakelockReader.readKernelWakelockStats(this.mTmpWakelockStats);
            if (readKernelWakelockStats == null) {
                android.util.Slog.w(TAG, "Couldn't get kernel wake lock stats");
                return;
            }
            for (java.util.Map.Entry<java.lang.String, com.android.server.power.stats.KernelWakelockStats.Entry> entry : readKernelWakelockStats.entrySet()) {
                java.lang.String key = entry.getKey();
                com.android.server.power.stats.KernelWakelockStats.Entry value = entry.getValue();
                com.android.server.power.stats.BatteryStatsImpl.SamplingTimer samplingTimer = this.mKernelWakelockStats.get(key);
                if (samplingTimer == null) {
                    samplingTimer = new com.android.server.power.stats.BatteryStatsImpl.SamplingTimer(this.mClock, this.mOnBatteryScreenOffTimeBase);
                    this.mKernelWakelockStats.put(key, samplingTimer);
                }
                samplingTimer.update(value.totalTimeUs, value.activeTimeUs, value.count, j);
                samplingTimer.setUpdateVersion(value.version);
            }
            java.util.Iterator<java.util.Map.Entry<java.lang.String, com.android.server.power.stats.BatteryStatsImpl.SamplingTimer>> it = this.mKernelWakelockStats.entrySet().iterator();
            while (it.hasNext()) {
                com.android.server.power.stats.BatteryStatsImpl.SamplingTimer value2 = it.next().getValue();
                if (value2.getUpdateVersion() != readKernelWakelockStats.kernelWakelockVersion) {
                    value2.endSample(j);
                }
            }
        }
    }

    public void updateKernelMemoryBandwidthLocked(long j) {
        com.android.server.power.stats.BatteryStatsImpl.SamplingTimer samplingTimer;
        this.mKernelMemoryBandwidthStats.updateStats();
        android.util.LongSparseLongArray bandwidthEntries = this.mKernelMemoryBandwidthStats.getBandwidthEntries();
        int size = bandwidthEntries.size();
        for (int i = 0; i < size; i++) {
            int indexOfKey = this.mKernelMemoryStats.indexOfKey(bandwidthEntries.keyAt(i));
            if (indexOfKey >= 0) {
                samplingTimer = this.mKernelMemoryStats.valueAt(indexOfKey);
            } else {
                samplingTimer = new com.android.server.power.stats.BatteryStatsImpl.SamplingTimer(this.mClock, this.mOnBatteryTimeBase);
                this.mKernelMemoryStats.put(bandwidthEntries.keyAt(i), samplingTimer);
            }
            samplingTimer.update(bandwidthEntries.valueAt(i), 1, j);
        }
    }

    public boolean isOnBatteryLocked() {
        return this.mOnBatteryTimeBase.isRunning();
    }

    public boolean isOnBatteryScreenOffLocked() {
        return this.mOnBatteryScreenOffTimeBase.isRunning();
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class CpuDeltaPowerAccumulator {
        private final com.android.server.power.stats.CpuPowerCalculator mCalculator;
        public final double[] totalClusterChargesMah;
        private com.android.server.power.stats.BatteryStatsImpl.Uid mCachedUid = null;
        private double[] mUidClusterCache = null;
        public final android.util.ArrayMap<com.android.server.power.stats.BatteryStatsImpl.Uid, double[]> perUidCpuClusterChargesMah = new android.util.ArrayMap<>();

        CpuDeltaPowerAccumulator(com.android.server.power.stats.CpuPowerCalculator cpuPowerCalculator, int i) {
            this.mCalculator = cpuPowerCalculator;
            this.totalClusterChargesMah = new double[i];
        }

        public void addCpuClusterDurationsMs(com.android.server.power.stats.BatteryStatsImpl.Uid uid, long[] jArr) {
            double[] orCreateUidCpuClusterCharges = getOrCreateUidCpuClusterCharges(uid);
            for (int i = 0; i < jArr.length; i++) {
                double calculatePerCpuClusterPowerMah = this.mCalculator.calculatePerCpuClusterPowerMah(i, jArr[i]);
                orCreateUidCpuClusterCharges[i] = orCreateUidCpuClusterCharges[i] + calculatePerCpuClusterPowerMah;
                double[] dArr = this.totalClusterChargesMah;
                dArr[i] = dArr[i] + calculatePerCpuClusterPowerMah;
            }
        }

        public void addCpuClusterSpeedDurationsMs(com.android.server.power.stats.BatteryStatsImpl.Uid uid, int i, int i2, long j) {
            double[] orCreateUidCpuClusterCharges = getOrCreateUidCpuClusterCharges(uid);
            double calculatePerCpuFreqPowerMah = this.mCalculator.calculatePerCpuFreqPowerMah(i, i2, j);
            orCreateUidCpuClusterCharges[i] = orCreateUidCpuClusterCharges[i] + calculatePerCpuFreqPowerMah;
            double[] dArr = this.totalClusterChargesMah;
            dArr[i] = dArr[i] + calculatePerCpuFreqPowerMah;
        }

        private double[] getOrCreateUidCpuClusterCharges(com.android.server.power.stats.BatteryStatsImpl.Uid uid) {
            if (uid == this.mCachedUid) {
                return this.mUidClusterCache;
            }
            double[] dArr = this.perUidCpuClusterChargesMah.get(uid);
            if (dArr == null) {
                dArr = new double[this.totalClusterChargesMah.length];
                this.perUidCpuClusterChargesMah.put(uid, dArr);
            }
            this.mCachedUid = uid;
            this.mUidClusterCache = dArr;
            return dArr;
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void updateCpuTimeLocked(boolean z, boolean z2, long[] jArr) {
        java.util.ArrayList<com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer> arrayList;
        com.android.server.power.stats.BatteryStatsImpl.CpuDeltaPowerAccumulator cpuDeltaPowerAccumulator = null;
        if (!z2) {
            arrayList = null;
        } else {
            arrayList = new java.util.ArrayList<>();
            for (int size = this.mPartialTimers.size() - 1; size >= 0; size--) {
                com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer stopwatchTimer = this.mPartialTimers.get(size);
                if (stopwatchTimer.mInList && stopwatchTimer.mUid != null && stopwatchTimer.mUid.mUid != 1000) {
                    arrayList.add(stopwatchTimer);
                }
            }
        }
        markPartialTimersAsEligible();
        if (!z) {
            this.mCpuUidUserSysTimeReader.readDelta(false, (com.android.internal.os.KernelCpuUidTimeReader.Callback) null);
            this.mCpuUidFreqTimeReader.readDelta(false, (com.android.internal.os.KernelCpuUidTimeReader.Callback) null);
            this.mNumAllUidCpuTimeReads += 2;
            if (this.mConstants.TRACK_CPU_ACTIVE_CLUSTER_TIME) {
                this.mCpuUidActiveTimeReader.readDelta(false, (com.android.internal.os.KernelCpuUidTimeReader.Callback) null);
                this.mCpuUidClusterTimeReader.readDelta(false, (com.android.internal.os.KernelCpuUidTimeReader.Callback) null);
                this.mNumAllUidCpuTimeReads += 2;
            }
            for (int length = this.mKernelCpuSpeedReaders.length - 1; length >= 0; length--) {
                if (this.mKernelCpuSpeedReaders[length] != null) {
                    this.mKernelCpuSpeedReaders[length].readDelta();
                }
            }
            com.android.server.power.optimization.Flags.disableSystemServicePowerAttr();
            this.mSystemServerCpuThreadReader.readDelta();
            return;
        }
        this.mUserInfoProvider.refreshUserIds();
        android.util.SparseLongArray sparseLongArray = this.mCpuUidFreqTimeReader.allUidTimesAvailable() ? null : new android.util.SparseLongArray();
        if (this.mGlobalEnergyConsumerStats != null && this.mGlobalEnergyConsumerStats.isStandardBucketSupported(3)) {
            if (jArr == null) {
                android.util.Slog.wtf(TAG, "POWER_BUCKET_CPU supported but no EnergyConsumer Cpu Cluster charge reported on updateCpuTimeLocked!");
            } else {
                if (this.mCpuPowerCalculator == null) {
                    this.mCpuPowerCalculator = new com.android.server.power.stats.CpuPowerCalculator(this.mCpuScalingPolicies, this.mPowerProfile);
                }
                cpuDeltaPowerAccumulator = new com.android.server.power.stats.BatteryStatsImpl.CpuDeltaPowerAccumulator(this.mCpuPowerCalculator, this.mCpuScalingPolicies.getPolicies().length);
            }
        }
        readKernelUidCpuTimesLocked(arrayList, sparseLongArray, z);
        if (sparseLongArray != null) {
            updateClusterSpeedTimes(sparseLongArray, z, cpuDeltaPowerAccumulator);
        }
        readKernelUidCpuFreqTimesLocked(arrayList, z, z2, cpuDeltaPowerAccumulator);
        this.mNumAllUidCpuTimeReads += 2;
        if (this.mConstants.TRACK_CPU_ACTIVE_CLUSTER_TIME) {
            readKernelUidCpuActiveTimesLocked(z);
            readKernelUidCpuClusterTimesLocked(z, cpuDeltaPowerAccumulator);
            this.mNumAllUidCpuTimeReads += 2;
        }
        com.android.server.power.optimization.Flags.disableSystemServicePowerAttr();
        updateSystemServerThreadStats();
        if (cpuDeltaPowerAccumulator != null) {
            updateCpuEnergyConsumerStatsLocked(jArr, cpuDeltaPowerAccumulator);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public void updateSystemServerThreadStats() {
        com.android.server.power.stats.SystemServerCpuThreadReader.SystemServiceCpuThreadTimes readDelta = this.mSystemServerCpuThreadReader.readDelta();
        if (readDelta == null) {
            return;
        }
        if (this.mBinderThreadCpuTimesUs == null) {
            this.mBinderThreadCpuTimesUs = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounterArray(this.mOnBatteryTimeBase);
        }
        this.mBinderThreadCpuTimesUs.addCountLocked(readDelta.binderThreadCpuTimesUs);
    }

    @com.android.internal.annotations.VisibleForTesting
    public void markPartialTimersAsEligible() {
        int i;
        if (com.android.internal.util.ArrayUtils.referenceEquals(this.mPartialTimers, this.mLastPartialTimers)) {
            for (int size = this.mPartialTimers.size() - 1; size >= 0; size--) {
                this.mPartialTimers.get(size).mInList = true;
            }
            return;
        }
        int size2 = this.mLastPartialTimers.size() - 1;
        while (true) {
            if (size2 < 0) {
                break;
            }
            this.mLastPartialTimers.get(size2).mInList = false;
            size2--;
        }
        this.mLastPartialTimers.clear();
        int size3 = this.mPartialTimers.size();
        for (i = 0; i < size3; i++) {
            com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer stopwatchTimer = this.mPartialTimers.get(i);
            stopwatchTimer.mInList = true;
            this.mLastPartialTimers.add(stopwatchTimer);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public void updateClusterSpeedTimes(@android.annotation.NonNull android.util.SparseLongArray sparseLongArray, boolean z, @android.annotation.Nullable com.android.server.power.stats.BatteryStatsImpl.CpuDeltaPowerAccumulator cpuDeltaPowerAccumulator) {
        int i;
        int i2;
        android.util.SparseLongArray sparseLongArray2 = sparseLongArray;
        long[][] jArr = new long[this.mKernelCpuSpeedReaders.length][];
        long j = 0;
        for (int i3 = 0; i3 < this.mKernelCpuSpeedReaders.length; i3++) {
            if (this.mKernelCpuSpeedReaders[i3] != null) {
                jArr[i3] = this.mKernelCpuSpeedReaders[i3].readDelta();
                if (jArr[i3] != null) {
                    for (int length = jArr[i3].length - 1; length >= 0; length--) {
                        j += jArr[i3][length];
                    }
                }
            }
        }
        if (j != 0) {
            int size = sparseLongArray.size();
            long elapsedRealtime = this.mClock.elapsedRealtime();
            long uptimeMillis = this.mClock.uptimeMillis();
            int i4 = 0;
            while (i4 < size) {
                int i5 = i4;
                com.android.server.power.stats.BatteryStatsImpl.Uid uidStatsLocked = getUidStatsLocked(sparseLongArray2.keyAt(i4), elapsedRealtime, uptimeMillis);
                long valueAt = sparseLongArray2.valueAt(i5);
                int[] policies = this.mCpuScalingPolicies.getPolicies();
                if (uidStatsLocked.mCpuClusterSpeedTimesUs == null || uidStatsLocked.mCpuClusterSpeedTimesUs.length != policies.length) {
                    uidStatsLocked.mCpuClusterSpeedTimesUs = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter[policies.length][];
                }
                int i6 = 0;
                while (i6 < policies.length) {
                    int length2 = jArr[i6].length;
                    int[] iArr = policies;
                    if (uidStatsLocked.mCpuClusterSpeedTimesUs[i6] == null || length2 != uidStatsLocked.mCpuClusterSpeedTimesUs[i6].length) {
                        uidStatsLocked.mCpuClusterSpeedTimesUs[i6] = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter[length2];
                    }
                    com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter[] longSamplingCounterArr = uidStatsLocked.mCpuClusterSpeedTimesUs[i6];
                    int i7 = 0;
                    while (i7 < length2) {
                        if (longSamplingCounterArr[i7] != null) {
                            i = length2;
                            i2 = size;
                        } else {
                            i = length2;
                            i2 = size;
                            longSamplingCounterArr[i7] = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter(this.mOnBatteryTimeBase);
                        }
                        long j2 = valueAt;
                        long j3 = (jArr[i6][i7] * valueAt) / j;
                        longSamplingCounterArr[i7].addCountLocked(j3, z);
                        if (cpuDeltaPowerAccumulator != null) {
                            cpuDeltaPowerAccumulator.addCpuClusterSpeedDurationsMs(uidStatsLocked, i6, i7, j3);
                        }
                        i7++;
                        length2 = i;
                        size = i2;
                        valueAt = j2;
                    }
                    i6++;
                    policies = iArr;
                    size = size;
                }
                i4 = i5 + 1;
                sparseLongArray2 = sparseLongArray;
                size = size;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public void readKernelUidCpuTimesLocked(@android.annotation.Nullable java.util.ArrayList<com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer> arrayList, @android.annotation.Nullable final android.util.SparseLongArray sparseLongArray, final boolean z) {
        this.mTempTotalCpuSystemTimeUs = 0L;
        this.mTempTotalCpuUserTimeUs = 0L;
        int size = arrayList == null ? 0 : arrayList.size();
        final long uptimeMillis = this.mClock.uptimeMillis();
        final long elapsedRealtime = this.mClock.elapsedRealtime();
        final int i = size;
        int i2 = size;
        this.mCpuUidUserSysTimeReader.readDelta(false, new com.android.internal.os.KernelCpuUidTimeReader.Callback() { // from class: com.android.server.power.stats.BatteryStatsImpl$$ExternalSyntheticLambda5
            public final void onUidCpuTime(int i3, java.lang.Object obj) {
                com.android.server.power.stats.BatteryStatsImpl.this.lambda$readKernelUidCpuTimesLocked$4(elapsedRealtime, uptimeMillis, i, z, sparseLongArray, i3, (long[]) obj);
            }
        });
        long uptimeMillis2 = this.mClock.uptimeMillis() - uptimeMillis;
        if (uptimeMillis2 >= 100) {
            android.util.Slog.d(TAG, "Reading cpu stats took " + uptimeMillis2 + "ms");
        }
        if (i2 > 0) {
            this.mTempTotalCpuUserTimeUs = (this.mTempTotalCpuUserTimeUs * 50) / 100;
            this.mTempTotalCpuSystemTimeUs = (this.mTempTotalCpuSystemTimeUs * 50) / 100;
            for (int i3 = 0; i3 < i2; i3++) {
                com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer stopwatchTimer = arrayList.get(i3);
                long j = i2 - i3;
                int i4 = (int) (this.mTempTotalCpuUserTimeUs / j);
                int i5 = (int) (this.mTempTotalCpuSystemTimeUs / j);
                long j2 = i4;
                stopwatchTimer.mUid.mUserCpuTime.addCountLocked(j2, z);
                long j3 = i5;
                stopwatchTimer.mUid.mSystemCpuTime.addCountLocked(j3, z);
                if (sparseLongArray != null) {
                    int uid = stopwatchTimer.mUid.getUid();
                    sparseLongArray.put(uid, sparseLongArray.get(uid, 0L) + j2 + j3);
                }
                stopwatchTimer.mUid.getProcessStatsLocked("*wakelock*").addCpuTimeLocked(i4 / 1000, i5 / 1000, z);
                this.mTempTotalCpuUserTimeUs -= j2;
                this.mTempTotalCpuSystemTimeUs -= j3;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$readKernelUidCpuTimesLocked$4(long j, long j2, int i, boolean z, android.util.SparseLongArray sparseLongArray, int i2, long[] jArr) {
        long j3 = jArr[0];
        long j4 = jArr[1];
        int mapUid = mapUid(i2);
        if (android.os.Process.isIsolated(mapUid) || !this.mUserInfoProvider.exists(android.os.UserHandle.getUserId(mapUid))) {
            return;
        }
        com.android.server.power.stats.BatteryStatsImpl.Uid uidStatsLocked = getUidStatsLocked(mapUid, j, j2);
        this.mTempTotalCpuUserTimeUs += j3;
        this.mTempTotalCpuSystemTimeUs += j4;
        if (i > 0) {
            j3 = (j3 * 50) / 100;
            j4 = (j4 * 50) / 100;
        }
        uidStatsLocked.mUserCpuTime.addCountLocked(j3, z);
        uidStatsLocked.mSystemCpuTime.addCountLocked(j4, z);
        if (sparseLongArray != null) {
            sparseLongArray.put(uidStatsLocked.getUid(), j3 + j4);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0097, code lost:
    
        if (r9.mCpuClusterSpeedTimesUs.length != r10) goto L24;
     */
    @com.android.internal.annotations.VisibleForTesting
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void readKernelUidCpuFreqTimesLocked(@android.annotation.Nullable java.util.ArrayList<com.android.server.power.stats.BatteryStatsImpl.StopwatchTimer> arrayList, final boolean z, final boolean z2, @android.annotation.Nullable final com.android.server.power.stats.BatteryStatsImpl.CpuDeltaPowerAccumulator cpuDeltaPowerAccumulator) {
        int i;
        final boolean perClusterTimesAvailable = this.mCpuUidFreqTimeReader.perClusterTimesAvailable();
        int size = arrayList == null ? 0 : arrayList.size();
        final int[] policies = this.mCpuScalingPolicies.getPolicies();
        final int length = policies.length;
        this.mWakeLockAllocationsUs = null;
        final long uptimeMillis = this.mClock.uptimeMillis();
        final long elapsedRealtime = this.mClock.elapsedRealtime();
        int i2 = length;
        final int i3 = size;
        int i4 = size;
        this.mCpuUidFreqTimeReader.readDelta(cpuDeltaPowerAccumulator != null, new com.android.internal.os.KernelCpuUidTimeReader.Callback() { // from class: com.android.server.power.stats.BatteryStatsImpl$$ExternalSyntheticLambda3
            public final void onUidCpuTime(int i5, java.lang.Object obj) {
                com.android.server.power.stats.BatteryStatsImpl.this.lambda$readKernelUidCpuFreqTimesLocked$5(elapsedRealtime, uptimeMillis, z, z2, perClusterTimesAvailable, length, i3, policies, cpuDeltaPowerAccumulator, i5, (long[]) obj);
            }
        });
        long uptimeMillis2 = this.mClock.uptimeMillis() - uptimeMillis;
        if (uptimeMillis2 >= 100) {
            android.util.Slog.d(TAG, "Reading cpu freq times took " + uptimeMillis2 + "ms");
        }
        if (this.mWakeLockAllocationsUs != null) {
            int i5 = 0;
            while (true) {
                int i6 = i4;
                if (i5 < i6) {
                    com.android.server.power.stats.BatteryStatsImpl.Uid uid = arrayList.get(i5).mUid;
                    if (uid.mCpuClusterSpeedTimesUs != null) {
                        i = i2;
                    } else {
                        i = i2;
                    }
                    detachIfNotNull(uid.mCpuClusterSpeedTimesUs);
                    uid.mCpuClusterSpeedTimesUs = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter[i][];
                    for (int i7 = 0; i7 < i; i7++) {
                        int length2 = this.mCpuScalingPolicies.getFrequencies(policies[i7]).length;
                        if (uid.mCpuClusterSpeedTimesUs[i7] == null || uid.mCpuClusterSpeedTimesUs[i7].length != length2) {
                            detachIfNotNull(uid.mCpuClusterSpeedTimesUs[i7]);
                            uid.mCpuClusterSpeedTimesUs[i7] = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter[length2];
                        }
                        com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter[] longSamplingCounterArr = uid.mCpuClusterSpeedTimesUs[i7];
                        for (int i8 = 0; i8 < length2; i8++) {
                            if (longSamplingCounterArr[i8] == null) {
                                longSamplingCounterArr[i8] = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter(this.mOnBatteryTimeBase);
                            }
                            long j = this.mWakeLockAllocationsUs[i7][i8] / (i6 - i5);
                            longSamplingCounterArr[i8].addCountLocked(j, z);
                            long[] jArr = this.mWakeLockAllocationsUs[i7];
                            jArr[i8] = jArr[i8] - j;
                            if (cpuDeltaPowerAccumulator != null) {
                                cpuDeltaPowerAccumulator.addCpuClusterSpeedDurationsMs(uid, i7, i8, j / 1000);
                            }
                        }
                    }
                    i5++;
                    i2 = i;
                    i4 = i6;
                } else {
                    return;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$readKernelUidCpuFreqTimesLocked$5(long j, long j2, boolean z, boolean z2, boolean z3, int i, int i2, int[] iArr, com.android.server.power.stats.BatteryStatsImpl.CpuDeltaPowerAccumulator cpuDeltaPowerAccumulator, int i3, long[] jArr) {
        int i4;
        long j3;
        int i5;
        int mapUid = mapUid(i3);
        if (android.os.Process.isIsolated(mapUid) || !this.mUserInfoProvider.exists(android.os.UserHandle.getUserId(mapUid))) {
            return;
        }
        com.android.server.power.stats.BatteryStatsImpl.Uid uidStatsLocked = getUidStatsLocked(mapUid, j, j2);
        if (uidStatsLocked.mCpuFreqTimeMs == null || uidStatsLocked.mCpuFreqTimeMs.getSize() != jArr.length) {
            detachIfNotNull(uidStatsLocked.mCpuFreqTimeMs);
            uidStatsLocked.mCpuFreqTimeMs = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounterArray(this.mOnBatteryTimeBase);
        }
        uidStatsLocked.mCpuFreqTimeMs.addCountLocked(jArr, z);
        if (uidStatsLocked.mScreenOffCpuFreqTimeMs == null || uidStatsLocked.mScreenOffCpuFreqTimeMs.getSize() != jArr.length) {
            detachIfNotNull(uidStatsLocked.mScreenOffCpuFreqTimeMs);
            uidStatsLocked.mScreenOffCpuFreqTimeMs = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounterArray(this.mOnBatteryScreenOffTimeBase);
        }
        uidStatsLocked.mScreenOffCpuFreqTimeMs.addCountLocked(jArr, z2);
        if (z3) {
            if (uidStatsLocked.mCpuClusterSpeedTimesUs == null || uidStatsLocked.mCpuClusterSpeedTimesUs.length != i) {
                detachIfNotNull(uidStatsLocked.mCpuClusterSpeedTimesUs);
                uidStatsLocked.mCpuClusterSpeedTimesUs = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter[i][];
            }
            if (i2 > 0 && this.mWakeLockAllocationsUs == null) {
                this.mWakeLockAllocationsUs = new long[i][];
            }
            int i6 = 0;
            int i7 = 0;
            while (i6 < i) {
                int[] frequencies = this.mCpuScalingPolicies.getFrequencies(iArr[i6]);
                if (uidStatsLocked.mCpuClusterSpeedTimesUs[i6] == null || uidStatsLocked.mCpuClusterSpeedTimesUs[i6].length != frequencies.length) {
                    detachIfNotNull(uidStatsLocked.mCpuClusterSpeedTimesUs[i6]);
                    uidStatsLocked.mCpuClusterSpeedTimesUs[i6] = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter[frequencies.length];
                }
                if (i2 > 0 && this.mWakeLockAllocationsUs[i6] == null) {
                    this.mWakeLockAllocationsUs[i6] = new long[frequencies.length];
                }
                com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter[] longSamplingCounterArr = uidStatsLocked.mCpuClusterSpeedTimesUs[i6];
                int i8 = 0;
                while (i8 < frequencies.length) {
                    if (longSamplingCounterArr[i8] == null) {
                        longSamplingCounterArr[i8] = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter(this.mOnBatteryTimeBase);
                    }
                    if (this.mWakeLockAllocationsUs != null) {
                        long j4 = ((jArr[i7] * 1000) * 50) / 100;
                        long[] jArr2 = this.mWakeLockAllocationsUs[i6];
                        jArr2[i8] = jArr2[i8] + ((jArr[i7] * 1000) - j4);
                        i4 = i6;
                        j3 = j4;
                    } else {
                        i4 = i6;
                        j3 = jArr[i7] * 1000;
                    }
                    longSamplingCounterArr[i8].addCountLocked(j3, z);
                    if (cpuDeltaPowerAccumulator == null) {
                        i5 = i8;
                    } else {
                        i5 = i8;
                        cpuDeltaPowerAccumulator.addCpuClusterSpeedDurationsMs(uidStatsLocked, i4, i8, j3 / 1000);
                    }
                    i7++;
                    i8 = i5 + 1;
                    i6 = i4;
                }
                i6++;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public void readKernelUidCpuActiveTimesLocked(boolean z) {
        final long uptimeMillis = this.mClock.uptimeMillis();
        final long elapsedRealtime = this.mClock.elapsedRealtime();
        this.mCpuUidActiveTimeReader.readAbsolute(new com.android.internal.os.KernelCpuUidTimeReader.Callback() { // from class: com.android.server.power.stats.BatteryStatsImpl$$ExternalSyntheticLambda1
            public final void onUidCpuTime(int i, java.lang.Object obj) {
                com.android.server.power.stats.BatteryStatsImpl.this.lambda$readKernelUidCpuActiveTimesLocked$6(elapsedRealtime, uptimeMillis, i, (java.lang.Long) obj);
            }
        });
        long uptimeMillis2 = this.mClock.uptimeMillis() - uptimeMillis;
        if (uptimeMillis2 >= 100) {
            android.util.Slog.d(TAG, "Reading cpu active times took " + uptimeMillis2 + "ms");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$readKernelUidCpuActiveTimesLocked$6(long j, long j2, int i, java.lang.Long l) {
        com.android.server.power.stats.BatteryStatsImpl.Uid.ChildUid childUid;
        int mapUid = mapUid(i);
        if (android.os.Process.isIsolated(mapUid) || !this.mUserInfoProvider.exists(android.os.UserHandle.getUserId(i))) {
            return;
        }
        com.android.server.power.stats.BatteryStatsImpl.Uid uidStatsLocked = getUidStatsLocked(mapUid, j, j2);
        if (mapUid == i) {
            uidStatsLocked.getCpuActiveTimeCounter().update(l.longValue(), j);
            return;
        }
        android.util.SparseArray<com.android.server.power.stats.BatteryStatsImpl.Uid.ChildUid> sparseArray = uidStatsLocked.mChildUids;
        if (sparseArray != null && (childUid = sparseArray.get(i)) != null) {
            uidStatsLocked.getCpuActiveTimeCounter().increment(childUid.cpuActiveCounter.update(l.longValue(), j), j);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public void readKernelUidCpuClusterTimesLocked(final boolean z, @android.annotation.Nullable final com.android.server.power.stats.BatteryStatsImpl.CpuDeltaPowerAccumulator cpuDeltaPowerAccumulator) {
        final long uptimeMillis = this.mClock.uptimeMillis();
        final long elapsedRealtime = this.mClock.elapsedRealtime();
        this.mCpuUidClusterTimeReader.readDelta(cpuDeltaPowerAccumulator != null, new com.android.internal.os.KernelCpuUidTimeReader.Callback() { // from class: com.android.server.power.stats.BatteryStatsImpl$$ExternalSyntheticLambda4
            public final void onUidCpuTime(int i, java.lang.Object obj) {
                com.android.server.power.stats.BatteryStatsImpl.this.lambda$readKernelUidCpuClusterTimesLocked$7(elapsedRealtime, uptimeMillis, z, cpuDeltaPowerAccumulator, i, (long[]) obj);
            }
        });
        long uptimeMillis2 = this.mClock.uptimeMillis() - uptimeMillis;
        if (uptimeMillis2 >= 100) {
            android.util.Slog.d(TAG, "Reading cpu cluster times took " + uptimeMillis2 + "ms");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$readKernelUidCpuClusterTimesLocked$7(long j, long j2, boolean z, com.android.server.power.stats.BatteryStatsImpl.CpuDeltaPowerAccumulator cpuDeltaPowerAccumulator, int i, long[] jArr) {
        int mapUid = mapUid(i);
        if (android.os.Process.isIsolated(mapUid) || !this.mUserInfoProvider.exists(android.os.UserHandle.getUserId(mapUid))) {
            return;
        }
        com.android.server.power.stats.BatteryStatsImpl.Uid uidStatsLocked = getUidStatsLocked(mapUid, j, j2);
        uidStatsLocked.mCpuClusterTimesMs.addCountLocked(jArr, z);
        if (cpuDeltaPowerAccumulator != null) {
            cpuDeltaPowerAccumulator.addCpuClusterDurationsMs(uidStatsLocked, jArr);
        }
    }

    boolean setChargingLocked(boolean z) {
        this.mHandler.removeCallbacks(this.mDeferSetCharging);
        if (this.mCharging != z) {
            this.mCharging = z;
            this.mHistory.setChargingState(z);
            this.mHandler.sendEmptyMessage(3);
            return true;
        }
        return false;
    }

    public void onSystemReady() {
        if (this.mCpuUidFreqTimeReader != null) {
            this.mCpuUidFreqTimeReader.onSystemReady();
        }
        if (this.mCpuPowerStatsCollector != null) {
            this.mCpuPowerStatsCollector.setEnabled(this.mPowerStatsCollectorEnabled);
        }
        this.mSystemReady = true;
    }

    @com.android.internal.annotations.VisibleForTesting
    public void forceRecordAllHistory() {
        this.mHistory.forceRecordAllHistory();
        this.mRecordAllHistory = true;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    @com.android.internal.annotations.VisibleForTesting
    public void maybeResetWhilePluggedInLocked() {
        long elapsedRealtime = this.mClock.elapsedRealtime();
        if (shouldResetWhilePluggedInLocked(elapsedRealtime)) {
            android.util.Slog.i(TAG, "Resetting due to long plug in duration. elapsed time = " + elapsedRealtime + " ms, last plug in time = " + this.mBatteryPluggedInRealTimeMs + " ms, last reset time = " + (this.mRealtimeStartUs / 1000));
            resetAllStatsAndHistoryLocked(5);
        }
        scheduleNextResetWhilePluggedInCheck();
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void scheduleNextResetWhilePluggedInCheck() {
        if (this.mAlarmManager == null) {
            return;
        }
        long currentTimeMillis = this.mClock.currentTimeMillis() + (this.mConstants.RESET_WHILE_PLUGGED_IN_MINIMUM_DURATION_HOURS * 3600000);
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTimeInMillis(currentTimeMillis);
        calendar.set(14, 0);
        calendar.set(13, 0);
        calendar.set(12, 0);
        calendar.set(11, 2);
        final long timeInMillis = calendar.getTimeInMillis();
        if (timeInMillis < currentTimeMillis) {
            timeInMillis += 86400000;
        }
        final android.app.AlarmManager alarmManager = this.mAlarmManager;
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.power.stats.BatteryStatsImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.power.stats.BatteryStatsImpl.this.lambda$scheduleNextResetWhilePluggedInCheck$8(alarmManager, timeInMillis);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleNextResetWhilePluggedInCheck$8(android.app.AlarmManager alarmManager, long j) {
        alarmManager.setWindow(1, j, 3600000L, TAG, this.mLongPlugInAlarmHandler, this.mHandler);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean shouldResetWhilePluggedInLocked(long j) {
        return !this.mNoAutoReset && this.mSystemReady && this.mHistory.isResetEnabled() && j >= this.mBatteryPluggedInRealTimeMs + (((long) this.mConstants.RESET_WHILE_PLUGGED_IN_MINIMUM_DURATION_HOURS) * 3600000) && j >= (this.mRealtimeStartUs / 1000) + (((long) this.mConstants.RESET_WHILE_PLUGGED_IN_MINIMUM_DURATION_HOURS) * 3600000);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean shouldResetOnUnplugLocked(int i, int i2) {
        if (this.mNoAutoReset || !this.mSystemReady || !this.mHistory.isResetEnabled()) {
            return false;
        }
        if (!this.mBatteryStatsConfig.shouldResetOnUnplugHighBatteryLevel() || (i != 5 && i2 < 90)) {
            return (this.mBatteryStatsConfig.shouldResetOnUnplugAfterSignificantCharge() && this.mDischargePlugLevel < 20 && i2 >= 80) || getHighDischargeAmountSinceCharge() >= 200;
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v3, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r8v4 */
    @com.android.internal.annotations.GuardedBy({"this"})
    protected void setOnBatteryLocked(long j, long j2, boolean z, int i, int i2, int i3) {
        byte b;
        boolean z2;
        ?? r8;
        boolean z3;
        byte b2;
        android.os.Message obtainMessage = this.mHandler.obtainMessage(2);
        obtainMessage.arg1 = z ? 1 : 0;
        this.mHandler.sendMessage(obtainMessage);
        long j3 = j2 * 1000;
        long j4 = j * 1000;
        int i4 = this.mScreenState;
        if (!z) {
            this.mOnBatteryInternal = false;
            this.mOnBattery = false;
            pullPendingStateUpdatesLocked();
            this.mBatteryPluggedIn = true;
            this.mBatteryPluggedInRealTimeMs = j;
            this.mHistory.recordBatteryState(j, j2, i2, this.mBatteryPluggedIn);
            this.mDischargePlugLevel = i2;
            this.mDischargeCurrentLevel = i2;
            if (i2 < this.mDischargeUnplugLevel) {
                this.mLowDischargeAmountSinceCharge += (this.mDischargeUnplugLevel - i2) - 1;
                this.mHighDischargeAmountSinceCharge += this.mDischargeUnplugLevel - i2;
            }
            updateDischargeScreenLevelsLocked(i4, i4);
            updateTimeBasesLocked(false, i4, j3, j4);
            this.mChargeStepTracker.init();
            this.mLastChargeStepLevel = i2;
            this.mMaxChargeStepLevel = i2;
            this.mInitStepMode = this.mCurStepMode;
            this.mModStepMode = 0;
            scheduleNextResetWhilePluggedInCheck();
            b = false;
        } else {
            if (!shouldResetOnUnplugLocked(i, i2)) {
                z2 = true;
                r8 = 0;
                z3 = false;
                b2 = false;
            } else {
                android.util.Slog.i(TAG, "Resetting battery stats: level=" + i2 + " status=" + i + " dischargeLevel=" + this.mDischargePlugLevel + " lowAmount=" + getLowDischargeAmountSinceCharge() + " highAmount=" + getHighDischargeAmountSinceCharge());
                if (getLowDischargeAmountSinceCharge() >= 20) {
                    long uptimeMillis = android.os.SystemClock.uptimeMillis();
                    final android.os.Parcel obtain = android.os.Parcel.obtain();
                    writeSummaryToParcel(obtain, true);
                    final long uptimeMillis2 = android.os.SystemClock.uptimeMillis() - uptimeMillis;
                    com.android.internal.os.BackgroundThread.getHandler().post(new java.lang.Runnable() { // from class: com.android.server.power.stats.BatteryStatsImpl.6
                        @Override // java.lang.Runnable
                        public void run() {
                            android.os.Parcel parcel;
                            synchronized (com.android.server.power.stats.BatteryStatsImpl.this.mCheckinFile) {
                                long uptimeMillis3 = android.os.SystemClock.uptimeMillis();
                                java.io.FileOutputStream fileOutputStream = null;
                                try {
                                    try {
                                        fileOutputStream = com.android.server.power.stats.BatteryStatsImpl.this.mCheckinFile.startWrite();
                                        fileOutputStream.write(obtain.marshall());
                                        fileOutputStream.flush();
                                        com.android.server.power.stats.BatteryStatsImpl.this.mCheckinFile.finishWrite(fileOutputStream);
                                        com.android.internal.logging.EventLogTags.writeCommitSysConfigFile("batterystats-checkin", (uptimeMillis2 + android.os.SystemClock.uptimeMillis()) - uptimeMillis3);
                                        parcel = obtain;
                                    } catch (java.io.IOException e) {
                                        android.util.Slog.w("BatteryStats", "Error writing checkin battery statistics", e);
                                        com.android.server.power.stats.BatteryStatsImpl.this.mCheckinFile.failWrite(fileOutputStream);
                                        parcel = obtain;
                                    }
                                    parcel.recycle();
                                } catch (java.lang.Throwable th) {
                                    obtain.recycle();
                                    throw th;
                                }
                            }
                        }
                    });
                }
                r8 = 0;
                z2 = true;
                resetAllStatsLocked(j2, j, 3);
                if (i3 > 0 && i2 > 0) {
                    this.mEstimatedBatteryCapacityMah = (int) ((i3 / 1000) / (i2 / 100.0d));
                }
                this.mDischargeStepTracker.init();
                z3 = true;
                b2 = true;
            }
            if (this.mCharging) {
                setChargingLocked(r8);
            }
            this.mOnBatteryInternal = z2;
            this.mOnBattery = z2;
            this.mLastDischargeStepLevel = i2;
            this.mMinDischargeStepLevel = i2;
            this.mDischargeStepTracker.clearTime();
            this.mDailyDischargeStepTracker.clearTime();
            this.mInitStepMode = this.mCurStepMode;
            this.mModStepMode = r8;
            pullPendingStateUpdatesLocked();
            if (z3) {
                this.mHistory.startRecordingHistory(j, j2, z3);
                initActiveHistoryEventsLocked(j, j2);
            }
            this.mBatteryPluggedIn = r8;
            if (this.mAlarmManager != null) {
                final android.app.AlarmManager alarmManager = this.mAlarmManager;
                this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.power.stats.BatteryStatsImpl$$ExternalSyntheticLambda10
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.server.power.stats.BatteryStatsImpl.this.lambda$setOnBatteryLocked$9(alarmManager);
                    }
                });
            }
            this.mHistory.recordBatteryState(j, j2, i2, this.mBatteryPluggedIn);
            this.mDischargeUnplugLevel = i2;
            this.mDischargeCurrentLevel = i2;
            if (android.view.Display.isOnState(i4)) {
                this.mDischargeScreenOnUnplugLevel = i2;
                this.mDischargeScreenDozeUnplugLevel = r8;
                this.mDischargeScreenOffUnplugLevel = r8;
            } else if (android.view.Display.isDozeState(i4)) {
                this.mDischargeScreenOnUnplugLevel = r8;
                this.mDischargeScreenDozeUnplugLevel = i2;
                this.mDischargeScreenOffUnplugLevel = r8;
            } else {
                this.mDischargeScreenOnUnplugLevel = r8;
                this.mDischargeScreenDozeUnplugLevel = r8;
                this.mDischargeScreenOffUnplugLevel = i2;
            }
            this.mDischargeAmountScreenOn = r8;
            this.mDischargeAmountScreenDoze = r8;
            this.mDischargeAmountScreenOff = r8;
            updateTimeBasesLocked(true, i4, j3, j4);
            b = b2;
        }
        if ((b != false || this.mLastWriteTimeMs + 60000 < j) && this.mStatsFile != null && !this.mHistory.isReadOnly()) {
            writeAsyncLocked();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setOnBatteryLocked$9(android.app.AlarmManager alarmManager) {
        alarmManager.cancel(this.mLongPlugInAlarmHandler);
    }

    private void scheduleSyncExternalStatsLocked(java.lang.String str, int i) {
        if (this.mExternalSync != null) {
            this.mExternalSync.scheduleSync(str, i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void setBatteryStateLocked(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, long j, long j2, long j3, long j4) {
        boolean z;
        int max = java.lang.Math.max(0, i5);
        reportChangesToStatsLog(i, i3, i4);
        boolean isOnBattery = isOnBattery(i3, i);
        if (!this.mHaveBatteryLevel) {
            this.mHaveBatteryLevel = true;
            if (isOnBattery == this.mOnBattery) {
                this.mHistory.setPluggedInState(!isOnBattery);
            }
            this.mBatteryStatus = i;
            this.mBatteryLevel = i4;
            this.mBatteryChargeUah = i7;
            this.mHistory.setBatteryState(true, i, i4, i7);
            this.mLastDischargeStepLevel = i4;
            this.mLastChargeStepLevel = i4;
            this.mMinDischargeStepLevel = i4;
            this.mMaxChargeStepLevel = i4;
        } else if (this.mBatteryLevel != i4 || this.mOnBattery != isOnBattery) {
            recordDailyStatsIfNeededLocked(i4 >= 100 && isOnBattery, j4);
        }
        int i9 = this.mBatteryStatus;
        if (isOnBattery) {
            this.mDischargeCurrentLevel = i4;
            if (!this.mHistory.isRecordingHistory()) {
                this.mHistory.startRecordingHistory(j2, j3, true);
            }
        } else if (i4 < 96 && i != 1 && !this.mHistory.isRecordingHistory()) {
            this.mHistory.startRecordingHistory(j2, j3, true);
        }
        if (this.mDischargePlugLevel < 0) {
            this.mDischargePlugLevel = i4;
        }
        if (isOnBattery != this.mOnBattery) {
            this.mBatteryLevel = i4;
            this.mBatteryStatus = i;
            this.mBatteryHealth = i2;
            this.mBatteryPlugType = i3;
            this.mBatteryTemperature = max;
            this.mBatteryVoltageMv = i6;
            this.mHistory.setBatteryState(i, i4, i2, i3, max, i6, i7);
            if (i7 < this.mBatteryChargeUah) {
                long j5 = this.mBatteryChargeUah - i7;
                this.mDischargeCounter.addCountLocked(j5);
                this.mDischargeScreenOffCounter.addCountLocked(j5);
                if (android.view.Display.isDozeState(this.mScreenState)) {
                    this.mDischargeScreenDozeCounter.addCountLocked(j5);
                }
                if (this.mDeviceIdleMode == 1) {
                    this.mDischargeLightDozeCounter.addCountLocked(j5);
                } else if (this.mDeviceIdleMode == 2) {
                    this.mDischargeDeepDozeCounter.addCountLocked(j5);
                }
            }
            this.mBatteryChargeUah = i7;
            setOnBatteryLocked(j2, j3, isOnBattery, i9, i4, i7);
        } else {
            if (this.mBatteryLevel == i4) {
                z = false;
            } else {
                this.mBatteryLevel = i4;
                this.mExternalSync.scheduleSyncDueToBatteryLevelChange(this.mConstants.BATTERY_LEVEL_COLLECTION_DELAY_MS);
                z = true;
            }
            if (this.mBatteryStatus != i) {
                this.mBatteryStatus = i;
                z = true;
            }
            if (this.mBatteryHealth != i2) {
                this.mBatteryHealth = i2;
                z = true;
            }
            if (this.mBatteryPlugType != i3) {
                this.mBatteryPlugType = i3;
                z = true;
            }
            if (max >= this.mBatteryTemperature + 10 || max <= this.mBatteryTemperature - 10) {
                this.mBatteryTemperature = max;
                z = true;
            }
            if (i6 > this.mBatteryVoltageMv + 20 || i6 < this.mBatteryVoltageMv - 20) {
                this.mBatteryVoltageMv = i6;
                z = true;
            }
            if (i7 >= this.mBatteryChargeUah + 10 || i7 <= this.mBatteryChargeUah - 10) {
                if (i7 < this.mBatteryChargeUah) {
                    long j6 = this.mBatteryChargeUah - i7;
                    this.mDischargeCounter.addCountLocked(j6);
                    this.mDischargeScreenOffCounter.addCountLocked(j6);
                    if (android.view.Display.isDozeState(this.mScreenState)) {
                        this.mDischargeScreenDozeCounter.addCountLocked(j6);
                    }
                    if (this.mDeviceIdleMode != 1) {
                        if (this.mDeviceIdleMode == 2) {
                            this.mDischargeDeepDozeCounter.addCountLocked(j6);
                        }
                    } else {
                        this.mDischargeLightDozeCounter.addCountLocked(j6);
                    }
                }
                this.mBatteryChargeUah = i7;
                z = true;
            }
            long j7 = (this.mInitStepMode << 48) | (this.mModStepMode << 56) | ((i4 & 255) << 40);
            if (isOnBattery) {
                z |= setChargingLocked(false);
                if (this.mLastDischargeStepLevel != i4 && this.mMinDischargeStepLevel > i4) {
                    this.mDischargeStepTracker.addLevelSteps(this.mLastDischargeStepLevel - i4, j7, j2);
                    this.mDailyDischargeStepTracker.addLevelSteps(this.mLastDischargeStepLevel - i4, j7, j2);
                    this.mLastDischargeStepLevel = i4;
                    this.mMinDischargeStepLevel = i4;
                    this.mInitStepMode = this.mCurStepMode;
                    this.mModStepMode = 0;
                }
            } else {
                if (i4 >= this.mConstants.BATTERY_CHARGING_ENFORCE_LEVEL) {
                    z |= setChargingLocked(true);
                } else if (!this.mCharging) {
                    if (this.mLastChargeStepLevel < i4) {
                        if (!this.mHandler.hasCallbacks(this.mDeferSetCharging)) {
                            this.mHandler.postDelayed(this.mDeferSetCharging, this.mConstants.BATTERY_CHARGED_DELAY_MS);
                        }
                    } else if (this.mLastChargeStepLevel > i4) {
                        this.mHandler.removeCallbacks(this.mDeferSetCharging);
                    }
                } else if (this.mLastChargeStepLevel > i4) {
                    z |= setChargingLocked(false);
                }
                if (this.mLastChargeStepLevel != i4 && this.mMaxChargeStepLevel < i4) {
                    this.mChargeStepTracker.addLevelSteps(i4 - this.mLastChargeStepLevel, j7, j2);
                    this.mDailyChargeStepTracker.addLevelSteps(i4 - this.mLastChargeStepLevel, j7, j2);
                    this.mMaxChargeStepLevel = i4;
                    this.mInitStepMode = this.mCurStepMode;
                    this.mModStepMode = 0;
                }
                this.mLastChargeStepLevel = i4;
            }
            if (z) {
                this.mHistory.setBatteryState(this.mBatteryStatus, this.mBatteryLevel, this.mBatteryHealth, this.mBatteryPlugType, this.mBatteryTemperature, this.mBatteryVoltageMv, this.mBatteryChargeUah);
                this.mHistory.writeHistoryItem(j2, j3);
            }
        }
        if (!isOnBattery && (i == 5 || i == 1)) {
            this.mHistory.setHistoryRecordingEnabled(false);
        }
        this.mLastLearnedBatteryCapacityUah = i8;
        if (this.mMinLearnedBatteryCapacityUah != -1) {
            this.mMinLearnedBatteryCapacityUah = java.lang.Math.min(this.mMinLearnedBatteryCapacityUah, i8);
        } else {
            this.mMinLearnedBatteryCapacityUah = i8;
        }
        this.mMaxLearnedBatteryCapacityUah = java.lang.Math.max(this.mMaxLearnedBatteryCapacityUah, i8);
        this.mBatteryTimeToFullSeconds = j;
    }

    public static boolean isOnBattery(int i, int i2) {
        return i == 0 && i2 != 1;
    }

    private void reportChangesToStatsLog(int i, int i2, int i3) {
        if (!this.mHaveBatteryLevel || this.mBatteryStatus != i) {
            this.mFrameworkStatsLogger.chargingStateChanged(i);
        }
        if (!this.mHaveBatteryLevel || this.mBatteryPlugType != i2) {
            this.mFrameworkStatsLogger.pluggedStateChanged(i2);
        }
        if (!this.mHaveBatteryLevel || this.mBatteryLevel != i3) {
            this.mFrameworkStatsLogger.batteryLevelChanged(i3);
        }
    }

    public long getAwakeTimeBattery() {
        return getBatteryUptimeLocked(this.mClock.uptimeMillis());
    }

    public long getAwakeTimePlugged() {
        return (this.mClock.uptimeMillis() * 1000) - getAwakeTimeBattery();
    }

    public long computeUptime(long j, int i) {
        return this.mUptimeUs + (j - this.mUptimeStartUs);
    }

    public long computeRealtime(long j, int i) {
        return this.mRealtimeUs + (j - this.mRealtimeStartUs);
    }

    public long computeBatteryUptime(long j, int i) {
        return this.mOnBatteryTimeBase.computeUptime(j, i);
    }

    public long computeBatteryRealtime(long j, int i) {
        return this.mOnBatteryTimeBase.computeRealtime(j, i);
    }

    public long computeBatteryScreenOffUptime(long j, int i) {
        return this.mOnBatteryScreenOffTimeBase.computeUptime(j, i);
    }

    public long computeBatteryScreenOffRealtime(long j, int i) {
        return this.mOnBatteryScreenOffTimeBase.computeRealtime(j, i);
    }

    public long computeBatteryTimeRemaining(long j) {
        if (!this.mOnBattery || this.mDischargeStepTracker.mNumStepDurations < 1) {
            return -1L;
        }
        long computeTimePerLevel = this.mDischargeStepTracker.computeTimePerLevel();
        if (computeTimePerLevel <= 0) {
            return -1L;
        }
        return computeTimePerLevel * this.mBatteryLevel * 1000;
    }

    public android.os.BatteryStats.LevelStepTracker getDischargeLevelStepTracker() {
        return this.mDischargeStepTracker;
    }

    public android.os.BatteryStats.LevelStepTracker getDailyDischargeLevelStepTracker() {
        return this.mDailyDischargeStepTracker;
    }

    public long computeChargeTimeRemaining(long j) {
        if (this.mOnBattery) {
            return -1L;
        }
        if (this.mBatteryTimeToFullSeconds >= 0) {
            return this.mBatteryTimeToFullSeconds * 1000000;
        }
        if (this.mChargeStepTracker.mNumStepDurations < 1) {
            return -1L;
        }
        long computeTimePerLevel = this.mChargeStepTracker.computeTimePerLevel();
        if (computeTimePerLevel <= 0) {
            return -1L;
        }
        return computeTimePerLevel * (100 - this.mBatteryLevel) * 1000;
    }

    public android.os.connectivity.CellularBatteryStats getCellularBatteryStats() {
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime() * 1000;
        android.os.BatteryStats.ControllerActivityCounter modemControllerActivity = getModemControllerActivity();
        long countLocked = modemControllerActivity.getSleepTimeCounter().getCountLocked(0);
        long countLocked2 = modemControllerActivity.getIdleTimeCounter().getCountLocked(0);
        long countLocked3 = modemControllerActivity.getRxTimeCounter().getCountLocked(0);
        long countLocked4 = modemControllerActivity.getPowerCounter().getCountLocked(0);
        long countLocked5 = modemControllerActivity.getMonitoredRailChargeConsumedMaMs().getCountLocked(0);
        int i = android.os.BatteryStats.NUM_DATA_CONNECTION_TYPES;
        long[] jArr = new long[i];
        for (int i2 = 0; i2 < i; i2++) {
            jArr[i2] = getPhoneDataConnectionTime(i2, elapsedRealtime, 0) / 1000;
        }
        int i3 = CELL_SIGNAL_STRENGTH_LEVEL_COUNT;
        long[] jArr2 = new long[i3];
        for (int i4 = 0; i4 < i3; i4++) {
            jArr2[i4] = getPhoneSignalStrengthTime(i4, elapsedRealtime, 0) / 1000;
        }
        int min = java.lang.Math.min(MODEM_TX_POWER_LEVEL_COUNT, modemControllerActivity.getTxTimeCounters().length);
        long[] jArr3 = new long[min];
        for (int i5 = 0; i5 < min; i5++) {
            jArr3[i5] = modemControllerActivity.getTxTimeCounters()[i5].getCountLocked(0);
            long j = jArr3[i5];
        }
        return new android.os.connectivity.CellularBatteryStats(computeBatteryRealtime(elapsedRealtime, 0) / 1000, getMobileRadioActiveTime(elapsedRealtime, 0) / 1000, getNetworkActivityPackets(1, 0), getNetworkActivityBytes(1, 0), getNetworkActivityPackets(0, 0), getNetworkActivityBytes(0, 0), countLocked, countLocked2, countLocked3, java.lang.Long.valueOf(countLocked4), jArr, jArr2, jArr3, countLocked5);
    }

    public android.os.connectivity.WifiBatteryStats getWifiBatteryStats() {
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime() * 1000;
        android.os.BatteryStats.ControllerActivityCounter wifiControllerActivity = getWifiControllerActivity();
        long countLocked = wifiControllerActivity.getIdleTimeCounter().getCountLocked(0);
        long countLocked2 = wifiControllerActivity.getScanTimeCounter().getCountLocked(0);
        long countLocked3 = wifiControllerActivity.getRxTimeCounter().getCountLocked(0);
        long countLocked4 = wifiControllerActivity.getTxTimeCounters()[0].getCountLocked(0);
        long computeBatteryRealtime = (computeBatteryRealtime(android.os.SystemClock.elapsedRealtime() * 1000, 0) / 1000) - ((countLocked + countLocked3) + countLocked4);
        long countLocked5 = wifiControllerActivity.getPowerCounter().getCountLocked(0);
        long countLocked6 = wifiControllerActivity.getMonitoredRailChargeConsumedMaMs().getCountLocked(0);
        long j = 0;
        for (int i = 0; i < this.mUidStats.size(); i++) {
            j += this.mUidStats.valueAt(i).mWifiScanTimer.getCountLocked(0);
        }
        long[] jArr = new long[8];
        for (int i2 = 0; i2 < 8; i2++) {
            jArr[i2] = getWifiStateTime(i2, elapsedRealtime, 0) / 1000;
        }
        long[] jArr2 = new long[13];
        for (int i3 = 0; i3 < 13; i3++) {
            jArr2[i3] = getWifiSupplStateTime(i3, elapsedRealtime, 0) / 1000;
        }
        long[] jArr3 = new long[5];
        for (int i4 = 0; i4 < 5; i4++) {
            jArr3[i4] = getWifiSignalStrengthTime(i4, elapsedRealtime, 0) / 1000;
        }
        return new android.os.connectivity.WifiBatteryStats(computeBatteryRealtime(elapsedRealtime, 0) / 1000, getWifiActiveTime(elapsedRealtime, 0) / 1000, getNetworkActivityPackets(3, 0), getNetworkActivityBytes(3, 0), getNetworkActivityPackets(2, 0), getNetworkActivityBytes(2, 0), computeBatteryRealtime, countLocked2, countLocked, countLocked3, countLocked4, countLocked5, j, jArr, jArr3, jArr2, countLocked6);
    }

    public android.os.connectivity.GpsBatteryStats getGpsBatteryStats() {
        android.os.connectivity.GpsBatteryStats gpsBatteryStats = new android.os.connectivity.GpsBatteryStats();
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime() * 1000;
        gpsBatteryStats.setLoggingDurationMs(computeBatteryRealtime(elapsedRealtime, 0) / 1000);
        gpsBatteryStats.setEnergyConsumedMaMs(getGpsBatteryDrainMaMs());
        int length = this.mGpsSignalQualityTimer.length;
        long[] jArr = new long[length];
        for (int i = 0; i < length; i++) {
            jArr[i] = getGpsSignalQualityTime(i, elapsedRealtime, 0) / 1000;
        }
        gpsBatteryStats.setTimeInGpsSignalQualityLevel(jArr);
        return gpsBatteryStats;
    }

    public android.os.BatteryStats.LevelStepTracker getChargeLevelStepTracker() {
        return this.mChargeStepTracker;
    }

    public android.os.BatteryStats.LevelStepTracker getDailyChargeLevelStepTracker() {
        return this.mDailyChargeStepTracker;
    }

    public java.util.ArrayList<android.os.BatteryStats.PackageChange> getDailyPackageChanges() {
        return this.mDailyPackageChanges;
    }

    protected long getBatteryUptimeLocked(long j) {
        return this.mOnBatteryTimeBase.getUptime(j * 1000);
    }

    public long getBatteryUptime(long j) {
        return this.mOnBatteryTimeBase.getUptime(j);
    }

    public long getBatteryRealtime(long j) {
        return this.mOnBatteryTimeBase.getRealtime(j);
    }

    public int getDischargeStartLevel() {
        int dischargeStartLevelLocked;
        synchronized (this) {
            dischargeStartLevelLocked = getDischargeStartLevelLocked();
        }
        return dischargeStartLevelLocked;
    }

    public int getDischargeStartLevelLocked() {
        return this.mDischargeUnplugLevel;
    }

    public int getDischargeCurrentLevel() {
        int dischargeCurrentLevelLocked;
        synchronized (this) {
            dischargeCurrentLevelLocked = getDischargeCurrentLevelLocked();
        }
        return dischargeCurrentLevelLocked;
    }

    public int getDischargeCurrentLevelLocked() {
        return this.mDischargeCurrentLevel;
    }

    public int getLowDischargeAmountSinceCharge() {
        int i;
        synchronized (this) {
            try {
                i = this.mLowDischargeAmountSinceCharge;
                if (this.mOnBattery && this.mDischargeCurrentLevel < this.mDischargeUnplugLevel) {
                    i += (this.mDischargeUnplugLevel - this.mDischargeCurrentLevel) - 1;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public int getHighDischargeAmountSinceCharge() {
        int i;
        synchronized (this) {
            try {
                i = this.mHighDischargeAmountSinceCharge;
                if (this.mOnBattery && this.mDischargeCurrentLevel < this.mDischargeUnplugLevel) {
                    i += this.mDischargeUnplugLevel - this.mDischargeCurrentLevel;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public int getDischargeAmount(int i) {
        int dischargeStartLevel;
        if (i == 0) {
            dischargeStartLevel = getHighDischargeAmountSinceCharge();
        } else {
            dischargeStartLevel = getDischargeStartLevel() - getDischargeCurrentLevel();
        }
        if (dischargeStartLevel < 0) {
            return 0;
        }
        return dischargeStartLevel;
    }

    public int getDischargeAmountScreenOn() {
        int i;
        synchronized (this) {
            try {
                i = this.mDischargeAmountScreenOn;
                if (this.mOnBattery && android.view.Display.isOnState(this.mScreenState) && this.mDischargeCurrentLevel < this.mDischargeScreenOnUnplugLevel) {
                    i += this.mDischargeScreenOnUnplugLevel - this.mDischargeCurrentLevel;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public int getDischargeAmountScreenOnSinceCharge() {
        int i;
        synchronized (this) {
            try {
                i = this.mDischargeAmountScreenOnSinceCharge;
                if (this.mOnBattery && android.view.Display.isOnState(this.mScreenState) && this.mDischargeCurrentLevel < this.mDischargeScreenOnUnplugLevel) {
                    i += this.mDischargeScreenOnUnplugLevel - this.mDischargeCurrentLevel;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public int getDischargeAmountScreenOff() {
        int dischargeAmountScreenDoze;
        synchronized (this) {
            try {
                int i = this.mDischargeAmountScreenOff;
                if (this.mOnBattery && android.view.Display.isOffState(this.mScreenState) && this.mDischargeCurrentLevel < this.mDischargeScreenOffUnplugLevel) {
                    i += this.mDischargeScreenOffUnplugLevel - this.mDischargeCurrentLevel;
                }
                dischargeAmountScreenDoze = i + getDischargeAmountScreenDoze();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return dischargeAmountScreenDoze;
    }

    public int getDischargeAmountScreenOffSinceCharge() {
        int dischargeAmountScreenDozeSinceCharge;
        synchronized (this) {
            try {
                int i = this.mDischargeAmountScreenOffSinceCharge;
                if (this.mOnBattery && android.view.Display.isOffState(this.mScreenState) && this.mDischargeCurrentLevel < this.mDischargeScreenOffUnplugLevel) {
                    i += this.mDischargeScreenOffUnplugLevel - this.mDischargeCurrentLevel;
                }
                dischargeAmountScreenDozeSinceCharge = i + getDischargeAmountScreenDozeSinceCharge();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return dischargeAmountScreenDozeSinceCharge;
    }

    public int getDischargeAmountScreenDoze() {
        int i;
        synchronized (this) {
            try {
                i = this.mDischargeAmountScreenDoze;
                if (this.mOnBattery && android.view.Display.isDozeState(this.mScreenState) && this.mDischargeCurrentLevel < this.mDischargeScreenDozeUnplugLevel) {
                    i += this.mDischargeScreenDozeUnplugLevel - this.mDischargeCurrentLevel;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public int getDischargeAmountScreenDozeSinceCharge() {
        int i;
        synchronized (this) {
            try {
                i = this.mDischargeAmountScreenDozeSinceCharge;
                if (this.mOnBattery && android.view.Display.isDozeState(this.mScreenState) && this.mDischargeCurrentLevel < this.mDischargeScreenDozeUnplugLevel) {
                    i += this.mDischargeScreenDozeUnplugLevel - this.mDischargeCurrentLevel;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public long[] getSystemServiceTimeAtCpuSpeeds() {
        if (this.mBinderThreadCpuTimesUs == null) {
            return null;
        }
        return this.mBinderThreadCpuTimesUs.getCountsLocked(0);
    }

    public com.android.server.power.stats.BatteryStatsImpl.Uid getUidStatsLocked(int i) {
        return getUidStatsLocked(i, this.mClock.elapsedRealtime(), this.mClock.uptimeMillis());
    }

    public com.android.server.power.stats.BatteryStatsImpl.Uid getUidStatsLocked(int i, long j, long j2) {
        com.android.server.power.stats.BatteryStatsImpl.Uid uid = this.mUidStats.get(i);
        if (uid == null) {
            if (android.os.Process.isSdkSandboxUid(i)) {
                android.util.Log.wtf(TAG, "Tracking an SDK Sandbox UID");
            }
            com.android.server.power.stats.BatteryStatsImpl.Uid uid2 = new com.android.server.power.stats.BatteryStatsImpl.Uid(this, i, j, j2);
            this.mUidStats.put(i, uid2);
            return uid2;
        }
        return uid;
    }

    public com.android.server.power.stats.BatteryStatsImpl.Uid getAvailableUidStatsLocked(int i) {
        return this.mUidStats.get(i);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void onCleanupUserLocked(int i, long j) {
        this.mPendingRemovedUids.add(new com.android.server.power.stats.BatteryStatsImpl.UidToRemove(android.os.UserHandle.getUid(i, 0), android.os.UserHandle.getUid(i, 99999), j));
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void onUserRemovedLocked(int i) {
        if (this.mExternalSync != null) {
            this.mExternalSync.scheduleCleanupDueToRemovedUser(i);
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void clearRemovedUserUidsLocked(int i) {
        int uid = android.os.UserHandle.getUid(i, 0);
        int uid2 = android.os.UserHandle.getUid(i, 99999);
        this.mUidStats.put(uid, null);
        this.mUidStats.put(uid2, null);
        int indexOfKey = this.mUidStats.indexOfKey(uid);
        int indexOfKey2 = this.mUidStats.indexOfKey(uid2);
        for (int i2 = indexOfKey; i2 <= indexOfKey2; i2++) {
            com.android.server.power.stats.BatteryStatsImpl.Uid valueAt = this.mUidStats.valueAt(i2);
            if (valueAt != null) {
                valueAt.detachFromTimeBase();
            }
        }
        this.mUidStats.removeAtRange(indexOfKey, (indexOfKey2 - indexOfKey) + 1);
        removeCpuStatsForUidRangeLocked(uid, uid2);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void removeUidStatsLocked(int i, long j) {
        com.android.server.power.stats.BatteryStatsImpl.Uid uid = this.mUidStats.get(i);
        if (uid != null) {
            uid.detachFromTimeBase();
        }
        this.mUidStats.remove(i);
        this.mPendingRemovedUids.add(new com.android.server.power.stats.BatteryStatsImpl.UidToRemove(this, i, j));
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"this"})
    public void removeCpuStatsForUidRangeLocked(int i, int i2) {
        if (i == i2) {
            this.mCpuUidUserSysTimeReader.removeUid(i);
            this.mCpuUidFreqTimeReader.removeUid(i);
            if (this.mConstants.TRACK_CPU_ACTIVE_CLUSTER_TIME) {
                this.mCpuUidActiveTimeReader.removeUid(i);
                this.mCpuUidClusterTimeReader.removeUid(i);
            }
            if (this.mKernelSingleUidTimeReader != null) {
                this.mKernelSingleUidTimeReader.removeUid(i);
            }
            this.mNumUidsRemoved++;
            return;
        }
        if (i < i2) {
            this.mCpuUidFreqTimeReader.removeUidsInRange(i, i2);
            this.mCpuUidUserSysTimeReader.removeUidsInRange(i, i2);
            if (this.mConstants.TRACK_CPU_ACTIVE_CLUSTER_TIME) {
                this.mCpuUidActiveTimeReader.removeUidsInRange(i, i2);
                this.mCpuUidClusterTimeReader.removeUidsInRange(i, i2);
            }
            if (this.mKernelSingleUidTimeReader != null) {
                this.mKernelSingleUidTimeReader.removeUidsInRange(i, i2);
            }
            this.mPowerStatsUidResolver.releaseUidsInRange(i, i2);
            this.mNumUidsRemoved++;
            return;
        }
        android.util.Slog.w(TAG, "End UID " + i2 + " is smaller than start UID " + i);
    }

    public com.android.server.power.stats.BatteryStatsImpl.Uid.Proc getProcessStatsLocked(int i, java.lang.String str, long j, long j2) {
        return getUidStatsLocked(mapUid(i), j, j2).getProcessStatsLocked(str);
    }

    public com.android.server.power.stats.BatteryStatsImpl.Uid.Pkg getPackageStatsLocked(int i, java.lang.String str) {
        return getPackageStatsLocked(i, str, this.mClock.elapsedRealtime(), this.mClock.uptimeMillis());
    }

    public com.android.server.power.stats.BatteryStatsImpl.Uid.Pkg getPackageStatsLocked(int i, java.lang.String str, long j, long j2) {
        return getUidStatsLocked(mapUid(i), j, j2).getPackageStatsLocked(str);
    }

    public com.android.server.power.stats.BatteryStatsImpl.Uid.Pkg.Serv getServiceStatsLocked(int i, java.lang.String str, java.lang.String str2, long j, long j2) {
        return getUidStatsLocked(mapUid(i), j, j2).getServiceStatsLocked(str, str2);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void shutdownLocked() {
        this.mHistory.recordShutdownEvent(this.mClock.elapsedRealtime(), this.mClock.uptimeMillis(), this.mClock.currentTimeMillis());
        writeSyncLocked();
        this.mShuttingDown = true;
    }

    public boolean isProcessStateDataAvailable() {
        boolean trackPerProcStateCpuTimes;
        synchronized (this) {
            trackPerProcStateCpuTimes = trackPerProcStateCpuTimes();
        }
        return trackPerProcStateCpuTimes;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"this"})
    public boolean trackPerProcStateCpuTimes() {
        return this.mCpuUidFreqTimeReader.isFastCpuTimesReader();
    }

    public void setPowerStatsCollectorEnabled(boolean z) {
        synchronized (this) {
            this.mPowerStatsCollectorEnabled = z;
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void systemServicesReady(android.content.Context context) {
        this.mConstants.startObserving(context.getContentResolver());
        registerUsbStateReceiver(context);
        synchronized (this) {
            try {
                this.mAlarmManager = (android.app.AlarmManager) context.getSystemService(android.app.AlarmManager.class);
                if (this.mBatteryPluggedIn) {
                    scheduleNextResetWhilePluggedInCheck();
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void initEnergyConsumerStatsLocked(@android.annotation.Nullable boolean[] zArr, java.lang.String[] strArr) {
        int length = this.mPerDisplayBatteryStats.length;
        for (int i = 0; i < length; i++) {
            this.mPerDisplayBatteryStats[i].screenStateAtLastEnergyMeasurement = this.mPerDisplayBatteryStats[i].screenState;
        }
        if (zArr != null) {
            com.android.internal.power.EnergyConsumerStats.Config config = new com.android.internal.power.EnergyConsumerStats.Config(zArr, strArr, SUPPORTED_PER_PROCESS_STATE_STANDARD_ENERGY_BUCKETS, getBatteryConsumerProcessStateNames());
            if (this.mEnergyConsumerStatsConfig != null && !this.mEnergyConsumerStatsConfig.isCompatible(config)) {
                resetAllStatsLocked(android.os.SystemClock.uptimeMillis(), android.os.SystemClock.elapsedRealtime(), 4);
            }
            this.mEnergyConsumerStatsConfig = config;
            this.mGlobalEnergyConsumerStats = new com.android.internal.power.EnergyConsumerStats(config);
            if (zArr[5]) {
                this.mBluetoothPowerCalculator = new com.android.server.power.stats.BluetoothPowerCalculator(this.mPowerProfile);
            }
            if (zArr[7]) {
                this.mMobileRadioPowerCalculator = new com.android.server.power.stats.MobileRadioPowerCalculator(this.mPowerProfile);
            }
            if (zArr[4]) {
                this.mWifiPowerCalculator = new com.android.server.power.stats.WifiPowerCalculator(this.mPowerProfile);
                return;
            }
            return;
        }
        if (this.mEnergyConsumerStatsConfig != null) {
            resetAllStatsLocked(android.os.SystemClock.uptimeMillis(), android.os.SystemClock.elapsedRealtime(), 4);
        }
        this.mEnergyConsumerStatsConfig = null;
        this.mGlobalEnergyConsumerStats = null;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private boolean isMobileRadioEnergyConsumerSupportedLocked() {
        if (this.mGlobalEnergyConsumerStats == null) {
            return false;
        }
        return this.mGlobalEnergyConsumerStats.isStandardBucketSupported(7);
    }

    @android.annotation.NonNull
    private static java.lang.String[] getBatteryConsumerProcessStateNames() {
        java.lang.String[] strArr = new java.lang.String[5];
        for (int i = 0; i < 5; i++) {
            strArr[i] = android.os.BatteryConsumer.processStateToString(i);
        }
        return strArr;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public int getBatteryVoltageMvLocked() {
        return this.mBatteryVoltageMv;
    }

    @com.android.internal.annotations.VisibleForTesting
    public final class Constants extends android.database.ContentObserver {
        private static final int DEFAULT_BATTERY_CHARGED_DELAY_MS = 900000;
        private static final int DEFAULT_BATTERY_CHARGING_ENFORCE_LEVEL = 90;
        private static final long DEFAULT_BATTERY_LEVEL_COLLECTION_DELAY_MS = 300000;
        private static final long DEFAULT_EXTERNAL_STATS_COLLECTION_RATE_LIMIT_MS = 600000;
        private static final long DEFAULT_KERNEL_UID_READERS_THROTTLE_TIME = 1000;
        private static final int DEFAULT_MAX_HISTORY_BUFFER_KB = 128;
        private static final int DEFAULT_MAX_HISTORY_BUFFER_LOW_RAM_DEVICE_KB = 64;
        private static final int DEFAULT_MAX_HISTORY_FILES = 32;
        private static final int DEFAULT_MAX_HISTORY_FILES_LOW_RAM_DEVICE = 64;
        private static final int DEFAULT_PER_UID_MODEM_MODEL = 2;
        private static final boolean DEFAULT_PHONE_ON_EXTERNAL_STATS_COLLECTION = true;
        private static final long DEFAULT_PROC_STATE_CHANGE_COLLECTION_DELAY_MS = 60000;
        private static final int DEFAULT_RESET_WHILE_PLUGGED_IN_MINIMUM_DURATION_HOURS = 47;
        private static final boolean DEFAULT_TRACK_CPU_ACTIVE_CLUSTER_TIME = true;
        private static final long DEFAULT_UID_REMOVE_DELAY_MS = 300000;
        public static final java.lang.String KEY_BATTERY_CHARGED_DELAY_MS = "battery_charged_delay_ms";
        public static final java.lang.String KEY_BATTERY_CHARGING_ENFORCE_LEVEL = "battery_charging_enforce_level";
        public static final java.lang.String KEY_BATTERY_LEVEL_COLLECTION_DELAY_MS = "battery_level_collection_delay_ms";
        public static final java.lang.String KEY_EXTERNAL_STATS_COLLECTION_RATE_LIMIT_MS = "external_stats_collection_rate_limit_ms";
        public static final java.lang.String KEY_KERNEL_UID_READERS_THROTTLE_TIME = "kernel_uid_readers_throttle_time";
        public static final java.lang.String KEY_MAX_HISTORY_BUFFER_KB = "max_history_buffer_kb";
        public static final java.lang.String KEY_MAX_HISTORY_FILES = "max_history_files";
        public static final java.lang.String KEY_PER_UID_MODEM_POWER_MODEL = "per_uid_modem_power_model";
        public static final java.lang.String KEY_PHONE_ON_EXTERNAL_STATS_COLLECTION = "phone_on_external_stats_collection";
        public static final java.lang.String KEY_PROC_STATE_CHANGE_COLLECTION_DELAY_MS = "procstate_change_collection_delay_ms";
        public static final java.lang.String KEY_RESET_WHILE_PLUGGED_IN_MINIMUM_DURATION_HOURS = "reset_while_plugged_in_minimum_duration_hours";
        public static final java.lang.String KEY_TRACK_CPU_ACTIVE_CLUSTER_TIME = "track_cpu_active_cluster_time";
        public static final java.lang.String KEY_UID_REMOVE_DELAY_MS = "uid_remove_delay_ms";
        public static final java.lang.String PER_UID_MODEM_POWER_MODEL_MOBILE_RADIO_ACTIVE_TIME_NAME = "mobile_radio_active_time";
        public static final java.lang.String PER_UID_MODEM_POWER_MODEL_MODEM_ACTIVITY_INFO_RX_TX_NAME = "modem_activity_info_rx_tx";
        public int BATTERY_CHARGED_DELAY_MS;
        public int BATTERY_CHARGING_ENFORCE_LEVEL;
        public long BATTERY_LEVEL_COLLECTION_DELAY_MS;
        public long EXTERNAL_STATS_COLLECTION_RATE_LIMIT_MS;
        public long KERNEL_UID_READERS_THROTTLE_TIME;
        public int MAX_HISTORY_BUFFER;
        public int MAX_HISTORY_FILES;
        public int PER_UID_MODEM_MODEL;
        public boolean PHONE_ON_EXTERNAL_STATS_COLLECTION;
        public long PROC_STATE_CHANGE_COLLECTION_DELAY_MS;
        public int RESET_WHILE_PLUGGED_IN_MINIMUM_DURATION_HOURS;
        public boolean TRACK_CPU_ACTIVE_CLUSTER_TIME;
        public long UID_REMOVE_DELAY_MS;
        private final android.util.KeyValueListParser mParser;
        private android.content.ContentResolver mResolver;

        public java.lang.String getPerUidModemModelName(int i) {
            switch (i) {
                case 1:
                    return PER_UID_MODEM_POWER_MODEL_MOBILE_RADIO_ACTIVE_TIME_NAME;
                case 2:
                    return PER_UID_MODEM_POWER_MODEL_MODEM_ACTIVITY_INFO_RX_TX_NAME;
                default:
                    android.util.Slog.w(com.android.server.power.stats.BatteryStatsImpl.TAG, "Unexpected per uid modem model (" + i + ")");
                    return "unknown_" + i;
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public int getPerUidModemModel(java.lang.String str) {
            char c;
            switch (str.hashCode()) {
                case -615381273:
                    if (str.equals(PER_UID_MODEM_POWER_MODEL_MODEM_ACTIVITY_INFO_RX_TX_NAME)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 426026949:
                    if (str.equals(PER_UID_MODEM_POWER_MODEL_MOBILE_RADIO_ACTIVE_TIME_NAME)) {
                        c = 0;
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
                    break;
                case 1:
                    break;
                default:
                    android.util.Slog.w(com.android.server.power.stats.BatteryStatsImpl.TAG, "Unexpected per uid modem model name (" + str + ")");
                    break;
            }
            return 2;
        }

        public Constants(android.os.Handler handler) {
            super(handler);
            this.TRACK_CPU_ACTIVE_CLUSTER_TIME = true;
            this.UID_REMOVE_DELAY_MS = com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS;
            this.EXTERNAL_STATS_COLLECTION_RATE_LIMIT_MS = 600000L;
            this.BATTERY_LEVEL_COLLECTION_DELAY_MS = com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS;
            this.PROC_STATE_CHANGE_COLLECTION_DELAY_MS = 60000L;
            this.BATTERY_CHARGED_DELAY_MS = DEFAULT_BATTERY_CHARGED_DELAY_MS;
            this.BATTERY_CHARGING_ENFORCE_LEVEL = 90;
            this.PER_UID_MODEM_MODEL = 2;
            this.PHONE_ON_EXTERNAL_STATS_COLLECTION = true;
            this.RESET_WHILE_PLUGGED_IN_MINIMUM_DURATION_HOURS = 47;
            this.mParser = new android.util.KeyValueListParser(',');
            if (android.os.BatteryStats.isLowRamDevice()) {
                this.MAX_HISTORY_FILES = 64;
                this.MAX_HISTORY_BUFFER = 65536;
            } else {
                this.MAX_HISTORY_FILES = 32;
                this.MAX_HISTORY_BUFFER = 131072;
            }
        }

        public void startObserving(android.content.ContentResolver contentResolver) {
            this.mResolver = contentResolver;
            this.mResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("battery_stats_constants"), false, this);
            this.mResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("battery_charging_state_update_delay"), false, this);
            this.mResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("battery_charging_state_enforce_level"), false, this);
            updateConstants();
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            if (uri.equals(android.provider.Settings.Global.getUriFor("battery_charging_state_update_delay"))) {
                synchronized (com.android.server.power.stats.BatteryStatsImpl.this) {
                    updateBatteryChargedDelayMsLocked();
                }
            } else {
                if (uri.equals(android.provider.Settings.Global.getUriFor("battery_charging_state_enforce_level"))) {
                    synchronized (com.android.server.power.stats.BatteryStatsImpl.this) {
                        updateBatteryChargingEnforceLevelLocked();
                    }
                    return;
                }
                updateConstants();
            }
        }

        private void updateConstants() {
            synchronized (com.android.server.power.stats.BatteryStatsImpl.this) {
                try {
                    this.mParser.setString(android.provider.Settings.Global.getString(this.mResolver, "battery_stats_constants"));
                } catch (java.lang.IllegalArgumentException e) {
                    android.util.Slog.e(com.android.server.power.stats.BatteryStatsImpl.TAG, "Bad batterystats settings", e);
                }
                this.TRACK_CPU_ACTIVE_CLUSTER_TIME = this.mParser.getBoolean(KEY_TRACK_CPU_ACTIVE_CLUSTER_TIME, true);
                updateKernelUidReadersThrottleTime(this.KERNEL_UID_READERS_THROTTLE_TIME, this.mParser.getLong(KEY_KERNEL_UID_READERS_THROTTLE_TIME, 1000L));
                updateUidRemoveDelay(this.mParser.getLong(KEY_UID_REMOVE_DELAY_MS, com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS));
                this.EXTERNAL_STATS_COLLECTION_RATE_LIMIT_MS = this.mParser.getLong(KEY_EXTERNAL_STATS_COLLECTION_RATE_LIMIT_MS, 600000L);
                this.BATTERY_LEVEL_COLLECTION_DELAY_MS = this.mParser.getLong(KEY_BATTERY_LEVEL_COLLECTION_DELAY_MS, com.android.server.backup.BackupAgentTimeoutParameters.DEFAULT_FULL_BACKUP_AGENT_TIMEOUT_MILLIS);
                this.PROC_STATE_CHANGE_COLLECTION_DELAY_MS = this.mParser.getLong(KEY_PROC_STATE_CHANGE_COLLECTION_DELAY_MS, 60000L);
                int i = 64;
                this.MAX_HISTORY_FILES = this.mParser.getInt(KEY_MAX_HISTORY_FILES, android.os.BatteryStats.isLowRamDevice() ? 64 : 32);
                android.util.KeyValueListParser keyValueListParser = this.mParser;
                if (!android.os.BatteryStats.isLowRamDevice()) {
                    i = 128;
                }
                this.MAX_HISTORY_BUFFER = keyValueListParser.getInt(KEY_MAX_HISTORY_BUFFER_KB, i) * 1024;
                this.PER_UID_MODEM_MODEL = getPerUidModemModel(this.mParser.getString(KEY_PER_UID_MODEM_POWER_MODEL, ""));
                this.PHONE_ON_EXTERNAL_STATS_COLLECTION = this.mParser.getBoolean(KEY_PHONE_ON_EXTERNAL_STATS_COLLECTION, true);
                this.RESET_WHILE_PLUGGED_IN_MINIMUM_DURATION_HOURS = this.mParser.getInt(KEY_RESET_WHILE_PLUGGED_IN_MINIMUM_DURATION_HOURS, 47);
                updateBatteryChargedDelayMsLocked();
                updateBatteryChargingEnforceLevelLocked();
                onChange();
            }
        }

        @com.android.internal.annotations.VisibleForTesting
        public void onChange() {
            com.android.server.power.stats.BatteryStatsImpl.this.mHistory.setMaxHistoryFiles(this.MAX_HISTORY_FILES);
            com.android.server.power.stats.BatteryStatsImpl.this.mHistory.setMaxHistoryBufferSize(this.MAX_HISTORY_BUFFER);
        }

        private void updateBatteryChargedDelayMsLocked() {
            int i = android.provider.Settings.Global.getInt(this.mResolver, "battery_charging_state_update_delay", -1);
            if (i < 0) {
                i = this.mParser.getInt(KEY_BATTERY_CHARGED_DELAY_MS, DEFAULT_BATTERY_CHARGED_DELAY_MS);
            }
            this.BATTERY_CHARGED_DELAY_MS = i;
            if (com.android.server.power.stats.BatteryStatsImpl.this.mHandler.hasCallbacks(com.android.server.power.stats.BatteryStatsImpl.this.mDeferSetCharging)) {
                com.android.server.power.stats.BatteryStatsImpl.this.mHandler.removeCallbacks(com.android.server.power.stats.BatteryStatsImpl.this.mDeferSetCharging);
                com.android.server.power.stats.BatteryStatsImpl.this.mHandler.postDelayed(com.android.server.power.stats.BatteryStatsImpl.this.mDeferSetCharging, this.BATTERY_CHARGED_DELAY_MS);
            }
        }

        private void updateBatteryChargingEnforceLevelLocked() {
            int i = this.BATTERY_CHARGING_ENFORCE_LEVEL;
            int i2 = android.provider.Settings.Global.getInt(this.mResolver, "battery_charging_state_enforce_level", -1);
            if (i2 < 0) {
                i2 = this.mParser.getInt(KEY_BATTERY_CHARGING_ENFORCE_LEVEL, 90);
            }
            this.BATTERY_CHARGING_ENFORCE_LEVEL = i2;
            if (this.BATTERY_CHARGING_ENFORCE_LEVEL <= com.android.server.power.stats.BatteryStatsImpl.this.mLastChargeStepLevel && com.android.server.power.stats.BatteryStatsImpl.this.mLastChargeStepLevel < i) {
                com.android.server.power.stats.BatteryStatsImpl.this.setChargingLocked(true);
            }
        }

        private void updateKernelUidReadersThrottleTime(long j, long j2) {
            this.KERNEL_UID_READERS_THROTTLE_TIME = j2;
            if (j != j2) {
                com.android.server.power.stats.BatteryStatsImpl.this.mCpuUidUserSysTimeReader.setThrottle(this.KERNEL_UID_READERS_THROTTLE_TIME);
                com.android.server.power.stats.BatteryStatsImpl.this.mCpuUidFreqTimeReader.setThrottle(this.KERNEL_UID_READERS_THROTTLE_TIME);
                com.android.server.power.stats.BatteryStatsImpl.this.mCpuUidActiveTimeReader.setThrottle(this.KERNEL_UID_READERS_THROTTLE_TIME);
                com.android.server.power.stats.BatteryStatsImpl.this.mCpuUidClusterTimeReader.setThrottle(this.KERNEL_UID_READERS_THROTTLE_TIME);
            }
        }

        @com.android.internal.annotations.GuardedBy({"BatteryStatsImpl.this"})
        private void updateUidRemoveDelay(long j) {
            this.UID_REMOVE_DELAY_MS = j;
            com.android.server.power.stats.BatteryStatsImpl.this.clearPendingRemovedUidsLocked();
        }

        public void dumpLocked(java.io.PrintWriter printWriter) {
            printWriter.print(KEY_TRACK_CPU_ACTIVE_CLUSTER_TIME);
            printWriter.print("=");
            printWriter.println(this.TRACK_CPU_ACTIVE_CLUSTER_TIME);
            printWriter.print(KEY_KERNEL_UID_READERS_THROTTLE_TIME);
            printWriter.print("=");
            printWriter.println(this.KERNEL_UID_READERS_THROTTLE_TIME);
            printWriter.print(KEY_EXTERNAL_STATS_COLLECTION_RATE_LIMIT_MS);
            printWriter.print("=");
            printWriter.println(this.EXTERNAL_STATS_COLLECTION_RATE_LIMIT_MS);
            printWriter.print(KEY_BATTERY_LEVEL_COLLECTION_DELAY_MS);
            printWriter.print("=");
            printWriter.println(this.BATTERY_LEVEL_COLLECTION_DELAY_MS);
            printWriter.print(KEY_PROC_STATE_CHANGE_COLLECTION_DELAY_MS);
            printWriter.print("=");
            printWriter.println(this.PROC_STATE_CHANGE_COLLECTION_DELAY_MS);
            printWriter.print(KEY_MAX_HISTORY_FILES);
            printWriter.print("=");
            printWriter.println(this.MAX_HISTORY_FILES);
            printWriter.print(KEY_MAX_HISTORY_BUFFER_KB);
            printWriter.print("=");
            printWriter.println(this.MAX_HISTORY_BUFFER / 1024);
            printWriter.print(KEY_BATTERY_CHARGED_DELAY_MS);
            printWriter.print("=");
            printWriter.println(this.BATTERY_CHARGED_DELAY_MS);
            printWriter.print(KEY_BATTERY_CHARGING_ENFORCE_LEVEL);
            printWriter.print("=");
            printWriter.println(this.BATTERY_CHARGING_ENFORCE_LEVEL);
            printWriter.print(KEY_PER_UID_MODEM_POWER_MODEL);
            printWriter.print("=");
            printWriter.println(getPerUidModemModelName(this.PER_UID_MODEM_MODEL));
            printWriter.print(KEY_PHONE_ON_EXTERNAL_STATS_COLLECTION);
            printWriter.print("=");
            printWriter.println(this.PHONE_ON_EXTERNAL_STATS_COLLECTION);
            printWriter.print(KEY_RESET_WHILE_PLUGGED_IN_MINIMUM_DURATION_HOURS);
            printWriter.print("=");
            printWriter.println(this.RESET_WHILE_PLUGGED_IN_MINIMUM_DURATION_HOURS);
        }
    }

    public long getExternalStatsCollectionRateLimitMs() {
        long j;
        synchronized (this) {
            j = this.mConstants.EXTERNAL_STATS_COLLECTION_RATE_LIMIT_MS;
        }
        return j;
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void dumpConstantsLocked(java.io.PrintWriter printWriter) {
        java.io.PrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter, "    ");
        indentingPrintWriter.println("BatteryStats constants:");
        indentingPrintWriter.increaseIndent();
        this.mConstants.dumpLocked(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void dumpCpuStatsLocked(java.io.PrintWriter printWriter) {
        long j;
        int size = this.mUidStats.size();
        printWriter.println("Per UID CPU user & system time in ms:");
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            int keyAt = this.mUidStats.keyAt(i2);
            com.android.server.power.stats.BatteryStatsImpl.Uid uid = this.mUidStats.get(keyAt);
            printWriter.print("  ");
            printWriter.print(keyAt);
            printWriter.print(": ");
            printWriter.print(uid.getUserCpuTimeUs(0) / 1000);
            printWriter.print(" ");
            printWriter.println(uid.getSystemCpuTimeUs(0) / 1000);
        }
        printWriter.println("Per UID CPU active time in ms:");
        int i3 = 0;
        while (true) {
            j = 0;
            if (i3 >= size) {
                break;
            }
            int keyAt2 = this.mUidStats.keyAt(i3);
            com.android.server.power.stats.BatteryStatsImpl.Uid uid2 = this.mUidStats.get(keyAt2);
            if (uid2.getCpuActiveTime() > 0) {
                printWriter.print("  ");
                printWriter.print(keyAt2);
                printWriter.print(": ");
                printWriter.println(uid2.getCpuActiveTime());
            }
            i3++;
        }
        printWriter.println("Per UID CPU cluster time in ms:");
        for (int i4 = 0; i4 < size; i4++) {
            int keyAt3 = this.mUidStats.keyAt(i4);
            long[] cpuClusterTimes = this.mUidStats.get(keyAt3).getCpuClusterTimes();
            if (cpuClusterTimes != null) {
                printWriter.print("  ");
                printWriter.print(keyAt3);
                printWriter.print(": ");
                printWriter.println(java.util.Arrays.toString(cpuClusterTimes));
            }
        }
        printWriter.println("Per UID CPU frequency time in ms:");
        for (int i5 = 0; i5 < size; i5++) {
            int keyAt4 = this.mUidStats.keyAt(i5);
            long[] cpuFreqTimes = this.mUidStats.get(keyAt4).getCpuFreqTimes(0);
            if (cpuFreqTimes != null) {
                printWriter.print("  ");
                printWriter.print(keyAt4);
                printWriter.print(": ");
                printWriter.println(java.util.Arrays.toString(cpuFreqTimes));
            }
        }
        com.android.server.power.optimization.Flags.disableSystemServicePowerAttr();
        updateSystemServiceCallStats();
        if (this.mBinderThreadCpuTimesUs != null) {
            printWriter.println("Per UID System server binder time in ms:");
            long[] systemServiceTimeAtCpuSpeeds = getSystemServiceTimeAtCpuSpeeds();
            while (i < size) {
                int keyAt5 = this.mUidStats.keyAt(i);
                double proportionalSystemServiceUsage = this.mUidStats.get(keyAt5).getProportionalSystemServiceUsage();
                for (int length = systemServiceTimeAtCpuSpeeds.length - 1; length >= 0; length--) {
                    j = (long) (j + (systemServiceTimeAtCpuSpeeds[length] * proportionalSystemServiceUsage));
                }
                printWriter.print("  ");
                printWriter.print(keyAt5);
                printWriter.print(": ");
                printWriter.println(j / 1000);
                i++;
                j = 0;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void dumpEnergyConsumerStatsLocked(java.io.PrintWriter printWriter) {
        printWriter.printf("On-battery energy consumer stats (microcoulombs) \n", new java.lang.Object[0]);
        if (this.mGlobalEnergyConsumerStats == null) {
            printWriter.printf("    Not supported on this device.\n", new java.lang.Object[0]);
            return;
        }
        dumpEnergyConsumerStatsLocked(printWriter, "global usage", this.mGlobalEnergyConsumerStats);
        int size = this.mUidStats.size();
        for (int i = 0; i < size; i++) {
            com.android.server.power.stats.BatteryStatsImpl.Uid uid = this.mUidStats.get(this.mUidStats.keyAt(i));
            dumpEnergyConsumerStatsLocked(printWriter, "uid " + uid.mUid, uid.mUidEnergyConsumerStats);
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void dumpEnergyConsumerStatsLocked(java.io.PrintWriter printWriter, java.lang.String str, com.android.internal.power.EnergyConsumerStats energyConsumerStats) {
        if (energyConsumerStats == null) {
            return;
        }
        android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter, "    ");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printf("%s:\n", new java.lang.Object[]{str});
        indentingPrintWriter.increaseIndent();
        energyConsumerStats.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void dumpPowerProfileLocked(java.io.PrintWriter printWriter) {
        android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter, "    ");
        indentingPrintWriter.printf("Power Profile: \n", new java.lang.Object[0]);
        indentingPrintWriter.increaseIndent();
        this.mPowerProfile.dump(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
    }

    public void schedulePowerStatsSampleCollection() {
        if (this.mCpuPowerStatsCollector == null) {
            return;
        }
        this.mCpuPowerStatsCollector.forceSchedule();
    }

    public void collectPowerStatsSamples() {
        schedulePowerStatsSampleCollection();
        android.os.ConditionVariable conditionVariable = new android.os.ConditionVariable();
        android.os.Handler handler = this.mHandler;
        java.util.Objects.requireNonNull(conditionVariable);
        handler.post(new com.android.server.power.stats.BatteryStatsImpl$$ExternalSyntheticLambda11(conditionVariable));
        conditionVariable.block();
    }

    public void dumpStatsSample(java.io.PrintWriter printWriter) {
        this.mCpuPowerStatsCollector.collectAndDump(printWriter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$10() {
        synchronized (this) {
            writeSyncLocked();
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void writeAsyncLocked() {
        com.android.internal.os.BackgroundThread.getHandler().removeCallbacks(this.mWriteAsyncRunnable);
        com.android.internal.os.BackgroundThread.getHandler().post(this.mWriteAsyncRunnable);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void writeSyncLocked() {
        com.android.internal.os.BackgroundThread.getHandler().removeCallbacks(this.mWriteAsyncRunnable);
        writeStatsLocked();
        writeHistoryLocked();
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    private void writeStatsLocked() {
        if (this.mStatsFile == null) {
            android.util.Slog.w(TAG, "writeStatsLocked: no file associated with this instance");
            return;
        }
        if (this.mShuttingDown) {
            return;
        }
        android.os.Parcel obtain = android.os.Parcel.obtain();
        try {
            android.os.SystemClock.uptimeMillis();
            writeSummaryToParcel(obtain, false);
            this.mLastWriteTimeMs = this.mClock.elapsedRealtime();
            writeParcelToFileLocked(obtain, this.mStatsFile);
        } finally {
            obtain.recycle();
        }
    }

    private void writeHistoryLocked() {
        if (this.mShuttingDown) {
            return;
        }
        this.mHistory.writeHistory();
    }

    private void writeParcelToFileLocked(android.os.Parcel parcel, android.util.AtomicFile atomicFile) {
        this.mWriteLock.lock();
        java.io.FileOutputStream fileOutputStream = null;
        try {
            try {
                long uptimeMillis = android.os.SystemClock.uptimeMillis();
                fileOutputStream = atomicFile.startWrite();
                fileOutputStream.write(parcel.marshall());
                fileOutputStream.flush();
                atomicFile.finishWrite(fileOutputStream);
                com.android.internal.logging.EventLogTags.writeCommitSysConfigFile("batterystats", android.os.SystemClock.uptimeMillis() - uptimeMillis);
            } catch (java.io.IOException e) {
                android.util.Slog.w(TAG, "Error writing battery statistics", e);
                atomicFile.failWrite(fileOutputStream);
            }
        } finally {
            this.mWriteLock.unlock();
        }
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void readLocked() {
        if (this.mDailyFile != null) {
            readDailyStatsLocked();
        }
        if (this.mStatsFile == null) {
            android.util.Slog.w(TAG, "readLocked: no file associated with this instance");
            return;
        }
        this.mUidStats.clear();
        android.os.Parcel obtain = android.os.Parcel.obtain();
        try {
            try {
                android.os.SystemClock.uptimeMillis();
                if (this.mStatsFile.exists()) {
                    byte[] readFully = this.mStatsFile.readFully();
                    obtain.unmarshall(readFully, 0, readFully.length);
                    obtain.setDataPosition(0);
                    readSummaryFromParcel(obtain);
                }
            } catch (java.lang.Exception e) {
                android.util.Slog.e(TAG, "Error reading battery statistics", e);
                resetAllStatsLocked(android.os.SystemClock.uptimeMillis(), android.os.SystemClock.elapsedRealtime(), 1);
            }
            if (!this.mHistory.readSummary()) {
                resetAllStatsLocked(android.os.SystemClock.uptimeMillis(), android.os.SystemClock.elapsedRealtime(), 1);
            }
            this.mEndPlatformVersion = android.os.Build.ID;
            this.mMonotonicEndTime = -1L;
            this.mHistory.continueRecordingHistory();
            recordDailyStatsIfNeededLocked(false, this.mClock.currentTimeMillis());
        } finally {
            obtain.recycle();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @com.android.internal.annotations.GuardedBy({"this"})
    public void readSummaryFromParcel(android.os.Parcel parcel) throws android.os.ParcelFormatException {
        int i;
        int i2;
        long j;
        long j2;
        int[] iArr;
        int readInt = parcel.readInt();
        if (readInt != VERSION) {
            android.util.Slog.w("BatteryStats", "readFromParcel: version got " + readInt + ", expected " + VERSION + "; erasing old stats");
            return;
        }
        this.mHistory.readSummaryFromParcel(parcel);
        this.mStartCount = parcel.readInt();
        this.mUptimeUs = parcel.readLong();
        this.mRealtimeUs = parcel.readLong();
        this.mStartClockTimeMs = parcel.readLong();
        this.mMonotonicStartTime = parcel.readLong();
        this.mMonotonicEndTime = parcel.readLong();
        this.mStartPlatformVersion = parcel.readString();
        this.mEndPlatformVersion = parcel.readString();
        this.mOnBatteryTimeBase.readSummaryFromParcel(parcel);
        this.mOnBatteryScreenOffTimeBase.readSummaryFromParcel(parcel);
        this.mDischargeUnplugLevel = parcel.readInt();
        this.mDischargePlugLevel = parcel.readInt();
        this.mDischargeCurrentLevel = parcel.readInt();
        this.mBatteryLevel = parcel.readInt();
        this.mEstimatedBatteryCapacityMah = parcel.readInt();
        this.mLastLearnedBatteryCapacityUah = parcel.readInt();
        this.mMinLearnedBatteryCapacityUah = parcel.readInt();
        this.mMaxLearnedBatteryCapacityUah = parcel.readInt();
        this.mLowDischargeAmountSinceCharge = parcel.readInt();
        this.mHighDischargeAmountSinceCharge = parcel.readInt();
        this.mDischargeAmountScreenOnSinceCharge = parcel.readInt();
        this.mDischargeAmountScreenOffSinceCharge = parcel.readInt();
        this.mDischargeAmountScreenDozeSinceCharge = parcel.readInt();
        this.mDischargeStepTracker.readFromParcel(parcel);
        this.mChargeStepTracker.readFromParcel(parcel);
        this.mDailyDischargeStepTracker.readFromParcel(parcel);
        this.mDailyChargeStepTracker.readFromParcel(parcel);
        this.mDischargeCounter.readSummaryFromParcelLocked(parcel);
        this.mDischargeScreenOffCounter.readSummaryFromParcelLocked(parcel);
        this.mDischargeScreenDozeCounter.readSummaryFromParcelLocked(parcel);
        this.mDischargeLightDozeCounter.readSummaryFromParcelLocked(parcel);
        this.mDischargeDeepDozeCounter.readSummaryFromParcelLocked(parcel);
        int readInt2 = parcel.readInt();
        boolean z = 0;
        if (readInt2 > 0) {
            this.mDailyPackageChanges = new java.util.ArrayList<>(readInt2);
            while (readInt2 > 0) {
                readInt2--;
                android.os.BatteryStats.PackageChange packageChange = new android.os.BatteryStats.PackageChange();
                packageChange.mPackageName = parcel.readString();
                packageChange.mUpdate = parcel.readInt() != 0;
                packageChange.mVersionCode = parcel.readLong();
                this.mDailyPackageChanges.add(packageChange);
            }
        } else {
            this.mDailyPackageChanges = null;
        }
        this.mDailyStartTimeMs = parcel.readLong();
        this.mNextMinDailyDeadlineMs = parcel.readLong();
        this.mNextMaxDailyDeadlineMs = parcel.readLong();
        this.mBatteryTimeToFullSeconds = parcel.readLong();
        com.android.internal.power.EnergyConsumerStats.Config createFromParcel = com.android.internal.power.EnergyConsumerStats.Config.createFromParcel(parcel);
        com.android.internal.power.EnergyConsumerStats createAndReadSummaryFromParcel = com.android.internal.power.EnergyConsumerStats.createAndReadSummaryFromParcel(this.mEnergyConsumerStatsConfig, parcel);
        if (createFromParcel != null && java.util.Arrays.equals(createFromParcel.getStateNames(), getBatteryConsumerProcessStateNames())) {
            this.mEnergyConsumerStatsConfig = createFromParcel;
            this.mGlobalEnergyConsumerStats = createAndReadSummaryFromParcel;
        }
        this.mStartCount++;
        this.mScreenState = 0;
        this.mScreenOnTimer.readSummaryFromParcelLocked(parcel);
        this.mScreenDozeTimer.readSummaryFromParcelLocked(parcel);
        int i3 = 0;
        while (true) {
            i = 5;
            if (i3 >= 5) {
                break;
            }
            this.mScreenBrightnessTimer[i3].readSummaryFromParcelLocked(parcel);
            i3++;
        }
        int readInt3 = parcel.readInt();
        for (int i4 = 0; i4 < readInt3; i4++) {
            this.mPerDisplayBatteryStats[i4].readSummaryFromParcel(parcel);
        }
        this.mInteractive = false;
        this.mInteractiveTimer.readSummaryFromParcelLocked(parcel);
        this.mPhoneOn = false;
        this.mPowerSaveModeEnabledTimer.readSummaryFromParcelLocked(parcel);
        this.mLongestLightIdleTimeMs = parcel.readLong();
        this.mLongestFullIdleTimeMs = parcel.readLong();
        this.mDeviceIdleModeLightTimer.readSummaryFromParcelLocked(parcel);
        this.mDeviceIdleModeFullTimer.readSummaryFromParcelLocked(parcel);
        this.mDeviceLightIdlingTimer.readSummaryFromParcelLocked(parcel);
        this.mDeviceIdlingTimer.readSummaryFromParcelLocked(parcel);
        this.mPhoneOnTimer.readSummaryFromParcelLocked(parcel);
        for (int i5 = 0; i5 < CELL_SIGNAL_STRENGTH_LEVEL_COUNT; i5++) {
            this.mPhoneSignalStrengthsTimer[i5].readSummaryFromParcelLocked(parcel);
        }
        this.mPhoneSignalScanningTimer.readSummaryFromParcelLocked(parcel);
        for (int i6 = 0; i6 < android.os.BatteryStats.NUM_DATA_CONNECTION_TYPES; i6++) {
            this.mPhoneDataConnectionsTimer[i6].readSummaryFromParcelLocked(parcel);
        }
        this.mNrNsaTimer.readSummaryFromParcelLocked(parcel);
        int i7 = 0;
        while (true) {
            i2 = 10;
            if (i7 >= 10) {
                break;
            }
            this.mNetworkByteActivityCounters[i7].readSummaryFromParcelLocked(parcel);
            this.mNetworkPacketActivityCounters[i7].readSummaryFromParcelLocked(parcel);
            i7++;
        }
        int readInt4 = parcel.readInt();
        for (int i8 = 0; i8 < readInt4; i8++) {
            if (parcel.readInt() != 0) {
                getRatBatteryStatsLocked(i8).readSummaryFromParcel(parcel);
            }
        }
        this.mMobileRadioPowerState = 1;
        this.mMobileRadioActiveTimer.readSummaryFromParcelLocked(parcel);
        this.mMobileRadioActivePerAppTimer.readSummaryFromParcelLocked(parcel);
        this.mMobileRadioActiveAdjustedTime.readSummaryFromParcelLocked(parcel);
        this.mMobileRadioActiveUnknownTime.readSummaryFromParcelLocked(parcel);
        this.mMobileRadioActiveUnknownCount.readSummaryFromParcelLocked(parcel);
        this.mWifiMulticastWakelockTimer.readSummaryFromParcelLocked(parcel);
        this.mWifiRadioPowerState = 1;
        this.mWifiOn = false;
        this.mWifiOnTimer.readSummaryFromParcelLocked(parcel);
        this.mGlobalWifiRunning = false;
        this.mGlobalWifiRunningTimer.readSummaryFromParcelLocked(parcel);
        for (int i9 = 0; i9 < 8; i9++) {
            this.mWifiStateTimer[i9].readSummaryFromParcelLocked(parcel);
        }
        for (int i10 = 0; i10 < 13; i10++) {
            this.mWifiSupplStateTimer[i10].readSummaryFromParcelLocked(parcel);
        }
        for (int i11 = 0; i11 < 5; i11++) {
            this.mWifiSignalStrengthsTimer[i11].readSummaryFromParcelLocked(parcel);
        }
        this.mWifiActiveTimer.readSummaryFromParcelLocked(parcel);
        this.mWifiActivity.readSummaryFromParcel(parcel);
        for (int i12 = 0; i12 < this.mGpsSignalQualityTimer.length; i12++) {
            this.mGpsSignalQualityTimer[i12].readSummaryFromParcelLocked(parcel);
        }
        this.mBluetoothActivity.readSummaryFromParcel(parcel);
        this.mModemActivity.readSummaryFromParcel(parcel);
        this.mHasWifiReporting = parcel.readInt() != 0;
        this.mHasBluetoothReporting = parcel.readInt() != 0;
        this.mHasModemReporting = parcel.readInt() != 0;
        this.mNumConnectivityChange = parcel.readInt();
        this.mFlashlightOnNesting = 0;
        this.mFlashlightOnTimer.readSummaryFromParcelLocked(parcel);
        this.mCameraOnNesting = 0;
        this.mCameraOnTimer.readSummaryFromParcelLocked(parcel);
        this.mBluetoothScanNesting = 0;
        this.mBluetoothScanTimer.readSummaryFromParcelLocked(parcel);
        int readInt5 = parcel.readInt();
        if (readInt5 > 10000) {
            throw new android.os.ParcelFormatException("File corrupt: too many rpm stats " + readInt5);
        }
        for (int i13 = 0; i13 < readInt5; i13++) {
            if (parcel.readInt() != 0) {
                getRpmTimerLocked(parcel.readString()).readSummaryFromParcelLocked(parcel);
            }
        }
        int readInt6 = parcel.readInt();
        if (readInt6 > 10000) {
            throw new android.os.ParcelFormatException("File corrupt: too many screen-off rpm stats " + readInt6);
        }
        for (int i14 = 0; i14 < readInt6; i14++) {
            if (parcel.readInt() != 0) {
                getScreenOffRpmTimerLocked(parcel.readString()).readSummaryFromParcelLocked(parcel);
            }
        }
        int readInt7 = parcel.readInt();
        if (readInt7 > 10000) {
            throw new android.os.ParcelFormatException("File corrupt: too many kernel wake locks " + readInt7);
        }
        for (int i15 = 0; i15 < readInt7; i15++) {
            if (parcel.readInt() != 0) {
                getKernelWakelockTimerLocked(parcel.readString()).readSummaryFromParcelLocked(parcel);
            }
        }
        int readInt8 = parcel.readInt();
        if (readInt8 > 10000) {
            throw new android.os.ParcelFormatException("File corrupt: too many wakeup reasons " + readInt8);
        }
        for (int i16 = 0; i16 < readInt8; i16++) {
            if (parcel.readInt() != 0) {
                getWakeupReasonTimerLocked(parcel.readString()).readSummaryFromParcelLocked(parcel);
            }
        }
        int readInt9 = parcel.readInt();
        for (int i17 = 0; i17 < readInt9; i17++) {
            if (parcel.readInt() != 0) {
                getKernelMemoryTimerLocked(parcel.readLong()).readSummaryFromParcelLocked(parcel);
            }
        }
        int readInt10 = parcel.readInt();
        if (readInt10 > 10000) {
            throw new android.os.ParcelFormatException("File corrupt: too many uids " + readInt10);
        }
        long elapsedRealtime = this.mClock.elapsedRealtime();
        long uptimeMillis = this.mClock.uptimeMillis();
        int i18 = 0;
        while (i18 < readInt10) {
            int readInt11 = parcel.readInt();
            int i19 = i18;
            long j3 = elapsedRealtime;
            com.android.server.power.stats.BatteryStatsImpl.Uid uid = new com.android.server.power.stats.BatteryStatsImpl.Uid(this, readInt11, elapsedRealtime, uptimeMillis);
            this.mUidStats.put(readInt11, uid);
            uid.mOnBatteryBackgroundTimeBase.readSummaryFromParcel(parcel);
            uid.mOnBatteryScreenOffBackgroundTimeBase.readSummaryFromParcel(parcel);
            uid.mWifiRunning = z;
            if (parcel.readInt() != 0) {
                uid.mWifiRunningTimer.readSummaryFromParcelLocked(parcel);
            }
            uid.mFullWifiLockOut = z;
            if (parcel.readInt() != 0) {
                uid.mFullWifiLockTimer.readSummaryFromParcelLocked(parcel);
            }
            uid.mWifiScanStarted = z;
            if (parcel.readInt() != 0) {
                uid.mWifiScanTimer.readSummaryFromParcelLocked(parcel);
            }
            uid.mWifiBatchedScanBinStarted = -1;
            for (int i20 = z ? 1 : 0; i20 < i; i20++) {
                if (parcel.readInt() != 0) {
                    uid.makeWifiBatchedScanBin(i20, null);
                    uid.mWifiBatchedScanTimer[i20].readSummaryFromParcelLocked(parcel);
                }
            }
            uid.mWifiMulticastWakelockCount = z ? 1 : 0;
            if (parcel.readInt() != 0) {
                uid.mWifiMulticastTimer.readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                uid.createAudioTurnedOnTimerLocked().readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                uid.createVideoTurnedOnTimerLocked().readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                uid.createFlashlightTurnedOnTimerLocked().readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                uid.createCameraTurnedOnTimerLocked().readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                uid.createForegroundActivityTimerLocked().readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                uid.createForegroundServiceTimerLocked().readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                uid.createAggregatedPartialWakelockTimerLocked().readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                uid.createBluetoothScanTimerLocked().readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                uid.createBluetoothUnoptimizedScanTimerLocked().readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                uid.createBluetoothScanResultCounterLocked().readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                uid.createBluetoothScanResultBgCounterLocked().readSummaryFromParcelLocked(parcel);
            }
            uid.mProcessState = 7;
            for (int i21 = z ? 1 : 0; i21 < 7; i21++) {
                if (parcel.readInt() != 0) {
                    uid.makeProcessState(i21, null);
                    uid.mProcessStateTimer[i21].readSummaryFromParcelLocked(parcel);
                }
            }
            if (parcel.readInt() != 0) {
                uid.createVibratorOnTimerLocked().readSummaryFromParcelLocked(parcel);
            }
            if (parcel.readInt() != 0) {
                if (uid.mUserActivityCounters == null) {
                    uid.initUserActivityLocked();
                }
                for (int i22 = z ? 1 : 0; i22 < android.os.BatteryStats.Uid.NUM_USER_ACTIVITY_TYPES; i22++) {
                    uid.mUserActivityCounters[i22].readSummaryFromParcelLocked(parcel);
                }
            }
            if (parcel.readInt() == 0) {
                j = j3;
            } else {
                uid.ensureNetworkActivityLocked();
                for (int i23 = z ? 1 : 0; i23 < i2; i23++) {
                    uid.mNetworkByteActivityCounters[i23].readSummaryFromParcelLocked(parcel);
                    uid.mNetworkPacketActivityCounters[i23].readSummaryFromParcelLocked(parcel);
                }
                if (!parcel.readBoolean()) {
                    j = j3;
                } else {
                    j = j3;
                    uid.mMobileRadioActiveTime = com.android.server.power.stats.BatteryStatsImpl.TimeMultiStateCounter.readFromParcel(parcel, this.mOnBatteryTimeBase, i, j);
                }
                uid.mMobileRadioActiveCount.readSummaryFromParcelLocked(parcel);
            }
            uid.mUserCpuTime.readSummaryFromParcelLocked(parcel);
            uid.mSystemCpuTime.readSummaryFromParcelLocked(parcel);
            if (parcel.readInt() != 0) {
                int readInt12 = parcel.readInt();
                if (this.mCpuScalingPolicies != null) {
                    iArr = this.mCpuScalingPolicies.getPolicies();
                } else {
                    iArr = null;
                }
                if (iArr != null && iArr.length != readInt12) {
                    throw new android.os.ParcelFormatException("Incompatible cpu cluster arrangement");
                }
                detachIfNotNull(uid.mCpuClusterSpeedTimesUs);
                uid.mCpuClusterSpeedTimesUs = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter[readInt12][];
                int i24 = z ? 1 : 0;
                int i25 = z;
                while (i24 < readInt12) {
                    if (parcel.readInt() == 0) {
                        uid.mCpuClusterSpeedTimesUs[i24] = null;
                    } else {
                        int readInt13 = parcel.readInt();
                        if (iArr != null && this.mCpuScalingPolicies.getFrequencies(iArr[i24]).length != readInt13) {
                            throw new android.os.ParcelFormatException("File corrupt: too many speed bins " + readInt13);
                        }
                        uid.mCpuClusterSpeedTimesUs[i24] = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter[readInt13];
                        for (int i26 = i25; i26 < readInt13; i26++) {
                            if (parcel.readInt() != 0) {
                                uid.mCpuClusterSpeedTimesUs[i24][i26] = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter(this.mOnBatteryTimeBase);
                                uid.mCpuClusterSpeedTimesUs[i24][i26].readSummaryFromParcelLocked(parcel);
                            }
                        }
                    }
                    i24++;
                    i25 = 0;
                }
            } else {
                detachIfNotNull(uid.mCpuClusterSpeedTimesUs);
                uid.mCpuClusterSpeedTimesUs = null;
            }
            detachIfNotNull(uid.mCpuFreqTimeMs);
            uid.mCpuFreqTimeMs = com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounterArray.readSummaryFromParcelLocked(parcel, this.mOnBatteryTimeBase);
            detachIfNotNull(uid.mScreenOffCpuFreqTimeMs);
            uid.mScreenOffCpuFreqTimeMs = com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounterArray.readSummaryFromParcelLocked(parcel, this.mOnBatteryScreenOffTimeBase);
            if (parcel.readInt() != 0) {
                uid.mCpuActiveTimeMs = com.android.server.power.stats.BatteryStatsImpl.TimeMultiStateCounter.readFromParcel(parcel, this.mOnBatteryTimeBase, i, this.mClock.elapsedRealtime());
            }
            uid.mCpuClusterTimesMs.readSummaryFromParcelLocked(parcel);
            detachIfNotNull(uid.mProcStateTimeMs);
            uid.mProcStateTimeMs = null;
            if (parcel.readInt() == 0) {
                j2 = j;
            } else {
                detachIfNotNull(uid.mProcStateTimeMs);
                j2 = j;
                uid.mProcStateTimeMs = com.android.server.power.stats.BatteryStatsImpl.TimeInFreqMultiStateCounter.readFromParcel(parcel, this.mOnBatteryTimeBase, 8, this.mCpuScalingPolicies.getScalingStepCount(), this.mClock.elapsedRealtime());
            }
            detachIfNotNull(uid.mProcStateScreenOffTimeMs);
            uid.mProcStateScreenOffTimeMs = null;
            if (parcel.readInt() != 0) {
                detachIfNotNull(uid.mProcStateScreenOffTimeMs);
                uid.mProcStateScreenOffTimeMs = com.android.server.power.stats.BatteryStatsImpl.TimeInFreqMultiStateCounter.readFromParcel(parcel, this.mOnBatteryScreenOffTimeBase, 8, this.mCpuScalingPolicies.getScalingStepCount(), this.mClock.elapsedRealtime());
            }
            if (parcel.readInt() != 0) {
                detachIfNotNull(uid.mMobileRadioApWakeupCount);
                uid.mMobileRadioApWakeupCount = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter(this.mOnBatteryTimeBase);
                uid.mMobileRadioApWakeupCount.readSummaryFromParcelLocked(parcel);
            } else {
                detachIfNotNull(uid.mMobileRadioApWakeupCount);
                uid.mMobileRadioApWakeupCount = null;
            }
            if (parcel.readInt() != 0) {
                detachIfNotNull(uid.mWifiRadioApWakeupCount);
                uid.mWifiRadioApWakeupCount = new com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter(this.mOnBatteryTimeBase);
                uid.mWifiRadioApWakeupCount.readSummaryFromParcelLocked(parcel);
            } else {
                detachIfNotNull(uid.mWifiRadioApWakeupCount);
                uid.mWifiRadioApWakeupCount = null;
            }
            uid.mUidEnergyConsumerStats = com.android.internal.power.EnergyConsumerStats.createAndReadSummaryFromParcel(this.mEnergyConsumerStatsConfig, parcel);
            int readInt14 = parcel.readInt();
            if (readInt14 > MAX_WAKELOCKS_PER_UID + 1) {
                throw new android.os.ParcelFormatException("File corrupt: too many wake locks " + readInt14);
            }
            for (int i27 = 0; i27 < readInt14; i27++) {
                uid.readWakeSummaryFromParcelLocked(parcel.readString(), parcel);
            }
            int readInt15 = parcel.readInt();
            if (readInt15 > MAX_WAKELOCKS_PER_UID + 1) {
                throw new android.os.ParcelFormatException("File corrupt: too many syncs " + readInt15);
            }
            for (int i28 = 0; i28 < readInt15; i28++) {
                uid.readSyncSummaryFromParcelLocked(parcel.readString(), parcel);
            }
            int readInt16 = parcel.readInt();
            if (readInt16 > MAX_WAKELOCKS_PER_UID + 1) {
                throw new android.os.ParcelFormatException("File corrupt: too many job timers " + readInt16);
            }
            for (int i29 = 0; i29 < readInt16; i29++) {
                uid.readJobSummaryFromParcelLocked(parcel.readString(), parcel);
            }
            uid.readJobCompletionsFromParcelLocked(parcel);
            uid.mJobsDeferredEventCount.readSummaryFromParcelLocked(parcel);
            uid.mJobsDeferredCount.readSummaryFromParcelLocked(parcel);
            uid.mJobsFreshnessTimeMs.readSummaryFromParcelLocked(parcel);
            detachIfNotNull(uid.mJobsFreshnessBuckets);
            for (int i30 = 0; i30 < android.os.BatteryStats.JOB_FRESHNESS_BUCKETS.length; i30++) {
                if (parcel.readInt() != 0) {
                    uid.mJobsFreshnessBuckets[i30] = new com.android.server.power.stats.BatteryStatsImpl.Counter(uid.mBsi.mOnBatteryTimeBase);
                    uid.mJobsFreshnessBuckets[i30].readSummaryFromParcelLocked(parcel);
                }
            }
            int readInt17 = parcel.readInt();
            if (readInt17 > 1000) {
                throw new android.os.ParcelFormatException("File corrupt: too many sensors " + readInt17);
            }
            for (int i31 = 0; i31 < readInt17; i31++) {
                int readInt18 = parcel.readInt();
                if (parcel.readInt() != 0) {
                    uid.getSensorTimerLocked(readInt18, true).readSummaryFromParcelLocked(parcel);
                }
            }
            int readInt19 = parcel.readInt();
            if (readInt19 > 10000) {
                throw new android.os.ParcelFormatException("File corrupt: too many processes " + readInt19);
            }
            for (int i32 = 0; i32 < readInt19; i32++) {
                com.android.server.power.stats.BatteryStatsImpl.Uid.Proc processStatsLocked = uid.getProcessStatsLocked(parcel.readString());
                processStatsLocked.mUserTimeMs = parcel.readLong();
                processStatsLocked.mSystemTimeMs = parcel.readLong();
                processStatsLocked.mForegroundTimeMs = parcel.readLong();
                processStatsLocked.mStarts = parcel.readInt();
                processStatsLocked.mNumCrashes = parcel.readInt();
                processStatsLocked.mNumAnrs = parcel.readInt();
                processStatsLocked.readExcessivePowerFromParcelLocked(parcel);
            }
            int readInt20 = parcel.readInt();
            if (readInt20 > 10000) {
                throw new android.os.ParcelFormatException("File corrupt: too many packages " + readInt20);
            }
            for (int i33 = 0; i33 < readInt20; i33++) {
                java.lang.String readString = parcel.readString();
                detachIfNotNull(uid.mPackageStats.get(readString));
                com.android.server.power.stats.BatteryStatsImpl.Uid.Pkg packageStatsLocked = uid.getPackageStatsLocked(readString);
                int readInt21 = parcel.readInt();
                if (readInt21 > 10000) {
                    throw new android.os.ParcelFormatException("File corrupt: too many wakeup alarms " + readInt21);
                }
                packageStatsLocked.mWakeupAlarms.clear();
                for (int i34 = 0; i34 < readInt21; i34++) {
                    java.lang.String readString2 = parcel.readString();
                    com.android.server.power.stats.BatteryStatsImpl.Counter counter = new com.android.server.power.stats.BatteryStatsImpl.Counter(this.mOnBatteryScreenOffTimeBase);
                    counter.readSummaryFromParcelLocked(parcel);
                    packageStatsLocked.mWakeupAlarms.put(readString2, counter);
                }
                int readInt22 = parcel.readInt();
                if (readInt22 > 10000) {
                    throw new android.os.ParcelFormatException("File corrupt: too many services " + readInt22);
                }
                for (int i35 = 0; i35 < readInt22; i35++) {
                    com.android.server.power.stats.BatteryStatsImpl.Uid.Pkg.Serv serviceStatsLocked = uid.getServiceStatsLocked(readString, parcel.readString());
                    serviceStatsLocked.mStartTimeMs = parcel.readLong();
                    serviceStatsLocked.mStarts = parcel.readInt();
                    serviceStatsLocked.mLaunches = parcel.readInt();
                }
            }
            elapsedRealtime = j2;
            z = 0;
            i = 5;
            i2 = 10;
            i18 = i19 + 1;
        }
        com.android.server.power.optimization.Flags.disableSystemServicePowerAttr();
        this.mBinderThreadCpuTimesUs = com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounterArray.readSummaryFromParcelLocked(parcel, this.mOnBatteryTimeBase);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v0 */
    /* JADX WARN: Type inference failed for: r14v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r14v8 */
    @com.android.internal.annotations.GuardedBy({"this"})
    public void writeSummaryToParcel(android.os.Parcel parcel, boolean z) {
        boolean z2;
        int i;
        pullPendingStateUpdatesLocked();
        getStartClockTime();
        long uptimeMillis = this.mClock.uptimeMillis() * 1000;
        long elapsedRealtime = this.mClock.elapsedRealtime() * 1000;
        parcel.writeInt(VERSION);
        this.mHistory.writeSummaryToParcel(parcel, z);
        parcel.writeInt(this.mStartCount);
        ?? r14 = 0;
        parcel.writeLong(computeUptime(uptimeMillis, 0));
        parcel.writeLong(computeRealtime(elapsedRealtime, 0));
        parcel.writeLong(this.mStartClockTimeMs);
        parcel.writeLong(this.mMonotonicStartTime);
        parcel.writeLong(this.mMonotonicClock.monotonicTime());
        parcel.writeString(this.mStartPlatformVersion);
        parcel.writeString(this.mEndPlatformVersion);
        this.mOnBatteryTimeBase.writeSummaryToParcel(parcel, uptimeMillis, elapsedRealtime);
        this.mOnBatteryScreenOffTimeBase.writeSummaryToParcel(parcel, uptimeMillis, elapsedRealtime);
        parcel.writeInt(this.mDischargeUnplugLevel);
        parcel.writeInt(this.mDischargePlugLevel);
        parcel.writeInt(this.mDischargeCurrentLevel);
        parcel.writeInt(this.mBatteryLevel);
        parcel.writeInt(this.mEstimatedBatteryCapacityMah);
        parcel.writeInt(this.mLastLearnedBatteryCapacityUah);
        parcel.writeInt(this.mMinLearnedBatteryCapacityUah);
        parcel.writeInt(this.mMaxLearnedBatteryCapacityUah);
        parcel.writeInt(getLowDischargeAmountSinceCharge());
        parcel.writeInt(getHighDischargeAmountSinceCharge());
        parcel.writeInt(getDischargeAmountScreenOnSinceCharge());
        parcel.writeInt(getDischargeAmountScreenOffSinceCharge());
        parcel.writeInt(getDischargeAmountScreenDozeSinceCharge());
        this.mDischargeStepTracker.writeToParcel(parcel);
        this.mChargeStepTracker.writeToParcel(parcel);
        this.mDailyDischargeStepTracker.writeToParcel(parcel);
        this.mDailyChargeStepTracker.writeToParcel(parcel);
        this.mDischargeCounter.writeSummaryFromParcelLocked(parcel);
        this.mDischargeScreenOffCounter.writeSummaryFromParcelLocked(parcel);
        this.mDischargeScreenDozeCounter.writeSummaryFromParcelLocked(parcel);
        this.mDischargeLightDozeCounter.writeSummaryFromParcelLocked(parcel);
        this.mDischargeDeepDozeCounter.writeSummaryFromParcelLocked(parcel);
        if (this.mDailyPackageChanges != null) {
            int size = this.mDailyPackageChanges.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                android.os.BatteryStats.PackageChange packageChange = this.mDailyPackageChanges.get(i2);
                parcel.writeString(packageChange.mPackageName);
                parcel.writeInt(packageChange.mUpdate ? 1 : 0);
                parcel.writeLong(packageChange.mVersionCode);
            }
        } else {
            parcel.writeInt(0);
        }
        parcel.writeLong(this.mDailyStartTimeMs);
        parcel.writeLong(this.mNextMinDailyDeadlineMs);
        parcel.writeLong(this.mNextMaxDailyDeadlineMs);
        parcel.writeLong(this.mBatteryTimeToFullSeconds);
        com.android.internal.power.EnergyConsumerStats.Config.writeToParcel(this.mEnergyConsumerStatsConfig, parcel);
        com.android.internal.power.EnergyConsumerStats.writeSummaryToParcel(this.mGlobalEnergyConsumerStats, parcel);
        this.mScreenOnTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        this.mScreenDozeTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        for (int i3 = 0; i3 < 5; i3++) {
            this.mScreenBrightnessTimer[i3].writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        }
        int length = this.mPerDisplayBatteryStats.length;
        parcel.writeInt(length);
        for (int i4 = 0; i4 < length; i4++) {
            this.mPerDisplayBatteryStats[i4].writeSummaryToParcel(parcel, elapsedRealtime);
        }
        this.mInteractiveTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        this.mPowerSaveModeEnabledTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        parcel.writeLong(this.mLongestLightIdleTimeMs);
        parcel.writeLong(this.mLongestFullIdleTimeMs);
        this.mDeviceIdleModeLightTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        this.mDeviceIdleModeFullTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        this.mDeviceLightIdlingTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        this.mDeviceIdlingTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        this.mPhoneOnTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        for (int i5 = 0; i5 < CELL_SIGNAL_STRENGTH_LEVEL_COUNT; i5++) {
            this.mPhoneSignalStrengthsTimer[i5].writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        }
        this.mPhoneSignalScanningTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        for (int i6 = 0; i6 < android.os.BatteryStats.NUM_DATA_CONNECTION_TYPES; i6++) {
            this.mPhoneDataConnectionsTimer[i6].writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        }
        this.mNrNsaTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        for (int i7 = 0; i7 < 10; i7++) {
            this.mNetworkByteActivityCounters[i7].writeSummaryFromParcelLocked(parcel);
            this.mNetworkPacketActivityCounters[i7].writeSummaryFromParcelLocked(parcel);
        }
        int length2 = this.mPerRatBatteryStats.length;
        parcel.writeInt(length2);
        int i8 = 0;
        while (true) {
            z2 = true;
            if (i8 >= length2) {
                break;
            }
            com.android.server.power.stats.BatteryStatsImpl.RadioAccessTechnologyBatteryStats radioAccessTechnologyBatteryStats = this.mPerRatBatteryStats[i8];
            if (radioAccessTechnologyBatteryStats == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                radioAccessTechnologyBatteryStats.writeSummaryToParcel(parcel, elapsedRealtime);
            }
            i8++;
        }
        this.mMobileRadioActiveTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        this.mMobileRadioActivePerAppTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        this.mMobileRadioActiveAdjustedTime.writeSummaryFromParcelLocked(parcel);
        this.mMobileRadioActiveUnknownTime.writeSummaryFromParcelLocked(parcel);
        this.mMobileRadioActiveUnknownCount.writeSummaryFromParcelLocked(parcel);
        this.mWifiMulticastWakelockTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        this.mWifiOnTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        this.mGlobalWifiRunningTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        for (int i9 = 0; i9 < 8; i9++) {
            this.mWifiStateTimer[i9].writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        }
        for (int i10 = 0; i10 < 13; i10++) {
            this.mWifiSupplStateTimer[i10].writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        }
        for (int i11 = 0; i11 < 5; i11++) {
            this.mWifiSignalStrengthsTimer[i11].writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        }
        this.mWifiActiveTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        this.mWifiActivity.writeSummaryToParcel(parcel);
        for (int i12 = 0; i12 < this.mGpsSignalQualityTimer.length; i12++) {
            this.mGpsSignalQualityTimer[i12].writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        }
        this.mBluetoothActivity.writeSummaryToParcel(parcel);
        this.mModemActivity.writeSummaryToParcel(parcel);
        parcel.writeInt(this.mHasWifiReporting ? 1 : 0);
        parcel.writeInt(this.mHasBluetoothReporting ? 1 : 0);
        parcel.writeInt(this.mHasModemReporting ? 1 : 0);
        parcel.writeInt(this.mNumConnectivityChange);
        this.mFlashlightOnTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        this.mCameraOnTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        this.mBluetoothScanTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
        parcel.writeInt(this.mRpmStats.size());
        for (java.util.Map.Entry<java.lang.String, com.android.server.power.stats.BatteryStatsImpl.SamplingTimer> entry : this.mRpmStats.entrySet()) {
            com.android.server.power.stats.BatteryStatsImpl.SamplingTimer value = entry.getValue();
            if (value != null) {
                parcel.writeInt(1);
                parcel.writeString(entry.getKey());
                value.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(0);
            }
        }
        parcel.writeInt(this.mScreenOffRpmStats.size());
        for (java.util.Map.Entry<java.lang.String, com.android.server.power.stats.BatteryStatsImpl.SamplingTimer> entry2 : this.mScreenOffRpmStats.entrySet()) {
            com.android.server.power.stats.BatteryStatsImpl.SamplingTimer value2 = entry2.getValue();
            if (value2 != null) {
                parcel.writeInt(1);
                parcel.writeString(entry2.getKey());
                value2.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(0);
            }
        }
        parcel.writeInt(this.mKernelWakelockStats.size());
        for (java.util.Map.Entry<java.lang.String, com.android.server.power.stats.BatteryStatsImpl.SamplingTimer> entry3 : this.mKernelWakelockStats.entrySet()) {
            com.android.server.power.stats.BatteryStatsImpl.SamplingTimer value3 = entry3.getValue();
            if (value3 != null) {
                parcel.writeInt(1);
                parcel.writeString(entry3.getKey());
                value3.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(0);
            }
        }
        parcel.writeInt(this.mWakeupReasonStats.size());
        for (java.util.Map.Entry<java.lang.String, com.android.server.power.stats.BatteryStatsImpl.SamplingTimer> entry4 : this.mWakeupReasonStats.entrySet()) {
            com.android.server.power.stats.BatteryStatsImpl.SamplingTimer value4 = entry4.getValue();
            if (value4 != null) {
                parcel.writeInt(1);
                parcel.writeString(entry4.getKey());
                value4.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(0);
            }
        }
        parcel.writeInt(this.mKernelMemoryStats.size());
        for (int i13 = 0; i13 < this.mKernelMemoryStats.size(); i13++) {
            com.android.server.power.stats.BatteryStatsImpl.SamplingTimer valueAt = this.mKernelMemoryStats.valueAt(i13);
            if (valueAt != null) {
                parcel.writeInt(1);
                parcel.writeLong(this.mKernelMemoryStats.keyAt(i13));
                valueAt.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(0);
            }
        }
        int size2 = this.mUidStats.size();
        parcel.writeInt(size2);
        int i14 = 0;
        while (i14 < size2) {
            parcel.writeInt(this.mUidStats.keyAt(i14));
            com.android.server.power.stats.BatteryStatsImpl.Uid valueAt2 = this.mUidStats.valueAt(i14);
            int i15 = size2;
            int i16 = i14;
            boolean z3 = z2;
            valueAt2.mOnBatteryBackgroundTimeBase.writeSummaryToParcel(parcel, uptimeMillis, elapsedRealtime);
            valueAt2.mOnBatteryScreenOffBackgroundTimeBase.writeSummaryToParcel(parcel, uptimeMillis, elapsedRealtime);
            if (valueAt2.mWifiRunningTimer != null) {
                parcel.writeInt(z3 ? 1 : 0);
                valueAt2.mWifiRunningTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(r14);
            }
            if (valueAt2.mFullWifiLockTimer != null) {
                parcel.writeInt(z3 ? 1 : 0);
                valueAt2.mFullWifiLockTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(r14);
            }
            if (valueAt2.mWifiScanTimer != null) {
                parcel.writeInt(z3 ? 1 : 0);
                valueAt2.mWifiScanTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(r14);
            }
            for (int i17 = r14; i17 < 5; i17++) {
                if (valueAt2.mWifiBatchedScanTimer[i17] != null) {
                    parcel.writeInt(z3 ? 1 : 0);
                    valueAt2.mWifiBatchedScanTimer[i17].writeSummaryFromParcelLocked(parcel, elapsedRealtime);
                } else {
                    parcel.writeInt(r14);
                }
            }
            if (valueAt2.mWifiMulticastTimer != null) {
                parcel.writeInt(z3 ? 1 : 0);
                valueAt2.mWifiMulticastTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(r14);
            }
            if (valueAt2.mAudioTurnedOnTimer != null) {
                parcel.writeInt(z3 ? 1 : 0);
                valueAt2.mAudioTurnedOnTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(r14);
            }
            if (valueAt2.mVideoTurnedOnTimer != null) {
                parcel.writeInt(z3 ? 1 : 0);
                valueAt2.mVideoTurnedOnTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(r14);
            }
            if (valueAt2.mFlashlightTurnedOnTimer != null) {
                parcel.writeInt(z3 ? 1 : 0);
                valueAt2.mFlashlightTurnedOnTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(r14);
            }
            if (valueAt2.mCameraTurnedOnTimer != null) {
                parcel.writeInt(z3 ? 1 : 0);
                valueAt2.mCameraTurnedOnTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(r14);
            }
            if (valueAt2.mForegroundActivityTimer != null) {
                parcel.writeInt(z3 ? 1 : 0);
                valueAt2.mForegroundActivityTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(r14);
            }
            if (valueAt2.mForegroundServiceTimer != null) {
                parcel.writeInt(z3 ? 1 : 0);
                valueAt2.mForegroundServiceTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(r14);
            }
            if (valueAt2.mAggregatedPartialWakelockTimer != null) {
                parcel.writeInt(z3 ? 1 : 0);
                valueAt2.mAggregatedPartialWakelockTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(r14);
            }
            if (valueAt2.mBluetoothScanTimer != null) {
                parcel.writeInt(z3 ? 1 : 0);
                valueAt2.mBluetoothScanTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(r14);
            }
            if (valueAt2.mBluetoothUnoptimizedScanTimer != null) {
                parcel.writeInt(z3 ? 1 : 0);
                valueAt2.mBluetoothUnoptimizedScanTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(r14);
            }
            if (valueAt2.mBluetoothScanResultCounter != null) {
                parcel.writeInt(z3 ? 1 : 0);
                valueAt2.mBluetoothScanResultCounter.writeSummaryFromParcelLocked(parcel);
            } else {
                parcel.writeInt(r14);
            }
            if (valueAt2.mBluetoothScanResultBgCounter != null) {
                parcel.writeInt(z3 ? 1 : 0);
                valueAt2.mBluetoothScanResultBgCounter.writeSummaryFromParcelLocked(parcel);
            } else {
                parcel.writeInt(r14);
            }
            for (int i18 = r14; i18 < 7; i18++) {
                if (valueAt2.mProcessStateTimer[i18] != null) {
                    parcel.writeInt(z3 ? 1 : 0);
                    valueAt2.mProcessStateTimer[i18].writeSummaryFromParcelLocked(parcel, elapsedRealtime);
                } else {
                    parcel.writeInt(r14);
                }
            }
            if (valueAt2.mVibratorOnTimer != null) {
                parcel.writeInt(z3 ? 1 : 0);
                valueAt2.mVibratorOnTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            } else {
                parcel.writeInt(r14);
            }
            if (valueAt2.mUserActivityCounters == null) {
                parcel.writeInt(r14);
            } else {
                parcel.writeInt(z3 ? 1 : 0);
                for (int i19 = r14; i19 < android.os.BatteryStats.Uid.NUM_USER_ACTIVITY_TYPES; i19++) {
                    valueAt2.mUserActivityCounters[i19].writeSummaryFromParcelLocked(parcel);
                }
            }
            if (valueAt2.mNetworkByteActivityCounters == null) {
                parcel.writeInt(r14);
            } else {
                parcel.writeInt(z3 ? 1 : 0);
                for (int i20 = r14; i20 < 10; i20++) {
                    valueAt2.mNetworkByteActivityCounters[i20].writeSummaryFromParcelLocked(parcel);
                    valueAt2.mNetworkPacketActivityCounters[i20].writeSummaryFromParcelLocked(parcel);
                }
                if (valueAt2.mMobileRadioActiveTime != null) {
                    parcel.writeBoolean(z3);
                    valueAt2.mMobileRadioActiveTime.writeToParcel(parcel);
                } else {
                    parcel.writeBoolean(r14);
                }
                valueAt2.mMobileRadioActiveCount.writeSummaryFromParcelLocked(parcel);
            }
            valueAt2.mUserCpuTime.writeSummaryFromParcelLocked(parcel);
            valueAt2.mSystemCpuTime.writeSummaryFromParcelLocked(parcel);
            if (valueAt2.mCpuClusterSpeedTimesUs != null) {
                parcel.writeInt(z3 ? 1 : 0);
                parcel.writeInt(valueAt2.mCpuClusterSpeedTimesUs.length);
                com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter[][] longSamplingCounterArr = valueAt2.mCpuClusterSpeedTimesUs;
                int length3 = longSamplingCounterArr.length;
                int i21 = r14;
                int i22 = r14;
                while (i21 < length3) {
                    com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter[] longSamplingCounterArr2 = longSamplingCounterArr[i21];
                    if (longSamplingCounterArr2 != null) {
                        parcel.writeInt(z3 ? 1 : 0);
                        parcel.writeInt(longSamplingCounterArr2.length);
                        int length4 = longSamplingCounterArr2.length;
                        int i23 = i22;
                        i22 = i22;
                        while (i23 < length4) {
                            com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounter longSamplingCounter = longSamplingCounterArr2[i23];
                            if (longSamplingCounter != null) {
                                parcel.writeInt(z3 ? 1 : 0);
                                longSamplingCounter.writeSummaryFromParcelLocked(parcel);
                                i = 0;
                            } else {
                                i = 0;
                                parcel.writeInt(0);
                            }
                            i23++;
                            i22 = i;
                        }
                    } else {
                        parcel.writeInt(i22);
                    }
                    i21++;
                    i22 = i22;
                }
            } else {
                parcel.writeInt(r14);
            }
            com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounterArray.writeSummaryToParcelLocked(parcel, valueAt2.mCpuFreqTimeMs);
            com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounterArray.writeSummaryToParcelLocked(parcel, valueAt2.mScreenOffCpuFreqTimeMs);
            if (valueAt2.mCpuActiveTimeMs != null) {
                parcel.writeInt(valueAt2.mCpuActiveTimeMs.getStateCount());
                valueAt2.mCpuActiveTimeMs.writeToParcel(parcel);
            } else {
                parcel.writeInt(0);
            }
            valueAt2.mCpuClusterTimesMs.writeSummaryToParcelLocked(parcel);
            if (valueAt2.mProcStateTimeMs != null) {
                parcel.writeInt(valueAt2.mProcStateTimeMs.getStateCount());
                valueAt2.mProcStateTimeMs.writeToParcel(parcel);
            } else {
                parcel.writeInt(0);
            }
            if (valueAt2.mProcStateScreenOffTimeMs != null) {
                parcel.writeInt(valueAt2.mProcStateScreenOffTimeMs.getStateCount());
                valueAt2.mProcStateScreenOffTimeMs.writeToParcel(parcel);
            } else {
                parcel.writeInt(0);
            }
            if (valueAt2.mMobileRadioApWakeupCount != null) {
                parcel.writeInt(z3 ? 1 : 0);
                valueAt2.mMobileRadioApWakeupCount.writeSummaryFromParcelLocked(parcel);
            } else {
                parcel.writeInt(0);
            }
            if (valueAt2.mWifiRadioApWakeupCount != null) {
                parcel.writeInt(z3 ? 1 : 0);
                valueAt2.mWifiRadioApWakeupCount.writeSummaryFromParcelLocked(parcel);
            } else {
                parcel.writeInt(0);
            }
            com.android.internal.power.EnergyConsumerStats.writeSummaryToParcel(valueAt2.mUidEnergyConsumerStats, parcel);
            android.util.ArrayMap<java.lang.String, com.android.server.power.stats.BatteryStatsImpl.Uid.Wakelock> map = valueAt2.mWakelockStats.getMap();
            int size3 = map.size();
            parcel.writeInt(size3);
            for (int i24 = 0; i24 < size3; i24++) {
                parcel.writeString(map.keyAt(i24));
                com.android.server.power.stats.BatteryStatsImpl.Uid.Wakelock valueAt3 = map.valueAt(i24);
                if (valueAt3.mTimerFull != null) {
                    parcel.writeInt(z3 ? 1 : 0);
                    valueAt3.mTimerFull.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
                } else {
                    parcel.writeInt(0);
                }
                if (valueAt3.mTimerPartial != null) {
                    parcel.writeInt(z3 ? 1 : 0);
                    valueAt3.mTimerPartial.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
                } else {
                    parcel.writeInt(0);
                }
                if (valueAt3.mTimerWindow != null) {
                    parcel.writeInt(z3 ? 1 : 0);
                    valueAt3.mTimerWindow.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
                } else {
                    parcel.writeInt(0);
                }
                if (valueAt3.mTimerDraw != null) {
                    parcel.writeInt(z3 ? 1 : 0);
                    valueAt3.mTimerDraw.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
                } else {
                    parcel.writeInt(0);
                }
            }
            android.util.ArrayMap<java.lang.String, com.android.server.power.stats.BatteryStatsImpl.DualTimer> map2 = valueAt2.mSyncStats.getMap();
            int size4 = map2.size();
            parcel.writeInt(size4);
            for (int i25 = 0; i25 < size4; i25++) {
                parcel.writeString(map2.keyAt(i25));
                map2.valueAt(i25).writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            }
            android.util.ArrayMap<java.lang.String, com.android.server.power.stats.BatteryStatsImpl.DualTimer> map3 = valueAt2.mJobStats.getMap();
            int size5 = map3.size();
            parcel.writeInt(size5);
            for (int i26 = 0; i26 < size5; i26++) {
                parcel.writeString(map3.keyAt(i26));
                map3.valueAt(i26).writeSummaryFromParcelLocked(parcel, elapsedRealtime);
            }
            valueAt2.writeJobCompletionsToParcelLocked(parcel);
            valueAt2.mJobsDeferredEventCount.writeSummaryFromParcelLocked(parcel);
            valueAt2.mJobsDeferredCount.writeSummaryFromParcelLocked(parcel);
            valueAt2.mJobsFreshnessTimeMs.writeSummaryFromParcelLocked(parcel);
            for (int i27 = 0; i27 < android.os.BatteryStats.JOB_FRESHNESS_BUCKETS.length; i27++) {
                if (valueAt2.mJobsFreshnessBuckets[i27] != null) {
                    parcel.writeInt(z3 ? 1 : 0);
                    valueAt2.mJobsFreshnessBuckets[i27].writeSummaryFromParcelLocked(parcel);
                } else {
                    parcel.writeInt(0);
                }
            }
            int size6 = valueAt2.mSensorStats.size();
            parcel.writeInt(size6);
            for (int i28 = 0; i28 < size6; i28++) {
                parcel.writeInt(valueAt2.mSensorStats.keyAt(i28));
                com.android.server.power.stats.BatteryStatsImpl.Uid.Sensor valueAt4 = valueAt2.mSensorStats.valueAt(i28);
                if (valueAt4.mTimer != null) {
                    parcel.writeInt(z3 ? 1 : 0);
                    valueAt4.mTimer.writeSummaryFromParcelLocked(parcel, elapsedRealtime);
                } else {
                    parcel.writeInt(0);
                }
            }
            int i29 = 0;
            int size7 = valueAt2.mProcessStats.size();
            parcel.writeInt(size7);
            for (int i30 = 0; i30 < size7; i30++) {
                parcel.writeString(valueAt2.mProcessStats.keyAt(i30));
                com.android.server.power.stats.BatteryStatsImpl.Uid.Proc valueAt5 = valueAt2.mProcessStats.valueAt(i30);
                parcel.writeLong(valueAt5.mUserTimeMs);
                parcel.writeLong(valueAt5.mSystemTimeMs);
                parcel.writeLong(valueAt5.mForegroundTimeMs);
                parcel.writeInt(valueAt5.mStarts);
                parcel.writeInt(valueAt5.mNumCrashes);
                parcel.writeInt(valueAt5.mNumAnrs);
                valueAt5.writeExcessivePowerToParcelLocked(parcel);
            }
            int size8 = valueAt2.mPackageStats.size();
            parcel.writeInt(size8);
            if (size8 > 0) {
                for (java.util.Map.Entry<java.lang.String, com.android.server.power.stats.BatteryStatsImpl.Uid.Pkg> entry5 : valueAt2.mPackageStats.entrySet()) {
                    parcel.writeString(entry5.getKey());
                    com.android.server.power.stats.BatteryStatsImpl.Uid.Pkg value5 = entry5.getValue();
                    int size9 = value5.mWakeupAlarms.size();
                    parcel.writeInt(size9);
                    for (int i31 = i29; i31 < size9; i31++) {
                        parcel.writeString(value5.mWakeupAlarms.keyAt(i31));
                        value5.mWakeupAlarms.valueAt(i31).writeSummaryFromParcelLocked(parcel);
                    }
                    int size10 = value5.mServiceStats.size();
                    parcel.writeInt(size10);
                    int i32 = i29;
                    while (i32 < size10) {
                        parcel.writeString(value5.mServiceStats.keyAt(i32));
                        com.android.server.power.stats.BatteryStatsImpl.Uid.Pkg.Serv valueAt6 = value5.mServiceStats.valueAt(i32);
                        parcel.writeLong(valueAt6.getStartTimeToNowLocked(this.mOnBatteryTimeBase.getUptime(uptimeMillis) / 1000));
                        parcel.writeInt(valueAt6.mStarts);
                        parcel.writeInt(valueAt6.mLaunches);
                        i32++;
                        value5 = value5;
                    }
                    i29 = 0;
                }
            }
            i14 = i16 + 1;
            size2 = i15;
            z2 = z3 ? 1 : 0;
            r14 = 0;
        }
        com.android.server.power.optimization.Flags.disableSystemServicePowerAttr();
        com.android.server.power.stats.BatteryStatsImpl.LongSamplingCounterArray.writeSummaryToParcelLocked(parcel, this.mBinderThreadCpuTimesUs);
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void prepareForDumpLocked() {
        pullPendingStateUpdatesLocked();
        getStartClockTime();
        com.android.server.power.optimization.Flags.disableSystemServicePowerAttr();
        updateSystemServiceCallStats();
    }

    @com.android.internal.annotations.GuardedBy({"this"})
    public void dump(android.content.Context context, java.io.PrintWriter printWriter, int i, int i2, long j, android.os.BatteryStats.BatteryStatsDumpHelper batteryStatsDumpHelper) {
        super.dump(context, printWriter, i, i2, j, batteryStatsDumpHelper);
        synchronized (this) {
            try {
                printWriter.print("Per process state tracking available: ");
                printWriter.println(trackPerProcStateCpuTimes());
                printWriter.print("Total cpu time reads: ");
                printWriter.println(this.mNumSingleUidCpuTimeReads);
                printWriter.print("Batching Duration (min): ");
                printWriter.println((this.mClock.uptimeMillis() - this.mCpuTimeReadsTrackingStartTimeMs) / 60000);
                printWriter.print("All UID cpu time reads since the later of device start or stats reset: ");
                printWriter.println(this.mNumAllUidCpuTimeReads);
                printWriter.print("UIDs removed since the later of device start or stats reset: ");
                printWriter.println(this.mNumUidsRemoved);
                this.mPowerStatsUidResolver.dump(printWriter);
                printWriter.println();
                dumpConstantsLocked(printWriter);
                if (this.mCpuPowerStatsCollector != null) {
                    printWriter.println();
                    this.mCpuPowerStatsCollector.dumpCpuPowerBracketsLocked(printWriter);
                }
                printWriter.println();
                dumpEnergyConsumerStatsLocked(printWriter);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }
}
