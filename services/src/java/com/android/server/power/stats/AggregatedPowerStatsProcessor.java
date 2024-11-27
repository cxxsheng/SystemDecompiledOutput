package com.android.server.power.stats;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public abstract class AggregatedPowerStatsProcessor {
    private static final int INDEX_DOES_NOT_EXIST = -1;
    private static final double MILLIAMPHOUR_PER_MICROCOULOMB = 2.777777777777778E-7d;
    private static final java.lang.String TAG = "AggregatedPowerStatsProcessor";

    AggregatedPowerStatsProcessor() {
    }

    abstract java.lang.String deviceStatsToString(com.android.internal.os.PowerStats.Descriptor descriptor, long[] jArr);

    abstract void finish(com.android.server.power.stats.PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats);

    abstract java.lang.String uidStatsToString(com.android.internal.os.PowerStats.Descriptor descriptor, long[] jArr);

    protected static class PowerEstimationPlan {
        private final com.android.server.power.stats.AggregatedPowerStatsConfig.PowerComponent mConfig;
        public java.util.List<com.android.server.power.stats.AggregatedPowerStatsProcessor.DeviceStateEstimation> deviceStateEstimations = new java.util.ArrayList();
        public java.util.List<com.android.server.power.stats.AggregatedPowerStatsProcessor.CombinedDeviceStateEstimate> combinedDeviceStateEstimations = new java.util.ArrayList();
        public java.util.List<com.android.server.power.stats.AggregatedPowerStatsProcessor.UidStateEstimate> uidStateEstimates = new java.util.ArrayList();

        public PowerEstimationPlan(com.android.server.power.stats.AggregatedPowerStatsConfig.PowerComponent powerComponent) {
            this.mConfig = powerComponent;
            addDeviceStateEstimations();
            combineDeviceStateEstimations();
            addUidStateEstimations();
        }

        private void addDeviceStateEstimations() {
            com.android.server.power.stats.MultiStateStats.States[] deviceStateConfig = this.mConfig.getDeviceStateConfig();
            for (int[] iArr : com.android.server.power.stats.AggregatedPowerStatsProcessor.getAllTrackedStateCombinations(deviceStateConfig)) {
                this.deviceStateEstimations.add(new com.android.server.power.stats.AggregatedPowerStatsProcessor.DeviceStateEstimation(deviceStateConfig, iArr));
            }
        }

        private void combineDeviceStateEstimations() {
            int findTrackedStateByName;
            com.android.server.power.stats.MultiStateStats.States[] deviceStateConfig = this.mConfig.getDeviceStateConfig();
            com.android.server.power.stats.MultiStateStats.States[] uidStateConfig = this.mConfig.getUidStateConfig();
            com.android.server.power.stats.MultiStateStats.States[] statesArr = new com.android.server.power.stats.MultiStateStats.States[deviceStateConfig.length];
            for (int i = 0; i < deviceStateConfig.length; i++) {
                if (deviceStateConfig[i].isTracked() && (findTrackedStateByName = com.android.server.power.stats.AggregatedPowerStatsProcessor.findTrackedStateByName(uidStateConfig, deviceStateConfig[i].getName())) != -1 && uidStateConfig[findTrackedStateByName].isTracked()) {
                    statesArr[i] = deviceStateConfig[i];
                }
            }
            combineDeviceStateEstimationsRecursively(deviceStateConfig, statesArr, new int[deviceStateConfig.length], 0);
        }

        private void combineDeviceStateEstimationsRecursively(com.android.server.power.stats.MultiStateStats.States[] statesArr, com.android.server.power.stats.MultiStateStats.States[] statesArr2, int[] iArr, int i) {
            if (i >= statesArr.length) {
                com.android.server.power.stats.AggregatedPowerStatsProcessor.DeviceStateEstimation deviceStateEstimate = getDeviceStateEstimate(iArr);
                com.android.server.power.stats.AggregatedPowerStatsProcessor.CombinedDeviceStateEstimate combinedDeviceStateEstimate = getCombinedDeviceStateEstimate(statesArr2, iArr);
                if (combinedDeviceStateEstimate == null) {
                    combinedDeviceStateEstimate = new com.android.server.power.stats.AggregatedPowerStatsProcessor.CombinedDeviceStateEstimate(statesArr2, iArr);
                    this.combinedDeviceStateEstimations.add(combinedDeviceStateEstimate);
                }
                combinedDeviceStateEstimate.deviceStateEstimations.add(deviceStateEstimate);
                return;
            }
            if (statesArr[i].isTracked()) {
                for (int i2 = 0; i2 < statesArr[i].getLabels().length; i2++) {
                    iArr[i] = i2;
                    combineDeviceStateEstimationsRecursively(statesArr, statesArr2, iArr, i + 1);
                }
                return;
            }
            combineDeviceStateEstimationsRecursively(statesArr, statesArr2, iArr, i + 1);
        }

        private void addUidStateEstimations() {
            com.android.server.power.stats.MultiStateStats.States[] deviceStateConfig = this.mConfig.getDeviceStateConfig();
            com.android.server.power.stats.MultiStateStats.States[] uidStateConfig = this.mConfig.getUidStateConfig();
            com.android.server.power.stats.MultiStateStats.States[] statesArr = new com.android.server.power.stats.MultiStateStats.States[uidStateConfig.length];
            com.android.server.power.stats.MultiStateStats.States[] statesArr2 = new com.android.server.power.stats.MultiStateStats.States[uidStateConfig.length];
            for (int i = 0; i < uidStateConfig.length; i++) {
                if (uidStateConfig[i].isTracked()) {
                    int findTrackedStateByName = com.android.server.power.stats.AggregatedPowerStatsProcessor.findTrackedStateByName(deviceStateConfig, uidStateConfig[i].getName());
                    if (findTrackedStateByName != -1 && deviceStateConfig[findTrackedStateByName].isTracked()) {
                        statesArr[i] = uidStateConfig[i];
                    } else {
                        statesArr2[i] = uidStateConfig[i];
                    }
                }
            }
            for (int[] iArr : com.android.server.power.stats.AggregatedPowerStatsProcessor.getAllTrackedStateCombinations(uidStateConfig)) {
                com.android.server.power.stats.AggregatedPowerStatsProcessor.CombinedDeviceStateEstimate combinedDeviceStateEstimate = getCombinedDeviceStateEstimate(statesArr, iArr);
                if (combinedDeviceStateEstimate == null) {
                    android.util.Log.wtf(com.android.server.power.stats.AggregatedPowerStatsProcessor.TAG, "Mismatch in UID and combined device states: " + com.android.server.power.stats.AggregatedPowerStatsProcessor.concatLabels(statesArr, iArr));
                } else {
                    com.android.server.power.stats.AggregatedPowerStatsProcessor.UidStateEstimate uidStateEstimate = getUidStateEstimate(combinedDeviceStateEstimate);
                    if (uidStateEstimate == null) {
                        uidStateEstimate = new com.android.server.power.stats.AggregatedPowerStatsProcessor.UidStateEstimate(combinedDeviceStateEstimate, statesArr2);
                        this.uidStateEstimates.add(uidStateEstimate);
                    }
                    uidStateEstimate.proportionalEstimates.add(new com.android.server.power.stats.AggregatedPowerStatsProcessor.UidStateProportionalEstimate(iArr));
                }
            }
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("Step 1. Compute device-wide power estimates for state combinations:\n");
            for (com.android.server.power.stats.AggregatedPowerStatsProcessor.DeviceStateEstimation deviceStateEstimation : this.deviceStateEstimations) {
                sb.append("    ");
                sb.append(deviceStateEstimation.id);
                sb.append("\n");
            }
            sb.append("Step 2. Combine device-wide estimates that are untracked per UID:\n");
            boolean z = false;
            for (com.android.server.power.stats.AggregatedPowerStatsProcessor.CombinedDeviceStateEstimate combinedDeviceStateEstimate : this.combinedDeviceStateEstimations) {
                if (combinedDeviceStateEstimate.deviceStateEstimations.size() > 1) {
                    sb.append("    ");
                    sb.append(combinedDeviceStateEstimate.id);
                    sb.append(": ");
                    for (int i = 0; i < combinedDeviceStateEstimate.deviceStateEstimations.size(); i++) {
                        if (i != 0) {
                            sb.append(" + ");
                        }
                        sb.append(combinedDeviceStateEstimate.deviceStateEstimations.get(i).id);
                    }
                    sb.append("\n");
                    z = true;
                }
            }
            if (!z) {
                sb.append("    N/A\n");
            }
            sb.append("Step 3. Proportionally distribute power estimates to UIDs:\n");
            for (com.android.server.power.stats.AggregatedPowerStatsProcessor.UidStateEstimate uidStateEstimate : this.uidStateEstimates) {
                sb.append("    ");
                sb.append(uidStateEstimate.combinedDeviceStateEstimate.id);
                sb.append("\n        among: ");
                for (int i2 = 0; i2 < uidStateEstimate.proportionalEstimates.size(); i2++) {
                    com.android.server.power.stats.AggregatedPowerStatsProcessor.UidStateProportionalEstimate uidStateProportionalEstimate = uidStateEstimate.proportionalEstimates.get(i2);
                    if (i2 != 0) {
                        sb.append(", ");
                    }
                    sb.append(com.android.server.power.stats.AggregatedPowerStatsProcessor.concatLabels(uidStateEstimate.states, uidStateProportionalEstimate.stateValues));
                }
                sb.append("\n");
            }
            return sb.toString();
        }

        @android.annotation.Nullable
        public com.android.server.power.stats.AggregatedPowerStatsProcessor.DeviceStateEstimation getDeviceStateEstimate(int[] iArr) {
            java.lang.String concatLabels = com.android.server.power.stats.AggregatedPowerStatsProcessor.concatLabels(this.mConfig.getDeviceStateConfig(), iArr);
            for (int i = 0; i < this.deviceStateEstimations.size(); i++) {
                com.android.server.power.stats.AggregatedPowerStatsProcessor.DeviceStateEstimation deviceStateEstimation = this.deviceStateEstimations.get(i);
                if (deviceStateEstimation.id.equals(concatLabels)) {
                    return deviceStateEstimation;
                }
            }
            return null;
        }

        public com.android.server.power.stats.AggregatedPowerStatsProcessor.CombinedDeviceStateEstimate getCombinedDeviceStateEstimate(com.android.server.power.stats.MultiStateStats.States[] statesArr, int[] iArr) {
            java.lang.String concatLabels = com.android.server.power.stats.AggregatedPowerStatsProcessor.concatLabels(statesArr, iArr);
            for (int i = 0; i < this.combinedDeviceStateEstimations.size(); i++) {
                com.android.server.power.stats.AggregatedPowerStatsProcessor.CombinedDeviceStateEstimate combinedDeviceStateEstimate = this.combinedDeviceStateEstimations.get(i);
                if (combinedDeviceStateEstimate.id.equals(concatLabels)) {
                    return combinedDeviceStateEstimate;
                }
            }
            return null;
        }

        public com.android.server.power.stats.AggregatedPowerStatsProcessor.UidStateEstimate getUidStateEstimate(com.android.server.power.stats.AggregatedPowerStatsProcessor.CombinedDeviceStateEstimate combinedDeviceStateEstimate) {
            for (int i = 0; i < this.uidStateEstimates.size(); i++) {
                com.android.server.power.stats.AggregatedPowerStatsProcessor.UidStateEstimate uidStateEstimate = this.uidStateEstimates.get(i);
                if (uidStateEstimate.combinedDeviceStateEstimate == combinedDeviceStateEstimate) {
                    return uidStateEstimate;
                }
            }
            return null;
        }

        public void resetIntermediates() {
            int size = this.deviceStateEstimations.size();
            while (true) {
                size--;
                if (size < 0) {
                    break;
                } else {
                    this.deviceStateEstimations.get(size).intermediates = null;
                }
            }
            for (int size2 = this.deviceStateEstimations.size() - 1; size2 >= 0; size2--) {
                this.deviceStateEstimations.get(size2).intermediates = null;
            }
            for (int size3 = this.uidStateEstimates.size() - 1; size3 >= 0; size3--) {
                java.util.List<com.android.server.power.stats.AggregatedPowerStatsProcessor.UidStateProportionalEstimate> list = this.uidStateEstimates.get(size3).proportionalEstimates;
                for (int size4 = list.size() - 1; size4 >= 0; size4--) {
                    list.get(size4).intermediates = null;
                }
            }
        }
    }

    protected static class DeviceStateEstimation {
        public final java.lang.String id;
        public java.lang.Object intermediates;
        public final int[] stateValues;

        public DeviceStateEstimation(com.android.server.power.stats.MultiStateStats.States[] statesArr, int[] iArr) {
            this.id = com.android.server.power.stats.AggregatedPowerStatsProcessor.concatLabels(statesArr, iArr);
            this.stateValues = iArr;
        }
    }

    protected static class CombinedDeviceStateEstimate {
        public java.util.List<com.android.server.power.stats.AggregatedPowerStatsProcessor.DeviceStateEstimation> deviceStateEstimations = new java.util.ArrayList();
        public final java.lang.String id;
        public java.lang.Object intermediates;

        public CombinedDeviceStateEstimate(com.android.server.power.stats.MultiStateStats.States[] statesArr, int[] iArr) {
            this.id = com.android.server.power.stats.AggregatedPowerStatsProcessor.concatLabels(statesArr, iArr);
        }
    }

    protected static class UidStateEstimate {
        public com.android.server.power.stats.AggregatedPowerStatsProcessor.CombinedDeviceStateEstimate combinedDeviceStateEstimate;
        public java.util.List<com.android.server.power.stats.AggregatedPowerStatsProcessor.UidStateProportionalEstimate> proportionalEstimates = new java.util.ArrayList();
        public final com.android.server.power.stats.MultiStateStats.States[] states;

        public UidStateEstimate(com.android.server.power.stats.AggregatedPowerStatsProcessor.CombinedDeviceStateEstimate combinedDeviceStateEstimate, com.android.server.power.stats.MultiStateStats.States[] statesArr) {
            this.combinedDeviceStateEstimate = combinedDeviceStateEstimate;
            this.states = statesArr;
        }
    }

    protected static class UidStateProportionalEstimate {
        public java.lang.Object intermediates;
        public final int[] stateValues;

        protected UidStateProportionalEstimate(int[] iArr) {
            this.stateValues = iArr;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int findTrackedStateByName(com.android.server.power.stats.MultiStateStats.States[] statesArr, java.lang.String str) {
        for (int i = 0; i < statesArr.length; i++) {
            if (statesArr[i].getName().equals(str)) {
                return i;
            }
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @android.annotation.NonNull
    public static java.lang.String concatLabels(com.android.server.power.stats.MultiStateStats.States[] statesArr, int[] iArr) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < statesArr.length; i++) {
            if (statesArr[i] != null && statesArr[i].isTracked()) {
                arrayList.add(statesArr[i].getName() + "=" + statesArr[i].getLabels()[iArr[i]]);
            }
        }
        java.util.Collections.sort(arrayList);
        return arrayList.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int[][] getAllTrackedStateCombinations(com.android.server.power.stats.MultiStateStats.States[] statesArr) {
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        com.android.server.power.stats.MultiStateStats.States.forEachTrackedStateCombination(statesArr, new java.util.function.Consumer() { // from class: com.android.server.power.stats.AggregatedPowerStatsProcessor$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.power.stats.AggregatedPowerStatsProcessor.lambda$getAllTrackedStateCombinations$0(arrayList, (int[]) obj);
            }
        });
        return (int[][]) arrayList.toArray((int[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Integer.TYPE, arrayList.size(), 0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getAllTrackedStateCombinations$0(java.util.List list, int[] iArr) {
        list.add(java.util.Arrays.copyOf(iArr, iArr.length));
    }

    public static double uCtoMah(long j) {
        return j * MILLIAMPHOUR_PER_MICROCOULOMB;
    }
}
