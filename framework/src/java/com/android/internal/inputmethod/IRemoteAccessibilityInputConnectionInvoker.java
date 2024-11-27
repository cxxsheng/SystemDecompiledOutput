package com.android.internal.inputmethod;

/* loaded from: classes4.dex */
final class IRemoteAccessibilityInputConnectionInvoker {
    private final com.android.internal.inputmethod.IRemoteAccessibilityInputConnection mConnection;
    private final int mSessionId;

    private IRemoteAccessibilityInputConnectionInvoker(com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i) {
        this.mConnection = iRemoteAccessibilityInputConnection;
        this.mSessionId = i;
    }

    public static com.android.internal.inputmethod.IRemoteAccessibilityInputConnectionInvoker create(com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection) {
        java.util.Objects.requireNonNull(iRemoteAccessibilityInputConnection);
        return new com.android.internal.inputmethod.IRemoteAccessibilityInputConnectionInvoker(iRemoteAccessibilityInputConnection, 0);
    }

    public com.android.internal.inputmethod.IRemoteAccessibilityInputConnectionInvoker cloneWithSessionId(int i) {
        return new com.android.internal.inputmethod.IRemoteAccessibilityInputConnectionInvoker(this.mConnection, i);
    }

    public boolean isSameConnection(com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection) {
        return iRemoteAccessibilityInputConnection != null && this.mConnection.asBinder() == iRemoteAccessibilityInputConnection.asBinder();
    }

    com.android.internal.inputmethod.InputConnectionCommandHeader createHeader() {
        return new com.android.internal.inputmethod.InputConnectionCommandHeader(this.mSessionId);
    }

    public void commitText(java.lang.CharSequence charSequence, int i, android.view.inputmethod.TextAttribute textAttribute) {
        try {
            this.mConnection.commitText(createHeader(), charSequence, i, textAttribute);
        } catch (android.os.RemoteException e) {
        }
    }

    public void setSelection(int i, int i2) {
        try {
            this.mConnection.setSelection(createHeader(), i, i2);
        } catch (android.os.RemoteException e) {
        }
    }

    public com.android.internal.infra.AndroidFuture<android.view.inputmethod.SurroundingText> getSurroundingText(int i, int i2, int i3) {
        com.android.internal.infra.AndroidFuture<android.view.inputmethod.SurroundingText> androidFuture = new com.android.internal.infra.AndroidFuture<>();
        try {
            this.mConnection.getSurroundingText(createHeader(), i, i2, i3, androidFuture);
        } catch (android.os.RemoteException e) {
            androidFuture.completeExceptionally(e);
        }
        return androidFuture;
    }

    public void deleteSurroundingText(int i, int i2) {
        try {
            this.mConnection.deleteSurroundingText(createHeader(), i, i2);
        } catch (android.os.RemoteException e) {
        }
    }

    public void sendKeyEvent(android.view.KeyEvent keyEvent) {
        try {
            this.mConnection.sendKeyEvent(createHeader(), keyEvent);
        } catch (android.os.RemoteException e) {
        }
    }

    public void performEditorAction(int i) {
        try {
            this.mConnection.performEditorAction(createHeader(), i);
        } catch (android.os.RemoteException e) {
        }
    }

    public void performContextMenuAction(int i) {
        try {
            this.mConnection.performContextMenuAction(createHeader(), i);
        } catch (android.os.RemoteException e) {
        }
    }

    public com.android.internal.infra.AndroidFuture<java.lang.Integer> getCursorCapsMode(int i) {
        com.android.internal.infra.AndroidFuture<java.lang.Integer> androidFuture = new com.android.internal.infra.AndroidFuture<>();
        try {
            this.mConnection.getCursorCapsMode(createHeader(), i, androidFuture);
        } catch (android.os.RemoteException e) {
            androidFuture.completeExceptionally(e);
        }
        return androidFuture;
    }

    public void clearMetaKeyStates(int i) {
        try {
            this.mConnection.clearMetaKeyStates(createHeader(), i);
        } catch (android.os.RemoteException e) {
        }
    }
}
