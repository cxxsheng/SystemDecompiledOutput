package android.app.prediction;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class AppPredictionSessionId implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.prediction.AppPredictionSessionId> CREATOR = new android.os.Parcelable.Creator<android.app.prediction.AppPredictionSessionId>() { // from class: android.app.prediction.AppPredictionSessionId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.prediction.AppPredictionSessionId createFromParcel(android.os.Parcel parcel) {
            return new android.app.prediction.AppPredictionSessionId(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.prediction.AppPredictionSessionId[] newArray(int i) {
            return new android.app.prediction.AppPredictionSessionId[i];
        }
    };
    private final java.lang.String mId;
    private final int mUserId;

    public AppPredictionSessionId(java.lang.String str, int i) {
        this.mId = str;
        this.mUserId = i;
    }

    private AppPredictionSessionId(android.os.Parcel parcel) {
        this.mId = parcel.readString();
        this.mUserId = parcel.readInt();
    }

    public int getUserId() {
        return this.mUserId;
    }

    public boolean equals(java.lang.Object obj) {
        if (!getClass().equals(obj != null ? obj.getClass() : null)) {
            return false;
        }
        android.app.prediction.AppPredictionSessionId appPredictionSessionId = (android.app.prediction.AppPredictionSessionId) obj;
        return this.mId.equals(appPredictionSessionId.mId) && this.mUserId == appPredictionSessionId.mUserId;
    }

    public java.lang.String toString() {
        return this.mId + "," + this.mUserId;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mId, java.lang.Integer.valueOf(this.mUserId));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mId);
        parcel.writeInt(this.mUserId);
    }
}
