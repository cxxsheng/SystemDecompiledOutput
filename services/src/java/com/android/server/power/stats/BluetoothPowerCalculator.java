package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class BluetoothPowerCalculator extends com.android.server.power.stats.PowerCalculator {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "BluetoothPowerCalc";
    private static final android.os.BatteryConsumer.Key[] UNINITIALIZED_KEYS = new android.os.BatteryConsumer.Key[0];
    private final boolean mHasBluetoothPowerController;
    private final double mIdleMa;
    private final double mRxMa;
    private final double mTxMa;

    private static class PowerAndDuration {
        public long durationMs;
        public android.os.BatteryConsumer.Key[] keys;
        public double powerMah;
        public double[] powerPerKeyMah;
        public long totalDurationMs;
        public double totalPowerMah;

        private PowerAndDuration() {
        }
    }

    public BluetoothPowerCalculator(com.android.internal.os.PowerProfile powerProfile) {
        this.mIdleMa = powerProfile.getAveragePower("bluetooth.controller.idle");
        this.mRxMa = powerProfile.getAveragePower("bluetooth.controller.rx");
        this.mTxMa = powerProfile.getAveragePower("bluetooth.controller.tx");
        this.mHasBluetoothPowerController = (this.mIdleMa == 0.0d || this.mRxMa == 0.0d || this.mTxMa == 0.0d) ? false : true;
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public boolean isPowerComponentSupported(int i) {
        return i == 2;
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public void calculate(android.os.BatteryUsageStats.Builder builder, android.os.BatteryStats batteryStats, long j, long j2, android.os.BatteryUsageStatsQuery batteryUsageStatsQuery) {
        if (!batteryStats.hasBluetoothActivityReporting()) {
            return;
        }
        android.os.BatteryConsumer.Key[] keyArr = UNINITIALIZED_KEYS;
        com.android.server.power.stats.BluetoothPowerCalculator.PowerAndDuration powerAndDuration = new com.android.server.power.stats.BluetoothPowerCalculator.PowerAndDuration();
        android.util.SparseArray uidBatteryConsumerBuilders = builder.getUidBatteryConsumerBuilders();
        for (int size = uidBatteryConsumerBuilders.size() - 1; size >= 0; size--) {
            android.os.UidBatteryConsumer.Builder builder2 = (android.os.UidBatteryConsumer.Builder) uidBatteryConsumerBuilders.valueAt(size);
            if (keyArr == UNINITIALIZED_KEYS) {
                if (batteryUsageStatsQuery.isProcessStateDataNeeded()) {
                    keyArr = builder2.getKeys(2);
                    powerAndDuration.keys = keyArr;
                    powerAndDuration.powerPerKeyMah = new double[keyArr.length];
                } else {
                    keyArr = null;
                }
            }
            calculateApp(builder2, powerAndDuration, batteryUsageStatsQuery);
        }
        long bluetoothEnergyConsumptionUC = batteryStats.getBluetoothEnergyConsumptionUC();
        int powerModel = com.android.server.power.stats.PowerCalculator.getPowerModel(bluetoothEnergyConsumptionUC, batteryUsageStatsQuery);
        calculatePowerAndDuration(null, powerModel, bluetoothEnergyConsumptionUC, batteryStats.getBluetoothControllerActivity(), batteryUsageStatsQuery.shouldForceUsePowerProfileModel(), powerAndDuration);
        java.lang.Math.max(0L, powerAndDuration.durationMs - powerAndDuration.totalDurationMs);
        builder.getAggregateBatteryConsumerBuilder(0).setUsageDurationMillis(2, powerAndDuration.durationMs).setConsumedPower(2, java.lang.Math.max(powerAndDuration.powerMah, powerAndDuration.totalPowerMah), powerModel);
        builder.getAggregateBatteryConsumerBuilder(1).setUsageDurationMillis(2, powerAndDuration.totalDurationMs).setConsumedPower(2, powerAndDuration.totalPowerMah, powerModel);
    }

    private void calculateApp(android.os.UidBatteryConsumer.Builder builder, com.android.server.power.stats.BluetoothPowerCalculator.PowerAndDuration powerAndDuration, android.os.BatteryUsageStatsQuery batteryUsageStatsQuery) {
        long bluetoothEnergyConsumptionUC = builder.getBatteryStatsUid().getBluetoothEnergyConsumptionUC();
        int powerModel = com.android.server.power.stats.PowerCalculator.getPowerModel(bluetoothEnergyConsumptionUC, batteryUsageStatsQuery);
        calculatePowerAndDuration(builder.getBatteryStatsUid(), powerModel, bluetoothEnergyConsumptionUC, builder.getBatteryStatsUid().getBluetoothControllerActivity(), batteryUsageStatsQuery.shouldForceUsePowerProfileModel(), powerAndDuration);
        builder.setUsageDurationMillis(2, powerAndDuration.durationMs).setConsumedPower(2, powerAndDuration.powerMah, powerModel);
        if (!builder.isVirtualUid()) {
            powerAndDuration.totalDurationMs += powerAndDuration.durationMs;
            powerAndDuration.totalPowerMah += powerAndDuration.powerMah;
        }
        if (batteryUsageStatsQuery.isProcessStateDataNeeded() && powerAndDuration.keys != null) {
            for (int i = 0; i < powerAndDuration.keys.length; i++) {
                android.os.BatteryConsumer.Key key = powerAndDuration.keys[i];
                if (key.processState != 0) {
                    builder.setConsumedPower(key, powerAndDuration.powerPerKeyMah[i], powerModel);
                }
            }
        }
    }

    private void calculatePowerAndDuration(@android.annotation.Nullable android.os.BatteryStats.Uid uid, int i, long j, android.os.BatteryStats.ControllerActivityCounter controllerActivityCounter, boolean z, com.android.server.power.stats.BluetoothPowerCalculator.PowerAndDuration powerAndDuration) {
        if (controllerActivityCounter == null) {
            powerAndDuration.durationMs = 0L;
            powerAndDuration.powerMah = 0.0d;
            if (powerAndDuration.powerPerKeyMah != null) {
                java.util.Arrays.fill(powerAndDuration.powerPerKeyMah, 0.0d);
                return;
            }
            return;
        }
        android.os.BatteryStats.LongCounter idleTimeCounter = controllerActivityCounter.getIdleTimeCounter();
        android.os.BatteryStats.LongCounter rxTimeCounter = controllerActivityCounter.getRxTimeCounter();
        android.os.BatteryStats.LongCounter longCounter = controllerActivityCounter.getTxTimeCounters()[0];
        long countLocked = idleTimeCounter.getCountLocked(0);
        long countLocked2 = rxTimeCounter.getCountLocked(0);
        long countLocked3 = longCounter.getCountLocked(0);
        powerAndDuration.durationMs = countLocked + countLocked2 + countLocked3;
        if (i == 2) {
            powerAndDuration.powerMah = com.android.server.power.stats.PowerCalculator.uCtoMah(j);
            if (uid == null || powerAndDuration.keys == null) {
                return;
            }
            for (int i2 = 0; i2 < powerAndDuration.keys.length; i2++) {
                int i3 = powerAndDuration.keys[i2].processState;
                if (i3 != 0) {
                    powerAndDuration.powerPerKeyMah[i2] = com.android.server.power.stats.PowerCalculator.uCtoMah(uid.getBluetoothEnergyConsumptionUC(i3));
                }
            }
            return;
        }
        if (!z) {
            double countLocked4 = controllerActivityCounter.getPowerCounter().getCountLocked(0) / 3600000.0d;
            if (countLocked4 != 0.0d) {
                powerAndDuration.powerMah = countLocked4;
                if (powerAndDuration.powerPerKeyMah != null) {
                    java.util.Arrays.fill(powerAndDuration.powerPerKeyMah, 0.0d);
                    return;
                }
                return;
            }
        }
        if (this.mHasBluetoothPowerController) {
            powerAndDuration.powerMah = calculatePowerMah(countLocked2, countLocked3, countLocked);
            if (powerAndDuration.keys != null) {
                for (int i4 = 0; i4 < powerAndDuration.keys.length; i4++) {
                    int i5 = powerAndDuration.keys[i4].processState;
                    if (i5 != 0) {
                        powerAndDuration.powerPerKeyMah[i4] = calculatePowerMah(rxTimeCounter.getCountForProcessState(i5), longCounter.getCountForProcessState(i5), idleTimeCounter.getCountForProcessState(i5));
                    }
                }
                return;
            }
            return;
        }
        powerAndDuration.powerMah = 0.0d;
        if (powerAndDuration.powerPerKeyMah != null) {
            java.util.Arrays.fill(powerAndDuration.powerPerKeyMah, 0.0d);
        }
    }

    public double calculatePowerMah(long j, long j2, long j3) {
        return (((j3 * this.mIdleMa) + (j * this.mRxMa)) + (j2 * this.mTxMa)) / 3600000.0d;
    }
}
