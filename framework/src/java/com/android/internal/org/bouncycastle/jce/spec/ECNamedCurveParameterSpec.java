package com.android.internal.org.bouncycastle.jce.spec;

/* loaded from: classes4.dex */
public class ECNamedCurveParameterSpec extends com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec {
    private java.lang.String name;

    public ECNamedCurveParameterSpec(java.lang.String str, com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, java.math.BigInteger bigInteger) {
        super(eCCurve, eCPoint, bigInteger);
        this.name = str;
    }

    public ECNamedCurveParameterSpec(java.lang.String str, com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
        super(eCCurve, eCPoint, bigInteger, bigInteger2);
        this.name = str;
    }

    public ECNamedCurveParameterSpec(java.lang.String str, com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, byte[] bArr) {
        super(eCCurve, eCPoint, bigInteger, bigInteger2, bArr);
        this.name = str;
    }

    public java.lang.String getName() {
        return this.name;
    }
}
