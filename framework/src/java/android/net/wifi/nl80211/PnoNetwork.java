package android.net.wifi.nl80211;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class PnoNetwork implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.wifi.nl80211.PnoNetwork> CREATOR = new android.os.Parcelable.Creator<android.net.wifi.nl80211.PnoNetwork>() { // from class: android.net.wifi.nl80211.PnoNetwork.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.wifi.nl80211.PnoNetwork createFromParcel(android.os.Parcel parcel) {
            android.net.wifi.nl80211.PnoNetwork pnoNetwork = new android.net.wifi.nl80211.PnoNetwork();
            pnoNetwork.mIsHidden = parcel.readInt() != 0;
            pnoNetwork.mSsid = parcel.createByteArray();
            if (pnoNetwork.mSsid == null) {
                pnoNetwork.mSsid = new byte[0];
            }
            pnoNetwork.mFrequencies = parcel.createIntArray();
            if (pnoNetwork.mFrequencies == null) {
                pnoNetwork.mFrequencies = new int[0];
            }
            return pnoNetwork;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.wifi.nl80211.PnoNetwork[] newArray(int i) {
            return new android.net.wifi.nl80211.PnoNetwork[i];
        }
    };
    private int[] mFrequencies;
    private boolean mIsHidden;
    private byte[] mSsid;

    public boolean isHidden() {
        return this.mIsHidden;
    }

    public void setHidden(boolean z) {
        this.mIsHidden = z;
    }

    public byte[] getSsid() {
        return this.mSsid;
    }

    public void setSsid(byte[] bArr) {
        if (bArr == null) {
            throw new java.lang.IllegalArgumentException("null argument");
        }
        this.mSsid = bArr;
    }

    public int[] getFrequenciesMhz() {
        return this.mFrequencies;
    }

    public void setFrequenciesMhz(int[] iArr) {
        if (iArr == null) {
            throw new java.lang.IllegalArgumentException("null argument");
        }
        this.mFrequencies = iArr;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.net.wifi.nl80211.PnoNetwork)) {
            return false;
        }
        android.net.wifi.nl80211.PnoNetwork pnoNetwork = (android.net.wifi.nl80211.PnoNetwork) obj;
        return java.util.Arrays.equals(this.mSsid, pnoNetwork.mSsid) && java.util.Arrays.equals(this.mFrequencies, pnoNetwork.mFrequencies) && this.mIsHidden == pnoNetwork.mIsHidden;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Boolean.valueOf(this.mIsHidden), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mSsid)), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mFrequencies)));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mIsHidden ? 1 : 0);
        parcel.writeByteArray(this.mSsid);
        parcel.writeIntArray(this.mFrequencies);
    }
}
