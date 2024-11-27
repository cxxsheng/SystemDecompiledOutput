package com.android.server.display.brightness.strategy;

/* loaded from: classes.dex */
public class OffloadBrightnessStrategy implements com.android.server.display.brightness.strategy.DisplayBrightnessStrategy {
    private float mOffloadScreenBrightness = Float.NaN;

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public com.android.server.display.DisplayBrightnessState updateBrightness(android.hardware.display.DisplayManagerInternal.DisplayPowerRequest displayPowerRequest) {
        com.android.server.display.brightness.BrightnessReason brightnessReason = new com.android.server.display.brightness.BrightnessReason();
        brightnessReason.setReason(11);
        return new com.android.server.display.DisplayBrightnessState.Builder().setBrightness(this.mOffloadScreenBrightness).setSdrBrightness(this.mOffloadScreenBrightness).setBrightnessReason(brightnessReason).setDisplayBrightnessStrategyName(getName()).setIsSlowChange(false).setShouldUpdateScreenBrightnessSetting(true).build();
    }

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public java.lang.String getName() {
        return "OffloadBrightnessStrategy";
    }

    public float getOffloadScreenBrightness() {
        return this.mOffloadScreenBrightness;
    }

    public void setOffloadScreenBrightness(float f) {
        this.mOffloadScreenBrightness = f;
    }

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("OffloadBrightnessStrategy:");
        printWriter.println("  mOffloadScreenBrightness:" + this.mOffloadScreenBrightness);
    }
}
