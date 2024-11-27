package com.android.internal.org.bouncycastle.crypto.params;

/* loaded from: classes4.dex */
public class DHParameters implements com.android.internal.org.bouncycastle.crypto.CipherParameters {
    private static final int DEFAULT_MINIMUM_LENGTH = 160;
    private java.math.BigInteger g;
    private java.math.BigInteger j;
    private int l;
    private int m;
    private java.math.BigInteger p;
    private java.math.BigInteger q;
    private com.android.internal.org.bouncycastle.crypto.params.DHValidationParameters validation;

    private static int getDefaultMParam(int i) {
        if (i != 0 && i < 160) {
            return i;
        }
        return 160;
    }

    public DHParameters(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
        this(bigInteger, bigInteger2, null, 0);
    }

    public DHParameters(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, java.math.BigInteger bigInteger3) {
        this(bigInteger, bigInteger2, bigInteger3, 0);
    }

    public DHParameters(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, java.math.BigInteger bigInteger3, int i) {
        this(bigInteger, bigInteger2, bigInteger3, getDefaultMParam(i), i, null, null);
    }

    public DHParameters(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, java.math.BigInteger bigInteger3, int i, int i2) {
        this(bigInteger, bigInteger2, bigInteger3, i, i2, null, null);
    }

    public DHParameters(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, java.math.BigInteger bigInteger3, java.math.BigInteger bigInteger4, com.android.internal.org.bouncycastle.crypto.params.DHValidationParameters dHValidationParameters) {
        this(bigInteger, bigInteger2, bigInteger3, 160, 0, bigInteger4, dHValidationParameters);
    }

    public DHParameters(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, java.math.BigInteger bigInteger3, int i, int i2, java.math.BigInteger bigInteger4, com.android.internal.org.bouncycastle.crypto.params.DHValidationParameters dHValidationParameters) {
        if (i2 != 0) {
            if (i2 > bigInteger.bitLength()) {
                throw new java.lang.IllegalArgumentException("when l value specified, it must satisfy 2^(l-1) <= p");
            }
            if (i2 < i) {
                throw new java.lang.IllegalArgumentException("when l value specified, it may not be less than m value");
            }
        }
        if (i > bigInteger.bitLength() && !com.android.internal.org.bouncycastle.util.Properties.isOverrideSet("com.android.internal.org.bouncycastle.dh.allow_unsafe_p_value")) {
            throw new java.lang.IllegalArgumentException("unsafe p value so small specific l required");
        }
        this.g = bigInteger2;
        this.p = bigInteger;
        this.q = bigInteger3;
        this.m = i;
        this.l = i2;
        this.j = bigInteger4;
        this.validation = dHValidationParameters;
    }

    public java.math.BigInteger getP() {
        return this.p;
    }

    public java.math.BigInteger getG() {
        return this.g;
    }

    public java.math.BigInteger getQ() {
        return this.q;
    }

    public java.math.BigInteger getJ() {
        return this.j;
    }

    public int getM() {
        return this.m;
    }

    public int getL() {
        return this.l;
    }

    public com.android.internal.org.bouncycastle.crypto.params.DHValidationParameters getValidationParameters() {
        return this.validation;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof com.android.internal.org.bouncycastle.crypto.params.DHParameters)) {
            return false;
        }
        com.android.internal.org.bouncycastle.crypto.params.DHParameters dHParameters = (com.android.internal.org.bouncycastle.crypto.params.DHParameters) obj;
        if (getQ() != null) {
            if (!getQ().equals(dHParameters.getQ())) {
                return false;
            }
        } else if (dHParameters.getQ() != null) {
            return false;
        }
        return dHParameters.getP().equals(this.p) && dHParameters.getG().equals(this.g);
    }

    public int hashCode() {
        return (getP().hashCode() ^ getG().hashCode()) ^ (getQ() != null ? getQ().hashCode() : 0);
    }
}
