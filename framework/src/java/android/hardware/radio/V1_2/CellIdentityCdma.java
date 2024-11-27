package android.hardware.radio.V1_2;

/* loaded from: classes2.dex */
public final class CellIdentityCdma {
    public android.hardware.radio.V1_0.CellIdentityCdma base = new android.hardware.radio.V1_0.CellIdentityCdma();
    public android.hardware.radio.V1_2.CellIdentityOperatorNames operatorNames = new android.hardware.radio.V1_2.CellIdentityOperatorNames();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_2.CellIdentityCdma.class) {
            return false;
        }
        android.hardware.radio.V1_2.CellIdentityCdma cellIdentityCdma = (android.hardware.radio.V1_2.CellIdentityCdma) obj;
        if (android.os.HidlSupport.deepEquals(this.base, cellIdentityCdma.base) && android.os.HidlSupport.deepEquals(this.operatorNames, cellIdentityCdma.operatorNames)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.base)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.operatorNames)));
    }

    public final java.lang.String toString() {
        return "{.base = " + this.base + ", .operatorNames = " + this.operatorNames + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(56L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_2.CellIdentityCdma> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_2.CellIdentityCdma> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 56, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_2.CellIdentityCdma cellIdentityCdma = new android.hardware.radio.V1_2.CellIdentityCdma();
            cellIdentityCdma.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 56);
            arrayList.add(cellIdentityCdma);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, 0 + j);
        this.operatorNames.readEmbeddedFromParcel(hwParcel, hwBlob, j + 24);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(56);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_2.CellIdentityCdma> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 56);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 56);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        this.base.writeEmbeddedToBlob(hwBlob, 0 + j);
        this.operatorNames.writeEmbeddedToBlob(hwBlob, j + 24);
    }
}
