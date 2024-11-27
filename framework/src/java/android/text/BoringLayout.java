package android.text;

/* loaded from: classes3.dex */
public class BoringLayout extends android.text.Layout implements android.text.TextUtils.EllipsizeCallback {
    int mBottom;
    private int mBottomPadding;
    int mDesc;
    private java.lang.String mDirect;
    private final android.graphics.RectF mDrawingBounds;
    private int mEllipsizedCount;
    private int mEllipsizedStart;
    private int mEllipsizedWidth;
    private float mMax;
    private android.graphics.Paint mPaint;
    private int mTopPadding;
    private boolean mUseFallbackLineSpacing;

    public static android.text.BoringLayout make(java.lang.CharSequence charSequence, android.text.TextPaint textPaint, int i, android.text.Layout.Alignment alignment, float f, float f2, android.text.BoringLayout.Metrics metrics, boolean z) {
        return new android.text.BoringLayout(charSequence, textPaint, i, alignment, f, f2, metrics, z);
    }

    public static android.text.BoringLayout make(java.lang.CharSequence charSequence, android.text.TextPaint textPaint, int i, android.text.Layout.Alignment alignment, float f, float f2, android.text.BoringLayout.Metrics metrics, boolean z, android.text.TextUtils.TruncateAt truncateAt, int i2) {
        return new android.text.BoringLayout(charSequence, textPaint, i, alignment, f, f2, metrics, z, truncateAt, i2);
    }

    public static android.text.BoringLayout make(java.lang.CharSequence charSequence, android.text.TextPaint textPaint, int i, android.text.Layout.Alignment alignment, android.text.BoringLayout.Metrics metrics, boolean z, android.text.TextUtils.TruncateAt truncateAt, int i2, boolean z2) {
        return new android.text.BoringLayout(charSequence, textPaint, i, alignment, 1.0f, 0.0f, metrics, z, truncateAt, i2, z2);
    }

    public android.text.BoringLayout replaceOrMake(java.lang.CharSequence charSequence, android.text.TextPaint textPaint, int i, android.text.Layout.Alignment alignment, float f, float f2, android.text.BoringLayout.Metrics metrics, boolean z) {
        replaceWith(charSequence, textPaint, i, alignment, f, f2);
        this.mEllipsizedWidth = i;
        this.mEllipsizedStart = 0;
        this.mEllipsizedCount = 0;
        this.mUseFallbackLineSpacing = false;
        init(charSequence, textPaint, alignment, metrics, z, true, false);
        return this;
    }

    public android.text.BoringLayout replaceOrMake(java.lang.CharSequence charSequence, android.text.TextPaint textPaint, int i, android.text.Layout.Alignment alignment, android.text.BoringLayout.Metrics metrics, boolean z, android.text.TextUtils.TruncateAt truncateAt, int i2, boolean z2) {
        return replaceOrMake(charSequence, textPaint, i, alignment, 1.0f, 0.0f, metrics, z, truncateAt, i2, z2, false, null);
    }

    public android.text.BoringLayout replaceOrMake(java.lang.CharSequence charSequence, android.text.TextPaint textPaint, int i, android.text.Layout.Alignment alignment, float f, float f2, android.text.BoringLayout.Metrics metrics, boolean z, android.text.TextUtils.TruncateAt truncateAt, int i2, boolean z2, boolean z3, android.graphics.Paint.FontMetrics fontMetrics) {
        boolean z4;
        if (truncateAt == null || truncateAt == android.text.TextUtils.TruncateAt.MARQUEE) {
            replaceWith(charSequence, textPaint, i, alignment, 1.0f, 0.0f);
            this.mEllipsizedWidth = i;
            this.mEllipsizedStart = 0;
            this.mEllipsizedCount = 0;
            z4 = true;
        } else {
            replaceWith(android.text.TextUtils.ellipsize(charSequence, textPaint, i2, truncateAt, true, this), textPaint, i, alignment, f, f2);
            this.mEllipsizedWidth = i2;
            z4 = false;
        }
        this.mUseFallbackLineSpacing = z2;
        init(getText(), textPaint, alignment, metrics, z, z4, z2);
        return this;
    }

    public android.text.BoringLayout replaceOrMake(java.lang.CharSequence charSequence, android.text.TextPaint textPaint, int i, android.text.Layout.Alignment alignment, float f, float f2, android.text.BoringLayout.Metrics metrics, boolean z, android.text.TextUtils.TruncateAt truncateAt, int i2) {
        return replaceOrMake(charSequence, textPaint, i, alignment, metrics, z, truncateAt, i2, false);
    }

    public BoringLayout(java.lang.CharSequence charSequence, android.text.TextPaint textPaint, int i, android.text.Layout.Alignment alignment, float f, float f2, android.text.BoringLayout.Metrics metrics, boolean z) {
        super(charSequence, textPaint, i, alignment, android.text.TextDirectionHeuristics.LTR, f, f2, z, false, i, null, 1, 0, 0, null, null, 0, android.graphics.text.LineBreakConfig.NONE, false, false, null);
        this.mDrawingBounds = new android.graphics.RectF();
        this.mEllipsizedWidth = i;
        this.mEllipsizedStart = 0;
        this.mEllipsizedCount = 0;
        this.mUseFallbackLineSpacing = false;
        init(charSequence, textPaint, alignment, metrics, z, true, false);
    }

    public BoringLayout(java.lang.CharSequence charSequence, android.text.TextPaint textPaint, int i, android.text.Layout.Alignment alignment, float f, float f2, android.text.BoringLayout.Metrics metrics, boolean z, android.text.TextUtils.TruncateAt truncateAt, int i2) {
        this(charSequence, textPaint, i, alignment, f, f2, metrics, z, truncateAt, i2, false);
    }

    public BoringLayout(java.lang.CharSequence charSequence, android.text.TextPaint textPaint, int i, android.text.Layout.Alignment alignment, float f, float f2, android.text.BoringLayout.Metrics metrics, boolean z, android.text.TextUtils.TruncateAt truncateAt, int i2, boolean z2) {
        this(charSequence, textPaint, i, alignment, android.text.TextDirectionHeuristics.LTR, f, f2, z, z2, i2, truncateAt, 1, 0, 0, null, null, 0, android.graphics.text.LineBreakConfig.NONE, metrics, false, false, null);
    }

    public BoringLayout(java.lang.CharSequence charSequence, android.text.TextPaint textPaint, int i, android.text.Layout.Alignment alignment, float f, float f2, boolean z, boolean z2, int i2, android.text.TextUtils.TruncateAt truncateAt, android.text.BoringLayout.Metrics metrics, boolean z3, boolean z4, android.graphics.Paint.FontMetrics fontMetrics) {
        this(charSequence, textPaint, i, alignment, android.text.TextDirectionHeuristics.LTR, f, f2, z, z2, i2, truncateAt, 1, 0, 0, null, null, 0, android.graphics.text.LineBreakConfig.NONE, metrics, z3, z4, fontMetrics);
    }

    BoringLayout(java.lang.CharSequence charSequence, android.text.TextPaint textPaint, int i, android.text.Layout.Alignment alignment, android.text.TextDirectionHeuristic textDirectionHeuristic, float f, float f2, boolean z, boolean z2, int i2, android.text.TextUtils.TruncateAt truncateAt, int i3, int i4, int i5, int[] iArr, int[] iArr2, int i6, android.graphics.text.LineBreakConfig lineBreakConfig, android.text.BoringLayout.Metrics metrics, boolean z3, boolean z4, android.graphics.Paint.FontMetrics fontMetrics) {
        super(charSequence, textPaint, i, alignment, textDirectionHeuristic, f, f2, z, z2, i2, truncateAt, i3, i4, i5, iArr, iArr2, i6, lineBreakConfig, z3, z4, fontMetrics);
        this.mDrawingBounds = new android.graphics.RectF();
        boolean z5 = false;
        if (truncateAt == null || truncateAt == android.text.TextUtils.TruncateAt.MARQUEE) {
            this.mEllipsizedWidth = i;
            this.mEllipsizedStart = 0;
            this.mEllipsizedCount = 0;
            z5 = true;
        } else {
            replaceWith(android.text.TextUtils.ellipsize(charSequence, textPaint, i2, truncateAt, true, this), textPaint, i, alignment, f, f2);
            this.mEllipsizedWidth = i2;
        }
        this.mUseFallbackLineSpacing = z2;
        init(getText(), textPaint, alignment, metrics, z, z5, z2);
    }

    void init(java.lang.CharSequence charSequence, android.text.TextPaint textPaint, android.text.Layout.Alignment alignment, android.text.BoringLayout.Metrics metrics, boolean z, boolean z2, boolean z3) {
        int i;
        if ((charSequence instanceof java.lang.String) && alignment == android.text.Layout.Alignment.ALIGN_NORMAL) {
            this.mDirect = charSequence.toString();
        } else {
            this.mDirect = null;
        }
        this.mPaint = textPaint;
        if (z) {
            i = metrics.bottom - metrics.top;
            this.mDesc = metrics.bottom;
        } else {
            i = metrics.descent - metrics.ascent;
            this.mDesc = metrics.descent;
        }
        this.mBottom = i;
        if (z2) {
            this.mMax = metrics.width;
        } else {
            android.text.TextLine obtain = android.text.TextLine.obtain();
            obtain.set(textPaint, charSequence, 0, charSequence.length(), 1, android.text.Layout.DIRS_ALL_LEFT_TO_RIGHT, false, null, this.mEllipsizedStart, this.mEllipsizedStart + this.mEllipsizedCount, z3);
            this.mMax = (int) java.lang.Math.ceil(obtain.metrics(null, null, false, null));
            android.text.TextLine.recycle(obtain);
        }
        if (z) {
            this.mTopPadding = metrics.top - metrics.ascent;
            this.mBottomPadding = metrics.bottom - metrics.descent;
        }
        this.mDrawingBounds.set(metrics.mDrawingBounds);
        this.mDrawingBounds.offset(0.0f, this.mBottom - this.mDesc);
    }

    public static android.text.BoringLayout.Metrics isBoring(java.lang.CharSequence charSequence, android.text.TextPaint textPaint) {
        return isBoring(charSequence, textPaint, android.text.TextDirectionHeuristics.FIRSTSTRONG_LTR, null);
    }

    public static android.text.BoringLayout.Metrics isBoring(java.lang.CharSequence charSequence, android.text.TextPaint textPaint, android.text.BoringLayout.Metrics metrics) {
        return isBoring(charSequence, textPaint, android.text.TextDirectionHeuristics.FIRSTSTRONG_LTR, metrics);
    }

    private static boolean hasAnyInterestingChars(java.lang.CharSequence charSequence, int i) {
        char[] obtain = android.text.TextUtils.obtain(500);
        int i2 = 0;
        while (i2 < i) {
            int i3 = i2 + 500;
            try {
                int min = java.lang.Math.min(i3, i);
                android.text.TextUtils.getChars(charSequence, i2, min, obtain, 0);
                int i4 = min - i2;
                for (int i5 = 0; i5 < i4; i5++) {
                    char c = obtain[i5];
                    if (c == '\n' || c == '\t' || android.text.TextUtils.couldAffectRtl(c)) {
                        android.text.TextUtils.recycle(obtain);
                        return true;
                    }
                }
                i2 = i3;
            } finally {
                android.text.TextUtils.recycle(obtain);
            }
        }
        return false;
    }

    public static android.text.BoringLayout.Metrics isBoring(java.lang.CharSequence charSequence, android.text.TextPaint textPaint, android.text.TextDirectionHeuristic textDirectionHeuristic, android.text.BoringLayout.Metrics metrics) {
        return isBoring(charSequence, textPaint, textDirectionHeuristic, false, metrics);
    }

    public static android.text.BoringLayout.Metrics isBoring(java.lang.CharSequence charSequence, android.text.TextPaint textPaint, android.text.TextDirectionHeuristic textDirectionHeuristic, boolean z, android.text.BoringLayout.Metrics metrics) {
        return isBoring(charSequence, textPaint, textDirectionHeuristic, z, null, metrics);
    }

    public static android.text.BoringLayout.Metrics isBoring(java.lang.CharSequence charSequence, android.text.TextPaint textPaint, android.text.TextDirectionHeuristic textDirectionHeuristic, boolean z, android.graphics.Paint.FontMetrics fontMetrics, android.text.BoringLayout.Metrics metrics) {
        android.text.BoringLayout.Metrics metrics2;
        int length = charSequence.length();
        if (hasAnyInterestingChars(charSequence, length)) {
            return null;
        }
        if (textDirectionHeuristic != null && textDirectionHeuristic.isRtl(charSequence, 0, length)) {
            return null;
        }
        if ((charSequence instanceof android.text.Spanned) && ((android.text.Spanned) charSequence).getSpans(0, length, android.text.style.ParagraphStyle.class).length > 0) {
            return null;
        }
        if (metrics == null) {
            metrics2 = new android.text.BoringLayout.Metrics();
        } else {
            metrics.reset();
            metrics2 = metrics;
        }
        if (android.text.ClientFlags.fixLineHeightForLocale() && fontMetrics != null) {
            metrics2.set(fontMetrics);
            metrics2.top = java.lang.Math.min(metrics2.top, metrics2.ascent);
            metrics2.bottom = java.lang.Math.max(metrics2.bottom, metrics2.descent);
        }
        android.text.TextLine obtain = android.text.TextLine.obtain();
        obtain.set(textPaint, charSequence, 0, length, 1, android.text.Layout.DIRS_ALL_LEFT_TO_RIGHT, false, null, 0, 0, z);
        metrics2.width = (int) java.lang.Math.ceil(obtain.metrics(metrics2, metrics2.mDrawingBounds, false, null));
        android.text.TextLine.recycle(obtain);
        return metrics2;
    }

    @Override // android.text.Layout
    public int getHeight() {
        return this.mBottom;
    }

    @Override // android.text.Layout
    public int getLineCount() {
        return 1;
    }

    @Override // android.text.Layout
    public int getLineTop(int i) {
        if (i == 0) {
            return 0;
        }
        return this.mBottom;
    }

    @Override // android.text.Layout
    public int getLineDescent(int i) {
        return this.mDesc;
    }

    @Override // android.text.Layout
    public int getLineStart(int i) {
        if (i == 0) {
            return 0;
        }
        return getText().length();
    }

    @Override // android.text.Layout
    public int getParagraphDirection(int i) {
        return 1;
    }

    @Override // android.text.Layout
    public boolean getLineContainsTab(int i) {
        return false;
    }

    @Override // android.text.Layout
    public float getLineMax(int i) {
        if (getUseBoundsForWidth()) {
            return super.getLineMax(i);
        }
        return this.mMax;
    }

    @Override // android.text.Layout
    public float getLineWidth(int i) {
        if (getUseBoundsForWidth()) {
            return super.getLineWidth(i);
        }
        if (i == 0) {
            return this.mMax;
        }
        return 0.0f;
    }

    @Override // android.text.Layout
    public final android.text.Layout.Directions getLineDirections(int i) {
        return android.text.Layout.DIRS_ALL_LEFT_TO_RIGHT;
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
    public int getEllipsisCount(int i) {
        return this.mEllipsizedCount;
    }

    @Override // android.text.Layout
    public int getEllipsisStart(int i) {
        return this.mEllipsizedStart;
    }

    @Override // android.text.Layout
    public int getEllipsizedWidth() {
        return this.mEllipsizedWidth;
    }

    @Override // android.text.Layout
    public boolean isFallbackLineSpacingEnabled() {
        return this.mUseFallbackLineSpacing;
    }

    @Override // android.text.Layout
    public android.graphics.RectF computeDrawingBoundingBox() {
        return this.mDrawingBounds;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
    @Override // android.text.Layout
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void draw(android.graphics.Canvas canvas, android.graphics.Path path, android.graphics.Paint paint, int i) {
        float f;
        if (this.mDirect != null && path == null) {
            if (getUseBoundsForWidth() && getShiftDrawingOffsetForStartOverhang()) {
                android.graphics.RectF computeDrawingBoundingBox = computeDrawingBoundingBox();
                if (computeDrawingBoundingBox.left < 0.0f) {
                    f = -computeDrawingBoundingBox.left;
                    canvas.translate(f, 0.0f);
                    canvas.drawText(this.mDirect, 0.0f, this.mBottom - this.mDesc, this.mPaint);
                    if (f == 0.0f) {
                        canvas.translate(-f, 0.0f);
                        return;
                    }
                    return;
                }
            }
            f = 0.0f;
            canvas.drawText(this.mDirect, 0.0f, this.mBottom - this.mDesc, this.mPaint);
            if (f == 0.0f) {
            }
        } else {
            super.draw(canvas, path, paint, i);
        }
    }

    @Override // android.text.TextUtils.EllipsizeCallback
    public void ellipsized(int i, int i2) {
        this.mEllipsizedStart = i;
        this.mEllipsizedCount = i2 - i;
    }

    public static class Metrics extends android.graphics.Paint.FontMetricsInt {
        private final android.graphics.RectF mDrawingBounds = new android.graphics.RectF();
        public int width;

        public android.graphics.RectF getDrawingBoundingBox() {
            return this.mDrawingBounds;
        }

        @Override // android.graphics.Paint.FontMetricsInt
        public java.lang.String toString() {
            return super.toString() + " width=" + this.width + ", drawingBounds = " + this.mDrawingBounds;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void reset() {
            this.top = 0;
            this.bottom = 0;
            this.ascent = 0;
            this.descent = 0;
            this.width = 0;
            this.leading = 0;
            this.mDrawingBounds.setEmpty();
        }
    }
}
