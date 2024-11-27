package android.app.search;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class SearchContext implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.search.SearchContext> CREATOR = new android.os.Parcelable.Creator<android.app.search.SearchContext>() { // from class: android.app.search.SearchContext.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.search.SearchContext createFromParcel(android.os.Parcel parcel) {
            return new android.app.search.SearchContext(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.search.SearchContext[] newArray(int i) {
            return new android.app.search.SearchContext[i];
        }
    };
    private final android.os.Bundle mExtras;
    private java.lang.String mPackageName;
    private final int mResultTypes;
    private final int mTimeoutMillis;

    public SearchContext(int i, int i2) {
        this(i, i2, new android.os.Bundle());
    }

    public SearchContext(int i, int i2, android.os.Bundle bundle) {
        this.mResultTypes = i;
        this.mTimeoutMillis = i2;
        this.mExtras = (android.os.Bundle) java.util.Objects.requireNonNull(bundle);
    }

    private SearchContext(android.os.Parcel parcel) {
        this.mResultTypes = parcel.readInt();
        this.mTimeoutMillis = parcel.readInt();
        this.mPackageName = parcel.readString();
        this.mExtras = parcel.readBundle();
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    void setPackageName(java.lang.String str) {
        this.mPackageName = str;
    }

    public int getTimeoutMillis() {
        return this.mTimeoutMillis;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public int getResultTypes() {
        return this.mResultTypes;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mResultTypes);
        parcel.writeInt(this.mTimeoutMillis);
        parcel.writeString(this.mPackageName);
        parcel.writeBundle(this.mExtras);
    }
}
