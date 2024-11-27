package com.android.server.hdmi;

/* loaded from: classes2.dex */
public class HdmiCecMessageValidator {
    static final int ADDR_ALL = 65535;
    static final int ADDR_AUDIO_SYSTEM = 32;
    static final int ADDR_BACKUP_1 = 4096;
    static final int ADDR_BACKUP_2 = 8192;
    static final int ADDR_BROADCAST = 32768;
    static final int ADDR_DIRECT = 32767;
    static final int ADDR_NOT_UNREGISTERED = 32767;
    static final int ADDR_PLAYBACK_1 = 16;
    static final int ADDR_PLAYBACK_2 = 256;
    static final int ADDR_PLAYBACK_3 = 2048;
    static final int ADDR_RECORDER_1 = 2;
    static final int ADDR_RECORDER_2 = 4;
    static final int ADDR_RECORDER_3 = 512;
    static final int ADDR_SPECIFIC_USE = 16384;
    static final int ADDR_TUNER_1 = 8;
    static final int ADDR_TUNER_2 = 64;
    static final int ADDR_TUNER_3 = 128;
    static final int ADDR_TUNER_4 = 1024;
    static final int ADDR_TV = 1;
    static final int ADDR_UNREGISTERED = 32768;
    static final int ERROR_DESTINATION = 2;
    static final int ERROR_PARAMETER = 3;
    static final int ERROR_PARAMETER_LONG = 5;
    static final int ERROR_PARAMETER_SHORT = 4;
    static final int ERROR_SOURCE = 1;
    static final int OK = 0;
    private static final java.lang.String TAG = "HdmiCecMessageValidator";
    private static final android.util.SparseArray<com.android.server.hdmi.HdmiCecMessageValidator.ValidationInfo> sValidationInfo = new android.util.SparseArray<>();

    interface ParameterValidator {
        int isValid(byte[] bArr);
    }

    public @interface ValidationResult {
    }

    private HdmiCecMessageValidator() {
    }

    private static class ValidationInfo {
        public final com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator parameterValidator;
        public final int validDestinations;
        public final int validSources;

        ValidationInfo(com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator parameterValidator, int i, int i2) {
            this.parameterValidator = parameterValidator;
            this.validSources = i;
            this.validDestinations = i2;
        }
    }

    static {
        com.android.server.hdmi.HdmiCecMessageValidator.PhysicalAddressValidator physicalAddressValidator = new com.android.server.hdmi.HdmiCecMessageValidator.PhysicalAddressValidator();
        addValidationInfo(130, physicalAddressValidator, 65503, 32768);
        addValidationInfo(157, physicalAddressValidator, 32767, 32767);
        addValidationInfo(132, new com.android.server.hdmi.HdmiCecMessageValidator.ReportPhysicalAddressValidator(), 65535, 32768);
        addValidationInfo(128, new com.android.server.hdmi.HdmiCecMessageValidator.RoutingChangeValidator(), 65535, 32768);
        addValidationInfo(129, physicalAddressValidator, 65535, 32768);
        addValidationInfo(134, physicalAddressValidator, 32767, 32768);
        addValidationInfo(112, new com.android.server.hdmi.HdmiCecMessageValidator.SystemAudioModeRequestValidator(), 32767, 32767);
        com.android.server.hdmi.HdmiCecMessageValidator.FixedLengthValidator fixedLengthValidator = new com.android.server.hdmi.HdmiCecMessageValidator.FixedLengthValidator(0);
        addValidationInfo(255, fixedLengthValidator, 32767, 32767);
        addValidationInfo(159, fixedLengthValidator, 32767, 32767);
        addValidationInfo(145, fixedLengthValidator, 65535, 32767);
        addValidationInfo(113, fixedLengthValidator, 32767, 32767);
        addValidationInfo(143, fixedLengthValidator, 32767, 32767);
        addValidationInfo(140, fixedLengthValidator, 65535, 32767);
        addValidationInfo(70, fixedLengthValidator, 32767, 32767);
        addValidationInfo(131, fixedLengthValidator, 65535, 32767);
        addValidationInfo(125, fixedLengthValidator, 32767, 32767);
        addValidationInfo(4, fixedLengthValidator, 32767, 32767);
        addValidationInfo(192, fixedLengthValidator, 32767, 32767);
        addValidationInfo(11, fixedLengthValidator, 32767, 32767);
        addValidationInfo(15, fixedLengthValidator, 32767, 32767);
        addValidationInfo(193, fixedLengthValidator, 32767, 32767);
        addValidationInfo(194, fixedLengthValidator, 32767, 32767);
        addValidationInfo(195, fixedLengthValidator, 32767, 32767);
        addValidationInfo(196, fixedLengthValidator, 32767, 32767);
        addValidationInfo(133, fixedLengthValidator, 65535, 32768);
        addValidationInfo(54, fixedLengthValidator, 65535, 65535);
        addValidationInfo(197, fixedLengthValidator, 32767, 32767);
        addValidationInfo(13, fixedLengthValidator, 32767, 32767);
        addValidationInfo(6, fixedLengthValidator, 32767, 32767);
        addValidationInfo(5, fixedLengthValidator, 32767, 32767);
        addValidationInfo(69, fixedLengthValidator, 32767, 32767);
        addValidationInfo(139, fixedLengthValidator, 32767, 65535);
        addValidationInfo(9, new com.android.server.hdmi.HdmiCecMessageValidator.VariableLengthValidator(1, 8), 32767, 32767);
        addValidationInfo(10, new com.android.server.hdmi.HdmiCecMessageValidator.RecordStatusInfoValidator(), 32767, 32767);
        addValidationInfo(51, new com.android.server.hdmi.HdmiCecMessageValidator.AnalogueTimerValidator(), 32767, 32767);
        addValidationInfo(153, new com.android.server.hdmi.HdmiCecMessageValidator.DigitalTimerValidator(), 32767, 32767);
        addValidationInfo(161, new com.android.server.hdmi.HdmiCecMessageValidator.ExternalTimerValidator(), 32767, 32767);
        addValidationInfo(52, new com.android.server.hdmi.HdmiCecMessageValidator.AnalogueTimerValidator(), 32767, 32767);
        addValidationInfo(151, new com.android.server.hdmi.HdmiCecMessageValidator.DigitalTimerValidator(), 32767, 32767);
        addValidationInfo(162, new com.android.server.hdmi.HdmiCecMessageValidator.ExternalTimerValidator(), 32767, 32767);
        addValidationInfo(103, new com.android.server.hdmi.HdmiCecMessageValidator.AsciiValidator(1, 14), 32767, 32767);
        addValidationInfo(67, new com.android.server.hdmi.HdmiCecMessageValidator.TimerClearedStatusValidator(), 32767, 32767);
        addValidationInfo(53, new com.android.server.hdmi.HdmiCecMessageValidator.TimerStatusValidator(), 32767, 32767);
        com.android.server.hdmi.HdmiCecMessageValidator.FixedLengthValidator fixedLengthValidator2 = new com.android.server.hdmi.HdmiCecMessageValidator.FixedLengthValidator(1);
        addValidationInfo(158, fixedLengthValidator2, 32767, 32767);
        addValidationInfo(50, new com.android.server.hdmi.HdmiCecMessageValidator.AsciiValidator(3), 32767, 32768);
        com.android.server.hdmi.HdmiCecMessageValidator.MinimumOneByteRangeValidator minimumOneByteRangeValidator = new com.android.server.hdmi.HdmiCecMessageValidator.MinimumOneByteRangeValidator(1, 3);
        addValidationInfo(66, new com.android.server.hdmi.HdmiCecMessageValidator.MinimumOneByteRangeValidator(1, 4), 32767, 32767);
        addValidationInfo(27, new com.android.server.hdmi.HdmiCecMessageValidator.MinimumOneByteRangeValidator(17, 31), 32767, 32767);
        addValidationInfo(26, minimumOneByteRangeValidator, 32767, 32767);
        addValidationInfo(65, new com.android.server.hdmi.HdmiCecMessageValidator.PlayModeValidator(), 32767, 32767);
        addValidationInfo(8, minimumOneByteRangeValidator, 32767, 32767);
        addValidationInfo(146, new com.android.server.hdmi.HdmiCecMessageValidator.SelectAnalogueServiceValidator(), 32767, 32767);
        addValidationInfo(147, new com.android.server.hdmi.HdmiCecMessageValidator.SelectDigitalServiceValidator(), 32767, 32767);
        addValidationInfo(7, new com.android.server.hdmi.HdmiCecMessageValidator.TunerDeviceStatusValidator(), 32767, 32767);
        com.android.server.hdmi.HdmiCecMessageValidator.VariableLengthValidator variableLengthValidator = new com.android.server.hdmi.HdmiCecMessageValidator.VariableLengthValidator(0, 14);
        addValidationInfo(135, new com.android.server.hdmi.HdmiCecMessageValidator.FixedLengthValidator(3), 32767, 32768);
        addValidationInfo(137, new com.android.server.hdmi.HdmiCecMessageValidator.VariableLengthValidator(1, 14), 65535, 32767);
        addValidationInfo(160, new com.android.server.hdmi.HdmiCecMessageValidator.VariableLengthValidator(4, 14), 65535, 65535);
        addValidationInfo(138, variableLengthValidator, 65535, 65535);
        addValidationInfo(100, new com.android.server.hdmi.HdmiCecMessageValidator.OsdStringValidator(), 32767, 32767);
        addValidationInfo(71, new com.android.server.hdmi.HdmiCecMessageValidator.AsciiValidator(1, 14), 32767, 32767);
        addValidationInfo(141, new com.android.server.hdmi.HdmiCecMessageValidator.MinimumOneByteRangeValidator(0, 2), 32767, 32767);
        addValidationInfo(142, new com.android.server.hdmi.HdmiCecMessageValidator.MinimumOneByteRangeValidator(0, 1), 32767, 32767);
        addValidationInfo(68, new com.android.server.hdmi.HdmiCecMessageValidator.UserControlPressedValidator(), 32767, 32767);
        addValidationInfo(144, new com.android.server.hdmi.HdmiCecMessageValidator.MinimumOneByteRangeValidator(0, 3), 32767, 65535);
        addValidationInfo(0, new com.android.server.hdmi.HdmiCecMessageValidator.FixedLengthValidator(2), 32767, 32767);
        addValidationInfo(122, fixedLengthValidator2, 32767, 32767);
        addValidationInfo(163, new com.android.server.hdmi.HdmiCecMessageValidator.FixedLengthValidator(3), 32767, 32767);
        addValidationInfo(164, fixedLengthValidator2, 32767, 32767);
        addValidationInfo(114, new com.android.server.hdmi.HdmiCecMessageValidator.MinimumOneByteRangeValidator(0, 1), 32767, 65535);
        addValidationInfo(126, new com.android.server.hdmi.HdmiCecMessageValidator.SingleByteRangeValidator(0, 1), 32767, 32767);
        addValidationInfo(154, new com.android.server.hdmi.HdmiCecMessageValidator.MinimumOneByteRangeValidator(0, 6), 32767, 32767);
        addValidationInfo(165, fixedLengthValidator, 65535, 32767);
        addValidationInfo(167, physicalAddressValidator, 32767, 32768);
        addValidationInfo(168, new com.android.server.hdmi.HdmiCecMessageValidator.VariableLengthValidator(4, 14), 32767, 32768);
        addValidationInfo(com.android.internal.util.FrameworkStatsLog.INTEGRITY_RULES_PUSHED, variableLengthValidator, 65535, 32768);
    }

    private static void addValidationInfo(int i, com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator parameterValidator, int i2, int i3) {
        sValidationInfo.append(i, new com.android.server.hdmi.HdmiCecMessageValidator.ValidationInfo(parameterValidator, i2, i3));
    }

    @com.android.server.hdmi.HdmiCecMessageValidator.ValidationResult
    static int validate(int i, int i2, int i3, byte[] bArr) {
        com.android.server.hdmi.HdmiCecMessageValidator.ValidationInfo validationInfo = sValidationInfo.get(i3);
        if (validationInfo == null) {
            com.android.server.hdmi.HdmiLogger.warning("No validation information for the opcode: " + i3, new java.lang.Object[0]);
            return 0;
        }
        int validateAddress = validateAddress(i, i2, validationInfo.validSources, validationInfo.validDestinations);
        if (validateAddress != 0) {
            return validateAddress;
        }
        int isValid = validationInfo.parameterValidator.isValid(bArr);
        if (isValid == 0) {
            return 0;
        }
        return isValid;
    }

    @com.android.server.hdmi.HdmiCecMessageValidator.ValidationResult
    static int validateAddress(int i, int i2, int i3, int i4) {
        if (((1 << i) & i3) == 0) {
            return 1;
        }
        if (((1 << i2) & i4) == 0) {
            return 2;
        }
        return 0;
    }

    private static class FixedLengthValidator implements com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator {
        private final int mLength;

        public FixedLengthValidator(int i) {
            this.mLength = i;
        }

        @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
        public int isValid(byte[] bArr) {
            return bArr.length < this.mLength ? 4 : 0;
        }
    }

    private static class VariableLengthValidator implements com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator {
        private final int mMaxLength;
        private final int mMinLength;

        public VariableLengthValidator(int i, int i2) {
            this.mMinLength = i;
            this.mMaxLength = i2;
        }

        @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
        public int isValid(byte[] bArr) {
            return bArr.length < this.mMinLength ? 4 : 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidPhysicalAddress(byte[] bArr, int i) {
        int twoBytesToInt = com.android.server.hdmi.HdmiUtils.twoBytesToInt(bArr, i);
        while (twoBytesToInt != 0) {
            int i2 = 61440 & twoBytesToInt;
            twoBytesToInt = (twoBytesToInt << 4) & 65535;
            if (i2 == 0 && twoBytesToInt != 0) {
                return false;
            }
        }
        return true;
    }

    static boolean isValidType(int i) {
        return i >= 0 && i <= 7 && i != 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int toErrorCode(boolean z) {
        return z ? 0 : 3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isWithinRange(int i, int i2, int i3) {
        int i4 = i & 255;
        return i4 >= i2 && i4 <= i3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidDisplayControl(int i) {
        int i2 = i & 255;
        return i2 == 0 || i2 == 64 || i2 == 128 || i2 == 192;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidAsciiString(byte[] bArr, int i, int i2) {
        while (i < bArr.length && i < i2) {
            if (isWithinRange(bArr[i], 32, 126)) {
                i++;
            } else {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidDayOfMonth(int i) {
        return isWithinRange(i, 1, 31);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidMonthOfYear(int i) {
        return isWithinRange(i, 1, 12);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidHour(int i) {
        return isWithinRange(i, 0, 23);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidMinute(int i) {
        return isWithinRange(i, 0, 59);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidDurationHours(int i) {
        return isWithinRange(i, 0, 99);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidRecordingSequence(int i) {
        int i2 = i & 255;
        return (i2 & 128) == 0 && java.lang.Integer.bitCount(i2) <= 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidAnalogueBroadcastType(int i) {
        return isWithinRange(i, 0, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidAnalogueFrequency(int i) {
        int i2 = i & 65535;
        return (i2 == 0 || i2 == 65535) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidBroadcastSystem(int i) {
        return isWithinRange(i, 0, 31);
    }

    private static boolean isAribDbs(int i) {
        return i == 0 || isWithinRange(i, 8, 10);
    }

    private static boolean isAtscDbs(int i) {
        return i == 1 || isWithinRange(i, 16, 18);
    }

    private static boolean isDvbDbs(int i) {
        return i == 2 || isWithinRange(i, 24, 27);
    }

    private static boolean isValidDigitalBroadcastSystem(int i) {
        return isAribDbs(i) || isAtscDbs(i) || isDvbDbs(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidChannelIdentifier(byte[] bArr, int i) {
        int i2 = bArr[i] & 252;
        return i2 == 4 ? bArr.length - i >= 3 : i2 == 8 && bArr.length - i >= 4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidDigitalServiceIdentification(byte[] bArr, int i) {
        int i2 = bArr[i] & 128;
        int i3 = bArr[i] & Byte.MAX_VALUE;
        int i4 = i + 1;
        if (i2 == 0) {
            if (isAribDbs(i3)) {
                if (bArr.length - i4 >= 6) {
                    return true;
                }
                return false;
            }
            if (isAtscDbs(i3)) {
                if (bArr.length - i4 >= 4) {
                    return true;
                }
                return false;
            }
            if (isDvbDbs(i3) && bArr.length - i4 >= 6) {
                return true;
            }
            return false;
        }
        if (i2 == 128 && isValidDigitalBroadcastSystem(i3)) {
            return isValidChannelIdentifier(bArr, i4);
        }
        return false;
    }

    private static boolean isValidExternalPlug(int i) {
        return isWithinRange(i, 1, 255);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidExternalSource(byte[] bArr, int i) {
        byte b = bArr[i];
        int i2 = i + 1;
        if (b == 4) {
            return isValidExternalPlug(bArr[i2]);
        }
        if (b == 5 && bArr.length - i2 >= 2) {
            return isValidPhysicalAddress(bArr, i2);
        }
        return false;
    }

    private static boolean isValidProgrammedInfo(int i) {
        return isWithinRange(i, 0, 11);
    }

    private static boolean isValidNotProgrammedErrorInfo(int i) {
        return isWithinRange(i, 0, 14);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidTimerStatusData(byte[] bArr, int i) {
        boolean z;
        if ((bArr[i] & com.android.server.usb.descriptors.UsbDescriptor.DESCRIPTORTYPE_CAPABILITY) == 16) {
            int i2 = bArr[i] & 15;
            if (!isValidProgrammedInfo(i2)) {
                z = false;
            } else {
                i++;
                if ((i2 != 9 && i2 != 11) || bArr.length - i < 2) {
                    return true;
                }
                z = true;
            }
        } else {
            int i3 = bArr[i] & 15;
            if (!isValidNotProgrammedErrorInfo(i3)) {
                z = false;
            } else {
                i++;
                if (i3 != 14 || bArr.length - i < 2) {
                    return true;
                }
                z = true;
            }
        }
        if (z) {
            return isValidDurationHours(bArr[i]) && isValidMinute(bArr[i + 1]);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidPlayMode(int i) {
        return isWithinRange(i, 5, 7) || isWithinRange(i, 9, 11) || isWithinRange(i, 21, 23) || isWithinRange(i, 25, 27) || isWithinRange(i, 36, 37) || i == 32;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidUiBroadcastType(int i) {
        return i == 0 || i == 1 || i == 16 || i == 32 || i == 48 || i == 64 || i == 80 || i == 96 || i == 112 || i == 128 || i == 144 || i == 145 || i == 160;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidUiSoundPresenationControl(int i) {
        int i2 = i & 255;
        return i2 == 32 || i2 == 48 || i2 == 128 || i2 == 144 || i2 == 160 || isWithinRange(i2, 177, 179) || isWithinRange(i2, 193, 195);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isValidTunerDeviceInfo(byte[] bArr) {
        int i = bArr[0] & Byte.MAX_VALUE;
        if (i == 0) {
            if (bArr.length >= 5) {
                return isValidDigitalServiceIdentification(bArr, 1);
            }
        } else {
            if (i == 1) {
                return true;
            }
            return i == 2 && bArr.length >= 5 && isValidAnalogueBroadcastType(bArr[1]) && isValidAnalogueFrequency(com.android.server.hdmi.HdmiUtils.twoBytesToInt(bArr, 2)) && isValidBroadcastSystem(bArr[4]);
        }
        return false;
    }

    private static class PhysicalAddressValidator implements com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator {
        private PhysicalAddressValidator() {
        }

        @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
        public int isValid(byte[] bArr) {
            if (bArr.length < 2) {
                return 4;
            }
            return com.android.server.hdmi.HdmiCecMessageValidator.toErrorCode(com.android.server.hdmi.HdmiCecMessageValidator.isValidPhysicalAddress(bArr, 0));
        }
    }

    private static class SystemAudioModeRequestValidator extends com.android.server.hdmi.HdmiCecMessageValidator.PhysicalAddressValidator {
        private SystemAudioModeRequestValidator() {
            super();
        }

        @Override // com.android.server.hdmi.HdmiCecMessageValidator.PhysicalAddressValidator, com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
        public int isValid(byte[] bArr) {
            if (bArr.length == 0) {
                return 0;
            }
            return super.isValid(bArr);
        }
    }

    private static class ReportPhysicalAddressValidator implements com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator {
        private ReportPhysicalAddressValidator() {
        }

        @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
        public int isValid(byte[] bArr) {
            if (bArr.length < 3) {
                return 4;
            }
            boolean z = false;
            if (com.android.server.hdmi.HdmiCecMessageValidator.isValidPhysicalAddress(bArr, 0) && com.android.server.hdmi.HdmiCecMessageValidator.isValidType(bArr[2])) {
                z = true;
            }
            return com.android.server.hdmi.HdmiCecMessageValidator.toErrorCode(z);
        }
    }

    private static class RoutingChangeValidator implements com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator {
        private RoutingChangeValidator() {
        }

        @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
        public int isValid(byte[] bArr) {
            if (bArr.length < 4) {
                return 4;
            }
            boolean z = false;
            if (com.android.server.hdmi.HdmiCecMessageValidator.isValidPhysicalAddress(bArr, 0) && com.android.server.hdmi.HdmiCecMessageValidator.isValidPhysicalAddress(bArr, 2)) {
                z = true;
            }
            return com.android.server.hdmi.HdmiCecMessageValidator.toErrorCode(z);
        }
    }

    private static class RecordStatusInfoValidator implements com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator {
        private RecordStatusInfoValidator() {
        }

        @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
        public int isValid(byte[] bArr) {
            boolean z = true;
            if (bArr.length < 1) {
                return 4;
            }
            if (!com.android.server.hdmi.HdmiCecMessageValidator.isWithinRange(bArr[0], 1, 7) && !com.android.server.hdmi.HdmiCecMessageValidator.isWithinRange(bArr[0], 9, 14) && !com.android.server.hdmi.HdmiCecMessageValidator.isWithinRange(bArr[0], 16, 23) && !com.android.server.hdmi.HdmiCecMessageValidator.isWithinRange(bArr[0], 26, 27) && bArr[0] != 31) {
                z = false;
            }
            return com.android.server.hdmi.HdmiCecMessageValidator.toErrorCode(z);
        }
    }

    private static class AsciiValidator implements com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator {
        private final int mMaxLength;
        private final int mMinLength;

        AsciiValidator(int i) {
            this.mMinLength = i;
            this.mMaxLength = i;
        }

        AsciiValidator(int i, int i2) {
            this.mMinLength = i;
            this.mMaxLength = i2;
        }

        @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
        public int isValid(byte[] bArr) {
            if (bArr.length < this.mMinLength) {
                return 4;
            }
            return com.android.server.hdmi.HdmiCecMessageValidator.toErrorCode(com.android.server.hdmi.HdmiCecMessageValidator.isValidAsciiString(bArr, 0, this.mMaxLength));
        }
    }

    private static class OsdStringValidator implements com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator {
        private OsdStringValidator() {
        }

        @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
        public int isValid(byte[] bArr) {
            if (bArr.length < 2) {
                return 4;
            }
            boolean z = false;
            if (com.android.server.hdmi.HdmiCecMessageValidator.isValidDisplayControl(bArr[0]) && com.android.server.hdmi.HdmiCecMessageValidator.isValidAsciiString(bArr, 1, 14)) {
                z = true;
            }
            return com.android.server.hdmi.HdmiCecMessageValidator.toErrorCode(z);
        }
    }

    private static class MinimumOneByteRangeValidator implements com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator {
        private final int mMaxValue;
        private final int mMinValue;

        MinimumOneByteRangeValidator(int i, int i2) {
            this.mMinValue = i;
            this.mMaxValue = i2;
        }

        @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
        public int isValid(byte[] bArr) {
            if (bArr.length < 1) {
                return 4;
            }
            return com.android.server.hdmi.HdmiCecMessageValidator.toErrorCode(com.android.server.hdmi.HdmiCecMessageValidator.isWithinRange(bArr[0], this.mMinValue, this.mMaxValue));
        }
    }

    private static class SingleByteRangeValidator implements com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator {
        private final int mMaxValue;
        private final int mMinValue;

        SingleByteRangeValidator(int i, int i2) {
            this.mMinValue = i;
            this.mMaxValue = i2;
        }

        @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
        public int isValid(byte[] bArr) {
            if (bArr.length < 1) {
                return 4;
            }
            if (bArr.length > 1) {
                return 5;
            }
            return com.android.server.hdmi.HdmiCecMessageValidator.toErrorCode(com.android.server.hdmi.HdmiCecMessageValidator.isWithinRange(bArr[0], this.mMinValue, this.mMaxValue));
        }
    }

    private static class AnalogueTimerValidator implements com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator {
        private AnalogueTimerValidator() {
        }

        @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
        public int isValid(byte[] bArr) {
            if (bArr.length < 11) {
                return 4;
            }
            boolean z = false;
            if (com.android.server.hdmi.HdmiCecMessageValidator.isValidDayOfMonth(bArr[0]) && com.android.server.hdmi.HdmiCecMessageValidator.isValidMonthOfYear(bArr[1]) && com.android.server.hdmi.HdmiCecMessageValidator.isValidHour(bArr[2]) && com.android.server.hdmi.HdmiCecMessageValidator.isValidMinute(bArr[3]) && com.android.server.hdmi.HdmiCecMessageValidator.isValidDurationHours(bArr[4]) && com.android.server.hdmi.HdmiCecMessageValidator.isValidMinute(bArr[5]) && com.android.server.hdmi.HdmiCecMessageValidator.isValidRecordingSequence(bArr[6]) && com.android.server.hdmi.HdmiCecMessageValidator.isValidAnalogueBroadcastType(bArr[7]) && com.android.server.hdmi.HdmiCecMessageValidator.isValidAnalogueFrequency(com.android.server.hdmi.HdmiUtils.twoBytesToInt(bArr, 8)) && com.android.server.hdmi.HdmiCecMessageValidator.isValidBroadcastSystem(bArr[10])) {
                z = true;
            }
            return com.android.server.hdmi.HdmiCecMessageValidator.toErrorCode(z);
        }
    }

    private static class DigitalTimerValidator implements com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator {
        private DigitalTimerValidator() {
        }

        @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
        public int isValid(byte[] bArr) {
            if (bArr.length < 11) {
                return 4;
            }
            boolean z = false;
            if (com.android.server.hdmi.HdmiCecMessageValidator.isValidDayOfMonth(bArr[0]) && com.android.server.hdmi.HdmiCecMessageValidator.isValidMonthOfYear(bArr[1]) && com.android.server.hdmi.HdmiCecMessageValidator.isValidHour(bArr[2]) && com.android.server.hdmi.HdmiCecMessageValidator.isValidMinute(bArr[3]) && com.android.server.hdmi.HdmiCecMessageValidator.isValidDurationHours(bArr[4]) && com.android.server.hdmi.HdmiCecMessageValidator.isValidMinute(bArr[5]) && com.android.server.hdmi.HdmiCecMessageValidator.isValidRecordingSequence(bArr[6]) && com.android.server.hdmi.HdmiCecMessageValidator.isValidDigitalServiceIdentification(bArr, 7)) {
                z = true;
            }
            return com.android.server.hdmi.HdmiCecMessageValidator.toErrorCode(z);
        }
    }

    private static class ExternalTimerValidator implements com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator {
        private ExternalTimerValidator() {
        }

        @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
        public int isValid(byte[] bArr) {
            if (bArr.length < 9) {
                return 4;
            }
            boolean z = false;
            if (com.android.server.hdmi.HdmiCecMessageValidator.isValidDayOfMonth(bArr[0]) && com.android.server.hdmi.HdmiCecMessageValidator.isValidMonthOfYear(bArr[1]) && com.android.server.hdmi.HdmiCecMessageValidator.isValidHour(bArr[2]) && com.android.server.hdmi.HdmiCecMessageValidator.isValidMinute(bArr[3]) && com.android.server.hdmi.HdmiCecMessageValidator.isValidDurationHours(bArr[4]) && com.android.server.hdmi.HdmiCecMessageValidator.isValidMinute(bArr[5]) && com.android.server.hdmi.HdmiCecMessageValidator.isValidRecordingSequence(bArr[6]) && com.android.server.hdmi.HdmiCecMessageValidator.isValidExternalSource(bArr, 7)) {
                z = true;
            }
            return com.android.server.hdmi.HdmiCecMessageValidator.toErrorCode(z);
        }
    }

    private static class TimerClearedStatusValidator implements com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator {
        private TimerClearedStatusValidator() {
        }

        @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
        public int isValid(byte[] bArr) {
            boolean z = true;
            if (bArr.length < 1) {
                return 4;
            }
            if (!com.android.server.hdmi.HdmiCecMessageValidator.isWithinRange(bArr[0], 0, 2) && (bArr[0] & android.hardware.tv.hdmi.cec.CecDeviceType.INACTIVE) != 128) {
                z = false;
            }
            return com.android.server.hdmi.HdmiCecMessageValidator.toErrorCode(z);
        }
    }

    private static class TimerStatusValidator implements com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator {
        private TimerStatusValidator() {
        }

        @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
        public int isValid(byte[] bArr) {
            if (bArr.length < 1) {
                return 4;
            }
            return com.android.server.hdmi.HdmiCecMessageValidator.toErrorCode(com.android.server.hdmi.HdmiCecMessageValidator.isValidTimerStatusData(bArr, 0));
        }
    }

    private static class PlayModeValidator implements com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator {
        private PlayModeValidator() {
        }

        @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
        public int isValid(byte[] bArr) {
            if (bArr.length < 1) {
                return 4;
            }
            return com.android.server.hdmi.HdmiCecMessageValidator.toErrorCode(com.android.server.hdmi.HdmiCecMessageValidator.isValidPlayMode(bArr[0]));
        }
    }

    private static class SelectAnalogueServiceValidator implements com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator {
        private SelectAnalogueServiceValidator() {
        }

        @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
        public int isValid(byte[] bArr) {
            if (bArr.length < 4) {
                return 4;
            }
            boolean z = false;
            if (com.android.server.hdmi.HdmiCecMessageValidator.isValidAnalogueBroadcastType(bArr[0]) && com.android.server.hdmi.HdmiCecMessageValidator.isValidAnalogueFrequency(com.android.server.hdmi.HdmiUtils.twoBytesToInt(bArr, 1)) && com.android.server.hdmi.HdmiCecMessageValidator.isValidBroadcastSystem(bArr[3])) {
                z = true;
            }
            return com.android.server.hdmi.HdmiCecMessageValidator.toErrorCode(z);
        }
    }

    private static class SelectDigitalServiceValidator implements com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator {
        private SelectDigitalServiceValidator() {
        }

        @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
        public int isValid(byte[] bArr) {
            if (bArr.length < 4) {
                return 4;
            }
            return com.android.server.hdmi.HdmiCecMessageValidator.toErrorCode(com.android.server.hdmi.HdmiCecMessageValidator.isValidDigitalServiceIdentification(bArr, 0));
        }
    }

    private static class TunerDeviceStatusValidator implements com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator {
        private TunerDeviceStatusValidator() {
        }

        @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
        public int isValid(byte[] bArr) {
            if (bArr.length < 1) {
                return 4;
            }
            return com.android.server.hdmi.HdmiCecMessageValidator.toErrorCode(com.android.server.hdmi.HdmiCecMessageValidator.isValidTunerDeviceInfo(bArr));
        }
    }

    private static class UserControlPressedValidator implements com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator {
        private UserControlPressedValidator() {
        }

        @Override // com.android.server.hdmi.HdmiCecMessageValidator.ParameterValidator
        public int isValid(byte[] bArr) {
            if (bArr.length < 1) {
                return 4;
            }
            if (bArr.length == 1) {
                return 0;
            }
            switch (bArr[0]) {
                case 86:
                    return com.android.server.hdmi.HdmiCecMessageValidator.toErrorCode(com.android.server.hdmi.HdmiCecMessageValidator.isValidUiBroadcastType(bArr[1]));
                case 87:
                    return com.android.server.hdmi.HdmiCecMessageValidator.toErrorCode(com.android.server.hdmi.HdmiCecMessageValidator.isValidUiSoundPresenationControl(bArr[1]));
                case 96:
                    return com.android.server.hdmi.HdmiCecMessageValidator.toErrorCode(com.android.server.hdmi.HdmiCecMessageValidator.isValidPlayMode(bArr[1]));
                case 103:
                    if (bArr.length >= 4) {
                        return com.android.server.hdmi.HdmiCecMessageValidator.toErrorCode(com.android.server.hdmi.HdmiCecMessageValidator.isValidChannelIdentifier(bArr, 1));
                    }
                    return 4;
                default:
                    return 0;
            }
        }
    }
}
