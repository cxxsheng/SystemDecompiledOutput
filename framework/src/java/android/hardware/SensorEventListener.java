package android.hardware;

/* loaded from: classes.dex */
public interface SensorEventListener {
    void onAccuracyChanged(android.hardware.Sensor sensor, int i);

    void onSensorChanged(android.hardware.SensorEvent sensorEvent);
}
