package com.android.internal.org.bouncycastle.crypto.params;

/* loaded from: classes4.dex */
public class RSAKeyGenerationParameters extends com.android.internal.org.bouncycastle.crypto.KeyGenerationParameters {
    private int certainty;
    private java.math.BigInteger publicExponent;

    public RSAKeyGenerationParameters(java.math.BigInteger bigInteger, java.security.SecureRandom secureRandom, int i, int i2) {
        super(secureRandom, i);
        if (i < 12) {
            throw new java.lang.IllegalArgumentException("key strength too small");
        }
        if (!bigInteger.testBit(0)) {
            throw new java.lang.IllegalArgumentException("public exponent cannot be even");
        }
        this.publicExponent = bigInteger;
        this.certainty = i2;
    }

    public java.math.BigInteger getPublicExponent() {
        return this.publicExponent;
    }

    public int getCertainty() {
        return this.certainty;
    }
}
