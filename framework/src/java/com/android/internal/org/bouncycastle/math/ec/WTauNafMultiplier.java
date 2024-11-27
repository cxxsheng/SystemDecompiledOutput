package com.android.internal.org.bouncycastle.math.ec;

/* loaded from: classes4.dex */
public class WTauNafMultiplier extends com.android.internal.org.bouncycastle.math.ec.AbstractECMultiplier {
    static final java.lang.String PRECOMP_NAME = "bc_wtnaf";

    @Override // com.android.internal.org.bouncycastle.math.ec.AbstractECMultiplier
    protected com.android.internal.org.bouncycastle.math.ec.ECPoint multiplyPositive(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, java.math.BigInteger bigInteger) {
        if (!(eCPoint instanceof com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m)) {
            throw new java.lang.IllegalArgumentException("Only ECPoint.AbstractF2m can be used in WTauNafMultiplier");
        }
        com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m abstractF2m = (com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m) eCPoint;
        com.android.internal.org.bouncycastle.math.ec.ECCurve.AbstractF2m abstractF2m2 = (com.android.internal.org.bouncycastle.math.ec.ECCurve.AbstractF2m) abstractF2m.getCurve();
        int fieldSize = abstractF2m2.getFieldSize();
        byte byteValue = abstractF2m2.getA().toBigInteger().byteValue();
        byte mu = com.android.internal.org.bouncycastle.math.ec.Tnaf.getMu(byteValue);
        return multiplyWTnaf(abstractF2m, com.android.internal.org.bouncycastle.math.ec.Tnaf.partModReduction(bigInteger, fieldSize, byteValue, abstractF2m2.getSi(), mu, (byte) 10), byteValue, mu);
    }

    private com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m multiplyWTnaf(com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m abstractF2m, com.android.internal.org.bouncycastle.math.ec.ZTauElement zTauElement, byte b, byte b2) {
        com.android.internal.org.bouncycastle.math.ec.ZTauElement[] zTauElementArr = b == 0 ? com.android.internal.org.bouncycastle.math.ec.Tnaf.alpha0 : com.android.internal.org.bouncycastle.math.ec.Tnaf.alpha1;
        return multiplyFromWTnaf(abstractF2m, com.android.internal.org.bouncycastle.math.ec.Tnaf.tauAdicWNaf(b2, zTauElement, (byte) 4, java.math.BigInteger.valueOf(16L), com.android.internal.org.bouncycastle.math.ec.Tnaf.getTw(b2, 4), zTauElementArr));
    }

    private static com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m multiplyFromWTnaf(final com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m abstractF2m, byte[] bArr) {
        com.android.internal.org.bouncycastle.math.ec.ECCurve.AbstractF2m abstractF2m2 = (com.android.internal.org.bouncycastle.math.ec.ECCurve.AbstractF2m) abstractF2m.getCurve();
        final byte byteValue = abstractF2m2.getA().toBigInteger().byteValue();
        com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m[] preComp = ((com.android.internal.org.bouncycastle.math.ec.WTauNafPreCompInfo) abstractF2m2.precompute(abstractF2m, PRECOMP_NAME, new com.android.internal.org.bouncycastle.math.ec.PreCompCallback() { // from class: com.android.internal.org.bouncycastle.math.ec.WTauNafMultiplier.1
            @Override // com.android.internal.org.bouncycastle.math.ec.PreCompCallback
            public com.android.internal.org.bouncycastle.math.ec.PreCompInfo precompute(com.android.internal.org.bouncycastle.math.ec.PreCompInfo preCompInfo) {
                if (preCompInfo instanceof com.android.internal.org.bouncycastle.math.ec.WTauNafPreCompInfo) {
                    return preCompInfo;
                }
                com.android.internal.org.bouncycastle.math.ec.WTauNafPreCompInfo wTauNafPreCompInfo = new com.android.internal.org.bouncycastle.math.ec.WTauNafPreCompInfo();
                wTauNafPreCompInfo.setPreComp(com.android.internal.org.bouncycastle.math.ec.Tnaf.getPreComp(com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m.this, byteValue));
                return wTauNafPreCompInfo;
            }
        })).getPreComp();
        com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m[] abstractF2mArr = new com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m[preComp.length];
        for (int i = 0; i < preComp.length; i++) {
            abstractF2mArr[i] = (com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m) preComp[i].negate();
        }
        com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m abstractF2m3 = (com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m) abstractF2m.getCurve().getInfinity();
        int i2 = 0;
        for (int length = bArr.length - 1; length >= 0; length--) {
            i2++;
            byte b = bArr[length];
            if (b != 0) {
                abstractF2m3 = (com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m) abstractF2m3.tauPow(i2).add(b > 0 ? preComp[b >>> 1] : abstractF2mArr[(-b) >>> 1]);
                i2 = 0;
            }
        }
        if (i2 > 0) {
            return abstractF2m3.tauPow(i2);
        }
        return abstractF2m3;
    }
}
