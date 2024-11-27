package android.hardware.location;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public abstract class GeofenceHardwareMonitorCallback {
    @java.lang.Deprecated
    public void onMonitoringSystemChange(int i, boolean z, android.location.Location location) {
    }

    public void onMonitoringSystemChange(android.hardware.location.GeofenceHardwareMonitorEvent geofenceHardwareMonitorEvent) {
    }
}
