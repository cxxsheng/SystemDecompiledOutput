package android.hardware.radio.V1_6;

/* loaded from: classes2.dex */
public final class ImsiEncryptionInfo {
    public android.hardware.radio.V1_1.ImsiEncryptionInfo base = new android.hardware.radio.V1_1.ImsiEncryptionInfo();
    public byte keyType = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_6.ImsiEncryptionInfo.class) {
            return false;
        }
        android.hardware.radio.V1_6.ImsiEncryptionInfo imsiEncryptionInfo = (android.hardware.radio.V1_6.ImsiEncryptionInfo) obj;
        if (android.os.HidlSupport.deepEquals(this.base, imsiEncryptionInfo.base) && this.keyType == imsiEncryptionInfo.keyType) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.base)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.keyType))));
    }

    public final java.lang.String toString() {
        return "{.base = " + this.base + ", .keyType = " + android.hardware.radio.V1_6.PublicKeyType.toString(this.keyType) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(80L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_6.ImsiEncryptionInfo> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_6.ImsiEncryptionInfo> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 80, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_6.ImsiEncryptionInfo imsiEncryptionInfo = new android.hardware.radio.V1_6.ImsiEncryptionInfo();
            imsiEncryptionInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 80);
            arrayList.add(imsiEncryptionInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, 0 + j);
        this.keyType = hwBlob.getInt8(j + 72);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(80);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_6.ImsiEncryptionInfo> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 80);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 80);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        this.base.writeEmbeddedToBlob(hwBlob, 0 + j);
        hwBlob.putInt8(j + 72, this.keyType);
    }
}
