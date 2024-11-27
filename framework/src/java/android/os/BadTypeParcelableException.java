package android.os;

/* loaded from: classes3.dex */
class BadTypeParcelableException extends android.os.BadParcelableException {
    BadTypeParcelableException(java.lang.String str) {
        super(str);
    }

    BadTypeParcelableException(java.lang.Exception exc) {
        super(exc);
    }

    BadTypeParcelableException(java.lang.String str, java.lang.Throwable th) {
        super(str, th);
    }
}
