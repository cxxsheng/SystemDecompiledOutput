package android.telephony.satellite.stub;

/* loaded from: classes3.dex */
public abstract class SatelliteGatewayService extends android.app.Service {
    public static final java.lang.String SERVICE_INTERFACE = "android.telephony.satellite.SatelliteGatewayService";
    private static final java.lang.String TAG = "SatelliteGatewayService";
    private final android.os.IBinder mBinder = new android.telephony.satellite.stub.ISatelliteGateway.Stub() { // from class: android.telephony.satellite.stub.SatelliteGatewayService.1
    };

    @Override // android.app.Service
    public final android.os.IBinder onBind(android.content.Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            com.android.telephony.Rlog.d(TAG, "SatelliteGatewayService bound");
            return this.mBinder;
        }
        return null;
    }

    public final android.os.IBinder getBinder() {
        return this.mBinder;
    }
}
