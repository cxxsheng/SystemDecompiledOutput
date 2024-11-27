package com.android.internal.org.bouncycastle.math.ec.custom.sec;

/* loaded from: classes4.dex */
public class SecP224R1FieldElement extends com.android.internal.org.bouncycastle.math.ec.ECFieldElement.AbstractFp {
    public static final java.math.BigInteger Q = new java.math.BigInteger(1, com.android.internal.org.bouncycastle.util.encoders.Hex.decodeStrict("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF000000000000000000000001"));
    protected int[] x;

    public SecP224R1FieldElement(java.math.BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.compareTo(Q) >= 0) {
            throw new java.lang.IllegalArgumentException("x value invalid for SecP224R1FieldElement");
        }
        this.x = com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.fromBigInteger(bigInteger);
    }

    public SecP224R1FieldElement() {
        this.x = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
    }

    protected SecP224R1FieldElement(int[] iArr) {
        this.x = iArr;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public boolean isZero() {
        return com.android.internal.org.bouncycastle.math.raw.Nat224.isZero(this.x);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public boolean isOne() {
        return com.android.internal.org.bouncycastle.math.raw.Nat224.isOne(this.x);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public boolean testBitZero() {
        return com.android.internal.org.bouncycastle.math.raw.Nat224.getBit(this.x, 0) == 1;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public java.math.BigInteger toBigInteger() {
        return com.android.internal.org.bouncycastle.math.raw.Nat224.toBigInteger(this.x);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public java.lang.String getFieldName() {
        return "SecP224R1Field";
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public int getFieldSize() {
        return Q.bitLength();
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement add(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.add(this.x, ((com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement) eCFieldElement).x, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement addOne() {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.addOne(this.x, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement subtract(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.subtract(this.x, ((com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement) eCFieldElement).x, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.multiply(this.x, ((com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement) eCFieldElement).x, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement divide(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.inv(((com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement) eCFieldElement).x, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.multiply(create, this.x, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement negate() {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.negate(this.x, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement square() {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.square(this.x, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement invert() {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.inv(this.x, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement sqrt() {
        int[] iArr = this.x;
        if (com.android.internal.org.bouncycastle.math.raw.Nat224.isZero(iArr) || com.android.internal.org.bouncycastle.math.raw.Nat224.isOne(iArr)) {
            return this;
        }
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.negate(iArr, create);
        int[] random = com.android.internal.org.bouncycastle.math.raw.Mod.random(com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.P);
        int[] create2 = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        if (!isSquare(iArr)) {
            return null;
        }
        while (!trySqrt(create, random, create2)) {
            com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.addOne(random, random);
        }
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.square(create2, random);
        if (com.android.internal.org.bouncycastle.math.raw.Nat224.eq(iArr, random)) {
            return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement(create2);
        }
        return null;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement)) {
            return false;
        }
        return com.android.internal.org.bouncycastle.math.raw.Nat224.eq(this.x, ((com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement) obj).x);
    }

    public int hashCode() {
        return Q.hashCode() ^ com.android.internal.org.bouncycastle.util.Arrays.hashCode(this.x, 0, 7);
    }

    private static boolean isSquare(int[] iArr) {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        int[] create2 = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        com.android.internal.org.bouncycastle.math.raw.Nat224.copy(iArr, create);
        for (int i = 0; i < 7; i++) {
            com.android.internal.org.bouncycastle.math.raw.Nat224.copy(create, create2);
            com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.squareN(create, 1 << i, create);
            com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.multiply(create, create2, create);
        }
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.squareN(create, 95, create);
        return com.android.internal.org.bouncycastle.math.raw.Nat224.isOne(create);
    }

    private static void RM(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int[] iArr5, int[] iArr6, int[] iArr7) {
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.multiply(iArr5, iArr3, iArr7);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.multiply(iArr7, iArr, iArr7);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.multiply(iArr4, iArr2, iArr6);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.add(iArr6, iArr7, iArr6);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.multiply(iArr4, iArr3, iArr7);
        com.android.internal.org.bouncycastle.math.raw.Nat224.copy(iArr6, iArr4);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.multiply(iArr5, iArr2, iArr5);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.add(iArr5, iArr7, iArr5);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.square(iArr5, iArr6);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.multiply(iArr6, iArr, iArr6);
    }

    private static void RP(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, int[] iArr5) {
        com.android.internal.org.bouncycastle.math.raw.Nat224.copy(iArr, iArr4);
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        int[] create2 = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        for (int i = 0; i < 7; i++) {
            com.android.internal.org.bouncycastle.math.raw.Nat224.copy(iArr2, create);
            com.android.internal.org.bouncycastle.math.raw.Nat224.copy(iArr3, create2);
            int i2 = 1 << i;
            while (true) {
                i2--;
                if (i2 >= 0) {
                    RS(iArr2, iArr3, iArr4, iArr5);
                }
            }
            RM(iArr, create, create2, iArr2, iArr3, iArr4, iArr5);
        }
    }

    private static void RS(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4) {
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.multiply(iArr2, iArr, iArr2);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.twice(iArr2, iArr2);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.square(iArr, iArr4);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.add(iArr3, iArr4, iArr);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.multiply(iArr3, iArr4, iArr3);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.reduce32(com.android.internal.org.bouncycastle.math.raw.Nat.shiftUpBits(7, iArr3, 2, 0), iArr3);
    }

    private static boolean trySqrt(int[] iArr, int[] iArr2, int[] iArr3) {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        com.android.internal.org.bouncycastle.math.raw.Nat224.copy(iArr2, create);
        int[] create2 = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        create2[0] = 1;
        int[] create3 = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        RP(iArr, create, create2, create3, iArr3);
        int[] create4 = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        int[] create5 = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        for (int i = 1; i < 96; i++) {
            com.android.internal.org.bouncycastle.math.raw.Nat224.copy(create, create4);
            com.android.internal.org.bouncycastle.math.raw.Nat224.copy(create2, create5);
            RS(create, create2, create3, iArr3);
            if (com.android.internal.org.bouncycastle.math.raw.Nat224.isZero(create)) {
                com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.inv(create5, iArr3);
                com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.multiply(iArr3, create4, iArr3);
                return true;
            }
        }
        return false;
    }
}
