package android.net.wifi.sharedconnectivity.app;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class HotspotNetwork implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.wifi.sharedconnectivity.app.HotspotNetwork> CREATOR = new android.os.Parcelable.Creator<android.net.wifi.sharedconnectivity.app.HotspotNetwork>() { // from class: android.net.wifi.sharedconnectivity.app.HotspotNetwork.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.wifi.sharedconnectivity.app.HotspotNetwork createFromParcel(android.os.Parcel parcel) {
            return android.net.wifi.sharedconnectivity.app.HotspotNetwork.readFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.wifi.sharedconnectivity.app.HotspotNetwork[] newArray(int i) {
            return new android.net.wifi.sharedconnectivity.app.HotspotNetwork[i];
        }
    };
    public static final int NETWORK_TYPE_CELLULAR = 1;
    public static final int NETWORK_TYPE_ETHERNET = 3;
    public static final int NETWORK_TYPE_UNKNOWN = 0;
    public static final int NETWORK_TYPE_WIFI = 2;
    private final long mDeviceId;
    private final android.os.Bundle mExtras;
    private final java.lang.String mHotspotBssid;
    private final android.util.ArraySet<java.lang.Integer> mHotspotSecurityTypes;
    private final java.lang.String mHotspotSsid;
    private final java.lang.String mNetworkName;
    private final android.net.wifi.sharedconnectivity.app.NetworkProviderInfo mNetworkProviderInfo;
    private final int mNetworkType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NetworkType {
    }

    public static final class Builder {
        private java.lang.String mHotspotBssid;
        private java.lang.String mHotspotSsid;
        private java.lang.String mNetworkName;
        private android.net.wifi.sharedconnectivity.app.NetworkProviderInfo mNetworkProviderInfo;
        private int mNetworkType;
        private long mDeviceId = -1;
        private final android.util.ArraySet<java.lang.Integer> mHotspotSecurityTypes = new android.util.ArraySet<>();
        private android.os.Bundle mExtras = android.os.Bundle.EMPTY;

        public android.net.wifi.sharedconnectivity.app.HotspotNetwork.Builder setDeviceId(long j) {
            this.mDeviceId = j;
            return this;
        }

        public android.net.wifi.sharedconnectivity.app.HotspotNetwork.Builder setNetworkProviderInfo(android.net.wifi.sharedconnectivity.app.NetworkProviderInfo networkProviderInfo) {
            this.mNetworkProviderInfo = networkProviderInfo;
            return this;
        }

        public android.net.wifi.sharedconnectivity.app.HotspotNetwork.Builder setHostNetworkType(int i) {
            this.mNetworkType = i;
            return this;
        }

        public android.net.wifi.sharedconnectivity.app.HotspotNetwork.Builder setNetworkName(java.lang.String str) {
            this.mNetworkName = str;
            return this;
        }

        public android.net.wifi.sharedconnectivity.app.HotspotNetwork.Builder setHotspotSsid(java.lang.String str) {
            this.mHotspotSsid = str;
            return this;
        }

        public android.net.wifi.sharedconnectivity.app.HotspotNetwork.Builder setHotspotBssid(java.lang.String str) {
            this.mHotspotBssid = str;
            return this;
        }

        public android.net.wifi.sharedconnectivity.app.HotspotNetwork.Builder addHotspotSecurityType(int i) {
            this.mHotspotSecurityTypes.add(java.lang.Integer.valueOf(i));
            return this;
        }

        public android.net.wifi.sharedconnectivity.app.HotspotNetwork.Builder setExtras(android.os.Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public android.net.wifi.sharedconnectivity.app.HotspotNetwork build() {
            return new android.net.wifi.sharedconnectivity.app.HotspotNetwork(this.mDeviceId, this.mNetworkProviderInfo, this.mNetworkType, this.mNetworkName, this.mHotspotSsid, this.mHotspotBssid, this.mHotspotSecurityTypes, this.mExtras);
        }
    }

    private static void validate(long j, int i, java.lang.String str, android.net.wifi.sharedconnectivity.app.NetworkProviderInfo networkProviderInfo) {
        if (j < 0) {
            throw new java.lang.IllegalArgumentException("DeviceId must be set");
        }
        if (java.util.Objects.isNull(networkProviderInfo)) {
            throw new java.lang.IllegalArgumentException("NetworkProviderInfo must be set");
        }
        if (i != 1 && i != 2 && i != 3 && i != 0) {
            throw new java.lang.IllegalArgumentException("Illegal network type");
        }
        if (java.util.Objects.isNull(str)) {
            throw new java.lang.IllegalArgumentException("NetworkName must be set");
        }
    }

    private HotspotNetwork(long j, android.net.wifi.sharedconnectivity.app.NetworkProviderInfo networkProviderInfo, int i, java.lang.String str, java.lang.String str2, java.lang.String str3, android.util.ArraySet<java.lang.Integer> arraySet, android.os.Bundle bundle) {
        validate(j, i, str, networkProviderInfo);
        this.mDeviceId = j;
        this.mNetworkProviderInfo = networkProviderInfo;
        this.mNetworkType = i;
        this.mNetworkName = str;
        this.mHotspotSsid = str2;
        this.mHotspotBssid = str3;
        this.mHotspotSecurityTypes = new android.util.ArraySet<>((android.util.ArraySet) arraySet);
        this.mExtras = bundle;
    }

    public long getDeviceId() {
        return this.mDeviceId;
    }

    public android.net.wifi.sharedconnectivity.app.NetworkProviderInfo getNetworkProviderInfo() {
        return this.mNetworkProviderInfo;
    }

    public int getHostNetworkType() {
        return this.mNetworkType;
    }

    public java.lang.String getNetworkName() {
        return this.mNetworkName;
    }

    public java.lang.String getHotspotSsid() {
        return this.mHotspotSsid;
    }

    public java.lang.String getHotspotBssid() {
        return this.mHotspotBssid;
    }

    public java.util.Set<java.lang.Integer> getHotspotSecurityTypes() {
        return this.mHotspotSecurityTypes;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.net.wifi.sharedconnectivity.app.HotspotNetwork)) {
            return false;
        }
        android.net.wifi.sharedconnectivity.app.HotspotNetwork hotspotNetwork = (android.net.wifi.sharedconnectivity.app.HotspotNetwork) obj;
        return this.mDeviceId == hotspotNetwork.getDeviceId() && java.util.Objects.equals(this.mNetworkProviderInfo, hotspotNetwork.getNetworkProviderInfo()) && this.mNetworkType == hotspotNetwork.getHostNetworkType() && java.util.Objects.equals(this.mNetworkName, hotspotNetwork.getNetworkName()) && java.util.Objects.equals(this.mHotspotSsid, hotspotNetwork.getHotspotSsid()) && java.util.Objects.equals(this.mHotspotBssid, hotspotNetwork.getHotspotBssid()) && java.util.Objects.equals(this.mHotspotSecurityTypes, hotspotNetwork.getHotspotSecurityTypes());
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Long.valueOf(this.mDeviceId), this.mNetworkProviderInfo, this.mNetworkName, this.mHotspotSsid, this.mHotspotBssid, this.mHotspotSecurityTypes);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mDeviceId);
        this.mNetworkProviderInfo.writeToParcel(parcel, i);
        parcel.writeInt(this.mNetworkType);
        parcel.writeString(this.mNetworkName);
        parcel.writeString(this.mHotspotSsid);
        parcel.writeString(this.mHotspotBssid);
        parcel.writeArraySet(this.mHotspotSecurityTypes);
        parcel.writeBundle(this.mExtras);
    }

    public static android.net.wifi.sharedconnectivity.app.HotspotNetwork readFromParcel(android.os.Parcel parcel) {
        return new android.net.wifi.sharedconnectivity.app.HotspotNetwork(parcel.readLong(), android.net.wifi.sharedconnectivity.app.NetworkProviderInfo.readFromParcel(parcel), parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readArraySet(null), parcel.readBundle());
    }

    public java.lang.String toString() {
        return "HotspotNetwork[deviceId=" + this.mDeviceId + ", networkType=" + this.mNetworkType + ", networkProviderInfo=" + this.mNetworkProviderInfo.toString() + ", networkName=" + this.mNetworkName + ", hotspotSsid=" + this.mHotspotSsid + ", hotspotBssid=" + this.mHotspotBssid + ", hotspotSecurityTypes=" + this.mHotspotSecurityTypes.toString() + ", extras=" + this.mExtras.toString() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }
}
