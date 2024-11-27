package android.app.admin;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class DevicePolicyStringResource implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.admin.DevicePolicyStringResource> CREATOR = new android.os.Parcelable.Creator<android.app.admin.DevicePolicyStringResource>() { // from class: android.app.admin.DevicePolicyStringResource.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.DevicePolicyStringResource createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.DevicePolicyStringResource(parcel.readString(), parcel.readInt(), (android.app.admin.ParcelableResource) parcel.readTypedObject(android.app.admin.ParcelableResource.CREATOR));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.DevicePolicyStringResource[] newArray(int i) {
            return new android.app.admin.DevicePolicyStringResource[i];
        }
    };
    private android.app.admin.ParcelableResource mResource;
    private final int mResourceIdInCallingPackage;
    private final java.lang.String mStringId;

    public DevicePolicyStringResource(android.content.Context context, java.lang.String str, int i) {
        this(str, i, new android.app.admin.ParcelableResource(context, i, 2));
    }

    private DevicePolicyStringResource(java.lang.String str, int i, android.app.admin.ParcelableResource parcelableResource) {
        java.util.Objects.requireNonNull(str, "stringId must be provided.");
        java.util.Objects.requireNonNull(parcelableResource, "ParcelableResource must be provided.");
        this.mStringId = str;
        this.mResourceIdInCallingPackage = i;
        this.mResource = parcelableResource;
    }

    public java.lang.String getStringId() {
        return this.mStringId;
    }

    public int getResourceIdInCallingPackage() {
        return this.mResourceIdInCallingPackage;
    }

    public android.app.admin.ParcelableResource getResource() {
        return this.mResource;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.app.admin.DevicePolicyStringResource devicePolicyStringResource = (android.app.admin.DevicePolicyStringResource) obj;
        if (this.mStringId == devicePolicyStringResource.mStringId && this.mResourceIdInCallingPackage == devicePolicyStringResource.mResourceIdInCallingPackage && this.mResource.equals(devicePolicyStringResource.mResource)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mStringId, java.lang.Integer.valueOf(this.mResourceIdInCallingPackage), this.mResource);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mStringId);
        parcel.writeInt(this.mResourceIdInCallingPackage);
        parcel.writeTypedObject(this.mResource, i);
    }
}
