package android.app.contentsuggestions;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class ContentClassification implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.contentsuggestions.ContentClassification> CREATOR = new android.os.Parcelable.Creator<android.app.contentsuggestions.ContentClassification>() { // from class: android.app.contentsuggestions.ContentClassification.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.contentsuggestions.ContentClassification createFromParcel(android.os.Parcel parcel) {
            return new android.app.contentsuggestions.ContentClassification(parcel.readString(), parcel.readBundle());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.contentsuggestions.ContentClassification[] newArray(int i) {
            return new android.app.contentsuggestions.ContentClassification[i];
        }
    };
    private final java.lang.String mClassificationId;
    private final android.os.Bundle mExtras;

    public ContentClassification(java.lang.String str, android.os.Bundle bundle) {
        this.mClassificationId = str;
        this.mExtras = bundle;
    }

    public java.lang.String getId() {
        return this.mClassificationId;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mClassificationId);
        parcel.writeBundle(this.mExtras);
    }
}
