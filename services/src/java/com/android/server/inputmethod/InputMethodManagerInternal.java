package com.android.server.inputmethod;

/* loaded from: classes2.dex */
public abstract class InputMethodManagerInternal {
    private static final com.android.server.inputmethod.InputMethodManagerInternal NOP = new com.android.server.inputmethod.InputMethodManagerInternal() { // from class: com.android.server.inputmethod.InputMethodManagerInternal.1
        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void setInteractive(boolean z) {
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void hideAllInputMethods(int i, int i2) {
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public java.util.List<android.view.inputmethod.InputMethodInfo> getInputMethodListAsUser(int i) {
            return java.util.Collections.emptyList();
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public java.util.List<android.view.inputmethod.InputMethodInfo> getEnabledInputMethodListAsUser(int i) {
            return java.util.Collections.emptyList();
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void onCreateInlineSuggestionsRequest(int i, com.android.internal.inputmethod.InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo, com.android.internal.inputmethod.IInlineSuggestionsRequestCallback iInlineSuggestionsRequestCallback) {
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public boolean switchToInputMethod(java.lang.String str, int i) {
            return false;
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public boolean setInputMethodEnabled(java.lang.String str, boolean z, int i) {
            return false;
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void setVirtualDeviceInputMethodForAllUsers(int i, @android.annotation.Nullable java.lang.String str) {
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void registerInputMethodListListener(com.android.server.inputmethod.InputMethodManagerInternal.InputMethodListListener inputMethodListListener) {
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public boolean transferTouchFocusToImeWindow(@android.annotation.NonNull android.os.IBinder iBinder, int i) {
            return false;
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void reportImeControl(@android.annotation.Nullable android.os.IBinder iBinder) {
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void onImeParentChanged(int i) {
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void removeImeSurface(int i) {
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void updateImeWindowStatus(boolean z, int i) {
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void onSessionForAccessibilityCreated(int i, com.android.internal.inputmethod.IAccessibilityInputMethodSession iAccessibilityInputMethodSession, int i2) {
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void unbindAccessibilityFromCurrentClient(int i, int i2) {
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void maybeFinishStylusHandwriting() {
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public void onSwitchKeyboardLayoutShortcut(int i, int i2, android.os.IBinder iBinder) {
        }

        @Override // com.android.server.inputmethod.InputMethodManagerInternal
        public boolean isAnyInputConnectionActive() {
            return false;
        }
    };

    public interface InputMethodListListener {
        void onInputMethodListUpdated(java.util.List<android.view.inputmethod.InputMethodInfo> list, int i);
    }

    public abstract java.util.List<android.view.inputmethod.InputMethodInfo> getEnabledInputMethodListAsUser(int i);

    public abstract java.util.List<android.view.inputmethod.InputMethodInfo> getInputMethodListAsUser(int i);

    public abstract void hideAllInputMethods(int i, int i2);

    public abstract boolean isAnyInputConnectionActive();

    public abstract void maybeFinishStylusHandwriting();

    public abstract void onCreateInlineSuggestionsRequest(int i, com.android.internal.inputmethod.InlineSuggestionsRequestInfo inlineSuggestionsRequestInfo, com.android.internal.inputmethod.IInlineSuggestionsRequestCallback iInlineSuggestionsRequestCallback);

    public abstract void onImeParentChanged(int i);

    public abstract void onSessionForAccessibilityCreated(int i, com.android.internal.inputmethod.IAccessibilityInputMethodSession iAccessibilityInputMethodSession, int i2);

    public abstract void onSwitchKeyboardLayoutShortcut(int i, int i2, android.os.IBinder iBinder);

    public abstract void registerInputMethodListListener(com.android.server.inputmethod.InputMethodManagerInternal.InputMethodListListener inputMethodListListener);

    public abstract void removeImeSurface(int i);

    public abstract void reportImeControl(@android.annotation.Nullable android.os.IBinder iBinder);

    public abstract boolean setInputMethodEnabled(java.lang.String str, boolean z, int i);

    public abstract void setInteractive(boolean z);

    public abstract void setVirtualDeviceInputMethodForAllUsers(int i, @android.annotation.Nullable java.lang.String str);

    public abstract boolean switchToInputMethod(java.lang.String str, int i);

    public abstract boolean transferTouchFocusToImeWindow(@android.annotation.NonNull android.os.IBinder iBinder, int i);

    public abstract void unbindAccessibilityFromCurrentClient(int i, int i2);

    public abstract void updateImeWindowStatus(boolean z, int i);

    @android.annotation.NonNull
    public static com.android.server.inputmethod.InputMethodManagerInternal get() {
        com.android.server.inputmethod.InputMethodManagerInternal inputMethodManagerInternal = (com.android.server.inputmethod.InputMethodManagerInternal) com.android.server.LocalServices.getService(com.android.server.inputmethod.InputMethodManagerInternal.class);
        return inputMethodManagerInternal != null ? inputMethodManagerInternal : NOP;
    }
}
