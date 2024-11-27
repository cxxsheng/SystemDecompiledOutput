package android.hardware.radio.V1_4;

/* loaded from: classes2.dex */
public final class RadioCapability {
    public int raf;
    public int session = 0;
    public int phase = 0;
    public java.lang.String logicalModemUuid = new java.lang.String();
    public int status = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_4.RadioCapability.class) {
            return false;
        }
        android.hardware.radio.V1_4.RadioCapability radioCapability = (android.hardware.radio.V1_4.RadioCapability) obj;
        if (this.session == radioCapability.session && this.phase == radioCapability.phase && android.os.HidlSupport.deepEquals(java.lang.Integer.valueOf(this.raf), java.lang.Integer.valueOf(radioCapability.raf)) && android.os.HidlSupport.deepEquals(this.logicalModemUuid, radioCapability.logicalModemUuid) && this.status == radioCapability.status) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.session))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.phase))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.raf))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.logicalModemUuid)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.status))));
    }

    public final java.lang.String toString() {
        return "{.session = " + this.session + ", .phase = " + android.hardware.radio.V1_0.RadioCapabilityPhase.toString(this.phase) + ", .raf = " + android.hardware.radio.V1_4.RadioAccessFamily.dumpBitfield(this.raf) + ", .logicalModemUuid = " + this.logicalModemUuid + ", .status = " + android.hardware.radio.V1_0.RadioCapabilityStatus.toString(this.status) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_4.RadioCapability> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_4.RadioCapability> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_4.RadioCapability radioCapability = new android.hardware.radio.V1_4.RadioCapability();
            radioCapability.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 40);
            arrayList.add(radioCapability);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.session = hwBlob.getInt32(j + 0);
        this.phase = hwBlob.getInt32(j + 4);
        this.raf = hwBlob.getInt32(j + 8);
        long j2 = j + 16;
        this.logicalModemUuid = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.logicalModemUuid.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        this.status = hwBlob.getInt32(j + 32);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(40);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_4.RadioCapability> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 40);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 40);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(0 + j, this.session);
        hwBlob.putInt32(4 + j, this.phase);
        hwBlob.putInt32(8 + j, this.raf);
        hwBlob.putString(16 + j, this.logicalModemUuid);
        hwBlob.putInt32(j + 32, this.status);
    }
}
