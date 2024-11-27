package android.telephony.satellite;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public interface SatelliteTransmissionUpdateCallback {
    void onReceiveDatagramStateChanged(int i, int i2, int i3);

    void onSatellitePositionChanged(android.telephony.satellite.PointingInfo pointingInfo);

    void onSendDatagramStateChanged(int i, int i2, int i3);
}
