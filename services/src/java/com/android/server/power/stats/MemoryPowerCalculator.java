package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class MemoryPowerCalculator extends com.android.server.power.stats.PowerCalculator {
    public static final java.lang.String TAG = "MemoryPowerCalculator";
    private final com.android.server.power.stats.UsageBasedPowerEstimator[] mPowerEstimators;

    public MemoryPowerCalculator(com.android.internal.os.PowerProfile powerProfile) {
        int numElements = powerProfile.getNumElements("memory.bandwidths");
        this.mPowerEstimators = new com.android.server.power.stats.UsageBasedPowerEstimator[numElements];
        for (int i = 0; i < numElements; i++) {
            this.mPowerEstimators[i] = new com.android.server.power.stats.UsageBasedPowerEstimator(powerProfile.getAveragePower("memory.bandwidths", i));
        }
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public boolean isPowerComponentSupported(int i) {
        return i == 13;
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public void calculate(android.os.BatteryUsageStats.Builder builder, android.os.BatteryStats batteryStats, long j, long j2, android.os.BatteryUsageStatsQuery batteryUsageStatsQuery) {
        long calculateDuration = calculateDuration(batteryStats, j, 0);
        builder.getAggregateBatteryConsumerBuilder(0).setUsageDurationMillis(13, calculateDuration).setConsumedPower(13, calculatePower(batteryStats, j, 0));
    }

    private long calculateDuration(android.os.BatteryStats batteryStats, long j, int i) {
        android.util.LongSparseArray kernelMemoryStats = batteryStats.getKernelMemoryStats();
        long j2 = 0;
        for (int i2 = 0; i2 < kernelMemoryStats.size() && i2 < this.mPowerEstimators.length; i2++) {
            j2 += this.mPowerEstimators[i2].calculateDuration((android.os.BatteryStats.Timer) kernelMemoryStats.valueAt(i2), j, i);
        }
        return j2;
    }

    private double calculatePower(android.os.BatteryStats batteryStats, long j, int i) {
        android.util.LongSparseArray kernelMemoryStats = batteryStats.getKernelMemoryStats();
        double d = 0.0d;
        for (int i2 = 0; i2 < kernelMemoryStats.size() && i2 < this.mPowerEstimators.length; i2++) {
            com.android.server.power.stats.UsageBasedPowerEstimator usageBasedPowerEstimator = this.mPowerEstimators[(int) kernelMemoryStats.keyAt(i2)];
            d += usageBasedPowerEstimator.calculatePower(usageBasedPowerEstimator.calculateDuration((android.os.BatteryStats.Timer) kernelMemoryStats.valueAt(i2), j, i));
        }
        return d;
    }
}
