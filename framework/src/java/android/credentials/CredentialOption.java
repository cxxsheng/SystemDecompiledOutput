package android.credentials;

/* loaded from: classes.dex */
public final class CredentialOption implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.credentials.CredentialOption> CREATOR = new android.os.Parcelable.Creator<android.credentials.CredentialOption>() { // from class: android.credentials.CredentialOption.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.CredentialOption[] newArray(int i) {
            return new android.credentials.CredentialOption[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.CredentialOption createFromParcel(android.os.Parcel parcel) {
            return new android.credentials.CredentialOption(parcel);
        }
    };
    public static final java.lang.String SUPPORTED_ELEMENT_KEYS = "android.credentials.GetCredentialOption.SUPPORTED_ELEMENT_KEYS";
    private final android.util.ArraySet<android.content.ComponentName> mAllowedProviders;
    private final android.os.Bundle mCandidateQueryData;
    private final android.os.Bundle mCredentialRetrievalData;
    private final boolean mIsSystemProviderRequired;
    private final java.lang.String mType;

    public java.lang.String getType() {
        return this.mType;
    }

    public android.os.Bundle getCredentialRetrievalData() {
        return this.mCredentialRetrievalData;
    }

    public android.os.Bundle getCandidateQueryData() {
        return this.mCandidateQueryData;
    }

    public boolean isSystemProviderRequired() {
        return this.mIsSystemProviderRequired;
    }

    public java.util.Set<android.content.ComponentName> getAllowedProviders() {
        return this.mAllowedProviders;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString8(this.mType);
        parcel.writeBundle(this.mCredentialRetrievalData);
        parcel.writeBundle(this.mCandidateQueryData);
        parcel.writeBoolean(this.mIsSystemProviderRequired);
        parcel.writeArraySet(this.mAllowedProviders);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "CredentialOption {type=" + this.mType + ", requestData=" + this.mCredentialRetrievalData + ", candidateQueryData=" + this.mCandidateQueryData + ", isSystemProviderRequired=" + this.mIsSystemProviderRequired + ", allowedProviders=" + this.mAllowedProviders + "}";
    }

    private CredentialOption(java.lang.String str, android.os.Bundle bundle, android.os.Bundle bundle2, boolean z, android.util.ArraySet<android.content.ComponentName> arraySet) {
        this.mType = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str, "type must not be empty");
        this.mCredentialRetrievalData = (android.os.Bundle) java.util.Objects.requireNonNull(bundle, "requestData must not be null");
        this.mCandidateQueryData = (android.os.Bundle) java.util.Objects.requireNonNull(bundle2, "candidateQueryData must not be null");
        this.mIsSystemProviderRequired = z;
        this.mAllowedProviders = (android.util.ArraySet) java.util.Objects.requireNonNull(arraySet, "providerFilterSer mustnot be empty");
    }

    public CredentialOption(java.lang.String str, android.os.Bundle bundle, android.os.Bundle bundle2, boolean z) {
        this(str, bundle, bundle2, z, new android.util.ArraySet());
    }

    private CredentialOption(android.os.Parcel parcel) {
        java.lang.String readString8 = parcel.readString8();
        android.os.Bundle readBundle = parcel.readBundle();
        android.os.Bundle readBundle2 = parcel.readBundle();
        boolean readBoolean = parcel.readBoolean();
        this.mType = readString8;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mType);
        this.mCredentialRetrievalData = readBundle;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mCredentialRetrievalData);
        this.mCandidateQueryData = readBundle2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mCandidateQueryData);
        this.mIsSystemProviderRequired = readBoolean;
        this.mAllowedProviders = parcel.readArraySet(null);
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mAllowedProviders);
    }

    public static final class Builder {
        private android.os.Bundle mCandidateQueryData;
        private android.os.Bundle mCredentialRetrievalData;
        private java.lang.String mType;
        private boolean mIsSystemProviderRequired = false;
        private android.util.ArraySet<android.content.ComponentName> mAllowedProviders = new android.util.ArraySet<>();

        public Builder(java.lang.String str, android.os.Bundle bundle, android.os.Bundle bundle2) {
            this.mType = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str, "type must not be null, or empty");
            this.mCredentialRetrievalData = (android.os.Bundle) java.util.Objects.requireNonNull(bundle, "credentialRetrievalData must not be null");
            this.mCandidateQueryData = (android.os.Bundle) java.util.Objects.requireNonNull(bundle2, "candidateQueryData must not be null");
        }

        public android.credentials.CredentialOption.Builder setIsSystemProviderRequired(boolean z) {
            this.mIsSystemProviderRequired = z;
            return this;
        }

        public android.credentials.CredentialOption.Builder addAllowedProvider(android.content.ComponentName componentName) {
            this.mAllowedProviders.add((android.content.ComponentName) java.util.Objects.requireNonNull(componentName, "allowedProvider must not be null"));
            return this;
        }

        public android.credentials.CredentialOption.Builder setAllowedProviders(java.util.Set<android.content.ComponentName> set) {
            com.android.internal.util.Preconditions.checkCollectionElementsNotNull(set, "allowedProviders");
            this.mAllowedProviders = new android.util.ArraySet<>(set);
            return this;
        }

        public android.credentials.CredentialOption build() {
            return new android.credentials.CredentialOption(this.mType, this.mCredentialRetrievalData, this.mCandidateQueryData, this.mIsSystemProviderRequired, this.mAllowedProviders);
        }
    }
}
