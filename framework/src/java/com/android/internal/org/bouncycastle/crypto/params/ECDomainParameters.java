package com.android.internal.org.bouncycastle.crypto.params;

/* loaded from: classes4.dex */
public class ECDomainParameters implements com.android.internal.org.bouncycastle.math.ec.ECConstants {
    private final com.android.internal.org.bouncycastle.math.ec.ECPoint G;
    private final com.android.internal.org.bouncycastle.math.ec.ECCurve curve;
    private final java.math.BigInteger h;
    private java.math.BigInteger hInv;
    private final java.math.BigInteger n;
    private final byte[] seed;

    public ECDomainParameters(com.android.internal.org.bouncycastle.asn1.x9.X9ECParameters x9ECParameters) {
        this(x9ECParameters.getCurve(), x9ECParameters.getG(), x9ECParameters.getN(), x9ECParameters.getH(), x9ECParameters.getSeed());
    }

    public ECDomainParameters(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, java.math.BigInteger bigInteger) {
        this(eCCurve, eCPoint, bigInteger, ONE, null);
    }

    public ECDomainParameters(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
        this(eCCurve, eCPoint, bigInteger, bigInteger2, null);
    }

    public ECDomainParameters(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, byte[] bArr) {
        this.hInv = null;
        if (eCCurve == null) {
            throw new java.lang.NullPointerException("curve");
        }
        if (bigInteger == null) {
            throw new java.lang.NullPointerException("n");
        }
        this.curve = eCCurve;
        this.G = validatePublicPoint(eCCurve, eCPoint);
        this.n = bigInteger;
        this.h = bigInteger2;
        this.seed = com.android.internal.org.bouncycastle.util.Arrays.clone(bArr);
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

    public synchronized java.math.BigInteger getHInv() {
        if (this.hInv == null) {
            this.hInv = com.android.internal.org.bouncycastle.util.BigIntegers.modOddInverseVar(this.n, this.h);
        }
        return this.hInv;
    }

    public byte[] getSeed() {
        return com.android.internal.org.bouncycastle.util.Arrays.clone(this.seed);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters)) {
            return false;
        }
        com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters eCDomainParameters = (com.android.internal.org.bouncycastle.crypto.params.ECDomainParameters) obj;
        return this.curve.equals(eCDomainParameters.curve) && this.G.equals(eCDomainParameters.G) && this.n.equals(eCDomainParameters.n);
    }

    public int hashCode() {
        return ((((this.curve.hashCode() ^ 1028) * 257) ^ this.G.hashCode()) * 257) ^ this.n.hashCode();
    }

    public java.math.BigInteger validatePrivateScalar(java.math.BigInteger bigInteger) {
        if (bigInteger == null) {
            throw new java.lang.NullPointerException("Scalar cannot be null");
        }
        if (bigInteger.compareTo(com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE) < 0 || bigInteger.compareTo(getN()) >= 0) {
            throw new java.lang.IllegalArgumentException("Scalar is not in the interval [1, n - 1]");
        }
        return bigInteger;
    }

    public com.android.internal.org.bouncycastle.math.ec.ECPoint validatePublicPoint(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint) {
        return validatePublicPoint(getCurve(), eCPoint);
    }

    static com.android.internal.org.bouncycastle.math.ec.ECPoint validatePublicPoint(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint) {
        if (eCPoint == null) {
            throw new java.lang.NullPointerException("Point cannot be null");
        }
        com.android.internal.org.bouncycastle.math.ec.ECPoint normalize = com.android.internal.org.bouncycastle.math.ec.ECAlgorithms.importPoint(eCCurve, eCPoint).normalize();
        if (normalize.isInfinity()) {
            throw new java.lang.IllegalArgumentException("Point at infinity");
        }
        if (!normalize.isValid()) {
            throw new java.lang.IllegalArgumentException("Point not on curve");
        }
        return normalize;
    }
}
