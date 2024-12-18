package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public enum AudioInOutFlag {
    AUDIO_OUTPUT_FLAG_DIRECT("AUDIO_OUTPUT_FLAG_DIRECT"),
    AUDIO_OUTPUT_FLAG_PRIMARY("AUDIO_OUTPUT_FLAG_PRIMARY"),
    AUDIO_OUTPUT_FLAG_FAST("AUDIO_OUTPUT_FLAG_FAST"),
    AUDIO_OUTPUT_FLAG_DEEP_BUFFER("AUDIO_OUTPUT_FLAG_DEEP_BUFFER"),
    AUDIO_OUTPUT_FLAG_COMPRESS_OFFLOAD("AUDIO_OUTPUT_FLAG_COMPRESS_OFFLOAD"),
    AUDIO_OUTPUT_FLAG_NON_BLOCKING("AUDIO_OUTPUT_FLAG_NON_BLOCKING"),
    AUDIO_OUTPUT_FLAG_HW_AV_SYNC("AUDIO_OUTPUT_FLAG_HW_AV_SYNC"),
    AUDIO_OUTPUT_FLAG_TTS("AUDIO_OUTPUT_FLAG_TTS"),
    AUDIO_OUTPUT_FLAG_RAW("AUDIO_OUTPUT_FLAG_RAW"),
    AUDIO_OUTPUT_FLAG_SYNC("AUDIO_OUTPUT_FLAG_SYNC"),
    AUDIO_OUTPUT_FLAG_IEC958_NONAUDIO("AUDIO_OUTPUT_FLAG_IEC958_NONAUDIO"),
    AUDIO_OUTPUT_FLAG_DIRECT_PCM("AUDIO_OUTPUT_FLAG_DIRECT_PCM"),
    AUDIO_OUTPUT_FLAG_MMAP_NOIRQ("AUDIO_OUTPUT_FLAG_MMAP_NOIRQ"),
    AUDIO_OUTPUT_FLAG_VOIP_RX("AUDIO_OUTPUT_FLAG_VOIP_RX"),
    AUDIO_OUTPUT_FLAG_INCALL_MUSIC("AUDIO_OUTPUT_FLAG_INCALL_MUSIC"),
    AUDIO_OUTPUT_FLAG_GAPLESS_OFFLOAD("AUDIO_OUTPUT_FLAG_GAPLESS_OFFLOAD"),
    AUDIO_INPUT_FLAG_FAST("AUDIO_INPUT_FLAG_FAST"),
    AUDIO_INPUT_FLAG_HW_HOTWORD("AUDIO_INPUT_FLAG_HW_HOTWORD"),
    AUDIO_INPUT_FLAG_RAW("AUDIO_INPUT_FLAG_RAW"),
    AUDIO_INPUT_FLAG_SYNC("AUDIO_INPUT_FLAG_SYNC"),
    AUDIO_INPUT_FLAG_MMAP_NOIRQ("AUDIO_INPUT_FLAG_MMAP_NOIRQ"),
    AUDIO_INPUT_FLAG_VOIP_TX("AUDIO_INPUT_FLAG_VOIP_TX"),
    AUDIO_INPUT_FLAG_HW_AV_SYNC("AUDIO_INPUT_FLAG_HW_AV_SYNC"),
    AUDIO_INPUT_FLAG_DIRECT("AUDIO_INPUT_FLAG_DIRECT");

    private final java.lang.String rawName;

    AudioInOutFlag(java.lang.String str) {
        this.rawName = str;
    }

    public java.lang.String getRawName() {
        return this.rawName;
    }

    static android.audio.policy.configuration.V7_0.AudioInOutFlag fromString(java.lang.String str) {
        for (android.audio.policy.configuration.V7_0.AudioInOutFlag audioInOutFlag : values()) {
            if (audioInOutFlag.getRawName().equals(str)) {
                return audioInOutFlag;
            }
        }
        throw new java.lang.IllegalArgumentException(str);
    }
}
