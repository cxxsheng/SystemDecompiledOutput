package android.app.time;

/* loaded from: classes.dex */
public final class TelephonyTimeZoneAlgorithmStatus implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.time.TelephonyTimeZoneAlgorithmStatus> CREATOR = new android.os.Parcelable.Creator<android.app.time.TelephonyTimeZoneAlgorithmStatus>() { // from class: android.app.time.TelephonyTimeZoneAlgorithmStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.time.TelephonyTimeZoneAlgorithmStatus createFromParcel(android.os.Parcel parcel) {
            return new android.app.time.TelephonyTimeZoneAlgorithmStatus(parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.time.TelephonyTimeZoneAlgorithmStatus[] newArray(int i) {
            return new android.app.time.TelephonyTimeZoneAlgorithmStatus[i];
        }
    };
    private final int mAlgorithmStatus;

    public TelephonyTimeZoneAlgorithmStatus(int i) {
        this.mAlgorithmStatus = android.app.time.DetectorStatusTypes.requireValidDetectionAlgorithmStatus(i);
    }

    public int getAlgorithmStatus() {
        return this.mAlgorithmStatus;
    }

    public java.lang.String toString() {
        return "TelephonyTimeZoneAlgorithmStatus{mAlgorithmStatus=" + android.app.time.DetectorStatusTypes.detectionAlgorithmStatusToString(this.mAlgorithmStatus) + '}';
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mAlgorithmStatus);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass() && this.mAlgorithmStatus == ((android.app.time.TelephonyTimeZoneAlgorithmStatus) obj).mAlgorithmStatus) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mAlgorithmStatus));
    }
}
