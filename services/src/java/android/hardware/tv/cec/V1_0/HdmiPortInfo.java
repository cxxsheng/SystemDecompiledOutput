package android.hardware.tv.cec.V1_0;

/* loaded from: classes.dex */
public final class HdmiPortInfo {
    public int type = 0;
    public int portId = 0;
    public boolean cecSupported = false;
    public boolean arcSupported = false;
    public short physicalAddress = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.tv.cec.V1_0.HdmiPortInfo.class) {
            return false;
        }
        android.hardware.tv.cec.V1_0.HdmiPortInfo hdmiPortInfo = (android.hardware.tv.cec.V1_0.HdmiPortInfo) obj;
        if (this.type == hdmiPortInfo.type && this.portId == hdmiPortInfo.portId && this.cecSupported == hdmiPortInfo.cecSupported && this.arcSupported == hdmiPortInfo.arcSupported && this.physicalAddress == hdmiPortInfo.physicalAddress) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.type))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.portId))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.cecSupported))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.arcSupported))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Short.valueOf(this.physicalAddress))));
    }

    public final java.lang.String toString() {
        return "{.type = " + android.hardware.tv.cec.V1_0.HdmiPortType.toString(this.type) + ", .portId = " + this.portId + ", .cecSupported = " + this.cecSupported + ", .arcSupported = " + this.arcSupported + ", .physicalAddress = " + ((int) this.physicalAddress) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(12L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.tv.cec.V1_0.HdmiPortInfo> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.tv.cec.V1_0.HdmiPortInfo> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 12, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.tv.cec.V1_0.HdmiPortInfo hdmiPortInfo = new android.hardware.tv.cec.V1_0.HdmiPortInfo();
            hdmiPortInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 12);
            arrayList.add(hdmiPortInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.type = hwBlob.getInt32(0 + j);
        this.portId = hwBlob.getInt32(4 + j);
        this.cecSupported = hwBlob.getBool(8 + j);
        this.arcSupported = hwBlob.getBool(9 + j);
        this.physicalAddress = hwBlob.getInt16(j + 10);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(12);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.tv.cec.V1_0.HdmiPortInfo> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 12);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 12);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(0 + j, this.type);
        hwBlob.putInt32(4 + j, this.portId);
        hwBlob.putBool(8 + j, this.cecSupported);
        hwBlob.putBool(9 + j, this.arcSupported);
        hwBlob.putInt16(j + 10, this.physicalAddress);
    }
}
