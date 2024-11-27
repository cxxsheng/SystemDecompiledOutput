package android.hardware.gnss.measurement_corrections.V1_1;

/* loaded from: classes2.dex */
public final class SingleSatCorrection {
    public android.hardware.gnss.measurement_corrections.V1_0.SingleSatCorrection v1_0 = new android.hardware.gnss.measurement_corrections.V1_0.SingleSatCorrection();
    public byte constellation = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.gnss.measurement_corrections.V1_1.SingleSatCorrection.class) {
            return false;
        }
        android.hardware.gnss.measurement_corrections.V1_1.SingleSatCorrection singleSatCorrection = (android.hardware.gnss.measurement_corrections.V1_1.SingleSatCorrection) obj;
        if (android.os.HidlSupport.deepEquals(this.v1_0, singleSatCorrection.v1_0) && this.constellation == singleSatCorrection.constellation) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.v1_0)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.constellation))));
    }

    public final java.lang.String toString() {
        return "{.v1_0 = " + this.v1_0 + ", .constellation = " + android.hardware.gnss.V2_0.GnssConstellationType.toString(this.constellation) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(64L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.gnss.measurement_corrections.V1_1.SingleSatCorrection> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.gnss.measurement_corrections.V1_1.SingleSatCorrection> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 64, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.gnss.measurement_corrections.V1_1.SingleSatCorrection singleSatCorrection = new android.hardware.gnss.measurement_corrections.V1_1.SingleSatCorrection();
            singleSatCorrection.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 64);
            arrayList.add(singleSatCorrection);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.v1_0.readEmbeddedFromParcel(hwParcel, hwBlob, 0 + j);
        this.constellation = hwBlob.getInt8(j + 56);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(64);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.gnss.measurement_corrections.V1_1.SingleSatCorrection> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 64);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 64);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        this.v1_0.writeEmbeddedToBlob(hwBlob, 0 + j);
        hwBlob.putInt8(j + 56, this.constellation);
    }
}
