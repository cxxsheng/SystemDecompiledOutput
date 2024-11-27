package android.security.keystore;

/* loaded from: classes3.dex */
public class StrongBoxUnavailableException extends java.security.ProviderException {
    public StrongBoxUnavailableException() {
    }

    public StrongBoxUnavailableException(java.lang.String str) {
        super(str, new android.security.KeyStoreException(-68, "No StrongBox available"));
    }

    public StrongBoxUnavailableException(java.lang.String str, java.lang.Throwable th) {
        super(str, th);
    }

    public StrongBoxUnavailableException(java.lang.Throwable th) {
        super(th);
    }
}
