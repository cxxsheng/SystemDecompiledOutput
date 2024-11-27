package android.security.keystore;

/* loaded from: classes3.dex */
public class SecureKeyImportUnavailableException extends java.security.ProviderException {
    public SecureKeyImportUnavailableException() {
    }

    public SecureKeyImportUnavailableException(java.lang.String str) {
        super(str, new android.security.KeyStoreException(-68, "Secure Key Import not available"));
    }

    public SecureKeyImportUnavailableException(java.lang.String str, java.lang.Throwable th) {
        super(str, th);
    }

    public SecureKeyImportUnavailableException(java.lang.Throwable th) {
        super(th);
    }
}
