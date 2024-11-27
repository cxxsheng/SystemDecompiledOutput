package android.hardware.gnss.measurement_corrections.V1_0;

/* loaded from: classes2.dex */
public final class SingleSatCorrection {
    public short singleSatCorrectionFlags;
    public byte constellation = 0;
    public short svid = 0;
    public float carrierFrequencyHz = 0.0f;
    public float probSatIsLos = 0.0f;
    public float excessPathLengthMeters = 0.0f;
    public float excessPathLengthUncertaintyMeters = 0.0f;
    public android.hardware.gnss.measurement_corrections.V1_0.ReflectingPlane reflectingPlane = new android.hardware.gnss.measurement_corrections.V1_0.ReflectingPlane();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.gnss.measurement_corrections.V1_0.SingleSatCorrection.class) {
            return false;
        }
        android.hardware.gnss.measurement_corrections.V1_0.SingleSatCorrection singleSatCorrection = (android.hardware.gnss.measurement_corrections.V1_0.SingleSatCorrection) obj;
        if (android.os.HidlSupport.deepEquals(java.lang.Short.valueOf(this.singleSatCorrectionFlags), java.lang.Short.valueOf(singleSatCorrection.singleSatCorrectionFlags)) && this.constellation == singleSatCorrection.constellation && this.svid == singleSatCorrection.svid && this.carrierFrequencyHz == singleSatCorrection.carrierFrequencyHz && this.probSatIsLos == singleSatCorrection.probSatIsLos && this.excessPathLengthMeters == singleSatCorrection.excessPathLengthMeters && this.excessPathLengthUncertaintyMeters == singleSatCorrection.excessPathLengthUncertaintyMeters && android.os.HidlSupport.deepEquals(this.reflectingPlane, singleSatCorrection.reflectingPlane)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Short.valueOf(this.singleSatCorrectionFlags))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.constellation))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Short.valueOf(this.svid))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.carrierFrequencyHz))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.probSatIsLos))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.excessPathLengthMeters))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.excessPathLengthUncertaintyMeters))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.reflectingPlane)));
    }

    public final java.lang.String toString() {
        return "{.singleSatCorrectionFlags = " + android.hardware.gnss.measurement_corrections.V1_0.GnssSingleSatCorrectionFlags.dumpBitfield(this.singleSatCorrectionFlags) + ", .constellation = " + android.hardware.gnss.V1_0.GnssConstellationType.toString(this.constellation) + ", .svid = " + ((int) this.svid) + ", .carrierFrequencyHz = " + this.carrierFrequencyHz + ", .probSatIsLos = " + this.probSatIsLos + ", .excessPathLengthMeters = " + this.excessPathLengthMeters + ", .excessPathLengthUncertaintyMeters = " + this.excessPathLengthUncertaintyMeters + ", .reflectingPlane = " + this.reflectingPlane + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(56L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.gnss.measurement_corrections.V1_0.SingleSatCorrection> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.gnss.measurement_corrections.V1_0.SingleSatCorrection> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 56, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.gnss.measurement_corrections.V1_0.SingleSatCorrection singleSatCorrection = new android.hardware.gnss.measurement_corrections.V1_0.SingleSatCorrection();
            singleSatCorrection.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 56);
            arrayList.add(singleSatCorrection);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.singleSatCorrectionFlags = hwBlob.getInt16(0 + j);
        this.constellation = hwBlob.getInt8(2 + j);
        this.svid = hwBlob.getInt16(4 + j);
        this.carrierFrequencyHz = hwBlob.getFloat(8 + j);
        this.probSatIsLos = hwBlob.getFloat(12 + j);
        this.excessPathLengthMeters = hwBlob.getFloat(16 + j);
        this.excessPathLengthUncertaintyMeters = hwBlob.getFloat(20 + j);
        this.reflectingPlane.readEmbeddedFromParcel(hwParcel, hwBlob, j + 24);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(56);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.gnss.measurement_corrections.V1_0.SingleSatCorrection> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 56);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 56);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt16(0 + j, this.singleSatCorrectionFlags);
        hwBlob.putInt8(2 + j, this.constellation);
        hwBlob.putInt16(4 + j, this.svid);
        hwBlob.putFloat(8 + j, this.carrierFrequencyHz);
        hwBlob.putFloat(12 + j, this.probSatIsLos);
        hwBlob.putFloat(16 + j, this.excessPathLengthMeters);
        hwBlob.putFloat(20 + j, this.excessPathLengthUncertaintyMeters);
        this.reflectingPlane.writeEmbeddedToBlob(hwBlob, j + 24);
    }
}
