package com.android.server.updates;

/* loaded from: classes2.dex */
public class CarrierIdInstallReceiver extends com.android.server.updates.ConfigUpdateInstallReceiver {
    public CarrierIdInstallReceiver() {
        super("/data/misc/carrierid", "carrier_list.pb", "metadata/", "version");
    }

    @Override // com.android.server.updates.ConfigUpdateInstallReceiver
    protected void postInstall(android.content.Context context, android.content.Intent intent) {
        context.getContentResolver().update(android.net.Uri.withAppendedPath(android.provider.Telephony.CarrierId.All.CONTENT_URI, "update_db"), new android.content.ContentValues(), null, null);
    }
}
