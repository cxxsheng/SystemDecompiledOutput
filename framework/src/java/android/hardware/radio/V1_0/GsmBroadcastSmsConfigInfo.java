package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class GsmBroadcastSmsConfigInfo {
    public int fromServiceId = 0;
    public int toServiceId = 0;
    public int fromCodeScheme = 0;
    public int toCodeScheme = 0;
    public boolean selected = false;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.GsmBroadcastSmsConfigInfo.class) {
            return false;
        }
        android.hardware.radio.V1_0.GsmBroadcastSmsConfigInfo gsmBroadcastSmsConfigInfo = (android.hardware.radio.V1_0.GsmBroadcastSmsConfigInfo) obj;
        if (this.fromServiceId == gsmBroadcastSmsConfigInfo.fromServiceId && this.toServiceId == gsmBroadcastSmsConfigInfo.toServiceId && this.fromCodeScheme == gsmBroadcastSmsConfigInfo.fromCodeScheme && this.toCodeScheme == gsmBroadcastSmsConfigInfo.toCodeScheme && this.selected == gsmBroadcastSmsConfigInfo.selected) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.fromServiceId))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.toServiceId))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.fromCodeScheme))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.toCodeScheme))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.selected))));
    }

    public final java.lang.String toString() {
        return "{.fromServiceId = " + this.fromServiceId + ", .toServiceId = " + this.toServiceId + ", .fromCodeScheme = " + this.fromCodeScheme + ", .toCodeScheme = " + this.toCodeScheme + ", .selected = " + this.selected + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(20L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.GsmBroadcastSmsConfigInfo> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.GsmBroadcastSmsConfigInfo> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 20, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.GsmBroadcastSmsConfigInfo gsmBroadcastSmsConfigInfo = new android.hardware.radio.V1_0.GsmBroadcastSmsConfigInfo();
            gsmBroadcastSmsConfigInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 20);
            arrayList.add(gsmBroadcastSmsConfigInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.fromServiceId = hwBlob.getInt32(0 + j);
        this.toServiceId = hwBlob.getInt32(4 + j);
        this.fromCodeScheme = hwBlob.getInt32(8 + j);
        this.toCodeScheme = hwBlob.getInt32(12 + j);
        this.selected = hwBlob.getBool(j + 16);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(20);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.GsmBroadcastSmsConfigInfo> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 20);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 20);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(0 + j, this.fromServiceId);
        hwBlob.putInt32(4 + j, this.toServiceId);
        hwBlob.putInt32(8 + j, this.fromCodeScheme);
        hwBlob.putInt32(12 + j, this.toCodeScheme);
        hwBlob.putBool(j + 16, this.selected);
    }
}
