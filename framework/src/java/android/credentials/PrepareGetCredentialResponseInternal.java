package android.credentials;

/* loaded from: classes.dex */
public final class PrepareGetCredentialResponseInternal implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.credentials.PrepareGetCredentialResponseInternal> CREATOR = new android.os.Parcelable.Creator<android.credentials.PrepareGetCredentialResponseInternal>() { // from class: android.credentials.PrepareGetCredentialResponseInternal.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.PrepareGetCredentialResponseInternal[] newArray(int i) {
            return new android.credentials.PrepareGetCredentialResponseInternal[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.PrepareGetCredentialResponseInternal createFromParcel(android.os.Parcel parcel) {
            return new android.credentials.PrepareGetCredentialResponseInternal(parcel);
        }
    };
    private static final java.lang.String TAG = "CredentialManager";
    private final android.util.ArraySet<java.lang.String> mCredentialResultTypes;
    private final boolean mHasAuthenticationResults;
    private final boolean mHasQueryApiPermission;
    private final boolean mHasRemoteResults;
    private final android.app.PendingIntent mPendingIntent;

    public android.app.PendingIntent getPendingIntent() {
        return this.mPendingIntent;
    }

    public boolean hasCredentialResults(java.lang.String str) {
        if (!this.mHasQueryApiPermission) {
            throw new java.lang.SecurityException("caller doesn't have the permission to query credential results");
        }
        if (this.mCredentialResultTypes == null) {
            return false;
        }
        return this.mCredentialResultTypes.contains(str);
    }

    public boolean hasAuthenticationResults() {
        if (!this.mHasQueryApiPermission) {
            throw new java.lang.SecurityException("caller doesn't have the permission to query authentication results");
        }
        return this.mHasAuthenticationResults;
    }

    public boolean hasRemoteResults() {
        if (!this.mHasQueryApiPermission) {
            throw new java.lang.SecurityException("caller doesn't have the permission to query remote results");
        }
        return this.mHasRemoteResults;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeBoolean(this.mHasQueryApiPermission);
        parcel.writeArraySet(this.mCredentialResultTypes);
        parcel.writeBoolean(this.mHasAuthenticationResults);
        parcel.writeBoolean(this.mHasRemoteResults);
        parcel.writeTypedObject(this.mPendingIntent, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PrepareGetCredentialResponseInternal(boolean z, java.util.Set<java.lang.String> set, boolean z2, boolean z3, android.app.PendingIntent pendingIntent) {
        this.mHasQueryApiPermission = z;
        this.mCredentialResultTypes = new android.util.ArraySet<>(set);
        this.mHasAuthenticationResults = z2;
        this.mHasRemoteResults = z3;
        this.mPendingIntent = pendingIntent;
    }

    private PrepareGetCredentialResponseInternal(android.os.Parcel parcel) {
        this.mHasQueryApiPermission = parcel.readBoolean();
        this.mCredentialResultTypes = parcel.readArraySet(null);
        this.mHasAuthenticationResults = parcel.readBoolean();
        this.mHasRemoteResults = parcel.readBoolean();
        this.mPendingIntent = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
    }
}
