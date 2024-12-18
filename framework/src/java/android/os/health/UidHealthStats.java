package android.os.health;

/* loaded from: classes3.dex */
public final class UidHealthStats {
    public static final android.os.health.HealthKeys.Constants CONSTANTS = new android.os.health.HealthKeys.Constants(android.os.health.UidHealthStats.class);

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_BLUETOOTH_IDLE_MS = 10020;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_BLUETOOTH_POWER_MAMS = 10023;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_BLUETOOTH_RX_BYTES = 10052;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_BLUETOOTH_RX_MS = 10021;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_BLUETOOTH_RX_PACKETS = 10058;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_BLUETOOTH_TX_BYTES = 10053;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_BLUETOOTH_TX_MS = 10022;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_BLUETOOTH_TX_PACKETS = 10059;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_BUTTON_USER_ACTIVITY_COUNT = 10046;

    @android.os.health.HealthKeys.Constant(type = 1)
    @java.lang.Deprecated
    public static final int MEASUREMENT_CPU_POWER_MAMS = 10064;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_MOBILE_IDLE_MS = 10024;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_MOBILE_POWER_MAMS = 10027;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_MOBILE_RX_BYTES = 10048;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_MOBILE_RX_MS = 10025;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_MOBILE_RX_PACKETS = 10054;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_MOBILE_TX_BYTES = 10049;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_MOBILE_TX_MS = 10026;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_MOBILE_TX_PACKETS = 10055;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_OTHER_USER_ACTIVITY_COUNT = 10045;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_REALTIME_BATTERY_MS = 10001;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_REALTIME_SCREEN_OFF_BATTERY_MS = 10003;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_SYSTEM_CPU_TIME_MS = 10063;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_TOUCH_USER_ACTIVITY_COUNT = 10047;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_UPTIME_BATTERY_MS = 10002;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_UPTIME_SCREEN_OFF_BATTERY_MS = 10004;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_USER_CPU_TIME_MS = 10062;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_WIFI_FULL_LOCK_MS = 10029;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_WIFI_IDLE_MS = 10016;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_WIFI_MULTICAST_MS = 10031;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_WIFI_POWER_MAMS = 10019;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_WIFI_RUNNING_MS = 10028;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_WIFI_RX_BYTES = 10050;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_WIFI_RX_MS = 10017;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_WIFI_RX_PACKETS = 10056;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_WIFI_TX_BYTES = 10051;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_WIFI_TX_MS = 10018;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_WIFI_TX_PACKETS = 10057;

    @android.os.health.HealthKeys.Constant(type = 2)
    public static final int STATS_PACKAGES = 10015;

    @android.os.health.HealthKeys.Constant(type = 2)
    public static final int STATS_PIDS = 10013;

    @android.os.health.HealthKeys.Constant(type = 2)
    public static final int STATS_PROCESSES = 10014;

    @android.os.health.HealthKeys.Constant(type = 3)
    public static final int TIMERS_JOBS = 10010;

    @android.os.health.HealthKeys.Constant(type = 3)
    public static final int TIMERS_SENSORS = 10012;

    @android.os.health.HealthKeys.Constant(type = 3)
    public static final int TIMERS_SYNCS = 10009;

    @android.os.health.HealthKeys.Constant(type = 3)
    public static final int TIMERS_WAKELOCKS_DRAW = 10008;

    @android.os.health.HealthKeys.Constant(type = 3)
    public static final int TIMERS_WAKELOCKS_FULL = 10005;

    @android.os.health.HealthKeys.Constant(type = 3)
    public static final int TIMERS_WAKELOCKS_PARTIAL = 10006;

    @android.os.health.HealthKeys.Constant(type = 3)
    public static final int TIMERS_WAKELOCKS_WINDOW = 10007;

    @android.os.health.HealthKeys.Constant(type = 0)
    public static final int TIMER_AUDIO = 10032;

    @android.os.health.HealthKeys.Constant(type = 0)
    public static final int TIMER_BLUETOOTH_SCAN = 10037;

    @android.os.health.HealthKeys.Constant(type = 0)
    public static final int TIMER_CAMERA = 10035;

    @android.os.health.HealthKeys.Constant(type = 0)
    public static final int TIMER_FLASHLIGHT = 10034;

    @android.os.health.HealthKeys.Constant(type = 0)
    public static final int TIMER_FOREGROUND_ACTIVITY = 10036;

    @android.os.health.HealthKeys.Constant(type = 0)
    public static final int TIMER_GPS_SENSOR = 10011;

    @android.os.health.HealthKeys.Constant(type = 0)
    public static final int TIMER_MOBILE_RADIO_ACTIVE = 10061;

    @android.os.health.HealthKeys.Constant(type = 0)
    public static final int TIMER_PROCESS_STATE_BACKGROUND_MS = 10042;

    @android.os.health.HealthKeys.Constant(type = 0)
    public static final int TIMER_PROCESS_STATE_CACHED_MS = 10043;

    @android.os.health.HealthKeys.Constant(type = 0)
    public static final int TIMER_PROCESS_STATE_FOREGROUND_MS = 10041;

    @android.os.health.HealthKeys.Constant(type = 0)
    public static final int TIMER_PROCESS_STATE_FOREGROUND_SERVICE_MS = 10039;

    @android.os.health.HealthKeys.Constant(type = 0)
    public static final int TIMER_PROCESS_STATE_TOP_MS = 10038;

    @android.os.health.HealthKeys.Constant(type = 0)
    public static final int TIMER_PROCESS_STATE_TOP_SLEEPING_MS = 10040;

    @android.os.health.HealthKeys.Constant(type = 0)
    public static final int TIMER_VIBRATOR = 10044;

    @android.os.health.HealthKeys.Constant(type = 0)
    public static final int TIMER_VIDEO = 10033;

    @android.os.health.HealthKeys.Constant(type = 0)
    public static final int TIMER_WIFI_SCAN = 10030;

    private UidHealthStats() {
    }
}
