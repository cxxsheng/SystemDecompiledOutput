package com.android.server.display.config;

/* loaded from: classes.dex */
public enum AutoBrightnessModeName {
    _default("default"),
    idle("idle"),
    doze("doze");

    private final java.lang.String rawName;

    AutoBrightnessModeName(java.lang.String str) {
        this.rawName = str;
    }

    public java.lang.String getRawName() {
        return this.rawName;
    }

    static com.android.server.display.config.AutoBrightnessModeName fromString(java.lang.String str) {
        for (com.android.server.display.config.AutoBrightnessModeName autoBrightnessModeName : values()) {
            if (autoBrightnessModeName.getRawName().equals(str)) {
                return autoBrightnessModeName;
            }
        }
        throw new java.lang.IllegalArgumentException(str);
    }
}
