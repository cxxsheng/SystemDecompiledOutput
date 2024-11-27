package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class ScreenPowerCalculator extends com.android.server.power.stats.PowerCalculator {
    private static final boolean DEBUG = false;
    public static final long MIN_ACTIVE_TIME_FOR_SMEARING = 600000;
    private static final java.lang.String TAG = "ScreenPowerCalculator";
    private final com.android.server.power.stats.UsageBasedPowerEstimator[] mScreenFullPowerEstimators;
    private final com.android.server.power.stats.UsageBasedPowerEstimator[] mScreenOnPowerEstimators;

    private static class PowerAndDuration {
        public long durationMs;
        public double powerMah;

        private PowerAndDuration() {
        }
    }

    public ScreenPowerCalculator(com.android.internal.os.PowerProfile powerProfile) {
        int numDisplays = powerProfile.getNumDisplays();
        this.mScreenOnPowerEstimators = new com.android.server.power.stats.UsageBasedPowerEstimator[numDisplays];
        this.mScreenFullPowerEstimators = new com.android.server.power.stats.UsageBasedPowerEstimator[numDisplays];
        for (int i = 0; i < numDisplays; i++) {
            this.mScreenOnPowerEstimators[i] = new com.android.server.power.stats.UsageBasedPowerEstimator(powerProfile.getAveragePowerForOrdinal("screen.on.display", i));
            this.mScreenFullPowerEstimators[i] = new com.android.server.power.stats.UsageBasedPowerEstimator(powerProfile.getAveragePowerForOrdinal("screen.full.display", i));
        }
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public boolean isPowerComponentSupported(int i) {
        return i == 0;
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public void calculate(android.os.BatteryUsageStats.Builder builder, android.os.BatteryStats batteryStats, long j, long j2, android.os.BatteryUsageStatsQuery batteryUsageStatsQuery) {
        double d;
        long j3;
        long j4 = j;
        com.android.server.power.stats.ScreenPowerCalculator.PowerAndDuration powerAndDuration = new com.android.server.power.stats.ScreenPowerCalculator.PowerAndDuration();
        long screenOnEnergyConsumptionUC = batteryStats.getScreenOnEnergyConsumptionUC();
        int powerModel = com.android.server.power.stats.PowerCalculator.getPowerModel(screenOnEnergyConsumptionUC, batteryUsageStatsQuery);
        calculateTotalDurationAndPower(powerAndDuration, powerModel, batteryStats, j, 0, screenOnEnergyConsumptionUC);
        android.util.SparseArray<android.os.UidBatteryConsumer.Builder> uidBatteryConsumerBuilders = builder.getUidBatteryConsumerBuilders();
        switch (powerModel) {
            case 2:
                com.android.server.power.stats.ScreenPowerCalculator.PowerAndDuration powerAndDuration2 = new com.android.server.power.stats.ScreenPowerCalculator.PowerAndDuration();
                int size = uidBatteryConsumerBuilders.size() - 1;
                double d2 = 0.0d;
                long j5 = 0;
                while (size >= 0) {
                    android.os.UidBatteryConsumer.Builder valueAt = uidBatteryConsumerBuilders.valueAt(size);
                    calculateAppUsingEnergyConsumption(powerAndDuration2, valueAt.getBatteryStatsUid(), j4);
                    android.util.SparseArray<android.os.UidBatteryConsumer.Builder> sparseArray = uidBatteryConsumerBuilders;
                    valueAt.setUsageDurationMillis(0, powerAndDuration2.durationMs).setConsumedPower(0, powerAndDuration2.powerMah, powerModel);
                    if (!valueAt.isVirtualUid()) {
                        d2 += powerAndDuration2.powerMah;
                        j5 += powerAndDuration2.durationMs;
                    }
                    size--;
                    j4 = j;
                    uidBatteryConsumerBuilders = sparseArray;
                }
                d = d2;
                j3 = j5;
                break;
            default:
                smearScreenBatteryDrain(uidBatteryConsumerBuilders, powerAndDuration, j4);
                d = powerAndDuration.powerMah;
                j3 = powerAndDuration.durationMs;
                break;
        }
        builder.getAggregateBatteryConsumerBuilder(0).setConsumedPower(0, java.lang.Math.max(powerAndDuration.powerMah, d), powerModel).setUsageDurationMillis(0, powerAndDuration.durationMs);
        builder.getAggregateBatteryConsumerBuilder(1).setConsumedPower(0, d, powerModel).setUsageDurationMillis(0, j3);
    }

    private void calculateTotalDurationAndPower(com.android.server.power.stats.ScreenPowerCalculator.PowerAndDuration powerAndDuration, int i, android.os.BatteryStats batteryStats, long j, int i2, long j2) {
        powerAndDuration.durationMs = calculateDuration(batteryStats, j, i2);
        switch (i) {
            case 2:
                powerAndDuration.powerMah = com.android.server.power.stats.PowerCalculator.uCtoMah(j2);
                break;
            default:
                powerAndDuration.powerMah = calculateTotalPowerFromBrightness(batteryStats, j);
                break;
        }
    }

    private void calculateAppUsingEnergyConsumption(com.android.server.power.stats.ScreenPowerCalculator.PowerAndDuration powerAndDuration, android.os.BatteryStats.Uid uid, long j) {
        powerAndDuration.durationMs = getProcessForegroundTimeMs(uid, j);
        long screenOnEnergyConsumptionUC = uid.getScreenOnEnergyConsumptionUC();
        if (screenOnEnergyConsumptionUC < 0) {
            android.util.Slog.wtf(TAG, "Screen energy not supported, so calculateApp shouldn't de called");
            powerAndDuration.powerMah = 0.0d;
        } else {
            powerAndDuration.powerMah = com.android.server.power.stats.PowerCalculator.uCtoMah(screenOnEnergyConsumptionUC);
        }
    }

    private long calculateDuration(android.os.BatteryStats batteryStats, long j, int i) {
        return batteryStats.getScreenOnTime(j, i) / 1000;
    }

    private double calculateTotalPowerFromBrightness(android.os.BatteryStats batteryStats, long j) {
        int length = this.mScreenOnPowerEstimators.length;
        double d = 0.0d;
        for (int i = 0; i < length; i++) {
            long j2 = 1000;
            d += this.mScreenOnPowerEstimators[i].calculatePower(batteryStats.getDisplayScreenOnTime(i, j) / 1000);
            int i2 = 0;
            while (i2 < 5) {
                d += (this.mScreenFullPowerEstimators[i].calculatePower(batteryStats.getDisplayScreenBrightnessTime(i, i2, j) / j2) * (i2 + 0.5f)) / 5.0d;
                i2++;
                j2 = 1000;
            }
        }
        return d;
    }

    private void smearScreenBatteryDrain(android.util.SparseArray<android.os.UidBatteryConsumer.Builder> sparseArray, com.android.server.power.stats.ScreenPowerCalculator.PowerAndDuration powerAndDuration, long j) {
        android.util.SparseLongArray sparseLongArray = new android.util.SparseLongArray();
        long j2 = 0;
        long j3 = 0;
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            android.os.UidBatteryConsumer.Builder valueAt = sparseArray.valueAt(size);
            android.os.BatteryStats.Uid batteryStatsUid = valueAt.getBatteryStatsUid();
            long processForegroundTimeMs = getProcessForegroundTimeMs(batteryStatsUid, j);
            sparseLongArray.put(batteryStatsUid.getUid(), processForegroundTimeMs);
            if (!valueAt.isVirtualUid()) {
                j3 += processForegroundTimeMs;
            }
        }
        if (j3 >= 600000) {
            double d = powerAndDuration.powerMah;
            int size2 = sparseArray.size() - 1;
            while (size2 >= 0) {
                android.os.UidBatteryConsumer.Builder valueAt2 = sparseArray.valueAt(size2);
                long j4 = sparseLongArray.get(valueAt2.getUid(), j2);
                valueAt2.setUsageDurationMillis(0, j4).setConsumedPower(0, (j4 * d) / j3, 1);
                size2--;
                j2 = 0;
            }
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public long getProcessForegroundTimeMs(android.os.BatteryStats.Uid uid, long j) {
        int[] iArr = {0};
        long j2 = 0;
        for (int i = 0; i < 1; i++) {
            j2 += uid.getProcessStateTime(iArr[i], j, 0);
        }
        return java.lang.Math.min(j2, getForegroundActivityTotalTimeUs(uid, j)) / 1000;
    }

    @com.android.internal.annotations.VisibleForTesting
    public long getForegroundActivityTotalTimeUs(android.os.BatteryStats.Uid uid, long j) {
        android.os.BatteryStats.Timer foregroundActivityTimer = uid.getForegroundActivityTimer();
        if (foregroundActivityTimer == null) {
            return 0L;
        }
        return foregroundActivityTimer.getTotalTimeLocked(j, 0);
    }
}
