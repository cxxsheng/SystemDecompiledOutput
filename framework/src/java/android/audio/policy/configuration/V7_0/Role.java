package android.audio.policy.configuration.V7_0;

/* loaded from: classes.dex */
public enum Role {
    sink("sink"),
    source(android.app.slice.Slice.SUBTYPE_SOURCE);

    private final java.lang.String rawName;

    Role(java.lang.String str) {
        this.rawName = str;
    }

    public java.lang.String getRawName() {
        return this.rawName;
    }

    static android.audio.policy.configuration.V7_0.Role fromString(java.lang.String str) {
        for (android.audio.policy.configuration.V7_0.Role role : values()) {
            if (role.getRawName().equals(str)) {
                return role;
            }
        }
        throw new java.lang.IllegalArgumentException(str);
    }
}
