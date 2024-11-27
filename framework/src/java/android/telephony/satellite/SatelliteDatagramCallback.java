package android.telephony.satellite;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public interface SatelliteDatagramCallback {
    void onSatelliteDatagramReceived(long j, android.telephony.satellite.SatelliteDatagram satelliteDatagram, int i, java.util.function.Consumer<java.lang.Void> consumer);
}
