package android.security.keystore;

/* loaded from: classes3.dex */
public class KeyExpiredException extends java.security.InvalidKeyException {
    public KeyExpiredException() {
        super("Key expired");
    }

    public KeyExpiredException(java.lang.String str) {
        super(str);
    }

    public KeyExpiredException(java.lang.String str, java.lang.Throwable th) {
        super(str, th);
    }
}
