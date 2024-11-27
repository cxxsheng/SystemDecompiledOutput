package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public enum Version {
    _7_0("7.0");

    private final java.lang.String rawName;

    Version(java.lang.String str) {
        this.rawName = str;
    }

    public java.lang.String getRawName() {
        return this.rawName;
    }

    static android.audio.policy.configuration.V7_0.Version fromString(java.lang.String str) {
        for (android.audio.policy.configuration.V7_0.Version version : values()) {
            if (version.getRawName().equals(str)) {
                return version;
            }
        }
        throw new java.lang.IllegalArgumentException(str);
    }
}
