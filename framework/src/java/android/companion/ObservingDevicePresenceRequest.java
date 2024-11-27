package android.companion;

/* loaded from: classes.dex */
public final class ObservingDevicePresenceRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.companion.ObservingDevicePresenceRequest> CREATOR = new android.os.Parcelable.Creator<android.companion.ObservingDevicePresenceRequest>() { // from class: android.companion.ObservingDevicePresenceRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.companion.ObservingDevicePresenceRequest[] newArray(int i) {
            return new android.companion.ObservingDevicePresenceRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.companion.ObservingDevicePresenceRequest createFromParcel(android.os.Parcel parcel) {
            return new android.companion.ObservingDevicePresenceRequest(parcel);
        }
    };
    private static final int PARCEL_UUID_NOT_NULL = 1;
    private static final int PARCEL_UUID_NULL = 0;
    private final int mAssociationId;
    private final android.os.ParcelUuid mUuid;

    private ObservingDevicePresenceRequest(int i, android.os.ParcelUuid parcelUuid) {
        this.mAssociationId = i;
        this.mUuid = parcelUuid;
    }

    private ObservingDevicePresenceRequest(android.os.Parcel parcel) {
        this.mAssociationId = parcel.readInt();
        if (parcel.readInt() == 0) {
            this.mUuid = null;
        } else {
            this.mUuid = android.os.ParcelUuid.CREATOR.createFromParcel(parcel);
        }
    }

    public int getAssociationId() {
        return this.mAssociationId;
    }

    public android.os.ParcelUuid getUuid() {
        return this.mUuid;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mAssociationId);
        if (this.mUuid == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.mUuid.writeToParcel(parcel, i);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "ObservingDevicePresenceRequest { Association Id= " + this.mAssociationId + ",ParcelUuid= " + this.mUuid + "}";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.companion.ObservingDevicePresenceRequest)) {
            return false;
        }
        android.companion.ObservingDevicePresenceRequest observingDevicePresenceRequest = (android.companion.ObservingDevicePresenceRequest) obj;
        return java.util.Objects.equals(this.mUuid, observingDevicePresenceRequest.mUuid) && this.mAssociationId == observingDevicePresenceRequest.mAssociationId;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mAssociationId), this.mUuid);
    }

    public static final class Builder extends android.provider.OneTimeUseBuilder<android.companion.ObservingDevicePresenceRequest> {
        private int mAssociationId = -1;
        private android.os.ParcelUuid mUuid;

        public android.companion.ObservingDevicePresenceRequest.Builder setAssociationId(int i) {
            checkNotUsed();
            this.mAssociationId = i;
            return this;
        }

        public android.companion.ObservingDevicePresenceRequest.Builder setUuid(android.os.ParcelUuid parcelUuid) {
            checkNotUsed();
            this.mUuid = parcelUuid;
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.provider.OneTimeUseBuilder
        public android.companion.ObservingDevicePresenceRequest build() {
            markUsed();
            if (this.mUuid != null && this.mAssociationId != -1) {
                throw new java.lang.IllegalStateException("Cannot observe device presence based on both ParcelUuid and association ID. Choose one or the other.");
            }
            if (this.mUuid == null && this.mAssociationId <= 0) {
                throw new java.lang.IllegalStateException("Must provide either a ParcelUuid or a valid association ID to observe device presence.");
            }
            return new android.companion.ObservingDevicePresenceRequest(this.mAssociationId, this.mUuid);
        }
    }
}
