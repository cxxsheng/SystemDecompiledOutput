package android.filterfw.core;

/* loaded from: classes.dex */
public class AsyncRunner extends android.filterfw.core.GraphRunner {
    private static final java.lang.String TAG = "AsyncRunner";
    private boolean isProcessing;
    private android.filterfw.core.GraphRunner.OnRunnerDoneListener mDoneListener;
    private java.lang.Exception mException;
    private boolean mLogVerbose;
    private android.filterfw.core.AsyncRunner.AsyncRunnerTask mRunTask;
    private android.filterfw.core.SyncRunner mRunner;
    private java.lang.Class mSchedulerClass;

    private class RunnerResult {
        public java.lang.Exception exception;
        public int status;

        private RunnerResult() {
            this.status = 0;
        }
    }

    private class AsyncRunnerTask extends android.os.AsyncTask<android.filterfw.core.SyncRunner, java.lang.Void, android.filterfw.core.AsyncRunner.RunnerResult> {
        private static final java.lang.String TAG = "AsyncRunnerTask";

        private AsyncRunnerTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public android.filterfw.core.AsyncRunner.RunnerResult doInBackground(android.filterfw.core.SyncRunner... syncRunnerArr) {
            android.filterfw.core.AsyncRunner.RunnerResult runnerResult = new android.filterfw.core.AsyncRunner.RunnerResult();
            try {
            } catch (java.lang.Exception e) {
                runnerResult.exception = e;
                runnerResult.status = 6;
            }
            if (syncRunnerArr.length > 1) {
                throw new java.lang.RuntimeException("More than one runner received!");
            }
            syncRunnerArr[0].assertReadyToStep();
            if (android.filterfw.core.AsyncRunner.this.mLogVerbose) {
                android.util.Log.v(TAG, "Starting background graph processing.");
            }
            android.filterfw.core.AsyncRunner.this.activateGlContext();
            if (android.filterfw.core.AsyncRunner.this.mLogVerbose) {
                android.util.Log.v(TAG, "Preparing filter graph for processing.");
            }
            syncRunnerArr[0].beginProcessing();
            if (android.filterfw.core.AsyncRunner.this.mLogVerbose) {
                android.util.Log.v(TAG, "Running graph.");
            }
            runnerResult.status = 1;
            while (!isCancelled() && runnerResult.status == 1) {
                if (!syncRunnerArr[0].performStep()) {
                    runnerResult.status = syncRunnerArr[0].determinePostRunState();
                    if (runnerResult.status == 3) {
                        syncRunnerArr[0].waitUntilWake();
                        runnerResult.status = 1;
                    }
                }
            }
            if (isCancelled()) {
                runnerResult.status = 5;
            }
            try {
                android.filterfw.core.AsyncRunner.this.deactivateGlContext();
            } catch (java.lang.Exception e2) {
                runnerResult.exception = e2;
                runnerResult.status = 6;
            }
            if (android.filterfw.core.AsyncRunner.this.mLogVerbose) {
                android.util.Log.v(TAG, "Done with background graph processing.");
            }
            return runnerResult;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onCancelled(android.filterfw.core.AsyncRunner.RunnerResult runnerResult) {
            onPostExecute(runnerResult);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(android.filterfw.core.AsyncRunner.RunnerResult runnerResult) {
            if (android.filterfw.core.AsyncRunner.this.mLogVerbose) {
                android.util.Log.v(TAG, "Starting post-execute.");
            }
            android.filterfw.core.AsyncRunner.this.setRunning(false);
            if (runnerResult == null) {
                runnerResult = new android.filterfw.core.AsyncRunner.RunnerResult();
                runnerResult.status = 5;
            }
            android.filterfw.core.AsyncRunner.this.setException(runnerResult.exception);
            if (runnerResult.status == 5 || runnerResult.status == 6) {
                if (android.filterfw.core.AsyncRunner.this.mLogVerbose) {
                    android.util.Log.v(TAG, "Closing filters.");
                }
                try {
                    android.filterfw.core.AsyncRunner.this.mRunner.close();
                } catch (java.lang.Exception e) {
                    runnerResult.status = 6;
                    android.filterfw.core.AsyncRunner.this.setException(e);
                }
            }
            if (android.filterfw.core.AsyncRunner.this.mDoneListener != null) {
                if (android.filterfw.core.AsyncRunner.this.mLogVerbose) {
                    android.util.Log.v(TAG, "Calling graph done callback.");
                }
                android.filterfw.core.AsyncRunner.this.mDoneListener.onRunnerDone(runnerResult.status);
            }
            if (android.filterfw.core.AsyncRunner.this.mLogVerbose) {
                android.util.Log.v(TAG, "Completed post-execute.");
            }
        }
    }

    public AsyncRunner(android.filterfw.core.FilterContext filterContext, java.lang.Class cls) {
        super(filterContext);
        this.mSchedulerClass = cls;
        this.mLogVerbose = android.util.Log.isLoggable(TAG, 2);
    }

    public AsyncRunner(android.filterfw.core.FilterContext filterContext) {
        super(filterContext);
        this.mSchedulerClass = android.filterfw.core.SimpleScheduler.class;
        this.mLogVerbose = android.util.Log.isLoggable(TAG, 2);
    }

    @Override // android.filterfw.core.GraphRunner
    public void setDoneCallback(android.filterfw.core.GraphRunner.OnRunnerDoneListener onRunnerDoneListener) {
        this.mDoneListener = onRunnerDoneListener;
    }

    public synchronized void setGraph(android.filterfw.core.FilterGraph filterGraph) {
        if (isRunning()) {
            throw new java.lang.RuntimeException("Graph is already running!");
        }
        this.mRunner = new android.filterfw.core.SyncRunner(this.mFilterContext, filterGraph, this.mSchedulerClass);
    }

    @Override // android.filterfw.core.GraphRunner
    public android.filterfw.core.FilterGraph getGraph() {
        if (this.mRunner != null) {
            return this.mRunner.getGraph();
        }
        return null;
    }

    @Override // android.filterfw.core.GraphRunner
    public synchronized void run() {
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Running graph.");
        }
        setException(null);
        if (isRunning()) {
            throw new java.lang.RuntimeException("Graph is already running!");
        }
        if (this.mRunner == null) {
            throw new java.lang.RuntimeException("Cannot run before a graph is set!");
        }
        this.mRunTask = new android.filterfw.core.AsyncRunner.AsyncRunnerTask();
        setRunning(true);
        this.mRunTask.execute(this.mRunner);
    }

    @Override // android.filterfw.core.GraphRunner
    public synchronized void stop() {
        if (this.mRunTask != null && !this.mRunTask.isCancelled()) {
            if (this.mLogVerbose) {
                android.util.Log.v(TAG, "Stopping graph.");
            }
            this.mRunTask.cancel(false);
        }
    }

    @Override // android.filterfw.core.GraphRunner
    public synchronized void close() {
        if (isRunning()) {
            throw new java.lang.RuntimeException("Cannot close graph while it is running!");
        }
        if (this.mLogVerbose) {
            android.util.Log.v(TAG, "Closing filters.");
        }
        this.mRunner.close();
    }

    @Override // android.filterfw.core.GraphRunner
    public synchronized boolean isRunning() {
        return this.isProcessing;
    }

    @Override // android.filterfw.core.GraphRunner
    public synchronized java.lang.Exception getError() {
        return this.mException;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void setRunning(boolean z) {
        this.isProcessing = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void setException(java.lang.Exception exc) {
        this.mException = exc;
    }
}
