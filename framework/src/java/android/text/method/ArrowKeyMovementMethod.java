package android.text.method;

/* loaded from: classes3.dex */
public class ArrowKeyMovementMethod extends android.text.method.BaseMovementMethod implements android.text.method.MovementMethod {
    private static final java.lang.Object LAST_TAP_DOWN = new java.lang.Object();
    private static android.text.method.ArrowKeyMovementMethod sInstance;

    private static boolean isSelecting(android.text.Spannable spannable) {
        return android.text.method.MetaKeyKeyListener.getMetaState(spannable, 1) == 1 || android.text.method.MetaKeyKeyListener.getMetaState(spannable, 2048) != 0;
    }

    private static int getCurrentLineTop(android.text.Spannable spannable, android.text.Layout layout) {
        return layout.getLineTop(layout.getLineForOffset(android.text.Selection.getSelectionEnd(spannable)));
    }

    private static int getPageHeight(android.widget.TextView textView) {
        android.graphics.Rect rect = new android.graphics.Rect();
        if (textView.getGlobalVisibleRect(rect)) {
            return rect.height();
        }
        return 0;
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean handleMovementKey(android.widget.TextView textView, android.text.Spannable spannable, int i, int i2, android.view.KeyEvent keyEvent) {
        switch (i) {
            case 23:
                if (android.view.KeyEvent.metaStateHasNoModifiers(i2) && keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0 && android.text.method.MetaKeyKeyListener.getMetaState(spannable, 2048, keyEvent) != 0) {
                    return textView.showContextMenu();
                }
                break;
        }
        return super.handleMovementKey(textView, spannable, i, i2, keyEvent);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean left(android.widget.TextView textView, android.text.Spannable spannable) {
        if (textView.isOffsetMappingAvailable()) {
            return false;
        }
        android.text.Layout layout = textView.getLayout();
        if (isSelecting(spannable)) {
            return android.text.Selection.extendLeft(spannable, layout);
        }
        return android.text.Selection.moveLeft(spannable, layout);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean right(android.widget.TextView textView, android.text.Spannable spannable) {
        if (textView.isOffsetMappingAvailable()) {
            return false;
        }
        android.text.Layout layout = textView.getLayout();
        if (isSelecting(spannable)) {
            return android.text.Selection.extendRight(spannable, layout);
        }
        return android.text.Selection.moveRight(spannable, layout);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean up(android.widget.TextView textView, android.text.Spannable spannable) {
        if (textView.isOffsetMappingAvailable()) {
            return false;
        }
        android.text.Layout layout = textView.getLayout();
        if (isSelecting(spannable)) {
            return android.text.Selection.extendUp(spannable, layout);
        }
        return android.text.Selection.moveUp(spannable, layout);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean down(android.widget.TextView textView, android.text.Spannable spannable) {
        if (textView.isOffsetMappingAvailable()) {
            return false;
        }
        android.text.Layout layout = textView.getLayout();
        if (isSelecting(spannable)) {
            return android.text.Selection.extendDown(spannable, layout);
        }
        return android.text.Selection.moveDown(spannable, layout);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean pageUp(android.widget.TextView textView, android.text.Spannable spannable) {
        boolean z = false;
        if (textView.isOffsetMappingAvailable()) {
            return false;
        }
        android.text.Layout layout = textView.getLayout();
        boolean isSelecting = isSelecting(spannable);
        int currentLineTop = getCurrentLineTop(spannable, layout) - getPageHeight(textView);
        do {
            int selectionEnd = android.text.Selection.getSelectionEnd(spannable);
            if (isSelecting) {
                android.text.Selection.extendUp(spannable, layout);
            } else {
                android.text.Selection.moveUp(spannable, layout);
            }
            if (android.text.Selection.getSelectionEnd(spannable) == selectionEnd) {
                break;
            }
            z = true;
        } while (getCurrentLineTop(spannable, layout) > currentLineTop);
        return z;
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean pageDown(android.widget.TextView textView, android.text.Spannable spannable) {
        boolean z = false;
        if (textView.isOffsetMappingAvailable()) {
            return false;
        }
        android.text.Layout layout = textView.getLayout();
        boolean isSelecting = isSelecting(spannable);
        int currentLineTop = getCurrentLineTop(spannable, layout) + getPageHeight(textView);
        do {
            int selectionEnd = android.text.Selection.getSelectionEnd(spannable);
            if (isSelecting) {
                android.text.Selection.extendDown(spannable, layout);
            } else {
                android.text.Selection.moveDown(spannable, layout);
            }
            if (android.text.Selection.getSelectionEnd(spannable) == selectionEnd) {
                break;
            }
            z = true;
        } while (getCurrentLineTop(spannable, layout) < currentLineTop);
        return z;
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean top(android.widget.TextView textView, android.text.Spannable spannable) {
        if (isSelecting(spannable)) {
            android.text.Selection.extendSelection(spannable, 0);
            return true;
        }
        android.text.Selection.setSelection(spannable, 0);
        return true;
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean bottom(android.widget.TextView textView, android.text.Spannable spannable) {
        if (isSelecting(spannable)) {
            android.text.Selection.extendSelection(spannable, spannable.length());
            return true;
        }
        android.text.Selection.setSelection(spannable, spannable.length());
        return true;
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean lineStart(android.widget.TextView textView, android.text.Spannable spannable) {
        if (textView.isOffsetMappingAvailable()) {
            return false;
        }
        android.text.Layout layout = textView.getLayout();
        if (isSelecting(spannable)) {
            return android.text.Selection.extendToLeftEdge(spannable, layout);
        }
        return android.text.Selection.moveToLeftEdge(spannable, layout);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean lineEnd(android.widget.TextView textView, android.text.Spannable spannable) {
        if (textView.isOffsetMappingAvailable()) {
            return false;
        }
        android.text.Layout layout = textView.getLayout();
        if (isSelecting(spannable)) {
            return android.text.Selection.extendToRightEdge(spannable, layout);
        }
        return android.text.Selection.moveToRightEdge(spannable, layout);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean leftWord(android.widget.TextView textView, android.text.Spannable spannable) {
        int selectionEnd = textView.getSelectionEnd();
        android.text.method.WordIterator wordIterator = textView.getWordIterator();
        wordIterator.setCharSequence(spannable, selectionEnd, selectionEnd);
        return android.text.Selection.moveToPreceding(spannable, wordIterator, isSelecting(spannable));
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean rightWord(android.widget.TextView textView, android.text.Spannable spannable) {
        int selectionEnd = textView.getSelectionEnd();
        android.text.method.WordIterator wordIterator = textView.getWordIterator();
        wordIterator.setCharSequence(spannable, selectionEnd, selectionEnd);
        return android.text.Selection.moveToFollowing(spannable, wordIterator, isSelecting(spannable));
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean home(android.widget.TextView textView, android.text.Spannable spannable) {
        return lineStart(textView, spannable);
    }

    @Override // android.text.method.BaseMovementMethod
    protected boolean end(android.widget.TextView textView, android.text.Spannable spannable) {
        return lineEnd(textView, spannable);
    }

    @Override // android.text.method.BaseMovementMethod
    public boolean previousParagraph(android.widget.TextView textView, android.text.Spannable spannable) {
        if (textView.isOffsetMappingAvailable()) {
            return false;
        }
        android.text.Layout layout = textView.getLayout();
        if (isSelecting(spannable)) {
            return android.text.Selection.extendToParagraphStart(spannable);
        }
        return android.text.Selection.moveToParagraphStart(spannable, layout);
    }

    @Override // android.text.method.BaseMovementMethod
    public boolean nextParagraph(android.widget.TextView textView, android.text.Spannable spannable) {
        if (textView.isOffsetMappingAvailable()) {
            return false;
        }
        android.text.Layout layout = textView.getLayout();
        if (isSelecting(spannable)) {
            return android.text.Selection.extendToParagraphEnd(spannable);
        }
        return android.text.Selection.moveToParagraphEnd(spannable, layout);
    }

    @Override // android.text.method.BaseMovementMethod, android.text.method.MovementMethod
    public boolean onTouchEvent(android.widget.TextView textView, android.text.Spannable spannable, android.view.MotionEvent motionEvent) {
        int i;
        int i2;
        int action = motionEvent.getAction();
        if (action != 1) {
            i = -1;
            i2 = -1;
        } else {
            i = android.text.method.Touch.getInitialScrollX(textView, spannable);
            i2 = android.text.method.Touch.getInitialScrollY(textView, spannable);
        }
        boolean isSelecting = isSelecting(spannable);
        boolean onTouchEvent = android.text.method.Touch.onTouchEvent(textView, spannable, motionEvent);
        if (textView.didTouchFocusSelect()) {
            return onTouchEvent;
        }
        if (action == 0) {
            if (isSelecting(spannable)) {
                if (!textView.isFocused() && !textView.requestFocus()) {
                    return onTouchEvent;
                }
                int offsetForPosition = textView.getOffsetForPosition(motionEvent.getX(), motionEvent.getY());
                spannable.setSpan(LAST_TAP_DOWN, offsetForPosition, offsetForPosition, 34);
                textView.getParent().requestDisallowInterceptTouchEvent(true);
            }
        } else if (textView.isFocused()) {
            if (action == 2) {
                if (isSelecting(spannable) && onTouchEvent) {
                    int spanStart = spannable.getSpanStart(LAST_TAP_DOWN);
                    textView.cancelLongPress();
                    int offsetForPosition2 = textView.getOffsetForPosition(motionEvent.getX(), motionEvent.getY());
                    android.text.Selection.setSelection(spannable, java.lang.Math.min(spanStart, offsetForPosition2), java.lang.Math.max(spanStart, offsetForPosition2));
                    return true;
                }
            } else if (action == 1) {
                if ((i2 >= 0 && i2 != textView.getScrollY()) || (i >= 0 && i != textView.getScrollX())) {
                    textView.moveCursorToVisibleOffset();
                    return true;
                }
                if (isSelecting) {
                    int spanStart2 = spannable.getSpanStart(LAST_TAP_DOWN);
                    int offsetForPosition3 = textView.getOffsetForPosition(motionEvent.getX(), motionEvent.getY());
                    android.text.Selection.setSelection(spannable, java.lang.Math.min(spanStart2, offsetForPosition3), java.lang.Math.max(spanStart2, offsetForPosition3));
                    spannable.removeSpan(LAST_TAP_DOWN);
                }
                android.text.method.MetaKeyKeyListener.adjustMetaAfterKeypress(spannable);
                android.text.method.MetaKeyKeyListener.resetLockedMeta(spannable);
                return true;
            }
        }
        return onTouchEvent;
    }

    @Override // android.text.method.BaseMovementMethod, android.text.method.MovementMethod
    public boolean canSelectArbitrarily() {
        return true;
    }

    @Override // android.text.method.BaseMovementMethod, android.text.method.MovementMethod
    public void initialize(android.widget.TextView textView, android.text.Spannable spannable) {
        android.text.Selection.setSelection(spannable, 0);
    }

    @Override // android.text.method.BaseMovementMethod, android.text.method.MovementMethod
    public void onTakeFocus(android.widget.TextView textView, android.text.Spannable spannable, int i) {
        if ((i & 130) != 0) {
            if (textView.getLayout() == null) {
                android.text.Selection.setSelection(spannable, spannable.length());
                return;
            }
            return;
        }
        android.text.Selection.setSelection(spannable, spannable.length());
    }

    public static android.text.method.MovementMethod getInstance() {
        if (sInstance == null) {
            sInstance = new android.text.method.ArrowKeyMovementMethod();
        }
        return sInstance;
    }
}
