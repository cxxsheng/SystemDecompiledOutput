package com.android.server.hdmi;

/* loaded from: classes2.dex */
public interface AudioDeviceVolumeManagerWrapper {
    void addOnDeviceVolumeBehaviorChangedListener(@android.annotation.NonNull java.util.concurrent.Executor executor, @android.annotation.NonNull android.media.AudioDeviceVolumeManager.OnDeviceVolumeBehaviorChangedListener onDeviceVolumeBehaviorChangedListener);

    void removeOnDeviceVolumeBehaviorChangedListener(@android.annotation.NonNull android.media.AudioDeviceVolumeManager.OnDeviceVolumeBehaviorChangedListener onDeviceVolumeBehaviorChangedListener);

    void setDeviceAbsoluteVolumeAdjustOnlyBehavior(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes, @android.annotation.NonNull android.media.VolumeInfo volumeInfo, @android.annotation.NonNull java.util.concurrent.Executor executor, @android.annotation.NonNull android.media.AudioDeviceVolumeManager.OnAudioDeviceVolumeChangedListener onAudioDeviceVolumeChangedListener, boolean z);

    void setDeviceAbsoluteVolumeBehavior(@android.annotation.NonNull android.media.AudioDeviceAttributes audioDeviceAttributes, @android.annotation.NonNull android.media.VolumeInfo volumeInfo, @android.annotation.NonNull java.util.concurrent.Executor executor, @android.annotation.NonNull android.media.AudioDeviceVolumeManager.OnAudioDeviceVolumeChangedListener onAudioDeviceVolumeChangedListener, boolean z);
}
