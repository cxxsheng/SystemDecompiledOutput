package com.android.server.integrity.parser;

/* loaded from: classes2.dex */
public class LimitInputStream extends java.io.FilterInputStream {
    private final int mLimit;
    private int mReadBytes;

    public LimitInputStream(java.io.InputStream inputStream, int i) {
        super(inputStream);
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("limit " + i + " cannot be negative");
        }
        this.mReadBytes = 0;
        this.mLimit = i;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int available() throws java.io.IOException {
        return java.lang.Math.min(super.available(), this.mLimit - this.mReadBytes);
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws java.io.IOException {
        if (this.mReadBytes == this.mLimit) {
            return -1;
        }
        this.mReadBytes++;
        return super.read();
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr) throws java.io.IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws java.io.IOException {
        if (i2 <= 0) {
            return 0;
        }
        int available = available();
        if (available <= 0) {
            return -1;
        }
        int read = super.read(bArr, i, java.lang.Math.min(i2, available));
        this.mReadBytes += read;
        return read;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public long skip(long j) throws java.io.IOException {
        int available;
        if (j <= 0 || (available = available()) <= 0) {
            return 0L;
        }
        long skip = super.skip((int) java.lang.Math.min(available, j));
        this.mReadBytes += (int) skip;
        return skip;
    }
}
