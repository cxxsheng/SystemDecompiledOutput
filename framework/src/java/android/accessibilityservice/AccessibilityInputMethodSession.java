package android.accessibilityservice;

/* loaded from: classes.dex */
interface AccessibilityInputMethodSession {
    void finishInput();

    void invalidateInput(android.view.inputmethod.EditorInfo editorInfo, com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, int i);

    void setEnabled(boolean z);

    void updateSelection(int i, int i2, int i3, int i4, int i5, int i6);
}
