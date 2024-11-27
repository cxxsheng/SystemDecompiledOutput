package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public enum EngineSuffix {
    _default("default"),
    configurable("configurable");

    private final java.lang.String rawName;

    EngineSuffix(java.lang.String str) {
        this.rawName = str;
    }

    public java.lang.String getRawName() {
        return this.rawName;
    }

    static android.audio.policy.configuration.V7_0.EngineSuffix fromString(java.lang.String str) {
        for (android.audio.policy.configuration.V7_0.EngineSuffix engineSuffix : values()) {
            if (engineSuffix.getRawName().equals(str)) {
                return engineSuffix;
            }
        }
        throw new java.lang.IllegalArgumentException(str);
    }
}
