package android.hardware.gnss.measurement_corrections.V1_0;

/* loaded from: classes2.dex */
public final class ReflectingPlane {
    public double latitudeDegrees = 0.0d;
    public double longitudeDegrees = 0.0d;
    public double altitudeMeters = 0.0d;
    public double azimuthDegrees = 0.0d;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.gnss.measurement_corrections.V1_0.ReflectingPlane.class) {
            return false;
        }
        android.hardware.gnss.measurement_corrections.V1_0.ReflectingPlane reflectingPlane = (android.hardware.gnss.measurement_corrections.V1_0.ReflectingPlane) obj;
        if (this.latitudeDegrees == reflectingPlane.latitudeDegrees && this.longitudeDegrees == reflectingPlane.longitudeDegrees && this.altitudeMeters == reflectingPlane.altitudeMeters && this.azimuthDegrees == reflectingPlane.azimuthDegrees) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.latitudeDegrees))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.longitudeDegrees))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.altitudeMeters))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Double.valueOf(this.azimuthDegrees))));
    }

    public final java.lang.String toString() {
        return "{.latitudeDegrees = " + this.latitudeDegrees + ", .longitudeDegrees = " + this.longitudeDegrees + ", .altitudeMeters = " + this.altitudeMeters + ", .azimuthDegrees = " + this.azimuthDegrees + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.gnss.measurement_corrections.V1_0.ReflectingPlane> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.gnss.measurement_corrections.V1_0.ReflectingPlane> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.gnss.measurement_corrections.V1_0.ReflectingPlane reflectingPlane = new android.hardware.gnss.measurement_corrections.V1_0.ReflectingPlane();
            reflectingPlane.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 32);
            arrayList.add(reflectingPlane);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.latitudeDegrees = hwBlob.getDouble(0 + j);
        this.longitudeDegrees = hwBlob.getDouble(8 + j);
        this.altitudeMeters = hwBlob.getDouble(16 + j);
        this.azimuthDegrees = hwBlob.getDouble(j + 24);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(32);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.gnss.measurement_corrections.V1_0.ReflectingPlane> arrayList) {
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
        hwBlob.putDouble(0 + j, this.latitudeDegrees);
        hwBlob.putDouble(8 + j, this.longitudeDegrees);
        hwBlob.putDouble(16 + j, this.altitudeMeters);
        hwBlob.putDouble(j + 24, this.azimuthDegrees);
    }
}
