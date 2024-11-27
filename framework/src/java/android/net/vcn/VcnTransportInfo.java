package android.net.vcn;

/* loaded from: classes2.dex */
public class VcnTransportInfo implements android.net.TransportInfo, android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.vcn.VcnTransportInfo> CREATOR = new android.os.Parcelable.Creator<android.net.vcn.VcnTransportInfo>() { // from class: android.net.vcn.VcnTransportInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.vcn.VcnTransportInfo createFromParcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            android.net.wifi.WifiInfo wifiInfo = (android.net.wifi.WifiInfo) parcel.readParcelable(null, android.net.wifi.WifiInfo.class);
            int readInt2 = parcel.readInt();
            if (wifiInfo == null && readInt == -1 && readInt2 == -1) {
                return null;
            }
            return new android.net.vcn.VcnTransportInfo(wifiInfo, readInt, readInt2);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.vcn.VcnTransportInfo[] newArray(int i) {
            return new android.net.vcn.VcnTransportInfo[i];
        }
    };
    private final int mMinUdpPort4500NatTimeoutSeconds;
    private final int mSubId;
    private final android.net.wifi.WifiInfo mWifiInfo;

    public VcnTransportInfo(android.net.wifi.WifiInfo wifiInfo) {
        this(wifiInfo, -1, -1);
    }

    public VcnTransportInfo(android.net.wifi.WifiInfo wifiInfo, int i) {
        this(wifiInfo, -1, i);
    }

    public VcnTransportInfo(int i) {
        this(null, i, -1);
    }

    public VcnTransportInfo(int i, int i2) {
        this(null, i, i2);
    }

    private VcnTransportInfo(android.net.wifi.WifiInfo wifiInfo, int i, int i2) {
        this.mWifiInfo = wifiInfo;
        this.mSubId = i;
        this.mMinUdpPort4500NatTimeoutSeconds = i2;
    }

    public android.net.wifi.WifiInfo getWifiInfo() {
        return this.mWifiInfo;
    }

    public int getSubId() {
        return this.mSubId;
    }

    public int getMinUdpPort4500NatTimeoutSeconds() {
        return this.mMinUdpPort4500NatTimeoutSeconds;
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mWifiInfo, java.lang.Integer.valueOf(this.mSubId), java.lang.Integer.valueOf(this.mMinUdpPort4500NatTimeoutSeconds));
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.net.vcn.VcnTransportInfo)) {
            return false;
        }
        android.net.vcn.VcnTransportInfo vcnTransportInfo = (android.net.vcn.VcnTransportInfo) obj;
        return java.util.Objects.equals(this.mWifiInfo, vcnTransportInfo.mWifiInfo) && this.mSubId == vcnTransportInfo.mSubId && this.mMinUdpPort4500NatTimeoutSeconds == vcnTransportInfo.mMinUdpPort4500NatTimeoutSeconds;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public android.net.TransportInfo makeCopy(long j) {
        if ((4 & j) != 0) {
            return new android.net.vcn.VcnTransportInfo(null, -1, -1);
        }
        return new android.net.vcn.VcnTransportInfo(this.mWifiInfo != null ? this.mWifiInfo.makeCopy(j) : null, this.mSubId, this.mMinUdpPort4500NatTimeoutSeconds);
    }

    public long getApplicableRedactions() {
        if (this.mWifiInfo != null) {
            return 4 | this.mWifiInfo.getApplicableRedactions();
        }
        return 4L;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mSubId);
        parcel.writeParcelable(this.mWifiInfo, i);
        parcel.writeInt(this.mMinUdpPort4500NatTimeoutSeconds);
    }

    public java.lang.String toString() {
        return "VcnTransportInfo { mWifiInfo = " + this.mWifiInfo + ", mSubId = " + this.mSubId + " }";
    }
}
