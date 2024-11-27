package com.android.server.backup;

/* loaded from: classes5.dex */
public class PermissionBackupHelper extends android.app.backup.BlobBackupHelper {
    private static final boolean DEBUG = false;
    private static final java.lang.String KEY_PERMISSIONS = "permissions";
    private static final int STATE_VERSION = 1;
    private static final java.lang.String TAG = "PermissionBackup";
    private final android.permission.PermissionManagerInternal mPermissionManager;
    private final int mUserId;

    public PermissionBackupHelper(int i) {
        super(1, KEY_PERMISSIONS);
        this.mUserId = i;
        this.mPermissionManager = (android.permission.PermissionManagerInternal) com.android.server.LocalServices.getService(android.permission.PermissionManagerInternal.class);
    }

    @Override // android.app.backup.BlobBackupHelper
    protected byte[] getBackupPayload(java.lang.String str) {
        char c;
        try {
            switch (str.hashCode()) {
                case 1133704324:
                    if (str.equals(KEY_PERMISSIONS)) {
                        c = 0;
                        break;
                    }
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    return this.mPermissionManager.backupRuntimePermissions(this.mUserId);
                default:
                    android.util.Slog.w(TAG, "Unexpected backup key " + str);
                    return null;
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Unable to store payload " + str, e);
            return null;
        }
    }

    @Override // android.app.backup.BlobBackupHelper
    protected void applyRestoredPayload(java.lang.String str, byte[] bArr) {
        char c;
        try {
            switch (str.hashCode()) {
                case 1133704324:
                    if (str.equals(KEY_PERMISSIONS)) {
                        c = 0;
                        break;
                    }
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    this.mPermissionManager.restoreRuntimePermissions(bArr, this.mUserId);
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
