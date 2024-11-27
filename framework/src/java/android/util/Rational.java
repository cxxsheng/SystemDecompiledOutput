package android.util;

/* loaded from: classes3.dex */
public final class Rational extends java.lang.Number implements java.lang.Comparable<android.util.Rational> {
    private static final long serialVersionUID = 1;
    private final int mDenominator;
    private final int mNumerator;
    public static final android.util.Rational NaN = new android.util.Rational(0, 0);
    public static final android.util.Rational POSITIVE_INFINITY = new android.util.Rational(1, 0);
    public static final android.util.Rational NEGATIVE_INFINITY = new android.util.Rational(-1, 0);
    public static final android.util.Rational ZERO = new android.util.Rational(0, 1);

    public Rational(int i, int i2) {
        if (i2 < 0) {
            i = -i;
            i2 = -i2;
        }
        if (i2 == 0 && i > 0) {
            this.mNumerator = 1;
            this.mDenominator = 0;
            return;
        }
        if (i2 == 0 && i < 0) {
            this.mNumerator = -1;
            this.mDenominator = 0;
            return;
        }
        if (i2 == 0 && i == 0) {
            this.mNumerator = 0;
            this.mDenominator = 0;
        } else if (i == 0) {
            this.mNumerator = 0;
            this.mDenominator = 1;
        } else {
            int gcd = gcd(i, i2);
            this.mNumerator = i / gcd;
            this.mDenominator = i2 / gcd;
        }
    }

    public int getNumerator() {
        return this.mNumerator;
    }

    public int getDenominator() {
        return this.mDenominator;
    }

    public boolean isNaN() {
        return this.mDenominator == 0 && this.mNumerator == 0;
    }

    public boolean isInfinite() {
        return this.mNumerator != 0 && this.mDenominator == 0;
    }

    public boolean isFinite() {
        return this.mDenominator != 0;
    }

    public boolean isZero() {
        return isFinite() && this.mNumerator == 0;
    }

    private boolean isPosInf() {
        return this.mDenominator == 0 && this.mNumerator > 0;
    }

    private boolean isNegInf() {
        return this.mDenominator == 0 && this.mNumerator < 0;
    }

    public boolean equals(java.lang.Object obj) {
        return (obj instanceof android.util.Rational) && equals((android.util.Rational) obj);
    }

    private boolean equals(android.util.Rational rational) {
        return this.mNumerator == rational.mNumerator && this.mDenominator == rational.mDenominator;
    }

    public java.lang.String toString() {
        if (isNaN()) {
            return "NaN";
        }
        if (isPosInf()) {
            return "Infinity";
        }
        if (isNegInf()) {
            return "-Infinity";
        }
        return this.mNumerator + "/" + this.mDenominator;
    }

    public float toFloat() {
        return floatValue();
    }

    public int hashCode() {
        return ((this.mNumerator << 16) | (this.mNumerator >>> 16)) ^ this.mDenominator;
    }

    public static int gcd(int i, int i2) {
        int i3 = i;
        int i4 = i2;
        while (i4 != 0) {
            int i5 = i3 % i4;
            i3 = i4;
            i4 = i5;
        }
        return java.lang.Math.abs(i3);
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return this.mNumerator / this.mDenominator;
    }

    @Override // java.lang.Number
    public float floatValue() {
        return this.mNumerator / this.mDenominator;
    }

    @Override // java.lang.Number
    public int intValue() {
        if (isPosInf()) {
            return Integer.MAX_VALUE;
        }
        if (isNegInf()) {
            return Integer.MIN_VALUE;
        }
        if (isNaN()) {
            return 0;
        }
        return this.mNumerator / this.mDenominator;
    }

    @Override // java.lang.Number
    public long longValue() {
        if (isPosInf()) {
            return Long.MAX_VALUE;
        }
        if (isNegInf()) {
            return Long.MIN_VALUE;
        }
        if (isNaN()) {
            return 0L;
        }
        return this.mNumerator / this.mDenominator;
    }

    @Override // java.lang.Number
    public short shortValue() {
        return (short) intValue();
    }

    @Override // java.lang.Comparable
    public int compareTo(android.util.Rational rational) {
        com.android.internal.util.Preconditions.checkNotNull(rational, "another must not be null");
        if (equals(rational)) {
            return 0;
        }
        if (isNaN()) {
            return 1;
        }
        if (rational.isNaN()) {
            return -1;
        }
        if (isPosInf() || rational.isNegInf()) {
            return 1;
        }
        if (isNegInf() || rational.isPosInf()) {
            return -1;
        }
        long j = this.mNumerator * rational.mDenominator;
        long j2 = rational.mNumerator * this.mDenominator;
        if (j < j2) {
            return -1;
        }
        return j > j2 ? 1 : 0;
    }

    private void readObject(java.io.ObjectInputStream objectInputStream) throws java.io.IOException, java.lang.ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.mNumerator == 0) {
            if (this.mDenominator == 1 || this.mDenominator == 0) {
            } else {
                throw new java.io.InvalidObjectException("Rational must be deserialized from a reduced form for zero values");
            }
        } else if (this.mDenominator == 0) {
            if (this.mNumerator == 1 || this.mNumerator == -1) {
            } else {
                throw new java.io.InvalidObjectException("Rational must be deserialized from a reduced form for infinity values");
            }
        } else if (gcd(this.mNumerator, this.mDenominator) > 1) {
            throw new java.io.InvalidObjectException("Rational must be deserialized from a reduced form for finite values");
        }
    }

    private static java.lang.NumberFormatException invalidRational(java.lang.String str) {
        throw new java.lang.NumberFormatException("Invalid Rational: \"" + str + "\"");
    }

    public static android.util.Rational parseRational(java.lang.String str) throws java.lang.NumberFormatException {
        com.android.internal.util.Preconditions.checkNotNull(str, "string must not be null");
        if (str.equals("NaN")) {
            return NaN;
        }
        if (str.equals("Infinity")) {
            return POSITIVE_INFINITY;
        }
        if (str.equals("-Infinity")) {
            return NEGATIVE_INFINITY;
        }
        int indexOf = str.indexOf(58);
        if (indexOf < 0) {
            indexOf = str.indexOf(47);
        }
        if (indexOf < 0) {
            throw invalidRational(str);
        }
        try {
            return new android.util.Rational(java.lang.Integer.parseInt(str.substring(0, indexOf)), java.lang.Integer.parseInt(str.substring(indexOf + 1)));
        } catch (java.lang.NumberFormatException e) {
            throw invalidRational(str);
        }
    }
}
