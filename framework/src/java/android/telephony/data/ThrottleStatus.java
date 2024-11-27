package android.telephony.data;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class ThrottleStatus implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.data.ThrottleStatus> CREATOR = new android.os.Parcelable.Creator<android.telephony.data.ThrottleStatus>() { // from class: android.telephony.data.ThrottleStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.data.ThrottleStatus createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.data.ThrottleStatus(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.data.ThrottleStatus[] newArray(int i) {
            return new android.telephony.data.ThrottleStatus[i];
        }
    };
    public static final int RETRY_TYPE_HANDOVER = 3;
    public static final int RETRY_TYPE_NEW_CONNECTION = 2;
    public static final int RETRY_TYPE_NONE = 1;
    public static final int THROTTLE_TYPE_ELAPSED_TIME = 2;
    public static final int THROTTLE_TYPE_NONE = 1;
    private final int mApnType;
    private final int mRetryType;
    private final int mSlotIndex;
    private final long mThrottleExpiryTimeMillis;
    private final int mThrottleType;
    private final int mTransportType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RetryType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ThrottleType {
    }

    public int getSlotIndex() {
        return this.mSlotIndex;
    }

    public int getTransportType() {
        return this.mTransportType;
    }

    public int getApnType() {
        return this.mApnType;
    }

    public int getThrottleType() {
        return this.mThrottleType;
    }

    public int getRetryType() {
        return this.mRetryType;
    }

    public long getThrottleExpiryTimeMillis() {
        return this.mThrottleExpiryTimeMillis;
    }

    private ThrottleStatus(int i, int i2, int i3, int i4, long j, int i5) {
        this.mSlotIndex = i;
        this.mTransportType = i2;
        this.mApnType = i3;
        this.mThrottleType = i4;
        this.mThrottleExpiryTimeMillis = j;
        this.mRetryType = i5;
    }

    private ThrottleStatus(android.os.Parcel parcel) {
        this.mSlotIndex = parcel.readInt();
        this.mTransportType = parcel.readInt();
        this.mApnType = parcel.readInt();
        this.mThrottleExpiryTimeMillis = parcel.readLong();
        this.mRetryType = parcel.readInt();
        this.mThrottleType = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mSlotIndex);
        parcel.writeInt(this.mTransportType);
        parcel.writeInt(this.mApnType);
        parcel.writeLong(this.mThrottleExpiryTimeMillis);
        parcel.writeInt(this.mRetryType);
        parcel.writeInt(this.mThrottleType);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mSlotIndex), java.lang.Integer.valueOf(this.mApnType), java.lang.Integer.valueOf(this.mRetryType), java.lang.Integer.valueOf(this.mThrottleType), java.lang.Long.valueOf(this.mThrottleExpiryTimeMillis), java.lang.Integer.valueOf(this.mTransportType));
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.telephony.data.ThrottleStatus)) {
            return false;
        }
        android.telephony.data.ThrottleStatus throttleStatus = (android.telephony.data.ThrottleStatus) obj;
        return this.mSlotIndex == throttleStatus.mSlotIndex && this.mApnType == throttleStatus.mApnType && this.mRetryType == throttleStatus.mRetryType && this.mThrottleType == throttleStatus.mThrottleType && this.mThrottleExpiryTimeMillis == throttleStatus.mThrottleExpiryTimeMillis && this.mTransportType == throttleStatus.mTransportType;
    }

    public java.lang.String toString() {
        return "ThrottleStatus{mSlotIndex=" + this.mSlotIndex + ", mTransportType=" + this.mTransportType + ", mApnType=" + android.telephony.data.ApnSetting.getApnTypeString(this.mApnType) + ", mThrottleExpiryTimeMillis=" + this.mThrottleExpiryTimeMillis + ", mRetryType=" + this.mRetryType + ", mThrottleType=" + this.mThrottleType + '}';
    }

    public static final class Builder {
        public static final long NO_THROTTLE_EXPIRY_TIME = -1;
        private int mApnType;
        private int mRetryType;
        private int mSlotIndex;
        private long mThrottleExpiryTimeMillis;
        private int mThrottleType;
        private int mTransportType;

        public android.telephony.data.ThrottleStatus.Builder setSlotIndex(int i) {
            this.mSlotIndex = i;
            return this;
        }

        public android.telephony.data.ThrottleStatus.Builder setTransportType(int i) {
            this.mTransportType = i;
            return this;
        }

        public android.telephony.data.ThrottleStatus.Builder setApnType(int i) {
            this.mApnType = i;
            return this;
        }

        public android.telephony.data.ThrottleStatus.Builder setThrottleExpiryTimeMillis(long j) {
            if (j >= 0) {
                this.mThrottleExpiryTimeMillis = j;
                this.mThrottleType = 2;
                return this;
            }
            throw new java.lang.IllegalArgumentException("throttleExpiryTimeMillis must be greater than or equal to 0");
        }

        public android.telephony.data.ThrottleStatus.Builder setNoThrottle() {
            this.mThrottleType = 1;
            this.mThrottleExpiryTimeMillis = -1L;
            return this;
        }

        public android.telephony.data.ThrottleStatus.Builder setRetryType(int i) {
            this.mRetryType = i;
            return this;
        }

        public android.telephony.data.ThrottleStatus build() {
            return new android.telephony.data.ThrottleStatus(this.mSlotIndex, this.mTransportType, this.mApnType, this.mThrottleType, this.mThrottleExpiryTimeMillis, this.mRetryType);
        }
    }
}
