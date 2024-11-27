package com.android.server.display.brightness.clamper;

/* loaded from: classes.dex */
abstract class BrightnessModifier implements com.android.server.display.brightness.clamper.BrightnessStateModifier {
    private boolean mApplied = false;

    abstract float getBrightnessAdjusted(float f, android.hardware.display.DisplayManagerInternal.DisplayPowerRequest displayPowerRequest);

    abstract int getModifier();

    abstract boolean shouldApply(android.hardware.display.DisplayManagerInternal.DisplayPowerRequest displayPowerRequest);

    BrightnessModifier() {
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessStateModifier
    public void apply(android.hardware.display.DisplayManagerInternal.DisplayPowerRequest displayPowerRequest, com.android.server.display.DisplayBrightnessState.Builder builder) {
        if (!shouldApply(displayPowerRequest)) {
            if (this.mApplied) {
                builder.setIsSlowChange(false);
                this.mApplied = false;
                return;
            }
            return;
        }
        float brightness = builder.getBrightness();
        if (brightness > com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE) {
            builder.setBrightness(getBrightnessAdjusted(brightness, displayPowerRequest));
            builder.getBrightnessReason().addModifier(getModifier());
        }
        if (!this.mApplied) {
            builder.setIsSlowChange(false);
        }
        this.mApplied = true;
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessStateModifier
    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("BrightnessModifier:");
        printWriter.println("  mApplied=" + this.mApplied);
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessStateModifier
    public void stop() {
    }
}
