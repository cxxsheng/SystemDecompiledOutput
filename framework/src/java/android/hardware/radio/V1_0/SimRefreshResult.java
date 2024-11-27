package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class SimRefreshResult {
    public int type = 0;
    public int efId = 0;
    public java.lang.String aid = new java.lang.String();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.SimRefreshResult.class) {
            return false;
        }
        android.hardware.radio.V1_0.SimRefreshResult simRefreshResult = (android.hardware.radio.V1_0.SimRefreshResult) obj;
        if (this.type == simRefreshResult.type && this.efId == simRefreshResult.efId && android.os.HidlSupport.deepEquals(this.aid, simRefreshResult.aid)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.type))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.efId))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.aid)));
    }

    public final java.lang.String toString() {
        return "{.type = " + android.hardware.radio.V1_0.SimRefreshType.toString(this.type) + ", .efId = " + this.efId + ", .aid = " + this.aid + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.SimRefreshResult> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.SimRefreshResult> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.SimRefreshResult simRefreshResult = new android.hardware.radio.V1_0.SimRefreshResult();
            simRefreshResult.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 24);
            arrayList.add(simRefreshResult);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.type = hwBlob.getInt32(j + 0);
        this.efId = hwBlob.getInt32(4 + j);
        long j2 = j + 8;
        this.aid = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.aid.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(24);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.SimRefreshResult> arrayList) {
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
        hwBlob.putInt32(0 + j, this.type);
        hwBlob.putInt32(4 + j, this.efId);
        hwBlob.putString(j + 8, this.aid);
    }
}
