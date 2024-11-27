package com.android.internal.org.bouncycastle.math.ec;

/* loaded from: classes4.dex */
public class FixedPointUtil {
    public static final java.lang.String PRECOMP_NAME = "bc_fixed_point";

    public static int getCombSize(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve) {
        java.math.BigInteger order = eCCurve.getOrder();
        return order == null ? eCCurve.getFieldSize() + 1 : order.bitLength();
    }

    public static com.android.internal.org.bouncycastle.math.ec.FixedPointPreCompInfo getFixedPointPreCompInfo(com.android.internal.org.bouncycastle.math.ec.PreCompInfo preCompInfo) {
        if (preCompInfo instanceof com.android.internal.org.bouncycastle.math.ec.FixedPointPreCompInfo) {
            return (com.android.internal.org.bouncycastle.math.ec.FixedPointPreCompInfo) preCompInfo;
        }
        return null;
    }

    public static com.android.internal.org.bouncycastle.math.ec.FixedPointPreCompInfo precompute(final com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint) {
        final com.android.internal.org.bouncycastle.math.ec.ECCurve curve = eCPoint.getCurve();
        return (com.android.internal.org.bouncycastle.math.ec.FixedPointPreCompInfo) curve.precompute(eCPoint, PRECOMP_NAME, new com.android.internal.org.bouncycastle.math.ec.PreCompCallback() { // from class: com.android.internal.org.bouncycastle.math.ec.FixedPointUtil.1
            @Override // com.android.internal.org.bouncycastle.math.ec.PreCompCallback
            public com.android.internal.org.bouncycastle.math.ec.PreCompInfo precompute(com.android.internal.org.bouncycastle.math.ec.PreCompInfo preCompInfo) {
                com.android.internal.org.bouncycastle.math.ec.FixedPointPreCompInfo fixedPointPreCompInfo = preCompInfo instanceof com.android.internal.org.bouncycastle.math.ec.FixedPointPreCompInfo ? (com.android.internal.org.bouncycastle.math.ec.FixedPointPreCompInfo) preCompInfo : null;
                int combSize = com.android.internal.org.bouncycastle.math.ec.FixedPointUtil.getCombSize(com.android.internal.org.bouncycastle.math.ec.ECCurve.this);
                int i = combSize > 250 ? 6 : 5;
                int i2 = 1 << i;
                if (!checkExisting(fixedPointPreCompInfo, i2)) {
                    int i3 = ((combSize + i) - 1) / i;
                    com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr = new com.android.internal.org.bouncycastle.math.ec.ECPoint[i + 1];
                    eCPointArr[0] = eCPoint;
                    for (int i4 = 1; i4 < i; i4++) {
                        eCPointArr[i4] = eCPointArr[i4 - 1].timesPow2(i3);
                    }
                    eCPointArr[i] = eCPointArr[0].subtract(eCPointArr[1]);
                    com.android.internal.org.bouncycastle.math.ec.ECCurve.this.normalizeAll(eCPointArr);
                    com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr2 = new com.android.internal.org.bouncycastle.math.ec.ECPoint[i2];
                    eCPointArr2[0] = eCPointArr[0];
                    for (int i5 = i - 1; i5 >= 0; i5--) {
                        com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint2 = eCPointArr[i5];
                        int i6 = 1 << i5;
                        for (int i7 = i6; i7 < i2; i7 += i6 << 1) {
                            eCPointArr2[i7] = eCPointArr2[i7 - i6].add(eCPoint2);
                        }
                    }
                    com.android.internal.org.bouncycastle.math.ec.ECCurve.this.normalizeAll(eCPointArr2);
                    com.android.internal.org.bouncycastle.math.ec.FixedPointPreCompInfo fixedPointPreCompInfo2 = new com.android.internal.org.bouncycastle.math.ec.FixedPointPreCompInfo();
                    fixedPointPreCompInfo2.setLookupTable(com.android.internal.org.bouncycastle.math.ec.ECCurve.this.createCacheSafeLookupTable(eCPointArr2, 0, i2));
                    fixedPointPreCompInfo2.setOffset(eCPointArr[i]);
                    fixedPointPreCompInfo2.setWidth(i);
                    return fixedPointPreCompInfo2;
                }
                return fixedPointPreCompInfo;
            }

            private boolean checkExisting(com.android.internal.org.bouncycastle.math.ec.FixedPointPreCompInfo fixedPointPreCompInfo, int i) {
                return fixedPointPreCompInfo != null && checkTable(fixedPointPreCompInfo.getLookupTable(), i);
            }

            private boolean checkTable(com.android.internal.org.bouncycastle.math.ec.ECLookupTable eCLookupTable, int i) {
                return eCLookupTable != null && eCLookupTable.getSize() >= i;
            }
        });
    }
}
