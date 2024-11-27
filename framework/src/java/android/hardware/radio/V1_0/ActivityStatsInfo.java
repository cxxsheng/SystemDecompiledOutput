package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class ActivityStatsInfo {
    public int sleepModeTimeMs = 0;
    public int idleModeTimeMs = 0;
    public int[] txmModetimeMs = new int[5];
    public int rxModeTimeMs = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.ActivityStatsInfo.class) {
            return false;
        }
        android.hardware.radio.V1_0.ActivityStatsInfo activityStatsInfo = (android.hardware.radio.V1_0.ActivityStatsInfo) obj;
        if (this.sleepModeTimeMs == activityStatsInfo.sleepModeTimeMs && this.idleModeTimeMs == activityStatsInfo.idleModeTimeMs && android.os.HidlSupport.deepEquals(this.txmModetimeMs, activityStatsInfo.txmModetimeMs) && this.rxModeTimeMs == activityStatsInfo.rxModeTimeMs) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.sleepModeTimeMs))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.idleModeTimeMs))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.txmModetimeMs)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.rxModeTimeMs))));
    }

    public final java.lang.String toString() {
        return "{.sleepModeTimeMs = " + this.sleepModeTimeMs + ", .idleModeTimeMs = " + this.idleModeTimeMs + ", .txmModetimeMs = " + java.util.Arrays.toString(this.txmModetimeMs) + ", .rxModeTimeMs = " + this.rxModeTimeMs + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.ActivityStatsInfo> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.ActivityStatsInfo> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.ActivityStatsInfo activityStatsInfo = new android.hardware.radio.V1_0.ActivityStatsInfo();
            activityStatsInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 32);
            arrayList.add(activityStatsInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.sleepModeTimeMs = hwBlob.getInt32(0 + j);
        this.idleModeTimeMs = hwBlob.getInt32(4 + j);
        hwBlob.copyToInt32Array(8 + j, this.txmModetimeMs, 5);
        this.rxModeTimeMs = hwBlob.getInt32(j + 28);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(32);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.ActivityStatsInfo> arrayList) {
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
        hwBlob.putInt32(0 + j, this.sleepModeTimeMs);
        hwBlob.putInt32(4 + j, this.idleModeTimeMs);
        long j2 = 8 + j;
        int[] iArr = this.txmModetimeMs;
        if (iArr == null || iArr.length != 5) {
            throw new java.lang.IllegalArgumentException("Array element is not of the expected length");
        }
        hwBlob.putInt32Array(j2, iArr);
        hwBlob.putInt32(j + 28, this.rxModeTimeMs);
    }
}
