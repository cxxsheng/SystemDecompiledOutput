package android.service.quickaccesswallet;

/* loaded from: classes3.dex */
public final class GetWalletCardsRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.quickaccesswallet.GetWalletCardsRequest> CREATOR = new android.os.Parcelable.Creator<android.service.quickaccesswallet.GetWalletCardsRequest>() { // from class: android.service.quickaccesswallet.GetWalletCardsRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.quickaccesswallet.GetWalletCardsRequest createFromParcel(android.os.Parcel parcel) {
            return new android.service.quickaccesswallet.GetWalletCardsRequest(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.quickaccesswallet.GetWalletCardsRequest[] newArray(int i) {
            return new android.service.quickaccesswallet.GetWalletCardsRequest[i];
        }
    };
    private final int mCardHeightPx;
    private final int mCardWidthPx;
    private final int mIconSizePx;
    private final int mMaxCards;

    public GetWalletCardsRequest(int i, int i2, int i3, int i4) {
        this.mCardWidthPx = i;
        this.mCardHeightPx = i2;
        this.mIconSizePx = i3;
        this.mMaxCards = i4;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mCardWidthPx);
        parcel.writeInt(this.mCardHeightPx);
        parcel.writeInt(this.mIconSizePx);
        parcel.writeInt(this.mMaxCards);
    }

    public int getCardWidthPx() {
        return this.mCardWidthPx;
    }

    public int getCardHeightPx() {
        return this.mCardHeightPx;
    }

    public int getIconSizePx() {
        return this.mIconSizePx;
    }

    public int getMaxCards() {
        return this.mMaxCards;
    }
}
