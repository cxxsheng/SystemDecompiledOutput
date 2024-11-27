package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public enum HalVersion {
    _2_0("2.0"),
    _3_0("3.0");

    private final java.lang.String rawName;

    HalVersion(java.lang.String str) {
        this.rawName = str;
    }

    public java.lang.String getRawName() {
        return this.rawName;
    }

    static android.audio.policy.configuration.V7_0.HalVersion fromString(java.lang.String str) {
        for (android.audio.policy.configuration.V7_0.HalVersion halVersion : values()) {
            if (halVersion.getRawName().equals(str)) {
                return halVersion;
            }
        }
        throw new java.lang.IllegalArgumentException(str);
    }
}
