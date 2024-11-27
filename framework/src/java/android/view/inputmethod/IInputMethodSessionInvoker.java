package android.view.inputmethod;

/* loaded from: classes4.dex */
final class IInputMethodSessionInvoker {
    private static final java.lang.String TAG = "InputMethodSessionWrapper";
    private static android.os.Handler sAsyncBinderEmulationHandler;
    private static final java.lang.Object sAsyncBinderEmulationHandlerLock = new java.lang.Object();
    private final android.os.Handler mCustomHandler;
    private final com.android.internal.inputmethod.IInputMethodSession mSession;

    private IInputMethodSessionInvoker(com.android.internal.inputmethod.IInputMethodSession iInputMethodSession, android.os.Handler handler) {
        this.mSession = iInputMethodSession;
        this.mCustomHandler = handler;
    }

    public static android.view.inputmethod.IInputMethodSessionInvoker createOrNull(com.android.internal.inputmethod.IInputMethodSession iInputMethodSession) {
        android.os.Handler handler;
        if (iInputMethodSession != null && !android.os.Binder.isProxy(iInputMethodSession)) {
            synchronized (sAsyncBinderEmulationHandlerLock) {
                if (sAsyncBinderEmulationHandler == null) {
                    android.os.HandlerThread handlerThread = new android.os.HandlerThread("IMM.binder-emu");
                    handlerThread.start();
                    sAsyncBinderEmulationHandler = android.os.Handler.createAsync(handlerThread.getLooper());
                }
                handler = sAsyncBinderEmulationHandler;
            }
        } else {
            handler = null;
        }
        if (iInputMethodSession == null) {
            return null;
        }
        return new android.view.inputmethod.IInputMethodSessionInvoker(iInputMethodSession, handler);
    }

    void finishInput() {
        if (this.mCustomHandler == null) {
            finishInputInternal();
        } else {
            this.mCustomHandler.post(new java.lang.Runnable() { // from class: android.view.inputmethod.IInputMethodSessionInvoker$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.inputmethod.IInputMethodSessionInvoker.this.finishInputInternal();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishInputInternal() {
        try {
            this.mSession.finishInput();
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "IME died", e);
        }
    }

    void updateCursorAnchorInfo(final android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo) {
        if (this.mCustomHandler == null) {
            lambda$updateCursorAnchorInfo$0(cursorAnchorInfo);
        } else {
            this.mCustomHandler.post(new java.lang.Runnable() { // from class: android.view.inputmethod.IInputMethodSessionInvoker$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.inputmethod.IInputMethodSessionInvoker.this.lambda$updateCursorAnchorInfo$0(cursorAnchorInfo);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: updateCursorAnchorInfoInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$updateCursorAnchorInfo$0(android.view.inputmethod.CursorAnchorInfo cursorAnchorInfo) {
        try {
            this.mSession.updateCursorAnchorInfo(cursorAnchorInfo);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "IME died", e);
        }
    }

    void displayCompletions(final android.view.inputmethod.CompletionInfo[] completionInfoArr) {
        if (this.mCustomHandler == null) {
            lambda$displayCompletions$1(completionInfoArr);
        } else {
            this.mCustomHandler.post(new java.lang.Runnable() { // from class: android.view.inputmethod.IInputMethodSessionInvoker$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.inputmethod.IInputMethodSessionInvoker.this.lambda$displayCompletions$1(completionInfoArr);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: displayCompletionsInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$displayCompletions$1(android.view.inputmethod.CompletionInfo[] completionInfoArr) {
        try {
            this.mSession.displayCompletions(completionInfoArr);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "IME died", e);
        }
    }

    void updateExtractedText(final int i, final android.view.inputmethod.ExtractedText extractedText) {
        if (this.mCustomHandler == null) {
            lambda$updateExtractedText$2(i, extractedText);
        } else {
            this.mCustomHandler.post(new java.lang.Runnable() { // from class: android.view.inputmethod.IInputMethodSessionInvoker$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.inputmethod.IInputMethodSessionInvoker.this.lambda$updateExtractedText$2(i, extractedText);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: updateExtractedTextInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$updateExtractedText$2(int i, android.view.inputmethod.ExtractedText extractedText) {
        try {
            this.mSession.updateExtractedText(i, extractedText);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "IME died", e);
        }
    }

    void appPrivateCommand(final java.lang.String str, final android.os.Bundle bundle) {
        if (this.mCustomHandler == null) {
            lambda$appPrivateCommand$3(str, bundle);
        } else {
            this.mCustomHandler.post(new java.lang.Runnable() { // from class: android.view.inputmethod.IInputMethodSessionInvoker$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.inputmethod.IInputMethodSessionInvoker.this.lambda$appPrivateCommand$3(str, bundle);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: appPrivateCommandInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$appPrivateCommand$3(java.lang.String str, android.os.Bundle bundle) {
        try {
            this.mSession.appPrivateCommand(str, bundle);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "IME died", e);
        }
    }

    void viewClicked(final boolean z) {
        if (this.mCustomHandler == null) {
            lambda$viewClicked$4(z);
        } else {
            this.mCustomHandler.post(new java.lang.Runnable() { // from class: android.view.inputmethod.IInputMethodSessionInvoker$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.inputmethod.IInputMethodSessionInvoker.this.lambda$viewClicked$4(z);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: viewClickedInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$viewClicked$4(boolean z) {
        try {
            this.mSession.viewClicked(z);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "IME died", e);
        }
    }

    void updateCursor(final android.graphics.Rect rect) {
        if (this.mCustomHandler == null) {
            lambda$updateCursor$5(rect);
        } else {
            this.mCustomHandler.post(new java.lang.Runnable() { // from class: android.view.inputmethod.IInputMethodSessionInvoker$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.inputmethod.IInputMethodSessionInvoker.this.lambda$updateCursor$5(rect);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: updateCursorInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$updateCursor$5(android.graphics.Rect rect) {
        try {
            this.mSession.updateCursor(rect);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "IME died", e);
        }
    }

    void updateSelection(final int i, final int i2, final int i3, final int i4, final int i5, final int i6) {
        if (this.mCustomHandler == null) {
            lambda$updateSelection$6(i, i2, i3, i4, i5, i6);
        } else {
            this.mCustomHandler.post(new java.lang.Runnable() { // from class: android.view.inputmethod.IInputMethodSessionInvoker$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.inputmethod.IInputMethodSessionInvoker.this.lambda$updateSelection$6(i, i2, i3, i4, i5, i6);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: updateSelectionInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$updateSelection$6(int i, int i2, int i3, int i4, int i5, int i6) {
        try {
            this.mSession.updateSelection(i, i2, i3, i4, i5, i6);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "IME died", e);
        }
    }

    void invalidateInput(final android.view.inputmethod.EditorInfo editorInfo, final com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, final int i) {
        if (this.mCustomHandler == null) {
            lambda$invalidateInput$7(editorInfo, iRemoteInputConnection, i);
        } else {
            this.mCustomHandler.post(new java.lang.Runnable() { // from class: android.view.inputmethod.IInputMethodSessionInvoker$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.inputmethod.IInputMethodSessionInvoker.this.lambda$invalidateInput$7(editorInfo, iRemoteInputConnection, i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: invalidateInputInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$invalidateInput$7(android.view.inputmethod.EditorInfo editorInfo, com.android.internal.inputmethod.IRemoteInputConnection iRemoteInputConnection, int i) {
        try {
            this.mSession.invalidateInput(editorInfo, iRemoteInputConnection, i);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "IME died", e);
        }
    }

    public java.lang.String toString() {
        return this.mSession.toString();
    }
}
