package android.app.admin;

/* loaded from: classes.dex */
public final class MostRecent<V> extends android.app.admin.ResolutionMechanism<V> {
    public static final android.app.admin.MostRecent<?> MOST_RECENT = new android.app.admin.MostRecent<>();
    public static final android.os.Parcelable.Creator<android.app.admin.MostRecent<?>> CREATOR = new android.os.Parcelable.Creator<android.app.admin.MostRecent<?>>() { // from class: android.app.admin.MostRecent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.MostRecent<?> createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.MostRecent<>();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.MostRecent<?>[] newArray(int i) {
            return new android.app.admin.MostRecent[i];
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
        return "MostRecent {}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
    }
}
