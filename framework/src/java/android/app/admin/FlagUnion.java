package android.app.admin;

/* loaded from: classes.dex */
public final class FlagUnion extends android.app.admin.ResolutionMechanism<java.lang.Integer> {
    public static final android.app.admin.FlagUnion FLAG_UNION = new android.app.admin.FlagUnion();
    public static final android.os.Parcelable.Creator<android.app.admin.FlagUnion> CREATOR = new android.os.Parcelable.Creator<android.app.admin.FlagUnion>() { // from class: android.app.admin.FlagUnion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.FlagUnion createFromParcel(android.os.Parcel parcel) {
            return android.app.admin.FlagUnion.FLAG_UNION;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.FlagUnion[] newArray(int i) {
            return new android.app.admin.FlagUnion[i];
        }
    };

    private FlagUnion() {
    }

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
        return "FlagUnion {}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
    }
}
