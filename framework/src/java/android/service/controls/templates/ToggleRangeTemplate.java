package android.service.controls.templates;

/* loaded from: classes3.dex */
public final class ToggleRangeTemplate extends android.service.controls.templates.ControlTemplate {
    private static final java.lang.String KEY_BUTTON = "key_button";
    private static final java.lang.String KEY_RANGE = "key_range";
    private static final int TYPE = 6;
    private final android.service.controls.templates.ControlButton mControlButton;
    private final android.service.controls.templates.RangeTemplate mRangeTemplate;

    ToggleRangeTemplate(android.os.Bundle bundle) {
        super(bundle);
        this.mControlButton = (android.service.controls.templates.ControlButton) bundle.getParcelable(KEY_BUTTON, android.service.controls.templates.ControlButton.class);
        this.mRangeTemplate = new android.service.controls.templates.RangeTemplate(bundle.getBundle(KEY_RANGE));
    }

    public ToggleRangeTemplate(java.lang.String str, android.service.controls.templates.ControlButton controlButton, android.service.controls.templates.RangeTemplate rangeTemplate) {
        super(str);
        com.android.internal.util.Preconditions.checkNotNull(controlButton);
        com.android.internal.util.Preconditions.checkNotNull(rangeTemplate);
        this.mControlButton = controlButton;
        this.mRangeTemplate = rangeTemplate;
    }

    public ToggleRangeTemplate(java.lang.String str, boolean z, java.lang.CharSequence charSequence, android.service.controls.templates.RangeTemplate rangeTemplate) {
        this(str, new android.service.controls.templates.ControlButton(z, charSequence), rangeTemplate);
    }

    @Override // android.service.controls.templates.ControlTemplate
    android.os.Bundle getDataBundle() {
        android.os.Bundle dataBundle = super.getDataBundle();
        dataBundle.putParcelable(KEY_BUTTON, this.mControlButton);
        dataBundle.putBundle(KEY_RANGE, this.mRangeTemplate.getDataBundle());
        return dataBundle;
    }

    public android.service.controls.templates.RangeTemplate getRange() {
        return this.mRangeTemplate;
    }

    public boolean isChecked() {
        return this.mControlButton.isChecked();
    }

    public java.lang.CharSequence getActionDescription() {
        return this.mControlButton.getActionDescription();
    }

    @Override // android.service.controls.templates.ControlTemplate
    public int getTemplateType() {
        return 6;
    }
}
