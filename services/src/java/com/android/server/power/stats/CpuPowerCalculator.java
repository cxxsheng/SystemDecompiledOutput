package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class CpuPowerCalculator extends com.android.server.power.stats.PowerCalculator {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "CpuPowerCalculator";
    private static final android.os.BatteryConsumer.Key[] UNINITIALIZED_KEYS = new android.os.BatteryConsumer.Key[0];
    private final com.android.server.power.stats.UsageBasedPowerEstimator mCpuActivePowerEstimator;
    private final com.android.internal.os.CpuScalingPolicies mCpuScalingPolicies;
    private final int mNumCpuClusters;
    private final com.android.server.power.stats.UsageBasedPowerEstimator[] mPerClusterPowerEstimators;
    private final com.android.server.power.stats.UsageBasedPowerEstimator[] mPerCpuFreqPowerEstimators;
    private final com.android.server.power.stats.UsageBasedPowerEstimator[][] mPerCpuFreqPowerEstimatorsByCluster;

    private static class Result {
        public long[] cpuFreqTimes;
        public long durationFgMs;
        public long durationMs;
        public java.lang.String packageWithHighestDrain;
        public double[] perProcStatePowerMah;
        public double powerMah;

        private Result() {
        }
    }

    public CpuPowerCalculator(com.android.internal.os.CpuScalingPolicies cpuScalingPolicies, com.android.internal.os.PowerProfile powerProfile) {
        this.mCpuScalingPolicies = cpuScalingPolicies;
        int[] policies = this.mCpuScalingPolicies.getPolicies();
        this.mNumCpuClusters = policies.length;
        this.mCpuActivePowerEstimator = new com.android.server.power.stats.UsageBasedPowerEstimator(powerProfile.getAveragePower("cpu.active"));
        this.mPerClusterPowerEstimators = new com.android.server.power.stats.UsageBasedPowerEstimator[policies.length];
        for (int i = 0; i < policies.length; i++) {
            this.mPerClusterPowerEstimators[i] = new com.android.server.power.stats.UsageBasedPowerEstimator(powerProfile.getAveragePowerForCpuScalingPolicy(policies[i]));
        }
        this.mPerCpuFreqPowerEstimators = new com.android.server.power.stats.UsageBasedPowerEstimator[cpuScalingPolicies.getScalingStepCount()];
        this.mPerCpuFreqPowerEstimatorsByCluster = new com.android.server.power.stats.UsageBasedPowerEstimator[this.mNumCpuClusters][];
        int i2 = 0;
        for (int i3 = 0; i3 < policies.length; i3++) {
            int i4 = policies[i3];
            int[] frequencies = cpuScalingPolicies.getFrequencies(i4);
            this.mPerCpuFreqPowerEstimatorsByCluster[i3] = new com.android.server.power.stats.UsageBasedPowerEstimator[frequencies.length];
            int i5 = 0;
            while (i5 < frequencies.length) {
                com.android.server.power.stats.UsageBasedPowerEstimator usageBasedPowerEstimator = new com.android.server.power.stats.UsageBasedPowerEstimator(powerProfile.getAveragePowerForCpuScalingStep(i4, i5));
                this.mPerCpuFreqPowerEstimatorsByCluster[i3][i5] = usageBasedPowerEstimator;
                this.mPerCpuFreqPowerEstimators[i2] = usageBasedPowerEstimator;
                i5++;
                i2++;
            }
        }
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public boolean isPowerComponentSupported(int i) {
        return i == 1;
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public void calculate(android.os.BatteryUsageStats.Builder builder, android.os.BatteryStats batteryStats, long j, long j2, android.os.BatteryUsageStatsQuery batteryUsageStatsQuery) {
        android.os.BatteryConsumer.Key[] keyArr = UNINITIALIZED_KEYS;
        com.android.server.power.stats.CpuPowerCalculator.Result result = new com.android.server.power.stats.CpuPowerCalculator.Result();
        if (batteryUsageStatsQuery.isProcessStateDataNeeded()) {
            result.cpuFreqTimes = new long[this.mCpuScalingPolicies.getScalingStepCount()];
        }
        android.util.SparseArray uidBatteryConsumerBuilders = builder.getUidBatteryConsumerBuilders();
        double d = 0.0d;
        for (int size = uidBatteryConsumerBuilders.size() - 1; size >= 0; size--) {
            android.os.UidBatteryConsumer.Builder builder2 = (android.os.UidBatteryConsumer.Builder) uidBatteryConsumerBuilders.valueAt(size);
            if (keyArr == UNINITIALIZED_KEYS) {
                if (batteryUsageStatsQuery.isProcessStateDataNeeded()) {
                    keyArr = builder2.getKeys(1);
                } else {
                    keyArr = null;
                }
            }
            calculateApp(builder2, builder2.getBatteryStatsUid(), batteryUsageStatsQuery, result, keyArr);
            if (!builder2.isVirtualUid()) {
                d += result.powerMah;
            }
        }
        long cpuEnergyConsumptionUC = batteryStats.getCpuEnergyConsumptionUC();
        int powerModel = com.android.server.power.stats.PowerCalculator.getPowerModel(cpuEnergyConsumptionUC, batteryUsageStatsQuery);
        builder.getAggregateBatteryConsumerBuilder(1).setConsumedPower(1, d);
        android.os.AggregateBatteryConsumer.Builder aggregateBatteryConsumerBuilder = builder.getAggregateBatteryConsumerBuilder(0);
        if (powerModel == 2) {
            d = com.android.server.power.stats.PowerCalculator.uCtoMah(cpuEnergyConsumptionUC);
        }
        aggregateBatteryConsumerBuilder.setConsumedPower(1, d, powerModel);
    }

    private void calculateApp(android.os.UidBatteryConsumer.Builder builder, android.os.BatteryStats.Uid uid, android.os.BatteryUsageStatsQuery batteryUsageStatsQuery, com.android.server.power.stats.CpuPowerCalculator.Result result, android.os.BatteryConsumer.Key[] keyArr) {
        long cpuEnergyConsumptionUC = uid.getCpuEnergyConsumptionUC();
        int powerModel = com.android.server.power.stats.PowerCalculator.getPowerModel(cpuEnergyConsumptionUC, batteryUsageStatsQuery);
        calculatePowerAndDuration(uid, powerModel, cpuEnergyConsumptionUC, 0, result);
        builder.setConsumedPower(1, result.powerMah, powerModel).setUsageDurationMillis(1, result.durationMs).setPackageWithHighestDrain(result.packageWithHighestDrain);
        if (batteryUsageStatsQuery.isProcessStateDataNeeded() && keyArr != null) {
            switch (powerModel) {
                case 1:
                    calculateModeledPowerPerProcessState(builder, uid, keyArr, result);
                    break;
                case 2:
                    calculateEnergyConsumptionPerProcessState(builder, uid, keyArr);
                    break;
            }
        }
    }

    private void calculateEnergyConsumptionPerProcessState(android.os.UidBatteryConsumer.Builder builder, android.os.BatteryStats.Uid uid, android.os.BatteryConsumer.Key[] keyArr) {
        for (android.os.BatteryConsumer.Key key : keyArr) {
            if (key.processState != 0) {
                long cpuEnergyConsumptionUC = uid.getCpuEnergyConsumptionUC(key.processState);
                if (cpuEnergyConsumptionUC != 0) {
                    builder.setConsumedPower(key, com.android.server.power.stats.PowerCalculator.uCtoMah(cpuEnergyConsumptionUC), 2);
                }
            }
        }
    }

    private void calculateModeledPowerPerProcessState(android.os.UidBatteryConsumer.Builder builder, android.os.BatteryStats.Uid uid, android.os.BatteryConsumer.Key[] keyArr, com.android.server.power.stats.CpuPowerCalculator.Result result) {
        if (result.perProcStatePowerMah == null) {
            result.perProcStatePowerMah = new double[5];
        } else {
            java.util.Arrays.fill(result.perProcStatePowerMah, 0.0d);
        }
        for (int i = 0; i < 7; i++) {
            int mapUidProcessStateToBatteryConsumerProcessState = android.os.BatteryStats.mapUidProcessStateToBatteryConsumerProcessState(i);
            if (mapUidProcessStateToBatteryConsumerProcessState != 0 && uid.getCpuFreqTimes(result.cpuFreqTimes, i)) {
                double[] dArr = result.perProcStatePowerMah;
                dArr[mapUidProcessStateToBatteryConsumerProcessState] = dArr[mapUidProcessStateToBatteryConsumerProcessState] + calculateUidModeledPowerMah(uid, 0L, null, result.cpuFreqTimes);
            }
        }
        for (android.os.BatteryConsumer.Key key : keyArr) {
            if (key.processState != 0) {
                long cpuActiveTime = uid.getCpuActiveTime(key.processState);
                builder.setConsumedPower(key, result.perProcStatePowerMah[key.processState] + this.mCpuActivePowerEstimator.calculatePower(cpuActiveTime), 1).setUsageDurationMillis(key, cpuActiveTime);
            }
        }
    }

    private void calculatePowerAndDuration(android.os.BatteryStats.Uid uid, int i, long j, int i2, com.android.server.power.stats.CpuPowerCalculator.Result result) {
        double uCtoMah;
        int i3 = i2;
        long userCpuTimeUs = (uid.getUserCpuTimeUs(i3) + uid.getSystemCpuTimeUs(i3)) / 1000;
        switch (i) {
            case 2:
                uCtoMah = com.android.server.power.stats.PowerCalculator.uCtoMah(j);
                break;
            default:
                uCtoMah = calculateUidModeledPowerMah(uid, i3);
                break;
        }
        android.util.ArrayMap processStats = uid.getProcessStats();
        int size = processStats.size();
        double d = 0.0d;
        java.lang.String str = null;
        long j2 = 0;
        int i4 = 0;
        while (i4 < size) {
            android.os.BatteryStats.Uid.Proc proc = (android.os.BatteryStats.Uid.Proc) processStats.valueAt(i4);
            java.lang.String str2 = (java.lang.String) processStats.keyAt(i4);
            j2 += proc.getForegroundTime(i3);
            android.util.ArrayMap arrayMap = processStats;
            long userTime = proc.getUserTime(i3) + proc.getSystemTime(i3) + proc.getForegroundTime(i3);
            if (str == null || str.startsWith(com.android.server.am.SettingsToPropertiesMapper.NAMESPACE_REBOOT_STAGING_DELIMITER)) {
                d = userTime;
                str = str2;
            } else {
                double d2 = userTime;
                if (d < d2 && !str2.startsWith(com.android.server.am.SettingsToPropertiesMapper.NAMESPACE_REBOOT_STAGING_DELIMITER)) {
                    d = d2;
                    str = str2;
                }
            }
            i4++;
            processStats = arrayMap;
            i3 = i2;
        }
        if (j2 > userCpuTimeUs) {
            userCpuTimeUs = j2;
        }
        result.durationMs = userCpuTimeUs;
        result.durationFgMs = j2;
        result.powerMah = uCtoMah;
        result.packageWithHighestDrain = str;
    }

    public double calculateUidModeledPowerMah(android.os.BatteryStats.Uid uid, int i) {
        return calculateUidModeledPowerMah(uid, uid.getCpuActiveTime(), uid.getCpuClusterTimes(), uid.getCpuFreqTimes(i));
    }

    private double calculateUidModeledPowerMah(android.os.BatteryStats.Uid uid, long j, long[] jArr, long[] jArr2) {
        double calculateActiveCpuPowerMah = calculateActiveCpuPowerMah(j);
        if (jArr != null) {
            if (jArr.length == this.mNumCpuClusters) {
                for (int i = 0; i < this.mNumCpuClusters; i++) {
                    calculateActiveCpuPowerMah += this.mPerClusterPowerEstimators[i].calculatePower(jArr[i]);
                }
            } else {
                android.util.Log.w(TAG, "UID " + uid.getUid() + " CPU cluster # mismatch: Power Profile # " + this.mNumCpuClusters + " actual # " + jArr.length);
            }
        }
        if (jArr2 != null) {
            if (jArr2.length == this.mPerCpuFreqPowerEstimators.length) {
                for (int i2 = 0; i2 < jArr2.length; i2++) {
                    calculateActiveCpuPowerMah += this.mPerCpuFreqPowerEstimators[i2].calculatePower(jArr2[i2]);
                }
            } else {
                android.util.Log.w(TAG, "UID " + uid.getUid() + " CPU freq # mismatch: Power Profile # " + this.mPerCpuFreqPowerEstimators.length + " actual # " + jArr2.length);
            }
        }
        return calculateActiveCpuPowerMah;
    }

    private double calculateActiveCpuPowerMah(long j) {
        return this.mCpuActivePowerEstimator.calculatePower(j);
    }

    public double calculatePerCpuClusterPowerMah(int i, long j) {
        return this.mPerClusterPowerEstimators[i].calculatePower(j);
    }

    public double calculatePerCpuFreqPowerMah(int i, int i2, long j) {
        return this.mPerCpuFreqPowerEstimatorsByCluster[i][i2].calculatePower(j);
    }
}
