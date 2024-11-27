package com.android.internal.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class EmphasizedNotificationButton extends android.widget.Button {
    private static final java.lang.String FIRST_STRONG_ISOLATE = "\u2068";
    private static final java.lang.String IMAGE_SPAN_TEXT = "�";
    private static final java.lang.String LEFT_TO_RIGHT_ISOLATE = "\u2066";
    private static final java.lang.String POP_DIRECTIONAL_ISOLATE = "\u2069";
    private static final java.lang.String RIGHT_TO_LEFT_ISOLATE = "\u2067";
    private static final java.lang.String SPACER_SPAN_TEXT = " ";
    private static final java.lang.String TAG = "EmphasizedNotificationButton";
    private final android.graphics.drawable.GradientDrawable mBackground;
    private boolean mGluePending;
    private int mGluedLayoutDirection;
    private int mIconSize;
    private android.graphics.drawable.Drawable mIconToGlue;
    private int mInitialDrawablePadding;
    private java.lang.CharSequence mLabelToGlue;
    private boolean mPriority;
    private final android.graphics.drawable.RippleDrawable mRipple;

    public EmphasizedNotificationButton(android.content.Context context) {
        this(context, null);
    }

    public EmphasizedNotificationButton(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public EmphasizedNotificationButton(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public EmphasizedNotificationButton(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mGluedLayoutDirection = -1;
        this.mRipple = (android.graphics.drawable.RippleDrawable) getBackground();
        this.mRipple.mutate();
        this.mBackground = (android.graphics.drawable.GradientDrawable) ((android.graphics.drawable.DrawableWrapper) this.mRipple.getDrawable(0)).getDrawable();
        this.mIconSize = this.mContext.getResources().getDimensionPixelSize(com.android.internal.R.dimen.notification_actions_icon_drawable_size);
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, android.R.styleable.TextView, i, i2);
        try {
            this.mInitialDrawablePadding = obtainStyledAttributes.getDimensionPixelSize(52, 0);
            if (obtainStyledAttributes != null) {
                obtainStyledAttributes.close();
            }
            android.util.Log.v(TAG, "iconSize = " + this.mIconSize + "px, initialDrawablePadding = " + this.mInitialDrawablePadding + "px");
        } catch (java.lang.Throwable th) {
            if (obtainStyledAttributes != null) {
                try {
                    obtainStyledAttributes.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @android.view.RemotableViewMethod
    public void setRippleColor(android.content.res.ColorStateList colorStateList) {
        this.mRipple.setColor(colorStateList);
        invalidate();
    }

    @android.view.RemotableViewMethod
    public void setButtonBackground(android.content.res.ColorStateList colorStateList) {
        this.mBackground.setColor(colorStateList);
        invalidate();
    }

    @android.view.RemotableViewMethod(asyncImpl = "setImageIconAsync")
    public void setImageIcon(android.graphics.drawable.Icon icon) {
        lambda$setImageIconAsync$0(icon == null ? null : icon.loadDrawable(this.mContext));
    }

    @android.view.RemotableViewMethod
    public java.lang.Runnable setImageIconAsync(android.graphics.drawable.Icon icon) {
        final android.graphics.drawable.Drawable loadDrawable = icon == null ? null : icon.loadDrawable(this.mContext);
        return new java.lang.Runnable() { // from class: com.android.internal.widget.EmphasizedNotificationButton$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.internal.widget.EmphasizedNotificationButton.this.lambda$setImageIconAsync$0(loadDrawable);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: setImageDrawable, reason: merged with bridge method [inline-methods] */
    public void lambda$setImageIconAsync$0(android.graphics.drawable.Drawable drawable) {
        if (drawable != null) {
            prepareIcon(drawable);
        }
        setCompoundDrawablesRelative(drawable, null, null, null);
    }

    @android.view.RemotableViewMethod(asyncImpl = "glueIconAsync")
    public void glueIcon(android.graphics.drawable.Icon icon) {
        lambda$glueIconAsync$1(icon == null ? null : icon.loadDrawable(this.mContext));
    }

    @android.view.RemotableViewMethod
    public java.lang.Runnable glueIconAsync(android.graphics.drawable.Icon icon) {
        final android.graphics.drawable.Drawable loadDrawable = icon == null ? null : icon.loadDrawable(this.mContext);
        return new java.lang.Runnable() { // from class: com.android.internal.widget.EmphasizedNotificationButton$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                com.android.internal.widget.EmphasizedNotificationButton.this.lambda$glueIconAsync$1(loadDrawable);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: setIconToGlue, reason: merged with bridge method [inline-methods] */
    public void lambda$glueIconAsync$1(android.graphics.drawable.Drawable drawable) {
        if (!android.app.Flags.evenlyDividedCallStyleActionLayout()) {
            android.util.Log.e(TAG, "glueIcon: new action layout disabled; doing nothing");
            return;
        }
        if (drawable != null) {
            prepareIcon(drawable);
        }
        this.mIconToGlue = drawable;
        this.mGluePending = true;
        glueIconAndLabelIfNeeded();
    }

    private void prepareIcon(android.graphics.drawable.Drawable drawable) {
        drawable.mutate();
        drawable.setTintList(getTextColors());
        drawable.setTintBlendMode(android.graphics.BlendMode.SRC_IN);
        drawable.setBounds(0, 0, this.mIconSize, this.mIconSize);
    }

    @android.view.RemotableViewMethod(asyncImpl = "glueLabelAsync")
    public void glueLabel(java.lang.CharSequence charSequence) {
        lambda$glueLabelAsync$2(charSequence);
    }

    @android.view.RemotableViewMethod
    public java.lang.Runnable glueLabelAsync(final java.lang.CharSequence charSequence) {
        return new java.lang.Runnable() { // from class: com.android.internal.widget.EmphasizedNotificationButton$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                com.android.internal.widget.EmphasizedNotificationButton.this.lambda$glueLabelAsync$2(charSequence);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: setLabelToGlue, reason: merged with bridge method [inline-methods] */
    public void lambda$glueLabelAsync$2(java.lang.CharSequence charSequence) {
        if (!android.app.Flags.evenlyDividedCallStyleActionLayout()) {
            android.util.Log.e(TAG, "glueLabel: new action layout disabled; doing nothing");
            return;
        }
        this.mLabelToGlue = charSequence;
        this.mGluePending = true;
        glueIconAndLabelIfNeeded();
    }

    @Override // android.widget.TextView, android.view.View
    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        android.util.Log.v(TAG, "onRtlPropertiesChanged: layoutDirection = " + i + ", gluedLayoutDirection = " + this.mGluedLayoutDirection);
        if ((this.mGluedLayoutDirection != -1) && i != this.mGluedLayoutDirection) {
            android.util.Log.d(TAG, "onRtlPropertiesChanged: layout direction changed; regluing");
            this.mGluePending = true;
        }
        glueIconAndLabelIfNeeded();
    }

    private void glueIconAndLabelIfNeeded() {
        if (!this.mGluePending) {
            android.util.Log.v(TAG, "glueIconAndLabelIfNeeded: glue not pending; doing nothing");
            return;
        }
        if (!android.app.Flags.evenlyDividedCallStyleActionLayout()) {
            android.util.Log.e(TAG, "glueIconAndLabelIfNeeded: new action layout disabled; doing nothing");
            return;
        }
        if (!isLayoutDirectionResolved()) {
            android.util.Log.v(TAG, "glueIconAndLabelIfNeeded: layout direction not resolved; doing nothing");
            return;
        }
        int layoutDirection = getLayoutDirection();
        if (layoutDirection != 0 && layoutDirection != 1) {
            android.util.Log.e(TAG, "glueIconAndLabelIfNeeded: resolved layout direction neither LTR nor RTL; doing nothing");
            return;
        }
        glueIconAndLabel(layoutDirection);
        this.mGluePending = false;
        this.mGluedLayoutDirection = layoutDirection;
    }

    private void glueIconAndLabel(int i) {
        if (this.mIconToGlue == null && this.mLabelToGlue == null) {
            android.util.Log.d(TAG, "glueIconAndLabel: null icon and label, setting text to empty string");
            lambda$setTextAsync$0("");
            return;
        }
        if (this.mIconToGlue == null) {
            android.util.Log.d(TAG, "glueIconAndLabel: null icon, setting text to label");
            lambda$setTextAsync$0(this.mLabelToGlue);
            return;
        }
        if (this.mLabelToGlue == null) {
            android.util.Log.d(TAG, "glueIconAndLabel: null label, setting text to ImageSpan with icon");
            android.text.SpannableStringBuilder spannableStringBuilder = new android.text.SpannableStringBuilder();
            appendSpan(spannableStringBuilder, IMAGE_SPAN_TEXT, new android.text.style.ImageSpan(this.mIconToGlue, 2));
            lambda$setTextAsync$0(spannableStringBuilder);
            return;
        }
        boolean z = i == 1;
        android.util.Log.d(TAG, "glueIconAndLabel: icon = " + this.mIconToGlue + ", iconSize = " + this.mIconSize + "px, initialDrawablePadding = " + this.mInitialDrawablePadding + "px, labelToGlue.length = " + this.mLabelToGlue.length() + ", rtlLayout = " + z);
        logIfTextDirectionNotFirstStrong();
        android.text.SpannableStringBuilder spannableStringBuilder2 = new android.text.SpannableStringBuilder();
        spannableStringBuilder2.append((java.lang.CharSequence) (z ? RIGHT_TO_LEFT_ISOLATE : LEFT_TO_RIGHT_ISOLATE));
        appendSpan(spannableStringBuilder2, IMAGE_SPAN_TEXT, new android.text.style.ImageSpan(this.mIconToGlue, 2));
        appendSpan(spannableStringBuilder2, SPACER_SPAN_TEXT, new com.android.internal.widget.EmphasizedNotificationButton.SpacerSpan(this.mInitialDrawablePadding));
        spannableStringBuilder2.append(FIRST_STRONG_ISOLATE);
        appendSpan(spannableStringBuilder2, this.mLabelToGlue, new com.android.internal.widget.EmphasizedNotificationButton.CenterBesideImageSpan(this.mIconSize));
        spannableStringBuilder2.append(POP_DIRECTIONAL_ISOLATE);
        spannableStringBuilder2.append(POP_DIRECTIONAL_ISOLATE);
        lambda$setTextAsync$0(spannableStringBuilder2);
    }

    private void logIfTextDirectionNotFirstStrong() {
        if (!isTextDirectionResolved()) {
            android.util.Log.e(TAG, "glueIconAndLabel: text direction not resolved; letting View assume FIRST STRONG");
        }
        int textDirection = getTextDirection();
        if (textDirection != 1) {
            android.util.Log.w(TAG, "glueIconAndLabel: expected text direction TEXT_DIRECTION_FIRST_STRONG but found " + textDirection + "; will use a FIRST STRONG ISOLATE regardless");
        }
    }

    private void appendSpan(android.text.SpannableStringBuilder spannableStringBuilder, java.lang.CharSequence charSequence, java.lang.Object obj) {
        int length = spannableStringBuilder.length();
        spannableStringBuilder.append(charSequence);
        spannableStringBuilder.setSpan(obj, length, spannableStringBuilder.length(), 0);
    }

    @android.view.RemotableViewMethod
    public void setIsPriority(boolean z) {
        this.mPriority = z;
    }

    public boolean isPriority() {
        return this.mPriority;
    }

    private static class SpacerSpan extends android.text.style.ReplacementSpan {
        private static final java.lang.String TAG = "SpacerSpan";
        private int mWidth;

        SpacerSpan(int i) {
            this.mWidth = i;
            android.util.Log.d(TAG, "width = " + this.mWidth + "px");
        }

        @Override // android.text.style.ReplacementSpan
        public int getSize(android.graphics.Paint paint, java.lang.CharSequence charSequence, int i, int i2, android.graphics.Paint.FontMetricsInt fontMetricsInt) {
            android.util.Log.v(TAG, "getSize returning " + this.mWidth + "px");
            return this.mWidth;
        }

        @Override // android.text.style.ReplacementSpan
        public void draw(android.graphics.Canvas canvas, java.lang.CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, android.graphics.Paint paint) {
            android.util.Log.v(TAG, "drawing nothing");
        }
    }

    private static class CenterBesideImageSpan extends android.text.style.MetricAffectingSpan {
        private static final java.lang.String TAG = "CenterBesideImageSpan";
        private int mBaselineShiftOffset;
        private int mImageHeight;
        private boolean mMeasured;

        CenterBesideImageSpan(int i) {
            this.mImageHeight = i;
            android.util.Log.d(TAG, "imageHeight = " + this.mImageHeight + "px");
        }

        @Override // android.text.style.MetricAffectingSpan
        public void updateMeasureState(android.text.TextPaint textPaint) {
            int i = (int) (-textPaint.ascent());
            if (i < this.mImageHeight) {
                this.mBaselineShiftOffset = (-(this.mImageHeight - i)) / 2;
            } else {
                this.mBaselineShiftOffset = 0;
            }
            this.mMeasured = true;
            android.util.Log.d(TAG, "updateMeasureState: imageHeight = " + this.mImageHeight + "px, textHeight = " + i + "px, baselineShiftOffset = " + this.mBaselineShiftOffset + "px");
            textPaint.baselineShift += this.mBaselineShiftOffset;
        }

        @Override // android.text.style.CharacterStyle
        public void updateDrawState(android.text.TextPaint textPaint) {
            if (textPaint == null) {
                android.util.Log.e(TAG, "updateDrawState: textPaint is null; doing nothing");
            } else if (!this.mMeasured) {
                android.util.Log.e(TAG, "updateDrawState: called without measure; doing nothing");
            } else {
                android.util.Log.v(TAG, "updateDrawState: baselineShiftOffset = " + this.mBaselineShiftOffset + "px");
                textPaint.baselineShift += this.mBaselineShiftOffset;
            }
        }
    }
}
