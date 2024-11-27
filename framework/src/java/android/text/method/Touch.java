package android.text.method;

/* loaded from: classes3.dex */
public class Touch {
    private Touch() {
    }

    public static void scrollTo(android.widget.TextView textView, android.text.Layout layout, int i, int i2) {
        int i3;
        int width = textView.getWidth() - (textView.getTotalPaddingLeft() + textView.getTotalPaddingRight());
        int lineForVertical = layout.getLineForVertical(i2);
        android.text.Layout.Alignment paragraphAlignment = layout.getParagraphAlignment(lineForVertical);
        int i4 = 0;
        boolean z = layout.getParagraphDirection(lineForVertical) > 0;
        if (textView.getHorizontallyScrolling()) {
            int lineForVertical2 = layout.getLineForVertical((textView.getHeight() + i2) - (textView.getTotalPaddingTop() + textView.getTotalPaddingBottom()));
            i3 = 0;
            i4 = Integer.MAX_VALUE;
            while (lineForVertical <= lineForVertical2) {
                i4 = (int) java.lang.Math.min(i4, layout.getLineLeft(lineForVertical));
                i3 = (int) java.lang.Math.max(i3, layout.getLineRight(lineForVertical));
                lineForVertical++;
            }
        } else {
            i3 = width;
        }
        int i5 = i3 - i4;
        if (i5 < width) {
            if (paragraphAlignment == android.text.Layout.Alignment.ALIGN_CENTER) {
                i4 -= (width - i5) / 2;
            } else if ((z && paragraphAlignment == android.text.Layout.Alignment.ALIGN_OPPOSITE) || ((!z && paragraphAlignment == android.text.Layout.Alignment.ALIGN_NORMAL) || paragraphAlignment == android.text.Layout.Alignment.ALIGN_RIGHT)) {
                i4 -= width - i5;
            }
        } else {
            i4 = java.lang.Math.max(java.lang.Math.min(i, i3 - width), i4);
        }
        textView.scrollTo(i4, i2);
    }

    public static boolean onTouchEvent(android.widget.TextView textView, android.text.Spannable spannable, android.view.MotionEvent motionEvent) {
        float x;
        float y;
        switch (motionEvent.getActionMasked()) {
            case 0:
                for (java.lang.Object obj : (android.text.method.Touch.DragState[]) spannable.getSpans(0, spannable.length(), android.text.method.Touch.DragState.class)) {
                    spannable.removeSpan(obj);
                }
                spannable.setSpan(new android.text.method.Touch.DragState(motionEvent.getX(), motionEvent.getY(), textView.getScrollX(), textView.getScrollY()), 0, 0, 17);
                return true;
            case 1:
                android.text.method.Touch.DragState[] dragStateArr = (android.text.method.Touch.DragState[]) spannable.getSpans(0, spannable.length(), android.text.method.Touch.DragState.class);
                for (android.text.method.Touch.DragState dragState : dragStateArr) {
                    spannable.removeSpan(dragState);
                }
                return dragStateArr.length > 0 && dragStateArr[0].mUsed;
            case 2:
                android.text.method.Touch.DragState[] dragStateArr2 = (android.text.method.Touch.DragState[]) spannable.getSpans(0, spannable.length(), android.text.method.Touch.DragState.class);
                if (dragStateArr2.length > 0) {
                    if (!dragStateArr2[0].mFarEnough) {
                        float scaledTouchSlop = android.view.ViewConfiguration.get(textView.getContext()).getScaledTouchSlop();
                        if (java.lang.Math.abs(motionEvent.getX() - dragStateArr2[0].mX) >= scaledTouchSlop || java.lang.Math.abs(motionEvent.getY() - dragStateArr2[0].mY) >= scaledTouchSlop) {
                            dragStateArr2[0].mFarEnough = true;
                        }
                    }
                    if (dragStateArr2[0].mFarEnough) {
                        dragStateArr2[0].mUsed = true;
                        if (((motionEvent.getMetaState() & 1) == 0 && android.text.method.MetaKeyKeyListener.getMetaState(spannable, 1) != 1 && android.text.method.MetaKeyKeyListener.getMetaState(spannable, 2048) == 0) ? false : true) {
                            x = motionEvent.getX() - dragStateArr2[0].mX;
                            y = motionEvent.getY() - dragStateArr2[0].mY;
                        } else {
                            x = dragStateArr2[0].mX - motionEvent.getX();
                            y = dragStateArr2[0].mY - motionEvent.getY();
                        }
                        dragStateArr2[0].mX = motionEvent.getX();
                        dragStateArr2[0].mY = motionEvent.getY();
                        int scrollX = textView.getScrollX() + ((int) x);
                        int scrollY = textView.getScrollY() + ((int) y);
                        int totalPaddingTop = textView.getTotalPaddingTop() + textView.getTotalPaddingBottom();
                        android.text.Layout layout = textView.getLayout();
                        int max = java.lang.Math.max(java.lang.Math.min(scrollY, layout.getHeight() - (textView.getHeight() - totalPaddingTop)), 0);
                        int scrollX2 = textView.getScrollX();
                        int scrollY2 = textView.getScrollY();
                        scrollTo(textView, layout, scrollX, max);
                        if (scrollX2 != textView.getScrollX() || scrollY2 != textView.getScrollY()) {
                            textView.cancelLongPress();
                        }
                        return true;
                    }
                }
                break;
            default:
                return false;
        }
    }

    public static int getInitialScrollX(android.widget.TextView textView, android.text.Spannable spannable) {
        android.text.method.Touch.DragState[] dragStateArr = (android.text.method.Touch.DragState[]) spannable.getSpans(0, spannable.length(), android.text.method.Touch.DragState.class);
        if (dragStateArr.length > 0) {
            return dragStateArr[0].mScrollX;
        }
        return -1;
    }

    public static int getInitialScrollY(android.widget.TextView textView, android.text.Spannable spannable) {
        android.text.method.Touch.DragState[] dragStateArr = (android.text.method.Touch.DragState[]) spannable.getSpans(0, spannable.length(), android.text.method.Touch.DragState.class);
        if (dragStateArr.length > 0) {
            return dragStateArr[0].mScrollY;
        }
        return -1;
    }

    private static class DragState implements android.text.NoCopySpan {
        public boolean mFarEnough;
        public int mScrollX;
        public int mScrollY;
        public boolean mUsed;
        public float mX;
        public float mY;

        public DragState(float f, float f2, int i, int i2) {
            this.mX = f;
            this.mY = f2;
            this.mScrollX = i;
            this.mScrollY = i2;
        }
    }
}
