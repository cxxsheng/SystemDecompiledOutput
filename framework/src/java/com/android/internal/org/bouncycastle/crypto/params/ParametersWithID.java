package com.android.internal.org.bouncycastle.crypto.params;

/* loaded from: classes4.dex */
public class ParametersWithID implements com.android.internal.org.bouncycastle.crypto.CipherParameters {
    private byte[] id;
    private com.android.internal.org.bouncycastle.crypto.CipherParameters parameters;

    public ParametersWithID(com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters, byte[] bArr) {
        this.parameters = cipherParameters;
        this.id = bArr;
    }

    public byte[] getID() {
        return this.id;
    }

    public com.android.internal.org.bouncycastle.crypto.CipherParameters getParameters() {
        return this.parameters;
    }
}
