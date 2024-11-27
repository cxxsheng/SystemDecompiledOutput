package android.credentials;

/* loaded from: classes.dex */
public final class ListEnabledProvidersResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.credentials.ListEnabledProvidersResponse> CREATOR = new android.os.Parcelable.Creator<android.credentials.ListEnabledProvidersResponse>() { // from class: android.credentials.ListEnabledProvidersResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.ListEnabledProvidersResponse createFromParcel(android.os.Parcel parcel) {
            return new android.credentials.ListEnabledProvidersResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.ListEnabledProvidersResponse[] newArray(int i) {
            return new android.credentials.ListEnabledProvidersResponse[i];
        }
    };
    private final java.util.List<java.lang.String> mProviders;

    public static android.credentials.ListEnabledProvidersResponse create(java.util.List<java.lang.String> list) {
        java.util.Objects.requireNonNull(list, "providers must not be null");
        com.android.internal.util.Preconditions.checkCollectionElementsNotNull(list, "providers");
        return new android.credentials.ListEnabledProvidersResponse(list);
    }

    private ListEnabledProvidersResponse(java.util.List<java.lang.String> list) {
        this.mProviders = list;
    }

    private ListEnabledProvidersResponse(android.os.Parcel parcel) {
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
