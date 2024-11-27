package android.hardware.usb.V1_2;

/* loaded from: classes.dex */
public final class PortStatus {
    public int supportedContaminantProtectionModes;
    public android.hardware.usb.V1_1.PortStatus_1_1 status_1_1 = new android.hardware.usb.V1_1.PortStatus_1_1();
    public boolean supportsEnableContaminantPresenceProtection = false;
    public int contaminantProtectionStatus = 0;
    public boolean supportsEnableContaminantPresenceDetection = false;
    public int contaminantDetectionStatus = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.usb.V1_2.PortStatus.class) {
            return false;
        }
        android.hardware.usb.V1_2.PortStatus portStatus = (android.hardware.usb.V1_2.PortStatus) obj;
        if (android.os.HidlSupport.deepEquals(this.status_1_1, portStatus.status_1_1) && android.os.HidlSupport.deepEquals(java.lang.Integer.valueOf(this.supportedContaminantProtectionModes), java.lang.Integer.valueOf(portStatus.supportedContaminantProtectionModes)) && this.supportsEnableContaminantPresenceProtection == portStatus.supportsEnableContaminantPresenceProtection && this.contaminantProtectionStatus == portStatus.contaminantProtectionStatus && this.supportsEnableContaminantPresenceDetection == portStatus.supportsEnableContaminantPresenceDetection && this.contaminantDetectionStatus == portStatus.contaminantDetectionStatus) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.status_1_1)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.supportedContaminantProtectionModes))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.supportsEnableContaminantPresenceProtection))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.contaminantProtectionStatus))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.supportsEnableContaminantPresenceDetection))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.contaminantDetectionStatus))));
    }

    public final java.lang.String toString() {
        return "{.status_1_1 = " + this.status_1_1 + ", .supportedContaminantProtectionModes = " + android.hardware.usb.V1_2.ContaminantProtectionMode.dumpBitfield(this.supportedContaminantProtectionModes) + ", .supportsEnableContaminantPresenceProtection = " + this.supportsEnableContaminantPresenceProtection + ", .contaminantProtectionStatus = " + android.hardware.usb.V1_2.ContaminantProtectionStatus.toString(this.contaminantProtectionStatus) + ", .supportsEnableContaminantPresenceDetection = " + this.supportsEnableContaminantPresenceDetection + ", .contaminantDetectionStatus = " + android.hardware.usb.V1_2.ContaminantDetectionStatus.toString(this.contaminantDetectionStatus) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(72L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.usb.V1_2.PortStatus> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.usb.V1_2.PortStatus> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 72, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.usb.V1_2.PortStatus portStatus = new android.hardware.usb.V1_2.PortStatus();
            portStatus.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 72);
            arrayList.add(portStatus);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.status_1_1.readEmbeddedFromParcel(hwParcel, hwBlob, 0 + j);
        this.supportedContaminantProtectionModes = hwBlob.getInt32(48 + j);
        this.supportsEnableContaminantPresenceProtection = hwBlob.getBool(52 + j);
        this.contaminantProtectionStatus = hwBlob.getInt32(56 + j);
        this.supportsEnableContaminantPresenceDetection = hwBlob.getBool(60 + j);
        this.contaminantDetectionStatus = hwBlob.getInt32(j + 64);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(72);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.usb.V1_2.PortStatus> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 72);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 72);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        this.status_1_1.writeEmbeddedToBlob(hwBlob, 0 + j);
        hwBlob.putInt32(48 + j, this.supportedContaminantProtectionModes);
        hwBlob.putBool(52 + j, this.supportsEnableContaminantPresenceProtection);
        hwBlob.putInt32(56 + j, this.contaminantProtectionStatus);
        hwBlob.putBool(60 + j, this.supportsEnableContaminantPresenceDetection);
        hwBlob.putInt32(j + 64, this.contaminantDetectionStatus);
    }
}
