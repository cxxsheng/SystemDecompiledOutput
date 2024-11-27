package com.android.internal.org.bouncycastle.util.io;

/* loaded from: classes4.dex */
public class TeeOutputStream extends java.io.OutputStream {
    private java.io.OutputStream output1;
    private java.io.OutputStream output2;

    public TeeOutputStream(java.io.OutputStream outputStream, java.io.OutputStream outputStream2) {
        this.output1 = outputStream;
        this.output2 = outputStream2;
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) throws java.io.IOException {
        this.output1.write(bArr);
        this.output2.write(bArr);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws java.io.IOException {
        this.output1.write(bArr, i, i2);
        this.output2.write(bArr, i, i2);
    }

    @Override // java.io.OutputStream
    public void write(int i) throws java.io.IOException {
        this.output1.write(i);
        this.output2.write(i);
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws java.io.IOException {
        this.output1.flush();
        this.output2.flush();
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws java.io.IOException {
        this.output1.close();
        this.output2.close();
    }
}
