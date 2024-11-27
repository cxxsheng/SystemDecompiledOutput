package android.hardware.radio.V1_6;

/* loaded from: classes2.dex */
public final class NrSignalStrength {
    public android.hardware.radio.V1_4.NrSignalStrength base = new android.hardware.radio.V1_4.NrSignalStrength();
    public int csiCqiTableIndex = 0;
    public java.util.ArrayList<java.lang.Byte> csiCqiReport = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_6.NrSignalStrength.class) {
            return false;
        }
        android.hardware.radio.V1_6.NrSignalStrength nrSignalStrength = (android.hardware.radio.V1_6.NrSignalStrength) obj;
        if (android.os.HidlSupport.deepEquals(this.base, nrSignalStrength.base) && this.csiCqiTableIndex == nrSignalStrength.csiCqiTableIndex && android.os.HidlSupport.deepEquals(this.csiCqiReport, nrSignalStrength.csiCqiReport)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.base)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.csiCqiTableIndex))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.csiCqiReport)));
    }

    public final java.lang.String toString() {
        return "{.base = " + this.base + ", .csiCqiTableIndex = " + this.csiCqiTableIndex + ", .csiCqiReport = " + this.csiCqiReport + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(48L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_6.NrSignalStrength> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_6.NrSignalStrength> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 48, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_6.NrSignalStrength nrSignalStrength = new android.hardware.radio.V1_6.NrSignalStrength();
            nrSignalStrength.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 48);
            arrayList.add(nrSignalStrength);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, j + 0);
        this.csiCqiTableIndex = hwBlob.getInt32(j + 24);
        long j2 = j + 32;
        int int32 = hwBlob.getInt32(8 + j2);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 1, hwBlob.handle(), j2 + 0, true);
        this.csiCqiReport.clear();
        for (int i = 0; i < int32; i++) {
            this.csiCqiReport.add(java.lang.Byte.valueOf(readEmbeddedBuffer.getInt8(i * 1)));
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(48);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_6.NrSignalStrength> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 48);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 48);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        this.base.writeEmbeddedToBlob(hwBlob, j + 0);
        hwBlob.putInt32(24 + j, this.csiCqiTableIndex);
        int size = this.csiCqiReport.size();
        long j2 = j + 32;
        hwBlob.putInt32(8 + j2, size);
        hwBlob.putBool(12 + j2, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 1);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt8(i * 1, this.csiCqiReport.get(i).byteValue());
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
    }
}
