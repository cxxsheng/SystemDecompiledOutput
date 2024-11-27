package android.os.health;

/* loaded from: classes3.dex */
public final class PidHealthStats {
    public static final android.os.health.HealthKeys.Constants CONSTANTS = new android.os.health.HealthKeys.Constants(android.os.health.PidHealthStats.class);

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_WAKE_NESTING_COUNT = 20001;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_WAKE_START_MS = 20003;

    @android.os.health.HealthKeys.Constant(type = 1)
    public static final int MEASUREMENT_WAKE_SUM_MS = 20002;

    private PidHealthStats() {
    }
}
