package com.android.internal.org.bouncycastle.crypto.params;

/* loaded from: classes4.dex */
public class RSAPrivateCrtKeyParameters extends com.android.internal.org.bouncycastle.crypto.params.RSAKeyParameters {
    private java.math.BigInteger dP;
    private java.math.BigInteger dQ;
    private java.math.BigInteger e;
    private java.math.BigInteger p;
    private java.math.BigInteger q;
    private java.math.BigInteger qInv;

    public RSAPrivateCrtKeyParameters(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, java.math.BigInteger bigInteger3, java.math.BigInteger bigInteger4, java.math.BigInteger bigInteger5, java.math.BigInteger bigInteger6, java.math.BigInteger bigInteger7, java.math.BigInteger bigInteger8) {
        super(true, bigInteger, bigInteger3);
        this.e = bigInteger2;
        this.p = bigInteger4;
        this.q = bigInteger5;
        this.dP = bigInteger6;
        this.dQ = bigInteger7;
        this.qInv = bigInteger8;
    }

    public java.math.BigInteger getPublicExponent() {
        return this.e;
    }

    public java.math.BigInteger getP() {
        return this.p;
    }

    public java.math.BigInteger getQ() {
        return this.q;
    }

    public java.math.BigInteger getDP() {
        return this.dP;
    }

    public java.math.BigInteger getDQ() {
        return this.dQ;
    }

    public java.math.BigInteger getQInv() {
        return this.qInv;
    }
}
