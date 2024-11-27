package android.hardware.broadcastradio.V2_0;

/* loaded from: classes.dex */
public final class Properties {
    public java.lang.String maker = new java.lang.String();
    public java.lang.String product = new java.lang.String();
    public java.lang.String version = new java.lang.String();
    public java.lang.String serial = new java.lang.String();
    public java.util.ArrayList<java.lang.Integer> supportedIdentifierTypes = new java.util.ArrayList<>();
    public java.util.ArrayList<android.hardware.broadcastradio.V2_0.VendorKeyValue> vendorInfo = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.broadcastradio.V2_0.Properties.class) {
            return false;
        }
        android.hardware.broadcastradio.V2_0.Properties properties = (android.hardware.broadcastradio.V2_0.Properties) obj;
        if (android.os.HidlSupport.deepEquals(this.maker, properties.maker) && android.os.HidlSupport.deepEquals(this.product, properties.product) && android.os.HidlSupport.deepEquals(this.version, properties.version) && android.os.HidlSupport.deepEquals(this.serial, properties.serial) && android.os.HidlSupport.deepEquals(this.supportedIdentifierTypes, properties.supportedIdentifierTypes) && android.os.HidlSupport.deepEquals(this.vendorInfo, properties.vendorInfo)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.maker)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.product)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.version)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.serial)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.supportedIdentifierTypes)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.vendorInfo)));
    }

    public final java.lang.String toString() {
        return "{.maker = " + this.maker + ", .product = " + this.product + ", .version = " + this.version + ", .serial = " + this.serial + ", .supportedIdentifierTypes = " + this.supportedIdentifierTypes + ", .vendorInfo = " + this.vendorInfo + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(96L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.broadcastradio.V2_0.Properties> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.broadcastradio.V2_0.Properties> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 96, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.broadcastradio.V2_0.Properties properties = new android.hardware.broadcastradio.V2_0.Properties();
            properties.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 96);
            arrayList.add(properties);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        long j2 = j + 0;
        this.maker = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.maker.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        long j3 = j + 16;
        this.product = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(this.product.getBytes().length + 1, hwBlob.handle(), j3 + 0, false);
        long j4 = j + 32;
        this.version = hwBlob.getString(j4);
        hwParcel.readEmbeddedBuffer(this.version.getBytes().length + 1, hwBlob.handle(), j4 + 0, false);
        long j5 = j + 48;
        this.serial = hwBlob.getString(j5);
        hwParcel.readEmbeddedBuffer(this.serial.getBytes().length + 1, hwBlob.handle(), j5 + 0, false);
        long j6 = j + 64;
        int int32 = hwBlob.getInt32(j6 + 8);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 4, hwBlob.handle(), j6 + 0, true);
        this.supportedIdentifierTypes.clear();
        for (int i = 0; i < int32; i++) {
            this.supportedIdentifierTypes.add(java.lang.Integer.valueOf(readEmbeddedBuffer.getInt32(i * 4)));
        }
        long j7 = j + 80;
        int int322 = hwBlob.getInt32(8 + j7);
        android.os.HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 32, hwBlob.handle(), j7 + 0, true);
        this.vendorInfo.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            android.hardware.broadcastradio.V2_0.VendorKeyValue vendorKeyValue = new android.hardware.broadcastradio.V2_0.VendorKeyValue();
            vendorKeyValue.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer2, i2 * 32);
            this.vendorInfo.add(vendorKeyValue);
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(96);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.broadcastradio.V2_0.Properties> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 96);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 96);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putString(j + 0, this.maker);
        hwBlob.putString(j + 16, this.product);
        hwBlob.putString(j + 32, this.version);
        hwBlob.putString(j + 48, this.serial);
        int size = this.supportedIdentifierTypes.size();
        long j2 = j + 64;
        hwBlob.putInt32(j2 + 8, size);
        hwBlob.putBool(j2 + 12, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 4);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt32(i * 4, this.supportedIdentifierTypes.get(i).intValue());
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
        int size2 = this.vendorInfo.size();
        long j3 = j + 80;
        hwBlob.putInt32(8 + j3, size2);
        hwBlob.putBool(12 + j3, false);
        android.os.HwBlob hwBlob3 = new android.os.HwBlob(size2 * 32);
        for (int i2 = 0; i2 < size2; i2++) {
            this.vendorInfo.get(i2).writeEmbeddedToBlob(hwBlob3, i2 * 32);
        }
        hwBlob.putBlob(j3 + 0, hwBlob3);
    }
}
