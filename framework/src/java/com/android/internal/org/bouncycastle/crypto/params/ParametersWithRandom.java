package com.android.internal.org.bouncycastle.crypto.params;

/* loaded from: classes4.dex */
public class ParametersWithRandom implements com.android.internal.org.bouncycastle.crypto.CipherParameters {
    private com.android.internal.org.bouncycastle.crypto.CipherParameters parameters;
    private java.security.SecureRandom random;

    public ParametersWithRandom(com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters, java.security.SecureRandom secureRandom) {
        this.random = com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar.getSecureRandom(secureRandom);
        this.parameters = cipherParameters;
    }

    public ParametersWithRandom(com.android.internal.org.bouncycastle.crypto.CipherParameters cipherParameters) {
        this(cipherParameters, null);
    }

    public java.security.SecureRandom getRandom() {
        return this.random;
    }

    public com.android.internal.org.bouncycastle.crypto.CipherParameters getParameters() {
        return this.parameters;
    }
}
