package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class HardwareConfigModem {
    public int rilModel = 0;
    public int rat = 0;
    public int maxVoice = 0;
    public int maxData = 0;
    public int maxStandby = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.HardwareConfigModem.class) {
            return false;
        }
        android.hardware.radio.V1_0.HardwareConfigModem hardwareConfigModem = (android.hardware.radio.V1_0.HardwareConfigModem) obj;
        if (this.rilModel == hardwareConfigModem.rilModel && this.rat == hardwareConfigModem.rat && this.maxVoice == hardwareConfigModem.maxVoice && this.maxData == hardwareConfigModem.maxData && this.maxStandby == hardwareConfigModem.maxStandby) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.rilModel))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.rat))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.maxVoice))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.maxData))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.maxStandby))));
    }

    public final java.lang.String toString() {
        return "{.rilModel = " + this.rilModel + ", .rat = " + this.rat + ", .maxVoice = " + this.maxVoice + ", .maxData = " + this.maxData + ", .maxStandby = " + this.maxStandby + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(20L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.HardwareConfigModem> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.HardwareConfigModem> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 20, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.HardwareConfigModem hardwareConfigModem = new android.hardware.radio.V1_0.HardwareConfigModem();
            hardwareConfigModem.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 20);
            arrayList.add(hardwareConfigModem);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.rilModel = hwBlob.getInt32(0 + j);
        this.rat = hwBlob.getInt32(4 + j);
        this.maxVoice = hwBlob.getInt32(8 + j);
        this.maxData = hwBlob.getInt32(12 + j);
        this.maxStandby = hwBlob.getInt32(j + 16);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(20);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.HardwareConfigModem> arrayList) {
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
        hwBlob.putInt32(0 + j, this.rilModel);
        hwBlob.putInt32(4 + j, this.rat);
        hwBlob.putInt32(8 + j, this.maxVoice);
        hwBlob.putInt32(12 + j, this.maxData);
        hwBlob.putInt32(j + 16, this.maxStandby);
    }
}
