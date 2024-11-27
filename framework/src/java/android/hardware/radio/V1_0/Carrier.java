package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class Carrier {
    public java.lang.String mcc = new java.lang.String();
    public java.lang.String mnc = new java.lang.String();
    public int matchType = 0;
    public java.lang.String matchData = new java.lang.String();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.Carrier.class) {
            return false;
        }
        android.hardware.radio.V1_0.Carrier carrier = (android.hardware.radio.V1_0.Carrier) obj;
        if (android.os.HidlSupport.deepEquals(this.mcc, carrier.mcc) && android.os.HidlSupport.deepEquals(this.mnc, carrier.mnc) && this.matchType == carrier.matchType && android.os.HidlSupport.deepEquals(this.matchData, carrier.matchData)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.mcc)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.mnc)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.matchType))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.matchData)));
    }

    public final java.lang.String toString() {
        return "{.mcc = " + this.mcc + ", .mnc = " + this.mnc + ", .matchType = " + android.hardware.radio.V1_0.CarrierMatchType.toString(this.matchType) + ", .matchData = " + this.matchData + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(56L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.Carrier> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.Carrier> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 56, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.Carrier carrier = new android.hardware.radio.V1_0.Carrier();
            carrier.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 56);
            arrayList.add(carrier);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        long j2 = j + 0;
        this.mcc = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.mcc.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        long j3 = j + 16;
        this.mnc = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(this.mnc.getBytes().length + 1, hwBlob.handle(), j3 + 0, false);
        this.matchType = hwBlob.getInt32(j + 32);
        long j4 = j + 40;
        this.matchData = hwBlob.getString(j4);
        hwParcel.readEmbeddedBuffer(this.matchData.getBytes().length + 1, hwBlob.handle(), j4 + 0, false);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(56);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.Carrier> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 56);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 56);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putString(0 + j, this.mcc);
        hwBlob.putString(16 + j, this.mnc);
        hwBlob.putInt32(32 + j, this.matchType);
        hwBlob.putString(j + 40, this.matchData);
    }
}
