package android.hardware.contexthub.V1_0;

/* loaded from: classes.dex */
public final class ContextHub {
    public java.lang.String name = new java.lang.String();
    public java.lang.String vendor = new java.lang.String();
    public java.lang.String toolchain = new java.lang.String();
    public int platformVersion = 0;
    public int toolchainVersion = 0;
    public int hubId = 0;
    public float peakMips = 0.0f;
    public float stoppedPowerDrawMw = 0.0f;
    public float sleepPowerDrawMw = 0.0f;
    public float peakPowerDrawMw = 0.0f;
    public java.util.ArrayList<android.hardware.contexthub.V1_0.PhysicalSensor> connectedSensors = new java.util.ArrayList<>();
    public int maxSupportedMsgLen = 0;
    public long chrePlatformId = 0;
    public byte chreApiMajorVersion = 0;
    public byte chreApiMinorVersion = 0;
    public short chrePatchVersion = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.contexthub.V1_0.ContextHub.class) {
            return false;
        }
        android.hardware.contexthub.V1_0.ContextHub contextHub = (android.hardware.contexthub.V1_0.ContextHub) obj;
        if (android.os.HidlSupport.deepEquals(this.name, contextHub.name) && android.os.HidlSupport.deepEquals(this.vendor, contextHub.vendor) && android.os.HidlSupport.deepEquals(this.toolchain, contextHub.toolchain) && this.platformVersion == contextHub.platformVersion && this.toolchainVersion == contextHub.toolchainVersion && this.hubId == contextHub.hubId && this.peakMips == contextHub.peakMips && this.stoppedPowerDrawMw == contextHub.stoppedPowerDrawMw && this.sleepPowerDrawMw == contextHub.sleepPowerDrawMw && this.peakPowerDrawMw == contextHub.peakPowerDrawMw && android.os.HidlSupport.deepEquals(this.connectedSensors, contextHub.connectedSensors) && this.maxSupportedMsgLen == contextHub.maxSupportedMsgLen && this.chrePlatformId == contextHub.chrePlatformId && this.chreApiMajorVersion == contextHub.chreApiMajorVersion && this.chreApiMinorVersion == contextHub.chreApiMinorVersion && this.chrePatchVersion == contextHub.chrePatchVersion) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.name)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.vendor)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.toolchain)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.platformVersion))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.toolchainVersion))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.hubId))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.peakMips))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.stoppedPowerDrawMw))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.sleepPowerDrawMw))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Float.valueOf(this.peakPowerDrawMw))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.connectedSensors)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.maxSupportedMsgLen))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.chrePlatformId))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.chreApiMajorVersion))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Byte.valueOf(this.chreApiMinorVersion))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Short.valueOf(this.chrePatchVersion))));
    }

    public final java.lang.String toString() {
        return "{.name = " + this.name + ", .vendor = " + this.vendor + ", .toolchain = " + this.toolchain + ", .platformVersion = " + this.platformVersion + ", .toolchainVersion = " + this.toolchainVersion + ", .hubId = " + this.hubId + ", .peakMips = " + this.peakMips + ", .stoppedPowerDrawMw = " + this.stoppedPowerDrawMw + ", .sleepPowerDrawMw = " + this.sleepPowerDrawMw + ", .peakPowerDrawMw = " + this.peakPowerDrawMw + ", .connectedSensors = " + this.connectedSensors + ", .maxSupportedMsgLen = " + this.maxSupportedMsgLen + ", .chrePlatformId = " + this.chrePlatformId + ", .chreApiMajorVersion = " + ((int) this.chreApiMajorVersion) + ", .chreApiMinorVersion = " + ((int) this.chreApiMinorVersion) + ", .chrePatchVersion = " + ((int) this.chrePatchVersion) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(120L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.contexthub.V1_0.ContextHub> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.contexthub.V1_0.ContextHub> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 120, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.contexthub.V1_0.ContextHub contextHub = new android.hardware.contexthub.V1_0.ContextHub();
            contextHub.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 120);
            arrayList.add(contextHub);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        long j2 = j + 0;
        this.name = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.name.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        long j3 = j + 16;
        this.vendor = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(this.vendor.getBytes().length + 1, hwBlob.handle(), j3 + 0, false);
        long j4 = j + 32;
        this.toolchain = hwBlob.getString(j4);
        hwParcel.readEmbeddedBuffer(this.toolchain.getBytes().length + 1, hwBlob.handle(), j4 + 0, false);
        this.platformVersion = hwBlob.getInt32(j + 48);
        this.toolchainVersion = hwBlob.getInt32(j + 52);
        this.hubId = hwBlob.getInt32(j + 56);
        this.peakMips = hwBlob.getFloat(j + 60);
        this.stoppedPowerDrawMw = hwBlob.getFloat(j + 64);
        this.sleepPowerDrawMw = hwBlob.getFloat(j + 68);
        this.peakPowerDrawMw = hwBlob.getFloat(j + 72);
        long j5 = j + 80;
        int int32 = hwBlob.getInt32(8 + j5);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 96, hwBlob.handle(), j5 + 0, true);
        this.connectedSensors.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.contexthub.V1_0.PhysicalSensor physicalSensor = new android.hardware.contexthub.V1_0.PhysicalSensor();
            physicalSensor.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 96);
            this.connectedSensors.add(physicalSensor);
        }
        this.maxSupportedMsgLen = hwBlob.getInt32(j + 96);
        this.chrePlatformId = hwBlob.getInt64(j + 104);
        this.chreApiMajorVersion = hwBlob.getInt8(j + 112);
        this.chreApiMinorVersion = hwBlob.getInt8(j + 113);
        this.chrePatchVersion = hwBlob.getInt16(j + 114);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(120);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.contexthub.V1_0.ContextHub> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 120);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 120);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putString(j + 0, this.name);
        hwBlob.putString(16 + j, this.vendor);
        hwBlob.putString(32 + j, this.toolchain);
        hwBlob.putInt32(48 + j, this.platformVersion);
        hwBlob.putInt32(52 + j, this.toolchainVersion);
        hwBlob.putInt32(56 + j, this.hubId);
        hwBlob.putFloat(60 + j, this.peakMips);
        hwBlob.putFloat(64 + j, this.stoppedPowerDrawMw);
        hwBlob.putFloat(68 + j, this.sleepPowerDrawMw);
        hwBlob.putFloat(72 + j, this.peakPowerDrawMw);
        int size = this.connectedSensors.size();
        long j2 = 80 + j;
        hwBlob.putInt32(8 + j2, size);
        hwBlob.putBool(12 + j2, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 96);
        for (int i = 0; i < size; i++) {
            this.connectedSensors.get(i).writeEmbeddedToBlob(hwBlob2, i * 96);
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
        hwBlob.putInt32(96 + j, this.maxSupportedMsgLen);
        hwBlob.putInt64(104 + j, this.chrePlatformId);
        hwBlob.putInt8(112 + j, this.chreApiMajorVersion);
        hwBlob.putInt8(113 + j, this.chreApiMinorVersion);
        hwBlob.putInt16(j + 114, this.chrePatchVersion);
    }
}
