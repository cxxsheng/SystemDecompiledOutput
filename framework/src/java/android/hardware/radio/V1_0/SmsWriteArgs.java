package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class SmsWriteArgs {
    public int status = 0;
    public java.lang.String pdu = new java.lang.String();
    public java.lang.String smsc = new java.lang.String();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.SmsWriteArgs.class) {
            return false;
        }
        android.hardware.radio.V1_0.SmsWriteArgs smsWriteArgs = (android.hardware.radio.V1_0.SmsWriteArgs) obj;
        if (this.status == smsWriteArgs.status && android.os.HidlSupport.deepEquals(this.pdu, smsWriteArgs.pdu) && android.os.HidlSupport.deepEquals(this.smsc, smsWriteArgs.smsc)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.status))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.pdu)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.smsc)));
    }

    public final java.lang.String toString() {
        return "{.status = " + android.hardware.radio.V1_0.SmsWriteArgsStatus.toString(this.status) + ", .pdu = " + this.pdu + ", .smsc = " + this.smsc + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.SmsWriteArgs> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.SmsWriteArgs> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.SmsWriteArgs smsWriteArgs = new android.hardware.radio.V1_0.SmsWriteArgs();
            smsWriteArgs.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 40);
            arrayList.add(smsWriteArgs);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.status = hwBlob.getInt32(j + 0);
        long j2 = j + 8;
        this.pdu = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.pdu.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        long j3 = j + 24;
        this.smsc = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(this.smsc.getBytes().length + 1, hwBlob.handle(), j3 + 0, false);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(40);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.SmsWriteArgs> arrayList) {
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
        hwBlob.putInt32(0 + j, this.status);
        hwBlob.putString(8 + j, this.pdu);
        hwBlob.putString(j + 24, this.smsc);
    }
}
