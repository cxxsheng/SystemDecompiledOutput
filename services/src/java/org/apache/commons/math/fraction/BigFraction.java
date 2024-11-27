package org.apache.commons.math.fraction;

/* loaded from: classes3.dex */
public class BigFraction extends java.lang.Number implements org.apache.commons.math.FieldElement<org.apache.commons.math.fraction.BigFraction>, java.lang.Comparable<org.apache.commons.math.fraction.BigFraction>, java.io.Serializable {
    private static final long serialVersionUID = -5630213147331578515L;
    private final java.math.BigInteger denominator;
    private final java.math.BigInteger numerator;
    public static final org.apache.commons.math.fraction.BigFraction TWO = new org.apache.commons.math.fraction.BigFraction(2);
    public static final org.apache.commons.math.fraction.BigFraction ONE = new org.apache.commons.math.fraction.BigFraction(1);
    public static final org.apache.commons.math.fraction.BigFraction ZERO = new org.apache.commons.math.fraction.BigFraction(0);
    public static final org.apache.commons.math.fraction.BigFraction MINUS_ONE = new org.apache.commons.math.fraction.BigFraction(-1);
    public static final org.apache.commons.math.fraction.BigFraction FOUR_FIFTHS = new org.apache.commons.math.fraction.BigFraction(4, 5);
    public static final org.apache.commons.math.fraction.BigFraction ONE_FIFTH = new org.apache.commons.math.fraction.BigFraction(1, 5);
    public static final org.apache.commons.math.fraction.BigFraction ONE_HALF = new org.apache.commons.math.fraction.BigFraction(1, 2);
    public static final org.apache.commons.math.fraction.BigFraction ONE_QUARTER = new org.apache.commons.math.fraction.BigFraction(1, 4);
    public static final org.apache.commons.math.fraction.BigFraction ONE_THIRD = new org.apache.commons.math.fraction.BigFraction(1, 3);
    public static final org.apache.commons.math.fraction.BigFraction THREE_FIFTHS = new org.apache.commons.math.fraction.BigFraction(3, 5);
    public static final org.apache.commons.math.fraction.BigFraction THREE_QUARTERS = new org.apache.commons.math.fraction.BigFraction(3, 4);
    public static final org.apache.commons.math.fraction.BigFraction TWO_FIFTHS = new org.apache.commons.math.fraction.BigFraction(2, 5);
    public static final org.apache.commons.math.fraction.BigFraction TWO_QUARTERS = new org.apache.commons.math.fraction.BigFraction(2, 4);
    public static final org.apache.commons.math.fraction.BigFraction TWO_THIRDS = new org.apache.commons.math.fraction.BigFraction(2, 3);
    private static final java.math.BigInteger ONE_HUNDRED_DOUBLE = java.math.BigInteger.valueOf(100);

    public BigFraction(java.math.BigInteger bigInteger) {
        this(bigInteger, java.math.BigInteger.ONE);
    }

    public BigFraction(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) {
        if (bigInteger == null) {
            throw new java.lang.NullPointerException(org.apache.commons.math.exception.util.LocalizedFormats.NUMERATOR.getSourceString());
        }
        if (bigInteger2 == null) {
            throw new java.lang.NullPointerException(org.apache.commons.math.exception.util.LocalizedFormats.DENOMINATOR.getSourceString());
        }
        if (java.math.BigInteger.ZERO.equals(bigInteger2)) {
            throw org.apache.commons.math.MathRuntimeException.createArithmeticException(org.apache.commons.math.exception.util.LocalizedFormats.ZERO_DENOMINATOR, new java.lang.Object[0]);
        }
        if (java.math.BigInteger.ZERO.equals(bigInteger)) {
            this.numerator = java.math.BigInteger.ZERO;
            this.denominator = java.math.BigInteger.ONE;
            return;
        }
        java.math.BigInteger gcd = bigInteger.gcd(bigInteger2);
        if (java.math.BigInteger.ONE.compareTo(gcd) < 0) {
            bigInteger = bigInteger.divide(gcd);
            bigInteger2 = bigInteger2.divide(gcd);
        }
        if (java.math.BigInteger.ZERO.compareTo(bigInteger2) > 0) {
            bigInteger = bigInteger.negate();
            bigInteger2 = bigInteger2.negate();
        }
        this.numerator = bigInteger;
        this.denominator = bigInteger2;
    }

    public BigFraction(double d) throws java.lang.IllegalArgumentException {
        if (java.lang.Double.isNaN(d)) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NAN_VALUE_CONVERSION, new java.lang.Object[0]);
        }
        if (java.lang.Double.isInfinite(d)) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INFINITE_VALUE_CONVERSION, new java.lang.Object[0]);
        }
        long doubleToLongBits = java.lang.Double.doubleToLongBits(d);
        long j = Long.MIN_VALUE & doubleToLongBits;
        long j2 = 9218868437227405312L & doubleToLongBits;
        long j3 = doubleToLongBits & 4503599627370495L;
        j3 = j2 != 0 ? j3 | 4503599627370496L : j3;
        j3 = j != 0 ? -j3 : j3;
        int i = ((int) (j2 >> 52)) - 1075;
        while ((9007199254740990L & j3) != 0 && (1 & j3) == 0) {
            j3 >>= 1;
            i++;
        }
        if (i < 0) {
            this.numerator = java.math.BigInteger.valueOf(j3);
            this.denominator = java.math.BigInteger.ZERO.flipBit(-i);
        } else {
            this.numerator = java.math.BigInteger.valueOf(j3).multiply(java.math.BigInteger.ZERO.flipBit(i));
            this.denominator = java.math.BigInteger.ONE;
        }
    }

    public BigFraction(double d, double d2, int i) throws org.apache.commons.math.fraction.FractionConversionException {
        this(d, d2, Integer.MAX_VALUE, i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x00d6, code lost:
    
        throw new org.apache.commons.math.fraction.FractionConversionException(r36, r5, r7);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private BigFraction(double d, double d2, int i, int i2) throws org.apache.commons.math.fraction.FractionConversionException {
        long floor = (long) org.apache.commons.math.util.FastMath.floor(d);
        if (floor > 2147483647L) {
            throw new org.apache.commons.math.fraction.FractionConversionException(d, floor, 1L);
        }
        if (org.apache.commons.math.util.FastMath.abs(floor - d) < d2) {
            this.numerator = java.math.BigInteger.valueOf(floor);
            this.denominator = java.math.BigInteger.ONE;
            return;
        }
        double d3 = d;
        long j = 1;
        long j2 = 0;
        boolean z = false;
        long j3 = 1;
        int i3 = 0;
        long j4 = floor;
        while (true) {
            i3++;
            double d4 = 1.0d / (d3 - floor);
            long floor2 = (long) org.apache.commons.math.util.FastMath.floor(d4);
            long j5 = floor;
            long j6 = (floor2 * j4) + j3;
            long j7 = (floor2 * j) + j2;
            if (j6 > 2147483647L || j7 > 2147483647L) {
                break;
            }
            long j8 = j4;
            boolean z2 = z;
            long j9 = j3;
            double d5 = j6 / j7;
            if (i3 < i2 && org.apache.commons.math.util.FastMath.abs(d5 - d) > d2 && j7 < i) {
                j4 = j6;
                j2 = j;
                d3 = d4;
                j3 = j8;
                j5 = floor2;
                z = z2;
                j = j7;
            } else {
                z = true;
                j4 = j8;
                j3 = j9;
            }
            if (z) {
                if (i3 >= i2) {
                    throw new org.apache.commons.math.fraction.FractionConversionException(d, i2);
                }
                if (j7 < i) {
                    this.numerator = java.math.BigInteger.valueOf(j6);
                    this.denominator = java.math.BigInteger.valueOf(j7);
                    return;
                } else {
                    this.numerator = java.math.BigInteger.valueOf(j4);
                    this.denominator = java.math.BigInteger.valueOf(j);
                    return;
                }
            }
            floor = j5;
        }
    }

    public BigFraction(double d, int i) throws org.apache.commons.math.fraction.FractionConversionException {
        this(d, 0.0d, i, 100);
    }

    public BigFraction(int i) {
        this(java.math.BigInteger.valueOf(i), java.math.BigInteger.ONE);
    }

    public BigFraction(int i, int i2) {
        this(java.math.BigInteger.valueOf(i), java.math.BigInteger.valueOf(i2));
    }

    public BigFraction(long j) {
        this(java.math.BigInteger.valueOf(j), java.math.BigInteger.ONE);
    }

    public BigFraction(long j, long j2) {
        this(java.math.BigInteger.valueOf(j), java.math.BigInteger.valueOf(j2));
    }

    public static org.apache.commons.math.fraction.BigFraction getReducedFraction(int i, int i2) {
        if (i == 0) {
            return ZERO;
        }
        return new org.apache.commons.math.fraction.BigFraction(i, i2);
    }

    public org.apache.commons.math.fraction.BigFraction abs() {
        return java.math.BigInteger.ZERO.compareTo(this.numerator) <= 0 ? this : negate();
    }

    public org.apache.commons.math.fraction.BigFraction add(java.math.BigInteger bigInteger) {
        return new org.apache.commons.math.fraction.BigFraction(this.numerator.add(this.denominator.multiply(bigInteger)), this.denominator);
    }

    public org.apache.commons.math.fraction.BigFraction add(int i) {
        return add(java.math.BigInteger.valueOf(i));
    }

    public org.apache.commons.math.fraction.BigFraction add(long j) {
        return add(java.math.BigInteger.valueOf(j));
    }

    @Override // org.apache.commons.math.FieldElement
    public org.apache.commons.math.fraction.BigFraction add(org.apache.commons.math.fraction.BigFraction bigFraction) {
        java.math.BigInteger multiply;
        java.math.BigInteger bigInteger;
        if (bigFraction == null) {
            throw new java.lang.NullPointerException(org.apache.commons.math.exception.util.LocalizedFormats.FRACTION.getSourceString());
        }
        if (ZERO.equals(bigFraction)) {
            return this;
        }
        if (this.denominator.equals(bigFraction.denominator)) {
            bigInteger = this.numerator.add(bigFraction.numerator);
            multiply = this.denominator;
        } else {
            java.math.BigInteger add = this.numerator.multiply(bigFraction.denominator).add(bigFraction.numerator.multiply(this.denominator));
            multiply = this.denominator.multiply(bigFraction.denominator);
            bigInteger = add;
        }
        return new org.apache.commons.math.fraction.BigFraction(bigInteger, multiply);
    }

    public java.math.BigDecimal bigDecimalValue() {
        return new java.math.BigDecimal(this.numerator).divide(new java.math.BigDecimal(this.denominator));
    }

    public java.math.BigDecimal bigDecimalValue(int i) {
        return new java.math.BigDecimal(this.numerator).divide(new java.math.BigDecimal(this.denominator), i);
    }

    public java.math.BigDecimal bigDecimalValue(int i, int i2) {
        return new java.math.BigDecimal(this.numerator).divide(new java.math.BigDecimal(this.denominator), i, i2);
    }

    @Override // java.lang.Comparable
    public int compareTo(org.apache.commons.math.fraction.BigFraction bigFraction) {
        return this.numerator.multiply(bigFraction.denominator).compareTo(this.denominator.multiply(bigFraction.numerator));
    }

    public org.apache.commons.math.fraction.BigFraction divide(java.math.BigInteger bigInteger) {
        if (java.math.BigInteger.ZERO.equals(bigInteger)) {
            throw org.apache.commons.math.MathRuntimeException.createArithmeticException(org.apache.commons.math.exception.util.LocalizedFormats.ZERO_DENOMINATOR, new java.lang.Object[0]);
        }
        return new org.apache.commons.math.fraction.BigFraction(this.numerator, this.denominator.multiply(bigInteger));
    }

    public org.apache.commons.math.fraction.BigFraction divide(int i) {
        return divide(java.math.BigInteger.valueOf(i));
    }

    public org.apache.commons.math.fraction.BigFraction divide(long j) {
        return divide(java.math.BigInteger.valueOf(j));
    }

    @Override // org.apache.commons.math.FieldElement
    public org.apache.commons.math.fraction.BigFraction divide(org.apache.commons.math.fraction.BigFraction bigFraction) {
        if (bigFraction == null) {
            throw new java.lang.NullPointerException(org.apache.commons.math.exception.util.LocalizedFormats.FRACTION.getSourceString());
        }
        if (java.math.BigInteger.ZERO.equals(bigFraction.numerator)) {
            throw org.apache.commons.math.MathRuntimeException.createArithmeticException(org.apache.commons.math.exception.util.LocalizedFormats.ZERO_DENOMINATOR, new java.lang.Object[0]);
        }
        return multiply(bigFraction.reciprocal());
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return this.numerator.doubleValue() / this.denominator.doubleValue();
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof org.apache.commons.math.fraction.BigFraction)) {
            return false;
        }
        org.apache.commons.math.fraction.BigFraction reduce = ((org.apache.commons.math.fraction.BigFraction) obj).reduce();
        org.apache.commons.math.fraction.BigFraction reduce2 = reduce();
        return reduce2.numerator.equals(reduce.numerator) && reduce2.denominator.equals(reduce.denominator);
    }

    @Override // java.lang.Number
    public float floatValue() {
        return this.numerator.floatValue() / this.denominator.floatValue();
    }

    public java.math.BigInteger getDenominator() {
        return this.denominator;
    }

    public int getDenominatorAsInt() {
        return this.denominator.intValue();
    }

    public long getDenominatorAsLong() {
        return this.denominator.longValue();
    }

    public java.math.BigInteger getNumerator() {
        return this.numerator;
    }

    public int getNumeratorAsInt() {
        return this.numerator.intValue();
    }

    public long getNumeratorAsLong() {
        return this.numerator.longValue();
    }

    public int hashCode() {
        return ((this.numerator.hashCode() + 629) * 37) + this.denominator.hashCode();
    }

    @Override // java.lang.Number
    public int intValue() {
        return this.numerator.divide(this.denominator).intValue();
    }

    @Override // java.lang.Number
    public long longValue() {
        return this.numerator.divide(this.denominator).longValue();
    }

    public org.apache.commons.math.fraction.BigFraction multiply(java.math.BigInteger bigInteger) {
        if (bigInteger == null) {
            throw new java.lang.NullPointerException();
        }
        return new org.apache.commons.math.fraction.BigFraction(bigInteger.multiply(this.numerator), this.denominator);
    }

    public org.apache.commons.math.fraction.BigFraction multiply(int i) {
        return multiply(java.math.BigInteger.valueOf(i));
    }

    public org.apache.commons.math.fraction.BigFraction multiply(long j) {
        return multiply(java.math.BigInteger.valueOf(j));
    }

    @Override // org.apache.commons.math.FieldElement
    public org.apache.commons.math.fraction.BigFraction multiply(org.apache.commons.math.fraction.BigFraction bigFraction) {
        if (bigFraction == null) {
            throw new java.lang.NullPointerException(org.apache.commons.math.exception.util.LocalizedFormats.FRACTION.getSourceString());
        }
        if (this.numerator.equals(java.math.BigInteger.ZERO) || bigFraction.numerator.equals(java.math.BigInteger.ZERO)) {
            return ZERO;
        }
        return new org.apache.commons.math.fraction.BigFraction(this.numerator.multiply(bigFraction.numerator), this.denominator.multiply(bigFraction.denominator));
    }

    public org.apache.commons.math.fraction.BigFraction negate() {
        return new org.apache.commons.math.fraction.BigFraction(this.numerator.negate(), this.denominator);
    }

    public double percentageValue() {
        return this.numerator.divide(this.denominator).multiply(ONE_HUNDRED_DOUBLE).doubleValue();
    }

    public org.apache.commons.math.fraction.BigFraction pow(int i) {
        if (i < 0) {
            int i2 = -i;
            return new org.apache.commons.math.fraction.BigFraction(this.denominator.pow(i2), this.numerator.pow(i2));
        }
        return new org.apache.commons.math.fraction.BigFraction(this.numerator.pow(i), this.denominator.pow(i));
    }

    public org.apache.commons.math.fraction.BigFraction pow(long j) {
        if (j < 0) {
            long j2 = -j;
            return new org.apache.commons.math.fraction.BigFraction(org.apache.commons.math.util.MathUtils.pow(this.denominator, j2), org.apache.commons.math.util.MathUtils.pow(this.numerator, j2));
        }
        return new org.apache.commons.math.fraction.BigFraction(org.apache.commons.math.util.MathUtils.pow(this.numerator, j), org.apache.commons.math.util.MathUtils.pow(this.denominator, j));
    }

    public org.apache.commons.math.fraction.BigFraction pow(java.math.BigInteger bigInteger) {
        if (bigInteger.compareTo(java.math.BigInteger.ZERO) < 0) {
            java.math.BigInteger negate = bigInteger.negate();
            return new org.apache.commons.math.fraction.BigFraction(org.apache.commons.math.util.MathUtils.pow(this.denominator, negate), org.apache.commons.math.util.MathUtils.pow(this.numerator, negate));
        }
        return new org.apache.commons.math.fraction.BigFraction(org.apache.commons.math.util.MathUtils.pow(this.numerator, bigInteger), org.apache.commons.math.util.MathUtils.pow(this.denominator, bigInteger));
    }

    public double pow(double d) {
        return org.apache.commons.math.util.FastMath.pow(this.numerator.doubleValue(), d) / org.apache.commons.math.util.FastMath.pow(this.denominator.doubleValue(), d);
    }

    public org.apache.commons.math.fraction.BigFraction reciprocal() {
        return new org.apache.commons.math.fraction.BigFraction(this.denominator, this.numerator);
    }

    public org.apache.commons.math.fraction.BigFraction reduce() {
        java.math.BigInteger gcd = this.numerator.gcd(this.denominator);
        return new org.apache.commons.math.fraction.BigFraction(this.numerator.divide(gcd), this.denominator.divide(gcd));
    }

    public org.apache.commons.math.fraction.BigFraction subtract(java.math.BigInteger bigInteger) {
        if (bigInteger == null) {
            throw new java.lang.NullPointerException();
        }
        return new org.apache.commons.math.fraction.BigFraction(this.numerator.subtract(this.denominator.multiply(bigInteger)), this.denominator);
    }

    public org.apache.commons.math.fraction.BigFraction subtract(int i) {
        return subtract(java.math.BigInteger.valueOf(i));
    }

    public org.apache.commons.math.fraction.BigFraction subtract(long j) {
        return subtract(java.math.BigInteger.valueOf(j));
    }

    @Override // org.apache.commons.math.FieldElement
    public org.apache.commons.math.fraction.BigFraction subtract(org.apache.commons.math.fraction.BigFraction bigFraction) {
        java.math.BigInteger multiply;
        java.math.BigInteger bigInteger;
        if (bigFraction == null) {
            throw new java.lang.NullPointerException(org.apache.commons.math.exception.util.LocalizedFormats.FRACTION.getSourceString());
        }
        if (ZERO.equals(bigFraction)) {
            return this;
        }
        if (this.denominator.equals(bigFraction.denominator)) {
            bigInteger = this.numerator.subtract(bigFraction.numerator);
            multiply = this.denominator;
        } else {
            java.math.BigInteger subtract = this.numerator.multiply(bigFraction.denominator).subtract(bigFraction.numerator.multiply(this.denominator));
            multiply = this.denominator.multiply(bigFraction.denominator);
            bigInteger = subtract;
        }
        return new org.apache.commons.math.fraction.BigFraction(bigInteger, multiply);
    }

    public java.lang.String toString() {
        if (java.math.BigInteger.ONE.equals(this.denominator)) {
            return this.numerator.toString();
        }
        if (java.math.BigInteger.ZERO.equals(this.numerator)) {
            return "0";
        }
        return this.numerator + " / " + this.denominator;
    }

    @Override // org.apache.commons.math.FieldElement
    public org.apache.commons.math.Field<org.apache.commons.math.fraction.BigFraction> getField() {
        return org.apache.commons.math.fraction.BigFractionField.getInstance();
    }
}
