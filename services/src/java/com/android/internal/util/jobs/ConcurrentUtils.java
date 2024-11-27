package com.android.internal.util.jobs;

/* loaded from: classes.dex */
public class ConcurrentUtils {
    public static final java.util.concurrent.Executor DIRECT_EXECUTOR = new com.android.internal.util.jobs.ConcurrentUtils.DirectExecutor();

    private ConcurrentUtils() {
    }

    public static java.util.concurrent.ExecutorService newFixedThreadPool(int i, final java.lang.String str, final int i2) {
        return java.util.concurrent.Executors.newFixedThreadPool(i, new java.util.concurrent.ThreadFactory() { // from class: com.android.internal.util.jobs.ConcurrentUtils.1
            private final java.util.concurrent.atomic.AtomicInteger threadNum = new java.util.concurrent.atomic.AtomicInteger(0);

            @Override // java.util.concurrent.ThreadFactory
            public java.lang.Thread newThread(final java.lang.Runnable runnable) {
                return new java.lang.Thread(str + this.threadNum.incrementAndGet()) { // from class: com.android.internal.util.jobs.ConcurrentUtils.1.1
                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() {
                        android.os.Process.setThreadPriority(i2);
                        runnable.run();
                    }
                };
            }
        });
    }

    public static <T> T waitForFutureNoInterrupt(java.util.concurrent.Future<T> future, java.lang.String str) {
        try {
            return future.get();
        } catch (java.lang.InterruptedException e) {
            java.lang.Thread.currentThread().interrupt();
            throw new java.lang.IllegalStateException(str + " interrupted");
        } catch (java.util.concurrent.ExecutionException e2) {
            throw new java.lang.RuntimeException(str + " failed", e2);
        }
    }

    public static void waitForCountDownNoInterrupt(java.util.concurrent.CountDownLatch countDownLatch, long j, java.lang.String str) {
        try {
            if (!countDownLatch.await(j, java.util.concurrent.TimeUnit.MILLISECONDS)) {
                throw new java.lang.IllegalStateException(str + " timed out.");
            }
        } catch (java.lang.InterruptedException e) {
            java.lang.Thread.currentThread().interrupt();
            throw new java.lang.IllegalStateException(str + " interrupted.");
        }
    }

    public static void wtfIfLockHeld(java.lang.String str, java.lang.Object obj) {
        if (java.lang.Thread.holdsLock(obj)) {
            android.util.Slog.wtf(str, "Lock mustn't be held");
        }
    }

    public static void wtfIfLockNotHeld(java.lang.String str, java.lang.Object obj) {
        if (!java.lang.Thread.holdsLock(obj)) {
            android.util.Slog.wtf(str, "Lock must be held");
        }
    }

    private static class DirectExecutor implements java.util.concurrent.Executor {
        private DirectExecutor() {
        }

        @Override // java.util.concurrent.Executor
        public void execute(java.lang.Runnable runnable) {
            runnable.run();
        }

        public java.lang.String toString() {
            return "DIRECT_EXECUTOR";
        }
    }
}
