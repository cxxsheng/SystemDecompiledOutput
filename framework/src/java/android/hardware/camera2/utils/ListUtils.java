package android.hardware.camera2.utils;

/* loaded from: classes.dex */
public class ListUtils {
    public static <T> boolean listContains(java.util.List<T> list, T t) {
        if (list == null) {
            return false;
        }
        return list.contains(t);
    }

    public static <T> boolean listElementsEqualTo(java.util.List<T> list, T t) {
        return list != null && list.size() == 1 && list.contains(t);
    }

    public static <T> java.lang.String listToString(java.util.List<T> list) {
        if (list == null) {
            return null;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append('[');
        int size = list.size();
        java.util.Iterator<T> it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            sb.append(it.next());
            if (i != size - 1) {
                sb.append(',');
            }
            i++;
        }
        sb.append(']');
        return sb.toString();
    }

    public static <T> T listSelectFirstFrom(java.util.List<T> list, T[] tArr) {
        if (list == null) {
            return null;
        }
        for (T t : tArr) {
            if (list.contains(t)) {
                return t;
            }
        }
        return null;
    }

    private ListUtils() {
        throw new java.lang.AssertionError();
    }
}
