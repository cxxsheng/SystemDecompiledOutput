package android.widget;

/* loaded from: classes4.dex */
public class CheckedTextView extends android.widget.TextView implements android.widget.Checkable {
    private static final int[] CHECKED_STATE_SET = {16842912};
    private int mBasePadding;
    private android.graphics.BlendMode mCheckMarkBlendMode;
    private android.graphics.drawable.Drawable mCheckMarkDrawable;
    private int mCheckMarkGravity;
    private int mCheckMarkResource;
    private android.content.res.ColorStateList mCheckMarkTintList;
    private int mCheckMarkWidth;
    private boolean mChecked;
    private boolean mHasCheckMarkTint;
    private boolean mHasCheckMarkTintMode;
    private boolean mNeedRequestlayout;

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.CheckedTextView> {
        private int mCheckMarkId;
        private int mCheckMarkTintBlendModeId;
        private int mCheckMarkTintId;
        private int mCheckMarkTintModeId;
        private int mCheckedId;
        private boolean mPropertiesMapped = false;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
            this.mCheckMarkId = propertyMapper.mapObject("checkMark", 16843016);
            this.mCheckMarkTintId = propertyMapper.mapObject("checkMarkTint", 16843943);
            this.mCheckMarkTintBlendModeId = propertyMapper.mapObject("checkMarkTintBlendMode", 3);
            this.mCheckMarkTintModeId = propertyMapper.mapObject("checkMarkTintMode", 16843944);
            this.mCheckedId = propertyMapper.mapBoolean("checked", 16843014);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(android.widget.CheckedTextView checkedTextView, android.view.inspector.PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readObject(this.mCheckMarkId, checkedTextView.getCheckMarkDrawable());
            propertyReader.readObject(this.mCheckMarkTintId, checkedTextView.getCheckMarkTintList());
            propertyReader.readObject(this.mCheckMarkTintBlendModeId, checkedTextView.getCheckMarkTintBlendMode());
            propertyReader.readObject(this.mCheckMarkTintModeId, checkedTextView.getCheckMarkTintMode());
            propertyReader.readBoolean(this.mCheckedId, checkedTextView.isChecked());
        }
    }

    public CheckedTextView(android.content.Context context) {
        this(context, null);
    }

    public CheckedTextView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16843720);
    }

    public CheckedTextView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public CheckedTextView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mCheckMarkTintList = null;
        this.mCheckMarkBlendMode = null;
        this.mHasCheckMarkTint = false;
        this.mHasCheckMarkTintMode = false;
        this.mCheckMarkGravity = android.view.Gravity.END;
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.CheckedTextView, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.CheckedTextView, attributeSet, obtainStyledAttributes, i, i2);
        android.graphics.drawable.Drawable drawable = obtainStyledAttributes.getDrawable(1);
        if (drawable != null) {
            setCheckMarkDrawable(drawable);
        }
        if (obtainStyledAttributes.hasValue(3)) {
            this.mCheckMarkBlendMode = android.graphics.drawable.Drawable.parseBlendMode(obtainStyledAttributes.getInt(3, -1), this.mCheckMarkBlendMode);
            this.mHasCheckMarkTintMode = true;
        }
        if (obtainStyledAttributes.hasValue(2)) {
            this.mCheckMarkTintList = obtainStyledAttributes.getColorStateList(2);
            this.mHasCheckMarkTint = true;
        }
        this.mCheckMarkGravity = obtainStyledAttributes.getInt(4, android.view.Gravity.END);
        setChecked(obtainStyledAttributes.getBoolean(0, false));
        obtainStyledAttributes.recycle();
        applyCheckMarkTint();
    }

    @Override // android.widget.Checkable
    public void toggle() {
        setChecked(!this.mChecked);
    }

    @Override // android.widget.Checkable
    @android.view.ViewDebug.ExportedProperty
    public boolean isChecked() {
        return this.mChecked;
    }

    @Override // android.widget.Checkable
    public void setChecked(boolean z) {
        if (this.mChecked != z) {
            this.mChecked = z;
            refreshDrawableState();
            notifyViewAccessibilityStateChangedIfNeeded(0);
        }
    }

    public void setCheckMarkDrawable(int i) {
        if (i != 0 && i == this.mCheckMarkResource) {
            return;
        }
        setCheckMarkDrawableInternal(i != 0 ? getContext().getDrawable(i) : null, i);
    }

    public void setCheckMarkDrawable(android.graphics.drawable.Drawable drawable) {
        setCheckMarkDrawableInternal(drawable, 0);
    }

    private void setCheckMarkDrawableInternal(android.graphics.drawable.Drawable drawable, int i) {
        if (this.mCheckMarkDrawable != null) {
            this.mCheckMarkDrawable.setCallback(null);
            unscheduleDrawable(this.mCheckMarkDrawable);
        }
        this.mNeedRequestlayout = drawable != this.mCheckMarkDrawable;
        if (drawable != null) {
            drawable.setCallback(this);
            drawable.setVisible(getVisibility() == 0, false);
            drawable.setState(CHECKED_STATE_SET);
            setMinHeight(drawable.getIntrinsicHeight());
            this.mCheckMarkWidth = drawable.getIntrinsicWidth();
            drawable.setState(getDrawableState());
        } else {
            this.mCheckMarkWidth = 0;
        }
        this.mCheckMarkDrawable = drawable;
        this.mCheckMarkResource = i;
        applyCheckMarkTint();
        resolvePadding();
    }

    public void setCheckMarkTintList(android.content.res.ColorStateList colorStateList) {
        this.mCheckMarkTintList = colorStateList;
        this.mHasCheckMarkTint = true;
        applyCheckMarkTint();
    }

    public android.content.res.ColorStateList getCheckMarkTintList() {
        return this.mCheckMarkTintList;
    }

    public void setCheckMarkTintMode(android.graphics.PorterDuff.Mode mode) {
        setCheckMarkTintBlendMode(mode != null ? android.graphics.BlendMode.fromValue(mode.nativeInt) : null);
    }

    public void setCheckMarkTintBlendMode(android.graphics.BlendMode blendMode) {
        this.mCheckMarkBlendMode = blendMode;
        this.mHasCheckMarkTintMode = true;
        applyCheckMarkTint();
    }

    public android.graphics.PorterDuff.Mode getCheckMarkTintMode() {
        if (this.mCheckMarkBlendMode != null) {
            return android.graphics.BlendMode.blendModeToPorterDuffMode(this.mCheckMarkBlendMode);
        }
        return null;
    }

    public android.graphics.BlendMode getCheckMarkTintBlendMode() {
        return this.mCheckMarkBlendMode;
    }

    private void applyCheckMarkTint() {
        if (this.mCheckMarkDrawable != null) {
            if (this.mHasCheckMarkTint || this.mHasCheckMarkTintMode) {
                this.mCheckMarkDrawable = this.mCheckMarkDrawable.mutate();
                if (this.mHasCheckMarkTint) {
                    this.mCheckMarkDrawable.setTintList(this.mCheckMarkTintList);
                }
                if (this.mHasCheckMarkTintMode) {
                    this.mCheckMarkDrawable.setTintBlendMode(this.mCheckMarkBlendMode);
                }
                if (this.mCheckMarkDrawable.isStateful()) {
                    this.mCheckMarkDrawable.setState(getDrawableState());
                }
            }
        }
    }

    @Override // android.view.View
    @android.view.RemotableViewMethod
    public void setVisibility(int i) {
        super.setVisibility(i);
        if (this.mCheckMarkDrawable != null) {
            this.mCheckMarkDrawable.setVisible(i == 0, false);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.mCheckMarkDrawable != null) {
            this.mCheckMarkDrawable.jumpToCurrentState();
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected boolean verifyDrawable(android.graphics.drawable.Drawable drawable) {
        return drawable == this.mCheckMarkDrawable || super.verifyDrawable(drawable);
    }

    public android.graphics.drawable.Drawable getCheckMarkDrawable() {
        return this.mCheckMarkDrawable;
    }

    @Override // android.view.View
    protected void internalSetPadding(int i, int i2, int i3, int i4) {
        super.internalSetPadding(i, i2, i3, i4);
        setBasePadding(isCheckMarkAtStart());
    }

    @Override // android.widget.TextView, android.view.View
    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        updatePadding();
    }

    private void updatePadding() {
        resetPaddingToInitialValues();
        int i = this.mCheckMarkDrawable != null ? this.mCheckMarkWidth + this.mBasePadding : this.mBasePadding;
        if (isCheckMarkAtStart()) {
            this.mNeedRequestlayout |= this.mPaddingLeft != i;
            this.mPaddingLeft = i;
        } else {
            this.mNeedRequestlayout |= this.mPaddingRight != i;
            this.mPaddingRight = i;
        }
        if (this.mNeedRequestlayout) {
            requestLayout();
            this.mNeedRequestlayout = false;
        }
    }

    private void setBasePadding(boolean z) {
        if (z) {
            this.mBasePadding = this.mPaddingLeft;
        } else {
            this.mBasePadding = this.mPaddingRight;
        }
    }

    private boolean isCheckMarkAtStart() {
        return (android.view.Gravity.getAbsoluteGravity(this.mCheckMarkGravity, getLayoutDirection()) & 7) == 3;
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(android.graphics.Canvas canvas) {
        int height;
        int i;
        int i2;
        super.onDraw(canvas);
        android.graphics.drawable.Drawable drawable = this.mCheckMarkDrawable;
        if (drawable != null) {
            int gravity = getGravity() & 112;
            int intrinsicHeight = drawable.getIntrinsicHeight();
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
            boolean isCheckMarkAtStart = isCheckMarkAtStart();
            int width = getWidth();
            int i3 = intrinsicHeight + height;
            if (isCheckMarkAtStart) {
                i2 = this.mBasePadding;
                i = this.mCheckMarkWidth + i2;
            } else {
                i = width - this.mBasePadding;
                i2 = i - this.mCheckMarkWidth;
            }
            drawable.setBounds(this.mScrollX + i2, height, this.mScrollX + i, i3);
            drawable.draw(canvas);
            android.graphics.drawable.Drawable background = getBackground();
            if (background != null) {
                background.setHotspotBounds(this.mScrollX + i2, height, this.mScrollX + i, i3);
            }
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
        android.graphics.drawable.Drawable drawable = this.mCheckMarkDrawable;
        if (drawable != null && drawable.isStateful() && drawable.setState(getDrawableState())) {
            invalidateDrawable(drawable);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        if (this.mCheckMarkDrawable != null) {
            this.mCheckMarkDrawable.setHotspot(f, f2);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.CheckedTextView.class.getName();
    }

    static class SavedState extends android.view.View.BaseSavedState {
        public static final android.os.Parcelable.Creator<android.widget.CheckedTextView.SavedState> CREATOR = new android.os.Parcelable.Creator<android.widget.CheckedTextView.SavedState>() { // from class: android.widget.CheckedTextView.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.widget.CheckedTextView.SavedState createFromParcel(android.os.Parcel parcel) {
                return new android.widget.CheckedTextView.SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.widget.CheckedTextView.SavedState[] newArray(int i) {
                return new android.widget.CheckedTextView.SavedState[i];
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
            return "CheckedTextView.SavedState{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " checked=" + this.checked + "}";
        }
    }

    @Override // android.widget.TextView, android.view.View
    public android.os.Parcelable onSaveInstanceState() {
        android.widget.CheckedTextView.SavedState savedState = new android.widget.CheckedTextView.SavedState(super.onSaveInstanceState());
        savedState.checked = isChecked();
        return savedState;
    }

    @Override // android.widget.TextView, android.view.View
    public void onRestoreInstanceState(android.os.Parcelable parcelable) {
        android.widget.CheckedTextView.SavedState savedState = (android.widget.CheckedTextView.SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setChecked(savedState.checked);
        requestLayout();
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

    @Override // android.widget.TextView, android.view.View
    protected void encodeProperties(android.view.ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("text:checked", isChecked());
    }
}
