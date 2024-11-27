package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class UsageBasedPowerEstimator {
    private static final double MILLIS_IN_HOUR = 3600000.0d;
    private final double mAveragePowerMahPerMs;

    public UsageBasedPowerEstimator(double d) {
        this.mAveragePowerMahPerMs = d / MILLIS_IN_HOUR;
    }

    public boolean isSupported() {
        return this.mAveragePowerMahPerMs != 0.0d;
    }

    public long calculateDuration(android.os.BatteryStats.Timer timer, long j, int i) {
        if (timer == null) {
            return 0L;
        }
        return timer.getTotalTimeLocked(j, i) / 1000;
    }

    public double calculatePower(long j) {
        return this.mAveragePowerMahPerMs * j;
    }
}
