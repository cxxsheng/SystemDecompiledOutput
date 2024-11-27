package android.hardware.radio.V1_5;

/* loaded from: classes2.dex */
public final class CellIdentityWcdma {
    public android.hardware.radio.V1_2.CellIdentityWcdma base = new android.hardware.radio.V1_2.CellIdentityWcdma();
    public java.util.ArrayList<java.lang.String> additionalPlmns = new java.util.ArrayList<>();
    public android.hardware.radio.V1_5.OptionalCsgInfo optionalCsgInfo = new android.hardware.radio.V1_5.OptionalCsgInfo();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_5.CellIdentityWcdma.class) {
            return false;
        }
        android.hardware.radio.V1_5.CellIdentityWcdma cellIdentityWcdma = (android.hardware.radio.V1_5.CellIdentityWcdma) obj;
        if (android.os.HidlSupport.deepEquals(this.base, cellIdentityWcdma.base) && android.os.HidlSupport.deepEquals(this.additionalPlmns, cellIdentityWcdma.additionalPlmns) && android.os.HidlSupport.deepEquals(this.optionalCsgInfo, cellIdentityWcdma.optionalCsgInfo)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.base)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.additionalPlmns)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.optionalCsgInfo)));
    }

    public final java.lang.String toString() {
        return "{.base = " + this.base + ", .additionalPlmns = " + this.additionalPlmns + ", .optionalCsgInfo = " + this.optionalCsgInfo + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(136L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_5.CellIdentityWcdma> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_5.CellIdentityWcdma> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 136, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_5.CellIdentityWcdma cellIdentityWcdma = new android.hardware.radio.V1_5.CellIdentityWcdma();
            cellIdentityWcdma.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 136);
            arrayList.add(cellIdentityWcdma);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, j + 0);
        long j2 = j + 80;
        int int32 = hwBlob.getInt32(8 + j2);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, hwBlob.handle(), j2 + 0, true);
        this.additionalPlmns.clear();
        for (int i = 0; i < int32; i++) {
            new java.lang.String();
            java.lang.String string = readEmbeddedBuffer.getString(i * 16);
            hwParcel.readEmbeddedBuffer(string.getBytes().length + 1, readEmbeddedBuffer.handle(), r1 + 0, false);
            this.additionalPlmns.add(string);
        }
        this.optionalCsgInfo.readEmbeddedFromParcel(hwParcel, hwBlob, j + 96);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(136);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_5.CellIdentityWcdma> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 136);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 136);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        this.base.writeEmbeddedToBlob(hwBlob, j + 0);
        int size = this.additionalPlmns.size();
        long j2 = 80 + j;
        hwBlob.putInt32(8 + j2, size);
        hwBlob.putBool(12 + j2, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 16);
        for (int i = 0; i < size; i++) {
            hwBlob2.putString(i * 16, this.additionalPlmns.get(i));
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
        this.optionalCsgInfo.writeEmbeddedToBlob(hwBlob, j + 96);
    }
}
