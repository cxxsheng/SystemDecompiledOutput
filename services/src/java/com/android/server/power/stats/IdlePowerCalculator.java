package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class IdlePowerCalculator extends com.android.server.power.stats.PowerCalculator {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "IdlePowerCalculator";
    private final double mAveragePowerCpuIdleMahPerUs;
    private final double mAveragePowerCpuSuspendMahPerUs;
    public long mDurationMs;
    public double mPowerMah;

    public IdlePowerCalculator(com.android.internal.os.PowerProfile powerProfile) {
        this.mAveragePowerCpuSuspendMahPerUs = powerProfile.getAveragePower("cpu.suspend") / 3.6E9d;
        this.mAveragePowerCpuIdleMahPerUs = powerProfile.getAveragePower("cpu.idle") / 3.6E9d;
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public boolean isPowerComponentSupported(int i) {
        return i == 16;
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public void calculate(android.os.BatteryUsageStats.Builder builder, android.os.BatteryStats batteryStats, long j, long j2, android.os.BatteryUsageStatsQuery batteryUsageStatsQuery) {
        calculatePowerAndDuration(batteryStats, j, j2, 0);
        if (this.mPowerMah != 0.0d) {
            builder.getAggregateBatteryConsumerBuilder(0).setConsumedPower(16, this.mPowerMah).setUsageDurationMillis(16, this.mDurationMs);
        }
    }

    private void calculatePowerAndDuration(android.os.BatteryStats batteryStats, long j, long j2, int i) {
        long computeBatteryRealtime = batteryStats.computeBatteryRealtime(j, i);
        this.mPowerMah = (computeBatteryRealtime * this.mAveragePowerCpuSuspendMahPerUs) + (batteryStats.computeBatteryUptime(j2, i) * this.mAveragePowerCpuIdleMahPerUs);
        this.mDurationMs = computeBatteryRealtime / 1000;
    }
}
