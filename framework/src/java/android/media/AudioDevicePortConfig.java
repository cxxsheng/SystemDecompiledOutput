package android.media;

/* loaded from: classes2.dex */
public class AudioDevicePortConfig extends android.media.AudioPortConfig {
    AudioDevicePortConfig(android.media.AudioDevicePort audioDevicePort, int i, int i2, int i3, android.media.AudioGainConfig audioGainConfig) {
        super(audioDevicePort, i, i2, i3, audioGainConfig);
    }

    AudioDevicePortConfig(android.media.AudioDevicePortConfig audioDevicePortConfig) {
        this(audioDevicePortConfig.port(), audioDevicePortConfig.samplingRate(), audioDevicePortConfig.channelMask(), audioDevicePortConfig.format(), audioDevicePortConfig.gain());
    }

    @Override // android.media.AudioPortConfig
    public android.media.AudioDevicePort port() {
        return (android.media.AudioDevicePort) this.mPort;
    }
}
