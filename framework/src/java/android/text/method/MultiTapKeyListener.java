package android.text.method;

/* loaded from: classes3.dex */
public class MultiTapKeyListener extends android.text.method.BaseKeyListener implements android.text.SpanWatcher {
    private static android.text.method.MultiTapKeyListener[] sInstance = new android.text.method.MultiTapKeyListener[android.text.method.TextKeyListener.Capitalize.values().length * 2];
    private static final android.util.SparseArray<java.lang.String> sRecs = new android.util.SparseArray<>();
    private boolean mAutoText;
    private android.text.method.TextKeyListener.Capitalize mCapitalize;

    static {
        sRecs.put(8, ".,1!@#$%^&*:/?'=()");
        sRecs.put(9, "abc2ABC");
        sRecs.put(10, "def3DEF");
        sRecs.put(11, "ghi4GHI");
        sRecs.put(12, "jkl5JKL");
        sRecs.put(13, "mno6MNO");
        sRecs.put(14, "pqrs7PQRS");
        sRecs.put(15, "tuv8TUV");
        sRecs.put(16, "wxyz9WXYZ");
        sRecs.put(7, "0+");
        sRecs.put(18, " ");
    }

    public MultiTapKeyListener(android.text.method.TextKeyListener.Capitalize capitalize, boolean z) {
        this.mCapitalize = capitalize;
        this.mAutoText = z;
    }

    public static android.text.method.MultiTapKeyListener getInstance(boolean z, android.text.method.TextKeyListener.Capitalize capitalize) {
        int ordinal = (capitalize.ordinal() * 2) + (z ? 1 : 0);
        if (sInstance[ordinal] == null) {
            sInstance[ordinal] = new android.text.method.MultiTapKeyListener(capitalize, z);
        }
        return sInstance[ordinal];
    }

    @Override // android.text.method.KeyListener
    public int getInputType() {
        return makeTextContentType(this.mCapitalize, this.mAutoText);
    }

    @Override // android.text.method.BaseKeyListener, android.text.method.MetaKeyKeyListener, android.text.method.KeyListener
    public boolean onKeyDown(android.view.View view, android.text.Editable editable, int i, android.view.KeyEvent keyEvent) {
        int i2;
        int indexOfKey;
        int i3;
        java.lang.String valueAt;
        int indexOf;
        if (view == null) {
            i2 = 0;
        } else {
            i2 = android.text.method.TextKeyListener.getInstance().getPrefs(view.getContext());
        }
        int selectionStart = android.text.Selection.getSelectionStart(editable);
        int selectionEnd = android.text.Selection.getSelectionEnd(editable);
        int min = java.lang.Math.min(selectionStart, selectionEnd);
        int max = java.lang.Math.max(selectionStart, selectionEnd);
        int spanStart = editable.getSpanStart(android.text.method.TextKeyListener.ACTIVE);
        int spanEnd = editable.getSpanEnd(android.text.method.TextKeyListener.ACTIVE);
        int spanFlags = (editable.getSpanFlags(android.text.method.TextKeyListener.ACTIVE) & (-16777216)) >>> 24;
        if (spanStart == min && spanEnd == max && max - min == 1 && spanFlags >= 0 && spanFlags < sRecs.size()) {
            if (i == 17) {
                char charAt = editable.charAt(min);
                if (java.lang.Character.isLowerCase(charAt)) {
                    editable.replace(min, max, java.lang.String.valueOf(charAt).toUpperCase());
                    removeTimeouts(editable);
                    new android.text.method.MultiTapKeyListener.Timeout(editable);
                    return true;
                }
                if (java.lang.Character.isUpperCase(charAt)) {
                    editable.replace(min, max, java.lang.String.valueOf(charAt).toLowerCase());
                    removeTimeouts(editable);
                    new android.text.method.MultiTapKeyListener.Timeout(editable);
                    return true;
                }
            }
            if (sRecs.indexOfKey(i) == spanFlags && (indexOf = (valueAt = sRecs.valueAt(spanFlags)).indexOf(editable.charAt(min))) >= 0) {
                int length = (indexOf + 1) % valueAt.length();
                editable.replace(min, max, valueAt, length, length + 1);
                removeTimeouts(editable);
                new android.text.method.MultiTapKeyListener.Timeout(editable);
                return true;
            }
            int indexOfKey2 = sRecs.indexOfKey(i);
            if (indexOfKey2 < 0) {
                indexOfKey = indexOfKey2;
            } else {
                android.text.Selection.setSelection(editable, max, max);
                min = max;
                indexOfKey = indexOfKey2;
            }
        } else {
            indexOfKey = sRecs.indexOfKey(i);
        }
        if (indexOfKey >= 0) {
            java.lang.String valueAt2 = sRecs.valueAt(indexOfKey);
            if ((i2 & 1) != 0 && android.text.method.TextKeyListener.shouldCap(this.mCapitalize, editable, min)) {
                for (int i4 = 0; i4 < valueAt2.length(); i4++) {
                    if (java.lang.Character.isUpperCase(valueAt2.charAt(i4))) {
                        i3 = i4;
                        break;
                    }
                }
            }
            i3 = 0;
            if (min != max) {
                android.text.Selection.setSelection(editable, max);
            }
            editable.setSpan(OLD_SEL_START, min, min, 17);
            editable.replace(min, max, valueAt2, i3, i3 + 1);
            int spanStart2 = editable.getSpanStart(OLD_SEL_START);
            int selectionEnd2 = android.text.Selection.getSelectionEnd(editable);
            if (selectionEnd2 != spanStart2) {
                android.text.Selection.setSelection(editable, spanStart2, selectionEnd2);
                editable.setSpan(android.text.method.TextKeyListener.LAST_TYPED, spanStart2, selectionEnd2, 33);
                editable.setSpan(android.text.method.TextKeyListener.ACTIVE, spanStart2, selectionEnd2, 33 | (indexOfKey << 24));
            }
            removeTimeouts(editable);
            new android.text.method.MultiTapKeyListener.Timeout(editable);
            if (editable.getSpanStart(this) < 0) {
                for (java.lang.Object obj : (android.text.method.KeyListener[]) editable.getSpans(0, editable.length(), android.text.method.KeyListener.class)) {
                    editable.removeSpan(obj);
                }
                editable.setSpan(this, 0, editable.length(), 18);
            }
            return true;
        }
        return super.onKeyDown(view, editable, i, keyEvent);
    }

    @Override // android.text.SpanWatcher
    public void onSpanChanged(android.text.Spannable spannable, java.lang.Object obj, int i, int i2, int i3, int i4) {
        if (obj == android.text.Selection.SELECTION_END) {
            spannable.removeSpan(android.text.method.TextKeyListener.ACTIVE);
            removeTimeouts(spannable);
        }
    }

    private static void removeTimeouts(android.text.Spannable spannable) {
        for (android.text.method.MultiTapKeyListener.Timeout timeout : (android.text.method.MultiTapKeyListener.Timeout[]) spannable.getSpans(0, spannable.length(), android.text.method.MultiTapKeyListener.Timeout.class)) {
            timeout.removeCallbacks(timeout);
            timeout.mBuffer = null;
            spannable.removeSpan(timeout);
        }
    }

    private class Timeout extends android.os.Handler implements java.lang.Runnable {
        private android.text.Editable mBuffer;

        public Timeout(android.text.Editable editable) {
            this.mBuffer = editable;
            this.mBuffer.setSpan(this, 0, this.mBuffer.length(), 18);
            postAtTime(this, android.os.SystemClock.uptimeMillis() + 2000);
        }

        @Override // java.lang.Runnable
        public void run() {
            android.text.Editable editable = this.mBuffer;
            if (editable != null) {
                int selectionStart = android.text.Selection.getSelectionStart(editable);
                int selectionEnd = android.text.Selection.getSelectionEnd(editable);
                int spanStart = editable.getSpanStart(android.text.method.TextKeyListener.ACTIVE);
                int spanEnd = editable.getSpanEnd(android.text.method.TextKeyListener.ACTIVE);
                if (selectionStart == spanStart && selectionEnd == spanEnd) {
                    android.text.Selection.setSelection(editable, android.text.Selection.getSelectionEnd(editable));
                }
                editable.removeSpan(this);
            }
        }
    }

    @Override // android.text.SpanWatcher
    public void onSpanAdded(android.text.Spannable spannable, java.lang.Object obj, int i, int i2) {
    }

    @Override // android.text.SpanWatcher
    public void onSpanRemoved(android.text.Spannable spannable, java.lang.Object obj, int i, int i2) {
    }
}
