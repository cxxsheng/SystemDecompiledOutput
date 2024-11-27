package android.accessibilityservice;

/* loaded from: classes.dex */
final class AccessibilityInputMethodSessionWrapper extends com.android.internal.inputmethod.IAccessibilityInputMethodSession.Stub {
    private final android.os.Handler mHandler;
    private final java.util.concurrent.atomic.AtomicReference<android.accessibilityservice.AccessibilityInputMethodSession> mSessionRef;

    AccessibilityInputMethodSessionWrapper(android.os.Looper looper, android.accessibilityservice.AccessibilityInputMethodSession accessibilityInputMethodSession) {
        this.mSessionRef = new java.util.concurrent.atomic.AtomicReference<>(accessibilityInputMethodSession);
        this.mHandler = android.os.Handler.createAsync(looper);
    }

    android.accessibilityservice.AccessibilityInputMethodSession getSession() {
        return this.mSessionRef.get();
    }

    @Override // com.android.internal.inputmethod.IAccessibilityInputMethodSession
    public void updateSelection(final int i, final int i2, final int i3, final int i4, final int i5, final int i6) {
        if (this.mHandler.getLooper().isCurrentThread()) {
            lambda$updateSelection$0(i, i2, i3, i4, i5, i6);
        } else {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.accessibilityservice.AccessibilityInputMethodSessionWrapper$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    android.accessibilityservice.AccessibilityInputMethodSessionWrapper.this.lambda$updateSelection$0(i, i2, i3, i4, i5, i6);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: doUpdateSelection, reason: merged with bridge method [inline-methods] */
    public void lambda$updateSelection$0(int i, int i2, int i3, int i4, int i5, int i6) {
        android.accessibilityservice.AccessibilityInputMethodSession accessibilityInputMethodSession = this.mSessionRef.get();
        if (accessibilityInputMethodSession != null) {
            accessibilityInputMethodSession.updateSelection(i, i2, i3, i4, i5, i6);
        }
    }

    @Override // com.android.internal.inputmethod.IAccessibilityInputMethodSession
    public void finishInput() {
        if (this.mHandler.getLooper().isCurrentThread()) {
            doFinishInput();
        } else {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.accessibilityservice.AccessibilityInputMethodSessionWrapper$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    android.accessibilityservice.AccessibilityInputMethodSessionWrapper.this.doFinishInput();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doFinishInput() {
        android.accessibilityservice.AccessibilityInputMethodSession accessibilityInputMethodSession = this.mSessionRef.get();
        if (accessibilityInputMethodSession != null) {
            accessibilityInputMethodSession.finishInput();
        }
    }

    @Override // com.android.internal.inputmethod.IAccessibilityInputMethodSession
    public void finishSession() {
        if (this.mHandler.getLooper().isCurrentThread()) {
            doFinishSession();
        } else {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.accessibilityservice.AccessibilityInputMethodSessionWrapper$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    android.accessibilityservice.AccessibilityInputMethodSessionWrapper.this.doFinishSession();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doFinishSession() {
        this.mSessionRef.set(null);
    }

    @Override // com.android.internal.inputmethod.IAccessibilityInputMethodSession
    public void invalidateInput(final android.view.inputmethod.EditorInfo editorInfo, final com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, final int i) {
        if (this.mHandler.getLooper().isCurrentThread()) {
            lambda$invalidateInput$1(editorInfo, iRemoteAccessibilityInputConnection, i);
        } else {
            this.mHandler.post(new java.lang.Runnable() { // from class: android.accessibilityservice.AccessibilityInputMethodSessionWrapper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.accessibilityservice.AccessibilityInputMethodSessionWrapper.this.lambda$invalidateInput$1(editorInfo, iRemoteAccessibilityInputConnection, i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: doInvalidateInput, reason: merged with bridge method [inline-methods] */
    public void lambda$invalidateInput$1(android.view.inputmethod.EditorInfo editorInfo, com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i) {
        android.accessibilityservice.AccessibilityInputMethodSession accessibilityInputMethodSession = this.mSessionRef.get();
        if (accessibilityInputMethodSession != null) {
            accessibilityInputMethodSession.invalidateInput(editorInfo, iRemoteAccessibilityInputConnection, i);
        }
    }
}
