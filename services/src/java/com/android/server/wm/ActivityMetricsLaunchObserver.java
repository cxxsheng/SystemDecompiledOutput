package com.android.server.wm;

/* loaded from: classes3.dex */
public class ActivityMetricsLaunchObserver {
    public static final int TEMPERATURE_COLD = 1;
    public static final int TEMPERATURE_HOT = 3;
    public static final int TEMPERATURE_WARM = 2;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface Temperature {
    }

    public void onIntentStarted(@android.annotation.NonNull android.content.Intent intent, long j) {
    }

    public void onIntentFailed(long j) {
    }

    public void onActivityLaunched(long j, android.content.ComponentName componentName, int i, int i2) {
    }

    public void onActivityLaunchCancelled(long j) {
    }

    public void onActivityLaunchFinished(long j, android.content.ComponentName componentName, long j2, int i) {
    }

    public void onReportFullyDrawn(long j, long j2) {
    }
}
