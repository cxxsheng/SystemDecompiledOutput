package android.hardware.contexthub.V1_0;

/* loaded from: classes.dex */
public final class MemRange {
    public int flags;
    public int totalBytes = 0;
    public int freeBytes = 0;
    public int type = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.contexthub.V1_0.MemRange.class) {
            return false;
        }
        android.hardware.contexthub.V1_0.MemRange memRange = (android.hardware.contexthub.V1_0.MemRange) obj;
        if (this.totalBytes == memRange.totalBytes && this.freeBytes == memRange.freeBytes && this.type == memRange.type && android.os.HidlSupport.deepEquals(java.lang.Integer.valueOf(this.flags), java.lang.Integer.valueOf(memRange.flags))) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.totalBytes))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.freeBytes))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.type))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.flags))));
    }

    public final java.lang.String toString() {
        return "{.totalBytes = " + this.totalBytes + ", .freeBytes = " + this.freeBytes + ", .type = " + android.hardware.contexthub.V1_0.HubMemoryType.toString(this.type) + ", .flags = " + android.hardware.contexthub.V1_0.HubMemoryFlag.dumpBitfield(this.flags) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(16L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.contexthub.V1_0.MemRange> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.contexthub.V1_0.MemRange> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.contexthub.V1_0.MemRange memRange = new android.hardware.contexthub.V1_0.MemRange();
            memRange.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 16);
            arrayList.add(memRange);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.totalBytes = hwBlob.getInt32(0 + j);
        this.freeBytes = hwBlob.getInt32(4 + j);
        this.type = hwBlob.getInt32(8 + j);
        this.flags = hwBlob.getInt32(j + 12);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.contexthub.V1_0.MemRange> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 16);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 16);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(0 + j, this.totalBytes);
        hwBlob.putInt32(4 + j, this.freeBytes);
        hwBlob.putInt32(8 + j, this.type);
        hwBlob.putInt32(j + 12, this.flags);
    }
}
