package com.android.internal.util;

/* loaded from: classes5.dex */
public final class GcUtils {
    private static final java.lang.String TAG = com.android.internal.util.GcUtils.class.getSimpleName();

    public static void runGcAndFinalizersSync() {
        java.lang.Runtime.getRuntime().gc();
        java.lang.Runtime.getRuntime().runFinalization();
        java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
        createFinalizationObserver(countDownLatch);
        do {
            try {
                java.lang.Runtime.getRuntime().gc();
                java.lang.Runtime.getRuntime().runFinalization();
            } catch (java.lang.InterruptedException e) {
                throw new java.lang.RuntimeException(e);
            }
        } while (!countDownLatch.await(100L, java.util.concurrent.TimeUnit.MILLISECONDS));
        android.util.Slog.v(TAG, "Running gc and finalizers");
    }

    private static void createFinalizationObserver(final java.util.concurrent.CountDownLatch countDownLatch) {
        new java.lang.Object() { // from class: com.android.internal.util.GcUtils.1
            protected void finalize() throws java.lang.Throwable {
                try {
                    countDownLatch.countDown();
                } finally {
                    super.finalize();
                }
            }
        };
    }

    private GcUtils() {
    }
}
