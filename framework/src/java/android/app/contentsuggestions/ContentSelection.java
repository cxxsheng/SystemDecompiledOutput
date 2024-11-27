package android.app.contentsuggestions;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class ContentSelection implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.contentsuggestions.ContentSelection> CREATOR = new android.os.Parcelable.Creator<android.app.contentsuggestions.ContentSelection>() { // from class: android.app.contentsuggestions.ContentSelection.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.contentsuggestions.ContentSelection createFromParcel(android.os.Parcel parcel) {
            return new android.app.contentsuggestions.ContentSelection(parcel.readString(), parcel.readBundle());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.contentsuggestions.ContentSelection[] newArray(int i) {
            return new android.app.contentsuggestions.ContentSelection[i];
        }
    };
    private final android.os.Bundle mExtras;
    private final java.lang.String mSelectionId;

    public ContentSelection(java.lang.String str, android.os.Bundle bundle) {
        this.mSelectionId = str;
        this.mExtras = bundle;
    }

    public java.lang.String getId() {
        return this.mSelectionId;
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
        parcel.writeString(this.mSelectionId);
        parcel.writeBundle(this.mExtras);
    }
}
