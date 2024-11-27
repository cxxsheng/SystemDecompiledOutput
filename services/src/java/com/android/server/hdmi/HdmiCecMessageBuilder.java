package com.android.server.hdmi;

/* loaded from: classes2.dex */
public class HdmiCecMessageBuilder {
    private static final int OSD_NAME_MAX_LENGTH = 14;

    private HdmiCecMessageBuilder() {
    }

    static com.android.server.hdmi.HdmiCecMessage buildFeatureAbortCommand(int i, int i2, int i3, int i4) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 0, new byte[]{(byte) (i3 & 255), (byte) (i4 & 255)});
    }

    static com.android.server.hdmi.HdmiCecMessage buildGivePhysicalAddress(int i, int i2) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 131);
    }

    static com.android.server.hdmi.HdmiCecMessage buildGiveOsdNameCommand(int i, int i2) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 70);
    }

    static com.android.server.hdmi.HdmiCecMessage buildGiveDeviceVendorIdCommand(int i, int i2) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 140);
    }

    static com.android.server.hdmi.HdmiCecMessage buildSetMenuLanguageCommand(int i, java.lang.String str) {
        if (str.length() != 3) {
            return null;
        }
        java.lang.String lowerCase = str.toLowerCase();
        return com.android.server.hdmi.HdmiCecMessage.build(i, 15, 50, new byte[]{(byte) (lowerCase.charAt(0) & 255), (byte) (lowerCase.charAt(1) & 255), (byte) (lowerCase.charAt(2) & 255)});
    }

    static com.android.server.hdmi.HdmiCecMessage buildSetOsdNameCommand(int i, int i2, java.lang.String str) {
        try {
            return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 71, str.substring(0, java.lang.Math.min(str.length(), 14)).getBytes("US-ASCII"));
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
    }

    static com.android.server.hdmi.HdmiCecMessage buildReportPhysicalAddressCommand(int i, int i2, int i3) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, 15, 132, new byte[]{(byte) ((i2 >> 8) & 255), (byte) (i2 & 255), (byte) (i3 & 255)});
    }

    static com.android.server.hdmi.HdmiCecMessage buildDeviceVendorIdCommand(int i, int i2) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, 15, 135, new byte[]{(byte) ((i2 >> 16) & 255), (byte) ((i2 >> 8) & 255), (byte) (i2 & 255)});
    }

    static com.android.server.hdmi.HdmiCecMessage buildCecVersion(int i, int i2, int i3) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 158, new byte[]{(byte) (i3 & 255)});
    }

    static com.android.server.hdmi.HdmiCecMessage buildRequestArcInitiation(int i, int i2) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 195);
    }

    static com.android.server.hdmi.HdmiCecMessage buildInitiateArc(int i, int i2) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 192);
    }

    static com.android.server.hdmi.HdmiCecMessage buildTerminateArc(int i, int i2) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 197);
    }

    static com.android.server.hdmi.HdmiCecMessage buildRequestArcTermination(int i, int i2) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 196);
    }

    static com.android.server.hdmi.HdmiCecMessage buildReportArcInitiated(int i, int i2) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 193);
    }

    static com.android.server.hdmi.HdmiCecMessage buildReportArcTerminated(int i, int i2) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 194);
    }

    static com.android.server.hdmi.HdmiCecMessage buildRequestShortAudioDescriptor(int i, int i2, int[] iArr) {
        int min = java.lang.Math.min(iArr.length, 4);
        byte[] bArr = new byte[min];
        for (int i3 = 0; i3 < min; i3++) {
            bArr[i3] = (byte) (iArr[i3] & 255);
        }
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 164, bArr);
    }

    static com.android.server.hdmi.HdmiCecMessage buildTextViewOn(int i, int i2) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 13);
    }

    static com.android.server.hdmi.HdmiCecMessage buildImageViewOn(int i, int i2) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 4);
    }

    static com.android.server.hdmi.HdmiCecMessage buildRequestActiveSource(int i) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, 15, 133);
    }

    static com.android.server.hdmi.HdmiCecMessage buildActiveSource(int i, int i2) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, 15, 130, physicalAddressToParam(i2));
    }

    static com.android.server.hdmi.HdmiCecMessage buildInactiveSource(int i, int i2) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, 0, 157, physicalAddressToParam(i2));
    }

    static com.android.server.hdmi.HdmiCecMessage buildSetStreamPath(int i, int i2) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, 15, 134, physicalAddressToParam(i2));
    }

    static com.android.server.hdmi.HdmiCecMessage buildRoutingChange(int i, int i2, int i3) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, 15, 128, new byte[]{(byte) ((i2 >> 8) & 255), (byte) (i2 & 255), (byte) ((i3 >> 8) & 255), (byte) (i3 & 255)});
    }

    static com.android.server.hdmi.HdmiCecMessage buildRoutingInformation(int i, int i2) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, 15, 129, physicalAddressToParam(i2));
    }

    static com.android.server.hdmi.HdmiCecMessage buildGiveDevicePowerStatus(int i, int i2) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 143);
    }

    static com.android.server.hdmi.HdmiCecMessage buildReportPowerStatus(int i, int i2, int i3) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 144, new byte[]{(byte) (i3 & 255)});
    }

    static com.android.server.hdmi.HdmiCecMessage buildReportMenuStatus(int i, int i2, int i3) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 142, new byte[]{(byte) (i3 & 255)});
    }

    static com.android.server.hdmi.HdmiCecMessage buildSystemAudioModeRequest(int i, int i2, int i3, boolean z) {
        if (z) {
            return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 112, physicalAddressToParam(i3));
        }
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 112);
    }

    static com.android.server.hdmi.HdmiCecMessage buildSetSystemAudioMode(int i, int i2, boolean z) {
        return buildCommandWithBooleanParam(i, i2, 114, z);
    }

    static com.android.server.hdmi.HdmiCecMessage buildReportSystemAudioMode(int i, int i2, boolean z) {
        return buildCommandWithBooleanParam(i, i2, 126, z);
    }

    static com.android.server.hdmi.HdmiCecMessage buildReportShortAudioDescriptor(int i, int i2, byte[] bArr) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 163, bArr);
    }

    static com.android.server.hdmi.HdmiCecMessage buildGiveAudioStatus(int i, int i2) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 113);
    }

    static com.android.server.hdmi.HdmiCecMessage buildReportAudioStatus(int i, int i2, int i3, boolean z) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 122, new byte[]{(byte) ((((byte) i3) & Byte.MAX_VALUE) | ((byte) (z ? 128 : 0)))});
    }

    static com.android.server.hdmi.HdmiCecMessage buildUserControlPressed(int i, int i2, int i3) {
        return buildUserControlPressed(i, i2, new byte[]{(byte) (i3 & 255)});
    }

    static com.android.server.hdmi.HdmiCecMessage buildUserControlPressed(int i, int i2, byte[] bArr) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 68, bArr);
    }

    static com.android.server.hdmi.HdmiCecMessage buildUserControlReleased(int i, int i2) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 69);
    }

    static com.android.server.hdmi.HdmiCecMessage buildGiveSystemAudioModeStatus(int i, int i2) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 125);
    }

    public static com.android.server.hdmi.HdmiCecMessage buildStandby(int i, int i2) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 54);
    }

    static com.android.server.hdmi.HdmiCecMessage buildVendorCommand(int i, int i2, byte[] bArr) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 137, bArr);
    }

    static com.android.server.hdmi.HdmiCecMessage buildVendorCommandWithId(int i, int i2, int i3, byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length + 3];
        bArr2[0] = (byte) ((i3 >> 16) & 255);
        bArr2[1] = (byte) ((i3 >> 8) & 255);
        bArr2[2] = (byte) (i3 & 255);
        java.lang.System.arraycopy(bArr, 0, bArr2, 3, bArr.length);
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 160, bArr2);
    }

    static com.android.server.hdmi.HdmiCecMessage buildRecordOn(int i, int i2, byte[] bArr) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 9, bArr);
    }

    static com.android.server.hdmi.HdmiCecMessage buildRecordOff(int i, int i2) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 11);
    }

    static com.android.server.hdmi.HdmiCecMessage buildSetDigitalTimer(int i, int i2, byte[] bArr) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 151, bArr);
    }

    static com.android.server.hdmi.HdmiCecMessage buildSetAnalogueTimer(int i, int i2, byte[] bArr) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 52, bArr);
    }

    static com.android.server.hdmi.HdmiCecMessage buildSetExternalTimer(int i, int i2, byte[] bArr) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 162, bArr);
    }

    static com.android.server.hdmi.HdmiCecMessage buildClearDigitalTimer(int i, int i2, byte[] bArr) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 153, bArr);
    }

    static com.android.server.hdmi.HdmiCecMessage buildClearAnalogueTimer(int i, int i2, byte[] bArr) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 51, bArr);
    }

    static com.android.server.hdmi.HdmiCecMessage buildClearExternalTimer(int i, int i2, byte[] bArr) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 161, bArr);
    }

    static com.android.server.hdmi.HdmiCecMessage buildGiveFeatures(int i, int i2) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, 165);
    }

    private static com.android.server.hdmi.HdmiCecMessage buildCommandWithBooleanParam(int i, int i2, int i3, boolean z) {
        return com.android.server.hdmi.HdmiCecMessage.build(i, i2, i3, new byte[]{z ? (byte) 1 : (byte) 0});
    }

    private static byte[] physicalAddressToParam(int i) {
        return new byte[]{(byte) ((i >> 8) & 255), (byte) (i & 255)};
    }
}
