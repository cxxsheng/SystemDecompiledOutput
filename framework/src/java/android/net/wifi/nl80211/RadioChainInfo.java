package android.net.wifi.nl80211;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class RadioChainInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.wifi.nl80211.RadioChainInfo> CREATOR = new android.os.Parcelable.Creator<android.net.wifi.nl80211.RadioChainInfo>() { // from class: android.net.wifi.nl80211.RadioChainInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.wifi.nl80211.RadioChainInfo createFromParcel(android.os.Parcel parcel) {
            return new android.net.wifi.nl80211.RadioChainInfo(parcel.readInt(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.wifi.nl80211.RadioChainInfo[] newArray(int i) {
            return new android.net.wifi.nl80211.RadioChainInfo[i];
        }
    };
    private static final java.lang.String TAG = "RadioChainInfo";
    public int chainId;
    public int level;

    public int getChainId() {
        return this.chainId;
    }

    public int getLevelDbm() {
        return this.level;
    }

    public RadioChainInfo(int i, int i2) {
        this.chainId = i;
        this.level = i2;
    }

    public boolean equals(java.lang.Object obj) {
        android.net.wifi.nl80211.RadioChainInfo radioChainInfo;
        if (this == obj) {
            return true;
        }
        if ((obj instanceof android.net.wifi.nl80211.RadioChainInfo) && (radioChainInfo = (android.net.wifi.nl80211.RadioChainInfo) obj) != null) {
            return this.chainId == radioChainInfo.chainId && this.level == radioChainInfo.level;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.chainId), java.lang.Integer.valueOf(this.level));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.chainId);
        parcel.writeInt(this.level);
    }
}
