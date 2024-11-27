package android.net.wifi.nl80211;

/* loaded from: classes2.dex */
public class ChannelSettings implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.wifi.nl80211.ChannelSettings> CREATOR = new android.os.Parcelable.Creator<android.net.wifi.nl80211.ChannelSettings>() { // from class: android.net.wifi.nl80211.ChannelSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.wifi.nl80211.ChannelSettings createFromParcel(android.os.Parcel parcel) {
            android.net.wifi.nl80211.ChannelSettings channelSettings = new android.net.wifi.nl80211.ChannelSettings();
            channelSettings.frequency = parcel.readInt();
            if (parcel.dataAvail() != 0) {
                android.util.Log.e(android.net.wifi.nl80211.ChannelSettings.TAG, "Found trailing data after parcel parsing.");
            }
            return channelSettings;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.wifi.nl80211.ChannelSettings[] newArray(int i) {
            return new android.net.wifi.nl80211.ChannelSettings[i];
        }
    };
    private static final java.lang.String TAG = "ChannelSettings";
    public int frequency;

    public boolean equals(java.lang.Object obj) {
        android.net.wifi.nl80211.ChannelSettings channelSettings;
        if (this == obj) {
            return true;
        }
        return (obj instanceof android.net.wifi.nl80211.ChannelSettings) && (channelSettings = (android.net.wifi.nl80211.ChannelSettings) obj) != null && this.frequency == channelSettings.frequency;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.frequency));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.frequency);
    }
}
