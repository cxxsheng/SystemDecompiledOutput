package android.net;

/* loaded from: classes2.dex */
public final class NetworkScorerAppData implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.NetworkScorerAppData> CREATOR = new android.os.Parcelable.Creator<android.net.NetworkScorerAppData>() { // from class: android.net.NetworkScorerAppData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.NetworkScorerAppData createFromParcel(android.os.Parcel parcel) {
            return new android.net.NetworkScorerAppData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.NetworkScorerAppData[] newArray(int i) {
            return new android.net.NetworkScorerAppData[i];
        }
    };
    private final android.content.ComponentName mEnableUseOpenWifiActivity;
    private final java.lang.String mNetworkAvailableNotificationChannelId;
    private final android.content.ComponentName mRecommendationService;
    private final java.lang.String mRecommendationServiceLabel;
    public final int packageUid;

    public NetworkScorerAppData(int i, android.content.ComponentName componentName, java.lang.String str, android.content.ComponentName componentName2, java.lang.String str2) {
        this.packageUid = i;
        this.mRecommendationService = componentName;
        this.mRecommendationServiceLabel = str;
        this.mEnableUseOpenWifiActivity = componentName2;
        this.mNetworkAvailableNotificationChannelId = str2;
    }

    protected NetworkScorerAppData(android.os.Parcel parcel) {
        this.packageUid = parcel.readInt();
        this.mRecommendationService = android.content.ComponentName.readFromParcel(parcel);
        this.mRecommendationServiceLabel = parcel.readString();
        this.mEnableUseOpenWifiActivity = android.content.ComponentName.readFromParcel(parcel);
        this.mNetworkAvailableNotificationChannelId = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.packageUid);
        android.content.ComponentName.writeToParcel(this.mRecommendationService, parcel);
        parcel.writeString(this.mRecommendationServiceLabel);
        android.content.ComponentName.writeToParcel(this.mEnableUseOpenWifiActivity, parcel);
        parcel.writeString(this.mNetworkAvailableNotificationChannelId);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String getRecommendationServicePackageName() {
        return this.mRecommendationService.getPackageName();
    }

    public android.content.ComponentName getRecommendationServiceComponent() {
        return this.mRecommendationService;
    }

    public android.content.ComponentName getEnableUseOpenWifiActivity() {
        return this.mEnableUseOpenWifiActivity;
    }

    public java.lang.String getRecommendationServiceLabel() {
        return this.mRecommendationServiceLabel;
    }

    public java.lang.String getNetworkAvailableNotificationChannelId() {
        return this.mNetworkAvailableNotificationChannelId;
    }

    public java.lang.String toString() {
        return "NetworkScorerAppData{packageUid=" + this.packageUid + ", mRecommendationService=" + this.mRecommendationService + ", mRecommendationServiceLabel=" + this.mRecommendationServiceLabel + ", mEnableUseOpenWifiActivity=" + this.mEnableUseOpenWifiActivity + ", mNetworkAvailableNotificationChannelId=" + this.mNetworkAvailableNotificationChannelId + '}';
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.net.NetworkScorerAppData networkScorerAppData = (android.net.NetworkScorerAppData) obj;
        if (this.packageUid == networkScorerAppData.packageUid && java.util.Objects.equals(this.mRecommendationService, networkScorerAppData.mRecommendationService) && java.util.Objects.equals(this.mRecommendationServiceLabel, networkScorerAppData.mRecommendationServiceLabel) && java.util.Objects.equals(this.mEnableUseOpenWifiActivity, networkScorerAppData.mEnableUseOpenWifiActivity) && java.util.Objects.equals(this.mNetworkAvailableNotificationChannelId, networkScorerAppData.mNetworkAvailableNotificationChannelId)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.packageUid), this.mRecommendationService, this.mRecommendationServiceLabel, this.mEnableUseOpenWifiActivity, this.mNetworkAvailableNotificationChannelId);
    }
}
