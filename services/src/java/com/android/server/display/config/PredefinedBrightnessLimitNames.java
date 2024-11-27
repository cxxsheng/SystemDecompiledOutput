package com.android.server.display.config;

/* loaded from: classes.dex */
public enum PredefinedBrightnessLimitNames {
    _default("default"),
    adaptive("adaptive");

    private final java.lang.String rawName;

    PredefinedBrightnessLimitNames(java.lang.String str) {
        this.rawName = str;
    }

    public java.lang.String getRawName() {
        return this.rawName;
    }

    static com.android.server.display.config.PredefinedBrightnessLimitNames fromString(java.lang.String str) {
        for (com.android.server.display.config.PredefinedBrightnessLimitNames predefinedBrightnessLimitNames : values()) {
            if (predefinedBrightnessLimitNames.getRawName().equals(str)) {
                return predefinedBrightnessLimitNames;
            }
        }
        throw new java.lang.IllegalArgumentException(str);
    }
}
