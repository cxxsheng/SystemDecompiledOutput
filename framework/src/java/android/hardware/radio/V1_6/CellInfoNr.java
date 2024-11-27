package android.hardware.radio.V1_6;

/* loaded from: classes2.dex */
public final class CellInfoNr {
    public android.hardware.radio.V1_5.CellIdentityNr cellIdentityNr = new android.hardware.radio.V1_5.CellIdentityNr();
    public android.hardware.radio.V1_6.NrSignalStrength signalStrengthNr = new android.hardware.radio.V1_6.NrSignalStrength();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_6.CellInfoNr.class) {
            return false;
        }
        android.hardware.radio.V1_6.CellInfoNr cellInfoNr = (android.hardware.radio.V1_6.CellInfoNr) obj;
        if (android.os.HidlSupport.deepEquals(this.cellIdentityNr, cellInfoNr.cellIdentityNr) && android.os.HidlSupport.deepEquals(this.signalStrengthNr, cellInfoNr.signalStrengthNr)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.cellIdentityNr)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.signalStrengthNr)));
    }

    public final java.lang.String toString() {
        return "{.cellIdentityNr = " + this.cellIdentityNr + ", .signalStrengthNr = " + this.signalStrengthNr + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(168L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_6.CellInfoNr> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_6.CellInfoNr> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 168, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_6.CellInfoNr cellInfoNr = new android.hardware.radio.V1_6.CellInfoNr();
            cellInfoNr.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 168);
            arrayList.add(cellInfoNr);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.cellIdentityNr.readEmbeddedFromParcel(hwParcel, hwBlob, 0 + j);
        this.signalStrengthNr.readEmbeddedFromParcel(hwParcel, hwBlob, j + 120);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(168);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_6.CellInfoNr> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 168);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 168);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        this.cellIdentityNr.writeEmbeddedToBlob(hwBlob, 0 + j);
        this.signalStrengthNr.writeEmbeddedToBlob(hwBlob, j + 120);
    }
}
