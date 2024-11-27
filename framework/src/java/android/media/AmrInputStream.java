package android.media;

/* loaded from: classes2.dex */
public final class AmrInputStream extends java.io.InputStream {
    private static final int SAMPLES_PER_FRAME = 160;
    private static final java.lang.String TAG = "AmrInputStream";
    android.media.MediaCodec mCodec;
    android.media.MediaCodec.BufferInfo mInfo;
    private java.io.InputStream mInputStream;
    boolean mSawInputEOS;
    boolean mSawOutputEOS;
    private final byte[] mBuf = new byte[320];
    private int mBufIn = 0;
    private int mBufOut = 0;
    private byte[] mOneByte = new byte[1];

    public AmrInputStream(java.io.InputStream inputStream) {
        android.util.Log.w(TAG, "@@@@ AmrInputStream is not a public API @@@@");
        this.mInputStream = inputStream;
        android.media.MediaFormat mediaFormat = new android.media.MediaFormat();
        mediaFormat.setString(android.media.MediaFormat.KEY_MIME, "audio/3gpp");
        mediaFormat.setInteger(android.media.MediaFormat.KEY_SAMPLE_RATE, com.android.internal.widget.remotecompose.core.operations.BitmapData.MAX_IMAGE_DIMENSION);
        mediaFormat.setInteger(android.media.MediaFormat.KEY_CHANNEL_COUNT, 1);
        mediaFormat.setInteger(android.media.MediaFormat.KEY_BIT_RATE, 12200);
        java.lang.String findEncoderForFormat = new android.media.MediaCodecList(0).findEncoderForFormat(mediaFormat);
        if (findEncoderForFormat != null) {
            try {
                this.mCodec = android.media.MediaCodec.createByCodecName(findEncoderForFormat);
                this.mCodec.configure(mediaFormat, (android.view.Surface) null, (android.media.MediaCrypto) null, 1);
                this.mCodec.start();
            } catch (java.io.IOException e) {
                if (this.mCodec != null) {
                    this.mCodec.release();
                }
                this.mCodec = null;
            }
        }
        this.mInfo = new android.media.MediaCodec.BufferInfo();
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
        int i3;
        int dequeueInputBuffer;
        if (this.mCodec != null) {
            if (this.mBufOut >= this.mBufIn && !this.mSawOutputEOS) {
                this.mBufOut = 0;
                this.mBufIn = 0;
                while (!this.mSawInputEOS && (dequeueInputBuffer = this.mCodec.dequeueInputBuffer(0L)) >= 0) {
                    int i4 = 0;
                    while (true) {
                        if (i4 >= 320) {
                            break;
                        }
                        int read = this.mInputStream.read(this.mBuf, i4, 320 - i4);
                        if (read == -1) {
                            this.mSawInputEOS = true;
                            break;
                        }
                        i4 += read;
                    }
                    this.mCodec.getInputBuffer(dequeueInputBuffer).put(this.mBuf, 0, i4);
                    this.mCodec.queueInputBuffer(dequeueInputBuffer, 0, i4, 0L, this.mSawInputEOS ? 4 : 0);
                }
                int dequeueOutputBuffer = this.mCodec.dequeueOutputBuffer(this.mInfo, 0L);
                if (dequeueOutputBuffer >= 0) {
                    this.mBufIn = this.mInfo.size;
                    this.mCodec.getOutputBuffer(dequeueOutputBuffer).get(this.mBuf, 0, this.mBufIn);
                    this.mCodec.releaseOutputBuffer(dequeueOutputBuffer, false);
                    if ((this.mInfo.flags & 4) != 0) {
                        this.mSawOutputEOS = true;
                    }
                }
            }
            if (this.mBufOut >= this.mBufIn) {
                return (this.mSawInputEOS && this.mSawOutputEOS) ? -1 : 0;
            }
            if (i2 > this.mBufIn - this.mBufOut) {
                i3 = this.mBufIn - this.mBufOut;
            } else {
                i3 = i2;
            }
            java.lang.System.arraycopy(this.mBuf, this.mBufOut, bArr, i, i3);
            this.mBufOut += i3;
            return i3;
        }
        throw new java.lang.IllegalStateException("not open");
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws java.io.IOException {
        try {
            if (this.mInputStream != null) {
                this.mInputStream.close();
            }
            this.mInputStream = null;
            try {
                if (this.mCodec != null) {
                    this.mCodec.release();
                }
            } finally {
            }
        } catch (java.lang.Throwable th) {
            this.mInputStream = null;
            try {
                if (this.mCodec != null) {
                    this.mCodec.release();
                }
                throw th;
            } finally {
            }
        }
    }

    protected void finalize() throws java.lang.Throwable {
        if (this.mCodec != null) {
            android.util.Log.w(TAG, "AmrInputStream wasn't closed");
            this.mCodec.release();
        }
    }
}
