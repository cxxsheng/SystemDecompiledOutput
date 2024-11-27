package com.android.server;

/* loaded from: classes.dex */
public final class SystemClockTime {
    private static final java.lang.String TAG = "SystemClockTime";
    public static final int TIME_CONFIDENCE_HIGH = 100;
    public static final int TIME_CONFIDENCE_LOW = 0;

    @android.annotation.NonNull
    private static final android.util.LocalLog sTimeDebugLog = new android.util.LocalLog(30, false);
    private static int sTimeConfidence = 0;
    private static final long sNativeData = init();

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_USE})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TimeConfidence {
    }

    private static native long init();

    private static native int setTime(long j, long j2);

    private SystemClockTime() {
    }

    public static void initializeIfRequired() {
        long max = java.lang.Long.max(android.os.SystemProperties.getLong("ro.build.date.utc", -1L) * 1000, java.lang.Long.max(android.os.Environment.getRootDirectory().lastModified(), android.os.Build.TIME));
        long currentTimeMillis = getCurrentTimeMillis();
        if (currentTimeMillis < max) {
            java.lang.String str = "Current time only " + currentTimeMillis + ", advancing to build time " + max;
            android.util.Slog.i(TAG, str);
            setTimeAndConfidence(max, 0, str);
        }
    }

    public static void setTimeAndConfidence(long j, int i, @android.annotation.NonNull java.lang.String str) {
        synchronized (com.android.server.SystemClockTime.class) {
            setTime(sNativeData, j);
            sTimeConfidence = i;
            sTimeDebugLog.log(str);
        }
    }

    public static void setConfidence(int i, @android.annotation.NonNull java.lang.String str) {
        synchronized (com.android.server.SystemClockTime.class) {
            sTimeConfidence = i;
            sTimeDebugLog.log(str);
        }
    }

    private static long getCurrentTimeMillis() {
        return java.lang.System.currentTimeMillis();
    }

    public static int getTimeConfidence() {
        int i;
        synchronized (com.android.server.SystemClockTime.class) {
            i = sTimeConfidence;
        }
        return i;
    }

    public static void addDebugLogEntry(@android.annotation.NonNull java.lang.String str) {
        sTimeDebugLog.log(str);
    }

    public static void dump(java.io.PrintWriter printWriter) {
        sTimeDebugLog.dump(printWriter);
    }
}
