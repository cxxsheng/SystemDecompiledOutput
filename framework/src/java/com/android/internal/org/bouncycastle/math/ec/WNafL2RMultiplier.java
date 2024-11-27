package com.android.internal.org.bouncycastle.math.ec;

/* loaded from: classes4.dex */
public class WNafL2RMultiplier extends com.android.internal.org.bouncycastle.math.ec.AbstractECMultiplier {
    @Override // com.android.internal.org.bouncycastle.math.ec.AbstractECMultiplier
    protected com.android.internal.org.bouncycastle.math.ec.ECPoint multiplyPositive(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, java.math.BigInteger bigInteger) {
        com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint2;
        com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo precompute = com.android.internal.org.bouncycastle.math.ec.WNafUtil.precompute(eCPoint, com.android.internal.org.bouncycastle.math.ec.WNafUtil.getWindowSize(bigInteger.bitLength()), true);
        com.android.internal.org.bouncycastle.math.ec.ECPoint[] preComp = precompute.getPreComp();
        com.android.internal.org.bouncycastle.math.ec.ECPoint[] preCompNeg = precompute.getPreCompNeg();
        int width = precompute.getWidth();
        int[] generateCompactWindowNaf = com.android.internal.org.bouncycastle.math.ec.WNafUtil.generateCompactWindowNaf(width, bigInteger);
        com.android.internal.org.bouncycastle.math.ec.ECPoint infinity = eCPoint.getCurve().getInfinity();
        int length = generateCompactWindowNaf.length;
        if (length > 1) {
            length--;
            int i = generateCompactWindowNaf[length];
            int i2 = i >> 16;
            int i3 = i & 65535;
            int abs = java.lang.Math.abs(i2);
            com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr = i2 < 0 ? preCompNeg : preComp;
            if ((abs << 2) < (1 << width)) {
                int numberOfLeadingZeros = 32 - com.android.internal.org.bouncycastle.util.Integers.numberOfLeadingZeros(abs);
                int i4 = width - numberOfLeadingZeros;
                eCPoint2 = eCPointArr[((1 << (width - 1)) - 1) >>> 1].add(eCPointArr[(((abs ^ (1 << (numberOfLeadingZeros - 1))) << i4) + 1) >>> 1]);
                i3 -= i4;
            } else {
                eCPoint2 = eCPointArr[abs >>> 1];
            }
            infinity = eCPoint2.timesPow2(i3);
        }
        while (length > 0) {
            length--;
            int i5 = generateCompactWindowNaf[length];
            int i6 = i5 >> 16;
            infinity = infinity.twicePlus((i6 < 0 ? preCompNeg : preComp)[java.lang.Math.abs(i6) >>> 1]).timesPow2(i5 & 65535);
        }
        return infinity;
    }
}
