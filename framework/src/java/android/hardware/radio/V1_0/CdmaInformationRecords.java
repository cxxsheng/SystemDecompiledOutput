package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class CdmaInformationRecords {
    public java.util.ArrayList<android.hardware.radio.V1_0.CdmaInformationRecord> infoRec = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && obj.getClass() == android.hardware.radio.V1_0.CdmaInformationRecords.class && android.os.HidlSupport.deepEquals(this.infoRec, ((android.hardware.radio.V1_0.CdmaInformationRecords) obj).infoRec)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.infoRec)));
    }

    public final java.lang.String toString() {
        return "{.infoRec = " + this.infoRec + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(16L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.CdmaInformationRecords> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.CdmaInformationRecords> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.CdmaInformationRecords cdmaInformationRecords = new android.hardware.radio.V1_0.CdmaInformationRecords();
            cdmaInformationRecords.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 16);
            arrayList.add(cdmaInformationRecords);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        long j2 = j + 0;
        int int32 = hwBlob.getInt32(8 + j2);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 120, hwBlob.handle(), j2 + 0, true);
        this.infoRec.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.CdmaInformationRecord cdmaInformationRecord = new android.hardware.radio.V1_0.CdmaInformationRecord();
            cdmaInformationRecord.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 120);
            this.infoRec.add(cdmaInformationRecord);
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.CdmaInformationRecords> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 16);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 16);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        int size = this.infoRec.size();
        long j2 = j + 0;
        hwBlob.putInt32(8 + j2, size);
        hwBlob.putBool(12 + j2, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 120);
        for (int i = 0; i < size; i++) {
            this.infoRec.get(i).writeEmbeddedToBlob(hwBlob2, i * 120);
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
    }
}
