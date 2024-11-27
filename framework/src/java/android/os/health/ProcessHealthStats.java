package android.os.health;

/* loaded from: classes3.dex */
public final class ProcessHealthStats {
    public static final android.os.health.HealthKeys.Constants CONSTANTS = new android.os.health.HealthKeys.Constants(android.os.health.ProcessHealthStats.class);

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_ANR_COUNT = 30005;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_CRASHES_COUNT = 30004;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_FOREGROUND_MS = 30006;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_STARTS_COUNT = 30003;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_SYSTEM_TIME_MS = 30002;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_USER_TIME_MS = 30001;

    private ProcessHealthStats() {
    }
}
