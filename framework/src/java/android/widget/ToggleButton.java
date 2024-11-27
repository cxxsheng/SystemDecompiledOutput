package android.widget;

/* loaded from: classes4.dex */
public class ToggleButton extends android.widget.CompoundButton {
    private static final int NO_ALPHA = 255;
    private float mDisabledAlpha;
    private android.graphics.drawable.Drawable mIndicatorDrawable;
    private java.lang.CharSequence mTextOff;
    private java.lang.CharSequence mTextOn;

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.ToggleButton> {
        private int mDisabledAlphaId;
        private boolean mPropertiesMapped = false;
        private int mTextOffId;
        private int mTextOnId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
            this.mDisabledAlphaId = propertyMapper.mapFloat("disabledAlpha", 16842803);
            this.mTextOffId = propertyMapper.mapObject("textOff", 16843045);
            this.mTextOnId = propertyMapper.mapObject("textOn", 16843044);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(android.widget.ToggleButton toggleButton, android.view.inspector.PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readFloat(this.mDisabledAlphaId, toggleButton.getDisabledAlpha());
            propertyReader.readObject(this.mTextOffId, toggleButton.getTextOff());
            propertyReader.readObject(this.mTextOnId, toggleButton.getTextOn());
        }
    }

    public ToggleButton(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.ToggleButton, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.ToggleButton, attributeSet, obtainStyledAttributes, i, i2);
        this.mTextOn = obtainStyledAttributes.getText(1);
        this.mTextOff = obtainStyledAttributes.getText(2);
        this.mDisabledAlpha = obtainStyledAttributes.getFloat(0, 0.5f);
        syncTextState();
        setDefaultStateDescription();
        obtainStyledAttributes.recycle();
    }

    public ToggleButton(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ToggleButton(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842827);
    }

    public ToggleButton(android.content.Context context) {
        this(context, null);
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public void setChecked(boolean z) {
        super.setChecked(z);
        syncTextState();
    }

    private void syncTextState() {
        boolean isChecked = isChecked();
        if (isChecked && this.mTextOn != null) {
            lambda$setTextAsync$0(this.mTextOn);
        } else if (!isChecked && this.mTextOff != null) {
            lambda$setTextAsync$0(this.mTextOff);
        }
    }

    public java.lang.CharSequence getTextOn() {
        return this.mTextOn;
    }

    public void setTextOn(java.lang.CharSequence charSequence) {
        this.mTextOn = charSequence;
        setDefaultStateDescription();
    }

    public java.lang.CharSequence getTextOff() {
        return this.mTextOff;
    }

    public void setTextOff(java.lang.CharSequence charSequence) {
        this.mTextOff = charSequence;
        setDefaultStateDescription();
    }

    public float getDisabledAlpha() {
        return this.mDisabledAlpha;
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        updateReferenceToIndicatorDrawable(getBackground());
    }

    @Override // android.view.View
    public void setBackgroundDrawable(android.graphics.drawable.Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        updateReferenceToIndicatorDrawable(drawable);
    }

    private void updateReferenceToIndicatorDrawable(android.graphics.drawable.Drawable drawable) {
        if (drawable instanceof android.graphics.drawable.LayerDrawable) {
            this.mIndicatorDrawable = ((android.graphics.drawable.LayerDrawable) drawable).findDrawableByLayerId(16908311);
        } else {
            this.mIndicatorDrawable = null;
        }
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mIndicatorDrawable != null) {
            this.mIndicatorDrawable.setAlpha(isEnabled() ? 255 : (int) (this.mDisabledAlpha * 255.0f));
        }
    }

    @Override // android.widget.CompoundButton, android.widget.Button, android.widget.TextView, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.ToggleButton.class.getName();
    }

    @Override // android.widget.CompoundButton
    protected java.lang.CharSequence getButtonStateDescription() {
        return isChecked() ? this.mTextOn == null ? getResources().getString(com.android.internal.R.string.capital_on) : this.mTextOn : this.mTextOff == null ? getResources().getString(com.android.internal.R.string.capital_off) : this.mTextOff;
    }
}
