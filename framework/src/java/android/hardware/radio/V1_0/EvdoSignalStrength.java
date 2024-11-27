package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class EvdoSignalStrength {
    public int dbm = 0;
    public int ecio = 0;
    public int signalNoiseRatio = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.EvdoSignalStrength.class) {
            return false;
        }
        android.hardware.radio.V1_0.EvdoSignalStrength evdoSignalStrength = (android.hardware.radio.V1_0.EvdoSignalStrength) obj;
        if (this.dbm == evdoSignalStrength.dbm && this.ecio == evdoSignalStrength.ecio && this.signalNoiseRatio == evdoSignalStrength.signalNoiseRatio) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.dbm))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.ecio))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.signalNoiseRatio))));
    }

    public final java.lang.String toString() {
        return "{.dbm = " + this.dbm + ", .ecio = " + this.ecio + ", .signalNoiseRatio = " + this.signalNoiseRatio + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(12L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.EvdoSignalStrength> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.EvdoSignalStrength> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 12, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.EvdoSignalStrength evdoSignalStrength = new android.hardware.radio.V1_0.EvdoSignalStrength();
            evdoSignalStrength.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 12);
            arrayList.add(evdoSignalStrength);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.dbm = hwBlob.getInt32(0 + j);
        this.ecio = hwBlob.getInt32(4 + j);
        this.signalNoiseRatio = hwBlob.getInt32(j + 8);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(12);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.EvdoSignalStrength> arrayList) {
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
        hwBlob.putInt32(0 + j, this.dbm);
        hwBlob.putInt32(4 + j, this.ecio);
        hwBlob.putInt32(j + 8, this.signalNoiseRatio);
    }
}
