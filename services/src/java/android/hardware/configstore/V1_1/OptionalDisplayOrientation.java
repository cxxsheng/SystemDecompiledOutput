package android.hardware.configstore.V1_1;

/* loaded from: classes.dex */
public final class OptionalDisplayOrientation {
    public boolean specified = false;
    public byte value = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.configstore.V1_1.OptionalDisplayOrientation.class) {
            return false;
        }
        android.hardware.configstore.V1_1.OptionalDisplayOrientation optionalDisplayOrientation = (android.hardware.configstore.V1_1.OptionalDisplayOrientation) obj;
        if (this.specified == optionalDisplayOrientation.specified && this.value == optionalDisplayOrientation.value) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.specified))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.value))));
    }

    public final java.lang.String toString() {
        return "{.specified = " + this.specified + ", .value = " + android.hardware.configstore.V1_1.DisplayOrientation.toString(this.value) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(2L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.configstore.V1_1.OptionalDisplayOrientation> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.configstore.V1_1.OptionalDisplayOrientation> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 2, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.configstore.V1_1.OptionalDisplayOrientation optionalDisplayOrientation = new android.hardware.configstore.V1_1.OptionalDisplayOrientation();
            optionalDisplayOrientation.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 2);
            arrayList.add(optionalDisplayOrientation);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.specified = hwBlob.getBool(0 + j);
        this.value = hwBlob.getInt8(j + 1);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(2);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.configstore.V1_1.OptionalDisplayOrientation> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 2);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 2);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putBool(0 + j, this.specified);
        hwBlob.putInt8(j + 1, this.value);
    }
}
