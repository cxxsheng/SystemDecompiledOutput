package com.android.server.wm;

/* loaded from: classes3.dex */
public interface ActivityMetricsLaunchObserverRegistry {
    void registerLaunchObserver(@android.annotation.NonNull com.android.server.wm.ActivityMetricsLaunchObserver activityMetricsLaunchObserver);

    void unregisterLaunchObserver(@android.annotation.NonNull com.android.server.wm.ActivityMetricsLaunchObserver activityMetricsLaunchObserver);
}
