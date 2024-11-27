package com.android.internal.org.bouncycastle.crypto;

/* loaded from: classes4.dex */
public class AsymmetricCipherKeyPair {
    private com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter privateParam;
    private com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter publicParam;

    public AsymmetricCipherKeyPair(com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter asymmetricKeyParameter, com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter asymmetricKeyParameter2) {
        this.publicParam = asymmetricKeyParameter;
        this.privateParam = asymmetricKeyParameter2;
    }

    public AsymmetricCipherKeyPair(com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters, com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters2) {
        this.publicParam = (com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter) cipherParameters;
        this.privateParam = (com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter) cipherParameters2;
    }

    public com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter getPublic() {
        return this.publicParam;
    }

    public com.android.internal.org.bouncycastle.crypto.params.AsymmetricKeyParameter getPrivate() {
        return this.privateParam;
    }
}
