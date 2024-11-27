package android.net.wifi;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class WifiKeystore {
    private static final java.lang.String LEGACY_KEYSTORE_SERVICE_NAME = "android.security.legacykeystore";
    private static final java.lang.String TAG = "WifiKeystore";

    private static android.security.legacykeystore.ILegacyKeystore getService() {
        return android.security.legacykeystore.ILegacyKeystore.Stub.asInterface(android.os.ServiceManager.checkService(LEGACY_KEYSTORE_SERVICE_NAME));
    }

    WifiKeystore() {
    }

    @android.annotation.SystemApi
    public static boolean put(java.lang.String str, byte[] bArr) {
        try {
            android.util.Log.i(TAG, "put blob. alias " + str);
            getService().put(str, 1010, bArr);
            return true;
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Failed to put blob.", e);
            return false;
        }
    }

    @android.annotation.SystemApi
    public static byte[] get(java.lang.String str) {
        try {
            android.util.Log.i(TAG, "get blob. alias " + str);
            return getService().get(str, 1010);
        } catch (android.os.ServiceSpecificException e) {
            if (e.errorCode != 7) {
                android.util.Log.e(TAG, "Failed to get blob.", e);
                return null;
            }
            return null;
        } catch (java.lang.Exception e2) {
            android.util.Log.e(TAG, "Failed to get blob.", e2);
            return null;
        }
    }

    @android.annotation.SystemApi
    public static boolean remove(java.lang.String str) {
        try {
            getService().remove(str, 1010);
            return true;
        } catch (android.os.ServiceSpecificException e) {
            if (e.errorCode != 7) {
                android.util.Log.e(TAG, "Failed to remove blob.", e);
                return false;
            }
            return false;
        } catch (java.lang.Exception e2) {
            android.util.Log.e(TAG, "Failed to remove blob.", e2);
            return false;
        }
    }

    @android.annotation.SystemApi
    public static java.lang.String[] list(java.lang.String str) {
        try {
            java.lang.String[] list = getService().list(str, 1010);
            for (int i = 0; i < list.length; i++) {
                list[i] = list[i].substring(str.length());
            }
            return list;
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Failed to list blobs.", e);
            return new java.lang.String[0];
        }
    }
}
