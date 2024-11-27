package com.android.server.timezonedetector.location;

/* loaded from: classes2.dex */
final class HandlerThreadingDomain extends com.android.server.timezonedetector.location.ThreadingDomain {

    @android.annotation.NonNull
    private final android.os.Handler mHandler;

    HandlerThreadingDomain(android.os.Handler handler) {
        java.util.Objects.requireNonNull(handler);
        this.mHandler = handler;
    }

    @android.annotation.NonNull
    android.os.Handler getHandler() {
        return this.mHandler;
    }

    @Override // com.android.server.timezonedetector.location.ThreadingDomain
    @android.annotation.NonNull
    java.lang.Thread getThread() {
        return getHandler().getLooper().getThread();
    }

    @Override // com.android.server.timezonedetector.location.ThreadingDomain
    void post(@android.annotation.NonNull java.lang.Runnable runnable) {
        getHandler().post(runnable);
    }

    @Override // com.android.server.timezonedetector.location.ThreadingDomain
    <V> V postAndWait(@android.annotation.NonNull final java.util.concurrent.Callable<V> callable, long j) throws java.lang.Exception {
        assertNotCurrentThread();
        final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
        final java.util.concurrent.atomic.AtomicReference atomicReference2 = new java.util.concurrent.atomic.AtomicReference();
        final java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
        post(new java.lang.Runnable() { // from class: com.android.server.timezonedetector.location.HandlerThreadingDomain$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.timezonedetector.location.HandlerThreadingDomain.lambda$postAndWait$0(atomicReference, callable, atomicReference2, countDownLatch);
            }
        });
        try {
            if (!countDownLatch.await(j, java.util.concurrent.TimeUnit.MILLISECONDS)) {
                throw new java.lang.RuntimeException("Timed out");
            }
            if (atomicReference2.get() != null) {
                throw ((java.lang.Exception) atomicReference2.get());
            }
            return (V) atomicReference.get();
        } catch (java.lang.InterruptedException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$postAndWait$0(java.util.concurrent.atomic.AtomicReference atomicReference, java.util.concurrent.Callable callable, java.util.concurrent.atomic.AtomicReference atomicReference2, java.util.concurrent.CountDownLatch countDownLatch) {
        try {
            try {
                atomicReference.set(callable.call());
            } catch (java.lang.Exception e) {
                atomicReference2.set(e);
            }
        } finally {
            countDownLatch.countDown();
        }
    }

    @Override // com.android.server.timezonedetector.location.ThreadingDomain
    void postDelayed(@android.annotation.NonNull java.lang.Runnable runnable, long j) {
        getHandler().postDelayed(runnable, j);
    }

    @Override // com.android.server.timezonedetector.location.ThreadingDomain
    void postDelayed(java.lang.Runnable runnable, java.lang.Object obj, long j) {
        getHandler().postDelayed(runnable, obj, j);
    }

    @Override // com.android.server.timezonedetector.location.ThreadingDomain
    void removeQueuedRunnables(java.lang.Object obj) {
        getHandler().removeCallbacksAndMessages(obj);
    }
}
