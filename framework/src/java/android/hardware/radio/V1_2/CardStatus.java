package android.hardware.radio.V1_2;

/* loaded from: classes2.dex */
public final class CardStatus {
    public android.hardware.radio.V1_0.CardStatus base = new android.hardware.radio.V1_0.CardStatus();
    public int physicalSlotId = 0;
    public java.lang.String atr = new java.lang.String();
    public java.lang.String iccid = new java.lang.String();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_2.CardStatus.class) {
            return false;
        }
        android.hardware.radio.V1_2.CardStatus cardStatus = (android.hardware.radio.V1_2.CardStatus) obj;
        if (android.os.HidlSupport.deepEquals(this.base, cardStatus.base) && this.physicalSlotId == cardStatus.physicalSlotId && android.os.HidlSupport.deepEquals(this.atr, cardStatus.atr) && android.os.HidlSupport.deepEquals(this.iccid, cardStatus.iccid)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.base)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.physicalSlotId))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.atr)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.iccid)));
    }

    public final java.lang.String toString() {
        return "{.base = " + this.base + ", .physicalSlotId = " + this.physicalSlotId + ", .atr = " + this.atr + ", .iccid = " + this.iccid + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(80L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_2.CardStatus> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_2.CardStatus> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 80, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_2.CardStatus cardStatus = new android.hardware.radio.V1_2.CardStatus();
            cardStatus.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 80);
            arrayList.add(cardStatus);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, j + 0);
        this.physicalSlotId = hwBlob.getInt32(j + 40);
        long j2 = j + 48;
        this.atr = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.atr.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        long j3 = j + 64;
        this.iccid = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(this.iccid.getBytes().length + 1, hwBlob.handle(), j3 + 0, false);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(80);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_2.CardStatus> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 80);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 80);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        this.base.writeEmbeddedToBlob(hwBlob, 0 + j);
        hwBlob.putInt32(40 + j, this.physicalSlotId);
        hwBlob.putString(48 + j, this.atr);
        hwBlob.putString(j + 64, this.iccid);
    }
}
