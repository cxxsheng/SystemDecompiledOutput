package android.hardware.contexthub.V1_0;

/* loaded from: classes.dex */
public final class NanoAppBinary {
    public int flags;
    public long appId = 0;
    public int appVersion = 0;
    public byte targetChreApiMajorVersion = 0;
    public byte targetChreApiMinorVersion = 0;
    public java.util.ArrayList<java.lang.Byte> customBinary = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.contexthub.V1_0.NanoAppBinary.class) {
            return false;
        }
        android.hardware.contexthub.V1_0.NanoAppBinary nanoAppBinary = (android.hardware.contexthub.V1_0.NanoAppBinary) obj;
        if (this.appId == nanoAppBinary.appId && this.appVersion == nanoAppBinary.appVersion && android.os.HidlSupport.deepEquals(java.lang.Integer.valueOf(this.flags), java.lang.Integer.valueOf(nanoAppBinary.flags)) && this.targetChreApiMajorVersion == nanoAppBinary.targetChreApiMajorVersion && this.targetChreApiMinorVersion == nanoAppBinary.targetChreApiMinorVersion && android.os.HidlSupport.deepEquals(this.customBinary, nanoAppBinary.customBinary)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.appId))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.appVersion))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.flags))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.targetChreApiMajorVersion))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.targetChreApiMinorVersion))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.customBinary)));
    }

    public final java.lang.String toString() {
        return "{.appId = " + this.appId + ", .appVersion = " + this.appVersion + ", .flags = " + android.hardware.contexthub.V1_0.NanoAppFlags.dumpBitfield(this.flags) + ", .targetChreApiMajorVersion = " + ((int) this.targetChreApiMajorVersion) + ", .targetChreApiMinorVersion = " + ((int) this.targetChreApiMinorVersion) + ", .customBinary = " + this.customBinary + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.contexthub.V1_0.NanoAppBinary> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.contexthub.V1_0.NanoAppBinary> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.contexthub.V1_0.NanoAppBinary nanoAppBinary = new android.hardware.contexthub.V1_0.NanoAppBinary();
            nanoAppBinary.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 40);
            arrayList.add(nanoAppBinary);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.appId = hwBlob.getInt64(j + 0);
        this.appVersion = hwBlob.getInt32(j + 8);
        this.flags = hwBlob.getInt32(j + 12);
        this.targetChreApiMajorVersion = hwBlob.getInt8(j + 16);
        this.targetChreApiMinorVersion = hwBlob.getInt8(j + 17);
        long j2 = j + 24;
        int int32 = hwBlob.getInt32(8 + j2);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 1, hwBlob.handle(), j2 + 0, true);
        this.customBinary.clear();
        for (int i = 0; i < int32; i++) {
            this.customBinary.add(java.lang.Byte.valueOf(readEmbeddedBuffer.getInt8(i * 1)));
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(40);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.contexthub.V1_0.NanoAppBinary> arrayList) {
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
        hwBlob.putInt32(j + 8, this.appVersion);
        hwBlob.putInt32(j + 12, this.flags);
        hwBlob.putInt8(16 + j, this.targetChreApiMajorVersion);
        hwBlob.putInt8(17 + j, this.targetChreApiMinorVersion);
        int size = this.customBinary.size();
        long j2 = j + 24;
        hwBlob.putInt32(8 + j2, size);
        hwBlob.putBool(12 + j2, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 1);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt8(i * 1, this.customBinary.get(i).byteValue());
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
    }
}
