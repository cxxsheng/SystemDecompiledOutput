package android.service.controls.actions;

/* loaded from: classes3.dex */
public final class CommandAction extends android.service.controls.actions.ControlAction {
    private static final int TYPE = 5;

    public CommandAction(java.lang.String str, java.lang.String str2) {
        super(str, str2);
    }

    public CommandAction(java.lang.String str) {
        this(str, null);
    }

    CommandAction(android.os.Bundle bundle) {
        super(bundle);
    }

    @Override // android.service.controls.actions.ControlAction
    public int getActionType() {
        return 5;
    }
}
