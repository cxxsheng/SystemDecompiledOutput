package android.hardware.gnss.measurement_corrections.V1_1;

/* loaded from: classes2.dex */
public final class MeasurementCorrections {
    public android.hardware.gnss.measurement_corrections.V1_0.MeasurementCorrections v1_0 = new android.hardware.gnss.measurement_corrections.V1_0.MeasurementCorrections();
    public boolean hasEnvironmentBearing = false;
    public float environmentBearingDegrees = 0.0f;
    public float environmentBearingUncertaintyDegrees = 0.0f;
    public java.util.ArrayList<android.hardware.gnss.measurement_corrections.V1_1.SingleSatCorrection> satCorrections = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.gnss.measurement_corrections.V1_1.MeasurementCorrections.class) {
            return false;
        }
        android.hardware.gnss.measurement_corrections.V1_1.MeasurementCorrections measurementCorrections = (android.hardware.gnss.measurement_corrections.V1_1.MeasurementCorrections) obj;
        if (android.os.HidlSupport.deepEquals(this.v1_0, measurementCorrections.v1_0) && this.hasEnvironmentBearing == measurementCorrections.hasEnvironmentBearing && this.environmentBearingDegrees == measurementCorrections.environmentBearingDegrees && this.environmentBearingUncertaintyDegrees == measurementCorrections.environmentBearingUncertaintyDegrees && android.os.HidlSupport.deepEquals(this.satCorrections, measurementCorrections.satCorrections)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.v1_0)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.hasEnvironmentBearing))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.environmentBearingDegrees))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.environmentBearingUncertaintyDegrees))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.satCorrections)));
    }

    public final java.lang.String toString() {
        return "{.v1_0 = " + this.v1_0 + ", .hasEnvironmentBearing = " + this.hasEnvironmentBearing + ", .environmentBearingDegrees = " + this.environmentBearingDegrees + ", .environmentBearingUncertaintyDegrees = " + this.environmentBearingUncertaintyDegrees + ", .satCorrections = " + this.satCorrections + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(96L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.gnss.measurement_corrections.V1_1.MeasurementCorrections> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.gnss.measurement_corrections.V1_1.MeasurementCorrections> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 96, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.gnss.measurement_corrections.V1_1.MeasurementCorrections measurementCorrections = new android.hardware.gnss.measurement_corrections.V1_1.MeasurementCorrections();
            measurementCorrections.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 96);
            arrayList.add(measurementCorrections);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.v1_0.readEmbeddedFromParcel(hwParcel, hwBlob, j + 0);
        this.hasEnvironmentBearing = hwBlob.getBool(j + 64);
        this.environmentBearingDegrees = hwBlob.getFloat(j + 68);
        this.environmentBearingUncertaintyDegrees = hwBlob.getFloat(j + 72);
        long j2 = j + 80;
        int int32 = hwBlob.getInt32(8 + j2);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 64, hwBlob.handle(), j2 + 0, true);
        this.satCorrections.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.gnss.measurement_corrections.V1_1.SingleSatCorrection singleSatCorrection = new android.hardware.gnss.measurement_corrections.V1_1.SingleSatCorrection();
            singleSatCorrection.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 64);
            this.satCorrections.add(singleSatCorrection);
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(96);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.gnss.measurement_corrections.V1_1.MeasurementCorrections> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 96);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 96);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        this.v1_0.writeEmbeddedToBlob(hwBlob, j + 0);
        hwBlob.putBool(64 + j, this.hasEnvironmentBearing);
        hwBlob.putFloat(68 + j, this.environmentBearingDegrees);
        hwBlob.putFloat(72 + j, this.environmentBearingUncertaintyDegrees);
        int size = this.satCorrections.size();
        long j2 = j + 80;
        hwBlob.putInt32(8 + j2, size);
        hwBlob.putBool(12 + j2, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 64);
        for (int i = 0; i < size; i++) {
            this.satCorrections.get(i).writeEmbeddedToBlob(hwBlob2, i * 64);
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
    }
}
