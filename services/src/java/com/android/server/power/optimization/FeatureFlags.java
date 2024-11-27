package com.android.server.power.optimization;

/* loaded from: classes2.dex */
public interface FeatureFlags {
    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    boolean disableSystemServicePowerAttr();

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    boolean powerMonitorApi();

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    boolean streamlinedBatteryStats();

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    boolean streamlinedConnectivityBatteryStats();
}
