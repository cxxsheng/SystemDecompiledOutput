package com.android.server.hdmi;

/* loaded from: classes2.dex */
public class HdmiCecMessage {
    public static final byte[] EMPTY_PARAM = libcore.util.EmptyArray.BYTE;
    private final int mDestination;
    private final int mOpcode;
    private final byte[] mParams;
    private final int mSource;
    private final int mValidationResult;

    protected HdmiCecMessage(int i, int i2, int i3, byte[] bArr, @com.android.server.hdmi.HdmiCecMessageValidator.ValidationResult int i4) {
        this.mSource = i;
        this.mDestination = i2;
        this.mOpcode = i3 & 255;
        this.mParams = java.util.Arrays.copyOf(bArr, bArr.length);
        this.mValidationResult = i4;
    }

    private HdmiCecMessage(int i, int i2, int i3, byte[] bArr) {
        this(i, i2, i3, bArr, com.android.server.hdmi.HdmiCecMessageValidator.validate(i, i2, i3 & 255, bArr));
    }

    static com.android.server.hdmi.HdmiCecMessage build(int i, int i2, int i3, byte[] bArr) {
        int i4 = i3 & 255;
        switch (i4) {
            case 115:
                return com.android.server.hdmi.SetAudioVolumeLevelMessage.build(i, i2, bArr);
            case 166:
                return com.android.server.hdmi.ReportFeaturesMessage.build(i, i2, bArr);
            default:
                return new com.android.server.hdmi.HdmiCecMessage(i, i2, i4, bArr);
        }
    }

    static com.android.server.hdmi.HdmiCecMessage build(int i, int i2, int i3) {
        return new com.android.server.hdmi.HdmiCecMessage(i, i2, i3, EMPTY_PARAM);
    }

    public boolean equals(@android.annotation.Nullable java.lang.Object obj) {
        if (!(obj instanceof com.android.server.hdmi.HdmiCecMessage)) {
            return false;
        }
        com.android.server.hdmi.HdmiCecMessage hdmiCecMessage = (com.android.server.hdmi.HdmiCecMessage) obj;
        return this.mSource == hdmiCecMessage.getSource() && this.mDestination == hdmiCecMessage.getDestination() && this.mOpcode == hdmiCecMessage.getOpcode() && java.util.Arrays.equals(this.mParams, hdmiCecMessage.getParams()) && this.mValidationResult == hdmiCecMessage.getValidationResult();
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mSource), java.lang.Integer.valueOf(this.mDestination), java.lang.Integer.valueOf(this.mOpcode), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mParams)));
    }

    public int getSource() {
        return this.mSource;
    }

    public int getDestination() {
        return this.mDestination;
    }

    public int getOpcode() {
        return this.mOpcode;
    }

    public byte[] getParams() {
        return this.mParams;
    }

    public int getValidationResult() {
        return this.mValidationResult;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(java.lang.String.format("<%s> %X%X:%02X", opcodeToString(this.mOpcode), java.lang.Integer.valueOf(this.mSource), java.lang.Integer.valueOf(this.mDestination), java.lang.Integer.valueOf(this.mOpcode)));
        if (this.mParams.length > 0) {
            if (filterMessageParameters(this.mOpcode)) {
                sb.append(java.lang.String.format(" <Redacted len=%d>", java.lang.Integer.valueOf(this.mParams.length)));
            } else {
                if (isUserControlPressedMessage(this.mOpcode)) {
                    sb.append(java.lang.String.format(" <Keycode type = %s>", com.android.server.hdmi.HdmiCecKeycode.getKeycodeType(this.mParams[0])));
                } else {
                    for (byte b : this.mParams) {
                        sb.append(java.lang.String.format(":%02X", java.lang.Byte.valueOf(b)));
                    }
                }
            }
        }
        if (this.mValidationResult != 0) {
            sb.append(java.lang.String.format(" <Validation error: %s>", validationResultToString(this.mValidationResult)));
        }
        return sb.toString();
    }

    private static java.lang.String validationResultToString(@com.android.server.hdmi.HdmiCecMessageValidator.ValidationResult int i) {
        switch (i) {
            case 0:
                return "ok";
            case 1:
                return "invalid source";
            case 2:
                return "invalid destination";
            case 3:
                return "invalid parameters";
            case 4:
                return "short parameters";
            default:
                return "unknown error";
        }
    }

    private static java.lang.String opcodeToString(int i) {
        switch (i) {
            case 0:
                return "Feature Abort";
            case 4:
                return "Image View On";
            case 5:
                return "Tuner Step Increment";
            case 6:
                return "Tuner Step Decrement";
            case 7:
                return "Tuner Device Status";
            case 8:
                return "Give Tuner Device Status";
            case 9:
                return "Record On";
            case 10:
                return "Record Status";
            case 11:
                return "Record Off";
            case 13:
                return "Text View On";
            case 15:
                return "Record Tv Screen";
            case 26:
                return "Give Deck Status";
            case 27:
                return "Deck Status";
            case 50:
                return "Set Menu Language";
            case 51:
                return "Clear Analog Timer";
            case 52:
                return "Set Analog Timer";
            case 53:
                return "Timer Status";
            case 54:
                return "Standby";
            case 65:
                return "Play";
            case 66:
                return "Deck Control";
            case 67:
                return "Timer Cleared Status";
            case 68:
                return "User Control Pressed";
            case 69:
                return "User Control Release";
            case 70:
                return "Give Osd Name";
            case 71:
                return "Set Osd Name";
            case 100:
                return "Set Osd String";
            case 103:
                return "Set Timer Program Title";
            case 112:
                return "System Audio Mode Request";
            case 113:
                return "Give Audio Status";
            case 114:
                return "Set System Audio Mode";
            case 115:
                return "Set Audio Volume Level";
            case 122:
                return "Report Audio Status";
            case 125:
                return "Give System Audio Mode Status";
            case 126:
                return "System Audio Mode Status";
            case 128:
                return "Routing Change";
            case 129:
                return "Routing Information";
            case 130:
                return "Active Source";
            case 131:
                return "Give Physical Address";
            case 132:
                return "Report Physical Address";
            case 133:
                return "Request Active Source";
            case 134:
                return "Set Stream Path";
            case 135:
                return "Device Vendor Id";
            case 137:
                return "Vendor Command";
            case 138:
                return "Vendor Remote Button Down";
            case 139:
                return "Vendor Remote Button Up";
            case 140:
                return "Give Device Vendor Id";
            case 141:
                return "Menu Request";
            case 142:
                return "Menu Status";
            case 143:
                return "Give Device Power Status";
            case 144:
                return "Report Power Status";
            case 145:
                return "Get Menu Language";
            case 146:
                return "Select Analog Service";
            case 147:
                return "Select Digital Service";
            case 151:
                return "Set Digital Timer";
            case 153:
                return "Clear Digital Timer";
            case 154:
                return "Set Audio Rate";
            case 157:
                return "InActive Source";
            case 158:
                return "Cec Version";
            case 159:
                return "Get Cec Version";
            case 160:
                return "Vendor Command With Id";
            case 161:
                return "Clear External Timer";
            case 162:
                return "Set External Timer";
            case 163:
                return "Report Short Audio Descriptor";
            case 164:
                return "Request Short Audio Descriptor";
            case 165:
                return "Give Features";
            case 166:
                return "Report Features";
            case 167:
                return "Request Current Latency";
            case 168:
                return "Report Current Latency";
            case 192:
                return "Initiate ARC";
            case 193:
                return "Report ARC Initiated";
            case 194:
                return "Report ARC Terminated";
            case 195:
                return "Request ARC Initiation";
            case 196:
                return "Request ARC Termination";
            case 197:
                return "Terminate ARC";
            case com.android.internal.util.FrameworkStatsLog.INTEGRITY_RULES_PUSHED /* 248 */:
                return "Cdc Message";
            case 255:
                return "Abort";
            default:
                return java.lang.String.format("Opcode: %02X", java.lang.Integer.valueOf(i));
        }
    }

    private static boolean filterMessageParameters(int i) {
        switch (i) {
            case 69:
            case 71:
            case 100:
            case 137:
            case 138:
            case 139:
            case 160:
                return true;
            default:
                return false;
        }
    }

    private static boolean isUserControlPressedMessage(int i) {
        return 68 == i;
    }

    static boolean isCecTransportMessage(int i) {
        switch (i) {
            case 167:
            case 168:
            case com.android.internal.util.FrameworkStatsLog.INTEGRITY_RULES_PUSHED /* 248 */:
                return true;
            default:
                return false;
        }
    }
}
