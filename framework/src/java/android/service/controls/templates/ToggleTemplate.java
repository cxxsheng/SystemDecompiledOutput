package android.service.controls.templates;

/* loaded from: classes3.dex */
public final class ToggleTemplate extends android.service.controls.templates.ControlTemplate {
    private static final java.lang.String KEY_BUTTON = "key_button";
    private static final int TYPE = 1;
    private final android.service.controls.templates.ControlButton mButton;

    public ToggleTemplate(java.lang.String str, android.service.controls.templates.ControlButton controlButton) {
        super(str);
        com.android.internal.util.Preconditions.checkNotNull(controlButton);
        this.mButton = controlButton;
    }

    ToggleTemplate(android.os.Bundle bundle) {
        super(bundle);
        this.mButton = (android.service.controls.templates.ControlButton) bundle.getParcelable(KEY_BUTTON, android.service.controls.templates.ControlButton.class);
    }

    public boolean isChecked() {
        return this.mButton.isChecked();
    }

    public java.lang.CharSequence getContentDescription() {
        return this.mButton.getActionDescription();
    }

    @Override // android.service.controls.templates.ControlTemplate
    public int getTemplateType() {
        return 1;
    }

    @Override // android.service.controls.templates.ControlTemplate
    android.os.Bundle getDataBundle() {
        android.os.Bundle dataBundle = super.getDataBundle();
        dataBundle.putParcelable(KEY_BUTTON, this.mButton);
        return dataBundle;
    }
}
