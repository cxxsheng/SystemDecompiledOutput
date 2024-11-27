package android.media;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class HwAudioSource extends android.media.PlayerBase {
    private final android.media.AudioAttributes mAudioAttributes;
    private final android.media.AudioDeviceInfo mAudioDeviceInfo;
    private int mNativeHandle;

    private HwAudioSource(android.media.AudioDeviceInfo audioDeviceInfo, android.media.AudioAttributes audioAttributes) {
        super(audioAttributes, 14);
        this.mNativeHandle = 0;
        com.android.internal.util.Preconditions.checkNotNull(audioDeviceInfo);
        com.android.internal.util.Preconditions.checkNotNull(audioAttributes);
        com.android.internal.util.Preconditions.checkArgument(audioDeviceInfo.isSource(), "Requires a source device");
        this.mAudioDeviceInfo = audioDeviceInfo;
        this.mAudioAttributes = audioAttributes;
        baseRegisterPlayer(0);
    }

    @Override // android.media.PlayerBase
    void playerSetVolume(boolean z, float f, float f2) {
    }

    @Override // android.media.PlayerBase
    int playerApplyVolumeShaper(android.media.VolumeShaper.Configuration configuration, android.media.VolumeShaper.Operation operation) {
        return 0;
    }

    @Override // android.media.PlayerBase
    android.media.VolumeShaper.State playerGetVolumeShaperState(int i) {
        return new android.media.VolumeShaper.State(1.0f, 1.0f);
    }

    @Override // android.media.PlayerBase
    int playerSetAuxEffectSendLevel(boolean z, float f) {
        return 0;
    }

    @Override // android.media.PlayerBase
    void playerStart() {
        start();
    }

    @Override // android.media.PlayerBase
    void playerPause() {
        stop();
    }

    @Override // android.media.PlayerBase
    void playerStop() {
        stop();
    }

    public void start() {
        com.android.internal.util.Preconditions.checkState(!isPlaying(), "HwAudioSource is currently playing");
        this.mNativeHandle = android.media.AudioSystem.startAudioSource(this.mAudioDeviceInfo.getPort().activeConfig(), this.mAudioAttributes);
        if (isPlaying()) {
            baseStart(getDeviceId());
        }
    }

    private int getDeviceId() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (android.media.AudioManager.listAudioPatches(arrayList) != 0) {
            return 0;
        }
        for (int i = 0; i < arrayList.size(); i++) {
            android.media.AudioPatch audioPatch = (android.media.AudioPatch) arrayList.get(i);
            android.media.AudioPortConfig[] sources = audioPatch.sources();
            android.media.AudioPortConfig[] sinks = audioPatch.sinks();
            if (sources != null && sources.length > 0) {
                for (int i2 = 0; i2 < sources.length; i2++) {
                    if (sources[i2].port().id() == this.mAudioDeviceInfo.getId()) {
                        return sinks[i2].port().id();
                    }
                }
            }
        }
        return 0;
    }

    public boolean isPlaying() {
        return this.mNativeHandle > 0;
    }

    public void stop() {
        if (this.mNativeHandle > 0) {
            baseStop();
            android.media.AudioSystem.stopAudioSource(this.mNativeHandle);
            this.mNativeHandle = 0;
        }
    }

    public static final class Builder {
        private android.media.AudioAttributes mAudioAttributes;
        private android.media.AudioDeviceInfo mAudioDeviceInfo;

        public android.media.HwAudioSource.Builder setAudioAttributes(android.media.AudioAttributes audioAttributes) {
            com.android.internal.util.Preconditions.checkNotNull(audioAttributes);
            this.mAudioAttributes = audioAttributes;
            return this;
        }

        public android.media.HwAudioSource.Builder setAudioDeviceInfo(android.media.AudioDeviceInfo audioDeviceInfo) {
            com.android.internal.util.Preconditions.checkNotNull(audioDeviceInfo);
            com.android.internal.util.Preconditions.checkArgument(audioDeviceInfo.isSource());
            this.mAudioDeviceInfo = audioDeviceInfo;
            return this;
        }

        public android.media.HwAudioSource build() {
            com.android.internal.util.Preconditions.checkNotNull(this.mAudioDeviceInfo);
            if (this.mAudioAttributes == null) {
                this.mAudioAttributes = new android.media.AudioAttributes.Builder().setUsage(1).build();
            }
            return new android.media.HwAudioSource(this.mAudioDeviceInfo, this.mAudioAttributes);
        }
    }
}
