package com.android.server.wm;

/* loaded from: classes3.dex */
interface InsetsControlTarget {
    default void notifyInsetsControlChanged(int i) {
    }

    default com.android.server.wm.WindowState getWindow() {
        return null;
    }

    default boolean isRequestedVisible(int i) {
        return (i & android.view.WindowInsets.Type.defaultVisible()) != 0;
    }

    default int getRequestedVisibleTypes() {
        return android.view.WindowInsets.Type.defaultVisible();
    }

    default void showInsets(int i, boolean z, @android.annotation.Nullable android.view.inputmethod.ImeTracker.Token token) {
    }

    default void hideInsets(int i, boolean z, @android.annotation.Nullable android.view.inputmethod.ImeTracker.Token token) {
    }

    default boolean canShowTransient() {
        return false;
    }

    static com.android.server.wm.WindowState asWindowOrNull(com.android.server.wm.InsetsControlTarget insetsControlTarget) {
        if (insetsControlTarget != null) {
            return insetsControlTarget.getWindow();
        }
        return null;
    }
}
