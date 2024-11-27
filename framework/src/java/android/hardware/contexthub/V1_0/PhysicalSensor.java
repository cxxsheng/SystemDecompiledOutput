package android.hardware.contexthub.V1_0;

/* loaded from: classes.dex */
public final class PhysicalSensor {
    public int sensorType = 0;
    public java.lang.String type = new java.lang.String();
    public java.lang.String name = new java.lang.String();
    public java.lang.String vendor = new java.lang.String();
    public int version = 0;
    public int fifoReservedCount = 0;
    public int fifoMaxCount = 0;
    public long minDelayMs = 0;
    public long maxDelayMs = 0;
    public float peakPowerMw = 0.0f;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.contexthub.V1_0.PhysicalSensor.class) {
            return false;
        }
        android.hardware.contexthub.V1_0.PhysicalSensor physicalSensor = (android.hardware.contexthub.V1_0.PhysicalSensor) obj;
        if (this.sensorType == physicalSensor.sensorType && android.os.HidlSupport.deepEquals(this.type, physicalSensor.type) && android.os.HidlSupport.deepEquals(this.name, physicalSensor.name) && android.os.HidlSupport.deepEquals(this.vendor, physicalSensor.vendor) && this.version == physicalSensor.version && this.fifoReservedCount == physicalSensor.fifoReservedCount && this.fifoMaxCount == physicalSensor.fifoMaxCount && this.minDelayMs == physicalSensor.minDelayMs && this.maxDelayMs == physicalSensor.maxDelayMs && this.peakPowerMw == physicalSensor.peakPowerMw) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.sensorType))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.type)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.name)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.vendor)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.version))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.fifoReservedCount))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.fifoMaxCount))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.minDelayMs))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.maxDelayMs))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.peakPowerMw))));
    }

    public final java.lang.String toString() {
        return "{.sensorType = " + android.hardware.contexthub.V1_0.SensorType.toString(this.sensorType) + ", .type = " + this.type + ", .name = " + this.name + ", .vendor = " + this.vendor + ", .version = " + this.version + ", .fifoReservedCount = " + this.fifoReservedCount + ", .fifoMaxCount = " + this.fifoMaxCount + ", .minDelayMs = " + this.minDelayMs + ", .maxDelayMs = " + this.maxDelayMs + ", .peakPowerMw = " + this.peakPowerMw + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(96L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.contexthub.V1_0.PhysicalSensor> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.contexthub.V1_0.PhysicalSensor> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 96, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.contexthub.V1_0.PhysicalSensor physicalSensor = new android.hardware.contexthub.V1_0.PhysicalSensor();
            physicalSensor.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 96);
            arrayList.add(physicalSensor);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.sensorType = hwBlob.getInt32(j + 0);
        long j2 = j + 8;
        this.type = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.type.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        long j3 = j + 24;
        this.name = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(this.name.getBytes().length + 1, hwBlob.handle(), j3 + 0, false);
        long j4 = j + 40;
        this.vendor = hwBlob.getString(j4);
        hwParcel.readEmbeddedBuffer(this.vendor.getBytes().length + 1, hwBlob.handle(), j4 + 0, false);
        this.version = hwBlob.getInt32(j + 56);
        this.fifoReservedCount = hwBlob.getInt32(j + 60);
        this.fifoMaxCount = hwBlob.getInt32(j + 64);
        this.minDelayMs = hwBlob.getInt64(j + 72);
        this.maxDelayMs = hwBlob.getInt64(j + 80);
        this.peakPowerMw = hwBlob.getFloat(j + 88);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(96);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.contexthub.V1_0.PhysicalSensor> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 96);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 96);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(0 + j, this.sensorType);
        hwBlob.putString(8 + j, this.type);
        hwBlob.putString(24 + j, this.name);
        hwBlob.putString(40 + j, this.vendor);
        hwBlob.putInt32(56 + j, this.version);
        hwBlob.putInt32(60 + j, this.fifoReservedCount);
        hwBlob.putInt32(64 + j, this.fifoMaxCount);
        hwBlob.putInt64(72 + j, this.minDelayMs);
        hwBlob.putInt64(80 + j, this.maxDelayMs);
        hwBlob.putFloat(j + 88, this.peakPowerMw);
    }
}
