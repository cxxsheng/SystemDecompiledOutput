package android.security.keystore;

/* loaded from: classes3.dex */
public class KeystoreResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.security.keystore.KeystoreResponse> CREATOR = new android.os.Parcelable.Creator<android.security.keystore.KeystoreResponse>() { // from class: android.security.keystore.KeystoreResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.keystore.KeystoreResponse createFromParcel(android.os.Parcel parcel) {
            return new android.security.keystore.KeystoreResponse(parcel.readInt(), parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.security.keystore.KeystoreResponse[] newArray(int i) {
            return new android.security.keystore.KeystoreResponse[i];
        }
    };
    public final int error_code_;
    public final java.lang.String error_msg_;

    protected KeystoreResponse(int i, java.lang.String str) {
        this.error_code_ = i;
        this.error_msg_ = str;
    }

    public final int getErrorCode() {
        return this.error_code_;
    }

    public final java.lang.String getErrorMessage() {
        return this.error_msg_;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.error_code_);
        parcel.writeString(this.error_msg_);
    }
}
