package android.service.credentials;

/* loaded from: classes3.dex */
public final class BeginGetCredentialOption implements android.os.Parcelable {
    private static final java.lang.String BUNDLE_ID_KEY = "android.service.credentials.BeginGetCredentialOption.BUNDLE_ID_KEY";
    public static final android.os.Parcelable.Creator<android.service.credentials.BeginGetCredentialOption> CREATOR = new android.os.Parcelable.Creator<android.service.credentials.BeginGetCredentialOption>() { // from class: android.service.credentials.BeginGetCredentialOption.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.credentials.BeginGetCredentialOption[] newArray(int i) {
            return new android.service.credentials.BeginGetCredentialOption[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.credentials.BeginGetCredentialOption createFromParcel(android.os.Parcel parcel) {
            return new android.service.credentials.BeginGetCredentialOption(parcel);
        }
    };
    private final android.os.Bundle mCandidateQueryData;
    private final java.lang.String mId;
    private final java.lang.String mType;

    public java.lang.String getId() {
        return this.mId;
    }

    public java.lang.String getType() {
        return this.mType;
    }

    public android.os.Bundle getCandidateQueryData() {
        return this.mCandidateQueryData;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString8(this.mType);
        parcel.writeBundle(this.mCandidateQueryData);
        parcel.writeString8(this.mId);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "GetCredentialOption {type=" + this.mType + ", candidateQueryData=" + this.mCandidateQueryData + ", id=" + this.mId + "}";
    }

    public BeginGetCredentialOption(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) {
        this.mId = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str, "id must not be empty");
        this.mType = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str2, "type must not be empty");
        android.os.Bundle bundle2 = new android.os.Bundle();
        bundle2.putAll(bundle);
        this.mCandidateQueryData = bundle2;
        addIdToBundle();
    }

    private void addIdToBundle() {
        this.mCandidateQueryData.putString(BUNDLE_ID_KEY, this.mId);
    }

    private BeginGetCredentialOption(android.os.Parcel parcel) {
        java.lang.String readString8 = parcel.readString8();
        android.os.Bundle readBundle = parcel.readBundle();
        java.lang.String readString82 = parcel.readString8();
        this.mType = readString8;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mType);
        this.mCandidateQueryData = readBundle;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mCandidateQueryData);
        this.mId = readString82;
    }
}
