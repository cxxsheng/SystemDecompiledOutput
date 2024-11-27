package android.filterfw;

/* loaded from: classes.dex */
public class GraphEnvironment extends android.filterfw.MffEnvironment {
    public static final int MODE_ASYNCHRONOUS = 1;
    public static final int MODE_SYNCHRONOUS = 2;
    private android.filterfw.io.GraphReader mGraphReader;
    private java.util.ArrayList<android.filterfw.GraphEnvironment.GraphHandle> mGraphs;

    private class GraphHandle {
        private android.filterfw.core.AsyncRunner mAsyncRunner;
        private android.filterfw.core.FilterGraph mGraph;
        private android.filterfw.core.SyncRunner mSyncRunner;

        public GraphHandle(android.filterfw.core.FilterGraph filterGraph) {
            this.mGraph = filterGraph;
        }

        public android.filterfw.core.FilterGraph getGraph() {
            return this.mGraph;
        }

        public android.filterfw.core.AsyncRunner getAsyncRunner(android.filterfw.core.FilterContext filterContext) {
            if (this.mAsyncRunner == null) {
                this.mAsyncRunner = new android.filterfw.core.AsyncRunner(filterContext, android.filterfw.core.RoundRobinScheduler.class);
                this.mAsyncRunner.setGraph(this.mGraph);
            }
            return this.mAsyncRunner;
        }

        public android.filterfw.core.GraphRunner getSyncRunner(android.filterfw.core.FilterContext filterContext) {
            if (this.mSyncRunner == null) {
                this.mSyncRunner = new android.filterfw.core.SyncRunner(filterContext, this.mGraph, android.filterfw.core.RoundRobinScheduler.class);
            }
            return this.mSyncRunner;
        }
    }

    public GraphEnvironment() {
        super(null);
        this.mGraphs = new java.util.ArrayList<>();
    }

    public GraphEnvironment(android.filterfw.core.FrameManager frameManager, android.filterfw.io.GraphReader graphReader) {
        super(frameManager);
        this.mGraphs = new java.util.ArrayList<>();
        this.mGraphReader = graphReader;
    }

    public android.filterfw.io.GraphReader getGraphReader() {
        if (this.mGraphReader == null) {
            this.mGraphReader = new android.filterfw.io.TextGraphReader();
        }
        return this.mGraphReader;
    }

    public void addReferences(java.lang.Object... objArr) {
        getGraphReader().addReferencesByKeysAndValues(objArr);
    }

    public int loadGraph(android.content.Context context, int i) {
        try {
            return addGraph(getGraphReader().readGraphResource(context, i));
        } catch (android.filterfw.io.GraphIOException e) {
            throw new java.lang.RuntimeException("Could not read graph: " + e.getMessage());
        }
    }

    public int addGraph(android.filterfw.core.FilterGraph filterGraph) {
        this.mGraphs.add(new android.filterfw.GraphEnvironment.GraphHandle(filterGraph));
        return this.mGraphs.size() - 1;
    }

    public android.filterfw.core.FilterGraph getGraph(int i) {
        if (i < 0 || i >= this.mGraphs.size()) {
            throw new java.lang.IllegalArgumentException("Invalid graph ID " + i + " specified in runGraph()!");
        }
        return this.mGraphs.get(i).getGraph();
    }

    public android.filterfw.core.GraphRunner getRunner(int i, int i2) {
        switch (i2) {
            case 1:
                return this.mGraphs.get(i).getAsyncRunner(getContext());
            case 2:
                return this.mGraphs.get(i).getSyncRunner(getContext());
            default:
                throw new java.lang.RuntimeException("Invalid execution mode " + i2 + " specified in getRunner()!");
        }
    }
}
