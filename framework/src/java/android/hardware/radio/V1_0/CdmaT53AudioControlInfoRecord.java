package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class CdmaT53AudioControlInfoRecord {
    public byte upLink = 0;
    public byte downLink = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.CdmaT53AudioControlInfoRecord.class) {
            return false;
        }
        android.hardware.radio.V1_0.CdmaT53AudioControlInfoRecord cdmaT53AudioControlInfoRecord = (android.hardware.radio.V1_0.CdmaT53AudioControlInfoRecord) obj;
        if (this.upLink == cdmaT53AudioControlInfoRecord.upLink && this.downLink == cdmaT53AudioControlInfoRecord.downLink) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.upLink))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.downLink))));
    }

    public final java.lang.String toString() {
        return "{.upLink = " + ((int) this.upLink) + ", .downLink = " + ((int) this.downLink) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(2L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.CdmaT53AudioControlInfoRecord> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.CdmaT53AudioControlInfoRecord> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 2, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.CdmaT53AudioControlInfoRecord cdmaT53AudioControlInfoRecord = new android.hardware.radio.V1_0.CdmaT53AudioControlInfoRecord();
            cdmaT53AudioControlInfoRecord.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 2);
            arrayList.add(cdmaT53AudioControlInfoRecord);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.upLink = hwBlob.getInt8(0 + j);
        this.downLink = hwBlob.getInt8(j + 1);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(2);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.CdmaT53AudioControlInfoRecord> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 2);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 2);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt8(0 + j, this.upLink);
        hwBlob.putInt8(j + 1, this.downLink);
    }
}
