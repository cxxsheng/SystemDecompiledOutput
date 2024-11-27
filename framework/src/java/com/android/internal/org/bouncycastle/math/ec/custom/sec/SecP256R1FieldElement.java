package com.android.internal.org.bouncycastle.math.ec.custom.sec;

/* loaded from: classes4.dex */
public class SecP256R1FieldElement extends com.android.internal.org.bouncycastle.math.ec.ECFieldElement.AbstractFp {
    public static final java.math.BigInteger Q = new java.math.BigInteger(1, com.android.internal.org.bouncycastle.util.encoders.Hex.decodeStrict("FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFF"));
    protected int[] x;

    public SecP256R1FieldElement(java.math.BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.compareTo(Q) >= 0) {
            throw new java.lang.IllegalArgumentException("x value invalid for SecP256R1FieldElement");
        }
        this.x = com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1Field.fromBigInteger(bigInteger);
    }

    public SecP256R1FieldElement() {
        this.x = com.android.internal.org.bouncycastle.math.raw.Nat256.create();
    }

    protected SecP256R1FieldElement(int[] iArr) {
        this.x = iArr;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public boolean isZero() {
        return com.android.internal.org.bouncycastle.math.raw.Nat256.isZero(this.x);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public boolean isOne() {
        return com.android.internal.org.bouncycastle.math.raw.Nat256.isOne(this.x);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public boolean testBitZero() {
        return com.android.internal.org.bouncycastle.math.raw.Nat256.getBit(this.x, 0) == 1;
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public java.math.BigInteger toBigInteger() {
        return com.android.internal.org.bouncycastle.math.raw.Nat256.toBigInteger(this.x);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public java.lang.String getFieldName() {
        return "SecP256R1Field";
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public int getFieldSize() {
        return Q.bitLength();
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement add(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat256.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1Field.add(this.x, ((com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1FieldElement) eCFieldElement).x, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement addOne() {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat256.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1Field.addOne(this.x, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement subtract(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat256.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1Field.subtract(this.x, ((com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1FieldElement) eCFieldElement).x, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat256.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1Field.multiply(this.x, ((com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1FieldElement) eCFieldElement).x, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement divide(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat256.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1Field.inv(((com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1FieldElement) eCFieldElement).x, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1Field.multiply(create, this.x, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement negate() {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat256.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1Field.negate(this.x, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement square() {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat256.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1Field.square(this.x, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement invert() {
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat256.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1Field.inv(this.x, create);
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1FieldElement(create);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement sqrt() {
        int[] iArr = this.x;
        if (com.android.internal.org.bouncycastle.math.raw.Nat256.isZero(iArr) || com.android.internal.org.bouncycastle.math.raw.Nat256.isOne(iArr)) {
            return this;
        }
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat256.create();
        int[] create2 = com.android.internal.org.bouncycastle.math.raw.Nat256.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1Field.square(iArr, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1Field.multiply(create, iArr, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1Field.squareN(create, 2, create2);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1Field.multiply(create2, create, create2);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1Field.squareN(create2, 4, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1Field.multiply(create, create2, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1Field.squareN(create, 8, create2);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1Field.multiply(create2, create, create2);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1Field.squareN(create2, 16, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1Field.multiply(create, create2, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1Field.squareN(create, 32, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1Field.multiply(create, iArr, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1Field.squareN(create, 96, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1Field.multiply(create, iArr, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1Field.squareN(create, 94, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1Field.square(create, create2);
        if (com.android.internal.org.bouncycastle.math.raw.Nat256.eq(iArr, create2)) {
            return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1FieldElement(create);
        }
        return null;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1FieldElement)) {
            return false;
        }
        return com.android.internal.org.bouncycastle.math.raw.Nat256.eq(this.x, ((com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP256R1FieldElement) obj).x);
    }

    public int hashCode() {
        return Q.hashCode() ^ com.android.internal.org.bouncycastle.util.Arrays.hashCode(this.x, 0, 8);
    }
}