package android.hardware.radio.V1_2;

/* loaded from: classes2.dex */
public final class TdscdmaSignalStrength {
    public int signalStrength = 0;
    public int bitErrorRate = 0;
    public int rscp = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_2.TdscdmaSignalStrength.class) {
            return false;
        }
        android.hardware.radio.V1_2.TdscdmaSignalStrength tdscdmaSignalStrength = (android.hardware.radio.V1_2.TdscdmaSignalStrength) obj;
        if (this.signalStrength == tdscdmaSignalStrength.signalStrength && this.bitErrorRate == tdscdmaSignalStrength.bitErrorRate && this.rscp == tdscdmaSignalStrength.rscp) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.signalStrength))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.bitErrorRate))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.rscp))));
    }

    public final java.lang.String toString() {
        return "{.signalStrength = " + this.signalStrength + ", .bitErrorRate = " + this.bitErrorRate + ", .rscp = " + this.rscp + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(12L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_2.TdscdmaSignalStrength> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_2.TdscdmaSignalStrength> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 12, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_2.TdscdmaSignalStrength tdscdmaSignalStrength = new android.hardware.radio.V1_2.TdscdmaSignalStrength();
            tdscdmaSignalStrength.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 12);
            arrayList.add(tdscdmaSignalStrength);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.signalStrength = hwBlob.getInt32(0 + j);
        this.bitErrorRate = hwBlob.getInt32(4 + j);
        this.rscp = hwBlob.getInt32(j + 8);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(12);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_2.TdscdmaSignalStrength> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 12);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 12);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(0 + j, this.signalStrength);
        hwBlob.putInt32(4 + j, this.bitErrorRate);
        hwBlob.putInt32(j + 8, this.rscp);
    }
}
