package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class CellIdentityGsm {
    public java.lang.String mcc = new java.lang.String();
    public java.lang.String mnc = new java.lang.String();
    public int lac = 0;
    public int cid = 0;
    public int arfcn = 0;
    public byte bsic = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.CellIdentityGsm.class) {
            return false;
        }
        android.hardware.radio.V1_0.CellIdentityGsm cellIdentityGsm = (android.hardware.radio.V1_0.CellIdentityGsm) obj;
        if (android.os.HidlSupport.deepEquals(this.mcc, cellIdentityGsm.mcc) && android.os.HidlSupport.deepEquals(this.mnc, cellIdentityGsm.mnc) && this.lac == cellIdentityGsm.lac && this.cid == cellIdentityGsm.cid && this.arfcn == cellIdentityGsm.arfcn && this.bsic == cellIdentityGsm.bsic) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.mcc)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.mnc)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.lac))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.cid))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.arfcn))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.bsic))));
    }

    public final java.lang.String toString() {
        return "{.mcc = " + this.mcc + ", .mnc = " + this.mnc + ", .lac = " + this.lac + ", .cid = " + this.cid + ", .arfcn = " + this.arfcn + ", .bsic = " + ((int) this.bsic) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(48L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.CellIdentityGsm> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.CellIdentityGsm> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 48, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.CellIdentityGsm cellIdentityGsm = new android.hardware.radio.V1_0.CellIdentityGsm();
            cellIdentityGsm.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 48);
            arrayList.add(cellIdentityGsm);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        long j2 = j + 0;
        this.mcc = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.mcc.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        long j3 = j + 16;
        this.mnc = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(this.mnc.getBytes().length + 1, hwBlob.handle(), j3 + 0, false);
        this.lac = hwBlob.getInt32(j + 32);
        this.cid = hwBlob.getInt32(j + 36);
        this.arfcn = hwBlob.getInt32(j + 40);
        this.bsic = hwBlob.getInt8(j + 44);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(48);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.CellIdentityGsm> arrayList) {
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
        hwBlob.putString(0 + j, this.mcc);
        hwBlob.putString(16 + j, this.mnc);
        hwBlob.putInt32(32 + j, this.lac);
        hwBlob.putInt32(36 + j, this.cid);
        hwBlob.putInt32(40 + j, this.arfcn);
        hwBlob.putInt8(j + 44, this.bsic);
    }
}
