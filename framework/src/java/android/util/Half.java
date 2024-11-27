package android.util;

/* loaded from: classes3.dex */
public final class Half extends java.lang.Number implements java.lang.Comparable<android.util.Half> {
    public static final short EPSILON = 5120;
    public static final short LOWEST_VALUE = -1025;
    public static final int MAX_EXPONENT = 15;
    public static final short MAX_VALUE = 31743;
    public static final int MIN_EXPONENT = -14;
    public static final short MIN_NORMAL = 1024;
    public static final short MIN_VALUE = 1;
    public static final short NEGATIVE_INFINITY = -1024;
    public static final short NEGATIVE_ZERO = Short.MIN_VALUE;
    public static final short NaN = 32256;
    public static final short POSITIVE_INFINITY = 31744;
    public static final short POSITIVE_ZERO = 0;
    public static final int SIZE = 16;
    private final short mValue;

    public Half(short s) {
        this.mValue = s;
    }

    public Half(float f) {
        this.mValue = toHalf(f);
    }

    public Half(double d) {
        this.mValue = toHalf((float) d);
    }

    public Half(java.lang.String str) throws java.lang.NumberFormatException {
        this.mValue = toHalf(java.lang.Float.parseFloat(str));
    }

    public short halfValue() {
        return this.mValue;
    }

    @Override // java.lang.Number
    public byte byteValue() {
        return (byte) toFloat(this.mValue);
    }

    @Override // java.lang.Number
    public short shortValue() {
        return (short) toFloat(this.mValue);
    }

    @Override // java.lang.Number
    public int intValue() {
        return (int) toFloat(this.mValue);
    }

    @Override // java.lang.Number
    public long longValue() {
        return (long) toFloat(this.mValue);
    }

    @Override // java.lang.Number
    public float floatValue() {
        return toFloat(this.mValue);
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return toFloat(this.mValue);
    }

    public boolean isNaN() {
        return isNaN(this.mValue);
    }

    public boolean equals(java.lang.Object obj) {
        return (obj instanceof android.util.Half) && halfToIntBits(((android.util.Half) obj).mValue) == halfToIntBits(this.mValue);
    }

    public int hashCode() {
        return hashCode(this.mValue);
    }

    public java.lang.String toString() {
        return toString(this.mValue);
    }

    @Override // java.lang.Comparable
    public int compareTo(android.util.Half half) {
        return compare(this.mValue, half.mValue);
    }

    public static int hashCode(short s) {
        return halfToIntBits(s);
    }

    public static int compare(short s, short s2) {
        return libcore.util.FP16.compare(s, s2);
    }

    public static short halfToShortBits(short s) {
        return (s & Short.MAX_VALUE) > 31744 ? NaN : s;
    }

    public static int halfToIntBits(short s) {
        if ((s & Short.MAX_VALUE) > 31744) {
            return 32256;
        }
        return s & 65535;
    }

    public static int halfToRawIntBits(short s) {
        return s & 65535;
    }

    public static short intBitsToHalf(int i) {
        return (short) (i & 65535);
    }

    public static short copySign(short s, short s2) {
        return (short) ((s & Short.MAX_VALUE) | (s2 & 32768));
    }

    public static short abs(short s) {
        return (short) (s & Short.MAX_VALUE);
    }

    public static short round(short s) {
        return libcore.util.FP16.rint(s);
    }

    public static short ceil(short s) {
        return libcore.util.FP16.ceil(s);
    }

    public static short floor(short s) {
        return libcore.util.FP16.floor(s);
    }

    public static short trunc(short s) {
        return libcore.util.FP16.trunc(s);
    }

    public static short min(short s, short s2) {
        return libcore.util.FP16.min(s, s2);
    }

    public static short max(short s, short s2) {
        return libcore.util.FP16.max(s, s2);
    }

    public static boolean less(short s, short s2) {
        return libcore.util.FP16.less(s, s2);
    }

    public static boolean lessEquals(short s, short s2) {
        return libcore.util.FP16.lessEquals(s, s2);
    }

    public static boolean greater(short s, short s2) {
        return libcore.util.FP16.greater(s, s2);
    }

    public static boolean greaterEquals(short s, short s2) {
        return libcore.util.FP16.greaterEquals(s, s2);
    }

    public static boolean equals(short s, short s2) {
        return libcore.util.FP16.equals(s, s2);
    }

    public static int getSign(short s) {
        return (s & 32768) == 0 ? 1 : -1;
    }

    public static int getExponent(short s) {
        return ((s >>> 10) & 31) - 15;
    }

    public static int getSignificand(short s) {
        return s & 1023;
    }

    public static boolean isInfinite(short s) {
        return libcore.util.FP16.isInfinite(s);
    }

    public static boolean isNaN(short s) {
        return libcore.util.FP16.isNaN(s);
    }

    public static boolean isNormalized(short s) {
        return libcore.util.FP16.isNormalized(s);
    }

    public static float toFloat(short s) {
        return libcore.util.FP16.toFloat(s);
    }

    public static short toHalf(float f) {
        return libcore.util.FP16.toHalf(f);
    }

    public static android.util.Half valueOf(short s) {
        return new android.util.Half(s);
    }

    public static android.util.Half valueOf(float f) {
        return new android.util.Half(f);
    }

    public static android.util.Half valueOf(java.lang.String str) {
        return new android.util.Half(str);
    }

    public static short parseHalf(java.lang.String str) throws java.lang.NumberFormatException {
        return toHalf(java.lang.Float.parseFloat(str));
    }

    public static java.lang.String toString(short s) {
        return java.lang.Float.toString(toFloat(s));
    }

    public static java.lang.String toHexString(short s) {
        return libcore.util.FP16.toHexString(s);
    }
}
