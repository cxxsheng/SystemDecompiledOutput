package android.hardware.audio.common.V2_0;

/* loaded from: classes.dex */
public final class AudioPortDeviceExt {
    public int hwModule = 0;
    public int type = 0;
    public byte[] address = new byte[32];

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.audio.common.V2_0.AudioPortDeviceExt.class) {
            return false;
        }
        android.hardware.audio.common.V2_0.AudioPortDeviceExt audioPortDeviceExt = (android.hardware.audio.common.V2_0.AudioPortDeviceExt) obj;
        if (this.hwModule == audioPortDeviceExt.hwModule && this.type == audioPortDeviceExt.type && android.os.HidlSupport.deepEquals(this.address, audioPortDeviceExt.address)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.hwModule))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.type))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.address)));
    }

    public final java.lang.String toString() {
        return "{.hwModule = " + this.hwModule + ", .type = " + android.hardware.audio.common.V2_0.AudioDevice.toString(this.type) + ", .address = " + java.util.Arrays.toString(this.address) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.audio.common.V2_0.AudioPortDeviceExt> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.audio.common.V2_0.AudioPortDeviceExt> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.audio.common.V2_0.AudioPortDeviceExt audioPortDeviceExt = new android.hardware.audio.common.V2_0.AudioPortDeviceExt();
            audioPortDeviceExt.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 40);
            arrayList.add(audioPortDeviceExt);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.hwModule = hwBlob.getInt32(0 + j);
        this.type = hwBlob.getInt32(4 + j);
        hwBlob.copyToInt8Array(j + 8, this.address, 32);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(40);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.audio.common.V2_0.AudioPortDeviceExt> arrayList) {
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
        hwBlob.putInt32(0 + j, this.hwModule);
        hwBlob.putInt32(4 + j, this.type);
        long j2 = j + 8;
        byte[] bArr = this.address;
        if (bArr == null || bArr.length != 32) {
            throw new java.lang.IllegalArgumentException("Array element is not of the expected length");
        }
        hwBlob.putInt8Array(j2, bArr);
    }
}
