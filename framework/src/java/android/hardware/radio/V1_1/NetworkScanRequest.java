package android.hardware.radio.V1_1;

/* loaded from: classes2.dex */
public final class NetworkScanRequest {
    public int type = 0;
    public int interval = 0;
    public java.util.ArrayList<android.hardware.radio.V1_1.RadioAccessSpecifier> specifiers = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_1.NetworkScanRequest.class) {
            return false;
        }
        android.hardware.radio.V1_1.NetworkScanRequest networkScanRequest = (android.hardware.radio.V1_1.NetworkScanRequest) obj;
        if (this.type == networkScanRequest.type && this.interval == networkScanRequest.interval && android.os.HidlSupport.deepEquals(this.specifiers, networkScanRequest.specifiers)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.type))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.interval))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.specifiers)));
    }

    public final java.lang.String toString() {
        return "{.type = " + android.hardware.radio.V1_1.ScanType.toString(this.type) + ", .interval = " + this.interval + ", .specifiers = " + this.specifiers + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_1.NetworkScanRequest> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_1.NetworkScanRequest> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_1.NetworkScanRequest networkScanRequest = new android.hardware.radio.V1_1.NetworkScanRequest();
            networkScanRequest.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 24);
            arrayList.add(networkScanRequest);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.type = hwBlob.getInt32(j + 0);
        this.interval = hwBlob.getInt32(j + 4);
        long j2 = j + 8;
        int int32 = hwBlob.getInt32(8 + j2);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 72, hwBlob.handle(), j2 + 0, true);
        this.specifiers.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_1.RadioAccessSpecifier radioAccessSpecifier = new android.hardware.radio.V1_1.RadioAccessSpecifier();
            radioAccessSpecifier.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 72);
            this.specifiers.add(radioAccessSpecifier);
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(24);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_1.NetworkScanRequest> arrayList) {
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
        hwBlob.putInt32(j + 0, this.type);
        hwBlob.putInt32(4 + j, this.interval);
        int size = this.specifiers.size();
        long j2 = j + 8;
        hwBlob.putInt32(8 + j2, size);
        hwBlob.putBool(12 + j2, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 72);
        for (int i = 0; i < size; i++) {
            this.specifiers.get(i).writeEmbeddedToBlob(hwBlob2, i * 72);
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
    }
}
