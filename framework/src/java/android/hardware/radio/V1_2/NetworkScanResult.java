package android.hardware.radio.V1_2;

/* loaded from: classes2.dex */
public final class NetworkScanResult {
    public int status = 0;
    public int error = 0;
    public java.util.ArrayList<android.hardware.radio.V1_2.CellInfo> networkInfos = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_2.NetworkScanResult.class) {
            return false;
        }
        android.hardware.radio.V1_2.NetworkScanResult networkScanResult = (android.hardware.radio.V1_2.NetworkScanResult) obj;
        if (this.status == networkScanResult.status && this.error == networkScanResult.error && android.os.HidlSupport.deepEquals(this.networkInfos, networkScanResult.networkInfos)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.status))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.error))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.networkInfos)));
    }

    public final java.lang.String toString() {
        return "{.status = " + android.hardware.radio.V1_1.ScanStatus.toString(this.status) + ", .error = " + android.hardware.radio.V1_0.RadioError.toString(this.error) + ", .networkInfos = " + this.networkInfos + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_2.NetworkScanResult> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_2.NetworkScanResult> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_2.NetworkScanResult networkScanResult = new android.hardware.radio.V1_2.NetworkScanResult();
            networkScanResult.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 24);
            arrayList.add(networkScanResult);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.status = hwBlob.getInt32(j + 0);
        this.error = hwBlob.getInt32(j + 4);
        long j2 = j + 8;
        int int32 = hwBlob.getInt32(8 + j2);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 112, hwBlob.handle(), j2 + 0, true);
        this.networkInfos.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_2.CellInfo cellInfo = new android.hardware.radio.V1_2.CellInfo();
            cellInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 112);
            this.networkInfos.add(cellInfo);
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(24);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_2.NetworkScanResult> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 24);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 24);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(j + 0, this.status);
        hwBlob.putInt32(4 + j, this.error);
        int size = this.networkInfos.size();
        long j2 = j + 8;
        hwBlob.putInt32(8 + j2, size);
        hwBlob.putBool(12 + j2, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 112);
        for (int i = 0; i < size; i++) {
            this.networkInfos.get(i).writeEmbeddedToBlob(hwBlob2, i * 112);
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
    }
}
