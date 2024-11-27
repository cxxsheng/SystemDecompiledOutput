package android.media.tv.tuner.dvr;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class DvrPlayback implements java.lang.AutoCloseable {
    public static final int PLAYBACK_STATUS_ALMOST_EMPTY = 2;
    public static final int PLAYBACK_STATUS_ALMOST_FULL = 4;
    public static final int PLAYBACK_STATUS_EMPTY = 1;
    public static final int PLAYBACK_STATUS_FULL = 8;
    private static final java.lang.String TAG = "TvTunerPlayback";
    private static int sInstantId = 0;
    private java.util.concurrent.Executor mExecutor;
    private android.media.tv.tuner.dvr.OnPlaybackStatusChangedListener mListener;
    private long mNativeContext;
    private int mSegmentId;
    private int mUnderflow;
    private final java.lang.Object mListenerLock = new java.lang.Object();
    private int mUserId = android.os.Process.myUid();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface PlaybackStatus {
    }

    private native int nativeAttachFilter(android.media.tv.tuner.filter.Filter filter);

    private native int nativeClose();

    private native int nativeConfigureDvr(android.media.tv.tuner.dvr.DvrSettings dvrSettings);

    private native int nativeDetachFilter(android.media.tv.tuner.filter.Filter filter);

    private native int nativeFlushDvr();

    private native long nativeRead(long j);

    private native long nativeRead(byte[] bArr, long j, long j2);

    private native long nativeSeek(long j);

    private native void nativeSetFileDescriptor(int i);

    private native int nativeSetStatusCheckIntervalHint(long j);

    private native int nativeStartDvr();

    private native int nativeStopDvr();

    private DvrPlayback() {
        this.mSegmentId = 0;
        this.mSegmentId = (sInstantId & 65535) << 16;
        sInstantId++;
    }

    public void setListener(java.util.concurrent.Executor executor, android.media.tv.tuner.dvr.OnPlaybackStatusChangedListener onPlaybackStatusChangedListener) {
        synchronized (this.mListenerLock) {
            this.mExecutor = executor;
            this.mListener = onPlaybackStatusChangedListener;
        }
    }

    private void onPlaybackStatusChanged(final int i) {
        if (i == 1) {
            this.mUnderflow++;
        }
        synchronized (this.mListenerLock) {
            if (this.mExecutor != null && this.mListener != null) {
                this.mExecutor.execute(new java.lang.Runnable() { // from class: android.media.tv.tuner.dvr.DvrPlayback$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.tv.tuner.dvr.DvrPlayback.this.lambda$onPlaybackStatusChanged$0(i);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPlaybackStatusChanged$0(int i) {
        synchronized (this.mListenerLock) {
            if (this.mListener != null) {
                this.mListener.onPlaybackStatusChanged(i);
            }
        }
    }

    @java.lang.Deprecated
    public int attachFilter(android.media.tv.tuner.filter.Filter filter) {
        return 1;
    }

    @java.lang.Deprecated
    public int detachFilter(android.media.tv.tuner.filter.Filter filter) {
        return 1;
    }

    public int configure(android.media.tv.tuner.dvr.DvrSettings dvrSettings) {
        return nativeConfigureDvr(dvrSettings);
    }

    public int setPlaybackBufferStatusCheckIntervalHint(long j) {
        if (!android.media.tv.tuner.TunerVersionChecker.checkHigherOrEqualVersionTo(196608, "Set status check interval hint")) {
            return 1;
        }
        return nativeSetStatusCheckIntervalHint(j);
    }

    public int start() {
        this.mSegmentId = (this.mSegmentId & (-65536)) | (((this.mSegmentId & 65535) + 1) & 65535);
        this.mUnderflow = 0;
        android.util.Log.d(TAG, "Write Stats Log for Playback.");
        com.android.internal.util.FrameworkStatsLog.write(279, this.mUserId, 1, 1, this.mSegmentId, 0);
        return nativeStartDvr();
    }

    public int stop() {
        android.util.Log.d(TAG, "Write Stats Log for Playback.");
        com.android.internal.util.FrameworkStatsLog.write(279, this.mUserId, 1, 2, this.mSegmentId, this.mUnderflow);
        return nativeStopDvr();
    }

    public int flush() {
        return nativeFlushDvr();
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        int nativeClose = nativeClose();
        if (nativeClose != 0) {
            android.media.tv.tuner.TunerUtils.throwExceptionForResult(nativeClose, "failed to close DVR playback");
        }
    }

    public void setFileDescriptor(android.os.ParcelFileDescriptor parcelFileDescriptor) {
        nativeSetFileDescriptor(parcelFileDescriptor.getFd());
    }

    public long read(long j) {
        return nativeRead(j);
    }

    public long read(byte[] bArr, long j, long j2) {
        if (j2 + j > bArr.length) {
            throw new java.lang.ArrayIndexOutOfBoundsException("Array length=" + bArr.length + ", offset=" + j + ", size=" + j2);
        }
        return nativeRead(bArr, j, j2);
    }

    public long seek(long j) {
        return nativeSeek(j);
    }
}
