package com.android.internal.org.bouncycastle.crypto.params;

/* loaded from: classes4.dex */
public class DSAKeyGenerationParameters extends com.android.internal.org.bouncycastle.crypto.KeyGenerationParameters {
    private com.android.internal.org.bouncycastle.crypto.params.DSAParameters params;

    public DSAKeyGenerationParameters(java.security.SecureRandom secureRandom, com.android.internal.org.bouncycastle.crypto.params.DSAParameters dSAParameters) {
        super(secureRandom, dSAParameters.getP().bitLength() - 1);
        this.params = dSAParameters;
    }

    public com.android.internal.org.bouncycastle.crypto.params.DSAParameters getParameters() {
        return this.params;
    }
}
