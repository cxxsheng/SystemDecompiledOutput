package android.hardware.radio.V1_5;

/* loaded from: classes2.dex */
public final class CellInfoTdscdma {
    public android.hardware.radio.V1_5.CellIdentityTdscdma cellIdentityTdscdma = new android.hardware.radio.V1_5.CellIdentityTdscdma();
    public android.hardware.radio.V1_2.TdscdmaSignalStrength signalStrengthTdscdma = new android.hardware.radio.V1_2.TdscdmaSignalStrength();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_5.CellInfoTdscdma.class) {
            return false;
        }
        android.hardware.radio.V1_5.CellInfoTdscdma cellInfoTdscdma = (android.hardware.radio.V1_5.CellInfoTdscdma) obj;
        if (android.os.HidlSupport.deepEquals(this.cellIdentityTdscdma, cellInfoTdscdma.cellIdentityTdscdma) && android.os.HidlSupport.deepEquals(this.signalStrengthTdscdma, cellInfoTdscdma.signalStrengthTdscdma)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.cellIdentityTdscdma)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.signalStrengthTdscdma)));
    }

    public final java.lang.String toString() {
        return "{.cellIdentityTdscdma = " + this.cellIdentityTdscdma + ", .signalStrengthTdscdma = " + this.signalStrengthTdscdma + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(160L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_5.CellInfoTdscdma> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_5.CellInfoTdscdma> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 160, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_5.CellInfoTdscdma cellInfoTdscdma = new android.hardware.radio.V1_5.CellInfoTdscdma();
            cellInfoTdscdma.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 160);
            arrayList.add(cellInfoTdscdma);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.cellIdentityTdscdma.readEmbeddedFromParcel(hwParcel, hwBlob, 0 + j);
        this.signalStrengthTdscdma.readEmbeddedFromParcel(hwParcel, hwBlob, j + 144);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(160);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_5.CellInfoTdscdma> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 160);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 160);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        this.cellIdentityTdscdma.writeEmbeddedToBlob(hwBlob, 0 + j);
        this.signalStrengthTdscdma.writeEmbeddedToBlob(hwBlob, j + 144);
    }
}
