package com.android.internal.org.bouncycastle.math.ec;

/* loaded from: classes4.dex */
public abstract class ECCurve {
    public static final int COORD_AFFINE = 0;
    public static final int COORD_HOMOGENEOUS = 1;
    public static final int COORD_JACOBIAN = 2;
    public static final int COORD_JACOBIAN_CHUDNOVSKY = 3;
    public static final int COORD_JACOBIAN_MODIFIED = 4;
    public static final int COORD_LAMBDA_AFFINE = 5;
    public static final int COORD_LAMBDA_PROJECTIVE = 6;
    public static final int COORD_SKEWED = 7;
    protected com.android.internal.org.bouncycastle.math.ec.ECFieldElement a;
    protected com.android.internal.org.bouncycastle.math.ec.ECFieldElement b;
    protected java.math.BigInteger cofactor;
    protected com.android.internal.org.bouncycastle.math.field.FiniteField field;
    protected java.math.BigInteger order;
    protected int coord = 0;
    protected com.android.internal.org.bouncycastle.math.ec.endo.ECEndomorphism endomorphism = null;
    protected com.android.internal.org.bouncycastle.math.ec.ECMultiplier multiplier = null;

    protected abstract com.android.internal.org.bouncycastle.math.ec.ECCurve cloneCurve();

    protected abstract com.android.internal.org.bouncycastle.math.ec.ECPoint createRawPoint(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2);

    protected abstract com.android.internal.org.bouncycastle.math.ec.ECPoint createRawPoint(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2, com.android.internal.org.bouncycastle.math.ec.ECFieldElement[] eCFieldElementArr);

    protected abstract com.android.internal.org.bouncycastle.math.ec.ECPoint decompressPoint(int i, java.math.BigInteger bigInteger);

    public abstract com.android.internal.org.bouncycastle.math.ec.ECFieldElement fromBigInteger(java.math.BigInteger bigInteger);

    public abstract int getFieldSize();

    public abstract com.android.internal.org.bouncycastle.math.ec.ECPoint getInfinity();

    public abstract boolean isValidFieldElement(java.math.BigInteger bigInteger);

    public abstract com.android.internal.org.bouncycastle.math.ec.ECFieldElement randomFieldElement(java.security.SecureRandom secureRandom);

    public abstract com.android.internal.org.bouncycastle.math.ec.ECFieldElement randomFieldElementMult(java.security.SecureRandom secureRandom);

    public static int[] getAllCoordinateSystems() {
        return new int[]{0, 1, 2, 3, 4, 5, 6, 7};
    }

    public class Config {
        protected int coord;
        protected com.android.internal.org.bouncycastle.math.ec.endo.ECEndomorphism endomorphism;
        protected com.android.internal.org.bouncycastle.math.ec.ECMultiplier multiplier;

        Config(int i, com.android.internal.org.bouncycastle.math.ec.endo.ECEndomorphism eCEndomorphism, com.android.internal.org.bouncycastle.math.ec.ECMultiplier eCMultiplier) {
            this.coord = i;
            this.endomorphism = eCEndomorphism;
            this.multiplier = eCMultiplier;
        }

        public com.android.internal.org.bouncycastle.math.ec.ECCurve.Config setCoordinateSystem(int i) {
            this.coord = i;
            return this;
        }

        public com.android.internal.org.bouncycastle.math.ec.ECCurve.Config setEndomorphism(com.android.internal.org.bouncycastle.math.ec.endo.ECEndomorphism eCEndomorphism) {
            this.endomorphism = eCEndomorphism;
            return this;
        }

        public com.android.internal.org.bouncycastle.math.ec.ECCurve.Config setMultiplier(com.android.internal.org.bouncycastle.math.ec.ECMultiplier eCMultiplier) {
            this.multiplier = eCMultiplier;
            return this;
        }

        public com.android.internal.org.bouncycastle.math.ec.ECCurve create() {
            if (!com.android.internal.org.bouncycastle.math.ec.ECCurve.this.supportsCoordinateSystem(this.coord)) {
                throw new java.lang.IllegalStateException("unsupported coordinate system");
            }
            com.android.internal.org.bouncycastle.math.ec.ECCurve cloneCurve = com.android.internal.org.bouncycastle.math.ec.ECCurve.this.cloneCurve();
            if (cloneCurve == com.android.internal.org.bouncycastle.math.ec.ECCurve.this) {
                throw new java.lang.IllegalStateException("implementation returned current curve");
            }
            synchronized (cloneCurve) {
                cloneCurve.coord = this.coord;
                cloneCurve.endomorphism = this.endomorphism;
                cloneCurve.multiplier = this.multiplier;
            }
            return cloneCurve;
        }
    }

    protected ECCurve(com.android.internal.org.bouncycastle.math.field.FiniteField finiteField) {
        this.field = finiteField;
    }

    public synchronized com.android.internal.org.bouncycastle.math.ec.ECCurve.Config configure() {
        return new com.android.internal.org.bouncycastle.math.ec.ECCurve.Config(this.coord, this.endomorphism, this.multiplier);
    }

    public com.android.internal.org.bouncycastle.math.ec.ECPoint validatePoint(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
        com.android.internal.org.bouncycastle.math.ec.ECPoint createPoint = createPoint(bigInteger, bigInteger2);
        if (!createPoint.isValid()) {
            throw new java.lang.IllegalArgumentException("Invalid point coordinates");
        }
        return createPoint;
    }

    public com.android.internal.org.bouncycastle.math.ec.ECPoint createPoint(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
        return createRawPoint(fromBigInteger(bigInteger), fromBigInteger(bigInteger2));
    }

    protected com.android.internal.org.bouncycastle.math.ec.ECMultiplier createDefaultMultiplier() {
        if (this.endomorphism instanceof com.android.internal.org.bouncycastle.math.ec.endo.GLVEndomorphism) {
            return new com.android.internal.org.bouncycastle.math.ec.GLVMultiplier(this, (com.android.internal.org.bouncycastle.math.ec.endo.GLVEndomorphism) this.endomorphism);
        }
        return new com.android.internal.org.bouncycastle.math.ec.WNafL2RMultiplier();
    }

    public boolean supportsCoordinateSystem(int i) {
        return i == 0;
    }

    public com.android.internal.org.bouncycastle.math.ec.PreCompInfo getPreCompInfo(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, java.lang.String str) {
        java.util.Hashtable hashtable;
        com.android.internal.org.bouncycastle.math.ec.PreCompInfo preCompInfo;
        checkPoint(eCPoint);
        synchronized (eCPoint) {
            hashtable = eCPoint.preCompTable;
        }
        if (hashtable == null) {
            return null;
        }
        synchronized (hashtable) {
            preCompInfo = (com.android.internal.org.bouncycastle.math.ec.PreCompInfo) hashtable.get(str);
        }
        return preCompInfo;
    }

    public com.android.internal.org.bouncycastle.math.ec.PreCompInfo precompute(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint, java.lang.String str, com.android.internal.org.bouncycastle.math.ec.PreCompCallback preCompCallback) {
        java.util.Hashtable hashtable;
        com.android.internal.org.bouncycastle.math.ec.PreCompInfo precompute;
        checkPoint(eCPoint);
        synchronized (eCPoint) {
            hashtable = eCPoint.preCompTable;
            if (hashtable == null) {
                hashtable = new java.util.Hashtable(4);
                eCPoint.preCompTable = hashtable;
            }
        }
        synchronized (hashtable) {
            com.android.internal.org.bouncycastle.math.ec.PreCompInfo preCompInfo = (com.android.internal.org.bouncycastle.math.ec.PreCompInfo) hashtable.get(str);
            precompute = preCompCallback.precompute(preCompInfo);
            if (precompute != preCompInfo) {
                hashtable.put(str, precompute);
            }
        }
        return precompute;
    }

    public com.android.internal.org.bouncycastle.math.ec.ECPoint importPoint(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint) {
        if (this == eCPoint.getCurve()) {
            return eCPoint;
        }
        if (eCPoint.isInfinity()) {
            return getInfinity();
        }
        com.android.internal.org.bouncycastle.math.ec.ECPoint normalize = eCPoint.normalize();
        return createPoint(normalize.getXCoord().toBigInteger(), normalize.getYCoord().toBigInteger());
    }

    public void normalizeAll(com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr) {
        normalizeAll(eCPointArr, 0, eCPointArr.length, null);
    }

    public void normalizeAll(com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr, int i, int i2, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
        checkPoints(eCPointArr, i, i2);
        switch (getCoordinateSystem()) {
            case 0:
            case 5:
                if (eCFieldElement != null) {
                    throw new java.lang.IllegalArgumentException("'iso' not valid for affine coordinates");
                }
                return;
            default:
                com.android.internal.org.bouncycastle.math.ec.ECFieldElement[] eCFieldElementArr = new com.android.internal.org.bouncycastle.math.ec.ECFieldElement[i2];
                int[] iArr = new int[i2];
                int i3 = 0;
                for (int i4 = 0; i4 < i2; i4++) {
                    int i5 = i + i4;
                    com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint = eCPointArr[i5];
                    if (eCPoint != null && (eCFieldElement != null || !eCPoint.isNormalized())) {
                        eCFieldElementArr[i3] = eCPoint.getZCoord(0);
                        iArr[i3] = i5;
                        i3++;
                    }
                }
                if (i3 == 0) {
                    return;
                }
                com.android.internal.org.bouncycastle.math.ec.ECAlgorithms.montgomeryTrick(eCFieldElementArr, 0, i3, eCFieldElement);
                for (int i6 = 0; i6 < i3; i6++) {
                    int i7 = iArr[i6];
                    eCPointArr[i7] = eCPointArr[i7].normalize(eCFieldElementArr[i6]);
                }
                return;
        }
    }

    public com.android.internal.org.bouncycastle.math.field.FiniteField getField() {
        return this.field;
    }

    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement getA() {
        return this.a;
    }

    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement getB() {
        return this.b;
    }

    public java.math.BigInteger getOrder() {
        return this.order;
    }

    public java.math.BigInteger getCofactor() {
        return this.cofactor;
    }

    public int getCoordinateSystem() {
        return this.coord;
    }

    public com.android.internal.org.bouncycastle.math.ec.endo.ECEndomorphism getEndomorphism() {
        return this.endomorphism;
    }

    public com.android.internal.org.bouncycastle.math.ec.ECMultiplier getMultiplier() {
        if (this.multiplier == null) {
            this.multiplier = createDefaultMultiplier();
        }
        return this.multiplier;
    }

    public com.android.internal.org.bouncycastle.math.ec.ECPoint decodePoint(byte[] bArr) {
        com.android.internal.org.bouncycastle.math.ec.ECPoint infinity;
        int fieldSize = (getFieldSize() + 7) / 8;
        boolean z = false;
        byte b = bArr[0];
        switch (b) {
            case 0:
                if (bArr.length != 1) {
                    throw new java.lang.IllegalArgumentException("Incorrect length for infinity encoding");
                }
                infinity = getInfinity();
                break;
            case 1:
            case 5:
            default:
                throw new java.lang.IllegalArgumentException("Invalid point encoding 0x" + java.lang.Integer.toString(b, 16));
            case 2:
            case 3:
                if (bArr.length != fieldSize + 1) {
                    throw new java.lang.IllegalArgumentException("Incorrect length for compressed encoding");
                }
                infinity = decompressPoint(b & 1, com.android.internal.org.bouncycastle.util.BigIntegers.fromUnsignedByteArray(bArr, 1, fieldSize));
                if (!infinity.implIsValid(true, true)) {
                    throw new java.lang.IllegalArgumentException("Invalid point");
                }
                break;
            case 4:
                if (bArr.length != (fieldSize * 2) + 1) {
                    throw new java.lang.IllegalArgumentException("Incorrect length for uncompressed encoding");
                }
                infinity = validatePoint(com.android.internal.org.bouncycastle.util.BigIntegers.fromUnsignedByteArray(bArr, 1, fieldSize), com.android.internal.org.bouncycastle.util.BigIntegers.fromUnsignedByteArray(bArr, fieldSize + 1, fieldSize));
                break;
            case 6:
            case 7:
                if (bArr.length != (fieldSize * 2) + 1) {
                    throw new java.lang.IllegalArgumentException("Incorrect length for hybrid encoding");
                }
                java.math.BigInteger fromUnsignedByteArray = com.android.internal.org.bouncycastle.util.BigIntegers.fromUnsignedByteArray(bArr, 1, fieldSize);
                java.math.BigInteger fromUnsignedByteArray2 = com.android.internal.org.bouncycastle.util.BigIntegers.fromUnsignedByteArray(bArr, fieldSize + 1, fieldSize);
                boolean testBit = fromUnsignedByteArray2.testBit(0);
                if (b == 7) {
                    z = true;
                }
                if (testBit != z) {
                    throw new java.lang.IllegalArgumentException("Inconsistent Y coordinate in hybrid encoding");
                }
                infinity = validatePoint(fromUnsignedByteArray, fromUnsignedByteArray2);
                break;
        }
        if (b != 0 && infinity.isInfinity()) {
            throw new java.lang.IllegalArgumentException("Invalid infinity encoding");
        }
        return infinity;
    }

    public com.android.internal.org.bouncycastle.math.ec.ECLookupTable createCacheSafeLookupTable(com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr, int i, final int i2) {
        final int fieldSize = (getFieldSize() + 7) >>> 3;
        final byte[] bArr = new byte[i2 * fieldSize * 2];
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint = eCPointArr[i + i4];
            byte[] byteArray = eCPoint.getRawXCoord().toBigInteger().toByteArray();
            byte[] byteArray2 = eCPoint.getRawYCoord().toBigInteger().toByteArray();
            int i5 = 1;
            int i6 = byteArray.length > fieldSize ? 1 : 0;
            int length = byteArray.length - i6;
            if (byteArray2.length <= fieldSize) {
                i5 = 0;
            }
            int length2 = byteArray2.length - i5;
            int i7 = i3 + fieldSize;
            java.lang.System.arraycopy(byteArray, i6, bArr, i7 - length, length);
            i3 = i7 + fieldSize;
            java.lang.System.arraycopy(byteArray2, i5, bArr, i3 - length2, length2);
        }
        return new com.android.internal.org.bouncycastle.math.ec.AbstractECLookupTable() { // from class: com.android.internal.org.bouncycastle.math.ec.ECCurve.1
            @Override // com.android.internal.org.bouncycastle.math.ec.ECLookupTable
            public int getSize() {
                return i2;
            }

            @Override // com.android.internal.org.bouncycastle.math.ec.ECLookupTable
            public com.android.internal.org.bouncycastle.math.ec.ECPoint lookup(int i8) {
                byte[] bArr2 = new byte[fieldSize];
                byte[] bArr3 = new byte[fieldSize];
                int i9 = 0;
                for (int i10 = 0; i10 < i2; i10++) {
                    int i11 = ((i10 ^ i8) - 1) >> 31;
                    for (int i12 = 0; i12 < fieldSize; i12++) {
                        bArr2[i12] = (byte) (bArr2[i12] ^ (bArr[i9 + i12] & i11));
                        bArr3[i12] = (byte) (bArr3[i12] ^ (bArr[(fieldSize + i9) + i12] & i11));
                    }
                    i9 += fieldSize * 2;
                }
                return createPoint(bArr2, bArr3);
            }

            @Override // com.android.internal.org.bouncycastle.math.ec.AbstractECLookupTable, com.android.internal.org.bouncycastle.math.ec.ECLookupTable
            public com.android.internal.org.bouncycastle.math.ec.ECPoint lookupVar(int i8) {
                byte[] bArr2 = new byte[fieldSize];
                byte[] bArr3 = new byte[fieldSize];
                int i9 = i8 * fieldSize * 2;
                for (int i10 = 0; i10 < fieldSize; i10++) {
                    bArr2[i10] = bArr[i9 + i10];
                    bArr3[i10] = bArr[fieldSize + i9 + i10];
                }
                return createPoint(bArr2, bArr3);
            }

            private com.android.internal.org.bouncycastle.math.ec.ECPoint createPoint(byte[] bArr2, byte[] bArr3) {
                return com.android.internal.org.bouncycastle.math.ec.ECCurve.this.createRawPoint(com.android.internal.org.bouncycastle.math.ec.ECCurve.this.fromBigInteger(new java.math.BigInteger(1, bArr2)), com.android.internal.org.bouncycastle.math.ec.ECCurve.this.fromBigInteger(new java.math.BigInteger(1, bArr3)));
            }
        };
    }

    protected void checkPoint(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint) {
        if (eCPoint == null || this != eCPoint.getCurve()) {
            throw new java.lang.IllegalArgumentException("'point' must be non-null and on this curve");
        }
    }

    protected void checkPoints(com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr) {
        checkPoints(eCPointArr, 0, eCPointArr.length);
    }

    protected void checkPoints(com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr, int i, int i2) {
        if (eCPointArr == null) {
            throw new java.lang.IllegalArgumentException("'points' cannot be null");
        }
        if (i < 0 || i2 < 0 || i > eCPointArr.length - i2) {
            throw new java.lang.IllegalArgumentException("invalid range specified for 'points'");
        }
        for (int i3 = 0; i3 < i2; i3++) {
            com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint = eCPointArr[i + i3];
            if (eCPoint != null && this != eCPoint.getCurve()) {
                throw new java.lang.IllegalArgumentException("'points' entries must be null or on this curve");
            }
        }
    }

    public boolean equals(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve) {
        return this == eCCurve || (eCCurve != null && getField().equals(eCCurve.getField()) && getA().toBigInteger().equals(eCCurve.getA().toBigInteger()) && getB().toBigInteger().equals(eCCurve.getB().toBigInteger()));
    }

    public boolean equals(java.lang.Object obj) {
        return this == obj || ((obj instanceof com.android.internal.org.bouncycastle.math.ec.ECCurve) && equals((com.android.internal.org.bouncycastle.math.ec.ECCurve) obj));
    }

    public int hashCode() {
        return (getField().hashCode() ^ com.android.internal.org.bouncycastle.util.Integers.rotateLeft(getA().toBigInteger().hashCode(), 8)) ^ com.android.internal.org.bouncycastle.util.Integers.rotateLeft(getB().toBigInteger().hashCode(), 16);
    }

    public static abstract class AbstractFp extends com.android.internal.org.bouncycastle.math.ec.ECCurve {
        protected AbstractFp(java.math.BigInteger bigInteger) {
            super(com.android.internal.org.bouncycastle.math.field.FiniteFields.getPrimeField(bigInteger));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
        public boolean isValidFieldElement(java.math.BigInteger bigInteger) {
            return bigInteger != null && bigInteger.signum() >= 0 && bigInteger.compareTo(getField().getCharacteristic()) < 0;
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement randomFieldElement(java.security.SecureRandom secureRandom) {
            java.math.BigInteger characteristic = getField().getCharacteristic();
            return fromBigInteger(implRandomFieldElement(secureRandom, characteristic)).multiply(fromBigInteger(implRandomFieldElement(secureRandom, characteristic)));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement randomFieldElementMult(java.security.SecureRandom secureRandom) {
            java.math.BigInteger characteristic = getField().getCharacteristic();
            return fromBigInteger(implRandomFieldElementMult(secureRandom, characteristic)).multiply(fromBigInteger(implRandomFieldElementMult(secureRandom, characteristic)));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
        protected com.android.internal.org.bouncycastle.math.ec.ECPoint decompressPoint(int i, java.math.BigInteger bigInteger) {
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement fromBigInteger = fromBigInteger(bigInteger);
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement sqrt = fromBigInteger.square().add(this.a).multiply(fromBigInteger).add(this.b).sqrt();
            if (sqrt == null) {
                throw new java.lang.IllegalArgumentException("Invalid point compression");
            }
            if (sqrt.testBitZero() != (i == 1)) {
                sqrt = sqrt.negate();
            }
            return createRawPoint(fromBigInteger, sqrt);
        }

        private static java.math.BigInteger implRandomFieldElement(java.security.SecureRandom secureRandom, java.math.BigInteger bigInteger) {
            java.math.BigInteger createRandomBigInteger;
            do {
                createRandomBigInteger = com.android.internal.org.bouncycastle.util.BigIntegers.createRandomBigInteger(bigInteger.bitLength(), secureRandom);
            } while (createRandomBigInteger.compareTo(bigInteger) >= 0);
            return createRandomBigInteger;
        }

        private static java.math.BigInteger implRandomFieldElementMult(java.security.SecureRandom secureRandom, java.math.BigInteger bigInteger) {
            while (true) {
                java.math.BigInteger createRandomBigInteger = com.android.internal.org.bouncycastle.util.BigIntegers.createRandomBigInteger(bigInteger.bitLength(), secureRandom);
                if (createRandomBigInteger.signum() > 0 && createRandomBigInteger.compareTo(bigInteger) < 0) {
                    return createRandomBigInteger;
                }
            }
        }
    }

    public static class Fp extends com.android.internal.org.bouncycastle.math.ec.ECCurve.AbstractFp {
        private static final int FP_DEFAULT_COORDS = 4;
        com.android.internal.org.bouncycastle.math.ec.ECPoint.Fp infinity;
        java.math.BigInteger q;
        java.math.BigInteger r;

        public Fp(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, java.math.BigInteger bigInteger3) {
            this(bigInteger, bigInteger2, bigInteger3, null, null);
        }

        public Fp(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, java.math.BigInteger bigInteger3, java.math.BigInteger bigInteger4, java.math.BigInteger bigInteger5) {
            super(bigInteger);
            this.q = bigInteger;
            this.r = com.android.internal.org.bouncycastle.math.ec.ECFieldElement.Fp.calculateResidue(bigInteger);
            this.infinity = new com.android.internal.org.bouncycastle.math.ec.ECPoint.Fp(this, null, null);
            this.a = fromBigInteger(bigInteger2);
            this.b = fromBigInteger(bigInteger3);
            this.order = bigInteger4;
            this.cofactor = bigInteger5;
            this.coord = 4;
        }

        protected Fp(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2, java.math.BigInteger bigInteger3, java.math.BigInteger bigInteger4) {
            super(bigInteger);
            this.q = bigInteger;
            this.r = bigInteger2;
            this.infinity = new com.android.internal.org.bouncycastle.math.ec.ECPoint.Fp(this, null, null);
            this.a = eCFieldElement;
            this.b = eCFieldElement2;
            this.order = bigInteger3;
            this.cofactor = bigInteger4;
            this.coord = 4;
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
        protected com.android.internal.org.bouncycastle.math.ec.ECCurve cloneCurve() {
            return new com.android.internal.org.bouncycastle.math.ec.ECCurve.Fp(this.q, this.r, this.a, this.b, this.order, this.cofactor);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
        public boolean supportsCoordinateSystem(int i) {
            switch (i) {
                case 0:
                case 1:
                case 2:
                case 4:
                    return true;
                case 3:
                default:
                    return false;
            }
        }

        public java.math.BigInteger getQ() {
            return this.q;
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
        public int getFieldSize() {
            return this.q.bitLength();
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement fromBigInteger(java.math.BigInteger bigInteger) {
            return new com.android.internal.org.bouncycastle.math.ec.ECFieldElement.Fp(this.q, this.r, bigInteger);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
        protected com.android.internal.org.bouncycastle.math.ec.ECPoint createRawPoint(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2) {
            return new com.android.internal.org.bouncycastle.math.ec.ECPoint.Fp(this, eCFieldElement, eCFieldElement2);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
        protected com.android.internal.org.bouncycastle.math.ec.ECPoint createRawPoint(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2, com.android.internal.org.bouncycastle.math.ec.ECFieldElement[] eCFieldElementArr) {
            return new com.android.internal.org.bouncycastle.math.ec.ECPoint.Fp(this, eCFieldElement, eCFieldElement2, eCFieldElementArr);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
        public com.android.internal.org.bouncycastle.math.ec.ECPoint importPoint(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint) {
            if (this != eCPoint.getCurve() && getCoordinateSystem() == 2 && !eCPoint.isInfinity()) {
                switch (eCPoint.getCurve().getCoordinateSystem()) {
                    case 2:
                    case 3:
                    case 4:
                        return new com.android.internal.org.bouncycastle.math.ec.ECPoint.Fp(this, fromBigInteger(eCPoint.x.toBigInteger()), fromBigInteger(eCPoint.y.toBigInteger()), new com.android.internal.org.bouncycastle.math.ec.ECFieldElement[]{fromBigInteger(eCPoint.zs[0].toBigInteger())});
                }
            }
            return super.importPoint(eCPoint);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
        public com.android.internal.org.bouncycastle.math.ec.ECPoint getInfinity() {
            return this.infinity;
        }
    }

    public static abstract class AbstractF2m extends com.android.internal.org.bouncycastle.math.ec.ECCurve {
        private java.math.BigInteger[] si;

        public static java.math.BigInteger inverse(int i, int[] iArr, java.math.BigInteger bigInteger) {
            return new com.android.internal.org.bouncycastle.math.ec.LongArray(bigInteger).modInverse(i, iArr).toBigInteger();
        }

        private static com.android.internal.org.bouncycastle.math.field.FiniteField buildField(int i, int i2, int i3, int i4) {
            if (i2 == 0) {
                throw new java.lang.IllegalArgumentException("k1 must be > 0");
            }
            if (i3 == 0) {
                if (i4 != 0) {
                    throw new java.lang.IllegalArgumentException("k3 must be 0 if k2 == 0");
                }
                return com.android.internal.org.bouncycastle.math.field.FiniteFields.getBinaryExtensionField(new int[]{0, i2, i});
            }
            if (i3 <= i2) {
                throw new java.lang.IllegalArgumentException("k2 must be > k1");
            }
            if (i4 <= i3) {
                throw new java.lang.IllegalArgumentException("k3 must be > k2");
            }
            return com.android.internal.org.bouncycastle.math.field.FiniteFields.getBinaryExtensionField(new int[]{0, i2, i3, i4, i});
        }

        protected AbstractF2m(int i, int i2, int i3, int i4) {
            super(buildField(i, i2, i3, i4));
            this.si = null;
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
        public com.android.internal.org.bouncycastle.math.ec.ECPoint createPoint(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement fromBigInteger = fromBigInteger(bigInteger);
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement fromBigInteger2 = fromBigInteger(bigInteger2);
            switch (getCoordinateSystem()) {
                case 5:
                case 6:
                    if (fromBigInteger.isZero()) {
                        if (!fromBigInteger2.square().equals(getB())) {
                            throw new java.lang.IllegalArgumentException();
                        }
                    } else {
                        fromBigInteger2 = fromBigInteger2.divide(fromBigInteger).add(fromBigInteger);
                        break;
                    }
                    break;
            }
            return createRawPoint(fromBigInteger, fromBigInteger2);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
        public boolean isValidFieldElement(java.math.BigInteger bigInteger) {
            return bigInteger != null && bigInteger.signum() >= 0 && bigInteger.bitLength() <= getFieldSize();
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement randomFieldElement(java.security.SecureRandom secureRandom) {
            return fromBigInteger(com.android.internal.org.bouncycastle.util.BigIntegers.createRandomBigInteger(getFieldSize(), secureRandom));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement randomFieldElementMult(java.security.SecureRandom secureRandom) {
            int fieldSize = getFieldSize();
            return fromBigInteger(implRandomFieldElementMult(secureRandom, fieldSize)).multiply(fromBigInteger(implRandomFieldElementMult(secureRandom, fieldSize)));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
        protected com.android.internal.org.bouncycastle.math.ec.ECPoint decompressPoint(int i, java.math.BigInteger bigInteger) {
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement;
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement fromBigInteger = fromBigInteger(bigInteger);
            if (fromBigInteger.isZero()) {
                eCFieldElement = getB().sqrt();
            } else {
                com.android.internal.org.bouncycastle.math.ec.ECFieldElement solveQuadraticEquation = solveQuadraticEquation(fromBigInteger.square().invert().multiply(getB()).add(getA()).add(fromBigInteger));
                if (solveQuadraticEquation == null) {
                    eCFieldElement = null;
                } else {
                    if (solveQuadraticEquation.testBitZero() != (i == 1)) {
                        solveQuadraticEquation = solveQuadraticEquation.addOne();
                    }
                    switch (getCoordinateSystem()) {
                        case 5:
                        case 6:
                            eCFieldElement = solveQuadraticEquation.add(fromBigInteger);
                            break;
                        default:
                            eCFieldElement = solveQuadraticEquation.multiply(fromBigInteger);
                            break;
                    }
                }
            }
            if (eCFieldElement == null) {
                throw new java.lang.IllegalArgumentException("Invalid point compression");
            }
            return createRawPoint(fromBigInteger, eCFieldElement);
        }

        protected com.android.internal.org.bouncycastle.math.ec.ECFieldElement solveQuadraticEquation(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2;
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement.AbstractF2m abstractF2m = (com.android.internal.org.bouncycastle.math.ec.ECFieldElement.AbstractF2m) eCFieldElement;
            boolean hasFastTrace = abstractF2m.hasFastTrace();
            if (hasFastTrace && abstractF2m.trace() != 0) {
                return null;
            }
            int fieldSize = getFieldSize();
            if ((fieldSize & 1) != 0) {
                com.android.internal.org.bouncycastle.math.ec.ECFieldElement halfTrace = abstractF2m.halfTrace();
                if (!hasFastTrace && !halfTrace.square().add(halfTrace).add(eCFieldElement).isZero()) {
                    return null;
                }
                return halfTrace;
            }
            if (eCFieldElement.isZero()) {
                return eCFieldElement;
            }
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement fromBigInteger = fromBigInteger(com.android.internal.org.bouncycastle.math.ec.ECConstants.ZERO);
            java.util.Random random = new java.util.Random();
            do {
                com.android.internal.org.bouncycastle.math.ec.ECFieldElement fromBigInteger2 = fromBigInteger(new java.math.BigInteger(fieldSize, random));
                com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement3 = eCFieldElement;
                eCFieldElement2 = fromBigInteger;
                for (int i = 1; i < fieldSize; i++) {
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement square = eCFieldElement3.square();
                    eCFieldElement2 = eCFieldElement2.square().add(square.multiply(fromBigInteger2));
                    eCFieldElement3 = square.add(eCFieldElement);
                }
                if (!eCFieldElement3.isZero()) {
                    return null;
                }
            } while (eCFieldElement2.square().add(eCFieldElement2).isZero());
            return eCFieldElement2;
        }

        synchronized java.math.BigInteger[] getSi() {
            if (this.si == null) {
                this.si = com.android.internal.org.bouncycastle.math.ec.Tnaf.getSi(this);
            }
            return this.si;
        }

        public boolean isKoblitz() {
            return this.order != null && this.cofactor != null && this.b.isOne() && (this.a.isZero() || this.a.isOne());
        }

        private static java.math.BigInteger implRandomFieldElementMult(java.security.SecureRandom secureRandom, int i) {
            java.math.BigInteger createRandomBigInteger;
            do {
                createRandomBigInteger = com.android.internal.org.bouncycastle.util.BigIntegers.createRandomBigInteger(i, secureRandom);
            } while (createRandomBigInteger.signum() <= 0);
            return createRandomBigInteger;
        }
    }

    public static class F2m extends com.android.internal.org.bouncycastle.math.ec.ECCurve.AbstractF2m {
        private static final int F2M_DEFAULT_COORDS = 6;
        private com.android.internal.org.bouncycastle.math.ec.ECPoint.F2m infinity;
        private int k1;
        private int k2;
        private int k3;
        private int m;

        public F2m(int i, int i2, java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
            this(i, i2, 0, 0, bigInteger, bigInteger2, (java.math.BigInteger) null, (java.math.BigInteger) null);
        }

        public F2m(int i, int i2, java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, java.math.BigInteger bigInteger3, java.math.BigInteger bigInteger4) {
            this(i, i2, 0, 0, bigInteger, bigInteger2, bigInteger3, bigInteger4);
        }

        public F2m(int i, int i2, int i3, int i4, java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
            this(i, i2, i3, i4, bigInteger, bigInteger2, (java.math.BigInteger) null, (java.math.BigInteger) null);
        }

        public F2m(int i, int i2, int i3, int i4, java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, java.math.BigInteger bigInteger3, java.math.BigInteger bigInteger4) {
            super(i, i2, i3, i4);
            this.m = i;
            this.k1 = i2;
            this.k2 = i3;
            this.k3 = i4;
            this.order = bigInteger3;
            this.cofactor = bigInteger4;
            this.infinity = new com.android.internal.org.bouncycastle.math.ec.ECPoint.F2m(this, null, null);
            this.a = fromBigInteger(bigInteger);
            this.b = fromBigInteger(bigInteger2);
            this.coord = 6;
        }

        protected F2m(int i, int i2, int i3, int i4, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2, java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
            super(i, i2, i3, i4);
            this.m = i;
            this.k1 = i2;
            this.k2 = i3;
            this.k3 = i4;
            this.order = bigInteger;
            this.cofactor = bigInteger2;
            this.infinity = new com.android.internal.org.bouncycastle.math.ec.ECPoint.F2m(this, null, null);
            this.a = eCFieldElement;
            this.b = eCFieldElement2;
            this.coord = 6;
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
        protected com.android.internal.org.bouncycastle.math.ec.ECCurve cloneCurve() {
            return new com.android.internal.org.bouncycastle.math.ec.ECCurve.F2m(this.m, this.k1, this.k2, this.k3, this.a, this.b, this.order, this.cofactor);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
        public boolean supportsCoordinateSystem(int i) {
            switch (i) {
                case 0:
                case 1:
                case 6:
                    return true;
                default:
                    return false;
            }
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
        protected com.android.internal.org.bouncycastle.math.ec.ECMultiplier createDefaultMultiplier() {
            if (isKoblitz()) {
                return new com.android.internal.org.bouncycastle.math.ec.WTauNafMultiplier();
            }
            return super.createDefaultMultiplier();
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
        public int getFieldSize() {
            return this.m;
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement fromBigInteger(java.math.BigInteger bigInteger) {
            return new com.android.internal.org.bouncycastle.math.ec.ECFieldElement.F2m(this.m, this.k1, this.k2, this.k3, bigInteger);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
        protected com.android.internal.org.bouncycastle.math.ec.ECPoint createRawPoint(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2) {
            return new com.android.internal.org.bouncycastle.math.ec.ECPoint.F2m(this, eCFieldElement, eCFieldElement2);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
        protected com.android.internal.org.bouncycastle.math.ec.ECPoint createRawPoint(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2, com.android.internal.org.bouncycastle.math.ec.ECFieldElement[] eCFieldElementArr) {
            return new com.android.internal.org.bouncycastle.math.ec.ECPoint.F2m(this, eCFieldElement, eCFieldElement2, eCFieldElementArr);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
        public com.android.internal.org.bouncycastle.math.ec.ECPoint getInfinity() {
            return this.infinity;
        }

        public int getM() {
            return this.m;
        }

        public boolean isTrinomial() {
            return this.k2 == 0 && this.k3 == 0;
        }

        public int getK1() {
            return this.k1;
        }

        public int getK2() {
            return this.k2;
        }

        public int getK3() {
            return this.k3;
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
        public com.android.internal.org.bouncycastle.math.ec.ECLookupTable createCacheSafeLookupTable(com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr, int i, final int i2) {
            final int i3 = (this.m + 63) >>> 6;
            final int[] iArr = isTrinomial() ? new int[]{this.k1} : new int[]{this.k1, this.k2, this.k3};
            final long[] jArr = new long[i2 * i3 * 2];
            int i4 = 0;
            for (int i5 = 0; i5 < i2; i5++) {
                com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint = eCPointArr[i + i5];
                ((com.android.internal.org.bouncycastle.math.ec.ECFieldElement.F2m) eCPoint.getRawXCoord()).x.copyTo(jArr, i4);
                int i6 = i4 + i3;
                ((com.android.internal.org.bouncycastle.math.ec.ECFieldElement.F2m) eCPoint.getRawYCoord()).x.copyTo(jArr, i6);
                i4 = i6 + i3;
            }
            return new com.android.internal.org.bouncycastle.math.ec.AbstractECLookupTable() { // from class: com.android.internal.org.bouncycastle.math.ec.ECCurve.F2m.1
                @Override // com.android.internal.org.bouncycastle.math.ec.ECLookupTable
                public int getSize() {
                    return i2;
                }

                @Override // com.android.internal.org.bouncycastle.math.ec.ECLookupTable
                public com.android.internal.org.bouncycastle.math.ec.ECPoint lookup(int i7) {
                    long[] create64 = com.android.internal.org.bouncycastle.math.raw.Nat.create64(i3);
                    long[] create642 = com.android.internal.org.bouncycastle.math.raw.Nat.create64(i3);
                    int i8 = 0;
                    for (int i9 = 0; i9 < i2; i9++) {
                        long j = ((i9 ^ i7) - 1) >> 31;
                        for (int i10 = 0; i10 < i3; i10++) {
                            create64[i10] = create64[i10] ^ (jArr[i8 + i10] & j);
                            create642[i10] = create642[i10] ^ (jArr[(i3 + i8) + i10] & j);
                        }
                        i8 += i3 * 2;
                    }
                    return createPoint(create64, create642);
                }

                @Override // com.android.internal.org.bouncycastle.math.ec.AbstractECLookupTable, com.android.internal.org.bouncycastle.math.ec.ECLookupTable
                public com.android.internal.org.bouncycastle.math.ec.ECPoint lookupVar(int i7) {
                    long[] create64 = com.android.internal.org.bouncycastle.math.raw.Nat.create64(i3);
                    long[] create642 = com.android.internal.org.bouncycastle.math.raw.Nat.create64(i3);
                    int i8 = i7 * i3 * 2;
                    for (int i9 = 0; i9 < i3; i9++) {
                        create64[i9] = jArr[i8 + i9];
                        create642[i9] = jArr[i3 + i8 + i9];
                    }
                    return createPoint(create64, create642);
                }

                private com.android.internal.org.bouncycastle.math.ec.ECPoint createPoint(long[] jArr2, long[] jArr3) {
                    return com.android.internal.org.bouncycastle.math.ec.ECCurve.F2m.this.createRawPoint(new com.android.internal.org.bouncycastle.math.ec.ECFieldElement.F2m(com.android.internal.org.bouncycastle.math.ec.ECCurve.F2m.this.m, iArr, new com.android.internal.org.bouncycastle.math.ec.LongArray(jArr2)), new com.android.internal.org.bouncycastle.math.ec.ECFieldElement.F2m(com.android.internal.org.bouncycastle.math.ec.ECCurve.F2m.this.m, iArr, new com.android.internal.org.bouncycastle.math.ec.LongArray(jArr3)));
                }
            };
        }
    }
}
