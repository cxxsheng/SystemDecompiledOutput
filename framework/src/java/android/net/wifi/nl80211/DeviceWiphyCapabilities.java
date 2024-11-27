package android.net.wifi.nl80211;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class DeviceWiphyCapabilities implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.wifi.nl80211.DeviceWiphyCapabilities> CREATOR = new android.os.Parcelable.Creator<android.net.wifi.nl80211.DeviceWiphyCapabilities>() { // from class: android.net.wifi.nl80211.DeviceWiphyCapabilities.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.wifi.nl80211.DeviceWiphyCapabilities createFromParcel(android.os.Parcel parcel) {
            android.net.wifi.nl80211.DeviceWiphyCapabilities deviceWiphyCapabilities = new android.net.wifi.nl80211.DeviceWiphyCapabilities();
            deviceWiphyCapabilities.m80211nSupported = parcel.readBoolean();
            deviceWiphyCapabilities.m80211acSupported = parcel.readBoolean();
            deviceWiphyCapabilities.m80211axSupported = parcel.readBoolean();
            deviceWiphyCapabilities.m80211beSupported = parcel.readBoolean();
            deviceWiphyCapabilities.mChannelWidth160MhzSupported = parcel.readBoolean();
            deviceWiphyCapabilities.mChannelWidth80p80MhzSupported = parcel.readBoolean();
            deviceWiphyCapabilities.mChannelWidth320MhzSupported = parcel.readBoolean();
            deviceWiphyCapabilities.mMaxNumberTxSpatialStreams = parcel.readInt();
            deviceWiphyCapabilities.mMaxNumberRxSpatialStreams = parcel.readInt();
            deviceWiphyCapabilities.mMaxNumberAkms = parcel.readInt();
            return deviceWiphyCapabilities;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.wifi.nl80211.DeviceWiphyCapabilities[] newArray(int i) {
            return new android.net.wifi.nl80211.DeviceWiphyCapabilities[i];
        }
    };
    private static final java.lang.String TAG = "DeviceWiphyCapabilities";
    private boolean m80211nSupported = false;
    private boolean m80211acSupported = false;
    private boolean m80211axSupported = false;
    private boolean m80211beSupported = false;
    private boolean mChannelWidth160MhzSupported = false;
    private boolean mChannelWidth80p80MhzSupported = false;
    private boolean mChannelWidth320MhzSupported = false;
    private int mMaxNumberTxSpatialStreams = 1;
    private int mMaxNumberRxSpatialStreams = 1;
    private int mMaxNumberAkms = 1;

    public boolean isWifiStandardSupported(int i) {
        switch (i) {
            case 1:
                return true;
            case 2:
            case 3:
            case 7:
            default:
                android.util.Log.e(TAG, "isWifiStandardSupported called with invalid standard: " + i);
                return false;
            case 4:
                return this.m80211nSupported;
            case 5:
                return this.m80211acSupported;
            case 6:
                return this.m80211axSupported;
            case 8:
                return this.m80211beSupported;
        }
    }

    public void setWifiStandardSupport(int i, boolean z) {
        switch (i) {
            case 4:
                this.m80211nSupported = z;
                break;
            case 5:
                this.m80211acSupported = z;
                break;
            case 6:
                this.m80211axSupported = z;
                break;
            case 7:
            default:
                android.util.Log.e(TAG, "setWifiStandardSupport called with invalid standard: " + i);
                break;
            case 8:
                this.m80211beSupported = z;
                break;
        }
    }

    public boolean isChannelWidthSupported(int i) {
        switch (i) {
            case 0:
                return true;
            case 1:
                return this.m80211nSupported || this.m80211acSupported || this.m80211axSupported || this.m80211beSupported;
            case 2:
                return this.m80211acSupported || this.m80211axSupported || this.m80211beSupported;
            case 3:
                return this.mChannelWidth160MhzSupported;
            case 4:
                return this.mChannelWidth80p80MhzSupported;
            case 5:
                return this.mChannelWidth320MhzSupported;
            default:
                android.util.Log.e(TAG, "isChannelWidthSupported called with invalid channel width: " + i);
                return false;
        }
    }

    public void setChannelWidthSupported(int i, boolean z) {
        switch (i) {
            case 3:
                this.mChannelWidth160MhzSupported = z;
                break;
            case 4:
                this.mChannelWidth80p80MhzSupported = z;
                break;
            case 5:
                this.mChannelWidth320MhzSupported = z;
                break;
            default:
                android.util.Log.e(TAG, "setChannelWidthSupported called with Invalid channel width: " + i);
                break;
        }
    }

    public int getMaxNumberTxSpatialStreams() {
        return this.mMaxNumberTxSpatialStreams;
    }

    public void setMaxNumberTxSpatialStreams(int i) {
        this.mMaxNumberTxSpatialStreams = i;
    }

    public int getMaxNumberRxSpatialStreams() {
        return this.mMaxNumberRxSpatialStreams;
    }

    public int getMaxNumberAkms() {
        return this.mMaxNumberAkms;
    }

    public void setMaxNumberAkms(int i) {
        this.mMaxNumberAkms = i;
    }

    public void setMaxNumberRxSpatialStreams(int i) {
        this.mMaxNumberRxSpatialStreams = i;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.net.wifi.nl80211.DeviceWiphyCapabilities)) {
            return false;
        }
        android.net.wifi.nl80211.DeviceWiphyCapabilities deviceWiphyCapabilities = (android.net.wifi.nl80211.DeviceWiphyCapabilities) obj;
        return this.m80211nSupported == deviceWiphyCapabilities.m80211nSupported && this.m80211acSupported == deviceWiphyCapabilities.m80211acSupported && this.m80211axSupported == deviceWiphyCapabilities.m80211axSupported && this.m80211beSupported == deviceWiphyCapabilities.m80211beSupported && this.mChannelWidth160MhzSupported == deviceWiphyCapabilities.mChannelWidth160MhzSupported && this.mChannelWidth80p80MhzSupported == deviceWiphyCapabilities.mChannelWidth80p80MhzSupported && this.mChannelWidth320MhzSupported == deviceWiphyCapabilities.mChannelWidth320MhzSupported && this.mMaxNumberTxSpatialStreams == deviceWiphyCapabilities.mMaxNumberTxSpatialStreams && this.mMaxNumberRxSpatialStreams == deviceWiphyCapabilities.mMaxNumberRxSpatialStreams && this.mMaxNumberAkms == deviceWiphyCapabilities.mMaxNumberAkms;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Boolean.valueOf(this.m80211nSupported), java.lang.Boolean.valueOf(this.m80211acSupported), java.lang.Boolean.valueOf(this.m80211axSupported), java.lang.Boolean.valueOf(this.m80211beSupported), java.lang.Boolean.valueOf(this.mChannelWidth160MhzSupported), java.lang.Boolean.valueOf(this.mChannelWidth80p80MhzSupported), java.lang.Boolean.valueOf(this.mChannelWidth320MhzSupported), java.lang.Integer.valueOf(this.mMaxNumberTxSpatialStreams), java.lang.Integer.valueOf(this.mMaxNumberRxSpatialStreams), java.lang.Integer.valueOf(this.mMaxNumberAkms));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeBoolean(this.m80211nSupported);
        parcel.writeBoolean(this.m80211acSupported);
        parcel.writeBoolean(this.m80211axSupported);
        parcel.writeBoolean(this.m80211beSupported);
        parcel.writeBoolean(this.mChannelWidth160MhzSupported);
        parcel.writeBoolean(this.mChannelWidth80p80MhzSupported);
        parcel.writeBoolean(this.mChannelWidth320MhzSupported);
        parcel.writeInt(this.mMaxNumberTxSpatialStreams);
        parcel.writeInt(this.mMaxNumberRxSpatialStreams);
        parcel.writeInt(this.mMaxNumberAkms);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("m80211nSupported:").append(this.m80211nSupported ? "Yes" : "No");
        sb.append("m80211acSupported:").append(this.m80211acSupported ? "Yes" : "No");
        sb.append("m80211axSupported:").append(this.m80211axSupported ? "Yes" : "No");
        sb.append("m80211beSupported:").append(this.m80211beSupported ? "Yes" : "No");
        sb.append("mChannelWidth160MhzSupported: ").append(this.mChannelWidth160MhzSupported ? "Yes" : "No");
        sb.append("mChannelWidth80p80MhzSupported: ").append(this.mChannelWidth80p80MhzSupported ? "Yes" : "No");
        sb.append("mChannelWidth320MhzSupported: ").append(this.mChannelWidth320MhzSupported ? "Yes" : "No");
        sb.append("mMaxNumberTxSpatialStreams: ").append(this.mMaxNumberTxSpatialStreams);
        sb.append("mMaxNumberRxSpatialStreams: ").append(this.mMaxNumberRxSpatialStreams);
        sb.append("mMaxNumberAkms: ").append(this.mMaxNumberAkms);
        return sb.toString();
    }
}
