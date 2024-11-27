package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class OperatorInfo {
    public java.lang.String alphaLong = new java.lang.String();
    public java.lang.String alphaShort = new java.lang.String();
    public java.lang.String operatorNumeric = new java.lang.String();
    public int status = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.OperatorInfo.class) {
            return false;
        }
        android.hardware.radio.V1_0.OperatorInfo operatorInfo = (android.hardware.radio.V1_0.OperatorInfo) obj;
        if (android.os.HidlSupport.deepEquals(this.alphaLong, operatorInfo.alphaLong) && android.os.HidlSupport.deepEquals(this.alphaShort, operatorInfo.alphaShort) && android.os.HidlSupport.deepEquals(this.operatorNumeric, operatorInfo.operatorNumeric) && this.status == operatorInfo.status) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.alphaLong)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.alphaShort)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.operatorNumeric)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.status))));
    }

    public final java.lang.String toString() {
        return "{.alphaLong = " + this.alphaLong + ", .alphaShort = " + this.alphaShort + ", .operatorNumeric = " + this.operatorNumeric + ", .status = " + android.hardware.radio.V1_0.OperatorStatus.toString(this.status) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(56L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.OperatorInfo> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.OperatorInfo> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 56, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.OperatorInfo operatorInfo = new android.hardware.radio.V1_0.OperatorInfo();
            operatorInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 56);
            arrayList.add(operatorInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        long j2 = j + 0;
        this.alphaLong = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.alphaLong.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        long j3 = j + 16;
        this.alphaShort = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(this.alphaShort.getBytes().length + 1, hwBlob.handle(), j3 + 0, false);
        long j4 = j + 32;
        this.operatorNumeric = hwBlob.getString(j4);
        hwParcel.readEmbeddedBuffer(this.operatorNumeric.getBytes().length + 1, hwBlob.handle(), j4 + 0, false);
        this.status = hwBlob.getInt32(j + 48);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(56);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.OperatorInfo> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 56);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 56);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putString(0 + j, this.alphaLong);
        hwBlob.putString(16 + j, this.alphaShort);
        hwBlob.putString(32 + j, this.operatorNumeric);
        hwBlob.putInt32(j + 48, this.status);
    }
}
