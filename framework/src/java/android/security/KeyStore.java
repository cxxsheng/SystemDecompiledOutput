package android.security;

/* loaded from: classes3.dex */
public class KeyStore {
    private static final android.security.KeyStore KEY_STORE = new android.security.KeyStore();
    public static final int NO_ERROR = 1;
    public static final int UID_SELF = -1;

    public static android.security.KeyStore getInstance() {
        return KEY_STORE;
    }

    public int addAuthToken(byte[] bArr) {
        android.os.StrictMode.noteDiskWrite();
        return android.security.Authorization.addAuthToken(bArr);
    }

    public void onDeviceOffBody() {
        android.security.AndroidKeyStoreMaintenance.onDeviceOffBody();
    }
}
