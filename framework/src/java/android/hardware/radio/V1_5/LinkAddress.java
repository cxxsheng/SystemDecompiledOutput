package android.hardware.radio.V1_5;

/* loaded from: classes2.dex */
public final class LinkAddress {
    public java.lang.String address = new java.lang.String();
    public long deprecationTime = 0;
    public long expirationTime = 0;
    public int properties;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_5.LinkAddress.class) {
            return false;
        }
        android.hardware.radio.V1_5.LinkAddress linkAddress = (android.hardware.radio.V1_5.LinkAddress) obj;
        if (android.os.HidlSupport.deepEquals(this.address, linkAddress.address) && android.os.HidlSupport.deepEquals(java.lang.Integer.valueOf(this.properties), java.lang.Integer.valueOf(linkAddress.properties)) && this.deprecationTime == linkAddress.deprecationTime && this.expirationTime == linkAddress.expirationTime) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.address)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.properties))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.deprecationTime))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.expirationTime))));
    }

    public final java.lang.String toString() {
        return "{.address = " + this.address + ", .properties = " + android.hardware.radio.V1_5.AddressProperty.dumpBitfield(this.properties) + ", .deprecationTime = " + this.deprecationTime + ", .expirationTime = " + this.expirationTime + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_5.LinkAddress> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_5.LinkAddress> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_5.LinkAddress linkAddress = new android.hardware.radio.V1_5.LinkAddress();
            linkAddress.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 40);
            arrayList.add(linkAddress);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        long j2 = j + 0;
        this.address = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.address.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        this.properties = hwBlob.getInt32(j + 16);
        this.deprecationTime = hwBlob.getInt64(j + 24);
        this.expirationTime = hwBlob.getInt64(j + 32);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(40);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_5.LinkAddress> arrayList) {
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
        hwBlob.putString(0 + j, this.address);
        hwBlob.putInt32(16 + j, this.properties);
        hwBlob.putInt64(24 + j, this.deprecationTime);
        hwBlob.putInt64(j + 32, this.expirationTime);
    }
}
