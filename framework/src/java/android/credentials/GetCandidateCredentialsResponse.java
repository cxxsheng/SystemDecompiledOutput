package android.credentials;

/* loaded from: classes.dex */
public final class GetCandidateCredentialsResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.credentials.GetCandidateCredentialsResponse> CREATOR = new android.os.Parcelable.Creator<android.credentials.GetCandidateCredentialsResponse>() { // from class: android.credentials.GetCandidateCredentialsResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.GetCandidateCredentialsResponse createFromParcel(android.os.Parcel parcel) {
            return new android.credentials.GetCandidateCredentialsResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.GetCandidateCredentialsResponse[] newArray(int i) {
            return new android.credentials.GetCandidateCredentialsResponse[i];
        }
    };
    private final java.util.List<android.credentials.selection.GetCredentialProviderData> mCandidateProviderDataList;
    private final android.content.Intent mIntent;

    public GetCandidateCredentialsResponse(java.util.List<android.credentials.selection.GetCredentialProviderData> list, android.content.Intent intent) {
        com.android.internal.util.Preconditions.checkCollectionNotEmpty(list, "candidateProviderDataList");
        this.mCandidateProviderDataList = new java.util.ArrayList(list);
        this.mIntent = intent;
    }

    public java.util.List<android.credentials.selection.GetCredentialProviderData> getCandidateProviderDataList() {
        return this.mCandidateProviderDataList;
    }

    public android.content.Intent getIntent() {
        return this.mIntent;
    }

    protected GetCandidateCredentialsResponse(android.os.Parcel parcel) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readTypedList(arrayList, android.credentials.selection.GetCredentialProviderData.CREATOR);
        this.mCandidateProviderDataList = arrayList;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mCandidateProviderDataList);
        this.mIntent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedList(this.mCandidateProviderDataList);
        parcel.writeTypedObject(this.mIntent, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
