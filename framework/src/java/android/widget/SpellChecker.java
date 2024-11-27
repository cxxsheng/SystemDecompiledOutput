package android.widget;

/* loaded from: classes4.dex */
public class SpellChecker implements android.view.textservice.SpellCheckerSession.SpellCheckerSessionListener {
    public static final int AVERAGE_WORD_LENGTH = 7;
    private static final boolean DBG = false;
    public static final int MAX_NUMBER_OF_WORDS = 50;
    private static final int MAX_SENTENCE_LENGTH = 350;
    private static final int SPELL_PAUSE_DURATION = 400;
    private static final java.lang.String TAG = android.widget.SpellChecker.class.getSimpleName();
    private static final int USE_SPAN_RANGE = -1;
    public static final int WORD_ITERATOR_INTERVAL = 350;
    final int mCookie;
    private java.util.Locale mCurrentLocale;
    private int mLength;
    private android.widget.SpellChecker.SentenceIteratorWrapper mSentenceIterator;
    android.view.textservice.SpellCheckerSession mSpellCheckerSession;
    private java.lang.Runnable mSpellRunnable;
    private android.view.textservice.TextServicesManager mTextServicesManager;
    private final android.widget.TextView mTextView;
    private android.widget.SpellChecker.SpellParser[] mSpellParsers = new android.widget.SpellChecker.SpellParser[0];
    private int mSpanSequenceCounter = 0;
    private int[] mIds = com.android.internal.util.ArrayUtils.newUnpaddedIntArray(1);
    private android.text.style.SpellCheckSpan[] mSpellCheckSpans = new android.text.style.SpellCheckSpan[this.mIds.length];

    private enum RemoveReason {
        REPLACE,
        OBSOLETE
    }

    public SpellChecker(android.widget.TextView textView) {
        this.mTextView = textView;
        setLocale(this.mTextView.getSpellCheckerLocale());
        this.mCookie = hashCode();
    }

    void resetSession() {
        closeSession();
        this.mTextServicesManager = this.mTextView.getTextServicesManagerForUser();
        if (this.mCurrentLocale == null || this.mTextServicesManager == null || this.mTextView.length() == 0 || !this.mTextServicesManager.isSpellCheckerEnabled() || this.mTextServicesManager.getCurrentSpellCheckerSubtype(true) == null) {
            this.mSpellCheckerSession = null;
        } else {
            this.mSpellCheckerSession = this.mTextServicesManager.newSpellCheckerSession(new android.view.textservice.SpellCheckerSession.SpellCheckerSessionParams.Builder().setLocale(this.mCurrentLocale).setSupportedAttributes(27).build(), this.mTextView.getContext().getMainExecutor(), this);
        }
        for (int i = 0; i < this.mLength; i++) {
            this.mIds[i] = -1;
        }
        this.mLength = 0;
        this.mTextView.removeMisspelledSpans((android.text.Editable) this.mTextView.getText());
    }

    private void setLocale(java.util.Locale locale) {
        this.mCurrentLocale = locale;
        resetSession();
        if (locale != null) {
            this.mSentenceIterator = new android.widget.SpellChecker.SentenceIteratorWrapper(java.text.BreakIterator.getSentenceInstance(locale));
        }
        this.mTextView.onLocaleChanged();
    }

    private boolean isSessionActive() {
        return this.mSpellCheckerSession != null;
    }

    public void closeSession() {
        if (this.mSpellCheckerSession != null) {
            this.mSpellCheckerSession.close();
        }
        int length = this.mSpellParsers.length;
        for (int i = 0; i < length; i++) {
            this.mSpellParsers[i].stop();
        }
        if (this.mSpellRunnable != null) {
            this.mTextView.removeCallbacks(this.mSpellRunnable);
        }
    }

    private int nextSpellCheckSpanIndex() {
        for (int i = 0; i < this.mLength; i++) {
            if (this.mIds[i] < 0) {
                return i;
            }
        }
        this.mIds = com.android.internal.util.GrowingArrayUtils.append(this.mIds, this.mLength, 0);
        this.mSpellCheckSpans = (android.text.style.SpellCheckSpan[]) com.android.internal.util.GrowingArrayUtils.append(this.mSpellCheckSpans, this.mLength, new android.text.style.SpellCheckSpan());
        this.mLength++;
        return this.mLength - 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addSpellCheckSpan(android.text.Editable editable, int i, int i2) {
        int nextSpellCheckSpanIndex = nextSpellCheckSpanIndex();
        android.text.style.SpellCheckSpan spellCheckSpan = this.mSpellCheckSpans[nextSpellCheckSpanIndex];
        editable.setSpan(spellCheckSpan, i, i2, 33);
        spellCheckSpan.setSpellCheckInProgress(false);
        int[] iArr = this.mIds;
        int i3 = this.mSpanSequenceCounter;
        this.mSpanSequenceCounter = i3 + 1;
        iArr[nextSpellCheckSpanIndex] = i3;
    }

    public void onSpellCheckSpanRemoved(android.text.style.SpellCheckSpan spellCheckSpan) {
        for (int i = 0; i < this.mLength; i++) {
            if (this.mSpellCheckSpans[i] == spellCheckSpan) {
                this.mIds[i] = -1;
                return;
            }
        }
    }

    public void onSelectionChanged() {
        spellCheck();
    }

    void onPerformSpellCheck() {
        spellCheck(0, this.mTextView.length(), true);
    }

    public void spellCheck(int i, int i2) {
        spellCheck(i, i2, false);
    }

    public void spellCheck(int i, int i2, boolean z) {
        java.util.Locale spellCheckerLocale = this.mTextView.getSpellCheckerLocale();
        boolean isSessionActive = isSessionActive();
        if (spellCheckerLocale == null || this.mCurrentLocale == null || !this.mCurrentLocale.equals(spellCheckerLocale)) {
            setLocale(spellCheckerLocale);
            i2 = this.mTextView.getText().length();
            i = 0;
        } else if (isSessionActive != (this.mTextServicesManager != null && this.mTextServicesManager.isSpellCheckerEnabled())) {
            resetSession();
        }
        if (isSessionActive) {
            int length = this.mSpellParsers.length;
            for (int i3 = 0; i3 < length; i3++) {
                android.widget.SpellChecker.SpellParser spellParser = this.mSpellParsers[i3];
                if (spellParser.isFinished()) {
                    spellParser.parse(i, i2, z);
                    return;
                }
            }
            android.widget.SpellChecker.SpellParser[] spellParserArr = new android.widget.SpellChecker.SpellParser[length + 1];
            java.lang.System.arraycopy(this.mSpellParsers, 0, spellParserArr, 0, length);
            this.mSpellParsers = spellParserArr;
            android.widget.SpellChecker.SpellParser spellParser2 = new android.widget.SpellChecker.SpellParser();
            this.mSpellParsers[length] = spellParser2;
            spellParser2.parse(i, i2, z);
        }
    }

    private void spellCheck() {
        spellCheck(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void spellCheck(boolean z) {
        boolean z2;
        if (this.mSpellCheckerSession == null) {
            return;
        }
        android.text.Editable editable = (android.text.Editable) this.mTextView.getText();
        int selectionStart = android.text.Selection.getSelectionStart(editable);
        int selectionEnd = android.text.Selection.getSelectionEnd(editable);
        int i = this.mLength;
        android.view.textservice.TextInfo[] textInfoArr = new android.view.textservice.TextInfo[i];
        int i2 = 0;
        for (int i3 = 0; i3 < this.mLength; i3++) {
            android.text.style.SpellCheckSpan spellCheckSpan = this.mSpellCheckSpans[i3];
            if (this.mIds[i3] >= 0 && !spellCheckSpan.isSpellCheckInProgress()) {
                int spanStart = editable.getSpanStart(spellCheckSpan);
                int spanEnd = editable.getSpanEnd(spellCheckSpan);
                int i4 = spanEnd + 1;
                if (selectionStart == i4 && android.text.method.WordIterator.isMidWordPunctuation(this.mCurrentLocale, java.lang.Character.codePointBefore(editable, i4))) {
                    z2 = false;
                } else if (selectionEnd <= spanStart || selectionStart > spanEnd) {
                    z2 = true;
                } else {
                    z2 = selectionStart == spanEnd && selectionStart > 0 && isSeparator(java.lang.Character.codePointBefore(editable, selectionStart));
                }
                if (spanStart >= 0 && spanEnd > spanStart && (z || z2)) {
                    spellCheckSpan.setSpellCheckInProgress(true);
                    textInfoArr[i2] = new android.view.textservice.TextInfo(editable, spanStart, spanEnd, this.mCookie, this.mIds[i3]);
                    i2++;
                }
            }
        }
        if (i2 > 0) {
            if (i2 < i) {
                android.view.textservice.TextInfo[] textInfoArr2 = new android.view.textservice.TextInfo[i2];
                java.lang.System.arraycopy(textInfoArr, 0, textInfoArr2, 0, i2);
                textInfoArr = textInfoArr2;
            }
            this.mSpellCheckerSession.getSentenceSuggestions(textInfoArr, 5);
        }
    }

    private static boolean isSeparator(int i) {
        return ((1 << java.lang.Character.getType(i)) & 1634758656) != 0;
    }

    private android.text.style.SpellCheckSpan onGetSuggestionsInternal(android.view.textservice.SuggestionsInfo suggestionsInfo, int i, int i2) {
        if (suggestionsInfo == null || suggestionsInfo.getCookie() != this.mCookie) {
            return null;
        }
        android.text.Editable editable = (android.text.Editable) this.mTextView.getText();
        int sequence = suggestionsInfo.getSequence();
        for (int i3 = 0; i3 < this.mLength; i3++) {
            if (sequence == this.mIds[i3]) {
                android.text.style.SpellCheckSpan spellCheckSpan = this.mSpellCheckSpans[i3];
                int spanStart = editable.getSpanStart(spellCheckSpan);
                if (spanStart < 0) {
                    return null;
                }
                int suggestionsAttributes = suggestionsInfo.getSuggestionsAttributes();
                boolean z = (suggestionsAttributes & 1) > 0;
                boolean z2 = (suggestionsAttributes & 2) > 0;
                boolean z3 = (suggestionsAttributes & 8) > 0;
                int i4 = spanStart + i;
                int i5 = i4 + i2;
                if (i5 > editable.length()) {
                    return spellCheckSpan;
                }
                if (!z && (z2 || z3)) {
                    createMisspelledSuggestionSpan(editable, suggestionsInfo, spellCheckSpan, i, i2);
                } else {
                    int spanEnd = editable.getSpanEnd(spellCheckSpan);
                    if (i == -1 || i2 == -1) {
                        i5 = spanEnd;
                        i4 = spanStart;
                    }
                    if (spanStart >= 0 && spanEnd > spanStart && i5 > i4) {
                        boolean isVisibleToAccessibility = this.mTextView.isVisibleToAccessibility();
                        android.text.SpannedString spannedString = isVisibleToAccessibility ? new android.text.SpannedString(editable) : null;
                        boolean removeErrorSuggestionSpan = removeErrorSuggestionSpan(editable, i4, i5, android.widget.SpellChecker.RemoveReason.OBSOLETE);
                        if (isVisibleToAccessibility && removeErrorSuggestionSpan) {
                            this.mTextView.sendAccessibilityEventTypeViewTextChanged(spannedString, i4, i5);
                        }
                    }
                }
                return spellCheckSpan;
            }
        }
        return null;
    }

    private static boolean removeErrorSuggestionSpan(android.text.Editable editable, int i, int i2, android.widget.SpellChecker.RemoveReason removeReason) {
        boolean z = false;
        for (android.text.style.SuggestionSpan suggestionSpan : (android.text.style.SuggestionSpan[]) editable.getSpans(i, i2, android.text.style.SuggestionSpan.class)) {
            if (editable.getSpanStart(suggestionSpan) == i && editable.getSpanEnd(suggestionSpan) == i2 && (suggestionSpan.getFlags() & 10) != 0) {
                editable.removeSpan(suggestionSpan);
                z = true;
            }
        }
        return z;
    }

    @Override // android.view.textservice.SpellCheckerSession.SpellCheckerSessionListener
    public void onGetSuggestions(android.view.textservice.SuggestionsInfo[] suggestionsInfoArr) {
        android.text.Editable editable = (android.text.Editable) this.mTextView.getText();
        for (android.view.textservice.SuggestionsInfo suggestionsInfo : suggestionsInfoArr) {
            android.text.style.SpellCheckSpan onGetSuggestionsInternal = onGetSuggestionsInternal(suggestionsInfo, -1, -1);
            if (onGetSuggestionsInternal != null) {
                editable.removeSpan(onGetSuggestionsInternal);
            }
        }
        scheduleNewSpellCheck();
    }

    @Override // android.view.textservice.SpellCheckerSession.SpellCheckerSessionListener
    public void onGetSentenceSuggestions(android.view.textservice.SentenceSuggestionsInfo[] sentenceSuggestionsInfoArr) {
        android.text.Editable editable = (android.text.Editable) this.mTextView.getText();
        for (android.view.textservice.SentenceSuggestionsInfo sentenceSuggestionsInfo : sentenceSuggestionsInfoArr) {
            if (sentenceSuggestionsInfo != null) {
                android.text.style.SpellCheckSpan spellCheckSpan = null;
                for (int i = 0; i < sentenceSuggestionsInfo.getSuggestionsCount(); i++) {
                    android.view.textservice.SuggestionsInfo suggestionsInfoAt = sentenceSuggestionsInfo.getSuggestionsInfoAt(i);
                    if (suggestionsInfoAt != null) {
                        android.text.style.SpellCheckSpan onGetSuggestionsInternal = onGetSuggestionsInternal(suggestionsInfoAt, sentenceSuggestionsInfo.getOffsetAt(i), sentenceSuggestionsInfo.getLengthAt(i));
                        if (spellCheckSpan == null && onGetSuggestionsInternal != null) {
                            spellCheckSpan = onGetSuggestionsInternal;
                        }
                    }
                }
                if (spellCheckSpan != null) {
                    editable.removeSpan(spellCheckSpan);
                }
            }
        }
        scheduleNewSpellCheck();
    }

    private void scheduleNewSpellCheck() {
        if (this.mSpellRunnable == null) {
            this.mSpellRunnable = new java.lang.Runnable() { // from class: android.widget.SpellChecker.1
                @Override // java.lang.Runnable
                public void run() {
                    int length = android.widget.SpellChecker.this.mSpellParsers.length;
                    for (int i = 0; i < length; i++) {
                        android.widget.SpellChecker.SpellParser spellParser = android.widget.SpellChecker.this.mSpellParsers[i];
                        if (!spellParser.isFinished()) {
                            spellParser.parse();
                            return;
                        }
                    }
                }
            };
        } else {
            this.mTextView.removeCallbacks(this.mSpellRunnable);
        }
        this.mTextView.postDelayed(this.mSpellRunnable, 400L);
    }

    private void createMisspelledSuggestionSpan(android.text.Editable editable, android.view.textservice.SuggestionsInfo suggestionsInfo, android.text.style.SpellCheckSpan spellCheckSpan, int i, int i2) {
        java.lang.String[] strArr;
        int i3;
        int spanStart = editable.getSpanStart(spellCheckSpan);
        int spanEnd = editable.getSpanEnd(spellCheckSpan);
        if (spanStart < 0 || spanEnd <= spanStart) {
            return;
        }
        if (i != -1 && i2 != -1) {
            spanStart += i;
            spanEnd = spanStart + i2;
        }
        int suggestionsCount = suggestionsInfo.getSuggestionsCount();
        if (suggestionsCount > 0) {
            strArr = new java.lang.String[suggestionsCount];
            for (int i4 = 0; i4 < suggestionsCount; i4++) {
                strArr[i4] = suggestionsInfo.getSuggestionAt(i4);
            }
        } else {
            strArr = (java.lang.String[]) com.android.internal.util.ArrayUtils.emptyArray(java.lang.String.class);
        }
        int suggestionsAttributes = suggestionsInfo.getSuggestionsAttributes();
        if ((suggestionsAttributes & 16) != 0) {
            i3 = 0;
        } else {
            i3 = 1;
        }
        if ((suggestionsAttributes & 2) != 0) {
            i3 |= 2;
        }
        if ((suggestionsAttributes & 8) != 0) {
            i3 |= 8;
        }
        android.text.style.SuggestionSpan suggestionSpan = new android.text.style.SuggestionSpan(this.mTextView.getContext(), strArr, i3);
        boolean z = !removeErrorSuggestionSpan(editable, spanStart, spanEnd, android.widget.SpellChecker.RemoveReason.REPLACE) && this.mTextView.isVisibleToAccessibility();
        android.text.SpannedString spannedString = z ? new android.text.SpannedString(editable) : null;
        editable.setSpan(suggestionSpan, spanStart, spanEnd, 33);
        if (z) {
            this.mTextView.sendAccessibilityEventTypeViewTextChanged(spannedString, spanStart, spanEnd);
        }
        this.mTextView.invalidateRegion(spanStart, spanEnd, false);
    }

    private static class SentenceIteratorWrapper {
        private int mEndOffset;
        private java.text.BreakIterator mSentenceIterator;
        private int mStartOffset;

        SentenceIteratorWrapper(java.text.BreakIterator breakIterator) {
            this.mSentenceIterator = breakIterator;
        }

        public void setCharSequence(java.lang.CharSequence charSequence, int i, int i2) {
            this.mStartOffset = java.lang.Math.max(0, i);
            this.mEndOffset = java.lang.Math.min(i2, charSequence.length());
            this.mSentenceIterator.setText(charSequence.subSequence(this.mStartOffset, this.mEndOffset).toString());
        }

        public int preceding(int i) {
            int preceding;
            if (i >= this.mStartOffset && (preceding = this.mSentenceIterator.preceding(i - this.mStartOffset)) != -1) {
                return preceding + this.mStartOffset;
            }
            return -1;
        }

        public int following(int i) {
            int following;
            if (i <= this.mEndOffset && (following = this.mSentenceIterator.following(i - this.mStartOffset)) != -1) {
                return following + this.mStartOffset;
            }
            return -1;
        }

        public boolean isBoundary(int i) {
            if (i < this.mStartOffset || i > this.mEndOffset) {
                return false;
            }
            return this.mSentenceIterator.isBoundary(i - this.mStartOffset);
        }
    }

    private class SpellParser {
        private boolean mForceCheckWhenEditingWord;
        private java.lang.Object mRange;

        private SpellParser() {
            this.mRange = new java.lang.Object();
        }

        public void parse(int i, int i2, boolean z) {
            this.mForceCheckWhenEditingWord = z;
            int length = android.widget.SpellChecker.this.mTextView.length();
            if (i2 > length) {
                android.util.Log.w(android.widget.SpellChecker.TAG, "Parse invalid region, from " + i + " to " + i2);
                i2 = length;
            }
            if (i2 > i) {
                setRangeSpan((android.text.Editable) android.widget.SpellChecker.this.mTextView.getText(), i, i2);
                parse();
            }
        }

        public boolean isFinished() {
            return ((android.text.Editable) android.widget.SpellChecker.this.mTextView.getText()).getSpanStart(this.mRange) < 0;
        }

        public void stop() {
            removeRangeSpan((android.text.Editable) android.widget.SpellChecker.this.mTextView.getText());
            this.mForceCheckWhenEditingWord = false;
        }

        private void setRangeSpan(android.text.Editable editable, int i, int i2) {
            editable.setSpan(this.mRange, i, i2, 33);
        }

        private void removeRangeSpan(android.text.Editable editable) {
            editable.removeSpan(this.mRange);
        }

        public void parse() {
            boolean z;
            android.text.Editable editable = (android.text.Editable) android.widget.SpellChecker.this.mTextView.getText();
            int spanStart = editable.getSpanStart(this.mRange);
            int spanEnd = editable.getSpanEnd(this.mRange);
            android.util.Range detectSentenceBoundary = android.widget.SpellChecker.this.detectSentenceBoundary(editable, spanStart, spanEnd);
            int intValue = ((java.lang.Integer) detectSentenceBoundary.getLower()).intValue();
            int intValue2 = ((java.lang.Integer) detectSentenceBoundary.getUpper()).intValue();
            if (intValue == intValue2) {
                stop();
                return;
            }
            boolean z2 = true;
            if (intValue2 >= spanEnd) {
                z = false;
            } else {
                z = true;
            }
            int i = intValue;
            int i2 = 0;
            while (true) {
                if (i2 >= android.widget.SpellChecker.this.mLength) {
                    break;
                }
                android.text.style.SpellCheckSpan spellCheckSpan = android.widget.SpellChecker.this.mSpellCheckSpans[i2];
                if (android.widget.SpellChecker.this.mIds[i2] >= 0 && !spellCheckSpan.isSpellCheckInProgress()) {
                    int spanStart2 = editable.getSpanStart(spellCheckSpan);
                    int spanEnd2 = editable.getSpanEnd(spellCheckSpan);
                    if (spanEnd2 >= i && intValue2 >= spanStart2) {
                        if (spanStart2 <= i && intValue2 <= spanEnd2) {
                            z2 = false;
                            break;
                        } else {
                            editable.removeSpan(spellCheckSpan);
                            i = java.lang.Math.min(spanStart2, i);
                            intValue2 = java.lang.Math.max(spanEnd2, intValue2);
                        }
                    }
                }
                i2++;
            }
            if (intValue2 <= i) {
                android.util.Log.w(android.widget.SpellChecker.TAG, "Trying to spellcheck invalid region, from " + intValue + " to " + intValue2);
            } else if (z2) {
                android.widget.SpellChecker.this.addSpellCheckSpan(editable, i, intValue2);
            }
            if (z && intValue2 != -1 && intValue2 <= spanEnd) {
                setRangeSpan(editable, intValue2, spanEnd);
            } else {
                removeRangeSpan(editable);
            }
            android.widget.SpellChecker.this.spellCheck(this.mForceCheckWhenEditingWord);
        }

        private <T> void removeSpansAt(android.text.Editable editable, int i, T[] tArr) {
            for (T t : tArr) {
                if (editable.getSpanStart(t) <= i && editable.getSpanEnd(t) >= i) {
                    editable.removeSpan(t);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.util.Range<java.lang.Integer> detectSentenceBoundary(java.lang.CharSequence charSequence, int i, int i2) {
        int findSeparator = findSeparator(charSequence, java.lang.Math.max(0, i - 350), java.lang.Math.max(0, i - 700));
        int findSeparator2 = findSeparator(charSequence, java.lang.Math.min(i + 700, i2), java.lang.Math.min(i + 1050, charSequence.length()));
        this.mSentenceIterator.setCharSequence(charSequence, findSeparator, findSeparator2);
        int preceding = this.mSentenceIterator.isBoundary(i) ? i : this.mSentenceIterator.preceding(i);
        int following = this.mSentenceIterator.following(preceding);
        if (following != -1) {
            findSeparator2 = following;
        }
        if (findSeparator2 - preceding <= 350) {
            while (findSeparator2 < i2) {
                int following2 = this.mSentenceIterator.following(findSeparator2);
                if (following2 == -1 || following2 - preceding > 350) {
                    break;
                }
                findSeparator2 = following2;
            }
        } else if (findSeparator2 - i > 350) {
            findSeparator2 = findSeparator(charSequence, i + 350, findSeparator2);
            preceding = roundUpToWordStart(charSequence, i, preceding);
        } else {
            preceding = roundUpToWordStart(charSequence, findSeparator2 - 350, preceding);
        }
        return new android.util.Range<>(java.lang.Integer.valueOf(preceding), java.lang.Integer.valueOf(java.lang.Math.max(preceding, findSeparator2)));
    }

    private int roundUpToWordStart(java.lang.CharSequence charSequence, int i, int i2) {
        if (isSeparator(charSequence.charAt(i))) {
            return i;
        }
        int findSeparator = findSeparator(charSequence, i, i2);
        return findSeparator != i2 ? findSeparator + 1 : i2;
    }

    private static int findSeparator(java.lang.CharSequence charSequence, int i, int i2) {
        int i3 = i < i2 ? 1 : -1;
        while (i != i2) {
            if (!isSeparator(charSequence.charAt(i))) {
                i += i3;
            } else {
                return i;
            }
        }
        return i2;
    }

    public static boolean haveWordBoundariesChanged(android.text.Editable editable, int i, int i2, int i3, int i4) {
        if (i4 != i && i3 != i2) {
            return true;
        }
        if (i4 == i && i < editable.length()) {
            return java.lang.Character.isLetterOrDigit(java.lang.Character.codePointAt(editable, i));
        }
        if (i3 == i2 && i2 > 0) {
            return java.lang.Character.isLetterOrDigit(java.lang.Character.codePointBefore(editable, i2));
        }
        return false;
    }
}
