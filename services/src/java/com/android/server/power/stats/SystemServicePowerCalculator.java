package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class SystemServicePowerCalculator extends com.android.server.power.stats.PowerCalculator {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "SystemServicePowerCalc";
    private final com.android.server.power.stats.CpuPowerCalculator mCpuPowerCalculator;
    private final com.android.server.power.stats.UsageBasedPowerEstimator[] mPowerEstimators;

    public SystemServicePowerCalculator(com.android.internal.os.CpuScalingPolicies cpuScalingPolicies, com.android.internal.os.PowerProfile powerProfile) {
        this.mCpuPowerCalculator = new com.android.server.power.stats.CpuPowerCalculator(cpuScalingPolicies, powerProfile);
        this.mPowerEstimators = new com.android.server.power.stats.UsageBasedPowerEstimator[cpuScalingPolicies.getScalingStepCount()];
        int i = 0;
        for (int i2 : cpuScalingPolicies.getPolicies()) {
            int length = cpuScalingPolicies.getFrequencies(i2).length;
            int i3 = 0;
            while (i3 < length) {
                this.mPowerEstimators[i] = new com.android.server.power.stats.UsageBasedPowerEstimator(powerProfile.getAveragePowerForCpuScalingStep(i2, i3));
                i3++;
                i++;
            }
        }
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public boolean isPowerComponentSupported(int i) {
        return i == 7;
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public void calculate(android.os.BatteryUsageStats.Builder builder, android.os.BatteryStats batteryStats, long j, long j2, android.os.BatteryUsageStatsQuery batteryUsageStatsQuery) {
        double calculatePowerUsingPowerProfile;
        android.os.BatteryStats.Uid uid = (android.os.BatteryStats.Uid) batteryStats.getUidStats().get(1000);
        if (uid == null) {
            return;
        }
        long cpuEnergyConsumptionUC = uid.getCpuEnergyConsumptionUC();
        int powerModel = com.android.server.power.stats.PowerCalculator.getPowerModel(cpuEnergyConsumptionUC, batteryUsageStatsQuery);
        if (powerModel == 2) {
            calculatePowerUsingPowerProfile = calculatePowerUsingEnergyConsumption(batteryStats, uid, cpuEnergyConsumptionUC);
        } else {
            calculatePowerUsingPowerProfile = calculatePowerUsingPowerProfile(batteryStats);
        }
        android.util.SparseArray uidBatteryConsumerBuilders = builder.getUidBatteryConsumerBuilders();
        android.os.UidBatteryConsumer.Builder builder2 = (android.os.UidBatteryConsumer.Builder) uidBatteryConsumerBuilders.get(1000);
        if (builder2 != null) {
            calculatePowerUsingPowerProfile = java.lang.Math.min(calculatePowerUsingPowerProfile, builder2.getTotalPower());
            builder2.setConsumedPower(17, -calculatePowerUsingPowerProfile, powerModel);
        }
        for (int size = uidBatteryConsumerBuilders.size() - 1; size >= 0; size--) {
            android.os.UidBatteryConsumer.Builder builder3 = (android.os.UidBatteryConsumer.Builder) uidBatteryConsumerBuilders.valueAt(size);
            if (builder3 != builder2) {
                builder3.setConsumedPower(7, builder3.getBatteryStatsUid().getProportionalSystemServiceUsage() * calculatePowerUsingPowerProfile, powerModel);
            }
        }
        builder.getAggregateBatteryConsumerBuilder(0).setConsumedPower(7, calculatePowerUsingPowerProfile);
        builder.getAggregateBatteryConsumerBuilder(1).setConsumedPower(7, calculatePowerUsingPowerProfile);
    }

    private double calculatePowerUsingEnergyConsumption(android.os.BatteryStats batteryStats, android.os.BatteryStats.Uid uid, long j) {
        double calculatePowerUsingPowerProfile = calculatePowerUsingPowerProfile(batteryStats);
        double calculateUidModeledPowerMah = this.mCpuPowerCalculator.calculateUidModeledPowerMah(uid, 0);
        if (calculateUidModeledPowerMah <= 0.0d) {
            return 0.0d;
        }
        return (com.android.server.power.stats.PowerCalculator.uCtoMah(j) * calculatePowerUsingPowerProfile) / calculateUidModeledPowerMah;
    }

    private double calculatePowerUsingPowerProfile(android.os.BatteryStats batteryStats) {
        long[] systemServiceTimeAtCpuSpeeds = batteryStats.getSystemServiceTimeAtCpuSpeeds();
        double d = 0.0d;
        if (systemServiceTimeAtCpuSpeeds == null) {
            return 0.0d;
        }
        int min = java.lang.Math.min(this.mPowerEstimators.length, systemServiceTimeAtCpuSpeeds.length);
        for (int i = 0; i < min; i++) {
            d += this.mPowerEstimators[i].calculatePower(systemServiceTimeAtCpuSpeeds[i] / 1000);
        }
        return d;
    }
}
