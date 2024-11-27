package android.companion;

/* loaded from: classes.dex */
public final class AssociatedDevice implements android.os.Parcelable {
    private static final int BLUETOOTH_LE = 1;
    private static final int CLASSIC_BLUETOOTH = 0;
    public static final android.os.Parcelable.Creator<android.companion.AssociatedDevice> CREATOR = new android.os.Parcelable.Creator<android.companion.AssociatedDevice>() { // from class: android.companion.AssociatedDevice.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.companion.AssociatedDevice[] newArray(int i) {
            return new android.companion.AssociatedDevice[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.companion.AssociatedDevice createFromParcel(android.os.Parcel parcel) {
            return new android.companion.AssociatedDevice(parcel);
        }
    };
    private static final int WIFI = 2;
    private final android.os.Parcelable mDevice;

    public AssociatedDevice(android.os.Parcelable parcelable) {
        this.mDevice = parcelable;
    }

    private AssociatedDevice(android.os.Parcel parcel) {
        this.mDevice = getDeviceCreator(parcel.readInt()).createFromParcel(parcel);
    }

    public android.bluetooth.BluetoothDevice getBluetoothDevice() {
        if (this.mDevice instanceof android.bluetooth.BluetoothDevice) {
            return (android.bluetooth.BluetoothDevice) this.mDevice;
        }
        return null;
    }

    public android.bluetooth.le.ScanResult getBleDevice() {
        if (this.mDevice instanceof android.bluetooth.le.ScanResult) {
            return (android.bluetooth.le.ScanResult) this.mDevice;
        }
        return null;
    }

    public android.net.wifi.ScanResult getWifiDevice() {
        if (this.mDevice instanceof android.net.wifi.ScanResult) {
            return (android.net.wifi.ScanResult) this.mDevice;
        }
        return null;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(getDeviceType());
        this.mDevice.writeToParcel(parcel, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private int getDeviceType() {
        if (this.mDevice instanceof android.bluetooth.BluetoothDevice) {
            return 0;
        }
        if (this.mDevice instanceof android.bluetooth.le.ScanResult) {
            return 1;
        }
        if (this.mDevice instanceof android.net.wifi.ScanResult) {
            return 2;
        }
        throw new java.lang.UnsupportedOperationException("Unsupported device type.");
    }

    private static android.os.Parcelable.Creator<? extends android.os.Parcelable> getDeviceCreator(int i) {
        switch (i) {
            case 0:
                return android.bluetooth.BluetoothDevice.CREATOR;
            case 1:
                return android.bluetooth.le.ScanResult.CREATOR;
            case 2:
                return android.net.wifi.ScanResult.CREATOR;
            default:
                throw new java.lang.UnsupportedOperationException("Unsupported device type.");
        }
    }

    public java.lang.String toString() {
        return "AssociatedDevice { device = " + this.mDevice + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.companion.AssociatedDevice associatedDevice = (android.companion.AssociatedDevice) obj;
        if (getDeviceType() != associatedDevice.getDeviceType()) {
            return false;
        }
        if ((this.mDevice instanceof android.bluetooth.le.ScanResult) || (this.mDevice instanceof android.net.wifi.ScanResult)) {
            return this.mDevice.toString().equals(associatedDevice.mDevice.toString());
        }
        return java.util.Objects.equals(this.mDevice, associatedDevice.mDevice);
    }

    public int hashCode() {
        return java.util.Objects.hash(this.mDevice);
    }
}
