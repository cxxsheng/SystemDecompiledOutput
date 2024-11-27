package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class CameraPowerCalculator extends com.android.server.power.stats.PowerCalculator {
    private final com.android.server.power.stats.UsageBasedPowerEstimator mPowerEstimator;

    public CameraPowerCalculator(com.android.internal.os.PowerProfile powerProfile) {
        this.mPowerEstimator = new com.android.server.power.stats.UsageBasedPowerEstimator(powerProfile.getAveragePower("camera.avg"));
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public boolean isPowerComponentSupported(int i) {
        return i == 3;
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public void calculate(android.os.BatteryUsageStats.Builder builder, android.os.BatteryStats batteryStats, long j, long j2, android.os.BatteryUsageStatsQuery batteryUsageStatsQuery) {
        double calculatePower;
        super.calculate(builder, batteryStats, j, j2, batteryUsageStatsQuery);
        long cameraEnergyConsumptionUC = batteryStats.getCameraEnergyConsumptionUC();
        int powerModel = com.android.server.power.stats.PowerCalculator.getPowerModel(cameraEnergyConsumptionUC, batteryUsageStatsQuery);
        long cameraOnTime = batteryStats.getCameraOnTime(j, 0) / 1000;
        if (powerModel == 2) {
            calculatePower = com.android.server.power.stats.PowerCalculator.uCtoMah(cameraEnergyConsumptionUC);
        } else {
            calculatePower = this.mPowerEstimator.calculatePower(cameraOnTime);
        }
        builder.getAggregateBatteryConsumerBuilder(0).setUsageDurationMillis(3, cameraOnTime).setConsumedPower(3, calculatePower, powerModel);
        builder.getAggregateBatteryConsumerBuilder(1).setUsageDurationMillis(3, cameraOnTime).setConsumedPower(3, calculatePower, powerModel);
    }

    @Override // com.android.server.power.stats.PowerCalculator
    protected void calculateApp(android.os.UidBatteryConsumer.Builder builder, android.os.BatteryStats.Uid uid, long j, long j2, android.os.BatteryUsageStatsQuery batteryUsageStatsQuery) {
        double calculatePower;
        long cameraEnergyConsumptionUC = builder.getBatteryStatsUid().getCameraEnergyConsumptionUC();
        int powerModel = com.android.server.power.stats.PowerCalculator.getPowerModel(cameraEnergyConsumptionUC, batteryUsageStatsQuery);
        long calculateDuration = this.mPowerEstimator.calculateDuration(uid.getCameraTurnedOnTimer(), j, 0);
        if (powerModel == 2) {
            calculatePower = com.android.server.power.stats.PowerCalculator.uCtoMah(cameraEnergyConsumptionUC);
        } else {
            calculatePower = this.mPowerEstimator.calculatePower(calculateDuration);
        }
        builder.setUsageDurationMillis(3, calculateDuration).setConsumedPower(3, calculatePower, powerModel);
    }
}
