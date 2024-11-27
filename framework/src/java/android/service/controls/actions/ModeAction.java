package android.service.controls.actions;

/* loaded from: classes3.dex */
public final class ModeAction extends android.service.controls.actions.ControlAction {
    private static final java.lang.String KEY_MODE = "key_mode";
    private static final int TYPE = 4;
    private final int mNewMode;

    @Override // android.service.controls.actions.ControlAction
    public int getActionType() {
        return 4;
    }

    public ModeAction(java.lang.String str, int i, java.lang.String str2) {
        super(str, str2);
        this.mNewMode = i;
    }

    public ModeAction(java.lang.String str, int i) {
        this(str, i, null);
    }

    ModeAction(android.os.Bundle bundle) {
        super(bundle);
        this.mNewMode = bundle.getInt(KEY_MODE);
    }

    @Override // android.service.controls.actions.ControlAction
    android.os.Bundle getDataBundle() {
        android.os.Bundle dataBundle = super.getDataBundle();
        dataBundle.putInt(KEY_MODE, this.mNewMode);
        return dataBundle;
    }

    public int getNewMode() {
        return this.mNewMode;
    }
}
