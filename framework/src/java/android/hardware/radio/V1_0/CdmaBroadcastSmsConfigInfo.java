package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class CdmaBroadcastSmsConfigInfo {
    public int serviceCategory = 0;
    public int language = 0;
    public boolean selected = false;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.CdmaBroadcastSmsConfigInfo.class) {
            return false;
        }
        android.hardware.radio.V1_0.CdmaBroadcastSmsConfigInfo cdmaBroadcastSmsConfigInfo = (android.hardware.radio.V1_0.CdmaBroadcastSmsConfigInfo) obj;
        if (this.serviceCategory == cdmaBroadcastSmsConfigInfo.serviceCategory && this.language == cdmaBroadcastSmsConfigInfo.language && this.selected == cdmaBroadcastSmsConfigInfo.selected) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.serviceCategory))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.language))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.selected))));
    }

    public final java.lang.String toString() {
        return "{.serviceCategory = " + this.serviceCategory + ", .language = " + this.language + ", .selected = " + this.selected + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(12L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.CdmaBroadcastSmsConfigInfo> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.CdmaBroadcastSmsConfigInfo> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 12, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.CdmaBroadcastSmsConfigInfo cdmaBroadcastSmsConfigInfo = new android.hardware.radio.V1_0.CdmaBroadcastSmsConfigInfo();
            cdmaBroadcastSmsConfigInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 12);
            arrayList.add(cdmaBroadcastSmsConfigInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.serviceCategory = hwBlob.getInt32(0 + j);
        this.language = hwBlob.getInt32(4 + j);
        this.selected = hwBlob.getBool(j + 8);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(12);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.CdmaBroadcastSmsConfigInfo> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 12);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 12);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(0 + j, this.serviceCategory);
        hwBlob.putInt32(4 + j, this.language);
        hwBlob.putBool(j + 8, this.selected);
    }
}
