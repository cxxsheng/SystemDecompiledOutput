package android.hardware.radio.V1_2;

/* loaded from: classes2.dex */
public final class CellIdentity {
    public int cellInfoType = 0;
    public java.util.ArrayList<android.hardware.radio.V1_2.CellIdentityGsm> cellIdentityGsm = new java.util.ArrayList<>();
    public java.util.ArrayList<android.hardware.radio.V1_2.CellIdentityWcdma> cellIdentityWcdma = new java.util.ArrayList<>();
    public java.util.ArrayList<android.hardware.radio.V1_2.CellIdentityCdma> cellIdentityCdma = new java.util.ArrayList<>();
    public java.util.ArrayList<android.hardware.radio.V1_2.CellIdentityLte> cellIdentityLte = new java.util.ArrayList<>();
    public java.util.ArrayList<android.hardware.radio.V1_2.CellIdentityTdscdma> cellIdentityTdscdma = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_2.CellIdentity.class) {
            return false;
        }
        android.hardware.radio.V1_2.CellIdentity cellIdentity = (android.hardware.radio.V1_2.CellIdentity) obj;
        if (this.cellInfoType == cellIdentity.cellInfoType && android.os.HidlSupport.deepEquals(this.cellIdentityGsm, cellIdentity.cellIdentityGsm) && android.os.HidlSupport.deepEquals(this.cellIdentityWcdma, cellIdentity.cellIdentityWcdma) && android.os.HidlSupport.deepEquals(this.cellIdentityCdma, cellIdentity.cellIdentityCdma) && android.os.HidlSupport.deepEquals(this.cellIdentityLte, cellIdentity.cellIdentityLte) && android.os.HidlSupport.deepEquals(this.cellIdentityTdscdma, cellIdentity.cellIdentityTdscdma)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.cellInfoType))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.cellIdentityGsm)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.cellIdentityWcdma)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.cellIdentityCdma)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.cellIdentityLte)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.cellIdentityTdscdma)));
    }

    public final java.lang.String toString() {
        return "{.cellInfoType = " + android.hardware.radio.V1_0.CellInfoType.toString(this.cellInfoType) + ", .cellIdentityGsm = " + this.cellIdentityGsm + ", .cellIdentityWcdma = " + this.cellIdentityWcdma + ", .cellIdentityCdma = " + this.cellIdentityCdma + ", .cellIdentityLte = " + this.cellIdentityLte + ", .cellIdentityTdscdma = " + this.cellIdentityTdscdma + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(88L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_2.CellIdentity> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_2.CellIdentity> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 88, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_2.CellIdentity cellIdentity = new android.hardware.radio.V1_2.CellIdentity();
            cellIdentity.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 88);
            arrayList.add(cellIdentity);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.cellInfoType = hwBlob.getInt32(j + 0);
        long j2 = j + 8;
        int int32 = hwBlob.getInt32(j2 + 8);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 80, hwBlob.handle(), j2 + 0, true);
        this.cellIdentityGsm.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_2.CellIdentityGsm cellIdentityGsm = new android.hardware.radio.V1_2.CellIdentityGsm();
            cellIdentityGsm.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 80);
            this.cellIdentityGsm.add(cellIdentityGsm);
        }
        long j3 = j + 24;
        int int322 = hwBlob.getInt32(j3 + 8);
        android.os.HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 80, hwBlob.handle(), j3 + 0, true);
        this.cellIdentityWcdma.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            android.hardware.radio.V1_2.CellIdentityWcdma cellIdentityWcdma = new android.hardware.radio.V1_2.CellIdentityWcdma();
            cellIdentityWcdma.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer2, i2 * 80);
            this.cellIdentityWcdma.add(cellIdentityWcdma);
        }
        long j4 = j + 40;
        int int323 = hwBlob.getInt32(j4 + 8);
        android.os.HwBlob readEmbeddedBuffer3 = hwParcel.readEmbeddedBuffer(int323 * 56, hwBlob.handle(), j4 + 0, true);
        this.cellIdentityCdma.clear();
        for (int i3 = 0; i3 < int323; i3++) {
            android.hardware.radio.V1_2.CellIdentityCdma cellIdentityCdma = new android.hardware.radio.V1_2.CellIdentityCdma();
            cellIdentityCdma.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer3, i3 * 56);
            this.cellIdentityCdma.add(cellIdentityCdma);
        }
        long j5 = j + 56;
        int int324 = hwBlob.getInt32(j5 + 8);
        android.os.HwBlob readEmbeddedBuffer4 = hwParcel.readEmbeddedBuffer(int324 * 88, hwBlob.handle(), j5 + 0, true);
        this.cellIdentityLte.clear();
        for (int i4 = 0; i4 < int324; i4++) {
            android.hardware.radio.V1_2.CellIdentityLte cellIdentityLte = new android.hardware.radio.V1_2.CellIdentityLte();
            cellIdentityLte.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer4, i4 * 88);
            this.cellIdentityLte.add(cellIdentityLte);
        }
        long j6 = j + 72;
        int int325 = hwBlob.getInt32(8 + j6);
        android.os.HwBlob readEmbeddedBuffer5 = hwParcel.readEmbeddedBuffer(int325 * 88, hwBlob.handle(), j6 + 0, true);
        this.cellIdentityTdscdma.clear();
        for (int i5 = 0; i5 < int325; i5++) {
            android.hardware.radio.V1_2.CellIdentityTdscdma cellIdentityTdscdma = new android.hardware.radio.V1_2.CellIdentityTdscdma();
            cellIdentityTdscdma.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer5, i5 * 88);
            this.cellIdentityTdscdma.add(cellIdentityTdscdma);
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(88);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_2.CellIdentity> arrayList) {
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
        hwBlob.putInt32(j + 0, this.cellInfoType);
        int size = this.cellIdentityGsm.size();
        long j2 = j + 8;
        hwBlob.putInt32(j2 + 8, size);
        hwBlob.putBool(j2 + 12, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 80);
        for (int i = 0; i < size; i++) {
            this.cellIdentityGsm.get(i).writeEmbeddedToBlob(hwBlob2, i * 80);
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
        int size2 = this.cellIdentityWcdma.size();
        long j3 = j + 24;
        hwBlob.putInt32(j3 + 8, size2);
        hwBlob.putBool(j3 + 12, false);
        android.os.HwBlob hwBlob3 = new android.os.HwBlob(size2 * 80);
        for (int i2 = 0; i2 < size2; i2++) {
            this.cellIdentityWcdma.get(i2).writeEmbeddedToBlob(hwBlob3, i2 * 80);
        }
        hwBlob.putBlob(j3 + 0, hwBlob3);
        int size3 = this.cellIdentityCdma.size();
        long j4 = j + 40;
        hwBlob.putInt32(j4 + 8, size3);
        hwBlob.putBool(j4 + 12, false);
        android.os.HwBlob hwBlob4 = new android.os.HwBlob(size3 * 56);
        for (int i3 = 0; i3 < size3; i3++) {
            this.cellIdentityCdma.get(i3).writeEmbeddedToBlob(hwBlob4, i3 * 56);
        }
        hwBlob.putBlob(j4 + 0, hwBlob4);
        int size4 = this.cellIdentityLte.size();
        long j5 = j + 56;
        hwBlob.putInt32(j5 + 8, size4);
        hwBlob.putBool(j5 + 12, false);
        android.os.HwBlob hwBlob5 = new android.os.HwBlob(size4 * 88);
        for (int i4 = 0; i4 < size4; i4++) {
            this.cellIdentityLte.get(i4).writeEmbeddedToBlob(hwBlob5, i4 * 88);
        }
        hwBlob.putBlob(j5 + 0, hwBlob5);
        int size5 = this.cellIdentityTdscdma.size();
        long j6 = j + 72;
        hwBlob.putInt32(8 + j6, size5);
        hwBlob.putBool(j6 + 12, false);
        android.os.HwBlob hwBlob6 = new android.os.HwBlob(size5 * 88);
        for (int i5 = 0; i5 < size5; i5++) {
            this.cellIdentityTdscdma.get(i5).writeEmbeddedToBlob(hwBlob6, i5 * 88);
        }
        hwBlob.putBlob(j6 + 0, hwBlob6);
    }
}
