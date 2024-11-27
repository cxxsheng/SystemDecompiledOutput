package android.hardware.thermal.V1_0;

/* loaded from: classes2.dex */
public final class Temperature {
    public int type = 0;
    public java.lang.String name = new java.lang.String();
    public float currentValue = 0.0f;
    public float throttlingThreshold = 0.0f;
    public float shutdownThreshold = 0.0f;
    public float vrThrottlingThreshold = 0.0f;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.thermal.V1_0.Temperature.class) {
            return false;
        }
        android.hardware.thermal.V1_0.Temperature temperature = (android.hardware.thermal.V1_0.Temperature) obj;
        if (this.type == temperature.type && android.os.HidlSupport.deepEquals(this.name, temperature.name) && this.currentValue == temperature.currentValue && this.throttlingThreshold == temperature.throttlingThreshold && this.shutdownThreshold == temperature.shutdownThreshold && this.vrThrottlingThreshold == temperature.vrThrottlingThreshold) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.type))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.name)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.currentValue))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.throttlingThreshold))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.shutdownThreshold))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.vrThrottlingThreshold))));
    }

    public final java.lang.String toString() {
        return "{.type = " + android.hardware.thermal.V1_0.TemperatureType.toString(this.type) + ", .name = " + this.name + ", .currentValue = " + this.currentValue + ", .throttlingThreshold = " + this.throttlingThreshold + ", .shutdownThreshold = " + this.shutdownThreshold + ", .vrThrottlingThreshold = " + this.vrThrottlingThreshold + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.thermal.V1_0.Temperature> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.thermal.V1_0.Temperature> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.thermal.V1_0.Temperature temperature = new android.hardware.thermal.V1_0.Temperature();
            temperature.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 40);
            arrayList.add(temperature);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.type = hwBlob.getInt32(j + 0);
        long j2 = j + 8;
        this.name = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.name.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        this.currentValue = hwBlob.getFloat(j + 24);
        this.throttlingThreshold = hwBlob.getFloat(j + 28);
        this.shutdownThreshold = hwBlob.getFloat(j + 32);
        this.vrThrottlingThreshold = hwBlob.getFloat(j + 36);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(40);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.thermal.V1_0.Temperature> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 40);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 40);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(0 + j, this.type);
        hwBlob.putString(8 + j, this.name);
        hwBlob.putFloat(24 + j, this.currentValue);
        hwBlob.putFloat(28 + j, this.throttlingThreshold);
        hwBlob.putFloat(32 + j, this.shutdownThreshold);
        hwBlob.putFloat(j + 36, this.vrThrottlingThreshold);
    }
}
