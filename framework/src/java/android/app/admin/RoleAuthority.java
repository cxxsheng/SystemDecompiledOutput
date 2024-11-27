package android.app.admin;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class RoleAuthority extends android.app.admin.Authority {
    public static final android.os.Parcelable.Creator<android.app.admin.RoleAuthority> CREATOR = new android.os.Parcelable.Creator<android.app.admin.RoleAuthority>() { // from class: android.app.admin.RoleAuthority.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.RoleAuthority createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.RoleAuthority(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.RoleAuthority[] newArray(int i) {
            return new android.app.admin.RoleAuthority[i];
        }
    };
    private final java.util.Set<java.lang.String> mRoles;

    public RoleAuthority(java.util.Set<java.lang.String> set) {
        this.mRoles = new java.util.HashSet((java.util.Collection) java.util.Objects.requireNonNull(set));
    }

    private RoleAuthority(android.os.Parcel parcel) {
        this.mRoles = new java.util.HashSet();
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            this.mRoles.add(parcel.readString());
        }
    }

    public java.util.Set<java.lang.String> getRoles() {
        return this.mRoles;
    }

    @Override // android.app.admin.Authority, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mRoles.size());
        java.util.Iterator<java.lang.String> it = this.mRoles.iterator();
        while (it.hasNext()) {
            parcel.writeString(it.next());
        }
    }

    @Override // android.app.admin.Authority
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return java.util.Objects.equals(this.mRoles, ((android.app.admin.RoleAuthority) obj).mRoles);
    }

    @Override // android.app.admin.Authority
    public int hashCode() {
        return java.util.Objects.hash(this.mRoles);
    }

    public java.lang.String toString() {
        return "RoleAuthority { mRoles= " + this.mRoles + " }";
    }
}
