package android.util;

/* loaded from: classes3.dex */
public class AndroidException extends java.lang.Exception {
    public AndroidException() {
    }

    public AndroidException(java.lang.String str) {
        super(str);
    }

    public AndroidException(java.lang.String str, java.lang.Throwable th) {
        super(str, th);
    }

    public AndroidException(java.lang.Exception exc) {
        super(exc);
    }

    protected AndroidException(java.lang.String str, java.lang.Throwable th, boolean z, boolean z2) {
        super(str, th, z, z2);
    }
}
