package android.app.time;

/* loaded from: classes.dex */
public final class TimeZoneDetectorStatus implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.time.TimeZoneDetectorStatus> CREATOR = new android.os.Parcelable.Creator<android.app.time.TimeZoneDetectorStatus>() { // from class: android.app.time.TimeZoneDetectorStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.time.TimeZoneDetectorStatus createFromParcel(android.os.Parcel parcel) {
            return new android.app.time.TimeZoneDetectorStatus(parcel.readInt(), (android.app.time.TelephonyTimeZoneAlgorithmStatus) parcel.readParcelable(getClass().getClassLoader(), android.app.time.TelephonyTimeZoneAlgorithmStatus.class), (android.app.time.LocationTimeZoneAlgorithmStatus) parcel.readParcelable(getClass().getClassLoader(), android.app.time.LocationTimeZoneAlgorithmStatus.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.time.TimeZoneDetectorStatus[] newArray(int i) {
            return new android.app.time.TimeZoneDetectorStatus[i];
        }
    };
    private final int mDetectorStatus;
    private final android.app.time.LocationTimeZoneAlgorithmStatus mLocationTimeZoneAlgorithmStatus;
    private final android.app.time.TelephonyTimeZoneAlgorithmStatus mTelephonyTimeZoneAlgorithmStatus;

    public TimeZoneDetectorStatus(int i, android.app.time.TelephonyTimeZoneAlgorithmStatus telephonyTimeZoneAlgorithmStatus, android.app.time.LocationTimeZoneAlgorithmStatus locationTimeZoneAlgorithmStatus) {
        this.mDetectorStatus = android.app.time.DetectorStatusTypes.requireValidDetectorStatus(i);
        this.mTelephonyTimeZoneAlgorithmStatus = (android.app.time.TelephonyTimeZoneAlgorithmStatus) java.util.Objects.requireNonNull(telephonyTimeZoneAlgorithmStatus);
        this.mLocationTimeZoneAlgorithmStatus = (android.app.time.LocationTimeZoneAlgorithmStatus) java.util.Objects.requireNonNull(locationTimeZoneAlgorithmStatus);
    }

    public int getDetectorStatus() {
        return this.mDetectorStatus;
    }

    public android.app.time.TelephonyTimeZoneAlgorithmStatus getTelephonyTimeZoneAlgorithmStatus() {
        return this.mTelephonyTimeZoneAlgorithmStatus;
    }

    public android.app.time.LocationTimeZoneAlgorithmStatus getLocationTimeZoneAlgorithmStatus() {
        return this.mLocationTimeZoneAlgorithmStatus;
    }

    public java.lang.String toString() {
        return "TimeZoneDetectorStatus{mDetectorStatus=" + android.app.time.DetectorStatusTypes.detectorStatusToString(this.mDetectorStatus) + ", mTelephonyTimeZoneAlgorithmStatus=" + this.mTelephonyTimeZoneAlgorithmStatus + ", mLocationTimeZoneAlgorithmStatus=" + this.mLocationTimeZoneAlgorithmStatus + '}';
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mDetectorStatus);
        parcel.writeParcelable(this.mTelephonyTimeZoneAlgorithmStatus, i);
        parcel.writeParcelable(this.mLocationTimeZoneAlgorithmStatus, i);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.app.time.TimeZoneDetectorStatus timeZoneDetectorStatus = (android.app.time.TimeZoneDetectorStatus) obj;
        if (this.mDetectorStatus == timeZoneDetectorStatus.mDetectorStatus && this.mTelephonyTimeZoneAlgorithmStatus.equals(timeZoneDetectorStatus.mTelephonyTimeZoneAlgorithmStatus) && this.mLocationTimeZoneAlgorithmStatus.equals(timeZoneDetectorStatus.mLocationTimeZoneAlgorithmStatus)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mDetectorStatus), this.mTelephonyTimeZoneAlgorithmStatus, this.mLocationTimeZoneAlgorithmStatus);
    }
}
