package android.hardware.radio.V1_5;

/* loaded from: classes2.dex */
public final class CellInfoLte {
    public android.hardware.radio.V1_5.CellIdentityLte cellIdentityLte = new android.hardware.radio.V1_5.CellIdentityLte();
    public android.hardware.radio.V1_0.LteSignalStrength signalStrengthLte = new android.hardware.radio.V1_0.LteSignalStrength();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_5.CellInfoLte.class) {
            return false;
        }
        android.hardware.radio.V1_5.CellInfoLte cellInfoLte = (android.hardware.radio.V1_5.CellInfoLte) obj;
        if (android.os.HidlSupport.deepEquals(this.cellIdentityLte, cellInfoLte.cellIdentityLte) && android.os.HidlSupport.deepEquals(this.signalStrengthLte, cellInfoLte.signalStrengthLte)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.cellIdentityLte)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.signalStrengthLte)));
    }

    public final java.lang.String toString() {
        return "{.cellIdentityLte = " + this.cellIdentityLte + ", .signalStrengthLte = " + this.signalStrengthLte + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(184L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_5.CellInfoLte> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_5.CellInfoLte> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 184, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_5.CellInfoLte cellInfoLte = new android.hardware.radio.V1_5.CellInfoLte();
            cellInfoLte.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 184);
            arrayList.add(cellInfoLte);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.cellIdentityLte.readEmbeddedFromParcel(hwParcel, hwBlob, 0 + j);
        this.signalStrengthLte.readEmbeddedFromParcel(hwParcel, hwBlob, j + 160);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(184);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_5.CellInfoLte> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 184);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 184);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        this.cellIdentityLte.writeEmbeddedToBlob(hwBlob, 0 + j);
        this.signalStrengthLte.writeEmbeddedToBlob(hwBlob, j + 160);
    }
}
