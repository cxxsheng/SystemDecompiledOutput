package android.credentials.selection;

/* loaded from: classes.dex */
public final class GetCredentialProviderData extends android.credentials.selection.ProviderData implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.credentials.selection.GetCredentialProviderData> CREATOR = new android.os.Parcelable.Creator<android.credentials.selection.GetCredentialProviderData>() { // from class: android.credentials.selection.GetCredentialProviderData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.selection.GetCredentialProviderData createFromParcel(android.os.Parcel parcel) {
            return new android.credentials.selection.GetCredentialProviderData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.selection.GetCredentialProviderData[] newArray(int i) {
            return new android.credentials.selection.GetCredentialProviderData[i];
        }
    };
    private final java.util.List<android.credentials.selection.Entry> mActionChips;
    private final java.util.List<android.credentials.selection.AuthenticationEntry> mAuthenticationEntries;
    private final java.util.List<android.credentials.selection.Entry> mCredentialEntries;
    private final android.credentials.selection.Entry mRemoteEntry;

    public GetCredentialProviderData(java.lang.String str, java.util.List<android.credentials.selection.Entry> list, java.util.List<android.credentials.selection.Entry> list2, java.util.List<android.credentials.selection.AuthenticationEntry> list3, android.credentials.selection.Entry entry) {
        super(str);
        this.mCredentialEntries = new java.util.ArrayList(list);
        this.mActionChips = new java.util.ArrayList(list2);
        this.mAuthenticationEntries = new java.util.ArrayList(list3);
        this.mRemoteEntry = entry;
    }

    public android.credentials.selection.GetCredentialProviderInfo toGetCredentialProviderInfo() {
        return new android.credentials.selection.GetCredentialProviderInfo(getProviderFlattenedComponentName(), this.mCredentialEntries, this.mActionChips, this.mAuthenticationEntries, this.mRemoteEntry);
    }

    public java.util.List<android.credentials.selection.Entry> getCredentialEntries() {
        return this.mCredentialEntries;
    }

    public java.util.List<android.credentials.selection.Entry> getActionChips() {
        return this.mActionChips;
    }

    public java.util.List<android.credentials.selection.AuthenticationEntry> getAuthenticationEntries() {
        return this.mAuthenticationEntries;
    }

    public android.credentials.selection.Entry getRemoteEntry() {
        return this.mRemoteEntry;
    }

    private GetCredentialProviderData(android.os.Parcel parcel) {
        super(parcel);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readTypedList(arrayList, android.credentials.selection.Entry.CREATOR);
        this.mCredentialEntries = arrayList;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mCredentialEntries);
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        parcel.readTypedList(arrayList2, android.credentials.selection.Entry.CREATOR);
        this.mActionChips = arrayList2;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mActionChips);
        java.util.ArrayList arrayList3 = new java.util.ArrayList();
        parcel.readTypedList(arrayList3, android.credentials.selection.AuthenticationEntry.CREATOR);
        this.mAuthenticationEntries = arrayList3;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mAuthenticationEntries);
        this.mRemoteEntry = (android.credentials.selection.Entry) parcel.readTypedObject(android.credentials.selection.Entry.CREATOR);
    }

    @Override // android.credentials.selection.ProviderData, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedList(this.mCredentialEntries);
        parcel.writeTypedList(this.mActionChips);
        parcel.writeTypedList(this.mAuthenticationEntries);
        parcel.writeTypedObject(this.mRemoteEntry, i);
    }

    @Override // android.credentials.selection.ProviderData, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private java.lang.String mProviderFlattenedComponentName;
        private java.util.List<android.credentials.selection.Entry> mCredentialEntries = new java.util.ArrayList();
        private java.util.List<android.credentials.selection.Entry> mActionChips = new java.util.ArrayList();
        private java.util.List<android.credentials.selection.AuthenticationEntry> mAuthenticationEntries = new java.util.ArrayList();
        private android.credentials.selection.Entry mRemoteEntry = null;

        public Builder(java.lang.String str) {
            this.mProviderFlattenedComponentName = str;
        }

        public android.credentials.selection.GetCredentialProviderData.Builder setCredentialEntries(java.util.List<android.credentials.selection.Entry> list) {
            this.mCredentialEntries = list;
            return this;
        }

        public android.credentials.selection.GetCredentialProviderData.Builder setActionChips(java.util.List<android.credentials.selection.Entry> list) {
            this.mActionChips = list;
            return this;
        }

        public android.credentials.selection.GetCredentialProviderData.Builder setAuthenticationEntries(java.util.List<android.credentials.selection.AuthenticationEntry> list) {
            this.mAuthenticationEntries = list;
            return this;
        }

        public android.credentials.selection.GetCredentialProviderData.Builder setRemoteEntry(android.credentials.selection.Entry entry) {
            this.mRemoteEntry = entry;
            return this;
        }

        public android.credentials.selection.GetCredentialProviderData build() {
            return new android.credentials.selection.GetCredentialProviderData(this.mProviderFlattenedComponentName, this.mCredentialEntries, this.mActionChips, this.mAuthenticationEntries, this.mRemoteEntry);
        }
    }
}
