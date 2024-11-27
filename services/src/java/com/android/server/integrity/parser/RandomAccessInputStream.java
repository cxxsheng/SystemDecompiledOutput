package com.android.server.integrity.parser;

/* loaded from: classes2.dex */
public class RandomAccessInputStream extends java.io.InputStream {
    private int mPosition = 0;
    private final com.android.server.integrity.parser.RandomAccessObject mRandomAccessObject;

    public RandomAccessInputStream(com.android.server.integrity.parser.RandomAccessObject randomAccessObject) throws java.io.IOException {
        this.mRandomAccessObject = randomAccessObject;
    }

    public int getPosition() {
        return this.mPosition;
    }

    public void seek(int i) throws java.io.IOException {
        this.mRandomAccessObject.seek(i);
        this.mPosition = i;
    }

    @Override // java.io.InputStream
    public int available() throws java.io.IOException {
        return this.mRandomAccessObject.length() - this.mPosition;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws java.io.IOException {
        this.mRandomAccessObject.close();
    }

    @Override // java.io.InputStream
    public int read() throws java.io.IOException {
        if (available() <= 0) {
            return -1;
        }
        this.mPosition++;
        return this.mRandomAccessObject.read();
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws java.io.IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws java.io.IOException {
        if (i2 <= 0) {
            return 0;
        }
        int available = available();
        if (available <= 0) {
            return -1;
        }
        int read = this.mRandomAccessObject.read(bArr, i, java.lang.Math.min(i2, available));
        this.mPosition += read;
        return read;
    }

    @Override // java.io.InputStream
    public long skip(long j) throws java.io.IOException {
        int available;
        if (j <= 0 || (available = available()) <= 0) {
            return 0L;
        }
        int min = (int) java.lang.Math.min(available, j);
        this.mPosition += min;
        this.mRandomAccessObject.seek(this.mPosition);
        return min;
    }
}
