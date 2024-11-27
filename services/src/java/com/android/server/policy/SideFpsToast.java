package com.android.server.policy;

/* loaded from: classes2.dex */
public class SideFpsToast extends android.app.Dialog {
    SideFpsToast(android.content.Context context) {
        super(context);
    }

    @Override // android.app.Dialog
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        setContentView(android.R.layout.select_dialog_singlechoice_holo);
    }

    @Override // android.app.Dialog
    protected void onStart() {
        super.onStart();
        android.view.Window window = getWindow();
        android.view.WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.dimAmount = com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE;
        attributes.flags |= 2;
        attributes.gravity = 80;
        window.setAttributes(attributes);
    }

    public void setOnClickListener(android.view.View.OnClickListener onClickListener) {
        android.widget.Button button = (android.widget.Button) findViewById(android.R.id.transparent);
        if (button != null) {
            button.setOnClickListener(onClickListener);
        }
    }
}
