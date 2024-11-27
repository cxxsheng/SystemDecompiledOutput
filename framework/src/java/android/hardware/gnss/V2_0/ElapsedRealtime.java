package android.hardware.gnss.V2_0;

/* loaded from: classes2.dex */
public final class ElapsedRealtime {
    public short flags;
    public long timestampNs = 0;
    public long timeUncertaintyNs = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.gnss.V2_0.ElapsedRealtime.class) {
            return false;
        }
        android.hardware.gnss.V2_0.ElapsedRealtime elapsedRealtime = (android.hardware.gnss.V2_0.ElapsedRealtime) obj;
        if (android.os.HidlSupport.deepEquals(java.lang.Short.valueOf(this.flags), java.lang.Short.valueOf(elapsedRealtime.flags)) && this.timestampNs == elapsedRealtime.timestampNs && this.timeUncertaintyNs == elapsedRealtime.timeUncertaintyNs) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Short.valueOf(this.flags))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.timestampNs))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.timeUncertaintyNs))));
    }

    public final java.lang.String toString() {
        return "{.flags = " + android.hardware.gnss.V2_0.ElapsedRealtimeFlags.dumpBitfield(this.flags) + ", .timestampNs = " + this.timestampNs + ", .timeUncertaintyNs = " + this.timeUncertaintyNs + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.gnss.V2_0.ElapsedRealtime> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.gnss.V2_0.ElapsedRealtime> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.gnss.V2_0.ElapsedRealtime elapsedRealtime = new android.hardware.gnss.V2_0.ElapsedRealtime();
            elapsedRealtime.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 24);
            arrayList.add(elapsedRealtime);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.flags = hwBlob.getInt16(0 + j);
        this.timestampNs = hwBlob.getInt64(8 + j);
        this.timeUncertaintyNs = hwBlob.getInt64(j + 16);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(24);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.gnss.V2_0.ElapsedRealtime> arrayList) {
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
        hwBlob.putInt16(0 + j, this.flags);
        hwBlob.putInt64(8 + j, this.timestampNs);
        hwBlob.putInt64(j + 16, this.timeUncertaintyNs);
    }
}
