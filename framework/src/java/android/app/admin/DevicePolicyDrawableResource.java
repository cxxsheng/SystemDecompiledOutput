package android.app.admin;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class DevicePolicyDrawableResource implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.admin.DevicePolicyDrawableResource> CREATOR = new android.os.Parcelable.Creator<android.app.admin.DevicePolicyDrawableResource>() { // from class: android.app.admin.DevicePolicyDrawableResource.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.DevicePolicyDrawableResource createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.DevicePolicyDrawableResource(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readInt(), (android.app.admin.ParcelableResource) parcel.readTypedObject(android.app.admin.ParcelableResource.CREATOR));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.DevicePolicyDrawableResource[] newArray(int i) {
            return new android.app.admin.DevicePolicyDrawableResource[i];
        }
    };
    private final java.lang.String mDrawableId;
    private final java.lang.String mDrawableSource;
    private final java.lang.String mDrawableStyle;
    private android.app.admin.ParcelableResource mResource;
    private final int mResourceIdInCallingPackage;

    public DevicePolicyDrawableResource(android.content.Context context, java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        this(str, str2, str3, i, new android.app.admin.ParcelableResource(context, i, 1));
    }

    private DevicePolicyDrawableResource(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, android.app.admin.ParcelableResource parcelableResource) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(str2);
        java.util.Objects.requireNonNull(str3);
        java.util.Objects.requireNonNull(parcelableResource);
        this.mDrawableId = str;
        this.mDrawableStyle = str2;
        this.mDrawableSource = str3;
        this.mResourceIdInCallingPackage = i;
        this.mResource = parcelableResource;
    }

    public DevicePolicyDrawableResource(android.content.Context context, java.lang.String str, java.lang.String str2, int i) {
        this(context, str, str2, android.app.admin.DevicePolicyResources.UNDEFINED, i);
    }

    public java.lang.String getDrawableId() {
        return this.mDrawableId;
    }

    public java.lang.String getDrawableStyle() {
        return this.mDrawableStyle;
    }

    public java.lang.String getDrawableSource() {
        return this.mDrawableSource;
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
        android.app.admin.DevicePolicyDrawableResource devicePolicyDrawableResource = (android.app.admin.DevicePolicyDrawableResource) obj;
        if (this.mDrawableId.equals(devicePolicyDrawableResource.mDrawableId) && this.mDrawableStyle.equals(devicePolicyDrawableResource.mDrawableStyle) && this.mDrawableSource.equals(devicePolicyDrawableResource.mDrawableSource) && this.mResourceIdInCallingPackage == devicePolicyDrawableResource.mResourceIdInCallingPackage && this.mResource.equals(devicePolicyDrawableResource.mResource)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mDrawableId, this.mDrawableStyle, this.mDrawableSource, java.lang.Integer.valueOf(this.mResourceIdInCallingPackage), this.mResource);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mDrawableId);
        parcel.writeString(this.mDrawableStyle);
        parcel.writeString(this.mDrawableSource);
        parcel.writeInt(this.mResourceIdInCallingPackage);
        parcel.writeTypedObject(this.mResource, i);
    }
}
