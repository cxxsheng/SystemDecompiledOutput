package android.view.inputmethod;

/* loaded from: classes4.dex */
public class BaseInputConnection implements android.view.inputmethod.InputConnection {
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "BaseInputConnection";
    private java.lang.Object[] mDefaultComposingSpans;
    android.text.Editable mEditable;
    final boolean mFallbackMode;
    protected final android.view.inputmethod.InputMethodManager mIMM;
    android.view.KeyCharacterMap mKeyCharacterMap;
    final android.view.View mTargetView;
    static final java.lang.Object COMPOSING = new android.view.inputmethod.ComposingText();
    private static int INVALID_INDEX = -1;

    BaseInputConnection(android.view.inputmethod.InputMethodManager inputMethodManager, boolean z) {
        this.mIMM = inputMethodManager;
        this.mTargetView = null;
        this.mFallbackMode = !z;
    }

    public BaseInputConnection(android.view.View view, boolean z) {
        this.mIMM = (android.view.inputmethod.InputMethodManager) view.getContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
        this.mTargetView = view;
        this.mFallbackMode = !z;
    }

    public static final void removeComposingSpans(android.text.Spannable spannable) {
        spannable.removeSpan(COMPOSING);
        java.lang.Object[] spans = spannable.getSpans(0, spannable.length(), java.lang.Object.class);
        if (spans != null) {
            for (int length = spans.length - 1; length >= 0; length--) {
                java.lang.Object obj = spans[length];
                if ((spannable.getSpanFlags(obj) & 256) != 0) {
                    spannable.removeSpan(obj);
                }
            }
        }
    }

    public static void setComposingSpans(android.text.Spannable spannable) {
        setComposingSpans(spannable, 0, spannable.length());
    }

    public static void setComposingSpans(android.text.Spannable spannable, int i, int i2) {
        java.lang.Object[] spans = spannable.getSpans(i, i2, java.lang.Object.class);
        if (spans != null) {
            for (int length = spans.length - 1; length >= 0; length--) {
                java.lang.Object obj = spans[length];
                if (obj == COMPOSING) {
                    spannable.removeSpan(obj);
                } else {
                    int spanFlags = spannable.getSpanFlags(obj);
                    if ((spanFlags & 307) != 289) {
                        spannable.setSpan(obj, spannable.getSpanStart(obj), spannable.getSpanEnd(obj), (spanFlags & (-52)) | 256 | 33);
                    }
                }
            }
        }
        spannable.setSpan(COMPOSING, i, i2, 289);
    }

    public static int getComposingSpanStart(android.text.Spannable spannable) {
        return spannable.getSpanStart(COMPOSING);
    }

    public static int getComposingSpanEnd(android.text.Spannable spannable) {
        return spannable.getSpanEnd(COMPOSING);
    }

    public android.text.Editable getEditable() {
        if (this.mEditable == null) {
            this.mEditable = android.text.Editable.Factory.getInstance().newEditable("");
            android.text.Selection.setSelection(this.mEditable, 0);
        }
        return this.mEditable;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean beginBatchEdit() {
        return false;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean endBatchEdit() {
        return false;
    }

    public void endComposingRegionEditInternal() {
    }

    @Override // android.view.inputmethod.InputConnection
    public void closeConnection() {
        finishComposingText();
        setImeConsumesInput(false);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean clearMetaKeyStates(int i) {
        android.text.Editable editable = getEditable();
        if (editable == null) {
            return false;
        }
        android.text.method.MetaKeyKeyListener.clearMetaKeyState(editable, i);
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitCompletion(android.view.inputmethod.CompletionInfo completionInfo) {
        return false;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitCorrection(android.view.inputmethod.CorrectionInfo correctionInfo) {
        return false;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitText(java.lang.CharSequence charSequence, int i) {
        replaceText(charSequence, i, false);
        sendCurrentText();
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean deleteSurroundingText(int i, int i2) {
        android.text.Editable editable = getEditable();
        int i3 = 0;
        if (editable == null) {
            return false;
        }
        beginBatchEdit();
        int selectionStart = android.text.Selection.getSelectionStart(editable);
        int selectionEnd = android.text.Selection.getSelectionEnd(editable);
        if (selectionStart > selectionEnd) {
            selectionEnd = selectionStart;
            selectionStart = selectionEnd;
        }
        if (selectionStart == -1 || selectionEnd == -1) {
            endBatchEdit();
            return false;
        }
        int composingSpanStart = getComposingSpanStart(editable);
        int composingSpanEnd = getComposingSpanEnd(editable);
        if (composingSpanEnd < composingSpanStart) {
            composingSpanEnd = composingSpanStart;
            composingSpanStart = composingSpanEnd;
        }
        if (composingSpanStart != -1 && composingSpanEnd != -1) {
            if (composingSpanStart < selectionStart) {
                selectionStart = composingSpanStart;
            }
            if (composingSpanEnd > selectionEnd) {
                selectionEnd = composingSpanEnd;
            }
        }
        if (i > 0) {
            int i4 = selectionStart - i;
            if (i4 < 0) {
                i4 = 0;
            }
            int i5 = selectionStart - i4;
            if (selectionStart >= 0 && i5 > 0) {
                editable.delete(i4, selectionStart);
                i3 = i5;
            }
        }
        if (i2 > 0) {
            int i6 = selectionEnd - i3;
            int i7 = i2 + i6;
            if (i7 > editable.length()) {
                i7 = editable.length();
            }
            int i8 = i7 - i6;
            if (i6 >= 0 && i8 > 0) {
                editable.delete(i6, i7);
            }
        }
        endBatchEdit();
        return true;
    }

    private static int findIndexBackward(java.lang.CharSequence charSequence, int i, int i2) {
        int length = charSequence.length();
        if (i < 0 || length < i) {
            return INVALID_INDEX;
        }
        if (i2 < 0) {
            return INVALID_INDEX;
        }
        boolean z = false;
        while (i2 != 0) {
            i--;
            if (i < 0) {
                if (!z) {
                    return 0;
                }
                return INVALID_INDEX;
            }
            char charAt = charSequence.charAt(i);
            if (z) {
                if (!java.lang.Character.isHighSurrogate(charAt)) {
                    return INVALID_INDEX;
                }
                i2--;
                z = false;
            } else if (!java.lang.Character.isSurrogate(charAt)) {
                i2--;
            } else {
                if (java.lang.Character.isHighSurrogate(charAt)) {
                    return INVALID_INDEX;
                }
                z = true;
            }
        }
        return i;
    }

    private static int findIndexForward(java.lang.CharSequence charSequence, int i, int i2) {
        int length = charSequence.length();
        if (i < 0 || length < i) {
            return INVALID_INDEX;
        }
        if (i2 < 0) {
            return INVALID_INDEX;
        }
        boolean z = false;
        while (i2 != 0) {
            if (i >= length) {
                if (z) {
                    return INVALID_INDEX;
                }
                return length;
            }
            char charAt = charSequence.charAt(i);
            if (z) {
                if (!java.lang.Character.isLowSurrogate(charAt)) {
                    return INVALID_INDEX;
                }
                i2--;
                i++;
                z = false;
            } else if (!java.lang.Character.isSurrogate(charAt)) {
                i2--;
                i++;
            } else {
                if (java.lang.Character.isLowSurrogate(charAt)) {
                    return INVALID_INDEX;
                }
                i++;
                z = true;
            }
        }
        return i;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean deleteSurroundingTextInCodePoints(int i, int i2) {
        int findIndexBackward;
        int findIndexForward;
        android.text.Editable editable = getEditable();
        if (editable == null) {
            return false;
        }
        beginBatchEdit();
        int selectionStart = android.text.Selection.getSelectionStart(editable);
        int selectionEnd = android.text.Selection.getSelectionEnd(editable);
        if (selectionStart > selectionEnd) {
            selectionEnd = selectionStart;
            selectionStart = selectionEnd;
        }
        int composingSpanStart = getComposingSpanStart(editable);
        int composingSpanEnd = getComposingSpanEnd(editable);
        if (composingSpanEnd < composingSpanStart) {
            composingSpanEnd = composingSpanStart;
            composingSpanStart = composingSpanEnd;
        }
        if (composingSpanStart != -1 && composingSpanEnd != -1) {
            if (composingSpanStart < selectionStart) {
                selectionStart = composingSpanStart;
            }
            if (composingSpanEnd > selectionEnd) {
                selectionEnd = composingSpanEnd;
            }
        }
        if (selectionStart >= 0 && selectionEnd >= 0 && (findIndexBackward = findIndexBackward(editable, selectionStart, java.lang.Math.max(i, 0))) != INVALID_INDEX && (findIndexForward = findIndexForward(editable, selectionEnd, java.lang.Math.max(i2, 0))) != INVALID_INDEX) {
            int i3 = selectionStart - findIndexBackward;
            if (i3 > 0) {
                editable.delete(findIndexBackward, selectionStart);
            }
            if (findIndexForward - selectionEnd > 0) {
                editable.delete(selectionEnd - i3, findIndexForward - i3);
            }
        }
        endBatchEdit();
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean finishComposingText() {
        android.text.Editable editable = getEditable();
        if (editable != null) {
            beginBatchEdit();
            removeComposingSpans(editable);
            sendCurrentText();
            endBatchEdit();
            endComposingRegionEditInternal();
            return true;
        }
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public int getCursorCapsMode(int i) {
        android.text.Editable editable;
        if (this.mFallbackMode || (editable = getEditable()) == null) {
            return 0;
        }
        int selectionStart = android.text.Selection.getSelectionStart(editable);
        int selectionEnd = android.text.Selection.getSelectionEnd(editable);
        if (selectionStart > selectionEnd) {
            selectionStart = selectionEnd;
        }
        return android.text.TextUtils.getCapsMode(editable, selectionStart, i);
    }

    @Override // android.view.inputmethod.InputConnection
    public android.view.inputmethod.ExtractedText getExtractedText(android.view.inputmethod.ExtractedTextRequest extractedTextRequest, int i) {
        return null;
    }

    @Override // android.view.inputmethod.InputConnection
    public java.lang.CharSequence getTextBeforeCursor(int i, int i2) {
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i);
        android.text.Editable editable = getEditable();
        if (editable == null) {
            return null;
        }
        int selectionStart = android.text.Selection.getSelectionStart(editable);
        int selectionEnd = android.text.Selection.getSelectionEnd(editable);
        if (selectionStart > selectionEnd) {
            selectionStart = selectionEnd;
        }
        if (selectionStart <= 0) {
            return "";
        }
        if (i > selectionStart) {
            i = selectionStart;
        }
        if ((i2 & 1) != 0) {
            return editable.subSequence(selectionStart - i, selectionStart);
        }
        return android.text.TextUtils.substring(editable, selectionStart - i, selectionStart);
    }

    @Override // android.view.inputmethod.InputConnection
    public java.lang.CharSequence getSelectedText(int i) {
        android.text.Editable editable = getEditable();
        if (editable == null) {
            return null;
        }
        int selectionStart = android.text.Selection.getSelectionStart(editable);
        int selectionEnd = android.text.Selection.getSelectionEnd(editable);
        if (selectionStart > selectionEnd) {
            selectionEnd = selectionStart;
            selectionStart = selectionEnd;
        }
        if (selectionStart == selectionEnd || selectionStart < 0) {
            return null;
        }
        if ((i & 1) != 0) {
            return editable.subSequence(selectionStart, selectionEnd);
        }
        return android.text.TextUtils.substring(editable, selectionStart, selectionEnd);
    }

    @Override // android.view.inputmethod.InputConnection
    public java.lang.CharSequence getTextAfterCursor(int i, int i2) {
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i);
        android.text.Editable editable = getEditable();
        if (editable == null) {
            return null;
        }
        int selectionStart = android.text.Selection.getSelectionStart(editable);
        int selectionEnd = android.text.Selection.getSelectionEnd(editable);
        if (selectionStart <= selectionEnd) {
            selectionStart = selectionEnd;
        }
        if (selectionStart < 0) {
            selectionStart = 0;
        }
        int min = (int) java.lang.Math.min(selectionStart + i, editable.length());
        if ((i2 & 1) != 0) {
            return editable.subSequence(selectionStart, min);
        }
        return android.text.TextUtils.substring(editable, selectionStart, min);
    }

    @Override // android.view.inputmethod.InputConnection
    public android.view.inputmethod.SurroundingText getSurroundingText(int i, int i2, int i3) {
        java.lang.CharSequence substring;
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i);
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i2);
        android.text.Editable editable = getEditable();
        if (editable == null || this.mEditable == editable) {
            return super.getSurroundingText(i, i2, i3);
        }
        int selectionStart = android.text.Selection.getSelectionStart(editable);
        int selectionEnd = android.text.Selection.getSelectionEnd(editable);
        if (selectionStart < 0 || selectionEnd < 0) {
            return null;
        }
        if (selectionStart > selectionEnd) {
            selectionEnd = selectionStart;
            selectionStart = selectionEnd;
        }
        int max = java.lang.Math.max(0, selectionStart - i);
        int min = (int) java.lang.Math.min(selectionEnd + i2, editable.length());
        if ((i3 & 1) != 0) {
            substring = editable.subSequence(max, min);
        } else {
            substring = android.text.TextUtils.substring(editable, max, min);
        }
        return new android.view.inputmethod.SurroundingText(substring, selectionStart - max, selectionEnd - max, max);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean performEditorAction(int i) {
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        sendKeyEvent(new android.view.KeyEvent(uptimeMillis, uptimeMillis, 0, 66, 0, 0, -1, 0, 22));
        sendKeyEvent(new android.view.KeyEvent(android.os.SystemClock.uptimeMillis(), uptimeMillis, 1, 66, 0, 0, -1, 0, 22));
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean performContextMenuAction(int i) {
        return false;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean performPrivateCommand(java.lang.String str, android.os.Bundle bundle) {
        return false;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean requestCursorUpdates(int i) {
        return false;
    }

    @Override // android.view.inputmethod.InputConnection
    public android.os.Handler getHandler() {
        return null;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setComposingText(java.lang.CharSequence charSequence, int i) {
        replaceText(charSequence, i, true);
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setComposingRegion(int i, int i2) {
        android.text.Editable editable = getEditable();
        if (editable != null) {
            beginBatchEdit();
            removeComposingSpans(editable);
            if (i > i2) {
                i2 = i;
                i = i2;
            }
            int length = editable.length();
            if (i < 0) {
                i = 0;
            }
            if (i2 < 0) {
                i2 = 0;
            }
            if (i > length) {
                i = length;
            }
            if (i2 <= length) {
                length = i2;
            }
            ensureDefaultComposingSpans();
            if (this.mDefaultComposingSpans != null) {
                for (int i3 = 0; i3 < this.mDefaultComposingSpans.length; i3++) {
                    editable.setSpan(this.mDefaultComposingSpans[i3], i, length, 289);
                }
            }
            editable.setSpan(COMPOSING, i, length, 289);
            sendCurrentText();
            endBatchEdit();
            endComposingRegionEditInternal();
            return true;
        }
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setSelection(int i, int i2) {
        android.text.Editable editable = getEditable();
        if (editable == null) {
            return false;
        }
        int length = editable.length();
        if (i > length || i2 > length || i < 0 || i2 < 0) {
            return true;
        }
        if (i == i2 && android.text.method.MetaKeyKeyListener.getMetaState(editable, 2048) != 0) {
            android.text.Selection.extendSelection(editable, i);
        } else {
            android.text.Selection.setSelection(editable, i, i2);
        }
        return true;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean sendKeyEvent(android.view.KeyEvent keyEvent) {
        this.mIMM.dispatchKeyEventFromInputMethod(this.mTargetView, keyEvent);
        return false;
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean reportFullscreenMode(boolean z) {
        return true;
    }

    private void sendCurrentText() {
        android.text.Editable editable;
        int length;
        if (!this.mFallbackMode || (editable = getEditable()) == null || (length = editable.length()) == 0) {
            return;
        }
        if (length == 1) {
            if (this.mKeyCharacterMap == null) {
                this.mKeyCharacterMap = android.view.KeyCharacterMap.load(-1);
            }
            char[] cArr = new char[1];
            editable.getChars(0, 1, cArr, 0);
            android.view.KeyEvent[] events = this.mKeyCharacterMap.getEvents(cArr);
            if (events != null) {
                for (android.view.KeyEvent keyEvent : events) {
                    sendKeyEvent(keyEvent);
                }
                editable.clear();
                return;
            }
        }
        sendKeyEvent(new android.view.KeyEvent(android.os.SystemClock.uptimeMillis(), editable.toString(), -1, 0));
        editable.clear();
    }

    private void ensureDefaultComposingSpans() {
        android.content.Context fallbackContextFromServedView;
        if (this.mDefaultComposingSpans == null) {
            if (this.mTargetView != null) {
                fallbackContextFromServedView = this.mTargetView.getContext();
            } else {
                fallbackContextFromServedView = this.mIMM.getFallbackContextFromServedView();
            }
            if (fallbackContextFromServedView != null) {
                android.content.res.TypedArray obtainStyledAttributes = fallbackContextFromServedView.getTheme().obtainStyledAttributes(new int[]{16843312});
                java.lang.CharSequence text = obtainStyledAttributes.getText(0);
                obtainStyledAttributes.recycle();
                if (text != null && (text instanceof android.text.Spanned)) {
                    this.mDefaultComposingSpans = ((android.text.Spanned) text).getSpans(0, text.length(), java.lang.Object.class);
                }
            }
        }
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean replaceText(int i, int i2, java.lang.CharSequence charSequence, int i3, android.view.inputmethod.TextAttribute textAttribute) {
        int i4;
        int i5;
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i);
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i2);
        android.text.Editable editable = getEditable();
        if (editable == null) {
            return false;
        }
        beginBatchEdit();
        removeComposingSpans(editable);
        int length = editable.length();
        int min = java.lang.Math.min(i, length);
        int min2 = java.lang.Math.min(i2, length);
        if (min2 >= min) {
            i4 = min;
            i5 = min2;
        } else {
            i5 = min;
            i4 = min2;
        }
        replaceTextInternal(i4, i5, charSequence, i3, false);
        endBatchEdit();
        return true;
    }

    private void replaceText(java.lang.CharSequence charSequence, int i, boolean z) {
        int i2;
        int i3;
        android.text.Editable editable = getEditable();
        if (editable == null) {
            return;
        }
        beginBatchEdit();
        int composingSpanStart = getComposingSpanStart(editable);
        int composingSpanEnd = getComposingSpanEnd(editable);
        if (composingSpanEnd < composingSpanStart) {
            composingSpanEnd = composingSpanStart;
            composingSpanStart = composingSpanEnd;
        }
        if (composingSpanStart != -1 && composingSpanEnd != -1) {
            removeComposingSpans(editable);
            i3 = composingSpanStart;
            i2 = composingSpanEnd;
        } else {
            int selectionStart = android.text.Selection.getSelectionStart(editable);
            int selectionEnd = android.text.Selection.getSelectionEnd(editable);
            if (selectionStart < 0) {
                selectionStart = 0;
            }
            if (selectionEnd < 0) {
                selectionEnd = 0;
            }
            if (selectionEnd >= selectionStart) {
                i2 = selectionEnd;
                i3 = selectionStart;
            } else {
                i3 = selectionEnd;
                i2 = selectionStart;
            }
        }
        replaceTextInternal(i3, i2, charSequence, i, z);
        endBatchEdit();
    }

    private void replaceTextInternal(int i, int i2, java.lang.CharSequence charSequence, int i3, boolean z) {
        int i4;
        android.text.Spannable spannable;
        android.text.Editable editable = getEditable();
        if (editable == null) {
            return;
        }
        if (z) {
            if (!(charSequence instanceof android.text.Spannable)) {
                spannable = new android.text.SpannableStringBuilder(charSequence);
                ensureDefaultComposingSpans();
                if (this.mDefaultComposingSpans != null) {
                    for (int i5 = 0; i5 < this.mDefaultComposingSpans.length; i5++) {
                        spannable.setSpan(this.mDefaultComposingSpans[i5], 0, spannable.length(), 289);
                    }
                }
                charSequence = spannable;
            } else {
                spannable = (android.text.Spannable) charSequence;
            }
            setComposingSpans(spannable);
        }
        if (i3 > 0) {
            i4 = (i2 - 1) + i3;
        } else {
            i4 = i3 + i;
        }
        int i6 = i4 >= 0 ? i4 : 0;
        if (i6 > editable.length()) {
            i6 = editable.length();
        }
        android.text.Selection.setSelection(editable, i6);
        editable.replace(i, i2, charSequence);
        if (i3 == 0 && i == i2) {
            android.text.Selection.setSelection(editable, i6);
        }
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean commitContent(android.view.inputmethod.InputContentInfo inputContentInfo, int i, android.os.Bundle bundle) {
        if (this.mTargetView == null) {
            return false;
        }
        inputContentInfo.getDescription();
        if (this.mTargetView.getReceiveContentMimeTypes() == null) {
            return false;
        }
        if ((i & 1) != 0) {
            try {
                inputContentInfo.requestPermission();
            } catch (java.lang.Exception e) {
                android.util.Log.w(TAG, "Can't insert content from IME; requestPermission() failed", e);
                return false;
            }
        }
        return this.mTargetView.performReceiveContent(new android.view.ContentInfo.Builder(new android.content.ClipData(inputContentInfo.getDescription(), new android.content.ClipData.Item(inputContentInfo.getContentUri())), 2).setLinkUri(inputContentInfo.getLinkUri()).setExtras(bundle).setInputContentInfo(inputContentInfo).build()) == null;
    }

    @Override // android.view.inputmethod.InputConnection
    public android.view.inputmethod.TextSnapshot takeSnapshot() {
        android.text.Editable editable = getEditable();
        if (editable == null) {
            return null;
        }
        int composingSpanStart = getComposingSpanStart(editable);
        int composingSpanEnd = getComposingSpanEnd(editable);
        if (composingSpanEnd < composingSpanStart) {
            composingSpanStart = composingSpanEnd;
            composingSpanEnd = composingSpanStart;
        }
        android.view.inputmethod.SurroundingText surroundingText = getSurroundingText(1024, 1024, 1);
        if (surroundingText == null) {
            return null;
        }
        return new android.view.inputmethod.TextSnapshot(surroundingText, composingSpanStart, composingSpanEnd, getCursorCapsMode(28672));
    }
}
