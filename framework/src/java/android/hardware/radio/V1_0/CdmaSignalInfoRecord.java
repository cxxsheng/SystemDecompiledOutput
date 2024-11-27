package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class CdmaSignalInfoRecord {
    public boolean isPresent = false;
    public byte signalType = 0;
    public byte alertPitch = 0;
    public byte signal = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.CdmaSignalInfoRecord.class) {
            return false;
        }
        android.hardware.radio.V1_0.CdmaSignalInfoRecord cdmaSignalInfoRecord = (android.hardware.radio.V1_0.CdmaSignalInfoRecord) obj;
        if (this.isPresent == cdmaSignalInfoRecord.isPresent && this.signalType == cdmaSignalInfoRecord.signalType && this.alertPitch == cdmaSignalInfoRecord.alertPitch && this.signal == cdmaSignalInfoRecord.signal) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.isPresent))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.signalType))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.alertPitch))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.signal))));
    }

    public final java.lang.String toString() {
        return "{.isPresent = " + this.isPresent + ", .signalType = " + ((int) this.signalType) + ", .alertPitch = " + ((int) this.alertPitch) + ", .signal = " + ((int) this.signal) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(4L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.CdmaSignalInfoRecord> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.CdmaSignalInfoRecord> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 4, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.CdmaSignalInfoRecord cdmaSignalInfoRecord = new android.hardware.radio.V1_0.CdmaSignalInfoRecord();
            cdmaSignalInfoRecord.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 4);
            arrayList.add(cdmaSignalInfoRecord);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.isPresent = hwBlob.getBool(0 + j);
        this.signalType = hwBlob.getInt8(1 + j);
        this.alertPitch = hwBlob.getInt8(2 + j);
        this.signal = hwBlob.getInt8(j + 3);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(4);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.CdmaSignalInfoRecord> arrayList) {
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
        hwBlob.putBool(0 + j, this.isPresent);
        hwBlob.putInt8(1 + j, this.signalType);
        hwBlob.putInt8(2 + j, this.alertPitch);
        hwBlob.putInt8(j + 3, this.signal);
    }
}
