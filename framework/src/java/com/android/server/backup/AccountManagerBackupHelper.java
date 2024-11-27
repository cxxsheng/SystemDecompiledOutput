package com.android.server.backup;

/* loaded from: classes5.dex */
public class AccountManagerBackupHelper extends android.app.backup.BlobBackupHelper {
    private static final boolean DEBUG = false;
    private static final java.lang.String KEY_ACCOUNT_ACCESS_GRANTS = "account_access_grants";
    private static final int STATE_VERSION = 1;
    private static final java.lang.String TAG = "AccountsBackup";
    private final int mUserId;

    public AccountManagerBackupHelper(int i) {
        super(1, KEY_ACCOUNT_ACCESS_GRANTS);
        this.mUserId = i;
    }

    @Override // android.app.backup.BlobBackupHelper
    protected byte[] getBackupPayload(java.lang.String str) {
        char c;
        android.accounts.AccountManagerInternal accountManagerInternal = (android.accounts.AccountManagerInternal) com.android.server.LocalServices.getService(android.accounts.AccountManagerInternal.class);
        try {
            switch (str.hashCode()) {
                case 1544100736:
                    if (str.equals(KEY_ACCOUNT_ACCESS_GRANTS)) {
                        c = 0;
                        break;
                    }
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    return accountManagerInternal.backupAccountAccessPermissions(this.mUserId);
                default:
                    android.util.Slog.w(TAG, "Unexpected backup key " + str);
                    break;
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Unable to store payload " + str, e);
        }
        return new byte[0];
    }

    @Override // android.app.backup.BlobBackupHelper
    protected void applyRestoredPayload(java.lang.String str, byte[] bArr) {
        char c;
        android.accounts.AccountManagerInternal accountManagerInternal = (android.accounts.AccountManagerInternal) com.android.server.LocalServices.getService(android.accounts.AccountManagerInternal.class);
        try {
            switch (str.hashCode()) {
                case 1544100736:
                    if (str.equals(KEY_ACCOUNT_ACCESS_GRANTS)) {
                        c = 0;
                        break;
                    }
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    accountManagerInternal.restoreAccountAccessPermissions(bArr, this.mUserId);
                    break;
                default:
                    android.util.Slog.w(TAG, "Unexpected restore key " + str);
                    break;
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Unable to restore key " + str, e);
        }
    }
}
