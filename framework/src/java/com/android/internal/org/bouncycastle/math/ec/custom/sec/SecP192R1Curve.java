package com.android.internal.org.bouncycastle.math.ec.custom.sec;

/* loaded from: classes4.dex */
public class SecP192R1Curve extends com.android.internal.org.bouncycastle.math.ec.ECCurve.AbstractFp {
    private static final int SECP192R1_DEFAULT_COORDS = 2;
    protected com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192R1Point infinity;
    public static final java.math.BigInteger q = com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192R1FieldElement.Q;
    private static final com.android.internal.org.bouncycastle.math.ec.ECFieldElement[] SECP192R1_AFFINE_ZS = {new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192R1FieldElement(com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE)};

    public SecP192R1Curve() {
        super(q);
        this.infinity = new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192R1Point(this, null, null);
        this.a = fromBigInteger(new java.math.BigInteger(1, com.android.internal.org.bouncycastle.util.encoders.Hex.decodeStrict("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFFFFFFFFFC")));
        this.b = fromBigInteger(new java.math.BigInteger(1, com.android.internal.org.bouncycastle.util.encoders.Hex.decodeStrict("64210519E59C80E70FA7E9AB72243049FEB8DEECC146B9B1")));
        this.order = new java.math.BigInteger(1, com.android.internal.org.bouncycastle.util.encoders.Hex.decodeStrict("FFFFFFFFFFFFFFFFFFFFFFFF99DEF836146BC9B1B4D22831"));
        this.cofactor = java.math.BigInteger.valueOf(1L);
        this.coord = 2;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    protected com.android.internal.org.bouncycastle.math.ec.ECCurve cloneCurve() {
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192R1Curve();
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    public boolean supportsCoordinateSystem(int i) {
        switch (i) {
            case 2:
                return true;
            default:
                return false;
        }
    }

    public java.math.BigInteger getQ() {
        return q;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    public int getFieldSize() {
        return q.bitLength();
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement fromBigInteger(java.math.BigInteger bigInteger) {
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192R1FieldElement(bigInteger);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    protected com.android.internal.org.bouncycastle.math.ec.ECPoint createRawPoint(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2) {
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192R1Point(this, eCFieldElement, eCFieldElement2);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    protected com.android.internal.org.bouncycastle.math.ec.ECPoint createRawPoint(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2, com.android.internal.org.bouncycastle.math.ec.ECFieldElement[] eCFieldElementArr) {
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192R1Point(this, eCFieldElement, eCFieldElement2, eCFieldElementArr);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    public com.android.internal.org.bouncycastle.math.ec.ECPoint getInfinity() {
        return this.infinity;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve
    public com.android.internal.org.bouncycastle.math.ec.ECLookupTable createCacheSafeLookupTable(com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr, int i, final int i2) {
        final int[] iArr = new int[i2 * 6 * 2];
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint = eCPointArr[i + i4];
            com.android.internal.org.bouncycastle.math.raw.Nat192.copy(((com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192R1FieldElement) eCPoint.getRawXCoord()).x, 0, iArr, i3);
            int i5 = i3 + 6;
            com.android.internal.org.bouncycastle.math.raw.Nat192.copy(((com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192R1FieldElement) eCPoint.getRawYCoord()).x, 0, iArr, i5);
            i3 = i5 + 6;
        }
        return new com.android.internal.org.bouncycastle.math.ec.AbstractECLookupTable() { // from class: com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192R1Curve.1
            @Override // com.android.internal.org.bouncycastle.math.ec.ECLookupTable
            public int getSize() {
                return i2;
            }

            @Override // com.android.internal.org.bouncycastle.math.ec.ECLookupTable
            public com.android.internal.org.bouncycastle.math.ec.ECPoint lookup(int i6) {
                int[] create = com.android.internal.org.bouncycastle.math.raw.Nat192.create();
                int[] create2 = com.android.internal.org.bouncycastle.math.raw.Nat192.create();
                int i7 = 0;
                for (int i8 = 0; i8 < i2; i8++) {
                    int i9 = ((i8 ^ i6) - 1) >> 31;
                    for (int i10 = 0; i10 < 6; i10++) {
                        create[i10] = create[i10] ^ (iArr[i7 + i10] & i9);
                        create2[i10] = create2[i10] ^ (iArr[(i7 + 6) + i10] & i9);
                    }
                    i7 += 12;
                }
                return createPoint(create, create2);
            }

            @Override // com.android.internal.org.bouncycastle.math.ec.AbstractECLookupTable, com.android.internal.org.bouncycastle.math.ec.ECLookupTable
            public com.android.internal.org.bouncycastle.math.ec.ECPoint lookupVar(int i6) {
                int[] create = com.android.internal.org.bouncycastle.math.raw.Nat192.create();
                int[] create2 = com.android.internal.org.bouncycastle.math.raw.Nat192.create();
                int i7 = i6 * 6 * 2;
                for (int i8 = 0; i8 < 6; i8++) {
                    create[i8] = iArr[i7 + i8];
                    create2[i8] = iArr[i7 + 6 + i8];
                }
                return createPoint(create, create2);
            }

            private com.android.internal.org.bouncycastle.math.ec.ECPoint createPoint(int[] iArr2, int[] iArr3) {
                return com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192R1Curve.this.createRawPoint(new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192R1FieldElement(iArr2), new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192R1FieldElement(iArr3), com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192R1Curve.SECP192R1_AFFINE_ZS);
            }
        };
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve.AbstractFp, com.android.internal.org.bouncycastle.math.ec.ECCurve
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement randomFieldElement(java.security.SecureRandom secureRandom) {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat192.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192R1Field.random(secureRandom, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECCurve.AbstractFp, com.android.internal.org.bouncycastle.math.ec.ECCurve
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement randomFieldElementMult(java.security.SecureRandom secureRandom) {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat192.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192R1Field.randomMult(secureRandom, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192R1FieldElement(create);
    }
}
