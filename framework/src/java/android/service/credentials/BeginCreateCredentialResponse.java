package android.service.credentials;

/* loaded from: classes3.dex */
public final class BeginCreateCredentialResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.credentials.BeginCreateCredentialResponse> CREATOR = new android.os.Parcelable.Creator<android.service.credentials.BeginCreateCredentialResponse>() { // from class: android.service.credentials.BeginCreateCredentialResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.credentials.BeginCreateCredentialResponse createFromParcel(android.os.Parcel parcel) {
            return new android.service.credentials.BeginCreateCredentialResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.credentials.BeginCreateCredentialResponse[] newArray(int i) {
            return new android.service.credentials.BeginCreateCredentialResponse[i];
        }
    };
    private final android.content.pm.ParceledListSlice<android.service.credentials.CreateEntry> mCreateEntries;
    private final android.service.credentials.RemoteEntry mRemoteCreateEntry;

    public BeginCreateCredentialResponse() {
        this((android.content.pm.ParceledListSlice<android.service.credentials.CreateEntry>) new android.content.pm.ParceledListSlice(new java.util.ArrayList()), (android.service.credentials.RemoteEntry) null);
    }

    private BeginCreateCredentialResponse(android.os.Parcel parcel) {
        this.mCreateEntries = (android.content.pm.ParceledListSlice) parcel.readParcelable(null, android.content.pm.ParceledListSlice.class);
        this.mRemoteCreateEntry = (android.service.credentials.RemoteEntry) parcel.readTypedObject(android.service.credentials.RemoteEntry.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mCreateEntries, i);
        parcel.writeTypedObject(this.mRemoteCreateEntry, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    BeginCreateCredentialResponse(android.content.pm.ParceledListSlice<android.service.credentials.CreateEntry> parceledListSlice, android.service.credentials.RemoteEntry remoteEntry) {
        this.mCreateEntries = parceledListSlice;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mCreateEntries);
        this.mRemoteCreateEntry = remoteEntry;
    }

    public java.util.List<android.service.credentials.CreateEntry> getCreateEntries() {
        return this.mCreateEntries.getList();
    }

    public android.service.credentials.RemoteEntry getRemoteCreateEntry() {
        return this.mRemoteCreateEntry;
    }

    public static final class Builder {
        private java.util.List<android.service.credentials.CreateEntry> mCreateEntries = new java.util.ArrayList();
        private android.service.credentials.RemoteEntry mRemoteCreateEntry;

        public android.service.credentials.BeginCreateCredentialResponse.Builder setCreateEntries(java.util.List<android.service.credentials.CreateEntry> list) {
            com.android.internal.util.Preconditions.checkCollectionNotEmpty(list, "createEntries");
            this.mCreateEntries = (java.util.List) com.android.internal.util.Preconditions.checkCollectionElementsNotNull(list, "createEntries");
            return this;
        }

        public android.service.credentials.BeginCreateCredentialResponse.Builder addCreateEntry(android.service.credentials.CreateEntry createEntry) {
            this.mCreateEntries.add((android.service.credentials.CreateEntry) java.util.Objects.requireNonNull(createEntry));
            return this;
        }

        public android.service.credentials.BeginCreateCredentialResponse.Builder setRemoteCreateEntry(android.service.credentials.RemoteEntry remoteEntry) {
            this.mRemoteCreateEntry = remoteEntry;
            return this;
        }

        public android.service.credentials.BeginCreateCredentialResponse build() {
            return new android.service.credentials.BeginCreateCredentialResponse((android.content.pm.ParceledListSlice<android.service.credentials.CreateEntry>) new android.content.pm.ParceledListSlice(this.mCreateEntries), this.mRemoteCreateEntry);
        }
    }
}
