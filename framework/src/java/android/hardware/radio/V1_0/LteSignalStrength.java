package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class LteSignalStrength {
    public int signalStrength = 0;
    public int rsrp = 0;
    public int rsrq = 0;
    public int rssnr = 0;
    public int cqi = 0;
    public int timingAdvance = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.LteSignalStrength.class) {
            return false;
        }
        android.hardware.radio.V1_0.LteSignalStrength lteSignalStrength = (android.hardware.radio.V1_0.LteSignalStrength) obj;
        if (this.signalStrength == lteSignalStrength.signalStrength && this.rsrp == lteSignalStrength.rsrp && this.rsrq == lteSignalStrength.rsrq && this.rssnr == lteSignalStrength.rssnr && this.cqi == lteSignalStrength.cqi && this.timingAdvance == lteSignalStrength.timingAdvance) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.signalStrength))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.rsrp))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.rsrq))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.rssnr))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.cqi))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.timingAdvance))));
    }

    public final java.lang.String toString() {
        return "{.signalStrength = " + this.signalStrength + ", .rsrp = " + this.rsrp + ", .rsrq = " + this.rsrq + ", .rssnr = " + this.rssnr + ", .cqi = " + this.cqi + ", .timingAdvance = " + this.timingAdvance + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.LteSignalStrength> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.LteSignalStrength> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.LteSignalStrength lteSignalStrength = new android.hardware.radio.V1_0.LteSignalStrength();
            lteSignalStrength.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 24);
            arrayList.add(lteSignalStrength);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.signalStrength = hwBlob.getInt32(0 + j);
        this.rsrp = hwBlob.getInt32(4 + j);
        this.rsrq = hwBlob.getInt32(8 + j);
        this.rssnr = hwBlob.getInt32(12 + j);
        this.cqi = hwBlob.getInt32(16 + j);
        this.timingAdvance = hwBlob.getInt32(j + 20);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(24);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.LteSignalStrength> arrayList) {
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
        hwBlob.putInt32(0 + j, this.signalStrength);
        hwBlob.putInt32(4 + j, this.rsrp);
        hwBlob.putInt32(8 + j, this.rsrq);
        hwBlob.putInt32(12 + j, this.rssnr);
        hwBlob.putInt32(16 + j, this.cqi);
        hwBlob.putInt32(j + 20, this.timingAdvance);
    }
}
