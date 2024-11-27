package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class CdmaRedirectingNumberInfoRecord {
    public android.hardware.radio.V1_0.CdmaNumberInfoRecord redirectingNumber = new android.hardware.radio.V1_0.CdmaNumberInfoRecord();
    public int redirectingReason = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.CdmaRedirectingNumberInfoRecord.class) {
            return false;
        }
        android.hardware.radio.V1_0.CdmaRedirectingNumberInfoRecord cdmaRedirectingNumberInfoRecord = (android.hardware.radio.V1_0.CdmaRedirectingNumberInfoRecord) obj;
        if (android.os.HidlSupport.deepEquals(this.redirectingNumber, cdmaRedirectingNumberInfoRecord.redirectingNumber) && this.redirectingReason == cdmaRedirectingNumberInfoRecord.redirectingReason) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.redirectingNumber)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.redirectingReason))));
    }

    public final java.lang.String toString() {
        return "{.redirectingNumber = " + this.redirectingNumber + ", .redirectingReason = " + android.hardware.radio.V1_0.CdmaRedirectingReason.toString(this.redirectingReason) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.CdmaRedirectingNumberInfoRecord> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.CdmaRedirectingNumberInfoRecord> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.CdmaRedirectingNumberInfoRecord cdmaRedirectingNumberInfoRecord = new android.hardware.radio.V1_0.CdmaRedirectingNumberInfoRecord();
            cdmaRedirectingNumberInfoRecord.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 32);
            arrayList.add(cdmaRedirectingNumberInfoRecord);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.redirectingNumber.readEmbeddedFromParcel(hwParcel, hwBlob, 0 + j);
        this.redirectingReason = hwBlob.getInt32(j + 24);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(32);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.CdmaRedirectingNumberInfoRecord> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 32);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 32);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        this.redirectingNumber.writeEmbeddedToBlob(hwBlob, 0 + j);
        hwBlob.putInt32(j + 24, this.redirectingReason);
    }
}
