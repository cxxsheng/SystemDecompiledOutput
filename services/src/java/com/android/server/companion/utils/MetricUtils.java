package com.android.server.companion.utils;

/* loaded from: classes.dex */
public final class MetricUtils {
    private static final java.util.Map<java.lang.String, java.lang.Integer> METRIC_DEVICE_PROFILE;

    static {
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        arrayMap.put(null, 0);
        arrayMap.put("android.app.role.COMPANION_DEVICE_WATCH", 1);
        arrayMap.put("android.app.role.COMPANION_DEVICE_APP_STREAMING", 2);
        arrayMap.put("android.app.role.SYSTEM_AUTOMOTIVE_PROJECTION", 3);
        arrayMap.put("android.app.role.COMPANION_DEVICE_COMPUTER", 4);
        arrayMap.put("android.app.role.COMPANION_DEVICE_GLASSES", 5);
        arrayMap.put("android.app.role.COMPANION_DEVICE_NEARBY_DEVICE_STREAMING", 6);
        METRIC_DEVICE_PROFILE = java.util.Collections.unmodifiableMap(arrayMap);
    }

    public static void logCreateAssociation(java.lang.String str) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.CDM_ASSOCIATION_ACTION, 1, METRIC_DEVICE_PROFILE.get(str).intValue());
    }

    public static void logRemoveAssociation(java.lang.String str) {
        com.android.internal.util.FrameworkStatsLog.write(com.android.internal.util.FrameworkStatsLog.CDM_ASSOCIATION_ACTION, 2, METRIC_DEVICE_PROFILE.get(str).intValue());
    }
}
