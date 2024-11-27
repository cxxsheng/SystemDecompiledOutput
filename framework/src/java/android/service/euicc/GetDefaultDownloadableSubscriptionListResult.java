package android.service.euicc;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class GetDefaultDownloadableSubscriptionListResult implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.euicc.GetDefaultDownloadableSubscriptionListResult> CREATOR = new android.os.Parcelable.Creator<android.service.euicc.GetDefaultDownloadableSubscriptionListResult>() { // from class: android.service.euicc.GetDefaultDownloadableSubscriptionListResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.euicc.GetDefaultDownloadableSubscriptionListResult createFromParcel(android.os.Parcel parcel) {
            return new android.service.euicc.GetDefaultDownloadableSubscriptionListResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.euicc.GetDefaultDownloadableSubscriptionListResult[] newArray(int i) {
            return new android.service.euicc.GetDefaultDownloadableSubscriptionListResult[i];
        }
    };
    private final android.telephony.euicc.DownloadableSubscription[] mSubscriptions;

    @java.lang.Deprecated
    public final int result;

    public int getResult() {
        return this.result;
    }

    public java.util.List<android.telephony.euicc.DownloadableSubscription> getDownloadableSubscriptions() {
        if (this.mSubscriptions == null) {
            return null;
        }
        return java.util.Arrays.asList(this.mSubscriptions);
    }

    public GetDefaultDownloadableSubscriptionListResult(int i, android.telephony.euicc.DownloadableSubscription[] downloadableSubscriptionArr) {
        this.result = i;
        if (this.result == 0) {
            this.mSubscriptions = downloadableSubscriptionArr;
        } else {
            if (downloadableSubscriptionArr != null) {
                throw new java.lang.IllegalArgumentException("Error result with non-null subscriptions: " + i);
            }
            this.mSubscriptions = null;
        }
    }

    private GetDefaultDownloadableSubscriptionListResult(android.os.Parcel parcel) {
        this.result = parcel.readInt();
        this.mSubscriptions = (android.telephony.euicc.DownloadableSubscription[]) parcel.createTypedArray(android.telephony.euicc.DownloadableSubscription.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.result);
        parcel.writeTypedArray(this.mSubscriptions, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
