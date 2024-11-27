package android.hardware.camera2.utils;

/* loaded from: classes.dex */
public class ArrayUtils {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "ArrayUtils";

    public static <T> int getArrayIndex(T[] tArr, T t) {
        if (tArr == null) {
            return -1;
        }
        int i = 0;
        for (T t2 : tArr) {
            if (java.util.Objects.equals(t2, t)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static int getArrayIndex(int[] iArr, int i) {
        if (iArr == null) {
            return -1;
        }
        for (int i2 = 0; i2 < iArr.length; i2++) {
            if (iArr[i2] == i) {
                return i2;
            }
        }
        return -1;
    }

    public static int[] convertStringListToIntArray(java.util.List<java.lang.String> list, java.lang.String[] strArr, int[] iArr) {
        if (list == null) {
            return null;
        }
        java.util.List<java.lang.Integer> convertStringListToIntList = convertStringListToIntList(list, strArr, iArr);
        int size = convertStringListToIntList.size();
        int[] iArr2 = new int[size];
        for (int i = 0; i < size; i++) {
            iArr2[i] = convertStringListToIntList.get(i).intValue();
        }
        return iArr2;
    }

    public static java.util.List<java.lang.Integer> convertStringListToIntList(java.util.List<java.lang.String> list, java.lang.String[] strArr, int[] iArr) {
        if (list == null) {
            return null;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(list.size());
        java.util.Iterator<java.lang.String> it = list.iterator();
        while (it.hasNext()) {
            int arrayIndex = getArrayIndex(strArr, it.next());
            if (arrayIndex >= 0 && arrayIndex < iArr.length) {
                arrayList.add(java.lang.Integer.valueOf(iArr[arrayIndex]));
            }
        }
        return arrayList;
    }

    public static int[] toIntArray(java.util.List<java.lang.Integer> list) {
        if (list == null) {
            return null;
        }
        int[] iArr = new int[list.size()];
        java.util.Iterator<java.lang.Integer> it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            iArr[i] = it.next().intValue();
            i++;
        }
        return iArr;
    }

    public static boolean contains(int[] iArr, int i) {
        return getArrayIndex(iArr, i) != -1;
    }

    public static <T> boolean contains(T[] tArr, T t) {
        return getArrayIndex(tArr, t) != -1;
    }

    private ArrayUtils() {
        throw new java.lang.AssertionError();
    }
}
