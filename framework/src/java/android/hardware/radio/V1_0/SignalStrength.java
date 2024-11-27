package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class SignalStrength {
    public android.hardware.radio.V1_0.GsmSignalStrength gw = new android.hardware.radio.V1_0.GsmSignalStrength();
    public android.hardware.radio.V1_0.CdmaSignalStrength cdma = new android.hardware.radio.V1_0.CdmaSignalStrength();
    public android.hardware.radio.V1_0.EvdoSignalStrength evdo = new android.hardware.radio.V1_0.EvdoSignalStrength();
    public android.hardware.radio.V1_0.LteSignalStrength lte = new android.hardware.radio.V1_0.LteSignalStrength();
    public android.hardware.radio.V1_0.TdScdmaSignalStrength tdScdma = new android.hardware.radio.V1_0.TdScdmaSignalStrength();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.SignalStrength.class) {
            return false;
        }
        android.hardware.radio.V1_0.SignalStrength signalStrength = (android.hardware.radio.V1_0.SignalStrength) obj;
        if (android.os.HidlSupport.deepEquals(this.gw, signalStrength.gw) && android.os.HidlSupport.deepEquals(this.cdma, signalStrength.cdma) && android.os.HidlSupport.deepEquals(this.evdo, signalStrength.evdo) && android.os.HidlSupport.deepEquals(this.lte, signalStrength.lte) && android.os.HidlSupport.deepEquals(this.tdScdma, signalStrength.tdScdma)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.gw)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.cdma)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.evdo)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.lte)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.tdScdma)));
    }

    public final java.lang.String toString() {
        return "{.gw = " + this.gw + ", .cdma = " + this.cdma + ", .evdo = " + this.evdo + ", .lte = " + this.lte + ", .tdScdma = " + this.tdScdma + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(60L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.SignalStrength> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.SignalStrength> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 60, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.SignalStrength signalStrength = new android.hardware.radio.V1_0.SignalStrength();
            signalStrength.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 60);
            arrayList.add(signalStrength);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.gw.readEmbeddedFromParcel(hwParcel, hwBlob, 0 + j);
        this.cdma.readEmbeddedFromParcel(hwParcel, hwBlob, 12 + j);
        this.evdo.readEmbeddedFromParcel(hwParcel, hwBlob, 20 + j);
        this.lte.readEmbeddedFromParcel(hwParcel, hwBlob, 32 + j);
        this.tdScdma.readEmbeddedFromParcel(hwParcel, hwBlob, j + 56);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(60);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.SignalStrength> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 60);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 60);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        this.gw.writeEmbeddedToBlob(hwBlob, 0 + j);
        this.cdma.writeEmbeddedToBlob(hwBlob, 12 + j);
        this.evdo.writeEmbeddedToBlob(hwBlob, 20 + j);
        this.lte.writeEmbeddedToBlob(hwBlob, 32 + j);
        this.tdScdma.writeEmbeddedToBlob(hwBlob, j + 56);
    }
}
