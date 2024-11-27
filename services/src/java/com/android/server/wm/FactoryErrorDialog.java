package com.android.server.wm;

/* loaded from: classes3.dex */
final class FactoryErrorDialog extends com.android.server.am.BaseErrorDialog {
    private final android.os.Handler mHandler;

    public FactoryErrorDialog(android.content.Context context, java.lang.CharSequence charSequence) {
        super(context);
        this.mHandler = new android.os.Handler() { // from class: com.android.server.wm.FactoryErrorDialog.1
            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                throw new java.lang.RuntimeException("Rebooting from failed factory test");
            }
        };
        setCancelable(false);
        setTitle(context.getText(android.R.string.face_error_lockout_permanent));
        setMessage(charSequence);
        setButton(-1, context.getText(android.R.string.face_error_not_enrolled), this.mHandler.obtainMessage(0));
        android.view.WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.setTitle("Factory Error");
        getWindow().setAttributes(attributes);
    }

    @Override // com.android.server.am.BaseErrorDialog
    protected void closeDialog() {
    }
}
