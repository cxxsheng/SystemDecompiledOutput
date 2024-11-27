package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public enum AudioEncapsulationType {
    AUDIO_ENCAPSULATION_TYPE_NONE("AUDIO_ENCAPSULATION_TYPE_NONE"),
    AUDIO_ENCAPSULATION_TYPE_IEC61937("AUDIO_ENCAPSULATION_TYPE_IEC61937");

    private final java.lang.String rawName;

    AudioEncapsulationType(java.lang.String str) {
        this.rawName = str;
    }

    public java.lang.String getRawName() {
        return this.rawName;
    }

    static android.audio.policy.configuration.V7_0.AudioEncapsulationType fromString(java.lang.String str) {
        for (android.audio.policy.configuration.V7_0.AudioEncapsulationType audioEncapsulationType : values()) {
            if (audioEncapsulationType.getRawName().equals(str)) {
                return audioEncapsulationType;
            }
        }
        throw new java.lang.IllegalArgumentException(str);
    }
}
