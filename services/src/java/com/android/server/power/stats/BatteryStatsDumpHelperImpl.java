package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class BatteryStatsDumpHelperImpl implements android.os.BatteryStats.BatteryStatsDumpHelper {
    private final com.android.server.power.stats.BatteryUsageStatsProvider mBatteryUsageStatsProvider;

    public BatteryStatsDumpHelperImpl(com.android.server.power.stats.BatteryUsageStatsProvider batteryUsageStatsProvider) {
        this.mBatteryUsageStatsProvider = batteryUsageStatsProvider;
    }

    public android.os.BatteryUsageStats getBatteryUsageStats(android.os.BatteryStats batteryStats, boolean z) {
        android.os.BatteryUsageStatsQuery.Builder maxStatsAgeMs = new android.os.BatteryUsageStatsQuery.Builder().setMaxStatsAgeMs(0L);
        if (z) {
            maxStatsAgeMs.includePowerModels().includeProcessStateData().includeVirtualUids();
        }
        return this.mBatteryUsageStatsProvider.getBatteryUsageStats((com.android.server.power.stats.BatteryStatsImpl) batteryStats, maxStatsAgeMs.build());
    }
}
