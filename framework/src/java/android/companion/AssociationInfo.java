package android.companion;

/* loaded from: classes.dex */
public final class AssociationInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.companion.AssociationInfo> CREATOR = new android.os.Parcelable.Creator<android.companion.AssociationInfo>() { // from class: android.companion.AssociationInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.companion.AssociationInfo[] newArray(int i) {
            return new android.companion.AssociationInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.companion.AssociationInfo createFromParcel(android.os.Parcel parcel) {
            return new android.companion.AssociationInfo(parcel);
        }
    };
    private static final java.lang.String LAST_TIME_CONNECTED_NONE = "None";
    private final android.companion.AssociatedDevice mAssociatedDevice;
    private final android.net.MacAddress mDeviceMacAddress;
    private final java.lang.String mDeviceProfile;
    private final java.lang.CharSequence mDisplayName;
    private final int mId;
    private final long mLastTimeConnectedMs;
    private final boolean mNotifyOnDeviceNearby;
    private final java.lang.String mPackageName;
    private final boolean mPending;
    private final boolean mRevoked;
    private final boolean mSelfManaged;
    private final int mSystemDataSyncFlags;
    private final java.lang.String mTag;
    private final long mTimeApprovedMs;
    private final int mUserId;

    public AssociationInfo(int i, int i2, java.lang.String str, java.lang.String str2, android.net.MacAddress macAddress, java.lang.CharSequence charSequence, java.lang.String str3, android.companion.AssociatedDevice associatedDevice, boolean z, boolean z2, boolean z3, boolean z4, long j, long j2, int i3) {
        if (i <= 0) {
            throw new java.lang.IllegalArgumentException("Association ID should be greater than 0");
        }
        if (macAddress == null && charSequence == null) {
            throw new java.lang.IllegalArgumentException("MAC address and the Display Name must NOT be null at the same time");
        }
        this.mId = i;
        this.mUserId = i2;
        this.mPackageName = str;
        this.mDeviceMacAddress = macAddress;
        this.mDisplayName = charSequence;
        this.mTag = str2;
        this.mDeviceProfile = str3;
        this.mAssociatedDevice = associatedDevice;
        this.mSelfManaged = z;
        this.mNotifyOnDeviceNearby = z2;
        this.mRevoked = z3;
        this.mPending = z4;
        this.mTimeApprovedMs = j;
        this.mLastTimeConnectedMs = j2;
        this.mSystemDataSyncFlags = i3;
    }

    public int getId() {
        return this.mId;
    }

    public int getUserId() {
        return this.mUserId;
    }

    @android.annotation.SystemApi
    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public java.lang.String getTag() {
        return this.mTag;
    }

    public android.net.MacAddress getDeviceMacAddress() {
        return this.mDeviceMacAddress;
    }

    public java.lang.String getDeviceMacAddressAsString() {
        if (this.mDeviceMacAddress != null) {
            return this.mDeviceMacAddress.toString().toUpperCase();
        }
        return null;
    }

    public java.lang.CharSequence getDisplayName() {
        return this.mDisplayName;
    }

    public java.lang.String getDeviceProfile() {
        return this.mDeviceProfile;
    }

    public android.companion.AssociatedDevice getAssociatedDevice() {
        return this.mAssociatedDevice;
    }

    public boolean isSelfManaged() {
        return this.mSelfManaged;
    }

    public boolean isNotifyOnDeviceNearby() {
        return this.mNotifyOnDeviceNearby;
    }

    public long getTimeApprovedMs() {
        return this.mTimeApprovedMs;
    }

    public boolean belongsToPackage(int i, java.lang.String str) {
        return this.mUserId == i && java.util.Objects.equals(this.mPackageName, str);
    }

    public boolean isRevoked() {
        return this.mRevoked;
    }

    public boolean isPending() {
        return this.mPending;
    }

    public long getLastTimeConnectedMs() {
        return this.mLastTimeConnectedMs;
    }

    public int getSystemDataSyncFlags() {
        return this.mSystemDataSyncFlags;
    }

    public boolean isLinkedTo(java.lang.String str) {
        if (this.mSelfManaged || str == null) {
            return false;
        }
        try {
            return android.net.MacAddress.fromString(str).equals(this.mDeviceMacAddress);
        } catch (java.lang.IllegalArgumentException e) {
            return false;
        }
    }

    public boolean shouldBindWhenPresent() {
        return this.mNotifyOnDeviceNearby || this.mSelfManaged;
    }

    public java.lang.String toShortString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("id=").append(this.mId);
        if (this.mDeviceMacAddress != null) {
            sb.append(", addr=").append(getDeviceMacAddressAsString());
        }
        if (this.mSelfManaged) {
            sb.append(", self-managed");
        }
        sb.append(", pkg=u").append(this.mUserId).append('/').append(this.mPackageName);
        return sb.toString();
    }

    public java.lang.String toString() {
        return "Association{mId=" + this.mId + ", mUserId=" + this.mUserId + ", mPackageName='" + this.mPackageName + android.text.format.DateFormat.QUOTE + ", mTag='" + this.mTag + android.text.format.DateFormat.QUOTE + ", mDeviceMacAddress=" + this.mDeviceMacAddress + ", mDisplayName='" + ((java.lang.Object) this.mDisplayName) + android.text.format.DateFormat.QUOTE + ", mDeviceProfile='" + this.mDeviceProfile + android.text.format.DateFormat.QUOTE + ", mSelfManaged=" + this.mSelfManaged + ", mAssociatedDevice=" + this.mAssociatedDevice + ", mNotifyOnDeviceNearby=" + this.mNotifyOnDeviceNearby + ", mRevoked=" + this.mRevoked + ", mPending=" + this.mPending + ", mTimeApprovedMs=" + new java.util.Date(this.mTimeApprovedMs) + ", mLastTimeConnectedMs=" + (this.mLastTimeConnectedMs == Long.MAX_VALUE ? "None" : new java.util.Date(this.mLastTimeConnectedMs)) + ", mSystemDataSyncFlags=" + this.mSystemDataSyncFlags + '}';
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.companion.AssociationInfo)) {
            return false;
        }
        android.companion.AssociationInfo associationInfo = (android.companion.AssociationInfo) obj;
        return this.mId == associationInfo.mId && this.mUserId == associationInfo.mUserId && this.mSelfManaged == associationInfo.mSelfManaged && this.mNotifyOnDeviceNearby == associationInfo.mNotifyOnDeviceNearby && this.mRevoked == associationInfo.mRevoked && this.mPending == associationInfo.mPending && this.mTimeApprovedMs == associationInfo.mTimeApprovedMs && this.mLastTimeConnectedMs == associationInfo.mLastTimeConnectedMs && java.util.Objects.equals(this.mPackageName, associationInfo.mPackageName) && java.util.Objects.equals(this.mTag, associationInfo.mTag) && java.util.Objects.equals(this.mDeviceMacAddress, associationInfo.mDeviceMacAddress) && java.util.Objects.equals(this.mDisplayName, associationInfo.mDisplayName) && java.util.Objects.equals(this.mDeviceProfile, associationInfo.mDeviceProfile) && java.util.Objects.equals(this.mAssociatedDevice, associationInfo.mAssociatedDevice) && this.mSystemDataSyncFlags == associationInfo.mSystemDataSyncFlags;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mId), java.lang.Integer.valueOf(this.mUserId), this.mPackageName, this.mTag, this.mDeviceMacAddress, this.mDisplayName, this.mDeviceProfile, this.mAssociatedDevice, java.lang.Boolean.valueOf(this.mSelfManaged), java.lang.Boolean.valueOf(this.mNotifyOnDeviceNearby), java.lang.Boolean.valueOf(this.mRevoked), java.lang.Boolean.valueOf(this.mPending), java.lang.Long.valueOf(this.mTimeApprovedMs), java.lang.Long.valueOf(this.mLastTimeConnectedMs), java.lang.Integer.valueOf(this.mSystemDataSyncFlags));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mUserId);
        parcel.writeString(this.mPackageName);
        parcel.writeString(this.mTag);
        parcel.writeTypedObject(this.mDeviceMacAddress, 0);
        parcel.writeCharSequence(this.mDisplayName);
        parcel.writeString(this.mDeviceProfile);
        parcel.writeTypedObject(this.mAssociatedDevice, 0);
        parcel.writeBoolean(this.mSelfManaged);
        parcel.writeBoolean(this.mNotifyOnDeviceNearby);
        parcel.writeBoolean(this.mRevoked);
        parcel.writeBoolean(this.mPending);
        parcel.writeLong(this.mTimeApprovedMs);
        parcel.writeLong(this.mLastTimeConnectedMs);
        parcel.writeInt(this.mSystemDataSyncFlags);
    }

    private AssociationInfo(android.os.Parcel parcel) {
        this.mId = parcel.readInt();
        this.mUserId = parcel.readInt();
        this.mPackageName = parcel.readString();
        this.mTag = parcel.readString();
        this.mDeviceMacAddress = (android.net.MacAddress) parcel.readTypedObject(android.net.MacAddress.CREATOR);
        this.mDisplayName = parcel.readCharSequence();
        this.mDeviceProfile = parcel.readString();
        this.mAssociatedDevice = (android.companion.AssociatedDevice) parcel.readTypedObject(android.companion.AssociatedDevice.CREATOR);
        this.mSelfManaged = parcel.readBoolean();
        this.mNotifyOnDeviceNearby = parcel.readBoolean();
        this.mRevoked = parcel.readBoolean();
        this.mPending = parcel.readBoolean();
        this.mTimeApprovedMs = parcel.readLong();
        this.mLastTimeConnectedMs = parcel.readLong();
        this.mSystemDataSyncFlags = parcel.readInt();
    }

    public static final class Builder {
        private android.companion.AssociatedDevice mAssociatedDevice;
        private android.net.MacAddress mDeviceMacAddress;
        private java.lang.String mDeviceProfile;
        private java.lang.CharSequence mDisplayName;
        private final int mId;
        private long mLastTimeConnectedMs;
        private boolean mNotifyOnDeviceNearby;
        private final java.lang.String mPackageName;
        private boolean mPending;
        private boolean mRevoked;
        private boolean mSelfManaged;
        private int mSystemDataSyncFlags;
        private java.lang.String mTag;
        private long mTimeApprovedMs;
        private final int mUserId;

        public Builder(int i, int i2, java.lang.String str) {
            this.mId = i;
            this.mUserId = i2;
            this.mPackageName = str;
        }

        public Builder(android.companion.AssociationInfo associationInfo) {
            this.mId = associationInfo.mId;
            this.mUserId = associationInfo.mUserId;
            this.mPackageName = associationInfo.mPackageName;
            this.mTag = associationInfo.mTag;
            this.mDeviceMacAddress = associationInfo.mDeviceMacAddress;
            this.mDisplayName = associationInfo.mDisplayName;
            this.mDeviceProfile = associationInfo.mDeviceProfile;
            this.mAssociatedDevice = associationInfo.mAssociatedDevice;
            this.mSelfManaged = associationInfo.mSelfManaged;
            this.mNotifyOnDeviceNearby = associationInfo.mNotifyOnDeviceNearby;
            this.mRevoked = associationInfo.mRevoked;
            this.mPending = associationInfo.mPending;
            this.mTimeApprovedMs = associationInfo.mTimeApprovedMs;
            this.mLastTimeConnectedMs = associationInfo.mLastTimeConnectedMs;
            this.mSystemDataSyncFlags = associationInfo.mSystemDataSyncFlags;
        }

        public Builder(int i, int i2, java.lang.String str, android.companion.AssociationInfo associationInfo) {
            this.mId = i;
            this.mUserId = i2;
            this.mPackageName = str;
            this.mTag = associationInfo.mTag;
            this.mDeviceMacAddress = associationInfo.mDeviceMacAddress;
            this.mDisplayName = associationInfo.mDisplayName;
            this.mDeviceProfile = associationInfo.mDeviceProfile;
            this.mAssociatedDevice = associationInfo.mAssociatedDevice;
            this.mSelfManaged = associationInfo.mSelfManaged;
            this.mNotifyOnDeviceNearby = associationInfo.mNotifyOnDeviceNearby;
            this.mRevoked = associationInfo.mRevoked;
            this.mPending = associationInfo.mPending;
            this.mTimeApprovedMs = associationInfo.mTimeApprovedMs;
            this.mLastTimeConnectedMs = associationInfo.mLastTimeConnectedMs;
            this.mSystemDataSyncFlags = associationInfo.mSystemDataSyncFlags;
        }

        public android.companion.AssociationInfo.Builder setTag(java.lang.String str) {
            this.mTag = str;
            return this;
        }

        public android.companion.AssociationInfo.Builder setDeviceMacAddress(android.net.MacAddress macAddress) {
            this.mDeviceMacAddress = macAddress;
            return this;
        }

        public android.companion.AssociationInfo.Builder setDisplayName(java.lang.CharSequence charSequence) {
            this.mDisplayName = charSequence;
            return this;
        }

        public android.companion.AssociationInfo.Builder setDeviceProfile(java.lang.String str) {
            this.mDeviceProfile = str;
            return this;
        }

        public android.companion.AssociationInfo.Builder setAssociatedDevice(android.companion.AssociatedDevice associatedDevice) {
            this.mAssociatedDevice = associatedDevice;
            return this;
        }

        public android.companion.AssociationInfo.Builder setSelfManaged(boolean z) {
            this.mSelfManaged = z;
            return this;
        }

        public android.companion.AssociationInfo.Builder setNotifyOnDeviceNearby(boolean z) {
            this.mNotifyOnDeviceNearby = z;
            return this;
        }

        public android.companion.AssociationInfo.Builder setRevoked(boolean z) {
            this.mRevoked = z;
            return this;
        }

        public android.companion.AssociationInfo.Builder setPending(boolean z) {
            this.mPending = z;
            return this;
        }

        public android.companion.AssociationInfo.Builder setTimeApproved(long j) {
            if (j < 0) {
                throw new java.lang.IllegalArgumentException("timeApprovedMs must be positive. Was given (" + j + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
            }
            this.mTimeApprovedMs = j;
            return this;
        }

        public android.companion.AssociationInfo.Builder setLastTimeConnected(long j) {
            if (j < 0) {
                throw new java.lang.IllegalArgumentException("lastTimeConnectedMs must not be negative! (Given " + j + " )");
            }
            this.mLastTimeConnectedMs = j;
            return this;
        }

        public android.companion.AssociationInfo.Builder setSystemDataSyncFlags(int i) {
            this.mSystemDataSyncFlags = i;
            return this;
        }

        public android.companion.AssociationInfo build() {
            if (this.mId <= 0) {
                throw new java.lang.IllegalArgumentException("Association ID should be greater than 0");
            }
            if (this.mDeviceMacAddress == null && this.mDisplayName == null) {
                throw new java.lang.IllegalArgumentException("MAC address and the display name must NOT be null at the same time");
            }
            return new android.companion.AssociationInfo(this.mId, this.mUserId, this.mPackageName, this.mTag, this.mDeviceMacAddress, this.mDisplayName, this.mDeviceProfile, this.mAssociatedDevice, this.mSelfManaged, this.mNotifyOnDeviceNearby, this.mRevoked, this.mPending, this.mTimeApprovedMs, this.mLastTimeConnectedMs, this.mSystemDataSyncFlags);
        }
    }
}
