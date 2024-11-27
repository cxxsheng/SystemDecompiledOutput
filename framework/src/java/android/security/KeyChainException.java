package android.security;

/* loaded from: classes3.dex */
public class KeyChainException extends java.lang.Exception {
    public KeyChainException() {
    }

    public KeyChainException(java.lang.String str) {
        super(str);
    }

    public KeyChainException(java.lang.String str, java.lang.Throwable th) {
        super(str, th);
    }

    public KeyChainException(java.lang.Throwable th) {
        super(th == null ? null : th.toString(), th);
    }
}
