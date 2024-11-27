package com.android.internal.org.bouncycastle.math.ec.custom.sec;

/* loaded from: classes4.dex */
public class SecP192K1FieldElement extends com.android.internal.org.bouncycastle.math.ec.ECFieldElement.AbstractFp {
    public static final java.math.BigInteger Q = new java.math.BigInteger(1, com.android.internal.org.bouncycastle.util.encoders.Hex.decodeStrict("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFEE37"));
    protected int[] x;

    public SecP192K1FieldElement(java.math.BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.compareTo(Q) >= 0) {
            throw new java.lang.IllegalArgumentException("x value invalid for SecP192K1FieldElement");
        }
        this.x = com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.fromBigInteger(bigInteger);
    }

    public SecP192K1FieldElement() {
        this.x = com.android.internal.org.bouncycastle.math.raw.Nat192.create();
    }

    protected SecP192K1FieldElement(int[] iArr) {
        this.x = iArr;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public boolean isZero() {
        return com.android.internal.org.bouncycastle.math.raw.Nat192.isZero(this.x);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public boolean isOne() {
        return com.android.internal.org.bouncycastle.math.raw.Nat192.isOne(this.x);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public boolean testBitZero() {
        return com.android.internal.org.bouncycastle.math.raw.Nat192.getBit(this.x, 0) == 1;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public java.math.BigInteger toBigInteger() {
        return com.android.internal.org.bouncycastle.math.raw.Nat192.toBigInteger(this.x);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public java.lang.String getFieldName() {
        return "SecP192K1Field";
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public int getFieldSize() {
        return Q.bitLength();
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement add(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat192.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.add(this.x, ((com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1FieldElement) eCFieldElement).x, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement addOne() {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat192.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.addOne(this.x, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement subtract(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat192.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.subtract(this.x, ((com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1FieldElement) eCFieldElement).x, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat192.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.multiply(this.x, ((com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1FieldElement) eCFieldElement).x, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement divide(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat192.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.inv(((com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1FieldElement) eCFieldElement).x, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.multiply(create, this.x, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement negate() {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat192.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.negate(this.x, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement square() {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat192.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.square(this.x, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement invert() {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat192.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.inv(this.x, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement sqrt() {
        int[] iArr = this.x;
        if (com.android.internal.org.bouncycastle.math.raw.Nat192.isZero(iArr) || com.android.internal.org.bouncycastle.math.raw.Nat192.isOne(iArr)) {
            return this;
        }
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat192.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.square(iArr, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.multiply(create, iArr, create);
        int[] create2 = com.android.internal.org.bouncycastle.math.raw.Nat192.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.square(create, create2);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.multiply(create2, iArr, create2);
        int[] create3 = com.android.internal.org.bouncycastle.math.raw.Nat192.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.squareN(create2, 3, create3);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.multiply(create3, create2, create3);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.squareN(create3, 2, create3);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.multiply(create3, create, create3);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.squareN(create3, 8, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.multiply(create, create3, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.squareN(create, 3, create3);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.multiply(create3, create2, create3);
        int[] create4 = com.android.internal.org.bouncycastle.math.raw.Nat192.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.squareN(create3, 16, create4);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.multiply(create4, create, create4);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.squareN(create4, 35, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.multiply(create, create4, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.squareN(create, 70, create4);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.multiply(create4, create, create4);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.squareN(create4, 19, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.multiply(create, create3, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.squareN(create, 20, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.multiply(create, create3, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.squareN(create, 4, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.multiply(create, create2, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.squareN(create, 6, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.multiply(create, create2, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.square(create, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1Field.square(create, create2);
        if (com.android.internal.org.bouncycastle.math.raw.Nat192.eq(iArr, create2)) {
            return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1FieldElement(create);
        }
        return null;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1FieldElement)) {
            return false;
        }
        return com.android.internal.org.bouncycastle.math.raw.Nat192.eq(this.x, ((com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP192K1FieldElement) obj).x);
    }

    public int hashCode() {
        return Q.hashCode() ^ com.android.internal.org.bouncycastle.util.Arrays.hashCode(this.x, 0, 6);
    }
}
