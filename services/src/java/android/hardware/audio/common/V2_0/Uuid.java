package android.hardware.audio.common.V2_0;

/* loaded from: classes.dex */
public final class Uuid {
    public int timeLow = 0;
    public short timeMid = 0;
    public short versionAndTimeHigh = 0;
    public short variantAndClockSeqHigh = 0;
    public byte[] node = new byte[6];

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.audio.common.V2_0.Uuid.class) {
            return false;
        }
        android.hardware.audio.common.V2_0.Uuid uuid = (android.hardware.audio.common.V2_0.Uuid) obj;
        if (this.timeLow == uuid.timeLow && this.timeMid == uuid.timeMid && this.versionAndTimeHigh == uuid.versionAndTimeHigh && this.variantAndClockSeqHigh == uuid.variantAndClockSeqHigh && android.os.HidlSupport.deepEquals(this.node, uuid.node)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.timeLow))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Short.valueOf(this.timeMid))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Short.valueOf(this.versionAndTimeHigh))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Short.valueOf(this.variantAndClockSeqHigh))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.node)));
    }

    public final java.lang.String toString() {
        return "{.timeLow = " + this.timeLow + ", .timeMid = " + ((int) this.timeMid) + ", .versionAndTimeHigh = " + ((int) this.versionAndTimeHigh) + ", .variantAndClockSeqHigh = " + ((int) this.variantAndClockSeqHigh) + ", .node = " + java.util.Arrays.toString(this.node) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(16L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.audio.common.V2_0.Uuid> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.audio.common.V2_0.Uuid> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.audio.common.V2_0.Uuid uuid = new android.hardware.audio.common.V2_0.Uuid();
            uuid.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 16);
            arrayList.add(uuid);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.timeLow = hwBlob.getInt32(0 + j);
        this.timeMid = hwBlob.getInt16(4 + j);
        this.versionAndTimeHigh = hwBlob.getInt16(6 + j);
        this.variantAndClockSeqHigh = hwBlob.getInt16(8 + j);
        hwBlob.copyToInt8Array(j + 10, this.node, 6);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.audio.common.V2_0.Uuid> arrayList) {
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
        hwBlob.putInt32(0 + j, this.timeLow);
        hwBlob.putInt16(4 + j, this.timeMid);
        hwBlob.putInt16(6 + j, this.versionAndTimeHigh);
        hwBlob.putInt16(8 + j, this.variantAndClockSeqHigh);
        long j2 = j + 10;
        byte[] bArr = this.node;
        if (bArr == null || bArr.length != 6) {
            throw new java.lang.IllegalArgumentException("Array element is not of the expected length");
        }
        hwBlob.putInt8Array(j2, bArr);
    }
}
