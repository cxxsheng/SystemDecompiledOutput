package com.android.internal.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class ImageFloatingTextView extends android.widget.TextView {
    private static final boolean TRACE_ONMEASURE = android.os.Build.isDebuggable();
    private boolean mHasImage;
    private int mImageEndMargin;
    private int mIndentLines;
    private int mLayoutMaxLines;
    private int mMaxLinesForHeight;
    private int mResolvedDirection;
    private int mStaticLayoutCreationCountInOnMeasure;

    public ImageFloatingTextView(android.content.Context context) {
        this(context, null);
    }

    public ImageFloatingTextView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ImageFloatingTextView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ImageFloatingTextView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mIndentLines = 0;
        this.mHasImage = false;
        this.mResolvedDirection = -1;
        this.mMaxLinesForHeight = -1;
        this.mLayoutMaxLines = -1;
        this.mStaticLayoutCreationCountInOnMeasure = 0;
        setHyphenationFrequency(4);
        setBreakStrategy(1);
    }

    @Override // android.widget.TextView
    protected android.text.Layout makeSingleLayout(int i, android.text.BoringLayout.Metrics metrics, int i2, android.text.Layout.Alignment alignment, boolean z, android.text.TextUtils.TruncateAt truncateAt, boolean z2) {
        int maxLines;
        int[] iArr;
        if (TRACE_ONMEASURE) {
            android.os.Trace.beginSection("ImageFloatingTextView#makeSingleLayout");
            this.mStaticLayoutCreationCountInOnMeasure++;
        }
        android.text.method.TransformationMethod transformationMethod = getTransformationMethod();
        java.lang.CharSequence text = getText();
        if (transformationMethod != null) {
            text = transformationMethod.getTransformation(text, this);
        }
        if (text == null) {
            text = "";
        }
        android.text.StaticLayout.Builder hyphenationFrequency = android.text.StaticLayout.Builder.obtain(text, 0, text.length(), getPaint(), i).setAlignment(alignment).setTextDirection(getTextDirectionHeuristic()).setLineSpacing(getLineSpacingExtra(), getLineSpacingMultiplier()).setIncludePad(getIncludeFontPadding()).setUseLineSpacingFromFallbacks(true).setBreakStrategy(getBreakStrategy()).setHyphenationFrequency(getHyphenationFrequency());
        if (this.mMaxLinesForHeight > 0) {
            maxLines = this.mMaxLinesForHeight;
        } else {
            maxLines = getMaxLines() >= 0 ? getMaxLines() : Integer.MAX_VALUE;
        }
        hyphenationFrequency.setMaxLines(maxLines);
        this.mLayoutMaxLines = maxLines;
        if (z) {
            hyphenationFrequency.setEllipsize(truncateAt).setEllipsizedWidth(i2);
        }
        if (this.mHasImage && this.mIndentLines > 0) {
            iArr = new int[this.mIndentLines + 1];
            for (int i3 = 0; i3 < this.mIndentLines; i3++) {
                iArr[i3] = this.mImageEndMargin;
            }
        } else {
            iArr = null;
        }
        if (this.mResolvedDirection == 1) {
            hyphenationFrequency.setIndents(iArr, null);
        } else {
            hyphenationFrequency.setIndents(null, iArr);
        }
        android.text.StaticLayout build = hyphenationFrequency.build();
        if (TRACE_ONMEASURE) {
            trackMaxLines();
            android.os.Trace.endSection();
        }
        return build;
    }

    @android.view.RemotableViewMethod
    public void setImageEndMargin(int i) {
        if (this.mImageEndMargin != i) {
            this.mImageEndMargin = i;
            invalidateTextIfIndenting();
        }
    }

    @android.view.RemotableViewMethod
    public void setImageEndMarginDp(float f) {
        setImageEndMargin((int) (f * getResources().getDisplayMetrics().density));
    }

    @Override // android.widget.TextView, android.view.View
    protected void onMeasure(int i, int i2) {
        if (TRACE_ONMEASURE) {
            android.os.Trace.beginSection("ImageFloatingTextView#onMeasure");
        }
        this.mStaticLayoutCreationCountInOnMeasure = 0;
        int size = (android.view.View.MeasureSpec.getSize(i2) - this.mPaddingTop) - this.mPaddingBottom;
        if (getLayout() != null && getLayout().getHeight() != size) {
            this.mMaxLinesForHeight = -1;
            nullLayouts();
        }
        super.onMeasure(i, i2);
        android.text.Layout layout = getLayout();
        if (layout.getHeight() > size) {
            int lineCount = layout.getLineCount();
            while (lineCount > 1 && layout.getLineBottom(lineCount - 1) > size) {
                lineCount--;
            }
            if (getMaxLines() > 0) {
                lineCount = java.lang.Math.min(getMaxLines(), lineCount);
            }
            if (lineCount != this.mLayoutMaxLines) {
                this.mMaxLinesForHeight = lineCount;
                nullLayouts();
                super.onMeasure(i, i2);
            }
        }
        if (TRACE_ONMEASURE) {
            trackParameters();
            android.os.Trace.endSection();
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        if (i != this.mResolvedDirection && isLayoutDirectionResolved()) {
            this.mResolvedDirection = i;
            invalidateTextIfIndenting();
        }
    }

    private void invalidateTextIfIndenting() {
        if (this.mHasImage && this.mIndentLines > 0) {
            nullLayouts();
            requestLayout();
        }
    }

    @android.view.RemotableViewMethod
    public void setHasImage(boolean z) {
        setHasImageAndNumIndentLines(z, this.mIndentLines);
    }

    @android.view.RemotableViewMethod
    public void setNumIndentLines(int i) {
        setHasImageAndNumIndentLines(this.mHasImage, i);
    }

    private void setHasImageAndNumIndentLines(boolean z, int i) {
        int i2 = this.mHasImage ? this.mIndentLines : 0;
        int i3 = z ? i : 0;
        this.mIndentLines = i;
        this.mHasImage = z;
        if (i2 != i3) {
            nullLayouts();
            requestLayout();
        }
    }

    private void trackParameters() {
        if (!TRACE_ONMEASURE) {
            return;
        }
        android.os.Trace.setCounter("ImageFloatingView#staticLayoutCreationCount", this.mStaticLayoutCreationCountInOnMeasure);
        android.os.Trace.setCounter("ImageFloatingView#isPrecomputedText", isTextAPrecomputedText());
    }

    private int isTextAPrecomputedText() {
        java.lang.CharSequence text = getText();
        if (text == null || !(text instanceof android.text.PrecomputedText)) {
            return 0;
        }
        return 1;
    }

    private void trackMaxLines() {
        if (!TRACE_ONMEASURE) {
            return;
        }
        android.os.Trace.setCounter("ImageFloatingView#layoutMaxLines", this.mLayoutMaxLines);
    }
}
