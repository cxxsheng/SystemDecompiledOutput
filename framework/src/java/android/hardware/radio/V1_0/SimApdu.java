package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class SimApdu {
    public int sessionId = 0;
    public int cla = 0;
    public int instruction = 0;
    public int p1 = 0;
    public int p2 = 0;
    public int p3 = 0;
    public java.lang.String data = new java.lang.String();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.SimApdu.class) {
            return false;
        }
        android.hardware.radio.V1_0.SimApdu simApdu = (android.hardware.radio.V1_0.SimApdu) obj;
        if (this.sessionId == simApdu.sessionId && this.cla == simApdu.cla && this.instruction == simApdu.instruction && this.p1 == simApdu.p1 && this.p2 == simApdu.p2 && this.p3 == simApdu.p3 && android.os.HidlSupport.deepEquals(this.data, simApdu.data)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.sessionId))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.cla))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.instruction))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.p1))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.p2))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.p3))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.data)));
    }

    public final java.lang.String toString() {
        return "{.sessionId = " + this.sessionId + ", .cla = " + this.cla + ", .instruction = " + this.instruction + ", .p1 = " + this.p1 + ", .p2 = " + this.p2 + ", .p3 = " + this.p3 + ", .data = " + this.data + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.SimApdu> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.SimApdu> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.SimApdu simApdu = new android.hardware.radio.V1_0.SimApdu();
            simApdu.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 40);
            arrayList.add(simApdu);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.sessionId = hwBlob.getInt32(j + 0);
        this.cla = hwBlob.getInt32(4 + j);
        this.instruction = hwBlob.getInt32(8 + j);
        this.p1 = hwBlob.getInt32(12 + j);
        this.p2 = hwBlob.getInt32(16 + j);
        this.p3 = hwBlob.getInt32(20 + j);
        long j2 = j + 24;
        this.data = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.data.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(40);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.SimApdu> arrayList) {
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
        hwBlob.putInt32(0 + j, this.sessionId);
        hwBlob.putInt32(4 + j, this.cla);
        hwBlob.putInt32(8 + j, this.instruction);
        hwBlob.putInt32(12 + j, this.p1);
        hwBlob.putInt32(16 + j, this.p2);
        hwBlob.putInt32(20 + j, this.p3);
        hwBlob.putString(j + 24, this.data);
    }
}
