package android.widget;

/* loaded from: classes4.dex */
public abstract class AbsSeekBar extends android.widget.ProgressBar {
    private static final int NO_ALPHA = 255;
    private float mDisabledAlpha;
    private final java.util.List<android.graphics.Rect> mGestureExclusionRects;
    private boolean mHasThumbBlendMode;
    private boolean mHasThumbTint;
    private boolean mHasTickMarkBlendMode;
    private boolean mHasTickMarkTint;
    private boolean mIsDragging;
    boolean mIsUserSeekable;
    private int mKeyProgressIncrement;
    private int mScaledTouchSlop;
    private boolean mSplitTrack;
    private final android.graphics.Rect mTempRect;
    private android.graphics.drawable.Drawable mThumb;
    private android.graphics.BlendMode mThumbBlendMode;
    private int mThumbExclusionMaxSize;
    private int mThumbOffset;
    private final android.graphics.Rect mThumbRect;
    private android.content.res.ColorStateList mThumbTintList;
    private android.graphics.drawable.Drawable mTickMark;
    private android.graphics.BlendMode mTickMarkBlendMode;
    private android.content.res.ColorStateList mTickMarkTintList;
    private float mTouchDownX;
    float mTouchProgressOffset;
    private float mTouchThumbOffset;
    private java.util.List<android.graphics.Rect> mUserGestureExclusionRects;

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.AbsSeekBar> {
        private boolean mPropertiesMapped = false;
        private int mThumbTintId;
        private int mThumbTintModeId;
        private int mTickMarkTintBlendModeId;
        private int mTickMarkTintId;
        private int mTickMarkTintModeId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
            this.mThumbTintId = propertyMapper.mapObject("thumbTint", 16843889);
            this.mThumbTintModeId = propertyMapper.mapObject("thumbTintMode", 16843890);
            this.mTickMarkTintId = propertyMapper.mapObject("tickMarkTint", 16844043);
            this.mTickMarkTintBlendModeId = propertyMapper.mapObject("tickMarkTintBlendMode", 7);
            this.mTickMarkTintModeId = propertyMapper.mapObject("tickMarkTintMode", 16844044);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(android.widget.AbsSeekBar absSeekBar, android.view.inspector.PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readObject(this.mThumbTintId, absSeekBar.getThumbTintList());
            propertyReader.readObject(this.mThumbTintModeId, absSeekBar.getThumbTintMode());
            propertyReader.readObject(this.mTickMarkTintId, absSeekBar.getTickMarkTintList());
            propertyReader.readObject(this.mTickMarkTintBlendModeId, absSeekBar.getTickMarkTintBlendMode());
            propertyReader.readObject(this.mTickMarkTintModeId, absSeekBar.getTickMarkTintMode());
        }
    }

    public AbsSeekBar(android.content.Context context) {
        super(context);
        this.mTempRect = new android.graphics.Rect();
        this.mThumbTintList = null;
        this.mThumbBlendMode = null;
        this.mHasThumbTint = false;
        this.mHasThumbBlendMode = false;
        this.mTickMarkTintList = null;
        this.mTickMarkBlendMode = null;
        this.mHasTickMarkTint = false;
        this.mHasTickMarkBlendMode = false;
        this.mIsUserSeekable = true;
        this.mKeyProgressIncrement = 1;
        this.mTouchThumbOffset = 0.0f;
        this.mUserGestureExclusionRects = java.util.Collections.emptyList();
        this.mGestureExclusionRects = new java.util.ArrayList();
        this.mThumbRect = new android.graphics.Rect();
    }

    public AbsSeekBar(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTempRect = new android.graphics.Rect();
        this.mThumbTintList = null;
        this.mThumbBlendMode = null;
        this.mHasThumbTint = false;
        this.mHasThumbBlendMode = false;
        this.mTickMarkTintList = null;
        this.mTickMarkBlendMode = null;
        this.mHasTickMarkTint = false;
        this.mHasTickMarkBlendMode = false;
        this.mIsUserSeekable = true;
        this.mKeyProgressIncrement = 1;
        this.mTouchThumbOffset = 0.0f;
        this.mUserGestureExclusionRects = java.util.Collections.emptyList();
        this.mGestureExclusionRects = new java.util.ArrayList();
        this.mThumbRect = new android.graphics.Rect();
    }

    public AbsSeekBar(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public AbsSeekBar(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mTempRect = new android.graphics.Rect();
        this.mThumbTintList = null;
        this.mThumbBlendMode = null;
        this.mHasThumbTint = false;
        this.mHasThumbBlendMode = false;
        this.mTickMarkTintList = null;
        this.mTickMarkBlendMode = null;
        this.mHasTickMarkTint = false;
        this.mHasTickMarkBlendMode = false;
        this.mIsUserSeekable = true;
        this.mKeyProgressIncrement = 1;
        this.mTouchThumbOffset = 0.0f;
        this.mUserGestureExclusionRects = java.util.Collections.emptyList();
        this.mGestureExclusionRects = new java.util.ArrayList();
        this.mThumbRect = new android.graphics.Rect();
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.SeekBar, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.SeekBar, attributeSet, obtainStyledAttributes, i, i2);
        setThumb(obtainStyledAttributes.getDrawable(0));
        if (obtainStyledAttributes.hasValue(4)) {
            this.mThumbBlendMode = android.graphics.drawable.Drawable.parseBlendMode(obtainStyledAttributes.getInt(4, -1), this.mThumbBlendMode);
            this.mHasThumbBlendMode = true;
        }
        if (obtainStyledAttributes.hasValue(3)) {
            this.mThumbTintList = obtainStyledAttributes.getColorStateList(3);
            this.mHasThumbTint = true;
        }
        setTickMark(obtainStyledAttributes.getDrawable(5));
        if (obtainStyledAttributes.hasValue(7)) {
            this.mTickMarkBlendMode = android.graphics.drawable.Drawable.parseBlendMode(obtainStyledAttributes.getInt(7, -1), this.mTickMarkBlendMode);
            this.mHasTickMarkBlendMode = true;
        }
        if (obtainStyledAttributes.hasValue(6)) {
            this.mTickMarkTintList = obtainStyledAttributes.getColorStateList(6);
            this.mHasTickMarkTint = true;
        }
        this.mSplitTrack = obtainStyledAttributes.getBoolean(2, false);
        setThumbOffset(obtainStyledAttributes.getDimensionPixelOffset(1, getThumbOffset()));
        boolean z = obtainStyledAttributes.getBoolean(8, true);
        obtainStyledAttributes.recycle();
        if (z) {
            android.content.res.TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.Theme, 0, 0);
            this.mDisabledAlpha = obtainStyledAttributes2.getFloat(3, 0.5f);
            obtainStyledAttributes2.recycle();
        } else {
            this.mDisabledAlpha = 1.0f;
        }
        applyThumbTint();
        applyTickMarkTint();
        this.mScaledTouchSlop = android.view.ViewConfiguration.get(context).getScaledTouchSlop();
        this.mThumbExclusionMaxSize = getResources().getDimensionPixelSize(com.android.internal.R.dimen.seekbar_thumb_exclusion_max_size);
    }

    public void setThumb(android.graphics.drawable.Drawable drawable) {
        boolean z;
        if (this.mThumb != null && drawable != this.mThumb) {
            this.mThumb.setCallback(null);
            z = true;
        } else {
            z = false;
        }
        if (drawable != null) {
            drawable.setCallback(this);
            if (canResolveLayoutDirection()) {
                drawable.setLayoutDirection(getLayoutDirection());
            }
            this.mThumbOffset = drawable.getIntrinsicWidth() / 2;
            if (z && (drawable.getIntrinsicWidth() != this.mThumb.getIntrinsicWidth() || drawable.getIntrinsicHeight() != this.mThumb.getIntrinsicHeight())) {
                requestLayout();
            }
        }
        this.mThumb = drawable;
        applyThumbTint();
        invalidate();
        if (z) {
            updateThumbAndTrackPos(getWidth(), getHeight());
            if (drawable != null && drawable.isStateful()) {
                drawable.setState(getDrawableState());
            }
        }
    }

    public android.graphics.drawable.Drawable getThumb() {
        return this.mThumb;
    }

    public void setThumbTintList(android.content.res.ColorStateList colorStateList) {
        this.mThumbTintList = colorStateList;
        this.mHasThumbTint = true;
        applyThumbTint();
    }

    public android.content.res.ColorStateList getThumbTintList() {
        return this.mThumbTintList;
    }

    public void setThumbTintMode(android.graphics.PorterDuff.Mode mode) {
        setThumbTintBlendMode(mode != null ? android.graphics.BlendMode.fromValue(mode.nativeInt) : null);
    }

    public void setThumbTintBlendMode(android.graphics.BlendMode blendMode) {
        this.mThumbBlendMode = blendMode;
        this.mHasThumbBlendMode = true;
        applyThumbTint();
    }

    public android.graphics.PorterDuff.Mode getThumbTintMode() {
        if (this.mThumbBlendMode != null) {
            return android.graphics.BlendMode.blendModeToPorterDuffMode(this.mThumbBlendMode);
        }
        return null;
    }

    public android.graphics.BlendMode getThumbTintBlendMode() {
        return this.mThumbBlendMode;
    }

    private void applyThumbTint() {
        if (this.mThumb != null) {
            if (this.mHasThumbTint || this.mHasThumbBlendMode) {
                this.mThumb = this.mThumb.mutate();
                if (this.mHasThumbTint) {
                    this.mThumb.setTintList(this.mThumbTintList);
                }
                if (this.mHasThumbBlendMode) {
                    this.mThumb.setTintBlendMode(this.mThumbBlendMode);
                }
                if (this.mThumb.isStateful()) {
                    this.mThumb.setState(getDrawableState());
                }
            }
        }
    }

    public int getThumbOffset() {
        return this.mThumbOffset;
    }

    public void setThumbOffset(int i) {
        this.mThumbOffset = i;
        invalidate();
    }

    public void setSplitTrack(boolean z) {
        this.mSplitTrack = z;
        invalidate();
    }

    public boolean getSplitTrack() {
        return this.mSplitTrack;
    }

    public void setTickMark(android.graphics.drawable.Drawable drawable) {
        if (this.mTickMark != null) {
            this.mTickMark.setCallback(null);
        }
        this.mTickMark = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
            drawable.setLayoutDirection(getLayoutDirection());
            if (drawable.isStateful()) {
                drawable.setState(getDrawableState());
            }
            applyTickMarkTint();
        }
        invalidate();
    }

    public android.graphics.drawable.Drawable getTickMark() {
        return this.mTickMark;
    }

    public void setTickMarkTintList(android.content.res.ColorStateList colorStateList) {
        this.mTickMarkTintList = colorStateList;
        this.mHasTickMarkTint = true;
        applyTickMarkTint();
    }

    public android.content.res.ColorStateList getTickMarkTintList() {
        return this.mTickMarkTintList;
    }

    public void setTickMarkTintMode(android.graphics.PorterDuff.Mode mode) {
        setTickMarkTintBlendMode(mode != null ? android.graphics.BlendMode.fromValue(mode.nativeInt) : null);
    }

    public void setTickMarkTintBlendMode(android.graphics.BlendMode blendMode) {
        this.mTickMarkBlendMode = blendMode;
        this.mHasTickMarkBlendMode = true;
        applyTickMarkTint();
    }

    public android.graphics.PorterDuff.Mode getTickMarkTintMode() {
        if (this.mTickMarkBlendMode != null) {
            return android.graphics.BlendMode.blendModeToPorterDuffMode(this.mTickMarkBlendMode);
        }
        return null;
    }

    public android.graphics.BlendMode getTickMarkTintBlendMode() {
        return this.mTickMarkBlendMode;
    }

    private void applyTickMarkTint() {
        if (this.mTickMark != null) {
            if (this.mHasTickMarkTint || this.mHasTickMarkBlendMode) {
                this.mTickMark = this.mTickMark.mutate();
                if (this.mHasTickMarkTint) {
                    this.mTickMark.setTintList(this.mTickMarkTintList);
                }
                if (this.mHasTickMarkBlendMode) {
                    this.mTickMark.setTintBlendMode(this.mTickMarkBlendMode);
                }
                if (this.mTickMark.isStateful()) {
                    this.mTickMark.setState(getDrawableState());
                }
            }
        }
    }

    public void setKeyProgressIncrement(int i) {
        if (i < 0) {
            i = -i;
        }
        this.mKeyProgressIncrement = i;
    }

    public int getKeyProgressIncrement() {
        return this.mKeyProgressIncrement;
    }

    @Override // android.widget.ProgressBar
    public synchronized void setMin(int i) {
        super.setMin(i);
        int max = getMax() - getMin();
        if (this.mKeyProgressIncrement == 0 || max / this.mKeyProgressIncrement > 20) {
            setKeyProgressIncrement(java.lang.Math.max(1, java.lang.Math.round(max / 20.0f)));
        }
    }

    @Override // android.widget.ProgressBar
    public synchronized void setMax(int i) {
        super.setMax(i);
        int max = getMax() - getMin();
        if (this.mKeyProgressIncrement == 0 || max / this.mKeyProgressIncrement > 20) {
            setKeyProgressIncrement(java.lang.Math.max(1, java.lang.Math.round(max / 20.0f)));
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected boolean verifyDrawable(android.graphics.drawable.Drawable drawable) {
        return drawable == this.mThumb || drawable == this.mTickMark || super.verifyDrawable(drawable);
    }

    @Override // android.widget.ProgressBar, android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.mThumb != null) {
            this.mThumb.jumpToCurrentState();
        }
        if (this.mTickMark != null) {
            this.mTickMark.jumpToCurrentState();
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        android.graphics.drawable.Drawable progressDrawable = getProgressDrawable();
        if (progressDrawable != null && this.mDisabledAlpha < 1.0f) {
            progressDrawable.setAlpha(isEnabled() ? 255 : (int) (this.mDisabledAlpha * 255.0f));
        }
        android.graphics.drawable.Drawable drawable = this.mThumb;
        if (drawable != null && drawable.isStateful() && drawable.setState(getDrawableState())) {
            invalidateDrawable(drawable);
        }
        android.graphics.drawable.Drawable drawable2 = this.mTickMark;
        if (drawable2 != null && drawable2.isStateful() && drawable2.setState(getDrawableState())) {
            invalidateDrawable(drawable2);
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        if (this.mThumb != null) {
            this.mThumb.setHotspot(f, f2);
        }
    }

    @Override // android.widget.ProgressBar
    void onVisualProgressChanged(int i, float f) {
        android.graphics.drawable.Drawable drawable;
        super.onVisualProgressChanged(i, f);
        if (i == 16908301 && (drawable = this.mThumb) != null) {
            setThumbPos(getWidth(), drawable, f, Integer.MIN_VALUE);
            invalidate();
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        updateThumbAndTrackPos(i, i2);
    }

    private void updateThumbAndTrackPos(int i, int i2) {
        int i3;
        int i4;
        int i5 = (i2 - this.mPaddingTop) - this.mPaddingBottom;
        android.graphics.drawable.Drawable currentDrawable = getCurrentDrawable();
        android.graphics.drawable.Drawable drawable = this.mThumb;
        int min = java.lang.Math.min(this.mMaxHeight, i5);
        int intrinsicHeight = drawable == null ? 0 : drawable.getIntrinsicHeight();
        if (intrinsicHeight > min) {
            i4 = (i5 - intrinsicHeight) / 2;
            i3 = ((intrinsicHeight - min) / 2) + i4;
        } else {
            int i6 = (i5 - min) / 2;
            int i7 = ((min - intrinsicHeight) / 2) + i6;
            i3 = i6;
            i4 = i7;
        }
        if (currentDrawable != null) {
            currentDrawable.setBounds(0, i3, (i - this.mPaddingRight) - this.mPaddingLeft, min + i3);
        }
        if (drawable != null) {
            setThumbPos(i, drawable, getScale(), i4);
        }
    }

    private float getScale() {
        int max = getMax() - getMin();
        if (max > 0) {
            return (getProgress() - r0) / max;
        }
        return 0.0f;
    }

    private void setThumbPos(int i, android.graphics.drawable.Drawable drawable, float f, int i2) {
        int i3;
        int i4 = (i - this.mPaddingLeft) - this.mPaddingRight;
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        int i5 = (i4 - intrinsicWidth) + (this.mThumbOffset * 2);
        int i6 = (int) ((f * i5) + 0.5f);
        if (i2 == Integer.MIN_VALUE) {
            android.graphics.Rect bounds = drawable.getBounds();
            int i7 = bounds.top;
            i3 = bounds.bottom;
            i2 = i7;
        } else {
            i3 = intrinsicHeight + i2;
        }
        if (isLayoutRtl() && this.mMirrorForRtl) {
            i6 = i5 - i6;
        }
        int i8 = intrinsicWidth + i6;
        android.graphics.drawable.Drawable background = getBackground();
        if (background != null) {
            int i9 = this.mPaddingLeft - this.mThumbOffset;
            int i10 = this.mPaddingTop;
            background.setHotspotBounds(i6 + i9, i2 + i10, i9 + i8, i10 + i3);
        }
        drawable.setBounds(i6, i2, i8, i3);
        updateGestureExclusionRects();
    }

    @Override // android.view.View
    public void setSystemGestureExclusionRects(java.util.List<android.graphics.Rect> list) {
        com.android.internal.util.Preconditions.checkNotNull(list, "rects must not be null");
        this.mUserGestureExclusionRects = list;
        updateGestureExclusionRects();
    }

    private void updateGestureExclusionRects() {
        android.graphics.drawable.Drawable drawable = this.mThumb;
        if (drawable == null) {
            super.setSystemGestureExclusionRects(this.mUserGestureExclusionRects);
            return;
        }
        this.mGestureExclusionRects.clear();
        drawable.copyBounds(this.mThumbRect);
        this.mThumbRect.offset(this.mPaddingLeft - this.mThumbOffset, this.mPaddingTop);
        growRectTo(this.mThumbRect, java.lang.Math.min(getHeight(), this.mThumbExclusionMaxSize));
        this.mGestureExclusionRects.add(this.mThumbRect);
        this.mGestureExclusionRects.addAll(this.mUserGestureExclusionRects);
        super.setSystemGestureExclusionRects(this.mGestureExclusionRects);
    }

    public void growRectTo(android.graphics.Rect rect, int i) {
        int height = i - rect.height();
        if (height > 0) {
            rect.top -= (height + 1) / 2;
            rect.bottom += height / 2;
        }
        int width = i - rect.width();
        if (width > 0) {
            rect.left -= (width + 1) / 2;
            rect.right += width / 2;
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    public void onResolveDrawables(int i) {
        super.onResolveDrawables(i);
        if (this.mThumb != null) {
            this.mThumb.setLayoutDirection(i);
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected synchronized void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);
        drawThumb(canvas);
    }

    @Override // android.widget.ProgressBar
    void drawTrack(android.graphics.Canvas canvas) {
        android.graphics.drawable.Drawable drawable = this.mThumb;
        if (drawable != null && this.mSplitTrack) {
            android.graphics.Insets opticalInsets = drawable.getOpticalInsets();
            android.graphics.Rect rect = this.mTempRect;
            drawable.copyBounds(rect);
            rect.offset(this.mPaddingLeft - this.mThumbOffset, this.mPaddingTop);
            rect.left += opticalInsets.left;
            rect.right -= opticalInsets.right;
            int save = canvas.save();
            canvas.clipRect(rect, android.graphics.Region.Op.DIFFERENCE);
            super.drawTrack(canvas);
            drawTickMarks(canvas);
            canvas.restoreToCount(save);
            return;
        }
        super.drawTrack(canvas);
        drawTickMarks(canvas);
    }

    protected void drawTickMarks(android.graphics.Canvas canvas) {
        if (this.mTickMark != null) {
            int max = getMax() - getMin();
            if (max > 1) {
                int intrinsicWidth = this.mTickMark.getIntrinsicWidth();
                int intrinsicHeight = this.mTickMark.getIntrinsicHeight();
                int i = intrinsicWidth >= 0 ? intrinsicWidth / 2 : 1;
                int i2 = intrinsicHeight >= 0 ? intrinsicHeight / 2 : 1;
                this.mTickMark.setBounds(-i, -i2, i, i2);
                float width = ((getWidth() - this.mPaddingLeft) - this.mPaddingRight) / max;
                int save = canvas.save();
                canvas.translate(this.mPaddingLeft, getHeight() / 2);
                for (int i3 = 0; i3 <= max; i3++) {
                    this.mTickMark.draw(canvas);
                    canvas.translate(width, 0.0f);
                }
                canvas.restoreToCount(save);
            }
        }
    }

    void drawThumb(android.graphics.Canvas canvas) {
        if (this.mThumb != null) {
            int save = canvas.save();
            canvas.translate(this.mPaddingLeft - this.mThumbOffset, this.mPaddingTop);
            this.mThumb.draw(canvas);
            canvas.restoreToCount(save);
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected synchronized void onMeasure(int i, int i2) {
        int i3;
        int i4;
        android.graphics.drawable.Drawable currentDrawable = getCurrentDrawable();
        int intrinsicHeight = this.mThumb == null ? 0 : this.mThumb.getIntrinsicHeight();
        if (currentDrawable == null) {
            i3 = 0;
            i4 = 0;
        } else {
            i4 = java.lang.Math.max(this.mMinWidth, java.lang.Math.min(this.mMaxWidth, currentDrawable.getIntrinsicWidth()));
            i3 = java.lang.Math.max(intrinsicHeight, java.lang.Math.max(this.mMinHeight, java.lang.Math.min(this.mMaxHeight, currentDrawable.getIntrinsicHeight())));
        }
        setMeasuredDimension(resolveSizeAndState(i4 + this.mPaddingLeft + this.mPaddingRight, i, 0), resolveSizeAndState(i3 + this.mPaddingTop + this.mPaddingBottom, i2, 0));
    }

    @Override // android.view.View
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        if (!this.mIsUserSeekable || !isEnabled()) {
            return false;
        }
        switch (motionEvent.getAction()) {
            case 0:
                if (this.mThumb != null) {
                    float width = (getWidth() - this.mPaddingLeft) - this.mPaddingRight;
                    this.mTouchThumbOffset = ((getProgress() - getMin()) / (getMax() - getMin())) - ((motionEvent.getX() - this.mPaddingLeft) / width);
                    if (java.lang.Math.abs(this.mTouchThumbOffset * width) > getThumbOffset()) {
                        this.mTouchThumbOffset = 0.0f;
                    }
                }
                if (isInScrollingContainer()) {
                    this.mTouchDownX = motionEvent.getX();
                    return true;
                }
                startDrag(motionEvent);
                return true;
            case 1:
                if (this.mIsDragging) {
                    trackTouchEvent(motionEvent);
                    onStopTrackingTouch();
                    setPressed(false);
                } else {
                    onStartTrackingTouch();
                    trackTouchEvent(motionEvent);
                    onStopTrackingTouch();
                }
                invalidate();
                return true;
            case 2:
                if (this.mIsDragging) {
                    trackTouchEvent(motionEvent);
                    return true;
                }
                if (java.lang.Math.abs(motionEvent.getX() - this.mTouchDownX) > this.mScaledTouchSlop) {
                    startDrag(motionEvent);
                    return true;
                }
                return true;
            case 3:
                if (this.mIsDragging) {
                    onStopTrackingTouch();
                    setPressed(false);
                }
                invalidate();
                return true;
            default:
                return true;
        }
    }

    private void startDrag(android.view.MotionEvent motionEvent) {
        setPressed(true);
        if (this.mThumb != null) {
            invalidate(this.mThumb.getBounds());
        }
        onStartTrackingTouch();
        trackTouchEvent(motionEvent);
        attemptClaimDrag();
    }

    private void setHotspot(float f, float f2) {
        android.graphics.drawable.Drawable background = getBackground();
        if (background != null) {
            background.setHotspot(f, f2);
        }
    }

    private void trackTouchEvent(android.view.MotionEvent motionEvent) {
        int round = java.lang.Math.round(motionEvent.getX());
        int round2 = java.lang.Math.round(motionEvent.getY());
        int width = getWidth();
        int i = (width - this.mPaddingLeft) - this.mPaddingRight;
        float f = 1.0f;
        float f2 = 0.0f;
        if (isLayoutRtl() && this.mMirrorForRtl) {
            if (round > width - this.mPaddingRight) {
                f = 0.0f;
            } else if (round >= this.mPaddingLeft) {
                f = (((i - round) + this.mPaddingLeft) / i) + this.mTouchThumbOffset;
                f2 = this.mTouchProgressOffset;
            }
        } else if (round < this.mPaddingLeft) {
            f = 0.0f;
        } else if (round <= width - this.mPaddingRight) {
            f = ((round - this.mPaddingLeft) / i) + this.mTouchThumbOffset;
            f2 = this.mTouchProgressOffset;
        }
        setHotspot(round, round2);
        setProgressInternal(updateTouchProgress(getProgress(), java.lang.Math.round(f2 + (f * (getMax() - getMin())) + getMin())), true, false);
    }

    protected int updateTouchProgress(int i, int i2) {
        return i2;
    }

    private void attemptClaimDrag() {
        if (this.mParent != null) {
            this.mParent.requestDisallowInterceptTouchEvent(true);
        }
    }

    void onStartTrackingTouch() {
        this.mIsDragging = true;
    }

    void onStopTrackingTouch() {
        this.mIsDragging = false;
    }

    void onKeyChange() {
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, android.view.KeyEvent keyEvent) {
        if (isEnabled()) {
            int i2 = this.mKeyProgressIncrement;
            switch (i) {
                case 21:
                case 69:
                    i2 = -i2;
                case 22:
                case 70:
                case 81:
                    if (isLayoutRtl()) {
                        i2 = -i2;
                    }
                    if (setProgressInternal(getProgress() + i2, true, true)) {
                        onKeyChange();
                        return true;
                    }
                default:
                    return super.onKeyDown(i, keyEvent);
            }
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.widget.ProgressBar, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.AbsSeekBar.class.getName();
    }

    @Override // android.widget.ProgressBar, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (isEnabled()) {
            int progress = getProgress();
            if (progress > getMin()) {
                accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
            }
            if (progress < getMax()) {
                accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
            }
        }
    }

    @Override // android.view.View
    public boolean performAccessibilityActionInternal(int i, android.os.Bundle bundle) {
        if (super.performAccessibilityActionInternal(i, bundle)) {
            return true;
        }
        if (!isEnabled()) {
            return false;
        }
        switch (i) {
            case 4096:
            case 8192:
                if (!canUserSetProgress()) {
                    return false;
                }
                int max = java.lang.Math.max(1, java.lang.Math.round((getMax() - getMin()) / 20.0f));
                if (i == 8192) {
                    max = -max;
                }
                if (!setProgressInternal(getProgress() + max, true, true)) {
                    return false;
                }
                onKeyChange();
                return true;
            case 16908349:
                if (canUserSetProgress() && bundle != null && bundle.containsKey(android.view.accessibility.AccessibilityNodeInfo.ACTION_ARGUMENT_PROGRESS_VALUE)) {
                    return setProgressInternal((int) bundle.getFloat(android.view.accessibility.AccessibilityNodeInfo.ACTION_ARGUMENT_PROGRESS_VALUE), true, true);
                }
                return false;
            default:
                return false;
        }
    }

    boolean canUserSetProgress() {
        return !isIndeterminate() && isEnabled();
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        android.graphics.drawable.Drawable drawable = this.mThumb;
        if (drawable != null) {
            setThumbPos(getWidth(), drawable, getScale(), Integer.MIN_VALUE);
            invalidate();
        }
    }
}
