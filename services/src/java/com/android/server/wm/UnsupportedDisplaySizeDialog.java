package com.android.server.wm;

/* loaded from: classes3.dex */
class UnsupportedDisplaySizeDialog extends com.android.server.wm.AppWarnings.BaseDialog {
    UnsupportedDisplaySizeDialog(final com.android.server.wm.AppWarnings appWarnings, android.content.Context context, android.content.pm.ApplicationInfo applicationInfo) {
        super(appWarnings, applicationInfo.packageName);
        this.mDialog = new android.app.AlertDialog.Builder(context).setPositiveButton(android.R.string.ok, (android.content.DialogInterface.OnClickListener) null).setMessage(context.getString(android.R.string.time_of_day, applicationInfo.loadSafeLabel(context.getPackageManager(), 1000.0f, 5))).setView(android.R.layout.twelve_key_entry).create();
        this.mDialog.create();
        android.view.Window window = this.mDialog.getWindow();
        window.setType(2002);
        window.getAttributes().setTitle("UnsupportedDisplaySizeDialog");
        android.widget.CheckBox checkBox = (android.widget.CheckBox) this.mDialog.findViewById(android.R.id.ask_checkbox);
        checkBox.setChecked(true);
        checkBox.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() { // from class: com.android.server.wm.UnsupportedDisplaySizeDialog$$ExternalSyntheticLambda0
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(android.widget.CompoundButton compoundButton, boolean z) {
                com.android.server.wm.UnsupportedDisplaySizeDialog.this.lambda$new$0(appWarnings, compoundButton, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(com.android.server.wm.AppWarnings appWarnings, android.widget.CompoundButton compoundButton, boolean z) {
        appWarnings.setPackageFlag(this.mPackageName, 1, !z);
    }
}
