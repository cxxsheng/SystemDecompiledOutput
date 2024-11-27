package com.android.internal.org.bouncycastle.crypto.io;

/* loaded from: classes4.dex */
public class DigestOutputStream extends java.io.OutputStream {
    protected com.android.internal.org.bouncycastle.crypto.Digest digest;

    public DigestOutputStream(com.android.internal.org.bouncycastle.crypto.Digest digest) {
        this.digest = digest;
    }

    @Override // java.io.OutputStream
    public void write(int i) throws java.io.IOException {
        this.digest.update((byte) i);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws java.io.IOException {
        this.digest.update(bArr, i, i2);
    }

    public byte[] getDigest() {
        byte[] bArr = new byte[this.digest.getDigestSize()];
        this.digest.doFinal(bArr, 0);
        return bArr;
    }
}
