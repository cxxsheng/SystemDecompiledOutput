package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class WakelockPowerCalculator extends com.android.server.power.stats.PowerCalculator {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "WakelockPowerCalculator";
    private final com.android.server.power.stats.UsageBasedPowerEstimator mPowerEstimator;

    private static class PowerAndDuration {
        public long durationMs;
        public double powerMah;

        private PowerAndDuration() {
        }
    }

    public WakelockPowerCalculator(com.android.internal.os.PowerProfile powerProfile) {
        this.mPowerEstimator = new com.android.server.power.stats.UsageBasedPowerEstimator(powerProfile.getAveragePower("cpu.idle"));
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public boolean isPowerComponentSupported(int i) {
        return i == 12;
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public void calculate(android.os.BatteryUsageStats.Builder builder, android.os.BatteryStats batteryStats, long j, long j2, android.os.BatteryUsageStatsQuery batteryUsageStatsQuery) {
        int i;
        com.android.server.power.stats.WakelockPowerCalculator.PowerAndDuration powerAndDuration = new com.android.server.power.stats.WakelockPowerCalculator.PowerAndDuration();
        android.util.SparseArray uidBatteryConsumerBuilders = builder.getUidBatteryConsumerBuilders();
        android.os.UidBatteryConsumer.Builder builder2 = null;
        double d = 0.0d;
        double d2 = 0.0d;
        long j3 = 0;
        long j4 = 0;
        for (int size = uidBatteryConsumerBuilders.size() - 1; size >= 0; size--) {
            android.os.UidBatteryConsumer.Builder builder3 = (android.os.UidBatteryConsumer.Builder) uidBatteryConsumerBuilders.valueAt(size);
            double d3 = d;
            long j5 = j3;
            calculateApp(powerAndDuration, builder3.getBatteryStatsUid(), j, 0);
            builder3.setUsageDurationMillis(12, powerAndDuration.durationMs).setConsumedPower(12, powerAndDuration.powerMah);
            if (builder3.isVirtualUid()) {
                j3 = j5;
            } else {
                j3 = j5 + powerAndDuration.durationMs;
                d2 += powerAndDuration.powerMah;
            }
            if (builder3.getUid() != 0) {
                d = d3;
            } else {
                long j6 = powerAndDuration.durationMs;
                d = powerAndDuration.powerMah;
                j4 = j6;
                builder2 = builder3;
            }
        }
        double d4 = d;
        long j7 = j3;
        double d5 = d2;
        android.os.UidBatteryConsumer.Builder builder4 = builder2;
        calculateRemaining(powerAndDuration, batteryStats, j, j2, 0, d4, j4, j7);
        double d6 = powerAndDuration.powerMah;
        if (builder4 == null) {
            i = 12;
        } else {
            i = 12;
            builder4.setUsageDurationMillis(12, powerAndDuration.durationMs).setConsumedPower(12, d6);
        }
        long calculateWakeTimeMillis = calculateWakeTimeMillis(batteryStats, j, j2);
        if (calculateWakeTimeMillis < 0) {
            calculateWakeTimeMillis = 0;
        }
        int i2 = i;
        builder.getAggregateBatteryConsumerBuilder(0).setUsageDurationMillis(i2, calculateWakeTimeMillis).setConsumedPower(i2, d6 + d5);
        builder.getAggregateBatteryConsumerBuilder(1).setUsageDurationMillis(i2, j7).setConsumedPower(i2, d5);
    }

    private void calculateApp(com.android.server.power.stats.WakelockPowerCalculator.PowerAndDuration powerAndDuration, android.os.BatteryStats.Uid uid, long j, int i) {
        android.util.ArrayMap wakelockStats = uid.getWakelockStats();
        int size = wakelockStats.size();
        long j2 = 0;
        for (int i2 = 0; i2 < size; i2++) {
            android.os.BatteryStats.Timer wakeTime = ((android.os.BatteryStats.Uid.Wakelock) wakelockStats.valueAt(i2)).getWakeTime(0);
            if (wakeTime != null) {
                j2 += wakeTime.getTotalTimeLocked(j, i);
            }
        }
        powerAndDuration.durationMs = j2 / 1000;
        powerAndDuration.powerMah = this.mPowerEstimator.calculatePower(powerAndDuration.durationMs);
    }

    private void calculateRemaining(com.android.server.power.stats.WakelockPowerCalculator.PowerAndDuration powerAndDuration, android.os.BatteryStats batteryStats, long j, long j2, int i, double d, long j3, long j4) {
        long calculateWakeTimeMillis = calculateWakeTimeMillis(batteryStats, j, j2) - j4;
        if (calculateWakeTimeMillis > 0) {
            double calculatePower = this.mPowerEstimator.calculatePower(calculateWakeTimeMillis);
            powerAndDuration.durationMs = j3 + calculateWakeTimeMillis;
            powerAndDuration.powerMah = d + calculatePower;
        } else {
            powerAndDuration.durationMs = 0L;
            powerAndDuration.powerMah = 0.0d;
        }
    }

    private long calculateWakeTimeMillis(android.os.BatteryStats batteryStats, long j, long j2) {
        return (batteryStats.getBatteryUptime(j2) - batteryStats.getScreenOnTime(j, 0)) / 1000;
    }
}
