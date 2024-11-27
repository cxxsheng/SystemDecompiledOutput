package android.credentials.selection;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class RequestInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.credentials.selection.RequestInfo> CREATOR = new android.os.Parcelable.Creator<android.credentials.selection.RequestInfo>() { // from class: android.credentials.selection.RequestInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.selection.RequestInfo createFromParcel(android.os.Parcel parcel) {
            return new android.credentials.selection.RequestInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.selection.RequestInfo[] newArray(int i) {
            return new android.credentials.selection.RequestInfo[i];
        }
    };
    public static final java.lang.String EXTRA_REQUEST_INFO = "android.credentials.selection.extra.REQUEST_INFO";
    public static final java.lang.String TYPE_CREATE = "android.credentials.selection.TYPE_CREATE";
    public static final java.lang.String TYPE_GET = "android.credentials.selection.TYPE_GET";
    public static final java.lang.String TYPE_GET_VIA_REGISTRY = "android.credentials.selection.TYPE_GET_VIA_REGISTRY";
    public static final java.lang.String TYPE_UNDEFINED = "android.credentials.selection.TYPE_UNDEFINED";
    private final android.credentials.CreateCredentialRequest mCreateCredentialRequest;
    private final java.util.List<java.lang.String> mDefaultProviderIds;
    private final android.credentials.GetCredentialRequest mGetCredentialRequest;
    private final boolean mHasPermissionToOverrideDefault;
    private final boolean mIsShowAllOptionsRequested;
    private final java.lang.String mPackageName;
    private final java.util.List<java.lang.String> mRegistryProviderIds;
    private final android.os.IBinder mToken;
    private final java.lang.String mType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface RequestType {
    }

    public static android.credentials.selection.RequestInfo newCreateRequestInfo(android.os.IBinder iBinder, android.credentials.CreateCredentialRequest createCredentialRequest, java.lang.String str, boolean z, java.util.List<java.lang.String> list, boolean z2) {
        return new android.credentials.selection.RequestInfo(iBinder, TYPE_CREATE, str, createCredentialRequest, null, z, list, z2);
    }

    public static android.credentials.selection.RequestInfo newGetRequestInfo(android.os.IBinder iBinder, android.credentials.GetCredentialRequest getCredentialRequest, java.lang.String str, boolean z, boolean z2) {
        return new android.credentials.selection.RequestInfo(iBinder, TYPE_GET, str, null, getCredentialRequest, z, new java.util.ArrayList(), z2);
    }

    public boolean hasPermissionToOverrideDefault() {
        return this.mHasPermissionToOverrideDefault;
    }

    public android.os.IBinder getToken() {
        return this.mToken;
    }

    public java.lang.String getType() {
        return this.mType;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public android.credentials.CreateCredentialRequest getCreateCredentialRequest() {
        return this.mCreateCredentialRequest;
    }

    public android.credentials.selection.RequestToken getRequestToken() {
        return new android.credentials.selection.RequestToken(this.mToken);
    }

    public java.util.List<java.lang.String> getDefaultProviderIds() {
        return this.mDefaultProviderIds;
    }

    public java.util.List<java.lang.String> getRegistryProviderIds() {
        return this.mRegistryProviderIds;
    }

    public android.credentials.GetCredentialRequest getGetCredentialRequest() {
        return this.mGetCredentialRequest;
    }

    public boolean isShowAllOptionsRequested() {
        return this.mIsShowAllOptionsRequested;
    }

    private RequestInfo(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2, android.credentials.CreateCredentialRequest createCredentialRequest, android.credentials.GetCredentialRequest getCredentialRequest, boolean z, java.util.List<java.lang.String> list, boolean z2) {
        this.mToken = iBinder;
        this.mType = str;
        this.mPackageName = str2;
        this.mCreateCredentialRequest = createCredentialRequest;
        this.mGetCredentialRequest = getCredentialRequest;
        this.mHasPermissionToOverrideDefault = z;
        this.mDefaultProviderIds = list == null ? new java.util.ArrayList<>() : list;
        this.mRegistryProviderIds = new java.util.ArrayList();
        this.mIsShowAllOptionsRequested = z2;
    }

    private RequestInfo(android.os.Parcel parcel) {
        android.os.IBinder readStrongBinder = parcel.readStrongBinder();
        java.lang.String readString8 = parcel.readString8();
        java.lang.String readString82 = parcel.readString8();
        android.credentials.CreateCredentialRequest createCredentialRequest = (android.credentials.CreateCredentialRequest) parcel.readTypedObject(android.credentials.CreateCredentialRequest.CREATOR);
        android.credentials.GetCredentialRequest getCredentialRequest = (android.credentials.GetCredentialRequest) parcel.readTypedObject(android.credentials.GetCredentialRequest.CREATOR);
        this.mToken = readStrongBinder;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mToken);
        this.mType = readString8;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mType);
        this.mPackageName = readString82;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mPackageName);
        this.mCreateCredentialRequest = createCredentialRequest;
        this.mGetCredentialRequest = getCredentialRequest;
        this.mHasPermissionToOverrideDefault = parcel.readBoolean();
        this.mDefaultProviderIds = parcel.createStringArrayList();
        this.mRegistryProviderIds = parcel.createStringArrayList();
        this.mIsShowAllOptionsRequested = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mToken);
        parcel.writeString8(this.mType);
        parcel.writeString8(this.mPackageName);
        parcel.writeTypedObject(this.mCreateCredentialRequest, i);
        parcel.writeTypedObject(this.mGetCredentialRequest, i);
        parcel.writeBoolean(this.mHasPermissionToOverrideDefault);
        parcel.writeStringList(this.mDefaultProviderIds);
        parcel.writeStringList(this.mRegistryProviderIds);
        parcel.writeBoolean(this.mIsShowAllOptionsRequested);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
