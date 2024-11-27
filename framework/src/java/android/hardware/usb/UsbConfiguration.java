package android.hardware.usb;

/* loaded from: classes2.dex */
public class UsbConfiguration implements android.os.Parcelable {
    private static final int ATTR_REMOTE_WAKEUP = 32;
    private static final int ATTR_SELF_POWERED = 64;
    public static final android.os.Parcelable.Creator<android.hardware.usb.UsbConfiguration> CREATOR = new android.os.Parcelable.Creator<android.hardware.usb.UsbConfiguration>() { // from class: android.hardware.usb.UsbConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.usb.UsbConfiguration createFromParcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            java.lang.String readString = parcel.readString();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            android.os.Parcelable[] parcelableArr = (android.os.Parcelable[]) parcel.readParcelableArray(android.hardware.usb.UsbInterface.class.getClassLoader(), android.hardware.usb.UsbInterface.class);
            android.hardware.usb.UsbConfiguration usbConfiguration = new android.hardware.usb.UsbConfiguration(readInt, readString, readInt2, readInt3);
            usbConfiguration.setInterfaces(parcelableArr);
            return usbConfiguration;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.usb.UsbConfiguration[] newArray(int i) {
            return new android.hardware.usb.UsbConfiguration[i];
        }
    };
    private final int mAttributes;
    private final int mId;
    private android.os.Parcelable[] mInterfaces;
    private final int mMaxPower;
    private final java.lang.String mName;

    public UsbConfiguration(int i, java.lang.String str, int i2, int i3) {
        this.mId = i;
        this.mName = str;
        this.mAttributes = i2;
        this.mMaxPower = i3;
    }

    public int getId() {
        return this.mId;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public boolean isSelfPowered() {
        return (this.mAttributes & 64) != 0;
    }

    public boolean isRemoteWakeup() {
        return (this.mAttributes & 32) != 0;
    }

    public int getAttributes() {
        return this.mAttributes;
    }

    public int getMaxPower() {
        return this.mMaxPower * 2;
    }

    public int getInterfaceCount() {
        return this.mInterfaces.length;
    }

    public android.hardware.usb.UsbInterface getInterface(int i) {
        return (android.hardware.usb.UsbInterface) this.mInterfaces[i];
    }

    public void setInterfaces(android.os.Parcelable[] parcelableArr) {
        this.mInterfaces = (android.os.Parcelable[]) com.android.internal.util.Preconditions.checkArrayElementsNotNull(parcelableArr, "interfaces");
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("UsbConfiguration[mId=" + this.mId + ",mName=" + this.mName + ",mAttributes=" + this.mAttributes + ",mMaxPower=" + this.mMaxPower + ",mInterfaces=[");
        for (int i = 0; i < this.mInterfaces.length; i++) {
            sb.append("\n");
            sb.append(this.mInterfaces[i].toString());
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
        parcel.writeInt(this.mId);
        parcel.writeString(this.mName);
        parcel.writeInt(this.mAttributes);
        parcel.writeInt(this.mMaxPower);
        parcel.writeParcelableArray(this.mInterfaces, 0);
    }
}
