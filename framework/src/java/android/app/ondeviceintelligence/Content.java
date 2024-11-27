package android.app.ondeviceintelligence;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class Content implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.ondeviceintelligence.Content> CREATOR = new android.os.Parcelable.Creator<android.app.ondeviceintelligence.Content>() { // from class: android.app.ondeviceintelligence.Content.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.ondeviceintelligence.Content createFromParcel(android.os.Parcel parcel) {
            return new android.app.ondeviceintelligence.Content(parcel.readBundle(getClass().getClassLoader()));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.ondeviceintelligence.Content[] newArray(int i) {
            return new android.app.ondeviceintelligence.Content[i];
        }
    };
    private static final java.lang.String TAG = "Content";
    private final android.os.Bundle mData;

    public Content(android.os.Bundle bundle) {
        java.util.Objects.requireNonNull(bundle);
        validateBundleData(bundle);
        this.mData = bundle;
    }

    public android.os.Bundle getData() {
        return this.mData;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeBundle(this.mData);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return this.mData.describeContents() | 0;
    }

    private void validateBundleData(android.os.Bundle bundle) {
    }
}
