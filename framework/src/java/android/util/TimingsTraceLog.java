package android.util;

/* loaded from: classes3.dex */
public class TimingsTraceLog {
    private static final boolean DEBUG_BOOT_TIME = !android.os.Build.IS_USER;
    private static final int MAX_NESTED_CALLS = 10;
    private int mCurrentLevel;
    private final int mMaxNestedCalls;
    private final java.lang.String[] mStartNames;
    private final long[] mStartTimes;
    private final java.lang.String mTag;
    private final long mThreadId;
    private final long mTraceTag;

    public TimingsTraceLog(java.lang.String str, long j) {
        this(str, j, DEBUG_BOOT_TIME ? 10 : -1);
    }

    public TimingsTraceLog(java.lang.String str, long j, int i) {
        this.mCurrentLevel = -1;
        this.mTag = str;
        this.mTraceTag = j;
        this.mThreadId = java.lang.Thread.currentThread().getId();
        this.mMaxNestedCalls = i;
        this.mStartNames = createAndGetStartNamesArray();
        this.mStartTimes = createAndGetStartTimesArray();
    }

    protected TimingsTraceLog(android.util.TimingsTraceLog timingsTraceLog) {
        this.mCurrentLevel = -1;
        this.mTag = timingsTraceLog.mTag;
        this.mTraceTag = timingsTraceLog.mTraceTag;
        this.mThreadId = java.lang.Thread.currentThread().getId();
        this.mMaxNestedCalls = timingsTraceLog.mMaxNestedCalls;
        this.mStartNames = createAndGetStartNamesArray();
        this.mStartTimes = createAndGetStartTimesArray();
        this.mCurrentLevel = timingsTraceLog.mCurrentLevel;
    }

    private java.lang.String[] createAndGetStartNamesArray() {
        if (this.mMaxNestedCalls > 0) {
            return new java.lang.String[this.mMaxNestedCalls];
        }
        return null;
    }

    private long[] createAndGetStartTimesArray() {
        if (this.mMaxNestedCalls > 0) {
            return new long[this.mMaxNestedCalls];
        }
        return null;
    }

    public void traceBegin(java.lang.String str) {
        assertSameThread();
        android.os.Trace.traceBegin(this.mTraceTag, str);
        if (DEBUG_BOOT_TIME) {
            if (this.mCurrentLevel + 1 >= this.mMaxNestedCalls) {
                android.util.Slog.w(this.mTag, "not tracing duration of '" + str + "' because already reached " + this.mMaxNestedCalls + " levels");
                return;
            }
            this.mCurrentLevel++;
            this.mStartNames[this.mCurrentLevel] = str;
            this.mStartTimes[this.mCurrentLevel] = android.os.SystemClock.elapsedRealtime();
        }
    }

    public void traceEnd() {
        assertSameThread();
        android.os.Trace.traceEnd(this.mTraceTag);
        if (DEBUG_BOOT_TIME) {
            if (this.mCurrentLevel < 0) {
                android.util.Slog.w(this.mTag, "traceEnd called more times than traceBegin");
                return;
            }
            this.mCurrentLevel--;
            logDuration(this.mStartNames[this.mCurrentLevel], android.os.SystemClock.elapsedRealtime() - this.mStartTimes[this.mCurrentLevel]);
        }
    }

    private void assertSameThread() {
        java.lang.Thread currentThread = java.lang.Thread.currentThread();
        if (currentThread.getId() != this.mThreadId) {
            throw new java.lang.IllegalStateException("Instance of TimingsTraceLog can only be called from the thread it was created on (tid: " + this.mThreadId + "), but was from " + currentThread.getName() + " (tid: " + currentThread.getId() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    public void logDuration(java.lang.String str, long j) {
        android.util.Slog.v(this.mTag, str + " took to complete: " + j + "ms");
    }

    public final java.util.List<java.lang.String> getUnfinishedTracesForDebug() {
        if (this.mStartTimes == null || this.mCurrentLevel < 0) {
            return java.util.Collections.emptyList();
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(this.mCurrentLevel + 1);
        for (int i = 0; i <= this.mCurrentLevel; i++) {
            arrayList.add(this.mStartNames[i]);
        }
        return arrayList;
    }
}
