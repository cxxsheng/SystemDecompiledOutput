package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class CellIdentityCdma {
    public int networkId = 0;
    public int systemId = 0;
    public int baseStationId = 0;
    public int longitude = 0;
    public int latitude = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.CellIdentityCdma.class) {
            return false;
        }
        android.hardware.radio.V1_0.CellIdentityCdma cellIdentityCdma = (android.hardware.radio.V1_0.CellIdentityCdma) obj;
        if (this.networkId == cellIdentityCdma.networkId && this.systemId == cellIdentityCdma.systemId && this.baseStationId == cellIdentityCdma.baseStationId && this.longitude == cellIdentityCdma.longitude && this.latitude == cellIdentityCdma.latitude) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.networkId))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.systemId))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.baseStationId))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.longitude))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.latitude))));
    }

    public final java.lang.String toString() {
        return "{.networkId = " + this.networkId + ", .systemId = " + this.systemId + ", .baseStationId = " + this.baseStationId + ", .longitude = " + this.longitude + ", .latitude = " + this.latitude + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(20L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.CellIdentityCdma> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.CellIdentityCdma> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 20, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.CellIdentityCdma cellIdentityCdma = new android.hardware.radio.V1_0.CellIdentityCdma();
            cellIdentityCdma.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 20);
            arrayList.add(cellIdentityCdma);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.networkId = hwBlob.getInt32(0 + j);
        this.systemId = hwBlob.getInt32(4 + j);
        this.baseStationId = hwBlob.getInt32(8 + j);
        this.longitude = hwBlob.getInt32(12 + j);
        this.latitude = hwBlob.getInt32(j + 16);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(20);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.CellIdentityCdma> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 20);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 20);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(0 + j, this.networkId);
        hwBlob.putInt32(4 + j, this.systemId);
        hwBlob.putInt32(8 + j, this.baseStationId);
        hwBlob.putInt32(12 + j, this.longitude);
        hwBlob.putInt32(j + 16, this.latitude);
    }
}
