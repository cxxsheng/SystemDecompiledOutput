package android.gesture;

/* loaded from: classes.dex */
public class GesturePoint {
    public final long timestamp;
    public final float x;
    public final float y;

    public GesturePoint(float f, float f2, long j) {
        this.x = f;
        this.y = f2;
        this.timestamp = j;
    }

    static android.gesture.GesturePoint deserialize(java.io.DataInputStream dataInputStream) throws java.io.IOException {
        return new android.gesture.GesturePoint(dataInputStream.readFloat(), dataInputStream.readFloat(), dataInputStream.readLong());
    }

    public java.lang.Object clone() {
        return new android.gesture.GesturePoint(this.x, this.y, this.timestamp);
    }
}
