package android.window;

/* loaded from: classes4.dex */
public interface OnBackAnimationCallback extends android.window.OnBackInvokedCallback {
    default void onBackStarted(android.window.BackEvent backEvent) {
    }

    default void onBackProgressed(android.window.BackEvent backEvent) {
    }

    default void onBackCancelled() {
    }
}
