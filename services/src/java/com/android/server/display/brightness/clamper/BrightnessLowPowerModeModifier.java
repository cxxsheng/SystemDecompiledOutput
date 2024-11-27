package com.android.server.display.brightness.clamper;

/* loaded from: classes.dex */
class BrightnessLowPowerModeModifier extends com.android.server.display.brightness.clamper.BrightnessModifier {
    BrightnessLowPowerModeModifier() {
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessModifier
    boolean shouldApply(android.hardware.display.DisplayManagerInternal.DisplayPowerRequest displayPowerRequest) {
        return displayPowerRequest.lowPowerMode;
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessModifier
    float getBrightnessAdjusted(float f, android.hardware.display.DisplayManagerInternal.DisplayPowerRequest displayPowerRequest) {
        return java.lang.Math.max(f * java.lang.Math.min(displayPowerRequest.screenLowPowerBrightnessFactor, 1.0f), com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessModifier
    int getModifier() {
        return 2;
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessModifier, com.android.server.display.brightness.clamper.BrightnessStateModifier
    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("BrightnessLowPowerModeModifier:");
        super.dump(new android.util.IndentingPrintWriter(printWriter, "    "));
    }
}
