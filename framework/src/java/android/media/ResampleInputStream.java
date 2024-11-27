package android.media;

/* loaded from: classes2.dex */
public final class ResampleInputStream extends java.io.InputStream {
    private static final java.lang.String TAG = "ResampleInputStream";
    private static final int mFirLength = 29;
    private byte[] mBuf;
    private int mBufCount;
    private java.io.InputStream mInputStream;
    private final byte[] mOneByte = new byte[1];
    private final int mRateIn;
    private final int mRateOut;

    private static native void fir21(byte[] bArr, int i, byte[] bArr2, int i2, int i3);

    static {
        java.lang.System.loadLibrary("media_jni");
    }

    public ResampleInputStream(java.io.InputStream inputStream, int i, int i2) {
        if (i != i2 * 2) {
            throw new java.lang.IllegalArgumentException("only support 2:1 at the moment");
        }
        this.mInputStream = inputStream;
        this.mRateIn = 2;
        this.mRateOut = 1;
    }

    @Override // java.io.InputStream
    public int read() throws java.io.IOException {
        if (read(this.mOneByte, 0, 1) == 1) {
            return this.mOneByte[0] & 255;
        }
        return -1;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws java.io.IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws java.io.IOException {
        if (this.mInputStream == null) {
            throw new java.lang.IllegalStateException("not open");
        }
        int i3 = i2 / 2;
        int i4 = (((this.mRateIn * i3) / this.mRateOut) + 29) * 2;
        if (this.mBuf == null) {
            this.mBuf = new byte[i4];
        } else if (i4 > this.mBuf.length) {
            byte[] bArr2 = new byte[i4];
            java.lang.System.arraycopy(this.mBuf, 0, bArr2, 0, this.mBufCount);
            this.mBuf = bArr2;
        }
        while (true) {
            int i5 = ((((this.mBufCount / 2) - 29) * this.mRateOut) / this.mRateIn) * 2;
            if (i5 > 0) {
                if (i5 >= i2) {
                    i5 = i3 * 2;
                }
                fir21(this.mBuf, 0, bArr, i, i5 / 2);
                int i6 = (this.mRateIn * i5) / this.mRateOut;
                this.mBufCount -= i6;
                if (this.mBufCount > 0) {
                    java.lang.System.arraycopy(this.mBuf, i6, this.mBuf, 0, this.mBufCount);
                }
                return i5;
            }
            int read = this.mInputStream.read(this.mBuf, this.mBufCount, this.mBuf.length - this.mBufCount);
            if (read == -1) {
                return -1;
            }
            this.mBufCount += read;
        }
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws java.io.IOException {
        try {
            if (this.mInputStream != null) {
                this.mInputStream.close();
            }
        } finally {
            this.mInputStream = null;
        }
    }

    protected void finalize() throws java.lang.Throwable {
        if (this.mInputStream != null) {
            close();
            throw new java.lang.IllegalStateException("someone forgot to close ResampleInputStream");
        }
    }
}
