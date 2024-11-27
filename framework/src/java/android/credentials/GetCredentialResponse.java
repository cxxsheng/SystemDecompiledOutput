package android.credentials;

/* loaded from: classes.dex */
public final class GetCredentialResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.credentials.GetCredentialResponse> CREATOR = new android.os.Parcelable.Creator<android.credentials.GetCredentialResponse>() { // from class: android.credentials.GetCredentialResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.GetCredentialResponse[] newArray(int i) {
            return new android.credentials.GetCredentialResponse[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.GetCredentialResponse createFromParcel(android.os.Parcel parcel) {
            return new android.credentials.GetCredentialResponse(parcel);
        }
    };
    private final android.credentials.Credential mCredential;

    public android.credentials.Credential getCredential() {
        return this.mCredential;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.mCredential, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "GetCredentialResponse {credential=" + this.mCredential + "}";
    }

    public android.view.autofill.AutofillId getAutofillId() {
        return (android.view.autofill.AutofillId) this.mCredential.getData().getParcelable(android.service.credentials.CredentialProviderService.EXTRA_AUTOFILL_ID, android.view.autofill.AutofillId.class);
    }

    public GetCredentialResponse(android.credentials.Credential credential) {
        this.mCredential = (android.credentials.Credential) java.util.Objects.requireNonNull(credential, "credential must not be null");
    }

    private GetCredentialResponse(android.os.Parcel parcel) {
        this.mCredential = (android.credentials.Credential) parcel.readTypedObject(android.credentials.Credential.CREATOR);
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mCredential);
    }
}
