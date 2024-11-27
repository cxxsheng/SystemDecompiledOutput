package android.permission;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class AdminPermissionControlParams implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.permission.AdminPermissionControlParams> CREATOR = new android.os.Parcelable.Creator<android.permission.AdminPermissionControlParams>() { // from class: android.permission.AdminPermissionControlParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.permission.AdminPermissionControlParams createFromParcel(android.os.Parcel parcel) {
            return new android.permission.AdminPermissionControlParams(parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readBoolean());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.permission.AdminPermissionControlParams[] newArray(int i) {
            return new android.permission.AdminPermissionControlParams[i];
        }
    };
    private final boolean mCanAdminGrantSensorsPermissions;
    private final int mGrantState;
    private final java.lang.String mGranteePackageName;
    private final java.lang.String mPermission;

    public AdminPermissionControlParams(java.lang.String str, java.lang.String str2, int i, boolean z) {
        com.android.internal.util.Preconditions.checkStringNotEmpty(str, "Package name must not be empty.");
        com.android.internal.util.Preconditions.checkStringNotEmpty(str2, "Permission must not be empty.");
        boolean z2 = true;
        if (i != 1 && i != 2 && i != 0) {
            z2 = false;
        }
        com.android.internal.util.Preconditions.checkArgument(z2);
        this.mGranteePackageName = str;
        this.mPermission = str2;
        this.mGrantState = i;
        this.mCanAdminGrantSensorsPermissions = z;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mGranteePackageName);
        parcel.writeString(this.mPermission);
        parcel.writeInt(this.mGrantState);
        parcel.writeBoolean(this.mCanAdminGrantSensorsPermissions);
    }

    public java.lang.String getGranteePackageName() {
        return this.mGranteePackageName;
    }

    public java.lang.String getPermission() {
        return this.mPermission;
    }

    public int getGrantState() {
        return this.mGrantState;
    }

    public boolean canAdminGrantSensorsPermissions() {
        return this.mCanAdminGrantSensorsPermissions;
    }

    public java.lang.String toString() {
        return java.lang.String.format("Grantee %s Permission %s state: %d admin grant of sensors permissions: %b", this.mGranteePackageName, this.mPermission, java.lang.Integer.valueOf(this.mGrantState), java.lang.Boolean.valueOf(this.mCanAdminGrantSensorsPermissions));
    }
}
