package android.app.admin;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class EnforcingAdmin implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.admin.EnforcingAdmin> CREATOR = new android.os.Parcelable.Creator<android.app.admin.EnforcingAdmin>() { // from class: android.app.admin.EnforcingAdmin.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.EnforcingAdmin createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.EnforcingAdmin(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.EnforcingAdmin[] newArray(int i) {
            return new android.app.admin.EnforcingAdmin[i];
        }
    };
    private final android.app.admin.Authority mAuthority;
    private final android.content.ComponentName mComponentName;
    private final java.lang.String mPackageName;
    private final android.os.UserHandle mUserHandle;

    public EnforcingAdmin(java.lang.String str, android.app.admin.Authority authority, android.os.UserHandle userHandle) {
        this.mPackageName = (java.lang.String) java.util.Objects.requireNonNull(str);
        this.mAuthority = (android.app.admin.Authority) java.util.Objects.requireNonNull(authority);
        this.mUserHandle = (android.os.UserHandle) java.util.Objects.requireNonNull(userHandle);
        this.mComponentName = null;
    }

    public EnforcingAdmin(java.lang.String str, android.app.admin.Authority authority, android.os.UserHandle userHandle, android.content.ComponentName componentName) {
        this.mPackageName = (java.lang.String) java.util.Objects.requireNonNull(str);
        this.mAuthority = (android.app.admin.Authority) java.util.Objects.requireNonNull(authority);
        this.mUserHandle = (android.os.UserHandle) java.util.Objects.requireNonNull(userHandle);
        this.mComponentName = componentName;
    }

    private EnforcingAdmin(android.os.Parcel parcel) {
        this.mPackageName = (java.lang.String) java.util.Objects.requireNonNull(parcel.readString());
        this.mUserHandle = new android.os.UserHandle(parcel.readInt());
        this.mAuthority = (android.app.admin.Authority) java.util.Objects.requireNonNull((android.app.admin.Authority) parcel.readParcelable(android.app.admin.Authority.class.getClassLoader()));
        this.mComponentName = (android.content.ComponentName) parcel.readParcelable(android.content.ComponentName.class.getClassLoader());
    }

    public android.app.admin.Authority getAuthority() {
        return this.mAuthority;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public android.os.UserHandle getUserHandle() {
        return this.mUserHandle;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.app.admin.EnforcingAdmin enforcingAdmin = (android.app.admin.EnforcingAdmin) obj;
        if (java.util.Objects.equals(this.mPackageName, enforcingAdmin.mPackageName) && java.util.Objects.equals(this.mAuthority, enforcingAdmin.mAuthority) && java.util.Objects.equals(this.mUserHandle, enforcingAdmin.mUserHandle) && java.util.Objects.equals(this.mComponentName, enforcingAdmin.mComponentName)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mPackageName, this.mAuthority, this.mUserHandle);
    }

    public java.lang.String toString() {
        return "EnforcingAdmin { mPackageName= " + this.mPackageName + ", mAuthority= " + this.mAuthority + ", mUserHandle= " + this.mUserHandle + ", mComponentName= " + this.mComponentName + " }";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mPackageName);
        parcel.writeInt(this.mUserHandle.getIdentifier());
        parcel.writeParcelable(this.mAuthority, i);
        parcel.writeParcelable(this.mComponentName, i);
    }
}
