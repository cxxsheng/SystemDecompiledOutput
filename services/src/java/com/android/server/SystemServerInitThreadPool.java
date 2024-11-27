package com.android.server;

/* loaded from: classes.dex */
public final class SystemServerInitThreadPool implements android.util.Dumpable {
    private static final int SHUTDOWN_TIMEOUT_MILLIS = 20000;

    @com.android.internal.annotations.GuardedBy({"LOCK"})
    private static com.android.server.SystemServerInitThreadPool sInstance;
    private final java.util.concurrent.ExecutorService mService;

    @com.android.internal.annotations.GuardedBy({"mPendingTasks"})
    private boolean mShutDown;
    private static final java.lang.String TAG = com.android.server.SystemServerInitThreadPool.class.getSimpleName();
    private static final boolean IS_DEBUGGABLE = android.os.Build.IS_DEBUGGABLE;
    private static final java.lang.Object LOCK = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mPendingTasks"})
    private final java.util.List<java.lang.String> mPendingTasks = new java.util.ArrayList();
    private final int mSize = java.lang.Runtime.getRuntime().availableProcessors();

    private SystemServerInitThreadPool() {
        android.util.Slog.i(TAG, "Creating instance with " + this.mSize + " threads");
        this.mService = com.android.internal.util.ConcurrentUtils.newFixedThreadPool(this.mSize, "system-server-init-thread", -2);
    }

    @android.annotation.NonNull
    public static java.util.concurrent.Future<?> submit(@android.annotation.NonNull java.lang.Runnable runnable, @android.annotation.NonNull java.lang.String str) {
        com.android.server.SystemServerInitThreadPool systemServerInitThreadPool;
        java.util.Objects.requireNonNull(str, "description cannot be null");
        synchronized (LOCK) {
            com.android.internal.util.Preconditions.checkState(sInstance != null, "Cannot get " + TAG + " - it has been shut down");
            systemServerInitThreadPool = sInstance;
        }
        return systemServerInitThreadPool.submitTask(runnable, str);
    }

    @android.annotation.NonNull
    private java.util.concurrent.Future<?> submitTask(@android.annotation.NonNull final java.lang.Runnable runnable, @android.annotation.NonNull final java.lang.String str) {
        synchronized (this.mPendingTasks) {
            com.android.internal.util.Preconditions.checkState(!this.mShutDown, TAG + " already shut down");
            this.mPendingTasks.add(str);
        }
        return this.mService.submit(new java.lang.Runnable() { // from class: com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.SystemServerInitThreadPool.this.lambda$submitTask$0(str, runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$submitTask$0(java.lang.String str, java.lang.Runnable runnable) {
        com.android.server.utils.TimingsTraceAndSlog newAsyncLog = com.android.server.utils.TimingsTraceAndSlog.newAsyncLog();
        newAsyncLog.traceBegin("InitThreadPoolExec:" + str);
        if (IS_DEBUGGABLE) {
            android.util.Slog.d(TAG, "Started executing " + str);
        }
        try {
            runnable.run();
            synchronized (this.mPendingTasks) {
                this.mPendingTasks.remove(str);
            }
            if (IS_DEBUGGABLE) {
                android.util.Slog.d(TAG, "Finished executing " + str);
            }
            newAsyncLog.traceEnd();
        } catch (java.lang.RuntimeException e) {
            android.util.Slog.e(TAG, "Failure in " + str + ": " + e, e);
            newAsyncLog.traceEnd();
            throw e;
        }
    }

    static com.android.server.SystemServerInitThreadPool start() {
        com.android.server.SystemServerInitThreadPool systemServerInitThreadPool;
        synchronized (LOCK) {
            com.android.internal.util.Preconditions.checkState(sInstance == null, TAG + " already started");
            systemServerInitThreadPool = new com.android.server.SystemServerInitThreadPool();
            sInstance = systemServerInitThreadPool;
        }
        return systemServerInitThreadPool;
    }

    static void shutdown() {
        android.util.Slog.d(TAG, "Shutdown requested");
        synchronized (LOCK) {
            try {
                com.android.server.utils.TimingsTraceAndSlog timingsTraceAndSlog = new com.android.server.utils.TimingsTraceAndSlog();
                timingsTraceAndSlog.traceBegin("WaitInitThreadPoolShutdown");
                if (sInstance == null) {
                    timingsTraceAndSlog.traceEnd();
                    android.util.Slog.wtf(TAG, "Already shutdown", new java.lang.Exception());
                    return;
                }
                synchronized (sInstance.mPendingTasks) {
                    sInstance.mShutDown = true;
                }
                sInstance.mService.shutdown();
                try {
                    boolean awaitTermination = sInstance.mService.awaitTermination(20000L, java.util.concurrent.TimeUnit.MILLISECONDS);
                    if (!awaitTermination) {
                        dumpStackTraces();
                    }
                    java.util.List<java.lang.Runnable> shutdownNow = sInstance.mService.shutdownNow();
                    if (!awaitTermination) {
                        java.util.ArrayList arrayList = new java.util.ArrayList();
                        synchronized (sInstance.mPendingTasks) {
                            arrayList.addAll(sInstance.mPendingTasks);
                        }
                        timingsTraceAndSlog.traceEnd();
                        throw new java.lang.IllegalStateException("Cannot shutdown. Unstarted tasks " + shutdownNow + " Unfinished tasks " + arrayList);
                    }
                    sInstance = null;
                    android.util.Slog.d(TAG, "Shutdown successful");
                    timingsTraceAndSlog.traceEnd();
                } catch (java.lang.InterruptedException e) {
                    java.lang.Thread.currentThread().interrupt();
                    dumpStackTraces();
                    timingsTraceAndSlog.traceEnd();
                    throw new java.lang.IllegalStateException(TAG + " init interrupted");
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private static void dumpStackTraces() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        arrayList.add(java.lang.Integer.valueOf(android.os.Process.myPid()));
        com.android.server.am.StackTracesDumpHelper.dumpStackTraces(arrayList, null, null, java.util.concurrent.CompletableFuture.completedFuture(com.android.server.Watchdog.getInterestingNativePids()), null, null, null, new com.android.server.SystemServerInitThreadPool$$ExternalSyntheticLambda0(), null);
    }

    @Override // android.util.Dumpable
    public java.lang.String getDumpableName() {
        return com.android.server.SystemServerInitThreadPool.class.getSimpleName();
    }

    @Override // android.util.Dumpable
    public void dump(java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        synchronized (LOCK) {
            printWriter.printf("has instance: %b\n", java.lang.Boolean.valueOf(sInstance != null));
        }
        printWriter.printf("number of threads: %d\n", java.lang.Integer.valueOf(this.mSize));
        printWriter.printf("service: %s\n", this.mService);
        synchronized (this.mPendingTasks) {
            try {
                printWriter.printf("is shutdown: %b\n", java.lang.Boolean.valueOf(this.mShutDown));
                int size = this.mPendingTasks.size();
                if (size == 0) {
                    printWriter.println("no pending tasks");
                } else {
                    printWriter.printf("%d pending tasks: %s\n", java.lang.Integer.valueOf(size), this.mPendingTasks);
                }
            } finally {
            }
        }
    }
}
