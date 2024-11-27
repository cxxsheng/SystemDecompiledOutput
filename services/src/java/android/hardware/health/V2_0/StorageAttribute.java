package android.hardware.health.V2_0;

/* loaded from: classes.dex */
public final class StorageAttribute {
    public boolean isInternal = false;
    public boolean isBootDevice = false;
    public java.lang.String name = new java.lang.String();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.health.V2_0.StorageAttribute.class) {
            return false;
        }
        android.hardware.health.V2_0.StorageAttribute storageAttribute = (android.hardware.health.V2_0.StorageAttribute) obj;
        if (this.isInternal == storageAttribute.isInternal && this.isBootDevice == storageAttribute.isBootDevice && android.os.HidlSupport.deepEquals(this.name, storageAttribute.name)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.isInternal))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.isBootDevice))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.name)));
    }

    public final java.lang.String toString() {
        return "{.isInternal = " + this.isInternal + ", .isBootDevice = " + this.isBootDevice + ", .name = " + this.name + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.health.V2_0.StorageAttribute> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.health.V2_0.StorageAttribute> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.health.V2_0.StorageAttribute storageAttribute = new android.hardware.health.V2_0.StorageAttribute();
            storageAttribute.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 24);
            arrayList.add(storageAttribute);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.isInternal = hwBlob.getBool(j + 0);
        this.isBootDevice = hwBlob.getBool(1 + j);
        long j2 = j + 8;
        this.name = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.name.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(24);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.health.V2_0.StorageAttribute> arrayList) {
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
        hwBlob.putBool(0 + j, this.isInternal);
        hwBlob.putBool(1 + j, this.isBootDevice);
        hwBlob.putString(j + 8, this.name);
    }
}
