package android.service.euicc;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class DownloadSubscriptionResult implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.euicc.DownloadSubscriptionResult> CREATOR = new android.os.Parcelable.Creator<android.service.euicc.DownloadSubscriptionResult>() { // from class: android.service.euicc.DownloadSubscriptionResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.euicc.DownloadSubscriptionResult createFromParcel(android.os.Parcel parcel) {
            return new android.service.euicc.DownloadSubscriptionResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.euicc.DownloadSubscriptionResult[] newArray(int i) {
            return new android.service.euicc.DownloadSubscriptionResult[i];
        }
    };
    private final int mCardId;
    private final int mResolvableErrors;
    private final int mResult;

    public DownloadSubscriptionResult(int i, int i2, int i3) {
        this.mResult = i;
        this.mResolvableErrors = i2;
        this.mCardId = i3;
    }

    public int getResult() {
        return this.mResult;
    }

    public int getResolvableErrors() {
        return this.mResolvableErrors;
    }

    public int getCardId() {
        return this.mCardId;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mResult);
        parcel.writeInt(this.mResolvableErrors);
        parcel.writeInt(this.mCardId);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private DownloadSubscriptionResult(android.os.Parcel parcel) {
        this.mResult = parcel.readInt();
        this.mResolvableErrors = parcel.readInt();
        this.mCardId = parcel.readInt();
    }
}
