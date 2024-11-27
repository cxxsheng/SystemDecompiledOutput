package android.hardware.audio.common.V2_0;

/* loaded from: classes.dex */
public final class AudioOffloadInfo {
    public int sampleRateHz = 0;
    public int channelMask = 0;
    public int format = 0;
    public int streamType = 0;
    public int bitRatePerSecond = 0;
    public long durationMicroseconds = 0;
    public boolean hasVideo = false;
    public boolean isStreaming = false;
    public int bitWidth = 0;
    public int bufferSize = 0;
    public int usage = 0;

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != android.hardware.audio.common.V2_0.AudioOffloadInfo.class) {
            return false;
        }
        android.hardware.audio.common.V2_0.AudioOffloadInfo audioOffloadInfo = (android.hardware.audio.common.V2_0.AudioOffloadInfo) obj;
        if (this.sampleRateHz == audioOffloadInfo.sampleRateHz && this.channelMask == audioOffloadInfo.channelMask && this.format == audioOffloadInfo.format && this.streamType == audioOffloadInfo.streamType && this.bitRatePerSecond == audioOffloadInfo.bitRatePerSecond && this.durationMicroseconds == audioOffloadInfo.durationMicroseconds && this.hasVideo == audioOffloadInfo.hasVideo && this.isStreaming == audioOffloadInfo.isStreaming && this.bitWidth == audioOffloadInfo.bitWidth && this.bufferSize == audioOffloadInfo.bufferSize && this.usage == audioOffloadInfo.usage) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.sampleRateHz))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.channelMask))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.format))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.streamType))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.bitRatePerSecond))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Long.valueOf(this.durationMicroseconds))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.hasVideo))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Boolean.valueOf(this.isStreaming))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.bitWidth))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.bufferSize))), java.lang.Integer.valueOf(android.os.HidlSupport.deepHashCode(java.lang.Integer.valueOf(this.usage))));
    }

    public final java.lang.String toString() {
        return "{.sampleRateHz = " + this.sampleRateHz + ", .channelMask = " + android.hardware.audio.common.V2_0.AudioChannelMask.toString(this.channelMask) + ", .format = " + android.hardware.audio.common.V2_0.AudioFormat.toString(this.format) + ", .streamType = " + android.hardware.audio.common.V2_0.AudioStreamType.toString(this.streamType) + ", .bitRatePerSecond = " + this.bitRatePerSecond + ", .durationMicroseconds = " + this.durationMicroseconds + ", .hasVideo = " + this.hasVideo + ", .isStreaming = " + this.isStreaming + ", .bitWidth = " + this.bitWidth + ", .bufferSize = " + this.bufferSize + ", .usage = " + android.hardware.audio.common.V2_0.AudioUsage.toString(this.usage) + "}";
    }

    public final void readFromParcel(android.os.HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(48L), 0L);
    }

    public static final java.util.ArrayList<android.hardware.audio.common.V2_0.AudioOffloadInfo> readVectorFromParcel(android.os.HwParcel hwParcel) {
        java.util.ArrayList<android.hardware.audio.common.V2_0.AudioOffloadInfo> arrayList = new java.util.ArrayList<>();
        android.os.HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        android.os.HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 48, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            android.hardware.audio.common.V2_0.AudioOffloadInfo audioOffloadInfo = new android.hardware.audio.common.V2_0.AudioOffloadInfo();
            audioOffloadInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 48);
            arrayList.add(audioOffloadInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(android.os.HwParcel hwParcel, android.os.HwBlob hwBlob, long j) {
        this.sampleRateHz = hwBlob.getInt32(0 + j);
        this.channelMask = hwBlob.getInt32(4 + j);
        this.format = hwBlob.getInt32(8 + j);
        this.streamType = hwBlob.getInt32(12 + j);
        this.bitRatePerSecond = hwBlob.getInt32(16 + j);
        this.durationMicroseconds = hwBlob.getInt64(24 + j);
        this.hasVideo = hwBlob.getBool(32 + j);
        this.isStreaming = hwBlob.getBool(33 + j);
        this.bitWidth = hwBlob.getInt32(36 + j);
        this.bufferSize = hwBlob.getInt32(40 + j);
        this.usage = hwBlob.getInt32(j + 44);
    }

    public final void writeToParcel(android.os.HwParcel hwParcel) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(48);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(android.os.HwParcel hwParcel, java.util.ArrayList<android.hardware.audio.common.V2_0.AudioOffloadInfo> arrayList) {
        android.os.HwBlob hwBlob = new android.os.HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 48);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 48);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(android.os.HwBlob hwBlob, long j) {
        hwBlob.putInt32(0 + j, this.sampleRateHz);
        hwBlob.putInt32(4 + j, this.channelMask);
        hwBlob.putInt32(8 + j, this.format);
        hwBlob.putInt32(12 + j, this.streamType);
        hwBlob.putInt32(16 + j, this.bitRatePerSecond);
        hwBlob.putInt64(24 + j, this.durationMicroseconds);
        hwBlob.putBool(32 + j, this.hasVideo);
        hwBlob.putBool(33 + j, this.isStreaming);
        hwBlob.putInt32(36 + j, this.bitWidth);
        hwBlob.putInt32(40 + j, this.bufferSize);
        hwBlob.putInt32(j + 44, this.usage);
    }
}
