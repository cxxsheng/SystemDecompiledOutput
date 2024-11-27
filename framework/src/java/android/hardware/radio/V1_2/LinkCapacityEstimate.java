package android.hardware.radio.V1_2;

/* loaded from: classes2.dex */
public final class LinkCapacityEstimate {
    public int downlinkCapacityKbps = 0;
    public int uplinkCapacityKbps = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_2.LinkCapacityEstimate.class) {
            return false;
        }
        android.hardware.radio.V1_2.LinkCapacityEstimate linkCapacityEstimate = (android.hardware.radio.V1_2.LinkCapacityEstimate) obj;
        if (this.downlinkCapacityKbps == linkCapacityEstimate.downlinkCapacityKbps && this.uplinkCapacityKbps == linkCapacityEstimate.uplinkCapacityKbps) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.downlinkCapacityKbps))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.uplinkCapacityKbps))));
    }

    public final java.lang.String toString() {
        return "{.downlinkCapacityKbps = " + this.downlinkCapacityKbps + ", .uplinkCapacityKbps = " + this.uplinkCapacityKbps + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(8L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_2.LinkCapacityEstimate> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_2.LinkCapacityEstimate> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 8, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_2.LinkCapacityEstimate linkCapacityEstimate = new android.hardware.radio.V1_2.LinkCapacityEstimate();
            linkCapacityEstimate.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 8);
            arrayList.add(linkCapacityEstimate);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.downlinkCapacityKbps = hwBlob.getInt32(0 + j);
        this.uplinkCapacityKbps = hwBlob.getInt32(j + 4);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(8);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_2.LinkCapacityEstimate> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 8);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 8);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(0 + j, this.downlinkCapacityKbps);
        hwBlob.putInt32(j + 4, this.uplinkCapacityKbps);
    }
}
