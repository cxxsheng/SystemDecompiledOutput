package android.hardware.audio.common.V2_0;

/* loaded from: classes.dex */
public final class AudioGainConfig {
    public int index = 0;
    public int mode = 0;
    public int channelMask = 0;
    public int[] values = new int[32];
    public int rampDurationMs = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.audio.common.V2_0.AudioGainConfig.class) {
            return false;
        }
        android.hardware.audio.common.V2_0.AudioGainConfig audioGainConfig = (android.hardware.audio.common.V2_0.AudioGainConfig) obj;
        if (this.index == audioGainConfig.index && this.mode == audioGainConfig.mode && this.channelMask == audioGainConfig.channelMask && android.os.HidlSupport.deepEquals(this.values, audioGainConfig.values) && this.rampDurationMs == audioGainConfig.rampDurationMs) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.index))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.mode))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.channelMask))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(this.values)), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.rampDurationMs))));
    }

    public final java.lang.String toString() {
        return "{.index = " + this.index + ", .mode = " + android.hardware.audio.common.V2_0.AudioGainMode.toString(this.mode) + ", .channelMask = " + android.hardware.audio.common.V2_0.AudioChannelMask.toString(this.channelMask) + ", .values = " + java.util.Arrays.toString(this.values) + ", .rampDurationMs = " + this.rampDurationMs + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(144L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.audio.common.V2_0.AudioGainConfig> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.audio.common.V2_0.AudioGainConfig> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 144, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.audio.common.V2_0.AudioGainConfig audioGainConfig = new android.hardware.audio.common.V2_0.AudioGainConfig();
            audioGainConfig.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 144);
            arrayList.add(audioGainConfig);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.index = hwBlob.getInt32(0 + j);
        this.mode = hwBlob.getInt32(4 + j);
        this.channelMask = hwBlob.getInt32(8 + j);
        hwBlob.copyToInt32Array(12 + j, this.values, 32);
        this.rampDurationMs = hwBlob.getInt32(j + 140);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(144);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.audio.common.V2_0.AudioGainConfig> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 144);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 144);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(0 + j, this.index);
        hwBlob.putInt32(4 + j, this.mode);
        hwBlob.putInt32(8 + j, this.channelMask);
        long j2 = 12 + j;
        int[] iArr = this.values;
        if (iArr == null || iArr.length != 32) {
            throw new java.lang.IllegalArgumentException("Array element is not of the expected length");
        }
        hwBlob.putInt32Array(j2, iArr);
        hwBlob.putInt32(j + 140, this.rampDurationMs);
    }
}
