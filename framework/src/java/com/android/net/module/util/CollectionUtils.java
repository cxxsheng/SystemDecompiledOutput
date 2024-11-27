package com.android.net.module.util;

/* loaded from: classes5.dex */
public final class CollectionUtils {
    private CollectionUtils() {
    }

    public static <T> boolean isEmpty(T[] tArr) {
        return tArr == null || tArr.length == 0;
    }

    public static <T> boolean isEmpty(java.util.Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static int[] toIntArray(java.util.Collection<java.lang.Integer> collection) {
        int[] iArr = new int[collection.size()];
        java.util.Iterator<java.lang.Integer> it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            iArr[i] = it.next().intValue();
            i++;
        }
        return iArr;
    }

    public static long[] toLongArray(java.util.Collection<java.lang.Long> collection) {
        long[] jArr = new long[collection.size()];
        java.util.Iterator<java.lang.Long> it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            jArr[i] = it.next().longValue();
            i++;
        }
        return jArr;
    }

    public static <T> boolean all(java.util.Collection<T> collection, java.util.function.Predicate<T> predicate) {
        java.util.Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            if (!predicate.test(it.next())) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean any(java.util.Collection<T> collection, java.util.function.Predicate<T> predicate) {
        return indexOf(collection, predicate) >= 0;
    }

    public static <T> int indexOf(java.util.Collection<T> collection, java.util.function.Predicate<? super T> predicate) {
        java.util.Iterator<T> it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (predicate.test(it.next())) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static <T> boolean any(android.util.SparseArray<T> sparseArray, java.util.function.Predicate<T> predicate) {
        for (int i = 0; i < sparseArray.size(); i++) {
            if (predicate.test(sparseArray.valueAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(short[] sArr, short s) {
        if (sArr == null) {
            return false;
        }
        for (short s2 : sArr) {
            if (s2 == s) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(int[] iArr, int i) {
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

    public static <T> boolean contains(T[] tArr, T t) {
        return indexOf(tArr, t) != -1;
    }

    public static <T> int indexOf(T[] tArr, T t) {
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

    public static int indexOfSubArray(byte[] bArr, byte[] bArr2) {
        int i = 0;
        while (true) {
            boolean z = true;
            if (i < (bArr.length - bArr2.length) + 1) {
                int i2 = 0;
                while (true) {
                    if (i2 >= bArr2.length) {
                        break;
                    }
                    if (bArr[i + i2] == bArr2[i2]) {
                        i2++;
                    } else {
                        z = false;
                        break;
                    }
                }
                if (!z) {
                    i++;
                } else {
                    return i;
                }
            } else {
                return -1;
            }
        }
    }

    public static <T> java.util.ArrayList<T> filter(java.util.Collection<T> collection, java.util.function.Predicate<T> predicate) {
        java.util.ArrayList<T> arrayList = new java.util.ArrayList<>();
        for (T t : collection) {
            if (predicate.test(t)) {
                arrayList.add(t);
            }
        }
        return arrayList;
    }

    public static long total(long[] jArr) {
        long j = 0;
        if (jArr != null) {
            for (long j2 : jArr) {
                j += j2;
            }
        }
        return j;
    }

    public static <T> boolean containsAny(java.util.Collection<T> collection, java.util.Collection<? extends T> collection2) {
        java.util.Iterator<? extends T> it = collection2.iterator();
        while (it.hasNext()) {
            if (collection.contains(it.next())) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean containsAll(java.util.Collection<T> collection, java.util.Collection<? extends T> collection2) {
        return collection.containsAll(collection2);
    }

    public static <T> T findFirst(java.util.Collection<T> collection, java.util.function.Predicate<? super T> predicate) {
        for (T t : collection) {
            if (predicate.test(t)) {
                return t;
            }
        }
        return null;
    }

    public static <T> T findLast(java.util.List<T> list, java.util.function.Predicate<? super T> predicate) {
        for (int size = list.size() - 1; size >= 0; size--) {
            T t = list.get(size);
            if (predicate.test(t)) {
                return t;
            }
        }
        return null;
    }

    public static <T> boolean contains(java.util.Collection<T> collection, java.util.function.Predicate<? super T> predicate) {
        return -1 != indexOf(collection, predicate);
    }

    public static <T, R> java.util.ArrayList<R> map(java.util.Collection<T> collection, java.util.function.Function<? super T, ? extends R> function) {
        java.util.ArrayList<R> arrayList = new java.util.ArrayList<>(collection.size());
        java.util.Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(function.apply(it.next()));
        }
        return arrayList;
    }

    public static <T, R> java.util.ArrayList<android.util.Pair<T, R>> zip(java.util.List<T> list, java.util.List<R> list2) {
        int size = list.size();
        if (size != list2.size()) {
            throw new java.lang.IllegalArgumentException("zip : collections must be the same size");
        }
        java.util.ArrayList<android.util.Pair<T, R>> arrayList = new java.util.ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            arrayList.add(new android.util.Pair<>(list.get(i), list2.get(i)));
        }
        return arrayList;
    }

    public static <T, R> android.util.ArrayMap<T, R> assoc(java.util.List<T> list, java.util.List<R> list2) {
        int size = list.size();
        if (size != list2.size()) {
            throw new java.lang.IllegalArgumentException("assoc : collections must be the same size");
        }
        android.util.ArrayMap<T, R> arrayMap = new android.util.ArrayMap<>(size);
        for (int i = 0; i < size; i++) {
            T t = list.get(i);
            if (arrayMap.containsKey(t)) {
                throw new java.lang.IllegalArgumentException("assoc : keys may not contain the same value twice");
            }
            arrayMap.put(t, list2.get(i));
        }
        return arrayMap;
    }
}
