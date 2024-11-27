package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class CdmaLineControlInfoRecord {
    public byte lineCtrlPolarityIncluded = 0;
    public byte lineCtrlToggle = 0;
    public byte lineCtrlReverse = 0;
    public byte lineCtrlPowerDenial = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.CdmaLineControlInfoRecord.class) {
            return false;
        }
        android.hardware.radio.V1_0.CdmaLineControlInfoRecord cdmaLineControlInfoRecord = (android.hardware.radio.V1_0.CdmaLineControlInfoRecord) obj;
        if (this.lineCtrlPolarityIncluded == cdmaLineControlInfoRecord.lineCtrlPolarityIncluded && this.lineCtrlToggle == cdmaLineControlInfoRecord.lineCtrlToggle && this.lineCtrlReverse == cdmaLineControlInfoRecord.lineCtrlReverse && this.lineCtrlPowerDenial == cdmaLineControlInfoRecord.lineCtrlPowerDenial) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.lineCtrlPolarityIncluded))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.lineCtrlToggle))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.lineCtrlReverse))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.lineCtrlPowerDenial))));
    }

    public final java.lang.String toString() {
        return "{.lineCtrlPolarityIncluded = " + ((int) this.lineCtrlPolarityIncluded) + ", .lineCtrlToggle = " + ((int) this.lineCtrlToggle) + ", .lineCtrlReverse = " + ((int) this.lineCtrlReverse) + ", .lineCtrlPowerDenial = " + ((int) this.lineCtrlPowerDenial) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(4L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.CdmaLineControlInfoRecord> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.CdmaLineControlInfoRecord> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 4, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.CdmaLineControlInfoRecord cdmaLineControlInfoRecord = new android.hardware.radio.V1_0.CdmaLineControlInfoRecord();
            cdmaLineControlInfoRecord.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 4);
            arrayList.add(cdmaLineControlInfoRecord);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.lineCtrlPolarityIncluded = hwBlob.getInt8(0 + j);
        this.lineCtrlToggle = hwBlob.getInt8(1 + j);
        this.lineCtrlReverse = hwBlob.getInt8(2 + j);
        this.lineCtrlPowerDenial = hwBlob.getInt8(j + 3);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(4);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.CdmaLineControlInfoRecord> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 4);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 4);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt8(0 + j, this.lineCtrlPolarityIncluded);
        hwBlob.putInt8(1 + j, this.lineCtrlToggle);
        hwBlob.putInt8(2 + j, this.lineCtrlReverse);
        hwBlob.putInt8(j + 3, this.lineCtrlPowerDenial);
    }
}
