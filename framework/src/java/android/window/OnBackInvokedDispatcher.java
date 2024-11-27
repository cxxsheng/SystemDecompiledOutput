package android.window;

/* loaded from: classes4.dex */
public interface OnBackInvokedDispatcher {
    public static final boolean DEBUG = false;
    public static final int PRIORITY_DEFAULT = 0;
    public static final int PRIORITY_OVERLAY = 1000000;
    public static final int PRIORITY_SYSTEM = -1;
    public static final java.lang.String TAG = "OnBackInvokedDispatcher";

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Priority {
    }

    void registerOnBackInvokedCallback(int i, android.window.OnBackInvokedCallback onBackInvokedCallback);

    void unregisterOnBackInvokedCallback(android.window.OnBackInvokedCallback onBackInvokedCallback);

    default void registerSystemOnBackInvokedCallback(android.window.OnBackInvokedCallback onBackInvokedCallback) {
    }

    default void setImeOnBackInvokedDispatcher(android.window.ImeOnBackInvokedDispatcher imeOnBackInvokedDispatcher) {
    }
}
