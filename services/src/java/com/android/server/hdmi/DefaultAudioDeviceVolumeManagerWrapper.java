package com.android.server.hdmi;

/* loaded from: classes2.dex */
public class DefaultAudioDeviceVolumeManagerWrapper implements com.android.server.hdmi.AudioDeviceVolumeManagerWrapper {
    private static final java.lang.String TAG = "AudioDeviceVolumeManagerWrapper";
    private final android.media.AudioDeviceVolumeManager mAudioDeviceVolumeManager;

    public DefaultAudioDeviceVolumeManagerWrapper(android.content.Context context) {
        this.mAudioDeviceVolumeManager = new android.media.AudioDeviceVolumeManager(context);
    }

    @Override // com.android.server.hdmi.AudioDeviceVolumeManagerWrapper
    public void addOnDeviceVolumeBehaviorChangedListener(@android.annotation.NonNull java.util.concurrent.Executor executor, @android.annotation.NonNull android.media.AudioDeviceVolumeManager.OnDeviceVolumeBehaviorChangedListener onDeviceVolumeBehaviorChangedListener) throws java.lang.SecurityException {
        this.mAudioDeviceVolumeManager.addOnDeviceVolumeBehaviorChangedListener(executor, onDeviceVolumeBehaviorChangedListener);
    }

    @Override // com.android.server.hdmi.AudioDeviceVolumeManagerWrapper
    public void removeOnDeviceVolumeBehaviorChangedListener(@android.annotation.NonNull android.media.AudioDeviceVolumeManager.OnDeviceVolumeBehaviorChangedListener onDeviceVolumeBehaviorChangedListener) {
        this.mAudioDeviceVolumeManager.removeOnDeviceVolumeBehaviorChangedListener(onDeviceVolumeBehaviorChangedListener);
    }

    @Override // com.android.server.hdmi.AudioDeviceVolumeManagerWrapper
    public void setDeviceAbsoluteVolumeBehavior(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes, @android.annotation.NonNull android.media.VolumeInfo volumeInfo, @android.annotation.NonNull java.util.concurrent.Executor executor, @android.annotation.NonNull android.media.AudioDeviceVolumeManager.OnAudioDeviceVolumeChangedListener onAudioDeviceVolumeChangedListener, boolean z) {
        this.mAudioDeviceVolumeManager.setDeviceAbsoluteVolumeBehavior(audioDeviceAttributes, volumeInfo, executor, onAudioDeviceVolumeChangedListener, z);
    }

    @Override // com.android.server.hdmi.AudioDeviceVolumeManagerWrapper
    public void setDeviceAbsoluteVolumeAdjustOnlyBehavior(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes, @android.annotation.NonNull android.media.VolumeInfo volumeInfo, @android.annotation.NonNull java.util.concurrent.Executor executor, @android.annotation.NonNull android.media.AudioDeviceVolumeManager.OnAudioDeviceVolumeChangedListener onAudioDeviceVolumeChangedListener, boolean z) {
        this.mAudioDeviceVolumeManager.setDeviceAbsoluteVolumeAdjustOnlyBehavior(audioDeviceAttributes, volumeInfo, executor, onAudioDeviceVolumeChangedListener, z);
    }
}
