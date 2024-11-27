package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public enum AudioGainMode {
    AUDIO_GAIN_MODE_JOINT("AUDIO_GAIN_MODE_JOINT"),
    AUDIO_GAIN_MODE_CHANNELS("AUDIO_GAIN_MODE_CHANNELS"),
    AUDIO_GAIN_MODE_RAMP("AUDIO_GAIN_MODE_RAMP");

    private final java.lang.String rawName;

    AudioGainMode(java.lang.String str) {
        this.rawName = str;
    }

    public java.lang.String getRawName() {
        return this.rawName;
    }

    static android.audio.policy.configuration.V7_0.AudioGainMode fromString(java.lang.String str) {
        for (android.audio.policy.configuration.V7_0.AudioGainMode audioGainMode : values()) {
            if (audioGainMode.getRawName().equals(str)) {
                return audioGainMode;
            }
        }
        throw new java.lang.IllegalArgumentException(str);
    }
}
