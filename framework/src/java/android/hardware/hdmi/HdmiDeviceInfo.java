package android.hardware.hdmi;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class HdmiDeviceInfo implements android.os.Parcelable {
    public static final int ADDR_INTERNAL = 0;
    public static final int ADDR_INVALID = -1;
    public static final int DEVICE_AUDIO_SYSTEM = 5;
    public static final int DEVICE_INACTIVE = -1;
    public static final int DEVICE_PLAYBACK = 4;
    public static final int DEVICE_PURE_CEC_SWITCH = 6;
    public static final int DEVICE_RECORDER = 1;
    public static final int DEVICE_RESERVED = 2;
    public static final int DEVICE_TUNER = 3;
    public static final int DEVICE_TV = 0;
    public static final int DEVICE_VIDEO_PROCESSOR = 7;
    private static final int HDMI_DEVICE_TYPE_CEC = 0;
    private static final int HDMI_DEVICE_TYPE_HARDWARE = 2;
    private static final int HDMI_DEVICE_TYPE_INACTIVE = 100;
    private static final int HDMI_DEVICE_TYPE_MHL = 1;
    public static final int ID_INVALID = 65535;
    private static final int ID_OFFSET_CEC = 0;
    private static final int ID_OFFSET_HARDWARE = 192;
    private static final int ID_OFFSET_MHL = 128;
    public static final int PATH_INTERNAL = 0;
    public static final int PATH_INVALID = 65535;
    public static final int PORT_INVALID = -1;
    public static final int VENDOR_ID_UNKNOWN = 16777215;
    private final int mAdopterId;
    private final int mCecVersion;
    private final android.hardware.hdmi.DeviceFeatures mDeviceFeatures;
    private final int mDeviceId;
    private final int mDevicePowerStatus;
    private final int mDeviceType;
    private final java.lang.String mDisplayName;
    private final int mHdmiDeviceType;
    private final int mId;
    private final int mLogicalAddress;
    private final int mPhysicalAddress;
    private final int mPortId;
    private final int mVendorId;
    public static final android.hardware.hdmi.HdmiDeviceInfo INACTIVE_DEVICE = new android.hardware.hdmi.HdmiDeviceInfo();
    public static final android.os.Parcelable.Creator<android.hardware.hdmi.HdmiDeviceInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.hdmi.HdmiDeviceInfo>() { // from class: android.hardware.hdmi.HdmiDeviceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.hdmi.HdmiDeviceInfo createFromParcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            switch (readInt) {
                case 0:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    return android.hardware.hdmi.HdmiDeviceInfo.cecDeviceBuilder().setLogicalAddress(readInt4).setPhysicalAddress(readInt2).setPortId(readInt3).setDeviceType(readInt5).setVendorId(readInt6).setDisplayName(readString).setDevicePowerStatus(readInt7).setCecVersion(parcel.readInt()).build();
                case 1:
                    return android.hardware.hdmi.HdmiDeviceInfo.mhlDevice(readInt2, readInt3, parcel.readInt(), parcel.readInt());
                case 2:
                    return android.hardware.hdmi.HdmiDeviceInfo.hardwarePort(readInt2, readInt3);
                case 100:
                    return android.hardware.hdmi.HdmiDeviceInfo.INACTIVE_DEVICE;
                default:
                    return null;
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.hdmi.HdmiDeviceInfo[] newArray(int i) {
            return new android.hardware.hdmi.HdmiDeviceInfo[i];
        }
    };

    @java.lang.Deprecated
    public HdmiDeviceInfo() {
        this.mHdmiDeviceType = 100;
        this.mPhysicalAddress = 65535;
        this.mId = 65535;
        this.mLogicalAddress = -1;
        this.mDeviceType = -1;
        this.mCecVersion = 5;
        this.mPortId = -1;
        this.mDevicePowerStatus = -1;
        this.mDisplayName = "Inactive";
        this.mVendorId = 0;
        this.mDeviceFeatures = android.hardware.hdmi.DeviceFeatures.ALL_FEATURES_SUPPORT_UNKNOWN;
        this.mDeviceId = -1;
        this.mAdopterId = -1;
    }

    public android.hardware.hdmi.HdmiDeviceInfo.Builder toBuilder() {
        return new android.hardware.hdmi.HdmiDeviceInfo.Builder();
    }

    private HdmiDeviceInfo(android.hardware.hdmi.HdmiDeviceInfo.Builder builder) {
        this.mHdmiDeviceType = builder.mHdmiDeviceType;
        this.mPhysicalAddress = builder.mPhysicalAddress;
        this.mPortId = builder.mPortId;
        this.mLogicalAddress = builder.mLogicalAddress;
        this.mDeviceType = builder.mDeviceType;
        this.mCecVersion = builder.mCecVersion;
        this.mVendorId = builder.mVendorId;
        this.mDisplayName = builder.mDisplayName;
        this.mDevicePowerStatus = builder.mDevicePowerStatus;
        this.mDeviceFeatures = builder.mDeviceFeatures;
        this.mDeviceId = builder.mDeviceId;
        this.mAdopterId = builder.mAdopterId;
        switch (this.mHdmiDeviceType) {
            case 0:
                this.mId = idForCecDevice(this.mLogicalAddress);
                break;
            case 1:
                this.mId = idForMhlDevice(this.mPortId);
                break;
            case 2:
                this.mId = idForHardware(this.mPortId);
                break;
            default:
                this.mId = 65535;
                break;
        }
    }

    public static android.hardware.hdmi.HdmiDeviceInfo.Builder cecDeviceBuilder() {
        return new android.hardware.hdmi.HdmiDeviceInfo.Builder(0);
    }

    public static android.hardware.hdmi.HdmiDeviceInfo mhlDevice(int i, int i2, int i3, int i4) {
        return new android.hardware.hdmi.HdmiDeviceInfo.Builder(1).setPhysicalAddress(i).setPortId(i2).setVendorId(0).setDisplayName("Mobile").setDeviceId(i3).setAdopterId(i4).build();
    }

    public static android.hardware.hdmi.HdmiDeviceInfo hardwarePort(int i, int i2) {
        return new android.hardware.hdmi.HdmiDeviceInfo.Builder(2).setPhysicalAddress(i).setPortId(i2).setVendorId(0).setDisplayName("HDMI" + i2).build();
    }

    public int getId() {
        return this.mId;
    }

    public android.hardware.hdmi.DeviceFeatures getDeviceFeatures() {
        return this.mDeviceFeatures;
    }

    public static int idForCecDevice(int i) {
        return i + 0;
    }

    public static int idForMhlDevice(int i) {
        return i + 128;
    }

    public static int idForHardware(int i) {
        return i + 192;
    }

    public int getLogicalAddress() {
        return this.mLogicalAddress;
    }

    public int getPhysicalAddress() {
        return this.mPhysicalAddress;
    }

    public int getPortId() {
        return this.mPortId;
    }

    public int getDeviceType() {
        return this.mDeviceType;
    }

    public int getCecVersion() {
        return this.mCecVersion;
    }

    public int getDevicePowerStatus() {
        return this.mDevicePowerStatus;
    }

    public int getDeviceId() {
        return this.mDeviceId;
    }

    public int getAdopterId() {
        return this.mAdopterId;
    }

    public boolean isSourceType() {
        return isCecDevice() ? this.mDeviceType == 4 || this.mDeviceType == 1 || this.mDeviceType == 3 : isMhlDevice();
    }

    public boolean isCecDevice() {
        return this.mHdmiDeviceType == 0;
    }

    public boolean isMhlDevice() {
        return this.mHdmiDeviceType == 1;
    }

    public boolean isInactivated() {
        return this.mHdmiDeviceType == 100;
    }

    public java.lang.String getDisplayName() {
        return this.mDisplayName;
    }

    public int getVendorId() {
        return this.mVendorId;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mHdmiDeviceType);
        parcel.writeInt(this.mPhysicalAddress);
        parcel.writeInt(this.mPortId);
        switch (this.mHdmiDeviceType) {
            case 0:
                parcel.writeInt(this.mLogicalAddress);
                parcel.writeInt(this.mDeviceType);
                parcel.writeInt(this.mVendorId);
                parcel.writeInt(this.mDevicePowerStatus);
                parcel.writeString(this.mDisplayName);
                parcel.writeInt(this.mCecVersion);
                break;
            case 1:
                parcel.writeInt(this.mDeviceId);
                parcel.writeInt(this.mAdopterId);
                break;
        }
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        switch (this.mHdmiDeviceType) {
            case 0:
                sb.append("CEC: ");
                sb.append("logical_address: ").append(java.lang.String.format("0x%02X", java.lang.Integer.valueOf(this.mLogicalAddress)));
                sb.append(" ");
                sb.append("device_type: ").append(this.mDeviceType).append(" ");
                sb.append("cec_version: ").append(this.mCecVersion).append(" ");
                sb.append("vendor_id: ").append(this.mVendorId).append(" ");
                sb.append("display_name: ").append(this.mDisplayName).append(" ");
                sb.append("power_status: ").append(this.mDevicePowerStatus).append(" ");
                break;
            case 1:
                sb.append("MHL: ");
                sb.append("device_id: ").append(java.lang.String.format("0x%04X", java.lang.Integer.valueOf(this.mDeviceId))).append(" ");
                sb.append("adopter_id: ").append(java.lang.String.format("0x%04X", java.lang.Integer.valueOf(this.mAdopterId))).append(" ");
                break;
            case 2:
                sb.append("Hardware: ");
                break;
            case 100:
                sb.append("Inactivated: ");
                break;
            default:
                return "";
        }
        sb.append("physical_address: ").append(java.lang.String.format("0x%04X", java.lang.Integer.valueOf(this.mPhysicalAddress)));
        sb.append(" ");
        sb.append("port_id: ").append(this.mPortId);
        if (this.mHdmiDeviceType == 0) {
            sb.append("\n  ").append(this.mDeviceFeatures.toString());
        }
        return sb.toString();
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.hardware.hdmi.HdmiDeviceInfo)) {
            return false;
        }
        android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo = (android.hardware.hdmi.HdmiDeviceInfo) obj;
        return this.mHdmiDeviceType == hdmiDeviceInfo.mHdmiDeviceType && this.mPhysicalAddress == hdmiDeviceInfo.mPhysicalAddress && this.mPortId == hdmiDeviceInfo.mPortId && this.mLogicalAddress == hdmiDeviceInfo.mLogicalAddress && this.mDeviceType == hdmiDeviceInfo.mDeviceType && this.mCecVersion == hdmiDeviceInfo.mCecVersion && this.mVendorId == hdmiDeviceInfo.mVendorId && this.mDevicePowerStatus == hdmiDeviceInfo.mDevicePowerStatus && this.mDisplayName.equals(hdmiDeviceInfo.mDisplayName) && this.mDeviceId == hdmiDeviceInfo.mDeviceId && this.mAdopterId == hdmiDeviceInfo.mAdopterId;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mHdmiDeviceType), java.lang.Integer.valueOf(this.mPhysicalAddress), java.lang.Integer.valueOf(this.mPortId), java.lang.Integer.valueOf(this.mLogicalAddress), java.lang.Integer.valueOf(this.mDeviceType), java.lang.Integer.valueOf(this.mCecVersion), java.lang.Integer.valueOf(this.mVendorId), java.lang.Integer.valueOf(this.mDevicePowerStatus), this.mDisplayName, java.lang.Integer.valueOf(this.mDeviceId), java.lang.Integer.valueOf(this.mAdopterId));
    }

    public static final class Builder {
        private int mAdopterId;
        private int mCecVersion;
        private android.hardware.hdmi.DeviceFeatures mDeviceFeatures;
        private int mDeviceId;
        private int mDevicePowerStatus;
        private int mDeviceType;
        private java.lang.String mDisplayName;
        private final int mHdmiDeviceType;
        private int mLogicalAddress;
        private int mPhysicalAddress;
        private int mPortId;
        private int mVendorId;

        private Builder(int i) {
            this.mPhysicalAddress = 65535;
            this.mPortId = -1;
            this.mLogicalAddress = -1;
            this.mDeviceType = 2;
            this.mCecVersion = 5;
            this.mVendorId = 16777215;
            this.mDisplayName = "";
            this.mDevicePowerStatus = -1;
            this.mDeviceId = -1;
            this.mAdopterId = -1;
            this.mHdmiDeviceType = i;
            if (i == 0) {
                this.mDeviceFeatures = android.hardware.hdmi.DeviceFeatures.ALL_FEATURES_SUPPORT_UNKNOWN;
            } else {
                this.mDeviceFeatures = android.hardware.hdmi.DeviceFeatures.NO_FEATURES_SUPPORTED;
            }
        }

        private Builder(android.hardware.hdmi.HdmiDeviceInfo hdmiDeviceInfo) {
            this.mPhysicalAddress = 65535;
            this.mPortId = -1;
            this.mLogicalAddress = -1;
            this.mDeviceType = 2;
            this.mCecVersion = 5;
            this.mVendorId = 16777215;
            this.mDisplayName = "";
            this.mDevicePowerStatus = -1;
            this.mDeviceId = -1;
            this.mAdopterId = -1;
            this.mHdmiDeviceType = hdmiDeviceInfo.mHdmiDeviceType;
            this.mPhysicalAddress = hdmiDeviceInfo.mPhysicalAddress;
            this.mPortId = hdmiDeviceInfo.mPortId;
            this.mLogicalAddress = hdmiDeviceInfo.mLogicalAddress;
            this.mDeviceType = hdmiDeviceInfo.mDeviceType;
            this.mCecVersion = hdmiDeviceInfo.mCecVersion;
            this.mVendorId = hdmiDeviceInfo.mVendorId;
            this.mDisplayName = hdmiDeviceInfo.mDisplayName;
            this.mDevicePowerStatus = hdmiDeviceInfo.mDevicePowerStatus;
            this.mDeviceId = hdmiDeviceInfo.mDeviceId;
            this.mAdopterId = hdmiDeviceInfo.mAdopterId;
            this.mDeviceFeatures = hdmiDeviceInfo.mDeviceFeatures;
        }

        public android.hardware.hdmi.HdmiDeviceInfo build() {
            return new android.hardware.hdmi.HdmiDeviceInfo(this);
        }

        public android.hardware.hdmi.HdmiDeviceInfo.Builder setPhysicalAddress(int i) {
            this.mPhysicalAddress = i;
            return this;
        }

        public android.hardware.hdmi.HdmiDeviceInfo.Builder setPortId(int i) {
            this.mPortId = i;
            return this;
        }

        public android.hardware.hdmi.HdmiDeviceInfo.Builder setLogicalAddress(int i) {
            this.mLogicalAddress = i;
            return this;
        }

        public android.hardware.hdmi.HdmiDeviceInfo.Builder setDeviceType(int i) {
            this.mDeviceType = i;
            return this;
        }

        public android.hardware.hdmi.HdmiDeviceInfo.Builder setCecVersion(int i) {
            this.mCecVersion = i;
            return this;
        }

        public android.hardware.hdmi.HdmiDeviceInfo.Builder setVendorId(int i) {
            this.mVendorId = i;
            return this;
        }

        public android.hardware.hdmi.HdmiDeviceInfo.Builder setDisplayName(java.lang.String str) {
            this.mDisplayName = str;
            return this;
        }

        public android.hardware.hdmi.HdmiDeviceInfo.Builder setDevicePowerStatus(int i) {
            this.mDevicePowerStatus = i;
            return this;
        }

        public android.hardware.hdmi.HdmiDeviceInfo.Builder setDeviceFeatures(android.hardware.hdmi.DeviceFeatures deviceFeatures) {
            this.mDeviceFeatures = deviceFeatures;
            return this;
        }

        public android.hardware.hdmi.HdmiDeviceInfo.Builder setDeviceId(int i) {
            this.mDeviceId = i;
            return this;
        }

        public android.hardware.hdmi.HdmiDeviceInfo.Builder setAdopterId(int i) {
            this.mAdopterId = i;
            return this;
        }

        public android.hardware.hdmi.HdmiDeviceInfo.Builder updateDeviceFeatures(android.hardware.hdmi.DeviceFeatures deviceFeatures) {
            this.mDeviceFeatures = this.mDeviceFeatures.toBuilder().update(deviceFeatures).build();
            return this;
        }
    }
}
