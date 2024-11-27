package android.filterfw.core;

/* compiled from: StopWatchMap.java */
/* loaded from: classes.dex */
class StopWatch {
    private java.lang.String mName;
    private int STOP_WATCH_LOGGING_PERIOD = 200;
    private java.lang.String TAG = "MFF";
    private long mStartTime = -1;
    private long mTotalTime = 0;
    private int mNumCalls = 0;

    public StopWatch(java.lang.String str) {
        this.mName = str;
    }

    public void start() {
        if (this.mStartTime != -1) {
            throw new java.lang.RuntimeException("Calling start with StopWatch already running");
        }
        this.mStartTime = android.os.SystemClock.elapsedRealtime();
    }

    public void stop() {
        if (this.mStartTime == -1) {
            throw new java.lang.RuntimeException("Calling stop with StopWatch already stopped");
        }
        this.mTotalTime += android.os.SystemClock.elapsedRealtime() - this.mStartTime;
        this.mNumCalls++;
        this.mStartTime = -1L;
        if (this.mNumCalls % this.STOP_WATCH_LOGGING_PERIOD == 0) {
            android.util.Log.i(this.TAG, "AVG ms/call " + this.mName + ": " + java.lang.String.format("%.1f", java.lang.Float.valueOf((this.mTotalTime * 1.0f) / this.mNumCalls)));
            this.mTotalTime = 0L;
            this.mNumCalls = 0;
        }
    }
}
