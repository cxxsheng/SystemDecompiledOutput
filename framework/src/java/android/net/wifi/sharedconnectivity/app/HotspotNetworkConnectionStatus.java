package android.net.wifi.sharedconnectivity.app;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class HotspotNetworkConnectionStatus implements android.os.Parcelable {
    public static final int CONNECTION_STATUS_CONNECT_TO_HOTSPOT_FAILED = 9;
    public static final int CONNECTION_STATUS_ENABLING_HOTSPOT = 1;
    public static final int CONNECTION_STATUS_ENABLING_HOTSPOT_FAILED = 7;
    public static final int CONNECTION_STATUS_ENABLING_HOTSPOT_TIMEOUT = 8;
    public static final int CONNECTION_STATUS_NO_CELL_DATA = 6;
    public static final int CONNECTION_STATUS_PROVISIONING_FAILED = 3;
    public static final int CONNECTION_STATUS_TETHERING_TIMEOUT = 4;
    public static final int CONNECTION_STATUS_TETHERING_UNSUPPORTED = 5;
    public static final int CONNECTION_STATUS_UNKNOWN = 0;
    public static final int CONNECTION_STATUS_UNKNOWN_ERROR = 2;
    public static final android.os.Parcelable.Creator<android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus> CREATOR = new android.os.Parcelable.Creator<android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus>() { // from class: android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus createFromParcel(android.os.Parcel parcel) {
            return android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus.readFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus[] newArray(int i) {
            return new android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus[i];
        }
    };
    private final android.os.Bundle mExtras;
    private final android.net.wifi.sharedconnectivity.app.HotspotNetwork mHotspotNetwork;
    private final int mStatus;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ConnectionStatus {
    }

    public static final class Builder {
        private android.os.Bundle mExtras = android.os.Bundle.EMPTY;
        private android.net.wifi.sharedconnectivity.app.HotspotNetwork mHotspotNetwork;
        private int mStatus;

        public android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus.Builder setStatus(int i) {
            this.mStatus = i;
            return this;
        }

        public android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus.Builder setHotspotNetwork(android.net.wifi.sharedconnectivity.app.HotspotNetwork hotspotNetwork) {
            this.mHotspotNetwork = hotspotNetwork;
            return this;
        }

        public android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus.Builder setExtras(android.os.Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus build() {
            return new android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus(this.mStatus, this.mHotspotNetwork, this.mExtras);
        }
    }

    private static void validate(int i) {
        if (i != 0 && i != 1 && i != 2 && i != 3 && i != 4 && i != 5 && i != 6 && i != 7 && i != 8 && i != 9) {
            throw new java.lang.IllegalArgumentException("Illegal connection status");
        }
    }

    private HotspotNetworkConnectionStatus(int i, android.net.wifi.sharedconnectivity.app.HotspotNetwork hotspotNetwork, android.os.Bundle bundle) {
        validate(i);
        this.mStatus = i;
        this.mHotspotNetwork = hotspotNetwork;
        this.mExtras = bundle;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public android.net.wifi.sharedconnectivity.app.HotspotNetwork getHotspotNetwork() {
        return this.mHotspotNetwork;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus)) {
            return false;
        }
        android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus hotspotNetworkConnectionStatus = (android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus) obj;
        return this.mStatus == hotspotNetworkConnectionStatus.getStatus() && java.util.Objects.equals(this.mHotspotNetwork, hotspotNetworkConnectionStatus.getHotspotNetwork());
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mStatus), this.mHotspotNetwork);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mStatus);
        this.mHotspotNetwork.writeToParcel(parcel, i);
        parcel.writeBundle(this.mExtras);
    }

    public static android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus readFromParcel(android.os.Parcel parcel) {
        return new android.net.wifi.sharedconnectivity.app.HotspotNetworkConnectionStatus(parcel.readInt(), android.net.wifi.sharedconnectivity.app.HotspotNetwork.readFromParcel(parcel), parcel.readBundle());
    }

    public java.lang.String toString() {
        return "HotspotNetworkConnectionStatus[status=" + this.mStatus + "hotspot network=" + this.mHotspotNetwork.toString() + "extras=" + this.mExtras.toString() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }
}
