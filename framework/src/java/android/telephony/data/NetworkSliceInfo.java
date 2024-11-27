package android.telephony.data;

/* loaded from: classes3.dex */
public final class NetworkSliceInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.data.NetworkSliceInfo> CREATOR = new android.os.Parcelable.Creator<android.telephony.data.NetworkSliceInfo>() { // from class: android.telephony.data.NetworkSliceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.data.NetworkSliceInfo createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.data.NetworkSliceInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.data.NetworkSliceInfo[] newArray(int i) {
            return new android.telephony.data.NetworkSliceInfo[i];
        }
    };
    public static final int MAX_SLICE_DIFFERENTIATOR = 16777214;
    public static final int MAX_SLICE_STATUS = 5;
    public static final int MIN_SLICE_DIFFERENTIATOR = -1;
    public static final int MIN_SLICE_STATUS = 0;
    public static final int SLICE_DIFFERENTIATOR_NO_SLICE = -1;
    public static final int SLICE_SERVICE_TYPE_EMBB = 1;
    public static final int SLICE_SERVICE_TYPE_MIOT = 3;
    public static final int SLICE_SERVICE_TYPE_NONE = 0;
    public static final int SLICE_SERVICE_TYPE_URLLC = 2;
    public static final int SLICE_STATUS_ALLOWED = 2;
    public static final int SLICE_STATUS_CONFIGURED = 1;
    public static final int SLICE_STATUS_DEFAULT_CONFIGURED = 5;
    public static final int SLICE_STATUS_REJECTED_NOT_AVAILABLE_IN_PLMN = 3;
    public static final int SLICE_STATUS_REJECTED_NOT_AVAILABLE_IN_REGISTERED_AREA = 4;
    public static final int SLICE_STATUS_UNKNOWN = 0;
    private final int mMappedHplmnSliceDifferentiator;
    private final int mMappedHplmnSliceServiceType;
    private final int mSliceDifferentiator;
    private final int mSliceServiceType;
    private final int mStatus;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SliceServiceType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SliceStatus {
    }

    private NetworkSliceInfo(int i, int i2, int i3, int i4, int i5) {
        this.mSliceServiceType = i;
        this.mSliceDifferentiator = i2;
        this.mMappedHplmnSliceDifferentiator = i4;
        this.mMappedHplmnSliceServiceType = i3;
        this.mStatus = i5;
    }

    public int getSliceServiceType() {
        return this.mSliceServiceType;
    }

    public int getSliceDifferentiator() {
        return this.mSliceDifferentiator;
    }

    public int getMappedHplmnSliceServiceType() {
        return this.mMappedHplmnSliceServiceType;
    }

    public int getMappedHplmnSliceDifferentiator() {
        return this.mMappedHplmnSliceDifferentiator;
    }

    public int getStatus() {
        return this.mStatus;
    }

    private NetworkSliceInfo(android.os.Parcel parcel) {
        this.mSliceServiceType = parcel.readInt();
        this.mSliceDifferentiator = parcel.readInt();
        this.mMappedHplmnSliceServiceType = parcel.readInt();
        this.mMappedHplmnSliceDifferentiator = parcel.readInt();
        this.mStatus = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mSliceServiceType);
        parcel.writeInt(this.mSliceDifferentiator);
        parcel.writeInt(this.mMappedHplmnSliceServiceType);
        parcel.writeInt(this.mMappedHplmnSliceDifferentiator);
        parcel.writeInt(this.mStatus);
    }

    public java.lang.String toString() {
        return "SliceInfo{mSliceServiceType=" + sliceServiceTypeToString(this.mSliceServiceType) + ", mSliceDifferentiator=" + this.mSliceDifferentiator + ", mMappedHplmnSliceServiceType=" + sliceServiceTypeToString(this.mMappedHplmnSliceServiceType) + ", mMappedHplmnSliceDifferentiator=" + this.mMappedHplmnSliceDifferentiator + ", mStatus=" + sliceStatusToString(this.mStatus) + '}';
    }

    private static java.lang.String sliceServiceTypeToString(int i) {
        switch (i) {
            case 0:
                return android.security.keystore.KeyProperties.DIGEST_NONE;
            case 1:
                return "EMBB";
            case 2:
                return "URLLC";
            case 3:
                return "MIOT";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    private static java.lang.String sliceStatusToString(int i) {
        switch (i) {
            case 0:
                return "UNKNOWN";
            case 1:
                return "CONFIGURED";
            case 2:
                return "ALLOWED";
            case 3:
                return "REJECTED_NOT_AVAILABLE_IN_PLMN";
            case 4:
                return "REJECTED_NOT_AVAILABLE_IN_REGISTERED_AREA";
            case 5:
                return "DEFAULT_CONFIGURED";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.data.NetworkSliceInfo networkSliceInfo = (android.telephony.data.NetworkSliceInfo) obj;
        if (this.mSliceServiceType == networkSliceInfo.mSliceServiceType && this.mSliceDifferentiator == networkSliceInfo.mSliceDifferentiator && this.mMappedHplmnSliceServiceType == networkSliceInfo.mMappedHplmnSliceServiceType && this.mMappedHplmnSliceDifferentiator == networkSliceInfo.mMappedHplmnSliceDifferentiator && this.mStatus == networkSliceInfo.mStatus) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mSliceServiceType), java.lang.Integer.valueOf(this.mSliceDifferentiator), java.lang.Integer.valueOf(this.mMappedHplmnSliceServiceType), java.lang.Integer.valueOf(this.mMappedHplmnSliceDifferentiator), java.lang.Integer.valueOf(this.mStatus));
    }

    public static final class Builder {
        private int mSliceServiceType = 0;
        private int mSliceDifferentiator = -1;
        private int mMappedHplmnSliceServiceType = 0;
        private int mMappedHplmnSliceDifferentiator = -1;
        private int mStatus = 0;

        public android.telephony.data.NetworkSliceInfo.Builder setSliceServiceType(int i) {
            this.mSliceServiceType = i;
            return this;
        }

        public android.telephony.data.NetworkSliceInfo.Builder setSliceDifferentiator(int i) {
            if (i < -1 || i > 16777214) {
                throw new java.lang.IllegalArgumentException("The slice diffentiator value is out of range");
            }
            this.mSliceDifferentiator = i;
            return this;
        }

        public android.telephony.data.NetworkSliceInfo.Builder setMappedHplmnSliceServiceType(int i) {
            this.mMappedHplmnSliceServiceType = i;
            return this;
        }

        public android.telephony.data.NetworkSliceInfo.Builder setMappedHplmnSliceDifferentiator(int i) {
            if (i < -1 || i > 16777214) {
                throw new java.lang.IllegalArgumentException("The slice diffentiator value is out of range");
            }
            this.mMappedHplmnSliceDifferentiator = i;
            return this;
        }

        public android.telephony.data.NetworkSliceInfo.Builder setStatus(int i) {
            if (i < 0 || i > 5) {
                throw new java.lang.IllegalArgumentException("The slice status is not valid");
            }
            this.mStatus = i;
            return this;
        }

        public android.telephony.data.NetworkSliceInfo build() {
            return new android.telephony.data.NetworkSliceInfo(this.mSliceServiceType, this.mSliceDifferentiator, this.mMappedHplmnSliceServiceType, this.mMappedHplmnSliceDifferentiator, this.mStatus);
        }
    }
}
