package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class AggregatedPowerStatsConfig {
    private static final com.android.server.power.stats.AggregatedPowerStatsProcessor NO_OP_PROCESSOR;
    static final int POWER_STATE_BATTERY = 0;
    static final int POWER_STATE_OTHER = 1;
    static final int SCREEN_STATE_ON = 0;
    static final int SCREEN_STATE_OTHER = 1;
    static final java.lang.String[] STATE_LABELS_PROCESS_STATE;
    static final java.lang.String STATE_NAME_POWER = "pwr";
    static final java.lang.String STATE_NAME_PROCESS_STATE = "ps";
    static final java.lang.String STATE_NAME_SCREEN = "scr";
    public static final int STATE_POWER = 0;
    public static final int STATE_PROCESS_STATE = 2;
    public static final int STATE_SCREEN = 1;
    private final java.util.List<com.android.server.power.stats.AggregatedPowerStatsConfig.PowerComponent> mPowerComponents = new java.util.ArrayList();
    static final java.lang.String[] STATE_LABELS_POWER = {"pwr-battery", "pwr-other"};
    static final java.lang.String[] STATE_LABELS_SCREEN = {"scr-on", "scr-other"};

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TrackedState {
    }

    static {
        java.lang.String[] strArr = new java.lang.String[5];
        for (int i = 0; i < 5; i++) {
            strArr[i] = android.os.BatteryConsumer.processStateToString(i);
        }
        STATE_LABELS_PROCESS_STATE = strArr;
        NO_OP_PROCESSOR = new com.android.server.power.stats.AggregatedPowerStatsProcessor() { // from class: com.android.server.power.stats.AggregatedPowerStatsConfig.1
            @Override // com.android.server.power.stats.AggregatedPowerStatsProcessor
            public void finish(com.android.server.power.stats.PowerComponentAggregatedPowerStats powerComponentAggregatedPowerStats) {
            }

            @Override // com.android.server.power.stats.AggregatedPowerStatsProcessor
            public java.lang.String deviceStatsToString(com.android.internal.os.PowerStats.Descriptor descriptor, long[] jArr) {
                return java.util.Arrays.toString(jArr);
            }

            @Override // com.android.server.power.stats.AggregatedPowerStatsProcessor
            public java.lang.String uidStatsToString(com.android.internal.os.PowerStats.Descriptor descriptor, long[] jArr) {
                return java.util.Arrays.toString(jArr);
            }
        };
    }

    public static class PowerComponent {
        private final int mPowerComponentId;
        private com.android.server.power.stats.AggregatedPowerStatsProcessor mProcessor = com.android.server.power.stats.AggregatedPowerStatsConfig.NO_OP_PROCESSOR;
        private int[] mTrackedDeviceStates;
        private int[] mTrackedUidStates;

        PowerComponent(int i) {
            this.mPowerComponentId = i;
        }

        public com.android.server.power.stats.AggregatedPowerStatsConfig.PowerComponent trackDeviceStates(int... iArr) {
            this.mTrackedDeviceStates = iArr;
            return this;
        }

        public com.android.server.power.stats.AggregatedPowerStatsConfig.PowerComponent trackUidStates(int... iArr) {
            this.mTrackedUidStates = iArr;
            return this;
        }

        public com.android.server.power.stats.AggregatedPowerStatsConfig.PowerComponent setProcessor(@android.annotation.NonNull com.android.server.power.stats.AggregatedPowerStatsProcessor aggregatedPowerStatsProcessor) {
            this.mProcessor = aggregatedPowerStatsProcessor;
            return this;
        }

        public int getPowerComponentId() {
            return this.mPowerComponentId;
        }

        public com.android.server.power.stats.MultiStateStats.States[] getDeviceStateConfig() {
            return new com.android.server.power.stats.MultiStateStats.States[]{new com.android.server.power.stats.MultiStateStats.States(com.android.server.power.stats.AggregatedPowerStatsConfig.STATE_NAME_POWER, isTracked(this.mTrackedDeviceStates, 0), com.android.server.power.stats.AggregatedPowerStatsConfig.STATE_LABELS_POWER), new com.android.server.power.stats.MultiStateStats.States(com.android.server.power.stats.AggregatedPowerStatsConfig.STATE_NAME_SCREEN, isTracked(this.mTrackedDeviceStates, 1), com.android.server.power.stats.AggregatedPowerStatsConfig.STATE_LABELS_SCREEN)};
        }

        public com.android.server.power.stats.MultiStateStats.States[] getUidStateConfig() {
            return new com.android.server.power.stats.MultiStateStats.States[]{new com.android.server.power.stats.MultiStateStats.States(com.android.server.power.stats.AggregatedPowerStatsConfig.STATE_NAME_POWER, isTracked(this.mTrackedUidStates, 0), com.android.server.power.stats.AggregatedPowerStatsConfig.STATE_LABELS_POWER), new com.android.server.power.stats.MultiStateStats.States(com.android.server.power.stats.AggregatedPowerStatsConfig.STATE_NAME_SCREEN, isTracked(this.mTrackedUidStates, 1), com.android.server.power.stats.AggregatedPowerStatsConfig.STATE_LABELS_SCREEN), new com.android.server.power.stats.MultiStateStats.States(com.android.server.power.stats.AggregatedPowerStatsConfig.STATE_NAME_PROCESS_STATE, isTracked(this.mTrackedUidStates, 2), com.android.server.power.stats.AggregatedPowerStatsConfig.STATE_LABELS_PROCESS_STATE)};
        }

        @android.annotation.NonNull
        public com.android.server.power.stats.AggregatedPowerStatsProcessor getProcessor() {
            return this.mProcessor;
        }

        private boolean isTracked(int[] iArr, int i) {
            if (iArr == null) {
                return false;
            }
            for (int i2 : iArr) {
                if (i2 == i) {
                    return true;
                }
            }
            return false;
        }
    }

    public com.android.server.power.stats.AggregatedPowerStatsConfig.PowerComponent trackPowerComponent(int i) {
        com.android.server.power.stats.AggregatedPowerStatsConfig.PowerComponent powerComponent = new com.android.server.power.stats.AggregatedPowerStatsConfig.PowerComponent(i);
        this.mPowerComponents.add(powerComponent);
        return powerComponent;
    }

    public java.util.List<com.android.server.power.stats.AggregatedPowerStatsConfig.PowerComponent> getPowerComponentsAggregatedStatsConfigs() {
        return this.mPowerComponents;
    }
}
