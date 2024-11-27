package android.app.admin;

/* loaded from: classes.dex */
public final class StringSetUnion extends android.app.admin.ResolutionMechanism<java.util.Set<java.lang.String>> {
    public static final android.app.admin.StringSetUnion STRING_SET_UNION = new android.app.admin.StringSetUnion();
    public static final android.os.Parcelable.Creator<android.app.admin.StringSetUnion> CREATOR = new android.os.Parcelable.Creator<android.app.admin.StringSetUnion>() { // from class: android.app.admin.StringSetUnion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.StringSetUnion createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.StringSetUnion();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.StringSetUnion[] newArray(int i) {
            return new android.app.admin.StringSetUnion[i];
        }
    };

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass();
    }

    public int hashCode() {
        return 0;
    }

    public java.lang.String toString() {
        return "StringSetUnion {}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
    }
}
