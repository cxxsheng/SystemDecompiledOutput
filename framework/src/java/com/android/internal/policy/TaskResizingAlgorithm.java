package com.android.internal.policy;

/* loaded from: classes5.dex */
public class TaskResizingAlgorithm {
    public static final int CTRL_BOTTOM = 8;
    public static final int CTRL_LEFT = 1;
    public static final int CTRL_NONE = 0;
    public static final int CTRL_RIGHT = 2;
    public static final int CTRL_TOP = 4;
    public static final float MIN_ASPECT = 1.2f;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CtrlType {
    }

    public static android.graphics.Rect resizeDrag(float f, float f2, float f3, float f4, android.graphics.Rect rect, int i, int i2, int i3, android.graphics.Point point, boolean z, boolean z2) {
        int i4;
        int i5;
        int max;
        int max2;
        int min;
        int i6;
        int round = java.lang.Math.round(f - f3);
        int round2 = java.lang.Math.round(f2 - f4);
        int i7 = rect.left;
        int i8 = rect.top;
        int i9 = rect.right;
        int i10 = rect.bottom;
        int i11 = i9 - i7;
        int i12 = i10 - i8;
        int i13 = i & 1;
        if (i13 != 0) {
            i4 = java.lang.Math.max(i2, java.lang.Math.min(i11 - round, point.x));
        } else if ((i & 2) == 0) {
            i4 = i11;
        } else {
            i4 = java.lang.Math.max(i2, java.lang.Math.min(round + i11, point.x));
        }
        int i14 = i & 4;
        if (i14 != 0) {
            i5 = java.lang.Math.max(i3, java.lang.Math.min(i12 - round2, point.y));
        } else if ((i & 8) == 0) {
            i5 = i12;
        } else {
            i5 = java.lang.Math.max(i3, java.lang.Math.min(round2 + i12, point.y));
        }
        float f5 = i4 / i5;
        if (z && ((z2 && f5 < 1.2f) || (!z2 && f5 > 0.8333333002196431d))) {
            if (z2) {
                int max3 = java.lang.Math.max(i2, java.lang.Math.min(point.x, i4));
                max = java.lang.Math.min(i5, java.lang.Math.round(max3 / 1.2f));
                if (max < i3) {
                    max3 = java.lang.Math.max(i2, java.lang.Math.min(point.x, java.lang.Math.round(i3 * 1.2f)));
                    max = i3;
                }
                max2 = java.lang.Math.max(i3, java.lang.Math.min(point.y, i5));
                int i15 = max3;
                min = java.lang.Math.max(i4, java.lang.Math.round(max2 * 1.2f));
                if (min >= i2) {
                    i6 = i15;
                } else {
                    max2 = java.lang.Math.max(i3, java.lang.Math.min(point.y, java.lang.Math.round(i2 / 1.2f)));
                    min = i2;
                    i6 = i15;
                }
            } else {
                int max4 = java.lang.Math.max(i2, java.lang.Math.min(point.x, i4));
                max = java.lang.Math.max(i5, java.lang.Math.round(max4 * 1.2f));
                if (max < i3) {
                    max4 = java.lang.Math.max(i2, java.lang.Math.min(point.x, java.lang.Math.round(i3 / 1.2f)));
                    max = i3;
                }
                max2 = java.lang.Math.max(i3, java.lang.Math.min(point.y, i5));
                int i16 = max4;
                min = java.lang.Math.min(i4, java.lang.Math.round(max2 / 1.2f));
                if (min >= i2) {
                    i6 = i16;
                } else {
                    max2 = java.lang.Math.max(i3, java.lang.Math.min(point.y, java.lang.Math.round(i2 * 1.2f)));
                    min = i2;
                    i6 = i16;
                }
            }
            if ((i4 > i11 || i5 > i12) == (i6 * max > min * max2)) {
                i4 = i6;
                i5 = max;
            } else {
                i4 = min;
                i5 = max2;
            }
        }
        if (i13 != 0) {
            i7 = i9 - i4;
        } else {
            i9 = i7 + i4;
        }
        if (i14 != 0) {
            i8 = i10 - i5;
        } else {
            i10 = i8 + i5;
        }
        return new android.graphics.Rect(i7, i8, i9, i10);
    }
}
