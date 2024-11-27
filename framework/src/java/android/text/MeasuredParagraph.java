package android.text;

/* loaded from: classes3.dex */
public class MeasuredParagraph {
    private static final char OBJECT_REPLACEMENT_CHARACTER = 65532;
    private static final android.util.Pools.SynchronizedPool<android.text.MeasuredParagraph> sPool = new android.util.Pools.SynchronizedPool<>(1);
    private android.icu.text.Bidi mBidi;
    private android.graphics.Paint.FontMetricsInt mCachedFm;
    private char[] mCopiedBuffer;
    private boolean mLtrWithoutBidi;
    private android.graphics.text.MeasuredText mMeasuredText;
    private int mParaDir;
    private android.text.Spanned mSpanned;
    private int mTextLength;
    private int mTextStart;
    private float mWholeWidth;
    private android.text.AutoGrowArray.ByteArray mLevels = new android.text.AutoGrowArray.ByteArray();
    private android.text.AutoGrowArray.FloatArray mWidths = new android.text.AutoGrowArray.FloatArray();
    private android.text.AutoGrowArray.IntArray mSpanEndCache = new android.text.AutoGrowArray.IntArray(4);
    private android.text.AutoGrowArray.IntArray mFontMetrics = new android.text.AutoGrowArray.IntArray(16);
    private final android.text.TextPaint mCachedPaint = new android.text.TextPaint();
    private final android.graphics.text.LineBreakConfig.Builder mLineBreakConfigBuilder = new android.graphics.text.LineBreakConfig.Builder();

    public interface StyleRunCallback {
        void onAppendReplacementRun(android.graphics.Paint paint, int i, float f);

        void onAppendStyleRun(android.graphics.Paint paint, android.graphics.text.LineBreakConfig lineBreakConfig, int i, boolean z);
    }

    private MeasuredParagraph() {
    }

    private static android.text.MeasuredParagraph obtain() {
        android.text.MeasuredParagraph acquire = sPool.acquire();
        return acquire != null ? acquire : new android.text.MeasuredParagraph();
    }

    public void recycle() {
        release();
        sPool.release(this);
    }

    public void release() {
        reset();
        this.mLevels.clearWithReleasingLargeArray();
        this.mWidths.clearWithReleasingLargeArray();
        this.mFontMetrics.clearWithReleasingLargeArray();
        this.mSpanEndCache.clearWithReleasingLargeArray();
    }

    private void reset() {
        this.mSpanned = null;
        this.mCopiedBuffer = null;
        this.mWholeWidth = 0.0f;
        this.mLevels.clear();
        this.mWidths.clear();
        this.mFontMetrics.clear();
        this.mSpanEndCache.clear();
        this.mMeasuredText = null;
        this.mBidi = null;
    }

    public int getTextLength() {
        return this.mTextLength;
    }

    public char[] getChars() {
        return this.mCopiedBuffer;
    }

    public int getParagraphDir() {
        if (android.text.ClientFlags.icuBidiMigration()) {
            return (this.mBidi == null || (this.mBidi.getParaLevel() & 1) == 0) ? 1 : -1;
        }
        return this.mParaDir;
    }

    public android.text.Layout.Directions getDirections(int i, int i2) {
        int i3;
        if (android.text.ClientFlags.icuBidiMigration()) {
            if (this.mBidi == null) {
                return android.text.Layout.DIRS_ALL_LEFT_TO_RIGHT;
            }
            if (i == i2) {
                if ((this.mBidi.getParaLevel() & 1) == 0) {
                    return android.text.Layout.DIRS_ALL_LEFT_TO_RIGHT;
                }
                return android.text.Layout.DIRS_ALL_RIGHT_TO_LEFT;
            }
            android.icu.text.Bidi createLineBidi = this.mBidi.createLineBidi(i, i2);
            if (createLineBidi.getRunCount() == 1) {
                if (createLineBidi.getRunLevel(0) == 1) {
                    return android.text.Layout.DIRS_ALL_RIGHT_TO_LEFT;
                }
                if (createLineBidi.getRunLevel(0) == 0) {
                    return android.text.Layout.DIRS_ALL_LEFT_TO_RIGHT;
                }
                return new android.text.Layout.Directions(new int[]{0, (createLineBidi.getRunLevel(0) << 26) | (i2 - i)});
            }
            byte[] bArr = new byte[createLineBidi.getRunCount()];
            for (int i4 = 0; i4 < createLineBidi.getRunCount(); i4++) {
                bArr[i4] = (byte) createLineBidi.getRunLevel(i4);
            }
            int[] reorderVisual = android.icu.text.Bidi.reorderVisual(bArr);
            int[] iArr = new int[createLineBidi.getRunCount() * 2];
            for (int i5 = 0; i5 < createLineBidi.getRunCount(); i5++) {
                if ((this.mBidi.getBaseLevel() & 1) == 1) {
                    i3 = reorderVisual[(createLineBidi.getRunCount() - i5) - 1];
                } else {
                    i3 = reorderVisual[i5];
                }
                int i6 = i5 * 2;
                iArr[i6] = createLineBidi.getRunStart(i3);
                iArr[i6 + 1] = (createLineBidi.getRunLimit(i3) - iArr[i6]) | (createLineBidi.getRunLevel(i3) << 26);
            }
            return new android.text.Layout.Directions(iArr);
        }
        if (this.mLtrWithoutBidi) {
            return android.text.Layout.DIRS_ALL_LEFT_TO_RIGHT;
        }
        return android.text.AndroidBidi.directions(this.mParaDir, this.mLevels.getRawArray(), i, this.mCopiedBuffer, i, i2 - i);
    }

    public float getWholeWidth() {
        return this.mWholeWidth;
    }

    public android.text.AutoGrowArray.FloatArray getWidths() {
        return this.mWidths;
    }

    public android.text.AutoGrowArray.IntArray getSpanEndCache() {
        return this.mSpanEndCache;
    }

    public android.text.AutoGrowArray.IntArray getFontMetrics() {
        return this.mFontMetrics;
    }

    public android.graphics.text.MeasuredText getMeasuredText() {
        return this.mMeasuredText;
    }

    public float getWidth(int i, int i2) {
        if (this.mMeasuredText == null) {
            float[] rawArray = this.mWidths.getRawArray();
            float f = 0.0f;
            while (i < i2) {
                f += rawArray[i];
                i++;
            }
            return f;
        }
        return this.mMeasuredText.getWidth(i, i2);
    }

    public void getBounds(int i, int i2, android.graphics.Rect rect) {
        this.mMeasuredText.getBounds(i, i2, rect);
    }

    public void getFontMetricsInt(int i, int i2, android.graphics.Paint.FontMetricsInt fontMetricsInt) {
        this.mMeasuredText.getFontMetricsInt(i, i2, fontMetricsInt);
    }

    public float getCharWidthAt(int i) {
        return this.mMeasuredText.getCharWidthAt(i);
    }

    public static android.text.MeasuredParagraph buildForBidi(java.lang.CharSequence charSequence, int i, int i2, android.text.TextDirectionHeuristic textDirectionHeuristic, android.text.MeasuredParagraph measuredParagraph) {
        if (measuredParagraph == null) {
            measuredParagraph = obtain();
        }
        measuredParagraph.resetAndAnalyzeBidi(charSequence, i, i2, textDirectionHeuristic);
        return measuredParagraph;
    }

    public static android.text.MeasuredParagraph buildForMeasurement(android.text.TextPaint textPaint, java.lang.CharSequence charSequence, int i, int i2, android.text.TextDirectionHeuristic textDirectionHeuristic, android.text.MeasuredParagraph measuredParagraph) {
        if (measuredParagraph == null) {
            measuredParagraph = obtain();
        }
        measuredParagraph.resetAndAnalyzeBidi(charSequence, i, i2, textDirectionHeuristic);
        measuredParagraph.mWidths.resize(measuredParagraph.mTextLength);
        if (measuredParagraph.mTextLength == 0) {
            return measuredParagraph;
        }
        if (measuredParagraph.mSpanned != null) {
            while (true) {
                int i3 = i;
                if (i3 >= i2) {
                    break;
                }
                i = java.lang.Math.min(measuredParagraph.mSpanned.nextSpanTransition(i3, i2, android.text.style.MetricAffectingSpan.class), measuredParagraph.mSpanned.nextSpanTransition(i3, i2, android.text.style.LineBreakConfigSpan.class));
                android.text.MeasuredParagraph measuredParagraph2 = measuredParagraph;
                measuredParagraph2.applyMetricsAffectingSpan(textPaint, null, (android.text.style.MetricAffectingSpan[]) android.text.TextUtils.removeEmptySpans((android.text.style.MetricAffectingSpan[]) measuredParagraph.mSpanned.getSpans(i3, i, android.text.style.MetricAffectingSpan.class), measuredParagraph.mSpanned, android.text.style.MetricAffectingSpan.class), (android.text.style.LineBreakConfigSpan[]) android.text.TextUtils.removeEmptySpans((android.text.style.LineBreakConfigSpan[]) measuredParagraph.mSpanned.getSpans(i3, i, android.text.style.LineBreakConfigSpan.class), measuredParagraph.mSpanned, android.text.style.LineBreakConfigSpan.class), i3, i, null, null);
            }
        } else {
            measuredParagraph.applyMetricsAffectingSpan(textPaint, null, null, null, i, i2, null, null);
        }
        return measuredParagraph;
    }

    public static android.text.MeasuredParagraph buildForStaticLayout(android.text.TextPaint textPaint, android.graphics.text.LineBreakConfig lineBreakConfig, java.lang.CharSequence charSequence, int i, int i2, android.text.TextDirectionHeuristic textDirectionHeuristic, int i3, boolean z, boolean z2, android.text.MeasuredParagraph measuredParagraph, android.text.MeasuredParagraph measuredParagraph2) {
        return buildForStaticLayoutInternal(textPaint, lineBreakConfig, charSequence, i, i2, textDirectionHeuristic, i3, z, z2, measuredParagraph, measuredParagraph2, null);
    }

    public static android.text.MeasuredParagraph buildForStaticLayoutTest(android.text.TextPaint textPaint, android.graphics.text.LineBreakConfig lineBreakConfig, java.lang.CharSequence charSequence, int i, int i2, android.text.TextDirectionHeuristic textDirectionHeuristic, int i3, boolean z, android.text.MeasuredParagraph.StyleRunCallback styleRunCallback) {
        return buildForStaticLayoutInternal(textPaint, lineBreakConfig, charSequence, i, i2, textDirectionHeuristic, i3, z, false, null, null, styleRunCallback);
    }

    private static android.text.MeasuredParagraph buildForStaticLayoutInternal(android.text.TextPaint textPaint, android.graphics.text.LineBreakConfig lineBreakConfig, java.lang.CharSequence charSequence, int i, int i2, android.text.TextDirectionHeuristic textDirectionHeuristic, int i3, boolean z, boolean z2, android.text.MeasuredParagraph measuredParagraph, android.text.MeasuredParagraph measuredParagraph2, android.text.MeasuredParagraph.StyleRunCallback styleRunCallback) {
        android.graphics.text.MeasuredText.Builder builder;
        android.text.MeasuredParagraph measuredParagraph3;
        android.text.MeasuredParagraph obtain = measuredParagraph2 == null ? obtain() : measuredParagraph2;
        int i4 = i;
        obtain.resetAndAnalyzeBidi(charSequence, i4, i2, textDirectionHeuristic);
        if (measuredParagraph == null) {
            builder = new android.graphics.text.MeasuredText.Builder(obtain.mCopiedBuffer).setComputeHyphenation(i3).setComputeLayout(z).setComputeBounds(z2);
        } else {
            builder = new android.graphics.text.MeasuredText.Builder(measuredParagraph.mMeasuredText);
        }
        if (obtain.mTextLength == 0) {
            obtain.mMeasuredText = builder.build();
            return obtain;
        }
        if (obtain.mSpanned != null) {
            while (i4 < i2) {
                int min = java.lang.Math.min(obtain.mSpanned.nextSpanTransition(i4, i2, android.text.style.MetricAffectingSpan.class), obtain.mSpanned.nextSpanTransition(i4, i2, android.text.style.LineBreakConfigSpan.class));
                android.text.MeasuredParagraph measuredParagraph4 = obtain;
                obtain.applyMetricsAffectingSpan(textPaint, lineBreakConfig, (android.text.style.MetricAffectingSpan[]) android.text.TextUtils.removeEmptySpans((android.text.style.MetricAffectingSpan[]) obtain.mSpanned.getSpans(i4, min, android.text.style.MetricAffectingSpan.class), obtain.mSpanned, android.text.style.MetricAffectingSpan.class), (android.text.style.LineBreakConfigSpan[]) android.text.TextUtils.removeEmptySpans((android.text.style.LineBreakConfigSpan[]) obtain.mSpanned.getSpans(i4, min, android.text.style.LineBreakConfigSpan.class), obtain.mSpanned, android.text.style.LineBreakConfigSpan.class), i4, min, builder, styleRunCallback);
                measuredParagraph4.mSpanEndCache.append(min);
                i4 = min;
                obtain = measuredParagraph4;
            }
            measuredParagraph3 = obtain;
        } else {
            obtain.applyMetricsAffectingSpan(textPaint, lineBreakConfig, null, null, i, i2, builder, styleRunCallback);
            obtain.mSpanEndCache.append(i2);
            measuredParagraph3 = obtain;
        }
        measuredParagraph3.mMeasuredText = builder.build();
        return measuredParagraph3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v4, types: [int] */
    /* JADX WARN: Type inference failed for: r8v5 */
    private void resetAndAnalyzeBidi(java.lang.CharSequence charSequence, int i, int i2, android.text.TextDirectionHeuristic textDirectionHeuristic) {
        ?? isRtl;
        reset();
        this.mSpanned = charSequence instanceof android.text.Spanned ? (android.text.Spanned) charSequence : null;
        this.mTextStart = i;
        this.mTextLength = i2 - i;
        if (this.mCopiedBuffer == null || this.mCopiedBuffer.length != this.mTextLength) {
            this.mCopiedBuffer = new char[this.mTextLength];
        }
        android.text.TextUtils.getChars(charSequence, i, i2, this.mCopiedBuffer, 0);
        if (this.mSpanned != null) {
            android.text.style.ReplacementSpan[] replacementSpanArr = (android.text.style.ReplacementSpan[]) this.mSpanned.getSpans(i, i2, android.text.style.ReplacementSpan.class);
            for (int i3 = 0; i3 < replacementSpanArr.length; i3++) {
                int spanStart = this.mSpanned.getSpanStart(replacementSpanArr[i3]) - i;
                int spanEnd = this.mSpanned.getSpanEnd(replacementSpanArr[i3]) - i;
                if (spanStart < 0) {
                    spanStart = 0;
                }
                if (spanEnd > this.mTextLength) {
                    spanEnd = this.mTextLength;
                }
                java.util.Arrays.fill(this.mCopiedBuffer, spanStart, spanEnd, OBJECT_REPLACEMENT_CHARACTER);
            }
        }
        int i4 = 1;
        if (android.text.ClientFlags.icuBidiMigration()) {
            if ((textDirectionHeuristic == android.text.TextDirectionHeuristics.LTR || textDirectionHeuristic == android.text.TextDirectionHeuristics.FIRSTSTRONG_LTR || textDirectionHeuristic == android.text.TextDirectionHeuristics.ANYRTL_LTR) && android.text.TextUtils.doesNotNeedBidi(this.mCopiedBuffer, 0, this.mTextLength)) {
                this.mLevels.clear();
                this.mLtrWithoutBidi = true;
                return;
            }
            if (textDirectionHeuristic == android.text.TextDirectionHeuristics.LTR) {
                isRtl = 0;
            } else if (textDirectionHeuristic == android.text.TextDirectionHeuristics.RTL) {
                isRtl = 1;
            } else if (textDirectionHeuristic == android.text.TextDirectionHeuristics.FIRSTSTRONG_LTR) {
                isRtl = 126;
            } else if (textDirectionHeuristic == android.text.TextDirectionHeuristics.FIRSTSTRONG_RTL) {
                isRtl = 127;
            } else {
                isRtl = textDirectionHeuristic.isRtl(this.mCopiedBuffer, 0, this.mTextLength);
            }
            this.mBidi = new android.icu.text.Bidi(this.mCopiedBuffer, 0, null, 0, this.mCopiedBuffer.length, isRtl);
            this.mLevels.resize(this.mTextLength);
            byte[] rawArray = this.mLevels.getRawArray();
            for (int i5 = 0; i5 < this.mTextLength; i5++) {
                rawArray[i5] = this.mBidi.getLevelAt(i5);
            }
            this.mLtrWithoutBidi = false;
            return;
        }
        if ((textDirectionHeuristic == android.text.TextDirectionHeuristics.LTR || textDirectionHeuristic == android.text.TextDirectionHeuristics.FIRSTSTRONG_LTR || textDirectionHeuristic == android.text.TextDirectionHeuristics.ANYRTL_LTR) && android.text.TextUtils.doesNotNeedBidi(this.mCopiedBuffer, 0, this.mTextLength)) {
            this.mLevels.clear();
            this.mParaDir = 1;
            this.mLtrWithoutBidi = true;
            return;
        }
        if (textDirectionHeuristic != android.text.TextDirectionHeuristics.LTR) {
            if (textDirectionHeuristic == android.text.TextDirectionHeuristics.RTL) {
                i4 = -1;
            } else if (textDirectionHeuristic == android.text.TextDirectionHeuristics.FIRSTSTRONG_LTR) {
                i4 = 2;
            } else if (textDirectionHeuristic == android.text.TextDirectionHeuristics.FIRSTSTRONG_RTL) {
                i4 = -2;
            } else if (textDirectionHeuristic.isRtl(this.mCopiedBuffer, 0, this.mTextLength)) {
                i4 = -1;
            }
        }
        this.mLevels.resize(this.mTextLength);
        this.mParaDir = android.text.AndroidBidi.bidi(i4, this.mCopiedBuffer, this.mLevels.getRawArray());
        this.mLtrWithoutBidi = false;
    }

    private void applyReplacementRun(android.text.style.ReplacementSpan replacementSpan, int i, int i2, android.text.TextPaint textPaint, android.graphics.text.MeasuredText.Builder builder, android.text.MeasuredParagraph.StyleRunCallback styleRunCallback) {
        float size = replacementSpan.getSize(textPaint, this.mSpanned, i + this.mTextStart, i2 + this.mTextStart, this.mCachedFm);
        if (builder == null) {
            this.mWidths.set(i, size);
            int i3 = i + 1;
            if (i2 > i3) {
                java.util.Arrays.fill(this.mWidths.getRawArray(), i3, i2, 0.0f);
            }
            this.mWholeWidth += size;
        } else {
            builder.appendReplacementRun(textPaint, i2 - i, size);
        }
        if (styleRunCallback != null) {
            styleRunCallback.onAppendReplacementRun(textPaint, i2 - i, size);
        }
    }

    private void applyStyleRun(int i, int i2, android.text.TextPaint textPaint, android.graphics.text.LineBreakConfig lineBreakConfig, android.graphics.text.MeasuredText.Builder builder, android.text.MeasuredParagraph.StyleRunCallback styleRunCallback) {
        int i3;
        boolean z;
        boolean z2;
        android.text.MeasuredParagraph.StyleRunCallback styleRunCallback2;
        int i4;
        int i5;
        byte b;
        int i6;
        boolean z3;
        boolean z4;
        int i7 = i;
        int i8 = i2;
        android.text.MeasuredParagraph.StyleRunCallback styleRunCallback3 = styleRunCallback;
        boolean z5 = false;
        if (this.mLtrWithoutBidi) {
            if (builder == null) {
                int flags = textPaint.getFlags();
                textPaint.setFlags(textPaint.getFlags() | 24576);
                try {
                    int i9 = i8 - i7;
                    z4 = false;
                    this.mWholeWidth += textPaint.getTextRunAdvances(this.mCopiedBuffer, i, i9, i, i9, false, this.mWidths.getRawArray(), i);
                } finally {
                    textPaint.setFlags(flags);
                }
            } else {
                z4 = false;
                builder.appendStyleRun(textPaint, lineBreakConfig, i8 - i7, false);
            }
            boolean z6 = z4;
            if (styleRunCallback != null) {
                styleRunCallback.onAppendStyleRun(textPaint, lineBreakConfig, i8 - i7, z6);
                return;
            }
            return;
        }
        byte b2 = this.mLevels.get(i7);
        int i10 = i7 + 1;
        while (true) {
            if (i10 == i8 || this.mLevels.get(i10) != b2) {
                boolean z7 = (b2 & 1) != 0 ? true : z5;
                if (builder == null) {
                    int i11 = i10 - i7;
                    int flags2 = textPaint.getFlags();
                    textPaint.setFlags(textPaint.getFlags() | 24576);
                    try {
                        i6 = flags2;
                        z3 = z7;
                        i3 = i10;
                        z = z5;
                    } catch (java.lang.Throwable th) {
                        th = th;
                        i6 = flags2;
                    }
                    try {
                        this.mWholeWidth += textPaint.getTextRunAdvances(this.mCopiedBuffer, i7, i11, i7, i11, z3, this.mWidths.getRawArray(), i7);
                        textPaint.setFlags(i6);
                        z2 = z3;
                    } catch (java.lang.Throwable th2) {
                        th = th2;
                        textPaint.setFlags(i6);
                        throw th;
                    }
                } else {
                    i3 = i10;
                    z = z5;
                    z2 = z7;
                    builder.appendStyleRun(textPaint, lineBreakConfig, i3 - i7, z2);
                }
                styleRunCallback2 = styleRunCallback;
                i4 = i3;
                if (styleRunCallback2 != null) {
                    styleRunCallback2.onAppendStyleRun(textPaint, lineBreakConfig, i4 - i7, z2);
                }
                i5 = i2;
                if (i4 != i5) {
                    b = this.mLevels.get(i4);
                    i7 = i4;
                } else {
                    return;
                }
            } else {
                b = b2;
                i4 = i10;
                z = z5;
                i5 = i8;
                styleRunCallback2 = styleRunCallback3;
            }
            i10 = i4 + 1;
            styleRunCallback3 = styleRunCallback2;
            i8 = i5;
            b2 = b;
            z5 = z;
        }
    }

    private void applyMetricsAffectingSpan(android.text.TextPaint textPaint, android.graphics.text.LineBreakConfig lineBreakConfig, android.text.style.MetricAffectingSpan[] metricAffectingSpanArr, android.text.style.LineBreakConfigSpan[] lineBreakConfigSpanArr, int i, int i2, android.graphics.text.MeasuredText.Builder builder, android.text.MeasuredParagraph.StyleRunCallback styleRunCallback) {
        android.graphics.text.LineBreakConfig lineBreakConfig2;
        this.mCachedPaint.set(textPaint);
        this.mCachedPaint.baselineShift = 0;
        boolean z = builder != null;
        if (z && this.mCachedFm == null) {
            this.mCachedFm = new android.graphics.Paint.FontMetricsInt();
        }
        android.text.style.ReplacementSpan replacementSpan = null;
        if (metricAffectingSpanArr != null) {
            for (android.text.style.MetricAffectingSpan metricAffectingSpan : metricAffectingSpanArr) {
                if (metricAffectingSpan instanceof android.text.style.ReplacementSpan) {
                    replacementSpan = (android.text.style.ReplacementSpan) metricAffectingSpan;
                } else {
                    metricAffectingSpan.updateMeasureState(this.mCachedPaint);
                }
            }
        }
        if (lineBreakConfigSpanArr == null) {
            lineBreakConfig2 = lineBreakConfig;
        } else {
            this.mLineBreakConfigBuilder.reset(lineBreakConfig);
            for (android.text.style.LineBreakConfigSpan lineBreakConfigSpan : lineBreakConfigSpanArr) {
                this.mLineBreakConfigBuilder.merge(lineBreakConfigSpan.getLineBreakConfig());
            }
            lineBreakConfig2 = this.mLineBreakConfigBuilder.build();
        }
        int i3 = i - this.mTextStart;
        int i4 = i2 - this.mTextStart;
        if (builder != null) {
            this.mCachedPaint.getFontMetricsInt(this.mCachedFm);
        }
        if (replacementSpan != null) {
            applyReplacementRun(replacementSpan, i3, i4, this.mCachedPaint, builder, styleRunCallback);
        } else {
            applyStyleRun(i3, i4, this.mCachedPaint, lineBreakConfig2, builder, styleRunCallback);
        }
        if (z) {
            if (this.mCachedPaint.baselineShift < 0) {
                this.mCachedFm.ascent += this.mCachedPaint.baselineShift;
                this.mCachedFm.top += this.mCachedPaint.baselineShift;
            } else {
                this.mCachedFm.descent += this.mCachedPaint.baselineShift;
                this.mCachedFm.bottom += this.mCachedPaint.baselineShift;
            }
            this.mFontMetrics.append(this.mCachedFm.top);
            this.mFontMetrics.append(this.mCachedFm.bottom);
            this.mFontMetrics.append(this.mCachedFm.ascent);
            this.mFontMetrics.append(this.mCachedFm.descent);
        }
    }

    int breakText(int i, boolean z, float f) {
        float[] rawArray = this.mWidths.getRawArray();
        if (z) {
            int i2 = 0;
            while (i2 < i) {
                f -= rawArray[i2];
                if (f < 0.0f) {
                    break;
                }
                i2++;
            }
            while (i2 > 0 && this.mCopiedBuffer[i2 - 1] == ' ') {
                i2--;
            }
            return i2;
        }
        int i3 = i - 1;
        int i4 = i3;
        while (i4 >= 0) {
            f -= rawArray[i4];
            if (f < 0.0f) {
                break;
            }
            i4--;
        }
        while (i4 < i3) {
            int i5 = i4 + 1;
            if (this.mCopiedBuffer[i5] != ' ' && rawArray[i5] != 0.0f) {
                break;
            }
            i4 = i5;
        }
        return (i - i4) - 1;
    }

    float measure(int i, int i2) {
        float[] rawArray = this.mWidths.getRawArray();
        float f = 0.0f;
        while (i < i2) {
            f += rawArray[i];
            i++;
        }
        return f;
    }

    public int getMemoryUsage() {
        return this.mMeasuredText.getMemoryUsage();
    }
}
