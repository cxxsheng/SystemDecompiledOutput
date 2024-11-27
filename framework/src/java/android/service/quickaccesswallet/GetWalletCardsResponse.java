package android.service.quickaccesswallet;

/* loaded from: classes3.dex */
public final class GetWalletCardsResponse implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.quickaccesswallet.GetWalletCardsResponse> CREATOR = new android.os.Parcelable.Creator<android.service.quickaccesswallet.GetWalletCardsResponse>() { // from class: android.service.quickaccesswallet.GetWalletCardsResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.quickaccesswallet.GetWalletCardsResponse createFromParcel(android.os.Parcel parcel) {
            return android.service.quickaccesswallet.GetWalletCardsResponse.readFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.quickaccesswallet.GetWalletCardsResponse[] newArray(int i) {
            return new android.service.quickaccesswallet.GetWalletCardsResponse[i];
        }
    };
    private final int mSelectedIndex;
    private final java.util.List<android.service.quickaccesswallet.WalletCard> mWalletCards;

    public GetWalletCardsResponse(java.util.List<android.service.quickaccesswallet.WalletCard> list, int i) {
        this.mWalletCards = list;
        this.mSelectedIndex = i;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mWalletCards.size());
        parcel.writeParcelableList(this.mWalletCards, i);
        parcel.writeInt(this.mSelectedIndex);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.service.quickaccesswallet.GetWalletCardsResponse readFromParcel(android.os.Parcel parcel) {
        return new android.service.quickaccesswallet.GetWalletCardsResponse(parcel.readParcelableList(new java.util.ArrayList(parcel.readInt()), android.service.quickaccesswallet.WalletCard.class.getClassLoader(), android.service.quickaccesswallet.WalletCard.class), parcel.readInt());
    }

    public java.util.List<android.service.quickaccesswallet.WalletCard> getWalletCards() {
        return this.mWalletCards;
    }

    public int getSelectedIndex() {
        return this.mSelectedIndex;
    }
}
