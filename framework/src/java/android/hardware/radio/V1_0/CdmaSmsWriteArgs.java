package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class CdmaSmsWriteArgs {
    public int status = 0;
    public android.hardware.radio.V1_0.CdmaSmsMessage message = new android.hardware.radio.V1_0.CdmaSmsMessage();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.CdmaSmsWriteArgs.class) {
            return false;
        }
        android.hardware.radio.V1_0.CdmaSmsWriteArgs cdmaSmsWriteArgs = (android.hardware.radio.V1_0.CdmaSmsWriteArgs) obj;
        if (this.status == cdmaSmsWriteArgs.status && android.os.HidlSupport.deepEquals(this.message, cdmaSmsWriteArgs.message)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.status))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.message)));
    }

    public final java.lang.String toString() {
        return "{.status = " + android.hardware.radio.V1_0.CdmaSmsWriteArgsStatus.toString(this.status) + ", .message = " + this.message + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(96L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.CdmaSmsWriteArgs> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.CdmaSmsWriteArgs> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 96, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.CdmaSmsWriteArgs cdmaSmsWriteArgs = new android.hardware.radio.V1_0.CdmaSmsWriteArgs();
            cdmaSmsWriteArgs.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 96);
            arrayList.add(cdmaSmsWriteArgs);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.status = hwBlob.getInt32(0 + j);
        this.message.readEmbeddedFromParcel(hwParcel, hwBlob, j + 8);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(96);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.CdmaSmsWriteArgs> arrayList) {
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
        hwBlob.putInt32(0 + j, this.status);
        this.message.writeEmbeddedToBlob(hwBlob, j + 8);
    }
}
