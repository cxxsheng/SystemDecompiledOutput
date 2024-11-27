package android.service.credentials;

/* loaded from: classes3.dex */
public final class BeginGetCredentialRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.credentials.BeginGetCredentialRequest> CREATOR = new android.os.Parcelable.Creator<android.service.credentials.BeginGetCredentialRequest>() { // from class: android.service.credentials.BeginGetCredentialRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.credentials.BeginGetCredentialRequest createFromParcel(android.os.Parcel parcel) {
            return new android.service.credentials.BeginGetCredentialRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.credentials.BeginGetCredentialRequest[] newArray(int i) {
            return new android.service.credentials.BeginGetCredentialRequest[i];
        }
    };
    private final java.util.List<android.service.credentials.BeginGetCredentialOption> mBeginGetCredentialOptions;
    private final android.service.credentials.CallingAppInfo mCallingAppInfo;

    private BeginGetCredentialRequest(android.service.credentials.CallingAppInfo callingAppInfo, java.util.List<android.service.credentials.BeginGetCredentialOption> list) {
        this.mCallingAppInfo = callingAppInfo;
        this.mBeginGetCredentialOptions = list;
    }

    private BeginGetCredentialRequest(android.os.Parcel parcel) {
        this.mCallingAppInfo = (android.service.credentials.CallingAppInfo) parcel.readTypedObject(android.service.credentials.CallingAppInfo.CREATOR);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readTypedList(arrayList, android.service.credentials.BeginGetCredentialOption.CREATOR);
        this.mBeginGetCredentialOptions = arrayList;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mBeginGetCredentialOptions);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.mCallingAppInfo, i);
        parcel.writeTypedList(this.mBeginGetCredentialOptions);
    }

    public android.service.credentials.CallingAppInfo getCallingAppInfo() {
        return this.mCallingAppInfo;
    }

    public java.util.List<android.service.credentials.BeginGetCredentialOption> getBeginGetCredentialOptions() {
        return this.mBeginGetCredentialOptions;
    }

    public static final class Builder {
        private android.service.credentials.CallingAppInfo mCallingAppInfo = null;
        private java.util.List<android.service.credentials.BeginGetCredentialOption> mBeginGetCredentialOptions = new java.util.ArrayList();

        public android.service.credentials.BeginGetCredentialRequest.Builder setCallingAppInfo(android.service.credentials.CallingAppInfo callingAppInfo) {
            this.mCallingAppInfo = callingAppInfo;
            return this;
        }

        public android.service.credentials.BeginGetCredentialRequest.Builder setBeginGetCredentialOptions(java.util.List<android.service.credentials.BeginGetCredentialOption> list) {
            com.android.internal.util.Preconditions.checkCollectionNotEmpty(list, "getBeginCredentialOptions");
            com.android.internal.util.Preconditions.checkCollectionElementsNotNull(list, "getBeginCredentialOptions");
            this.mBeginGetCredentialOptions = list;
            return this;
        }

        public android.service.credentials.BeginGetCredentialRequest.Builder addBeginGetCredentialOption(android.service.credentials.BeginGetCredentialOption beginGetCredentialOption) {
            java.util.Objects.requireNonNull(beginGetCredentialOption, "beginGetCredentialOption must not be null");
            this.mBeginGetCredentialOptions.add(beginGetCredentialOption);
            return this;
        }

        public android.service.credentials.BeginGetCredentialRequest build() {
            com.android.internal.util.Preconditions.checkCollectionNotEmpty(this.mBeginGetCredentialOptions, "beginGetCredentialOptions");
            return new android.service.credentials.BeginGetCredentialRequest(this.mCallingAppInfo, this.mBeginGetCredentialOptions);
        }
    }
}
