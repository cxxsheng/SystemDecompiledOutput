package android.service.credentials;

/* loaded from: classes3.dex */
public final class BeginGetCredentialResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.credentials.BeginGetCredentialResponse> CREATOR = new android.os.Parcelable.Creator<android.service.credentials.BeginGetCredentialResponse>() { // from class: android.service.credentials.BeginGetCredentialResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.credentials.BeginGetCredentialResponse createFromParcel(android.os.Parcel parcel) {
            return new android.service.credentials.BeginGetCredentialResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.credentials.BeginGetCredentialResponse[] newArray(int i) {
            return new android.service.credentials.BeginGetCredentialResponse[i];
        }
    };
    private final android.content.pm.ParceledListSlice<android.service.credentials.Action> mActions;
    private final android.content.pm.ParceledListSlice<android.service.credentials.Action> mAuthenticationEntries;
    private final android.content.pm.ParceledListSlice<android.service.credentials.CredentialEntry> mCredentialEntries;
    private final android.service.credentials.RemoteEntry mRemoteCredentialEntry;

    public BeginGetCredentialResponse() {
        this(new android.content.pm.ParceledListSlice(new java.util.ArrayList()), new android.content.pm.ParceledListSlice(new java.util.ArrayList()), new android.content.pm.ParceledListSlice(new java.util.ArrayList()), null);
    }

    private BeginGetCredentialResponse(android.content.pm.ParceledListSlice<android.service.credentials.CredentialEntry> parceledListSlice, android.content.pm.ParceledListSlice<android.service.credentials.Action> parceledListSlice2, android.content.pm.ParceledListSlice<android.service.credentials.Action> parceledListSlice3, android.service.credentials.RemoteEntry remoteEntry) {
        this.mCredentialEntries = parceledListSlice;
        this.mAuthenticationEntries = parceledListSlice2;
        this.mActions = parceledListSlice3;
        this.mRemoteCredentialEntry = remoteEntry;
    }

    private BeginGetCredentialResponse(android.os.Parcel parcel) {
        this.mCredentialEntries = (android.content.pm.ParceledListSlice) parcel.readParcelable(null, android.content.pm.ParceledListSlice.class);
        this.mAuthenticationEntries = (android.content.pm.ParceledListSlice) parcel.readParcelable(null, android.content.pm.ParceledListSlice.class);
        this.mActions = (android.content.pm.ParceledListSlice) parcel.readParcelable(null, android.content.pm.ParceledListSlice.class);
        this.mRemoteCredentialEntry = (android.service.credentials.RemoteEntry) parcel.readTypedObject(android.service.credentials.RemoteEntry.CREATOR);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.mCredentialEntries, i);
        parcel.writeParcelable(this.mAuthenticationEntries, i);
        parcel.writeParcelable(this.mActions, i);
        parcel.writeTypedObject(this.mRemoteCredentialEntry, i);
    }

    public java.util.List<android.service.credentials.CredentialEntry> getCredentialEntries() {
        return this.mCredentialEntries.getList();
    }

    public java.util.List<android.service.credentials.Action> getAuthenticationActions() {
        return this.mAuthenticationEntries.getList();
    }

    public java.util.List<android.service.credentials.Action> getActions() {
        return this.mActions.getList();
    }

    public android.service.credentials.RemoteEntry getRemoteCredentialEntry() {
        return this.mRemoteCredentialEntry;
    }

    public static final class Builder {
        private android.service.credentials.RemoteEntry mRemoteCredentialEntry;
        private java.util.List<android.service.credentials.CredentialEntry> mCredentialEntries = new java.util.ArrayList();
        private java.util.List<android.service.credentials.Action> mAuthenticationEntries = new java.util.ArrayList();
        private java.util.List<android.service.credentials.Action> mActions = new java.util.ArrayList();

        public android.service.credentials.BeginGetCredentialResponse.Builder setRemoteCredentialEntry(android.service.credentials.RemoteEntry remoteEntry) {
            this.mRemoteCredentialEntry = remoteEntry;
            return this;
        }

        public android.service.credentials.BeginGetCredentialResponse.Builder addCredentialEntry(android.service.credentials.CredentialEntry credentialEntry) {
            this.mCredentialEntries.add((android.service.credentials.CredentialEntry) java.util.Objects.requireNonNull(credentialEntry));
            return this;
        }

        public android.service.credentials.BeginGetCredentialResponse.Builder addAuthenticationAction(android.service.credentials.Action action) {
            this.mAuthenticationEntries.add((android.service.credentials.Action) java.util.Objects.requireNonNull(action));
            return this;
        }

        public android.service.credentials.BeginGetCredentialResponse.Builder addAction(android.service.credentials.Action action) {
            this.mActions.add((android.service.credentials.Action) java.util.Objects.requireNonNull(action, "action must not be null"));
            return this;
        }

        public android.service.credentials.BeginGetCredentialResponse.Builder setActions(java.util.List<android.service.credentials.Action> list) {
            this.mActions = (java.util.List) com.android.internal.util.Preconditions.checkCollectionElementsNotNull(list, android.app.slice.Slice.HINT_ACTIONS);
            return this;
        }

        public android.service.credentials.BeginGetCredentialResponse.Builder setCredentialEntries(java.util.List<android.service.credentials.CredentialEntry> list) {
            this.mCredentialEntries = (java.util.List) com.android.internal.util.Preconditions.checkCollectionElementsNotNull(list, "credentialEntries");
            return this;
        }

        public android.service.credentials.BeginGetCredentialResponse.Builder setAuthenticationActions(java.util.List<android.service.credentials.Action> list) {
            this.mAuthenticationEntries = (java.util.List) com.android.internal.util.Preconditions.checkCollectionElementsNotNull(list, "authenticationActions");
            return this;
        }

        public android.service.credentials.BeginGetCredentialResponse build() {
            return new android.service.credentials.BeginGetCredentialResponse(new android.content.pm.ParceledListSlice(this.mCredentialEntries), new android.content.pm.ParceledListSlice(this.mAuthenticationEntries), new android.content.pm.ParceledListSlice(this.mActions), this.mRemoteCredentialEntry);
        }
    }
}
