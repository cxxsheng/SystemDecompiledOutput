package com.android.internal.display;

/* loaded from: classes4.dex */
public class BrightnessUtils {
    private static final float A = 0.17883277f;
    private static final float B = 0.28466892f;
    private static final float C = 0.5599107f;
    private static final float R = 0.5f;

    public static final float convertGammaToLinear(float f) {
        float exp;
        if (f <= 0.5f) {
            exp = android.util.MathUtils.sq(f / 0.5f);
        } else {
            exp = android.util.MathUtils.exp((f - C) / A) + B;
        }
        return android.util.MathUtils.constrain(exp, 0.0f, 12.0f) / 12.0f;
    }

    public static final float convertLinearToGamma(float f) {
        float f2 = f * 12.0f;
        if (f2 <= 1.0f) {
            return android.util.MathUtils.sqrt(f2) * 0.5f;
        }
        return (android.util.MathUtils.log(f2 - B) * A) + C;
    }
}
