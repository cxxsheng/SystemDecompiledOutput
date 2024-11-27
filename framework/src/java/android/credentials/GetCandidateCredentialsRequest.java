package android.credentials;

/* loaded from: classes.dex */
public final class GetCandidateCredentialsRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.credentials.GetCandidateCredentialsRequest> CREATOR = new android.os.Parcelable.Creator<android.credentials.GetCandidateCredentialsRequest>() { // from class: android.credentials.GetCandidateCredentialsRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.GetCandidateCredentialsRequest[] newArray(int i) {
            return new android.credentials.GetCandidateCredentialsRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.GetCandidateCredentialsRequest createFromParcel(android.os.Parcel parcel) {
            return new android.credentials.GetCandidateCredentialsRequest(parcel);
        }
    };
    private final java.util.List<android.credentials.CredentialOption> mCredentialOptions;
    private final android.os.Bundle mData;
    private java.lang.String mOrigin;

    public java.util.List<android.credentials.CredentialOption> getCredentialOptions() {
        return this.mCredentialOptions;
    }

    public android.os.Bundle getData() {
        return this.mData;
    }

    public java.lang.String getOrigin() {
        return this.mOrigin;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedList(this.mCredentialOptions, i);
        parcel.writeBundle(this.mData);
        parcel.writeString8(this.mOrigin);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "GetCandidateCredentialsRequest {credentialOption=" + this.mCredentialOptions + ", data=" + this.mData + ", origin=" + this.mOrigin + "}";
    }

    private GetCandidateCredentialsRequest(java.util.List<android.credentials.CredentialOption> list, android.os.Bundle bundle, java.lang.String str) {
        com.android.internal.util.Preconditions.checkCollectionNotEmpty(list, "credentialOptions");
        com.android.internal.util.Preconditions.checkCollectionElementsNotNull(list, "credentialOptions");
        this.mCredentialOptions = list;
        this.mData = (android.os.Bundle) java.util.Objects.requireNonNull(bundle, "data must not be null");
        this.mOrigin = str;
    }

    private GetCandidateCredentialsRequest(android.os.Parcel parcel) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readTypedList(arrayList, android.credentials.CredentialOption.CREATOR);
        this.mCredentialOptions = arrayList;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mCredentialOptions);
        this.mData = parcel.readBundle();
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mData);
        this.mOrigin = parcel.readString8();
    }
}
