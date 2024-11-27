package com.android.server.updates;

/* loaded from: classes2.dex */
public class ApnDbInstallReceiver extends com.android.server.updates.ConfigUpdateInstallReceiver {
    private static final android.net.Uri UPDATE_APN_DB = android.net.Uri.withAppendedPath(android.provider.Telephony.Carriers.CONTENT_URI, "update_db");

    public ApnDbInstallReceiver() {
        super("/data/misc/apns/", "apns-conf.xml", "metadata/", "version");
    }

    @Override // com.android.server.updates.ConfigUpdateInstallReceiver
    protected void postInstall(android.content.Context context, android.content.Intent intent) {
        context.getContentResolver().delete(UPDATE_APN_DB, null, null);
    }
}
