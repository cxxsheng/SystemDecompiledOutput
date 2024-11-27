package com.android.server.location.settings;

/* loaded from: classes2.dex */
public final class LocationUserSettings implements com.android.server.location.settings.SettingsStore.VersionedSettings {
    private static final int VERSION = 1;
    private final boolean mAdasGnssLocationEnabled;

    private LocationUserSettings(boolean z) {
        this.mAdasGnssLocationEnabled = z;
    }

    @Override // com.android.server.location.settings.SettingsStore.VersionedSettings
    public int getVersion() {
        return 1;
    }

    public boolean isAdasGnssLocationEnabled() {
        return this.mAdasGnssLocationEnabled;
    }

    public com.android.server.location.settings.LocationUserSettings withAdasGnssLocationEnabled(boolean z) {
        if (z == this.mAdasGnssLocationEnabled) {
            return this;
        }
        return new com.android.server.location.settings.LocationUserSettings(z);
    }

    void write(java.io.DataOutput dataOutput) throws java.io.IOException {
        dataOutput.writeBoolean(this.mAdasGnssLocationEnabled);
    }

    static com.android.server.location.settings.LocationUserSettings read(android.content.res.Resources resources, int i, java.io.DataInput dataInput) throws java.io.IOException {
        boolean readBoolean;
        switch (i) {
            case 1:
                readBoolean = dataInput.readBoolean();
                break;
            default:
                readBoolean = resources.getBoolean(android.R.bool.config_defaultAdasGnssLocationEnabled);
                break;
        }
        return new com.android.server.location.settings.LocationUserSettings(readBoolean);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof com.android.server.location.settings.LocationUserSettings) && this.mAdasGnssLocationEnabled == ((com.android.server.location.settings.LocationUserSettings) obj).mAdasGnssLocationEnabled;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Boolean.valueOf(this.mAdasGnssLocationEnabled));
    }
}
