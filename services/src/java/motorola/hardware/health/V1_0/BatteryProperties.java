package motorola.hardware.health.V1_0;

/* loaded from: classes3.dex */
public final class BatteryProperties {
    public int modLevel = 0;
    public int modStatus = 0;
    public int modFlag = 0;
    public int modType = 0;
    public int modPowerSource = 0;
    public int batteryLevel = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != motorola.hardware.health.V1_0.BatteryProperties.class) {
            return false;
        }
        motorola.hardware.health.V1_0.BatteryProperties batteryProperties = (motorola.hardware.health.V1_0.BatteryProperties) obj;
        if (this.modLevel == batteryProperties.modLevel && this.modStatus == batteryProperties.modStatus && this.modFlag == batteryProperties.modFlag && this.modType == batteryProperties.modType && this.modPowerSource == batteryProperties.modPowerSource && this.batteryLevel == batteryProperties.batteryLevel) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.modLevel))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.modStatus))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.modFlag))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.modType))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.modPowerSource))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.batteryLevel))));
    }

    public final java.lang.String toString() {
        return "{.modLevel = " + this.modLevel + ", .modStatus = " + this.modStatus + ", .modFlag = " + this.modFlag + ", .modType = " + this.modType + ", .modPowerSource = " + this.modPowerSource + ", .batteryLevel = " + this.batteryLevel + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public static final java.util.ArrayList<motorola.hardware.health.V1_0.BatteryProperties> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<motorola.hardware.health.V1_0.BatteryProperties> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            motorola.hardware.health.V1_0.BatteryProperties batteryProperties = new motorola.hardware.health.V1_0.BatteryProperties();
            batteryProperties.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 24);
            arrayList.add(batteryProperties);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.modLevel = hwBlob.getInt32(0 + j);
        this.modStatus = hwBlob.getInt32(4 + j);
        this.modFlag = hwBlob.getInt32(8 + j);
        this.modType = hwBlob.getInt32(12 + j);
        this.modPowerSource = hwBlob.getInt32(16 + j);
        this.batteryLevel = hwBlob.getInt32(j + 20);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(24);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<motorola.hardware.health.V1_0.BatteryProperties> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 24);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 24);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(0 + j, this.modLevel);
        hwBlob.putInt32(4 + j, this.modStatus);
        hwBlob.putInt32(8 + j, this.modFlag);
        hwBlob.putInt32(12 + j, this.modType);
        hwBlob.putInt32(16 + j, this.modPowerSource);
        hwBlob.putInt32(j + 20, this.batteryLevel);
    }
}
