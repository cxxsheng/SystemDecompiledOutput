package com.android.server.integrity.model;

/* loaded from: classes2.dex */
public class ByteTrackedOutputStream extends java.io.OutputStream {
    private final java.io.OutputStream mOutputStream;
    private int mWrittenBytesCount = 0;

    public ByteTrackedOutputStream(java.io.OutputStream outputStream) {
        this.mOutputStream = outputStream;
    }

    @Override // java.io.OutputStream
    public void write(int i) throws java.io.IOException {
        this.mWrittenBytesCount++;
        this.mOutputStream.write(i);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) throws java.io.IOException {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws java.io.IOException {
        this.mWrittenBytesCount += i2;
        this.mOutputStream.write(bArr, i, i2);
    }

    public int getWrittenBytesCount() {
        return this.mWrittenBytesCount;
    }
}
