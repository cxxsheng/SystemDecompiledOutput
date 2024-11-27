package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class CdmaSmsMessage {
    public int teleserviceId = 0;
    public boolean isServicePresent = false;
    public int serviceCategory = 0;
    public android.hardware.radio.V1_0.CdmaSmsAddress address = new android.hardware.radio.V1_0.CdmaSmsAddress();
    public android.hardware.radio.V1_0.CdmaSmsSubaddress subAddress = new android.hardware.radio.V1_0.CdmaSmsSubaddress();
    public java.util.ArrayList<java.lang.Byte> bearerData = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.CdmaSmsMessage.class) {
            return false;
        }
        android.hardware.radio.V1_0.CdmaSmsMessage cdmaSmsMessage = (android.hardware.radio.V1_0.CdmaSmsMessage) obj;
        if (this.teleserviceId == cdmaSmsMessage.teleserviceId && this.isServicePresent == cdmaSmsMessage.isServicePresent && this.serviceCategory == cdmaSmsMessage.serviceCategory && android.os.HidlSupport.deepEquals(this.address, cdmaSmsMessage.address) && android.os.HidlSupport.deepEquals(this.subAddress, cdmaSmsMessage.subAddress) && android.os.HidlSupport.deepEquals(this.bearerData, cdmaSmsMessage.bearerData)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.teleserviceId))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.isServicePresent))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.serviceCategory))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.address)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.subAddress)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.bearerData)));
    }

    public final java.lang.String toString() {
        return "{.teleserviceId = " + this.teleserviceId + ", .isServicePresent = " + this.isServicePresent + ", .serviceCategory = " + this.serviceCategory + ", .address = " + this.address + ", .subAddress = " + this.subAddress + ", .bearerData = " + this.bearerData + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(88L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.CdmaSmsMessage> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.CdmaSmsMessage> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 88, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.CdmaSmsMessage cdmaSmsMessage = new android.hardware.radio.V1_0.CdmaSmsMessage();
            cdmaSmsMessage.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 88);
            arrayList.add(cdmaSmsMessage);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.teleserviceId = hwBlob.getInt32(j + 0);
        this.isServicePresent = hwBlob.getBool(j + 4);
        this.serviceCategory = hwBlob.getInt32(j + 8);
        this.address.readEmbeddedFromParcel(hwParcel, hwBlob, j + 16);
        this.subAddress.readEmbeddedFromParcel(hwParcel, hwBlob, j + 48);
        long j2 = j + 72;
        int int32 = hwBlob.getInt32(8 + j2);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 1, hwBlob.handle(), j2 + 0, true);
        this.bearerData.clear();
        for (int i = 0; i < int32; i++) {
            this.bearerData.add(java.lang.Byte.valueOf(readEmbeddedBuffer.getInt8(i * 1)));
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(88);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.CdmaSmsMessage> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 88);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 88);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(j + 0, this.teleserviceId);
        hwBlob.putBool(4 + j, this.isServicePresent);
        hwBlob.putInt32(j + 8, this.serviceCategory);
        this.address.writeEmbeddedToBlob(hwBlob, 16 + j);
        this.subAddress.writeEmbeddedToBlob(hwBlob, 48 + j);
        int size = this.bearerData.size();
        long j2 = j + 72;
        hwBlob.putInt32(8 + j2, size);
        hwBlob.putBool(12 + j2, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 1);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt8(i * 1, this.bearerData.get(i).byteValue());
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
    }
}
