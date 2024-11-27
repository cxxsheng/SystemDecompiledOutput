package com.android.server.power.stats;

/* loaded from: classes2.dex */
public abstract class PowerCalculator {
    protected static final boolean DEBUG = false;
    protected static final double MILLIAMPHOUR_PER_MICROCOULOMB = 2.777777777777778E-7d;

    public abstract boolean isPowerComponentSupported(int i);

    public void calculate(android.os.BatteryUsageStats.Builder builder, android.os.BatteryStats batteryStats, long j, long j2, android.os.BatteryUsageStatsQuery batteryUsageStatsQuery) {
        android.util.SparseArray uidBatteryConsumerBuilders = builder.getUidBatteryConsumerBuilders();
        for (int size = uidBatteryConsumerBuilders.size() - 1; size >= 0; size--) {
            android.os.UidBatteryConsumer.Builder builder2 = (android.os.UidBatteryConsumer.Builder) uidBatteryConsumerBuilders.valueAt(size);
            calculateApp(builder2, builder2.getBatteryStatsUid(), j, j2, batteryUsageStatsQuery);
        }
    }

    protected void calculateApp(android.os.UidBatteryConsumer.Builder builder, android.os.BatteryStats.Uid uid, long j, long j2, android.os.BatteryUsageStatsQuery batteryUsageStatsQuery) {
    }

    public void reset() {
    }

    protected static int getPowerModel(long j, @android.annotation.NonNull android.os.BatteryUsageStatsQuery batteryUsageStatsQuery) {
        if (j != -1 && !batteryUsageStatsQuery.shouldForceUsePowerProfileModel()) {
            return 2;
        }
        return 1;
    }

    protected static int getPowerModel(long j) {
        if (j != -1) {
            return 2;
        }
        return 1;
    }

    public static double uCtoMah(long j) {
        return j * MILLIAMPHOUR_PER_MICROCOULOMB;
    }
}
