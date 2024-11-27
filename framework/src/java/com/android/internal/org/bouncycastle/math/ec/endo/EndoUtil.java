package com.android.internal.org.bouncycastle.math.ec.endo;

/* loaded from: classes4.dex */
public abstract class EndoUtil {
    public static final java.lang.String PRECOMP_NAME = "bc_endo";

    public static java.math.BigInteger[] decomposeScalar(com.android.internal.org.bouncycastle.math.ec.endo.ScalarSplitParameters scalarSplitParameters, java.math.BigInteger bigInteger) {
        int bits = scalarSplitParameters.getBits();
        java.math.BigInteger calculateB = calculateB(bigInteger, scalarSplitParameters.getG1(), bits);
        java.math.BigInteger calculateB2 = calculateB(bigInteger, scalarSplitParameters.getG2(), bits);
        return new java.math.BigInteger[]{bigInteger.subtract(calculateB.multiply(scalarSplitParameters.getV1A()).add(calculateB2.multiply(scalarSplitParameters.getV2A()))), calculateB.multiply(scalarSplitParameters.getV1B()).add(calculateB2.multiply(scalarSplitParameters.getV2B())).negate()};
    }

    public static com.android.internal.org.bouncycastle.math.ec.ECPoint mapPoint(final com.android.internal.org.bouncycastle.math.ec.endo.ECEndomorphism eCEndomorphism, final com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint) {
        return ((com.android.internal.org.bouncycastle.math.ec.endo.EndoPreCompInfo) eCPoint.getCurve().precompute(eCPoint, PRECOMP_NAME, new com.android.internal.org.bouncycastle.math.ec.PreCompCallback() { // from class: com.android.internal.org.bouncycastle.math.ec.endo.EndoUtil.1
            @Override // com.android.internal.org.bouncycastle.math.ec.PreCompCallback
            public com.android.internal.org.bouncycastle.math.ec.PreCompInfo precompute(com.android.internal.org.bouncycastle.math.ec.PreCompInfo preCompInfo) {
                com.android.internal.org.bouncycastle.math.ec.endo.EndoPreCompInfo endoPreCompInfo = preCompInfo instanceof com.android.internal.org.bouncycastle.math.ec.endo.EndoPreCompInfo ? (com.android.internal.org.bouncycastle.math.ec.endo.EndoPreCompInfo) preCompInfo : null;
                if (checkExisting(endoPreCompInfo, com.android.internal.org.bouncycastle.math.ec.endo.ECEndomorphism.this)) {
                    return endoPreCompInfo;
                }
                com.android.internal.org.bouncycastle.math.ec.ECPoint map = com.android.internal.org.bouncycastle.math.ec.endo.ECEndomorphism.this.getPointMap().map(eCPoint);
                com.android.internal.org.bouncycastle.math.ec.endo.EndoPreCompInfo endoPreCompInfo2 = new com.android.internal.org.bouncycastle.math.ec.endo.EndoPreCompInfo();
                endoPreCompInfo2.setEndomorphism(com.android.internal.org.bouncycastle.math.ec.endo.ECEndomorphism.this);
                endoPreCompInfo2.setMappedPoint(map);
                return endoPreCompInfo2;
            }

            private boolean checkExisting(com.android.internal.org.bouncycastle.math.ec.endo.EndoPreCompInfo endoPreCompInfo, com.android.internal.org.bouncycastle.math.ec.endo.ECEndomorphism eCEndomorphism2) {
                return (endoPreCompInfo == null || endoPreCompInfo.getEndomorphism() != eCEndomorphism2 || endoPreCompInfo.getMappedPoint() == null) ? false : true;
            }
        })).getMappedPoint();
    }

    private static java.math.BigInteger calculateB(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, int i) {
        boolean z = bigInteger2.signum() < 0;
        java.math.BigInteger multiply = bigInteger.multiply(bigInteger2.abs());
        boolean testBit = multiply.testBit(i - 1);
        java.math.BigInteger shiftRight = multiply.shiftRight(i);
        if (testBit) {
            shiftRight = shiftRight.add(com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE);
        }
        return z ? shiftRight.negate() : shiftRight;
    }
}
