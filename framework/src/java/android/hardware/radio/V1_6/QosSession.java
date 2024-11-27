package android.hardware.radio.V1_6;

/* loaded from: classes2.dex */
public final class QosSession {
    public int qosSessionId = 0;
    public android.hardware.radio.V1_6.Qos qos = new android.hardware.radio.V1_6.Qos();
    public java.util.ArrayList<android.hardware.radio.V1_6.QosFilter> qosFilters = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_6.QosSession.class) {
            return false;
        }
        android.hardware.radio.V1_6.QosSession qosSession = (android.hardware.radio.V1_6.QosSession) obj;
        if (this.qosSessionId == qosSession.qosSessionId && android.os.HidlSupport.deepEquals(this.qos, qosSession.qos) && android.os.HidlSupport.deepEquals(this.qosFilters, qosSession.qosFilters)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.qosSessionId))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.qos)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.qosFilters)));
    }

    public final java.lang.String toString() {
        return "{.qosSessionId = " + this.qosSessionId + ", .qos = " + this.qos + ", .qosFilters = " + this.qosFilters + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(48L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_6.QosSession> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_6.QosSession> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 48, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_6.QosSession qosSession = new android.hardware.radio.V1_6.QosSession();
            qosSession.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 48);
            arrayList.add(qosSession);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.qosSessionId = hwBlob.getInt32(j + 0);
        this.qos.readEmbeddedFromParcel(hwParcel, hwBlob, j + 4);
        long j2 = j + 32;
        int int32 = hwBlob.getInt32(8 + j2);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 88, hwBlob.handle(), j2 + 0, true);
        this.qosFilters.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_6.QosFilter qosFilter = new android.hardware.radio.V1_6.QosFilter();
            qosFilter.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 88);
            this.qosFilters.add(qosFilter);
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(48);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_6.QosSession> arrayList) {
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
        hwBlob.putInt32(j + 0, this.qosSessionId);
        this.qos.writeEmbeddedToBlob(hwBlob, 4 + j);
        int size = this.qosFilters.size();
        long j2 = j + 32;
        hwBlob.putInt32(8 + j2, size);
        hwBlob.putBool(12 + j2, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 88);
        for (int i = 0; i < size; i++) {
            this.qosFilters.get(i).writeEmbeddedToBlob(hwBlob2, i * 88);
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
    }
}
