package android.text;

/* loaded from: classes3.dex */
public abstract class Layout {
    public static final int BREAK_STRATEGY_BALANCED = 2;
    public static final int BREAK_STRATEGY_HIGH_QUALITY = 1;
    public static final int BREAK_STRATEGY_SIMPLE = 0;
    public static final float DEFAULT_LINESPACING_ADDITION = 0.0f;
    public static final float DEFAULT_LINESPACING_MULTIPLIER = 1.0f;
    public static final int DIR_LEFT_TO_RIGHT = 1;
    static final int DIR_REQUEST_DEFAULT_LTR = 2;
    static final int DIR_REQUEST_DEFAULT_RTL = -2;
    static final int DIR_REQUEST_LTR = 1;
    static final int DIR_REQUEST_RTL = -1;
    public static final int DIR_RIGHT_TO_LEFT = -1;
    public static final int HYPHENATION_FREQUENCY_FULL = 2;
    public static final int HYPHENATION_FREQUENCY_FULL_FAST = 4;
    public static final int HYPHENATION_FREQUENCY_NONE = 0;
    public static final int HYPHENATION_FREQUENCY_NORMAL = 1;
    public static final int HYPHENATION_FREQUENCY_NORMAL_FAST = 3;
    public static final int JUSTIFICATION_MODE_INTER_CHARACTER = 2;
    public static final int JUSTIFICATION_MODE_INTER_WORD = 1;
    public static final int JUSTIFICATION_MODE_NONE = 0;
    static final int RUN_LEVEL_MASK = 63;
    static final int RUN_LEVEL_SHIFT = 26;
    static final int RUN_RTL_FLAG = 67108864;
    private static final float TAB_INCREMENT = 20.0f;
    public static final int TEXT_SELECTION_LAYOUT_LEFT_TO_RIGHT = 1;
    public static final int TEXT_SELECTION_LAYOUT_RIGHT_TO_LEFT = 0;
    private android.text.Layout.Alignment mAlignment;
    private int mBreakStrategy;
    private android.text.TextUtils.TruncateAt mEllipsize;
    private int mEllipsizedWidth;
    private boolean mFallbackLineSpacing;
    private int mHyphenationFrequency;
    private boolean mIncludePad;
    private int mJustificationMode;
    private int[] mLeftIndents;
    private android.text.SpanSet<android.text.style.LineBackgroundSpan> mLineBackgroundSpans;
    private android.graphics.text.LineBreakConfig mLineBreakConfig;
    private android.text.TextLine.LineInfo mLineInfo;
    private int mMaxLines;
    private android.graphics.Paint.FontMetrics mMinimumFontMetrics;
    private android.text.TextPaint mPaint;
    private int[] mRightIndents;
    private boolean mShiftDrawingOffsetForStartOverhang;
    private float mSpacingAdd;
    private float mSpacingMult;
    private boolean mSpannedText;
    private java.lang.CharSequence mText;
    private android.text.TextDirectionHeuristic mTextDir;
    private boolean mUseBoundsForWidth;
    private int mWidth;
    private final android.text.TextPaint mWorkPaint;
    private final android.graphics.Paint mWorkPlainPaint;
    private static final android.text.style.ParagraphStyle[] NO_PARA_SPANS = (android.text.style.ParagraphStyle[]) com.android.internal.util.ArrayUtils.emptyArray(android.text.style.ParagraphStyle.class);
    public static final android.text.Layout.TextInclusionStrategy INCLUSION_STRATEGY_ANY_OVERLAP = new android.text.Layout.TextInclusionStrategy() { // from class: android.text.Layout$$ExternalSyntheticLambda1
        @Override // android.text.Layout.TextInclusionStrategy
        public final boolean isSegmentInside(android.graphics.RectF rectF, android.graphics.RectF rectF2) {
            return android.graphics.RectF.intersects(rectF, rectF2);
        }
    };
    public static final android.text.Layout.TextInclusionStrategy INCLUSION_STRATEGY_CONTAINS_CENTER = new android.text.Layout.TextInclusionStrategy() { // from class: android.text.Layout$$ExternalSyntheticLambda2
        @Override // android.text.Layout.TextInclusionStrategy
        public final boolean isSegmentInside(android.graphics.RectF rectF, android.graphics.RectF rectF2) {
            boolean contains;
            contains = rectF2.contains(rectF.centerX(), rectF.centerY());
            return contains;
        }
    };
    public static final android.text.Layout.TextInclusionStrategy INCLUSION_STRATEGY_CONTAINS_ALL = new android.text.Layout.TextInclusionStrategy() { // from class: android.text.Layout$$ExternalSyntheticLambda3
        @Override // android.text.Layout.TextInclusionStrategy
        public final boolean isSegmentInside(android.graphics.RectF rectF, android.graphics.RectF rectF2) {
            boolean contains;
            contains = rectF2.contains(rectF);
            return contains;
        }
    };
    private static final android.graphics.Rect sTempRect = new android.graphics.Rect();
    static final int RUN_LENGTH_MASK = 67108863;
    public static final android.text.Layout.Directions DIRS_ALL_LEFT_TO_RIGHT = new android.text.Layout.Directions(new int[]{0, RUN_LENGTH_MASK});
    public static final android.text.Layout.Directions DIRS_ALL_RIGHT_TO_LEFT = new android.text.Layout.Directions(new int[]{0, 134217727});

    public enum Alignment {
        ALIGN_NORMAL,
        ALIGN_OPPOSITE,
        ALIGN_CENTER,
        ALIGN_LEFT,
        ALIGN_RIGHT
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BreakStrategy {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Direction {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface HyphenationFrequency {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface JustificationMode {
    }

    @java.lang.FunctionalInterface
    public interface SelectionRectangleConsumer {
        void accept(float f, float f2, float f3, float f4, int i);
    }

    @java.lang.FunctionalInterface
    public interface TextInclusionStrategy {
        boolean isSegmentInside(android.graphics.RectF rectF, android.graphics.RectF rectF2);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TextSelectionLayout {
    }

    public abstract int getBottomPadding();

    public abstract int getEllipsisCount(int i);

    public abstract int getEllipsisStart(int i);

    public abstract boolean getLineContainsTab(int i);

    public abstract int getLineCount();

    public abstract int getLineDescent(int i);

    public abstract android.text.Layout.Directions getLineDirections(int i);

    public abstract int getLineStart(int i);

    public abstract int getLineTop(int i);

    public abstract int getParagraphDirection(int i);

    public abstract int getTopPadding();

    public static float getDesiredWidth(java.lang.CharSequence charSequence, android.text.TextPaint textPaint) {
        return getDesiredWidth(charSequence, 0, charSequence.length(), textPaint);
    }

    public static float getDesiredWidth(java.lang.CharSequence charSequence, int i, int i2, android.text.TextPaint textPaint) {
        return getDesiredWidth(charSequence, i, i2, textPaint, android.text.TextDirectionHeuristics.FIRSTSTRONG_LTR);
    }

    public static float getDesiredWidth(java.lang.CharSequence charSequence, int i, int i2, android.text.TextPaint textPaint, android.text.TextDirectionHeuristic textDirectionHeuristic) {
        return getDesiredWidthWithLimit(charSequence, i, i2, textPaint, textDirectionHeuristic, Float.MAX_VALUE, false);
    }

    public static float getDesiredWidthWithLimit(java.lang.CharSequence charSequence, int i, int i2, android.text.TextPaint textPaint, android.text.TextDirectionHeuristic textDirectionHeuristic, float f, boolean z) {
        float f2 = 0.0f;
        int i3 = i;
        while (i3 <= i2) {
            int indexOf = android.text.TextUtils.indexOf(charSequence, '\n', i3, i2);
            if (indexOf < 0) {
                indexOf = i2;
            }
            float measurePara = measurePara(textPaint, charSequence, i3, indexOf, textDirectionHeuristic, z);
            if (measurePara > f) {
                return f;
            }
            if (measurePara > f2) {
                f2 = measurePara;
            }
            i3 = indexOf + 1;
        }
        return f2;
    }

    protected Layout(java.lang.CharSequence charSequence, android.text.TextPaint textPaint, int i, android.text.Layout.Alignment alignment, float f, float f2) {
        this(charSequence, textPaint, i, alignment, android.text.TextDirectionHeuristics.FIRSTSTRONG_LTR, f, f2, false, false, 0, null, Integer.MAX_VALUE, 0, 0, null, null, 0, android.graphics.text.LineBreakConfig.NONE, false, false, null);
    }

    protected Layout(java.lang.CharSequence charSequence, android.text.TextPaint textPaint, int i, android.text.Layout.Alignment alignment, android.text.TextDirectionHeuristic textDirectionHeuristic, float f, float f2, boolean z, boolean z2, int i2, android.text.TextUtils.TruncateAt truncateAt, int i3, int i4, int i5, int[] iArr, int[] iArr2, int i6, android.graphics.text.LineBreakConfig lineBreakConfig, boolean z3, boolean z4, android.graphics.Paint.FontMetrics fontMetrics) {
        int i7 = i;
        this.mWorkPaint = new android.text.TextPaint();
        this.mWorkPlainPaint = new android.graphics.Paint();
        this.mAlignment = android.text.Layout.Alignment.ALIGN_NORMAL;
        this.mLineInfo = null;
        if (i7 < 0) {
            throw new java.lang.IllegalArgumentException("Layout: " + i + " < 0");
        }
        if (textPaint != null) {
            textPaint.bgColor = 0;
            textPaint.baselineShift = 0;
        }
        this.mText = charSequence;
        this.mPaint = textPaint;
        this.mWidth = i7;
        this.mAlignment = alignment;
        this.mSpacingMult = f;
        this.mSpacingAdd = f2;
        this.mSpannedText = charSequence instanceof android.text.Spanned;
        this.mTextDir = textDirectionHeuristic;
        this.mIncludePad = z;
        this.mFallbackLineSpacing = z2;
        this.mEllipsizedWidth = truncateAt != null ? i2 : i7;
        this.mEllipsize = truncateAt;
        this.mMaxLines = i3;
        this.mBreakStrategy = i4;
        this.mHyphenationFrequency = i5;
        this.mLeftIndents = iArr;
        this.mRightIndents = iArr2;
        this.mJustificationMode = i6;
        this.mLineBreakConfig = lineBreakConfig;
        this.mUseBoundsForWidth = z3;
        this.mShiftDrawingOffsetForStartOverhang = z4;
        this.mMinimumFontMetrics = fontMetrics;
    }

    void replaceWith(java.lang.CharSequence charSequence, android.text.TextPaint textPaint, int i, android.text.Layout.Alignment alignment, float f, float f2) {
        if (i < 0) {
            throw new java.lang.IllegalArgumentException("Layout: " + i + " < 0");
        }
        this.mText = charSequence;
        this.mPaint = textPaint;
        this.mWidth = i;
        this.mAlignment = alignment;
        this.mSpacingMult = f;
        this.mSpacingAdd = f2;
        this.mSpannedText = charSequence instanceof android.text.Spanned;
    }

    public void draw(android.graphics.Canvas canvas) {
        draw(canvas, null, null, 0);
    }

    public void draw(android.graphics.Canvas canvas, android.graphics.Path path, android.graphics.Paint paint, int i) {
        draw(canvas, null, null, path, paint, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x002d A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void draw(android.graphics.Canvas canvas, java.util.List<android.graphics.Path> list, java.util.List<android.graphics.Paint> list2, android.graphics.Path path, android.graphics.Paint paint, int i) {
        float f;
        int unpackRangeEndFromLong;
        if (this.mUseBoundsForWidth && this.mShiftDrawingOffsetForStartOverhang) {
            android.graphics.RectF computeDrawingBoundingBox = computeDrawingBoundingBox();
            if (computeDrawingBoundingBox.left < 0.0f) {
                float f2 = -computeDrawingBoundingBox.left;
                canvas.translate(f2, 0.0f);
                f = f2;
                long lineRangeForDraw = getLineRangeForDraw(canvas);
                int unpackRangeStartFromLong = android.text.TextUtils.unpackRangeStartFromLong(lineRangeForDraw);
                unpackRangeEndFromLong = android.text.TextUtils.unpackRangeEndFromLong(lineRangeForDraw);
                if (unpackRangeEndFromLong >= 0) {
                    return;
                }
                if (shouldDrawHighlightsOnTop(canvas)) {
                    drawBackground(canvas, unpackRangeStartFromLong, unpackRangeEndFromLong);
                } else {
                    drawWithoutText(canvas, list, list2, path, paint, i, unpackRangeStartFromLong, unpackRangeEndFromLong);
                }
                drawText(canvas, unpackRangeStartFromLong, unpackRangeEndFromLong);
                if (shouldDrawHighlightsOnTop(canvas)) {
                    drawHighlights(canvas, list, list2, path, paint, i, unpackRangeStartFromLong, unpackRangeEndFromLong);
                }
                if (f != 0.0f) {
                    canvas.translate(-f, 0.0f);
                    return;
                }
                return;
            }
        }
        f = 0.0f;
        long lineRangeForDraw2 = getLineRangeForDraw(canvas);
        int unpackRangeStartFromLong2 = android.text.TextUtils.unpackRangeStartFromLong(lineRangeForDraw2);
        unpackRangeEndFromLong = android.text.TextUtils.unpackRangeEndFromLong(lineRangeForDraw2);
        if (unpackRangeEndFromLong >= 0) {
        }
    }

    private static boolean shouldDrawHighlightsOnTop(android.graphics.Canvas canvas) {
        return com.android.graphics.hwui.flags.Flags.highContrastTextSmallTextRect() && canvas.isHighContrastTextEnabled();
    }

    private static android.graphics.Paint setToHighlightPaint(android.graphics.Paint paint, android.graphics.BlendMode blendMode, android.graphics.Paint paint2) {
        if (paint == null) {
            return null;
        }
        paint2.set(paint);
        paint2.setBlendMode(blendMode);
        paint2.setColor(-256);
        return paint2;
    }

    public void drawText(android.graphics.Canvas canvas) {
        long lineRangeForDraw = getLineRangeForDraw(canvas);
        int unpackRangeStartFromLong = android.text.TextUtils.unpackRangeStartFromLong(lineRangeForDraw);
        int unpackRangeEndFromLong = android.text.TextUtils.unpackRangeEndFromLong(lineRangeForDraw);
        if (unpackRangeEndFromLong < 0) {
            return;
        }
        drawText(canvas, unpackRangeStartFromLong, unpackRangeEndFromLong);
    }

    public void drawBackground(android.graphics.Canvas canvas) {
        long lineRangeForDraw = getLineRangeForDraw(canvas);
        int unpackRangeStartFromLong = android.text.TextUtils.unpackRangeStartFromLong(lineRangeForDraw);
        int unpackRangeEndFromLong = android.text.TextUtils.unpackRangeEndFromLong(lineRangeForDraw);
        if (unpackRangeEndFromLong < 0) {
            return;
        }
        drawBackground(canvas, unpackRangeStartFromLong, unpackRangeEndFromLong);
    }

    public void drawWithoutText(android.graphics.Canvas canvas, java.util.List<android.graphics.Path> list, java.util.List<android.graphics.Paint> list2, android.graphics.Path path, android.graphics.Paint paint, int i, int i2, int i3) {
        drawBackground(canvas, i2, i3);
        drawHighlights(canvas, list, list2, path, paint, i, i2, i3);
    }

    public void drawHighlights(android.graphics.Canvas canvas, java.util.List<android.graphics.Path> list, java.util.List<android.graphics.Paint> list2, android.graphics.Path path, android.graphics.Paint paint, int i, int i2, int i3) {
        if (list == null && list2 == null) {
            return;
        }
        if (i != 0) {
            canvas.translate(0.0f, i);
        }
        try {
            android.graphics.BlendMode determineHighContrastHighlightBlendMode = determineHighContrastHighlightBlendMode(canvas);
            if (list != null) {
                if (list2 == null) {
                    throw new java.lang.IllegalArgumentException("if highlight is specified, highlightPaint must be specified.");
                }
                if (list2.size() != list.size()) {
                    throw new java.lang.IllegalArgumentException("The highlight path size is different from the size of highlight paints");
                }
                for (int i4 = 0; i4 < list.size(); i4++) {
                    android.graphics.Path path2 = list.get(i4);
                    android.graphics.Paint paint2 = list2.get(i4);
                    if (shouldDrawHighlightsOnTop(canvas)) {
                        paint2 = setToHighlightPaint(paint2, determineHighContrastHighlightBlendMode, this.mWorkPlainPaint);
                    }
                    if (path2 != null) {
                        canvas.drawPath(path2, paint2);
                    }
                }
            }
            if (path != null) {
                if (shouldDrawHighlightsOnTop(canvas)) {
                    paint = setToHighlightPaint(paint, determineHighContrastHighlightBlendMode, this.mWorkPlainPaint);
                }
                canvas.drawPath(path, paint);
            }
        } finally {
            if (i != 0) {
                canvas.translate(0.0f, -i);
            }
        }
    }

    private android.graphics.BlendMode determineHighContrastHighlightBlendMode(android.graphics.Canvas canvas) {
        if (shouldDrawHighlightsOnTop(canvas)) {
            return isHighContrastTextDark() ? android.graphics.BlendMode.MULTIPLY : android.graphics.BlendMode.DIFFERENCE;
        }
        return null;
    }

    private boolean isHighContrastTextDark() {
        if (com.android.graphics.hwui.flags.Flags.highContrastTextLuminance()) {
            double[] dArr = new double[3];
            com.android.internal.graphics.ColorUtils.colorToLAB(this.mPaint.getColor(), dArr);
            return dArr[0] < 0.5d;
        }
        int color = this.mPaint.getColor();
        return (android.graphics.Color.red(color) + android.graphics.Color.green(color)) + android.graphics.Color.blue(color) < 384;
    }

    private boolean isJustificationRequired(int i) {
        int lineEnd;
        return (this.mJustificationMode == 0 || (lineEnd = getLineEnd(i)) >= this.mText.length() || this.mText.charAt(lineEnd - 1) == '\n') ? false : true;
    }

    private float getJustifyWidth(int i) {
        int indentAdjust;
        android.text.Layout.Alignment alignment = this.mAlignment;
        int i2 = this.mWidth;
        int paragraphDirection = getParagraphDirection(i);
        android.text.style.ParagraphStyle[] paragraphStyleArr = NO_PARA_SPANS;
        int i3 = 0;
        if (this.mSpannedText) {
            android.text.Spanned spanned = (android.text.Spanned) this.mText;
            int lineStart = getLineStart(i);
            boolean z = lineStart == 0 || this.mText.charAt(lineStart + (-1)) == '\n';
            if (z) {
                paragraphStyleArr = (android.text.style.ParagraphStyle[]) getParagraphSpans(spanned, lineStart, spanned.nextSpanTransition(lineStart, this.mText.length(), android.text.style.ParagraphStyle.class), android.text.style.ParagraphStyle.class);
                int length = paragraphStyleArr.length - 1;
                while (true) {
                    if (length < 0) {
                        break;
                    }
                    if (!(paragraphStyleArr[length] instanceof android.text.style.AlignmentSpan)) {
                        length--;
                    } else {
                        alignment = ((android.text.style.AlignmentSpan) paragraphStyleArr[length]).getAlignment();
                        break;
                    }
                }
            }
            int length2 = paragraphStyleArr.length;
            int i4 = 0;
            while (true) {
                if (i4 >= length2) {
                    break;
                }
                if (paragraphStyleArr[i4] instanceof android.text.style.LeadingMarginSpan.LeadingMarginSpan2) {
                    if (i < getLineForOffset(spanned.getSpanStart(paragraphStyleArr[i4])) + ((android.text.style.LeadingMarginSpan.LeadingMarginSpan2) paragraphStyleArr[i4]).getLeadingMarginLineCount()) {
                        z = true;
                        break;
                    }
                }
                i4++;
            }
            int i5 = 0;
            while (i3 < length2) {
                if (paragraphStyleArr[i3] instanceof android.text.style.LeadingMarginSpan) {
                    android.text.style.LeadingMarginSpan leadingMarginSpan = (android.text.style.LeadingMarginSpan) paragraphStyleArr[i3];
                    if (paragraphDirection == -1) {
                        i2 -= leadingMarginSpan.getLeadingMargin(z);
                    } else {
                        i5 += leadingMarginSpan.getLeadingMargin(z);
                    }
                }
                i3++;
            }
            i3 = i5;
        }
        if (alignment == android.text.Layout.Alignment.ALIGN_LEFT) {
            alignment = paragraphDirection == 1 ? android.text.Layout.Alignment.ALIGN_NORMAL : android.text.Layout.Alignment.ALIGN_OPPOSITE;
        } else if (alignment == android.text.Layout.Alignment.ALIGN_RIGHT) {
            alignment = paragraphDirection == 1 ? android.text.Layout.Alignment.ALIGN_OPPOSITE : android.text.Layout.Alignment.ALIGN_NORMAL;
        }
        if (alignment == android.text.Layout.Alignment.ALIGN_NORMAL) {
            if (paragraphDirection == 1) {
                indentAdjust = getIndentAdjust(i, android.text.Layout.Alignment.ALIGN_LEFT);
            } else {
                indentAdjust = -getIndentAdjust(i, android.text.Layout.Alignment.ALIGN_RIGHT);
            }
        } else if (alignment == android.text.Layout.Alignment.ALIGN_OPPOSITE) {
            if (paragraphDirection == 1) {
                indentAdjust = -getIndentAdjust(i, android.text.Layout.Alignment.ALIGN_RIGHT);
            } else {
                indentAdjust = getIndentAdjust(i, android.text.Layout.Alignment.ALIGN_LEFT);
            }
        } else {
            indentAdjust = getIndentAdjust(i, android.text.Layout.Alignment.ALIGN_CENTER);
        }
        return (i2 - i3) - indentAdjust;
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0108  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x00ff A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void drawText(android.graphics.Canvas canvas, int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        android.text.Layout.TabStops tabStops;
        int i7;
        int i8;
        java.lang.CharSequence charSequence;
        android.text.TextLine textLine;
        int i9;
        android.text.style.ParagraphStyle[] paragraphStyleArr;
        android.text.Layout.Alignment alignment;
        int i10;
        android.text.Layout.TabStops tabStops2;
        int i11;
        int i12;
        android.text.Layout.Alignment alignment2;
        boolean z;
        int i13;
        int i14;
        android.text.TextPaint textPaint;
        android.text.TextLine textLine2;
        android.text.Layout.TabStops tabStops3;
        android.text.Layout.Alignment alignment3;
        int i15;
        boolean z2;
        boolean z3;
        boolean z4;
        int i16;
        android.text.style.ParagraphStyle[] paragraphStyleArr2;
        int length;
        int i17;
        int i18;
        boolean z5;
        int i19;
        int i20;
        int i21;
        boolean z6;
        int i22;
        android.text.Layout.TabStops tabStops4;
        int i23;
        int i24;
        java.lang.CharSequence charSequence2;
        android.text.TextLine textLine3;
        int i25;
        int i26;
        android.text.style.ParagraphStyle[] paragraphStyleArr3;
        int i27;
        android.text.Layout layout = this;
        int i28 = i;
        int lineTop = layout.getLineTop(i28);
        int lineStart = layout.getLineStart(i28);
        android.text.style.ParagraphStyle[] paragraphStyleArr4 = NO_PARA_SPANS;
        android.text.TextPaint textPaint2 = layout.mWorkPaint;
        textPaint2.set(layout.mPaint);
        java.lang.CharSequence charSequence3 = layout.mText;
        android.text.Layout.Alignment alignment4 = layout.mAlignment;
        android.text.TextLine obtain = android.text.TextLine.obtain();
        int i29 = lineTop;
        int i30 = lineStart;
        android.text.Layout.TabStops tabStops5 = null;
        int i31 = i28;
        int i32 = 0;
        boolean z7 = false;
        while (i31 <= i2) {
            int i33 = i31 + 1;
            int lineStart2 = layout.getLineStart(i33);
            boolean isJustificationRequired = layout.isJustificationRequired(i31);
            android.text.TextLine textLine4 = obtain;
            int lineVisibleEnd = layout.getLineVisibleEnd(i31, i30, lineStart2, true);
            textPaint2.setStartHyphenEdit(layout.getStartHyphenEdit(i31));
            textPaint2.setEndHyphenEdit(layout.getEndHyphenEdit(i31));
            int lineTop2 = layout.getLineTop(i33);
            int lineDescent = lineTop2 - layout.getLineDescent(i31);
            int paragraphDirection = layout.getParagraphDirection(i31);
            boolean z8 = z7;
            int i34 = layout.mWidth;
            if (!layout.mSpannedText) {
                android.text.style.ParagraphStyle[] paragraphStyleArr5 = paragraphStyleArr4;
                i3 = lineStart2;
                i4 = i33;
                i5 = i31;
                i6 = i30;
                tabStops = tabStops5;
                i7 = lineDescent;
                i8 = paragraphDirection;
                charSequence = charSequence3;
                textLine = textLine4;
                z7 = z8;
                i9 = i34;
                paragraphStyleArr = paragraphStyleArr5;
                alignment = alignment4;
                i10 = 0;
            } else {
                android.text.Spanned spanned = (android.text.Spanned) charSequence3;
                android.text.style.ParagraphStyle[] paragraphStyleArr6 = paragraphStyleArr4;
                int length2 = charSequence3.length();
                if (i30 != 0) {
                    alignment3 = alignment4;
                    i15 = lineStart2;
                    if (charSequence3.charAt(i30 - 1) != '\n') {
                        z2 = false;
                        if (i30 >= i32) {
                            z3 = true;
                        } else if (i31 == i28 || z2) {
                            int nextSpanTransition = spanned.nextSpanTransition(i30, length2, android.text.style.ParagraphStyle.class);
                            android.text.style.ParagraphStyle[] paragraphStyleArr7 = (android.text.style.ParagraphStyle[]) getParagraphSpans(spanned, i30, nextSpanTransition, android.text.style.ParagraphStyle.class);
                            android.text.Layout.Alignment alignment5 = layout.mAlignment;
                            z3 = true;
                            int length3 = paragraphStyleArr7.length - 1;
                            while (true) {
                                if (length3 < 0) {
                                    i16 = nextSpanTransition;
                                    break;
                                }
                                i16 = nextSpanTransition;
                                if (!(paragraphStyleArr7[length3] instanceof android.text.style.AlignmentSpan)) {
                                    length3--;
                                    nextSpanTransition = i16;
                                } else {
                                    alignment5 = ((android.text.style.AlignmentSpan) paragraphStyleArr7[length3]).getAlignment();
                                    break;
                                }
                            }
                            paragraphStyleArr2 = paragraphStyleArr7;
                            alignment3 = alignment5;
                            z4 = false;
                            length = paragraphStyleArr2.length;
                            i17 = 0;
                            while (true) {
                                if (i17 >= length) {
                                    i18 = i33;
                                    z5 = z2;
                                    break;
                                }
                                if (!(paragraphStyleArr2[i17] instanceof android.text.style.LeadingMarginSpan.LeadingMarginSpan2)) {
                                    i18 = i33;
                                } else {
                                    i18 = i33;
                                    if (i31 < layout.getLineForOffset(spanned.getSpanStart(paragraphStyleArr2[i17])) + ((android.text.style.LeadingMarginSpan.LeadingMarginSpan2) paragraphStyleArr2[i17]).getLeadingMarginLineCount()) {
                                        z5 = z3;
                                        break;
                                    }
                                }
                                i17++;
                                i33 = i18;
                            }
                            i19 = 0;
                            i10 = 0;
                            while (i19 < length) {
                                if (!(paragraphStyleArr2[i19] instanceof android.text.style.LeadingMarginSpan)) {
                                    i20 = i19;
                                    i21 = length;
                                    z6 = z5;
                                    i22 = i31;
                                    tabStops4 = tabStops5;
                                    i23 = lineDescent;
                                    i24 = paragraphDirection;
                                    charSequence2 = charSequence3;
                                    textLine3 = textLine4;
                                    i25 = i15;
                                    i26 = i18;
                                    paragraphStyleArr3 = paragraphStyleArr2;
                                    i27 = i30;
                                } else {
                                    android.text.style.LeadingMarginSpan leadingMarginSpan = (android.text.style.LeadingMarginSpan) paragraphStyleArr2[i19];
                                    if (paragraphDirection == -1) {
                                        i20 = i19;
                                        i21 = length;
                                        i25 = i15;
                                        paragraphStyleArr3 = paragraphStyleArr2;
                                        z6 = z5;
                                        i26 = i18;
                                        i22 = i31;
                                        i27 = i30;
                                        tabStops4 = tabStops5;
                                        i23 = lineDescent;
                                        i24 = paragraphDirection;
                                        textLine3 = textLine4;
                                        charSequence2 = charSequence3;
                                        leadingMarginSpan.drawLeadingMargin(canvas, textPaint2, i34, paragraphDirection, i29, lineDescent, lineTop2, charSequence3, i27, lineVisibleEnd, z2, this);
                                        i34 -= leadingMarginSpan.getLeadingMargin(z6);
                                    } else {
                                        i20 = i19;
                                        i21 = length;
                                        z6 = z5;
                                        i22 = i31;
                                        tabStops4 = tabStops5;
                                        i23 = lineDescent;
                                        i24 = paragraphDirection;
                                        charSequence2 = charSequence3;
                                        textLine3 = textLine4;
                                        i25 = i15;
                                        i26 = i18;
                                        paragraphStyleArr3 = paragraphStyleArr2;
                                        i27 = i30;
                                        leadingMarginSpan.drawLeadingMargin(canvas, textPaint2, i10, i24, i29, i23, lineTop2, charSequence2, i27, lineVisibleEnd, z2, this);
                                        i10 += leadingMarginSpan.getLeadingMargin(z6);
                                    }
                                }
                                i19 = i20 + 1;
                                z5 = z6;
                                paragraphStyleArr2 = paragraphStyleArr3;
                                i30 = i27;
                                length = i21;
                                i15 = i25;
                                i18 = i26;
                                i31 = i22;
                                tabStops5 = tabStops4;
                                lineDescent = i23;
                                textLine4 = textLine3;
                                paragraphDirection = i24;
                                charSequence3 = charSequence2;
                            }
                            i5 = i31;
                            tabStops = tabStops5;
                            i7 = lineDescent;
                            i8 = paragraphDirection;
                            charSequence = charSequence3;
                            textLine = textLine4;
                            i3 = i15;
                            i4 = i18;
                            i6 = i30;
                            i32 = i16;
                            i9 = i34;
                            z7 = z4;
                            alignment = alignment3;
                            paragraphStyleArr = paragraphStyleArr2;
                        } else {
                            z3 = true;
                        }
                        paragraphStyleArr2 = paragraphStyleArr6;
                        z4 = z8;
                        i16 = i32;
                        length = paragraphStyleArr2.length;
                        i17 = 0;
                        while (true) {
                            if (i17 >= length) {
                            }
                            i17++;
                            i33 = i18;
                        }
                        i19 = 0;
                        i10 = 0;
                        while (i19 < length) {
                        }
                        i5 = i31;
                        tabStops = tabStops5;
                        i7 = lineDescent;
                        i8 = paragraphDirection;
                        charSequence = charSequence3;
                        textLine = textLine4;
                        i3 = i15;
                        i4 = i18;
                        i6 = i30;
                        i32 = i16;
                        i9 = i34;
                        z7 = z4;
                        alignment = alignment3;
                        paragraphStyleArr = paragraphStyleArr2;
                    }
                } else {
                    alignment3 = alignment4;
                    i15 = lineStart2;
                }
                z2 = true;
                if (i30 >= i32) {
                }
                paragraphStyleArr2 = paragraphStyleArr6;
                z4 = z8;
                i16 = i32;
                length = paragraphStyleArr2.length;
                i17 = 0;
                while (true) {
                    if (i17 >= length) {
                    }
                    i17++;
                    i33 = i18;
                }
                i19 = 0;
                i10 = 0;
                while (i19 < length) {
                }
                i5 = i31;
                tabStops = tabStops5;
                i7 = lineDescent;
                i8 = paragraphDirection;
                charSequence = charSequence3;
                textLine = textLine4;
                i3 = i15;
                i4 = i18;
                i6 = i30;
                i32 = i16;
                i9 = i34;
                z7 = z4;
                alignment = alignment3;
                paragraphStyleArr = paragraphStyleArr2;
            }
            int i35 = i5;
            boolean lineContainsTab = getLineContainsTab(i35);
            if (lineContainsTab && !z7) {
                android.text.Layout.TabStops tabStops6 = tabStops;
                if (tabStops6 == null) {
                    tabStops3 = new android.text.Layout.TabStops(TAB_INCREMENT, paragraphStyleArr);
                } else {
                    tabStops6.reset(TAB_INCREMENT, paragraphStyleArr);
                    tabStops3 = tabStops6;
                }
                tabStops2 = tabStops3;
                z7 = true;
            } else {
                tabStops2 = tabStops;
            }
            if (alignment == android.text.Layout.Alignment.ALIGN_LEFT) {
                i11 = i8;
                i12 = 1;
                alignment2 = i11 == 1 ? android.text.Layout.Alignment.ALIGN_NORMAL : android.text.Layout.Alignment.ALIGN_OPPOSITE;
            } else {
                i11 = i8;
                i12 = 1;
                if (alignment != android.text.Layout.Alignment.ALIGN_RIGHT) {
                    alignment2 = alignment;
                } else {
                    alignment2 = i11 == 1 ? android.text.Layout.Alignment.ALIGN_OPPOSITE : android.text.Layout.Alignment.ALIGN_NORMAL;
                }
            }
            if (alignment2 == android.text.Layout.Alignment.ALIGN_NORMAL) {
                if (i11 == i12) {
                    i14 = getIndentAdjust(i35, android.text.Layout.Alignment.ALIGN_LEFT);
                    i13 = i10 + i14;
                    z = false;
                } else {
                    i14 = -getIndentAdjust(i35, android.text.Layout.Alignment.ALIGN_RIGHT);
                    i13 = i9 - i14;
                    z = false;
                }
            } else {
                z = false;
                int lineExtent = (int) getLineExtent(i35, tabStops2, false);
                if (alignment2 != android.text.Layout.Alignment.ALIGN_OPPOSITE) {
                    int indentAdjust = getIndentAdjust(i35, android.text.Layout.Alignment.ALIGN_CENTER);
                    i13 = (((i9 + i10) - (lineExtent & (-2))) >> 1) + indentAdjust;
                    i14 = indentAdjust;
                } else if (i11 == i12) {
                    i14 = -getIndentAdjust(i35, android.text.Layout.Alignment.ALIGN_RIGHT);
                    i13 = (i9 - lineExtent) - i14;
                } else {
                    i14 = getIndentAdjust(i35, android.text.Layout.Alignment.ALIGN_LEFT);
                    i13 = (i10 - lineExtent) + i14;
                }
            }
            android.text.Layout.Directions lineDirections = getLineDirections(i35);
            if (lineDirections == DIRS_ALL_LEFT_TO_RIGHT && !this.mSpannedText && !lineContainsTab && !isJustificationRequired) {
                canvas.drawText(charSequence, i6, lineVisibleEnd, i13, i7, textPaint2);
                textPaint = textPaint2;
                textLine2 = textLine;
            } else {
                int i36 = i7;
                textPaint = textPaint2;
                textLine.set(textPaint, charSequence, i6, lineVisibleEnd, i11, lineDirections, lineContainsTab, tabStops2, getEllipsisStart(i35), getEllipsisStart(i35) + getEllipsisCount(i35), isFallbackLineSpacingEnabled());
                if (!isJustificationRequired) {
                    textLine2 = textLine;
                } else {
                    textLine2 = textLine;
                    textLine2.justify(this.mJustificationMode, (i9 - i10) - i14);
                }
                textLine2.draw(canvas, i13, i29, i36, lineTop2);
            }
            paragraphStyleArr4 = paragraphStyleArr;
            alignment4 = alignment;
            tabStops5 = tabStops2;
            obtain = textLine2;
            i29 = lineTop2;
            i30 = i3;
            i31 = i4;
            layout = this;
            textPaint2 = textPaint;
            charSequence3 = charSequence;
            i28 = i;
        }
        android.text.TextLine.recycle(obtain);
    }

    public void drawBackground(android.graphics.Canvas canvas, int i, int i2) {
        int i3;
        int i4;
        android.text.style.ParagraphStyle[] paragraphStyleArr;
        int i5 = i;
        if (this.mSpannedText) {
            if (this.mLineBackgroundSpans == null) {
                this.mLineBackgroundSpans = new android.text.SpanSet<>(android.text.style.LineBackgroundSpan.class);
            }
            android.text.Spanned spanned = (android.text.Spanned) this.mText;
            int length = spanned.length();
            int i6 = 0;
            this.mLineBackgroundSpans.init(spanned, 0, length);
            if (this.mLineBackgroundSpans.numberOfSpans > 0) {
                int lineTop = getLineTop(i5);
                int lineStart = getLineStart(i5);
                android.text.style.ParagraphStyle[] paragraphStyleArr2 = NO_PARA_SPANS;
                android.text.TextPaint textPaint = this.mPaint;
                int i7 = this.mWidth;
                int i8 = lineTop;
                int i9 = lineStart;
                int i10 = 0;
                int i11 = 0;
                while (i5 <= i2) {
                    int i12 = i5 + 1;
                    int lineStart2 = getLineStart(i12);
                    int lineTop2 = getLineTop(i12);
                    int lineDescent = lineTop2 - getLineDescent(i5);
                    if (lineStart2 < i10) {
                        i3 = i10;
                        i4 = i11;
                        paragraphStyleArr = paragraphStyleArr2;
                    } else {
                        int nextTransition = this.mLineBackgroundSpans.getNextTransition(i9, length);
                        if (i9 != lineStart2 || i9 == 0) {
                            int i13 = i6;
                            for (int i14 = i13; i14 < this.mLineBackgroundSpans.numberOfSpans; i14++) {
                                if (this.mLineBackgroundSpans.spanStarts[i14] < lineStart2 && this.mLineBackgroundSpans.spanEnds[i14] > i9) {
                                    paragraphStyleArr2 = (android.text.style.ParagraphStyle[]) com.android.internal.util.GrowingArrayUtils.append((android.text.style.LineBackgroundSpan[]) paragraphStyleArr2, i13, this.mLineBackgroundSpans.spans[i14]);
                                    i13++;
                                }
                            }
                            i3 = nextTransition;
                            i4 = i13;
                            paragraphStyleArr = paragraphStyleArr2;
                        } else {
                            i3 = nextTransition;
                            paragraphStyleArr = paragraphStyleArr2;
                            i4 = i6;
                        }
                    }
                    int i15 = i6;
                    while (i15 < i4) {
                        int i16 = lineStart2;
                        int i17 = i9;
                        ((android.text.style.LineBackgroundSpan) paragraphStyleArr[i15]).drawBackground(canvas, textPaint, 0, i7, i8, lineDescent, lineTop2, spanned, i17, i16, i5);
                        i15++;
                        i4 = i4;
                        lineStart2 = i16;
                        i12 = i12;
                        i9 = i17;
                        i7 = i7;
                        textPaint = textPaint;
                        i6 = i6;
                    }
                    i8 = lineTop2;
                    i10 = i3;
                    paragraphStyleArr2 = paragraphStyleArr;
                    i11 = i4;
                    i9 = lineStart2;
                    i5 = i12;
                }
            }
            this.mLineBackgroundSpans.recycle();
        }
    }

    public long getLineRangeForDraw(android.graphics.Canvas canvas) {
        synchronized (sTempRect) {
            if (!canvas.getClipBounds(sTempRect)) {
                return android.text.TextUtils.packRangeInLong(0, -1);
            }
            int i = sTempRect.top;
            int i2 = sTempRect.bottom;
            int max = java.lang.Math.max(i, 0);
            int min = java.lang.Math.min(getLineTop(getLineCount()), i2);
            return max >= min ? android.text.TextUtils.packRangeInLong(0, -1) : android.text.TextUtils.packRangeInLong(getLineForVertical(max), getLineForVertical(min));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0087  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int getLineStartPos(int i, int i2, int i3) {
        android.text.Layout.TabStops tabStops;
        android.text.Layout.Alignment paragraphAlignment = getParagraphAlignment(i);
        int paragraphDirection = getParagraphDirection(i);
        if (paragraphAlignment == android.text.Layout.Alignment.ALIGN_LEFT) {
            paragraphAlignment = paragraphDirection == 1 ? android.text.Layout.Alignment.ALIGN_NORMAL : android.text.Layout.Alignment.ALIGN_OPPOSITE;
        } else if (paragraphAlignment == android.text.Layout.Alignment.ALIGN_RIGHT) {
            paragraphAlignment = paragraphDirection == 1 ? android.text.Layout.Alignment.ALIGN_OPPOSITE : android.text.Layout.Alignment.ALIGN_NORMAL;
        }
        if (paragraphAlignment == android.text.Layout.Alignment.ALIGN_NORMAL) {
            if (paragraphDirection == 1) {
                return i2 + getIndentAdjust(i, android.text.Layout.Alignment.ALIGN_LEFT);
            }
            return i3 + getIndentAdjust(i, android.text.Layout.Alignment.ALIGN_RIGHT);
        }
        if (this.mSpannedText && getLineContainsTab(i)) {
            android.text.Spanned spanned = (android.text.Spanned) this.mText;
            int lineStart = getLineStart(i);
            android.text.style.TabStopSpan[] tabStopSpanArr = (android.text.style.TabStopSpan[]) getParagraphSpans(spanned, lineStart, spanned.nextSpanTransition(lineStart, spanned.length(), android.text.style.TabStopSpan.class), android.text.style.TabStopSpan.class);
            if (tabStopSpanArr.length > 0) {
                tabStops = new android.text.Layout.TabStops(TAB_INCREMENT, tabStopSpanArr);
                int lineExtent = (int) getLineExtent(i, tabStops, false);
                if (paragraphAlignment != android.text.Layout.Alignment.ALIGN_OPPOSITE) {
                    if (paragraphDirection == 1) {
                        return (i3 - lineExtent) + getIndentAdjust(i, android.text.Layout.Alignment.ALIGN_RIGHT);
                    }
                    return (i2 - lineExtent) + getIndentAdjust(i, android.text.Layout.Alignment.ALIGN_LEFT);
                }
                return ((i2 + i3) - (lineExtent & (-2))) >> (getIndentAdjust(i, android.text.Layout.Alignment.ALIGN_CENTER) + 1);
            }
        }
        tabStops = null;
        int lineExtent2 = (int) getLineExtent(i, tabStops, false);
        if (paragraphAlignment != android.text.Layout.Alignment.ALIGN_OPPOSITE) {
        }
    }

    public final void increaseWidthTo(int i) {
        if (i < this.mWidth) {
            throw new java.lang.RuntimeException("attempted to reduce Layout width");
        }
        this.mWidth = i;
    }

    public int getHeight() {
        return getLineTop(getLineCount());
    }

    public int getHeight(boolean z) {
        return getHeight();
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0055  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public android.graphics.RectF computeDrawingBoundingBox() {
        android.text.Layout.TabStops tabStops;
        android.text.Layout.Directions lineDirections;
        int i;
        android.text.TextLine obtain = android.text.TextLine.obtain();
        android.graphics.RectF rectF = new android.graphics.RectF();
        float f = 0.0f;
        float f2 = 0.0f;
        float f3 = 0.0f;
        float f4 = 0.0f;
        int i2 = 0;
        while (i2 < getLineCount()) {
            int lineStart = getLineStart(i2);
            int lineVisibleEnd = getLineVisibleEnd(i2);
            boolean lineContainsTab = getLineContainsTab(i2);
            if (lineContainsTab && (this.mText instanceof android.text.Spanned)) {
                android.text.style.TabStopSpan[] tabStopSpanArr = (android.text.style.TabStopSpan[]) getParagraphSpans((android.text.Spanned) this.mText, lineStart, lineVisibleEnd, android.text.style.TabStopSpan.class);
                if (tabStopSpanArr.length > 0) {
                    tabStops = new android.text.Layout.TabStops(TAB_INCREMENT, tabStopSpanArr);
                    lineDirections = getLineDirections(i2);
                    if (lineDirections != null) {
                        i = i2;
                    } else {
                        int paragraphDirection = getParagraphDirection(i2);
                        android.text.TextPaint textPaint = this.mWorkPaint;
                        textPaint.set(this.mPaint);
                        textPaint.setStartHyphenEdit(getStartHyphenEdit(i2));
                        textPaint.setEndHyphenEdit(getEndHyphenEdit(i2));
                        i = i2;
                        float f5 = f;
                        float f6 = f2;
                        float f7 = f3;
                        float f8 = f4;
                        obtain.set(textPaint, this.mText, lineStart, lineVisibleEnd, paragraphDirection, lineDirections, lineContainsTab, tabStops, getEllipsisStart(i2), getEllipsisStart(i2) + getEllipsisCount(i2), isFallbackLineSpacingEnabled());
                        if (isJustificationRequired(i)) {
                            obtain.justify(this.mJustificationMode, getJustifyWidth(i));
                        }
                        obtain.metrics(null, rectF, false, null);
                        float f9 = rectF.left;
                        float f10 = rectF.right;
                        float lineBaseline = rectF.top + getLineBaseline(i);
                        float lineBaseline2 = rectF.bottom + getLineBaseline(i);
                        if (getParagraphDirection(i) == -1) {
                            f9 += getWidth();
                            f10 += getWidth();
                        }
                        if (i == 0) {
                            f4 = f9;
                            f2 = f10;
                            f3 = lineBaseline;
                            f = lineBaseline2;
                        } else {
                            float min = java.lang.Math.min(f8, f9);
                            f4 = min;
                            f2 = java.lang.Math.max(f6, f10);
                            f3 = java.lang.Math.min(f7, lineBaseline);
                            f = java.lang.Math.max(f5, lineBaseline2);
                        }
                    }
                    i2 = i + 1;
                }
            }
            tabStops = null;
            lineDirections = getLineDirections(i2);
            if (lineDirections != null) {
            }
            i2 = i + 1;
        }
        float f11 = f3;
        float f12 = f4;
        android.text.TextLine.recycle(obtain);
        return new android.graphics.RectF(f12, f11, f2, f);
    }

    public int getLineBounds(int i, android.graphics.Rect rect) {
        if (rect != null) {
            rect.left = 0;
            rect.top = getLineTop(i);
            rect.right = this.mWidth;
            rect.bottom = getLineTop(i + 1);
        }
        return getLineBaseline(i);
    }

    public int getStartHyphenEdit(int i) {
        return 0;
    }

    public int getEndHyphenEdit(int i) {
        return 0;
    }

    public int getIndentAdjust(int i, android.text.Layout.Alignment alignment) {
        return 0;
    }

    public boolean isLevelBoundary(int i) {
        int lineForOffset = getLineForOffset(i);
        android.text.Layout.Directions lineDirections = getLineDirections(lineForOffset);
        if (lineDirections == DIRS_ALL_LEFT_TO_RIGHT || lineDirections == DIRS_ALL_RIGHT_TO_LEFT) {
            return false;
        }
        int[] iArr = lineDirections.mDirections;
        int lineStart = getLineStart(lineForOffset);
        int lineEnd = getLineEnd(lineForOffset);
        if (i == lineStart || i == lineEnd) {
            return ((iArr[(i == lineStart ? 0 : iArr.length + (-2)) + 1] >>> 26) & 63) != (getParagraphDirection(lineForOffset) == 1 ? 0 : 1);
        }
        int i2 = i - lineStart;
        for (int i3 = 0; i3 < iArr.length; i3 += 2) {
            if (i2 == iArr[i3]) {
                return true;
            }
        }
        return false;
    }

    public boolean isRtlCharAt(int i) {
        int lineForOffset = getLineForOffset(i);
        android.text.Layout.Directions lineDirections = getLineDirections(lineForOffset);
        if (lineDirections == DIRS_ALL_LEFT_TO_RIGHT) {
            return false;
        }
        if (lineDirections == DIRS_ALL_RIGHT_TO_LEFT) {
            return true;
        }
        int[] iArr = lineDirections.mDirections;
        int lineStart = getLineStart(lineForOffset);
        for (int i2 = 0; i2 < iArr.length; i2 += 2) {
            int i3 = iArr[i2] + lineStart;
            int i4 = i2 + 1;
            int i5 = (iArr[i4] & RUN_LENGTH_MASK) + i3;
            if (i >= i3 && i < i5) {
                return (((iArr[i4] >>> 26) & 63) & 1) != 0;
            }
        }
        return false;
    }

    public long getRunRange(int i) {
        int lineForOffset = getLineForOffset(i);
        android.text.Layout.Directions lineDirections = getLineDirections(lineForOffset);
        if (lineDirections == DIRS_ALL_LEFT_TO_RIGHT || lineDirections == DIRS_ALL_RIGHT_TO_LEFT) {
            return android.text.TextUtils.packRangeInLong(0, getLineEnd(lineForOffset));
        }
        int[] iArr = lineDirections.mDirections;
        int lineStart = getLineStart(lineForOffset);
        for (int i2 = 0; i2 < iArr.length; i2 += 2) {
            int i3 = iArr[i2] + lineStart;
            int i4 = (iArr[i2 + 1] & RUN_LENGTH_MASK) + i3;
            if (i >= i3 && i < i4) {
                return android.text.TextUtils.packRangeInLong(i3, i4);
            }
        }
        return android.text.TextUtils.packRangeInLong(0, getLineEnd(lineForOffset));
    }

    public boolean primaryIsTrailingPrevious(int i) {
        int i2;
        int i3;
        int lineForOffset = getLineForOffset(i);
        int lineStart = getLineStart(lineForOffset);
        int lineEnd = getLineEnd(lineForOffset);
        int[] iArr = getLineDirections(lineForOffset).mDirections;
        int i4 = 0;
        while (true) {
            i2 = -1;
            if (i4 >= iArr.length) {
                i3 = -1;
                break;
            }
            int i5 = iArr[i4] + lineStart;
            int i6 = i4 + 1;
            int i7 = (iArr[i6] & RUN_LENGTH_MASK) + i5;
            if (i7 > lineEnd) {
                i7 = lineEnd;
            }
            if (i < i5 || i >= i7) {
                i4 += 2;
            } else {
                if (i > i5) {
                    return false;
                }
                i3 = (iArr[i6] >>> 26) & 63;
            }
        }
        if (i3 == -1) {
            if (getParagraphDirection(lineForOffset) == 1) {
                i3 = 0;
            } else {
                i3 = 1;
            }
        }
        if (i == lineStart) {
            if (getParagraphDirection(lineForOffset) == 1) {
                i2 = 0;
            } else {
                i2 = 1;
            }
        } else {
            int i8 = i - 1;
            int i9 = 0;
            while (true) {
                if (i9 >= iArr.length) {
                    break;
                }
                int i10 = iArr[i9] + lineStart;
                int i11 = i9 + 1;
                int i12 = (iArr[i11] & RUN_LENGTH_MASK) + i10;
                if (i12 > lineEnd) {
                    i12 = lineEnd;
                }
                if (i8 < i10 || i8 >= i12) {
                    i9 += 2;
                } else {
                    i2 = (iArr[i11] >>> 26) & 63;
                    break;
                }
            }
        }
        if (i2 >= i3) {
            return false;
        }
        return true;
    }

    public boolean[] primaryIsTrailingPreviousAllLineOffsets(int i) {
        byte b;
        int lineStart = getLineStart(i);
        int lineEnd = getLineEnd(i);
        int[] iArr = getLineDirections(i).mDirections;
        int i2 = (lineEnd - lineStart) + 1;
        boolean[] zArr = new boolean[i2];
        byte[] bArr = new byte[i2];
        for (int i3 = 0; i3 < iArr.length; i3 += 2) {
            int i4 = iArr[i3] + lineStart;
            int i5 = i3 + 1;
            int i6 = (iArr[i5] & RUN_LENGTH_MASK) + i4;
            if (i6 > lineEnd) {
                i6 = lineEnd;
            }
            if (i6 != i4) {
                bArr[(i6 - lineStart) - 1] = (byte) ((iArr[i5] >>> 26) & 63);
            }
        }
        for (int i7 = 0; i7 < iArr.length; i7 += 2) {
            int i8 = iArr[i7] + lineStart;
            byte b2 = (byte) ((iArr[i7 + 1] >>> 26) & 63);
            int i9 = i8 - lineStart;
            if (i8 == lineStart) {
                b = getParagraphDirection(i) == 1 ? (byte) 0 : (byte) 1;
            } else {
                b = bArr[i9 - 1];
            }
            zArr[i9] = b2 > b;
        }
        return zArr;
    }

    public float getPrimaryHorizontal(int i) {
        return getPrimaryHorizontal(i, false);
    }

    public float getPrimaryHorizontal(int i, boolean z) {
        return getHorizontal(i, primaryIsTrailingPrevious(i), z);
    }

    public float getSecondaryHorizontal(int i) {
        return getSecondaryHorizontal(i, false);
    }

    public float getSecondaryHorizontal(int i, boolean z) {
        return getHorizontal(i, !primaryIsTrailingPrevious(i), z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getHorizontal(int i, boolean z) {
        return z ? getPrimaryHorizontal(i) : getSecondaryHorizontal(i);
    }

    private float getHorizontal(int i, boolean z, boolean z2) {
        return getHorizontal(i, z, getLineForOffset(i), z2);
    }

    private float getHorizontal(int i, boolean z, int i2, boolean z2) {
        android.text.Layout.TabStops tabStops;
        float measure;
        int lineStart = getLineStart(i2);
        int lineEnd = getLineEnd(i2);
        int paragraphDirection = getParagraphDirection(i2);
        boolean lineContainsTab = getLineContainsTab(i2);
        android.text.Layout.Directions lineDirections = getLineDirections(i2);
        if (lineContainsTab && (this.mText instanceof android.text.Spanned)) {
            android.text.style.TabStopSpan[] tabStopSpanArr = (android.text.style.TabStopSpan[]) getParagraphSpans((android.text.Spanned) this.mText, lineStart, lineEnd, android.text.style.TabStopSpan.class);
            if (tabStopSpanArr.length > 0) {
                tabStops = new android.text.Layout.TabStops(TAB_INCREMENT, tabStopSpanArr);
                android.text.TextLine obtain = android.text.TextLine.obtain();
                obtain.set(this.mPaint, this.mText, lineStart, lineEnd, paragraphDirection, lineDirections, lineContainsTab, tabStops, getEllipsisStart(i2), getEllipsisStart(i2) + getEllipsisCount(i2), isFallbackLineSpacingEnabled());
                measure = obtain.measure(i - lineStart, z, null, null, null);
                android.text.TextLine.recycle(obtain);
                if (z2 && measure > this.mWidth) {
                    measure = this.mWidth;
                }
                return getLineStartPos(i2, getParagraphLeft(i2), getParagraphRight(i2)) + measure;
            }
        }
        tabStops = null;
        android.text.TextLine obtain2 = android.text.TextLine.obtain();
        obtain2.set(this.mPaint, this.mText, lineStart, lineEnd, paragraphDirection, lineDirections, lineContainsTab, tabStops, getEllipsisStart(i2), getEllipsisStart(i2) + getEllipsisCount(i2), isFallbackLineSpacingEnabled());
        measure = obtain2.measure(i - lineStart, z, null, null, null);
        android.text.TextLine.recycle(obtain2);
        if (z2) {
            measure = this.mWidth;
        }
        return getLineStartPos(i2, getParagraphLeft(i2), getParagraphRight(i2)) + measure;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00a7 A[LOOP:2: B:28:0x00a5->B:29:0x00a7, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public float[] getLineHorizontals(int i, boolean z, boolean z2) {
        android.text.Layout.TabStops tabStops;
        int i2;
        int i3;
        int lineStart = getLineStart(i);
        int lineEnd = getLineEnd(i);
        int paragraphDirection = getParagraphDirection(i);
        boolean lineContainsTab = getLineContainsTab(i);
        android.text.Layout.Directions lineDirections = getLineDirections(i);
        if (lineContainsTab && (this.mText instanceof android.text.Spanned)) {
            android.text.style.TabStopSpan[] tabStopSpanArr = (android.text.style.TabStopSpan[]) getParagraphSpans((android.text.Spanned) this.mText, lineStart, lineEnd, android.text.style.TabStopSpan.class);
            if (tabStopSpanArr.length > 0) {
                tabStops = new android.text.Layout.TabStops(TAB_INCREMENT, tabStopSpanArr);
                android.text.TextLine obtain = android.text.TextLine.obtain();
                obtain.set(this.mPaint, this.mText, lineStart, lineEnd, paragraphDirection, lineDirections, lineContainsTab, tabStops, getEllipsisStart(i), getEllipsisStart(i) + getEllipsisCount(i), isFallbackLineSpacingEnabled());
                boolean[] primaryIsTrailingPreviousAllLineOffsets = primaryIsTrailingPreviousAllLineOffsets(i);
                if (!z2) {
                    for (int i4 = 0; i4 < primaryIsTrailingPreviousAllLineOffsets.length; i4++) {
                        primaryIsTrailingPreviousAllLineOffsets[i4] = !primaryIsTrailingPreviousAllLineOffsets[i4];
                    }
                }
                float[] measureAllOffsets = obtain.measureAllOffsets(primaryIsTrailingPreviousAllLineOffsets, null);
                android.text.TextLine.recycle(obtain);
                if (z) {
                    for (int i5 = 0; i5 < measureAllOffsets.length; i5++) {
                        if (measureAllOffsets[i5] > this.mWidth) {
                            measureAllOffsets[i5] = this.mWidth;
                        }
                    }
                }
                int lineStartPos = getLineStartPos(i, getParagraphLeft(i), getParagraphRight(i));
                i3 = (lineEnd - lineStart) + 1;
                float[] fArr = new float[i3];
                for (i2 = 0; i2 < i3; i2++) {
                    fArr[i2] = lineStartPos + measureAllOffsets[i2];
                }
                return fArr;
            }
        }
        tabStops = null;
        android.text.TextLine obtain2 = android.text.TextLine.obtain();
        obtain2.set(this.mPaint, this.mText, lineStart, lineEnd, paragraphDirection, lineDirections, lineContainsTab, tabStops, getEllipsisStart(i), getEllipsisStart(i) + getEllipsisCount(i), isFallbackLineSpacingEnabled());
        boolean[] primaryIsTrailingPreviousAllLineOffsets2 = primaryIsTrailingPreviousAllLineOffsets(i);
        if (!z2) {
        }
        float[] measureAllOffsets2 = obtain2.measureAllOffsets(primaryIsTrailingPreviousAllLineOffsets2, null);
        android.text.TextLine.recycle(obtain2);
        if (z) {
        }
        int lineStartPos2 = getLineStartPos(i, getParagraphLeft(i), getParagraphRight(i));
        i3 = (lineEnd - lineStart) + 1;
        float[] fArr2 = new float[i3];
        while (i2 < i3) {
        }
        return fArr2;
    }

    private void fillHorizontalBoundsForLine(int i, float[] fArr) {
        android.text.Layout.TabStops tabStops;
        int lineStart = getLineStart(i);
        int lineEnd = getLineEnd(i);
        int i2 = lineEnd - lineStart;
        int paragraphDirection = getParagraphDirection(i);
        android.text.Layout.Directions lineDirections = getLineDirections(i);
        boolean lineContainsTab = getLineContainsTab(i);
        if (lineContainsTab && (this.mText instanceof android.text.Spanned)) {
            android.text.style.TabStopSpan[] tabStopSpanArr = (android.text.style.TabStopSpan[]) getParagraphSpans((android.text.Spanned) this.mText, lineStart, lineEnd, android.text.style.TabStopSpan.class);
            if (tabStopSpanArr.length > 0) {
                tabStops = new android.text.Layout.TabStops(TAB_INCREMENT, tabStopSpanArr);
                android.text.TextLine obtain = android.text.TextLine.obtain();
                obtain.set(this.mPaint, this.mText, lineStart, lineEnd, paragraphDirection, lineDirections, lineContainsTab, tabStops, getEllipsisStart(i), getEllipsisCount(i) + getEllipsisStart(i), isFallbackLineSpacingEnabled());
                obtain.measureAllBounds((fArr != null || fArr.length < i2 * 2) ? new float[i2 * 2] : fArr, null);
                android.text.TextLine.recycle(obtain);
            }
        }
        tabStops = null;
        android.text.TextLine obtain2 = android.text.TextLine.obtain();
        obtain2.set(this.mPaint, this.mText, lineStart, lineEnd, paragraphDirection, lineDirections, lineContainsTab, tabStops, getEllipsisStart(i), getEllipsisCount(i) + getEllipsisStart(i), isFallbackLineSpacingEnabled());
        obtain2.measureAllBounds((fArr != null || fArr.length < i2 * 2) ? new float[i2 * 2] : fArr, null);
        android.text.TextLine.recycle(obtain2);
    }

    public void fillCharacterBounds(int i, int i2, float[] fArr, int i3) {
        if (i < 0 || i2 < i || i2 > this.mText.length()) {
            throw new java.lang.IndexOutOfBoundsException("given range: " + i + ", " + i2 + " is out of the text range: 0, " + this.mText.length());
        }
        if (fArr == null) {
            throw new java.lang.IllegalArgumentException("bounds can't be null.");
        }
        int i4 = (i2 - i) * 4;
        if (i4 > fArr.length - i3) {
            throw new java.lang.IndexOutOfBoundsException("bounds doesn't have enough space to store the result, needed: " + i4 + " had: " + (fArr.length - i3));
        }
        if (i == i2) {
            return;
        }
        int lineForOffset = getLineForOffset(i2 - 1);
        float[] fArr2 = null;
        for (int lineForOffset2 = getLineForOffset(i); lineForOffset2 <= lineForOffset; lineForOffset2++) {
            int lineStart = getLineStart(lineForOffset2);
            int lineEnd = getLineEnd(lineForOffset2);
            int i5 = lineEnd - lineStart;
            if (fArr2 == null || fArr2.length < i5 * 2) {
                fArr2 = new float[i5 * 2];
            }
            fillHorizontalBoundsForLine(lineForOffset2, fArr2);
            int lineStartPos = getLineStartPos(lineForOffset2, getParagraphLeft(lineForOffset2), getParagraphRight(lineForOffset2));
            int lineTop = getLineTop(lineForOffset2);
            int lineBottom = getLineBottom(lineForOffset2);
            int max = java.lang.Math.max(i, lineStart);
            int min = java.lang.Math.min(i2, lineEnd);
            while (max < min) {
                int i6 = (max - lineStart) * 2;
                float f = lineStartPos;
                float f2 = fArr2[i6] + f;
                float f3 = fArr2[i6 + 1] + f;
                int i7 = i3 + ((max - i) * 4);
                fArr[i7] = f2;
                fArr[i7 + 1] = lineTop;
                fArr[i7 + 2] = f3;
                fArr[i7 + 3] = lineBottom;
                max++;
                lineForOffset = lineForOffset;
            }
        }
    }

    public float getLineLeft(int i) {
        android.text.Layout.Alignment alignment;
        int paragraphDirection = getParagraphDirection(i);
        android.text.Layout.Alignment paragraphAlignment = getParagraphAlignment(i);
        if (paragraphAlignment == null) {
            paragraphAlignment = android.text.Layout.Alignment.ALIGN_CENTER;
        }
        switch (paragraphAlignment) {
            case ALIGN_NORMAL:
                if (paragraphDirection != -1) {
                    alignment = android.text.Layout.Alignment.ALIGN_LEFT;
                    break;
                } else {
                    alignment = android.text.Layout.Alignment.ALIGN_RIGHT;
                    break;
                }
            case ALIGN_OPPOSITE:
                if (paragraphDirection != -1) {
                    alignment = android.text.Layout.Alignment.ALIGN_RIGHT;
                    break;
                } else {
                    alignment = android.text.Layout.Alignment.ALIGN_LEFT;
                    break;
                }
            case ALIGN_CENTER:
                alignment = android.text.Layout.Alignment.ALIGN_CENTER;
                break;
            case ALIGN_RIGHT:
                alignment = android.text.Layout.Alignment.ALIGN_RIGHT;
                break;
            default:
                alignment = android.text.Layout.Alignment.ALIGN_LEFT;
                break;
        }
        switch (alignment) {
            case ALIGN_CENTER:
                return (float) java.lang.Math.floor(getParagraphLeft(i) + ((this.mWidth - getLineMax(i)) / 2.0f));
            case ALIGN_RIGHT:
                return this.mWidth - getLineMax(i);
            default:
                return 0.0f;
        }
    }

    public float getLineRight(int i) {
        android.text.Layout.Alignment alignment;
        int paragraphDirection = getParagraphDirection(i);
        android.text.Layout.Alignment paragraphAlignment = getParagraphAlignment(i);
        if (paragraphAlignment == null) {
            paragraphAlignment = android.text.Layout.Alignment.ALIGN_CENTER;
        }
        switch (paragraphAlignment) {
            case ALIGN_NORMAL:
                if (paragraphDirection != -1) {
                    alignment = android.text.Layout.Alignment.ALIGN_LEFT;
                    break;
                } else {
                    alignment = android.text.Layout.Alignment.ALIGN_RIGHT;
                    break;
                }
            case ALIGN_OPPOSITE:
                if (paragraphDirection != -1) {
                    alignment = android.text.Layout.Alignment.ALIGN_RIGHT;
                    break;
                } else {
                    alignment = android.text.Layout.Alignment.ALIGN_LEFT;
                    break;
                }
            case ALIGN_CENTER:
                alignment = android.text.Layout.Alignment.ALIGN_CENTER;
                break;
            case ALIGN_RIGHT:
                alignment = android.text.Layout.Alignment.ALIGN_RIGHT;
                break;
            default:
                alignment = android.text.Layout.Alignment.ALIGN_LEFT;
                break;
        }
        switch (alignment) {
            case ALIGN_CENTER:
                return (float) java.lang.Math.ceil(getParagraphRight(i) - ((this.mWidth - getLineMax(i)) / 2.0f));
            case ALIGN_RIGHT:
                return this.mWidth;
            default:
                return getLineMax(i);
        }
    }

    public float getLineMax(int i) {
        float paragraphLeadingMargin = getParagraphLeadingMargin(i);
        float lineExtent = getLineExtent(i, false);
        if (lineExtent < 0.0f) {
            lineExtent = -lineExtent;
        }
        return paragraphLeadingMargin + lineExtent;
    }

    public float getLineWidth(int i) {
        float paragraphLeadingMargin = getParagraphLeadingMargin(i);
        float lineExtent = getLineExtent(i, true);
        if (lineExtent < 0.0f) {
            lineExtent = -lineExtent;
        }
        return paragraphLeadingMargin + lineExtent;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x003d A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x003f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private float getLineExtent(int i, boolean z) {
        android.text.Layout.TabStops tabStops;
        android.text.Layout.Directions lineDirections;
        int lineStart = getLineStart(i);
        int lineEnd = z ? getLineEnd(i) : getLineVisibleEnd(i);
        boolean lineContainsTab = getLineContainsTab(i);
        if (lineContainsTab && (this.mText instanceof android.text.Spanned)) {
            android.text.style.TabStopSpan[] tabStopSpanArr = (android.text.style.TabStopSpan[]) getParagraphSpans((android.text.Spanned) this.mText, lineStart, lineEnd, android.text.style.TabStopSpan.class);
            if (tabStopSpanArr.length > 0) {
                tabStops = new android.text.Layout.TabStops(TAB_INCREMENT, tabStopSpanArr);
                lineDirections = getLineDirections(i);
                if (lineDirections != null) {
                    return 0.0f;
                }
                int paragraphDirection = getParagraphDirection(i);
                android.text.TextLine obtain = android.text.TextLine.obtain();
                android.text.TextPaint textPaint = this.mWorkPaint;
                textPaint.set(this.mPaint);
                textPaint.setStartHyphenEdit(getStartHyphenEdit(i));
                textPaint.setEndHyphenEdit(getEndHyphenEdit(i));
                obtain.set(textPaint, this.mText, lineStart, lineEnd, paragraphDirection, lineDirections, lineContainsTab, tabStops, getEllipsisStart(i), getEllipsisCount(i) + getEllipsisStart(i), isFallbackLineSpacingEnabled());
                if (isJustificationRequired(i)) {
                    obtain.justify(this.mJustificationMode, getJustifyWidth(i));
                }
                float metrics = obtain.metrics(null, null, this.mUseBoundsForWidth, null);
                android.text.TextLine.recycle(obtain);
                return metrics;
            }
        }
        tabStops = null;
        lineDirections = getLineDirections(i);
        if (lineDirections != null) {
        }
    }

    public int getLineLetterSpacingUnitCount(int i, boolean z) {
        int lineStart = getLineStart(i);
        int lineEnd = z ? getLineEnd(i) : getLineVisibleEnd(i, getLineStart(i), getLineStart(i + 1), false);
        android.text.Layout.Directions lineDirections = getLineDirections(i);
        if (lineDirections == null) {
            return 0;
        }
        int paragraphDirection = getParagraphDirection(i);
        android.text.TextLine obtain = android.text.TextLine.obtain();
        android.text.TextPaint textPaint = this.mWorkPaint;
        textPaint.set(this.mPaint);
        textPaint.setStartHyphenEdit(getStartHyphenEdit(i));
        textPaint.setEndHyphenEdit(getEndHyphenEdit(i));
        obtain.set(textPaint, this.mText, lineStart, lineEnd, paragraphDirection, lineDirections, false, null, getEllipsisStart(i), getEllipsisStart(i) + getEllipsisCount(i), isFallbackLineSpacingEnabled());
        if (this.mLineInfo == null) {
            this.mLineInfo = new android.text.TextLine.LineInfo();
        }
        this.mLineInfo.setClusterCount(0);
        obtain.metrics(null, null, this.mUseBoundsForWidth, this.mLineInfo);
        android.text.TextLine.recycle(obtain);
        return this.mLineInfo.getClusterCount();
    }

    private float getLineExtent(int i, android.text.Layout.TabStops tabStops, boolean z) {
        int lineStart = getLineStart(i);
        int lineEnd = z ? getLineEnd(i) : getLineVisibleEnd(i);
        boolean lineContainsTab = getLineContainsTab(i);
        android.text.Layout.Directions lineDirections = getLineDirections(i);
        int paragraphDirection = getParagraphDirection(i);
        android.text.TextLine obtain = android.text.TextLine.obtain();
        android.text.TextPaint textPaint = this.mWorkPaint;
        textPaint.set(this.mPaint);
        textPaint.setStartHyphenEdit(getStartHyphenEdit(i));
        textPaint.setEndHyphenEdit(getEndHyphenEdit(i));
        obtain.set(textPaint, this.mText, lineStart, lineEnd, paragraphDirection, lineDirections, lineContainsTab, tabStops, getEllipsisStart(i), getEllipsisStart(i) + getEllipsisCount(i), isFallbackLineSpacingEnabled());
        if (isJustificationRequired(i)) {
            obtain.justify(this.mJustificationMode, getJustifyWidth(i));
        }
        float metrics = obtain.metrics(null, null, this.mUseBoundsForWidth, null);
        android.text.TextLine.recycle(obtain);
        return metrics;
    }

    public int getLineForVertical(int i) {
        int lineCount = getLineCount();
        int i2 = -1;
        while (lineCount - i2 > 1) {
            int i3 = (lineCount + i2) / 2;
            if (getLineTop(i3) > i) {
                lineCount = i3;
            } else {
                i2 = i3;
            }
        }
        if (i2 < 0) {
            return 0;
        }
        return i2;
    }

    public int getLineForOffset(int i) {
        int lineCount = getLineCount();
        int i2 = -1;
        while (lineCount - i2 > 1) {
            int i3 = (lineCount + i2) / 2;
            if (getLineStart(i3) > i) {
                lineCount = i3;
            } else {
                i2 = i3;
            }
        }
        if (i2 < 0) {
            return 0;
        }
        return i2;
    }

    public int getOffsetForHorizontal(int i, float f) {
        return getOffsetForHorizontal(i, f, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x011c A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int getOffsetForHorizontal(int i, float f, boolean z) {
        android.text.TextLine textLine;
        int i2;
        float abs;
        android.text.Layout layout = this;
        int lineEnd = getLineEnd(i);
        int lineStart = getLineStart(i);
        android.text.Layout.Directions lineDirections = getLineDirections(i);
        android.text.TextLine obtain = android.text.TextLine.obtain();
        android.text.Layout.Directions directions = lineDirections;
        obtain.set(layout.mPaint, layout.mText, lineStart, lineEnd, getParagraphDirection(i), lineDirections, false, null, getEllipsisStart(i), getEllipsisStart(i) + getEllipsisCount(i), isFallbackLineSpacingEnabled());
        android.text.Layout.HorizontalMeasurementProvider horizontalMeasurementProvider = layout.new HorizontalMeasurementProvider(i, z);
        int i3 = 1;
        if (i == getLineCount() - 1) {
            textLine = obtain;
        } else {
            android.text.TextLine textLine2 = obtain;
            lineEnd = textLine2.getOffsetToLeftRightOf(lineEnd - lineStart, !layout.isRtlCharAt(lineEnd - 1)) + lineStart;
            textLine = textLine2;
        }
        float abs2 = java.lang.Math.abs(horizontalMeasurementProvider.get(lineStart) - f);
        int i4 = lineStart;
        int i5 = 0;
        while (true) {
            android.text.Layout.Directions directions2 = directions;
            if (i5 >= directions2.mDirections.length) {
                break;
            }
            int i6 = directions2.mDirections[i5] + lineStart;
            int i7 = i5 + 1;
            int i8 = (directions2.mDirections[i7] & RUN_LENGTH_MASK) + i6;
            boolean z2 = (directions2.mDirections[i7] & 67108864) != 0 ? i3 : 0;
            int i9 = z2 != 0 ? -1 : i3;
            if (i8 > lineEnd) {
                i8 = lineEnd;
            }
            int i10 = (i8 - 1) + i3;
            int i11 = i6 + 1;
            int i12 = i11 - 1;
            while (true) {
                i2 = i4;
                directions = directions2;
                if (i10 - i12 <= 1) {
                    break;
                }
                int i13 = (i10 + i12) / 2;
                float f2 = horizontalMeasurementProvider.get(layout.getOffsetAtStartOf(i13));
                float f3 = i9;
                if (f2 * f3 >= f3 * f) {
                    i10 = i13;
                } else {
                    i12 = i13;
                }
                layout = this;
                i4 = i2;
                directions2 = directions;
            }
            if (i12 >= i11) {
                i11 = i12;
            }
            if (i11 < i8) {
                int offsetToLeftRightOf = textLine.getOffsetToLeftRightOf(i11 - lineStart, z2) + lineStart;
                int offsetToLeftRightOf2 = textLine.getOffsetToLeftRightOf(offsetToLeftRightOf - lineStart, !z2) + lineStart;
                if (offsetToLeftRightOf2 >= i6 && offsetToLeftRightOf2 < i8) {
                    float abs3 = java.lang.Math.abs(horizontalMeasurementProvider.get(offsetToLeftRightOf2) - f);
                    if (offsetToLeftRightOf < i8) {
                        float abs4 = java.lang.Math.abs(horizontalMeasurementProvider.get(offsetToLeftRightOf) - f);
                        if (abs4 < abs3) {
                            abs3 = abs4;
                            if (abs3 < abs2) {
                                i4 = offsetToLeftRightOf;
                                abs2 = abs3;
                                abs = java.lang.Math.abs(horizontalMeasurementProvider.get(i6) - f);
                                if (abs >= abs2) {
                                    abs2 = abs;
                                    i4 = i6;
                                }
                                i5 += 2;
                                i3 = 1;
                                layout = this;
                            }
                        }
                    }
                    offsetToLeftRightOf = offsetToLeftRightOf2;
                    if (abs3 < abs2) {
                    }
                }
            }
            i4 = i2;
            abs = java.lang.Math.abs(horizontalMeasurementProvider.get(i6) - f);
            if (abs >= abs2) {
            }
            i5 += 2;
            i3 = 1;
            layout = this;
        }
        int i14 = i4;
        if (java.lang.Math.abs(horizontalMeasurementProvider.get(lineEnd) - f) > abs2) {
            lineEnd = i14;
        }
        android.text.TextLine.recycle(textLine);
        return lineEnd;
    }

    private class HorizontalMeasurementProvider {
        private float[] mHorizontals;
        private final int mLine;
        private int mLineStartOffset;
        private final boolean mPrimary;

        HorizontalMeasurementProvider(int i, boolean z) {
            this.mLine = i;
            this.mPrimary = z;
            init();
        }

        private void init() {
            if (android.text.Layout.this.getLineDirections(this.mLine) == android.text.Layout.DIRS_ALL_LEFT_TO_RIGHT) {
                return;
            }
            this.mHorizontals = android.text.Layout.this.getLineHorizontals(this.mLine, false, this.mPrimary);
            this.mLineStartOffset = android.text.Layout.this.getLineStart(this.mLine);
        }

        float get(int i) {
            int i2 = i - this.mLineStartOffset;
            if (this.mHorizontals == null || i2 < 0 || i2 >= this.mHorizontals.length) {
                return android.text.Layout.this.getHorizontal(i, this.mPrimary);
            }
            return this.mHorizontals[i2];
        }
    }

    public int[] getRangeForRect(android.graphics.RectF rectF, android.text.SegmentFinder segmentFinder, android.text.Layout.TextInclusionStrategy textInclusionStrategy) {
        int i;
        int lineForVertical = getLineForVertical((int) rectF.top);
        if (rectF.top <= getLineBottom(lineForVertical, false)) {
            i = lineForVertical;
        } else {
            int i2 = lineForVertical + 1;
            if (i2 >= getLineCount()) {
                return null;
            }
            i = i2;
        }
        int lineForVertical2 = getLineForVertical((int) rectF.bottom);
        if ((lineForVertical2 == 0 && rectF.bottom < getLineTop(0)) || lineForVertical2 < i) {
            return null;
        }
        int i3 = i;
        int startOrEndOffsetForAreaWithinLine = getStartOrEndOffsetForAreaWithinLine(i, rectF, segmentFinder, textInclusionStrategy, true);
        while (startOrEndOffsetForAreaWithinLine == -1 && i3 < lineForVertical2) {
            i3++;
            startOrEndOffsetForAreaWithinLine = getStartOrEndOffsetForAreaWithinLine(i3, rectF, segmentFinder, textInclusionStrategy, true);
        }
        if (startOrEndOffsetForAreaWithinLine == -1) {
            return null;
        }
        int startOrEndOffsetForAreaWithinLine2 = getStartOrEndOffsetForAreaWithinLine(lineForVertical2, rectF, segmentFinder, textInclusionStrategy, false);
        while (startOrEndOffsetForAreaWithinLine2 == -1 && i3 < lineForVertical2) {
            lineForVertical2--;
            startOrEndOffsetForAreaWithinLine2 = getStartOrEndOffsetForAreaWithinLine(lineForVertical2, rectF, segmentFinder, textInclusionStrategy, false);
        }
        if (startOrEndOffsetForAreaWithinLine2 == -1) {
            return null;
        }
        return new int[]{segmentFinder.previousStartBoundary(startOrEndOffsetForAreaWithinLine + 1), segmentFinder.nextEndBoundary(startOrEndOffsetForAreaWithinLine2 - 1)};
    }

    private int getStartOrEndOffsetForAreaWithinLine(int i, android.graphics.RectF rectF, android.text.SegmentFinder segmentFinder, android.text.Layout.TextInclusionStrategy textInclusionStrategy, boolean z) {
        float f;
        float f2;
        int i2;
        android.text.Layout.Directions directions;
        int i3;
        float[] fArr;
        int i4;
        int endOffsetForAreaWithinRun;
        int i5;
        int lineTop = getLineTop(i);
        int lineBottom = getLineBottom(i, false);
        int lineStart = getLineStart(i);
        int lineEnd = getLineEnd(i);
        if (lineStart == lineEnd) {
            return -1;
        }
        int i6 = lineEnd - lineStart;
        float[] fArr2 = new float[i6 * 2];
        fillHorizontalBoundsForLine(i, fArr2);
        int lineStartPos = getLineStartPos(i, getParagraphLeft(i), getParagraphRight(i));
        android.text.Layout.Directions lineDirections = getLineDirections(i);
        int runCount = z ? 0 : lineDirections.getRunCount() - 1;
        while (true) {
            if ((!z || runCount >= lineDirections.getRunCount()) && (z || runCount < 0)) {
                break;
            }
            int runStart = lineDirections.getRunStart(runCount);
            int min = java.lang.Math.min(lineDirections.getRunLength(runCount) + runStart, i6);
            boolean isRunRtl = lineDirections.isRunRtl(runCount);
            float f3 = lineStartPos;
            if (isRunRtl) {
                f = fArr2[(min - 1) * 2];
            } else {
                f = fArr2[runStart * 2];
            }
            float f4 = f3 + f;
            if (isRunRtl) {
                f2 = fArr2[(runStart * 2) + 1];
            } else {
                f2 = fArr2[((min - 1) * 2) + 1];
            }
            float f5 = f3 + f2;
            if (z) {
                i2 = runCount;
                directions = lineDirections;
                i3 = lineStartPos;
                fArr = fArr2;
                i4 = i6;
                endOffsetForAreaWithinRun = getStartOffsetForAreaWithinRun(rectF, lineTop, lineBottom, lineStart, lineStartPos, fArr2, runStart, min, f4, f5, isRunRtl, segmentFinder, textInclusionStrategy);
            } else {
                i2 = runCount;
                directions = lineDirections;
                i3 = lineStartPos;
                fArr = fArr2;
                i4 = i6;
                endOffsetForAreaWithinRun = getEndOffsetForAreaWithinRun(rectF, lineTop, lineBottom, lineStart, i3, fArr, runStart, min, f4, f5, isRunRtl, segmentFinder, textInclusionStrategy);
            }
            if (endOffsetForAreaWithinRun >= 0) {
                return endOffsetForAreaWithinRun;
            }
            if (!z) {
                i5 = -1;
            } else {
                i5 = 1;
            }
            runCount = i2 + i5;
            lineDirections = directions;
            lineStartPos = i3;
            fArr2 = fArr;
            i6 = i4;
        }
        return -1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0092, code lost:
    
        return -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static int getStartOffsetForAreaWithinRun(android.graphics.RectF rectF, int i, int i2, int i3, int i4, float[] fArr, int i5, int i6, float f, float f2, boolean z, android.text.SegmentFinder segmentFinder, android.text.Layout.TextInclusionStrategy textInclusionStrategy) {
        int i7;
        int previousStartBoundary;
        int i8;
        if (f2 < rectF.left || f > rectF.right) {
            return -1;
        }
        if ((!z && rectF.left <= f) || (z && rectF.right >= f2)) {
            i7 = i5;
        } else {
            int i9 = i5;
            i7 = i6;
            while (i7 - i9 > 1) {
                int i10 = (i7 + i9) / 2;
                float f3 = i4 + fArr[i10 * 2];
                if ((!z && f3 > rectF.left) || (z && f3 < rectF.right)) {
                    i7 = i10;
                } else {
                    i9 = i10;
                }
            }
            if (!z) {
                i7 = i9;
            }
        }
        int nextEndBoundary = segmentFinder.nextEndBoundary(i7 + i3);
        if (nextEndBoundary == -1 || (previousStartBoundary = segmentFinder.previousStartBoundary(nextEndBoundary)) >= (i8 = i3 + i6)) {
            return -1;
        }
        int max = java.lang.Math.max(previousStartBoundary, i3 + i5);
        int min = java.lang.Math.min(nextEndBoundary, i8);
        android.graphics.RectF rectF2 = new android.graphics.RectF(0.0f, i, 0.0f, i2);
        while (true) {
            float f4 = i4;
            float f5 = fArr[((max - i3) * 2) + (z ? 1 : 0)] + f4;
            if ((z || f5 <= rectF.right) && (!z || f5 >= rectF.left)) {
                float f6 = f4 + fArr[(((min - i3) - 1) * 2) + (!z ? 1 : 0)];
                rectF2.left = z ? f6 : f5;
                if (!z) {
                    f5 = f6;
                }
                rectF2.right = f5;
                if (textInclusionStrategy.isSegmentInside(rectF2, rectF)) {
                    return max;
                }
                max = segmentFinder.nextStartBoundary(max);
                if (max == -1 || max >= i8) {
                    break;
                }
                min = java.lang.Math.min(segmentFinder.nextEndBoundary(max), i8);
            }
        }
        return -1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0095, code lost:
    
        return -1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static int getEndOffsetForAreaWithinRun(android.graphics.RectF rectF, int i, int i2, int i3, int i4, float[] fArr, int i5, int i6, float f, float f2, boolean z, android.text.SegmentFinder segmentFinder, android.text.Layout.TextInclusionStrategy textInclusionStrategy) {
        int i7;
        int nextEndBoundary;
        int i8;
        if (f2 < rectF.left || f > rectF.right) {
            return -1;
        }
        if ((!z && rectF.right >= f2) || (z && rectF.left <= f)) {
            i7 = i6 - 1;
        } else {
            int i9 = i5;
            i7 = i6;
            while (i7 - i9 > 1) {
                int i10 = (i7 + i9) / 2;
                float f3 = i4 + fArr[i10 * 2];
                if ((!z && f3 > rectF.right) || (z && f3 < rectF.left)) {
                    i7 = i10;
                } else {
                    i9 = i10;
                }
            }
            if (!z) {
                i7 = i9;
            }
        }
        int previousStartBoundary = segmentFinder.previousStartBoundary(i7 + i3 + 1);
        if (previousStartBoundary == -1 || (nextEndBoundary = segmentFinder.nextEndBoundary(previousStartBoundary)) <= (i8 = i3 + i5)) {
            return -1;
        }
        int max = java.lang.Math.max(previousStartBoundary, i8);
        int min = java.lang.Math.min(nextEndBoundary, i3 + i6);
        android.graphics.RectF rectF2 = new android.graphics.RectF(0.0f, i, 0.0f, i2);
        while (true) {
            float f4 = i4;
            float f5 = fArr[(((min - i3) - 1) * 2) + (!z ? 1 : 0)] + f4;
            if ((z || f5 >= rectF.left) && (!z || f5 <= rectF.right)) {
                float f6 = f4 + fArr[((max - i3) * 2) + (z ? 1 : 0)];
                rectF2.left = z ? f5 : f6;
                if (z) {
                    f5 = f6;
                }
                rectF2.right = f5;
                if (textInclusionStrategy.isSegmentInside(rectF2, rectF)) {
                    return min;
                }
                min = segmentFinder.previousEndBoundary(min);
                if (min == -1 || min <= i8) {
                    break;
                }
                max = java.lang.Math.max(segmentFinder.previousStartBoundary(min), i8);
            }
        }
        return -1;
    }

    public final int getLineEnd(int i) {
        return getLineStart(i + 1);
    }

    public int getLineVisibleEnd(int i) {
        return getLineVisibleEnd(i, getLineStart(i), getLineStart(i + 1), true);
    }

    private int getLineVisibleEnd(int i, int i2, int i3, boolean z) {
        java.lang.CharSequence charSequence = this.mText;
        if (z && i == getLineCount() - 1) {
            return i3;
        }
        while (i3 > i2) {
            int i4 = i3 - 1;
            char charAt = charSequence.charAt(i4);
            if (charAt == '\n') {
                return i4;
            }
            if (!android.text.TextLine.isLineEndSpace(charAt)) {
                break;
            }
            i3--;
        }
        return i3;
    }

    public final int getLineBottom(int i) {
        return getLineBottom(i, true);
    }

    public int getLineBottom(int i, boolean z) {
        if (z) {
            return getLineTop(i + 1);
        }
        return getLineTop(i + 1) - getLineExtra(i);
    }

    public final int getLineBaseline(int i) {
        return getLineTop(i + 1) - getLineDescent(i);
    }

    public final int getLineAscent(int i) {
        return getLineTop(i) - (getLineTop(i + 1) - getLineDescent(i));
    }

    public int getLineExtra(int i) {
        return 0;
    }

    public int getOffsetToLeftOf(int i) {
        return getOffsetToLeftRightOf(i, true);
    }

    public int getOffsetToRightOf(int i) {
        return getOffsetToLeftRightOf(i, false);
    }

    private int getOffsetToLeftRightOf(int i, boolean z) {
        int i2;
        int i3;
        int i4;
        boolean z2 = z;
        int lineForOffset = getLineForOffset(i);
        int lineStart = getLineStart(lineForOffset);
        int lineEnd = getLineEnd(lineForOffset);
        int paragraphDirection = getParagraphDirection(lineForOffset);
        boolean z3 = true;
        if (z2 == (paragraphDirection == -1)) {
            if (i == lineEnd) {
                if (lineForOffset >= getLineCount() - 1) {
                    return i;
                }
                lineForOffset++;
            }
            z3 = false;
        } else {
            if (i == lineStart) {
                if (lineForOffset <= 0) {
                    return i;
                }
                lineForOffset--;
            }
            z3 = false;
        }
        if (!z3) {
            i2 = lineStart;
            i3 = lineEnd;
            i4 = paragraphDirection;
        } else {
            int lineStart2 = getLineStart(lineForOffset);
            int lineEnd2 = getLineEnd(lineForOffset);
            int paragraphDirection2 = getParagraphDirection(lineForOffset);
            if (paragraphDirection2 == paragraphDirection) {
                i2 = lineStart2;
                i3 = lineEnd2;
                i4 = paragraphDirection;
            } else {
                z2 = !z2;
                i2 = lineStart2;
                i3 = lineEnd2;
                i4 = paragraphDirection2;
            }
        }
        android.text.Layout.Directions lineDirections = getLineDirections(lineForOffset);
        android.text.TextLine obtain = android.text.TextLine.obtain();
        obtain.set(this.mPaint, this.mText, i2, i3, i4, lineDirections, false, null, getEllipsisStart(lineForOffset), getEllipsisStart(lineForOffset) + getEllipsisCount(lineForOffset), isFallbackLineSpacingEnabled());
        int offsetToLeftRightOf = i2 + obtain.getOffsetToLeftRightOf(i - i2, z2);
        android.text.TextLine.recycle(obtain);
        return offsetToLeftRightOf;
    }

    private int getOffsetAtStartOf(int i) {
        char charAt;
        if (i == 0) {
            return 0;
        }
        java.lang.CharSequence charSequence = this.mText;
        char charAt2 = charSequence.charAt(i);
        if (charAt2 >= 56320 && charAt2 <= 57343 && (charAt = charSequence.charAt(i - 1)) >= 55296 && charAt <= 56319) {
            i--;
        }
        if (this.mSpannedText) {
            android.text.Spanned spanned = (android.text.Spanned) charSequence;
            android.text.style.ReplacementSpan[] replacementSpanArr = (android.text.style.ReplacementSpan[]) spanned.getSpans(i, i, android.text.style.ReplacementSpan.class);
            for (int i2 = 0; i2 < replacementSpanArr.length; i2++) {
                int spanStart = spanned.getSpanStart(replacementSpanArr[i2]);
                int spanEnd = spanned.getSpanEnd(replacementSpanArr[i2]);
                if (spanStart < i && spanEnd > i) {
                    i = spanStart;
                }
            }
        }
        return i;
    }

    public boolean shouldClampCursor(int i) {
        switch (getParagraphAlignment(i)) {
            case ALIGN_NORMAL:
                return getParagraphDirection(i) > 0;
            case ALIGN_LEFT:
                return true;
            default:
                return false;
        }
    }

    public void getCursorPath(int i, android.graphics.Path path, java.lang.CharSequence charSequence) {
        path.reset();
        int lineForOffset = getLineForOffset(i);
        int lineTop = getLineTop(lineForOffset);
        int i2 = 0;
        int lineBottom = getLineBottom(lineForOffset, false);
        float primaryHorizontal = getPrimaryHorizontal(i, shouldClampCursor(lineForOffset)) - 0.5f;
        int metaState = android.text.method.TextKeyListener.getMetaState(charSequence, 1) | android.text.method.TextKeyListener.getMetaState(charSequence, 2048);
        int metaState2 = android.text.method.TextKeyListener.getMetaState(charSequence, 2);
        if (metaState != 0 || metaState2 != 0) {
            i2 = (lineBottom - lineTop) >> 2;
            if (metaState2 != 0) {
                lineTop += i2;
            }
            if (metaState != 0) {
                lineBottom -= i2;
            }
        }
        if (primaryHorizontal < 0.5f) {
            primaryHorizontal = 0.5f;
        }
        float f = lineTop;
        path.moveTo(primaryHorizontal, f);
        float f2 = lineBottom;
        path.lineTo(primaryHorizontal, f2);
        if (metaState == 2) {
            path.moveTo(primaryHorizontal, f2);
            float f3 = i2;
            float f4 = lineBottom + i2;
            path.lineTo(primaryHorizontal - f3, f4);
            path.lineTo(primaryHorizontal, f2);
            path.lineTo(f3 + primaryHorizontal, f4);
        } else if (metaState == 1) {
            path.moveTo(primaryHorizontal, f2);
            float f5 = i2;
            float f6 = primaryHorizontal - f5;
            float f7 = lineBottom + i2;
            path.lineTo(f6, f7);
            float f8 = f7 - 0.5f;
            path.moveTo(f6, f8);
            float f9 = f5 + primaryHorizontal;
            path.lineTo(f9, f8);
            path.moveTo(f9, f7);
            path.lineTo(primaryHorizontal, f2);
        }
        if (metaState2 == 2) {
            path.moveTo(primaryHorizontal, f);
            float f10 = i2;
            float f11 = lineTop - i2;
            path.lineTo(primaryHorizontal - f10, f11);
            path.lineTo(primaryHorizontal, f);
            path.lineTo(primaryHorizontal + f10, f11);
            return;
        }
        if (metaState2 == 1) {
            path.moveTo(primaryHorizontal, f);
            float f12 = i2;
            float f13 = primaryHorizontal - f12;
            float f14 = lineTop - i2;
            path.lineTo(f13, f14);
            float f15 = 0.5f + f14;
            path.moveTo(f13, f15);
            float f16 = f12 + primaryHorizontal;
            path.lineTo(f16, f15);
            path.moveTo(f16, f14);
            path.lineTo(primaryHorizontal, f);
        }
    }

    private void addSelection(int i, int i2, int i3, int i4, int i5, android.text.Layout.SelectionRectangleConsumer selectionRectangleConsumer) {
        int i6;
        int lineStart = getLineStart(i);
        int lineEnd = getLineEnd(i);
        android.text.Layout.Directions lineDirections = getLineDirections(i);
        if (lineEnd > lineStart && this.mText.charAt(lineEnd - 1) == '\n') {
            lineEnd--;
        }
        for (int i7 = 0; i7 < lineDirections.mDirections.length; i7 += 2) {
            int i8 = lineDirections.mDirections[i7] + lineStart;
            int i9 = i7 + 1;
            int i10 = (lineDirections.mDirections[i9] & RUN_LENGTH_MASK) + i8;
            if (i10 > lineEnd) {
                i10 = lineEnd;
            }
            if (i2 <= i10 && i3 >= i8) {
                int max = java.lang.Math.max(i2, i8);
                int min = java.lang.Math.min(i3, i10);
                if (max != min) {
                    float horizontal = getHorizontal(max, false, i, false);
                    float horizontal2 = getHorizontal(min, true, i, false);
                    float min2 = java.lang.Math.min(horizontal, horizontal2);
                    float max2 = java.lang.Math.max(horizontal, horizontal2);
                    if ((lineDirections.mDirections[i9] & 67108864) != 0) {
                        i6 = 0;
                    } else {
                        i6 = 1;
                    }
                    selectionRectangleConsumer.accept(min2, i4, max2, i5, i6);
                }
            }
        }
    }

    public void getSelectionPath(int i, int i2, final android.graphics.Path path) {
        path.reset();
        getSelection(i, i2, new android.text.Layout.SelectionRectangleConsumer() { // from class: android.text.Layout$$ExternalSyntheticLambda0
            @Override // android.text.Layout.SelectionRectangleConsumer
            public final void accept(float f, float f2, float f3, float f4, int i3) {
                android.graphics.Path.this.addRect(f, f2, f3, f4, android.graphics.Path.Direction.CW);
            }
        });
    }

    public final void getSelection(int i, int i2, android.text.Layout.SelectionRectangleConsumer selectionRectangleConsumer) {
        int i3;
        int i4;
        float f;
        if (i == i2) {
            return;
        }
        if (i2 >= i) {
            i3 = i;
            i4 = i2;
        } else {
            i4 = i;
            i3 = i2;
        }
        int lineForOffset = getLineForOffset(i3);
        int lineForOffset2 = getLineForOffset(i4);
        int lineTop = getLineTop(lineForOffset);
        int lineBottom = getLineBottom(lineForOffset2, false);
        if (lineForOffset == lineForOffset2) {
            addSelection(lineForOffset, i3, i4, lineTop, lineBottom, selectionRectangleConsumer);
            return;
        }
        float f2 = this.mWidth;
        addSelection(lineForOffset, i3, getLineEnd(lineForOffset), lineTop, getLineBottom(lineForOffset), selectionRectangleConsumer);
        if (getParagraphDirection(lineForOffset) == -1) {
            selectionRectangleConsumer.accept(getLineLeft(lineForOffset), lineTop, 0.0f, getLineBottom(lineForOffset), 0);
            f = f2;
        } else {
            f = f2;
            selectionRectangleConsumer.accept(getLineRight(lineForOffset), lineTop, f, getLineBottom(lineForOffset), 1);
        }
        while (true) {
            lineForOffset++;
            if (lineForOffset >= lineForOffset2) {
                break;
            }
            int lineTop2 = getLineTop(lineForOffset);
            int lineBottom2 = getLineBottom(lineForOffset);
            if (getParagraphDirection(lineForOffset) == -1) {
                selectionRectangleConsumer.accept(0.0f, lineTop2, f, lineBottom2, 0);
            } else {
                selectionRectangleConsumer.accept(0.0f, lineTop2, f, lineBottom2, 1);
            }
        }
        int lineTop3 = getLineTop(lineForOffset2);
        int lineBottom3 = getLineBottom(lineForOffset2, false);
        addSelection(lineForOffset2, getLineStart(lineForOffset2), i4, lineTop3, lineBottom3, selectionRectangleConsumer);
        if (getParagraphDirection(lineForOffset2) == -1) {
            selectionRectangleConsumer.accept(f, lineTop3, getLineRight(lineForOffset2), lineBottom3, 0);
        } else {
            selectionRectangleConsumer.accept(0.0f, lineTop3, getLineLeft(lineForOffset2), lineBottom3, 1);
        }
    }

    public final android.text.Layout.Alignment getParagraphAlignment(int i) {
        android.text.style.AlignmentSpan[] alignmentSpanArr;
        int length;
        android.text.Layout.Alignment alignment = this.mAlignment;
        if (this.mSpannedText && (length = (alignmentSpanArr = (android.text.style.AlignmentSpan[]) getParagraphSpans((android.text.Spanned) this.mText, getLineStart(i), getLineEnd(i), android.text.style.AlignmentSpan.class)).length) > 0) {
            return alignmentSpanArr[length - 1].getAlignment();
        }
        return alignment;
    }

    public final int getParagraphLeft(int i) {
        if (getParagraphDirection(i) == -1 || !this.mSpannedText) {
            return 0;
        }
        return getParagraphLeadingMargin(i);
    }

    public final int getParagraphRight(int i) {
        int i2 = this.mWidth;
        if (getParagraphDirection(i) == 1 || !this.mSpannedText) {
            return i2;
        }
        return i2 - getParagraphLeadingMargin(i);
    }

    private int getParagraphLeadingMargin(int i) {
        if (!this.mSpannedText) {
            return 0;
        }
        android.text.Spanned spanned = (android.text.Spanned) this.mText;
        int lineStart = getLineStart(i);
        android.text.style.LeadingMarginSpan[] leadingMarginSpanArr = (android.text.style.LeadingMarginSpan[]) getParagraphSpans(spanned, lineStart, spanned.nextSpanTransition(lineStart, getLineEnd(i), android.text.style.LeadingMarginSpan.class), android.text.style.LeadingMarginSpan.class);
        if (leadingMarginSpanArr.length == 0) {
            return 0;
        }
        boolean z = lineStart == 0 || spanned.charAt(lineStart - 1) == '\n';
        for (int i2 = 0; i2 < leadingMarginSpanArr.length; i2++) {
            if (leadingMarginSpanArr[i2] instanceof android.text.style.LeadingMarginSpan.LeadingMarginSpan2) {
                z |= i < getLineForOffset(spanned.getSpanStart(leadingMarginSpanArr[i2])) + ((android.text.style.LeadingMarginSpan.LeadingMarginSpan2) leadingMarginSpanArr[i2]).getLeadingMarginLineCount();
            }
        }
        int i3 = 0;
        for (android.text.style.LeadingMarginSpan leadingMarginSpan : leadingMarginSpanArr) {
            i3 += leadingMarginSpan.getLeadingMargin(z);
        }
        return i3;
    }

    private static float measurePara(android.text.TextPaint textPaint, java.lang.CharSequence charSequence, int i, int i2, android.text.TextDirectionHeuristic textDirectionHeuristic, boolean z) {
        android.text.MeasuredParagraph buildForBidi;
        int i3;
        android.text.Layout.TabStops tabStops;
        boolean z2;
        android.text.Layout.TabStops tabStops2;
        android.text.TextLine obtain = android.text.TextLine.obtain();
        android.text.MeasuredParagraph measuredParagraph = null;
        try {
            buildForBidi = android.text.MeasuredParagraph.buildForBidi(charSequence, i, i2, textDirectionHeuristic, null);
        } catch (java.lang.Throwable th) {
            th = th;
        }
        try {
            char[] chars = buildForBidi.getChars();
            int length = chars.length;
            android.text.Layout.Directions directions = buildForBidi.getDirections(0, length);
            int paragraphDir = buildForBidi.getParagraphDir();
            if (charSequence instanceof android.text.Spanned) {
                i3 = 0;
                for (android.text.style.LeadingMarginSpan leadingMarginSpan : (android.text.style.LeadingMarginSpan[]) getParagraphSpans((android.text.Spanned) charSequence, i, i2, android.text.style.LeadingMarginSpan.class)) {
                    i3 += leadingMarginSpan.getLeadingMargin(true);
                }
            } else {
                i3 = 0;
            }
            int i4 = 0;
            while (true) {
                if (i4 >= length) {
                    tabStops = null;
                    z2 = false;
                    break;
                }
                if (chars[i4] != '\t') {
                    i4++;
                } else if (!(charSequence instanceof android.text.Spanned)) {
                    z2 = true;
                    tabStops = null;
                } else {
                    android.text.Spanned spanned = (android.text.Spanned) charSequence;
                    android.text.style.TabStopSpan[] tabStopSpanArr = (android.text.style.TabStopSpan[]) getParagraphSpans(spanned, i, spanned.nextSpanTransition(i, i2, android.text.style.TabStopSpan.class), android.text.style.TabStopSpan.class);
                    if (tabStopSpanArr.length <= 0) {
                        tabStops2 = null;
                    } else {
                        tabStops2 = new android.text.Layout.TabStops(TAB_INCREMENT, tabStopSpanArr);
                    }
                    z2 = true;
                    tabStops = tabStops2;
                }
            }
            obtain.set(textPaint, charSequence, i, i2, paragraphDir, directions, z2, tabStops, 0, 0, false);
            float abs = i3 + java.lang.Math.abs(obtain.metrics(null, null, z, null));
            android.text.TextLine.recycle(obtain);
            if (buildForBidi != null) {
                buildForBidi.recycle();
            }
            return abs;
        } catch (java.lang.Throwable th2) {
            th = th2;
            measuredParagraph = buildForBidi;
            android.text.TextLine.recycle(obtain);
            if (measuredParagraph != null) {
                measuredParagraph.recycle();
            }
            throw th;
        }
    }

    public static class TabStops {
        private float mIncrement;
        private int mNumStops;
        private float[] mStops;

        public TabStops(float f, java.lang.Object[] objArr) {
            reset(f, objArr);
        }

        void reset(float f, java.lang.Object[] objArr) {
            this.mIncrement = f;
            int i = 0;
            if (objArr != null) {
                float[] fArr = this.mStops;
                int i2 = 0;
                for (java.lang.Object obj : objArr) {
                    if (obj instanceof android.text.style.TabStopSpan) {
                        if (fArr == null) {
                            fArr = new float[10];
                        } else if (i2 == fArr.length) {
                            float[] fArr2 = new float[i2 * 2];
                            for (int i3 = 0; i3 < i2; i3++) {
                                fArr2[i3] = fArr[i3];
                            }
                            fArr = fArr2;
                        }
                        fArr[i2] = ((android.text.style.TabStopSpan) r4).getTabStop();
                        i2++;
                    }
                }
                if (i2 > 1) {
                    java.util.Arrays.sort(fArr, 0, i2);
                }
                if (fArr != this.mStops) {
                    this.mStops = fArr;
                }
                i = i2;
            }
            this.mNumStops = i;
        }

        float nextTab(float f) {
            int i = this.mNumStops;
            if (i > 0) {
                float[] fArr = this.mStops;
                for (int i2 = 0; i2 < i; i2++) {
                    float f2 = fArr[i2];
                    if (f2 > f) {
                        return f2;
                    }
                }
            }
            return nextDefaultStop(f, this.mIncrement);
        }

        public static float nextDefaultStop(float f, float f2) {
            return ((int) ((f + f2) / f2)) * f2;
        }
    }

    static float nextTab(java.lang.CharSequence charSequence, int i, int i2, float f, java.lang.Object[] objArr) {
        boolean z;
        if (charSequence instanceof android.text.Spanned) {
            if (objArr != null) {
                z = false;
            } else {
                objArr = getParagraphSpans((android.text.Spanned) charSequence, i, i2, android.text.style.TabStopSpan.class);
                z = true;
            }
            float f2 = Float.MAX_VALUE;
            for (int i3 = 0; i3 < objArr.length; i3++) {
                if (z || (objArr[i3] instanceof android.text.style.TabStopSpan)) {
                    float tabStop = ((android.text.style.TabStopSpan) objArr[i3]).getTabStop();
                    if (tabStop < f2 && tabStop > f) {
                        f2 = tabStop;
                    }
                }
            }
            if (f2 != Float.MAX_VALUE) {
                return f2;
            }
        }
        return ((int) ((f + TAB_INCREMENT) / TAB_INCREMENT)) * TAB_INCREMENT;
    }

    protected final boolean isSpanned() {
        return this.mSpannedText;
    }

    static <T> T[] getParagraphSpans(android.text.Spanned spanned, int i, int i2, java.lang.Class<T> cls) {
        if (i == i2 && i > 0) {
            return (T[]) com.android.internal.util.ArrayUtils.emptyArray(cls);
        }
        if (spanned instanceof android.text.SpannableStringBuilder) {
            return (T[]) ((android.text.SpannableStringBuilder) spanned).getSpans(i, i2, cls, false);
        }
        return (T[]) spanned.getSpans(i, i2, cls);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void ellipsize(int i, int i2, int i3, char[] cArr, int i4, android.text.TextUtils.TruncateAt truncateAt) {
        char c;
        int ellipsisCount = getEllipsisCount(i3);
        if (ellipsisCount == 0) {
            return;
        }
        int ellipsisStart = getEllipsisStart(i3);
        int lineStart = getLineStart(i3);
        java.lang.String ellipsisString = android.text.TextUtils.getEllipsisString(truncateAt);
        int length = ellipsisString.length();
        boolean z = ellipsisCount >= length;
        int min = java.lang.Math.min(ellipsisCount, (i2 - ellipsisStart) - lineStart);
        for (int max = java.lang.Math.max(0, (i - ellipsisStart) - lineStart); max < min; max++) {
            if (z && max < length) {
                c = ellipsisString.charAt(max);
            } else {
                c = 65279;
            }
            cArr[(((max + ellipsisStart) + lineStart) + i4) - i] = c;
        }
    }

    public static class Directions {
        public int[] mDirections;

        public Directions(int[] iArr) {
            this.mDirections = iArr;
        }

        public int getRunCount() {
            return this.mDirections.length / 2;
        }

        public int getRunStart(int i) {
            return this.mDirections[i * 2];
        }

        public int getRunLength(int i) {
            return this.mDirections[(i * 2) + 1] & android.text.Layout.RUN_LENGTH_MASK;
        }

        public int getRunLevel(int i) {
            return (this.mDirections[(i * 2) + 1] >>> 26) & 63;
        }

        public boolean isRunRtl(int i) {
            return (this.mDirections[(i * 2) + 1] & 67108864) != 0;
        }
    }

    static class Ellipsizer implements java.lang.CharSequence, android.text.GetChars {
        android.text.Layout mLayout;
        android.text.TextUtils.TruncateAt mMethod;
        java.lang.CharSequence mText;
        int mWidth;

        public Ellipsizer(java.lang.CharSequence charSequence) {
            this.mText = charSequence;
        }

        @Override // java.lang.CharSequence
        public char charAt(int i) {
            char[] obtain = android.text.TextUtils.obtain(1);
            getChars(i, i + 1, obtain, 0);
            char c = obtain[0];
            android.text.TextUtils.recycle(obtain);
            return c;
        }

        @Override // android.text.GetChars
        public void getChars(int i, int i2, char[] cArr, int i3) {
            int lineForOffset = this.mLayout.getLineForOffset(i2);
            android.text.TextUtils.getChars(this.mText, i, i2, cArr, i3);
            for (int lineForOffset2 = this.mLayout.getLineForOffset(i); lineForOffset2 <= lineForOffset; lineForOffset2++) {
                this.mLayout.ellipsize(i, i2, lineForOffset2, cArr, i3, this.mMethod);
            }
        }

        @Override // java.lang.CharSequence
        public int length() {
            return this.mText.length();
        }

        @Override // java.lang.CharSequence
        public java.lang.CharSequence subSequence(int i, int i2) {
            char[] cArr = new char[i2 - i];
            getChars(i, i2, cArr, 0);
            return new java.lang.String(cArr);
        }

        @Override // java.lang.CharSequence
        public java.lang.String toString() {
            char[] cArr = new char[length()];
            getChars(0, length(), cArr, 0);
            return new java.lang.String(cArr);
        }
    }

    static class SpannedEllipsizer extends android.text.Layout.Ellipsizer implements android.text.Spanned {
        private android.text.Spanned mSpanned;

        public SpannedEllipsizer(java.lang.CharSequence charSequence) {
            super(charSequence);
            this.mSpanned = (android.text.Spanned) charSequence;
        }

        @Override // android.text.Spanned
        public <T> T[] getSpans(int i, int i2, java.lang.Class<T> cls) {
            return (T[]) this.mSpanned.getSpans(i, i2, cls);
        }

        @Override // android.text.Spanned
        public int getSpanStart(java.lang.Object obj) {
            return this.mSpanned.getSpanStart(obj);
        }

        @Override // android.text.Spanned
        public int getSpanEnd(java.lang.Object obj) {
            return this.mSpanned.getSpanEnd(obj);
        }

        @Override // android.text.Spanned
        public int getSpanFlags(java.lang.Object obj) {
            return this.mSpanned.getSpanFlags(obj);
        }

        @Override // android.text.Spanned
        public int nextSpanTransition(int i, int i2, java.lang.Class cls) {
            return this.mSpanned.nextSpanTransition(i, i2, cls);
        }

        @Override // android.text.Layout.Ellipsizer, java.lang.CharSequence
        public java.lang.CharSequence subSequence(int i, int i2) {
            char[] cArr = new char[i2 - i];
            getChars(i, i2, cArr, 0);
            android.text.SpannableString spannableString = new android.text.SpannableString(new java.lang.String(cArr));
            android.text.TextUtils.copySpansFrom(this.mSpanned, i, i2, java.lang.Object.class, spannableString, 0);
            return spannableString;
        }
    }

    public static final class Builder {
        private int mEllipsizedWidth;
        private final int mEnd;
        private android.graphics.Paint.FontMetrics mMinimumFontMetrics;
        private final android.text.TextPaint mPaint;
        private boolean mShiftDrawingOffsetForStartOverhang;
        private final int mStart;
        private final java.lang.CharSequence mText;
        private boolean mUseBoundsForWidth;
        private final int mWidth;
        private android.text.Layout.Alignment mAlignment = android.text.Layout.Alignment.ALIGN_NORMAL;
        private float mSpacingMult = 1.0f;
        private float mSpacingAdd = 0.0f;
        private android.text.TextDirectionHeuristic mTextDir = android.text.TextDirectionHeuristics.FIRSTSTRONG_LTR;
        private boolean mIncludePad = true;
        private boolean mFallbackLineSpacing = false;
        private android.text.TextUtils.TruncateAt mEllipsize = null;
        private int mMaxLines = Integer.MAX_VALUE;
        private int mBreakStrategy = 0;
        private int mHyphenationFrequency = 0;
        private int[] mLeftIndents = null;
        private int[] mRightIndents = null;
        private int mJustificationMode = 0;
        private android.graphics.text.LineBreakConfig mLineBreakConfig = android.graphics.text.LineBreakConfig.NONE;

        public Builder(java.lang.CharSequence charSequence, int i, int i2, android.text.TextPaint textPaint, int i3) {
            this.mText = charSequence;
            this.mStart = i;
            this.mEnd = i2;
            this.mPaint = textPaint;
            this.mWidth = i3;
            this.mEllipsizedWidth = i3;
        }

        public android.text.Layout.Builder setAlignment(android.text.Layout.Alignment alignment) {
            this.mAlignment = alignment;
            return this;
        }

        public android.text.Layout.Builder setTextDirectionHeuristic(android.text.TextDirectionHeuristic textDirectionHeuristic) {
            this.mTextDir = textDirectionHeuristic;
            return this;
        }

        public android.text.Layout.Builder setLineSpacingAmount(float f) {
            this.mSpacingAdd = f;
            return this;
        }

        public android.text.Layout.Builder setLineSpacingMultiplier(float f) {
            this.mSpacingMult = f;
            return this;
        }

        public android.text.Layout.Builder setFontPaddingIncluded(boolean z) {
            this.mIncludePad = z;
            return this;
        }

        public android.text.Layout.Builder setFallbackLineSpacingEnabled(boolean z) {
            this.mFallbackLineSpacing = z;
            return this;
        }

        public android.text.Layout.Builder setEllipsizedWidth(int i) {
            this.mEllipsizedWidth = i;
            return this;
        }

        public android.text.Layout.Builder setEllipsize(android.text.TextUtils.TruncateAt truncateAt) {
            this.mEllipsize = truncateAt;
            return this;
        }

        public android.text.Layout.Builder setMaxLines(int i) {
            this.mMaxLines = i;
            return this;
        }

        public android.text.Layout.Builder setBreakStrategy(int i) {
            this.mBreakStrategy = i;
            return this;
        }

        public android.text.Layout.Builder setHyphenationFrequency(int i) {
            this.mHyphenationFrequency = i;
            return this;
        }

        public android.text.Layout.Builder setLeftIndents(int[] iArr) {
            this.mLeftIndents = iArr;
            return this;
        }

        public android.text.Layout.Builder setRightIndents(int[] iArr) {
            this.mRightIndents = iArr;
            return this;
        }

        public android.text.Layout.Builder setJustificationMode(int i) {
            this.mJustificationMode = i;
            return this;
        }

        public android.text.Layout.Builder setLineBreakConfig(android.graphics.text.LineBreakConfig lineBreakConfig) {
            this.mLineBreakConfig = lineBreakConfig;
            return this;
        }

        public android.text.Layout.Builder setUseBoundsForWidth(boolean z) {
            this.mUseBoundsForWidth = z;
            return this;
        }

        public android.text.Layout.Builder setShiftDrawingOffsetForStartOverhang(boolean z) {
            this.mShiftDrawingOffsetForStartOverhang = z;
            return this;
        }

        public android.text.Layout.Builder setMinimumFontMetrics(android.graphics.Paint.FontMetrics fontMetrics) {
            this.mMinimumFontMetrics = fontMetrics;
            return this;
        }

        private android.text.BoringLayout.Metrics isBoring() {
            android.text.BoringLayout.Metrics isBoring;
            if (this.mStart != 0 || this.mEnd != this.mText.length() || (isBoring = android.text.BoringLayout.isBoring(this.mText, this.mPaint, this.mTextDir, this.mFallbackLineSpacing, this.mMinimumFontMetrics, null)) == null) {
                return null;
            }
            if (isBoring.width <= this.mWidth) {
                return isBoring;
            }
            if (this.mEllipsize != null) {
                return isBoring;
            }
            return null;
        }

        public android.text.Layout build() {
            android.text.BoringLayout.Metrics isBoring = isBoring();
            if (isBoring == null) {
                return android.text.StaticLayout.Builder.obtain(this.mText, this.mStart, this.mEnd, this.mPaint, this.mWidth).setAlignment(this.mAlignment).setLineSpacing(this.mSpacingAdd, this.mSpacingMult).setTextDirection(this.mTextDir).setIncludePad(this.mIncludePad).setUseLineSpacingFromFallbacks(this.mFallbackLineSpacing).setEllipsizedWidth(this.mEllipsizedWidth).setEllipsize(this.mEllipsize).setMaxLines(this.mMaxLines).setBreakStrategy(this.mBreakStrategy).setHyphenationFrequency(this.mHyphenationFrequency).setIndents(this.mLeftIndents, this.mRightIndents).setJustificationMode(this.mJustificationMode).setLineBreakConfig(this.mLineBreakConfig).setUseBoundsForWidth(this.mUseBoundsForWidth).setShiftDrawingOffsetForStartOverhang(this.mShiftDrawingOffsetForStartOverhang).build();
            }
            return new android.text.BoringLayout(this.mText, this.mPaint, this.mWidth, this.mAlignment, this.mTextDir, this.mSpacingMult, this.mSpacingAdd, this.mIncludePad, this.mFallbackLineSpacing, this.mEllipsizedWidth, this.mEllipsize, this.mMaxLines, this.mBreakStrategy, this.mHyphenationFrequency, this.mLeftIndents, this.mRightIndents, this.mJustificationMode, this.mLineBreakConfig, isBoring, this.mUseBoundsForWidth, this.mShiftDrawingOffsetForStartOverhang, this.mMinimumFontMetrics);
        }
    }

    public final java.lang.CharSequence getText() {
        return this.mText;
    }

    public final android.text.TextPaint getPaint() {
        return this.mPaint;
    }

    public final int getWidth() {
        return this.mWidth;
    }

    public final android.text.Layout.Alignment getAlignment() {
        return this.mAlignment;
    }

    public final android.text.TextDirectionHeuristic getTextDirectionHeuristic() {
        return this.mTextDir;
    }

    public final float getSpacingMultiplier() {
        return getLineSpacingMultiplier();
    }

    public final float getLineSpacingMultiplier() {
        return this.mSpacingMult;
    }

    public final float getSpacingAdd() {
        return getLineSpacingAmount();
    }

    public final float getLineSpacingAmount() {
        return this.mSpacingAdd;
    }

    public final boolean isFontPaddingIncluded() {
        return this.mIncludePad;
    }

    public boolean isFallbackLineSpacingEnabled() {
        return this.mFallbackLineSpacing;
    }

    public int getEllipsizedWidth() {
        return this.mEllipsizedWidth;
    }

    public final android.text.TextUtils.TruncateAt getEllipsize() {
        return this.mEllipsize;
    }

    public final int getMaxLines() {
        return this.mMaxLines;
    }

    public final int getBreakStrategy() {
        return this.mBreakStrategy;
    }

    public final int getHyphenationFrequency() {
        return this.mHyphenationFrequency;
    }

    public final int[] getLeftIndents() {
        if (this.mLeftIndents == null) {
            return null;
        }
        int length = this.mLeftIndents.length;
        int[] iArr = new int[length];
        java.lang.System.arraycopy(this.mLeftIndents, 0, iArr, 0, length);
        return iArr;
    }

    public final int[] getRightIndents() {
        if (this.mRightIndents == null) {
            return null;
        }
        int length = this.mRightIndents.length;
        int[] iArr = new int[length];
        java.lang.System.arraycopy(this.mRightIndents, 0, iArr, 0, length);
        return iArr;
    }

    public final int getJustificationMode() {
        return this.mJustificationMode;
    }

    public android.graphics.text.LineBreakConfig getLineBreakConfig() {
        return this.mLineBreakConfig;
    }

    public boolean getUseBoundsForWidth() {
        return this.mUseBoundsForWidth;
    }

    public boolean getShiftDrawingOffsetForStartOverhang() {
        return this.mShiftDrawingOffsetForStartOverhang;
    }

    public android.graphics.Paint.FontMetrics getMinimumFontMetrics() {
        return this.mMinimumFontMetrics;
    }
}
