package android.hardware.radio.V1_4;

/* loaded from: classes2.dex */
public final class PhysicalChannelConfig {
    public android.hardware.radio.V1_2.PhysicalChannelConfig base = new android.hardware.radio.V1_2.PhysicalChannelConfig();
    public int rat = 0;
    public android.hardware.radio.V1_4.RadioFrequencyInfo rfInfo = new android.hardware.radio.V1_4.RadioFrequencyInfo();
    public java.util.ArrayList<java.lang.Integer> contextIds = new java.util.ArrayList<>();
    public int physicalCellId = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_4.PhysicalChannelConfig.class) {
            return false;
        }
        android.hardware.radio.V1_4.PhysicalChannelConfig physicalChannelConfig = (android.hardware.radio.V1_4.PhysicalChannelConfig) obj;
        if (android.os.HidlSupport.deepEquals(this.base, physicalChannelConfig.base) && this.rat == physicalChannelConfig.rat && android.os.HidlSupport.deepEquals(this.rfInfo, physicalChannelConfig.rfInfo) && android.os.HidlSupport.deepEquals(this.contextIds, physicalChannelConfig.contextIds) && this.physicalCellId == physicalChannelConfig.physicalCellId) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.base)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.rat))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.rfInfo)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.contextIds)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.physicalCellId))));
    }

    public final java.lang.String toString() {
        return "{.base = " + this.base + ", .rat = " + android.hardware.radio.V1_4.RadioTechnology.toString(this.rat) + ", .rfInfo = " + this.rfInfo + ", .contextIds = " + this.contextIds + ", .physicalCellId = " + this.physicalCellId + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(48L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_4.PhysicalChannelConfig> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_4.PhysicalChannelConfig> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 48, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_4.PhysicalChannelConfig physicalChannelConfig = new android.hardware.radio.V1_4.PhysicalChannelConfig();
            physicalChannelConfig.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 48);
            arrayList.add(physicalChannelConfig);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, j + 0);
        this.rat = hwBlob.getInt32(j + 8);
        this.rfInfo.readEmbeddedFromParcel(hwParcel, hwBlob, j + 12);
        long j2 = j + 24;
        int int32 = hwBlob.getInt32(8 + j2);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 4, hwBlob.handle(), j2 + 0, true);
        this.contextIds.clear();
        for (int i = 0; i < int32; i++) {
            this.contextIds.add(java.lang.Integer.valueOf(readEmbeddedBuffer.getInt32(i * 4)));
        }
        this.physicalCellId = hwBlob.getInt32(j + 40);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(48);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_4.PhysicalChannelConfig> arrayList) {
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
        this.base.writeEmbeddedToBlob(hwBlob, j + 0);
        hwBlob.putInt32(j + 8, this.rat);
        this.rfInfo.writeEmbeddedToBlob(hwBlob, j + 12);
        int size = this.contextIds.size();
        long j2 = 24 + j;
        hwBlob.putInt32(8 + j2, size);
        hwBlob.putBool(12 + j2, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 4);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt32(i * 4, this.contextIds.get(i).intValue());
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
        hwBlob.putInt32(j + 40, this.physicalCellId);
    }
}
