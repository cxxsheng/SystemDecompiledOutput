package android.companion.virtual.sensor;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public interface VirtualSensorDirectChannelCallback {
    void onDirectChannelConfigured(int i, android.companion.virtual.sensor.VirtualSensor virtualSensor, int i2, int i3);

    void onDirectChannelCreated(int i, android.os.SharedMemory sharedMemory);

    void onDirectChannelDestroyed(int i);
}
