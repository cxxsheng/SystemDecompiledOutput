package com.android.server.utils;

@android.ravenwood.annotation.RavenwoodKeepWholeClass
/* loaded from: classes2.dex */
public final class TimingsTraceAndSlog extends android.util.TimingsTraceLog {
    private static final long BOTTLENECK_DURATION_MS = -1;
    private static final java.lang.String SYSTEM_SERVER_TIMING_ASYNC_TAG = "SystemServerTimingAsync";
    public static final java.lang.String SYSTEM_SERVER_TIMING_TAG = "SystemServerTiming";
    private final java.lang.String mTag;

    @android.annotation.NonNull
    public static com.android.server.utils.TimingsTraceAndSlog newAsyncLog() {
        return new com.android.server.utils.TimingsTraceAndSlog(SYSTEM_SERVER_TIMING_ASYNC_TAG, 524288L);
    }

    public TimingsTraceAndSlog() {
        this(SYSTEM_SERVER_TIMING_TAG);
    }

    public TimingsTraceAndSlog(@android.annotation.NonNull java.lang.String str) {
        this(str, 524288L);
    }

    public TimingsTraceAndSlog(@android.annotation.NonNull java.lang.String str, long j) {
        super(str, j);
        this.mTag = str;
    }

    public TimingsTraceAndSlog(@android.annotation.NonNull com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog) {
        super(timingsTraceAndSlog);
        this.mTag = timingsTraceAndSlog.mTag;
    }

    public void traceBegin(@android.annotation.NonNull java.lang.String str) {
        android.util.Slog.d(this.mTag, str);
        super.traceBegin(str);
    }

    public void logDuration(java.lang.String str, long j) {
        super.logDuration(str, j);
    }

    public java.lang.String toString() {
        return "TimingsTraceAndSlog[" + this.mTag + "]";
    }
}
