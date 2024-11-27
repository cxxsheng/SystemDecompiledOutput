package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class AmbientDisplayPowerCalculator extends com.android.server.power.stats.PowerCalculator {
    private final com.android.server.power.stats.UsageBasedPowerEstimator[] mPowerEstimators;

    @Override // com.android.server.power.stats.PowerCalculator
    public boolean isPowerComponentSupported(int i) {
        return i == 15;
    }

    public AmbientDisplayPowerCalculator(com.android.internal.os.PowerProfile powerProfile) {
        int numDisplays = powerProfile.getNumDisplays();
        this.mPowerEstimators = new com.android.server.power.stats.UsageBasedPowerEstimator[numDisplays];
        for (int i = 0; i < numDisplays; i++) {
            this.mPowerEstimators[i] = new com.android.server.power.stats.UsageBasedPowerEstimator(powerProfile.getAveragePowerForOrdinal("ambient.on.display", i));
        }
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public void calculate(android.os.BatteryUsageStats.Builder builder, android.os.BatteryStats batteryStats, long j, long j2, android.os.BatteryUsageStatsQuery batteryUsageStatsQuery) {
        long screenDozeEnergyConsumptionUC = batteryStats.getScreenDozeEnergyConsumptionUC();
        int powerModel = com.android.server.power.stats.PowerCalculator.getPowerModel(screenDozeEnergyConsumptionUC, batteryUsageStatsQuery);
        long calculateDuration = calculateDuration(batteryStats, j, 0);
        builder.getAggregateBatteryConsumerBuilder(0).setUsageDurationMillis(15, calculateDuration).setConsumedPower(15, calculateTotalPower(powerModel, batteryStats, j, screenDozeEnergyConsumptionUC), powerModel);
    }

    private long calculateDuration(android.os.BatteryStats batteryStats, long j, int i) {
        return batteryStats.getScreenDozeTime(j, i) / 1000;
    }

    private double calculateTotalPower(int i, android.os.BatteryStats batteryStats, long j, long j2) {
        switch (i) {
            case 2:
                return com.android.server.power.stats.PowerCalculator.uCtoMah(j2);
            default:
                return calculateEstimatedPower(batteryStats, j);
        }
    }

    private double calculateEstimatedPower(android.os.BatteryStats batteryStats, long j) {
        int length = this.mPowerEstimators.length;
        double d = 0.0d;
        for (int i = 0; i < length; i++) {
            d += this.mPowerEstimators[i].calculatePower(batteryStats.getDisplayScreenDozeTime(i, j) / 1000);
        }
        return d;
    }
}
