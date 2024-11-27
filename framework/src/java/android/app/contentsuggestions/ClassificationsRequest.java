package android.app.contentsuggestions;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class ClassificationsRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.contentsuggestions.ClassificationsRequest> CREATOR = new android.os.Parcelable.Creator<android.app.contentsuggestions.ClassificationsRequest>() { // from class: android.app.contentsuggestions.ClassificationsRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.contentsuggestions.ClassificationsRequest createFromParcel(android.os.Parcel parcel) {
            return new android.app.contentsuggestions.ClassificationsRequest(parcel.createTypedArrayList(android.app.contentsuggestions.ContentSelection.CREATOR), parcel.readBundle());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.contentsuggestions.ClassificationsRequest[] newArray(int i) {
            return new android.app.contentsuggestions.ClassificationsRequest[i];
        }
    };
    private final android.os.Bundle mExtras;
    private final java.util.List<android.app.contentsuggestions.ContentSelection> mSelections;

    private ClassificationsRequest(java.util.List<android.app.contentsuggestions.ContentSelection> list, android.os.Bundle bundle) {
        this.mSelections = list;
        this.mExtras = bundle;
    }

    public java.util.List<android.app.contentsuggestions.ContentSelection> getSelections() {
        return this.mSelections;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras == null ? new android.os.Bundle() : this.mExtras;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedList(this.mSelections);
        parcel.writeBundle(this.mExtras);
    }

    @android.annotation.SystemApi
    public static final class Builder {
        private android.os.Bundle mExtras;
        private final java.util.List<android.app.contentsuggestions.ContentSelection> mSelections;

        public Builder(java.util.List<android.app.contentsuggestions.ContentSelection> list) {
            this.mSelections = list;
        }

        public android.app.contentsuggestions.ClassificationsRequest.Builder setExtras(android.os.Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public android.app.contentsuggestions.ClassificationsRequest build() {
            return new android.app.contentsuggestions.ClassificationsRequest(this.mSelections, this.mExtras);
        }
    }
}
