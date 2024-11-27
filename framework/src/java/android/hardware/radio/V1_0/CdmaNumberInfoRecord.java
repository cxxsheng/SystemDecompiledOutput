package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class CdmaNumberInfoRecord {
    public java.lang.String number = new java.lang.String();
    public byte numberType = 0;
    public byte numberPlan = 0;
    public byte pi = 0;
    public byte si = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.CdmaNumberInfoRecord.class) {
            return false;
        }
        android.hardware.radio.V1_0.CdmaNumberInfoRecord cdmaNumberInfoRecord = (android.hardware.radio.V1_0.CdmaNumberInfoRecord) obj;
        if (android.os.HidlSupport.deepEquals(this.number, cdmaNumberInfoRecord.number) && this.numberType == cdmaNumberInfoRecord.numberType && this.numberPlan == cdmaNumberInfoRecord.numberPlan && this.pi == cdmaNumberInfoRecord.pi && this.si == cdmaNumberInfoRecord.si) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.number)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.numberType))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.numberPlan))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.pi))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.si))));
    }

    public final java.lang.String toString() {
        return "{.number = " + this.number + ", .numberType = " + ((int) this.numberType) + ", .numberPlan = " + ((int) this.numberPlan) + ", .pi = " + ((int) this.pi) + ", .si = " + ((int) this.si) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.CdmaNumberInfoRecord> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.CdmaNumberInfoRecord> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.CdmaNumberInfoRecord cdmaNumberInfoRecord = new android.hardware.radio.V1_0.CdmaNumberInfoRecord();
            cdmaNumberInfoRecord.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 24);
            arrayList.add(cdmaNumberInfoRecord);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        long j2 = j + 0;
        this.number = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.number.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        this.numberType = hwBlob.getInt8(j + 16);
        this.numberPlan = hwBlob.getInt8(j + 17);
        this.pi = hwBlob.getInt8(j + 18);
        this.si = hwBlob.getInt8(j + 19);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(24);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.CdmaNumberInfoRecord> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 24);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 24);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putString(0 + j, this.number);
        hwBlob.putInt8(16 + j, this.numberType);
        hwBlob.putInt8(17 + j, this.numberPlan);
        hwBlob.putInt8(18 + j, this.pi);
        hwBlob.putInt8(j + 19, this.si);
    }
}
