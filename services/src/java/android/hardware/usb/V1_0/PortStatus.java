package android.hardware.usb.V1_0;

/* loaded from: classes.dex */
public final class PortStatus {
    public java.lang.String portName = new java.lang.String();
    public int currentDataRole = 0;
    public int currentPowerRole = 0;
    public int currentMode = 0;
    public boolean canChangeMode = false;
    public boolean canChangeDataRole = false;
    public boolean canChangePowerRole = false;
    public int supportedModes = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.usb.V1_0.PortStatus.class) {
            return false;
        }
        android.hardware.usb.V1_0.PortStatus portStatus = (android.hardware.usb.V1_0.PortStatus) obj;
        if (android.os.HidlSupport.deepEquals(this.portName, portStatus.portName) && this.currentDataRole == portStatus.currentDataRole && this.currentPowerRole == portStatus.currentPowerRole && this.currentMode == portStatus.currentMode && this.canChangeMode == portStatus.canChangeMode && this.canChangeDataRole == portStatus.canChangeDataRole && this.canChangePowerRole == portStatus.canChangePowerRole && this.supportedModes == portStatus.supportedModes) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.portName)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.currentDataRole))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.currentPowerRole))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.currentMode))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.canChangeMode))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.canChangeDataRole))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.canChangePowerRole))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.supportedModes))));
    }

    public final java.lang.String toString() {
        return "{.portName = " + this.portName + ", .currentDataRole = " + android.hardware.usb.V1_0.PortDataRole.toString(this.currentDataRole) + ", .currentPowerRole = " + android.hardware.usb.V1_0.PortPowerRole.toString(this.currentPowerRole) + ", .currentMode = " + android.hardware.usb.V1_0.PortMode.toString(this.currentMode) + ", .canChangeMode = " + this.canChangeMode + ", .canChangeDataRole = " + this.canChangeDataRole + ", .canChangePowerRole = " + this.canChangePowerRole + ", .supportedModes = " + android.hardware.usb.V1_0.PortMode.toString(this.supportedModes) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.usb.V1_0.PortStatus> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.usb.V1_0.PortStatus> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.usb.V1_0.PortStatus portStatus = new android.hardware.usb.V1_0.PortStatus();
            portStatus.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 40);
            arrayList.add(portStatus);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        long j2 = j + 0;
        this.portName = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.portName.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        this.currentDataRole = hwBlob.getInt32(j + 16);
        this.currentPowerRole = hwBlob.getInt32(j + 20);
        this.currentMode = hwBlob.getInt32(j + 24);
        this.canChangeMode = hwBlob.getBool(j + 28);
        this.canChangeDataRole = hwBlob.getBool(j + 29);
        this.canChangePowerRole = hwBlob.getBool(j + 30);
        this.supportedModes = hwBlob.getInt32(j + 32);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(40);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.usb.V1_0.PortStatus> arrayList) {
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
        hwBlob.putString(0 + j, this.portName);
        hwBlob.putInt32(16 + j, this.currentDataRole);
        hwBlob.putInt32(20 + j, this.currentPowerRole);
        hwBlob.putInt32(24 + j, this.currentMode);
        hwBlob.putBool(28 + j, this.canChangeMode);
        hwBlob.putBool(29 + j, this.canChangeDataRole);
        hwBlob.putBool(30 + j, this.canChangePowerRole);
        hwBlob.putInt32(j + 32, this.supportedModes);
    }
}
