package android.hardware.radio.V1_2;

/* loaded from: classes2.dex */
public final class NetworkScanRequest {
    public int type = 0;
    public int interval = 0;
    public java.util.ArrayList<android.hardware.radio.V1_1.RadioAccessSpecifier> specifiers = new java.util.ArrayList<>();
    public int maxSearchTime = 0;
    public boolean incrementalResults = false;
    public int incrementalResultsPeriodicity = 0;
    public java.util.ArrayList<java.lang.String> mccMncs = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_2.NetworkScanRequest.class) {
            return false;
        }
        android.hardware.radio.V1_2.NetworkScanRequest networkScanRequest = (android.hardware.radio.V1_2.NetworkScanRequest) obj;
        if (this.type == networkScanRequest.type && this.interval == networkScanRequest.interval && android.os.HidlSupport.deepEquals(this.specifiers, networkScanRequest.specifiers) && this.maxSearchTime == networkScanRequest.maxSearchTime && this.incrementalResults == networkScanRequest.incrementalResults && this.incrementalResultsPeriodicity == networkScanRequest.incrementalResultsPeriodicity && android.os.HidlSupport.deepEquals(this.mccMncs, networkScanRequest.mccMncs)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.type))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.interval))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.specifiers)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.maxSearchTime))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.incrementalResults))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.incrementalResultsPeriodicity))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.mccMncs)));
    }

    public final java.lang.String toString() {
        return "{.type = " + android.hardware.radio.V1_1.ScanType.toString(this.type) + ", .interval = " + this.interval + ", .specifiers = " + this.specifiers + ", .maxSearchTime = " + this.maxSearchTime + ", .incrementalResults = " + this.incrementalResults + ", .incrementalResultsPeriodicity = " + this.incrementalResultsPeriodicity + ", .mccMncs = " + this.mccMncs + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(56L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_2.NetworkScanRequest> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_2.NetworkScanRequest> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 56, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_2.NetworkScanRequest networkScanRequest = new android.hardware.radio.V1_2.NetworkScanRequest();
            networkScanRequest.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 56);
            arrayList.add(networkScanRequest);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.type = hwBlob.getInt32(j + 0);
        this.interval = hwBlob.getInt32(j + 4);
        long j2 = j + 8;
        int int32 = hwBlob.getInt32(j2 + 8);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 72, hwBlob.handle(), j2 + 0, true);
        this.specifiers.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_1.RadioAccessSpecifier radioAccessSpecifier = new android.hardware.radio.V1_1.RadioAccessSpecifier();
            radioAccessSpecifier.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 72);
            this.specifiers.add(radioAccessSpecifier);
        }
        this.maxSearchTime = hwBlob.getInt32(j + 24);
        this.incrementalResults = hwBlob.getBool(j + 28);
        this.incrementalResultsPeriodicity = hwBlob.getInt32(j + 32);
        long j3 = j + 40;
        int int322 = hwBlob.getInt32(8 + j3);
        android.os.HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 16, hwBlob.handle(), j3 + 0, true);
        this.mccMncs.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            new java.lang.String();
            java.lang.String string = readEmbeddedBuffer2.getString(i2 * 16);
            hwParcel.readEmbeddedBuffer(string.getBytes().length + 1, readEmbeddedBuffer2.handle(), r3 + 0, false);
            this.mccMncs.add(string);
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(56);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_2.NetworkScanRequest> arrayList) {
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
        hwBlob.putInt32(j + 0, this.type);
        hwBlob.putInt32(j + 4, this.interval);
        int size = this.specifiers.size();
        long j2 = j + 8;
        hwBlob.putInt32(j2 + 8, size);
        hwBlob.putBool(j2 + 12, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 72);
        for (int i = 0; i < size; i++) {
            this.specifiers.get(i).writeEmbeddedToBlob(hwBlob2, i * 72);
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
        hwBlob.putInt32(j + 24, this.maxSearchTime);
        hwBlob.putBool(j + 28, this.incrementalResults);
        hwBlob.putInt32(j + 32, this.incrementalResultsPeriodicity);
        int size2 = this.mccMncs.size();
        long j3 = j + 40;
        hwBlob.putInt32(8 + j3, size2);
        hwBlob.putBool(j3 + 12, false);
        android.os.HwBlob hwBlob3 = new android.os.HwBlob(size2 * 16);
        for (int i2 = 0; i2 < size2; i2++) {
            hwBlob3.putString(i2 * 16, this.mccMncs.get(i2));
        }
        hwBlob.putBlob(j3 + 0, hwBlob3);
    }
}
