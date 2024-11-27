package com.android.internal.org.bouncycastle.math.ec.custom.sec;

/* loaded from: classes4.dex */
public class SecP224K1FieldElement extends com.android.internal.org.bouncycastle.math.ec.ECFieldElement.AbstractFp {
    protected int[] x;
    public static final java.math.BigInteger Q = new java.math.BigInteger(1, com.android.internal.org.bouncycastle.util.encoders.Hex.decodeStrict("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFE56D"));
    private static final int[] PRECOMP_POW2 = {868209154, -587542221, 579297866, -1014948952, -1470801668, 514782679, -1897982644};

    public SecP224K1FieldElement(java.math.BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.compareTo(Q) >= 0) {
            throw new java.lang.IllegalArgumentException("x value invalid for SecP224K1FieldElement");
        }
        this.x = com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.fromBigInteger(bigInteger);
    }

    public SecP224K1FieldElement() {
        this.x = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
    }

    protected SecP224K1FieldElement(int[] iArr) {
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
        return "SecP224K1Field";
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public int getFieldSize() {
        return Q.bitLength();
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement add(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.add(this.x, ((com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1FieldElement) eCFieldElement).x, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement addOne() {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.addOne(this.x, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement subtract(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.subtract(this.x, ((com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1FieldElement) eCFieldElement).x, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.multiply(this.x, ((com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1FieldElement) eCFieldElement).x, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement divide(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.inv(((com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1FieldElement) eCFieldElement).x, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.multiply(create, this.x, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement negate() {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.negate(this.x, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement square() {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.square(this.x, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement invert() {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.inv(this.x, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement sqrt() {
        int[] iArr = this.x;
        if (com.android.internal.org.bouncycastle.math.raw.Nat224.isZero(iArr) || com.android.internal.org.bouncycastle.math.raw.Nat224.isOne(iArr)) {
            return this;
        }
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.square(iArr, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.multiply(create, iArr, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.square(create, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.multiply(create, iArr, create);
        int[] create2 = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.square(create, create2);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.multiply(create2, iArr, create2);
        int[] create3 = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.squareN(create2, 4, create3);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.multiply(create3, create2, create3);
        int[] create4 = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.squareN(create3, 3, create4);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.multiply(create4, create, create4);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.squareN(create4, 8, create4);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.multiply(create4, create3, create4);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.squareN(create4, 4, create3);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.multiply(create3, create2, create3);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.squareN(create3, 19, create2);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.multiply(create2, create4, create2);
        int[] create5 = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.squareN(create2, 42, create5);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.multiply(create5, create2, create5);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.squareN(create5, 23, create2);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.multiply(create2, create3, create2);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.squareN(create2, 84, create3);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.multiply(create3, create5, create3);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.squareN(create3, 20, create3);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.multiply(create3, create4, create3);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.squareN(create3, 3, create3);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.multiply(create3, iArr, create3);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.squareN(create3, 2, create3);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.multiply(create3, iArr, create3);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.squareN(create3, 4, create3);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.multiply(create3, create, create3);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.square(create3, create3);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.square(create3, create5);
        if (com.android.internal.org.bouncycastle.math.raw.Nat224.eq(iArr, create5)) {
            return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1FieldElement(create3);
        }
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.multiply(create3, PRECOMP_POW2, create3);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1Field.square(create3, create5);
        if (com.android.internal.org.bouncycastle.math.raw.Nat224.eq(iArr, create5)) {
            return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1FieldElement(create3);
        }
        return null;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1FieldElement)) {
            return false;
        }
        return com.android.internal.org.bouncycastle.math.raw.Nat224.eq(this.x, ((com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224K1FieldElement) obj).x);
    }

    public int hashCode() {
        return Q.hashCode() ^ com.android.internal.org.bouncycastle.util.Arrays.hashCode(this.x, 0, 7);
    }
}
