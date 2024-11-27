package com.android.internal.org.bouncycastle.jcajce.spec;

/* loaded from: classes4.dex */
public class DHDomainParameterSpec extends javax.crypto.spec.DHParameterSpec {
    private final java.math.BigInteger j;
    private final int m;
    private final java.math.BigInteger q;
    private com.android.internal.org.bouncycastle.crypto.params.DHValidationParameters validationParameters;

    public DHDomainParameterSpec(com.android.internal.org.bouncycastle.crypto.params.DHParameters dHParameters) {
        this(dHParameters.getP(), dHParameters.getQ(), dHParameters.getG(), dHParameters.getJ(), dHParameters.getM(), dHParameters.getL());
        this.validationParameters = dHParameters.getValidationParameters();
    }

    public DHDomainParameterSpec(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, java.math.BigInteger bigInteger3) {
        this(bigInteger, bigInteger2, bigInteger3, null, 0);
    }

    public DHDomainParameterSpec(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, java.math.BigInteger bigInteger3, int i) {
        this(bigInteger, bigInteger2, bigInteger3, null, i);
    }

    public DHDomainParameterSpec(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, java.math.BigInteger bigInteger3, java.math.BigInteger bigInteger4, int i) {
        this(bigInteger, bigInteger2, bigInteger3, bigInteger4, 0, i);
    }

    public DHDomainParameterSpec(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, java.math.BigInteger bigInteger3, java.math.BigInteger bigInteger4, int i, int i2) {
        super(bigInteger, bigInteger3, i2);
        this.q = bigInteger2;
        this.j = bigInteger4;
        this.m = i;
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

    public com.android.internal.org.bouncycastle.crypto.params.DHParameters getDomainParameters() {
        return new com.android.internal.org.bouncycastle.crypto.params.DHParameters(getP(), getG(), this.q, this.m, getL(), this.j, this.validationParameters);
    }
}
