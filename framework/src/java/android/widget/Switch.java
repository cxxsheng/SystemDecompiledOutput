package android.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes4.dex */
public class Switch extends android.widget.CompoundButton {
    private static final int MONOSPACE = 3;
    private static final int SANS = 1;
    private static final int SERIF = 2;
    private static final int THUMB_ANIMATION_DURATION = 250;
    private static final int TOUCH_MODE_DOWN = 1;
    private static final int TOUCH_MODE_DRAGGING = 2;
    private static final int TOUCH_MODE_IDLE = 0;
    private boolean mHasThumbTint;
    private boolean mHasThumbTintMode;
    private boolean mHasTrackTint;
    private boolean mHasTrackTintMode;
    private int mMinFlingVelocity;
    private android.text.Layout mOffLayout;
    private android.text.Layout mOnLayout;
    private android.animation.ObjectAnimator mPositionAnimator;
    private boolean mShowText;
    private boolean mSplitTrack;
    private int mSwitchBottom;
    private int mSwitchHeight;
    private int mSwitchLeft;
    private int mSwitchMinWidth;
    private int mSwitchPadding;
    private int mSwitchRight;
    private int mSwitchTop;
    private android.text.method.TransformationMethod2 mSwitchTransformationMethod;
    private int mSwitchWidth;
    private final android.graphics.Rect mTempRect;
    private android.content.res.ColorStateList mTextColors;
    private java.lang.CharSequence mTextOff;
    private java.lang.CharSequence mTextOn;
    private android.text.TextPaint mTextPaint;
    private android.graphics.BlendMode mThumbBlendMode;
    private android.graphics.drawable.Drawable mThumbDrawable;
    private float mThumbPosition;
    private int mThumbTextPadding;
    private android.content.res.ColorStateList mThumbTintList;
    private int mThumbWidth;
    private int mTouchMode;
    private int mTouchSlop;
    private float mTouchX;
    private float mTouchY;
    private android.graphics.BlendMode mTrackBlendMode;
    private android.graphics.drawable.Drawable mTrackDrawable;
    private android.content.res.ColorStateList mTrackTintList;
    private boolean mUseFallbackLineSpacing;
    private android.view.VelocityTracker mVelocityTracker;
    private static final int[] CHECKED_STATE_SET = {16842912};
    private static final android.util.FloatProperty<android.widget.Switch> THUMB_POS = new android.util.FloatProperty<android.widget.Switch>("thumbPos") { // from class: android.widget.Switch.1
        @Override // android.util.Property
        public java.lang.Float get(android.widget.Switch r1) {
            return java.lang.Float.valueOf(r1.mThumbPosition);
        }

        @Override // android.util.FloatProperty
        public void setValue(android.widget.Switch r1, float f) {
            r1.setThumbPosition(f);
        }
    };

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.Switch> {
        private boolean mPropertiesMapped = false;
        private int mShowTextId;
        private int mSplitTrackId;
        private int mSwitchMinWidthId;
        private int mSwitchPaddingId;
        private int mTextOffId;
        private int mTextOnId;
        private int mThumbId;
        private int mThumbTextPaddingId;
        private int mThumbTintBlendModeId;
        private int mThumbTintId;
        private int mThumbTintModeId;
        private int mTrackId;
        private int mTrackTintBlendModeId;
        private int mTrackTintId;
        private int mTrackTintModeId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
            this.mShowTextId = propertyMapper.mapBoolean("showText", 16843949);
            this.mSplitTrackId = propertyMapper.mapBoolean("splitTrack", 16843852);
            this.mSwitchMinWidthId = propertyMapper.mapInt("switchMinWidth", 16843632);
            this.mSwitchPaddingId = propertyMapper.mapInt("switchPadding", 16843633);
            this.mTextOffId = propertyMapper.mapObject("textOff", 16843045);
            this.mTextOnId = propertyMapper.mapObject("textOn", 16843044);
            this.mThumbId = propertyMapper.mapObject("thumb", 16843074);
            this.mThumbTextPaddingId = propertyMapper.mapInt("thumbTextPadding", 16843634);
            this.mThumbTintId = propertyMapper.mapObject("thumbTint", 16843889);
            this.mThumbTintBlendModeId = propertyMapper.mapObject("thumbTintBlendMode", 10);
            this.mThumbTintModeId = propertyMapper.mapObject("thumbTintMode", 16843890);
            this.mTrackId = propertyMapper.mapObject("track", 16843631);
            this.mTrackTintId = propertyMapper.mapObject("trackTint", 16843993);
            this.mTrackTintBlendModeId = propertyMapper.mapObject("trackTintBlendMode", 13);
            this.mTrackTintModeId = propertyMapper.mapObject("trackTintMode", 16843994);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(android.widget.Switch r3, android.view.inspector.PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mShowTextId, r3.getShowText());
            propertyReader.readBoolean(this.mSplitTrackId, r3.getSplitTrack());
            propertyReader.readInt(this.mSwitchMinWidthId, r3.getSwitchMinWidth());
            propertyReader.readInt(this.mSwitchPaddingId, r3.getSwitchPadding());
            propertyReader.readObject(this.mTextOffId, r3.getTextOff());
            propertyReader.readObject(this.mTextOnId, r3.getTextOn());
            propertyReader.readObject(this.mThumbId, r3.getThumbDrawable());
            propertyReader.readInt(this.mThumbTextPaddingId, r3.getThumbTextPadding());
            propertyReader.readObject(this.mThumbTintId, r3.getThumbTintList());
            propertyReader.readObject(this.mThumbTintBlendModeId, r3.getThumbTintBlendMode());
            propertyReader.readObject(this.mThumbTintModeId, r3.getThumbTintMode());
            propertyReader.readObject(this.mTrackId, r3.getTrackDrawable());
            propertyReader.readObject(this.mTrackTintId, r3.getTrackTintList());
            propertyReader.readObject(this.mTrackTintBlendModeId, r3.getTrackTintBlendMode());
            propertyReader.readObject(this.mTrackTintModeId, r3.getTrackTintMode());
        }
    }

    public Switch(android.content.Context context) {
        this(context, null);
    }

    public Switch(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16843839);
    }

    public Switch(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public Switch(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mThumbTintList = null;
        this.mThumbBlendMode = null;
        this.mHasThumbTint = false;
        this.mHasThumbTintMode = false;
        this.mTrackTintList = null;
        this.mTrackBlendMode = null;
        this.mHasTrackTint = false;
        this.mHasTrackTintMode = false;
        this.mVelocityTracker = android.view.VelocityTracker.obtain();
        this.mTempRect = new android.graphics.Rect();
        this.mTextPaint = new android.text.TextPaint(1);
        android.content.res.Resources resources = getResources();
        this.mTextPaint.density = resources.getDisplayMetrics().density;
        this.mTextPaint.setCompatibilityScaling(resources.getCompatibilityInfo().applicationScale);
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.Switch, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.Switch, attributeSet, obtainStyledAttributes, i, i2);
        this.mThumbDrawable = obtainStyledAttributes.getDrawable(2);
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.setCallback(this);
        }
        this.mTrackDrawable = obtainStyledAttributes.getDrawable(4);
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.setCallback(this);
        }
        this.mTextOn = obtainStyledAttributes.getText(0);
        this.mTextOff = obtainStyledAttributes.getText(1);
        this.mShowText = obtainStyledAttributes.getBoolean(11, true);
        this.mThumbTextPadding = obtainStyledAttributes.getDimensionPixelSize(7, 0);
        this.mSwitchMinWidth = obtainStyledAttributes.getDimensionPixelSize(5, 0);
        this.mSwitchPadding = obtainStyledAttributes.getDimensionPixelSize(6, 0);
        this.mSplitTrack = obtainStyledAttributes.getBoolean(8, false);
        this.mUseFallbackLineSpacing = context.getApplicationInfo().targetSdkVersion >= 28;
        android.content.res.ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(9);
        if (colorStateList != null) {
            this.mThumbTintList = colorStateList;
            this.mHasThumbTint = true;
        }
        android.graphics.BlendMode parseBlendMode = android.graphics.drawable.Drawable.parseBlendMode(obtainStyledAttributes.getInt(10, -1), null);
        if (this.mThumbBlendMode != parseBlendMode) {
            this.mThumbBlendMode = parseBlendMode;
            this.mHasThumbTintMode = true;
        }
        if (this.mHasThumbTint || this.mHasThumbTintMode) {
            applyThumbTint();
        }
        android.content.res.ColorStateList colorStateList2 = obtainStyledAttributes.getColorStateList(12);
        if (colorStateList2 != null) {
            this.mTrackTintList = colorStateList2;
            this.mHasTrackTint = true;
        }
        android.graphics.BlendMode parseBlendMode2 = android.graphics.drawable.Drawable.parseBlendMode(obtainStyledAttributes.getInt(13, -1), null);
        if (this.mTrackBlendMode != parseBlendMode2) {
            this.mTrackBlendMode = parseBlendMode2;
            this.mHasTrackTintMode = true;
        }
        if (this.mHasTrackTint || this.mHasTrackTintMode) {
            applyTrackTint();
        }
        int resourceId = obtainStyledAttributes.getResourceId(3, 0);
        if (resourceId != 0) {
            setSwitchTextAppearance(context, resourceId);
        }
        obtainStyledAttributes.recycle();
        android.view.ViewConfiguration viewConfiguration = android.view.ViewConfiguration.get(context);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        refreshDrawableState();
        setDefaultStateDescription();
        setChecked(isChecked());
    }

    public void setSwitchTextAppearance(android.content.Context context, int i) {
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(i, com.android.internal.R.styleable.TextAppearance);
        android.content.res.ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(3);
        if (colorStateList != null) {
            this.mTextColors = colorStateList;
        } else {
            this.mTextColors = getTextColors();
        }
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(0, 0);
        if (dimensionPixelSize != 0) {
            float f = dimensionPixelSize;
            if (f != this.mTextPaint.getTextSize()) {
                this.mTextPaint.setTextSize(f);
                requestLayout();
            }
        }
        setSwitchTypefaceByIndex(obtainStyledAttributes.getInt(1, -1), obtainStyledAttributes.getInt(2, -1));
        if (obtainStyledAttributes.getBoolean(11, false)) {
            this.mSwitchTransformationMethod = new android.text.method.AllCapsTransformationMethod(getContext());
            this.mSwitchTransformationMethod.setLengthChangesAllowed(true);
        } else {
            this.mSwitchTransformationMethod = null;
        }
        obtainStyledAttributes.recycle();
    }

    private void setSwitchTypefaceByIndex(int i, int i2) {
        android.graphics.Typeface typeface;
        switch (i) {
            case 1:
                typeface = android.graphics.Typeface.SANS_SERIF;
                break;
            case 2:
                typeface = android.graphics.Typeface.SERIF;
                break;
            case 3:
                typeface = android.graphics.Typeface.MONOSPACE;
                break;
            default:
                typeface = null;
                break;
        }
        setSwitchTypeface(typeface, i2);
    }

    public void setSwitchTypeface(android.graphics.Typeface typeface, int i) {
        android.graphics.Typeface create;
        if (i > 0) {
            if (typeface == null) {
                create = android.graphics.Typeface.defaultFromStyle(i);
            } else {
                create = android.graphics.Typeface.create(typeface, i);
            }
            setSwitchTypeface(create);
            int i2 = (~(create != null ? create.getStyle() : 0)) & i;
            this.mTextPaint.setFakeBoldText((i2 & 1) != 0);
            this.mTextPaint.setTextSkewX((i2 & 2) != 0 ? -0.25f : 0.0f);
            return;
        }
        this.mTextPaint.setFakeBoldText(false);
        this.mTextPaint.setTextSkewX(0.0f);
        setSwitchTypeface(typeface);
    }

    public void setSwitchTypeface(android.graphics.Typeface typeface) {
        if (this.mTextPaint.getTypeface() != typeface) {
            this.mTextPaint.setTypeface(typeface);
            requestLayout();
            invalidate();
        }
    }

    @android.view.RemotableViewMethod
    public void setSwitchPadding(int i) {
        this.mSwitchPadding = i;
        requestLayout();
    }

    public int getSwitchPadding() {
        return this.mSwitchPadding;
    }

    @android.view.RemotableViewMethod
    public void setSwitchMinWidth(int i) {
        this.mSwitchMinWidth = i;
        requestLayout();
    }

    public int getSwitchMinWidth() {
        return this.mSwitchMinWidth;
    }

    @android.view.RemotableViewMethod
    public void setThumbTextPadding(int i) {
        this.mThumbTextPadding = i;
        requestLayout();
    }

    public int getThumbTextPadding() {
        return this.mThumbTextPadding;
    }

    /* renamed from: setTrackDrawable, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$setTrackResourceAsync$0(android.graphics.drawable.Drawable drawable) {
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.setCallback(null);
        }
        this.mTrackDrawable = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
        requestLayout();
    }

    @android.view.RemotableViewMethod(asyncImpl = "setTrackResourceAsync")
    public void setTrackResource(int i) {
        lambda$setTrackResourceAsync$0(getContext().getDrawable(i));
    }

    public java.lang.Runnable setTrackResourceAsync(int i) {
        final android.graphics.drawable.Drawable drawable = i == 0 ? null : getContext().getDrawable(i);
        return new java.lang.Runnable() { // from class: android.widget.Switch$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                android.widget.Switch.this.lambda$setTrackResourceAsync$0(drawable);
            }
        };
    }

    public android.graphics.drawable.Drawable getTrackDrawable() {
        return this.mTrackDrawable;
    }

    @android.view.RemotableViewMethod(asyncImpl = "setTrackIconAsync")
    public void setTrackIcon(android.graphics.drawable.Icon icon) {
        lambda$setTrackResourceAsync$0(icon == null ? null : icon.loadDrawable(getContext()));
    }

    public java.lang.Runnable setTrackIconAsync(android.graphics.drawable.Icon icon) {
        final android.graphics.drawable.Drawable loadDrawable = icon == null ? null : icon.loadDrawable(getContext());
        return new java.lang.Runnable() { // from class: android.widget.Switch$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                android.widget.Switch.this.lambda$setTrackIconAsync$1(loadDrawable);
            }
        };
    }

    @android.view.RemotableViewMethod
    public void setTrackTintList(android.content.res.ColorStateList colorStateList) {
        this.mTrackTintList = colorStateList;
        this.mHasTrackTint = true;
        applyTrackTint();
    }

    public android.content.res.ColorStateList getTrackTintList() {
        return this.mTrackTintList;
    }

    public void setTrackTintMode(android.graphics.PorterDuff.Mode mode) {
        setTrackTintBlendMode(mode != null ? android.graphics.BlendMode.fromValue(mode.nativeInt) : null);
    }

    @android.view.RemotableViewMethod
    public void setTrackTintBlendMode(android.graphics.BlendMode blendMode) {
        this.mTrackBlendMode = blendMode;
        this.mHasTrackTintMode = true;
        applyTrackTint();
    }

    public android.graphics.PorterDuff.Mode getTrackTintMode() {
        android.graphics.BlendMode trackTintBlendMode = getTrackTintBlendMode();
        if (trackTintBlendMode != null) {
            return android.graphics.BlendMode.blendModeToPorterDuffMode(trackTintBlendMode);
        }
        return null;
    }

    public android.graphics.BlendMode getTrackTintBlendMode() {
        return this.mTrackBlendMode;
    }

    private void applyTrackTint() {
        if (this.mTrackDrawable != null) {
            if (this.mHasTrackTint || this.mHasTrackTintMode) {
                this.mTrackDrawable = this.mTrackDrawable.mutate();
                if (this.mHasTrackTint) {
                    this.mTrackDrawable.setTintList(this.mTrackTintList);
                }
                if (this.mHasTrackTintMode) {
                    this.mTrackDrawable.setTintBlendMode(this.mTrackBlendMode);
                }
                if (this.mTrackDrawable.isStateful()) {
                    this.mTrackDrawable.setState(getDrawableState());
                }
            }
        }
    }

    /* renamed from: setThumbDrawable, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$setThumbResourceAsync$2(android.graphics.drawable.Drawable drawable) {
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.setCallback(null);
        }
        this.mThumbDrawable = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
        requestLayout();
    }

    @android.view.RemotableViewMethod(asyncImpl = "setThumbResourceAsync")
    public void setThumbResource(int i) {
        lambda$setThumbResourceAsync$2(getContext().getDrawable(i));
    }

    public java.lang.Runnable setThumbResourceAsync(int i) {
        final android.graphics.drawable.Drawable drawable = i == 0 ? null : getContext().getDrawable(i);
        return new java.lang.Runnable() { // from class: android.widget.Switch$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.widget.Switch.this.lambda$setThumbResourceAsync$2(drawable);
            }
        };
    }

    public android.graphics.drawable.Drawable getThumbDrawable() {
        return this.mThumbDrawable;
    }

    @android.view.RemotableViewMethod(asyncImpl = "setThumbIconAsync")
    public void setThumbIcon(android.graphics.drawable.Icon icon) {
        lambda$setThumbResourceAsync$2(icon == null ? null : icon.loadDrawable(getContext()));
    }

    public java.lang.Runnable setThumbIconAsync(android.graphics.drawable.Icon icon) {
        final android.graphics.drawable.Drawable loadDrawable = icon == null ? null : icon.loadDrawable(getContext());
        return new java.lang.Runnable() { // from class: android.widget.Switch$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                android.widget.Switch.this.lambda$setThumbIconAsync$3(loadDrawable);
            }
        };
    }

    @android.view.RemotableViewMethod
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

    @android.view.RemotableViewMethod
    public void setThumbTintBlendMode(android.graphics.BlendMode blendMode) {
        this.mThumbBlendMode = blendMode;
        this.mHasThumbTintMode = true;
        applyThumbTint();
    }

    public android.graphics.PorterDuff.Mode getThumbTintMode() {
        android.graphics.BlendMode thumbTintBlendMode = getThumbTintBlendMode();
        if (thumbTintBlendMode != null) {
            return android.graphics.BlendMode.blendModeToPorterDuffMode(thumbTintBlendMode);
        }
        return null;
    }

    public android.graphics.BlendMode getThumbTintBlendMode() {
        return this.mThumbBlendMode;
    }

    private void applyThumbTint() {
        if (this.mThumbDrawable != null) {
            if (this.mHasThumbTint || this.mHasThumbTintMode) {
                this.mThumbDrawable = this.mThumbDrawable.mutate();
                if (this.mHasThumbTint) {
                    this.mThumbDrawable.setTintList(this.mThumbTintList);
                }
                if (this.mHasThumbTintMode) {
                    this.mThumbDrawable.setTintBlendMode(this.mThumbBlendMode);
                }
                if (this.mThumbDrawable.isStateful()) {
                    this.mThumbDrawable.setState(getDrawableState());
                }
            }
        }
    }

    @android.view.RemotableViewMethod
    public void setSplitTrack(boolean z) {
        this.mSplitTrack = z;
        invalidate();
    }

    public boolean getSplitTrack() {
        return this.mSplitTrack;
    }

    public java.lang.CharSequence getTextOn() {
        return this.mTextOn;
    }

    @android.view.RemotableViewMethod
    public void setTextOn(java.lang.CharSequence charSequence) {
        this.mTextOn = charSequence;
        requestLayout();
        setDefaultStateDescription();
    }

    public java.lang.CharSequence getTextOff() {
        return this.mTextOff;
    }

    @android.view.RemotableViewMethod
    public void setTextOff(java.lang.CharSequence charSequence) {
        this.mTextOff = charSequence;
        requestLayout();
        setDefaultStateDescription();
    }

    @android.view.RemotableViewMethod
    public void setShowText(boolean z) {
        if (this.mShowText != z) {
            this.mShowText = z;
            requestLayout();
        }
    }

    public boolean getShowText() {
        return this.mShowText;
    }

    @Override // android.widget.TextView, android.view.View
    public void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int i5;
        if (this.mShowText) {
            if (this.mOnLayout == null) {
                this.mOnLayout = makeLayout(this.mTextOn);
            }
            if (this.mOffLayout == null) {
                this.mOffLayout = makeLayout(this.mTextOff);
            }
        }
        android.graphics.Rect rect = this.mTempRect;
        int i6 = 0;
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.getPadding(rect);
            i3 = (this.mThumbDrawable.getIntrinsicWidth() - rect.left) - rect.right;
            i4 = this.mThumbDrawable.getIntrinsicHeight();
        } else {
            i3 = 0;
            i4 = 0;
        }
        if (this.mShowText) {
            i5 = java.lang.Math.max(this.mOnLayout.getWidth(), this.mOffLayout.getWidth()) + (this.mThumbTextPadding * 2);
        } else {
            i5 = 0;
        }
        this.mThumbWidth = java.lang.Math.max(i5, i3);
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.getPadding(rect);
            i6 = this.mTrackDrawable.getIntrinsicHeight();
        } else {
            rect.setEmpty();
        }
        int i7 = rect.left;
        int i8 = rect.right;
        if (this.mThumbDrawable != null) {
            android.graphics.Insets opticalInsets = this.mThumbDrawable.getOpticalInsets();
            i7 = java.lang.Math.max(i7, opticalInsets.left);
            i8 = java.lang.Math.max(i8, opticalInsets.right);
        }
        int max = java.lang.Math.max(this.mSwitchMinWidth, (this.mThumbWidth * 2) + i7 + i8);
        int max2 = java.lang.Math.max(i6, i4);
        this.mSwitchWidth = max;
        this.mSwitchHeight = max2;
        super.onMeasure(i, i2);
        if (getMeasuredHeight() < max2) {
            setMeasuredDimension(getMeasuredWidthAndState(), max2);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onPopulateAccessibilityEventInternal(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEventInternal(accessibilityEvent);
        java.lang.CharSequence charSequence = isChecked() ? this.mTextOn : this.mTextOff;
        if (charSequence != null) {
            accessibilityEvent.getText().add(charSequence);
        }
    }

    private android.text.Layout makeLayout(java.lang.CharSequence charSequence) {
        if (this.mSwitchTransformationMethod != null) {
            charSequence = this.mSwitchTransformationMethod.getTransformation(charSequence, this);
        }
        return android.text.StaticLayout.Builder.obtain(charSequence, 0, charSequence.length(), this.mTextPaint, (int) java.lang.Math.ceil(android.text.Layout.getDesiredWidth(charSequence, 0, charSequence.length(), this.mTextPaint, getTextDirectionHeuristic()))).setUseLineSpacingFromFallbacks(this.mUseFallbackLineSpacing).build();
    }

    private boolean hitThumb(float f, float f2) {
        if (this.mThumbDrawable == null) {
            return false;
        }
        int thumbOffset = getThumbOffset();
        this.mThumbDrawable.getPadding(this.mTempRect);
        int i = this.mSwitchTop - this.mTouchSlop;
        int i2 = (this.mSwitchLeft + thumbOffset) - this.mTouchSlop;
        return f > ((float) i2) && f < ((float) ((((this.mThumbWidth + i2) + this.mTempRect.left) + this.mTempRect.right) + this.mTouchSlop)) && f2 > ((float) i) && f2 < ((float) (this.mSwitchBottom + this.mTouchSlop));
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        float f;
        this.mVelocityTracker.addMovement(motionEvent);
        switch (motionEvent.getActionMasked()) {
            case 0:
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                if (isEnabled() && hitThumb(x, y)) {
                    this.mTouchMode = 1;
                    this.mTouchX = x;
                    this.mTouchY = y;
                    break;
                }
                break;
            case 1:
            case 3:
                if (this.mTouchMode == 2) {
                    stopDrag(motionEvent);
                    super.onTouchEvent(motionEvent);
                    return true;
                }
                this.mTouchMode = 0;
                this.mVelocityTracker.clear();
                break;
            case 2:
                switch (this.mTouchMode) {
                    case 1:
                        float x2 = motionEvent.getX();
                        float y2 = motionEvent.getY();
                        if (java.lang.Math.abs(x2 - this.mTouchX) > this.mTouchSlop || java.lang.Math.abs(y2 - this.mTouchY) > this.mTouchSlop) {
                            this.mTouchMode = 2;
                            getParent().requestDisallowInterceptTouchEvent(true);
                            this.mTouchX = x2;
                            this.mTouchY = y2;
                            return true;
                        }
                        break;
                    case 2:
                        float x3 = motionEvent.getX();
                        int thumbScrollRange = getThumbScrollRange();
                        float f2 = x3 - this.mTouchX;
                        if (thumbScrollRange == 0) {
                            f = f2 > 0.0f ? 1.0f : -1.0f;
                        } else {
                            f = f2 / thumbScrollRange;
                        }
                        if (isLayoutRtl()) {
                            f = -f;
                        }
                        float constrain = android.util.MathUtils.constrain(this.mThumbPosition + f, 0.0f, 1.0f);
                        if (constrain != this.mThumbPosition) {
                            this.mTouchX = x3;
                            setThumbPosition(constrain);
                        }
                        return true;
                }
        }
        return super.onTouchEvent(motionEvent);
    }

    private void cancelSuperTouch(android.view.MotionEvent motionEvent) {
        android.view.MotionEvent obtain = android.view.MotionEvent.obtain(motionEvent);
        obtain.setAction(3);
        super.onTouchEvent(obtain);
        obtain.recycle();
    }

    private void stopDrag(android.view.MotionEvent motionEvent) {
        this.mTouchMode = 0;
        boolean z = true;
        boolean z2 = motionEvent.getAction() == 1 && isEnabled();
        boolean isChecked = isChecked();
        if (z2) {
            this.mVelocityTracker.computeCurrentVelocity(1000);
            float xVelocity = this.mVelocityTracker.getXVelocity();
            if (java.lang.Math.abs(xVelocity) > this.mMinFlingVelocity) {
                if (!isLayoutRtl() ? xVelocity <= 0.0f : xVelocity >= 0.0f) {
                    z = false;
                }
            } else {
                z = getTargetCheckedState();
            }
        } else {
            z = isChecked;
        }
        if (z != isChecked) {
            playSoundEffect(0);
        }
        setChecked(z);
        cancelSuperTouch(motionEvent);
    }

    private void animateThumbToCheckedState(boolean z) {
        this.mPositionAnimator = android.animation.ObjectAnimator.ofFloat(this, THUMB_POS, z ? 1.0f : 0.0f);
        this.mPositionAnimator.setDuration(250L);
        this.mPositionAnimator.setAutoCancel(true);
        this.mPositionAnimator.start();
    }

    private void cancelPositionAnimator() {
        if (this.mPositionAnimator != null) {
            this.mPositionAnimator.cancel();
        }
    }

    private boolean getTargetCheckedState() {
        return this.mThumbPosition > 0.5f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setThumbPosition(float f) {
        this.mThumbPosition = f;
        invalidate();
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public void toggle() {
        setChecked(!isChecked());
    }

    @Override // android.widget.CompoundButton
    protected java.lang.CharSequence getButtonStateDescription() {
        return isChecked() ? this.mTextOn == null ? getResources().getString(com.android.internal.R.string.capital_on) : this.mTextOn : this.mTextOff == null ? getResources().getString(com.android.internal.R.string.capital_off) : this.mTextOff;
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public void setChecked(boolean z) {
        super.setChecked(z);
        boolean isChecked = isChecked();
        if (isAttachedToWindow() && isLaidOut()) {
            animateThumbToCheckedState(isChecked);
        } else {
            cancelPositionAnimator();
            setThumbPosition(isChecked ? 1.0f : 0.0f);
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int width;
        int i6;
        int paddingTop;
        int i7;
        super.onLayout(z, i, i2, i3, i4);
        int i8 = 0;
        if (this.mThumbDrawable == null) {
            i5 = 0;
        } else {
            android.graphics.Rect rect = this.mTempRect;
            if (this.mTrackDrawable != null) {
                this.mTrackDrawable.getPadding(rect);
            } else {
                rect.setEmpty();
            }
            android.graphics.Insets opticalInsets = this.mThumbDrawable.getOpticalInsets();
            i5 = java.lang.Math.max(0, opticalInsets.left - rect.left);
            i8 = java.lang.Math.max(0, opticalInsets.right - rect.right);
        }
        if (isLayoutRtl()) {
            i6 = getPaddingLeft() + i5;
            width = ((this.mSwitchWidth + i6) - i5) - i8;
        } else {
            width = (getWidth() - getPaddingRight()) - i8;
            i6 = (width - this.mSwitchWidth) + i5 + i8;
        }
        switch (getGravity() & 112) {
            case 16:
                paddingTop = (((getPaddingTop() + getHeight()) - getPaddingBottom()) / 2) - (this.mSwitchHeight / 2);
                i7 = this.mSwitchHeight + paddingTop;
                break;
            case 80:
                i7 = getHeight() - getPaddingBottom();
                paddingTop = i7 - this.mSwitchHeight;
                break;
            default:
                paddingTop = getPaddingTop();
                i7 = this.mSwitchHeight + paddingTop;
                break;
        }
        this.mSwitchLeft = i6;
        this.mSwitchTop = paddingTop;
        this.mSwitchBottom = i7;
        this.mSwitchRight = width;
    }

    @Override // android.view.View
    public void draw(android.graphics.Canvas canvas) {
        android.graphics.Insets insets;
        int i;
        int i2;
        android.graphics.Rect rect = this.mTempRect;
        int i3 = this.mSwitchLeft;
        int i4 = this.mSwitchTop;
        int i5 = this.mSwitchRight;
        int i6 = this.mSwitchBottom;
        int thumbOffset = getThumbOffset() + i3;
        if (this.mThumbDrawable != null) {
            insets = this.mThumbDrawable.getOpticalInsets();
        } else {
            insets = android.graphics.Insets.NONE;
        }
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.getPadding(rect);
            thumbOffset += rect.left;
            if (insets == android.graphics.Insets.NONE) {
                i = i4;
                i2 = i6;
            } else {
                if (insets.left > rect.left) {
                    i3 += insets.left - rect.left;
                }
                if (insets.top <= rect.top) {
                    i = i4;
                } else {
                    i = (insets.top - rect.top) + i4;
                }
                if (insets.right > rect.right) {
                    i5 -= insets.right - rect.right;
                }
                if (insets.bottom <= rect.bottom) {
                    i2 = i6;
                } else {
                    i2 = i6 - (insets.bottom - rect.bottom);
                }
            }
            this.mTrackDrawable.setBounds(i3, i, i5, i2);
        }
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.getPadding(rect);
            int i7 = thumbOffset - rect.left;
            int i8 = thumbOffset + this.mThumbWidth + rect.right;
            this.mThumbDrawable.setBounds(i7, i4, i8, i6);
            android.graphics.drawable.Drawable background = getBackground();
            if (background != null) {
                background.setHotspotBounds(i7, i4, i8, i6);
            }
        }
        super.draw(canvas);
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    protected void onDraw(android.graphics.Canvas canvas) {
        int width;
        super.onDraw(canvas);
        android.graphics.Rect rect = this.mTempRect;
        android.graphics.drawable.Drawable drawable = this.mTrackDrawable;
        if (drawable != null) {
            drawable.getPadding(rect);
        } else {
            rect.setEmpty();
        }
        int i = this.mSwitchTop;
        int i2 = this.mSwitchBottom;
        int i3 = i + rect.top;
        int i4 = i2 - rect.bottom;
        android.graphics.drawable.Drawable drawable2 = this.mThumbDrawable;
        if (drawable != null) {
            if (this.mSplitTrack && drawable2 != null) {
                android.graphics.Insets opticalInsets = drawable2.getOpticalInsets();
                drawable2.copyBounds(rect);
                rect.left += opticalInsets.left;
                rect.right -= opticalInsets.right;
                int save = canvas.save();
                canvas.clipRect(rect, android.graphics.Region.Op.DIFFERENCE);
                drawable.draw(canvas);
                canvas.restoreToCount(save);
            } else {
                drawable.draw(canvas);
            }
        }
        int save2 = canvas.save();
        if (drawable2 != null) {
            drawable2.draw(canvas);
        }
        android.text.Layout layout = getTargetCheckedState() ? this.mOnLayout : this.mOffLayout;
        if (layout != null) {
            int[] drawableState = getDrawableState();
            if (this.mTextColors != null) {
                this.mTextPaint.setColor(this.mTextColors.getColorForState(drawableState, 0));
            }
            this.mTextPaint.drawableState = drawableState;
            if (drawable2 != null) {
                android.graphics.Rect bounds = drawable2.getBounds();
                width = bounds.left + bounds.right;
            } else {
                width = getWidth();
            }
            canvas.translate((width / 2) - (layout.getWidth() / 2), ((i3 + i4) / 2) - (layout.getHeight() / 2));
            layout.draw(canvas);
        }
        canvas.restoreToCount(save2);
    }

    @Override // android.widget.CompoundButton, android.widget.TextView
    public int getCompoundPaddingLeft() {
        if (!isLayoutRtl()) {
            return super.getCompoundPaddingLeft();
        }
        int compoundPaddingLeft = super.getCompoundPaddingLeft() + this.mSwitchWidth;
        if (!android.text.TextUtils.isEmpty(getText())) {
            return compoundPaddingLeft + this.mSwitchPadding;
        }
        return compoundPaddingLeft;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView
    public int getCompoundPaddingRight() {
        if (isLayoutRtl()) {
            return super.getCompoundPaddingRight();
        }
        int compoundPaddingRight = super.getCompoundPaddingRight() + this.mSwitchWidth;
        if (!android.text.TextUtils.isEmpty(getText())) {
            return compoundPaddingRight + this.mSwitchPadding;
        }
        return compoundPaddingRight;
    }

    private int getThumbOffset() {
        float f;
        if (isLayoutRtl()) {
            f = 1.0f - this.mThumbPosition;
        } else {
            f = this.mThumbPosition;
        }
        return (int) ((f * getThumbScrollRange()) + 0.5f);
    }

    private int getThumbScrollRange() {
        android.graphics.Insets insets;
        if (this.mTrackDrawable != null) {
            android.graphics.Rect rect = this.mTempRect;
            this.mTrackDrawable.getPadding(rect);
            if (this.mThumbDrawable != null) {
                insets = this.mThumbDrawable.getOpticalInsets();
            } else {
                insets = android.graphics.Insets.NONE;
            }
            return ((((this.mSwitchWidth - this.mThumbWidth) - rect.left) - rect.right) - insets.left) - insets.right;
        }
        return 0;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    protected int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
        if (isChecked()) {
            mergeDrawableStates(onCreateDrawableState, CHECKED_STATE_SET);
        }
        return onCreateDrawableState;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        android.graphics.drawable.Drawable drawable = this.mThumbDrawable;
        boolean z = false;
        if (drawable != null && drawable.isStateful()) {
            z = false | drawable.setState(drawableState);
        }
        android.graphics.drawable.Drawable drawable2 = this.mTrackDrawable;
        if (drawable2 != null && drawable2.isStateful()) {
            z |= drawable2.setState(drawableState);
        }
        if (z) {
            invalidate();
        }
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.setHotspot(f, f2);
        }
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.setHotspot(f, f2);
        }
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    protected boolean verifyDrawable(android.graphics.drawable.Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mThumbDrawable || drawable == this.mTrackDrawable;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.mThumbDrawable != null) {
            this.mThumbDrawable.jumpToCurrentState();
        }
        if (this.mTrackDrawable != null) {
            this.mTrackDrawable.jumpToCurrentState();
        }
        if (this.mPositionAnimator != null && this.mPositionAnimator.isStarted()) {
            this.mPositionAnimator.end();
            this.mPositionAnimator = null;
        }
    }

    @Override // android.widget.CompoundButton, android.widget.Button, android.widget.TextView, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.Switch.class.getName();
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    protected void onProvideStructure(android.view.ViewStructure viewStructure, int i, int i2) {
        java.lang.CharSequence charSequence = isChecked() ? this.mTextOn : this.mTextOff;
        if (!android.text.TextUtils.isEmpty(charSequence)) {
            java.lang.CharSequence text = viewStructure.getText();
            if (android.text.TextUtils.isEmpty(text)) {
                viewStructure.setText(charSequence);
                return;
            }
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(text).append(' ').append(charSequence);
            viewStructure.setText(sb);
        }
    }
}
