package android.filterfw.core;

/* loaded from: classes.dex */
public class SimpleScheduler extends android.filterfw.core.Scheduler {
    public SimpleScheduler(android.filterfw.core.FilterGraph filterGraph) {
        super(filterGraph);
    }

    @Override // android.filterfw.core.Scheduler
    public void reset() {
    }

    @Override // android.filterfw.core.Scheduler
    public android.filterfw.core.Filter scheduleNextNode() {
        for (android.filterfw.core.Filter filter : getGraph().getFilters()) {
            if (filter.canProcess()) {
                return filter;
            }
        }
        return null;
    }
}
