package android.service.quickaccesswallet;

/* loaded from: classes3.dex */
public final class SelectWalletCardRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.quickaccesswallet.SelectWalletCardRequest> CREATOR = new android.os.Parcelable.Creator<android.service.quickaccesswallet.SelectWalletCardRequest>() { // from class: android.service.quickaccesswallet.SelectWalletCardRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.quickaccesswallet.SelectWalletCardRequest createFromParcel(android.os.Parcel parcel) {
            return new android.service.quickaccesswallet.SelectWalletCardRequest(parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.quickaccesswallet.SelectWalletCardRequest[] newArray(int i) {
            return new android.service.quickaccesswallet.SelectWalletCardRequest[i];
        }
    };
    private final java.lang.String mCardId;

    public SelectWalletCardRequest(java.lang.String str) {
        this.mCardId = str;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mCardId);
    }

    public java.lang.String getCardId() {
        return this.mCardId;
    }
}
