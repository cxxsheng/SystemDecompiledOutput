package android.media;

/* loaded from: classes2.dex */
public class AudioMixPort extends android.media.AudioPort {
    private final int mIoHandle;

    AudioMixPort(android.media.AudioHandle audioHandle, int i, int i2, java.lang.String str, int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, android.media.AudioGain[] audioGainArr) {
        super(audioHandle, i2, str, iArr, iArr2, iArr3, iArr4, audioGainArr);
        this.mIoHandle = i;
    }

    AudioMixPort(android.media.AudioHandle audioHandle, int i, int i2, java.lang.String str, java.util.List<android.media.AudioProfile> list, android.media.AudioGain[] audioGainArr) {
        super(audioHandle, i2, str, list, audioGainArr, null);
        this.mIoHandle = i;
    }

    @Override // android.media.AudioPort
    public android.media.AudioMixPortConfig buildConfig(int i, int i2, int i3, android.media.AudioGainConfig audioGainConfig) {
        return new android.media.AudioMixPortConfig(this, i, i2, i3, audioGainConfig);
    }

    public int ioHandle() {
        return this.mIoHandle;
    }

    @Override // android.media.AudioPort
    public boolean equals(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.media.AudioMixPort) || this.mIoHandle != ((android.media.AudioMixPort) obj).ioHandle()) {
            return false;
        }
        return super.equals(obj);
    }
}
