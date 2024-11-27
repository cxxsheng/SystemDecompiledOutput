package android.hardware.health.V1_0;

/* loaded from: classes.dex */
public final class HealthConfig {
    public int periodicChoresIntervalFast = 0;
    public int periodicChoresIntervalSlow = 0;
    public java.lang.String batteryStatusPath = new java.lang.String();
    public java.lang.String batteryHealthPath = new java.lang.String();
    public java.lang.String batteryPresentPath = new java.lang.String();
    public java.lang.String batteryCapacityPath = new java.lang.String();
    public java.lang.String batteryVoltagePath = new java.lang.String();
    public java.lang.String batteryTemperaturePath = new java.lang.String();
    public java.lang.String batteryTechnologyPath = new java.lang.String();
    public java.lang.String batteryCurrentNowPath = new java.lang.String();
    public java.lang.String batteryCurrentAvgPath = new java.lang.String();
    public java.lang.String batteryChargeCounterPath = new java.lang.String();
    public java.lang.String batteryFullChargePath = new java.lang.String();
    public java.lang.String batteryCycleCountPath = new java.lang.String();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.health.V1_0.HealthConfig.class) {
            return false;
        }
        android.hardware.health.V1_0.HealthConfig healthConfig = (android.hardware.health.V1_0.HealthConfig) obj;
        if (this.periodicChoresIntervalFast == healthConfig.periodicChoresIntervalFast && this.periodicChoresIntervalSlow == healthConfig.periodicChoresIntervalSlow && android.os.HidlSupport.deepEquals(this.batteryStatusPath, healthConfig.batteryStatusPath) && android.os.HidlSupport.deepEquals(this.batteryHealthPath, healthConfig.batteryHealthPath) && android.os.HidlSupport.deepEquals(this.batteryPresentPath, healthConfig.batteryPresentPath) && android.os.HidlSupport.deepEquals(this.batteryCapacityPath, healthConfig.batteryCapacityPath) && android.os.HidlSupport.deepEquals(this.batteryVoltagePath, healthConfig.batteryVoltagePath) && android.os.HidlSupport.deepEquals(this.batteryTemperaturePath, healthConfig.batteryTemperaturePath) && android.os.HidlSupport.deepEquals(this.batteryTechnologyPath, healthConfig.batteryTechnologyPath) && android.os.HidlSupport.deepEquals(this.batteryCurrentNowPath, healthConfig.batteryCurrentNowPath) && android.os.HidlSupport.deepEquals(this.batteryCurrentAvgPath, healthConfig.batteryCurrentAvgPath) && android.os.HidlSupport.deepEquals(this.batteryChargeCounterPath, healthConfig.batteryChargeCounterPath) && android.os.HidlSupport.deepEquals(this.batteryFullChargePath, healthConfig.batteryFullChargePath) && android.os.HidlSupport.deepEquals(this.batteryCycleCountPath, healthConfig.batteryCycleCountPath)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.periodicChoresIntervalFast))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.periodicChoresIntervalSlow))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.batteryStatusPath)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.batteryHealthPath)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.batteryPresentPath)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.batteryCapacityPath)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.batteryVoltagePath)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.batteryTemperaturePath)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.batteryTechnologyPath)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.batteryCurrentNowPath)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.batteryCurrentAvgPath)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.batteryChargeCounterPath)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.batteryFullChargePath)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.batteryCycleCountPath)));
    }

    public final java.lang.String toString() {
        return "{.periodicChoresIntervalFast = " + this.periodicChoresIntervalFast + ", .periodicChoresIntervalSlow = " + this.periodicChoresIntervalSlow + ", .batteryStatusPath = " + this.batteryStatusPath + ", .batteryHealthPath = " + this.batteryHealthPath + ", .batteryPresentPath = " + this.batteryPresentPath + ", .batteryCapacityPath = " + this.batteryCapacityPath + ", .batteryVoltagePath = " + this.batteryVoltagePath + ", .batteryTemperaturePath = " + this.batteryTemperaturePath + ", .batteryTechnologyPath = " + this.batteryTechnologyPath + ", .batteryCurrentNowPath = " + this.batteryCurrentNowPath + ", .batteryCurrentAvgPath = " + this.batteryCurrentAvgPath + ", .batteryChargeCounterPath = " + this.batteryChargeCounterPath + ", .batteryFullChargePath = " + this.batteryFullChargePath + ", .batteryCycleCountPath = " + this.batteryCycleCountPath + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(200L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.health.V1_0.HealthConfig> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.health.V1_0.HealthConfig> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 200, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.health.V1_0.HealthConfig healthConfig = new android.hardware.health.V1_0.HealthConfig();
            healthConfig.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 200);
            arrayList.add(healthConfig);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.periodicChoresIntervalFast = hwBlob.getInt32(j + 0);
        this.periodicChoresIntervalSlow = hwBlob.getInt32(j + 4);
        long j2 = j + 8;
        this.batteryStatusPath = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.batteryStatusPath.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        long j3 = j + 24;
        this.batteryHealthPath = hwBlob.getString(j3);
        hwParcel.readEmbeddedBuffer(this.batteryHealthPath.getBytes().length + 1, hwBlob.handle(), j3 + 0, false);
        long j4 = j + 40;
        this.batteryPresentPath = hwBlob.getString(j4);
        hwParcel.readEmbeddedBuffer(this.batteryPresentPath.getBytes().length + 1, hwBlob.handle(), j4 + 0, false);
        long j5 = j + 56;
        this.batteryCapacityPath = hwBlob.getString(j5);
        hwParcel.readEmbeddedBuffer(this.batteryCapacityPath.getBytes().length + 1, hwBlob.handle(), j5 + 0, false);
        long j6 = j + 72;
        this.batteryVoltagePath = hwBlob.getString(j6);
        hwParcel.readEmbeddedBuffer(this.batteryVoltagePath.getBytes().length + 1, hwBlob.handle(), j6 + 0, false);
        long j7 = j + 88;
        this.batteryTemperaturePath = hwBlob.getString(j7);
        hwParcel.readEmbeddedBuffer(this.batteryTemperaturePath.getBytes().length + 1, hwBlob.handle(), j7 + 0, false);
        long j8 = j + 104;
        this.batteryTechnologyPath = hwBlob.getString(j8);
        hwParcel.readEmbeddedBuffer(this.batteryTechnologyPath.getBytes().length + 1, hwBlob.handle(), j8 + 0, false);
        long j9 = j + 120;
        this.batteryCurrentNowPath = hwBlob.getString(j9);
        hwParcel.readEmbeddedBuffer(this.batteryCurrentNowPath.getBytes().length + 1, hwBlob.handle(), j9 + 0, false);
        long j10 = j + 136;
        this.batteryCurrentAvgPath = hwBlob.getString(j10);
        hwParcel.readEmbeddedBuffer(this.batteryCurrentAvgPath.getBytes().length + 1, hwBlob.handle(), j10 + 0, false);
        long j11 = j + 152;
        this.batteryChargeCounterPath = hwBlob.getString(j11);
        hwParcel.readEmbeddedBuffer(this.batteryChargeCounterPath.getBytes().length + 1, hwBlob.handle(), j11 + 0, false);
        long j12 = j + 168;
        this.batteryFullChargePath = hwBlob.getString(j12);
        hwParcel.readEmbeddedBuffer(this.batteryFullChargePath.getBytes().length + 1, hwBlob.handle(), j12 + 0, false);
        long j13 = j + 184;
        this.batteryCycleCountPath = hwBlob.getString(j13);
        hwParcel.readEmbeddedBuffer(this.batteryCycleCountPath.getBytes().length + 1, hwBlob.handle(), j13 + 0, false);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(200);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.health.V1_0.HealthConfig> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 200);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 200);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(0 + j, this.periodicChoresIntervalFast);
        hwBlob.putInt32(4 + j, this.periodicChoresIntervalSlow);
        hwBlob.putString(8 + j, this.batteryStatusPath);
        hwBlob.putString(24 + j, this.batteryHealthPath);
        hwBlob.putString(40 + j, this.batteryPresentPath);
        hwBlob.putString(56 + j, this.batteryCapacityPath);
        hwBlob.putString(72 + j, this.batteryVoltagePath);
        hwBlob.putString(88 + j, this.batteryTemperaturePath);
        hwBlob.putString(104 + j, this.batteryTechnologyPath);
        hwBlob.putString(120 + j, this.batteryCurrentNowPath);
        hwBlob.putString(136 + j, this.batteryCurrentAvgPath);
        hwBlob.putString(152 + j, this.batteryChargeCounterPath);
        hwBlob.putString(168 + j, this.batteryFullChargePath);
        hwBlob.putString(j + 184, this.batteryCycleCountPath);
    }
}
