package com.android.server.hdmi;

/* loaded from: classes2.dex */
public class ReportFeaturesMessage extends com.android.server.hdmi.HdmiCecMessage {
    private final int mCecVersion;

    @android.annotation.NonNull
    private final android.hardware.hdmi.DeviceFeatures mDeviceFeatures;

    private ReportFeaturesMessage(int i, int i2, byte[] bArr, int i3, android.hardware.hdmi.DeviceFeatures deviceFeatures) {
        super(i, i2, 166, bArr, 0);
        this.mCecVersion = i3;
        this.mDeviceFeatures = deviceFeatures;
    }

    public static com.android.server.hdmi.HdmiCecMessage build(int i, int i2, java.util.List<java.lang.Integer> list, @com.android.server.hdmi.Constants.RcProfile int i3, java.util.List<java.lang.Integer> list2, android.hardware.hdmi.DeviceFeatures deviceFeatures) {
        byte b = (byte) (i2 & 255);
        java.util.Iterator<java.lang.Integer> it = list.iterator();
        byte b2 = 0;
        while (it.hasNext()) {
            b2 = (byte) (b2 | ((byte) (1 << hdmiDeviceInfoDeviceTypeToShiftValue(it.next().intValue()))));
        }
        byte b3 = (byte) (((byte) (i3 << 6)) | 0);
        if (i3 == 1) {
            java.util.Iterator<java.lang.Integer> it2 = list2.iterator();
            while (it2.hasNext()) {
                b3 = (byte) (b3 | ((byte) (1 << it2.next().intValue())));
            }
        } else {
            b3 = (byte) (b3 | ((byte) (list2.get(0).intValue() & com.android.server.location.gnss.hal.GnssNative.GNSS_AIDING_TYPE_ALL)));
        }
        byte[] bArr = {b, b2, b3};
        byte[] operand = deviceFeatures.toOperand();
        byte[] copyOf = java.util.Arrays.copyOf(bArr, operand.length + 3);
        java.lang.System.arraycopy(operand, 0, copyOf, 3, operand.length);
        int validateAddress = validateAddress(i, 15);
        if (validateAddress != 0) {
            return new com.android.server.hdmi.HdmiCecMessage(i, 15, 166, copyOf, validateAddress);
        }
        return new com.android.server.hdmi.ReportFeaturesMessage(i, 15, copyOf, i2, deviceFeatures);
    }

    @com.android.server.hdmi.Constants.DeviceType
    private static int hdmiDeviceInfoDeviceTypeToShiftValue(int i) {
        switch (i) {
            case 0:
                return 7;
            case 1:
                return 6;
            case 2:
            default:
                throw new java.lang.IllegalArgumentException("Unhandled device type: " + i);
            case 3:
                return 5;
            case 4:
                return 4;
            case 5:
                return 3;
            case 6:
                return 2;
        }
    }

    static com.android.server.hdmi.HdmiCecMessage build(final int i, final int i2, final byte[] bArr) {
        java.util.function.Function function = new java.util.function.Function() { // from class: com.android.server.hdmi.ReportFeaturesMessage$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                com.android.server.hdmi.HdmiCecMessage lambda$build$0;
                lambda$build$0 = com.android.server.hdmi.ReportFeaturesMessage.lambda$build$0(i, i2, bArr, (java.lang.Integer) obj);
                return lambda$build$0;
            }
        };
        int validateAddress = validateAddress(i, i2);
        if (validateAddress != 0) {
            return (com.android.server.hdmi.HdmiCecMessage) function.apply(java.lang.Integer.valueOf(validateAddress));
        }
        if (bArr.length < 4) {
            return (com.android.server.hdmi.HdmiCecMessage) function.apply(4);
        }
        int unsignedInt = java.lang.Byte.toUnsignedInt(bArr[0]);
        int endOfSequence = com.android.server.hdmi.HdmiUtils.getEndOfSequence(bArr, 2);
        if (endOfSequence == -1) {
            return (com.android.server.hdmi.HdmiCecMessage) function.apply(4);
        }
        if (com.android.server.hdmi.HdmiUtils.getEndOfSequence(bArr, endOfSequence + 1) == -1) {
            return (com.android.server.hdmi.HdmiCecMessage) function.apply(4);
        }
        return new com.android.server.hdmi.ReportFeaturesMessage(i, i2, bArr, unsignedInt, android.hardware.hdmi.DeviceFeatures.fromOperand(java.util.Arrays.copyOfRange(bArr, com.android.server.hdmi.HdmiUtils.getEndOfSequence(bArr, 2) + 1, bArr.length)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.server.hdmi.HdmiCecMessage lambda$build$0(int i, int i2, byte[] bArr, java.lang.Integer num) {
        return new com.android.server.hdmi.HdmiCecMessage(i, i2, 166, bArr, num.intValue());
    }

    public static int validateAddress(int i, int i2) {
        return com.android.server.hdmi.HdmiCecMessageValidator.validateAddress(i, i2, 32767, 32768);
    }

    public int getCecVersion() {
        return this.mCecVersion;
    }

    public android.hardware.hdmi.DeviceFeatures getDeviceFeatures() {
        return this.mDeviceFeatures;
    }
}
