package com.android.internal.util.jobs;

/* loaded from: classes.dex */
public class Preconditions {
    public static void checkArgument(boolean z) {
        if (!z) {
            throw new java.lang.IllegalArgumentException();
        }
    }

    public static void checkArgument(boolean z, java.lang.Object obj) {
        if (!z) {
            throw new java.lang.IllegalArgumentException(java.lang.String.valueOf(obj));
        }
    }

    public static void checkArgument(boolean z, @android.annotation.NonNull java.lang.String str, java.lang.Object... objArr) {
        if (!z) {
            throw new java.lang.IllegalArgumentException(java.lang.String.format(str, objArr));
        }
    }

    @android.annotation.NonNull
    public static <T extends java.lang.CharSequence> T checkStringNotEmpty(T t) {
        if (android.text.TextUtils.isEmpty(t)) {
            throw new java.lang.IllegalArgumentException();
        }
        return t;
    }

    @android.annotation.NonNull
    public static <T extends java.lang.CharSequence> T checkStringNotEmpty(T t, java.lang.Object obj) {
        if (android.text.TextUtils.isEmpty(t)) {
            throw new java.lang.IllegalArgumentException(java.lang.String.valueOf(obj));
        }
        return t;
    }

    @android.annotation.NonNull
    public static <T extends java.lang.CharSequence> T checkStringNotEmpty(T t, @android.annotation.NonNull java.lang.String str, java.lang.Object... objArr) {
        if (android.text.TextUtils.isEmpty(t)) {
            throw new java.lang.IllegalArgumentException(java.lang.String.format(str, objArr));
        }
        return t;
    }

    @android.annotation.NonNull
    @java.lang.Deprecated
    public static <T> T checkNotNull(T t) {
        if (t == null) {
            throw new java.lang.NullPointerException();
        }
        return t;
    }

    @android.annotation.NonNull
    @java.lang.Deprecated
    public static <T> T checkNotNull(T t, java.lang.Object obj) {
        if (t == null) {
            throw new java.lang.NullPointerException(java.lang.String.valueOf(obj));
        }
        return t;
    }

    @android.annotation.NonNull
    public static <T> T checkNotNull(T t, @android.annotation.NonNull java.lang.String str, java.lang.Object... objArr) {
        if (t == null) {
            throw new java.lang.NullPointerException(java.lang.String.format(str, objArr));
        }
        return t;
    }

    public static void checkState(boolean z) {
        checkState(z, null);
    }

    public static void checkState(boolean z, java.lang.String str) {
        if (!z) {
            throw new java.lang.IllegalStateException(str);
        }
    }

    public static void checkState(boolean z, @android.annotation.NonNull java.lang.String str, java.lang.Object... objArr) {
        if (!z) {
            throw new java.lang.IllegalStateException(java.lang.String.format(str, objArr));
        }
    }

    public static void checkCallAuthorization(boolean z) {
        if (!z) {
            throw new java.lang.SecurityException("Calling identity is not authorized");
        }
    }

    public static void checkCallAuthorization(boolean z, java.lang.String str) {
        if (!z) {
            throw new java.lang.SecurityException(str);
        }
    }

    public static void checkCallAuthorization(boolean z, @android.annotation.NonNull java.lang.String str, java.lang.Object... objArr) {
        if (!z) {
            throw new java.lang.SecurityException(java.lang.String.format(str, objArr));
        }
    }

    public static void checkCallingUser(boolean z) {
        if (!z) {
            throw new java.lang.SecurityException("Calling user is not authorized");
        }
    }

    public static int checkFlagsArgument(int i, int i2) {
        if ((i & i2) != i) {
            throw new java.lang.IllegalArgumentException("Requested flags 0x" + java.lang.Integer.toHexString(i) + ", but only 0x" + java.lang.Integer.toHexString(i2) + " are allowed");
        }
        return i;
    }

    public static int checkArgumentNonnegative(int i, java.lang.String str) {
        if (i < 0) {
            throw new java.lang.IllegalArgumentException(str);
        }
        return i;
    }

    public static int checkArgumentNonnegative(int i) {
        if (i < 0) {
            throw new java.lang.IllegalArgumentException();
        }
        return i;
    }

    public static long checkArgumentNonnegative(long j) {
        if (j < 0) {
            throw new java.lang.IllegalArgumentException();
        }
        return j;
    }

    public static long checkArgumentNonnegative(long j, java.lang.String str) {
        if (j < 0) {
            throw new java.lang.IllegalArgumentException(str);
        }
        return j;
    }

    public static int checkArgumentPositive(int i, java.lang.String str) {
        if (i <= 0) {
            throw new java.lang.IllegalArgumentException(str);
        }
        return i;
    }

    public static float checkArgumentNonNegative(float f, java.lang.String str) {
        if (f < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            throw new java.lang.IllegalArgumentException(str);
        }
        return f;
    }

    public static float checkArgumentPositive(float f, java.lang.String str) {
        if (f <= com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            throw new java.lang.IllegalArgumentException(str);
        }
        return f;
    }

    public static float checkArgumentFinite(float f, java.lang.String str) {
        if (java.lang.Float.isNaN(f)) {
            throw new java.lang.IllegalArgumentException(str + " must not be NaN");
        }
        if (java.lang.Float.isInfinite(f)) {
            throw new java.lang.IllegalArgumentException(str + " must not be infinite");
        }
        return f;
    }

    public static float checkArgumentInRange(float f, float f2, float f3, java.lang.String str) {
        if (java.lang.Float.isNaN(f)) {
            throw new java.lang.IllegalArgumentException(str + " must not be NaN");
        }
        if (f < f2) {
            throw new java.lang.IllegalArgumentException(java.lang.String.format("%s is out of range of [%f, %f] (too low)", str, java.lang.Float.valueOf(f2), java.lang.Float.valueOf(f3)));
        }
        if (f > f3) {
            throw new java.lang.IllegalArgumentException(java.lang.String.format("%s is out of range of [%f, %f] (too high)", str, java.lang.Float.valueOf(f2), java.lang.Float.valueOf(f3)));
        }
        return f;
    }

    public static double checkArgumentInRange(double d, double d2, double d3, java.lang.String str) {
        if (java.lang.Double.isNaN(d)) {
            throw new java.lang.IllegalArgumentException(str + " must not be NaN");
        }
        if (d < d2) {
            throw new java.lang.IllegalArgumentException(java.lang.String.format("%s is out of range of [%f, %f] (too low)", str, java.lang.Double.valueOf(d2), java.lang.Double.valueOf(d3)));
        }
        if (d > d3) {
            throw new java.lang.IllegalArgumentException(java.lang.String.format("%s is out of range of [%f, %f] (too high)", str, java.lang.Double.valueOf(d2), java.lang.Double.valueOf(d3)));
        }
        return d;
    }

    public static int checkArgumentInRange(int i, int i2, int i3, java.lang.String str) {
        if (i < i2) {
            throw new java.lang.IllegalArgumentException(java.lang.String.format("%s is out of range of [%d, %d] (too low)", str, java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3)));
        }
        if (i > i3) {
            throw new java.lang.IllegalArgumentException(java.lang.String.format("%s is out of range of [%d, %d] (too high)", str, java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(i3)));
        }
        return i;
    }

    public static long checkArgumentInRange(long j, long j2, long j3, java.lang.String str) {
        if (j < j2) {
            throw new java.lang.IllegalArgumentException(java.lang.String.format("%s is out of range of [%d, %d] (too low)", str, java.lang.Long.valueOf(j2), java.lang.Long.valueOf(j3)));
        }
        if (j > j3) {
            throw new java.lang.IllegalArgumentException(java.lang.String.format("%s is out of range of [%d, %d] (too high)", str, java.lang.Long.valueOf(j2), java.lang.Long.valueOf(j3)));
        }
        return j;
    }

    public static <T> T[] checkArrayElementsNotNull(T[] tArr, java.lang.String str) {
        if (tArr == null) {
            throw new java.lang.NullPointerException(str + " must not be null");
        }
        for (int i = 0; i < tArr.length; i++) {
            if (tArr[i] == null) {
                throw new java.lang.NullPointerException(java.lang.String.format("%s[%d] must not be null", str, java.lang.Integer.valueOf(i)));
            }
        }
        return tArr;
    }

    @android.annotation.NonNull
    public static <C extends java.util.Collection<T>, T> C checkCollectionElementsNotNull(C c, java.lang.String str) {
        if (c == null) {
            throw new java.lang.NullPointerException(str + " must not be null");
        }
        java.util.Iterator it = c.iterator();
        long j = 0;
        while (it.hasNext()) {
            if (it.next() == null) {
                throw new java.lang.NullPointerException(java.lang.String.format("%s[%d] must not be null", str, java.lang.Long.valueOf(j)));
            }
            j++;
        }
        return c;
    }

    public static <T> java.util.Collection<T> checkCollectionNotEmpty(java.util.Collection<T> collection, java.lang.String str) {
        if (collection == null) {
            throw new java.lang.NullPointerException(str + " must not be null");
        }
        if (collection.isEmpty()) {
            throw new java.lang.IllegalArgumentException(str + " is empty");
        }
        return collection;
    }

    @android.annotation.NonNull
    public static byte[] checkByteArrayNotEmpty(byte[] bArr, java.lang.String str) {
        if (bArr == null) {
            throw new java.lang.NullPointerException(str + " must not be null");
        }
        if (bArr.length == 0) {
            throw new java.lang.IllegalArgumentException(str + " is empty");
        }
        return bArr;
    }

    @android.annotation.NonNull
    public static java.lang.String checkArgumentIsSupported(java.lang.String[] strArr, java.lang.String str) {
        checkNotNull(str);
        checkNotNull(strArr);
        if (!contains(strArr, str)) {
            throw new java.lang.IllegalArgumentException(str + "is not supported " + java.util.Arrays.toString(strArr));
        }
        return str;
    }

    private static boolean contains(java.lang.String[] strArr, java.lang.String str) {
        if (strArr == null) {
            return false;
        }
        for (java.lang.String str2 : strArr) {
            if (java.util.Objects.equals(str, str2)) {
                return true;
            }
        }
        return false;
    }

    public static float[] checkArrayElementsInRange(float[] fArr, float f, float f2, java.lang.String str) {
        checkNotNull(fArr, "%s must not be null", str);
        for (int i = 0; i < fArr.length; i++) {
            float f3 = fArr[i];
            if (java.lang.Float.isNaN(f3)) {
                throw new java.lang.IllegalArgumentException(str + "[" + i + "] must not be NaN");
            }
            if (f3 < f) {
                throw new java.lang.IllegalArgumentException(java.lang.String.format("%s[%d] is out of range of [%f, %f] (too low)", str, java.lang.Integer.valueOf(i), java.lang.Float.valueOf(f), java.lang.Float.valueOf(f2)));
            }
            if (f3 > f2) {
                throw new java.lang.IllegalArgumentException(java.lang.String.format("%s[%d] is out of range of [%f, %f] (too high)", str, java.lang.Integer.valueOf(i), java.lang.Float.valueOf(f), java.lang.Float.valueOf(f2)));
            }
        }
        return fArr;
    }

    public static int[] checkArrayElementsInRange(int[] iArr, int i, int i2, java.lang.String str) {
        checkNotNull(iArr, "%s must not be null", str);
        for (int i3 = 0; i3 < iArr.length; i3++) {
            int i4 = iArr[i3];
            if (i4 < i) {
                throw new java.lang.IllegalArgumentException(java.lang.String.format("%s[%d] is out of range of [%d, %d] (too low)", str, java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)));
            }
            if (i4 > i2) {
                throw new java.lang.IllegalArgumentException(java.lang.String.format("%s[%d] is out of range of [%d, %d] (too high)", str, java.lang.Integer.valueOf(i3), java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2)));
            }
        }
        return iArr;
    }

    @android.annotation.NonNull
    public static <T> T requireNonNullViaRavenwoodRule(@android.annotation.Nullable T t) {
        if (t == null) {
            throw new java.lang.IllegalStateException("This operation requires that a RavenwoodRule be configured to accurately define the expected test environment");
        }
        return t;
    }
}
