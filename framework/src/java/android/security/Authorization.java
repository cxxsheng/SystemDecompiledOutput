package android.security;

/* loaded from: classes3.dex */
public class Authorization {
    public static final int SYSTEM_ERROR = 4;
    private static final java.lang.String TAG = "KeystoreAuthorization";

    public static android.security.authorization.IKeystoreAuthorization getService() {
        return android.security.authorization.IKeystoreAuthorization.Stub.asInterface(android.os.ServiceManager.checkService("android.security.authorization"));
    }

    public static int addAuthToken(android.hardware.security.keymint.HardwareAuthToken hardwareAuthToken) {
        android.os.StrictMode.noteSlowCall("addAuthToken");
        try {
            getService().addAuthToken(hardwareAuthToken);
            return 0;
        } catch (android.os.RemoteException | java.lang.NullPointerException e) {
            android.util.Log.w(TAG, "Can not connect to keystore", e);
            return 4;
        } catch (android.os.ServiceSpecificException e2) {
            return e2.errorCode;
        }
    }

    public static int addAuthToken(byte[] bArr) {
        return addAuthToken(android.security.AuthTokenUtils.toHardwareAuthToken(bArr));
    }

    public static int onDeviceUnlocked(int i, byte[] bArr) {
        android.os.StrictMode.noteDiskWrite();
        try {
            getService().onDeviceUnlocked(i, bArr);
            return 0;
        } catch (android.os.RemoteException | java.lang.NullPointerException e) {
            android.util.Log.w(TAG, "Can not connect to keystore", e);
            return 4;
        } catch (android.os.ServiceSpecificException e2) {
            return e2.errorCode;
        }
    }

    public static int onDeviceLocked(int i, long[] jArr, boolean z) {
        android.os.StrictMode.noteDiskWrite();
        try {
            getService().onDeviceLocked(i, jArr, z);
            return 0;
        } catch (android.os.RemoteException | java.lang.NullPointerException e) {
            android.util.Log.w(TAG, "Can not connect to keystore", e);
            return 4;
        } catch (android.os.ServiceSpecificException e2) {
            return e2.errorCode;
        }
    }

    public static long getLastAuthenticationTime(long j, int[] iArr) {
        try {
            return getService().getLastAuthTime(j, iArr);
        } catch (android.os.RemoteException | java.lang.NullPointerException e) {
            android.util.Log.w(TAG, "Can not connect to keystore", e);
            return -1L;
        } catch (android.os.ServiceSpecificException e2) {
            return -1L;
        }
    }
}
