package org.apache.commons.math.util;

/* loaded from: classes3.dex */
public final class MathUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final double EPSILON = 1.1102230246251565E-16d;
    private static final long[] FACTORIALS = {1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800, 39916800, 479001600, 6227020800L, 87178291200L, 1307674368000L, 20922789888000L, 355687428096000L, 6402373705728000L, 121645100408832000L, 2432902008176640000L};
    private static final int NAN_GAP = 4194304;
    private static final byte NB = -1;
    private static final short NS = -1;
    private static final byte PB = 1;
    private static final short PS = 1;
    public static final double SAFE_MIN = Double.MIN_NORMAL;
    private static final long SGN_MASK = Long.MIN_VALUE;
    private static final int SGN_MASK_FLOAT = Integer.MIN_VALUE;
    public static final double TWO_PI = 6.283185307179586d;
    private static final byte ZB = 0;
    private static final short ZS = 0;

    public enum OrderDirection {
        INCREASING,
        DECREASING
    }

    private MathUtils() {
    }

    public static int addAndCheck(int i, int i2) {
        long j = i + i2;
        if (j < -2147483648L || j > 2147483647L) {
            throw org.apache.commons.math.MathRuntimeException.createArithmeticException(org.apache.commons.math.exception.util.LocalizedFormats.OVERFLOW_IN_ADDITION, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        }
        return (int) j;
    }

    public static long addAndCheck(long j, long j2) {
        return addAndCheck(j, j2, org.apache.commons.math.exception.util.LocalizedFormats.OVERFLOW_IN_ADDITION);
    }

    private static long addAndCheck(long j, long j2, org.apache.commons.math.exception.util.Localizable localizable) {
        if (j > j2) {
            return addAndCheck(j2, j, localizable);
        }
        if (j >= 0) {
            if (j <= com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME - j2) {
                return j + j2;
            }
            throw org.apache.commons.math.MathRuntimeException.createArithmeticException(localizable, java.lang.Long.valueOf(j), java.lang.Long.valueOf(j2));
        }
        if (j2 >= 0) {
            return j + j2;
        }
        if (SGN_MASK - j2 <= j) {
            return j + j2;
        }
        throw org.apache.commons.math.MathRuntimeException.createArithmeticException(localizable, java.lang.Long.valueOf(j), java.lang.Long.valueOf(j2));
    }

    public static long binomialCoefficient(int i, int i2) {
        checkBinomial(i, i2);
        long j = 1;
        if (i == i2 || i2 == 0) {
            return 1L;
        }
        if (i2 == 1 || i2 == i - 1) {
            return i;
        }
        if (i2 > i / 2) {
            return binomialCoefficient(i, i - i2);
        }
        if (i <= 61) {
            int i3 = (i - i2) + 1;
            for (int i4 = 1; i4 <= i2; i4++) {
                j = (j * i3) / i4;
                i3++;
            }
        } else if (i <= 66) {
            int i5 = (i - i2) + 1;
            for (int i6 = 1; i6 <= i2; i6++) {
                long gcd = gcd(i5, i6);
                j = (j / (i6 / gcd)) * (i5 / gcd);
                i5++;
            }
        } else {
            int i7 = (i - i2) + 1;
            for (int i8 = 1; i8 <= i2; i8++) {
                long gcd2 = gcd(i7, i8);
                j = mulAndCheck(j / (i8 / gcd2), i7 / gcd2);
                i7++;
            }
        }
        return j;
    }

    public static double binomialCoefficientDouble(int i, int i2) {
        checkBinomial(i, i2);
        double d = 1.0d;
        if (i == i2 || i2 == 0) {
            return 1.0d;
        }
        if (i2 == 1 || i2 == i - 1) {
            return i;
        }
        if (i2 > i / 2) {
            return binomialCoefficientDouble(i, i - i2);
        }
        if (i < 67) {
            return binomialCoefficient(i, i2);
        }
        for (int i3 = 1; i3 <= i2; i3++) {
            d *= ((i - i2) + i3) / i3;
        }
        return org.apache.commons.math.util.FastMath.floor(d + 0.5d);
    }

    public static double binomialCoefficientLog(int i, int i2) {
        checkBinomial(i, i2);
        double d = 0.0d;
        if (i == i2 || i2 == 0) {
            return 0.0d;
        }
        if (i2 == 1 || i2 == i - 1) {
            return org.apache.commons.math.util.FastMath.log(i);
        }
        if (i < 67) {
            return org.apache.commons.math.util.FastMath.log(binomialCoefficient(i, i2));
        }
        if (i < 1030) {
            return org.apache.commons.math.util.FastMath.log(binomialCoefficientDouble(i, i2));
        }
        if (i2 <= i / 2) {
            for (int i3 = (i - i2) + 1; i3 <= i; i3++) {
                d += org.apache.commons.math.util.FastMath.log(i3);
            }
            for (int i4 = 2; i4 <= i2; i4++) {
                d -= org.apache.commons.math.util.FastMath.log(i4);
            }
            return d;
        }
        return binomialCoefficientLog(i, i - i2);
    }

    private static void checkBinomial(int i, int i2) throws java.lang.IllegalArgumentException {
        if (i < i2) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.BINOMIAL_INVALID_PARAMETERS_ORDER, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        }
        if (i < 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.BINOMIAL_NEGATIVE_PARAMETER, java.lang.Integer.valueOf(i));
        }
    }

    public static int compareTo(double d, double d2, double d3) {
        if (equals(d, d2, d3)) {
            return 0;
        }
        if (d < d2) {
            return -1;
        }
        return 1;
    }

    public static double cosh(double d) {
        return (org.apache.commons.math.util.FastMath.exp(d) + org.apache.commons.math.util.FastMath.exp(-d)) / 2.0d;
    }

    @java.lang.Deprecated
    public static boolean equals(float f, float f2) {
        return (java.lang.Float.isNaN(f) && java.lang.Float.isNaN(f2)) || f == f2;
    }

    public static boolean equalsIncludingNaN(float f, float f2) {
        return (java.lang.Float.isNaN(f) && java.lang.Float.isNaN(f2)) || equals(f, f2, 1);
    }

    public static boolean equals(float f, float f2, float f3) {
        return equals(f, f2, 1) || org.apache.commons.math.util.FastMath.abs(f2 - f) <= f3;
    }

    public static boolean equalsIncludingNaN(float f, float f2, float f3) {
        return equalsIncludingNaN(f, f2) || org.apache.commons.math.util.FastMath.abs(f2 - f) <= f3;
    }

    public static boolean equals(float f, float f2, int i) {
        int floatToIntBits = java.lang.Float.floatToIntBits(f);
        int floatToIntBits2 = java.lang.Float.floatToIntBits(f2);
        if (floatToIntBits < 0) {
            floatToIntBits = Integer.MIN_VALUE - floatToIntBits;
        }
        if (floatToIntBits2 < 0) {
            floatToIntBits2 = Integer.MIN_VALUE - floatToIntBits2;
        }
        return (!(org.apache.commons.math.util.FastMath.abs(floatToIntBits - floatToIntBits2) <= i) || java.lang.Float.isNaN(f) || java.lang.Float.isNaN(f2)) ? false : true;
    }

    public static boolean equalsIncludingNaN(float f, float f2, int i) {
        return (java.lang.Float.isNaN(f) && java.lang.Float.isNaN(f2)) || equals(f, f2, i);
    }

    @java.lang.Deprecated
    public static boolean equals(float[] fArr, float[] fArr2) {
        if (fArr == null || fArr2 == null) {
            return !((fArr == null) ^ (fArr2 == null));
        }
        if (fArr.length != fArr2.length) {
            return false;
        }
        for (int i = 0; i < fArr.length; i++) {
            if (!equals(fArr[i], fArr2[i])) {
                return false;
            }
        }
        return true;
    }

    public static boolean equalsIncludingNaN(float[] fArr, float[] fArr2) {
        if (fArr == null || fArr2 == null) {
            return !((fArr == null) ^ (fArr2 == null));
        }
        if (fArr.length != fArr2.length) {
            return false;
        }
        for (int i = 0; i < fArr.length; i++) {
            if (!equalsIncludingNaN(fArr[i], fArr2[i])) {
                return false;
            }
        }
        return true;
    }

    public static boolean equals(double d, double d2) {
        return (java.lang.Double.isNaN(d) && java.lang.Double.isNaN(d2)) || d == d2;
    }

    public static boolean equalsIncludingNaN(double d, double d2) {
        return (java.lang.Double.isNaN(d) && java.lang.Double.isNaN(d2)) || equals(d, d2, 1);
    }

    public static boolean equals(double d, double d2, double d3) {
        return equals(d, d2) || org.apache.commons.math.util.FastMath.abs(d2 - d) <= d3;
    }

    public static boolean equalsIncludingNaN(double d, double d2, double d3) {
        return equalsIncludingNaN(d, d2) || org.apache.commons.math.util.FastMath.abs(d2 - d) <= d3;
    }

    public static boolean equals(double d, double d2, int i) {
        long doubleToLongBits = java.lang.Double.doubleToLongBits(d);
        long doubleToLongBits2 = java.lang.Double.doubleToLongBits(d2);
        if (doubleToLongBits < 0) {
            doubleToLongBits = SGN_MASK - doubleToLongBits;
        }
        if (doubleToLongBits2 < 0) {
            doubleToLongBits2 = SGN_MASK - doubleToLongBits2;
        }
        return org.apache.commons.math.util.FastMath.abs(doubleToLongBits - doubleToLongBits2) <= ((long) i);
    }

    public static boolean equalsIncludingNaN(double d, double d2, int i) {
        return (java.lang.Double.isNaN(d) && java.lang.Double.isNaN(d2)) || equals(d, d2, i);
    }

    public static boolean equals(double[] dArr, double[] dArr2) {
        if (dArr == null || dArr2 == null) {
            return !((dArr == null) ^ (dArr2 == null));
        }
        if (dArr.length != dArr2.length) {
            return false;
        }
        for (int i = 0; i < dArr.length; i++) {
            if (!equals(dArr[i], dArr2[i])) {
                return false;
            }
        }
        return true;
    }

    public static boolean equalsIncludingNaN(double[] dArr, double[] dArr2) {
        if (dArr == null || dArr2 == null) {
            return !((dArr == null) ^ (dArr2 == null));
        }
        if (dArr.length != dArr2.length) {
            return false;
        }
        for (int i = 0; i < dArr.length; i++) {
            if (!equalsIncludingNaN(dArr[i], dArr2[i])) {
                return false;
            }
        }
        return true;
    }

    public static long factorial(int i) {
        if (i < 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.FACTORIAL_NEGATIVE_PARAMETER, java.lang.Integer.valueOf(i));
        }
        if (i > 20) {
            throw new java.lang.ArithmeticException("factorial value is too large to fit in a long");
        }
        return FACTORIALS[i];
    }

    public static double factorialDouble(int i) {
        if (i < 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.FACTORIAL_NEGATIVE_PARAMETER, java.lang.Integer.valueOf(i));
        }
        if (i < 21) {
            return factorial(i);
        }
        return org.apache.commons.math.util.FastMath.floor(org.apache.commons.math.util.FastMath.exp(factorialLog(i)) + 0.5d);
    }

    public static double factorialLog(int i) {
        if (i < 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.FACTORIAL_NEGATIVE_PARAMETER, java.lang.Integer.valueOf(i));
        }
        if (i < 21) {
            return org.apache.commons.math.util.FastMath.log(factorial(i));
        }
        double d = 0.0d;
        for (int i2 = 2; i2 <= i; i2++) {
            d += org.apache.commons.math.util.FastMath.log(i2);
        }
        return d;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0028, code lost:
    
        if (r3 != 1) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x002a, code lost:
    
        r6 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0031, code lost:
    
        if ((r6 & 1) != 0) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0036, code lost:
    
        if (r6 <= 0) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0038, code lost:
    
        r0 = -r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x003c, code lost:
    
        r6 = (r1 - r0) / 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0040, code lost:
    
        if (r6 != 0) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0046, code lost:
    
        return (-r0) * (1 << r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x003b, code lost:
    
        r1 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0033, code lost:
    
        r6 = r6 / 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x002c, code lost:
    
        r6 = -(r0 / 2);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int gcd(int i, int i2) {
        int i3;
        int i4;
        if (i == 0 || i2 == 0) {
            if (i == Integer.MIN_VALUE || i2 == Integer.MIN_VALUE) {
                throw org.apache.commons.math.MathRuntimeException.createArithmeticException(org.apache.commons.math.exception.util.LocalizedFormats.GCD_OVERFLOW_32_BITS, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
            }
            return org.apache.commons.math.util.FastMath.abs(i) + org.apache.commons.math.util.FastMath.abs(i2);
        }
        if (i <= 0) {
            i3 = i;
        } else {
            i3 = -i;
        }
        if (i2 <= 0) {
            i4 = i2;
        } else {
            i4 = -i2;
        }
        int i5 = 0;
        while (true) {
            int i6 = i3 & 1;
            if (i6 != 0 || (i4 & 1) != 0 || i5 >= 31) {
                break;
            }
            i3 /= 2;
            i4 /= 2;
            i5++;
        }
        throw org.apache.commons.math.MathRuntimeException.createArithmeticException(org.apache.commons.math.exception.util.LocalizedFormats.GCD_OVERFLOW_32_BITS, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x003f, code lost:
    
        if (r13 != 1) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0041, code lost:
    
        r0 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x004c, code lost:
    
        if ((r0 & 1) != 0) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0053, code lost:
    
        if (r0 <= 0) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0055, code lost:
    
        r8 = -r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0059, code lost:
    
        r0 = (r6 - r8) / 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x005f, code lost:
    
        if (r0 != 0) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0065, code lost:
    
        return (-r8) * (1 << r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0058, code lost:
    
        r6 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x004e, code lost:
    
        r0 = r0 / 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0043, code lost:
    
        r0 = -(r8 / 2);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static long gcd(long j, long j2) {
        long j3;
        long j4;
        long j5 = 0;
        if (j == 0 || j2 == 0) {
            if (j == SGN_MASK || j2 == SGN_MASK) {
                throw org.apache.commons.math.MathRuntimeException.createArithmeticException(org.apache.commons.math.exception.util.LocalizedFormats.GCD_OVERFLOW_64_BITS, java.lang.Long.valueOf(j), java.lang.Long.valueOf(j2));
            }
            return org.apache.commons.math.util.FastMath.abs(j) + org.apache.commons.math.util.FastMath.abs(j2);
        }
        if (j <= 0) {
            j3 = j;
        } else {
            j3 = -j;
        }
        if (j2 <= 0) {
            j4 = j2;
        } else {
            j4 = -j2;
        }
        int i = 0;
        while (true) {
            long j6 = j3 & 1;
            if (j6 != j5 || (j4 & 1) != 0 || i >= 63) {
                break;
            }
            j3 /= 2;
            j4 /= 2;
            i++;
            j5 = 0;
        }
        throw org.apache.commons.math.MathRuntimeException.createArithmeticException(org.apache.commons.math.exception.util.LocalizedFormats.GCD_OVERFLOW_64_BITS, java.lang.Long.valueOf(j), java.lang.Long.valueOf(j2));
    }

    public static int hash(double d) {
        return new java.lang.Double(d).hashCode();
    }

    public static int hash(double[] dArr) {
        return java.util.Arrays.hashCode(dArr);
    }

    public static byte indicator(byte b) {
        return b >= 0 ? (byte) 1 : (byte) -1;
    }

    public static double indicator(double d) {
        if (java.lang.Double.isNaN(d)) {
            return Double.NaN;
        }
        return d >= 0.0d ? 1.0d : -1.0d;
    }

    public static float indicator(float f) {
        if (java.lang.Float.isNaN(f)) {
            return Float.NaN;
        }
        return f >= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE ? 1.0f : -1.0f;
    }

    public static int indicator(int i) {
        return i >= 0 ? 1 : -1;
    }

    public static long indicator(long j) {
        return j >= 0 ? 1L : -1L;
    }

    public static short indicator(short s) {
        return s >= 0 ? PS : NS;
    }

    public static int lcm(int i, int i2) {
        if (i == 0 || i2 == 0) {
            return 0;
        }
        int abs = org.apache.commons.math.util.FastMath.abs(mulAndCheck(i / gcd(i, i2), i2));
        if (abs == Integer.MIN_VALUE) {
            throw org.apache.commons.math.MathRuntimeException.createArithmeticException(org.apache.commons.math.exception.util.LocalizedFormats.LCM_OVERFLOW_32_BITS, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        }
        return abs;
    }

    public static long lcm(long j, long j2) {
        if (j == 0 || j2 == 0) {
            return 0L;
        }
        long abs = org.apache.commons.math.util.FastMath.abs(mulAndCheck(j / gcd(j, j2), j2));
        if (abs == SGN_MASK) {
            throw org.apache.commons.math.MathRuntimeException.createArithmeticException(org.apache.commons.math.exception.util.LocalizedFormats.LCM_OVERFLOW_64_BITS, java.lang.Long.valueOf(j), java.lang.Long.valueOf(j2));
        }
        return abs;
    }

    public static double log(double d, double d2) {
        return org.apache.commons.math.util.FastMath.log(d2) / org.apache.commons.math.util.FastMath.log(d);
    }

    public static int mulAndCheck(int i, int i2) {
        long j = i * i2;
        if (j < -2147483648L || j > 2147483647L) {
            throw new java.lang.ArithmeticException("overflow: mul");
        }
        return (int) j;
    }

    public static long mulAndCheck(long j, long j2) {
        if (j > j2) {
            return mulAndCheck(j2, j);
        }
        if (j >= 0) {
            if (j <= 0) {
                return 0L;
            }
            if (j <= com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME / j2) {
                return j * j2;
            }
            throw new java.lang.ArithmeticException("overflow: multiply");
        }
        if (j2 < 0) {
            if (j >= com.android.server.job.controllers.JobStatus.NO_LATEST_RUNTIME / j2) {
                return j * j2;
            }
            throw new java.lang.ArithmeticException("overflow: multiply");
        }
        if (j2 <= 0) {
            return 0L;
        }
        if (SGN_MASK / j2 <= j) {
            return j * j2;
        }
        throw new java.lang.ArithmeticException("overflow: multiply");
    }

    @java.lang.Deprecated
    public static double nextAfter(double d, double d2) {
        if (java.lang.Double.isNaN(d) || java.lang.Double.isInfinite(d)) {
            return d;
        }
        if (d == 0.0d) {
            return d2 < 0.0d ? -4.9E-324d : Double.MIN_VALUE;
        }
        long doubleToLongBits = java.lang.Double.doubleToLongBits(d);
        long j = SGN_MASK & doubleToLongBits;
        long j2 = 9218868437227405312L & doubleToLongBits;
        long j3 = doubleToLongBits & 4503599627370495L;
        if (d * (d2 - d) >= 0.0d) {
            if (j3 == 4503599627370495L) {
                return java.lang.Double.longBitsToDouble(j | (j2 + 4503599627370496L));
            }
            return java.lang.Double.longBitsToDouble(j | j2 | (j3 + 1));
        }
        if (j3 == 0) {
            return java.lang.Double.longBitsToDouble(j | (j2 - 4503599627370496L) | 4503599627370495L);
        }
        return java.lang.Double.longBitsToDouble(j | j2 | (j3 - 1));
    }

    @java.lang.Deprecated
    public static double scalb(double d, int i) {
        return org.apache.commons.math.util.FastMath.scalb(d, i);
    }

    public static double normalizeAngle(double d, double d2) {
        return d - (org.apache.commons.math.util.FastMath.floor(((3.141592653589793d + d) - d2) / 6.283185307179586d) * 6.283185307179586d);
    }

    public static double[] normalizeArray(double[] dArr, double d) throws java.lang.ArithmeticException, java.lang.IllegalArgumentException {
        if (java.lang.Double.isInfinite(d)) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NORMALIZE_INFINITE, new java.lang.Object[0]);
        }
        if (java.lang.Double.isNaN(d)) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.NORMALIZE_NAN, new java.lang.Object[0]);
        }
        int length = dArr.length;
        double[] dArr2 = new double[length];
        double d2 = 0.0d;
        for (int i = 0; i < length; i++) {
            if (java.lang.Double.isInfinite(dArr[i])) {
                throw org.apache.commons.math.MathRuntimeException.createArithmeticException(org.apache.commons.math.exception.util.LocalizedFormats.INFINITE_ARRAY_ELEMENT, java.lang.Double.valueOf(dArr[i]), java.lang.Integer.valueOf(i));
            }
            if (!java.lang.Double.isNaN(dArr[i])) {
                d2 += dArr[i];
            }
        }
        if (d2 == 0.0d) {
            throw org.apache.commons.math.MathRuntimeException.createArithmeticException(org.apache.commons.math.exception.util.LocalizedFormats.ARRAY_SUMS_TO_ZERO, new java.lang.Object[0]);
        }
        for (int i2 = 0; i2 < length; i2++) {
            if (java.lang.Double.isNaN(dArr[i2])) {
                dArr2[i2] = Double.NaN;
            } else {
                dArr2[i2] = (dArr[i2] * d) / d2;
            }
        }
        return dArr2;
    }

    public static double round(double d, int i) {
        return round(d, i, 4);
    }

    public static double round(double d, int i, int i2) {
        try {
            return new java.math.BigDecimal(java.lang.Double.toString(d)).setScale(i, i2).doubleValue();
        } catch (java.lang.NumberFormatException e) {
            if (java.lang.Double.isInfinite(d)) {
                return d;
            }
            return Double.NaN;
        }
    }

    public static float round(float f, int i) {
        return round(f, i, 4);
    }

    public static float round(float f, int i, int i2) {
        float indicator = indicator(f);
        return ((float) roundUnscaled(f * r6, indicator, i2)) / (((float) org.apache.commons.math.util.FastMath.pow(10.0d, i)) * indicator);
    }

    private static double roundUnscaled(double d, double d2, int i) {
        switch (i) {
            case 0:
                return org.apache.commons.math.util.FastMath.ceil(nextAfter(d, Double.POSITIVE_INFINITY));
            case 1:
                return org.apache.commons.math.util.FastMath.floor(nextAfter(d, Double.NEGATIVE_INFINITY));
            case 2:
                if (d2 == -1.0d) {
                    return org.apache.commons.math.util.FastMath.floor(nextAfter(d, Double.NEGATIVE_INFINITY));
                }
                return org.apache.commons.math.util.FastMath.ceil(nextAfter(d, Double.POSITIVE_INFINITY));
            case 3:
                if (d2 == -1.0d) {
                    return org.apache.commons.math.util.FastMath.ceil(nextAfter(d, Double.POSITIVE_INFINITY));
                }
                return org.apache.commons.math.util.FastMath.floor(nextAfter(d, Double.NEGATIVE_INFINITY));
            case 4:
                double nextAfter = nextAfter(d, Double.POSITIVE_INFINITY);
                if (nextAfter - org.apache.commons.math.util.FastMath.floor(nextAfter) >= 0.5d) {
                    return org.apache.commons.math.util.FastMath.ceil(nextAfter);
                }
                return org.apache.commons.math.util.FastMath.floor(nextAfter);
            case 5:
                double nextAfter2 = nextAfter(d, Double.NEGATIVE_INFINITY);
                if (nextAfter2 - org.apache.commons.math.util.FastMath.floor(nextAfter2) > 0.5d) {
                    return org.apache.commons.math.util.FastMath.ceil(nextAfter2);
                }
                return org.apache.commons.math.util.FastMath.floor(nextAfter2);
            case 6:
                double floor = d - org.apache.commons.math.util.FastMath.floor(d);
                if (floor > 0.5d) {
                    return org.apache.commons.math.util.FastMath.ceil(d);
                }
                if (floor < 0.5d) {
                    return org.apache.commons.math.util.FastMath.floor(d);
                }
                if (org.apache.commons.math.util.FastMath.floor(d) / 2.0d == org.apache.commons.math.util.FastMath.floor(java.lang.Math.floor(d) / 2.0d)) {
                    return org.apache.commons.math.util.FastMath.floor(d);
                }
                return org.apache.commons.math.util.FastMath.ceil(d);
            case 7:
                if (d == org.apache.commons.math.util.FastMath.floor(d)) {
                    return d;
                }
                throw new java.lang.ArithmeticException("Inexact result from rounding");
            default:
                throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.INVALID_ROUNDING_METHOD, java.lang.Integer.valueOf(i), "ROUND_CEILING", 2, "ROUND_DOWN", 1, "ROUND_FLOOR", 3, "ROUND_HALF_DOWN", 5, "ROUND_HALF_EVEN", 6, "ROUND_HALF_UP", 4, "ROUND_UNNECESSARY", 7, "ROUND_UP", 0);
        }
    }

    public static byte sign(byte b) {
        if (b == 0) {
            return (byte) 0;
        }
        return b > 0 ? (byte) 1 : (byte) -1;
    }

    public static double sign(double d) {
        if (java.lang.Double.isNaN(d)) {
            return Double.NaN;
        }
        if (d == 0.0d) {
            return 0.0d;
        }
        return d > 0.0d ? 1.0d : -1.0d;
    }

    public static float sign(float f) {
        if (java.lang.Float.isNaN(f)) {
            return Float.NaN;
        }
        if (f == com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            return com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        }
        return f > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE ? 1.0f : -1.0f;
    }

    public static int sign(int i) {
        if (i == 0) {
            return 0;
        }
        return i > 0 ? 1 : -1;
    }

    public static long sign(long j) {
        if (j == 0) {
            return 0L;
        }
        return j > 0 ? 1L : -1L;
    }

    public static short sign(short s) {
        if (s == 0) {
            return (short) 0;
        }
        return s > 0 ? PS : NS;
    }

    public static double sinh(double d) {
        return (org.apache.commons.math.util.FastMath.exp(d) - org.apache.commons.math.util.FastMath.exp(-d)) / 2.0d;
    }

    public static int subAndCheck(int i, int i2) {
        long j = i - i2;
        if (j < -2147483648L || j > 2147483647L) {
            throw org.apache.commons.math.MathRuntimeException.createArithmeticException(org.apache.commons.math.exception.util.LocalizedFormats.OVERFLOW_IN_SUBTRACTION, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        }
        return (int) j;
    }

    public static long subAndCheck(long j, long j2) {
        if (j2 != SGN_MASK) {
            return addAndCheck(j, -j2, org.apache.commons.math.exception.util.LocalizedFormats.OVERFLOW_IN_ADDITION);
        }
        if (j < 0) {
            return j - j2;
        }
        throw new java.lang.ArithmeticException("overflow: subtract");
    }

    public static int pow(int i, int i2) throws java.lang.IllegalArgumentException {
        if (i2 < 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.POWER_NEGATIVE_PARAMETERS, java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
        }
        int i3 = 1;
        while (i2 != 0) {
            if ((i2 & 1) != 0) {
                i3 *= i;
            }
            i *= i;
            i2 >>= 1;
        }
        return i3;
    }

    public static int pow(int i, long j) throws java.lang.IllegalArgumentException {
        if (j < 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.POWER_NEGATIVE_PARAMETERS, java.lang.Integer.valueOf(i), java.lang.Long.valueOf(j));
        }
        int i2 = 1;
        while (j != 0) {
            if ((1 & j) != 0) {
                i2 *= i;
            }
            i *= i;
            j >>= 1;
        }
        return i2;
    }

    public static long pow(long j, int i) throws java.lang.IllegalArgumentException {
        if (i < 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.POWER_NEGATIVE_PARAMETERS, java.lang.Long.valueOf(j), java.lang.Integer.valueOf(i));
        }
        long j2 = 1;
        while (i != 0) {
            if ((i & 1) != 0) {
                j2 *= j;
            }
            j *= j;
            i >>= 1;
        }
        return j2;
    }

    public static long pow(long j, long j2) throws java.lang.IllegalArgumentException {
        if (j2 < 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.POWER_NEGATIVE_PARAMETERS, java.lang.Long.valueOf(j), java.lang.Long.valueOf(j2));
        }
        long j3 = 1;
        while (j2 != 0) {
            if ((j2 & 1) != 0) {
                j3 *= j;
            }
            j *= j;
            j2 >>= 1;
        }
        return j3;
    }

    public static java.math.BigInteger pow(java.math.BigInteger bigInteger, int i) throws java.lang.IllegalArgumentException {
        if (i < 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.POWER_NEGATIVE_PARAMETERS, bigInteger, java.lang.Integer.valueOf(i));
        }
        return bigInteger.pow(i);
    }

    public static java.math.BigInteger pow(java.math.BigInteger bigInteger, long j) throws java.lang.IllegalArgumentException {
        if (j < 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.POWER_NEGATIVE_PARAMETERS, bigInteger, java.lang.Long.valueOf(j));
        }
        java.math.BigInteger bigInteger2 = java.math.BigInteger.ONE;
        while (j != 0) {
            if ((1 & j) != 0) {
                bigInteger2 = bigInteger2.multiply(bigInteger);
            }
            bigInteger = bigInteger.multiply(bigInteger);
            j >>= 1;
        }
        return bigInteger2;
    }

    public static java.math.BigInteger pow(java.math.BigInteger bigInteger, java.math.BigInteger bigInteger2) throws java.lang.IllegalArgumentException {
        if (bigInteger2.compareTo(java.math.BigInteger.ZERO) < 0) {
            throw org.apache.commons.math.MathRuntimeException.createIllegalArgumentException(org.apache.commons.math.exception.util.LocalizedFormats.POWER_NEGATIVE_PARAMETERS, bigInteger, bigInteger2);
        }
        java.math.BigInteger bigInteger3 = java.math.BigInteger.ONE;
        while (!java.math.BigInteger.ZERO.equals(bigInteger2)) {
            if (bigInteger2.testBit(0)) {
                bigInteger3 = bigInteger3.multiply(bigInteger);
            }
            bigInteger = bigInteger.multiply(bigInteger);
            bigInteger2 = bigInteger2.shiftRight(1);
        }
        return bigInteger3;
    }

    public static double distance1(double[] dArr, double[] dArr2) {
        double d = 0.0d;
        for (int i = 0; i < dArr.length; i++) {
            d += org.apache.commons.math.util.FastMath.abs(dArr[i] - dArr2[i]);
        }
        return d;
    }

    public static int distance1(int[] iArr, int[] iArr2) {
        int i = 0;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            i += org.apache.commons.math.util.FastMath.abs(iArr[i2] - iArr2[i2]);
        }
        return i;
    }

    public static double distance(double[] dArr, double[] dArr2) {
        double d = 0.0d;
        for (int i = 0; i < dArr.length; i++) {
            double d2 = dArr[i] - dArr2[i];
            d += d2 * d2;
        }
        return org.apache.commons.math.util.FastMath.sqrt(d);
    }

    public static double distance(int[] iArr, int[] iArr2) {
        double d = 0.0d;
        for (int i = 0; i < iArr.length; i++) {
            double d2 = iArr[i] - iArr2[i];
            d += d2 * d2;
        }
        return org.apache.commons.math.util.FastMath.sqrt(d);
    }

    public static double distanceInf(double[] dArr, double[] dArr2) {
        double d = 0.0d;
        for (int i = 0; i < dArr.length; i++) {
            d = org.apache.commons.math.util.FastMath.max(d, org.apache.commons.math.util.FastMath.abs(dArr[i] - dArr2[i]));
        }
        return d;
    }

    public static int distanceInf(int[] iArr, int[] iArr2) {
        int i = 0;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            i = org.apache.commons.math.util.FastMath.max(i, org.apache.commons.math.util.FastMath.abs(iArr[i2] - iArr2[i2]));
        }
        return i;
    }

    public static void checkOrder(double[] dArr, org.apache.commons.math.util.MathUtils.OrderDirection orderDirection, boolean z) {
        double d = dArr[0];
        int length = dArr.length;
        boolean z2 = true;
        for (int i = 1; i < length; i++) {
            switch (org.apache.commons.math.util.MathUtils.AnonymousClass1.$SwitchMap$org$apache$commons$math$util$MathUtils$OrderDirection[orderDirection.ordinal()]) {
                case 1:
                    if (z) {
                        if (dArr[i] <= d) {
                            z2 = false;
                            break;
                        }
                    } else if (dArr[i] < d) {
                        z2 = false;
                        break;
                    }
                    break;
                case 2:
                    if (z) {
                        if (dArr[i] >= d) {
                            z2 = false;
                            break;
                        }
                    } else if (dArr[i] > d) {
                        z2 = false;
                        break;
                    }
                    break;
                default:
                    throw new java.lang.IllegalArgumentException();
            }
            if (!z2) {
                throw new org.apache.commons.math.exception.NonMonotonousSequenceException(java.lang.Double.valueOf(dArr[i]), java.lang.Double.valueOf(d), i, orderDirection, z);
            }
            d = dArr[i];
        }
    }

    /* renamed from: org.apache.commons.math.util.MathUtils$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$apache$commons$math$util$MathUtils$OrderDirection = new int[org.apache.commons.math.util.MathUtils.OrderDirection.values().length];

        static {
            try {
                $SwitchMap$org$apache$commons$math$util$MathUtils$OrderDirection[org.apache.commons.math.util.MathUtils.OrderDirection.INCREASING.ordinal()] = 1;
            } catch (java.lang.NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$apache$commons$math$util$MathUtils$OrderDirection[org.apache.commons.math.util.MathUtils.OrderDirection.DECREASING.ordinal()] = 2;
            } catch (java.lang.NoSuchFieldError e2) {
            }
        }
    }

    public static void checkOrder(double[] dArr) {
        checkOrder(dArr, org.apache.commons.math.util.MathUtils.OrderDirection.INCREASING, true);
    }

    @java.lang.Deprecated
    public static void checkOrder(double[] dArr, int i, boolean z) {
        if (i > 0) {
            checkOrder(dArr, org.apache.commons.math.util.MathUtils.OrderDirection.INCREASING, z);
        } else {
            checkOrder(dArr, org.apache.commons.math.util.MathUtils.OrderDirection.DECREASING, z);
        }
    }

    public static double safeNorm(double[] dArr) {
        double length = 1.304E19d / dArr.length;
        double d = 0.0d;
        double d2 = 0.0d;
        double d3 = 0.0d;
        double d4 = 0.0d;
        double d5 = 0.0d;
        for (double d6 : dArr) {
            double abs = java.lang.Math.abs(d6);
            if (abs >= 3.834E-20d && abs <= length) {
                d2 += abs * abs;
            } else if (abs > 3.834E-20d) {
                if (abs > d3) {
                    double d7 = d3 / abs;
                    d = (d * d7 * d7) + 1.0d;
                    d3 = abs;
                } else {
                    double d8 = abs / d3;
                    d += d8 * d8;
                }
            } else if (abs > d4) {
                double d9 = d4 / abs;
                d5 = (d5 * d9 * d9) + 1.0d;
                d4 = abs;
            } else if (abs != 0.0d) {
                double d10 = abs / d4;
                d5 += d10 * d10;
            }
        }
        if (d != 0.0d) {
            return d3 * java.lang.Math.sqrt(d + ((d2 / d3) / d3));
        }
        if (d2 == 0.0d) {
            return d4 * java.lang.Math.sqrt(d5);
        }
        if (d2 >= d4) {
            return java.lang.Math.sqrt(d2 * (((d4 / d2) * d4 * d5) + 1.0d));
        }
        return java.lang.Math.sqrt(d4 * ((d2 / d4) + (d5 * d4)));
    }
}
