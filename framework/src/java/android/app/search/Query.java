package android.app.search;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class Query implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.search.Query> CREATOR = new android.os.Parcelable.Creator<android.app.search.Query>() { // from class: android.app.search.Query.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.search.Query createFromParcel(android.os.Parcel parcel) {
            return new android.app.search.Query(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.search.Query[] newArray(int i) {
            return new android.app.search.Query[i];
        }
    };
    public static final java.lang.String EXTRA_IME_HEIGHT = "android.app.search.extra.IME_HEIGHT";
    private final android.os.Bundle mExtras;
    private final java.lang.String mInput;
    private final long mTimestampMillis;

    public Query(java.lang.String str, long j, android.os.Bundle bundle) {
        this.mInput = str;
        this.mTimestampMillis = j;
        this.mExtras = bundle == null ? new android.os.Bundle() : bundle;
    }

    public Query(java.lang.String str, long j) {
        this(str, j, new android.os.Bundle());
    }

    private Query(android.os.Parcel parcel) {
        this.mInput = parcel.readString();
        this.mTimestampMillis = parcel.readLong();
        this.mExtras = parcel.readBundle();
    }

    public java.lang.String getInput() {
        return this.mInput;
    }

    @java.lang.Deprecated
    public long getTimestamp() {
        return this.mTimestampMillis;
    }

    public long getTimestampMillis() {
        return this.mTimestampMillis;
    }

    public android.os.Bundle getExtras() {
        if (this.mExtras == null) {
            return new android.os.Bundle();
        }
        return this.mExtras;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mInput);
        parcel.writeLong(this.mTimestampMillis);
        parcel.writeBundle(this.mExtras);
    }
}
