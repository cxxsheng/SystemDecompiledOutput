package android.service.autofill;

/* loaded from: classes3.dex */
public final class ConvertCredentialRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.autofill.ConvertCredentialRequest> CREATOR = new android.os.Parcelable.Creator<android.service.autofill.ConvertCredentialRequest>() { // from class: android.service.autofill.ConvertCredentialRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.ConvertCredentialRequest[] newArray(int i) {
            return new android.service.autofill.ConvertCredentialRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.autofill.ConvertCredentialRequest createFromParcel(android.os.Parcel parcel) {
            return new android.service.autofill.ConvertCredentialRequest(parcel);
        }
    };
    private final android.os.Bundle mClientState;
    private final android.credentials.GetCredentialResponse mGetCredentialResponse;

    public ConvertCredentialRequest(android.credentials.GetCredentialResponse getCredentialResponse, android.os.Bundle bundle) {
        this.mGetCredentialResponse = getCredentialResponse;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mGetCredentialResponse);
        this.mClientState = bundle;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mClientState);
    }

    public android.credentials.GetCredentialResponse getGetCredentialResponse() {
        return this.mGetCredentialResponse;
    }

    public android.os.Bundle getClientState() {
        return this.mClientState;
    }

    public java.lang.String toString() {
        return "ConvertCredentialRequest { getCredentialResponse = " + this.mGetCredentialResponse + ", clientState = " + this.mClientState + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.mGetCredentialResponse, i);
        parcel.writeBundle(this.mClientState);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    ConvertCredentialRequest(android.os.Parcel parcel) {
        android.credentials.GetCredentialResponse getCredentialResponse = (android.credentials.GetCredentialResponse) parcel.readTypedObject(android.credentials.GetCredentialResponse.CREATOR);
        android.os.Bundle readBundle = parcel.readBundle();
        this.mGetCredentialResponse = getCredentialResponse;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mGetCredentialResponse);
        this.mClientState = readBundle;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mClientState);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
