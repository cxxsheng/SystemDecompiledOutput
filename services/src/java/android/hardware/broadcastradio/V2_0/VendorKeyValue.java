package android.hardware.broadcastradio.V2_0;

/* loaded from: classes.dex */
public final class VendorKeyValue {
    public java.lang.String key = new java.lang.String();
    public java.lang.String value = new java.lang.String();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.broadcastradio.V2_0.VendorKeyValue.class) {
            return false;
        }
        android.hardware.broadcastradio.V2_0.VendorKeyValue vendorKeyValue = (android.hardware.broadcastradio.V2_0.VendorKeyValue) obj;
        if (android.os.HidlSupport.deepEquals(this.key, vendorKeyValue.key) && android.os.HidlSupport.deepEquals(this.value, vendorKeyValue.value)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.key)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.value)));
    }

    public final java.lang.String toString() {
        return "{.key = " + this.key + ", .value = " + this.value + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.broadcastradio.V2_0.VendorKeyValue> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.broadcastradio.V2_0.VendorKeyValue> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.broadcastradio.V2_0.VendorKeyValue vendorKeyValue = new android.hardware.broadcastradio.V2_0.VendorKeyValue();
            vendorKeyValue.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 32);
            arrayList.add(vendorKeyValue);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        long j2 = j + 0;
        this.key = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.key.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        long j3 = j + 16;
        this.value = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(this.value.getBytes().length + 1, hwBlob.handle(), j3 + 0, false);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(32);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.broadcastradio.V2_0.VendorKeyValue> arrayList) {
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
        hwBlob.putString(0 + j, this.key);
        hwBlob.putString(j + 16, this.value);
    }
}
