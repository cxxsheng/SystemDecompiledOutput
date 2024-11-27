package android.service.credentials;

/* loaded from: classes3.dex */
public final class BeginCreateCredentialRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.credentials.BeginCreateCredentialRequest> CREATOR = new android.os.Parcelable.Creator<android.service.credentials.BeginCreateCredentialRequest>() { // from class: android.service.credentials.BeginCreateCredentialRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.credentials.BeginCreateCredentialRequest createFromParcel(android.os.Parcel parcel) {
            return new android.service.credentials.BeginCreateCredentialRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.credentials.BeginCreateCredentialRequest[] newArray(int i) {
            return new android.service.credentials.BeginCreateCredentialRequest[i];
        }
    };
    private final android.service.credentials.CallingAppInfo mCallingAppInfo;
    private final android.os.Bundle mData;
    private final java.lang.String mType;

    public BeginCreateCredentialRequest(java.lang.String str, android.os.Bundle bundle, android.service.credentials.CallingAppInfo callingAppInfo) {
        this.mType = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str, "type must not be null or empty");
        android.os.Bundle bundle2 = new android.os.Bundle();
        bundle2.putAll(bundle);
        this.mData = bundle2;
        this.mCallingAppInfo = callingAppInfo;
    }

    public BeginCreateCredentialRequest(java.lang.String str, android.os.Bundle bundle) {
        this(str, bundle, null);
    }

    private BeginCreateCredentialRequest(android.os.Parcel parcel) {
        this.mCallingAppInfo = (android.service.credentials.CallingAppInfo) parcel.readTypedObject(android.service.credentials.CallingAppInfo.CREATOR);
        this.mType = parcel.readString8();
        this.mData = parcel.readBundle(android.os.Bundle.class.getClassLoader());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.mCallingAppInfo, i);
        parcel.writeString8(this.mType);
        parcel.writeBundle(this.mData);
    }

    public android.service.credentials.CallingAppInfo getCallingAppInfo() {
        return this.mCallingAppInfo;
    }

    public java.lang.String getType() {
        return this.mType;
    }

    public android.os.Bundle getData() {
        return this.mData;
    }
}
