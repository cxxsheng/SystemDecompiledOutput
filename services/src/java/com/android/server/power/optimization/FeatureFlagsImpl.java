package com.android.server.power.optimization;

/* loaded from: classes2.dex */
public final class FeatureFlagsImpl implements com.android.server.power.optimization.FeatureFlags {
    @Override // com.android.server.power.optimization.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean disableSystemServicePowerAttr() {
        return false;
    }

    @Override // com.android.server.power.optimization.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean powerMonitorApi() {
        return false;
    }

    @Override // com.android.server.power.optimization.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean streamlinedBatteryStats() {
        return false;
    }

    @Override // com.android.server.power.optimization.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean streamlinedConnectivityBatteryStats() {
        return false;
    }
}
