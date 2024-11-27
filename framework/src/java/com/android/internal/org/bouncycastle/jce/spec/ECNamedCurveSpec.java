package com.android.internal.org.bouncycastle.jce.spec;

/* loaded from: classes4.dex */
public class ECNamedCurveSpec extends java.security.spec.ECParameterSpec {
    private java.lang.String name;

    private static java.security.spec.EllipticCurve convertCurve(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, byte[] bArr) {
        return new java.security.spec.EllipticCurve(convertField(eCCurve.getField()), eCCurve.getA().toBigInteger(), eCCurve.getB().toBigInteger(), bArr);
    }

    private static java.security.spec.ECField convertField(com.android.internal.org.bouncycastle.math.field.FiniteField finiteField) {
        if (com.android.internal.org.bouncycastle.math.ec.ECAlgorithms.isFpField(finiteField)) {
            return new java.security.spec.ECFieldFp(finiteField.getCharacteristic());
        }
        com.android.internal.org.bouncycastle.math.field.Polynomial minimalPolynomial = ((com.android.internal.org.bouncycastle.math.field.PolynomialExtensionField) finiteField).getMinimalPolynomial();
        int[] exponentsPresent = minimalPolynomial.getExponentsPresent();
        return new java.security.spec.ECFieldF2m(minimalPolynomial.getDegree(), com.android.internal.org.bouncycastle.util.Arrays.reverse(com.android.internal.org.bouncycastle.util.Arrays.copyOfRange(exponentsPresent, 1, exponentsPresent.length - 1)));
    }

    public ECNamedCurveSpec(java.lang.String str, com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, java.math.BigInteger bigInteger) {
        super(convertCurve(eCCurve, null), com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(eCPoint), bigInteger, 1);
        this.name = str;
    }

    public ECNamedCurveSpec(java.lang.String str, java.security.spec.EllipticCurve ellipticCurve, java.security.spec.ECPoint eCPoint, java.math.BigInteger bigInteger) {
        super(ellipticCurve, eCPoint, bigInteger, 1);
        this.name = str;
    }

    public ECNamedCurveSpec(java.lang.String str, com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
        super(convertCurve(eCCurve, null), com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(eCPoint), bigInteger, bigInteger2.intValue());
        this.name = str;
    }

    public ECNamedCurveSpec(java.lang.String str, java.security.spec.EllipticCurve ellipticCurve, java.security.spec.ECPoint eCPoint, java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
        super(ellipticCurve, eCPoint, bigInteger, bigInteger2.intValue());
        this.name = str;
    }

    public ECNamedCurveSpec(java.lang.String str, com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, byte[] bArr) {
        super(convertCurve(eCCurve, bArr), com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util.convertPoint(eCPoint), bigInteger, bigInteger2.intValue());
        this.name = str;
    }

    public java.lang.String getName() {
        return this.name;
    }
}
