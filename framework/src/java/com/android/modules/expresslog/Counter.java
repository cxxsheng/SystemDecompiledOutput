package com.android.modules.expresslog;

/* loaded from: classes5.dex */
public final class Counter {
    private Counter() {
    }

    public static void logIncrement(java.lang.String str) {
        logIncrement(str, 1L);
    }

    public static void logIncrementWithUid(java.lang.String str, int i) {
        logIncrementWithUid(str, i, 1L);
    }

    public static void logIncrement(java.lang.String str, long j) {
        com.android.modules.expresslog.StatsExpressLog.write(528, com.android.modules.expresslog.MetricIds.getMetricIdHash(str, 1), j);
    }

    public static void logIncrementWithUid(java.lang.String str, int i, long j) {
        com.android.modules.expresslog.StatsExpressLog.write(644, com.android.modules.expresslog.MetricIds.getMetricIdHash(str, 3), j, i);
    }
}
