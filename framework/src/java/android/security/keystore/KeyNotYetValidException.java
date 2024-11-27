package android.security.keystore;

/* loaded from: classes3.dex */
public class KeyNotYetValidException extends java.security.InvalidKeyException {
    public KeyNotYetValidException() {
        super("Key not yet valid");
    }

    public KeyNotYetValidException(java.lang.String str) {
        super(str);
    }

    public KeyNotYetValidException(java.lang.String str, java.lang.Throwable th) {
        super(str, th);
    }
}
