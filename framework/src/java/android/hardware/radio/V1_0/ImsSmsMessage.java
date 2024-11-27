package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class ImsSmsMessage {
    public int tech = 0;
    public boolean retry = false;
    public int messageRef = 0;
    public java.util.ArrayList<android.hardware.radio.V1_0.CdmaSmsMessage> cdmaMessage = new java.util.ArrayList<>();
    public java.util.ArrayList<android.hardware.radio.V1_0.GsmSmsMessage> gsmMessage = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.ImsSmsMessage.class) {
            return false;
        }
        android.hardware.radio.V1_0.ImsSmsMessage imsSmsMessage = (android.hardware.radio.V1_0.ImsSmsMessage) obj;
        if (this.tech == imsSmsMessage.tech && this.retry == imsSmsMessage.retry && this.messageRef == imsSmsMessage.messageRef && android.os.HidlSupport.deepEquals(this.cdmaMessage, imsSmsMessage.cdmaMessage) && android.os.HidlSupport.deepEquals(this.gsmMessage, imsSmsMessage.gsmMessage)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.tech))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.retry))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.messageRef))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.cdmaMessage)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.gsmMessage)));
    }

    public final java.lang.String toString() {
        return "{.tech = " + android.hardware.radio.V1_0.RadioTechnologyFamily.toString(this.tech) + ", .retry = " + this.retry + ", .messageRef = " + this.messageRef + ", .cdmaMessage = " + this.cdmaMessage + ", .gsmMessage = " + this.gsmMessage + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(48L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.ImsSmsMessage> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.ImsSmsMessage> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 48, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.ImsSmsMessage imsSmsMessage = new android.hardware.radio.V1_0.ImsSmsMessage();
            imsSmsMessage.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 48);
            arrayList.add(imsSmsMessage);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.tech = hwBlob.getInt32(j + 0);
        this.retry = hwBlob.getBool(j + 4);
        this.messageRef = hwBlob.getInt32(j + 8);
        long j2 = j + 16;
        int int32 = hwBlob.getInt32(j2 + 8);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 88, hwBlob.handle(), j2 + 0, true);
        this.cdmaMessage.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.CdmaSmsMessage cdmaSmsMessage = new android.hardware.radio.V1_0.CdmaSmsMessage();
            cdmaSmsMessage.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 88);
            this.cdmaMessage.add(cdmaSmsMessage);
        }
        long j3 = j + 32;
        int int322 = hwBlob.getInt32(8 + j3);
        android.os.HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 32, hwBlob.handle(), j3 + 0, true);
        this.gsmMessage.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            android.hardware.radio.V1_0.GsmSmsMessage gsmSmsMessage = new android.hardware.radio.V1_0.GsmSmsMessage();
            gsmSmsMessage.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer2, i2 * 32);
            this.gsmMessage.add(gsmSmsMessage);
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(48);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.ImsSmsMessage> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 48);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 48);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(j + 0, this.tech);
        hwBlob.putBool(j + 4, this.retry);
        hwBlob.putInt32(j + 8, this.messageRef);
        int size = this.cdmaMessage.size();
        long j2 = j + 16;
        hwBlob.putInt32(j2 + 8, size);
        hwBlob.putBool(j2 + 12, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 88);
        for (int i = 0; i < size; i++) {
            this.cdmaMessage.get(i).writeEmbeddedToBlob(hwBlob2, i * 88);
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
        int size2 = this.gsmMessage.size();
        long j3 = j + 32;
        hwBlob.putInt32(8 + j3, size2);
        hwBlob.putBool(j3 + 12, false);
        android.os.HwBlob hwBlob3 = new android.os.HwBlob(size2 * 32);
        for (int i2 = 0; i2 < size2; i2++) {
            this.gsmMessage.get(i2).writeEmbeddedToBlob(hwBlob3, i2 * 32);
        }
        hwBlob.putBlob(j3 + 0, hwBlob3);
    }
}
