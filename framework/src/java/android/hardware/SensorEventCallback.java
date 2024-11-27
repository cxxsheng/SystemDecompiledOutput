package android.hardware;

/* loaded from: classes.dex */
public abstract class SensorEventCallback implements android.hardware.SensorEventListener2 {
    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(android.hardware.SensorEvent sensorEvent) {
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(android.hardware.Sensor sensor, int i) {
    }

    @Override // android.hardware.SensorEventListener2
    public void onFlushCompleted(android.hardware.Sensor sensor) {
    }

    public void onSensorAdditionalInfo(android.hardware.SensorAdditionalInfo sensorAdditionalInfo) {
    }
}
