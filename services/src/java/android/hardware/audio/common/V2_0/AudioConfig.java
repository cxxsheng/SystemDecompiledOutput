package android.hardware.audio.common.V2_0;

/* loaded from: classes.dex */
public final class AudioConfig {
    public int sampleRateHz = 0;
    public int channelMask = 0;
    public int format = 0;
    public android.hardware.audio.common.V2_0.AudioOffloadInfo offloadInfo = new android.hardware.audio.common.V2_0.AudioOffloadInfo();
    public long frameCount = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.audio.common.V2_0.AudioConfig.class) {
            return false;
        }
        android.hardware.audio.common.V2_0.AudioConfig audioConfig = (android.hardware.audio.common.V2_0.AudioConfig) obj;
        if (this.sampleRateHz == audioConfig.sampleRateHz && this.channelMask == audioConfig.channelMask && this.format == audioConfig.format && android.os.HidlSupport.deepEquals(this.offloadInfo, audioConfig.offloadInfo) && this.frameCount == audioConfig.frameCount) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.sampleRateHz))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.channelMask))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.format))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.offloadInfo)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.frameCount))));
    }

    public final java.lang.String toString() {
        return "{.sampleRateHz = " + this.sampleRateHz + ", .channelMask = " + android.hardware.audio.common.V2_0.AudioChannelMask.toString(this.channelMask) + ", .format = " + android.hardware.audio.common.V2_0.AudioFormat.toString(this.format) + ", .offloadInfo = " + this.offloadInfo + ", .frameCount = " + this.frameCount + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(72L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.audio.common.V2_0.AudioConfig> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.audio.common.V2_0.AudioConfig> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 72, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.audio.common.V2_0.AudioConfig audioConfig = new android.hardware.audio.common.V2_0.AudioConfig();
            audioConfig.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 72);
            arrayList.add(audioConfig);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.sampleRateHz = hwBlob.getInt32(0 + j);
        this.channelMask = hwBlob.getInt32(4 + j);
        this.format = hwBlob.getInt32(8 + j);
        this.offloadInfo.readEmbeddedFromParcel(hwParcel, hwBlob, 16 + j);
        this.frameCount = hwBlob.getInt64(j + 64);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(72);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.audio.common.V2_0.AudioConfig> arrayList) {
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
        hwBlob.putInt32(0 + j, this.sampleRateHz);
        hwBlob.putInt32(4 + j, this.channelMask);
        hwBlob.putInt32(8 + j, this.format);
        this.offloadInfo.writeEmbeddedToBlob(hwBlob, 16 + j);
        hwBlob.putInt64(j + 64, this.frameCount);
    }
}
