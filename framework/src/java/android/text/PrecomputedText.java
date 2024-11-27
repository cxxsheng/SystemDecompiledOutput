package android.text;

/* loaded from: classes3.dex */
public class PrecomputedText implements android.text.Spannable {
    private static final char LINE_FEED = '\n';
    private final int mEnd;
    private final android.text.PrecomputedText.ParagraphInfo[] mParagraphInfo;
    private final android.text.PrecomputedText.Params mParams;
    private final int mStart;
    private final android.text.SpannableString mText;

    public static final class Params {
        public static final int NEED_RECOMPUTE = 1;
        public static final int UNUSABLE = 0;
        public static final int USABLE = 2;
        private final int mBreakStrategy;
        private final int mHyphenationFrequency;
        private final android.graphics.text.LineBreakConfig mLineBreakConfig;
        private final android.text.TextPaint mPaint;
        private final android.text.TextDirectionHeuristic mTextDir;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface CheckResultUsableResult {
        }

        public static class Builder {
            private int mBreakStrategy;
            private int mHyphenationFrequency;
            private android.graphics.text.LineBreakConfig mLineBreakConfig;
            private final android.text.TextPaint mPaint;
            private android.text.TextDirectionHeuristic mTextDir;

            public Builder(android.text.TextPaint textPaint) {
                this.mTextDir = android.text.TextDirectionHeuristics.FIRSTSTRONG_LTR;
                this.mBreakStrategy = 1;
                this.mHyphenationFrequency = 1;
                this.mLineBreakConfig = android.graphics.text.LineBreakConfig.NONE;
                this.mPaint = textPaint;
            }

            public Builder(android.text.PrecomputedText.Params params) {
                this.mTextDir = android.text.TextDirectionHeuristics.FIRSTSTRONG_LTR;
                this.mBreakStrategy = 1;
                this.mHyphenationFrequency = 1;
                this.mLineBreakConfig = android.graphics.text.LineBreakConfig.NONE;
                this.mPaint = params.mPaint;
                this.mTextDir = params.mTextDir;
                this.mBreakStrategy = params.mBreakStrategy;
                this.mHyphenationFrequency = params.mHyphenationFrequency;
                this.mLineBreakConfig = params.mLineBreakConfig;
            }

            public android.text.PrecomputedText.Params.Builder setBreakStrategy(int i) {
                this.mBreakStrategy = i;
                return this;
            }

            public android.text.PrecomputedText.Params.Builder setHyphenationFrequency(int i) {
                this.mHyphenationFrequency = i;
                return this;
            }

            public android.text.PrecomputedText.Params.Builder setTextDirection(android.text.TextDirectionHeuristic textDirectionHeuristic) {
                this.mTextDir = textDirectionHeuristic;
                return this;
            }

            public android.text.PrecomputedText.Params.Builder setLineBreakConfig(android.graphics.text.LineBreakConfig lineBreakConfig) {
                this.mLineBreakConfig = lineBreakConfig;
                return this;
            }

            public android.text.PrecomputedText.Params build() {
                return new android.text.PrecomputedText.Params(this.mPaint, this.mLineBreakConfig, this.mTextDir, this.mBreakStrategy, this.mHyphenationFrequency);
            }
        }

        public Params(android.text.TextPaint textPaint, android.graphics.text.LineBreakConfig lineBreakConfig, android.text.TextDirectionHeuristic textDirectionHeuristic, int i, int i2) {
            this.mPaint = textPaint;
            this.mTextDir = textDirectionHeuristic;
            this.mBreakStrategy = i;
            this.mHyphenationFrequency = i2;
            this.mLineBreakConfig = lineBreakConfig;
        }

        public android.text.TextPaint getTextPaint() {
            return this.mPaint;
        }

        public android.text.TextDirectionHeuristic getTextDirection() {
            return this.mTextDir;
        }

        public int getBreakStrategy() {
            return this.mBreakStrategy;
        }

        public int getHyphenationFrequency() {
            return this.mHyphenationFrequency;
        }

        public android.graphics.text.LineBreakConfig getLineBreakConfig() {
            return this.mLineBreakConfig;
        }

        public int checkResultUsable(android.text.TextPaint textPaint, android.text.TextDirectionHeuristic textDirectionHeuristic, int i, int i2, android.graphics.text.LineBreakConfig lineBreakConfig) {
            if (this.mBreakStrategy == i && this.mHyphenationFrequency == i2 && this.mLineBreakConfig.equals(lineBreakConfig) && this.mPaint.equalsForTextMeasurement(textPaint)) {
                return this.mTextDir == textDirectionHeuristic ? 2 : 1;
            }
            return 0;
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || !(obj instanceof android.text.PrecomputedText.Params)) {
                return false;
            }
            android.text.PrecomputedText.Params params = (android.text.PrecomputedText.Params) obj;
            if (checkResultUsable(params.mPaint, params.mTextDir, params.mBreakStrategy, params.mHyphenationFrequency, params.mLineBreakConfig) == 2) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Float.valueOf(this.mPaint.getTextSize()), java.lang.Float.valueOf(this.mPaint.getTextScaleX()), java.lang.Float.valueOf(this.mPaint.getTextSkewX()), java.lang.Float.valueOf(this.mPaint.getLetterSpacing()), java.lang.Float.valueOf(this.mPaint.getWordSpacing()), java.lang.Integer.valueOf(this.mPaint.getFlags()), this.mPaint.getTextLocales(), this.mPaint.getTypeface(), this.mPaint.getFontVariationSettings(), java.lang.Boolean.valueOf(this.mPaint.isElegantTextHeight()), this.mTextDir, java.lang.Integer.valueOf(this.mBreakStrategy), java.lang.Integer.valueOf(this.mHyphenationFrequency), java.lang.Integer.valueOf(android.graphics.text.LineBreakConfig.getResolvedLineBreakStyle(this.mLineBreakConfig)), java.lang.Integer.valueOf(android.graphics.text.LineBreakConfig.getResolvedLineBreakWordStyle(this.mLineBreakConfig)));
        }

        public java.lang.String toString() {
            return "{textSize=" + this.mPaint.getTextSize() + ", textScaleX=" + this.mPaint.getTextScaleX() + ", textSkewX=" + this.mPaint.getTextSkewX() + ", letterSpacing=" + this.mPaint.getLetterSpacing() + ", textLocale=" + this.mPaint.getTextLocales() + ", typeface=" + this.mPaint.getTypeface() + ", variationSettings=" + this.mPaint.getFontVariationSettings() + ", elegantTextHeight=" + this.mPaint.isElegantTextHeight() + ", textDir=" + this.mTextDir + ", breakStrategy=" + this.mBreakStrategy + ", hyphenationFrequency=" + this.mHyphenationFrequency + ", lineBreakStyle=" + android.graphics.text.LineBreakConfig.getResolvedLineBreakStyle(this.mLineBreakConfig) + ", lineBreakWordStyle=" + android.graphics.text.LineBreakConfig.getResolvedLineBreakWordStyle(this.mLineBreakConfig) + "}";
        }
    }

    public static class ParagraphInfo {
        public final android.text.MeasuredParagraph measured;
        public final int paragraphEnd;

        public ParagraphInfo(int i, android.text.MeasuredParagraph measuredParagraph) {
            this.paragraphEnd = i;
            this.measured = measuredParagraph;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0057  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static android.text.PrecomputedText create(java.lang.CharSequence charSequence, android.text.PrecomputedText.Params params) {
        android.text.PrecomputedText.ParagraphInfo[] paragraphInfoArr;
        android.text.PrecomputedText.ParagraphInfo[] paragraphInfoArr2;
        if (charSequence instanceof android.text.PrecomputedText) {
            android.text.PrecomputedText precomputedText = (android.text.PrecomputedText) charSequence;
            android.text.PrecomputedText.Params params2 = precomputedText.getParams();
            switch (params2.checkResultUsable(params.mPaint, params.mTextDir, params.mBreakStrategy, params.mHyphenationFrequency, params.mLineBreakConfig)) {
                case 1:
                    if (params.getBreakStrategy() == params2.getBreakStrategy() && params.getHyphenationFrequency() == params2.getHyphenationFrequency()) {
                        paragraphInfoArr = createMeasuredParagraphsFromPrecomputedText(precomputedText, params, true);
                        if (paragraphInfoArr == null) {
                            paragraphInfoArr2 = paragraphInfoArr;
                        } else {
                            paragraphInfoArr2 = createMeasuredParagraphs(charSequence, params, 0, charSequence.length(), true, true);
                        }
                        return new android.text.PrecomputedText(charSequence, 0, charSequence.length(), params, paragraphInfoArr2);
                    }
                    break;
                case 2:
                    return precomputedText;
            }
        }
        paragraphInfoArr = null;
        if (paragraphInfoArr == null) {
        }
        return new android.text.PrecomputedText(charSequence, 0, charSequence.length(), params, paragraphInfoArr2);
    }

    private static boolean isFastHyphenation(int i) {
        return i == 4 || i == 3;
    }

    private static android.text.PrecomputedText.ParagraphInfo[] createMeasuredParagraphsFromPrecomputedText(android.text.PrecomputedText precomputedText, android.text.PrecomputedText.Params params, boolean z) {
        int i;
        android.graphics.text.LineBreakConfig lineBreakConfig;
        int i2;
        android.text.PrecomputedText precomputedText2 = precomputedText;
        if ((params.getBreakStrategy() == 0 || params.getHyphenationFrequency() == 0) ? false : true) {
            if (isFastHyphenation(params.getHyphenationFrequency())) {
                i2 = 2;
            } else {
                i2 = 1;
            }
            i = i2;
        } else {
            i = 0;
        }
        android.graphics.text.LineBreakConfig lineBreakConfig2 = params.getLineBreakConfig();
        if (lineBreakConfig2.getLineBreakWordStyle() == 2 && precomputedText.getParagraphCount() != 1) {
            lineBreakConfig = new android.graphics.text.LineBreakConfig.Builder().merge(lineBreakConfig2).setLineBreakWordStyle(0).build();
        } else {
            lineBreakConfig = lineBreakConfig2;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        int i3 = 0;
        while (i3 < precomputedText.getParagraphCount()) {
            int paragraphStart = precomputedText2.getParagraphStart(i3);
            int paragraphEnd = precomputedText2.getParagraphEnd(i3);
            arrayList.add(new android.text.PrecomputedText.ParagraphInfo(paragraphEnd, android.text.MeasuredParagraph.buildForStaticLayout(params.getTextPaint(), lineBreakConfig, precomputedText, paragraphStart, paragraphEnd, params.getTextDirection(), i, z, true, precomputedText2.getMeasuredParagraph(i3), null)));
            i3++;
            precomputedText2 = precomputedText;
        }
        return (android.text.PrecomputedText.ParagraphInfo[]) arrayList.toArray(new android.text.PrecomputedText.ParagraphInfo[arrayList.size()]);
    }

    public static android.text.PrecomputedText.ParagraphInfo[] createMeasuredParagraphs(java.lang.CharSequence charSequence, android.text.PrecomputedText.Params params, int i, int i2, boolean z, boolean z2) {
        int i3;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        com.android.internal.util.Preconditions.checkNotNull(charSequence);
        com.android.internal.util.Preconditions.checkNotNull(params);
        int i4 = 1;
        int i5 = 0;
        int i6 = 2;
        if ((params.getBreakStrategy() == 0 || params.getHyphenationFrequency() == 0) ? false : true) {
            if (isFastHyphenation(params.getHyphenationFrequency())) {
                i4 = 2;
            }
        } else {
            i4 = 0;
        }
        android.graphics.text.LineBreakConfig lineBreakConfig = null;
        int i7 = i;
        while (i7 < i2) {
            int indexOf = android.text.TextUtils.indexOf(charSequence, LINE_FEED, i7, i2);
            if (indexOf < 0) {
                i3 = i2;
            } else {
                i3 = indexOf + 1;
            }
            if (lineBreakConfig == null) {
                lineBreakConfig = params.getLineBreakConfig();
                if (lineBreakConfig.getLineBreakWordStyle() == i6 && (i7 != i || i3 != i2)) {
                    lineBreakConfig = new android.graphics.text.LineBreakConfig.Builder().merge(lineBreakConfig).setLineBreakWordStyle(i5).build();
                }
            }
            int i8 = i3;
            arrayList.add(new android.text.PrecomputedText.ParagraphInfo(i8, android.text.MeasuredParagraph.buildForStaticLayout(params.getTextPaint(), lineBreakConfig, charSequence, i7, i3, params.getTextDirection(), i4, z, z2, null, null)));
            i7 = i8;
            i5 = 0;
            i6 = 2;
        }
        return (android.text.PrecomputedText.ParagraphInfo[]) arrayList.toArray(new android.text.PrecomputedText.ParagraphInfo[arrayList.size()]);
    }

    private PrecomputedText(java.lang.CharSequence charSequence, int i, int i2, android.text.PrecomputedText.Params params, android.text.PrecomputedText.ParagraphInfo[] paragraphInfoArr) {
        this.mText = new android.text.SpannableString(charSequence, true);
        this.mStart = i;
        this.mEnd = i2;
        this.mParams = params;
        this.mParagraphInfo = paragraphInfoArr;
    }

    public java.lang.CharSequence getText() {
        return this.mText;
    }

    public int getStart() {
        return this.mStart;
    }

    public int getEnd() {
        return this.mEnd;
    }

    public android.text.PrecomputedText.Params getParams() {
        return this.mParams;
    }

    public int getParagraphCount() {
        return this.mParagraphInfo.length;
    }

    public int getParagraphStart(int i) {
        com.android.internal.util.Preconditions.checkArgumentInRange(i, 0, getParagraphCount(), "paraIndex");
        return i == 0 ? this.mStart : getParagraphEnd(i - 1);
    }

    public int getParagraphEnd(int i) {
        com.android.internal.util.Preconditions.checkArgumentInRange(i, 0, getParagraphCount(), "paraIndex");
        return this.mParagraphInfo[i].paragraphEnd;
    }

    public android.text.MeasuredParagraph getMeasuredParagraph(int i) {
        return this.mParagraphInfo[i].measured;
    }

    public android.text.PrecomputedText.ParagraphInfo[] getParagraphInfo() {
        return this.mParagraphInfo;
    }

    public int checkResultUsable(int i, int i2, android.text.TextDirectionHeuristic textDirectionHeuristic, android.text.TextPaint textPaint, int i3, int i4, android.graphics.text.LineBreakConfig lineBreakConfig) {
        if (this.mStart != i || this.mEnd != i2) {
            return 0;
        }
        return this.mParams.checkResultUsable(textPaint, textDirectionHeuristic, i3, i4, lineBreakConfig);
    }

    public int findParaIndex(int i) {
        for (int i2 = 0; i2 < this.mParagraphInfo.length; i2++) {
            if (i < this.mParagraphInfo[i2].paragraphEnd) {
                return i2;
            }
        }
        throw new java.lang.IndexOutOfBoundsException("pos must be less than " + this.mParagraphInfo[this.mParagraphInfo.length - 1].paragraphEnd + ", gave " + i);
    }

    public float getWidth(int i, int i2) {
        com.android.internal.util.Preconditions.checkArgument(i >= 0 && i <= this.mText.length(), "invalid start offset");
        com.android.internal.util.Preconditions.checkArgument(i2 >= 0 && i2 <= this.mText.length(), "invalid end offset");
        com.android.internal.util.Preconditions.checkArgument(i <= i2, "start offset can not be larger than end offset");
        if (i == i2) {
            return 0.0f;
        }
        int findParaIndex = findParaIndex(i);
        int paragraphStart = getParagraphStart(findParaIndex);
        int paragraphEnd = getParagraphEnd(findParaIndex);
        if (i < paragraphStart || paragraphEnd < i2) {
            throw new java.lang.IllegalArgumentException("Cannot measured across the paragraph:para: (" + paragraphStart + ", " + paragraphEnd + "), request: (" + i + ", " + i2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        return getMeasuredParagraph(findParaIndex).getWidth(i - paragraphStart, i2 - paragraphStart);
    }

    public void getBounds(int i, int i2, android.graphics.Rect rect) {
        com.android.internal.util.Preconditions.checkArgument(i >= 0 && i <= this.mText.length(), "invalid start offset");
        com.android.internal.util.Preconditions.checkArgument(i2 >= 0 && i2 <= this.mText.length(), "invalid end offset");
        com.android.internal.util.Preconditions.checkArgument(i <= i2, "start offset can not be larger than end offset");
        com.android.internal.util.Preconditions.checkNotNull(rect);
        if (i == i2) {
            rect.set(0, 0, 0, 0);
            return;
        }
        int findParaIndex = findParaIndex(i);
        int paragraphStart = getParagraphStart(findParaIndex);
        int paragraphEnd = getParagraphEnd(findParaIndex);
        if (i < paragraphStart || paragraphEnd < i2) {
            throw new java.lang.IllegalArgumentException("Cannot measured across the paragraph:para: (" + paragraphStart + ", " + paragraphEnd + "), request: (" + i + ", " + i2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        getMeasuredParagraph(findParaIndex).getBounds(i - paragraphStart, i2 - paragraphStart, rect);
    }

    public void getFontMetricsInt(int i, int i2, android.graphics.Paint.FontMetricsInt fontMetricsInt) {
        com.android.internal.util.Preconditions.checkArgument(i >= 0 && i <= this.mText.length(), "invalid start offset");
        com.android.internal.util.Preconditions.checkArgument(i2 >= 0 && i2 <= this.mText.length(), "invalid end offset");
        com.android.internal.util.Preconditions.checkArgument(i <= i2, "start offset can not be larger than end offset");
        java.util.Objects.requireNonNull(fontMetricsInt);
        if (i == i2) {
            this.mParams.getTextPaint().getFontMetricsInt(fontMetricsInt);
            return;
        }
        int findParaIndex = findParaIndex(i);
        int paragraphStart = getParagraphStart(findParaIndex);
        int paragraphEnd = getParagraphEnd(findParaIndex);
        if (i < paragraphStart || paragraphEnd < i2) {
            throw new java.lang.IllegalArgumentException("Cannot measured across the paragraph:para: (" + paragraphStart + ", " + paragraphEnd + "), request: (" + i + ", " + i2 + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        getMeasuredParagraph(findParaIndex).getFontMetricsInt(i - paragraphStart, i2 - paragraphStart, fontMetricsInt);
    }

    public float getCharWidthAt(int i) {
        com.android.internal.util.Preconditions.checkArgument(i >= 0 && i < this.mText.length(), "invalid offset");
        int findParaIndex = findParaIndex(i);
        int paragraphStart = getParagraphStart(findParaIndex);
        getParagraphEnd(findParaIndex);
        return getMeasuredParagraph(findParaIndex).getCharWidthAt(i - paragraphStart);
    }

    public int getMemoryUsage() {
        int i = 0;
        for (int i2 = 0; i2 < getParagraphCount(); i2++) {
            i += getMeasuredParagraph(i2).getMemoryUsage();
        }
        return i;
    }

    @Override // android.text.Spannable
    public void setSpan(java.lang.Object obj, int i, int i2, int i3) {
        if (obj instanceof android.text.style.MetricAffectingSpan) {
            throw new java.lang.IllegalArgumentException("MetricAffectingSpan can not be set to PrecomputedText.");
        }
        this.mText.setSpan(obj, i, i2, i3);
    }

    @Override // android.text.Spannable
    public void removeSpan(java.lang.Object obj) {
        if (obj instanceof android.text.style.MetricAffectingSpan) {
            throw new java.lang.IllegalArgumentException("MetricAffectingSpan can not be removed from PrecomputedText.");
        }
        this.mText.removeSpan(obj);
    }

    @Override // android.text.Spanned
    public <T> T[] getSpans(int i, int i2, java.lang.Class<T> cls) {
        return (T[]) this.mText.getSpans(i, i2, cls);
    }

    @Override // android.text.Spanned
    public int getSpanStart(java.lang.Object obj) {
        return this.mText.getSpanStart(obj);
    }

    @Override // android.text.Spanned
    public int getSpanEnd(java.lang.Object obj) {
        return this.mText.getSpanEnd(obj);
    }

    @Override // android.text.Spanned
    public int getSpanFlags(java.lang.Object obj) {
        return this.mText.getSpanFlags(obj);
    }

    @Override // android.text.Spanned
    public int nextSpanTransition(int i, int i2, java.lang.Class cls) {
        return this.mText.nextSpanTransition(i, i2, cls);
    }

    @Override // java.lang.CharSequence
    public int length() {
        return this.mText.length();
    }

    @Override // java.lang.CharSequence
    public char charAt(int i) {
        return this.mText.charAt(i);
    }

    @Override // java.lang.CharSequence
    public java.lang.CharSequence subSequence(int i, int i2) {
        return create(this.mText.subSequence(i, i2), this.mParams);
    }

    @Override // java.lang.CharSequence
    public java.lang.String toString() {
        return this.mText.toString();
    }
}
