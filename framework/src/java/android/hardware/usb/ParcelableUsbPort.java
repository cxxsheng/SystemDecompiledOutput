package android.hardware.usb;

/* loaded from: classes2.dex */
public final class ParcelableUsbPort implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.hardware.usb.ParcelableUsbPort> CREATOR = new android.os.Parcelable.Creator<android.hardware.usb.ParcelableUsbPort>() { // from class: android.hardware.usb.ParcelableUsbPort.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.usb.ParcelableUsbPort createFromParcel(android.os.Parcel parcel) {
            return new android.hardware.usb.ParcelableUsbPort(parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean(), parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.usb.ParcelableUsbPort[] newArray(int i) {
            return new android.hardware.usb.ParcelableUsbPort[i];
        }
    };
    private final java.lang.String mId;
    private final int mSupportedAltModesMask;
    private final int mSupportedContaminantProtectionModes;
    private final int mSupportedModes;
    private final boolean mSupportsComplianceWarnings;
    private final boolean mSupportsEnableContaminantPresenceDetection;
    private final boolean mSupportsEnableContaminantPresenceProtection;

    private ParcelableUsbPort(java.lang.String str, int i, int i2, boolean z, boolean z2, boolean z3, int i3) {
        this.mId = str;
        this.mSupportedModes = i;
        this.mSupportedContaminantProtectionModes = i2;
        this.mSupportsEnableContaminantPresenceProtection = z;
        this.mSupportsEnableContaminantPresenceDetection = z2;
        this.mSupportsComplianceWarnings = z3;
        this.mSupportedAltModesMask = i3;
    }

    public static android.hardware.usb.ParcelableUsbPort of(android.hardware.usb.UsbPort usbPort) {
        return new android.hardware.usb.ParcelableUsbPort(usbPort.getId(), usbPort.getSupportedModes(), usbPort.getSupportedContaminantProtectionModes(), usbPort.supportsEnableContaminantPresenceProtection(), usbPort.supportsEnableContaminantPresenceDetection(), usbPort.supportsComplianceWarnings(), usbPort.getSupportedAltModesMask());
    }

    public android.hardware.usb.UsbPort getUsbPort(android.hardware.usb.UsbManager usbManager) {
        return new android.hardware.usb.UsbPort(usbManager, this.mId, this.mSupportedModes, this.mSupportedContaminantProtectionModes, this.mSupportsEnableContaminantPresenceProtection, this.mSupportsEnableContaminantPresenceDetection, this.mSupportsComplianceWarnings, this.mSupportedAltModesMask);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mId);
        parcel.writeInt(this.mSupportedModes);
        parcel.writeInt(this.mSupportedContaminantProtectionModes);
        parcel.writeBoolean(this.mSupportsEnableContaminantPresenceProtection);
        parcel.writeBoolean(this.mSupportsEnableContaminantPresenceDetection);
        parcel.writeBoolean(this.mSupportsComplianceWarnings);
        parcel.writeInt(this.mSupportedAltModesMask);
    }
}
