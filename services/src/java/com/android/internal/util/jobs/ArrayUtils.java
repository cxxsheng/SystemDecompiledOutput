package com.android.internal.util.jobs;

@android.ravenwood.annotation.RavenwoodKeepWholeClass
/* loaded from: classes.dex */
public class ArrayUtils {
    private static final int CACHE_SIZE = 73;
    private static java.lang.Object[] sCache = new java.lang.Object[73];
    public static final java.io.File[] EMPTY_FILE = new java.io.File[0];

    private ArrayUtils() {
    }

    @android.ravenwood.annotation.RavenwoodReplace
    public static byte[] newUnpaddedByteArray(int i) {
        return (byte[]) dalvik.system.VMRuntime.getRuntime().newUnpaddedArray(java.lang.Byte.TYPE, i);
    }

    @android.ravenwood.annotation.RavenwoodReplace
    public static char[] newUnpaddedCharArray(int i) {
        return (char[]) dalvik.system.VMRuntime.getRuntime().newUnpaddedArray(java.lang.Character.TYPE, i);
    }

    @android.ravenwood.annotation.RavenwoodReplace
    public static int[] newUnpaddedIntArray(int i) {
        return (int[]) dalvik.system.VMRuntime.getRuntime().newUnpaddedArray(java.lang.Integer.TYPE, i);
    }

    @android.ravenwood.annotation.RavenwoodReplace
    public static boolean[] newUnpaddedBooleanArray(int i) {
        return (boolean[]) dalvik.system.VMRuntime.getRuntime().newUnpaddedArray(java.lang.Boolean.TYPE, i);
    }

    @android.ravenwood.annotation.RavenwoodReplace
    public static long[] newUnpaddedLongArray(int i) {
        return (long[]) dalvik.system.VMRuntime.getRuntime().newUnpaddedArray(java.lang.Long.TYPE, i);
    }

    @android.ravenwood.annotation.RavenwoodReplace
    public static float[] newUnpaddedFloatArray(int i) {
        return (float[]) dalvik.system.VMRuntime.getRuntime().newUnpaddedArray(java.lang.Float.TYPE, i);
    }

    @android.ravenwood.annotation.RavenwoodReplace
    public static java.lang.Object[] newUnpaddedObjectArray(int i) {
        return (java.lang.Object[]) dalvik.system.VMRuntime.getRuntime().newUnpaddedArray(java.lang.Object.class, i);
    }

    @android.ravenwood.annotation.RavenwoodReplace
    public static <T> T[] newUnpaddedArray(java.lang.Class<T> cls, int i) {
        return (T[]) ((java.lang.Object[]) dalvik.system.VMRuntime.getRuntime().newUnpaddedArray(cls, i));
    }

    public static byte[] newUnpaddedByteArray$ravenwood(int i) {
        return new byte[i];
    }

    public static char[] newUnpaddedCharArray$ravenwood(int i) {
        return new char[i];
    }

    public static int[] newUnpaddedIntArray$ravenwood(int i) {
        return new int[i];
    }

    public static boolean[] newUnpaddedBooleanArray$ravenwood(int i) {
        return new boolean[i];
    }

    public static long[] newUnpaddedLongArray$ravenwood(int i) {
        return new long[i];
    }

    public static float[] newUnpaddedFloatArray$ravenwood(int i) {
        return new float[i];
    }

    public static java.lang.Object[] newUnpaddedObjectArray$ravenwood(int i) {
        return new java.lang.Object[i];
    }

    public static <T> T[] newUnpaddedArray$ravenwood(java.lang.Class<T> cls, int i) {
        return (T[]) ((java.lang.Object[]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) cls, i));
    }

    public static boolean equals(byte[] bArr, byte[] bArr2, int i) {
        if (i < 0) {
            throw new java.lang.IllegalArgumentException();
        }
        if (bArr == bArr2) {
            return true;
        }
        if (bArr == null || bArr2 == null || bArr.length < i || bArr2.length < i) {
            return false;
        }
        for (int i2 = 0; i2 < i; i2++) {
            if (bArr[i2] != bArr2[i2]) {
                return false;
            }
        }
        return true;
    }

    public static <T> T[] emptyArray(java.lang.Class<T> cls) {
        if (cls == java.lang.Object.class) {
            return (T[]) android.util.EmptyArray.OBJECT;
        }
        int hashCode = (cls.hashCode() & Integer.MAX_VALUE) % 73;
        java.lang.Object obj = sCache[hashCode];
        if (obj == null || obj.getClass().getComponentType() != cls) {
            obj = java.lang.reflect.Array.newInstance((java.lang.Class<?>) cls, 0);
            sCache[hashCode] = obj;
        }
        return (T[]) ((java.lang.Object[]) obj);
    }

    @android.annotation.NonNull
    public static <T> T[] emptyIfNull(@android.annotation.Nullable T[] tArr, java.lang.Class<T> cls) {
        return tArr != null ? tArr : (T[]) emptyArray(cls);
    }

    public static boolean isEmpty(@android.annotation.Nullable java.util.Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(@android.annotation.Nullable java.util.Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static <T> boolean isEmpty(@android.annotation.Nullable T[] tArr) {
        return tArr == null || tArr.length == 0;
    }

    public static boolean isEmpty(@android.annotation.Nullable int[] iArr) {
        return iArr == null || iArr.length == 0;
    }

    public static boolean isEmpty(@android.annotation.Nullable long[] jArr) {
        return jArr == null || jArr.length == 0;
    }

    public static boolean isEmpty(@android.annotation.Nullable byte[] bArr) {
        return bArr == null || bArr.length == 0;
    }

    public static boolean isEmpty(@android.annotation.Nullable boolean[] zArr) {
        return zArr == null || zArr.length == 0;
    }

    public static int size(@android.annotation.Nullable java.lang.Object[] objArr) {
        if (objArr == null) {
            return 0;
        }
        return objArr.length;
    }

    public static int size(@android.annotation.Nullable java.util.Collection<?> collection) {
        if (collection == null) {
            return 0;
        }
        return collection.size();
    }

    public static int size(@android.annotation.Nullable java.util.Map<?, ?> map) {
        if (map == null) {
            return 0;
        }
        return map.size();
    }

    public static <T> boolean contains(@android.annotation.Nullable T[] tArr, T t) {
        return indexOf(tArr, t) != -1;
    }

    public static <T> int indexOf(@android.annotation.Nullable T[] tArr, T t) {
        if (tArr == null) {
            return -1;
        }
        for (int i = 0; i < tArr.length; i++) {
            if (java.util.Objects.equals(tArr[i], t)) {
                return i;
            }
        }
        return -1;
    }

    public static <T> boolean containsAll(@android.annotation.Nullable T[] tArr, T[] tArr2) {
        if (tArr2 == null) {
            return true;
        }
        for (T t : tArr2) {
            if (!contains(tArr, t)) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean containsAny(@android.annotation.Nullable T[] tArr, T[] tArr2) {
        if (tArr2 == null) {
            return false;
        }
        for (T t : tArr2) {
            if (contains(tArr, t)) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(@android.annotation.Nullable int[] iArr, int i) {
        if (iArr == null) {
            return false;
        }
        for (int i2 : iArr) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(@android.annotation.Nullable long[] jArr, long j) {
        if (jArr == null) {
            return false;
        }
        for (long j2 : jArr) {
            if (j2 == j) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(@android.annotation.Nullable char[] cArr, char c) {
        if (cArr == null) {
            return false;
        }
        for (char c2 : cArr) {
            if (c2 == c) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean containsAll(@android.annotation.Nullable char[] cArr, char[] cArr2) {
        if (cArr2 == null) {
            return true;
        }
        for (char c : cArr2) {
            if (!contains(cArr, c)) {
                return false;
            }
        }
        return true;
    }

    public static long total(@android.annotation.Nullable long[] jArr) {
        long j = 0;
        if (jArr != null) {
            for (long j2 : jArr) {
                j += j2;
            }
        }
        return j;
    }

    @java.lang.Deprecated
    public static int[] convertToIntArray(java.util.List<java.lang.Integer> list) {
        int[] iArr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            iArr[i] = list.get(i).intValue();
        }
        return iArr;
    }

    @android.annotation.NonNull
    public static int[] convertToIntArray(@android.annotation.NonNull android.util.ArraySet<java.lang.Integer> arraySet) {
        int size = arraySet.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = arraySet.valueAt(i).intValue();
        }
        return iArr;
    }

    @android.annotation.Nullable
    public static long[] convertToLongArray(@android.annotation.Nullable int[] iArr) {
        if (iArr == null) {
            return null;
        }
        long[] jArr = new long[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            jArr[i] = iArr[i];
        }
        return jArr;
    }

    @android.annotation.NonNull
    public static <T> T[] concat(java.lang.Class<T> cls, @android.annotation.Nullable T[]... tArr) {
        if (tArr == null || tArr.length == 0) {
            return (T[]) createEmptyArray(cls);
        }
        int i = 0;
        for (T[] tArr2 : tArr) {
            if (tArr2 != null) {
                i += tArr2.length;
            }
        }
        if (i == 0) {
            return (T[]) createEmptyArray(cls);
        }
        T[] tArr3 = (T[]) ((java.lang.Object[]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) cls, i));
        int i2 = 0;
        for (T[] tArr4 : tArr) {
            if (tArr4 != null && tArr4.length != 0) {
                java.lang.System.arraycopy(tArr4, 0, tArr3, i2, tArr4.length);
                i2 += tArr4.length;
            }
        }
        return tArr3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @android.annotation.NonNull
    private static <T> T[] createEmptyArray(java.lang.Class<T> cls) {
        if (cls == java.lang.String.class) {
            return (T[]) android.util.EmptyArray.STRING;
        }
        if (cls == java.lang.Object.class) {
            return (T[]) android.util.EmptyArray.OBJECT;
        }
        return (T[]) ((java.lang.Object[]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) cls, 0));
    }

    @android.annotation.NonNull
    public static byte[] concat(@android.annotation.Nullable byte[]... bArr) {
        if (bArr == null) {
            return new byte[0];
        }
        int i = 0;
        for (byte[] bArr2 : bArr) {
            if (bArr2 != null) {
                i += bArr2.length;
            }
        }
        byte[] bArr3 = new byte[i];
        int i2 = 0;
        for (byte[] bArr4 : bArr) {
            if (bArr4 != null) {
                java.lang.System.arraycopy(bArr4, 0, bArr3, i2, bArr4.length);
                i2 += bArr4.length;
            }
        }
        return bArr3;
    }

    @android.annotation.NonNull
    public static <T> T[] appendElement(java.lang.Class<T> cls, @android.annotation.Nullable T[] tArr, T t) {
        return (T[]) appendElement(cls, tArr, t, false);
    }

    @android.annotation.NonNull
    public static <T> T[] appendElement(java.lang.Class<T> cls, @android.annotation.Nullable T[] tArr, T t, boolean z) {
        T[] tArr2;
        int i = 0;
        if (tArr != null) {
            if (!z && contains(tArr, t)) {
                return tArr;
            }
            int length = tArr.length;
            tArr2 = (T[]) ((java.lang.Object[]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) cls, length + 1));
            java.lang.System.arraycopy(tArr, 0, tArr2, 0, length);
            i = length;
        } else {
            tArr2 = (T[]) ((java.lang.Object[]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) cls, 1));
        }
        tArr2[i] = t;
        return tArr2;
    }

    @android.annotation.Nullable
    public static <T> T[] removeElement(java.lang.Class<T> cls, @android.annotation.Nullable T[] tArr, T t) {
        if (tArr != null) {
            if (!contains(tArr, t)) {
                return tArr;
            }
            int length = tArr.length;
            for (int i = 0; i < length; i++) {
                if (java.util.Objects.equals(tArr[i], t)) {
                    if (length == 1) {
                        return null;
                    }
                    T[] tArr2 = (T[]) ((java.lang.Object[]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) cls, length - 1));
                    java.lang.System.arraycopy(tArr, 0, tArr2, 0, i);
                    java.lang.System.arraycopy(tArr, i + 1, tArr2, i, (length - i) - 1);
                    return tArr2;
                }
            }
        }
        return tArr;
    }

    @android.annotation.NonNull
    public static int[] appendInt(@android.annotation.Nullable int[] iArr, int i, boolean z) {
        if (iArr == null) {
            return new int[]{i};
        }
        int length = iArr.length;
        if (!z) {
            for (int i2 : iArr) {
                if (i2 == i) {
                    return iArr;
                }
            }
        }
        int[] iArr2 = new int[length + 1];
        java.lang.System.arraycopy(iArr, 0, iArr2, 0, length);
        iArr2[length] = i;
        return iArr2;
    }

    @android.annotation.NonNull
    public static int[] appendInt(@android.annotation.Nullable int[] iArr, int i) {
        return appendInt(iArr, i, false);
    }

    @android.annotation.Nullable
    public static int[] removeInt(@android.annotation.Nullable int[] iArr, int i) {
        if (iArr == null) {
            return null;
        }
        int length = iArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (iArr[i2] == i) {
                int i3 = length - 1;
                int[] iArr2 = new int[i3];
                if (i2 > 0) {
                    java.lang.System.arraycopy(iArr, 0, iArr2, 0, i2);
                }
                if (i2 < i3) {
                    java.lang.System.arraycopy(iArr, i2 + 1, iArr2, i2, (length - i2) - 1);
                }
                return iArr2;
            }
        }
        return iArr;
    }

    @android.annotation.Nullable
    public static java.lang.String[] removeString(@android.annotation.Nullable java.lang.String[] strArr, java.lang.String str) {
        if (strArr == null) {
            return null;
        }
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            if (java.util.Objects.equals(strArr[i], str)) {
                int i2 = length - 1;
                java.lang.String[] strArr2 = new java.lang.String[i2];
                if (i > 0) {
                    java.lang.System.arraycopy(strArr, 0, strArr2, 0, i);
                }
                if (i < i2) {
                    java.lang.System.arraycopy(strArr, i + 1, strArr2, i, (length - i) - 1);
                }
                return strArr2;
            }
        }
        return strArr;
    }

    @android.annotation.NonNull
    public static long[] appendLong(@android.annotation.Nullable long[] jArr, long j, boolean z) {
        if (jArr == null) {
            return new long[]{j};
        }
        int length = jArr.length;
        if (!z) {
            for (long j2 : jArr) {
                if (j2 == j) {
                    return jArr;
                }
            }
        }
        long[] jArr2 = new long[1 + length];
        java.lang.System.arraycopy(jArr, 0, jArr2, 0, length);
        jArr2[length] = j;
        return jArr2;
    }

    public static boolean[] appendBoolean(@android.annotation.Nullable boolean[] zArr, boolean z) {
        if (zArr == null) {
            return new boolean[]{z};
        }
        int length = zArr.length;
        boolean[] zArr2 = new boolean[1 + length];
        java.lang.System.arraycopy(zArr, 0, zArr2, 0, length);
        zArr2[length] = z;
        return zArr2;
    }

    @android.annotation.NonNull
    public static long[] appendLong(@android.annotation.Nullable long[] jArr, long j) {
        return appendLong(jArr, j, false);
    }

    @android.annotation.Nullable
    public static long[] removeLong(@android.annotation.Nullable long[] jArr, long j) {
        if (jArr == null) {
            return null;
        }
        int length = jArr.length;
        for (int i = 0; i < length; i++) {
            if (jArr[i] == j) {
                int i2 = length - 1;
                long[] jArr2 = new long[i2];
                if (i > 0) {
                    java.lang.System.arraycopy(jArr, 0, jArr2, 0, i);
                }
                if (i < i2) {
                    java.lang.System.arraycopy(jArr, i + 1, jArr2, i, (length - i) - 1);
                }
                return jArr2;
            }
        }
        return jArr;
    }

    @android.annotation.Nullable
    public static long[] cloneOrNull(@android.annotation.Nullable long[] jArr) {
        if (jArr != null) {
            return (long[]) jArr.clone();
        }
        return null;
    }

    @android.annotation.Nullable
    public static <T> T[] cloneOrNull(@android.annotation.Nullable T[] tArr) {
        if (tArr != null) {
            return (T[]) ((java.lang.Object[]) tArr.clone());
        }
        return null;
    }

    @android.annotation.Nullable
    public static <T> android.util.ArraySet<T> cloneOrNull(@android.annotation.Nullable android.util.ArraySet<T> arraySet) {
        if (arraySet != null) {
            return new android.util.ArraySet<>((android.util.ArraySet) arraySet);
        }
        return null;
    }

    @android.annotation.NonNull
    public static <T> android.util.ArraySet<T> add(@android.annotation.Nullable android.util.ArraySet<T> arraySet, T t) {
        if (arraySet == null) {
            arraySet = new android.util.ArraySet<>();
        }
        arraySet.add(t);
        return arraySet;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @android.annotation.NonNull
    public static <T> android.util.ArraySet<T> addAll(@android.annotation.Nullable android.util.ArraySet<T> arraySet, @android.annotation.Nullable java.util.Collection<T> collection) {
        if (arraySet == null) {
            arraySet = new android.util.ArraySet<>();
        }
        if (collection != 0) {
            arraySet.addAll((java.util.Collection<? extends T>) collection);
        }
        return arraySet;
    }

    @android.annotation.Nullable
    public static <T> android.util.ArraySet<T> remove(@android.annotation.Nullable android.util.ArraySet<T> arraySet, T t) {
        if (arraySet == null) {
            return null;
        }
        arraySet.remove(t);
        if (arraySet.isEmpty()) {
            return null;
        }
        return arraySet;
    }

    @android.annotation.NonNull
    public static <T> java.util.ArrayList<T> add(@android.annotation.Nullable java.util.ArrayList<T> arrayList, T t) {
        if (arrayList == null) {
            arrayList = new java.util.ArrayList<>();
        }
        arrayList.add(t);
        return arrayList;
    }

    @android.annotation.NonNull
    public static <T> java.util.ArrayList<T> add(@android.annotation.Nullable java.util.ArrayList<T> arrayList, int i, T t) {
        if (arrayList == null) {
            arrayList = new java.util.ArrayList<>();
        }
        arrayList.add(i, t);
        return arrayList;
    }

    @android.annotation.Nullable
    public static <T> java.util.ArrayList<T> remove(@android.annotation.Nullable java.util.ArrayList<T> arrayList, T t) {
        if (arrayList == null) {
            return null;
        }
        arrayList.remove(t);
        if (arrayList.isEmpty()) {
            return null;
        }
        return arrayList;
    }

    public static <T> boolean contains(@android.annotation.Nullable java.util.Collection<T> collection, T t) {
        if (collection != null) {
            return collection.contains(t);
        }
        return false;
    }

    @android.annotation.Nullable
    public static <T> T[] trimToSize(@android.annotation.Nullable T[] tArr, int i) {
        if (tArr == null || i == 0) {
            return null;
        }
        if (tArr.length == i) {
            return tArr;
        }
        return (T[]) java.util.Arrays.copyOf(tArr, i);
    }

    public static <T> boolean referenceEquals(java.util.ArrayList<T> arrayList, java.util.ArrayList<T> arrayList2) {
        boolean z;
        if (arrayList == arrayList2) {
            return true;
        }
        int size = arrayList.size();
        if (size != arrayList2.size()) {
            return false;
        }
        boolean z2 = false;
        for (int i = 0; i < size && !z2; i++) {
            if (arrayList.get(i) != arrayList2.get(i)) {
                z = true;
            } else {
                z = false;
            }
            z2 |= z;
        }
        return !z2;
    }

    public static <T> int unstableRemoveIf(@android.annotation.Nullable java.util.ArrayList<T> arrayList, @android.annotation.NonNull java.util.function.Predicate<T> predicate) {
        int i = 0;
        if (arrayList == null) {
            return 0;
        }
        int size = arrayList.size();
        int i2 = size - 1;
        int i3 = i2;
        while (i <= i3) {
            while (i < size && !predicate.test(arrayList.get(i))) {
                i++;
            }
            while (i3 > i && predicate.test(arrayList.get(i3))) {
                i3--;
            }
            if (i >= i3) {
                break;
            }
            java.util.Collections.swap(arrayList, i, i3);
            i++;
            i3--;
        }
        while (i2 >= i) {
            arrayList.remove(i2);
            i2--;
        }
        return size - i;
    }

    @android.annotation.NonNull
    public static int[] defeatNullable(@android.annotation.Nullable int[] iArr) {
        return iArr != null ? iArr : android.util.EmptyArray.INT;
    }

    @android.annotation.NonNull
    public static java.lang.String[] defeatNullable(@android.annotation.Nullable java.lang.String[] strArr) {
        return strArr != null ? strArr : android.util.EmptyArray.STRING;
    }

    @android.annotation.NonNull
    public static java.io.File[] defeatNullable(@android.annotation.Nullable java.io.File[] fileArr) {
        return fileArr != null ? fileArr : EMPTY_FILE;
    }

    public static void checkBounds(int i, int i2) {
        if (i2 < 0 || i <= i2) {
            throw new java.lang.ArrayIndexOutOfBoundsException("length=" + i + "; index=" + i2);
        }
    }

    public static void throwsIfOutOfBounds(int i, int i2, int i3) {
        if (i < 0) {
            throw new java.lang.ArrayIndexOutOfBoundsException("Negative length: " + i);
        }
        if ((i2 | i3) < 0 || i2 > i - i3) {
            throw new java.lang.ArrayIndexOutOfBoundsException("length=" + i + "; regionStart=" + i2 + "; regionLength=" + i3);
        }
    }

    public static <T> T[] filterNotNull(T[] tArr, java.util.function.IntFunction<T[]> intFunction) {
        int size = size(tArr);
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            if (tArr[i2] == null) {
                i++;
            }
        }
        if (i == 0) {
            return tArr;
        }
        T[] apply = intFunction.apply(size - i);
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            if (tArr[i4] != null) {
                apply[i3] = tArr[i4];
                i3++;
            }
        }
        return apply;
    }

    @android.annotation.Nullable
    public static <T> T[] filter(@android.annotation.Nullable T[] tArr, @android.annotation.NonNull java.util.function.IntFunction<T[]> intFunction, @android.annotation.NonNull java.util.function.Predicate<T> predicate) {
        if (isEmpty(tArr)) {
            return tArr;
        }
        int size = size(tArr);
        boolean[] zArr = new boolean[size];
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            zArr[i2] = predicate.test(tArr[i2]);
            if (zArr[i2]) {
                i++;
            }
        }
        if (i == tArr.length) {
            return tArr;
        }
        T[] apply = intFunction.apply(i);
        if (i == 0) {
            return apply;
        }
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            if (zArr[i4]) {
                apply[i3] = tArr[i4];
                i3++;
            }
        }
        return apply;
    }

    public static boolean startsWith(byte[] bArr, byte[] bArr2) {
        if (bArr == null || bArr2 == null || bArr.length < bArr2.length) {
            return false;
        }
        for (int i = 0; i < bArr2.length; i++) {
            if (bArr[i] != bArr2[i]) {
                return false;
            }
        }
        return true;
    }

    @android.annotation.Nullable
    public static <T> T find(@android.annotation.Nullable T[] tArr, @android.annotation.NonNull java.util.function.Predicate<T> predicate) {
        if (isEmpty(tArr)) {
            return null;
        }
        for (T t : tArr) {
            if (predicate.test(t)) {
                return t;
            }
        }
        return null;
    }

    public static java.lang.String deepToString(java.lang.Object obj) {
        if (obj != null && obj.getClass().isArray()) {
            if (obj.getClass() == boolean[].class) {
                return java.util.Arrays.toString((boolean[]) obj);
            }
            if (obj.getClass() == byte[].class) {
                return java.util.Arrays.toString((byte[]) obj);
            }
            if (obj.getClass() == char[].class) {
                return java.util.Arrays.toString((char[]) obj);
            }
            if (obj.getClass() == double[].class) {
                return java.util.Arrays.toString((double[]) obj);
            }
            if (obj.getClass() == float[].class) {
                return java.util.Arrays.toString((float[]) obj);
            }
            if (obj.getClass() == int[].class) {
                return java.util.Arrays.toString((int[]) obj);
            }
            if (obj.getClass() == long[].class) {
                return java.util.Arrays.toString((long[]) obj);
            }
            if (obj.getClass() == short[].class) {
                return java.util.Arrays.toString((short[]) obj);
            }
            return java.util.Arrays.deepToString((java.lang.Object[]) obj);
        }
        return java.lang.String.valueOf(obj);
    }

    @android.annotation.Nullable
    public static <T> T getOrNull(@android.annotation.Nullable T[] tArr, int i) {
        if (tArr == null || tArr.length <= i) {
            return null;
        }
        return tArr[i];
    }

    @android.annotation.Nullable
    public static <T> T firstOrNull(T[] tArr) {
        if (tArr.length > 0) {
            return tArr[0];
        }
        return null;
    }

    public static <T> java.util.List<T> toList(T[] tArr) {
        java.util.ArrayList arrayList = new java.util.ArrayList(tArr.length);
        for (T t : tArr) {
            arrayList.add(t);
        }
        return arrayList;
    }
}
