package android.filterfw.core;

/* loaded from: classes.dex */
public class OneShotScheduler extends android.filterfw.core.RoundRobinScheduler {
    private static final java.lang.String TAG = "OneShotScheduler";
    private final boolean mLogVerbose;
    private java.util.HashMap<java.lang.String, java.lang.Integer> scheduled;

    public OneShotScheduler(android.filterfw.core.FilterGraph filterGraph) {
        super(filterGraph);
        this.scheduled = new java.util.HashMap<>();
        this.mLogVerbose = android.util.Log.isLoggable(TAG, 2);
    }

    @Override // android.filterfw.core.RoundRobinScheduler, android.filterfw.core.Scheduler
    public void reset() {
        super.reset();
        this.scheduled.clear();
    }

    @Override // android.filterfw.core.RoundRobinScheduler, android.filterfw.core.Scheduler
    public android.filterfw.core.Filter scheduleNextNode() {
        android.filterfw.core.Filter filter = null;
        while (true) {
            android.filterfw.core.Filter scheduleNextNode = super.scheduleNextNode();
            if (scheduleNextNode == null) {
                if (this.mLogVerbose) {
                    android.util.Log.v(TAG, "No filters available to run.");
                }
                return null;
            }
            if (!this.scheduled.containsKey(scheduleNextNode.getName())) {
                if (scheduleNextNode.getNumberOfConnectedInputs() == 0) {
                    this.scheduled.put(scheduleNextNode.getName(), 1);
                }
                if (this.mLogVerbose) {
                    android.util.Log.v(TAG, "Scheduling filter \"" + scheduleNextNode.getName() + "\" of type " + scheduleNextNode.getFilterClassName());
                }
                return scheduleNextNode;
            }
            if (filter != scheduleNextNode) {
                if (filter == null) {
                    filter = scheduleNextNode;
                }
            } else {
                if (this.mLogVerbose) {
                    android.util.Log.v(TAG, "One pass through graph completed.");
                }
                return null;
            }
        }
    }
}
