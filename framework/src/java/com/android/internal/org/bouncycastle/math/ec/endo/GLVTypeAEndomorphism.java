package com.android.internal.org.bouncycastle.math.ec.endo;

/* loaded from: classes4.dex */
public class GLVTypeAEndomorphism implements com.android.internal.org.bouncycastle.math.ec.endo.GLVEndomorphism {
    protected final com.android.internal.org.bouncycastle.math.ec.endo.GLVTypeAParameters parameters;
    protected final com.android.internal.org.bouncycastle.math.ec.ECPointMap pointMap;

    public GLVTypeAEndomorphism(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.math.ec.endo.GLVTypeAParameters gLVTypeAParameters) {
        this.parameters = gLVTypeAParameters;
        this.pointMap = new com.android.internal.org.bouncycastle.math.ec.ScaleYNegateXPointMap(eCCurve.fromBigInteger(gLVTypeAParameters.getI()));
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.endo.GLVEndomorphism
    public java.math.BigInteger[] decomposeScalar(java.math.BigInteger bigInteger) {
        return com.android.internal.org.bouncycastle.math.ec.endo.EndoUtil.decomposeScalar(this.parameters.getSplitParams(), bigInteger);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.endo.ECEndomorphism
    public com.android.internal.org.bouncycastle.math.ec.ECPointMap getPointMap() {
        return this.pointMap;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.endo.ECEndomorphism
    public boolean hasEfficientPointMap() {
        return true;
    }
}