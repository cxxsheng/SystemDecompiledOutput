package com.android.internal.inputmethod;

/* loaded from: classes4.dex */
public final class RemoteAccessibilityInputConnection {
    private static final int MAX_WAIT_TIME_MILLIS = 2000;
    private static final java.lang.String TAG = "RemoteA11yInputConnection";
    private final com.android.internal.inputmethod.CancellationGroup mCancellationGroup;
    com.android.internal.inputmethod.IRemoteAccessibilityInputConnectionInvoker mInvoker;

    public RemoteAccessibilityInputConnection(com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, com.android.internal.inputmethod.CancellationGroup cancellationGroup) {
        this.mInvoker = com.android.internal.inputmethod.IRemoteAccessibilityInputConnectionInvoker.create(iRemoteAccessibilityInputConnection);
        this.mCancellationGroup = cancellationGroup;
    }

    public RemoteAccessibilityInputConnection(com.android.internal.inputmethod.RemoteAccessibilityInputConnection remoteAccessibilityInputConnection, int i) {
        this.mInvoker = remoteAccessibilityInputConnection.mInvoker.cloneWithSessionId(i);
        this.mCancellationGroup = remoteAccessibilityInputConnection.mCancellationGroup;
    }

    public boolean isSameConnection(com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection) {
        return this.mInvoker.isSameConnection(iRemoteAccessibilityInputConnection);
    }

    public void commitText(java.lang.CharSequence charSequence, int i, android.view.inputmethod.TextAttribute textAttribute) {
        this.mInvoker.commitText(charSequence, i, textAttribute);
    }

    public void setSelection(int i, int i2) {
        this.mInvoker.setSelection(i, i2);
    }

    public android.view.inputmethod.SurroundingText getSurroundingText(int i, int i2, int i3) {
        if (this.mCancellationGroup.isCanceled()) {
            return null;
        }
        return (android.view.inputmethod.SurroundingText) com.android.internal.inputmethod.CompletableFutureUtil.getResultOrNull(this.mInvoker.getSurroundingText(i, i2, i3), TAG, "getSurroundingText()", this.mCancellationGroup, 2000L);
    }

    public void deleteSurroundingText(int i, int i2) {
        this.mInvoker.deleteSurroundingText(i, i2);
    }

    public void sendKeyEvent(android.view.KeyEvent keyEvent) {
        this.mInvoker.sendKeyEvent(keyEvent);
    }

    public void performEditorAction(int i) {
        this.mInvoker.performEditorAction(i);
    }

    public void performContextMenuAction(int i) {
        this.mInvoker.performContextMenuAction(i);
    }

    public int getCursorCapsMode(int i) {
        if (this.mCancellationGroup.isCanceled()) {
            return 0;
        }
        return com.android.internal.inputmethod.CompletableFutureUtil.getResultOrZero(this.mInvoker.getCursorCapsMode(i), TAG, "getCursorCapsMode()", this.mCancellationGroup, 2000L);
    }

    public void clearMetaKeyStates(int i) {
        this.mInvoker.clearMetaKeyStates(i);
    }
}
