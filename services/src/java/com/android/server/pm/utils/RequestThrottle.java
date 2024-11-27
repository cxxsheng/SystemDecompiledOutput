package com.android.server.pm.utils;

/* loaded from: classes2.dex */
public class RequestThrottle {
    private static final int DEFAULT_BACKOFF_BASE = 2;
    private static final int DEFAULT_DELAY_MS = 1000;
    private static final int DEFAULT_RETRY_MAX_ATTEMPTS = 5;
    private final int mBackoffBase;

    @android.annotation.NonNull
    private final java.util.function.Supplier<java.lang.Boolean> mBlock;
    private final java.util.concurrent.atomic.AtomicInteger mCurrentRetry;
    private final int mFirstDelay;

    @android.annotation.NonNull
    private final android.os.Handler mHandler;
    private final java.util.concurrent.atomic.AtomicInteger mLastCommitted;
    private final java.util.concurrent.atomic.AtomicInteger mLastRequest;
    private final int mMaxAttempts;

    @android.annotation.NonNull
    private final java.lang.Runnable mRunnable;

    public RequestThrottle(@android.annotation.NonNull android.os.Handler handler, @android.annotation.NonNull java.util.function.Supplier<java.lang.Boolean> supplier) {
        this(handler, 5, 1000, 2, supplier);
    }

    public RequestThrottle(@android.annotation.NonNull android.os.Handler handler, int i, int i2, int i3, @android.annotation.NonNull java.util.function.Supplier<java.lang.Boolean> supplier) {
        this.mLastRequest = new java.util.concurrent.atomic.AtomicInteger(0);
        this.mLastCommitted = new java.util.concurrent.atomic.AtomicInteger(-1);
        this.mCurrentRetry = new java.util.concurrent.atomic.AtomicInteger(0);
        this.mHandler = handler;
        this.mBlock = supplier;
        this.mMaxAttempts = i;
        this.mFirstDelay = i2;
        this.mBackoffBase = i3;
        this.mRunnable = new java.lang.Runnable() { // from class: com.android.server.pm.utils.RequestThrottle$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.pm.utils.RequestThrottle.this.runInternal();
            }
        };
    }

    public void schedule() {
        this.mLastRequest.incrementAndGet();
        this.mHandler.post(this.mRunnable);
    }

    public boolean runNow() {
        this.mLastRequest.incrementAndGet();
        return runInternal();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean runInternal() {
        int i = this.mLastRequest.get();
        if (i == this.mLastCommitted.get()) {
            return true;
        }
        if (this.mBlock.get().booleanValue()) {
            this.mCurrentRetry.set(0);
            this.mLastCommitted.set(i);
            return true;
        }
        int andIncrement = this.mCurrentRetry.getAndIncrement();
        if (andIncrement < this.mMaxAttempts) {
            this.mHandler.postDelayed(this.mRunnable, (long) (this.mFirstDelay * java.lang.Math.pow(this.mBackoffBase, andIncrement)));
        } else {
            this.mCurrentRetry.set(0);
        }
        return false;
    }
}
