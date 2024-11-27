package com.android.internal.org.bouncycastle.math.ec;

/* loaded from: classes4.dex */
public class ECAlgorithms {
    public static boolean isF2mCurve(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve) {
        return isF2mField(eCCurve.getField());
    }

    public static boolean isF2mField(com.android.internal.org.bouncycastle.math.field.FiniteField finiteField) {
        return finiteField.getDimension() > 1 && finiteField.getCharacteristic().equals(com.android.internal.org.bouncycastle.math.ec.ECConstants.TWO) && (finiteField instanceof com.android.internal.org.bouncycastle.math.field.PolynomialExtensionField);
    }

    public static boolean isFpCurve(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve) {
        return isFpField(eCCurve.getField());
    }

    public static boolean isFpField(com.android.internal.org.bouncycastle.math.field.FiniteField finiteField) {
        return finiteField.getDimension() == 1;
    }

    public static com.android.internal.org.bouncycastle.math.ec.ECPoint sumOfMultiplies(com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr, java.math.BigInteger[] bigIntegerArr) {
        if (eCPointArr != null && bigIntegerArr != null && eCPointArr.length == bigIntegerArr.length) {
            if (eCPointArr.length >= 1) {
                int length = eCPointArr.length;
                switch (length) {
                    case 1:
                        return eCPointArr[0].multiply(bigIntegerArr[0]);
                    case 2:
                        return sumOfTwoMultiplies(eCPointArr[0], bigIntegerArr[0], eCPointArr[1], bigIntegerArr[1]);
                    default:
                        com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint = eCPointArr[0];
                        com.android.internal.org.bouncycastle.math.ec.ECCurve curve = eCPoint.getCurve();
                        com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr2 = new com.android.internal.org.bouncycastle.math.ec.ECPoint[length];
                        eCPointArr2[0] = eCPoint;
                        for (int i = 1; i < length; i++) {
                            eCPointArr2[i] = importPoint(curve, eCPointArr[i]);
                        }
                        com.android.internal.org.bouncycastle.math.ec.endo.ECEndomorphism endomorphism = curve.getEndomorphism();
                        if (endomorphism instanceof com.android.internal.org.bouncycastle.math.ec.endo.GLVEndomorphism) {
                            return implCheckResult(implSumOfMultipliesGLV(eCPointArr2, bigIntegerArr, (com.android.internal.org.bouncycastle.math.ec.endo.GLVEndomorphism) endomorphism));
                        }
                        return implCheckResult(implSumOfMultiplies(eCPointArr2, bigIntegerArr));
                }
            }
        }
        throw new java.lang.IllegalArgumentException("point and scalar arrays should be non-null, and of equal, non-zero, length");
    }

    public static com.android.internal.org.bouncycastle.math.ec.ECPoint sumOfTwoMultiplies(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, java.math.BigInteger bigInteger, com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint2, java.math.BigInteger bigInteger2) {
        com.android.internal.org.bouncycastle.math.ec.ECCurve curve = eCPoint.getCurve();
        com.android.internal.org.bouncycastle.math.ec.ECPoint importPoint = importPoint(curve, eCPoint2);
        if ((curve instanceof com.android.internal.org.bouncycastle.math.ec.ECCurve.AbstractF2m) && ((com.android.internal.org.bouncycastle.math.ec.ECCurve.AbstractF2m) curve).isKoblitz()) {
            return implCheckResult(eCPoint.multiply(bigInteger).add(importPoint.multiply(bigInteger2)));
        }
        com.android.internal.org.bouncycastle.math.ec.endo.ECEndomorphism endomorphism = curve.getEndomorphism();
        if (endomorphism instanceof com.android.internal.org.bouncycastle.math.ec.endo.GLVEndomorphism) {
            return implCheckResult(implSumOfMultipliesGLV(new com.android.internal.org.bouncycastle.math.ec.ECPoint[]{eCPoint, importPoint}, new java.math.BigInteger[]{bigInteger, bigInteger2}, (com.android.internal.org.bouncycastle.math.ec.endo.GLVEndomorphism) endomorphism));
        }
        return implCheckResult(implShamirsTrickWNaf(eCPoint, bigInteger, importPoint, bigInteger2));
    }

    public static com.android.internal.org.bouncycastle.math.ec.ECPoint shamirsTrick(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, java.math.BigInteger bigInteger, com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint2, java.math.BigInteger bigInteger2) {
        return implCheckResult(implShamirsTrickJsf(eCPoint, bigInteger, importPoint(eCPoint.getCurve(), eCPoint2), bigInteger2));
    }

    public static com.android.internal.org.bouncycastle.math.ec.ECPoint importPoint(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint) {
        if (!eCCurve.equals(eCPoint.getCurve())) {
            throw new java.lang.IllegalArgumentException("Point must be on the same curve");
        }
        return eCCurve.importPoint(eCPoint);
    }

    public static void montgomeryTrick(com.android.internal.org.bouncycastle.math.ec.ECFieldElement[] eCFieldElementArr, int i, int i2) {
        montgomeryTrick(eCFieldElementArr, i, i2, null);
    }

    public static void montgomeryTrick(com.android.internal.org.bouncycastle.math.ec.ECFieldElement[] eCFieldElementArr, int i, int i2, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
        com.android.internal.org.bouncycastle.math.ec.ECFieldElement[] eCFieldElementArr2 = new com.android.internal.org.bouncycastle.math.ec.ECFieldElement[i2];
        int i3 = 0;
        eCFieldElementArr2[0] = eCFieldElementArr[i];
        while (true) {
            i3++;
            if (i3 >= i2) {
                break;
            } else {
                eCFieldElementArr2[i3] = eCFieldElementArr2[i3 - 1].multiply(eCFieldElementArr[i + i3]);
            }
        }
        int i4 = i3 - 1;
        if (eCFieldElement != null) {
            eCFieldElementArr2[i4] = eCFieldElementArr2[i4].multiply(eCFieldElement);
        }
        com.android.internal.org.bouncycastle.math.ec.ECFieldElement invert = eCFieldElementArr2[i4].invert();
        while (i4 > 0) {
            int i5 = i4 - 1;
            int i6 = i4 + i;
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2 = eCFieldElementArr[i6];
            eCFieldElementArr[i6] = eCFieldElementArr2[i5].multiply(invert);
            invert = invert.multiply(eCFieldElement2);
            i4 = i5;
        }
        eCFieldElementArr[i] = invert;
    }

    public static com.android.internal.org.bouncycastle.math.ec.ECPoint referenceMultiply(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, java.math.BigInteger bigInteger) {
        java.math.BigInteger abs = bigInteger.abs();
        com.android.internal.org.bouncycastle.math.ec.ECPoint infinity = eCPoint.getCurve().getInfinity();
        int bitLength = abs.bitLength();
        if (bitLength > 0) {
            if (abs.testBit(0)) {
                infinity = eCPoint;
            }
            for (int i = 1; i < bitLength; i++) {
                eCPoint = eCPoint.twice();
                if (abs.testBit(i)) {
                    infinity = infinity.add(eCPoint);
                }
            }
        }
        return bigInteger.signum() < 0 ? infinity.negate() : infinity;
    }

    public static com.android.internal.org.bouncycastle.math.ec.ECPoint validatePoint(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint) {
        if (!eCPoint.isValid()) {
            throw new java.lang.IllegalStateException("Invalid point");
        }
        return eCPoint;
    }

    public static com.android.internal.org.bouncycastle.math.ec.ECPoint cleanPoint(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint) {
        if (!eCCurve.equals(eCPoint.getCurve())) {
            throw new java.lang.IllegalArgumentException("Point must be on the same curve");
        }
        return eCCurve.decodePoint(eCPoint.getEncoded(false));
    }

    static com.android.internal.org.bouncycastle.math.ec.ECPoint implCheckResult(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint) {
        if (!eCPoint.isValidPartial()) {
            throw new java.lang.IllegalStateException("Invalid result");
        }
        return eCPoint;
    }

    static com.android.internal.org.bouncycastle.math.ec.ECPoint implShamirsTrickJsf(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, java.math.BigInteger bigInteger, com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint2, java.math.BigInteger bigInteger2) {
        com.android.internal.org.bouncycastle.math.ec.ECCurve curve = eCPoint.getCurve();
        com.android.internal.org.bouncycastle.math.ec.ECPoint infinity = curve.getInfinity();
        com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr = {eCPoint2, eCPoint.subtract(eCPoint2), eCPoint, eCPoint.add(eCPoint2)};
        curve.normalizeAll(eCPointArr);
        com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr2 = {eCPointArr[3].negate(), eCPointArr[2].negate(), eCPointArr[1].negate(), eCPointArr[0].negate(), infinity, eCPointArr[0], eCPointArr[1], eCPointArr[2], eCPointArr[3]};
        byte[] generateJSF = com.android.internal.org.bouncycastle.math.ec.WNafUtil.generateJSF(bigInteger, bigInteger2);
        int length = generateJSF.length;
        while (true) {
            length--;
            if (length >= 0) {
                byte b = generateJSF[length];
                infinity = infinity.twicePlus(eCPointArr2[(((b << android.hardware.biometrics.face.AcquiredInfo.FIRST_FRAME_RECEIVED) >> 28) * 3) + 4 + ((b << 28) >> 28)]);
            } else {
                return infinity;
            }
        }
    }

    static com.android.internal.org.bouncycastle.math.ec.ECPoint implShamirsTrickWNaf(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, java.math.BigInteger bigInteger, com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint2, java.math.BigInteger bigInteger2) {
        boolean z = bigInteger.signum() < 0;
        boolean z2 = bigInteger2.signum() < 0;
        java.math.BigInteger abs = bigInteger.abs();
        java.math.BigInteger abs2 = bigInteger2.abs();
        int windowSize = com.android.internal.org.bouncycastle.math.ec.WNafUtil.getWindowSize(abs.bitLength(), 8);
        int windowSize2 = com.android.internal.org.bouncycastle.math.ec.WNafUtil.getWindowSize(abs2.bitLength(), 8);
        com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo precompute = com.android.internal.org.bouncycastle.math.ec.WNafUtil.precompute(eCPoint, windowSize, true);
        com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo precompute2 = com.android.internal.org.bouncycastle.math.ec.WNafUtil.precompute(eCPoint2, windowSize2, true);
        int combSize = com.android.internal.org.bouncycastle.math.ec.FixedPointUtil.getCombSize(eCPoint.getCurve());
        if (!z && !z2 && bigInteger.bitLength() <= combSize && bigInteger2.bitLength() <= combSize && precompute.isPromoted() && precompute2.isPromoted()) {
            return implShamirsTrickFixedPoint(eCPoint, bigInteger, eCPoint2, bigInteger2);
        }
        int min = java.lang.Math.min(8, precompute.getWidth());
        int min2 = java.lang.Math.min(8, precompute2.getWidth());
        return implShamirsTrickWNaf(z ? precompute.getPreCompNeg() : precompute.getPreComp(), z ? precompute.getPreComp() : precompute.getPreCompNeg(), com.android.internal.org.bouncycastle.math.ec.WNafUtil.generateWindowNaf(min, abs), z2 ? precompute2.getPreCompNeg() : precompute2.getPreComp(), z2 ? precompute2.getPreComp() : precompute2.getPreCompNeg(), com.android.internal.org.bouncycastle.math.ec.WNafUtil.generateWindowNaf(min2, abs2));
    }

    static com.android.internal.org.bouncycastle.math.ec.ECPoint implShamirsTrickWNaf(com.android.internal.org.bouncycastle.math.ec.endo.ECEndomorphism eCEndomorphism, com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
        boolean z = bigInteger.signum() < 0;
        boolean z2 = bigInteger2.signum() < 0;
        java.math.BigInteger abs = bigInteger.abs();
        java.math.BigInteger abs2 = bigInteger2.abs();
        com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo precompute = com.android.internal.org.bouncycastle.math.ec.WNafUtil.precompute(eCPoint, com.android.internal.org.bouncycastle.math.ec.WNafUtil.getWindowSize(java.lang.Math.max(abs.bitLength(), abs2.bitLength()), 8), true);
        com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo precomputeWithPointMap = com.android.internal.org.bouncycastle.math.ec.WNafUtil.precomputeWithPointMap(com.android.internal.org.bouncycastle.math.ec.endo.EndoUtil.mapPoint(eCEndomorphism, eCPoint), eCEndomorphism.getPointMap(), precompute, true);
        int min = java.lang.Math.min(8, precompute.getWidth());
        int min2 = java.lang.Math.min(8, precomputeWithPointMap.getWidth());
        return implShamirsTrickWNaf(z ? precompute.getPreCompNeg() : precompute.getPreComp(), z ? precompute.getPreComp() : precompute.getPreCompNeg(), com.android.internal.org.bouncycastle.math.ec.WNafUtil.generateWindowNaf(min, abs), z2 ? precomputeWithPointMap.getPreCompNeg() : precomputeWithPointMap.getPreComp(), z2 ? precomputeWithPointMap.getPreComp() : precomputeWithPointMap.getPreCompNeg(), com.android.internal.org.bouncycastle.math.ec.WNafUtil.generateWindowNaf(min2, abs2));
    }

    private static com.android.internal.org.bouncycastle.math.ec.ECPoint implShamirsTrickWNaf(com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr, com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr2, byte[] bArr, com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr3, com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr4, byte[] bArr2) {
        com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint;
        int max = java.lang.Math.max(bArr.length, bArr2.length);
        com.android.internal.org.bouncycastle.math.ec.ECPoint infinity = eCPointArr[0].getCurve().getInfinity();
        int i = max - 1;
        int i2 = 0;
        com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint2 = infinity;
        while (i >= 0) {
            byte b = i < bArr.length ? bArr[i] : (byte) 0;
            byte b2 = i < bArr2.length ? bArr2[i] : (byte) 0;
            if ((b | b2) == 0) {
                i2++;
            } else {
                if (b == 0) {
                    eCPoint = infinity;
                } else {
                    eCPoint = infinity.add((b < 0 ? eCPointArr2 : eCPointArr)[java.lang.Math.abs((int) b) >>> 1]);
                }
                if (b2 != 0) {
                    eCPoint = eCPoint.add((b2 < 0 ? eCPointArr4 : eCPointArr3)[java.lang.Math.abs((int) b2) >>> 1]);
                }
                if (i2 > 0) {
                    eCPoint2 = eCPoint2.timesPow2(i2);
                    i2 = 0;
                }
                eCPoint2 = eCPoint2.twicePlus(eCPoint);
            }
            i--;
        }
        if (i2 > 0) {
            return eCPoint2.timesPow2(i2);
        }
        return eCPoint2;
    }

    static com.android.internal.org.bouncycastle.math.ec.ECPoint implSumOfMultiplies(com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr, java.math.BigInteger[] bigIntegerArr) {
        int length = eCPointArr.length;
        boolean[] zArr = new boolean[length];
        com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo[] wNafPreCompInfoArr = new com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo[length];
        byte[][] bArr = new byte[length][];
        for (int i = 0; i < length; i++) {
            java.math.BigInteger bigInteger = bigIntegerArr[i];
            zArr[i] = bigInteger.signum() < 0;
            java.math.BigInteger abs = bigInteger.abs();
            com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo precompute = com.android.internal.org.bouncycastle.math.ec.WNafUtil.precompute(eCPointArr[i], com.android.internal.org.bouncycastle.math.ec.WNafUtil.getWindowSize(abs.bitLength(), 8), true);
            int min = java.lang.Math.min(8, precompute.getWidth());
            wNafPreCompInfoArr[i] = precompute;
            bArr[i] = com.android.internal.org.bouncycastle.math.ec.WNafUtil.generateWindowNaf(min, abs);
        }
        return implSumOfMultiplies(zArr, wNafPreCompInfoArr, bArr);
    }

    static com.android.internal.org.bouncycastle.math.ec.ECPoint implSumOfMultipliesGLV(com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr, java.math.BigInteger[] bigIntegerArr, com.android.internal.org.bouncycastle.math.ec.endo.GLVEndomorphism gLVEndomorphism) {
        java.math.BigInteger order = eCPointArr[0].getCurve().getOrder();
        int length = eCPointArr.length;
        int i = length << 1;
        java.math.BigInteger[] bigIntegerArr2 = new java.math.BigInteger[i];
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            java.math.BigInteger[] decomposeScalar = gLVEndomorphism.decomposeScalar(bigIntegerArr[i3].mod(order));
            int i4 = i2 + 1;
            bigIntegerArr2[i2] = decomposeScalar[0];
            i2 = i4 + 1;
            bigIntegerArr2[i4] = decomposeScalar[1];
        }
        if (gLVEndomorphism.hasEfficientPointMap()) {
            return implSumOfMultiplies(gLVEndomorphism, eCPointArr, bigIntegerArr2);
        }
        com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr2 = new com.android.internal.org.bouncycastle.math.ec.ECPoint[i];
        int i5 = 0;
        for (com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint : eCPointArr) {
            com.android.internal.org.bouncycastle.math.ec.ECPoint mapPoint = com.android.internal.org.bouncycastle.math.ec.endo.EndoUtil.mapPoint(gLVEndomorphism, eCPoint);
            int i6 = i5 + 1;
            eCPointArr2[i5] = eCPoint;
            i5 = i6 + 1;
            eCPointArr2[i6] = mapPoint;
        }
        return implSumOfMultiplies(eCPointArr2, bigIntegerArr2);
    }

    static com.android.internal.org.bouncycastle.math.ec.ECPoint implSumOfMultiplies(com.android.internal.org.bouncycastle.math.ec.endo.ECEndomorphism eCEndomorphism, com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr, java.math.BigInteger[] bigIntegerArr) {
        com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr2 = eCPointArr;
        int length = eCPointArr2.length;
        int i = length << 1;
        boolean[] zArr = new boolean[i];
        com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo[] wNafPreCompInfoArr = new com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo[i];
        byte[][] bArr = new byte[i][];
        com.android.internal.org.bouncycastle.math.ec.ECPointMap pointMap = eCEndomorphism.getPointMap();
        int i2 = 0;
        while (i2 < length) {
            int i3 = i2 << 1;
            int i4 = i3 + 1;
            java.math.BigInteger bigInteger = bigIntegerArr[i3];
            zArr[i3] = bigInteger.signum() < 0;
            java.math.BigInteger abs = bigInteger.abs();
            java.math.BigInteger bigInteger2 = bigIntegerArr[i4];
            zArr[i4] = bigInteger2.signum() < 0;
            java.math.BigInteger abs2 = bigInteger2.abs();
            int windowSize = com.android.internal.org.bouncycastle.math.ec.WNafUtil.getWindowSize(java.lang.Math.max(abs.bitLength(), abs2.bitLength()), 8);
            com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint = eCPointArr2[i2];
            com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo precompute = com.android.internal.org.bouncycastle.math.ec.WNafUtil.precompute(eCPoint, windowSize, true);
            com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo precomputeWithPointMap = com.android.internal.org.bouncycastle.math.ec.WNafUtil.precomputeWithPointMap(com.android.internal.org.bouncycastle.math.ec.endo.EndoUtil.mapPoint(eCEndomorphism, eCPoint), pointMap, precompute, true);
            int min = java.lang.Math.min(8, precompute.getWidth());
            int min2 = java.lang.Math.min(8, precomputeWithPointMap.getWidth());
            wNafPreCompInfoArr[i3] = precompute;
            wNafPreCompInfoArr[i4] = precomputeWithPointMap;
            bArr[i3] = com.android.internal.org.bouncycastle.math.ec.WNafUtil.generateWindowNaf(min, abs);
            bArr[i4] = com.android.internal.org.bouncycastle.math.ec.WNafUtil.generateWindowNaf(min2, abs2);
            i2++;
            eCPointArr2 = eCPointArr;
        }
        return implSumOfMultiplies(zArr, wNafPreCompInfoArr, bArr);
    }

    private static com.android.internal.org.bouncycastle.math.ec.ECPoint implSumOfMultiplies(boolean[] zArr, com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo[] wNafPreCompInfoArr, byte[][] bArr) {
        boolean z;
        int length = bArr.length;
        int i = 0;
        for (byte[] bArr2 : bArr) {
            i = java.lang.Math.max(i, bArr2.length);
        }
        com.android.internal.org.bouncycastle.math.ec.ECPoint infinity = wNafPreCompInfoArr[0].getPreComp()[0].getCurve().getInfinity();
        int i2 = i - 1;
        int i3 = 0;
        com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint = infinity;
        while (i2 >= 0) {
            com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint2 = infinity;
            for (int i4 = 0; i4 < length; i4++) {
                byte[] bArr3 = bArr[i4];
                byte b = i2 < bArr3.length ? bArr3[i2] : (byte) 0;
                if (b != 0) {
                    int abs = java.lang.Math.abs((int) b);
                    com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo wNafPreCompInfo = wNafPreCompInfoArr[i4];
                    if (b >= 0) {
                        z = false;
                    } else {
                        z = true;
                    }
                    eCPoint2 = eCPoint2.add((z == zArr[i4] ? wNafPreCompInfo.getPreComp() : wNafPreCompInfo.getPreCompNeg())[abs >>> 1]);
                }
            }
            if (eCPoint2 == infinity) {
                i3++;
            } else {
                if (i3 > 0) {
                    eCPoint = eCPoint.timesPow2(i3);
                    i3 = 0;
                }
                eCPoint = eCPoint.twicePlus(eCPoint2);
            }
            i2--;
        }
        if (i3 > 0) {
            return eCPoint.timesPow2(i3);
        }
        return eCPoint;
    }

    private static com.android.internal.org.bouncycastle.math.ec.ECPoint implShamirsTrickFixedPoint(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, java.math.BigInteger bigInteger, com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint2, java.math.BigInteger bigInteger2) {
        com.android.internal.org.bouncycastle.math.ec.ECCurve curve = eCPoint.getCurve();
        int combSize = com.android.internal.org.bouncycastle.math.ec.FixedPointUtil.getCombSize(curve);
        if (bigInteger.bitLength() > combSize || bigInteger2.bitLength() > combSize) {
            throw new java.lang.IllegalStateException("fixed-point comb doesn't support scalars larger than the curve order");
        }
        com.android.internal.org.bouncycastle.math.ec.FixedPointPreCompInfo precompute = com.android.internal.org.bouncycastle.math.ec.FixedPointUtil.precompute(eCPoint);
        com.android.internal.org.bouncycastle.math.ec.FixedPointPreCompInfo precompute2 = com.android.internal.org.bouncycastle.math.ec.FixedPointUtil.precompute(eCPoint2);
        com.android.internal.org.bouncycastle.math.ec.ECLookupTable lookupTable = precompute.getLookupTable();
        com.android.internal.org.bouncycastle.math.ec.ECLookupTable lookupTable2 = precompute2.getLookupTable();
        int width = precompute.getWidth();
        if (width != precompute2.getWidth()) {
            com.android.internal.org.bouncycastle.math.ec.FixedPointCombMultiplier fixedPointCombMultiplier = new com.android.internal.org.bouncycastle.math.ec.FixedPointCombMultiplier();
            return fixedPointCombMultiplier.multiply(eCPoint, bigInteger).add(fixedPointCombMultiplier.multiply(eCPoint2, bigInteger2));
        }
        int i = ((combSize + width) - 1) / width;
        com.android.internal.org.bouncycastle.math.ec.ECPoint infinity = curve.getInfinity();
        int i2 = width * i;
        int[] fromBigInteger = com.android.internal.org.bouncycastle.math.raw.Nat.fromBigInteger(i2, bigInteger);
        int[] fromBigInteger2 = com.android.internal.org.bouncycastle.math.raw.Nat.fromBigInteger(i2, bigInteger2);
        int i3 = i2 - 1;
        for (int i4 = 0; i4 < i; i4++) {
            int i5 = 0;
            int i6 = 0;
            for (int i7 = i3 - i4; i7 >= 0; i7 -= i) {
                int i8 = i7 >>> 5;
                int i9 = i7 & 31;
                int i10 = fromBigInteger[i8] >>> i9;
                i5 = ((i5 ^ (i10 >>> 1)) << 1) ^ i10;
                int i11 = fromBigInteger2[i8] >>> i9;
                i6 = ((i6 ^ (i11 >>> 1)) << 1) ^ i11;
            }
            infinity = infinity.twicePlus(lookupTable.lookupVar(i5).add(lookupTable2.lookupVar(i6)));
        }
        return infinity.add(precompute.getOffset()).add(precompute2.getOffset());
    }
}
