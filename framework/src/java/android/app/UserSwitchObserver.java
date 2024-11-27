package android.app;

/* loaded from: classes.dex */
public class UserSwitchObserver extends android.app.IUserSwitchObserver.Stub {
    @Override // android.app.IUserSwitchObserver
    public void onBeforeUserSwitching(int i) throws android.os.RemoteException {
    }

    public void onUserSwitching(int i, android.os.IRemoteCallback iRemoteCallback) throws android.os.RemoteException {
        if (iRemoteCallback != null) {
            iRemoteCallback.sendResult(null);
        }
    }

    @Override // android.app.IUserSwitchObserver
    public void onUserSwitchComplete(int i) throws android.os.RemoteException {
    }

    @Override // android.app.IUserSwitchObserver
    public void onForegroundProfileSwitch(int i) throws android.os.RemoteException {
    }

    @Override // android.app.IUserSwitchObserver
    public void onLockedBootComplete(int i) throws android.os.RemoteException {
    }
}
