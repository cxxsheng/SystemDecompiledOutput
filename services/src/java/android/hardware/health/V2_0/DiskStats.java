package android.hardware.health.V2_0;

/* loaded from: classes.dex */
public final class DiskStats {
    public long reads = 0;
    public long readMerges = 0;
    public long readSectors = 0;
    public long readTicks = 0;
    public long writes = 0;
    public long writeMerges = 0;
    public long writeSectors = 0;
    public long writeTicks = 0;
    public long ioInFlight = 0;
    public long ioTicks = 0;
    public long ioInQueue = 0;
    public android.hardware.health.V2_0.StorageAttribute attr = new android.hardware.health.V2_0.StorageAttribute();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.health.V2_0.DiskStats.class) {
            return false;
        }
        android.hardware.health.V2_0.DiskStats diskStats = (android.hardware.health.V2_0.DiskStats) obj;
        if (this.reads == diskStats.reads && this.readMerges == diskStats.readMerges && this.readSectors == diskStats.readSectors && this.readTicks == diskStats.readTicks && this.writes == diskStats.writes && this.writeMerges == diskStats.writeMerges && this.writeSectors == diskStats.writeSectors && this.writeTicks == diskStats.writeTicks && this.ioInFlight == diskStats.ioInFlight && this.ioTicks == diskStats.ioTicks && this.ioInQueue == diskStats.ioInQueue && android.os.HidlSupport.deepEquals(this.attr, diskStats.attr)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.reads))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.readMerges))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.readSectors))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.readTicks))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.writes))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.writeMerges))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.writeSectors))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.writeTicks))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.ioInFlight))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.ioTicks))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.ioInQueue))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.attr)));
    }

    public final java.lang.String toString() {
        return "{.reads = " + this.reads + ", .readMerges = " + this.readMerges + ", .readSectors = " + this.readSectors + ", .readTicks = " + this.readTicks + ", .writes = " + this.writes + ", .writeMerges = " + this.writeMerges + ", .writeSectors = " + this.writeSectors + ", .writeTicks = " + this.writeTicks + ", .ioInFlight = " + this.ioInFlight + ", .ioTicks = " + this.ioTicks + ", .ioInQueue = " + this.ioInQueue + ", .attr = " + this.attr + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(112L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.health.V2_0.DiskStats> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.health.V2_0.DiskStats> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 112, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.health.V2_0.DiskStats diskStats = new android.hardware.health.V2_0.DiskStats();
            diskStats.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 112);
            arrayList.add(diskStats);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.reads = hwBlob.getInt64(0 + j);
        this.readMerges = hwBlob.getInt64(8 + j);
        this.readSectors = hwBlob.getInt64(16 + j);
        this.readTicks = hwBlob.getInt64(24 + j);
        this.writes = hwBlob.getInt64(32 + j);
        this.writeMerges = hwBlob.getInt64(40 + j);
        this.writeSectors = hwBlob.getInt64(48 + j);
        this.writeTicks = hwBlob.getInt64(56 + j);
        this.ioInFlight = hwBlob.getInt64(64 + j);
        this.ioTicks = hwBlob.getInt64(72 + j);
        this.ioInQueue = hwBlob.getInt64(80 + j);
        this.attr.readEmbeddedFromParcel(hwParcel, hwBlob, j + 88);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(112);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.health.V2_0.DiskStats> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 112);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 112);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt64(0 + j, this.reads);
        hwBlob.putInt64(8 + j, this.readMerges);
        hwBlob.putInt64(16 + j, this.readSectors);
        hwBlob.putInt64(24 + j, this.readTicks);
        hwBlob.putInt64(32 + j, this.writes);
        hwBlob.putInt64(40 + j, this.writeMerges);
        hwBlob.putInt64(48 + j, this.writeSectors);
        hwBlob.putInt64(56 + j, this.writeTicks);
        hwBlob.putInt64(64 + j, this.ioInFlight);
        hwBlob.putInt64(72 + j, this.ioTicks);
        hwBlob.putInt64(80 + j, this.ioInQueue);
        this.attr.writeEmbeddedToBlob(hwBlob, j + 88);
    }
}
