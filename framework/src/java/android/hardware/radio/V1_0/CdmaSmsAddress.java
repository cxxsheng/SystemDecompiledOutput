package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class CdmaSmsAddress {
    public int digitMode = 0;
    public int numberMode = 0;
    public int numberType = 0;
    public int numberPlan = 0;
    public java.util.ArrayList<java.lang.Byte> digits = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.CdmaSmsAddress.class) {
            return false;
        }
        android.hardware.radio.V1_0.CdmaSmsAddress cdmaSmsAddress = (android.hardware.radio.V1_0.CdmaSmsAddress) obj;
        if (this.digitMode == cdmaSmsAddress.digitMode && this.numberMode == cdmaSmsAddress.numberMode && this.numberType == cdmaSmsAddress.numberType && this.numberPlan == cdmaSmsAddress.numberPlan && android.os.HidlSupport.deepEquals(this.digits, cdmaSmsAddress.digits)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.digitMode))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.numberMode))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.numberType))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.numberPlan))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.digits)));
    }

    public final java.lang.String toString() {
        return "{.digitMode = " + android.hardware.radio.V1_0.CdmaSmsDigitMode.toString(this.digitMode) + ", .numberMode = " + android.hardware.radio.V1_0.CdmaSmsNumberMode.toString(this.numberMode) + ", .numberType = " + android.hardware.radio.V1_0.CdmaSmsNumberType.toString(this.numberType) + ", .numberPlan = " + android.hardware.radio.V1_0.CdmaSmsNumberPlan.toString(this.numberPlan) + ", .digits = " + this.digits + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.CdmaSmsAddress> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.CdmaSmsAddress> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.CdmaSmsAddress cdmaSmsAddress = new android.hardware.radio.V1_0.CdmaSmsAddress();
            cdmaSmsAddress.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 32);
            arrayList.add(cdmaSmsAddress);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.digitMode = hwBlob.getInt32(j + 0);
        this.numberMode = hwBlob.getInt32(j + 4);
        this.numberType = hwBlob.getInt32(j + 8);
        this.numberPlan = hwBlob.getInt32(j + 12);
        long j2 = j + 16;
        int int32 = hwBlob.getInt32(8 + j2);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 1, hwBlob.handle(), j2 + 0, true);
        this.digits.clear();
        for (int i = 0; i < int32; i++) {
            this.digits.add(java.lang.Byte.valueOf(readEmbeddedBuffer.getInt8(i * 1)));
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(32);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.CdmaSmsAddress> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 32);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 32);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(j + 0, this.digitMode);
        hwBlob.putInt32(4 + j, this.numberMode);
        hwBlob.putInt32(j + 8, this.numberType);
        hwBlob.putInt32(j + 12, this.numberPlan);
        int size = this.digits.size();
        long j2 = j + 16;
        hwBlob.putInt32(8 + j2, size);
        hwBlob.putBool(12 + j2, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 1);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt8(i * 1, this.digits.get(i).byteValue());
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
    }
}
