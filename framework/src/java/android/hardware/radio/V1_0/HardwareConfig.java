package android.hardware.radio.V1_0;

/* loaded from: classes2.dex */
public final class HardwareConfig {
    public int type = 0;
    public java.lang.String uuid = new java.lang.String();
    public int state = 0;
    public java.util.ArrayList<android.hardware.radio.V1_0.HardwareConfigModem> modem = new java.util.ArrayList<>();
    public java.util.ArrayList<android.hardware.radio.V1_0.HardwareConfigSim> sim = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.radio.V1_0.HardwareConfig.class) {
            return false;
        }
        android.hardware.radio.V1_0.HardwareConfig hardwareConfig = (android.hardware.radio.V1_0.HardwareConfig) obj;
        if (this.type == hardwareConfig.type && android.os.HidlSupport.deepEquals(this.uuid, hardwareConfig.uuid) && this.state == hardwareConfig.state && android.os.HidlSupport.deepEquals(this.modem, hardwareConfig.modem) && android.os.HidlSupport.deepEquals(this.sim, hardwareConfig.sim)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.type))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.uuid)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.state))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.modem)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.sim)));
    }

    public final java.lang.String toString() {
        return "{.type = " + android.hardware.radio.V1_0.HardwareConfigType.toString(this.type) + ", .uuid = " + this.uuid + ", .state = " + android.hardware.radio.V1_0.HardwareConfigState.toString(this.state) + ", .modem = " + this.modem + ", .sim = " + this.sim + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(64L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.radio.V1_0.HardwareConfig> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.radio.V1_0.HardwareConfig> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 64, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.HardwareConfig hardwareConfig = new android.hardware.radio.V1_0.HardwareConfig();
            hardwareConfig.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 64);
            arrayList.add(hardwareConfig);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.type = hwBlob.getInt32(j + 0);
        long j2 = j + 8;
        this.uuid = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.uuid.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        this.state = hwBlob.getInt32(j + 24);
        long j3 = j + 32;
        int int32 = hwBlob.getInt32(j3 + 8);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 20, hwBlob.handle(), j3 + 0, true);
        this.modem.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.radio.V1_0.HardwareConfigModem hardwareConfigModem = new android.hardware.radio.V1_0.HardwareConfigModem();
            hardwareConfigModem.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 20);
            this.modem.add(hardwareConfigModem);
        }
        long j4 = j + 48;
        int int322 = hwBlob.getInt32(8 + j4);
        android.os.HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 16, hwBlob.handle(), j4 + 0, true);
        this.sim.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            android.hardware.radio.V1_0.HardwareConfigSim hardwareConfigSim = new android.hardware.radio.V1_0.HardwareConfigSim();
            hardwareConfigSim.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer2, i2 * 16);
            this.sim.add(hardwareConfigSim);
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(64);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.radio.V1_0.HardwareConfig> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 64);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 64);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(j + 0, this.type);
        hwBlob.putString(j + 8, this.uuid);
        hwBlob.putInt32(j + 24, this.state);
        int size = this.modem.size();
        long j2 = j + 32;
        hwBlob.putInt32(j2 + 8, size);
        hwBlob.putBool(j2 + 12, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 20);
        for (int i = 0; i < size; i++) {
            this.modem.get(i).writeEmbeddedToBlob(hwBlob2, i * 20);
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
        int size2 = this.sim.size();
        long j3 = j + 48;
        hwBlob.putInt32(8 + j3, size2);
        hwBlob.putBool(j3 + 12, false);
        android.os.HwBlob hwBlob3 = new android.os.HwBlob(size2 * 16);
        for (int i2 = 0; i2 < size2; i2++) {
            this.sim.get(i2).writeEmbeddedToBlob(hwBlob3, i2 * 16);
        }
        hwBlob.putBlob(j3 + 0, hwBlob3);
    }
}
