package com.android.internal.org.bouncycastle.jce.spec;

/* loaded from: classes4.dex */
public class ECParameterSpec implements java.security.spec.AlgorithmParameterSpec {
    private com.android.internal.org.bouncycastle.math.ec.ECPoint G;
    private com.android.internal.org.bouncycastle.math.ec.ECCurve curve;
    private java.math.BigInteger h;
    private java.math.BigInteger n;
    private byte[] seed;

    public ECParameterSpec(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, java.math.BigInteger bigInteger) {
        this.curve = eCCurve;
        this.G = eCPoint.normalize();
        this.n = bigInteger;
        this.h = java.math.BigInteger.valueOf(1L);
        this.seed = null;
    }

    public ECParameterSpec(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
        this.curve = eCCurve;
        this.G = eCPoint.normalize();
        this.n = bigInteger;
        this.h = bigInteger2;
        this.seed = null;
    }

    public ECParameterSpec(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, byte[] bArr) {
        this.curve = eCCurve;
        this.G = eCPoint.normalize();
        this.n = bigInteger;
        this.h = bigInteger2;
        this.seed = bArr;
    }

    public com.android.internal.org.bouncycastle.math.ec.ECCurve getCurve() {
        return this.curve;
    }

    public com.android.internal.org.bouncycastle.math.ec.ECPoint getG() {
        return this.G;
    }

    public java.math.BigInteger getN() {
        return this.n;
    }

    public java.math.BigInteger getH() {
        return this.h;
    }

    public byte[] getSeed() {
        return this.seed;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec)) {
            return false;
        }
        com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec = (com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec) obj;
        return getCurve().equals(eCParameterSpec.getCurve()) && getG().equals(eCParameterSpec.getG());
    }

    public int hashCode() {
        return getCurve().hashCode() ^ getG().hashCode();
    }
}
