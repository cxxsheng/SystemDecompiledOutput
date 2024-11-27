package com.android.server.media;

/* loaded from: classes2.dex */
final class AudioRoutingUtils {
    static final android.media.AudioAttributes ATTRIBUTES_MEDIA = new android.media.AudioAttributes.Builder().setUsage(1).build();

    @android.annotation.RequiresPermission("android.permission.MODIFY_AUDIO_ROUTING")
    @android.annotation.Nullable
    static android.media.audiopolicy.AudioProductStrategy getMediaAudioProductStrategy() {
        for (android.media.audiopolicy.AudioProductStrategy audioProductStrategy : android.media.AudioManager.getAudioProductStrategies()) {
            if (audioProductStrategy.supportsAudioAttributes(ATTRIBUTES_MEDIA)) {
                return audioProductStrategy;
            }
        }
        return null;
    }

    private AudioRoutingUtils() {
    }
}
