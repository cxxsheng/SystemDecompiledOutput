package android.media;

/* compiled from: ClosedCaptionRenderer.java */
/* loaded from: classes2.dex */
class Cea608CCWidget extends android.media.ClosedCaptionWidget implements android.media.Cea608CCParser.DisplayListener {
    private static final java.lang.String mDummyText = "1234567890123456789012345678901234";
    private static final android.graphics.Rect mTextBounds = new android.graphics.Rect();

    public Cea608CCWidget(android.content.Context context) {
        this(context, null);
    }

    public Cea608CCWidget(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public Cea608CCWidget(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public Cea608CCWidget(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.media.ClosedCaptionWidget
    public android.media.ClosedCaptionWidget.ClosedCaptionLayout createCaptionLayout(android.content.Context context) {
        return new android.media.Cea608CCWidget.CCLayout(context);
    }

    @Override // android.media.Cea608CCParser.DisplayListener
    public void onDisplayChanged(android.text.SpannableStringBuilder[] spannableStringBuilderArr) {
        ((android.media.Cea608CCWidget.CCLayout) this.mClosedCaptionLayout).update(spannableStringBuilderArr);
        if (this.mListener != null) {
            this.mListener.onChanged(this);
        }
    }

    @Override // android.media.Cea608CCParser.DisplayListener
    public android.view.accessibility.CaptioningManager.CaptionStyle getCaptionStyle() {
        return this.mCaptionStyle;
    }

    /* compiled from: ClosedCaptionRenderer.java */
    private static class CCLineBox extends android.widget.TextView {
        private static final float EDGE_OUTLINE_RATIO = 0.1f;
        private static final float EDGE_SHADOW_RATIO = 0.05f;
        private static final float FONT_PADDING_RATIO = 0.75f;
        private int mBgColor;
        private int mEdgeColor;
        private int mEdgeType;
        private float mOutlineWidth;
        private float mShadowOffset;
        private float mShadowRadius;
        private int mTextColor;

        CCLineBox(android.content.Context context) {
            super(context);
            this.mTextColor = -1;
            this.mBgColor = -16777216;
            this.mEdgeType = 0;
            this.mEdgeColor = 0;
            setGravity(17);
            setBackgroundColor(0);
            setTextColor(-1);
            setTypeface(android.graphics.Typeface.MONOSPACE);
            setVisibility(4);
            android.content.res.Resources resources = getContext().getResources();
            this.mOutlineWidth = resources.getDimensionPixelSize(com.android.internal.R.dimen.subtitle_outline_width);
            this.mShadowRadius = resources.getDimensionPixelSize(com.android.internal.R.dimen.subtitle_shadow_radius);
            this.mShadowOffset = resources.getDimensionPixelSize(com.android.internal.R.dimen.subtitle_shadow_offset);
        }

        void setCaptionStyle(android.view.accessibility.CaptioningManager.CaptionStyle captionStyle) {
            this.mTextColor = captionStyle.foregroundColor;
            this.mBgColor = captionStyle.backgroundColor;
            this.mEdgeType = captionStyle.edgeType;
            this.mEdgeColor = captionStyle.edgeColor;
            setTextColor(this.mTextColor);
            if (this.mEdgeType == 2) {
                setShadowLayer(this.mShadowRadius, this.mShadowOffset, this.mShadowOffset, this.mEdgeColor);
            } else {
                setShadowLayer(0.0f, 0.0f, 0.0f, 0);
            }
            invalidate();
        }

        @Override // android.widget.TextView, android.view.View
        protected void onMeasure(int i, int i2) {
            float size = android.view.View.MeasureSpec.getSize(i2) * 0.75f;
            setTextSize(0, size);
            this.mOutlineWidth = (0.1f * size) + 1.0f;
            this.mShadowRadius = (size * EDGE_SHADOW_RATIO) + 1.0f;
            this.mShadowOffset = this.mShadowRadius;
            setScaleX(1.0f);
            getPaint().getTextBounds(android.media.Cea608CCWidget.mDummyText, 0, android.media.Cea608CCWidget.mDummyText.length(), android.media.Cea608CCWidget.mTextBounds);
            setScaleX(android.view.View.MeasureSpec.getSize(i) / android.media.Cea608CCWidget.mTextBounds.width());
            super.onMeasure(i, i2);
        }

        @Override // android.widget.TextView, android.view.View
        protected void onDraw(android.graphics.Canvas canvas) {
            if (this.mEdgeType == -1 || this.mEdgeType == 0 || this.mEdgeType == 2) {
                super.onDraw(canvas);
            } else if (this.mEdgeType == 1) {
                drawEdgeOutline(canvas);
            } else {
                drawEdgeRaisedOrDepressed(canvas);
            }
        }

        private void drawEdgeOutline(android.graphics.Canvas canvas) {
            android.text.TextPaint paint = getPaint();
            android.graphics.Paint.Style style = paint.getStyle();
            android.graphics.Paint.Join strokeJoin = paint.getStrokeJoin();
            float strokeWidth = paint.getStrokeWidth();
            setTextColor(this.mEdgeColor);
            paint.setStyle(android.graphics.Paint.Style.FILL_AND_STROKE);
            paint.setStrokeJoin(android.graphics.Paint.Join.ROUND);
            paint.setStrokeWidth(this.mOutlineWidth);
            super.onDraw(canvas);
            setTextColor(this.mTextColor);
            paint.setStyle(style);
            paint.setStrokeJoin(strokeJoin);
            paint.setStrokeWidth(strokeWidth);
            setBackgroundSpans(0);
            super.onDraw(canvas);
            setBackgroundSpans(this.mBgColor);
        }

        private void drawEdgeRaisedOrDepressed(android.graphics.Canvas canvas) {
            android.text.TextPaint paint = getPaint();
            android.graphics.Paint.Style style = paint.getStyle();
            paint.setStyle(android.graphics.Paint.Style.FILL);
            boolean z = this.mEdgeType == 3;
            int i = z ? -1 : this.mEdgeColor;
            int i2 = z ? this.mEdgeColor : -1;
            float f = this.mShadowRadius / 2.0f;
            float f2 = -f;
            setShadowLayer(this.mShadowRadius, f2, f2, i);
            super.onDraw(canvas);
            setBackgroundSpans(0);
            setShadowLayer(this.mShadowRadius, f, f, i2);
            super.onDraw(canvas);
            paint.setStyle(style);
            setBackgroundSpans(this.mBgColor);
        }

        private void setBackgroundSpans(int i) {
            java.lang.CharSequence text = getText();
            if (text instanceof android.text.Spannable) {
                android.text.Spannable spannable = (android.text.Spannable) text;
                for (android.media.Cea608CCParser.MutableBackgroundColorSpan mutableBackgroundColorSpan : (android.media.Cea608CCParser.MutableBackgroundColorSpan[]) spannable.getSpans(0, spannable.length(), android.media.Cea608CCParser.MutableBackgroundColorSpan.class)) {
                    mutableBackgroundColorSpan.setBackgroundColor(i);
                }
            }
        }
    }

    /* compiled from: ClosedCaptionRenderer.java */
    private static class CCLayout extends android.widget.LinearLayout implements android.media.ClosedCaptionWidget.ClosedCaptionLayout {
        private static final int MAX_ROWS = 15;
        private static final float SAFE_AREA_RATIO = 0.9f;
        private final android.media.Cea608CCWidget.CCLineBox[] mLineBoxes;

        CCLayout(android.content.Context context) {
            super(context);
            this.mLineBoxes = new android.media.Cea608CCWidget.CCLineBox[15];
            setGravity(android.view.Gravity.START);
            setOrientation(1);
            for (int i = 0; i < 15; i++) {
                this.mLineBoxes[i] = new android.media.Cea608CCWidget.CCLineBox(getContext());
                addView(this.mLineBoxes[i], -2, -2);
            }
        }

        @Override // android.media.ClosedCaptionWidget.ClosedCaptionLayout
        public void setCaptionStyle(android.view.accessibility.CaptioningManager.CaptionStyle captionStyle) {
            for (int i = 0; i < 15; i++) {
                this.mLineBoxes[i].setCaptionStyle(captionStyle);
            }
        }

        @Override // android.media.ClosedCaptionWidget.ClosedCaptionLayout
        public void setFontScale(float f) {
        }

        void update(android.text.SpannableStringBuilder[] spannableStringBuilderArr) {
            for (int i = 0; i < 15; i++) {
                if (spannableStringBuilderArr[i] != null) {
                    this.mLineBoxes[i].setText(spannableStringBuilderArr[i], android.widget.TextView.BufferType.SPANNABLE);
                    this.mLineBoxes[i].setVisibility(0);
                } else {
                    this.mLineBoxes[i].setVisibility(4);
                }
            }
        }

        @Override // android.widget.LinearLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
            int measuredWidth = getMeasuredWidth();
            int measuredHeight = getMeasuredHeight();
            int i3 = measuredWidth * 3;
            int i4 = measuredHeight * 4;
            if (i3 >= i4) {
                measuredWidth = i4 / 3;
            } else {
                measuredHeight = i3 / 4;
            }
            int i5 = (int) (measuredWidth * SAFE_AREA_RATIO);
            int makeMeasureSpec = android.view.View.MeasureSpec.makeMeasureSpec(((int) (measuredHeight * SAFE_AREA_RATIO)) / 15, 1073741824);
            int makeMeasureSpec2 = android.view.View.MeasureSpec.makeMeasureSpec(i5, 1073741824);
            for (int i6 = 0; i6 < 15; i6++) {
                this.mLineBoxes[i6].measure(makeMeasureSpec2, makeMeasureSpec);
            }
        }

        @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            int i5;
            int i6;
            int i7 = i3 - i;
            int i8 = i4 - i2;
            int i9 = i7 * 3;
            int i10 = i8 * 4;
            if (i9 >= i10) {
                i6 = i10 / 3;
                i5 = i8;
            } else {
                i5 = i9 / 4;
                i6 = i7;
            }
            int i11 = (int) (i6 * SAFE_AREA_RATIO);
            int i12 = (int) (i5 * SAFE_AREA_RATIO);
            int i13 = (i7 - i11) / 2;
            int i14 = (i8 - i12) / 2;
            int i15 = 0;
            while (i15 < 15) {
                i15++;
                this.mLineBoxes[i15].layout(i13, ((i12 * i15) / 15) + i14, i13 + i11, ((i12 * i15) / 15) + i14);
            }
        }
    }
}
