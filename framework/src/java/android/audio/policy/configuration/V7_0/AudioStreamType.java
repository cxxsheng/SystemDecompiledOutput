package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public enum AudioStreamType {
    AUDIO_STREAM_VOICE_CALL("AUDIO_STREAM_VOICE_CALL"),
    AUDIO_STREAM_SYSTEM("AUDIO_STREAM_SYSTEM"),
    AUDIO_STREAM_RING("AUDIO_STREAM_RING"),
    AUDIO_STREAM_MUSIC("AUDIO_STREAM_MUSIC"),
    AUDIO_STREAM_ALARM("AUDIO_STREAM_ALARM"),
    AUDIO_STREAM_NOTIFICATION("AUDIO_STREAM_NOTIFICATION"),
    AUDIO_STREAM_BLUETOOTH_SCO("AUDIO_STREAM_BLUETOOTH_SCO"),
    AUDIO_STREAM_ENFORCED_AUDIBLE("AUDIO_STREAM_ENFORCED_AUDIBLE"),
    AUDIO_STREAM_DTMF("AUDIO_STREAM_DTMF"),
    AUDIO_STREAM_TTS("AUDIO_STREAM_TTS"),
    AUDIO_STREAM_ACCESSIBILITY("AUDIO_STREAM_ACCESSIBILITY"),
    AUDIO_STREAM_ASSISTANT("AUDIO_STREAM_ASSISTANT"),
    AUDIO_STREAM_REROUTING("AUDIO_STREAM_REROUTING"),
    AUDIO_STREAM_PATCH("AUDIO_STREAM_PATCH"),
    AUDIO_STREAM_CALL_ASSISTANT("AUDIO_STREAM_CALL_ASSISTANT");

    private final java.lang.String rawName;

    AudioStreamType(java.lang.String str) {
        this.rawName = str;
    }

    public java.lang.String getRawName() {
        return this.rawName;
    }

    static android.audio.policy.configuration.V7_0.AudioStreamType fromString(java.lang.String str) {
        for (android.audio.policy.configuration.V7_0.AudioStreamType audioStreamType : values()) {
            if (audioStreamType.getRawName().equals(str)) {
                return audioStreamType;
            }
        }
        throw new java.lang.IllegalArgumentException(str);
    }
}
