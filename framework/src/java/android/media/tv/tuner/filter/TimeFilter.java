package android.media.tv.tuner.filter;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class TimeFilter implements java.lang.AutoCloseable {
    private boolean mEnable = false;
    private long mNativeContext;

    private native int nativeClearTimestamp();

    private native int nativeClose();

    private native java.lang.Long nativeGetSourceTime();

    private native java.lang.Long nativeGetTimestamp();

    private native int nativeSetTimestamp(long j);

    private TimeFilter() {
    }

    public int setCurrentTimestamp(long j) {
        int nativeSetTimestamp = nativeSetTimestamp(j);
        if (nativeSetTimestamp == 0) {
            this.mEnable = true;
        }
        return nativeSetTimestamp;
    }

    public int clearTimestamp() {
        int nativeClearTimestamp = nativeClearTimestamp();
        if (nativeClearTimestamp == 0) {
            this.mEnable = false;
        }
        return nativeClearTimestamp;
    }

    public long getTimeStamp() {
        if (!this.mEnable) {
            return -1L;
        }
        return nativeGetTimestamp().longValue();
    }

    public long getSourceTime() {
        if (!this.mEnable) {
            return -1L;
        }
        return nativeGetSourceTime().longValue();
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        int nativeClose = nativeClose();
        if (nativeClose != 0) {
            android.media.tv.tuner.TunerUtils.throwExceptionForResult(nativeClose, "Failed to close time filter.");
        }
    }
}
