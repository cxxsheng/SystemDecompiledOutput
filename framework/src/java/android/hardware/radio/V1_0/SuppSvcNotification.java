package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class SuppSvcNotification {
    public boolean isMT = false;
    public int code = 0;
    public int index = 0;
    public int type = 0;
    public java.lang.String number = new java.lang.String();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.SuppSvcNotification.class) {
            return false;
        }
        android.hardware.radio.V1_0.SuppSvcNotification suppSvcNotification = (android.hardware.radio.V1_0.SuppSvcNotification) obj;
        if (this.isMT == suppSvcNotification.isMT && this.code == suppSvcNotification.code && this.index == suppSvcNotification.index && this.type == suppSvcNotification.type && android.os.HidlSupport.deepEquals(this.number, suppSvcNotification.number)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.isMT))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.code))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.index))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.type))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.number)));
    }

    public final java.lang.String toString() {
        return "{.isMT = " + this.isMT + ", .code = " + this.code + ", .index = " + this.index + ", .type = " + this.type + ", .number = " + this.number + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.SuppSvcNotification> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.SuppSvcNotification> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.SuppSvcNotification suppSvcNotification = new android.hardware.radio.V1_0.SuppSvcNotification();
            suppSvcNotification.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 32);
            arrayList.add(suppSvcNotification);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.isMT = hwBlob.getBool(j + 0);
        this.code = hwBlob.getInt32(4 + j);
        this.index = hwBlob.getInt32(8 + j);
        this.type = hwBlob.getInt32(12 + j);
        long j2 = j + 16;
        this.number = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.number.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(32);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.SuppSvcNotification> arrayList) {
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
        hwBlob.putBool(0 + j, this.isMT);
        hwBlob.putInt32(4 + j, this.code);
        hwBlob.putInt32(8 + j, this.index);
        hwBlob.putInt32(12 + j, this.type);
        hwBlob.putString(j + 16, this.number);
    }
}
