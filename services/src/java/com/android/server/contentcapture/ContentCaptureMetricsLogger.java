package com.android.server.contentcapture;

/* loaded from: classes.dex */
public final class ContentCaptureMetricsLogger {
    private ContentCaptureMetricsLogger() {
    }

    public static void writeServiceEvent(int i, @android.annotation.NonNull java.lang.String str) {
        com.android.internal.util.FrameworkStatsLog.write(207, i, str, (java.lang.String) null, 0, 0);
    }

    public static void writeServiceEvent(int i, @android.annotation.NonNull android.content.ComponentName componentName) {
        writeServiceEvent(i, android.content.ComponentName.flattenToShortString(componentName));
    }

    public static void writeSetWhitelistEvent(@android.annotation.Nullable android.content.ComponentName componentName, @android.annotation.Nullable java.util.List<java.lang.String> list, @android.annotation.Nullable java.util.List<android.content.ComponentName> list2) {
        com.android.internal.util.FrameworkStatsLog.write(207, 3, android.content.ComponentName.flattenToShortString(componentName), (java.lang.String) null, list != null ? list.size() : 0, list2 != null ? list2.size() : 0);
    }

    public static void writeSessionEvent(int i, int i2, int i3, @android.annotation.NonNull android.content.ComponentName componentName, boolean z) {
        com.android.internal.util.FrameworkStatsLog.write(208, i, i2, i3, android.content.ComponentName.flattenToShortString(componentName), (java.lang.String) null, z);
    }

    public static void writeSessionFlush(int i, @android.annotation.NonNull android.content.ComponentName componentName, @android.annotation.NonNull android.service.contentcapture.FlushMetrics flushMetrics, @android.annotation.NonNull android.content.ContentCaptureOptions contentCaptureOptions, int i2) {
        com.android.internal.util.FrameworkStatsLog.write(209, i, android.content.ComponentName.flattenToShortString(componentName), (java.lang.String) null, flushMetrics.sessionStarted, flushMetrics.sessionFinished, flushMetrics.viewAppearedCount, flushMetrics.viewDisappearedCount, flushMetrics.viewTextChangedCount, contentCaptureOptions.maxBufferSize, contentCaptureOptions.idleFlushingFrequencyMs, contentCaptureOptions.textChangeFlushingFrequencyMs, i2);
    }
}
