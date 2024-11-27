package com.android.internal.org.bouncycastle.crypto.io;

/* loaded from: classes4.dex */
public class MacOutputStream extends java.io.OutputStream {
    protected com.android.internal.org.bouncycastle.crypto.Mac mac;

    public MacOutputStream(com.android.internal.org.bouncycastle.crypto.Mac mac) {
        this.mac = mac;
    }

    @Override // java.io.OutputStream
    public void write(int i) throws java.io.IOException {
        this.mac.update((byte) i);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws java.io.IOException {
        this.mac.update(bArr, i, i2);
    }

    public byte[] getMac() {
        byte[] bArr = new byte[this.mac.getMacSize()];
        this.mac.doFinal(bArr, 0);
        return bArr;
    }
}
