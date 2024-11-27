package android.app.time;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class TimeZoneConfiguration implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.time.TimeZoneConfiguration> CREATOR = new android.os.Parcelable.Creator<android.app.time.TimeZoneConfiguration>() { // from class: android.app.time.TimeZoneConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.time.TimeZoneConfiguration createFromParcel(android.os.Parcel parcel) {
            return android.app.time.TimeZoneConfiguration.createFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.time.TimeZoneConfiguration[] newArray(int i) {
            return new android.app.time.TimeZoneConfiguration[i];
        }
    };
    private static final java.lang.String SETTING_AUTO_DETECTION_ENABLED = "autoDetectionEnabled";
    private static final java.lang.String SETTING_GEO_DETECTION_ENABLED = "geoDetectionEnabled";
    private final android.os.Bundle mBundle;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface Setting {
    }

    private TimeZoneConfiguration(android.app.time.TimeZoneConfiguration.Builder builder) {
        this.mBundle = (android.os.Bundle) java.util.Objects.requireNonNull(builder.mBundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.app.time.TimeZoneConfiguration createFromParcel(android.os.Parcel parcel) {
        return new android.app.time.TimeZoneConfiguration.Builder().setPropertyBundleInternal(parcel.readBundle()).build();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeBundle(this.mBundle);
    }

    public boolean isComplete() {
        return hasIsAutoDetectionEnabled() && hasIsGeoDetectionEnabled();
    }

    public boolean isAutoDetectionEnabled() {
        enforceSettingPresent(SETTING_AUTO_DETECTION_ENABLED);
        return this.mBundle.getBoolean(SETTING_AUTO_DETECTION_ENABLED);
    }

    public boolean hasIsAutoDetectionEnabled() {
        return this.mBundle.containsKey(SETTING_AUTO_DETECTION_ENABLED);
    }

    public boolean isGeoDetectionEnabled() {
        enforceSettingPresent(SETTING_GEO_DETECTION_ENABLED);
        return this.mBundle.getBoolean(SETTING_GEO_DETECTION_ENABLED);
    }

    public boolean hasIsGeoDetectionEnabled() {
        return this.mBundle.containsKey(SETTING_GEO_DETECTION_ENABLED);
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
        return this.mBundle.kindofEquals(((android.app.time.TimeZoneConfiguration) obj).mBundle);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mBundle);
    }

    public java.lang.String toString() {
        return "TimeZoneConfiguration{mBundle=" + this.mBundle + '}';
    }

    private void enforceSettingPresent(java.lang.String str) {
        if (!this.mBundle.containsKey(str)) {
            throw new java.lang.IllegalStateException(str + " is not set");
        }
    }

    @android.annotation.SystemApi
    public static final class Builder {
        private final android.os.Bundle mBundle = new android.os.Bundle();

        public Builder() {
        }

        public Builder(android.app.time.TimeZoneConfiguration timeZoneConfiguration) {
            mergeProperties(timeZoneConfiguration);
        }

        public android.app.time.TimeZoneConfiguration.Builder mergeProperties(android.app.time.TimeZoneConfiguration timeZoneConfiguration) {
            this.mBundle.putAll(timeZoneConfiguration.mBundle);
            return this;
        }

        android.app.time.TimeZoneConfiguration.Builder setPropertyBundleInternal(android.os.Bundle bundle) {
            this.mBundle.putAll(bundle);
            return this;
        }

        public android.app.time.TimeZoneConfiguration.Builder setAutoDetectionEnabled(boolean z) {
            this.mBundle.putBoolean(android.app.time.TimeZoneConfiguration.SETTING_AUTO_DETECTION_ENABLED, z);
            return this;
        }

        public android.app.time.TimeZoneConfiguration.Builder setGeoDetectionEnabled(boolean z) {
            this.mBundle.putBoolean(android.app.time.TimeZoneConfiguration.SETTING_GEO_DETECTION_ENABLED, z);
            return this;
        }

        public android.app.time.TimeZoneConfiguration build() {
            return new android.app.time.TimeZoneConfiguration(this);
        }
    }
}
