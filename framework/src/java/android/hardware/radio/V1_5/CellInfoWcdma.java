package android.hardware.radio.V1_5;

/* loaded from: classes2.dex */
public final class CellInfoWcdma {
    public android.hardware.radio.V1_5.CellIdentityWcdma cellIdentityWcdma = new android.hardware.radio.V1_5.CellIdentityWcdma();
    public android.hardware.radio.V1_2.WcdmaSignalStrength signalStrengthWcdma = new android.hardware.radio.V1_2.WcdmaSignalStrength();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_5.CellInfoWcdma.class) {
            return false;
        }
        android.hardware.radio.V1_5.CellInfoWcdma cellInfoWcdma = (android.hardware.radio.V1_5.CellInfoWcdma) obj;
        if (android.os.HidlSupport.deepEquals(this.cellIdentityWcdma, cellInfoWcdma.cellIdentityWcdma) && android.os.HidlSupport.deepEquals(this.signalStrengthWcdma, cellInfoWcdma.signalStrengthWcdma)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.cellIdentityWcdma)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.signalStrengthWcdma)));
    }

    public final java.lang.String toString() {
        return "{.cellIdentityWcdma = " + this.cellIdentityWcdma + ", .signalStrengthWcdma = " + this.signalStrengthWcdma + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(152L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_5.CellInfoWcdma> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_5.CellInfoWcdma> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 152, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_5.CellInfoWcdma cellInfoWcdma = new android.hardware.radio.V1_5.CellInfoWcdma();
            cellInfoWcdma.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 152);
            arrayList.add(cellInfoWcdma);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.cellIdentityWcdma.readEmbeddedFromParcel(hwParcel, hwBlob, 0 + j);
        this.signalStrengthWcdma.readEmbeddedFromParcel(hwParcel, hwBlob, j + 136);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(152);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_5.CellInfoWcdma> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 152);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 152);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        this.cellIdentityWcdma.writeEmbeddedToBlob(hwBlob, 0 + j);
        this.signalStrengthWcdma.writeEmbeddedToBlob(hwBlob, j + 136);
    }
}
