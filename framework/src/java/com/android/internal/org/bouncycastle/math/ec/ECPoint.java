package com.android.internal.org.bouncycastle.math.ec;

/* loaded from: classes4.dex */
public abstract class ECPoint {
    protected static final com.android.internal.org.bouncycastle.math.ec.ECFieldElement[] EMPTY_ZS = new com.android.internal.org.bouncycastle.math.ec.ECFieldElement[0];
    protected com.android.internal.org.bouncycastle.math.ec.ECCurve curve;
    protected java.util.Hashtable preCompTable;
    protected com.android.internal.org.bouncycastle.math.ec.ECFieldElement x;
    protected com.android.internal.org.bouncycastle.math.ec.ECFieldElement y;
    protected com.android.internal.org.bouncycastle.math.ec.ECFieldElement[] zs;

    public abstract com.android.internal.org.bouncycastle.math.ec.ECPoint add(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint);

    protected abstract com.android.internal.org.bouncycastle.math.ec.ECPoint detach();

    protected abstract boolean getCompressionYTilde();

    public abstract com.android.internal.org.bouncycastle.math.ec.ECPoint negate();

    protected abstract boolean satisfiesCurveEquation();

    public abstract com.android.internal.org.bouncycastle.math.ec.ECPoint subtract(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint);

    public abstract com.android.internal.org.bouncycastle.math.ec.ECPoint twice();

    protected static com.android.internal.org.bouncycastle.math.ec.ECFieldElement[] getInitialZCoords(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve) {
        int coordinateSystem = eCCurve == null ? 0 : eCCurve.getCoordinateSystem();
        switch (coordinateSystem) {
            case 0:
            case 5:
                return EMPTY_ZS;
            default:
                com.android.internal.org.bouncycastle.math.ec.ECFieldElement fromBigInteger = eCCurve.fromBigInteger(com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE);
                switch (coordinateSystem) {
                    case 1:
                    case 2:
                    case 6:
                        return new com.android.internal.org.bouncycastle.math.ec.ECFieldElement[]{fromBigInteger};
                    case 3:
                        return new com.android.internal.org.bouncycastle.math.ec.ECFieldElement[]{fromBigInteger, fromBigInteger, fromBigInteger};
                    case 4:
                        return new com.android.internal.org.bouncycastle.math.ec.ECFieldElement[]{fromBigInteger, eCCurve.getA()};
                    case 5:
                    default:
                        throw new java.lang.IllegalArgumentException("unknown coordinate system");
                }
        }
    }

    protected ECPoint(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2) {
        this(eCCurve, eCFieldElement, eCFieldElement2, getInitialZCoords(eCCurve));
    }

    protected ECPoint(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2, com.android.internal.org.bouncycastle.math.ec.ECFieldElement[] eCFieldElementArr) {
        this.preCompTable = null;
        this.curve = eCCurve;
        this.x = eCFieldElement;
        this.y = eCFieldElement2;
        this.zs = eCFieldElementArr;
    }

    protected boolean satisfiesOrder() {
        java.math.BigInteger order;
        return com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE.equals(this.curve.getCofactor()) || (order = this.curve.getOrder()) == null || com.android.internal.org.bouncycastle.math.ec.ECAlgorithms.referenceMultiply(this, order).isInfinity();
    }

    public final com.android.internal.org.bouncycastle.math.ec.ECPoint getDetachedPoint() {
        return normalize().detach();
    }

    public com.android.internal.org.bouncycastle.math.ec.ECCurve getCurve() {
        return this.curve;
    }

    protected int getCurveCoordinateSystem() {
        if (this.curve == null) {
            return 0;
        }
        return this.curve.getCoordinateSystem();
    }

    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement getAffineXCoord() {
        checkNormalized();
        return getXCoord();
    }

    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement getAffineYCoord() {
        checkNormalized();
        return getYCoord();
    }

    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement getXCoord() {
        return this.x;
    }

    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement getYCoord() {
        return this.y;
    }

    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement getZCoord(int i) {
        if (i < 0 || i >= this.zs.length) {
            return null;
        }
        return this.zs[i];
    }

    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement[] getZCoords() {
        int length = this.zs.length;
        if (length == 0) {
            return EMPTY_ZS;
        }
        com.android.internal.org.bouncycastle.math.ec.ECFieldElement[] eCFieldElementArr = new com.android.internal.org.bouncycastle.math.ec.ECFieldElement[length];
        java.lang.System.arraycopy(this.zs, 0, eCFieldElementArr, 0, length);
        return eCFieldElementArr;
    }

    public final com.android.internal.org.bouncycastle.math.ec.ECFieldElement getRawXCoord() {
        return this.x;
    }

    public final com.android.internal.org.bouncycastle.math.ec.ECFieldElement getRawYCoord() {
        return this.y;
    }

    protected final com.android.internal.org.bouncycastle.math.ec.ECFieldElement[] getRawZCoords() {
        return this.zs;
    }

    protected void checkNormalized() {
        if (!isNormalized()) {
            throw new java.lang.IllegalStateException("point not in normal form");
        }
    }

    public boolean isNormalized() {
        int curveCoordinateSystem = getCurveCoordinateSystem();
        return curveCoordinateSystem == 0 || curveCoordinateSystem == 5 || isInfinity() || this.zs[0].isOne();
    }

    public com.android.internal.org.bouncycastle.math.ec.ECPoint normalize() {
        if (isInfinity()) {
            return this;
        }
        switch (getCurveCoordinateSystem()) {
            case 0:
            case 5:
                return this;
            default:
                com.android.internal.org.bouncycastle.math.ec.ECFieldElement zCoord = getZCoord(0);
                if (zCoord.isOne()) {
                    return this;
                }
                if (this.curve == null) {
                    throw new java.lang.IllegalStateException("Detached points must be in affine coordinates");
                }
                com.android.internal.org.bouncycastle.math.ec.ECFieldElement randomFieldElementMult = this.curve.randomFieldElementMult(com.android.internal.org.bouncycastle.crypto.CryptoServicesRegistrar.getSecureRandom());
                return normalize(zCoord.multiply(randomFieldElementMult).invert().multiply(randomFieldElementMult));
        }
    }

    com.android.internal.org.bouncycastle.math.ec.ECPoint normalize(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
        switch (getCurveCoordinateSystem()) {
            case 1:
            case 6:
                return createScaledPoint(eCFieldElement, eCFieldElement);
            case 2:
            case 3:
            case 4:
                com.android.internal.org.bouncycastle.math.ec.ECFieldElement square = eCFieldElement.square();
                return createScaledPoint(square, square.multiply(eCFieldElement));
            case 5:
            default:
                throw new java.lang.IllegalStateException("not a projective coordinate system");
        }
    }

    protected com.android.internal.org.bouncycastle.math.ec.ECPoint createScaledPoint(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2) {
        return getCurve().createRawPoint(getRawXCoord().multiply(eCFieldElement), getRawYCoord().multiply(eCFieldElement2));
    }

    public boolean isInfinity() {
        return this.x == null || this.y == null || (this.zs.length > 0 && this.zs[0].isZero());
    }

    public boolean isValid() {
        return implIsValid(false, true);
    }

    boolean isValidPartial() {
        return implIsValid(false, false);
    }

    boolean implIsValid(final boolean z, final boolean z2) {
        if (isInfinity()) {
            return true;
        }
        return !((com.android.internal.org.bouncycastle.math.ec.ValidityPrecompInfo) getCurve().precompute(this, "bc_validity", new com.android.internal.org.bouncycastle.math.ec.PreCompCallback() { // from class: com.android.internal.org.bouncycastle.math.ec.ECPoint.1
            @Override // com.android.internal.org.bouncycastle.math.ec.PreCompCallback
            public com.android.internal.org.bouncycastle.math.ec.PreCompInfo precompute(com.android.internal.org.bouncycastle.math.ec.PreCompInfo preCompInfo) {
                com.android.internal.org.bouncycastle.math.ec.ValidityPrecompInfo validityPrecompInfo = preCompInfo instanceof com.android.internal.org.bouncycastle.math.ec.ValidityPrecompInfo ? (com.android.internal.org.bouncycastle.math.ec.ValidityPrecompInfo) preCompInfo : null;
                if (validityPrecompInfo == null) {
                    validityPrecompInfo = new com.android.internal.org.bouncycastle.math.ec.ValidityPrecompInfo();
                }
                if (validityPrecompInfo.hasFailed()) {
                    return validityPrecompInfo;
                }
                if (!validityPrecompInfo.hasCurveEquationPassed()) {
                    if (!z && !com.android.internal.org.bouncycastle.math.ec.ECPoint.this.satisfiesCurveEquation()) {
                        validityPrecompInfo.reportFailed();
                        return validityPrecompInfo;
                    }
                    validityPrecompInfo.reportCurveEquationPassed();
                }
                if (z2 && !validityPrecompInfo.hasOrderPassed()) {
                    if (!com.android.internal.org.bouncycastle.math.ec.ECPoint.this.satisfiesOrder()) {
                        validityPrecompInfo.reportFailed();
                        return validityPrecompInfo;
                    }
                    validityPrecompInfo.reportOrderPassed();
                }
                return validityPrecompInfo;
            }
        })).hasFailed();
    }

    public com.android.internal.org.bouncycastle.math.ec.ECPoint scaleX(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
        if (isInfinity()) {
            return this;
        }
        return getCurve().createRawPoint(getRawXCoord().multiply(eCFieldElement), getRawYCoord(), getRawZCoords());
    }

    public com.android.internal.org.bouncycastle.math.ec.ECPoint scaleXNegateY(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
        if (isInfinity()) {
            return this;
        }
        return getCurve().createRawPoint(getRawXCoord().multiply(eCFieldElement), getRawYCoord().negate(), getRawZCoords());
    }

    public com.android.internal.org.bouncycastle.math.ec.ECPoint scaleY(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
        if (isInfinity()) {
            return this;
        }
        return getCurve().createRawPoint(getRawXCoord(), getRawYCoord().multiply(eCFieldElement), getRawZCoords());
    }

    public com.android.internal.org.bouncycastle.math.ec.ECPoint scaleYNegateX(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
        if (isInfinity()) {
            return this;
        }
        return getCurve().createRawPoint(getRawXCoord().negate(), getRawYCoord().multiply(eCFieldElement), getRawZCoords());
    }

    public boolean equals(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint) {
        boolean z;
        boolean z2;
        com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint2;
        if (eCPoint == null) {
            return false;
        }
        com.android.internal.org.bouncycastle.math.ec.ECCurve curve = getCurve();
        com.android.internal.org.bouncycastle.math.ec.ECCurve curve2 = eCPoint.getCurve();
        if (curve != null) {
            z = false;
        } else {
            z = true;
        }
        if (curve2 != null) {
            z2 = false;
        } else {
            z2 = true;
        }
        boolean isInfinity = isInfinity();
        boolean isInfinity2 = eCPoint.isInfinity();
        if (isInfinity || isInfinity2) {
            if (!isInfinity || !isInfinity2) {
                return false;
            }
            if (!z && !z2 && !curve.equals(curve2)) {
                return false;
            }
            return true;
        }
        if (z && z2) {
            eCPoint2 = this;
        } else if (z) {
            eCPoint = eCPoint.normalize();
            eCPoint2 = this;
        } else if (z2) {
            eCPoint2 = normalize();
        } else {
            if (!curve.equals(curve2)) {
                return false;
            }
            com.android.internal.org.bouncycastle.math.ec.ECPoint[] eCPointArr = {this, curve.importPoint(eCPoint)};
            curve.normalizeAll(eCPointArr);
            eCPoint2 = eCPointArr[0];
            eCPoint = eCPointArr[1];
        }
        if (!eCPoint2.getXCoord().equals(eCPoint.getXCoord()) || !eCPoint2.getYCoord().equals(eCPoint.getYCoord())) {
            return false;
        }
        return true;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof com.android.internal.org.bouncycastle.math.ec.ECPoint)) {
            return false;
        }
        return equals((com.android.internal.org.bouncycastle.math.ec.ECPoint) obj);
    }

    public int hashCode() {
        com.android.internal.org.bouncycastle.math.ec.ECCurve curve = getCurve();
        int i = curve == null ? 0 : ~curve.hashCode();
        if (!isInfinity()) {
            com.android.internal.org.bouncycastle.math.ec.ECPoint normalize = normalize();
            return (i ^ (normalize.getXCoord().hashCode() * 17)) ^ (normalize.getYCoord().hashCode() * 257);
        }
        return i;
    }

    public java.lang.String toString() {
        if (isInfinity()) {
            return "INF";
        }
        java.lang.StringBuffer stringBuffer = new java.lang.StringBuffer();
        stringBuffer.append('(');
        stringBuffer.append(getRawXCoord());
        stringBuffer.append(',');
        stringBuffer.append(getRawYCoord());
        for (int i = 0; i < this.zs.length; i++) {
            stringBuffer.append(',');
            stringBuffer.append(this.zs[i]);
        }
        stringBuffer.append(')');
        return stringBuffer.toString();
    }

    public byte[] getEncoded(boolean z) {
        if (isInfinity()) {
            return new byte[1];
        }
        com.android.internal.org.bouncycastle.math.ec.ECPoint normalize = normalize();
        byte[] encoded = normalize.getXCoord().getEncoded();
        if (z) {
            byte[] bArr = new byte[encoded.length + 1];
            bArr[0] = (byte) (normalize.getCompressionYTilde() ? 3 : 2);
            java.lang.System.arraycopy(encoded, 0, bArr, 1, encoded.length);
            return bArr;
        }
        byte[] encoded2 = normalize.getYCoord().getEncoded();
        byte[] bArr2 = new byte[encoded.length + encoded2.length + 1];
        bArr2[0] = 4;
        java.lang.System.arraycopy(encoded, 0, bArr2, 1, encoded.length);
        java.lang.System.arraycopy(encoded2, 0, bArr2, encoded.length + 1, encoded2.length);
        return bArr2;
    }

    public com.android.internal.org.bouncycastle.math.ec.ECPoint timesPow2(int i) {
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("'e' cannot be negative");
        }
        int i2 = i;
        com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint = this;
        while (true) {
            i2--;
            if (i2 >= 0) {
                eCPoint = eCPoint.twice();
            } else {
                return eCPoint;
            }
        }
    }

    public com.android.internal.org.bouncycastle.math.ec.ECPoint twicePlus(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint) {
        return twice().add(eCPoint);
    }

    public com.android.internal.org.bouncycastle.math.ec.ECPoint threeTimes() {
        return twicePlus(this);
    }

    public com.android.internal.org.bouncycastle.math.ec.ECPoint multiply(java.math.BigInteger bigInteger) {
        return getCurve().getMultiplier().multiply(this, bigInteger);
    }

    public static abstract class AbstractFp extends com.android.internal.org.bouncycastle.math.ec.ECPoint {
        protected AbstractFp(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2) {
            super(eCCurve, eCFieldElement, eCFieldElement2);
        }

        protected AbstractFp(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2, com.android.internal.org.bouncycastle.math.ec.ECFieldElement[] eCFieldElementArr) {
            super(eCCurve, eCFieldElement, eCFieldElement2, eCFieldElementArr);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        protected boolean getCompressionYTilde() {
            return getAffineYCoord().testBitZero();
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        protected boolean satisfiesCurveEquation() {
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement = this.x;
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2 = this.y;
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement a = this.curve.getA();
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement b = this.curve.getB();
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement square = eCFieldElement2.square();
            switch (getCurveCoordinateSystem()) {
                case 0:
                    break;
                case 1:
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement3 = this.zs[0];
                    if (!eCFieldElement3.isOne()) {
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement square2 = eCFieldElement3.square();
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply = eCFieldElement3.multiply(square2);
                        square = square.multiply(eCFieldElement3);
                        a = a.multiply(square2);
                        b = b.multiply(multiply);
                        break;
                    }
                    break;
                case 2:
                case 3:
                case 4:
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement4 = this.zs[0];
                    if (!eCFieldElement4.isOne()) {
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement square3 = eCFieldElement4.square();
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement square4 = square3.square();
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply2 = square3.multiply(square4);
                        a = a.multiply(square4);
                        b = b.multiply(multiply2);
                        break;
                    }
                    break;
                default:
                    throw new java.lang.IllegalStateException("unsupported coordinate system");
            }
            return square.equals(eCFieldElement.square().add(a).multiply(eCFieldElement).add(b));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public com.android.internal.org.bouncycastle.math.ec.ECPoint subtract(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint) {
            if (eCPoint.isInfinity()) {
                return this;
            }
            return add(eCPoint.negate());
        }
    }

    public static class Fp extends com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractFp {
        Fp(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2) {
            super(eCCurve, eCFieldElement, eCFieldElement2);
        }

        Fp(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2, com.android.internal.org.bouncycastle.math.ec.ECFieldElement[] eCFieldElementArr) {
            super(eCCurve, eCFieldElement, eCFieldElement2, eCFieldElementArr);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        protected com.android.internal.org.bouncycastle.math.ec.ECPoint detach() {
            return new com.android.internal.org.bouncycastle.math.ec.ECPoint.Fp(null, getAffineXCoord(), getAffineYCoord());
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement getZCoord(int i) {
            if (i == 1 && 4 == getCurveCoordinateSystem()) {
                return getJacobianModifiedW();
            }
            return super.getZCoord(i);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public com.android.internal.org.bouncycastle.math.ec.ECPoint add(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint) {
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiplyMinusProduct;
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement;
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2;
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement3;
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement4;
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement[] eCFieldElementArr;
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
            int coordinateSystem = curve.getCoordinateSystem();
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement5 = this.x;
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement6 = this.y;
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement7 = eCPoint.x;
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement8 = eCPoint.y;
            switch (coordinateSystem) {
                case 0:
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement subtract = eCFieldElement7.subtract(eCFieldElement5);
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement subtract2 = eCFieldElement8.subtract(eCFieldElement6);
                    if (subtract.isZero()) {
                        if (subtract2.isZero()) {
                            return twice();
                        }
                        return curve.getInfinity();
                    }
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement divide = subtract2.divide(subtract);
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement subtract3 = divide.square().subtract(eCFieldElement5).subtract(eCFieldElement7);
                    return new com.android.internal.org.bouncycastle.math.ec.ECPoint.Fp(curve, subtract3, divide.multiply(eCFieldElement5.subtract(subtract3)).subtract(eCFieldElement6));
                case 1:
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement9 = this.zs[0];
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement10 = eCPoint.zs[0];
                    boolean isOne = eCFieldElement9.isOne();
                    boolean isOne2 = eCFieldElement10.isOne();
                    if (!isOne) {
                        eCFieldElement8 = eCFieldElement8.multiply(eCFieldElement9);
                    }
                    if (!isOne2) {
                        eCFieldElement6 = eCFieldElement6.multiply(eCFieldElement10);
                    }
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement subtract4 = eCFieldElement8.subtract(eCFieldElement6);
                    if (!isOne) {
                        eCFieldElement7 = eCFieldElement7.multiply(eCFieldElement9);
                    }
                    if (!isOne2) {
                        eCFieldElement5 = eCFieldElement5.multiply(eCFieldElement10);
                    }
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement subtract5 = eCFieldElement7.subtract(eCFieldElement5);
                    if (subtract5.isZero()) {
                        if (subtract4.isZero()) {
                            return twice();
                        }
                        return curve.getInfinity();
                    }
                    if (isOne) {
                        eCFieldElement9 = eCFieldElement10;
                    } else if (!isOne2) {
                        eCFieldElement9 = eCFieldElement9.multiply(eCFieldElement10);
                    }
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement square = subtract5.square();
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply = square.multiply(subtract5);
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply2 = square.multiply(eCFieldElement5);
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement subtract6 = subtract4.square().multiply(eCFieldElement9).subtract(multiply).subtract(two(multiply2));
                    return new com.android.internal.org.bouncycastle.math.ec.ECPoint.Fp(curve, subtract5.multiply(subtract6), multiply2.subtract(subtract6).multiplyMinusProduct(subtract4, eCFieldElement6, multiply), new com.android.internal.org.bouncycastle.math.ec.ECFieldElement[]{multiply.multiply(eCFieldElement9)});
                case 2:
                case 4:
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement11 = this.zs[0];
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement12 = eCPoint.zs[0];
                    boolean isOne3 = eCFieldElement11.isOne();
                    if (!isOne3 && eCFieldElement11.equals(eCFieldElement12)) {
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement subtract7 = eCFieldElement5.subtract(eCFieldElement7);
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement subtract8 = eCFieldElement6.subtract(eCFieldElement8);
                        if (subtract7.isZero()) {
                            if (subtract8.isZero()) {
                                return twice();
                            }
                            return curve.getInfinity();
                        }
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement square2 = subtract7.square();
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply3 = eCFieldElement5.multiply(square2);
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply4 = eCFieldElement7.multiply(square2);
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply5 = multiply3.subtract(multiply4).multiply(eCFieldElement6);
                        eCFieldElement3 = subtract8.square().subtract(multiply3).subtract(multiply4);
                        multiplyMinusProduct = multiply3.subtract(eCFieldElement3).multiply(subtract8).subtract(multiply5);
                        eCFieldElement2 = subtract7.multiply(eCFieldElement11);
                        eCFieldElement4 = null;
                    } else {
                        if (!isOne3) {
                            com.android.internal.org.bouncycastle.math.ec.ECFieldElement square3 = eCFieldElement11.square();
                            eCFieldElement7 = square3.multiply(eCFieldElement7);
                            eCFieldElement8 = square3.multiply(eCFieldElement11).multiply(eCFieldElement8);
                        }
                        boolean isOne4 = eCFieldElement12.isOne();
                        if (!isOne4) {
                            com.android.internal.org.bouncycastle.math.ec.ECFieldElement square4 = eCFieldElement12.square();
                            eCFieldElement5 = square4.multiply(eCFieldElement5);
                            eCFieldElement6 = square4.multiply(eCFieldElement12).multiply(eCFieldElement6);
                        }
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement subtract9 = eCFieldElement5.subtract(eCFieldElement7);
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement subtract10 = eCFieldElement6.subtract(eCFieldElement8);
                        if (subtract9.isZero()) {
                            if (subtract10.isZero()) {
                                return twice();
                            }
                            return curve.getInfinity();
                        }
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement square5 = subtract9.square();
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply6 = square5.multiply(subtract9);
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply7 = square5.multiply(eCFieldElement5);
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement subtract11 = subtract10.square().add(multiply6).subtract(two(multiply7));
                        multiplyMinusProduct = multiply7.subtract(subtract11).multiplyMinusProduct(subtract10, multiply6, eCFieldElement6);
                        if (isOne3) {
                            eCFieldElement = subtract9;
                        } else {
                            eCFieldElement = subtract9.multiply(eCFieldElement11);
                        }
                        if (isOne4) {
                            eCFieldElement2 = eCFieldElement;
                        } else {
                            eCFieldElement2 = eCFieldElement.multiply(eCFieldElement12);
                        }
                        if (eCFieldElement2 != subtract9) {
                            eCFieldElement3 = subtract11;
                            eCFieldElement4 = null;
                        } else {
                            eCFieldElement3 = subtract11;
                            eCFieldElement4 = square5;
                        }
                    }
                    if (coordinateSystem == 4) {
                        eCFieldElementArr = new com.android.internal.org.bouncycastle.math.ec.ECFieldElement[]{eCFieldElement2, calculateJacobianModifiedW(eCFieldElement2, eCFieldElement4)};
                    } else {
                        eCFieldElementArr = new com.android.internal.org.bouncycastle.math.ec.ECFieldElement[]{eCFieldElement2};
                    }
                    return new com.android.internal.org.bouncycastle.math.ec.ECPoint.Fp(curve, eCFieldElement3, multiplyMinusProduct, eCFieldElementArr);
                case 3:
                default:
                    throw new java.lang.IllegalStateException("unsupported coordinate system");
            }
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public com.android.internal.org.bouncycastle.math.ec.ECPoint twice() {
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement;
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement four;
            if (isInfinity()) {
                return this;
            }
            com.android.internal.org.bouncycastle.math.ec.ECCurve curve = getCurve();
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2 = this.y;
            if (eCFieldElement2.isZero()) {
                return curve.getInfinity();
            }
            int coordinateSystem = curve.getCoordinateSystem();
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement3 = this.x;
            switch (coordinateSystem) {
                case 0:
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement divide = three(eCFieldElement3.square()).add(getCurve().getA()).divide(two(eCFieldElement2));
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement subtract = divide.square().subtract(two(eCFieldElement3));
                    return new com.android.internal.org.bouncycastle.math.ec.ECPoint.Fp(curve, subtract, divide.multiply(eCFieldElement3.subtract(subtract)).subtract(eCFieldElement2));
                case 1:
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement4 = this.zs[0];
                    boolean isOne = eCFieldElement4.isOne();
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement a = curve.getA();
                    if (!a.isZero() && !isOne) {
                        a = a.multiply(eCFieldElement4.square());
                    }
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement add = a.add(three(eCFieldElement3.square()));
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply = isOne ? eCFieldElement2 : eCFieldElement2.multiply(eCFieldElement4);
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement square = isOne ? eCFieldElement2.square() : multiply.multiply(eCFieldElement2);
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement four2 = four(eCFieldElement3.multiply(square));
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement subtract2 = add.square().subtract(two(four2));
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement two = two(multiply);
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply2 = subtract2.multiply(two);
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement two2 = two(square);
                    return new com.android.internal.org.bouncycastle.math.ec.ECPoint.Fp(curve, multiply2, four2.subtract(subtract2).multiply(add).subtract(two(two2.square())), new com.android.internal.org.bouncycastle.math.ec.ECFieldElement[]{two(isOne ? two(two2) : two.square()).multiply(multiply)});
                case 2:
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement5 = this.zs[0];
                    boolean isOne2 = eCFieldElement5.isOne();
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement square2 = eCFieldElement2.square();
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement square3 = square2.square();
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement a2 = curve.getA();
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement negate = a2.negate();
                    if (negate.toBigInteger().equals(java.math.BigInteger.valueOf(3L))) {
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement square4 = isOne2 ? eCFieldElement5 : eCFieldElement5.square();
                        eCFieldElement = three(eCFieldElement3.add(square4).multiply(eCFieldElement3.subtract(square4)));
                        four = four(square2.multiply(eCFieldElement3));
                    } else {
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement three = three(eCFieldElement3.square());
                        if (isOne2) {
                            eCFieldElement = three.add(a2);
                        } else if (a2.isZero()) {
                            eCFieldElement = three;
                        } else {
                            com.android.internal.org.bouncycastle.math.ec.ECFieldElement square5 = eCFieldElement5.square().square();
                            if (negate.bitLength() < a2.bitLength()) {
                                eCFieldElement = three.subtract(square5.multiply(negate));
                            } else {
                                eCFieldElement = three.add(square5.multiply(a2));
                            }
                        }
                        four = four(eCFieldElement3.multiply(square2));
                    }
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement subtract3 = eCFieldElement.square().subtract(two(four));
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement subtract4 = four.subtract(subtract3).multiply(eCFieldElement).subtract(eight(square3));
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement two3 = two(eCFieldElement2);
                    if (!isOne2) {
                        two3 = two3.multiply(eCFieldElement5);
                    }
                    return new com.android.internal.org.bouncycastle.math.ec.ECPoint.Fp(curve, subtract3, subtract4, new com.android.internal.org.bouncycastle.math.ec.ECFieldElement[]{two3});
                case 3:
                default:
                    throw new java.lang.IllegalStateException("unsupported coordinate system");
                case 4:
                    return twiceJacobianModified(true);
            }
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
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement = this.y;
            if (eCFieldElement.isZero()) {
                return eCPoint;
            }
            com.android.internal.org.bouncycastle.math.ec.ECCurve curve = getCurve();
            switch (curve.getCoordinateSystem()) {
                case 0:
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2 = this.x;
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement3 = eCPoint.x;
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement4 = eCPoint.y;
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement subtract = eCFieldElement3.subtract(eCFieldElement2);
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement subtract2 = eCFieldElement4.subtract(eCFieldElement);
                    if (subtract.isZero()) {
                        if (subtract2.isZero()) {
                            return threeTimes();
                        }
                        return this;
                    }
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement square = subtract.square();
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement subtract3 = square.multiply(two(eCFieldElement2).add(eCFieldElement3)).subtract(subtract2.square());
                    if (subtract3.isZero()) {
                        return curve.getInfinity();
                    }
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement invert = subtract3.multiply(subtract).invert();
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply = subtract3.multiply(invert).multiply(subtract2);
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement subtract4 = two(eCFieldElement).multiply(square).multiply(subtract).multiply(invert).subtract(multiply);
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement add = subtract4.subtract(multiply).multiply(multiply.add(subtract4)).add(eCFieldElement3);
                    return new com.android.internal.org.bouncycastle.math.ec.ECPoint.Fp(curve, add, eCFieldElement2.subtract(add).multiply(subtract4).subtract(eCFieldElement));
                case 4:
                    return twiceJacobianModified(false).add(eCPoint);
                default:
                    return twice().add(eCPoint);
            }
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public com.android.internal.org.bouncycastle.math.ec.ECPoint threeTimes() {
            if (isInfinity()) {
                return this;
            }
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement = this.y;
            if (eCFieldElement.isZero()) {
                return this;
            }
            com.android.internal.org.bouncycastle.math.ec.ECCurve curve = getCurve();
            switch (curve.getCoordinateSystem()) {
                case 0:
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2 = this.x;
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement two = two(eCFieldElement);
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement square = two.square();
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement add = three(eCFieldElement2.square()).add(getCurve().getA());
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement subtract = three(eCFieldElement2).multiply(square).subtract(add.square());
                    if (!subtract.isZero()) {
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement invert = subtract.multiply(two).invert();
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply = subtract.multiply(invert).multiply(add);
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement subtract2 = square.square().multiply(invert).subtract(multiply);
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement add2 = subtract2.subtract(multiply).multiply(multiply.add(subtract2)).add(eCFieldElement2);
                        break;
                    } else {
                        break;
                    }
                case 4:
                    break;
            }
            return this;
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public com.android.internal.org.bouncycastle.math.ec.ECPoint timesPow2(int i) {
            if (i < 0) {
                throw new java.lang.IllegalArgumentException("'e' cannot be negative");
            }
            if (i == 0 || isInfinity()) {
                return this;
            }
            if (i == 1) {
                return twice();
            }
            com.android.internal.org.bouncycastle.math.ec.ECCurve curve = getCurve();
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement = this.y;
            if (eCFieldElement.isZero()) {
                return curve.getInfinity();
            }
            int coordinateSystem = curve.getCoordinateSystem();
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement a = curve.getA();
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2 = this.x;
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement fromBigInteger = this.zs.length < 1 ? curve.fromBigInteger(com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE) : this.zs[0];
            if (!fromBigInteger.isOne()) {
                switch (coordinateSystem) {
                    case 0:
                        break;
                    case 1:
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement square = fromBigInteger.square();
                        eCFieldElement2 = eCFieldElement2.multiply(fromBigInteger);
                        eCFieldElement = eCFieldElement.multiply(square);
                        a = calculateJacobianModifiedW(fromBigInteger, square);
                        break;
                    case 2:
                        a = calculateJacobianModifiedW(fromBigInteger, null);
                        break;
                    case 3:
                    default:
                        throw new java.lang.IllegalStateException("unsupported coordinate system");
                    case 4:
                        a = getJacobianModifiedW();
                        break;
                }
            }
            int i2 = 0;
            while (i2 < i) {
                if (eCFieldElement.isZero()) {
                    return curve.getInfinity();
                }
                com.android.internal.org.bouncycastle.math.ec.ECFieldElement three = three(eCFieldElement2.square());
                com.android.internal.org.bouncycastle.math.ec.ECFieldElement two = two(eCFieldElement);
                com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply = two.multiply(eCFieldElement);
                com.android.internal.org.bouncycastle.math.ec.ECFieldElement two2 = two(eCFieldElement2.multiply(multiply));
                com.android.internal.org.bouncycastle.math.ec.ECFieldElement two3 = two(multiply.square());
                if (!a.isZero()) {
                    three = three.add(a);
                    a = two(two3.multiply(a));
                }
                com.android.internal.org.bouncycastle.math.ec.ECFieldElement subtract = three.square().subtract(two(two2));
                eCFieldElement = three.multiply(two2.subtract(subtract)).subtract(two3);
                fromBigInteger = fromBigInteger.isOne() ? two : two.multiply(fromBigInteger);
                i2++;
                eCFieldElement2 = subtract;
            }
            switch (coordinateSystem) {
                case 0:
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement invert = fromBigInteger.invert();
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement square2 = invert.square();
                    return new com.android.internal.org.bouncycastle.math.ec.ECPoint.Fp(curve, eCFieldElement2.multiply(square2), eCFieldElement.multiply(square2.multiply(invert)));
                case 1:
                    return new com.android.internal.org.bouncycastle.math.ec.ECPoint.Fp(curve, eCFieldElement2.multiply(fromBigInteger), eCFieldElement, new com.android.internal.org.bouncycastle.math.ec.ECFieldElement[]{fromBigInteger.multiply(fromBigInteger.square())});
                case 2:
                    return new com.android.internal.org.bouncycastle.math.ec.ECPoint.Fp(curve, eCFieldElement2, eCFieldElement, new com.android.internal.org.bouncycastle.math.ec.ECFieldElement[]{fromBigInteger});
                case 3:
                default:
                    throw new java.lang.IllegalStateException("unsupported coordinate system");
                case 4:
                    return new com.android.internal.org.bouncycastle.math.ec.ECPoint.Fp(curve, eCFieldElement2, eCFieldElement, new com.android.internal.org.bouncycastle.math.ec.ECFieldElement[]{fromBigInteger, a});
            }
        }

        protected com.android.internal.org.bouncycastle.math.ec.ECFieldElement two(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
            return eCFieldElement.add(eCFieldElement);
        }

        protected com.android.internal.org.bouncycastle.math.ec.ECFieldElement three(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
            return two(eCFieldElement).add(eCFieldElement);
        }

        protected com.android.internal.org.bouncycastle.math.ec.ECFieldElement four(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
            return two(two(eCFieldElement));
        }

        protected com.android.internal.org.bouncycastle.math.ec.ECFieldElement eight(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
            return four(two(eCFieldElement));
        }

        protected com.android.internal.org.bouncycastle.math.ec.ECFieldElement doubleProductFromSquares(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement3, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement4) {
            return eCFieldElement.add(eCFieldElement2).square().subtract(eCFieldElement3).subtract(eCFieldElement4);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public com.android.internal.org.bouncycastle.math.ec.ECPoint negate() {
            if (isInfinity()) {
                return this;
            }
            com.android.internal.org.bouncycastle.math.ec.ECCurve curve = getCurve();
            if (curve.getCoordinateSystem() != 0) {
                return new com.android.internal.org.bouncycastle.math.ec.ECPoint.Fp(curve, this.x, this.y.negate(), this.zs);
            }
            return new com.android.internal.org.bouncycastle.math.ec.ECPoint.Fp(curve, this.x, this.y.negate());
        }

        protected com.android.internal.org.bouncycastle.math.ec.ECFieldElement calculateJacobianModifiedW(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2) {
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement a = getCurve().getA();
            if (a.isZero() || eCFieldElement.isOne()) {
                return a;
            }
            if (eCFieldElement2 == null) {
                eCFieldElement2 = eCFieldElement.square();
            }
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement square = eCFieldElement2.square();
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement negate = a.negate();
            if (negate.bitLength() < a.bitLength()) {
                return square.multiply(negate).negate();
            }
            return square.multiply(a);
        }

        protected com.android.internal.org.bouncycastle.math.ec.ECFieldElement getJacobianModifiedW() {
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement = this.zs[1];
            if (eCFieldElement == null) {
                com.android.internal.org.bouncycastle.math.ec.ECFieldElement[] eCFieldElementArr = this.zs;
                com.android.internal.org.bouncycastle.math.ec.ECFieldElement calculateJacobianModifiedW = calculateJacobianModifiedW(this.zs[0], null);
                eCFieldElementArr[1] = calculateJacobianModifiedW;
                return calculateJacobianModifiedW;
            }
            return eCFieldElement;
        }

        protected com.android.internal.org.bouncycastle.math.ec.ECPoint.Fp twiceJacobianModified(boolean z) {
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement = this.x;
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2 = this.y;
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement3 = this.zs[0];
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement jacobianModifiedW = getJacobianModifiedW();
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement add = three(eCFieldElement.square()).add(jacobianModifiedW);
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement two = two(eCFieldElement2);
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply = two.multiply(eCFieldElement2);
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement two2 = two(eCFieldElement.multiply(multiply));
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement subtract = add.square().subtract(two(two2));
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement two3 = two(multiply.square());
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement subtract2 = add.multiply(two2.subtract(subtract)).subtract(two3);
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement two4 = z ? two(two3.multiply(jacobianModifiedW)) : null;
            if (!eCFieldElement3.isOne()) {
                two = two.multiply(eCFieldElement3);
            }
            return new com.android.internal.org.bouncycastle.math.ec.ECPoint.Fp(getCurve(), subtract, subtract2, new com.android.internal.org.bouncycastle.math.ec.ECFieldElement[]{two, two4});
        }
    }

    public static abstract class AbstractF2m extends com.android.internal.org.bouncycastle.math.ec.ECPoint {
        protected AbstractF2m(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2) {
            super(eCCurve, eCFieldElement, eCFieldElement2);
        }

        protected AbstractF2m(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2, com.android.internal.org.bouncycastle.math.ec.ECFieldElement[] eCFieldElementArr) {
            super(eCCurve, eCFieldElement, eCFieldElement2, eCFieldElementArr);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        protected boolean satisfiesCurveEquation() {
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiplyPlusProduct;
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement squarePlusProduct;
            com.android.internal.org.bouncycastle.math.ec.ECCurve curve = getCurve();
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement = this.x;
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement a = curve.getA();
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement b = curve.getB();
            int coordinateSystem = curve.getCoordinateSystem();
            if (coordinateSystem == 6) {
                com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2 = this.zs[0];
                boolean isOne = eCFieldElement2.isOne();
                if (eCFieldElement.isZero()) {
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement square = this.y.square();
                    if (!isOne) {
                        b = b.multiply(eCFieldElement2.square());
                    }
                    return square.equals(b);
                }
                com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement3 = this.y;
                com.android.internal.org.bouncycastle.math.ec.ECFieldElement square2 = eCFieldElement.square();
                if (isOne) {
                    multiplyPlusProduct = eCFieldElement3.square().add(eCFieldElement3).add(a);
                    squarePlusProduct = square2.square().add(b);
                } else {
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement square3 = eCFieldElement2.square();
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement square4 = square3.square();
                    multiplyPlusProduct = eCFieldElement3.add(eCFieldElement2).multiplyPlusProduct(eCFieldElement3, a, square3);
                    squarePlusProduct = square2.squarePlusProduct(b, square4);
                }
                return multiplyPlusProduct.multiply(square2).equals(squarePlusProduct);
            }
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement4 = this.y;
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply = eCFieldElement4.add(eCFieldElement).multiply(eCFieldElement4);
            switch (coordinateSystem) {
                case 0:
                    break;
                case 1:
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement5 = this.zs[0];
                    if (!eCFieldElement5.isOne()) {
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply2 = eCFieldElement5.multiply(eCFieldElement5.square());
                        multiply = multiply.multiply(eCFieldElement5);
                        a = a.multiply(eCFieldElement5);
                        b = b.multiply(multiply2);
                        break;
                    }
                    break;
                default:
                    throw new java.lang.IllegalStateException("unsupported coordinate system");
            }
            return multiply.equals(eCFieldElement.add(a).multiply(eCFieldElement.square()).add(b));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        protected boolean satisfiesOrder() {
            java.math.BigInteger cofactor = this.curve.getCofactor();
            if (com.android.internal.org.bouncycastle.math.ec.ECConstants.TWO.equals(cofactor)) {
                return ((com.android.internal.org.bouncycastle.math.ec.ECFieldElement.AbstractF2m) normalize().getAffineXCoord()).trace() != 0;
            }
            if (com.android.internal.org.bouncycastle.math.ec.ECConstants.FOUR.equals(cofactor)) {
                com.android.internal.org.bouncycastle.math.ec.ECPoint normalize = normalize();
                com.android.internal.org.bouncycastle.math.ec.ECFieldElement affineXCoord = normalize.getAffineXCoord();
                com.android.internal.org.bouncycastle.math.ec.ECFieldElement solveQuadraticEquation = ((com.android.internal.org.bouncycastle.math.ec.ECCurve.AbstractF2m) this.curve).solveQuadraticEquation(affineXCoord.add(this.curve.getA()));
                if (solveQuadraticEquation == null) {
                    return false;
                }
                return ((com.android.internal.org.bouncycastle.math.ec.ECFieldElement.AbstractF2m) affineXCoord.multiply(solveQuadraticEquation).add(normalize.getAffineYCoord())).trace() == 0;
            }
            return super.satisfiesOrder();
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public com.android.internal.org.bouncycastle.math.ec.ECPoint scaleX(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
            if (isInfinity()) {
                return this;
            }
            switch (getCurveCoordinateSystem()) {
                case 5:
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement rawXCoord = getRawXCoord();
                    return getCurve().createRawPoint(rawXCoord, getRawYCoord().add(rawXCoord).divide(eCFieldElement).add(rawXCoord.multiply(eCFieldElement)), getRawZCoords());
                case 6:
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement rawXCoord2 = getRawXCoord();
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement rawYCoord = getRawYCoord();
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2 = getRawZCoords()[0];
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply = rawXCoord2.multiply(eCFieldElement.square());
                    return getCurve().createRawPoint(multiply, rawYCoord.add(rawXCoord2).add(multiply), new com.android.internal.org.bouncycastle.math.ec.ECFieldElement[]{eCFieldElement2.multiply(eCFieldElement)});
                default:
                    return super.scaleX(eCFieldElement);
            }
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public com.android.internal.org.bouncycastle.math.ec.ECPoint scaleXNegateY(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
            return scaleX(eCFieldElement);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public com.android.internal.org.bouncycastle.math.ec.ECPoint scaleY(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
            if (isInfinity()) {
                return this;
            }
            switch (getCurveCoordinateSystem()) {
                case 5:
                case 6:
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement rawXCoord = getRawXCoord();
                    return getCurve().createRawPoint(rawXCoord, getRawYCoord().add(rawXCoord).multiply(eCFieldElement).add(rawXCoord), getRawZCoords());
                default:
                    return super.scaleY(eCFieldElement);
            }
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public com.android.internal.org.bouncycastle.math.ec.ECPoint scaleYNegateX(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
            return scaleY(eCFieldElement);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public com.android.internal.org.bouncycastle.math.ec.ECPoint subtract(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint) {
            if (eCPoint.isInfinity()) {
                return this;
            }
            return add(eCPoint.negate());
        }

        public com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m tau() {
            if (isInfinity()) {
                return this;
            }
            com.android.internal.org.bouncycastle.math.ec.ECCurve curve = getCurve();
            int coordinateSystem = curve.getCoordinateSystem();
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement = this.x;
            switch (coordinateSystem) {
                case 0:
                case 5:
                    return (com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m) curve.createRawPoint(eCFieldElement.square(), this.y.square());
                case 1:
                case 6:
                    return (com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m) curve.createRawPoint(eCFieldElement.square(), this.y.square(), new com.android.internal.org.bouncycastle.math.ec.ECFieldElement[]{this.zs[0].square()});
                case 2:
                case 3:
                case 4:
                default:
                    throw new java.lang.IllegalStateException("unsupported coordinate system");
            }
        }

        public com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m tauPow(int i) {
            if (isInfinity()) {
                return this;
            }
            com.android.internal.org.bouncycastle.math.ec.ECCurve curve = getCurve();
            int coordinateSystem = curve.getCoordinateSystem();
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement = this.x;
            switch (coordinateSystem) {
                case 0:
                case 5:
                    return (com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m) curve.createRawPoint(eCFieldElement.squarePow(i), this.y.squarePow(i));
                case 1:
                case 6:
                    return (com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m) curve.createRawPoint(eCFieldElement.squarePow(i), this.y.squarePow(i), new com.android.internal.org.bouncycastle.math.ec.ECFieldElement[]{this.zs[0].squarePow(i)});
                case 2:
                case 3:
                case 4:
                default:
                    throw new java.lang.IllegalStateException("unsupported coordinate system");
            }
        }
    }

    public static class F2m extends com.android.internal.org.bouncycastle.math.ec.ECPoint.AbstractF2m {
        F2m(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2) {
            super(eCCurve, eCFieldElement, eCFieldElement2);
        }

        F2m(com.android.internal.org.bouncycastle.math.ec.ECCurve eCCurve, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2, com.android.internal.org.bouncycastle.math.ec.ECFieldElement[] eCFieldElementArr) {
            super(eCCurve, eCFieldElement, eCFieldElement2, eCFieldElementArr);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        protected com.android.internal.org.bouncycastle.math.ec.ECPoint detach() {
            return new com.android.internal.org.bouncycastle.math.ec.ECPoint.F2m(null, getAffineXCoord(), getAffineYCoord());
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement getYCoord() {
            int curveCoordinateSystem = getCurveCoordinateSystem();
            switch (curveCoordinateSystem) {
                case 5:
                case 6:
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement = this.x;
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2 = this.y;
                    if (isInfinity() || eCFieldElement.isZero()) {
                        return eCFieldElement2;
                    }
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply = eCFieldElement2.add(eCFieldElement).multiply(eCFieldElement);
                    if (6 == curveCoordinateSystem) {
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement3 = this.zs[0];
                        if (!eCFieldElement3.isOne()) {
                            return multiply.divide(eCFieldElement3);
                        }
                        return multiply;
                    }
                    return multiply;
                default:
                    return this.y;
            }
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        protected boolean getCompressionYTilde() {
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement rawXCoord = getRawXCoord();
            if (rawXCoord.isZero()) {
                return false;
            }
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement rawYCoord = getRawYCoord();
            switch (getCurveCoordinateSystem()) {
                case 5:
                case 6:
                    return rawYCoord.testBitZero() != rawXCoord.testBitZero();
                default:
                    return rawYCoord.divide(rawXCoord).testBitZero();
            }
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public com.android.internal.org.bouncycastle.math.ec.ECPoint add(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint) {
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement;
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2;
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement3;
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply;
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement4;
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement squarePlusProduct;
            if (isInfinity()) {
                return eCPoint;
            }
            if (eCPoint.isInfinity()) {
                return this;
            }
            com.android.internal.org.bouncycastle.math.ec.ECCurve curve = getCurve();
            int coordinateSystem = curve.getCoordinateSystem();
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement5 = this.x;
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement6 = eCPoint.x;
            switch (coordinateSystem) {
                case 0:
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement7 = this.y;
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement8 = eCPoint.y;
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement add = eCFieldElement5.add(eCFieldElement6);
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement add2 = eCFieldElement7.add(eCFieldElement8);
                    if (add.isZero()) {
                        if (add2.isZero()) {
                            return twice();
                        }
                        return curve.getInfinity();
                    }
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement divide = add2.divide(add);
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement add3 = divide.square().add(divide).add(add).add(curve.getA());
                    return new com.android.internal.org.bouncycastle.math.ec.ECPoint.F2m(curve, add3, divide.multiply(eCFieldElement5.add(add3)).add(add3).add(eCFieldElement7));
                case 1:
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement9 = this.y;
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement10 = this.zs[0];
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement11 = eCPoint.y;
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement12 = eCPoint.zs[0];
                    boolean isOne = eCFieldElement12.isOne();
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement add4 = eCFieldElement10.multiply(eCFieldElement11).add(isOne ? eCFieldElement9 : eCFieldElement9.multiply(eCFieldElement12));
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement add5 = eCFieldElement10.multiply(eCFieldElement6).add(isOne ? eCFieldElement5 : eCFieldElement5.multiply(eCFieldElement12));
                    if (add5.isZero()) {
                        if (add4.isZero()) {
                            return twice();
                        }
                        return curve.getInfinity();
                    }
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement square = add5.square();
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply2 = square.multiply(add5);
                    if (!isOne) {
                        eCFieldElement10 = eCFieldElement10.multiply(eCFieldElement12);
                    }
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement add6 = add4.add(add5);
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement add7 = add6.multiplyPlusProduct(add4, square, curve.getA()).multiply(eCFieldElement10).add(multiply2);
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply3 = add5.multiply(add7);
                    if (!isOne) {
                        square = square.multiply(eCFieldElement12);
                    }
                    return new com.android.internal.org.bouncycastle.math.ec.ECPoint.F2m(curve, multiply3, add4.multiplyPlusProduct(eCFieldElement5, add5, eCFieldElement9).multiplyPlusProduct(square, add6, add7), new com.android.internal.org.bouncycastle.math.ec.ECFieldElement[]{multiply2.multiply(eCFieldElement10)});
                case 6:
                    if (eCFieldElement5.isZero()) {
                        if (eCFieldElement6.isZero()) {
                            return curve.getInfinity();
                        }
                        return eCPoint.add(this);
                    }
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement13 = this.y;
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement14 = this.zs[0];
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement15 = eCPoint.y;
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement16 = eCPoint.zs[0];
                    boolean isOne2 = eCFieldElement14.isOne();
                    if (isOne2) {
                        eCFieldElement = eCFieldElement6;
                        eCFieldElement2 = eCFieldElement15;
                    } else {
                        eCFieldElement = eCFieldElement6.multiply(eCFieldElement14);
                        eCFieldElement2 = eCFieldElement15.multiply(eCFieldElement14);
                    }
                    boolean isOne3 = eCFieldElement16.isOne();
                    if (isOne3) {
                        eCFieldElement3 = eCFieldElement13;
                    } else {
                        eCFieldElement5 = eCFieldElement5.multiply(eCFieldElement16);
                        eCFieldElement3 = eCFieldElement13.multiply(eCFieldElement16);
                    }
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement add8 = eCFieldElement3.add(eCFieldElement2);
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement add9 = eCFieldElement5.add(eCFieldElement);
                    if (add9.isZero()) {
                        if (add8.isZero()) {
                            return twice();
                        }
                        return curve.getInfinity();
                    }
                    if (eCFieldElement6.isZero()) {
                        com.android.internal.org.bouncycastle.math.ec.ECPoint normalize = normalize();
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement xCoord = normalize.getXCoord();
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement yCoord = normalize.getYCoord();
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement divide2 = yCoord.add(eCFieldElement15).divide(xCoord);
                        multiply = divide2.square().add(divide2).add(xCoord).add(curve.getA());
                        if (multiply.isZero()) {
                            return new com.android.internal.org.bouncycastle.math.ec.ECPoint.F2m(curve, multiply, curve.getB().sqrt());
                        }
                        squarePlusProduct = divide2.multiply(xCoord.add(multiply)).add(multiply).add(yCoord).divide(multiply).add(multiply);
                        eCFieldElement4 = curve.fromBigInteger(com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE);
                    } else {
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement square2 = add9.square();
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply4 = add8.multiply(eCFieldElement5);
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply5 = add8.multiply(eCFieldElement);
                        multiply = multiply4.multiply(multiply5);
                        if (multiply.isZero()) {
                            return new com.android.internal.org.bouncycastle.math.ec.ECPoint.F2m(curve, multiply, curve.getB().sqrt());
                        }
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply6 = add8.multiply(square2);
                        if (isOne3) {
                            eCFieldElement4 = multiply6;
                        } else {
                            eCFieldElement4 = multiply6.multiply(eCFieldElement16);
                        }
                        squarePlusProduct = multiply5.add(square2).squarePlusProduct(eCFieldElement4, eCFieldElement13.add(eCFieldElement14));
                        if (!isOne2) {
                            eCFieldElement4 = eCFieldElement4.multiply(eCFieldElement14);
                        }
                    }
                    return new com.android.internal.org.bouncycastle.math.ec.ECPoint.F2m(curve, multiply, squarePlusProduct, new com.android.internal.org.bouncycastle.math.ec.ECFieldElement[]{eCFieldElement4});
                default:
                    throw new java.lang.IllegalStateException("unsupported coordinate system");
            }
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public com.android.internal.org.bouncycastle.math.ec.ECPoint twice() {
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement add;
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement squarePlusProduct;
            if (isInfinity()) {
                return this;
            }
            com.android.internal.org.bouncycastle.math.ec.ECCurve curve = getCurve();
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement = this.x;
            if (eCFieldElement.isZero()) {
                return curve.getInfinity();
            }
            switch (curve.getCoordinateSystem()) {
                case 0:
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement add2 = this.y.divide(eCFieldElement).add(eCFieldElement);
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement add3 = add2.square().add(add2).add(curve.getA());
                    return new com.android.internal.org.bouncycastle.math.ec.ECPoint.F2m(curve, add3, eCFieldElement.squarePlusProduct(add3, add2.addOne()));
                case 1:
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2 = this.y;
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement3 = this.zs[0];
                    boolean isOne = eCFieldElement3.isOne();
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply = isOne ? eCFieldElement : eCFieldElement.multiply(eCFieldElement3);
                    if (!isOne) {
                        eCFieldElement2 = eCFieldElement2.multiply(eCFieldElement3);
                    }
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement square = eCFieldElement.square();
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement add4 = square.add(eCFieldElement2);
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement square2 = multiply.square();
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement add5 = add4.add(multiply);
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiplyPlusProduct = add5.multiplyPlusProduct(add4, square2, curve.getA());
                    return new com.android.internal.org.bouncycastle.math.ec.ECPoint.F2m(curve, multiply.multiply(multiplyPlusProduct), square.square().multiplyPlusProduct(multiply, multiplyPlusProduct, add5), new com.android.internal.org.bouncycastle.math.ec.ECFieldElement[]{multiply.multiply(square2)});
                case 6:
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement4 = this.y;
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement5 = this.zs[0];
                    boolean isOne2 = eCFieldElement5.isOne();
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply2 = isOne2 ? eCFieldElement4 : eCFieldElement4.multiply(eCFieldElement5);
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement square3 = isOne2 ? eCFieldElement5 : eCFieldElement5.square();
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement a = curve.getA();
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply3 = isOne2 ? a : a.multiply(square3);
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement add6 = eCFieldElement4.square().add(multiply2).add(multiply3);
                    if (add6.isZero()) {
                        return new com.android.internal.org.bouncycastle.math.ec.ECPoint.F2m(curve, add6, curve.getB().sqrt());
                    }
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement square4 = add6.square();
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply4 = isOne2 ? add6 : add6.multiply(square3);
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement b = curve.getB();
                    if (b.bitLength() < (curve.getFieldSize() >> 1)) {
                        com.android.internal.org.bouncycastle.math.ec.ECFieldElement square5 = eCFieldElement4.add(eCFieldElement).square();
                        if (b.isOne()) {
                            squarePlusProduct = multiply3.add(square3).square();
                        } else {
                            squarePlusProduct = multiply3.squarePlusProduct(b, square3.square());
                        }
                        add = square5.add(add6).add(square3).multiply(square5).add(squarePlusProduct).add(square4);
                        if (a.isZero()) {
                            add = add.add(multiply4);
                        } else if (!a.isOne()) {
                            add = add.add(a.addOne().multiply(multiply4));
                        }
                    } else {
                        if (!isOne2) {
                            eCFieldElement = eCFieldElement.multiply(eCFieldElement5);
                        }
                        add = eCFieldElement.squarePlusProduct(add6, multiply2).add(square4).add(multiply4);
                    }
                    return new com.android.internal.org.bouncycastle.math.ec.ECPoint.F2m(curve, square4, add, new com.android.internal.org.bouncycastle.math.ec.ECFieldElement[]{multiply4});
                default:
                    throw new java.lang.IllegalStateException("unsupported coordinate system");
            }
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public com.android.internal.org.bouncycastle.math.ec.ECPoint twicePlus(com.android.internal.org.bouncycastle.math.ec.ECPoint eCPoint) {
            if (isInfinity()) {
                return eCPoint;
            }
            if (eCPoint.isInfinity()) {
                return twice();
            }
            com.android.internal.org.bouncycastle.math.ec.ECCurve curve = getCurve();
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement = this.x;
            if (eCFieldElement.isZero()) {
                return eCPoint;
            }
            switch (curve.getCoordinateSystem()) {
                case 6:
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2 = eCPoint.x;
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement3 = eCPoint.zs[0];
                    if (eCFieldElement2.isZero() || !eCFieldElement3.isOne()) {
                        return twice().add(eCPoint);
                    }
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement4 = this.y;
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement5 = this.zs[0];
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement6 = eCPoint.y;
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement square = eCFieldElement.square();
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement square2 = eCFieldElement4.square();
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement square3 = eCFieldElement5.square();
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement add = curve.getA().multiply(square3).add(square2).add(eCFieldElement4.multiply(eCFieldElement5));
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement addOne = eCFieldElement6.addOne();
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiplyPlusProduct = curve.getA().add(addOne).multiply(square3).add(square2).multiplyPlusProduct(add, square, square3);
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply = eCFieldElement2.multiply(square3);
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement square4 = multiply.add(add).square();
                    if (square4.isZero()) {
                        if (multiplyPlusProduct.isZero()) {
                            return eCPoint.twice();
                        }
                        return curve.getInfinity();
                    }
                    if (multiplyPlusProduct.isZero()) {
                        return new com.android.internal.org.bouncycastle.math.ec.ECPoint.F2m(curve, multiplyPlusProduct, curve.getB().sqrt());
                    }
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply2 = multiplyPlusProduct.square().multiply(multiply);
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply3 = multiplyPlusProduct.multiply(square4).multiply(square3);
                    return new com.android.internal.org.bouncycastle.math.ec.ECPoint.F2m(curve, multiply2, multiplyPlusProduct.add(square4).square().multiplyPlusProduct(add, addOne, multiply3), new com.android.internal.org.bouncycastle.math.ec.ECFieldElement[]{multiply3});
                default:
                    return twice().add(eCPoint);
            }
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECPoint
        public com.android.internal.org.bouncycastle.math.ec.ECPoint negate() {
            if (isInfinity()) {
                return this;
            }
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement = this.x;
            if (eCFieldElement.isZero()) {
                return this;
            }
            switch (getCurveCoordinateSystem()) {
                case 0:
                    return new com.android.internal.org.bouncycastle.math.ec.ECPoint.F2m(this.curve, eCFieldElement, this.y.add(eCFieldElement));
                case 1:
                    return new com.android.internal.org.bouncycastle.math.ec.ECPoint.F2m(this.curve, eCFieldElement, this.y.add(eCFieldElement), new com.android.internal.org.bouncycastle.math.ec.ECFieldElement[]{this.zs[0]});
                case 2:
                case 3:
                case 4:
                default:
                    throw new java.lang.IllegalStateException("unsupported coordinate system");
                case 5:
                    return new com.android.internal.org.bouncycastle.math.ec.ECPoint.F2m(this.curve, eCFieldElement, this.y.addOne());
                case 6:
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2 = this.y;
                    com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement3 = this.zs[0];
                    return new com.android.internal.org.bouncycastle.math.ec.ECPoint.F2m(this.curve, eCFieldElement, eCFieldElement2.add(eCFieldElement3), new com.android.internal.org.bouncycastle.math.ec.ECFieldElement[]{eCFieldElement3});
            }
        }
    }
}
