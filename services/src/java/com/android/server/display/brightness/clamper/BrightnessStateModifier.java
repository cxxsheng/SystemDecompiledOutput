package com.android.server.display.brightness.clamper;

/* loaded from: classes.dex */
public interface BrightnessStateModifier {
    void apply(android.hardware.display.DisplayManagerInternal.DisplayPowerRequest displayPowerRequest, com.android.server.display.DisplayBrightnessState.Builder builder);

    void dump(java.io.PrintWriter printWriter);

    void stop();
}
