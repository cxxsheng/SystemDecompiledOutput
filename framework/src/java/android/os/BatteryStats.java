package android.os;

/* loaded from: classes3.dex */
public abstract class BatteryStats {
    private static final java.lang.String AGGREGATED_WAKELOCK_DATA = "awl";
    public static final int AGGREGATED_WAKE_TYPE_PARTIAL = 20;
    private static final java.lang.String APK_DATA = "apk";
    private static final java.lang.String AUDIO_DATA = "aud";
    public static final int AUDIO_TURNED_ON = 15;
    private static final java.lang.String BATTERY_DATA = "bt";
    private static final java.lang.String BATTERY_DISCHARGE_DATA = "dc";
    private static final java.lang.String BATTERY_LEVEL_DATA = "lv";
    private static final int BATTERY_STATS_CHECKIN_VERSION = 9;
    private static final java.lang.String BLUETOOTH_CONTROLLER_DATA = "ble";
    private static final java.lang.String BLUETOOTH_MISC_DATA = "blem";
    public static final int BLUETOOTH_SCAN_ON = 19;
    public static final int BLUETOOTH_UNOPTIMIZED_SCAN_ON = 21;
    private static final long BYTES_PER_GB = 1073741824;
    private static final long BYTES_PER_KB = 1024;
    private static final long BYTES_PER_MB = 1048576;
    private static final java.lang.String CAMERA_DATA = "cam";
    public static final int CAMERA_TURNED_ON = 17;
    private static final java.lang.String CELLULAR_CONTROLLER_NAME = "Cellular";
    private static final java.lang.String CHARGE_STEP_DATA = "csd";
    private static final java.lang.String CHARGE_TIME_REMAIN_DATA = "ctr";
    static final int CHECKIN_VERSION = 36;
    private static final java.lang.String CPU_DATA = "cpu";
    private static final java.lang.String CPU_TIMES_AT_FREQ_DATA = "ctf";
    private static final java.lang.String DATA_CONNECTION_COUNT_DATA = "dcc";
    public static final int DATA_CONNECTION_OUT_OF_SERVICE = 0;
    private static final java.lang.String DATA_CONNECTION_TIME_DATA = "dct";
    public static final int DEVICE_IDLE_MODE_DEEP = 2;
    public static final int DEVICE_IDLE_MODE_LIGHT = 1;
    public static final int DEVICE_IDLE_MODE_OFF = 0;
    private static final java.lang.String DISCHARGE_STEP_DATA = "dsd";
    private static final java.lang.String DISCHARGE_TIME_REMAIN_DATA = "dtr";
    private static final int[] DISPLAY_TRANSPORT_PRIORITIES;
    public static final int DUMP_CHARGED_ONLY = 2;
    public static final int DUMP_DAILY_ONLY = 4;
    public static final int DUMP_DEVICE_WIFI_ONLY = 64;
    public static final int DUMP_HISTORY_ONLY = 8;
    public static final int DUMP_INCLUDE_HISTORY = 16;
    public static final int DUMP_VERBOSE = 32;
    public static final long DURATION_UNAVAILABLE = -1;
    private static final java.lang.String FLASHLIGHT_DATA = "fla";
    public static final int FLASHLIGHT_TURNED_ON = 16;
    public static final int FOREGROUND_ACTIVITY = 10;
    public static final int FOREGROUND_SERVICE = 22;
    private static final java.lang.String FOREGROUND_SERVICE_DATA = "fgs";
    public static final int FULL_WIFI_LOCK = 5;
    private static final java.lang.String GLOBAL_BLUETOOTH_CONTROLLER_DATA = "gble";
    private static final java.lang.String GLOBAL_CPU_FREQ_DATA = "gcf";
    private static final java.lang.String GLOBAL_MODEM_CONTROLLER_DATA = "gmcd";
    private static final java.lang.String GLOBAL_NETWORK_DATA = "gn";
    private static final java.lang.String GLOBAL_WIFI_CONTROLLER_DATA = "gwfcd";
    private static final java.lang.String GLOBAL_WIFI_DATA = "gwfl";
    private static final java.lang.String HISTORY_DATA = "h";
    private static final java.lang.String HISTORY_STRING_POOL = "hsp";
    public static final int JOB = 14;
    private static final java.lang.String JOBS_DEFERRED_DATA = "jbd";
    private static final java.lang.String JOB_COMPLETION_DATA = "jbc";
    private static final java.lang.String JOB_DATA = "jb";
    private static final java.lang.String KERNEL_WAKELOCK_DATA = "kwl";
    private static final boolean LOCAL_LOGV = false;
    public static final int MAX_TRACKED_SCREEN_STATE = 4;
    public static final double MILLISECONDS_IN_HOUR = 3600000.0d;
    private static final java.lang.String MISC_DATA = "m";
    private static final java.lang.String MODEM_CONTROLLER_DATA = "mcd";
    public static final int NETWORK_BT_RX_DATA = 4;
    public static final int NETWORK_BT_TX_DATA = 5;
    private static final java.lang.String NETWORK_DATA = "nt";
    public static final int NETWORK_MOBILE_BG_RX_DATA = 6;
    public static final int NETWORK_MOBILE_BG_TX_DATA = 7;
    public static final int NETWORK_MOBILE_RX_DATA = 0;
    public static final int NETWORK_MOBILE_TX_DATA = 1;
    public static final int NETWORK_WIFI_BG_RX_DATA = 8;
    public static final int NETWORK_WIFI_BG_TX_DATA = 9;
    public static final int NETWORK_WIFI_RX_DATA = 2;
    public static final int NETWORK_WIFI_TX_DATA = 3;
    public static final int NUM_NETWORK_ACTIVITY_TYPES = 10;
    public static final int NUM_SCREEN_BRIGHTNESS_BINS = 5;
    public static final int NUM_WIFI_SIGNAL_STRENGTH_BINS = 5;
    public static final long POWER_DATA_UNAVAILABLE = -1;
    private static final java.lang.String POWER_USE_ITEM_DATA = "pwi";
    private static final java.lang.String POWER_USE_SUMMARY_DATA = "pws";
    private static final java.lang.String PROCESS_DATA = "pr";
    public static final int PROCESS_STATE = 12;
    public static final int RADIO_ACCESS_TECHNOLOGY_COUNT = 3;
    public static final int RADIO_ACCESS_TECHNOLOGY_LTE = 1;
    public static final int RADIO_ACCESS_TECHNOLOGY_NR = 2;
    public static final int RADIO_ACCESS_TECHNOLOGY_OTHER = 0;
    private static final java.lang.String RESOURCE_POWER_MANAGER_DATA = "rpm";
    public static final java.lang.String RESULT_RECEIVER_CONTROLLER_KEY = "controller_activity";
    public static final int SCREEN_BRIGHTNESS_BRIGHT = 4;
    public static final int SCREEN_BRIGHTNESS_DARK = 0;
    private static final java.lang.String SCREEN_BRIGHTNESS_DATA = "br";
    public static final int SCREEN_BRIGHTNESS_DIM = 1;
    public static final int SCREEN_BRIGHTNESS_LIGHT = 3;
    public static final int SCREEN_BRIGHTNESS_MEDIUM = 2;
    protected static final boolean SCREEN_OFF_RPM_STATS_ENABLED = false;
    public static final int SENSOR = 3;
    private static final java.lang.String SENSOR_DATA = "sr";
    public static final java.lang.String SERVICE_NAME = "batterystats";
    private static final java.lang.String SIGNAL_SCANNING_TIME_DATA = "sst";
    private static final java.lang.String SIGNAL_STRENGTH_COUNT_DATA = "sgc";
    private static final java.lang.String SIGNAL_STRENGTH_TIME_DATA = "sgt";
    private static final java.lang.String STATE_TIME_DATA = "st";

    @java.lang.Deprecated
    public static final int STATS_CURRENT = 1;
    public static final int STATS_SINCE_CHARGED = 0;

    @java.lang.Deprecated
    public static final int STATS_SINCE_UNPLUGGED = 2;
    public static final long STEP_LEVEL_INITIAL_MODE_MASK = 71776119061217280L;
    public static final int STEP_LEVEL_INITIAL_MODE_SHIFT = 48;
    public static final long STEP_LEVEL_LEVEL_MASK = 280375465082880L;
    public static final int STEP_LEVEL_LEVEL_SHIFT = 40;
    public static final int STEP_LEVEL_MODE_DEVICE_IDLE = 8;
    public static final int STEP_LEVEL_MODE_POWER_SAVE = 4;
    public static final int STEP_LEVEL_MODE_SCREEN_STATE = 3;
    public static final long STEP_LEVEL_MODIFIED_MODE_MASK = -72057594037927936L;
    public static final int STEP_LEVEL_MODIFIED_MODE_SHIFT = 56;
    public static final long STEP_LEVEL_TIME_MASK = 1099511627775L;
    public static final int SYNC = 13;
    private static final java.lang.String SYNC_DATA = "sy";
    private static final java.lang.String TAG = "BatteryStats";
    private static final java.lang.String UID_DATA = "uid";
    public static final java.lang.String UID_TIMES_TYPE_ALL = "A";
    private static final java.lang.String USER_ACTIVITY_DATA = "ua";
    private static final java.lang.String VERSION_DATA = "vers";
    private static final java.lang.String VIBRATOR_DATA = "vib";
    public static final int VIBRATOR_ON = 9;
    private static final java.lang.String VIDEO_DATA = "vid";
    public static final int VIDEO_TURNED_ON = 8;
    private static final java.lang.String WAKELOCK_DATA = "wl";
    private static final java.lang.String WAKEUP_ALARM_DATA = "wua";
    private static final java.lang.String WAKEUP_REASON_DATA = "wr";
    public static final int WAKE_TYPE_DRAW = 18;
    public static final int WAKE_TYPE_FULL = 1;
    public static final int WAKE_TYPE_PARTIAL = 0;
    public static final int WAKE_TYPE_WINDOW = 2;
    public static final int WIFI_AGGREGATE_MULTICAST_ENABLED = 23;
    public static final int WIFI_BATCHED_SCAN = 11;
    private static final java.lang.String WIFI_CONTROLLER_DATA = "wfcd";
    private static final java.lang.String WIFI_CONTROLLER_NAME = "WiFi";
    private static final java.lang.String WIFI_DATA = "wfl";
    private static final java.lang.String WIFI_MULTICAST_DATA = "wmc";
    public static final int WIFI_MULTICAST_ENABLED = 7;
    private static final java.lang.String WIFI_MULTICAST_TOTAL_DATA = "wmct";
    public static final int WIFI_RUNNING = 4;
    public static final int WIFI_SCAN = 6;
    private static final java.lang.String WIFI_SIGNAL_STRENGTH_COUNT_DATA = "wsgc";
    private static final java.lang.String WIFI_SIGNAL_STRENGTH_TIME_DATA = "wsgt";
    private static final java.lang.String WIFI_STATE_COUNT_DATA = "wsc";
    private static final java.lang.String WIFI_STATE_TIME_DATA = "wst";
    private static final java.lang.String WIFI_SUPPL_STATE_COUNT_DATA = "wssc";
    private static final java.lang.String WIFI_SUPPL_STATE_TIME_DATA = "wsst";
    private final java.lang.StringBuilder mFormatBuilder = new java.lang.StringBuilder(32);
    private final java.util.Formatter mFormatter = new java.util.Formatter(this.mFormatBuilder);
    private static final java.lang.String[] STAT_NAMES = {android.app.blob.XmlTags.TAG_LEASEE, "c", android.app.blob.XmlTags.ATTR_UID};
    public static final long[] JOB_FRESHNESS_BUCKETS = {3600000, 7200000, 14400000, 28800000, Long.MAX_VALUE};
    static final java.lang.String[] SCREEN_BRIGHTNESS_NAMES = {"dark", "dim", "medium", "light", "bright"};
    static final java.lang.String[] SCREEN_BRIGHTNESS_SHORT_NAMES = {android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS, "1", "2", "3", "4"};
    static final java.lang.String[] DATA_CONNECTION_NAMES = {"oos", "gprs", "edge", "umts", "cdma", "evdo_0", "evdo_A", "1xrtt", "hsdpa", "hsupa", "hspa", "iden", "evdo_b", "lte", "ehrpd", "hspap", "gsm", "td_scdma", "iwlan", "lte_ca", "nr", "emngcy", "other"};
    public static final int NUM_ALL_NETWORK_TYPES = getAllNetworkTypesCount();
    public static final int DATA_CONNECTION_EMERGENCY_SERVICE = NUM_ALL_NETWORK_TYPES + 1;
    public static final int DATA_CONNECTION_OTHER = NUM_ALL_NETWORK_TYPES + 2;
    public static final int NUM_DATA_CONNECTION_TYPES = NUM_ALL_NETWORK_TYPES + 3;
    public static final java.lang.String[] RADIO_ACCESS_TECHNOLOGY_NAMES = {"Other", com.android.internal.telephony.DctConstants.RAT_NAME_LTE, "NR"};
    static final java.lang.String[] WIFI_SUPPL_STATE_NAMES = {"invalid", "disconn", "disabled", "inactive", "scanning", "authenticating", "associating", "associated", "4-way-handshake", "group-handshake", "completed", "dormant", "uninit"};
    static final java.lang.String[] WIFI_SUPPL_STATE_SHORT_NAMES = {"inv", "dsc", "dis", "inact", "scan", android.content.Context.AUTH_SERVICE, "ascing", "asced", "4-way", "group", "compl", "dorm", "uninit"};
    public static final android.os.BatteryStats.BitDescription[] HISTORY_STATE_DESCRIPTIONS = {new android.os.BatteryStats.BitDescription(Integer.MIN_VALUE, "running", "r"), new android.os.BatteryStats.BitDescription(1073741824, "wake_lock", "w"), new android.os.BatteryStats.BitDescription(8388608, android.content.Context.SENSOR_SERVICE, android.app.blob.XmlTags.TAG_SESSION), new android.os.BatteryStats.BitDescription(536870912, "gps", "g"), new android.os.BatteryStats.BitDescription(268435456, "wifi_full_lock", "Wl"), new android.os.BatteryStats.BitDescription(134217728, "wifi_scan", "Ws"), new android.os.BatteryStats.BitDescription(65536, "wifi_multicast", "Wm"), new android.os.BatteryStats.BitDescription(67108864, "wifi_radio", "Wr"), new android.os.BatteryStats.BitDescription(33554432, "mobile_radio", "Pr"), new android.os.BatteryStats.BitDescription(2097152, "phone_scanning", "Psc"), new android.os.BatteryStats.BitDescription(4194304, "audio", android.app.backup.FullBackup.APK_TREE_TOKEN), new android.os.BatteryStats.BitDescription(1048576, "screen", android.hardware.gnss.GnssSignalType.CODE_TYPE_S), new android.os.BatteryStats.BitDescription(524288, android.os.BatteryManager.EXTRA_PLUGGED, "BP"), new android.os.BatteryStats.BitDescription(262144, "screen_doze", "Sd"), new android.os.BatteryStats.BitDescription(android.os.BatteryStats.HistoryItem.STATE_DATA_CONNECTION_MASK, 9, "data_conn", "Pcn", DATA_CONNECTION_NAMES, DATA_CONNECTION_NAMES), new android.os.BatteryStats.BitDescription(448, 6, "phone_state", "Pst", new java.lang.String[]{"in", "out", "emergency", "off"}, new java.lang.String[]{"in", "out", "em", "off"}), new android.os.BatteryStats.BitDescription(56, 3, "phone_signal_strength", "Pss", new java.lang.String[]{"none", "poor", "moderate", "good", "great"}, new java.lang.String[]{android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS, "1", "2", "3", "4"}), new android.os.BatteryStats.BitDescription(7, 0, "brightness", "Sb", SCREEN_BRIGHTNESS_NAMES, SCREEN_BRIGHTNESS_SHORT_NAMES)};
    public static final android.os.BatteryStats.BitDescription[] HISTORY_STATE2_DESCRIPTIONS = {new android.os.BatteryStats.BitDescription(Integer.MIN_VALUE, "power_save", "ps"), new android.os.BatteryStats.BitDescription(1073741824, "video", "v"), new android.os.BatteryStats.BitDescription(536870912, "wifi_running", "Ww"), new android.os.BatteryStats.BitDescription(268435456, "wifi", android.hardware.gnss.GnssSignalType.CODE_TYPE_W), new android.os.BatteryStats.BitDescription(134217728, "flashlight", "fl"), new android.os.BatteryStats.BitDescription(100663296, 25, "device_idle", "di", new java.lang.String[]{"off", "light", "full", "???"}, new java.lang.String[]{"off", "light", "full", "???"}), new android.os.BatteryStats.BitDescription(16777216, "charging", "ch"), new android.os.BatteryStats.BitDescription(262144, "usb_data", "Ud"), new android.os.BatteryStats.BitDescription(8388608, "phone_in_call", "Pcl"), new android.os.BatteryStats.BitDescription(4194304, "bluetooth", android.app.blob.XmlTags.TAG_BLOB), new android.os.BatteryStats.BitDescription(112, 4, "wifi_signal_strength", "Wss", new java.lang.String[]{android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS, "1", "2", "3", "4"}, new java.lang.String[]{android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS, "1", "2", "3", "4"}), new android.os.BatteryStats.BitDescription(15, 0, "wifi_suppl", "Wsp", WIFI_SUPPL_STATE_NAMES, WIFI_SUPPL_STATE_SHORT_NAMES), new android.os.BatteryStats.BitDescription(2097152, android.content.Context.CAMERA_SERVICE, android.security.Credentials.CERTIFICATE_USAGE_CA), new android.os.BatteryStats.BitDescription(1048576, "ble_scan", "bles"), new android.os.BatteryStats.BitDescription(524288, "cellular_high_tx_power", "Chtp"), new android.os.BatteryStats.BitDescription(384, 7, "gps_signal_quality", "Gss", new java.lang.String[]{"poor", "good", "none"}, new java.lang.String[]{"poor", "good", "none"}), new android.os.BatteryStats.BitDescription(1536, 9, "nr_state", "nrs", new java.lang.String[]{"none", android.net.NetworkPolicyManager.FIREWALL_CHAIN_NAME_RESTRICTED, "not_restricted", "connected"}, new java.lang.String[]{android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS, "1", "2", "3"})};
    private static final java.lang.String FOREGROUND_ACTIVITY_DATA = "fg";
    public static final java.lang.String[] HISTORY_EVENT_NAMES = {"null", "proc", FOREGROUND_ACTIVITY_DATA, "top", "sync", "wake_lock_in", "job", "user", "userfg", "conn", "active", "pkginst", "pkgunin", "alarm", android.content.Context.STATS_MANAGER, "pkginactive", "pkgactive", "tmpwhitelist", "screenwake", "wakeupap", "longwake", "est_capacity"};
    public static final java.lang.String[] HISTORY_EVENT_CHECKIN_NAMES = {"Enl", "Epr", "Efg", "Etp", "Esy", "Ewl", "Ejb", "Eur", "Euf", "Ecn", "Eac", "Epi", "Epu", "Eal", "Est", "Eai", "Eaa", "Etw", "Esw", "Ewa", "Elw", "Eec"};
    private static final android.os.BatteryStats.IntToString sUidToString = new android.os.BatteryStats.IntToString() { // from class: android.os.BatteryStats$$ExternalSyntheticLambda0
        @Override // android.os.BatteryStats.IntToString
        public final java.lang.String applyAsString(int i) {
            return android.os.UserHandle.formatUid(i);
        }
    };
    private static final android.os.BatteryStats.IntToString sIntToString = new android.os.BatteryStats.IntToString() { // from class: android.os.BatteryStats$$ExternalSyntheticLambda1
        @Override // android.os.BatteryStats.IntToString
        public final java.lang.String applyAsString(int i) {
            return java.lang.Integer.toString(i);
        }
    };
    public static final android.os.BatteryStats.IntToString[] HISTORY_EVENT_INT_FORMATTERS = {sUidToString, sUidToString, sUidToString, sUidToString, sUidToString, sUidToString, sUidToString, sUidToString, sUidToString, sUidToString, sUidToString, sIntToString, sUidToString, sUidToString, sUidToString, sUidToString, sUidToString, sUidToString, sUidToString, sUidToString, sUidToString, sIntToString};
    static final java.lang.String[] WIFI_STATE_NAMES = {"off", "scanning", "no_net", "disconn", "sta", "p2p", "sta_p2p", "soft_ap"};
    public static final int[] STEP_LEVEL_MODES_OF_INTEREST = {7, 15, 11, 7, 7, 7, 7, 7, 15, 11};
    public static final int[] STEP_LEVEL_MODE_VALUES = {0, 4, 8, 1, 5, 2, 6, 3, 7, 11};
    public static final java.lang.String[] STEP_LEVEL_MODE_LABELS = {"screen off", "screen off power save", "screen off device idle", "screen on", "screen on power save", "screen doze", "screen doze power save", "screen doze-suspend", "screen doze-suspend power save", "screen doze-suspend device idle"};
    private static final java.lang.String[] CHECKIN_POWER_COMPONENT_LABELS = new java.lang.String[18];

    public interface BatteryStatsDumpHelper {
        android.os.BatteryUsageStats getBatteryUsageStats(android.os.BatteryStats batteryStats, boolean z);
    }

    public static abstract class ControllerActivityCounter {
        public abstract android.os.BatteryStats.LongCounter getIdleTimeCounter();

        public abstract android.os.BatteryStats.LongCounter getMonitoredRailChargeConsumedMaMs();

        public abstract android.os.BatteryStats.LongCounter getPowerCounter();

        public abstract android.os.BatteryStats.LongCounter getRxTimeCounter();

        public abstract android.os.BatteryStats.LongCounter getScanTimeCounter();

        public abstract android.os.BatteryStats.LongCounter getSleepTimeCounter();

        public abstract android.os.BatteryStats.LongCounter[] getTxTimeCounters();
    }

    public static abstract class Counter {
        public abstract int getCountLocked(int i);

        public abstract void logState(android.util.Printer printer, java.lang.String str);
    }

    public static final class DailyItem {
        public android.os.BatteryStats.LevelStepTracker mChargeSteps;
        public android.os.BatteryStats.LevelStepTracker mDischargeSteps;
        public long mEndTime;
        public java.util.ArrayList<android.os.BatteryStats.PackageChange> mPackageChanges;
        public long mStartTime;
    }

    @java.lang.FunctionalInterface
    public interface IntToString {
        java.lang.String applyAsString(int i);
    }

    public static abstract class LongCounter {
        public abstract long getCountForProcessState(int i);

        public abstract long getCountLocked(int i);

        public abstract void logState(android.util.Printer printer, java.lang.String str);
    }

    public static abstract class LongCounterArray {
        public abstract long[] getCountsLocked(int i);

        public abstract void logState(android.util.Printer printer, java.lang.String str);
    }

    public static final class PackageChange {
        public java.lang.String mPackageName;
        public boolean mUpdate;
        public long mVersionCode;
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RadioAccessTechnology {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StatName {
    }

    public abstract void commitCurrentHistoryBatchLocked();

    public abstract long computeBatteryRealtime(long j, int i);

    public abstract long computeBatteryScreenOffRealtime(long j, int i);

    public abstract long computeBatteryScreenOffUptime(long j, int i);

    public abstract long computeBatteryTimeRemaining(long j);

    public abstract long computeBatteryUptime(long j, int i);

    public abstract long computeChargeTimeRemaining(long j);

    public abstract long computeRealtime(long j, int i);

    public abstract long computeUptime(long j, int i);

    public abstract long getActiveRadioDurationMs(int i, int i2, int i3, long j);

    public abstract long getActiveRxRadioDurationMs(int i, int i2, long j);

    public abstract long getActiveTxRadioDurationMs(int i, int i2, int i3, long j);

    public abstract long getBatteryRealtime(long j);

    public abstract long getBatteryUptime(long j);

    public abstract android.os.BluetoothBatteryStats getBluetoothBatteryStats();

    public abstract android.os.BatteryStats.ControllerActivityCounter getBluetoothControllerActivity();

    public abstract long getBluetoothEnergyConsumptionUC();

    public abstract long getBluetoothScanTime(long j, int i);

    public abstract long getCameraEnergyConsumptionUC();

    public abstract long getCameraOnTime(long j, int i);

    public abstract android.os.BatteryStats.LevelStepTracker getChargeLevelStepTracker();

    public abstract long getCpuEnergyConsumptionUC();

    public abstract com.android.internal.os.CpuScalingPolicies getCpuScalingPolicies();

    public abstract long getCurrentDailyStartTime();

    public abstract long[] getCustomEnergyConsumerBatteryConsumptionUC();

    public abstract java.lang.String[] getCustomEnergyConsumerNames();

    public abstract android.os.BatteryStats.LevelStepTracker getDailyChargeLevelStepTracker();

    public abstract android.os.BatteryStats.LevelStepTracker getDailyDischargeLevelStepTracker();

    public abstract android.os.BatteryStats.DailyItem getDailyItemLocked(int i);

    public abstract java.util.ArrayList<android.os.BatteryStats.PackageChange> getDailyPackageChanges();

    public abstract int getDeviceIdleModeCount(int i, int i2);

    public abstract long getDeviceIdleModeTime(int i, long j, int i2);

    public abstract int getDeviceIdlingCount(int i, int i2);

    public abstract long getDeviceIdlingTime(int i, long j, int i2);

    public abstract int getDischargeAmount(int i);

    public abstract int getDischargeAmountScreenDoze();

    public abstract int getDischargeAmountScreenDozeSinceCharge();

    public abstract int getDischargeAmountScreenOff();

    public abstract int getDischargeAmountScreenOffSinceCharge();

    public abstract int getDischargeAmountScreenOn();

    public abstract int getDischargeAmountScreenOnSinceCharge();

    public abstract int getDischargeCurrentLevel();

    public abstract android.os.BatteryStats.LevelStepTracker getDischargeLevelStepTracker();

    public abstract int getDischargeStartLevel();

    public abstract int getDisplayCount();

    public abstract long getDisplayScreenBrightnessTime(int i, int i2, long j);

    public abstract long getDisplayScreenDozeTime(int i, long j);

    public abstract long getDisplayScreenOnTime(int i, long j);

    public abstract java.lang.String getEndPlatformVersion();

    public abstract int getEstimatedBatteryCapacity();

    public abstract long getFlashlightOnCount(int i);

    public abstract long getFlashlightOnTime(long j, int i);

    public abstract long getGlobalWifiRunningTime(long j, int i);

    public abstract long getGnssEnergyConsumptionUC();

    public abstract long getGpsBatteryDrainMaMs();

    public abstract long getGpsSignalQualityTime(int i, long j, int i2);

    public abstract int getHighDischargeAmountSinceCharge();

    public abstract int getHistoryStringPoolBytes();

    public abstract int getHistoryStringPoolSize();

    public abstract java.lang.String getHistoryTagPoolString(int i);

    public abstract int getHistoryTagPoolUid(int i);

    public abstract int getHistoryTotalSize();

    public abstract int getHistoryUsedSize();

    public abstract long getInteractiveTime(long j, int i);

    public abstract boolean getIsOnBattery();

    public abstract android.util.LongSparseArray<? extends android.os.BatteryStats.Timer> getKernelMemoryStats();

    public abstract java.util.Map<java.lang.String, ? extends android.os.BatteryStats.Timer> getKernelWakelockStats();

    public abstract int getLearnedBatteryCapacity();

    public abstract long getLongestDeviceIdleModeTime(int i);

    public abstract int getLowDischargeAmountSinceCharge();

    public abstract int getMaxLearnedBatteryCapacity();

    public abstract int getMinLearnedBatteryCapacity();

    public abstract long getMobileRadioActiveAdjustedTime(int i);

    public abstract int getMobileRadioActiveCount(int i);

    public abstract long getMobileRadioActiveTime(long j, int i);

    public abstract int getMobileRadioActiveUnknownCount(int i);

    public abstract long getMobileRadioActiveUnknownTime(int i);

    public abstract long getMobileRadioEnergyConsumptionUC();

    public abstract android.os.BatteryStats.ControllerActivityCounter getModemControllerActivity();

    public abstract long getNetworkActivityBytes(int i, int i2);

    public abstract long getNetworkActivityPackets(int i, int i2);

    public abstract long getNextMaxDailyDeadline();

    public abstract long getNextMinDailyDeadline();

    public abstract long getNrNsaTime(long j);

    public abstract int getNumConnectivityChange(int i);

    public abstract int getParcelVersion();

    public abstract int getPhoneDataConnectionCount(int i, int i2);

    public abstract long getPhoneDataConnectionTime(int i, long j, int i2);

    public abstract android.os.BatteryStats.Timer getPhoneDataConnectionTimer(int i);

    public abstract long getPhoneEnergyConsumptionUC();

    public abstract int getPhoneOnCount(int i);

    public abstract long getPhoneOnTime(long j, int i);

    public abstract long getPhoneSignalScanningTime(long j, int i);

    public abstract android.os.BatteryStats.Timer getPhoneSignalScanningTimer();

    public abstract int getPhoneSignalStrengthCount(int i, int i2);

    public abstract long getPhoneSignalStrengthTime(int i, long j, int i2);

    protected abstract android.os.BatteryStats.Timer getPhoneSignalStrengthTimer(int i);

    public abstract int getPowerSaveModeEnabledCount(int i);

    public abstract long getPowerSaveModeEnabledTime(long j, int i);

    public abstract java.util.Map<java.lang.String, ? extends android.os.BatteryStats.Timer> getRpmStats();

    public abstract long getScreenBrightnessTime(int i, long j, int i2);

    public abstract android.os.BatteryStats.Timer getScreenBrightnessTimer(int i);

    public abstract int getScreenDozeCount(int i);

    public abstract long getScreenDozeEnergyConsumptionUC();

    public abstract long getScreenDozeTime(long j, int i);

    public abstract java.util.Map<java.lang.String, ? extends android.os.BatteryStats.Timer> getScreenOffRpmStats();

    public abstract int getScreenOnCount(int i);

    public abstract long getScreenOnEnergyConsumptionUC();

    public abstract long getScreenOnTime(long j, int i);

    public abstract long getStartClockTime();

    public abstract int getStartCount();

    public abstract java.lang.String getStartPlatformVersion();

    public abstract long getStatsStartRealtime();

    public abstract long[] getSystemServiceTimeAtCpuSpeeds();

    public abstract long getUahDischarge(int i);

    public abstract long getUahDischargeDeepDoze(int i);

    public abstract long getUahDischargeLightDoze(int i);

    public abstract long getUahDischargeScreenDoze(int i);

    public abstract long getUahDischargeScreenOff(int i);

    public abstract android.util.SparseArray<? extends android.os.BatteryStats.Uid> getUidStats();

    public abstract android.os.WakeLockStats getWakeLockStats();

    public abstract java.util.Map<java.lang.String, ? extends android.os.BatteryStats.Timer> getWakeupReasonStats();

    public abstract long getWifiActiveTime(long j, int i);

    public abstract android.os.BatteryStats.ControllerActivityCounter getWifiControllerActivity();

    public abstract long getWifiEnergyConsumptionUC();

    public abstract int getWifiMulticastWakelockCount(int i);

    public abstract long getWifiMulticastWakelockTime(long j, int i);

    public abstract long getWifiOnTime(long j, int i);

    public abstract int getWifiSignalStrengthCount(int i, int i2);

    public abstract long getWifiSignalStrengthTime(int i, long j, int i2);

    public abstract android.os.BatteryStats.Timer getWifiSignalStrengthTimer(int i);

    public abstract int getWifiStateCount(int i, int i2);

    public abstract long getWifiStateTime(int i, long j, int i2);

    public abstract android.os.BatteryStats.Timer getWifiStateTimer(int i);

    public abstract int getWifiSupplStateCount(int i, int i2);

    public abstract long getWifiSupplStateTime(int i, long j, int i2);

    public abstract android.os.BatteryStats.Timer getWifiSupplStateTimer(int i);

    public abstract boolean hasBluetoothActivityReporting();

    public abstract boolean hasModemActivityReporting();

    public abstract boolean hasWifiActivityReporting();

    public abstract boolean isProcessStateDataAvailable();

    public abstract com.android.internal.os.BatteryStatsHistoryIterator iterateBatteryStatsHistory(long j, long j2);

    static {
        CHECKIN_POWER_COMPONENT_LABELS[0] = "scrn";
        CHECKIN_POWER_COMPONENT_LABELS[1] = CPU_DATA;
        CHECKIN_POWER_COMPONENT_LABELS[2] = "blue";
        CHECKIN_POWER_COMPONENT_LABELS[3] = android.content.Context.CAMERA_SERVICE;
        CHECKIN_POWER_COMPONENT_LABELS[4] = "audio";
        CHECKIN_POWER_COMPONENT_LABELS[5] = "video";
        CHECKIN_POWER_COMPONENT_LABELS[6] = "flashlight";
        CHECKIN_POWER_COMPONENT_LABELS[8] = "cell";
        CHECKIN_POWER_COMPONENT_LABELS[9] = "sensors";
        CHECKIN_POWER_COMPONENT_LABELS[10] = "gnss";
        CHECKIN_POWER_COMPONENT_LABELS[11] = "wifi";
        CHECKIN_POWER_COMPONENT_LABELS[13] = "memory";
        CHECKIN_POWER_COMPONENT_LABELS[14] = "phone";
        CHECKIN_POWER_COMPONENT_LABELS[15] = "ambi";
        CHECKIN_POWER_COMPONENT_LABELS[16] = "idle";
        DISPLAY_TRANSPORT_PRIORITIES = new int[]{4, 0, 5, 2, 1, 3, 8};
    }

    public static abstract class Timer {
        public abstract int getCountLocked(int i);

        public abstract long getTimeSinceMarkLocked(long j);

        public abstract long getTotalTimeLocked(long j, int i);

        public abstract void logState(android.util.Printer printer, java.lang.String str);

        public long getMaxDurationMsLocked(long j) {
            return -1L;
        }

        public long getCurrentDurationMsLocked(long j) {
            return -1L;
        }

        public long getTotalDurationMsLocked(long j) {
            return -1L;
        }

        public android.os.BatteryStats.Timer getSubTimer() {
            return null;
        }

        public boolean isRunningLocked() {
            return false;
        }
    }

    public static int mapToInternalProcessState(int i) {
        if (i == 20) {
            return 7;
        }
        if (i == 2) {
            return 0;
        }
        if (i == 3) {
            return 3;
        }
        if (i == 4 || i == 5) {
            return 1;
        }
        if (i <= 6) {
            return 2;
        }
        if (i <= 11) {
            return 3;
        }
        if (i <= 12) {
            return 4;
        }
        if (i <= 13) {
            return 5;
        }
        return 6;
    }

    public static int mapUidProcessStateToBatteryConsumerProcessState(int i) {
        switch (i) {
            case 0:
            case 2:
                return 1;
            case 1:
                return 3;
            case 3:
            case 4:
                return 2;
            case 5:
            default:
                return 0;
            case 6:
                return 4;
        }
    }

    public static abstract class Uid {
        public static final int NUM_PROCESS_STATE = 7;
        public static final int NUM_WIFI_BATCHED_SCAN_BINS = 5;
        public static final int PROCESS_STATE_BACKGROUND = 3;
        public static final int PROCESS_STATE_CACHED = 6;
        public static final int PROCESS_STATE_FOREGROUND = 2;
        public static final int PROCESS_STATE_FOREGROUND_SERVICE = 1;
        public static final int PROCESS_STATE_HEAVY_WEIGHT = 5;
        public static final int PROCESS_STATE_NONEXISTENT = 7;
        public static final int PROCESS_STATE_TOP = 0;
        public static final int PROCESS_STATE_TOP_SLEEPING = 4;
        static final java.lang.String[] PROCESS_STATE_NAMES = {"Top", "Fg Service", "Foreground", "Background", "Top Sleeping", "Heavy Weight", "Cached"};
        public static final java.lang.String[] UID_PROCESS_TYPES = {"T", "FS", "F", android.hardware.gnss.GnssSignalType.CODE_TYPE_B, "TS", "HW", android.hardware.gnss.GnssSignalType.CODE_TYPE_C};
        static final java.lang.String[] USER_ACTIVITY_TYPES = {"other", "button", "touch", android.content.Context.ACCESSIBILITY_SERVICE, android.content.Context.ATTENTION_SERVICE, "faceDown", "deviceState"};
        public static final int NUM_USER_ACTIVITY_TYPES = USER_ACTIVITY_TYPES.length;

        public static abstract class Pkg {

            public static abstract class Serv {
                public abstract int getLaunches(int i);

                public abstract long getStartTime(long j, int i);

                public abstract int getStarts(int i);
            }

            public abstract android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Pkg.Serv> getServiceStats();

            public abstract android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Counter> getWakeupAlarmStats();
        }

        public static abstract class Proc {

            public static class ExcessivePower {
                public static final int TYPE_CPU = 2;
                public static final int TYPE_WAKE = 1;
                public long overTime;
                public int type;
                public long usedTime;
            }

            public abstract int countExcessivePowers();

            public abstract android.os.BatteryStats.Uid.Proc.ExcessivePower getExcessivePower(int i);

            public abstract long getForegroundTime(int i);

            public abstract int getNumAnrs(int i);

            public abstract int getNumCrashes(int i);

            public abstract int getStarts(int i);

            public abstract long getSystemTime(int i);

            public abstract long getUserTime(int i);

            public abstract boolean isActive();
        }

        public static abstract class Sensor {
            public static final int GPS = -10000;

            public abstract int getHandle();

            public abstract android.os.BatteryStats.Timer getSensorBackgroundTime();

            public abstract android.os.BatteryStats.Timer getSensorTime();
        }

        public static abstract class Wakelock {
            public abstract android.os.BatteryStats.Timer getWakeTime(int i);
        }

        public abstract android.os.BatteryStats.Timer getAggregatedPartialWakelockTimer();

        public abstract android.os.BatteryStats.Timer getAudioTurnedOnTimer();

        public abstract android.os.BatteryStats.ControllerActivityCounter getBluetoothControllerActivity();

        public abstract long getBluetoothEnergyConsumptionUC();

        public abstract long getBluetoothEnergyConsumptionUC(int i);

        public abstract android.os.BatteryStats.Timer getBluetoothScanBackgroundTimer();

        public abstract android.os.BatteryStats.Counter getBluetoothScanResultBgCounter();

        public abstract android.os.BatteryStats.Counter getBluetoothScanResultCounter();

        public abstract android.os.BatteryStats.Timer getBluetoothScanTimer();

        public abstract android.os.BatteryStats.Timer getBluetoothUnoptimizedScanBackgroundTimer();

        public abstract android.os.BatteryStats.Timer getBluetoothUnoptimizedScanTimer();

        public abstract long getCameraEnergyConsumptionUC();

        public abstract android.os.BatteryStats.Timer getCameraTurnedOnTimer();

        public abstract long getCpuActiveTime();

        public abstract long getCpuActiveTime(int i);

        public abstract long[] getCpuClusterTimes();

        public abstract long getCpuEnergyConsumptionUC();

        public abstract long getCpuEnergyConsumptionUC(int i);

        public abstract boolean getCpuFreqTimes(long[] jArr, int i);

        public abstract long[] getCpuFreqTimes(int i);

        public abstract long[] getCustomEnergyConsumerBatteryConsumptionUC();

        public abstract void getDeferredJobsCheckinLineLocked(java.lang.StringBuilder sb, int i);

        public abstract void getDeferredJobsLineLocked(java.lang.StringBuilder sb, int i);

        public abstract android.os.BatteryStats.Timer getFlashlightTurnedOnTimer();

        public abstract android.os.BatteryStats.Timer getForegroundActivityTimer();

        public abstract android.os.BatteryStats.Timer getForegroundServiceTimer();

        public abstract long getFullWifiLockTime(long j, int i);

        public abstract long getGnssEnergyConsumptionUC();

        public abstract android.util.ArrayMap<java.lang.String, android.util.SparseIntArray> getJobCompletionStats();

        public abstract android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Timer> getJobStats();

        public abstract int getMobileRadioActiveCount(int i);

        public abstract long getMobileRadioActiveTime(int i);

        public abstract long getMobileRadioActiveTimeInProcessState(int i);

        public abstract long getMobileRadioApWakeupCount(int i);

        public abstract long getMobileRadioEnergyConsumptionUC();

        public abstract long getMobileRadioEnergyConsumptionUC(int i);

        public abstract android.os.BatteryStats.ControllerActivityCounter getModemControllerActivity();

        public abstract android.os.BatteryStats.Timer getMulticastWakelockStats();

        public abstract long getNetworkActivityBytes(int i, int i2);

        public abstract long getNetworkActivityPackets(int i, int i2);

        public abstract android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Pkg> getPackageStats();

        public abstract android.util.SparseArray<? extends android.os.BatteryStats.Uid.Pid> getPidStats();

        public abstract long getProcessStateTime(int i, long j, int i2);

        public abstract android.os.BatteryStats.Timer getProcessStateTimer(int i);

        public abstract android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Proc> getProcessStats();

        public abstract double getProportionalSystemServiceUsage();

        public abstract boolean getScreenOffCpuFreqTimes(long[] jArr, int i);

        public abstract long[] getScreenOffCpuFreqTimes(int i);

        public abstract long getScreenOnEnergyConsumptionUC();

        public abstract android.util.SparseArray<? extends android.os.BatteryStats.Uid.Sensor> getSensorStats();

        public abstract android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Timer> getSyncStats();

        public abstract long getSystemCpuTimeUs(int i);

        @java.lang.Deprecated
        public abstract long getTimeAtCpuSpeed(int i, int i2, int i3);

        public abstract int getUid();

        public abstract int getUserActivityCount(int i, int i2);

        public abstract long getUserCpuTimeUs(int i);

        public abstract android.os.BatteryStats.Timer getVibratorOnTimer();

        public abstract android.os.BatteryStats.Timer getVideoTurnedOnTimer();

        public abstract android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Wakelock> getWakelockStats();

        public abstract int getWifiBatchedScanCount(int i, int i2);

        public abstract long getWifiBatchedScanTime(int i, long j, int i2);

        public abstract android.os.BatteryStats.ControllerActivityCounter getWifiControllerActivity();

        public abstract long getWifiEnergyConsumptionUC();

        public abstract long getWifiEnergyConsumptionUC(int i);

        public abstract long getWifiMulticastTime(long j, int i);

        public abstract long getWifiRadioApWakeupCount(int i);

        public abstract long getWifiRunningTime(long j, int i);

        public abstract long getWifiScanActualTime(long j);

        public abstract int getWifiScanBackgroundCount(int i);

        public abstract long getWifiScanBackgroundTime(long j);

        public abstract android.os.BatteryStats.Timer getWifiScanBackgroundTimer();

        public abstract int getWifiScanCount(int i);

        public abstract long getWifiScanTime(long j, int i);

        public abstract android.os.BatteryStats.Timer getWifiScanTimer();

        public abstract boolean hasNetworkActivity();

        public abstract boolean hasUserActivity();

        public abstract void noteActivityPausedLocked(long j);

        public abstract void noteActivityResumedLocked(long j);

        public abstract void noteFullWifiLockAcquiredLocked(long j);

        public abstract void noteFullWifiLockReleasedLocked(long j);

        public abstract void noteUserActivityLocked(int i);

        public abstract void noteWifiBatchedScanStartedLocked(int i, long j);

        public abstract void noteWifiBatchedScanStoppedLocked(long j);

        public abstract void noteWifiMulticastDisabledLocked(long j);

        public abstract void noteWifiMulticastEnabledLocked(long j);

        public abstract void noteWifiRunningLocked(long j);

        public abstract void noteWifiScanStartedLocked(long j);

        public abstract void noteWifiScanStoppedLocked(long j);

        public abstract void noteWifiStoppedLocked(long j);

        public class Pid {
            public int mWakeNesting;
            public long mWakeStartMs;
            public long mWakeSumMs;

            public Pid() {
            }
        }
    }

    public static final class LevelStepTracker {
        public long mLastStepTime = -1;
        public int mNumStepDurations;
        public final long[] mStepDurations;

        public LevelStepTracker(int i) {
            this.mStepDurations = new long[i];
        }

        public LevelStepTracker(int i, long[] jArr) {
            this.mNumStepDurations = i;
            this.mStepDurations = new long[i];
            java.lang.System.arraycopy(jArr, 0, this.mStepDurations, 0, i);
        }

        public long getDurationAt(int i) {
            return this.mStepDurations[i] & android.os.BatteryStats.STEP_LEVEL_TIME_MASK;
        }

        public int getLevelAt(int i) {
            return (int) ((this.mStepDurations[i] & android.os.BatteryStats.STEP_LEVEL_LEVEL_MASK) >> 40);
        }

        public int getInitModeAt(int i) {
            return (int) ((this.mStepDurations[i] & android.os.BatteryStats.STEP_LEVEL_INITIAL_MODE_MASK) >> 48);
        }

        public int getModModeAt(int i) {
            return (int) ((this.mStepDurations[i] & android.os.BatteryStats.STEP_LEVEL_MODIFIED_MODE_MASK) >> 56);
        }

        private void appendHex(long j, int i, java.lang.StringBuilder sb) {
            boolean z = false;
            while (i >= 0) {
                int i2 = (int) ((j >> i) & 15);
                i -= 4;
                if (z || i2 != 0) {
                    if (i2 >= 0 && i2 <= 9) {
                        sb.append((char) (i2 + 48));
                    } else {
                        sb.append((char) ((i2 + 97) - 10));
                    }
                    z = true;
                }
            }
        }

        public void encodeEntryAt(int i, java.lang.StringBuilder sb) {
            long j = this.mStepDurations[i];
            long j2 = android.os.BatteryStats.STEP_LEVEL_TIME_MASK & j;
            int i2 = (int) ((android.os.BatteryStats.STEP_LEVEL_LEVEL_MASK & j) >> 40);
            int i3 = (int) ((android.os.BatteryStats.STEP_LEVEL_INITIAL_MODE_MASK & j) >> 48);
            int i4 = (int) ((j & android.os.BatteryStats.STEP_LEVEL_MODIFIED_MODE_MASK) >> 56);
            switch ((i3 & 3) + 1) {
                case 1:
                    sb.append('f');
                    break;
                case 2:
                    sb.append('o');
                    break;
                case 3:
                    sb.append(android.text.format.DateFormat.DATE);
                    break;
                case 4:
                    sb.append(android.text.format.DateFormat.TIME_ZONE);
                    break;
            }
            if ((i3 & 4) != 0) {
                sb.append('p');
            }
            if ((i3 & 8) != 0) {
                sb.append('i');
            }
            switch ((i4 & 3) + 1) {
                case 1:
                    sb.append('F');
                    break;
                case 2:
                    sb.append('O');
                    break;
                case 3:
                    sb.append('D');
                    break;
                case 4:
                    sb.append('Z');
                    break;
            }
            if ((i4 & 4) != 0) {
                sb.append('P');
            }
            if ((i4 & 8) != 0) {
                sb.append('I');
            }
            sb.append('-');
            appendHex(i2, 4, sb);
            sb.append('-');
            appendHex(j2, 36, sb);
        }

        public void decodeEntryAt(int i, java.lang.String str) {
            char charAt;
            char charAt2;
            int length = str.length();
            int i2 = 0;
            long j = 0;
            while (i2 < length && (charAt2 = str.charAt(i2)) != '-') {
                i2++;
                switch (charAt2) {
                    case 'D':
                        j |= 144115188075855872L;
                        break;
                    case 'F':
                        j |= 0;
                        break;
                    case 'I':
                        j |= 576460752303423488L;
                        break;
                    case 'O':
                        j |= 72057594037927936L;
                        break;
                    case 'P':
                        j |= 288230376151711744L;
                        break;
                    case 'Z':
                        j |= 216172782113783808L;
                        break;
                    case 'd':
                        j |= android.hardware.tv.tuner.FrontendInnerFec.FEC_128_180;
                        break;
                    case 'f':
                        j |= 0;
                        break;
                    case 'i':
                        j |= android.hardware.tv.tuner.FrontendInnerFec.FEC_135_180;
                        break;
                    case 'o':
                        j |= android.hardware.tv.tuner.FrontendInnerFec.FEC_104_180;
                        break;
                    case 'p':
                        j |= android.hardware.tv.tuner.FrontendInnerFec.FEC_132_180;
                        break;
                    case 'z':
                        j |= 844424930131968L;
                        break;
                }
            }
            int i3 = i2 + 1;
            long j2 = 0;
            while (i3 < length && (charAt = str.charAt(i3)) != '-') {
                i3++;
                j2 <<= 4;
                if (charAt >= '0' && charAt <= '9') {
                    j2 += charAt - '0';
                } else if (charAt >= 'a' && charAt <= 'f') {
                    j2 += (charAt - 'a') + 10;
                } else if (charAt >= 'A' && charAt <= 'F') {
                    j2 += (charAt - 'A') + 10;
                }
            }
            int i4 = i3 + 1;
            long j3 = j | ((j2 << 40) & android.os.BatteryStats.STEP_LEVEL_LEVEL_MASK);
            long j4 = 0;
            while (i4 < length) {
                char charAt3 = str.charAt(i4);
                if (charAt3 != '-') {
                    i4++;
                    j4 <<= 4;
                    if (charAt3 >= '0' && charAt3 <= '9') {
                        j4 += charAt3 - '0';
                    } else if (charAt3 >= 'a' && charAt3 <= 'f') {
                        j4 += (charAt3 - 'a') + 10;
                    } else if (charAt3 >= 'A' && charAt3 <= 'F') {
                        j4 += (charAt3 - 'A') + 10;
                    }
                } else {
                    this.mStepDurations[i] = (j4 & android.os.BatteryStats.STEP_LEVEL_TIME_MASK) | j3;
                }
            }
            this.mStepDurations[i] = (j4 & android.os.BatteryStats.STEP_LEVEL_TIME_MASK) | j3;
        }

        public void init() {
            this.mLastStepTime = -1L;
            this.mNumStepDurations = 0;
        }

        public void clearTime() {
            this.mLastStepTime = -1L;
        }

        public long computeTimePerLevel() {
            long[] jArr = this.mStepDurations;
            int i = this.mNumStepDurations;
            if (i <= 0) {
                return -1L;
            }
            long j = 0;
            for (int i2 = 0; i2 < i; i2++) {
                j += jArr[i2] & android.os.BatteryStats.STEP_LEVEL_TIME_MASK;
            }
            return j / i;
        }

        public long computeTimeEstimate(long j, long j2, int[] iArr) {
            long[] jArr = this.mStepDurations;
            int i = this.mNumStepDurations;
            if (i <= 0) {
                return -1L;
            }
            long j3 = 0;
            int i2 = 0;
            for (int i3 = 0; i3 < i; i3++) {
                long j4 = (jArr[i3] & android.os.BatteryStats.STEP_LEVEL_INITIAL_MODE_MASK) >> 48;
                if ((((jArr[i3] & android.os.BatteryStats.STEP_LEVEL_MODIFIED_MODE_MASK) >> 56) & j) == 0 && (j4 & j) == j2) {
                    i2++;
                    j3 += jArr[i3] & android.os.BatteryStats.STEP_LEVEL_TIME_MASK;
                }
            }
            if (i2 <= 0) {
                return -1L;
            }
            if (iArr != null) {
                iArr[0] = i2;
            }
            return (j3 / i2) * 100;
        }

        public void addLevelSteps(int i, long j, long j2) {
            int i2 = this.mNumStepDurations;
            long j3 = this.mLastStepTime;
            if (j3 >= 0 && i > 0) {
                long[] jArr = this.mStepDurations;
                long j4 = j2 - j3;
                for (int i3 = 0; i3 < i; i3++) {
                    java.lang.System.arraycopy(jArr, 0, jArr, 1, jArr.length - 1);
                    long j5 = j4 / (i - i3);
                    j4 -= j5;
                    if (j5 > android.os.BatteryStats.STEP_LEVEL_TIME_MASK) {
                        j5 = 1099511627775L;
                    }
                    jArr[0] = j5 | j;
                }
                i2 += i;
                if (i2 > jArr.length) {
                    i2 = jArr.length;
                }
            }
            this.mNumStepDurations = i2;
            this.mLastStepTime = j2;
        }

        public void readFromParcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            if (readInt > this.mStepDurations.length) {
                throw new android.os.ParcelFormatException("more step durations than available: " + readInt);
            }
            this.mNumStepDurations = readInt;
            for (int i = 0; i < readInt; i++) {
                this.mStepDurations[i] = parcel.readLong();
            }
        }

        public void writeToParcel(android.os.Parcel parcel) {
            int i = this.mNumStepDurations;
            parcel.writeInt(i);
            for (int i2 = 0; i2 < i; i2++) {
                parcel.writeLong(this.mStepDurations[i2]);
            }
        }
    }

    public static final class HistoryTag {
        public static final int HISTORY_TAG_POOL_OVERFLOW = -1;
        public int poolIdx;
        public java.lang.String string;
        public int uid;

        public void setTo(android.os.BatteryStats.HistoryTag historyTag) {
            this.string = historyTag.string;
            this.uid = historyTag.uid;
            this.poolIdx = historyTag.poolIdx;
        }

        public void setTo(java.lang.String str, int i) {
            this.string = str;
            this.uid = i;
            this.poolIdx = -1;
        }

        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeString(this.string);
            parcel.writeInt(this.uid);
        }

        public void readFromParcel(android.os.Parcel parcel) {
            this.string = parcel.readString();
            this.uid = parcel.readInt();
            this.poolIdx = -1;
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.os.BatteryStats.HistoryTag historyTag = (android.os.BatteryStats.HistoryTag) obj;
            if (this.uid == historyTag.uid && this.string.equals(historyTag.string)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (this.string.hashCode() * 31) + this.uid;
        }
    }

    public static final class HistoryStepDetails {
        public int appCpuSTime1;
        public int appCpuSTime2;
        public int appCpuSTime3;
        public int appCpuUTime1;
        public int appCpuUTime2;
        public int appCpuUTime3;
        public int appCpuUid1;
        public int appCpuUid2;
        public int appCpuUid3;
        public int statIOWaitTime;
        public int statIdlTime;
        public int statIrqTime;
        public int statSoftIrqTime;
        public java.lang.String statSubsystemPowerState;
        public int statSystemTime;
        public int statUserTime;
        public int systemTime;
        public int userTime;

        public HistoryStepDetails() {
            clear();
        }

        public void clear() {
            this.systemTime = 0;
            this.userTime = 0;
            this.appCpuUid3 = -1;
            this.appCpuUid2 = -1;
            this.appCpuUid1 = -1;
            this.appCpuSTime3 = 0;
            this.appCpuUTime3 = 0;
            this.appCpuSTime2 = 0;
            this.appCpuUTime2 = 0;
            this.appCpuSTime1 = 0;
            this.appCpuUTime1 = 0;
        }

        public void writeToParcel(android.os.Parcel parcel) {
            parcel.writeInt(this.userTime);
            parcel.writeInt(this.systemTime);
            parcel.writeInt(this.appCpuUid1);
            parcel.writeInt(this.appCpuUTime1);
            parcel.writeInt(this.appCpuSTime1);
            parcel.writeInt(this.appCpuUid2);
            parcel.writeInt(this.appCpuUTime2);
            parcel.writeInt(this.appCpuSTime2);
            parcel.writeInt(this.appCpuUid3);
            parcel.writeInt(this.appCpuUTime3);
            parcel.writeInt(this.appCpuSTime3);
            parcel.writeInt(this.statUserTime);
            parcel.writeInt(this.statSystemTime);
            parcel.writeInt(this.statIOWaitTime);
            parcel.writeInt(this.statIrqTime);
            parcel.writeInt(this.statSoftIrqTime);
            parcel.writeInt(this.statIdlTime);
            parcel.writeString(this.statSubsystemPowerState);
        }

        public void readFromParcel(android.os.Parcel parcel) {
            this.userTime = parcel.readInt();
            this.systemTime = parcel.readInt();
            this.appCpuUid1 = parcel.readInt();
            this.appCpuUTime1 = parcel.readInt();
            this.appCpuSTime1 = parcel.readInt();
            this.appCpuUid2 = parcel.readInt();
            this.appCpuUTime2 = parcel.readInt();
            this.appCpuSTime2 = parcel.readInt();
            this.appCpuUid3 = parcel.readInt();
            this.appCpuUTime3 = parcel.readInt();
            this.appCpuSTime3 = parcel.readInt();
            this.statUserTime = parcel.readInt();
            this.statSystemTime = parcel.readInt();
            this.statIOWaitTime = parcel.readInt();
            this.statIrqTime = parcel.readInt();
            this.statSoftIrqTime = parcel.readInt();
            this.statIdlTime = parcel.readInt();
            this.statSubsystemPowerState = parcel.readString();
        }
    }

    public static final class ProcessStateChange {
        private static final int LARGE_UID_FLAG = Integer.MIN_VALUE;
        private static final int PROC_STATE_MASK = 2130706432;
        private static final int PROC_STATE_SHIFT = java.lang.Integer.numberOfTrailingZeros(PROC_STATE_MASK);
        private static final int SMALL_UID_MASK = 16777215;
        public int processState;
        public int uid;

        public void writeToParcel(android.os.Parcel parcel) {
            int i = this.processState << PROC_STATE_SHIFT;
            if ((this.uid & (-16777216)) == 0) {
                parcel.writeInt(i | this.uid);
            } else {
                parcel.writeInt(i | Integer.MIN_VALUE);
                parcel.writeInt(this.uid);
            }
        }

        public void readFromParcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            this.processState = (PROC_STATE_MASK & readInt) >>> PROC_STATE_SHIFT;
            if (this.processState >= 5) {
                android.util.Slog.e(android.os.BatteryStats.TAG, "Unrecognized proc state in battery history: " + this.processState);
                this.processState = 0;
            }
            if ((Integer.MIN_VALUE & readInt) == 0) {
                this.uid = (-2130706433) & readInt;
            } else {
                this.uid = parcel.readInt();
            }
        }

        public java.lang.String formatForBatteryHistory() {
            return android.os.UserHandle.formatUid(this.uid) + ": " + android.os.BatteryConsumer.processStateToString(this.processState);
        }
    }

    public static final class HistoryItem {
        public static final byte CMD_CURRENT_TIME = 5;
        public static final byte CMD_NULL = -1;
        public static final byte CMD_OVERFLOW = 6;
        public static final byte CMD_RESET = 7;
        public static final byte CMD_SHUTDOWN = 8;
        public static final byte CMD_START = 4;
        public static final byte CMD_UPDATE = 0;
        public static final int EVENT_ACTIVE = 10;
        public static final int EVENT_ALARM = 13;
        public static final int EVENT_ALARM_FINISH = 16397;
        public static final int EVENT_ALARM_START = 32781;
        public static final int EVENT_COLLECT_EXTERNAL_STATS = 14;
        public static final int EVENT_CONNECTIVITY_CHANGED = 9;
        public static final int EVENT_COUNT = 22;
        public static final int EVENT_FLAG_FINISH = 16384;
        public static final int EVENT_FLAG_START = 32768;
        public static final int EVENT_FOREGROUND = 2;
        public static final int EVENT_FOREGROUND_FINISH = 16386;
        public static final int EVENT_FOREGROUND_START = 32770;
        public static final int EVENT_JOB = 6;
        public static final int EVENT_JOB_FINISH = 16390;
        public static final int EVENT_JOB_START = 32774;
        public static final int EVENT_LONG_WAKE_LOCK = 20;
        public static final int EVENT_LONG_WAKE_LOCK_FINISH = 16404;
        public static final int EVENT_LONG_WAKE_LOCK_START = 32788;
        public static final int EVENT_NONE = 0;
        public static final int EVENT_PACKAGE_ACTIVE = 16;
        public static final int EVENT_PACKAGE_INACTIVE = 15;
        public static final int EVENT_PACKAGE_INSTALLED = 11;
        public static final int EVENT_PACKAGE_UNINSTALLED = 12;
        public static final int EVENT_PROC = 1;
        public static final int EVENT_PROC_FINISH = 16385;
        public static final int EVENT_PROC_START = 32769;
        public static final int EVENT_SCREEN_WAKE_UP = 18;
        public static final int EVENT_SYNC = 4;
        public static final int EVENT_SYNC_FINISH = 16388;
        public static final int EVENT_SYNC_START = 32772;
        public static final int EVENT_TEMP_WHITELIST = 17;
        public static final int EVENT_TEMP_WHITELIST_FINISH = 16401;
        public static final int EVENT_TEMP_WHITELIST_START = 32785;
        public static final int EVENT_TOP = 3;
        public static final int EVENT_TOP_FINISH = 16387;
        public static final int EVENT_TOP_START = 32771;
        public static final int EVENT_TYPE_MASK = -49153;
        public static final int EVENT_USER_FOREGROUND = 8;
        public static final int EVENT_USER_FOREGROUND_FINISH = 16392;
        public static final int EVENT_USER_FOREGROUND_START = 32776;
        public static final int EVENT_USER_RUNNING = 7;
        public static final int EVENT_USER_RUNNING_FINISH = 16391;
        public static final int EVENT_USER_RUNNING_START = 32775;
        public static final int EVENT_WAKEUP_AP = 19;
        public static final int EVENT_WAKE_LOCK = 5;
        public static final int EVENT_WAKE_LOCK_FINISH = 16389;
        public static final int EVENT_WAKE_LOCK_START = 32773;
        public static final int MOST_INTERESTING_STATES = 1835008;
        public static final int MOST_INTERESTING_STATES2 = -1749024768;
        public static final int SETTLE_TO_ZERO_STATES = -1900544;
        public static final int SETTLE_TO_ZERO_STATES2 = 1748959232;
        public static final int STATE2_BLUETOOTH_ON_FLAG = 4194304;
        public static final int STATE2_BLUETOOTH_SCAN_FLAG = 1048576;
        public static final int STATE2_CAMERA_FLAG = 2097152;
        public static final int STATE2_CELLULAR_HIGH_TX_POWER_FLAG = 524288;
        public static final int STATE2_CHARGING_FLAG = 16777216;
        public static final int STATE2_DEVICE_IDLE_MASK = 100663296;
        public static final int STATE2_DEVICE_IDLE_SHIFT = 25;
        public static final int STATE2_EXTENSIONS_FLAG = 131072;
        public static final int STATE2_FLASHLIGHT_FLAG = 134217728;
        public static final int STATE2_GPS_SIGNAL_QUALITY_MASK = 384;
        public static final int STATE2_GPS_SIGNAL_QUALITY_SHIFT = 7;
        public static final int STATE2_NR_STATE_MASK = 1536;
        public static final int STATE2_NR_STATE_SHIFT = 9;
        public static final int STATE2_PHONE_IN_CALL_FLAG = 8388608;
        public static final int STATE2_POWER_SAVE_FLAG = Integer.MIN_VALUE;
        public static final int STATE2_USB_DATA_LINK_FLAG = 262144;
        public static final int STATE2_VIDEO_ON_FLAG = 1073741824;
        public static final int STATE2_WIFI_ON_FLAG = 268435456;
        public static final int STATE2_WIFI_RUNNING_FLAG = 536870912;
        public static final int STATE2_WIFI_SIGNAL_STRENGTH_MASK = 112;
        public static final int STATE2_WIFI_SIGNAL_STRENGTH_SHIFT = 4;
        public static final int STATE2_WIFI_SUPPL_STATE_MASK = 15;
        public static final int STATE2_WIFI_SUPPL_STATE_SHIFT = 0;
        public static final int STATE_AUDIO_ON_FLAG = 4194304;
        public static final int STATE_BATTERY_PLUGGED_FLAG = 524288;
        public static final int STATE_BRIGHTNESS_MASK = 7;
        public static final int STATE_BRIGHTNESS_SHIFT = 0;
        public static final int STATE_CPU_RUNNING_FLAG = Integer.MIN_VALUE;
        public static final int STATE_DATA_CONNECTION_MASK = 15872;
        public static final int STATE_DATA_CONNECTION_SHIFT = 9;
        public static final int STATE_GPS_ON_FLAG = 536870912;
        public static final int STATE_MOBILE_RADIO_ACTIVE_FLAG = 33554432;
        public static final int STATE_PHONE_SCANNING_FLAG = 2097152;
        public static final int STATE_PHONE_SIGNAL_STRENGTH_MASK = 56;
        public static final int STATE_PHONE_SIGNAL_STRENGTH_SHIFT = 3;
        public static final int STATE_PHONE_STATE_MASK = 448;
        public static final int STATE_PHONE_STATE_SHIFT = 6;
        private static final int STATE_RESERVED_0 = 16777216;
        public static final int STATE_SCREEN_DOZE_FLAG = 262144;
        public static final int STATE_SCREEN_ON_FLAG = 1048576;
        public static final int STATE_SENSOR_ON_FLAG = 8388608;
        public static final int STATE_WAKE_LOCK_FLAG = 1073741824;
        public static final int STATE_WIFI_FULL_LOCK_FLAG = 268435456;
        public static final int STATE_WIFI_MULTICAST_ON_FLAG = 65536;
        public static final int STATE_WIFI_RADIO_ACTIVE_FLAG = 67108864;
        public static final int STATE_WIFI_SCAN_FLAG = 134217728;
        public int batteryChargeUah;
        public byte batteryHealth;
        public byte batteryLevel;
        public byte batteryPlugType;
        public byte batteryStatus;
        public short batteryTemperature;
        public short batteryVoltage;
        public long currentTime;
        public int eventCode;
        public android.os.BatteryStats.HistoryTag eventTag;
        public double modemRailChargeMah;
        public android.os.BatteryStats.HistoryItem next;
        public int numReadInts;
        public com.android.internal.os.PowerStats powerStats;
        public android.os.BatteryStats.ProcessStateChange processStateChange;
        public int states;
        public int states2;
        public android.os.BatteryStats.HistoryStepDetails stepDetails;
        public boolean tagsFirstOccurrence;
        public long time;
        public android.os.BatteryStats.HistoryTag wakeReasonTag;
        public android.os.BatteryStats.HistoryTag wakelockTag;
        public double wifiRailChargeMah;
        public byte cmd = -1;
        public final android.os.BatteryStats.HistoryTag localWakelockTag = new android.os.BatteryStats.HistoryTag();
        public final android.os.BatteryStats.HistoryTag localWakeReasonTag = new android.os.BatteryStats.HistoryTag();
        public final android.os.BatteryStats.HistoryTag localEventTag = new android.os.BatteryStats.HistoryTag();
        public final android.os.BatteryStats.ProcessStateChange localProcessStateChange = new android.os.BatteryStats.ProcessStateChange();

        public boolean isDeltaData() {
            return this.cmd == 0;
        }

        public HistoryItem() {
        }

        public HistoryItem(android.os.Parcel parcel) {
            readFromParcel(parcel);
        }

        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeLong(this.time);
            parcel.writeInt((this.cmd & 255) | ((this.batteryLevel << 8) & 65280) | ((this.batteryStatus << 16) & android.view.SurfaceControl.FX_SURFACE_MASK) | ((this.batteryHealth << 20) & 15728640) | ((this.batteryPlugType << android.hardware.biometrics.face.AcquiredInfo.FIRST_FRAME_RECEIVED) & 251658240) | (this.wakelockTag != null ? 268435456 : 0) | (this.wakeReasonTag != null ? 536870912 : 0) | (this.eventCode != 0 ? 1073741824 : 0));
            parcel.writeInt((this.batteryTemperature & 65535) | ((this.batteryVoltage << 16) & (-65536)));
            parcel.writeInt(this.batteryChargeUah);
            parcel.writeDouble(this.modemRailChargeMah);
            parcel.writeDouble(this.wifiRailChargeMah);
            parcel.writeInt(this.states);
            parcel.writeInt(this.states2);
            if (this.wakelockTag != null) {
                this.wakelockTag.writeToParcel(parcel, i);
            }
            if (this.wakeReasonTag != null) {
                this.wakeReasonTag.writeToParcel(parcel, i);
            }
            if (this.eventCode != 0) {
                parcel.writeInt(this.eventCode);
                this.eventTag.writeToParcel(parcel, i);
            }
            if (this.cmd == 5 || this.cmd == 7) {
                parcel.writeLong(this.currentTime);
            }
        }

        public void readFromParcel(android.os.Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            this.time = parcel.readLong();
            int readInt = parcel.readInt();
            this.cmd = (byte) (readInt & 255);
            this.batteryLevel = (byte) ((readInt >> 8) & 255);
            this.batteryStatus = (byte) ((readInt >> 16) & 15);
            this.batteryHealth = (byte) ((readInt >> 20) & 15);
            this.batteryPlugType = (byte) ((readInt >> 24) & 15);
            int readInt2 = parcel.readInt();
            this.batteryTemperature = (short) (readInt2 & 65535);
            this.batteryVoltage = (short) ((readInt2 >> 16) & 65535);
            this.batteryChargeUah = parcel.readInt();
            this.modemRailChargeMah = parcel.readDouble();
            this.wifiRailChargeMah = parcel.readDouble();
            this.states = parcel.readInt();
            this.states2 = parcel.readInt();
            if ((268435456 & readInt) != 0) {
                this.wakelockTag = this.localWakelockTag;
                this.wakelockTag.readFromParcel(parcel);
            } else {
                this.wakelockTag = null;
            }
            if ((536870912 & readInt) != 0) {
                this.wakeReasonTag = this.localWakeReasonTag;
                this.wakeReasonTag.readFromParcel(parcel);
            } else {
                this.wakeReasonTag = null;
            }
            if ((readInt & 1073741824) != 0) {
                this.eventCode = parcel.readInt();
                this.eventTag = this.localEventTag;
                this.eventTag.readFromParcel(parcel);
            } else {
                this.eventCode = 0;
                this.eventTag = null;
            }
            if (this.cmd == 5 || this.cmd == 7) {
                this.currentTime = parcel.readLong();
            } else {
                this.currentTime = 0L;
            }
            this.numReadInts += (parcel.dataPosition() - dataPosition) / 4;
        }

        public void clear() {
            this.time = 0L;
            this.cmd = (byte) -1;
            this.batteryLevel = (byte) 0;
            this.batteryStatus = (byte) 0;
            this.batteryHealth = (byte) 0;
            this.batteryPlugType = (byte) 0;
            this.batteryTemperature = (short) 0;
            this.batteryVoltage = (short) 0;
            this.batteryChargeUah = 0;
            this.modemRailChargeMah = 0.0d;
            this.wifiRailChargeMah = 0.0d;
            this.states = 0;
            this.states2 = 0;
            this.wakelockTag = null;
            this.wakeReasonTag = null;
            this.eventCode = 0;
            this.eventTag = null;
            this.tagsFirstOccurrence = false;
            this.powerStats = null;
            this.processStateChange = null;
        }

        public void setTo(android.os.BatteryStats.HistoryItem historyItem) {
            this.time = historyItem.time;
            this.cmd = historyItem.cmd;
            setToCommon(historyItem);
        }

        public void setTo(long j, byte b, android.os.BatteryStats.HistoryItem historyItem) {
            this.time = j;
            this.cmd = b;
            setToCommon(historyItem);
        }

        private void setToCommon(android.os.BatteryStats.HistoryItem historyItem) {
            this.batteryLevel = historyItem.batteryLevel;
            this.batteryStatus = historyItem.batteryStatus;
            this.batteryHealth = historyItem.batteryHealth;
            this.batteryPlugType = historyItem.batteryPlugType;
            this.batteryTemperature = historyItem.batteryTemperature;
            this.batteryVoltage = historyItem.batteryVoltage;
            this.batteryChargeUah = historyItem.batteryChargeUah;
            this.modemRailChargeMah = historyItem.modemRailChargeMah;
            this.wifiRailChargeMah = historyItem.wifiRailChargeMah;
            this.states = historyItem.states;
            this.states2 = historyItem.states2;
            if (historyItem.wakelockTag != null) {
                this.wakelockTag = this.localWakelockTag;
                this.wakelockTag.setTo(historyItem.wakelockTag);
            } else {
                this.wakelockTag = null;
            }
            if (historyItem.wakeReasonTag != null) {
                this.wakeReasonTag = this.localWakeReasonTag;
                this.wakeReasonTag.setTo(historyItem.wakeReasonTag);
            } else {
                this.wakeReasonTag = null;
            }
            this.eventCode = historyItem.eventCode;
            if (historyItem.eventTag != null) {
                this.eventTag = this.localEventTag;
                this.eventTag.setTo(historyItem.eventTag);
            } else {
                this.eventTag = null;
            }
            this.tagsFirstOccurrence = historyItem.tagsFirstOccurrence;
            this.currentTime = historyItem.currentTime;
            this.powerStats = historyItem.powerStats;
            this.processStateChange = historyItem.processStateChange;
        }

        public boolean sameNonEvent(android.os.BatteryStats.HistoryItem historyItem) {
            return this.batteryLevel == historyItem.batteryLevel && this.batteryStatus == historyItem.batteryStatus && this.batteryHealth == historyItem.batteryHealth && this.batteryPlugType == historyItem.batteryPlugType && this.batteryTemperature == historyItem.batteryTemperature && this.batteryVoltage == historyItem.batteryVoltage && this.batteryChargeUah == historyItem.batteryChargeUah && this.modemRailChargeMah == historyItem.modemRailChargeMah && this.wifiRailChargeMah == historyItem.wifiRailChargeMah && this.states == historyItem.states && this.states2 == historyItem.states2 && this.currentTime == historyItem.currentTime;
        }

        public boolean same(android.os.BatteryStats.HistoryItem historyItem) {
            if (!sameNonEvent(historyItem) || this.eventCode != historyItem.eventCode) {
                return false;
            }
            if (this.wakelockTag != historyItem.wakelockTag && (this.wakelockTag == null || historyItem.wakelockTag == null || !this.wakelockTag.equals(historyItem.wakelockTag))) {
                return false;
            }
            if (this.wakeReasonTag != historyItem.wakeReasonTag && (this.wakeReasonTag == null || historyItem.wakeReasonTag == null || !this.wakeReasonTag.equals(historyItem.wakeReasonTag))) {
                return false;
            }
            if (this.eventTag != historyItem.eventTag) {
                return (this.eventTag == null || historyItem.eventTag == null || !this.eventTag.equals(historyItem.eventTag)) ? false : true;
            }
            return true;
        }
    }

    public static final class HistoryEventTracker {
        private final java.util.HashMap<java.lang.String, android.util.SparseIntArray>[] mActiveEvents = new java.util.HashMap[22];

        public boolean updateState(int i, java.lang.String str, int i2, int i3) {
            android.util.SparseIntArray sparseIntArray;
            int indexOfKey;
            if ((32768 & i) != 0) {
                int i4 = i & android.os.BatteryStats.HistoryItem.EVENT_TYPE_MASK;
                java.util.HashMap<java.lang.String, android.util.SparseIntArray> hashMap = this.mActiveEvents[i4];
                if (hashMap == null) {
                    hashMap = new java.util.HashMap<>();
                    this.mActiveEvents[i4] = hashMap;
                }
                android.util.SparseIntArray sparseIntArray2 = hashMap.get(str);
                if (sparseIntArray2 == null) {
                    sparseIntArray2 = new android.util.SparseIntArray();
                    hashMap.put(str, sparseIntArray2);
                }
                if (sparseIntArray2.indexOfKey(i2) >= 0) {
                    return false;
                }
                sparseIntArray2.put(i2, i3);
                return true;
            }
            if ((i & 16384) != 0) {
                java.util.HashMap<java.lang.String, android.util.SparseIntArray> hashMap2 = this.mActiveEvents[i & android.os.BatteryStats.HistoryItem.EVENT_TYPE_MASK];
                if (hashMap2 == null || (sparseIntArray = hashMap2.get(str)) == null || (indexOfKey = sparseIntArray.indexOfKey(i2)) < 0) {
                    return false;
                }
                sparseIntArray.removeAt(indexOfKey);
                if (sparseIntArray.size() <= 0) {
                    hashMap2.remove(str);
                    return true;
                }
                return true;
            }
            return true;
        }

        public void removeEvents(int i) {
            this.mActiveEvents[i & android.os.BatteryStats.HistoryItem.EVENT_TYPE_MASK] = null;
        }

        public java.util.HashMap<java.lang.String, android.util.SparseIntArray> getStateForEvent(int i) {
            return this.mActiveEvents[i];
        }
    }

    public static final class BitDescription {
        public final int mask;
        public final java.lang.String name;
        public final int shift;
        public final java.lang.String shortName;
        public final java.lang.String[] shortValues;
        public final java.lang.String[] values;

        public BitDescription(int i, java.lang.String str, java.lang.String str2) {
            this.mask = i;
            this.shift = -1;
            this.name = str;
            this.shortName = str2;
            this.values = null;
            this.shortValues = null;
        }

        public BitDescription(int i, int i2, java.lang.String str, java.lang.String str2, java.lang.String[] strArr, java.lang.String[] strArr2) {
            this.mask = i;
            this.shift = i2;
            this.name = str;
            this.shortName = str2;
            this.values = strArr;
            this.shortValues = strArr2;
        }
    }

    public static int getAllNetworkTypesCount() {
        int length = android.telephony.TelephonyManager.getAllNetworkTypes().length;
        int i = length + 3;
        if (DATA_CONNECTION_NAMES.length != i) {
            throw new java.lang.IllegalStateException("DATA_CONNECTION_NAMES length does not match network type count. Expected: " + i + ", actual:" + DATA_CONNECTION_NAMES.length);
        }
        return length;
    }

    public static int getAllNetworkTypesCount$ravenwood() {
        return DATA_CONNECTION_NAMES.length - 3;
    }

    private static final void formatTimeRaw(java.lang.StringBuilder sb, long j) {
        long j2 = j / 86400;
        if (j2 != 0) {
            sb.append(j2);
            sb.append("d ");
        }
        long j3 = j2 * 60 * 60 * 24;
        long j4 = (j - j3) / 3600;
        if (j4 != 0 || j3 != 0) {
            sb.append(j4);
            sb.append("h ");
        }
        long j5 = j3 + (j4 * 60 * 60);
        long j6 = (j - j5) / 60;
        if (j6 != 0 || j5 != 0) {
            sb.append(j6);
            sb.append("m ");
        }
        long j7 = j5 + (j6 * 60);
        if (j != 0 || j7 != 0) {
            sb.append(j - j7);
            sb.append("s ");
        }
    }

    public static final void formatTimeMs(java.lang.StringBuilder sb, long j) {
        long j2 = j / 1000;
        formatTimeRaw(sb, j2);
        sb.append(j - (j2 * 1000));
        sb.append("ms ");
    }

    public static final void formatTimeMsNoSpace(java.lang.StringBuilder sb, long j) {
        long j2 = j / 1000;
        formatTimeRaw(sb, j2);
        sb.append(j - (j2 * 1000));
        sb.append("ms");
    }

    public final java.lang.String formatRatioLocked(long j, long j2) {
        if (j2 == 0) {
            return "--%";
        }
        this.mFormatBuilder.setLength(0);
        this.mFormatter.format("%.1f%%", java.lang.Float.valueOf((j / j2) * 100.0f));
        return this.mFormatBuilder.toString();
    }

    final java.lang.String formatBytesLocked(long j) {
        this.mFormatBuilder.setLength(0);
        if (j < 1024) {
            return j + android.hardware.gnss.GnssSignalType.CODE_TYPE_B;
        }
        if (j < 1048576) {
            this.mFormatter.format("%.2fKB", java.lang.Double.valueOf(j / 1024.0d));
            return this.mFormatBuilder.toString();
        }
        if (j < 1073741824) {
            this.mFormatter.format("%.2fMB", java.lang.Double.valueOf(j / 1048576.0d));
            return this.mFormatBuilder.toString();
        }
        this.mFormatter.format("%.2fGB", java.lang.Double.valueOf(j / 1.073741824E9d));
        return this.mFormatBuilder.toString();
    }

    public static java.lang.String formatCharge(double d) {
        return formatValue(d);
    }

    private static java.lang.String formatValue(double d) {
        java.lang.String str;
        if (d == 0.0d) {
            return android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS;
        }
        if (d < 1.0E-5d) {
            str = "%.8f";
        } else if (d < 1.0E-4d) {
            str = "%.7f";
        } else if (d < 0.001d) {
            str = "%.6f";
        } else if (d < 0.01d) {
            str = "%.5f";
        } else if (d < 0.1d) {
            str = "%.4f";
        } else if (d < 1.0d) {
            str = "%.3f";
        } else if (d < 10.0d) {
            str = "%.2f";
        } else if (d < 100.0d) {
            str = "%.1f";
        } else {
            str = "%.0f";
        }
        return java.lang.String.format(java.util.Locale.ENGLISH, str, java.lang.Double.valueOf(d));
    }

    private static long roundUsToMs(long j) {
        return (j + 500) / 1000;
    }

    private static long computeWakeLock(android.os.BatteryStats.Timer timer, long j, int i) {
        if (timer != null) {
            return (timer.getTotalTimeLocked(j, i) + 500) / 1000;
        }
        return 0L;
    }

    private static final java.lang.String printWakeLock(java.lang.StringBuilder sb, android.os.BatteryStats.Timer timer, long j, java.lang.String str, int i, java.lang.String str2) {
        if (timer != null) {
            long computeWakeLock = computeWakeLock(timer, j, i);
            int countLocked = timer.getCountLocked(i);
            if (computeWakeLock != 0) {
                sb.append(str2);
                formatTimeMs(sb, computeWakeLock);
                if (str != null) {
                    sb.append(str);
                    sb.append(' ');
                }
                sb.append('(');
                sb.append(countLocked);
                sb.append(" times)");
                long j2 = j / 1000;
                long maxDurationMsLocked = timer.getMaxDurationMsLocked(j2);
                if (maxDurationMsLocked >= 0) {
                    sb.append(" max=");
                    sb.append(maxDurationMsLocked);
                }
                long totalDurationMsLocked = timer.getTotalDurationMsLocked(j2);
                if (totalDurationMsLocked > computeWakeLock) {
                    sb.append(" actual=");
                    sb.append(totalDurationMsLocked);
                }
                if (timer.isRunningLocked()) {
                    long currentDurationMsLocked = timer.getCurrentDurationMsLocked(j2);
                    if (currentDurationMsLocked >= 0) {
                        sb.append(" (running for ");
                        sb.append(currentDurationMsLocked);
                        sb.append("ms)");
                        return ", ";
                    }
                    sb.append(" (running)");
                    return ", ";
                }
                return ", ";
            }
        }
        return str2;
    }

    private static final boolean printTimer(java.io.PrintWriter printWriter, java.lang.StringBuilder sb, android.os.BatteryStats.Timer timer, long j, int i, java.lang.String str, java.lang.String str2) {
        if (timer != null) {
            long totalTimeLocked = (timer.getTotalTimeLocked(j, i) + 500) / 1000;
            int countLocked = timer.getCountLocked(i);
            if (totalTimeLocked != 0) {
                sb.setLength(0);
                sb.append(str);
                sb.append("    ");
                sb.append(str2);
                sb.append(": ");
                formatTimeMs(sb, totalTimeLocked);
                sb.append("realtime (");
                sb.append(countLocked);
                sb.append(" times)");
                long j2 = j / 1000;
                long maxDurationMsLocked = timer.getMaxDurationMsLocked(j2);
                if (maxDurationMsLocked >= 0) {
                    sb.append(" max=");
                    sb.append(maxDurationMsLocked);
                }
                if (timer.isRunningLocked()) {
                    long currentDurationMsLocked = timer.getCurrentDurationMsLocked(j2);
                    if (currentDurationMsLocked >= 0) {
                        sb.append(" (running for ");
                        sb.append(currentDurationMsLocked);
                        sb.append("ms)");
                    } else {
                        sb.append(" (running)");
                    }
                }
                printWriter.println(sb.toString());
                return true;
            }
        }
        return false;
    }

    private static final java.lang.String printWakeLockCheckin(java.lang.StringBuilder sb, android.os.BatteryStats.Timer timer, long j, java.lang.String str, int i, java.lang.String str2) {
        long j2;
        int i2;
        long j3;
        long j4;
        long j5;
        if (timer == null) {
            j2 = 0;
            i2 = 0;
            j3 = 0;
            j4 = 0;
            j5 = 0;
        } else {
            j2 = timer.getTotalTimeLocked(j, i);
            i2 = timer.getCountLocked(i);
            long j6 = j / 1000;
            j4 = timer.getCurrentDurationMsLocked(j6);
            j5 = timer.getMaxDurationMsLocked(j6);
            j3 = timer.getTotalDurationMsLocked(j6);
        }
        sb.append(str2);
        sb.append((j2 + 500) / 1000);
        sb.append(',');
        sb.append(str != null ? str + "," : "");
        sb.append(i2);
        sb.append(',');
        sb.append(j4);
        sb.append(',');
        sb.append(j5);
        if (str != null) {
            sb.append(',');
            sb.append(j3);
        }
        return ",";
    }

    private static final void dumpLineHeader(java.io.PrintWriter printWriter, int i, java.lang.String str, java.lang.String str2) {
        printWriter.print(9);
        printWriter.print(',');
        printWriter.print(i);
        printWriter.print(',');
        printWriter.print(str);
        printWriter.print(',');
        printWriter.print(str2);
    }

    private static final void dumpLine(java.io.PrintWriter printWriter, int i, java.lang.String str, java.lang.String str2, java.lang.Object... objArr) {
        dumpLineHeader(printWriter, i, str, str2);
        for (java.lang.Object obj : objArr) {
            printWriter.print(',');
            printWriter.print(obj);
        }
        printWriter.println();
    }

    private static final void dumpTimer(java.io.PrintWriter printWriter, int i, java.lang.String str, java.lang.String str2, android.os.BatteryStats.Timer timer, long j, int i2) {
        if (timer != null) {
            long roundUsToMs = roundUsToMs(timer.getTotalTimeLocked(j, i2));
            int countLocked = timer.getCountLocked(i2);
            if (roundUsToMs != 0 || countLocked != 0) {
                dumpLine(printWriter, i, str, str2, java.lang.Long.valueOf(roundUsToMs), java.lang.Integer.valueOf(countLocked));
            }
        }
    }

    private static void dumpTimer(android.util.proto.ProtoOutputStream protoOutputStream, long j, android.os.BatteryStats.Timer timer, long j2, int i) {
        if (timer == null) {
            return;
        }
        long roundUsToMs = roundUsToMs(timer.getTotalTimeLocked(j2, i));
        int countLocked = timer.getCountLocked(i);
        long j3 = j2 / 1000;
        long maxDurationMsLocked = timer.getMaxDurationMsLocked(j3);
        long currentDurationMsLocked = timer.getCurrentDurationMsLocked(j3);
        long totalDurationMsLocked = timer.getTotalDurationMsLocked(j3);
        if (roundUsToMs != 0 || countLocked != 0 || maxDurationMsLocked != -1 || currentDurationMsLocked != -1 || totalDurationMsLocked != -1) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1112396529665L, roundUsToMs);
            protoOutputStream.write(1112396529666L, countLocked);
            if (maxDurationMsLocked != -1) {
                protoOutputStream.write(1112396529667L, maxDurationMsLocked);
            }
            if (currentDurationMsLocked != -1) {
                protoOutputStream.write(1112396529668L, currentDurationMsLocked);
            }
            if (totalDurationMsLocked != -1) {
                protoOutputStream.write(1112396529669L, totalDurationMsLocked);
            }
            protoOutputStream.end(start);
        }
    }

    private static boolean controllerActivityHasData(android.os.BatteryStats.ControllerActivityCounter controllerActivityCounter, int i) {
        if (controllerActivityCounter == null) {
            return false;
        }
        if (controllerActivityCounter.getIdleTimeCounter().getCountLocked(i) != 0 || controllerActivityCounter.getRxTimeCounter().getCountLocked(i) != 0 || controllerActivityCounter.getPowerCounter().getCountLocked(i) != 0 || controllerActivityCounter.getMonitoredRailChargeConsumedMaMs().getCountLocked(i) != 0) {
            return true;
        }
        for (android.os.BatteryStats.LongCounter longCounter : controllerActivityCounter.getTxTimeCounters()) {
            if (longCounter.getCountLocked(i) != 0) {
                return true;
            }
        }
        return false;
    }

    private static final void dumpControllerActivityLine(java.io.PrintWriter printWriter, int i, java.lang.String str, java.lang.String str2, android.os.BatteryStats.ControllerActivityCounter controllerActivityCounter, int i2) {
        if (!controllerActivityHasData(controllerActivityCounter, i2)) {
            return;
        }
        dumpLineHeader(printWriter, i, str, str2);
        printWriter.print(",");
        printWriter.print(controllerActivityCounter.getIdleTimeCounter().getCountLocked(i2));
        printWriter.print(",");
        printWriter.print(controllerActivityCounter.getRxTimeCounter().getCountLocked(i2));
        printWriter.print(",");
        printWriter.print(controllerActivityCounter.getPowerCounter().getCountLocked(i2) / 3600000.0d);
        printWriter.print(",");
        printWriter.print(controllerActivityCounter.getMonitoredRailChargeConsumedMaMs().getCountLocked(i2) / 3600000.0d);
        android.os.BatteryStats.LongCounter[] txTimeCounters = controllerActivityCounter.getTxTimeCounters();
        for (android.os.BatteryStats.LongCounter longCounter : txTimeCounters) {
            printWriter.print(",");
            printWriter.print(longCounter.getCountLocked(i2));
        }
        printWriter.println();
    }

    private static void dumpControllerActivityProto(android.util.proto.ProtoOutputStream protoOutputStream, long j, android.os.BatteryStats.ControllerActivityCounter controllerActivityCounter, int i) {
        if (!controllerActivityHasData(controllerActivityCounter, i)) {
            return;
        }
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1112396529665L, controllerActivityCounter.getIdleTimeCounter().getCountLocked(i));
        protoOutputStream.write(1112396529666L, controllerActivityCounter.getRxTimeCounter().getCountLocked(i));
        protoOutputStream.write(1112396529667L, controllerActivityCounter.getPowerCounter().getCountLocked(i) / 3600000.0d);
        protoOutputStream.write(1103806595077L, controllerActivityCounter.getMonitoredRailChargeConsumedMaMs().getCountLocked(i) / 3600000.0d);
        android.os.BatteryStats.LongCounter[] txTimeCounters = controllerActivityCounter.getTxTimeCounters();
        for (int i2 = 0; i2 < txTimeCounters.length; i2++) {
            android.os.BatteryStats.LongCounter longCounter = txTimeCounters[i2];
            long start2 = protoOutputStream.start(2246267895812L);
            protoOutputStream.write(1120986464257L, i2);
            protoOutputStream.write(1112396529666L, longCounter.getCountLocked(i));
            protoOutputStream.end(start2);
        }
        protoOutputStream.end(start);
    }

    private final void printControllerActivityIfInteresting(java.io.PrintWriter printWriter, java.lang.StringBuilder sb, java.lang.String str, java.lang.String str2, android.os.BatteryStats.ControllerActivityCounter controllerActivityCounter, int i) {
        if (controllerActivityHasData(controllerActivityCounter, i)) {
            printControllerActivity(printWriter, sb, str, str2, controllerActivityCounter, i);
        }
    }

    private final void printControllerActivity(java.io.PrintWriter printWriter, java.lang.StringBuilder sb, java.lang.String str, java.lang.String str2, android.os.BatteryStats.ControllerActivityCounter controllerActivityCounter, int i) {
        java.lang.String str3;
        long j;
        java.lang.Object obj;
        int i2;
        char c;
        java.lang.String[] strArr;
        long countLocked = controllerActivityCounter.getIdleTimeCounter().getCountLocked(i);
        long countLocked2 = controllerActivityCounter.getRxTimeCounter().getCountLocked(i);
        long countLocked3 = controllerActivityCounter.getPowerCounter().getCountLocked(i);
        long countLocked4 = controllerActivityCounter.getMonitoredRailChargeConsumedMaMs().getCountLocked(i);
        long computeBatteryRealtime = computeBatteryRealtime(android.os.SystemClock.elapsedRealtime() * 1000, i) / 1000;
        android.os.BatteryStats.LongCounter[] txTimeCounters = controllerActivityCounter.getTxTimeCounters();
        long j2 = 0;
        int i3 = 0;
        for (int length = txTimeCounters.length; i3 < length; length = length) {
            j2 += txTimeCounters[i3].getCountLocked(i);
            i3++;
        }
        if (!str2.equals(WIFI_CONTROLLER_NAME)) {
            str3 = " Sleep time:  ";
        } else {
            long countLocked5 = controllerActivityCounter.getScanTimeCounter().getCountLocked(i);
            sb.setLength(0);
            sb.append(str);
            sb.append("     ");
            sb.append(str2);
            sb.append(" Scan time:  ");
            formatTimeMs(sb, countLocked5);
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
            sb.append(formatRatioLocked(countLocked5, computeBatteryRealtime));
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            printWriter.println(sb.toString());
            long j3 = computeBatteryRealtime - ((countLocked + countLocked2) + j2);
            sb.setLength(0);
            sb.append(str);
            sb.append("     ");
            sb.append(str2);
            str3 = " Sleep time:  ";
            sb.append(str3);
            formatTimeMs(sb, j3);
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
            sb.append(formatRatioLocked(j3, computeBatteryRealtime));
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            printWriter.println(sb.toString());
        }
        if (!str2.equals(CELLULAR_CONTROLLER_NAME)) {
            j = countLocked2;
            obj = CELLULAR_CONTROLLER_NAME;
            i2 = i;
        } else {
            android.os.BatteryStats.LongCounter sleepTimeCounter = controllerActivityCounter.getSleepTimeCounter();
            j = countLocked2;
            obj = CELLULAR_CONTROLLER_NAME;
            i2 = i;
            long countLocked6 = sleepTimeCounter.getCountLocked(i2);
            sb.setLength(0);
            sb.append(str);
            sb.append("     ");
            sb.append(str2);
            sb.append(str3);
            formatTimeMs(sb, countLocked6);
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
            sb.append(formatRatioLocked(countLocked6, computeBatteryRealtime));
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            printWriter.println(sb.toString());
        }
        sb.setLength(0);
        sb.append(str);
        sb.append("     ");
        sb.append(str2);
        sb.append(" Idle time:   ");
        formatTimeMs(sb, countLocked);
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
        sb.append(formatRatioLocked(countLocked, computeBatteryRealtime));
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        printWriter.println(sb.toString());
        sb.setLength(0);
        sb.append(str);
        sb.append("     ");
        sb.append(str2);
        sb.append(" Rx time:     ");
        long j4 = j;
        formatTimeMs(sb, j4);
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
        sb.append(formatRatioLocked(j4, computeBatteryRealtime));
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        printWriter.println(sb.toString());
        sb.setLength(0);
        sb.append(str);
        sb.append("     ");
        sb.append(str2);
        sb.append(" Tx time:     ");
        switch (str2.hashCode()) {
            case -851952246:
                if (str2.equals(obj)) {
                    c = 0;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                strArr = new java.lang.String[]{"   less than 0dBm: ", "   0dBm to 8dBm: ", "   8dBm to 15dBm: ", "   15dBm to 20dBm: ", "   above 20dBm: "};
                break;
            default:
                strArr = new java.lang.String[]{"[0]", "[1]", "[2]", "[3]", "[4]"};
                break;
        }
        int min = java.lang.Math.min(controllerActivityCounter.getTxTimeCounters().length, strArr.length);
        if (min > 1) {
            printWriter.println(sb.toString());
            for (int i4 = 0; i4 < min; i4++) {
                long countLocked7 = controllerActivityCounter.getTxTimeCounters()[i4].getCountLocked(i2);
                sb.setLength(0);
                sb.append(str);
                sb.append("    ");
                sb.append(strArr[i4]);
                sb.append(" ");
                formatTimeMs(sb, countLocked7);
                sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
                sb.append(formatRatioLocked(countLocked7, computeBatteryRealtime));
                sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                printWriter.println(sb.toString());
            }
        } else {
            long countLocked8 = controllerActivityCounter.getTxTimeCounters()[0].getCountLocked(i2);
            formatTimeMs(sb, countLocked8);
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
            sb.append(formatRatioLocked(countLocked8, computeBatteryRealtime));
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            printWriter.println(sb.toString());
        }
        if (countLocked3 > 0) {
            sb.setLength(0);
            sb.append(str);
            sb.append("     ");
            sb.append(str2);
            sb.append(" Battery drain: ").append(formatCharge(countLocked3 / 3600000.0d));
            sb.append("mAh");
            printWriter.println(sb.toString());
        }
        if (countLocked4 > 0) {
            sb.setLength(0);
            sb.append(str);
            sb.append("     ");
            sb.append(str2);
            sb.append(" Monitored rail energy drain: ").append(new java.text.DecimalFormat("#.##").format(countLocked4 / 3600000.0d));
            sb.append(" mAh");
            printWriter.println(sb.toString());
        }
    }

    private void printCellularPerRatBreakdown(java.io.PrintWriter printWriter, java.lang.StringBuilder sb, java.lang.String str, long j) {
        java.lang.String[] strArr = {"    Unknown frequency:\n", "    Low frequency (less than 1GHz):\n", "    Middle frequency (1GHz to 3GHz):\n", "    High frequency (3GHz to 6GHz):\n", "    Mmwave frequency (greater than 6GHz):\n"};
        java.lang.String[] strArr2 = {"        unknown:  ", "        poor:     ", "        moderate: ", "        good:     ", "        great:    "};
        int i = 0;
        long mobileRadioActiveTime = getMobileRadioActiveTime(j * 1000, 0) / 1000;
        sb.setLength(0);
        sb.append(str);
        sb.append("Active Cellular Radio Access Technology Breakdown:");
        printWriter.println(sb);
        int numSignalStrengthLevels = android.telephony.CellSignalStrength.getNumSignalStrengthLevels();
        int i2 = 2;
        int i3 = 2;
        boolean z = false;
        while (i3 >= 0) {
            sb.setLength(i);
            sb.append(str);
            sb.append("  ");
            sb.append(RADIO_ACCESS_TECHNOLOGY_NAMES[i3]);
            sb.append(":\n");
            sb.append(str);
            boolean z2 = z;
            int i4 = (i3 == i2 ? 5 : 1) - 1;
            while (i4 >= 0) {
                int length = sb.length();
                if (i3 == i2) {
                    sb.append(strArr[i4]);
                } else {
                    sb.append("    All frequencies:\n");
                }
                sb.append(str);
                sb.append("      Signal Strength Time:\n");
                int i5 = i;
                int i6 = i5;
                while (i5 < numSignalStrengthLevels) {
                    java.lang.String[] strArr3 = strArr;
                    int i7 = i5;
                    int i8 = length;
                    int i9 = i4;
                    int i10 = i2;
                    int i11 = i3;
                    long activeRadioDurationMs = getActiveRadioDurationMs(i3, i4, i7, j);
                    if (activeRadioDurationMs > 0) {
                        sb.append(str);
                        sb.append(strArr2[i7]);
                        formatTimeMs(sb, activeRadioDurationMs);
                        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
                        sb.append(formatRatioLocked(activeRadioDurationMs, mobileRadioActiveTime));
                        sb.append(")\n");
                        i6 = 1;
                    }
                    i5 = i7 + 1;
                    strArr = strArr3;
                    length = i8;
                    i4 = i9;
                    i3 = i11;
                    i2 = i10;
                }
                int i12 = length;
                int i13 = i4;
                int i14 = i2;
                int i15 = i3;
                java.lang.String[] strArr4 = strArr;
                sb.append(str);
                sb.append("      Tx Time:\n");
                int i16 = 0;
                while (i16 < numSignalStrengthLevels) {
                    int i17 = i16;
                    long activeTxRadioDurationMs = getActiveTxRadioDurationMs(i15, i13, i16, j);
                    if (activeTxRadioDurationMs > 0) {
                        sb.append(str);
                        sb.append(strArr2[i17]);
                        formatTimeMs(sb, activeTxRadioDurationMs);
                        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
                        sb.append(formatRatioLocked(activeTxRadioDurationMs, mobileRadioActiveTime));
                        sb.append(")\n");
                        i6 = 1;
                    }
                    i16 = i17 + 1;
                }
                sb.append(str);
                sb.append("      Rx Time: ");
                long activeRxRadioDurationMs = getActiveRxRadioDurationMs(i15, i13, j);
                formatTimeMs(sb, activeRxRadioDurationMs);
                sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
                sb.append(formatRatioLocked(activeRxRadioDurationMs, mobileRadioActiveTime));
                sb.append(")\n");
                if (i6 != 0) {
                    printWriter.print(sb);
                    sb.setLength(0);
                    sb.append(str);
                    z2 = true;
                } else {
                    sb.setLength(i12);
                }
                i4 = i13 - 1;
                i3 = i15;
                strArr = strArr4;
                i2 = i14;
                i = 0;
            }
            i3--;
            z = z2;
            i2 = i2;
            i = 0;
        }
        if (!z) {
            sb.setLength(0);
            sb.append(str);
            sb.append("  (no activity)");
            printWriter.println(sb);
        }
    }

    public final void dumpCheckinLocked(android.content.Context context, java.io.PrintWriter printWriter, int i, int i2, boolean z, android.os.BatteryStats.BatteryStatsDumpHelper batteryStatsDumpHelper) {
        java.lang.String str;
        java.lang.Integer num;
        java.lang.String str2;
        java.lang.StringBuilder sb;
        int i3;
        android.util.SparseArray<? extends android.os.BatteryStats.Uid> sparseArray;
        long j;
        long j2;
        char c;
        java.lang.String str3;
        java.lang.String str4;
        com.android.internal.os.CpuScalingPolicies cpuScalingPolicies;
        long j3;
        long j4;
        int i4;
        long j5;
        long j6;
        java.lang.StringBuilder sb2;
        java.lang.StringBuilder sb3;
        java.lang.String str5;
        long j7;
        java.lang.Integer num2;
        java.lang.StringBuilder sb4;
        java.lang.String str6;
        android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Proc> arrayMap;
        long j8;
        long j9;
        int i5;
        long j10;
        long j11;
        android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Timer> arrayMap2;
        java.lang.String str7;
        long j12;
        long j13;
        java.lang.String str8;
        android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Timer> arrayMap3;
        char c2;
        long j14;
        long j15;
        long j16;
        long j17;
        long j18;
        long j19;
        int[] iArr;
        android.os.BatteryStats batteryStats = this;
        java.lang.Integer num3 = 0;
        if (i != 0) {
            dumpLine(printWriter, 0, STAT_NAMES[i], android.app.Notification.CATEGORY_ERROR, "ERROR: BatteryStats.dumpCheckin called for which type " + i + " but only STATS_SINCE_CHARGED is supported.");
            return;
        }
        long uptimeMillis = android.os.SystemClock.uptimeMillis() * 1000;
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        long j20 = elapsedRealtime * 1000;
        long batteryUptime = batteryStats.getBatteryUptime(uptimeMillis);
        long computeBatteryUptime = batteryStats.computeBatteryUptime(uptimeMillis, i);
        long computeBatteryRealtime = batteryStats.computeBatteryRealtime(j20, i);
        long computeBatteryScreenOffUptime = batteryStats.computeBatteryScreenOffUptime(uptimeMillis, i);
        long computeBatteryScreenOffRealtime = batteryStats.computeBatteryScreenOffRealtime(j20, i);
        long computeRealtime = batteryStats.computeRealtime(j20, i);
        long computeUptime = batteryStats.computeUptime(uptimeMillis, i);
        long screenOnTime = batteryStats.getScreenOnTime(j20, i);
        long screenDozeTime = batteryStats.getScreenDozeTime(j20, i);
        long interactiveTime = batteryStats.getInteractiveTime(j20, i);
        long powerSaveModeEnabledTime = batteryStats.getPowerSaveModeEnabledTime(j20, i);
        long deviceIdleModeTime = batteryStats.getDeviceIdleModeTime(1, j20, i);
        long deviceIdleModeTime2 = batteryStats.getDeviceIdleModeTime(2, j20, i);
        long deviceIdlingTime = batteryStats.getDeviceIdlingTime(1, j20, i);
        long deviceIdlingTime2 = batteryStats.getDeviceIdlingTime(2, j20, i);
        int numConnectivityChange = batteryStats.getNumConnectivityChange(i);
        long phoneOnTime = batteryStats.getPhoneOnTime(j20, i);
        long uahDischarge = batteryStats.getUahDischarge(i);
        long uahDischargeScreenOff = batteryStats.getUahDischargeScreenOff(i);
        long uahDischargeScreenDoze = batteryStats.getUahDischargeScreenDoze(i);
        long uahDischargeLightDoze = batteryStats.getUahDischargeLightDoze(i);
        long uahDischargeDeepDoze = batteryStats.getUahDischargeDeepDoze(i);
        java.lang.StringBuilder sb5 = new java.lang.StringBuilder(128);
        android.util.SparseArray<? extends android.os.BatteryStats.Uid> uidStats = getUidStats();
        int size = uidStats.size();
        long j21 = elapsedRealtime;
        java.lang.String str9 = STAT_NAMES[i];
        dumpLine(printWriter, 0, str9, BATTERY_DATA, i == 0 ? java.lang.Integer.valueOf(getStartCount()) : "N/A", java.lang.Long.valueOf(computeBatteryRealtime / 1000), java.lang.Long.valueOf(computeBatteryUptime / 1000), java.lang.Long.valueOf(computeRealtime / 1000), java.lang.Long.valueOf(computeUptime / 1000), java.lang.Long.valueOf(getStartClockTime()), java.lang.Long.valueOf(computeBatteryScreenOffRealtime / 1000), java.lang.Long.valueOf(computeBatteryScreenOffUptime / 1000), java.lang.Integer.valueOf(getEstimatedBatteryCapacity()), java.lang.Integer.valueOf(getMinLearnedBatteryCapacity()), java.lang.Integer.valueOf(getMaxLearnedBatteryCapacity()), java.lang.Long.valueOf(screenDozeTime / 1000));
        long j22 = 0;
        long j23 = 0;
        int i6 = 0;
        while (i6 < size) {
            android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Wakelock> wakelockStats = uidStats.valueAt(i6).getWakelockStats();
            java.lang.StringBuilder sb6 = sb5;
            int i7 = 1;
            int size2 = wakelockStats.size() - 1;
            while (size2 >= 0) {
                android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Wakelock> arrayMap4 = wakelockStats;
                android.os.BatteryStats.Uid.Wakelock valueAt = wakelockStats.valueAt(size2);
                long j24 = batteryUptime;
                android.os.BatteryStats.Timer wakeTime = valueAt.getWakeTime(i7);
                if (wakeTime != null) {
                    j22 += wakeTime.getTotalTimeLocked(j20, i);
                }
                android.os.BatteryStats.Timer wakeTime2 = valueAt.getWakeTime(0);
                if (wakeTime2 != null) {
                    j23 += wakeTime2.getTotalTimeLocked(j20, i);
                }
                size2--;
                wakelockStats = arrayMap4;
                batteryUptime = j24;
                i7 = 1;
            }
            i6++;
            sb5 = sb6;
        }
        dumpLine(printWriter, 0, str9, GLOBAL_NETWORK_DATA, java.lang.Long.valueOf(batteryStats.getNetworkActivityBytes(0, i)), java.lang.Long.valueOf(batteryStats.getNetworkActivityBytes(1, i)), java.lang.Long.valueOf(batteryStats.getNetworkActivityBytes(2, i)), java.lang.Long.valueOf(batteryStats.getNetworkActivityBytes(3, i)), java.lang.Long.valueOf(batteryStats.getNetworkActivityPackets(0, i)), java.lang.Long.valueOf(batteryStats.getNetworkActivityPackets(1, i)), java.lang.Long.valueOf(batteryStats.getNetworkActivityPackets(2, i)), java.lang.Long.valueOf(batteryStats.getNetworkActivityPackets(3, i)), java.lang.Long.valueOf(batteryStats.getNetworkActivityBytes(4, i)), java.lang.Long.valueOf(batteryStats.getNetworkActivityBytes(5, i)));
        long j25 = batteryUptime;
        java.lang.StringBuilder sb7 = sb5;
        long j26 = j20;
        dumpControllerActivityLine(printWriter, 0, str9, GLOBAL_MODEM_CONTROLLER_DATA, getModemControllerActivity(), i);
        java.lang.String str10 = str9;
        dumpLine(printWriter, 0, str10, GLOBAL_WIFI_DATA, java.lang.Long.valueOf(batteryStats.getWifiOnTime(j26, i) / 1000), java.lang.Long.valueOf(batteryStats.getGlobalWifiRunningTime(j26, i) / 1000), num3, num3, num3);
        dumpControllerActivityLine(printWriter, 0, str10, GLOBAL_WIFI_CONTROLLER_DATA, getWifiControllerActivity(), i);
        dumpControllerActivityLine(printWriter, 0, str10, GLOBAL_BLUETOOTH_CONTROLLER_DATA, getBluetoothControllerActivity(), i);
        dumpLine(printWriter, 0, str10, MISC_DATA, java.lang.Long.valueOf(screenOnTime / 1000), java.lang.Long.valueOf(phoneOnTime / 1000), java.lang.Long.valueOf(j22 / 1000), java.lang.Long.valueOf(j23 / 1000), java.lang.Long.valueOf(batteryStats.getMobileRadioActiveTime(j26, i) / 1000), java.lang.Long.valueOf(batteryStats.getMobileRadioActiveAdjustedTime(i) / 1000), java.lang.Long.valueOf(interactiveTime / 1000), java.lang.Long.valueOf(powerSaveModeEnabledTime / 1000), java.lang.Integer.valueOf(numConnectivityChange), java.lang.Long.valueOf(deviceIdleModeTime2 / 1000), java.lang.Integer.valueOf(batteryStats.getDeviceIdleModeCount(2, i)), java.lang.Long.valueOf(deviceIdlingTime2 / 1000), java.lang.Integer.valueOf(batteryStats.getDeviceIdlingCount(2, i)), java.lang.Integer.valueOf(batteryStats.getMobileRadioActiveCount(i)), java.lang.Long.valueOf(batteryStats.getMobileRadioActiveUnknownTime(i) / 1000), java.lang.Long.valueOf(deviceIdleModeTime / 1000), java.lang.Integer.valueOf(batteryStats.getDeviceIdleModeCount(1, i)), java.lang.Long.valueOf(deviceIdlingTime / 1000), java.lang.Integer.valueOf(batteryStats.getDeviceIdlingCount(1, i)), java.lang.Long.valueOf(batteryStats.getLongestDeviceIdleModeTime(1)), java.lang.Long.valueOf(batteryStats.getLongestDeviceIdleModeTime(2)));
        java.lang.Object[] objArr = new java.lang.Object[5];
        int i8 = 0;
        for (int i9 = 5; i8 < i9; i9 = 5) {
            objArr[i8] = java.lang.Long.valueOf(batteryStats.getScreenBrightnessTime(i8, j26, i) / 1000);
            i8++;
        }
        dumpLine(printWriter, 0, str10, "br", objArr);
        java.lang.Object[] objArr2 = new java.lang.Object[android.telephony.CellSignalStrength.getNumSignalStrengthLevels()];
        for (int i10 = 0; i10 < android.telephony.CellSignalStrength.getNumSignalStrengthLevels(); i10++) {
            objArr2[i10] = java.lang.Long.valueOf(batteryStats.getPhoneSignalStrengthTime(i10, j26, i) / 1000);
        }
        dumpLine(printWriter, 0, str10, SIGNAL_STRENGTH_TIME_DATA, objArr2);
        dumpLine(printWriter, 0, str10, SIGNAL_SCANNING_TIME_DATA, java.lang.Long.valueOf(batteryStats.getPhoneSignalScanningTime(j26, i) / 1000));
        for (int i11 = 0; i11 < android.telephony.CellSignalStrength.getNumSignalStrengthLevels(); i11++) {
            objArr2[i11] = java.lang.Integer.valueOf(batteryStats.getPhoneSignalStrengthCount(i11, i));
        }
        dumpLine(printWriter, 0, str10, SIGNAL_STRENGTH_COUNT_DATA, objArr2);
        java.lang.Object[] objArr3 = new java.lang.Object[NUM_DATA_CONNECTION_TYPES];
        for (int i12 = 0; i12 < NUM_DATA_CONNECTION_TYPES; i12++) {
            objArr3[i12] = java.lang.Long.valueOf(batteryStats.getPhoneDataConnectionTime(i12, j26, i) / 1000);
        }
        dumpLine(printWriter, 0, str10, DATA_CONNECTION_TIME_DATA, objArr3);
        for (int i13 = 0; i13 < NUM_DATA_CONNECTION_TYPES; i13++) {
            objArr3[i13] = java.lang.Integer.valueOf(batteryStats.getPhoneDataConnectionCount(i13, i));
        }
        dumpLine(printWriter, 0, str10, DATA_CONNECTION_COUNT_DATA, objArr3);
        java.lang.Object[] objArr4 = new java.lang.Object[8];
        for (int i14 = 0; i14 < 8; i14++) {
            objArr4[i14] = java.lang.Long.valueOf(batteryStats.getWifiStateTime(i14, j26, i) / 1000);
        }
        dumpLine(printWriter, 0, str10, WIFI_STATE_TIME_DATA, objArr4);
        for (int i15 = 0; i15 < 8; i15++) {
            objArr4[i15] = java.lang.Integer.valueOf(batteryStats.getWifiStateCount(i15, i));
        }
        dumpLine(printWriter, 0, str10, WIFI_STATE_COUNT_DATA, objArr4);
        java.lang.Object[] objArr5 = new java.lang.Object[13];
        for (int i16 = 0; i16 < 13; i16++) {
            objArr5[i16] = java.lang.Long.valueOf(batteryStats.getWifiSupplStateTime(i16, j26, i) / 1000);
        }
        dumpLine(printWriter, 0, str10, WIFI_SUPPL_STATE_TIME_DATA, objArr5);
        for (int i17 = 0; i17 < 13; i17++) {
            objArr5[i17] = java.lang.Integer.valueOf(batteryStats.getWifiSupplStateCount(i17, i));
        }
        dumpLine(printWriter, 0, str10, WIFI_SUPPL_STATE_COUNT_DATA, objArr5);
        java.lang.Object[] objArr6 = new java.lang.Object[5];
        int i18 = 0;
        for (int i19 = 5; i18 < i19; i19 = 5) {
            objArr6[i18] = java.lang.Long.valueOf(batteryStats.getWifiSignalStrengthTime(i18, j26, i) / 1000);
            i18++;
        }
        dumpLine(printWriter, 0, str10, WIFI_SIGNAL_STRENGTH_TIME_DATA, objArr6);
        for (int i20 = 0; i20 < 5; i20++) {
            objArr6[i20] = java.lang.Integer.valueOf(batteryStats.getWifiSignalStrengthCount(i20, i));
        }
        dumpLine(printWriter, 0, str10, WIFI_SIGNAL_STRENGTH_COUNT_DATA, objArr6);
        dumpLine(printWriter, 0, str10, WIFI_MULTICAST_TOTAL_DATA, java.lang.Long.valueOf(batteryStats.getWifiMulticastWakelockTime(j26, i) / 1000), java.lang.Integer.valueOf(batteryStats.getWifiMulticastWakelockCount(i)));
        dumpLine(printWriter, 0, str10, BATTERY_DISCHARGE_DATA, java.lang.Integer.valueOf(getLowDischargeAmountSinceCharge()), java.lang.Integer.valueOf(getHighDischargeAmountSinceCharge()), java.lang.Integer.valueOf(getDischargeAmountScreenOnSinceCharge()), java.lang.Integer.valueOf(getDischargeAmountScreenOffSinceCharge()), java.lang.Long.valueOf(uahDischarge / 1000), java.lang.Long.valueOf(uahDischargeScreenOff / 1000), java.lang.Integer.valueOf(getDischargeAmountScreenDozeSinceCharge()), java.lang.Long.valueOf(uahDischargeScreenDoze / 1000), java.lang.Long.valueOf(uahDischargeLightDoze / 1000), java.lang.Long.valueOf(uahDischargeDeepDoze / 1000));
        java.lang.String str11 = "\"";
        if (i2 >= 0) {
            str = str10;
            num = num3;
            str2 = "\"";
        } else {
            java.util.Map<java.lang.String, ? extends android.os.BatteryStats.Timer> kernelWakelockStats = getKernelWakelockStats();
            if (kernelWakelockStats.size() <= 0) {
                str = str10;
                num = num3;
                str2 = "\"";
            } else {
                for (java.util.Map.Entry<java.lang.String, ? extends android.os.BatteryStats.Timer> entry : kernelWakelockStats.entrySet()) {
                    sb7.setLength(0);
                    java.lang.Integer num4 = num3;
                    java.lang.String str12 = str11;
                    java.lang.String str13 = str10;
                    printWakeLockCheckin(sb7, entry.getValue(), j26, null, i, "");
                    dumpLine(printWriter, 0, str13, KERNEL_WAKELOCK_DATA, str12 + entry.getKey() + str12, sb7.toString());
                    str11 = str12;
                    str10 = str13;
                    num3 = num4;
                }
                str = str10;
                num = num3;
                str2 = str11;
            }
            java.util.Map<java.lang.String, ? extends android.os.BatteryStats.Timer> wakeupReasonStats = getWakeupReasonStats();
            if (wakeupReasonStats.size() > 0) {
                for (java.util.Map.Entry<java.lang.String, ? extends android.os.BatteryStats.Timer> entry2 : wakeupReasonStats.entrySet()) {
                    dumpLine(printWriter, 0, str, WAKEUP_REASON_DATA, str2 + entry2.getKey() + str2, java.lang.Long.valueOf((entry2.getValue().getTotalTimeLocked(j26, i) + 500) / 1000), java.lang.Integer.valueOf(entry2.getValue().getCountLocked(i)));
                }
            }
        }
        java.util.Map<java.lang.String, ? extends android.os.BatteryStats.Timer> rpmStats = getRpmStats();
        java.util.Map<java.lang.String, ? extends android.os.BatteryStats.Timer> screenOffRpmStats = getScreenOffRpmStats();
        if (rpmStats.size() <= 0) {
            sb = sb7;
        } else {
            java.util.Iterator<java.util.Map.Entry<java.lang.String, ? extends android.os.BatteryStats.Timer>> it = rpmStats.entrySet().iterator();
            while (it.hasNext()) {
                java.util.Map.Entry<java.lang.String, ? extends android.os.BatteryStats.Timer> next = it.next();
                java.lang.StringBuilder sb8 = sb7;
                sb8.setLength(0);
                android.os.BatteryStats.Timer value = next.getValue();
                long totalTimeLocked = (value.getTotalTimeLocked(j26, i) + 500) / 1000;
                int countLocked = value.getCountLocked(i);
                java.util.Iterator<java.util.Map.Entry<java.lang.String, ? extends android.os.BatteryStats.Timer>> it2 = it;
                android.os.BatteryStats.Timer timer = screenOffRpmStats.get(next.getKey());
                if (timer != null) {
                    long totalTimeLocked2 = (timer.getTotalTimeLocked(j26, i) + 500) / 1000;
                }
                if (timer != null) {
                    timer.getCountLocked(i);
                }
                dumpLine(printWriter, 0, str, RESOURCE_POWER_MANAGER_DATA, str2 + next.getKey() + str2, java.lang.Long.valueOf(totalTimeLocked), java.lang.Integer.valueOf(countLocked));
                sb7 = sb8;
                it = it2;
            }
            sb = sb7;
        }
        android.os.BatteryUsageStats batteryUsageStats = batteryStatsDumpHelper.getBatteryUsageStats(batteryStats, true);
        dumpLine(printWriter, 0, str, POWER_USE_SUMMARY_DATA, formatCharge(batteryUsageStats.getBatteryCapacity()), formatCharge(batteryUsageStats.getConsumedPower()), formatCharge(batteryUsageStats.getDischargedPowerRange().getLower().doubleValue()), formatCharge(batteryUsageStats.getDischargedPowerRange().getUpper().doubleValue()));
        android.os.AggregateBatteryConsumer aggregateBatteryConsumer = batteryUsageStats.getAggregateBatteryConsumer(0);
        int i21 = 0;
        while (i21 < 18) {
            java.lang.String str14 = CHECKIN_POWER_COMPONENT_LABELS[i21];
            if (str14 == null) {
                str14 = "???";
            }
            dumpLine(printWriter, 0, str, POWER_USE_ITEM_DATA, str14, formatCharge(aggregateBatteryConsumer.getConsumedPower(i21)), java.lang.Integer.valueOf(batteryStats.shouldHidePowerComponent(i21) ? 1 : 0), android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS, android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS);
            i21++;
            batteryStats = this;
            aggregateBatteryConsumer = aggregateBatteryConsumer;
        }
        android.os.BatteryStats.ProportionalAttributionCalculator proportionalAttributionCalculator = new android.os.BatteryStats.ProportionalAttributionCalculator(context, batteryUsageStats);
        java.util.List<android.os.UidBatteryConsumer> uidBatteryConsumers = batteryUsageStats.getUidBatteryConsumers();
        int i22 = 0;
        while (i22 < uidBatteryConsumers.size()) {
            android.os.UidBatteryConsumer uidBatteryConsumer = uidBatteryConsumers.get(i22);
            dumpLine(printWriter, uidBatteryConsumer.getUid(), str, POWER_USE_ITEM_DATA, "uid", formatCharge(uidBatteryConsumer.getConsumedPower()), java.lang.Integer.valueOf(proportionalAttributionCalculator.isSystemBatteryConsumer(uidBatteryConsumer) ? 1 : 0), formatCharge(uidBatteryConsumer.getConsumedPower(0)), formatCharge(proportionalAttributionCalculator.getProportionalPowerMah(uidBatteryConsumer)));
            i22++;
            uidBatteryConsumers = uidBatteryConsumers;
            proportionalAttributionCalculator = proportionalAttributionCalculator;
            str2 = str2;
        }
        java.lang.String str15 = str2;
        com.android.internal.os.CpuScalingPolicies cpuScalingPolicies2 = getCpuScalingPolicies();
        if (cpuScalingPolicies2 != null) {
            sb.setLength(0);
            int[] policies = cpuScalingPolicies2.getPolicies();
            int length = policies.length;
            for (int i23 = 0; i23 < length; i23++) {
                int[] frequencies = cpuScalingPolicies2.getFrequencies(policies[i23]);
                int length2 = frequencies.length;
                int i24 = 0;
                while (i24 < length2) {
                    int i25 = frequencies[i24];
                    if (sb.length() != 0) {
                        iArr = policies;
                        sb.append(',');
                    } else {
                        iArr = policies;
                    }
                    sb.append(i25);
                    i24++;
                    policies = iArr;
                }
            }
            dumpLine(printWriter, 0, str, GLOBAL_CPU_FREQ_DATA, sb.toString());
        }
        int i26 = 0;
        while (i26 < size) {
            int keyAt = uidStats.keyAt(i26);
            java.lang.String str16 = str;
            if (i2 >= 0 && keyAt != i2) {
                cpuScalingPolicies = cpuScalingPolicies2;
                sb3 = sb;
                i4 = i26;
                i3 = size;
                sparseArray = uidStats;
                j5 = j26;
                str5 = str15;
                j6 = j21;
                j7 = j25;
                str4 = str16;
            } else {
                android.os.BatteryStats.Uid valueAt2 = uidStats.valueAt(i26);
                long networkActivityBytes = valueAt2.getNetworkActivityBytes(0, i);
                long networkActivityBytes2 = valueAt2.getNetworkActivityBytes(1, i);
                long networkActivityBytes3 = valueAt2.getNetworkActivityBytes(2, i);
                long networkActivityBytes4 = valueAt2.getNetworkActivityBytes(3, i);
                long networkActivityPackets = valueAt2.getNetworkActivityPackets(0, i);
                long networkActivityPackets2 = valueAt2.getNetworkActivityPackets(1, i);
                long mobileRadioActiveTime = valueAt2.getMobileRadioActiveTime(i);
                int mobileRadioActiveCount = valueAt2.getMobileRadioActiveCount(i);
                long mobileRadioApWakeupCount = valueAt2.getMobileRadioApWakeupCount(i);
                int i27 = i26;
                long networkActivityPackets3 = valueAt2.getNetworkActivityPackets(2, i);
                long networkActivityPackets4 = valueAt2.getNetworkActivityPackets(3, i);
                long wifiRadioApWakeupCount = valueAt2.getWifiRadioApWakeupCount(i);
                long networkActivityBytes5 = valueAt2.getNetworkActivityBytes(4, i);
                long networkActivityBytes6 = valueAt2.getNetworkActivityBytes(5, i);
                long networkActivityBytes7 = valueAt2.getNetworkActivityBytes(6, i);
                long networkActivityBytes8 = valueAt2.getNetworkActivityBytes(7, i);
                long networkActivityBytes9 = valueAt2.getNetworkActivityBytes(8, i);
                long networkActivityBytes10 = valueAt2.getNetworkActivityBytes(9, i);
                long networkActivityPackets5 = valueAt2.getNetworkActivityPackets(6, i);
                long networkActivityPackets6 = valueAt2.getNetworkActivityPackets(7, i);
                long networkActivityPackets7 = valueAt2.getNetworkActivityPackets(8, i);
                long networkActivityPackets8 = valueAt2.getNetworkActivityPackets(9, i);
                if (networkActivityBytes > 0 || networkActivityBytes2 > 0 || networkActivityBytes3 > 0 || networkActivityBytes4 > 0 || networkActivityPackets > 0 || networkActivityPackets2 > 0 || networkActivityPackets3 > 0 || networkActivityPackets4 > 0 || mobileRadioActiveTime > 0 || mobileRadioActiveCount > 0 || networkActivityBytes5 > 0 || networkActivityBytes6 > 0 || mobileRadioApWakeupCount > 0 || wifiRadioApWakeupCount > 0 || networkActivityBytes7 > 0 || networkActivityBytes8 > 0 || networkActivityBytes9 > 0 || networkActivityBytes10 > 0 || networkActivityPackets5 > 0 || networkActivityPackets6 > 0 || networkActivityPackets7 > 0 || networkActivityPackets8 > 0) {
                    dumpLine(printWriter, keyAt, str16, NETWORK_DATA, java.lang.Long.valueOf(networkActivityBytes), java.lang.Long.valueOf(networkActivityBytes2), java.lang.Long.valueOf(networkActivityBytes3), java.lang.Long.valueOf(networkActivityBytes4), java.lang.Long.valueOf(networkActivityPackets), java.lang.Long.valueOf(networkActivityPackets2), java.lang.Long.valueOf(networkActivityPackets3), java.lang.Long.valueOf(networkActivityPackets4), java.lang.Long.valueOf(mobileRadioActiveTime), java.lang.Integer.valueOf(mobileRadioActiveCount), java.lang.Long.valueOf(networkActivityBytes5), java.lang.Long.valueOf(networkActivityBytes6), java.lang.Long.valueOf(mobileRadioApWakeupCount), java.lang.Long.valueOf(wifiRadioApWakeupCount), java.lang.Long.valueOf(networkActivityBytes7), java.lang.Long.valueOf(networkActivityBytes8), java.lang.Long.valueOf(networkActivityBytes9), java.lang.Long.valueOf(networkActivityBytes10), java.lang.Long.valueOf(networkActivityPackets5), java.lang.Long.valueOf(networkActivityPackets6), java.lang.Long.valueOf(networkActivityPackets7), java.lang.Long.valueOf(networkActivityPackets8));
                }
                dumpControllerActivityLine(printWriter, keyAt, str16, MODEM_CONTROLLER_DATA, valueAt2.getModemControllerActivity(), i);
                long fullWifiLockTime = valueAt2.getFullWifiLockTime(j26, i);
                long wifiScanTime = valueAt2.getWifiScanTime(j26, i);
                int wifiScanCount = valueAt2.getWifiScanCount(i);
                int wifiScanBackgroundCount = valueAt2.getWifiScanBackgroundCount(i);
                long wifiScanActualTime = (valueAt2.getWifiScanActualTime(j26) + 500) / 1000;
                long wifiScanBackgroundTime = (valueAt2.getWifiScanBackgroundTime(j26) + 500) / 1000;
                long wifiRunningTime = valueAt2.getWifiRunningTime(j26, i);
                if (fullWifiLockTime == 0 && wifiScanTime == 0 && wifiScanCount == 0 && wifiScanBackgroundCount == 0 && wifiScanActualTime == 0 && wifiScanBackgroundTime == 0 && wifiRunningTime == 0) {
                    i3 = size;
                    sparseArray = uidStats;
                    j = j26;
                    str3 = str16;
                    j2 = j21;
                    c = 2;
                } else {
                    i3 = size;
                    sparseArray = uidStats;
                    j = j26;
                    j2 = j21;
                    c = 2;
                    str3 = str16;
                    dumpLine(printWriter, keyAt, str3, WIFI_DATA, java.lang.Long.valueOf(fullWifiLockTime), java.lang.Long.valueOf(wifiScanTime), java.lang.Long.valueOf(wifiRunningTime), java.lang.Integer.valueOf(wifiScanCount), num, num, num, java.lang.Integer.valueOf(wifiScanBackgroundCount), java.lang.Long.valueOf(wifiScanActualTime), java.lang.Long.valueOf(wifiScanBackgroundTime));
                }
                str4 = str3;
                dumpControllerActivityLine(printWriter, keyAt, str4, WIFI_CONTROLLER_DATA, valueAt2.getWifiControllerActivity(), i);
                android.os.BatteryStats.Timer bluetoothScanTimer = valueAt2.getBluetoothScanTimer();
                if (bluetoothScanTimer == null) {
                    cpuScalingPolicies = cpuScalingPolicies2;
                    j3 = j2;
                } else {
                    long j27 = j;
                    long totalTimeLocked3 = (bluetoothScanTimer.getTotalTimeLocked(j27, i) + 500) / 1000;
                    if (totalTimeLocked3 == 0) {
                        cpuScalingPolicies = cpuScalingPolicies2;
                        j = j27;
                        j3 = j2;
                    } else {
                        int countLocked2 = bluetoothScanTimer.getCountLocked(i);
                        android.os.BatteryStats.Timer bluetoothScanBackgroundTimer = valueAt2.getBluetoothScanBackgroundTimer();
                        int countLocked3 = bluetoothScanBackgroundTimer != null ? bluetoothScanBackgroundTimer.getCountLocked(i) : 0;
                        j3 = j2;
                        long totalDurationMsLocked = bluetoothScanTimer.getTotalDurationMsLocked(j3);
                        if (bluetoothScanBackgroundTimer == null) {
                            j15 = 0;
                        } else {
                            j15 = bluetoothScanBackgroundTimer.getTotalDurationMsLocked(j3);
                        }
                        int countLocked4 = valueAt2.getBluetoothScanResultCounter() != null ? valueAt2.getBluetoothScanResultCounter().getCountLocked(i) : 0;
                        int countLocked5 = valueAt2.getBluetoothScanResultBgCounter() != null ? valueAt2.getBluetoothScanResultBgCounter().getCountLocked(i) : 0;
                        j = j27;
                        android.os.BatteryStats.Timer bluetoothUnoptimizedScanTimer = valueAt2.getBluetoothUnoptimizedScanTimer();
                        if (bluetoothUnoptimizedScanTimer == null) {
                            j16 = 0;
                        } else {
                            j16 = bluetoothUnoptimizedScanTimer.getTotalDurationMsLocked(j3);
                        }
                        if (bluetoothUnoptimizedScanTimer == null) {
                            j17 = 0;
                        } else {
                            j17 = bluetoothUnoptimizedScanTimer.getMaxDurationMsLocked(j3);
                        }
                        cpuScalingPolicies = cpuScalingPolicies2;
                        android.os.BatteryStats.Timer bluetoothUnoptimizedScanBackgroundTimer = valueAt2.getBluetoothUnoptimizedScanBackgroundTimer();
                        if (bluetoothUnoptimizedScanBackgroundTimer == null) {
                            j18 = 0;
                        } else {
                            j18 = bluetoothUnoptimizedScanBackgroundTimer.getTotalDurationMsLocked(j3);
                        }
                        if (bluetoothUnoptimizedScanBackgroundTimer == null) {
                            j19 = 0;
                        } else {
                            j19 = bluetoothUnoptimizedScanBackgroundTimer.getMaxDurationMsLocked(j3);
                        }
                        dumpLine(printWriter, keyAt, str4, BLUETOOTH_MISC_DATA, java.lang.Long.valueOf(totalTimeLocked3), java.lang.Integer.valueOf(countLocked2), java.lang.Integer.valueOf(countLocked3), java.lang.Long.valueOf(totalDurationMsLocked), java.lang.Long.valueOf(j15), java.lang.Integer.valueOf(countLocked4), java.lang.Integer.valueOf(countLocked5), java.lang.Long.valueOf(j16), java.lang.Long.valueOf(j18), java.lang.Long.valueOf(j17), java.lang.Long.valueOf(j19));
                    }
                }
                long j28 = j;
                dumpControllerActivityLine(printWriter, keyAt, str4, BLUETOOTH_CONTROLLER_DATA, valueAt2.getBluetoothControllerActivity(), i);
                if (valueAt2.hasUserActivity()) {
                    java.lang.Object[] objArr7 = new java.lang.Object[android.os.BatteryStats.Uid.NUM_USER_ACTIVITY_TYPES];
                    boolean z2 = false;
                    for (int i28 = 0; i28 < android.os.BatteryStats.Uid.NUM_USER_ACTIVITY_TYPES; i28++) {
                        int userActivityCount = valueAt2.getUserActivityCount(i28, i);
                        objArr7[i28] = java.lang.Integer.valueOf(userActivityCount);
                        if (userActivityCount != 0) {
                            z2 = true;
                        }
                    }
                    if (z2) {
                        dumpLine(printWriter, keyAt, str4, USER_ACTIVITY_DATA, objArr7);
                    }
                }
                if (valueAt2.getAggregatedPartialWakelockTimer() != null) {
                    android.os.BatteryStats.Timer aggregatedPartialWakelockTimer = valueAt2.getAggregatedPartialWakelockTimer();
                    long totalDurationMsLocked2 = aggregatedPartialWakelockTimer.getTotalDurationMsLocked(j3);
                    android.os.BatteryStats.Timer subTimer = aggregatedPartialWakelockTimer.getSubTimer();
                    if (subTimer == null) {
                        j14 = 0;
                    } else {
                        j14 = subTimer.getTotalDurationMsLocked(j3);
                    }
                    dumpLine(printWriter, keyAt, str4, AGGREGATED_WAKELOCK_DATA, java.lang.Long.valueOf(totalDurationMsLocked2), java.lang.Long.valueOf(j14));
                }
                android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Wakelock> wakelockStats2 = valueAt2.getWakelockStats();
                int size3 = wakelockStats2.size() - 1;
                while (size3 >= 0) {
                    android.os.BatteryStats.Uid.Wakelock valueAt3 = wakelockStats2.valueAt(size3);
                    sb.setLength(0);
                    long j29 = j3;
                    int i29 = size3;
                    java.lang.StringBuilder sb9 = sb;
                    java.lang.String printWakeLockCheckin = printWakeLockCheckin(sb, valueAt3.getWakeTime(1), j28, android.app.backup.FullBackup.FILES_TREE_TOKEN, i, "");
                    android.os.BatteryStats.Timer wakeTime3 = valueAt3.getWakeTime(0);
                    printWakeLockCheckin(sb9, valueAt3.getWakeTime(2), j28, "w", i, printWakeLockCheckin(sb9, wakeTime3 != null ? wakeTime3.getSubTimer() : null, j28, "bp", i, printWakeLockCheckin(sb9, wakeTime3, j28, "p", i, printWakeLockCheckin)));
                    if (sb9.length() > 0) {
                        java.lang.String keyAt2 = wakelockStats2.keyAt(i29);
                        if (keyAt2.indexOf(44) < 0) {
                            c2 = '_';
                        } else {
                            c2 = '_';
                            keyAt2 = keyAt2.replace(',', '_');
                        }
                        if (keyAt2.indexOf(10) >= 0) {
                            keyAt2 = keyAt2.replace('\n', c2);
                        }
                        if (keyAt2.indexOf(13) >= 0) {
                            keyAt2 = keyAt2.replace('\r', c2);
                        }
                        dumpLine(printWriter, keyAt, str4, "wl", keyAt2, sb9.toString());
                    }
                    size3 = i29 - 1;
                    sb = sb9;
                    j3 = j29;
                }
                java.lang.StringBuilder sb10 = sb;
                long j30 = j3;
                android.os.BatteryStats.Timer multicastWakelockStats = valueAt2.getMulticastWakelockStats();
                if (multicastWakelockStats == null) {
                    j4 = j28;
                } else {
                    j4 = j28;
                    long totalTimeLocked4 = multicastWakelockStats.getTotalTimeLocked(j4, i) / 1000;
                    int countLocked6 = multicastWakelockStats.getCountLocked(i);
                    if (totalTimeLocked4 > 0) {
                        dumpLine(printWriter, keyAt, str4, WIFI_MULTICAST_DATA, java.lang.Long.valueOf(totalTimeLocked4), java.lang.Integer.valueOf(countLocked6));
                    }
                }
                android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Timer> syncStats = valueAt2.getSyncStats();
                int size4 = syncStats.size() - 1;
                while (size4 >= 0) {
                    android.os.BatteryStats.Timer valueAt4 = syncStats.valueAt(size4);
                    long totalTimeLocked5 = (valueAt4.getTotalTimeLocked(j4, i) + 500) / 1000;
                    int countLocked7 = valueAt4.getCountLocked(i);
                    android.os.BatteryStats.Timer subTimer2 = valueAt4.getSubTimer();
                    if (subTimer2 != null) {
                        long j31 = j30;
                        j12 = j31;
                        j13 = subTimer2.getTotalDurationMsLocked(j31);
                    } else {
                        j12 = j30;
                        j13 = -1;
                    }
                    int countLocked8 = subTimer2 != null ? subTimer2.getCountLocked(i) : -1;
                    if (totalTimeLocked5 == 0) {
                        str8 = str15;
                        arrayMap3 = syncStats;
                    } else {
                        java.lang.String str17 = str15;
                        arrayMap3 = syncStats;
                        java.lang.String str18 = str17 + syncStats.keyAt(size4) + str17;
                        java.lang.Long valueOf = java.lang.Long.valueOf(totalTimeLocked5);
                        str8 = str17;
                        dumpLine(printWriter, keyAt, str4, SYNC_DATA, str18, valueOf, java.lang.Integer.valueOf(countLocked7), java.lang.Long.valueOf(j13), java.lang.Integer.valueOf(countLocked8));
                    }
                    size4--;
                    j30 = j12;
                    syncStats = arrayMap3;
                    str15 = str8;
                }
                java.lang.String str19 = str15;
                long j32 = j30;
                android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Timer> jobStats = valueAt2.getJobStats();
                int size5 = jobStats.size() - 1;
                while (size5 >= 0) {
                    android.os.BatteryStats.Timer valueAt5 = jobStats.valueAt(size5);
                    long totalTimeLocked6 = (valueAt5.getTotalTimeLocked(j4, i) + 500) / 1000;
                    int countLocked9 = valueAt5.getCountLocked(i);
                    android.os.BatteryStats.Timer subTimer3 = valueAt5.getSubTimer();
                    long totalDurationMsLocked3 = subTimer3 != null ? subTimer3.getTotalDurationMsLocked(j32) : -1L;
                    int countLocked10 = subTimer3 != null ? subTimer3.getCountLocked(i) : -1;
                    if (totalTimeLocked6 == 0) {
                        arrayMap2 = jobStats;
                        str7 = str19;
                    } else {
                        str7 = str19;
                        arrayMap2 = jobStats;
                        dumpLine(printWriter, keyAt, str4, JOB_DATA, str7 + jobStats.keyAt(size5) + str7, java.lang.Long.valueOf(totalTimeLocked6), java.lang.Integer.valueOf(countLocked9), java.lang.Long.valueOf(totalDurationMsLocked3), java.lang.Integer.valueOf(countLocked10));
                    }
                    size5--;
                    str19 = str7;
                    jobStats = arrayMap2;
                }
                java.lang.String str20 = str19;
                int[] jobStopReasonCodes = android.app.job.JobParameters.getJobStopReasonCodes();
                java.lang.Object[] objArr8 = new java.lang.Object[jobStopReasonCodes.length + 1];
                android.util.ArrayMap<java.lang.String, android.util.SparseIntArray> jobCompletionStats = valueAt2.getJobCompletionStats();
                int size6 = jobCompletionStats.size() - 1;
                while (size6 >= 0) {
                    android.util.SparseIntArray valueAt6 = jobCompletionStats.valueAt(size6);
                    if (valueAt6 == null) {
                        j11 = j4;
                    } else {
                        j11 = j4;
                        int i30 = 0;
                        objArr8[0] = str20 + jobCompletionStats.keyAt(size6) + str20;
                        int i31 = 0;
                        while (i31 < jobStopReasonCodes.length) {
                            int i32 = i31 + 1;
                            objArr8[i32] = java.lang.Integer.valueOf(valueAt6.get(jobStopReasonCodes[i31], i30));
                            i31 = i32;
                            i30 = 0;
                        }
                        dumpLine(printWriter, keyAt, str4, JOB_COMPLETION_DATA, objArr8);
                    }
                    size6--;
                    j4 = j11;
                }
                long j33 = j4;
                valueAt2.getDeferredJobsCheckinLineLocked(sb10, i);
                if (sb10.length() > 0) {
                    dumpLine(printWriter, keyAt, str4, JOBS_DEFERRED_DATA, sb10.toString());
                }
                long j34 = j33;
                int i33 = keyAt;
                java.lang.String str21 = str20;
                i4 = i27;
                java.lang.Integer num5 = num;
                dumpTimer(printWriter, keyAt, str4, FLASHLIGHT_DATA, valueAt2.getFlashlightTurnedOnTimer(), j34, i);
                dumpTimer(printWriter, i33, str4, CAMERA_DATA, valueAt2.getCameraTurnedOnTimer(), j34, i);
                dumpTimer(printWriter, i33, str4, VIDEO_DATA, valueAt2.getVideoTurnedOnTimer(), j34, i);
                dumpTimer(printWriter, i33, str4, AUDIO_DATA, valueAt2.getAudioTurnedOnTimer(), j34, i);
                android.util.SparseArray<? extends android.os.BatteryStats.Uid.Sensor> sensorStats = valueAt2.getSensorStats();
                int size7 = sensorStats.size();
                int i34 = 0;
                while (i34 < size7) {
                    android.os.BatteryStats.Uid.Sensor valueAt7 = sensorStats.valueAt(i34);
                    int keyAt3 = sensorStats.keyAt(i34);
                    android.os.BatteryStats.Timer sensorTime = valueAt7.getSensorTime();
                    if (sensorTime == null) {
                        j9 = j34;
                        i5 = i33;
                    } else {
                        j9 = j34;
                        long totalTimeLocked7 = (sensorTime.getTotalTimeLocked(j9, i) + 500) / 1000;
                        if (totalTimeLocked7 == 0) {
                            i5 = i33;
                        } else {
                            int countLocked11 = sensorTime.getCountLocked(i);
                            android.os.BatteryStats.Timer sensorBackgroundTime = valueAt7.getSensorBackgroundTime();
                            int countLocked12 = sensorBackgroundTime != null ? sensorBackgroundTime.getCountLocked(i) : 0;
                            long totalDurationMsLocked4 = sensorTime.getTotalDurationMsLocked(j32);
                            if (sensorBackgroundTime == null) {
                                j10 = 0;
                            } else {
                                j10 = sensorBackgroundTime.getTotalDurationMsLocked(j32);
                            }
                            java.lang.Object[] objArr9 = {java.lang.Integer.valueOf(keyAt3), java.lang.Long.valueOf(totalTimeLocked7), java.lang.Integer.valueOf(countLocked11), java.lang.Integer.valueOf(countLocked12), java.lang.Long.valueOf(totalDurationMsLocked4), java.lang.Long.valueOf(j10)};
                            i5 = i33;
                            dumpLine(printWriter, i5, str4, SENSOR_DATA, objArr9);
                        }
                    }
                    i34++;
                    i33 = i5;
                    j34 = j9;
                }
                long j35 = j34;
                int i35 = i33;
                dumpTimer(printWriter, i35, str4, VIBRATOR_DATA, valueAt2.getVibratorOnTimer(), j35, i);
                dumpTimer(printWriter, i35, str4, FOREGROUND_ACTIVITY_DATA, valueAt2.getForegroundActivityTimer(), j35, i);
                dumpTimer(printWriter, i35, str4, FOREGROUND_SERVICE_DATA, valueAt2.getForegroundServiceTimer(), j35, i);
                java.lang.Object[] objArr10 = new java.lang.Object[7];
                long j36 = 0;
                int i36 = 0;
                for (int i37 = 7; i36 < i37; i37 = 7) {
                    long processStateTime = valueAt2.getProcessStateTime(i36, j35, i);
                    j36 += processStateTime;
                    objArr10[i36] = java.lang.Long.valueOf((processStateTime + 500) / 1000);
                    i36++;
                }
                long j37 = j35;
                if (j36 > 0) {
                    dumpLine(printWriter, i35, str4, "st", objArr10);
                }
                long userCpuTimeUs = valueAt2.getUserCpuTimeUs(i);
                long systemCpuTimeUs = valueAt2.getSystemCpuTimeUs(i);
                if (userCpuTimeUs > 0 || systemCpuTimeUs > 0) {
                    dumpLine(printWriter, i35, str4, CPU_DATA, java.lang.Long.valueOf(userCpuTimeUs / 1000), java.lang.Long.valueOf(systemCpuTimeUs / 1000), num5);
                }
                if (cpuScalingPolicies == null) {
                    j5 = j37;
                    j6 = j32;
                    sb2 = sb10;
                } else {
                    long[] cpuFreqTimes = valueAt2.getCpuFreqTimes(i);
                    if (cpuFreqTimes == null) {
                        j5 = j37;
                        sb2 = sb10;
                    } else if (cpuFreqTimes.length != cpuScalingPolicies.getScalingStepCount()) {
                        j5 = j37;
                        sb2 = sb10;
                    } else {
                        sb2 = sb10;
                        sb2.setLength(0);
                        for (int i38 = 0; i38 < cpuFreqTimes.length; i38++) {
                            if (i38 != 0) {
                                sb2.append(',');
                            }
                            sb2.append(cpuFreqTimes[i38]);
                        }
                        long[] screenOffCpuFreqTimes = valueAt2.getScreenOffCpuFreqTimes(i);
                        if (screenOffCpuFreqTimes != null) {
                            int i39 = 0;
                            while (i39 < screenOffCpuFreqTimes.length) {
                                sb2.append(',').append(screenOffCpuFreqTimes[i39]);
                                i39++;
                                j37 = j37;
                            }
                            j5 = j37;
                        } else {
                            j5 = j37;
                            for (int i40 = 0; i40 < cpuFreqTimes.length; i40++) {
                                sb2.append(",0");
                            }
                        }
                        dumpLine(printWriter, i35, str4, CPU_TIMES_AT_FREQ_DATA, "A", java.lang.Integer.valueOf(cpuFreqTimes.length), sb2.toString());
                    }
                    int scalingStepCount = getCpuScalingPolicies().getScalingStepCount();
                    long[] jArr = new long[scalingStepCount];
                    int i41 = 0;
                    while (i41 < 7) {
                        if (!valueAt2.getCpuFreqTimes(jArr, i41)) {
                            j8 = j32;
                        } else {
                            sb2.setLength(0);
                            for (int i42 = 0; i42 < scalingStepCount; i42++) {
                                if (i42 != 0) {
                                    sb2.append(',');
                                }
                                sb2.append(jArr[i42]);
                            }
                            if (valueAt2.getScreenOffCpuFreqTimes(jArr, i41)) {
                                int i43 = 0;
                                while (i43 < scalingStepCount) {
                                    sb2.append(',').append(jArr[i43]);
                                    i43++;
                                    j32 = j32;
                                }
                                j8 = j32;
                            } else {
                                j8 = j32;
                                for (int i44 = 0; i44 < scalingStepCount; i44++) {
                                    sb2.append(",0");
                                }
                            }
                            dumpLine(printWriter, i35, str4, CPU_TIMES_AT_FREQ_DATA, android.os.BatteryStats.Uid.UID_PROCESS_TYPES[i41], java.lang.Integer.valueOf(scalingStepCount), sb2.toString());
                        }
                        i41++;
                        j32 = j8;
                    }
                    j6 = j32;
                }
                android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Proc> processStats = valueAt2.getProcessStats();
                int size8 = processStats.size() - 1;
                while (size8 >= 0) {
                    android.os.BatteryStats.Uid.Proc valueAt8 = processStats.valueAt(size8);
                    long userTime = valueAt8.getUserTime(i);
                    long systemTime = valueAt8.getSystemTime(i);
                    long foregroundTime = valueAt8.getForegroundTime(i);
                    int starts = valueAt8.getStarts(i);
                    int numCrashes = valueAt8.getNumCrashes(i);
                    int numAnrs = valueAt8.getNumAnrs(i);
                    if (userTime == 0 && systemTime == 0 && foregroundTime == 0 && starts == 0 && numAnrs == 0 && numCrashes == 0) {
                        num2 = num5;
                        arrayMap = processStats;
                        sb4 = sb2;
                        str6 = str21;
                    } else {
                        num2 = num5;
                        sb4 = sb2;
                        str6 = str21;
                        arrayMap = processStats;
                        dumpLine(printWriter, i35, str4, PROCESS_DATA, str6 + processStats.keyAt(size8) + str6, java.lang.Long.valueOf(userTime), java.lang.Long.valueOf(systemTime), java.lang.Long.valueOf(foregroundTime), java.lang.Integer.valueOf(starts), java.lang.Integer.valueOf(numAnrs), java.lang.Integer.valueOf(numCrashes));
                    }
                    size8--;
                    processStats = arrayMap;
                    str21 = str6;
                    num5 = num2;
                    sb2 = sb4;
                }
                num = num5;
                sb3 = sb2;
                str5 = str21;
                android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Pkg> packageStats = valueAt2.getPackageStats();
                for (int size9 = packageStats.size() - 1; size9 >= 0; size9--) {
                    android.os.BatteryStats.Uid.Pkg valueAt9 = packageStats.valueAt(size9);
                    android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Counter> wakeupAlarmStats = valueAt9.getWakeupAlarmStats();
                    int i45 = 0;
                    for (int size10 = wakeupAlarmStats.size() - 1; size10 >= 0; size10--) {
                        int countLocked13 = wakeupAlarmStats.valueAt(size10).getCountLocked(i);
                        i45 += countLocked13;
                        dumpLine(printWriter, i35, str4, WAKEUP_ALARM_DATA, wakeupAlarmStats.keyAt(size10).replace(',', '_'), java.lang.Integer.valueOf(countLocked13));
                    }
                    android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Pkg.Serv> serviceStats = valueAt9.getServiceStats();
                    int size11 = serviceStats.size() - 1;
                    while (size11 >= 0) {
                        android.os.BatteryStats.Uid.Pkg.Serv valueAt10 = serviceStats.valueAt(size11);
                        long j38 = j25;
                        long startTime = valueAt10.getStartTime(j38, i);
                        int starts2 = valueAt10.getStarts(i);
                        int launches = valueAt10.getLaunches(i);
                        if (startTime != 0 || starts2 != 0 || launches != 0) {
                            dumpLine(printWriter, i35, str4, APK_DATA, java.lang.Integer.valueOf(i45), packageStats.keyAt(size9), serviceStats.keyAt(size11), java.lang.Long.valueOf(startTime / 1000), java.lang.Integer.valueOf(starts2), java.lang.Integer.valueOf(launches));
                        }
                        size11--;
                        j25 = j38;
                    }
                }
                j7 = j25;
            }
            i26 = i4 + 1;
            str15 = str5;
            j25 = j7;
            str = str4;
            size = i3;
            uidStats = sparseArray;
            cpuScalingPolicies2 = cpuScalingPolicies;
            sb = sb3;
            j21 = j6;
            j26 = j5;
        }
    }

    static final class TimerEntry {
        final int mId;
        final java.lang.String mName;
        final long mTime;
        final android.os.BatteryStats.Timer mTimer;

        TimerEntry(java.lang.String str, int i, android.os.BatteryStats.Timer timer, long j) {
            this.mName = str;
            this.mId = i;
            this.mTimer = timer;
            this.mTime = j;
        }
    }

    private void printmAh(java.io.PrintWriter printWriter, double d) {
        printWriter.print(formatCharge(d));
    }

    private void printmAh(java.lang.StringBuilder sb, double d) {
        sb.append(formatCharge(d));
    }

    /* JADX WARN: Removed duplicated region for block: B:405:0x17b6  */
    /* JADX WARN: Removed duplicated region for block: B:424:0x182a  */
    /* JADX WARN: Removed duplicated region for block: B:434:0x1910  */
    /* JADX WARN: Removed duplicated region for block: B:453:0x19fe  */
    /* JADX WARN: Removed duplicated region for block: B:458:0x1a4f  */
    /* JADX WARN: Removed duplicated region for block: B:480:0x1afd  */
    /* JADX WARN: Removed duplicated region for block: B:501:0x1b97  */
    /* JADX WARN: Removed duplicated region for block: B:516:0x1c03  */
    /* JADX WARN: Removed duplicated region for block: B:519:0x1c79  */
    /* JADX WARN: Removed duplicated region for block: B:554:0x1da3  */
    /* JADX WARN: Removed duplicated region for block: B:564:0x1df1  */
    /* JADX WARN: Removed duplicated region for block: B:567:0x1e17  */
    /* JADX WARN: Removed duplicated region for block: B:571:0x1e44  */
    /* JADX WARN: Removed duplicated region for block: B:579:0x1e6c  */
    /* JADX WARN: Removed duplicated region for block: B:588:0x1e9c  */
    /* JADX WARN: Removed duplicated region for block: B:610:0x1f36  */
    /* JADX WARN: Removed duplicated region for block: B:668:0x20b9  */
    /* JADX WARN: Removed duplicated region for block: B:692:0x21b2  */
    /* JADX WARN: Removed duplicated region for block: B:694:0x21ba A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:697:0x1a3b  */
    /* JADX WARN: Removed duplicated region for block: B:731:0x19f3  */
    /* JADX WARN: Removed duplicated region for block: B:732:0x17ff  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void dumpLocked(android.content.Context context, java.io.PrintWriter printWriter, java.lang.String str, int i, int i2, boolean z, android.os.BatteryStats.BatteryStatsDumpHelper batteryStatsDumpHelper) {
        java.lang.String str2;
        java.lang.String str3;
        java.lang.String str4;
        long j;
        long j2;
        java.lang.String str5;
        java.lang.String str6;
        java.lang.String str7;
        java.lang.String str8;
        java.lang.String str9;
        int i3;
        java.lang.String str10;
        long j3;
        long j4;
        java.io.PrintWriter printWriter2;
        long j5;
        long j6;
        long j7;
        java.lang.String str11;
        java.lang.String str12;
        java.lang.String str13;
        long j8;
        long j9;
        java.lang.String str14;
        java.lang.String str15;
        java.lang.String str16;
        java.lang.String str17;
        long j10;
        java.lang.String str18;
        java.lang.String str19;
        java.lang.String str20;
        long j11;
        java.lang.String str21;
        java.lang.String str22;
        java.lang.String str23;
        java.lang.String str24;
        java.lang.String str25;
        long j12;
        boolean z2;
        int i4;
        int i5;
        java.lang.String str26;
        int i6;
        java.lang.StringBuilder sb;
        long j13;
        java.lang.String str27;
        int i7;
        long j14;
        java.lang.String str28;
        java.lang.String str29;
        java.lang.String str30;
        java.io.PrintWriter printWriter3;
        int i8;
        android.os.BatteryStats batteryStats;
        java.lang.String str31;
        long j15;
        java.lang.StringBuilder sb2;
        java.lang.String str32;
        long j16;
        int i9;
        java.lang.String str33;
        int i10;
        android.os.BatteryStats.Uid uid;
        long j17;
        long j18;
        java.lang.String str34;
        java.lang.String str35;
        int i11;
        int i12;
        long j19;
        long j20;
        java.lang.String str36;
        java.lang.String str37;
        java.lang.String str38;
        long j21;
        long j22;
        java.lang.String str39;
        java.lang.String str40;
        java.lang.String str41;
        java.lang.String str42;
        boolean z3;
        int i13;
        android.os.BatteryStats.Uid uid2;
        java.lang.String str43;
        int i14;
        int size;
        java.lang.String str44;
        boolean z4;
        java.lang.String str45;
        android.os.BatteryStats.Timer multicastWakelockStats;
        java.lang.String str46;
        int i15;
        long j23;
        int size2;
        int size3;
        java.lang.String str47;
        int size4;
        long j24;
        long j25;
        java.lang.String str48;
        android.util.SparseArray<? extends android.os.BatteryStats.Uid> sparseArray;
        java.lang.String str49;
        int size5;
        int i16;
        java.io.PrintWriter printWriter4;
        long j26;
        int i17;
        java.lang.StringBuilder sb3;
        long j27;
        long userCpuTimeUs;
        long systemCpuTimeUs;
        long[] cpuFreqTimes;
        long[] screenOffCpuFreqTimes;
        int i18;
        long j28;
        int size6;
        java.lang.String str50;
        int size7;
        boolean z5;
        java.lang.String str51;
        long j29;
        java.lang.String str52;
        boolean z6;
        boolean z7;
        java.lang.String str53;
        android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Proc> arrayMap;
        java.lang.String str54;
        boolean z8;
        java.lang.String str55;
        android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Proc> arrayMap2;
        java.lang.String str56;
        long j30;
        java.lang.String str57;
        boolean z9;
        java.lang.StringBuilder sb4;
        long j31;
        java.lang.String str58;
        android.util.SparseArray<? extends android.os.BatteryStats.Uid.Sensor> sparseArray2;
        int i19;
        long j32;
        java.lang.String str59;
        int i20;
        long j33;
        java.lang.String str60;
        java.lang.String str61;
        long j34;
        long j35;
        java.lang.String str62;
        long j36;
        long j37;
        java.lang.String str63;
        java.lang.String str64;
        java.lang.String str65;
        long j38;
        long j39;
        boolean z10;
        boolean z11;
        long j40;
        java.lang.String str66;
        android.os.BatteryStats.Timer timer;
        long j41;
        int i21;
        long j42;
        long j43;
        long j44;
        long j45;
        long j46;
        java.lang.String str67;
        java.util.Comparator<android.os.BatteryStats.TimerEntry> comparator;
        java.lang.String str68;
        java.lang.String str69;
        long j47;
        long j48;
        java.lang.String str70;
        java.lang.String str71;
        long j49;
        long j50;
        java.lang.String str72;
        java.lang.String[] strArr;
        int i22;
        java.lang.String str73;
        java.lang.String str74;
        long j51;
        java.lang.String str75;
        java.lang.String str76;
        java.lang.String str77;
        long j52;
        long j53;
        java.lang.String str78;
        android.os.BatteryStats batteryStats2 = this;
        if (i != 0) {
            printWriter.println("ERROR: BatteryStats.dump called for which type " + i + " but only STATS_SINCE_CHARGED is supported");
            return;
        }
        long uptimeMillis = android.os.SystemClock.uptimeMillis() * 1000;
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime() * 1000;
        long batteryUptime = batteryStats2.getBatteryUptime(uptimeMillis);
        long computeBatteryUptime = batteryStats2.computeBatteryUptime(uptimeMillis, i);
        long j54 = (elapsedRealtime + 500) / 1000;
        long computeBatteryRealtime = batteryStats2.computeBatteryRealtime(elapsedRealtime, i);
        long computeRealtime = batteryStats2.computeRealtime(elapsedRealtime, i);
        long computeUptime = batteryStats2.computeUptime(uptimeMillis, i);
        long computeBatteryScreenOffUptime = batteryStats2.computeBatteryScreenOffUptime(uptimeMillis, i);
        long computeBatteryScreenOffRealtime = batteryStats2.computeBatteryScreenOffRealtime(elapsedRealtime, i);
        long computeBatteryTimeRemaining = batteryStats2.computeBatteryTimeRemaining(elapsedRealtime);
        long computeChargeTimeRemaining = batteryStats2.computeChargeTimeRemaining(elapsedRealtime);
        long screenDozeTime = batteryStats2.getScreenDozeTime(elapsedRealtime, i);
        java.lang.StringBuilder sb5 = new java.lang.StringBuilder(128);
        android.util.SparseArray<? extends android.os.BatteryStats.Uid> uidStats = getUidStats();
        int size8 = uidStats.size();
        int estimatedBatteryCapacity = getEstimatedBatteryCapacity();
        android.util.SparseArray<? extends android.os.BatteryStats.Uid> sparseArray3 = uidStats;
        int i23 = size8;
        if (estimatedBatteryCapacity > 0) {
            sb5.setLength(0);
            sb5.append(str);
            sb5.append("  Estimated battery capacity: ");
            sb5.append(formatCharge(estimatedBatteryCapacity));
            sb5.append(" mAh");
            printWriter.println(sb5.toString());
        }
        if (getLearnedBatteryCapacity() > 0) {
            sb5.setLength(0);
            sb5.append(str);
            sb5.append("  Last learned battery capacity: ");
            sb5.append(formatCharge(r9 / 1000));
            sb5.append(" mAh");
            printWriter.println(sb5.toString());
        }
        if (getMinLearnedBatteryCapacity() > 0) {
            sb5.setLength(0);
            sb5.append(str);
            sb5.append("  Min learned battery capacity: ");
            sb5.append(formatCharge(r9 / 1000));
            sb5.append(" mAh");
            printWriter.println(sb5.toString());
        }
        if (getMaxLearnedBatteryCapacity() > 0) {
            sb5.setLength(0);
            sb5.append(str);
            sb5.append("  Max learned battery capacity: ");
            sb5.append(formatCharge(r9 / 1000));
            sb5.append(" mAh");
            printWriter.println(sb5.toString());
        }
        sb5.setLength(0);
        sb5.append(str);
        sb5.append("  Time on battery: ");
        long j55 = computeBatteryRealtime / 1000;
        formatTimeMs(sb5, j55);
        sb5.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
        sb5.append(batteryStats2.formatRatioLocked(computeBatteryRealtime, computeRealtime));
        sb5.append(") realtime, ");
        formatTimeMs(sb5, computeBatteryUptime / 1000);
        sb5.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
        sb5.append(batteryStats2.formatRatioLocked(computeBatteryUptime, computeBatteryRealtime));
        sb5.append(") uptime");
        printWriter.println(sb5.toString());
        sb5.setLength(0);
        sb5.append(str);
        sb5.append("  Time on battery screen off: ");
        formatTimeMs(sb5, computeBatteryScreenOffRealtime / 1000);
        sb5.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
        sb5.append(batteryStats2.formatRatioLocked(computeBatteryScreenOffRealtime, computeBatteryRealtime));
        sb5.append(") realtime, ");
        formatTimeMs(sb5, computeBatteryScreenOffUptime / 1000);
        sb5.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
        sb5.append(batteryStats2.formatRatioLocked(computeBatteryScreenOffUptime, computeBatteryRealtime));
        sb5.append(") uptime");
        printWriter.println(sb5.toString());
        sb5.setLength(0);
        sb5.append(str);
        sb5.append("  Time on battery screen doze: ");
        formatTimeMs(sb5, screenDozeTime / 1000);
        sb5.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
        sb5.append(batteryStats2.formatRatioLocked(screenDozeTime, computeBatteryRealtime));
        sb5.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        printWriter.println(sb5.toString());
        sb5.setLength(0);
        sb5.append(str);
        sb5.append("  Total run time: ");
        formatTimeMs(sb5, computeRealtime / 1000);
        sb5.append("realtime, ");
        formatTimeMs(sb5, computeUptime / 1000);
        sb5.append("uptime");
        printWriter.println(sb5.toString());
        if (computeBatteryTimeRemaining >= 0) {
            sb5.setLength(0);
            sb5.append(str);
            sb5.append("  Battery time remaining: ");
            formatTimeMs(sb5, computeBatteryTimeRemaining / 1000);
            printWriter.println(sb5.toString());
        }
        if (computeChargeTimeRemaining >= 0) {
            sb5.setLength(0);
            sb5.append(str);
            sb5.append("  Charge time remaining: ");
            formatTimeMs(sb5, computeChargeTimeRemaining / 1000);
            printWriter.println(sb5.toString());
        }
        long uahDischarge = batteryStats2.getUahDischarge(i);
        if (uahDischarge >= 0) {
            sb5.setLength(0);
            sb5.append(str);
            sb5.append("  Discharge: ");
            sb5.append(formatCharge(uahDischarge / 1000.0d));
            sb5.append(" mAh");
            printWriter.println(sb5.toString());
        }
        long uahDischargeScreenOff = batteryStats2.getUahDischargeScreenOff(i);
        if (uahDischargeScreenOff < 0) {
            str2 = android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        } else {
            sb5.setLength(0);
            sb5.append(str);
            sb5.append("  Screen off discharge: ");
            str2 = android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
            sb5.append(formatCharge(uahDischargeScreenOff / 1000.0d));
            sb5.append(" mAh");
            printWriter.println(sb5.toString());
        }
        long uahDischargeScreenDoze = batteryStats2.getUahDischargeScreenDoze(i);
        if (uahDischargeScreenDoze < 0) {
            str3 = str2;
        } else {
            str3 = str2;
            sb5.setLength(0);
            sb5.append(str);
            sb5.append("  Screen doze discharge: ");
            sb5.append(formatCharge(uahDischargeScreenDoze / 1000.0d));
            sb5.append(" mAh");
            printWriter.println(sb5.toString());
        }
        long j56 = uahDischarge - uahDischargeScreenOff;
        if (j56 >= 0) {
            sb5.setLength(0);
            sb5.append(str);
            sb5.append("  Screen on discharge: ");
            sb5.append(formatCharge(j56 / 1000.0d));
            sb5.append(" mAh");
            printWriter.println(sb5.toString());
        }
        long uahDischargeLightDoze = batteryStats2.getUahDischargeLightDoze(i);
        if (uahDischargeLightDoze >= 0) {
            sb5.setLength(0);
            sb5.append(str);
            sb5.append("  Device light doze discharge: ");
            sb5.append(formatCharge(uahDischargeLightDoze / 1000.0d));
            sb5.append(" mAh");
            printWriter.println(sb5.toString());
        }
        long uahDischargeDeepDoze = batteryStats2.getUahDischargeDeepDoze(i);
        if (uahDischargeDeepDoze >= 0) {
            sb5.setLength(0);
            sb5.append(str);
            sb5.append("  Device deep doze discharge: ");
            sb5.append(formatCharge(uahDischargeDeepDoze / 1000.0d));
            sb5.append(" mAh");
            printWriter.println(sb5.toString());
        }
        printWriter.print("  Start clock time: ");
        printWriter.println(android.text.format.DateFormat.format("yyyy-MM-dd-HH-mm-ss", getStartClockTime()).toString());
        long screenOnTime = batteryStats2.getScreenOnTime(elapsedRealtime, i);
        long interactiveTime = batteryStats2.getInteractiveTime(elapsedRealtime, i);
        long powerSaveModeEnabledTime = batteryStats2.getPowerSaveModeEnabledTime(elapsedRealtime, i);
        long deviceIdleModeTime = batteryStats2.getDeviceIdleModeTime(1, elapsedRealtime, i);
        long deviceIdleModeTime2 = batteryStats2.getDeviceIdleModeTime(2, elapsedRealtime, i);
        long deviceIdlingTime = batteryStats2.getDeviceIdlingTime(1, elapsedRealtime, i);
        long deviceIdlingTime2 = batteryStats2.getDeviceIdlingTime(2, elapsedRealtime, i);
        long phoneOnTime = batteryStats2.getPhoneOnTime(elapsedRealtime, i);
        batteryStats2.getGlobalWifiRunningTime(elapsedRealtime, i);
        batteryStats2.getWifiOnTime(elapsedRealtime, i);
        sb5.setLength(0);
        sb5.append(str);
        sb5.append("  Screen on: ");
        formatTimeMs(sb5, screenOnTime / 1000);
        sb5.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
        long j57 = computeBatteryRealtime;
        sb5.append(batteryStats2.formatRatioLocked(screenOnTime, j57));
        java.lang.String str79 = ") ";
        sb5.append(") ");
        sb5.append(batteryStats2.getScreenOnCount(i));
        sb5.append("x, Interactive: ");
        formatTimeMs(sb5, interactiveTime / 1000);
        sb5.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
        sb5.append(batteryStats2.formatRatioLocked(interactiveTime, j57));
        java.lang.String str80 = str3;
        sb5.append(str80);
        printWriter.println(sb5.toString());
        sb5.setLength(0);
        sb5.append(str);
        sb5.append("  Screen brightnesses:");
        int i24 = 0;
        boolean z12 = false;
        while (true) {
            str4 = str79;
            j = j57;
            if (i24 >= 5) {
                break;
            }
            java.lang.String str81 = str80;
            long screenBrightnessTime = batteryStats2.getScreenBrightnessTime(i24, elapsedRealtime, i);
            if (screenBrightnessTime == 0) {
                str80 = str81;
            } else {
                sb5.append("\n    ");
                sb5.append(str);
                sb5.append(SCREEN_BRIGHTNESS_NAMES[i24]);
                sb5.append(" ");
                formatTimeMs(sb5, screenBrightnessTime / 1000);
                sb5.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
                sb5.append(batteryStats2.formatRatioLocked(screenBrightnessTime, screenOnTime));
                str80 = str81;
                sb5.append(str80);
                z12 = true;
            }
            i24++;
            str79 = str4;
            j57 = j;
        }
        if (!z12) {
            sb5.append(" (no activity)");
        }
        printWriter.println(sb5.toString());
        if (powerSaveModeEnabledTime == 0) {
            j2 = j;
        } else {
            sb5.setLength(0);
            sb5.append(str);
            sb5.append("  Power save mode enabled: ");
            formatTimeMs(sb5, powerSaveModeEnabledTime / 1000);
            sb5.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
            j2 = j;
            sb5.append(batteryStats2.formatRatioLocked(powerSaveModeEnabledTime, j2));
            sb5.append(str80);
            printWriter.println(sb5.toString());
        }
        java.lang.String str82 = "x";
        if (deviceIdlingTime == 0) {
            str5 = str80;
            str6 = str4;
        } else {
            sb5.setLength(0);
            sb5.append(str);
            sb5.append("  Device light idling: ");
            formatTimeMs(sb5, deviceIdlingTime / 1000);
            sb5.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
            batteryStats2 = this;
            str5 = str80;
            sb5.append(batteryStats2.formatRatioLocked(deviceIdlingTime, j2));
            str6 = str4;
            sb5.append(str6);
            sb5.append(batteryStats2.getDeviceIdlingCount(1, i));
            sb5.append("x");
            printWriter.println(sb5.toString());
        }
        if (deviceIdleModeTime == 0) {
            str7 = " ";
            str8 = " (no activity)";
        } else {
            sb5.setLength(0);
            sb5.append(str);
            sb5.append("  Idle mode light time: ");
            formatTimeMs(sb5, deviceIdleModeTime / 1000);
            sb5.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
            sb5.append(batteryStats2.formatRatioLocked(deviceIdleModeTime, j2));
            sb5.append(str6);
            sb5.append(batteryStats2.getDeviceIdleModeCount(1, i));
            sb5.append("x");
            sb5.append(" -- longest ");
            str7 = " ";
            str8 = " (no activity)";
            formatTimeMs(sb5, batteryStats2.getLongestDeviceIdleModeTime(1));
            printWriter.println(sb5.toString());
        }
        if (deviceIdlingTime2 == 0) {
            str9 = str6;
        } else {
            sb5.setLength(0);
            sb5.append(str);
            sb5.append("  Device full idling: ");
            formatTimeMs(sb5, deviceIdlingTime2 / 1000);
            sb5.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
            sb5.append(batteryStats2.formatRatioLocked(deviceIdlingTime2, j2));
            sb5.append(str6);
            str9 = str6;
            sb5.append(batteryStats2.getDeviceIdlingCount(2, i));
            sb5.append("x");
            printWriter.println(sb5.toString());
        }
        if (deviceIdleModeTime2 != 0) {
            sb5.setLength(0);
            sb5.append(str);
            sb5.append("  Idle mode full time: ");
            formatTimeMs(sb5, deviceIdleModeTime2 / 1000);
            sb5.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
            sb5.append(batteryStats2.formatRatioLocked(deviceIdleModeTime2, j2));
            sb5.append(str9);
            sb5.append(batteryStats2.getDeviceIdleModeCount(2, i));
            sb5.append("x");
            sb5.append(" -- longest ");
            formatTimeMs(sb5, batteryStats2.getLongestDeviceIdleModeTime(2));
            printWriter.println(sb5.toString());
        }
        if (phoneOnTime != 0) {
            sb5.setLength(0);
            sb5.append(str);
            sb5.append("  Active phone call: ");
            formatTimeMs(sb5, phoneOnTime / 1000);
            sb5.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
            sb5.append(batteryStats2.formatRatioLocked(phoneOnTime, j2));
            sb5.append(str9);
            sb5.append(batteryStats2.getPhoneOnCount(i));
            sb5.append("x");
        }
        int numConnectivityChange = batteryStats2.getNumConnectivityChange(i);
        if (numConnectivityChange != 0) {
            printWriter.print(str);
            printWriter.print("  Connectivity changes: ");
            printWriter.println(numConnectivityChange);
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        long j58 = 0;
        long j59 = 0;
        int i25 = 0;
        while (true) {
            i3 = i23;
            if (i25 >= i3) {
                break;
            }
            i23 = i3;
            android.util.SparseArray<? extends android.os.BatteryStats.Uid> sparseArray4 = sparseArray3;
            android.os.BatteryStats.Uid valueAt = sparseArray4.valueAt(i25);
            android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Wakelock> wakelockStats = valueAt.getWakelockStats();
            java.lang.String str83 = str82;
            int size9 = wakelockStats.size() - 1;
            while (size9 >= 0) {
                android.util.SparseArray<? extends android.os.BatteryStats.Uid> sparseArray5 = sparseArray4;
                android.os.BatteryStats.Uid.Wakelock valueAt2 = wakelockStats.valueAt(size9);
                long j60 = j2;
                java.lang.String str84 = str9;
                android.os.BatteryStats.Timer wakeTime = valueAt2.getWakeTime(1);
                if (wakeTime != null) {
                    j58 += wakeTime.getTotalTimeLocked(elapsedRealtime, i);
                }
                android.os.BatteryStats.Timer wakeTime2 = valueAt2.getWakeTime(0);
                if (wakeTime2 != null) {
                    long totalTimeLocked = wakeTime2.getTotalTimeLocked(elapsedRealtime, i);
                    if (totalTimeLocked > 0) {
                        if (i2 < 0) {
                            arrayList.add(new android.os.BatteryStats.TimerEntry(wakelockStats.keyAt(size9), valueAt.getUid(), wakeTime2, totalTimeLocked));
                        }
                        j59 += totalTimeLocked;
                    }
                }
                size9--;
                str9 = str84;
                sparseArray4 = sparseArray5;
                j2 = j60;
            }
            i25++;
            str82 = str83;
            sparseArray3 = sparseArray4;
        }
        android.util.SparseArray<? extends android.os.BatteryStats.Uid> sparseArray6 = sparseArray3;
        java.lang.String str85 = str82;
        long j61 = j2;
        java.lang.String str86 = str9;
        long networkActivityBytes = batteryStats2.getNetworkActivityBytes(0, i);
        long networkActivityBytes2 = batteryStats2.getNetworkActivityBytes(1, i);
        long networkActivityBytes3 = batteryStats2.getNetworkActivityBytes(2, i);
        long networkActivityBytes4 = batteryStats2.getNetworkActivityBytes(3, i);
        long networkActivityPackets = batteryStats2.getNetworkActivityPackets(0, i);
        long networkActivityPackets2 = batteryStats2.getNetworkActivityPackets(1, i);
        long networkActivityPackets3 = batteryStats2.getNetworkActivityPackets(2, i);
        long networkActivityPackets4 = batteryStats2.getNetworkActivityPackets(3, i);
        long networkActivityBytes5 = batteryStats2.getNetworkActivityBytes(4, i);
        long networkActivityBytes6 = batteryStats2.getNetworkActivityBytes(5, i);
        if (j58 == 0) {
            str10 = str;
            j3 = networkActivityBytes3;
            j4 = networkActivityPackets4;
            printWriter2 = printWriter;
            j5 = networkActivityBytes4;
            j6 = networkActivityBytes6;
        } else {
            sb5.setLength(0);
            str10 = str;
            j5 = networkActivityBytes4;
            j6 = networkActivityBytes6;
            sb5.append(str10);
            sb5.append("  Total full wakelock time: ");
            j3 = networkActivityBytes3;
            formatTimeMsNoSpace(sb5, (j58 + 500) / 1000);
            printWriter2 = printWriter;
            j4 = networkActivityPackets4;
            printWriter2.println(sb5.toString());
        }
        if (j59 != 0) {
            sb5.setLength(0);
            sb5.append(str10);
            sb5.append("  Total partial wakelock time: ");
            formatTimeMsNoSpace(sb5, (j59 + 500) / 1000);
            printWriter2.println(sb5.toString());
        }
        long wifiMulticastWakelockTime = batteryStats2.getWifiMulticastWakelockTime(elapsedRealtime, i);
        int wifiMulticastWakelockCount = batteryStats2.getWifiMulticastWakelockCount(i);
        if (wifiMulticastWakelockTime != 0) {
            sb5.setLength(0);
            sb5.append(str10);
            sb5.append("  Total WiFi Multicast wakelock Count: ");
            sb5.append(wifiMulticastWakelockCount);
            printWriter2.println(sb5.toString());
            sb5.setLength(0);
            sb5.append(str10);
            sb5.append("  Total WiFi Multicast wakelock time: ");
            formatTimeMsNoSpace(sb5, (wifiMulticastWakelockTime + 500) / 1000);
            printWriter2.println(sb5.toString());
        }
        int displayCount = getDisplayCount();
        if (displayCount <= 1) {
            j7 = networkActivityPackets3;
            str11 = str7;
            str12 = str8;
            str13 = str86;
            j8 = j61;
            j9 = j5;
        } else {
            printWriter2.println("");
            printWriter.print(str);
            sb5.setLength(0);
            sb5.append(str10);
            sb5.append("  MULTI-DISPLAY POWER SUMMARY START");
            printWriter2.println(sb5.toString());
            int i26 = 0;
            while (i26 < displayCount) {
                sb5.setLength(0);
                sb5.append(str10);
                sb5.append("  Display ");
                sb5.append(i26);
                sb5.append(" Statistics:");
                printWriter2.println(sb5.toString());
                long displayScreenOnTime = batteryStats2.getDisplayScreenOnTime(i26, elapsedRealtime);
                int i27 = displayCount;
                sb5.setLength(0);
                sb5.append(str10);
                sb5.append("    Screen on: ");
                long j62 = networkActivityPackets3;
                formatTimeMs(sb5, displayScreenOnTime / 1000);
                sb5.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
                long j63 = j61;
                sb5.append(batteryStats2.formatRatioLocked(displayScreenOnTime, j63));
                java.lang.String str87 = str86;
                sb5.append(str87);
                long j64 = j5;
                printWriter2.println(sb5.toString());
                sb5.setLength(0);
                sb5.append("    Screen brightness levels:");
                int i28 = 0;
                boolean z13 = false;
                while (i28 < 5) {
                    long j65 = displayScreenOnTime;
                    long displayScreenBrightnessTime = batteryStats2.getDisplayScreenBrightnessTime(i26, i28, elapsedRealtime);
                    if (displayScreenBrightnessTime == 0) {
                        j52 = j63;
                        str77 = str7;
                        str78 = str5;
                        j53 = j65;
                    } else {
                        sb5.append("\n      ");
                        sb5.append(str10);
                        sb5.append(SCREEN_BRIGHTNESS_NAMES[i28]);
                        str77 = str7;
                        sb5.append(str77);
                        j52 = j63;
                        formatTimeMs(sb5, displayScreenBrightnessTime / 1000);
                        sb5.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
                        j53 = j65;
                        sb5.append(batteryStats2.formatRatioLocked(displayScreenBrightnessTime, j53));
                        str78 = str5;
                        sb5.append(str78);
                        z13 = true;
                    }
                    i28++;
                    str5 = str78;
                    str7 = str77;
                    displayScreenOnTime = j53;
                    j63 = j52;
                }
                long j66 = j63;
                java.lang.String str88 = str7;
                java.lang.String str89 = str5;
                if (z13) {
                    str76 = str8;
                } else {
                    str76 = str8;
                    sb5.append(str76);
                }
                printWriter2.println(sb5.toString());
                long displayScreenDozeTime = batteryStats2.getDisplayScreenDozeTime(i26, elapsedRealtime);
                sb5.setLength(0);
                sb5.append(str10);
                sb5.append("    Screen Doze: ");
                str5 = str89;
                formatTimeMs(sb5, displayScreenDozeTime / 1000);
                sb5.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
                sb5.append(batteryStats2.formatRatioLocked(displayScreenDozeTime, j66));
                sb5.append(str87);
                printWriter2.println(sb5.toString());
                i26++;
                str86 = str87;
                str7 = str88;
                str8 = str76;
                displayCount = i27;
                j5 = j64;
                networkActivityPackets3 = j62;
                j61 = j66;
            }
            j7 = networkActivityPackets3;
            str11 = str7;
            str12 = str8;
            str13 = str86;
            j8 = j61;
            j9 = j5;
            printWriter.print(str);
            sb5.setLength(0);
            sb5.append(str10);
            sb5.append("  MULTI-DISPLAY POWER SUMMARY END");
            printWriter2.println(sb5.toString());
        }
        printWriter2.println("");
        printWriter.print(str);
        sb5.setLength(0);
        sb5.append(str10);
        sb5.append("  CONNECTIVITY POWER SUMMARY START");
        printWriter2.println(sb5.toString());
        printWriter.print(str);
        sb5.setLength(0);
        sb5.append(str10);
        sb5.append("  Logging duration for connectivity statistics: ");
        formatTimeMs(sb5, j55);
        printWriter2.println(sb5.toString());
        sb5.setLength(0);
        sb5.append(str10);
        sb5.append("  Cellular Statistics:");
        printWriter2.println(sb5.toString());
        printWriter.print(str);
        sb5.setLength(0);
        sb5.append(str10);
        sb5.append("     Cellular kernel active time: ");
        long mobileRadioActiveTime = batteryStats2.getMobileRadioActiveTime(elapsedRealtime, i);
        formatTimeMs(sb5, mobileRadioActiveTime / 1000);
        sb5.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START);
        sb5.append(batteryStats2.formatRatioLocked(mobileRadioActiveTime, j8));
        java.lang.String str90 = str5;
        sb5.append(str90);
        printWriter2.println(sb5.toString());
        android.os.BatteryStats.ControllerActivityCounter modemControllerActivity = getModemControllerActivity();
        java.io.PrintWriter printWriter5 = printWriter2;
        long j67 = batteryUptime;
        java.lang.String str91 = str12;
        java.lang.String str92 = str13;
        android.util.SparseArray<? extends android.os.BatteryStats.Uid> sparseArray7 = sparseArray6;
        int i29 = i;
        java.lang.String str93 = str10;
        java.lang.String str94 = android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START;
        long j68 = j8;
        long j69 = elapsedRealtime;
        java.lang.String str95 = str11;
        printControllerActivity(printWriter, sb5, str, CELLULAR_CONTROLLER_NAME, modemControllerActivity, i);
        printCellularPerRatBreakdown(printWriter, sb5, str93 + "     ", j54);
        printWriter5.print("     Cellular data received: ");
        printWriter5.println(batteryStats2.formatBytesLocked(networkActivityBytes));
        printWriter5.print("     Cellular data sent: ");
        printWriter5.println(batteryStats2.formatBytesLocked(networkActivityBytes2));
        printWriter5.print("     Cellular packets received: ");
        printWriter5.println(networkActivityPackets);
        printWriter5.print("     Cellular packets sent: ");
        printWriter5.println(networkActivityPackets2);
        sb5.setLength(0);
        sb5.append(str93);
        sb5.append("     Cellular Radio Access Technology:");
        int i30 = 0;
        boolean z14 = false;
        while (i30 < NUM_DATA_CONNECTION_TYPES) {
            long j70 = j69;
            long phoneDataConnectionTime = batteryStats2.getPhoneDataConnectionTime(i30, j70, i29);
            if (phoneDataConnectionTime == 0) {
                str74 = str94;
                j69 = j70;
                str75 = str92;
                j51 = j68;
                str73 = str95;
            } else {
                sb5.append("\n       ");
                sb5.append(str93);
                sb5.append(i30 < DATA_CONNECTION_NAMES.length ? DATA_CONNECTION_NAMES[i30] : android.service.timezone.TimeZoneProviderService.TEST_COMMAND_RESULT_ERROR_KEY);
                str73 = str95;
                sb5.append(str73);
                formatTimeMs(sb5, phoneDataConnectionTime / 1000);
                str74 = str94;
                sb5.append(str74);
                j51 = j68;
                sb5.append(batteryStats2.formatRatioLocked(phoneDataConnectionTime, j51));
                str75 = str92;
                sb5.append(str75);
                if (i30 != 13) {
                    j69 = j70;
                } else {
                    long nrNsaTime = batteryStats2.getNrNsaTime(j70);
                    if (nrNsaTime == 0) {
                        j69 = j70;
                    } else {
                        sb5.append("\n         ");
                        sb5.append(str93);
                        sb5.append("nr_nsa");
                        sb5.append(str73);
                        j69 = j70;
                        formatTimeMs(sb5, nrNsaTime / 1000);
                        sb5.append(str74);
                        sb5.append(batteryStats2.formatRatioLocked(nrNsaTime, j51));
                        sb5.append(str75);
                    }
                }
                z14 = true;
            }
            i30++;
            str92 = str75;
            j68 = j51;
            str95 = str73;
            str94 = str74;
        }
        java.lang.String str96 = str94;
        java.lang.String str97 = str92;
        long j71 = j68;
        java.lang.String str98 = str95;
        if (z14) {
            str14 = str91;
        } else {
            str14 = str91;
            sb5.append(str14);
        }
        printWriter5.println(sb5.toString());
        sb5.setLength(0);
        sb5.append(str93);
        sb5.append("     Cellular Rx signal strength (RSRP):");
        java.lang.String[] strArr2 = {"very poor (less than -128dBm): ", "poor (-128dBm to -118dBm): ", "moderate (-118dBm to -108dBm): ", "good (-108dBm to -98dBm): ", "great (greater than -98dBm): "};
        int min = java.lang.Math.min(android.telephony.CellSignalStrength.getNumSignalStrengthLevels(), 5);
        int i31 = 0;
        boolean z15 = false;
        while (i31 < min) {
            java.lang.String str99 = str14;
            long j72 = j69;
            long phoneSignalStrengthTime = batteryStats2.getPhoneSignalStrengthTime(i31, j72, i29);
            if (phoneSignalStrengthTime == 0) {
                strArr = strArr2;
                i22 = min;
                j50 = j72;
                str72 = str;
            } else {
                sb5.append("\n       ");
                j50 = j72;
                str72 = str;
                sb5.append(str72);
                sb5.append(strArr2[i31]);
                sb5.append(str98);
                strArr = strArr2;
                i22 = min;
                formatTimeMs(sb5, phoneSignalStrengthTime / 1000);
                sb5.append(str96);
                sb5.append(batteryStats2.formatRatioLocked(phoneSignalStrengthTime, j71));
                sb5.append(str97);
                z15 = true;
            }
            i31++;
            strArr2 = strArr;
            min = i22;
            str93 = str72;
            str14 = str99;
            j69 = j50;
        }
        java.lang.String str100 = str14;
        java.lang.String str101 = str93;
        long j73 = j69;
        if (z15) {
            str15 = str100;
        } else {
            str15 = str100;
            sb5.append(str15);
        }
        printWriter.println(sb5.toString());
        printWriter.print(str);
        sb5.setLength(0);
        sb5.append(str101);
        sb5.append("  Wifi Statistics:");
        printWriter.println(sb5.toString());
        printWriter.print(str);
        sb5.setLength(0);
        sb5.append(str101);
        sb5.append("     Wifi kernel active time: ");
        long wifiActiveTime = batteryStats2.getWifiActiveTime(j73, i29);
        formatTimeMs(sb5, wifiActiveTime / 1000);
        sb5.append(str96);
        sb5.append(batteryStats2.formatRatioLocked(wifiActiveTime, j71));
        sb5.append(str90);
        printWriter.println(sb5.toString());
        java.lang.String str102 = str98;
        java.lang.String str103 = str96;
        long j74 = j73;
        java.lang.String str104 = str97;
        long j75 = j71;
        printControllerActivity(printWriter, sb5, str, WIFI_CONTROLLER_NAME, getWifiControllerActivity(), i);
        printWriter.print("     Wifi data received: ");
        printWriter.println(batteryStats2.formatBytesLocked(j3));
        printWriter.print("     Wifi data sent: ");
        printWriter.println(batteryStats2.formatBytesLocked(j9));
        printWriter.print("     Wifi packets received: ");
        printWriter.println(j7);
        printWriter.print("     Wifi packets sent: ");
        printWriter.println(j4);
        sb5.setLength(0);
        sb5.append(str101);
        sb5.append("     Wifi states:");
        int i32 = 0;
        boolean z16 = false;
        while (i32 < 8) {
            long wifiStateTime = batteryStats2.getWifiStateTime(i32, j74, i29);
            if (wifiStateTime == 0) {
                str71 = str102;
                j49 = j75;
            } else {
                sb5.append("\n       ");
                sb5.append(WIFI_STATE_NAMES[i32]);
                str71 = str102;
                sb5.append(str71);
                formatTimeMs(sb5, wifiStateTime / 1000);
                sb5.append(str103);
                j49 = j75;
                sb5.append(batteryStats2.formatRatioLocked(wifiStateTime, j49));
                sb5.append(str104);
                z16 = true;
            }
            i32++;
            j75 = j49;
            str102 = str71;
        }
        java.lang.String str105 = str102;
        long j76 = j75;
        if (!z16) {
            sb5.append(str15);
        }
        printWriter.println(sb5.toString());
        sb5.setLength(0);
        sb5.append(str101);
        sb5.append("     Wifi supplicant states:");
        int i33 = 0;
        boolean z17 = false;
        while (i33 < 13) {
            long wifiSupplStateTime = batteryStats2.getWifiSupplStateTime(i33, j74, i29);
            if (wifiSupplStateTime == 0) {
                str70 = str103;
                j48 = j74;
            } else {
                sb5.append("\n       ");
                sb5.append(WIFI_SUPPL_STATE_NAMES[i33]);
                sb5.append(str105);
                j48 = j74;
                formatTimeMs(sb5, wifiSupplStateTime / 1000);
                str70 = str103;
                sb5.append(str70);
                sb5.append(batteryStats2.formatRatioLocked(wifiSupplStateTime, j76));
                sb5.append(str104);
                z17 = true;
            }
            i33++;
            str103 = str70;
            j74 = j48;
            i29 = i;
        }
        java.lang.String str106 = str103;
        long j77 = j74;
        if (!z17) {
            sb5.append(str15);
        }
        printWriter.println(sb5.toString());
        sb5.setLength(0);
        sb5.append(str101);
        sb5.append("     Wifi Rx signal strength (RSSI):");
        java.lang.String[] strArr3 = {"very poor (less than -88.75dBm): ", "poor (-88.75 to -77.5dBm): ", "moderate (-77.5dBm to -66.25dBm): ", "good (-66.25dBm to -55dBm): ", "great (greater than -55dBm): "};
        int min2 = java.lang.Math.min(5, 5);
        int i34 = 0;
        boolean z18 = false;
        while (i34 < min2) {
            boolean z19 = z18;
            long j78 = j76;
            java.lang.String str107 = str15;
            long j79 = j77;
            long wifiSignalStrengthTime = batteryStats2.getWifiSignalStrengthTime(i34, j79, i);
            if (wifiSignalStrengthTime == 0) {
                z18 = z19;
                j47 = j78;
            } else {
                sb5.append("\n    ");
                sb5.append(str101);
                sb5.append("     ");
                sb5.append(strArr3[i34]);
                formatTimeMs(sb5, wifiSignalStrengthTime / 1000);
                sb5.append(str106);
                j47 = j78;
                sb5.append(batteryStats2.formatRatioLocked(wifiSignalStrengthTime, j47));
                sb5.append(str104);
                z18 = true;
            }
            i34++;
            j77 = j79;
            j76 = j47;
            str15 = str107;
        }
        long j80 = j76;
        java.lang.String str108 = str15;
        long j81 = j77;
        if (!z18) {
            sb5.append(str108);
        }
        long j82 = j81;
        printWriter.println(sb5.toString());
        printWriter.print(str);
        sb5.setLength(0);
        sb5.append(str101);
        sb5.append("  GPS Statistics:");
        printWriter.println(sb5.toString());
        sb5.setLength(0);
        sb5.append(str101);
        sb5.append("     GPS signal quality (Top 4 Average CN0):");
        java.lang.String[] strArr4 = {"poor (less than 20 dBHz): ", "good (greater than 20 dBHz): "};
        int min3 = java.lang.Math.min(2, 2);
        int i35 = 0;
        while (i35 < min3) {
            long gpsSignalQualityTime = batteryStats2.getGpsSignalQualityTime(i35, j82, i);
            sb5.append("\n    ");
            str101 = str;
            sb5.append(str101);
            sb5.append("  ");
            sb5.append(strArr4[i35]);
            formatTimeMs(sb5, gpsSignalQualityTime / 1000);
            sb5.append(str106);
            sb5.append(batteryStats2.formatRatioLocked(gpsSignalQualityTime, j80));
            sb5.append(str104);
            i35++;
            min3 = min3;
            strArr4 = strArr4;
            j82 = j82;
        }
        long j83 = j82;
        printWriter.println(sb5.toString());
        long gpsBatteryDrainMaMs = getGpsBatteryDrainMaMs();
        if (gpsBatteryDrainMaMs > 0) {
            printWriter.print(str);
            sb5.setLength(0);
            sb5.append(str101);
            sb5.append("     GPS Battery Drain: ");
            sb5.append(new java.text.DecimalFormat("#.##").format(gpsBatteryDrainMaMs / 3600000.0d));
            sb5.append("mAh");
            printWriter.println(sb5.toString());
        }
        printWriter.print(str);
        sb5.setLength(0);
        sb5.append(str101);
        sb5.append("  CONNECTIVITY POWER SUMMARY END");
        printWriter.println(sb5.toString());
        printWriter.println("");
        printWriter.print(str);
        printWriter.print("  Bluetooth total received: ");
        printWriter.print(batteryStats2.formatBytesLocked(networkActivityBytes5));
        printWriter.print(", sent: ");
        printWriter.println(batteryStats2.formatBytesLocked(j6));
        long bluetoothScanTime = batteryStats2.getBluetoothScanTime(j83, i) / 1000;
        sb5.setLength(0);
        sb5.append(str101);
        sb5.append("  Bluetooth scan time: ");
        formatTimeMs(sb5, bluetoothScanTime);
        printWriter.println(sb5.toString());
        long j84 = j80;
        java.lang.String str109 = str106;
        java.lang.String str110 = str105;
        printControllerActivity(printWriter, sb5, str, "Bluetooth", getBluetoothControllerActivity(), i);
        printWriter.println();
        printWriter.print(str);
        printWriter.println("  Device battery use since last full charge");
        printWriter.print(str);
        printWriter.print("    Amount discharged (lower bound): ");
        printWriter.println(getLowDischargeAmountSinceCharge());
        printWriter.print(str);
        printWriter.print("    Amount discharged (upper bound): ");
        printWriter.println(getHighDischargeAmountSinceCharge());
        printWriter.print(str);
        printWriter.print("    Amount discharged while screen on: ");
        printWriter.println(getDischargeAmountScreenOnSinceCharge());
        printWriter.print(str);
        printWriter.print("    Amount discharged while screen off: ");
        printWriter.println(getDischargeAmountScreenOffSinceCharge());
        printWriter.print(str);
        printWriter.print("    Amount discharged while screen doze: ");
        printWriter.println(getDischargeAmountScreenDozeSinceCharge());
        printWriter.println();
        android.os.BatteryUsageStats batteryUsageStats = batteryStatsDumpHelper.getBatteryUsageStats(batteryStats2, true);
        batteryUsageStats.dump(printWriter, str101);
        java.util.List<android.os.BatteryStats.UidMobileRadioStats> uidMobileRadioStats = batteryStats2.getUidMobileRadioStats(batteryUsageStats.getUidBatteryConsumers());
        java.lang.String str111 = " (";
        java.lang.String str112 = ": ";
        if (uidMobileRadioStats.size() <= 0) {
            str16 = ": ";
            str17 = " (";
            j10 = j84;
            str18 = str90;
            str19 = str85;
        } else {
            printWriter.print(str);
            printWriter.println("  Per-app mobile ms per packet:");
            long j85 = 0;
            int i36 = 0;
            while (i36 < uidMobileRadioStats.size()) {
                android.os.BatteryStats.UidMobileRadioStats uidMobileRadioStats2 = uidMobileRadioStats.get(i36);
                sb5.setLength(0);
                sb5.append(str101);
                sb5.append("    Uid ");
                android.os.UserHandle.formatUid(sb5, uidMobileRadioStats2.uid);
                sb5.append(str112);
                sb5.append(formatValue(uidMobileRadioStats2.millisecondsPerPacket));
                sb5.append(str111);
                sb5.append(uidMobileRadioStats2.rxPackets + uidMobileRadioStats2.txPackets);
                sb5.append(" packets over ");
                formatTimeMsNoSpace(sb5, uidMobileRadioStats2.radioActiveMs);
                sb5.append(str104);
                sb5.append(uidMobileRadioStats2.radioActiveCount);
                sb5.append(str85);
                printWriter.println(sb5);
                j85 += uidMobileRadioStats2.radioActiveMs;
                i36++;
                uidMobileRadioStats = uidMobileRadioStats;
                str111 = str111;
                str112 = str112;
                j84 = j84;
            }
            str16 = str112;
            str17 = str111;
            str19 = str85;
            sb5.setLength(0);
            sb5.append(str101);
            sb5.append("    TOTAL TIME: ");
            formatTimeMs(sb5, j85);
            sb5.append(str109);
            j10 = j84;
            sb5.append(batteryStats2.formatRatioLocked(j85, j10));
            str18 = str90;
            sb5.append(str18);
            printWriter.println(sb5);
            printWriter.println();
        }
        java.util.Comparator<android.os.BatteryStats.TimerEntry> comparator2 = new java.util.Comparator<android.os.BatteryStats.TimerEntry>() { // from class: android.os.BatteryStats.1
            @Override // java.util.Comparator
            public int compare(android.os.BatteryStats.TimerEntry timerEntry, android.os.BatteryStats.TimerEntry timerEntry2) {
                long j86 = timerEntry.mTime;
                long j87 = timerEntry2.mTime;
                if (j86 < j87) {
                    return 1;
                }
                if (j86 > j87) {
                    return -1;
                }
                return 0;
            }
        };
        java.lang.String str113 = " realtime";
        if (i2 >= 0) {
            str20 = str17;
            j11 = j10;
            str21 = " realtime";
            str22 = str109;
            str23 = str104;
            str24 = str18;
            str25 = str19;
            j12 = j83;
            z2 = true;
            i4 = i;
        } else {
            java.util.Map<java.lang.String, ? extends android.os.BatteryStats.Timer> kernelWakelockStats = getKernelWakelockStats();
            if (kernelWakelockStats.size() <= 0) {
                str20 = str17;
                str67 = str16;
                j11 = j10;
                str22 = str109;
                str23 = str104;
                str24 = str18;
                str25 = str19;
                j12 = j83;
                z2 = true;
                i4 = i;
                comparator = comparator2;
                str68 = " realtime";
            } else {
                java.util.ArrayList arrayList2 = new java.util.ArrayList();
                for (java.util.Map.Entry<java.lang.String, ? extends android.os.BatteryStats.Timer> entry : kernelWakelockStats.entrySet()) {
                    long j86 = j10;
                    android.os.BatteryStats.Timer value = entry.getValue();
                    java.lang.String str114 = str104;
                    java.lang.String str115 = str18;
                    long j87 = j83;
                    long computeWakeLock = computeWakeLock(value, j87, i);
                    if (computeWakeLock > 0) {
                        arrayList2.add(new android.os.BatteryStats.TimerEntry(entry.getKey(), 0, value, computeWakeLock));
                    }
                    j83 = j87;
                    str104 = str114;
                    str18 = str115;
                    j10 = j86;
                }
                j11 = j10;
                str23 = str104;
                str24 = str18;
                j12 = j83;
                if (arrayList2.size() <= 0) {
                    str20 = str17;
                    str67 = str16;
                    str22 = str109;
                    str25 = str19;
                    z2 = true;
                    i4 = i;
                    comparator = comparator2;
                    str68 = " realtime";
                } else {
                    java.util.Collections.sort(arrayList2, comparator2);
                    printWriter.print(str);
                    printWriter.println("  All kernel wake locks:");
                    int i37 = 0;
                    while (i37 < arrayList2.size()) {
                        android.os.BatteryStats.TimerEntry timerEntry = (android.os.BatteryStats.TimerEntry) arrayList2.get(i37);
                        sb5.setLength(0);
                        sb5.append(str101);
                        sb5.append("  Kernel Wake lock ");
                        sb5.append(timerEntry.mName);
                        int i38 = i37;
                        java.lang.String str116 = str19;
                        java.lang.String str117 = str16;
                        java.util.ArrayList arrayList3 = arrayList2;
                        java.lang.String str118 = str17;
                        java.lang.String str119 = str113;
                        java.lang.String str120 = str109;
                        java.util.Comparator<android.os.BatteryStats.TimerEntry> comparator3 = comparator2;
                        if (printWakeLock(sb5, timerEntry.mTimer, j12, null, i, ": ").equals(str117)) {
                            str69 = str119;
                        } else {
                            str69 = str119;
                            sb5.append(str69);
                            printWriter.println(sb5.toString());
                        }
                        i37 = i38 + 1;
                        str113 = str69;
                        str16 = str117;
                        comparator2 = comparator3;
                        arrayList2 = arrayList3;
                        str19 = str116;
                        str17 = str118;
                        str109 = str120;
                    }
                    str20 = str17;
                    str67 = str16;
                    str22 = str109;
                    str25 = str19;
                    z2 = true;
                    i4 = i;
                    comparator = comparator2;
                    str68 = str113;
                    printWriter.println();
                }
            }
            if (arrayList.size() <= 0) {
                str16 = str67;
                str21 = str68;
            } else {
                java.util.ArrayList arrayList4 = arrayList;
                java.util.Collections.sort(arrayList4, comparator);
                printWriter.print(str);
                printWriter.println("  All partial wake locks:");
                int i39 = 0;
                while (i39 < arrayList4.size()) {
                    android.os.BatteryStats.TimerEntry timerEntry2 = (android.os.BatteryStats.TimerEntry) arrayList4.get(i39);
                    sb5.setLength(0);
                    sb5.append("  Wake lock ");
                    android.os.UserHandle.formatUid(sb5, timerEntry2.mId);
                    sb5.append(str110);
                    sb5.append(timerEntry2.mName);
                    java.lang.String str121 = str67;
                    java.lang.String str122 = str68;
                    printWakeLock(sb5, timerEntry2.mTimer, j12, null, i, ": ");
                    sb5.append(str122);
                    printWriter.println(sb5.toString());
                    i39++;
                    str68 = str122;
                    arrayList4 = arrayList4;
                    str67 = str121;
                }
                str16 = str67;
                str21 = str68;
                arrayList4.clear();
                printWriter.println();
            }
            java.util.Map<java.lang.String, ? extends android.os.BatteryStats.Timer> wakeupReasonStats = getWakeupReasonStats();
            if (wakeupReasonStats.size() > 0) {
                printWriter.print(str);
                printWriter.println("  All wakeup reasons:");
                java.util.ArrayList arrayList5 = new java.util.ArrayList();
                for (java.util.Map.Entry<java.lang.String, ? extends android.os.BatteryStats.Timer> entry2 : wakeupReasonStats.entrySet()) {
                    arrayList5.add(new android.os.BatteryStats.TimerEntry(entry2.getKey(), 0, entry2.getValue(), r2.getCountLocked(i4)));
                }
                java.util.Collections.sort(arrayList5, comparator);
                int i40 = 0;
                while (i40 < arrayList5.size()) {
                    android.os.BatteryStats.TimerEntry timerEntry3 = (android.os.BatteryStats.TimerEntry) arrayList5.get(i40);
                    sb5.setLength(0);
                    sb5.append(str101);
                    sb5.append("  Wakeup reason ");
                    sb5.append(timerEntry3.mName);
                    printWakeLock(sb5, timerEntry3.mTimer, j12, null, i, ": ");
                    sb5.append(str21);
                    printWriter.println(sb5.toString());
                    i40++;
                    arrayList5 = arrayList5;
                }
                printWriter.println();
            }
        }
        android.util.LongSparseArray<? extends android.os.BatteryStats.Timer> kernelMemoryStats = getKernelMemoryStats();
        if (kernelMemoryStats.size() <= 0) {
            i5 = 0;
        } else {
            printWriter.println("  Memory Stats");
            for (int i41 = 0; i41 < kernelMemoryStats.size(); i41++) {
                sb5.setLength(0);
                sb5.append("  Bandwidth ");
                sb5.append(kernelMemoryStats.keyAt(i41));
                sb5.append(" Time ");
                sb5.append(kernelMemoryStats.valueAt(i41).getTotalTimeLocked(j12, i4));
                printWriter.println(sb5.toString());
            }
            i5 = 0;
            printWriter.println();
        }
        java.util.Map<java.lang.String, ? extends android.os.BatteryStats.Timer> rpmStats = getRpmStats();
        if (rpmStats.size() <= 0) {
            str26 = str22;
            i6 = i5;
            sb = sb5;
            j13 = j12;
            str27 = str110;
            i7 = i3;
            j14 = mobileRadioActiveTime;
            str28 = str23;
            str29 = str25;
            str30 = str24;
        } else {
            printWriter.print(str);
            printWriter.println("  Resource Power Manager Stats");
            if (rpmStats.size() <= 0) {
                str26 = str22;
                i6 = i5;
                sb = sb5;
                j13 = j12;
                str27 = str110;
                i7 = i3;
                j14 = mobileRadioActiveTime;
                str28 = str23;
                str29 = str25;
                str30 = str24;
            } else {
                for (java.util.Map.Entry<java.lang.String, ? extends android.os.BatteryStats.Timer> entry3 : rpmStats.entrySet()) {
                    java.lang.StringBuilder sb6 = sb5;
                    int i42 = i5;
                    long j88 = j12;
                    printTimer(printWriter, sb6, entry3.getValue(), j88, i, str, entry3.getKey());
                    j12 = j88;
                    i5 = i42;
                    sb5 = sb6;
                    str110 = str110;
                    str23 = str23;
                    str22 = str22;
                    z2 = true;
                }
                str26 = str22;
                i6 = i5;
                sb = sb5;
                j13 = j12;
                str27 = str110;
                i7 = i3;
                j14 = mobileRadioActiveTime;
                str28 = str23;
                str29 = str25;
                str30 = str24;
            }
            printWriter.println();
        }
        com.android.internal.os.CpuScalingPolicies cpuScalingPolicies = getCpuScalingPolicies();
        if (cpuScalingPolicies == null) {
            printWriter3 = printWriter;
        } else {
            sb.setLength(i6);
            sb.append("  CPU scaling: ");
            int[] policies = cpuScalingPolicies.getPolicies();
            int length = policies.length;
            for (int i43 = i6; i43 < length; i43++) {
                int i44 = policies[i43];
                sb.append(" policy").append(i44).append(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR);
                int[] frequencies = cpuScalingPolicies.getFrequencies(i44);
                int length2 = frequencies.length;
                for (int i45 = i6; i45 < length2; i45++) {
                    sb.append(' ').append(frequencies[i45]);
                }
            }
            printWriter3 = printWriter;
            printWriter3.println(sb);
            printWriter.println();
        }
        int i46 = i6;
        while (i46 < i7) {
            android.util.SparseArray<? extends android.os.BatteryStats.Uid> sparseArray8 = sparseArray7;
            int keyAt = sparseArray8.keyAt(i46);
            if (i2 < 0 || keyAt == i2 || keyAt == 1000) {
                android.os.BatteryStats.Uid valueAt3 = sparseArray8.valueAt(i46);
                printWriter.print(str);
                printWriter3.print("  ");
                android.os.UserHandle.formatUid(printWriter3, keyAt);
                printWriter3.println(":");
                long networkActivityBytes7 = valueAt3.getNetworkActivityBytes(i6, i);
                java.lang.StringBuilder sb7 = sb;
                long networkActivityBytes8 = valueAt3.getNetworkActivityBytes(1, i);
                int i47 = i7;
                long networkActivityBytes9 = valueAt3.getNetworkActivityBytes(2, i);
                i8 = i46;
                long networkActivityBytes10 = valueAt3.getNetworkActivityBytes(3, i);
                long networkActivityBytes11 = valueAt3.getNetworkActivityBytes(4, i);
                long networkActivityBytes12 = valueAt3.getNetworkActivityBytes(5, i);
                long networkActivityPackets5 = valueAt3.getNetworkActivityPackets(0, i);
                long networkActivityPackets6 = valueAt3.getNetworkActivityPackets(1, i);
                long networkActivityPackets7 = valueAt3.getNetworkActivityPackets(2, i);
                long networkActivityPackets8 = valueAt3.getNetworkActivityPackets(3, i);
                long mobileRadioActiveTime2 = valueAt3.getMobileRadioActiveTime(i);
                int mobileRadioActiveCount = valueAt3.getMobileRadioActiveCount(i);
                long fullWifiLockTime = valueAt3.getFullWifiLockTime(j13, i);
                long wifiScanTime = valueAt3.getWifiScanTime(j13, i);
                int wifiScanCount = valueAt3.getWifiScanCount(i);
                java.lang.String str123 = str21;
                int wifiScanBackgroundCount = valueAt3.getWifiScanBackgroundCount(i);
                long wifiScanActualTime = valueAt3.getWifiScanActualTime(j13);
                long wifiScanBackgroundTime = valueAt3.getWifiScanBackgroundTime(j13);
                long wifiRunningTime = valueAt3.getWifiRunningTime(j13, i);
                long j89 = j13;
                long mobileRadioApWakeupCount = valueAt3.getMobileRadioApWakeupCount(i);
                long wifiRadioApWakeupCount = valueAt3.getWifiRadioApWakeupCount(i);
                if (networkActivityBytes7 <= 0 && networkActivityBytes8 <= 0 && networkActivityPackets5 <= 0 && networkActivityPackets6 <= 0) {
                    batteryStats = this;
                    str31 = str16;
                    j15 = networkActivityPackets6;
                } else {
                    printWriter.print(str);
                    printWriter3.print("    Mobile network: ");
                    batteryStats = this;
                    str31 = str16;
                    printWriter3.print(batteryStats.formatBytesLocked(networkActivityBytes7));
                    printWriter3.print(" received, ");
                    printWriter3.print(batteryStats.formatBytesLocked(networkActivityBytes8));
                    printWriter3.print(" sent (packets ");
                    printWriter3.print(networkActivityPackets5);
                    printWriter3.print(" received, ");
                    j15 = networkActivityPackets6;
                    printWriter3.print(j15);
                    printWriter3.println(" sent)");
                }
                if (mobileRadioActiveTime2 > 0 || mobileRadioActiveCount > 0) {
                    sb2 = sb7;
                    sb2.setLength(0);
                    str32 = str;
                    sb2.append(str32);
                    j16 = wifiRadioApWakeupCount;
                    sb2.append("    Mobile radio active: ");
                    long j90 = mobileRadioActiveTime2 / 1000;
                    formatTimeMs(sb2, j90);
                    i9 = wifiScanBackgroundCount;
                    str33 = str26;
                    sb2.append(str33);
                    i10 = wifiScanCount;
                    uid = valueAt3;
                    j17 = j14;
                    j18 = mobileRadioApWakeupCount;
                    sb2.append(batteryStats.formatRatioLocked(mobileRadioActiveTime2, j17));
                    str34 = str28;
                    sb2.append(str34);
                    sb2.append(mobileRadioActiveCount);
                    str35 = str29;
                    sb2.append(str35);
                    long j91 = networkActivityPackets5 + j15;
                    if (j91 == 0) {
                        j91 = 1;
                    }
                    sb2.append(" @ ");
                    sb2.append(formatCharge(j90 / j91));
                    sb2.append(" mspp");
                    printWriter3.println(sb2.toString());
                } else {
                    sb2 = sb7;
                    str32 = str;
                    j18 = mobileRadioApWakeupCount;
                    i9 = wifiScanBackgroundCount;
                    i10 = wifiScanCount;
                    uid = valueAt3;
                    j16 = wifiRadioApWakeupCount;
                    str34 = str28;
                    str33 = str26;
                    j17 = j14;
                    str35 = str29;
                }
                if (j18 <= 0) {
                    i11 = 0;
                } else {
                    i11 = 0;
                    sb2.setLength(0);
                    sb2.append(str32);
                    sb2.append("    Mobile radio AP wakeups: ");
                    sb2.append(j18);
                    printWriter3.println(sb2.toString());
                }
                java.lang.String str124 = str35;
                java.lang.String str125 = str34;
                java.lang.StringBuilder sb8 = sb2;
                long j92 = j17;
                java.lang.StringBuilder sb9 = sb2;
                i12 = i47;
                int i48 = i11;
                printControllerActivityIfInteresting(printWriter, sb8, str32 + "  ", CELLULAR_CONTROLLER_NAME, uid.getModemControllerActivity(), i);
                if (networkActivityBytes9 > 0 || networkActivityBytes10 > 0 || networkActivityPackets7 > 0 || networkActivityPackets8 > 0) {
                    printWriter.print(str);
                    printWriter3.print("    Wi-Fi network: ");
                    printWriter3.print(batteryStats.formatBytesLocked(networkActivityBytes9));
                    printWriter3.print(" received, ");
                    printWriter3.print(batteryStats.formatBytesLocked(networkActivityBytes10));
                    printWriter3.print(" sent (packets ");
                    printWriter3.print(networkActivityPackets7);
                    printWriter3.print(" received, ");
                    printWriter3.print(networkActivityPackets8);
                    printWriter3.println(" sent)");
                }
                if (fullWifiLockTime == 0 && wifiScanTime == 0 && i10 == 0 && i9 == 0 && wifiScanActualTime == 0 && wifiScanBackgroundTime == 0 && wifiRunningTime == 0) {
                    str36 = str124;
                    j19 = j11;
                    j20 = j89;
                } else {
                    sb9.setLength(i48);
                    sb9.append(str32);
                    sb9.append("    Wifi Running: ");
                    formatTimeMs(sb9, wifiRunningTime / 1000);
                    sb9.append(str33);
                    j19 = j11;
                    sb9.append(batteryStats.formatRatioLocked(wifiRunningTime, j19));
                    sb9.append(")\n");
                    sb9.append(str32);
                    sb9.append("    Full Wifi Lock: ");
                    formatTimeMs(sb9, fullWifiLockTime / 1000);
                    sb9.append(str33);
                    sb9.append(batteryStats.formatRatioLocked(fullWifiLockTime, j19));
                    sb9.append(")\n");
                    sb9.append(str32);
                    sb9.append("    Wifi Scan (blamed): ");
                    formatTimeMs(sb9, wifiScanTime / 1000);
                    sb9.append(str33);
                    sb9.append(batteryStats.formatRatioLocked(wifiScanTime, j19));
                    sb9.append(str125);
                    int i49 = i10;
                    sb9.append(i49);
                    sb9.append("x\n");
                    sb9.append(str32);
                    sb9.append("    Wifi Scan (actual): ");
                    formatTimeMs(sb9, wifiScanActualTime / 1000);
                    sb9.append(str33);
                    j20 = j89;
                    sb9.append(batteryStats.formatRatioLocked(wifiScanActualTime, batteryStats.computeBatteryRealtime(j20, i48)));
                    sb9.append(str125);
                    sb9.append(i49);
                    sb9.append("x\n");
                    sb9.append(str32);
                    sb9.append("    Background Wifi Scan: ");
                    formatTimeMs(sb9, wifiScanBackgroundTime / 1000);
                    sb9.append(str33);
                    sb9.append(batteryStats.formatRatioLocked(wifiScanBackgroundTime, batteryStats.computeBatteryRealtime(j20, i48)));
                    sb9.append(str125);
                    sb9.append(i9);
                    str36 = str124;
                    sb9.append(str36);
                    printWriter3.println(sb9.toString());
                }
                if (j16 > 0) {
                    sb9.setLength(i48);
                    sb9.append(str32);
                    sb9.append("    WiFi AP wakeups: ");
                    sb9.append(j16);
                    printWriter3.println(sb9.toString());
                }
                str37 = str36;
                long j93 = j20;
                printControllerActivityIfInteresting(printWriter, sb9, str32 + "  ", WIFI_CONTROLLER_NAME, uid.getWifiControllerActivity(), i);
                if (networkActivityBytes11 > 0 || networkActivityBytes12 > 0) {
                    printWriter.print(str);
                    printWriter3.print("    Bluetooth network: ");
                    printWriter3.print(batteryStats.formatBytesLocked(networkActivityBytes11));
                    printWriter3.print(" received, ");
                    printWriter3.print(batteryStats.formatBytesLocked(networkActivityBytes12));
                    printWriter3.println(" sent");
                }
                android.os.BatteryStats.Timer bluetoothScanTimer = uid.getBluetoothScanTimer();
                java.lang.String str126 = "\n";
                java.lang.String str127 = " times)";
                if (bluetoothScanTimer != null) {
                    long totalTimeLocked2 = (bluetoothScanTimer.getTotalTimeLocked(j93, i) + 500) / 1000;
                    if (totalTimeLocked2 == 0) {
                        str38 = str33;
                        j21 = j93;
                        j22 = j19;
                        str39 = str125;
                        str40 = str20;
                        str41 = str30;
                        str42 = str;
                    } else {
                        int countLocked = bluetoothScanTimer.getCountLocked(i);
                        j21 = j93;
                        android.os.BatteryStats.Timer bluetoothScanBackgroundTimer = uid.getBluetoothScanBackgroundTimer();
                        int countLocked2 = bluetoothScanBackgroundTimer != null ? bluetoothScanBackgroundTimer.getCountLocked(i) : 0;
                        j22 = j19;
                        long j94 = j54;
                        long totalDurationMsLocked = bluetoothScanTimer.getTotalDurationMsLocked(j94);
                        if (bluetoothScanBackgroundTimer == null) {
                            str38 = str33;
                            timer = bluetoothScanBackgroundTimer;
                            j41 = 0;
                        } else {
                            str38 = str33;
                            timer = bluetoothScanBackgroundTimer;
                            j41 = bluetoothScanBackgroundTimer.getTotalDurationMsLocked(j94);
                        }
                        if (uid.getBluetoothScanResultCounter() != null) {
                            str39 = str125;
                            i21 = uid.getBluetoothScanResultCounter().getCountLocked(i);
                        } else {
                            str39 = str125;
                            i21 = 0;
                        }
                        int countLocked3 = uid.getBluetoothScanResultBgCounter() != null ? uid.getBluetoothScanResultBgCounter().getCountLocked(i) : 0;
                        android.os.BatteryStats.Timer bluetoothUnoptimizedScanTimer = uid.getBluetoothUnoptimizedScanTimer();
                        if (bluetoothUnoptimizedScanTimer == null) {
                            j42 = 0;
                        } else {
                            j42 = bluetoothUnoptimizedScanTimer.getTotalDurationMsLocked(j94);
                        }
                        if (bluetoothUnoptimizedScanTimer == null) {
                            j43 = 0;
                        } else {
                            j43 = bluetoothUnoptimizedScanTimer.getMaxDurationMsLocked(j94);
                        }
                        android.os.BatteryStats.Timer bluetoothUnoptimizedScanBackgroundTimer = uid.getBluetoothUnoptimizedScanBackgroundTimer();
                        if (bluetoothUnoptimizedScanBackgroundTimer == null) {
                            j44 = 0;
                        } else {
                            j44 = bluetoothUnoptimizedScanBackgroundTimer.getTotalDurationMsLocked(j94);
                        }
                        if (bluetoothUnoptimizedScanBackgroundTimer == null) {
                            j54 = j94;
                            j45 = 0;
                        } else {
                            j45 = bluetoothUnoptimizedScanBackgroundTimer.getMaxDurationMsLocked(j94);
                            j54 = j94;
                        }
                        long j95 = j45;
                        sb9.setLength(0);
                        if (totalDurationMsLocked == totalTimeLocked2) {
                            str42 = str;
                            str40 = str20;
                        } else {
                            str42 = str;
                            sb9.append(str42);
                            sb9.append("    Bluetooth Scan (total blamed realtime): ");
                            formatTimeMs(sb9, totalTimeLocked2);
                            str40 = str20;
                            sb9.append(str40);
                            sb9.append(countLocked);
                            sb9.append(" times)");
                            if (bluetoothScanTimer.isRunningLocked()) {
                                sb9.append(" (currently running)");
                            }
                            sb9.append("\n");
                        }
                        sb9.append(str42);
                        sb9.append("    Bluetooth Scan (total actual realtime): ");
                        formatTimeMs(sb9, totalDurationMsLocked);
                        sb9.append(str40);
                        sb9.append(countLocked);
                        sb9.append(" times)");
                        if (bluetoothScanTimer.isRunningLocked()) {
                            sb9.append(" (currently running)");
                        }
                        sb9.append("\n");
                        if (j41 > 0 || countLocked2 > 0) {
                            sb9.append(str42);
                            sb9.append("    Bluetooth Scan (background realtime): ");
                            formatTimeMs(sb9, j41);
                            sb9.append(str40);
                            sb9.append(countLocked2);
                            sb9.append(" times)");
                            if (timer != null && timer.isRunningLocked()) {
                                sb9.append(" (currently running in background)");
                            }
                            sb9.append("\n");
                        }
                        sb9.append(str42);
                        sb9.append("    Bluetooth Scan Results: ");
                        sb9.append(i21);
                        sb9.append(str40);
                        sb9.append(countLocked3);
                        sb9.append(" in background)");
                        long j96 = j42;
                        if (j96 <= 0) {
                            j46 = j44;
                            if (j46 <= 0) {
                                str41 = str30;
                                printWriter3 = printWriter;
                                printWriter3.println(sb9.toString());
                                z3 = true;
                                if (!uid.hasUserActivity()) {
                                    i13 = i;
                                    uid2 = uid;
                                    str43 = str27;
                                } else {
                                    int i50 = 0;
                                    boolean z20 = false;
                                    while (i50 < android.os.BatteryStats.Uid.NUM_USER_ACTIVITY_TYPES) {
                                        android.os.BatteryStats.Uid uid3 = uid;
                                        int userActivityCount = uid3.getUserActivityCount(i50, i);
                                        if (userActivityCount == 0) {
                                            str66 = str27;
                                        } else {
                                            if (!z20) {
                                                sb9.setLength(0);
                                                sb9.append("    User activity: ");
                                                z20 = true;
                                            } else {
                                                sb9.append(", ");
                                            }
                                            sb9.append(userActivityCount);
                                            str66 = str27;
                                            sb9.append(str66);
                                            sb9.append(android.os.BatteryStats.Uid.USER_ACTIVITY_TYPES[i50]);
                                        }
                                        i50++;
                                        str27 = str66;
                                        uid = uid3;
                                    }
                                    i13 = i;
                                    uid2 = uid;
                                    str43 = str27;
                                    if (z20) {
                                        printWriter3.println(sb9.toString());
                                    }
                                }
                                android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Wakelock> wakelockStats2 = uid2.getWakelockStats();
                                str30 = str41;
                                java.lang.String str128 = str43;
                                java.lang.String str129 = str40;
                                long j97 = 0;
                                long j98 = 0;
                                long j99 = 0;
                                long j100 = 0;
                                i14 = 0;
                                boolean z21 = z3;
                                size = wakelockStats2.size() - 1;
                                boolean z22 = z21;
                                while (size >= 0) {
                                    android.os.BatteryStats.Uid.Wakelock valueAt4 = wakelockStats2.valueAt(size);
                                    sb9.setLength(0);
                                    sb9.append(str42);
                                    sb9.append("    Wake lock ");
                                    sb9.append(wakelockStats2.keyAt(size));
                                    android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Wakelock> arrayMap3 = wakelockStats2;
                                    android.os.BatteryStats.Uid uid4 = uid2;
                                    int i51 = i14;
                                    long j101 = j21;
                                    int i52 = size;
                                    int i53 = i13;
                                    java.lang.String str130 = str127;
                                    java.lang.String str131 = str126;
                                    java.lang.String printWakeLock = printWakeLock(sb9, valueAt4.getWakeTime(1), j101, "full", i, ": ");
                                    android.os.BatteryStats.Timer wakeTime3 = valueAt4.getWakeTime(0);
                                    java.lang.String printWakeLock2 = printWakeLock(sb9, wakeTime3, j101, android.app.slice.Slice.HINT_PARTIAL, i, printWakeLock);
                                    long j102 = j21;
                                    printWakeLock(sb9, valueAt4.getWakeTime(18), j102, "draw", i, printWakeLock(sb9, valueAt4.getWakeTime(2), j102, android.content.Context.WINDOW_SERVICE, i, printWakeLock(sb9, wakeTime3 != null ? wakeTime3.getSubTimer() : null, j102, "background partial", i, printWakeLock2)));
                                    sb9.append(str123);
                                    printWriter3.println(sb9.toString());
                                    int i54 = i51 + 1;
                                    long j103 = j21;
                                    j97 += computeWakeLock(valueAt4.getWakeTime(1), j103, i53);
                                    j98 += computeWakeLock(valueAt4.getWakeTime(0), j103, i53);
                                    j99 += computeWakeLock(valueAt4.getWakeTime(2), j103, i53);
                                    j100 += computeWakeLock(valueAt4.getWakeTime(18), j103, i53);
                                    str42 = str;
                                    uid2 = uid4;
                                    i14 = i54;
                                    i13 = i53;
                                    str127 = str130;
                                    str126 = str131;
                                    z22 = true;
                                    size = i52 - 1;
                                    wakelockStats2 = arrayMap3;
                                }
                                java.lang.String str132 = str127;
                                java.lang.String str133 = str126;
                                android.os.BatteryStats.Uid uid5 = uid2;
                                long j104 = j100;
                                long j105 = j99;
                                if (i14 <= 1) {
                                    str44 = ", ";
                                    z4 = z22;
                                    str45 = str123;
                                } else {
                                    if (uid5.getAggregatedPartialWakelockTimer() == null) {
                                        z4 = z22;
                                        str65 = str123;
                                        j38 = 0;
                                        j39 = 0;
                                    } else {
                                        android.os.BatteryStats.Timer aggregatedPartialWakelockTimer = uid5.getAggregatedPartialWakelockTimer();
                                        long j106 = j54;
                                        long totalDurationMsLocked2 = aggregatedPartialWakelockTimer.getTotalDurationMsLocked(j106);
                                        android.os.BatteryStats.Timer subTimer = aggregatedPartialWakelockTimer.getSubTimer();
                                        if (subTimer == null) {
                                            j40 = 0;
                                        } else {
                                            j40 = subTimer.getTotalDurationMsLocked(j106);
                                        }
                                        z4 = z22;
                                        str65 = str123;
                                        j38 = j40;
                                        j39 = totalDurationMsLocked2;
                                        j54 = j106;
                                    }
                                    if (j39 == 0 && j38 == 0 && j97 == 0 && j98 == 0 && j105 == 0) {
                                        str44 = ", ";
                                        str45 = str65;
                                    } else {
                                        sb9.setLength(0);
                                        sb9.append(str);
                                        sb9.append("    TOTAL wake: ");
                                        if (j97 == 0) {
                                            z10 = false;
                                        } else {
                                            formatTimeMs(sb9, j97);
                                            sb9.append("full");
                                            z10 = true;
                                        }
                                        if (j98 == 0) {
                                            str44 = ", ";
                                            z11 = z10;
                                        } else {
                                            if (!z10) {
                                                str44 = ", ";
                                            } else {
                                                str44 = ", ";
                                                sb9.append(str44);
                                            }
                                            formatTimeMs(sb9, j98);
                                            sb9.append("blamed partial");
                                            z11 = true;
                                        }
                                        if (j39 != 0) {
                                            if (z11) {
                                                sb9.append(str44);
                                            }
                                            formatTimeMs(sb9, j39);
                                            sb9.append("actual partial");
                                            z11 = true;
                                        }
                                        if (j38 != 0) {
                                            if (z11) {
                                                sb9.append(str44);
                                            }
                                            formatTimeMs(sb9, j38);
                                            sb9.append("actual background partial");
                                            z11 = true;
                                        }
                                        if (j105 != 0) {
                                            if (z11) {
                                                sb9.append(str44);
                                            }
                                            formatTimeMs(sb9, j105);
                                            sb9.append(android.content.Context.WINDOW_SERVICE);
                                            z11 = true;
                                        }
                                        if (j104 != 0) {
                                            if (z11) {
                                                sb9.append(",");
                                            }
                                            formatTimeMs(sb9, j104);
                                            sb9.append("draw");
                                        }
                                        str45 = str65;
                                        sb9.append(str45);
                                        printWriter3.println(sb9.toString());
                                    }
                                }
                                multicastWakelockStats = uid5.getMulticastWakelockStats();
                                if (multicastWakelockStats == null) {
                                    str46 = str;
                                    i15 = i;
                                    j23 = j21;
                                } else {
                                    i15 = i;
                                    j23 = j21;
                                    long totalTimeLocked3 = multicastWakelockStats.getTotalTimeLocked(j23, i15);
                                    int countLocked4 = multicastWakelockStats.getCountLocked(i15);
                                    if (totalTimeLocked3 <= 0) {
                                        str46 = str;
                                    } else {
                                        sb9.setLength(0);
                                        str46 = str;
                                        sb9.append(str46);
                                        sb9.append("    WiFi Multicast Wakelock");
                                        sb9.append(" count = ");
                                        sb9.append(countLocked4);
                                        sb9.append(" time = ");
                                        formatTimeMsNoSpace(sb9, (totalTimeLocked3 + 500) / 1000);
                                        printWriter3.println(sb9.toString());
                                    }
                                }
                                android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Timer> syncStats = uid5.getSyncStats();
                                size2 = syncStats.size() - 1;
                                boolean z23 = z4;
                                while (size2 >= 0) {
                                    android.os.BatteryStats.Timer valueAt5 = syncStats.valueAt(size2);
                                    long totalTimeLocked4 = (valueAt5.getTotalTimeLocked(j23, i15) + 500) / 1000;
                                    int countLocked5 = valueAt5.getCountLocked(i15);
                                    android.os.BatteryStats.Timer subTimer2 = valueAt5.getSubTimer();
                                    if (subTimer2 != null) {
                                        str62 = str44;
                                        j36 = j54;
                                        j37 = subTimer2.getTotalDurationMsLocked(j36);
                                    } else {
                                        str62 = str44;
                                        j36 = j54;
                                        j37 = -1;
                                    }
                                    long j107 = j36;
                                    long j108 = j37;
                                    int countLocked6 = subTimer2 != null ? subTimer2.getCountLocked(i15) : -1;
                                    sb9.setLength(0);
                                    sb9.append(str46);
                                    sb9.append("    Sync ");
                                    sb9.append(syncStats.keyAt(size2));
                                    java.lang.String str134 = str31;
                                    sb9.append(str134);
                                    if (totalTimeLocked4 != 0) {
                                        formatTimeMs(sb9, totalTimeLocked4);
                                        sb9.append("realtime (");
                                        sb9.append(countLocked5);
                                        str64 = str132;
                                        sb9.append(str64);
                                        if (j108 <= 0) {
                                            str63 = str62;
                                        } else {
                                            str63 = str62;
                                            sb9.append(str63);
                                            formatTimeMs(sb9, j108);
                                            sb9.append("background (");
                                            sb9.append(countLocked6);
                                            sb9.append(str64);
                                        }
                                    } else {
                                        str63 = str62;
                                        str64 = str132;
                                        sb9.append("(not used)");
                                    }
                                    printWriter3.println(sb9.toString());
                                    size2--;
                                    str44 = str63;
                                    str132 = str64;
                                    str31 = str134;
                                    j54 = j107;
                                    z23 = true;
                                }
                                java.lang.String str135 = str44;
                                long j109 = j54;
                                java.lang.String str136 = str31;
                                java.lang.String str137 = str132;
                                android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Timer> jobStats = uid5.getJobStats();
                                size3 = jobStats.size() - 1;
                                boolean z24 = z23;
                                while (size3 >= 0) {
                                    android.os.BatteryStats.Timer valueAt6 = jobStats.valueAt(size3);
                                    long totalTimeLocked5 = (valueAt6.getTotalTimeLocked(j23, i15) + 500) / 1000;
                                    int countLocked7 = valueAt6.getCountLocked(i15);
                                    android.os.BatteryStats.Timer subTimer3 = valueAt6.getSubTimer();
                                    if (subTimer3 != null) {
                                        j34 = j23;
                                        j35 = subTimer3.getTotalDurationMsLocked(j109);
                                    } else {
                                        j34 = j23;
                                        j35 = -1;
                                    }
                                    long j110 = j35;
                                    int countLocked8 = subTimer3 != null ? subTimer3.getCountLocked(i15) : -1;
                                    java.lang.String str138 = str45;
                                    sb9.setLength(0);
                                    sb9.append(str46);
                                    sb9.append("    Job ");
                                    sb9.append(jobStats.keyAt(size3));
                                    sb9.append(str136);
                                    if (totalTimeLocked5 != 0) {
                                        formatTimeMs(sb9, totalTimeLocked5);
                                        sb9.append("realtime (");
                                        sb9.append(countLocked7);
                                        sb9.append(str137);
                                        if (j110 > 0) {
                                            sb9.append(str135);
                                            formatTimeMs(sb9, j110);
                                            sb9.append("background (");
                                            sb9.append(countLocked8);
                                            sb9.append(str137);
                                        }
                                    } else {
                                        sb9.append("(not used)");
                                    }
                                    printWriter3.println(sb9.toString());
                                    size3--;
                                    j23 = j34;
                                    str45 = str138;
                                    z24 = true;
                                }
                                str47 = str45;
                                long j111 = j23;
                                android.util.ArrayMap<java.lang.String, android.util.SparseIntArray> jobCompletionStats = uid5.getJobCompletionStats();
                                size4 = jobCompletionStats.size() - 1;
                                while (size4 >= 0) {
                                    android.util.SparseIntArray valueAt7 = jobCompletionStats.valueAt(size4);
                                    if (valueAt7 == null) {
                                        str60 = str128;
                                        str61 = str38;
                                    } else {
                                        printWriter.print(str);
                                        printWriter3.print("    Job Completions ");
                                        printWriter3.print(jobCompletionStats.keyAt(size4));
                                        printWriter3.print(":");
                                        for (int i55 = 0; i55 < valueAt7.size(); i55++) {
                                            printWriter3.print(str128);
                                            printWriter3.print(android.app.job.JobParameters.getInternalReasonCodeDescription(valueAt7.keyAt(i55)));
                                            printWriter3.print(str38);
                                            printWriter3.print(valueAt7.valueAt(i55));
                                            printWriter3.print("x)");
                                        }
                                        str60 = str128;
                                        str61 = str38;
                                        printWriter.println();
                                    }
                                    size4--;
                                    str128 = str60;
                                    str38 = str61;
                                }
                                java.lang.String str139 = str128;
                                java.lang.String str140 = str38;
                                uid5.getDeferredJobsLineLocked(sb9, i15);
                                if (sb9.length() > 0) {
                                    printWriter3.print("    Jobs deferred on launch ");
                                    printWriter3.println(sb9.toString());
                                }
                                java.lang.String str141 = str139;
                                int i56 = 0;
                                java.lang.String str142 = str46;
                                int i57 = i;
                                long j112 = j109;
                                android.os.BatteryStats.Uid uid6 = uid5;
                                j24 = j22;
                                java.lang.String str143 = str129;
                                j25 = j92;
                                str48 = str140;
                                java.lang.String str144 = str136;
                                java.lang.String str145 = str135;
                                sparseArray = sparseArray8;
                                str49 = str39;
                                java.lang.StringBuilder sb10 = sb9;
                                boolean printTimer = printTimer(printWriter, sb9, uid5.getFlashlightTurnedOnTimer(), j111, i, str, "Flashlight") | z24 | printTimer(printWriter, sb10, uid6.getCameraTurnedOnTimer(), j111, i, str, "Camera") | printTimer(printWriter, sb10, uid6.getVideoTurnedOnTimer(), j111, i, str, "Video") | printTimer(printWriter, sb10, uid6.getAudioTurnedOnTimer(), j111, i, str, "Audio");
                                android.util.SparseArray<? extends android.os.BatteryStats.Uid.Sensor> sensorStats = uid6.getSensorStats();
                                size5 = sensorStats.size();
                                i16 = 0;
                                boolean z25 = printTimer;
                                while (i16 < size5) {
                                    android.os.BatteryStats.Uid.Sensor valueAt8 = sensorStats.valueAt(i16);
                                    sensorStats.keyAt(i16);
                                    java.lang.StringBuilder sb11 = sb10;
                                    sb11.setLength(i56);
                                    sb11.append(str142);
                                    sb11.append("    Sensor ");
                                    int handle = valueAt8.getHandle();
                                    if (handle == -10000) {
                                        sb11.append("GPS");
                                    } else {
                                        sb11.append(handle);
                                    }
                                    java.lang.String str146 = str144;
                                    sb11.append(str146);
                                    android.os.BatteryStats.Timer sensorTime = valueAt8.getSensorTime();
                                    if (sensorTime != null) {
                                        long j113 = j111;
                                        long totalTimeLocked6 = (sensorTime.getTotalTimeLocked(j113, i57) + 500) / 1000;
                                        int countLocked9 = sensorTime.getCountLocked(i57);
                                        sparseArray2 = sensorStats;
                                        android.os.BatteryStats.Timer sensorBackgroundTime = valueAt8.getSensorBackgroundTime();
                                        if (sensorBackgroundTime != null) {
                                            i19 = size5;
                                            i20 = sensorBackgroundTime.getCountLocked(i57);
                                        } else {
                                            i19 = size5;
                                            i20 = 0;
                                        }
                                        str58 = str141;
                                        j32 = j113;
                                        long j114 = j112;
                                        long totalDurationMsLocked3 = sensorTime.getTotalDurationMsLocked(j114);
                                        if (sensorBackgroundTime == null) {
                                            j112 = j114;
                                            j33 = 0;
                                        } else {
                                            j112 = j114;
                                            j33 = sensorBackgroundTime.getTotalDurationMsLocked(j114);
                                        }
                                        if (totalTimeLocked6 != 0) {
                                            if (totalDurationMsLocked3 != totalTimeLocked6) {
                                                formatTimeMs(sb11, totalTimeLocked6);
                                                sb11.append("blamed realtime, ");
                                            }
                                            formatTimeMs(sb11, totalDurationMsLocked3);
                                            sb11.append("realtime (");
                                            sb11.append(countLocked9);
                                            sb11.append(str137);
                                            if (j33 != 0 || i20 > 0) {
                                                str59 = str145;
                                                sb11.append(str59);
                                                formatTimeMs(sb11, j33);
                                                sb11.append("background (");
                                                sb11.append(i20);
                                                sb11.append(str137);
                                            } else {
                                                str59 = str145;
                                            }
                                        } else {
                                            str59 = str145;
                                            sb11.append("(not used)");
                                        }
                                    } else {
                                        str58 = str141;
                                        sparseArray2 = sensorStats;
                                        i19 = size5;
                                        j32 = j111;
                                        str59 = str145;
                                        sb11.append("(not used)");
                                    }
                                    printWriter.println(sb11.toString());
                                    i16++;
                                    size5 = i19;
                                    str142 = str;
                                    str145 = str59;
                                    str144 = str146;
                                    sb10 = sb11;
                                    j111 = j32;
                                    str141 = str58;
                                    i56 = 0;
                                    z25 = true;
                                    sensorStats = sparseArray2;
                                }
                                java.lang.String str147 = str141;
                                java.lang.String str148 = str145;
                                printWriter4 = printWriter;
                                long j115 = j111;
                                java.lang.String str149 = str144;
                                java.lang.StringBuilder sb12 = sb10;
                                boolean printTimer2 = z25 | printTimer(printWriter, sb10, uid6.getVibratorOnTimer(), j115, i, str, "Vibrator") | printTimer(printWriter, sb12, uid6.getForegroundActivityTimer(), j115, i, str, "Foreground activities") | printTimer(printWriter, sb12, uid6.getForegroundServiceTimer(), j115, i, str, "Foreground services");
                                j26 = 0;
                                i17 = 0;
                                while (i17 < 7) {
                                    long processStateTime = uid6.getProcessStateTime(i17, j115, i57);
                                    if (processStateTime <= 0) {
                                        sb4 = sb10;
                                        j31 = j112;
                                    } else {
                                        j26 += processStateTime;
                                        sb4 = sb10;
                                        sb4.setLength(0);
                                        j31 = j112;
                                        sb4.append(str);
                                        sb4.append("    ");
                                        sb4.append(android.os.BatteryStats.Uid.PROCESS_STATE_NAMES[i17]);
                                        sb4.append(" for: ");
                                        formatTimeMs(sb4, (processStateTime + 500) / 1000);
                                        printWriter4.println(sb4.toString());
                                        printTimer2 = true;
                                    }
                                    i17++;
                                    sb10 = sb4;
                                    j112 = j31;
                                }
                                sb3 = sb10;
                                j27 = j112;
                                if (j26 > 0) {
                                    sb3.setLength(0);
                                    sb3.append(str);
                                    sb3.append("    Total running: ");
                                    formatTimeMs(sb3, (j26 + 500) / 1000);
                                    printWriter4.println(sb3.toString());
                                }
                                userCpuTimeUs = uid6.getUserCpuTimeUs(i57);
                                systemCpuTimeUs = uid6.getSystemCpuTimeUs(i57);
                                if (userCpuTimeUs <= 0 || systemCpuTimeUs > 0) {
                                    sb3.setLength(0);
                                    sb3.append(str);
                                    sb3.append("    Total cpu time: u=");
                                    formatTimeMs(sb3, userCpuTimeUs / 1000);
                                    sb3.append("s=");
                                    formatTimeMs(sb3, systemCpuTimeUs / 1000);
                                    printWriter4.println(sb3.toString());
                                }
                                cpuFreqTimes = uid6.getCpuFreqTimes(i57);
                                if (cpuFreqTimes != null) {
                                    sb3.setLength(0);
                                    sb3.append("    Total cpu time per freq:");
                                    for (long j116 : cpuFreqTimes) {
                                        sb3.append(' ').append(j116);
                                    }
                                    printWriter4.println(sb3.toString());
                                }
                                screenOffCpuFreqTimes = uid6.getScreenOffCpuFreqTimes(i57);
                                if (screenOffCpuFreqTimes != null) {
                                    sb3.setLength(0);
                                    sb3.append("    Total screen-off cpu time per freq:");
                                    for (long j117 : screenOffCpuFreqTimes) {
                                        sb3.append(' ').append(j117);
                                    }
                                    printWriter4.println(sb3.toString());
                                }
                                int scalingStepCount = getCpuScalingPolicies().getScalingStepCount();
                                long[] jArr = new long[scalingStepCount];
                                i18 = 0;
                                while (i18 < 7) {
                                    if (!uid6.getCpuFreqTimes(jArr, i18)) {
                                        j30 = j115;
                                        str57 = str147;
                                    } else {
                                        sb3.setLength(0);
                                        sb3.append("    Cpu times per freq at state ").append(android.os.BatteryStats.Uid.PROCESS_STATE_NAMES[i18]).append(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR);
                                        int i58 = 0;
                                        while (i58 < scalingStepCount) {
                                            sb3.append(str147).append(jArr[i58]);
                                            i58++;
                                            j115 = j115;
                                        }
                                        j30 = j115;
                                        str57 = str147;
                                        printWriter4.println(sb3.toString());
                                    }
                                    if (!uid6.getScreenOffCpuFreqTimes(jArr, i18)) {
                                        z9 = printTimer2;
                                    } else {
                                        sb3.setLength(0);
                                        sb3.append("   Screen-off cpu times per freq at state ").append(android.os.BatteryStats.Uid.PROCESS_STATE_NAMES[i18]).append(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR);
                                        int i59 = 0;
                                        while (i59 < scalingStepCount) {
                                            sb3.append(str57).append(jArr[i59]);
                                            i59++;
                                            printTimer2 = printTimer2;
                                        }
                                        z9 = printTimer2;
                                        printWriter4.println(sb3.toString());
                                    }
                                    i18++;
                                    printTimer2 = z9;
                                    str147 = str57;
                                    j115 = j30;
                                }
                                j28 = j115;
                                java.lang.String str150 = str147;
                                android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Proc> processStats = uid6.getProcessStats();
                                size6 = processStats.size() - 1;
                                while (size6 >= 0) {
                                    android.os.BatteryStats.Uid.Proc valueAt9 = processStats.valueAt(size6);
                                    long userTime = valueAt9.getUserTime(i57);
                                    boolean z26 = printTimer2;
                                    long systemTime = valueAt9.getSystemTime(i57);
                                    java.lang.String str151 = str150;
                                    long foregroundTime = valueAt9.getForegroundTime(i57);
                                    java.lang.String str152 = str149;
                                    int starts = valueAt9.getStarts(i57);
                                    android.os.BatteryStats.Uid uid7 = uid6;
                                    int numCrashes = valueAt9.getNumCrashes(i57);
                                    int numAnrs = valueAt9.getNumAnrs(i57);
                                    int countExcessivePowers = i57 == 0 ? valueAt9.countExcessivePowers() : 0;
                                    if (userTime == 0 && systemTime == 0 && foregroundTime == 0 && starts == 0 && countExcessivePowers == 0 && numCrashes == 0 && numAnrs == 0) {
                                        z8 = z26;
                                        printWriter4 = printWriter;
                                        str53 = str148;
                                        arrayMap = processStats;
                                        str52 = str133;
                                        str54 = str143;
                                    } else {
                                        android.os.BatteryStats.Uid.Proc proc = valueAt9;
                                        sb3.setLength(0);
                                        sb3.append(str);
                                        sb3.append("    Proc ");
                                        sb3.append(processStats.keyAt(size6));
                                        sb3.append(":\n");
                                        sb3.append(str);
                                        sb3.append("      CPU: ");
                                        formatTimeMs(sb3, userTime);
                                        sb3.append("usr + ");
                                        formatTimeMs(sb3, systemTime);
                                        sb3.append("krn ; ");
                                        formatTimeMs(sb3, foregroundTime);
                                        sb3.append(FOREGROUND_ACTIVITY_DATA);
                                        if (starts == 0 && numCrashes == 0 && numAnrs == 0) {
                                            str52 = str133;
                                        } else {
                                            str52 = str133;
                                            sb3.append(str52);
                                            sb3.append(str);
                                            sb3.append("      ");
                                            if (starts == 0) {
                                                z6 = false;
                                            } else {
                                                sb3.append(starts);
                                                sb3.append(" starts");
                                                z6 = true;
                                            }
                                            if (numCrashes == 0) {
                                                z7 = z6;
                                            } else {
                                                if (z6) {
                                                    sb3.append(str148);
                                                }
                                                sb3.append(numCrashes);
                                                sb3.append(" crashes");
                                                z7 = true;
                                            }
                                            if (numAnrs != 0) {
                                                if (z7) {
                                                    sb3.append(str148);
                                                }
                                                sb3.append(numAnrs);
                                                sb3.append(" anrs");
                                            }
                                        }
                                        printWriter4 = printWriter;
                                        printWriter4.println(sb3.toString());
                                        int i60 = 0;
                                        while (i60 < countExcessivePowers) {
                                            android.os.BatteryStats.Uid.Proc proc2 = proc;
                                            android.os.BatteryStats.Uid.Proc.ExcessivePower excessivePower = proc2.getExcessivePower(i60);
                                            if (excessivePower == null) {
                                                str55 = str148;
                                                arrayMap2 = processStats;
                                                str56 = str143;
                                            } else {
                                                printWriter.print(str);
                                                printWriter4.print("      * Killed for ");
                                                if (excessivePower.type == 2) {
                                                    printWriter4.print(CPU_DATA);
                                                } else {
                                                    printWriter4.print("unknown");
                                                }
                                                printWriter4.print(" use: ");
                                                android.util.TimeUtils.formatDuration(excessivePower.usedTime, printWriter4);
                                                printWriter4.print(" over ");
                                                android.util.TimeUtils.formatDuration(excessivePower.overTime, printWriter4);
                                                if (excessivePower.overTime == 0) {
                                                    str55 = str148;
                                                    arrayMap2 = processStats;
                                                    str56 = str143;
                                                } else {
                                                    str56 = str143;
                                                    printWriter4.print(str56);
                                                    str55 = str148;
                                                    arrayMap2 = processStats;
                                                    printWriter4.print((excessivePower.usedTime * 100) / excessivePower.overTime);
                                                    printWriter4.println("%)");
                                                }
                                            }
                                            i60++;
                                            proc = proc2;
                                            str143 = str56;
                                            processStats = arrayMap2;
                                            str148 = str55;
                                        }
                                        str53 = str148;
                                        arrayMap = processStats;
                                        str54 = str143;
                                        z8 = true;
                                    }
                                    size6--;
                                    uid6 = uid7;
                                    str149 = str152;
                                    printTimer2 = z8;
                                    str133 = str52;
                                    str143 = str54;
                                    processStats = arrayMap;
                                    str150 = str151;
                                    str148 = str53;
                                    i57 = i;
                                }
                                java.lang.String str153 = str149;
                                str27 = str150;
                                str50 = str143;
                                android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Pkg> packageStats = uid6.getPackageStats();
                                size7 = packageStats.size() - 1;
                                z5 = printTimer2;
                                while (size7 >= 0) {
                                    printWriter.print(str);
                                    printWriter4.print("    Apk ");
                                    printWriter4.print(packageStats.keyAt(size7));
                                    printWriter4.println(":");
                                    android.os.BatteryStats.Uid.Pkg valueAt10 = packageStats.valueAt(size7);
                                    android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Counter> wakeupAlarmStats = valueAt10.getWakeupAlarmStats();
                                    int size10 = wakeupAlarmStats.size() - 1;
                                    boolean z27 = false;
                                    while (size10 >= 0) {
                                        printWriter.print(str);
                                        printWriter4.print("      Wakeup alarm ");
                                        printWriter4.print(wakeupAlarmStats.keyAt(size10));
                                        printWriter4.print(str153);
                                        printWriter4.print(wakeupAlarmStats.valueAt(size10).getCountLocked(i));
                                        printWriter4.println(" times");
                                        size10--;
                                        z27 = true;
                                    }
                                    java.lang.String str154 = str153;
                                    android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Pkg.Serv> serviceStats = valueAt10.getServiceStats();
                                    int size11 = serviceStats.size() - 1;
                                    while (size11 >= 0) {
                                        android.os.BatteryStats.Uid.Pkg.Serv valueAt11 = serviceStats.valueAt(size11);
                                        long j118 = j67;
                                        long startTime = valueAt11.getStartTime(j118, i);
                                        int starts2 = valueAt11.getStarts(i);
                                        int launches = valueAt11.getLaunches(i);
                                        if (startTime != 0 || starts2 != 0 || launches != 0) {
                                            sb3.setLength(0);
                                            sb3.append(str);
                                            sb3.append("      Service ");
                                            sb3.append(serviceStats.keyAt(size11));
                                            sb3.append(":\n");
                                            sb3.append(str);
                                            sb3.append("        Created for: ");
                                            formatTimeMs(sb3, startTime / 1000);
                                            sb3.append("uptime\n");
                                            sb3.append(str);
                                            sb3.append("        Starts: ");
                                            sb3.append(starts2);
                                            sb3.append(", launches: ");
                                            sb3.append(launches);
                                            printWriter4.println(sb3.toString());
                                            z27 = true;
                                        }
                                        size11--;
                                        j67 = j118;
                                    }
                                    long j119 = j67;
                                    if (!z27) {
                                        printWriter.print(str);
                                        printWriter4.println("      (nothing executed)");
                                    }
                                    size7--;
                                    str153 = str154;
                                    j67 = j119;
                                    z5 = true;
                                }
                                str51 = str153;
                                j29 = j67;
                                i6 = 0;
                                if (!z5) {
                                    printWriter.print(str);
                                    printWriter4.println("    (nothing executed)");
                                }
                            }
                        } else {
                            j46 = j44;
                        }
                        sb9.append("\n");
                        sb9.append(str42);
                        sb9.append("    Unoptimized Bluetooth Scan (realtime): ");
                        formatTimeMs(sb9, j96);
                        sb9.append(" (max ");
                        formatTimeMs(sb9, j43);
                        str41 = str30;
                        sb9.append(str41);
                        if (bluetoothUnoptimizedScanTimer != null && bluetoothUnoptimizedScanTimer.isRunningLocked()) {
                            sb9.append(" (currently running unoptimized)");
                        }
                        if (bluetoothUnoptimizedScanBackgroundTimer != null && j46 > 0) {
                            sb9.append("\n");
                            sb9.append(str42);
                            sb9.append("    Unoptimized Bluetooth Scan (background realtime): ");
                            formatTimeMs(sb9, j46);
                            sb9.append(" (max ");
                            formatTimeMs(sb9, j95);
                            sb9.append(str41);
                            if (bluetoothUnoptimizedScanBackgroundTimer.isRunningLocked()) {
                                sb9.append(" (currently running unoptimized in background)");
                            }
                        }
                        printWriter3 = printWriter;
                        printWriter3.println(sb9.toString());
                        z3 = true;
                        if (!uid.hasUserActivity()) {
                        }
                        android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Wakelock> wakelockStats22 = uid2.getWakelockStats();
                        str30 = str41;
                        java.lang.String str1282 = str43;
                        java.lang.String str1292 = str40;
                        long j972 = 0;
                        long j982 = 0;
                        long j992 = 0;
                        long j1002 = 0;
                        i14 = 0;
                        boolean z212 = z3;
                        size = wakelockStats22.size() - 1;
                        boolean z222 = z212;
                        while (size >= 0) {
                        }
                        java.lang.String str1322 = str127;
                        java.lang.String str1332 = str126;
                        android.os.BatteryStats.Uid uid52 = uid2;
                        long j1042 = j1002;
                        long j1052 = j992;
                        if (i14 <= 1) {
                        }
                        multicastWakelockStats = uid52.getMulticastWakelockStats();
                        if (multicastWakelockStats == null) {
                        }
                        android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Timer> syncStats2 = uid52.getSyncStats();
                        size2 = syncStats2.size() - 1;
                        boolean z232 = z4;
                        while (size2 >= 0) {
                        }
                        java.lang.String str1352 = str44;
                        long j1092 = j54;
                        java.lang.String str1362 = str31;
                        java.lang.String str1372 = str1322;
                        android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Timer> jobStats2 = uid52.getJobStats();
                        size3 = jobStats2.size() - 1;
                        boolean z242 = z232;
                        while (size3 >= 0) {
                        }
                        str47 = str45;
                        long j1112 = j23;
                        android.util.ArrayMap<java.lang.String, android.util.SparseIntArray> jobCompletionStats2 = uid52.getJobCompletionStats();
                        size4 = jobCompletionStats2.size() - 1;
                        while (size4 >= 0) {
                        }
                        java.lang.String str1392 = str1282;
                        java.lang.String str1402 = str38;
                        uid52.getDeferredJobsLineLocked(sb9, i15);
                        if (sb9.length() > 0) {
                        }
                        java.lang.String str1412 = str1392;
                        int i562 = 0;
                        java.lang.String str1422 = str46;
                        int i572 = i;
                        long j1122 = j1092;
                        android.os.BatteryStats.Uid uid62 = uid52;
                        j24 = j22;
                        java.lang.String str1432 = str1292;
                        j25 = j92;
                        str48 = str1402;
                        java.lang.String str1442 = str1362;
                        java.lang.String str1452 = str1352;
                        sparseArray = sparseArray8;
                        str49 = str39;
                        java.lang.StringBuilder sb102 = sb9;
                        boolean printTimer3 = printTimer(printWriter, sb9, uid52.getFlashlightTurnedOnTimer(), j1112, i, str, "Flashlight") | z242 | printTimer(printWriter, sb102, uid62.getCameraTurnedOnTimer(), j1112, i, str, "Camera") | printTimer(printWriter, sb102, uid62.getVideoTurnedOnTimer(), j1112, i, str, "Video") | printTimer(printWriter, sb102, uid62.getAudioTurnedOnTimer(), j1112, i, str, "Audio");
                        android.util.SparseArray<? extends android.os.BatteryStats.Uid.Sensor> sensorStats2 = uid62.getSensorStats();
                        size5 = sensorStats2.size();
                        i16 = 0;
                        boolean z252 = printTimer3;
                        while (i16 < size5) {
                        }
                        java.lang.String str1472 = str1412;
                        java.lang.String str1482 = str1452;
                        printWriter4 = printWriter;
                        long j1152 = j1112;
                        java.lang.String str1492 = str1442;
                        java.lang.StringBuilder sb122 = sb102;
                        boolean printTimer22 = z252 | printTimer(printWriter, sb102, uid62.getVibratorOnTimer(), j1152, i, str, "Vibrator") | printTimer(printWriter, sb122, uid62.getForegroundActivityTimer(), j1152, i, str, "Foreground activities") | printTimer(printWriter, sb122, uid62.getForegroundServiceTimer(), j1152, i, str, "Foreground services");
                        j26 = 0;
                        i17 = 0;
                        while (i17 < 7) {
                        }
                        sb3 = sb102;
                        j27 = j1122;
                        if (j26 > 0) {
                        }
                        userCpuTimeUs = uid62.getUserCpuTimeUs(i572);
                        systemCpuTimeUs = uid62.getSystemCpuTimeUs(i572);
                        if (userCpuTimeUs <= 0) {
                        }
                        sb3.setLength(0);
                        sb3.append(str);
                        sb3.append("    Total cpu time: u=");
                        formatTimeMs(sb3, userCpuTimeUs / 1000);
                        sb3.append("s=");
                        formatTimeMs(sb3, systemCpuTimeUs / 1000);
                        printWriter4.println(sb3.toString());
                        cpuFreqTimes = uid62.getCpuFreqTimes(i572);
                        if (cpuFreqTimes != null) {
                        }
                        screenOffCpuFreqTimes = uid62.getScreenOffCpuFreqTimes(i572);
                        if (screenOffCpuFreqTimes != null) {
                        }
                        int scalingStepCount2 = getCpuScalingPolicies().getScalingStepCount();
                        long[] jArr2 = new long[scalingStepCount2];
                        i18 = 0;
                        while (i18 < 7) {
                        }
                        j28 = j1152;
                        java.lang.String str1502 = str1472;
                        android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Proc> processStats2 = uid62.getProcessStats();
                        size6 = processStats2.size() - 1;
                        while (size6 >= 0) {
                        }
                        java.lang.String str1532 = str1492;
                        str27 = str1502;
                        str50 = str1432;
                        android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Pkg> packageStats2 = uid62.getPackageStats();
                        size7 = packageStats2.size() - 1;
                        z5 = printTimer22;
                        while (size7 >= 0) {
                        }
                        str51 = str1532;
                        j29 = j67;
                        i6 = 0;
                        if (!z5) {
                        }
                    }
                } else {
                    str38 = str33;
                    j21 = j93;
                    j22 = j19;
                    str39 = str125;
                    str40 = str20;
                    str41 = str30;
                    str42 = str;
                }
                z3 = false;
                if (!uid.hasUserActivity()) {
                }
                android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Wakelock> wakelockStats222 = uid2.getWakelockStats();
                str30 = str41;
                java.lang.String str12822 = str43;
                java.lang.String str12922 = str40;
                long j9722 = 0;
                long j9822 = 0;
                long j9922 = 0;
                long j10022 = 0;
                i14 = 0;
                boolean z2122 = z3;
                size = wakelockStats222.size() - 1;
                boolean z2222 = z2122;
                while (size >= 0) {
                }
                java.lang.String str13222 = str127;
                java.lang.String str13322 = str126;
                android.os.BatteryStats.Uid uid522 = uid2;
                long j10422 = j10022;
                long j10522 = j9922;
                if (i14 <= 1) {
                }
                multicastWakelockStats = uid522.getMulticastWakelockStats();
                if (multicastWakelockStats == null) {
                }
                android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Timer> syncStats22 = uid522.getSyncStats();
                size2 = syncStats22.size() - 1;
                boolean z2322 = z4;
                while (size2 >= 0) {
                }
                java.lang.String str13522 = str44;
                long j10922 = j54;
                java.lang.String str13622 = str31;
                java.lang.String str13722 = str13222;
                android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Timer> jobStats22 = uid522.getJobStats();
                size3 = jobStats22.size() - 1;
                boolean z2422 = z2322;
                while (size3 >= 0) {
                }
                str47 = str45;
                long j11122 = j23;
                android.util.ArrayMap<java.lang.String, android.util.SparseIntArray> jobCompletionStats22 = uid522.getJobCompletionStats();
                size4 = jobCompletionStats22.size() - 1;
                while (size4 >= 0) {
                }
                java.lang.String str13922 = str12822;
                java.lang.String str14022 = str38;
                uid522.getDeferredJobsLineLocked(sb9, i15);
                if (sb9.length() > 0) {
                }
                java.lang.String str14122 = str13922;
                int i5622 = 0;
                java.lang.String str14222 = str46;
                int i5722 = i;
                long j11222 = j10922;
                android.os.BatteryStats.Uid uid622 = uid522;
                j24 = j22;
                java.lang.String str14322 = str12922;
                j25 = j92;
                str48 = str14022;
                java.lang.String str14422 = str13622;
                java.lang.String str14522 = str13522;
                sparseArray = sparseArray8;
                str49 = str39;
                java.lang.StringBuilder sb1022 = sb9;
                boolean printTimer32 = printTimer(printWriter, sb9, uid522.getFlashlightTurnedOnTimer(), j11122, i, str, "Flashlight") | z2422 | printTimer(printWriter, sb1022, uid622.getCameraTurnedOnTimer(), j11122, i, str, "Camera") | printTimer(printWriter, sb1022, uid622.getVideoTurnedOnTimer(), j11122, i, str, "Video") | printTimer(printWriter, sb1022, uid622.getAudioTurnedOnTimer(), j11122, i, str, "Audio");
                android.util.SparseArray<? extends android.os.BatteryStats.Uid.Sensor> sensorStats22 = uid622.getSensorStats();
                size5 = sensorStats22.size();
                i16 = 0;
                boolean z2522 = printTimer32;
                while (i16 < size5) {
                }
                java.lang.String str14722 = str14122;
                java.lang.String str14822 = str14522;
                printWriter4 = printWriter;
                long j11522 = j11122;
                java.lang.String str14922 = str14422;
                java.lang.StringBuilder sb1222 = sb1022;
                boolean printTimer222 = z2522 | printTimer(printWriter, sb1022, uid622.getVibratorOnTimer(), j11522, i, str, "Vibrator") | printTimer(printWriter, sb1222, uid622.getForegroundActivityTimer(), j11522, i, str, "Foreground activities") | printTimer(printWriter, sb1222, uid622.getForegroundServiceTimer(), j11522, i, str, "Foreground services");
                j26 = 0;
                i17 = 0;
                while (i17 < 7) {
                }
                sb3 = sb1022;
                j27 = j11222;
                if (j26 > 0) {
                }
                userCpuTimeUs = uid622.getUserCpuTimeUs(i5722);
                systemCpuTimeUs = uid622.getSystemCpuTimeUs(i5722);
                if (userCpuTimeUs <= 0) {
                }
                sb3.setLength(0);
                sb3.append(str);
                sb3.append("    Total cpu time: u=");
                formatTimeMs(sb3, userCpuTimeUs / 1000);
                sb3.append("s=");
                formatTimeMs(sb3, systemCpuTimeUs / 1000);
                printWriter4.println(sb3.toString());
                cpuFreqTimes = uid622.getCpuFreqTimes(i5722);
                if (cpuFreqTimes != null) {
                }
                screenOffCpuFreqTimes = uid622.getScreenOffCpuFreqTimes(i5722);
                if (screenOffCpuFreqTimes != null) {
                }
                int scalingStepCount22 = getCpuScalingPolicies().getScalingStepCount();
                long[] jArr22 = new long[scalingStepCount22];
                i18 = 0;
                while (i18 < 7) {
                }
                j28 = j11522;
                java.lang.String str15022 = str14722;
                android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Proc> processStats22 = uid622.getProcessStats();
                size6 = processStats22.size() - 1;
                while (size6 >= 0) {
                }
                java.lang.String str15322 = str14922;
                str27 = str15022;
                str50 = str14322;
                android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Pkg> packageStats22 = uid622.getPackageStats();
                size7 = packageStats22.size() - 1;
                z5 = printTimer222;
                while (size7 >= 0) {
                }
                str51 = str15322;
                j29 = j67;
                i6 = 0;
                if (!z5) {
                }
            } else {
                j28 = j13;
                i12 = i7;
                sb3 = sb;
                str47 = str21;
                sparseArray = sparseArray8;
                i8 = i46;
                printWriter4 = printWriter3;
                j27 = j54;
                j29 = j67;
                j24 = j11;
                str50 = str20;
                str49 = str28;
                str48 = str26;
                j25 = j14;
                str37 = str29;
                str51 = str16;
            }
            sb = sb3;
            str16 = str51;
            str20 = str50;
            j67 = j29;
            j11 = j24;
            str26 = str48;
            sparseArray7 = sparseArray;
            str29 = str37;
            j14 = j25;
            str28 = str49;
            i7 = i12;
            j54 = j27;
            j13 = j28;
            str21 = str47;
            i46 = i8 + 1;
            printWriter3 = printWriter4;
        }
    }

    static void printBitDescriptions(java.lang.StringBuilder sb, int i, int i2, android.os.BatteryStats.HistoryTag historyTag, android.os.BatteryStats.BitDescription[] bitDescriptionArr, boolean z) {
        int i3 = i ^ i2;
        if (i3 == 0) {
            return;
        }
        boolean z2 = false;
        for (android.os.BatteryStats.BitDescription bitDescription : bitDescriptionArr) {
            if ((bitDescription.mask & i3) != 0) {
                sb.append(z ? " " : ",");
                if (bitDescription.shift < 0) {
                    sb.append((bitDescription.mask & i2) != 0 ? "+" : com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                    sb.append(z ? bitDescription.name : bitDescription.shortName);
                    if (bitDescription.mask == 1073741824 && historyTag != null) {
                        sb.append("=");
                        if (z || historyTag.poolIdx == -1) {
                            android.os.UserHandle.formatUid(sb, historyTag.uid);
                            sb.append(":\"");
                            sb.append(historyTag.string.replace("\"", "\"\""));
                            sb.append("\"");
                        } else {
                            sb.append(historyTag.poolIdx);
                        }
                        z2 = true;
                    }
                } else {
                    sb.append(z ? bitDescription.name : bitDescription.shortName);
                    sb.append("=");
                    int i4 = (bitDescription.mask & i2) >> bitDescription.shift;
                    if (bitDescription.values != null && i4 >= 0 && i4 < bitDescription.values.length) {
                        sb.append(z ? bitDescription.values[i4] : bitDescription.shortValues[i4]);
                    } else {
                        sb.append(i4);
                    }
                }
            }
        }
        if (!z2 && historyTag != null) {
            sb.append(z ? " wake_lock=" : ",w=");
            if (z || historyTag.poolIdx == -1) {
                android.os.UserHandle.formatUid(sb, historyTag.uid);
                sb.append(":\"");
                sb.append(historyTag.string);
                sb.append("\"");
                return;
            }
            sb.append(historyTag.poolIdx);
        }
    }

    public void prepareForDumpLocked() {
    }

    public static class HistoryPrinter {
        int oldState = 0;
        int oldState2 = 0;
        int oldLevel = -1;
        int oldStatus = -1;
        int oldHealth = -1;
        int oldPlug = -1;
        int oldTemp = -1;
        int oldVolt = -1;
        int oldChargeMAh = -1;
        double oldModemRailChargeMah = -1.0d;
        double oldWifiRailChargeMah = -1.0d;
        long lastTime = -1;

        void reset() {
            this.oldState2 = 0;
            this.oldState = 0;
            this.oldLevel = -1;
            this.oldStatus = -1;
            this.oldHealth = -1;
            this.oldPlug = -1;
            this.oldTemp = -1;
            this.oldVolt = -1;
            this.oldChargeMAh = -1;
            this.oldModemRailChargeMah = -1.0d;
            this.oldWifiRailChargeMah = -1.0d;
        }

        public void printNextItem(java.io.PrintWriter printWriter, android.os.BatteryStats.HistoryItem historyItem, long j, boolean z, boolean z2) {
            printWriter.print(printNextItem(historyItem, j, z, z2));
        }

        public void printNextItem(android.util.proto.ProtoOutputStream protoOutputStream, android.os.BatteryStats.HistoryItem historyItem, long j, boolean z) {
            for (java.lang.String str : printNextItem(historyItem, j, true, z).split("\n")) {
                protoOutputStream.write(2237677961222L, str);
            }
        }

        private java.lang.String printNextItem(android.os.BatteryStats.HistoryItem historyItem, long j, boolean z, boolean z2) {
            java.lang.String str;
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            if (!z) {
                sb.append("  ");
                android.util.TimeUtils.formatDuration(historyItem.time - j, sb, 19);
                sb.append(" (");
                sb.append(historyItem.numReadInts);
                sb.append(") ");
            } else {
                sb.append(9);
                sb.append(',');
                sb.append(android.os.BatteryStats.HISTORY_DATA);
                sb.append(',');
                if (this.lastTime >= 0) {
                    sb.append(historyItem.time - this.lastTime);
                } else {
                    sb.append(historyItem.time - j);
                }
                this.lastTime = historyItem.time;
            }
            if (historyItem.cmd == 4) {
                if (z) {
                    sb.append(":");
                }
                sb.append("START\n");
                reset();
            } else {
                if (historyItem.cmd == 5) {
                    str = ":";
                } else if (historyItem.cmd == 7) {
                    str = ":";
                } else if (historyItem.cmd == 8) {
                    if (z) {
                        sb.append(":");
                    }
                    sb.append("SHUTDOWN\n");
                } else if (historyItem.cmd == 6) {
                    if (z) {
                        sb.append(":");
                    }
                    sb.append("*OVERFLOW*\n");
                } else {
                    if (!z) {
                        if (historyItem.batteryLevel < 10) {
                            sb.append("00");
                        } else if (historyItem.batteryLevel < 100) {
                            sb.append(android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS);
                        }
                        sb.append(historyItem.batteryLevel);
                        if (z2) {
                            sb.append(" ");
                            if (historyItem.states >= 0) {
                                if (historyItem.states < 16) {
                                    sb.append("0000000");
                                } else if (historyItem.states < 256) {
                                    sb.append("000000");
                                } else if (historyItem.states < 4096) {
                                    sb.append("00000");
                                } else if (historyItem.states < 65536) {
                                    sb.append("0000");
                                } else if (historyItem.states < 1048576) {
                                    sb.append("000");
                                } else if (historyItem.states < 16777216) {
                                    sb.append("00");
                                } else if (historyItem.states < 268435456) {
                                    sb.append(android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS);
                                }
                            }
                            sb.append(java.lang.Integer.toHexString(historyItem.states));
                        }
                    } else if (this.oldLevel != historyItem.batteryLevel) {
                        this.oldLevel = historyItem.batteryLevel;
                        sb.append(",Bl=");
                        sb.append(historyItem.batteryLevel);
                    }
                    int i = this.oldStatus;
                    byte b = historyItem.batteryStatus;
                    java.lang.String str2 = android.app.backup.FullBackup.FILES_TREE_TOKEN;
                    java.lang.String str3 = android.app.blob.XmlTags.ATTR_DESCRIPTION;
                    if (i != b) {
                        this.oldStatus = historyItem.batteryStatus;
                        sb.append(z ? ",Bs=" : " status=");
                        switch (this.oldStatus) {
                            case 1:
                                sb.append(z ? "?" : "unknown");
                                break;
                            case 2:
                                sb.append(z ? "c" : "charging");
                                break;
                            case 3:
                                sb.append(z ? android.app.blob.XmlTags.ATTR_DESCRIPTION : "discharging");
                                break;
                            case 4:
                                sb.append(z ? "n" : "not-charging");
                                break;
                            case 5:
                                sb.append(z ? android.app.backup.FullBackup.FILES_TREE_TOKEN : "full");
                                break;
                            default:
                                sb.append(this.oldStatus);
                                break;
                        }
                    }
                    if (this.oldHealth != historyItem.batteryHealth) {
                        this.oldHealth = historyItem.batteryHealth;
                        sb.append(z ? ",Bh=" : " health=");
                        switch (this.oldHealth) {
                            case 1:
                                sb.append(z ? "?" : "unknown");
                                break;
                            case 2:
                                sb.append(z ? "g" : "good");
                                break;
                            case 3:
                                sb.append(z ? android.os.BatteryStats.HISTORY_DATA : "overheat");
                                break;
                            case 4:
                                if (!z) {
                                    str3 = "dead";
                                }
                                sb.append(str3);
                                break;
                            case 5:
                                sb.append(z ? "v" : "over-voltage");
                                break;
                            case 6:
                                if (!z) {
                                    str2 = "failure";
                                }
                                sb.append(str2);
                                break;
                            case 7:
                                sb.append(z ? "c" : "cold");
                                break;
                            default:
                                sb.append(this.oldHealth);
                                break;
                        }
                    }
                    if (this.oldPlug != historyItem.batteryPlugType) {
                        this.oldPlug = historyItem.batteryPlugType;
                        sb.append(z ? ",Bp=" : " plug=");
                        switch (this.oldPlug) {
                            case 0:
                                sb.append(z ? "n" : "none");
                                break;
                            case 1:
                                sb.append(z ? android.app.backup.FullBackup.APK_TREE_TOKEN : "ac");
                                break;
                            case 2:
                                sb.append(z ? android.app.blob.XmlTags.ATTR_UID : "usb");
                                break;
                            case 3:
                            default:
                                sb.append(this.oldPlug);
                                break;
                            case 4:
                                sb.append(z ? "w" : android.media.audio.common.AudioDeviceDescription.CONNECTION_WIRELESS);
                                break;
                        }
                    }
                    if (this.oldTemp != historyItem.batteryTemperature) {
                        this.oldTemp = historyItem.batteryTemperature;
                        sb.append(z ? ",Bt=" : " temp=");
                        sb.append(this.oldTemp);
                    }
                    if (this.oldVolt != historyItem.batteryVoltage) {
                        this.oldVolt = historyItem.batteryVoltage;
                        sb.append(z ? ",Bv=" : " volt=");
                        sb.append(this.oldVolt);
                    }
                    int i2 = historyItem.batteryChargeUah / 1000;
                    if (this.oldChargeMAh != i2) {
                        this.oldChargeMAh = i2;
                        sb.append(z ? ",Bcc=" : " charge=");
                        sb.append(this.oldChargeMAh);
                    }
                    if (this.oldModemRailChargeMah != historyItem.modemRailChargeMah) {
                        this.oldModemRailChargeMah = historyItem.modemRailChargeMah;
                        sb.append(z ? ",Mrc=" : " modemRailChargemAh=");
                        sb.append(new java.text.DecimalFormat("#.##").format(this.oldModemRailChargeMah));
                    }
                    if (this.oldWifiRailChargeMah != historyItem.wifiRailChargeMah) {
                        this.oldWifiRailChargeMah = historyItem.wifiRailChargeMah;
                        sb.append(z ? ",Wrc=" : " wifiRailChargemAh=");
                        sb.append(new java.text.DecimalFormat("#.##").format(this.oldWifiRailChargeMah));
                    }
                    android.os.BatteryStats.printBitDescriptions(sb, this.oldState, historyItem.states, historyItem.wakelockTag, android.os.BatteryStats.HISTORY_STATE_DESCRIPTIONS, !z);
                    android.os.BatteryStats.printBitDescriptions(sb, this.oldState2, historyItem.states2, null, android.os.BatteryStats.HISTORY_STATE2_DESCRIPTIONS, !z);
                    if (historyItem.wakeReasonTag != null) {
                        if (z) {
                            sb.append(",wr=");
                            if (historyItem.wakeReasonTag.poolIdx == -1) {
                                sb.append(android.os.BatteryStats.sUidToString.applyAsString(historyItem.wakeReasonTag.uid));
                                sb.append(":\"");
                                sb.append(historyItem.wakeReasonTag.string.replace("\"", "\"\""));
                                sb.append("\"");
                            } else {
                                sb.append(historyItem.wakeReasonTag.poolIdx);
                            }
                        } else {
                            sb.append(" wake_reason=");
                            sb.append(historyItem.wakeReasonTag.uid);
                            sb.append(":\"");
                            sb.append(historyItem.wakeReasonTag.string);
                            sb.append("\"");
                        }
                    }
                    if (historyItem.eventCode != 0) {
                        sb.append(z ? "," : " ");
                        if ((historyItem.eventCode & 32768) != 0) {
                            sb.append("+");
                        } else if ((historyItem.eventCode & 16384) != 0) {
                            sb.append(com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
                        }
                        java.lang.String[] strArr = z ? android.os.BatteryStats.HISTORY_EVENT_CHECKIN_NAMES : android.os.BatteryStats.HISTORY_EVENT_NAMES;
                        int i3 = historyItem.eventCode & android.os.BatteryStats.HistoryItem.EVENT_TYPE_MASK;
                        if (i3 >= 0 && i3 < strArr.length) {
                            sb.append(strArr[i3]);
                        } else {
                            sb.append(z ? "Ev" : "event");
                            sb.append(i3);
                        }
                        sb.append("=");
                        if (z) {
                            if (historyItem.eventTag.poolIdx == -1) {
                                sb.append(android.os.BatteryStats.HISTORY_EVENT_INT_FORMATTERS[i3].applyAsString(historyItem.eventTag.uid));
                                sb.append(":\"");
                                sb.append(historyItem.eventTag.string.replace("\"", "\"\""));
                                sb.append("\"");
                            } else {
                                sb.append(historyItem.eventTag.poolIdx);
                            }
                        } else {
                            sb.append(android.os.BatteryStats.HISTORY_EVENT_INT_FORMATTERS[i3].applyAsString(historyItem.eventTag.uid));
                            sb.append(":\"");
                            sb.append(historyItem.eventTag.string);
                            sb.append("\"");
                        }
                    }
                    if (historyItem.powerStats != null && z2 && !z) {
                        sb.append("\n                 Stats: ");
                        sb.append(historyItem.powerStats.formatForBatteryHistory("\n                    "));
                    }
                    if (historyItem.processStateChange != null && z2 && !z) {
                        sb.append(" procstate: ");
                        sb.append(historyItem.processStateChange.formatForBatteryHistory());
                    }
                    sb.append("\n");
                    if (historyItem.stepDetails != null) {
                        if (!z) {
                            sb.append("                 Details: cpu=");
                            sb.append(historyItem.stepDetails.userTime);
                            sb.append("u+");
                            sb.append(historyItem.stepDetails.systemTime);
                            sb.append(android.app.blob.XmlTags.TAG_SESSION);
                            if (historyItem.stepDetails.appCpuUid1 >= 0) {
                                sb.append(" (");
                                printStepCpuUidDetails(sb, historyItem.stepDetails.appCpuUid1, historyItem.stepDetails.appCpuUTime1, historyItem.stepDetails.appCpuSTime1);
                                if (historyItem.stepDetails.appCpuUid2 >= 0) {
                                    sb.append(", ");
                                    printStepCpuUidDetails(sb, historyItem.stepDetails.appCpuUid2, historyItem.stepDetails.appCpuUTime2, historyItem.stepDetails.appCpuSTime2);
                                }
                                if (historyItem.stepDetails.appCpuUid3 >= 0) {
                                    sb.append(", ");
                                    printStepCpuUidDetails(sb, historyItem.stepDetails.appCpuUid3, historyItem.stepDetails.appCpuUTime3, historyItem.stepDetails.appCpuSTime3);
                                }
                                sb.append(')');
                            }
                            sb.append("\n");
                            sb.append("                          /proc/stat=");
                            sb.append(historyItem.stepDetails.statUserTime);
                            sb.append(" usr, ");
                            sb.append(historyItem.stepDetails.statSystemTime);
                            sb.append(" sys, ");
                            sb.append(historyItem.stepDetails.statIOWaitTime);
                            sb.append(" io, ");
                            sb.append(historyItem.stepDetails.statIrqTime);
                            sb.append(" irq, ");
                            sb.append(historyItem.stepDetails.statSoftIrqTime);
                            sb.append(" sirq, ");
                            sb.append(historyItem.stepDetails.statIdlTime);
                            sb.append(" idle");
                            int i4 = historyItem.stepDetails.statUserTime + historyItem.stepDetails.statSystemTime + historyItem.stepDetails.statIOWaitTime + historyItem.stepDetails.statIrqTime + historyItem.stepDetails.statSoftIrqTime;
                            int i5 = historyItem.stepDetails.statIdlTime + i4;
                            if (i5 > 0) {
                                sb.append(" (");
                                sb.append(java.lang.String.format("%.1f%%", java.lang.Float.valueOf((i4 / i5) * 100.0f)));
                                sb.append(" of ");
                                java.lang.StringBuilder sb2 = new java.lang.StringBuilder(64);
                                android.os.BatteryStats.formatTimeMsNoSpace(sb2, i5 * 10);
                                sb.append((java.lang.CharSequence) sb2);
                                sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                            }
                            sb.append(", SubsystemPowerState ");
                            sb.append(historyItem.stepDetails.statSubsystemPowerState);
                            sb.append("\n");
                        } else {
                            sb.append(9);
                            sb.append(',');
                            sb.append(android.os.BatteryStats.HISTORY_DATA);
                            sb.append(",0,Dcpu=");
                            sb.append(historyItem.stepDetails.userTime);
                            sb.append(":");
                            sb.append(historyItem.stepDetails.systemTime);
                            if (historyItem.stepDetails.appCpuUid1 >= 0) {
                                printStepCpuUidCheckinDetails(sb, historyItem.stepDetails.appCpuUid1, historyItem.stepDetails.appCpuUTime1, historyItem.stepDetails.appCpuSTime1);
                                if (historyItem.stepDetails.appCpuUid2 >= 0) {
                                    printStepCpuUidCheckinDetails(sb, historyItem.stepDetails.appCpuUid2, historyItem.stepDetails.appCpuUTime2, historyItem.stepDetails.appCpuSTime2);
                                }
                                if (historyItem.stepDetails.appCpuUid3 >= 0) {
                                    printStepCpuUidCheckinDetails(sb, historyItem.stepDetails.appCpuUid3, historyItem.stepDetails.appCpuUTime3, historyItem.stepDetails.appCpuSTime3);
                                }
                            }
                            sb.append("\n");
                            sb.append(9);
                            sb.append(',');
                            sb.append(android.os.BatteryStats.HISTORY_DATA);
                            sb.append(",0,Dpst=");
                            sb.append(historyItem.stepDetails.statUserTime);
                            sb.append(',');
                            sb.append(historyItem.stepDetails.statSystemTime);
                            sb.append(',');
                            sb.append(historyItem.stepDetails.statIOWaitTime);
                            sb.append(',');
                            sb.append(historyItem.stepDetails.statIrqTime);
                            sb.append(',');
                            sb.append(historyItem.stepDetails.statSoftIrqTime);
                            sb.append(',');
                            sb.append(historyItem.stepDetails.statIdlTime);
                            sb.append(',');
                            if (historyItem.stepDetails.statSubsystemPowerState != null) {
                                sb.append(historyItem.stepDetails.statSubsystemPowerState);
                            }
                            sb.append("\n");
                        }
                    }
                    this.oldState = historyItem.states;
                    this.oldState2 = historyItem.states2;
                    if ((historyItem.states2 & 524288) != 0) {
                        historyItem.states2 &= -524289;
                    }
                }
                if (z) {
                    sb.append(str);
                }
                if (historyItem.cmd == 7) {
                    sb.append("RESET:");
                    reset();
                }
                sb.append("TIME:");
                if (z) {
                    sb.append(historyItem.currentTime);
                    sb.append("\n");
                } else {
                    sb.append(" ");
                    sb.append(android.text.format.DateFormat.format("yyyy-MM-dd-HH-mm-ss", historyItem.currentTime).toString());
                    sb.append("\n");
                }
            }
            return sb.toString();
        }

        private void printStepCpuUidDetails(java.lang.StringBuilder sb, int i, int i2, int i3) {
            android.os.UserHandle.formatUid(sb, i);
            sb.append("=");
            sb.append(i2);
            sb.append("u+");
            sb.append(i3);
            sb.append(android.app.blob.XmlTags.TAG_SESSION);
        }

        private void printStepCpuUidCheckinDetails(java.lang.StringBuilder sb, int i, int i2, int i3) {
            sb.append('/');
            sb.append(i);
            sb.append(":");
            sb.append(i2);
            sb.append(":");
            sb.append(i3);
        }
    }

    private void printSizeValue(java.io.PrintWriter printWriter, long j) {
        java.lang.String str;
        float f = j;
        if (f < 10240.0f) {
            str = "";
        } else {
            f /= 1024.0f;
            str = "KB";
        }
        if (f >= 10240.0f) {
            f /= 1024.0f;
            str = "MB";
        }
        if (f >= 10240.0f) {
            f /= 1024.0f;
            str = "GB";
        }
        if (f >= 10240.0f) {
            f /= 1024.0f;
            str = "TB";
        }
        if (f >= 10240.0f) {
            f /= 1024.0f;
            str = "PB";
        }
        printWriter.print((int) f);
        printWriter.print(str);
    }

    private static boolean dumpTimeEstimate(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2, java.lang.String str3, long j) {
        if (j < 0) {
            return false;
        }
        printWriter.print(str);
        printWriter.print(str2);
        printWriter.print(str3);
        java.lang.StringBuilder sb = new java.lang.StringBuilder(64);
        formatTimeMs(sb, j);
        printWriter.print(sb);
        printWriter.println();
        return true;
    }

    private static boolean dumpDurationSteps(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2, android.os.BatteryStats.LevelStepTracker levelStepTracker, boolean z) {
        int i;
        int i2;
        char c;
        char c2 = 0;
        if (levelStepTracker == null || (i = levelStepTracker.mNumStepDurations) <= 0) {
            return false;
        }
        if (!z) {
            printWriter.println(str2);
        }
        java.lang.String[] strArr = new java.lang.String[5];
        int i3 = 0;
        while (true) {
            char c3 = 1;
            if (i3 >= i) {
                return true;
            }
            long durationAt = levelStepTracker.getDurationAt(i3);
            int levelAt = levelStepTracker.getLevelAt(i3);
            long initModeAt = levelStepTracker.getInitModeAt(i3);
            long modModeAt = levelStepTracker.getModModeAt(i3);
            if (z) {
                strArr[c2] = java.lang.Long.toString(durationAt);
                strArr[1] = java.lang.Integer.toString(levelAt);
                if ((modModeAt & 3) == 0) {
                    i2 = i;
                    switch (((int) (initModeAt & 3)) + 1) {
                        case 1:
                            strArr[2] = "s-";
                            break;
                        case 2:
                            strArr[2] = "s+";
                            break;
                        case 3:
                            strArr[2] = "sd";
                            break;
                        case 4:
                            strArr[2] = "sds";
                            break;
                        default:
                            strArr[2] = "?";
                            break;
                    }
                } else {
                    i2 = i;
                    strArr[2] = "";
                }
                if ((modModeAt & 4) == 0) {
                    strArr[3] = (initModeAt & 4) != 0 ? "p+" : "p-";
                } else {
                    strArr[3] = "";
                }
                if ((modModeAt & 8) == 0) {
                    strArr[4] = (initModeAt & 8) != 0 ? "i+" : "i-";
                } else {
                    strArr[4] = "";
                }
                dumpLine(printWriter, 0, "i", str2, strArr);
                c2 = 0;
            } else {
                i2 = i;
                printWriter.print(str);
                printWriter.print("#");
                printWriter.print(i3);
                printWriter.print(": ");
                android.util.TimeUtils.formatDuration(durationAt, printWriter);
                printWriter.print(" to ");
                printWriter.print(levelAt);
                if ((modModeAt & 3) != 0) {
                    c = c2;
                } else {
                    printWriter.print(" (");
                    switch (((int) (initModeAt & 3)) + 1) {
                        case 1:
                            printWriter.print("screen-off");
                            break;
                        case 2:
                            printWriter.print("screen-on");
                            break;
                        case 3:
                            printWriter.print("screen-doze");
                            break;
                        case 4:
                            printWriter.print("screen-doze-suspend");
                            break;
                        default:
                            printWriter.print("screen-?");
                            break;
                    }
                    c = 1;
                }
                if ((modModeAt & 4) == 0) {
                    printWriter.print(c != 0 ? ", " : " (");
                    printWriter.print((initModeAt & 4) != 0 ? "power-save-on" : "power-save-off");
                    c = 1;
                }
                if ((modModeAt & 8) == 0) {
                    printWriter.print(c != 0 ? ", " : " (");
                    printWriter.print((initModeAt & 8) != 0 ? "device-idle-on" : "device-idle-off");
                } else {
                    c3 = c;
                }
                if (c3 != 0) {
                    printWriter.print(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                }
                printWriter.println();
            }
            i3++;
            i = i2;
        }
    }

    private static void dumpDurationSteps(android.util.proto.ProtoOutputStream protoOutputStream, long j, android.os.BatteryStats.LevelStepTracker levelStepTracker) {
        int i;
        int i2;
        if (levelStepTracker == null) {
            return;
        }
        int i3 = levelStepTracker.mNumStepDurations;
        for (int i4 = 0; i4 < i3; i4++) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1112396529665L, levelStepTracker.getDurationAt(i4));
            protoOutputStream.write(1120986464258L, levelStepTracker.getLevelAt(i4));
            long initModeAt = levelStepTracker.getInitModeAt(i4);
            long modModeAt = levelStepTracker.getModModeAt(i4);
            int i5 = 3;
            if ((modModeAt & 3) != 0) {
                i = 0;
            } else {
                switch (((int) (3 & initModeAt)) + 1) {
                    case 1:
                        i = 2;
                        break;
                    case 2:
                        i = 1;
                        break;
                    case 3:
                        i = 3;
                        break;
                    case 4:
                        i = 4;
                        break;
                    default:
                        i = 5;
                        break;
                }
            }
            protoOutputStream.write(1159641169923L, i);
            if ((modModeAt & 4) != 0) {
                i2 = 0;
            } else {
                i2 = (4 & initModeAt) == 0 ? 2 : 1;
            }
            protoOutputStream.write(1159641169924L, i2);
            if ((modModeAt & 8) != 0) {
                i5 = 0;
            } else if ((initModeAt & 8) != 0) {
                i5 = 2;
            }
            protoOutputStream.write(1159641169925L, i5);
            protoOutputStream.end(start);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00a7 A[Catch: all -> 0x019b, TryCatch #3 {all -> 0x019b, blocks: (B:32:0x008d, B:35:0x0096, B:37:0x00a7, B:39:0x00ab, B:42:0x00b3, B:44:0x00c0, B:47:0x00d1, B:51:0x0164, B:52:0x00e0, B:53:0x00e8, B:55:0x00ee, B:56:0x00fe, B:58:0x0104, B:62:0x0129, B:71:0x016d, B:72:0x0182, B:75:0x0189, B:100:0x005c, B:102:0x0063, B:105:0x0071), top: B:31:0x008d }] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x01bf  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x01c8  */
    /* JADX WARN: Removed duplicated region for block: B:97:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void dumpHistory(java.io.PrintWriter printWriter, int i, long j, boolean z) {
        long j2;
        long j3;
        android.os.BatteryStats.HistoryItem historyItem;
        android.os.BatteryStats.HistoryEventTracker historyEventTracker;
        java.lang.Object obj;
        boolean z2;
        android.os.BatteryStats.HistoryEventTracker historyEventTracker2;
        boolean z3;
        int i2;
        android.os.BatteryStats.HistoryTag historyTag;
        android.os.BatteryStats.HistoryEventTracker historyEventTracker3;
        int i3;
        synchronized (this) {
            dumpHistoryTagPoolLocked(printWriter, z);
        }
        android.os.BatteryStats.HistoryPrinter historyPrinter = new android.os.BatteryStats.HistoryPrinter();
        long j4 = 0;
        long j5 = -1;
        com.android.internal.os.BatteryStatsHistoryIterator iterateBatteryStatsHistory = iterateBatteryStatsHistory(0L, -1L);
        boolean z4 = false;
        long j6 = -1;
        boolean z5 = false;
        android.os.BatteryStats.HistoryEventTracker historyEventTracker4 = null;
        while (true) {
            try {
                android.os.BatteryStats.HistoryItem next = iterateBatteryStatsHistory.next();
                if (next == null) {
                    break;
                }
                try {
                    j6 = next.time;
                    if (j5 >= j4) {
                        j2 = j5;
                    } else {
                        j2 = j6;
                    }
                    try {
                        if (next.time >= j) {
                            boolean z6 = true;
                            if (j < j4 || z5) {
                                j3 = j6;
                                historyItem = next;
                                historyEventTracker = historyEventTracker4;
                                obj = null;
                                z2 = z5;
                            } else {
                                try {
                                    if (next.cmd == 5 || next.cmd == 7 || next.cmd == 4) {
                                        j3 = j6;
                                        historyItem = next;
                                        historyEventTracker2 = historyEventTracker4;
                                    } else if (next.cmd == 8) {
                                        j3 = j6;
                                        historyItem = next;
                                        historyEventTracker2 = historyEventTracker4;
                                    } else {
                                        j3 = j6;
                                        if (next.currentTime == j4) {
                                            historyItem = next;
                                            historyEventTracker2 = historyEventTracker4;
                                            z3 = z5;
                                        } else {
                                            byte b = next.cmd;
                                            next.cmd = (byte) 5;
                                            historyItem = next;
                                            historyEventTracker2 = historyEventTracker4;
                                            historyPrinter.printNextItem(printWriter, next, j2, z, (i & 32) != 0 ? true : z4 ? 1 : 0);
                                            historyItem.cmd = b;
                                            z3 = true;
                                        }
                                        if (historyEventTracker2 != null) {
                                            historyEventTracker = historyEventTracker2;
                                            obj = null;
                                            z2 = z3;
                                        } else {
                                            if (historyItem.cmd != 0) {
                                                historyPrinter.printNextItem(printWriter, historyItem, j2, z, (i & 32) != 0 ? true : z4 ? 1 : 0);
                                                historyItem.cmd = z4 ? (byte) 1 : (byte) 0;
                                            }
                                            int i4 = historyItem.eventCode;
                                            android.os.BatteryStats.HistoryTag historyTag2 = historyItem.eventTag;
                                            historyItem.eventTag = new android.os.BatteryStats.HistoryTag();
                                            int i5 = z4 ? 1 : 0;
                                            while (i5 < 22) {
                                                java.util.HashMap<java.lang.String, android.util.SparseIntArray> stateForEvent = historyEventTracker2.getStateForEvent(i5);
                                                if (stateForEvent == null) {
                                                    i2 = i5;
                                                    historyTag = historyTag2;
                                                    historyEventTracker3 = historyEventTracker2;
                                                    i3 = i4;
                                                } else {
                                                    for (java.util.Map.Entry<java.lang.String, android.util.SparseIntArray> entry : stateForEvent.entrySet()) {
                                                        android.util.SparseIntArray value = entry.getValue();
                                                        int i6 = z4 ? 1 : 0;
                                                        while (i6 < value.size()) {
                                                            historyItem.eventCode = i5;
                                                            historyItem.eventTag.string = entry.getKey();
                                                            historyItem.eventTag.uid = value.keyAt(i6);
                                                            historyItem.eventTag.poolIdx = value.valueAt(i6);
                                                            historyPrinter.printNextItem(printWriter, historyItem, j2, z, (i & 32) != 0 ? true : z4);
                                                            historyItem.wakeReasonTag = null;
                                                            historyItem.wakelockTag = null;
                                                            i6++;
                                                            i4 = i4;
                                                            historyTag2 = historyTag2;
                                                            i5 = i5;
                                                            value = value;
                                                            historyEventTracker2 = historyEventTracker2;
                                                            z4 = false;
                                                        }
                                                        i4 = i4;
                                                        historyEventTracker2 = historyEventTracker2;
                                                        z4 = false;
                                                    }
                                                    i2 = i5;
                                                    historyTag = historyTag2;
                                                    historyEventTracker3 = historyEventTracker2;
                                                    i3 = i4;
                                                }
                                                i5 = i2 + 1;
                                                i4 = i3;
                                                historyTag2 = historyTag;
                                                historyEventTracker2 = historyEventTracker3;
                                                z4 = false;
                                            }
                                            int i7 = i4;
                                            obj = null;
                                            historyItem.eventCode = i7;
                                            historyItem.eventTag = historyTag2;
                                            historyEventTracker = null;
                                            z2 = z3;
                                        }
                                    }
                                    historyPrinter.printNextItem(printWriter, historyItem, j2, z, (i & 32) != 0 ? true : z4 ? 1 : 0);
                                    historyItem.cmd = z4 ? (byte) 1 : (byte) 0;
                                    z3 = true;
                                    if (historyEventTracker2 != null) {
                                    }
                                } catch (java.lang.Throwable th) {
                                    th = th;
                                    j6 = j3;
                                    th.printStackTrace(printWriter);
                                    android.util.Slog.wtf(TAG, "Corrupted battery history", th);
                                    if (iterateBatteryStatsHistory != null) {
                                    }
                                    if (j < 0) {
                                    }
                                }
                            }
                            if ((i & 32) == 0) {
                                z6 = false;
                            }
                            historyPrinter.printNextItem(printWriter, historyItem, j2, z, z6);
                            z5 = z2;
                            historyEventTracker4 = historyEventTracker;
                        } else {
                            j3 = j6;
                        }
                        j5 = j2;
                        j6 = j3;
                        j4 = 0;
                        z4 = false;
                        z5 = z5;
                    } catch (java.lang.Throwable th2) {
                        th = th2;
                    }
                } catch (java.lang.Throwable th3) {
                    th = th3;
                }
            } finally {
            }
        }
        if (iterateBatteryStatsHistory != null) {
            iterateBatteryStatsHistory.close();
        }
        if (j < 0) {
            commitCurrentHistoryBatchLocked();
            printWriter.print(z ? "NEXT: " : "  NEXT: ");
            printWriter.println(j6 + 1);
        }
    }

    private void dumpHistoryTagPoolLocked(java.io.PrintWriter printWriter, boolean z) {
        if (z) {
            for (int i = 0; i < getHistoryStringPoolSize(); i++) {
                printWriter.print(9);
                printWriter.print(',');
                printWriter.print(HISTORY_STRING_POOL);
                printWriter.print(',');
                printWriter.print(i);
                printWriter.print(",");
                printWriter.print(getHistoryTagPoolUid(i));
                printWriter.print(",\"");
                java.lang.String historyTagPoolString = getHistoryTagPoolString(i);
                if (historyTagPoolString != null) {
                    printWriter.print(historyTagPoolString.replace("\\", "\\\\").replace("\"", "\\\""));
                }
                printWriter.print("\"");
                printWriter.println();
            }
            return;
        }
        long historyTotalSize = getHistoryTotalSize();
        long historyUsedSize = getHistoryUsedSize();
        printWriter.print("Battery History (");
        printWriter.print((100 * historyUsedSize) / historyTotalSize);
        printWriter.print("% used, ");
        printSizeValue(printWriter, historyUsedSize);
        printWriter.print(" used of ");
        printSizeValue(printWriter, historyTotalSize);
        printWriter.print(", ");
        printWriter.print(getHistoryStringPoolSize());
        printWriter.print(" strings using ");
        printSizeValue(printWriter, getHistoryStringPoolBytes());
        printWriter.println("):");
    }

    private void dumpDailyLevelStepSummary(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2, android.os.BatteryStats.LevelStepTracker levelStepTracker, java.lang.StringBuilder sb, int[] iArr) {
        if (levelStepTracker == null) {
            return;
        }
        long computeTimeEstimate = levelStepTracker.computeTimeEstimate(0L, 0L, iArr);
        if (computeTimeEstimate >= 0) {
            printWriter.print(str);
            printWriter.print(str2);
            printWriter.print(" total time: ");
            sb.setLength(0);
            formatTimeMs(sb, computeTimeEstimate);
            printWriter.print(sb);
            printWriter.print(" (from ");
            printWriter.print(iArr[0]);
            printWriter.println(" steps)");
        }
        for (int i = 0; i < STEP_LEVEL_MODES_OF_INTEREST.length; i++) {
            long computeTimeEstimate2 = levelStepTracker.computeTimeEstimate(STEP_LEVEL_MODES_OF_INTEREST[i], STEP_LEVEL_MODE_VALUES[i], iArr);
            if (computeTimeEstimate2 > 0) {
                printWriter.print(str);
                printWriter.print(str2);
                printWriter.print(" ");
                printWriter.print(STEP_LEVEL_MODE_LABELS[i]);
                printWriter.print(" time: ");
                sb.setLength(0);
                formatTimeMs(sb, computeTimeEstimate2);
                printWriter.print(sb);
                printWriter.print(" (from ");
                printWriter.print(iArr[0]);
                printWriter.println(" steps)");
            }
        }
    }

    private void dumpDailyPackageChanges(java.io.PrintWriter printWriter, java.lang.String str, java.util.ArrayList<android.os.BatteryStats.PackageChange> arrayList) {
        if (arrayList == null) {
            return;
        }
        printWriter.print(str);
        printWriter.println("Package changes:");
        for (int i = 0; i < arrayList.size(); i++) {
            android.os.BatteryStats.PackageChange packageChange = arrayList.get(i);
            if (packageChange.mUpdate) {
                printWriter.print(str);
                printWriter.print("  Update ");
                printWriter.print(packageChange.mPackageName);
                printWriter.print(" vers=");
                printWriter.println(packageChange.mVersionCode);
            } else {
                printWriter.print(str);
                printWriter.print("  Uninstall ");
                printWriter.println(packageChange.mPackageName);
            }
        }
    }

    public void dump(android.content.Context context, java.io.PrintWriter printWriter, int i, int i2, long j, android.os.BatteryStats.BatteryStatsDumpHelper batteryStatsDumpHelper) {
        synchronized (this) {
            prepareForDumpLocked();
        }
        boolean z = (i & 14) != 0;
        if ((i & 8) != 0 || !z) {
            dumpHistory(printWriter, i, j, false);
            printWriter.println();
        }
        if (z && (i & 6) == 0) {
            return;
        }
        synchronized (this) {
            dumpLocked(context, printWriter, i, i2, z, batteryStatsDumpHelper);
        }
    }

    private void dumpLocked(android.content.Context context, java.io.PrintWriter printWriter, int i, int i2, boolean z, android.os.BatteryStats.BatteryStatsDumpHelper batteryStatsDumpHelper) {
        java.lang.String str;
        java.util.ArrayList<android.os.BatteryStats.PackageChange> arrayList;
        java.lang.String str2;
        java.lang.String str3;
        if (!z) {
            android.util.SparseArray<? extends android.os.BatteryStats.Uid> uidStats = getUidStats();
            int size = uidStats.size();
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            boolean z2 = false;
            for (int i3 = 0; i3 < size; i3++) {
                android.util.SparseArray<? extends android.os.BatteryStats.Uid.Pid> pidStats = uidStats.valueAt(i3).getPidStats();
                if (pidStats != null) {
                    for (int i4 = 0; i4 < pidStats.size(); i4++) {
                        android.os.BatteryStats.Uid.Pid valueAt = pidStats.valueAt(i4);
                        if (!z2) {
                            printWriter.println("Per-PID Stats:");
                            z2 = true;
                        }
                        long j = valueAt.mWakeSumMs;
                        long j2 = valueAt.mWakeNesting > 0 ? elapsedRealtime - valueAt.mWakeStartMs : 0L;
                        printWriter.print("  PID ");
                        printWriter.print(pidStats.keyAt(i4));
                        printWriter.print(" wake time: ");
                        android.util.TimeUtils.formatDuration(j + j2, printWriter);
                        printWriter.println("");
                    }
                }
            }
            if (z2) {
                printWriter.println();
            }
        }
        if (!z || (i & 2) != 0) {
            if (dumpDurationSteps(printWriter, "  ", "Discharge step durations:", getDischargeLevelStepTracker(), false)) {
                long computeBatteryTimeRemaining = computeBatteryTimeRemaining(android.os.SystemClock.elapsedRealtime() * 1000);
                if (computeBatteryTimeRemaining >= 0) {
                    printWriter.print("  Estimated discharge time remaining: ");
                    android.util.TimeUtils.formatDuration(computeBatteryTimeRemaining / 1000, printWriter);
                    printWriter.println();
                }
                android.os.BatteryStats.LevelStepTracker dischargeLevelStepTracker = getDischargeLevelStepTracker();
                for (int i5 = 0; i5 < STEP_LEVEL_MODES_OF_INTEREST.length; i5++) {
                    dumpTimeEstimate(printWriter, "  Estimated ", STEP_LEVEL_MODE_LABELS[i5], " time: ", dischargeLevelStepTracker.computeTimeEstimate(STEP_LEVEL_MODES_OF_INTEREST[i5], STEP_LEVEL_MODE_VALUES[i5], null));
                }
                printWriter.println();
            }
            if (dumpDurationSteps(printWriter, "  ", "Charge step durations:", getChargeLevelStepTracker(), false)) {
                long computeChargeTimeRemaining = computeChargeTimeRemaining(android.os.SystemClock.elapsedRealtime() * 1000);
                if (computeChargeTimeRemaining >= 0) {
                    printWriter.print("  Estimated charge time remaining: ");
                    android.util.TimeUtils.formatDuration(computeChargeTimeRemaining / 1000, printWriter);
                    printWriter.println();
                }
                printWriter.println();
            }
        }
        if (!z || (i & 4) != 0) {
            printWriter.println("Daily stats:");
            printWriter.print("  Current start time: ");
            java.lang.String str4 = "yyyy-MM-dd-HH-mm-ss";
            printWriter.println(android.text.format.DateFormat.format("yyyy-MM-dd-HH-mm-ss", getCurrentDailyStartTime()).toString());
            printWriter.print("  Next min deadline: ");
            printWriter.println(android.text.format.DateFormat.format("yyyy-MM-dd-HH-mm-ss", getNextMinDailyDeadline()).toString());
            printWriter.print("  Next max deadline: ");
            printWriter.println(android.text.format.DateFormat.format("yyyy-MM-dd-HH-mm-ss", getNextMaxDailyDeadline()).toString());
            java.lang.StringBuilder sb = new java.lang.StringBuilder(64);
            int[] iArr = new int[1];
            android.os.BatteryStats.LevelStepTracker dailyDischargeLevelStepTracker = getDailyDischargeLevelStepTracker();
            android.os.BatteryStats.LevelStepTracker dailyChargeLevelStepTracker = getDailyChargeLevelStepTracker();
            java.util.ArrayList<android.os.BatteryStats.PackageChange> dailyPackageChanges = getDailyPackageChanges();
            if (dailyDischargeLevelStepTracker.mNumStepDurations > 0 || dailyChargeLevelStepTracker.mNumStepDurations > 0 || dailyPackageChanges != null) {
                if ((i & 4) != 0) {
                    str = "    ";
                } else if (z) {
                    printWriter.println("  Current daily steps:");
                    str = "    ";
                    dumpDailyLevelStepSummary(printWriter, "    ", "Discharge", dailyDischargeLevelStepTracker, sb, iArr);
                    dumpDailyLevelStepSummary(printWriter, "    ", "Charge", dailyChargeLevelStepTracker, sb, iArr);
                } else {
                    str = "    ";
                }
                if (!dumpDurationSteps(printWriter, str, "  Current daily discharge step durations:", dailyDischargeLevelStepTracker, false)) {
                    arrayList = dailyPackageChanges;
                } else {
                    arrayList = dailyPackageChanges;
                    dumpDailyLevelStepSummary(printWriter, "      ", "Discharge", dailyDischargeLevelStepTracker, sb, iArr);
                }
                if (dumpDurationSteps(printWriter, str, "  Current daily charge step durations:", dailyChargeLevelStepTracker, false)) {
                    dumpDailyLevelStepSummary(printWriter, "      ", "Charge", dailyChargeLevelStepTracker, sb, iArr);
                }
                dumpDailyPackageChanges(printWriter, str, arrayList);
            } else {
                str = "    ";
            }
            int i6 = 0;
            while (true) {
                android.os.BatteryStats.DailyItem dailyItemLocked = getDailyItemLocked(i6);
                if (dailyItemLocked == null) {
                    break;
                }
                int i7 = i6 + 1;
                int i8 = i & 4;
                if (i8 != 0) {
                    printWriter.println();
                }
                printWriter.print("  Daily from ");
                printWriter.print(android.text.format.DateFormat.format(str4, dailyItemLocked.mStartTime).toString());
                printWriter.print(" to ");
                printWriter.print(android.text.format.DateFormat.format(str4, dailyItemLocked.mEndTime).toString());
                printWriter.println(":");
                if (i8 != 0 || !z) {
                    if (!dumpDurationSteps(printWriter, "      ", "    Discharge step durations:", dailyItemLocked.mDischargeSteps, false)) {
                        str2 = str4;
                        str3 = "      ";
                    } else {
                        str2 = str4;
                        str3 = "      ";
                        dumpDailyLevelStepSummary(printWriter, "        ", "Discharge", dailyItemLocked.mDischargeSteps, sb, iArr);
                    }
                    if (dumpDurationSteps(printWriter, str3, "    Charge step durations:", dailyItemLocked.mChargeSteps, false)) {
                        dumpDailyLevelStepSummary(printWriter, "        ", "Charge", dailyItemLocked.mChargeSteps, sb, iArr);
                    }
                    dumpDailyPackageChanges(printWriter, str, dailyItemLocked.mPackageChanges);
                } else {
                    dumpDailyLevelStepSummary(printWriter, "    ", "Discharge", dailyItemLocked.mDischargeSteps, sb, iArr);
                    dumpDailyLevelStepSummary(printWriter, "    ", "Charge", dailyItemLocked.mChargeSteps, sb, iArr);
                    str2 = str4;
                }
                i6 = i7;
                str4 = str2;
            }
            printWriter.println();
        }
        if (!z || (i & 2) != 0) {
            printWriter.println("Statistics since last charge:");
            printWriter.println("  System starts: " + getStartCount() + ", currently on battery: " + getIsOnBattery());
            dumpLocked(context, printWriter, "", 0, i2, (i & 64) != 0, batteryStatsDumpHelper);
            printWriter.println();
        }
    }

    public void dumpCheckin(android.content.Context context, java.io.PrintWriter printWriter, java.util.List<android.content.pm.ApplicationInfo> list, int i, long j, android.os.BatteryStats.BatteryStatsDumpHelper batteryStatsDumpHelper) {
        synchronized (this) {
            prepareForDumpLocked();
            dumpLine(printWriter, 0, "i", VERSION_DATA, 36, java.lang.Integer.valueOf(getParcelVersion()), getStartPlatformVersion(), getEndPlatformVersion());
        }
        if ((i & 24) != 0) {
            dumpHistory(printWriter, i, j, true);
        }
        if ((i & 8) != 0) {
            return;
        }
        synchronized (this) {
            dumpCheckinLocked(context, printWriter, list, i, batteryStatsDumpHelper);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void dumpCheckinLocked(android.content.Context context, java.io.PrintWriter printWriter, java.util.List<android.content.pm.ApplicationInfo> list, int i, android.os.BatteryStats.BatteryStatsDumpHelper batteryStatsDumpHelper) {
        if (list != null) {
            android.util.SparseArray sparseArray = new android.util.SparseArray();
            for (int i2 = 0; i2 < list.size(); i2++) {
                android.content.pm.ApplicationInfo applicationInfo = list.get(i2);
                android.util.Pair pair = (android.util.Pair) sparseArray.get(android.os.UserHandle.getAppId(applicationInfo.uid));
                if (pair == null) {
                    pair = new android.util.Pair(new java.util.ArrayList(), new android.util.MutableBoolean(false));
                    sparseArray.put(android.os.UserHandle.getAppId(applicationInfo.uid), pair);
                }
                ((java.util.ArrayList) pair.first).add(applicationInfo.packageName);
            }
            android.util.SparseArray<? extends android.os.BatteryStats.Uid> uidStats = getUidStats();
            int size = uidStats.size();
            for (int i3 = 0; i3 < size; i3++) {
                int appId = android.os.UserHandle.getAppId(uidStats.keyAt(i3));
                android.util.Pair pair2 = (android.util.Pair) sparseArray.get(appId);
                if (pair2 != null && !((android.util.MutableBoolean) pair2.second).value) {
                    ((android.util.MutableBoolean) pair2.second).value = true;
                    for (int i4 = 0; i4 < ((java.util.ArrayList) pair2.first).size(); i4++) {
                        dumpLine(printWriter, 0, "i", "uid", java.lang.Integer.toString(appId), (java.lang.String) ((java.util.ArrayList) pair2.first).get(i4));
                    }
                }
            }
        }
        if ((i & 4) == 0) {
            dumpDurationSteps(printWriter, "", DISCHARGE_STEP_DATA, getDischargeLevelStepTracker(), true);
            java.lang.String[] strArr = new java.lang.String[1];
            long computeBatteryTimeRemaining = computeBatteryTimeRemaining(android.os.SystemClock.elapsedRealtime() * 1000);
            if (computeBatteryTimeRemaining >= 0) {
                strArr[0] = java.lang.Long.toString(computeBatteryTimeRemaining);
                dumpLine(printWriter, 0, "i", DISCHARGE_TIME_REMAIN_DATA, strArr);
            }
            dumpDurationSteps(printWriter, "", CHARGE_STEP_DATA, getChargeLevelStepTracker(), true);
            long computeChargeTimeRemaining = computeChargeTimeRemaining(android.os.SystemClock.elapsedRealtime() * 1000);
            if (computeChargeTimeRemaining >= 0) {
                strArr[0] = java.lang.Long.toString(computeChargeTimeRemaining);
                dumpLine(printWriter, 0, "i", CHARGE_TIME_REMAIN_DATA, strArr);
            }
            dumpCheckinLocked(context, printWriter, 0, -1, (i & 64) != 0, batteryStatsDumpHelper);
        }
    }

    public void dumpProtoLocked(android.content.Context context, java.io.FileDescriptor fileDescriptor, java.util.List<android.content.pm.ApplicationInfo> list, int i, long j, android.os.BatteryStats.BatteryStatsDumpHelper batteryStatsDumpHelper) {
        android.util.proto.ProtoOutputStream protoOutputStream = new android.util.proto.ProtoOutputStream(fileDescriptor);
        prepareForDumpLocked();
        if ((i & 24) != 0) {
            dumpProtoHistoryLocked(protoOutputStream, i, j);
            protoOutputStream.flush();
            return;
        }
        long start = protoOutputStream.start(1146756268033L);
        protoOutputStream.write(1120986464257L, 36);
        protoOutputStream.write(1112396529666L, getParcelVersion());
        protoOutputStream.write(1138166333443L, getStartPlatformVersion());
        protoOutputStream.write(1138166333444L, getEndPlatformVersion());
        if ((i & 4) == 0) {
            android.os.BatteryUsageStats batteryUsageStats = batteryStatsDumpHelper.getBatteryUsageStats(this, false);
            dumpProtoAppsLocked(protoOutputStream, batteryUsageStats, list, new android.os.BatteryStats.ProportionalAttributionCalculator(context, batteryUsageStats));
            dumpProtoSystemLocked(protoOutputStream, batteryUsageStats);
        }
        protoOutputStream.end(start);
        protoOutputStream.flush();
    }

    private void dumpProtoAppsLocked(android.util.proto.ProtoOutputStream protoOutputStream, android.os.BatteryUsageStats batteryUsageStats, java.util.List<android.content.pm.ApplicationInfo> list, android.os.BatteryStats.ProportionalAttributionCalculator proportionalAttributionCalculator) {
        long j;
        long j2;
        int i;
        long j3;
        android.os.BatteryStats.ProportionalAttributionCalculator proportionalAttributionCalculator2;
        int i2;
        long[] jArr;
        long j4;
        android.os.BatteryStats.Uid uid;
        android.util.SparseArray sparseArray;
        android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Pkg> arrayMap;
        int i3;
        android.util.SparseArray<? extends android.os.BatteryStats.Uid> sparseArray2;
        long j5;
        long j6;
        long j7;
        android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Pkg.Serv> arrayMap2;
        long uptimeMillis = android.os.SystemClock.uptimeMillis() * 1000;
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        long j8 = elapsedRealtime * 1000;
        long batteryUptime = getBatteryUptime(uptimeMillis);
        android.util.SparseArray sparseArray3 = new android.util.SparseArray();
        if (list != null) {
            for (int i4 = 0; i4 < list.size(); i4++) {
                android.content.pm.ApplicationInfo applicationInfo = list.get(i4);
                int appId = android.os.UserHandle.getAppId(applicationInfo.uid);
                java.util.ArrayList arrayList = (java.util.ArrayList) sparseArray3.get(appId);
                if (arrayList == null) {
                    arrayList = new java.util.ArrayList();
                    sparseArray3.put(appId, arrayList);
                }
                arrayList.add(applicationInfo.packageName);
            }
        }
        android.util.SparseArray sparseArray4 = new android.util.SparseArray();
        java.util.List<android.os.UidBatteryConsumer> uidBatteryConsumers = batteryUsageStats.getUidBatteryConsumers();
        for (int size = uidBatteryConsumers.size() - 1; size >= 0; size--) {
            android.os.UidBatteryConsumer uidBatteryConsumer = uidBatteryConsumers.get(size);
            sparseArray4.put(uidBatteryConsumer.getUid(), uidBatteryConsumer);
        }
        android.util.SparseArray<? extends android.os.BatteryStats.Uid> uidStats = getUidStats();
        int size2 = uidStats.size();
        int i5 = 0;
        while (i5 < size2) {
            long start = protoOutputStream.start(2246267895813L);
            android.util.SparseArray<? extends android.os.BatteryStats.Uid> sparseArray5 = uidStats;
            int i6 = size2;
            android.os.BatteryStats.Uid valueAt = sparseArray5.valueAt(i5);
            int keyAt = sparseArray5.keyAt(i5);
            int i7 = i5;
            protoOutputStream.write(1120986464257L, keyAt);
            java.util.ArrayList arrayList2 = (java.util.ArrayList) sparseArray3.get(android.os.UserHandle.getAppId(keyAt));
            if (arrayList2 == null) {
                arrayList2 = new java.util.ArrayList();
            }
            android.util.SparseArray sparseArray6 = sparseArray3;
            android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Pkg> packageStats = valueAt.getPackageStats();
            int size3 = packageStats.size() - 1;
            while (true) {
                j = j8;
                if (size3 < 0) {
                    break;
                }
                java.lang.String keyAt2 = packageStats.keyAt(size3);
                android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Pkg.Serv> serviceStats = packageStats.valueAt(size3).getServiceStats();
                if (serviceStats.size() == 0) {
                    sparseArray2 = sparseArray5;
                    sparseArray = sparseArray4;
                    arrayMap = packageStats;
                    j5 = elapsedRealtime;
                    i3 = keyAt;
                    j6 = batteryUptime;
                } else {
                    sparseArray = sparseArray4;
                    arrayMap = packageStats;
                    i3 = keyAt;
                    long start2 = protoOutputStream.start(2246267895810L);
                    protoOutputStream.write(1138166333441L, keyAt2);
                    arrayList2.remove(keyAt2);
                    int size4 = serviceStats.size() - 1;
                    while (size4 >= 0) {
                        android.os.BatteryStats.Uid.Pkg.Serv valueAt2 = serviceStats.valueAt(size4);
                        long j9 = batteryUptime;
                        long roundUsToMs = roundUsToMs(valueAt2.getStartTime(batteryUptime, 0));
                        android.util.SparseArray<? extends android.os.BatteryStats.Uid> sparseArray7 = sparseArray5;
                        int starts = valueAt2.getStarts(0);
                        int launches = valueAt2.getLaunches(0);
                        if (roundUsToMs != 0 || starts != 0 || launches != 0) {
                            j7 = elapsedRealtime;
                            long start3 = protoOutputStream.start(2246267895810L);
                            arrayMap2 = serviceStats;
                            protoOutputStream.write(1138166333441L, serviceStats.keyAt(size4));
                            protoOutputStream.write(1112396529666L, roundUsToMs);
                            protoOutputStream.write(1120986464259L, starts);
                            protoOutputStream.write(1120986464260L, launches);
                            protoOutputStream.end(start3);
                        } else {
                            j7 = elapsedRealtime;
                            arrayMap2 = serviceStats;
                        }
                        size4--;
                        serviceStats = arrayMap2;
                        sparseArray5 = sparseArray7;
                        batteryUptime = j9;
                        elapsedRealtime = j7;
                    }
                    sparseArray2 = sparseArray5;
                    j5 = elapsedRealtime;
                    j6 = batteryUptime;
                    protoOutputStream.end(start2);
                }
                size3--;
                sparseArray5 = sparseArray2;
                packageStats = arrayMap;
                j8 = j;
                keyAt = i3;
                sparseArray4 = sparseArray;
                batteryUptime = j6;
                elapsedRealtime = j5;
            }
            android.util.SparseArray<? extends android.os.BatteryStats.Uid> sparseArray8 = sparseArray5;
            android.util.SparseArray sparseArray9 = sparseArray4;
            android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Pkg> arrayMap3 = packageStats;
            long j10 = elapsedRealtime;
            int i8 = keyAt;
            long j11 = batteryUptime;
            java.util.Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                java.lang.String str = (java.lang.String) it.next();
                long start4 = protoOutputStream.start(2246267895810L);
                protoOutputStream.write(1138166333441L, str);
                protoOutputStream.end(start4);
            }
            if (valueAt.getAggregatedPartialWakelockTimer() == null) {
                j2 = j10;
            } else {
                android.os.BatteryStats.Timer aggregatedPartialWakelockTimer = valueAt.getAggregatedPartialWakelockTimer();
                j2 = j10;
                long totalDurationMsLocked = aggregatedPartialWakelockTimer.getTotalDurationMsLocked(j2);
                android.os.BatteryStats.Timer subTimer = aggregatedPartialWakelockTimer.getSubTimer();
                long totalDurationMsLocked2 = subTimer != null ? subTimer.getTotalDurationMsLocked(j2) : 0L;
                long start5 = protoOutputStream.start(1146756268056L);
                protoOutputStream.write(1112396529665L, totalDurationMsLocked);
                protoOutputStream.write(1112396529666L, totalDurationMsLocked2);
                protoOutputStream.end(start5);
            }
            int i9 = i6;
            android.os.BatteryStats.Uid uid2 = valueAt;
            dumpTimer(protoOutputStream, 1146756268040L, valueAt.getAudioTurnedOnTimer(), j, 0);
            dumpControllerActivityProto(protoOutputStream, 1146756268035L, uid2.getBluetoothControllerActivity(), 0);
            android.os.BatteryStats.Timer bluetoothScanTimer = uid2.getBluetoothScanTimer();
            if (bluetoothScanTimer != null) {
                long start6 = protoOutputStream.start(1146756268038L);
                dumpTimer(protoOutputStream, 1146756268033L, bluetoothScanTimer, j, 0);
                dumpTimer(protoOutputStream, 1146756268034L, uid2.getBluetoothScanBackgroundTimer(), j, 0);
                dumpTimer(protoOutputStream, 1146756268035L, uid2.getBluetoothUnoptimizedScanTimer(), j, 0);
                dumpTimer(protoOutputStream, 1146756268036L, uid2.getBluetoothUnoptimizedScanBackgroundTimer(), j, 0);
                protoOutputStream.write(1120986464261L, uid2.getBluetoothScanResultCounter() != null ? uid2.getBluetoothScanResultCounter().getCountLocked(0) : 0);
                protoOutputStream.write(1120986464262L, uid2.getBluetoothScanResultBgCounter() != null ? uid2.getBluetoothScanResultBgCounter().getCountLocked(0) : 0);
                protoOutputStream.end(start6);
            }
            dumpTimer(protoOutputStream, 1146756268041L, uid2.getCameraTurnedOnTimer(), j, 0);
            long start7 = protoOutputStream.start(1146756268039L);
            protoOutputStream.write(1112396529665L, roundUsToMs(uid2.getUserCpuTimeUs(0)));
            protoOutputStream.write(1112396529666L, roundUsToMs(uid2.getSystemCpuTimeUs(0)));
            com.android.internal.os.CpuScalingPolicies cpuScalingPolicies = getCpuScalingPolicies();
            if (cpuScalingPolicies == null) {
                i = i9;
            } else {
                long[] cpuFreqTimes = uid2.getCpuFreqTimes(0);
                if (cpuFreqTimes == null) {
                    i = i9;
                } else if (cpuFreqTimes.length != cpuScalingPolicies.getScalingStepCount()) {
                    i = i9;
                } else {
                    long[] screenOffCpuFreqTimes = uid2.getScreenOffCpuFreqTimes(0);
                    if (screenOffCpuFreqTimes == null) {
                        screenOffCpuFreqTimes = new long[cpuFreqTimes.length];
                    }
                    int i10 = 0;
                    while (i10 < cpuFreqTimes.length) {
                        long start8 = protoOutputStream.start(2246267895811L);
                        int i11 = i10 + 1;
                        protoOutputStream.write(1120986464257L, i11);
                        protoOutputStream.write(1112396529666L, cpuFreqTimes[i10]);
                        protoOutputStream.write(1112396529667L, screenOffCpuFreqTimes[i10]);
                        protoOutputStream.end(start8);
                        i9 = i9;
                        i10 = i11;
                    }
                    i = i9;
                }
            }
            int scalingStepCount = getCpuScalingPolicies().getScalingStepCount();
            long[] jArr2 = new long[scalingStepCount];
            long[] jArr3 = new long[scalingStepCount];
            int i12 = 0;
            while (true) {
                j3 = 1159641169921L;
                if (i12 >= 7) {
                    break;
                }
                if (!uid2.getCpuFreqTimes(jArr2, i12)) {
                    i2 = scalingStepCount;
                    jArr = jArr2;
                    j4 = j2;
                    uid = uid2;
                } else {
                    if (!uid2.getScreenOffCpuFreqTimes(jArr3, i12)) {
                        java.util.Arrays.fill(jArr3, 0L);
                    }
                    long start9 = protoOutputStream.start(2246267895812L);
                    protoOutputStream.write(1159641169921L, i12);
                    int i13 = 0;
                    while (i13 < scalingStepCount) {
                        long j12 = j2;
                        long start10 = protoOutputStream.start(2246267895810L);
                        int i14 = i13 + 1;
                        protoOutputStream.write(1120986464257L, i14);
                        protoOutputStream.write(1112396529666L, jArr2[i13]);
                        protoOutputStream.write(1112396529667L, jArr3[i13]);
                        protoOutputStream.end(start10);
                        i13 = i14;
                        uid2 = uid2;
                        scalingStepCount = scalingStepCount;
                        j2 = j12;
                        jArr2 = jArr2;
                    }
                    i2 = scalingStepCount;
                    jArr = jArr2;
                    j4 = j2;
                    uid = uid2;
                    protoOutputStream.end(start9);
                }
                i12++;
                uid2 = uid;
                scalingStepCount = i2;
                j2 = j4;
                jArr2 = jArr;
            }
            long j13 = j2;
            android.os.BatteryStats.Uid uid3 = uid2;
            protoOutputStream.end(start7);
            dumpTimer(protoOutputStream, 1146756268042L, uid3.getFlashlightTurnedOnTimer(), j, 0);
            dumpTimer(protoOutputStream, 1146756268043L, uid3.getForegroundActivityTimer(), j, 0);
            dumpTimer(protoOutputStream, 1146756268044L, uid3.getForegroundServiceTimer(), j, 0);
            android.util.ArrayMap<java.lang.String, android.util.SparseIntArray> jobCompletionStats = uid3.getJobCompletionStats();
            int i15 = 0;
            while (i15 < jobCompletionStats.size()) {
                android.util.SparseIntArray valueAt3 = jobCompletionStats.valueAt(i15);
                if (valueAt3 != null) {
                    long start11 = protoOutputStream.start(2246267895824L);
                    protoOutputStream.write(1138166333441L, jobCompletionStats.keyAt(i15));
                    int[] jobStopReasonCodes = android.app.job.JobParameters.getJobStopReasonCodes();
                    int length = jobStopReasonCodes.length;
                    int i16 = 0;
                    while (i16 < length) {
                        int i17 = jobStopReasonCodes[i16];
                        long start12 = protoOutputStream.start(2246267895810L);
                        protoOutputStream.write(j3, i17);
                        protoOutputStream.write(1120986464258L, valueAt3.get(i17, 0));
                        protoOutputStream.end(start12);
                        i16++;
                        j3 = 1159641169921L;
                    }
                    protoOutputStream.end(start11);
                }
                i15++;
                j3 = 1159641169921L;
            }
            android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Timer> jobStats = uid3.getJobStats();
            for (int size5 = jobStats.size() - 1; size5 >= 0; size5--) {
                android.os.BatteryStats.Timer valueAt4 = jobStats.valueAt(size5);
                android.os.BatteryStats.Timer subTimer2 = valueAt4.getSubTimer();
                long start13 = protoOutputStream.start(2246267895823L);
                protoOutputStream.write(1138166333441L, jobStats.keyAt(size5));
                dumpTimer(protoOutputStream, 1146756268034L, valueAt4, j, 0);
                dumpTimer(protoOutputStream, 1146756268035L, subTimer2, j, 0);
                protoOutputStream.end(start13);
            }
            dumpControllerActivityProto(protoOutputStream, 1146756268036L, uid3.getModemControllerActivity(), 0);
            long start14 = protoOutputStream.start(1146756268049L);
            protoOutputStream.write(1112396529665L, uid3.getNetworkActivityBytes(0, 0));
            protoOutputStream.write(1112396529666L, uid3.getNetworkActivityBytes(1, 0));
            protoOutputStream.write(1112396529667L, uid3.getNetworkActivityBytes(2, 0));
            protoOutputStream.write(1112396529668L, uid3.getNetworkActivityBytes(3, 0));
            protoOutputStream.write(1112396529669L, uid3.getNetworkActivityBytes(4, 0));
            protoOutputStream.write(1112396529670L, uid3.getNetworkActivityBytes(5, 0));
            protoOutputStream.write(1112396529671L, uid3.getNetworkActivityPackets(0, 0));
            protoOutputStream.write(1112396529672L, uid3.getNetworkActivityPackets(1, 0));
            protoOutputStream.write(1112396529673L, uid3.getNetworkActivityPackets(2, 0));
            protoOutputStream.write(1112396529674L, uid3.getNetworkActivityPackets(3, 0));
            protoOutputStream.write(1112396529675L, roundUsToMs(uid3.getMobileRadioActiveTime(0)));
            protoOutputStream.write(1120986464268L, uid3.getMobileRadioActiveCount(0));
            protoOutputStream.write(1120986464269L, uid3.getMobileRadioApWakeupCount(0));
            protoOutputStream.write(1120986464270L, uid3.getWifiRadioApWakeupCount(0));
            protoOutputStream.write(1112396529679L, uid3.getNetworkActivityBytes(6, 0));
            protoOutputStream.write(1112396529680L, uid3.getNetworkActivityBytes(7, 0));
            protoOutputStream.write(1112396529681L, uid3.getNetworkActivityBytes(8, 0));
            protoOutputStream.write(1112396529682L, uid3.getNetworkActivityBytes(9, 0));
            protoOutputStream.write(1112396529683L, uid3.getNetworkActivityPackets(6, 0));
            protoOutputStream.write(1112396529684L, uid3.getNetworkActivityPackets(7, 0));
            protoOutputStream.write(1112396529685L, uid3.getNetworkActivityPackets(8, 0));
            protoOutputStream.write(1112396529686L, uid3.getNetworkActivityPackets(9, 0));
            protoOutputStream.end(start14);
            android.os.UidBatteryConsumer uidBatteryConsumer2 = (android.os.UidBatteryConsumer) sparseArray9.get(i8);
            if (uidBatteryConsumer2 != null) {
                long start15 = protoOutputStream.start(1146756268050L);
                protoOutputStream.write(1103806595073L, uidBatteryConsumer2.getConsumedPower());
                proportionalAttributionCalculator2 = proportionalAttributionCalculator;
                protoOutputStream.write(1133871366146L, proportionalAttributionCalculator2.isSystemBatteryConsumer(uidBatteryConsumer2));
                protoOutputStream.write(1103806595075L, uidBatteryConsumer2.getConsumedPower(0));
                protoOutputStream.write(1103806595076L, proportionalAttributionCalculator2.getProportionalPowerMah(uidBatteryConsumer2));
                protoOutputStream.end(start15);
            } else {
                proportionalAttributionCalculator2 = proportionalAttributionCalculator;
            }
            android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Proc> processStats = uid3.getProcessStats();
            for (int size6 = processStats.size() - 1; size6 >= 0; size6--) {
                android.os.BatteryStats.Uid.Proc valueAt5 = processStats.valueAt(size6);
                long start16 = protoOutputStream.start(2246267895827L);
                protoOutputStream.write(1138166333441L, processStats.keyAt(size6));
                protoOutputStream.write(1112396529666L, valueAt5.getUserTime(0));
                protoOutputStream.write(1112396529667L, valueAt5.getSystemTime(0));
                protoOutputStream.write(1112396529668L, valueAt5.getForegroundTime(0));
                protoOutputStream.write(1120986464261L, valueAt5.getStarts(0));
                protoOutputStream.write(1120986464262L, valueAt5.getNumAnrs(0));
                protoOutputStream.write(1120986464263L, valueAt5.getNumCrashes(0));
                protoOutputStream.end(start16);
            }
            android.util.SparseArray<? extends android.os.BatteryStats.Uid.Sensor> sensorStats = uid3.getSensorStats();
            for (int i18 = 0; i18 < sensorStats.size(); i18++) {
                android.os.BatteryStats.Uid.Sensor valueAt6 = sensorStats.valueAt(i18);
                android.os.BatteryStats.Timer sensorTime = valueAt6.getSensorTime();
                if (sensorTime != null) {
                    android.os.BatteryStats.Timer sensorBackgroundTime = valueAt6.getSensorBackgroundTime();
                    int keyAt3 = sensorStats.keyAt(i18);
                    long start17 = protoOutputStream.start(2246267895829L);
                    protoOutputStream.write(1120986464257L, keyAt3);
                    dumpTimer(protoOutputStream, 1146756268034L, sensorTime, j, 0);
                    dumpTimer(protoOutputStream, 1146756268035L, sensorBackgroundTime, j, 0);
                    protoOutputStream.end(start17);
                }
            }
            int i19 = 0;
            while (i19 < 7) {
                long j14 = j;
                long roundUsToMs2 = roundUsToMs(uid3.getProcessStateTime(i19, j14, 0));
                if (roundUsToMs2 != 0) {
                    long start18 = protoOutputStream.start(2246267895828L);
                    protoOutputStream.write(1159641169921L, i19);
                    protoOutputStream.write(1112396529666L, roundUsToMs2);
                    protoOutputStream.end(start18);
                }
                i19++;
                j = j14;
            }
            j8 = j;
            android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Timer> syncStats = uid3.getSyncStats();
            for (int size7 = syncStats.size() - 1; size7 >= 0; size7--) {
                android.os.BatteryStats.Timer valueAt7 = syncStats.valueAt(size7);
                android.os.BatteryStats.Timer subTimer3 = valueAt7.getSubTimer();
                long start19 = protoOutputStream.start(2246267895830L);
                protoOutputStream.write(1138166333441L, syncStats.keyAt(size7));
                dumpTimer(protoOutputStream, 1146756268034L, valueAt7, j8, 0);
                dumpTimer(protoOutputStream, 1146756268035L, subTimer3, j8, 0);
                protoOutputStream.end(start19);
            }
            if (uid3.hasUserActivity()) {
                for (int i20 = 0; i20 < android.os.BatteryStats.Uid.NUM_USER_ACTIVITY_TYPES; i20++) {
                    int userActivityCount = uid3.getUserActivityCount(i20, 0);
                    if (userActivityCount != 0) {
                        long start20 = protoOutputStream.start(2246267895831L);
                        protoOutputStream.write(1159641169921L, i20);
                        protoOutputStream.write(1120986464258L, userActivityCount);
                        protoOutputStream.end(start20);
                    }
                }
            }
            dumpTimer(protoOutputStream, 1146756268045L, uid3.getVibratorOnTimer(), j8, 0);
            dumpTimer(protoOutputStream, 1146756268046L, uid3.getVideoTurnedOnTimer(), j8, 0);
            android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Wakelock> wakelockStats = uid3.getWakelockStats();
            for (int size8 = wakelockStats.size() - 1; size8 >= 0; size8--) {
                android.os.BatteryStats.Uid.Wakelock valueAt8 = wakelockStats.valueAt(size8);
                long start21 = protoOutputStream.start(2246267895833L);
                protoOutputStream.write(1138166333441L, wakelockStats.keyAt(size8));
                dumpTimer(protoOutputStream, 1146756268034L, valueAt8.getWakeTime(1), j8, 0);
                android.os.BatteryStats.Timer wakeTime = valueAt8.getWakeTime(0);
                if (wakeTime != null) {
                    dumpTimer(protoOutputStream, 1146756268035L, wakeTime, j8, 0);
                    dumpTimer(protoOutputStream, 1146756268036L, wakeTime.getSubTimer(), j8, 0);
                }
                dumpTimer(protoOutputStream, 1146756268037L, valueAt8.getWakeTime(2), j8, 0);
                protoOutputStream.end(start21);
            }
            dumpTimer(protoOutputStream, 1146756268060L, uid3.getMulticastWakelockStats(), j8, 0);
            int i21 = 1;
            int size9 = arrayMap3.size() - 1;
            while (size9 >= 0) {
                android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Pkg> arrayMap4 = arrayMap3;
                android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Counter> wakeupAlarmStats = arrayMap4.valueAt(size9).getWakeupAlarmStats();
                for (int size10 = wakeupAlarmStats.size() - i21; size10 >= 0; size10--) {
                    long start22 = protoOutputStream.start(2246267895834L);
                    protoOutputStream.write(1138166333441L, wakeupAlarmStats.keyAt(size10));
                    protoOutputStream.write(1120986464258L, wakeupAlarmStats.valueAt(size10).getCountLocked(0));
                    protoOutputStream.end(start22);
                }
                size9--;
                arrayMap3 = arrayMap4;
                i21 = 1;
            }
            dumpControllerActivityProto(protoOutputStream, 1146756268037L, uid3.getWifiControllerActivity(), 0);
            long start23 = protoOutputStream.start(1146756268059L);
            protoOutputStream.write(1112396529665L, roundUsToMs(uid3.getFullWifiLockTime(j8, 0)));
            dumpTimer(protoOutputStream, 1146756268035L, uid3.getWifiScanTimer(), j8, 0);
            protoOutputStream.write(1112396529666L, roundUsToMs(uid3.getWifiRunningTime(j8, 0)));
            dumpTimer(protoOutputStream, 1146756268036L, uid3.getWifiScanBackgroundTimer(), j8, 0);
            protoOutputStream.end(start23);
            protoOutputStream.end(start);
            i5 = i7 + 1;
            size2 = i;
            sparseArray4 = sparseArray9;
            uidStats = sparseArray8;
            sparseArray3 = sparseArray6;
            batteryUptime = j11;
            elapsedRealtime = j13;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00f5 A[Catch: all -> 0x0220, TryCatch #0 {all -> 0x0220, blocks: (B:8:0x0077, B:10:0x007d, B:13:0x0088, B:23:0x0096, B:25:0x009b, B:27:0x00a0, B:29:0x00a5, B:32:0x00dd, B:35:0x00e6, B:37:0x00f5, B:39:0x00f9, B:42:0x0101, B:44:0x010c, B:47:0x011d, B:51:0x01ac, B:52:0x012d, B:53:0x0135, B:55:0x013b, B:56:0x014b, B:58:0x0151, B:62:0x0177, B:71:0x01b6, B:72:0x01cc, B:75:0x01d4, B:80:0x00b0, B:82:0x00b9, B:85:0x00c5, B:94:0x01f9), top: B:7:0x0077 }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01be  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void dumpProtoHistoryLocked(android.util.proto.ProtoOutputStream protoOutputStream, int i, long j) {
        long j2;
        long j3;
        boolean z;
        android.os.BatteryStats.HistoryEventTracker historyEventTracker;
        int i2;
        android.os.BatteryStats.HistoryEventTracker historyEventTracker2;
        android.os.BatteryStats.HistoryTag historyTag;
        protoOutputStream.write(1120986464257L, 36);
        protoOutputStream.write(1112396529666L, getParcelVersion());
        protoOutputStream.write(1138166333443L, getStartPlatformVersion());
        protoOutputStream.write(1138166333444L, getEndPlatformVersion());
        byte b = 0;
        for (int i3 = 0; i3 < getHistoryStringPoolSize(); i3++) {
            long start = protoOutputStream.start(2246267895813L);
            protoOutputStream.write(1120986464257L, i3);
            protoOutputStream.write(1120986464258L, getHistoryTagPoolUid(i3));
            protoOutputStream.write(1138166333443L, getHistoryTagPoolString(i3));
            protoOutputStream.end(start);
        }
        android.os.BatteryStats.HistoryPrinter historyPrinter = new android.os.BatteryStats.HistoryPrinter();
        long j4 = 0;
        long j5 = -1;
        com.android.internal.os.BatteryStatsHistoryIterator iterateBatteryStatsHistory = iterateBatteryStatsHistory(0L, -1L);
        long j6 = -1;
        boolean z2 = false;
        android.os.BatteryStats.HistoryEventTracker historyEventTracker3 = null;
        while (true) {
            try {
                android.os.BatteryStats.HistoryItem next = iterateBatteryStatsHistory.next();
                if (next == null) {
                    break;
                }
                j6 = next.time;
                if (j5 >= j4) {
                    j2 = j5;
                } else {
                    j2 = j6;
                }
                if (next.time >= j) {
                    if (j < j4 || z2) {
                        j3 = j6;
                        z = z2;
                        historyEventTracker3 = historyEventTracker3;
                    } else {
                        if (next.cmd == 5 || next.cmd == 7 || next.cmd == 4) {
                            j3 = j6;
                            historyEventTracker = historyEventTracker3;
                        } else if (next.cmd == 8) {
                            j3 = j6;
                            historyEventTracker = historyEventTracker3;
                        } else {
                            historyEventTracker = historyEventTracker3;
                            if (next.currentTime == j4) {
                                j3 = j6;
                                z = z2;
                            } else {
                                byte b2 = next.cmd;
                                next.cmd = (byte) 5;
                                j3 = j6;
                                historyPrinter.printNextItem(protoOutputStream, next, j2, (i & 32) != 0 ? 1 : b);
                                next.cmd = b2;
                                z = true;
                            }
                            if (historyEventTracker != null) {
                                historyEventTracker3 = historyEventTracker;
                            } else {
                                if (next.cmd != 0) {
                                    historyPrinter.printNextItem(protoOutputStream, next, j2, (i & 32) != 0 ? 1 : b);
                                    next.cmd = b;
                                }
                                int i4 = next.eventCode;
                                android.os.BatteryStats.HistoryTag historyTag2 = next.eventTag;
                                next.eventTag = new android.os.BatteryStats.HistoryTag();
                                int i5 = b;
                                while (i5 < 22) {
                                    android.os.BatteryStats.HistoryEventTracker historyEventTracker4 = historyEventTracker;
                                    java.util.HashMap<java.lang.String, android.util.SparseIntArray> stateForEvent = historyEventTracker4.getStateForEvent(i5);
                                    if (stateForEvent == null) {
                                        i2 = i5;
                                        historyEventTracker2 = historyEventTracker4;
                                        historyTag = historyTag2;
                                    } else {
                                        for (java.util.Map.Entry<java.lang.String, android.util.SparseIntArray> entry : stateForEvent.entrySet()) {
                                            android.util.SparseIntArray value = entry.getValue();
                                            int i6 = b;
                                            while (i6 < value.size()) {
                                                next.eventCode = i5;
                                                next.eventTag.string = entry.getKey();
                                                next.eventTag.uid = value.keyAt(i6);
                                                next.eventTag.poolIdx = value.valueAt(i6);
                                                historyPrinter.printNextItem(protoOutputStream, next, j2, (i & 32) != 0);
                                                next.wakeReasonTag = null;
                                                next.wakelockTag = null;
                                                i6++;
                                                historyTag2 = historyTag2;
                                                value = value;
                                                i5 = i5;
                                                historyEventTracker4 = historyEventTracker4;
                                            }
                                            b = 0;
                                        }
                                        i2 = i5;
                                        historyEventTracker2 = historyEventTracker4;
                                        historyTag = historyTag2;
                                    }
                                    i5 = i2 + 1;
                                    historyTag2 = historyTag;
                                    historyEventTracker = historyEventTracker2;
                                    b = 0;
                                }
                                next.eventCode = i4;
                                next.eventTag = historyTag2;
                                historyEventTracker3 = null;
                            }
                        }
                        historyPrinter.printNextItem(protoOutputStream, next, j2, (i & 32) != 0 ? 1 : b);
                        next.cmd = b;
                        z = true;
                        if (historyEventTracker != null) {
                        }
                    }
                    historyPrinter.printNextItem(protoOutputStream, next, j2, (i & 32) != 0);
                    z2 = z;
                    j5 = j2;
                    j6 = j3;
                    b = 0;
                    j4 = 0;
                } else {
                    j5 = j2;
                    b = 0;
                    j4 = 0;
                }
            } finally {
            }
        }
        if (j >= 0) {
            commitCurrentHistoryBatchLocked();
            protoOutputStream.write(2237677961222L, "NEXT: " + (j6 + 1));
        }
        if (iterateBatteryStatsHistory != null) {
            iterateBatteryStatsHistory.close();
        }
    }

    private void dumpProtoSystemLocked(android.util.proto.ProtoOutputStream protoOutputStream, android.os.BatteryUsageStats batteryUsageStats) {
        int i;
        int i2;
        long start = protoOutputStream.start(1146756268038L);
        long uptimeMillis = android.os.SystemClock.uptimeMillis() * 1000;
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime() * 1000;
        long start2 = protoOutputStream.start(1146756268033L);
        protoOutputStream.write(1112396529665L, getStartClockTime());
        protoOutputStream.write(1112396529666L, getStartCount());
        protoOutputStream.write(1112396529667L, computeRealtime(elapsedRealtime, 0) / 1000);
        protoOutputStream.write(1112396529668L, computeUptime(uptimeMillis, 0) / 1000);
        protoOutputStream.write(1112396529669L, computeBatteryRealtime(elapsedRealtime, 0) / 1000);
        protoOutputStream.write(1112396529670L, computeBatteryUptime(uptimeMillis, 0) / 1000);
        protoOutputStream.write(1112396529671L, computeBatteryScreenOffRealtime(elapsedRealtime, 0) / 1000);
        protoOutputStream.write(1112396529672L, computeBatteryScreenOffUptime(uptimeMillis, 0) / 1000);
        protoOutputStream.write(1112396529673L, getScreenDozeTime(elapsedRealtime, 0) / 1000);
        protoOutputStream.write(1112396529674L, getEstimatedBatteryCapacity());
        protoOutputStream.write(1112396529675L, getMinLearnedBatteryCapacity());
        protoOutputStream.write(1112396529676L, getMaxLearnedBatteryCapacity());
        protoOutputStream.end(start2);
        long start3 = protoOutputStream.start(1146756268034L);
        protoOutputStream.write(1120986464257L, getLowDischargeAmountSinceCharge());
        protoOutputStream.write(1120986464258L, getHighDischargeAmountSinceCharge());
        protoOutputStream.write(1120986464259L, getDischargeAmountScreenOnSinceCharge());
        protoOutputStream.write(1120986464260L, getDischargeAmountScreenOffSinceCharge());
        protoOutputStream.write(1120986464261L, getDischargeAmountScreenDozeSinceCharge());
        protoOutputStream.write(1112396529670L, getUahDischarge(0) / 1000);
        protoOutputStream.write(1112396529671L, getUahDischargeScreenOff(0) / 1000);
        protoOutputStream.write(1112396529672L, getUahDischargeScreenDoze(0) / 1000);
        protoOutputStream.write(1112396529673L, getUahDischargeLightDoze(0) / 1000);
        protoOutputStream.write(1112396529674L, getUahDischargeDeepDoze(0) / 1000);
        protoOutputStream.end(start3);
        long computeChargeTimeRemaining = computeChargeTimeRemaining(elapsedRealtime);
        long j = 0;
        if (computeChargeTimeRemaining < 0) {
            long computeBatteryTimeRemaining = computeBatteryTimeRemaining(elapsedRealtime);
            if (computeBatteryTimeRemaining >= 0) {
                protoOutputStream.write(1112396529668L, computeBatteryTimeRemaining / 1000);
            } else {
                protoOutputStream.write(1112396529668L, -1);
            }
        } else {
            protoOutputStream.write(1112396529667L, computeChargeTimeRemaining / 1000);
        }
        dumpDurationSteps(protoOutputStream, 2246267895813L, getChargeLevelStepTracker());
        int i3 = 0;
        while (i3 < NUM_DATA_CONNECTION_TYPES) {
            boolean z = i3 == 0;
            int i4 = (i3 == DATA_CONNECTION_OTHER || i3 == DATA_CONNECTION_EMERGENCY_SERVICE) ? 0 : i3;
            long start4 = protoOutputStream.start(2246267895816L);
            if (z) {
                protoOutputStream.write(1133871366146L, z);
            } else {
                protoOutputStream.write(1159641169921L, i4);
            }
            dumpTimer(protoOutputStream, 1146756268035L, getPhoneDataConnectionTimer(i3), elapsedRealtime, 0);
            protoOutputStream.end(start4);
            i3++;
        }
        dumpDurationSteps(protoOutputStream, 2246267895814L, getDischargeLevelStepTracker());
        com.android.internal.os.CpuScalingPolicies cpuScalingPolicies = getCpuScalingPolicies();
        if (cpuScalingPolicies != null) {
            for (int i5 : cpuScalingPolicies.getPolicies()) {
                for (int i6 : cpuScalingPolicies.getFrequencies(i5)) {
                    protoOutputStream.write(android.os.SystemProto.CPU_FREQUENCY, i6);
                }
            }
        }
        dumpControllerActivityProto(protoOutputStream, 1146756268041L, getBluetoothControllerActivity(), 0);
        dumpControllerActivityProto(protoOutputStream, 1146756268042L, getModemControllerActivity(), 0);
        long start5 = protoOutputStream.start(1146756268044L);
        protoOutputStream.write(1112396529665L, getNetworkActivityBytes(0, 0));
        int i7 = 1;
        protoOutputStream.write(1112396529666L, getNetworkActivityBytes(1, 0));
        protoOutputStream.write(1112396529669L, getNetworkActivityPackets(0, 0));
        protoOutputStream.write(1112396529670L, getNetworkActivityPackets(1, 0));
        protoOutputStream.write(1112396529667L, getNetworkActivityBytes(2, 0));
        protoOutputStream.write(1112396529668L, getNetworkActivityBytes(3, 0));
        protoOutputStream.write(1112396529671L, getNetworkActivityPackets(2, 0));
        protoOutputStream.write(1112396529672L, getNetworkActivityPackets(3, 0));
        protoOutputStream.write(1112396529673L, getNetworkActivityBytes(4, 0));
        protoOutputStream.write(1112396529674L, getNetworkActivityBytes(5, 0));
        protoOutputStream.end(start5);
        dumpControllerActivityProto(protoOutputStream, 1146756268043L, getWifiControllerActivity(), 0);
        long start6 = protoOutputStream.start(1146756268045L);
        protoOutputStream.write(1112396529665L, getWifiOnTime(elapsedRealtime, 0) / 1000);
        protoOutputStream.write(1112396529666L, getGlobalWifiRunningTime(elapsedRealtime, 0) / 1000);
        protoOutputStream.end(start6);
        for (java.util.Map.Entry<java.lang.String, ? extends android.os.BatteryStats.Timer> entry : getKernelWakelockStats().entrySet()) {
            long start7 = protoOutputStream.start(2246267895822L);
            protoOutputStream.write(1138166333441L, entry.getKey());
            dumpTimer(protoOutputStream, 1146756268034L, entry.getValue(), elapsedRealtime, 0);
            protoOutputStream.end(start7);
            i7 = i7;
        }
        int i8 = i7;
        android.util.SparseArray<? extends android.os.BatteryStats.Uid> uidStats = getUidStats();
        long j2 = 0;
        for (int i9 = 0; i9 < uidStats.size(); i9++) {
            android.util.ArrayMap<java.lang.String, ? extends android.os.BatteryStats.Uid.Wakelock> wakelockStats = uidStats.valueAt(i9).getWakelockStats();
            for (int size = wakelockStats.size() - i8; size >= 0; size--) {
                android.os.BatteryStats.Uid.Wakelock valueAt = wakelockStats.valueAt(size);
                android.os.BatteryStats.Timer wakeTime = valueAt.getWakeTime(i8);
                if (wakeTime == null) {
                    i2 = 0;
                } else {
                    i2 = 0;
                    j += wakeTime.getTotalTimeLocked(elapsedRealtime, 0);
                }
                android.os.BatteryStats.Timer wakeTime2 = valueAt.getWakeTime(i2);
                if (wakeTime2 != null) {
                    j2 += wakeTime2.getTotalTimeLocked(elapsedRealtime, i2);
                }
            }
        }
        long start8 = protoOutputStream.start(1146756268047L);
        protoOutputStream.write(1112396529665L, getScreenOnTime(elapsedRealtime, 0) / 1000);
        protoOutputStream.write(1112396529666L, getPhoneOnTime(elapsedRealtime, 0) / 1000);
        protoOutputStream.write(1112396529667L, j / 1000);
        protoOutputStream.write(1112396529668L, j2 / 1000);
        protoOutputStream.write(1112396529669L, getMobileRadioActiveTime(elapsedRealtime, 0) / 1000);
        protoOutputStream.write(1112396529670L, getMobileRadioActiveAdjustedTime(0) / 1000);
        protoOutputStream.write(1120986464263L, getMobileRadioActiveCount(0));
        protoOutputStream.write(1120986464264L, getMobileRadioActiveUnknownTime(0) / 1000);
        protoOutputStream.write(1112396529673L, getInteractiveTime(elapsedRealtime, 0) / 1000);
        protoOutputStream.write(1112396529674L, getPowerSaveModeEnabledTime(elapsedRealtime, 0) / 1000);
        protoOutputStream.write(1120986464267L, getNumConnectivityChange(0));
        int i10 = 2;
        protoOutputStream.write(1112396529676L, getDeviceIdleModeTime(2, elapsedRealtime, 0) / 1000);
        protoOutputStream.write(1120986464269L, getDeviceIdleModeCount(2, 0));
        protoOutputStream.write(1112396529678L, getDeviceIdlingTime(2, elapsedRealtime, 0) / 1000);
        protoOutputStream.write(1120986464271L, getDeviceIdlingCount(2, 0));
        protoOutputStream.write(1112396529680L, getLongestDeviceIdleModeTime(2));
        protoOutputStream.write(1112396529681L, getDeviceIdleModeTime(i8, elapsedRealtime, 0) / 1000);
        protoOutputStream.write(1120986464274L, getDeviceIdleModeCount(i8, 0));
        protoOutputStream.write(1112396529683L, getDeviceIdlingTime(i8, elapsedRealtime, 0) / 1000);
        protoOutputStream.write(1120986464276L, getDeviceIdlingCount(i8, 0));
        protoOutputStream.write(1112396529685L, getLongestDeviceIdleModeTime(i8));
        protoOutputStream.end(start8);
        long wifiMulticastWakelockTime = getWifiMulticastWakelockTime(elapsedRealtime, 0);
        int wifiMulticastWakelockCount = getWifiMulticastWakelockCount(0);
        long start9 = protoOutputStream.start(1146756268055L);
        protoOutputStream.write(1112396529665L, wifiMulticastWakelockTime / 1000);
        long j3 = 1120986464258L;
        protoOutputStream.write(1120986464258L, wifiMulticastWakelockCount);
        protoOutputStream.end(start9);
        android.os.AggregateBatteryConsumer aggregateBatteryConsumer = batteryUsageStats.getAggregateBatteryConsumer(0);
        int i11 = 0;
        while (i11 < 18) {
            switch (i11) {
                case 0:
                    i = 7;
                    break;
                case 1:
                case 4:
                case 5:
                case 7:
                case 9:
                case 10:
                case 12:
                default:
                    i = 0;
                    break;
                case 2:
                    i = 5;
                    break;
                case 3:
                    i = 11;
                    break;
                case 6:
                    i = 6;
                    break;
                case 8:
                    i = i10;
                    break;
                case 11:
                    i = 4;
                    break;
                case 13:
                    i = 12;
                    break;
                case 14:
                    i = 3;
                    break;
                case 15:
                    i = 13;
                    break;
                case 16:
                    i = i8;
                    break;
            }
            long start10 = protoOutputStream.start(2246267895825L);
            protoOutputStream.write(1159641169921L, i);
            protoOutputStream.write(j3, 0);
            protoOutputStream.write(1103806595075L, aggregateBatteryConsumer.getConsumedPower(i11));
            protoOutputStream.write(1133871366148L, shouldHidePowerComponent(i11));
            protoOutputStream.write(1103806595077L, 0);
            protoOutputStream.write(1103806595078L, 0);
            protoOutputStream.end(start10);
            i11++;
            j3 = 1120986464258L;
            i10 = 2;
        }
        int i12 = 0;
        long start11 = protoOutputStream.start(1146756268050L);
        protoOutputStream.write(1103806595073L, batteryUsageStats.getBatteryCapacity());
        protoOutputStream.write(1103806595074L, batteryUsageStats.getConsumedPower());
        protoOutputStream.write(1103806595075L, batteryUsageStats.getDischargedPowerRange().getLower().doubleValue());
        protoOutputStream.write(1103806595076L, batteryUsageStats.getDischargedPowerRange().getUpper().doubleValue());
        protoOutputStream.end(start11);
        java.util.Map<java.lang.String, ? extends android.os.BatteryStats.Timer> rpmStats = getRpmStats();
        java.util.Map<java.lang.String, ? extends android.os.BatteryStats.Timer> screenOffRpmStats = getScreenOffRpmStats();
        for (java.util.Map.Entry<java.lang.String, ? extends android.os.BatteryStats.Timer> entry2 : rpmStats.entrySet()) {
            long start12 = protoOutputStream.start(2246267895827L);
            protoOutputStream.write(1138166333441L, entry2.getKey());
            dumpTimer(protoOutputStream, 1146756268034L, entry2.getValue(), elapsedRealtime, 0);
            dumpTimer(protoOutputStream, 1146756268035L, screenOffRpmStats.get(entry2.getKey()), elapsedRealtime, 0);
            protoOutputStream.end(start12);
            i12 = i12;
        }
        int i13 = i12;
        for (int i14 = i13; i14 < 5; i14++) {
            long start13 = protoOutputStream.start(2246267895828L);
            protoOutputStream.write(1159641169921L, i14);
            dumpTimer(protoOutputStream, 1146756268034L, getScreenBrightnessTimer(i14), elapsedRealtime, 0);
            protoOutputStream.end(start13);
        }
        dumpTimer(protoOutputStream, 1146756268053L, getPhoneSignalScanningTimer(), elapsedRealtime, 0);
        for (int i15 = i13; i15 < android.telephony.CellSignalStrength.getNumSignalStrengthLevels(); i15++) {
            long start14 = protoOutputStream.start(2246267895824L);
            protoOutputStream.write(1159641169921L, i15);
            dumpTimer(protoOutputStream, 1146756268034L, getPhoneSignalStrengthTimer(i15), elapsedRealtime, 0);
            protoOutputStream.end(start14);
        }
        for (java.util.Map.Entry<java.lang.String, ? extends android.os.BatteryStats.Timer> entry3 : getWakeupReasonStats().entrySet()) {
            long start15 = protoOutputStream.start(2246267895830L);
            protoOutputStream.write(1138166333441L, entry3.getKey());
            dumpTimer(protoOutputStream, 1146756268034L, entry3.getValue(), elapsedRealtime, 0);
            protoOutputStream.end(start15);
        }
        for (int i16 = i13; i16 < 5; i16++) {
            long start16 = protoOutputStream.start(2246267895832L);
            protoOutputStream.write(1159641169921L, i16);
            dumpTimer(protoOutputStream, 1146756268034L, getWifiSignalStrengthTimer(i16), elapsedRealtime, 0);
            protoOutputStream.end(start16);
        }
        for (int i17 = i13; i17 < 8; i17++) {
            long start17 = protoOutputStream.start(2246267895833L);
            protoOutputStream.write(1159641169921L, i17);
            dumpTimer(protoOutputStream, 1146756268034L, getWifiStateTimer(i17), elapsedRealtime, 0);
            protoOutputStream.end(start17);
        }
        for (int i18 = i13; i18 < 13; i18++) {
            long start18 = protoOutputStream.start(2246267895834L);
            protoOutputStream.write(1159641169921L, i18);
            dumpTimer(protoOutputStream, 1146756268034L, getWifiSupplStateTimer(i18), elapsedRealtime, 0);
            protoOutputStream.end(start18);
        }
        protoOutputStream.end(start);
    }

    public static boolean checkWifiOnly(android.content.Context context) {
        if (((android.telephony.TelephonyManager) context.getSystemService(android.telephony.TelephonyManager.class)) == null) {
            return false;
        }
        return !r1.isDataCapable();
    }

    private boolean shouldHidePowerComponent(int i) {
        return i == 16 || i == 8 || i == 0 || i == 15;
    }

    private static class ProportionalAttributionCalculator {
        private static final double SYSTEM_BATTERY_CONSUMER = -1.0d;
        private final android.content.pm.PackageManager mPackageManager;
        private final android.util.SparseDoubleArray mProportionalPowerMah;
        private final java.util.HashSet<java.lang.String> mSystemAndServicePackages;

        ProportionalAttributionCalculator(android.content.Context context, android.os.BatteryUsageStats batteryUsageStats) {
            this.mPackageManager = context.getPackageManager();
            android.content.res.Resources resources = context.getResources();
            java.lang.String[] stringArray = resources.getStringArray(com.android.internal.R.array.config_batteryPackageTypeSystem);
            java.lang.String[] stringArray2 = resources.getStringArray(com.android.internal.R.array.config_batteryPackageTypeService);
            this.mSystemAndServicePackages = new java.util.HashSet<>(stringArray.length + stringArray2.length);
            for (java.lang.String str : stringArray) {
                this.mSystemAndServicePackages.add(str);
            }
            for (java.lang.String str2 : stringArray2) {
                this.mSystemAndServicePackages.add(str2);
            }
            java.util.List<android.os.UidBatteryConsumer> uidBatteryConsumers = batteryUsageStats.getUidBatteryConsumers();
            this.mProportionalPowerMah = new android.util.SparseDoubleArray(uidBatteryConsumers.size());
            double d = 0.0d;
            for (int size = uidBatteryConsumers.size() - 1; size >= 0; size--) {
                android.os.UidBatteryConsumer uidBatteryConsumer = uidBatteryConsumers.get(size);
                int uid = uidBatteryConsumer.getUid();
                if (isSystemUid(uid)) {
                    this.mProportionalPowerMah.put(uid, -1.0d);
                    d += uidBatteryConsumer.getConsumedPower();
                }
            }
            double consumedPower = batteryUsageStats.getConsumedPower() - d;
            if (java.lang.Math.abs(consumedPower) > 0.001d) {
                for (int size2 = uidBatteryConsumers.size() - 1; size2 >= 0; size2--) {
                    android.os.UidBatteryConsumer uidBatteryConsumer2 = uidBatteryConsumers.get(size2);
                    int uid2 = uidBatteryConsumer2.getUid();
                    if (this.mProportionalPowerMah.get(uid2) != -1.0d) {
                        double consumedPower2 = uidBatteryConsumer2.getConsumedPower();
                        this.mProportionalPowerMah.put(uid2, consumedPower2 + ((d * consumedPower2) / consumedPower));
                    }
                }
            }
        }

        boolean isSystemBatteryConsumer(android.os.UidBatteryConsumer uidBatteryConsumer) {
            return this.mProportionalPowerMah.get(uidBatteryConsumer.getUid()) < 0.0d;
        }

        double getProportionalPowerMah(android.os.UidBatteryConsumer uidBatteryConsumer) {
            double d = this.mProportionalPowerMah.get(uidBatteryConsumer.getUid());
            if (d >= 0.0d) {
                return d;
            }
            return 0.0d;
        }

        private boolean isSystemUid(int i) {
            if (i >= 0 && i < 10000) {
                return true;
            }
            java.lang.String[] packagesForUid = this.mPackageManager.getPackagesForUid(i);
            if (packagesForUid == null) {
                return false;
            }
            for (java.lang.String str : packagesForUid) {
                if (this.mSystemAndServicePackages.contains(str)) {
                    return true;
                }
            }
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class UidMobileRadioStats {
        public final double millisecondsPerPacket;
        public final int radioActiveCount;
        public final long radioActiveMs;
        public final long rxPackets;
        public final long txPackets;
        public final int uid;

        private UidMobileRadioStats(int i, long j, long j2, long j3, int i2, double d) {
            this.uid = i;
            this.txPackets = j2;
            this.rxPackets = j;
            this.radioActiveMs = j3;
            this.radioActiveCount = i2;
            this.millisecondsPerPacket = d;
        }
    }

    private java.util.List<android.os.BatteryStats.UidMobileRadioStats> getUidMobileRadioStats(java.util.List<android.os.UidBatteryConsumer> list) {
        int i;
        android.util.SparseArray<? extends android.os.BatteryStats.Uid> uidStats = getUidStats();
        java.util.ArrayList newArrayList = com.google.android.collect.Lists.newArrayList();
        int i2 = 0;
        int i3 = 0;
        while (i3 < list.size()) {
            android.os.UidBatteryConsumer uidBatteryConsumer = list.get(i3);
            if (uidBatteryConsumer.getConsumedPower(8) == 0.0d) {
                i = i3;
            } else {
                int uid = uidBatteryConsumer.getUid();
                android.os.BatteryStats.Uid uid2 = uidStats.get(uid);
                long networkActivityPackets = uid2.getNetworkActivityPackets(i2, i2);
                long networkActivityPackets2 = uid2.getNetworkActivityPackets(1, i2);
                if (networkActivityPackets == 0 && networkActivityPackets2 == 0) {
                    i = i3;
                } else {
                    long mobileRadioActiveTime = uid2.getMobileRadioActiveTime(i2) / 1000;
                    int mobileRadioActiveCount = uid2.getMobileRadioActiveCount(i2);
                    i = i3;
                    double d = mobileRadioActiveTime / (networkActivityPackets + networkActivityPackets2);
                    if (d != 0.0d) {
                        newArrayList.add(new android.os.BatteryStats.UidMobileRadioStats(uid, networkActivityPackets, networkActivityPackets2, mobileRadioActiveTime, mobileRadioActiveCount, d));
                    }
                }
            }
            i3 = i + 1;
            i2 = 0;
        }
        newArrayList.sort(new java.util.Comparator() { // from class: android.os.BatteryStats$$ExternalSyntheticLambda2
            @Override // java.util.Comparator
            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                int compare;
                compare = java.lang.Double.compare(((android.os.BatteryStats.UidMobileRadioStats) obj2).millisecondsPerPacket, ((android.os.BatteryStats.UidMobileRadioStats) obj).millisecondsPerPacket);
                return compare;
            }
        });
        return newArrayList;
    }

    protected static boolean isLowRamDevice() {
        return android.app.ActivityManager.isLowRamDeviceStatic();
    }

    protected static boolean isLowRamDevice$ravenwood() {
        return false;
    }

    protected static int getCellSignalStrengthLevelCount() {
        return android.telephony.CellSignalStrength.getNumSignalStrengthLevels();
    }

    protected static int getCellSignalStrengthLevelCount$ravenwood() {
        return 5;
    }

    protected static int getModemTxPowerLevelCount() {
        return android.telephony.ModemActivityInfo.getNumTxPowerLevels();
    }

    protected static int getModemTxPowerLevelCount$ravenwood() {
        return 5;
    }

    protected static boolean isKernelStatsAvailable() {
        return true;
    }

    protected static boolean isKernelStatsAvailable$ravenwood() {
        return false;
    }

    protected static int getDisplayTransport(int[] iArr) {
        return com.android.net.module.util.NetworkCapabilitiesUtils.getDisplayTransport(iArr);
    }

    protected static int getDisplayTransport$ravenwood(int[] iArr) {
        for (int i : DISPLAY_TRANSPORT_PRIORITIES) {
            for (int i2 : iArr) {
                if (i2 == i) {
                    return i;
                }
            }
        }
        return iArr[0];
    }
}
