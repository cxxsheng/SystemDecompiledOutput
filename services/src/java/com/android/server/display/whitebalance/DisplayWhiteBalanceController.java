package com.android.server.display.whitebalance;

/* loaded from: classes.dex */
public class DisplayWhiteBalanceController implements com.android.server.display.whitebalance.AmbientSensor.AmbientBrightnessSensor.Callbacks, com.android.server.display.whitebalance.AmbientSensor.AmbientColorTemperatureSensor.Callbacks {
    private static final java.lang.String TAG = "DisplayWhiteBalanceController";
    private float mAmbientColorTemperature;
    private final com.android.server.display.utils.History mAmbientColorTemperatureHistory;
    private float mAmbientColorTemperatureOverride;
    private android.util.Spline.LinearSpline mAmbientToDisplayColorTemperatureSpline;

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.display.utils.AmbientFilter mBrightnessFilter;
    private final com.android.server.display.whitebalance.AmbientSensor.AmbientBrightnessSensor mBrightnessSensor;
    private final com.android.server.display.color.ColorDisplayService.ColorDisplayServiceInternal mColorDisplayServiceInternal;

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.display.utils.AmbientFilter mColorTemperatureFilter;
    private final com.android.server.display.whitebalance.AmbientSensor.AmbientColorTemperatureSensor mColorTemperatureSensor;
    private com.android.server.display.whitebalance.DisplayWhiteBalanceController.Callbacks mDisplayPowerControllerCallbacks;
    private boolean mEnabled;
    private android.util.Spline.LinearSpline mHighLightAmbientBrightnessToBiasSpline;
    private android.util.Spline.LinearSpline mHighLightAmbientBrightnessToBiasSplineStrong;
    private final float mHighLightAmbientColorTemperature;
    private final float mHighLightAmbientColorTemperatureStrong;
    private float mLastAmbientColorTemperature;
    private float mLatestAmbientBrightness;
    private float mLatestAmbientColorTemperature;
    private float mLatestHighLightBias;
    private float mLatestLowLightBias;
    private final boolean mLightModeAllowed;
    private boolean mLoggingEnabled;
    private android.util.Spline.LinearSpline mLowLightAmbientBrightnessToBiasSpline;
    private android.util.Spline.LinearSpline mLowLightAmbientBrightnessToBiasSplineStrong;
    private final float mLowLightAmbientColorTemperature;
    private final float mLowLightAmbientColorTemperatureStrong;

    @com.android.internal.annotations.VisibleForTesting
    float mPendingAmbientColorTemperature;
    private android.util.Spline.LinearSpline mStrongAmbientToDisplayColorTemperatureSpline;
    private boolean mStrongModeEnabled;
    private final com.android.server.display.whitebalance.DisplayWhiteBalanceThrottler mThrottler;

    public interface Callbacks {
        void updateWhiteBalance();
    }

    public DisplayWhiteBalanceController(@android.annotation.NonNull com.android.server.display.whitebalance.AmbientSensor.AmbientBrightnessSensor ambientBrightnessSensor, @android.annotation.NonNull com.android.server.display.utils.AmbientFilter ambientFilter, @android.annotation.NonNull com.android.server.display.whitebalance.AmbientSensor.AmbientColorTemperatureSensor ambientColorTemperatureSensor, @android.annotation.NonNull com.android.server.display.utils.AmbientFilter ambientFilter2, @android.annotation.NonNull com.android.server.display.whitebalance.DisplayWhiteBalanceThrottler displayWhiteBalanceThrottler, float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4, float f, float f2, float[] fArr5, float[] fArr6, float[] fArr7, float[] fArr8, float f3, float f4, float[] fArr9, float[] fArr10, float[] fArr11, float[] fArr12, boolean z) {
        validateArguments(ambientBrightnessSensor, ambientFilter, ambientColorTemperatureSensor, ambientFilter2, displayWhiteBalanceThrottler);
        this.mBrightnessSensor = ambientBrightnessSensor;
        this.mBrightnessFilter = ambientFilter;
        this.mColorTemperatureSensor = ambientColorTemperatureSensor;
        this.mColorTemperatureFilter = ambientFilter2;
        this.mThrottler = displayWhiteBalanceThrottler;
        this.mLowLightAmbientColorTemperature = f;
        this.mLowLightAmbientColorTemperatureStrong = f2;
        this.mHighLightAmbientColorTemperature = f3;
        this.mHighLightAmbientColorTemperatureStrong = f4;
        this.mAmbientColorTemperature = -1.0f;
        this.mPendingAmbientColorTemperature = -1.0f;
        this.mLastAmbientColorTemperature = -1.0f;
        this.mAmbientColorTemperatureHistory = new com.android.server.display.utils.History(50);
        this.mAmbientColorTemperatureOverride = -1.0f;
        this.mLightModeAllowed = z;
        try {
            this.mLowLightAmbientBrightnessToBiasSpline = new android.util.Spline.LinearSpline(fArr, fArr3);
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "failed to create low light ambient brightness to bias spline.", e);
            this.mLowLightAmbientBrightnessToBiasSpline = null;
        }
        if (this.mLowLightAmbientBrightnessToBiasSpline != null && (this.mLowLightAmbientBrightnessToBiasSpline.interpolate(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) != com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || this.mLowLightAmbientBrightnessToBiasSpline.interpolate(Float.POSITIVE_INFINITY) != 1.0f)) {
            android.util.Slog.d(TAG, "invalid low light ambient brightness to bias spline, bias must begin at 0.0 and end at 1.0.");
            this.mLowLightAmbientBrightnessToBiasSpline = null;
        }
        try {
            this.mLowLightAmbientBrightnessToBiasSplineStrong = new android.util.Spline.LinearSpline(fArr2, fArr4);
        } catch (java.lang.Exception e2) {
            android.util.Slog.e(TAG, "failed to create strong low light ambient brightness to bias spline.", e2);
            this.mLowLightAmbientBrightnessToBiasSplineStrong = null;
        }
        if (this.mLowLightAmbientBrightnessToBiasSplineStrong != null && (this.mLowLightAmbientBrightnessToBiasSplineStrong.interpolate(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) != com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || this.mLowLightAmbientBrightnessToBiasSplineStrong.interpolate(Float.POSITIVE_INFINITY) != 1.0f)) {
            android.util.Slog.d(TAG, "invalid strong low light ambient brightness to bias spline, bias must begin at 0.0 and end at 1.0.");
            this.mLowLightAmbientBrightnessToBiasSplineStrong = null;
        }
        try {
            this.mHighLightAmbientBrightnessToBiasSpline = new android.util.Spline.LinearSpline(fArr5, fArr7);
        } catch (java.lang.Exception e3) {
            android.util.Slog.e(TAG, "failed to create high light ambient brightness to bias spline.", e3);
            this.mHighLightAmbientBrightnessToBiasSpline = null;
        }
        if (this.mHighLightAmbientBrightnessToBiasSpline != null && (this.mHighLightAmbientBrightnessToBiasSpline.interpolate(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) != com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || this.mHighLightAmbientBrightnessToBiasSpline.interpolate(Float.POSITIVE_INFINITY) != 1.0f)) {
            android.util.Slog.d(TAG, "invalid high light ambient brightness to bias spline, bias must begin at 0.0 and end at 1.0.");
            this.mHighLightAmbientBrightnessToBiasSpline = null;
        }
        try {
            this.mHighLightAmbientBrightnessToBiasSplineStrong = new android.util.Spline.LinearSpline(fArr6, fArr8);
        } catch (java.lang.Exception e4) {
            android.util.Slog.e(TAG, "failed to create strong high light ambient brightness to bias spline.", e4);
            this.mHighLightAmbientBrightnessToBiasSplineStrong = null;
        }
        if (this.mHighLightAmbientBrightnessToBiasSplineStrong != null && (this.mHighLightAmbientBrightnessToBiasSplineStrong.interpolate(com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) != com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE || this.mHighLightAmbientBrightnessToBiasSplineStrong.interpolate(Float.POSITIVE_INFINITY) != 1.0f)) {
            android.util.Slog.d(TAG, "invalid strong high light ambient brightness to bias spline, bias must begin at 0.0 and end at 1.0.");
            this.mHighLightAmbientBrightnessToBiasSplineStrong = null;
        }
        if (this.mLowLightAmbientBrightnessToBiasSpline != null && this.mHighLightAmbientBrightnessToBiasSpline != null && fArr[fArr.length - 1] > fArr5[0]) {
            android.util.Slog.d(TAG, "invalid low light and high light ambient brightness to bias spline combination, defined domains must not intersect.");
            this.mLowLightAmbientBrightnessToBiasSpline = null;
            this.mHighLightAmbientBrightnessToBiasSpline = null;
        }
        if (this.mLowLightAmbientBrightnessToBiasSplineStrong != null && this.mHighLightAmbientBrightnessToBiasSplineStrong != null && fArr2[fArr2.length - 1] > fArr6[0]) {
            android.util.Slog.d(TAG, "invalid strong low light and high light ambient brightness to bias spline combination, defined domains must not intersect.");
            this.mLowLightAmbientBrightnessToBiasSplineStrong = null;
            this.mHighLightAmbientBrightnessToBiasSplineStrong = null;
        }
        try {
            this.mAmbientToDisplayColorTemperatureSpline = new android.util.Spline.LinearSpline(fArr9, fArr10);
        } catch (java.lang.Exception e5) {
            android.util.Slog.e(TAG, "failed to create ambient to display color temperature spline.", e5);
            this.mAmbientToDisplayColorTemperatureSpline = null;
        }
        try {
            this.mStrongAmbientToDisplayColorTemperatureSpline = new android.util.Spline.LinearSpline(fArr11, fArr12);
        } catch (java.lang.Exception e6) {
            android.util.Slog.e(TAG, "Failed to create strong ambient to display color temperature spline", e6);
        }
        this.mColorDisplayServiceInternal = (com.android.server.display.color.ColorDisplayService.ColorDisplayServiceInternal) com.android.server.LocalServices.getService(com.android.server.display.color.ColorDisplayService.ColorDisplayServiceInternal.class);
    }

    public boolean setEnabled(boolean z) {
        if (z) {
            return enable();
        }
        return disable();
    }

    public void setStrongModeEnabled(boolean z) {
        this.mStrongModeEnabled = z;
        this.mColorDisplayServiceInternal.setDisplayWhiteBalanceAllowed(this.mLightModeAllowed || this.mStrongModeEnabled);
        if (this.mEnabled) {
            updateAmbientColorTemperature();
            updateDisplayColorTemperature();
        }
    }

    public boolean setCallbacks(com.android.server.display.whitebalance.DisplayWhiteBalanceController.Callbacks callbacks) {
        if (this.mDisplayPowerControllerCallbacks == callbacks) {
            return false;
        }
        this.mDisplayPowerControllerCallbacks = callbacks;
        return true;
    }

    public boolean setLoggingEnabled(boolean z) {
        if (this.mLoggingEnabled == z) {
            return false;
        }
        this.mLoggingEnabled = z;
        this.mBrightnessSensor.setLoggingEnabled(z);
        this.mBrightnessFilter.setLoggingEnabled(z);
        this.mColorTemperatureSensor.setLoggingEnabled(z);
        this.mColorTemperatureFilter.setLoggingEnabled(z);
        this.mThrottler.setLoggingEnabled(z);
        return true;
    }

    public boolean setAmbientColorTemperatureOverride(float f) {
        if (this.mAmbientColorTemperatureOverride == f) {
            return false;
        }
        this.mAmbientColorTemperatureOverride = f;
        return true;
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println(TAG);
        printWriter.println("  mLoggingEnabled=" + this.mLoggingEnabled);
        printWriter.println("  mEnabled=" + this.mEnabled);
        printWriter.println("  mStrongModeEnabled=" + this.mStrongModeEnabled);
        printWriter.println("  mDisplayPowerControllerCallbacks=" + this.mDisplayPowerControllerCallbacks);
        this.mBrightnessSensor.dump(printWriter);
        this.mBrightnessFilter.dump(printWriter);
        this.mColorTemperatureSensor.dump(printWriter);
        this.mColorTemperatureFilter.dump(printWriter);
        this.mThrottler.dump(printWriter);
        printWriter.println("  mLowLightAmbientColorTemperature=" + this.mLowLightAmbientColorTemperature);
        printWriter.println("  mLowLightAmbientColorTemperatureStrong=" + this.mLowLightAmbientColorTemperatureStrong);
        printWriter.println("  mHighLightAmbientColorTemperature=" + this.mHighLightAmbientColorTemperature);
        printWriter.println("  mHighLightAmbientColorTemperatureStrong=" + this.mHighLightAmbientColorTemperatureStrong);
        printWriter.println("  mAmbientColorTemperature=" + this.mAmbientColorTemperature);
        printWriter.println("  mPendingAmbientColorTemperature=" + this.mPendingAmbientColorTemperature);
        printWriter.println("  mLastAmbientColorTemperature=" + this.mLastAmbientColorTemperature);
        printWriter.println("  mAmbientColorTemperatureHistory=" + this.mAmbientColorTemperatureHistory);
        printWriter.println("  mAmbientColorTemperatureOverride=" + this.mAmbientColorTemperatureOverride);
        printWriter.println("  mAmbientToDisplayColorTemperatureSpline=" + this.mAmbientToDisplayColorTemperatureSpline);
        printWriter.println("  mStrongAmbientToDisplayColorTemperatureSpline=" + this.mStrongAmbientToDisplayColorTemperatureSpline);
        printWriter.println("  mLowLightAmbientBrightnessToBiasSpline=" + this.mLowLightAmbientBrightnessToBiasSpline);
        printWriter.println("  mLowLightAmbientBrightnessToBiasSplineStrong=" + this.mLowLightAmbientBrightnessToBiasSplineStrong);
        printWriter.println("  mHighLightAmbientBrightnessToBiasSpline=" + this.mHighLightAmbientBrightnessToBiasSpline);
        printWriter.println("  mHighLightAmbientBrightnessToBiasSplineStrong=" + this.mHighLightAmbientBrightnessToBiasSplineStrong);
    }

    @Override // com.android.server.display.whitebalance.AmbientSensor.AmbientBrightnessSensor.Callbacks
    public void onAmbientBrightnessChanged(float f) {
        this.mBrightnessFilter.addValue(java.lang.System.currentTimeMillis(), f);
        updateAmbientColorTemperature();
    }

    @Override // com.android.server.display.whitebalance.AmbientSensor.AmbientColorTemperatureSensor.Callbacks
    public void onAmbientColorTemperatureChanged(float f) {
        this.mColorTemperatureFilter.addValue(java.lang.System.currentTimeMillis(), f);
        updateAmbientColorTemperature();
    }

    public void updateAmbientColorTemperature() {
        android.util.Spline.LinearSpline linearSpline;
        android.util.Spline.LinearSpline linearSpline2;
        long currentTimeMillis = java.lang.System.currentTimeMillis();
        float f = this.mStrongModeEnabled ? this.mLowLightAmbientColorTemperatureStrong : this.mLowLightAmbientColorTemperature;
        float f2 = this.mStrongModeEnabled ? this.mHighLightAmbientColorTemperatureStrong : this.mHighLightAmbientColorTemperature;
        if (this.mStrongModeEnabled) {
            linearSpline = this.mLowLightAmbientBrightnessToBiasSplineStrong;
        } else {
            linearSpline = this.mLowLightAmbientBrightnessToBiasSpline;
        }
        if (this.mStrongModeEnabled) {
            linearSpline2 = this.mHighLightAmbientBrightnessToBiasSplineStrong;
        } else {
            linearSpline2 = this.mHighLightAmbientBrightnessToBiasSpline;
        }
        float estimate = this.mColorTemperatureFilter.getEstimate(currentTimeMillis);
        this.mLatestAmbientColorTemperature = estimate;
        if (this.mStrongModeEnabled) {
            if (this.mStrongAmbientToDisplayColorTemperatureSpline != null && estimate != -1.0f) {
                estimate = this.mStrongAmbientToDisplayColorTemperatureSpline.interpolate(estimate);
            }
        } else if (this.mAmbientToDisplayColorTemperatureSpline != null && estimate != -1.0f) {
            estimate = this.mAmbientToDisplayColorTemperatureSpline.interpolate(estimate);
        }
        float estimate2 = this.mBrightnessFilter.getEstimate(currentTimeMillis);
        this.mLatestAmbientBrightness = estimate2;
        if (estimate != -1.0f && estimate2 != -1.0f && linearSpline != null) {
            float interpolate = linearSpline.interpolate(estimate2);
            estimate = (estimate * interpolate) + ((1.0f - interpolate) * f);
            this.mLatestLowLightBias = interpolate;
        }
        if (estimate != -1.0f && estimate2 != -1.0f && linearSpline2 != null) {
            float interpolate2 = linearSpline2.interpolate(estimate2);
            estimate = ((1.0f - interpolate2) * estimate) + (f2 * interpolate2);
            this.mLatestHighLightBias = interpolate2;
        }
        if (this.mAmbientColorTemperatureOverride != -1.0f) {
            if (this.mLoggingEnabled) {
                android.util.Slog.d(TAG, "override ambient color temperature: " + estimate + " => " + this.mAmbientColorTemperatureOverride);
            }
            estimate = this.mAmbientColorTemperatureOverride;
        }
        if (estimate == -1.0f || this.mThrottler.throttle(estimate)) {
            return;
        }
        if (this.mLoggingEnabled) {
            android.util.Slog.d(TAG, "pending ambient color temperature: " + estimate);
        }
        this.mPendingAmbientColorTemperature = estimate;
        if (this.mDisplayPowerControllerCallbacks != null) {
            this.mDisplayPowerControllerCallbacks.updateWhiteBalance();
        }
    }

    public void updateDisplayColorTemperature() {
        float f;
        if (this.mAmbientColorTemperature == -1.0f && this.mPendingAmbientColorTemperature == -1.0f) {
            f = this.mLastAmbientColorTemperature;
        } else {
            f = -1.0f;
        }
        if (this.mPendingAmbientColorTemperature != -1.0f && this.mPendingAmbientColorTemperature != this.mAmbientColorTemperature) {
            f = this.mPendingAmbientColorTemperature;
        }
        if (f == -1.0f) {
            return;
        }
        this.mAmbientColorTemperature = f;
        if (this.mLoggingEnabled) {
            android.util.Slog.d(TAG, "ambient color temperature: " + this.mAmbientColorTemperature);
        }
        this.mPendingAmbientColorTemperature = -1.0f;
        this.mAmbientColorTemperatureHistory.add(this.mAmbientColorTemperature);
        android.util.Slog.d(TAG, "Display cct: " + this.mAmbientColorTemperature + " Latest ambient cct: " + this.mLatestAmbientColorTemperature + " Latest ambient lux: " + this.mLatestAmbientBrightness + " Latest low light bias: " + this.mLatestLowLightBias + " Latest high light bias: " + this.mLatestHighLightBias);
        this.mColorDisplayServiceInternal.setDisplayWhiteBalanceColorTemperature((int) this.mAmbientColorTemperature);
        this.mLastAmbientColorTemperature = this.mAmbientColorTemperature;
    }

    public float calculateAdjustedBrightnessNits(float f) {
        float displayWhiteBalanceLuminance = this.mColorDisplayServiceInternal.getDisplayWhiteBalanceLuminance();
        if (displayWhiteBalanceLuminance == -1.0f) {
            return f;
        }
        return (f - (displayWhiteBalanceLuminance * f)) + f;
    }

    private void validateArguments(com.android.server.display.whitebalance.AmbientSensor.AmbientBrightnessSensor ambientBrightnessSensor, com.android.server.display.utils.AmbientFilter ambientFilter, com.android.server.display.whitebalance.AmbientSensor.AmbientColorTemperatureSensor ambientColorTemperatureSensor, com.android.server.display.utils.AmbientFilter ambientFilter2, com.android.server.display.whitebalance.DisplayWhiteBalanceThrottler displayWhiteBalanceThrottler) {
        java.util.Objects.requireNonNull(ambientBrightnessSensor, "brightnessSensor must not be null");
        java.util.Objects.requireNonNull(ambientFilter, "brightnessFilter must not be null");
        java.util.Objects.requireNonNull(ambientColorTemperatureSensor, "colorTemperatureSensor must not be null");
        java.util.Objects.requireNonNull(ambientFilter2, "colorTemperatureFilter must not be null");
        java.util.Objects.requireNonNull(displayWhiteBalanceThrottler, "throttler cannot be null");
    }

    private boolean enable() {
        if (this.mEnabled) {
            return false;
        }
        if (this.mLoggingEnabled) {
            android.util.Slog.d(TAG, "enabling");
        }
        this.mEnabled = true;
        this.mBrightnessSensor.setEnabled(true);
        this.mColorTemperatureSensor.setEnabled(true);
        return true;
    }

    private boolean disable() {
        if (!this.mEnabled) {
            return false;
        }
        if (this.mLoggingEnabled) {
            android.util.Slog.d(TAG, "disabling");
        }
        this.mEnabled = false;
        this.mBrightnessSensor.setEnabled(false);
        this.mBrightnessFilter.clear();
        this.mColorTemperatureSensor.setEnabled(false);
        this.mColorTemperatureFilter.clear();
        this.mThrottler.clear();
        this.mAmbientColorTemperature = -1.0f;
        this.mPendingAmbientColorTemperature = -1.0f;
        this.mColorDisplayServiceInternal.resetDisplayWhiteBalanceColorTemperature();
        return true;
    }
}
