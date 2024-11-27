package android.hardware.audio.common.V2_0;

/* loaded from: classes.dex */
public final class AudioPortMixExt {
    public int hwModule = 0;
    public int ioHandle = 0;
    public int latencyClass = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.audio.common.V2_0.AudioPortMixExt.class) {
            return false;
        }
        android.hardware.audio.common.V2_0.AudioPortMixExt audioPortMixExt = (android.hardware.audio.common.V2_0.AudioPortMixExt) obj;
        if (this.hwModule == audioPortMixExt.hwModule && this.ioHandle == audioPortMixExt.ioHandle && this.latencyClass == audioPortMixExt.latencyClass) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.hwModule))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.ioHandle))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.latencyClass))));
    }

    public final java.lang.String toString() {
        return "{.hwModule = " + this.hwModule + ", .ioHandle = " + this.ioHandle + ", .latencyClass = " + android.hardware.audio.common.V2_0.AudioMixLatencyClass.toString(this.latencyClass) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(12L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.audio.common.V2_0.AudioPortMixExt> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.audio.common.V2_0.AudioPortMixExt> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 12, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.audio.common.V2_0.AudioPortMixExt audioPortMixExt = new android.hardware.audio.common.V2_0.AudioPortMixExt();
            audioPortMixExt.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 12);
            arrayList.add(audioPortMixExt);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.hwModule = hwBlob.getInt32(0 + j);
        this.ioHandle = hwBlob.getInt32(4 + j);
        this.latencyClass = hwBlob.getInt32(j + 8);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(12);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.audio.common.V2_0.AudioPortMixExt> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 12);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 12);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(0 + j, this.hwModule);
        hwBlob.putInt32(4 + j, this.ioHandle);
        hwBlob.putInt32(j + 8, this.latencyClass);
    }
}
