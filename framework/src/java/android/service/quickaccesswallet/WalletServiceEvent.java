package android.service.quickaccesswallet;

/* loaded from: classes3.dex */
public final class WalletServiceEvent implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.quickaccesswallet.WalletServiceEvent> CREATOR = new android.os.Parcelable.Creator<android.service.quickaccesswallet.WalletServiceEvent>() { // from class: android.service.quickaccesswallet.WalletServiceEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.quickaccesswallet.WalletServiceEvent createFromParcel(android.os.Parcel parcel) {
            return new android.service.quickaccesswallet.WalletServiceEvent(parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.quickaccesswallet.WalletServiceEvent[] newArray(int i) {
            return new android.service.quickaccesswallet.WalletServiceEvent[i];
        }
    };
    public static final int TYPE_NFC_PAYMENT_STARTED = 1;
    public static final int TYPE_WALLET_CARDS_UPDATED = 2;
    private final int mEventType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EventType {
    }

    public WalletServiceEvent(int i) {
        this.mEventType = i;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mEventType);
    }

    public int getEventType() {
        return this.mEventType;
    }
}
