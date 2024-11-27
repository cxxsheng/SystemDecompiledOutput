package android.text;

/* loaded from: classes3.dex */
public class Selection {
    private static final char PARAGRAPH_SEPARATOR = '\n';
    private static final java.lang.Object SELECTION_MEMORY = new android.text.Selection.MEMORY();
    public static final java.lang.Object SELECTION_START = new android.text.Selection.START();
    public static final java.lang.Object SELECTION_END = new android.text.Selection.END();

    public interface PositionIterator {
        public static final int DONE = -1;

        int following(int i);

        int preceding(int i);
    }

    private Selection() {
    }

    public static final int getSelectionStart(java.lang.CharSequence charSequence) {
        if (charSequence instanceof android.text.Spanned) {
            return ((android.text.Spanned) charSequence).getSpanStart(SELECTION_START);
        }
        return -1;
    }

    public static final int getSelectionEnd(java.lang.CharSequence charSequence) {
        if (charSequence instanceof android.text.Spanned) {
            return ((android.text.Spanned) charSequence).getSpanStart(SELECTION_END);
        }
        return -1;
    }

    private static int getSelectionMemory(java.lang.CharSequence charSequence) {
        if (charSequence instanceof android.text.Spanned) {
            return ((android.text.Spanned) charSequence).getSpanStart(SELECTION_MEMORY);
        }
        return -1;
    }

    public static void setSelection(android.text.Spannable spannable, int i, int i2) {
        setSelection(spannable, i, i2, -1);
    }

    private static void setSelection(android.text.Spannable spannable, int i, int i2, int i3) {
        int selectionStart = getSelectionStart(spannable);
        int selectionEnd = getSelectionEnd(spannable);
        if (selectionStart != i || selectionEnd != i2) {
            spannable.setSpan(SELECTION_START, i, i, 546);
            spannable.setSpan(SELECTION_END, i2, i2, 34);
            updateMemory(spannable, i3);
        }
    }

    private static void updateMemory(android.text.Spannable spannable, int i) {
        if (i > -1) {
            int selectionMemory = getSelectionMemory(spannable);
            if (i != selectionMemory) {
                spannable.setSpan(SELECTION_MEMORY, i, i, 34);
                if (selectionMemory == -1) {
                    spannable.setSpan(new android.text.Selection.MemoryTextWatcher(), 0, spannable.length(), 18);
                    return;
                }
                return;
            }
            return;
        }
        removeMemory(spannable);
    }

    private static void removeMemory(android.text.Spannable spannable) {
        spannable.removeSpan(SELECTION_MEMORY);
        for (android.text.Selection.MemoryTextWatcher memoryTextWatcher : (android.text.Selection.MemoryTextWatcher[]) spannable.getSpans(0, spannable.length(), android.text.Selection.MemoryTextWatcher.class)) {
            spannable.removeSpan(memoryTextWatcher);
        }
    }

    public static final class MemoryTextWatcher implements android.text.TextWatcher {
        @Override // android.text.TextWatcher
        public void beforeTextChanged(java.lang.CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(java.lang.CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(android.text.Editable editable) {
            editable.removeSpan(android.text.Selection.SELECTION_MEMORY);
            editable.removeSpan(this);
        }
    }

    public static final void setSelection(android.text.Spannable spannable, int i) {
        setSelection(spannable, i, i);
    }

    public static final void selectAll(android.text.Spannable spannable) {
        setSelection(spannable, 0, spannable.length());
    }

    public static final void extendSelection(android.text.Spannable spannable, int i) {
        extendSelection(spannable, i, -1);
    }

    private static void extendSelection(android.text.Spannable spannable, int i, int i2) {
        if (spannable.getSpanStart(SELECTION_END) != i) {
            spannable.setSpan(SELECTION_END, i, i, 34);
        }
        updateMemory(spannable, i2);
    }

    public static final void removeSelection(android.text.Spannable spannable) {
        spannable.removeSpan(SELECTION_START, 512);
        spannable.removeSpan(SELECTION_END);
        removeMemory(spannable);
    }

    public static boolean moveUp(android.text.Spannable spannable, android.text.Layout layout) {
        int selectionStart = getSelectionStart(spannable);
        int selectionEnd = getSelectionEnd(spannable);
        if (selectionStart != selectionEnd) {
            int min = java.lang.Math.min(selectionStart, selectionEnd);
            int max = java.lang.Math.max(selectionStart, selectionEnd);
            setSelection(spannable, min);
            return (min == 0 && max == spannable.length()) ? false : true;
        }
        int lineForOffset = layout.getLineForOffset(selectionEnd);
        if (lineForOffset > 0) {
            setSelectionAndMemory(spannable, layout, lineForOffset, selectionEnd, -1, false);
            return true;
        }
        if (selectionEnd == 0) {
            return false;
        }
        setSelection(spannable, 0);
        return true;
    }

    private static void setSelectionAndMemory(android.text.Spannable spannable, android.text.Layout layout, int i, int i2, int i3, boolean z) {
        int lineStart;
        int paragraphDirection = layout.getParagraphDirection(i);
        int i4 = i + i3;
        int i5 = -1;
        if (paragraphDirection == layout.getParagraphDirection(i4)) {
            int selectionMemory = getSelectionMemory(spannable);
            if (selectionMemory > -1) {
                lineStart = layout.getOffsetForHorizontal(i4, layout.getPrimaryHorizontal(selectionMemory));
                i2 = selectionMemory;
            } else {
                lineStart = layout.getOffsetForHorizontal(i4, layout.getPrimaryHorizontal(i2));
            }
            i5 = i2;
        } else {
            lineStart = layout.getLineStart(i4);
        }
        if (z) {
            extendSelection(spannable, lineStart, i5);
        } else {
            setSelection(spannable, lineStart, lineStart, i5);
        }
    }

    public static boolean moveDown(android.text.Spannable spannable, android.text.Layout layout) {
        int selectionStart = getSelectionStart(spannable);
        int selectionEnd = getSelectionEnd(spannable);
        if (selectionStart != selectionEnd) {
            int min = java.lang.Math.min(selectionStart, selectionEnd);
            int max = java.lang.Math.max(selectionStart, selectionEnd);
            setSelection(spannable, max);
            return (min == 0 && max == spannable.length()) ? false : true;
        }
        int lineForOffset = layout.getLineForOffset(selectionEnd);
        if (lineForOffset < layout.getLineCount() - 1) {
            setSelectionAndMemory(spannable, layout, lineForOffset, selectionEnd, 1, false);
            return true;
        }
        if (selectionEnd == spannable.length()) {
            return false;
        }
        setSelection(spannable, spannable.length());
        return true;
    }

    public static boolean moveLeft(android.text.Spannable spannable, android.text.Layout layout) {
        int selectionStart = getSelectionStart(spannable);
        int selectionEnd = getSelectionEnd(spannable);
        if (selectionStart != selectionEnd) {
            setSelection(spannable, chooseHorizontal(layout, -1, selectionStart, selectionEnd));
            return true;
        }
        int offsetToLeftOf = layout.getOffsetToLeftOf(selectionEnd);
        if (offsetToLeftOf != selectionEnd) {
            setSelection(spannable, offsetToLeftOf);
            return true;
        }
        return false;
    }

    public static boolean moveRight(android.text.Spannable spannable, android.text.Layout layout) {
        int selectionStart = getSelectionStart(spannable);
        int selectionEnd = getSelectionEnd(spannable);
        if (selectionStart != selectionEnd) {
            setSelection(spannable, chooseHorizontal(layout, 1, selectionStart, selectionEnd));
            return true;
        }
        int offsetToRightOf = layout.getOffsetToRightOf(selectionEnd);
        if (offsetToRightOf != selectionEnd) {
            setSelection(spannable, offsetToRightOf);
            return true;
        }
        return false;
    }

    public static boolean moveToParagraphStart(android.text.Spannable spannable, android.text.Layout layout) {
        int selectionStart = getSelectionStart(spannable);
        int selectionEnd = getSelectionEnd(spannable);
        if (selectionStart != selectionEnd) {
            setSelection(spannable, chooseHorizontal(layout, -1, selectionStart, selectionEnd));
            return true;
        }
        int lastIndexOf = android.text.TextUtils.lastIndexOf(spannable, PARAGRAPH_SEPARATOR, selectionStart - 1);
        if (lastIndexOf == -1) {
            lastIndexOf = 0;
        }
        if (lastIndexOf == selectionEnd) {
            return false;
        }
        setSelection(spannable, lastIndexOf);
        return true;
    }

    public static boolean moveToParagraphEnd(android.text.Spannable spannable, android.text.Layout layout) {
        int selectionStart = getSelectionStart(spannable);
        int selectionEnd = getSelectionEnd(spannable);
        if (selectionStart != selectionEnd) {
            setSelection(spannable, chooseHorizontal(layout, 1, selectionStart, selectionEnd));
            return true;
        }
        int indexOf = android.text.TextUtils.indexOf(spannable, PARAGRAPH_SEPARATOR, selectionEnd + 1);
        if (indexOf == -1) {
            indexOf = spannable.length();
        }
        if (indexOf != selectionEnd) {
            setSelection(spannable, indexOf);
            return true;
        }
        return false;
    }

    public static boolean extendToParagraphStart(android.text.Spannable spannable) {
        int selectionEnd = getSelectionEnd(spannable);
        int lastIndexOf = android.text.TextUtils.lastIndexOf(spannable, PARAGRAPH_SEPARATOR, selectionEnd - 1);
        if (lastIndexOf == -1) {
            lastIndexOf = 0;
        }
        if (lastIndexOf == selectionEnd) {
            return false;
        }
        extendSelection(spannable, lastIndexOf);
        return true;
    }

    public static boolean extendToParagraphEnd(android.text.Spannable spannable) {
        int selectionEnd = getSelectionEnd(spannable);
        int indexOf = android.text.TextUtils.indexOf(spannable, PARAGRAPH_SEPARATOR, selectionEnd + 1);
        if (indexOf == -1) {
            indexOf = spannable.length();
        }
        if (indexOf != selectionEnd) {
            extendSelection(spannable, indexOf);
            return true;
        }
        return false;
    }

    public static boolean extendUp(android.text.Spannable spannable, android.text.Layout layout) {
        int selectionEnd = getSelectionEnd(spannable);
        int lineForOffset = layout.getLineForOffset(selectionEnd);
        if (lineForOffset > 0) {
            setSelectionAndMemory(spannable, layout, lineForOffset, selectionEnd, -1, true);
            return true;
        }
        if (selectionEnd == 0) {
            return true;
        }
        extendSelection(spannable, 0);
        return true;
    }

    public static boolean extendDown(android.text.Spannable spannable, android.text.Layout layout) {
        int selectionEnd = getSelectionEnd(spannable);
        int lineForOffset = layout.getLineForOffset(selectionEnd);
        if (lineForOffset < layout.getLineCount() - 1) {
            setSelectionAndMemory(spannable, layout, lineForOffset, selectionEnd, 1, true);
            return true;
        }
        if (selectionEnd == spannable.length()) {
            return true;
        }
        extendSelection(spannable, spannable.length(), -1);
        return true;
    }

    public static boolean extendLeft(android.text.Spannable spannable, android.text.Layout layout) {
        int selectionEnd = getSelectionEnd(spannable);
        int offsetToLeftOf = layout.getOffsetToLeftOf(selectionEnd);
        if (offsetToLeftOf == selectionEnd) {
            return true;
        }
        extendSelection(spannable, offsetToLeftOf);
        return true;
    }

    public static boolean extendRight(android.text.Spannable spannable, android.text.Layout layout) {
        int selectionEnd = getSelectionEnd(spannable);
        int offsetToRightOf = layout.getOffsetToRightOf(selectionEnd);
        if (offsetToRightOf == selectionEnd) {
            return true;
        }
        extendSelection(spannable, offsetToRightOf);
        return true;
    }

    public static boolean extendToLeftEdge(android.text.Spannable spannable, android.text.Layout layout) {
        extendSelection(spannable, findEdge(spannable, layout, -1));
        return true;
    }

    public static boolean extendToRightEdge(android.text.Spannable spannable, android.text.Layout layout) {
        extendSelection(spannable, findEdge(spannable, layout, 1));
        return true;
    }

    public static boolean moveToLeftEdge(android.text.Spannable spannable, android.text.Layout layout) {
        setSelection(spannable, findEdge(spannable, layout, -1));
        return true;
    }

    public static boolean moveToRightEdge(android.text.Spannable spannable, android.text.Layout layout) {
        setSelection(spannable, findEdge(spannable, layout, 1));
        return true;
    }

    public static boolean moveToPreceding(android.text.Spannable spannable, android.text.Selection.PositionIterator positionIterator, boolean z) {
        int preceding = positionIterator.preceding(getSelectionEnd(spannable));
        if (preceding != -1) {
            if (z) {
                extendSelection(spannable, preceding);
                return true;
            }
            setSelection(spannable, preceding);
            return true;
        }
        return true;
    }

    public static boolean moveToFollowing(android.text.Spannable spannable, android.text.Selection.PositionIterator positionIterator, boolean z) {
        int following = positionIterator.following(getSelectionEnd(spannable));
        if (following != -1) {
            if (z) {
                extendSelection(spannable, following);
                return true;
            }
            setSelection(spannable, following);
            return true;
        }
        return true;
    }

    private static int findEdge(android.text.Spannable spannable, android.text.Layout layout, int i) {
        int lineForOffset = layout.getLineForOffset(getSelectionEnd(spannable));
        if (i * layout.getParagraphDirection(lineForOffset) < 0) {
            return layout.getLineStart(lineForOffset);
        }
        int lineEnd = layout.getLineEnd(lineForOffset);
        if (lineForOffset == layout.getLineCount() - 1) {
            return lineEnd;
        }
        return lineEnd - 1;
    }

    private static int chooseHorizontal(android.text.Layout layout, int i, int i2, int i3) {
        if (layout.getLineForOffset(i2) == layout.getLineForOffset(i3)) {
            float primaryHorizontal = layout.getPrimaryHorizontal(i2);
            float primaryHorizontal2 = layout.getPrimaryHorizontal(i3);
            if (i < 0) {
                if (primaryHorizontal < primaryHorizontal2) {
                    return i2;
                }
                return i3;
            }
            if (primaryHorizontal > primaryHorizontal2) {
                return i2;
            }
            return i3;
        }
        if (layout.getParagraphDirection(layout.getLineForOffset(i2)) == i) {
            return java.lang.Math.max(i2, i3);
        }
        return java.lang.Math.min(i2, i3);
    }

    private static final class START implements android.text.NoCopySpan {
        private START() {
        }
    }

    private static final class END implements android.text.NoCopySpan {
        private END() {
        }
    }

    private static final class MEMORY implements android.text.NoCopySpan {
        private MEMORY() {
        }
    }
}
