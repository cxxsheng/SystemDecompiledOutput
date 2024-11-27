package com.android.server.display.config;

/* loaded from: classes.dex */
public enum ThermalStatus {
    none("none"),
    light("light"),
    moderate("moderate"),
    severe("severe"),
    critical("critical"),
    emergency("emergency"),
    shutdown("shutdown");

    private final java.lang.String rawName;

    ThermalStatus(java.lang.String str) {
        this.rawName = str;
    }

    public java.lang.String getRawName() {
        return this.rawName;
    }

    static com.android.server.display.config.ThermalStatus fromString(java.lang.String str) {
        for (com.android.server.display.config.ThermalStatus thermalStatus : values()) {
            if (thermalStatus.getRawName().equals(str)) {
                return thermalStatus;
            }
        }
        throw new java.lang.IllegalArgumentException(str);
    }
}
