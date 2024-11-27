package android.hardware.broadcastradio.V2_0;

/* loaded from: classes.dex */
public final class AmFmBandRange {
    public int lowerBound = 0;
    public int upperBound = 0;
    public int spacing = 0;
    public int scanSpacing = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.broadcastradio.V2_0.AmFmBandRange.class) {
            return false;
        }
        android.hardware.broadcastradio.V2_0.AmFmBandRange amFmBandRange = (android.hardware.broadcastradio.V2_0.AmFmBandRange) obj;
        if (this.lowerBound == amFmBandRange.lowerBound && this.upperBound == amFmBandRange.upperBound && this.spacing == amFmBandRange.spacing && this.scanSpacing == amFmBandRange.scanSpacing) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.lowerBound))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.upperBound))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.spacing))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.scanSpacing))));
    }

    public final java.lang.String toString() {
        return "{.lowerBound = " + this.lowerBound + ", .upperBound = " + this.upperBound + ", .spacing = " + this.spacing + ", .scanSpacing = " + this.scanSpacing + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(16L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.broadcastradio.V2_0.AmFmBandRange> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.broadcastradio.V2_0.AmFmBandRange> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.broadcastradio.V2_0.AmFmBandRange amFmBandRange = new android.hardware.broadcastradio.V2_0.AmFmBandRange();
            amFmBandRange.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 16);
            arrayList.add(amFmBandRange);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.lowerBound = hwBlob.getInt32(0 + j);
        this.upperBound = hwBlob.getInt32(4 + j);
        this.spacing = hwBlob.getInt32(8 + j);
        this.scanSpacing = hwBlob.getInt32(j + 12);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.broadcastradio.V2_0.AmFmBandRange> arrayList) {
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
        hwBlob.putInt32(0 + j, this.lowerBound);
        hwBlob.putInt32(4 + j, this.upperBound);
        hwBlob.putInt32(8 + j, this.spacing);
        hwBlob.putInt32(j + 12, this.scanSpacing);
    }
}
