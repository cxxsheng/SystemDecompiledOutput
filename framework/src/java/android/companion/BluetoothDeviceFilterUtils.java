package android.companion;

/* loaded from: classes.dex */
public class BluetoothDeviceFilterUtils {
    private static final boolean DEBUG = false;
    private static final java.lang.String LOG_TAG = "CDM_BluetoothDeviceFilterUtils";

    private BluetoothDeviceFilterUtils() {
    }

    static java.lang.String patternToString(java.util.regex.Pattern pattern) {
        if (pattern == null) {
            return null;
        }
        return pattern.pattern();
    }

    static java.util.regex.Pattern patternFromString(java.lang.String str) {
        if (str == null) {
            return null;
        }
        return java.util.regex.Pattern.compile(str);
    }

    static boolean matchesAddress(java.lang.String str, android.bluetooth.BluetoothDevice bluetoothDevice) {
        return str == null || (bluetoothDevice != null && str.equals(bluetoothDevice.getAddress()));
    }

    static boolean matchesServiceUuids(java.util.List<android.os.ParcelUuid> list, java.util.List<android.os.ParcelUuid> list2, android.bluetooth.BluetoothDevice bluetoothDevice) {
        for (int i = 0; i < list.size(); i++) {
            if (!matchesServiceUuid(list.get(i), list2.get(i), bluetoothDevice)) {
                return false;
            }
        }
        return true;
    }

    static boolean matchesServiceUuid(android.os.ParcelUuid parcelUuid, android.os.ParcelUuid parcelUuid2, android.bluetooth.BluetoothDevice bluetoothDevice) {
        java.util.List emptyList = bluetoothDevice.getUuids() == null ? java.util.Collections.emptyList() : java.util.Arrays.asList(bluetoothDevice.getUuids());
        if (parcelUuid == null) {
            return true;
        }
        java.util.Iterator it = emptyList.iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (uuidsMaskedEquals(((android.os.ParcelUuid) it.next()).getUuid(), parcelUuid.getUuid(), parcelUuid2 == null ? null : parcelUuid2.getUuid())) {
                z = true;
            }
        }
        return z;
    }

    static boolean matchesName(java.util.regex.Pattern pattern, android.bluetooth.BluetoothDevice bluetoothDevice) {
        if (pattern == null) {
            return true;
        }
        if (bluetoothDevice == null) {
            return false;
        }
        java.lang.String name = bluetoothDevice.getName();
        if (name != null && pattern.matcher(name).find()) {
            return true;
        }
        return false;
    }

    static boolean matchesName(java.util.regex.Pattern pattern, android.net.wifi.ScanResult scanResult) {
        if (pattern == null) {
            return true;
        }
        if (scanResult == null) {
            return false;
        }
        java.lang.String str = scanResult.SSID;
        if (str != null && pattern.matcher(str).find()) {
            return true;
        }
        return false;
    }

    private static void debugLogMatchResult(boolean z, android.bluetooth.BluetoothDevice bluetoothDevice, java.lang.Object obj) {
        android.util.Log.i(LOG_TAG, getDeviceDisplayNameInternal(bluetoothDevice) + (z ? " ~ " : " !~ ") + obj);
    }

    private static void debugLogMatchResult(boolean z, android.net.wifi.ScanResult scanResult, java.lang.Object obj) {
        android.util.Log.i(LOG_TAG, getDeviceDisplayNameInternal(scanResult) + (z ? " ~ " : " !~ ") + obj);
    }

    public static java.lang.String getDeviceDisplayNameInternal(android.bluetooth.BluetoothDevice bluetoothDevice) {
        return android.text.TextUtils.firstNotEmpty(bluetoothDevice.getAlias(), bluetoothDevice.getAddress());
    }

    public static java.lang.String getDeviceDisplayNameInternal(android.net.wifi.ScanResult scanResult) {
        return android.text.TextUtils.firstNotEmpty(scanResult.SSID, scanResult.BSSID);
    }

    public static java.lang.String getDeviceMacAddress(android.os.Parcelable parcelable) {
        if (parcelable instanceof android.bluetooth.BluetoothDevice) {
            return ((android.bluetooth.BluetoothDevice) parcelable).getAddress();
        }
        if (parcelable instanceof android.net.wifi.ScanResult) {
            return ((android.net.wifi.ScanResult) parcelable).BSSID;
        }
        if (parcelable instanceof android.bluetooth.le.ScanResult) {
            return getDeviceMacAddress(((android.bluetooth.le.ScanResult) parcelable).getDevice());
        }
        throw new java.lang.IllegalArgumentException("Unknown device type: " + parcelable);
    }

    public static boolean uuidsMaskedEquals(java.util.UUID uuid, java.util.UUID uuid2, java.util.UUID uuid3) {
        if (uuid3 == null) {
            return java.util.Objects.equals(uuid, uuid2);
        }
        return (uuid.getLeastSignificantBits() & uuid3.getLeastSignificantBits()) == (uuid2.getLeastSignificantBits() & uuid3.getLeastSignificantBits()) && (uuid.getMostSignificantBits() & uuid3.getMostSignificantBits()) == (uuid2.getMostSignificantBits() & uuid3.getMostSignificantBits());
    }
}
