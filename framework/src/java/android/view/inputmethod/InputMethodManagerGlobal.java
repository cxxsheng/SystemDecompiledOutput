package android.view.inputmethod;

/* loaded from: classes4.dex */
public class InputMethodManagerGlobal {
    public static boolean isImeTraceAvailable() {
        return android.view.inputmethod.IInputMethodManagerGlobalInvoker.isAvailable();
    }

    public static void startProtoDump(byte[] bArr, int i, java.lang.String str, java.util.function.Consumer<android.os.RemoteException> consumer) {
        android.view.inputmethod.IInputMethodManagerGlobalInvoker.startProtoDump(bArr, i, str, consumer);
    }

    public static void startImeTrace(java.util.function.Consumer<android.os.RemoteException> consumer) {
        android.view.inputmethod.IInputMethodManagerGlobalInvoker.startImeTrace(consumer);
    }

    public static void stopImeTrace(java.util.function.Consumer<android.os.RemoteException> consumer) {
        android.view.inputmethod.IInputMethodManagerGlobalInvoker.stopImeTrace(consumer);
    }

    public static boolean isImeTraceEnabled() {
        return android.view.inputmethod.IInputMethodManagerGlobalInvoker.isImeTraceEnabled();
    }

    public static void removeImeSurface(java.util.function.Consumer<android.os.RemoteException> consumer) {
        android.view.inputmethod.IInputMethodManagerGlobalInvoker.removeImeSurface(consumer);
    }
}
