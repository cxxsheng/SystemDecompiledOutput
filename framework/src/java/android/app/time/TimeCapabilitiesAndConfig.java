package android.app.time;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class TimeCapabilitiesAndConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.time.TimeCapabilitiesAndConfig> CREATOR = new android.os.Parcelable.Creator<android.app.time.TimeCapabilitiesAndConfig>() { // from class: android.app.time.TimeCapabilitiesAndConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.time.TimeCapabilitiesAndConfig createFromParcel(android.os.Parcel parcel) {
            return android.app.time.TimeCapabilitiesAndConfig.readFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.time.TimeCapabilitiesAndConfig[] newArray(int i) {
            return new android.app.time.TimeCapabilitiesAndConfig[i];
        }
    };
    private final android.app.time.TimeCapabilities mCapabilities;
    private final android.app.time.TimeConfiguration mConfiguration;

    public TimeCapabilitiesAndConfig(android.app.time.TimeCapabilities timeCapabilities, android.app.time.TimeConfiguration timeConfiguration) {
        this.mCapabilities = (android.app.time.TimeCapabilities) java.util.Objects.requireNonNull(timeCapabilities);
        this.mConfiguration = (android.app.time.TimeConfiguration) java.util.Objects.requireNonNull(timeConfiguration);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.app.time.TimeCapabilitiesAndConfig readFromParcel(android.os.Parcel parcel) {
        return new android.app.time.TimeCapabilitiesAndConfig((android.app.time.TimeCapabilities) parcel.readParcelable(null, android.app.time.TimeCapabilities.class), (android.app.time.TimeConfiguration) parcel.readParcelable(null, android.app.time.TimeConfiguration.class));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mCapabilities, i);
        parcel.writeParcelable(this.mConfiguration, i);
    }

    public android.app.time.TimeCapabilities getCapabilities() {
        return this.mCapabilities;
    }

    public android.app.time.TimeConfiguration getConfiguration() {
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
        android.app.time.TimeCapabilitiesAndConfig timeCapabilitiesAndConfig = (android.app.time.TimeCapabilitiesAndConfig) obj;
        if (this.mCapabilities.equals(timeCapabilitiesAndConfig.mCapabilities) && this.mConfiguration.equals(timeCapabilitiesAndConfig.mConfiguration)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mCapabilities, this.mConfiguration);
    }

    public java.lang.String toString() {
        return "TimeCapabilitiesAndConfig{mCapabilities=" + this.mCapabilities + ", mConfiguration=" + this.mConfiguration + '}';
    }
}
