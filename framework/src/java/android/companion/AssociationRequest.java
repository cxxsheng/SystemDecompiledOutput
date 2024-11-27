package android.companion;

/* loaded from: classes.dex */
public final class AssociationRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.companion.AssociationRequest> CREATOR = new android.os.Parcelable.Creator<android.companion.AssociationRequest>() { // from class: android.companion.AssociationRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.companion.AssociationRequest[] newArray(int i) {
            return new android.companion.AssociationRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.companion.AssociationRequest createFromParcel(android.os.Parcel parcel) {
            return new android.companion.AssociationRequest(parcel);
        }
    };
    public static final java.lang.String DEVICE_PROFILE_APP_STREAMING = "android.app.role.COMPANION_DEVICE_APP_STREAMING";
    public static final java.lang.String DEVICE_PROFILE_AUTOMOTIVE_PROJECTION = "android.app.role.SYSTEM_AUTOMOTIVE_PROJECTION";
    public static final java.lang.String DEVICE_PROFILE_COMPUTER = "android.app.role.COMPANION_DEVICE_COMPUTER";
    public static final java.lang.String DEVICE_PROFILE_GLASSES = "android.app.role.COMPANION_DEVICE_GLASSES";
    public static final java.lang.String DEVICE_PROFILE_NEARBY_DEVICE_STREAMING = "android.app.role.COMPANION_DEVICE_NEARBY_DEVICE_STREAMING";
    public static final java.lang.String DEVICE_PROFILE_WATCH = "android.app.role.COMPANION_DEVICE_WATCH";
    private android.companion.AssociatedDevice mAssociatedDevice;
    private final long mCreationTime;
    private final java.util.List<android.companion.DeviceFilter<?>> mDeviceFilters;
    private final java.lang.String mDeviceProfile;
    private java.lang.String mDeviceProfilePrivilegesDescription;
    private java.lang.CharSequence mDisplayName;
    private final boolean mForceConfirmation;
    private java.lang.String mPackageName;
    private final boolean mSelfManaged;
    private final boolean mSingleDevice;
    private boolean mSkipPrompt;
    private int mUserId;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DeviceProfile {
    }

    private AssociationRequest(boolean z, java.util.List<android.companion.DeviceFilter<?>> list, java.lang.String str, java.lang.CharSequence charSequence, boolean z2, boolean z3) {
        this.mSingleDevice = z;
        this.mDeviceFilters = (java.util.List) java.util.Objects.requireNonNull(list);
        this.mDeviceProfile = str;
        this.mDisplayName = charSequence;
        this.mSelfManaged = z2;
        this.mForceConfirmation = z3;
        this.mCreationTime = java.lang.System.currentTimeMillis();
    }

    public java.lang.String getDeviceProfile() {
        return this.mDeviceProfile;
    }

    public java.lang.CharSequence getDisplayName() {
        return this.mDisplayName;
    }

    public boolean isSelfManaged() {
        return this.mSelfManaged;
    }

    public boolean isForceConfirmation() {
        return this.mForceConfirmation;
    }

    public boolean isSingleDevice() {
        return this.mSingleDevice;
    }

    public void setPackageName(java.lang.String str) {
        this.mPackageName = str;
    }

    public void setUserId(int i) {
        this.mUserId = i;
    }

    public void setDeviceProfilePrivilegesDescription(java.lang.String str) {
        this.mDeviceProfilePrivilegesDescription = str;
    }

    public void setSkipPrompt(boolean z) {
        this.mSkipPrompt = z;
    }

    public void setDisplayName(java.lang.CharSequence charSequence) {
        this.mDisplayName = charSequence;
    }

    public void setAssociatedDevice(android.companion.AssociatedDevice associatedDevice) {
        this.mAssociatedDevice = associatedDevice;
    }

    public java.util.List<android.companion.DeviceFilter<?>> getDeviceFilters() {
        return this.mDeviceFilters;
    }

    public static final class Builder extends android.provider.OneTimeUseBuilder<android.companion.AssociationRequest> {
        private java.lang.String mDeviceProfile;
        private java.lang.CharSequence mDisplayName;
        private boolean mSingleDevice = false;
        private java.util.ArrayList<android.companion.DeviceFilter<?>> mDeviceFilters = null;
        private boolean mSelfManaged = false;
        private boolean mForceConfirmation = false;

        public android.companion.AssociationRequest.Builder setSingleDevice(boolean z) {
            checkNotUsed();
            this.mSingleDevice = z;
            return this;
        }

        public android.companion.AssociationRequest.Builder addDeviceFilter(android.companion.DeviceFilter<?> deviceFilter) {
            checkNotUsed();
            if (deviceFilter != null) {
                this.mDeviceFilters = com.android.internal.util.ArrayUtils.add(this.mDeviceFilters, deviceFilter);
            }
            return this;
        }

        public android.companion.AssociationRequest.Builder setDeviceProfile(java.lang.String str) {
            checkNotUsed();
            this.mDeviceProfile = str;
            return this;
        }

        public android.companion.AssociationRequest.Builder setDisplayName(java.lang.CharSequence charSequence) {
            checkNotUsed();
            this.mDisplayName = (java.lang.CharSequence) java.util.Objects.requireNonNull(charSequence);
            return this;
        }

        public android.companion.AssociationRequest.Builder setSelfManaged(boolean z) {
            checkNotUsed();
            this.mSelfManaged = z;
            return this;
        }

        public android.companion.AssociationRequest.Builder setForceConfirmation(boolean z) {
            checkNotUsed();
            this.mForceConfirmation = z;
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.provider.OneTimeUseBuilder
        public android.companion.AssociationRequest build() {
            markUsed();
            if (this.mSelfManaged && this.mDisplayName == null) {
                throw new java.lang.IllegalStateException("Request for a self-managed association MUST provide the display name of the device");
            }
            return new android.companion.AssociationRequest(this.mSingleDevice, com.android.internal.util.CollectionUtils.emptyIfNull(this.mDeviceFilters), this.mDeviceProfile, this.mDisplayName, this.mSelfManaged, this.mForceConfirmation);
        }
    }

    public android.companion.AssociatedDevice getAssociatedDevice() {
        return this.mAssociatedDevice;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public int getUserId() {
        return this.mUserId;
    }

    public java.lang.String getDeviceProfilePrivilegesDescription() {
        return this.mDeviceProfilePrivilegesDescription;
    }

    public long getCreationTime() {
        return this.mCreationTime;
    }

    public boolean isSkipPrompt() {
        return this.mSkipPrompt;
    }

    public java.lang.String toString() {
        return "AssociationRequest { singleDevice = " + this.mSingleDevice + ", deviceFilters = " + this.mDeviceFilters + ", deviceProfile = " + this.mDeviceProfile + ", displayName = " + ((java.lang.Object) this.mDisplayName) + ", associatedDevice = " + this.mAssociatedDevice + ", selfManaged = " + this.mSelfManaged + ", forceConfirmation = " + this.mForceConfirmation + ", packageName = " + this.mPackageName + ", userId = " + this.mUserId + ", deviceProfilePrivilegesDescription = " + this.mDeviceProfilePrivilegesDescription + ", creationTime = " + this.mCreationTime + ", skipPrompt = " + this.mSkipPrompt + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.companion.AssociationRequest associationRequest = (android.companion.AssociationRequest) obj;
        if (this.mSingleDevice == associationRequest.mSingleDevice && java.util.Objects.equals(this.mDeviceFilters, associationRequest.mDeviceFilters) && java.util.Objects.equals(this.mDeviceProfile, associationRequest.mDeviceProfile) && java.util.Objects.equals(this.mDisplayName, associationRequest.mDisplayName) && java.util.Objects.equals(this.mAssociatedDevice, associationRequest.mAssociatedDevice) && this.mSelfManaged == associationRequest.mSelfManaged && this.mForceConfirmation == associationRequest.mForceConfirmation && java.util.Objects.equals(this.mPackageName, associationRequest.mPackageName) && this.mUserId == associationRequest.mUserId && java.util.Objects.equals(this.mDeviceProfilePrivilegesDescription, associationRequest.mDeviceProfilePrivilegesDescription) && this.mCreationTime == associationRequest.mCreationTime && this.mSkipPrompt == associationRequest.mSkipPrompt) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((((((((((((((((((java.lang.Boolean.hashCode(this.mSingleDevice) + 31) * 31) + java.util.Objects.hashCode(this.mDeviceFilters)) * 31) + java.util.Objects.hashCode(this.mDeviceProfile)) * 31) + java.util.Objects.hashCode(this.mDisplayName)) * 31) + java.util.Objects.hashCode(this.mAssociatedDevice)) * 31) + java.lang.Boolean.hashCode(this.mSelfManaged)) * 31) + java.lang.Boolean.hashCode(this.mForceConfirmation)) * 31) + java.util.Objects.hashCode(this.mPackageName)) * 31) + this.mUserId) * 31) + java.util.Objects.hashCode(this.mDeviceProfilePrivilegesDescription)) * 31) + java.lang.Long.hashCode(this.mCreationTime)) * 31) + java.lang.Boolean.hashCode(this.mSkipPrompt);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        boolean z = this.mSingleDevice;
        int i2 = z;
        if (this.mSelfManaged) {
            i2 = (z ? 1 : 0) | 2;
        }
        int i3 = i2;
        if (this.mForceConfirmation) {
            i3 = (i2 == true ? 1 : 0) | 4;
        }
        int i4 = i3;
        if (this.mSkipPrompt) {
            i4 = (i3 == true ? 1 : 0) | 8;
        }
        int i5 = i4;
        if (this.mDeviceProfile != null) {
            i5 = (i4 == true ? 1 : 0) | 16;
        }
        int i6 = i5;
        if (this.mDisplayName != null) {
            i6 = (i5 == true ? 1 : 0) | 32;
        }
        int i7 = i6;
        if (this.mAssociatedDevice != null) {
            i7 = (i6 == true ? 1 : 0) | 64;
        }
        int i8 = i7;
        if (this.mPackageName != null) {
            i8 = (i7 == true ? 1 : 0) | 128;
        }
        int i9 = i8;
        if (this.mDeviceProfilePrivilegesDescription != null) {
            i9 = (i8 == true ? 1 : 0) | 256;
        }
        parcel.writeInt(i9);
        parcel.writeParcelableList(this.mDeviceFilters, i);
        if (this.mDeviceProfile != null) {
            parcel.writeString(this.mDeviceProfile);
        }
        if (this.mDisplayName != null) {
            parcel.writeCharSequence(this.mDisplayName);
        }
        if (this.mAssociatedDevice != null) {
            parcel.writeTypedObject(this.mAssociatedDevice, i);
        }
        if (this.mPackageName != null) {
            parcel.writeString(this.mPackageName);
        }
        parcel.writeInt(this.mUserId);
        if (this.mDeviceProfilePrivilegesDescription != null) {
            parcel.writeString8(this.mDeviceProfilePrivilegesDescription);
        }
        parcel.writeLong(this.mCreationTime);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    AssociationRequest(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        boolean z = (readInt & 1) != 0;
        boolean z2 = (readInt & 2) != 0;
        boolean z3 = (readInt & 4) != 0;
        boolean z4 = (readInt & 8) != 0;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readParcelableList(arrayList, android.companion.DeviceFilter.class.getClassLoader(), android.companion.DeviceFilter.class);
        java.lang.String readString = (readInt & 16) == 0 ? null : parcel.readString();
        java.lang.CharSequence readCharSequence = (readInt & 32) == 0 ? null : parcel.readCharSequence();
        android.companion.AssociatedDevice associatedDevice = (readInt & 64) == 0 ? null : (android.companion.AssociatedDevice) parcel.readTypedObject(android.companion.AssociatedDevice.CREATOR);
        java.lang.String readString2 = (readInt & 128) == 0 ? null : parcel.readString();
        int readInt2 = parcel.readInt();
        java.lang.String readString8 = (readInt & 256) == 0 ? null : parcel.readString8();
        long readLong = parcel.readLong();
        this.mSingleDevice = z;
        this.mDeviceFilters = arrayList;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mDeviceFilters);
        this.mDeviceProfile = readString;
        this.mDisplayName = readCharSequence;
        this.mAssociatedDevice = associatedDevice;
        this.mSelfManaged = z2;
        this.mForceConfirmation = z3;
        this.mPackageName = readString2;
        this.mUserId = readInt2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.UserIdInt>) android.annotation.UserIdInt.class, (android.annotation.UserIdInt) null, this.mUserId);
        this.mDeviceProfilePrivilegesDescription = readString8;
        this.mCreationTime = readLong;
        this.mSkipPrompt = z4;
    }
}
