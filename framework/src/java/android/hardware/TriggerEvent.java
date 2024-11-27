package android.hardware;

/* loaded from: classes.dex */
public final class TriggerEvent {
    public android.hardware.Sensor sensor;
    public long timestamp;
    public final float[] values;

    TriggerEvent(int i) {
        this.values = new float[i];
    }
}
