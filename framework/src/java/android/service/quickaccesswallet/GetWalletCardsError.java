package android.service.quickaccesswallet;

/* loaded from: classes3.dex */
public final class GetWalletCardsError implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.quickaccesswallet.GetWalletCardsError> CREATOR = new android.os.Parcelable.Creator<android.service.quickaccesswallet.GetWalletCardsError>() { // from class: android.service.quickaccesswallet.GetWalletCardsError.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.quickaccesswallet.GetWalletCardsError createFromParcel(android.os.Parcel parcel) {
            return android.service.quickaccesswallet.GetWalletCardsError.readFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.quickaccesswallet.GetWalletCardsError[] newArray(int i) {
            return new android.service.quickaccesswallet.GetWalletCardsError[i];
        }
    };
    private final android.graphics.drawable.Icon mIcon;
    private final java.lang.CharSequence mMessage;

    public GetWalletCardsError(android.graphics.drawable.Icon icon, java.lang.CharSequence charSequence) {
        this.mIcon = icon;
        this.mMessage = charSequence;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (this.mIcon == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            this.mIcon.writeToParcel(parcel, i);
        }
        android.text.TextUtils.writeToParcel(this.mMessage, parcel, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.service.quickaccesswallet.GetWalletCardsError readFromParcel(android.os.Parcel parcel) {
        return new android.service.quickaccesswallet.GetWalletCardsError(parcel.readByte() == 0 ? null : android.graphics.drawable.Icon.CREATOR.createFromParcel(parcel), android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel));
    }

    public android.graphics.drawable.Icon getIcon() {
        return this.mIcon;
    }

    public java.lang.CharSequence getMessage() {
        return this.mMessage;
    }
}
