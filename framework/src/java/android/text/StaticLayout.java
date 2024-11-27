package android.text;

/* loaded from: classes3.dex */
public class StaticLayout extends android.text.Layout {
    private static final char CHAR_NEW_LINE = '\n';
    private static final int COLUMNS_ELLIPSIZE = 7;
    private static final int COLUMNS_NORMAL = 5;
    private static final int DEFAULT_MAX_LINE_HEIGHT = -1;
    private static final int DESCENT = 2;
    private static final int DIR = 0;
    private static final int DIR_SHIFT = 30;
    private static final int ELLIPSIS_COUNT = 6;
    private static final int ELLIPSIS_START = 5;
    private static final int END_HYPHEN_MASK = 7;
    private static final int EXTRA = 3;
    private static final double EXTRA_ROUNDING = 0.5d;
    private static final int HYPHEN = 4;
    private static final int HYPHEN_MASK = 255;
    private static final int START = 0;
    private static final int START_HYPHEN_BITS_SHIFT = 3;
    private static final int START_HYPHEN_MASK = 24;
    private static final int START_MASK = 536870911;
    private static final int TAB = 0;
    private static final float TAB_INCREMENT = 20.0f;
    private static final int TAB_MASK = 536870912;
    static final java.lang.String TAG = "StaticLayout";
    private static final int TOP = 1;
    private int mBottomPadding;
    private int mColumns;
    private android.graphics.RectF mDrawingBounds;
    private boolean mEllipsized;
    private int[] mLeftIndents;
    private int mLineCount;
    private android.text.Layout.Directions[] mLineDirections;
    private int[] mLines;
    private int mMaxLineHeight;
    private int mMaximumVisibleLineCount;
    private int[] mRightIndents;
    private int mTopPadding;

    public static final class Builder {
        private static final android.util.Pools.SynchronizedPool<android.text.StaticLayout.Builder> sPool = new android.util.Pools.SynchronizedPool<>(3);
        private boolean mAddLastLineLineSpacing;
        private android.text.Layout.Alignment mAlignment;
        private int mBreakStrategy;
        private boolean mCalculateBounds;
        private android.text.TextUtils.TruncateAt mEllipsize;
        private int mEllipsizedWidth;
        private int mEnd;
        private boolean mFallbackLineSpacing;
        private int mHyphenationFrequency;
        private boolean mIncludePad;
        private int mJustificationMode;
        private int[] mLeftIndents;
        private int mMaxLines;
        private android.graphics.Paint.FontMetrics mMinimumFontMetrics;
        private android.text.TextPaint mPaint;
        private int[] mRightIndents;
        private boolean mShiftDrawingOffsetForStartOverhang;
        private float mSpacingAdd;
        private float mSpacingMult;
        private int mStart;
        private java.lang.CharSequence mText;
        private android.text.TextDirectionHeuristic mTextDir;
        private boolean mUseBoundsForWidth;
        private int mWidth;
        private android.graphics.text.LineBreakConfig mLineBreakConfig = android.graphics.text.LineBreakConfig.NONE;
        private final android.graphics.Paint.FontMetricsInt mFontMetricsInt = new android.graphics.Paint.FontMetricsInt();

        private Builder() {
        }

        public static android.text.StaticLayout.Builder obtain(java.lang.CharSequence charSequence, int i, int i2, android.text.TextPaint textPaint, int i3) {
            android.text.StaticLayout.Builder acquire = sPool.acquire();
            if (acquire == null) {
                acquire = new android.text.StaticLayout.Builder();
            }
            acquire.mText = charSequence;
            acquire.mStart = i;
            acquire.mEnd = i2;
            acquire.mPaint = textPaint;
            acquire.mWidth = i3;
            acquire.mAlignment = android.text.Layout.Alignment.ALIGN_NORMAL;
            acquire.mTextDir = android.text.TextDirectionHeuristics.FIRSTSTRONG_LTR;
            acquire.mSpacingMult = 1.0f;
            acquire.mSpacingAdd = 0.0f;
            acquire.mIncludePad = true;
            acquire.mFallbackLineSpacing = false;
            acquire.mEllipsizedWidth = i3;
            acquire.mEllipsize = null;
            acquire.mMaxLines = Integer.MAX_VALUE;
            acquire.mBreakStrategy = 0;
            acquire.mHyphenationFrequency = 0;
            acquire.mJustificationMode = 0;
            acquire.mLineBreakConfig = android.graphics.text.LineBreakConfig.NONE;
            acquire.mMinimumFontMetrics = null;
            return acquire;
        }

        private static void recycle(android.text.StaticLayout.Builder builder) {
            builder.mPaint = null;
            builder.mText = null;
            builder.mLeftIndents = null;
            builder.mRightIndents = null;
            builder.mMinimumFontMetrics = null;
            sPool.release(builder);
        }

        void finish() {
            this.mText = null;
            this.mPaint = null;
            this.mLeftIndents = null;
            this.mRightIndents = null;
            this.mMinimumFontMetrics = null;
        }

        public android.text.StaticLayout.Builder setText(java.lang.CharSequence charSequence) {
            return setText(charSequence, 0, charSequence.length());
        }

        public android.text.StaticLayout.Builder setText(java.lang.CharSequence charSequence, int i, int i2) {
            this.mText = charSequence;
            this.mStart = i;
            this.mEnd = i2;
            return this;
        }

        public android.text.StaticLayout.Builder setPaint(android.text.TextPaint textPaint) {
            this.mPaint = textPaint;
            return this;
        }

        public android.text.StaticLayout.Builder setWidth(int i) {
            this.mWidth = i;
            if (this.mEllipsize == null) {
                this.mEllipsizedWidth = i;
            }
            return this;
        }

        public android.text.StaticLayout.Builder setAlignment(android.text.Layout.Alignment alignment) {
            this.mAlignment = alignment;
            return this;
        }

        public android.text.StaticLayout.Builder setTextDirection(android.text.TextDirectionHeuristic textDirectionHeuristic) {
            this.mTextDir = textDirectionHeuristic;
            return this;
        }

        public android.text.StaticLayout.Builder setLineSpacing(float f, float f2) {
            this.mSpacingAdd = f;
            this.mSpacingMult = f2;
            return this;
        }

        public android.text.StaticLayout.Builder setIncludePad(boolean z) {
            this.mIncludePad = z;
            return this;
        }

        public android.text.StaticLayout.Builder setUseLineSpacingFromFallbacks(boolean z) {
            this.mFallbackLineSpacing = z;
            return this;
        }

        public android.text.StaticLayout.Builder setEllipsizedWidth(int i) {
            this.mEllipsizedWidth = i;
            return this;
        }

        public android.text.StaticLayout.Builder setEllipsize(android.text.TextUtils.TruncateAt truncateAt) {
            this.mEllipsize = truncateAt;
            return this;
        }

        public android.text.StaticLayout.Builder setMaxLines(int i) {
            this.mMaxLines = i;
            return this;
        }

        public android.text.StaticLayout.Builder setBreakStrategy(int i) {
            this.mBreakStrategy = i;
            return this;
        }

        public android.text.StaticLayout.Builder setHyphenationFrequency(int i) {
            this.mHyphenationFrequency = i;
            return this;
        }

        public android.text.StaticLayout.Builder setIndents(int[] iArr, int[] iArr2) {
            this.mLeftIndents = iArr;
            this.mRightIndents = iArr2;
            return this;
        }

        public android.text.StaticLayout.Builder setJustificationMode(int i) {
            this.mJustificationMode = i;
            return this;
        }

        android.text.StaticLayout.Builder setAddLastLineLineSpacing(boolean z) {
            this.mAddLastLineLineSpacing = z;
            return this;
        }

        public android.text.StaticLayout.Builder setLineBreakConfig(android.graphics.text.LineBreakConfig lineBreakConfig) {
            this.mLineBreakConfig = lineBreakConfig;
            return this;
        }

        public android.text.StaticLayout.Builder setUseBoundsForWidth(boolean z) {
            this.mUseBoundsForWidth = z;
            return this;
        }

        public android.text.StaticLayout.Builder setShiftDrawingOffsetForStartOverhang(boolean z) {
            this.mShiftDrawingOffsetForStartOverhang = z;
            return this;
        }

        public android.text.StaticLayout.Builder setCalculateBounds(boolean z) {
            this.mCalculateBounds = z;
            return this;
        }

        public android.text.StaticLayout.Builder setMinimumFontMetrics(android.graphics.Paint.FontMetrics fontMetrics) {
            this.mMinimumFontMetrics = fontMetrics;
            return this;
        }

        public android.text.StaticLayout build() {
            android.text.StaticLayout staticLayout = new android.text.StaticLayout(this, this.mIncludePad, this.mEllipsize != null ? 7 : 5);
            recycle(this);
            return staticLayout;
        }

        android.text.StaticLayout buildPartialStaticLayoutForDynamicLayout(boolean z, android.text.StaticLayout staticLayout) {
            if (staticLayout == null) {
                staticLayout = new android.text.StaticLayout();
            }
            android.os.Trace.beginSection("Generating StaticLayout For DynamicLayout");
            try {
                staticLayout.generate(this, this.mIncludePad, z);
                return staticLayout;
            } finally {
                android.os.Trace.endSection();
            }
        }
    }

    private StaticLayout() {
        super(null, null, 0, null, null, 1.0f, 0.0f, false, false, 0, null, 1, 0, 0, null, null, 0, null, false, false, null);
        this.mDrawingBounds = null;
        this.mMaxLineHeight = -1;
        this.mMaximumVisibleLineCount = Integer.MAX_VALUE;
        this.mColumns = 7;
        this.mLineDirections = (android.text.Layout.Directions[]) com.android.internal.util.ArrayUtils.newUnpaddedArray(android.text.Layout.Directions.class, 2);
        this.mLines = com.android.internal.util.ArrayUtils.newUnpaddedIntArray(this.mColumns * 2);
    }

    @java.lang.Deprecated
    public StaticLayout(java.lang.CharSequence charSequence, android.text.TextPaint textPaint, int i, android.text.Layout.Alignment alignment, float f, float f2, boolean z) {
        this(charSequence, 0, charSequence.length(), textPaint, i, alignment, f, f2, z);
    }

    @java.lang.Deprecated
    public StaticLayout(java.lang.CharSequence charSequence, int i, int i2, android.text.TextPaint textPaint, int i3, android.text.Layout.Alignment alignment, float f, float f2, boolean z) {
        this(charSequence, i, i2, textPaint, i3, alignment, f, f2, z, null, 0);
    }

    @java.lang.Deprecated
    public StaticLayout(java.lang.CharSequence charSequence, int i, int i2, android.text.TextPaint textPaint, int i3, android.text.Layout.Alignment alignment, float f, float f2, boolean z, android.text.TextUtils.TruncateAt truncateAt, int i4) {
        this(charSequence, i, i2, textPaint, i3, alignment, android.text.TextDirectionHeuristics.FIRSTSTRONG_LTR, f, f2, z, truncateAt, i4, Integer.MAX_VALUE);
    }

    @java.lang.Deprecated
    public StaticLayout(java.lang.CharSequence charSequence, int i, int i2, android.text.TextPaint textPaint, int i3, android.text.Layout.Alignment alignment, android.text.TextDirectionHeuristic textDirectionHeuristic, float f, float f2, boolean z, android.text.TextUtils.TruncateAt truncateAt, int i4, int i5) {
        this(android.text.StaticLayout.Builder.obtain(charSequence, i, i2, textPaint, i3).setAlignment(alignment).setTextDirection(textDirectionHeuristic).setLineSpacing(f2, f).setIncludePad(z).setEllipsize(truncateAt).setEllipsizedWidth(i4).setMaxLines(i5), z, truncateAt != null ? 7 : 5);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private StaticLayout(android.text.StaticLayout.Builder builder, boolean z, int i) {
        super(r2, builder.mPaint, builder.mWidth, builder.mAlignment, builder.mTextDir, builder.mSpacingMult, builder.mSpacingAdd, builder.mIncludePad, builder.mFallbackLineSpacing, builder.mEllipsizedWidth, builder.mEllipsize, builder.mMaxLines, builder.mBreakStrategy, builder.mHyphenationFrequency, builder.mLeftIndents, builder.mRightIndents, builder.mJustificationMode, builder.mLineBreakConfig, builder.mUseBoundsForWidth, builder.mShiftDrawingOffsetForStartOverhang, builder.mMinimumFontMetrics);
        java.lang.CharSequence spannedEllipsizer;
        if (builder.mEllipsize == null) {
            spannedEllipsizer = builder.mText;
        } else {
            spannedEllipsizer = builder.mText instanceof android.text.Spanned ? new android.text.Layout.SpannedEllipsizer(builder.mText) : new android.text.Layout.Ellipsizer(builder.mText);
        }
        this.mDrawingBounds = null;
        this.mMaxLineHeight = -1;
        this.mMaximumVisibleLineCount = Integer.MAX_VALUE;
        this.mColumns = i;
        if (builder.mEllipsize != null) {
            android.text.Layout.Ellipsizer ellipsizer = (android.text.Layout.Ellipsizer) getText();
            ellipsizer.mLayout = this;
            ellipsizer.mWidth = builder.mEllipsizedWidth;
            ellipsizer.mMethod = builder.mEllipsize;
        }
        this.mLineDirections = (android.text.Layout.Directions[]) com.android.internal.util.ArrayUtils.newUnpaddedArray(android.text.Layout.Directions.class, 2);
        this.mLines = com.android.internal.util.ArrayUtils.newUnpaddedIntArray(this.mColumns * 2);
        this.mMaximumVisibleLineCount = builder.mMaxLines;
        this.mLeftIndents = builder.mLeftIndents;
        this.mRightIndents = builder.mRightIndents;
        android.os.Trace.beginSection("Constructing StaticLayout");
        try {
            generate(builder, builder.mIncludePad, z);
        } finally {
            android.os.Trace.endSection();
        }
    }

    private static int getBaseHyphenationFrequency(int i) {
        switch (i) {
            case 1:
            case 3:
                return 1;
            case 2:
            case 4:
                return 2;
            default:
                return 0;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:155:0x060f A[LOOP:0: B:30:0x01f0->B:155:0x060f, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:156:0x060c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:160:0x0661  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x0677  */
    /* JADX WARN: Removed duplicated region for block: B:171:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:173:0x066e  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x0398  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x033f  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x0645 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:201:0x01e3  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x01c0  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x01f5  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0320  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0350 A[LOOP:3: B:62:0x034e->B:63:0x0350, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0387  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x039d A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x040f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void generate(android.text.StaticLayout.Builder builder, boolean z, boolean z2) {
        int[] iArr;
        int i;
        int i2;
        int i3;
        int i4;
        android.graphics.text.LineBreaker.ParagraphConstraints paragraphConstraints;
        android.graphics.text.LineBreaker lineBreaker;
        int i5;
        android.graphics.Paint.FontMetricsInt fontMetricsInt;
        int i6;
        int i7;
        int i8;
        android.text.TextUtils.TruncateAt truncateAt;
        float f;
        android.text.Spanned spanned;
        android.text.PrecomputedText.ParagraphInfo[] paragraphInfoArr;
        android.text.PrecomputedText.ParagraphInfo[] paragraphInfoArr2;
        int[] iArr2;
        int i9;
        float[] fArr;
        float[] fArr2;
        float[] fArr3;
        boolean[] zArr;
        int[] iArr3;
        int[] iArr4;
        int i10;
        int i11;
        android.text.TextDirectionHeuristic textDirectionHeuristic;
        android.text.TextPaint textPaint;
        int i12;
        int i13;
        java.lang.CharSequence charSequence;
        android.text.StaticLayout staticLayout;
        float f2;
        android.text.TextUtils.TruncateAt truncateAt2;
        int i14;
        android.graphics.Paint.FontMetricsInt fontMetricsInt2;
        int i15;
        int i16;
        int i17;
        int i18;
        java.lang.CharSequence charSequence2;
        android.text.TextPaint textPaint2;
        float[] fArr4;
        boolean[] zArr2;
        int[] iArr5;
        android.text.TextDirectionHeuristic textDirectionHeuristic2;
        android.text.TextPaint textPaint3;
        int i19;
        int i20;
        int[] iArr6;
        int i21;
        int i22;
        int i23;
        android.text.style.LineHeightSpan[] lineHeightSpanArr;
        android.text.Spanned spanned2;
        float[] fArr5;
        int lineCount;
        int[] iArr7;
        int i24;
        float[] fArr6;
        float[] fArr7;
        float[] fArr8;
        boolean[] zArr3;
        int[] iArr8;
        int i25;
        int i26;
        android.text.TextUtils.TruncateAt truncateAt3;
        boolean z3;
        android.text.TextUtils.TruncateAt truncateAt4;
        int i27;
        int i28;
        int i29;
        android.graphics.text.LineBreaker.ParagraphConstraints paragraphConstraints2;
        int i30;
        android.graphics.text.LineBreaker lineBreaker2;
        android.text.PrecomputedText.ParagraphInfo[] paragraphInfoArr3;
        android.text.Spanned spanned3;
        int i31;
        int i32;
        int i33;
        int i34;
        int i35;
        int i36;
        int i37;
        int i38;
        android.text.TextUtils.TruncateAt truncateAt5;
        android.text.StaticLayout staticLayout2 = this;
        java.lang.CharSequence charSequence3 = builder.mText;
        int i39 = builder.mStart;
        int i40 = builder.mEnd;
        android.text.TextPaint textPaint4 = builder.mPaint;
        int i41 = builder.mWidth;
        android.text.TextDirectionHeuristic textDirectionHeuristic3 = builder.mTextDir;
        float f3 = builder.mSpacingMult;
        float f4 = builder.mSpacingAdd;
        float f5 = builder.mEllipsizedWidth;
        android.text.TextUtils.TruncateAt truncateAt6 = builder.mEllipsize;
        boolean z4 = builder.mAddLastLineLineSpacing;
        staticLayout2.mLineCount = 0;
        staticLayout2.mEllipsized = false;
        staticLayout2.mMaxLineHeight = staticLayout2.mMaximumVisibleLineCount < 1 ? 0 : -1;
        staticLayout2.mDrawingBounds = null;
        boolean z5 = builder.mFallbackLineSpacing;
        boolean z6 = (f3 == 1.0f && f4 == 0.0f) ? false : true;
        android.graphics.Paint.FontMetricsInt fontMetricsInt3 = builder.mFontMetricsInt;
        if (staticLayout2.mLeftIndents != null || staticLayout2.mRightIndents != null) {
            int length = staticLayout2.mLeftIndents == null ? 0 : staticLayout2.mLeftIndents.length;
            int length2 = staticLayout2.mRightIndents == null ? 0 : staticLayout2.mRightIndents.length;
            iArr = new int[java.lang.Math.max(length, length2)];
            for (int i42 = 0; i42 < length; i42++) {
                iArr[i42] = staticLayout2.mLeftIndents[i42];
            }
            for (int i43 = 0; i43 < length2; i43++) {
                iArr[i43] = iArr[i43] + staticLayout2.mRightIndents[i43];
            }
        } else {
            iArr = null;
        }
        if (android.text.ClientFlags.fixLineHeightForLocale() && builder.mMinimumFontMetrics != null) {
            int floor = (int) java.lang.Math.floor(builder.mMinimumFontMetrics.top);
            int round = java.lang.Math.round(builder.mMinimumFontMetrics.ascent);
            int round2 = java.lang.Math.round(builder.mMinimumFontMetrics.descent);
            int ceil = (int) java.lang.Math.ceil(builder.mMinimumFontMetrics.bottom);
            int min = java.lang.Math.min(floor, round);
            int max = java.lang.Math.max(ceil, round2);
            i4 = min;
            i3 = round;
            i2 = round2;
            i = max;
        } else {
            i = 0;
            i2 = 0;
            i3 = 0;
            i4 = 0;
        }
        android.graphics.text.LineBreaker build = new android.graphics.text.LineBreaker.Builder().setBreakStrategy(builder.mBreakStrategy).setHyphenationFrequency(getBaseHyphenationFrequency(builder.mHyphenationFrequency)).setJustificationMode(builder.mJustificationMode).setIndents(iArr).setUseBoundsForWidth(builder.mUseBoundsForWidth).build();
        android.graphics.text.LineBreaker.ParagraphConstraints paragraphConstraints3 = new android.graphics.text.LineBreaker.ParagraphConstraints();
        android.text.Spanned spanned4 = charSequence3 instanceof android.text.Spanned ? (android.text.Spanned) charSequence3 : null;
        if (!(charSequence3 instanceof android.text.PrecomputedText)) {
            paragraphConstraints = paragraphConstraints3;
            lineBreaker = build;
            i5 = i;
            fontMetricsInt = fontMetricsInt3;
            i6 = i2;
            i7 = i3;
            i8 = i4;
            truncateAt = truncateAt6;
            f = f5;
            spanned = spanned4;
        } else {
            android.text.PrecomputedText precomputedText = (android.text.PrecomputedText) charSequence3;
            f = f5;
            spanned = spanned4;
            truncateAt = truncateAt6;
            lineBreaker = build;
            i5 = i;
            fontMetricsInt = fontMetricsInt3;
            i6 = i2;
            i7 = i3;
            paragraphConstraints = paragraphConstraints3;
            i8 = i4;
            switch (precomputedText.checkResultUsable(i39, i40, textDirectionHeuristic3, textPaint4, builder.mBreakStrategy, builder.mHyphenationFrequency, builder.mLineBreakConfig)) {
                case 1:
                    paragraphInfoArr = android.text.PrecomputedText.create(precomputedText, new android.text.PrecomputedText.Params.Builder(textPaint4).setBreakStrategy(builder.mBreakStrategy).setHyphenationFrequency(builder.mHyphenationFrequency).setTextDirection(textDirectionHeuristic3).setLineBreakConfig(builder.mLineBreakConfig).build()).getParagraphInfo();
                    break;
                case 2:
                    paragraphInfoArr = precomputedText.getParagraphInfo();
                    break;
            }
            if (paragraphInfoArr == null) {
                paragraphInfoArr2 = paragraphInfoArr;
            } else {
                paragraphInfoArr2 = android.text.PrecomputedText.createMeasuredParagraphs(charSequence3, new android.text.PrecomputedText.Params(textPaint4, builder.mLineBreakConfig, textDirectionHeuristic3, builder.mBreakStrategy, builder.mHyphenationFrequency), i39, i40, false, builder.mCalculateBounds);
            }
            iArr2 = null;
            i9 = 0;
            fArr = null;
            fArr2 = null;
            fArr3 = null;
            zArr = null;
            iArr3 = null;
            iArr4 = null;
            i10 = 0;
            i11 = 0;
            while (true) {
                float[] fArr9 = fArr;
                if (i10 >= paragraphInfoArr2.length) {
                    int i44 = i10 == 0 ? i39 : paragraphInfoArr2[i10 - 1].paragraphEnd;
                    float[] fArr10 = fArr2;
                    int i45 = paragraphInfoArr2[i10].paragraphEnd;
                    if (spanned == null) {
                        fArr4 = fArr3;
                        zArr2 = zArr;
                        iArr5 = iArr4;
                        textDirectionHeuristic2 = textDirectionHeuristic3;
                        textPaint3 = textPaint4;
                        i19 = i39;
                        i20 = 0;
                        iArr6 = iArr3;
                        i21 = i41;
                        i22 = i21;
                        i23 = 1;
                        lineHeightSpanArr = null;
                    } else {
                        fArr4 = fArr3;
                        android.text.style.LeadingMarginSpan[] leadingMarginSpanArr = (android.text.style.LeadingMarginSpan[]) getParagraphSpans(spanned, i44, i45, android.text.style.LeadingMarginSpan.class);
                        zArr2 = zArr;
                        iArr5 = iArr4;
                        int i46 = i41;
                        int i47 = i46;
                        int i48 = 1;
                        int i49 = 0;
                        while (true) {
                            textDirectionHeuristic2 = textDirectionHeuristic3;
                            if (i49 < leadingMarginSpanArr.length) {
                                android.text.style.LeadingMarginSpan leadingMarginSpan = leadingMarginSpanArr[i49];
                                android.text.TextPaint textPaint5 = textPaint4;
                                int i50 = i39;
                                i46 -= leadingMarginSpanArr[i49].getLeadingMargin(true);
                                i47 -= leadingMarginSpanArr[i49].getLeadingMargin(false);
                                if (leadingMarginSpan instanceof android.text.style.LeadingMarginSpan.LeadingMarginSpan2) {
                                    i48 = java.lang.Math.max(i48, ((android.text.style.LeadingMarginSpan.LeadingMarginSpan2) leadingMarginSpan).getLeadingMarginLineCount());
                                }
                                i49++;
                                textDirectionHeuristic3 = textDirectionHeuristic2;
                                textPaint4 = textPaint5;
                                i39 = i50;
                            } else {
                                textPaint3 = textPaint4;
                                i19 = i39;
                                i20 = 0;
                                android.text.style.LineHeightSpan[] lineHeightSpanArr2 = (android.text.style.LineHeightSpan[]) getParagraphSpans(spanned, i44, i45, android.text.style.LineHeightSpan.class);
                                if (lineHeightSpanArr2.length == 0) {
                                    i23 = i48;
                                    iArr6 = iArr3;
                                    i21 = i46;
                                    i22 = i47;
                                    lineHeightSpanArr = null;
                                } else {
                                    if (iArr3 == null || iArr3.length < lineHeightSpanArr2.length) {
                                        iArr3 = com.android.internal.util.ArrayUtils.newUnpaddedIntArray(lineHeightSpanArr2.length);
                                    }
                                    for (int i51 = 0; i51 < lineHeightSpanArr2.length; i51++) {
                                        int spanStart = spanned.getSpanStart(lineHeightSpanArr2[i51]);
                                        if (spanStart < i44) {
                                            iArr3[i51] = staticLayout2.getLineTop(staticLayout2.getLineForOffset(spanStart));
                                        } else {
                                            iArr3[i51] = i11;
                                        }
                                    }
                                    lineHeightSpanArr = lineHeightSpanArr2;
                                    i23 = i48;
                                    iArr6 = iArr3;
                                    i21 = i46;
                                    i22 = i47;
                                }
                            }
                        }
                    }
                    if (spanned == null) {
                        spanned2 = spanned;
                    } else {
                        android.text.style.TabStopSpan[] tabStopSpanArr = (android.text.style.TabStopSpan[]) getParagraphSpans(spanned, i44, i45, android.text.style.TabStopSpan.class);
                        if (tabStopSpanArr.length <= 0) {
                            spanned2 = spanned;
                        } else {
                            int length3 = tabStopSpanArr.length;
                            float[] fArr11 = new float[length3];
                            while (true) {
                                spanned2 = spanned;
                                if (i20 < tabStopSpanArr.length) {
                                    fArr11[i20] = tabStopSpanArr[i20].getTabStop();
                                    i20++;
                                    spanned = spanned2;
                                } else {
                                    java.util.Arrays.sort(fArr11, 0, length3);
                                    fArr5 = fArr11;
                                    android.text.MeasuredParagraph measuredParagraph = paragraphInfoArr2[i10].measured;
                                    char[] chars = measuredParagraph.getChars();
                                    int[] rawArray = measuredParagraph.getSpanEndCache().getRawArray();
                                    int[] rawArray2 = measuredParagraph.getFontMetrics().getRawArray();
                                    android.graphics.text.LineBreaker.ParagraphConstraints paragraphConstraints4 = paragraphConstraints;
                                    paragraphConstraints4.setWidth(i22);
                                    paragraphConstraints4.setIndent(i21, i23);
                                    paragraphConstraints4.setTabStops(fArr5, TAB_INCREMENT);
                                    android.graphics.text.LineBreaker lineBreaker3 = lineBreaker;
                                    android.graphics.text.LineBreaker.Result computeLineBreaks = lineBreaker3.computeLineBreaks(measuredParagraph.getMeasuredText(), paragraphConstraints4, staticLayout2.mLineCount);
                                    lineCount = computeLineBreaks.getLineCount();
                                    if (i9 < lineCount) {
                                        iArr7 = iArr2;
                                        i24 = i9;
                                        fArr6 = fArr9;
                                        fArr7 = fArr10;
                                        fArr8 = fArr4;
                                        zArr3 = zArr2;
                                        iArr8 = iArr5;
                                    } else {
                                        iArr8 = new int[lineCount];
                                        i24 = lineCount;
                                        fArr6 = new float[lineCount];
                                        fArr8 = new float[lineCount];
                                        fArr7 = new float[lineCount];
                                        iArr7 = new int[lineCount];
                                        zArr3 = new boolean[lineCount];
                                    }
                                    for (i25 = 0; i25 < lineCount; i25++) {
                                        iArr7[i25] = computeLineBreaks.getLineBreakOffset(i25);
                                        fArr6[i25] = computeLineBreaks.getLineWidth(i25);
                                        fArr7[i25] = computeLineBreaks.getLineAscent(i25);
                                        fArr8[i25] = computeLineBreaks.getLineDescent(i25);
                                        zArr3[i25] = computeLineBreaks.hasLineTab(i25);
                                        iArr8[i25] = packHyphenEdit(computeLineBreaks.getStartLineHyphenEdit(i25), computeLineBreaks.getEndLineHyphenEdit(i25));
                                    }
                                    i26 = staticLayout2.mMaximumVisibleLineCount - staticLayout2.mLineCount;
                                    if (truncateAt == null) {
                                        truncateAt3 = truncateAt;
                                        if (truncateAt3 == android.text.TextUtils.TruncateAt.END || (staticLayout2.mMaximumVisibleLineCount == 1 && truncateAt3 != android.text.TextUtils.TruncateAt.MARQUEE)) {
                                            z3 = true;
                                            if (i26 > 0 || i26 >= lineCount || !z3) {
                                                truncateAt4 = truncateAt3;
                                                i27 = lineCount;
                                            } else {
                                                int i52 = i26 - 1;
                                                int i53 = i52;
                                                float f6 = 0.0f;
                                                boolean z7 = false;
                                                while (i53 < lineCount) {
                                                    int i54 = i26;
                                                    if (i53 == lineCount - 1) {
                                                        f6 += fArr6[i53];
                                                        truncateAt5 = truncateAt3;
                                                    } else {
                                                        int i55 = i53 == 0 ? 0 : iArr7[i53 - 1];
                                                        while (true) {
                                                            truncateAt5 = truncateAt3;
                                                            if (i55 < iArr7[i53]) {
                                                                f6 += measuredParagraph.getCharWidthAt(i55);
                                                                i55++;
                                                                truncateAt3 = truncateAt5;
                                                            }
                                                        }
                                                    }
                                                    z7 |= zArr3[i53];
                                                    i53++;
                                                    i26 = i54;
                                                    truncateAt3 = truncateAt5;
                                                }
                                                truncateAt4 = truncateAt3;
                                                iArr7[i52] = iArr7[lineCount - 1];
                                                fArr6[i52] = f6;
                                                zArr3[i52] = z7;
                                                i27 = i26;
                                            }
                                            int i56 = i44;
                                            android.graphics.text.LineBreaker lineBreaker4 = lineBreaker3;
                                            android.text.PrecomputedText.ParagraphInfo[] paragraphInfoArr4 = paragraphInfoArr2;
                                            i28 = i11;
                                            int i57 = i5;
                                            int i58 = i6;
                                            int i59 = i7;
                                            int i60 = i8;
                                            int i61 = 0;
                                            int i62 = 0;
                                            int i63 = 0;
                                            i29 = i56;
                                            while (i29 < i45) {
                                                int i64 = i61 + 1;
                                                int i65 = rawArray[i61];
                                                int i66 = i62 * 4;
                                                int i67 = i45;
                                                android.graphics.Paint.FontMetricsInt fontMetricsInt4 = fontMetricsInt;
                                                fontMetricsInt4.top = rawArray2[i66 + 0];
                                                fontMetricsInt4.bottom = rawArray2[i66 + 1];
                                                fontMetricsInt4.ascent = rawArray2[i66 + 2];
                                                fontMetricsInt4.descent = rawArray2[i66 + 3];
                                                int i68 = i62 + 1;
                                                if (fontMetricsInt4.top < i60) {
                                                    i60 = fontMetricsInt4.top;
                                                }
                                                if (fontMetricsInt4.ascent < i59) {
                                                    i59 = fontMetricsInt4.ascent;
                                                }
                                                if (fontMetricsInt4.descent > i58) {
                                                    i58 = fontMetricsInt4.descent;
                                                }
                                                if (fontMetricsInt4.bottom <= i57) {
                                                    i31 = i63;
                                                } else {
                                                    i57 = fontMetricsInt4.bottom;
                                                    i31 = i63;
                                                }
                                                while (i31 < i27 && i44 + iArr7[i31] < i29) {
                                                    i31++;
                                                }
                                                int i69 = i31;
                                                while (i69 < i27 && iArr7[i69] + i44 <= i65) {
                                                    int i70 = iArr7[i69] + i44;
                                                    boolean z8 = i70 < i40;
                                                    if (z5) {
                                                        i59 = java.lang.Math.min(i59, java.lang.Math.round(fArr7[i69]));
                                                    }
                                                    if (z5) {
                                                        i58 = java.lang.Math.max(i58, java.lang.Math.round(fArr8[i69]));
                                                    }
                                                    if (!z5) {
                                                        i32 = i57;
                                                    } else {
                                                        if (i59 < i60) {
                                                            i60 = i59;
                                                        }
                                                        if (i58 <= i57) {
                                                            i32 = i57;
                                                        } else {
                                                            i32 = i58;
                                                        }
                                                    }
                                                    android.graphics.text.LineBreaker.ParagraphConstraints paragraphConstraints5 = paragraphConstraints4;
                                                    int i71 = i10;
                                                    android.text.TextUtils.TruncateAt truncateAt7 = truncateAt4;
                                                    float f7 = f;
                                                    android.text.Spanned spanned5 = spanned2;
                                                    int i72 = i44;
                                                    int i73 = i67;
                                                    int i74 = i65;
                                                    android.graphics.text.LineBreaker lineBreaker5 = lineBreaker4;
                                                    int i75 = i69;
                                                    android.text.PrecomputedText.ParagraphInfo[] paragraphInfoArr5 = paragraphInfoArr4;
                                                    android.text.TextDirectionHeuristic textDirectionHeuristic4 = textDirectionHeuristic2;
                                                    android.text.TextPaint textPaint6 = textPaint3;
                                                    android.text.MeasuredParagraph measuredParagraph2 = measuredParagraph;
                                                    int i76 = i40;
                                                    int i77 = i19;
                                                    int i78 = i27;
                                                    java.lang.CharSequence charSequence4 = charSequence3;
                                                    android.graphics.Paint.FontMetricsInt fontMetricsInt5 = fontMetricsInt4;
                                                    i28 = out(charSequence3, i56, i70, i59, i58, i60, i32, i28, f3, f4, lineHeightSpanArr, iArr6, fontMetricsInt4, zArr3[i69], iArr8[i69], z6, measuredParagraph2, i76, z, z2, z4, chars, i72, truncateAt4, f7, fArr6[i69], textPaint6, z8);
                                                    if (i70 < i74) {
                                                        fontMetricsInt4 = fontMetricsInt5;
                                                        i36 = i8;
                                                        int min2 = java.lang.Math.min(i36, fontMetricsInt4.top);
                                                        i33 = i5;
                                                        i37 = java.lang.Math.max(i33, fontMetricsInt4.bottom);
                                                        i35 = i7;
                                                        i38 = java.lang.Math.min(i35, fontMetricsInt4.ascent);
                                                        i34 = i6;
                                                        i58 = java.lang.Math.max(i34, fontMetricsInt4.descent);
                                                        i60 = min2;
                                                    } else {
                                                        i33 = i5;
                                                        i34 = i6;
                                                        i35 = i7;
                                                        i36 = i8;
                                                        fontMetricsInt4 = fontMetricsInt5;
                                                        i37 = 0;
                                                        i38 = 0;
                                                        i58 = 0;
                                                        i60 = 0;
                                                    }
                                                    i69 = i75 + 1;
                                                    if (this.mLineCount >= this.mMaximumVisibleLineCount && this.mEllipsized) {
                                                        return;
                                                    }
                                                    i56 = i70;
                                                    i59 = i38;
                                                    i8 = i36;
                                                    i5 = i33;
                                                    i7 = i35;
                                                    i6 = i34;
                                                    measuredParagraph = measuredParagraph2;
                                                    i27 = i78;
                                                    truncateAt4 = truncateAt7;
                                                    paragraphConstraints4 = paragraphConstraints5;
                                                    i10 = i71;
                                                    f = f7;
                                                    spanned2 = spanned5;
                                                    i67 = i73;
                                                    lineBreaker4 = lineBreaker5;
                                                    paragraphInfoArr4 = paragraphInfoArr5;
                                                    textDirectionHeuristic2 = textDirectionHeuristic4;
                                                    textPaint3 = textPaint6;
                                                    i40 = i76;
                                                    i19 = i77;
                                                    charSequence3 = charSequence4;
                                                    i65 = i74;
                                                    i57 = i37;
                                                    i44 = i72;
                                                }
                                                int i79 = i69;
                                                i29 = i65;
                                                i8 = i8;
                                                i5 = i5;
                                                i7 = i7;
                                                i6 = i6;
                                                measuredParagraph = measuredParagraph;
                                                i27 = i27;
                                                i61 = i64;
                                                truncateAt4 = truncateAt4;
                                                paragraphConstraints4 = paragraphConstraints4;
                                                i10 = i10;
                                                f = f;
                                                spanned2 = spanned2;
                                                i44 = i44;
                                                i45 = i67;
                                                lineBreaker4 = lineBreaker4;
                                                paragraphInfoArr4 = paragraphInfoArr4;
                                                i63 = i79;
                                                textDirectionHeuristic2 = textDirectionHeuristic2;
                                                textPaint3 = textPaint3;
                                                i40 = i40;
                                                i19 = i19;
                                                charSequence3 = charSequence3;
                                                fontMetricsInt = fontMetricsInt4;
                                                staticLayout2 = this;
                                                i62 = i68;
                                            }
                                            paragraphConstraints2 = paragraphConstraints4;
                                            int i80 = i40;
                                            charSequence = charSequence3;
                                            staticLayout = staticLayout2;
                                            i30 = i10;
                                            lineBreaker2 = lineBreaker4;
                                            paragraphInfoArr3 = paragraphInfoArr4;
                                            f2 = f;
                                            truncateAt2 = truncateAt4;
                                            spanned3 = spanned2;
                                            textDirectionHeuristic = textDirectionHeuristic2;
                                            i14 = i5;
                                            fontMetricsInt2 = fontMetricsInt;
                                            i15 = i6;
                                            i16 = i7;
                                            i17 = i8;
                                            textPaint = textPaint3;
                                            i13 = i19;
                                            i12 = i80;
                                            if (i45 != i12) {
                                                i10 = i30 + 1;
                                                i8 = i17;
                                                i5 = i14;
                                                i7 = i16;
                                                i6 = i15;
                                                fontMetricsInt = fontMetricsInt2;
                                                staticLayout2 = staticLayout;
                                                i11 = i28;
                                                iArr2 = iArr7;
                                                iArr3 = iArr6;
                                                i9 = i24;
                                                fArr = fArr6;
                                                fArr2 = fArr7;
                                                zArr = zArr3;
                                                iArr4 = iArr8;
                                                truncateAt = truncateAt2;
                                                paragraphConstraints = paragraphConstraints2;
                                                f = f2;
                                                spanned = spanned3;
                                                lineBreaker = lineBreaker2;
                                                paragraphInfoArr2 = paragraphInfoArr3;
                                                textDirectionHeuristic3 = textDirectionHeuristic;
                                                textPaint4 = textPaint;
                                                i39 = i13;
                                                charSequence3 = charSequence;
                                                i40 = i12;
                                                fArr3 = fArr8;
                                            } else {
                                                i11 = i28;
                                            }
                                        }
                                    } else {
                                        truncateAt3 = truncateAt;
                                    }
                                    z3 = false;
                                    if (i26 > 0) {
                                    }
                                    truncateAt4 = truncateAt3;
                                    i27 = lineCount;
                                    int i562 = i44;
                                    android.graphics.text.LineBreaker lineBreaker42 = lineBreaker3;
                                    android.text.PrecomputedText.ParagraphInfo[] paragraphInfoArr42 = paragraphInfoArr2;
                                    i28 = i11;
                                    int i572 = i5;
                                    int i582 = i6;
                                    int i592 = i7;
                                    int i602 = i8;
                                    int i612 = 0;
                                    int i622 = 0;
                                    int i632 = 0;
                                    i29 = i562;
                                    while (i29 < i45) {
                                    }
                                    paragraphConstraints2 = paragraphConstraints4;
                                    int i802 = i40;
                                    charSequence = charSequence3;
                                    staticLayout = staticLayout2;
                                    i30 = i10;
                                    lineBreaker2 = lineBreaker42;
                                    paragraphInfoArr3 = paragraphInfoArr42;
                                    f2 = f;
                                    truncateAt2 = truncateAt4;
                                    spanned3 = spanned2;
                                    textDirectionHeuristic = textDirectionHeuristic2;
                                    i14 = i5;
                                    fontMetricsInt2 = fontMetricsInt;
                                    i15 = i6;
                                    i16 = i7;
                                    i17 = i8;
                                    textPaint = textPaint3;
                                    i13 = i19;
                                    i12 = i802;
                                    if (i45 != i12) {
                                    }
                                }
                            }
                        }
                    }
                    fArr5 = null;
                    android.text.MeasuredParagraph measuredParagraph3 = paragraphInfoArr2[i10].measured;
                    char[] chars2 = measuredParagraph3.getChars();
                    int[] rawArray3 = measuredParagraph3.getSpanEndCache().getRawArray();
                    int[] rawArray22 = measuredParagraph3.getFontMetrics().getRawArray();
                    android.graphics.text.LineBreaker.ParagraphConstraints paragraphConstraints42 = paragraphConstraints;
                    paragraphConstraints42.setWidth(i22);
                    paragraphConstraints42.setIndent(i21, i23);
                    paragraphConstraints42.setTabStops(fArr5, TAB_INCREMENT);
                    android.graphics.text.LineBreaker lineBreaker32 = lineBreaker;
                    android.graphics.text.LineBreaker.Result computeLineBreaks2 = lineBreaker32.computeLineBreaks(measuredParagraph3.getMeasuredText(), paragraphConstraints42, staticLayout2.mLineCount);
                    lineCount = computeLineBreaks2.getLineCount();
                    if (i9 < lineCount) {
                    }
                    while (i25 < lineCount) {
                    }
                    i26 = staticLayout2.mMaximumVisibleLineCount - staticLayout2.mLineCount;
                    if (truncateAt == null) {
                    }
                    z3 = false;
                    if (i26 > 0) {
                    }
                    truncateAt4 = truncateAt3;
                    i27 = lineCount;
                    int i5622 = i44;
                    android.graphics.text.LineBreaker lineBreaker422 = lineBreaker32;
                    android.text.PrecomputedText.ParagraphInfo[] paragraphInfoArr422 = paragraphInfoArr2;
                    i28 = i11;
                    int i5722 = i5;
                    int i5822 = i6;
                    int i5922 = i7;
                    int i6022 = i8;
                    int i6122 = 0;
                    int i6222 = 0;
                    int i6322 = 0;
                    i29 = i5622;
                    while (i29 < i45) {
                    }
                    paragraphConstraints2 = paragraphConstraints42;
                    int i8022 = i40;
                    charSequence = charSequence3;
                    staticLayout = staticLayout2;
                    i30 = i10;
                    lineBreaker2 = lineBreaker422;
                    paragraphInfoArr3 = paragraphInfoArr422;
                    f2 = f;
                    truncateAt2 = truncateAt4;
                    spanned3 = spanned2;
                    textDirectionHeuristic = textDirectionHeuristic2;
                    i14 = i5;
                    fontMetricsInt2 = fontMetricsInt;
                    i15 = i6;
                    i16 = i7;
                    i17 = i8;
                    textPaint = textPaint3;
                    i13 = i19;
                    i12 = i8022;
                    if (i45 != i12) {
                    }
                } else {
                    textDirectionHeuristic = textDirectionHeuristic3;
                    textPaint = textPaint4;
                    i12 = i40;
                    i13 = i39;
                    charSequence = charSequence3;
                    staticLayout = staticLayout2;
                    f2 = f;
                    truncateAt2 = truncateAt;
                    i14 = i5;
                    fontMetricsInt2 = fontMetricsInt;
                    i15 = i6;
                    i16 = i7;
                    i17 = i8;
                }
            }
            i18 = i13;
            if (i12 == i18) {
                charSequence2 = charSequence;
                if (charSequence2.charAt(i12 - 1) != '\n') {
                    return;
                }
            } else {
                charSequence2 = charSequence;
            }
            if (staticLayout.mLineCount >= staticLayout.mMaximumVisibleLineCount) {
                android.text.MeasuredParagraph buildForBidi = android.text.MeasuredParagraph.buildForBidi(charSequence2, i12, i12, textDirectionHeuristic, null);
                if (i16 != 0 && i15 != 0) {
                    fontMetricsInt2.top = i17;
                    fontMetricsInt2.ascent = i16;
                    fontMetricsInt2.descent = i15;
                    fontMetricsInt2.bottom = i14;
                    textPaint2 = textPaint;
                } else {
                    textPaint2 = textPaint;
                    textPaint2.getFontMetricsInt(fontMetricsInt2);
                }
                out(charSequence2, i12, i12, fontMetricsInt2.ascent, fontMetricsInt2.descent, fontMetricsInt2.top, fontMetricsInt2.bottom, i11, f3, f4, null, null, fontMetricsInt2, false, 0, z6, buildForBidi, i12, z, z2, z4, null, i18, truncateAt2, f2, 0.0f, textPaint2, false);
                return;
            }
            return;
        }
        paragraphInfoArr = null;
        if (paragraphInfoArr == null) {
        }
        iArr2 = null;
        i9 = 0;
        fArr = null;
        fArr2 = null;
        fArr3 = null;
        zArr = null;
        iArr3 = null;
        iArr4 = null;
        i10 = 0;
        i11 = 0;
        while (true) {
            float[] fArr92 = fArr;
            if (i10 >= paragraphInfoArr2.length) {
            }
            i10 = i30 + 1;
            i8 = i17;
            i5 = i14;
            i7 = i16;
            i6 = i15;
            fontMetricsInt = fontMetricsInt2;
            staticLayout2 = staticLayout;
            i11 = i28;
            iArr2 = iArr7;
            iArr3 = iArr6;
            i9 = i24;
            fArr = fArr6;
            fArr2 = fArr7;
            zArr = zArr3;
            iArr4 = iArr8;
            truncateAt = truncateAt2;
            paragraphConstraints = paragraphConstraints2;
            f = f2;
            spanned = spanned3;
            lineBreaker = lineBreaker2;
            paragraphInfoArr2 = paragraphInfoArr3;
            textDirectionHeuristic3 = textDirectionHeuristic;
            textPaint4 = textPaint;
            i39 = i13;
            charSequence3 = charSequence;
            i40 = i12;
            fArr3 = fArr8;
        }
        i18 = i13;
        if (i12 == i18) {
        }
        if (staticLayout.mLineCount >= staticLayout.mMaximumVisibleLineCount) {
        }
    }

    private int out(java.lang.CharSequence charSequence, int i, int i2, int i3, int i4, int i5, int i6, int i7, float f, float f2, android.text.style.LineHeightSpan[] lineHeightSpanArr, int[] iArr, android.graphics.Paint.FontMetricsInt fontMetricsInt, boolean z, int i8, boolean z2, android.text.MeasuredParagraph measuredParagraph, int i9, boolean z3, boolean z4, boolean z5, char[] cArr, int i10, android.text.TextUtils.TruncateAt truncateAt, float f3, float f4, android.text.TextPaint textPaint, boolean z6) {
        int[] iArr2;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17;
        int i18;
        int i19;
        int i20;
        int i21;
        int i22;
        int i23;
        int i24;
        int i25 = this.mLineCount;
        int i26 = i25 * this.mColumns;
        int i27 = 1;
        int i28 = i26 + this.mColumns + 1;
        int[] iArr3 = this.mLines;
        int paragraphDir = measuredParagraph.getParagraphDir();
        boolean z7 = false;
        if (i28 < iArr3.length) {
            iArr2 = iArr3;
        } else {
            int[] newUnpaddedIntArray = com.android.internal.util.ArrayUtils.newUnpaddedIntArray(com.android.internal.util.GrowingArrayUtils.growSize(i28));
            java.lang.System.arraycopy(iArr3, 0, newUnpaddedIntArray, 0, iArr3.length);
            this.mLines = newUnpaddedIntArray;
            iArr2 = newUnpaddedIntArray;
        }
        if (i25 >= this.mLineDirections.length) {
            android.text.Layout.Directions[] directionsArr = (android.text.Layout.Directions[]) com.android.internal.util.ArrayUtils.newUnpaddedArray(android.text.Layout.Directions.class, com.android.internal.util.GrowingArrayUtils.growSize(i25));
            java.lang.System.arraycopy(this.mLineDirections, 0, directionsArr, 0, this.mLineDirections.length);
            this.mLineDirections = directionsArr;
        }
        if (lineHeightSpanArr == null) {
            i11 = 1;
            i12 = i25;
            i13 = i3;
            i14 = i4;
            i15 = i5;
            i16 = i6;
        } else {
            fontMetricsInt.ascent = i3;
            fontMetricsInt.descent = i4;
            fontMetricsInt.top = i5;
            fontMetricsInt.bottom = i6;
            int i29 = 0;
            while (i29 < lineHeightSpanArr.length) {
                if (lineHeightSpanArr[i29] instanceof android.text.style.LineHeightSpan.WithDensity) {
                    i22 = i29;
                    i23 = i27;
                    i24 = i25;
                    ((android.text.style.LineHeightSpan.WithDensity) lineHeightSpanArr[i29]).chooseHeight(charSequence, i, i2, iArr[i29], i7, fontMetricsInt, textPaint);
                } else {
                    i22 = i29;
                    i23 = i27;
                    i24 = i25;
                    lineHeightSpanArr[i22].chooseHeight(charSequence, i, i2, iArr[i22], i7, fontMetricsInt);
                }
                i29 = i22 + 1;
                i27 = i23;
                i25 = i24;
                z7 = false;
            }
            i11 = i27;
            i12 = i25;
            i13 = fontMetricsInt.ascent;
            i14 = fontMetricsInt.descent;
            i15 = fontMetricsInt.top;
            i16 = fontMetricsInt.bottom;
        }
        int i30 = i12 == 0 ? i11 : 0;
        int i31 = i12 + 1 == this.mMaximumVisibleLineCount ? i11 : 0;
        if (truncateAt == null) {
            i17 = 0;
        } else {
            int i32 = (z6 && this.mLineCount + i11 == this.mMaximumVisibleLineCount) ? i11 : 0;
            if (((((!(this.mMaximumVisibleLineCount == i11 && z6) && (i30 == 0 || z6)) || truncateAt == android.text.TextUtils.TruncateAt.MARQUEE) && (i30 != 0 || ((i31 == 0 && z6) || truncateAt != android.text.TextUtils.TruncateAt.END))) ? 0 : i11) == 0) {
                i17 = 0;
                this.mLines[(this.mColumns * i12) + 5] = 0;
                this.mLines[(this.mColumns * i12) + 6] = 0;
            } else {
                calculateEllipsis(i, i2, measuredParagraph, i10, f3, truncateAt, i12, f4, textPaint, i32);
                i17 = 0;
            }
        }
        if (this.mEllipsized) {
            i18 = i;
            i19 = i17;
            i20 = i11;
        } else {
            int i33 = (i10 == i9 || i9 <= 0 || charSequence.charAt(i9 + (-1)) != '\n') ? i17 : i11;
            if (i2 == i9 && i33 == 0) {
                i18 = i;
                i19 = i17;
                i20 = i11;
            } else {
                i18 = i;
                i19 = i17;
                if (i18 == i9 && i33 != 0) {
                    i20 = i11;
                } else {
                    i20 = i19;
                }
            }
        }
        if (i30 != 0) {
            if (z4) {
                this.mTopPadding = i15 - i13;
            }
            if (z3) {
                i13 = i15;
            }
        }
        if (i20 != 0) {
            if (z4) {
                this.mBottomPadding = i16 - i14;
            }
            if (z3) {
                i14 = i16;
            }
        }
        if (z2 && (z5 || i20 == 0)) {
            double d = ((i14 - i13) * (f - 1.0f)) + f2;
            if (d >= 0.0d) {
                i21 = (int) (d + EXTRA_ROUNDING);
            } else {
                i21 = -((int) ((-d) + EXTRA_ROUNDING));
            }
        } else {
            i21 = i19;
        }
        int i34 = i26 + 0;
        iArr2[i34] = i18;
        iArr2[i26 + 1] = i7;
        iArr2[i26 + 2] = i14 + i21;
        iArr2[i26 + 3] = i21;
        if (!this.mEllipsized && i31 != 0) {
            if (!z3) {
                i16 = i14;
            }
            this.mMaxLineHeight = i7 + (i16 - i13);
        }
        int i35 = i7 + (i14 - i13) + i21;
        iArr2[i26 + this.mColumns + i19] = i2;
        iArr2[i26 + this.mColumns + i11] = i35;
        iArr2[i34] = iArr2[i34] | (z ? 536870912 : i19);
        if (this.mEllipsized) {
            if (truncateAt == android.text.TextUtils.TruncateAt.START) {
                iArr2[i26 + 4] = packHyphenEdit(i19, unpackEndHyphenEdit(i8));
            } else if (truncateAt == android.text.TextUtils.TruncateAt.END) {
                iArr2[i26 + 4] = packHyphenEdit(unpackStartHyphenEdit(i8), i19);
            } else {
                iArr2[i26 + 4] = packHyphenEdit(i19, i19);
            }
        } else {
            iArr2[i26 + 4] = i8;
        }
        iArr2[i34] = iArr2[i34] | (paragraphDir << 30);
        this.mLineDirections[i12] = measuredParagraph.getDirections(i18 - i10, i2 - i10);
        this.mLineCount += i11;
        return i35;
    }

    private void calculateEllipsis(int i, int i2, android.text.MeasuredParagraph measuredParagraph, int i3, float f, android.text.TextUtils.TruncateAt truncateAt, int i4, float f2, android.text.TextPaint textPaint, boolean z) {
        int i5;
        float totalInsets = f - getTotalInsets(i4);
        int i6 = 0;
        if (f2 <= totalInsets && !z) {
            this.mLines[(this.mColumns * i4) + 5] = 0;
            this.mLines[(this.mColumns * i4) + 6] = 0;
            return;
        }
        float measureText = textPaint.measureText(android.text.TextUtils.getEllipsisString(truncateAt));
        int i7 = i2 - i;
        float f3 = 0.0f;
        if (truncateAt == android.text.TextUtils.TruncateAt.START) {
            if (this.mMaximumVisibleLineCount == 1) {
                int i8 = i7;
                float f4 = 0.0f;
                while (true) {
                    if (i8 <= 0) {
                        break;
                    }
                    f4 += measuredParagraph.getCharWidthAt(((i8 - 1) + i) - i3);
                    if (f4 + measureText <= totalInsets) {
                        i8--;
                    } else {
                        while (i8 < i7 && measuredParagraph.getCharWidthAt((i8 + i) - i3) == 0.0f) {
                            i8++;
                        }
                    }
                }
                i5 = i8;
            } else {
                if (android.util.Log.isLoggable(TAG, 5)) {
                    android.util.Log.w(TAG, "Start Ellipsis only supported with one line");
                }
                i5 = 0;
            }
        } else if (truncateAt == android.text.TextUtils.TruncateAt.END || truncateAt == android.text.TextUtils.TruncateAt.MARQUEE || truncateAt == android.text.TextUtils.TruncateAt.END_SMALL) {
            while (i6 < i7) {
                f3 += measuredParagraph.getCharWidthAt((i6 + i) - i3);
                if (f3 + measureText > totalInsets) {
                    break;
                } else {
                    i6++;
                }
            }
            i5 = i7 - i6;
            if (z && i5 == 0 && i7 > 0) {
                i6 = i7 - 1;
                i5 = 1;
            }
        } else if (this.mMaximumVisibleLineCount == 1) {
            float f5 = totalInsets - measureText;
            float f6 = f5 / 2.0f;
            int i9 = i7;
            float f7 = 0.0f;
            while (true) {
                if (i9 <= 0) {
                    break;
                }
                float charWidthAt = measuredParagraph.getCharWidthAt(((i9 - 1) + i) - i3) + f7;
                if (charWidthAt <= f6) {
                    i9--;
                    f7 = charWidthAt;
                } else {
                    while (i9 < i7 && measuredParagraph.getCharWidthAt((i9 + i) - i3) == 0.0f) {
                        i9++;
                    }
                }
            }
            float f8 = f5 - f7;
            while (i6 < i9) {
                f3 += measuredParagraph.getCharWidthAt((i6 + i) - i3);
                if (f3 > f8) {
                    break;
                } else {
                    i6++;
                }
            }
            i5 = i9 - i6;
        } else {
            if (android.util.Log.isLoggable(TAG, 5)) {
                android.util.Log.w(TAG, "Middle Ellipsis only supported with one line");
            }
            i5 = 0;
        }
        this.mEllipsized = true;
        this.mLines[(this.mColumns * i4) + 5] = i6;
        this.mLines[(this.mColumns * i4) + 6] = i5;
    }

    private float getTotalInsets(int i) {
        int i2;
        if (this.mLeftIndents == null) {
            i2 = 0;
        } else {
            i2 = this.mLeftIndents[java.lang.Math.min(i, this.mLeftIndents.length - 1)];
        }
        if (this.mRightIndents != null) {
            i2 += this.mRightIndents[java.lang.Math.min(i, this.mRightIndents.length - 1)];
        }
        return i2;
    }

    @Override // android.text.Layout
    public int getLineForVertical(int i) {
        int i2 = this.mLineCount;
        int[] iArr = this.mLines;
        int i3 = -1;
        while (i2 - i3 > 1) {
            int i4 = (i2 + i3) >> 1;
            if (iArr[(this.mColumns * i4) + 1] > i) {
                i2 = i4;
            } else {
                i3 = i4;
            }
        }
        if (i3 < 0) {
            return 0;
        }
        return i3;
    }

    @Override // android.text.Layout
    public int getLineCount() {
        return this.mLineCount;
    }

    @Override // android.text.Layout
    public int getLineTop(int i) {
        return this.mLines[(this.mColumns * i) + 1];
    }

    @Override // android.text.Layout
    public int getLineExtra(int i) {
        return this.mLines[(this.mColumns * i) + 3];
    }

    @Override // android.text.Layout
    public int getLineDescent(int i) {
        return this.mLines[(this.mColumns * i) + 2];
    }

    @Override // android.text.Layout
    public int getLineStart(int i) {
        return this.mLines[(this.mColumns * i) + 0] & 536870911;
    }

    @Override // android.text.Layout
    public int getParagraphDirection(int i) {
        return this.mLines[(this.mColumns * i) + 0] >> 30;
    }

    @Override // android.text.Layout
    public boolean getLineContainsTab(int i) {
        return (this.mLines[(this.mColumns * i) + 0] & 536870912) != 0;
    }

    @Override // android.text.Layout
    public final android.text.Layout.Directions getLineDirections(int i) {
        if (i > getLineCount()) {
            throw new java.lang.ArrayIndexOutOfBoundsException();
        }
        return this.mLineDirections[i];
    }

    @Override // android.text.Layout
    public int getTopPadding() {
        return this.mTopPadding;
    }

    @Override // android.text.Layout
    public int getBottomPadding() {
        return this.mBottomPadding;
    }

    static int packHyphenEdit(int i, int i2) {
        return (i << 3) | i2;
    }

    static int unpackStartHyphenEdit(int i) {
        return (i & 24) >> 3;
    }

    static int unpackEndHyphenEdit(int i) {
        return i & 7;
    }

    @Override // android.text.Layout
    public int getStartHyphenEdit(int i) {
        return unpackStartHyphenEdit(this.mLines[(this.mColumns * i) + 4] & 255);
    }

    @Override // android.text.Layout
    public int getEndHyphenEdit(int i) {
        return unpackEndHyphenEdit(this.mLines[(this.mColumns * i) + 4] & 255);
    }

    @Override // android.text.Layout
    public int getIndentAdjust(int i, android.text.Layout.Alignment alignment) {
        int i2;
        if (alignment == android.text.Layout.Alignment.ALIGN_LEFT) {
            if (this.mLeftIndents == null) {
                return 0;
            }
            return this.mLeftIndents[java.lang.Math.min(i, this.mLeftIndents.length - 1)];
        }
        if (alignment == android.text.Layout.Alignment.ALIGN_RIGHT) {
            if (this.mRightIndents == null) {
                return 0;
            }
            return -this.mRightIndents[java.lang.Math.min(i, this.mRightIndents.length - 1)];
        }
        if (alignment == android.text.Layout.Alignment.ALIGN_CENTER) {
            if (this.mLeftIndents == null) {
                i2 = 0;
            } else {
                i2 = this.mLeftIndents[java.lang.Math.min(i, this.mLeftIndents.length - 1)];
            }
            return (i2 - (this.mRightIndents != null ? this.mRightIndents[java.lang.Math.min(i, this.mRightIndents.length - 1)] : 0)) >> 1;
        }
        throw new java.lang.AssertionError("unhandled alignment " + alignment);
    }

    @Override // android.text.Layout
    public int getEllipsisCount(int i) {
        if (this.mColumns < 7) {
            return 0;
        }
        return this.mLines[(this.mColumns * i) + 6];
    }

    @Override // android.text.Layout
    public int getEllipsisStart(int i) {
        if (this.mColumns < 7) {
            return 0;
        }
        return this.mLines[(this.mColumns * i) + 5];
    }

    @Override // android.text.Layout
    public android.graphics.RectF computeDrawingBoundingBox() {
        if (this.mDrawingBounds == null) {
            this.mDrawingBounds = super.computeDrawingBoundingBox();
        }
        return this.mDrawingBounds;
    }

    @Override // android.text.Layout
    public int getHeight(boolean z) {
        if (z && this.mLineCount > this.mMaximumVisibleLineCount && this.mMaxLineHeight == -1 && android.util.Log.isLoggable(TAG, 5)) {
            android.util.Log.w(TAG, "maxLineHeight should not be -1.  maxLines:" + this.mMaximumVisibleLineCount + " lineCount:" + this.mLineCount);
        }
        return (!z || this.mLineCount <= this.mMaximumVisibleLineCount || this.mMaxLineHeight == -1) ? super.getHeight() : this.mMaxLineHeight;
    }

    static class LineBreaks {
        private static final int INITIAL_SIZE = 16;
        public int[] breaks = new int[16];
        public float[] widths = new float[16];
        public float[] ascents = new float[16];
        public float[] descents = new float[16];
        public int[] flags = new int[16];

        LineBreaks() {
        }
    }
}
