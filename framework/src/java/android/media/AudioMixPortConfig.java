package android.media;

/* loaded from: classes2.dex */
public class AudioMixPortConfig extends android.media.AudioPortConfig {
    AudioMixPortConfig(android.media.AudioMixPort audioMixPort, int i, int i2, int i3, android.media.AudioGainConfig audioGainConfig) {
        super(audioMixPort, i, i2, i3, audioGainConfig);
    }

    @Override // android.media.AudioPortConfig
    public android.media.AudioMixPort port() {
        return (android.media.AudioMixPort) this.mPort;
    }
}
