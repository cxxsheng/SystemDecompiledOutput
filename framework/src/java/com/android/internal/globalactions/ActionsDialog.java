package com.android.internal.globalactions;

/* loaded from: classes4.dex */
public final class ActionsDialog extends android.app.Dialog implements android.content.DialogInterface {
    private final com.android.internal.globalactions.ActionsAdapter mAdapter;
    private final com.android.internal.app.AlertController mAlert;
    private final android.content.Context mContext;

    public ActionsDialog(android.content.Context context, com.android.internal.app.AlertController.AlertParams alertParams) {
        super(context, getDialogTheme(context));
        this.mContext = getContext();
        this.mAlert = com.android.internal.app.AlertController.create(this.mContext, this, getWindow());
        this.mAdapter = (com.android.internal.globalactions.ActionsAdapter) alertParams.mAdapter;
        alertParams.apply(this.mAlert);
    }

    private static int getDialogTheme(android.content.Context context) {
        android.util.TypedValue typedValue = new android.util.TypedValue();
        context.getTheme().resolveAttribute(16843529, typedValue, true);
        return typedValue.resourceId;
    }

    @Override // android.app.Dialog
    protected void onStart() {
        super.setCanceledOnTouchOutside(true);
        super.onStart();
    }

    public android.widget.ListView getListView() {
        return this.mAlert.getListView();
    }

    @Override // android.app.Dialog
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        this.mAlert.installContent();
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public boolean dispatchPopulateAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() == 32) {
            for (int i = 0; i < this.mAdapter.getCount(); i++) {
                java.lang.CharSequence labelForAccessibility = this.mAdapter.getItem(i).getLabelForAccessibility(getContext());
                if (labelForAccessibility != null) {
                    accessibilityEvent.getText().add(labelForAccessibility);
                }
            }
        }
        return super.dispatchPopulateAccessibilityEvent(accessibilityEvent);
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, android.view.KeyEvent keyEvent) {
        if (this.mAlert.onKeyDown(i, keyEvent)) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.app.Dialog, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, android.view.KeyEvent keyEvent) {
        if (this.mAlert.onKeyUp(i, keyEvent)) {
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }
}
