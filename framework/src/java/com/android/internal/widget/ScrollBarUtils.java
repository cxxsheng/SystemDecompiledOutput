package com.android.internal.widget;

/* loaded from: classes5.dex */
public class ScrollBarUtils {
    public static int getThumbLength(int i, int i2, int i3, int i4) {
        int i5 = i2 * 2;
        int round = java.lang.Math.round((i * i3) / i4);
        if (round >= i5) {
            return round;
        }
        return i5;
    }

    public static int getThumbOffset(int i, int i2, int i3, int i4, int i5) {
        int i6 = i - i2;
        int round = java.lang.Math.round((i6 * i5) / (i4 - i3));
        if (round <= i6) {
            return round;
        }
        return i6;
    }
}
