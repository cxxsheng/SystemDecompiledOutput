package android.hardware.gnss.V1_0;

/* loaded from: classes2.dex */
public final class GnssLocation {
    public short gnssLocationFlags;
    public double latitudeDegrees = 0.0d;
    public double longitudeDegrees = 0.0d;
    public double altitudeMeters = 0.0d;
    public float speedMetersPerSec = 0.0f;
    public float bearingDegrees = 0.0f;
    public float horizontalAccuracyMeters = 0.0f;
    public float verticalAccuracyMeters = 0.0f;
    public float speedAccuracyMetersPerSecond = 0.0f;
    public float bearingAccuracyDegrees = 0.0f;
    public long timestamp = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.gnss.V1_0.GnssLocation.class) {
            return false;
        }
        android.hardware.gnss.V1_0.GnssLocation gnssLocation = (android.hardware.gnss.V1_0.GnssLocation) obj;
        if (android.os.HidlSupport.deepEquals(java.lang.Short.valueOf(this.gnssLocationFlags), java.lang.Short.valueOf(gnssLocation.gnssLocationFlags)) && this.latitudeDegrees == gnssLocation.latitudeDegrees && this.longitudeDegrees == gnssLocation.longitudeDegrees && this.altitudeMeters == gnssLocation.altitudeMeters && this.speedMetersPerSec == gnssLocation.speedMetersPerSec && this.bearingDegrees == gnssLocation.bearingDegrees && this.horizontalAccuracyMeters == gnssLocation.horizontalAccuracyMeters && this.verticalAccuracyMeters == gnssLocation.verticalAccuracyMeters && this.speedAccuracyMetersPerSecond == gnssLocation.speedAccuracyMetersPerSecond && this.bearingAccuracyDegrees == gnssLocation.bearingAccuracyDegrees && this.timestamp == gnssLocation.timestamp) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Short.valueOf(this.gnssLocationFlags))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.latitudeDegrees))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.longitudeDegrees))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.altitudeMeters))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.speedMetersPerSec))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.bearingDegrees))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.horizontalAccuracyMeters))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.verticalAccuracyMeters))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.speedAccuracyMetersPerSecond))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.bearingAccuracyDegrees))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.timestamp))));
    }

    public final java.lang.String toString() {
        return "{.gnssLocationFlags = " + android.hardware.gnss.V1_0.GnssLocationFlags.dumpBitfield(this.gnssLocationFlags) + ", .latitudeDegrees = " + this.latitudeDegrees + ", .longitudeDegrees = " + this.longitudeDegrees + ", .altitudeMeters = " + this.altitudeMeters + ", .speedMetersPerSec = " + this.speedMetersPerSec + ", .bearingDegrees = " + this.bearingDegrees + ", .horizontalAccuracyMeters = " + this.horizontalAccuracyMeters + ", .verticalAccuracyMeters = " + this.verticalAccuracyMeters + ", .speedAccuracyMetersPerSecond = " + this.speedAccuracyMetersPerSecond + ", .bearingAccuracyDegrees = " + this.bearingAccuracyDegrees + ", .timestamp = " + this.timestamp + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(64L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.gnss.V1_0.GnssLocation> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.gnss.V1_0.GnssLocation> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 64, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.gnss.V1_0.GnssLocation gnssLocation = new android.hardware.gnss.V1_0.GnssLocation();
            gnssLocation.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 64);
            arrayList.add(gnssLocation);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.gnssLocationFlags = hwBlob.getInt16(0 + j);
        this.latitudeDegrees = hwBlob.getDouble(8 + j);
        this.longitudeDegrees = hwBlob.getDouble(16 + j);
        this.altitudeMeters = hwBlob.getDouble(24 + j);
        this.speedMetersPerSec = hwBlob.getFloat(32 + j);
        this.bearingDegrees = hwBlob.getFloat(36 + j);
        this.horizontalAccuracyMeters = hwBlob.getFloat(40 + j);
        this.verticalAccuracyMeters = hwBlob.getFloat(44 + j);
        this.speedAccuracyMetersPerSecond = hwBlob.getFloat(48 + j);
        this.bearingAccuracyDegrees = hwBlob.getFloat(52 + j);
        this.timestamp = hwBlob.getInt64(j + 56);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(64);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.gnss.V1_0.GnssLocation> arrayList) {
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
        hwBlob.putInt16(0 + j, this.gnssLocationFlags);
        hwBlob.putDouble(8 + j, this.latitudeDegrees);
        hwBlob.putDouble(16 + j, this.longitudeDegrees);
        hwBlob.putDouble(24 + j, this.altitudeMeters);
        hwBlob.putFloat(32 + j, this.speedMetersPerSec);
        hwBlob.putFloat(36 + j, this.bearingDegrees);
        hwBlob.putFloat(40 + j, this.horizontalAccuracyMeters);
        hwBlob.putFloat(44 + j, this.verticalAccuracyMeters);
        hwBlob.putFloat(48 + j, this.speedAccuracyMetersPerSecond);
        hwBlob.putFloat(52 + j, this.bearingAccuracyDegrees);
        hwBlob.putInt64(j + 56, this.timestamp);
    }
}
