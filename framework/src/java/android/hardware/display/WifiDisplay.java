package android.hardware.display;

/* loaded from: classes2.dex */
public final class WifiDisplay implements android.os.Parcelable {
    private final boolean mCanConnect;
    private final java.lang.String mDeviceAddress;
    private final java.lang.String mDeviceAlias;
    private final java.lang.String mDeviceName;
    private final boolean mIsAvailable;
    private final boolean mIsRemembered;
    public static final android.hardware.display.WifiDisplay[] EMPTY_ARRAY = new android.hardware.display.WifiDisplay[0];
    public static final android.os.Parcelable.Creator<android.hardware.display.WifiDisplay> CREATOR = new android.os.Parcelable.Creator<android.hardware.display.WifiDisplay>() { // from class: android.hardware.display.WifiDisplay.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.display.WifiDisplay createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.display.WifiDisplay(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readInt() != 0, parcel.readInt() != 0, parcel.readInt() != 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.display.WifiDisplay[] newArray(int i) {
            return i == 0 ? android.hardware.display.WifiDisplay.EMPTY_ARRAY : new android.hardware.display.WifiDisplay[i];
        }
    };

    public WifiDisplay(java.lang.String str, java.lang.String str2, java.lang.String str3, boolean z, boolean z2, boolean z3) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("deviceAddress must not be null");
        }
        if (str2 == null) {
            throw new java.lang.IllegalArgumentException("deviceName must not be null");
        }
        this.mDeviceAddress = str;
        this.mDeviceName = str2;
        this.mDeviceAlias = str3;
        this.mIsAvailable = z;
        this.mCanConnect = z2;
        this.mIsRemembered = z3;
    }

    public java.lang.String getDeviceAddress() {
        return this.mDeviceAddress;
    }

    public java.lang.String getDeviceName() {
        return this.mDeviceName;
    }

    public java.lang.String getDeviceAlias() {
        return this.mDeviceAlias;
    }

    public boolean isAvailable() {
        return this.mIsAvailable;
    }

    public boolean canConnect() {
        return this.mCanConnect;
    }

    public boolean isRemembered() {
        return this.mIsRemembered;
    }

    public java.lang.String getFriendlyDisplayName() {
        return this.mDeviceAlias != null ? this.mDeviceAlias : this.mDeviceName;
    }

    public boolean equals(java.lang.Object obj) {
        return (obj instanceof android.hardware.display.WifiDisplay) && equals((android.hardware.display.WifiDisplay) obj);
    }

    public boolean equals(android.hardware.display.WifiDisplay wifiDisplay) {
        return wifiDisplay != null && this.mDeviceAddress.equals(wifiDisplay.mDeviceAddress) && this.mDeviceName.equals(wifiDisplay.mDeviceName) && java.util.Objects.equals(this.mDeviceAlias, wifiDisplay.mDeviceAlias);
    }

    public boolean hasSameAddress(android.hardware.display.WifiDisplay wifiDisplay) {
        return wifiDisplay != null && this.mDeviceAddress.equals(wifiDisplay.mDeviceAddress);
    }

    public int hashCode() {
        return this.mDeviceAddress.hashCode();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mDeviceAddress);
        parcel.writeString(this.mDeviceName);
        parcel.writeString(this.mDeviceAlias);
        parcel.writeInt(this.mIsAvailable ? 1 : 0);
        parcel.writeInt(this.mCanConnect ? 1 : 0);
        parcel.writeInt(this.mIsRemembered ? 1 : 0);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public java.lang.String toString() {
        java.lang.String str = this.mDeviceName + " (" + this.mDeviceAddress + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        if (this.mDeviceAlias != null) {
            str = str + ", alias " + this.mDeviceAlias;
        }
        return str + ", isAvailable " + this.mIsAvailable + ", canConnect " + this.mCanConnect + ", isRemembered " + this.mIsRemembered;
    }
}
