package android.service.timezone;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class TimeZoneProviderStatus implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.timezone.TimeZoneProviderStatus> CREATOR = new android.os.Parcelable.Creator<android.service.timezone.TimeZoneProviderStatus>() { // from class: android.service.timezone.TimeZoneProviderStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.timezone.TimeZoneProviderStatus createFromParcel(android.os.Parcel parcel) {
            return new android.service.timezone.TimeZoneProviderStatus(parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.timezone.TimeZoneProviderStatus[] newArray(int i) {
            return new android.service.timezone.TimeZoneProviderStatus[i];
        }
    };
    public static final int DEPENDENCY_STATUS_BLOCKED_BY_ENVIRONMENT = 4;
    public static final int DEPENDENCY_STATUS_BLOCKED_BY_SETTINGS = 6;
    public static final int DEPENDENCY_STATUS_DEGRADED_BY_SETTINGS = 5;
    public static final int DEPENDENCY_STATUS_NOT_APPLICABLE = 1;
    public static final int DEPENDENCY_STATUS_OK = 2;
    public static final int DEPENDENCY_STATUS_TEMPORARILY_UNAVAILABLE = 3;
    public static final int DEPENDENCY_STATUS_UNKNOWN = 0;
    public static final int OPERATION_STATUS_FAILED = 3;
    public static final int OPERATION_STATUS_NOT_APPLICABLE = 1;
    public static final int OPERATION_STATUS_OK = 2;
    public static final int OPERATION_STATUS_UNKNOWN = 0;
    private final int mConnectivityDependencyStatus;
    private final int mLocationDetectionDependencyStatus;
    private final int mTimeZoneResolutionOperationStatus;

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_USE})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DependencyStatus {
    }

    @java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE_USE})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface OperationStatus {
    }

    private TimeZoneProviderStatus(int i, int i2, int i3) {
        this.mLocationDetectionDependencyStatus = i;
        this.mConnectivityDependencyStatus = i2;
        this.mTimeZoneResolutionOperationStatus = i3;
    }

    public int getLocationDetectionDependencyStatus() {
        return this.mLocationDetectionDependencyStatus;
    }

    public int getConnectivityDependencyStatus() {
        return this.mConnectivityDependencyStatus;
    }

    public int getTimeZoneResolutionOperationStatus() {
        return this.mTimeZoneResolutionOperationStatus;
    }

    public java.lang.String toString() {
        return "TimeZoneProviderStatus{mLocationDetectionDependencyStatus=" + dependencyStatusToString(this.mLocationDetectionDependencyStatus) + ", mConnectivityDependencyStatus=" + dependencyStatusToString(this.mConnectivityDependencyStatus) + ", mTimeZoneResolutionOperationStatus=" + operationStatusToString(this.mTimeZoneResolutionOperationStatus) + '}';
    }

    public static android.service.timezone.TimeZoneProviderStatus parseProviderStatus(java.lang.String str) {
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("TimeZoneProviderStatus\\{mLocationDetectionDependencyStatus=([^,]+), mConnectivityDependencyStatus=([^,]+), mTimeZoneResolutionOperationStatus=([^\\}]+)\\}").matcher(str);
        if (!matcher.matches()) {
            throw new java.lang.IllegalArgumentException("Unable to parse provider status: " + str);
        }
        return new android.service.timezone.TimeZoneProviderStatus(dependencyStatusFromString(matcher.group(1)), dependencyStatusFromString(matcher.group(2)), operationStatusFromString(matcher.group(3)));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mLocationDetectionDependencyStatus);
        parcel.writeInt(this.mConnectivityDependencyStatus);
        parcel.writeInt(this.mTimeZoneResolutionOperationStatus);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.service.timezone.TimeZoneProviderStatus timeZoneProviderStatus = (android.service.timezone.TimeZoneProviderStatus) obj;
        if (this.mLocationDetectionDependencyStatus == timeZoneProviderStatus.mLocationDetectionDependencyStatus && this.mConnectivityDependencyStatus == timeZoneProviderStatus.mConnectivityDependencyStatus && this.mTimeZoneResolutionOperationStatus == timeZoneProviderStatus.mTimeZoneResolutionOperationStatus) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mLocationDetectionDependencyStatus), java.lang.Integer.valueOf(this.mConnectivityDependencyStatus), java.lang.Integer.valueOf(this.mTimeZoneResolutionOperationStatus));
    }

    public boolean couldEnableTelephonyFallback() {
        return this.mLocationDetectionDependencyStatus == 4 || this.mLocationDetectionDependencyStatus == 6 || this.mConnectivityDependencyStatus == 4 || this.mConnectivityDependencyStatus == 6;
    }

    public static final class Builder {
        private int mConnectivityDependencyStatus;
        private int mLocationDetectionDependencyStatus;
        private int mTimeZoneResolutionOperationStatus;

        public Builder() {
            this.mLocationDetectionDependencyStatus = 0;
            this.mConnectivityDependencyStatus = 0;
            this.mTimeZoneResolutionOperationStatus = 0;
        }

        public Builder(android.service.timezone.TimeZoneProviderStatus timeZoneProviderStatus) {
            this.mLocationDetectionDependencyStatus = 0;
            this.mConnectivityDependencyStatus = 0;
            this.mTimeZoneResolutionOperationStatus = 0;
            this.mLocationDetectionDependencyStatus = timeZoneProviderStatus.mLocationDetectionDependencyStatus;
            this.mConnectivityDependencyStatus = timeZoneProviderStatus.mConnectivityDependencyStatus;
            this.mTimeZoneResolutionOperationStatus = timeZoneProviderStatus.mTimeZoneResolutionOperationStatus;
        }

        public android.service.timezone.TimeZoneProviderStatus.Builder setLocationDetectionDependencyStatus(int i) {
            this.mLocationDetectionDependencyStatus = i;
            return this;
        }

        public android.service.timezone.TimeZoneProviderStatus.Builder setConnectivityDependencyStatus(int i) {
            this.mConnectivityDependencyStatus = i;
            return this;
        }

        public android.service.timezone.TimeZoneProviderStatus.Builder setTimeZoneResolutionOperationStatus(int i) {
            this.mTimeZoneResolutionOperationStatus = i;
            return this;
        }

        public android.service.timezone.TimeZoneProviderStatus build() {
            return new android.service.timezone.TimeZoneProviderStatus(android.service.timezone.TimeZoneProviderStatus.requireValidDependencyStatus(this.mLocationDetectionDependencyStatus), android.service.timezone.TimeZoneProviderStatus.requireValidDependencyStatus(this.mConnectivityDependencyStatus), android.service.timezone.TimeZoneProviderStatus.requireValidOperationStatus(this.mTimeZoneResolutionOperationStatus));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int requireValidOperationStatus(int i) {
        if (i < 0 || i > 3) {
            throw new java.lang.IllegalArgumentException(java.lang.Integer.toString(i));
        }
        return i;
    }

    public static java.lang.String operationStatusToString(int i) {
        switch (i) {
            case 0:
                return "UNKNOWN";
            case 1:
                return "NOT_APPLICABLE";
            case 2:
                return "OK";
            case 3:
                return "FAILED";
            default:
                throw new java.lang.IllegalArgumentException("Unknown status: " + i);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int operationStatusFromString(java.lang.String str) {
        char c;
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("Empty status: " + str);
        }
        switch (str.hashCode()) {
            case 2524:
                if (str.equals("OK")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 433141802:
                if (str.equals("UNKNOWN")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 978028715:
                if (str.equals("NOT_APPLICABLE")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 2066319421:
                if (str.equals("FAILED")) {
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
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            default:
                throw new java.lang.IllegalArgumentException("Unknown status: " + str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int requireValidDependencyStatus(int i) {
        if (i < 0 || i > 6) {
            throw new java.lang.IllegalArgumentException(java.lang.Integer.toString(i));
        }
        return i;
    }

    public static java.lang.String dependencyStatusToString(int i) {
        switch (i) {
            case 0:
                return "UNKNOWN";
            case 1:
                return "NOT_APPLICABLE";
            case 2:
                return "OK";
            case 3:
                return "TEMPORARILY_UNAVAILABLE";
            case 4:
                return "BLOCKED_BY_ENVIRONMENT";
            case 5:
                return "DEGRADED_BY_SETTINGS";
            case 6:
                return "BLOCKED_BY_SETTINGS";
            default:
                throw new java.lang.IllegalArgumentException("Unknown status: " + i);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int dependencyStatusFromString(java.lang.String str) {
        char c;
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("Empty status: " + str);
        }
        switch (str.hashCode()) {
            case -1834303208:
                if (str.equals("BLOCKED_BY_SETTINGS")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -1113872161:
                if (str.equals("TEMPORARILY_UNAVAILABLE")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -822505250:
                if (str.equals("BLOCKED_BY_ENVIRONMENT")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 2524:
                if (str.equals("OK")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 433141802:
                if (str.equals("UNKNOWN")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 978028715:
                if (str.equals("NOT_APPLICABLE")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1705468794:
                if (str.equals("DEGRADED_BY_SETTINGS")) {
                    c = 5;
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
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            default:
                throw new java.lang.IllegalArgumentException("Unknown status: " + str);
        }
    }
}
