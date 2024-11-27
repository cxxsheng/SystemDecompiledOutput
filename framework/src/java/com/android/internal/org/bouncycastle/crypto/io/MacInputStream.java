package com.android.internal.org.bouncycastle.crypto.io;

/* loaded from: classes4.dex */
public class MacInputStream extends java.io.FilterInputStream {
    protected com.android.internal.org.bouncycastle.crypto.Mac mac;

    public MacInputStream(java.io.InputStream inputStream, com.android.internal.org.bouncycastle.crypto.Mac mac) {
        super(inputStream);
        this.mac = mac;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws java.io.IOException {
        int read = this.in.read();
        if (read >= 0) {
            this.mac.update((byte) read);
        }
        return read;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws java.io.IOException {
        int read = this.in.read(bArr, i, i2);
        if (read >= 0) {
            this.mac.update(bArr, i, read);
        }
        return read;
    }

    public com.android.internal.org.bouncycastle.crypto.Mac getMac() {
        return this.mac;
    }
}
