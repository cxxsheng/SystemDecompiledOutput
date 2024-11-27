package android.net.wifi.sharedconnectivity.app;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class NetworkProviderInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.net.wifi.sharedconnectivity.app.NetworkProviderInfo> CREATOR = new android.os.Parcelable.Creator<android.net.wifi.sharedconnectivity.app.NetworkProviderInfo>() { // from class: android.net.wifi.sharedconnectivity.app.NetworkProviderInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.wifi.sharedconnectivity.app.NetworkProviderInfo createFromParcel(android.os.Parcel parcel) {
            return android.net.wifi.sharedconnectivity.app.NetworkProviderInfo.readFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.net.wifi.sharedconnectivity.app.NetworkProviderInfo[] newArray(int i) {
            return new android.net.wifi.sharedconnectivity.app.NetworkProviderInfo[i];
        }
    };
    public static final int DEVICE_TYPE_AUTO = 5;
    public static final int DEVICE_TYPE_LAPTOP = 3;
    public static final int DEVICE_TYPE_PHONE = 1;
    public static final int DEVICE_TYPE_TABLET = 2;
    public static final int DEVICE_TYPE_UNKNOWN = 0;
    public static final int DEVICE_TYPE_WATCH = 4;
    private final int mBatteryPercentage;
    private final int mConnectionStrength;
    private final java.lang.String mDeviceName;
    private final int mDeviceType;
    private final android.os.Bundle mExtras;
    private final boolean mIsBatteryCharging;
    private final java.lang.String mModelName;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DeviceType {
    }

    public static final class Builder {
        private int mBatteryPercentage;
        private int mConnectionStrength;
        private java.lang.String mDeviceName;
        private int mDeviceType;
        private android.os.Bundle mExtras = android.os.Bundle.EMPTY;
        private boolean mIsBatteryCharging;
        private java.lang.String mModelName;

        public Builder(java.lang.String str, java.lang.String str2) {
            java.util.Objects.requireNonNull(str);
            java.util.Objects.requireNonNull(str2);
            this.mDeviceName = str;
            this.mModelName = str2;
        }

        public android.net.wifi.sharedconnectivity.app.NetworkProviderInfo.Builder setDeviceType(int i) {
            this.mDeviceType = i;
            return this;
        }

        public android.net.wifi.sharedconnectivity.app.NetworkProviderInfo.Builder setDeviceName(java.lang.String str) {
            java.util.Objects.requireNonNull(str);
            this.mDeviceName = str;
            return this;
        }

        public android.net.wifi.sharedconnectivity.app.NetworkProviderInfo.Builder setModelName(java.lang.String str) {
            java.util.Objects.requireNonNull(str);
            this.mModelName = str;
            return this;
        }

        public android.net.wifi.sharedconnectivity.app.NetworkProviderInfo.Builder setBatteryPercentage(int i) {
            this.mBatteryPercentage = i;
            return this;
        }

        public android.net.wifi.sharedconnectivity.app.NetworkProviderInfo.Builder setBatteryCharging(boolean z) {
            this.mIsBatteryCharging = z;
            return this;
        }

        public android.net.wifi.sharedconnectivity.app.NetworkProviderInfo.Builder setConnectionStrength(int i) {
            this.mConnectionStrength = i;
            return this;
        }

        public android.net.wifi.sharedconnectivity.app.NetworkProviderInfo.Builder setExtras(android.os.Bundle bundle) {
            java.util.Objects.requireNonNull(bundle);
            this.mExtras = bundle;
            return this;
        }

        public android.net.wifi.sharedconnectivity.app.NetworkProviderInfo build() {
            return new android.net.wifi.sharedconnectivity.app.NetworkProviderInfo(this.mDeviceType, this.mDeviceName, this.mModelName, this.mBatteryPercentage, this.mIsBatteryCharging, this.mConnectionStrength, this.mExtras);
        }
    }

    private static void validate(int i, java.lang.String str, java.lang.String str2, int i2, int i3) {
        if (i != 0 && i != 1 && i != 2 && i != 3 && i != 4 && i != 5) {
            throw new java.lang.IllegalArgumentException("Illegal device type");
        }
        if (i2 < 0 || i2 > 100) {
            throw new java.lang.IllegalArgumentException("BatteryPercentage must be in range 0-100");
        }
        if (i3 < 0 || i3 > 4) {
            throw new java.lang.IllegalArgumentException("ConnectionStrength must be in range 0-4");
        }
    }

    private NetworkProviderInfo(int i, java.lang.String str, java.lang.String str2, int i2, boolean z, int i3, android.os.Bundle bundle) {
        validate(i, str, str2, i2, i3);
        this.mDeviceType = i;
        this.mDeviceName = str;
        this.mModelName = str2;
        this.mBatteryPercentage = i2;
        this.mIsBatteryCharging = z;
        this.mConnectionStrength = i3;
        this.mExtras = bundle;
    }

    public int getDeviceType() {
        return this.mDeviceType;
    }

    public java.lang.String getDeviceName() {
        return this.mDeviceName;
    }

    public java.lang.String getModelName() {
        return this.mModelName;
    }

    public int getBatteryPercentage() {
        return this.mBatteryPercentage;
    }

    public boolean isBatteryCharging() {
        return this.mIsBatteryCharging;
    }

    public int getConnectionStrength() {
        return this.mConnectionStrength;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.net.wifi.sharedconnectivity.app.NetworkProviderInfo)) {
            return false;
        }
        android.net.wifi.sharedconnectivity.app.NetworkProviderInfo networkProviderInfo = (android.net.wifi.sharedconnectivity.app.NetworkProviderInfo) obj;
        return this.mDeviceType == networkProviderInfo.getDeviceType() && java.util.Objects.equals(this.mDeviceName, networkProviderInfo.mDeviceName) && java.util.Objects.equals(this.mModelName, networkProviderInfo.mModelName) && this.mBatteryPercentage == networkProviderInfo.mBatteryPercentage && this.mIsBatteryCharging == networkProviderInfo.mIsBatteryCharging && this.mConnectionStrength == networkProviderInfo.mConnectionStrength;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mDeviceType), this.mDeviceName, this.mModelName, java.lang.Integer.valueOf(this.mBatteryPercentage), java.lang.Boolean.valueOf(this.mIsBatteryCharging), java.lang.Integer.valueOf(this.mConnectionStrength));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mDeviceType);
        parcel.writeString(this.mDeviceName);
        parcel.writeString(this.mModelName);
        parcel.writeInt(this.mBatteryPercentage);
        parcel.writeBoolean(this.mIsBatteryCharging);
        parcel.writeInt(this.mConnectionStrength);
        parcel.writeBundle(this.mExtras);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static android.net.wifi.sharedconnectivity.app.NetworkProviderInfo readFromParcel(android.os.Parcel parcel) {
        return new android.net.wifi.sharedconnectivity.app.NetworkProviderInfo(parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readBoolean(), parcel.readInt(), parcel.readBundle());
    }

    public java.lang.String toString() {
        return "NetworkProviderInfo[deviceType=" + this.mDeviceType + ", deviceName=" + this.mDeviceName + ", modelName=" + this.mModelName + ", batteryPercentage=" + this.mBatteryPercentage + ", isBatteryCharging=" + this.mIsBatteryCharging + ", connectionStrength=" + this.mConnectionStrength + ", extras=" + this.mExtras.toString() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }
}
