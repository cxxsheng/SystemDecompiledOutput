package com.android.server.power.optimization;

/* loaded from: classes2.dex */
public final class Flags {
    private static com.android.server.power.optimization.FeatureFlags FEATURE_FLAGS = new com.android.server.power.optimization.FeatureFlagsImpl();
    public static final java.lang.String FLAG_DISABLE_SYSTEM_SERVICE_POWER_ATTR = "com.android.server.power.optimization.disable_system_service_power_attr";
    public static final java.lang.String FLAG_POWER_MONITOR_API = "com.android.server.power.optimization.power_monitor_api";
    public static final java.lang.String FLAG_STREAMLINED_BATTERY_STATS = "com.android.server.power.optimization.streamlined_battery_stats";
    public static final java.lang.String FLAG_STREAMLINED_CONNECTIVITY_BATTERY_STATS = "com.android.server.power.optimization.streamlined_connectivity_battery_stats";

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean disableSystemServicePowerAttr() {
        FEATURE_FLAGS.disableSystemServicePowerAttr();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean powerMonitorApi() {
        FEATURE_FLAGS.powerMonitorApi();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean streamlinedBatteryStats() {
        FEATURE_FLAGS.streamlinedBatteryStats();
        return false;
    }

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    public static boolean streamlinedConnectivityBatteryStats() {
        FEATURE_FLAGS.streamlinedConnectivityBatteryStats();
        return false;
    }
}
