package android.service.controls.actions;

/* loaded from: classes3.dex */
public final class FloatAction extends android.service.controls.actions.ControlAction {
    private static final java.lang.String KEY_NEW_VALUE = "key_new_value";
    private static final int TYPE = 2;
    private final float mNewValue;

    public FloatAction(java.lang.String str, float f) {
        this(str, f, null);
    }

    public FloatAction(java.lang.String str, float f, java.lang.String str2) {
        super(str, str2);
        this.mNewValue = f;
    }

    FloatAction(android.os.Bundle bundle) {
        super(bundle);
        this.mNewValue = bundle.getFloat(KEY_NEW_VALUE);
    }

    public float getNewValue() {
        return this.mNewValue;
    }

    @Override // android.service.controls.actions.ControlAction
    public int getActionType() {
        return 2;
    }

    @Override // android.service.controls.actions.ControlAction
    android.os.Bundle getDataBundle() {
        android.os.Bundle dataBundle = super.getDataBundle();
        dataBundle.putFloat(KEY_NEW_VALUE, this.mNewValue);
        return dataBundle;
    }
}
