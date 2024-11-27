package android.telephony.data;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class DataProfile implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telephony.data.DataProfile> CREATOR = new android.os.Parcelable.Creator<android.telephony.data.DataProfile>() { // from class: android.telephony.data.DataProfile.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.data.DataProfile createFromParcel(android.os.Parcel parcel) {
            return new android.telephony.data.DataProfile(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telephony.data.DataProfile[] newArray(int i) {
            return new android.telephony.data.DataProfile[i];
        }
    };
    public static final int TYPE_3GPP = 1;
    public static final int TYPE_3GPP2 = 2;
    public static final int TYPE_COMMON = 0;
    private final android.telephony.data.ApnSetting mApnSetting;
    private boolean mPreferred;
    private long mSetupTimestamp;
    private final android.telephony.data.TrafficDescriptor mTrafficDescriptor;
    private final int mType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Type {
    }

    private DataProfile(android.telephony.data.DataProfile.Builder builder) {
        this.mApnSetting = builder.mApnSetting;
        this.mTrafficDescriptor = builder.mTrafficDescriptor;
        this.mPreferred = builder.mPreferred;
        if (builder.mType != -1) {
            this.mType = builder.mType;
            return;
        }
        if (this.mApnSetting != null) {
            int networkTypeBitmask = this.mApnSetting.getNetworkTypeBitmask();
            if (networkTypeBitmask == 0) {
                this.mType = 0;
                return;
            }
            long j = networkTypeBitmask;
            if ((android.telephony.TelephonyManager.NETWORK_STANDARDS_FAMILY_BITMASK_3GPP2 & j) == j) {
                this.mType = 2;
                return;
            } else if ((android.telephony.TelephonyManager.NETWORK_STANDARDS_FAMILY_BITMASK_3GPP & j) == j) {
                this.mType = 1;
                return;
            } else {
                this.mType = 0;
                return;
            }
        }
        this.mType = 0;
    }

    private DataProfile(android.os.Parcel parcel) {
        this.mType = parcel.readInt();
        this.mApnSetting = (android.telephony.data.ApnSetting) parcel.readParcelable(android.telephony.data.ApnSetting.class.getClassLoader(), android.telephony.data.ApnSetting.class);
        this.mTrafficDescriptor = (android.telephony.data.TrafficDescriptor) parcel.readParcelable(android.telephony.data.TrafficDescriptor.class.getClassLoader(), android.telephony.data.TrafficDescriptor.class);
        this.mPreferred = parcel.readBoolean();
        this.mSetupTimestamp = parcel.readLong();
    }

    @java.lang.Deprecated
    public int getProfileId() {
        if (this.mApnSetting != null) {
            return this.mApnSetting.getProfileId();
        }
        return 0;
    }

    @java.lang.Deprecated
    public java.lang.String getApn() {
        if (this.mApnSetting != null) {
            return android.text.TextUtils.emptyIfNull(this.mApnSetting.getApnName());
        }
        return "";
    }

    @java.lang.Deprecated
    public int getProtocolType() {
        if (this.mApnSetting != null) {
            return this.mApnSetting.getProtocol();
        }
        return 2;
    }

    @java.lang.Deprecated
    public int getAuthType() {
        if (this.mApnSetting != null) {
            return this.mApnSetting.getAuthType();
        }
        return 0;
    }

    @java.lang.Deprecated
    public java.lang.String getUserName() {
        if (this.mApnSetting != null) {
            return this.mApnSetting.getUser();
        }
        return null;
    }

    @java.lang.Deprecated
    public java.lang.String getPassword() {
        if (this.mApnSetting != null) {
            return this.mApnSetting.getPassword();
        }
        return null;
    }

    public int getType() {
        return this.mType;
    }

    public int getMaxConnectionsTime() {
        if (this.mApnSetting != null) {
            return this.mApnSetting.getMaxConnsTime();
        }
        return 0;
    }

    public int getMaxConnections() {
        if (this.mApnSetting != null) {
            return this.mApnSetting.getMaxConns();
        }
        return 0;
    }

    public int getWaitTime() {
        if (this.mApnSetting != null) {
            return this.mApnSetting.getWaitTime();
        }
        return 0;
    }

    public boolean isEnabled() {
        if (this.mApnSetting != null) {
            return this.mApnSetting.isEnabled();
        }
        return true;
    }

    @java.lang.Deprecated
    public int getSupportedApnTypesBitmask() {
        if (this.mApnSetting != null) {
            return this.mApnSetting.getApnTypeBitmask();
        }
        return 0;
    }

    @java.lang.Deprecated
    public int getRoamingProtocolType() {
        if (this.mApnSetting != null) {
            return this.mApnSetting.getRoamingProtocol();
        }
        return 0;
    }

    @java.lang.Deprecated
    public int getBearerBitmask() {
        if (this.mApnSetting != null) {
            return this.mApnSetting.getNetworkTypeBitmask();
        }
        return 0;
    }

    @java.lang.Deprecated
    public int getMtu() {
        return getMtuV4();
    }

    @java.lang.Deprecated
    public int getMtuV4() {
        if (this.mApnSetting != null) {
            return this.mApnSetting.getMtuV4();
        }
        return 0;
    }

    @java.lang.Deprecated
    public int getMtuV6() {
        if (this.mApnSetting != null) {
            return this.mApnSetting.getMtuV6();
        }
        return 0;
    }

    @java.lang.Deprecated
    public boolean isPersistent() {
        if (this.mApnSetting != null) {
            return this.mApnSetting.isPersistent();
        }
        return false;
    }

    public void setPreferred(boolean z) {
        this.mPreferred = z;
    }

    public boolean isPreferred() {
        return this.mPreferred;
    }

    public android.telephony.data.ApnSetting getApnSetting() {
        return this.mApnSetting;
    }

    public android.telephony.data.TrafficDescriptor getTrafficDescriptor() {
        return this.mTrafficDescriptor;
    }

    public boolean canSatisfy(int[] iArr) {
        if (this.mApnSetting == null) {
            return false;
        }
        for (int i : iArr) {
            if (!canSatisfy(i)) {
                return false;
            }
        }
        return true;
    }

    public boolean canSatisfy(int i) {
        return this.mApnSetting != null && this.mApnSetting.canHandleType(networkCapabilityToApnType(i));
    }

    private static int networkCapabilityToApnType(int i) {
        switch (i) {
            case 0:
                return 2;
            case 1:
                return 4;
            case 2:
                return 8;
            case 3:
                return 32;
            case 4:
                return 64;
            case 5:
                return 128;
            case 7:
                return 256;
            case 8:
                return 32768;
            case 9:
                return 2048;
            case 10:
                return 512;
            case 12:
                return 17;
            case 23:
                return 1024;
            case 29:
                return 16384;
            case 30:
                return 4096;
            case 31:
                return 8192;
            default:
                return 0;
        }
    }

    public void setLastSetupTimestamp(long j) {
        this.mSetupTimestamp = j;
    }

    public long getLastSetupTimestamp() {
        return this.mSetupTimestamp;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "[DataProfile=" + this.mApnSetting + ", " + this.mTrafficDescriptor + ", preferred=" + this.mPreferred + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeParcelable(this.mApnSetting, i);
        parcel.writeParcelable(this.mTrafficDescriptor, i);
        parcel.writeBoolean(this.mPreferred);
        parcel.writeLong(this.mSetupTimestamp);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.telephony.data.DataProfile dataProfile = (android.telephony.data.DataProfile) obj;
        if (this.mType == dataProfile.mType && java.util.Objects.equals(this.mApnSetting, dataProfile.mApnSetting) && java.util.Objects.equals(this.mTrafficDescriptor, dataProfile.mTrafficDescriptor)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mType), this.mApnSetting, this.mTrafficDescriptor);
    }

    public static final class Builder {
        private java.lang.String mApn;
        private android.telephony.data.ApnSetting mApnSetting;
        private int mAuthType;
        private int mBearerBitmask;
        private int mMtuV4;
        private int mMtuV6;
        private java.lang.String mPassword;
        private boolean mPersistent;
        private boolean mPreferred;
        private int mProfileId;
        private int mProtocolType;
        private int mRoamingProtocolType;
        private int mSupportedApnTypesBitmask;
        private android.telephony.data.TrafficDescriptor mTrafficDescriptor;
        private java.lang.String mUserName;
        private int mType = -1;
        private boolean mEnabled = true;

        @java.lang.Deprecated
        public android.telephony.data.DataProfile.Builder setProfileId(int i) {
            this.mProfileId = i;
            return this;
        }

        @java.lang.Deprecated
        public android.telephony.data.DataProfile.Builder setApn(java.lang.String str) {
            this.mApn = str;
            return this;
        }

        @java.lang.Deprecated
        public android.telephony.data.DataProfile.Builder setProtocolType(int i) {
            this.mProtocolType = i;
            return this;
        }

        @java.lang.Deprecated
        public android.telephony.data.DataProfile.Builder setAuthType(int i) {
            this.mAuthType = i;
            return this;
        }

        @java.lang.Deprecated
        public android.telephony.data.DataProfile.Builder setUserName(java.lang.String str) {
            this.mUserName = str;
            return this;
        }

        @java.lang.Deprecated
        public android.telephony.data.DataProfile.Builder setPassword(java.lang.String str) {
            this.mPassword = str;
            return this;
        }

        public android.telephony.data.DataProfile.Builder setType(int i) {
            this.mType = i;
            return this;
        }

        public android.telephony.data.DataProfile.Builder enable(boolean z) {
            this.mEnabled = z;
            return this;
        }

        @java.lang.Deprecated
        public android.telephony.data.DataProfile.Builder setSupportedApnTypesBitmask(int i) {
            this.mSupportedApnTypesBitmask = i;
            return this;
        }

        @java.lang.Deprecated
        public android.telephony.data.DataProfile.Builder setRoamingProtocolType(int i) {
            this.mRoamingProtocolType = i;
            return this;
        }

        @java.lang.Deprecated
        public android.telephony.data.DataProfile.Builder setBearerBitmask(int i) {
            this.mBearerBitmask = i;
            return this;
        }

        @java.lang.Deprecated
        public android.telephony.data.DataProfile.Builder setMtu(int i) {
            this.mMtuV6 = i;
            this.mMtuV4 = i;
            return this;
        }

        @java.lang.Deprecated
        public android.telephony.data.DataProfile.Builder setMtuV4(int i) {
            this.mMtuV4 = i;
            return this;
        }

        @java.lang.Deprecated
        public android.telephony.data.DataProfile.Builder setMtuV6(int i) {
            this.mMtuV6 = i;
            return this;
        }

        public android.telephony.data.DataProfile.Builder setPreferred(boolean z) {
            this.mPreferred = z;
            return this;
        }

        @java.lang.Deprecated
        public android.telephony.data.DataProfile.Builder setPersistent(boolean z) {
            this.mPersistent = z;
            return this;
        }

        public android.telephony.data.DataProfile.Builder setApnSetting(android.telephony.data.ApnSetting apnSetting) {
            this.mApnSetting = apnSetting;
            return this;
        }

        public android.telephony.data.DataProfile.Builder setTrafficDescriptor(android.telephony.data.TrafficDescriptor trafficDescriptor) {
            this.mTrafficDescriptor = trafficDescriptor;
            return this;
        }

        public android.telephony.data.DataProfile build() {
            if (this.mApnSetting == null && this.mApn != null) {
                this.mApnSetting = new android.telephony.data.ApnSetting.Builder().setEntryName(this.mApn).setApnName(this.mApn).setApnTypeBitmask(this.mSupportedApnTypesBitmask).setAuthType(this.mAuthType).setCarrierEnabled(this.mEnabled).setModemCognitive(this.mPersistent).setMtuV4(this.mMtuV4).setMtuV6(this.mMtuV6).setNetworkTypeBitmask(this.mBearerBitmask).setProfileId(this.mProfileId).setPassword(this.mPassword).setProtocol(this.mProtocolType).setRoamingProtocol(this.mRoamingProtocolType).setUser(this.mUserName).build();
            }
            if (this.mApnSetting == null && this.mTrafficDescriptor == null) {
                throw new java.lang.IllegalArgumentException("APN setting and traffic descriptor can't be both null.");
            }
            return new android.telephony.data.DataProfile(this);
        }
    }
}
