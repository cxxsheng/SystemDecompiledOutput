package com.android.server.wm;

/* loaded from: classes3.dex */
class DeprecatedAbiDialog extends com.android.server.wm.AppWarnings.BaseDialog {
    DeprecatedAbiDialog(final com.android.server.wm.AppWarnings appWarnings, android.content.Context context, android.content.pm.ApplicationInfo applicationInfo) {
        super(appWarnings, applicationInfo.packageName);
        this.mDialog = new android.app.AlertDialog.Builder(context).setPositiveButton(android.R.string.ok, new android.content.DialogInterface.OnClickListener() { // from class: com.android.server.wm.DeprecatedAbiDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(android.content.DialogInterface dialogInterface, int i) {
                com.android.server.wm.DeprecatedAbiDialog.this.lambda$new$0(appWarnings, dialogInterface, i);
            }
        }).setMessage(context.getString(android.R.string.default_audio_route_name_headphones)).setTitle(applicationInfo.loadSafeLabel(context.getPackageManager(), 1000.0f, 5)).create();
        this.mDialog.create();
        android.view.Window window = this.mDialog.getWindow();
        window.setType(2002);
        window.getAttributes().setTitle("DeprecatedAbiDialog");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(com.android.server.wm.AppWarnings appWarnings, android.content.DialogInterface dialogInterface, int i) {
        appWarnings.setPackageFlag(this.mPackageName, 8, true);
    }
}
