package com.android.server.display.brightness.strategy;

/* loaded from: classes.dex */
public interface DisplayBrightnessStrategy {
    void dump(java.io.PrintWriter printWriter);

    @android.annotation.NonNull
    java.lang.String getName();

    com.android.server.display.DisplayBrightnessState updateBrightness(android.hardware.display.DisplayManagerInternal.DisplayPowerRequest displayPowerRequest);
}
