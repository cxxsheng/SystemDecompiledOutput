package com.android.server.backup;

/* loaded from: classes5.dex */
public class CompanionBackupHelper extends android.app.backup.BlobBackupHelper {
    private static final int BLOB_VERSION = 1;
    private static final java.lang.String KEY_COMPANION = "companion";
    private static final java.lang.String TAG = "CompanionBackupHelper";
    private final int mUserId;

    public CompanionBackupHelper(int i) {
        super(1, KEY_COMPANION);
        this.mUserId = i;
    }

    @Override // android.app.backup.BlobBackupHelper
    protected byte[] getBackupPayload(java.lang.String str) {
        if (KEY_COMPANION.equals(str)) {
            try {
                return android.companion.ICompanionDeviceManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.COMPANION_DEVICE_SERVICE)).getBackupPayload(this.mUserId);
            } catch (java.lang.Exception e) {
                android.util.Slog.e(TAG, "Error getting backup from CompanionDeviceManager.", e);
            }
        }
        return null;
    }

    @Override // android.app.backup.BlobBackupHelper
    protected void applyRestoredPayload(java.lang.String str, byte[] bArr) {
        android.util.Slog.i(TAG, "Got companion backup data.");
        if (KEY_COMPANION.equals(str)) {
            try {
                android.companion.ICompanionDeviceManager.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.COMPANION_DEVICE_SERVICE)).applyRestoredPayload(bArr, this.mUserId);
            } catch (java.lang.Exception e) {
                android.util.Slog.e(TAG, "Error applying restored payload to CompanionDeviceManager.", e);
            }
        }
    }
}
