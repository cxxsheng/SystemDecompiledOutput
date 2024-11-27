package android.app.admin;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class DpcAuthority extends android.app.admin.Authority {
    public static final android.app.admin.DpcAuthority DPC_AUTHORITY = new android.app.admin.DpcAuthority();
    public static final android.os.Parcelable.Creator<android.app.admin.DpcAuthority> CREATOR = new android.os.Parcelable.Creator<android.app.admin.DpcAuthority>() { // from class: android.app.admin.DpcAuthority.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.DpcAuthority createFromParcel(android.os.Parcel parcel) {
            return android.app.admin.DpcAuthority.DPC_AUTHORITY;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.DpcAuthority[] newArray(int i) {
            return new android.app.admin.DpcAuthority[i];
        }
    };

    public java.lang.String toString() {
        return "DpcAuthority {}";
    }

    @Override // android.app.admin.Authority
    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass();
    }

    @Override // android.app.admin.Authority
    public int hashCode() {
        return 0;
    }

    @Override // android.app.admin.Authority, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
    }
}
