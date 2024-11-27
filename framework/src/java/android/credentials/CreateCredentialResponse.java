package android.credentials;

/* loaded from: classes.dex */
public final class CreateCredentialResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.credentials.CreateCredentialResponse> CREATOR = new android.os.Parcelable.Creator<android.credentials.CreateCredentialResponse>() { // from class: android.credentials.CreateCredentialResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.CreateCredentialResponse[] newArray(int i) {
            return new android.credentials.CreateCredentialResponse[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.CreateCredentialResponse createFromParcel(android.os.Parcel parcel) {
            return new android.credentials.CreateCredentialResponse(parcel);
        }
    };
    private final android.os.Bundle mData;

    public android.os.Bundle getData() {
        return this.mData;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeBundle(this.mData);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "CreateCredentialResponse {data=" + this.mData + "}";
    }

    public CreateCredentialResponse(android.os.Bundle bundle) {
        this.mData = (android.os.Bundle) java.util.Objects.requireNonNull(bundle, "data must not be null");
    }

    private CreateCredentialResponse(android.os.Parcel parcel) {
        this.mData = parcel.readBundle();
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mData);
    }
}
