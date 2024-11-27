package com.android.server.wm;

/* loaded from: classes3.dex */
class DeprecatedTargetSdkVersionDialog extends com.android.server.wm.AppWarnings.BaseDialog {
    DeprecatedTargetSdkVersionDialog(final com.android.server.wm.AppWarnings appWarnings, final android.content.Context context, android.content.pm.ApplicationInfo applicationInfo) {
        super(appWarnings, applicationInfo.packageName);
        android.app.AlertDialog.Builder title = new android.app.AlertDialog.Builder(context).setPositiveButton(android.R.string.ok, new android.content.DialogInterface.OnClickListener() { // from class: com.android.server.wm.DeprecatedTargetSdkVersionDialog$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(android.content.DialogInterface dialogInterface, int i) {
                com.android.server.wm.DeprecatedTargetSdkVersionDialog.this.lambda$new$0(appWarnings, dialogInterface, i);
            }
        }).setMessage(context.getString(android.R.string.default_browser)).setTitle(applicationInfo.loadSafeLabel(context.getPackageManager(), 1000.0f, 5));
        final android.content.Intent createIntent = com.android.server.utils.AppInstallerUtil.createIntent(context, applicationInfo.packageName);
        if (createIntent != null) {
            title.setNeutralButton(android.R.string.default_audio_route_name_usb, new android.content.DialogInterface.OnClickListener() { // from class: com.android.server.wm.DeprecatedTargetSdkVersionDialog$$ExternalSyntheticLambda1
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(android.content.DialogInterface dialogInterface, int i) {
                    context.startActivity(createIntent);
                }
            });
        }
        this.mDialog = title.create();
        this.mDialog.create();
        android.view.Window window = this.mDialog.getWindow();
        window.setType(2002);
        window.getAttributes().setTitle("DeprecatedTargetSdkVersionDialog");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(com.android.server.wm.AppWarnings appWarnings, android.content.DialogInterface dialogInterface, int i) {
        appWarnings.setPackageFlag(this.mPackageName, 4, true);
    }
}
