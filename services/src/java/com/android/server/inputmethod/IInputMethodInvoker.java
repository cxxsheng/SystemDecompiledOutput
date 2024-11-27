package com.android.server.inputmethod;

/* loaded from: classes2.dex */
final class IInputMethodInvoker {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "InputMethodManagerService";

    @android.annotation.NonNull
    private final com.android.internal.inputmethod.IInputMethod mTarget;

    @android.annotation.Nullable
    static com.android.server.inputmethod.IInputMethodInvoker create(@android.annotation.Nullable com.android.internal.inputmethod.IInputMethod iInputMethod) {
        if (iInputMethod == null) {
            return null;
        }
        if (!android.os.Binder.isProxy(iInputMethod)) {
            throw new java.lang.UnsupportedOperationException(iInputMethod + " must have been a BinderProxy.");
        }
        return new com.android.server.inputmethod.IInputMethodInvoker(iInputMethod);
    }

    private static java.lang.String getCallerMethodName() {
        java.lang.StackTraceElement[] stackTrace = java.lang.Thread.currentThread().getStackTrace();
        if (stackTrace.length <= 4) {
            return "<bottom of call stack>";
        }
        return stackTrace[4].getMethodName();
    }

    private static void logRemoteException(@android.annotation.NonNull android.os.RemoteException remoteException) {
        if (!(remoteException instanceof android.os.DeadObjectException)) {
            android.util.Slog.w(TAG, "IPC failed at IInputMethodInvoker#" + getCallerMethodName(), remoteException);
        }
    }

    static int getBinderIdentityHashCode(@android.annotation.Nullable com.android.server.inputmethod.IInputMethodInvoker iInputMethodInvoker) {
        if (iInputMethodInvoker == null) {
            return 0;
        }
        return java.lang.System.identityHashCode(iInputMethodInvoker.mTarget);
    }

    private IInputMethodInvoker(@android.annotation.NonNull com.android.internal.inputmethod.IInputMethod iInputMethod) {
        this.mTarget = iInputMethod;
    }

    @android.annotation.NonNull
    android.os.IBinder asBinder() {
        return this.mTarget.asBinder();
    }

    void initializeInternal(android.os.IBinder iBinder, com.android.internal.inputmethod.IInputMethodPrivilegedOperations iInputMethodPrivilegedOperations, int i) {
        com.android.internal.inputmethod.IInputMethod.InitParams initParams = new com.android.internal.inputmethod.IInputMethod.InitParams();
        initParams.token = iBinder;
        initParams.privilegedOperations = iInputMethodPrivilegedOperations;
        initParams.navigationBarFlags = i;
        try {
            this.mTarget.initializeInternal(initParams);
        } catch (android.os.RemoteException e) {
            logRemoteException(e);
        }
    }

    void onCreateInlineSuggestionsRequest(com.android.internal.inputmethod.InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo, com.android.internal.inputmethod.IInlineSuggestionsRequestCallback iInlineSuggestionsRequestCallback) {
        try {
            this.mTarget.onCreateInlineSuggestionsRequest(inlineSuggestionsRequestInfo, iInlineSuggestionsRequestCallback);
        } catch (android.os.RemoteException e) {
            logRemoteException(e);
        }
    }

    void bindInput(android.view.inputmethod.InputBinding inputBinding) {
        try {
            this.mTarget.bindInput(inputBinding);
        } catch (android.os.RemoteException e) {
            logRemoteException(e);
        }
    }

    void unbindInput() {
        try {
            this.mTarget.unbindInput();
        } catch (android.os.RemoteException e) {
            logRemoteException(e);
        }
    }

    void startInput(android.os.IBinder iBinder, com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, android.view.inputmethod.EditorInfo editorInfo, boolean z, int i, @android.annotation.NonNull android.window.ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher) {
        com.android.internal.inputmethod.IInputMethod.StartInputParams startInputParams = new com.android.internal.inputmethod.IInputMethod.StartInputParams();
        startInputParams.startInputToken = iBinder;
        startInputParams.remoteInputConnection = iRemoteInputConnection;
        startInputParams.editorInfo = editorInfo;
        startInputParams.restarting = z;
        startInputParams.navigationBarFlags = i;
        startInputParams.imeDispatcher = imeOnBackInvokedDispatcher;
        try {
            this.mTarget.startInput(startInputParams);
        } catch (android.os.RemoteException e) {
            logRemoteException(e);
        }
    }

    void onNavButtonFlagsChanged(int i) {
        try {
            this.mTarget.onNavButtonFlagsChanged(i);
        } catch (android.os.RemoteException e) {
            logRemoteException(e);
        }
    }

    void createSession(android.view.InputChannel inputChannel, com.android.internal.inputmethod.IInputMethodSessionCallback iInputMethodSessionCallback) {
        try {
            this.mTarget.createSession(inputChannel, iInputMethodSessionCallback);
        } catch (android.os.RemoteException e) {
            logRemoteException(e);
        }
    }

    void setSessionEnabled(com.android.internal.inputmethod.IInputMethodSession iInputMethodSession, boolean z) {
        try {
            this.mTarget.setSessionEnabled(iInputMethodSession, z);
        } catch (android.os.RemoteException e) {
            logRemoteException(e);
        }
    }

    boolean showSoftInput(android.os.IBinder iBinder, @android.annotation.NonNull android.view.inputmethod.ImeTracker.Token token, int i, android.os.ResultReceiver resultReceiver) {
        try {
            this.mTarget.showSoftInput(iBinder, token, i, resultReceiver);
            return true;
        } catch (android.os.RemoteException e) {
            logRemoteException(e);
            return false;
        }
    }

    boolean hideSoftInput(android.os.IBinder iBinder, @android.annotation.NonNull android.view.inputmethod.ImeTracker.Token token, int i, android.os.ResultReceiver resultReceiver) {
        try {
            this.mTarget.hideSoftInput(iBinder, token, i, resultReceiver);
            return true;
        } catch (android.os.RemoteException e) {
            logRemoteException(e);
            return false;
        }
    }

    void updateEditorToolType(int i) {
        try {
            this.mTarget.updateEditorToolType(i);
        } catch (android.os.RemoteException e) {
            logRemoteException(e);
        }
    }

    void changeInputMethodSubtype(android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
        try {
            this.mTarget.changeInputMethodSubtype(inputMethodSubtype);
        } catch (android.os.RemoteException e) {
            logRemoteException(e);
        }
    }

    void canStartStylusHandwriting(int i, com.android.internal.inputmethod.IConnectionlessHandwritingCallback iConnectionlessHandwritingCallback, android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo, boolean z) {
        try {
            this.mTarget.canStartStylusHandwriting(i, iConnectionlessHandwritingCallback, cursorAnchorInfo, z);
        } catch (android.os.RemoteException e) {
            logRemoteException(e);
        }
    }

    boolean startStylusHandwriting(int i, android.view.InputChannel inputChannel, java.util.List<android.view.MotionEvent> list) {
        try {
            this.mTarget.startStylusHandwriting(i, inputChannel, list);
            return true;
        } catch (android.os.RemoteException e) {
            logRemoteException(e);
            return false;
        }
    }

    void commitHandwritingDelegationTextIfAvailable() {
        try {
            this.mTarget.commitHandwritingDelegationTextIfAvailable();
        } catch (android.os.RemoteException e) {
            logRemoteException(e);
        }
    }

    void discardHandwritingDelegationText() {
        try {
            this.mTarget.discardHandwritingDelegationText();
        } catch (android.os.RemoteException e) {
            logRemoteException(e);
        }
    }

    void initInkWindow() {
        try {
            this.mTarget.initInkWindow();
        } catch (android.os.RemoteException e) {
            logRemoteException(e);
        }
    }

    void finishStylusHandwriting() {
        try {
            this.mTarget.finishStylusHandwriting();
        } catch (android.os.RemoteException e) {
            logRemoteException(e);
        }
    }

    void removeStylusHandwritingWindow() {
        try {
            this.mTarget.removeStylusHandwritingWindow();
        } catch (android.os.RemoteException e) {
            logRemoteException(e);
        }
    }

    void setStylusWindowIdleTimeoutForTest(long j) {
        try {
            this.mTarget.setStylusWindowIdleTimeoutForTest(j);
        } catch (android.os.RemoteException e) {
            logRemoteException(e);
        }
    }
}
