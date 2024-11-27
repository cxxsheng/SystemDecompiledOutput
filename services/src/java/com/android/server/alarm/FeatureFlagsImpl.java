package com.android.server.alarm;

/* loaded from: classes.dex */
public final class FeatureFlagsImpl implements com.android.server.alarm.FeatureFlags {
    @Override // com.android.server.alarm.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean startUserBeforeScheduledAlarms() {
        return false;
    }

    @Override // com.android.server.alarm.FeatureFlags
    @android.compat.annotation.UnsupportedAppUsage
    public boolean useFrozenStateToDropListenerAlarms() {
        return false;
    }
}
