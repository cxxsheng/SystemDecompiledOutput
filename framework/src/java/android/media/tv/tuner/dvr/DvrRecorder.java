package android.media.tv.tuner.dvr;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class DvrRecorder implements java.lang.AutoCloseable {
    private static final java.lang.String TAG = "TvTunerRecord";
    private static int sInstantId = 0;
    private java.util.concurrent.Executor mExecutor;
    private android.media.tv.tuner.dvr.OnRecordStatusChangedListener mListener;
    private long mNativeContext;
    private int mOverflow;
    private int mSegmentId;
    private final java.lang.Object mIsStoppedLock = new java.lang.Object();
    private boolean mIsStopped = true;
    private final java.lang.Object mListenerLock = new java.lang.Object();
    private int mUserId = android.os.Process.myUid();

    private native int nativeAttachFilter(android.media.tv.tuner.filter.Filter filter);

    private native int nativeClose();

    private native int nativeConfigureDvr(android.media.tv.tuner.dvr.DvrSettings dvrSettings);

    private native int nativeDetachFilter(android.media.tv.tuner.filter.Filter filter);

    private native int nativeFlushDvr();

    private native void nativeSetFileDescriptor(int i);

    private native int nativeSetStatusCheckIntervalHint(long j);

    private native int nativeStartDvr();

    private native int nativeStopDvr();

    private native long nativeWrite(long j);

    private native long nativeWrite(byte[] bArr, long j, long j2);

    private DvrRecorder() {
        this.mSegmentId = 0;
        this.mSegmentId = (sInstantId & 65535) << 16;
        sInstantId++;
    }

    public void setListener(java.util.concurrent.Executor executor, android.media.tv.tuner.dvr.OnRecordStatusChangedListener onRecordStatusChangedListener) {
        synchronized (this.mListenerLock) {
            this.mExecutor = executor;
            this.mListener = onRecordStatusChangedListener;
        }
    }

    private void onRecordStatusChanged(final int i) {
        if (i == 8) {
            this.mOverflow++;
        }
        synchronized (this.mListenerLock) {
            if (this.mExecutor != null && this.mListener != null) {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.tuner.dvr.DvrRecorder$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.tv.tuner.dvr.DvrRecorder.this.lambda$onRecordStatusChanged$0(i);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onRecordStatusChanged$0(int i) {
        synchronized (this.mListenerLock) {
            if (this.mListener != null) {
                this.mListener.onRecordStatusChanged(i);
            }
        }
    }

    public int attachFilter(android.media.tv.tuner.filter.Filter filter) {
        return nativeAttachFilter(filter);
    }

    public int detachFilter(android.media.tv.tuner.filter.Filter filter) {
        return nativeDetachFilter(filter);
    }

    public int configure(android.media.tv.tuner.dvr.DvrSettings dvrSettings) {
        return nativeConfigureDvr(dvrSettings);
    }

    public int setRecordBufferStatusCheckIntervalHint(long j) {
        if (!android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(196608, "Set status check interval hint")) {
            return 1;
        }
        return nativeSetStatusCheckIntervalHint(j);
    }

    public int start() {
        int nativeStartDvr;
        this.mSegmentId = (this.mSegmentId & (-65536)) | (((this.mSegmentId & 65535) + 1) & 65535);
        this.mOverflow = 0;
        android.util.Log.d(TAG, "Write Stats Log for Record.");
        com.android.internal.util.FrameworkStatsLog.write(279, this.mUserId, 2, 1, this.mSegmentId, 0);
        synchronized (this.mIsStoppedLock) {
            nativeStartDvr = nativeStartDvr();
            if (nativeStartDvr == 0) {
                this.mIsStopped = false;
            }
        }
        return nativeStartDvr;
    }

    public int stop() {
        int nativeStopDvr;
        android.util.Log.d(TAG, "Write Stats Log for Playback.");
        com.android.internal.util.FrameworkStatsLog.write(279, this.mUserId, 2, 2, this.mSegmentId, this.mOverflow);
        synchronized (this.mIsStoppedLock) {
            nativeStopDvr = nativeStopDvr();
            if (nativeStopDvr == 0) {
                this.mIsStopped = true;
            }
        }
        return nativeStopDvr;
    }

    public int flush() {
        synchronized (this.mIsStoppedLock) {
            if (this.mIsStopped) {
                return nativeFlushDvr();
            }
            android.util.Log.w(TAG, "Cannot flush non-stopped Record DVR.");
            return 3;
        }
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        int nativeClose = nativeClose();
        if (nativeClose != 0) {
            android.media.tv.tuner.TunerUtils.throwExceptionForResult(nativeClose, "failed to close DVR recorder");
        }
    }

    public void setFileDescriptor(android.os.ParcelFileDescriptor parcelFileDescriptor) {
        nativeSetFileDescriptor(parcelFileDescriptor.getFd());
    }

    public long write(long j) {
        return nativeWrite(j);
    }

    public long write(byte[] bArr, long j, long j2) {
        if (j2 + j > bArr.length) {
            throw new java.lang.ArrayIndexOutOfBoundsException("Array length=" + bArr.length + ", offset=" + j + ", size=" + j2);
        }
        return nativeWrite(bArr, j, j2);
    }
}
