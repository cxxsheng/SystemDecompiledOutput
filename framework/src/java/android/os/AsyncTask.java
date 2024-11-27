package android.os;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public abstract class AsyncTask<Params, Progress, Result> {
    private static final int BACKUP_POOL_SIZE = 5;
    private static final int CORE_POOL_SIZE = 1;
    private static final int KEEP_ALIVE_SECONDS = 3;
    private static final java.lang.String LOG_TAG = "AsyncTask";
    private static final int MAXIMUM_POOL_SIZE = 20;
    private static final int MESSAGE_POST_PROGRESS = 2;
    private static final int MESSAGE_POST_RESULT = 1;

    @java.lang.Deprecated
    public static final java.util.concurrent.Executor SERIAL_EXECUTOR;

    @java.lang.Deprecated
    public static final java.util.concurrent.Executor THREAD_POOL_EXECUTOR;
    private static java.util.concurrent.ThreadPoolExecutor sBackupExecutor;
    private static java.util.concurrent.LinkedBlockingQueue<java.lang.Runnable> sBackupExecutorQueue;
    private static volatile java.util.concurrent.Executor sDefaultExecutor;
    private static android.os.AsyncTask.InternalHandler sHandler;
    private final java.util.concurrent.atomic.AtomicBoolean mCancelled;
    private final java.util.concurrent.FutureTask<Result> mFuture;
    private final android.os.Handler mHandler;
    private volatile android.os.AsyncTask.Status mStatus;
    private final java.util.concurrent.atomic.AtomicBoolean mTaskInvoked;
    private final android.os.AsyncTask.WorkerRunnable<Params, Result> mWorker;
    private static final java.util.concurrent.ThreadFactory sThreadFactory = new java.util.concurrent.ThreadFactory() { // from class: android.os.AsyncTask.1
        private final java.util.concurrent.atomic.AtomicInteger mCount = new java.util.concurrent.atomic.AtomicInteger(1);

        @Override // java.util.concurrent.ThreadFactory
        public java.lang.Thread newThread(java.lang.Runnable runnable) {
            return new java.lang.Thread(runnable, "AsyncTask #" + this.mCount.getAndIncrement());
        }
    };
    private static final java.util.concurrent.RejectedExecutionHandler sRunOnSerialPolicy = new java.util.concurrent.RejectedExecutionHandler() { // from class: android.os.AsyncTask.2
        @Override // java.util.concurrent.RejectedExecutionHandler
        public void rejectedExecution(java.lang.Runnable runnable, java.util.concurrent.ThreadPoolExecutor threadPoolExecutor) {
            android.util.Log.w(android.os.AsyncTask.LOG_TAG, "Exceeded ThreadPoolExecutor pool size");
            synchronized (this) {
                if (android.os.AsyncTask.sBackupExecutor == null) {
                    android.os.AsyncTask.sBackupExecutorQueue = new java.util.concurrent.LinkedBlockingQueue();
                    android.os.AsyncTask.sBackupExecutor = new java.util.concurrent.ThreadPoolExecutor(5, 5, 3L, java.util.concurrent.TimeUnit.SECONDS, android.os.AsyncTask.sBackupExecutorQueue, android.os.AsyncTask.sThreadFactory);
                    android.os.AsyncTask.sBackupExecutor.allowCoreThreadTimeOut(true);
                }
            }
            android.os.AsyncTask.sBackupExecutor.execute(runnable);
        }
    };

    public enum Status {
        PENDING,
        RUNNING,
        FINISHED
    }

    protected abstract Result doInBackground(Params... paramsArr);

    static {
        java.util.concurrent.ThreadPoolExecutor threadPoolExecutor = new java.util.concurrent.ThreadPoolExecutor(1, 20, 3L, java.util.concurrent.TimeUnit.SECONDS, new java.util.concurrent.SynchronousQueue(), sThreadFactory);
        threadPoolExecutor.setRejectedExecutionHandler(sRunOnSerialPolicy);
        THREAD_POOL_EXECUTOR = threadPoolExecutor;
        SERIAL_EXECUTOR = new android.os.AsyncTask.SerialExecutor();
        sDefaultExecutor = SERIAL_EXECUTOR;
    }

    private static class SerialExecutor implements java.util.concurrent.Executor {
        java.lang.Runnable mActive;
        final java.util.ArrayDeque<java.lang.Runnable> mTasks;

        private SerialExecutor() {
            this.mTasks = new java.util.ArrayDeque<>();
        }

        @Override // java.util.concurrent.Executor
        public synchronized void execute(final java.lang.Runnable runnable) {
            this.mTasks.offer(new java.lang.Runnable() { // from class: android.os.AsyncTask.SerialExecutor.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        runnable.run();
                    } finally {
                        android.os.AsyncTask.SerialExecutor.this.scheduleNext();
                    }
                }
            });
            if (this.mActive == null) {
                scheduleNext();
            }
        }

        protected synchronized void scheduleNext() {
            java.lang.Runnable poll = this.mTasks.poll();
            this.mActive = poll;
            if (poll != null) {
                android.os.AsyncTask.THREAD_POOL_EXECUTOR.execute(this.mActive);
            }
        }
    }

    private static android.os.Handler getMainHandler() {
        android.os.AsyncTask.InternalHandler internalHandler;
        synchronized (android.os.AsyncTask.class) {
            if (sHandler == null) {
                sHandler = new android.os.AsyncTask.InternalHandler(android.os.Looper.getMainLooper());
            }
            internalHandler = sHandler;
        }
        return internalHandler;
    }

    private android.os.Handler getHandler() {
        return this.mHandler;
    }

    public static void setDefaultExecutor(java.util.concurrent.Executor executor) {
        sDefaultExecutor = executor;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public AsyncTask() {
        this((android.os.Looper) null);
    }

    public AsyncTask(android.os.Handler handler) {
        this(handler != null ? handler.getLooper() : null);
    }

    public AsyncTask(android.os.Looper looper) {
        android.os.Handler mainHandler;
        this.mStatus = android.os.AsyncTask.Status.PENDING;
        this.mCancelled = new java.util.concurrent.atomic.AtomicBoolean();
        this.mTaskInvoked = new java.util.concurrent.atomic.AtomicBoolean();
        if (looper == null || looper == android.os.Looper.getMainLooper()) {
            mainHandler = getMainHandler();
        } else {
            mainHandler = new android.os.Handler(looper);
        }
        this.mHandler = mainHandler;
        this.mWorker = new android.os.AsyncTask.WorkerRunnable<Params, Result>() { // from class: android.os.AsyncTask.3
            @Override // java.util.concurrent.Callable
            public Result call() throws java.lang.Exception {
                android.os.AsyncTask.this.mTaskInvoked.set(true);
                Result result = null;
                try {
                    android.os.Process.setThreadPriority(10);
                    result = (Result) android.os.AsyncTask.this.doInBackground(this.mParams);
                    android.os.Binder.flushPendingCommands();
                    return result;
                } finally {
                }
            }
        };
        this.mFuture = new java.util.concurrent.FutureTask<Result>(this.mWorker) { // from class: android.os.AsyncTask.4
            @Override // java.util.concurrent.FutureTask
            protected void done() {
                try {
                    android.os.AsyncTask.this.postResultIfNotInvoked(get());
                } catch (java.lang.InterruptedException e) {
                    android.util.Log.w(android.os.AsyncTask.LOG_TAG, e);
                } catch (java.util.concurrent.CancellationException e2) {
                    android.os.AsyncTask.this.postResultIfNotInvoked(null);
                } catch (java.util.concurrent.ExecutionException e3) {
                    throw new java.lang.RuntimeException("An error occurred while executing doInBackground()", e3.getCause());
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postResultIfNotInvoked(Result result) {
        if (!this.mTaskInvoked.get()) {
            postResult(result);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Result postResult(Result result) {
        getHandler().obtainMessage(1, new android.os.AsyncTask.AsyncTaskResult(this, result)).sendToTarget();
        return result;
    }

    public final android.os.AsyncTask.Status getStatus() {
        return this.mStatus;
    }

    protected void onPreExecute() {
    }

    protected void onPostExecute(Result result) {
    }

    protected void onProgressUpdate(Progress... progressArr) {
    }

    protected void onCancelled(Result result) {
        onCancelled();
    }

    protected void onCancelled() {
    }

    public final boolean isCancelled() {
        return this.mCancelled.get();
    }

    public final boolean cancel(boolean z) {
        this.mCancelled.set(true);
        return this.mFuture.cancel(z);
    }

    public final Result get() throws java.lang.InterruptedException, java.util.concurrent.ExecutionException {
        return this.mFuture.get();
    }

    public final Result get(long j, java.util.concurrent.TimeUnit timeUnit) throws java.lang.InterruptedException, java.util.concurrent.ExecutionException, java.util.concurrent.TimeoutException {
        return this.mFuture.get(j, timeUnit);
    }

    public final android.os.AsyncTask<Params, Progress, Result> execute(Params... paramsArr) {
        return executeOnExecutor(sDefaultExecutor, paramsArr);
    }

    public final android.os.AsyncTask<Params, Progress, Result> executeOnExecutor(java.util.concurrent.Executor executor, Params... paramsArr) {
        if (this.mStatus != android.os.AsyncTask.Status.PENDING) {
            switch (this.mStatus) {
                case RUNNING:
                    throw new java.lang.IllegalStateException("Cannot execute task: the task is already running.");
                case FINISHED:
                    throw new java.lang.IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
            }
        }
        this.mStatus = android.os.AsyncTask.Status.RUNNING;
        onPreExecute();
        this.mWorker.mParams = paramsArr;
        executor.execute(this.mFuture);
        return this;
    }

    public static void execute(java.lang.Runnable runnable) {
        sDefaultExecutor.execute(runnable);
    }

    protected final void publishProgress(Progress... progressArr) {
        if (!isCancelled()) {
            getHandler().obtainMessage(2, new android.os.AsyncTask.AsyncTaskResult(this, progressArr)).sendToTarget();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finish(Result result) {
        if (isCancelled()) {
            onCancelled(result);
        } else {
            onPostExecute(result);
        }
        this.mStatus = android.os.AsyncTask.Status.FINISHED;
    }

    private static class InternalHandler extends android.os.Handler {
        public InternalHandler(android.os.Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            android.os.AsyncTask.AsyncTaskResult asyncTaskResult = (android.os.AsyncTask.AsyncTaskResult) message.obj;
            switch (message.what) {
                case 1:
                    asyncTaskResult.mTask.finish(asyncTaskResult.mData[0]);
                    break;
                case 2:
                    asyncTaskResult.mTask.onProgressUpdate(asyncTaskResult.mData);
                    break;
            }
        }
    }

    private static abstract class WorkerRunnable<Params, Result> implements java.util.concurrent.Callable<Result> {
        Params[] mParams;

        private WorkerRunnable() {
        }
    }

    private static class AsyncTaskResult<Data> {
        final Data[] mData;
        final android.os.AsyncTask mTask;

        AsyncTaskResult(android.os.AsyncTask asyncTask, Data... dataArr) {
            this.mTask = asyncTask;
            this.mData = dataArr;
        }
    }
}
