package com.android.server.display.brightness.strategy;

/* loaded from: classes.dex */
public class OverrideBrightnessStrategy implements com.android.server.display.brightness.strategy.DisplayBrightnessStrategy {
    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public com.android.server.display.DisplayBrightnessState updateBrightness(android.hardware.display.DisplayManagerInternal.DisplayPowerRequest displayPowerRequest) {
        return com.android.server.display.brightness.BrightnessUtils.constructDisplayBrightnessState(6, displayPowerRequest.screenBrightnessOverride, displayPowerRequest.screenBrightnessOverride, getName());
    }

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public java.lang.String getName() {
        return "OverrideBrightnessStrategy";
    }

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public void dump(java.io.PrintWriter printWriter) {
    }
}
