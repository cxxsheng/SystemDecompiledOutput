package android.net.wifi.nl80211;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class NativeWifiClient implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.wifi.nl80211.NativeWifiClient> CREATOR = new android.os.Parcelable.Creator<android.net.wifi.nl80211.NativeWifiClient>() { // from class: android.net.wifi.nl80211.NativeWifiClient.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.wifi.nl80211.NativeWifiClient createFromParcel(android.os.Parcel parcel) {
            android.net.MacAddress macAddress;
            try {
                macAddress = android.net.MacAddress.fromBytes(parcel.createByteArray());
            } catch (java.lang.IllegalArgumentException e) {
                macAddress = null;
            }
            return new android.net.wifi.nl80211.NativeWifiClient(macAddress);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.wifi.nl80211.NativeWifiClient[] newArray(int i) {
            return new android.net.wifi.nl80211.NativeWifiClient[i];
        }
    };
    private final android.net.MacAddress mMacAddress;

    public android.net.MacAddress getMacAddress() {
        return this.mMacAddress;
    }

    public NativeWifiClient(android.net.MacAddress macAddress) {
        this.mMacAddress = macAddress;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.net.wifi.nl80211.NativeWifiClient)) {
            return false;
        }
        return java.util.Objects.equals(this.mMacAddress, ((android.net.wifi.nl80211.NativeWifiClient) obj).mMacAddress);
    }

    public int hashCode() {
        return this.mMacAddress.hashCode();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByteArray(this.mMacAddress.toByteArray());
    }
}
