package com.android.internal.org.bouncycastle.crypto.params;

/* loaded from: classes4.dex */
public class ParametersWithIV implements com.android.internal.org.bouncycastle.crypto.CipherParameters {
    private byte[] iv;
    private com.android.internal.org.bouncycastle.crypto.CipherParameters parameters;

    public ParametersWithIV(com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters, byte[] bArr) {
        this(cipherParameters, bArr, 0, bArr.length);
    }

    public ParametersWithIV(com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters, byte[] bArr, int i, int i2) {
        this.iv = new byte[i2];
        this.parameters = cipherParameters;
        java.lang.System.arraycopy(bArr, i, this.iv, 0, i2);
    }

    public byte[] getIV() {
        return this.iv;
    }

    public com.android.internal.org.bouncycastle.crypto.CipherParameters getParameters() {
        return this.parameters;
    }
}
