package android.security;

/* loaded from: classes3.dex */
public class AndroidProtectedConfirmation {
    public static final int ERROR_ABORTED = 2;
    public static final int ERROR_CANCELED = 1;
    public static final int ERROR_IGNORED = 4;
    public static final int ERROR_OK = 0;
    public static final int ERROR_OPERATION_PENDING = 3;
    public static final int ERROR_SYSTEM_ERROR = 5;
    public static final int ERROR_UNIMPLEMENTED = 6;
    public static final int FLAG_UI_OPTION_INVERTED = 1;
    public static final int FLAG_UI_OPTION_MAGNIFIED = 2;
    private static final java.lang.String TAG = "AndroidProtectedConfirmation";
    private android.security.apc.IProtectedConfirmation mProtectedConfirmation = null;

    private synchronized android.security.apc.IProtectedConfirmation getService() {
        if (this.mProtectedConfirmation == null) {
            this.mProtectedConfirmation = android.security.apc.IProtectedConfirmation.Stub.asInterface(android.os.ServiceManager.getService("android.security.apc"));
        }
        return this.mProtectedConfirmation;
    }

    @java.lang.Deprecated
    public int presentConfirmationPrompt(android.security.apc.IConfirmationCallback iConfirmationCallback, java.lang.String str, byte[] bArr, java.lang.String str2, int i) {
        try {
            getService().presentPrompt(iConfirmationCallback, str, bArr, str2, i);
            return 0;
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Cannot connect to keystore", e);
            return 5;
        } catch (android.os.ServiceSpecificException e2) {
            return e2.errorCode;
        }
    }

    @java.lang.Deprecated
    public int cancelConfirmationPrompt(android.security.apc.IConfirmationCallback iConfirmationCallback) {
        try {
            getService().cancelPrompt(iConfirmationCallback);
            return 0;
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Cannot connect to keystore", e);
            return 5;
        } catch (android.os.ServiceSpecificException e2) {
            return e2.errorCode;
        }
    }

    @java.lang.Deprecated
    public boolean isConfirmationPromptSupported() {
        try {
            return getService().isSupported();
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Cannot connect to keystore", e);
            return false;
        }
    }
}
