package android.hardware.broadcastradio.V2_0;

/* loaded from: classes.dex */
public final class Announcement {
    public android.hardware.broadcastradio.V2_0.ProgramSelector selector = new android.hardware.broadcastradio.V2_0.ProgramSelector();
    public byte type = 0;
    public java.util.ArrayList<android.hardware.broadcastradio.V2_0.VendorKeyValue> vendorInfo = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.broadcastradio.V2_0.Announcement.class) {
            return false;
        }
        android.hardware.broadcastradio.V2_0.Announcement announcement = (android.hardware.broadcastradio.V2_0.Announcement) obj;
        if (android.os.HidlSupport.deepEquals(this.selector, announcement.selector) && this.type == announcement.type && android.os.HidlSupport.deepEquals(this.vendorInfo, announcement.vendorInfo)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.selector)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.type))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.vendorInfo)));
    }

    public final java.lang.String toString() {
        return "{.selector = " + this.selector + ", .type = " + android.hardware.broadcastradio.V2_0.AnnouncementType.toString(this.type) + ", .vendorInfo = " + this.vendorInfo + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(56L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.broadcastradio.V2_0.Announcement> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.broadcastradio.V2_0.Announcement> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 56, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.broadcastradio.V2_0.Announcement announcement = new android.hardware.broadcastradio.V2_0.Announcement();
            announcement.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 56);
            arrayList.add(announcement);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.selector.readEmbeddedFromParcel(hwParcel, hwBlob, j + 0);
        this.type = hwBlob.getInt8(j + 32);
        long j2 = j + 40;
        int int32 = hwBlob.getInt32(8 + j2);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, hwBlob.handle(), j2 + 0, true);
        this.vendorInfo.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.broadcastradio.V2_0.VendorKeyValue vendorKeyValue = new android.hardware.broadcastradio.V2_0.VendorKeyValue();
            vendorKeyValue.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 32);
            this.vendorInfo.add(vendorKeyValue);
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(56);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.broadcastradio.V2_0.Announcement> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 56);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 56);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        this.selector.writeEmbeddedToBlob(hwBlob, j + 0);
        hwBlob.putInt8(32 + j, this.type);
        int size = this.vendorInfo.size();
        long j2 = j + 40;
        hwBlob.putInt32(8 + j2, size);
        hwBlob.putBool(12 + j2, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 32);
        for (int i = 0; i < size; i++) {
            this.vendorInfo.get(i).writeEmbeddedToBlob(hwBlob2, i * 32);
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
    }
}
