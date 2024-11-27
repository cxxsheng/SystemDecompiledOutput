package com.android.internal.org.bouncycastle.math.ec.custom.sec;

/* loaded from: classes4.dex */
public class SecP384R1Point extends com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractFp {
    SecP384R1Point(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2) {
        super(eCCurve, eCFieldElement, eCFieldElement2);
    }

    SecP384R1Point(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2, com.android.internal.org.bouncycastle.math.ec.ECFieldElement[] eCFieldElementArr) {
        super(eCCurve, eCFieldElement, eCFieldElement2, eCFieldElementArr);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
    protected com.android.internal.org.bouncycastle.math.ec.ECPoint detach() {
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Point(null, getAffineXCoord(), getAffineYCoord());
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
    public com.android.internal.org.bouncycastle.math.ec.ECPoint add(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint) {
        int[] iArr;
        int[] iArr2;
        int[] iArr3;
        int[] iArr4;
        if (isInfinity()) {
            return eCPoint;
        }
        if (eCPoint.isInfinity()) {
            return this;
        }
        if (this == eCPoint) {
            return twice();
        }
        com.android.internal.org.bouncycastle.math.ec.ECCurve curve = getCurve();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement secP384R1FieldElement = (com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement) this.x;
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement secP384R1FieldElement2 = (com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement) this.y;
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement secP384R1FieldElement3 = (com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement) eCPoint.getXCoord();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement secP384R1FieldElement4 = (com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement) eCPoint.getYCoord();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement secP384R1FieldElement5 = (com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement) this.zs[0];
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement secP384R1FieldElement6 = (com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement) eCPoint.getZCoord(0);
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat.create(24);
        int[] create2 = com.android.internal.org.bouncycastle.math.raw.Nat.create(24);
        int[] create3 = com.android.internal.org.bouncycastle.math.raw.Nat.create(12);
        int[] create4 = com.android.internal.org.bouncycastle.math.raw.Nat.create(12);
        boolean isOne = secP384R1FieldElement5.isOne();
        if (isOne) {
            iArr = secP384R1FieldElement3.x;
            iArr2 = secP384R1FieldElement4.x;
        } else {
            com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.square(secP384R1FieldElement5.x, create3);
            com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.multiply(create3, secP384R1FieldElement3.x, create2);
            com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.multiply(create3, secP384R1FieldElement5.x, create3);
            com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.multiply(create3, secP384R1FieldElement4.x, create3);
            iArr = create2;
            iArr2 = create3;
        }
        boolean isOne2 = secP384R1FieldElement6.isOne();
        if (isOne2) {
            iArr3 = secP384R1FieldElement.x;
            iArr4 = secP384R1FieldElement2.x;
        } else {
            com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.square(secP384R1FieldElement6.x, create4);
            com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.multiply(create4, secP384R1FieldElement.x, create);
            com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.multiply(create4, secP384R1FieldElement6.x, create4);
            com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.multiply(create4, secP384R1FieldElement2.x, create4);
            iArr3 = create;
            iArr4 = create4;
        }
        int[] create5 = com.android.internal.org.bouncycastle.math.raw.Nat.create(12);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.subtract(iArr3, iArr, create5);
        int[] create6 = com.android.internal.org.bouncycastle.math.raw.Nat.create(12);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.subtract(iArr4, iArr2, create6);
        if (com.android.internal.org.bouncycastle.math.raw.Nat.isZero(12, create5)) {
            if (com.android.internal.org.bouncycastle.math.raw.Nat.isZero(12, create6)) {
                return twice();
            }
            return curve.getInfinity();
        }
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.square(create5, create3);
        int[] create7 = com.android.internal.org.bouncycastle.math.raw.Nat.create(12);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.multiply(create3, create5, create7);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.multiply(create3, iArr3, create3);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.negate(create7, create7);
        com.android.internal.org.bouncycastle.math.raw.Nat384.mul(iArr4, create7, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.reduce32(com.android.internal.org.bouncycastle.math.raw.Nat.addBothTo(12, create3, create3, create7), create7);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement secP384R1FieldElement7 = new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement(create4);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.square(create6, secP384R1FieldElement7.x);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.subtract(secP384R1FieldElement7.x, create7, secP384R1FieldElement7.x);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement secP384R1FieldElement8 = new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement(create7);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.subtract(create3, secP384R1FieldElement7.x, secP384R1FieldElement8.x);
        com.android.internal.org.bouncycastle.math.raw.Nat384.mul(secP384R1FieldElement8.x, create6, create2);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.addExt(create, create2, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.reduce(create, secP384R1FieldElement8.x);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement secP384R1FieldElement9 = new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement(create5);
        if (!isOne) {
            com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.multiply(secP384R1FieldElement9.x, secP384R1FieldElement5.x, secP384R1FieldElement9.x);
        }
        if (!isOne2) {
            com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.multiply(secP384R1FieldElement9.x, secP384R1FieldElement6.x, secP384R1FieldElement9.x);
        }
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Point(curve, secP384R1FieldElement7, secP384R1FieldElement8, new com.android.internal.org.bouncycastle.math.ec.ECFieldElement[]{secP384R1FieldElement9});
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
    public com.android.internal.org.bouncycastle.math.ec.ECPoint twice() {
        if (isInfinity()) {
            return this;
        }
        com.android.internal.org.bouncycastle.math.ec.ECCurve curve = getCurve();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement secP384R1FieldElement = (com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement) this.y;
        if (secP384R1FieldElement.isZero()) {
            return curve.getInfinity();
        }
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement secP384R1FieldElement2 = (com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement) this.x;
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement secP384R1FieldElement3 = (com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement) this.zs[0];
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat.create(12);
        int[] create2 = com.android.internal.org.bouncycastle.math.raw.Nat.create(12);
        int[] create3 = com.android.internal.org.bouncycastle.math.raw.Nat.create(12);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.square(secP384R1FieldElement.x, create3);
        int[] create4 = com.android.internal.org.bouncycastle.math.raw.Nat.create(12);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.square(create3, create4);
        boolean isOne = secP384R1FieldElement3.isOne();
        int[] iArr = secP384R1FieldElement3.x;
        if (!isOne) {
            com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.square(secP384R1FieldElement3.x, create2);
            iArr = create2;
        }
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.subtract(secP384R1FieldElement2.x, iArr, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.add(secP384R1FieldElement2.x, iArr, create2);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.multiply(create2, create, create2);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.reduce32(com.android.internal.org.bouncycastle.math.raw.Nat.addBothTo(12, create2, create2, create2), create2);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.multiply(create3, secP384R1FieldElement2.x, create3);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.reduce32(com.android.internal.org.bouncycastle.math.raw.Nat.shiftUpBits(12, create3, 2, 0), create3);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.reduce32(com.android.internal.org.bouncycastle.math.raw.Nat.shiftUpBits(12, create4, 3, 0, create), create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement secP384R1FieldElement4 = new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement(create4);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.square(create2, secP384R1FieldElement4.x);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.subtract(secP384R1FieldElement4.x, create3, secP384R1FieldElement4.x);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.subtract(secP384R1FieldElement4.x, create3, secP384R1FieldElement4.x);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement secP384R1FieldElement5 = new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement(create3);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.subtract(create3, secP384R1FieldElement4.x, secP384R1FieldElement5.x);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.multiply(secP384R1FieldElement5.x, create2, secP384R1FieldElement5.x);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.subtract(secP384R1FieldElement5.x, create, secP384R1FieldElement5.x);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement secP384R1FieldElement6 = new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1FieldElement(create2);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.twice(secP384R1FieldElement.x, secP384R1FieldElement6.x);
        if (!isOne) {
            com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Field.multiply(secP384R1FieldElement6.x, secP384R1FieldElement3.x, secP384R1FieldElement6.x);
        }
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Point(curve, secP384R1FieldElement4, secP384R1FieldElement5, new com.android.internal.org.bouncycastle.math.ec.ECFieldElement[]{secP384R1FieldElement6});
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
    public com.android.internal.org.bouncycastle.math.ec.ECPoint twicePlus(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint) {
        if (this == eCPoint) {
            return threeTimes();
        }
        if (isInfinity()) {
            return eCPoint;
        }
        if (eCPoint.isInfinity()) {
            return twice();
        }
        if (this.y.isZero()) {
            return eCPoint;
        }
        return twice().add(eCPoint);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
    public com.android.internal.org.bouncycastle.math.ec.ECPoint threeTimes() {
        if (isInfinity() || this.y.isZero()) {
            return this;
        }
        return twice().add(this);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
    public com.android.internal.org.bouncycastle.math.ec.ECPoint negate() {
        if (isInfinity()) {
            return this;
        }
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP384R1Point(this.curve, this.x, this.y.negate(), this.zs);
    }
}
