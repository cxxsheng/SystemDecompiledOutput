package android.hardware.usb;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class UsbPort {
    public static final int ENABLE_LIMIT_POWER_TRANSFER_ERROR_INTERNAL = 1;
    public static final int ENABLE_LIMIT_POWER_TRANSFER_ERROR_NOT_SUPPORTED = 2;
    public static final int ENABLE_LIMIT_POWER_TRANSFER_ERROR_OTHER = 4;
    public static final int ENABLE_LIMIT_POWER_TRANSFER_ERROR_PORT_MISMATCH = 3;
    public static final int ENABLE_LIMIT_POWER_TRANSFER_SUCCESS = 0;
    public static final int ENABLE_USB_DATA_ERROR_INTERNAL = 1;
    public static final int ENABLE_USB_DATA_ERROR_NOT_SUPPORTED = 2;
    public static final int ENABLE_USB_DATA_ERROR_OTHER = 4;
    public static final int ENABLE_USB_DATA_ERROR_PORT_MISMATCH = 3;
    public static final int ENABLE_USB_DATA_SUCCESS = 0;
    public static final int ENABLE_USB_DATA_WHILE_DOCKED_ERROR_DATA_ENABLED = 4;
    public static final int ENABLE_USB_DATA_WHILE_DOCKED_ERROR_INTERNAL = 1;
    public static final int ENABLE_USB_DATA_WHILE_DOCKED_ERROR_NOT_SUPPORTED = 2;
    public static final int ENABLE_USB_DATA_WHILE_DOCKED_ERROR_OTHER = 5;
    public static final int ENABLE_USB_DATA_WHILE_DOCKED_ERROR_PORT_MISMATCH = 3;
    public static final int ENABLE_USB_DATA_WHILE_DOCKED_SUCCESS = 0;
    public static final int FLAG_ALT_MODE_TYPE_DISPLAYPORT = 1;
    private static final int NUM_DATA_ROLES = 3;
    private static final int POWER_ROLE_OFFSET = 0;
    public static final int RESET_USB_PORT_ERROR_INTERNAL = 1;
    public static final int RESET_USB_PORT_ERROR_NOT_SUPPORTED = 2;
    public static final int RESET_USB_PORT_ERROR_OTHER = 4;
    public static final int RESET_USB_PORT_ERROR_PORT_MISMATCH = 3;
    public static final int RESET_USB_PORT_SUCCESS = 0;
    private static final java.lang.String TAG = "UsbPort";
    private static final java.util.concurrent.atomic.AtomicInteger sUsbOperationCount = new java.util.concurrent.atomic.AtomicInteger();
    private final java.lang.String mId;
    private final int mSupportedAltModes;
    private final int mSupportedContaminantProtectionModes;
    private final int mSupportedModes;
    private final boolean mSupportsComplianceWarnings;
    private final boolean mSupportsEnableContaminantPresenceDetection;
    private final boolean mSupportsEnableContaminantPresenceProtection;
    private final android.hardware.usb.UsbManager mUsbManager;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AltModeType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface EnableLimitPowerTransferStatus {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface EnableUsbDataStatus {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface EnableUsbDataWhileDockedStatus {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface ResetUsbPortStatus {
    }

    public UsbPort(android.hardware.usb.UsbManager usbManager, java.lang.String str, int i, int i2, boolean z, boolean z2) {
        this(usbManager, str, i, i2, z, z2, false, 0);
    }

    public UsbPort(android.hardware.usb.UsbManager usbManager, java.lang.String str, int i, int i2, boolean z, boolean z2, boolean z3, int i3) {
        java.util.Objects.requireNonNull(str);
        com.android.internal.util.Preconditions.checkFlagsArgument(i, 15);
        this.mUsbManager = usbManager;
        this.mId = str;
        this.mSupportedModes = i;
        this.mSupportedContaminantProtectionModes = i2;
        this.mSupportsEnableContaminantPresenceProtection = z;
        this.mSupportsEnableContaminantPresenceDetection = z2;
        this.mSupportsComplianceWarnings = z3;
        this.mSupportedAltModes = i3;
    }

    public java.lang.String getId() {
        return this.mId;
    }

    public int getSupportedModes() {
        return this.mSupportedModes;
    }

    public int getSupportedContaminantProtectionModes() {
        return this.mSupportedContaminantProtectionModes;
    }

    public boolean supportsEnableContaminantPresenceProtection() {
        return this.mSupportsEnableContaminantPresenceProtection;
    }

    public boolean supportsEnableContaminantPresenceDetection() {
        return this.mSupportsEnableContaminantPresenceDetection;
    }

    public android.hardware.usb.UsbPortStatus getStatus() {
        return this.mUsbManager.getPortStatus(this);
    }

    public boolean isModeChangeSupported() {
        return this.mUsbManager.isModeChangeSupported(this);
    }

    public boolean supportsComplianceWarnings() {
        return this.mSupportsComplianceWarnings;
    }

    public int getSupportedAltModesMask() {
        return this.mSupportedAltModes;
    }

    public boolean isAltModeSupported(int i) {
        return (this.mSupportedAltModes & i) == i;
    }

    public void setRoles(int i, int i2) {
        checkRoles(i, i2);
        this.mUsbManager.setPortRoles(this, i, i2);
    }

    public void resetUsbPort(java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Integer> consumer) {
        int incrementAndGet = sUsbOperationCount.incrementAndGet() + android.os.Binder.getCallingUid();
        android.util.Log.i(TAG, "resetUsbPort opId:" + incrementAndGet);
        this.mUsbManager.resetUsbPort(this, incrementAndGet, new android.hardware.usb.UsbOperationInternal(incrementAndGet, this.mId, executor, consumer));
    }

    public int enableUsbData(boolean z) {
        int incrementAndGet = sUsbOperationCount.incrementAndGet() + android.os.Binder.getCallingUid();
        android.util.Log.i(TAG, "enableUsbData opId:" + incrementAndGet + " callingUid:" + android.os.Binder.getCallingUid());
        android.hardware.usb.UsbOperationInternal usbOperationInternal = new android.hardware.usb.UsbOperationInternal(incrementAndGet, this.mId);
        if (this.mUsbManager.enableUsbData(this, z, incrementAndGet, usbOperationInternal)) {
            usbOperationInternal.waitForOperationComplete();
        }
        switch (usbOperationInternal.getStatus()) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            default:
                return 4;
        }
    }

    public int enableUsbDataWhileDocked() {
        int incrementAndGet = sUsbOperationCount.incrementAndGet() + android.os.Binder.getCallingUid();
        android.util.Log.i(TAG, "enableUsbData opId:" + incrementAndGet + " callingUid:" + android.os.Binder.getCallingUid());
        android.hardware.usb.UsbPortStatus status = getStatus();
        if (status != null && (status.getUsbDataStatus() & 8) != 8) {
            return 4;
        }
        android.hardware.usb.UsbOperationInternal usbOperationInternal = new android.hardware.usb.UsbOperationInternal(incrementAndGet, this.mId);
        this.mUsbManager.enableUsbDataWhileDocked(this, incrementAndGet, usbOperationInternal);
        usbOperationInternal.waitForOperationComplete();
        switch (usbOperationInternal.getStatus()) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            default:
                return 5;
        }
    }

    public int enableLimitPowerTransfer(boolean z) {
        int incrementAndGet = sUsbOperationCount.incrementAndGet() + android.os.Binder.getCallingUid();
        android.util.Log.i(TAG, "enableLimitPowerTransfer opId:" + incrementAndGet + " callingUid:" + android.os.Binder.getCallingUid());
        android.hardware.usb.UsbOperationInternal usbOperationInternal = new android.hardware.usb.UsbOperationInternal(incrementAndGet, this.mId);
        this.mUsbManager.enableLimitPowerTransfer(this, z, incrementAndGet, usbOperationInternal);
        usbOperationInternal.waitForOperationComplete();
        switch (usbOperationInternal.getStatus()) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            default:
                return 4;
        }
    }

    public void enableContaminantDetection(boolean z) {
        this.mUsbManager.enableContaminantDetection(this, z);
    }

    public static int combineRolesAsBit(int i, int i2) {
        checkRoles(i, i2);
        return 1 << (((i + 0) * 3) + i2);
    }

    public static java.lang.String modeToString(int i) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (i == 0) {
            return "none";
        }
        if ((i & 3) == 3) {
            sb.append("dual, ");
        } else if ((i & 2) == 2) {
            sb.append("dfp, ");
        } else if ((i & 1) == 1) {
            sb.append("ufp, ");
        }
        if ((i & 4) == 4) {
            sb.append("audio_acc, ");
        }
        if ((i & 8) == 8) {
            sb.append("debug_acc, ");
        }
        if (sb.length() == 0) {
            return java.lang.Integer.toString(i);
        }
        return sb.substring(0, sb.length() - 2);
    }

    public static java.lang.String powerRoleToString(int i) {
        switch (i) {
            case 0:
                return "no-power";
            case 1:
                return android.app.slice.Slice.SUBTYPE_SOURCE;
            case 2:
                return "sink";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    public static java.lang.String dataRoleToString(int i) {
        switch (i) {
            case 0:
                return "no-data";
            case 1:
                return "host";
            case 2:
                return android.hardware.usb.UsbManager.EXTRA_DEVICE;
            default:
                return java.lang.Integer.toString(i);
        }
    }

    public static java.lang.String contaminantPresenceStatusToString(int i) {
        switch (i) {
            case 0:
                return "not-supported";
            case 1:
                return "disabled";
            case 2:
                return "not detected";
            case 3:
                return "detected";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    public static java.lang.String usbDataStatusToString(int i) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (i == 0) {
            return "unknown";
        }
        if ((i & 1) == 1) {
            return "enabled";
        }
        if ((i & 2) == 2) {
            sb.append("disabled-overheat, ");
        }
        if ((i & 4) == 4) {
            sb.append("disabled-contaminant, ");
        }
        if ((i & 8) == 8) {
            sb.append("disabled-dock, ");
        }
        if ((i & 16) == 16) {
            sb.append("disabled-force, ");
        }
        if ((i & 32) == 32) {
            sb.append("disabled-debug, ");
        }
        if ((i & 64) == 64) {
            sb.append("disabled-host-dock, ");
        }
        if ((i & 128) == 128) {
            sb.append("disabled-device-dock, ");
        }
        return sb.toString().replaceAll(", $", "");
    }

    public static java.lang.String powerBrickConnectionStatusToString(int i) {
        switch (i) {
            case 0:
                return "unknown";
            case 1:
                return "connected";
            case 2:
                return "disconnected";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    public static java.lang.String roleCombinationsToString(int i) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
        boolean z = true;
        while (i != 0) {
            int numberOfTrailingZeros = java.lang.Integer.numberOfTrailingZeros(i);
            i &= ~(1 << numberOfTrailingZeros);
            int i2 = (numberOfTrailingZeros / 3) + 0;
            int i3 = numberOfTrailingZeros % 3;
            if (z) {
                z = false;
            } else {
                sb.append(", ");
            }
            sb.append(powerRoleToString(i2));
            sb.append(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR);
            sb.append(dataRoleToString(i3));
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    public static java.lang.String complianceWarningsToString(int[] iArr) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
        if (iArr != null) {
            for (int i : iArr) {
                switch (i) {
                    case 1:
                        sb.append("other, ");
                        break;
                    case 2:
                        sb.append("debug accessory, ");
                        break;
                    case 3:
                        sb.append("bc12, ");
                        break;
                    case 4:
                        sb.append("missing rp, ");
                        break;
                    case 5:
                        sb.append("input power limited, ");
                        break;
                    case 6:
                        sb.append("missing data lines, ");
                        break;
                    case 7:
                        sb.append("enumeration fail, ");
                        break;
                    case 8:
                        sb.append("flaky connection, ");
                        break;
                    case 9:
                        sb.append("unreliable io, ");
                        break;
                    default:
                        sb.append(java.lang.String.format("Unknown(%d), ", java.lang.Integer.valueOf(i)));
                        break;
                }
            }
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString().replaceAll(", ]$", android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
    }

    public static java.lang.String dpAltModeStatusToString(int i) {
        switch (i) {
            case 0:
                return "Unknown";
            case 1:
                return "Not Capable";
            case 2:
                return "Capable-Disabled";
            case 3:
                return "Enabled";
            default:
                return java.lang.Integer.toString(i);
        }
    }

    public static void checkMode(int i) {
        com.android.internal.util.Preconditions.checkArgumentInRange(i, 0, 3, "portMode");
    }

    public static void checkPowerRole(int i) {
        com.android.internal.util.Preconditions.checkArgumentInRange(i, 0, 2, "powerRole");
    }

    public static void checkDataRole(int i) {
        com.android.internal.util.Preconditions.checkArgumentInRange(i, 0, 2, "powerRole");
    }

    public static void checkRoles(int i, int i2) {
        com.android.internal.util.Preconditions.checkArgumentInRange(i, 0, 2, "powerRole");
        com.android.internal.util.Preconditions.checkArgumentInRange(i2, 0, 2, "dataRole");
    }

    public boolean isModeSupported(int i) {
        return (this.mSupportedModes & i) == i;
    }

    public java.lang.String toString() {
        return "UsbPort{id=" + this.mId + ", supportedModes=" + modeToString(this.mSupportedModes) + ", supportedContaminantProtectionModes=" + this.mSupportedContaminantProtectionModes + ", supportsEnableContaminantPresenceProtection=" + this.mSupportsEnableContaminantPresenceProtection + ", supportsEnableContaminantPresenceDetection=" + this.mSupportsEnableContaminantPresenceDetection + ", supportsComplianceWarnings=" + this.mSupportsComplianceWarnings;
    }
}
