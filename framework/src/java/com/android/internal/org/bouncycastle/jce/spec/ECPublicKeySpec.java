package com.android.internal.org.bouncycastle.jce.spec;

/* loaded from: classes4.dex */
public class ECPublicKeySpec extends com.android.internal.org.bouncycastle.jce.spec.ECKeySpec {
    private com.android.internal.org.bouncycastle.math.ec.ECPoint q;

    public ECPublicKeySpec(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, com.android.internal.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec) {
        super(eCParameterSpec);
        if (eCPoint.getCurve() != null) {
            this.q = eCPoint.normalize();
        } else {
            this.q = eCPoint;
        }
    }

    public com.android.internal.org.bouncycastle.math.ec.ECPoint getQ() {
        return this.q;
    }
}
