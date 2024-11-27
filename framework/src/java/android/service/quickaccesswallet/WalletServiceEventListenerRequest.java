package android.service.quickaccesswallet;

/* loaded from: classes3.dex */
public final class WalletServiceEventListenerRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.quickaccesswallet.WalletServiceEventListenerRequest> CREATOR = new android.os.Parcelable.Creator<android.service.quickaccesswallet.WalletServiceEventListenerRequest>() { // from class: android.service.quickaccesswallet.WalletServiceEventListenerRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.quickaccesswallet.WalletServiceEventListenerRequest createFromParcel(android.os.Parcel parcel) {
            return android.service.quickaccesswallet.WalletServiceEventListenerRequest.readFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.quickaccesswallet.WalletServiceEventListenerRequest[] newArray(int i) {
            return new android.service.quickaccesswallet.WalletServiceEventListenerRequest[i];
        }
    };
    private final java.lang.String mListenerId;

    public WalletServiceEventListenerRequest(java.lang.String str) {
        this.mListenerId = str;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mListenerId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.service.quickaccesswallet.WalletServiceEventListenerRequest readFromParcel(android.os.Parcel parcel) {
        return new android.service.quickaccesswallet.WalletServiceEventListenerRequest(parcel.readString());
    }

    public java.lang.String getListenerId() {
        return this.mListenerId;
    }
}
