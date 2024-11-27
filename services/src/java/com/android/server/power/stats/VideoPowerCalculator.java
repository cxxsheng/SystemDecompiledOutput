package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class VideoPowerCalculator extends com.android.server.power.stats.PowerCalculator {
    private final com.android.server.power.stats.UsageBasedPowerEstimator mPowerEstimator;

    private static class PowerAndDuration {
        public long durationMs;
        public double powerMah;

        private PowerAndDuration() {
        }
    }

    public VideoPowerCalculator(com.android.internal.os.PowerProfile powerProfile) {
        this.mPowerEstimator = new com.android.server.power.stats.UsageBasedPowerEstimator(powerProfile.getAveragePower("video"));
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public boolean isPowerComponentSupported(int i) {
        return i == 5;
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public void calculate(android.os.BatteryUsageStats.Builder builder, android.os.BatteryStats batteryStats, long j, long j2, android.os.BatteryUsageStatsQuery batteryUsageStatsQuery) {
        com.android.server.power.stats.VideoPowerCalculator.PowerAndDuration powerAndDuration = new com.android.server.power.stats.VideoPowerCalculator.PowerAndDuration();
        android.util.SparseArray uidBatteryConsumerBuilders = builder.getUidBatteryConsumerBuilders();
        for (int size = uidBatteryConsumerBuilders.size() - 1; size >= 0; size--) {
            android.os.UidBatteryConsumer.Builder builder2 = (android.os.UidBatteryConsumer.Builder) uidBatteryConsumerBuilders.valueAt(size);
            calculateApp(builder2, powerAndDuration, builder2.getBatteryStatsUid(), j);
        }
        builder.getAggregateBatteryConsumerBuilder(0).setUsageDurationMillis(5, powerAndDuration.durationMs).setConsumedPower(5, powerAndDuration.powerMah);
        builder.getAggregateBatteryConsumerBuilder(1).setUsageDurationMillis(5, powerAndDuration.durationMs).setConsumedPower(5, powerAndDuration.powerMah);
    }

    private void calculateApp(android.os.UidBatteryConsumer.Builder builder, com.android.server.power.stats.VideoPowerCalculator.PowerAndDuration powerAndDuration, android.os.BatteryStats.Uid uid, long j) {
        long calculateDuration = this.mPowerEstimator.calculateDuration(uid.getVideoTurnedOnTimer(), j, 0);
        double calculatePower = this.mPowerEstimator.calculatePower(calculateDuration);
        builder.setUsageDurationMillis(5, calculateDuration).setConsumedPower(5, calculatePower);
        if (!builder.isVirtualUid()) {
            powerAndDuration.durationMs += calculateDuration;
            powerAndDuration.powerMah += calculatePower;
        }
    }
}
