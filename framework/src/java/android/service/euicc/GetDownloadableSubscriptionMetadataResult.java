package android.service.euicc;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class GetDownloadableSubscriptionMetadataResult implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.euicc.GetDownloadableSubscriptionMetadataResult> CREATOR = new android.os.Parcelable.Creator<android.service.euicc.GetDownloadableSubscriptionMetadataResult>() { // from class: android.service.euicc.GetDownloadableSubscriptionMetadataResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.euicc.GetDownloadableSubscriptionMetadataResult createFromParcel(android.os.Parcel parcel) {
            return new android.service.euicc.GetDownloadableSubscriptionMetadataResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.euicc.GetDownloadableSubscriptionMetadataResult[] newArray(int i) {
            return new android.service.euicc.GetDownloadableSubscriptionMetadataResult[i];
        }
    };
    private final android.telephony.euicc.DownloadableSubscription mSubscription;

    @java.lang.Deprecated
    public final int result;

    public int getResult() {
        return this.result;
    }

    public android.telephony.euicc.DownloadableSubscription getDownloadableSubscription() {
        return this.mSubscription;
    }

    public GetDownloadableSubscriptionMetadataResult(int i, android.telephony.euicc.DownloadableSubscription downloadableSubscription) {
        this.result = i;
        if (this.result == 0) {
            this.mSubscription = downloadableSubscription;
        } else {
            if (downloadableSubscription != null) {
                throw new java.lang.IllegalArgumentException("Error result with non-null subscription: " + i);
            }
            this.mSubscription = null;
        }
    }

    private GetDownloadableSubscriptionMetadataResult(android.os.Parcel parcel) {
        this.result = parcel.readInt();
        this.mSubscription = (android.telephony.euicc.DownloadableSubscription) parcel.readTypedObject(android.telephony.euicc.DownloadableSubscription.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.result);
        parcel.writeTypedObject(this.mSubscription, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
