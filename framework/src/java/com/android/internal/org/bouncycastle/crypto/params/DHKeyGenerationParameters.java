package com.android.internal.org.bouncycastle.crypto.params;

/* loaded from: classes4.dex */
public class DHKeyGenerationParameters extends com.android.internal.org.bouncycastle.crypto.KeyGenerationParameters {
    private com.android.internal.org.bouncycastle.crypto.params.DHParameters params;

    public DHKeyGenerationParameters(java.security.SecureRandom secureRandom, com.android.internal.org.bouncycastle.crypto.params.DHParameters dHParameters) {
        super(secureRandom, getStrength(dHParameters));
        this.params = dHParameters;
    }

    public com.android.internal.org.bouncycastle.crypto.params.DHParameters getParameters() {
        return this.params;
    }

    static int getStrength(com.android.internal.org.bouncycastle.crypto.params.DHParameters dHParameters) {
        return dHParameters.getL() != 0 ? dHParameters.getL() : dHParameters.getP().bitLength();
    }
}
