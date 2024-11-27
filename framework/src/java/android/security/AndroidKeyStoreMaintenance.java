package android.security;

/* loaded from: classes3.dex */
public class AndroidKeyStoreMaintenance {
    public static final int INVALID_ARGUMENT = 20;
    public static final int KEY_NOT_FOUND = 7;
    public static final int PERMISSION_DENIED = 6;
    public static final int SYSTEM_ERROR = 4;
    private static final java.lang.String TAG = "AndroidKeyStoreMaintenance";

    private static android.security.maintenance.IKeystoreMaintenance getService() {
        return android.security.maintenance.IKeystoreMaintenance.Stub.asInterface(android.os.ServiceManager.checkService("android.security.maintenance"));
    }

    public static int onUserAdded(int i) {
        android.os.StrictMode.noteDiskWrite();
        try {
            getService().onUserAdded(i);
            return 0;
        } catch (android.os.ServiceSpecificException e) {
            android.util.Log.e(TAG, "onUserAdded failed", e);
            return e.errorCode;
        } catch (java.lang.Exception e2) {
            android.util.Log.e(TAG, "Can not connect to keystore", e2);
            return 4;
        }
    }

    public static int initUserSuperKeys(int i, byte[] bArr, boolean z) {
        android.os.StrictMode.noteDiskWrite();
        try {
            getService().initUserSuperKeys(i, bArr, z);
            return 0;
        } catch (android.os.ServiceSpecificException e) {
            android.util.Log.e(TAG, "initUserSuperKeys failed", e);
            return e.errorCode;
        } catch (java.lang.Exception e2) {
            android.util.Log.e(TAG, "Can not connect to keystore", e2);
            return 4;
        }
    }

    public static int onUserRemoved(int i) {
        android.os.StrictMode.noteDiskWrite();
        try {
            getService().onUserRemoved(i);
            return 0;
        } catch (android.os.ServiceSpecificException e) {
            android.util.Log.e(TAG, "onUserRemoved failed", e);
            return e.errorCode;
        } catch (java.lang.Exception e2) {
            android.util.Log.e(TAG, "Can not connect to keystore", e2);
            return 4;
        }
    }

    public static int onUserPasswordChanged(int i, byte[] bArr) {
        android.os.StrictMode.noteDiskWrite();
        try {
            getService().onUserPasswordChanged(i, bArr);
            return 0;
        } catch (android.os.ServiceSpecificException e) {
            android.util.Log.e(TAG, "onUserPasswordChanged failed", e);
            return e.errorCode;
        } catch (java.lang.Exception e2) {
            android.util.Log.e(TAG, "Can not connect to keystore", e2);
            return 4;
        }
    }

    public static int onUserLskfRemoved(int i) {
        android.os.StrictMode.noteDiskWrite();
        try {
            getService().onUserLskfRemoved(i);
            return 0;
        } catch (android.os.ServiceSpecificException e) {
            android.util.Log.e(TAG, "onUserLskfRemoved failed", e);
            return e.errorCode;
        } catch (java.lang.Exception e2) {
            android.util.Log.e(TAG, "Can not connect to keystore", e2);
            return 4;
        }
    }

    public static int clearNamespace(int i, long j) {
        android.os.StrictMode.noteDiskWrite();
        try {
            getService().clearNamespace(i, j);
            return 0;
        } catch (android.os.ServiceSpecificException e) {
            android.util.Log.e(TAG, "clearNamespace failed", e);
            return e.errorCode;
        } catch (java.lang.Exception e2) {
            android.util.Log.e(TAG, "Can not connect to keystore", e2);
            return 4;
        }
    }

    public static void onDeviceOffBody() {
        android.os.StrictMode.noteDiskWrite();
        try {
            getService().onDeviceOffBody();
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Error while reporting device off body event.", e);
        }
    }

    public static int migrateKeyNamespace(android.system.keystore2.KeyDescriptor keyDescriptor, android.system.keystore2.KeyDescriptor keyDescriptor2) {
        android.os.StrictMode.noteDiskWrite();
        try {
            getService().migrateKeyNamespace(keyDescriptor, keyDescriptor2);
            return 0;
        } catch (android.os.ServiceSpecificException e) {
            android.util.Log.e(TAG, "migrateKeyNamespace failed", e);
            return e.errorCode;
        } catch (java.lang.Exception e2) {
            android.util.Log.e(TAG, "Can not connect to keystore", e2);
            return 4;
        }
    }

    public static long[] getAllAppUidsAffectedBySid(int i, long j) throws android.security.KeyStoreException {
        android.os.StrictMode.noteDiskWrite();
        try {
            return getService().getAppUidsAffectedBySid(i, j);
        } catch (android.os.RemoteException | java.lang.NullPointerException e) {
            throw new android.security.KeyStoreException(4, "Failure to connect to Keystore while trying to get apps affected by SID.");
        } catch (android.os.ServiceSpecificException e2) {
            throw new android.security.KeyStoreException(e2.errorCode, "Keystore error while trying to get apps affected by SID.");
        }
    }

    public static void deleteAllKeys() throws android.security.KeyStoreException {
        android.os.StrictMode.noteDiskWrite();
        try {
            getService().deleteAllKeys();
        } catch (android.os.RemoteException | java.lang.NullPointerException e) {
            throw new android.security.KeyStoreException(4, "Failure to connect to Keystore while trying to delete all keys.");
        } catch (android.os.ServiceSpecificException e2) {
            throw new android.security.KeyStoreException(e2.errorCode, "Keystore error while trying to delete all keys.");
        }
    }
}
