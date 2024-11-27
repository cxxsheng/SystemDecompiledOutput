package com.android.internal.util;

/* loaded from: classes5.dex */
public final class GrowingArrayUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.lang.Object, java.lang.Object[]] */
    public static <T> T[] append(T[] tArr, int i, T t) {
        if (i + 1 > tArr.length) {
            ?? newUnpaddedArray = com.android.internal.util.ArrayUtils.newUnpaddedArray(tArr.getClass().getComponentType(), growSize(i));
            java.lang.System.arraycopy(tArr, 0, newUnpaddedArray, 0, i);
            tArr = newUnpaddedArray;
        }
        tArr[i] = t;
        return tArr;
    }

    public static int[] append(int[] iArr, int i, int i2) {
        if (i + 1 > iArr.length) {
            int[] newUnpaddedIntArray = com.android.internal.util.ArrayUtils.newUnpaddedIntArray(growSize(i));
            java.lang.System.arraycopy(iArr, 0, newUnpaddedIntArray, 0, i);
            iArr = newUnpaddedIntArray;
        }
        iArr[i] = i2;
        return iArr;
    }

    public static long[] append(long[] jArr, int i, long j) {
        if (i + 1 > jArr.length) {
            long[] newUnpaddedLongArray = com.android.internal.util.ArrayUtils.newUnpaddedLongArray(growSize(i));
            java.lang.System.arraycopy(jArr, 0, newUnpaddedLongArray, 0, i);
            jArr = newUnpaddedLongArray;
        }
        jArr[i] = j;
        return jArr;
    }

    public static boolean[] append(boolean[] zArr, int i, boolean z) {
        if (i + 1 > zArr.length) {
            boolean[] newUnpaddedBooleanArray = com.android.internal.util.ArrayUtils.newUnpaddedBooleanArray(growSize(i));
            java.lang.System.arraycopy(zArr, 0, newUnpaddedBooleanArray, 0, i);
            zArr = newUnpaddedBooleanArray;
        }
        zArr[i] = z;
        return zArr;
    }

    public static float[] append(float[] fArr, int i, float f) {
        if (i + 1 > fArr.length) {
            float[] newUnpaddedFloatArray = com.android.internal.util.ArrayUtils.newUnpaddedFloatArray(growSize(i));
            java.lang.System.arraycopy(fArr, 0, newUnpaddedFloatArray, 0, i);
            fArr = newUnpaddedFloatArray;
        }
        fArr[i] = f;
        return fArr;
    }

    public static <T> T[] insert(T[] tArr, int i, int i2, T t) {
        if (i + 1 <= tArr.length) {
            java.lang.System.arraycopy(tArr, i2, tArr, i2 + 1, i - i2);
            tArr[i2] = t;
            return tArr;
        }
        T[] tArr2 = (T[]) com.android.internal.util.ArrayUtils.newUnpaddedArray(tArr.getClass().getComponentType(), growSize(i));
        java.lang.System.arraycopy(tArr, 0, tArr2, 0, i2);
        tArr2[i2] = t;
        java.lang.System.arraycopy(tArr, i2, tArr2, i2 + 1, tArr.length - i2);
        return tArr2;
    }

    public static int[] insert(int[] iArr, int i, int i2, int i3) {
        if (i + 1 <= iArr.length) {
            java.lang.System.arraycopy(iArr, i2, iArr, i2 + 1, i - i2);
            iArr[i2] = i3;
            return iArr;
        }
        int[] newUnpaddedIntArray = com.android.internal.util.ArrayUtils.newUnpaddedIntArray(growSize(i));
        java.lang.System.arraycopy(iArr, 0, newUnpaddedIntArray, 0, i2);
        newUnpaddedIntArray[i2] = i3;
        java.lang.System.arraycopy(iArr, i2, newUnpaddedIntArray, i2 + 1, iArr.length - i2);
        return newUnpaddedIntArray;
    }

    public static long[] insert(long[] jArr, int i, int i2, long j) {
        if (i + 1 <= jArr.length) {
            java.lang.System.arraycopy(jArr, i2, jArr, i2 + 1, i - i2);
            jArr[i2] = j;
            return jArr;
        }
        long[] newUnpaddedLongArray = com.android.internal.util.ArrayUtils.newUnpaddedLongArray(growSize(i));
        java.lang.System.arraycopy(jArr, 0, newUnpaddedLongArray, 0, i2);
        newUnpaddedLongArray[i2] = j;
        java.lang.System.arraycopy(jArr, i2, newUnpaddedLongArray, i2 + 1, jArr.length - i2);
        return newUnpaddedLongArray;
    }

    public static boolean[] insert(boolean[] zArr, int i, int i2, boolean z) {
        if (i + 1 <= zArr.length) {
            java.lang.System.arraycopy(zArr, i2, zArr, i2 + 1, i - i2);
            zArr[i2] = z;
            return zArr;
        }
        boolean[] newUnpaddedBooleanArray = com.android.internal.util.ArrayUtils.newUnpaddedBooleanArray(growSize(i));
        java.lang.System.arraycopy(zArr, 0, newUnpaddedBooleanArray, 0, i2);
        newUnpaddedBooleanArray[i2] = z;
        java.lang.System.arraycopy(zArr, i2, newUnpaddedBooleanArray, i2 + 1, zArr.length - i2);
        return newUnpaddedBooleanArray;
    }

    public static int growSize(int i) {
        if (i <= 4) {
            return 8;
        }
        return i * 2;
    }

    private GrowingArrayUtils() {
    }
}
