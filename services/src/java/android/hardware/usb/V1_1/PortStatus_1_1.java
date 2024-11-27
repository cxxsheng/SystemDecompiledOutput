package android.hardware.usb.V1_1;

/* loaded from: classes.dex */
public final class PortStatus_1_1 {
    public int supportedModes;
    public android.hardware.usb.V1_0.PortStatus status = new android.hardware.usb.V1_0.PortStatus();
    public int currentMode = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.usb.V1_1.PortStatus_1_1.class) {
            return false;
        }
        android.hardware.usb.V1_1.PortStatus_1_1 portStatus_1_1 = (android.hardware.usb.V1_1.PortStatus_1_1) obj;
        if (android.os.HidlSupport.deepEquals(this.status, portStatus_1_1.status) && android.os.HidlSupport.deepEquals(java.lang.Integer.valueOf(this.supportedModes), java.lang.Integer.valueOf(portStatus_1_1.supportedModes)) && this.currentMode == portStatus_1_1.currentMode) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.status)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.supportedModes))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.currentMode))));
    }

    public final java.lang.String toString() {
        return "{.status = " + this.status + ", .supportedModes = " + android.hardware.usb.V1_1.PortMode_1_1.dumpBitfield(this.supportedModes) + ", .currentMode = " + android.hardware.usb.V1_1.PortMode_1_1.toString(this.currentMode) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(48L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.usb.V1_1.PortStatus_1_1> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.usb.V1_1.PortStatus_1_1> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 48, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.usb.V1_1.PortStatus_1_1 portStatus_1_1 = new android.hardware.usb.V1_1.PortStatus_1_1();
            portStatus_1_1.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 48);
            arrayList.add(portStatus_1_1);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.status.readEmbeddedFromParcel(hwParcel, hwBlob, 0 + j);
        this.supportedModes = hwBlob.getInt32(40 + j);
        this.currentMode = hwBlob.getInt32(j + 44);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(48);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.usb.V1_1.PortStatus_1_1> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 48);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 48);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        this.status.writeEmbeddedToBlob(hwBlob, 0 + j);
        hwBlob.putInt32(40 + j, this.supportedModes);
        hwBlob.putInt32(j + 44, this.currentMode);
    }
}
