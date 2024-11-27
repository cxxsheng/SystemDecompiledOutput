package android.service.credentials;

/* loaded from: classes3.dex */
public final class GetCredentialRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.credentials.GetCredentialRequest> CREATOR = new android.os.Parcelable.Creator<android.service.credentials.GetCredentialRequest>() { // from class: android.service.credentials.GetCredentialRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.credentials.GetCredentialRequest createFromParcel(android.os.Parcel parcel) {
            return new android.service.credentials.GetCredentialRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.credentials.GetCredentialRequest[] newArray(int i) {
            return new android.service.credentials.GetCredentialRequest[i];
        }
    };
    private final android.service.credentials.CallingAppInfo mCallingAppInfo;
    private final java.util.List<android.credentials.CredentialOption> mCredentialOptions;

    public GetCredentialRequest(android.service.credentials.CallingAppInfo callingAppInfo, java.util.List<android.credentials.CredentialOption> list) {
        this.mCallingAppInfo = (android.service.credentials.CallingAppInfo) java.util.Objects.requireNonNull(callingAppInfo, "callingAppInfo must not be null");
        this.mCredentialOptions = (java.util.List) java.util.Objects.requireNonNull(list, "credentialOptions must not be null");
    }

    private GetCredentialRequest(android.os.Parcel parcel) {
        this.mCallingAppInfo = (android.service.credentials.CallingAppInfo) parcel.readTypedObject(android.service.credentials.CallingAppInfo.CREATOR);
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mCallingAppInfo);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readTypedList(arrayList, android.credentials.CredentialOption.CREATOR);
        this.mCredentialOptions = arrayList;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mCredentialOptions);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.mCallingAppInfo, i);
        parcel.writeTypedList(this.mCredentialOptions, i);
    }

    public android.service.credentials.CallingAppInfo getCallingAppInfo() {
        return this.mCallingAppInfo;
    }

    public java.util.List<android.credentials.CredentialOption> getCredentialOptions() {
        return this.mCredentialOptions;
    }
}
