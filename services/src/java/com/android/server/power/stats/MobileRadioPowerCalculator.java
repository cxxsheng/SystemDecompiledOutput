package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class MobileRadioPowerCalculator extends com.android.server.power.stats.PowerCalculator {
    private static final boolean DEBUG = false;
    private static final int IGNORE = -1;
    private static final double MILLIS_IN_HOUR = 3600000.0d;
    private static final java.lang.String TAG = "MobRadioPowerCalculator";
    private final com.android.server.power.stats.UsageBasedPowerEstimator mActivePowerEstimator;

    @android.annotation.Nullable
    private final com.android.server.power.stats.UsageBasedPowerEstimator mIdlePowerEstimator;
    private final com.android.server.power.stats.UsageBasedPowerEstimator[] mIdlePowerEstimators = new com.android.server.power.stats.UsageBasedPowerEstimator[NUM_SIGNAL_STRENGTH_LEVELS];
    private final com.android.internal.os.PowerProfile mPowerProfile;
    private final com.android.server.power.stats.UsageBasedPowerEstimator mScanPowerEstimator;

    @android.annotation.Nullable
    private final com.android.server.power.stats.UsageBasedPowerEstimator mSleepPowerEstimator;
    private static final int NUM_SIGNAL_STRENGTH_LEVELS = android.telephony.CellSignalStrength.getNumSignalStrengthLevels();
    private static final android.os.BatteryConsumer.Key[] UNINITIALIZED_KEYS = new android.os.BatteryConsumer.Key[0];

    private static class PowerAndDuration {
        public long remainingDurationMs;
        public double remainingPowerMah;
        public long totalAppDurationMs;
        public double totalAppPowerMah;

        private PowerAndDuration() {
        }
    }

    public MobileRadioPowerCalculator(com.android.internal.os.PowerProfile powerProfile) {
        this.mPowerProfile = powerProfile;
        double averageBatteryDrainOrDefaultMa = this.mPowerProfile.getAverageBatteryDrainOrDefaultMa(4294967296L, Double.NaN);
        if (java.lang.Double.isNaN(averageBatteryDrainOrDefaultMa)) {
            this.mSleepPowerEstimator = null;
        } else {
            this.mSleepPowerEstimator = new com.android.server.power.stats.UsageBasedPowerEstimator(averageBatteryDrainOrDefaultMa);
        }
        double averageBatteryDrainOrDefaultMa2 = this.mPowerProfile.getAverageBatteryDrainOrDefaultMa(4563402752L, Double.NaN);
        if (java.lang.Double.isNaN(averageBatteryDrainOrDefaultMa2)) {
            this.mIdlePowerEstimator = null;
        } else {
            this.mIdlePowerEstimator = new com.android.server.power.stats.UsageBasedPowerEstimator(averageBatteryDrainOrDefaultMa2);
        }
        double averagePowerOrDefault = powerProfile.getAveragePowerOrDefault("radio.active", Double.NaN);
        if (java.lang.Double.isNaN(averagePowerOrDefault)) {
            double averagePower = powerProfile.getAveragePower("modem.controller.rx") + 0.0d;
            for (int i = 0; i < NUM_SIGNAL_STRENGTH_LEVELS; i++) {
                averagePower += powerProfile.getAveragePower("modem.controller.tx", i);
            }
            averagePowerOrDefault = averagePower / (NUM_SIGNAL_STRENGTH_LEVELS + 1);
        }
        this.mActivePowerEstimator = new com.android.server.power.stats.UsageBasedPowerEstimator(averagePowerOrDefault);
        if (java.lang.Double.isNaN(powerProfile.getAveragePowerOrDefault("radio.on", Double.NaN))) {
            double averagePower2 = powerProfile.getAveragePower("modem.controller.idle");
            this.mIdlePowerEstimators[0] = new com.android.server.power.stats.UsageBasedPowerEstimator((25.0d * averagePower2) / 180.0d);
            for (int i2 = 1; i2 < NUM_SIGNAL_STRENGTH_LEVELS; i2++) {
                this.mIdlePowerEstimators[i2] = new com.android.server.power.stats.UsageBasedPowerEstimator(java.lang.Math.max(1.0d, averagePower2 / 256.0d));
            }
        } else {
            for (int i3 = 0; i3 < NUM_SIGNAL_STRENGTH_LEVELS; i3++) {
                this.mIdlePowerEstimators[i3] = new com.android.server.power.stats.UsageBasedPowerEstimator(powerProfile.getAveragePower("radio.on", i3));
            }
        }
        this.mScanPowerEstimator = new com.android.server.power.stats.UsageBasedPowerEstimator(powerProfile.getAveragePowerOrDefault("radio.scanning", 0.0d));
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public boolean isPowerComponentSupported(int i) {
        return i == 8;
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public void calculate(android.os.BatteryUsageStats.Builder builder, android.os.BatteryStats batteryStats, long j, long j2, android.os.BatteryUsageStatsQuery batteryUsageStatsQuery) {
        double calculateActiveModemPowerMah;
        android.util.LongArrayQueue longArrayQueue;
        com.android.server.power.stats.MobileRadioPowerCalculator.PowerAndDuration powerAndDuration;
        long j3;
        int i;
        double d;
        double d2;
        java.util.ArrayList arrayList;
        long j4;
        android.os.BatteryStats.Uid uid;
        com.android.server.power.stats.MobileRadioPowerCalculator.PowerAndDuration powerAndDuration2;
        double d3;
        android.os.BatteryConsumer.Key[] keyArr;
        android.os.BatteryStats.Uid uid2;
        android.os.BatteryConsumer.Key[] keyArr2;
        java.util.ArrayList arrayList2 = null;
        com.android.server.power.stats.MobileRadioPowerCalculator.PowerAndDuration powerAndDuration3 = new com.android.server.power.stats.MobileRadioPowerCalculator.PowerAndDuration();
        long mobileRadioEnergyConsumptionUC = batteryStats.getMobileRadioEnergyConsumptionUC();
        int powerModel = com.android.server.power.stats.PowerCalculator.getPowerModel(mobileRadioEnergyConsumptionUC, batteryUsageStatsQuery);
        if (powerModel == 2) {
            longArrayQueue = null;
            calculateActiveModemPowerMah = Double.NaN;
        } else {
            calculateActiveModemPowerMah = calculateActiveModemPowerMah(batteryStats, j);
            arrayList2 = new java.util.ArrayList();
            longArrayQueue = new android.util.LongArrayQueue();
        }
        android.util.SparseArray uidBatteryConsumerBuilders = builder.getUidBatteryConsumerBuilders();
        android.os.BatteryConsumer.Key[] keyArr3 = UNINITIALIZED_KEYS;
        int size = uidBatteryConsumerBuilders.size() - 1;
        while (size >= 0) {
            android.os.UidBatteryConsumer.Builder builder2 = (android.os.UidBatteryConsumer.Builder) uidBatteryConsumerBuilders.valueAt(size);
            android.util.SparseArray sparseArray = uidBatteryConsumerBuilders;
            android.os.BatteryStats.Uid batteryStatsUid = builder2.getBatteryStatsUid();
            long j5 = mobileRadioEnergyConsumptionUC;
            if (keyArr3 == UNINITIALIZED_KEYS) {
                if (batteryUsageStatsQuery.isProcessStateDataNeeded()) {
                    keyArr3 = builder2.getKeys(8);
                } else {
                    keyArr3 = null;
                }
            }
            double d4 = calculateActiveModemPowerMah;
            long calculateDuration = calculateDuration(batteryStatsUid, 0);
            if (!builder2.isVirtualUid()) {
                powerAndDuration3.totalAppDurationMs += calculateDuration;
            }
            builder2.setUsageDurationMillis(8, calculateDuration);
            if (powerModel == 2) {
                long mobileRadioEnergyConsumptionUC2 = batteryStatsUid.getMobileRadioEnergyConsumptionUC();
                if (mobileRadioEnergyConsumptionUC2 == -1) {
                    keyArr = keyArr3;
                } else {
                    double uCtoMah = com.android.server.power.stats.PowerCalculator.uCtoMah(mobileRadioEnergyConsumptionUC2);
                    if (!builder2.isVirtualUid()) {
                        powerAndDuration3.totalAppPowerMah += uCtoMah;
                    }
                    builder2.setConsumedPower(8, uCtoMah, powerModel);
                    if (!batteryUsageStatsQuery.isProcessStateDataNeeded() || keyArr3 == null) {
                        keyArr = keyArr3;
                    } else {
                        int length = keyArr3.length;
                        int i2 = 0;
                        while (i2 < length) {
                            android.os.BatteryConsumer.Key key = keyArr3[i2];
                            int i3 = key.processState;
                            if (i3 == 0) {
                                uid2 = batteryStatsUid;
                                keyArr2 = keyArr3;
                            } else {
                                uid2 = batteryStatsUid;
                                keyArr2 = keyArr3;
                                builder2.setConsumedPower(key, com.android.server.power.stats.PowerCalculator.uCtoMah(batteryStatsUid.getMobileRadioEnergyConsumptionUC(i3)), powerModel);
                            }
                            i2++;
                            batteryStatsUid = uid2;
                            keyArr3 = keyArr2;
                        }
                        keyArr = keyArr3;
                    }
                }
            } else {
                keyArr = keyArr3;
                arrayList2.add(builder2);
                longArrayQueue.addLast(calculateDuration);
            }
            size--;
            uidBatteryConsumerBuilders = sparseArray;
            mobileRadioEnergyConsumptionUC = j5;
            calculateActiveModemPowerMah = d4;
            keyArr3 = keyArr;
        }
        long j6 = mobileRadioEnergyConsumptionUC;
        double d5 = calculateActiveModemPowerMah;
        long mobileRadioActiveTime = batteryStats.getMobileRadioActiveTime(j, 0) / 1000;
        if (mobileRadioActiveTime < powerAndDuration3.totalAppDurationMs) {
            mobileRadioActiveTime = powerAndDuration3.totalAppDurationMs;
        }
        if (powerModel == 2) {
            powerAndDuration = powerAndDuration3;
            j3 = mobileRadioActiveTime;
        } else {
            int size2 = arrayList2.size();
            int i4 = 0;
            while (i4 < size2) {
                android.os.UidBatteryConsumer.Builder builder3 = (android.os.UidBatteryConsumer.Builder) arrayList2.get(i4);
                long j7 = longArrayQueue.get(i4);
                int i5 = size2;
                double d6 = mobileRadioActiveTime;
                if (d6 == 0.0d) {
                    d2 = 0.0d;
                } else {
                    d2 = (j7 * d5) / d6;
                }
                if (!builder3.isVirtualUid()) {
                    powerAndDuration3.totalAppPowerMah += d2;
                }
                builder3.setConsumedPower(8, d2, powerModel);
                if (!batteryUsageStatsQuery.isProcessStateDataNeeded() || keyArr3 == null) {
                    arrayList = arrayList2;
                } else {
                    android.os.BatteryStats.Uid batteryStatsUid2 = builder3.getBatteryStatsUid();
                    int length2 = keyArr3.length;
                    arrayList = arrayList2;
                    int i6 = 0;
                    while (i6 < length2) {
                        int i7 = length2;
                        android.os.BatteryConsumer.Key key2 = keyArr3[i6];
                        android.util.LongArrayQueue longArrayQueue2 = longArrayQueue;
                        int i8 = key2.processState;
                        if (i8 == 0) {
                            uid = batteryStatsUid2;
                            powerAndDuration2 = powerAndDuration3;
                            j4 = mobileRadioActiveTime;
                        } else {
                            j4 = mobileRadioActiveTime;
                            long mobileRadioActiveTimeInProcessState = batteryStatsUid2.getMobileRadioActiveTimeInProcessState(i8) / 1000;
                            uid = batteryStatsUid2;
                            powerAndDuration2 = powerAndDuration3;
                            double d7 = j7;
                            if (d7 == 0.0d) {
                                d3 = 0.0d;
                            } else {
                                d3 = (mobileRadioActiveTimeInProcessState * d2) / d7;
                            }
                            builder3.setConsumedPower(key2, d3, powerModel);
                        }
                        i6++;
                        powerAndDuration3 = powerAndDuration2;
                        length2 = i7;
                        longArrayQueue = longArrayQueue2;
                        batteryStatsUid2 = uid;
                        mobileRadioActiveTime = j4;
                    }
                }
                i4++;
                size2 = i5;
                powerAndDuration3 = powerAndDuration3;
                arrayList2 = arrayList;
                longArrayQueue = longArrayQueue;
                mobileRadioActiveTime = mobileRadioActiveTime;
            }
            powerAndDuration = powerAndDuration3;
            j3 = mobileRadioActiveTime;
        }
        com.android.server.power.stats.MobileRadioPowerCalculator.PowerAndDuration powerAndDuration4 = powerAndDuration;
        powerAndDuration4.remainingDurationMs = j3 - powerAndDuration4.totalAppDurationMs;
        if (powerModel == 2) {
            powerAndDuration4.remainingPowerMah = com.android.server.power.stats.PowerCalculator.uCtoMah(j6) - powerAndDuration4.totalAppPowerMah;
            if (powerAndDuration4.remainingPowerMah < 0.0d) {
                powerAndDuration4.remainingPowerMah = 0.0d;
            }
        } else {
            if (j3 != 0) {
                powerAndDuration4.remainingPowerMah += (d5 * powerAndDuration4.remainingDurationMs) / j3;
            }
            android.os.BatteryStats.ControllerActivityCounter modemControllerActivity = batteryStats.getModemControllerActivity();
            if (modemControllerActivity == null) {
                i = 0;
                d = Double.NaN;
            } else {
                i = 0;
                d = calcInactiveStatePowerMah(modemControllerActivity.getSleepTimeCounter().getCountLocked(0), modemControllerActivity.getIdleTimeCounter().getCountLocked(0));
            }
            if (java.lang.Double.isNaN(d)) {
                double calcScanTimePowerMah = calcScanTimePowerMah(batteryStats.getPhoneSignalScanningTime(j, i) / 1000);
                int i9 = i;
                while (i9 < NUM_SIGNAL_STRENGTH_LEVELS) {
                    calcScanTimePowerMah += calcIdlePowerAtSignalStrengthMah(batteryStats.getPhoneSignalStrengthTime(i9, j, i) / 1000, i9);
                    i9++;
                    i = 0;
                }
                d = calcScanTimePowerMah;
            }
            if (!java.lang.Double.isNaN(d)) {
                powerAndDuration4.remainingPowerMah += d;
            }
        }
        if (powerAndDuration4.remainingPowerMah != 0.0d || powerAndDuration4.totalAppPowerMah != 0.0d) {
            builder.getAggregateBatteryConsumerBuilder(0).setUsageDurationMillis(8, powerAndDuration4.remainingDurationMs + powerAndDuration4.totalAppDurationMs).setConsumedPower(8, powerAndDuration4.remainingPowerMah + powerAndDuration4.totalAppPowerMah, powerModel);
            builder.getAggregateBatteryConsumerBuilder(1).setUsageDurationMillis(8, powerAndDuration4.totalAppDurationMs).setConsumedPower(8, powerAndDuration4.totalAppPowerMah, powerModel);
        }
    }

    private long calculateDuration(android.os.BatteryStats.Uid uid, int i) {
        return uid.getMobileRadioActiveTime(i) / 1000;
    }

    private double calculateActiveModemPowerMah(android.os.BatteryStats batteryStats, long j) {
        long j2 = j / 1000;
        int i = NUM_SIGNAL_STRENGTH_LEVELS;
        int i2 = 0;
        boolean z = false;
        int i3 = 0;
        double d = 0.0d;
        while (i3 < 3) {
            int i4 = i3 == 2 ? 5 : 1;
            int i5 = i2;
            while (i5 < i4) {
                boolean z2 = z;
                double d2 = d;
                int i6 = i2;
                while (i6 < i) {
                    int i7 = i6;
                    int i8 = i4;
                    int i9 = i2;
                    int i10 = i3;
                    long activeTxRadioDurationMs = batteryStats.getActiveTxRadioDurationMs(i3, i5, i7, j2);
                    if (activeTxRadioDurationMs != -1) {
                        double calcTxStatePowerMah = calcTxStatePowerMah(i10, i5, i7, activeTxRadioDurationMs);
                        if (!java.lang.Double.isNaN(calcTxStatePowerMah)) {
                            d2 += calcTxStatePowerMah;
                            z2 = true;
                        }
                    }
                    i6 = i7 + 1;
                    i2 = i9;
                    i3 = i10;
                    i4 = i8;
                }
                int i11 = i4;
                int i12 = i2;
                int i13 = i3;
                long activeRxRadioDurationMs = batteryStats.getActiveRxRadioDurationMs(i13, i5, j2);
                if (activeRxRadioDurationMs != -1) {
                    double calcRxStatePowerMah = calcRxStatePowerMah(i13, i5, activeRxRadioDurationMs);
                    if (!java.lang.Double.isNaN(calcRxStatePowerMah)) {
                        d2 += calcRxStatePowerMah;
                        z = true;
                        d = d2;
                        i5++;
                        i2 = i12;
                        i3 = i13;
                        i4 = i11;
                    }
                }
                z = z2;
                d = d2;
                i5++;
                i2 = i12;
                i3 = i13;
                i4 = i11;
            }
            i3++;
        }
        int i14 = i2;
        if (z) {
            return d;
        }
        long mobileRadioActiveTime = batteryStats.getMobileRadioActiveTime(j, i14) / 1000;
        if (mobileRadioActiveTime > 0) {
            return calcPowerFromRadioActiveDurationMah(mobileRadioActiveTime);
        }
        return 0.0d;
    }

    private static long buildModemPowerProfileKey(int i, int i2, int i3, int i4) {
        long j = i != -1 ? 4294967296L | i : 4294967296L;
        switch (i2) {
            case -1:
                break;
            case 0:
                j |= 0;
                break;
            case 1:
                j |= 1048576;
                break;
            case 2:
                j |= 2097152;
                break;
            default:
                android.util.Log.w(TAG, "Unexpected RadioAccessTechnology : " + i2);
                break;
        }
        switch (i3) {
            case -1:
                break;
            case 0:
                j |= 0;
                break;
            case 1:
                j |= 65536;
                break;
            case 2:
                j |= 131072;
                break;
            case 3:
                j |= 196608;
                break;
            case 4:
                j |= 262144;
                break;
            default:
                android.util.Log.w(TAG, "Unexpected NR frequency range : " + i3);
                break;
        }
        switch (i4) {
            case -1:
                return j;
            case 0:
                return j | 0;
            case 1:
                return j | 16777216;
            case 2:
                return j | 33554432;
            case 3:
                return j | 50331648;
            case 4:
                return j | 67108864;
            default:
                android.util.Log.w(TAG, "Unexpected transmission level : " + i4);
                return j;
        }
    }

    public double calcRxStatePowerMah(int i, int i2, long j) {
        long buildModemPowerProfileKey = buildModemPowerProfileKey(536870912, i, i2, -1);
        double averageBatteryDrainOrDefaultMa = this.mPowerProfile.getAverageBatteryDrainOrDefaultMa(buildModemPowerProfileKey, Double.NaN);
        if (java.lang.Double.isNaN(averageBatteryDrainOrDefaultMa)) {
            android.util.Log.w(TAG, "Unavailable Power Profile constant for key 0x" + java.lang.Long.toHexString(buildModemPowerProfileKey));
            return Double.NaN;
        }
        return (averageBatteryDrainOrDefaultMa * j) / MILLIS_IN_HOUR;
    }

    public double calcTxStatePowerMah(int i, int i2, int i3, long j) {
        long buildModemPowerProfileKey = buildModemPowerProfileKey(805306368, i, i2, i3);
        double averageBatteryDrainOrDefaultMa = this.mPowerProfile.getAverageBatteryDrainOrDefaultMa(buildModemPowerProfileKey, Double.NaN);
        if (java.lang.Double.isNaN(averageBatteryDrainOrDefaultMa)) {
            android.util.Log.w(TAG, "Unavailable Power Profile constant for key 0x" + java.lang.Long.toHexString(buildModemPowerProfileKey));
            return Double.NaN;
        }
        return (averageBatteryDrainOrDefaultMa * j) / MILLIS_IN_HOUR;
    }

    public double calcInactiveStatePowerMah(long j, long j2) {
        if (this.mSleepPowerEstimator == null || this.mIdlePowerEstimator == null) {
            return Double.NaN;
        }
        return this.mSleepPowerEstimator.calculatePower(j) + this.mIdlePowerEstimator.calculatePower(j2);
    }

    public double calcPowerFromRadioActiveDurationMah(long j) {
        return this.mActivePowerEstimator.calculatePower(j);
    }

    public double calcIdlePowerAtSignalStrengthMah(long j, int i) {
        return this.mIdlePowerEstimators[i].calculatePower(j);
    }

    public double calcScanTimePowerMah(long j) {
        return this.mScanPowerEstimator.calculatePower(j);
    }
}
