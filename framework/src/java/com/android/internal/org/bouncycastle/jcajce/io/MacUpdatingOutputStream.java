package com.android.internal.org.bouncycastle.jcajce.io;

/* loaded from: classes4.dex */
class MacUpdatingOutputStream extends java.io.OutputStream {
    private javax.crypto.Mac mac;

    MacUpdatingOutputStream(javax.crypto.Mac mac) {
        this.mac = mac;
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws java.io.IOException {
        this.mac.update(bArr, i, i2);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) throws java.io.IOException {
        this.mac.update(bArr);
    }

    @Override // java.io.OutputStream
    public void write(int i) throws java.io.IOException {
        this.mac.update((byte) i);
    }
}
