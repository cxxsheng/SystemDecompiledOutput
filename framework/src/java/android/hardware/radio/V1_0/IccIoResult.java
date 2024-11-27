package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class IccIoResult {
    public int sw1 = 0;
    public int sw2 = 0;
    public java.lang.String simResponse = new java.lang.String();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.IccIoResult.class) {
            return false;
        }
        android.hardware.radio.V1_0.IccIoResult iccIoResult = (android.hardware.radio.V1_0.IccIoResult) obj;
        if (this.sw1 == iccIoResult.sw1 && this.sw2 == iccIoResult.sw2 && android.os.HidlSupport.deepEquals(this.simResponse, iccIoResult.simResponse)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.sw1))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.sw2))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.simResponse)));
    }

    public final java.lang.String toString() {
        return "{.sw1 = " + this.sw1 + ", .sw2 = " + this.sw2 + ", .simResponse = " + this.simResponse + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.IccIoResult> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.IccIoResult> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.IccIoResult iccIoResult = new android.hardware.radio.V1_0.IccIoResult();
            iccIoResult.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 24);
            arrayList.add(iccIoResult);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.sw1 = hwBlob.getInt32(j + 0);
        this.sw2 = hwBlob.getInt32(4 + j);
        long j2 = j + 8;
        this.simResponse = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.simResponse.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(24);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.IccIoResult> arrayList) {
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
        hwBlob.putInt32(0 + j, this.sw1);
        hwBlob.putInt32(4 + j, this.sw2);
        hwBlob.putString(j + 8, this.simResponse);
    }
}
