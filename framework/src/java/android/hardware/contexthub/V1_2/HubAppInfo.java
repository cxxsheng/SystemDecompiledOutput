package android.hardware.contexthub.V1_2;

/* loaded from: classes2.dex */
public final class HubAppInfo {
    public android.hardware.contexthub.V1_0.HubAppInfo info_1_0 = new android.hardware.contexthub.V1_0.HubAppInfo();
    public java.util.ArrayList<java.lang.String> permissions = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.contexthub.V1_2.HubAppInfo.class) {
            return false;
        }
        android.hardware.contexthub.V1_2.HubAppInfo hubAppInfo = (android.hardware.contexthub.V1_2.HubAppInfo) obj;
        if (android.os.HidlSupport.deepEquals(this.info_1_0, hubAppInfo.info_1_0) && android.os.HidlSupport.deepEquals(this.permissions, hubAppInfo.permissions)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.info_1_0)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.permissions)));
    }

    public final java.lang.String toString() {
        return "{.info_1_0 = " + this.info_1_0 + ", .permissions = " + this.permissions + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(56L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.contexthub.V1_2.HubAppInfo> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.contexthub.V1_2.HubAppInfo> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 56, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.contexthub.V1_2.HubAppInfo hubAppInfo = new android.hardware.contexthub.V1_2.HubAppInfo();
            hubAppInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 56);
            arrayList.add(hubAppInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.info_1_0.readEmbeddedFromParcel(hwParcel, hwBlob, j + 0);
        long j2 = j + 40;
        int int32 = hwBlob.getInt32(8 + j2);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, hwBlob.handle(), j2 + 0, true);
        this.permissions.clear();
        for (int i = 0; i < int32; i++) {
            new java.lang.String();
            java.lang.String string = readEmbeddedBuffer.getString(i * 16);
            hwParcel.readEmbeddedBuffer(string.getBytes().length + 1, readEmbeddedBuffer.handle(), r5 + 0, false);
            this.permissions.add(string);
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(56);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.contexthub.V1_2.HubAppInfo> arrayList) {
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
        this.info_1_0.writeEmbeddedToBlob(hwBlob, j + 0);
        int size = this.permissions.size();
        long j2 = j + 40;
        hwBlob.putInt32(8 + j2, size);
        hwBlob.putBool(12 + j2, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 16);
        for (int i = 0; i < size; i++) {
            hwBlob2.putString(i * 16, this.permissions.get(i));
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
    }
}
