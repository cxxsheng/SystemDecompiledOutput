package android.hardware.radio.V1_6;

/* loaded from: classes2.dex */
public final class TrafficDescriptor {
    public android.hardware.radio.V1_6.OptionalDnn dnn = new android.hardware.radio.V1_6.OptionalDnn();
    public android.hardware.radio.V1_6.OptionalOsAppId osAppId = new android.hardware.radio.V1_6.OptionalOsAppId();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_6.TrafficDescriptor.class) {
            return false;
        }
        android.hardware.radio.V1_6.TrafficDescriptor trafficDescriptor = (android.hardware.radio.V1_6.TrafficDescriptor) obj;
        if (android.os.HidlSupport.deepEquals(this.dnn, trafficDescriptor.dnn) && android.os.HidlSupport.deepEquals(this.osAppId, trafficDescriptor.osAppId)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.dnn)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.osAppId)));
    }

    public final java.lang.String toString() {
        return "{.dnn = " + this.dnn + ", .osAppId = " + this.osAppId + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(48L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_6.TrafficDescriptor> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_6.TrafficDescriptor> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 48, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_6.TrafficDescriptor trafficDescriptor = new android.hardware.radio.V1_6.TrafficDescriptor();
            trafficDescriptor.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 48);
            arrayList.add(trafficDescriptor);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.dnn.readEmbeddedFromParcel(hwParcel, hwBlob, 0 + j);
        this.osAppId.readEmbeddedFromParcel(hwParcel, hwBlob, j + 24);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(48);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_6.TrafficDescriptor> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 48);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 48);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        this.dnn.writeEmbeddedToBlob(hwBlob, 0 + j);
        this.osAppId.writeEmbeddedToBlob(hwBlob, j + 24);
    }
}
