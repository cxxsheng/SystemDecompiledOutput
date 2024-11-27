package android.app.prediction;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class AppTargetId implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.prediction.AppTargetId> CREATOR = new android.os.Parcelable.Creator<android.app.prediction.AppTargetId>() { // from class: android.app.prediction.AppTargetId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.prediction.AppTargetId createFromParcel(android.os.Parcel parcel) {
            return new android.app.prediction.AppTargetId(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.prediction.AppTargetId[] newArray(int i) {
            return new android.app.prediction.AppTargetId[i];
        }
    };
    private final java.lang.String mId;

    @android.annotation.SystemApi
    public AppTargetId(java.lang.String str) {
        this.mId = str;
    }

    private AppTargetId(android.os.Parcel parcel) {
        this.mId = parcel.readString();
    }

    public java.lang.String getId() {
        return this.mId;
    }

    public boolean equals(java.lang.Object obj) {
        if (getClass().equals(obj != null ? obj.getClass() : null)) {
            return this.mId.equals(((android.app.prediction.AppTargetId) obj).mId);
        }
        return false;
    }

    public int hashCode() {
        return this.mId.hashCode();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mId);
    }
}
