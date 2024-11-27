package com.android.internal.org.bouncycastle.math.ec.custom.sec;

/* loaded from: classes4.dex */
public class SecP224R1Point extends com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractFp {
    SecP224R1Point(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2) {
        super(eCCurve, eCFieldElement, eCFieldElement2);
    }

    SecP224R1Point(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2, com.android.internal.org.bouncycastle.math.ec.ECFieldElement[] eCFieldElementArr) {
        super(eCCurve, eCFieldElement, eCFieldElement2, eCFieldElementArr);
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
    protected com.android.internal.org.bouncycastle.math.ec.ECPoint detach() {
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Point(null, getAffineXCoord(), getAffineYCoord());
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
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement secP224R1FieldElement = (com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement) this.x;
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement secP224R1FieldElement2 = (com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement) this.y;
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement secP224R1FieldElement3 = (com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement) eCPoint.getXCoord();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement secP224R1FieldElement4 = (com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement) eCPoint.getYCoord();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement secP224R1FieldElement5 = (com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement) this.zs[0];
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement secP224R1FieldElement6 = (com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement) eCPoint.getZCoord(0);
        int[] createExt = com.android.internal.org.bouncycastle.math.raw.Nat224.createExt();
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        int[] create2 = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        int[] create3 = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        boolean isOne = secP224R1FieldElement5.isOne();
        if (isOne) {
            iArr = secP224R1FieldElement3.x;
            iArr2 = secP224R1FieldElement4.x;
        } else {
            com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.square(secP224R1FieldElement5.x, create2);
            com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.multiply(create2, secP224R1FieldElement3.x, create);
            com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.multiply(create2, secP224R1FieldElement5.x, create2);
            com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.multiply(create2, secP224R1FieldElement4.x, create2);
            iArr = create;
            iArr2 = create2;
        }
        boolean isOne2 = secP224R1FieldElement6.isOne();
        if (isOne2) {
            iArr3 = secP224R1FieldElement.x;
            iArr4 = secP224R1FieldElement2.x;
        } else {
            com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.square(secP224R1FieldElement6.x, create3);
            com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.multiply(create3, secP224R1FieldElement.x, createExt);
            com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.multiply(create3, secP224R1FieldElement6.x, create3);
            com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.multiply(create3, secP224R1FieldElement2.x, create3);
            iArr3 = createExt;
            iArr4 = create3;
        }
        int[] create4 = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.subtract(iArr3, iArr, create4);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.subtract(iArr4, iArr2, create);
        if (com.android.internal.org.bouncycastle.math.raw.Nat224.isZero(create4)) {
            if (com.android.internal.org.bouncycastle.math.raw.Nat224.isZero(create)) {
                return twice();
            }
            return curve.getInfinity();
        }
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.square(create4, create2);
        int[] create5 = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.multiply(create2, create4, create5);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.multiply(create2, iArr3, create2);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.negate(create5, create5);
        com.android.internal.org.bouncycastle.math.raw.Nat224.mul(iArr4, create5, createExt);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.reduce32(com.android.internal.org.bouncycastle.math.raw.Nat224.addBothTo(create2, create2, create5), create5);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement secP224R1FieldElement7 = new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement(create3);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.square(create, secP224R1FieldElement7.x);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.subtract(secP224R1FieldElement7.x, create5, secP224R1FieldElement7.x);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement secP224R1FieldElement8 = new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement(create5);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.subtract(create2, secP224R1FieldElement7.x, secP224R1FieldElement8.x);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.multiplyAddToExt(secP224R1FieldElement8.x, create, createExt);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.reduce(createExt, secP224R1FieldElement8.x);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement secP224R1FieldElement9 = new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement(create4);
        if (!isOne) {
            com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.multiply(secP224R1FieldElement9.x, secP224R1FieldElement5.x, secP224R1FieldElement9.x);
        }
        if (!isOne2) {
            com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.multiply(secP224R1FieldElement9.x, secP224R1FieldElement6.x, secP224R1FieldElement9.x);
        }
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Point(curve, secP224R1FieldElement7, secP224R1FieldElement8, new com.android.internal.org.bouncycastle.math.ec.ECFieldElement[]{secP224R1FieldElement9});
    }

    @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
    public com.android.internal.org.bouncycastle.math.ec.ECPoint twice() {
        if (isInfinity()) {
            return this;
        }
        com.android.internal.org.bouncycastle.math.ec.ECCurve curve = getCurve();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement secP224R1FieldElement = (com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement) this.y;
        if (secP224R1FieldElement.isZero()) {
            return curve.getInfinity();
        }
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement secP224R1FieldElement2 = (com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement) this.x;
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement secP224R1FieldElement3 = (com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement) this.zs[0];
        int[] create = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        int[] create2 = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        int[] create3 = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.square(secP224R1FieldElement.x, create3);
        int[] create4 = com.android.internal.org.bouncycastle.math.raw.Nat224.create();
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.square(create3, create4);
        boolean isOne = secP224R1FieldElement3.isOne();
        int[] iArr = secP224R1FieldElement3.x;
        if (!isOne) {
            com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.square(secP224R1FieldElement3.x, create2);
            iArr = create2;
        }
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.subtract(secP224R1FieldElement2.x, iArr, create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.add(secP224R1FieldElement2.x, iArr, create2);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.multiply(create2, create, create2);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.reduce32(com.android.internal.org.bouncycastle.math.raw.Nat224.addBothTo(create2, create2, create2), create2);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.multiply(create3, secP224R1FieldElement2.x, create3);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.reduce32(com.android.internal.org.bouncycastle.math.raw.Nat.shiftUpBits(7, create3, 2, 0), create3);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.reduce32(com.android.internal.org.bouncycastle.math.raw.Nat.shiftUpBits(7, create4, 3, 0, create), create);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement secP224R1FieldElement4 = new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement(create4);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.square(create2, secP224R1FieldElement4.x);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.subtract(secP224R1FieldElement4.x, create3, secP224R1FieldElement4.x);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.subtract(secP224R1FieldElement4.x, create3, secP224R1FieldElement4.x);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement secP224R1FieldElement5 = new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement(create3);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.subtract(create3, secP224R1FieldElement4.x, secP224R1FieldElement5.x);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.multiply(secP224R1FieldElement5.x, create2, secP224R1FieldElement5.x);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.subtract(secP224R1FieldElement5.x, create, secP224R1FieldElement5.x);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement secP224R1FieldElement6 = new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1FieldElement(create2);
        com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.twice(secP224R1FieldElement.x, secP224R1FieldElement6.x);
        if (!isOne) {
            com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Field.multiply(secP224R1FieldElement6.x, secP224R1FieldElement3.x, secP224R1FieldElement6.x);
        }
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Point(curve, secP224R1FieldElement4, secP224R1FieldElement5, new com.android.internal.org.bouncycastle.math.ec.ECFieldElement[]{secP224R1FieldElement6});
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
        return new com.android.internal.org.bouncycastle.math.ec.custom.sec.SecP224R1Point(this.curve, this.x, this.y.negate(), this.zs);
    }
}
