package com.android.internal.app;

/* loaded from: classes4.dex */
public abstract class AlertActivity extends android.app.Activity implements android.content.DialogInterface {
    protected com.android.internal.app.AlertController mAlert;
    protected com.android.internal.app.AlertController.AlertParams mAlertParams;

    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        this.mAlert = com.android.internal.app.AlertController.create(this, this, getWindow());
        this.mAlertParams = new com.android.internal.app.AlertController.AlertParams(this);
    }

    @Override // android.content.DialogInterface
    public void cancel() {
        finish();
    }

    @Override // android.content.DialogInterface
    public void dismiss() {
        if (!isFinishing()) {
            finish();
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchPopulateAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        return dispatchPopulateAccessibilityEvent(this, accessibilityEvent);
    }

    public static boolean dispatchPopulateAccessibilityEvent(android.app.Activity activity, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        accessibilityEvent.setClassName(android.app.Dialog.class.getName());
        accessibilityEvent.setPackageName(activity.getPackageName());
        android.view.WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
        accessibilityEvent.setFullScreen(attributes.width == -1 && attributes.height == -1);
        return false;
    }

    protected void setupAlert() {
        this.mAlert.installContent(this.mAlertParams);
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, android.view.KeyEvent keyEvent) {
        if (this.mAlert.onKeyDown(i, keyEvent)) {
            return true;
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, android.view.KeyEvent keyEvent) {
        if (this.mAlert.onKeyUp(i, keyEvent)) {
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }
}
