package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class SendSmsResult {
    public int messageRef = 0;
    public java.lang.String ackPDU = new java.lang.String();
    public int errorCode = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.SendSmsResult.class) {
            return false;
        }
        android.hardware.radio.V1_0.SendSmsResult sendSmsResult = (android.hardware.radio.V1_0.SendSmsResult) obj;
        if (this.messageRef == sendSmsResult.messageRef && android.os.HidlSupport.deepEquals(this.ackPDU, sendSmsResult.ackPDU) && this.errorCode == sendSmsResult.errorCode) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.messageRef))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.ackPDU)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.errorCode))));
    }

    public final java.lang.String toString() {
        return "{.messageRef = " + this.messageRef + ", .ackPDU = " + this.ackPDU + ", .errorCode = " + this.errorCode + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.SendSmsResult> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.SendSmsResult> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.SendSmsResult sendSmsResult = new android.hardware.radio.V1_0.SendSmsResult();
            sendSmsResult.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 32);
            arrayList.add(sendSmsResult);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.messageRef = hwBlob.getInt32(j + 0);
        long j2 = j + 8;
        this.ackPDU = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.ackPDU.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        this.errorCode = hwBlob.getInt32(j + 24);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(32);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.SendSmsResult> arrayList) {
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
        hwBlob.putInt32(0 + j, this.messageRef);
        hwBlob.putString(8 + j, this.ackPDU);
        hwBlob.putInt32(j + 24, this.errorCode);
    }
}
