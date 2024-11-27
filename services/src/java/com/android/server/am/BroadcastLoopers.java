package com.android.server.am;

/* loaded from: classes.dex */
public class BroadcastLoopers {
    private static final java.lang.String TAG = "BroadcastLoopers";

    @com.android.internal.annotations.GuardedBy({"sLoopers"})
    private static final android.util.ArraySet<android.os.Looper> sLoopers = new android.util.ArraySet<>();

    public static void addLooper(@android.annotation.NonNull android.os.Looper looper) {
        synchronized (sLoopers) {
            android.util.ArraySet<android.os.Looper> arraySet = sLoopers;
            java.util.Objects.requireNonNull(looper);
            android.os.Looper looper2 = looper;
            arraySet.add(looper);
        }
    }

    public static void addMyLooper() {
        android.os.Looper myLooper = android.os.Looper.myLooper();
        if (myLooper != null) {
            synchronized (sLoopers) {
                try {
                    if (sLoopers.add(myLooper)) {
                        android.util.Slog.w(TAG, "Found previously unknown looper " + myLooper.getThread());
                    }
                } finally {
                }
            }
        }
    }

    public static void waitForIdle(@android.annotation.NonNull java.io.PrintWriter printWriter) {
        waitForCondition(printWriter, new java.util.function.BiConsumer() { // from class: com.android.server.am.BroadcastLoopers$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.am.BroadcastLoopers.lambda$waitForIdle$1((android.os.Looper) obj, (java.util.concurrent.CountDownLatch) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$waitForIdle$1(android.os.Looper looper, final java.util.concurrent.CountDownLatch countDownLatch) {
        looper.getQueue().addIdleHandler(new android.os.MessageQueue.IdleHandler() { // from class: com.android.server.am.BroadcastLoopers$$ExternalSyntheticLambda0
            @Override // android.os.MessageQueue.IdleHandler
            public final boolean queueIdle() {
                boolean lambda$waitForIdle$0;
                lambda$waitForIdle$0 = com.android.server.am.BroadcastLoopers.lambda$waitForIdle$0(countDownLatch);
                return lambda$waitForIdle$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$waitForIdle$0(java.util.concurrent.CountDownLatch countDownLatch) {
        countDownLatch.countDown();
        return false;
    }

    public static void waitForBarrier(@android.annotation.NonNull java.io.PrintWriter printWriter) {
        waitForCondition(printWriter, new java.util.function.BiConsumer() { // from class: com.android.server.am.BroadcastLoopers$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                com.android.server.am.BroadcastLoopers.lambda$waitForBarrier$3((android.os.Looper) obj, (java.util.concurrent.CountDownLatch) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$waitForBarrier$3(android.os.Looper looper, final java.util.concurrent.CountDownLatch countDownLatch) {
        new android.os.Handler(looper).post(new java.lang.Runnable() { // from class: com.android.server.am.BroadcastLoopers$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                countDownLatch.countDown();
            }
        });
    }

    private static void waitForCondition(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.util.function.BiConsumer<android.os.Looper, java.util.concurrent.CountDownLatch> biConsumer) {
        java.util.concurrent.CountDownLatch countDownLatch;
        synchronized (sLoopers) {
            try {
                int size = sLoopers.size();
                countDownLatch = new java.util.concurrent.CountDownLatch(size);
                for (int i = 0; i < size; i++) {
                    android.os.Looper valueAt = sLoopers.valueAt(i);
                    if (valueAt.getQueue().isIdle()) {
                        countDownLatch.countDown();
                    } else {
                        biConsumer.accept(valueAt, countDownLatch);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        long j = 0;
        while (countDownLatch.getCount() > 0) {
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            if (uptimeMillis >= 1000 + j) {
                printWriter.println("Waiting for " + countDownLatch.getCount() + " loopers to drain...");
                printWriter.flush();
                j = uptimeMillis;
            }
            android.os.SystemClock.sleep(100L);
        }
        printWriter.println("Loopers drained!");
        printWriter.flush();
    }
}
