package android.widget;

/* loaded from: classes4.dex */
final class AccessibilityIterators {
    AccessibilityIterators() {
    }

    static class LineTextSegmentIterator extends android.view.AccessibilityIterators.AbstractTextSegmentIterator {
        protected static final int DIRECTION_END = 1;
        protected static final int DIRECTION_START = -1;
        private static android.widget.AccessibilityIterators.LineTextSegmentIterator sLineInstance;
        protected android.text.Layout mLayout;

        LineTextSegmentIterator() {
        }

        public static android.widget.AccessibilityIterators.LineTextSegmentIterator getInstance() {
            if (sLineInstance == null) {
                sLineInstance = new android.widget.AccessibilityIterators.LineTextSegmentIterator();
            }
            return sLineInstance;
        }

        public void initialize(android.text.Spannable spannable, android.text.Layout layout) {
            this.mText = spannable.toString();
            this.mLayout = layout;
        }

        @Override // android.view.AccessibilityIterators.TextSegmentIterator
        public int[] following(int i) {
            int i2;
            if (this.mText.length() <= 0 || i >= this.mText.length()) {
                return null;
            }
            if (i < 0) {
                i2 = this.mLayout.getLineForOffset(0);
            } else {
                int lineForOffset = this.mLayout.getLineForOffset(i);
                if (getLineEdgeIndex(lineForOffset, -1) == i) {
                    i2 = lineForOffset;
                } else {
                    i2 = lineForOffset + 1;
                }
            }
            if (i2 >= this.mLayout.getLineCount()) {
                return null;
            }
            return getRange(getLineEdgeIndex(i2, -1), getLineEdgeIndex(i2, 1) + 1);
        }

        @Override // android.view.AccessibilityIterators.TextSegmentIterator
        public int[] preceding(int i) {
            int i2;
            if (this.mText.length() <= 0 || i <= 0) {
                return null;
            }
            if (i > this.mText.length()) {
                i2 = this.mLayout.getLineForOffset(this.mText.length());
            } else {
                int lineForOffset = this.mLayout.getLineForOffset(i);
                if (getLineEdgeIndex(lineForOffset, 1) + 1 == i) {
                    i2 = lineForOffset;
                } else {
                    i2 = lineForOffset - 1;
                }
            }
            if (i2 < 0) {
                return null;
            }
            return getRange(getLineEdgeIndex(i2, -1), getLineEdgeIndex(i2, 1) + 1);
        }

        protected int getLineEdgeIndex(int i, int i2) {
            if (i2 * this.mLayout.getParagraphDirection(i) < 0) {
                return this.mLayout.getLineStart(i);
            }
            return this.mLayout.getLineEnd(i) - 1;
        }
    }

    static class PageTextSegmentIterator extends android.widget.AccessibilityIterators.LineTextSegmentIterator {
        private static android.widget.AccessibilityIterators.PageTextSegmentIterator sPageInstance;
        private final android.graphics.Rect mTempRect = new android.graphics.Rect();
        private android.widget.TextView mView;

        PageTextSegmentIterator() {
        }

        public static android.widget.AccessibilityIterators.PageTextSegmentIterator getInstance() {
            if (sPageInstance == null) {
                sPageInstance = new android.widget.AccessibilityIterators.PageTextSegmentIterator();
            }
            return sPageInstance;
        }

        public void initialize(android.widget.TextView textView) {
            super.initialize((android.text.Spannable) textView.getIterableTextForAccessibility(), textView.getLayout());
            this.mView = textView;
        }

        @Override // android.widget.AccessibilityIterators.LineTextSegmentIterator, android.view.AccessibilityIterators.TextSegmentIterator
        public int[] following(int i) {
            if (this.mText.length() <= 0 || i >= this.mText.length() || !this.mView.getGlobalVisibleRect(this.mTempRect)) {
                return null;
            }
            int max = java.lang.Math.max(0, i);
            int lineTop = this.mLayout.getLineTop(this.mLayout.getLineForOffset(max)) + ((this.mTempRect.height() - this.mView.getTotalPaddingTop()) - this.mView.getTotalPaddingBottom());
            return getRange(max, getLineEdgeIndex((lineTop < this.mLayout.getLineTop(this.mLayout.getLineCount() - 1) ? this.mLayout.getLineForVertical(lineTop) : this.mLayout.getLineCount()) - 1, 1) + 1);
        }

        @Override // android.widget.AccessibilityIterators.LineTextSegmentIterator, android.view.AccessibilityIterators.TextSegmentIterator
        public int[] preceding(int i) {
            if (this.mText.length() <= 0 || i <= 0 || !this.mView.getGlobalVisibleRect(this.mTempRect)) {
                return null;
            }
            int min = java.lang.Math.min(this.mText.length(), i);
            int lineForOffset = this.mLayout.getLineForOffset(min);
            int lineTop = this.mLayout.getLineTop(lineForOffset) - ((this.mTempRect.height() - this.mView.getTotalPaddingTop()) - this.mView.getTotalPaddingBottom());
            int lineForVertical = lineTop > 0 ? this.mLayout.getLineForVertical(lineTop) : 0;
            if (min == this.mText.length() && lineForVertical < lineForOffset) {
                lineForVertical++;
            }
            return getRange(getLineEdgeIndex(lineForVertical, -1), min);
        }
    }
}
