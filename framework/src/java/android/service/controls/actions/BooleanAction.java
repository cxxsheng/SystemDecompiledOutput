package android.service.controls.actions;

/* loaded from: classes3.dex */
public final class BooleanAction extends android.service.controls.actions.ControlAction {
    private static final java.lang.String KEY_NEW_STATE = "key_new_state";
    private static final int TYPE = 1;
    private final boolean mNewState;

    public BooleanAction(java.lang.String str, boolean z) {
        this(str, z, null);
    }

    public BooleanAction(java.lang.String str, boolean z, java.lang.String str2) {
        super(str, str2);
        this.mNewState = z;
    }

    BooleanAction(android.os.Bundle bundle) {
        super(bundle);
        this.mNewState = bundle.getBoolean(KEY_NEW_STATE);
    }

    public boolean getNewState() {
        return this.mNewState;
    }

    @Override // android.service.controls.actions.ControlAction
    public int getActionType() {
        return 1;
    }

    @Override // android.service.controls.actions.ControlAction
    android.os.Bundle getDataBundle() {
        android.os.Bundle dataBundle = super.getDataBundle();
        dataBundle.putBoolean(KEY_NEW_STATE, this.mNewState);
        return dataBundle;
    }
}
