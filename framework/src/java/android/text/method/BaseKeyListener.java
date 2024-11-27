package android.text.method;

/* loaded from: classes3.dex */
public abstract class BaseKeyListener extends android.text.method.MetaKeyKeyListener implements android.text.method.KeyListener {
    private static final int CARRIAGE_RETURN = 13;
    private static final int LINE_FEED = 10;
    static final java.lang.Object OLD_SEL_START = new android.text.NoCopySpan.Concrete();
    static android.graphics.Paint sCachedPaint = null;
    private final java.lang.Object mLock = new java.lang.Object();

    public boolean backspace(android.view.View view, android.text.Editable editable, int i, android.view.KeyEvent keyEvent) {
        return backspaceOrForwardDelete(view, editable, i, keyEvent, false);
    }

    public boolean forwardDelete(android.view.View view, android.text.Editable editable, int i, android.view.KeyEvent keyEvent) {
        return backspaceOrForwardDelete(view, editable, i, keyEvent, true);
    }

    private static boolean isVariationSelector(int i) {
        return android.icu.lang.UCharacter.hasBinaryProperty(i, 36);
    }

    private static int adjustReplacementSpan(java.lang.CharSequence charSequence, int i, boolean z) {
        if (!(charSequence instanceof android.text.Spanned)) {
            return i;
        }
        android.text.Spanned spanned = (android.text.Spanned) charSequence;
        android.text.style.ReplacementSpan[] replacementSpanArr = (android.text.style.ReplacementSpan[]) spanned.getSpans(i, i, android.text.style.ReplacementSpan.class);
        for (int i2 = 0; i2 < replacementSpanArr.length; i2++) {
            int spanStart = spanned.getSpanStart(replacementSpanArr[i2]);
            int spanEnd = spanned.getSpanEnd(replacementSpanArr[i2]);
            if (spanStart < i && spanEnd > i) {
                if (!z) {
                    spanStart = spanEnd;
                }
                i = spanStart;
            }
        }
        return i;
    }

    private static int getOffsetForBackspaceKey(java.lang.CharSequence charSequence, int i) {
        if (i <= 1) {
            return 0;
        }
        int i2 = i;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        do {
            int codePointBefore = java.lang.Character.codePointBefore(charSequence, i2);
            i2 -= java.lang.Character.charCount(codePointBefore);
            switch (i3) {
                case 0:
                    i4 = java.lang.Character.charCount(codePointBefore);
                    if (codePointBefore == 10) {
                        i3 = 1;
                        break;
                    } else if (isVariationSelector(codePointBefore)) {
                        i3 = 6;
                        break;
                    } else if (android.text.Emoji.isRegionalIndicatorSymbol(codePointBefore)) {
                        i3 = 10;
                        break;
                    } else if (android.text.Emoji.isEmojiModifier(codePointBefore)) {
                        i3 = 4;
                        break;
                    } else if (codePointBefore == android.text.Emoji.COMBINING_ENCLOSING_KEYCAP) {
                        i3 = 2;
                        break;
                    } else if (android.text.Emoji.isEmoji(codePointBefore)) {
                        i3 = 7;
                        break;
                    } else if (codePointBefore == android.text.Emoji.CANCEL_TAG) {
                        i3 = 12;
                        break;
                    } else {
                        i3 = 13;
                        break;
                    }
                case 1:
                    if (codePointBefore == 13) {
                        i4++;
                    }
                    i3 = 13;
                    break;
                case 2:
                    if (isVariationSelector(codePointBefore)) {
                        i5 = java.lang.Character.charCount(codePointBefore);
                        i3 = 3;
                        break;
                    } else {
                        if (android.text.Emoji.isKeycapBase(codePointBefore)) {
                            i4 += java.lang.Character.charCount(codePointBefore);
                        }
                        i3 = 13;
                        break;
                    }
                case 3:
                    if (android.text.Emoji.isKeycapBase(codePointBefore)) {
                        i4 += java.lang.Character.charCount(codePointBefore) + i5;
                    }
                    i3 = 13;
                    break;
                case 4:
                    if (isVariationSelector(codePointBefore)) {
                        i5 = java.lang.Character.charCount(codePointBefore);
                        i3 = 5;
                        break;
                    } else if (android.text.Emoji.isEmojiModifierBase(codePointBefore)) {
                        i4 += java.lang.Character.charCount(codePointBefore);
                        i3 = 7;
                        break;
                    } else {
                        i3 = 13;
                        break;
                    }
                case 5:
                    if (android.text.Emoji.isEmojiModifierBase(codePointBefore)) {
                        i4 += java.lang.Character.charCount(codePointBefore) + i5;
                    }
                    i3 = 13;
                    break;
                case 6:
                    if (android.text.Emoji.isEmoji(codePointBefore)) {
                        i4 += java.lang.Character.charCount(codePointBefore);
                        i3 = 7;
                        break;
                    } else {
                        if (!isVariationSelector(codePointBefore) && android.icu.lang.UCharacter.getCombiningClass(codePointBefore) == 0) {
                            i4 += java.lang.Character.charCount(codePointBefore);
                        }
                        i3 = 13;
                        break;
                    }
                    break;
                case 7:
                    if (codePointBefore == android.text.Emoji.ZERO_WIDTH_JOINER) {
                        i3 = 8;
                        break;
                    } else {
                        i3 = 13;
                        break;
                    }
                case 8:
                    if (android.text.Emoji.isEmoji(codePointBefore)) {
                        i4 += java.lang.Character.charCount(codePointBefore) + 1;
                        if (!android.text.Emoji.isEmojiModifier(codePointBefore)) {
                            i3 = 7;
                            break;
                        } else {
                            i3 = 4;
                            break;
                        }
                    } else if (isVariationSelector(codePointBefore)) {
                        i5 = java.lang.Character.charCount(codePointBefore);
                        i3 = 9;
                        break;
                    } else {
                        i3 = 13;
                        break;
                    }
                case 9:
                    if (android.text.Emoji.isEmoji(codePointBefore)) {
                        i4 += i5 + 1 + java.lang.Character.charCount(codePointBefore);
                        i5 = 0;
                        i3 = 7;
                        break;
                    } else {
                        i3 = 13;
                        break;
                    }
                case 10:
                    if (android.text.Emoji.isRegionalIndicatorSymbol(codePointBefore)) {
                        i4 += 2;
                        i3 = 11;
                        break;
                    } else {
                        i3 = 13;
                        break;
                    }
                case 11:
                    if (android.text.Emoji.isRegionalIndicatorSymbol(codePointBefore)) {
                        i4 -= 2;
                        i3 = 10;
                        break;
                    } else {
                        i3 = 13;
                        break;
                    }
                case 12:
                    if (android.text.Emoji.isTagSpecChar(codePointBefore)) {
                        i4 += 2;
                        break;
                    } else if (android.text.Emoji.isEmoji(codePointBefore)) {
                        i4 += java.lang.Character.charCount(codePointBefore);
                        i3 = 13;
                        break;
                    } else {
                        i4 = 2;
                        i3 = 13;
                        break;
                    }
                default:
                    throw new java.lang.IllegalArgumentException("state " + i3 + " is unknown");
            }
            if (i2 > 0) {
            }
            return adjustReplacementSpan(charSequence, i - i4, true);
        } while (i3 != 13);
        return adjustReplacementSpan(charSequence, i - i4, true);
    }

    private static int getOffsetForForwardDeleteKey(java.lang.CharSequence charSequence, int i, android.graphics.Paint paint) {
        int length = charSequence.length();
        if (i >= length - 1) {
            return length;
        }
        return adjustReplacementSpan(charSequence, paint.getTextRunCursor(charSequence, i, length, false, i, 0), false);
    }

    private boolean backspaceOrForwardDelete(android.view.View view, android.text.Editable editable, int i, android.view.KeyEvent keyEvent, boolean z) {
        int offsetForBackspaceKey;
        android.graphics.Paint paint;
        android.graphics.Paint paint2;
        if (!android.view.KeyEvent.metaStateHasNoModifiers(keyEvent.getMetaState() & (-28916))) {
            return false;
        }
        if (deleteSelection(view, editable)) {
            return true;
        }
        boolean z2 = (keyEvent.getMetaState() & 4096) != 0;
        boolean z3 = getMetaState(editable, 1, keyEvent) == 1;
        boolean z4 = getMetaState(editable, 2, keyEvent) == 1;
        if (z2) {
            if (z4 || z3) {
                return false;
            }
            return deleteUntilWordBoundary(view, editable, z);
        }
        if (z4 && deleteLineFromCursor(view, editable, z)) {
            return true;
        }
        int selectionEnd = android.text.Selection.getSelectionEnd(editable);
        if (z) {
            if (view instanceof android.widget.TextView) {
                paint2 = ((android.widget.TextView) view).getPaint();
            } else {
                synchronized (this.mLock) {
                    if (sCachedPaint == null) {
                        sCachedPaint = new android.graphics.Paint();
                    }
                    paint = sCachedPaint;
                }
                paint2 = paint;
            }
            offsetForBackspaceKey = getOffsetForForwardDeleteKey(editable, selectionEnd, paint2);
        } else {
            offsetForBackspaceKey = getOffsetForBackspaceKey(editable, selectionEnd);
        }
        if (selectionEnd == offsetForBackspaceKey) {
            return false;
        }
        editable.delete(java.lang.Math.min(selectionEnd, offsetForBackspaceKey), java.lang.Math.max(selectionEnd, offsetForBackspaceKey));
        return true;
    }

    private boolean deleteUntilWordBoundary(android.view.View view, android.text.Editable editable, boolean z) {
        android.text.method.WordIterator wordIterator;
        int i;
        int selectionStart = android.text.Selection.getSelectionStart(editable);
        if (selectionStart != android.text.Selection.getSelectionEnd(editable)) {
            return false;
        }
        if ((!z && selectionStart == 0) || (z && selectionStart == editable.length())) {
            return false;
        }
        if (!(view instanceof android.widget.TextView)) {
            wordIterator = null;
        } else {
            wordIterator = ((android.widget.TextView) view).getWordIterator();
        }
        if (wordIterator == null) {
            wordIterator = new android.text.method.WordIterator();
        }
        if (z) {
            wordIterator.setCharSequence(editable, selectionStart, editable.length());
            i = wordIterator.following(selectionStart);
            if (i == -1) {
                i = editable.length();
            }
        } else {
            wordIterator.setCharSequence(editable, 0, selectionStart);
            int preceding = wordIterator.preceding(selectionStart);
            if (preceding != -1) {
                selectionStart = preceding;
                i = selectionStart;
            } else {
                i = selectionStart;
                selectionStart = 0;
            }
        }
        editable.delete(selectionStart, i);
        return true;
    }

    private boolean deleteSelection(android.view.View view, android.text.Editable editable) {
        int selectionStart = android.text.Selection.getSelectionStart(editable);
        int selectionEnd = android.text.Selection.getSelectionEnd(editable);
        if (selectionEnd < selectionStart) {
            selectionEnd = selectionStart;
            selectionStart = selectionEnd;
        }
        if (selectionStart != selectionEnd) {
            editable.delete(selectionStart, selectionEnd);
            return true;
        }
        return false;
    }

    private boolean deleteLineFromCursor(android.view.View view, android.text.Editable editable, boolean z) {
        if (view instanceof android.widget.TextView) {
            int selectionStart = android.text.Selection.getSelectionStart(editable);
            int selectionEnd = android.text.Selection.getSelectionEnd(editable);
            if (selectionStart < selectionEnd) {
                selectionEnd = selectionStart;
                selectionStart = selectionEnd;
            }
            android.widget.TextView textView = (android.widget.TextView) view;
            android.text.Layout layout = textView.getLayout();
            if (layout != null && !textView.isOffsetMappingAvailable()) {
                int lineForOffset = layout.getLineForOffset(android.text.Selection.getSelectionStart(editable));
                int lineStart = layout.getLineStart(lineForOffset);
                int lineEnd = layout.getLineEnd(lineForOffset);
                if (z) {
                    editable.delete(selectionEnd, lineEnd);
                    return true;
                }
                editable.delete(lineStart, selectionStart);
                return true;
            }
            return false;
        }
        return false;
    }

    static int makeTextContentType(android.text.method.TextKeyListener.Capitalize capitalize, boolean z) {
        int i;
        switch (capitalize) {
            case CHARACTERS:
                i = 4097;
                break;
            case WORDS:
                i = 8193;
                break;
            case SENTENCES:
                i = 16385;
                break;
            default:
                i = 1;
                break;
        }
        if (z) {
            return i | 32768;
        }
        return i;
    }

    @Override // android.text.method.MetaKeyKeyListener, android.text.method.KeyListener
    public boolean onKeyDown(android.view.View view, android.text.Editable editable, int i, android.view.KeyEvent keyEvent) {
        boolean backspace;
        switch (i) {
            case 67:
                backspace = backspace(view, editable, i, keyEvent);
                break;
            case 112:
                backspace = forwardDelete(view, editable, i, keyEvent);
                break;
            default:
                backspace = false;
                break;
        }
        if (backspace) {
            adjustMetaAfterKeypress(editable);
            return true;
        }
        return super.onKeyDown(view, editable, i, keyEvent);
    }

    @Override // android.text.method.KeyListener
    public boolean onKeyOther(android.view.View view, android.text.Editable editable, android.view.KeyEvent keyEvent) {
        if (keyEvent.getAction() != 2 || keyEvent.getKeyCode() != 0) {
            return false;
        }
        int selectionStart = android.text.Selection.getSelectionStart(editable);
        int selectionEnd = android.text.Selection.getSelectionEnd(editable);
        if (selectionEnd < selectionStart) {
            selectionEnd = selectionStart;
            selectionStart = selectionEnd;
        }
        java.lang.String characters = keyEvent.getCharacters();
        if (characters == null) {
            return false;
        }
        editable.replace(selectionStart, selectionEnd, characters);
        return true;
    }
}
