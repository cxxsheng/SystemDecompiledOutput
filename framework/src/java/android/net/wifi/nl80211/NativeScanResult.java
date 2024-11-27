package android.net.wifi.nl80211;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class NativeScanResult implements android.os.Parcelable {
    public static final int BSS_CAPABILITY_APSD = 2048;
    public static final int BSS_CAPABILITY_CF_POLLABLE = 4;
    public static final int BSS_CAPABILITY_CF_POLL_REQUEST = 8;
    public static final int BSS_CAPABILITY_CHANNEL_AGILITY = 128;
    public static final int BSS_CAPABILITY_DELAYED_BLOCK_ACK = 16384;
    public static final int BSS_CAPABILITY_DMG_ESS = 3;
    public static final int BSS_CAPABILITY_DMG_IBSS = 1;
    public static final int BSS_CAPABILITY_DSSS_OFDM = 8192;
    public static final int BSS_CAPABILITY_ESS = 1;
    public static final int BSS_CAPABILITY_IBSS = 2;
    public static final int BSS_CAPABILITY_IMMEDIATE_BLOCK_ACK = 32768;
    public static final int BSS_CAPABILITY_PBCC = 64;
    public static final int BSS_CAPABILITY_PRIVACY = 16;
    public static final int BSS_CAPABILITY_QOS = 512;
    public static final int BSS_CAPABILITY_RADIO_MANAGEMENT = 4096;
    public static final int BSS_CAPABILITY_SHORT_PREAMBLE = 32;
    public static final int BSS_CAPABILITY_SHORT_SLOT_TIME = 1024;
    public static final int BSS_CAPABILITY_SPECTRUM_MANAGEMENT = 256;
    public static final android.os.Parcelable.Creator<android.net.wifi.nl80211.NativeScanResult> CREATOR = new android.os.Parcelable.Creator<android.net.wifi.nl80211.NativeScanResult>() { // from class: android.net.wifi.nl80211.NativeScanResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.wifi.nl80211.NativeScanResult createFromParcel(android.os.Parcel parcel) {
            android.net.wifi.nl80211.NativeScanResult nativeScanResult = new android.net.wifi.nl80211.NativeScanResult();
            nativeScanResult.ssid = parcel.createByteArray();
            if (nativeScanResult.ssid == null) {
                nativeScanResult.ssid = new byte[0];
            }
            nativeScanResult.bssid = parcel.createByteArray();
            if (nativeScanResult.bssid == null) {
                nativeScanResult.bssid = new byte[0];
            }
            nativeScanResult.infoElement = parcel.createByteArray();
            if (nativeScanResult.infoElement == null) {
                nativeScanResult.infoElement = new byte[0];
            }
            nativeScanResult.frequency = parcel.readInt();
            nativeScanResult.signalMbm = parcel.readInt();
            nativeScanResult.tsf = parcel.readLong();
            nativeScanResult.capability = parcel.readInt();
            nativeScanResult.associated = parcel.readInt() != 0;
            nativeScanResult.radioChainInfos = new java.util.ArrayList();
            parcel.readTypedList(nativeScanResult.radioChainInfos, android.net.wifi.nl80211.RadioChainInfo.CREATOR);
            return nativeScanResult;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.wifi.nl80211.NativeScanResult[] newArray(int i) {
            return new android.net.wifi.nl80211.NativeScanResult[i];
        }
    };
    private static final java.lang.String TAG = "NativeScanResult";
    public boolean associated;
    public byte[] bssid;
    public int capability;
    public int frequency;
    public byte[] infoElement;
    public java.util.List<android.net.wifi.nl80211.RadioChainInfo> radioChainInfos;
    public int signalMbm;
    public byte[] ssid;
    public long tsf;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BssCapabilityBits {
    }

    public byte[] getSsid() {
        return this.ssid;
    }

    public android.net.MacAddress getBssid() {
        try {
            return android.net.MacAddress.fromBytes(this.bssid);
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Log.e(TAG, "Illegal argument " + java.util.Arrays.toString(this.bssid), e);
            return null;
        }
    }

    public byte[] getInformationElements() {
        return this.infoElement;
    }

    public int getFrequencyMhz() {
        return this.frequency;
    }

    public int getSignalMbm() {
        return this.signalMbm;
    }

    public long getTsf() {
        return this.tsf;
    }

    public boolean isAssociated() {
        return this.associated;
    }

    public int getCapabilities() {
        return this.capability;
    }

    public java.util.List<android.net.wifi.nl80211.RadioChainInfo> getRadioChainInfos() {
        return this.radioChainInfos;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeByteArray(this.ssid);
        parcel.writeByteArray(this.bssid);
        parcel.writeByteArray(this.infoElement);
        parcel.writeInt(this.frequency);
        parcel.writeInt(this.signalMbm);
        parcel.writeLong(this.tsf);
        parcel.writeInt(this.capability);
        parcel.writeInt(this.associated ? 1 : 0);
        parcel.writeTypedList(this.radioChainInfos);
    }
}
