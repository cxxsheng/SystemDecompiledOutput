package android.filterfw.core;

/* loaded from: classes.dex */
public abstract class Scheduler {
    private android.filterfw.core.FilterGraph mGraph;

    abstract void reset();

    abstract android.filterfw.core.Filter scheduleNextNode();

    Scheduler(android.filterfw.core.FilterGraph filterGraph) {
        this.mGraph = filterGraph;
    }

    android.filterfw.core.FilterGraph getGraph() {
        return this.mGraph;
    }

    boolean finished() {
        return true;
    }
}
