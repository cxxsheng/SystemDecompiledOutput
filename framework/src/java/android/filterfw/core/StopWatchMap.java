package android.filterfw.core;

/* loaded from: classes.dex */
public class StopWatchMap {
    public boolean LOG_MFF_RUNNING_TIMES = false;
    private java.util.HashMap<java.lang.String, android.filterfw.core.StopWatch> mStopWatches;

    public StopWatchMap() {
        this.mStopWatches = null;
        this.mStopWatches = new java.util.HashMap<>();
    }

    public void start(java.lang.String str) {
        if (!this.LOG_MFF_RUNNING_TIMES) {
            return;
        }
        if (!this.mStopWatches.containsKey(str)) {
            this.mStopWatches.put(str, new android.filterfw.core.StopWatch(str));
        }
        this.mStopWatches.get(str).start();
    }

    public void stop(java.lang.String str) {
        if (!this.LOG_MFF_RUNNING_TIMES) {
            return;
        }
        if (!this.mStopWatches.containsKey(str)) {
            throw new java.lang.RuntimeException("Calling stop with unknown stopWatchName: " + str);
        }
        this.mStopWatches.get(str).stop();
    }
}
