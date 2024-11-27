package com.android.server.inputmethod;

/* loaded from: classes2.dex */
public final class ImeVisibilityStateComputer {
    private static final boolean DEBUG = false;
    public static final int STATE_HIDE_IME = 0;
    public static final int STATE_HIDE_IME_EXPLICIT = 5;
    public static final int STATE_HIDE_IME_NOT_ALWAYS = 6;
    public static final int STATE_INVALID = -1;
    public static final int STATE_REMOVE_IME_SNAPSHOT = 8;
    public static final int STATE_SHOW_IME = 1;
    public static final int STATE_SHOW_IME_ABOVE_OVERLAY = 2;
    public static final int STATE_SHOW_IME_BEHIND_OVERLAY = 3;
    public static final int STATE_SHOW_IME_IMPLICIT = 7;
    public static final int STATE_SHOW_IME_SNAPSHOT = 4;
    private static final java.lang.String TAG = "ImeVisibilityStateComputer";
    private android.os.IBinder mCurVisibleImeInputTarget;
    private android.os.IBinder mCurVisibleImeLayeringOverlay;
    final com.android.server.inputmethod.InputMethodManagerService.ImeDisplayValidator mImeDisplayValidator;
    private boolean mInputShown;
    private final com.android.server.inputmethod.ImeVisibilityStateComputer.ImeVisibilityPolicy mPolicy;
    private final java.util.WeakHashMap<android.os.IBinder, com.android.server.inputmethod.ImeVisibilityStateComputer.ImeTargetWindowState> mRequestWindowStateMap;
    private boolean mRequestedImeScreenshot;
    boolean mRequestedShowExplicitly;
    private final com.android.server.inputmethod.InputMethodManagerService mService;
    boolean mShowForced;
    private final com.android.server.wm.WindowManagerInternal mWindowManagerInternal;

    @interface VisibilityState {
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ImeVisibilityStateComputer(@android.annotation.NonNull com.android.server.inputmethod.InputMethodManagerService inputMethodManagerService) {
        this(inputMethodManagerService, r1, new com.android.server.inputmethod.InputMethodManagerService.ImeDisplayValidator() { // from class: com.android.server.inputmethod.ImeVisibilityStateComputer$$ExternalSyntheticLambda0
            @Override // com.android.server.inputmethod.InputMethodManagerService.ImeDisplayValidator
            public final int getDisplayImePolicy(int i) {
                return com.android.server.wm.WindowManagerInternal.this.getDisplayImePolicy(i);
            }
        }, new com.android.server.inputmethod.ImeVisibilityStateComputer.ImeVisibilityPolicy());
        com.android.server.wm.WindowManagerInternal windowManagerInternal = (com.android.server.wm.WindowManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.WindowManagerInternal.class);
        final com.android.server.wm.WindowManagerInternal windowManagerInternal2 = (com.android.server.wm.WindowManagerInternal) com.android.server.LocalServices.getService(com.android.server.wm.WindowManagerInternal.class);
        java.util.Objects.requireNonNull(windowManagerInternal2);
    }

    @com.android.internal.annotations.VisibleForTesting
    public ImeVisibilityStateComputer(@android.annotation.NonNull com.android.server.inputmethod.InputMethodManagerService inputMethodManagerService, @android.annotation.NonNull com.android.server.inputmethod.ImeVisibilityStateComputer.Injector injector) {
        this(inputMethodManagerService, injector.getWmService(), injector.getImeValidator(), new com.android.server.inputmethod.ImeVisibilityStateComputer.ImeVisibilityPolicy());
    }

    interface Injector {
        default com.android.server.wm.WindowManagerInternal getWmService() {
            return null;
        }

        default com.android.server.inputmethod.InputMethodManagerService.ImeDisplayValidator getImeValidator() {
            return null;
        }
    }

    private ImeVisibilityStateComputer(com.android.server.inputmethod.InputMethodManagerService inputMethodManagerService, com.android.server.wm.WindowManagerInternal windowManagerInternal, com.android.server.inputmethod.InputMethodManagerService.ImeDisplayValidator imeDisplayValidator, com.android.server.inputmethod.ImeVisibilityStateComputer.ImeVisibilityPolicy imeVisibilityPolicy) {
        this.mRequestWindowStateMap = new java.util.WeakHashMap<>();
        this.mService = inputMethodManagerService;
        this.mWindowManagerInternal = windowManagerInternal;
        this.mImeDisplayValidator = imeDisplayValidator;
        this.mPolicy = imeVisibilityPolicy;
        this.mWindowManagerInternal.setInputMethodTargetChangeListener(new com.android.server.wm.ImeTargetChangeListener() { // from class: com.android.server.inputmethod.ImeVisibilityStateComputer.1
            @Override // com.android.server.wm.ImeTargetChangeListener
            public void onImeTargetOverlayVisibilityChanged(android.os.IBinder iBinder, int i, boolean z, boolean z2) {
                com.android.server.inputmethod.ImeVisibilityStateComputer imeVisibilityStateComputer = com.android.server.inputmethod.ImeVisibilityStateComputer.this;
                if (!z || z2 || i == 3) {
                    iBinder = null;
                }
                imeVisibilityStateComputer.mCurVisibleImeLayeringOverlay = iBinder;
            }

            @Override // com.android.server.wm.ImeTargetChangeListener
            public void onImeInputTargetVisibilityChanged(android.os.IBinder iBinder, boolean z, boolean z2) {
                if (com.android.server.inputmethod.ImeVisibilityStateComputer.this.mCurVisibleImeInputTarget == iBinder && ((!z || z2) && com.android.server.inputmethod.ImeVisibilityStateComputer.this.mCurVisibleImeLayeringOverlay != null)) {
                    com.android.server.inputmethod.ImeVisibilityStateComputer.this.mService.onApplyImeVisibilityFromComputer(iBinder, android.view.inputmethod.ImeTracker.forLogging().onStart(2, 6, 37, false), new com.android.server.inputmethod.ImeVisibilityStateComputer.ImeVisibilityResult(5, 37));
                }
                com.android.server.inputmethod.ImeVisibilityStateComputer imeVisibilityStateComputer = com.android.server.inputmethod.ImeVisibilityStateComputer.this;
                if (!z || z2) {
                    iBinder = null;
                }
                imeVisibilityStateComputer.mCurVisibleImeInputTarget = iBinder;
            }
        });
    }

    boolean onImeShowFlags(@android.annotation.NonNull android.view.inputmethod.ImeTracker.Token token, int i) {
        if (this.mPolicy.mA11yRequestingNoSoftKeyboard || this.mPolicy.mImeHiddenByDisplayPolicy) {
            android.view.inputmethod.ImeTracker.forLogging().onFailed(token, 4);
            return false;
        }
        android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 4);
        if ((i & 2) != 0) {
            this.mRequestedShowExplicitly = true;
            this.mShowForced = true;
        } else if ((i & 1) == 0) {
            this.mRequestedShowExplicitly = true;
        }
        return true;
    }

    boolean canHideIme(@android.annotation.NonNull android.view.inputmethod.ImeTracker.Token token, int i) {
        if ((i & 1) != 0 && (this.mRequestedShowExplicitly || this.mShowForced)) {
            android.view.inputmethod.ImeTracker.forLogging().onFailed(token, 6);
            return false;
        }
        if (this.mShowForced && (i & 2) != 0) {
            android.view.inputmethod.ImeTracker.forLogging().onFailed(token, 7);
            return false;
        }
        android.view.inputmethod.ImeTracker.forLogging().onProgress(token, 7);
        return true;
    }

    int getShowFlagsForInputMethodServiceOnly() {
        if (this.mShowForced) {
            return 3;
        }
        if (!this.mRequestedShowExplicitly) {
            return 0;
        }
        return 1;
    }

    int getShowFlags() {
        if (this.mShowForced) {
            return 2;
        }
        if (this.mRequestedShowExplicitly) {
            return 0;
        }
        return 1;
    }

    void clearImeShowFlags() {
        this.mRequestedShowExplicitly = false;
        this.mShowForced = false;
        this.mInputShown = false;
    }

    int computeImeDisplayId(@android.annotation.NonNull com.android.server.inputmethod.ImeVisibilityStateComputer.ImeTargetWindowState imeTargetWindowState, int i) {
        int computeImeDisplayIdForTarget = com.android.server.inputmethod.InputMethodManagerService.computeImeDisplayIdForTarget(i, this.mImeDisplayValidator);
        imeTargetWindowState.setImeDisplayId(computeImeDisplayIdForTarget);
        this.mPolicy.setImeHiddenByDisplayPolicy(computeImeDisplayIdForTarget == -1);
        return computeImeDisplayIdForTarget;
    }

    void requestImeVisibility(android.os.IBinder iBinder, boolean z) {
        com.android.server.inputmethod.ImeVisibilityStateComputer.ImeTargetWindowState orCreateWindowState = getOrCreateWindowState(iBinder);
        if (!this.mPolicy.mPendingA11yRequestingHideKeyboard) {
            orCreateWindowState.setRequestedImeVisible(z);
        } else {
            this.mPolicy.mPendingA11yRequestingHideKeyboard = false;
        }
        orCreateWindowState.setRequestImeToken(new android.os.Binder());
        setWindowStateInner(iBinder, orCreateWindowState);
    }

    com.android.server.inputmethod.ImeVisibilityStateComputer.ImeTargetWindowState getOrCreateWindowState(android.os.IBinder iBinder) {
        com.android.server.inputmethod.ImeVisibilityStateComputer.ImeTargetWindowState imeTargetWindowState = this.mRequestWindowStateMap.get(iBinder);
        if (imeTargetWindowState == null) {
            return new com.android.server.inputmethod.ImeVisibilityStateComputer.ImeTargetWindowState(0, 0, false, false, false);
        }
        return imeTargetWindowState;
    }

    com.android.server.inputmethod.ImeVisibilityStateComputer.ImeTargetWindowState getWindowStateOrNull(android.os.IBinder iBinder) {
        return this.mRequestWindowStateMap.get(iBinder);
    }

    void setWindowState(android.os.IBinder iBinder, @android.annotation.NonNull com.android.server.inputmethod.ImeVisibilityStateComputer.ImeTargetWindowState imeTargetWindowState) {
        com.android.server.inputmethod.ImeVisibilityStateComputer.ImeTargetWindowState imeTargetWindowState2 = this.mRequestWindowStateMap.get(iBinder);
        if (imeTargetWindowState2 != null && imeTargetWindowState.hasEditorFocused() && imeTargetWindowState.mToolType != 2) {
            imeTargetWindowState.setRequestedImeVisible(imeTargetWindowState2.mRequestedImeVisible);
        }
        setWindowStateInner(iBinder, imeTargetWindowState);
    }

    private void setWindowStateInner(android.os.IBinder iBinder, @android.annotation.NonNull com.android.server.inputmethod.ImeVisibilityStateComputer.ImeTargetWindowState imeTargetWindowState) {
        this.mRequestWindowStateMap.put(iBinder, imeTargetWindowState);
    }

    static class ImeVisibilityResult {
        private final int mReason;

        @com.android.server.inputmethod.ImeVisibilityStateComputer.VisibilityState
        private final int mState;

        ImeVisibilityResult(@com.android.server.inputmethod.ImeVisibilityStateComputer.VisibilityState int i, int i2) {
            this.mState = i;
            this.mReason = i2;
        }

        @com.android.server.inputmethod.ImeVisibilityStateComputer.VisibilityState
        int getState() {
            return this.mState;
        }

        int getReason() {
            return this.mReason;
        }
    }

    com.android.server.inputmethod.ImeVisibilityStateComputer.ImeVisibilityResult computeState(com.android.server.inputmethod.ImeVisibilityStateComputer.ImeTargetWindowState imeTargetWindowState, boolean z) {
        int i = imeTargetWindowState.mSoftInputModeState & 15;
        boolean z2 = (imeTargetWindowState.mSoftInputModeState & com.android.internal.util.FrameworkStatsLog.BOOT_TIME_EVENT_ELAPSED_TIME_REPORTED) == 16 || this.mService.mRes.getConfiguration().isLayoutSizeAtLeast(3);
        boolean z3 = (imeTargetWindowState.mSoftInputModeState & 256) != 0;
        if (imeTargetWindowState.hasEditorFocused() && shouldRestoreImeVisibility(imeTargetWindowState)) {
            imeTargetWindowState.setRequestedImeVisible(true);
            setWindowStateInner(getWindowTokenFrom(imeTargetWindowState), imeTargetWindowState);
            return new com.android.server.inputmethod.ImeVisibilityStateComputer.ImeVisibilityResult(7, 23);
        }
        switch (i) {
            case 0:
                if (imeTargetWindowState.hasImeFocusChanged() && (!imeTargetWindowState.hasEditorFocused() || !z2)) {
                    if (android.view.WindowManager.LayoutParams.mayUseInputMethod(imeTargetWindowState.getWindowFlags())) {
                        return new com.android.server.inputmethod.ImeVisibilityStateComputer.ImeVisibilityResult(6, 12);
                    }
                } else if (imeTargetWindowState.hasEditorFocused() && z2 && z3) {
                    return new com.android.server.inputmethod.ImeVisibilityStateComputer.ImeVisibilityResult(7, 6);
                }
                break;
            case 1:
                com.android.server.inputmethod.ImeVisibilityStateComputer.ImeTargetWindowState windowStateOrNull = getWindowStateOrNull(this.mService.mLastImeTargetWindow);
                if (windowStateOrNull != null) {
                    imeTargetWindowState.setRequestedImeVisible(windowStateOrNull.mRequestedImeVisible);
                    break;
                }
                break;
            case 2:
                if (z3) {
                    return new com.android.server.inputmethod.ImeVisibilityStateComputer.ImeVisibilityResult(5, 13);
                }
                break;
            case 3:
                if (imeTargetWindowState.hasImeFocusChanged()) {
                    return new com.android.server.inputmethod.ImeVisibilityStateComputer.ImeVisibilityResult(5, 14);
                }
                break;
            case 4:
                if (z3) {
                    if (!z) {
                        android.util.Slog.e(TAG, "SOFT_INPUT_STATE_VISIBLE is ignored because there is no focused view that also returns true from View#onCheckIsTextEditor()");
                        break;
                    } else {
                        return new com.android.server.inputmethod.ImeVisibilityStateComputer.ImeVisibilityResult(7, 7);
                    }
                }
                break;
            case 5:
                if (!z) {
                    android.util.Slog.e(TAG, "SOFT_INPUT_STATE_ALWAYS_VISIBLE is ignored because there is no focused view that also returns true from View#onCheckIsTextEditor()");
                    break;
                } else if (imeTargetWindowState.hasImeFocusChanged()) {
                    return new com.android.server.inputmethod.ImeVisibilityStateComputer.ImeVisibilityResult(7, 8);
                }
                break;
        }
        if (!imeTargetWindowState.hasImeFocusChanged() && imeTargetWindowState.isStartInputByGainFocus()) {
            return new com.android.server.inputmethod.ImeVisibilityStateComputer.ImeVisibilityResult(5, 21);
        }
        if (!imeTargetWindowState.hasEditorFocused() && this.mInputShown && imeTargetWindowState.isStartInputByGainFocus() && this.mService.mInputMethodDeviceConfigs.shouldHideImeWhenNoEditorFocus()) {
            return new com.android.server.inputmethod.ImeVisibilityStateComputer.ImeVisibilityResult(5, 33);
        }
        return null;
    }

    @com.android.internal.annotations.VisibleForTesting
    com.android.server.inputmethod.ImeVisibilityStateComputer.ImeVisibilityResult onInteractiveChanged(android.os.IBinder iBinder, boolean z) {
        com.android.server.inputmethod.ImeVisibilityStateComputer.ImeTargetWindowState windowStateOrNull = getWindowStateOrNull(iBinder);
        if (windowStateOrNull != null && windowStateOrNull.isRequestedImeVisible() && this.mInputShown && !z) {
            this.mRequestedImeScreenshot = true;
            return new com.android.server.inputmethod.ImeVisibilityStateComputer.ImeVisibilityResult(4, 34);
        }
        if (z && this.mRequestedImeScreenshot) {
            this.mRequestedImeScreenshot = false;
            return new com.android.server.inputmethod.ImeVisibilityStateComputer.ImeVisibilityResult(8, 35);
        }
        return null;
    }

    android.os.IBinder getWindowTokenFrom(android.os.IBinder iBinder) {
        for (android.os.IBinder iBinder2 : this.mRequestWindowStateMap.keySet()) {
            if (this.mRequestWindowStateMap.get(iBinder2).getRequestImeToken() == iBinder) {
                return iBinder2;
            }
        }
        return this.mService.mCurFocusedWindow;
    }

    android.os.IBinder getWindowTokenFrom(com.android.server.inputmethod.ImeVisibilityStateComputer.ImeTargetWindowState imeTargetWindowState) {
        for (android.os.IBinder iBinder : this.mRequestWindowStateMap.keySet()) {
            if (this.mRequestWindowStateMap.get(iBinder) == imeTargetWindowState) {
                return iBinder;
            }
        }
        return null;
    }

    boolean shouldRestoreImeVisibility(@android.annotation.NonNull com.android.server.inputmethod.ImeVisibilityStateComputer.ImeTargetWindowState imeTargetWindowState) {
        int softInputModeState = imeTargetWindowState.getSoftInputModeState();
        switch (softInputModeState & 15) {
            case 2:
                if ((softInputModeState & 256) != 0) {
                    return false;
                }
                break;
            case 3:
                return false;
        }
        return this.mWindowManagerInternal.shouldRestoreImeVisibility(getWindowTokenFrom(imeTargetWindowState));
    }

    boolean isInputShown() {
        return this.mInputShown;
    }

    void setInputShown(boolean z) {
        this.mInputShown = z;
    }

    void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        protoOutputStream.write(1133871366154L, this.mRequestedShowExplicitly);
        protoOutputStream.write(1133871366155L, this.mShowForced);
        protoOutputStream.write(1133871366168L, this.mPolicy.isA11yRequestNoSoftKeyboard());
        protoOutputStream.write(1133871366156L, this.mInputShown);
    }

    void dump(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull java.lang.String str) {
        android.util.PrintWriterPrinter printWriterPrinter = new android.util.PrintWriterPrinter(printWriter);
        printWriterPrinter.println(str + "mRequestedShowExplicitly=" + this.mRequestedShowExplicitly + " mShowForced=" + this.mShowForced);
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(str);
        sb.append("mImeHiddenByDisplayPolicy=");
        sb.append(this.mPolicy.isImeHiddenByDisplayPolicy());
        printWriterPrinter.println(sb.toString());
        printWriterPrinter.println(str + "mInputShown=" + this.mInputShown);
    }

    static class ImeVisibilityPolicy {
        private boolean mA11yRequestingNoSoftKeyboard;
        private boolean mImeHiddenByDisplayPolicy;
        private boolean mPendingA11yRequestingHideKeyboard;

        ImeVisibilityPolicy() {
        }

        void setImeHiddenByDisplayPolicy(boolean z) {
            this.mImeHiddenByDisplayPolicy = z;
        }

        boolean isImeHiddenByDisplayPolicy() {
            return this.mImeHiddenByDisplayPolicy;
        }

        void setA11yRequestNoSoftKeyboard(int i) {
            this.mA11yRequestingNoSoftKeyboard = (i & 3) == 1;
            if (this.mA11yRequestingNoSoftKeyboard) {
                this.mPendingA11yRequestingHideKeyboard = true;
            }
        }

        boolean isA11yRequestNoSoftKeyboard() {
            return this.mA11yRequestingNoSoftKeyboard;
        }
    }

    com.android.server.inputmethod.ImeVisibilityStateComputer.ImeVisibilityPolicy getImePolicy() {
        return this.mPolicy;
    }

    static class ImeTargetWindowState {
        private final boolean mHasFocusedEditor;
        private int mImeDisplayId;
        private final boolean mImeFocusChanged;
        private final boolean mIsStartInputByGainFocus;
        private android.os.IBinder mRequestImeToken;
        private boolean mRequestedImeVisible;
        private final int mSoftInputModeState;
        private final int mToolType;
        private final int mWindowFlags;

        ImeTargetWindowState(int i, int i2, boolean z, boolean z2, boolean z3) {
            this(i, i2, z, z2, z3, 0);
        }

        ImeTargetWindowState(int i, int i2, boolean z, boolean z2, boolean z3, int i3) {
            this.mImeDisplayId = 0;
            this.mSoftInputModeState = i;
            this.mWindowFlags = i2;
            this.mImeFocusChanged = z;
            this.mHasFocusedEditor = z2;
            this.mIsStartInputByGainFocus = z3;
            this.mToolType = i3;
        }

        boolean hasImeFocusChanged() {
            return this.mImeFocusChanged;
        }

        boolean hasEditorFocused() {
            return this.mHasFocusedEditor;
        }

        boolean isStartInputByGainFocus() {
            return this.mIsStartInputByGainFocus;
        }

        int getSoftInputModeState() {
            return this.mSoftInputModeState;
        }

        int getWindowFlags() {
            return this.mWindowFlags;
        }

        int getToolType() {
            return this.mToolType;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setImeDisplayId(int i) {
            this.mImeDisplayId = i;
        }

        int getImeDisplayId() {
            return this.mImeDisplayId;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setRequestedImeVisible(boolean z) {
            this.mRequestedImeVisible = z;
        }

        boolean isRequestedImeVisible() {
            return this.mRequestedImeVisible;
        }

        void setRequestImeToken(android.os.IBinder iBinder) {
            this.mRequestImeToken = iBinder;
        }

        android.os.IBinder getRequestImeToken() {
            return this.mRequestImeToken;
        }

        public java.lang.String toString() {
            return "ImeTargetWindowState{ imeToken " + this.mRequestImeToken + " imeFocusChanged " + this.mImeFocusChanged + " hasEditorFocused " + this.mHasFocusedEditor + " requestedImeVisible " + this.mRequestedImeVisible + " imeDisplayId " + this.mImeDisplayId + " softInputModeState " + com.android.internal.inputmethod.InputMethodDebug.softInputModeToString(this.mSoftInputModeState) + " isStartInputByGainFocus " + this.mIsStartInputByGainFocus + "}";
        }
    }
}
