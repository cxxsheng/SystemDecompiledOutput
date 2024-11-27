package com.android.internal.widget;

/* loaded from: classes5.dex */
public class SubtitleView extends android.view.View {
    private static final int COLOR_BEVEL_DARK = Integer.MIN_VALUE;
    private static final int COLOR_BEVEL_LIGHT = -2130706433;
    private static final float INNER_PADDING_RATIO = 0.125f;
    private android.text.Layout.Alignment mAlignment;
    private int mBackgroundColor;
    private final float mCornerRadius;
    private int mEdgeColor;
    private int mEdgeType;
    private int mForegroundColor;
    private boolean mHasMeasurements;
    private int mInnerPaddingX;
    private int mLastMeasuredWidth;
    private android.text.StaticLayout mLayout;
    private final android.graphics.RectF mLineBounds;
    private final float mOutlineWidth;
    private android.graphics.Paint mPaint;
    private final float mShadowOffsetX;
    private final float mShadowOffsetY;
    private final float mShadowRadius;
    private float mSpacingAdd;
    private float mSpacingMult;
    private final android.text.SpannableStringBuilder mText;
    private android.text.TextPaint mTextPaint;

    public SubtitleView(android.content.Context context) {
        this(context, null);
    }

    public SubtitleView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SubtitleView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SubtitleView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet);
        this.mLineBounds = new android.graphics.RectF();
        this.mText = new android.text.SpannableStringBuilder();
        this.mAlignment = android.text.Layout.Alignment.ALIGN_CENTER;
        this.mSpacingMult = 1.0f;
        this.mSpacingAdd = 0.0f;
        this.mInnerPaddingX = 0;
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, android.R.styleable.TextView, i, i2);
        int indexCount = obtainStyledAttributes.getIndexCount();
        java.lang.CharSequence charSequence = "";
        int i3 = 15;
        for (int i4 = 0; i4 < indexCount; i4++) {
            int index = obtainStyledAttributes.getIndex(i4);
            switch (index) {
                case 0:
                    i3 = obtainStyledAttributes.getDimensionPixelSize(index, i3);
                    break;
                case 18:
                    charSequence = obtainStyledAttributes.getText(index);
                    break;
                case 53:
                    this.mSpacingAdd = obtainStyledAttributes.getDimensionPixelSize(index, (int) this.mSpacingAdd);
                    break;
                case 54:
                    this.mSpacingMult = obtainStyledAttributes.getFloat(index, this.mSpacingMult);
                    break;
            }
        }
        obtainStyledAttributes.recycle();
        android.content.res.Resources resources = getContext().getResources();
        this.mCornerRadius = resources.getDimensionPixelSize(com.android.internal.R.dimen.subtitle_corner_radius);
        this.mOutlineWidth = resources.getDimensionPixelSize(com.android.internal.R.dimen.subtitle_outline_width);
        this.mShadowRadius = resources.getDimensionPixelSize(com.android.internal.R.dimen.subtitle_shadow_radius);
        this.mShadowOffsetX = resources.getDimensionPixelSize(com.android.internal.R.dimen.subtitle_shadow_offset);
        this.mShadowOffsetY = this.mShadowOffsetX;
        this.mTextPaint = new android.text.TextPaint();
        this.mTextPaint.setAntiAlias(true);
        this.mTextPaint.setSubpixelText(true);
        this.mPaint = new android.graphics.Paint();
        this.mPaint.setAntiAlias(true);
        setText(charSequence);
        setTextSize(i3);
    }

    public void setText(int i) {
        setText(getContext().getText(i));
    }

    public void setText(java.lang.CharSequence charSequence) {
        this.mText.clear();
        this.mText.append(charSequence);
        this.mHasMeasurements = false;
        requestLayout();
        invalidate();
    }

    public void setForegroundColor(int i) {
        this.mForegroundColor = i;
        invalidate();
    }

    @Override // android.view.View
    public void setBackgroundColor(int i) {
        this.mBackgroundColor = i;
        invalidate();
    }

    public void setEdgeType(int i) {
        this.mEdgeType = i;
        invalidate();
    }

    public void setEdgeColor(int i) {
        this.mEdgeColor = i;
        invalidate();
    }

    public void setTextSize(float f) {
        if (this.mTextPaint.getTextSize() != f) {
            this.mTextPaint.setTextSize(f);
            this.mInnerPaddingX = (int) ((f * INNER_PADDING_RATIO) + 0.5f);
            this.mHasMeasurements = false;
            requestLayout();
            invalidate();
        }
    }

    public void setTypeface(android.graphics.Typeface typeface) {
        if (this.mTextPaint.getTypeface() != typeface) {
            this.mTextPaint.setTypeface(typeface);
            this.mHasMeasurements = false;
            requestLayout();
            invalidate();
        }
    }

    public void setAlignment(android.text.Layout.Alignment alignment) {
        if (this.mAlignment != alignment) {
            this.mAlignment = alignment;
            this.mHasMeasurements = false;
            requestLayout();
            invalidate();
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        if (computeMeasurements(android.view.View.MeasureSpec.getSize(i))) {
            android.text.StaticLayout staticLayout = this.mLayout;
            setMeasuredDimension(staticLayout.getWidth() + this.mPaddingLeft + this.mPaddingRight + (this.mInnerPaddingX * 2), staticLayout.getHeight() + this.mPaddingTop + this.mPaddingBottom);
            return;
        }
        setMeasuredDimension(16777216, 16777216);
    }

    @Override // android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        computeMeasurements(i3 - i);
    }

    private boolean computeMeasurements(int i) {
        if (this.mHasMeasurements && i == this.mLastMeasuredWidth) {
            return true;
        }
        int i2 = i - ((this.mPaddingLeft + this.mPaddingRight) + (this.mInnerPaddingX * 2));
        if (i2 <= 0) {
            return false;
        }
        this.mHasMeasurements = true;
        this.mLastMeasuredWidth = i2;
        this.mLayout = android.text.StaticLayout.Builder.obtain(this.mText, 0, this.mText.length(), this.mTextPaint, i2).setAlignment(this.mAlignment).setLineSpacing(this.mSpacingAdd, this.mSpacingMult).setUseLineSpacingFromFallbacks(true).build();
        return true;
    }

    public void setStyle(int i) {
        android.view.accessibility.CaptioningManager.CaptionStyle captionStyle;
        android.content.ContentResolver contentResolver = this.mContext.getContentResolver();
        if (i == -1) {
            captionStyle = android.view.accessibility.CaptioningManager.CaptionStyle.getCustomStyle(contentResolver);
        } else {
            captionStyle = android.view.accessibility.CaptioningManager.CaptionStyle.PRESETS[i];
        }
        android.view.accessibility.CaptioningManager.CaptionStyle captionStyle2 = android.view.accessibility.CaptioningManager.CaptionStyle.DEFAULT;
        this.mForegroundColor = captionStyle.hasForegroundColor() ? captionStyle.foregroundColor : captionStyle2.foregroundColor;
        this.mBackgroundColor = captionStyle.hasBackgroundColor() ? captionStyle.backgroundColor : captionStyle2.backgroundColor;
        this.mEdgeType = captionStyle.hasEdgeType() ? captionStyle.edgeType : captionStyle2.edgeType;
        this.mEdgeColor = captionStyle.hasEdgeColor() ? captionStyle.edgeColor : captionStyle2.edgeColor;
        this.mHasMeasurements = false;
        setTypeface(captionStyle.getTypeface());
        requestLayout();
    }

    @Override // android.view.View
    protected void onDraw(android.graphics.Canvas canvas) {
        android.text.StaticLayout staticLayout = this.mLayout;
        if (staticLayout == null) {
            return;
        }
        int save = canvas.save();
        int i = this.mInnerPaddingX;
        canvas.translate(this.mPaddingLeft + i, this.mPaddingTop);
        int lineCount = staticLayout.getLineCount();
        android.text.TextPaint textPaint = this.mTextPaint;
        android.graphics.Paint paint = this.mPaint;
        android.graphics.RectF rectF = this.mLineBounds;
        if (android.graphics.Color.alpha(this.mBackgroundColor) > 0) {
            float f = this.mCornerRadius;
            float lineTop = staticLayout.getLineTop(0);
            paint.setColor(this.mBackgroundColor);
            paint.setStyle(android.graphics.Paint.Style.FILL);
            for (int i2 = 0; i2 < lineCount; i2++) {
                float f2 = i;
                rectF.left = staticLayout.getLineLeft(i2) - f2;
                rectF.right = staticLayout.getLineRight(i2) + f2;
                rectF.top = lineTop;
                rectF.bottom = staticLayout.getLineBottom(i2);
                lineTop = rectF.bottom;
                canvas.drawRoundRect(rectF, f, f, paint);
            }
        }
        int i3 = this.mEdgeType;
        if (i3 == 1) {
            textPaint.setStrokeJoin(android.graphics.Paint.Join.ROUND);
            textPaint.setStrokeWidth(this.mOutlineWidth);
            textPaint.setColor(this.mEdgeColor);
            textPaint.setStyle(android.graphics.Paint.Style.FILL_AND_STROKE);
            for (int i4 = 0; i4 < lineCount; i4++) {
                staticLayout.drawText(canvas, i4, i4);
            }
        } else if (i3 == 2) {
            textPaint.setShadowLayer(this.mShadowRadius, this.mShadowOffsetX, this.mShadowOffsetY, this.mEdgeColor);
        } else if (i3 == 3 || i3 == 4) {
            boolean z = i3 == 3;
            int i5 = z ? -1 : this.mEdgeColor;
            int i6 = z ? this.mEdgeColor : -1;
            float f3 = this.mShadowRadius / 2.0f;
            textPaint.setColor(this.mForegroundColor);
            textPaint.setStyle(android.graphics.Paint.Style.FILL);
            float f4 = -f3;
            textPaint.setShadowLayer(this.mShadowRadius, f4, f4, i5);
            for (int i7 = 0; i7 < lineCount; i7++) {
                staticLayout.drawText(canvas, i7, i7);
            }
            textPaint.setShadowLayer(this.mShadowRadius, f3, f3, i6);
        }
        textPaint.setColor(this.mForegroundColor);
        textPaint.setStyle(android.graphics.Paint.Style.FILL);
        for (int i8 = 0; i8 < lineCount; i8++) {
            staticLayout.drawText(canvas, i8, i8);
        }
        textPaint.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
        canvas.restoreToCount(save);
    }
}
