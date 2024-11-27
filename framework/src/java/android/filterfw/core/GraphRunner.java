package android.filterfw.core;

/* loaded from: classes.dex */
public abstract class GraphRunner {
    public static final int RESULT_BLOCKED = 4;
    public static final int RESULT_ERROR = 6;
    public static final int RESULT_FINISHED = 2;
    public static final int RESULT_RUNNING = 1;
    public static final int RESULT_SLEEPING = 3;
    public static final int RESULT_STOPPED = 5;
    public static final int RESULT_UNKNOWN = 0;
    protected android.filterfw.core.FilterContext mFilterContext;

    public interface OnRunnerDoneListener {
        void onRunnerDone(int i);
    }

    public abstract void close();

    public abstract java.lang.Exception getError();

    public abstract android.filterfw.core.FilterGraph getGraph();

    public abstract boolean isRunning();

    public abstract void run();

    public abstract void setDoneCallback(android.filterfw.core.GraphRunner.OnRunnerDoneListener onRunnerDoneListener);

    public abstract void stop();

    public GraphRunner(android.filterfw.core.FilterContext filterContext) {
        this.mFilterContext = null;
        this.mFilterContext = filterContext;
    }

    public android.filterfw.core.FilterContext getContext() {
        return this.mFilterContext;
    }

    protected boolean activateGlContext() {
        android.filterfw.core.GLEnvironment gLEnvironment = this.mFilterContext.getGLEnvironment();
        if (gLEnvironment != null && !gLEnvironment.isActive()) {
            gLEnvironment.activate();
            return true;
        }
        return false;
    }

    protected void deactivateGlContext() {
        android.filterfw.core.GLEnvironment gLEnvironment = this.mFilterContext.getGLEnvironment();
        if (gLEnvironment != null) {
            gLEnvironment.deactivate();
        }
    }
}
