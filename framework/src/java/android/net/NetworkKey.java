package android.net;

@android.annotation.SystemApi
@java.lang.Deprecated
/* loaded from: classes2.dex */
public class NetworkKey implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.NetworkKey> CREATOR = new android.os.Parcelable.Creator<android.net.NetworkKey>() { // from class: android.net.NetworkKey.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.NetworkKey createFromParcel(android.os.Parcel parcel) {
            return new android.net.NetworkKey(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.NetworkKey[] newArray(int i) {
            return new android.net.NetworkKey[i];
        }
    };
    private static final java.lang.String TAG = "NetworkKey";
    public static final int TYPE_WIFI = 1;
    public final int type;
    public final android.net.WifiKey wifiKey;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NetworkType {
    }

    public static android.net.NetworkKey createFromScanResult(android.net.wifi.ScanResult scanResult) {
        java.util.Objects.requireNonNull(scanResult);
        java.lang.String str = scanResult.SSID;
        if (android.text.TextUtils.isEmpty(str) || str.equals("<unknown ssid>")) {
            return null;
        }
        java.lang.String str2 = scanResult.BSSID;
        if (android.text.TextUtils.isEmpty(str2)) {
            return null;
        }
        try {
            return new android.net.NetworkKey(new android.net.WifiKey(java.lang.String.format("\"%s\"", str), str2));
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Log.e(TAG, "Unable to create WifiKey.", e);
            return null;
        }
    }

    public static android.net.NetworkKey createFromWifiInfo(android.net.wifi.WifiInfo wifiInfo) {
        if (wifiInfo != null) {
            java.lang.String ssid = wifiInfo.getSSID();
            java.lang.String bssid = wifiInfo.getBSSID();
            if (!android.text.TextUtils.isEmpty(ssid) && !ssid.equals("<unknown ssid>") && !android.text.TextUtils.isEmpty(bssid)) {
                try {
                    return new android.net.NetworkKey(new android.net.WifiKey(ssid, bssid));
                } catch (java.lang.IllegalArgumentException e) {
                    android.util.Log.e(TAG, "Unable to create WifiKey.", e);
                    return null;
                }
            }
        }
        return null;
    }

    public NetworkKey(android.net.WifiKey wifiKey) {
        this.type = 1;
        this.wifiKey = wifiKey;
    }

    private NetworkKey(android.os.Parcel parcel) {
        this.type = parcel.readInt();
        switch (this.type) {
            case 1:
                this.wifiKey = android.net.WifiKey.CREATOR.createFromParcel(parcel);
                return;
            default:
                throw new java.lang.IllegalArgumentException("Parcel has unknown type: " + this.type);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.type);
        switch (this.type) {
            case 1:
                this.wifiKey.writeToParcel(parcel, i);
                return;
            default:
                throw new java.lang.IllegalStateException("NetworkKey has unknown type " + this.type);
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.net.NetworkKey networkKey = (android.net.NetworkKey) obj;
        if (this.type == networkKey.type && java.util.Objects.equals(this.wifiKey, networkKey.wifiKey)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.type), this.wifiKey);
    }

    public java.lang.String toString() {
        switch (this.type) {
            case 1:
                return this.wifiKey.toString();
            default:
                return "InvalidKey";
        }
    }
}
