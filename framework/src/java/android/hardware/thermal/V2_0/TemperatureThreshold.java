package android.hardware.thermal.V2_0;

/* loaded from: classes2.dex */
public final class TemperatureThreshold {
    public int type = 0;
    public java.lang.String name = new java.lang.String();
    public float[] hotThrottlingThresholds = new float[7];
    public float[] coldThrottlingThresholds = new float[7];
    public float vrThrottlingThreshold = 0.0f;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.thermal.V2_0.TemperatureThreshold.class) {
            return false;
        }
        android.hardware.thermal.V2_0.TemperatureThreshold temperatureThreshold = (android.hardware.thermal.V2_0.TemperatureThreshold) obj;
        if (this.type == temperatureThreshold.type && android.os.HidlSupport.deepEquals(this.name, temperatureThreshold.name) && android.os.HidlSupport.deepEquals(this.hotThrottlingThresholds, temperatureThreshold.hotThrottlingThresholds) && android.os.HidlSupport.deepEquals(this.coldThrottlingThresholds, temperatureThreshold.coldThrottlingThresholds) && this.vrThrottlingThreshold == temperatureThreshold.vrThrottlingThreshold) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.type))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.name)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.hotThrottlingThresholds)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.coldThrottlingThresholds)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.vrThrottlingThreshold))));
    }

    public final java.lang.String toString() {
        return "{.type = " + android.hardware.thermal.V2_0.TemperatureType.toString(this.type) + ", .name = " + this.name + ", .hotThrottlingThresholds = " + java.util.Arrays.toString(this.hotThrottlingThresholds) + ", .coldThrottlingThresholds = " + java.util.Arrays.toString(this.coldThrottlingThresholds) + ", .vrThrottlingThreshold = " + this.vrThrottlingThreshold + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(88L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.thermal.V2_0.TemperatureThreshold> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.thermal.V2_0.TemperatureThreshold> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 88, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.thermal.V2_0.TemperatureThreshold temperatureThreshold = new android.hardware.thermal.V2_0.TemperatureThreshold();
            temperatureThreshold.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 88);
            arrayList.add(temperatureThreshold);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.type = hwBlob.getInt32(j + 0);
        long j2 = j + 8;
        this.name = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.name.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        hwBlob.copyToFloatArray(j + 24, this.hotThrottlingThresholds, 7);
        hwBlob.copyToFloatArray(j + 52, this.coldThrottlingThresholds, 7);
        this.vrThrottlingThreshold = hwBlob.getFloat(j + 80);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(88);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.thermal.V2_0.TemperatureThreshold> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 88);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 88);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(0 + j, this.type);
        hwBlob.putString(8 + j, this.name);
        long j2 = 24 + j;
        float[] fArr = this.hotThrottlingThresholds;
        if (fArr == null || fArr.length != 7) {
            throw new java.lang.IllegalArgumentException("Array element is not of the expected length");
        }
        hwBlob.putFloatArray(j2, fArr);
        long j3 = 52 + j;
        float[] fArr2 = this.coldThrottlingThresholds;
        if (fArr2 == null || fArr2.length != 7) {
            throw new java.lang.IllegalArgumentException("Array element is not of the expected length");
        }
        hwBlob.putFloatArray(j3, fArr2);
        hwBlob.putFloat(j + 80, this.vrThrottlingThreshold);
    }
}
