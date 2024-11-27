package com.android.internal.org.bouncycastle.math.ec;

/* loaded from: classes4.dex */
public class FixedPointCombMultiplier extends com.android.internal.org.bouncycastle.math.ec.AbstractECMultiplier {
    @Override // com.android.internal.org.bouncycastle.math.ec.AbstractECMultiplier
    protected com.android.internal.org.bouncycastle.math.ec.ECPoint multiplyPositive(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, java.math.BigInteger bigInteger) {
        com.android.internal.org.bouncycastle.math.ec.ECCurve curve = eCPoint.getCurve();
        if (bigInteger.bitLength() > com.android.internal.org.bouncycastle.math.ec.FixedPointUtil.getCombSize(curve)) {
            throw new java.lang.IllegalStateException("fixed-point comb doesn't support scalars larger than the curve order");
        }
        com.android.internal.org.bouncycastle.math.ec.FixedPointPreCompInfo precompute = com.android.internal.org.bouncycastle.math.ec.FixedPointUtil.precompute(eCPoint);
        com.android.internal.org.bouncycastle.math.ec.ECLookupTable lookupTable = precompute.getLookupTable();
        int width = precompute.getWidth();
        int i = ((r1 + width) - 1) / width;
        com.android.internal.org.bouncycastle.math.ec.ECPoint infinity = curve.getInfinity();
        int i2 = width * i;
        int[] fromBigInteger = com.android.internal.org.bouncycastle.math.raw.Nat.fromBigInteger(i2, bigInteger);
        int i3 = i2 - 1;
        for (int i4 = 0; i4 < i; i4++) {
            int i5 = 0;
            for (int i6 = i3 - i4; i6 >= 0; i6 -= i) {
                int i7 = fromBigInteger[i6 >>> 5] >>> (i6 & 31);
                i5 = ((i5 ^ (i7 >>> 1)) << 1) ^ i7;
            }
            infinity = infinity.twicePlus(lookupTable.lookup(i5));
        }
        return infinity.add(precompute.getOffset());
    }
}
