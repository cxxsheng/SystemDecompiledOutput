package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class IccIo {
    public int command = 0;
    public int fileId = 0;
    public java.lang.String path = new java.lang.String();
    public int p1 = 0;
    public int p2 = 0;
    public int p3 = 0;
    public java.lang.String data = new java.lang.String();
    public java.lang.String pin2 = new java.lang.String();
    public java.lang.String aid = new java.lang.String();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.IccIo.class) {
            return false;
        }
        android.hardware.radio.V1_0.IccIo iccIo = (android.hardware.radio.V1_0.IccIo) obj;
        if (this.command == iccIo.command && this.fileId == iccIo.fileId && android.os.HidlSupport.deepEquals(this.path, iccIo.path) && this.p1 == iccIo.p1 && this.p2 == iccIo.p2 && this.p3 == iccIo.p3 && android.os.HidlSupport.deepEquals(this.data, iccIo.data) && android.os.HidlSupport.deepEquals(this.pin2, iccIo.pin2) && android.os.HidlSupport.deepEquals(this.aid, iccIo.aid)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.command))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.fileId))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.path)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.p1))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.p2))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.p3))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.data)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.pin2)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.aid)));
    }

    public final java.lang.String toString() {
        return "{.command = " + this.command + ", .fileId = " + this.fileId + ", .path = " + this.path + ", .p1 = " + this.p1 + ", .p2 = " + this.p2 + ", .p3 = " + this.p3 + ", .data = " + this.data + ", .pin2 = " + this.pin2 + ", .aid = " + this.aid + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(88L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.IccIo> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.IccIo> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 88, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.IccIo iccIo = new android.hardware.radio.V1_0.IccIo();
            iccIo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 88);
            arrayList.add(iccIo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.command = hwBlob.getInt32(j + 0);
        this.fileId = hwBlob.getInt32(j + 4);
        long j2 = j + 8;
        this.path = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.path.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        this.p1 = hwBlob.getInt32(j + 24);
        this.p2 = hwBlob.getInt32(j + 28);
        this.p3 = hwBlob.getInt32(j + 32);
        long j3 = j + 40;
        this.data = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(this.data.getBytes().length + 1, hwBlob.handle(), j3 + 0, false);
        long j4 = j + 56;
        this.pin2 = hwBlob.getString(j4);
        hwParcel.readEmbeddedBuffer(this.pin2.getBytes().length + 1, hwBlob.handle(), j4 + 0, false);
        long j5 = j + 72;
        this.aid = hwBlob.getString(j5);
        hwParcel.readEmbeddedBuffer(this.aid.getBytes().length + 1, hwBlob.handle(), j5 + 0, false);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(88);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.IccIo> arrayList) {
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
        hwBlob.putInt32(0 + j, this.command);
        hwBlob.putInt32(4 + j, this.fileId);
        hwBlob.putString(8 + j, this.path);
        hwBlob.putInt32(24 + j, this.p1);
        hwBlob.putInt32(28 + j, this.p2);
        hwBlob.putInt32(32 + j, this.p3);
        hwBlob.putString(40 + j, this.data);
        hwBlob.putString(56 + j, this.pin2);
        hwBlob.putString(j + 72, this.aid);
    }
}
