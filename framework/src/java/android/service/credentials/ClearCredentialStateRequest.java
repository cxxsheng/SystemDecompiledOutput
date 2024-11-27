package android.service.credentials;

/* loaded from: classes3.dex */
public final class ClearCredentialStateRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.credentials.ClearCredentialStateRequest> CREATOR = new android.os.Parcelable.Creator<android.service.credentials.ClearCredentialStateRequest>() { // from class: android.service.credentials.ClearCredentialStateRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.credentials.ClearCredentialStateRequest[] newArray(int i) {
            return new android.service.credentials.ClearCredentialStateRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.credentials.ClearCredentialStateRequest createFromParcel(android.os.Parcel parcel) {
            return new android.service.credentials.ClearCredentialStateRequest(parcel);
        }
    };
    private final android.service.credentials.CallingAppInfo mCallingAppInfo;
    private final android.os.Bundle mData;

    public android.os.Bundle getData() {
        return this.mData;
    }

    public android.service.credentials.CallingAppInfo getCallingAppInfo() {
        return this.mCallingAppInfo;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.mCallingAppInfo, i);
        parcel.writeBundle(this.mData);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "ClearCredentialStateRequest {callingAppInfo=" + this.mCallingAppInfo.toString() + " }, {data= " + this.mData + "}";
    }

    public ClearCredentialStateRequest(android.service.credentials.CallingAppInfo callingAppInfo, android.os.Bundle bundle) {
        this.mCallingAppInfo = (android.service.credentials.CallingAppInfo) java.util.Objects.requireNonNull(callingAppInfo, "callingAppInfo must not be null");
        this.mData = (android.os.Bundle) java.util.Objects.requireNonNull(bundle, "data must not be null");
    }

    private ClearCredentialStateRequest(android.os.Parcel parcel) {
        this.mCallingAppInfo = (android.service.credentials.CallingAppInfo) parcel.readTypedObject(android.service.credentials.CallingAppInfo.CREATOR);
        this.mData = parcel.readBundle();
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mData);
    }
}
