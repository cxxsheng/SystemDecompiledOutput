package com.android.server.display.config;

/* loaded from: classes.dex */
public enum AutoBrightnessSettingName {
    dim("dim"),
    normal("normal"),
    bright("bright");

    private final java.lang.String rawName;

    AutoBrightnessSettingName(java.lang.String str) {
        this.rawName = str;
    }

    public java.lang.String getRawName() {
        return this.rawName;
    }

    static com.android.server.display.config.AutoBrightnessSettingName fromString(java.lang.String str) {
        for (com.android.server.display.config.AutoBrightnessSettingName autoBrightnessSettingName : values()) {
            if (autoBrightnessSettingName.getRawName().equals(str)) {
                return autoBrightnessSettingName;
            }
        }
        throw new java.lang.IllegalArgumentException(str);
    }
}
