package android.app.admin;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class UnknownAuthority extends android.app.admin.Authority {
    public static final android.app.admin.UnknownAuthority UNKNOWN_AUTHORITY = new android.app.admin.UnknownAuthority();
    public static final android.os.Parcelable.Creator<android.app.admin.UnknownAuthority> CREATOR = new android.os.Parcelable.Creator<android.app.admin.UnknownAuthority>() { // from class: android.app.admin.UnknownAuthority.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.UnknownAuthority createFromParcel(android.os.Parcel parcel) {
            return android.app.admin.UnknownAuthority.UNKNOWN_AUTHORITY;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.UnknownAuthority[] newArray(int i) {
            return new android.app.admin.UnknownAuthority[i];
        }
    };

    public java.lang.String toString() {
        return "DefaultAuthority {}";
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
