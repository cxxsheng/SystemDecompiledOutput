package android.text.method;

/* loaded from: classes3.dex */
public class ScrollingMovementMethod extends android.text.method.BaseMovementMethod implements android.text.method.MovementMethod {
    private static android.text.method.ScrollingMovementMethod sInstance;

    @Override // android.text.method.BaseMovementMethod
    protected boolean left(android.widget.TextView textView, android.text.Spannable spannable) {
        return scrollLeft(textView, spannable, 1);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean right(android.widget.TextView textView, android.text.Spannable spannable) {
        return scrollRight(textView, spannable, 1);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean up(android.widget.TextView textView, android.text.Spannable spannable) {
        return scrollUp(textView, spannable, 1);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean down(android.widget.TextView textView, android.text.Spannable spannable) {
        return scrollDown(textView, spannable, 1);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean pageUp(android.widget.TextView textView, android.text.Spannable spannable) {
        return scrollPageUp(textView, spannable);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean pageDown(android.widget.TextView textView, android.text.Spannable spannable) {
        return scrollPageDown(textView, spannable);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean top(android.widget.TextView textView, android.text.Spannable spannable) {
        return scrollTop(textView, spannable);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean bottom(android.widget.TextView textView, android.text.Spannable spannable) {
        return scrollBottom(textView, spannable);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean lineStart(android.widget.TextView textView, android.text.Spannable spannable) {
        return scrollLineStart(textView, spannable);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean lineEnd(android.widget.TextView textView, android.text.Spannable spannable) {
        return scrollLineEnd(textView, spannable);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean home(android.widget.TextView textView, android.text.Spannable spannable) {
        return top(textView, spannable);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean end(android.widget.TextView textView, android.text.Spannable spannable) {
        return bottom(textView, spannable);
    }

    @Override // android.text.method.BaseMovementMethod, android.text.method.MovementMethod
    public boolean onTouchEvent(android.widget.TextView textView, android.text.Spannable spannable, android.view.MotionEvent motionEvent) {
        return android.text.method.Touch.onTouchEvent(textView, spannable, motionEvent);
    }

    @Override // android.text.method.BaseMovementMethod, android.text.method.MovementMethod
    public void onTakeFocus(android.widget.TextView textView, android.text.Spannable spannable, int i) {
        android.text.Layout layout = textView.getLayout();
        if (layout != null && (i & 2) != 0) {
            textView.scrollTo(textView.getScrollX(), layout.getLineTop(0));
        }
        if (layout != null && (i & 1) != 0) {
            textView.scrollTo(textView.getScrollX(), layout.getLineTop((layout.getLineCount() - 1) + 1) - (textView.getHeight() - (textView.getTotalPaddingTop() + textView.getTotalPaddingBottom())));
        }
    }

    public static android.text.method.MovementMethod getInstance() {
        if (sInstance == null) {
            sInstance = new android.text.method.ScrollingMovementMethod();
        }
        return sInstance;
    }
}
