package android.hardware.thermal.V1_0;

/* loaded from: classes2.dex */
public final class CpuUsage {
    public java.lang.String name = new java.lang.String();
    public long active = 0;
    public long total = 0;
    public boolean isOnline = false;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.thermal.V1_0.CpuUsage.class) {
            return false;
        }
        android.hardware.thermal.V1_0.CpuUsage cpuUsage = (android.hardware.thermal.V1_0.CpuUsage) obj;
        if (android.os.HidlSupport.deepEquals(this.name, cpuUsage.name) && this.active == cpuUsage.active && this.total == cpuUsage.total && this.isOnline == cpuUsage.isOnline) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.name)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.active))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.total))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.isOnline))));
    }

    public final java.lang.String toString() {
        return "{.name = " + this.name + ", .active = " + this.active + ", .total = " + this.total + ", .isOnline = " + this.isOnline + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.thermal.V1_0.CpuUsage> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.thermal.V1_0.CpuUsage> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.thermal.V1_0.CpuUsage cpuUsage = new android.hardware.thermal.V1_0.CpuUsage();
            cpuUsage.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 40);
            arrayList.add(cpuUsage);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        long j2 = j + 0;
        this.name = hwBlob.getString(j2);
        hwParcel.readEmbeddedBuffer(this.name.getBytes().length + 1, hwBlob.handle(), j2 + 0, false);
        this.active = hwBlob.getInt64(j + 16);
        this.total = hwBlob.getInt64(j + 24);
        this.isOnline = hwBlob.getBool(j + 32);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(40);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.thermal.V1_0.CpuUsage> arrayList) {
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
        hwBlob.putString(0 + j, this.name);
        hwBlob.putInt64(16 + j, this.active);
        hwBlob.putInt64(24 + j, this.total);
        hwBlob.putBool(j + 32, this.isOnline);
    }
}
