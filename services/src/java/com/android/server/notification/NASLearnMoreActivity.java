package com.android.server.notification;

/* loaded from: classes2.dex */
public class NASLearnMoreActivity extends android.app.Activity {
    @Override // android.app.Activity
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        showLearnMoreDialog();
    }

    private void showLearnMoreDialog() {
        android.app.AlertDialog create = new android.app.AlertDialog.Builder(this).setMessage(android.R.string.mismatchPin).setPositiveButton(android.R.string.ok, new android.content.DialogInterface.OnClickListener() { // from class: com.android.server.notification.NASLearnMoreActivity.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(android.content.DialogInterface dialogInterface, int i) {
                com.android.server.notification.NASLearnMoreActivity.this.finish();
            }
        }).create();
        create.getWindow().setType(2003);
        create.show();
    }
}
