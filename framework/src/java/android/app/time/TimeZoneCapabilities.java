package android.app.time;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class TimeZoneCapabilities implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.time.TimeZoneCapabilities> CREATOR = new android.os.Parcelable.Creator<android.app.time.TimeZoneCapabilities>() { // from class: android.app.time.TimeZoneCapabilities.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.time.TimeZoneCapabilities createFromParcel(android.os.Parcel parcel) {
            return android.app.time.TimeZoneCapabilities.createFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.time.TimeZoneCapabilities[] newArray(int i) {
            return new android.app.time.TimeZoneCapabilities[i];
        }
    };
    private final int mConfigureAutoDetectionEnabledCapability;
    private final int mConfigureGeoDetectionEnabledCapability;
    private final int mSetManualTimeZoneCapability;
    private final boolean mUseLocationEnabled;
    private final android.os.UserHandle mUserHandle;

    private TimeZoneCapabilities(android.app.time.TimeZoneCapabilities.Builder builder) {
        this.mUserHandle = (android.os.UserHandle) java.util.Objects.requireNonNull(builder.mUserHandle);
        this.mConfigureAutoDetectionEnabledCapability = builder.mConfigureAutoDetectionEnabledCapability;
        this.mUseLocationEnabled = builder.mUseLocationEnabled.booleanValue();
        this.mConfigureGeoDetectionEnabledCapability = builder.mConfigureGeoDetectionEnabledCapability;
        this.mSetManualTimeZoneCapability = builder.mSetManualTimeZoneCapability;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.app.time.TimeZoneCapabilities createFromParcel(android.os.Parcel parcel) {
        return new android.app.time.TimeZoneCapabilities.Builder(android.os.UserHandle.readFromParcel(parcel)).setConfigureAutoDetectionEnabledCapability(parcel.readInt()).setUseLocationEnabled(parcel.readBoolean()).setConfigureGeoDetectionEnabledCapability(parcel.readInt()).setSetManualTimeZoneCapability(parcel.readInt()).build();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        android.os.UserHandle.writeToParcel(this.mUserHandle, parcel);
        parcel.writeInt(this.mConfigureAutoDetectionEnabledCapability);
        parcel.writeBoolean(this.mUseLocationEnabled);
        parcel.writeInt(this.mConfigureGeoDetectionEnabledCapability);
        parcel.writeInt(this.mSetManualTimeZoneCapability);
    }

    public int getConfigureAutoDetectionEnabledCapability() {
        return this.mConfigureAutoDetectionEnabledCapability;
    }

    public boolean isUseLocationEnabled() {
        return this.mUseLocationEnabled;
    }

    public int getConfigureGeoDetectionEnabledCapability() {
        return this.mConfigureGeoDetectionEnabledCapability;
    }

    public int getSetManualTimeZoneCapability() {
        return this.mSetManualTimeZoneCapability;
    }

    public android.app.time.TimeZoneConfiguration tryApplyConfigChanges(android.app.time.TimeZoneConfiguration timeZoneConfiguration, android.app.time.TimeZoneConfiguration timeZoneConfiguration2) {
        android.app.time.TimeZoneConfiguration.Builder builder = new android.app.time.TimeZoneConfiguration.Builder(timeZoneConfiguration);
        if (timeZoneConfiguration2.hasIsAutoDetectionEnabled()) {
            if (getConfigureAutoDetectionEnabledCapability() < 30) {
                return null;
            }
            builder.setAutoDetectionEnabled(timeZoneConfiguration2.isAutoDetectionEnabled());
        }
        if (timeZoneConfiguration2.hasIsGeoDetectionEnabled()) {
            if (getConfigureGeoDetectionEnabledCapability() < 30) {
                return null;
            }
            builder.setGeoDetectionEnabled(timeZoneConfiguration2.isGeoDetectionEnabled());
        }
        return builder.build();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.app.time.TimeZoneCapabilities timeZoneCapabilities = (android.app.time.TimeZoneCapabilities) obj;
        if (this.mUserHandle.equals(timeZoneCapabilities.mUserHandle) && this.mConfigureAutoDetectionEnabledCapability == timeZoneCapabilities.mConfigureAutoDetectionEnabledCapability && this.mUseLocationEnabled == timeZoneCapabilities.mUseLocationEnabled && this.mConfigureGeoDetectionEnabledCapability == timeZoneCapabilities.mConfigureGeoDetectionEnabledCapability && this.mSetManualTimeZoneCapability == timeZoneCapabilities.mSetManualTimeZoneCapability) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mUserHandle, java.lang.Integer.valueOf(this.mConfigureAutoDetectionEnabledCapability), java.lang.Integer.valueOf(this.mConfigureGeoDetectionEnabledCapability), java.lang.Integer.valueOf(this.mSetManualTimeZoneCapability));
    }

    public java.lang.String toString() {
        return "TimeZoneDetectorCapabilities{mUserHandle=" + this.mUserHandle + ", mConfigureAutoDetectionEnabledCapability=" + this.mConfigureAutoDetectionEnabledCapability + ", mUseLocationEnabled=" + this.mUseLocationEnabled + ", mConfigureGeoDetectionEnabledCapability=" + this.mConfigureGeoDetectionEnabledCapability + ", mSetManualTimeZoneCapability=" + this.mSetManualTimeZoneCapability + '}';
    }

    public static class Builder {
        private int mConfigureAutoDetectionEnabledCapability;
        private int mConfigureGeoDetectionEnabledCapability;
        private int mSetManualTimeZoneCapability;
        private java.lang.Boolean mUseLocationEnabled;
        private android.os.UserHandle mUserHandle;

        public Builder(android.os.UserHandle userHandle) {
            this.mUserHandle = (android.os.UserHandle) java.util.Objects.requireNonNull(userHandle);
        }

        public Builder(android.app.time.TimeZoneCapabilities timeZoneCapabilities) {
            java.util.Objects.requireNonNull(timeZoneCapabilities);
            this.mUserHandle = timeZoneCapabilities.mUserHandle;
            this.mConfigureAutoDetectionEnabledCapability = timeZoneCapabilities.mConfigureAutoDetectionEnabledCapability;
            this.mUseLocationEnabled = java.lang.Boolean.valueOf(timeZoneCapabilities.mUseLocationEnabled);
            this.mConfigureGeoDetectionEnabledCapability = timeZoneCapabilities.mConfigureGeoDetectionEnabledCapability;
            this.mSetManualTimeZoneCapability = timeZoneCapabilities.mSetManualTimeZoneCapability;
        }

        public android.app.time.TimeZoneCapabilities.Builder setConfigureAutoDetectionEnabledCapability(int i) {
            this.mConfigureAutoDetectionEnabledCapability = i;
            return this;
        }

        public android.app.time.TimeZoneCapabilities.Builder setUseLocationEnabled(boolean z) {
            this.mUseLocationEnabled = java.lang.Boolean.valueOf(z);
            return this;
        }

        public android.app.time.TimeZoneCapabilities.Builder setConfigureGeoDetectionEnabledCapability(int i) {
            this.mConfigureGeoDetectionEnabledCapability = i;
            return this;
        }

        public android.app.time.TimeZoneCapabilities.Builder setSetManualTimeZoneCapability(int i) {
            this.mSetManualTimeZoneCapability = i;
            return this;
        }

        public android.app.time.TimeZoneCapabilities build() {
            verifyCapabilitySet(this.mConfigureAutoDetectionEnabledCapability, "configureAutoDetectionEnabledCapability");
            java.util.Objects.requireNonNull(this.mUseLocationEnabled, "useLocationEnabled");
            verifyCapabilitySet(this.mConfigureGeoDetectionEnabledCapability, "configureGeoDetectionEnabledCapability");
            verifyCapabilitySet(this.mSetManualTimeZoneCapability, "mSetManualTimeZoneCapability");
            return new android.app.time.TimeZoneCapabilities(this);
        }

        private void verifyCapabilitySet(int i, java.lang.String str) {
            if (i == 0) {
                throw new java.lang.IllegalStateException(str + " not set");
            }
        }
    }
}
