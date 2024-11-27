package android.security;

@java.lang.FunctionalInterface
/* loaded from: classes3.dex */
interface CheckedRemoteRequest<R> {
    R execute() throws android.os.RemoteException;
}
