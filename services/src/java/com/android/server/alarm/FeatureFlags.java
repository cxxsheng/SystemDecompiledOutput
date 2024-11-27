package com.android.server.alarm;

/* loaded from: classes.dex */
public interface FeatureFlags {
    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    boolean startUserBeforeScheduledAlarms();

    @android.compat.annotation.UnsupportedAppUsage
    @com.android.aconfig.annotations.AssumeFalseForR8
    boolean useFrozenStateToDropListenerAlarms();
}
