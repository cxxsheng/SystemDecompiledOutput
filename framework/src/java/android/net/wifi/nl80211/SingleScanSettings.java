package android.net.wifi.nl80211;

/* loaded from: classes2.dex */
public class SingleScanSettings implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.wifi.nl80211.SingleScanSettings> CREATOR = new android.os.Parcelable.Creator<android.net.wifi.nl80211.SingleScanSettings>() { // from class: android.net.wifi.nl80211.SingleScanSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.wifi.nl80211.SingleScanSettings createFromParcel(android.os.Parcel parcel) {
            android.net.wifi.nl80211.SingleScanSettings singleScanSettings = new android.net.wifi.nl80211.SingleScanSettings();
            singleScanSettings.scanType = parcel.readInt();
            if (!android.net.wifi.nl80211.SingleScanSettings.isValidScanType(singleScanSettings.scanType)) {
                android.util.Log.wtf(android.net.wifi.nl80211.SingleScanSettings.TAG, "Invalid scan type " + singleScanSettings.scanType);
            }
            singleScanSettings.enable6GhzRnr = parcel.readBoolean();
            singleScanSettings.channelSettings = new java.util.ArrayList<>();
            parcel.readTypedList(singleScanSettings.channelSettings, android.net.wifi.nl80211.ChannelSettings.CREATOR);
            singleScanSettings.hiddenNetworks = new java.util.ArrayList<>();
            parcel.readTypedList(singleScanSettings.hiddenNetworks, android.net.wifi.nl80211.HiddenNetwork.CREATOR);
            singleScanSettings.vendorIes = parcel.createByteArray();
            if (singleScanSettings.vendorIes == null) {
                singleScanSettings.vendorIes = new byte[0];
            }
            if (parcel.dataAvail() != 0) {
                android.util.Log.e(android.net.wifi.nl80211.SingleScanSettings.TAG, "Found trailing data after parcel parsing.");
            }
            return singleScanSettings;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.wifi.nl80211.SingleScanSettings[] newArray(int i) {
            return new android.net.wifi.nl80211.SingleScanSettings[i];
        }
    };
    private static final java.lang.String TAG = "SingleScanSettings";
    public java.util.ArrayList<android.net.wifi.nl80211.ChannelSettings> channelSettings;
    public boolean enable6GhzRnr;
    public java.util.ArrayList<android.net.wifi.nl80211.HiddenNetwork> hiddenNetworks;
    public int scanType;
    public byte[] vendorIes;

    public boolean equals(java.lang.Object obj) {
        android.net.wifi.nl80211.SingleScanSettings singleScanSettings;
        if (this == obj) {
            return true;
        }
        if ((obj instanceof android.net.wifi.nl80211.SingleScanSettings) && (singleScanSettings = (android.net.wifi.nl80211.SingleScanSettings) obj) != null) {
            return this.scanType == singleScanSettings.scanType && this.enable6GhzRnr == singleScanSettings.enable6GhzRnr && this.channelSettings.equals(singleScanSettings.channelSettings) && this.hiddenNetworks.equals(singleScanSettings.hiddenNetworks) && java.util.Arrays.equals(this.vendorIes, singleScanSettings.vendorIes);
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.scanType), this.channelSettings, this.hiddenNetworks, java.lang.Boolean.valueOf(this.enable6GhzRnr), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.vendorIes)));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidScanType(int i) {
        return i == 0 || i == 1 || i == 2;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (!isValidScanType(this.scanType)) {
            android.util.Log.wtf(TAG, "Invalid scan type " + this.scanType);
        }
        parcel.writeInt(this.scanType);
        parcel.writeBoolean(this.enable6GhzRnr);
        parcel.writeTypedList(this.channelSettings);
        parcel.writeTypedList(this.hiddenNetworks);
        if (this.vendorIes == null) {
            parcel.writeByteArray(new byte[0]);
        } else {
            parcel.writeByteArray(this.vendorIes);
        }
    }
}
