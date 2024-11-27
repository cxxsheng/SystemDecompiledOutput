package android.os;

/* loaded from: classes3.dex */
public class RemoteException extends android.util.AndroidException {
    public RemoteException() {
    }

    public RemoteException(java.lang.String str) {
        super(str);
    }

    public RemoteException(java.lang.String str, java.lang.Throwable th, boolean z, boolean z2) {
        super(str, th, z, z2);
    }

    public RemoteException(java.lang.Throwable th) {
        this(th.getMessage(), th, true, false);
    }

    public java.lang.RuntimeException rethrowAsRuntimeException() {
        throw new java.lang.RuntimeException(this);
    }

    public java.lang.RuntimeException rethrowFromSystemServer() {
        if (this instanceof android.os.DeadObjectException) {
            throw new android.os.DeadSystemRuntimeException();
        }
        throw new java.lang.RuntimeException(this);
    }
}
