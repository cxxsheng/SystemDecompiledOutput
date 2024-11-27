package android.hardware.usb;

/* loaded from: classes2.dex */
public class UsbAccessory implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.usb.UsbAccessory> CREATOR = new android.os.Parcelable.Creator<android.hardware.usb.UsbAccessory>() { // from class: android.hardware.usb.UsbAccessory.2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.usb.UsbAccessory createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.usb.UsbAccessory(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), android.hardware.usb.IUsbSerialReader.Stub.asInterface(parcel.readStrongBinder()));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.usb.UsbAccessory[] newArray(int i) {
            return new android.hardware.usb.UsbAccessory[i];
        }
    };
    public static final int DESCRIPTION_STRING = 2;
    public static final int MANUFACTURER_STRING = 0;
    public static final int MODEL_STRING = 1;
    public static final int SERIAL_STRING = 5;
    private static final java.lang.String TAG = "UsbAccessory";
    public static final int URI_STRING = 4;
    public static final int VERSION_STRING = 3;
    private final java.lang.String mDescription;
    private final java.lang.String mManufacturer;
    private final java.lang.String mModel;
    private final android.hardware.usb.IUsbSerialReader mSerialNumberReader;
    private final java.lang.String mUri;
    private final java.lang.String mVersion;

    public UsbAccessory(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, android.hardware.usb.IUsbSerialReader iUsbSerialReader) {
        this.mManufacturer = (java.lang.String) java.util.Objects.requireNonNull(str);
        this.mModel = (java.lang.String) java.util.Objects.requireNonNull(str2);
        this.mDescription = str3;
        this.mVersion = str4;
        this.mUri = str5;
        this.mSerialNumberReader = iUsbSerialReader;
        if (android.app.ActivityThread.isSystem()) {
            com.android.internal.util.Preconditions.checkArgument(this.mSerialNumberReader instanceof android.hardware.usb.IUsbSerialReader.Stub);
        }
    }

    @java.lang.Deprecated
    public UsbAccessory(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5, final java.lang.String str6) {
        this(str, str2, str3, str4, str5, new android.hardware.usb.IUsbSerialReader.Stub() { // from class: android.hardware.usb.UsbAccessory.1
            @Override // android.hardware.usb.IUsbSerialReader
            public java.lang.String getSerial(java.lang.String str7) {
                return str6;
            }
        });
    }

    public java.lang.String getManufacturer() {
        return this.mManufacturer;
    }

    public java.lang.String getModel() {
        return this.mModel;
    }

    public java.lang.String getDescription() {
        return this.mDescription;
    }

    public java.lang.String getVersion() {
        return this.mVersion;
    }

    public java.lang.String getUri() {
        return this.mUri;
    }

    public java.lang.String getSerial() {
        try {
            return this.mSerialNumberReader.getSerial(android.app.ActivityThread.currentPackageName());
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }

    private static boolean compare(java.lang.String str, java.lang.String str2) {
        if (str == null) {
            return str2 == null;
        }
        return str.equals(str2);
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.hardware.usb.UsbAccessory)) {
            return false;
        }
        android.hardware.usb.UsbAccessory usbAccessory = (android.hardware.usb.UsbAccessory) obj;
        return compare(this.mManufacturer, usbAccessory.getManufacturer()) && compare(this.mModel, usbAccessory.getModel()) && compare(this.mDescription, usbAccessory.getDescription()) && compare(this.mVersion, usbAccessory.getVersion()) && compare(this.mUri, usbAccessory.getUri()) && compare(getSerial(), usbAccessory.getSerial());
    }

    public int hashCode() {
        return (((this.mManufacturer.hashCode() ^ this.mModel.hashCode()) ^ (this.mDescription == null ? 0 : this.mDescription.hashCode())) ^ (this.mVersion == null ? 0 : this.mVersion.hashCode())) ^ (this.mUri != null ? this.mUri.hashCode() : 0);
    }

    public java.lang.String toString() {
        return "UsbAccessory[mManufacturer=" + this.mManufacturer + ", mModel=" + this.mModel + ", mDescription=" + this.mDescription + ", mVersion=" + this.mVersion + ", mUri=" + this.mUri + ", mSerialNumberReader=" + this.mSerialNumberReader + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mManufacturer);
        parcel.writeString(this.mModel);
        parcel.writeString(this.mDescription);
        parcel.writeString(this.mVersion);
        parcel.writeString(this.mUri);
        parcel.writeStrongBinder(this.mSerialNumberReader.asBinder());
    }
}
