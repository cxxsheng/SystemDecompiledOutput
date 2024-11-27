package android.hardware.radio.V1_4;

/* loaded from: classes2.dex */
public final class CellInfoNr {
    public android.hardware.radio.V1_4.NrSignalStrength signalStrength = new android.hardware.radio.V1_4.NrSignalStrength();
    public android.hardware.radio.V1_4.CellIdentityNr cellidentity = new android.hardware.radio.V1_4.CellIdentityNr();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_4.CellInfoNr.class) {
            return false;
        }
        android.hardware.radio.V1_4.CellInfoNr cellInfoNr = (android.hardware.radio.V1_4.CellInfoNr) obj;
        if (android.os.HidlSupport.deepEquals(this.signalStrength, cellInfoNr.signalStrength) && android.os.HidlSupport.deepEquals(this.cellidentity, cellInfoNr.cellidentity)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.signalStrength)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.cellidentity)));
    }

    public final java.lang.String toString() {
        return "{.signalStrength = " + this.signalStrength + ", .cellidentity = " + this.cellidentity + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(112L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_4.CellInfoNr> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_4.CellInfoNr> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 112, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_4.CellInfoNr cellInfoNr = new android.hardware.radio.V1_4.CellInfoNr();
            cellInfoNr.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 112);
            arrayList.add(cellInfoNr);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.signalStrength.readEmbeddedFromParcel(hwParcel, hwBlob, 0 + j);
        this.cellidentity.readEmbeddedFromParcel(hwParcel, hwBlob, j + 24);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(112);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_4.CellInfoNr> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 112);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 112);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        this.signalStrength.writeEmbeddedToBlob(hwBlob, 0 + j);
        this.cellidentity.writeEmbeddedToBlob(hwBlob, j + 24);
    }
}
