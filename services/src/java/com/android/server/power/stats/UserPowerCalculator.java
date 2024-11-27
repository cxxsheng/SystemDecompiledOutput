package com.android.server.power.stats;

/* loaded from: classes2.dex */
public class UserPowerCalculator extends com.android.server.power.stats.PowerCalculator {
    @Override // com.android.server.power.stats.PowerCalculator
    public boolean isPowerComponentSupported(int i) {
        return true;
    }

    @Override // com.android.server.power.stats.PowerCalculator
    public void calculate(android.os.BatteryUsageStats.Builder builder, android.os.BatteryStats batteryStats, long j, long j2, android.os.BatteryUsageStatsQuery batteryUsageStatsQuery) {
        int[] userIds = batteryUsageStatsQuery.getUserIds();
        if (com.android.internal.util.ArrayUtils.contains(userIds, -1)) {
            return;
        }
        android.util.SparseArray uidBatteryConsumerBuilders = builder.getUidBatteryConsumerBuilders();
        for (int size = uidBatteryConsumerBuilders.size() - 1; size >= 0; size--) {
            android.os.UidBatteryConsumer.Builder builder2 = (android.os.UidBatteryConsumer.Builder) uidBatteryConsumerBuilders.valueAt(size);
            if (!builder2.isVirtualUid()) {
                int uid = builder2.getUid();
                if (android.os.UserHandle.getAppId(uid) >= 10000) {
                    int userId = android.os.UserHandle.getUserId(uid);
                    if (!com.android.internal.util.ArrayUtils.contains(userIds, userId)) {
                        builder2.excludeFromBatteryUsageStats();
                        builder.getOrCreateUserBatteryConsumerBuilder(userId).addUidBatteryConsumer(builder2);
                    }
                }
            }
        }
    }
}
