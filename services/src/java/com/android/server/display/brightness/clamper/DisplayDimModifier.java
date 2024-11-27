package com.android.server.display.brightness.clamper;

/* loaded from: classes.dex */
class DisplayDimModifier extends com.android.server.display.brightness.clamper.BrightnessModifier {
    private final float mScreenBrightnessDimConfig;
    private final float mScreenBrightnessMinimumDimAmount;

    DisplayDimModifier(android.content.Context context) {
        android.os.PowerManager powerManager = (android.os.PowerManager) context.getSystemService(android.os.PowerManager.class);
        java.util.Objects.requireNonNull(powerManager);
        android.content.res.Resources resources = context.getResources();
        this.mScreenBrightnessDimConfig = com.android.server.display.brightness.BrightnessUtils.clampAbsoluteBrightness(powerManager.getBrightnessConstraint(3));
        this.mScreenBrightnessMinimumDimAmount = resources.getFloat(android.R.dimen.config_pictureInPictureMaxAspectRatio);
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessModifier
    boolean shouldApply(android.hardware.display.DisplayManagerInternal.DisplayPowerRequest displayPowerRequest) {
        return displayPowerRequest.policy == 2;
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessModifier
    float getBrightnessAdjusted(float f, android.hardware.display.DisplayManagerInternal.DisplayPowerRequest displayPowerRequest) {
        return java.lang.Math.max(java.lang.Math.min(f - this.mScreenBrightnessMinimumDimAmount, this.mScreenBrightnessDimConfig), com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessModifier
    int getModifier() {
        return 1;
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessModifier, com.android.server.display.brightness.clamper.BrightnessStateModifier
    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("DisplayDimModifier:");
        printWriter.println("  mScreenBrightnessDimConfig=" + this.mScreenBrightnessDimConfig);
        printWriter.println("  mScreenBrightnessMinimumDimAmount=" + this.mScreenBrightnessMinimumDimAmount);
        super.dump(new android.util.IndentingPrintWriter(printWriter, "    "));
    }
}
