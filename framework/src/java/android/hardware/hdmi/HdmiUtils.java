package android.hardware.hdmi;

/* loaded from: classes2.dex */
public final class HdmiUtils {
    public static final int HDMI_RELATIVE_POSITION_ABOVE = 5;
    public static final int HDMI_RELATIVE_POSITION_BELOW = 2;
    public static final int HDMI_RELATIVE_POSITION_DIFFERENT_BRANCH = 7;
    public static final int HDMI_RELATIVE_POSITION_DIRECTLY_ABOVE = 4;
    public static final int HDMI_RELATIVE_POSITION_DIRECTLY_BELOW = 1;
    public static final int HDMI_RELATIVE_POSITION_SAME = 3;
    public static final int HDMI_RELATIVE_POSITION_SIBLING = 6;
    public static final int HDMI_RELATIVE_POSITION_UNKNOWN = 0;
    private static final int NPOS = -1;
    static final int TARGET_NOT_UNDER_LOCAL_DEVICE = -1;
    static final int TARGET_SAME_PHYSICAL_ADDRESS = 0;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface HdmiAddressRelativePosition {
    }

    private HdmiUtils() {
    }

    public static int getLocalPortFromPhysicalAddress(int i, int i2) {
        if (i2 == i) {
            return 0;
        }
        int i3 = 61440;
        int i4 = i2;
        int i5 = 61440;
        while (i4 != 0) {
            i4 = i2 & i5;
            i3 |= i5;
            i5 >>= 4;
        }
        int i6 = i & i3;
        if (((i3 << 4) & i6) != i2) {
            return -1;
        }
        int i7 = i6 & (i5 << 4);
        while (true) {
            int i8 = i7 >> 4;
            if (i8 != 0) {
                i7 = i8;
            } else {
                return i7;
            }
        }
    }

    public static boolean isValidPhysicalAddress(int i) {
        if (i < 0 || i >= 65535) {
            return false;
        }
        int i2 = 61440;
        boolean z = false;
        for (int i3 = 0; i3 < 4; i3++) {
            if ((i & i2) == 0) {
                z = true;
            } else if (z) {
                return false;
            }
            i2 >>= 4;
        }
        return true;
    }

    public static int getHdmiAddressRelativePosition(int i, int i2) {
        if (i == 65535 || i2 == 65535) {
            return 0;
        }
        try {
            int physicalAddressFirstDifferentDigitPos = physicalAddressFirstDifferentDigitPos(i, i2);
            if (physicalAddressFirstDifferentDigitPos == -1) {
                return 3;
            }
            int i3 = 61440 >> (physicalAddressFirstDifferentDigitPos * 4);
            int i4 = physicalAddressFirstDifferentDigitPos + 1;
            if ((i & i3) == 0) {
                return (i4 == 4 || ((61440 >> (i4 * 4)) & i2) == 0) ? 4 : 5;
            }
            if ((i3 & i2) == 0) {
                if (i4 == 4 || (i & (61440 >> (i4 * 4))) == 0) {
                    return 1;
                }
                return 2;
            }
            if (i4 == 4) {
                return 6;
            }
            int i5 = 61440 >> (i4 * 4);
            if ((i & i5) == 0 && (i5 & i2) == 0) {
                return 6;
            }
            return 7;
        } catch (java.lang.IllegalArgumentException e) {
            return 0;
        }
    }

    private static int physicalAddressFirstDifferentDigitPos(int i, int i2) throws java.lang.IllegalArgumentException {
        if (!isValidPhysicalAddress(i)) {
            throw new java.lang.IllegalArgumentException(i + " is not a valid address.");
        }
        if (!isValidPhysicalAddress(i2)) {
            throw new java.lang.IllegalArgumentException(i2 + " is not a valid address.");
        }
        int i3 = 61440;
        for (int i4 = 0; i4 < 4; i4++) {
            if ((i & i3) != (i2 & i3)) {
                return i4;
            }
            i3 >>= 4;
        }
        return -1;
    }
}
