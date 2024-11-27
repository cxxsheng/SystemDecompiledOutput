package com.android.internal.org.bouncycastle.math.ec;

/* loaded from: classes4.dex */
public abstract class WNafUtil {
    private static final int[] DEFAULT_WINDOW_SIZE_CUTOFFS = {13, 41, 121, 337, com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_APPOP_DENIED_REQUEST_INSTALL_PACKAGES, 2305};
    private static final byte[] EMPTY_BYTES = new byte[0];
    private static final int[] EMPTY_INTS = new int[0];
    private static final com.android.internal.org.bouncycastle.math.ec.ECPoint[] EMPTY_POINTS = new com.android.internal.org.bouncycastle.math.ec.ECPoint[0];
    private static final int MAX_WIDTH = 16;
    public static final java.lang.String PRECOMP_NAME = "bc_wnaf";

    public static void configureBasepoint(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint) {
        com.android.internal.org.bouncycastle.math.ec.ECCurve curve = eCPoint.getCurve();
        if (curve == null) {
            return;
        }
        java.math.BigInteger order = curve.getOrder();
        final int min = java.lang.Math.min(16, getWindowSize(order == null ? curve.getFieldSize() + 1 : order.bitLength()) + 3);
        curve.precompute(eCPoint, PRECOMP_NAME, new com.android.internal.org.bouncycastle.math.ec.PreCompCallback() { // from class: com.android.internal.org.bouncycastle.math.ec.WNafUtil.1
            @Override // com.android.internal.org.bouncycastle.math.ec.PreCompCallback
            public com.android.internal.org.bouncycastle.math.ec.PreCompInfo precompute(com.android.internal.org.bouncycastle.math.ec.PreCompInfo preCompInfo) {
                com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo wNafPreCompInfo = preCompInfo instanceof com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo ? (com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo) preCompInfo : null;
                if (wNafPreCompInfo != null && wNafPreCompInfo.getConfWidth() == min) {
                    wNafPreCompInfo.setPromotionCountdown(0);
                    return wNafPreCompInfo;
                }
                com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo wNafPreCompInfo2 = new com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo();
                wNafPreCompInfo2.setPromotionCountdown(0);
                wNafPreCompInfo2.setConfWidth(min);
                if (wNafPreCompInfo != null) {
                    wNafPreCompInfo2.setPreComp(wNafPreCompInfo.getPreComp());
                    wNafPreCompInfo2.setPreCompNeg(wNafPreCompInfo.getPreCompNeg());
                    wNafPreCompInfo2.setTwice(wNafPreCompInfo.getTwice());
                    wNafPreCompInfo2.setWidth(wNafPreCompInfo.getWidth());
                }
                return wNafPreCompInfo2;
            }
        });
    }

    public static int[] generateCompactNaf(java.math.BigInteger bigInteger) {
        if ((bigInteger.bitLength() >>> 16) != 0) {
            throw new java.lang.IllegalArgumentException("'k' must have bitlength < 2^16");
        }
        if (bigInteger.signum() == 0) {
            return EMPTY_INTS;
        }
        java.math.BigInteger add = bigInteger.shiftLeft(1).add(bigInteger);
        int bitLength = add.bitLength();
        int i = bitLength >> 1;
        int[] iArr = new int[i];
        java.math.BigInteger xor = add.xor(bigInteger);
        int i2 = bitLength - 1;
        int i3 = 0;
        int i4 = 1;
        int i5 = 0;
        while (i4 < i2) {
            if (!xor.testBit(i4)) {
                i5++;
            } else {
                iArr[i3] = i5 | ((bigInteger.testBit(i4) ? -1 : 1) << 16);
                i4++;
                i5 = 1;
                i3++;
            }
            i4++;
        }
        int i6 = i3 + 1;
        iArr[i3] = 65536 | i5;
        if (i > i6) {
            return trim(iArr, i6);
        }
        return iArr;
    }

    public static int[] generateCompactWindowNaf(int i, java.math.BigInteger bigInteger) {
        if (i == 2) {
            return generateCompactNaf(bigInteger);
        }
        if (i < 2 || i > 16) {
            throw new java.lang.IllegalArgumentException("'width' must be in the range [2, 16]");
        }
        if ((bigInteger.bitLength() >>> 16) != 0) {
            throw new java.lang.IllegalArgumentException("'k' must have bitlength < 2^16");
        }
        if (bigInteger.signum() == 0) {
            return EMPTY_INTS;
        }
        int bitLength = (bigInteger.bitLength() / i) + 1;
        int[] iArr = new int[bitLength];
        int i2 = 1 << i;
        int i3 = i2 - 1;
        int i4 = i2 >>> 1;
        int i5 = 0;
        int i6 = 0;
        boolean z = false;
        while (i5 <= bigInteger.bitLength()) {
            if (bigInteger.testBit(i5) == z) {
                i5++;
            } else {
                bigInteger = bigInteger.shiftRight(i5);
                int intValue = bigInteger.intValue() & i3;
                if (z) {
                    intValue++;
                }
                z = (intValue & i4) != 0;
                if (z) {
                    intValue -= i2;
                }
                if (i6 > 0) {
                    i5--;
                }
                iArr[i6] = i5 | (intValue << 16);
                i5 = i;
                i6++;
            }
        }
        if (bitLength > i6) {
            return trim(iArr, i6);
        }
        return iArr;
    }

    public static byte[] generateJSF(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
        int max = java.lang.Math.max(bigInteger.bitLength(), bigInteger2.bitLength()) + 1;
        byte[] bArr = new byte[max];
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if ((i | i2) == 0 && bigInteger.bitLength() <= i3 && bigInteger2.bitLength() <= i3) {
                break;
            }
            int intValue = ((bigInteger.intValue() >>> i3) + i) & 7;
            int intValue2 = ((bigInteger2.intValue() >>> i3) + i2) & 7;
            int i5 = intValue & 1;
            if (i5 != 0) {
                i5 -= intValue & 2;
                if (intValue + i5 == 4 && (intValue2 & 3) == 2) {
                    i5 = -i5;
                }
            }
            int i6 = intValue2 & 1;
            if (i6 != 0) {
                i6 -= intValue2 & 2;
                if (intValue2 + i6 == 4 && (intValue & 3) == 2) {
                    i6 = -i6;
                }
            }
            if ((i << 1) == i5 + 1) {
                i ^= 1;
            }
            if ((i2 << 1) == i6 + 1) {
                i2 ^= 1;
            }
            i3++;
            if (i3 == 30) {
                bigInteger = bigInteger.shiftRight(30);
                bigInteger2 = bigInteger2.shiftRight(30);
                i3 = 0;
            }
            bArr[i4] = (byte) ((i5 << 4) | (i6 & 15));
            i4++;
        }
        if (max > i4) {
            return trim(bArr, i4);
        }
        return bArr;
    }

    public static byte[] generateNaf(java.math.BigInteger bigInteger) {
        if (bigInteger.signum() == 0) {
            return EMPTY_BYTES;
        }
        java.math.BigInteger add = bigInteger.shiftLeft(1).add(bigInteger);
        int bitLength = add.bitLength() - 1;
        byte[] bArr = new byte[bitLength];
        java.math.BigInteger xor = add.xor(bigInteger);
        int i = 1;
        while (i < bitLength) {
            if (xor.testBit(i)) {
                bArr[i - 1] = (byte) (bigInteger.testBit(i) ? -1 : 1);
                i++;
            }
            i++;
        }
        bArr[bitLength - 1] = 1;
        return bArr;
    }

    public static byte[] generateWindowNaf(int i, java.math.BigInteger bigInteger) {
        if (i == 2) {
            return generateNaf(bigInteger);
        }
        if (i < 2 || i > 8) {
            throw new java.lang.IllegalArgumentException("'width' must be in the range [2, 8]");
        }
        if (bigInteger.signum() == 0) {
            return EMPTY_BYTES;
        }
        int bitLength = bigInteger.bitLength() + 1;
        byte[] bArr = new byte[bitLength];
        int i2 = 1 << i;
        int i3 = i2 - 1;
        int i4 = i2 >>> 1;
        int i5 = 0;
        int i6 = 0;
        boolean z = false;
        while (i5 <= bigInteger.bitLength()) {
            if (bigInteger.testBit(i5) == z) {
                i5++;
            } else {
                bigInteger = bigInteger.shiftRight(i5);
                int intValue = bigInteger.intValue() & i3;
                if (z) {
                    intValue++;
                }
                z = (intValue & i4) != 0;
                if (z) {
                    intValue -= i2;
                }
                if (i6 > 0) {
                    i5--;
                }
                int i7 = i6 + i5;
                bArr[i7] = (byte) intValue;
                i6 = i7 + 1;
                i5 = i;
            }
        }
        if (bitLength > i6) {
            return trim(bArr, i6);
        }
        return bArr;
    }

    public static int getNafWeight(java.math.BigInteger bigInteger) {
        if (bigInteger.signum() == 0) {
            return 0;
        }
        return bigInteger.shiftLeft(1).add(bigInteger).xor(bigInteger).bitCount();
    }

    public static com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo getWNafPreCompInfo(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint) {
        return getWNafPreCompInfo(eCPoint.getCurve().getPreCompInfo(eCPoint, PRECOMP_NAME));
    }

    public static com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo getWNafPreCompInfo(com.android.internal.org.bouncycastle.math.ec.PreCompInfo preCompInfo) {
        if (preCompInfo instanceof com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo) {
            return (com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo) preCompInfo;
        }
        return null;
    }

    public static int getWindowSize(int i) {
        return getWindowSize(i, DEFAULT_WINDOW_SIZE_CUTOFFS, 16);
    }

    public static int getWindowSize(int i, int i2) {
        return getWindowSize(i, DEFAULT_WINDOW_SIZE_CUTOFFS, i2);
    }

    public static int getWindowSize(int i, int[] iArr) {
        return getWindowSize(i, iArr, 16);
    }

    public static int getWindowSize(int i, int[] iArr, int i2) {
        int i3 = 0;
        while (i3 < iArr.length && i >= iArr[i3]) {
            i3++;
        }
        return java.lang.Math.max(2, java.lang.Math.min(i2, i3 + 2));
    }

    public static com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo precompute(final com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, final int i, final boolean z) {
        final com.android.internal.org.bouncycastle.math.ec.ECCurve curve = eCPoint.getCurve();
        return (com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo) curve.precompute(eCPoint, PRECOMP_NAME, new com.android.internal.org.bouncycastle.math.ec.PreCompCallback() { // from class: com.android.internal.org.bouncycastle.math.ec.WNafUtil.2
            @Override // com.android.internal.org.bouncycastle.math.ec.PreCompCallback
            public com.android.internal.org.bouncycastle.math.ec.PreCompInfo precompute(com.android.internal.org.bouncycastle.math.ec.PreCompInfo preCompInfo) {
                com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint2;
                com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr;
                com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr2;
                int length;
                int i2;
                com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint3;
                com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement = null;
                com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo wNafPreCompInfo = preCompInfo instanceof com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo ? (com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo) preCompInfo : null;
                int max = java.lang.Math.max(2, java.lang.Math.min(16, i));
                if (checkExisting(wNafPreCompInfo, max, 1 << (max - 2), z)) {
                    wNafPreCompInfo.decrementPromotionCountdown();
                    return wNafPreCompInfo;
                }
                com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo wNafPreCompInfo2 = new com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo();
                if (wNafPreCompInfo == null) {
                    eCPoint2 = null;
                    eCPointArr = null;
                    eCPointArr2 = null;
                } else {
                    wNafPreCompInfo2.setPromotionCountdown(wNafPreCompInfo.decrementPromotionCountdown());
                    wNafPreCompInfo2.setConfWidth(wNafPreCompInfo.getConfWidth());
                    eCPointArr = wNafPreCompInfo.getPreComp();
                    eCPointArr2 = wNafPreCompInfo.getPreCompNeg();
                    eCPoint2 = wNafPreCompInfo.getTwice();
                }
                int min = java.lang.Math.min(16, java.lang.Math.max(wNafPreCompInfo2.getConfWidth(), max));
                int i3 = 1 << (min - 2);
                int i4 = 0;
                if (eCPointArr == null) {
                    eCPointArr = com.android.internal.org.bouncycastle.math.ec.WNafUtil.EMPTY_POINTS;
                    length = 0;
                } else {
                    length = eCPointArr.length;
                }
                if (length < i3) {
                    eCPointArr = com.android.internal.org.bouncycastle.math.ec.WNafUtil.resizeTable(eCPointArr, i3);
                    if (i3 == 1) {
                        eCPointArr[0] = eCPoint.normalize();
                    } else {
                        if (length == 0) {
                            eCPointArr[0] = eCPoint;
                            i2 = 1;
                        } else {
                            i2 = length;
                        }
                        if (i3 == 2) {
                            eCPointArr[1] = eCPoint.threeTimes();
                        } else {
                            com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint4 = eCPointArr[i2 - 1];
                            if (eCPoint2 != null) {
                                eCPoint3 = eCPoint2;
                            } else {
                                eCPoint2 = eCPointArr[0].twice();
                                if (!eCPoint2.isInfinity() && com.android.internal.org.bouncycastle.math.ec.ECAlgorithms.isFpCurve(curve) && curve.getFieldSize() >= 64) {
                                    switch (curve.getCoordinateSystem()) {
                                        case 2:
                                        case 3:
                                        case 4:
                                            eCFieldElement = eCPoint2.getZCoord(0);
                                            eCPoint3 = curve.createPoint(eCPoint2.getXCoord().toBigInteger(), eCPoint2.getYCoord().toBigInteger());
                                            com.android.internal.org.bouncycastle.math.ec.ECFieldElement square = eCFieldElement.square();
                                            eCPoint4 = eCPoint4.scaleX(square).scaleY(square.multiply(eCFieldElement));
                                            if (length == 0) {
                                                eCPointArr[0] = eCPoint4;
                                                break;
                                            }
                                            break;
                                    }
                                }
                                eCPoint3 = eCPoint2;
                            }
                            while (i2 < i3) {
                                eCPoint4 = eCPoint4.add(eCPoint3);
                                eCPointArr[i2] = eCPoint4;
                                i2++;
                            }
                        }
                        curve.normalizeAll(eCPointArr, length, i3 - length, eCFieldElement);
                    }
                }
                if (z) {
                    if (eCPointArr2 == null) {
                        eCPointArr2 = new com.android.internal.org.bouncycastle.math.ec.ECPoint[i3];
                    } else {
                        i4 = eCPointArr2.length;
                        if (i4 < i3) {
                            eCPointArr2 = com.android.internal.org.bouncycastle.math.ec.WNafUtil.resizeTable(eCPointArr2, i3);
                        }
                    }
                    while (i4 < i3) {
                        eCPointArr2[i4] = eCPointArr[i4].negate();
                        i4++;
                    }
                }
                wNafPreCompInfo2.setPreComp(eCPointArr);
                wNafPreCompInfo2.setPreCompNeg(eCPointArr2);
                wNafPreCompInfo2.setTwice(eCPoint2);
                wNafPreCompInfo2.setWidth(min);
                return wNafPreCompInfo2;
            }

            private boolean checkExisting(com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo wNafPreCompInfo, int i2, int i3, boolean z2) {
                return wNafPreCompInfo != null && wNafPreCompInfo.getWidth() >= java.lang.Math.max(wNafPreCompInfo.getConfWidth(), i2) && checkTable(wNafPreCompInfo.getPreComp(), i3) && (!z2 || checkTable(wNafPreCompInfo.getPreCompNeg(), i3));
            }

            private boolean checkTable(com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr, int i2) {
                return eCPointArr != null && eCPointArr.length >= i2;
            }
        });
    }

    public static com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo precomputeWithPointMap(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, final com.android.internal.org.bouncycastle.math.ec.ECPointMap eCPointMap, final com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo wNafPreCompInfo, final boolean z) {
        return (com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo) eCPoint.getCurve().precompute(eCPoint, PRECOMP_NAME, new com.android.internal.org.bouncycastle.math.ec.PreCompCallback() { // from class: com.android.internal.org.bouncycastle.math.ec.WNafUtil.3
            @Override // com.android.internal.org.bouncycastle.math.ec.PreCompCallback
            public com.android.internal.org.bouncycastle.math.ec.PreCompInfo precompute(com.android.internal.org.bouncycastle.math.ec.PreCompInfo preCompInfo) {
                com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo wNafPreCompInfo2 = preCompInfo instanceof com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo ? (com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo) preCompInfo : null;
                int width = com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo.this.getWidth();
                if (checkExisting(wNafPreCompInfo2, width, com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo.this.getPreComp().length, z)) {
                    wNafPreCompInfo2.decrementPromotionCountdown();
                    return wNafPreCompInfo2;
                }
                com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo wNafPreCompInfo3 = new com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo();
                wNafPreCompInfo3.setPromotionCountdown(com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo.this.getPromotionCountdown());
                com.android.internal.org.bouncycastle.math.ec.ECPoint twice = com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo.this.getTwice();
                if (twice != null) {
                    wNafPreCompInfo3.setTwice(eCPointMap.map(twice));
                }
                com.android.internal.org.bouncycastle.math.ec.ECPoint[] preComp = com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo.this.getPreComp();
                int length = preComp.length;
                com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr = new com.android.internal.org.bouncycastle.math.ec.ECPoint[length];
                for (int i = 0; i < preComp.length; i++) {
                    eCPointArr[i] = eCPointMap.map(preComp[i]);
                }
                wNafPreCompInfo3.setPreComp(eCPointArr);
                wNafPreCompInfo3.setWidth(width);
                if (z) {
                    com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr2 = new com.android.internal.org.bouncycastle.math.ec.ECPoint[length];
                    for (int i2 = 0; i2 < length; i2++) {
                        eCPointArr2[i2] = eCPointArr[i2].negate();
                    }
                    wNafPreCompInfo3.setPreCompNeg(eCPointArr2);
                }
                return wNafPreCompInfo3;
            }

            private boolean checkExisting(com.android.internal.org.bouncycastle.math.ec.WNafPreCompInfo wNafPreCompInfo2, int i, int i2, boolean z2) {
                return wNafPreCompInfo2 != null && wNafPreCompInfo2.getWidth() >= i && checkTable(wNafPreCompInfo2.getPreComp(), i2) && (!z2 || checkTable(wNafPreCompInfo2.getPreCompNeg(), i2));
            }

            private boolean checkTable(com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr, int i) {
                return eCPointArr != null && eCPointArr.length >= i;
            }
        });
    }

    private static byte[] trim(byte[] bArr, int i) {
        byte[] bArr2 = new byte[i];
        java.lang.System.arraycopy(bArr, 0, bArr2, 0, i);
        return bArr2;
    }

    private static int[] trim(int[] iArr, int i) {
        int[] iArr2 = new int[i];
        java.lang.System.arraycopy(iArr, 0, iArr2, 0, i);
        return iArr2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.internal.org.bouncycastle.math.ec.ECPoint[] resizeTable(com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr, int i) {
        com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr2 = new com.android.internal.org.bouncycastle.math.ec.ECPoint[i];
        java.lang.System.arraycopy(eCPointArr, 0, eCPointArr2, 0, eCPointArr.length);
        return eCPointArr2;
    }
}
