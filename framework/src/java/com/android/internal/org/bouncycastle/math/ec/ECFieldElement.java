package com.android.internal.org.bouncycastle.math.ec;

/* loaded from: classes4.dex */
public abstract class ECFieldElement implements com.android.internal.org.bouncycastle.math.ec.ECConstants {

    public static abstract class AbstractFp extends com.android.internal.org.bouncycastle.math.ec.ECFieldElement {
    }

    public abstract com.android.internal.org.bouncycastle.math.ec.ECFieldElement add(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement);

    public abstract com.android.internal.org.bouncycastle.math.ec.ECFieldElement addOne();

    public abstract com.android.internal.org.bouncycastle.math.ec.ECFieldElement divide(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement);

    public abstract java.lang.String getFieldName();

    public abstract int getFieldSize();

    public abstract com.android.internal.org.bouncycastle.math.ec.ECFieldElement invert();

    public abstract com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement);

    public abstract com.android.internal.org.bouncycastle.math.ec.ECFieldElement negate();

    public abstract com.android.internal.org.bouncycastle.math.ec.ECFieldElement sqrt();

    public abstract com.android.internal.org.bouncycastle.math.ec.ECFieldElement square();

    public abstract com.android.internal.org.bouncycastle.math.ec.ECFieldElement subtract(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement);

    public abstract java.math.BigInteger toBigInteger();

    public int bitLength() {
        return toBigInteger().bitLength();
    }

    public boolean isOne() {
        return bitLength() == 1;
    }

    public boolean isZero() {
        return toBigInteger().signum() == 0;
    }

    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiplyMinusProduct(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement3) {
        return multiply(eCFieldElement).subtract(eCFieldElement2.multiply(eCFieldElement3));
    }

    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiplyPlusProduct(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement3) {
        return multiply(eCFieldElement).add(eCFieldElement2.multiply(eCFieldElement3));
    }

    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement squareMinusProduct(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2) {
        return square().subtract(eCFieldElement.multiply(eCFieldElement2));
    }

    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement squarePlusProduct(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2) {
        return square().add(eCFieldElement.multiply(eCFieldElement2));
    }

    public com.android.internal.org.bouncycastle.math.ec.ECFieldElement squarePow(int i) {
        com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement = this;
        for (int i2 = 0; i2 < i; i2++) {
            eCFieldElement = eCFieldElement.square();
        }
        return eCFieldElement;
    }

    public boolean testBitZero() {
        return toBigInteger().testBit(0);
    }

    public java.lang.String toString() {
        return toBigInteger().toString(16);
    }

    public byte[] getEncoded() {
        return com.android.internal.org.bouncycastle.util.BigIntegers.asUnsignedByteArray((getFieldSize() + 7) / 8, toBigInteger());
    }

    public static class Fp extends com.android.internal.org.bouncycastle.math.ec.ECFieldElement.AbstractFp {
        java.math.BigInteger q;
        java.math.BigInteger r;
        java.math.BigInteger x;

        static java.math.BigInteger calculateResidue(java.math.BigInteger bigInteger) {
            int bitLength = bigInteger.bitLength();
            if (bitLength >= 96 && bigInteger.shiftRight(bitLength - 64).longValue() == -1) {
                return ONE.shiftLeft(bitLength).subtract(bigInteger);
            }
            return null;
        }

        public Fp(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
            this(bigInteger, calculateResidue(bigInteger), bigInteger2);
        }

        Fp(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, java.math.BigInteger bigInteger3) {
            if (bigInteger3 == null || bigInteger3.signum() < 0 || bigInteger3.compareTo(bigInteger) >= 0) {
                throw new java.lang.IllegalArgumentException("x value invalid in Fp field element");
            }
            this.q = bigInteger;
            this.r = bigInteger2;
            this.x = bigInteger3;
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public java.math.BigInteger toBigInteger() {
            return this.x;
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public java.lang.String getFieldName() {
            return "Fp";
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public int getFieldSize() {
            return this.q.bitLength();
        }

        public java.math.BigInteger getQ() {
            return this.q;
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement add(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
            return new com.android.internal.org.bouncycastle.math.ec.ECFieldElement.Fp(this.q, this.r, modAdd(this.x, eCFieldElement.toBigInteger()));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement addOne() {
            java.math.BigInteger add = this.x.add(com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE);
            if (add.compareTo(this.q) == 0) {
                add = com.android.internal.org.bouncycastle.math.ec.ECConstants.ZERO;
            }
            return new com.android.internal.org.bouncycastle.math.ec.ECFieldElement.Fp(this.q, this.r, add);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement subtract(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
            return new com.android.internal.org.bouncycastle.math.ec.ECFieldElement.Fp(this.q, this.r, modSubtract(this.x, eCFieldElement.toBigInteger()));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
            return new com.android.internal.org.bouncycastle.math.ec.ECFieldElement.Fp(this.q, this.r, modMult(this.x, eCFieldElement.toBigInteger()));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiplyMinusProduct(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement3) {
            java.math.BigInteger bigInteger = this.x;
            java.math.BigInteger bigInteger2 = eCFieldElement.toBigInteger();
            java.math.BigInteger bigInteger3 = eCFieldElement2.toBigInteger();
            java.math.BigInteger bigInteger4 = eCFieldElement3.toBigInteger();
            return new com.android.internal.org.bouncycastle.math.ec.ECFieldElement.Fp(this.q, this.r, modReduce(bigInteger.multiply(bigInteger2).subtract(bigInteger3.multiply(bigInteger4))));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiplyPlusProduct(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement3) {
            java.math.BigInteger bigInteger = this.x;
            java.math.BigInteger bigInteger2 = eCFieldElement.toBigInteger();
            java.math.BigInteger bigInteger3 = eCFieldElement2.toBigInteger();
            java.math.BigInteger bigInteger4 = eCFieldElement3.toBigInteger();
            return new com.android.internal.org.bouncycastle.math.ec.ECFieldElement.Fp(this.q, this.r, modReduce(bigInteger.multiply(bigInteger2).add(bigInteger3.multiply(bigInteger4))));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement divide(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
            return new com.android.internal.org.bouncycastle.math.ec.ECFieldElement.Fp(this.q, this.r, modMult(this.x, modInverse(eCFieldElement.toBigInteger())));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement negate() {
            return this.x.signum() == 0 ? this : new com.android.internal.org.bouncycastle.math.ec.ECFieldElement.Fp(this.q, this.r, this.q.subtract(this.x));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement square() {
            return new com.android.internal.org.bouncycastle.math.ec.ECFieldElement.Fp(this.q, this.r, modMult(this.x, this.x));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement squareMinusProduct(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2) {
            java.math.BigInteger bigInteger = this.x;
            java.math.BigInteger bigInteger2 = eCFieldElement.toBigInteger();
            java.math.BigInteger bigInteger3 = eCFieldElement2.toBigInteger();
            return new com.android.internal.org.bouncycastle.math.ec.ECFieldElement.Fp(this.q, this.r, modReduce(bigInteger.multiply(bigInteger).subtract(bigInteger2.multiply(bigInteger3))));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement squarePlusProduct(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2) {
            java.math.BigInteger bigInteger = this.x;
            java.math.BigInteger bigInteger2 = eCFieldElement.toBigInteger();
            java.math.BigInteger bigInteger3 = eCFieldElement2.toBigInteger();
            return new com.android.internal.org.bouncycastle.math.ec.ECFieldElement.Fp(this.q, this.r, modReduce(bigInteger.multiply(bigInteger).add(bigInteger2.multiply(bigInteger3))));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement invert() {
            return new com.android.internal.org.bouncycastle.math.ec.ECFieldElement.Fp(this.q, this.r, modInverse(this.x));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement sqrt() {
            if (isZero() || isOne()) {
                return this;
            }
            if (!this.q.testBit(0)) {
                throw new java.lang.RuntimeException("not done yet");
            }
            if (this.q.testBit(1)) {
                return checkSqrt(new com.android.internal.org.bouncycastle.math.ec.ECFieldElement.Fp(this.q, this.r, this.x.modPow(this.q.shiftRight(2).add(com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE), this.q)));
            }
            if (this.q.testBit(2)) {
                java.math.BigInteger modPow = this.x.modPow(this.q.shiftRight(3), this.q);
                java.math.BigInteger modMult = modMult(modPow, this.x);
                if (modMult(modMult, modPow).equals(com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE)) {
                    return checkSqrt(new com.android.internal.org.bouncycastle.math.ec.ECFieldElement.Fp(this.q, this.r, modMult));
                }
                return checkSqrt(new com.android.internal.org.bouncycastle.math.ec.ECFieldElement.Fp(this.q, this.r, modMult(modMult, com.android.internal.org.bouncycastle.math.ec.ECConstants.TWO.modPow(this.q.shiftRight(2), this.q))));
            }
            java.math.BigInteger shiftRight = this.q.shiftRight(1);
            if (!this.x.modPow(shiftRight, this.q).equals(com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE)) {
                return null;
            }
            java.math.BigInteger bigInteger = this.x;
            java.math.BigInteger modDouble = modDouble(modDouble(bigInteger));
            java.math.BigInteger add = shiftRight.add(com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE);
            java.math.BigInteger subtract = this.q.subtract(com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE);
            java.util.Random random = new java.util.Random();
            while (true) {
                java.math.BigInteger bigInteger2 = new java.math.BigInteger(this.q.bitLength(), random);
                if (bigInteger2.compareTo(this.q) < 0 && modReduce(bigInteger2.multiply(bigInteger2).subtract(modDouble)).modPow(shiftRight, this.q).equals(subtract)) {
                    java.math.BigInteger[] lucasSequence = lucasSequence(bigInteger2, bigInteger, add);
                    java.math.BigInteger bigInteger3 = lucasSequence[0];
                    java.math.BigInteger bigInteger4 = lucasSequence[1];
                    if (modMult(bigInteger4, bigInteger4).equals(modDouble)) {
                        return new com.android.internal.org.bouncycastle.math.ec.ECFieldElement.Fp(this.q, this.r, modHalfAbs(bigInteger4));
                    }
                    if (!bigInteger3.equals(com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE) && !bigInteger3.equals(subtract)) {
                        return null;
                    }
                }
            }
        }

        private com.android.internal.org.bouncycastle.math.ec.ECFieldElement checkSqrt(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
            if (eCFieldElement.square().equals(this)) {
                return eCFieldElement;
            }
            return null;
        }

        private java.math.BigInteger[] lucasSequence(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2, java.math.BigInteger bigInteger3) {
            int bitLength = bigInteger3.bitLength();
            int lowestSetBit = bigInteger3.getLowestSetBit();
            java.math.BigInteger bigInteger4 = com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE;
            java.math.BigInteger bigInteger5 = com.android.internal.org.bouncycastle.math.ec.ECConstants.TWO;
            java.math.BigInteger bigInteger6 = com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE;
            java.math.BigInteger bigInteger7 = com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE;
            java.math.BigInteger bigInteger8 = bigInteger;
            for (int i = bitLength - 1; i >= lowestSetBit + 1; i--) {
                bigInteger6 = modMult(bigInteger6, bigInteger7);
                if (bigInteger3.testBit(i)) {
                    bigInteger7 = modMult(bigInteger6, bigInteger2);
                    bigInteger4 = modMult(bigInteger4, bigInteger8);
                    bigInteger5 = modReduce(bigInteger8.multiply(bigInteger5).subtract(bigInteger.multiply(bigInteger6)));
                    bigInteger8 = modReduce(bigInteger8.multiply(bigInteger8).subtract(bigInteger7.shiftLeft(1)));
                } else {
                    bigInteger4 = modReduce(bigInteger4.multiply(bigInteger5).subtract(bigInteger6));
                    java.math.BigInteger modReduce = modReduce(bigInteger8.multiply(bigInteger5).subtract(bigInteger.multiply(bigInteger6)));
                    bigInteger5 = modReduce(bigInteger5.multiply(bigInteger5).subtract(bigInteger6.shiftLeft(1)));
                    bigInteger8 = modReduce;
                    bigInteger7 = bigInteger6;
                }
            }
            java.math.BigInteger modMult = modMult(bigInteger6, bigInteger7);
            java.math.BigInteger modMult2 = modMult(modMult, bigInteger2);
            java.math.BigInteger modReduce2 = modReduce(bigInteger4.multiply(bigInteger5).subtract(modMult));
            java.math.BigInteger modReduce3 = modReduce(bigInteger8.multiply(bigInteger5).subtract(bigInteger.multiply(modMult)));
            java.math.BigInteger modMult3 = modMult(modMult, modMult2);
            for (int i2 = 1; i2 <= lowestSetBit; i2++) {
                modReduce2 = modMult(modReduce2, modReduce3);
                modReduce3 = modReduce(modReduce3.multiply(modReduce3).subtract(modMult3.shiftLeft(1)));
                modMult3 = modMult(modMult3, modMult3);
            }
            return new java.math.BigInteger[]{modReduce2, modReduce3};
        }

        protected java.math.BigInteger modAdd(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
            java.math.BigInteger add = bigInteger.add(bigInteger2);
            if (add.compareTo(this.q) >= 0) {
                return add.subtract(this.q);
            }
            return add;
        }

        protected java.math.BigInteger modDouble(java.math.BigInteger bigInteger) {
            java.math.BigInteger shiftLeft = bigInteger.shiftLeft(1);
            if (shiftLeft.compareTo(this.q) >= 0) {
                return shiftLeft.subtract(this.q);
            }
            return shiftLeft;
        }

        protected java.math.BigInteger modHalf(java.math.BigInteger bigInteger) {
            if (bigInteger.testBit(0)) {
                bigInteger = this.q.add(bigInteger);
            }
            return bigInteger.shiftRight(1);
        }

        protected java.math.BigInteger modHalfAbs(java.math.BigInteger bigInteger) {
            if (bigInteger.testBit(0)) {
                bigInteger = this.q.subtract(bigInteger);
            }
            return bigInteger.shiftRight(1);
        }

        protected java.math.BigInteger modInverse(java.math.BigInteger bigInteger) {
            return com.android.internal.org.bouncycastle.util.BigIntegers.modOddInverse(this.q, bigInteger);
        }

        protected java.math.BigInteger modMult(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
            return modReduce(bigInteger.multiply(bigInteger2));
        }

        protected java.math.BigInteger modReduce(java.math.BigInteger bigInteger) {
            if (this.r != null) {
                boolean z = bigInteger.signum() < 0;
                if (z) {
                    bigInteger = bigInteger.abs();
                }
                int bitLength = this.q.bitLength();
                boolean equals = this.r.equals(com.android.internal.org.bouncycastle.math.ec.ECConstants.ONE);
                while (bigInteger.bitLength() > bitLength + 1) {
                    java.math.BigInteger shiftRight = bigInteger.shiftRight(bitLength);
                    java.math.BigInteger subtract = bigInteger.subtract(shiftRight.shiftLeft(bitLength));
                    if (!equals) {
                        shiftRight = shiftRight.multiply(this.r);
                    }
                    bigInteger = shiftRight.add(subtract);
                }
                while (bigInteger.compareTo(this.q) >= 0) {
                    bigInteger = bigInteger.subtract(this.q);
                }
                if (z && bigInteger.signum() != 0) {
                    return this.q.subtract(bigInteger);
                }
                return bigInteger;
            }
            return bigInteger.mod(this.q);
        }

        protected java.math.BigInteger modSubtract(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
            java.math.BigInteger subtract = bigInteger.subtract(bigInteger2);
            if (subtract.signum() < 0) {
                return subtract.add(this.q);
            }
            return subtract;
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof com.android.internal.org.bouncycastle.math.ec.ECFieldElement.Fp)) {
                return false;
            }
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement.Fp fp = (com.android.internal.org.bouncycastle.math.ec.ECFieldElement.Fp) obj;
            return this.q.equals(fp.q) && this.x.equals(fp.x);
        }

        public int hashCode() {
            return this.q.hashCode() ^ this.x.hashCode();
        }
    }

    public static abstract class AbstractF2m extends com.android.internal.org.bouncycastle.math.ec.ECFieldElement {
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement halfTrace() {
            int fieldSize = getFieldSize();
            if ((fieldSize & 1) == 0) {
                throw new java.lang.IllegalStateException("Half-trace only defined for odd m");
            }
            int i = (fieldSize + 1) >>> 1;
            int numberOfLeadingZeros = 31 - com.android.internal.org.bouncycastle.util.Integers.numberOfLeadingZeros(i);
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement = this;
            int i2 = 1;
            while (numberOfLeadingZeros > 0) {
                eCFieldElement = eCFieldElement.squarePow(i2 << 1).add(eCFieldElement);
                numberOfLeadingZeros--;
                i2 = i >>> numberOfLeadingZeros;
                if ((i2 & 1) != 0) {
                    eCFieldElement = eCFieldElement.squarePow(2).add(this);
                }
            }
            return eCFieldElement;
        }

        public boolean hasFastTrace() {
            return false;
        }

        public int trace() {
            int fieldSize = getFieldSize();
            int numberOfLeadingZeros = 31 - com.android.internal.org.bouncycastle.util.Integers.numberOfLeadingZeros(fieldSize);
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement = this;
            int i = 1;
            while (numberOfLeadingZeros > 0) {
                eCFieldElement = eCFieldElement.squarePow(i).add(eCFieldElement);
                numberOfLeadingZeros--;
                i = fieldSize >>> numberOfLeadingZeros;
                if ((i & 1) != 0) {
                    eCFieldElement = eCFieldElement.square().add(this);
                }
            }
            if (eCFieldElement.isZero()) {
                return 0;
            }
            if (eCFieldElement.isOne()) {
                return 1;
            }
            throw new java.lang.IllegalStateException("Internal error in trace calculation");
        }
    }

    public static class F2m extends com.android.internal.org.bouncycastle.math.ec.ECFieldElement.AbstractF2m {
        public static final int GNB = 1;
        public static final int PPB = 3;
        public static final int TPB = 2;
        private int[] ks;
        private int m;
        private int representation;
        com.android.internal.org.bouncycastle.math.ec.LongArray x;

        public F2m(int i, int i2, int i3, int i4, java.math.BigInteger bigInteger) {
            if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.bitLength() > i) {
                throw new java.lang.IllegalArgumentException("x value invalid in F2m field element");
            }
            if (i3 == 0 && i4 == 0) {
                this.representation = 2;
                this.ks = new int[]{i2};
            } else {
                if (i3 >= i4) {
                    throw new java.lang.IllegalArgumentException("k2 must be smaller than k3");
                }
                if (i3 <= 0) {
                    throw new java.lang.IllegalArgumentException("k2 must be larger than 0");
                }
                this.representation = 3;
                this.ks = new int[]{i2, i3, i4};
            }
            this.m = i;
            this.x = new com.android.internal.org.bouncycastle.math.ec.LongArray(bigInteger);
        }

        F2m(int i, int[] iArr, com.android.internal.org.bouncycastle.math.ec.LongArray longArray) {
            this.m = i;
            this.representation = iArr.length == 1 ? 2 : 3;
            this.ks = iArr;
            this.x = longArray;
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public int bitLength() {
            return this.x.degree();
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public boolean isOne() {
            return this.x.isOne();
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public boolean isZero() {
            return this.x.isZero();
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public boolean testBitZero() {
            return this.x.testBitZero();
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public java.math.BigInteger toBigInteger() {
            return this.x.toBigInteger();
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public java.lang.String getFieldName() {
            return "F2m";
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public int getFieldSize() {
            return this.m;
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement add(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
            com.android.internal.org.bouncycastle.math.ec.LongArray longArray = (com.android.internal.org.bouncycastle.math.ec.LongArray) this.x.clone();
            longArray.addShiftedByWords(((com.android.internal.org.bouncycastle.math.ec.ECFieldElement.F2m) eCFieldElement).x, 0);
            return new com.android.internal.org.bouncycastle.math.ec.ECFieldElement.F2m(this.m, this.ks, longArray);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement addOne() {
            return new com.android.internal.org.bouncycastle.math.ec.ECFieldElement.F2m(this.m, this.ks, this.x.addOne());
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement subtract(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
            return add(eCFieldElement);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiply(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
            return new com.android.internal.org.bouncycastle.math.ec.ECFieldElement.F2m(this.m, this.ks, this.x.modMultiply(((com.android.internal.org.bouncycastle.math.ec.ECFieldElement.F2m) eCFieldElement).x, this.m, this.ks));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiplyMinusProduct(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement3) {
            return multiplyPlusProduct(eCFieldElement, eCFieldElement2, eCFieldElement3);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement multiplyPlusProduct(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement3) {
            com.android.internal.org.bouncycastle.math.ec.LongArray longArray = this.x;
            com.android.internal.org.bouncycastle.math.ec.LongArray longArray2 = ((com.android.internal.org.bouncycastle.math.ec.ECFieldElement.F2m) eCFieldElement).x;
            com.android.internal.org.bouncycastle.math.ec.LongArray longArray3 = ((com.android.internal.org.bouncycastle.math.ec.ECFieldElement.F2m) eCFieldElement2).x;
            com.android.internal.org.bouncycastle.math.ec.LongArray longArray4 = ((com.android.internal.org.bouncycastle.math.ec.ECFieldElement.F2m) eCFieldElement3).x;
            com.android.internal.org.bouncycastle.math.ec.LongArray multiply = longArray.multiply(longArray2, this.m, this.ks);
            com.android.internal.org.bouncycastle.math.ec.LongArray multiply2 = longArray3.multiply(longArray4, this.m, this.ks);
            if (multiply == longArray || multiply == longArray2) {
                multiply = (com.android.internal.org.bouncycastle.math.ec.LongArray) multiply.clone();
            }
            multiply.addShiftedByWords(multiply2, 0);
            multiply.reduce(this.m, this.ks);
            return new com.android.internal.org.bouncycastle.math.ec.ECFieldElement.F2m(this.m, this.ks, multiply);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement divide(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement) {
            return multiply(eCFieldElement.invert());
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement negate() {
            return this;
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement square() {
            return new com.android.internal.org.bouncycastle.math.ec.ECFieldElement.F2m(this.m, this.ks, this.x.modSquare(this.m, this.ks));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement squareMinusProduct(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2) {
            return squarePlusProduct(eCFieldElement, eCFieldElement2);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement squarePlusProduct(com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement, com.android.internal.org.bouncycastle.math.ec.ECFieldElement eCFieldElement2) {
            com.android.internal.org.bouncycastle.math.ec.LongArray longArray = this.x;
            com.android.internal.org.bouncycastle.math.ec.LongArray longArray2 = ((com.android.internal.org.bouncycastle.math.ec.ECFieldElement.F2m) eCFieldElement).x;
            com.android.internal.org.bouncycastle.math.ec.LongArray longArray3 = ((com.android.internal.org.bouncycastle.math.ec.ECFieldElement.F2m) eCFieldElement2).x;
            com.android.internal.org.bouncycastle.math.ec.LongArray square = longArray.square(this.m, this.ks);
            com.android.internal.org.bouncycastle.math.ec.LongArray multiply = longArray2.multiply(longArray3, this.m, this.ks);
            if (square == longArray) {
                square = (com.android.internal.org.bouncycastle.math.ec.LongArray) square.clone();
            }
            square.addShiftedByWords(multiply, 0);
            square.reduce(this.m, this.ks);
            return new com.android.internal.org.bouncycastle.math.ec.ECFieldElement.F2m(this.m, this.ks, square);
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement squarePow(int i) {
            return i < 1 ? this : new com.android.internal.org.bouncycastle.math.ec.ECFieldElement.F2m(this.m, this.ks, this.x.modSquareN(i, this.m, this.ks));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement invert() {
            return new com.android.internal.org.bouncycastle.math.ec.ECFieldElement.F2m(this.m, this.ks, this.x.modInverse(this.m, this.ks));
        }

        @Override // com.android.internal.org.bouncycastle.math.ec.ECFieldElement
        public com.android.internal.org.bouncycastle.math.ec.ECFieldElement sqrt() {
            return (this.x.isZero() || this.x.isOne()) ? this : squarePow(this.m - 1);
        }

        public int getRepresentation() {
            return this.representation;
        }

        public int getM() {
            return this.m;
        }

        public int getK1() {
            return this.ks[0];
        }

        public int getK2() {
            if (this.ks.length >= 2) {
                return this.ks[1];
            }
            return 0;
        }

        public int getK3() {
            if (this.ks.length >= 3) {
                return this.ks[2];
            }
            return 0;
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof com.android.internal.org.bouncycastle.math.ec.ECFieldElement.F2m)) {
                return false;
            }
            com.android.internal.org.bouncycastle.math.ec.ECFieldElement.F2m f2m = (com.android.internal.org.bouncycastle.math.ec.ECFieldElement.F2m) obj;
            return this.m == f2m.m && this.representation == f2m.representation && com.android.internal.org.bouncycastle.util.Arrays.areEqual(this.ks, f2m.ks) && this.x.equals(f2m.x);
        }

        public int hashCode() {
            return (this.x.hashCode() ^ this.m) ^ com.android.internal.org.bouncycastle.util.Arrays.hashCode(this.ks);
        }
    }
}
