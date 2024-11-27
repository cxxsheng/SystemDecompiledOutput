package android.telephony;

/* loaded from: classes3.dex */
public class MmsManager {
    private static final java.lang.String TAG = "MmsManager";
    private final android.content.Context mContext;

    public MmsManager(android.content.Context context) {
        this.mContext = context;
    }

    public void sendMultimediaMessage(int i, android.net.Uri uri, java.lang.String str, android.os.Bundle bundle, android.app.PendingIntent pendingIntent, long j) {
        try {
            com.android.internal.telephony.IMms asInterface = com.android.internal.telephony.IMms.Stub.asInterface(android.os.ServiceManager.getService("imms"));
            if (asInterface == null) {
                return;
            }
            asInterface.sendMessage(i, android.app.ActivityThread.currentPackageName(), uri, str, bundle, pendingIntent, j, this.mContext.getAttributionTag());
        } catch (android.os.RemoteException e) {
        }
    }

    public void downloadMultimediaMessage(int i, java.lang.String str, android.net.Uri uri, android.os.Bundle bundle, android.app.PendingIntent pendingIntent, long j) {
        try {
            com.android.internal.telephony.IMms asInterface = com.android.internal.telephony.IMms.Stub.asInterface(android.os.ServiceManager.getService("imms"));
            if (asInterface == null) {
                return;
            }
            asInterface.downloadMessage(i, android.app.ActivityThread.currentPackageName(), str, uri, bundle, pendingIntent, j, this.mContext.getAttributionTag());
        } catch (android.os.RemoteException e) {
        }
    }
}
