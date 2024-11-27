package android.security;

/* loaded from: classes3.dex */
public class LegacyVpnProfileStore {
    private static final java.lang.String LEGACY_KEYSTORE_SERVICE_NAME = "android.security.legacykeystore";
    public static final int PROFILE_NOT_FOUND = 7;
    public static final int SYSTEM_ERROR = 4;
    private static final java.lang.String TAG = "LegacyVpnProfileStore";

    private static android.security.legacykeystore.ILegacyKeystore getService() {
        return android.security.legacykeystore.ILegacyKeystore.Stub.asInterface(android.os.ServiceManager.checkService(LEGACY_KEYSTORE_SERVICE_NAME));
    }

    public static boolean put(java.lang.String str, byte[] bArr) {
        android.os.StrictMode.noteDiskWrite();
        try {
            getService().put(str, -1, bArr);
            return true;
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Failed to put vpn profile.", e);
            return false;
        }
    }

    public static byte[] get(java.lang.String str) {
        android.os.StrictMode.noteDiskRead();
        try {
            return getService().get(str, -1);
        } catch (android.os.ServiceSpecificException e) {
            if (e.errorCode != 7) {
                android.util.Log.e(TAG, "Failed to get vpn profile.", e);
                return null;
            }
            return null;
        } catch (java.lang.Exception e2) {
            android.util.Log.e(TAG, "Failed to get vpn profile.", e2);
            return null;
        }
    }

    public static boolean remove(java.lang.String str) {
        android.os.StrictMode.noteDiskWrite();
        try {
            getService().remove(str, -1);
            return true;
        } catch (android.os.ServiceSpecificException e) {
            if (e.errorCode != 7) {
                android.util.Log.e(TAG, "Failed to remove vpn profile.", e);
                return false;
            }
            return false;
        } catch (java.lang.Exception e2) {
            android.util.Log.e(TAG, "Failed to remove vpn profile.", e2);
            return false;
        }
    }

    public static java.lang.String[] list(java.lang.String str) {
        android.os.StrictMode.noteDiskRead();
        try {
            java.lang.String[] list = getService().list(str, -1);
            for (int i = 0; i < list.length; i++) {
                list[i] = list[i].substring(str.length());
            }
            return list;
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Failed to list vpn profiles.", e);
            return new java.lang.String[0];
        }
    }
}
