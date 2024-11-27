package android.filterfw.core;

/* loaded from: classes.dex */
public class SyncRunner extends android.filterfw.core.GraphRunner {
    private static final java.lang.String TAG = "SyncRunner";
    private android.filterfw.core.GraphRunner.OnRunnerDoneListener mDoneListener;
    private final boolean mLogVerbose;
    private android.filterfw.core.Scheduler mScheduler;
    private android.filterfw.core.StopWatchMap mTimer;
    private android.os.ConditionVariable mWakeCondition;
    private java.util.concurrent.ScheduledThreadPoolExecutor mWakeExecutor;

    public SyncRunner(android.filterfw.core.FilterContext filterContext, android.filterfw.core.FilterGraph filterGraph, java.lang.Class cls) {
        super(filterContext);
        this.mScheduler = null;
        this.mDoneListener = null;
        this.mWakeExecutor = new java.util.concurrent.ScheduledThreadPoolExecutor(1);
        this.mWakeCondition = new android.os.ConditionVariable();
        this.mTimer = null;
        this.mLogVerbose = android.util.Log.isLoggable(TAG, 2);
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Initializing SyncRunner");
        }
        if (android.filterfw.core.Scheduler.class.isAssignableFrom(cls)) {
            try {
                this.mScheduler = (android.filterfw.core.Scheduler) cls.getConstructor(android.filterfw.core.FilterGraph.class).newInstance(filterGraph);
                this.mFilterContext = filterContext;
                this.mFilterContext.addGraph(filterGraph);
                this.mTimer = new android.filterfw.core.StopWatchMap();
                if (this.mLogVerbose) {
                    android.util.Log.v(TAG, "Setting up filters");
                }
                filterGraph.setupFilters();
                return;
            } catch (java.lang.IllegalAccessException e) {
                throw new java.lang.RuntimeException("Cannot access Scheduler constructor!", e);
            } catch (java.lang.InstantiationException e2) {
                throw new java.lang.RuntimeException("Could not instantiate the Scheduler instance!", e2);
            } catch (java.lang.NoSuchMethodException e3) {
                throw new java.lang.RuntimeException("Scheduler does not have constructor <init>(FilterGraph)!", e3);
            } catch (java.lang.reflect.InvocationTargetException e4) {
                throw new java.lang.RuntimeException("Scheduler constructor threw an exception", e4);
            } catch (java.lang.Exception e5) {
                throw new java.lang.RuntimeException("Could not instantiate Scheduler", e5);
            }
        }
        throw new java.lang.IllegalArgumentException("Class provided is not a Scheduler subclass!");
    }

    @Override // android.filterfw.core.GraphRunner
    public android.filterfw.core.FilterGraph getGraph() {
        if (this.mScheduler != null) {
            return this.mScheduler.getGraph();
        }
        return null;
    }

    public int step() {
        assertReadyToStep();
        if (!getGraph().isReady()) {
            throw new java.lang.RuntimeException("Trying to process graph that is not open!");
        }
        if (performStep()) {
            return 1;
        }
        return determinePostRunState();
    }

    public void beginProcessing() {
        this.mScheduler.reset();
        getGraph().beginProcessing();
    }

    @Override // android.filterfw.core.GraphRunner
    public void close() {
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Closing graph.");
        }
        getGraph().closeFilters(this.mFilterContext);
        this.mScheduler.reset();
    }

    @Override // android.filterfw.core.GraphRunner
    public void run() {
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Beginning run.");
        }
        assertReadyToStep();
        beginProcessing();
        boolean activateGlContext = activateGlContext();
        boolean z = true;
        while (z) {
            z = performStep();
        }
        if (activateGlContext) {
            deactivateGlContext();
        }
        if (this.mDoneListener != null) {
            if (this.mLogVerbose) {
                android.util.Log.v(TAG, "Calling completion listener.");
            }
            this.mDoneListener.onRunnerDone(determinePostRunState());
        }
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Run complete");
        }
    }

    @Override // android.filterfw.core.GraphRunner
    public boolean isRunning() {
        return false;
    }

    @Override // android.filterfw.core.GraphRunner
    public void setDoneCallback(android.filterfw.core.GraphRunner.OnRunnerDoneListener onRunnerDoneListener) {
        this.mDoneListener = onRunnerDoneListener;
    }

    @Override // android.filterfw.core.GraphRunner
    public void stop() {
        throw new java.lang.RuntimeException("SyncRunner does not support stopping a graph!");
    }

    @Override // android.filterfw.core.GraphRunner
    public synchronized java.lang.Exception getError() {
        return null;
    }

    protected void waitUntilWake() {
        this.mWakeCondition.block();
    }

    protected void processFilterNode(android.filterfw.core.Filter filter) {
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Processing filter node");
        }
        filter.performProcess(this.mFilterContext);
        if (filter.getStatus() == 6) {
            throw new java.lang.RuntimeException("There was an error executing " + filter + "!");
        }
        if (filter.getStatus() == 4) {
            if (this.mLogVerbose) {
                android.util.Log.v(TAG, "Scheduling filter wakeup");
            }
            scheduleFilterWake(filter, filter.getSleepDelay());
        }
    }

    protected void scheduleFilterWake(final android.filterfw.core.Filter filter, int i) {
        this.mWakeCondition.close();
        final android.os.ConditionVariable conditionVariable = this.mWakeCondition;
        this.mWakeExecutor.schedule(new java.lang.Runnable() { // from class: android.filterfw.core.SyncRunner.1
            @Override // java.lang.Runnable
            public void run() {
                filter.unsetStatus(4);
                conditionVariable.open();
            }
        }, i, java.util.concurrent.TimeUnit.MILLISECONDS);
    }

    protected int determinePostRunState() {
        for (android.filterfw.core.Filter filter : this.mScheduler.getGraph().getFilters()) {
            if (filter.isOpen()) {
                return filter.getStatus() == 4 ? 3 : 4;
            }
        }
        return 2;
    }

    boolean performStep() {
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Performing one step.");
        }
        android.filterfw.core.Filter scheduleNextNode = this.mScheduler.scheduleNextNode();
        if (scheduleNextNode != null) {
            this.mTimer.start(scheduleNextNode.getName());
            processFilterNode(scheduleNextNode);
            this.mTimer.stop(scheduleNextNode.getName());
            return true;
        }
        return false;
    }

    void assertReadyToStep() {
        if (this.mScheduler == null) {
            throw new java.lang.RuntimeException("Attempting to run schedule with no scheduler in place!");
        }
        if (getGraph() == null) {
            throw new java.lang.RuntimeException("Calling step on scheduler with no graph in place!");
        }
    }
}
