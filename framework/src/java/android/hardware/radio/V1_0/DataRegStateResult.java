package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class DataRegStateResult {
    public int regState = 0;
    public int rat = 0;
    public int reasonDataDenied = 0;
    public int maxDataCalls = 0;
    public android.hardware.radio.V1_0.CellIdentity cellIdentity = new android.hardware.radio.V1_0.CellIdentity();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.DataRegStateResult.class) {
            return false;
        }
        android.hardware.radio.V1_0.DataRegStateResult dataRegStateResult = (android.hardware.radio.V1_0.DataRegStateResult) obj;
        if (this.regState == dataRegStateResult.regState && this.rat == dataRegStateResult.rat && this.reasonDataDenied == dataRegStateResult.reasonDataDenied && this.maxDataCalls == dataRegStateResult.maxDataCalls && android.os.HidlSupport.deepEquals(this.cellIdentity, dataRegStateResult.cellIdentity)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.regState))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.rat))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.reasonDataDenied))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.maxDataCalls))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.cellIdentity)));
    }

    public final java.lang.String toString() {
        return "{.regState = " + android.hardware.radio.V1_0.RegState.toString(this.regState) + ", .rat = " + this.rat + ", .reasonDataDenied = " + this.reasonDataDenied + ", .maxDataCalls = " + this.maxDataCalls + ", .cellIdentity = " + this.cellIdentity + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(104L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.DataRegStateResult> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.DataRegStateResult> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 104, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.DataRegStateResult dataRegStateResult = new android.hardware.radio.V1_0.DataRegStateResult();
            dataRegStateResult.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 104);
            arrayList.add(dataRegStateResult);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.regState = hwBlob.getInt32(0 + j);
        this.rat = hwBlob.getInt32(4 + j);
        this.reasonDataDenied = hwBlob.getInt32(8 + j);
        this.maxDataCalls = hwBlob.getInt32(12 + j);
        this.cellIdentity.readEmbeddedFromParcel(hwParcel, hwBlob, j + 16);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(104);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.DataRegStateResult> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 104);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 104);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(0 + j, this.regState);
        hwBlob.putInt32(4 + j, this.rat);
        hwBlob.putInt32(8 + j, this.reasonDataDenied);
        hwBlob.putInt32(12 + j, this.maxDataCalls);
        this.cellIdentity.writeEmbeddedToBlob(hwBlob, j + 16);
    }
}
