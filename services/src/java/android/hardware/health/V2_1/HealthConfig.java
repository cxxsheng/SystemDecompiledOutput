package android.hardware.health.V2_1;

/* loaded from: classes.dex */
public final class HealthConfig {
    public android.hardware.health.V1_0.HealthConfig battery = new android.hardware.health.V1_0.HealthConfig();
    public int bootMinCap = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.health.V2_1.HealthConfig.class) {
            return false;
        }
        android.hardware.health.V2_1.HealthConfig healthConfig = (android.hardware.health.V2_1.HealthConfig) obj;
        if (android.os.HidlSupport.deepEquals(this.battery, healthConfig.battery) && this.bootMinCap == healthConfig.bootMinCap) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.battery)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.bootMinCap))));
    }

    public final java.lang.String toString() {
        return "{.battery = " + this.battery + ", .bootMinCap = " + this.bootMinCap + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(208L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.health.V2_1.HealthConfig> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.health.V2_1.HealthConfig> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 208, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.health.V2_1.HealthConfig healthConfig = new android.hardware.health.V2_1.HealthConfig();
            healthConfig.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 208);
            arrayList.add(healthConfig);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.battery.readEmbeddedFromParcel(hwParcel, hwBlob, 0 + j);
        this.bootMinCap = hwBlob.getInt32(j + 200);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(208);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.health.V2_1.HealthConfig> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 208);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 208);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        this.battery.writeEmbeddedToBlob(hwBlob, 0 + j);
        hwBlob.putInt32(j + 200, this.bootMinCap);
    }
}
