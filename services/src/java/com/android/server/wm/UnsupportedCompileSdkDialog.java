package com.android.server.wm;

/* loaded from: classes3.dex */
class UnsupportedCompileSdkDialog extends com.android.server.wm.AppWarnings.BaseDialog {
    UnsupportedCompileSdkDialog(final com.android.server.wm.AppWarnings appWarnings, final android.content.Context context, android.content.pm.ApplicationInfo applicationInfo) {
        super(appWarnings, applicationInfo.packageName);
        android.app.AlertDialog.Builder view = new android.app.AlertDialog.Builder(context).setPositiveButton(android.R.string.ok, (android.content.DialogInterface.OnClickListener) null).setMessage(context.getString(android.R.string.test_harness_mode_notification_title, applicationInfo.loadSafeLabel(context.getPackageManager(), 1000.0f, 5))).setView(android.R.layout.transient_notification_with_icon);
        final android.content.Intent createIntent = com.android.server.utils.AppInstallerUtil.createIntent(context, applicationInfo.packageName);
        if (createIntent != null) {
            view.setNeutralButton(android.R.string.test_harness_mode_notification_message, new android.content.DialogInterface.OnClickListener() { // from class: com.android.server.wm.UnsupportedCompileSdkDialog$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(android.content.DialogInterface dialogInterface, int i) {
                    context.startActivity(createIntent);
                }
            });
        }
        this.mDialog = view.create();
        this.mDialog.create();
        android.view.Window window = this.mDialog.getWindow();
        window.setType(2002);
        window.getAttributes().setTitle("UnsupportedCompileSdkDialog");
        android.widget.CheckBox checkBox = (android.widget.CheckBox) this.mDialog.findViewById(android.R.id.ask_checkbox);
        checkBox.setChecked(true);
        checkBox.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() { // from class: com.android.server.wm.UnsupportedCompileSdkDialog$$ExternalSyntheticLambda1
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(android.widget.CompoundButton compoundButton, boolean z) {
                com.android.server.wm.UnsupportedCompileSdkDialog.this.lambda$new$1(appWarnings, compoundButton, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(com.android.server.wm.AppWarnings appWarnings, android.widget.CompoundButton compoundButton, boolean z) {
        appWarnings.setPackageFlag(this.mPackageName, 2, !z);
    }
}
