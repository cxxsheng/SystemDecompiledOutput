package android.hardware.radio.V1_6;

/* loaded from: classes2.dex */
public final class NrVopsInfo {
    public byte vopsSupported = 0;
    public byte emcSupported = 0;
    public byte emfSupported = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_6.NrVopsInfo.class) {
            return false;
        }
        android.hardware.radio.V1_6.NrVopsInfo nrVopsInfo = (android.hardware.radio.V1_6.NrVopsInfo) obj;
        if (this.vopsSupported == nrVopsInfo.vopsSupported && this.emcSupported == nrVopsInfo.emcSupported && this.emfSupported == nrVopsInfo.emfSupported) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.vopsSupported))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.emcSupported))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.emfSupported))));
    }

    public final java.lang.String toString() {
        return "{.vopsSupported = " + android.hardware.radio.V1_6.VopsIndicator.toString(this.vopsSupported) + ", .emcSupported = " + android.hardware.radio.V1_6.EmcIndicator.toString(this.emcSupported) + ", .emfSupported = " + android.hardware.radio.V1_6.EmfIndicator.toString(this.emfSupported) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(3L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_6.NrVopsInfo> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_6.NrVopsInfo> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 3, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_6.NrVopsInfo nrVopsInfo = new android.hardware.radio.V1_6.NrVopsInfo();
            nrVopsInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 3);
            arrayList.add(nrVopsInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.vopsSupported = hwBlob.getInt8(0 + j);
        this.emcSupported = hwBlob.getInt8(1 + j);
        this.emfSupported = hwBlob.getInt8(j + 2);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(3);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_6.NrVopsInfo> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 3);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 3);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt8(0 + j, this.vopsSupported);
        hwBlob.putInt8(1 + j, this.emcSupported);
        hwBlob.putInt8(j + 2, this.emfSupported);
    }
}
