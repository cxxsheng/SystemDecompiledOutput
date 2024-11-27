package com.android.server.display;

/* loaded from: classes.dex */
public class HysteresisLevels {
    private final float[] mBrighteningThresholdLevels;
    private final float[] mBrighteningThresholdsPercentages;
    private final float[] mDarkeningThresholdLevels;
    private final float[] mDarkeningThresholdsPercentages;
    private final float mMinBrightening;
    private final float mMinDarkening;
    private static final java.lang.String TAG = "HysteresisLevels";
    private static final boolean DEBUG = com.android.server.display.utils.DebugUtils.isDebuggable(TAG);

    HysteresisLevels(float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4, float f, float f2, boolean z) {
        if (fArr.length != fArr3.length || fArr2.length != fArr4.length) {
            throw new java.lang.IllegalArgumentException("Mismatch between hysteresis array lengths.");
        }
        this.mBrighteningThresholdsPercentages = setArrayFormat(fArr, 100.0f);
        this.mDarkeningThresholdsPercentages = setArrayFormat(fArr2, 100.0f);
        this.mBrighteningThresholdLevels = setArrayFormat(fArr3, 1.0f);
        this.mDarkeningThresholdLevels = setArrayFormat(fArr4, 1.0f);
        this.mMinDarkening = f;
        this.mMinBrightening = f2;
    }

    HysteresisLevels(float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4, float f, float f2) {
        this(fArr, fArr2, fArr3, fArr4, f, f2, false);
    }

    public float getBrighteningThreshold(float f) {
        float referenceLevel = getReferenceLevel(f, this.mBrighteningThresholdLevels, this.mBrighteningThresholdsPercentages);
        float f2 = (1.0f + referenceLevel) * f;
        if (DEBUG) {
            android.util.Slog.d(TAG, "bright hysteresis constant=" + referenceLevel + ", threshold=" + f2 + ", value=" + f);
        }
        return java.lang.Math.max(f2, f + this.mMinBrightening);
    }

    public float getDarkeningThreshold(float f) {
        float referenceLevel = getReferenceLevel(f, this.mDarkeningThresholdLevels, this.mDarkeningThresholdsPercentages);
        float f2 = (1.0f - referenceLevel) * f;
        if (DEBUG) {
            android.util.Slog.d(TAG, "dark hysteresis constant=: " + referenceLevel + ", threshold=" + f2 + ", value=" + f);
        }
        return java.lang.Math.max(java.lang.Math.min(f2, f - this.mMinDarkening), com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
    }

    private float getReferenceLevel(float f, float[] fArr, float[] fArr2) {
        if (fArr == null || fArr.length == 0) {
            return com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        }
        int i = 0;
        if (f < fArr[0]) {
            return com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        }
        while (i < fArr.length - 1) {
            int i2 = i + 1;
            if (f < fArr[i2]) {
                break;
            }
            i = i2;
        }
        return fArr2[i];
    }

    private float[] setArrayFormat(float[] fArr, float f) {
        int length = fArr.length;
        float[] fArr2 = new float[length];
        for (int i = 0; length > i; i++) {
            fArr2[i] = fArr[i] / f;
        }
        return fArr2;
    }

    void dump(java.io.PrintWriter printWriter) {
        printWriter.println(TAG);
        printWriter.println("  mBrighteningThresholdLevels=" + java.util.Arrays.toString(this.mBrighteningThresholdLevels));
        printWriter.println("  mBrighteningThresholdsPercentages=" + java.util.Arrays.toString(this.mBrighteningThresholdsPercentages));
        printWriter.println("  mMinBrightening=" + this.mMinBrightening);
        printWriter.println("  mDarkeningThresholdLevels=" + java.util.Arrays.toString(this.mDarkeningThresholdLevels));
        printWriter.println("  mDarkeningThresholdsPercentages=" + java.util.Arrays.toString(this.mDarkeningThresholdsPercentages));
        printWriter.println("  mMinDarkening=" + this.mMinDarkening);
    }
}
