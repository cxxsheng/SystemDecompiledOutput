package android.service.controls.templates;

/* loaded from: classes3.dex */
public final class RangeTemplate extends android.service.controls.templates.ControlTemplate {
    private static final java.lang.String KEY_CURRENT_VALUE = "key_current_value";
    private static final java.lang.String KEY_FORMAT_STRING = "key_format_string";
    private static final java.lang.String KEY_MAX_VALUE = "key_max_value";
    private static final java.lang.String KEY_MIN_VALUE = "key_min_value";
    private static final java.lang.String KEY_STEP_VALUE = "key_step_value";
    private static final int TYPE = 2;
    private final float mCurrentValue;
    private final java.lang.CharSequence mFormatString;
    private final float mMaxValue;
    private final float mMinValue;
    private final float mStepValue;

    public RangeTemplate(java.lang.String str, float f, float f2, float f3, float f4, java.lang.CharSequence charSequence) {
        super(str);
        this.mMinValue = f;
        this.mMaxValue = f2;
        this.mCurrentValue = f3;
        this.mStepValue = f4;
        if (charSequence != null) {
            this.mFormatString = charSequence;
        } else {
            this.mFormatString = "%.1f";
        }
        validate();
    }

    RangeTemplate(android.os.Bundle bundle) {
        super(bundle);
        this.mMinValue = bundle.getFloat(KEY_MIN_VALUE);
        this.mMaxValue = bundle.getFloat(KEY_MAX_VALUE);
        this.mCurrentValue = bundle.getFloat(KEY_CURRENT_VALUE);
        this.mStepValue = bundle.getFloat(KEY_STEP_VALUE);
        this.mFormatString = bundle.getCharSequence(KEY_FORMAT_STRING, "%.1f");
        validate();
    }

    public float getMinValue() {
        return this.mMinValue;
    }

    public float getMaxValue() {
        return this.mMaxValue;
    }

    public float getCurrentValue() {
        return this.mCurrentValue;
    }

    public float getStepValue() {
        return this.mStepValue;
    }

    public java.lang.CharSequence getFormatString() {
        return this.mFormatString;
    }

    @Override // android.service.controls.templates.ControlTemplate
    public int getTemplateType() {
        return 2;
    }

    @Override // android.service.controls.templates.ControlTemplate
    android.os.Bundle getDataBundle() {
        android.os.Bundle dataBundle = super.getDataBundle();
        dataBundle.putFloat(KEY_MIN_VALUE, this.mMinValue);
        dataBundle.putFloat(KEY_MAX_VALUE, this.mMaxValue);
        dataBundle.putFloat(KEY_CURRENT_VALUE, this.mCurrentValue);
        dataBundle.putFloat(KEY_STEP_VALUE, this.mStepValue);
        dataBundle.putCharSequence(KEY_FORMAT_STRING, this.mFormatString);
        return dataBundle;
    }

    private void validate() {
        if (java.lang.Float.compare(this.mMinValue, this.mMaxValue) > 0) {
            throw new java.lang.IllegalArgumentException(java.lang.String.format("minValue=%f > maxValue=%f", java.lang.Float.valueOf(this.mMinValue), java.lang.Float.valueOf(this.mMaxValue)));
        }
        if (java.lang.Float.compare(this.mMinValue, this.mCurrentValue) > 0) {
            throw new java.lang.IllegalArgumentException(java.lang.String.format("minValue=%f > currentValue=%f", java.lang.Float.valueOf(this.mMinValue), java.lang.Float.valueOf(this.mCurrentValue)));
        }
        if (java.lang.Float.compare(this.mCurrentValue, this.mMaxValue) > 0) {
            throw new java.lang.IllegalArgumentException(java.lang.String.format("currentValue=%f > maxValue=%f", java.lang.Float.valueOf(this.mCurrentValue), java.lang.Float.valueOf(this.mMaxValue)));
        }
        if (this.mStepValue <= 0.0f) {
            throw new java.lang.IllegalArgumentException(java.lang.String.format("stepValue=%f <= 0", java.lang.Float.valueOf(this.mStepValue)));
        }
    }
}
