package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class StkCcUnsolSsResult {
    public int serviceClass;
    public int serviceType = 0;
    public int requestType = 0;
    public int teleserviceType = 0;
    public int result = 0;
    public java.util.ArrayList<android.hardware.radio.V1_0.SsInfoData> ssInfo = new java.util.ArrayList<>();
    public java.util.ArrayList<android.hardware.radio.V1_0.CfData> cfData = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.StkCcUnsolSsResult.class) {
            return false;
        }
        android.hardware.radio.V1_0.StkCcUnsolSsResult stkCcUnsolSsResult = (android.hardware.radio.V1_0.StkCcUnsolSsResult) obj;
        if (this.serviceType == stkCcUnsolSsResult.serviceType && this.requestType == stkCcUnsolSsResult.requestType && this.teleserviceType == stkCcUnsolSsResult.teleserviceType && android.os.HidlSupport.deepEquals(java.lang.Integer.valueOf(this.serviceClass), java.lang.Integer.valueOf(stkCcUnsolSsResult.serviceClass)) && this.result == stkCcUnsolSsResult.result && android.os.HidlSupport.deepEquals(this.ssInfo, stkCcUnsolSsResult.ssInfo) && android.os.HidlSupport.deepEquals(this.cfData, stkCcUnsolSsResult.cfData)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.serviceType))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.requestType))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.teleserviceType))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.serviceClass))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.result))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.ssInfo)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.cfData)));
    }

    public final java.lang.String toString() {
        return "{.serviceType = " + android.hardware.radio.V1_0.SsServiceType.toString(this.serviceType) + ", .requestType = " + android.hardware.radio.V1_0.SsRequestType.toString(this.requestType) + ", .teleserviceType = " + android.hardware.radio.V1_0.SsTeleserviceType.toString(this.teleserviceType) + ", .serviceClass = " + android.hardware.radio.V1_0.SuppServiceClass.dumpBitfield(this.serviceClass) + ", .result = " + android.hardware.radio.V1_0.RadioError.toString(this.result) + ", .ssInfo = " + this.ssInfo + ", .cfData = " + this.cfData + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(56L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.StkCcUnsolSsResult> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.StkCcUnsolSsResult> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 56, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.StkCcUnsolSsResult stkCcUnsolSsResult = new android.hardware.radio.V1_0.StkCcUnsolSsResult();
            stkCcUnsolSsResult.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 56);
            arrayList.add(stkCcUnsolSsResult);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.serviceType = hwBlob.getInt32(j + 0);
        this.requestType = hwBlob.getInt32(j + 4);
        this.teleserviceType = hwBlob.getInt32(j + 8);
        this.serviceClass = hwBlob.getInt32(j + 12);
        this.result = hwBlob.getInt32(j + 16);
        long j2 = j + 24;
        int int32 = hwBlob.getInt32(j2 + 8);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, hwBlob.handle(), j2 + 0, true);
        this.ssInfo.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.SsInfoData ssInfoData = new android.hardware.radio.V1_0.SsInfoData();
            ssInfoData.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 16);
            this.ssInfo.add(ssInfoData);
        }
        long j3 = j + 40;
        int int322 = hwBlob.getInt32(8 + j3);
        android.os.HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 16, hwBlob.handle(), j3 + 0, true);
        this.cfData.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            android.hardware.radio.V1_0.CfData cfData = new android.hardware.radio.V1_0.CfData();
            cfData.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer2, i2 * 16);
            this.cfData.add(cfData);
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(56);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.StkCcUnsolSsResult> arrayList) {
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
        hwBlob.putInt32(j + 0, this.serviceType);
        hwBlob.putInt32(j + 4, this.requestType);
        hwBlob.putInt32(j + 8, this.teleserviceType);
        hwBlob.putInt32(j + 12, this.serviceClass);
        hwBlob.putInt32(j + 16, this.result);
        int size = this.ssInfo.size();
        long j2 = j + 24;
        hwBlob.putInt32(j2 + 8, size);
        hwBlob.putBool(j2 + 12, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 16);
        for (int i = 0; i < size; i++) {
            this.ssInfo.get(i).writeEmbeddedToBlob(hwBlob2, i * 16);
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
        int size2 = this.cfData.size();
        long j3 = j + 40;
        hwBlob.putInt32(8 + j3, size2);
        hwBlob.putBool(12 + j3, false);
        android.os.HwBlob hwBlob3 = new android.os.HwBlob(size2 * 16);
        for (int i2 = 0; i2 < size2; i2++) {
            this.cfData.get(i2).writeEmbeddedToBlob(hwBlob3, i2 * 16);
        }
        hwBlob.putBlob(j3 + 0, hwBlob3);
    }
}
