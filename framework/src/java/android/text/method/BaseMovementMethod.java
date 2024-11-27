package android.text.method;

/* loaded from: classes3.dex */
public class BaseMovementMethod implements android.text.method.MovementMethod {
    @Override // android.text.method.MovementMethod
    public boolean canSelectArbitrarily() {
        return false;
    }

    @Override // android.text.method.MovementMethod
    public void initialize(android.widget.TextView textView, android.text.Spannable spannable) {
    }

    @Override // android.text.method.MovementMethod
    public boolean onKeyDown(android.widget.TextView textView, android.text.Spannable spannable, int i, android.view.KeyEvent keyEvent) {
        boolean handleMovementKey = handleMovementKey(textView, spannable, i, getMovementMetaState(spannable, keyEvent), keyEvent);
        if (handleMovementKey) {
            android.text.method.MetaKeyKeyListener.adjustMetaAfterKeypress(spannable);
            android.text.method.MetaKeyKeyListener.resetLockedMeta(spannable);
        }
        return handleMovementKey;
    }

    @Override // android.text.method.MovementMethod
    public boolean onKeyOther(android.widget.TextView textView, android.text.Spannable spannable, android.view.KeyEvent keyEvent) {
        int movementMetaState = getMovementMetaState(spannable, keyEvent);
        int keyCode = keyEvent.getKeyCode();
        if (keyCode == 0 || keyEvent.getAction() != 2) {
            return false;
        }
        int repeatCount = keyEvent.getRepeatCount();
        int i = 0;
        boolean z = false;
        while (i < repeatCount && handleMovementKey(textView, spannable, keyCode, movementMetaState, keyEvent)) {
            i++;
            z = true;
        }
        if (z) {
            android.text.method.MetaKeyKeyListener.adjustMetaAfterKeypress(spannable);
            android.text.method.MetaKeyKeyListener.resetLockedMeta(spannable);
        }
        return z;
    }

    @Override // android.text.method.MovementMethod
    public boolean onKeyUp(android.widget.TextView textView, android.text.Spannable spannable, int i, android.view.KeyEvent keyEvent) {
        return false;
    }

    @Override // android.text.method.MovementMethod
    public void onTakeFocus(android.widget.TextView textView, android.text.Spannable spannable, int i) {
    }

    @Override // android.text.method.MovementMethod
    public boolean onTouchEvent(android.widget.TextView textView, android.text.Spannable spannable, android.view.MotionEvent motionEvent) {
        return false;
    }

    @Override // android.text.method.MovementMethod
    public boolean onTrackballEvent(android.widget.TextView textView, android.text.Spannable spannable, android.view.MotionEvent motionEvent) {
        return false;
    }

    @Override // android.text.method.MovementMethod
    public boolean onGenericMotionEvent(android.widget.TextView textView, android.text.Spannable spannable, android.view.MotionEvent motionEvent) {
        float f;
        float axisValue;
        boolean z = false;
        if ((motionEvent.getSource() & 2) != 0) {
            switch (motionEvent.getAction()) {
                case 8:
                    if ((motionEvent.getMetaState() & 1) != 0) {
                        axisValue = motionEvent.getAxisValue(9);
                        f = 0.0f;
                    } else {
                        f = -motionEvent.getAxisValue(9);
                        axisValue = motionEvent.getAxisValue(10);
                    }
                    if (axisValue < 0.0f) {
                        z = false | scrollLeft(textView, spannable, (int) java.lang.Math.ceil(-axisValue));
                    } else if (axisValue > 0.0f) {
                        z = false | scrollRight(textView, spannable, (int) java.lang.Math.ceil(axisValue));
                    }
                    if (f < 0.0f) {
                        return z | scrollUp(textView, spannable, (int) java.lang.Math.ceil(-f));
                    }
                    if (f > 0.0f) {
                        return z | scrollDown(textView, spannable, (int) java.lang.Math.ceil(f));
                    }
                    return z;
            }
        }
        return false;
    }

    protected int getMovementMetaState(android.text.Spannable spannable, android.view.KeyEvent keyEvent) {
        return android.view.KeyEvent.normalizeMetaState(android.text.method.MetaKeyKeyListener.getMetaState(spannable, keyEvent) & (-1537)) & (-194);
    }

    protected boolean handleMovementKey(android.widget.TextView textView, android.text.Spannable spannable, int i, int i2, android.view.KeyEvent keyEvent) {
        switch (i) {
            case 19:
                if (android.view.KeyEvent.metaStateHasNoModifiers(i2)) {
                    return up(textView, spannable);
                }
                if (android.view.KeyEvent.metaStateHasModifiers(i2, 2)) {
                    return top(textView, spannable);
                }
                if (android.view.KeyEvent.metaStateHasModifiers(i2, 4096)) {
                    return previousParagraph(textView, spannable);
                }
                return false;
            case 20:
                if (android.view.KeyEvent.metaStateHasNoModifiers(i2)) {
                    return down(textView, spannable);
                }
                if (android.view.KeyEvent.metaStateHasModifiers(i2, 2)) {
                    return bottom(textView, spannable);
                }
                if (android.view.KeyEvent.metaStateHasModifiers(i2, 4096)) {
                    return nextParagraph(textView, spannable);
                }
                return false;
            case 21:
                if (android.view.KeyEvent.metaStateHasNoModifiers(i2)) {
                    return left(textView, spannable);
                }
                if (android.view.KeyEvent.metaStateHasModifiers(i2, 4096)) {
                    return leftWord(textView, spannable);
                }
                if (android.view.KeyEvent.metaStateHasModifiers(i2, 2)) {
                    return lineStart(textView, spannable);
                }
                return false;
            case 22:
                if (android.view.KeyEvent.metaStateHasNoModifiers(i2)) {
                    return right(textView, spannable);
                }
                if (android.view.KeyEvent.metaStateHasModifiers(i2, 4096)) {
                    return rightWord(textView, spannable);
                }
                if (android.view.KeyEvent.metaStateHasModifiers(i2, 2)) {
                    return lineEnd(textView, spannable);
                }
                return false;
            case 92:
                if (android.view.KeyEvent.metaStateHasNoModifiers(i2)) {
                    return pageUp(textView, spannable);
                }
                if (android.view.KeyEvent.metaStateHasModifiers(i2, 2)) {
                    return top(textView, spannable);
                }
                return false;
            case 93:
                if (android.view.KeyEvent.metaStateHasNoModifiers(i2)) {
                    return pageDown(textView, spannable);
                }
                if (android.view.KeyEvent.metaStateHasModifiers(i2, 2)) {
                    return bottom(textView, spannable);
                }
                return false;
            case 122:
                if (android.view.KeyEvent.metaStateHasNoModifiers(i2)) {
                    return home(textView, spannable);
                }
                if (android.view.KeyEvent.metaStateHasModifiers(i2, 4096)) {
                    return top(textView, spannable);
                }
                return false;
            case 123:
                if (android.view.KeyEvent.metaStateHasNoModifiers(i2)) {
                    return end(textView, spannable);
                }
                if (android.view.KeyEvent.metaStateHasModifiers(i2, 4096)) {
                    return bottom(textView, spannable);
                }
                return false;
            default:
                return false;
        }
    }

    protected boolean left(android.widget.TextView textView, android.text.Spannable spannable) {
        return false;
    }

    protected boolean right(android.widget.TextView textView, android.text.Spannable spannable) {
        return false;
    }

    protected boolean up(android.widget.TextView textView, android.text.Spannable spannable) {
        return false;
    }

    protected boolean down(android.widget.TextView textView, android.text.Spannable spannable) {
        return false;
    }

    protected boolean pageUp(android.widget.TextView textView, android.text.Spannable spannable) {
        return false;
    }

    protected boolean pageDown(android.widget.TextView textView, android.text.Spannable spannable) {
        return false;
    }

    protected boolean top(android.widget.TextView textView, android.text.Spannable spannable) {
        return false;
    }

    protected boolean bottom(android.widget.TextView textView, android.text.Spannable spannable) {
        return false;
    }

    protected boolean lineStart(android.widget.TextView textView, android.text.Spannable spannable) {
        return false;
    }

    protected boolean lineEnd(android.widget.TextView textView, android.text.Spannable spannable) {
        return false;
    }

    protected boolean leftWord(android.widget.TextView textView, android.text.Spannable spannable) {
        return false;
    }

    protected boolean rightWord(android.widget.TextView textView, android.text.Spannable spannable) {
        return false;
    }

    protected boolean home(android.widget.TextView textView, android.text.Spannable spannable) {
        return false;
    }

    protected boolean end(android.widget.TextView textView, android.text.Spannable spannable) {
        return false;
    }

    public boolean previousParagraph(android.widget.TextView textView, android.text.Spannable spannable) {
        return false;
    }

    public boolean nextParagraph(android.widget.TextView textView, android.text.Spannable spannable) {
        return false;
    }

    private int getTopLine(android.widget.TextView textView) {
        return textView.getLayout().getLineForVertical(textView.getScrollY());
    }

    private int getBottomLine(android.widget.TextView textView) {
        return textView.getLayout().getLineForVertical(textView.getScrollY() + getInnerHeight(textView));
    }

    private int getInnerWidth(android.widget.TextView textView) {
        return (textView.getWidth() - textView.getTotalPaddingLeft()) - textView.getTotalPaddingRight();
    }

    private int getInnerHeight(android.widget.TextView textView) {
        return (textView.getHeight() - textView.getTotalPaddingTop()) - textView.getTotalPaddingBottom();
    }

    private int getCharacterWidth(android.widget.TextView textView) {
        return (int) java.lang.Math.ceil(textView.getPaint().getFontSpacing());
    }

    private int getScrollBoundsLeft(android.widget.TextView textView) {
        android.text.Layout layout = textView.getLayout();
        int topLine = getTopLine(textView);
        int bottomLine = getBottomLine(textView);
        if (topLine > bottomLine) {
            return 0;
        }
        int i = Integer.MAX_VALUE;
        while (topLine <= bottomLine) {
            int floor = (int) java.lang.Math.floor(layout.getLineLeft(topLine));
            if (floor < i) {
                i = floor;
            }
            topLine++;
        }
        return i;
    }

    private int getScrollBoundsRight(android.widget.TextView textView) {
        android.text.Layout layout = textView.getLayout();
        int topLine = getTopLine(textView);
        int bottomLine = getBottomLine(textView);
        if (topLine > bottomLine) {
            return 0;
        }
        int i = Integer.MIN_VALUE;
        while (topLine <= bottomLine) {
            int ceil = (int) java.lang.Math.ceil(layout.getLineRight(topLine));
            if (ceil > i) {
                i = ceil;
            }
            topLine++;
        }
        return i;
    }

    protected boolean scrollLeft(android.widget.TextView textView, android.text.Spannable spannable, int i) {
        int scrollBoundsLeft = getScrollBoundsLeft(textView);
        int scrollX = textView.getScrollX();
        if (scrollX > scrollBoundsLeft) {
            textView.scrollTo(java.lang.Math.max(scrollX - (getCharacterWidth(textView) * i), scrollBoundsLeft), textView.getScrollY());
            return true;
        }
        return false;
    }

    protected boolean scrollRight(android.widget.TextView textView, android.text.Spannable spannable, int i) {
        int scrollBoundsRight = getScrollBoundsRight(textView) - getInnerWidth(textView);
        int scrollX = textView.getScrollX();
        if (scrollX < scrollBoundsRight) {
            textView.scrollTo(java.lang.Math.min(scrollX + (getCharacterWidth(textView) * i), scrollBoundsRight), textView.getScrollY());
            return true;
        }
        return false;
    }

    protected boolean scrollUp(android.widget.TextView textView, android.text.Spannable spannable, int i) {
        android.text.Layout layout = textView.getLayout();
        int scrollY = textView.getScrollY();
        int lineForVertical = layout.getLineForVertical(scrollY);
        if (layout.getLineTop(lineForVertical) == scrollY) {
            lineForVertical--;
        }
        if (lineForVertical >= 0) {
            android.text.method.Touch.scrollTo(textView, layout, textView.getScrollX(), layout.getLineTop(java.lang.Math.max((lineForVertical - i) + 1, 0)));
            return true;
        }
        return false;
    }

    protected boolean scrollDown(android.widget.TextView textView, android.text.Spannable spannable, int i) {
        android.text.Layout layout = textView.getLayout();
        int innerHeight = getInnerHeight(textView);
        int scrollY = textView.getScrollY() + innerHeight;
        int lineForVertical = layout.getLineForVertical(scrollY);
        int i2 = lineForVertical + 1;
        if (layout.getLineTop(i2) < scrollY + 1) {
            lineForVertical = i2;
        }
        int lineCount = layout.getLineCount() - 1;
        if (lineForVertical <= lineCount) {
            android.text.method.Touch.scrollTo(textView, layout, textView.getScrollX(), layout.getLineTop(java.lang.Math.min((lineForVertical + i) - 1, lineCount) + 1) - innerHeight);
            return true;
        }
        return false;
    }

    protected boolean scrollPageUp(android.widget.TextView textView, android.text.Spannable spannable) {
        android.text.Layout layout = textView.getLayout();
        int lineForVertical = layout.getLineForVertical(textView.getScrollY() - getInnerHeight(textView));
        if (lineForVertical >= 0) {
            android.text.method.Touch.scrollTo(textView, layout, textView.getScrollX(), layout.getLineTop(lineForVertical));
            return true;
        }
        return false;
    }

    protected boolean scrollPageDown(android.widget.TextView textView, android.text.Spannable spannable) {
        android.text.Layout layout = textView.getLayout();
        int innerHeight = getInnerHeight(textView);
        int lineForVertical = layout.getLineForVertical(textView.getScrollY() + innerHeight + innerHeight);
        if (lineForVertical <= layout.getLineCount() - 1) {
            android.text.method.Touch.scrollTo(textView, layout, textView.getScrollX(), layout.getLineTop(lineForVertical + 1) - innerHeight);
            return true;
        }
        return false;
    }

    protected boolean scrollTop(android.widget.TextView textView, android.text.Spannable spannable) {
        android.text.Layout layout = textView.getLayout();
        if (getTopLine(textView) < 0) {
            return false;
        }
        android.text.method.Touch.scrollTo(textView, layout, textView.getScrollX(), layout.getLineTop(0));
        return true;
    }

    protected boolean scrollBottom(android.widget.TextView textView, android.text.Spannable spannable) {
        android.text.Layout layout = textView.getLayout();
        int lineCount = layout.getLineCount();
        if (getBottomLine(textView) <= lineCount - 1) {
            android.text.method.Touch.scrollTo(textView, layout, textView.getScrollX(), layout.getLineTop(lineCount) - getInnerHeight(textView));
            return true;
        }
        return false;
    }

    protected boolean scrollLineStart(android.widget.TextView textView, android.text.Spannable spannable) {
        int scrollBoundsLeft = getScrollBoundsLeft(textView);
        if (textView.getScrollX() > scrollBoundsLeft) {
            textView.scrollTo(scrollBoundsLeft, textView.getScrollY());
            return true;
        }
        return false;
    }

    protected boolean scrollLineEnd(android.widget.TextView textView, android.text.Spannable spannable) {
        int scrollBoundsRight = getScrollBoundsRight(textView) - getInnerWidth(textView);
        if (textView.getScrollX() < scrollBoundsRight) {
            textView.scrollTo(scrollBoundsRight, textView.getScrollY());
            return true;
        }
        return false;
    }
}
