package com.android.server.display;

/* loaded from: classes.dex */
public final class DisplayBrightnessState {
    public static final float CUSTOM_ANIMATION_RATE_NOT_SET = -1.0f;
    private final float mBrightness;
    private final com.android.server.display.brightness.BrightnessReason mBrightnessReason;
    private final float mCustomAnimationRate;
    private final java.lang.String mDisplayBrightnessStrategyName;
    private final boolean mIsSlowChange;
    private final float mMaxBrightness;
    private final float mMinBrightness;
    private final float mSdrBrightness;
    private final boolean mShouldUpdateScreenBrightnessSetting;
    private final boolean mShouldUseAutoBrightness;

    private DisplayBrightnessState(com.android.server.display.DisplayBrightnessState.Builder builder) {
        this.mBrightness = builder.getBrightness();
        this.mSdrBrightness = builder.getSdrBrightness();
        this.mBrightnessReason = builder.getBrightnessReason();
        this.mDisplayBrightnessStrategyName = builder.getDisplayBrightnessStrategyName();
        this.mShouldUseAutoBrightness = builder.getShouldUseAutoBrightness();
        this.mIsSlowChange = builder.isSlowChange();
        this.mMaxBrightness = builder.getMaxBrightness();
        this.mMinBrightness = builder.getMinBrightness();
        this.mCustomAnimationRate = builder.getCustomAnimationRate();
        this.mShouldUpdateScreenBrightnessSetting = builder.shouldUpdateScreenBrightnessSetting();
    }

    public float getBrightness() {
        return this.mBrightness;
    }

    public float getSdrBrightness() {
        return this.mSdrBrightness;
    }

    public com.android.server.display.brightness.BrightnessReason getBrightnessReason() {
        return this.mBrightnessReason;
    }

    public java.lang.String getDisplayBrightnessStrategyName() {
        return this.mDisplayBrightnessStrategyName;
    }

    public boolean getShouldUseAutoBrightness() {
        return this.mShouldUseAutoBrightness;
    }

    public boolean isSlowChange() {
        return this.mIsSlowChange;
    }

    public float getMaxBrightness() {
        return this.mMaxBrightness;
    }

    public float getMinBrightness() {
        return this.mMinBrightness;
    }

    public float getCustomAnimationRate() {
        return this.mCustomAnimationRate;
    }

    public boolean shouldUpdateScreenBrightnessSetting() {
        return this.mShouldUpdateScreenBrightnessSetting;
    }

    public java.lang.String toString() {
        return "DisplayBrightnessState:\n    brightness:" + getBrightness() + "\n    sdrBrightness:" + getSdrBrightness() + "\n    brightnessReason:" + getBrightnessReason() + "\n    shouldUseAutoBrightness:" + getShouldUseAutoBrightness() + "\n    isSlowChange:" + this.mIsSlowChange + "\n    maxBrightness:" + this.mMaxBrightness + "\n    minBrightness:" + this.mMinBrightness + "\n    customAnimationRate:" + this.mCustomAnimationRate + "\n    shouldUpdateScreenBrightnessSetting:" + this.mShouldUpdateScreenBrightnessSetting;
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof com.android.server.display.DisplayBrightnessState)) {
            return false;
        }
        com.android.server.display.DisplayBrightnessState displayBrightnessState = (com.android.server.display.DisplayBrightnessState) obj;
        return this.mBrightness == displayBrightnessState.getBrightness() && this.mSdrBrightness == displayBrightnessState.getSdrBrightness() && this.mBrightnessReason.equals(displayBrightnessState.getBrightnessReason()) && android.text.TextUtils.equals(this.mDisplayBrightnessStrategyName, displayBrightnessState.getDisplayBrightnessStrategyName()) && this.mShouldUseAutoBrightness == displayBrightnessState.getShouldUseAutoBrightness() && this.mIsSlowChange == displayBrightnessState.isSlowChange() && this.mMaxBrightness == displayBrightnessState.getMaxBrightness() && this.mMinBrightness == displayBrightnessState.getMinBrightness() && this.mCustomAnimationRate == displayBrightnessState.getCustomAnimationRate() && this.mShouldUpdateScreenBrightnessSetting == displayBrightnessState.shouldUpdateScreenBrightnessSetting();
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Float.valueOf(this.mBrightness), java.lang.Float.valueOf(this.mSdrBrightness), this.mBrightnessReason, java.lang.Boolean.valueOf(this.mShouldUseAutoBrightness), java.lang.Boolean.valueOf(this.mIsSlowChange), java.lang.Float.valueOf(this.mMaxBrightness), java.lang.Float.valueOf(this.mMinBrightness), java.lang.Float.valueOf(this.mCustomAnimationRate), java.lang.Boolean.valueOf(this.mShouldUpdateScreenBrightnessSetting));
    }

    public static com.android.server.display.DisplayBrightnessState.Builder builder() {
        return new com.android.server.display.DisplayBrightnessState.Builder();
    }

    public static class Builder {
        private float mBrightness;
        private com.android.server.display.brightness.BrightnessReason mBrightnessReason = new com.android.server.display.brightness.BrightnessReason();
        private float mCustomAnimationRate = -1.0f;
        private java.lang.String mDisplayBrightnessStrategyName;
        private boolean mIsSlowChange;
        private float mMaxBrightness;
        private float mMinBrightness;
        private float mSdrBrightness;
        private boolean mShouldUpdateScreenBrightnessSetting;
        private boolean mShouldUseAutoBrightness;

        public static com.android.server.display.DisplayBrightnessState.Builder from(com.android.server.display.DisplayBrightnessState displayBrightnessState) {
            com.android.server.display.DisplayBrightnessState.Builder builder = new com.android.server.display.DisplayBrightnessState.Builder();
            builder.setBrightness(displayBrightnessState.getBrightness());
            builder.setSdrBrightness(displayBrightnessState.getSdrBrightness());
            builder.setBrightnessReason(displayBrightnessState.getBrightnessReason());
            builder.setDisplayBrightnessStrategyName(displayBrightnessState.getDisplayBrightnessStrategyName());
            builder.setShouldUseAutoBrightness(displayBrightnessState.getShouldUseAutoBrightness());
            builder.setIsSlowChange(displayBrightnessState.isSlowChange());
            builder.setMaxBrightness(displayBrightnessState.getMaxBrightness());
            builder.setMinBrightness(displayBrightnessState.getMinBrightness());
            builder.setCustomAnimationRate(displayBrightnessState.getCustomAnimationRate());
            builder.setShouldUpdateScreenBrightnessSetting(displayBrightnessState.shouldUpdateScreenBrightnessSetting());
            return builder;
        }

        public float getBrightness() {
            return this.mBrightness;
        }

        public com.android.server.display.DisplayBrightnessState.Builder setBrightness(float f) {
            this.mBrightness = f;
            return this;
        }

        public float getSdrBrightness() {
            return this.mSdrBrightness;
        }

        public com.android.server.display.DisplayBrightnessState.Builder setSdrBrightness(float f) {
            this.mSdrBrightness = f;
            return this;
        }

        public com.android.server.display.brightness.BrightnessReason getBrightnessReason() {
            return this.mBrightnessReason;
        }

        public com.android.server.display.DisplayBrightnessState.Builder setBrightnessReason(com.android.server.display.brightness.BrightnessReason brightnessReason) {
            this.mBrightnessReason = brightnessReason;
            return this;
        }

        public java.lang.String getDisplayBrightnessStrategyName() {
            return this.mDisplayBrightnessStrategyName;
        }

        public com.android.server.display.DisplayBrightnessState.Builder setDisplayBrightnessStrategyName(java.lang.String str) {
            this.mDisplayBrightnessStrategyName = str;
            return this;
        }

        public com.android.server.display.DisplayBrightnessState.Builder setShouldUseAutoBrightness(boolean z) {
            this.mShouldUseAutoBrightness = z;
            return this;
        }

        public boolean getShouldUseAutoBrightness() {
            return this.mShouldUseAutoBrightness;
        }

        public com.android.server.display.DisplayBrightnessState.Builder setIsSlowChange(boolean z) {
            this.mIsSlowChange = z;
            return this;
        }

        public boolean isSlowChange() {
            return this.mIsSlowChange;
        }

        public com.android.server.display.DisplayBrightnessState.Builder setMaxBrightness(float f) {
            this.mMaxBrightness = f;
            return this;
        }

        public float getMaxBrightness() {
            return this.mMaxBrightness;
        }

        public com.android.server.display.DisplayBrightnessState.Builder setMinBrightness(float f) {
            this.mMinBrightness = f;
            return this;
        }

        public float getMinBrightness() {
            return this.mMinBrightness;
        }

        public com.android.server.display.DisplayBrightnessState.Builder setCustomAnimationRate(float f) {
            this.mCustomAnimationRate = f;
            return this;
        }

        public float getCustomAnimationRate() {
            return this.mCustomAnimationRate;
        }

        public boolean shouldUpdateScreenBrightnessSetting() {
            return this.mShouldUpdateScreenBrightnessSetting;
        }

        public com.android.server.display.DisplayBrightnessState.Builder setShouldUpdateScreenBrightnessSetting(boolean z) {
            this.mShouldUpdateScreenBrightnessSetting = z;
            return this;
        }

        public com.android.server.display.DisplayBrightnessState build() {
            return new com.android.server.display.DisplayBrightnessState(this);
        }
    }
}
