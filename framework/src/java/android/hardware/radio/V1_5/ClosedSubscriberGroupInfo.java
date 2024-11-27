package android.hardware.radio.V1_5;

/* loaded from: classes2.dex */
public final class ClosedSubscriberGroupInfo {
    public boolean csgIndication = false;
    public java.lang.String homeNodebName = new java.lang.String();
    public int csgIdentity = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_5.ClosedSubscriberGroupInfo.class) {
            return false;
        }
        android.hardware.radio.V1_5.ClosedSubscriberGroupInfo closedSubscriberGroupInfo = (android.hardware.radio.V1_5.ClosedSubscriberGroupInfo) obj;
        if (this.csgIndication == closedSubscriberGroupInfo.csgIndication && android.os.HidlSupport.deepEquals(this.homeNodebName, closedSubscriberGroupInfo.homeNodebName) && this.csgIdentity == closedSubscriberGroupInfo.csgIdentity) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.csgIndication))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.homeNodebName)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.csgIdentity))));
    }

    public final java.lang.String toString() {
        return "{.csgIndication = " + this.csgIndication + ", .homeNodebName = " + this.homeNodebName + ", .csgIdentity = " + this.csgIdentity + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_5.ClosedSubscriberGroupInfo> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_5.ClosedSubscriberGroupInfo> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_5.ClosedSubscriberGroupInfo closedSubscriberGroupInfo = new android.hardware.radio.V1_5.ClosedSubscriberGroupInfo();
            closedSubscriberGroupInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 32);
            arrayList.add(closedSubscriberGroupInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.csgIndication = hwBlob.getBool(j + 0);
        long j2 = j + 8;
        this.homeNodebName = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.homeNodebName.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        this.csgIdentity = hwBlob.getInt32(j + 24);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(32);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_5.ClosedSubscriberGroupInfo> arrayList) {
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
        hwBlob.putBool(0 + j, this.csgIndication);
        hwBlob.putString(8 + j, this.homeNodebName);
        hwBlob.putInt32(j + 24, this.csgIdentity);
    }
}
