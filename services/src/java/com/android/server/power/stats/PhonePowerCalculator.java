package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class PhonePowerCalculator extends com.android.server.power.stats.PowerCalculator {
    private final com.android.server.power.stats.UsageBasedPowerEstimator mPowerEstimator;

    public PhonePowerCalculator(com.android.internal.os.PowerProfile powerProfile) {
        this.mPowerEstimator = new com.android.server.power.stats.UsageBasedPowerEstimator(powerProfile.getAveragePower("radio.active"));
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public boolean isPowerComponentSupported(int i) {
        return i == 14;
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public void calculate(android.os.BatteryUsageStats.Builder builder, android.os.BatteryStats batteryStats, long j, long j2, android.os.BatteryUsageStatsQuery batteryUsageStatsQuery) {
        double uCtoMah;
        long phoneEnergyConsumptionUC = batteryStats.getPhoneEnergyConsumptionUC();
        int powerModel = com.android.server.power.stats.PowerCalculator.getPowerModel(phoneEnergyConsumptionUC, batteryUsageStatsQuery);
        long phoneOnTime = batteryStats.getPhoneOnTime(j, 0) / 1000;
        switch (powerModel) {
            case 2:
                uCtoMah = com.android.server.power.stats.PowerCalculator.uCtoMah(phoneEnergyConsumptionUC);
                break;
            default:
                uCtoMah = this.mPowerEstimator.calculatePower(phoneOnTime);
                break;
        }
        if (uCtoMah == 0.0d) {
            return;
        }
        builder.getAggregateBatteryConsumerBuilder(0).setConsumedPower(14, uCtoMah, powerModel).setUsageDurationMillis(14, phoneOnTime);
    }
}
