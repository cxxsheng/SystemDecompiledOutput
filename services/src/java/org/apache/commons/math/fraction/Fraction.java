package org.apache.commons.math.fraction;

/* loaded from: classes3.dex */
public class Fraction extends java.lang.Number implements org.apache.commons.math.FieldElement<org.apache.commons.math.fraction.Fraction>, java.lang.Comparable<org.apache.commons.math.fraction.Fraction>, java.io.Serializable {
    private static final long serialVersionUID = 3698073679419233275L;
    private final int denominator;
    private final int numerator;
    public static final org.apache.commons.math.fraction.Fraction TWO = new org.apache.commons.math.fraction.Fraction(2, 1);
    public static final org.apache.commons.math.fraction.Fraction ONE = new org.apache.commons.math.fraction.Fraction(1, 1);
    public static final org.apache.commons.math.fraction.Fraction ZERO = new org.apache.commons.math.fraction.Fraction(0, 1);
    public static final org.apache.commons.math.fraction.Fraction FOUR_FIFTHS = new org.apache.commons.math.fraction.Fraction(4, 5);
    public static final org.apache.commons.math.fraction.Fraction ONE_FIFTH = new org.apache.commons.math.fraction.Fraction(1, 5);
    public static final org.apache.commons.math.fraction.Fraction ONE_HALF = new org.apache.commons.math.fraction.Fraction(1, 2);
    public static final org.apache.commons.math.fraction.Fraction ONE_QUARTER = new org.apache.commons.math.fraction.Fraction(1, 4);
    public static final org.apache.commons.math.fraction.Fraction ONE_THIRD = new org.apache.commons.math.fraction.Fraction(1, 3);
    public static final org.apache.commons.math.fraction.Fraction THREE_FIFTHS = new org.apache.commons.math.fraction.Fraction(3, 5);
    public static final org.apache.commons.math.fraction.Fraction THREE_QUARTERS = new org.apache.commons.math.fraction.Fraction(3, 4);
    public static final org.apache.commons.math.fraction.Fraction TWO_FIFTHS = new org.apache.commons.math.fraction.Fraction(2, 5);
    public static final org.apache.commons.math.fraction.Fraction TWO_QUARTERS = new org.apache.commons.math.fraction.Fraction(2, 4);
    public static final org.apache.commons.math.fraction.Fraction TWO_THIRDS = new org.apache.commons.math.fraction.Fraction(2, 3);
    public static final org.apache.commons.math.fraction.Fraction MINUS_ONE = new org.apache.commons.math.fraction.Fraction(-1, 1);

    public Fraction(double d) throws org.apache.commons.math.fraction.FractionConversionException {
        this(d, 1.0E-5d, 100);
    }

    public Fraction(double d, double d2, int i) throws org.apache.commons.math.fraction.FractionConversionException {
        this(d, d2, Integer.MAX_VALUE, i);
    }

    public Fraction(double d, int i) throws org.apache.commons.math.fraction.FractionConversionException {
        this(d, 0.0d, i, 100);
    }

    private Fraction(double d, double d2, int i, int i2) throws org.apache.commons.math.fraction.FractionConversionException {
        long j;
        long j2;
        long j3;
        long j4;
        long j5;
        long floor = (long) org.apache.commons.math.util.FastMath.floor(d);
        if (floor > 2147483647L) {
            throw new org.apache.commons.math.fraction.FractionConversionException(d, floor, 1L);
        }
        int i3 = 1;
        if (org.apache.commons.math.util.FastMath.abs(floor - d) < d2) {
            this.numerator = (int) floor;
            this.denominator = 1;
            return;
        }
        int i4 = 0;
        double d3 = d;
        long j6 = 1;
        long j7 = 0;
        boolean z = false;
        long j8 = 1;
        long j9 = floor;
        while (true) {
            i4 += i3;
            double d4 = 1.0d / (d3 - floor);
            long floor2 = (long) org.apache.commons.math.util.FastMath.floor(d4);
            long j10 = j9;
            j = (floor2 * j9) + j8;
            long j11 = floor;
            j2 = (floor2 * j6) + j7;
            if (j > 2147483647L || j2 > 2147483647L) {
                break;
            }
            long j12 = floor2;
            boolean z2 = z;
            double d5 = j / j2;
            if (i4 < i2 && org.apache.commons.math.util.FastMath.abs(d5 - d) > d2 && j2 < i) {
                j4 = j2;
                j3 = j;
                j7 = j6;
                d3 = d4;
                j5 = j10;
                z = z2;
            } else {
                j3 = j10;
                j12 = j11;
                z = true;
                long j13 = j8;
                j4 = j6;
                j5 = j13;
            }
            if (!z) {
                j9 = j3;
                floor = j12;
                i3 = 1;
                long j14 = j4;
                j8 = j5;
                j6 = j14;
            } else {
                if (i4 >= i2) {
                    throw new org.apache.commons.math.fraction.FractionConversionException(d, i2);
                }
                if (j2 < i) {
                    this.numerator = (int) j;
                    this.denominator = (int) j2;
                    return;
                } else {
                    this.numerator = (int) j3;
                    this.denominator = (int) j4;
                    return;
                }
            }
        }
        throw new org.apache.commons.math.fraction.FractionConversionException(d, j, j2);
    }

    public Fraction(int i) {
        this(i, 1);
    }

    public Fraction(int i, int i2) {
        if (i2 == 0) {
            throw org.apache.commons.math.MathRuntimeException.createArithmeticException(org.apache.commons.math.exception.util.LocalizedFormats.ZERO_DENOMINATOR_IN_FRACTION, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        }
        if (i2 < 0) {
            if (i == Integer.MIN_VALUE || i2 == Integer.MIN_VALUE) {
                throw org.apache.commons.math.MathRuntimeException.createArithmeticException(org.apache.commons.math.exception.util.LocalizedFormats.OVERFLOW_IN_FRACTION, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
            }
            i = -i;
            i2 = -i2;
        }
        int gcd = org.apache.commons.math.util.MathUtils.gcd(i, i2);
        if (gcd > 1) {
            i /= gcd;
            i2 /= gcd;
        }
        if (i2 < 0) {
            i = -i;
            i2 = -i2;
        }
        this.numerator = i;
        this.denominator = i2;
    }

    public org.apache.commons.math.fraction.Fraction abs() {
        if (this.numerator >= 0) {
            return this;
        }
        return negate();
    }

    @Override // java.lang.Comparable
    public int compareTo(org.apache.commons.math.fraction.Fraction fraction) {
        long j = this.numerator * fraction.denominator;
        long j2 = this.denominator * fraction.numerator;
        if (j < j2) {
            return -1;
        }
        return j > j2 ? 1 : 0;
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return this.numerator / this.denominator;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof org.apache.commons.math.fraction.Fraction)) {
            return false;
        }
        org.apache.commons.math.fraction.Fraction fraction = (org.apache.commons.math.fraction.Fraction) obj;
        return this.numerator == fraction.numerator && this.denominator == fraction.denominator;
    }

    @Override // java.lang.Number
    public float floatValue() {
        return (float) doubleValue();
    }

    public int getDenominator() {
        return this.denominator;
    }

    public int getNumerator() {
        return this.numerator;
    }

    public int hashCode() {
        return ((this.numerator + 629) * 37) + this.denominator;
    }

    @Override // java.lang.Number
    public int intValue() {
        return (int) doubleValue();
    }

    @Override // java.lang.Number
    public long longValue() {
        return (long) doubleValue();
    }

    public org.apache.commons.math.fraction.Fraction negate() {
        if (this.numerator == Integer.MIN_VALUE) {
            throw org.apache.commons.math.MathRuntimeException.createArithmeticException(org.apache.commons.math.exception.util.LocalizedFormats.OVERFLOW_IN_FRACTION, java.lang.Integer.valueOf(this.numerator), java.lang.Integer.valueOf(this.denominator));
        }
        return new org.apache.commons.math.fraction.Fraction(-this.numerator, this.denominator);
    }

    public org.apache.commons.math.fraction.Fraction reciprocal() {
        return new org.apache.commons.math.fraction.Fraction(this.denominator, this.numerator);
    }

    @Override // org.apache.commons.math.FieldElement
    public org.apache.commons.math.fraction.Fraction add(org.apache.commons.math.fraction.Fraction fraction) {
        return addSub(fraction, true);
    }

    public org.apache.commons.math.fraction.Fraction add(int i) {
        return new org.apache.commons.math.fraction.Fraction(this.numerator + (i * this.denominator), this.denominator);
    }

    @Override // org.apache.commons.math.FieldElement
    public org.apache.commons.math.fraction.Fraction subtract(org.apache.commons.math.fraction.Fraction fraction) {
        return addSub(fraction, false);
    }

    public org.apache.commons.math.fraction.Fraction subtract(int i) {
        return new org.apache.commons.math.fraction.Fraction(this.numerator - (i * this.denominator), this.denominator);
    }

    private org.apache.commons.math.fraction.Fraction addSub(org.apache.commons.math.fraction.Fraction fraction, boolean z) {
        if (fraction == null) {
            throw new org.apache.commons.math.exception.NullArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.FRACTION);
        }
        if (this.numerator == 0) {
            return z ? fraction : fraction.negate();
        }
        if (fraction.numerator == 0) {
            return this;
        }
        int gcd = org.apache.commons.math.util.MathUtils.gcd(this.denominator, fraction.denominator);
        if (gcd == 1) {
            int mulAndCheck = org.apache.commons.math.util.MathUtils.mulAndCheck(this.numerator, fraction.denominator);
            int mulAndCheck2 = org.apache.commons.math.util.MathUtils.mulAndCheck(fraction.numerator, this.denominator);
            return new org.apache.commons.math.fraction.Fraction(z ? org.apache.commons.math.util.MathUtils.addAndCheck(mulAndCheck, mulAndCheck2) : org.apache.commons.math.util.MathUtils.subAndCheck(mulAndCheck, mulAndCheck2), org.apache.commons.math.util.MathUtils.mulAndCheck(this.denominator, fraction.denominator));
        }
        java.math.BigInteger multiply = java.math.BigInteger.valueOf(this.numerator).multiply(java.math.BigInteger.valueOf(fraction.denominator / gcd));
        java.math.BigInteger multiply2 = java.math.BigInteger.valueOf(fraction.numerator).multiply(java.math.BigInteger.valueOf(this.denominator / gcd));
        java.math.BigInteger add = z ? multiply.add(multiply2) : multiply.subtract(multiply2);
        int intValue = add.mod(java.math.BigInteger.valueOf(gcd)).intValue();
        int gcd2 = intValue == 0 ? gcd : org.apache.commons.math.util.MathUtils.gcd(intValue, gcd);
        java.math.BigInteger divide = add.divide(java.math.BigInteger.valueOf(gcd2));
        if (divide.bitLength() > 31) {
            throw org.apache.commons.math.MathRuntimeException.createArithmeticException(org.apache.commons.math.exception.util.LocalizedFormats.NUMERATOR_OVERFLOW_AFTER_MULTIPLY, divide);
        }
        return new org.apache.commons.math.fraction.Fraction(divide.intValue(), org.apache.commons.math.util.MathUtils.mulAndCheck(this.denominator / gcd, fraction.denominator / gcd2));
    }

    @Override // org.apache.commons.math.FieldElement
    public org.apache.commons.math.fraction.Fraction multiply(org.apache.commons.math.fraction.Fraction fraction) {
        if (fraction == null) {
            throw new org.apache.commons.math.exception.NullArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.FRACTION);
        }
        if (this.numerator == 0 || fraction.numerator == 0) {
            return ZERO;
        }
        int gcd = org.apache.commons.math.util.MathUtils.gcd(this.numerator, fraction.denominator);
        int gcd2 = org.apache.commons.math.util.MathUtils.gcd(fraction.numerator, this.denominator);
        return getReducedFraction(org.apache.commons.math.util.MathUtils.mulAndCheck(this.numerator / gcd, fraction.numerator / gcd2), org.apache.commons.math.util.MathUtils.mulAndCheck(this.denominator / gcd2, fraction.denominator / gcd));
    }

    public org.apache.commons.math.fraction.Fraction multiply(int i) {
        return new org.apache.commons.math.fraction.Fraction(this.numerator * i, this.denominator);
    }

    @Override // org.apache.commons.math.FieldElement
    public org.apache.commons.math.fraction.Fraction divide(org.apache.commons.math.fraction.Fraction fraction) {
        if (fraction == null) {
            throw new org.apache.commons.math.exception.NullArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.FRACTION);
        }
        if (fraction.numerator == 0) {
            throw org.apache.commons.math.MathRuntimeException.createArithmeticException(org.apache.commons.math.exception.util.LocalizedFormats.ZERO_FRACTION_TO_DIVIDE_BY, java.lang.Integer.valueOf(fraction.numerator), java.lang.Integer.valueOf(fraction.denominator));
        }
        return multiply(fraction.reciprocal());
    }

    public org.apache.commons.math.fraction.Fraction divide(int i) {
        return new org.apache.commons.math.fraction.Fraction(this.numerator, this.denominator * i);
    }

    public static org.apache.commons.math.fraction.Fraction getReducedFraction(int i, int i2) {
        if (i2 == 0) {
            throw org.apache.commons.math.MathRuntimeException.createArithmeticException(org.apache.commons.math.exception.util.LocalizedFormats.ZERO_DENOMINATOR_IN_FRACTION, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        }
        if (i == 0) {
            return ZERO;
        }
        if (i2 == Integer.MIN_VALUE && (i & 1) == 0) {
            i /= 2;
            i2 /= 2;
        }
        if (i2 < 0) {
            if (i == Integer.MIN_VALUE || i2 == Integer.MIN_VALUE) {
                throw org.apache.commons.math.MathRuntimeException.createArithmeticException(org.apache.commons.math.exception.util.LocalizedFormats.OVERFLOW_IN_FRACTION, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
            }
            i = -i;
            i2 = -i2;
        }
        int gcd = org.apache.commons.math.util.MathUtils.gcd(i, i2);
        return new org.apache.commons.math.fraction.Fraction(i / gcd, i2 / gcd);
    }

    public java.lang.String toString() {
        if (this.denominator == 1) {
            return java.lang.Integer.toString(this.numerator);
        }
        if (this.numerator == 0) {
            return "0";
        }
        return this.numerator + " / " + this.denominator;
    }

    @Override // org.apache.commons.math.FieldElement
    public org.apache.commons.math.Field<org.apache.commons.math.fraction.Fraction> getField() {
        return org.apache.commons.math.fraction.FractionField.getInstance();
    }
}
