package android.hardware.radio.V1_6;

/* loaded from: classes2.dex */
public final class LteSignalStrength {
    public android.hardware.radio.V1_0.LteSignalStrength base = new android.hardware.radio.V1_0.LteSignalStrength();
    public int cqiTableIndex = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_6.LteSignalStrength.class) {
            return false;
        }
        android.hardware.radio.V1_6.LteSignalStrength lteSignalStrength = (android.hardware.radio.V1_6.LteSignalStrength) obj;
        if (android.os.HidlSupport.deepEquals(this.base, lteSignalStrength.base) && this.cqiTableIndex == lteSignalStrength.cqiTableIndex) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.base)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.cqiTableIndex))));
    }

    public final java.lang.String toString() {
        return "{.base = " + this.base + ", .cqiTableIndex = " + this.cqiTableIndex + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(28L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_6.LteSignalStrength> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_6.LteSignalStrength> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 28, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_6.LteSignalStrength lteSignalStrength = new android.hardware.radio.V1_6.LteSignalStrength();
            lteSignalStrength.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 28);
            arrayList.add(lteSignalStrength);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, 0 + j);
        this.cqiTableIndex = hwBlob.getInt32(j + 24);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(28);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_6.LteSignalStrength> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 28);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 28);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        this.base.writeEmbeddedToBlob(hwBlob, 0 + j);
        hwBlob.putInt32(j + 24, this.cqiTableIndex);
    }
}
