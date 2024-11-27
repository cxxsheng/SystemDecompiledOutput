package android.media.tv.tuner.filter;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class SharedFilter implements java.lang.AutoCloseable {
    public static final int STATUS_INACCESSIBLE = 128;
    private static final java.lang.String TAG = "SharedFilter";
    private android.media.tv.tuner.filter.SharedFilterCallback mCallback;
    private java.lang.Object mCallbackLock;
    private java.util.concurrent.Executor mExecutor;
    private java.lang.Object mLock;
    private long mNativeContext;
    private boolean mIsClosed = false;
    private boolean mIsAccessible = true;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Status {
    }

    private native int nativeFlushSharedFilter();

    private native int nativeSharedClose();

    private native int nativeSharedRead(byte[] bArr, long j, long j2);

    private native int nativeStartSharedFilter();

    private native int nativeStopSharedFilter();

    private SharedFilter() {
        this.mCallbackLock = null;
        this.mLock = null;
        this.mCallbackLock = new java.lang.Object();
        this.mLock = new java.lang.Object();
    }

    private void onFilterStatus(final int i) {
        synchronized (this.mLock) {
            if (i == 128) {
                this.mIsAccessible = false;
            }
        }
        synchronized (this.mCallbackLock) {
            if (this.mCallback != null && this.mExecutor != null) {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.tuner.filter.SharedFilter$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.tv.tuner.filter.SharedFilter.this.lambda$onFilterStatus$0(i);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFilterStatus$0(int i) {
        synchronized (this.mCallbackLock) {
            if (this.mCallback != null) {
                this.mCallback.onFilterStatusChanged(this, i);
            }
        }
    }

    private void onFilterEvent(final android.media.tv.tuner.filter.FilterEvent[] filterEventArr) {
        synchronized (this.mCallbackLock) {
            if (this.mCallback == null || this.mExecutor == null) {
                for (android.media.tv.tuner.filter.FilterEvent filterEvent : filterEventArr) {
                    if (filterEvent instanceof android.media.tv.tuner.filter.MediaEvent) {
                        ((android.media.tv.tuner.filter.MediaEvent) filterEvent).release();
                    }
                }
            } else {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.tuner.filter.SharedFilter$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.tv.tuner.filter.SharedFilter.this.lambda$onFilterEvent$1(filterEventArr);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFilterEvent$1(android.media.tv.tuner.filter.FilterEvent[] filterEventArr) {
        synchronized (this.mCallbackLock) {
            if (this.mCallback != null) {
                this.mCallback.onFilterEvent(this, filterEventArr);
            } else {
                for (android.media.tv.tuner.filter.FilterEvent filterEvent : filterEventArr) {
                    if (filterEvent instanceof android.media.tv.tuner.filter.MediaEvent) {
                        ((android.media.tv.tuner.filter.MediaEvent) filterEvent).release();
                    }
                }
            }
        }
    }

    public void setCallback(android.media.tv.tuner.filter.SharedFilterCallback sharedFilterCallback, java.util.concurrent.Executor executor) {
        synchronized (this.mCallbackLock) {
            this.mCallback = sharedFilterCallback;
            this.mExecutor = executor;
        }
    }

    public android.media.tv.tuner.filter.SharedFilterCallback getCallback() {
        android.media.tv.tuner.filter.SharedFilterCallback sharedFilterCallback;
        synchronized (this.mCallbackLock) {
            sharedFilterCallback = this.mCallback;
        }
        return sharedFilterCallback;
    }

    public int start() {
        int nativeStartSharedFilter;
        synchronized (this.mLock) {
            android.media.tv.tuner.TunerUtils.checkResourceAccessible(TAG, this.mIsAccessible);
            android.media.tv.tuner.TunerUtils.checkResourceState(TAG, this.mIsClosed);
            nativeStartSharedFilter = nativeStartSharedFilter();
        }
        return nativeStartSharedFilter;
    }

    public int stop() {
        int nativeStopSharedFilter;
        synchronized (this.mLock) {
            android.media.tv.tuner.TunerUtils.checkResourceAccessible(TAG, this.mIsAccessible);
            android.media.tv.tuner.TunerUtils.checkResourceState(TAG, this.mIsClosed);
            nativeStopSharedFilter = nativeStopSharedFilter();
        }
        return nativeStopSharedFilter;
    }

    public int flush() {
        int nativeFlushSharedFilter;
        synchronized (this.mLock) {
            android.media.tv.tuner.TunerUtils.checkResourceAccessible(TAG, this.mIsAccessible);
            android.media.tv.tuner.TunerUtils.checkResourceState(TAG, this.mIsClosed);
            nativeFlushSharedFilter = nativeFlushSharedFilter();
        }
        return nativeFlushSharedFilter;
    }

    public int read(byte[] bArr, long j, long j2) {
        int nativeSharedRead;
        synchronized (this.mLock) {
            android.media.tv.tuner.TunerUtils.checkResourceAccessible(TAG, this.mIsAccessible);
            android.media.tv.tuner.TunerUtils.checkResourceState(TAG, this.mIsClosed);
            nativeSharedRead = nativeSharedRead(bArr, j, java.lang.Math.min(j2, bArr.length - j));
        }
        return nativeSharedRead;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        synchronized (this.mLock) {
            if (this.mIsClosed) {
                return;
            }
            synchronized (this.mCallbackLock) {
                this.mCallback = null;
                this.mExecutor = null;
            }
            nativeSharedClose();
            this.mIsClosed = true;
            this.mCallbackLock = null;
        }
    }
}
