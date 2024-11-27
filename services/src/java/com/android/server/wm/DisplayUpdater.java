package com.android.server.wm;

/* loaded from: classes3.dex */
interface DisplayUpdater {
    void updateDisplayInfo(@android.annotation.NonNull java.lang.Runnable runnable);

    default void onDisplayContentDisplayPropertiesPreChanged(int i, int i2, int i3, int i4, int i5) {
    }

    default void onDisplayContentDisplayPropertiesPostChanged(int i, int i2, @android.annotation.NonNull android.window.DisplayAreaInfo displayAreaInfo) {
    }
}
