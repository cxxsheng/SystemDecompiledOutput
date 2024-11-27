package com.android.internal.org.bouncycastle.crypto.io;

/* loaded from: classes4.dex */
public class DigestInputStream extends java.io.FilterInputStream {
    protected com.android.internal.org.bouncycastle.crypto.Digest digest;

    public DigestInputStream(java.io.InputStream inputStream, com.android.internal.org.bouncycastle.crypto.Digest digest) {
        super(inputStream);
        this.digest = digest;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws java.io.IOException {
        int read = this.in.read();
        if (read >= 0) {
            this.digest.update((byte) read);
        }
        return read;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws java.io.IOException {
        int read = this.in.read(bArr, i, i2);
        if (read > 0) {
            this.digest.update(bArr, i, read);
        }
        return read;
    }

    public com.android.internal.org.bouncycastle.crypto.Digest getDigest() {
        return this.digest;
    }
}
