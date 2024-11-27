package android.service.credentials;

/* loaded from: classes3.dex */
public final class CreateCredentialRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.credentials.CreateCredentialRequest> CREATOR = new android.os.Parcelable.Creator<android.service.credentials.CreateCredentialRequest>() { // from class: android.service.credentials.CreateCredentialRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.credentials.CreateCredentialRequest createFromParcel(android.os.Parcel parcel) {
            return new android.service.credentials.CreateCredentialRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.credentials.CreateCredentialRequest[] newArray(int i) {
            return new android.service.credentials.CreateCredentialRequest[i];
        }
    };
    private final android.service.credentials.CallingAppInfo mCallingAppInfo;
    private final android.os.Bundle mData;
    private final java.lang.String mType;

    public CreateCredentialRequest(android.service.credentials.CallingAppInfo callingAppInfo, java.lang.String str, android.os.Bundle bundle) {
        this.mCallingAppInfo = (android.service.credentials.CallingAppInfo) java.util.Objects.requireNonNull(callingAppInfo, "callingAppInfo must not be null");
        this.mType = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str, "type must not be null or empty");
        this.mData = (android.os.Bundle) java.util.Objects.requireNonNull(bundle, "data must not be null");
    }

    private CreateCredentialRequest(android.os.Parcel parcel) {
        this.mCallingAppInfo = (android.service.credentials.CallingAppInfo) parcel.readTypedObject(android.service.credentials.CallingAppInfo.CREATOR);
        this.mType = parcel.readString8();
        this.mData = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.mCallingAppInfo, i);
        parcel.writeString8(this.mType);
        parcel.writeTypedObject(this.mData, i);
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
