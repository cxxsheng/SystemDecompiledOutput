package com.android.internal.util.jobs;

@android.ravenwood.annotation.RavenwoodKeepWholeClass
/* loaded from: classes.dex */
public final class ParseUtils {
    private ParseUtils() {
    }

    public static int parseInt(@android.annotation.Nullable java.lang.String str, int i) {
        return parseIntWithBase(str, 10, i);
    }

    public static int parseIntWithBase(@android.annotation.Nullable java.lang.String str, int i, int i2) {
        if (str == null) {
            return i2;
        }
        try {
            return java.lang.Integer.parseInt(str, i);
        } catch (java.lang.NumberFormatException e) {
            return i2;
        }
    }

    public static long parseLong(@android.annotation.Nullable java.lang.String str, long j) {
        return parseLongWithBase(str, 10, j);
    }

    public static long parseLongWithBase(@android.annotation.Nullable java.lang.String str, int i, long j) {
        if (str == null) {
            return j;
        }
        try {
            return java.lang.Long.parseLong(str, i);
        } catch (java.lang.NumberFormatException e) {
            return j;
        }
    }

    public static float parseFloat(@android.annotation.Nullable java.lang.String str, float f) {
        if (str == null) {
            return f;
        }
        try {
            return java.lang.Float.parseFloat(str);
        } catch (java.lang.NumberFormatException e) {
            return f;
        }
    }

    public static double parseDouble(@android.annotation.Nullable java.lang.String str, double d) {
        if (str == null) {
            return d;
        }
        try {
            return java.lang.Double.parseDouble(str);
        } catch (java.lang.NumberFormatException e) {
            return d;
        }
    }

    public static boolean parseBoolean(@android.annotation.Nullable java.lang.String str, boolean z) {
        if ("true".equals(str)) {
            return true;
        }
        return ("false".equals(str) || parseInt(str, z ? 1 : 0) == 0) ? false : true;
    }
}
