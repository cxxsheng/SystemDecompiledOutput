package com.android.internal.org.bouncycastle.jcajce.io;

/* loaded from: classes4.dex */
class SignatureUpdatingOutputStream extends java.io.OutputStream {
    private java.security.Signature sig;

    SignatureUpdatingOutputStream(java.security.Signature signature) {
        this.sig = signature;
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws java.io.IOException {
        try {
            this.sig.update(bArr, i, i2);
        } catch (java.security.SignatureException e) {
            throw new java.io.IOException(e.getMessage());
        }
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) throws java.io.IOException {
        try {
            this.sig.update(bArr);
        } catch (java.security.SignatureException e) {
            throw new java.io.IOException(e.getMessage());
        }
    }

    @Override // java.io.OutputStream
    public void write(int i) throws java.io.IOException {
        try {
            this.sig.update((byte) i);
        } catch (java.security.SignatureException e) {
            throw new java.io.IOException(e.getMessage());
        }
    }
}
