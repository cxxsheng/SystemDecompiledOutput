package android.telephony.satellite.stub;

/* loaded from: classes3.dex */
public class SatelliteService extends android.app.Service {
    public static final java.lang.String SERVICE_INTERFACE = "android.telephony.satellite.SatelliteService";
    private static final java.lang.String TAG = "SatelliteService";

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            com.android.telephony.Rlog.d(TAG, "SatelliteService bound");
            return new android.telephony.satellite.stub.SatelliteImplBase(new android.app.PendingIntent$$ExternalSyntheticLambda0()).getBinder();
        }
        return null;
    }
}
