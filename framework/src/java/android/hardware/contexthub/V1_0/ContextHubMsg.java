package android.hardware.contexthub.V1_0;

/* loaded from: classes.dex */
public final class ContextHubMsg {
    public long appName = 0;
    public short hostEndPoint = 0;
    public int msgType = 0;
    public java.util.ArrayList<java.lang.Byte> msg = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.contexthub.V1_0.ContextHubMsg.class) {
            return false;
        }
        android.hardware.contexthub.V1_0.ContextHubMsg contextHubMsg = (android.hardware.contexthub.V1_0.ContextHubMsg) obj;
        if (this.appName == contextHubMsg.appName && this.hostEndPoint == contextHubMsg.hostEndPoint && this.msgType == contextHubMsg.msgType && android.os.HidlSupport.deepEquals(this.msg, contextHubMsg.msg)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.appName))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Short.valueOf(this.hostEndPoint))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.msgType))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.msg)));
    }

    public final java.lang.String toString() {
        return "{.appName = " + this.appName + ", .hostEndPoint = " + ((int) this.hostEndPoint) + ", .msgType = " + this.msgType + ", .msg = " + this.msg + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.contexthub.V1_0.ContextHubMsg> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.contexthub.V1_0.ContextHubMsg> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.contexthub.V1_0.ContextHubMsg contextHubMsg = new android.hardware.contexthub.V1_0.ContextHubMsg();
            contextHubMsg.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 32);
            arrayList.add(contextHubMsg);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.appName = hwBlob.getInt64(j + 0);
        this.hostEndPoint = hwBlob.getInt16(j + 8);
        this.msgType = hwBlob.getInt32(j + 12);
        long j2 = j + 16;
        int int32 = hwBlob.getInt32(8 + j2);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 1, hwBlob.handle(), j2 + 0, true);
        this.msg.clear();
        for (int i = 0; i < int32; i++) {
            this.msg.add(java.lang.Byte.valueOf(readEmbeddedBuffer.getInt8(i * 1)));
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(32);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.contexthub.V1_0.ContextHubMsg> arrayList) {
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
        hwBlob.putInt64(j + 0, this.appName);
        hwBlob.putInt16(j + 8, this.hostEndPoint);
        hwBlob.putInt32(j + 12, this.msgType);
        int size = this.msg.size();
        long j2 = j + 16;
        hwBlob.putInt32(8 + j2, size);
        hwBlob.putBool(12 + j2, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 1);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt8(i * 1, this.msg.get(i).byteValue());
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
    }
}
