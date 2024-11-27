package com.android.internal.org.bouncycastle.crypto.params;

/* loaded from: classes4.dex */
public class DSAParameters implements com.android.internal.org.bouncycastle.crypto.CipherParameters {
    private java.math.BigInteger g;
    private java.math.BigInteger p;
    private java.math.BigInteger q;
    private com.android.internal.org.bouncycastle.crypto.params.DSAValidationParameters validation;

    public DSAParameters(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, java.math.BigInteger bigInteger3) {
        this.g = bigInteger3;
        this.p = bigInteger;
        this.q = bigInteger2;
    }

    public DSAParameters(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, java.math.BigInteger bigInteger3, com.android.internal.org.bouncycastle.crypto.params.DSAValidationParameters dSAValidationParameters) {
        this.g = bigInteger3;
        this.p = bigInteger;
        this.q = bigInteger2;
        this.validation = dSAValidationParameters;
    }

    public java.math.BigInteger getP() {
        return this.p;
    }

    public java.math.BigInteger getQ() {
        return this.q;
    }

    public java.math.BigInteger getG() {
        return this.g;
    }

    public com.android.internal.org.bouncycastle.crypto.params.DSAValidationParameters getValidationParameters() {
        return this.validation;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof com.android.internal.org.bouncycastle.crypto.params.DSAParameters)) {
            return false;
        }
        com.android.internal.org.bouncycastle.crypto.params.DSAParameters dSAParameters = (com.android.internal.org.bouncycastle.crypto.params.DSAParameters) obj;
        return dSAParameters.getP().equals(this.p) && dSAParameters.getQ().equals(this.q) && dSAParameters.getG().equals(this.g);
    }

    public int hashCode() {
        return (getP().hashCode() ^ getQ().hashCode()) ^ getG().hashCode();
    }
}
