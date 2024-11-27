package android.view;

/* loaded from: classes4.dex */
public interface FallbackEventHandler {
    boolean dispatchKeyEvent(android.view.KeyEvent keyEvent);

    void preDispatchKeyEvent(android.view.KeyEvent keyEvent);

    void setView(android.view.View view);
}
