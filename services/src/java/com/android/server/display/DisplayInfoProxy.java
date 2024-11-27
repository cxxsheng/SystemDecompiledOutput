package com.android.server.display;

/* loaded from: classes.dex */
public class DisplayInfoProxy {
    private android.view.DisplayInfo mInfo;

    public DisplayInfoProxy(@android.annotation.Nullable android.view.DisplayInfo displayInfo) {
        this.mInfo = displayInfo;
    }

    public void set(@android.annotation.Nullable android.view.DisplayInfo displayInfo) {
        this.mInfo = displayInfo;
        android.hardware.display.DisplayManagerGlobal.invalidateLocalDisplayInfoCaches();
    }

    @android.annotation.Nullable
    public android.view.DisplayInfo get() {
        return this.mInfo;
    }
}
