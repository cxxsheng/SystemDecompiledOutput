package android.hardware.radio.V1_4;

/* loaded from: classes2.dex */
public final class LteVopsInfo {
    public boolean isVopsSupported = false;
    public boolean isEmcBearerSupported = false;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_4.LteVopsInfo.class) {
            return false;
        }
        android.hardware.radio.V1_4.LteVopsInfo lteVopsInfo = (android.hardware.radio.V1_4.LteVopsInfo) obj;
        if (this.isVopsSupported == lteVopsInfo.isVopsSupported && this.isEmcBearerSupported == lteVopsInfo.isEmcBearerSupported) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.isVopsSupported))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.isEmcBearerSupported))));
    }

    public final java.lang.String toString() {
        return "{.isVopsSupported = " + this.isVopsSupported + ", .isEmcBearerSupported = " + this.isEmcBearerSupported + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(2L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_4.LteVopsInfo> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_4.LteVopsInfo> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 2, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_4.LteVopsInfo lteVopsInfo = new android.hardware.radio.V1_4.LteVopsInfo();
            lteVopsInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 2);
            arrayList.add(lteVopsInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.isVopsSupported = hwBlob.getBool(0 + j);
        this.isEmcBearerSupported = hwBlob.getBool(j + 1);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(2);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_4.LteVopsInfo> arrayList) {
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
        hwBlob.putBool(0 + j, this.isVopsSupported);
        hwBlob.putBool(j + 1, this.isEmcBearerSupported);
    }
}
