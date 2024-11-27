package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public enum MixType {
    mix("mix"),
    mux("mux");

    private final java.lang.String rawName;

    MixType(java.lang.String str) {
        this.rawName = str;
    }

    public java.lang.String getRawName() {
        return this.rawName;
    }

    static android.audio.policy.configuration.V7_0.MixType fromString(java.lang.String str) {
        for (android.audio.policy.configuration.V7_0.MixType mixType : values()) {
            if (mixType.getRawName().equals(str)) {
                return mixType;
            }
        }
        throw new java.lang.IllegalArgumentException(str);
    }
}
