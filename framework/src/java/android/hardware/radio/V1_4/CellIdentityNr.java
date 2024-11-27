package android.hardware.radio.V1_4;

/* loaded from: classes2.dex */
public final class CellIdentityNr {
    public java.lang.String mcc = new java.lang.String();
    public java.lang.String mnc = new java.lang.String();
    public long nci = 0;
    public int pci = 0;
    public int tac = 0;
    public int nrarfcn = 0;
    public android.hardware.radio.V1_2.CellIdentityOperatorNames operatorNames = new android.hardware.radio.V1_2.CellIdentityOperatorNames();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_4.CellIdentityNr.class) {
            return false;
        }
        android.hardware.radio.V1_4.CellIdentityNr cellIdentityNr = (android.hardware.radio.V1_4.CellIdentityNr) obj;
        if (android.os.HidlSupport.deepEquals(this.mcc, cellIdentityNr.mcc) && android.os.HidlSupport.deepEquals(this.mnc, cellIdentityNr.mnc) && this.nci == cellIdentityNr.nci && this.pci == cellIdentityNr.pci && this.tac == cellIdentityNr.tac && this.nrarfcn == cellIdentityNr.nrarfcn && android.os.HidlSupport.deepEquals(this.operatorNames, cellIdentityNr.operatorNames)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.mcc)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.mnc)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.nci))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.pci))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.tac))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.nrarfcn))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.operatorNames)));
    }

    public final java.lang.String toString() {
        return "{.mcc = " + this.mcc + ", .mnc = " + this.mnc + ", .nci = " + this.nci + ", .pci = " + this.pci + ", .tac = " + this.tac + ", .nrarfcn = " + this.nrarfcn + ", .operatorNames = " + this.operatorNames + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(88L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_4.CellIdentityNr> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_4.CellIdentityNr> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 88, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_4.CellIdentityNr cellIdentityNr = new android.hardware.radio.V1_4.CellIdentityNr();
            cellIdentityNr.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 88);
            arrayList.add(cellIdentityNr);
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
        this.nci = hwBlob.getInt64(j + 32);
        this.pci = hwBlob.getInt32(j + 40);
        this.tac = hwBlob.getInt32(j + 44);
        this.nrarfcn = hwBlob.getInt32(j + 48);
        this.operatorNames.readEmbeddedFromParcel(hwParcel, hwBlob, j + 56);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(88);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_4.CellIdentityNr> arrayList) {
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
        hwBlob.putString(0 + j, this.mcc);
        hwBlob.putString(16 + j, this.mnc);
        hwBlob.putInt64(32 + j, this.nci);
        hwBlob.putInt32(40 + j, this.pci);
        hwBlob.putInt32(44 + j, this.tac);
        hwBlob.putInt32(48 + j, this.nrarfcn);
        this.operatorNames.writeEmbeddedToBlob(hwBlob, j + 56);
    }
}
