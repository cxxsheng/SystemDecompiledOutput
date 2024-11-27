package android.hardware.radio.V1_6;

/* loaded from: classes2.dex */
public final class LinkCapacityEstimate {
    public int downlinkCapacityKbps = 0;
    public int uplinkCapacityKbps = 0;
    public int secondaryDownlinkCapacityKbps = 0;
    public int secondaryUplinkCapacityKbps = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_6.LinkCapacityEstimate.class) {
            return false;
        }
        android.hardware.radio.V1_6.LinkCapacityEstimate linkCapacityEstimate = (android.hardware.radio.V1_6.LinkCapacityEstimate) obj;
        if (this.downlinkCapacityKbps == linkCapacityEstimate.downlinkCapacityKbps && this.uplinkCapacityKbps == linkCapacityEstimate.uplinkCapacityKbps && this.secondaryDownlinkCapacityKbps == linkCapacityEstimate.secondaryDownlinkCapacityKbps && this.secondaryUplinkCapacityKbps == linkCapacityEstimate.secondaryUplinkCapacityKbps) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.downlinkCapacityKbps))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.uplinkCapacityKbps))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.secondaryDownlinkCapacityKbps))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.secondaryUplinkCapacityKbps))));
    }

    public final java.lang.String toString() {
        return "{.downlinkCapacityKbps = " + this.downlinkCapacityKbps + ", .uplinkCapacityKbps = " + this.uplinkCapacityKbps + ", .secondaryDownlinkCapacityKbps = " + this.secondaryDownlinkCapacityKbps + ", .secondaryUplinkCapacityKbps = " + this.secondaryUplinkCapacityKbps + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(16L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_6.LinkCapacityEstimate> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_6.LinkCapacityEstimate> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_6.LinkCapacityEstimate linkCapacityEstimate = new android.hardware.radio.V1_6.LinkCapacityEstimate();
            linkCapacityEstimate.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 16);
            arrayList.add(linkCapacityEstimate);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.downlinkCapacityKbps = hwBlob.getInt32(0 + j);
        this.uplinkCapacityKbps = hwBlob.getInt32(4 + j);
        this.secondaryDownlinkCapacityKbps = hwBlob.getInt32(8 + j);
        this.secondaryUplinkCapacityKbps = hwBlob.getInt32(j + 12);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_6.LinkCapacityEstimate> arrayList) {
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
        hwBlob.putInt32(0 + j, this.downlinkCapacityKbps);
        hwBlob.putInt32(4 + j, this.uplinkCapacityKbps);
        hwBlob.putInt32(8 + j, this.secondaryDownlinkCapacityKbps);
        hwBlob.putInt32(j + 12, this.secondaryUplinkCapacityKbps);
    }
}
