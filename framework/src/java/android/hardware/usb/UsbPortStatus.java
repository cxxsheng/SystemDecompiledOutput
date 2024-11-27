package android.hardware.usb;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class UsbPortStatus implements android.os.Parcelable {
    public static final int COMPLIANCE_WARNING_BC_1_2 = 3;
    public static final int COMPLIANCE_WARNING_DEBUG_ACCESSORY = 2;
    public static final int COMPLIANCE_WARNING_ENUMERATION_FAIL = 7;
    public static final int COMPLIANCE_WARNING_FLAKY_CONNECTION = 8;
    public static final int COMPLIANCE_WARNING_INPUT_POWER_LIMITED = 5;
    public static final int COMPLIANCE_WARNING_MISSING_DATA_LINES = 6;
    public static final int COMPLIANCE_WARNING_MISSING_RP = 4;
    public static final int COMPLIANCE_WARNING_OTHER = 1;
    public static final int COMPLIANCE_WARNING_UNRELIABLE_IO = 9;
    public static final int CONTAMINANT_DETECTION_DETECTED = 3;
    public static final int CONTAMINANT_DETECTION_DISABLED = 1;
    public static final int CONTAMINANT_DETECTION_NOT_DETECTED = 2;
    public static final int CONTAMINANT_DETECTION_NOT_SUPPORTED = 0;
    public static final int CONTAMINANT_PROTECTION_DISABLED = 8;
    public static final int CONTAMINANT_PROTECTION_FORCE_DISABLE = 4;
    public static final int CONTAMINANT_PROTECTION_NONE = 0;
    public static final int CONTAMINANT_PROTECTION_SINK = 1;
    public static final int CONTAMINANT_PROTECTION_SOURCE = 2;
    public static final android.os.Parcelable.Creator<android.hardware.usb.UsbPortStatus> CREATOR = new android.os.Parcelable.Creator<android.hardware.usb.UsbPortStatus>() { // from class: android.hardware.usb.UsbPortStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.usb.UsbPortStatus createFromParcel(android.os.Parcel parcel) {
            android.hardware.usb.DisplayPortAltModeInfo displayPortAltModeInfo;
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            int readInt4 = parcel.readInt();
            int readInt5 = parcel.readInt();
            int readInt6 = parcel.readInt();
            int readInt7 = parcel.readInt();
            boolean readBoolean = parcel.readBoolean();
            int readInt8 = parcel.readInt();
            int[] createIntArray = parcel.createIntArray();
            int readInt9 = parcel.readInt();
            if (parcel.readBoolean()) {
                displayPortAltModeInfo = android.hardware.usb.DisplayPortAltModeInfo.CREATOR.createFromParcel(parcel);
            } else {
                displayPortAltModeInfo = null;
            }
            return new android.hardware.usb.UsbPortStatus(readInt, readInt2, readInt3, readInt4, readInt5, readInt6, readInt7, readBoolean, readInt8, createIntArray, readInt9, displayPortAltModeInfo);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.hardware.usb.UsbPortStatus[] newArray(int i) {
            return new android.hardware.usb.UsbPortStatus[i];
        }
    };
    public static final int DATA_ROLE_DEVICE = 2;
    public static final int DATA_ROLE_HOST = 1;
    public static final int DATA_ROLE_NONE = 0;
    public static final int DATA_STATUS_DISABLED_CONTAMINANT = 4;
    public static final int DATA_STATUS_DISABLED_DEBUG = 32;
    public static final int DATA_STATUS_DISABLED_DOCK = 8;
    public static final int DATA_STATUS_DISABLED_DOCK_DEVICE_MODE = 128;
    public static final int DATA_STATUS_DISABLED_DOCK_HOST_MODE = 64;
    public static final int DATA_STATUS_DISABLED_FORCE = 16;
    public static final int DATA_STATUS_DISABLED_OVERHEAT = 2;
    public static final int DATA_STATUS_ENABLED = 1;
    public static final int DATA_STATUS_UNKNOWN = 0;
    public static final int MODE_AUDIO_ACCESSORY = 4;
    public static final int MODE_DEBUG_ACCESSORY = 8;
    public static final int MODE_DFP = 2;
    public static final int MODE_DUAL = 3;
    public static final int MODE_NONE = 0;
    public static final int MODE_UFP = 1;
    public static final int PLUG_STATE_PLUGGED_ORIENTATION_FLIPPED = 4;
    public static final int PLUG_STATE_PLUGGED_ORIENTATION_NORMAL = 3;
    public static final int PLUG_STATE_PLUGGED_ORIENTATION_UNKNOWN = 2;
    public static final int PLUG_STATE_UNKNOWN = 0;
    public static final int PLUG_STATE_UNPLUGGED = 1;
    public static final int POWER_BRICK_STATUS_CONNECTED = 1;
    public static final int POWER_BRICK_STATUS_DISCONNECTED = 2;
    public static final int POWER_BRICK_STATUS_UNKNOWN = 0;
    public static final int POWER_ROLE_NONE = 0;
    public static final int POWER_ROLE_SINK = 2;
    public static final int POWER_ROLE_SOURCE = 1;
    private static final java.lang.String TAG = "UsbPortStatus";
    private final int[] mComplianceWarnings;
    private final int mContaminantDetectionStatus;
    private final int mContaminantProtectionStatus;
    private final int mCurrentDataRole;
    private final int mCurrentMode;
    private final int mCurrentPowerRole;
    private final android.hardware.usb.DisplayPortAltModeInfo mDisplayPortAltModeInfo;
    private final int mPlugState;
    private final int mPowerBrickConnectionStatus;
    private final boolean mPowerTransferLimited;
    private final int mSupportedRoleCombinations;
    private final int mUsbDataStatus;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface ComplianceWarning {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface ContaminantDetectionStatus {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface ContaminantProtectionStatus {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface PlugState {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface PowerBrickConnectionStatus {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface UsbDataRole {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface UsbDataStatus {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface UsbPortMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface UsbPowerRole {
    }

    public UsbPortStatus(int i, int i2, int i3, int i4, int i5, int i6, int i7, boolean z, int i8, int[] iArr, int i9, android.hardware.usb.DisplayPortAltModeInfo displayPortAltModeInfo) {
        int i10;
        this.mCurrentMode = i;
        this.mCurrentPowerRole = i2;
        this.mCurrentDataRole = i3;
        this.mSupportedRoleCombinations = i4;
        this.mContaminantProtectionStatus = i5;
        this.mContaminantDetectionStatus = i6;
        if ((i7 & 192) != 0) {
            i10 = i7 | 8;
        } else {
            i10 = i7 & (-9);
        }
        this.mUsbDataStatus = i10;
        this.mPowerTransferLimited = z;
        this.mPowerBrickConnectionStatus = i8;
        this.mComplianceWarnings = iArr;
        this.mPlugState = i9;
        this.mDisplayPortAltModeInfo = displayPortAltModeInfo;
    }

    public UsbPortStatus(int i, int i2, int i3, int i4, int i5, int i6, int i7, boolean z, int i8) {
        this(i, i2, i3, i4, i5, i6, i7, z, i8, new int[0], 0, null);
    }

    public UsbPortStatus(int i, int i2, int i3, int i4, int i5, int i6) {
        this(i, i2, i3, i4, i5, i6, 0, false, 0, new int[0], 0, null);
    }

    public boolean isConnected() {
        return this.mCurrentMode != 0;
    }

    public int getCurrentMode() {
        return this.mCurrentMode;
    }

    public int getCurrentPowerRole() {
        return this.mCurrentPowerRole;
    }

    public int getCurrentDataRole() {
        return this.mCurrentDataRole;
    }

    public boolean isRoleCombinationSupported(int i, int i2) {
        return (android.hardware.usb.UsbPort.combineRolesAsBit(i, i2) & this.mSupportedRoleCombinations) != 0;
    }

    public boolean isPdCompliant() {
        return isRoleCombinationSupported(2, 2) && isRoleCombinationSupported(2, 1) && isRoleCombinationSupported(1, 2) && isRoleCombinationSupported(1, 1);
    }

    public int getSupportedRoleCombinations() {
        return this.mSupportedRoleCombinations;
    }

    public int getContaminantDetectionStatus() {
        return this.mContaminantDetectionStatus;
    }

    public int getContaminantProtectionStatus() {
        return this.mContaminantProtectionStatus;
    }

    public int getUsbDataStatus() {
        return this.mUsbDataStatus;
    }

    public boolean isPowerTransferLimited() {
        return this.mPowerTransferLimited;
    }

    public int getPowerBrickConnectionStatus() {
        return this.mPowerBrickConnectionStatus;
    }

    public int[] getComplianceWarnings() {
        return this.mComplianceWarnings;
    }

    public int getPlugState() {
        return this.mPlugState;
    }

    public android.hardware.usb.DisplayPortAltModeInfo getDisplayPortAltModeInfo() {
        if (this.mDisplayPortAltModeInfo == null) {
            return null;
        }
        return this.mDisplayPortAltModeInfo;
    }

    public java.lang.String toString() {
        return new java.lang.StringBuilder("UsbPortStatus{connected=" + isConnected() + ", currentMode=" + android.hardware.usb.UsbPort.modeToString(this.mCurrentMode) + ", currentPowerRole=" + android.hardware.usb.UsbPort.powerRoleToString(this.mCurrentPowerRole) + ", currentDataRole=" + android.hardware.usb.UsbPort.dataRoleToString(this.mCurrentDataRole) + ", supportedRoleCombinations=" + android.hardware.usb.UsbPort.roleCombinationsToString(this.mSupportedRoleCombinations) + ", contaminantDetectionStatus=" + getContaminantDetectionStatus() + ", contaminantProtectionStatus=" + getContaminantProtectionStatus() + ", usbDataStatus=" + android.hardware.usb.UsbPort.usbDataStatusToString(getUsbDataStatus()) + ", isPowerTransferLimited=" + isPowerTransferLimited() + ", powerBrickConnectionStatus=" + android.hardware.usb.UsbPort.powerBrickConnectionStatusToString(getPowerBrickConnectionStatus()) + ", complianceWarnings=" + android.hardware.usb.UsbPort.complianceWarningsToString(getComplianceWarnings()) + ", plugState=" + getPlugState() + ", displayPortAltModeInfo=" + this.mDisplayPortAltModeInfo + "}").toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mCurrentMode);
        parcel.writeInt(this.mCurrentPowerRole);
        parcel.writeInt(this.mCurrentDataRole);
        parcel.writeInt(this.mSupportedRoleCombinations);
        parcel.writeInt(this.mContaminantProtectionStatus);
        parcel.writeInt(this.mContaminantDetectionStatus);
        parcel.writeInt(this.mUsbDataStatus);
        parcel.writeBoolean(this.mPowerTransferLimited);
        parcel.writeInt(this.mPowerBrickConnectionStatus);
        parcel.writeIntArray(this.mComplianceWarnings);
        parcel.writeInt(this.mPlugState);
        if (this.mDisplayPortAltModeInfo == null) {
            parcel.writeBoolean(false);
        } else {
            parcel.writeBoolean(true);
            this.mDisplayPortAltModeInfo.writeToParcel(parcel, 0);
        }
    }

    public static final class Builder {
        private boolean mPowerTransferLimited;
        private int mSupportedRoleCombinations;
        private int mCurrentMode = 0;
        private int mCurrentPowerRole = 0;
        private int mCurrentDataRole = 0;
        private int mContaminantProtectionStatus = 0;
        private int mContaminantDetectionStatus = 0;
        private int mUsbDataStatus = 0;
        private int mPowerBrickConnectionStatus = 0;
        private int[] mComplianceWarnings = new int[0];
        private int mPlugState = 0;
        private android.hardware.usb.DisplayPortAltModeInfo mDisplayPortAltModeInfo = null;

        public android.hardware.usb.UsbPortStatus.Builder setCurrentMode(int i) {
            this.mCurrentMode = i;
            return this;
        }

        public android.hardware.usb.UsbPortStatus.Builder setCurrentRoles(int i, int i2) {
            this.mCurrentPowerRole = i;
            this.mCurrentDataRole = i2;
            return this;
        }

        public android.hardware.usb.UsbPortStatus.Builder setSupportedRoleCombinations(int i) {
            this.mSupportedRoleCombinations = i;
            return this;
        }

        public android.hardware.usb.UsbPortStatus.Builder setContaminantStatus(int i, int i2) {
            this.mContaminantProtectionStatus = i;
            this.mContaminantDetectionStatus = i2;
            return this;
        }

        public android.hardware.usb.UsbPortStatus.Builder setPowerTransferLimited(boolean z) {
            this.mPowerTransferLimited = z;
            return this;
        }

        public android.hardware.usb.UsbPortStatus.Builder setUsbDataStatus(int i) {
            this.mUsbDataStatus = i;
            return this;
        }

        public android.hardware.usb.UsbPortStatus.Builder setPowerBrickConnectionStatus(int i) {
            this.mPowerBrickConnectionStatus = i;
            return this;
        }

        public android.hardware.usb.UsbPortStatus.Builder setComplianceWarnings(int[] iArr) {
            if (iArr == null) {
                iArr = new int[0];
            }
            this.mComplianceWarnings = iArr;
            return this;
        }

        public android.hardware.usb.UsbPortStatus.Builder setPlugState(int i) {
            this.mPlugState = i;
            return this;
        }

        public android.hardware.usb.UsbPortStatus.Builder setDisplayPortAltModeInfo(android.hardware.usb.DisplayPortAltModeInfo displayPortAltModeInfo) {
            this.mDisplayPortAltModeInfo = displayPortAltModeInfo;
            return this;
        }

        public android.hardware.usb.UsbPortStatus build() {
            return new android.hardware.usb.UsbPortStatus(this.mCurrentMode, this.mCurrentPowerRole, this.mCurrentDataRole, this.mSupportedRoleCombinations, this.mContaminantProtectionStatus, this.mContaminantDetectionStatus, this.mUsbDataStatus, this.mPowerTransferLimited, this.mPowerBrickConnectionStatus, this.mComplianceWarnings, this.mPlugState, this.mDisplayPortAltModeInfo);
        }
    }
}
