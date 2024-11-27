package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class PowerStatsAggregator {
    private static final long UNINITIALIZED = -1;
    private final com.android.server.power.stats.AggregatedPowerStatsConfig mAggregatedPowerStatsConfig;
    private final com.android.internal.os.BatteryStatsHistory mHistory;
    private com.android.server.power.stats.AggregatedPowerStats mStats;
    private final android.util.SparseArray<com.android.server.power.stats.AggregatedPowerStatsProcessor> mProcessors = new android.util.SparseArray<>();
    private int mCurrentBatteryState = 0;
    private int mCurrentScreenState = 1;

    public PowerStatsAggregator(com.android.server.power.stats.AggregatedPowerStatsConfig aggregatedPowerStatsConfig, com.android.internal.os.BatteryStatsHistory batteryStatsHistory) {
        this.mAggregatedPowerStatsConfig = aggregatedPowerStatsConfig;
        this.mHistory = batteryStatsHistory;
        for (com.android.server.power.stats.AggregatedPowerStatsConfig.PowerComponent powerComponent : aggregatedPowerStatsConfig.getPowerComponentsAggregatedStatsConfigs()) {
            this.mProcessors.put(powerComponent.getPowerComponentId(), powerComponent.getProcessor());
        }
    }

    com.android.server.power.stats.AggregatedPowerStatsConfig getConfig() {
        return this.mAggregatedPowerStatsConfig;
    }

    public void aggregatePowerStats(long j, long j2, java.util.function.Consumer<com.android.server.power.stats.AggregatedPowerStats> consumer) {
        int i;
        int i2;
        synchronized (this) {
            try {
                if (this.mStats == null) {
                    this.mStats = new com.android.server.power.stats.AggregatedPowerStats(this.mAggregatedPowerStatsConfig);
                }
                long j3 = 0;
                long j4 = -1;
                long j5 = j > 0 ? j : -1L;
                com.android.internal.os.BatteryStatsHistoryIterator iterate = this.mHistory.iterate(j, j2);
                boolean z = false;
                while (iterate.hasNext()) {
                    try {
                        android.os.BatteryStats.HistoryItem next = iterate.next();
                        if (!z) {
                            this.mStats.addClockUpdate(next.time, next.currentTime);
                            if (j5 == j4) {
                                j5 = next.time;
                            }
                            z = true;
                        } else if (next.cmd == 5 || next.cmd == 7) {
                            this.mStats.addClockUpdate(next.time, next.currentTime);
                        }
                        long j6 = next.time;
                        if ((next.states & 524288) != 0) {
                            i = 1;
                        } else {
                            i = 0;
                        }
                        if (i != this.mCurrentBatteryState) {
                            this.mStats.setDeviceState(0, i, next.time);
                            this.mCurrentBatteryState = i;
                        }
                        if ((next.states & 1048576) != 0) {
                            i2 = 0;
                        } else {
                            i2 = 1;
                        }
                        if (i2 != this.mCurrentScreenState) {
                            this.mStats.setDeviceState(1, i2, next.time);
                            this.mCurrentScreenState = i2;
                        }
                        if (next.processStateChange != null) {
                            this.mStats.setUidState(next.processStateChange.uid, 2, next.processStateChange.processState, next.time);
                        }
                        if (next.powerStats != null) {
                            if (!this.mStats.isCompatible(next.powerStats)) {
                                if (j6 > j5) {
                                    this.mStats.setDuration(j6 - j5);
                                    finish(this.mStats);
                                    consumer.accept(this.mStats);
                                }
                                this.mStats.reset();
                                this.mStats.addClockUpdate(next.time, next.currentTime);
                                j5 = next.time;
                                j6 = j5;
                            }
                            this.mStats.addPowerStats(next.powerStats, next.time);
                        }
                        j3 = j6;
                        j4 = -1;
                    } finally {
                    }
                }
                iterate.close();
                if (j3 > j5) {
                    this.mStats.setDuration(j3 - j5);
                    finish(this.mStats);
                    consumer.accept(this.mStats);
                }
                this.mStats.reset();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void finish(com.android.server.power.stats.AggregatedPowerStats aggregatedPowerStats) {
        for (int i = 0; i < this.mProcessors.size(); i++) {
            com.android.server.power.stats.PowerComponentAggregatedPowerStats powerComponentStats = aggregatedPowerStats.getPowerComponentStats(this.mProcessors.keyAt(i));
            if (powerComponentStats != null) {
                this.mProcessors.valueAt(i).finish(powerComponentStats);
            }
        }
    }

    public void reset() {
        synchronized (this) {
            this.mStats = null;
        }
    }
}
