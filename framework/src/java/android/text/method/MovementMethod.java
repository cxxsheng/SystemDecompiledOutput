package android.text.method;

/* loaded from: classes3.dex */
public interface MovementMethod {
    boolean canSelectArbitrarily();

    void initialize(android.widget.TextView textView, android.text.Spannable spannable);

    boolean onGenericMotionEvent(android.widget.TextView textView, android.text.Spannable spannable, android.view.MotionEvent motionEvent);

    boolean onKeyDown(android.widget.TextView textView, android.text.Spannable spannable, int i, android.view.KeyEvent keyEvent);

    boolean onKeyOther(android.widget.TextView textView, android.text.Spannable spannable, android.view.KeyEvent keyEvent);

    boolean onKeyUp(android.widget.TextView textView, android.text.Spannable spannable, int i, android.view.KeyEvent keyEvent);

    void onTakeFocus(android.widget.TextView textView, android.text.Spannable spannable, int i);

    boolean onTouchEvent(android.widget.TextView textView, android.text.Spannable spannable, android.view.MotionEvent motionEvent);

    boolean onTrackballEvent(android.widget.TextView textView, android.text.Spannable spannable, android.view.MotionEvent motionEvent);
}
