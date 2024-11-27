package com.android.internal.util;

/* loaded from: classes5.dex */
public final class StringPool {
    private final java.lang.String[] mPool = new java.lang.String[512];

    private static boolean contentEquals(java.lang.String str, char[] cArr, int i, int i2) {
        if (str.length() != i2) {
            return false;
        }
        for (int i3 = 0; i3 < i2; i3++) {
            if (cArr[i + i3] != str.charAt(i3)) {
                return false;
            }
        }
        return true;
    }

    public java.lang.String get(char[] cArr, int i, int i2) {
        int i3 = 0;
        for (int i4 = i; i4 < i + i2; i4++) {
            i3 = (i3 * 31) + cArr[i4];
        }
        int i5 = i3 ^ ((i3 >>> 20) ^ (i3 >>> 12));
        int length = (i5 ^ ((i5 >>> 7) ^ (i5 >>> 4))) & (this.mPool.length - 1);
        java.lang.String str = this.mPool[length];
        if (str != null && contentEquals(str, cArr, i, i2)) {
            return str;
        }
        java.lang.String str2 = new java.lang.String(cArr, i, i2);
        this.mPool[length] = str2;
        return str2;
    }
}
