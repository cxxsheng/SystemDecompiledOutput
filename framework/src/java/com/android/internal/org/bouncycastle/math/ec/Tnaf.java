package com.android.internal.org.bouncycastle.math.ec;

/* loaded from: classes4.dex */
class Tnaf {
    public static final byte POW_2_WIDTH = 16;
    public static final byte WIDTH = 4;
    private static final java.math.BigInteger MINUS_ONE = com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE.negate();
    private static final java.math.BigInteger MINUS_TWO = com.android.internal.org.bouncycastle.math.ec.ECConstants.TWO.negate();
    private static final java.math.BigInteger MINUS_THREE = com.android.internal.org.bouncycastle.math.ec.ECConstants.THREE.negate();
    public static final com.android.internal.org.bouncycastle.math.ec.ZTauElement[] alpha0 = {null, new com.android.internal.org.bouncycastle.math.ec.ZTauElement(com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE, com.android.internal.org.bouncycastle.math.ec.ECConstants.ZERO), null, new com.android.internal.org.bouncycastle.math.ec.ZTauElement(MINUS_THREE, MINUS_ONE), null, new com.android.internal.org.bouncycastle.math.ec.ZTauElement(MINUS_ONE, MINUS_ONE), null, new com.android.internal.org.bouncycastle.math.ec.ZTauElement(com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE, MINUS_ONE), null};
    public static final byte[][] alpha0Tnaf = {null, new byte[]{1}, null, new byte[]{-1, 0, 1}, null, new byte[]{1, 0, 1}, null, new byte[]{-1, 0, 0, 1}};
    public static final com.android.internal.org.bouncycastle.math.ec.ZTauElement[] alpha1 = {null, new com.android.internal.org.bouncycastle.math.ec.ZTauElement(com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE, com.android.internal.org.bouncycastle.math.ec.ECConstants.ZERO), null, new com.android.internal.org.bouncycastle.math.ec.ZTauElement(MINUS_THREE, com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE), null, new com.android.internal.org.bouncycastle.math.ec.ZTauElement(MINUS_ONE, com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE), null, new com.android.internal.org.bouncycastle.math.ec.ZTauElement(com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE, com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE), null};
    public static final byte[][] alpha1Tnaf = {null, new byte[]{1}, null, new byte[]{-1, 0, 1}, null, new byte[]{1, 0, 1}, null, new byte[]{-1, 0, 0, -1}};

    Tnaf() {
    }

    public static java.math.BigInteger norm(byte b, com.android.internal.org.bouncycastle.math.ec.ZTauElement zTauElement) {
        java.math.BigInteger multiply = zTauElement.u.multiply(zTauElement.u);
        java.math.BigInteger multiply2 = zTauElement.u.multiply(zTauElement.v);
        java.math.BigInteger shiftLeft = zTauElement.v.multiply(zTauElement.v).shiftLeft(1);
        if (b == 1) {
            return multiply.add(multiply2).add(shiftLeft);
        }
        if (b == -1) {
            return multiply.subtract(multiply2).add(shiftLeft);
        }
        throw new java.lang.IllegalArgumentException("mu must be 1 or -1");
    }

    public static com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal norm(byte b, com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal simpleBigDecimal, com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal simpleBigDecimal2) {
        com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal multiply = simpleBigDecimal.multiply(simpleBigDecimal);
        com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal multiply2 = simpleBigDecimal.multiply(simpleBigDecimal2);
        com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal shiftLeft = simpleBigDecimal2.multiply(simpleBigDecimal2).shiftLeft(1);
        if (b == 1) {
            return multiply.add(multiply2).add(shiftLeft);
        }
        if (b == -1) {
            return multiply.subtract(multiply2).add(shiftLeft);
        }
        throw new java.lang.IllegalArgumentException("mu must be 1 or -1");
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0068, code lost:
    
        if (r5.compareTo(com.android.internal.org.bouncycastle.math.ec.Tnaf.MINUS_ONE) < 0) goto L26;
     */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x008e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static com.android.internal.org.bouncycastle.math.ec.ZTauElement round(com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal simpleBigDecimal, com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal simpleBigDecimal2, byte b) {
        com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal subtract;
        com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal add;
        com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal subtract2;
        if (simpleBigDecimal2.getScale() != simpleBigDecimal.getScale()) {
            throw new java.lang.IllegalArgumentException("lambda0 and lambda1 do not have same scale");
        }
        int i = -1;
        int i2 = 1;
        if (b != 1 && b != -1) {
            throw new java.lang.IllegalArgumentException("mu must be 1 or -1");
        }
        java.math.BigInteger round = simpleBigDecimal.round();
        java.math.BigInteger round2 = simpleBigDecimal2.round();
        com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal subtract3 = simpleBigDecimal.subtract(round);
        com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal subtract4 = simpleBigDecimal2.subtract(round2);
        com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal add2 = subtract3.add(subtract3);
        if (b == 1) {
            subtract = add2.add(subtract4);
        } else {
            subtract = add2.subtract(subtract4);
        }
        com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal add3 = subtract4.add(subtract4).add(subtract4);
        com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal add4 = add3.add(subtract4);
        if (b == 1) {
            add = subtract3.subtract(add3);
            subtract2 = subtract3.add(add4);
        } else {
            add = subtract3.add(add3);
            subtract2 = subtract3.subtract(add4);
        }
        byte b2 = 0;
        if (subtract.compareTo(com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE) < 0) {
            if (subtract2.compareTo(com.android.internal.org.bouncycastle.math.ec.ECConstants.TWO) < 0) {
                i2 = 0;
                if (subtract.compareTo(MINUS_ONE) >= 0) {
                    if (add.compareTo(com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE) >= 0) {
                        b2 = (byte) (-b);
                        i = i2;
                    }
                } else if (subtract2.compareTo(MINUS_TWO) >= 0) {
                    i = i2;
                } else {
                    b2 = (byte) (-b);
                    i = i2;
                }
                return new com.android.internal.org.bouncycastle.math.ec.ZTauElement(round.add(java.math.BigInteger.valueOf(i)), round2.add(java.math.BigInteger.valueOf(b2)));
            }
            i2 = 0;
            b2 = b;
            if (subtract.compareTo(MINUS_ONE) >= 0) {
            }
            return new com.android.internal.org.bouncycastle.math.ec.ZTauElement(round.add(java.math.BigInteger.valueOf(i)), round2.add(java.math.BigInteger.valueOf(b2)));
        }
    }

    public static com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal approximateDivisionByN(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, java.math.BigInteger bigInteger3, byte b, int i, int i2) {
        java.math.BigInteger multiply = bigInteger2.multiply(bigInteger.shiftRight(((i - r0) - 2) + b));
        java.math.BigInteger add = multiply.add(bigInteger3.multiply(multiply.shiftRight(i)));
        int i3 = (((i + 5) / 2) + i2) - i2;
        java.math.BigInteger shiftRight = add.shiftRight(i3);
        if (add.testBit(i3 - 1)) {
            shiftRight = shiftRight.add(com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE);
        }
        return new com.android.internal.org.bouncycastle.math.ec.SimpleBigDecimal(shiftRight, i2);
    }

    public static byte[] tauAdicNaf(byte b, com.android.internal.org.bouncycastle.math.ec.ZTauElement zTauElement) {
        java.math.BigInteger subtract;
        if (b != 1 && b != -1) {
            throw new java.lang.IllegalArgumentException("mu must be 1 or -1");
        }
        int bitLength = norm(b, zTauElement).bitLength();
        byte[] bArr = new byte[bitLength > 30 ? bitLength + 4 : 34];
        java.math.BigInteger bigInteger = zTauElement.u;
        java.math.BigInteger bigInteger2 = zTauElement.v;
        int i = 0;
        int i2 = 0;
        while (true) {
            if (!bigInteger.equals(com.android.internal.org.bouncycastle.math.ec.ECConstants.ZERO) || !bigInteger2.equals(com.android.internal.org.bouncycastle.math.ec.ECConstants.ZERO)) {
                if (bigInteger.testBit(0)) {
                    bArr[i2] = (byte) com.android.internal.org.bouncycastle.math.ec.ECConstants.TWO.subtract(bigInteger.subtract(bigInteger2.shiftLeft(1)).mod(com.android.internal.org.bouncycastle.math.ec.ECConstants.FOUR)).intValue();
                    if (bArr[i2] == 1) {
                        bigInteger = bigInteger.clearBit(0);
                    } else {
                        bigInteger = bigInteger.add(com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE);
                    }
                    i = i2;
                } else {
                    bArr[i2] = 0;
                }
                java.math.BigInteger shiftRight = bigInteger.shiftRight(1);
                if (b == 1) {
                    subtract = bigInteger2.add(shiftRight);
                } else {
                    subtract = bigInteger2.subtract(shiftRight);
                }
                java.math.BigInteger negate = bigInteger.shiftRight(1).negate();
                i2++;
                bigInteger = subtract;
                bigInteger2 = negate;
            } else {
                int i3 = i + 1;
                byte[] bArr2 = new byte[i3];
                java.lang.System.arraycopy(bArr, 0, bArr2, 0, i3);
                return bArr2;
            }
        }
    }

    public static com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m tau(com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m abstractF2m) {
        return abstractF2m.tau();
    }

    public static byte getMu(com.android.internal.org.bouncycastle.math.ec.ECCurve.AbstractF2m abstractF2m) {
        if (!abstractF2m.isKoblitz()) {
            throw new java.lang.IllegalArgumentException("No Koblitz curve (ABC), TNAF multiplication not possible");
        }
        if (abstractF2m.getA().isZero()) {
            return (byte) -1;
        }
        return (byte) 1;
    }

    public static byte getMu(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
        return (byte) (eCFieldElement.isZero() ? -1 : 1);
    }

    public static byte getMu(int i) {
        return (byte) (i == 0 ? -1 : 1);
    }

    public static java.math.BigInteger[] getLucas(byte b, int i, boolean z) {
        java.math.BigInteger bigInteger;
        java.math.BigInteger bigInteger2;
        java.math.BigInteger negate;
        if (b != 1 && b != -1) {
            throw new java.lang.IllegalArgumentException("mu must be 1 or -1");
        }
        if (z) {
            bigInteger = com.android.internal.org.bouncycastle.math.ec.ECConstants.TWO;
            bigInteger2 = java.math.BigInteger.valueOf(b);
        } else {
            bigInteger = com.android.internal.org.bouncycastle.math.ec.ECConstants.ZERO;
            bigInteger2 = com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE;
        }
        int i2 = 1;
        while (i2 < i) {
            if (b == 1) {
                negate = bigInteger2;
            } else {
                negate = bigInteger2.negate();
            }
            i2++;
            java.math.BigInteger bigInteger3 = bigInteger2;
            bigInteger2 = negate.subtract(bigInteger.shiftLeft(1));
            bigInteger = bigInteger3;
        }
        return new java.math.BigInteger[]{bigInteger, bigInteger2};
    }

    public static java.math.BigInteger getTw(byte b, int i) {
        if (i == 4) {
            if (b == 1) {
                return java.math.BigInteger.valueOf(6L);
            }
            return java.math.BigInteger.valueOf(10L);
        }
        java.math.BigInteger[] lucas = getLucas(b, i, false);
        java.math.BigInteger bit = com.android.internal.org.bouncycastle.math.ec.ECConstants.ZERO.setBit(i);
        return com.android.internal.org.bouncycastle.math.ec.ECConstants.TWO.multiply(lucas[0]).multiply(lucas[1].modInverse(bit)).mod(bit);
    }

    public static java.math.BigInteger[] getSi(com.android.internal.org.bouncycastle.math.ec.ECCurve.AbstractF2m abstractF2m) {
        if (!abstractF2m.isKoblitz()) {
            throw new java.lang.IllegalArgumentException("si is defined for Koblitz curves only");
        }
        int fieldSize = abstractF2m.getFieldSize();
        int intValue = abstractF2m.getA().toBigInteger().intValue();
        byte mu = getMu(intValue);
        int shiftsForCofactor = getShiftsForCofactor(abstractF2m.getCofactor());
        java.math.BigInteger[] lucas = getLucas(mu, (fieldSize + 3) - intValue, false);
        if (mu == 1) {
            lucas[0] = lucas[0].negate();
            lucas[1] = lucas[1].negate();
        }
        return new java.math.BigInteger[]{com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE.add(lucas[1]).shiftRight(shiftsForCofactor), com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE.add(lucas[0]).shiftRight(shiftsForCofactor).negate()};
    }

    public static java.math.BigInteger[] getSi(int i, int i2, java.math.BigInteger bigInteger) {
        byte mu = getMu(i2);
        int shiftsForCofactor = getShiftsForCofactor(bigInteger);
        java.math.BigInteger[] lucas = getLucas(mu, (i + 3) - i2, false);
        if (mu == 1) {
            lucas[0] = lucas[0].negate();
            lucas[1] = lucas[1].negate();
        }
        return new java.math.BigInteger[]{com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE.add(lucas[1]).shiftRight(shiftsForCofactor), com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE.add(lucas[0]).shiftRight(shiftsForCofactor).negate()};
    }

    protected static int getShiftsForCofactor(java.math.BigInteger bigInteger) {
        if (bigInteger != null) {
            if (bigInteger.equals(com.android.internal.org.bouncycastle.math.ec.ECConstants.TWO)) {
                return 1;
            }
            if (bigInteger.equals(com.android.internal.org.bouncycastle.math.ec.ECConstants.FOUR)) {
                return 2;
            }
        }
        throw new java.lang.IllegalArgumentException("h (Cofactor) must be 2 or 4");
    }

    public static com.android.internal.org.bouncycastle.math.ec.ZTauElement partModReduction(java.math.BigInteger bigInteger, int i, byte b, java.math.BigInteger[] bigIntegerArr, byte b2, byte b3) {
        java.math.BigInteger subtract;
        if (b2 == 1) {
            subtract = bigIntegerArr[0].add(bigIntegerArr[1]);
        } else {
            subtract = bigIntegerArr[0].subtract(bigIntegerArr[1]);
        }
        java.math.BigInteger bigInteger2 = getLucas(b2, i, true)[1];
        com.android.internal.org.bouncycastle.math.ec.ZTauElement round = round(approximateDivisionByN(bigInteger, bigIntegerArr[0], bigInteger2, b, i, b3), approximateDivisionByN(bigInteger, bigIntegerArr[1], bigInteger2, b, i, b3), b2);
        return new com.android.internal.org.bouncycastle.math.ec.ZTauElement(bigInteger.subtract(subtract.multiply(round.u)).subtract(java.math.BigInteger.valueOf(2L).multiply(bigIntegerArr[1]).multiply(round.v)), bigIntegerArr[1].multiply(round.u).subtract(bigIntegerArr[0].multiply(round.v)));
    }

    public static com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m multiplyRTnaf(com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m abstractF2m, java.math.BigInteger bigInteger) {
        com.android.internal.org.bouncycastle.math.ec.ECCurve.AbstractF2m abstractF2m2 = (com.android.internal.org.bouncycastle.math.ec.ECCurve.AbstractF2m) abstractF2m.getCurve();
        int fieldSize = abstractF2m2.getFieldSize();
        int intValue = abstractF2m2.getA().toBigInteger().intValue();
        return multiplyTnaf(abstractF2m, partModReduction(bigInteger, fieldSize, (byte) intValue, abstractF2m2.getSi(), getMu(intValue), (byte) 10));
    }

    public static com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m multiplyTnaf(com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m abstractF2m, com.android.internal.org.bouncycastle.math.ec.ZTauElement zTauElement) {
        return multiplyFromTnaf(abstractF2m, tauAdicNaf(getMu(((com.android.internal.org.bouncycastle.math.ec.ECCurve.AbstractF2m) abstractF2m.getCurve()).getA()), zTauElement));
    }

    public static com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m multiplyFromTnaf(com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m abstractF2m, byte[] bArr) {
        com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m abstractF2m2 = (com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m) abstractF2m.getCurve().getInfinity();
        com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m abstractF2m3 = (com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m) abstractF2m.negate();
        int i = 0;
        for (int length = bArr.length - 1; length >= 0; length--) {
            i++;
            byte b = bArr[length];
            if (b != 0) {
                abstractF2m2 = (com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m) abstractF2m2.tauPow(i).add(b > 0 ? abstractF2m : abstractF2m3);
                i = 0;
            }
        }
        if (i > 0) {
            return abstractF2m2.tauPow(i);
        }
        return abstractF2m2;
    }

    public static byte[] tauAdicWNaf(byte b, com.android.internal.org.bouncycastle.math.ec.ZTauElement zTauElement, byte b2, java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, com.android.internal.org.bouncycastle.math.ec.ZTauElement[] zTauElementArr) {
        java.math.BigInteger subtract;
        byte intValue;
        boolean z;
        if (b != 1 && b != -1) {
            throw new java.lang.IllegalArgumentException("mu must be 1 or -1");
        }
        int bitLength = norm(b, zTauElement).bitLength();
        byte[] bArr = new byte[bitLength > 30 ? bitLength + 4 + b2 : b2 + 34];
        java.math.BigInteger shiftRight = bigInteger.shiftRight(1);
        java.math.BigInteger bigInteger3 = zTauElement.u;
        java.math.BigInteger bigInteger4 = zTauElement.v;
        int i = 0;
        while (true) {
            if (!bigInteger3.equals(com.android.internal.org.bouncycastle.math.ec.ECConstants.ZERO) || !bigInteger4.equals(com.android.internal.org.bouncycastle.math.ec.ECConstants.ZERO)) {
                if (bigInteger3.testBit(0)) {
                    java.math.BigInteger mod = bigInteger3.add(bigInteger4.multiply(bigInteger2)).mod(bigInteger);
                    if (mod.compareTo(shiftRight) >= 0) {
                        intValue = (byte) mod.subtract(bigInteger).intValue();
                    } else {
                        intValue = (byte) mod.intValue();
                    }
                    bArr[i] = intValue;
                    if (intValue >= 0) {
                        z = true;
                    } else {
                        intValue = (byte) (-intValue);
                        z = false;
                    }
                    if (z) {
                        bigInteger3 = bigInteger3.subtract(zTauElementArr[intValue].u);
                        bigInteger4 = bigInteger4.subtract(zTauElementArr[intValue].v);
                    } else {
                        bigInteger3 = bigInteger3.add(zTauElementArr[intValue].u);
                        bigInteger4 = bigInteger4.add(zTauElementArr[intValue].v);
                    }
                } else {
                    bArr[i] = 0;
                }
                if (b == 1) {
                    subtract = bigInteger4.add(bigInteger3.shiftRight(1));
                } else {
                    subtract = bigInteger4.subtract(bigInteger3.shiftRight(1));
                }
                java.math.BigInteger negate = bigInteger3.shiftRight(1).negate();
                i++;
                bigInteger3 = subtract;
                bigInteger4 = negate;
            } else {
                return bArr;
            }
        }
    }

    public static com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m[] getPreComp(com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m abstractF2m, byte b) {
        byte[][] bArr = b == 0 ? alpha0Tnaf : alpha1Tnaf;
        com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m[] abstractF2mArr = new com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m[(bArr.length + 1) >>> 1];
        abstractF2mArr[0] = abstractF2m;
        int length = bArr.length;
        for (int i = 3; i < length; i += 2) {
            abstractF2mArr[i >>> 1] = multiplyFromTnaf(abstractF2m, bArr[i]);
        }
        abstractF2m.getCurve().normalizeAll(abstractF2mArr);
        return abstractF2mArr;
    }
}
