package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public enum AudioContentType {
    AUDIO_CONTENT_TYPE_UNKNOWN("AUDIO_CONTENT_TYPE_UNKNOWN"),
    AUDIO_CONTENT_TYPE_SPEECH("AUDIO_CONTENT_TYPE_SPEECH"),
    AUDIO_CONTENT_TYPE_MUSIC("AUDIO_CONTENT_TYPE_MUSIC"),
    AUDIO_CONTENT_TYPE_MOVIE("AUDIO_CONTENT_TYPE_MOVIE"),
    AUDIO_CONTENT_TYPE_SONIFICATION("AUDIO_CONTENT_TYPE_SONIFICATION");

    private final java.lang.String rawName;

    AudioContentType(java.lang.String str) {
        this.rawName = str;
    }

    public java.lang.String getRawName() {
        return this.rawName;
    }

    static android.audio.policy.configuration.V7_0.AudioContentType fromString(java.lang.String str) {
        for (android.audio.policy.configuration.V7_0.AudioContentType audioContentType : values()) {
            if (audioContentType.getRawName().equals(str)) {
                return audioContentType;
            }
        }
        throw new java.lang.IllegalArgumentException(str);
    }
}
