package android.hardware.contexthub.V1_0;

/* loaded from: classes.dex */
public final class HubAppInfo {
    public long appId = 0;
    public int version = 0;
    public java.util.ArrayList<android.hardware.contexthub.V1_0.MemRange> memUsage = new java.util.ArrayList<>();
    public boolean enabled = false;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.contexthub.V1_0.HubAppInfo.class) {
            return false;
        }
        android.hardware.contexthub.V1_0.HubAppInfo hubAppInfo = (android.hardware.contexthub.V1_0.HubAppInfo) obj;
        if (this.appId == hubAppInfo.appId && this.version == hubAppInfo.version && android.os.HidlSupport.deepEquals(this.memUsage, hubAppInfo.memUsage) && this.enabled == hubAppInfo.enabled) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.appId))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.version))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.memUsage)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.enabled))));
    }

    public final java.lang.String toString() {
        return "{.appId = " + this.appId + ", .version = " + this.version + ", .memUsage = " + this.memUsage + ", .enabled = " + this.enabled + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.contexthub.V1_0.HubAppInfo> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.contexthub.V1_0.HubAppInfo> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.contexthub.V1_0.HubAppInfo hubAppInfo = new android.hardware.contexthub.V1_0.HubAppInfo();
            hubAppInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 40);
            arrayList.add(hubAppInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.appId = hwBlob.getInt64(j + 0);
        this.version = hwBlob.getInt32(j + 8);
        long j2 = j + 16;
        int int32 = hwBlob.getInt32(8 + j2);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, hwBlob.handle(), j2 + 0, true);
        this.memUsage.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.contexthub.V1_0.MemRange memRange = new android.hardware.contexthub.V1_0.MemRange();
            memRange.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 16);
            this.memUsage.add(memRange);
        }
        this.enabled = hwBlob.getBool(j + 32);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(40);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.contexthub.V1_0.HubAppInfo> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 40);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 40);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt64(j + 0, this.appId);
        hwBlob.putInt32(j + 8, this.version);
        int size = this.memUsage.size();
        long j2 = 16 + j;
        hwBlob.putInt32(8 + j2, size);
        hwBlob.putBool(12 + j2, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 16);
        for (int i = 0; i < size; i++) {
            this.memUsage.get(i).writeEmbeddedToBlob(hwBlob2, i * 16);
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
        hwBlob.putBool(j + 32, this.enabled);
    }
}
