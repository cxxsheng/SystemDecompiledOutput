package android.credentials;

/* loaded from: classes.dex */
public final class RegisterCredentialDescriptionRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.credentials.RegisterCredentialDescriptionRequest> CREATOR = new android.os.Parcelable.Creator<android.credentials.RegisterCredentialDescriptionRequest>() { // from class: android.credentials.RegisterCredentialDescriptionRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.RegisterCredentialDescriptionRequest createFromParcel(android.os.Parcel parcel) {
            return new android.credentials.RegisterCredentialDescriptionRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.RegisterCredentialDescriptionRequest[] newArray(int i) {
            return new android.credentials.RegisterCredentialDescriptionRequest[i];
        }
    };
    private final java.util.List<android.credentials.CredentialDescription> mCredentialDescriptions;

    public RegisterCredentialDescriptionRequest(android.credentials.CredentialDescription credentialDescription) {
        this.mCredentialDescriptions = java.util.Arrays.asList((android.credentials.CredentialDescription) java.util.Objects.requireNonNull(credentialDescription));
    }

    public RegisterCredentialDescriptionRequest(java.util.Set<android.credentials.CredentialDescription> set) {
        this.mCredentialDescriptions = new java.util.ArrayList((java.util.Collection) java.util.Objects.requireNonNull(set));
    }

    private RegisterCredentialDescriptionRequest(android.os.Parcel parcel) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readTypedList(arrayList, android.credentials.CredentialDescription.CREATOR);
        this.mCredentialDescriptions = new java.util.ArrayList();
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) arrayList);
        this.mCredentialDescriptions.addAll(arrayList);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedList(this.mCredentialDescriptions, i);
    }

    public java.util.Set<android.credentials.CredentialDescription> getCredentialDescriptions() {
        return new java.util.HashSet(this.mCredentialDescriptions);
    }
}
