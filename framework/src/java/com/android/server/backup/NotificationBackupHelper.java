package com.android.server.backup;

/* loaded from: classes5.dex */
public class NotificationBackupHelper extends android.app.backup.BlobBackupHelper {
    static final int BLOB_VERSION = 1;
    static final java.lang.String KEY_NOTIFICATIONS = "notifications";
    private final int mUserId;
    static final java.lang.String TAG = "NotifBackupHelper";
    static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);

    public NotificationBackupHelper(int i) {
        super(1, KEY_NOTIFICATIONS);
        this.mUserId = i;
    }

    @Override // android.app.backup.BlobBackupHelper
    protected byte[] getBackupPayload(java.lang.String str) {
        if (!KEY_NOTIFICATIONS.equals(str)) {
            return null;
        }
        try {
            return android.app.INotificationManager.Stub.asInterface(android.os.ServiceManager.getService("notification")).getBackupPayload(this.mUserId);
        } catch (java.lang.Exception e) {
            android.util.Slog.e(TAG, "Couldn't communicate with notification manager", e);
            return null;
        }
    }

    @Override // android.app.backup.BlobBackupHelper
    protected void applyRestoredPayload(java.lang.String str, byte[] bArr) {
        if (DEBUG) {
            android.util.Slog.v(TAG, "Got restore of " + str);
        }
        if (KEY_NOTIFICATIONS.equals(str)) {
            try {
                android.app.INotificationManager.Stub.asInterface(android.os.ServiceManager.getService("notification")).applyRestore(bArr, this.mUserId);
            } catch (java.lang.Exception e) {
                android.util.Slog.e(TAG, "Couldn't communicate with notification manager", e);
            }
        }
    }
}
