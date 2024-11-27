package com.android.server.usb.hal.port;

/* loaded from: classes2.dex */
public final class RawPortInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<com.android.server.usb.hal.port.RawPortInfo> CREATOR = new android.os.Parcelable.Creator<com.android.server.usb.hal.port.RawPortInfo>() { // from class: com.android.server.usb.hal.port.RawPortInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.server.usb.hal.port.RawPortInfo createFromParcel(android.os.Parcel parcel) {
            android.hardware.usb.DisplayPortAltModeInfo displayPortAltModeInfo;
            java.lang.String readString = parcel.readString();
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            boolean z = parcel.readByte() != 0;
            int readInt4 = parcel.readInt();
            boolean z2 = parcel.readByte() != 0;
            int readInt5 = parcel.readInt();
            boolean z3 = parcel.readByte() != 0;
            boolean readBoolean = parcel.readBoolean();
            int readInt6 = parcel.readInt();
            boolean readBoolean2 = parcel.readBoolean();
            int readInt7 = parcel.readInt();
            int readInt8 = parcel.readInt();
            boolean readBoolean3 = parcel.readBoolean();
            int readInt9 = parcel.readInt();
            boolean readBoolean4 = parcel.readBoolean();
            int[] createIntArray = parcel.createIntArray();
            int readInt10 = parcel.readInt();
            int readInt11 = parcel.readInt();
            if ((readInt11 & 1) != 0) {
                displayPortAltModeInfo = (android.hardware.usb.DisplayPortAltModeInfo) android.hardware.usb.DisplayPortAltModeInfo.CREATOR.createFromParcel(parcel);
            } else {
                displayPortAltModeInfo = null;
            }
            return new com.android.server.usb.hal.port.RawPortInfo(readString, readInt, readInt2, readInt3, z, readInt4, z2, readInt5, z3, readBoolean, readInt6, readBoolean2, readInt7, readInt8, readBoolean3, readInt9, readBoolean4, createIntArray, readInt10, readInt11, displayPortAltModeInfo);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public com.android.server.usb.hal.port.RawPortInfo[] newArray(int i) {
            return new com.android.server.usb.hal.port.RawPortInfo[i];
        }
    };
    public boolean canChangeDataRole;
    public boolean canChangeMode;
    public boolean canChangePowerRole;
    public int[] complianceWarnings;
    public int contaminantDetectionStatus;
    public int contaminantProtectionStatus;
    public int currentDataRole;
    public int currentMode;
    public int currentPowerRole;
    public android.hardware.usb.DisplayPortAltModeInfo displayPortAltModeInfo;
    public int plugState;
    public final java.lang.String portId;
    public int powerBrickConnectionStatus;
    public boolean powerTransferLimited;
    public int supportedAltModes;
    public final int supportedContaminantProtectionModes;
    public final int supportedModes;
    public final boolean supportsComplianceWarnings;
    public boolean supportsEnableContaminantPresenceDetection;
    public boolean supportsEnableContaminantPresenceProtection;
    public int usbDataStatus;

    public RawPortInfo(java.lang.String str, int i) {
        this.portId = str;
        this.supportedModes = i;
        this.supportedContaminantProtectionModes = 0;
        this.supportsEnableContaminantPresenceProtection = false;
        this.contaminantProtectionStatus = 0;
        this.supportsEnableContaminantPresenceDetection = false;
        this.contaminantDetectionStatus = 0;
        this.usbDataStatus = 0;
        this.powerTransferLimited = false;
        this.powerBrickConnectionStatus = 0;
        this.supportsComplianceWarnings = false;
        this.complianceWarnings = new int[0];
        this.plugState = 0;
        this.supportedAltModes = 0;
        this.displayPortAltModeInfo = null;
    }

    public RawPortInfo(java.lang.String str, int i, int i2, int i3, boolean z, int i4, boolean z2, int i5, boolean z3, boolean z4, int i6, boolean z5, int i7, int i8, boolean z6, int i9) {
        this(str, i, i2, i3, z, i4, z2, i5, z3, z4, i6, z5, i7, i8, z6, i9, false, new int[0], 0, 0, null);
    }

    public RawPortInfo(java.lang.String str, int i, int i2, int i3, boolean z, int i4, boolean z2, int i5, boolean z3, boolean z4, int i6, boolean z5, int i7, int i8, boolean z6, int i9, boolean z7, int[] iArr, int i10, int i11, android.hardware.usb.DisplayPortAltModeInfo displayPortAltModeInfo) {
        this.portId = str;
        this.supportedModes = i;
        this.supportedContaminantProtectionModes = i2;
        this.currentMode = i3;
        this.canChangeMode = z;
        this.currentPowerRole = i4;
        this.canChangePowerRole = z2;
        this.currentDataRole = i5;
        this.canChangeDataRole = z3;
        this.supportsEnableContaminantPresenceProtection = z4;
        this.contaminantProtectionStatus = i6;
        this.supportsEnableContaminantPresenceDetection = z5;
        this.contaminantDetectionStatus = i7;
        this.usbDataStatus = i8;
        this.powerTransferLimited = z6;
        this.powerBrickConnectionStatus = i9;
        this.supportsComplianceWarnings = z7;
        this.complianceWarnings = iArr;
        this.plugState = i10;
        this.supportedAltModes = i11;
        this.displayPortAltModeInfo = displayPortAltModeInfo;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.portId);
        parcel.writeInt(this.supportedModes);
        parcel.writeInt(this.supportedContaminantProtectionModes);
        parcel.writeInt(this.currentMode);
        parcel.writeByte(this.canChangeMode ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.currentPowerRole);
        parcel.writeByte(this.canChangePowerRole ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.currentDataRole);
        parcel.writeByte(this.canChangeDataRole ? (byte) 1 : (byte) 0);
        parcel.writeBoolean(this.supportsEnableContaminantPresenceProtection);
        parcel.writeInt(this.contaminantProtectionStatus);
        parcel.writeBoolean(this.supportsEnableContaminantPresenceDetection);
        parcel.writeInt(this.contaminantDetectionStatus);
        parcel.writeInt(this.usbDataStatus);
        parcel.writeBoolean(this.powerTransferLimited);
        parcel.writeInt(this.powerBrickConnectionStatus);
        parcel.writeBoolean(this.supportsComplianceWarnings);
        parcel.writeIntArray(this.complianceWarnings);
        parcel.writeInt(this.plugState);
        parcel.writeInt(this.supportedAltModes);
        if ((this.supportedAltModes & 1) != 0) {
            this.displayPortAltModeInfo.writeToParcel(parcel, 0);
        }
    }
}
