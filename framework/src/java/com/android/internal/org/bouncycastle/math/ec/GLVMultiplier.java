package com.android.internal.org.bouncycastle.math.ec;

/* loaded from: classes4.dex */
public class GLVMultiplier extends com.android.internal.org.bouncycastle.math.ec.AbstractECMultiplier {
    protected final com.android.internal.org.bouncycastle.math.ec.ECCurve curve;
    protected final com.android.internal.org.bouncycastle.math.ec.endo.GLVEndomorphism glvEndomorphism;

    public GLVMultiplier(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.math.ec.endo.GLVEndomorphism gLVEndomorphism) {
        if (eCCurve == null || eCCurve.getOrder() == null) {
            throw new java.lang.IllegalArgumentException("Need curve with known group order");
        }
        this.curve = eCCurve;
        this.glvEndomorphism = gLVEndomorphism;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.AbstractECMultiplier
    protected com.android.internal.org.bouncycastle.math.ec.ECPoint multiplyPositive(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, java.math.BigInteger bigInteger) {
        if (!this.curve.equals(eCPoint.getCurve())) {
            throw new java.lang.IllegalStateException();
        }
        java.math.BigInteger[] decomposeScalar = this.glvEndomorphism.decomposeScalar(bigInteger.mod(eCPoint.getCurve().getOrder()));
        java.math.BigInteger bigInteger2 = decomposeScalar[0];
        java.math.BigInteger bigInteger3 = decomposeScalar[1];
        if (this.glvEndomorphism.hasEfficientPointMap()) {
            return com.android.internal.org.bouncycastle.math.ec.ECAlgorithms.implShamirsTrickWNaf(this.glvEndomorphism, eCPoint, bigInteger2, bigInteger3);
        }
        return com.android.internal.org.bouncycastle.math.ec.ECAlgorithms.implShamirsTrickWNaf(eCPoint, bigInteger2, com.android.internal.org.bouncycastle.math.ec.endo.EndoUtil.mapPoint(this.glvEndomorphism, eCPoint), bigInteger3);
    }
}
