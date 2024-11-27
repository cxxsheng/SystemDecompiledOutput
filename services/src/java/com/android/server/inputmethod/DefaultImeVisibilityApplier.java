package com.android.server.inputmethod;

/* loaded from: classes2.dex */
final class DefaultImeVisibilityApplier implements com.android.server.inputmethod.ImeVisibilityApplier {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "DefaultImeVisibilityApplier";
    private com.android.server.inputmethod.InputMethodManagerService mService;
    private final com.android.server.wm.WindowManagerInternal mWindowManagerInternal = (com.android.server.wm.WindowManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.WindowManagerInternal.class);

    @android.annotation.NonNull
    private final com.android.server.wm.ImeTargetVisibilityPolicy mImeTargetVisibilityPolicy = (com.android.server.wm.ImeTargetVisibilityPolicy) com.android.server.LocalServices.getService(com.android.server.wm.ImeTargetVisibilityPolicy.class);

    DefaultImeVisibilityApplier(com.android.server.inputmethod.InputMethodManagerService inputMethodManagerService) {
        this.mService = inputMethodManagerService;
    }

    @Override // com.android.server.inputmethod.ImeVisibilityApplier
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    public void performShowIme(android.os.IBinder iBinder, @android.annotation.NonNull android.view.inputmethod.ImeTracker.Token token, int i, android.os.ResultReceiver resultReceiver, int i2) {
        com.android.server.inputmethod.IInputMethodInvoker curMethodLocked = this.mService.getCurMethodLocked();
        if (curMethodLocked != null && curMethodLocked.showSoftInput(iBinder, token, i, resultReceiver)) {
            if (android.view.inputmethod.ImeTracker.DEBUG_IME_VISIBILITY) {
                android.util.EventLog.writeEvent(com.android.server.EventLogTags.IMF_SHOW_IME, token != null ? token.getTag() : "TOKEN_NONE", java.util.Objects.toString(this.mService.mCurFocusedWindow), com.android.internal.inputmethod.InputMethodDebug.softInputDisplayReasonToString(i2), com.android.internal.inputmethod.InputMethodDebug.softInputModeToString(this.mService.mCurFocusedWindowSoftInputMode));
            }
            this.mService.onShowHideSoftInputRequested(true, iBinder, i2, token);
        }
    }

    @Override // com.android.server.inputmethod.ImeVisibilityApplier
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    public void performHideIme(android.os.IBinder iBinder, @android.annotation.NonNull android.view.inputmethod.ImeTracker.Token token, android.os.ResultReceiver resultReceiver, int i) {
        com.android.server.inputmethod.IInputMethodInvoker curMethodLocked = this.mService.getCurMethodLocked();
        if (curMethodLocked != null && curMethodLocked.hideSoftInput(iBinder, token, 0, resultReceiver)) {
            if (android.view.inputmethod.ImeTracker.DEBUG_IME_VISIBILITY) {
                android.util.EventLog.writeEvent(com.android.server.EventLogTags.IMF_HIDE_IME, token != null ? token.getTag() : "TOKEN_NONE", java.util.Objects.toString(this.mService.mCurFocusedWindow), com.android.internal.inputmethod.InputMethodDebug.softInputDisplayReasonToString(i), com.android.internal.inputmethod.InputMethodDebug.softInputModeToString(this.mService.mCurFocusedWindowSoftInputMode));
            }
            this.mService.onShowHideSoftInputRequested(false, iBinder, i, token);
        }
    }

    @Override // com.android.server.inputmethod.ImeVisibilityApplier
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    public void applyImeVisibility(android.os.IBinder iBinder, @android.annotation.NonNull android.view.inputmethod.ImeTracker.Token token, @com.android.server.inputmethod.ImeVisibilityStateComputer.VisibilityState int i) {
        applyImeVisibility(iBinder, token, i, 0);
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void applyImeVisibility(android.os.IBinder iBinder, @android.annotation.Nullable android.view.inputmethod.ImeTracker.Token token, @com.android.server.inputmethod.ImeVisibilityStateComputer.VisibilityState int i, int i2) {
        switch (i) {
            case 0:
                if (this.mService.hasAttachedClient()) {
                    android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 17);
                    this.mWindowManagerInternal.hideIme(iBinder, this.mService.getDisplayIdToShowImeLocked(), token);
                    return;
                } else {
                    android.view.inputmethod.ImeTracker.forLogging().onFailed(token, 17);
                    return;
                }
            case 1:
                android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 17);
                this.mWindowManagerInternal.showImePostLayout(iBinder, token);
                return;
            case 2:
            case 3:
            default:
                throw new java.lang.IllegalArgumentException("Invalid IME visibility state: " + i);
            case 4:
                showImeScreenshot(iBinder, this.mService.getDisplayIdToShowImeLocked());
                return;
            case 5:
                this.mService.hideCurrentInputLocked(iBinder, token, 0, null, i2);
                return;
            case 6:
                this.mService.hideCurrentInputLocked(iBinder, token, 2, null, i2);
                return;
            case 7:
                this.mService.showCurrentInputLocked(iBinder, token, 1, 0, null, i2);
                return;
            case 8:
                removeImeScreenshot(this.mService.getDisplayIdToShowImeLocked());
                return;
        }
    }

    @Override // com.android.server.inputmethod.ImeVisibilityApplier
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    public boolean showImeScreenshot(@android.annotation.NonNull android.os.IBinder iBinder, int i) {
        if (!this.mImeTargetVisibilityPolicy.showImeScreenshot(iBinder, i)) {
            return false;
        }
        this.mService.onShowHideSoftInputRequested(false, iBinder, 34, null);
        return true;
    }

    @Override // com.android.server.inputmethod.ImeVisibilityApplier
    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    public boolean removeImeScreenshot(int i) {
        if (!this.mImeTargetVisibilityPolicy.removeImeScreenshot(i)) {
            return false;
        }
        this.mService.onShowHideSoftInputRequested(false, this.mService.mCurFocusedWindow, 35, null);
        return true;
    }
}
