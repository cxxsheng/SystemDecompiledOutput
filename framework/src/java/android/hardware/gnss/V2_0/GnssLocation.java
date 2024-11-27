package android.hardware.gnss.V2_0;

/* loaded from: classes2.dex */
public final class GnssLocation {
    public android.hardware.gnss.V1_0.GnssLocation v1_0 = new android.hardware.gnss.V1_0.GnssLocation();
    public android.hardware.gnss.V2_0.ElapsedRealtime elapsedRealtime = new android.hardware.gnss.V2_0.ElapsedRealtime();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.gnss.V2_0.GnssLocation.class) {
            return false;
        }
        android.hardware.gnss.V2_0.GnssLocation gnssLocation = (android.hardware.gnss.V2_0.GnssLocation) obj;
        if (android.os.HidlSupport.deepEquals(this.v1_0, gnssLocation.v1_0) && android.os.HidlSupport.deepEquals(this.elapsedRealtime, gnssLocation.elapsedRealtime)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.v1_0)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.elapsedRealtime)));
    }

    public final java.lang.String toString() {
        return "{.v1_0 = " + this.v1_0 + ", .elapsedRealtime = " + this.elapsedRealtime + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(88L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.gnss.V2_0.GnssLocation> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.gnss.V2_0.GnssLocation> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 88, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.gnss.V2_0.GnssLocation gnssLocation = new android.hardware.gnss.V2_0.GnssLocation();
            gnssLocation.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 88);
            arrayList.add(gnssLocation);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.v1_0.readEmbeddedFromParcel(hwParcel, hwBlob, 0 + j);
        this.elapsedRealtime.readEmbeddedFromParcel(hwParcel, hwBlob, j + 64);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(88);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.gnss.V2_0.GnssLocation> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 88);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 88);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        this.v1_0.writeEmbeddedToBlob(hwBlob, 0 + j);
        this.elapsedRealtime.writeEmbeddedToBlob(hwBlob, j + 64);
    }
}
