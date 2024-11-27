package android.app;

/* loaded from: classes.dex */
public abstract class SynchronousUserSwitchObserver extends android.app.UserSwitchObserver {
    public abstract void onUserSwitching(int i) throws android.os.RemoteException;

    @Override // android.app.UserSwitchObserver, android.app.IUserSwitchObserver
    public final void onUserSwitching(int i, android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
        try {
            onUserSwitching(i);
        } finally {
            if (iRemoteCallback != null) {
                iRemoteCallback.sendResult(null);
            }
        }
    }
}
