package android.hardware.broadcastradio.V2_0;

/* loaded from: classes.dex */
public final class AmFmRegionConfig {
    public byte fmDeemphasis;
    public byte fmRds;
    public java.util.ArrayList<android.hardware.broadcastradio.V2_0.AmFmBandRange> ranges = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.broadcastradio.V2_0.AmFmRegionConfig.class) {
            return false;
        }
        android.hardware.broadcastradio.V2_0.AmFmRegionConfig amFmRegionConfig = (android.hardware.broadcastradio.V2_0.AmFmRegionConfig) obj;
        if (android.os.HidlSupport.deepEquals(this.ranges, amFmRegionConfig.ranges) && android.os.HidlSupport.deepEquals(java.lang.Byte.valueOf(this.fmDeemphasis), java.lang.Byte.valueOf(amFmRegionConfig.fmDeemphasis)) && android.os.HidlSupport.deepEquals(java.lang.Byte.valueOf(this.fmRds), java.lang.Byte.valueOf(amFmRegionConfig.fmRds))) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.ranges)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.fmDeemphasis))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.fmRds))));
    }

    public final java.lang.String toString() {
        return "{.ranges = " + this.ranges + ", .fmDeemphasis = " + android.hardware.broadcastradio.V2_0.Deemphasis.dumpBitfield(this.fmDeemphasis) + ", .fmRds = " + android.hardware.broadcastradio.V2_0.Rds.dumpBitfield(this.fmRds) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.broadcastradio.V2_0.AmFmRegionConfig> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.broadcastradio.V2_0.AmFmRegionConfig> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.broadcastradio.V2_0.AmFmRegionConfig amFmRegionConfig = new android.hardware.broadcastradio.V2_0.AmFmRegionConfig();
            amFmRegionConfig.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 24);
            arrayList.add(amFmRegionConfig);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        long j2 = j + 0;
        int int32 = hwBlob.getInt32(8 + j2);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, hwBlob.handle(), j2 + 0, true);
        this.ranges.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.broadcastradio.V2_0.AmFmBandRange amFmBandRange = new android.hardware.broadcastradio.V2_0.AmFmBandRange();
            amFmBandRange.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 16);
            this.ranges.add(amFmBandRange);
        }
        this.fmDeemphasis = hwBlob.getInt8(j + 16);
        this.fmRds = hwBlob.getInt8(j + 17);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(24);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.broadcastradio.V2_0.AmFmRegionConfig> arrayList) {
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
        int size = this.ranges.size();
        long j2 = j + 0;
        hwBlob.putInt32(8 + j2, size);
        hwBlob.putBool(12 + j2, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 16);
        for (int i = 0; i < size; i++) {
            this.ranges.get(i).writeEmbeddedToBlob(hwBlob2, i * 16);
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
        hwBlob.putInt8(16 + j, this.fmDeemphasis);
        hwBlob.putInt8(j + 17, this.fmRds);
    }
}
