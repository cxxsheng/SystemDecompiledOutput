package com.android.internal.inputmethod;

/* loaded from: classes4.dex */
public final class CancellationGroup {
    private final java.lang.Object mLock = new java.lang.Object();
    private java.util.ArrayList<java.util.concurrent.CompletableFuture<?>> mFutureList = null;
    private boolean mCanceled = false;

    boolean tryRegisterFutureOrCancelImmediately(java.util.concurrent.CompletableFuture<?> completableFuture) {
        synchronized (this.mLock) {
            if (this.mCanceled) {
                completableFuture.cancel(false);
                return false;
            }
            if (this.mFutureList == null) {
                this.mFutureList = new java.util.ArrayList<>(1);
            }
            this.mFutureList.add(completableFuture);
            return true;
        }
    }

    void unregisterFuture(java.util.concurrent.CompletableFuture<?> completableFuture) {
        synchronized (this.mLock) {
            if (this.mFutureList != null) {
                this.mFutureList.remove(completableFuture);
            }
        }
    }

    public void cancelAll() {
        synchronized (this.mLock) {
            if (!this.mCanceled) {
                this.mCanceled = true;
                if (this.mFutureList != null) {
                    this.mFutureList.forEach(new java.util.function.Consumer() { // from class: com.android.internal.inputmethod.CancellationGroup$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            ((java.util.concurrent.CompletableFuture) obj).cancel(false);
                        }
                    });
                    this.mFutureList.clear();
                    this.mFutureList = null;
                }
            }
        }
    }

    public boolean isCanceled() {
        boolean z;
        synchronized (this.mLock) {
            z = this.mCanceled;
        }
        return z;
    }
}
