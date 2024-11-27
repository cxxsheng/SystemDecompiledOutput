package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public enum AudioUsage {
    AUDIO_USAGE_UNKNOWN("AUDIO_USAGE_UNKNOWN"),
    AUDIO_USAGE_MEDIA("AUDIO_USAGE_MEDIA"),
    AUDIO_USAGE_VOICE_COMMUNICATION("AUDIO_USAGE_VOICE_COMMUNICATION"),
    AUDIO_USAGE_VOICE_COMMUNICATION_SIGNALLING("AUDIO_USAGE_VOICE_COMMUNICATION_SIGNALLING"),
    AUDIO_USAGE_ALARM("AUDIO_USAGE_ALARM"),
    AUDIO_USAGE_NOTIFICATION("AUDIO_USAGE_NOTIFICATION"),
    AUDIO_USAGE_NOTIFICATION_TELEPHONY_RINGTONE("AUDIO_USAGE_NOTIFICATION_TELEPHONY_RINGTONE"),
    AUDIO_USAGE_ASSISTANCE_ACCESSIBILITY("AUDIO_USAGE_ASSISTANCE_ACCESSIBILITY"),
    AUDIO_USAGE_ASSISTANCE_NAVIGATION_GUIDANCE("AUDIO_USAGE_ASSISTANCE_NAVIGATION_GUIDANCE"),
    AUDIO_USAGE_ASSISTANCE_SONIFICATION("AUDIO_USAGE_ASSISTANCE_SONIFICATION"),
    AUDIO_USAGE_GAME("AUDIO_USAGE_GAME"),
    AUDIO_USAGE_VIRTUAL_SOURCE("AUDIO_USAGE_VIRTUAL_SOURCE"),
    AUDIO_USAGE_ASSISTANT("AUDIO_USAGE_ASSISTANT"),
    AUDIO_USAGE_CALL_ASSISTANT("AUDIO_USAGE_CALL_ASSISTANT"),
    AUDIO_USAGE_EMERGENCY("AUDIO_USAGE_EMERGENCY"),
    AUDIO_USAGE_SAFETY("AUDIO_USAGE_SAFETY"),
    AUDIO_USAGE_VEHICLE_STATUS("AUDIO_USAGE_VEHICLE_STATUS"),
    AUDIO_USAGE_ANNOUNCEMENT("AUDIO_USAGE_ANNOUNCEMENT");

    private final java.lang.String rawName;

    AudioUsage(java.lang.String str) {
        this.rawName = str;
    }

    public java.lang.String getRawName() {
        return this.rawName;
    }

    static android.audio.policy.configuration.V7_0.AudioUsage fromString(java.lang.String str) {
        for (android.audio.policy.configuration.V7_0.AudioUsage audioUsage : values()) {
            if (audioUsage.getRawName().equals(str)) {
                return audioUsage;
            }
        }
        throw new java.lang.IllegalArgumentException(str);
    }
}
