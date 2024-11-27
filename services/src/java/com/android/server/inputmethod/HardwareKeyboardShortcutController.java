package com.android.server.inputmethod;

/* loaded from: classes2.dex */
final class HardwareKeyboardShortcutController {

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    private final java.util.ArrayList<com.android.internal.inputmethod.InputMethodSubtypeHandle> mSubtypeHandles = new java.util.ArrayList<>();
    private final int mUserId;

    int getUserId() {
        return this.mUserId;
    }

    HardwareKeyboardShortcutController(@android.annotation.NonNull com.android.server.inputmethod.InputMethodMap inputMethodMap, int i) {
        this.mUserId = i;
        reset(inputMethodMap);
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    void reset(@android.annotation.NonNull com.android.server.inputmethod.InputMethodMap inputMethodMap) {
        this.mSubtypeHandles.clear();
        com.android.server.inputmethod.InputMethodSettings create = com.android.server.inputmethod.InputMethodSettings.create(inputMethodMap, this.mUserId);
        java.util.ArrayList<android.view.inputmethod.InputMethodInfo> enabledInputMethodList = create.getEnabledInputMethodList();
        for (int i = 0; i < enabledInputMethodList.size(); i++) {
            android.view.inputmethod.InputMethodInfo inputMethodInfo = enabledInputMethodList.get(i);
            if (inputMethodInfo.shouldShowInInputMethodPicker()) {
                java.util.List<android.view.inputmethod.InputMethodSubtype> enabledInputMethodSubtypeList = create.getEnabledInputMethodSubtypeList(inputMethodInfo, true);
                if (enabledInputMethodSubtypeList.isEmpty()) {
                    this.mSubtypeHandles.add(com.android.internal.inputmethod.InputMethodSubtypeHandle.of(inputMethodInfo, (android.view.inputmethod.InputMethodSubtype) null));
                } else {
                    for (android.view.inputmethod.InputMethodSubtype inputMethodSubtype : enabledInputMethodSubtypeList) {
                        if (inputMethodSubtype.isSuitableForPhysicalKeyboardLayoutMapping()) {
                            this.mSubtypeHandles.add(com.android.internal.inputmethod.InputMethodSubtypeHandle.of(inputMethodInfo, inputMethodSubtype));
                        }
                    }
                }
            }
        }
    }

    @android.annotation.Nullable
    static <T> T getNeighborItem(@android.annotation.NonNull java.util.List<T> list, @android.annotation.NonNull T t, boolean z) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (java.util.Objects.equals(t, list.get(i))) {
                return list.get(((i + (z ? 1 : -1)) + size) % size);
            }
        }
        return null;
    }

    @com.android.internal.annotations.GuardedBy({"ImfLock.class"})
    @android.annotation.Nullable
    com.android.internal.inputmethod.InputMethodSubtypeHandle onSubtypeSwitch(@android.annotation.NonNull com.android.internal.inputmethod.InputMethodSubtypeHandle inputMethodSubtypeHandle, boolean z) {
        return (com.android.internal.inputmethod.InputMethodSubtypeHandle) getNeighborItem(this.mSubtypeHandles, inputMethodSubtypeHandle, z);
    }
}
