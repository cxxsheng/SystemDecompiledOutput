package android.hardware.radio.V1_6;

/* loaded from: classes2.dex */
public final class NrQos {
    public short fiveQi = 0;
    public android.hardware.radio.V1_6.QosBandwidth downlink = new android.hardware.radio.V1_6.QosBandwidth();
    public android.hardware.radio.V1_6.QosBandwidth uplink = new android.hardware.radio.V1_6.QosBandwidth();
    public byte qfi = 0;
    public short averagingWindowMs = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_6.NrQos.class) {
            return false;
        }
        android.hardware.radio.V1_6.NrQos nrQos = (android.hardware.radio.V1_6.NrQos) obj;
        if (this.fiveQi == nrQos.fiveQi && android.os.HidlSupport.deepEquals(this.downlink, nrQos.downlink) && android.os.HidlSupport.deepEquals(this.uplink, nrQos.uplink) && this.qfi == nrQos.qfi && this.averagingWindowMs == nrQos.averagingWindowMs) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Short.valueOf(this.fiveQi))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.downlink)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.uplink)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.qfi))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Short.valueOf(this.averagingWindowMs))));
    }

    public final java.lang.String toString() {
        return "{.fiveQi = " + ((int) this.fiveQi) + ", .downlink = " + this.downlink + ", .uplink = " + this.uplink + ", .qfi = " + ((int) this.qfi) + ", .averagingWindowMs = " + ((int) this.averagingWindowMs) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_6.NrQos> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_6.NrQos> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_6.NrQos nrQos = new android.hardware.radio.V1_6.NrQos();
            nrQos.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 24);
            arrayList.add(nrQos);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.fiveQi = hwBlob.getInt16(0 + j);
        this.downlink.readEmbeddedFromParcel(hwParcel, hwBlob, 4 + j);
        this.uplink.readEmbeddedFromParcel(hwParcel, hwBlob, 12 + j);
        this.qfi = hwBlob.getInt8(20 + j);
        this.averagingWindowMs = hwBlob.getInt16(j + 22);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(24);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_6.NrQos> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 24);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 24);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt16(0 + j, this.fiveQi);
        this.downlink.writeEmbeddedToBlob(hwBlob, 4 + j);
        this.uplink.writeEmbeddedToBlob(hwBlob, 12 + j);
        hwBlob.putInt8(20 + j, this.qfi);
        hwBlob.putInt16(j + 22, this.averagingWindowMs);
    }
}
