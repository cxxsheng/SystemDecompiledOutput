package android.view.inputmethod;

/* loaded from: classes4.dex */
final class IAccessibilityInputMethodSessionInvoker {
    private static final java.lang.String TAG = "IAccessibilityInputMethodSessionInvoker";
    private static android.os.Handler sAsyncBinderEmulationHandler;
    private static final java.lang.Object sAsyncBinderEmulationHandlerLock = new java.lang.Object();
    private final android.os.Handler mCustomHandler;
    private final com.android.internal.inputmethod.IAccessibilityInputMethodSession mSession;

    private IAccessibilityInputMethodSessionInvoker(com.android.internal.inputmethod.IAccessibilityInputMethodSession iAccessibilityInputMethodSession, android.os.Handler handler) {
        this.mSession = iAccessibilityInputMethodSession;
        this.mCustomHandler = handler;
    }

    public static android.view.inputmethod.IAccessibilityInputMethodSessionInvoker createOrNull(com.android.internal.inputmethod.IAccessibilityInputMethodSession iAccessibilityInputMethodSession) {
        android.os.Handler handler;
        if (iAccessibilityInputMethodSession != null && !android.os.Binder.isProxy(iAccessibilityInputMethodSession)) {
            synchronized (sAsyncBinderEmulationHandlerLock) {
                if (sAsyncBinderEmulationHandler == null) {
                    android.os.HandlerThread handlerThread = new android.os.HandlerThread("IMM.IAIMS");
                    handlerThread.start();
                    sAsyncBinderEmulationHandler = android.os.Handler.createAsync(handlerThread.getLooper());
                }
                handler = sAsyncBinderEmulationHandler;
            }
        } else {
            handler = null;
        }
        if (iAccessibilityInputMethodSession == null) {
            return null;
        }
        return new android.view.inputmethod.IAccessibilityInputMethodSessionInvoker(iAccessibilityInputMethodSession, handler);
    }

    void finishInput() {
        if (this.mCustomHandler == null) {
            finishInputInternal();
        } else {
            this.mCustomHandler.post(new java.lang.Runnable() { // from class: android.view.inputmethod.IAccessibilityInputMethodSessionInvoker$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.inputmethod.IAccessibilityInputMethodSessionInvoker.this.finishInputInternal();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishInputInternal() {
        try {
            this.mSession.finishInput();
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "A11yIME died", e);
        }
    }

    void updateSelection(final int i, final int i2, final int i3, final int i4, final int i5, final int i6) {
        if (this.mCustomHandler == null) {
            lambda$updateSelection$0(i, i2, i3, i4, i5, i6);
        } else {
            this.mCustomHandler.post(new java.lang.Runnable() { // from class: android.view.inputmethod.IAccessibilityInputMethodSessionInvoker$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.inputmethod.IAccessibilityInputMethodSessionInvoker.this.lambda$updateSelection$0(i, i2, i3, i4, i5, i6);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: updateSelectionInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$updateSelection$0(int i, int i2, int i3, int i4, int i5, int i6) {
        try {
            this.mSession.updateSelection(i, i2, i3, i4, i5, i6);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "A11yIME died", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void invalidateInput(final android.view.inputmethod.EditorInfo editorInfo, final com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, final int i) {
        if (this.mCustomHandler == null) {
            lambda$invalidateInput$1(editorInfo, iRemoteAccessibilityInputConnection, i);
        } else {
            this.mCustomHandler.post(new java.lang.Runnable() { // from class: android.view.inputmethod.IAccessibilityInputMethodSessionInvoker$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.inputmethod.IAccessibilityInputMethodSessionInvoker.this.lambda$invalidateInput$1(editorInfo, iRemoteAccessibilityInputConnection, i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: invalidateInputInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$invalidateInput$1(android.view.inputmethod.EditorInfo editorInfo, com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i) {
        try {
            this.mSession.invalidateInput(editorInfo, iRemoteAccessibilityInputConnection, i);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "A11yIME died", e);
        }
    }
}
