package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class FlashlightPowerCalculator extends com.android.server.power.stats.PowerCalculator {
    private final com.android.server.power.stats.UsageBasedPowerEstimator mPowerEstimator;

    public FlashlightPowerCalculator(com.android.internal.os.PowerProfile powerProfile) {
        this.mPowerEstimator = new com.android.server.power.stats.UsageBasedPowerEstimator(powerProfile.getAveragePower("camera.flashlight"));
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public boolean isPowerComponentSupported(int i) {
        return i == 6;
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public void calculate(android.os.BatteryUsageStats.Builder builder, android.os.BatteryStats batteryStats, long j, long j2, android.os.BatteryUsageStatsQuery batteryUsageStatsQuery) {
        super.calculate(builder, batteryStats, j, j2, batteryUsageStatsQuery);
        long flashlightOnTime = batteryStats.getFlashlightOnTime(j, 0) / 1000;
        double calculatePower = this.mPowerEstimator.calculatePower(flashlightOnTime);
        builder.getAggregateBatteryConsumerBuilder(0).setUsageDurationMillis(6, flashlightOnTime).setConsumedPower(6, calculatePower);
        builder.getAggregateBatteryConsumerBuilder(1).setUsageDurationMillis(6, flashlightOnTime).setConsumedPower(6, calculatePower);
    }

    @Override // com.android.server.power.stats.PowerCalculator
    protected void calculateApp(android.os.UidBatteryConsumer.Builder builder, android.os.BatteryStats.Uid uid, long j, long j2, android.os.BatteryUsageStatsQuery batteryUsageStatsQuery) {
        long calculateDuration = this.mPowerEstimator.calculateDuration(uid.getFlashlightTurnedOnTimer(), j, 0);
        builder.setUsageDurationMillis(6, calculateDuration).setConsumedPower(6, this.mPowerEstimator.calculatePower(calculateDuration));
    }
}
