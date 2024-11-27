package android.credentials;

/* loaded from: classes.dex */
public final class SetEnabledProvidersRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.credentials.SetEnabledProvidersRequest> CREATOR = new android.os.Parcelable.Creator<android.credentials.SetEnabledProvidersRequest>() { // from class: android.credentials.SetEnabledProvidersRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.SetEnabledProvidersRequest createFromParcel(android.os.Parcel parcel) {
            return new android.credentials.SetEnabledProvidersRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.SetEnabledProvidersRequest[] newArray(int i) {
            return new android.credentials.SetEnabledProvidersRequest[i];
        }
    };
    private final java.util.List<java.lang.String> mProviders;

    public SetEnabledProvidersRequest(java.util.List<java.lang.String> list) {
        java.util.Objects.requireNonNull(list, "providers must not be null");
        com.android.internal.util.Preconditions.checkCollectionElementsNotNull(list, "providers");
        this.mProviders = list;
    }

    private SetEnabledProvidersRequest(android.os.Parcel parcel) {
        this.mProviders = parcel.createStringArrayList();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStringList(this.mProviders);
    }

    public java.util.List<java.lang.String> getProviderComponentNames() {
        return this.mProviders;
    }
}
