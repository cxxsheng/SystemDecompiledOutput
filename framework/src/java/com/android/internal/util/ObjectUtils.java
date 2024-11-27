package com.android.internal.util;

/* loaded from: classes5.dex */
public class ObjectUtils {
    private ObjectUtils() {
    }

    public static <T> T firstNotNull(T t, T t2) {
        return t != null ? t : (T) java.util.Objects.requireNonNull(t2);
    }

    public static <T extends java.lang.Comparable> int compare(T t, T t2) {
        if (t == null) {
            return t2 != null ? -1 : 0;
        }
        if (t2 != null) {
            return t.compareTo(t2);
        }
        return 1;
    }

    public static <T> T getOrElse(T t, T t2) {
        return t != null ? t : t2;
    }
}
