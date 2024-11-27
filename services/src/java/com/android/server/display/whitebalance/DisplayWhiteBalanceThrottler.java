package com.android.server.display.whitebalance;

/* loaded from: classes.dex */
class DisplayWhiteBalanceThrottler {
    protected static final java.lang.String TAG = "DisplayWhiteBalanceThrottler";
    private float[] mBaseThresholds;
    private int mDecreaseDebounce;
    private float mDecreaseThreshold;
    private float[] mDecreaseThresholds;
    private int mIncreaseDebounce;
    private float mIncreaseThreshold;
    private float[] mIncreaseThresholds;
    private long mLastTime;
    private float mLastValue;
    protected boolean mLoggingEnabled;

    DisplayWhiteBalanceThrottler(int i, int i2, float[] fArr, float[] fArr2, float[] fArr3) {
        validateArguments(i, i2, fArr, fArr2, fArr3);
        this.mLoggingEnabled = false;
        this.mIncreaseDebounce = i;
        this.mDecreaseDebounce = i2;
        this.mBaseThresholds = fArr;
        this.mIncreaseThresholds = fArr2;
        this.mDecreaseThresholds = fArr3;
        clear();
    }

    public boolean throttle(float f) {
        if (this.mLastTime != -1 && (tooSoon(f) || tooClose(f))) {
            return true;
        }
        computeThresholds(f);
        this.mLastTime = java.lang.System.currentTimeMillis();
        this.mLastValue = f;
        return false;
    }

    public void clear() {
        this.mLastTime = -1L;
        this.mIncreaseThreshold = -1.0f;
        this.mDecreaseThreshold = -1.0f;
        this.mLastValue = -1.0f;
    }

    public boolean setLoggingEnabled(boolean z) {
        if (this.mLoggingEnabled == z) {
            return false;
        }
        this.mLoggingEnabled = z;
        return true;
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("  DisplayWhiteBalanceThrottler");
        printWriter.println("    mLoggingEnabled=" + this.mLoggingEnabled);
        printWriter.println("    mIncreaseDebounce=" + this.mIncreaseDebounce);
        printWriter.println("    mDecreaseDebounce=" + this.mDecreaseDebounce);
        printWriter.println("    mLastTime=" + this.mLastTime);
        printWriter.println("    mBaseThresholds=" + java.util.Arrays.toString(this.mBaseThresholds));
        printWriter.println("    mIncreaseThresholds=" + java.util.Arrays.toString(this.mIncreaseThresholds));
        printWriter.println("    mDecreaseThresholds=" + java.util.Arrays.toString(this.mDecreaseThresholds));
        printWriter.println("    mIncreaseThreshold=" + this.mIncreaseThreshold);
        printWriter.println("    mDecreaseThreshold=" + this.mDecreaseThreshold);
        printWriter.println("    mLastValue=" + this.mLastValue);
    }

    private void validateArguments(float f, float f2, float[] fArr, float[] fArr2, float[] fArr3) {
        if (java.lang.Float.isNaN(f) || f < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            throw new java.lang.IllegalArgumentException("increaseDebounce must be a non-negative number.");
        }
        if (java.lang.Float.isNaN(f2) || f2 < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            throw new java.lang.IllegalArgumentException("decreaseDebounce must be a non-negative number.");
        }
        if (!isValidMapping(fArr, fArr2)) {
            throw new java.lang.IllegalArgumentException("baseThresholds to increaseThresholds is not a valid mapping.");
        }
        if (!isValidMapping(fArr, fArr3)) {
            throw new java.lang.IllegalArgumentException("baseThresholds to decreaseThresholds is not a valid mapping.");
        }
    }

    private static boolean isValidMapping(float[] fArr, float[] fArr2) {
        if (fArr == null || fArr2 == null || fArr.length == 0 || fArr2.length == 0 || fArr.length != fArr2.length) {
            return false;
        }
        float f = -1.0f;
        for (int i = 0; i < fArr.length; i++) {
            if (java.lang.Float.isNaN(fArr[i]) || java.lang.Float.isNaN(fArr2[i]) || fArr[i] < com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || f >= fArr[i]) {
                return false;
            }
            f = fArr[i];
        }
        return true;
    }

    private boolean tooSoon(float f) {
        long j;
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        if (f > this.mLastValue) {
            j = this.mLastTime + this.mIncreaseDebounce;
        } else {
            j = this.mLastTime + this.mDecreaseDebounce;
        }
        boolean z = currentTimeMillis < j;
        if (this.mLoggingEnabled) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(z ? "too soon: " : "late enough: ");
            sb.append(currentTimeMillis);
            sb.append(z ? " < " : " > ");
            sb.append(j);
            android.util.Slog.d(TAG, sb.toString());
        }
        return z;
    }

    private boolean tooClose(float f) {
        float f2;
        boolean z = false;
        if (f > this.mLastValue) {
            f2 = this.mIncreaseThreshold;
            if (f < f2) {
                z = true;
            }
        } else {
            f2 = this.mDecreaseThreshold;
            if (f > f2) {
                z = true;
            }
        }
        if (this.mLoggingEnabled) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(z ? "too close: " : "far enough: ");
            sb.append(f);
            sb.append(f > f2 ? " > " : " < ");
            sb.append(f2);
            android.util.Slog.d(TAG, sb.toString());
        }
        return z;
    }

    private void computeThresholds(float f) {
        int highestIndexBefore = getHighestIndexBefore(f, this.mBaseThresholds);
        this.mIncreaseThreshold = (this.mIncreaseThresholds[highestIndexBefore] + 1.0f) * f;
        this.mDecreaseThreshold = f * (1.0f - this.mDecreaseThresholds[highestIndexBefore]);
    }

    private int getHighestIndexBefore(float f, float[] fArr) {
        for (int i = 0; i < fArr.length; i++) {
            if (fArr[i] >= f) {
                return i;
            }
        }
        return fArr.length - 1;
    }
}
