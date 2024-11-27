package android.credentials;

/* loaded from: classes.dex */
public final class ClearCredentialStateRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.credentials.ClearCredentialStateRequest> CREATOR = new android.os.Parcelable.Creator<android.credentials.ClearCredentialStateRequest>() { // from class: android.credentials.ClearCredentialStateRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.ClearCredentialStateRequest[] newArray(int i) {
            return new android.credentials.ClearCredentialStateRequest[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.credentials.ClearCredentialStateRequest createFromParcel(android.os.Parcel parcel) {
            return new android.credentials.ClearCredentialStateRequest(parcel);
        }
    };
    private final android.os.Bundle mData;

    public android.os.Bundle getData() {
        return this.mData;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeBundle(this.mData);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        return "ClearCredentialStateRequest {data=" + this.mData + "}";
    }

    public ClearCredentialStateRequest(android.os.Bundle bundle) {
        this.mData = (android.os.Bundle) java.util.Objects.requireNonNull(bundle, "data must not be null");
    }

    private ClearCredentialStateRequest(android.os.Parcel parcel) {
        this.mData = parcel.readBundle();
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mData);
    }
}
