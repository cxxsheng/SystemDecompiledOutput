package android.hardware.usb;

/* loaded from: classes2.dex */
public class UsbInterface implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.usb.UsbInterface> CREATOR = new android.os.Parcelable.Creator<android.hardware.usb.UsbInterface>() { // from class: android.hardware.usb.UsbInterface.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.usb.UsbInterface createFromParcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            java.lang.String readString = parcel.readString();
            int readInt3 = parcel.readInt();
            int readInt4 = parcel.readInt();
            int readInt5 = parcel.readInt();
            android.os.Parcelable[] readParcelableArray = parcel.readParcelableArray(android.hardware.usb.UsbEndpoint.class.getClassLoader());
            android.hardware.usb.UsbInterface usbInterface = new android.hardware.usb.UsbInterface(readInt, readInt2, readString, readInt3, readInt4, readInt5);
            usbInterface.setEndpoints(readParcelableArray);
            return usbInterface;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.usb.UsbInterface[] newArray(int i) {
            return new android.hardware.usb.UsbInterface[i];
        }
    };
    private final int mAlternateSetting;
    private final int mClass;
    private android.os.Parcelable[] mEndpoints;
    private final int mId;
    private final java.lang.String mName;
    private final int mProtocol;
    private final int mSubclass;

    public UsbInterface(int i, int i2, java.lang.String str, int i3, int i4, int i5) {
        this.mId = i;
        this.mAlternateSetting = i2;
        this.mName = str;
        this.mClass = i3;
        this.mSubclass = i4;
        this.mProtocol = i5;
    }

    public int getId() {
        return this.mId;
    }

    public int getAlternateSetting() {
        return this.mAlternateSetting;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public int getInterfaceClass() {
        return this.mClass;
    }

    public int getInterfaceSubclass() {
        return this.mSubclass;
    }

    public int getInterfaceProtocol() {
        return this.mProtocol;
    }

    public int getEndpointCount() {
        return this.mEndpoints.length;
    }

    public android.hardware.usb.UsbEndpoint getEndpoint(int i) {
        return (android.hardware.usb.UsbEndpoint) this.mEndpoints[i];
    }

    public void setEndpoints(android.os.Parcelable[] parcelableArr) {
        this.mEndpoints = (android.os.Parcelable[]) com.android.internal.util.Preconditions.checkArrayElementsNotNull(parcelableArr, "endpoints");
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("UsbInterface[mId=" + this.mId + ",mAlternateSetting=" + this.mAlternateSetting + ",mName=" + this.mName + ",mClass=" + this.mClass + ",mSubclass=" + this.mSubclass + ",mProtocol=" + this.mProtocol + ",mEndpoints=[");
        for (int i = 0; i < this.mEndpoints.length; i++) {
            sb.append("\n");
            sb.append(this.mEndpoints[i].toString());
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
        parcel.writeInt(this.mAlternateSetting);
        parcel.writeString(this.mName);
        parcel.writeInt(this.mClass);
        parcel.writeInt(this.mSubclass);
        parcel.writeInt(this.mProtocol);
        parcel.writeParcelableArray(this.mEndpoints, 0);
    }
}
