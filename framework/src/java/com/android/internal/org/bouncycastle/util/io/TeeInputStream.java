package com.android.internal.org.bouncycastle.util.io;

/* loaded from: classes4.dex */
public class TeeInputStream extends java.io.InputStream {
    private final java.io.InputStream input;
    private final java.io.OutputStream output;

    public TeeInputStream(java.io.InputStream inputStream, java.io.OutputStream outputStream) {
        this.input = inputStream;
        this.output = outputStream;
    }

    @Override // java.io.InputStream
    public int available() throws java.io.IOException {
        return this.input.available();
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws java.io.IOException {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws java.io.IOException {
        int read = this.input.read(bArr, i, i2);
        if (read > 0) {
            this.output.write(bArr, i, read);
        }
        return read;
    }

    @Override // java.io.InputStream
    public int read() throws java.io.IOException {
        int read = this.input.read();
        if (read >= 0) {
            this.output.write(read);
        }
        return read;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws java.io.IOException {
        this.input.close();
        this.output.close();
    }

    public java.io.OutputStream getOutputStream() {
        return this.output;
    }
}
