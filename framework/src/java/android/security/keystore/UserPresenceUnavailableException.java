package android.security.keystore;

/* loaded from: classes3.dex */
public class UserPresenceUnavailableException extends java.security.InvalidKeyException {
    public UserPresenceUnavailableException() {
        super("No Strong Box available.");
    }

    public UserPresenceUnavailableException(java.lang.String str) {
        super(str);
    }

    public UserPresenceUnavailableException(java.lang.String str, java.lang.Throwable th) {
        super(str, th);
    }
}
