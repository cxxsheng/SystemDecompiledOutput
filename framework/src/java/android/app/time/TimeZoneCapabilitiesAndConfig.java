package android.app.time;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class TimeZoneCapabilitiesAndConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.time.TimeZoneCapabilitiesAndConfig> CREATOR = new android.os.Parcelable.Creator<android.app.time.TimeZoneCapabilitiesAndConfig>() { // from class: android.app.time.TimeZoneCapabilitiesAndConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.time.TimeZoneCapabilitiesAndConfig createFromParcel(android.os.Parcel parcel) {
            return android.app.time.TimeZoneCapabilitiesAndConfig.createFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.time.TimeZoneCapabilitiesAndConfig[] newArray(int i) {
            return new android.app.time.TimeZoneCapabilitiesAndConfig[i];
        }
    };
    private final android.app.time.TimeZoneCapabilities mCapabilities;
    private final android.app.time.TimeZoneConfiguration mConfiguration;
    private final android.app.time.TimeZoneDetectorStatus mDetectorStatus;

    public TimeZoneCapabilitiesAndConfig(android.app.time.TimeZoneDetectorStatus timeZoneDetectorStatus, android.app.time.TimeZoneCapabilities timeZoneCapabilities, android.app.time.TimeZoneConfiguration timeZoneConfiguration) {
        this.mDetectorStatus = (android.app.time.TimeZoneDetectorStatus) java.util.Objects.requireNonNull(timeZoneDetectorStatus);
        this.mCapabilities = (android.app.time.TimeZoneCapabilities) java.util.Objects.requireNonNull(timeZoneCapabilities);
        this.mConfiguration = (android.app.time.TimeZoneConfiguration) java.util.Objects.requireNonNull(timeZoneConfiguration);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.app.time.TimeZoneCapabilitiesAndConfig createFromParcel(android.os.Parcel parcel) {
        return new android.app.time.TimeZoneCapabilitiesAndConfig((android.app.time.TimeZoneDetectorStatus) parcel.readParcelable(null, android.app.time.TimeZoneDetectorStatus.class), (android.app.time.TimeZoneCapabilities) parcel.readParcelable(null, android.app.time.TimeZoneCapabilities.class), (android.app.time.TimeZoneConfiguration) parcel.readParcelable(null, android.app.time.TimeZoneConfiguration.class));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mDetectorStatus, i);
        parcel.writeParcelable(this.mCapabilities, i);
        parcel.writeParcelable(this.mConfiguration, i);
    }

    public android.app.time.TimeZoneDetectorStatus getDetectorStatus() {
        return this.mDetectorStatus;
    }

    public android.app.time.TimeZoneCapabilities getCapabilities() {
        return this.mCapabilities;
    }

    public android.app.time.TimeZoneConfiguration getConfiguration() {
        return this.mConfiguration;
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
        android.app.time.TimeZoneCapabilitiesAndConfig timeZoneCapabilitiesAndConfig = (android.app.time.TimeZoneCapabilitiesAndConfig) obj;
        if (this.mDetectorStatus.equals(timeZoneCapabilitiesAndConfig.mDetectorStatus) && this.mCapabilities.equals(timeZoneCapabilitiesAndConfig.mCapabilities) && this.mConfiguration.equals(timeZoneCapabilitiesAndConfig.mConfiguration)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mCapabilities, this.mConfiguration);
    }

    public java.lang.String toString() {
        return "TimeZoneCapabilitiesAndConfig{mDetectorStatus=" + this.mDetectorStatus + ", mCapabilities=" + this.mCapabilities + ", mConfiguration=" + this.mConfiguration + '}';
    }
}
