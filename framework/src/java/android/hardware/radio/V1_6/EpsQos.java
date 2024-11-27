package android.hardware.radio.V1_6;

/* loaded from: classes2.dex */
public final class EpsQos {
    public short qci = 0;
    public android.hardware.radio.V1_6.QosBandwidth downlink = new android.hardware.radio.V1_6.QosBandwidth();
    public android.hardware.radio.V1_6.QosBandwidth uplink = new android.hardware.radio.V1_6.QosBandwidth();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_6.EpsQos.class) {
            return false;
        }
        android.hardware.radio.V1_6.EpsQos epsQos = (android.hardware.radio.V1_6.EpsQos) obj;
        if (this.qci == epsQos.qci && android.os.HidlSupport.deepEquals(this.downlink, epsQos.downlink) && android.os.HidlSupport.deepEquals(this.uplink, epsQos.uplink)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Short.valueOf(this.qci))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.downlink)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.uplink)));
    }

    public final java.lang.String toString() {
        return "{.qci = " + ((int) this.qci) + ", .downlink = " + this.downlink + ", .uplink = " + this.uplink + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(20L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_6.EpsQos> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_6.EpsQos> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 20, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_6.EpsQos epsQos = new android.hardware.radio.V1_6.EpsQos();
            epsQos.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 20);
            arrayList.add(epsQos);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.qci = hwBlob.getInt16(0 + j);
        this.downlink.readEmbeddedFromParcel(hwParcel, hwBlob, 4 + j);
        this.uplink.readEmbeddedFromParcel(hwParcel, hwBlob, j + 12);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(20);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_6.EpsQos> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 20);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 20);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt16(0 + j, this.qci);
        this.downlink.writeEmbeddedToBlob(hwBlob, 4 + j);
        this.uplink.writeEmbeddedToBlob(hwBlob, j + 12);
    }
}
