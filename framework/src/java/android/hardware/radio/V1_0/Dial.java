package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class Dial {
    public java.lang.String address = new java.lang.String();
    public int clir = 0;
    public java.util.ArrayList<android.hardware.radio.V1_0.UusInfo> uusInfo = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.Dial.class) {
            return false;
        }
        android.hardware.radio.V1_0.Dial dial = (android.hardware.radio.V1_0.Dial) obj;
        if (android.os.HidlSupport.deepEquals(this.address, dial.address) && this.clir == dial.clir && android.os.HidlSupport.deepEquals(this.uusInfo, dial.uusInfo)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.address)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.clir))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.uusInfo)));
    }

    public final java.lang.String toString() {
        return "{.address = " + this.address + ", .clir = " + android.hardware.radio.V1_0.Clir.toString(this.clir) + ", .uusInfo = " + this.uusInfo + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.Dial> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.Dial> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.Dial dial = new android.hardware.radio.V1_0.Dial();
            dial.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 40);
            arrayList.add(dial);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        long j2 = j + 0;
        this.address = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.address.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        this.clir = hwBlob.getInt32(j + 16);
        long j3 = j + 24;
        int int32 = hwBlob.getInt32(8 + j3);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, hwBlob.handle(), j3 + 0, true);
        this.uusInfo.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.UusInfo uusInfo = new android.hardware.radio.V1_0.UusInfo();
            uusInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 24);
            this.uusInfo.add(uusInfo);
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(40);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.Dial> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 40);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 40);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putString(j + 0, this.address);
        hwBlob.putInt32(16 + j, this.clir);
        int size = this.uusInfo.size();
        long j2 = j + 24;
        hwBlob.putInt32(8 + j2, size);
        hwBlob.putBool(12 + j2, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 24);
        for (int i = 0; i < size; i++) {
            this.uusInfo.get(i).writeEmbeddedToBlob(hwBlob2, i * 24);
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
    }
}
