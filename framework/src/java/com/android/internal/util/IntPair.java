package com.android.internal.util;

/* loaded from: classes5.dex */
public class IntPair {
    private IntPair() {
    }

    public static long of(int i, int i2) {
        return (i2 & 4294967295L) | (i << 32);
    }

    public static int first(long j) {
        return (int) (j >> 32);
    }

    public static int second(long j) {
        return (int) j;
    }
}
