package com.android.server.display.brightness;

/* loaded from: classes.dex */
public final class BrightnessEvent {
    public static final int FLAG_DOZE_SCALE = 4;
    public static final int FLAG_INVALID_LUX = 2;
    public static final int FLAG_LOW_POWER_MODE = 32;
    public static final int FLAG_RBC = 1;
    public static final int FLAG_USER_SET = 8;
    private static final java.text.SimpleDateFormat FORMAT = new java.text.SimpleDateFormat("MM-dd HH:mm:ss.SSS");
    private int mAdjustmentFlags;
    private int mAutoBrightnessMode;
    private boolean mAutomaticBrightnessEnabled;
    private float mBrightness;
    private java.lang.String mDisplayBrightnessStrategyName;
    private int mDisplayId;
    private int mDisplayPolicy;
    private int mDisplayState;
    private int mFlags;
    private float mHbmMax;
    private int mHbmMode;
    private float mInitialBrightness;
    private float mLux;
    private java.lang.String mPhysicalDisplayId;
    private float mPowerFactor;
    private float mPreThresholdBrightness;
    private float mPreThresholdLux;
    private int mRbcStrength;
    private com.android.server.display.brightness.BrightnessReason mReason = new com.android.server.display.brightness.BrightnessReason();
    private float mRecommendedBrightness;
    private float mThermalMax;
    private long mTime;
    private boolean mWasShortTermModelActive;

    public BrightnessEvent(com.android.server.display.brightness.BrightnessEvent brightnessEvent) {
        copyFrom(brightnessEvent);
    }

    public BrightnessEvent(int i) {
        this.mDisplayId = i;
        reset();
    }

    public void copyFrom(com.android.server.display.brightness.BrightnessEvent brightnessEvent) {
        this.mReason.set(brightnessEvent.getReason());
        this.mDisplayId = brightnessEvent.getDisplayId();
        this.mPhysicalDisplayId = brightnessEvent.getPhysicalDisplayId();
        this.mDisplayState = brightnessEvent.mDisplayState;
        this.mDisplayPolicy = brightnessEvent.mDisplayPolicy;
        this.mTime = brightnessEvent.getTime();
        this.mLux = brightnessEvent.getLux();
        this.mPreThresholdLux = brightnessEvent.getPreThresholdLux();
        this.mInitialBrightness = brightnessEvent.getInitialBrightness();
        this.mBrightness = brightnessEvent.getBrightness();
        this.mRecommendedBrightness = brightnessEvent.getRecommendedBrightness();
        this.mPreThresholdBrightness = brightnessEvent.getPreThresholdBrightness();
        this.mHbmMode = brightnessEvent.getHbmMode();
        this.mHbmMax = brightnessEvent.getHbmMax();
        this.mRbcStrength = brightnessEvent.getRbcStrength();
        this.mThermalMax = brightnessEvent.getThermalMax();
        this.mPowerFactor = brightnessEvent.getPowerFactor();
        this.mWasShortTermModelActive = brightnessEvent.wasShortTermModelActive();
        this.mFlags = brightnessEvent.getFlags();
        this.mAdjustmentFlags = brightnessEvent.getAdjustmentFlags();
        this.mAutomaticBrightnessEnabled = brightnessEvent.isAutomaticBrightnessEnabled();
        this.mDisplayBrightnessStrategyName = brightnessEvent.getDisplayBrightnessStrategyName();
        this.mAutoBrightnessMode = brightnessEvent.mAutoBrightnessMode;
    }

    public void reset() {
        this.mReason = new com.android.server.display.brightness.BrightnessReason();
        this.mTime = android.os.SystemClock.uptimeMillis();
        this.mPhysicalDisplayId = "";
        this.mDisplayState = 0;
        this.mDisplayPolicy = 0;
        this.mLux = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        this.mPreThresholdLux = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        this.mInitialBrightness = Float.NaN;
        this.mBrightness = Float.NaN;
        this.mRecommendedBrightness = Float.NaN;
        this.mPreThresholdBrightness = Float.NaN;
        this.mHbmMode = 0;
        this.mHbmMax = 1.0f;
        this.mRbcStrength = 0;
        this.mThermalMax = 1.0f;
        this.mPowerFactor = 1.0f;
        this.mWasShortTermModelActive = false;
        this.mFlags = 0;
        this.mAdjustmentFlags = 0;
        this.mAutomaticBrightnessEnabled = true;
        this.mDisplayBrightnessStrategyName = "";
        this.mAutoBrightnessMode = 0;
    }

    public boolean equalsMainData(com.android.server.display.brightness.BrightnessEvent brightnessEvent) {
        return this.mReason.equals(brightnessEvent.mReason) && this.mDisplayId == brightnessEvent.mDisplayId && this.mPhysicalDisplayId.equals(brightnessEvent.mPhysicalDisplayId) && this.mDisplayState == brightnessEvent.mDisplayState && this.mDisplayPolicy == brightnessEvent.mDisplayPolicy && java.lang.Float.floatToRawIntBits(this.mLux) == java.lang.Float.floatToRawIntBits(brightnessEvent.mLux) && java.lang.Float.floatToRawIntBits(this.mPreThresholdLux) == java.lang.Float.floatToRawIntBits(brightnessEvent.mPreThresholdLux) && java.lang.Float.floatToRawIntBits(this.mBrightness) == java.lang.Float.floatToRawIntBits(brightnessEvent.mBrightness) && java.lang.Float.floatToRawIntBits(this.mRecommendedBrightness) == java.lang.Float.floatToRawIntBits(brightnessEvent.mRecommendedBrightness) && java.lang.Float.floatToRawIntBits(this.mPreThresholdBrightness) == java.lang.Float.floatToRawIntBits(brightnessEvent.mPreThresholdBrightness) && this.mHbmMode == brightnessEvent.mHbmMode && java.lang.Float.floatToRawIntBits(this.mHbmMax) == java.lang.Float.floatToRawIntBits(brightnessEvent.mHbmMax) && this.mRbcStrength == brightnessEvent.mRbcStrength && java.lang.Float.floatToRawIntBits(this.mThermalMax) == java.lang.Float.floatToRawIntBits(brightnessEvent.mThermalMax) && java.lang.Float.floatToRawIntBits(this.mPowerFactor) == java.lang.Float.floatToRawIntBits(brightnessEvent.mPowerFactor) && this.mWasShortTermModelActive == brightnessEvent.mWasShortTermModelActive && this.mFlags == brightnessEvent.mFlags && this.mAdjustmentFlags == brightnessEvent.mAdjustmentFlags && this.mAutomaticBrightnessEnabled == brightnessEvent.mAutomaticBrightnessEnabled && this.mDisplayBrightnessStrategyName.equals(brightnessEvent.mDisplayBrightnessStrategyName) && this.mAutoBrightnessMode == brightnessEvent.mAutoBrightnessMode;
    }

    public java.lang.String toString(boolean z) {
        java.lang.String str;
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (z) {
            str = FORMAT.format(new java.util.Date(this.mTime)) + " - ";
        } else {
            str = "";
        }
        sb.append(str);
        sb.append("BrightnessEvent: disp=");
        sb.append(this.mDisplayId);
        sb.append(", physDisp=");
        sb.append(this.mPhysicalDisplayId);
        sb.append(", displayState=");
        sb.append(android.view.Display.stateToString(this.mDisplayState));
        sb.append(", displayPolicy=");
        sb.append(android.hardware.display.DisplayManagerInternal.DisplayPowerRequest.policyToString(this.mDisplayPolicy));
        sb.append(", brt=");
        sb.append(this.mBrightness);
        sb.append((this.mFlags & 8) != 0 ? "(user_set)" : "");
        sb.append(", initBrt=");
        sb.append(this.mInitialBrightness);
        sb.append(", rcmdBrt=");
        sb.append(this.mRecommendedBrightness);
        sb.append(", preBrt=");
        sb.append(this.mPreThresholdBrightness);
        sb.append(", lux=");
        sb.append(this.mLux);
        sb.append(", preLux=");
        sb.append(this.mPreThresholdLux);
        sb.append(", hbmMax=");
        sb.append(this.mHbmMax);
        sb.append(", hbmMode=");
        sb.append(android.hardware.display.BrightnessInfo.hbmToString(this.mHbmMode));
        sb.append(", rbcStrength=");
        sb.append(this.mRbcStrength);
        sb.append(", thrmMax=");
        sb.append(this.mThermalMax);
        sb.append(", powerFactor=");
        sb.append(this.mPowerFactor);
        sb.append(", wasShortTermModelActive=");
        sb.append(this.mWasShortTermModelActive);
        sb.append(", flags=");
        sb.append(flagsToString());
        sb.append(", reason=");
        sb.append(this.mReason.toString(this.mAdjustmentFlags));
        sb.append(", autoBrightness=");
        sb.append(this.mAutomaticBrightnessEnabled);
        sb.append(", strategy=");
        sb.append(this.mDisplayBrightnessStrategyName);
        sb.append(", autoBrightnessMode=");
        sb.append(com.android.server.display.config.DisplayBrightnessMappingConfig.autoBrightnessModeToString(this.mAutoBrightnessMode));
        return sb.toString();
    }

    public java.lang.String toString() {
        return toString(true);
    }

    public com.android.server.display.brightness.BrightnessReason getReason() {
        return this.mReason;
    }

    public void setReason(com.android.server.display.brightness.BrightnessReason brightnessReason) {
        this.mReason = brightnessReason;
    }

    public long getTime() {
        return this.mTime;
    }

    public void setTime(long j) {
        this.mTime = j;
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    public void setDisplayId(int i) {
        this.mDisplayId = i;
    }

    public java.lang.String getPhysicalDisplayId() {
        return this.mPhysicalDisplayId;
    }

    public void setPhysicalDisplayId(java.lang.String str) {
        this.mPhysicalDisplayId = str;
    }

    public void setDisplayState(int i) {
        this.mDisplayState = i;
    }

    public void setDisplayPolicy(int i) {
        this.mDisplayPolicy = i;
    }

    public float getLux() {
        return this.mLux;
    }

    public void setLux(float f) {
        this.mLux = f;
    }

    public float getPreThresholdLux() {
        return this.mPreThresholdLux;
    }

    public void setPreThresholdLux(float f) {
        this.mPreThresholdLux = f;
    }

    public float getInitialBrightness() {
        return this.mInitialBrightness;
    }

    public void setInitialBrightness(float f) {
        this.mInitialBrightness = f;
    }

    public float getBrightness() {
        return this.mBrightness;
    }

    public void setBrightness(float f) {
        this.mBrightness = f;
    }

    public float getRecommendedBrightness() {
        return this.mRecommendedBrightness;
    }

    public void setRecommendedBrightness(float f) {
        this.mRecommendedBrightness = f;
    }

    public float getPreThresholdBrightness() {
        return this.mPreThresholdBrightness;
    }

    public void setPreThresholdBrightness(float f) {
        this.mPreThresholdBrightness = f;
    }

    public int getHbmMode() {
        return this.mHbmMode;
    }

    public void setHbmMode(int i) {
        this.mHbmMode = i;
    }

    public float getHbmMax() {
        return this.mHbmMax;
    }

    public void setHbmMax(float f) {
        this.mHbmMax = f;
    }

    public int getRbcStrength() {
        return this.mRbcStrength;
    }

    public void setRbcStrength(int i) {
        this.mRbcStrength = i;
    }

    public boolean isRbcEnabled() {
        return (this.mFlags & 1) != 0;
    }

    public float getThermalMax() {
        return this.mThermalMax;
    }

    public void setThermalMax(float f) {
        this.mThermalMax = f;
    }

    public float getPowerFactor() {
        return this.mPowerFactor;
    }

    public void setPowerFactor(float f) {
        this.mPowerFactor = f;
    }

    public boolean isLowPowerModeSet() {
        return (this.mFlags & 32) != 0;
    }

    public boolean setWasShortTermModelActive(boolean z) {
        this.mWasShortTermModelActive = z;
        return z;
    }

    public boolean wasShortTermModelActive() {
        return this.mWasShortTermModelActive;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public void setFlags(int i) {
        this.mFlags = i;
    }

    public int getAdjustmentFlags() {
        return this.mAdjustmentFlags;
    }

    public void setAdjustmentFlags(int i) {
        this.mAdjustmentFlags = i;
    }

    public boolean isAutomaticBrightnessEnabled() {
        return this.mAutomaticBrightnessEnabled;
    }

    public void setDisplayBrightnessStrategyName(java.lang.String str) {
        this.mDisplayBrightnessStrategyName = str;
    }

    public java.lang.String getDisplayBrightnessStrategyName() {
        return this.mDisplayBrightnessStrategyName;
    }

    public void setAutomaticBrightnessEnabled(boolean z) {
        this.mAutomaticBrightnessEnabled = z;
    }

    public int getAutoBrightnessMode() {
        return this.mAutoBrightnessMode;
    }

    public void setAutoBrightnessMode(int i) {
        this.mAutoBrightnessMode = i;
    }

    @com.android.internal.annotations.VisibleForTesting
    public java.lang.String flagsToString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append((this.mFlags & 8) != 0 ? "user_set " : "");
        sb.append((this.mFlags & 1) != 0 ? "rbc " : "");
        sb.append((this.mFlags & 2) != 0 ? "invalid_lux " : "");
        sb.append((this.mFlags & 4) != 0 ? "doze_scale " : "");
        sb.append((this.mFlags & 32) != 0 ? "low_power_mode " : "");
        return sb.toString();
    }
}
