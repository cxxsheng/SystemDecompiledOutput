package android.hardware.radio.V1_1;

/* loaded from: classes2.dex */
public final class ImsiEncryptionInfo {
    public java.lang.String mcc = new java.lang.String();
    public java.lang.String mnc = new java.lang.String();
    public java.util.ArrayList<java.lang.Byte> carrierKey = new java.util.ArrayList<>();
    public java.lang.String keyIdentifier = new java.lang.String();
    public long expirationTime = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_1.ImsiEncryptionInfo.class) {
            return false;
        }
        android.hardware.radio.V1_1.ImsiEncryptionInfo imsiEncryptionInfo = (android.hardware.radio.V1_1.ImsiEncryptionInfo) obj;
        if (android.os.HidlSupport.deepEquals(this.mcc, imsiEncryptionInfo.mcc) && android.os.HidlSupport.deepEquals(this.mnc, imsiEncryptionInfo.mnc) && android.os.HidlSupport.deepEquals(this.carrierKey, imsiEncryptionInfo.carrierKey) && android.os.HidlSupport.deepEquals(this.keyIdentifier, imsiEncryptionInfo.keyIdentifier) && this.expirationTime == imsiEncryptionInfo.expirationTime) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.mcc)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.mnc)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.carrierKey)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.keyIdentifier)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.expirationTime))));
    }

    public final java.lang.String toString() {
        return "{.mcc = " + this.mcc + ", .mnc = " + this.mnc + ", .carrierKey = " + this.carrierKey + ", .keyIdentifier = " + this.keyIdentifier + ", .expirationTime = " + this.expirationTime + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(72L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_1.ImsiEncryptionInfo> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_1.ImsiEncryptionInfo> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 72, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_1.ImsiEncryptionInfo imsiEncryptionInfo = new android.hardware.radio.V1_1.ImsiEncryptionInfo();
            imsiEncryptionInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 72);
            arrayList.add(imsiEncryptionInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        long j2 = j + 0;
        this.mcc = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.mcc.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        long j3 = j + 16;
        this.mnc = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(this.mnc.getBytes().length + 1, hwBlob.handle(), j3 + 0, false);
        long j4 = j + 32;
        int int32 = hwBlob.getInt32(8 + j4);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 1, hwBlob.handle(), j4 + 0, true);
        this.carrierKey.clear();
        for (int i = 0; i < int32; i++) {
            this.carrierKey.add(java.lang.Byte.valueOf(readEmbeddedBuffer.getInt8(i * 1)));
        }
        long j5 = j + 48;
        this.keyIdentifier = hwBlob.getString(j5);
        hwParcel.readEmbeddedBuffer(this.keyIdentifier.getBytes().length + 1, hwBlob.handle(), j5 + 0, false);
        this.expirationTime = hwBlob.getInt64(j + 64);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(72);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_1.ImsiEncryptionInfo> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 72);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 72);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putString(j + 0, this.mcc);
        hwBlob.putString(16 + j, this.mnc);
        int size = this.carrierKey.size();
        long j2 = 32 + j;
        hwBlob.putInt32(8 + j2, size);
        hwBlob.putBool(12 + j2, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 1);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt8(i * 1, this.carrierKey.get(i).byteValue());
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
        hwBlob.putString(48 + j, this.keyIdentifier);
        hwBlob.putInt64(j + 64, this.expirationTime);
    }
}
