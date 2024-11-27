package android.hardware;

/* loaded from: classes.dex */
public class SensorEvent {
    public int accuracy;
    public boolean firstEventAfterDiscontinuity;
    public android.hardware.Sensor sensor;
    public long timestamp;
    public final float[] values;

    SensorEvent(int i) {
        this.values = new float[i];
    }

    public SensorEvent(android.hardware.Sensor sensor, int i, long j, float[] fArr) {
        this.sensor = sensor;
        this.accuracy = i;
        this.timestamp = j;
        this.values = fArr;
    }
}
