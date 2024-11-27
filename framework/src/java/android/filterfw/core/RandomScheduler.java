package android.filterfw.core;

/* loaded from: classes.dex */
public class RandomScheduler extends android.filterfw.core.Scheduler {
    private java.util.Random mRand;

    public RandomScheduler(android.filterfw.core.FilterGraph filterGraph) {
        super(filterGraph);
        this.mRand = new java.util.Random();
    }

    @Override // android.filterfw.core.Scheduler
    public void reset() {
    }

    @Override // android.filterfw.core.Scheduler
    public android.filterfw.core.Filter scheduleNextNode() {
        java.util.Vector vector = new java.util.Vector();
        for (android.filterfw.core.Filter filter : getGraph().getFilters()) {
            if (filter.canProcess()) {
                vector.add(filter);
            }
        }
        if (vector.size() > 0) {
            return (android.filterfw.core.Filter) vector.elementAt(this.mRand.nextInt(vector.size()));
        }
        return null;
    }
}
