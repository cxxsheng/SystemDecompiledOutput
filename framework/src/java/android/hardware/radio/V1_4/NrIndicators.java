package android.hardware.radio.V1_4;

/* loaded from: classes2.dex */
public final class NrIndicators {
    public boolean isEndcAvailable = false;
    public boolean isDcNrRestricted = false;
    public boolean isNrAvailable = false;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_4.NrIndicators.class) {
            return false;
        }
        android.hardware.radio.V1_4.NrIndicators nrIndicators = (android.hardware.radio.V1_4.NrIndicators) obj;
        if (this.isEndcAvailable == nrIndicators.isEndcAvailable && this.isDcNrRestricted == nrIndicators.isDcNrRestricted && this.isNrAvailable == nrIndicators.isNrAvailable) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.isEndcAvailable))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.isDcNrRestricted))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.isNrAvailable))));
    }

    public final java.lang.String toString() {
        return "{.isEndcAvailable = " + this.isEndcAvailable + ", .isDcNrRestricted = " + this.isDcNrRestricted + ", .isNrAvailable = " + this.isNrAvailable + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(3L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_4.NrIndicators> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_4.NrIndicators> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 3, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_4.NrIndicators nrIndicators = new android.hardware.radio.V1_4.NrIndicators();
            nrIndicators.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 3);
            arrayList.add(nrIndicators);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.isEndcAvailable = hwBlob.getBool(0 + j);
        this.isDcNrRestricted = hwBlob.getBool(1 + j);
        this.isNrAvailable = hwBlob.getBool(j + 2);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(3);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_4.NrIndicators> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 3);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 3);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putBool(0 + j, this.isEndcAvailable);
        hwBlob.putBool(1 + j, this.isDcNrRestricted);
        hwBlob.putBool(j + 2, this.isNrAvailable);
    }
}
