package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class CellInfoCdma {
    public android.hardware.radio.V1_0.CellIdentityCdma cellIdentityCdma = new android.hardware.radio.V1_0.CellIdentityCdma();
    public android.hardware.radio.V1_0.CdmaSignalStrength signalStrengthCdma = new android.hardware.radio.V1_0.CdmaSignalStrength();
    public android.hardware.radio.V1_0.EvdoSignalStrength signalStrengthEvdo = new android.hardware.radio.V1_0.EvdoSignalStrength();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.CellInfoCdma.class) {
            return false;
        }
        android.hardware.radio.V1_0.CellInfoCdma cellInfoCdma = (android.hardware.radio.V1_0.CellInfoCdma) obj;
        if (android.os.HidlSupport.deepEquals(this.cellIdentityCdma, cellInfoCdma.cellIdentityCdma) && android.os.HidlSupport.deepEquals(this.signalStrengthCdma, cellInfoCdma.signalStrengthCdma) && android.os.HidlSupport.deepEquals(this.signalStrengthEvdo, cellInfoCdma.signalStrengthEvdo)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.cellIdentityCdma)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.signalStrengthCdma)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.signalStrengthEvdo)));
    }

    public final java.lang.String toString() {
        return "{.cellIdentityCdma = " + this.cellIdentityCdma + ", .signalStrengthCdma = " + this.signalStrengthCdma + ", .signalStrengthEvdo = " + this.signalStrengthEvdo + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.CellInfoCdma> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.CellInfoCdma> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.CellInfoCdma cellInfoCdma = new android.hardware.radio.V1_0.CellInfoCdma();
            cellInfoCdma.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 40);
            arrayList.add(cellInfoCdma);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.cellIdentityCdma.readEmbeddedFromParcel(hwParcel, hwBlob, 0 + j);
        this.signalStrengthCdma.readEmbeddedFromParcel(hwParcel, hwBlob, 20 + j);
        this.signalStrengthEvdo.readEmbeddedFromParcel(hwParcel, hwBlob, j + 28);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(40);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.CellInfoCdma> arrayList) {
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
        this.cellIdentityCdma.writeEmbeddedToBlob(hwBlob, 0 + j);
        this.signalStrengthCdma.writeEmbeddedToBlob(hwBlob, 20 + j);
        this.signalStrengthEvdo.writeEmbeddedToBlob(hwBlob, j + 28);
    }
}
