package android.text;

/* loaded from: classes3.dex */
public class DynamicLayout extends android.text.Layout {
    private static final int BLOCK_MINIMUM_CHARACTER_LENGTH = 400;
    private static final int COLUMNS_ELLIPSIZE = 7;
    private static final int COLUMNS_NORMAL = 5;
    private static final int DESCENT = 2;
    private static final int DIR = 0;
    private static final int DIR_SHIFT = 30;
    private static final int ELLIPSIS_COUNT = 6;
    private static final int ELLIPSIS_START = 5;
    private static final int ELLIPSIS_UNDEFINED = Integer.MIN_VALUE;
    private static final int EXTRA = 3;
    private static final int HYPHEN = 4;
    private static final int HYPHEN_MASK = 255;
    public static final int INVALID_BLOCK_INDEX = -1;
    private static final int MAY_PROTRUDE_FROM_TOP_OR_BOTTOM = 4;
    private static final int MAY_PROTRUDE_FROM_TOP_OR_BOTTOM_MASK = 256;
    private static final int PRIORITY = 128;
    private static final int START = 0;
    private static final int START_MASK = 536870911;
    private static final int TAB = 0;
    private static final int TAB_MASK = 536870912;
    private static final int TOP = 1;
    private java.lang.CharSequence mBase;
    private int[] mBlockEndLines;
    private int[] mBlockIndices;
    private android.util.ArraySet<java.lang.Integer> mBlocksAlwaysNeedToBeRedrawn;
    private int mBottomPadding;
    private int mBreakStrategy;
    private java.lang.CharSequence mDisplay;
    private boolean mEllipsize;
    private android.text.TextUtils.TruncateAt mEllipsizeAt;
    private int mEllipsizedWidth;
    private boolean mFallbackLineSpacing;
    private int mHyphenationFrequency;
    private boolean mIncludePad;
    private int mIndexFirstChangedBlock;
    private android.text.PackedIntVector mInts;
    private int mJustificationMode;
    private android.graphics.text.LineBreakConfig mLineBreakConfig;
    android.graphics.Paint.FontMetrics mMinimumFontMetrics;
    private int mNumberOfBlocks;
    private android.text.PackedObjectVector<android.text.Layout.Directions> mObjects;
    private boolean mShiftDrawingOffsetForStartOverhang;
    private android.graphics.Rect mTempRect;
    private int mTopPadding;
    private boolean mUseBoundsForWidth;
    private android.text.DynamicLayout.ChangeWatcher mWatcher;
    private static android.text.StaticLayout sStaticLayout = null;
    private static android.text.StaticLayout.Builder sBuilder = null;
    private static final java.lang.Object[] sLock = new java.lang.Object[0];

    public static final class Builder {
        private static final android.util.Pools.SynchronizedPool<android.text.DynamicLayout.Builder> sPool = new android.util.Pools.SynchronizedPool<>(3);
        private android.text.Layout.Alignment mAlignment;
        private java.lang.CharSequence mBase;
        private int mBreakStrategy;
        private java.lang.CharSequence mDisplay;
        private android.text.TextUtils.TruncateAt mEllipsize;
        private int mEllipsizedWidth;
        private boolean mFallbackLineSpacing;
        private int mHyphenationFrequency;
        private boolean mIncludePad;
        private int mJustificationMode;
        private android.graphics.Paint.FontMetrics mMinimumFontMetrics;
        private android.text.TextPaint mPaint;
        private boolean mShiftDrawingOffsetForStartOverhang;
        private float mSpacingAdd;
        private float mSpacingMult;
        private android.text.TextDirectionHeuristic mTextDir;
        private boolean mUseBoundsForWidth;
        private int mWidth;
        private android.graphics.text.LineBreakConfig mLineBreakConfig = android.graphics.text.LineBreakConfig.NONE;
        private final android.graphics.Paint.FontMetricsInt mFontMetricsInt = new android.graphics.Paint.FontMetricsInt();

        private Builder() {
        }

        public static android.text.DynamicLayout.Builder obtain(java.lang.CharSequence charSequence, android.text.TextPaint textPaint, int i) {
            android.text.DynamicLayout.Builder acquire = sPool.acquire();
            if (acquire == null) {
                acquire = new android.text.DynamicLayout.Builder();
            }
            acquire.mBase = charSequence;
            acquire.mDisplay = charSequence;
            acquire.mPaint = textPaint;
            acquire.mWidth = i;
            acquire.mAlignment = android.text.Layout.Alignment.ALIGN_NORMAL;
            acquire.mTextDir = android.text.TextDirectionHeuristics.FIRSTSTRONG_LTR;
            acquire.mSpacingMult = 1.0f;
            acquire.mSpacingAdd = 0.0f;
            acquire.mIncludePad = true;
            acquire.mFallbackLineSpacing = false;
            acquire.mEllipsizedWidth = i;
            acquire.mEllipsize = null;
            acquire.mBreakStrategy = 0;
            acquire.mHyphenationFrequency = 0;
            acquire.mJustificationMode = 0;
            acquire.mLineBreakConfig = android.graphics.text.LineBreakConfig.NONE;
            return acquire;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void recycle(android.text.DynamicLayout.Builder builder) {
            builder.mBase = null;
            builder.mDisplay = null;
            builder.mPaint = null;
            sPool.release(builder);
        }

        public android.text.DynamicLayout.Builder setDisplayText(java.lang.CharSequence charSequence) {
            this.mDisplay = charSequence;
            return this;
        }

        public android.text.DynamicLayout.Builder setAlignment(android.text.Layout.Alignment alignment) {
            this.mAlignment = alignment;
            return this;
        }

        public android.text.DynamicLayout.Builder setTextDirection(android.text.TextDirectionHeuristic textDirectionHeuristic) {
            this.mTextDir = textDirectionHeuristic;
            return this;
        }

        public android.text.DynamicLayout.Builder setLineSpacing(float f, float f2) {
            this.mSpacingAdd = f;
            this.mSpacingMult = f2;
            return this;
        }

        public android.text.DynamicLayout.Builder setIncludePad(boolean z) {
            this.mIncludePad = z;
            return this;
        }

        public android.text.DynamicLayout.Builder setUseLineSpacingFromFallbacks(boolean z) {
            this.mFallbackLineSpacing = z;
            return this;
        }

        public android.text.DynamicLayout.Builder setEllipsizedWidth(int i) {
            this.mEllipsizedWidth = i;
            return this;
        }

        public android.text.DynamicLayout.Builder setEllipsize(android.text.TextUtils.TruncateAt truncateAt) {
            this.mEllipsize = truncateAt;
            return this;
        }

        public android.text.DynamicLayout.Builder setBreakStrategy(int i) {
            this.mBreakStrategy = i;
            return this;
        }

        public android.text.DynamicLayout.Builder setHyphenationFrequency(int i) {
            this.mHyphenationFrequency = i;
            return this;
        }

        public android.text.DynamicLayout.Builder setJustificationMode(int i) {
            this.mJustificationMode = i;
            return this;
        }

        public android.text.DynamicLayout.Builder setLineBreakConfig(android.graphics.text.LineBreakConfig lineBreakConfig) {
            this.mLineBreakConfig = lineBreakConfig;
            return this;
        }

        public android.text.DynamicLayout.Builder setUseBoundsForWidth(boolean z) {
            this.mUseBoundsForWidth = z;
            return this;
        }

        public android.text.DynamicLayout.Builder setShiftDrawingOffsetForStartOverhang(boolean z) {
            this.mShiftDrawingOffsetForStartOverhang = z;
            return this;
        }

        public android.text.DynamicLayout.Builder setMinimumFontMetrics(android.graphics.Paint.FontMetrics fontMetrics) {
            this.mMinimumFontMetrics = fontMetrics;
            return this;
        }

        public android.text.DynamicLayout build() {
            android.text.DynamicLayout dynamicLayout = new android.text.DynamicLayout(this);
            recycle(this);
            return dynamicLayout;
        }
    }

    @java.lang.Deprecated
    public DynamicLayout(java.lang.CharSequence charSequence, android.text.TextPaint textPaint, int i, android.text.Layout.Alignment alignment, float f, float f2, boolean z) {
        this(charSequence, charSequence, textPaint, i, alignment, f, f2, z);
    }

    @java.lang.Deprecated
    public DynamicLayout(java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, android.text.TextPaint textPaint, int i, android.text.Layout.Alignment alignment, float f, float f2, boolean z) {
        this(charSequence, charSequence2, textPaint, i, alignment, f, f2, z, null, 0);
    }

    @java.lang.Deprecated
    public DynamicLayout(java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, android.text.TextPaint textPaint, int i, android.text.Layout.Alignment alignment, float f, float f2, boolean z, android.text.TextUtils.TruncateAt truncateAt, int i2) {
        this(charSequence, charSequence2, textPaint, i, alignment, android.text.TextDirectionHeuristics.FIRSTSTRONG_LTR, f, f2, z, 0, 0, 0, android.graphics.text.LineBreakConfig.NONE, truncateAt, i2);
    }

    @java.lang.Deprecated
    public DynamicLayout(java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, android.text.TextPaint textPaint, int i, android.text.Layout.Alignment alignment, android.text.TextDirectionHeuristic textDirectionHeuristic, float f, float f2, boolean z, int i2, int i3, int i4, android.graphics.text.LineBreakConfig lineBreakConfig, android.text.TextUtils.TruncateAt truncateAt, int i5) {
        super(createEllipsizer(truncateAt, charSequence2), textPaint, i, alignment, textDirectionHeuristic, f, f2, z, false, i5, truncateAt, Integer.MAX_VALUE, i2, i3, null, null, i4, lineBreakConfig, false, false, null);
        this.mTempRect = new android.graphics.Rect();
        android.text.DynamicLayout.Builder ellipsize = android.text.DynamicLayout.Builder.obtain(charSequence, textPaint, i).setAlignment(alignment).setTextDirection(textDirectionHeuristic).setLineSpacing(f2, f).setEllipsizedWidth(i5).setEllipsize(truncateAt);
        this.mDisplay = charSequence2;
        this.mIncludePad = z;
        this.mBreakStrategy = i2;
        this.mJustificationMode = i4;
        this.mHyphenationFrequency = i3;
        this.mLineBreakConfig = lineBreakConfig;
        generate(ellipsize);
        android.text.DynamicLayout.Builder.recycle(ellipsize);
    }

    private DynamicLayout(android.text.DynamicLayout.Builder builder) {
        super(createEllipsizer(builder.mEllipsize, builder.mDisplay), builder.mPaint, builder.mWidth, builder.mAlignment, builder.mTextDir, builder.mSpacingMult, builder.mSpacingAdd, builder.mIncludePad, builder.mFallbackLineSpacing, builder.mEllipsizedWidth, builder.mEllipsize, Integer.MAX_VALUE, builder.mBreakStrategy, builder.mHyphenationFrequency, null, null, builder.mJustificationMode, builder.mLineBreakConfig, builder.mUseBoundsForWidth, builder.mShiftDrawingOffsetForStartOverhang, builder.mMinimumFontMetrics);
        this.mTempRect = new android.graphics.Rect();
        this.mDisplay = builder.mDisplay;
        this.mIncludePad = builder.mIncludePad;
        this.mBreakStrategy = builder.mBreakStrategy;
        this.mJustificationMode = builder.mJustificationMode;
        this.mHyphenationFrequency = builder.mHyphenationFrequency;
        this.mLineBreakConfig = builder.mLineBreakConfig;
        generate(builder);
    }

    private static java.lang.CharSequence createEllipsizer(android.text.TextUtils.TruncateAt truncateAt, java.lang.CharSequence charSequence) {
        if (truncateAt == null) {
            return charSequence;
        }
        if (charSequence instanceof android.text.Spanned) {
            return new android.text.Layout.SpannedEllipsizer(charSequence);
        }
        return new android.text.Layout.Ellipsizer(charSequence);
    }

    private void generate(android.text.DynamicLayout.Builder builder) {
        int[] iArr;
        this.mBase = builder.mBase;
        this.mFallbackLineSpacing = builder.mFallbackLineSpacing;
        this.mUseBoundsForWidth = builder.mUseBoundsForWidth;
        this.mShiftDrawingOffsetForStartOverhang = builder.mShiftDrawingOffsetForStartOverhang;
        this.mMinimumFontMetrics = builder.mMinimumFontMetrics;
        if (builder.mEllipsize != null) {
            this.mInts = new android.text.PackedIntVector(7);
            this.mEllipsizedWidth = builder.mEllipsizedWidth;
            this.mEllipsizeAt = builder.mEllipsize;
            android.text.Layout.Ellipsizer ellipsizer = (android.text.Layout.Ellipsizer) getText();
            ellipsizer.mLayout = this;
            ellipsizer.mWidth = builder.mEllipsizedWidth;
            ellipsizer.mMethod = builder.mEllipsize;
            this.mEllipsize = true;
        } else {
            this.mInts = new android.text.PackedIntVector(5);
            this.mEllipsizedWidth = builder.mWidth;
            this.mEllipsizeAt = null;
        }
        this.mObjects = new android.text.PackedObjectVector<>(1);
        if (builder.mEllipsize != null) {
            iArr = new int[7];
            iArr[5] = Integer.MIN_VALUE;
        } else {
            iArr = new int[5];
        }
        android.text.Layout.Directions[] directionsArr = {DIRS_ALL_LEFT_TO_RIGHT};
        android.graphics.Paint.FontMetricsInt fontMetricsInt = builder.mFontMetricsInt;
        builder.mPaint.getFontMetricsInt(fontMetricsInt);
        int i = fontMetricsInt.ascent;
        int i2 = fontMetricsInt.descent;
        iArr[0] = 1073741824;
        iArr[1] = 0;
        iArr[2] = i2;
        this.mInts.insertAt(0, iArr);
        iArr[1] = i2 - i;
        this.mInts.insertAt(1, iArr);
        this.mObjects.insertAt(0, directionsArr);
        reflow(this.mBase, 0, 0, this.mDisplay.length());
        if (this.mBase instanceof android.text.Spannable) {
            if (this.mWatcher == null) {
                this.mWatcher = new android.text.DynamicLayout.ChangeWatcher(this);
            }
            android.text.Spannable spannable = (android.text.Spannable) this.mBase;
            int length = this.mBase.length();
            for (android.text.DynamicLayout.ChangeWatcher changeWatcher : (android.text.DynamicLayout.ChangeWatcher[]) spannable.getSpans(0, length, android.text.DynamicLayout.ChangeWatcher.class)) {
                spannable.removeSpan(changeWatcher);
            }
            spannable.setSpan(this.mWatcher, 0, length, 8388626);
        }
    }

    public void reflow(java.lang.CharSequence charSequence, int i, int i2, int i3) {
        int i4;
        int i5;
        android.text.StaticLayout staticLayout;
        android.text.StaticLayout.Builder builder;
        android.text.StaticLayout.Builder builder2;
        int i6;
        int i7;
        int[] iArr;
        char c;
        boolean z;
        if (charSequence != this.mBase) {
            return;
        }
        java.lang.CharSequence charSequence2 = this.mDisplay;
        int length = charSequence2.length();
        int lastIndexOf = android.text.TextUtils.lastIndexOf(charSequence2, '\n', i - 1);
        if (lastIndexOf < 0) {
            i4 = 0;
        } else {
            i4 = lastIndexOf + 1;
        }
        int i8 = i - i4;
        int i9 = i2 + i8;
        int i10 = i3 + i8;
        int i11 = i - i8;
        int i12 = i11 + i10;
        int indexOf = android.text.TextUtils.indexOf(charSequence2, '\n', i12);
        if (indexOf < 0) {
            i5 = length;
        } else {
            i5 = indexOf + 1;
        }
        int i13 = i5 - i12;
        int i14 = i9 + i13;
        int i15 = i10 + i13;
        if (charSequence2 instanceof android.text.Spanned) {
            android.text.Spanned spanned = (android.text.Spanned) charSequence2;
            do {
                java.lang.Object[] spans = spanned.getSpans(i11, i11 + i15, android.text.style.WrapTogetherSpan.class);
                z = false;
                for (int i16 = 0; i16 < spans.length; i16++) {
                    int spanStart = spanned.getSpanStart(spans[i16]);
                    int spanEnd = spanned.getSpanEnd(spans[i16]);
                    if (spanStart < i11) {
                        int i17 = i11 - spanStart;
                        i14 += i17;
                        i15 += i17;
                        i11 -= i17;
                        z = true;
                    }
                    int i18 = i11 + i15;
                    if (spanEnd > i18) {
                        int i19 = spanEnd - i18;
                        i14 += i19;
                        i15 += i19;
                        z = true;
                    }
                }
            } while (z);
        }
        int lineForOffset = getLineForOffset(i11);
        int lineTop = getLineTop(lineForOffset);
        int lineForOffset2 = getLineForOffset(i11 + i14);
        int i20 = i11 + i15;
        if (i20 == length) {
            lineForOffset2 = getLineCount();
        }
        int lineTop2 = getLineTop(lineForOffset2);
        boolean z2 = lineForOffset2 == getLineCount();
        synchronized (sLock) {
            staticLayout = sStaticLayout;
            builder = sBuilder;
            sStaticLayout = null;
            sBuilder = null;
        }
        if (builder != null) {
            builder2 = builder;
        } else {
            builder2 = android.text.StaticLayout.Builder.obtain(charSequence2, i11, i20, getPaint(), getWidth());
        }
        builder2.setText(charSequence2, i11, i20).setPaint(getPaint()).setWidth(getWidth()).setTextDirection(getTextDirectionHeuristic()).setLineSpacing(getSpacingAdd(), getSpacingMultiplier()).setUseLineSpacingFromFallbacks(this.mFallbackLineSpacing).setEllipsizedWidth(this.mEllipsizedWidth).setEllipsize(this.mEllipsizeAt).setBreakStrategy(this.mBreakStrategy).setHyphenationFrequency(this.mHyphenationFrequency).setJustificationMode(this.mJustificationMode).setLineBreakConfig(this.mLineBreakConfig).setAddLastLineLineSpacing(!z2).setIncludePad(false).setUseBoundsForWidth(this.mUseBoundsForWidth).setShiftDrawingOffsetForStartOverhang(this.mShiftDrawingOffsetForStartOverhang).setMinimumFontMetrics(this.mMinimumFontMetrics).setCalculateBounds(true);
        android.text.StaticLayout buildPartialStaticLayoutForDynamicLayout = builder2.buildPartialStaticLayoutForDynamicLayout(true, staticLayout);
        int lineCount = buildPartialStaticLayoutForDynamicLayout.getLineCount();
        if (i20 != length && buildPartialStaticLayoutForDynamicLayout.getLineStart(lineCount - 1) == i20) {
            lineCount--;
        }
        int i21 = lineForOffset2 - lineForOffset;
        this.mInts.deleteAt(lineForOffset, i21);
        this.mObjects.deleteAt(lineForOffset, i21);
        int lineTop3 = buildPartialStaticLayoutForDynamicLayout.getLineTop(lineCount);
        if (this.mIncludePad && lineForOffset == 0) {
            i6 = buildPartialStaticLayoutForDynamicLayout.getTopPadding();
            this.mTopPadding = i6;
            lineTop3 -= i6;
        } else {
            i6 = 0;
        }
        if (this.mIncludePad && z2) {
            i7 = buildPartialStaticLayoutForDynamicLayout.getBottomPadding();
            this.mBottomPadding = i7;
            lineTop3 += i7;
        } else {
            i7 = 0;
        }
        this.mInts.adjustValuesBelow(lineForOffset, 0, i15 - i14);
        this.mInts.adjustValuesBelow(lineForOffset, 1, (lineTop - lineTop2) + lineTop3);
        if (this.mEllipsize) {
            iArr = new int[7];
            iArr[5] = Integer.MIN_VALUE;
        } else {
            iArr = new int[5];
        }
        android.text.Layout.Directions[] directionsArr = new android.text.Layout.Directions[1];
        int i22 = 0;
        while (i22 < lineCount) {
            int lineStart = buildPartialStaticLayoutForDynamicLayout.getLineStart(i22);
            iArr[0] = lineStart;
            iArr[0] = iArr[0] | (buildPartialStaticLayoutForDynamicLayout.getParagraphDirection(i22) << 30);
            iArr[0] = iArr[0] | (buildPartialStaticLayoutForDynamicLayout.getLineContainsTab(i22) ? 536870912 : 0);
            int lineTop4 = buildPartialStaticLayoutForDynamicLayout.getLineTop(i22) + lineTop;
            if (i22 > 0) {
                lineTop4 -= i6;
            }
            iArr[1] = lineTop4;
            int lineDescent = buildPartialStaticLayoutForDynamicLayout.getLineDescent(i22);
            int i23 = lineCount - 1;
            if (i22 == i23) {
                lineDescent += i7;
            }
            iArr[2] = lineDescent;
            iArr[3] = buildPartialStaticLayoutForDynamicLayout.getLineExtra(i22);
            directionsArr[0] = buildPartialStaticLayoutForDynamicLayout.getLineDirections(i22);
            int lineStart2 = i22 == i23 ? i20 : buildPartialStaticLayoutForDynamicLayout.getLineStart(i22 + 1);
            int i24 = lineTop;
            int i25 = i20;
            iArr[4] = android.text.StaticLayout.packHyphenEdit(buildPartialStaticLayoutForDynamicLayout.getStartHyphenEdit(i22), buildPartialStaticLayoutForDynamicLayout.getEndHyphenEdit(i22));
            iArr[4] = (contentMayProtrudeFromLineTopOrBottom(charSequence2, lineStart, lineStart2) ? 256 : 0) | iArr[4];
            if (!this.mEllipsize) {
                c = 5;
            } else {
                c = 5;
                iArr[5] = buildPartialStaticLayoutForDynamicLayout.getEllipsisStart(i22);
                iArr[6] = buildPartialStaticLayoutForDynamicLayout.getEllipsisCount(i22);
            }
            int i26 = lineForOffset + i22;
            this.mInts.insertAt(i26, iArr);
            this.mObjects.insertAt(i26, directionsArr);
            i22++;
            i20 = i25;
            lineTop = i24;
        }
        updateBlocks(lineForOffset, lineForOffset2 - 1, lineCount);
        builder2.finish();
        synchronized (sLock) {
            sStaticLayout = buildPartialStaticLayoutForDynamicLayout;
            sBuilder = builder2;
        }
    }

    private boolean contentMayProtrudeFromLineTopOrBottom(java.lang.CharSequence charSequence, int i, int i2) {
        if ((charSequence instanceof android.text.Spanned) && ((android.text.style.ReplacementSpan[]) ((android.text.Spanned) charSequence).getSpans(i, i2, android.text.style.ReplacementSpan.class)).length > 0) {
            return true;
        }
        android.text.TextPaint paint = getPaint();
        if (charSequence instanceof android.text.PrecomputedText) {
            ((android.text.PrecomputedText) charSequence).getBounds(i, i2, this.mTempRect);
        } else {
            paint.getTextBounds(charSequence, i, i2, this.mTempRect);
        }
        android.graphics.Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        return this.mTempRect.top < fontMetricsInt.top || this.mTempRect.bottom > fontMetricsInt.bottom;
    }

    private void createBlocks() {
        this.mNumberOfBlocks = 0;
        java.lang.CharSequence charSequence = this.mDisplay;
        int i = 400;
        while (true) {
            int indexOf = android.text.TextUtils.indexOf(charSequence, '\n', i);
            if (indexOf < 0) {
                break;
            }
            addBlockAtOffset(indexOf);
            i = indexOf + 400;
        }
        addBlockAtOffset(charSequence.length());
        this.mBlockIndices = new int[this.mBlockEndLines.length];
        for (int i2 = 0; i2 < this.mBlockEndLines.length; i2++) {
            this.mBlockIndices[i2] = -1;
        }
    }

    public android.util.ArraySet<java.lang.Integer> getBlocksAlwaysNeedToBeRedrawn() {
        return this.mBlocksAlwaysNeedToBeRedrawn;
    }

    private void updateAlwaysNeedsToBeRedrawn(int i) {
        int i2 = this.mBlockEndLines[i];
        for (int i3 = i == 0 ? 0 : this.mBlockEndLines[i - 1] + 1; i3 <= i2; i3++) {
            if (getContentMayProtrudeFromTopOrBottom(i3)) {
                if (this.mBlocksAlwaysNeedToBeRedrawn == null) {
                    this.mBlocksAlwaysNeedToBeRedrawn = new android.util.ArraySet<>();
                }
                this.mBlocksAlwaysNeedToBeRedrawn.add(java.lang.Integer.valueOf(i));
                return;
            }
        }
        if (this.mBlocksAlwaysNeedToBeRedrawn != null) {
            this.mBlocksAlwaysNeedToBeRedrawn.remove(java.lang.Integer.valueOf(i));
        }
    }

    private void addBlockAtOffset(int i) {
        int lineForOffset = getLineForOffset(i);
        if (this.mBlockEndLines == null) {
            this.mBlockEndLines = com.android.internal.util.ArrayUtils.newUnpaddedIntArray(1);
            this.mBlockEndLines[this.mNumberOfBlocks] = lineForOffset;
            updateAlwaysNeedsToBeRedrawn(this.mNumberOfBlocks);
            this.mNumberOfBlocks++;
            return;
        }
        if (lineForOffset > this.mBlockEndLines[this.mNumberOfBlocks - 1]) {
            this.mBlockEndLines = com.android.internal.util.GrowingArrayUtils.append(this.mBlockEndLines, this.mNumberOfBlocks, lineForOffset);
            updateAlwaysNeedsToBeRedrawn(this.mNumberOfBlocks);
            this.mNumberOfBlocks++;
        }
    }

    public void updateBlocks(int i, int i2, int i3) {
        int i4;
        boolean z;
        boolean z2;
        boolean z3;
        int i5;
        int i6;
        boolean z4;
        int i7;
        if (this.mBlockEndLines == null) {
            createBlocks();
            return;
        }
        int i8 = 0;
        while (true) {
            if (i8 >= this.mNumberOfBlocks) {
                i8 = -1;
                break;
            } else if (this.mBlockEndLines[i8] >= i) {
                break;
            } else {
                i8++;
            }
        }
        int i9 = i8;
        while (true) {
            if (i9 >= this.mNumberOfBlocks) {
                i9 = -1;
                break;
            } else if (this.mBlockEndLines[i9] >= i2) {
                break;
            } else {
                i9++;
            }
        }
        int i10 = this.mBlockEndLines[i9];
        if (i8 == 0) {
            i4 = 0;
        } else {
            i4 = this.mBlockEndLines[i8 - 1] + 1;
        }
        if (i <= i4) {
            z = false;
        } else {
            z = true;
        }
        if (i3 <= 0) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (i2 >= this.mBlockEndLines[i9]) {
            z3 = false;
        } else {
            z3 = true;
        }
        if (!z) {
            i5 = 0;
        } else {
            i5 = 1;
        }
        if (z2) {
            i5++;
        }
        if (z3) {
            i5++;
        }
        int i11 = (i9 - i8) + 1;
        int i12 = (this.mNumberOfBlocks + i5) - i11;
        if (i12 == 0) {
            this.mBlockEndLines[0] = 0;
            this.mBlockIndices[0] = -1;
            this.mNumberOfBlocks = 1;
            return;
        }
        if (i12 > this.mBlockEndLines.length) {
            int[] newUnpaddedIntArray = com.android.internal.util.ArrayUtils.newUnpaddedIntArray(java.lang.Math.max(this.mBlockEndLines.length * 2, i12));
            int[] iArr = new int[newUnpaddedIntArray.length];
            java.lang.System.arraycopy(this.mBlockEndLines, 0, newUnpaddedIntArray, 0, i8);
            java.lang.System.arraycopy(this.mBlockIndices, 0, iArr, 0, i8);
            int i13 = i9 + 1;
            i6 = i10;
            int i14 = i8 + i5;
            z4 = z3;
            java.lang.System.arraycopy(this.mBlockEndLines, i13, newUnpaddedIntArray, i14, (this.mNumberOfBlocks - i9) - 1);
            java.lang.System.arraycopy(this.mBlockIndices, i13, iArr, i14, (this.mNumberOfBlocks - i9) - 1);
            this.mBlockEndLines = newUnpaddedIntArray;
            this.mBlockIndices = iArr;
        } else {
            i6 = i10;
            z4 = z3;
            if (i5 + i11 != 0) {
                int i15 = i9 + 1;
                int i16 = i8 + i5;
                java.lang.System.arraycopy(this.mBlockEndLines, i15, this.mBlockEndLines, i16, (this.mNumberOfBlocks - i9) - 1);
                java.lang.System.arraycopy(this.mBlockIndices, i15, this.mBlockIndices, i16, (this.mNumberOfBlocks - i9) - 1);
            }
        }
        if (i5 + i11 != 0 && this.mBlocksAlwaysNeedToBeRedrawn != null) {
            android.util.ArraySet<java.lang.Integer> arraySet = new android.util.ArraySet<>();
            int i17 = i5 - i11;
            for (int i18 = 0; i18 < this.mBlocksAlwaysNeedToBeRedrawn.size(); i18++) {
                java.lang.Integer valueAt = this.mBlocksAlwaysNeedToBeRedrawn.valueAt(i18);
                if (valueAt.intValue() < i8) {
                    arraySet.add(valueAt);
                }
                if (valueAt.intValue() > i9) {
                    arraySet.add(java.lang.Integer.valueOf(valueAt.intValue() + i17));
                }
            }
            this.mBlocksAlwaysNeedToBeRedrawn = arraySet;
        }
        this.mNumberOfBlocks = i12;
        int i19 = i3 - ((i2 - i) + 1);
        if (i19 != 0) {
            i7 = i5 + i8;
            for (int i20 = i7; i20 < this.mNumberOfBlocks; i20++) {
                int[] iArr2 = this.mBlockEndLines;
                iArr2[i20] = iArr2[i20] + i19;
            }
        } else {
            i7 = this.mNumberOfBlocks;
        }
        this.mIndexFirstChangedBlock = java.lang.Math.min(this.mIndexFirstChangedBlock, i7);
        if (z) {
            this.mBlockEndLines[i8] = i - 1;
            updateAlwaysNeedsToBeRedrawn(i8);
            this.mBlockIndices[i8] = -1;
            i8++;
        }
        if (z2) {
            this.mBlockEndLines[i8] = (i + i3) - 1;
            updateAlwaysNeedsToBeRedrawn(i8);
            this.mBlockIndices[i8] = -1;
            i8++;
        }
        if (z4) {
            this.mBlockEndLines[i8] = i6 + i19;
            updateAlwaysNeedsToBeRedrawn(i8);
            this.mBlockIndices[i8] = -1;
        }
    }

    public void setBlocksDataForTest(int[] iArr, int[] iArr2, int i, int i2) {
        this.mBlockEndLines = new int[iArr.length];
        this.mBlockIndices = new int[iArr2.length];
        java.lang.System.arraycopy(iArr, 0, this.mBlockEndLines, 0, iArr.length);
        java.lang.System.arraycopy(iArr2, 0, this.mBlockIndices, 0, iArr2.length);
        this.mNumberOfBlocks = i;
        while (this.mInts.size() < i2) {
            this.mInts.insertAt(this.mInts.size(), new int[5]);
        }
    }

    public int[] getBlockEndLines() {
        return this.mBlockEndLines;
    }

    public int[] getBlockIndices() {
        return this.mBlockIndices;
    }

    public int getBlockIndex(int i) {
        return this.mBlockIndices[i];
    }

    public void setBlockIndex(int i, int i2) {
        this.mBlockIndices[i] = i2;
    }

    public int getNumberOfBlocks() {
        return this.mNumberOfBlocks;
    }

    public int getIndexFirstChangedBlock() {
        return this.mIndexFirstChangedBlock;
    }

    public void setIndexFirstChangedBlock(int i) {
        this.mIndexFirstChangedBlock = i;
    }

    @Override // android.text.Layout
    public int getLineCount() {
        return this.mInts.size() - 1;
    }

    @Override // android.text.Layout
    public int getLineTop(int i) {
        return this.mInts.getValue(i, 1);
    }

    @Override // android.text.Layout
    public int getLineDescent(int i) {
        return this.mInts.getValue(i, 2);
    }

    @Override // android.text.Layout
    public int getLineExtra(int i) {
        return this.mInts.getValue(i, 3);
    }

    @Override // android.text.Layout
    public int getLineStart(int i) {
        return this.mInts.getValue(i, 0) & 536870911;
    }

    @Override // android.text.Layout
    public boolean getLineContainsTab(int i) {
        return (this.mInts.getValue(i, 0) & 536870912) != 0;
    }

    @Override // android.text.Layout
    public int getParagraphDirection(int i) {
        return this.mInts.getValue(i, 0) >> 30;
    }

    @Override // android.text.Layout
    public final android.text.Layout.Directions getLineDirections(int i) {
        return this.mObjects.getValue(i, 0);
    }

    @Override // android.text.Layout
    public int getTopPadding() {
        return this.mTopPadding;
    }

    @Override // android.text.Layout
    public int getBottomPadding() {
        return this.mBottomPadding;
    }

    @Override // android.text.Layout
    public int getStartHyphenEdit(int i) {
        return android.text.StaticLayout.unpackStartHyphenEdit(this.mInts.getValue(i, 4) & 255);
    }

    @Override // android.text.Layout
    public int getEndHyphenEdit(int i) {
        return android.text.StaticLayout.unpackEndHyphenEdit(this.mInts.getValue(i, 4) & 255);
    }

    private boolean getContentMayProtrudeFromTopOrBottom(int i) {
        return (this.mInts.getValue(i, 4) & 256) != 0;
    }

    @Override // android.text.Layout
    public int getEllipsizedWidth() {
        return this.mEllipsizedWidth;
    }

    private static class ChangeWatcher implements android.text.TextWatcher, android.text.SpanWatcher {
        private java.lang.ref.WeakReference<android.text.DynamicLayout> mLayout;
        private android.text.method.OffsetMapping.TextUpdate mTransformedTextUpdate;

        public ChangeWatcher(android.text.DynamicLayout dynamicLayout) {
            this.mLayout = new java.lang.ref.WeakReference<>(dynamicLayout);
        }

        private void reflow(java.lang.CharSequence charSequence, int i, int i2, int i3) {
            android.text.DynamicLayout dynamicLayout = this.mLayout.get();
            if (dynamicLayout != null) {
                dynamicLayout.reflow(charSequence, i, i2, i3);
            } else if (charSequence instanceof android.text.Spannable) {
                ((android.text.Spannable) charSequence).removeSpan(this);
            }
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(java.lang.CharSequence charSequence, int i, int i2, int i3) {
            android.text.DynamicLayout dynamicLayout = this.mLayout.get();
            if (dynamicLayout != null && (dynamicLayout.mDisplay instanceof android.text.method.OffsetMapping)) {
                android.text.method.OffsetMapping offsetMapping = (android.text.method.OffsetMapping) dynamicLayout.mDisplay;
                if (this.mTransformedTextUpdate == null) {
                    this.mTransformedTextUpdate = new android.text.method.OffsetMapping.TextUpdate(i, i2, i3);
                } else {
                    this.mTransformedTextUpdate.where = i;
                    this.mTransformedTextUpdate.before = i2;
                    this.mTransformedTextUpdate.after = i3;
                }
                offsetMapping.originalToTransformed(this.mTransformedTextUpdate);
            }
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(java.lang.CharSequence charSequence, int i, int i2, int i3) {
            android.text.DynamicLayout dynamicLayout = this.mLayout.get();
            if (dynamicLayout != null && (dynamicLayout.mDisplay instanceof android.text.method.OffsetMapping)) {
                if (this.mTransformedTextUpdate != null && this.mTransformedTextUpdate.where >= 0) {
                    i = this.mTransformedTextUpdate.where;
                    i2 = this.mTransformedTextUpdate.before;
                    i3 = this.mTransformedTextUpdate.after;
                    this.mTransformedTextUpdate.where = -1;
                } else {
                    i2 = dynamicLayout.getLineEnd(dynamicLayout.getLineCount() - 1);
                    i3 = dynamicLayout.mDisplay.length();
                    i = 0;
                }
            }
            reflow(charSequence, i, i2, i3);
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(android.text.Editable editable) {
        }

        private void transformAndReflow(android.text.Spannable spannable, int i, int i2) {
            android.text.DynamicLayout dynamicLayout = this.mLayout.get();
            if (dynamicLayout != null && (dynamicLayout.mDisplay instanceof android.text.method.OffsetMapping)) {
                android.text.method.OffsetMapping offsetMapping = (android.text.method.OffsetMapping) dynamicLayout.mDisplay;
                i = offsetMapping.originalToTransformed(i, 0);
                i2 = offsetMapping.originalToTransformed(i2, 0);
            }
            int i3 = i2 - i;
            reflow(spannable, i, i3, i3);
        }

        @Override // android.text.SpanWatcher
        public void onSpanAdded(android.text.Spannable spannable, java.lang.Object obj, int i, int i2) {
            if (obj instanceof android.text.style.UpdateLayout) {
                transformAndReflow(spannable, i, i2);
            }
        }

        @Override // android.text.SpanWatcher
        public void onSpanRemoved(android.text.Spannable spannable, java.lang.Object obj, int i, int i2) {
            if (obj instanceof android.text.style.UpdateLayout) {
                if (com.android.text.flags.Flags.insertModeCrashWhenDelete()) {
                    android.text.DynamicLayout dynamicLayout = this.mLayout.get();
                    if (dynamicLayout != null && (dynamicLayout.mDisplay instanceof android.text.method.OffsetMapping)) {
                        reflow(spannable, 0, 0, spannable.length());
                        return;
                    } else {
                        int i3 = i2 - i;
                        reflow(spannable, i, i3, i3);
                        return;
                    }
                }
                transformAndReflow(spannable, i, i2);
            }
        }

        @Override // android.text.SpanWatcher
        public void onSpanChanged(android.text.Spannable spannable, java.lang.Object obj, int i, int i2, int i3, int i4) {
            if (obj instanceof android.text.style.UpdateLayout) {
                if (i > i2) {
                    i = 0;
                }
                if (com.android.text.flags.Flags.insertModeCrashWhenDelete()) {
                    android.text.DynamicLayout dynamicLayout = this.mLayout.get();
                    if (dynamicLayout != null && (dynamicLayout.mDisplay instanceof android.text.method.OffsetMapping)) {
                        reflow(spannable, 0, 0, spannable.length());
                        return;
                    }
                    int i5 = i2 - i;
                    reflow(spannable, i, i5, i5);
                    int i6 = i4 - i3;
                    reflow(spannable, i3, i6, i6);
                    return;
                }
                transformAndReflow(spannable, i, i2);
                transformAndReflow(spannable, i3, i4);
            }
        }
    }

    @Override // android.text.Layout
    public int getEllipsisStart(int i) {
        if (this.mEllipsizeAt == null) {
            return 0;
        }
        return this.mInts.getValue(i, 5);
    }

    @Override // android.text.Layout
    public int getEllipsisCount(int i) {
        if (this.mEllipsizeAt == null) {
            return 0;
        }
        return this.mInts.getValue(i, 6);
    }

    @Override // android.text.Layout
    public android.graphics.text.LineBreakConfig getLineBreakConfig() {
        return this.mLineBreakConfig;
    }
}
