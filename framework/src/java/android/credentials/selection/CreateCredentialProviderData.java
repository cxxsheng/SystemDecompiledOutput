package android.credentials.selection;

/* loaded from: classes.dex */
public final class CreateCredentialProviderData extends android.credentials.selection.ProviderData implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.credentials.selection.CreateCredentialProviderData> CREATOR = new android.os.Parcelable.Creator<android.credentials.selection.CreateCredentialProviderData>() { // from class: android.credentials.selection.CreateCredentialProviderData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.selection.CreateCredentialProviderData createFromParcel(android.os.Parcel parcel) {
            return new android.credentials.selection.CreateCredentialProviderData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.selection.CreateCredentialProviderData[] newArray(int i) {
            return new android.credentials.selection.CreateCredentialProviderData[i];
        }
    };
    private final android.credentials.selection.Entry mRemoteEntry;
    private final java.util.List<android.credentials.selection.Entry> mSaveEntries;

    public CreateCredentialProviderData(java.lang.String str, java.util.List<android.credentials.selection.Entry> list, android.credentials.selection.Entry entry) {
        super(str);
        this.mSaveEntries = new java.util.ArrayList(list);
        this.mRemoteEntry = entry;
    }

    public android.credentials.selection.CreateCredentialProviderInfo toCreateCredentialProviderInfo() {
        return new android.credentials.selection.CreateCredentialProviderInfo(getProviderFlattenedComponentName(), this.mSaveEntries, this.mRemoteEntry);
    }

    public java.util.List<android.credentials.selection.Entry> getSaveEntries() {
        return this.mSaveEntries;
    }

    public android.credentials.selection.Entry getRemoteEntry() {
        return this.mRemoteEntry;
    }

    private CreateCredentialProviderData(android.os.Parcel parcel) {
        super(parcel);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readTypedList(arrayList, android.credentials.selection.Entry.CREATOR);
        this.mSaveEntries = arrayList;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mSaveEntries);
        this.mRemoteEntry = (android.credentials.selection.Entry) parcel.readTypedObject(android.credentials.selection.Entry.CREATOR);
    }

    @Override // android.credentials.selection.ProviderData, android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedList(this.mSaveEntries);
        parcel.writeTypedObject(this.mRemoteEntry, i);
    }

    @Override // android.credentials.selection.ProviderData, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private java.lang.String mProviderFlattenedComponentName;
        private java.util.List<android.credentials.selection.Entry> mSaveEntries = new java.util.ArrayList();
        private android.credentials.selection.Entry mRemoteEntry = null;

        public Builder(java.lang.String str) {
            this.mProviderFlattenedComponentName = str;
        }

        public android.credentials.selection.CreateCredentialProviderData.Builder setSaveEntries(java.util.List<android.credentials.selection.Entry> list) {
            this.mSaveEntries = list;
            return this;
        }

        public android.credentials.selection.CreateCredentialProviderData.Builder setRemoteEntry(android.credentials.selection.Entry entry) {
            this.mRemoteEntry = entry;
            return this;
        }

        public android.credentials.selection.CreateCredentialProviderData build() {
            return new android.credentials.selection.CreateCredentialProviderData(this.mProviderFlattenedComponentName, this.mSaveEntries, this.mRemoteEntry);
        }
    }
}
