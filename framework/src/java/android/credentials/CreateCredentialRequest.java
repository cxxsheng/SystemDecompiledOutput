package android.credentials;

/* loaded from: classes.dex */
public final class CreateCredentialRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.credentials.CreateCredentialRequest> CREATOR = new android.os.Parcelable.Creator<android.credentials.CreateCredentialRequest>() { // from class: android.credentials.CreateCredentialRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.CreateCredentialRequest[] newArray(int i) {
            return new android.credentials.CreateCredentialRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.CreateCredentialRequest createFromParcel(android.os.Parcel parcel) {
            return new android.credentials.CreateCredentialRequest(parcel);
        }
    };
    private final boolean mAlwaysSendAppInfoToProvider;
    private final android.os.Bundle mCandidateQueryData;
    private final android.os.Bundle mCredentialData;
    private final boolean mIsSystemProviderRequired;
    private final java.lang.String mOrigin;
    private final java.lang.String mType;

    public java.lang.String getType() {
        return this.mType;
    }

    public android.os.Bundle getCredentialData() {
        return this.mCredentialData;
    }

    public android.os.Bundle getCandidateQueryData() {
        return this.mCandidateQueryData;
    }

    public boolean isSystemProviderRequired() {
        return this.mIsSystemProviderRequired;
    }

    public boolean alwaysSendAppInfoToProvider() {
        return this.mAlwaysSendAppInfoToProvider;
    }

    public java.lang.String getOrigin() {
        return this.mOrigin;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString8(this.mType);
        parcel.writeBundle(this.mCredentialData);
        parcel.writeBundle(this.mCandidateQueryData);
        parcel.writeBoolean(this.mIsSystemProviderRequired);
        parcel.writeBoolean(this.mAlwaysSendAppInfoToProvider);
        parcel.writeString8(this.mOrigin);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "CreateCredentialRequest {type=" + this.mType + ", credentialData=" + this.mCredentialData + ", candidateQueryData=" + this.mCandidateQueryData + ", isSystemProviderRequired=" + this.mIsSystemProviderRequired + ", alwaysSendAppInfoToProvider=" + this.mAlwaysSendAppInfoToProvider + ", origin=" + this.mOrigin + "}";
    }

    private CreateCredentialRequest(java.lang.String str, android.os.Bundle bundle, android.os.Bundle bundle2, boolean z, boolean z2, java.lang.String str2) {
        this.mType = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str, "type must not be empty");
        this.mCredentialData = (android.os.Bundle) java.util.Objects.requireNonNull(bundle, "credentialData must not be null");
        this.mCandidateQueryData = (android.os.Bundle) java.util.Objects.requireNonNull(bundle2, "candidateQueryData must not be null");
        this.mIsSystemProviderRequired = z;
        this.mAlwaysSendAppInfoToProvider = z2;
        this.mOrigin = str2;
    }

    private CreateCredentialRequest(android.os.Parcel parcel) {
        java.lang.String readString8 = parcel.readString8();
        android.os.Bundle readBundle = parcel.readBundle();
        android.os.Bundle readBundle2 = parcel.readBundle();
        boolean readBoolean = parcel.readBoolean();
        boolean readBoolean2 = parcel.readBoolean();
        this.mOrigin = parcel.readString8();
        this.mType = readString8;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mType);
        this.mCredentialData = readBundle;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mCredentialData);
        this.mCandidateQueryData = readBundle2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mCandidateQueryData);
        this.mIsSystemProviderRequired = readBoolean;
        this.mAlwaysSendAppInfoToProvider = readBoolean2;
    }

    public static final class Builder {
        private boolean mAlwaysSendAppInfoToProvider = true;
        private final android.os.Bundle mCandidateQueryData;
        private final android.os.Bundle mCredentialData;
        private boolean mIsSystemProviderRequired;
        private java.lang.String mOrigin;
        private java.lang.String mType;

        public Builder(java.lang.String str, android.os.Bundle bundle, android.os.Bundle bundle2) {
            this.mType = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str, "type must not be null or empty");
            this.mCredentialData = (android.os.Bundle) java.util.Objects.requireNonNull(bundle, "credentialData must not be null");
            this.mCandidateQueryData = (android.os.Bundle) java.util.Objects.requireNonNull(bundle2, "candidateQueryData must not be null");
        }

        public android.credentials.CreateCredentialRequest.Builder setAlwaysSendAppInfoToProvider(boolean z) {
            this.mAlwaysSendAppInfoToProvider = z;
            return this;
        }

        public android.credentials.CreateCredentialRequest.Builder setIsSystemProviderRequired(boolean z) {
            this.mIsSystemProviderRequired = z;
            return this;
        }

        public android.credentials.CreateCredentialRequest.Builder setOrigin(java.lang.String str) {
            this.mOrigin = str;
            return this;
        }

        public android.credentials.CreateCredentialRequest build() {
            com.android.internal.util.Preconditions.checkStringNotEmpty(this.mType, "type must not be empty");
            return new android.credentials.CreateCredentialRequest(this.mType, this.mCredentialData, this.mCandidateQueryData, this.mIsSystemProviderRequired, this.mAlwaysSendAppInfoToProvider, this.mOrigin);
        }
    }
}
