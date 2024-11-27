package android.hardware.audio.common.V2_0;

/* loaded from: classes.dex */
public final class AudioGain {
    public int mode = 0;
    public int channelMask = 0;
    public int minValue = 0;
    public int maxValue = 0;
    public int defaultValue = 0;
    public int stepValue = 0;
    public int minRampMs = 0;
    public int maxRampMs = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.audio.common.V2_0.AudioGain.class) {
            return false;
        }
        android.hardware.audio.common.V2_0.AudioGain audioGain = (android.hardware.audio.common.V2_0.AudioGain) obj;
        if (this.mode == audioGain.mode && this.channelMask == audioGain.channelMask && this.minValue == audioGain.minValue && this.maxValue == audioGain.maxValue && this.defaultValue == audioGain.defaultValue && this.stepValue == audioGain.stepValue && this.minRampMs == audioGain.minRampMs && this.maxRampMs == audioGain.maxRampMs) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.mode))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.channelMask))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.minValue))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.maxValue))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.defaultValue))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.stepValue))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.minRampMs))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.maxRampMs))));
    }

    public final java.lang.String toString() {
        return "{.mode = " + android.hardware.audio.common.V2_0.AudioGainMode.toString(this.mode) + ", .channelMask = " + android.hardware.audio.common.V2_0.AudioChannelMask.toString(this.channelMask) + ", .minValue = " + this.minValue + ", .maxValue = " + this.maxValue + ", .defaultValue = " + this.defaultValue + ", .stepValue = " + this.stepValue + ", .minRampMs = " + this.minRampMs + ", .maxRampMs = " + this.maxRampMs + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.audio.common.V2_0.AudioGain> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.audio.common.V2_0.AudioGain> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.audio.common.V2_0.AudioGain audioGain = new android.hardware.audio.common.V2_0.AudioGain();
            audioGain.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 32);
            arrayList.add(audioGain);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.mode = hwBlob.getInt32(0 + j);
        this.channelMask = hwBlob.getInt32(4 + j);
        this.minValue = hwBlob.getInt32(8 + j);
        this.maxValue = hwBlob.getInt32(12 + j);
        this.defaultValue = hwBlob.getInt32(16 + j);
        this.stepValue = hwBlob.getInt32(20 + j);
        this.minRampMs = hwBlob.getInt32(24 + j);
        this.maxRampMs = hwBlob.getInt32(j + 28);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(32);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.audio.common.V2_0.AudioGain> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 32);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 32);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(0 + j, this.mode);
        hwBlob.putInt32(4 + j, this.channelMask);
        hwBlob.putInt32(8 + j, this.minValue);
        hwBlob.putInt32(12 + j, this.maxValue);
        hwBlob.putInt32(16 + j, this.defaultValue);
        hwBlob.putInt32(20 + j, this.stepValue);
        hwBlob.putInt32(24 + j, this.minRampMs);
        hwBlob.putInt32(j + 28, this.maxRampMs);
    }
}
