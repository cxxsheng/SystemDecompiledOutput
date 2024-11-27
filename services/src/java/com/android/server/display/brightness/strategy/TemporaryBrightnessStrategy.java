package com.android.server.display.brightness.strategy;

/* loaded from: classes.dex */
public class TemporaryBrightnessStrategy implements com.android.server.display.brightness.strategy.DisplayBrightnessStrategy {
    private float mTemporaryScreenBrightness = Float.NaN;

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public com.android.server.display.DisplayBrightnessState updateBrightness(android.hardware.display.DisplayManagerInternal.DisplayPowerRequest displayPowerRequest) {
        return com.android.server.display.brightness.BrightnessUtils.constructDisplayBrightnessState(7, this.mTemporaryScreenBrightness, this.mTemporaryScreenBrightness, getName());
    }

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public java.lang.String getName() {
        return "TemporaryBrightnessStrategy";
    }

    public float getTemporaryScreenBrightness() {
        return this.mTemporaryScreenBrightness;
    }

    public void setTemporaryScreenBrightness(float f) {
        this.mTemporaryScreenBrightness = f;
    }

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("TemporaryBrightnessStrategy:");
        printWriter.println("  mTemporaryScreenBrightness:" + this.mTemporaryScreenBrightness);
    }
}
