package com.android.server.backup;

/* loaded from: classes5.dex */
public class ShortcutBackupHelper extends android.app.backup.BlobBackupHelper {
    private static final int BLOB_VERSION = 1;
    private static final java.lang.String KEY_USER_FILE = "shortcutuser.xml";
    private static final java.lang.String TAG = "ShortcutBackupAgent";
    private final int mUserId;

    public ShortcutBackupHelper(int i) {
        super(1, KEY_USER_FILE);
        this.mUserId = i;
    }

    private android.content.pm.IShortcutService getShortcutService() {
        return android.content.pm.IShortcutService.Stub.asInterface(android.os.ServiceManager.getService("shortcut"));
    }

    @Override // android.app.backup.BlobBackupHelper
    protected byte[] getBackupPayload(java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case -792920646:
                if (str.equals(KEY_USER_FILE)) {
                    c = 0;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                try {
                    return getShortcutService().getBackupPayload(this.mUserId);
                } catch (java.lang.Exception e) {
                    android.util.Slog.wtf(TAG, "Backup failed", e);
                    return null;
                }
            default:
                android.util.Slog.w(TAG, "Unknown key: " + str);
                return null;
        }
    }

    @Override // android.app.backup.BlobBackupHelper
    protected void applyRestoredPayload(java.lang.String str, byte[] bArr) {
        char c;
        switch (str.hashCode()) {
            case -792920646:
                if (str.equals(KEY_USER_FILE)) {
                    c = 0;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                try {
                    getShortcutService().applyRestore(bArr, this.mUserId);
                    break;
                } catch (java.lang.Exception e) {
                    android.util.Slog.wtf(TAG, "Restore failed", e);
                }
            default:
                android.util.Slog.w(TAG, "Unknown key: " + str);
                break;
        }
    }
}
