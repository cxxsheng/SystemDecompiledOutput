package com.android.server.display.brightness.strategy;

/* loaded from: classes.dex */
public class AutomaticBrightnessStrategy {
    private boolean mAppliedTemporaryAutoBrightnessAdjustment;
    private boolean mAutoBrightnessAdjustmentChanged;
    private boolean mAutoBrightnessDisabledDueToDisplayOff;
    private boolean mAutoBrightnessOneShotEnabled;

    @android.annotation.Nullable
    private com.android.server.display.AutomaticBrightnessController mAutomaticBrightnessController;

    @android.annotation.Nullable
    private android.hardware.display.BrightnessConfiguration mBrightnessConfiguration;
    private final android.content.Context mContext;
    private final int mDisplayId;
    private int mAutoBrightnessAdjustmentReasonsFlags = 0;
    private boolean mShouldResetShortTermModel = false;
    private boolean mAppliedAutoBrightness = false;
    private boolean mUseAutoBrightness = false;
    private boolean mIsAutoBrightnessEnabled = false;
    private boolean mIsShortTermModelActive = false;
    private float mAutoBrightnessAdjustment = getAutoBrightnessAdjustmentSetting();
    private float mPendingAutoBrightnessAdjustment = Float.NaN;
    private float mTemporaryAutoBrightnessAdjustment = Float.NaN;

    public AutomaticBrightnessStrategy(android.content.Context context, int i) {
        this.mContext = context;
        this.mDisplayId = i;
    }

    public void setAutoBrightnessState(int i, boolean z, int i2, int i3, float f, boolean z2) {
        int i4;
        boolean z3 = false;
        boolean z4 = z && i3 == 1;
        this.mIsAutoBrightnessEnabled = shouldUseAutoBrightness() && !((i != 2 && !z4) || i2 == 6 || i2 == 11 || this.mAutomaticBrightnessController == null);
        if (shouldUseAutoBrightness() && i != 2 && !z4) {
            z3 = true;
        }
        this.mAutoBrightnessDisabledDueToDisplayOff = z3;
        if (this.mIsAutoBrightnessEnabled && i2 != 10) {
            i4 = 1;
        } else if (this.mAutoBrightnessDisabledDueToDisplayOff) {
            i4 = 3;
        } else {
            i4 = 2;
        }
        accommodateUserBrightnessChanges(z2, f, i3, this.mBrightnessConfiguration, i4);
    }

    public boolean isAutoBrightnessEnabled() {
        return this.mIsAutoBrightnessEnabled;
    }

    public boolean isAutoBrightnessDisabledDueToDisplayOff() {
        return this.mAutoBrightnessDisabledDueToDisplayOff;
    }

    public void setAutoBrightnessOneShotEnabled(boolean z) {
        this.mAutoBrightnessOneShotEnabled = z;
    }

    public void setBrightnessConfiguration(android.hardware.display.BrightnessConfiguration brightnessConfiguration, boolean z) {
        this.mBrightnessConfiguration = brightnessConfiguration;
        setShouldResetShortTermModel(z);
    }

    public boolean processPendingAutoBrightnessAdjustments() {
        this.mAutoBrightnessAdjustmentChanged = false;
        if (java.lang.Float.isNaN(this.mPendingAutoBrightnessAdjustment)) {
            return false;
        }
        if (this.mAutoBrightnessAdjustment == this.mPendingAutoBrightnessAdjustment) {
            this.mPendingAutoBrightnessAdjustment = Float.NaN;
            return false;
        }
        this.mAutoBrightnessAdjustment = this.mPendingAutoBrightnessAdjustment;
        this.mPendingAutoBrightnessAdjustment = Float.NaN;
        this.mTemporaryAutoBrightnessAdjustment = Float.NaN;
        this.mAutoBrightnessAdjustmentChanged = true;
        return true;
    }

    public void setAutomaticBrightnessController(com.android.server.display.AutomaticBrightnessController automaticBrightnessController) {
        if (automaticBrightnessController == this.mAutomaticBrightnessController) {
            return;
        }
        if (this.mAutomaticBrightnessController != null) {
            this.mAutomaticBrightnessController.stop();
        }
        this.mAutomaticBrightnessController = automaticBrightnessController;
    }

    public boolean shouldUseAutoBrightness() {
        return this.mUseAutoBrightness;
    }

    public void setUseAutoBrightness(boolean z) {
        this.mUseAutoBrightness = z;
    }

    public boolean isShortTermModelActive() {
        return this.mIsShortTermModelActive;
    }

    public void updatePendingAutoBrightnessAdjustments() {
        float floatForUser = android.provider.Settings.System.getFloatForUser(this.mContext.getContentResolver(), "screen_auto_brightness_adj", com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, -2);
        this.mPendingAutoBrightnessAdjustment = java.lang.Float.isNaN(floatForUser) ? Float.NaN : com.android.server.display.brightness.BrightnessUtils.clampBrightnessAdjustment(floatForUser);
    }

    public void setTemporaryAutoBrightnessAdjustment(float f) {
        this.mTemporaryAutoBrightnessAdjustment = f;
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("AutomaticBrightnessStrategy:");
        printWriter.println("  mDisplayId=" + this.mDisplayId);
        printWriter.println("  mAutoBrightnessAdjustment=" + this.mAutoBrightnessAdjustment);
        printWriter.println("  mPendingAutoBrightnessAdjustment=" + this.mPendingAutoBrightnessAdjustment);
        printWriter.println("  mTemporaryAutoBrightnessAdjustment=" + this.mTemporaryAutoBrightnessAdjustment);
        printWriter.println("  mShouldResetShortTermModel=" + this.mShouldResetShortTermModel);
        printWriter.println("  mAppliedAutoBrightness=" + this.mAppliedAutoBrightness);
        printWriter.println("  mAutoBrightnessAdjustmentChanged=" + this.mAutoBrightnessAdjustmentChanged);
        printWriter.println("  mAppliedTemporaryAutoBrightnessAdjustment=" + this.mAppliedTemporaryAutoBrightnessAdjustment);
        printWriter.println("  mUseAutoBrightness=" + this.mUseAutoBrightness);
        printWriter.println("  mWasShortTermModelActive=" + this.mIsShortTermModelActive);
        printWriter.println("  mAutoBrightnessAdjustmentReasonsFlags=" + this.mAutoBrightnessAdjustmentReasonsFlags);
    }

    public boolean getAutoBrightnessAdjustmentChanged() {
        return this.mAutoBrightnessAdjustmentChanged;
    }

    public boolean isTemporaryAutoBrightnessAdjustmentApplied() {
        return this.mAppliedTemporaryAutoBrightnessAdjustment;
    }

    public float getAutomaticScreenBrightness(com.android.server.display.brightness.BrightnessEvent brightnessEvent) {
        float f;
        if (this.mAutomaticBrightnessController != null) {
            f = this.mAutomaticBrightnessController.getAutomaticScreenBrightness(brightnessEvent);
        } else {
            f = Float.NaN;
        }
        adjustAutomaticBrightnessStateIfValid(f);
        return f;
    }

    public float getAutomaticScreenBrightnessBasedOnLastObservedLux(com.android.server.display.brightness.BrightnessEvent brightnessEvent) {
        float f;
        if (this.mAutomaticBrightnessController != null) {
            f = this.mAutomaticBrightnessController.getAutomaticScreenBrightnessBasedOnLastObservedLux(brightnessEvent);
        } else {
            f = Float.NaN;
        }
        adjustAutomaticBrightnessStateIfValid(f);
        return f;
    }

    public int getAutoBrightnessAdjustmentReasonsFlags() {
        return this.mAutoBrightnessAdjustmentReasonsFlags;
    }

    public boolean hasAppliedAutoBrightness() {
        return this.mAppliedAutoBrightness;
    }

    @com.android.internal.annotations.VisibleForTesting
    void adjustAutomaticBrightnessStateIfValid(float f) {
        int i;
        float f2;
        if (isTemporaryAutoBrightnessAdjustmentApplied()) {
            i = 1;
        } else {
            i = 2;
        }
        this.mAutoBrightnessAdjustmentReasonsFlags = i;
        if (this.mAutomaticBrightnessController != null) {
            f2 = this.mAutomaticBrightnessController.getAutomaticScreenBrightnessAdjustment();
        } else {
            f2 = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        }
        if (!java.lang.Float.isNaN(f2) && this.mAutoBrightnessAdjustment != f2) {
            putAutoBrightnessAdjustmentSetting(f2);
        } else {
            this.mAutoBrightnessAdjustmentReasonsFlags = 0;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    void setShouldResetShortTermModel(boolean z) {
        this.mShouldResetShortTermModel = z;
    }

    @com.android.internal.annotations.VisibleForTesting
    boolean shouldResetShortTermModel() {
        return this.mShouldResetShortTermModel;
    }

    @com.android.internal.annotations.VisibleForTesting
    float getAutoBrightnessAdjustment() {
        return this.mAutoBrightnessAdjustment;
    }

    @com.android.internal.annotations.VisibleForTesting
    float getPendingAutoBrightnessAdjustment() {
        return this.mPendingAutoBrightnessAdjustment;
    }

    @com.android.internal.annotations.VisibleForTesting
    float getTemporaryAutoBrightnessAdjustment() {
        return this.mTemporaryAutoBrightnessAdjustment;
    }

    @com.android.internal.annotations.VisibleForTesting
    void putAutoBrightnessAdjustmentSetting(float f) {
        if (this.mDisplayId == 0) {
            this.mAutoBrightnessAdjustment = f;
            android.provider.Settings.System.putFloatForUser(this.mContext.getContentResolver(), "screen_auto_brightness_adj", f, -2);
        }
    }

    public void setAutoBrightnessApplied(boolean z) {
        this.mAppliedAutoBrightness = z;
    }

    @com.android.internal.annotations.VisibleForTesting
    void accommodateUserBrightnessChanges(boolean z, float f, int i, android.hardware.display.BrightnessConfiguration brightnessConfiguration, int i2) {
        processPendingAutoBrightnessAdjustments();
        float updateTemporaryAutoBrightnessAdjustments = updateTemporaryAutoBrightnessAdjustments();
        this.mIsShortTermModelActive = false;
        if (this.mAutomaticBrightnessController != null) {
            this.mAutomaticBrightnessController.configure(i2, brightnessConfiguration, f, z, updateTemporaryAutoBrightnessAdjustments, this.mAutoBrightnessAdjustmentChanged, i, this.mShouldResetShortTermModel, this.mAutoBrightnessOneShotEnabled);
            this.mShouldResetShortTermModel = false;
            this.mIsShortTermModelActive = this.mAutomaticBrightnessController.hasUserDataPoints();
        }
    }

    private float updateTemporaryAutoBrightnessAdjustments() {
        this.mAppliedTemporaryAutoBrightnessAdjustment = !java.lang.Float.isNaN(this.mTemporaryAutoBrightnessAdjustment);
        return this.mAppliedTemporaryAutoBrightnessAdjustment ? this.mTemporaryAutoBrightnessAdjustment : this.mAutoBrightnessAdjustment;
    }

    private float getAutoBrightnessAdjustmentSetting() {
        float floatForUser = android.provider.Settings.System.getFloatForUser(this.mContext.getContentResolver(), "screen_auto_brightness_adj", com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE, -2);
        return java.lang.Float.isNaN(floatForUser) ? com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE : com.android.server.display.brightness.BrightnessUtils.clampBrightnessAdjustment(floatForUser);
    }
}
