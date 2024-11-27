package android.hardware.radio.V1_4;

/* loaded from: classes2.dex */
public final class SignalStrength {
    public android.hardware.radio.V1_0.GsmSignalStrength gsm = new android.hardware.radio.V1_0.GsmSignalStrength();
    public android.hardware.radio.V1_0.CdmaSignalStrength cdma = new android.hardware.radio.V1_0.CdmaSignalStrength();
    public android.hardware.radio.V1_0.EvdoSignalStrength evdo = new android.hardware.radio.V1_0.EvdoSignalStrength();
    public android.hardware.radio.V1_0.LteSignalStrength lte = new android.hardware.radio.V1_0.LteSignalStrength();
    public android.hardware.radio.V1_2.TdscdmaSignalStrength tdscdma = new android.hardware.radio.V1_2.TdscdmaSignalStrength();
    public android.hardware.radio.V1_2.WcdmaSignalStrength wcdma = new android.hardware.radio.V1_2.WcdmaSignalStrength();
    public android.hardware.radio.V1_4.NrSignalStrength nr = new android.hardware.radio.V1_4.NrSignalStrength();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_4.SignalStrength.class) {
            return false;
        }
        android.hardware.radio.V1_4.SignalStrength signalStrength = (android.hardware.radio.V1_4.SignalStrength) obj;
        if (android.os.HidlSupport.deepEquals(this.gsm, signalStrength.gsm) && android.os.HidlSupport.deepEquals(this.cdma, signalStrength.cdma) && android.os.HidlSupport.deepEquals(this.evdo, signalStrength.evdo) && android.os.HidlSupport.deepEquals(this.lte, signalStrength.lte) && android.os.HidlSupport.deepEquals(this.tdscdma, signalStrength.tdscdma) && android.os.HidlSupport.deepEquals(this.wcdma, signalStrength.wcdma) && android.os.HidlSupport.deepEquals(this.nr, signalStrength.nr)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.gsm)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.cdma)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.evdo)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.lte)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.tdscdma)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.wcdma)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.nr)));
    }

    public final java.lang.String toString() {
        return "{.gsm = " + this.gsm + ", .cdma = " + this.cdma + ", .evdo = " + this.evdo + ", .lte = " + this.lte + ", .tdscdma = " + this.tdscdma + ", .wcdma = " + this.wcdma + ", .nr = " + this.nr + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(108L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_4.SignalStrength> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_4.SignalStrength> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 108, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_4.SignalStrength signalStrength = new android.hardware.radio.V1_4.SignalStrength();
            signalStrength.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 108);
            arrayList.add(signalStrength);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.gsm.readEmbeddedFromParcel(hwParcel, hwBlob, 0 + j);
        this.cdma.readEmbeddedFromParcel(hwParcel, hwBlob, 12 + j);
        this.evdo.readEmbeddedFromParcel(hwParcel, hwBlob, 20 + j);
        this.lte.readEmbeddedFromParcel(hwParcel, hwBlob, 32 + j);
        this.tdscdma.readEmbeddedFromParcel(hwParcel, hwBlob, 56 + j);
        this.wcdma.readEmbeddedFromParcel(hwParcel, hwBlob, 68 + j);
        this.nr.readEmbeddedFromParcel(hwParcel, hwBlob, j + 84);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(108);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_4.SignalStrength> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 108);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 108);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        this.gsm.writeEmbeddedToBlob(hwBlob, 0 + j);
        this.cdma.writeEmbeddedToBlob(hwBlob, 12 + j);
        this.evdo.writeEmbeddedToBlob(hwBlob, 20 + j);
        this.lte.writeEmbeddedToBlob(hwBlob, 32 + j);
        this.tdscdma.writeEmbeddedToBlob(hwBlob, 56 + j);
        this.wcdma.writeEmbeddedToBlob(hwBlob, 68 + j);
        this.nr.writeEmbeddedToBlob(hwBlob, j + 84);
    }
}
