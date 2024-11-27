package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class SelectUiccSub {
    public int slot = 0;
    public int appIndex = 0;
    public int subType = 0;
    public int actStatus = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.SelectUiccSub.class) {
            return false;
        }
        android.hardware.radio.V1_0.SelectUiccSub selectUiccSub = (android.hardware.radio.V1_0.SelectUiccSub) obj;
        if (this.slot == selectUiccSub.slot && this.appIndex == selectUiccSub.appIndex && this.subType == selectUiccSub.subType && this.actStatus == selectUiccSub.actStatus) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.slot))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.appIndex))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.subType))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.actStatus))));
    }

    public final java.lang.String toString() {
        return "{.slot = " + this.slot + ", .appIndex = " + this.appIndex + ", .subType = " + android.hardware.radio.V1_0.SubscriptionType.toString(this.subType) + ", .actStatus = " + android.hardware.radio.V1_0.UiccSubActStatus.toString(this.actStatus) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(16L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.SelectUiccSub> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.SelectUiccSub> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.SelectUiccSub selectUiccSub = new android.hardware.radio.V1_0.SelectUiccSub();
            selectUiccSub.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 16);
            arrayList.add(selectUiccSub);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.slot = hwBlob.getInt32(0 + j);
        this.appIndex = hwBlob.getInt32(4 + j);
        this.subType = hwBlob.getInt32(8 + j);
        this.actStatus = hwBlob.getInt32(j + 12);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.SelectUiccSub> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 16);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 16);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(0 + j, this.slot);
        hwBlob.putInt32(4 + j, this.appIndex);
        hwBlob.putInt32(8 + j, this.subType);
        hwBlob.putInt32(j + 12, this.actStatus);
    }
}
