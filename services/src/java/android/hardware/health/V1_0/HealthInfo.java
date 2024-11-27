package android.hardware.health.V1_0;

/* loaded from: classes.dex */
public final class HealthInfo {
    public boolean chargerAcOnline = false;
    public boolean chargerUsbOnline = false;
    public boolean chargerWirelessOnline = false;
    public int maxChargingCurrent = 0;
    public int maxChargingVoltage = 0;
    public int batteryStatus = 0;
    public int batteryHealth = 0;
    public boolean batteryPresent = false;
    public int batteryLevel = 0;
    public int batteryVoltage = 0;
    public int batteryTemperature = 0;
    public int batteryCurrent = 0;
    public int batteryCycleCount = 0;
    public int batteryFullCharge = 0;
    public int batteryChargeCounter = 0;
    public java.lang.String batteryTechnology = new java.lang.String();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.health.V1_0.HealthInfo.class) {
            return false;
        }
        android.hardware.health.V1_0.HealthInfo healthInfo = (android.hardware.health.V1_0.HealthInfo) obj;
        if (this.chargerAcOnline == healthInfo.chargerAcOnline && this.chargerUsbOnline == healthInfo.chargerUsbOnline && this.chargerWirelessOnline == healthInfo.chargerWirelessOnline && this.maxChargingCurrent == healthInfo.maxChargingCurrent && this.maxChargingVoltage == healthInfo.maxChargingVoltage && this.batteryStatus == healthInfo.batteryStatus && this.batteryHealth == healthInfo.batteryHealth && this.batteryPresent == healthInfo.batteryPresent && this.batteryLevel == healthInfo.batteryLevel && this.batteryVoltage == healthInfo.batteryVoltage && this.batteryTemperature == healthInfo.batteryTemperature && this.batteryCurrent == healthInfo.batteryCurrent && this.batteryCycleCount == healthInfo.batteryCycleCount && this.batteryFullCharge == healthInfo.batteryFullCharge && this.batteryChargeCounter == healthInfo.batteryChargeCounter && android.os.HidlSupport.deepEquals(this.batteryTechnology, healthInfo.batteryTechnology)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.chargerAcOnline))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.chargerUsbOnline))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.chargerWirelessOnline))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.maxChargingCurrent))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.maxChargingVoltage))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.batteryStatus))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.batteryHealth))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.batteryPresent))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.batteryLevel))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.batteryVoltage))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.batteryTemperature))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.batteryCurrent))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.batteryCycleCount))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.batteryFullCharge))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.batteryChargeCounter))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.batteryTechnology)));
    }

    public final java.lang.String toString() {
        return "{.chargerAcOnline = " + this.chargerAcOnline + ", .chargerUsbOnline = " + this.chargerUsbOnline + ", .chargerWirelessOnline = " + this.chargerWirelessOnline + ", .maxChargingCurrent = " + this.maxChargingCurrent + ", .maxChargingVoltage = " + this.maxChargingVoltage + ", .batteryStatus = " + android.hardware.health.V1_0.BatteryStatus.toString(this.batteryStatus) + ", .batteryHealth = " + android.hardware.health.V1_0.BatteryHealth.toString(this.batteryHealth) + ", .batteryPresent = " + this.batteryPresent + ", .batteryLevel = " + this.batteryLevel + ", .batteryVoltage = " + this.batteryVoltage + ", .batteryTemperature = " + this.batteryTemperature + ", .batteryCurrent = " + this.batteryCurrent + ", .batteryCycleCount = " + this.batteryCycleCount + ", .batteryFullCharge = " + this.batteryFullCharge + ", .batteryChargeCounter = " + this.batteryChargeCounter + ", .batteryTechnology = " + this.batteryTechnology + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(72L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.health.V1_0.HealthInfo> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.health.V1_0.HealthInfo> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 72, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.health.V1_0.HealthInfo healthInfo = new android.hardware.health.V1_0.HealthInfo();
            healthInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 72);
            arrayList.add(healthInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.chargerAcOnline = hwBlob.getBool(j + 0);
        this.chargerUsbOnline = hwBlob.getBool(1 + j);
        this.chargerWirelessOnline = hwBlob.getBool(2 + j);
        this.maxChargingCurrent = hwBlob.getInt32(4 + j);
        this.maxChargingVoltage = hwBlob.getInt32(8 + j);
        this.batteryStatus = hwBlob.getInt32(12 + j);
        this.batteryHealth = hwBlob.getInt32(16 + j);
        this.batteryPresent = hwBlob.getBool(20 + j);
        this.batteryLevel = hwBlob.getInt32(24 + j);
        this.batteryVoltage = hwBlob.getInt32(28 + j);
        this.batteryTemperature = hwBlob.getInt32(32 + j);
        this.batteryCurrent = hwBlob.getInt32(36 + j);
        this.batteryCycleCount = hwBlob.getInt32(40 + j);
        this.batteryFullCharge = hwBlob.getInt32(44 + j);
        this.batteryChargeCounter = hwBlob.getInt32(48 + j);
        long j2 = j + 56;
        this.batteryTechnology = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.batteryTechnology.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(72);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.health.V1_0.HealthInfo> arrayList) {
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
        hwBlob.putBool(0 + j, this.chargerAcOnline);
        hwBlob.putBool(1 + j, this.chargerUsbOnline);
        hwBlob.putBool(2 + j, this.chargerWirelessOnline);
        hwBlob.putInt32(4 + j, this.maxChargingCurrent);
        hwBlob.putInt32(8 + j, this.maxChargingVoltage);
        hwBlob.putInt32(12 + j, this.batteryStatus);
        hwBlob.putInt32(16 + j, this.batteryHealth);
        hwBlob.putBool(20 + j, this.batteryPresent);
        hwBlob.putInt32(24 + j, this.batteryLevel);
        hwBlob.putInt32(28 + j, this.batteryVoltage);
        hwBlob.putInt32(32 + j, this.batteryTemperature);
        hwBlob.putInt32(36 + j, this.batteryCurrent);
        hwBlob.putInt32(40 + j, this.batteryCycleCount);
        hwBlob.putInt32(44 + j, this.batteryFullCharge);
        hwBlob.putInt32(48 + j, this.batteryChargeCounter);
        hwBlob.putString(j + 56, this.batteryTechnology);
    }
}
