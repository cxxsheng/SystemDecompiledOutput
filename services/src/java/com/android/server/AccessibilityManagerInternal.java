package com.android.server;

/* loaded from: classes.dex */
public abstract class AccessibilityManagerInternal {
    private static final com.android.server.AccessibilityManagerInternal NOP = new com.android.server.AccessibilityManagerInternal() { // from class: com.android.server.AccessibilityManagerInternal.1
        @Override // com.android.server.AccessibilityManagerInternal
        public void setImeSessionEnabled(android.util.SparseArray<com.android.internal.inputmethod.IAccessibilityInputMethodSession> sparseArray, boolean z) {
        }

        @Override // com.android.server.AccessibilityManagerInternal
        public void unbindInput() {
        }

        @Override // com.android.server.AccessibilityManagerInternal
        public void bindInput() {
        }

        @Override // com.android.server.AccessibilityManagerInternal
        public void createImeSession(android.util.ArraySet<java.lang.Integer> arraySet) {
        }

        @Override // com.android.server.AccessibilityManagerInternal
        public void startInput(com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, android.view.inputmethod.EditorInfo editorInfo, boolean z) {
        }

        @Override // com.android.server.AccessibilityManagerInternal
        public boolean isTouchExplorationEnabled(int i) {
            return false;
        }

        @Override // com.android.server.AccessibilityManagerInternal
        public void performSystemAction(int i) {
        }
    };

    public abstract void bindInput();

    public abstract void createImeSession(android.util.ArraySet<java.lang.Integer> arraySet);

    public abstract boolean isTouchExplorationEnabled(int i);

    public abstract void performSystemAction(int i);

    public abstract void setImeSessionEnabled(android.util.SparseArray<com.android.internal.inputmethod.IAccessibilityInputMethodSession> sparseArray, boolean z);

    public abstract void startInput(com.android.internal.inputmethod.IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, android.view.inputmethod.EditorInfo editorInfo, boolean z);

    public abstract void unbindInput();

    @android.annotation.NonNull
    public static com.android.server.AccessibilityManagerInternal get() {
        com.android.server.AccessibilityManagerInternal accessibilityManagerInternal = (com.android.server.AccessibilityManagerInternal) com.android.server.LocalServices.getService(com.android.server.AccessibilityManagerInternal.class);
        return accessibilityManagerInternal != null ? accessibilityManagerInternal : NOP;
    }
}
