package android.inputmethodservice;

/* loaded from: classes2.dex */
interface InputMethodServiceInternal {
    android.content.Context getContext();

    default void exposeContent(android.view.inputmethod.InputContentInfo inputContentInfo, android.view.inputmethod.InputConnection inputConnection) {
    }

    default void notifyUserActionIfNecessary() {
    }

    default void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
    }

    default void triggerServiceDump(java.lang.String str, byte[] bArr) {
    }

    default boolean isServiceDestroyed() {
        return false;
    }
}
