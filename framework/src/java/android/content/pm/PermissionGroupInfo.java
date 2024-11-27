package android.content.pm;

/* loaded from: classes.dex */
public class PermissionGroupInfo extends android.content.pm.PackageItemInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.content.pm.PermissionGroupInfo> CREATOR = new android.os.Parcelable.Creator<android.content.pm.PermissionGroupInfo>() { // from class: android.content.pm.PermissionGroupInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.PermissionGroupInfo createFromParcel(android.os.Parcel parcel) {
            return new android.content.pm.PermissionGroupInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.pm.PermissionGroupInfo[] newArray(int i) {
            return new android.content.pm.PermissionGroupInfo[i];
        }
    };
    public static final int FLAG_PERSONAL_INFO = 1;

    @android.annotation.SystemApi
    public final int backgroundRequestDetailResourceId;

    @android.annotation.SystemApi
    public final int backgroundRequestResourceId;
    public int descriptionRes;
    public int flags;
    public java.lang.CharSequence nonLocalizedDescription;
    public int priority;

    @android.annotation.SystemApi
    public final int requestDetailResourceId;

    @android.annotation.SystemApi
    public int requestRes;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    public PermissionGroupInfo(int i, int i2, int i3) {
        this.requestDetailResourceId = i;
        this.backgroundRequestResourceId = i2;
        this.backgroundRequestDetailResourceId = i3;
    }

    @java.lang.Deprecated
    public PermissionGroupInfo() {
        this(0, 0, 0);
    }

    @java.lang.Deprecated
    public PermissionGroupInfo(android.content.pm.PermissionGroupInfo permissionGroupInfo) {
        super(permissionGroupInfo);
        this.descriptionRes = permissionGroupInfo.descriptionRes;
        this.requestRes = permissionGroupInfo.requestRes;
        this.requestDetailResourceId = permissionGroupInfo.requestDetailResourceId;
        this.backgroundRequestResourceId = permissionGroupInfo.backgroundRequestResourceId;
        this.backgroundRequestDetailResourceId = permissionGroupInfo.backgroundRequestDetailResourceId;
        this.nonLocalizedDescription = permissionGroupInfo.nonLocalizedDescription;
        this.flags = permissionGroupInfo.flags;
        this.priority = permissionGroupInfo.priority;
    }

    public java.lang.CharSequence loadDescription(android.content.pm.PackageManager packageManager) {
        java.lang.CharSequence text;
        if (this.nonLocalizedDescription != null) {
            return this.nonLocalizedDescription;
        }
        if (this.descriptionRes == 0 || (text = packageManager.getText(this.packageName, this.descriptionRes, null)) == null) {
            return null;
        }
        return text;
    }

    public java.lang.String toString() {
        return "PermissionGroupInfo{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " " + this.name + " flgs=0x" + java.lang.Integer.toHexString(this.flags) + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.content.pm.PackageItemInfo, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.descriptionRes);
        parcel.writeInt(this.requestRes);
        parcel.writeInt(this.requestDetailResourceId);
        parcel.writeInt(this.backgroundRequestResourceId);
        parcel.writeInt(this.backgroundRequestDetailResourceId);
        android.text.TextUtils.writeToParcel(this.nonLocalizedDescription, parcel, i);
        parcel.writeInt(this.flags);
        parcel.writeInt(this.priority);
    }

    private PermissionGroupInfo(android.os.Parcel parcel) {
        super(parcel);
        this.descriptionRes = parcel.readInt();
        this.requestRes = parcel.readInt();
        this.requestDetailResourceId = parcel.readInt();
        this.backgroundRequestResourceId = parcel.readInt();
        this.backgroundRequestDetailResourceId = parcel.readInt();
        this.nonLocalizedDescription = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.flags = parcel.readInt();
        this.priority = parcel.readInt();
    }
}
