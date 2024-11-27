package com.android.server.display.brightness.strategy;

/* loaded from: classes.dex */
public class BoostBrightnessStrategy implements com.android.server.display.brightness.strategy.DisplayBrightnessStrategy {
    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public com.android.server.display.DisplayBrightnessState updateBrightness(android.hardware.display.DisplayManagerInternal.DisplayPowerRequest displayPowerRequest) {
        return com.android.server.display.brightness.BrightnessUtils.constructDisplayBrightnessState(8, 1.0f, 1.0f, getName());
    }

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public java.lang.String getName() {
        return "BoostBrightnessStrategy";
    }

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public void dump(java.io.PrintWriter printWriter) {
    }
}
