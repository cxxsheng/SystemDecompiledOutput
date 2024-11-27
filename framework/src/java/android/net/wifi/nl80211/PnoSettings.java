package android.net.wifi.nl80211;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class PnoSettings implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.wifi.nl80211.PnoSettings> CREATOR = new android.os.Parcelable.Creator<android.net.wifi.nl80211.PnoSettings>() { // from class: android.net.wifi.nl80211.PnoSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.wifi.nl80211.PnoSettings createFromParcel(android.os.Parcel parcel) {
            android.net.wifi.nl80211.PnoSettings pnoSettings = new android.net.wifi.nl80211.PnoSettings();
            pnoSettings.mIntervalMs = parcel.readLong();
            pnoSettings.mMin2gRssi = parcel.readInt();
            pnoSettings.mMin5gRssi = parcel.readInt();
            pnoSettings.mMin6gRssi = parcel.readInt();
            pnoSettings.mScanIterations = parcel.readInt();
            pnoSettings.mScanIntervalMultiplier = parcel.readInt();
            pnoSettings.mPnoNetworks = new java.util.ArrayList();
            parcel.readTypedList(pnoSettings.mPnoNetworks, android.net.wifi.nl80211.PnoNetwork.CREATOR);
            return pnoSettings;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.wifi.nl80211.PnoSettings[] newArray(int i) {
            return new android.net.wifi.nl80211.PnoSettings[i];
        }
    };
    private long mIntervalMs;
    private int mMin2gRssi;
    private int mMin5gRssi;
    private int mMin6gRssi;
    private java.util.List<android.net.wifi.nl80211.PnoNetwork> mPnoNetworks;
    private int mScanIntervalMultiplier;
    private int mScanIterations;

    public long getIntervalMillis() {
        return this.mIntervalMs;
    }

    public void setIntervalMillis(long j) {
        this.mIntervalMs = j;
    }

    public int getMin2gRssiDbm() {
        return this.mMin2gRssi;
    }

    public void setMin2gRssiDbm(int i) {
        this.mMin2gRssi = i;
    }

    public int getMin5gRssiDbm() {
        return this.mMin5gRssi;
    }

    public void setMin5gRssiDbm(int i) {
        this.mMin5gRssi = i;
    }

    public int getMin6gRssiDbm() {
        return this.mMin6gRssi;
    }

    public void setMin6gRssiDbm(int i) {
        this.mMin6gRssi = i;
    }

    public int getScanIterations() {
        return this.mScanIterations;
    }

    public void setScanIterations(int i) {
        this.mScanIterations = i;
    }

    public int getScanIntervalMultiplier() {
        return this.mScanIntervalMultiplier;
    }

    public void setScanIntervalMultiplier(int i) {
        this.mScanIntervalMultiplier = i;
    }

    public java.util.List<android.net.wifi.nl80211.PnoNetwork> getPnoNetworks() {
        return this.mPnoNetworks;
    }

    public void setPnoNetworks(java.util.List<android.net.wifi.nl80211.PnoNetwork> list) {
        this.mPnoNetworks = list;
    }

    public boolean equals(java.lang.Object obj) {
        android.net.wifi.nl80211.PnoSettings pnoSettings;
        if (this == obj) {
            return true;
        }
        if ((obj instanceof android.net.wifi.nl80211.PnoSettings) && (pnoSettings = (android.net.wifi.nl80211.PnoSettings) obj) != null) {
            return this.mIntervalMs == pnoSettings.mIntervalMs && this.mMin2gRssi == pnoSettings.mMin2gRssi && this.mMin5gRssi == pnoSettings.mMin5gRssi && this.mMin6gRssi == pnoSettings.mMin6gRssi && this.mScanIterations == pnoSettings.mScanIterations && this.mScanIntervalMultiplier == pnoSettings.mScanIntervalMultiplier && this.mPnoNetworks.equals(pnoSettings.mPnoNetworks);
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Long.valueOf(this.mIntervalMs), java.lang.Integer.valueOf(this.mMin2gRssi), java.lang.Integer.valueOf(this.mMin5gRssi), java.lang.Integer.valueOf(this.mMin6gRssi), java.lang.Integer.valueOf(this.mScanIterations), java.lang.Integer.valueOf(this.mScanIntervalMultiplier), this.mPnoNetworks);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeLong(this.mIntervalMs);
        parcel.writeInt(this.mMin2gRssi);
        parcel.writeInt(this.mMin5gRssi);
        parcel.writeInt(this.mMin6gRssi);
        parcel.writeInt(this.mScanIterations);
        parcel.writeInt(this.mScanIntervalMultiplier);
        parcel.writeTypedList(this.mPnoNetworks);
    }
}
