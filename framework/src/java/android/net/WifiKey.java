package android.net;

@android.annotation.SystemApi
@java.lang.Deprecated
/* loaded from: classes2.dex */
public class WifiKey implements android.os.Parcelable {
    public final java.lang.String bssid;
    public final java.lang.String ssid;
    private static final java.util.regex.Pattern SSID_PATTERN = java.util.regex.Pattern.compile("(\".*\")|(0x[\\p{XDigit}]+)", 32);
    private static final java.util.regex.Pattern BSSID_PATTERN = java.util.regex.Pattern.compile("([\\p{XDigit}]{2}:){5}[\\p{XDigit}]{2}");
    public static final android.os.Parcelable.Creator<android.net.WifiKey> CREATOR = new android.os.Parcelable.Creator<android.net.WifiKey>() { // from class: android.net.WifiKey.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.WifiKey createFromParcel(android.os.Parcel parcel) {
            return new android.net.WifiKey(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.WifiKey[] newArray(int i) {
            return new android.net.WifiKey[i];
        }
    };

    public WifiKey(java.lang.String str, java.lang.String str2) {
        if (str == null || !SSID_PATTERN.matcher(str).matches()) {
            throw new java.lang.IllegalArgumentException("Invalid ssid: " + str);
        }
        if (str2 == null || !BSSID_PATTERN.matcher(str2).matches()) {
            throw new java.lang.IllegalArgumentException("Invalid bssid: " + str2);
        }
        this.ssid = str;
        this.bssid = str2;
    }

    private WifiKey(android.os.Parcel parcel) {
        this.ssid = parcel.readString();
        this.bssid = parcel.readString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.ssid);
        parcel.writeString(this.bssid);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.net.WifiKey wifiKey = (android.net.WifiKey) obj;
        if (java.util.Objects.equals(this.ssid, wifiKey.ssid) && java.util.Objects.equals(this.bssid, wifiKey.bssid)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.ssid, this.bssid);
    }

    public java.lang.String toString() {
        return "WifiKey[SSID=" + this.ssid + ",BSSID=" + this.bssid + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }
}
