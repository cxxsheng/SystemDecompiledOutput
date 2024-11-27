package com.android.server.inputmethod;

@com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
/* loaded from: classes2.dex */
public final class InputMethodDialogWindowContext {

    @android.annotation.Nullable
    private android.content.Context mDialogWindowContext;

    @android.annotation.NonNull
    @com.android.internal.annotations.VisibleForTesting(visibility = com.android.internal.annotations.VisibleForTesting.Visibility.PACKAGE)
    public android.content.Context get(int i) {
        if (this.mDialogWindowContext == null || this.mDialogWindowContext.getDisplayId() != i) {
            this.mDialogWindowContext = new android.view.ContextThemeWrapper(android.app.ActivityThread.currentActivityThread().getSystemUiContext(i).createWindowContext(2012, null), android.R.style.Theme.DeviceDefault.Settings);
        }
        return this.mDialogWindowContext;
    }
}
