package android.text.method;

/* loaded from: classes3.dex */
public class LinkMovementMethod extends android.text.method.ScrollingMovementMethod {
    private static final int CLICK = 1;
    private static final int DOWN = 3;
    private static java.lang.Object FROM_BELOW = new android.text.NoCopySpan.Concrete();
    private static final int HIDE_FLOATING_TOOLBAR_DELAY_MS = 200;
    private static final int UP = 2;
    private static android.text.method.LinkMovementMethod sInstance;

    @Override // android.text.method.BaseMovementMethod, android.text.method.MovementMethod
    public boolean canSelectArbitrarily() {
        return true;
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean handleMovementKey(android.widget.TextView textView, android.text.Spannable spannable, int i, int i2, android.view.KeyEvent keyEvent) {
        switch (i) {
            case 23:
            case 66:
                if (android.view.KeyEvent.metaStateHasNoModifiers(i2) && keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0 && action(1, textView, spannable)) {
                    return true;
                }
                break;
        }
        return super.handleMovementKey(textView, spannable, i, i2, keyEvent);
    }

    @Override // android.text.method.ScrollingMovementMethod, android.text.method.BaseMovementMethod
    protected boolean up(android.widget.TextView textView, android.text.Spannable spannable) {
        if (action(2, textView, spannable)) {
            return true;
        }
        return super.up(textView, spannable);
    }

    @Override // android.text.method.ScrollingMovementMethod, android.text.method.BaseMovementMethod
    protected boolean down(android.widget.TextView textView, android.text.Spannable spannable) {
        if (action(3, textView, spannable)) {
            return true;
        }
        return super.down(textView, spannable);
    }

    @Override // android.text.method.ScrollingMovementMethod, android.text.method.BaseMovementMethod
    protected boolean left(android.widget.TextView textView, android.text.Spannable spannable) {
        if (action(2, textView, spannable)) {
            return true;
        }
        return super.left(textView, spannable);
    }

    @Override // android.text.method.ScrollingMovementMethod, android.text.method.BaseMovementMethod
    protected boolean right(android.widget.TextView textView, android.text.Spannable spannable) {
        if (action(3, textView, spannable)) {
            return true;
        }
        return super.right(textView, spannable);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private boolean action(int i, android.widget.TextView textView, android.text.Spannable spannable) {
        android.text.Layout layout = textView.getLayout();
        if (textView.isOffsetMappingAvailable()) {
            return false;
        }
        int totalPaddingTop = textView.getTotalPaddingTop() + textView.getTotalPaddingBottom();
        int scrollY = textView.getScrollY();
        int height = (textView.getHeight() + scrollY) - totalPaddingTop;
        int lineForVertical = layout.getLineForVertical(scrollY);
        int lineForVertical2 = layout.getLineForVertical(height);
        int lineStart = layout.getLineStart(lineForVertical);
        int lineEnd = layout.getLineEnd(lineForVertical2);
        android.text.style.ClickableSpan[] clickableSpanArr = (android.text.style.ClickableSpan[]) spannable.getSpans(lineStart, lineEnd, android.text.style.ClickableSpan.class);
        int selectionStart = android.text.Selection.getSelectionStart(spannable);
        int selectionEnd = android.text.Selection.getSelectionEnd(spannable);
        int min = java.lang.Math.min(selectionStart, selectionEnd);
        int max = java.lang.Math.max(selectionStart, selectionEnd);
        if (min < 0 && spannable.getSpanStart(FROM_BELOW) >= 0) {
            min = spannable.length();
            max = min;
        }
        if (min > lineEnd) {
            max = Integer.MAX_VALUE;
            min = Integer.MAX_VALUE;
        }
        int i2 = -1;
        if (max < lineStart) {
            max = -1;
            min = -1;
        }
        switch (i) {
            case 1:
                if (min == max) {
                    return false;
                }
                android.text.style.ClickableSpan[] clickableSpanArr2 = (android.text.style.ClickableSpan[]) spannable.getSpans(min, max, android.text.style.ClickableSpan.class);
                if (clickableSpanArr2.length != 1) {
                    return false;
                }
                android.text.style.ClickableSpan clickableSpan = clickableSpanArr2[0];
                if (clickableSpan instanceof android.view.textclassifier.TextLinks.TextLinkSpan) {
                    ((android.view.textclassifier.TextLinks.TextLinkSpan) clickableSpan).onClick(textView, 1);
                } else {
                    clickableSpan.onClick(textView);
                }
                return false;
            case 2:
                int i3 = -1;
                for (int i4 = 0; i4 < clickableSpanArr.length; i4++) {
                    int spanEnd = spannable.getSpanEnd(clickableSpanArr[i4]);
                    if ((spanEnd < max || min == max) && spanEnd > i3) {
                        i2 = spannable.getSpanStart(clickableSpanArr[i4]);
                        i3 = spanEnd;
                    }
                }
                if (i2 >= 0) {
                    android.text.Selection.setSelection(spannable, i3, i2);
                    return true;
                }
                return false;
            case 3:
                int i5 = Integer.MAX_VALUE;
                int i6 = Integer.MAX_VALUE;
                for (int i7 = 0; i7 < clickableSpanArr.length; i7++) {
                    int spanStart = spannable.getSpanStart(clickableSpanArr[i7]);
                    if ((spanStart > min || min == max) && spanStart < i6) {
                        i5 = spannable.getSpanEnd(clickableSpanArr[i7]);
                        i6 = spanStart;
                    }
                }
                if (i5 < Integer.MAX_VALUE) {
                    android.text.Selection.setSelection(spannable, i6, i5);
                    return true;
                }
                return false;
            default:
                return false;
        }
    }

    @Override // android.text.method.ScrollingMovementMethod, android.text.method.BaseMovementMethod, android.text.method.MovementMethod
    public boolean onTouchEvent(android.widget.TextView textView, android.text.Spannable spannable, android.view.MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 1 || action == 0) {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            int totalPaddingLeft = x - textView.getTotalPaddingLeft();
            int totalPaddingTop = y - textView.getTotalPaddingTop();
            int scrollX = totalPaddingLeft + textView.getScrollX();
            int scrollY = totalPaddingTop + textView.getScrollY();
            android.text.Layout layout = textView.getLayout();
            android.text.style.ClickableSpan[] clickableSpanArr = null;
            if (scrollY >= 0 && scrollY <= layout.getHeight()) {
                int lineForVertical = layout.getLineForVertical(scrollY);
                float f = scrollX;
                if (f >= layout.getLineLeft(lineForVertical) && f <= layout.getLineRight(lineForVertical)) {
                    int offsetForHorizontal = layout.getOffsetForHorizontal(lineForVertical, f);
                    clickableSpanArr = (android.text.style.ClickableSpan[]) spannable.getSpans(offsetForHorizontal, offsetForHorizontal, android.text.style.ClickableSpan.class);
                }
            }
            if (clickableSpanArr != null && clickableSpanArr.length != 0) {
                android.text.style.ClickableSpan clickableSpan = clickableSpanArr[0];
                if (action == 1) {
                    if (clickableSpan instanceof android.view.textclassifier.TextLinks.TextLinkSpan) {
                        ((android.view.textclassifier.TextLinks.TextLinkSpan) clickableSpan).onClick(textView, 0);
                    } else {
                        clickableSpan.onClick(textView);
                    }
                } else if (action == 0) {
                    if (textView.getContext().getApplicationInfo().targetSdkVersion >= 28) {
                        textView.hideFloatingToolbar(200);
                    }
                    android.text.Selection.setSelection(spannable, spannable.getSpanStart(clickableSpan), spannable.getSpanEnd(clickableSpan));
                }
                return true;
            }
            android.text.Selection.removeSelection(spannable);
        }
        return super.onTouchEvent(textView, spannable, motionEvent);
    }

    @Override // android.text.method.BaseMovementMethod, android.text.method.MovementMethod
    public void initialize(android.widget.TextView textView, android.text.Spannable spannable) {
        android.text.Selection.removeSelection(spannable);
        spannable.removeSpan(FROM_BELOW);
    }

    @Override // android.text.method.ScrollingMovementMethod, android.text.method.BaseMovementMethod, android.text.method.MovementMethod
    public void onTakeFocus(android.widget.TextView textView, android.text.Spannable spannable, int i) {
        android.text.Selection.removeSelection(spannable);
        if ((i & 1) != 0) {
            spannable.setSpan(FROM_BELOW, 0, 0, 34);
        } else {
            spannable.removeSpan(FROM_BELOW);
        }
    }

    public static android.text.method.MovementMethod getInstance() {
        if (sInstance == null) {
            sInstance = new android.text.method.LinkMovementMethod();
        }
        return sInstance;
    }
}
