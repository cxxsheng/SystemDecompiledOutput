package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class PcoDataInfo {
    public int cid = 0;
    public java.lang.String bearerProto = new java.lang.String();
    public int pcoId = 0;
    public java.util.ArrayList<java.lang.Byte> contents = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.PcoDataInfo.class) {
            return false;
        }
        android.hardware.radio.V1_0.PcoDataInfo pcoDataInfo = (android.hardware.radio.V1_0.PcoDataInfo) obj;
        if (this.cid == pcoDataInfo.cid && android.os.HidlSupport.deepEquals(this.bearerProto, pcoDataInfo.bearerProto) && this.pcoId == pcoDataInfo.pcoId && android.os.HidlSupport.deepEquals(this.contents, pcoDataInfo.contents)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.cid))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.bearerProto)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.pcoId))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.contents)));
    }

    public final java.lang.String toString() {
        return "{.cid = " + this.cid + ", .bearerProto = " + this.bearerProto + ", .pcoId = " + this.pcoId + ", .contents = " + this.contents + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(48L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.PcoDataInfo> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.PcoDataInfo> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 48, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.PcoDataInfo pcoDataInfo = new android.hardware.radio.V1_0.PcoDataInfo();
            pcoDataInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 48);
            arrayList.add(pcoDataInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.cid = hwBlob.getInt32(j + 0);
        long j2 = j + 8;
        this.bearerProto = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.bearerProto.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        this.pcoId = hwBlob.getInt32(j + 24);
        long j3 = j + 32;
        int int32 = hwBlob.getInt32(8 + j3);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 1, hwBlob.handle(), j3 + 0, true);
        this.contents.clear();
        for (int i = 0; i < int32; i++) {
            this.contents.add(java.lang.Byte.valueOf(readEmbeddedBuffer.getInt8(i * 1)));
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(48);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.PcoDataInfo> arrayList) {
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
        hwBlob.putInt32(j + 0, this.cid);
        hwBlob.putString(j + 8, this.bearerProto);
        hwBlob.putInt32(24 + j, this.pcoId);
        int size = this.contents.size();
        long j2 = j + 32;
        hwBlob.putInt32(8 + j2, size);
        hwBlob.putBool(12 + j2, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 1);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt8(i * 1, this.contents.get(i).byteValue());
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
    }
}
