package android.hardware.radio.V1_3;

/* loaded from: classes2.dex */
public final class RadioResponseInfoModem {
    public int type = 0;
    public int serial = 0;
    public int error = 0;
    public boolean isEnabled = false;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_3.RadioResponseInfoModem.class) {
            return false;
        }
        android.hardware.radio.V1_3.RadioResponseInfoModem radioResponseInfoModem = (android.hardware.radio.V1_3.RadioResponseInfoModem) obj;
        if (this.type == radioResponseInfoModem.type && this.serial == radioResponseInfoModem.serial && this.error == radioResponseInfoModem.error && this.isEnabled == radioResponseInfoModem.isEnabled) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.type))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.serial))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.error))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.isEnabled))));
    }

    public final java.lang.String toString() {
        return "{.type = " + android.hardware.radio.V1_0.RadioResponseType.toString(this.type) + ", .serial = " + this.serial + ", .error = " + android.hardware.radio.V1_0.RadioError.toString(this.error) + ", .isEnabled = " + this.isEnabled + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(16L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_3.RadioResponseInfoModem> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_3.RadioResponseInfoModem> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_3.RadioResponseInfoModem radioResponseInfoModem = new android.hardware.radio.V1_3.RadioResponseInfoModem();
            radioResponseInfoModem.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 16);
            arrayList.add(radioResponseInfoModem);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.type = hwBlob.getInt32(0 + j);
        this.serial = hwBlob.getInt32(4 + j);
        this.error = hwBlob.getInt32(8 + j);
        this.isEnabled = hwBlob.getBool(j + 12);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_3.RadioResponseInfoModem> arrayList) {
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
        hwBlob.putInt32(0 + j, this.type);
        hwBlob.putInt32(4 + j, this.serial);
        hwBlob.putInt32(8 + j, this.error);
        hwBlob.putBool(j + 12, this.isEnabled);
    }
}
