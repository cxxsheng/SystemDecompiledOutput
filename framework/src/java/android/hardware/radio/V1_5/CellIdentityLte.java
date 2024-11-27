package android.hardware.radio.V1_5;

/* loaded from: classes2.dex */
public final class CellIdentityLte {
    public android.hardware.radio.V1_2.CellIdentityLte base = new android.hardware.radio.V1_2.CellIdentityLte();
    public java.util.ArrayList<java.lang.String> additionalPlmns = new java.util.ArrayList<>();
    public android.hardware.radio.V1_5.OptionalCsgInfo optionalCsgInfo = new android.hardware.radio.V1_5.OptionalCsgInfo();
    public java.util.ArrayList<java.lang.Integer> bands = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_5.CellIdentityLte.class) {
            return false;
        }
        android.hardware.radio.V1_5.CellIdentityLte cellIdentityLte = (android.hardware.radio.V1_5.CellIdentityLte) obj;
        if (android.os.HidlSupport.deepEquals(this.base, cellIdentityLte.base) && android.os.HidlSupport.deepEquals(this.additionalPlmns, cellIdentityLte.additionalPlmns) && android.os.HidlSupport.deepEquals(this.optionalCsgInfo, cellIdentityLte.optionalCsgInfo) && android.os.HidlSupport.deepEquals(this.bands, cellIdentityLte.bands)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.base)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.additionalPlmns)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.optionalCsgInfo)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.bands)));
    }

    public final java.lang.String toString() {
        return "{.base = " + this.base + ", .additionalPlmns = " + this.additionalPlmns + ", .optionalCsgInfo = " + this.optionalCsgInfo + ", .bands = " + this.bands + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(160L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_5.CellIdentityLte> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_5.CellIdentityLte> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 160, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_5.CellIdentityLte cellIdentityLte = new android.hardware.radio.V1_5.CellIdentityLte();
            cellIdentityLte.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 160);
            arrayList.add(cellIdentityLte);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, j + 0);
        long j2 = j + 88;
        int int32 = hwBlob.getInt32(j2 + 8);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, hwBlob.handle(), j2 + 0, true);
        this.additionalPlmns.clear();
        int i = 0;
        while (i < int32) {
            new java.lang.String();
            java.lang.String string = readEmbeddedBuffer.getString(i * 16);
            hwParcel.readEmbeddedBuffer(string.getBytes().length + 1, readEmbeddedBuffer.handle(), r1 + 0, false);
            this.additionalPlmns.add(string);
            i++;
            readEmbeddedBuffer = readEmbeddedBuffer;
        }
        this.optionalCsgInfo.readEmbeddedFromParcel(hwParcel, hwBlob, j + 104);
        long j3 = j + 144;
        int int322 = hwBlob.getInt32(j3 + 8);
        android.os.HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 4, hwBlob.handle(), 0 + j3, true);
        this.bands.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            this.bands.add(java.lang.Integer.valueOf(readEmbeddedBuffer2.getInt32(i2 * 4)));
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(160);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_5.CellIdentityLte> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 160);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 160);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        this.base.writeEmbeddedToBlob(hwBlob, j + 0);
        int size = this.additionalPlmns.size();
        long j2 = j + 88;
        hwBlob.putInt32(j2 + 8, size);
        hwBlob.putBool(j2 + 12, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 16);
        for (int i = 0; i < size; i++) {
            hwBlob2.putString(i * 16, this.additionalPlmns.get(i));
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
        this.optionalCsgInfo.writeEmbeddedToBlob(hwBlob, j + 104);
        int size2 = this.bands.size();
        long j3 = j + 144;
        hwBlob.putInt32(8 + j3, size2);
        hwBlob.putBool(12 + j3, false);
        android.os.HwBlob hwBlob3 = new android.os.HwBlob(size2 * 4);
        for (int i2 = 0; i2 < size2; i2++) {
            hwBlob3.putInt32(i2 * 4, this.bands.get(i2).intValue());
        }
        hwBlob.putBlob(j3 + 0, hwBlob3);
    }
}
