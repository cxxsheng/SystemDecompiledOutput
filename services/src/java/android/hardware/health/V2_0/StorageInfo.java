package android.hardware.health.V2_0;

/* loaded from: classes.dex */
public final class StorageInfo {
    public android.hardware.health.V2_0.StorageAttribute attr = new android.hardware.health.V2_0.StorageAttribute();
    public short eol = 0;
    public short lifetimeA = 0;
    public short lifetimeB = 0;
    public java.lang.String version = new java.lang.String();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.health.V2_0.StorageInfo.class) {
            return false;
        }
        android.hardware.health.V2_0.StorageInfo storageInfo = (android.hardware.health.V2_0.StorageInfo) obj;
        if (android.os.HidlSupport.deepEquals(this.attr, storageInfo.attr) && this.eol == storageInfo.eol && this.lifetimeA == storageInfo.lifetimeA && this.lifetimeB == storageInfo.lifetimeB && android.os.HidlSupport.deepEquals(this.version, storageInfo.version)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.attr)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Short.valueOf(this.eol))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Short.valueOf(this.lifetimeA))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Short.valueOf(this.lifetimeB))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.version)));
    }

    public final java.lang.String toString() {
        return "{.attr = " + this.attr + ", .eol = " + ((int) this.eol) + ", .lifetimeA = " + ((int) this.lifetimeA) + ", .lifetimeB = " + ((int) this.lifetimeB) + ", .version = " + this.version + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(48L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.health.V2_0.StorageInfo> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.health.V2_0.StorageInfo> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 48, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.health.V2_0.StorageInfo storageInfo = new android.hardware.health.V2_0.StorageInfo();
            storageInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 48);
            arrayList.add(storageInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.attr.readEmbeddedFromParcel(hwParcel, hwBlob, j + 0);
        this.eol = hwBlob.getInt16(24 + j);
        this.lifetimeA = hwBlob.getInt16(26 + j);
        this.lifetimeB = hwBlob.getInt16(28 + j);
        long j2 = j + 32;
        this.version = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.version.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(48);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.health.V2_0.StorageInfo> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 48);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 48);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        this.attr.writeEmbeddedToBlob(hwBlob, 0 + j);
        hwBlob.putInt16(24 + j, this.eol);
        hwBlob.putInt16(26 + j, this.lifetimeA);
        hwBlob.putInt16(28 + j, this.lifetimeB);
        hwBlob.putString(j + 32, this.version);
    }
}
