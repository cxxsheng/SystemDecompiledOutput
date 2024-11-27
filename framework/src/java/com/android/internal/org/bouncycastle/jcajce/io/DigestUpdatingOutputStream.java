package com.android.internal.org.bouncycastle.jcajce.io;

/* loaded from: classes4.dex */
class DigestUpdatingOutputStream extends java.io.OutputStream {
    private java.security.MessageDigest digest;

    DigestUpdatingOutputStream(java.security.MessageDigest messageDigest) {
        this.digest = messageDigest;
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws java.io.IOException {
        this.digest.update(bArr, i, i2);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) throws java.io.IOException {
        this.digest.update(bArr);
    }

    @Override // java.io.OutputStream
    public void write(int i) throws java.io.IOException {
        this.digest.update((byte) i);
    }
}
