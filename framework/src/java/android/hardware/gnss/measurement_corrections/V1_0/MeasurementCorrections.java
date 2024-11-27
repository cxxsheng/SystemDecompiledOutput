package android.hardware.gnss.measurement_corrections.V1_0;

/* loaded from: classes2.dex */
public final class MeasurementCorrections {
    public double latitudeDegrees = 0.0d;
    public double longitudeDegrees = 0.0d;
    public double altitudeMeters = 0.0d;
    public double horizontalPositionUncertaintyMeters = 0.0d;
    public double verticalPositionUncertaintyMeters = 0.0d;
    public long toaGpsNanosecondsOfWeek = 0;
    public java.util.ArrayList<android.hardware.gnss.measurement_corrections.V1_0.SingleSatCorrection> satCorrections = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.gnss.measurement_corrections.V1_0.MeasurementCorrections.class) {
            return false;
        }
        android.hardware.gnss.measurement_corrections.V1_0.MeasurementCorrections measurementCorrections = (android.hardware.gnss.measurement_corrections.V1_0.MeasurementCorrections) obj;
        if (this.latitudeDegrees == measurementCorrections.latitudeDegrees && this.longitudeDegrees == measurementCorrections.longitudeDegrees && this.altitudeMeters == measurementCorrections.altitudeMeters && this.horizontalPositionUncertaintyMeters == measurementCorrections.horizontalPositionUncertaintyMeters && this.verticalPositionUncertaintyMeters == measurementCorrections.verticalPositionUncertaintyMeters && this.toaGpsNanosecondsOfWeek == measurementCorrections.toaGpsNanosecondsOfWeek && android.os.HidlSupport.deepEquals(this.satCorrections, measurementCorrections.satCorrections)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.latitudeDegrees))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.longitudeDegrees))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.altitudeMeters))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.horizontalPositionUncertaintyMeters))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.verticalPositionUncertaintyMeters))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.toaGpsNanosecondsOfWeek))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.satCorrections)));
    }

    public final java.lang.String toString() {
        return "{.latitudeDegrees = " + this.latitudeDegrees + ", .longitudeDegrees = " + this.longitudeDegrees + ", .altitudeMeters = " + this.altitudeMeters + ", .horizontalPositionUncertaintyMeters = " + this.horizontalPositionUncertaintyMeters + ", .verticalPositionUncertaintyMeters = " + this.verticalPositionUncertaintyMeters + ", .toaGpsNanosecondsOfWeek = " + this.toaGpsNanosecondsOfWeek + ", .satCorrections = " + this.satCorrections + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(64L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.gnss.measurement_corrections.V1_0.MeasurementCorrections> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.gnss.measurement_corrections.V1_0.MeasurementCorrections> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 64, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.gnss.measurement_corrections.V1_0.MeasurementCorrections measurementCorrections = new android.hardware.gnss.measurement_corrections.V1_0.MeasurementCorrections();
            measurementCorrections.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 64);
            arrayList.add(measurementCorrections);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.latitudeDegrees = hwBlob.getDouble(j + 0);
        this.longitudeDegrees = hwBlob.getDouble(j + 8);
        this.altitudeMeters = hwBlob.getDouble(j + 16);
        this.horizontalPositionUncertaintyMeters = hwBlob.getDouble(j + 24);
        this.verticalPositionUncertaintyMeters = hwBlob.getDouble(j + 32);
        this.toaGpsNanosecondsOfWeek = hwBlob.getInt64(j + 40);
        long j2 = j + 48;
        int int32 = hwBlob.getInt32(8 + j2);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 56, hwBlob.handle(), j2 + 0, true);
        this.satCorrections.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.gnss.measurement_corrections.V1_0.SingleSatCorrection singleSatCorrection = new android.hardware.gnss.measurement_corrections.V1_0.SingleSatCorrection();
            singleSatCorrection.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 56);
            this.satCorrections.add(singleSatCorrection);
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(64);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.gnss.measurement_corrections.V1_0.MeasurementCorrections> arrayList) {
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
        hwBlob.putDouble(j + 0, this.latitudeDegrees);
        hwBlob.putDouble(j + 8, this.longitudeDegrees);
        hwBlob.putDouble(16 + j, this.altitudeMeters);
        hwBlob.putDouble(24 + j, this.horizontalPositionUncertaintyMeters);
        hwBlob.putDouble(32 + j, this.verticalPositionUncertaintyMeters);
        hwBlob.putInt64(40 + j, this.toaGpsNanosecondsOfWeek);
        int size = this.satCorrections.size();
        long j2 = j + 48;
        hwBlob.putInt32(8 + j2, size);
        hwBlob.putBool(12 + j2, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 56);
        for (int i = 0; i < size; i++) {
            this.satCorrections.get(i).writeEmbeddedToBlob(hwBlob2, i * 56);
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
    }
}
