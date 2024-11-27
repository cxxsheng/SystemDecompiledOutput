package android.text;

/* loaded from: classes3.dex */
public class TextLine {
    private static final boolean DEBUG = false;
    private static final char TAB_CHAR = '\t';
    private static final int TAB_INCREMENT = 20;
    private static final android.text.TextLine[] sCached = new android.text.TextLine[3];
    private float mAddedLetterSpacingInPx;
    private float mAddedWordSpacingInPx;
    private char[] mChars;
    private boolean mCharsValid;
    private android.text.PrecomputedText mComputed;
    private int mDir;
    private android.text.Layout.Directions mDirections;
    private int mEllipsisEnd;
    private int mEllipsisStart;
    private boolean mHasTabs;
    private boolean mIsJustifying;
    private int mLen;
    private android.text.TextPaint mPaint;
    private android.graphics.Paint.RunInfo mRunInfo;
    private android.text.Spanned mSpanned;
    private int mStart;
    private android.text.Layout.TabStops mTabs;
    private java.lang.CharSequence mText;
    private android.graphics.RectF mTmpRectForMeasure;
    private android.graphics.RectF mTmpRectForPaintAPI;
    private android.graphics.Rect mTmpRectForPrecompute;
    private boolean mUseFallbackExtent = false;
    private final android.text.TextPaint mWorkPaint = new android.text.TextPaint();
    private final android.text.TextPaint mActivePaint = new android.text.TextPaint();
    private final android.text.SpanSet<android.text.style.MetricAffectingSpan> mMetricAffectingSpanSpanSet = new android.text.SpanSet<>(android.text.style.MetricAffectingSpan.class);
    private final android.text.SpanSet<android.text.style.CharacterStyle> mCharacterStyleSpanSet = new android.text.SpanSet<>(android.text.style.CharacterStyle.class);
    private final android.text.SpanSet<android.text.style.ReplacementSpan> mReplacementSpanSpanSet = new android.text.SpanSet<>(android.text.style.ReplacementSpan.class);
    private final android.text.TextLine.DecorationInfo mDecorationInfo = new android.text.TextLine.DecorationInfo();
    private final java.util.ArrayList<android.text.TextLine.DecorationInfo> mDecorations = new java.util.ArrayList<>();

    public static final class LineInfo {
        private int mClusterCount;

        public int getClusterCount() {
            return this.mClusterCount;
        }

        public void setClusterCount(int i) {
            this.mClusterCount = i;
        }
    }

    public float getAddedWordSpacingInPx() {
        return this.mAddedWordSpacingInPx;
    }

    public float getAddedLetterSpacingInPx() {
        return this.mAddedLetterSpacingInPx;
    }

    public boolean isJustifying() {
        return this.mIsJustifying;
    }

    public static android.text.TextLine obtain() {
        synchronized (sCached) {
            int length = sCached.length;
            do {
                length--;
                if (length < 0) {
                    return new android.text.TextLine();
                }
            } while (sCached[length] == null);
            android.text.TextLine textLine = sCached[length];
            sCached[length] = null;
            return textLine;
        }
    }

    public static android.text.TextLine recycle(android.text.TextLine textLine) {
        textLine.mText = null;
        textLine.mPaint = null;
        textLine.mDirections = null;
        textLine.mSpanned = null;
        textLine.mTabs = null;
        textLine.mChars = null;
        textLine.mComputed = null;
        int i = 0;
        textLine.mUseFallbackExtent = false;
        textLine.mMetricAffectingSpanSpanSet.recycle();
        textLine.mCharacterStyleSpanSet.recycle();
        textLine.mReplacementSpanSpanSet.recycle();
        synchronized (sCached) {
            while (true) {
                if (i >= sCached.length) {
                    break;
                }
                if (sCached[i] != null) {
                    i++;
                } else {
                    sCached[i] = textLine;
                    break;
                }
            }
        }
        return null;
    }

    public void set(android.text.TextPaint textPaint, java.lang.CharSequence charSequence, int i, int i2, int i3, android.text.Layout.Directions directions, boolean z, android.text.Layout.TabStops tabStops, int i4, int i5, boolean z2) {
        boolean z3;
        int i6;
        this.mPaint = textPaint;
        this.mText = charSequence;
        this.mStart = i;
        this.mLen = i2 - i;
        this.mDir = i3;
        this.mDirections = directions;
        this.mUseFallbackExtent = z2;
        if (this.mDirections == null) {
            throw new java.lang.IllegalArgumentException("Directions cannot be null");
        }
        this.mHasTabs = z;
        this.mSpanned = null;
        if (!(charSequence instanceof android.text.Spanned)) {
            z3 = false;
        } else {
            this.mSpanned = (android.text.Spanned) charSequence;
            this.mReplacementSpanSpanSet.init(this.mSpanned, i, i2);
            z3 = this.mReplacementSpanSpanSet.numberOfSpans > 0;
        }
        this.mComputed = null;
        if (charSequence instanceof android.text.PrecomputedText) {
            this.mComputed = (android.text.PrecomputedText) charSequence;
            if (!this.mComputed.getParams().getTextPaint().equalsForTextMeasurement(textPaint)) {
                this.mComputed = null;
            }
        }
        this.mCharsValid = z3;
        if (this.mCharsValid) {
            if (this.mChars == null || this.mChars.length < this.mLen) {
                this.mChars = com.android.internal.util.ArrayUtils.newUnpaddedCharArray(this.mLen);
            }
            android.text.TextUtils.getChars(charSequence, i, i2, this.mChars, 0);
            if (z3) {
                char[] cArr = this.mChars;
                int i7 = i;
                while (i7 < i2) {
                    int nextTransition = this.mReplacementSpanSpanSet.getNextTransition(i7, i2);
                    if (this.mReplacementSpanSpanSet.hasSpansIntersecting(i7, nextTransition) && ((i6 = i7 - i) >= i5 || nextTransition - i <= i4)) {
                        cArr[i6] = 65532;
                        int i8 = nextTransition - i;
                        for (int i9 = i6 + 1; i9 < i8; i9++) {
                            cArr[i9] = 65279;
                        }
                    }
                    i7 = nextTransition;
                }
            }
        }
        this.mTabs = tabStops;
        this.mAddedWordSpacingInPx = 0.0f;
        this.mIsJustifying = false;
        this.mEllipsisStart = i4 != i5 ? i4 : 0;
        if (i4 == i5) {
            i5 = 0;
        }
        this.mEllipsisEnd = i5;
    }

    private char charAt(int i) {
        return this.mCharsValid ? this.mChars[i] : this.mText.charAt(i + this.mStart);
    }

    public void justify(int i, float f) {
        int i2 = this.mLen;
        while (i2 > 0 && isLineEndSpace(this.mText.charAt((this.mStart + i2) - 1))) {
            i2--;
        }
        if (i == 1) {
            float abs = java.lang.Math.abs(measure(i2, false, null, null, null));
            int countStretchableSpaces = countStretchableSpaces(0, i2);
            if (countStretchableSpaces == 0) {
                return;
            }
            this.mAddedWordSpacingInPx = (f - abs) / countStretchableSpaces;
            this.mAddedLetterSpacingInPx = 0.0f;
        } else {
            android.text.TextLine.LineInfo lineInfo = new android.text.TextLine.LineInfo();
            float abs2 = java.lang.Math.abs(measure(i2, false, null, null, lineInfo));
            if (lineInfo.getClusterCount() >= 2) {
                this.mAddedLetterSpacingInPx = (f - abs2) / (r2 - 1);
                if (this.mAddedLetterSpacingInPx > 0.03d) {
                    java.lang.String fontFeatureSettings = this.mPaint.getFontFeatureSettings();
                    this.mPaint.setFontFeatureSettings(fontFeatureSettings + ", \"liga\" off, \"cliga\" off");
                    this.mAddedLetterSpacingInPx = (f - java.lang.Math.abs(measure(i2, false, null, null, lineInfo))) / (lineInfo.getClusterCount() - 1);
                    this.mPaint.setFontFeatureSettings(fontFeatureSettings);
                }
                this.mAddedWordSpacingInPx = 0.0f;
            } else {
                return;
            }
        }
        this.mIsJustifying = true;
    }

    public static int calculateRunFlag(int i, int i2, int i3) {
        if (i2 == 1) {
            return 24576;
        }
        int i4 = 0;
        if (i != 0 && i != i2 - 1) {
            return 0;
        }
        if (i == 0) {
            if (i3 == 1) {
                i4 = 8192;
            } else {
                i4 = 16384;
            }
        }
        if (i == i2 - 1) {
            if (i3 == 1) {
                return i4 | 16384;
            }
            return i4 | 8192;
        }
        return i4;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x001a  */
    /* JADX WARN: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int resolveRunFlagForSubSequence(int i, boolean z, int i2, int i3, int i4, int i5) {
        int i6;
        if (i == 0) {
            return 0;
        }
        if ((i & 8192) != 0) {
            if (z) {
                if (i5 != i3) {
                    i6 = i & (-8193);
                }
            } else if (i4 != i2) {
                i6 = i & (-8193);
            }
            if ((i & 16384) == 0) {
                if (z) {
                    if (i4 != i2) {
                        return i6 & (-16385);
                    }
                    return i6;
                }
                if (i5 != i3) {
                    return i6 & (-16385);
                }
                return i6;
            }
            return i6;
        }
        i6 = i;
        if ((i & 16384) == 0) {
        }
    }

    void draw(android.graphics.Canvas canvas, float f, int i, int i2, int i3) {
        int i4;
        int runCount = this.mDirections.getRunCount();
        float f2 = 0.0f;
        int i5 = 0;
        while (i5 < runCount) {
            int runStart = this.mDirections.getRunStart(i5);
            if (runStart <= this.mLen) {
                int min = java.lang.Math.min(this.mDirections.getRunLength(i5) + runStart, this.mLen);
                boolean isRunRtl = this.mDirections.isRunRtl(i5);
                int calculateRunFlag = calculateRunFlag(i5, runCount, this.mDir);
                float f3 = f2;
                int i6 = this.mHasTabs ? runStart : min;
                int i7 = runStart;
                while (i6 <= min) {
                    if (i6 == min || charAt(i6) == '\t') {
                        i4 = i6;
                        f3 += drawRun(canvas, i7, i6, isRunRtl, f + f3, i, i2, i3, (i5 == runCount + (-1) && i6 == this.mLen) ? false : true, calculateRunFlag);
                        if (i4 != min) {
                            f3 = this.mDir * nextTab(this.mDir * f3);
                        }
                        i7 = i4 + 1;
                    } else {
                        i4 = i6;
                    }
                    i6 = i4 + 1;
                }
                i5++;
                f2 = f3;
            } else {
                return;
            }
        }
    }

    public float metrics(android.graphics.Paint.FontMetricsInt fontMetricsInt, android.graphics.RectF rectF, boolean z, android.text.TextLine.LineInfo lineInfo) {
        if (z) {
            if (rectF == null) {
                if (this.mTmpRectForMeasure == null) {
                    this.mTmpRectForMeasure = new android.graphics.RectF();
                }
                rectF = this.mTmpRectForMeasure;
            }
            rectF.setEmpty();
            float measure = measure(this.mLen, false, fontMetricsInt, rectF, lineInfo);
            float width = rectF.width();
            if (java.lang.Math.abs(measure) > width) {
                return measure;
            }
            return java.lang.Math.signum(measure) * width;
        }
        return measure(this.mLen, false, fontMetricsInt, rectF, lineInfo);
    }

    void shape(android.text.TextShaper.GlyphsConsumer glyphsConsumer) {
        int i;
        int runCount = this.mDirections.getRunCount();
        float f = 0.0f;
        float f2 = 0.0f;
        int i2 = 0;
        while (i2 < runCount) {
            int runStart = this.mDirections.getRunStart(i2);
            if (runStart <= this.mLen) {
                int min = java.lang.Math.min(this.mDirections.getRunLength(i2) + runStart, this.mLen);
                boolean isRunRtl = this.mDirections.isRunRtl(i2);
                int calculateRunFlag = calculateRunFlag(i2, runCount, this.mDir);
                float f3 = f2;
                int i3 = this.mHasTabs ? runStart : min;
                int i4 = runStart;
                while (i3 <= min) {
                    if (i3 == min || charAt(i3) == '\t') {
                        float f4 = f + f3;
                        i = i3;
                        f3 += shapeRun(glyphsConsumer, i4, i3, isRunRtl, f4, (i2 == runCount + (-1) && i3 == this.mLen) ? false : true, calculateRunFlag);
                        if (i != min) {
                            f3 = this.mDir * nextTab(this.mDir * f3);
                        }
                        i4 = i + 1;
                    } else {
                        i = i3;
                    }
                    i3 = i + 1;
                    f = 0.0f;
                }
                i2++;
                f2 = f3;
                f = 0.0f;
            } else {
                return;
            }
        }
    }

    public float measure(int i, boolean z, android.graphics.Paint.FontMetricsInt fontMetricsInt, android.graphics.RectF rectF, android.text.TextLine.LineInfo lineInfo) {
        boolean z2;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        if (i > this.mLen) {
            throw new java.lang.IndexOutOfBoundsException("offset(" + i + ") should be less than line limit(" + this.mLen + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        boolean z3 = false;
        if (lineInfo != null) {
            lineInfo.setClusterCount(0);
        }
        int i7 = z ? i - 1 : i;
        float f = 0.0f;
        if (i7 < 0) {
            return 0.0f;
        }
        int runCount = this.mDirections.getRunCount();
        int i8 = 0;
        while (i8 < runCount) {
            int runStart = this.mDirections.getRunStart(i8);
            if (runStart > this.mLen) {
                break;
            }
            int min = java.lang.Math.min(this.mDirections.getRunLength(i8) + runStart, this.mLen);
            boolean isRunRtl = this.mDirections.isRunRtl(i8);
            int calculateRunFlag = calculateRunFlag(i8, runCount, this.mDir);
            float f2 = f;
            int i9 = runStart;
            int i10 = this.mHasTabs ? runStart : min;
            while (i10 <= min) {
                if (i10 == min || charAt(i10) == '\t') {
                    boolean z4 = (i7 < i9 || i7 >= i10) ? z3 : true;
                    boolean z5 = (this.mDir == -1 ? true : z3) == isRunRtl ? true : z3;
                    if (z4 && z5) {
                        return f2 + measureRun(i9, i, i10, isRunRtl, fontMetricsInt, rectF, null, 0, f2, lineInfo, calculateRunFlag);
                    }
                    int i11 = i10;
                    int i12 = i9;
                    z2 = isRunRtl;
                    i2 = min;
                    i3 = i8;
                    i4 = runCount;
                    i5 = i7;
                    float measureRun = measureRun(i9, i10, i10, isRunRtl, fontMetricsInt, rectF, null, 0, f2, lineInfo, calculateRunFlag);
                    if (!z5) {
                        measureRun = -measureRun;
                    }
                    f2 += measureRun;
                    if (z4) {
                        return f2 + measureRun(i12, i, i11, z2, null, null, null, 0, f2, lineInfo, calculateRunFlag);
                    }
                    i6 = i11;
                    if (i6 != i2) {
                        if (i != i6) {
                            float nextTab = this.mDir * nextTab(this.mDir * f2);
                            if (i5 != i6) {
                                f2 = nextTab;
                            } else {
                                return nextTab;
                            }
                        } else {
                            return f2;
                        }
                    }
                    i9 = i6 + 1;
                } else {
                    i6 = i10;
                    z2 = isRunRtl;
                    i2 = min;
                    i3 = i8;
                    i4 = runCount;
                    i5 = i7;
                }
                i10 = i6 + 1;
                i7 = i5;
                min = i2;
                i8 = i3;
                isRunRtl = z2;
                runCount = i4;
                z3 = false;
            }
            i8++;
            f = f2;
            z3 = false;
        }
        return f;
    }

    public void measureAllBounds(float[] fArr, float[] fArr2) {
        float[] fArr3;
        boolean z;
        int i;
        int i2;
        int i3;
        float nextTab;
        float f;
        if (fArr != null) {
            if (fArr.length < this.mLen * 2) {
                throw new java.lang.IndexOutOfBoundsException("bounds doesn't have enough space to receive the result, needed: " + (this.mLen * 2) + " had: " + fArr.length);
            }
            if (fArr2 != null) {
                fArr3 = fArr2;
            } else {
                fArr3 = new float[this.mLen];
            }
            if (fArr3.length < this.mLen) {
                throw new java.lang.IndexOutOfBoundsException("advance doesn't have enough space to receive the result, needed: " + this.mLen + " had: " + fArr3.length);
            }
            int runCount = this.mDirections.getRunCount();
            float f2 = 0.0f;
            int i4 = 0;
            while (i4 < runCount) {
                int runStart = this.mDirections.getRunStart(i4);
                if (runStart <= this.mLen) {
                    int min = java.lang.Math.min(this.mDirections.getRunLength(i4) + runStart, this.mLen);
                    boolean isRunRtl = this.mDirections.isRunRtl(i4);
                    int calculateRunFlag = calculateRunFlag(i4, runCount, this.mDir);
                    float f3 = f2;
                    int i5 = runStart;
                    int i6 = this.mHasTabs ? runStart : min;
                    while (i6 <= min) {
                        if (i6 == min || charAt(i6) == '\t') {
                            boolean z2 = (this.mDir == -1) == isRunRtl;
                            int i7 = i6;
                            z = isRunRtl;
                            int i8 = min;
                            i = i4;
                            float measureRun = measureRun(i5, i6, i6, isRunRtl, null, null, fArr3, i5, 0.0f, null, calculateRunFlag);
                            if (!z2) {
                                measureRun = -measureRun;
                            }
                            float f4 = f3 + measureRun;
                            if (!z2) {
                                f3 = f4;
                            }
                            int i9 = i5;
                            while (true) {
                                i2 = i7;
                                if (i9 >= i2 || i9 >= this.mLen) {
                                    break;
                                }
                                if (z) {
                                    int i10 = i9 * 2;
                                    fArr[i10 + 1] = f3;
                                    f3 -= fArr3[i9];
                                    fArr[i10] = f3;
                                } else {
                                    int i11 = i9 * 2;
                                    fArr[i11] = f3;
                                    f3 += fArr3[i9];
                                    fArr[i11 + 1] = f3;
                                }
                                i9++;
                                i7 = i2;
                            }
                            i3 = i8;
                            if (i2 == i3) {
                                f3 = f4;
                            } else {
                                if (z) {
                                    f = this.mDir * nextTab(this.mDir * f4);
                                    nextTab = f4;
                                    f4 = f;
                                } else {
                                    nextTab = this.mDir * nextTab(this.mDir * f4);
                                    f = nextTab;
                                }
                                int i12 = i2 * 2;
                                fArr[i12] = f4;
                                fArr[i12 + 1] = nextTab;
                                fArr3[i2] = nextTab - f4;
                                f3 = f;
                            }
                            i5 = i2 + 1;
                        } else {
                            i2 = i6;
                            z = isRunRtl;
                            i3 = min;
                            i = i4;
                        }
                        i6 = i2 + 1;
                        min = i3;
                        i4 = i;
                        isRunRtl = z;
                    }
                    i4++;
                    f2 = f3;
                } else {
                    return;
                }
            }
            return;
        }
        throw new java.lang.IllegalArgumentException("bounds can't be null");
    }

    public float[] measureAllOffsets(boolean[] zArr, android.graphics.Paint.FontMetricsInt fontMetricsInt) {
        int i;
        boolean z;
        int i2;
        int i3;
        int i4;
        float f;
        boolean z2 = true;
        float[] fArr = new float[this.mLen + 1];
        boolean z3 = false;
        if (zArr[0]) {
            fArr[0] = 0.0f;
        }
        int runCount = this.mDirections.getRunCount();
        int i5 = 0;
        float f2 = 0.0f;
        while (i5 < runCount) {
            int runStart = this.mDirections.getRunStart(i5);
            if (runStart > this.mLen) {
                break;
            }
            int min = java.lang.Math.min(this.mDirections.getRunLength(i5) + runStart, this.mLen);
            boolean isRunRtl = this.mDirections.isRunRtl(i5);
            int calculateRunFlag = calculateRunFlag(i5, runCount, this.mDir);
            float f3 = f2;
            int i6 = runStart;
            int i7 = this.mHasTabs ? runStart : min;
            while (i7 <= min) {
                if (i7 == min || charAt(i7) == '\t') {
                    boolean z4 = (this.mDir == -1 ? z2 : z3) == isRunRtl ? z2 : z3;
                    float f4 = fArr[i6];
                    i = i7;
                    int i8 = i6;
                    z = isRunRtl;
                    i2 = min;
                    i3 = i5;
                    i4 = runCount;
                    float measureRun = measureRun(i6, i7, i7, isRunRtl, fontMetricsInt, null, fArr, i8, 0.0f, null, calculateRunFlag);
                    if (!z4) {
                        measureRun = -measureRun;
                    }
                    float f5 = f3 + measureRun;
                    if (!z4) {
                        f3 = f5;
                    }
                    int min2 = java.lang.Math.min(i, this.mLen);
                    int i9 = i8;
                    while (i9 <= min2) {
                        if (i9 >= min2) {
                            f = 0.0f;
                        } else {
                            f = z ? -fArr[i9] : fArr[i9];
                        }
                        int i10 = i8;
                        if (i9 == i10 && zArr[i9]) {
                            fArr[i9] = f4;
                        } else if (i9 != min2 || zArr[i9]) {
                            fArr[i9] = f3;
                        }
                        f3 += f;
                        i9++;
                        i8 = i10;
                    }
                    if (i != i2) {
                        if (!zArr[i]) {
                            fArr[i] = f5;
                        }
                        f5 = nextTab(f5 * this.mDir) * this.mDir;
                        int i11 = i + 1;
                        if (zArr[i11]) {
                            fArr[i11] = f5;
                        }
                    }
                    f3 = f5;
                    i6 = i + 1;
                } else {
                    i = i7;
                    z = isRunRtl;
                    i2 = min;
                    i3 = i5;
                    i4 = runCount;
                }
                i7 = i + 1;
                min = i2;
                i5 = i3;
                runCount = i4;
                isRunRtl = z;
                z2 = true;
                z3 = false;
            }
            i5++;
            f2 = f3;
            z2 = true;
            z3 = false;
        }
        if (!zArr[this.mLen]) {
            fArr[this.mLen] = f2;
        }
        return fArr;
    }

    private float drawRun(android.graphics.Canvas canvas, int i, int i2, boolean z, float f, int i3, int i4, int i5, boolean z2, int i6) {
        if ((this.mDir == 1) == z) {
            float f2 = -measureRun(i, i2, i2, z, null, null, null, 0, 0.0f, null, i6);
            handleRun(i, i2, i2, z, canvas, null, f + f2, i3, i4, i5, null, null, false, null, 0, null, i6);
            return f2;
        }
        return handleRun(i, i2, i2, z, canvas, null, f, i3, i4, i5, null, null, z2, null, 0, null, i6);
    }

    private float measureRun(int i, int i2, int i3, boolean z, android.graphics.Paint.FontMetricsInt fontMetricsInt, android.graphics.RectF rectF, float[] fArr, int i4, float f, android.text.TextLine.LineInfo lineInfo, int i5) {
        if (rectF != null) {
            if ((this.mDir == 1) == z) {
                return handleRun(i, i2, i3, z, null, null, f + (-measureRun(i, i2, i3, z, null, null, null, 0, 0.0f, null, i5)), 0, 0, 0, fontMetricsInt, rectF, true, fArr, i4, lineInfo, i5);
            }
        }
        return handleRun(i, i2, i3, z, null, null, f, 0, 0, 0, fontMetricsInt, rectF, true, fArr, i4, lineInfo, i5);
    }

    private float shapeRun(android.text.TextShaper.GlyphsConsumer glyphsConsumer, int i, int i2, boolean z, float f, boolean z2, int i3) {
        if ((this.mDir == 1) == z) {
            float f2 = -measureRun(i, i2, i2, z, null, null, null, 0, 0.0f, null, i3);
            handleRun(i, i2, i2, z, null, glyphsConsumer, f + f2, 0, 0, 0, null, null, false, null, 0, null, i3);
            return f2;
        }
        return handleRun(i, i2, i2, z, null, glyphsConsumer, f, 0, 0, 0, null, null, z2, null, 0, null, i3);
    }

    /* JADX WARN: Code restructure failed: missing block: B:53:0x0131, code lost:
    
        if (r12 != (-1)) goto L106;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0133, code lost:
    
        if (r1 == false) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:?, code lost:
    
        return r21.mLen + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:?, code lost:
    
        return -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x013d, code lost:
    
        if (r12 > r9) goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x013f, code lost:
    
        if (r1 == false) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0142, code lost:
    
        r9 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0144, code lost:
    
        return r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:?, code lost:
    
        return r12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    int getOffsetToLeftRightOf(int i, boolean z) {
        int i2;
        int i3;
        int i4;
        boolean z2;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9 = this.mLen;
        int i10 = -1;
        boolean z3 = this.mDir == -1;
        int[] iArr = this.mDirections.mDirections;
        if (i == 0) {
            i5 = 0;
            i6 = -2;
        } else if (i == i9) {
            i6 = iArr.length;
            i5 = 0;
        } else {
            int i11 = i9;
            int i12 = 0;
            int i13 = 0;
            while (true) {
                if (i12 >= iArr.length) {
                    i2 = i12;
                    i3 = i13;
                    i4 = i11;
                    z2 = false;
                    i5 = 0;
                    break;
                }
                i13 = iArr[i12] + 0;
                if (i >= i13) {
                    int i14 = i12 + 1;
                    int i15 = (iArr[i14] & 67108863) + i13;
                    if (i15 > i9) {
                        i15 = i9;
                    }
                    if (i >= i15) {
                        i11 = i15;
                    } else {
                        int i16 = (iArr[i14] >>> 26) & 63;
                        if (i != i13) {
                            i2 = i12;
                            i3 = i13;
                            i5 = i16;
                            i4 = i15;
                            z2 = false;
                        } else {
                            int i17 = i - 1;
                            int i18 = 0;
                            while (true) {
                                if (i18 >= iArr.length) {
                                    z2 = false;
                                    break;
                                }
                                int i19 = iArr[i18] + 0;
                                if (i17 >= i19) {
                                    int i20 = i18 + 1;
                                    int i21 = i19 + (iArr[i20] & 67108863);
                                    if (i21 > i9) {
                                        i21 = i9;
                                    }
                                    if (i17 < i21 && (i7 = (iArr[i20] >>> 26) & 63) < i16) {
                                        i12 = i18;
                                        i15 = i21;
                                        i16 = i7;
                                        i13 = i19;
                                        z2 = true;
                                        break;
                                    }
                                }
                                i18 += 2;
                            }
                            i2 = i12;
                            i3 = i13;
                            i5 = i16;
                            i4 = i15;
                        }
                    }
                }
                i12 += 2;
            }
            if (i2 != iArr.length) {
                boolean z4 = (i5 & 1) != 0;
                boolean z5 = z == z4;
                if (i != (z5 ? i4 : i3) || z5 != z2) {
                    boolean z6 = z5;
                    int offsetBeforeAfter = getOffsetBeforeAfter(i2, i3, i4, z4, i, z6);
                    if (z6) {
                        i3 = i4;
                    }
                    if (offsetBeforeAfter == i3) {
                        i10 = offsetBeforeAfter;
                        i6 = i2;
                    } else {
                        return offsetBeforeAfter;
                    }
                }
            }
            i6 = i2;
            i10 = -1;
        }
        while (true) {
            boolean z7 = z == z3;
            int i22 = i6 + (z7 ? 2 : -2);
            if (i22 < 0 || i22 >= iArr.length) {
                break;
            }
            int i23 = 0 + iArr[i22];
            int i24 = i22 + 1;
            int i25 = i23 + (iArr[i24] & 67108863);
            if (i25 <= i9) {
                i8 = i25;
            } else {
                i8 = i9;
            }
            int i26 = (iArr[i24] >>> 26) & 63;
            boolean z8 = (i26 & 1) != 0;
            boolean z9 = z == z8;
            if (i10 == -1) {
                int offsetBeforeAfter2 = getOffsetBeforeAfter(i22, i23, i8, z8, z9 ? i23 : i8, z9);
                if (offsetBeforeAfter2 != (z9 ? i8 : i23)) {
                    return offsetBeforeAfter2;
                }
                i5 = i26;
                i10 = offsetBeforeAfter2;
                i6 = i22;
            } else {
                if (i26 < i5) {
                    return z9 ? i23 : i8;
                }
                return i10;
            }
        }
    }

    private int getOffsetBeforeAfter(int i, int i2, int i3, boolean z, int i4, boolean z2) {
        int i5;
        if (i >= 0) {
            if (i4 != (z2 ? this.mLen : 0)) {
                android.text.TextPaint textPaint = this.mWorkPaint;
                textPaint.set(this.mPaint);
                if (this.mIsJustifying) {
                    textPaint.setWordSpacing(this.mAddedWordSpacingInPx);
                    textPaint.setLetterSpacing(this.mAddedLetterSpacingInPx / textPaint.getTextSize());
                }
                if (this.mSpanned == null || i2 == i3) {
                    i5 = i2;
                } else {
                    int i6 = z2 ? i4 + 1 : i4;
                    int i7 = this.mStart + i3;
                    while (true) {
                        i3 = this.mSpanned.nextSpanTransition(this.mStart + i2, i7, android.text.style.MetricAffectingSpan.class) - this.mStart;
                        if (i3 >= i6) {
                            break;
                        }
                        i2 = i3;
                    }
                    android.text.style.MetricAffectingSpan[] metricAffectingSpanArr = (android.text.style.MetricAffectingSpan[]) android.text.TextUtils.removeEmptySpans((android.text.style.MetricAffectingSpan[]) this.mSpanned.getSpans(this.mStart + i2, this.mStart + i3, android.text.style.MetricAffectingSpan.class), this.mSpanned, android.text.style.MetricAffectingSpan.class);
                    if (metricAffectingSpanArr.length > 0) {
                        android.text.style.ReplacementSpan replacementSpan = null;
                        for (android.text.style.MetricAffectingSpan metricAffectingSpan : metricAffectingSpanArr) {
                            if (metricAffectingSpan instanceof android.text.style.ReplacementSpan) {
                                replacementSpan = (android.text.style.ReplacementSpan) metricAffectingSpan;
                            } else {
                                metricAffectingSpan.updateMeasureState(textPaint);
                            }
                        }
                        if (replacementSpan != null) {
                            return z2 ? i3 : i2;
                        }
                    }
                    i5 = i2;
                }
                int i8 = z2 ? 0 : 2;
                if (this.mCharsValid) {
                    return textPaint.getTextRunCursor(this.mChars, i5, i3 - i5, z, i4, i8);
                }
                return textPaint.getTextRunCursor(this.mText, i5 + this.mStart, this.mStart + i3, z, this.mStart + i4, i8) - this.mStart;
            }
        }
        if (z2) {
            return android.text.TextUtils.getOffsetAfter(this.mText, i4 + this.mStart) - this.mStart;
        }
        return android.text.TextUtils.getOffsetBefore(this.mText, i4 + this.mStart) - this.mStart;
    }

    private static void expandMetricsFromPaint(android.graphics.Paint.FontMetricsInt fontMetricsInt, android.text.TextPaint textPaint) {
        int i = fontMetricsInt.top;
        int i2 = fontMetricsInt.ascent;
        int i3 = fontMetricsInt.descent;
        int i4 = fontMetricsInt.bottom;
        int i5 = fontMetricsInt.leading;
        textPaint.getFontMetricsInt(fontMetricsInt);
        updateMetrics(fontMetricsInt, i, i2, i3, i4, i5);
    }

    private void expandMetricsFromPaint(android.text.TextPaint textPaint, int i, int i2, int i3, int i4, boolean z, android.graphics.Paint.FontMetricsInt fontMetricsInt) {
        int i5 = fontMetricsInt.top;
        int i6 = fontMetricsInt.ascent;
        int i7 = fontMetricsInt.descent;
        int i8 = fontMetricsInt.bottom;
        int i9 = fontMetricsInt.leading;
        int i10 = i2 - i;
        int i11 = i4 - i3;
        if (this.mCharsValid) {
            textPaint.getFontMetricsInt(this.mChars, i, i10, i3, i11, z, fontMetricsInt);
        } else if (this.mComputed == null) {
            textPaint.getFontMetricsInt(this.mText, this.mStart + i, i10, this.mStart + i3, i11, z, fontMetricsInt);
        } else {
            this.mComputed.getFontMetricsInt(this.mStart + i, this.mStart + i2, fontMetricsInt);
        }
        updateMetrics(fontMetricsInt, i5, i6, i7, i8, i9);
    }

    static void updateMetrics(android.graphics.Paint.FontMetricsInt fontMetricsInt, int i, int i2, int i3, int i4, int i5) {
        fontMetricsInt.top = java.lang.Math.min(fontMetricsInt.top, i);
        fontMetricsInt.ascent = java.lang.Math.min(fontMetricsInt.ascent, i2);
        fontMetricsInt.descent = java.lang.Math.max(fontMetricsInt.descent, i3);
        fontMetricsInt.bottom = java.lang.Math.max(fontMetricsInt.bottom, i4);
        fontMetricsInt.leading = java.lang.Math.max(fontMetricsInt.leading, i5);
    }

    private static void drawStroke(android.text.TextPaint textPaint, android.graphics.Canvas canvas, int i, float f, float f2, float f3, float f4, float f5) {
        float f6 = f5 + textPaint.baselineShift + f;
        int color = textPaint.getColor();
        android.graphics.Paint.Style style = textPaint.getStyle();
        boolean isAntiAlias = textPaint.isAntiAlias();
        textPaint.setStyle(android.graphics.Paint.Style.FILL);
        textPaint.setAntiAlias(true);
        textPaint.setColor(i);
        canvas.drawRect(f3, f6, f4, f6 + f2, textPaint);
        textPaint.setStyle(style);
        textPaint.setColor(color);
        textPaint.setAntiAlias(isAntiAlias);
    }

    private float getRunAdvance(android.text.TextPaint textPaint, int i, int i2, int i3, int i4, boolean z, int i5, float[] fArr, int i6, android.graphics.RectF rectF, android.text.TextLine.LineInfo lineInfo) {
        if (lineInfo == null) {
            this.mRunInfo = null;
        } else {
            if (this.mRunInfo == null) {
                this.mRunInfo = new android.graphics.Paint.RunInfo();
            }
            this.mRunInfo.setClusterCount(0);
        }
        if (this.mCharsValid) {
            float runCharacterAdvance = textPaint.getRunCharacterAdvance(this.mChars, i, i2, i3, i4, z, i5, fArr, i6, rectF, this.mRunInfo);
            if (lineInfo != null) {
                lineInfo.setClusterCount(lineInfo.getClusterCount() + this.mRunInfo.getClusterCount());
            }
            return runCharacterAdvance;
        }
        int i7 = this.mStart;
        if (this.mComputed == null || fArr != null || lineInfo != null) {
            float runCharacterAdvance2 = textPaint.getRunCharacterAdvance(this.mText, i7 + i, i7 + i2, i7 + i3, i7 + i4, z, i7 + i5, fArr, i6, rectF, this.mRunInfo);
            if (lineInfo != null) {
                lineInfo.setClusterCount(lineInfo.getClusterCount() + this.mRunInfo.getClusterCount());
            }
            return runCharacterAdvance2;
        }
        if (rectF != null) {
            if (this.mTmpRectForPrecompute == null) {
                this.mTmpRectForPrecompute = new android.graphics.Rect();
            }
            this.mComputed.getBounds(i + i7, i2 + i7, this.mTmpRectForPrecompute);
            rectF.set(this.mTmpRectForPrecompute);
        }
        return this.mComputed.getWidth(i + i7, i2 + i7);
    }

    private float handleText(android.text.TextPaint textPaint, int i, int i2, int i3, int i4, boolean z, android.graphics.Canvas canvas, android.text.TextShaper.GlyphsConsumer glyphsConsumer, float f, int i5, int i6, int i7, android.graphics.Paint.FontMetricsInt fontMetricsInt, android.graphics.RectF rectF, boolean z2, int i8, java.util.ArrayList<android.text.TextLine.DecorationInfo> arrayList, float[] fArr, int i9, android.text.TextLine.LineInfo lineInfo, int i10) {
        android.graphics.Paint.FontMetricsInt fontMetricsInt2;
        int i11;
        android.graphics.Paint.FontMetricsInt fontMetricsInt3;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6 = f;
        if (this.mIsJustifying) {
            textPaint.setWordSpacing(this.mAddedWordSpacingInPx);
            textPaint.setLetterSpacing(this.mAddedLetterSpacingInPx / textPaint.getTextSize());
        }
        if (rectF != null && fontMetricsInt == null) {
            fontMetricsInt2 = new android.graphics.Paint.FontMetricsInt();
        } else {
            fontMetricsInt2 = fontMetricsInt;
        }
        if (fontMetricsInt2 != null) {
            expandMetricsFromPaint(fontMetricsInt2, textPaint);
        }
        if (i2 == i) {
            return 0.0f;
        }
        if ((i10 & 8192) == 8192) {
            textPaint.setFlags(textPaint.getFlags() | 8192);
        } else {
            textPaint.setFlags(textPaint.getFlags() & (-8193));
        }
        if ((i10 & 16384) == 16384) {
            textPaint.setFlags(textPaint.getFlags() | 16384);
        } else {
            textPaint.setFlags(textPaint.getFlags() & (-16385));
        }
        int size = arrayList == null ? 0 : arrayList.size();
        if (z2 || ((canvas != null || glyphsConsumer != null) && (textPaint.bgColor != 0 || size != 0 || z))) {
            if (rectF != null && this.mTmpRectForPaintAPI == null) {
                this.mTmpRectForPaintAPI = new android.graphics.RectF();
            }
            i11 = size;
            fontMetricsInt3 = fontMetricsInt2;
            float runAdvance = getRunAdvance(textPaint, i, i2, i3, i4, z, i8, fArr, i9, rectF == null ? null : this.mTmpRectForPaintAPI, lineInfo);
            if (rectF != null) {
                if (z) {
                    this.mTmpRectForPaintAPI.offset(f6 - runAdvance, 0.0f);
                } else {
                    this.mTmpRectForPaintAPI.offset(f6, 0.0f);
                }
                rectF.union(this.mTmpRectForPaintAPI);
            }
            f2 = runAdvance;
        } else {
            i11 = size;
            f2 = 0.0f;
            fontMetricsInt3 = fontMetricsInt2;
        }
        if (z) {
            f3 = f6;
            f6 -= f2;
        } else {
            f3 = f6 + f2;
        }
        if (glyphsConsumer != null) {
            shapeTextRun(glyphsConsumer, textPaint, i, i2, i3, i4, z, f6);
        }
        if (this.mUseFallbackExtent && fontMetricsInt3 != null) {
            expandMetricsFromPaint(textPaint, i, i2, i3, i4, z, fontMetricsInt3);
        }
        if (canvas != null) {
            if (textPaint.bgColor != 0) {
                int color = textPaint.getColor();
                android.graphics.Paint.Style style = textPaint.getStyle();
                textPaint.setColor(textPaint.bgColor);
                textPaint.setStyle(android.graphics.Paint.Style.FILL);
                canvas.drawRect(f6, i5, f3, i7, textPaint);
                textPaint.setStyle(style);
                textPaint.setColor(color);
            }
            int i12 = i6;
            drawTextRun(canvas, textPaint, i, i2, i3, i4, z, f6, i12 + textPaint.baselineShift);
            if (i11 != 0) {
                int i13 = 0;
                while (true) {
                    int i14 = i11;
                    if (i13 >= i14) {
                        break;
                    }
                    android.text.TextLine.DecorationInfo decorationInfo = arrayList.get(i13);
                    int max = java.lang.Math.max(decorationInfo.start, i);
                    int min = java.lang.Math.min(decorationInfo.end, i8);
                    int i15 = i13;
                    int i16 = i12;
                    float runAdvance2 = getRunAdvance(textPaint, i, i2, i3, i4, z, max, null, 0, null, null);
                    float runAdvance3 = getRunAdvance(textPaint, i, i2, i3, i4, z, min, null, 0, null, null);
                    if (z) {
                        float f7 = f3 - runAdvance2;
                        f4 = f3 - runAdvance3;
                        f5 = f7;
                    } else {
                        f4 = f6 + runAdvance2;
                        f5 = runAdvance3 + f6;
                    }
                    if (decorationInfo.underlineColor != 0) {
                        drawStroke(textPaint, canvas, decorationInfo.underlineColor, textPaint.getUnderlinePosition(), decorationInfo.underlineThickness, f4, f5, i16);
                    }
                    if (decorationInfo.isUnderlineText) {
                        drawStroke(textPaint, canvas, textPaint.getColor(), textPaint.getUnderlinePosition(), java.lang.Math.max(textPaint.getUnderlineThickness(), 1.0f), f4, f5, i16);
                    }
                    if (decorationInfo.isStrikeThruText) {
                        drawStroke(textPaint, canvas, textPaint.getColor(), textPaint.getStrikeThruPosition(), java.lang.Math.max(textPaint.getStrikeThruThickness(), 1.0f), f4, f5, i16);
                    }
                    i13 = i15 + 1;
                    i12 = i16;
                    i11 = i14;
                }
            }
        }
        return z ? -f2 : f2;
    }

    private float handleReplacement(android.text.style.ReplacementSpan replacementSpan, android.text.TextPaint textPaint, int i, int i2, boolean z, android.graphics.Canvas canvas, float f, int i3, int i4, int i5, android.graphics.Paint.FontMetricsInt fontMetricsInt, boolean z2) {
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        float f2;
        float f3;
        float f4;
        int i11 = this.mStart + i;
        int i12 = this.mStart + i2;
        if (z2 || (canvas != null && z)) {
            boolean z3 = fontMetricsInt != null;
            if (!z3) {
                i6 = 0;
                i7 = 0;
                i8 = 0;
                i9 = 0;
                i10 = 0;
            } else {
                int i13 = fontMetricsInt.top;
                i6 = i13;
                i7 = fontMetricsInt.ascent;
                i8 = fontMetricsInt.descent;
                i9 = fontMetricsInt.bottom;
                i10 = fontMetricsInt.leading;
            }
            float size = replacementSpan.getSize(textPaint, this.mText, i11, i12, fontMetricsInt);
            if (!z3) {
                f2 = size;
            } else {
                f2 = size;
                updateMetrics(fontMetricsInt, i6, i7, i8, i9, i10);
            }
            f3 = f2;
        } else {
            f3 = 0.0f;
        }
        if (canvas != null) {
            if (!z) {
                f4 = f;
            } else {
                f4 = f - f3;
            }
            replacementSpan.draw(canvas, this.mText, i11, i12, f4, i3, i4, i5, textPaint);
        }
        return z ? -f3 : f3;
    }

    private int adjustStartHyphenEdit(int i, int i2) {
        if (i > 0) {
            return 0;
        }
        return i2;
    }

    private int adjustEndHyphenEdit(int i, int i2) {
        if (i < this.mLen) {
            return 0;
        }
        return i2;
    }

    private static final class DecorationInfo {
        public int end;
        public boolean isStrikeThruText;
        public boolean isUnderlineText;
        public int start;
        public int underlineColor;
        public float underlineThickness;

        private DecorationInfo() {
            this.start = -1;
            this.end = -1;
        }

        public boolean hasDecoration() {
            return this.isStrikeThruText || this.isUnderlineText || this.underlineColor != 0;
        }

        public android.text.TextLine.DecorationInfo copyInfo() {
            android.text.TextLine.DecorationInfo decorationInfo = new android.text.TextLine.DecorationInfo();
            decorationInfo.isStrikeThruText = this.isStrikeThruText;
            decorationInfo.isUnderlineText = this.isUnderlineText;
            decorationInfo.underlineColor = this.underlineColor;
            decorationInfo.underlineThickness = this.underlineThickness;
            return decorationInfo;
        }
    }

    private void extractDecorationInfo(android.text.TextPaint textPaint, android.text.TextLine.DecorationInfo decorationInfo) {
        decorationInfo.isStrikeThruText = textPaint.isStrikeThruText();
        if (decorationInfo.isStrikeThruText) {
            textPaint.setStrikeThruText(false);
        }
        decorationInfo.isUnderlineText = textPaint.isUnderlineText();
        if (decorationInfo.isUnderlineText) {
            textPaint.setUnderlineText(false);
        }
        decorationInfo.underlineColor = textPaint.underlineColor;
        decorationInfo.underlineThickness = textPaint.underlineThickness;
        textPaint.setUnderlineText(0, 0.0f);
    }

    private float handleRun(int i, int i2, int i3, boolean z, android.graphics.Canvas canvas, android.text.TextShaper.GlyphsConsumer glyphsConsumer, float f, int i4, int i5, int i6, android.graphics.Paint.FontMetricsInt fontMetricsInt, android.graphics.RectF rectF, boolean z2, float[] fArr, int i7, android.text.TextLine.LineInfo lineInfo, int i8) {
        boolean z3;
        float f2;
        int i9;
        boolean z4;
        int i10;
        int i11;
        android.text.TextLine.DecorationInfo decorationInfo;
        android.text.TextPaint textPaint;
        android.text.TextPaint textPaint2;
        int i12;
        int i13;
        int i14;
        boolean z5;
        int i15;
        android.graphics.Paint.FontMetricsInt fontMetricsInt2;
        android.text.TextLine textLine = this;
        int i16 = i;
        int i17 = i3;
        if (i2 < i16 || i2 > i17) {
            throw new java.lang.IndexOutOfBoundsException("measureLimit (" + i2 + ") is out of start (" + i + ") and limit (" + i3 + ") bounds");
        }
        if (fArr != null && fArr.length - i7 < i2 - i16) {
            throw new java.lang.IndexOutOfBoundsException("advances doesn't have enough space to receive the result");
        }
        float f3 = 0.0f;
        if (i16 == i2) {
            android.text.TextPaint textPaint3 = textLine.mWorkPaint;
            textPaint3.set(textLine.mPaint);
            if (fontMetricsInt != null) {
                expandMetricsFromPaint(fontMetricsInt, textPaint3);
            }
            if (rectF != null) {
                if (fontMetricsInt != null) {
                    fontMetricsInt2 = fontMetricsInt;
                } else {
                    fontMetricsInt2 = new android.graphics.Paint.FontMetricsInt();
                    expandMetricsFromPaint(fontMetricsInt2, textPaint3);
                }
                rectF.union(0.0f, fontMetricsInt2.top, 0.0f, fontMetricsInt2.bottom);
            }
            return 0.0f;
        }
        if (textLine.mSpanned == null) {
            z3 = false;
        } else {
            textLine.mMetricAffectingSpanSpanSet.init(textLine.mSpanned, textLine.mStart + i16, textLine.mStart + i17);
            textLine.mCharacterStyleSpanSet.init(textLine.mSpanned, textLine.mStart + i16, textLine.mStart + i17);
            z3 = (textLine.mMetricAffectingSpanSpanSet.numberOfSpans == 0 && textLine.mCharacterStyleSpanSet.numberOfSpans == 0) ? false : true;
        }
        if (!z3) {
            android.text.TextPaint textPaint4 = textLine.mWorkPaint;
            textPaint4.set(textLine.mPaint);
            textPaint4.setStartHyphenEdit(textLine.adjustStartHyphenEdit(i16, textPaint4.getStartHyphenEdit()));
            textPaint4.setEndHyphenEdit(textLine.adjustEndHyphenEdit(i17, textPaint4.getEndHyphenEdit()));
            return handleText(textPaint4, i, i3, i, i3, z, canvas, glyphsConsumer, f, i4, i5, i6, fontMetricsInt, rectF, z2, i2, null, fArr, i7, lineInfo, i8);
        }
        float f4 = f;
        int i18 = i16;
        while (i18 < i2) {
            android.text.TextPaint textPaint5 = textLine.mWorkPaint;
            textPaint5.set(textLine.mPaint);
            int nextTransition = textLine.mMetricAffectingSpanSpanSet.getNextTransition(textLine.mStart + i18, textLine.mStart + i17) - textLine.mStart;
            int min = java.lang.Math.min(nextTransition, i2);
            android.text.style.ReplacementSpan replacementSpan = null;
            for (int i19 = 0; i19 < textLine.mMetricAffectingSpanSpanSet.numberOfSpans; i19++) {
                if (textLine.mMetricAffectingSpanSpanSet.spanStarts[i19] < textLine.mStart + min && textLine.mMetricAffectingSpanSpanSet.spanEnds[i19] > textLine.mStart + i18) {
                    boolean z6 = textLine.mStart + textLine.mEllipsisStart <= textLine.mMetricAffectingSpanSpanSet.spanStarts[i19] && textLine.mMetricAffectingSpanSpanSet.spanEnds[i19] <= textLine.mStart + textLine.mEllipsisEnd;
                    android.text.style.MetricAffectingSpan metricAffectingSpan = textLine.mMetricAffectingSpanSpanSet.spans[i19];
                    if (metricAffectingSpan instanceof android.text.style.ReplacementSpan) {
                        replacementSpan = !z6 ? (android.text.style.ReplacementSpan) metricAffectingSpan : null;
                    } else {
                        metricAffectingSpan.updateDrawState(textPaint5);
                    }
                }
            }
            if (replacementSpan != null) {
                f2 = f3;
                i9 = nextTransition;
                float handleReplacement = handleReplacement(replacementSpan, textPaint5, i18, min, z, canvas, f4, i4, i5, i6, fontMetricsInt, z2 || min < i2);
                f4 += handleReplacement;
                if (fArr != null) {
                    int i20 = (i7 + i18) - i16;
                    if (z) {
                        handleReplacement = -handleReplacement;
                    }
                    fArr[i20] = handleReplacement;
                    while (true) {
                        i18++;
                        if (i18 < min) {
                            fArr[(i7 + i18) - i16] = f2;
                        }
                    }
                }
            } else {
                f2 = f3;
                i9 = nextTransition;
                int i21 = min;
                android.text.TextLine textLine2 = this;
                android.text.TextPaint textPaint6 = textLine2.mActivePaint;
                textPaint6.set(textLine2.mPaint);
                android.text.TextLine.DecorationInfo decorationInfo2 = textLine2.mDecorationInfo;
                textLine2.mDecorations.clear();
                int i22 = i21;
                int i23 = i18;
                int i24 = i23;
                float f5 = f4;
                while (i24 < i21) {
                    int nextTransition2 = textLine2.mCharacterStyleSpanSet.getNextTransition(textLine2.mStart + i24, textLine2.mStart + i9) - textLine2.mStart;
                    int min2 = java.lang.Math.min(nextTransition2, i21);
                    textPaint5.set(textLine2.mPaint);
                    for (int i25 = 0; i25 < textLine2.mCharacterStyleSpanSet.numberOfSpans; i25++) {
                        if (textLine2.mCharacterStyleSpanSet.spanStarts[i25] < textLine2.mStart + min2 && textLine2.mCharacterStyleSpanSet.spanEnds[i25] > textLine2.mStart + i24) {
                            textLine2.mCharacterStyleSpanSet.spans[i25].updateDrawState(textPaint5);
                        }
                    }
                    textLine2.extractDecorationInfo(textPaint5, decorationInfo2);
                    if (i24 == i18) {
                        textPaint6.set(textPaint5);
                        i10 = nextTransition2;
                        i11 = i24;
                        decorationInfo = decorationInfo2;
                        textPaint = textPaint5;
                        textPaint2 = textPaint6;
                        i12 = i21;
                        i13 = i18;
                    } else if (equalAttributes(textPaint5, textPaint6)) {
                        i10 = nextTransition2;
                        i11 = i24;
                        decorationInfo = decorationInfo2;
                        textPaint = textPaint5;
                        textPaint2 = textPaint6;
                        i12 = i21;
                        i13 = i18;
                    } else {
                        i10 = nextTransition2;
                        int resolveRunFlagForSubSequence = resolveRunFlagForSubSequence(i8, z, i, i2, i23, i22);
                        textPaint6.setStartHyphenEdit(textLine2.adjustStartHyphenEdit(i23, textLine2.mPaint.getStartHyphenEdit()));
                        textPaint6.setEndHyphenEdit(textLine2.adjustEndHyphenEdit(i22, textLine2.mPaint.getEndHyphenEdit()));
                        if (z2) {
                            i14 = i2;
                        } else {
                            i14 = i2;
                            if (i22 >= i14) {
                                z5 = false;
                                i11 = i24;
                                decorationInfo = decorationInfo2;
                                i12 = i21;
                                i13 = i18;
                                f5 += handleText(textPaint6, i23, i22, i18, i9, z, canvas, glyphsConsumer, f5, i4, i5, i6, fontMetricsInt, rectF, z5, java.lang.Math.min(i22, i21), textLine2.mDecorations, fArr, (i7 + i23) - i16, lineInfo, resolveRunFlagForSubSequence);
                                textPaint = textPaint5;
                                textPaint2 = textPaint6;
                                textPaint2.set(textPaint);
                                textLine2 = this;
                                textLine2.mDecorations.clear();
                                i23 = i11;
                            }
                        }
                        z5 = true;
                        i11 = i24;
                        decorationInfo = decorationInfo2;
                        i12 = i21;
                        i13 = i18;
                        f5 += handleText(textPaint6, i23, i22, i18, i9, z, canvas, glyphsConsumer, f5, i4, i5, i6, fontMetricsInt, rectF, z5, java.lang.Math.min(i22, i21), textLine2.mDecorations, fArr, (i7 + i23) - i16, lineInfo, resolveRunFlagForSubSequence);
                        textPaint = textPaint5;
                        textPaint2 = textPaint6;
                        textPaint2.set(textPaint);
                        textLine2 = this;
                        textLine2.mDecorations.clear();
                        i23 = i11;
                    }
                    if (!decorationInfo.hasDecoration()) {
                        i15 = i10;
                    } else {
                        android.text.TextLine.DecorationInfo copyInfo = decorationInfo.copyInfo();
                        copyInfo.start = i11;
                        i15 = i10;
                        copyInfo.end = i15;
                        textLine2.mDecorations.add(copyInfo);
                    }
                    i16 = i;
                    textPaint5 = textPaint;
                    i22 = i15;
                    textPaint6 = textPaint2;
                    decorationInfo2 = decorationInfo;
                    i21 = i12;
                    i18 = i13;
                    i24 = i22;
                }
                android.text.TextPaint textPaint7 = textPaint6;
                int i26 = i21;
                int i27 = i18;
                int resolveRunFlagForSubSequence2 = resolveRunFlagForSubSequence(i8, z, i, i2, i23, i22);
                textPaint7.setStartHyphenEdit(textLine2.adjustStartHyphenEdit(i23, textLine2.mPaint.getStartHyphenEdit()));
                textPaint7.setEndHyphenEdit(textLine2.adjustEndHyphenEdit(i22, textLine2.mPaint.getEndHyphenEdit()));
                if (!z2 && i22 >= i2) {
                    z4 = false;
                    f4 = f5 + handleText(textPaint7, i23, i22, i27, i9, z, canvas, glyphsConsumer, f5, i4, i5, i6, fontMetricsInt, rectF, z4, java.lang.Math.min(i22, i26), textLine2.mDecorations, fArr, (i7 + i23) - i, lineInfo, resolveRunFlagForSubSequence2);
                }
                z4 = true;
                f4 = f5 + handleText(textPaint7, i23, i22, i27, i9, z, canvas, glyphsConsumer, f5, i4, i5, i6, fontMetricsInt, rectF, z4, java.lang.Math.min(i22, i26), textLine2.mDecorations, fArr, (i7 + i23) - i, lineInfo, resolveRunFlagForSubSequence2);
            }
            textLine = this;
            i16 = i;
            i17 = i3;
            f3 = f2;
            i18 = i9;
        }
        return f4 - f;
    }

    private void drawTextRun(android.graphics.Canvas canvas, android.text.TextPaint textPaint, int i, int i2, int i3, int i4, boolean z, float f, int i5) {
        if (this.mCharsValid) {
            canvas.drawTextRun(this.mChars, i, i2 - i, i3, i4 - i3, f, i5, z, textPaint);
        } else {
            int i6 = this.mStart;
            canvas.drawTextRun(this.mText, i6 + i, i6 + i2, i6 + i3, i6 + i4, f, i5, z, textPaint);
        }
    }

    private void shapeTextRun(android.text.TextShaper.GlyphsConsumer glyphsConsumer, android.text.TextPaint textPaint, int i, int i2, int i3, int i4, boolean z, float f) {
        android.graphics.text.PositionedGlyphs shapeTextRun;
        int i5 = i2 - i;
        int i6 = i4 - i3;
        if (!this.mCharsValid) {
            shapeTextRun = android.graphics.text.TextRunShaper.shapeTextRun(this.mText, this.mStart + i, i5, this.mStart + i3, i6, f, 0.0f, z, textPaint);
        } else {
            shapeTextRun = android.graphics.text.TextRunShaper.shapeTextRun(this.mChars, i, i5, i3, i6, f, 0.0f, z, textPaint);
        }
        glyphsConsumer.accept(i, i5, shapeTextRun, textPaint);
    }

    float nextTab(float f) {
        if (this.mTabs != null) {
            return this.mTabs.nextTab(f);
        }
        return android.text.Layout.TabStops.nextDefaultStop(f, 20.0f);
    }

    private boolean isStretchableWhitespace(int i) {
        return i == 32;
    }

    private int countStretchableSpaces(int i, int i2) {
        int i3 = 0;
        while (i < i2) {
            if (isStretchableWhitespace(this.mCharsValid ? this.mChars[i] : this.mText.charAt(this.mStart + i))) {
                i3++;
            }
            i++;
        }
        return i3;
    }

    public static boolean isLineEndSpace(char c) {
        return c == ' ' || c == '\t' || c == 5760 || (8192 <= c && c <= 8202 && c != 8199) || c == 8287 || c == 12288;
    }

    private static boolean equalAttributes(android.text.TextPaint textPaint, android.text.TextPaint textPaint2) {
        return textPaint.getColorFilter() == textPaint2.getColorFilter() && textPaint.getMaskFilter() == textPaint2.getMaskFilter() && textPaint.getShader() == textPaint2.getShader() && textPaint.getTypeface() == textPaint2.getTypeface() && textPaint.getXfermode() == textPaint2.getXfermode() && textPaint.getTextLocales().equals(textPaint2.getTextLocales()) && android.text.TextUtils.equals(textPaint.getFontFeatureSettings(), textPaint2.getFontFeatureSettings()) && android.text.TextUtils.equals(textPaint.getFontVariationSettings(), textPaint2.getFontVariationSettings()) && textPaint.getShadowLayerRadius() == textPaint2.getShadowLayerRadius() && textPaint.getShadowLayerDx() == textPaint2.getShadowLayerDx() && textPaint.getShadowLayerDy() == textPaint2.getShadowLayerDy() && textPaint.getShadowLayerColor() == textPaint2.getShadowLayerColor() && textPaint.getFlags() == textPaint2.getFlags() && textPaint.getHinting() == textPaint2.getHinting() && textPaint.getStyle() == textPaint2.getStyle() && textPaint.getColor() == textPaint2.getColor() && textPaint.getStrokeWidth() == textPaint2.getStrokeWidth() && textPaint.getStrokeMiter() == textPaint2.getStrokeMiter() && textPaint.getStrokeCap() == textPaint2.getStrokeCap() && textPaint.getStrokeJoin() == textPaint2.getStrokeJoin() && textPaint.getTextAlign() == textPaint2.getTextAlign() && textPaint.isElegantTextHeight() == textPaint2.isElegantTextHeight() && textPaint.getTextSize() == textPaint2.getTextSize() && textPaint.getTextScaleX() == textPaint2.getTextScaleX() && textPaint.getTextSkewX() == textPaint2.getTextSkewX() && textPaint.getLetterSpacing() == textPaint2.getLetterSpacing() && textPaint.getWordSpacing() == textPaint2.getWordSpacing() && textPaint.getStartHyphenEdit() == textPaint2.getStartHyphenEdit() && textPaint.getEndHyphenEdit() == textPaint2.getEndHyphenEdit() && textPaint.bgColor == textPaint2.bgColor && textPaint.baselineShift == textPaint2.baselineShift && textPaint.linkColor == textPaint2.linkColor && textPaint.drawableState == textPaint2.drawableState && textPaint.density == textPaint2.density && textPaint.underlineColor == textPaint2.underlineColor && textPaint.underlineThickness == textPaint2.underlineThickness;
    }
}
