package android.hardware.health.V2_1;

/* loaded from: classes.dex */
public final class HealthInfo {
    public android.hardware.health.V2_0.HealthInfo legacy = new android.hardware.health.V2_0.HealthInfo();
    public int batteryCapacityLevel = 0;
    public long batteryChargeTimeToFullNowSeconds = 0;
    public int batteryFullChargeDesignCapacityUah = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.health.V2_1.HealthInfo.class) {
            return false;
        }
        android.hardware.health.V2_1.HealthInfo healthInfo = (android.hardware.health.V2_1.HealthInfo) obj;
        if (android.os.HidlSupport.deepEquals(this.legacy, healthInfo.legacy) && this.batteryCapacityLevel == healthInfo.batteryCapacityLevel && this.batteryChargeTimeToFullNowSeconds == healthInfo.batteryChargeTimeToFullNowSeconds && this.batteryFullChargeDesignCapacityUah == healthInfo.batteryFullChargeDesignCapacityUah) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.legacy)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.batteryCapacityLevel))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.batteryChargeTimeToFullNowSeconds))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.batteryFullChargeDesignCapacityUah))));
    }

    public final java.lang.String toString() {
        return "{.legacy = " + this.legacy + ", .batteryCapacityLevel = " + android.hardware.health.V2_1.BatteryCapacityLevel.toString(this.batteryCapacityLevel) + ", .batteryChargeTimeToFullNowSeconds = " + this.batteryChargeTimeToFullNowSeconds + ", .batteryFullChargeDesignCapacityUah = " + this.batteryFullChargeDesignCapacityUah + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(136L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.health.V2_1.HealthInfo> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.health.V2_1.HealthInfo> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 136, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.health.V2_1.HealthInfo healthInfo = new android.hardware.health.V2_1.HealthInfo();
            healthInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 136);
            arrayList.add(healthInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.legacy.readEmbeddedFromParcel(hwParcel, hwBlob, 0 + j);
        this.batteryCapacityLevel = hwBlob.getInt32(112 + j);
        this.batteryChargeTimeToFullNowSeconds = hwBlob.getInt64(120 + j);
        this.batteryFullChargeDesignCapacityUah = hwBlob.getInt32(j + 128);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(136);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.health.V2_1.HealthInfo> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 136);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 136);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        this.legacy.writeEmbeddedToBlob(hwBlob, 0 + j);
        hwBlob.putInt32(112 + j, this.batteryCapacityLevel);
        hwBlob.putInt64(120 + j, this.batteryChargeTimeToFullNowSeconds);
        hwBlob.putInt32(j + 128, this.batteryFullChargeDesignCapacityUah);
    }
}
