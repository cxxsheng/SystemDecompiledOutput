package android.net.wifi.nl80211;

/* loaded from: classes2.dex */
public class HiddenNetwork implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.wifi.nl80211.HiddenNetwork> CREATOR = new android.os.Parcelable.Creator<android.net.wifi.nl80211.HiddenNetwork>() { // from class: android.net.wifi.nl80211.HiddenNetwork.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.wifi.nl80211.HiddenNetwork createFromParcel(android.os.Parcel parcel) {
            android.net.wifi.nl80211.HiddenNetwork hiddenNetwork = new android.net.wifi.nl80211.HiddenNetwork();
            hiddenNetwork.ssid = parcel.createByteArray();
            return hiddenNetwork;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.wifi.nl80211.HiddenNetwork[] newArray(int i) {
            return new android.net.wifi.nl80211.HiddenNetwork[i];
        }
    };
    private static final java.lang.String TAG = "HiddenNetwork";
    public byte[] ssid;

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.net.wifi.nl80211.HiddenNetwork)) {
            return false;
        }
        return java.util.Arrays.equals(this.ssid, ((android.net.wifi.nl80211.HiddenNetwork) obj).ssid);
    }

    public int hashCode() {
        return java.util.Arrays.hashCode(this.ssid);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByteArray(this.ssid);
    }
}
