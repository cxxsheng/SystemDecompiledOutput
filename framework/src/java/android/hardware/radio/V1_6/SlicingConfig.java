package android.hardware.radio.V1_6;

/* loaded from: classes2.dex */
public final class SlicingConfig {
    public java.util.ArrayList<android.hardware.radio.V1_6.UrspRule> urspRules = new java.util.ArrayList<>();
    public java.util.ArrayList<android.hardware.radio.V1_6.SliceInfo> sliceInfo = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_6.SlicingConfig.class) {
            return false;
        }
        android.hardware.radio.V1_6.SlicingConfig slicingConfig = (android.hardware.radio.V1_6.SlicingConfig) obj;
        if (android.os.HidlSupport.deepEquals(this.urspRules, slicingConfig.urspRules) && android.os.HidlSupport.deepEquals(this.sliceInfo, slicingConfig.sliceInfo)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.urspRules)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.sliceInfo)));
    }

    public final java.lang.String toString() {
        return "{.urspRules = " + this.urspRules + ", .sliceInfo = " + this.sliceInfo + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_6.SlicingConfig> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_6.SlicingConfig> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_6.SlicingConfig slicingConfig = new android.hardware.radio.V1_6.SlicingConfig();
            slicingConfig.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 32);
            arrayList.add(slicingConfig);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        long j2 = j + 0;
        int int32 = hwBlob.getInt32(j2 + 8);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, hwBlob.handle(), j2 + 0, true);
        this.urspRules.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_6.UrspRule urspRule = new android.hardware.radio.V1_6.UrspRule();
            urspRule.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 40);
            this.urspRules.add(urspRule);
        }
        long j3 = j + 16;
        int int322 = hwBlob.getInt32(8 + j3);
        android.os.HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 20, hwBlob.handle(), j3 + 0, true);
        this.sliceInfo.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            android.hardware.radio.V1_6.SliceInfo sliceInfo = new android.hardware.radio.V1_6.SliceInfo();
            sliceInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer2, i2 * 20);
            this.sliceInfo.add(sliceInfo);
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(32);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_6.SlicingConfig> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 32);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 32);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        int size = this.urspRules.size();
        long j2 = j + 0;
        hwBlob.putInt32(j2 + 8, size);
        hwBlob.putBool(j2 + 12, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 40);
        for (int i = 0; i < size; i++) {
            this.urspRules.get(i).writeEmbeddedToBlob(hwBlob2, i * 40);
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
        int size2 = this.sliceInfo.size();
        long j3 = j + 16;
        hwBlob.putInt32(8 + j3, size2);
        hwBlob.putBool(j3 + 12, false);
        android.os.HwBlob hwBlob3 = new android.os.HwBlob(size2 * 20);
        for (int i2 = 0; i2 < size2; i2++) {
            this.sliceInfo.get(i2).writeEmbeddedToBlob(hwBlob3, i2 * 20);
        }
        hwBlob.putBlob(j3 + 0, hwBlob3);
    }
}
