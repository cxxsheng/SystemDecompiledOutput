package android.service.quickaccesswallet;

/* loaded from: classes3.dex */
public final class WalletCard implements android.os.Parcelable {
    public static final int CARD_TYPE_NON_PAYMENT = 2;
    public static final int CARD_TYPE_PAYMENT = 1;
    public static final int CARD_TYPE_UNKNOWN = 0;
    public static final android.os.Parcelable.Creator<android.service.quickaccesswallet.WalletCard> CREATOR = new android.os.Parcelable.Creator<android.service.quickaccesswallet.WalletCard>() { // from class: android.service.quickaccesswallet.WalletCard.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.quickaccesswallet.WalletCard createFromParcel(android.os.Parcel parcel) {
            return android.service.quickaccesswallet.WalletCard.readFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.quickaccesswallet.WalletCard[] newArray(int i) {
            return new android.service.quickaccesswallet.WalletCard[i];
        }
    };
    private final android.graphics.drawable.Icon mCardIcon;
    private final java.lang.String mCardId;
    private final android.graphics.drawable.Icon mCardImage;
    private final java.lang.CharSequence mCardLabel;
    private java.util.List<android.location.Location> mCardLocations;
    private final int mCardType;
    private final java.lang.CharSequence mContentDescription;
    private final android.graphics.drawable.Icon mNonPaymentCardSecondaryImage;
    private final android.app.PendingIntent mPendingIntent;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CardType {
    }

    private WalletCard(android.service.quickaccesswallet.WalletCard.Builder builder) {
        this.mCardId = builder.mCardId;
        this.mCardType = builder.mCardType;
        this.mCardImage = builder.mCardImage;
        this.mContentDescription = builder.mContentDescription;
        this.mPendingIntent = builder.mPendingIntent;
        this.mCardIcon = builder.mCardIcon;
        this.mCardLabel = builder.mCardLabel;
        this.mNonPaymentCardSecondaryImage = builder.mNonPaymentCardSecondaryImage;
        this.mCardLocations = builder.mCardLocations;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mCardId);
        parcel.writeInt(this.mCardType);
        this.mCardImage.writeToParcel(parcel, i);
        android.text.TextUtils.writeToParcel(this.mContentDescription, parcel, i);
        android.app.PendingIntent.writePendingIntentOrNullToParcel(this.mPendingIntent, parcel);
        writeIconIfNonNull(this.mCardIcon, parcel, i);
        android.text.TextUtils.writeToParcel(this.mCardLabel, parcel, i);
        writeIconIfNonNull(this.mNonPaymentCardSecondaryImage, parcel, i);
        parcel.writeTypedList(this.mCardLocations, i);
    }

    private void writeIconIfNonNull(android.graphics.drawable.Icon icon, android.os.Parcel parcel, int i) {
        if (icon == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            icon.writeToParcel(parcel, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.service.quickaccesswallet.WalletCard readFromParcel(android.os.Parcel parcel) {
        java.lang.String readString = parcel.readString();
        int readInt = parcel.readInt();
        android.graphics.drawable.Icon createFromParcel = android.graphics.drawable.Icon.CREATOR.createFromParcel(parcel);
        java.lang.CharSequence createFromParcel2 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        android.app.PendingIntent readPendingIntentOrNullFromParcel = android.app.PendingIntent.readPendingIntentOrNullFromParcel(parcel);
        android.graphics.drawable.Icon createFromParcel3 = parcel.readByte() == 0 ? null : android.graphics.drawable.Icon.CREATOR.createFromParcel(parcel);
        java.lang.CharSequence createFromParcel4 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        android.graphics.drawable.Icon createFromParcel5 = parcel.readByte() != 0 ? android.graphics.drawable.Icon.CREATOR.createFromParcel(parcel) : null;
        android.service.quickaccesswallet.WalletCard.Builder cardLabel = new android.service.quickaccesswallet.WalletCard.Builder(readString, readInt, createFromParcel, createFromParcel2, readPendingIntentOrNullFromParcel).setCardIcon(createFromParcel3).setCardLabel(createFromParcel4);
        if (readInt == 2) {
            cardLabel.setNonPaymentCardSecondaryImage(createFromParcel5);
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        parcel.readTypedList(arrayList, android.location.Location.CREATOR);
        cardLabel.setCardLocations(arrayList);
        return cardLabel.build();
    }

    public java.lang.String getCardId() {
        return this.mCardId;
    }

    public int getCardType() {
        return this.mCardType;
    }

    public android.graphics.drawable.Icon getCardImage() {
        return this.mCardImage;
    }

    public java.lang.CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    public android.app.PendingIntent getPendingIntent() {
        return this.mPendingIntent;
    }

    public android.graphics.drawable.Icon getCardIcon() {
        return this.mCardIcon;
    }

    public java.lang.CharSequence getCardLabel() {
        return this.mCardLabel;
    }

    public android.graphics.drawable.Icon getNonPaymentCardSecondaryImage() {
        return this.mNonPaymentCardSecondaryImage;
    }

    public java.util.List<android.location.Location> getCardLocations() {
        return this.mCardLocations;
    }

    public void removeCardLocations() {
        this.mCardLocations = new java.util.ArrayList();
    }

    public static final class Builder {
        private android.graphics.drawable.Icon mCardIcon;
        private java.lang.String mCardId;
        private android.graphics.drawable.Icon mCardImage;
        private java.lang.CharSequence mCardLabel;
        private java.util.List<android.location.Location> mCardLocations;
        private int mCardType;
        private java.lang.CharSequence mContentDescription;
        private android.graphics.drawable.Icon mNonPaymentCardSecondaryImage;
        private android.app.PendingIntent mPendingIntent;

        public Builder(java.lang.String str, int i, android.graphics.drawable.Icon icon, java.lang.CharSequence charSequence, android.app.PendingIntent pendingIntent) {
            this.mCardLocations = new java.util.ArrayList();
            this.mCardId = str;
            this.mCardType = i;
            this.mCardImage = icon;
            this.mContentDescription = charSequence;
            this.mPendingIntent = pendingIntent;
        }

        public Builder(java.lang.String str, android.graphics.drawable.Icon icon, java.lang.CharSequence charSequence, android.app.PendingIntent pendingIntent) {
            this(str, 0, icon, charSequence, pendingIntent);
        }

        public android.service.quickaccesswallet.WalletCard.Builder setCardIcon(android.graphics.drawable.Icon icon) {
            this.mCardIcon = icon;
            return this;
        }

        public android.service.quickaccesswallet.WalletCard.Builder setCardLabel(java.lang.CharSequence charSequence) {
            this.mCardLabel = charSequence;
            return this;
        }

        public android.service.quickaccesswallet.WalletCard.Builder setNonPaymentCardSecondaryImage(android.graphics.drawable.Icon icon) {
            com.android.internal.util.Preconditions.checkState(this.mCardType == 2, "This field can only be set on non-payment cards");
            this.mNonPaymentCardSecondaryImage = icon;
            return this;
        }

        public android.service.quickaccesswallet.WalletCard.Builder setCardLocations(java.util.List<android.location.Location> list) {
            com.android.internal.util.Preconditions.checkCollectionElementsNotNull(list, "cardLocations");
            this.mCardLocations = list;
            return this;
        }

        public android.service.quickaccesswallet.WalletCard build() {
            return new android.service.quickaccesswallet.WalletCard(this);
        }
    }
}
