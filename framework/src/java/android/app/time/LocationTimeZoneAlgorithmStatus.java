package android.app.time;

/* loaded from: classes.dex */
public final class LocationTimeZoneAlgorithmStatus implements android.os.Parcelable {
    public static final int PROVIDER_STATUS_IS_CERTAIN = 3;
    public static final int PROVIDER_STATUS_IS_UNCERTAIN = 4;
    public static final int PROVIDER_STATUS_NOT_PRESENT = 1;
    public static final int PROVIDER_STATUS_NOT_READY = 2;
    private final android.service.timezone.TimeZoneProviderStatus mPrimaryProviderReportedStatus;
    private final int mPrimaryProviderStatus;
    private final android.service.timezone.TimeZoneProviderStatus mSecondaryProviderReportedStatus;
    private final int mSecondaryProviderStatus;
    private final int mStatus;
    public static final android.app.time.LocationTimeZoneAlgorithmStatus NOT_SUPPORTED = new android.app.time.LocationTimeZoneAlgorithmStatus(1, 1, null, 1, null);
    public static final android.app.time.LocationTimeZoneAlgorithmStatus RUNNING_NOT_REPORTED = new android.app.time.LocationTimeZoneAlgorithmStatus(2, 2, null, 2, null);
    public static final android.app.time.LocationTimeZoneAlgorithmStatus NOT_RUNNING = new android.app.time.LocationTimeZoneAlgorithmStatus(2, 2, null, 2, null);
    public static final android.os.Parcelable.Creator<android.app.time.LocationTimeZoneAlgorithmStatus> CREATOR = new android.os.Parcelable.Creator<android.app.time.LocationTimeZoneAlgorithmStatus>() { // from class: android.app.time.LocationTimeZoneAlgorithmStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.time.LocationTimeZoneAlgorithmStatus createFromParcel(android.os.Parcel parcel) {
            return new android.app.time.LocationTimeZoneAlgorithmStatus(parcel.readInt(), parcel.readInt(), (android.service.timezone.TimeZoneProviderStatus) parcel.readParcelable(getClass().getClassLoader(), android.service.timezone.TimeZoneProviderStatus.class), parcel.readInt(), (android.service.timezone.TimeZoneProviderStatus) parcel.readParcelable(getClass().getClassLoader(), android.service.timezone.TimeZoneProviderStatus.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.time.LocationTimeZoneAlgorithmStatus[] newArray(int i) {
            return new android.app.time.LocationTimeZoneAlgorithmStatus[i];
        }
    };

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_USE})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ProviderStatus {
    }

    public LocationTimeZoneAlgorithmStatus(int i, int i2, android.service.timezone.TimeZoneProviderStatus timeZoneProviderStatus, int i3, android.service.timezone.TimeZoneProviderStatus timeZoneProviderStatus2) {
        this.mStatus = android.app.time.DetectorStatusTypes.requireValidDetectionAlgorithmStatus(i);
        this.mPrimaryProviderStatus = requireValidProviderStatus(i2);
        this.mPrimaryProviderReportedStatus = timeZoneProviderStatus;
        this.mSecondaryProviderStatus = requireValidProviderStatus(i3);
        this.mSecondaryProviderReportedStatus = timeZoneProviderStatus2;
        boolean hasProviderReported = hasProviderReported(i2);
        boolean z = timeZoneProviderStatus != null;
        if (!hasProviderReported && z) {
            throw new java.lang.IllegalArgumentException("primaryProviderReportedStatus=" + timeZoneProviderStatus + ", primaryProviderStatus=" + providerStatusToString(i2));
        }
        boolean hasProviderReported2 = hasProviderReported(i3);
        boolean z2 = timeZoneProviderStatus2 != null;
        if (!hasProviderReported2 && z2) {
            throw new java.lang.IllegalArgumentException("secondaryProviderReportedStatus=" + timeZoneProviderStatus2 + ", secondaryProviderStatus=" + providerStatusToString(i3));
        }
        if (i != 3) {
            if (hasProviderReported || hasProviderReported2) {
                throw new java.lang.IllegalArgumentException("algorithmStatus=" + android.app.time.DetectorStatusTypes.detectionAlgorithmStatusToString(i) + ", primaryProviderReportedStatus=" + timeZoneProviderStatus + ", secondaryProviderReportedStatus=" + timeZoneProviderStatus2);
            }
        }
    }

    public int getStatus() {
        return this.mStatus;
    }

    public int getPrimaryProviderStatus() {
        return this.mPrimaryProviderStatus;
    }

    public android.service.timezone.TimeZoneProviderStatus getPrimaryProviderReportedStatus() {
        return this.mPrimaryProviderReportedStatus;
    }

    public int getSecondaryProviderStatus() {
        return this.mSecondaryProviderStatus;
    }

    public android.service.timezone.TimeZoneProviderStatus getSecondaryProviderReportedStatus() {
        return this.mSecondaryProviderReportedStatus;
    }

    public java.lang.String toString() {
        return "LocationTimeZoneAlgorithmStatus{mAlgorithmStatus=" + android.app.time.DetectorStatusTypes.detectionAlgorithmStatusToString(this.mStatus) + ", mPrimaryProviderStatus=" + providerStatusToString(this.mPrimaryProviderStatus) + ", mPrimaryProviderReportedStatus=" + this.mPrimaryProviderReportedStatus + ", mSecondaryProviderStatus=" + providerStatusToString(this.mSecondaryProviderStatus) + ", mSecondaryProviderReportedStatus=" + this.mSecondaryProviderReportedStatus + '}';
    }

    public static android.app.time.LocationTimeZoneAlgorithmStatus parseCommandlineArg(java.lang.String str) {
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("LocationTimeZoneAlgorithmStatus\\{mAlgorithmStatus=(.+), mPrimaryProviderStatus=([^,]+), mPrimaryProviderReportedStatus=(null|TimeZoneProviderStatus\\{[^}]+\\}), mSecondaryProviderStatus=([^,]+), mSecondaryProviderReportedStatus=(null|TimeZoneProviderStatus\\{[^}]+\\})\\}").matcher(str);
        if (!matcher.matches()) {
            throw new java.lang.IllegalArgumentException("Unable to parse algorithm status arg: " + str);
        }
        return new android.app.time.LocationTimeZoneAlgorithmStatus(android.app.time.DetectorStatusTypes.detectionAlgorithmStatusFromString(matcher.group(1)), providerStatusFromString(matcher.group(2)), parseTimeZoneProviderStatusOrNull(matcher.group(3)), providerStatusFromString(matcher.group(4)), parseTimeZoneProviderStatusOrNull(matcher.group(5)));
    }

    private static android.service.timezone.TimeZoneProviderStatus parseTimeZoneProviderStatusOrNull(java.lang.String str) {
        if ("null".equals(str)) {
            return null;
        }
        return android.service.timezone.TimeZoneProviderStatus.parseProviderStatus(str);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mStatus);
        parcel.writeInt(this.mPrimaryProviderStatus);
        parcel.writeParcelable(this.mPrimaryProviderReportedStatus, i);
        parcel.writeInt(this.mSecondaryProviderStatus);
        parcel.writeParcelable(this.mSecondaryProviderReportedStatus, i);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.app.time.LocationTimeZoneAlgorithmStatus locationTimeZoneAlgorithmStatus = (android.app.time.LocationTimeZoneAlgorithmStatus) obj;
        if (this.mStatus == locationTimeZoneAlgorithmStatus.mStatus && this.mPrimaryProviderStatus == locationTimeZoneAlgorithmStatus.mPrimaryProviderStatus && java.util.Objects.equals(this.mPrimaryProviderReportedStatus, locationTimeZoneAlgorithmStatus.mPrimaryProviderReportedStatus) && this.mSecondaryProviderStatus == locationTimeZoneAlgorithmStatus.mSecondaryProviderStatus && java.util.Objects.equals(this.mSecondaryProviderReportedStatus, locationTimeZoneAlgorithmStatus.mSecondaryProviderReportedStatus)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mStatus), java.lang.Integer.valueOf(this.mPrimaryProviderStatus), this.mPrimaryProviderReportedStatus, java.lang.Integer.valueOf(this.mSecondaryProviderStatus), this.mSecondaryProviderReportedStatus);
    }

    public boolean couldEnableTelephonyFallback() {
        boolean z;
        boolean z2;
        if (this.mStatus == 0 || this.mStatus == 2 || this.mStatus == 1) {
            return false;
        }
        if (this.mPrimaryProviderStatus == 1) {
            z = true;
        } else if (this.mPrimaryProviderStatus == 4 && this.mPrimaryProviderReportedStatus != null) {
            z = this.mPrimaryProviderReportedStatus.couldEnableTelephonyFallback();
        } else {
            z = false;
        }
        if (this.mSecondaryProviderStatus == 1) {
            z2 = true;
        } else if (this.mSecondaryProviderStatus == 4 && this.mSecondaryProviderReportedStatus != null) {
            z2 = this.mSecondaryProviderReportedStatus.couldEnableTelephonyFallback();
        } else {
            z2 = false;
        }
        return z && z2;
    }

    public static java.lang.String providerStatusToString(int i) {
        switch (i) {
            case 1:
                return "NOT_PRESENT";
            case 2:
                return "NOT_READY";
            case 3:
                return "IS_CERTAIN";
            case 4:
                return "IS_UNCERTAIN";
            default:
                throw new java.lang.IllegalArgumentException("Unknown status: " + i);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int providerStatusFromString(java.lang.String str) {
        char c;
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("Empty status: " + str);
        }
        switch (str.hashCode()) {
            case -1705279891:
                if (str.equals("IS_CERTAIN")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 187660047:
                if (str.equals("NOT_PRESENT")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1034051831:
                if (str.equals("NOT_READY")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1393440500:
                if (str.equals("IS_UNCERTAIN")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            default:
                throw new java.lang.IllegalArgumentException("Unknown status: " + str);
        }
    }

    private static boolean hasProviderReported(int i) {
        return i == 3 || i == 4;
    }

    public static int requireValidProviderStatus(int i) {
        if (i < 1 || i > 4) {
            throw new java.lang.IllegalArgumentException("Invalid provider status: " + i);
        }
        return i;
    }
}
