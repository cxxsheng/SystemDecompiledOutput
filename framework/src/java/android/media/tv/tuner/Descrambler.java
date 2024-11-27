package android.media.tv.tuner;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class Descrambler implements java.lang.AutoCloseable {
    public static final int PID_TYPE_MMTP = 2;
    public static final int PID_TYPE_T = 1;
    private static final java.lang.String TAG = "Descrambler";
    private boolean mIsClosed = false;
    private final java.lang.Object mLock = new java.lang.Object();
    private long mNativeContext;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PidType {
    }

    private native int nativeAddPid(int i, int i2, android.media.tv.tuner.filter.Filter filter);

    private native int nativeClose();

    private native int nativeRemovePid(int i, int i2, android.media.tv.tuner.filter.Filter filter);

    private native int nativeSetKeyToken(byte[] bArr);

    private Descrambler() {
    }

    public int addPid(int i, int i2, android.media.tv.tuner.filter.Filter filter) {
        int nativeAddPid;
        synchronized (this.mLock) {
            android.media.tv.tuner.TunerUtils.checkResourceState(TAG, this.mIsClosed);
            nativeAddPid = nativeAddPid(i, i2, filter);
        }
        return nativeAddPid;
    }

    public int removePid(int i, int i2, android.media.tv.tuner.filter.Filter filter) {
        int nativeRemovePid;
        synchronized (this.mLock) {
            android.media.tv.tuner.TunerUtils.checkResourceState(TAG, this.mIsClosed);
            nativeRemovePid = nativeRemovePid(i, i2, filter);
        }
        return nativeRemovePid;
    }

    public int setKeyToken(byte[] bArr) {
        synchronized (this.mLock) {
            android.media.tv.tuner.TunerUtils.checkResourceState(TAG, this.mIsClosed);
            java.util.Objects.requireNonNull(bArr, "key token must not be null");
            if (!isValidKeyToken(bArr)) {
                return 4;
            }
            return nativeSetKeyToken(bArr);
        }
    }

    public static boolean isValidKeyToken(byte[] bArr) {
        if (bArr.length == 0 || bArr.length > 16) {
            android.util.Log.d(TAG, "Invalid key token size: " + (bArr.length * 8) + " bit.");
            return false;
        }
        return true;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        synchronized (this.mLock) {
            if (this.mIsClosed) {
                return;
            }
            int nativeClose = nativeClose();
            if (nativeClose != 0) {
                android.media.tv.tuner.TunerUtils.throwExceptionForResult(nativeClose, "Failed to close descrambler");
            } else {
                this.mIsClosed = true;
            }
        }
    }
}
