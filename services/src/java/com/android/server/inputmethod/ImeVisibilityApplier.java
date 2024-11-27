package com.android.server.inputmethod;

/* loaded from: classes2.dex */
interface ImeVisibilityApplier {
    default void performShowIme(android.os.IBinder iBinder, @android.annotation.NonNull android.view.inputmethod.ImeTracker.Token token, int i, android.os.ResultReceiver resultReceiver, int i2) {
    }

    default void performHideIme(android.os.IBinder iBinder, @android.annotation.NonNull android.view.inputmethod.ImeTracker.Token token, android.os.ResultReceiver resultReceiver, int i) {
    }

    default void applyImeVisibility(android.os.IBinder iBinder, @android.annotation.NonNull android.view.inputmethod.ImeTracker.Token token, @com.android.server.inputmethod.ImeVisibilityStateComputer.VisibilityState int i) {
    }

    default void updateImeLayeringByTarget(android.os.IBinder iBinder) {
    }

    default boolean showImeScreenshot(@android.annotation.NonNull android.os.IBinder iBinder, int i) {
        return false;
    }

    default boolean removeImeScreenshot(int i) {
        return false;
    }
}
