package android.filterfw.core;

/* loaded from: classes.dex */
public class RoundRobinScheduler extends android.filterfw.core.Scheduler {
    private int mLastPos;

    public RoundRobinScheduler(android.filterfw.core.FilterGraph filterGraph) {
        super(filterGraph);
        this.mLastPos = -1;
    }

    @Override // android.filterfw.core.Scheduler
    public void reset() {
        this.mLastPos = -1;
    }

    @Override // android.filterfw.core.Scheduler
    public android.filterfw.core.Filter scheduleNextNode() {
        java.util.Set<android.filterfw.core.Filter> filters = getGraph().getFilters();
        int i = -1;
        if (this.mLastPos >= filters.size()) {
            this.mLastPos = -1;
        }
        int i2 = 0;
        android.filterfw.core.Filter filter = null;
        for (android.filterfw.core.Filter filter2 : filters) {
            if (filter2.canProcess()) {
                if (i2 <= this.mLastPos) {
                    if (filter == null) {
                        i = i2;
                        filter = filter2;
                    }
                } else {
                    this.mLastPos = i2;
                    return filter2;
                }
            }
            i2++;
        }
        if (filter == null) {
            return null;
        }
        this.mLastPos = i;
        return filter;
    }
}
