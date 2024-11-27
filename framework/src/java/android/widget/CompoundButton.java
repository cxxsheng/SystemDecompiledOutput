package android.widget;

/* loaded from: classes4.dex */
public abstract class CompoundButton extends android.widget.Button implements android.widget.Checkable {
    private boolean mBroadcasting;
    private android.graphics.BlendMode mButtonBlendMode;
    private android.graphics.drawable.Drawable mButtonDrawable;
    private android.content.res.ColorStateList mButtonTintList;
    private boolean mChecked;
    private boolean mCheckedFromResource;
    private java.lang.CharSequence mCustomStateDescription;
    private boolean mHasButtonBlendMode;
    private boolean mHasButtonTint;
    private android.widget.CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener;
    private android.widget.CompoundButton.OnCheckedChangeListener mOnCheckedChangeWidgetListener;
    private static final java.lang.String LOG_TAG = android.widget.CompoundButton.class.getSimpleName();
    private static final int[] CHECKED_STATE_SET = {16842912};

    public interface OnCheckedChangeListener {
        void onCheckedChanged(android.widget.CompoundButton compoundButton, boolean z);
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.CompoundButton> {
        private int mButtonBlendModeId;
        private int mButtonId;
        private int mButtonTintId;
        private int mButtonTintModeId;
        private int mCheckedId;
        private boolean mPropertiesMapped = false;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
            this.mButtonId = propertyMapper.mapObject("button", 16843015);
            this.mButtonBlendModeId = propertyMapper.mapObject("buttonBlendMode", 3);
            this.mButtonTintId = propertyMapper.mapObject("buttonTint", 16843887);
            this.mButtonTintModeId = propertyMapper.mapObject("buttonTintMode", 16843888);
            this.mCheckedId = propertyMapper.mapBoolean("checked", 16843014);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(android.widget.CompoundButton compoundButton, android.view.inspector.PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readObject(this.mButtonId, compoundButton.getButtonDrawable());
            propertyReader.readObject(this.mButtonBlendModeId, compoundButton.getButtonTintBlendMode());
            propertyReader.readObject(this.mButtonTintId, compoundButton.getButtonTintList());
            propertyReader.readObject(this.mButtonTintModeId, compoundButton.getButtonTintMode());
            propertyReader.readBoolean(this.mCheckedId, compoundButton.isChecked());
        }
    }

    public CompoundButton(android.content.Context context) {
        this(context, null);
    }

    public CompoundButton(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CompoundButton(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public CompoundButton(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mButtonTintList = null;
        this.mButtonBlendMode = null;
        this.mHasButtonTint = false;
        this.mHasButtonBlendMode = false;
        this.mCheckedFromResource = false;
        this.mCustomStateDescription = null;
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.CompoundButton, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.CompoundButton, attributeSet, obtainStyledAttributes, i, i2);
        android.graphics.drawable.Drawable drawable = obtainStyledAttributes.getDrawable(1);
        if (drawable != null) {
            lambda$setButtonIconAsync$1(drawable);
        }
        if (obtainStyledAttributes.hasValue(3)) {
            this.mButtonBlendMode = android.graphics.drawable.Drawable.parseBlendMode(obtainStyledAttributes.getInt(3, -1), this.mButtonBlendMode);
            this.mHasButtonBlendMode = true;
        }
        if (obtainStyledAttributes.hasValue(2)) {
            this.mButtonTintList = obtainStyledAttributes.getColorStateList(2);
            this.mHasButtonTint = true;
        }
        setChecked(obtainStyledAttributes.getBoolean(0, false));
        this.mCheckedFromResource = true;
        obtainStyledAttributes.recycle();
        applyButtonTint();
    }

    @Override // android.widget.Checkable
    public void toggle() {
        setChecked(!this.mChecked);
    }

    @Override // android.view.View
    public boolean performClick() {
        toggle();
        boolean performClick = super.performClick();
        if (!performClick) {
            playSoundEffect(0);
        }
        return performClick;
    }

    @Override // android.widget.Checkable
    @android.view.ViewDebug.ExportedProperty
    public boolean isChecked() {
        return this.mChecked;
    }

    protected java.lang.CharSequence getButtonStateDescription() {
        if (isChecked()) {
            return getResources().getString(com.android.internal.R.string.checked);
        }
        return getResources().getString(com.android.internal.R.string.not_checked);
    }

    @Override // android.view.View
    public void setStateDescription(java.lang.CharSequence charSequence) {
        this.mCustomStateDescription = charSequence;
        if (charSequence == null) {
            setDefaultStateDescription();
        } else {
            super.setStateDescription(charSequence);
        }
    }

    protected void setDefaultStateDescription() {
        if (this.mCustomStateDescription == null) {
            super.setStateDescription(getButtonStateDescription());
        }
    }

    @Override // android.widget.Checkable
    public void setChecked(boolean z) {
        if (this.mChecked != z) {
            this.mCheckedFromResource = false;
            this.mChecked = z;
            refreshDrawableState();
            if (this.mBroadcasting) {
                setDefaultStateDescription();
                return;
            }
            this.mBroadcasting = true;
            if (this.mOnCheckedChangeListener != null) {
                this.mOnCheckedChangeListener.onCheckedChanged(this, this.mChecked);
            }
            if (this.mOnCheckedChangeWidgetListener != null) {
                this.mOnCheckedChangeWidgetListener.onCheckedChanged(this, this.mChecked);
            }
            android.view.autofill.AutofillManager autofillManager = (android.view.autofill.AutofillManager) this.mContext.getSystemService(android.view.autofill.AutofillManager.class);
            if (autofillManager != null) {
                autofillManager.notifyValueChanged(this);
            }
            this.mBroadcasting = false;
        }
        setDefaultStateDescription();
    }

    public void setOnCheckedChangeListener(android.widget.CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.mOnCheckedChangeListener = onCheckedChangeListener;
    }

    void setOnCheckedChangeWidgetListener(android.widget.CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.mOnCheckedChangeWidgetListener = onCheckedChangeListener;
    }

    @android.view.RemotableViewMethod(asyncImpl = "setButtonDrawableAsync")
    public void setButtonDrawable(int i) {
        android.graphics.drawable.Drawable drawable;
        if (i != 0) {
            drawable = getContext().getDrawable(i);
        } else {
            drawable = null;
        }
        lambda$setButtonIconAsync$1(drawable);
    }

    public java.lang.Runnable setButtonDrawableAsync(int i) {
        final android.graphics.drawable.Drawable drawable = i == 0 ? null : getContext().getDrawable(i);
        return new java.lang.Runnable() { // from class: android.widget.CompoundButton$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                android.widget.CompoundButton.this.lambda$setButtonDrawableAsync$0(drawable);
            }
        };
    }

    /* renamed from: setButtonDrawable, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$setButtonIconAsync$1(android.graphics.drawable.Drawable drawable) {
        if (this.mButtonDrawable != drawable) {
            if (this.mButtonDrawable != null) {
                this.mButtonDrawable.setCallback(null);
                unscheduleDrawable(this.mButtonDrawable);
            }
            this.mButtonDrawable = drawable;
            if (drawable != null) {
                drawable.setCallback(this);
                drawable.setLayoutDirection(getLayoutDirection());
                if (drawable.isStateful()) {
                    drawable.setState(getDrawableState());
                }
                drawable.setVisible(getVisibility() == 0, false);
                setMinHeight(drawable.getIntrinsicHeight());
                applyButtonTint();
            }
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onResolveDrawables(int i) {
        super.onResolveDrawables(i);
        if (this.mButtonDrawable != null) {
            this.mButtonDrawable.setLayoutDirection(i);
        }
    }

    public android.graphics.drawable.Drawable getButtonDrawable() {
        return this.mButtonDrawable;
    }

    @android.view.RemotableViewMethod(asyncImpl = "setButtonIconAsync")
    public void setButtonIcon(android.graphics.drawable.Icon icon) {
        lambda$setButtonIconAsync$1(icon == null ? null : icon.loadDrawable(getContext()));
    }

    public java.lang.Runnable setButtonIconAsync(android.graphics.drawable.Icon icon) {
        final android.graphics.drawable.Drawable loadDrawable = icon == null ? null : icon.loadDrawable(getContext());
        return new java.lang.Runnable() { // from class: android.widget.CompoundButton$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.widget.CompoundButton.this.lambda$setButtonIconAsync$1(loadDrawable);
            }
        };
    }

    @android.view.RemotableViewMethod
    public void setButtonTintList(android.content.res.ColorStateList colorStateList) {
        this.mButtonTintList = colorStateList;
        this.mHasButtonTint = true;
        applyButtonTint();
    }

    public android.content.res.ColorStateList getButtonTintList() {
        return this.mButtonTintList;
    }

    public void setButtonTintMode(android.graphics.PorterDuff.Mode mode) {
        setButtonTintBlendMode(mode != null ? android.graphics.BlendMode.fromValue(mode.nativeInt) : null);
    }

    @android.view.RemotableViewMethod
    public void setButtonTintBlendMode(android.graphics.BlendMode blendMode) {
        this.mButtonBlendMode = blendMode;
        this.mHasButtonBlendMode = true;
        applyButtonTint();
    }

    public android.graphics.PorterDuff.Mode getButtonTintMode() {
        if (this.mButtonBlendMode != null) {
            return android.graphics.BlendMode.blendModeToPorterDuffMode(this.mButtonBlendMode);
        }
        return null;
    }

    public android.graphics.BlendMode getButtonTintBlendMode() {
        return this.mButtonBlendMode;
    }

    private void applyButtonTint() {
        if (this.mButtonDrawable != null) {
            if (this.mHasButtonTint || this.mHasButtonBlendMode) {
                this.mButtonDrawable = this.mButtonDrawable.mutate();
                if (this.mHasButtonTint) {
                    this.mButtonDrawable.setTintList(this.mButtonTintList);
                }
                if (this.mHasButtonBlendMode) {
                    this.mButtonDrawable.setTintBlendMode(this.mButtonBlendMode);
                }
                if (this.mButtonDrawable.isStateful()) {
                    this.mButtonDrawable.setState(getDrawableState());
                }
            }
        }
    }

    @Override // android.widget.Button, android.widget.TextView, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.CompoundButton.class.getName();
    }

    @Override // android.widget.TextView, android.view.View
    public void onInitializeAccessibilityEventInternal(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEventInternal(accessibilityEvent);
        accessibilityEvent.setChecked(this.mChecked);
    }

    @Override // android.widget.TextView, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        accessibilityNodeInfo.setCheckable(true);
        accessibilityNodeInfo.setChecked(this.mChecked);
    }

    @Override // android.widget.TextView
    public int getCompoundPaddingLeft() {
        android.graphics.drawable.Drawable drawable;
        int compoundPaddingLeft = super.getCompoundPaddingLeft();
        if (!isLayoutRtl() && (drawable = this.mButtonDrawable) != null) {
            return compoundPaddingLeft + drawable.getIntrinsicWidth();
        }
        return compoundPaddingLeft;
    }

    @Override // android.widget.TextView
    public int getCompoundPaddingRight() {
        android.graphics.drawable.Drawable drawable;
        int compoundPaddingRight = super.getCompoundPaddingRight();
        if (isLayoutRtl() && (drawable = this.mButtonDrawable) != null) {
            return compoundPaddingRight + drawable.getIntrinsicWidth();
        }
        return compoundPaddingRight;
    }

    @Override // android.widget.TextView
    public int getHorizontalOffsetForDrawables() {
        android.graphics.drawable.Drawable drawable = this.mButtonDrawable;
        if (drawable != null) {
            return drawable.getIntrinsicWidth();
        }
        return 0;
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(android.graphics.Canvas canvas) {
        int height;
        android.graphics.drawable.Drawable drawable = this.mButtonDrawable;
        if (drawable != null) {
            int gravity = getGravity() & 112;
            int intrinsicHeight = drawable.getIntrinsicHeight();
            int intrinsicWidth = drawable.getIntrinsicWidth();
            switch (gravity) {
                case 16:
                    height = (getHeight() - intrinsicHeight) / 2;
                    break;
                case 80:
                    height = getHeight() - intrinsicHeight;
                    break;
                default:
                    height = 0;
                    break;
            }
            int i = intrinsicHeight + height;
            int width = isLayoutRtl() ? getWidth() - intrinsicWidth : 0;
            if (isLayoutRtl()) {
                intrinsicWidth = getWidth();
            }
            drawable.setBounds(width, height, intrinsicWidth, i);
            android.graphics.drawable.Drawable background = getBackground();
            if (background != null) {
                background.setHotspotBounds(width, height, intrinsicWidth, i);
            }
        }
        super.onDraw(canvas);
        if (drawable != null) {
            int i2 = this.mScrollX;
            int i3 = this.mScrollY;
            if (i2 == 0 && i3 == 0) {
                drawable.draw(canvas);
                return;
            }
            canvas.translate(i2, i3);
            drawable.draw(canvas);
            canvas.translate(-i2, -i3);
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
        if (isChecked()) {
            mergeDrawableStates(onCreateDrawableState, CHECKED_STATE_SET);
        }
        return onCreateDrawableState;
    }

    @Override // android.widget.TextView, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        android.graphics.drawable.Drawable drawable = this.mButtonDrawable;
        if (drawable != null && drawable.isStateful() && drawable.setState(getDrawableState())) {
            invalidateDrawable(drawable);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        if (this.mButtonDrawable != null) {
            this.mButtonDrawable.setHotspot(f, f2);
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected boolean verifyDrawable(android.graphics.drawable.Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mButtonDrawable;
    }

    @Override // android.widget.TextView, android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.mButtonDrawable != null) {
            this.mButtonDrawable.jumpToCurrentState();
        }
    }

    static class SavedState extends android.view.View.BaseSavedState {
        public static final android.os.Parcelable.Creator<android.widget.CompoundButton.SavedState> CREATOR = new android.os.Parcelable.Creator<android.widget.CompoundButton.SavedState>() { // from class: android.widget.CompoundButton.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.widget.CompoundButton.SavedState createFromParcel(android.os.Parcel parcel) {
                return new android.widget.CompoundButton.SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.widget.CompoundButton.SavedState[] newArray(int i) {
                return new android.widget.CompoundButton.SavedState[i];
            }
        };
        boolean checked;

        SavedState(android.os.Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(android.os.Parcel parcel) {
            super(parcel);
            this.checked = ((java.lang.Boolean) parcel.readValue(null)).booleanValue();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeValue(java.lang.Boolean.valueOf(this.checked));
        }

        public java.lang.String toString() {
            return "CompoundButton.SavedState{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " checked=" + this.checked + "}";
        }
    }

    @Override // android.widget.TextView, android.view.View
    public android.os.Parcelable onSaveInstanceState() {
        android.widget.CompoundButton.SavedState savedState = new android.widget.CompoundButton.SavedState(super.onSaveInstanceState());
        savedState.checked = isChecked();
        return savedState;
    }

    @Override // android.widget.TextView, android.view.View
    public void onRestoreInstanceState(android.os.Parcelable parcelable) {
        android.widget.CompoundButton.SavedState savedState = (android.widget.CompoundButton.SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setChecked(savedState.checked);
        requestLayout();
    }

    @Override // android.widget.TextView, android.view.View
    protected void encodeProperties(android.view.ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("checked", isChecked());
    }

    @Override // android.widget.TextView, android.view.View
    protected void onProvideStructure(android.view.ViewStructure viewStructure, int i, int i2) {
        super.onProvideStructure(viewStructure, i, i2);
        if (i == 1) {
            viewStructure.setDataIsSensitive(!this.mCheckedFromResource);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void autofill(android.view.autofill.AutofillValue autofillValue) {
        if (isEnabled()) {
            if (!autofillValue.isToggle()) {
                android.util.Log.w(LOG_TAG, autofillValue + " could not be autofilled into " + this);
            } else {
                setChecked(autofillValue.getToggleValue());
            }
        }
    }

    @Override // android.widget.TextView, android.view.View
    public int getAutofillType() {
        return isEnabled() ? 2 : 0;
    }

    @Override // android.widget.TextView, android.view.View
    public android.view.autofill.AutofillValue getAutofillValue() {
        if (isEnabled()) {
            return android.view.autofill.AutofillValue.forToggle(isChecked());
        }
        return null;
    }
}
