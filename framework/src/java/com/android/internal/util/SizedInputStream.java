package com.android.internal.util;

/* loaded from: classes5.dex */
public class SizedInputStream extends java.io.InputStream {
    private long mLength;
    private final java.io.InputStream mWrapped;

    public SizedInputStream(java.io.InputStream inputStream, long j) {
        this.mWrapped = inputStream;
        this.mLength = j;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws java.io.IOException {
        super.close();
        this.mWrapped.close();
    }

    @Override // java.io.InputStream
    public int read() throws java.io.IOException {
        byte[] bArr = new byte[1];
        if (read(bArr, 0, 1) != -1) {
            return bArr[0] & 255;
        }
        return -1;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws java.io.IOException {
        if (this.mLength <= 0) {
            return -1;
        }
        if (i2 > this.mLength) {
            i2 = (int) this.mLength;
        }
        int read = this.mWrapped.read(bArr, i, i2);
        if (read != -1) {
            this.mLength -= read;
        } else if (this.mLength > 0) {
            throw new java.io.IOException("Unexpected EOF; expected " + this.mLength + " more bytes");
        }
        return read;
    }
}
