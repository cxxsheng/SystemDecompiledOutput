package android.hardware.boot.V1_0;

/* loaded from: classes.dex */
public final class CommandResult {
    public boolean success = false;
    public java.lang.String errMsg = new java.lang.String();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.boot.V1_0.CommandResult.class) {
            return false;
        }
        android.hardware.boot.V1_0.CommandResult commandResult = (android.hardware.boot.V1_0.CommandResult) obj;
        if (this.success == commandResult.success && android.os.HidlSupport.deepEquals(this.errMsg, commandResult.errMsg)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.success))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.errMsg)));
    }

    public final java.lang.String toString() {
        return "{.success = " + this.success + ", .errMsg = " + this.errMsg + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.boot.V1_0.CommandResult> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.boot.V1_0.CommandResult> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.boot.V1_0.CommandResult commandResult = new android.hardware.boot.V1_0.CommandResult();
            commandResult.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 24);
            arrayList.add(commandResult);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.success = hwBlob.getBool(j + 0);
        long j2 = j + 8;
        this.errMsg = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.errMsg.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(24);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.boot.V1_0.CommandResult> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 24);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 24);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putBool(0 + j, this.success);
        hwBlob.putString(j + 8, this.errMsg);
    }
}
