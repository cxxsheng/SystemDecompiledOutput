package android.telecom;

/* loaded from: classes3.dex */
public class GatewayInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.telecom.GatewayInfo> CREATOR = new android.os.Parcelable.Creator<android.telecom.GatewayInfo>() { // from class: android.telecom.GatewayInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.GatewayInfo createFromParcel(android.os.Parcel parcel) {
            return new android.telecom.GatewayInfo(parcel.readString(), android.net.Uri.CREATOR.createFromParcel(parcel), android.net.Uri.CREATOR.createFromParcel(parcel));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.telecom.GatewayInfo[] newArray(int i) {
            return new android.telecom.GatewayInfo[i];
        }
    };
    private final android.net.Uri mGatewayAddress;
    private final java.lang.String mGatewayProviderPackageName;
    private final android.net.Uri mOriginalAddress;

    public GatewayInfo(java.lang.String str, android.net.Uri uri, android.net.Uri uri2) {
        this.mGatewayProviderPackageName = str;
        this.mGatewayAddress = uri;
        this.mOriginalAddress = uri2;
    }

    public java.lang.String getGatewayProviderPackageName() {
        return this.mGatewayProviderPackageName;
    }

    public android.net.Uri getGatewayAddress() {
        return this.mGatewayAddress;
    }

    public android.net.Uri getOriginalAddress() {
        return this.mOriginalAddress;
    }

    public boolean isEmpty() {
        return android.text.TextUtils.isEmpty(this.mGatewayProviderPackageName) || this.mGatewayAddress == null;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mGatewayProviderPackageName);
        android.net.Uri.writeToParcel(parcel, this.mGatewayAddress);
        android.net.Uri.writeToParcel(parcel, this.mOriginalAddress);
    }
}
