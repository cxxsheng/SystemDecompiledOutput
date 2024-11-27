package android.security.keystore;

/* loaded from: classes3.dex */
public class UserNotAuthenticatedException extends java.security.InvalidKeyException {
    public UserNotAuthenticatedException() {
        super("User not authenticated");
    }

    public UserNotAuthenticatedException(java.lang.String str) {
        super(str);
    }

    public UserNotAuthenticatedException(java.lang.String str, java.lang.Throwable th) {
        super(str, th);
    }
}
