package android.text.method;

/* loaded from: classes3.dex */
public interface KeyListener {
    void clearMetaKeyState(android.view.View view, android.text.Editable editable, int i);

    int getInputType();

    boolean onKeyDown(android.view.View view, android.text.Editable editable, int i, android.view.KeyEvent keyEvent);

    boolean onKeyOther(android.view.View view, android.text.Editable editable, android.view.KeyEvent keyEvent);

    boolean onKeyUp(android.view.View view, android.text.Editable editable, int i, android.view.KeyEvent keyEvent);
}
