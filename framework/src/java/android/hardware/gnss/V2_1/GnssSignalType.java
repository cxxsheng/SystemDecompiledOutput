package android.hardware.gnss.V2_1;

/* loaded from: classes2.dex */
public final class GnssSignalType {
    public byte constellation = 0;
    public double carrierFrequencyHz = 0.0d;
    public java.lang.String codeType = new java.lang.String();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.gnss.V2_1.GnssSignalType.class) {
            return false;
        }
        android.hardware.gnss.V2_1.GnssSignalType gnssSignalType = (android.hardware.gnss.V2_1.GnssSignalType) obj;
        if (this.constellation == gnssSignalType.constellation && this.carrierFrequencyHz == gnssSignalType.carrierFrequencyHz && android.os.HidlSupport.deepEquals(this.codeType, gnssSignalType.codeType)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.constellation))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.carrierFrequencyHz))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.codeType)));
    }

    public final java.lang.String toString() {
        return "{.constellation = " + android.hardware.gnss.V2_0.GnssConstellationType.toString(this.constellation) + ", .carrierFrequencyHz = " + this.carrierFrequencyHz + ", .codeType = " + this.codeType + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.gnss.V2_1.GnssSignalType> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.gnss.V2_1.GnssSignalType> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.gnss.V2_1.GnssSignalType gnssSignalType = new android.hardware.gnss.V2_1.GnssSignalType();
            gnssSignalType.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 32);
            arrayList.add(gnssSignalType);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.constellation = hwBlob.getInt8(j + 0);
        this.carrierFrequencyHz = hwBlob.getDouble(8 + j);
        long j2 = j + 16;
        this.codeType = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.codeType.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(32);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.gnss.V2_1.GnssSignalType> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 32);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 32);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt8(0 + j, this.constellation);
        hwBlob.putDouble(8 + j, this.carrierFrequencyHz);
        hwBlob.putString(j + 16, this.codeType);
    }
}
