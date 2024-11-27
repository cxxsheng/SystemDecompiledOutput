package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class CdmaSmsSubaddress {
    public int subaddressType = 0;
    public boolean odd = false;
    public java.util.ArrayList<java.lang.Byte> digits = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.CdmaSmsSubaddress.class) {
            return false;
        }
        android.hardware.radio.V1_0.CdmaSmsSubaddress cdmaSmsSubaddress = (android.hardware.radio.V1_0.CdmaSmsSubaddress) obj;
        if (this.subaddressType == cdmaSmsSubaddress.subaddressType && this.odd == cdmaSmsSubaddress.odd && android.os.HidlSupport.deepEquals(this.digits, cdmaSmsSubaddress.digits)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.subaddressType))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.odd))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.digits)));
    }

    public final java.lang.String toString() {
        return "{.subaddressType = " + android.hardware.radio.V1_0.CdmaSmsSubaddressType.toString(this.subaddressType) + ", .odd = " + this.odd + ", .digits = " + this.digits + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.CdmaSmsSubaddress> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.CdmaSmsSubaddress> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.CdmaSmsSubaddress cdmaSmsSubaddress = new android.hardware.radio.V1_0.CdmaSmsSubaddress();
            cdmaSmsSubaddress.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 24);
            arrayList.add(cdmaSmsSubaddress);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.subaddressType = hwBlob.getInt32(j + 0);
        this.odd = hwBlob.getBool(j + 4);
        long j2 = j + 8;
        int int32 = hwBlob.getInt32(8 + j2);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 1, hwBlob.handle(), j2 + 0, true);
        this.digits.clear();
        for (int i = 0; i < int32; i++) {
            this.digits.add(java.lang.Byte.valueOf(readEmbeddedBuffer.getInt8(i * 1)));
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(24);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.CdmaSmsSubaddress> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 24);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 24);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(j + 0, this.subaddressType);
        hwBlob.putBool(4 + j, this.odd);
        int size = this.digits.size();
        long j2 = j + 8;
        hwBlob.putInt32(8 + j2, size);
        hwBlob.putBool(12 + j2, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 1);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt8(i * 1, this.digits.get(i).byteValue());
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
    }
}
