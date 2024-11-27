package com.android.internal.graphics.palette;

/* loaded from: classes4.dex */
public class Contrast {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    public static float lighterY(float f, float f2) {
        float f3 = (f2 * (f + 5.0f)) - 5.0f;
        if (f3 > 100.0d) {
            return -1.0f;
        }
        return f3;
    }

    public static float darkerY(float f, float f2) {
        float f3 = ((5.0f - (f2 * 5.0f)) + f) / f2;
        if (f3 < 0.0d) {
            return -1.0f;
        }
        return f3;
    }

    public static float lstarToY(float f) {
        if (f > 8.0f) {
            return (float) (java.lang.Math.pow((f + 16.0d) / 116.0d, 3.0d) * 100.0d);
        }
        return (float) ((f / 903.0f) * 100.0d);
    }

    public static float yToLstar(float f) {
        float f2 = f / 100.0f;
        if (f2 <= 0.008856452f) {
            return f2 * 903.2963f;
        }
        return (((float) java.lang.Math.cbrt(f2)) * 116.0f) - 16.0f;
    }

    public static float contrastYs(float f, float f2) {
        float max = java.lang.Math.max(f, f2);
        if (max == f) {
            f = f2;
        }
        return (max + 5.0f) / (f + 5.0f);
    }
}
