package android.credentials;

/* loaded from: classes.dex */
public final class GetCredentialRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.credentials.GetCredentialRequest> CREATOR = new android.os.Parcelable.Creator<android.credentials.GetCredentialRequest>() { // from class: android.credentials.GetCredentialRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.GetCredentialRequest[] newArray(int i) {
            return new android.credentials.GetCredentialRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.GetCredentialRequest createFromParcel(android.os.Parcel parcel) {
            return new android.credentials.GetCredentialRequest(parcel);
        }
    };
    private final boolean mAlwaysSendAppInfoToProvider;
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

    public boolean alwaysSendAppInfoToProvider() {
        return this.mAlwaysSendAppInfoToProvider;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedList(this.mCredentialOptions, i);
        parcel.writeBundle(this.mData);
        parcel.writeBoolean(this.mAlwaysSendAppInfoToProvider);
        parcel.writeString8(this.mOrigin);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "GetCredentialRequest {credentialOption=" + this.mCredentialOptions + ", data=" + this.mData + ", alwaysSendAppInfoToProvider=" + this.mAlwaysSendAppInfoToProvider + ", origin=" + this.mOrigin + "}";
    }

    private GetCredentialRequest(java.util.List<android.credentials.CredentialOption> list, android.os.Bundle bundle, boolean z, java.lang.String str) {
        com.android.internal.util.Preconditions.checkCollectionNotEmpty(list, "credentialOptions");
        com.android.internal.util.Preconditions.checkCollectionElementsNotNull(list, "credentialOptions");
        this.mCredentialOptions = list;
        this.mData = (android.os.Bundle) java.util.Objects.requireNonNull(bundle, "data must not be null");
        this.mAlwaysSendAppInfoToProvider = z;
        this.mOrigin = str;
    }

    private GetCredentialRequest(android.os.Parcel parcel) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readTypedList(arrayList, android.credentials.CredentialOption.CREATOR);
        this.mCredentialOptions = arrayList;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mCredentialOptions);
        this.mData = parcel.readBundle();
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mData);
        this.mAlwaysSendAppInfoToProvider = parcel.readBoolean();
        this.mOrigin = parcel.readString8();
    }

    public static final class Builder {
        private final android.os.Bundle mData;
        private java.lang.String mOrigin;
        private java.util.List<android.credentials.CredentialOption> mCredentialOptions = new java.util.ArrayList();
        private boolean mAlwaysSendAppInfoToProvider = true;

        public Builder(android.os.Bundle bundle) {
            this.mData = (android.os.Bundle) java.util.Objects.requireNonNull(bundle, "data must not be null");
        }

        public android.credentials.GetCredentialRequest.Builder addCredentialOption(android.credentials.CredentialOption credentialOption) {
            this.mCredentialOptions.add((android.credentials.CredentialOption) java.util.Objects.requireNonNull(credentialOption, "credentialOption must not be null"));
            return this;
        }

        public android.credentials.GetCredentialRequest.Builder setAlwaysSendAppInfoToProvider(boolean z) {
            this.mAlwaysSendAppInfoToProvider = z;
            return this;
        }

        public android.credentials.GetCredentialRequest.Builder setCredentialOptions(java.util.List<android.credentials.CredentialOption> list) {
            com.android.internal.util.Preconditions.checkCollectionElementsNotNull(list, "credentialOptions");
            this.mCredentialOptions = new java.util.ArrayList(list);
            return this;
        }

        public android.credentials.GetCredentialRequest.Builder setOrigin(java.lang.String str) {
            this.mOrigin = str;
            return this;
        }

        public android.credentials.GetCredentialRequest build() {
            com.android.internal.util.Preconditions.checkCollectionNotEmpty(this.mCredentialOptions, "credentialOptions");
            com.android.internal.util.Preconditions.checkCollectionElementsNotNull(this.mCredentialOptions, "credentialOptions");
            return new android.credentials.GetCredentialRequest(this.mCredentialOptions, this.mData, this.mAlwaysSendAppInfoToProvider, this.mOrigin);
        }
    }
}
