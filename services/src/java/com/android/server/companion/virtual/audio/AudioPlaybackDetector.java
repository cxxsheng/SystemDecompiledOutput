package com.android.server.companion.virtual.audio;

/* loaded from: classes.dex */
final class AudioPlaybackDetector extends android.media.AudioManager.AudioPlaybackCallback {
    private final android.media.AudioManager mAudioManager;
    private com.android.server.companion.virtual.audio.AudioPlaybackDetector.AudioPlaybackCallback mAudioPlaybackCallback;

    interface AudioPlaybackCallback {
        void onPlaybackConfigChanged(java.util.List<android.media.AudioPlaybackConfiguration> list);
    }

    AudioPlaybackDetector(android.content.Context context) {
        this.mAudioManager = (android.media.AudioManager) context.getSystemService(android.media.AudioManager.class);
    }

    void register(@android.annotation.NonNull com.android.server.companion.virtual.audio.AudioPlaybackDetector.AudioPlaybackCallback audioPlaybackCallback) {
        this.mAudioPlaybackCallback = audioPlaybackCallback;
        this.mAudioManager.registerAudioPlaybackCallback(this, null);
    }

    void unregister() {
        if (this.mAudioPlaybackCallback != null) {
            this.mAudioPlaybackCallback = null;
            this.mAudioManager.unregisterAudioPlaybackCallback(this);
        }
    }

    @Override // android.media.AudioManager.AudioPlaybackCallback
    public void onPlaybackConfigChanged(java.util.List<android.media.AudioPlaybackConfiguration> list) {
        super.onPlaybackConfigChanged(list);
        if (this.mAudioPlaybackCallback != null) {
            this.mAudioPlaybackCallback.onPlaybackConfigChanged(list);
        }
    }
}
