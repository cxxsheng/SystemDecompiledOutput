package android.accounts;

/* loaded from: classes.dex */
public abstract class AccountManagerInternal {

    public interface OnAppPermissionChangeListener {
        void onAppPermissionChanged(android.accounts.Account account, int i);
    }

    public abstract void addOnAppPermissionChangeListener(android.accounts.AccountManagerInternal.OnAppPermissionChangeListener onAppPermissionChangeListener);

    public abstract byte[] backupAccountAccessPermissions(int i);

    public abstract boolean hasAccountAccess(android.accounts.Account account, int i);

    public abstract void requestAccountAccess(android.accounts.Account account, java.lang.String str, int i, android.os.RemoteCallback remoteCallback);

    public abstract void restoreAccountAccessPermissions(byte[] bArr, int i);
}
