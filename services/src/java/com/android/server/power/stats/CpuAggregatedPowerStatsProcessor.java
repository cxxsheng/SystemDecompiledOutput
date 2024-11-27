package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class CpuAggregatedPowerStatsProcessor extends com.android.server.power.stats.AggregatedPowerStatsProcessor {
    private static final double HOUR_IN_MILLIS = java.util.concurrent.TimeUnit.HOURS.toMillis(1);
    private static final java.lang.String TAG = "CpuAggregatedPowerStatsProcessor";
    private static final int UNKNOWN = -1;
    private int[][] mCombinedEnergyConsumerToPowerBracketMap;
    private final int mCpuClusterCount;
    private final com.android.internal.os.CpuScalingPolicies mCpuScalingPolicies;
    private final int mCpuScalingStepCount;
    private int[] mEnergyConsumerToCombinedEnergyConsumerMap;
    private com.android.internal.os.PowerStats.Descriptor mLastUsedDescriptor;
    private com.android.server.power.stats.AggregatedPowerStatsProcessor.PowerEstimationPlan mPlan;
    private final double mPowerMultiplierForCpuActive;
    private final double[] mPowerMultipliersByCluster;
    private final double[] mPowerMultipliersByScalingStep;
    private final int[] mScalingStepToCluster;
    private com.android.server.power.stats.CpuPowerStatsCollector.CpuStatsArrayLayout mStatsLayout;
    private long[] mTmpDeviceStatsArray;
    private long[] mTmpUidStatsArray;

    private static class DeviceStatsIntermediates {
        public double power;
        public double[] powerByBracket;
        public long[] timeByBracket;

        private DeviceStatsIntermediates() {
        }
    }

    private static final class Intermediates {
        public long cumulativeTime;
        public long[] cumulativeTimeByCluster;
        public double[] powerByCluster;
        public long[] powerByEnergyConsumer;
        public double[] powerByScalingStep;
        public long[] timeByCluster;
        public long[] timeByScalingStep;
        public long uptime;

        private Intermediates() {
        }
    }

    public CpuAggregatedPowerStatsProcessor(com.android.internal.os.PowerProfile powerProfile, com.android.internal.os.CpuScalingPolicies cpuScalingPolicies) {
        this.mCpuScalingPolicies = cpuScalingPolicies;
        this.mCpuScalingStepCount = cpuScalingPolicies.getScalingStepCount();
        this.mScalingStepToCluster = new int[this.mCpuScalingStepCount];
        this.mPowerMultipliersByScalingStep = new double[this.mCpuScalingStepCount];
        int[] policies = cpuScalingPolicies.getPolicies();
        this.mCpuClusterCount = policies.length;
        this.mPowerMultipliersByCluster = new double[this.mCpuClusterCount];
        int i = 0;
        for (int i2 = 0; i2 < this.mCpuClusterCount; i2++) {
            int i3 = policies[i2];
            this.mPowerMultipliersByCluster[i2] = powerProfile.getAveragePowerForCpuScalingPolicy(i3) / HOUR_IN_MILLIS;
            int[] frequencies = cpuScalingPolicies.getFrequencies(i3);
            for (int i4 = 0; i4 < frequencies.length; i4++) {
                this.mScalingStepToCluster[i] = i2;
                this.mPowerMultipliersByScalingStep[i] = powerProfile.getAveragePowerForCpuScalingStep(i3, i4) / HOUR_IN_MILLIS;
                i++;
            }
        }
        this.mPowerMultiplierForCpuActive = powerProfile.getAveragePower("cpu.active") / HOUR_IN_MILLIS;
    }

    private void unpackPowerStatsDescriptor(com.android.internal.os.PowerStats.Descriptor descriptor) {
        if (descriptor.equals(this.mLastUsedDescriptor)) {
            return;
        }
        this.mLastUsedDescriptor = descriptor;
        this.mStatsLayout = new com.android.server.power.stats.CpuPowerStatsCollector.CpuStatsArrayLayout();
        this.mStatsLayout.fromExtras(descriptor.extras);
        this.mTmpDeviceStatsArray = new long[descriptor.statsArrayLength];
        this.mTmpUidStatsArray = new long[descriptor.uidStatsArrayLength];
    }

    @Override // com.android.server.power.stats.AggregatedPowerStatsProcessor
    public void finish(com.android.server.power.stats.PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats) {
        if (powerComponentAggregatedPowerStats.getPowerStatsDescriptor() == null) {
            return;
        }
        unpackPowerStatsDescriptor(powerComponentAggregatedPowerStats.getPowerStatsDescriptor());
        if (this.mPlan == null) {
            this.mPlan = new com.android.server.power.stats.AggregatedPowerStatsProcessor.PowerEstimationPlan(powerComponentAggregatedPowerStats.getConfig());
            if (this.mStatsLayout.getEnergyConsumerCount() != 0) {
                initEnergyConsumerToPowerBracketMaps();
            }
        }
        com.android.server.power.stats.CpuAggregatedPowerStatsProcessor.Intermediates intermediates = new com.android.server.power.stats.CpuAggregatedPowerStatsProcessor.Intermediates();
        int cpuScalingStepCount = this.mStatsLayout.getCpuScalingStepCount();
        if (cpuScalingStepCount != this.mCpuScalingStepCount) {
            android.util.Log.e(TAG, "Mismatched CPU scaling step count in PowerStats: " + cpuScalingStepCount + ", expected: " + this.mCpuScalingStepCount);
            return;
        }
        int cpuClusterCount = this.mStatsLayout.getCpuClusterCount();
        if (cpuClusterCount != this.mCpuClusterCount) {
            android.util.Log.e(TAG, "Mismatched CPU cluster count in PowerStats: " + cpuClusterCount + ", expected: " + this.mCpuClusterCount);
            return;
        }
        computeTotals(powerComponentAggregatedPowerStats, intermediates);
        if (intermediates.cumulativeTime == 0) {
            return;
        }
        estimatePowerByScalingStep(intermediates);
        estimatePowerByDeviceState(powerComponentAggregatedPowerStats, intermediates);
        combineDeviceStateEstimates();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        powerComponentAggregatedPowerStats.collectUids(arrayList);
        if (!arrayList.isEmpty()) {
            java.util.Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                int intValue = ((java.lang.Integer) it.next()).intValue();
                for (int i = 0; i < this.mPlan.uidStateEstimates.size(); i++) {
                    estimateUidPowerConsumption(powerComponentAggregatedPowerStats, intValue, this.mPlan.uidStateEstimates.get(i));
                }
            }
        }
        this.mPlan.resetIntermediates();
    }

    private void initEnergyConsumerToPowerBracketMaps() {
        int energyConsumerCount = this.mStatsLayout.getEnergyConsumerCount();
        int cpuPowerBracketCount = this.mStatsLayout.getCpuPowerBracketCount();
        this.mEnergyConsumerToCombinedEnergyConsumerMap = new int[energyConsumerCount];
        this.mCombinedEnergyConsumerToPowerBracketMap = new int[energyConsumerCount][];
        int[] policies = this.mCpuScalingPolicies.getPolicies();
        if (energyConsumerCount == policies.length) {
            int[] scalingStepToPowerBracketMap = this.mStatsLayout.getScalingStepToPowerBracketMap();
            int length = policies.length;
            android.util.ArraySet[] arraySetArr = new android.util.ArraySet[length];
            int i = 0;
            for (int i2 = 0; i2 < policies.length; i2++) {
                int[] frequencies = this.mCpuScalingPolicies.getFrequencies(policies[i2]);
                arraySetArr[i2] = new android.util.ArraySet(frequencies.length);
                int i3 = 0;
                while (i3 < frequencies.length) {
                    arraySetArr[i2].add(java.lang.Integer.valueOf(scalingStepToPowerBracketMap[i]));
                    i3++;
                    i++;
                }
            }
            int length2 = policies.length;
            android.util.ArraySet[] arraySetArr2 = new android.util.ArraySet[length2];
            int i4 = 0;
            for (int i5 = 0; i5 < length; i5++) {
                int i6 = 0;
                while (true) {
                    if (i6 >= i4) {
                        i6 = -1;
                        break;
                    } else if (containsAny(arraySetArr2[i6], arraySetArr[i5])) {
                        break;
                    } else {
                        i6++;
                    }
                }
                if (i6 != -1) {
                    this.mEnergyConsumerToCombinedEnergyConsumerMap[i5] = i6;
                    arraySetArr2[i6].addAll(arraySetArr[i5]);
                } else {
                    this.mEnergyConsumerToCombinedEnergyConsumerMap[i5] = i4;
                    arraySetArr2[i4] = arraySetArr[i5];
                    i4++;
                }
            }
            for (int i7 = length2 - 1; i7 >= 0; i7--) {
                this.mCombinedEnergyConsumerToPowerBracketMap[i7] = new int[arraySetArr2[i7].size()];
                for (int size = arraySetArr2[i7].size() - 1; size >= 0; size--) {
                    this.mCombinedEnergyConsumerToPowerBracketMap[i7][size] = ((java.lang.Integer) arraySetArr2[i7].valueAt(size)).intValue();
                }
            }
            return;
        }
        int[] iArr = new int[cpuPowerBracketCount];
        for (int i8 = 0; i8 < cpuPowerBracketCount; i8++) {
            iArr[i8] = i8;
        }
        this.mCombinedEnergyConsumerToPowerBracketMap[0] = iArr;
    }

    private static boolean containsAny(android.util.ArraySet<java.lang.Integer> arraySet, android.util.ArraySet<java.lang.Integer> arraySet2) {
        for (int i = 0; i < arraySet2.size(); i++) {
            if (arraySet.contains(arraySet2.valueAt(i))) {
                return true;
            }
        }
        return false;
    }

    private void computeTotals(com.android.server.power.stats.PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats, com.android.server.power.stats.CpuAggregatedPowerStatsProcessor.Intermediates intermediates) {
        intermediates.timeByScalingStep = new long[this.mCpuScalingStepCount];
        intermediates.timeByCluster = new long[this.mCpuClusterCount];
        intermediates.cumulativeTimeByCluster = new long[this.mCpuClusterCount];
        java.util.List<com.android.server.power.stats.AggregatedPowerStatsProcessor.DeviceStateEstimation> list = this.mPlan.deviceStateEstimations;
        for (int size = list.size() - 1; size >= 0; size--) {
            if (powerComponentAggregatedPowerStats.getDeviceStats(this.mTmpDeviceStatsArray, list.get(size).stateValues)) {
                intermediates.uptime += this.mStatsLayout.getUsageDuration(this.mTmpDeviceStatsArray);
                for (int i = 0; i < this.mCpuClusterCount; i++) {
                    long[] jArr = intermediates.timeByCluster;
                    jArr[i] = jArr[i] + this.mStatsLayout.getTimeByCluster(this.mTmpDeviceStatsArray, i);
                }
                for (int i2 = 0; i2 < this.mCpuScalingStepCount; i2++) {
                    long timeByScalingStep = this.mStatsLayout.getTimeByScalingStep(this.mTmpDeviceStatsArray, i2);
                    intermediates.cumulativeTime += timeByScalingStep;
                    long[] jArr2 = intermediates.timeByScalingStep;
                    jArr2[i2] = jArr2[i2] + timeByScalingStep;
                    long[] jArr3 = intermediates.cumulativeTimeByCluster;
                    int i3 = this.mScalingStepToCluster[i2];
                    jArr3[i3] = jArr3[i3] + timeByScalingStep;
                }
            }
        }
    }

    private void estimatePowerByScalingStep(com.android.server.power.stats.CpuAggregatedPowerStatsProcessor.Intermediates intermediates) {
        double d = this.mPowerMultiplierForCpuActive * intermediates.uptime;
        intermediates.powerByCluster = new double[this.mCpuClusterCount];
        for (int i = 0; i < this.mCpuClusterCount; i++) {
            intermediates.powerByCluster[i] = this.mPowerMultipliersByCluster[i] * intermediates.timeByCluster[i];
        }
        intermediates.powerByScalingStep = new double[this.mCpuScalingStepCount];
        for (int i2 = 0; i2 < this.mCpuScalingStepCount; i2++) {
            int i3 = this.mScalingStepToCluster[i2];
            double d2 = (intermediates.timeByScalingStep[i2] * d) / intermediates.cumulativeTime;
            long j = intermediates.cumulativeTimeByCluster[i3];
            if (j != 0) {
                d2 += (intermediates.powerByCluster[i3] * intermediates.timeByScalingStep[i2]) / j;
            }
            intermediates.powerByScalingStep[i2] = d2 + (this.mPowerMultipliersByScalingStep[i2] * intermediates.timeByScalingStep[i2]);
        }
    }

    private void estimatePowerByDeviceState(com.android.server.power.stats.PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats, com.android.server.power.stats.CpuAggregatedPowerStatsProcessor.Intermediates intermediates) {
        int i;
        int i2;
        int cpuScalingStepCount = this.mStatsLayout.getCpuScalingStepCount();
        int cpuPowerBracketCount = this.mStatsLayout.getCpuPowerBracketCount();
        int[] scalingStepToPowerBracketMap = this.mStatsLayout.getScalingStepToPowerBracketMap();
        int energyConsumerCount = this.mStatsLayout.getEnergyConsumerCount();
        java.util.List<com.android.server.power.stats.AggregatedPowerStatsProcessor.DeviceStateEstimation> list = this.mPlan.deviceStateEstimations;
        int size = list.size() - 1;
        while (size >= 0) {
            com.android.server.power.stats.AggregatedPowerStatsProcessor.DeviceStateEstimation deviceStateEstimation = list.get(size);
            deviceStateEstimation.intermediates = new com.android.server.power.stats.CpuAggregatedPowerStatsProcessor.DeviceStatsIntermediates();
            com.android.server.power.stats.CpuAggregatedPowerStatsProcessor.DeviceStatsIntermediates deviceStatsIntermediates = (com.android.server.power.stats.CpuAggregatedPowerStatsProcessor.DeviceStatsIntermediates) deviceStateEstimation.intermediates;
            deviceStatsIntermediates.timeByBracket = new long[cpuPowerBracketCount];
            deviceStatsIntermediates.powerByBracket = new double[cpuPowerBracketCount];
            powerComponentAggregatedPowerStats.getDeviceStats(this.mTmpDeviceStatsArray, deviceStateEstimation.stateValues);
            int i3 = 0;
            while (i3 < cpuScalingStepCount) {
                if (intermediates.timeByScalingStep[i3] != 0) {
                    long timeByScalingStep = this.mStatsLayout.getTimeByScalingStep(this.mTmpDeviceStatsArray, i3);
                    i = cpuScalingStepCount;
                    i2 = cpuPowerBracketCount;
                    double d = (intermediates.powerByScalingStep[i3] * timeByScalingStep) / intermediates.timeByScalingStep[i3];
                    int i4 = scalingStepToPowerBracketMap[i3];
                    long[] jArr = deviceStatsIntermediates.timeByBracket;
                    jArr[i4] = jArr[i4] + timeByScalingStep;
                    double[] dArr = deviceStatsIntermediates.powerByBracket;
                    dArr[i4] = dArr[i4] + d;
                } else {
                    i = cpuScalingStepCount;
                    i2 = cpuPowerBracketCount;
                }
                i3++;
                cpuScalingStepCount = i;
                cpuPowerBracketCount = i2;
            }
            int i5 = cpuScalingStepCount;
            int i6 = cpuPowerBracketCount;
            if (energyConsumerCount != 0) {
                adjustEstimatesUsingEnergyConsumers(intermediates, deviceStatsIntermediates);
            }
            double d2 = HOUR_IN_MILLIS;
            for (int length = deviceStatsIntermediates.powerByBracket.length - 1; length >= 0; length--) {
                d2 += deviceStatsIntermediates.powerByBracket[length];
            }
            deviceStatsIntermediates.power = d2;
            this.mStatsLayout.setDevicePowerEstimate(this.mTmpDeviceStatsArray, d2);
            powerComponentAggregatedPowerStats.setDeviceStats(deviceStateEstimation.stateValues, this.mTmpDeviceStatsArray);
            size--;
            cpuScalingStepCount = i5;
            cpuPowerBracketCount = i6;
        }
    }

    private void adjustEstimatesUsingEnergyConsumers(com.android.server.power.stats.CpuAggregatedPowerStatsProcessor.Intermediates intermediates, com.android.server.power.stats.CpuAggregatedPowerStatsProcessor.DeviceStatsIntermediates deviceStatsIntermediates) {
        int energyConsumerCount = this.mStatsLayout.getEnergyConsumerCount();
        if (energyConsumerCount == 0) {
            return;
        }
        if (intermediates.powerByEnergyConsumer == null) {
            intermediates.powerByEnergyConsumer = new long[energyConsumerCount];
        } else {
            java.util.Arrays.fill(intermediates.powerByEnergyConsumer, 0L);
        }
        for (int i = 0; i < energyConsumerCount; i++) {
            long[] jArr = intermediates.powerByEnergyConsumer;
            int i2 = this.mEnergyConsumerToCombinedEnergyConsumerMap[i];
            jArr[i2] = jArr[i2] + this.mStatsLayout.getConsumedEnergy(this.mTmpDeviceStatsArray, i);
        }
        for (int length = this.mCombinedEnergyConsumerToPowerBracketMap.length - 1; length >= 0; length--) {
            int[] iArr = this.mCombinedEnergyConsumerToPowerBracketMap[length];
            if (iArr != null) {
                double uCtoMah = com.android.server.power.stats.AggregatedPowerStatsProcessor.uCtoMah(intermediates.powerByEnergyConsumer[length]);
                double d = 0.0d;
                for (int i3 : iArr) {
                    d += deviceStatsIntermediates.powerByBracket[i3];
                }
                if (d != HOUR_IN_MILLIS) {
                    for (int i4 : iArr) {
                        deviceStatsIntermediates.powerByBracket[i4] = (deviceStatsIntermediates.powerByBracket[i4] * uCtoMah) / d;
                    }
                }
            }
        }
    }

    private void combineDeviceStateEstimates() {
        for (int size = this.mPlan.combinedDeviceStateEstimations.size() - 1; size >= 0; size--) {
            com.android.server.power.stats.AggregatedPowerStatsProcessor.CombinedDeviceStateEstimate combinedDeviceStateEstimate = this.mPlan.combinedDeviceStateEstimations.get(size);
            com.android.server.power.stats.CpuAggregatedPowerStatsProcessor.DeviceStatsIntermediates deviceStatsIntermediates = new com.android.server.power.stats.CpuAggregatedPowerStatsProcessor.DeviceStatsIntermediates();
            combinedDeviceStateEstimate.intermediates = deviceStatsIntermediates;
            int cpuPowerBracketCount = this.mStatsLayout.getCpuPowerBracketCount();
            deviceStatsIntermediates.timeByBracket = new long[cpuPowerBracketCount];
            deviceStatsIntermediates.powerByBracket = new double[cpuPowerBracketCount];
            java.util.List<com.android.server.power.stats.AggregatedPowerStatsProcessor.DeviceStateEstimation> list = combinedDeviceStateEstimate.deviceStateEstimations;
            for (int size2 = list.size() - 1; size2 >= 0; size2--) {
                com.android.server.power.stats.CpuAggregatedPowerStatsProcessor.DeviceStatsIntermediates deviceStatsIntermediates2 = (com.android.server.power.stats.CpuAggregatedPowerStatsProcessor.DeviceStatsIntermediates) list.get(size2).intermediates;
                deviceStatsIntermediates.power += deviceStatsIntermediates2.power;
                for (int i = 0; i < cpuPowerBracketCount; i++) {
                    long[] jArr = deviceStatsIntermediates.timeByBracket;
                    jArr[i] = jArr[i] + deviceStatsIntermediates2.timeByBracket[i];
                    double[] dArr = deviceStatsIntermediates.powerByBracket;
                    dArr[i] = dArr[i] + deviceStatsIntermediates2.powerByBracket[i];
                }
            }
        }
    }

    private void estimateUidPowerConsumption(com.android.server.power.stats.PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats, int i, com.android.server.power.stats.AggregatedPowerStatsProcessor.UidStateEstimate uidStateEstimate) {
        com.android.server.power.stats.CpuAggregatedPowerStatsProcessor.DeviceStatsIntermediates deviceStatsIntermediates = (com.android.server.power.stats.CpuAggregatedPowerStatsProcessor.DeviceStatsIntermediates) uidStateEstimate.combinedDeviceStateEstimate.intermediates;
        for (int i2 = 0; i2 < uidStateEstimate.proportionalEstimates.size(); i2++) {
            com.android.server.power.stats.AggregatedPowerStatsProcessor.UidStateProportionalEstimate uidStateProportionalEstimate = uidStateEstimate.proportionalEstimates.get(i2);
            if (powerComponentAggregatedPowerStats.getUidStats(this.mTmpUidStatsArray, i, uidStateProportionalEstimate.stateValues)) {
                double d = HOUR_IN_MILLIS;
                for (int i3 = 0; i3 < this.mStatsLayout.getCpuPowerBracketCount(); i3++) {
                    if (deviceStatsIntermediates.timeByBracket[i3] != 0) {
                        long uidTimeByPowerBracket = this.mStatsLayout.getUidTimeByPowerBracket(this.mTmpUidStatsArray, i3);
                        if (uidTimeByPowerBracket != 0) {
                            d += (deviceStatsIntermediates.powerByBracket[i3] * uidTimeByPowerBracket) / deviceStatsIntermediates.timeByBracket[i3];
                        }
                    }
                }
                this.mStatsLayout.setUidPowerEstimate(this.mTmpUidStatsArray, d);
                powerComponentAggregatedPowerStats.setUidStats(i, uidStateProportionalEstimate.stateValues, this.mTmpUidStatsArray);
            }
        }
    }

    @Override // com.android.server.power.stats.AggregatedPowerStatsProcessor
    public java.lang.String deviceStatsToString(com.android.internal.os.PowerStats.Descriptor descriptor, long[] jArr) {
        unpackPowerStatsDescriptor(descriptor);
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        int cpuScalingStepCount = this.mStatsLayout.getCpuScalingStepCount();
        sb.append("steps: [");
        for (int i = 0; i < cpuScalingStepCount; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(this.mStatsLayout.getTimeByScalingStep(jArr, i));
        }
        int cpuClusterCount = this.mStatsLayout.getCpuClusterCount();
        sb.append("] clusters: [");
        for (int i2 = 0; i2 < cpuClusterCount; i2++) {
            if (i2 != 0) {
                sb.append(", ");
            }
            sb.append(this.mStatsLayout.getTimeByCluster(jArr, i2));
        }
        sb.append("] uptime: ");
        sb.append(this.mStatsLayout.getUsageDuration(jArr));
        int energyConsumerCount = this.mStatsLayout.getEnergyConsumerCount();
        if (energyConsumerCount > 0) {
            sb.append(" energy: [");
            for (int i3 = 0; i3 < energyConsumerCount; i3++) {
                if (i3 != 0) {
                    sb.append(", ");
                }
                sb.append(this.mStatsLayout.getConsumedEnergy(jArr, i3));
            }
            sb.append("]");
        }
        sb.append(" power: ");
        sb.append(android.os.BatteryStats.formatCharge(this.mStatsLayout.getDevicePowerEstimate(jArr)));
        return sb.toString();
    }

    @Override // com.android.server.power.stats.AggregatedPowerStatsProcessor
    public java.lang.String uidStatsToString(com.android.internal.os.PowerStats.Descriptor descriptor, long[] jArr) {
        unpackPowerStatsDescriptor(descriptor);
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("[");
        int cpuPowerBracketCount = this.mStatsLayout.getCpuPowerBracketCount();
        for (int i = 0; i < cpuPowerBracketCount; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append(this.mStatsLayout.getUidTimeByPowerBracket(jArr, i));
        }
        sb.append("] power: ");
        sb.append(android.os.BatteryStats.formatCharge(this.mStatsLayout.getUidPowerEstimate(jArr)));
        return sb.toString();
    }
}
