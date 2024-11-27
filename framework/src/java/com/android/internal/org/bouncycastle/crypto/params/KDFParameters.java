package com.android.internal.org.bouncycastle.crypto.params;

/* loaded from: classes4.dex */
public class KDFParameters implements com.android.internal.org.bouncycastle.crypto.DerivationParameters {
    byte[] iv;
    byte[] shared;

    public KDFParameters(byte[] bArr, byte[] bArr2) {
        this.shared = bArr;
        this.iv = bArr2;
    }

    public byte[] getSharedSecret() {
        return this.shared;
    }

    public byte[] getIV() {
        return this.iv;
    }
}
