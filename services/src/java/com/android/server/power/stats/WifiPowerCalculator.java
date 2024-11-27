package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class WifiPowerCalculator extends com.android.server.power.stats.PowerCalculator {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "WifiPowerCalculator";
    private static final android.os.BatteryConsumer.Key[] UNINITIALIZED_KEYS = new android.os.BatteryConsumer.Key[0];
    private final com.android.server.power.stats.UsageBasedPowerEstimator mBatchScanPowerEstimator;
    private final boolean mHasWifiPowerController;
    private final com.android.server.power.stats.UsageBasedPowerEstimator mIdlePowerEstimator;
    private final com.android.server.power.stats.UsageBasedPowerEstimator mPowerOnPowerEstimator;
    private final com.android.server.power.stats.UsageBasedPowerEstimator mRxPowerEstimator;
    private final com.android.server.power.stats.UsageBasedPowerEstimator mScanPowerEstimator;
    private final com.android.server.power.stats.UsageBasedPowerEstimator mTxPowerEstimator;
    private final double mWifiPowerPerPacket;

    private static class PowerDurationAndTraffic {
        public long durationMs;
        public android.os.BatteryConsumer.Key[] keys;
        public double powerMah;
        public double[] powerPerKeyMah;
        public long wifiRxBytes;
        public long wifiRxPackets;
        public long wifiTxBytes;
        public long wifiTxPackets;

        private PowerDurationAndTraffic() {
        }
    }

    public WifiPowerCalculator(com.android.internal.os.PowerProfile powerProfile) {
        this.mPowerOnPowerEstimator = new com.android.server.power.stats.UsageBasedPowerEstimator(powerProfile.getAveragePower("wifi.on"));
        this.mScanPowerEstimator = new com.android.server.power.stats.UsageBasedPowerEstimator(powerProfile.getAveragePower("wifi.scan"));
        this.mBatchScanPowerEstimator = new com.android.server.power.stats.UsageBasedPowerEstimator(powerProfile.getAveragePower("wifi.batchedscan"));
        this.mIdlePowerEstimator = new com.android.server.power.stats.UsageBasedPowerEstimator(powerProfile.getAveragePower("wifi.controller.idle"));
        this.mTxPowerEstimator = new com.android.server.power.stats.UsageBasedPowerEstimator(powerProfile.getAveragePower("wifi.controller.tx"));
        this.mRxPowerEstimator = new com.android.server.power.stats.UsageBasedPowerEstimator(powerProfile.getAveragePower("wifi.controller.rx"));
        this.mWifiPowerPerPacket = getWifiPowerPerPacket(powerProfile);
        this.mHasWifiPowerController = this.mIdlePowerEstimator.isSupported() && this.mTxPowerEstimator.isSupported() && this.mRxPowerEstimator.isSupported();
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public boolean isPowerComponentSupported(int i) {
        return i == 11;
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public void calculate(android.os.BatteryUsageStats.Builder builder, android.os.BatteryStats batteryStats, long j, long j2, android.os.BatteryUsageStatsQuery batteryUsageStatsQuery) {
        android.os.BatteryUsageStatsQuery batteryUsageStatsQuery2 = batteryUsageStatsQuery;
        android.os.BatteryConsumer.Key[] keyArr = UNINITIALIZED_KEYS;
        com.android.server.power.stats.WifiPowerCalculator.PowerDurationAndTraffic powerDurationAndTraffic = new com.android.server.power.stats.WifiPowerCalculator.PowerDurationAndTraffic();
        android.util.SparseArray uidBatteryConsumerBuilders = builder.getUidBatteryConsumerBuilders();
        int size = uidBatteryConsumerBuilders.size() - 1;
        long j3 = 0;
        double d = 0.0d;
        while (size >= 0) {
            android.os.UidBatteryConsumer.Builder builder2 = (android.os.UidBatteryConsumer.Builder) uidBatteryConsumerBuilders.valueAt(size);
            if (keyArr == UNINITIALIZED_KEYS) {
                if (batteryUsageStatsQuery.isProcessStateDataNeeded()) {
                    keyArr = builder2.getKeys(11);
                    powerDurationAndTraffic.keys = keyArr;
                    powerDurationAndTraffic.powerPerKeyMah = new double[keyArr.length];
                } else {
                    keyArr = null;
                }
            }
            long wifiEnergyConsumptionUC = builder2.getBatteryStatsUid().getWifiEnergyConsumptionUC();
            int powerModel = com.android.server.power.stats.PowerCalculator.getPowerModel(wifiEnergyConsumptionUC, batteryUsageStatsQuery2);
            double d2 = d;
            int i = size;
            calculateApp(powerDurationAndTraffic, builder2.getBatteryStatsUid(), powerModel, j, 0, batteryStats.hasWifiActivityReporting(), wifiEnergyConsumptionUC);
            if (builder2.isVirtualUid()) {
                d = d2;
            } else {
                j3 += powerDurationAndTraffic.durationMs;
                d = d2 + powerDurationAndTraffic.powerMah;
            }
            builder2.setUsageDurationMillis(11, powerDurationAndTraffic.durationMs);
            builder2.setConsumedPower(11, powerDurationAndTraffic.powerMah, powerModel);
            if (batteryUsageStatsQuery.isProcessStateDataNeeded() && keyArr != null) {
                for (int i2 = 0; i2 < keyArr.length; i2++) {
                    android.os.BatteryConsumer.Key key = keyArr[i2];
                    if (key.processState != 0) {
                        builder2.setConsumedPower(key, powerDurationAndTraffic.powerPerKeyMah[i2], powerModel);
                    }
                }
            }
            size = i - 1;
            batteryUsageStatsQuery2 = batteryUsageStatsQuery;
        }
        double d3 = d;
        long wifiEnergyConsumptionUC2 = batteryStats.getWifiEnergyConsumptionUC();
        int powerModel2 = com.android.server.power.stats.PowerCalculator.getPowerModel(wifiEnergyConsumptionUC2, batteryUsageStatsQuery);
        calculateRemaining(powerDurationAndTraffic, powerModel2, batteryStats, j, 0, batteryStats.hasWifiActivityReporting(), j3, d3, wifiEnergyConsumptionUC2);
        builder.getAggregateBatteryConsumerBuilder(0).setUsageDurationMillis(11, powerDurationAndTraffic.durationMs).setConsumedPower(11, d3 + powerDurationAndTraffic.powerMah, powerModel2);
        builder.getAggregateBatteryConsumerBuilder(1).setConsumedPower(11, d3, powerModel2);
    }

    private void calculateApp(com.android.server.power.stats.WifiPowerCalculator.PowerDurationAndTraffic powerDurationAndTraffic, android.os.BatteryStats.Uid uid, int i, long j, int i2, boolean z, long j2) {
        powerDurationAndTraffic.wifiRxPackets = uid.getNetworkActivityPackets(2, i2);
        powerDurationAndTraffic.wifiTxPackets = uid.getNetworkActivityPackets(3, i2);
        powerDurationAndTraffic.wifiRxBytes = uid.getNetworkActivityBytes(2, i2);
        powerDurationAndTraffic.wifiTxBytes = uid.getNetworkActivityBytes(3, i2);
        if (z && this.mHasWifiPowerController) {
            android.os.BatteryStats.ControllerActivityCounter wifiControllerActivity = uid.getWifiControllerActivity();
            if (wifiControllerActivity != null) {
                android.os.BatteryStats.LongCounter rxTimeCounter = wifiControllerActivity.getRxTimeCounter();
                android.os.BatteryStats.LongCounter longCounter = wifiControllerActivity.getTxTimeCounters()[0];
                android.os.BatteryStats.LongCounter idleTimeCounter = wifiControllerActivity.getIdleTimeCounter();
                long countLocked = rxTimeCounter.getCountLocked(i2);
                long countLocked2 = longCounter.getCountLocked(i2);
                long countLocked3 = idleTimeCounter.getCountLocked(i2);
                powerDurationAndTraffic.durationMs = countLocked3 + countLocked + countLocked2;
                if (i == 1) {
                    powerDurationAndTraffic.powerMah = calcPowerFromControllerDataMah(countLocked, countLocked2, countLocked3);
                } else {
                    powerDurationAndTraffic.powerMah = com.android.server.power.stats.PowerCalculator.uCtoMah(j2);
                }
                if (powerDurationAndTraffic.keys != null) {
                    for (int i3 = 0; i3 < powerDurationAndTraffic.keys.length; i3++) {
                        int i4 = powerDurationAndTraffic.keys[i3].processState;
                        if (i4 != 0) {
                            if (i == 1) {
                                powerDurationAndTraffic.powerPerKeyMah[i3] = calcPowerFromControllerDataMah(rxTimeCounter.getCountForProcessState(i4), longCounter.getCountForProcessState(i4), idleTimeCounter.getCountForProcessState(i4));
                            } else {
                                powerDurationAndTraffic.powerPerKeyMah[i3] = com.android.server.power.stats.PowerCalculator.uCtoMah(uid.getWifiEnergyConsumptionUC(i4));
                            }
                        }
                    }
                    return;
                }
                return;
            }
            powerDurationAndTraffic.durationMs = 0L;
            powerDurationAndTraffic.powerMah = 0.0d;
            if (powerDurationAndTraffic.powerPerKeyMah != null) {
                java.util.Arrays.fill(powerDurationAndTraffic.powerPerKeyMah, 0.0d);
                return;
            }
            return;
        }
        long wifiRunningTime = uid.getWifiRunningTime(j, i2) / 1000;
        powerDurationAndTraffic.durationMs = wifiRunningTime;
        if (i != 1) {
            powerDurationAndTraffic.powerMah = com.android.server.power.stats.PowerCalculator.uCtoMah(j2);
        } else {
            long wifiScanTime = uid.getWifiScanTime(j, i2) / 1000;
            long j3 = 0;
            for (int i5 = 0; i5 < 5; i5++) {
                j3 += uid.getWifiBatchedScanTime(i5, j, i2) / 1000;
            }
            powerDurationAndTraffic.powerMah = calcPowerWithoutControllerDataMah(powerDurationAndTraffic.wifiRxPackets, powerDurationAndTraffic.wifiTxPackets, wifiRunningTime, wifiScanTime, j3);
        }
        if (powerDurationAndTraffic.powerPerKeyMah != null) {
            java.util.Arrays.fill(powerDurationAndTraffic.powerPerKeyMah, 0.0d);
        }
    }

    private void calculateRemaining(com.android.server.power.stats.WifiPowerCalculator.PowerDurationAndTraffic powerDurationAndTraffic, int i, android.os.BatteryStats batteryStats, long j, int i2, boolean z, long j2, double d, long j3) {
        double d2;
        long j4;
        if (i != 2) {
            d2 = 0.0d;
        } else {
            d2 = com.android.server.power.stats.PowerCalculator.uCtoMah(j3);
        }
        if (z && this.mHasWifiPowerController) {
            android.os.BatteryStats.ControllerActivityCounter wifiControllerActivity = batteryStats.getWifiControllerActivity();
            long countLocked = wifiControllerActivity.getIdleTimeCounter().getCountLocked(i2);
            long countLocked2 = wifiControllerActivity.getTxTimeCounters()[0].getCountLocked(i2);
            long countLocked3 = wifiControllerActivity.getRxTimeCounter().getCountLocked(i2);
            j4 = countLocked + countLocked3 + countLocked2;
            if (i == 1) {
                d2 = wifiControllerActivity.getPowerCounter().getCountLocked(i2) / 3600000.0d;
                if (d2 == 0.0d) {
                    d2 = calcPowerFromControllerDataMah(countLocked3, countLocked2, countLocked);
                }
            }
        } else {
            long globalWifiRunningTime = batteryStats.getGlobalWifiRunningTime(j, i2) / 1000;
            if (i != 1) {
                j4 = globalWifiRunningTime;
            } else {
                d2 = calcGlobalPowerWithoutControllerDataMah(globalWifiRunningTime);
                j4 = globalWifiRunningTime;
            }
        }
        powerDurationAndTraffic.durationMs = java.lang.Math.max(0L, j4 - j2);
        powerDurationAndTraffic.powerMah = java.lang.Math.max(0.0d, d2 - d);
    }

    public double calcPowerFromControllerDataMah(long j, long j2, long j3) {
        return this.mRxPowerEstimator.calculatePower(j) + this.mTxPowerEstimator.calculatePower(j2) + this.mIdlePowerEstimator.calculatePower(j3);
    }

    public double calcPowerWithoutControllerDataMah(long j, long j2, long j3, long j4, long j5) {
        return ((j + j2) * this.mWifiPowerPerPacket) + this.mPowerOnPowerEstimator.calculatePower(j3) + this.mScanPowerEstimator.calculatePower(j4) + this.mBatchScanPowerEstimator.calculatePower(j5);
    }

    public double calcGlobalPowerWithoutControllerDataMah(long j) {
        return this.mPowerOnPowerEstimator.calculatePower(j);
    }

    private static double getWifiPowerPerPacket(com.android.internal.os.PowerProfile powerProfile) {
        return (powerProfile.getAveragePower("wifi.active") / 3600.0d) / 61.03515625d;
    }
}
