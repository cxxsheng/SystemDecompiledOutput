package android.security.keystore;

/* loaded from: classes3.dex */
public class KeyPermanentlyInvalidatedException extends java.security.InvalidKeyException {
    public KeyPermanentlyInvalidatedException() {
        super("Key permanently invalidated");
    }

    public KeyPermanentlyInvalidatedException(java.lang.String str) {
        super(str);
    }

    public KeyPermanentlyInvalidatedException(java.lang.String str, java.lang.Throwable th) {
        super(str, th);
    }
}
