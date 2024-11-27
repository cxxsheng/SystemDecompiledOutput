package android.hardware.weaver.V1_0;

/* loaded from: classes.dex */
public final class WeaverConfig {
    public int slots = 0;
    public int keySize = 0;
    public int valueSize = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.weaver.V1_0.WeaverConfig.class) {
            return false;
        }
        android.hardware.weaver.V1_0.WeaverConfig weaverConfig = (android.hardware.weaver.V1_0.WeaverConfig) obj;
        if (this.slots == weaverConfig.slots && this.keySize == weaverConfig.keySize && this.valueSize == weaverConfig.valueSize) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.slots))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.keySize))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.valueSize))));
    }

    public final java.lang.String toString() {
        return "{.slots = " + this.slots + ", .keySize = " + this.keySize + ", .valueSize = " + this.valueSize + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(12L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.weaver.V1_0.WeaverConfig> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.weaver.V1_0.WeaverConfig> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 12, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.weaver.V1_0.WeaverConfig weaverConfig = new android.hardware.weaver.V1_0.WeaverConfig();
            weaverConfig.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 12);
            arrayList.add(weaverConfig);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.slots = hwBlob.getInt32(0 + j);
        this.keySize = hwBlob.getInt32(4 + j);
        this.valueSize = hwBlob.getInt32(j + 8);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(12);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.weaver.V1_0.WeaverConfig> arrayList) {
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
        hwBlob.putInt32(0 + j, this.slots);
        hwBlob.putInt32(4 + j, this.keySize);
        hwBlob.putInt32(j + 8, this.valueSize);
    }
}
