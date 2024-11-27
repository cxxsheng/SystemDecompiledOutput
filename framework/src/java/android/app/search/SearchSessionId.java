package android.app.search;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class SearchSessionId implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.search.SearchSessionId> CREATOR = new android.os.Parcelable.Creator<android.app.search.SearchSessionId>() { // from class: android.app.search.SearchSessionId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.search.SearchSessionId createFromParcel(android.os.Parcel parcel) {
            return new android.app.search.SearchSessionId(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.search.SearchSessionId[] newArray(int i) {
            return new android.app.search.SearchSessionId[i];
        }
    };
    private final java.lang.String mId;
    private final int mUserId;

    public SearchSessionId(java.lang.String str, int i) {
        this.mId = str;
        this.mUserId = i;
    }

    private SearchSessionId(android.os.Parcel parcel) {
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
        android.app.search.SearchSessionId searchSessionId = (android.app.search.SearchSessionId) obj;
        return this.mId.equals(searchSessionId.mId) && this.mUserId == searchSessionId.mUserId;
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
