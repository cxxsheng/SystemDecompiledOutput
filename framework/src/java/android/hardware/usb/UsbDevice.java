package android.hardware.usb;

/* loaded from: classes2.dex */
public class UsbDevice implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.usb.UsbDevice> CREATOR = new android.os.Parcelable.Creator<android.hardware.usb.UsbDevice>() { // from class: android.hardware.usb.UsbDevice.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.usb.UsbDevice createFromParcel(android.os.Parcel parcel) {
            java.lang.String readString = parcel.readString();
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            int readInt4 = parcel.readInt();
            int readInt5 = parcel.readInt();
            java.lang.String readString2 = parcel.readString();
            java.lang.String readString3 = parcel.readString();
            java.lang.String readString4 = parcel.readString();
            android.hardware.usb.IUsbSerialReader asInterface = android.hardware.usb.IUsbSerialReader.Stub.asInterface(parcel.readStrongBinder());
            return new android.hardware.usb.UsbDevice(readString, readInt, readInt2, readInt3, readInt4, readInt5, readString2, readString3, readString4, (android.hardware.usb.UsbConfiguration[]) parcel.readParcelableArray(android.hardware.usb.UsbConfiguration.class.getClassLoader(), android.hardware.usb.UsbConfiguration.class), asInterface, parcel.readInt() == 1, parcel.readInt() == 1, parcel.readInt() == 1, parcel.readInt() == 1, parcel.readInt() == 1);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.usb.UsbDevice[] newArray(int i) {
            return new android.hardware.usb.UsbDevice[i];
        }
    };
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "UsbDevice";
    private final int mClass;
    private final android.hardware.usb.UsbConfiguration[] mConfigurations;
    private final boolean mHasAudioCapture;
    private final boolean mHasAudioPlayback;
    private final boolean mHasMidi;
    private final boolean mHasVideoCapture;
    private final boolean mHasVideoPlayback;
    private android.hardware.usb.UsbInterface[] mInterfaces;
    private final java.lang.String mManufacturerName;
    private final java.lang.String mName;
    private final int mProductId;
    private final java.lang.String mProductName;
    private final int mProtocol;
    private final android.hardware.usb.IUsbSerialReader mSerialNumberReader;
    private final int mSubclass;
    private final int mVendorId;
    private final java.lang.String mVersion;

    private static native int native_get_device_id(java.lang.String str);

    private static native java.lang.String native_get_device_name(int i);

    private UsbDevice(java.lang.String str, int i, int i2, int i3, int i4, int i5, java.lang.String str2, java.lang.String str3, java.lang.String str4, android.hardware.usb.UsbConfiguration[] usbConfigurationArr, android.hardware.usb.IUsbSerialReader iUsbSerialReader, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        this.mName = (java.lang.String) java.util.Objects.requireNonNull(str);
        this.mVendorId = i;
        this.mProductId = i2;
        this.mClass = i3;
        this.mSubclass = i4;
        this.mProtocol = i5;
        this.mManufacturerName = str2;
        this.mProductName = str3;
        this.mVersion = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str4);
        this.mConfigurations = (android.hardware.usb.UsbConfiguration[]) com.android.internal.util.Preconditions.checkArrayElementsNotNull(usbConfigurationArr, "configurations");
        this.mSerialNumberReader = (android.hardware.usb.IUsbSerialReader) java.util.Objects.requireNonNull(iUsbSerialReader);
        this.mHasAudioPlayback = z;
        this.mHasAudioCapture = z2;
        this.mHasMidi = z3;
        this.mHasVideoPlayback = z4;
        this.mHasVideoCapture = z5;
        if (android.app.ActivityThread.isSystem()) {
            com.android.internal.util.Preconditions.checkArgument(this.mSerialNumberReader instanceof android.hardware.usb.IUsbSerialReader.Stub);
        }
    }

    public java.lang.String getDeviceName() {
        return this.mName;
    }

    public java.lang.String getManufacturerName() {
        return this.mManufacturerName;
    }

    public java.lang.String getProductName() {
        return this.mProductName;
    }

    public java.lang.String getVersion() {
        return this.mVersion;
    }

    public java.lang.String getSerialNumber() {
        try {
            return this.mSerialNumberReader.getSerial(android.app.ActivityThread.currentPackageName());
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }

    public int getDeviceId() {
        return getDeviceId(this.mName);
    }

    public int getVendorId() {
        return this.mVendorId;
    }

    public int getProductId() {
        return this.mProductId;
    }

    public int getDeviceClass() {
        return this.mClass;
    }

    public int getDeviceSubclass() {
        return this.mSubclass;
    }

    public int getDeviceProtocol() {
        return this.mProtocol;
    }

    public int getConfigurationCount() {
        return this.mConfigurations.length;
    }

    public boolean getHasAudioPlayback() {
        return this.mHasAudioPlayback;
    }

    public boolean getHasAudioCapture() {
        return this.mHasAudioCapture;
    }

    public boolean getHasMidi() {
        return this.mHasMidi;
    }

    public boolean getHasVideoPlayback() {
        return this.mHasVideoPlayback;
    }

    public boolean getHasVideoCapture() {
        return this.mHasVideoCapture;
    }

    public android.hardware.usb.UsbConfiguration getConfiguration(int i) {
        return this.mConfigurations[i];
    }

    private android.hardware.usb.UsbInterface[] getInterfaceList() {
        if (this.mInterfaces == null) {
            int length = this.mConfigurations.length;
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                i += this.mConfigurations[i2].getInterfaceCount();
            }
            this.mInterfaces = new android.hardware.usb.UsbInterface[i];
            int i3 = 0;
            for (int i4 = 0; i4 < length; i4++) {
                android.hardware.usb.UsbConfiguration usbConfiguration = this.mConfigurations[i4];
                int interfaceCount = usbConfiguration.getInterfaceCount();
                int i5 = 0;
                while (i5 < interfaceCount) {
                    this.mInterfaces[i3] = usbConfiguration.getInterface(i5);
                    i5++;
                    i3++;
                }
            }
        }
        return this.mInterfaces;
    }

    public int getInterfaceCount() {
        return getInterfaceList().length;
    }

    public android.hardware.usb.UsbInterface getInterface(int i) {
        return getInterfaceList()[i];
    }

    public boolean equals(java.lang.Object obj) {
        if (obj instanceof android.hardware.usb.UsbDevice) {
            return ((android.hardware.usb.UsbDevice) obj).mName.equals(this.mName);
        }
        if (obj instanceof java.lang.String) {
            return ((java.lang.String) obj).equals(this.mName);
        }
        return false;
    }

    public int hashCode() {
        return this.mName.hashCode();
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("UsbDevice[mName=" + this.mName + ",mVendorId=" + this.mVendorId + ",mProductId=" + this.mProductId + ",mClass=" + this.mClass + ",mSubclass=" + this.mSubclass + ",mProtocol=" + this.mProtocol + ",mManufacturerName=" + this.mManufacturerName + ",mProductName=" + this.mProductName + ",mVersion=" + this.mVersion + ",mSerialNumberReader=" + this.mSerialNumberReader + ", mHasAudioPlayback=" + this.mHasAudioPlayback + ", mHasAudioCapture=" + this.mHasAudioCapture + ", mHasMidi=" + this.mHasMidi + ", mHasVideoCapture=" + this.mHasVideoCapture + ", mHasVideoPlayback=" + this.mHasVideoPlayback + ", mConfigurations=[");
        for (int i = 0; i < this.mConfigurations.length; i++) {
            sb.append("\n");
            sb.append(this.mConfigurations[i].toString());
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mName);
        parcel.writeInt(this.mVendorId);
        parcel.writeInt(this.mProductId);
        parcel.writeInt(this.mClass);
        parcel.writeInt(this.mSubclass);
        parcel.writeInt(this.mProtocol);
        parcel.writeString(this.mManufacturerName);
        parcel.writeString(this.mProductName);
        parcel.writeString(this.mVersion);
        parcel.writeStrongBinder(this.mSerialNumberReader.asBinder());
        parcel.writeParcelableArray(this.mConfigurations, 0);
        parcel.writeInt(this.mHasAudioPlayback ? 1 : 0);
        parcel.writeInt(this.mHasAudioCapture ? 1 : 0);
        parcel.writeInt(this.mHasMidi ? 1 : 0);
        parcel.writeInt(this.mHasVideoPlayback ? 1 : 0);
        parcel.writeInt(this.mHasVideoCapture ? 1 : 0);
    }

    public static int getDeviceId(java.lang.String str) {
        return native_get_device_id(str);
    }

    public static java.lang.String getDeviceName(int i) {
        return native_get_device_name(i);
    }

    public static class Builder {
        private final int mClass;
        private final android.hardware.usb.UsbConfiguration[] mConfigurations;
        private final boolean mHasAudioCapture;
        private final boolean mHasAudioPlayback;
        private final boolean mHasMidi;
        private final boolean mHasVideoCapture;
        private final boolean mHasVideoPlayback;
        private final java.lang.String mManufacturerName;
        private final java.lang.String mName;
        private final int mProductId;
        private final java.lang.String mProductName;
        private final int mProtocol;
        private final int mSubclass;
        private final int mVendorId;
        private final java.lang.String mVersion;
        public final java.lang.String serialNumber;

        public Builder(java.lang.String str, int i, int i2, int i3, int i4, int i5, java.lang.String str2, java.lang.String str3, java.lang.String str4, android.hardware.usb.UsbConfiguration[] usbConfigurationArr, java.lang.String str5, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
            this.mName = (java.lang.String) java.util.Objects.requireNonNull(str);
            this.mVendorId = i;
            this.mProductId = i2;
            this.mClass = i3;
            this.mSubclass = i4;
            this.mProtocol = i5;
            this.mManufacturerName = str2;
            this.mProductName = str3;
            this.mVersion = (java.lang.String) com.android.internal.util.Preconditions.checkStringNotEmpty(str4);
            this.mConfigurations = usbConfigurationArr;
            this.serialNumber = str5;
            this.mHasAudioPlayback = z;
            this.mHasAudioCapture = z2;
            this.mHasMidi = z3;
            this.mHasVideoPlayback = z4;
            this.mHasVideoCapture = z5;
        }

        public android.hardware.usb.UsbDevice build(android.hardware.usb.IUsbSerialReader iUsbSerialReader) {
            return new android.hardware.usb.UsbDevice(this.mName, this.mVendorId, this.mProductId, this.mClass, this.mSubclass, this.mProtocol, this.mManufacturerName, this.mProductName, this.mVersion, this.mConfigurations, iUsbSerialReader, this.mHasAudioPlayback, this.mHasAudioCapture, this.mHasMidi, this.mHasVideoPlayback, this.mHasVideoCapture);
        }
    }
}
