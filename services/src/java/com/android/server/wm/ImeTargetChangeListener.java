package com.android.server.wm;

/* loaded from: classes3.dex */
public interface ImeTargetChangeListener {
    default void onImeTargetOverlayVisibilityChanged(@android.annotation.NonNull android.os.IBinder iBinder, int i, boolean z, boolean z2) {
    }

    default void onImeInputTargetVisibilityChanged(@android.annotation.NonNull android.os.IBinder iBinder, boolean z, boolean z2) {
    }
}
