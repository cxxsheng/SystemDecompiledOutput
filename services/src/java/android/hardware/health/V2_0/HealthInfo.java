package android.hardware.health.V2_0;

/* loaded from: classes.dex */
public final class HealthInfo {
    public android.hardware.health.V1_0.HealthInfo legacy = new android.hardware.health.V1_0.HealthInfo();
    public int batteryCurrentAverage = 0;
    public java.util.ArrayList<android.hardware.health.V2_0.DiskStats> diskStats = new java.util.ArrayList<>();
    public java.util.ArrayList<android.hardware.health.V2_0.StorageInfo> storageInfos = new java.util.ArrayList<>();

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.health.V2_0.HealthInfo.class) {
            return false;
        }
        android.hardware.health.V2_0.HealthInfo healthInfo = (android.hardware.health.V2_0.HealthInfo) obj;
        if (android.os.HidlSupport.deepEquals(this.legacy, healthInfo.legacy) && this.batteryCurrentAverage == healthInfo.batteryCurrentAverage && android.os.HidlSupport.deepEquals(this.diskStats, healthInfo.diskStats) && android.os.HidlSupport.deepEquals(this.storageInfos, healthInfo.storageInfos)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.legacy)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.batteryCurrentAverage))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.diskStats)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.storageInfos)));
    }

    public final java.lang.String toString() {
        return "{.legacy = " + this.legacy + ", .batteryCurrentAverage = " + this.batteryCurrentAverage + ", .diskStats = " + this.diskStats + ", .storageInfos = " + this.storageInfos + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(112L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.health.V2_0.HealthInfo> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.health.V2_0.HealthInfo> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 112, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.health.V2_0.HealthInfo healthInfo = new android.hardware.health.V2_0.HealthInfo();
            healthInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 112);
            arrayList.add(healthInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.legacy.readEmbeddedFromParcel(hwParcel, hwBlob, j + 0);
        this.batteryCurrentAverage = hwBlob.getInt32(j + 72);
        long j2 = j + 80;
        int int32 = hwBlob.getInt32(j2 + 8);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 112, hwBlob.handle(), j2 + 0, true);
        this.diskStats.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.health.V2_0.DiskStats diskStats = new android.hardware.health.V2_0.DiskStats();
            diskStats.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 112);
            this.diskStats.add(diskStats);
        }
        long j3 = j + 96;
        int int322 = hwBlob.getInt32(8 + j3);
        android.os.HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 48, hwBlob.handle(), j3 + 0, true);
        this.storageInfos.clear();
        for (int i2 = 0; i2 < int322; i2++) {
            android.hardware.health.V2_0.StorageInfo storageInfo = new android.hardware.health.V2_0.StorageInfo();
            storageInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer2, i2 * 48);
            this.storageInfos.add(storageInfo);
        }
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(112);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.health.V2_0.HealthInfo> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 112);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 112);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        this.legacy.writeEmbeddedToBlob(hwBlob, j + 0);
        hwBlob.putInt32(j + 72, this.batteryCurrentAverage);
        int size = this.diskStats.size();
        long j2 = j + 80;
        hwBlob.putInt32(j2 + 8, size);
        hwBlob.putBool(j2 + 12, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 112);
        for (int i = 0; i < size; i++) {
            this.diskStats.get(i).writeEmbeddedToBlob(hwBlob2, i * 112);
        }
        hwBlob.putBlob(j2 + 0, hwBlob2);
        int size2 = this.storageInfos.size();
        long j3 = j + 96;
        hwBlob.putInt32(8 + j3, size2);
        hwBlob.putBool(j3 + 12, false);
        android.os.HwBlob hwBlob3 = new android.os.HwBlob(size2 * 48);
        for (int i2 = 0; i2 < size2; i2++) {
            this.storageInfos.get(i2).writeEmbeddedToBlob(hwBlob3, i2 * 48);
        }
        hwBlob.putBlob(j3 + 0, hwBlob3);
    }
}
